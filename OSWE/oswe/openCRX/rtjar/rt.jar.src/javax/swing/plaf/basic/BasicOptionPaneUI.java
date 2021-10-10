/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.HierarchyEvent;
/*      */ import java.awt.event.HierarchyListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.security.AccessController;
/*      */ import java.util.Locale;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.OptionPaneUI;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicOptionPaneUI
/*      */   extends OptionPaneUI
/*      */ {
/*      */   public static final int MinimumWidth = 262;
/*      */   public static final int MinimumHeight = 90;
/*  112 */   private static String newline = AccessController.<String>doPrivileged(new GetPropertyAction("line.separator")); protected JOptionPane optionPane; protected Dimension minimumSize; protected JComponent inputComponent;
/*      */   static {
/*  114 */     if (newline == null)
/*  115 */       newline = "\n"; 
/*      */   }
/*      */   protected Component initialFocusComponent; protected boolean hasCustomComponents; protected PropertyChangeListener propertyChangeListener; private Handler handler;
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  120 */     paramLazyActionMap.put(new Actions("close"));
/*  121 */     BasicLookAndFeel.installAudioActionMap(paramLazyActionMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  130 */     return new BasicOptionPaneUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  138 */     this.optionPane = (JOptionPane)paramJComponent;
/*  139 */     installDefaults();
/*  140 */     this.optionPane.setLayout(createLayoutManager());
/*  141 */     installComponents();
/*  142 */     installListeners();
/*  143 */     installKeyboardActions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  151 */     uninstallComponents();
/*  152 */     this.optionPane.setLayout((LayoutManager)null);
/*  153 */     uninstallKeyboardActions();
/*  154 */     uninstallListeners();
/*  155 */     uninstallDefaults();
/*  156 */     this.optionPane = null;
/*      */   }
/*      */   
/*      */   protected void installDefaults() {
/*  160 */     LookAndFeel.installColorsAndFont(this.optionPane, "OptionPane.background", "OptionPane.foreground", "OptionPane.font");
/*      */     
/*  162 */     LookAndFeel.installBorder(this.optionPane, "OptionPane.border");
/*  163 */     this.minimumSize = UIManager.getDimension("OptionPane.minimumSize");
/*  164 */     LookAndFeel.installProperty(this.optionPane, "opaque", Boolean.TRUE);
/*      */   }
/*      */   
/*      */   protected void uninstallDefaults() {
/*  168 */     LookAndFeel.uninstallBorder(this.optionPane);
/*      */   }
/*      */   
/*      */   protected void installComponents() {
/*  172 */     this.optionPane.add(createMessageArea());
/*      */     
/*  174 */     Container container = createSeparator();
/*  175 */     if (container != null) {
/*  176 */       this.optionPane.add(container);
/*      */     }
/*  178 */     this.optionPane.add(createButtonArea());
/*  179 */     this.optionPane.applyComponentOrientation(this.optionPane.getComponentOrientation());
/*      */   }
/*      */   
/*      */   protected void uninstallComponents() {
/*  183 */     this.hasCustomComponents = false;
/*  184 */     this.inputComponent = null;
/*  185 */     this.initialFocusComponent = null;
/*  186 */     this.optionPane.removeAll();
/*      */   }
/*      */   
/*      */   protected LayoutManager createLayoutManager() {
/*  190 */     return new BoxLayout(this.optionPane, 1);
/*      */   }
/*      */   
/*      */   protected void installListeners() {
/*  194 */     if ((this.propertyChangeListener = createPropertyChangeListener()) != null) {
/*  195 */       this.optionPane.addPropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void uninstallListeners() {
/*  200 */     if (this.propertyChangeListener != null) {
/*  201 */       this.optionPane.removePropertyChangeListener(this.propertyChangeListener);
/*  202 */       this.propertyChangeListener = null;
/*      */     } 
/*  204 */     this.handler = null;
/*      */   }
/*      */   
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  208 */     return getHandler();
/*      */   }
/*      */   
/*      */   private Handler getHandler() {
/*  212 */     if (this.handler == null) {
/*  213 */       this.handler = new Handler();
/*      */     }
/*  215 */     return this.handler;
/*      */   }
/*      */   
/*      */   protected void installKeyboardActions() {
/*  219 */     InputMap inputMap = getInputMap(2);
/*      */     
/*  221 */     SwingUtilities.replaceUIInputMap(this.optionPane, 2, inputMap);
/*      */ 
/*      */     
/*  224 */     LazyActionMap.installLazyActionMap(this.optionPane, BasicOptionPaneUI.class, "OptionPane.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/*  229 */     SwingUtilities.replaceUIInputMap(this.optionPane, 2, null);
/*      */     
/*  231 */     SwingUtilities.replaceUIActionMap(this.optionPane, null);
/*      */   }
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  235 */     if (paramInt == 2) {
/*  236 */       Object[] arrayOfObject = (Object[])DefaultLookup.get(this.optionPane, this, "OptionPane.windowBindings");
/*      */       
/*  238 */       if (arrayOfObject != null) {
/*  239 */         return LookAndFeel.makeComponentInputMap(this.optionPane, arrayOfObject);
/*      */       }
/*      */     } 
/*  242 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumOptionPaneSize() {
/*  250 */     if (this.minimumSize == null) {
/*  251 */       return new Dimension(262, 90);
/*      */     }
/*  253 */     return new Dimension(this.minimumSize.width, this.minimumSize.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  265 */     if (paramJComponent == this.optionPane) {
/*  266 */       Dimension dimension = getMinimumOptionPaneSize();
/*  267 */       LayoutManager layoutManager = paramJComponent.getLayout();
/*      */       
/*  269 */       if (layoutManager != null) {
/*  270 */         Dimension dimension1 = layoutManager.preferredLayoutSize(paramJComponent);
/*      */         
/*  272 */         if (dimension != null)
/*  273 */           return new Dimension(
/*  274 */               Math.max(dimension1.width, dimension.width), 
/*  275 */               Math.max(dimension1.height, dimension.height)); 
/*  276 */         return dimension1;
/*      */       } 
/*  278 */       return dimension;
/*      */     } 
/*  280 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Container createMessageArea() {
/*  289 */     JPanel jPanel1 = new JPanel();
/*  290 */     Border border = (Border)DefaultLookup.get(this.optionPane, this, "OptionPane.messageAreaBorder");
/*      */     
/*  292 */     if (border != null) {
/*  293 */       jPanel1.setBorder(border);
/*      */     }
/*  295 */     jPanel1.setLayout(new BorderLayout());
/*      */ 
/*      */     
/*  298 */     JPanel jPanel2 = new JPanel(new GridBagLayout());
/*  299 */     JPanel jPanel3 = new JPanel(new BorderLayout());
/*      */     
/*  301 */     jPanel2.setName("OptionPane.body");
/*  302 */     jPanel3.setName("OptionPane.realBody");
/*      */     
/*  304 */     if (getIcon() != null) {
/*  305 */       JPanel jPanel = new JPanel();
/*  306 */       jPanel.setName("OptionPane.separator");
/*  307 */       jPanel.setPreferredSize(new Dimension(15, 1));
/*  308 */       jPanel3.add(jPanel, "Before");
/*      */     } 
/*  310 */     jPanel3.add(jPanel2, "Center");
/*      */     
/*  312 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  313 */     gridBagConstraints.gridx = gridBagConstraints.gridy = 0;
/*  314 */     gridBagConstraints.gridwidth = 0;
/*  315 */     gridBagConstraints.gridheight = 1;
/*  316 */     gridBagConstraints.anchor = DefaultLookup.getInt(this.optionPane, this, "OptionPane.messageAnchor", 10);
/*      */     
/*  318 */     gridBagConstraints.insets = new Insets(0, 0, 3, 0);
/*      */     
/*  320 */     addMessageComponents(jPanel2, gridBagConstraints, getMessage(), 
/*  321 */         getMaxCharactersPerLineCount(), false);
/*  322 */     jPanel1.add(jPanel3, "Center");
/*      */     
/*  324 */     addIcon(jPanel1);
/*  325 */     return jPanel1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addMessageComponents(Container paramContainer, GridBagConstraints paramGridBagConstraints, Object paramObject, int paramInt, boolean paramBoolean) {
/*  343 */     if (paramObject == null) {
/*      */       return;
/*      */     }
/*  346 */     if (paramObject instanceof Component) {
/*      */ 
/*      */ 
/*      */       
/*  350 */       if (paramObject instanceof JScrollPane || paramObject instanceof JPanel) {
/*  351 */         paramGridBagConstraints.fill = 1;
/*  352 */         paramGridBagConstraints.weighty = 1.0D;
/*      */       } else {
/*  354 */         paramGridBagConstraints.fill = 2;
/*      */       } 
/*  356 */       paramGridBagConstraints.weightx = 1.0D;
/*      */       
/*  358 */       paramContainer.add((Component)paramObject, paramGridBagConstraints);
/*  359 */       paramGridBagConstraints.weightx = 0.0D;
/*  360 */       paramGridBagConstraints.weighty = 0.0D;
/*  361 */       paramGridBagConstraints.fill = 0;
/*  362 */       paramGridBagConstraints.gridy++;
/*  363 */       if (!paramBoolean) {
/*  364 */         this.hasCustomComponents = true;
/*      */       }
/*      */     }
/*  367 */     else if (paramObject instanceof Object[]) {
/*  368 */       Object[] arrayOfObject = (Object[])paramObject;
/*  369 */       for (Object object : arrayOfObject) {
/*  370 */         addMessageComponents(paramContainer, paramGridBagConstraints, object, paramInt, false);
/*      */       }
/*      */     }
/*  373 */     else if (paramObject instanceof Icon) {
/*  374 */       JLabel jLabel = new JLabel((Icon)paramObject, 0);
/*  375 */       configureMessageLabel(jLabel);
/*  376 */       addMessageComponents(paramContainer, paramGridBagConstraints, jLabel, paramInt, true);
/*      */     } else {
/*      */       
/*  379 */       String str = paramObject.toString();
/*  380 */       int i = str.length();
/*  381 */       if (i <= 0) {
/*      */         return;
/*      */       }
/*      */       
/*  385 */       int k = 0;
/*      */       int j;
/*  387 */       if ((j = str.indexOf(newline)) >= 0) {
/*  388 */         k = newline.length();
/*  389 */       } else if ((j = str.indexOf("\r\n")) >= 0) {
/*  390 */         k = 2;
/*  391 */       } else if ((j = str.indexOf('\n')) >= 0) {
/*  392 */         k = 1;
/*      */       } 
/*  394 */       if (j >= 0) {
/*      */         
/*  396 */         if (j == 0) {
/*  397 */           JPanel jPanel = new JPanel() {
/*      */               public Dimension getPreferredSize() {
/*  399 */                 Font font = getFont();
/*      */                 
/*  401 */                 if (font != null) {
/*  402 */                   return new Dimension(1, font.getSize() + 2);
/*      */                 }
/*  404 */                 return new Dimension(0, 0);
/*      */               }
/*      */             };
/*  407 */           jPanel.setName("OptionPane.break");
/*  408 */           addMessageComponents(paramContainer, paramGridBagConstraints, jPanel, paramInt, true);
/*      */         } else {
/*      */           
/*  411 */           addMessageComponents(paramContainer, paramGridBagConstraints, str.substring(0, j), paramInt, false);
/*      */         } 
/*      */         
/*  414 */         addMessageComponents(paramContainer, paramGridBagConstraints, str.substring(j + k), paramInt, false);
/*      */       
/*      */       }
/*  417 */       else if (i > paramInt) {
/*  418 */         Box box = Box.createVerticalBox();
/*  419 */         box.setName("OptionPane.verticalBox");
/*  420 */         burstStringInto(box, str, paramInt);
/*  421 */         addMessageComponents(paramContainer, paramGridBagConstraints, box, paramInt, true);
/*      */       }
/*      */       else {
/*      */         
/*  425 */         JLabel jLabel = new JLabel(str, 10);
/*  426 */         jLabel.setName("OptionPane.label");
/*  427 */         configureMessageLabel(jLabel);
/*  428 */         addMessageComponents(paramContainer, paramGridBagConstraints, jLabel, paramInt, true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object getMessage() {
/*  438 */     this.inputComponent = null;
/*  439 */     if (this.optionPane != null) {
/*  440 */       if (this.optionPane.getWantsInput()) {
/*      */         JComponent jComponent;
/*      */ 
/*      */ 
/*      */         
/*  445 */         Object arrayOfObject2[], object1 = this.optionPane.getMessage();
/*  446 */         Object[] arrayOfObject1 = this.optionPane.getSelectionValues();
/*      */         
/*  448 */         Object object2 = this.optionPane.getInitialSelectionValue();
/*      */ 
/*      */         
/*  451 */         if (arrayOfObject1 != null) {
/*  452 */           if (arrayOfObject1.length < 20) {
/*  453 */             JComboBox<Object> jComboBox = new JComboBox();
/*      */             
/*  455 */             jComboBox.setName("OptionPane.comboBox");
/*  456 */             byte b = 0; int i = arrayOfObject1.length;
/*  457 */             for (; b < i; b++) {
/*  458 */               jComboBox.addItem(arrayOfObject1[b]);
/*      */             }
/*  460 */             if (object2 != null) {
/*  461 */               jComboBox.setSelectedItem(object2);
/*      */             }
/*  463 */             this.inputComponent = jComboBox;
/*  464 */             jComponent = jComboBox;
/*      */           } else {
/*      */             
/*  467 */             JList jList = new JList(arrayOfObject1);
/*  468 */             JScrollPane jScrollPane = new JScrollPane(jList);
/*      */             
/*  470 */             jScrollPane.setName("OptionPane.scrollPane");
/*  471 */             jList.setName("OptionPane.list");
/*  472 */             jList.setVisibleRowCount(10);
/*  473 */             jList.setSelectionMode(0);
/*  474 */             if (object2 != null)
/*  475 */               jList.setSelectedValue(object2, true); 
/*  476 */             jList.addMouseListener(getHandler());
/*  477 */             jComponent = jScrollPane;
/*  478 */             this.inputComponent = jList;
/*      */           } 
/*      */         } else {
/*      */           
/*  482 */           MultiplexingTextField multiplexingTextField = new MultiplexingTextField(20);
/*      */           
/*  484 */           multiplexingTextField.setName("OptionPane.textField");
/*  485 */           multiplexingTextField.setKeyStrokes(new KeyStroke[] {
/*  486 */                 KeyStroke.getKeyStroke("ENTER") });
/*  487 */           if (object2 != null) {
/*  488 */             String str = object2.toString();
/*  489 */             multiplexingTextField.setText(str);
/*  490 */             multiplexingTextField.setSelectionStart(0);
/*  491 */             multiplexingTextField.setSelectionEnd(str.length());
/*      */           } 
/*  493 */           multiplexingTextField.addActionListener(getHandler());
/*  494 */           jComponent = this.inputComponent = multiplexingTextField;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  499 */         if (object1 == null) {
/*  500 */           arrayOfObject2 = new Object[1];
/*  501 */           arrayOfObject2[0] = jComponent;
/*      */         } else {
/*      */           
/*  504 */           arrayOfObject2 = new Object[2];
/*  505 */           arrayOfObject2[0] = object1;
/*  506 */           arrayOfObject2[1] = jComponent;
/*      */         } 
/*  508 */         return arrayOfObject2;
/*      */       } 
/*  510 */       return this.optionPane.getMessage();
/*      */     } 
/*  512 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addIcon(Container paramContainer) {
/*  522 */     Icon icon = getIcon();
/*      */     
/*  524 */     if (icon != null) {
/*  525 */       JLabel jLabel = new JLabel(icon);
/*      */       
/*  527 */       jLabel.setName("OptionPane.iconLabel");
/*  528 */       jLabel.setVerticalAlignment(1);
/*  529 */       paramContainer.add(jLabel, "Before");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Icon getIcon() {
/*  539 */     Icon icon = (this.optionPane == null) ? null : this.optionPane.getIcon();
/*      */     
/*  541 */     if (icon == null && this.optionPane != null)
/*  542 */       icon = getIconForType(this.optionPane.getMessageType()); 
/*  543 */     return icon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Icon getIconForType(int paramInt) {
/*  550 */     if (paramInt < 0 || paramInt > 3)
/*  551 */       return null; 
/*  552 */     String str = null;
/*  553 */     switch (paramInt) {
/*      */       case 0:
/*  555 */         str = "OptionPane.errorIcon";
/*      */         break;
/*      */       case 1:
/*  558 */         str = "OptionPane.informationIcon";
/*      */         break;
/*      */       case 2:
/*  561 */         str = "OptionPane.warningIcon";
/*      */         break;
/*      */       case 3:
/*  564 */         str = "OptionPane.questionIcon";
/*      */         break;
/*      */     } 
/*  567 */     if (str != null) {
/*  568 */       return (Icon)DefaultLookup.get(this.optionPane, this, str);
/*      */     }
/*  570 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getMaxCharactersPerLineCount() {
/*  577 */     return this.optionPane.getMaxCharactersPerLineCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void burstStringInto(Container paramContainer, String paramString, int paramInt) {
/*  586 */     int i = paramString.length();
/*  587 */     if (i <= 0)
/*      */       return; 
/*  589 */     if (i > paramInt) {
/*  590 */       int j = paramString.lastIndexOf(' ', paramInt);
/*  591 */       if (j <= 0)
/*  592 */         j = paramString.indexOf(' ', paramInt); 
/*  593 */       if (j > 0 && j < i) {
/*  594 */         burstStringInto(paramContainer, paramString.substring(0, j), paramInt);
/*  595 */         burstStringInto(paramContainer, paramString.substring(j + 1), paramInt);
/*      */         return;
/*      */       } 
/*      */     } 
/*  599 */     JLabel jLabel = new JLabel(paramString, 2);
/*  600 */     jLabel.setName("OptionPane.label");
/*  601 */     configureMessageLabel(jLabel);
/*  602 */     paramContainer.add(jLabel);
/*      */   }
/*      */   
/*      */   protected Container createSeparator() {
/*  606 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Container createButtonArea() {
/*  614 */     JPanel jPanel = new JPanel();
/*  615 */     Border border = (Border)DefaultLookup.get(this.optionPane, this, "OptionPane.buttonAreaBorder");
/*      */     
/*  617 */     jPanel.setName("OptionPane.buttonArea");
/*  618 */     if (border != null) {
/*  619 */       jPanel.setBorder(border);
/*      */     }
/*  621 */     jPanel.setLayout(new ButtonAreaLayout(
/*  622 */           DefaultLookup.getBoolean(this.optionPane, this, "OptionPane.sameSizeButtons", true), 
/*      */           
/*  624 */           DefaultLookup.getInt(this.optionPane, this, "OptionPane.buttonPadding", 6), 
/*      */           
/*  626 */           DefaultLookup.getInt(this.optionPane, this, "OptionPane.buttonOrientation", 0), 
/*      */           
/*  628 */           DefaultLookup.getBoolean(this.optionPane, this, "OptionPane.isYesLast", false)));
/*      */     
/*  630 */     addButtonComponents(jPanel, getButtons(), getInitialValueIndex());
/*  631 */     return jPanel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addButtonComponents(Container paramContainer, Object[] paramArrayOfObject, int paramInt) {
/*  643 */     if (paramArrayOfObject != null && paramArrayOfObject.length > 0) {
/*  644 */       boolean bool = getSizeButtonsToSameWidth();
/*  645 */       boolean bool1 = true;
/*  646 */       int i = paramArrayOfObject.length;
/*  647 */       JButton[] arrayOfJButton = null;
/*  648 */       int j = 0;
/*      */       
/*  650 */       if (bool) {
/*  651 */         arrayOfJButton = new JButton[i];
/*      */       }
/*      */       
/*  654 */       for (byte b = 0; b < i; b++) {
/*  655 */         Component component; Object object = paramArrayOfObject[b];
/*      */ 
/*      */         
/*  658 */         if (object instanceof Component) {
/*  659 */           bool1 = false;
/*  660 */           component = (Component)object;
/*  661 */           paramContainer.add(component);
/*  662 */           this.hasCustomComponents = true;
/*      */         } else {
/*      */           JButton jButton;
/*      */ 
/*      */           
/*  667 */           if (object instanceof ButtonFactory) {
/*  668 */             jButton = ((ButtonFactory)object).createButton();
/*      */           }
/*  670 */           else if (object instanceof Icon) {
/*  671 */             jButton = new JButton((Icon)object);
/*      */           } else {
/*  673 */             jButton = new JButton(object.toString());
/*      */           } 
/*  675 */           jButton.setName("OptionPane.button");
/*  676 */           jButton.setMultiClickThreshhold(DefaultLookup.getInt(this.optionPane, this, "OptionPane.buttonClickThreshhold", 0));
/*      */ 
/*      */           
/*  679 */           configureButton(jButton);
/*      */           
/*  681 */           paramContainer.add(jButton);
/*      */           
/*  683 */           ActionListener actionListener = createButtonActionListener(b);
/*  684 */           if (actionListener != null) {
/*  685 */             jButton.addActionListener(actionListener);
/*      */           }
/*  687 */           component = jButton;
/*      */         } 
/*  689 */         if (bool && bool1 && component instanceof JButton) {
/*      */           
/*  691 */           arrayOfJButton[b] = (JButton)component;
/*  692 */           j = Math.max(j, 
/*  693 */               (component.getMinimumSize()).width);
/*      */         } 
/*  695 */         if (b == paramInt) {
/*  696 */           this.initialFocusComponent = component;
/*  697 */           if (this.initialFocusComponent instanceof JButton) {
/*  698 */             JButton jButton = (JButton)this.initialFocusComponent;
/*  699 */             jButton.addHierarchyListener(new HierarchyListener() {
/*      */                   public void hierarchyChanged(HierarchyEvent param1HierarchyEvent) {
/*  701 */                     if ((param1HierarchyEvent.getChangeFlags() & 0x1L) != 0L) {
/*      */                       
/*  703 */                       JButton jButton = (JButton)param1HierarchyEvent.getComponent();
/*      */                       
/*  705 */                       JRootPane jRootPane = SwingUtilities.getRootPane(jButton);
/*  706 */                       if (jRootPane != null) {
/*  707 */                         jRootPane.setDefaultButton(jButton);
/*      */                       }
/*      */                     } 
/*      */                   }
/*      */                 });
/*      */           } 
/*      */         } 
/*      */       } 
/*  715 */       ((ButtonAreaLayout)paramContainer.getLayout())
/*  716 */         .setSyncAllWidths((bool && bool1));
/*      */ 
/*      */ 
/*      */       
/*  720 */       if (DefaultLookup.getBoolean(this.optionPane, this, "OptionPane.setButtonMargin", true) && bool && bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  726 */         byte b1 = (i <= 2) ? 8 : 4;
/*      */         
/*  728 */         for (byte b2 = 0; b2 < i; b2++) {
/*  729 */           JButton jButton = arrayOfJButton[b2];
/*  730 */           jButton.setMargin(new Insets(2, b1, 2, b1));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected ActionListener createButtonActionListener(int paramInt) {
/*  737 */     return new ButtonActionListener(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object[] getButtons() {
/*  749 */     if (this.optionPane != null) {
/*  750 */       Object[] arrayOfObject = this.optionPane.getOptions();
/*      */       
/*  752 */       if (arrayOfObject == null) {
/*      */         ButtonFactory[] arrayOfButtonFactory;
/*  754 */         int i = this.optionPane.getOptionType();
/*  755 */         Locale locale = this.optionPane.getLocale();
/*      */         
/*  757 */         int j = DefaultLookup.getInt(this.optionPane, this, "OptionPane.buttonMinimumWidth", -1);
/*      */         
/*  759 */         if (i == 0) {
/*  760 */           arrayOfButtonFactory = new ButtonFactory[2];
/*  761 */           arrayOfButtonFactory[0] = new ButtonFactory(
/*  762 */               UIManager.getString("OptionPane.yesButtonText", locale), 
/*  763 */               getMnemonic("OptionPane.yesButtonMnemonic", locale), 
/*  764 */               (Icon)DefaultLookup.get(this.optionPane, this, "OptionPane.yesIcon"), j);
/*      */           
/*  766 */           arrayOfButtonFactory[1] = new ButtonFactory(
/*  767 */               UIManager.getString("OptionPane.noButtonText", locale), 
/*  768 */               getMnemonic("OptionPane.noButtonMnemonic", locale), 
/*  769 */               (Icon)DefaultLookup.get(this.optionPane, this, "OptionPane.noIcon"), j);
/*      */         }
/*  771 */         else if (i == 1) {
/*  772 */           arrayOfButtonFactory = new ButtonFactory[3];
/*  773 */           arrayOfButtonFactory[0] = new ButtonFactory(
/*  774 */               UIManager.getString("OptionPane.yesButtonText", locale), 
/*  775 */               getMnemonic("OptionPane.yesButtonMnemonic", locale), 
/*  776 */               (Icon)DefaultLookup.get(this.optionPane, this, "OptionPane.yesIcon"), j);
/*      */           
/*  778 */           arrayOfButtonFactory[1] = new ButtonFactory(
/*  779 */               UIManager.getString("OptionPane.noButtonText", locale), 
/*  780 */               getMnemonic("OptionPane.noButtonMnemonic", locale), 
/*  781 */               (Icon)DefaultLookup.get(this.optionPane, this, "OptionPane.noIcon"), j);
/*      */           
/*  783 */           arrayOfButtonFactory[2] = new ButtonFactory(
/*  784 */               UIManager.getString("OptionPane.cancelButtonText", locale), 
/*  785 */               getMnemonic("OptionPane.cancelButtonMnemonic", locale), 
/*  786 */               (Icon)DefaultLookup.get(this.optionPane, this, "OptionPane.cancelIcon"), j);
/*      */         }
/*  788 */         else if (i == 2) {
/*  789 */           arrayOfButtonFactory = new ButtonFactory[2];
/*  790 */           arrayOfButtonFactory[0] = new ButtonFactory(
/*  791 */               UIManager.getString("OptionPane.okButtonText", locale), 
/*  792 */               getMnemonic("OptionPane.okButtonMnemonic", locale), 
/*  793 */               (Icon)DefaultLookup.get(this.optionPane, this, "OptionPane.okIcon"), j);
/*      */           
/*  795 */           arrayOfButtonFactory[1] = new ButtonFactory(
/*  796 */               UIManager.getString("OptionPane.cancelButtonText", locale), 
/*  797 */               getMnemonic("OptionPane.cancelButtonMnemonic", locale), 
/*  798 */               (Icon)DefaultLookup.get(this.optionPane, this, "OptionPane.cancelIcon"), j);
/*      */         } else {
/*      */           
/*  801 */           arrayOfButtonFactory = new ButtonFactory[1];
/*  802 */           arrayOfButtonFactory[0] = new ButtonFactory(
/*  803 */               UIManager.getString("OptionPane.okButtonText", locale), 
/*  804 */               getMnemonic("OptionPane.okButtonMnemonic", locale), 
/*  805 */               (Icon)DefaultLookup.get(this.optionPane, this, "OptionPane.okIcon"), j);
/*      */         } 
/*      */         
/*  808 */         return (Object[])arrayOfButtonFactory;
/*      */       } 
/*      */       
/*  811 */       return arrayOfObject;
/*      */     } 
/*  813 */     return null;
/*      */   }
/*      */   
/*      */   private int getMnemonic(String paramString, Locale paramLocale) {
/*  817 */     String str = (String)UIManager.get(paramString, paramLocale);
/*      */     
/*  819 */     if (str == null) {
/*  820 */       return 0;
/*      */     }
/*      */     try {
/*  823 */       return Integer.parseInt(str);
/*      */     }
/*  825 */     catch (NumberFormatException numberFormatException) {
/*  826 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getSizeButtonsToSameWidth() {
/*  834 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getInitialValueIndex() {
/*  843 */     if (this.optionPane != null) {
/*  844 */       Object object = this.optionPane.getInitialValue();
/*  845 */       Object[] arrayOfObject = this.optionPane.getOptions();
/*      */       
/*  847 */       if (arrayOfObject == null) {
/*  848 */         return 0;
/*      */       }
/*  850 */       if (object != null)
/*  851 */         for (int i = arrayOfObject.length - 1; i >= 0; i--) {
/*  852 */           if (arrayOfObject[i].equals(object)) {
/*  853 */             return i;
/*      */           }
/*      */         }  
/*      */     } 
/*  857 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetInputValue() {
/*  865 */     if (this.inputComponent != null && this.inputComponent instanceof JTextField) {
/*  866 */       this.optionPane.setInputValue(((JTextField)this.inputComponent).getText());
/*      */     }
/*  868 */     else if (this.inputComponent != null && this.inputComponent instanceof JComboBox) {
/*      */       
/*  870 */       this.optionPane.setInputValue(((JComboBox)this.inputComponent)
/*  871 */           .getSelectedItem());
/*  872 */     } else if (this.inputComponent != null) {
/*  873 */       this.optionPane.setInputValue(((JList)this.inputComponent)
/*  874 */           .getSelectedValue());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectInitialValue(JOptionPane paramJOptionPane) {
/*  884 */     if (this.inputComponent != null) {
/*  885 */       this.inputComponent.requestFocus();
/*      */     } else {
/*  887 */       if (this.initialFocusComponent != null) {
/*  888 */         this.initialFocusComponent.requestFocus();
/*      */       }
/*  890 */       if (this.initialFocusComponent instanceof JButton) {
/*  891 */         JRootPane jRootPane = SwingUtilities.getRootPane(this.initialFocusComponent);
/*  892 */         if (jRootPane != null) {
/*  893 */           jRootPane.setDefaultButton((JButton)this.initialFocusComponent);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsCustomComponents(JOptionPane paramJOptionPane) {
/*  904 */     return this.hasCustomComponents;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ButtonAreaLayout
/*      */     implements LayoutManager
/*      */   {
/*      */     protected boolean syncAllWidths;
/*      */ 
/*      */     
/*      */     protected int padding;
/*      */ 
/*      */     
/*      */     protected boolean centersChildren;
/*      */ 
/*      */     
/*      */     private int orientation;
/*      */ 
/*      */     
/*      */     private boolean reverseButtons;
/*      */ 
/*      */     
/*      */     private boolean useOrientation;
/*      */ 
/*      */ 
/*      */     
/*      */     public ButtonAreaLayout(boolean param1Boolean, int param1Int) {
/*  932 */       this.syncAllWidths = param1Boolean;
/*  933 */       this.padding = param1Int;
/*  934 */       this.centersChildren = true;
/*  935 */       this.useOrientation = false;
/*      */     }
/*      */ 
/*      */     
/*      */     ButtonAreaLayout(boolean param1Boolean1, int param1Int1, int param1Int2, boolean param1Boolean2) {
/*  940 */       this(param1Boolean1, param1Int1);
/*  941 */       this.useOrientation = true;
/*  942 */       this.orientation = param1Int2;
/*  943 */       this.reverseButtons = param1Boolean2;
/*      */     }
/*      */     
/*      */     public void setSyncAllWidths(boolean param1Boolean) {
/*  947 */       this.syncAllWidths = param1Boolean;
/*      */     }
/*      */     
/*      */     public boolean getSyncAllWidths() {
/*  951 */       return this.syncAllWidths;
/*      */     }
/*      */     
/*      */     public void setPadding(int param1Int) {
/*  955 */       this.padding = param1Int;
/*      */     }
/*      */     
/*      */     public int getPadding() {
/*  959 */       return this.padding;
/*      */     }
/*      */     
/*      */     public void setCentersChildren(boolean param1Boolean) {
/*  963 */       this.centersChildren = param1Boolean;
/*  964 */       this.useOrientation = false;
/*      */     }
/*      */     
/*      */     public boolean getCentersChildren() {
/*  968 */       return this.centersChildren;
/*      */     }
/*      */     
/*      */     private int getOrientation(Container param1Container) {
/*  972 */       if (!this.useOrientation) {
/*  973 */         return 0;
/*      */       }
/*  975 */       if (param1Container.getComponentOrientation().isLeftToRight()) {
/*  976 */         return this.orientation;
/*      */       }
/*  978 */       switch (this.orientation) {
/*      */         case 2:
/*  980 */           return 4;
/*      */         case 4:
/*  982 */           return 2;
/*      */         case 0:
/*  984 */           return 0;
/*      */       } 
/*  986 */       return 2;
/*      */     }
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/*  993 */       Component[] arrayOfComponent = param1Container.getComponents();
/*      */       
/*  995 */       if (arrayOfComponent != null && arrayOfComponent.length > 0) {
/*  996 */         int i = arrayOfComponent.length;
/*  997 */         Insets insets = param1Container.getInsets();
/*  998 */         int j = 0;
/*  999 */         int k = 0;
/* 1000 */         int m = 0;
/* 1001 */         int n = 0;
/* 1002 */         int i1 = 0;
/*      */         
/* 1004 */         boolean bool = param1Container.getComponentOrientation().isLeftToRight();
/* 1005 */         boolean bool1 = bool ? this.reverseButtons : (!this.reverseButtons ? true : false);
/*      */         byte b;
/* 1007 */         for (b = 0; b < i; b++) {
/* 1008 */           Dimension dimension = arrayOfComponent[b].getPreferredSize();
/* 1009 */           j = Math.max(j, dimension.width);
/* 1010 */           k = Math.max(k, dimension.height);
/* 1011 */           m += dimension.width;
/*      */         } 
/* 1013 */         if (getSyncAllWidths()) {
/* 1014 */           m = j * i;
/*      */         }
/* 1016 */         m += (i - 1) * this.padding;
/*      */         
/* 1018 */         switch (getOrientation(param1Container)) {
/*      */           case 2:
/* 1020 */             n = insets.left;
/*      */             break;
/*      */           case 4:
/* 1023 */             n = param1Container.getWidth() - insets.right - m;
/*      */             break;
/*      */           case 0:
/* 1026 */             if (getCentersChildren() || i < 2) {
/* 1027 */               n = (param1Container.getWidth() - m) / 2;
/*      */               break;
/*      */             } 
/* 1030 */             n = insets.left;
/* 1031 */             if (getSyncAllWidths()) {
/* 1032 */               i1 = (param1Container.getWidth() - insets.left - insets.right - m) / (i - 1) + j;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/* 1037 */             i1 = (param1Container.getWidth() - insets.left - insets.right - m) / (i - 1);
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1045 */         for (b = 0; b < i; b++) {
/* 1046 */           boolean bool2 = bool1 ? (i - b - 1) : b;
/*      */           
/* 1048 */           Dimension dimension = arrayOfComponent[bool2].getPreferredSize();
/*      */           
/* 1050 */           if (getSyncAllWidths()) {
/* 1051 */             arrayOfComponent[bool2].setBounds(n, insets.top, j, k);
/*      */           }
/*      */           else {
/*      */             
/* 1055 */             arrayOfComponent[bool2].setBounds(n, insets.top, dimension.width, dimension.height);
/*      */           } 
/*      */           
/* 1058 */           if (i1 != 0) {
/* 1059 */             n += i1;
/*      */           } else {
/*      */             
/* 1062 */             n += arrayOfComponent[bool2].getWidth() + this.padding;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/* 1069 */       if (param1Container != null) {
/* 1070 */         Component[] arrayOfComponent = param1Container.getComponents();
/*      */         
/* 1072 */         if (arrayOfComponent != null && arrayOfComponent.length > 0) {
/*      */           
/* 1074 */           int i = arrayOfComponent.length;
/* 1075 */           int j = 0;
/* 1076 */           Insets insets = param1Container.getInsets();
/* 1077 */           int k = insets.top + insets.bottom;
/* 1078 */           int m = insets.left + insets.right;
/*      */           
/* 1080 */           if (this.syncAllWidths) {
/* 1081 */             int i1 = 0;
/*      */             
/* 1083 */             for (byte b1 = 0; b1 < i; b1++) {
/* 1084 */               Dimension dimension = arrayOfComponent[b1].getPreferredSize();
/* 1085 */               j = Math.max(j, dimension.height);
/* 1086 */               i1 = Math.max(i1, dimension.width);
/*      */             } 
/* 1088 */             return new Dimension(m + i1 * i + (i - 1) * this.padding, k + j);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1093 */           int n = 0;
/*      */           
/* 1095 */           for (byte b = 0; b < i; b++) {
/* 1096 */             Dimension dimension = arrayOfComponent[b].getPreferredSize();
/* 1097 */             j = Math.max(j, dimension.height);
/* 1098 */             n += dimension.width;
/*      */           } 
/* 1100 */           n += (i - 1) * this.padding;
/* 1101 */           return new Dimension(m + n, k + j);
/*      */         } 
/*      */       } 
/*      */       
/* 1105 */       return new Dimension(0, 0);
/*      */     }
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/* 1109 */       return minimumLayoutSize(param1Container);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1128 */       BasicOptionPaneUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void configureMessageLabel(JLabel paramJLabel) {
/* 1137 */     Color color = (Color)DefaultLookup.get(this.optionPane, this, "OptionPane.messageForeground");
/*      */     
/* 1139 */     if (color != null) {
/* 1140 */       paramJLabel.setForeground(color);
/*      */     }
/* 1142 */     Font font = (Font)DefaultLookup.get(this.optionPane, this, "OptionPane.messageFont");
/*      */     
/* 1144 */     if (font != null) {
/* 1145 */       paramJLabel.setFont(font);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void configureButton(JButton paramJButton) {
/* 1154 */     Font font = (Font)DefaultLookup.get(this.optionPane, this, "OptionPane.buttonFont");
/*      */     
/* 1156 */     if (font != null) {
/* 1157 */       paramJButton.setFont(font);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class ButtonActionListener
/*      */     implements ActionListener
/*      */   {
/*      */     protected int buttonIndex;
/*      */ 
/*      */     
/*      */     public ButtonActionListener(int param1Int) {
/* 1169 */       this.buttonIndex = param1Int;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1173 */       if (BasicOptionPaneUI.this.optionPane != null) {
/* 1174 */         int i = BasicOptionPaneUI.this.optionPane.getOptionType();
/* 1175 */         Object[] arrayOfObject = BasicOptionPaneUI.this.optionPane.getOptions();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1182 */         if (BasicOptionPaneUI.this.inputComponent != null && (
/* 1183 */           arrayOfObject != null || i == -1 || ((i == 0 || i == 1 || i == 2) && this.buttonIndex == 0)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1189 */           BasicOptionPaneUI.this.resetInputValue();
/*      */         }
/*      */         
/* 1192 */         if (arrayOfObject == null) {
/* 1193 */           if (i == 2 && this.buttonIndex == 1) {
/*      */             
/* 1195 */             BasicOptionPaneUI.this.optionPane.setValue(Integer.valueOf(2));
/*      */           } else {
/*      */             
/* 1198 */             BasicOptionPaneUI.this.optionPane.setValue(Integer.valueOf(this.buttonIndex));
/*      */           } 
/*      */         } else {
/* 1201 */           BasicOptionPaneUI.this.optionPane.setValue(arrayOfObject[this.buttonIndex]);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements ActionListener, MouseListener, PropertyChangeListener
/*      */   {
/*      */     private Handler() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1214 */       BasicOptionPaneUI.this.optionPane.setInputValue(((JTextField)param1ActionEvent.getSource()).getText());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 1234 */       if (param1MouseEvent.getClickCount() == 2) {
/* 1235 */         JList jList = (JList)param1MouseEvent.getSource();
/* 1236 */         int i = jList.locationToIndex(param1MouseEvent.getPoint());
/*      */         
/* 1238 */         BasicOptionPaneUI.this.optionPane.setInputValue(jList.getModel().getElementAt(i));
/* 1239 */         BasicOptionPaneUI.this.optionPane.setValue(Integer.valueOf(0));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1247 */       if (param1PropertyChangeEvent.getSource() == BasicOptionPaneUI.this.optionPane) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1252 */         if ("ancestor" == param1PropertyChangeEvent.getPropertyName()) {
/* 1253 */           boolean bool; JOptionPane jOptionPane = (JOptionPane)param1PropertyChangeEvent.getSource();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1258 */           if (param1PropertyChangeEvent.getOldValue() == null) {
/* 1259 */             bool = true;
/*      */           } else {
/* 1261 */             bool = false;
/*      */           } 
/*      */ 
/*      */           
/* 1265 */           switch (jOptionPane.getMessageType()) {
/*      */             case -1:
/* 1267 */               if (bool) {
/* 1268 */                 BasicLookAndFeel.playSound(BasicOptionPaneUI.this.optionPane, "OptionPane.informationSound");
/*      */               }
/*      */               break;
/*      */             
/*      */             case 3:
/* 1273 */               if (bool) {
/* 1274 */                 BasicLookAndFeel.playSound(BasicOptionPaneUI.this.optionPane, "OptionPane.questionSound");
/*      */               }
/*      */               break;
/*      */             
/*      */             case 1:
/* 1279 */               if (bool) {
/* 1280 */                 BasicLookAndFeel.playSound(BasicOptionPaneUI.this.optionPane, "OptionPane.informationSound");
/*      */               }
/*      */               break;
/*      */             
/*      */             case 2:
/* 1285 */               if (bool) {
/* 1286 */                 BasicLookAndFeel.playSound(BasicOptionPaneUI.this.optionPane, "OptionPane.warningSound");
/*      */               }
/*      */               break;
/*      */             
/*      */             case 0:
/* 1291 */               if (bool) {
/* 1292 */                 BasicLookAndFeel.playSound(BasicOptionPaneUI.this.optionPane, "OptionPane.errorSound");
/*      */               }
/*      */               break;
/*      */             
/*      */             default:
/* 1297 */               System.err.println("Undefined JOptionPane type: " + jOptionPane
/* 1298 */                   .getMessageType());
/*      */               break;
/*      */           } 
/*      */         
/*      */         } 
/* 1303 */         String str = param1PropertyChangeEvent.getPropertyName();
/*      */         
/* 1305 */         if (str == "options" || str == "initialValue" || str == "icon" || str == "messageType" || str == "optionType" || str == "message" || str == "selectionValues" || str == "initialSelectionValue" || str == "wantsInput") {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1314 */           BasicOptionPaneUI.this.uninstallComponents();
/* 1315 */           BasicOptionPaneUI.this.installComponents();
/* 1316 */           BasicOptionPaneUI.this.optionPane.validate();
/*      */         }
/* 1318 */         else if (str == "componentOrientation") {
/* 1319 */           ComponentOrientation componentOrientation = (ComponentOrientation)param1PropertyChangeEvent.getNewValue();
/* 1320 */           JOptionPane jOptionPane = (JOptionPane)param1PropertyChangeEvent.getSource();
/* 1321 */           if (componentOrientation != param1PropertyChangeEvent.getOldValue()) {
/* 1322 */             jOptionPane.applyComponentOrientation(componentOrientation);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class MultiplexingTextField
/*      */     extends JTextField
/*      */   {
/*      */     private KeyStroke[] strokes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MultiplexingTextField(int param1Int) {
/* 1346 */       super(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setKeyStrokes(KeyStroke[] param1ArrayOfKeyStroke) {
/* 1354 */       this.strokes = param1ArrayOfKeyStroke;
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean processKeyBinding(KeyStroke param1KeyStroke, KeyEvent param1KeyEvent, int param1Int, boolean param1Boolean) {
/* 1359 */       boolean bool = super.processKeyBinding(param1KeyStroke, param1KeyEvent, param1Int, param1Boolean);
/*      */ 
/*      */       
/* 1362 */       if (bool && param1Int != 2) {
/* 1363 */         for (int i = this.strokes.length - 1; i >= 0; 
/* 1364 */           i--) {
/* 1365 */           if (this.strokes[i].equals(param1KeyStroke))
/*      */           {
/*      */ 
/*      */             
/* 1369 */             return false;
/*      */           }
/*      */         } 
/*      */       }
/* 1373 */       return bool;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction
/*      */   {
/*      */     private static final String CLOSE = "close";
/*      */ 
/*      */ 
/*      */     
/*      */     Actions(String param1String) {
/* 1387 */       super(param1String);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1391 */       if (getName() == "close") {
/* 1392 */         JOptionPane jOptionPane = (JOptionPane)param1ActionEvent.getSource();
/*      */         
/* 1394 */         jOptionPane.setValue(Integer.valueOf(-1));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ButtonFactory
/*      */   {
/*      */     private String text;
/*      */     
/*      */     private int mnemonic;
/*      */     
/*      */     private Icon icon;
/*      */     
/* 1409 */     private int minimumWidth = -1;
/*      */     
/*      */     ButtonFactory(String param1String, int param1Int1, Icon param1Icon, int param1Int2) {
/* 1412 */       this.text = param1String;
/* 1413 */       this.mnemonic = param1Int1;
/* 1414 */       this.icon = param1Icon;
/* 1415 */       this.minimumWidth = param1Int2;
/*      */     }
/*      */ 
/*      */     
/*      */     JButton createButton() {
/*      */       JButton jButton;
/* 1421 */       if (this.minimumWidth > 0) {
/* 1422 */         jButton = new ConstrainedButton(this.text, this.minimumWidth);
/*      */       } else {
/* 1424 */         jButton = new JButton(this.text);
/*      */       } 
/* 1426 */       if (this.icon != null) {
/* 1427 */         jButton.setIcon(this.icon);
/*      */       }
/* 1429 */       if (this.mnemonic != 0) {
/* 1430 */         jButton.setMnemonic(this.mnemonic);
/*      */       }
/* 1432 */       return jButton;
/*      */     }
/*      */     
/*      */     private static class ConstrainedButton extends JButton {
/*      */       int minimumWidth;
/*      */       
/*      */       ConstrainedButton(String param2String, int param2Int) {
/* 1439 */         super(param2String);
/* 1440 */         this.minimumWidth = param2Int;
/*      */       }
/*      */       
/*      */       public Dimension getMinimumSize() {
/* 1444 */         Dimension dimension = super.getMinimumSize();
/* 1445 */         dimension.width = Math.max(dimension.width, this.minimumWidth);
/* 1446 */         return dimension;
/*      */       }
/*      */       
/*      */       public Dimension getPreferredSize() {
/* 1450 */         Dimension dimension = super.getPreferredSize();
/* 1451 */         dimension.width = Math.max(dimension.width, this.minimumWidth);
/* 1452 */         return dimension;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicOptionPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */