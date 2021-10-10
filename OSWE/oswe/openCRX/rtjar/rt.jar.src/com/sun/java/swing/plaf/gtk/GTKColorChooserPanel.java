/*      */ package com.sun.java.swing.plaf.gtk;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.image.BufferedImage;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JColorChooser;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFormattedTextField;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JSeparator;
/*      */ import javax.swing.JSpinner;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.SpinnerNumberModel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.colorchooser.AbstractColorChooserPanel;
/*      */ import javax.swing.colorchooser.ColorSelectionModel;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class GTKColorChooserPanel
/*      */   extends AbstractColorChooserPanel
/*      */   implements ChangeListener
/*      */ {
/*      */   private static final float PI_3 = 1.0471976F;
/*      */   private ColorTriangle triangle;
/*      */   private JLabel lastLabel;
/*      */   private JLabel label;
/*      */   private JSpinner hueSpinner;
/*      */   private JSpinner saturationSpinner;
/*      */   private JSpinner valueSpinner;
/*      */   private JSpinner redSpinner;
/*      */   private JSpinner greenSpinner;
/*      */   private JSpinner blueSpinner;
/*      */   private JTextField colorNameTF;
/*      */   private boolean settingColor;
/*      */   private float hue;
/*      */   private float saturation;
/*      */   private float brightness;
/*      */   private static final int FLAGS_CHANGED_ANGLE = 1;
/*      */   private static final int FLAGS_DRAGGING = 2;
/*      */   private static final int FLAGS_DRAGGING_TRIANGLE = 4;
/*      */   private static final int FLAGS_SETTING_COLOR = 8;
/*      */   private static final int FLAGS_FOCUSED_WHEEL = 16;
/*      */   private static final int FLAGS_FOCUSED_TRIANGLE = 32;
/*      */   
/*      */   static void compositeRequestFocus(Component paramComponent, boolean paramBoolean) {
/*   74 */     if (paramComponent instanceof Container) {
/*   75 */       Container container1 = (Container)paramComponent;
/*   76 */       if (container1.isFocusCycleRoot()) {
/*      */         
/*   78 */         FocusTraversalPolicy focusTraversalPolicy = container1.getFocusTraversalPolicy();
/*   79 */         Component component = focusTraversalPolicy.getDefaultComponent(container1);
/*   80 */         if (component != null) {
/*   81 */           component.requestFocus();
/*      */           return;
/*      */         } 
/*      */       } 
/*   85 */       Container container2 = container1.getFocusCycleRootAncestor();
/*   86 */       if (container2 != null) {
/*      */         Component component;
/*   88 */         FocusTraversalPolicy focusTraversalPolicy = container2.getFocusTraversalPolicy();
/*      */ 
/*      */         
/*   91 */         if (paramBoolean) {
/*   92 */           component = focusTraversalPolicy.getComponentAfter(container2, container1);
/*      */         } else {
/*      */           
/*   95 */           component = focusTraversalPolicy.getComponentBefore(container2, container1);
/*      */         } 
/*   97 */         if (component != null) {
/*   98 */           component.requestFocus();
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  103 */     paramComponent.requestFocus();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDisplayName() {
/*  111 */     return (String)UIManager.get("GTKColorChooserPanel.nameText");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMnemonic() {
/*  118 */     String str = (String)UIManager.get("GTKColorChooserPanel.mnemonic");
/*      */     
/*  120 */     if (str != null) {
/*      */       try {
/*  122 */         return Integer.parseInt(str);
/*      */       
/*      */       }
/*  125 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*  127 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDisplayedMnemonicIndex() {
/*  134 */     String str = (String)UIManager.get("GTKColorChooserPanel.displayedMnemonicIndex");
/*      */ 
/*      */     
/*  137 */     if (str != null) {
/*      */       try {
/*  139 */         return Integer.parseInt(str);
/*      */       
/*      */       }
/*  142 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*  144 */     return -1;
/*      */   }
/*      */   
/*      */   public Icon getSmallDisplayIcon() {
/*  148 */     return null;
/*      */   }
/*      */   
/*      */   public Icon getLargeDisplayIcon() {
/*  152 */     return null;
/*      */   }
/*      */   
/*      */   public void uninstallChooserPanel(JColorChooser paramJColorChooser) {
/*  156 */     super.uninstallChooserPanel(paramJColorChooser);
/*  157 */     removeAll();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void buildChooser() {
/*  164 */     this.triangle = new ColorTriangle();
/*  165 */     this.triangle.setName("GTKColorChooserPanel.triangle");
/*      */ 
/*      */ 
/*      */     
/*  169 */     this.label = new OpaqueLabel();
/*  170 */     this.label.setName("GTKColorChooserPanel.colorWell");
/*  171 */     this.label.setOpaque(true);
/*  172 */     this.label.setMinimumSize(new Dimension(67, 32));
/*  173 */     this.label.setPreferredSize(new Dimension(67, 32));
/*  174 */     this.label.setMaximumSize(new Dimension(67, 32));
/*      */ 
/*      */ 
/*      */     
/*  178 */     this.lastLabel = new OpaqueLabel();
/*  179 */     this.lastLabel.setName("GTKColorChooserPanel.lastColorWell");
/*  180 */     this.lastLabel.setOpaque(true);
/*  181 */     this.lastLabel.setMinimumSize(new Dimension(67, 32));
/*  182 */     this.lastLabel.setPreferredSize(new Dimension(67, 32));
/*  183 */     this.lastLabel.setMaximumSize(new Dimension(67, 32));
/*      */     
/*  185 */     this.hueSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 360, 1));
/*  186 */     configureSpinner(this.hueSpinner, "GTKColorChooserPanel.hueSpinner");
/*  187 */     this.saturationSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
/*  188 */     configureSpinner(this.saturationSpinner, "GTKColorChooserPanel.saturationSpinner");
/*      */     
/*  190 */     this.valueSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
/*  191 */     configureSpinner(this.valueSpinner, "GTKColorChooserPanel.valueSpinner");
/*  192 */     this.redSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
/*  193 */     configureSpinner(this.redSpinner, "GTKColorChooserPanel.redSpinner");
/*  194 */     this.greenSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
/*  195 */     configureSpinner(this.greenSpinner, "GTKColorChooserPanel.greenSpinner");
/*  196 */     this.blueSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
/*  197 */     configureSpinner(this.blueSpinner, "GTKColorChooserPanel.blueSpinner");
/*      */     
/*  199 */     this.colorNameTF = new JTextField(8);
/*      */     
/*  201 */     setLayout(new GridBagLayout());
/*      */     
/*  203 */     add(this, "GTKColorChooserPanel.hue", this.hueSpinner, -1, -1);
/*  204 */     add(this, "GTKColorChooserPanel.red", this.redSpinner, -1, -1);
/*  205 */     add(this, "GTKColorChooserPanel.saturation", this.saturationSpinner, -1, -1);
/*  206 */     add(this, "GTKColorChooserPanel.green", this.greenSpinner, -1, -1);
/*  207 */     add(this, "GTKColorChooserPanel.value", this.valueSpinner, -1, -1);
/*  208 */     add(this, "GTKColorChooserPanel.blue", this.blueSpinner, -1, -1);
/*      */     
/*  210 */     add(new JSeparator(0), new GridBagConstraints(1, 3, 4, 1, 1.0D, 0.0D, 21, 2, new Insets(14, 0, 0, 0), 0, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  215 */     add(this, "GTKColorChooserPanel.colorName", this.colorNameTF, 0, 4);
/*      */     
/*  217 */     add(this.triangle, new GridBagConstraints(0, 0, 1, 5, 0.0D, 0.0D, 21, 0, new Insets(14, 20, 2, 9), 0, 0));
/*      */ 
/*      */ 
/*      */     
/*  221 */     Box box = Box.createHorizontalBox();
/*  222 */     box.add(this.lastLabel);
/*  223 */     box.add(this.label);
/*  224 */     add(box, new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/*      */ 
/*      */ 
/*      */     
/*  228 */     add(new JSeparator(0), new GridBagConstraints(0, 6, 5, 1, 1.0D, 0.0D, 21, 2, new Insets(12, 0, 0, 0), 0, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void configureSpinner(JSpinner paramJSpinner, String paramString) {
/*  238 */     paramJSpinner.addChangeListener(this);
/*  239 */     paramJSpinner.setName(paramString);
/*  240 */     JComponent jComponent = paramJSpinner.getEditor();
/*  241 */     if (jComponent instanceof JSpinner.DefaultEditor) {
/*      */       
/*  243 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)jComponent).getTextField();
/*      */       
/*  245 */       jFormattedTextField.setFocusLostBehavior(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void add(Container paramContainer, String paramString, JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  254 */     JLabel jLabel = new JLabel(UIManager.getString(paramString + "Text", 
/*  255 */           getLocale()));
/*  256 */     String str = (String)UIManager.get(paramString + "Mnemonic", getLocale());
/*      */     
/*  258 */     if (str != null) {
/*      */       try {
/*  260 */         jLabel.setDisplayedMnemonic(Integer.parseInt(str));
/*  261 */       } catch (NumberFormatException numberFormatException) {}
/*      */       
/*  263 */       String str1 = (String)UIManager.get(paramString + "MnemonicIndex", 
/*  264 */           getLocale());
/*      */       
/*  266 */       if (str1 != null) {
/*      */         try {
/*  268 */           jLabel.setDisplayedMnemonicIndex(Integer.parseInt(str1));
/*      */         }
/*  270 */         catch (NumberFormatException numberFormatException) {}
/*      */       }
/*      */     } 
/*      */     
/*  274 */     jLabel.setLabelFor(paramJComponent);
/*  275 */     if (paramInt1 < 0) {
/*  276 */       paramInt1 = paramContainer.getComponentCount() % 4;
/*      */     }
/*  278 */     if (paramInt2 < 0) {
/*  279 */       paramInt2 = paramContainer.getComponentCount() / 4;
/*      */     }
/*  281 */     GridBagConstraints gridBagConstraints = new GridBagConstraints(paramInt1 + 1, paramInt2, 1, 1, 0.0D, 0.0D, 24, 0, new Insets(4, 0, 0, 4), 0, 0);
/*      */ 
/*      */     
/*  284 */     if (paramInt2 == 0) {
/*  285 */       gridBagConstraints.insets.top = 14;
/*      */     }
/*  287 */     paramContainer.add(jLabel, gridBagConstraints);
/*  288 */     gridBagConstraints.gridx++;
/*  289 */     paramContainer.add(paramJComponent, gridBagConstraints);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateChooser() {
/*  296 */     if (!this.settingColor) {
/*  297 */       this.lastLabel.setBackground(getColorFromModel());
/*  298 */       setColor(getColorFromModel(), true, true, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setRed(int paramInt) {
/*  306 */     setRGB(paramInt << 16 | getColor().getGreen() << 8 | getColor().getBlue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setGreen(int paramInt) {
/*  313 */     setRGB(getColor().getRed() << 16 | paramInt << 8 | getColor().getBlue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setBlue(int paramInt) {
/*  320 */     setRGB(getColor().getRed() << 16 | getColor().getGreen() << 8 | paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHue(float paramFloat, boolean paramBoolean) {
/*  328 */     setHSB(paramFloat, this.saturation, this.brightness);
/*  329 */     if (paramBoolean) {
/*  330 */       this.settingColor = true;
/*  331 */       this.hueSpinner.setValue(Integer.valueOf((int)(paramFloat * 360.0F)));
/*  332 */       this.settingColor = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getHue() {
/*  340 */     return this.hue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSaturation(float paramFloat) {
/*  347 */     setHSB(this.hue, paramFloat, this.brightness);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getSaturation() {
/*  354 */     return this.saturation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setBrightness(float paramFloat) {
/*  361 */     setHSB(this.hue, this.saturation, paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getBrightness() {
/*  368 */     return this.brightness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSaturationAndBrightness(float paramFloat1, float paramFloat2, boolean paramBoolean) {
/*  376 */     setHSB(this.hue, paramFloat1, paramFloat2);
/*  377 */     if (paramBoolean) {
/*  378 */       this.settingColor = true;
/*  379 */       this.saturationSpinner.setValue(Integer.valueOf((int)(paramFloat1 * 255.0F)));
/*  380 */       this.valueSpinner.setValue(Integer.valueOf((int)(paramFloat2 * 255.0F)));
/*  381 */       this.settingColor = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setRGB(int paramInt) {
/*  389 */     Color color = new Color(paramInt);
/*      */     
/*  391 */     setColor(color, false, true, true);
/*      */     
/*  393 */     this.settingColor = true;
/*  394 */     this.hueSpinner.setValue(Integer.valueOf((int)(this.hue * 360.0F)));
/*  395 */     this.saturationSpinner.setValue(Integer.valueOf((int)(this.saturation * 255.0F)));
/*  396 */     this.valueSpinner.setValue(Integer.valueOf((int)(this.brightness * 255.0F)));
/*  397 */     this.settingColor = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHSB(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  404 */     Color color = Color.getHSBColor(paramFloat1, paramFloat2, paramFloat3);
/*      */     
/*  406 */     this.hue = paramFloat1;
/*  407 */     this.saturation = paramFloat2;
/*  408 */     this.brightness = paramFloat3;
/*  409 */     setColor(color, false, false, true);
/*      */     
/*  411 */     this.settingColor = true;
/*  412 */     this.redSpinner.setValue(Integer.valueOf(color.getRed()));
/*  413 */     this.greenSpinner.setValue(Integer.valueOf(color.getGreen()));
/*  414 */     this.blueSpinner.setValue(Integer.valueOf(color.getBlue()));
/*  415 */     this.settingColor = false;
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
/*      */   private void setColor(Color paramColor, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*  430 */     if (paramColor == null) {
/*  431 */       paramColor = Color.BLACK;
/*      */     }
/*      */     
/*  434 */     this.settingColor = true;
/*      */     
/*  436 */     if (paramBoolean2) {
/*  437 */       float[] arrayOfFloat = Color.RGBtoHSB(paramColor.getRed(), paramColor.getGreen(), paramColor
/*  438 */           .getBlue(), null);
/*  439 */       this.hue = arrayOfFloat[0];
/*  440 */       this.saturation = arrayOfFloat[1];
/*  441 */       this.brightness = arrayOfFloat[2];
/*      */     } 
/*      */     
/*  444 */     if (paramBoolean3) {
/*  445 */       ColorSelectionModel colorSelectionModel = getColorSelectionModel();
/*  446 */       if (colorSelectionModel != null) {
/*  447 */         colorSelectionModel.setSelectedColor(paramColor);
/*      */       }
/*      */     } 
/*      */     
/*  451 */     this.triangle.setColor(this.hue, this.saturation, this.brightness);
/*  452 */     this.label.setBackground(paramColor);
/*      */ 
/*      */     
/*  455 */     String str = Integer.toHexString(paramColor
/*  456 */         .getRGB() & 0xFFFFFF | 0x1000000);
/*  457 */     this.colorNameTF.setText("#" + str.substring(1));
/*      */     
/*  459 */     if (paramBoolean1) {
/*  460 */       this.redSpinner.setValue(Integer.valueOf(paramColor.getRed()));
/*  461 */       this.greenSpinner.setValue(Integer.valueOf(paramColor.getGreen()));
/*  462 */       this.blueSpinner.setValue(Integer.valueOf(paramColor.getBlue()));
/*      */       
/*  464 */       this.hueSpinner.setValue(Integer.valueOf((int)(this.hue * 360.0F)));
/*  465 */       this.saturationSpinner.setValue(Integer.valueOf((int)(this.saturation * 255.0F)));
/*  466 */       this.valueSpinner.setValue(Integer.valueOf((int)(this.brightness * 255.0F)));
/*      */     } 
/*  468 */     this.settingColor = false;
/*      */   }
/*      */   
/*      */   public Color getColor() {
/*  472 */     return this.label.getBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stateChanged(ChangeEvent paramChangeEvent) {
/*  479 */     if (this.settingColor) {
/*      */       return;
/*      */     }
/*  482 */     Color color = getColor();
/*      */     
/*  484 */     if (paramChangeEvent.getSource() == this.hueSpinner) {
/*  485 */       setHue(((Number)this.hueSpinner.getValue()).floatValue() / 360.0F, false);
/*      */     }
/*  487 */     else if (paramChangeEvent.getSource() == this.saturationSpinner) {
/*  488 */       setSaturation(((Number)this.saturationSpinner.getValue())
/*  489 */           .floatValue() / 255.0F);
/*      */     }
/*  491 */     else if (paramChangeEvent.getSource() == this.valueSpinner) {
/*  492 */       setBrightness(((Number)this.valueSpinner.getValue())
/*  493 */           .floatValue() / 255.0F);
/*      */     }
/*  495 */     else if (paramChangeEvent.getSource() == this.redSpinner) {
/*  496 */       setRed(((Number)this.redSpinner.getValue()).intValue());
/*      */     }
/*  498 */     else if (paramChangeEvent.getSource() == this.greenSpinner) {
/*  499 */       setGreen(((Number)this.greenSpinner.getValue()).intValue());
/*      */     }
/*  501 */     else if (paramChangeEvent.getSource() == this.blueSpinner) {
/*  502 */       setBlue(((Number)this.blueSpinner.getValue()).intValue());
/*      */     } 
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
/*      */   private class ColorTriangle
/*      */     extends JPanel
/*      */   {
/*      */     private Image wheelImage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Image triangleImage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double angle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int flags;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int circleX;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int circleY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ColorTriangle() {
/*  570 */       enableEvents(4L);
/*  571 */       enableEvents(16L);
/*  572 */       enableEvents(32L);
/*      */       
/*  574 */       setMinimumSize(new Dimension(getWheelRadius() * 2 + 2, 
/*  575 */             getWheelRadius() * 2 + 2));
/*  576 */       setPreferredSize(new Dimension(getWheelRadius() * 2 + 2, 
/*  577 */             getWheelRadius() * 2 + 2));
/*      */ 
/*      */       
/*  580 */       setFocusTraversalKeysEnabled(false);
/*      */ 
/*      */       
/*  583 */       getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
/*  584 */       getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
/*  585 */       getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
/*  586 */       getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
/*      */       
/*  588 */       getInputMap().put(KeyStroke.getKeyStroke("KP_UP"), "up");
/*  589 */       getInputMap().put(KeyStroke.getKeyStroke("KP_DOWN"), "down");
/*  590 */       getInputMap().put(KeyStroke.getKeyStroke("KP_LEFT"), "left");
/*  591 */       getInputMap().put(KeyStroke.getKeyStroke("KP_RIGHT"), "right");
/*      */       
/*  593 */       getInputMap().put(KeyStroke.getKeyStroke("TAB"), "focusNext");
/*  594 */       getInputMap().put(KeyStroke.getKeyStroke("shift TAB"), "focusLast");
/*      */       
/*  596 */       ActionMap actionMap = (ActionMap)UIManager.get("GTKColorChooserPanel.actionMap");
/*      */ 
/*      */       
/*  599 */       if (actionMap == null) {
/*  600 */         actionMap = new ActionMapUIResource();
/*  601 */         actionMap.put("left", new GTKColorChooserPanel.ColorAction("left", 2));
/*  602 */         actionMap.put("right", new GTKColorChooserPanel.ColorAction("right", 3));
/*  603 */         actionMap.put("up", new GTKColorChooserPanel.ColorAction("up", 0));
/*  604 */         actionMap.put("down", new GTKColorChooserPanel.ColorAction("down", 1));
/*  605 */         actionMap.put("focusNext", new GTKColorChooserPanel.ColorAction("focusNext", 4));
/*  606 */         actionMap.put("focusLast", new GTKColorChooserPanel.ColorAction("focusLast", 5));
/*  607 */         UIManager.getLookAndFeelDefaults().put("GTKColorChooserPanel.actionMap", actionMap);
/*      */       } 
/*      */       
/*  610 */       SwingUtilities.replaceUIActionMap(this, actionMap);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     GTKColorChooserPanel getGTKColorChooserPanel() {
/*  617 */       return GTKColorChooserPanel.this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void focusWheel() {
/*  624 */       setFocusType(1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void focusTriangle() {
/*  631 */       setFocusType(2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isWheelFocused() {
/*  638 */       return isSet(16);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setColor(float param1Float1, float param1Float2, float param1Float3) {
/*  645 */       if (isSet(8)) {
/*      */         return;
/*      */       }
/*      */       
/*  649 */       setAngleFromHue(param1Float1);
/*  650 */       setSaturationAndBrightness(param1Float2, param1Float3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Color getColor() {
/*  657 */       return GTKColorChooserPanel.this.getColor();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getColorX() {
/*  664 */       return this.circleX + getIndicatorSize() / 2 - getWheelXOrigin();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getColorY() {
/*  671 */       return this.circleY + getIndicatorSize() / 2 - getWheelYOrigin();
/*      */     }
/*      */     
/*      */     protected void processEvent(AWTEvent param1AWTEvent) {
/*  675 */       if (param1AWTEvent.getID() == 501 || ((
/*  676 */         isSet(2) || isSet(4)) && param1AWTEvent
/*  677 */         .getID() == 506)) {
/*      */ 
/*      */         
/*  680 */         int i = getWheelRadius();
/*  681 */         int j = ((MouseEvent)param1AWTEvent).getX() - i;
/*  682 */         int k = ((MouseEvent)param1AWTEvent).getY() - i;
/*      */         
/*  684 */         if (!hasFocus()) {
/*  685 */           requestFocus();
/*      */         }
/*  687 */         if (!isSet(4) && 
/*  688 */           adjustHue(j, k, (param1AWTEvent.getID() == 501))) {
/*  689 */           setFlag(2, true);
/*  690 */           setFocusType(1);
/*      */         }
/*  692 */         else if (adjustSB(j, k, (param1AWTEvent.getID() == 501))) {
/*      */           
/*  694 */           setFlag(4, true);
/*  695 */           setFocusType(2);
/*      */         } else {
/*      */           
/*  698 */           setFocusType(2);
/*      */         }
/*      */       
/*  701 */       } else if (param1AWTEvent.getID() == 502) {
/*      */         
/*  703 */         setFlag(4, false);
/*  704 */         setFlag(2, false);
/*      */       }
/*  706 */       else if (param1AWTEvent.getID() == 1005) {
/*      */         
/*  708 */         setFocusType(0);
/*      */       }
/*  710 */       else if (param1AWTEvent.getID() == 1004) {
/*      */ 
/*      */         
/*  713 */         if (!isSet(32) && 
/*  714 */           !isSet(16)) {
/*  715 */           setFlag(16, true);
/*  716 */           setFocusType(1);
/*      */         } 
/*  718 */         repaint();
/*      */       } 
/*  720 */       super.processEvent(param1AWTEvent);
/*      */     }
/*      */     
/*      */     public void paintComponent(Graphics param1Graphics) {
/*  724 */       super.paintComponent(param1Graphics);
/*      */ 
/*      */       
/*  727 */       int i = getWheelRadius();
/*  728 */       int j = getWheelWidth();
/*  729 */       Image image = getImage(i);
/*  730 */       param1Graphics.drawImage(image, getWheelXOrigin() - i, 
/*  731 */           getWheelYOrigin() - i, null);
/*      */ 
/*      */       
/*  734 */       if (hasFocus() && isSet(16)) {
/*  735 */         param1Graphics.setColor(Color.BLACK);
/*  736 */         param1Graphics.drawOval(getWheelXOrigin() - i, getWheelYOrigin() - i, 2 * i, 2 * i);
/*      */         
/*  738 */         param1Graphics.drawOval(getWheelXOrigin() - i + j, getWheelYOrigin() - i + j, 2 * (i - j), 2 * (i - j));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  744 */       if (Math.toDegrees(6.283185307179586D - this.angle) <= 20.0D || 
/*  745 */         Math.toDegrees(6.283185307179586D - this.angle) >= 201.0D) {
/*  746 */         param1Graphics.setColor(Color.WHITE);
/*      */       } else {
/*      */         
/*  749 */         param1Graphics.setColor(Color.BLACK);
/*      */       } 
/*  751 */       int k = (int)(Math.cos(this.angle) * i);
/*  752 */       int m = (int)(Math.sin(this.angle) * i);
/*  753 */       int n = (int)(Math.cos(this.angle) * (i - j));
/*  754 */       int i1 = (int)(Math.sin(this.angle) * (i - j));
/*  755 */       param1Graphics.drawLine(k + i, m + i, n + i, i1 + i);
/*      */ 
/*      */ 
/*      */       
/*  759 */       if (hasFocus() && isSet(32)) {
/*  760 */         Graphics graphics = param1Graphics.create();
/*  761 */         int i2 = getTriangleCircumscribedRadius();
/*  762 */         int i3 = (int)((3 * i2) / Math.sqrt(3.0D));
/*  763 */         graphics.translate(getWheelXOrigin(), getWheelYOrigin());
/*  764 */         ((Graphics2D)graphics).rotate(this.angle + 1.5707963267948966D);
/*  765 */         graphics.setColor(Color.BLACK);
/*  766 */         graphics.drawLine(0, -i2, i3 / 2, i2 / 2);
/*  767 */         graphics.drawLine(i3 / 2, i2 / 2, -i3 / 2, i2 / 2);
/*  768 */         graphics.drawLine(-i3 / 2, i2 / 2, 0, -i2);
/*  769 */         graphics.dispose();
/*      */       } 
/*      */ 
/*      */       
/*  773 */       param1Graphics.setColor(Color.BLACK);
/*  774 */       param1Graphics.drawOval(this.circleX, this.circleY, getIndicatorSize() - 1, 
/*  775 */           getIndicatorSize() - 1);
/*  776 */       param1Graphics.setColor(Color.WHITE);
/*  777 */       param1Graphics.drawOval(this.circleX + 1, this.circleY + 1, getIndicatorSize() - 3, 
/*  778 */           getIndicatorSize() - 3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Image getImage(int param1Int) {
/*  785 */       if (!isSet(1) && this.wheelImage != null && this.wheelImage
/*  786 */         .getWidth(null) == param1Int * 2) {
/*  787 */         return this.wheelImage;
/*      */       }
/*  789 */       if (this.wheelImage == null || this.wheelImage.getWidth(null) != param1Int) {
/*  790 */         this.wheelImage = getWheelImage(param1Int);
/*      */       }
/*  792 */       int i = getTriangleCircumscribedRadius();
/*  793 */       int j = (int)(i * 3.0D / 2.0D);
/*  794 */       int k = (int)((2 * j) / Math.sqrt(3.0D));
/*  795 */       if (this.triangleImage == null || this.triangleImage.getWidth(null) != k) {
/*  796 */         this.triangleImage = new BufferedImage(k, k, 2);
/*      */       }
/*      */       
/*  799 */       Graphics graphics = this.triangleImage.getGraphics();
/*  800 */       graphics.setColor(new Color(0, 0, 0, 0));
/*  801 */       graphics.fillRect(0, 0, k, k);
/*  802 */       graphics.translate(k / 2, 0);
/*  803 */       paintTriangle(graphics, j, getColor());
/*  804 */       graphics.translate(-k / 2, 0);
/*  805 */       graphics.dispose();
/*      */       
/*  807 */       graphics = this.wheelImage.getGraphics();
/*  808 */       graphics.setColor(new Color(0, 0, 0, 0));
/*  809 */       graphics.fillOval(getWheelWidth(), getWheelWidth(), 2 * (param1Int - 
/*  810 */           getWheelWidth()), 2 * (param1Int - 
/*  811 */           getWheelWidth()));
/*      */       
/*  813 */       double d = Math.toRadians(-30.0D) + this.angle;
/*  814 */       graphics.translate(param1Int, param1Int);
/*  815 */       ((Graphics2D)graphics).rotate(d);
/*  816 */       graphics.drawImage(this.triangleImage, -k / 2, 
/*  817 */           getWheelWidth() - param1Int, null);
/*  818 */       ((Graphics2D)graphics).rotate(-d);
/*  819 */       graphics.translate(k / 2, param1Int - getWheelWidth());
/*      */       
/*  821 */       setFlag(1, false);
/*      */       
/*  823 */       return this.wheelImage;
/*      */     }
/*      */     
/*      */     private void paintTriangle(Graphics param1Graphics, int param1Int, Color param1Color) {
/*  827 */       float[] arrayOfFloat = Color.RGBtoHSB(param1Color.getRed(), param1Color
/*  828 */           .getGreen(), param1Color
/*  829 */           .getBlue(), null);
/*  830 */       float f = arrayOfFloat[0];
/*  831 */       double d = param1Int;
/*  832 */       for (byte b = 0; b < param1Int; b++) {
/*  833 */         int i = (int)(b * Math.tan(Math.toRadians(30.0D)));
/*  834 */         float f1 = (i * 2);
/*  835 */         if (i > 0) {
/*  836 */           float f2 = (float)(b / d);
/*  837 */           for (int j = -i; j <= i; j++) {
/*  838 */             float f3 = j / f1 + 0.5F;
/*  839 */             param1Graphics.setColor(Color.getHSBColor(f, f3, f2));
/*  840 */             param1Graphics.fillRect(j, b, 1, 1);
/*      */           } 
/*      */         } else {
/*      */           
/*  844 */           param1Graphics.setColor(param1Color);
/*  845 */           param1Graphics.fillRect(0, b, 1, 1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Image getWheelImage(int param1Int) {
/*  857 */       int i = param1Int - getWheelWidth();
/*  858 */       int j = param1Int * 2;
/*  859 */       BufferedImage bufferedImage = new BufferedImage(j, j, 2);
/*      */ 
/*      */       
/*  862 */       for (int k = -param1Int; k < param1Int; k++) {
/*  863 */         int m = k * k;
/*  864 */         for (int n = -param1Int; n < param1Int; n++) {
/*  865 */           double d = Math.sqrt((m + n * n));
/*      */           
/*  867 */           if (d < param1Int && d > i) {
/*  868 */             int i1 = colorWheelLocationToRGB(n, k, d) | 0xFF000000;
/*      */             
/*  870 */             bufferedImage.setRGB(n + param1Int, k + param1Int, i1);
/*      */           } 
/*      */         } 
/*      */       } 
/*  874 */       this.wheelImage = bufferedImage;
/*  875 */       return this.wheelImage;
/*      */     }
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
/*      */     boolean adjustSB(int param1Int1, int param1Int2, boolean param1Boolean) {
/*  891 */       int i = getWheelRadius() - getWheelWidth();
/*  892 */       boolean bool = false;
/*      */       
/*  894 */       param1Int2 = -param1Int2;
/*  895 */       if (param1Boolean && (param1Int1 < -i || param1Int1 > i || param1Int2 < -i || param1Int2 > i))
/*      */       {
/*  897 */         return false;
/*      */       }
/*      */       
/*  900 */       int j = i * 3 / 2;
/*  901 */       double d1 = Math.cos(this.angle) * param1Int1 - Math.sin(this.angle) * param1Int2;
/*  902 */       double d2 = Math.sin(this.angle) * param1Int1 + Math.cos(this.angle) * param1Int2;
/*  903 */       if (d1 < -(i / 2)) {
/*  904 */         if (param1Boolean) {
/*  905 */           return false;
/*      */         }
/*  907 */         d1 = (-i / 2);
/*  908 */         bool = true;
/*      */       }
/*  910 */       else if ((int)d1 > i) {
/*  911 */         if (param1Boolean) {
/*  912 */           return false;
/*      */         }
/*  914 */         d1 = i;
/*  915 */         bool = true;
/*      */       } 
/*      */ 
/*      */       
/*  919 */       int k = (int)((j - d1 - i / 2.0D) * Math.tan(Math.toRadians(30.0D)));
/*  920 */       if (d2 <= -k) {
/*  921 */         if (param1Boolean) {
/*  922 */           return false;
/*      */         }
/*  924 */         d2 = -k;
/*  925 */         bool = true;
/*      */       }
/*  927 */       else if (d2 > k) {
/*  928 */         if (param1Boolean) {
/*  929 */           return false;
/*      */         }
/*  931 */         d2 = k;
/*  932 */         bool = true;
/*      */       } 
/*      */ 
/*      */       
/*  936 */       double d3 = Math.cos(Math.toRadians(-30.0D)) * d1 - Math.sin(Math.toRadians(-30.0D)) * d2;
/*      */       
/*  938 */       double d4 = Math.sin(Math.toRadians(-30.0D)) * d1 + Math.cos(Math.toRadians(-30.0D)) * d2;
/*  939 */       float f1 = Math.min(1.0F, (float)((i - d4) / j));
/*      */       
/*  941 */       float f2 = (float)(Math.tan(Math.toRadians(30.0D)) * (i - d4));
/*  942 */       float f3 = Math.min(1.0F, (float)(d3 / f2 / 2.0D + 0.5D));
/*      */       
/*  944 */       setFlag(8, true);
/*  945 */       if (bool) {
/*  946 */         setSaturationAndBrightness(f3, f1);
/*      */       } else {
/*      */         
/*  949 */         setSaturationAndBrightness(f3, f1, param1Int1 + 
/*  950 */             getWheelXOrigin(), getWheelYOrigin() - param1Int2);
/*      */       } 
/*  952 */       GTKColorChooserPanel.this.setSaturationAndBrightness(f3, f1, true);
/*      */       
/*  954 */       setFlag(8, false);
/*  955 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setSaturationAndBrightness(float param1Float1, float param1Float2) {
/*  962 */       int i = getTriangleCircumscribedRadius();
/*  963 */       int j = i * 3 / 2;
/*  964 */       double d1 = (param1Float2 * j);
/*  965 */       double d2 = d1 * Math.tan(Math.toRadians(30.0D));
/*  966 */       double d3 = 2.0D * d2 * param1Float1 - d2;
/*  967 */       d1 -= i;
/*      */       
/*  969 */       double d4 = Math.cos(Math.toRadians(-60.0D) - this.angle) * d1 - Math.sin(Math.toRadians(-60.0D) - this.angle) * d3;
/*      */       
/*  971 */       double d5 = Math.sin(Math.toRadians(-60.0D) - this.angle) * d1 + Math.cos(Math.toRadians(-60.0D) - this.angle) * d3;
/*  972 */       int k = (int)d4 + getWheelXOrigin();
/*  973 */       int m = getWheelYOrigin() - (int)d5;
/*      */       
/*  975 */       setSaturationAndBrightness(param1Float1, param1Float2, k, m);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setSaturationAndBrightness(float param1Float1, float param1Float2, int param1Int1, int param1Int2) {
/*  984 */       param1Int1 -= getIndicatorSize() / 2;
/*  985 */       param1Int2 -= getIndicatorSize() / 2;
/*      */       
/*  987 */       int i = Math.min(param1Int1, this.circleX);
/*  988 */       int j = Math.min(param1Int2, this.circleY);
/*      */       
/*  990 */       repaint(i, j, Math.max(this.circleX, param1Int1) - i + 
/*  991 */           getIndicatorSize() + 1, Math.max(this.circleY, param1Int2) - j + 
/*  992 */           getIndicatorSize() + 1);
/*  993 */       this.circleX = param1Int1;
/*  994 */       this.circleY = param1Int2;
/*      */     }
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
/*      */     private boolean adjustHue(int param1Int1, int param1Int2, boolean param1Boolean) {
/* 1010 */       double d = Math.sqrt((param1Int1 * param1Int1 + param1Int2 * param1Int2));
/* 1011 */       int i = getWheelRadius();
/*      */       
/* 1013 */       if (!param1Boolean || (d >= (i - getWheelWidth()) && d < i)) {
/*      */         double d1;
/*      */         
/* 1016 */         if (param1Int1 == 0) {
/* 1017 */           if (param1Int2 > 0) {
/* 1018 */             d1 = 1.5707963267948966D;
/*      */           } else {
/*      */             
/* 1021 */             d1 = 4.71238898038469D;
/*      */           } 
/*      */         } else {
/*      */           
/* 1025 */           d1 = Math.atan(param1Int2 / param1Int1);
/* 1026 */           if (param1Int1 < 0) {
/* 1027 */             d1 += Math.PI;
/*      */           }
/* 1029 */           else if (d1 < 0.0D) {
/* 1030 */             d1 += 6.283185307179586D;
/*      */           } 
/*      */         } 
/* 1033 */         setFlag(8, true);
/* 1034 */         GTKColorChooserPanel.this.setHue((float)(1.0D - d1 / Math.PI / 2.0D), true);
/* 1035 */         setFlag(8, false);
/* 1036 */         setHueAngle(d1);
/* 1037 */         setSaturationAndBrightness(GTKColorChooserPanel.this.getSaturation(), GTKColorChooserPanel.this.getBrightness());
/* 1038 */         return true;
/*      */       } 
/* 1040 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setAngleFromHue(float param1Float) {
/* 1047 */       setHueAngle((1.0D - param1Float) * Math.PI * 2.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setHueAngle(double param1Double) {
/* 1054 */       double d = this.angle;
/*      */       
/* 1056 */       this.angle = param1Double;
/* 1057 */       if (param1Double != d) {
/* 1058 */         setFlag(1, true);
/* 1059 */         repaint();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getIndicatorSize() {
/* 1067 */       return 8;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getTriangleCircumscribedRadius() {
/* 1074 */       return 72;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getWheelXOrigin() {
/* 1081 */       return 85;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getWheelYOrigin() {
/* 1088 */       return 85;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getWheelWidth() {
/* 1095 */       return 13;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setFocusType(int param1Int) {
/* 1102 */       if (param1Int == 0) {
/* 1103 */         setFlag(16, false);
/* 1104 */         setFlag(32, false);
/* 1105 */         repaint();
/*      */       } else {
/*      */         
/* 1108 */         byte b1 = 16;
/* 1109 */         byte b2 = 32;
/*      */         
/* 1111 */         if (param1Int == 2) {
/* 1112 */           b1 = 32;
/* 1113 */           b2 = 16;
/*      */         } 
/* 1115 */         if (!isSet(b1)) {
/* 1116 */           setFlag(b1, true);
/* 1117 */           repaint();
/* 1118 */           setFlag(b2, false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getWheelRadius() {
/* 1129 */       return 85;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setFlag(int param1Int, boolean param1Boolean) {
/* 1136 */       if (param1Boolean) {
/* 1137 */         this.flags |= param1Int;
/*      */       } else {
/*      */         
/* 1140 */         this.flags &= param1Int ^ 0xFFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isSet(int param1Int) {
/* 1148 */       return ((this.flags & param1Int) == param1Int);
/*      */     }
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
/*      */     private int colorWheelLocationToRGB(int param1Int1, int param1Int2, double param1Double) {
/*      */       int i;
/* 1162 */       double d = Math.acos(param1Int1 / param1Double);
/*      */ 
/*      */       
/* 1165 */       if (d < 1.0471975803375244D) {
/* 1166 */         if (param1Int2 < 0)
/*      */         {
/* 1168 */           i = 0xFF0000 | Math.min(255, (int)(255.0D * d / 1.0471975803375244D)) << 8;
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 1173 */           i = 0xFF0000 | Math.min(255, (int)(255.0D * d / 1.0471975803375244D));
/*      */         }
/*      */       
/*      */       }
/* 1177 */       else if (d < 2.094395160675049D) {
/* 1178 */         d -= 1.0471975803375244D;
/* 1179 */         if (param1Int2 < 0)
/*      */         {
/* 1181 */           i = 0xFF00 | Math.max(0, 255 - (int)(255.0D * d / 1.0471975803375244D)) << 16;
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 1186 */           i = 0xFF | Math.max(0, 255 - (int)(255.0D * d / 1.0471975803375244D)) << 16;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1191 */         d -= 2.094395160675049D;
/* 1192 */         if (param1Int2 < 0) {
/*      */           
/* 1194 */           i = 0xFF00 | Math.min(255, (int)(255.0D * d / 1.0471975803375244D));
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1199 */           i = 0xFF | Math.min(255, (int)(255.0D * d / 1.0471975803375244D)) << 8;
/*      */         } 
/*      */       } 
/*      */       
/* 1203 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void incrementHue(boolean param1Boolean) {
/* 1210 */       float f = GTKColorChooserPanel.this.triangle.getGTKColorChooserPanel().getHue();
/*      */       
/* 1212 */       if (param1Boolean) {
/* 1213 */         f += 0.0027777778F;
/*      */       } else {
/*      */         
/* 1216 */         f -= 0.0027777778F;
/*      */       } 
/* 1218 */       if (f > 1.0F) {
/* 1219 */         f--;
/*      */       }
/* 1221 */       else if (f < 0.0F) {
/* 1222 */         f++;
/*      */       } 
/* 1224 */       getGTKColorChooserPanel().setHue(f, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ColorAction
/*      */     extends AbstractAction
/*      */   {
/*      */     private int type;
/*      */ 
/*      */     
/*      */     ColorAction(String param1String, int param1Int) {
/* 1236 */       super(param1String);
/* 1237 */       this.type = param1Int;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1241 */       GTKColorChooserPanel.ColorTriangle colorTriangle = (GTKColorChooserPanel.ColorTriangle)param1ActionEvent.getSource();
/*      */       
/* 1243 */       if (colorTriangle.isWheelFocused()) {
/* 1244 */         float f = colorTriangle.getGTKColorChooserPanel().getHue();
/*      */         
/* 1246 */         switch (this.type) {
/*      */           case 0:
/*      */           case 2:
/* 1249 */             colorTriangle.incrementHue(true);
/*      */             break;
/*      */           case 1:
/*      */           case 3:
/* 1253 */             colorTriangle.incrementHue(false);
/*      */             break;
/*      */           case 4:
/* 1256 */             colorTriangle.focusTriangle();
/*      */             break;
/*      */           case 5:
/* 1259 */             GTKColorChooserPanel.compositeRequestFocus(colorTriangle, false);
/*      */             break;
/*      */         } 
/*      */       
/*      */       } else {
/* 1264 */         byte b1 = 0;
/* 1265 */         byte b2 = 0;
/*      */         
/* 1267 */         switch (this.type) {
/*      */           
/*      */           case 0:
/* 1270 */             b2--;
/*      */             break;
/*      */           
/*      */           case 1:
/* 1274 */             b2++;
/*      */             break;
/*      */           
/*      */           case 2:
/* 1278 */             b1--;
/*      */             break;
/*      */           
/*      */           case 3:
/* 1282 */             b1++;
/*      */             break;
/*      */           case 4:
/* 1285 */             GTKColorChooserPanel.compositeRequestFocus(colorTriangle, true);
/*      */             return;
/*      */           case 5:
/* 1288 */             colorTriangle.focusWheel();
/*      */             return;
/*      */         } 
/* 1291 */         colorTriangle.adjustSB(colorTriangle.getColorX() + b1, colorTriangle
/* 1292 */             .getColorY() + b2, true);
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class OpaqueLabel extends JLabel {
/*      */     private OpaqueLabel() {}
/*      */     
/*      */     public boolean isOpaque() {
/* 1300 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKColorChooserPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */