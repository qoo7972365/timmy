/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicComboBoxEditor;
/*     */ import javax.swing.plaf.basic.BasicComboBoxRenderer;
/*     */ import javax.swing.plaf.basic.BasicComboBoxUI;
/*     */ import javax.swing.plaf.basic.BasicComboPopup;
/*     */ import javax.swing.plaf.basic.ComboPopup;
/*     */ import sun.swing.DefaultLookup;
/*     */ import sun.swing.StringUIClientPropertyKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsComboBoxUI
/*     */   extends BasicComboBoxUI
/*     */ {
/*  61 */   private static final MouseListener rolloverListener = new MouseAdapter()
/*     */     {
/*     */       private void handleRollover(MouseEvent param1MouseEvent, boolean param1Boolean) {
/*  64 */         JComboBox jComboBox = getComboBox(param1MouseEvent);
/*  65 */         WindowsComboBoxUI windowsComboBoxUI = getWindowsComboBoxUI(param1MouseEvent);
/*  66 */         if (jComboBox == null || windowsComboBoxUI == null) {
/*     */           return;
/*     */         }
/*  69 */         if (!jComboBox.isEditable()) {
/*     */ 
/*     */           
/*  72 */           ButtonModel buttonModel = null;
/*  73 */           if (windowsComboBoxUI.arrowButton != null) {
/*  74 */             buttonModel = windowsComboBoxUI.arrowButton.getModel();
/*     */           }
/*  76 */           if (buttonModel != null) {
/*  77 */             buttonModel.setRollover(param1Boolean);
/*     */           }
/*     */         } 
/*  80 */         windowsComboBoxUI.isRollover = param1Boolean;
/*  81 */         jComboBox.repaint();
/*     */       }
/*     */       
/*     */       public void mouseEntered(MouseEvent param1MouseEvent) {
/*  85 */         handleRollover(param1MouseEvent, true);
/*     */       }
/*     */       
/*     */       public void mouseExited(MouseEvent param1MouseEvent) {
/*  89 */         handleRollover(param1MouseEvent, false);
/*     */       }
/*     */       
/*     */       private JComboBox getComboBox(MouseEvent param1MouseEvent) {
/*  93 */         Object object = param1MouseEvent.getSource();
/*  94 */         JComboBox jComboBox = null;
/*  95 */         if (object instanceof JComboBox) {
/*  96 */           jComboBox = (JComboBox)object;
/*  97 */         } else if (object instanceof WindowsComboBoxUI.XPComboBoxButton) {
/*     */           
/*  99 */           jComboBox = (((WindowsComboBoxUI.XPComboBoxButton)object).getWindowsComboBoxUI()).comboBox;
/*     */         } 
/* 101 */         return jComboBox;
/*     */       }
/*     */       
/*     */       private WindowsComboBoxUI getWindowsComboBoxUI(MouseEvent param1MouseEvent) {
/* 105 */         JComboBox jComboBox = getComboBox(param1MouseEvent);
/* 106 */         WindowsComboBoxUI windowsComboBoxUI = null;
/* 107 */         if (jComboBox != null && jComboBox
/* 108 */           .getUI() instanceof WindowsComboBoxUI) {
/* 109 */           windowsComboBoxUI = (WindowsComboBoxUI)jComboBox.getUI();
/*     */         }
/* 111 */         return windowsComboBoxUI;
/*     */       }
/*     */     };
/*     */   
/*     */   private boolean isRollover = false;
/*     */   
/* 117 */   private static final PropertyChangeListener componentOrientationListener = new PropertyChangeListener()
/*     */     {
/*     */       public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 120 */         String str = param1PropertyChangeEvent.getPropertyName();
/* 121 */         Object object = null;
/* 122 */         if ("componentOrientation" == str && 
/* 123 */           object = param1PropertyChangeEvent.getSource() instanceof JComboBox && ((JComboBox)object)
/* 124 */           .getUI() instanceof WindowsComboBoxUI) {
/*     */           
/* 126 */           JComboBox jComboBox = (JComboBox)object;
/* 127 */           WindowsComboBoxUI windowsComboBoxUI = (WindowsComboBoxUI)jComboBox.getUI();
/* 128 */           if (windowsComboBoxUI.arrowButton instanceof WindowsComboBoxUI.XPComboBoxButton) {
/* 129 */             ((WindowsComboBoxUI.XPComboBoxButton)windowsComboBoxUI.arrowButton).setPart(
/* 130 */                 (jComboBox.getComponentOrientation() == ComponentOrientation.RIGHT_TO_LEFT) ? TMSchema.Part.CP_DROPDOWNBUTTONLEFT : TMSchema.Part.CP_DROPDOWNBUTTONRIGHT);
/*     */           }
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 140 */     return new WindowsComboBoxUI();
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 144 */     super.installUI(paramJComponent);
/* 145 */     this.isRollover = false;
/* 146 */     this.comboBox.setRequestFocusEnabled(true);
/* 147 */     if (XPStyle.getXP() != null && this.arrowButton != null) {
/*     */ 
/*     */       
/* 150 */       this.comboBox.addMouseListener(rolloverListener);
/* 151 */       this.arrowButton.addMouseListener(rolloverListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 156 */     this.comboBox.removeMouseListener(rolloverListener);
/* 157 */     if (this.arrowButton != null) {
/* 158 */       this.arrowButton.removeMouseListener(rolloverListener);
/*     */     }
/* 160 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 169 */     super.installListeners();
/* 170 */     XPStyle xPStyle = XPStyle.getXP();
/*     */     
/* 172 */     if (xPStyle != null && xPStyle
/* 173 */       .isSkinDefined(this.comboBox, TMSchema.Part.CP_DROPDOWNBUTTONRIGHT)) {
/* 174 */       this.comboBox.addPropertyChangeListener("componentOrientation", componentOrientationListener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 185 */     super.uninstallListeners();
/* 186 */     this.comboBox.removePropertyChangeListener("componentOrientation", componentOrientationListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configureEditor() {
/* 195 */     super.configureEditor();
/* 196 */     if (XPStyle.getXP() != null) {
/* 197 */       this.editor.addMouseListener(rolloverListener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unconfigureEditor() {
/* 206 */     super.unconfigureEditor();
/* 207 */     this.editor.removeMouseListener(rolloverListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 215 */     if (XPStyle.getXP() != null) {
/* 216 */       paintXPComboBoxBackground(paramGraphics, paramJComponent);
/*     */     }
/* 218 */     super.paint(paramGraphics, paramJComponent);
/*     */   }
/*     */   
/*     */   TMSchema.State getXPComboBoxState(JComponent paramJComponent) {
/* 222 */     TMSchema.State state = TMSchema.State.NORMAL;
/* 223 */     if (!paramJComponent.isEnabled()) {
/* 224 */       state = TMSchema.State.DISABLED;
/* 225 */     } else if (isPopupVisible(this.comboBox)) {
/* 226 */       state = TMSchema.State.PRESSED;
/* 227 */     } else if (this.isRollover) {
/* 228 */       state = TMSchema.State.HOT;
/*     */     } 
/* 230 */     return state;
/*     */   }
/*     */   
/*     */   private void paintXPComboBoxBackground(Graphics paramGraphics, JComponent paramJComponent) {
/* 234 */     XPStyle xPStyle = XPStyle.getXP();
/* 235 */     if (xPStyle == null) {
/*     */       return;
/*     */     }
/* 238 */     TMSchema.State state = getXPComboBoxState(paramJComponent);
/* 239 */     XPStyle.Skin skin = null;
/* 240 */     if (!this.comboBox.isEditable() && xPStyle
/* 241 */       .isSkinDefined(paramJComponent, TMSchema.Part.CP_READONLY)) {
/* 242 */       skin = xPStyle.getSkin(paramJComponent, TMSchema.Part.CP_READONLY);
/*     */     }
/* 244 */     if (skin == null) {
/* 245 */       skin = xPStyle.getSkin(paramJComponent, TMSchema.Part.CP_COMBOBOX);
/*     */     }
/* 247 */     skin.paintSkin(paramGraphics, 0, 0, paramJComponent.getWidth(), paramJComponent.getHeight(), state);
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
/*     */   public void paintCurrentValue(Graphics paramGraphics, Rectangle paramRectangle, boolean paramBoolean) {
/* 261 */     XPStyle xPStyle = XPStyle.getXP();
/* 262 */     if (xPStyle != null) {
/* 263 */       paramRectangle.x += 2;
/* 264 */       paramRectangle.y += 2;
/* 265 */       paramRectangle.width -= 4;
/* 266 */       paramRectangle.height -= 4;
/*     */     } else {
/* 268 */       paramRectangle.x++;
/* 269 */       paramRectangle.y++;
/* 270 */       paramRectangle.width -= 2;
/* 271 */       paramRectangle.height -= 2;
/*     */     } 
/* 273 */     if (!this.comboBox.isEditable() && xPStyle != null && xPStyle
/*     */       
/* 275 */       .isSkinDefined(this.comboBox, TMSchema.Part.CP_READONLY)) {
/*     */       Component component;
/*     */ 
/*     */ 
/*     */       
/* 280 */       ListCellRenderer<? super Object> listCellRenderer = this.comboBox.getRenderer();
/*     */       
/* 282 */       if (paramBoolean && !isPopupVisible(this.comboBox)) {
/* 283 */         component = listCellRenderer.getListCellRendererComponent(this.listBox, this.comboBox
/*     */             
/* 285 */             .getSelectedItem(), -1, true, false);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 290 */         component = listCellRenderer.getListCellRendererComponent(this.listBox, this.comboBox
/*     */             
/* 292 */             .getSelectedItem(), -1, false, false);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 297 */       component.setFont(this.comboBox.getFont());
/* 298 */       if (this.comboBox.isEnabled()) {
/* 299 */         component.setForeground(this.comboBox.getForeground());
/* 300 */         component.setBackground(this.comboBox.getBackground());
/*     */       } else {
/* 302 */         component.setForeground(DefaultLookup.getColor(this.comboBox, this, "ComboBox.disabledForeground", null));
/*     */         
/* 304 */         component.setBackground(DefaultLookup.getColor(this.comboBox, this, "ComboBox.disabledBackground", null));
/*     */       } 
/*     */       
/* 307 */       boolean bool = false;
/* 308 */       if (component instanceof javax.swing.JPanel) {
/* 309 */         bool = true;
/*     */       }
/* 311 */       this.currentValuePane.paintComponent(paramGraphics, component, this.comboBox, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, bool);
/*     */     }
/*     */     else {
/*     */       
/* 315 */       super.paintCurrentValue(paramGraphics, paramRectangle, paramBoolean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintCurrentValueBackground(Graphics paramGraphics, Rectangle paramRectangle, boolean paramBoolean) {
/* 325 */     if (XPStyle.getXP() == null) {
/* 326 */       super.paintCurrentValueBackground(paramGraphics, paramRectangle, paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 331 */     Dimension dimension = super.getMinimumSize(paramJComponent);
/* 332 */     if (XPStyle.getXP() != null) {
/* 333 */       dimension.width += 5;
/*     */     } else {
/* 335 */       dimension.width += 4;
/*     */     } 
/* 337 */     dimension.height += 2;
/* 338 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutManager createLayoutManager() {
/* 348 */     return new BasicComboBoxUI.ComboBoxLayoutManager() {
/*     */         public void layoutContainer(Container param1Container) {
/* 350 */           super.layoutContainer(param1Container);
/*     */           
/* 352 */           if (XPStyle.getXP() != null && WindowsComboBoxUI.this.arrowButton != null) {
/* 353 */             Dimension dimension = param1Container.getSize();
/* 354 */             Insets insets = WindowsComboBoxUI.this.getInsets();
/* 355 */             int i = (WindowsComboBoxUI.this.arrowButton.getPreferredSize()).width;
/* 356 */             WindowsComboBoxUI.this.arrowButton.setBounds(WindowsGraphicsUtils.isLeftToRight(param1Container) ? (dimension.width - insets.right - i) : insets.left, insets.top, i, dimension.height - insets.top - insets.bottom);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installKeyboardActions() {
/* 367 */     super.installKeyboardActions();
/*     */   }
/*     */   
/*     */   protected ComboPopup createPopup() {
/* 371 */     return super.createPopup();
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
/*     */   protected ComboBoxEditor createEditor() {
/* 383 */     return new WindowsComboBoxEditor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ListCellRenderer createRenderer() {
/* 392 */     XPStyle xPStyle = XPStyle.getXP();
/* 393 */     if (xPStyle != null && xPStyle.isSkinDefined(this.comboBox, TMSchema.Part.CP_READONLY)) {
/* 394 */       return new WindowsComboBoxRenderer();
/*     */     }
/* 396 */     return super.createRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createArrowButton() {
/* 407 */     XPStyle xPStyle = XPStyle.getXP();
/* 408 */     if (xPStyle != null) {
/* 409 */       return new XPComboBoxButton(xPStyle);
/*     */     }
/* 411 */     return super.createArrowButton();
/*     */   }
/*     */   
/*     */   private class XPComboBoxButton
/*     */     extends XPStyle.GlyphButton {
/*     */     public XPComboBoxButton(XPStyle param1XPStyle) {
/* 417 */       super((Component)null, 
/* 418 */           !param1XPStyle.isSkinDefined(WindowsComboBoxUI.this.comboBox, TMSchema.Part.CP_DROPDOWNBUTTONRIGHT) ? TMSchema.Part.CP_DROPDOWNBUTTON : (
/*     */           
/* 420 */           (WindowsComboBoxUI.this.comboBox.getComponentOrientation() == ComponentOrientation.RIGHT_TO_LEFT) ? TMSchema.Part.CP_DROPDOWNBUTTONLEFT : TMSchema.Part.CP_DROPDOWNBUTTONRIGHT));
/*     */ 
/*     */ 
/*     */       
/* 424 */       setRequestFocusEnabled(false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected TMSchema.State getState() {
/* 430 */       TMSchema.State state = super.getState();
/* 431 */       XPStyle xPStyle = XPStyle.getXP();
/* 432 */       if (state != TMSchema.State.DISABLED && WindowsComboBoxUI.this
/* 433 */         .comboBox != null && !WindowsComboBoxUI.this.comboBox.isEditable() && xPStyle != null && xPStyle
/* 434 */         .isSkinDefined(WindowsComboBoxUI.this.comboBox, TMSchema.Part.CP_DROPDOWNBUTTONRIGHT))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 440 */         state = TMSchema.State.NORMAL;
/*     */       }
/* 442 */       return state;
/*     */     }
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 446 */       return new Dimension(17, 21);
/*     */     }
/*     */     
/*     */     void setPart(TMSchema.Part param1Part) {
/* 450 */       setPart(WindowsComboBoxUI.this.comboBox, param1Part);
/*     */     }
/*     */     
/*     */     WindowsComboBoxUI getWindowsComboBoxUI() {
/* 454 */       return WindowsComboBoxUI.this;
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
/*     */   @Deprecated
/*     */   protected class WindowsComboPopup
/*     */     extends BasicComboPopup
/*     */   {
/*     */     public WindowsComboPopup(JComboBox<Object> param1JComboBox) {
/* 471 */       super(param1JComboBox);
/*     */     }
/*     */     
/*     */     protected KeyListener createKeyListener() {
/* 475 */       return new InvocationKeyHandler();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected class InvocationKeyHandler
/*     */       extends BasicComboPopup.InvocationKeyHandler {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class WindowsComboBoxEditor
/*     */     extends BasicComboBoxEditor.UIResource
/*     */   {
/*     */     protected JTextField createEditorComponent() {
/* 497 */       JTextField jTextField = super.createEditorComponent();
/* 498 */       Border border = (Border)UIManager.get("ComboBox.editorBorder");
/* 499 */       if (border != null) {
/* 500 */         jTextField.setBorder(border);
/*     */       }
/* 502 */       jTextField.setOpaque(false);
/* 503 */       return jTextField;
/*     */     }
/*     */     
/*     */     public void setItem(Object param1Object) {
/* 507 */       super.setItem(param1Object);
/* 508 */       Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/* 509 */       if (component == this.editor || component == this.editor.getParent()) {
/* 510 */         this.editor.selectAll();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class WindowsComboBoxRenderer
/*     */     extends BasicComboBoxRenderer.UIResource
/*     */   {
/*     */     private WindowsComboBoxRenderer() {}
/*     */     
/* 521 */     private static final Object BORDER_KEY = new StringUIClientPropertyKey("BORDER_KEY");
/*     */     
/* 523 */     private static final Border NULL_BORDER = new EmptyBorder(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 535 */       Component component = super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*     */       
/* 537 */       if (component instanceof JComponent) {
/* 538 */         JComponent jComponent = (JComponent)component;
/* 539 */         if (param1Int == -1 && param1Boolean1) {
/* 540 */           Border border = jComponent.getBorder();
/*     */           
/* 542 */           WindowsBorders.DashedBorder dashedBorder = new WindowsBorders.DashedBorder(param1JList.getForeground());
/* 543 */           jComponent.setBorder(dashedBorder);
/*     */           
/* 545 */           if (jComponent.getClientProperty(BORDER_KEY) == null) {
/* 546 */             jComponent.putClientProperty(BORDER_KEY, (border == null) ? NULL_BORDER : border);
/*     */           
/*     */           }
/*     */         }
/* 550 */         else if (jComponent.getBorder() instanceof WindowsBorders.DashedBorder) {
/*     */           
/* 552 */           Object object = jComponent.getClientProperty(BORDER_KEY);
/* 553 */           if (object instanceof Border) {
/* 554 */             jComponent.setBorder((object == NULL_BORDER) ? null : (Border)object);
/*     */           }
/*     */ 
/*     */           
/* 558 */           jComponent.putClientProperty(BORDER_KEY, (Object)null);
/*     */         } 
/*     */         
/* 561 */         if (param1Int == -1) {
/* 562 */           jComponent.setOpaque(false);
/* 563 */           jComponent.setForeground(param1JList.getForeground());
/*     */         } else {
/* 565 */           jComponent.setOpaque(true);
/*     */         } 
/*     */       } 
/* 568 */       return component;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsComboBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */