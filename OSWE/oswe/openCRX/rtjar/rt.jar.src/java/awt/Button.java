/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.peer.ButtonPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleAction;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Button
/*     */   extends Component
/*     */   implements Accessible
/*     */ {
/*     */   String label;
/*     */   String actionCommand;
/*     */   transient ActionListener actionListener;
/*     */   private static final String base = "button";
/* 109 */   private static int nameCounter = 0;
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -8774683716313001058L;
/*     */ 
/*     */   
/*     */   private int buttonSerializedDataVersion;
/*     */ 
/*     */   
/*     */   static {
/* 119 */     Toolkit.loadLibraries();
/* 120 */     if (!GraphicsEnvironment.isHeadless()) {
/* 121 */       initIDs();
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
/*     */   public Button() throws HeadlessException {
/* 139 */     this("");
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
/*     */ 
/*     */ 
/*     */   
/*     */   String constructComponentName() {
/*     */     synchronized (Button.class) {
/*     */       return "button" + nameCounter++;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify() {
/*     */     synchronized (getTreeLock()) {
/*     */       if (this.peer == null) {
/*     */         this.peer = getToolkit().createButton(this);
/*     */       }
/*     */       super.addNotify();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabel() {
/*     */     return this.label;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabel(String paramString) {
/*     */     boolean bool = false;
/*     */     synchronized (this) {
/*     */       if (paramString != this.label && (this.label == null || !this.label.equals(paramString))) {
/*     */         this.label = paramString;
/*     */         ButtonPeer buttonPeer = (ButtonPeer)this.peer;
/*     */         if (buttonPeer != null) {
/*     */           buttonPeer.setLabel(paramString);
/*     */         }
/*     */         bool = true;
/*     */       } 
/*     */     } 
/*     */     if (bool) {
/*     */       invalidateIfValid();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Button(String paramString) throws HeadlessException {
/* 434 */     this.buttonSerializedDataVersion = 1;
/*     */     GraphicsEnvironment.checkHeadless();
/*     */     this.label = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActionCommand(String paramString) {
/*     */     this.actionCommand = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getActionCommand() {
/*     */     return (this.actionCommand == null) ? this.label : this.actionCommand;
/*     */   }
/*     */   
/*     */   public synchronized void addActionListener(ActionListener paramActionListener) {
/*     */     if (paramActionListener == null) {
/*     */       return;
/*     */     }
/*     */     this.actionListener = AWTEventMulticaster.add(this.actionListener, paramActionListener);
/*     */     this.newEventsOnly = true;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 458 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 460 */     AWTEventMulticaster.save(paramObjectOutputStream, "actionL", this.actionListener);
/* 461 */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeActionListener(ActionListener paramActionListener) {
/*     */     if (paramActionListener == null) {
/*     */       return;
/*     */     }
/*     */     this.actionListener = AWTEventMulticaster.remove(this.actionListener, paramActionListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException {
/* 483 */     GraphicsEnvironment.checkHeadless();
/* 484 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     Object object;
/* 487 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 488 */       String str = ((String)object).intern();
/*     */       
/* 490 */       if ("actionL" == str) {
/* 491 */         addActionListener((ActionListener)paramObjectInputStream.readObject());
/*     */         continue;
/*     */       } 
/* 494 */       paramObjectInputStream.readObject();
/*     */     }  } public synchronized ActionListener[] getActionListeners() {
/*     */     return getListeners(ActionListener.class);
/*     */   } public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/*     */     ActionListener actionListener = null;
/*     */     if (paramClass == ActionListener.class) {
/*     */       actionListener = this.actionListener;
/*     */     } else {
/*     */       return super.getListeners(paramClass);
/*     */     } 
/*     */     return AWTEventMulticaster.getListeners(actionListener, paramClass);
/*     */   } boolean eventEnabled(AWTEvent paramAWTEvent) {
/*     */     if (paramAWTEvent.id == 1001) {
/*     */       if ((this.eventMask & 0x80L) != 0L || this.actionListener != null)
/*     */         return true; 
/*     */       return false;
/*     */     } 
/*     */     return super.eventEnabled(paramAWTEvent);
/*     */   } protected void processEvent(AWTEvent paramAWTEvent) {
/*     */     if (paramAWTEvent instanceof ActionEvent) {
/*     */       processActionEvent((ActionEvent)paramAWTEvent);
/*     */       return;
/*     */     } 
/*     */     super.processEvent(paramAWTEvent);
/*     */   } public AccessibleContext getAccessibleContext() {
/* 519 */     if (this.accessibleContext == null) {
/* 520 */       this.accessibleContext = new AccessibleAWTButton();
/*     */     }
/* 522 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processActionEvent(ActionEvent paramActionEvent) {
/*     */     ActionListener actionListener = this.actionListener;
/*     */     if (actionListener != null) {
/*     */       actionListener.actionPerformed(paramActionEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   protected String paramString() {
/*     */     return super.paramString() + ",label=" + this.label;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   protected class AccessibleAWTButton
/*     */     extends Component.AccessibleAWTComponent
/*     */     implements AccessibleAction, AccessibleValue
/*     */   {
/*     */     private static final long serialVersionUID = -5932203980244017102L;
/*     */     
/*     */     public String getAccessibleName() {
/* 546 */       if (this.accessibleName != null) {
/* 547 */         return this.accessibleName;
/*     */       }
/* 549 */       if (Button.this.getLabel() == null) {
/* 550 */         return super.getAccessibleName();
/*     */       }
/* 552 */       return Button.this.getLabel();
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
/*     */     public AccessibleAction getAccessibleAction() {
/* 566 */       return this;
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
/*     */     public AccessibleValue getAccessibleValue() {
/* 578 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAccessibleActionCount() {
/* 589 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAccessibleActionDescription(int param1Int) {
/* 598 */       if (param1Int == 0)
/*     */       {
/* 600 */         return "click";
/*     */       }
/* 602 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean doAccessibleAction(int param1Int) {
/* 613 */       if (param1Int == 0) {
/*     */         
/* 615 */         Toolkit.getEventQueue().postEvent(new ActionEvent(Button.this, 1001, Button.this
/*     */ 
/*     */               
/* 618 */               .getActionCommand()));
/* 619 */         return true;
/*     */       } 
/* 621 */       return false;
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
/*     */     public Number getCurrentAccessibleValue() {
/* 633 */       return Integer.valueOf(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean setCurrentAccessibleValue(Number param1Number) {
/* 642 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getMinimumAccessibleValue() {
/* 651 */       return Integer.valueOf(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getMaximumAccessibleValue() {
/* 660 */       return Integer.valueOf(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 671 */       return AccessibleRole.PUSH_BUTTON;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */