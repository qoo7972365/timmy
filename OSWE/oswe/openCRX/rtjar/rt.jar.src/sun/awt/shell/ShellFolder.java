/*     */ package sun.awt.shell;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ShellFolder
/*     */   extends File
/*     */ {
/*     */   private static final String COLUMN_NAME = "FileChooser.fileNameHeaderText";
/*     */   private static final String COLUMN_SIZE = "FileChooser.fileSizeHeaderText";
/*     */   private static final String COLUMN_DATE = "FileChooser.fileDateHeaderText";
/*     */   protected ShellFolder parent;
/*     */   private static final ShellFolderManager shellFolderManager;
/*     */   
/*     */   ShellFolder(ShellFolder paramShellFolder, String paramString) {
/*  56 */     super((paramString != null) ? paramString : "ShellFolder");
/*  57 */     this.parent = paramShellFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFileSystem() {
/*  64 */     return !getPath().startsWith("ShellFolder");
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
/*     */   public String getParent() {
/*  92 */     if (this.parent == null && isFileSystem()) {
/*  93 */       return super.getParent();
/*     */     }
/*  95 */     if (this.parent != null) {
/*  96 */       return this.parent.getPath();
/*     */     }
/*  98 */     return null;
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
/*     */   public File getParentFile() {
/* 115 */     if (this.parent != null)
/* 116 */       return this.parent; 
/* 117 */     if (isFileSystem()) {
/* 118 */       return super.getParentFile();
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public File[] listFiles() {
/* 125 */     return listFiles(true);
/*     */   }
/*     */   
/*     */   public File[] listFiles(boolean paramBoolean) {
/* 129 */     File[] arrayOfFile = super.listFiles();
/*     */     
/* 131 */     if (!paramBoolean) {
/* 132 */       Vector<File> vector = new Vector();
/* 133 */       byte b1 = (arrayOfFile == null) ? 0 : arrayOfFile.length;
/* 134 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 135 */         if (!arrayOfFile[b2].isHidden()) {
/* 136 */           vector.addElement(arrayOfFile[b2]);
/*     */         }
/*     */       } 
/* 139 */       arrayOfFile = vector.<File>toArray(new File[vector.size()]);
/*     */     } 
/*     */     
/* 142 */     return arrayOfFile;
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
/*     */   public int compareTo(File paramFile) {
/* 178 */     if (paramFile == null || !(paramFile instanceof ShellFolder) || (paramFile instanceof ShellFolder && ((ShellFolder)paramFile)
/* 179 */       .isFileSystem())) {
/*     */       
/* 181 */       if (isFileSystem()) {
/* 182 */         return super.compareTo(paramFile);
/*     */       }
/* 184 */       return -1;
/*     */     } 
/*     */     
/* 187 */     if (isFileSystem()) {
/* 188 */       return 1;
/*     */     }
/* 190 */     return getName().compareTo(paramFile.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getIcon(boolean paramBoolean) {
/* 200 */     return null;
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
/*     */   static {
/* 212 */     String str = (String)Toolkit.getDefaultToolkit().getDesktopProperty("Shell.shellFolderManager");
/* 213 */     Class<?> clazz = null;
/*     */     
/* 215 */     try { clazz = Class.forName(str, false, null);
/* 216 */       if (!ShellFolderManager.class.isAssignableFrom(clazz)) {
/* 217 */         clazz = null;
/*     */       } }
/*     */     
/* 220 */     catch (ClassNotFoundException classNotFoundException) {  }
/* 221 */     catch (NullPointerException nullPointerException) {  }
/* 222 */     catch (SecurityException securityException) {}
/*     */ 
/*     */     
/* 225 */     if (clazz == null) {
/* 226 */       clazz = ShellFolderManager.class;
/*     */     }
/*     */     
/*     */     try {
/* 230 */       shellFolderManager = (ShellFolderManager)clazz.newInstance();
/* 231 */     } catch (InstantiationException instantiationException) {
/* 232 */       throw new Error("Could not instantiate Shell Folder Manager: " + clazz
/* 233 */           .getName());
/* 234 */     } catch (IllegalAccessException illegalAccessException) {
/* 235 */       throw new Error("Could not access Shell Folder Manager: " + clazz
/* 236 */           .getName());
/*     */     } 
/*     */   }
/* 239 */   private static final Invoker invoker = shellFolderManager.createInvoker();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShellFolder getShellFolder(File paramFile) throws FileNotFoundException {
/* 247 */     Path path = Paths.get(paramFile.getPath(), new String[0]);
/* 248 */     if (paramFile instanceof ShellFolder) {
/* 249 */       return (ShellFolder)paramFile;
/*     */     }
/* 251 */     if (!Files.exists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
/* 252 */       throw new FileNotFoundException();
/*     */     }
/* 254 */     return shellFolderManager.createShellFolder(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object get(String paramString) {
/* 263 */     return shellFolderManager.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isComputerNode(File paramFile) {
/* 271 */     return shellFolderManager.isComputerNode(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFileSystemRoot(File paramFile) {
/* 278 */     return shellFolderManager.isFileSystemRoot(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File getNormalizedFile(File paramFile) throws IOException {
/* 286 */     File file = paramFile.getCanonicalFile();
/* 287 */     if (paramFile.equals(file))
/*     */     {
/* 289 */       return file;
/*     */     }
/*     */ 
/*     */     
/* 293 */     return new File(paramFile.toURI().normalize());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sort(final List<? extends File> files) {
/* 299 */     if (files == null || files.size() <= 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 305 */     invoke(new Callable<Void>()
/*     */         {
/*     */           
/*     */           public Void call()
/*     */           {
/* 310 */             File file = null;
/*     */             
/* 312 */             for (File file1 : files) {
/* 313 */               File file2 = file1.getParentFile();
/*     */               
/* 315 */               if (file2 == null || !(file1 instanceof ShellFolder)) {
/* 316 */                 file = null;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 321 */               if (file == null) {
/* 322 */                 file = file2; continue;
/*     */               } 
/* 324 */               if (file != file2 && !file.equals(file2)) {
/* 325 */                 file = null;
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */             
/* 332 */             if (file instanceof ShellFolder) {
/* 333 */               ((ShellFolder)file).sortChildren(files);
/*     */             } else {
/* 335 */               Collections.sort(files, ShellFolder.FILE_COMPARATOR);
/*     */             } 
/*     */             
/* 338 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sortChildren(final List<? extends File> files) {
/* 346 */     invoke(new Callable<Void>() {
/*     */           public Void call() {
/* 348 */             Collections.sort(files, ShellFolder.FILE_COMPARATOR);
/*     */             
/* 350 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public boolean isAbsolute() {
/* 356 */     return (!isFileSystem() || super.isAbsolute());
/*     */   }
/*     */   
/*     */   public File getAbsoluteFile() {
/* 360 */     return isFileSystem() ? super.getAbsoluteFile() : this;
/*     */   }
/*     */   
/*     */   public boolean canRead() {
/* 364 */     return isFileSystem() ? super.canRead() : true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canWrite() {
/* 373 */     return isFileSystem() ? super.canWrite() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/* 379 */     return (!isFileSystem() || isFileSystemRoot(this) || super.exists());
/*     */   }
/*     */   
/*     */   public boolean isDirectory() {
/* 383 */     return isFileSystem() ? super.isDirectory() : true;
/*     */   }
/*     */   
/*     */   public boolean isFile() {
/* 387 */     return isFileSystem() ? super.isFile() : (!isDirectory());
/*     */   }
/*     */   
/*     */   public long lastModified() {
/* 391 */     return isFileSystem() ? super.lastModified() : 0L;
/*     */   }
/*     */   
/*     */   public long length() {
/* 395 */     return isFileSystem() ? super.length() : 0L;
/*     */   }
/*     */   
/*     */   public boolean createNewFile() throws IOException {
/* 399 */     return isFileSystem() ? super.createNewFile() : false;
/*     */   }
/*     */   
/*     */   public boolean delete() {
/* 403 */     return isFileSystem() ? super.delete() : false;
/*     */   }
/*     */   
/*     */   public void deleteOnExit() {
/* 407 */     if (isFileSystem()) {
/* 408 */       super.deleteOnExit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mkdir() {
/* 415 */     return isFileSystem() ? super.mkdir() : false;
/*     */   }
/*     */   
/*     */   public boolean mkdirs() {
/* 419 */     return isFileSystem() ? super.mkdirs() : false;
/*     */   }
/*     */   
/*     */   public boolean renameTo(File paramFile) {
/* 423 */     return isFileSystem() ? super.renameTo(paramFile) : false;
/*     */   }
/*     */   
/*     */   public boolean setLastModified(long paramLong) {
/* 427 */     return isFileSystem() ? super.setLastModified(paramLong) : false;
/*     */   }
/*     */   
/*     */   public boolean setReadOnly() {
/* 431 */     return isFileSystem() ? super.setReadOnly() : false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 435 */     return isFileSystem() ? super.toString() : getDisplayName();
/*     */   }
/*     */   
/*     */   public static ShellFolderColumnInfo[] getFolderColumns(File paramFile) {
/* 439 */     ShellFolderColumnInfo[] arrayOfShellFolderColumnInfo = null;
/*     */     
/* 441 */     if (paramFile instanceof ShellFolder) {
/* 442 */       arrayOfShellFolderColumnInfo = ((ShellFolder)paramFile).getFolderColumns();
/*     */     }
/*     */     
/* 445 */     if (arrayOfShellFolderColumnInfo == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 454 */       arrayOfShellFolderColumnInfo = new ShellFolderColumnInfo[] { new ShellFolderColumnInfo("FileChooser.fileNameHeaderText", Integer.valueOf(150), Integer.valueOf(10), true, null, FILE_COMPARATOR), new ShellFolderColumnInfo("FileChooser.fileSizeHeaderText", Integer.valueOf(75), Integer.valueOf(4), true, null, DEFAULT_COMPARATOR, true), new ShellFolderColumnInfo("FileChooser.fileDateHeaderText", Integer.valueOf(130), Integer.valueOf(10), true, null, DEFAULT_COMPARATOR, true) };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 459 */     return arrayOfShellFolderColumnInfo;
/*     */   }
/*     */   
/*     */   public ShellFolderColumnInfo[] getFolderColumns() {
/* 463 */     return null;
/*     */   }
/*     */   public static Object getFolderColumnValue(File paramFile, int paramInt) {
/*     */     long l;
/* 467 */     if (paramFile instanceof ShellFolder) {
/* 468 */       Object object = ((ShellFolder)paramFile).getFolderColumnValue(paramInt);
/* 469 */       if (object != null) {
/* 470 */         return object;
/*     */       }
/*     */     } 
/*     */     
/* 474 */     if (paramFile == null || !paramFile.exists()) {
/* 475 */       return null;
/*     */     }
/*     */     
/* 478 */     switch (paramInt) {
/*     */       
/*     */       case 0:
/* 481 */         return paramFile;
/*     */       
/*     */       case 1:
/* 484 */         return paramFile.isDirectory() ? null : Long.valueOf(paramFile.length());
/*     */       
/*     */       case 2:
/* 487 */         if (isFileSystemRoot(paramFile)) {
/* 488 */           return null;
/*     */         }
/* 490 */         l = paramFile.lastModified();
/* 491 */         return (l == 0L) ? null : new Date(l);
/*     */     } 
/*     */     
/* 494 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getFolderColumnValue(int paramInt) {
/* 499 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T invoke(Callable<T> paramCallable) {
/*     */     try {
/* 509 */       return invoke(paramCallable, RuntimeException.class);
/* 510 */     } catch (InterruptedException interruptedException) {
/* 511 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T, E extends Throwable> T invoke(Callable<T> paramCallable, Class<E> paramClass) throws InterruptedException, E {
/*     */     try {
/* 523 */       return invoker.invoke(paramCallable);
/* 524 */     } catch (Exception exception) {
/* 525 */       if (exception instanceof RuntimeException)
/*     */       {
/* 527 */         throw (RuntimeException)exception;
/*     */       }
/*     */       
/* 530 */       if (exception instanceof InterruptedException) {
/*     */         
/* 532 */         Thread.currentThread().interrupt();
/*     */ 
/*     */         
/* 535 */         throw (InterruptedException)exception;
/*     */       } 
/*     */       
/* 538 */       if (paramClass.isInstance(exception)) {
/* 539 */         throw (Throwable)paramClass.cast(exception);
/*     */       }
/*     */       
/* 542 */       throw new RuntimeException("Unexpected error", exception);
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
/* 563 */   private static final Comparator DEFAULT_COMPARATOR = new Comparator()
/*     */     {
/*     */       public int compare(Object param1Object1, Object param1Object2) {
/*     */         boolean bool;
/* 567 */         if (param1Object1 == null && param1Object2 == null) {
/* 568 */           bool = false;
/* 569 */         } else if (param1Object1 != null && param1Object2 == null) {
/* 570 */           bool = true;
/* 571 */         } else if (param1Object1 == null && param1Object2 != null) {
/* 572 */           bool = true;
/* 573 */         } else if (param1Object1 instanceof Comparable) {
/* 574 */           bool = ((Comparable<Object>)param1Object1).compareTo(param1Object2);
/*     */         } else {
/* 576 */           bool = false;
/*     */         } 
/*     */         
/* 579 */         return bool;
/*     */       }
/*     */     };
/*     */   
/* 583 */   private static final Comparator<File> FILE_COMPARATOR = new Comparator<File>() {
/*     */       public int compare(File param1File1, File param1File2) {
/* 585 */         ShellFolder shellFolder1 = null;
/* 586 */         ShellFolder shellFolder2 = null;
/*     */         
/* 588 */         if (param1File1 instanceof ShellFolder) {
/* 589 */           shellFolder1 = (ShellFolder)param1File1;
/* 590 */           if (shellFolder1.isFileSystem()) {
/* 591 */             shellFolder1 = null;
/*     */           }
/*     */         } 
/* 594 */         if (param1File2 instanceof ShellFolder) {
/* 595 */           shellFolder2 = (ShellFolder)param1File2;
/* 596 */           if (shellFolder2.isFileSystem()) {
/* 597 */             shellFolder2 = null;
/*     */           }
/*     */         } 
/*     */         
/* 601 */         if (shellFolder1 != null && shellFolder2 != null)
/* 602 */           return shellFolder1.compareTo(shellFolder2); 
/* 603 */         if (shellFolder1 != null)
/*     */         {
/* 605 */           return -1; } 
/* 606 */         if (shellFolder2 != null) {
/* 607 */           return 1;
/*     */         }
/* 609 */         String str1 = param1File1.getName();
/* 610 */         String str2 = param1File2.getName();
/*     */ 
/*     */         
/* 613 */         int i = str1.compareToIgnoreCase(str2);
/* 614 */         if (i != 0) {
/* 615 */           return i;
/*     */         }
/*     */ 
/*     */         
/* 619 */         return str1.compareTo(str2);
/*     */       }
/*     */     };
/*     */   
/*     */   protected abstract Object writeReplace() throws ObjectStreamException;
/*     */   
/*     */   public abstract boolean isLink();
/*     */   
/*     */   public abstract ShellFolder getLinkLocation() throws FileNotFoundException;
/*     */   
/*     */   public abstract String getDisplayName();
/*     */   
/*     */   public abstract String getFolderType();
/*     */   
/*     */   public abstract String getExecutableType();
/*     */   
/*     */   public static interface Invoker {
/*     */     <T> T invoke(Callable<T> param1Callable) throws Exception;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/shell/ShellFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */