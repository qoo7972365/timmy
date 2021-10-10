/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.peer.CheckboxMenuItemPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleAction;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleValue;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CheckboxMenuItem
/*     */   extends MenuItem
/*     */   implements ItemSelectable, Accessible
/*     */ {
/*     */   static {
/*  68 */     Toolkit.loadLibraries();
/*  69 */     if (!GraphicsEnvironment.isHeadless()) {
/*  70 */       initIDs();
/*     */     }
/*     */     
/*  73 */     AWTAccessor.setCheckboxMenuItemAccessor(new AWTAccessor.CheckboxMenuItemAccessor()
/*     */         {
/*     */           public boolean getState(CheckboxMenuItem param1CheckboxMenuItem) {
/*  76 */             return param1CheckboxMenuItem.state;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean state = false;
/*     */ 
/*     */   
/*     */   transient ItemListener itemListener;
/*     */ 
/*     */   
/*     */   private static final String base = "chkmenuitem";
/*     */ 
/*     */   
/*  92 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 6190621106981774043L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int checkboxMenuItemSerializedDataVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckboxMenuItem() throws HeadlessException {
/* 108 */     this("", false);
/*     */   } String constructComponentName() {
/*     */     synchronized (CheckboxMenuItem.class) {
/*     */       return "chkmenuitem" + nameCounter++;
/*     */     } 
/*     */   }
/*     */   public void addNotify() {
/*     */     synchronized (getTreeLock()) {
/*     */       if (this.peer == null)
/*     */         this.peer = Toolkit.getDefaultToolkit().createCheckboxMenuItem(this); 
/*     */       super.addNotify();
/*     */     } 
/*     */   }
/*     */   public CheckboxMenuItem(String paramString) throws HeadlessException {
/* 122 */     this(paramString, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getState() {
/*     */     return this.state;
/*     */   }
/*     */   
/*     */   public synchronized void setState(boolean paramBoolean) {
/*     */     this.state = paramBoolean;
/*     */     CheckboxMenuItemPeer checkboxMenuItemPeer = (CheckboxMenuItemPeer)this.peer;
/*     */     if (checkboxMenuItemPeer != null) {
/*     */       checkboxMenuItemPeer.setState(paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   public CheckboxMenuItem(String paramString, boolean paramBoolean) throws HeadlessException {
/* 139 */     super(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 426 */     this.checkboxMenuItemSerializedDataVersion = 1;
/*     */     this.state = paramBoolean;
/*     */   }
/*     */   public synchronized Object[] getSelectedObjects() {
/*     */     if (this.state) {
/*     */       Object[] arrayOfObject = new Object[1];
/*     */       arrayOfObject[0] = this.label;
/*     */       return arrayOfObject;
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   public synchronized void addItemListener(ItemListener paramItemListener) {
/*     */     if (paramItemListener == null)
/*     */       return; 
/*     */     this.itemListener = AWTEventMulticaster.add(this.itemListener, paramItemListener);
/*     */     this.newEventsOnly = true;
/*     */   }
/*     */   public synchronized void removeItemListener(ItemListener paramItemListener) {
/*     */     if (paramItemListener == null)
/*     */       return; 
/*     */     this.itemListener = AWTEventMulticaster.remove(this.itemListener, paramItemListener);
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 450 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 452 */     AWTEventMulticaster.save(paramObjectOutputStream, "itemL", this.itemListener);
/* 453 */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ItemListener[] getItemListeners() {
/*     */     return getListeners(ItemListener.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 471 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     Object object;
/* 474 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 475 */       String str = ((String)object).intern();
/*     */       
/* 477 */       if ("itemL" == str) {
/* 478 */         addItemListener((ItemListener)paramObjectInputStream.readObject());
/*     */         continue;
/*     */       } 
/* 481 */       paramObjectInputStream.readObject();
/*     */     } 
/*     */   }
/*     */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) { ItemListener itemListener = null;
/*     */     if (paramClass == ItemListener.class) {
/*     */       itemListener = this.itemListener;
/*     */     } else {
/*     */       return super.getListeners(paramClass);
/*     */     } 
/*     */     return AWTEventMulticaster.getListeners(itemListener, paramClass); }
/*     */   boolean eventEnabled(AWTEvent paramAWTEvent) { if (paramAWTEvent.id == 701) {
/*     */       if ((this.eventMask & 0x200L) != 0L || this.itemListener != null)
/*     */         return true; 
/*     */       return false;
/*     */     } 
/*     */     return super.eventEnabled(paramAWTEvent); }
/*     */   protected void processEvent(AWTEvent paramAWTEvent) { if (paramAWTEvent instanceof ItemEvent) {
/*     */       processItemEvent((ItemEvent)paramAWTEvent);
/*     */       return;
/*     */     } 
/*     */     super.processEvent(paramAWTEvent); }
/*     */   protected void processItemEvent(ItemEvent paramItemEvent) { ItemListener itemListener = this.itemListener;
/*     */     if (itemListener != null)
/*     */       itemListener.itemStateChanged(paramItemEvent);  }
/*     */   void doMenuEvent(long paramLong, int paramInt) { setState(!this.state);
/* 506 */     Toolkit.getEventQueue().postEvent(new ItemEvent(this, 701, getLabel(), this.state ? 1 : 2)); } public String paramString() { return super.paramString() + ",state=" + this.state; } public AccessibleContext getAccessibleContext() { if (this.accessibleContext == null) {
/* 507 */       this.accessibleContext = new AccessibleAWTCheckboxMenuItem();
/*     */     }
/* 509 */     return this.accessibleContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void initIDs();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class AccessibleAWTCheckboxMenuItem
/*     */     extends MenuItem.AccessibleAWTMenuItem
/*     */     implements AccessibleAction, AccessibleValue
/*     */   {
/*     */     private static final long serialVersionUID = -1122642964303476L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleAction getAccessibleAction() {
/* 541 */       return this;
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
/* 553 */       return this;
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
/* 564 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAccessibleActionDescription(int param1Int) {
/* 573 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean doAccessibleAction(int param1Int) {
/* 583 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getCurrentAccessibleValue() {
/* 594 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean setCurrentAccessibleValue(Number param1Number) {
/* 604 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getMinimumAccessibleValue() {
/* 615 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getMaximumAccessibleValue() {
/* 626 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 636 */       return AccessibleRole.CHECK_BOX;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/CheckboxMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */