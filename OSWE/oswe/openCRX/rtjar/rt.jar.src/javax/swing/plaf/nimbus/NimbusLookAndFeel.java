/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.GrayFilter;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.synth.Region;
/*     */ import javax.swing.plaf.synth.SynthLookAndFeel;
/*     */ import javax.swing.plaf.synth.SynthStyle;
/*     */ import javax.swing.plaf.synth.SynthStyleFactory;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.swing.ImageIconUIResource;
/*     */ import sun.swing.plaf.GTKKeybindings;
/*     */ import sun.swing.plaf.WindowsKeybindings;
/*     */ import sun.swing.plaf.synth.SynthIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NimbusLookAndFeel
/*     */   extends SynthLookAndFeel
/*     */ {
/*  67 */   private static final String[] COMPONENT_KEYS = new String[] { "ArrowButton", "Button", "CheckBox", "CheckBoxMenuItem", "ColorChooser", "ComboBox", "DesktopPane", "DesktopIcon", "EditorPane", "FileChooser", "FormattedTextField", "InternalFrame", "InternalFrameTitlePane", "Label", "List", "Menu", "MenuBar", "MenuItem", "OptionPane", "Panel", "PasswordField", "PopupMenu", "PopupMenuSeparator", "ProgressBar", "RadioButton", "RadioButtonMenuItem", "RootPane", "ScrollBar", "ScrollBarTrack", "ScrollBarThumb", "ScrollPane", "Separator", "Slider", "SliderTrack", "SliderThumb", "Spinner", "SplitPane", "TabbedPane", "Table", "TableHeader", "TextArea", "TextField", "TextPane", "ToggleButton", "ToolBar", "ToolTip", "Tree", "Viewport" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NimbusDefaults defaults;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UIDefaults uiDefaults;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private DefaultsListener defaultsListener = new DefaultsListener();
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, Map<String, Object>> compiledDefaults;
/*     */ 
/*     */   
/*     */   private boolean defaultListenerAdded;
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 105 */     super.initialize();
/* 106 */     this.defaults.initialize();
/*     */     
/* 108 */     setStyleFactory(new SynthStyleFactory()
/*     */         {
/*     */           public SynthStyle getStyle(JComponent param1JComponent, Region param1Region) {
/* 111 */             return NimbusLookAndFeel.this.defaults.getStyle(param1JComponent, param1Region);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninitialize() {
/* 119 */     super.uninitialize();
/* 120 */     this.defaults.uninitialize();
/*     */     
/* 122 */     ImageCache.getInstance().flush();
/* 123 */     UIManager.getDefaults().removePropertyChangeListener(this.defaultsListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UIDefaults getDefaults() {
/* 130 */     if (this.uiDefaults == null) {
/*     */       
/* 132 */       String str = getSystemProperty("os.name");
/* 133 */       boolean bool = (str != null && str.contains("Windows")) ? true : false;
/*     */ 
/*     */       
/* 136 */       this.uiDefaults = super.getDefaults();
/* 137 */       this.defaults.initializeDefaults(this.uiDefaults);
/*     */ 
/*     */       
/* 140 */       if (bool) {
/* 141 */         WindowsKeybindings.installKeybindings(this.uiDefaults);
/*     */       } else {
/* 143 */         GTKKeybindings.installKeybindings(this.uiDefaults);
/*     */       } 
/*     */ 
/*     */       
/* 147 */       this.uiDefaults.put("TitledBorder.titlePosition", 
/* 148 */           Integer.valueOf(1));
/* 149 */       this.uiDefaults.put("TitledBorder.border", new BorderUIResource(new LoweredBorder()));
/*     */       
/* 151 */       this.uiDefaults.put("TitledBorder.titleColor", 
/* 152 */           getDerivedColor("text", 0.0F, 0.0F, 0.23F, 0, true));
/* 153 */       this.uiDefaults.put("TitledBorder.font", new NimbusDefaults.DerivedFont("defaultFont", 1.0F, 
/*     */             
/* 155 */             Boolean.valueOf(true), null));
/*     */ 
/*     */       
/* 158 */       this.uiDefaults.put("OptionPane.isYesLast", Boolean.valueOf(!bool));
/*     */ 
/*     */       
/* 161 */       this.uiDefaults.put("Table.scrollPaneCornerComponent", new UIDefaults.ActiveValue()
/*     */           {
/*     */             public Object createValue(UIDefaults param1UIDefaults)
/*     */             {
/* 165 */               return new TableScrollPaneCorner();
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */       
/* 171 */       this.uiDefaults.put("ToolBarSeparator[Enabled].backgroundPainter", new ToolBarSeparatorPainter());
/*     */ 
/*     */ 
/*     */       
/* 175 */       for (String str1 : COMPONENT_KEYS) {
/* 176 */         String str2 = str1 + ".foreground";
/* 177 */         if (!this.uiDefaults.containsKey(str2)) {
/* 178 */           this.uiDefaults.put(str2, new NimbusProperty(str1, "textForeground"));
/*     */         }
/*     */         
/* 181 */         str2 = str1 + ".background";
/* 182 */         if (!this.uiDefaults.containsKey(str2)) {
/* 183 */           this.uiDefaults.put(str2, new NimbusProperty(str1, "background"));
/*     */         }
/*     */         
/* 186 */         str2 = str1 + ".font";
/* 187 */         if (!this.uiDefaults.containsKey(str2)) {
/* 188 */           this.uiDefaults.put(str2, new NimbusProperty(str1, "font"));
/*     */         }
/*     */         
/* 191 */         str2 = str1 + ".disabledText";
/* 192 */         if (!this.uiDefaults.containsKey(str2)) {
/* 193 */           this.uiDefaults.put(str2, new NimbusProperty(str1, "Disabled", "textForeground"));
/*     */         }
/*     */ 
/*     */         
/* 197 */         str2 = str1 + ".disabled";
/* 198 */         if (!this.uiDefaults.containsKey(str2)) {
/* 199 */           this.uiDefaults.put(str2, new NimbusProperty(str1, "Disabled", "background"));
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 207 */       this.uiDefaults.put("FileView.computerIcon", new LinkProperty("FileChooser.homeFolderIcon"));
/*     */       
/* 209 */       this.uiDefaults.put("FileView.directoryIcon", new LinkProperty("FileChooser.directoryIcon"));
/*     */       
/* 211 */       this.uiDefaults.put("FileView.fileIcon", new LinkProperty("FileChooser.fileIcon"));
/*     */       
/* 213 */       this.uiDefaults.put("FileView.floppyDriveIcon", new LinkProperty("FileChooser.floppyDriveIcon"));
/*     */       
/* 215 */       this.uiDefaults.put("FileView.hardDriveIcon", new LinkProperty("FileChooser.hardDriveIcon"));
/*     */     } 
/*     */     
/* 218 */     return this.uiDefaults;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NimbusStyle getStyle(JComponent paramJComponent, Region paramRegion) {
/* 231 */     return (NimbusStyle)SynthLookAndFeel.getStyle(paramJComponent, paramRegion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 241 */     return "Nimbus";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/* 251 */     return "Nimbus";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 260 */     return "Nimbus Look and Feel";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUpdateStyleOnAncestorChanged() {
/* 268 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shouldUpdateStyleOnEvent(PropertyChangeEvent paramPropertyChangeEvent) {
/* 286 */     String str = paramPropertyChangeEvent.getPropertyName();
/*     */ 
/*     */     
/* 289 */     if ("name" == str || "ancestor" == str || "Nimbus.Overrides" == str || "Nimbus.Overrides.InheritDefaults" == str || "JComponent.sizeVariant" == str) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       JComponent jComponent = (JComponent)paramPropertyChangeEvent.getSource();
/* 296 */       this.defaults.clearOverridesCache(jComponent);
/* 297 */       return true;
/*     */     } 
/*     */     
/* 300 */     return super.shouldUpdateStyleOnEvent(paramPropertyChangeEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(Region paramRegion, String paramString) {
/* 348 */     this.defaults.register(paramRegion, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSystemProperty(String paramString) {
/* 355 */     return AccessController.<String>doPrivileged(new GetPropertyAction(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public Icon getDisabledIcon(JComponent paramJComponent, Icon paramIcon) {
/* 360 */     if (paramIcon instanceof SynthIcon) {
/* 361 */       SynthIcon synthIcon = (SynthIcon)paramIcon;
/* 362 */       BufferedImage bufferedImage = EffectUtils.createCompatibleTranslucentImage(synthIcon
/* 363 */           .getIconWidth(), synthIcon.getIconHeight());
/* 364 */       Graphics2D graphics2D = bufferedImage.createGraphics();
/* 365 */       synthIcon.paintIcon(paramJComponent, graphics2D, 0, 0);
/* 366 */       graphics2D.dispose();
/* 367 */       return new ImageIconUIResource(GrayFilter.createDisabledImage(bufferedImage));
/*     */     } 
/* 369 */     return super.getDisabledIcon(paramJComponent, paramIcon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getDerivedColor(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, boolean paramBoolean) {
/* 390 */     return this.defaults.getDerivedColor(paramString, paramFloat1, paramFloat2, paramFloat3, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Color getDerivedColor(Color paramColor1, Color paramColor2, float paramFloat, boolean paramBoolean) {
/* 407 */     int i = deriveARGB(paramColor1, paramColor2, paramFloat);
/* 408 */     if (paramBoolean) {
/* 409 */       return new ColorUIResource(i);
/*     */     }
/* 411 */     return new Color(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Color getDerivedColor(Color paramColor1, Color paramColor2, float paramFloat) {
/* 427 */     return getDerivedColor(paramColor1, paramColor2, paramFloat, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object resolveToolbarConstraint(JToolBar paramJToolBar) {
/* 451 */     if (paramJToolBar != null) {
/* 452 */       Container container = paramJToolBar.getParent();
/* 453 */       if (container != null) {
/* 454 */         LayoutManager layoutManager = container.getLayout();
/* 455 */         if (layoutManager instanceof BorderLayout) {
/* 456 */           BorderLayout borderLayout = (BorderLayout)layoutManager;
/* 457 */           Object object = borderLayout.getConstraints(paramJToolBar);
/* 458 */           if (object == "South" || object == "East" || object == "West") {
/* 459 */             return object;
/*     */           }
/* 461 */           return "North";
/*     */         } 
/*     */       } 
/*     */     } 
/* 465 */     return "North";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int deriveARGB(Color paramColor1, Color paramColor2, float paramFloat) {
/* 480 */     int i = paramColor1.getRed() + Math.round((paramColor2.getRed() - paramColor1.getRed()) * paramFloat);
/*     */     
/* 482 */     int j = paramColor1.getGreen() + Math.round((paramColor2.getGreen() - paramColor1.getGreen()) * paramFloat);
/*     */     
/* 484 */     int k = paramColor1.getBlue() + Math.round((paramColor2.getBlue() - paramColor1.getBlue()) * paramFloat);
/*     */     
/* 486 */     int m = paramColor1.getAlpha() + Math.round((paramColor2.getAlpha() - paramColor1.getAlpha()) * paramFloat);
/* 487 */     return (m & 0xFF) << 24 | (i & 0xFF) << 16 | (j & 0xFF) << 8 | k & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class LinkProperty
/*     */     implements UIDefaults.ActiveValue, UIResource
/*     */   {
/*     */     private String dstPropName;
/*     */ 
/*     */ 
/*     */     
/*     */     private LinkProperty(String param1String) {
/* 500 */       this.dstPropName = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object createValue(UIDefaults param1UIDefaults) {
/* 505 */       return UIManager.get(this.dstPropName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class NimbusProperty
/*     */     implements UIDefaults.ActiveValue, UIResource
/*     */   {
/*     */     private String prefix;
/*     */     
/* 515 */     private String state = null;
/*     */     private String suffix;
/*     */     private boolean isFont;
/*     */     
/*     */     private NimbusProperty(String param1String1, String param1String2) {
/* 520 */       this.prefix = param1String1;
/* 521 */       this.suffix = param1String2;
/* 522 */       this.isFont = "font".equals(param1String2);
/*     */     }
/*     */     
/*     */     private NimbusProperty(String param1String1, String param1String2, String param1String3) {
/* 526 */       this(param1String1, param1String3);
/* 527 */       this.state = param1String2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object createValue(UIDefaults param1UIDefaults) {
/* 539 */       Object object = null;
/*     */       
/* 541 */       if (this.state != null) {
/* 542 */         object = NimbusLookAndFeel.this.uiDefaults.get(this.prefix + "[" + this.state + "]." + this.suffix);
/*     */       }
/*     */       
/* 545 */       if (object == null) {
/* 546 */         object = NimbusLookAndFeel.this.uiDefaults.get(this.prefix + "[Enabled]." + this.suffix);
/*     */       }
/*     */       
/* 549 */       if (object == null) {
/* 550 */         if (this.isFont) {
/* 551 */           object = NimbusLookAndFeel.this.uiDefaults.get("defaultFont");
/*     */         } else {
/* 553 */           object = NimbusLookAndFeel.this.uiDefaults.get(this.suffix);
/*     */         } 
/*     */       }
/* 556 */       return object;
/*     */     } }
/*     */   
/*     */   public NimbusLookAndFeel() {
/* 560 */     this.compiledDefaults = null;
/* 561 */     this.defaultListenerAdded = false;
/*     */     this.defaults = new NimbusDefaults();
/*     */   } static String parsePrefix(String paramString) {
/* 564 */     if (paramString == null) {
/* 565 */       return null;
/*     */     }
/* 567 */     boolean bool = false;
/* 568 */     for (byte b = 0; b < paramString.length(); b++) {
/* 569 */       char c = paramString.charAt(b);
/* 570 */       if (c == '"') {
/* 571 */         bool = !bool ? true : false;
/* 572 */       } else if ((c == '[' || c == '.') && !bool) {
/* 573 */         return paramString.substring(0, b);
/*     */       } 
/*     */     } 
/* 576 */     return null;
/*     */   }
/*     */   
/*     */   Map<String, Object> getDefaultsForPrefix(String paramString) {
/* 580 */     if (this.compiledDefaults == null) {
/* 581 */       this.compiledDefaults = new HashMap<>();
/* 582 */       for (Map.Entry<Object, Object> entry : UIManager.getDefaults().entrySet()) {
/* 583 */         if (entry.getKey() instanceof String) {
/* 584 */           addDefault((String)entry.getKey(), entry.getValue());
/*     */         }
/*     */       } 
/* 587 */       if (!this.defaultListenerAdded) {
/* 588 */         UIManager.getDefaults().addPropertyChangeListener(this.defaultsListener);
/* 589 */         this.defaultListenerAdded = true;
/*     */       } 
/*     */     } 
/* 592 */     return this.compiledDefaults.get(paramString);
/*     */   }
/*     */   
/*     */   private void addDefault(String paramString, Object paramObject) {
/* 596 */     if (this.compiledDefaults == null) {
/*     */       return;
/*     */     }
/*     */     
/* 600 */     String str = parsePrefix(paramString);
/* 601 */     if (str != null) {
/* 602 */       Map<Object, Object> map = (Map)this.compiledDefaults.get(str);
/* 603 */       if (map == null) {
/* 604 */         map = new HashMap<>();
/* 605 */         this.compiledDefaults.put(str, map);
/*     */       } 
/* 607 */       map.put(paramString, paramObject);
/*     */     } 
/*     */   }
/*     */   private class DefaultsListener implements PropertyChangeListener { private DefaultsListener() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 613 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 614 */       if ("UIDefaults".equals(str)) {
/* 615 */         NimbusLookAndFeel.this.compiledDefaults = null;
/*     */       } else {
/* 617 */         NimbusLookAndFeel.this.addDefault(str, param1PropertyChangeEvent.getNewValue());
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/NimbusLookAndFeel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */