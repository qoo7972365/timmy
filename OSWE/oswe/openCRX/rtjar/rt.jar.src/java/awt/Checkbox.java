/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.peer.CheckboxPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleAction;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.accessibility.AccessibleStateSet;
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
/*     */ public class Checkbox
/*     */   extends Component
/*     */   implements ItemSelectable, Accessible
/*     */ {
/*     */   String label;
/*     */   boolean state;
/*     */   CheckboxGroup group;
/*     */   transient ItemListener itemListener;
/*     */   private static final String base = "checkbox";
/*     */   
/*     */   static {
/*  80 */     Toolkit.loadLibraries();
/*  81 */     if (!GraphicsEnvironment.isHeadless()) {
/*  82 */       initIDs();
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
/* 116 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 7270714317450821763L;
/*     */ 
/*     */   
/*     */   private int checkboxSerializedDataVersion;
/*     */ 
/*     */ 
/*     */   
/*     */   void setStateInternal(boolean paramBoolean) {
/* 128 */     this.state = paramBoolean;
/* 129 */     CheckboxPeer checkboxPeer = (CheckboxPeer)this.peer;
/* 130 */     if (checkboxPeer != null) {
/* 131 */       checkboxPeer.setState(paramBoolean);
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
/*     */   public Checkbox() throws HeadlessException {
/* 144 */     this("", false, (CheckboxGroup)null);
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
/*     */   public Checkbox(String paramString) throws HeadlessException {
/* 160 */     this(paramString, false, (CheckboxGroup)null);
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
/*     */   public Checkbox(String paramString, boolean paramBoolean) throws HeadlessException {
/* 177 */     this(paramString, paramBoolean, (CheckboxGroup)null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Checkbox(String paramString, CheckboxGroup paramCheckboxGroup, boolean paramBoolean) throws HeadlessException {
/*     */     this(paramString, paramBoolean, paramCheckboxGroup);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     synchronized (Checkbox.class) {
/*     */       return "checkbox" + nameCounter++;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */         this.peer = getToolkit().createCheckbox(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */         CheckboxPeer checkboxPeer = (CheckboxPeer)this.peer;
/*     */         if (checkboxPeer != null) {
/*     */           checkboxPeer.setLabel(paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Checkbox(String paramString, boolean paramBoolean, CheckboxGroup paramCheckboxGroup) throws HeadlessException {
/* 610 */     this.checkboxSerializedDataVersion = 1; GraphicsEnvironment.checkHeadless(); this.label = paramString; this.state = paramBoolean; this.group = paramCheckboxGroup; if (paramBoolean && paramCheckboxGroup != null)
/*     */       paramCheckboxGroup.setSelectedCheckbox(this); 
/*     */   }
/*     */   public boolean getState() { return this.state; }
/*     */   public void setState(boolean paramBoolean) { CheckboxGroup checkboxGroup = this.group; if (checkboxGroup != null)
/*     */       if (paramBoolean) { checkboxGroup.setSelectedCheckbox(this); }
/*     */       else if (checkboxGroup.getSelectedCheckbox() == this)
/*     */       { paramBoolean = true; }
/*     */         setStateInternal(paramBoolean); }
/*     */   public Object[] getSelectedObjects() { if (this.state) {
/*     */       Object[] arrayOfObject = new Object[1]; arrayOfObject[0] = this.label; return arrayOfObject;
/*     */     }  return null; }
/*     */   public CheckboxGroup getCheckboxGroup() { return this.group; }
/*     */   public void setCheckboxGroup(CheckboxGroup paramCheckboxGroup) { CheckboxGroup checkboxGroup; boolean bool; if (this.group == paramCheckboxGroup)
/*     */       return;  synchronized (this) {
/*     */       checkboxGroup = this.group; bool = getState(); this.group = paramCheckboxGroup; CheckboxPeer checkboxPeer = (CheckboxPeer)this.peer; if (checkboxPeer != null)
/*     */         checkboxPeer.setCheckboxGroup(paramCheckboxGroup);  if (this.group != null && getState())
/*     */         if (this.group.getSelectedCheckbox() != null) {
/*     */           setState(false);
/*     */         } else {
/*     */           this.group.setSelectedCheckbox(this);
/*     */         }  
/*     */     }  if (checkboxGroup != null && bool)
/*     */       checkboxGroup.setSelectedCheckbox(null);  } public synchronized void addItemListener(ItemListener paramItemListener) { if (paramItemListener == null)
/* 634 */       return;  this.itemListener = AWTEventMulticaster.add(this.itemListener, paramItemListener); this.newEventsOnly = true; } private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException { paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 636 */     AWTEventMulticaster.save(paramObjectOutputStream, "itemL", this.itemListener);
/* 637 */     paramObjectOutputStream.writeObject(null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeItemListener(ItemListener paramItemListener) {
/*     */     if (paramItemListener == null) {
/*     */       return;
/*     */     }
/*     */     this.itemListener = AWTEventMulticaster.remove(this.itemListener, paramItemListener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ItemListener[] getItemListeners() {
/*     */     return getListeners(ItemListener.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException {
/* 659 */     GraphicsEnvironment.checkHeadless();
/* 660 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     Object object;
/* 663 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 664 */       String str = ((String)object).intern();
/*     */       
/* 666 */       if ("itemL" == str) {
/* 667 */         addItemListener((ItemListener)paramObjectInputStream.readObject());
/*     */         continue;
/*     */       } 
/* 670 */       paramObjectInputStream.readObject();
/*     */     }  } public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) { ItemListener itemListener = null; if (paramClass == ItemListener.class) {
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
/*     */   protected String paramString() { String str1 = super.paramString();
/*     */     String str2 = this.label;
/*     */     if (str2 != null)
/*     */       str1 = str1 + ",label=" + str2; 
/*     */     return str1 + ",state=" + this.state; }
/* 696 */   public AccessibleContext getAccessibleContext() { if (this.accessibleContext == null) {
/* 697 */       this.accessibleContext = new AccessibleAWTCheckbox();
/*     */     }
/* 699 */     return this.accessibleContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void initIDs();
/*     */ 
/*     */ 
/*     */   
/*     */   protected class AccessibleAWTCheckbox
/*     */     extends Component.AccessibleAWTComponent
/*     */     implements ItemListener, AccessibleAction, AccessibleValue
/*     */   {
/*     */     private static final long serialVersionUID = 7881579233144754107L;
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleAWTCheckbox() {
/* 718 */       Checkbox.this.addItemListener(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void itemStateChanged(ItemEvent param1ItemEvent) {
/* 726 */       Checkbox checkbox = (Checkbox)param1ItemEvent.getSource();
/* 727 */       if (Checkbox.this.accessibleContext != null) {
/* 728 */         if (checkbox.getState()) {
/* 729 */           Checkbox.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.CHECKED);
/*     */         }
/*     */         else {
/*     */           
/* 733 */           Checkbox.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.CHECKED, null);
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
/*     */ 
/*     */     
/*     */     public AccessibleAction getAccessibleAction() {
/* 749 */       return this;
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
/* 761 */       return this;
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
/* 772 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAccessibleActionDescription(int param1Int) {
/* 781 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean doAccessibleAction(int param1Int) {
/* 791 */       return false;
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
/* 802 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean setCurrentAccessibleValue(Number param1Number) {
/* 812 */       return false;
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
/* 823 */       return null;
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
/* 834 */       return null;
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
/* 845 */       return AccessibleRole.CHECK_BOX;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleStateSet getAccessibleStateSet() {
/* 856 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 857 */       if (Checkbox.this.getState()) {
/* 858 */         accessibleStateSet.add(AccessibleState.CHECKED);
/*     */       }
/* 860 */       return accessibleStateSet;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Checkbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */