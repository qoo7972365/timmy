/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.peer.TextFieldPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextField
/*     */   extends TextComponent
/*     */ {
/*     */   int columns;
/*     */   char echoChar;
/*     */   transient ActionListener actionListener;
/*     */   private static final String base = "textfield";
/* 127 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -2966288784432217853L;
/*     */ 
/*     */ 
/*     */   
/*     */   private int textFieldSerializedDataVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 141 */     Toolkit.loadLibraries();
/* 142 */     if (!GraphicsEnvironment.isHeadless()) {
/* 143 */       initIDs();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextField() throws HeadlessException {
/* 154 */     this("", 0);
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
/*     */   public TextField(String paramString) throws HeadlessException {
/* 167 */     this(paramString, (paramString != null) ? paramString.length() : 0);
/*     */   }
/*     */   String constructComponentName() {
/*     */     synchronized (TextField.class) {
/*     */       return "textfield" + nameCounter++;
/*     */     } 
/*     */   } public void addNotify() {
/*     */     synchronized (getTreeLock()) {
/*     */       if (this.peer == null)
/*     */         this.peer = getToolkit().createTextField(this); 
/*     */       super.addNotify();
/*     */     } 
/*     */   } public char getEchoChar() {
/*     */     return this.echoChar;
/*     */   }
/* 182 */   public TextField(int paramInt) throws HeadlessException { this("", paramInt); }
/*     */   public void setEchoChar(char paramChar) { setEchoCharacter(paramChar); }
/*     */   @Deprecated public synchronized void setEchoCharacter(char paramChar) { if (this.echoChar != paramChar) {
/*     */       this.echoChar = paramChar; TextFieldPeer textFieldPeer = (TextFieldPeer)this.peer; if (textFieldPeer != null)
/*     */         textFieldPeer.setEchoChar(paramChar); 
/*     */     }  }
/*     */   public void setText(String paramString) { super.setText(paramString);
/*     */     invalidateIfValid(); }
/*     */   public boolean echoCharIsSet() { return (this.echoChar != '\000'); }
/*     */   public int getColumns() { return this.columns; }
/*     */   public void setColumns(int paramInt) { int i;
/*     */     synchronized (this) {
/*     */       i = this.columns;
/*     */       if (paramInt < 0)
/*     */         throw new IllegalArgumentException("columns less than zero."); 
/*     */       if (paramInt != i)
/*     */         this.columns = paramInt; 
/*     */     } 
/*     */     if (paramInt != i)
/* 201 */       invalidate();  } public TextField(String paramString, int paramInt) throws HeadlessException { super(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 647 */     this.textFieldSerializedDataVersion = 1;
/*     */     this.columns = (paramInt >= 0) ? paramInt : 0; }
/*     */   
/*     */   public Dimension getPreferredSize(int paramInt) {
/*     */     return preferredSize(paramInt);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Dimension preferredSize(int paramInt) {
/*     */     synchronized (getTreeLock()) {
/*     */       TextFieldPeer textFieldPeer = (TextFieldPeer)this.peer;
/*     */       return (textFieldPeer != null) ? textFieldPeer.getPreferredSize(paramInt) : super.preferredSize();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/*     */     return preferredSize();
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 667 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 669 */     AWTEventMulticaster.save(paramObjectOutputStream, "actionL", this.actionListener);
/* 670 */     paramObjectOutputStream.writeObject(null); }
/*     */   @Deprecated
/*     */   public Dimension preferredSize() { synchronized (getTreeLock()) {
/*     */       return (this.columns > 0) ? preferredSize(this.columns) : super.preferredSize();
/*     */     }  }
/*     */   public Dimension getMinimumSize(int paramInt) { return minimumSize(paramInt); }
/*     */   @Deprecated
/*     */   public Dimension minimumSize(int paramInt) { synchronized (getTreeLock()) {
/*     */       TextFieldPeer textFieldPeer = (TextFieldPeer)this.peer;
/*     */       return (textFieldPeer != null) ? textFieldPeer.getMinimumSize(paramInt) : super.minimumSize();
/*     */     }  }
/*     */   public Dimension getMinimumSize() { return minimumSize(); }
/*     */   @Deprecated
/*     */   public Dimension minimumSize() { synchronized (getTreeLock()) {
/*     */       return (this.columns > 0) ? minimumSize(this.columns) : super.minimumSize();
/*     */     }  }
/*     */   public synchronized void addActionListener(ActionListener paramActionListener) { if (paramActionListener == null)
/*     */       return; 
/*     */     this.actionListener = AWTEventMulticaster.add(this.actionListener, paramActionListener);
/*     */     this.newEventsOnly = true; }
/* 690 */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException { paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 693 */     if (this.columns < 0) {
/* 694 */       this.columns = 0;
/*     */     }
/*     */     
/*     */     Object object;
/*     */     
/* 699 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 700 */       String str = ((String)object).intern();
/*     */       
/* 702 */       if ("actionL" == str) {
/* 703 */         addActionListener((ActionListener)paramObjectInputStream.readObject());
/*     */         continue;
/*     */       } 
/* 706 */       paramObjectInputStream.readObject();
/*     */     }  }
/*     */   
/*     */   public synchronized void removeActionListener(ActionListener paramActionListener) {
/*     */     if (paramActionListener == null)
/*     */       return; 
/*     */     this.actionListener = AWTEventMulticaster.remove(this.actionListener, paramActionListener);
/*     */   }
/*     */   public synchronized ActionListener[] getActionListeners() {
/*     */     return getListeners(ActionListener.class);
/*     */   }
/*     */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/*     */     ActionListener actionListener = null;
/*     */     if (paramClass == ActionListener.class) {
/*     */       actionListener = this.actionListener;
/*     */     } else {
/*     */       return super.getListeners(paramClass);
/*     */     } 
/*     */     return AWTEventMulticaster.getListeners(actionListener, paramClass);
/*     */   }
/*     */   
/*     */   public AccessibleContext getAccessibleContext() {
/* 728 */     if (this.accessibleContext == null) {
/* 729 */       this.accessibleContext = new AccessibleAWTTextField();
/*     */     }
/* 731 */     return this.accessibleContext;
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
/*     */   } protected void processActionEvent(ActionEvent paramActionEvent) {
/*     */     ActionListener actionListener = this.actionListener;
/*     */     if (actionListener != null)
/*     */       actionListener.actionPerformed(paramActionEvent); 
/*     */   } protected String paramString() {
/*     */     String str = super.paramString();
/*     */     if (this.echoChar != '\000')
/*     */       str = str + ",echo=" + this.echoChar; 
/*     */     return str;
/*     */   } private static native void initIDs(); protected class AccessibleAWTTextField extends TextComponent.AccessibleAWTTextComponent { private static final long serialVersionUID = 6219164359235943158L; public AccessibleStateSet getAccessibleStateSet() {
/* 755 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 756 */       accessibleStateSet.add(AccessibleState.SINGLE_LINE);
/* 757 */       return accessibleStateSet;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/TextField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */