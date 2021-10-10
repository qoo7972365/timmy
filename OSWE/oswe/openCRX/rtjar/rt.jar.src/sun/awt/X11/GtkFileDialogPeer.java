/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.peer.FileDialogPeer;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
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
/*     */ final class GtkFileDialogPeer
/*     */   extends XDialogPeer
/*     */   implements FileDialogPeer
/*     */ {
/*     */   private final FileDialog fd;
/*  43 */   private volatile long widget = 0L;
/*     */   
/*     */   GtkFileDialogPeer(FileDialog paramFileDialog) {
/*  46 */     super(paramFileDialog);
/*  47 */     this.fd = paramFileDialog;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  52 */     initIDs();
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
/*     */   private void setFileInternal(String paramString, String[] paramArrayOfString) {
/*  70 */     AWTAccessor.FileDialogAccessor fileDialogAccessor = AWTAccessor.getFileDialogAccessor();
/*     */     
/*  72 */     if (paramArrayOfString == null) {
/*  73 */       fileDialogAccessor.setDirectory(this.fd, null);
/*  74 */       fileDialogAccessor.setFile(this.fd, null);
/*  75 */       fileDialogAccessor.setFiles(this.fd, null);
/*     */     } else {
/*     */       
/*  78 */       String str = paramString;
/*  79 */       if (paramString != null) {
/*  80 */         str = paramString.endsWith(File.separator) ? paramString : (paramString + File.separator);
/*     */       }
/*     */       
/*  83 */       fileDialogAccessor.setDirectory(this.fd, str);
/*  84 */       fileDialogAccessor.setFile(this.fd, paramArrayOfString[0]);
/*     */       
/*  86 */       byte b1 = (paramArrayOfString != null) ? paramArrayOfString.length : 0;
/*  87 */       File[] arrayOfFile = new File[b1];
/*  88 */       for (byte b2 = 0; b2 < b1; b2++) {
/*  89 */         arrayOfFile[b2] = new File(paramString, paramArrayOfString[b2]);
/*     */       }
/*  91 */       fileDialogAccessor.setFiles(this.fd, arrayOfFile);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean filenameFilterCallback(String paramString) {
/*  99 */     if (this.fd.getFilenameFilter() == null)
/*     */     {
/* 101 */       return true;
/*     */     }
/*     */     
/* 104 */     File file = new File(paramString);
/* 105 */     return this.fd.getFilenameFilter().accept(new File(file.getParent()), file
/* 106 */         .getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean paramBoolean) {
/* 111 */     XToolkit.awtLock();
/*     */     try {
/* 113 */       if (paramBoolean) {
/* 114 */         Thread thread = new Thread() {
/*     */             public void run() {
/* 116 */               GtkFileDialogPeer.this.showNativeDialog();
/* 117 */               GtkFileDialogPeer.this.fd.setVisible(false);
/*     */             }
/*     */           };
/* 120 */         thread.start();
/*     */       } else {
/* 122 */         quit();
/* 123 */         this.fd.setVisible(false);
/*     */       } 
/*     */     } finally {
/* 126 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 132 */     quit();
/* 133 */     super.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirectory(String paramString) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFile(String paramString) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFilenameFilter(FilenameFilter paramFilenameFilter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showNativeDialog() {
/* 155 */     String str1 = this.fd.getDirectory();
/*     */     
/* 157 */     String str2 = this.fd.getFile();
/* 158 */     if (str2 != null) {
/* 159 */       File file = new File(str2);
/* 160 */       if (this.fd.getMode() == 0 && str1 != null && file
/*     */         
/* 162 */         .getParent() == null)
/*     */       {
/* 164 */         str2 = str1 + (str1.endsWith(File.separator) ? "" : File.separator) + str2;
/*     */       }
/*     */       
/* 167 */       if (this.fd.getMode() == 1 && file.getParent() != null) {
/*     */         
/* 169 */         str2 = file.getName();
/*     */         
/* 171 */         str1 = file.getParent();
/*     */       } 
/*     */     } 
/* 174 */     run(this.fd.getTitle(), this.fd.getMode(), str1, str2, this.fd
/* 175 */         .getFilenameFilter(), this.fd.isMultipleMode(), this.fd.getX(), this.fd.getY());
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private native void run(String paramString1, int paramInt1, String paramString2, String paramString3, FilenameFilter paramFilenameFilter, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */   
/*     */   private native void quit();
/*     */   
/*     */   public native void toFront();
/*     */   
/*     */   public native void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/GtkFileDialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */