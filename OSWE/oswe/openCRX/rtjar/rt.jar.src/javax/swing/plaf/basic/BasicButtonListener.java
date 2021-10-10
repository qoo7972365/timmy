/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.ComponentInputMapUIResource;
/*     */ import sun.swing.DefaultLookup;
/*     */ import sun.swing.UIAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicButtonListener
/*     */   implements MouseListener, MouseMotionListener, FocusListener, ChangeListener, PropertyChangeListener
/*     */ {
/*  49 */   private long lastPressedTimestamp = -1L;
/*     */ 
/*     */   
/*     */   private boolean shouldDiscardRelease = false;
/*     */ 
/*     */   
/*     */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  56 */     paramLazyActionMap.put(new Actions("pressed"));
/*  57 */     paramLazyActionMap.put(new Actions("released"));
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicButtonListener(AbstractButton paramAbstractButton) {}
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/*  65 */     String str = paramPropertyChangeEvent.getPropertyName();
/*  66 */     if (str == "mnemonic") {
/*  67 */       updateMnemonicBinding((AbstractButton)paramPropertyChangeEvent.getSource());
/*     */     }
/*  69 */     else if (str == "contentAreaFilled") {
/*  70 */       checkOpacity((AbstractButton)paramPropertyChangeEvent.getSource());
/*     */     }
/*  72 */     else if (str == "text" || "font" == str || "foreground" == str) {
/*     */       
/*  74 */       AbstractButton abstractButton = (AbstractButton)paramPropertyChangeEvent.getSource();
/*  75 */       BasicHTML.updateRenderer(abstractButton, abstractButton.getText());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void checkOpacity(AbstractButton paramAbstractButton) {
/*  80 */     paramAbstractButton.setOpaque(paramAbstractButton.isContentAreaFilled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installKeyboardActions(JComponent paramJComponent) {
/*  88 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/*     */     
/*  90 */     updateMnemonicBinding(abstractButton);
/*     */     
/*  92 */     LazyActionMap.installLazyActionMap(paramJComponent, BasicButtonListener.class, "Button.actionMap");
/*     */ 
/*     */     
/*  95 */     InputMap inputMap = getInputMap(0, paramJComponent);
/*     */     
/*  97 */     SwingUtilities.replaceUIInputMap(paramJComponent, 0, inputMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallKeyboardActions(JComponent paramJComponent) {
/* 104 */     SwingUtilities.replaceUIInputMap(paramJComponent, 2, null);
/*     */     
/* 106 */     SwingUtilities.replaceUIInputMap(paramJComponent, 0, null);
/* 107 */     SwingUtilities.replaceUIActionMap(paramJComponent, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InputMap getInputMap(int paramInt, JComponent paramJComponent) {
/* 115 */     if (paramInt == 0) {
/* 116 */       BasicButtonUI basicButtonUI = (BasicButtonUI)BasicLookAndFeel.getUIOfType(((AbstractButton)paramJComponent)
/* 117 */           .getUI(), BasicButtonUI.class);
/* 118 */       if (basicButtonUI != null) {
/* 119 */         return (InputMap)DefaultLookup.get(paramJComponent, basicButtonUI, basicButtonUI
/* 120 */             .getPropertyPrefix() + "focusInputMap");
/*     */       }
/*     */     } 
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateMnemonicBinding(AbstractButton paramAbstractButton) {
/* 131 */     int i = paramAbstractButton.getMnemonic();
/* 132 */     if (i != 0) {
/* 133 */       InputMap inputMap = SwingUtilities.getUIInputMap(paramAbstractButton, 2);
/*     */ 
/*     */       
/* 136 */       if (inputMap == null) {
/* 137 */         inputMap = new ComponentInputMapUIResource(paramAbstractButton);
/* 138 */         SwingUtilities.replaceUIInputMap(paramAbstractButton, 2, inputMap);
/*     */       } 
/*     */       
/* 141 */       inputMap.clear();
/* 142 */       inputMap.put(KeyStroke.getKeyStroke(i, BasicLookAndFeel.getFocusAcceleratorKeyMask(), false), "pressed");
/*     */       
/* 144 */       inputMap.put(KeyStroke.getKeyStroke(i, BasicLookAndFeel.getFocusAcceleratorKeyMask(), true), "released");
/*     */       
/* 146 */       inputMap.put(KeyStroke.getKeyStroke(i, 0, true), "released");
/*     */     } else {
/*     */       
/* 149 */       InputMap inputMap = SwingUtilities.getUIInputMap(paramAbstractButton, 2);
/*     */       
/* 151 */       if (inputMap != null) {
/* 152 */         inputMap.clear();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stateChanged(ChangeEvent paramChangeEvent) {
/* 158 */     AbstractButton abstractButton = (AbstractButton)paramChangeEvent.getSource();
/* 159 */     abstractButton.repaint();
/*     */   }
/*     */   
/*     */   public void focusGained(FocusEvent paramFocusEvent) {
/* 163 */     AbstractButton abstractButton = (AbstractButton)paramFocusEvent.getSource();
/* 164 */     if (abstractButton instanceof JButton && ((JButton)abstractButton).isDefaultCapable()) {
/* 165 */       JRootPane jRootPane = abstractButton.getRootPane();
/* 166 */       if (jRootPane != null) {
/* 167 */         BasicButtonUI basicButtonUI = (BasicButtonUI)BasicLookAndFeel.getUIOfType(abstractButton
/* 168 */             .getUI(), BasicButtonUI.class);
/* 169 */         if (basicButtonUI != null) if (DefaultLookup.getBoolean(abstractButton, basicButtonUI, basicButtonUI
/* 170 */               .getPropertyPrefix() + "defaultButtonFollowsFocus", true)) {
/*     */             
/* 172 */             jRootPane.putClientProperty("temporaryDefaultButton", abstractButton);
/* 173 */             jRootPane.setDefaultButton((JButton)abstractButton);
/* 174 */             jRootPane.putClientProperty("temporaryDefaultButton", (Object)null);
/*     */           }  
/*     */       } 
/*     */     } 
/* 178 */     abstractButton.repaint();
/*     */   }
/*     */   
/*     */   public void focusLost(FocusEvent paramFocusEvent) {
/* 182 */     AbstractButton abstractButton = (AbstractButton)paramFocusEvent.getSource();
/* 183 */     JRootPane jRootPane = abstractButton.getRootPane();
/* 184 */     if (jRootPane != null) {
/* 185 */       JButton jButton = (JButton)jRootPane.getClientProperty("initialDefaultButton");
/* 186 */       if (abstractButton != jButton) {
/* 187 */         BasicButtonUI basicButtonUI = (BasicButtonUI)BasicLookAndFeel.getUIOfType(abstractButton
/* 188 */             .getUI(), BasicButtonUI.class);
/* 189 */         if (basicButtonUI != null) if (DefaultLookup.getBoolean(abstractButton, basicButtonUI, basicButtonUI
/* 190 */               .getPropertyPrefix() + "defaultButtonFollowsFocus", true))
/*     */           {
/* 192 */             jRootPane.setDefaultButton(jButton);
/*     */           }
/*     */       
/*     */       } 
/*     */     } 
/* 197 */     ButtonModel buttonModel = abstractButton.getModel();
/* 198 */     buttonModel.setPressed(false);
/* 199 */     buttonModel.setArmed(false);
/* 200 */     abstractButton.repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseMoved(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 214 */     if (SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/* 215 */       AbstractButton abstractButton = (AbstractButton)paramMouseEvent.getSource();
/*     */       
/* 217 */       if (abstractButton.contains(paramMouseEvent.getX(), paramMouseEvent.getY())) {
/* 218 */         long l1 = abstractButton.getMultiClickThreshhold();
/* 219 */         long l2 = this.lastPressedTimestamp;
/* 220 */         long l3 = this.lastPressedTimestamp = paramMouseEvent.getWhen();
/* 221 */         if (l2 != -1L && l3 - l2 < l1) {
/* 222 */           this.shouldDiscardRelease = true;
/*     */           
/*     */           return;
/*     */         } 
/* 226 */         ButtonModel buttonModel = abstractButton.getModel();
/* 227 */         if (!buttonModel.isEnabled()) {
/*     */           return;
/*     */         }
/*     */         
/* 231 */         if (!buttonModel.isArmed())
/*     */         {
/* 233 */           buttonModel.setArmed(true);
/*     */         }
/* 235 */         buttonModel.setPressed(true);
/* 236 */         if (!abstractButton.hasFocus() && abstractButton.isRequestFocusEnabled()) {
/* 237 */           abstractButton.requestFocus();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseReleased(MouseEvent paramMouseEvent) {
/* 244 */     if (SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/*     */       
/* 246 */       if (this.shouldDiscardRelease) {
/* 247 */         this.shouldDiscardRelease = false;
/*     */         return;
/*     */       } 
/* 250 */       AbstractButton abstractButton = (AbstractButton)paramMouseEvent.getSource();
/* 251 */       ButtonModel buttonModel = abstractButton.getModel();
/* 252 */       buttonModel.setPressed(false);
/* 253 */       buttonModel.setArmed(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {
/* 258 */     AbstractButton abstractButton = (AbstractButton)paramMouseEvent.getSource();
/* 259 */     ButtonModel buttonModel = abstractButton.getModel();
/* 260 */     if (abstractButton.isRolloverEnabled() && !SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/* 261 */       buttonModel.setRollover(true);
/*     */     }
/* 263 */     if (buttonModel.isPressed())
/* 264 */       buttonModel.setArmed(true); 
/*     */   }
/*     */   
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {
/* 268 */     AbstractButton abstractButton = (AbstractButton)paramMouseEvent.getSource();
/* 269 */     ButtonModel buttonModel = abstractButton.getModel();
/* 270 */     if (abstractButton.isRolloverEnabled()) {
/* 271 */       buttonModel.setRollover(false);
/*     */     }
/* 273 */     buttonModel.setArmed(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Actions
/*     */     extends UIAction
/*     */   {
/*     */     private static final String PRESS = "pressed";
/*     */     
/*     */     private static final String RELEASE = "released";
/*     */ 
/*     */     
/*     */     Actions(String param1String) {
/* 287 */       super(param1String);
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 291 */       AbstractButton abstractButton = (AbstractButton)param1ActionEvent.getSource();
/* 292 */       String str = getName();
/* 293 */       if (str == "pressed") {
/* 294 */         ButtonModel buttonModel = abstractButton.getModel();
/* 295 */         buttonModel.setArmed(true);
/* 296 */         buttonModel.setPressed(true);
/* 297 */         if (!abstractButton.hasFocus()) {
/* 298 */           abstractButton.requestFocus();
/*     */         }
/*     */       }
/* 301 */       else if (str == "released") {
/* 302 */         ButtonModel buttonModel = abstractButton.getModel();
/* 303 */         buttonModel.setPressed(false);
/* 304 */         buttonModel.setArmed(false);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEnabled(Object param1Object) {
/* 309 */       if (param1Object != null && param1Object instanceof AbstractButton && 
/* 310 */         !((AbstractButton)param1Object).getModel().isEnabled()) {
/* 311 */         return false;
/*     */       }
/* 313 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicButtonListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */