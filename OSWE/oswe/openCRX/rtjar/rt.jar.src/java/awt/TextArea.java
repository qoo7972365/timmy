/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.TextAreaPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class TextArea
/*     */   extends TextComponent
/*     */ {
/*     */   int rows;
/*     */   int columns;
/*     */   private static final String base = "text";
/*  83 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SCROLLBARS_BOTH = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SCROLLBARS_VERTICAL_ONLY = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SCROLLBARS_HORIZONTAL_ONLY = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SCROLLBARS_NONE = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int scrollbarVisibility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 140 */     Toolkit.loadLibraries();
/* 141 */     if (!GraphicsEnvironment.isHeadless())
/* 142 */       initIDs(); 
/*     */   }
/* 144 */   private static Set<AWTKeyStroke> forwardTraversalKeys = KeyboardFocusManager.initFocusTraversalKeysSet("ctrl TAB", new HashSet<>());
/*     */ 
/*     */   
/* 147 */   private static Set<AWTKeyStroke> backwardTraversalKeys = KeyboardFocusManager.initFocusTraversalKeysSet("ctrl shift TAB", new HashSet<>());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 3692302836626095722L;
/*     */ 
/*     */ 
/*     */   
/*     */   private int textAreaSerializedDataVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextArea() throws HeadlessException {
/* 162 */     this("", 0, 0, 0);
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
/*     */   public TextArea(String paramString) throws HeadlessException {
/* 178 */     this(paramString, 0, 0, 0);
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
/*     */   public TextArea(int paramInt1, int paramInt2) throws HeadlessException {
/* 196 */     this("", paramInt1, paramInt2, 0);
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
/*     */   public TextArea(String paramString, int paramInt1, int paramInt2) throws HeadlessException {
/* 218 */     this(paramString, paramInt1, paramInt2, 0);
/*     */   }
/*     */   
/*     */   String constructComponentName() {
/*     */     synchronized (TextArea.class) {
/*     */       return "text" + nameCounter++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addNotify() {
/*     */     synchronized (getTreeLock()) {
/*     */       if (this.peer == null) {
/*     */         this.peer = getToolkit().createTextArea(this);
/*     */       }
/*     */       super.addNotify();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void insert(String paramString, int paramInt) {
/*     */     insertText(paramString, paramInt);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public synchronized void insertText(String paramString, int paramInt) {
/*     */     TextAreaPeer textAreaPeer = (TextAreaPeer)this.peer;
/*     */     if (textAreaPeer != null)
/*     */       textAreaPeer.insert(paramString, paramInt); 
/*     */     this.text = this.text.substring(0, paramInt) + paramString + this.text.substring(paramInt);
/*     */   }
/*     */   
/*     */   public void append(String paramString) {
/*     */     appendText(paramString);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public synchronized void appendText(String paramString) {
/*     */     insertText(paramString, getText().length());
/*     */   }
/*     */   
/*     */   public TextArea(String paramString, int paramInt1, int paramInt2, int paramInt3) throws HeadlessException {
/* 258 */     super(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 619 */     this.textAreaSerializedDataVersion = 2; this.rows = (paramInt1 >= 0) ? paramInt1 : 0; this.columns = (paramInt2 >= 0) ? paramInt2 : 0; if (paramInt3 >= 0 && paramInt3 <= 3) {
/*     */       this.scrollbarVisibility = paramInt3;
/*     */     } else {
/*     */       this.scrollbarVisibility = 0;
/*     */     }  setFocusTraversalKeys(0, forwardTraversalKeys); setFocusTraversalKeys(1, backwardTraversalKeys); } public void replaceRange(String paramString, int paramInt1, int paramInt2) { replaceText(paramString, paramInt1, paramInt2); }
/*     */   @Deprecated public synchronized void replaceText(String paramString, int paramInt1, int paramInt2) { TextAreaPeer textAreaPeer = (TextAreaPeer)this.peer; if (textAreaPeer != null)
/*     */       textAreaPeer.replaceRange(paramString, paramInt1, paramInt2);  this.text = this.text.substring(0, paramInt1) + paramString + this.text.substring(paramInt2); }
/*     */   public int getRows() { return this.rows; }
/*     */   public void setRows(int paramInt) { int i = this.rows; if (paramInt < 0)
/*     */       throw new IllegalArgumentException("rows less than zero.");  if (paramInt != i) {
/*     */       this.rows = paramInt; invalidate();
/*     */     }  }
/*     */   public int getColumns() { return this.columns; }
/* 632 */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException { paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */ 
/*     */     
/* 636 */     if (this.columns < 0) {
/* 637 */       this.columns = 0;
/*     */     }
/* 639 */     if (this.rows < 0) {
/* 640 */       this.rows = 0;
/*     */     }
/*     */     
/* 643 */     if (this.scrollbarVisibility < 0 || this.scrollbarVisibility > 3)
/*     */     {
/* 645 */       this.scrollbarVisibility = 0;
/*     */     }
/*     */     
/* 648 */     if (this.textAreaSerializedDataVersion < 2) {
/* 649 */       setFocusTraversalKeys(0, forwardTraversalKeys);
/*     */       
/* 651 */       setFocusTraversalKeys(1, backwardTraversalKeys);
/*     */     }  }
/*     */   
/*     */   public void setColumns(int paramInt) {
/*     */     int i = this.columns;
/*     */     if (paramInt < 0)
/*     */       throw new IllegalArgumentException("columns less than zero."); 
/*     */     if (paramInt != i) {
/*     */       this.columns = paramInt;
/*     */       invalidate();
/*     */     } 
/*     */   } public int getScrollbarVisibility() {
/*     */     return this.scrollbarVisibility;
/*     */   } public Dimension getPreferredSize(int paramInt1, int paramInt2) {
/*     */     return preferredSize(paramInt1, paramInt2);
/*     */   }
/*     */   @Deprecated
/*     */   public Dimension preferredSize(int paramInt1, int paramInt2) {
/*     */     synchronized (getTreeLock()) {
/*     */       TextAreaPeer textAreaPeer = (TextAreaPeer)this.peer;
/*     */       return (textAreaPeer != null) ? textAreaPeer.getPreferredSize(paramInt1, paramInt2) : super.preferredSize();
/*     */     } 
/*     */   }
/* 674 */   public AccessibleContext getAccessibleContext() { if (this.accessibleContext == null) {
/* 675 */       this.accessibleContext = new AccessibleAWTTextArea();
/*     */     }
/* 677 */     return this.accessibleContext; }
/*     */   public Dimension getPreferredSize() { return preferredSize(); }
/*     */   @Deprecated public Dimension preferredSize() { synchronized (getTreeLock()) {
/*     */       return (this.rows > 0 && this.columns > 0) ? preferredSize(this.rows, this.columns) : super.preferredSize();
/*     */     }  }
/*     */   public Dimension getMinimumSize(int paramInt1, int paramInt2) { return minimumSize(paramInt1, paramInt2); }
/*     */   @Deprecated public Dimension minimumSize(int paramInt1, int paramInt2) { synchronized (getTreeLock()) {
/*     */       TextAreaPeer textAreaPeer = (TextAreaPeer)this.peer; return (textAreaPeer != null) ? textAreaPeer.getMinimumSize(paramInt1, paramInt2) : super.minimumSize();
/*     */     }  }
/*     */   public Dimension getMinimumSize() { return minimumSize(); } @Deprecated public Dimension minimumSize() { synchronized (getTreeLock()) {
/*     */       return (this.rows > 0 && this.columns > 0) ? minimumSize(this.rows, this.columns) : super.minimumSize();
/*     */     }  } protected String paramString() { switch (this.scrollbarVisibility) {
/*     */       case 0:
/*     */         str = "both"; return super.paramString() + ",rows=" + this.rows + ",columns=" + this.columns + ",scrollbarVisibility=" + str;
/*     */       case 1:
/*     */         str = "vertical-only"; return super.paramString() + ",rows=" + this.rows + ",columns=" + this.columns + ",scrollbarVisibility=" + str;
/*     */       case 2:
/*     */         str = "horizontal-only"; return super.paramString() + ",rows=" + this.rows + ",columns=" + this.columns + ",scrollbarVisibility=" + str;
/*     */       case 3:
/*     */         str = "none"; return super.paramString() + ",rows=" + this.rows + ",columns=" + this.columns + ",scrollbarVisibility=" + str;
/*     */     } 
/*     */     String str = "invalid display policy";
/*     */     return super.paramString() + ",rows=" + this.rows + ",columns=" + this.columns + ",scrollbarVisibility=" + str; } private static native void initIDs(); protected class AccessibleAWTTextArea extends TextComponent.AccessibleAWTTextComponent
/*     */   {
/* 701 */     public AccessibleStateSet getAccessibleStateSet() { AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 702 */       accessibleStateSet.add(AccessibleState.MULTI_LINE);
/* 703 */       return accessibleStateSet; }
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 3472827823632144419L;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/TextArea.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */