/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.ComponentInputMapUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ import javax.swing.plaf.InputMapUIResource;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.swing.DefaultLayoutStyle;
/*     */ import sun.swing.ImageIconUIResource;
/*     */ import sun.swing.SwingUtilities2;
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
/*     */ public abstract class LookAndFeel
/*     */ {
/*     */   public static void installColors(JComponent paramJComponent, String paramString1, String paramString2) {
/* 173 */     Color color1 = paramJComponent.getBackground();
/* 174 */     if (color1 == null || color1 instanceof javax.swing.plaf.UIResource) {
/* 175 */       paramJComponent.setBackground(UIManager.getColor(paramString1));
/*     */     }
/*     */     
/* 178 */     Color color2 = paramJComponent.getForeground();
/* 179 */     if (color2 == null || color2 instanceof javax.swing.plaf.UIResource) {
/* 180 */       paramJComponent.setForeground(UIManager.getColor(paramString2));
/*     */     }
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
/*     */   public static void installColorsAndFont(JComponent paramJComponent, String paramString1, String paramString2, String paramString3) {
/* 206 */     Font font = paramJComponent.getFont();
/* 207 */     if (font == null || font instanceof javax.swing.plaf.UIResource) {
/* 208 */       paramJComponent.setFont(UIManager.getFont(paramString3));
/*     */     }
/*     */     
/* 211 */     installColors(paramJComponent, paramString1, paramString2);
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
/*     */   public static void installBorder(JComponent paramJComponent, String paramString) {
/* 226 */     Border border = paramJComponent.getBorder();
/* 227 */     if (border == null || border instanceof javax.swing.plaf.UIResource) {
/* 228 */       paramJComponent.setBorder(UIManager.getBorder(paramString));
/*     */     }
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
/*     */   public static void uninstallBorder(JComponent paramJComponent) {
/* 242 */     if (paramJComponent.getBorder() instanceof javax.swing.plaf.UIResource) {
/* 243 */       paramJComponent.setBorder((Border)null);
/*     */     }
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
/*     */   public static void installProperty(JComponent paramJComponent, String paramString, Object paramObject) {
/* 275 */     if (SunToolkit.isInstanceOf(paramJComponent, "javax.swing.JPasswordField")) {
/* 276 */       if (!((JPasswordField)paramJComponent).customSetUIProperty(paramString, paramObject)) {
/* 277 */         paramJComponent.setUIProperty(paramString, paramObject);
/*     */       }
/*     */     } else {
/* 280 */       paramJComponent.setUIProperty(paramString, paramObject);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JTextComponent.KeyBinding[] makeKeyBindings(Object[] paramArrayOfObject) {
/* 333 */     JTextComponent.KeyBinding[] arrayOfKeyBinding = new JTextComponent.KeyBinding[paramArrayOfObject.length / 2];
/*     */     
/* 335 */     for (byte b = 0; b < arrayOfKeyBinding.length; b++) {
/* 336 */       Object object = paramArrayOfObject[2 * b];
/*     */ 
/*     */       
/* 339 */       KeyStroke keyStroke = (object instanceof KeyStroke) ? (KeyStroke)object : KeyStroke.getKeyStroke((String)object);
/* 340 */       String str = (String)paramArrayOfObject[2 * b + 1];
/* 341 */       arrayOfKeyBinding[b] = new JTextComponent.KeyBinding(keyStroke, str);
/*     */     } 
/*     */     
/* 344 */     return arrayOfKeyBinding;
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
/*     */   public static InputMap makeInputMap(Object[] paramArrayOfObject) {
/* 361 */     InputMapUIResource inputMapUIResource = new InputMapUIResource();
/* 362 */     loadKeyBindings(inputMapUIResource, paramArrayOfObject);
/* 363 */     return inputMapUIResource;
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
/*     */   public static ComponentInputMap makeComponentInputMap(JComponent paramJComponent, Object[] paramArrayOfObject) {
/* 387 */     ComponentInputMapUIResource componentInputMapUIResource = new ComponentInputMapUIResource(paramJComponent);
/* 388 */     loadKeyBindings(componentInputMapUIResource, paramArrayOfObject);
/* 389 */     return componentInputMapUIResource;
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
/*     */   public static void loadKeyBindings(InputMap paramInputMap, Object[] paramArrayOfObject) {
/* 432 */     if (paramArrayOfObject != null) {
/* 433 */       byte b = 0; int i = paramArrayOfObject.length;
/* 434 */       for (; b < i; b++) {
/* 435 */         Object object = paramArrayOfObject[b++];
/*     */ 
/*     */         
/* 438 */         KeyStroke keyStroke = (object instanceof KeyStroke) ? (KeyStroke)object : KeyStroke.getKeyStroke((String)object);
/* 439 */         paramInputMap.put(keyStroke, paramArrayOfObject[b]);
/*     */       } 
/*     */     } 
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
/*     */   public static Object makeIcon(Class<?> paramClass, String paramString) {
/* 467 */     return SwingUtilities2.makeIcon(paramClass, paramClass, paramString);
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
/*     */   public LayoutStyle getLayoutStyle() {
/* 483 */     return DefaultLayoutStyle.getInstance();
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
/*     */   public void provideErrorFeedback(Component paramComponent) {
/* 500 */     Toolkit toolkit = null;
/* 501 */     if (paramComponent != null) {
/* 502 */       toolkit = paramComponent.getToolkit();
/*     */     } else {
/* 504 */       toolkit = Toolkit.getDefaultToolkit();
/*     */     } 
/* 506 */     toolkit.beep();
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
/*     */   public static Object getDesktopPropertyValue(String paramString, Object paramObject) {
/* 524 */     Object object = Toolkit.getDefaultToolkit().getDesktopProperty(paramString);
/* 525 */     if (object == null)
/* 526 */       return paramObject; 
/* 527 */     if (object instanceof Color)
/* 528 */       return new ColorUIResource((Color)object); 
/* 529 */     if (object instanceof Font) {
/* 530 */       return new FontUIResource((Font)object);
/*     */     }
/* 532 */     return object;
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
/*     */   public Icon getDisabledIcon(JComponent paramJComponent, Icon paramIcon) {
/* 555 */     if (paramIcon instanceof ImageIcon) {
/* 556 */       return new ImageIconUIResource(
/* 557 */           GrayFilter.createDisabledImage(((ImageIcon)paramIcon).getImage()));
/*     */     }
/* 559 */     return null;
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
/*     */   public Icon getDisabledSelectedIcon(JComponent paramJComponent, Icon paramIcon) {
/* 584 */     return getDisabledIcon(paramJComponent, paramIcon);
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
/*     */   public abstract String getName();
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
/*     */   public abstract String getID();
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
/*     */   public abstract String getDescription();
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
/*     */   public boolean getSupportsWindowDecorations() {
/* 642 */     return false;
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
/*     */   public abstract boolean isNativeLookAndFeel();
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
/*     */   public abstract boolean isSupportedLookAndFeel();
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
/*     */   public void initialize() {}
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
/*     */   public void uninitialize() {}
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
/*     */   public UIDefaults getDefaults() {
/* 717 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 727 */     return "[" + getDescription() + " - " + getClass().getName() + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/LookAndFeel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */