/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.FileDialogPeer;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileDialog
/*     */   extends Dialog
/*     */ {
/*     */   public static final int LOAD = 0;
/*     */   public static final int SAVE = 1;
/*     */   int mode;
/*     */   String dir;
/*     */   String file;
/*     */   private File[] files;
/*     */   private boolean multipleMode = false;
/*     */   FilenameFilter filter;
/*     */   private static final String base = "filedlg";
/* 131 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 5035145889651310422L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 141 */     Toolkit.loadLibraries();
/* 142 */     if (!GraphicsEnvironment.isHeadless()) {
/* 143 */       initIDs();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 148 */     AWTAccessor.setFileDialogAccessor(new AWTAccessor.FileDialogAccessor()
/*     */         {
/*     */           public void setFiles(FileDialog param1FileDialog, File[] param1ArrayOfFile) {
/* 151 */             param1FileDialog.setFiles(param1ArrayOfFile);
/*     */           }
/*     */           public void setFile(FileDialog param1FileDialog, String param1String) {
/* 154 */             param1FileDialog.file = "".equals(param1String) ? null : param1String;
/*     */           }
/*     */           public void setDirectory(FileDialog param1FileDialog, String param1String) {
/* 157 */             param1FileDialog.dir = "".equals(param1String) ? null : param1String;
/*     */           }
/*     */           public boolean isMultipleMode(FileDialog param1FileDialog) {
/* 160 */             synchronized (param1FileDialog.getObjectLock()) {
/* 161 */               return param1FileDialog.multipleMode;
/*     */             } 
/*     */           }
/*     */         });
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
/*     */   public FileDialog(Frame paramFrame) {
/* 182 */     this(paramFrame, "", 0);
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
/*     */   public FileDialog(Frame paramFrame, String paramString) {
/* 195 */     this(paramFrame, paramString, 0);
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
/*     */   public FileDialog(Frame paramFrame, String paramString, int paramInt) {
/* 218 */     super(paramFrame, paramString, true);
/* 219 */     setMode(paramInt);
/* 220 */     setLayout((LayoutManager)null);
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
/*     */   public FileDialog(Dialog paramDialog) {
/* 240 */     this(paramDialog, "", 0);
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
/*     */   public FileDialog(Dialog paramDialog, String paramString) {
/* 264 */     this(paramDialog, paramString, 0);
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
/*     */   public FileDialog(Dialog paramDialog, String paramString, int paramInt) {
/* 298 */     super(paramDialog, paramString, true);
/* 299 */     setMode(paramInt);
/* 300 */     setLayout((LayoutManager)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String constructComponentName() {
/* 308 */     synchronized (FileDialog.class) {
/* 309 */       return "filedlg" + nameCounter++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 318 */     synchronized (getTreeLock()) {
/* 319 */       if (this.parent != null && this.parent.getPeer() == null) {
/* 320 */         this.parent.addNotify();
/*     */       }
/* 322 */       if (this.peer == null)
/* 323 */         this.peer = getToolkit().createFileDialog(this); 
/* 324 */       super.addNotify();
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
/*     */   public int getMode() {
/* 340 */     return this.mode;
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
/*     */   public void setMode(int paramInt) {
/* 359 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/* 362 */         this.mode = paramInt;
/*     */         return;
/*     */     } 
/* 365 */     throw new IllegalArgumentException("illegal file dialog mode");
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
/*     */   public String getDirectory() {
/* 377 */     return this.dir;
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
/*     */   public void setDirectory(String paramString) {
/* 395 */     this.dir = (paramString != null && paramString.equals("")) ? null : paramString;
/* 396 */     FileDialogPeer fileDialogPeer = (FileDialogPeer)this.peer;
/* 397 */     if (fileDialogPeer != null) {
/* 398 */       fileDialogPeer.setDirectory(this.dir);
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
/*     */   public String getFile() {
/* 411 */     return this.file;
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
/*     */   public File[] getFiles() {
/* 427 */     synchronized (getObjectLock()) {
/* 428 */       if (this.files != null) {
/* 429 */         return (File[])this.files.clone();
/*     */       }
/* 431 */       return new File[0];
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
/*     */   private void setFiles(File[] paramArrayOfFile) {
/* 449 */     synchronized (getObjectLock()) {
/* 450 */       this.files = paramArrayOfFile;
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
/*     */   public void setFile(String paramString) {
/* 475 */     this.file = (paramString != null && paramString.equals("")) ? null : paramString;
/* 476 */     FileDialogPeer fileDialogPeer = (FileDialogPeer)this.peer;
/* 477 */     if (fileDialogPeer != null) {
/* 478 */       fileDialogPeer.setFile(this.file);
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
/*     */   public void setMultipleMode(boolean paramBoolean) {
/* 491 */     synchronized (getObjectLock()) {
/* 492 */       this.multipleMode = paramBoolean;
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
/*     */   public boolean isMultipleMode() {
/* 505 */     synchronized (getObjectLock()) {
/* 506 */       return this.multipleMode;
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
/*     */   public FilenameFilter getFilenameFilter() {
/* 521 */     return this.filter;
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
/*     */   public synchronized void setFilenameFilter(FilenameFilter paramFilenameFilter) {
/* 535 */     this.filter = paramFilenameFilter;
/* 536 */     FileDialogPeer fileDialogPeer = (FileDialogPeer)this.peer;
/* 537 */     if (fileDialogPeer != null) {
/* 538 */       fileDialogPeer.setFilenameFilter(paramFilenameFilter);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 553 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 556 */     if (this.dir != null && this.dir.equals("")) {
/* 557 */       this.dir = null;
/*     */     }
/* 559 */     if (this.file != null && this.file.equals("")) {
/* 560 */       this.file = null;
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
/*     */   protected String paramString() {
/* 574 */     String str = super.paramString();
/* 575 */     str = str + ",dir= " + this.dir;
/* 576 */     str = str + ",file= " + this.file;
/* 577 */     return str + ((this.mode == 0) ? ",load" : ",save");
/*     */   }
/*     */   
/*     */   boolean postsOldMouseEvents() {
/* 581 */     return false;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/FileDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */