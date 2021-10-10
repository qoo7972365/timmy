/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicComboBoxEditor;
/*     */ import javax.swing.plaf.basic.BasicComboBoxUI;
/*     */ import javax.swing.plaf.basic.ComboPopup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthComboBoxUI
/*     */   extends BasicComboBoxUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   private boolean useListColors;
/*     */   Insets popupInsets;
/*     */   private boolean buttonWhenNotEditable;
/*     */   private boolean pressedWhenPopupVisible;
/*     */   private ButtonHandler buttonHandler;
/*     */   private EditorFocusHandler editorFocusHandler;
/*     */   private boolean forceOpaque = false;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 102 */     return new SynthComboBoxUI();
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
/*     */   public void installUI(JComponent paramJComponent) {
/* 114 */     this.buttonHandler = new ButtonHandler();
/* 115 */     super.installUI(paramJComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/* 120 */     updateStyle(this.comboBox);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComboBox paramJComboBox) {
/* 124 */     SynthStyle synthStyle = this.style;
/* 125 */     SynthContext synthContext = getContext(paramJComboBox, 1);
/*     */     
/* 127 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 128 */     if (this.style != synthStyle) {
/* 129 */       this.padding = (Insets)this.style.get(synthContext, "ComboBox.padding");
/* 130 */       this.popupInsets = (Insets)this.style.get(synthContext, "ComboBox.popupInsets");
/* 131 */       this.useListColors = this.style.getBoolean(synthContext, "ComboBox.rendererUseListColors", true);
/*     */       
/* 133 */       this.buttonWhenNotEditable = this.style.getBoolean(synthContext, "ComboBox.buttonWhenNotEditable", false);
/*     */       
/* 135 */       this.pressedWhenPopupVisible = this.style.getBoolean(synthContext, "ComboBox.pressedWhenPopupVisible", false);
/*     */       
/* 137 */       this.squareButton = this.style.getBoolean(synthContext, "ComboBox.squareButton", true);
/*     */ 
/*     */       
/* 140 */       if (synthStyle != null) {
/* 141 */         uninstallKeyboardActions();
/* 142 */         installKeyboardActions();
/*     */       } 
/* 144 */       this.forceOpaque = this.style.getBoolean(synthContext, "ComboBox.forceOpaque", false);
/*     */     } 
/*     */     
/* 147 */     synthContext.dispose();
/*     */     
/* 149 */     if (this.listBox != null) {
/* 150 */       SynthLookAndFeel.updateStyles(this.listBox);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 159 */     this.comboBox.addPropertyChangeListener(this);
/* 160 */     this.comboBox.addMouseListener(this.buttonHandler);
/* 161 */     this.editorFocusHandler = new EditorFocusHandler(this.comboBox);
/* 162 */     super.installListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 170 */     if (this.popup instanceof SynthComboPopup) {
/* 171 */       ((SynthComboPopup)this.popup).removePopupMenuListener(this.buttonHandler);
/*     */     }
/* 173 */     super.uninstallUI(paramJComponent);
/* 174 */     this.buttonHandler = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 182 */     SynthContext synthContext = getContext(this.comboBox, 1);
/*     */     
/* 184 */     this.style.uninstallDefaults(synthContext);
/* 185 */     synthContext.dispose();
/* 186 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 194 */     this.editorFocusHandler.unregister();
/* 195 */     this.comboBox.removePropertyChangeListener(this);
/* 196 */     this.comboBox.removeMouseListener(this.buttonHandler);
/* 197 */     this.buttonHandler.pressed = false;
/* 198 */     this.buttonHandler.over = false;
/* 199 */     super.uninstallListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 207 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 211 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 221 */     if (!(paramJComponent instanceof JComboBox)) return SynthLookAndFeel.getComponentState(paramJComponent);
/*     */     
/* 223 */     JComboBox jComboBox = (JComboBox)paramJComponent;
/* 224 */     if (shouldActLikeButton()) {
/* 225 */       int j = 1;
/* 226 */       if (!paramJComponent.isEnabled()) {
/* 227 */         j = 8;
/*     */       }
/* 229 */       if (this.buttonHandler.isPressed()) {
/* 230 */         j |= 0x4;
/*     */       }
/* 232 */       if (this.buttonHandler.isRollover()) {
/* 233 */         j |= 0x2;
/*     */       }
/* 235 */       if (jComboBox.isFocusOwner()) {
/* 236 */         j |= 0x100;
/*     */       }
/* 238 */       return j;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 243 */     int i = SynthLookAndFeel.getComponentState(paramJComponent);
/* 244 */     if (jComboBox.isEditable() && jComboBox
/* 245 */       .getEditor().getEditorComponent().isFocusOwner()) {
/* 246 */       i |= 0x100;
/*     */     }
/* 248 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComboPopup createPopup() {
/* 257 */     SynthComboPopup synthComboPopup = new SynthComboPopup(this.comboBox);
/* 258 */     synthComboPopup.addPopupMenuListener(this.buttonHandler);
/* 259 */     return synthComboPopup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ListCellRenderer createRenderer() {
/* 267 */     return new SynthComboBoxRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComboBoxEditor createEditor() {
/* 275 */     return new SynthComboBoxEditor();
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
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 287 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 288 */       updateStyle(this.comboBox);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createArrowButton() {
/* 297 */     SynthArrowButton synthArrowButton = new SynthArrowButton(5);
/* 298 */     synthArrowButton.setName("ComboBox.arrowButton");
/* 299 */     synthArrowButton.setModel(this.buttonHandler);
/* 300 */     return synthArrowButton;
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 320 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 322 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 323 */     synthContext.getPainter().paintComboBoxBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 324 */         .getWidth(), paramJComponent.getHeight());
/* 325 */     paint(synthContext, paramGraphics);
/* 326 */     synthContext.dispose();
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 340 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 342 */     paint(synthContext, paramGraphics);
/* 343 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 354 */     this.hasFocus = this.comboBox.hasFocus();
/* 355 */     if (!this.comboBox.isEditable()) {
/* 356 */       Rectangle rectangle = rectangleForCurrentValue();
/* 357 */       paintCurrentValue(paramGraphics, rectangle, this.hasFocus);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 367 */     paramSynthContext.getPainter().paintComboBoxBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintCurrentValue(Graphics paramGraphics, Rectangle paramRectangle, boolean paramBoolean) {
/* 375 */     ListCellRenderer<? super Object> listCellRenderer = this.comboBox.getRenderer();
/*     */ 
/*     */     
/* 378 */     Component component = listCellRenderer.getListCellRendererComponent(this.listBox, this.comboBox
/* 379 */         .getSelectedItem(), -1, false, false);
/*     */ 
/*     */     
/* 382 */     boolean bool1 = false;
/* 383 */     if (component instanceof javax.swing.JPanel) {
/* 384 */       bool1 = true;
/*     */     }
/*     */     
/* 387 */     if (component instanceof UIResource) {
/* 388 */       component.setName("ComboBox.renderer");
/*     */     }
/*     */     
/* 391 */     boolean bool2 = (this.forceOpaque && component instanceof JComponent) ? true : false;
/* 392 */     if (bool2) {
/* 393 */       ((JComponent)component).setOpaque(false);
/*     */     }
/*     */     
/* 396 */     int i = paramRectangle.x, j = paramRectangle.y, k = paramRectangle.width, m = paramRectangle.height;
/* 397 */     if (this.padding != null) {
/* 398 */       i = paramRectangle.x + this.padding.left;
/* 399 */       j = paramRectangle.y + this.padding.top;
/* 400 */       k = paramRectangle.width - this.padding.left + this.padding.right;
/* 401 */       m = paramRectangle.height - this.padding.top + this.padding.bottom;
/*     */     } 
/*     */     
/* 404 */     this.currentValuePane.paintComponent(paramGraphics, component, this.comboBox, i, j, k, m, bool1);
/*     */     
/* 406 */     if (bool2) {
/* 407 */       ((JComponent)component).setOpaque(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldActLikeButton() {
/* 417 */     return (this.buttonWhenNotEditable && !this.comboBox.isEditable());
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
/*     */   protected Dimension getDefaultSize() {
/* 434 */     SynthComboBoxRenderer synthComboBoxRenderer = new SynthComboBoxRenderer();
/* 435 */     Dimension dimension = getSizeForComponent(synthComboBoxRenderer.getListCellRendererComponent(this.listBox, " ", -1, false, false));
/* 436 */     return new Dimension(dimension.width, dimension.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SynthComboBoxRenderer
/*     */     extends JLabel
/*     */     implements ListCellRenderer<Object>, UIResource
/*     */   {
/*     */     public SynthComboBoxRenderer() {
/* 449 */       setText(" ");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 458 */       String str = super.getName();
/*     */       
/* 460 */       return (str == null) ? "ComboBox.renderer" : str;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 466 */       setName("ComboBox.listRenderer");
/* 467 */       SynthLookAndFeel.resetSelectedUI();
/* 468 */       if (param1Boolean1) {
/* 469 */         setBackground(param1JList.getSelectionBackground());
/* 470 */         setForeground(param1JList.getSelectionForeground());
/* 471 */         if (!SynthComboBoxUI.this.useListColors) {
/* 472 */           SynthLookAndFeel.setSelectedUI(
/* 473 */               (SynthLabelUI)SynthLookAndFeel.getUIOfType(getUI(), SynthLabelUI.class), param1Boolean1, param1Boolean2, param1JList
/*     */               
/* 475 */               .isEnabled(), false);
/*     */         }
/*     */       } else {
/* 478 */         setBackground(param1JList.getBackground());
/* 479 */         setForeground(param1JList.getForeground());
/*     */       } 
/*     */       
/* 482 */       setFont(param1JList.getFont());
/*     */       
/* 484 */       if (param1Object instanceof Icon) {
/* 485 */         setIcon((Icon)param1Object);
/* 486 */         setText("");
/*     */       } else {
/* 488 */         String str = (param1Object == null) ? " " : param1Object.toString();
/*     */         
/* 490 */         if ("".equals(str)) {
/* 491 */           str = " ";
/*     */         }
/* 493 */         setText(str);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 501 */       if (SynthComboBoxUI.this.comboBox != null) {
/* 502 */         setEnabled(SynthComboBoxUI.this.comboBox.isEnabled());
/* 503 */         setComponentOrientation(SynthComboBoxUI.this.comboBox.getComponentOrientation());
/*     */       } 
/*     */       
/* 506 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 511 */       super.paint(param1Graphics);
/* 512 */       SynthLookAndFeel.resetSelectedUI();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SynthComboBoxEditor
/*     */     extends BasicComboBoxEditor.UIResource {
/*     */     private SynthComboBoxEditor() {}
/*     */     
/*     */     public JTextField createEditorComponent() {
/* 521 */       JTextField jTextField = new JTextField("", 9);
/* 522 */       jTextField.setName("ComboBox.textField");
/* 523 */       return jTextField;
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
/*     */   private final class ButtonHandler
/*     */     extends DefaultButtonModel
/*     */     implements MouseListener, PopupMenuListener
/*     */   {
/*     */     private boolean over;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean pressed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ButtonHandler() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void updatePressed(boolean param1Boolean) {
/* 563 */       this.pressed = (param1Boolean && isEnabled());
/* 564 */       if (SynthComboBoxUI.this.shouldActLikeButton()) {
/* 565 */         SynthComboBoxUI.this.comboBox.repaint();
/*     */       }
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
/*     */     private void updateOver(boolean param1Boolean) {
/* 578 */       boolean bool1 = isRollover();
/* 579 */       this.over = (param1Boolean && isEnabled());
/* 580 */       boolean bool2 = isRollover();
/* 581 */       if (SynthComboBoxUI.this.shouldActLikeButton() && bool1 != bool2) {
/* 582 */         SynthComboBoxUI.this.comboBox.repaint();
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isPressed() {
/* 600 */       boolean bool = SynthComboBoxUI.this.shouldActLikeButton() ? this.pressed : super.isPressed();
/* 601 */       return (bool || (SynthComboBoxUI.this.pressedWhenPopupVisible && SynthComboBoxUI.this.comboBox.isPopupVisible()));
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
/*     */ 
/*     */     
/*     */     public boolean isArmed() {
/* 615 */       boolean bool = (SynthComboBoxUI.this.shouldActLikeButton() || (SynthComboBoxUI.this.pressedWhenPopupVisible && SynthComboBoxUI.this.comboBox.isPopupVisible())) ? true : false;
/* 616 */       return bool ? isPressed() : super.isArmed();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRollover() {
/* 627 */       return SynthComboBoxUI.this.shouldActLikeButton() ? this.over : super.isRollover();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPressed(boolean param1Boolean) {
/* 637 */       super.setPressed(param1Boolean);
/* 638 */       updatePressed(param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRollover(boolean param1Boolean) {
/* 648 */       super.setRollover(param1Boolean);
/* 649 */       updateOver(param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 658 */       updateOver(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 663 */       updateOver(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 668 */       updatePressed(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 673 */       updatePressed(false);
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
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void popupMenuCanceled(PopupMenuEvent param1PopupMenuEvent) {
/* 695 */       if (SynthComboBoxUI.this.shouldActLikeButton() || SynthComboBoxUI.this.pressedWhenPopupVisible) {
/* 696 */         SynthComboBoxUI.this.comboBox.repaint();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void popupMenuWillBecomeVisible(PopupMenuEvent param1PopupMenuEvent) {}
/*     */ 
/*     */     
/*     */     public void popupMenuWillBecomeInvisible(PopupMenuEvent param1PopupMenuEvent) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EditorFocusHandler
/*     */     implements FocusListener, PropertyChangeListener
/*     */   {
/*     */     private JComboBox comboBox;
/* 712 */     private ComboBoxEditor editor = null;
/* 713 */     private Component editorComponent = null;
/*     */     
/*     */     private EditorFocusHandler(JComboBox param1JComboBox) {
/* 716 */       this.comboBox = param1JComboBox;
/* 717 */       this.editor = param1JComboBox.getEditor();
/* 718 */       if (this.editor != null) {
/* 719 */         this.editorComponent = this.editor.getEditorComponent();
/* 720 */         if (this.editorComponent != null) {
/* 721 */           this.editorComponent.addFocusListener(this);
/*     */         }
/*     */       } 
/* 724 */       param1JComboBox.addPropertyChangeListener("editor", this);
/*     */     }
/*     */     
/*     */     public void unregister() {
/* 728 */       this.comboBox.removePropertyChangeListener(this);
/* 729 */       if (this.editorComponent != null) {
/* 730 */         this.editorComponent.removeFocusListener(this);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void focusGained(FocusEvent param1FocusEvent) {
/* 737 */       this.comboBox.repaint();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void focusLost(FocusEvent param1FocusEvent) {
/* 743 */       this.comboBox.repaint();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 753 */       ComboBoxEditor comboBoxEditor = this.comboBox.getEditor();
/* 754 */       if (this.editor != comboBoxEditor) {
/* 755 */         if (this.editorComponent != null) {
/* 756 */           this.editorComponent.removeFocusListener(this);
/*     */         }
/* 758 */         this.editor = comboBoxEditor;
/* 759 */         if (this.editor != null) {
/* 760 */           this.editorComponent = this.editor.getEditorComponent();
/* 761 */           if (this.editorComponent != null)
/* 762 */             this.editorComponent.addFocusListener(this); 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthComboBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */