/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.swing.plaf.ButtonUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JToggleButton
/*     */   extends AbstractButton
/*     */   implements Accessible
/*     */ {
/*     */   private static final String uiClassID = "ToggleButtonUI";
/*     */   
/*     */   public JToggleButton() {
/*  92 */     this((String)null, (Icon)null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JToggleButton(Icon paramIcon) {
/* 102 */     this((String)null, paramIcon, false);
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
/*     */   public JToggleButton(Icon paramIcon, boolean paramBoolean) {
/* 114 */     this((String)null, paramIcon, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JToggleButton(String paramString) {
/* 123 */     this(paramString, (Icon)null, false);
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
/*     */   public JToggleButton(String paramString, boolean paramBoolean) {
/* 135 */     this(paramString, (Icon)null, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JToggleButton(Action paramAction) {
/* 145 */     this();
/* 146 */     setAction(paramAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JToggleButton(String paramString, Icon paramIcon) {
/* 157 */     this(paramString, paramIcon, false);
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
/*     */   public JToggleButton(String paramString, Icon paramIcon, boolean paramBoolean) {
/* 171 */     setModel(new ToggleButtonModel());
/*     */     
/* 173 */     this.model.setSelected(paramBoolean);
/*     */ 
/*     */     
/* 176 */     init(paramString, paramIcon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 185 */     setUI((ButtonUI)UIManager.getUI(this));
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
/*     */   public String getUIClassID() {
/* 199 */     return "ToggleButtonUI";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean shouldUpdateSelectedStateFromAction() {
/* 208 */     return true;
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
/*     */   public static class ToggleButtonModel
/*     */     extends DefaultButtonModel
/*     */   {
/*     */     public boolean isSelected() {
/* 240 */       return ((this.stateMask & 0x2) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setSelected(boolean param1Boolean) {
/* 251 */       ButtonGroup buttonGroup = getGroup();
/* 252 */       if (buttonGroup != null) {
/*     */         
/* 254 */         buttonGroup.setSelected(this, param1Boolean);
/* 255 */         param1Boolean = buttonGroup.isSelected(this);
/*     */       } 
/*     */       
/* 258 */       if (isSelected() == param1Boolean) {
/*     */         return;
/*     */       }
/*     */       
/* 262 */       if (param1Boolean) {
/* 263 */         this.stateMask |= 0x2;
/*     */       } else {
/* 265 */         this.stateMask &= 0xFFFFFFFD;
/*     */       } 
/*     */ 
/*     */       
/* 269 */       fireStateChanged();
/*     */ 
/*     */       
/* 272 */       fireItemStateChanged(new ItemEvent(this, 701, this, 
/*     */ 
/*     */ 
/*     */             
/* 276 */             isSelected() ? 1 : 2));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPressed(boolean param1Boolean) {
/* 284 */       if (isPressed() == param1Boolean || !isEnabled()) {
/*     */         return;
/*     */       }
/*     */       
/* 288 */       if (!param1Boolean && isArmed()) {
/* 289 */         setSelected(!isSelected());
/*     */       }
/*     */       
/* 292 */       if (param1Boolean) {
/* 293 */         this.stateMask |= 0x4;
/*     */       } else {
/* 295 */         this.stateMask &= 0xFFFFFFFB;
/*     */       } 
/*     */       
/* 298 */       fireStateChanged();
/*     */       
/* 300 */       if (!isPressed() && isArmed()) {
/* 301 */         int i = 0;
/* 302 */         AWTEvent aWTEvent = EventQueue.getCurrentEvent();
/* 303 */         if (aWTEvent instanceof InputEvent) {
/* 304 */           i = ((InputEvent)aWTEvent).getModifiers();
/* 305 */         } else if (aWTEvent instanceof ActionEvent) {
/* 306 */           i = ((ActionEvent)aWTEvent).getModifiers();
/*     */         } 
/* 308 */         fireActionPerformed(new ActionEvent(this, 1001, 
/*     */               
/* 310 */               getActionCommand(), 
/* 311 */               EventQueue.getMostRecentEventTime(), i));
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 324 */     paramObjectOutputStream.defaultWriteObject();
/* 325 */     if (getUIClassID().equals("ToggleButtonUI")) {
/* 326 */       byte b = JComponent.getWriteObjCounter(this);
/* 327 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 328 */       if (b == 0 && this.ui != null) {
/* 329 */         this.ui.installUI(this);
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
/*     */   protected String paramString() {
/* 345 */     return super.paramString();
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 366 */     if (this.accessibleContext == null) {
/* 367 */       this.accessibleContext = new AccessibleJToggleButton();
/*     */     }
/* 369 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJToggleButton
/*     */     extends AbstractButton.AccessibleAbstractButton
/*     */     implements ItemListener
/*     */   {
/*     */     public AccessibleJToggleButton() {
/* 392 */       JToggleButton.this.addItemListener(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void itemStateChanged(ItemEvent param1ItemEvent) {
/* 400 */       JToggleButton jToggleButton = (JToggleButton)param1ItemEvent.getSource();
/* 401 */       if (JToggleButton.this.accessibleContext != null) {
/* 402 */         if (jToggleButton.isSelected()) {
/* 403 */           JToggleButton.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.CHECKED);
/*     */         }
/*     */         else {
/*     */           
/* 407 */           JToggleButton.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.CHECKED, null);
/*     */         } 
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
/*     */     public AccessibleRole getAccessibleRole() {
/* 421 */       return AccessibleRole.TOGGLE_BUTTON;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JToggleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */