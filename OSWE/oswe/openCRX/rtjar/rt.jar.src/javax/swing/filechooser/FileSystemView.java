/*     */ package javax.swing.filechooser;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.UIManager;
/*     */ import sun.awt.shell.ShellFolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FileSystemView
/*     */ {
/*  68 */   static FileSystemView windowsFileSystemView = null;
/*  69 */   static FileSystemView unixFileSystemView = null;
/*     */   
/*  71 */   static FileSystemView genericFileSystemView = null;
/*     */ 
/*     */   
/*  74 */   private boolean useSystemExtensionHiding = UIManager.getDefaults().getBoolean("FileChooser.useSystemExtensionHiding");
/*     */   
/*     */   public static FileSystemView getFileSystemView() {
/*  77 */     if (File.separatorChar == '\\') {
/*  78 */       if (windowsFileSystemView == null) {
/*  79 */         windowsFileSystemView = new WindowsFileSystemView();
/*     */       }
/*  81 */       return windowsFileSystemView;
/*     */     } 
/*     */     
/*  84 */     if (File.separatorChar == '/') {
/*  85 */       if (unixFileSystemView == null) {
/*  86 */         unixFileSystemView = new UnixFileSystemView();
/*     */       }
/*  88 */       return unixFileSystemView;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (genericFileSystemView == null) {
/*  99 */       genericFileSystemView = new GenericFileSystemView();
/*     */     }
/* 101 */     return genericFileSystemView;
/*     */   }
/*     */   
/*     */   public FileSystemView() {
/* 105 */     final WeakReference<FileSystemView> weakReference = new WeakReference<>(this);
/*     */     
/* 107 */     UIManager.addPropertyChangeListener(new PropertyChangeListener() {
/*     */           public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 109 */             FileSystemView fileSystemView = weakReference.get();
/*     */             
/* 111 */             if (fileSystemView == null) {
/*     */               
/* 113 */               UIManager.removePropertyChangeListener(this);
/*     */             }
/* 115 */             else if (param1PropertyChangeEvent.getPropertyName().equals("lookAndFeel")) {
/* 116 */               fileSystemView.useSystemExtensionHiding = 
/* 117 */                 UIManager.getDefaults().getBoolean("FileChooser.useSystemExtensionHiding");
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
/*     */   public boolean isRoot(File paramFile) {
/* 137 */     if (paramFile == null || !paramFile.isAbsolute()) {
/* 138 */       return false;
/*     */     }
/*     */     
/* 141 */     File[] arrayOfFile = getRoots();
/* 142 */     for (File file : arrayOfFile) {
/* 143 */       if (file.equals(paramFile)) {
/* 144 */         return true;
/*     */       }
/*     */     } 
/* 147 */     return false;
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
/*     */   public Boolean isTraversable(File paramFile) {
/* 161 */     return Boolean.valueOf(paramFile.isDirectory());
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
/*     */   public String getSystemDisplayName(File paramFile) {
/* 177 */     if (paramFile == null) {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     String str = paramFile.getName();
/*     */     
/* 183 */     if (!str.equals("..") && !str.equals(".") && (this.useSystemExtensionHiding || 
/* 184 */       !isFileSystem(paramFile) || isFileSystemRoot(paramFile)) && (paramFile instanceof ShellFolder || paramFile
/* 185 */       .exists())) {
/*     */       
/*     */       try {
/* 188 */         str = getShellFolder(paramFile).getDisplayName();
/* 189 */       } catch (FileNotFoundException fileNotFoundException) {
/* 190 */         return null;
/*     */       } 
/*     */       
/* 193 */       if (str == null || str.length() == 0) {
/* 194 */         str = paramFile.getPath();
/*     */       }
/*     */     } 
/*     */     
/* 198 */     return str;
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
/*     */   public String getSystemTypeDescription(File paramFile) {
/* 215 */     return null;
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
/*     */   public Icon getSystemIcon(File paramFile) {
/*     */     ShellFolder shellFolder;
/* 231 */     if (paramFile == null) {
/* 232 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 238 */       shellFolder = getShellFolder(paramFile);
/* 239 */     } catch (FileNotFoundException fileNotFoundException) {
/* 240 */       return null;
/*     */     } 
/*     */     
/* 243 */     Image image = shellFolder.getIcon(false);
/*     */     
/* 245 */     if (image != null) {
/* 246 */       return new ImageIcon(image, shellFolder.getFolderType());
/*     */     }
/* 248 */     return UIManager.getIcon(paramFile.isDirectory() ? "FileView.directoryIcon" : "FileView.fileIcon");
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
/*     */   public boolean isParent(File paramFile1, File paramFile2) {
/* 263 */     if (paramFile1 == null || paramFile2 == null)
/* 264 */       return false; 
/* 265 */     if (paramFile1 instanceof ShellFolder) {
/* 266 */       File file = paramFile2.getParentFile();
/* 267 */       if (file != null && file.equals(paramFile1)) {
/* 268 */         return true;
/*     */       }
/* 270 */       File[] arrayOfFile = getFiles(paramFile1, false);
/* 271 */       for (File file1 : arrayOfFile) {
/* 272 */         if (paramFile2.equals(file1)) {
/* 273 */           return true;
/*     */         }
/*     */       } 
/* 276 */       return false;
/*     */     } 
/* 278 */     return paramFile1.equals(paramFile2.getParentFile());
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
/*     */   public File getChild(File paramFile, String paramString) {
/* 293 */     if (paramFile instanceof ShellFolder) {
/* 294 */       File[] arrayOfFile = getFiles(paramFile, false);
/* 295 */       for (File file : arrayOfFile) {
/* 296 */         if (file.getName().equals(paramString)) {
/* 297 */           return file;
/*     */         }
/*     */       } 
/*     */     } 
/* 301 */     return createFileObject(paramFile, paramString);
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
/*     */   public boolean isFileSystem(File paramFile) {
/* 315 */     if (paramFile instanceof ShellFolder) {
/* 316 */       ShellFolder shellFolder = (ShellFolder)paramFile;
/*     */ 
/*     */       
/* 319 */       return (shellFolder.isFileSystem() && (!shellFolder.isLink() || !shellFolder.isDirectory()));
/*     */     } 
/* 321 */     return true;
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
/*     */   public boolean isHiddenFile(File paramFile) {
/* 334 */     return paramFile.isHidden();
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
/*     */   public boolean isFileSystemRoot(File paramFile) {
/* 348 */     return ShellFolder.isFileSystemRoot(paramFile);
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
/*     */   public boolean isDrive(File paramFile) {
/* 362 */     return false;
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
/*     */   public boolean isFloppyDrive(File paramFile) {
/* 376 */     return false;
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
/*     */   public boolean isComputerNode(File paramFile) {
/* 390 */     return ShellFolder.isComputerNode(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File[] getRoots() {
/* 401 */     File[] arrayOfFile = (File[])ShellFolder.get("roots");
/*     */     
/* 403 */     for (byte b = 0; b < arrayOfFile.length; b++) {
/* 404 */       if (isFileSystemRoot(arrayOfFile[b])) {
/* 405 */         arrayOfFile[b] = createFileSystemRoot(arrayOfFile[b]);
/*     */       }
/*     */     } 
/* 408 */     return arrayOfFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getHomeDirectory() {
/* 418 */     return createFileObject(System.getProperty("user.home"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getDefaultDirectory() {
/* 429 */     File file = (File)ShellFolder.get("fileChooserDefaultFolder");
/* 430 */     if (isFileSystemRoot(file)) {
/* 431 */       file = createFileSystemRoot(file);
/*     */     }
/* 433 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File createFileObject(File paramFile, String paramString) {
/* 440 */     if (paramFile == null) {
/* 441 */       return new File(paramString);
/*     */     }
/* 443 */     return new File(paramFile, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File createFileObject(String paramString) {
/* 451 */     File file = new File(paramString);
/* 452 */     if (isFileSystemRoot(file)) {
/* 453 */       file = createFileSystemRoot(file);
/*     */     }
/* 455 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File[] getFiles(File paramFile, boolean paramBoolean) {
/* 463 */     ArrayList<File> arrayList = new ArrayList();
/*     */ 
/*     */     
/* 466 */     if (!(paramFile instanceof ShellFolder)) {
/*     */       try {
/* 468 */         paramFile = getShellFolder(paramFile);
/* 469 */       } catch (FileNotFoundException fileNotFoundException) {
/* 470 */         return new File[0];
/*     */       } 
/*     */     }
/*     */     
/* 474 */     File[] arrayOfFile = ((ShellFolder)paramFile).listFiles(!paramBoolean);
/*     */     
/* 476 */     if (arrayOfFile == null) {
/* 477 */       return new File[0];
/*     */     }
/*     */     
/* 480 */     for (File file : arrayOfFile) {
/* 481 */       if (Thread.currentThread().isInterrupted()) {
/*     */         break;
/*     */       }
/*     */       
/* 485 */       if (!(file instanceof ShellFolder)) {
/* 486 */         if (isFileSystemRoot(file)) {
/* 487 */           file = createFileSystemRoot(file);
/*     */         }
/*     */         try {
/* 490 */           file = ShellFolder.getShellFolder(file);
/* 491 */         } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */         
/*     */         }
/* 495 */         catch (InternalError internalError) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 501 */       if (!paramBoolean || !isHiddenFile(file)) {
/* 502 */         arrayList.add(file);
/*     */       }
/*     */     } 
/*     */     
/* 506 */     return arrayList.<File>toArray(new File[arrayList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getParentDirectory(File paramFile) {
/*     */     ShellFolder shellFolder;
/* 518 */     if (paramFile == null || !paramFile.exists()) {
/* 519 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 525 */       shellFolder = getShellFolder(paramFile);
/* 526 */     } catch (FileNotFoundException fileNotFoundException) {
/* 527 */       return null;
/*     */     } 
/*     */     
/* 530 */     File file = shellFolder.getParentFile();
/*     */     
/* 532 */     if (file == null) {
/* 533 */       return null;
/*     */     }
/*     */     
/* 536 */     if (isFileSystem(file)) {
/* 537 */       File file1 = file;
/* 538 */       if (!file1.exists()) {
/*     */         
/* 540 */         File file2 = file.getParentFile();
/* 541 */         if (file2 == null || !isFileSystem(file2))
/*     */         {
/* 543 */           file1 = createFileSystemRoot(file1);
/*     */         }
/*     */       } 
/* 546 */       return file1;
/*     */     } 
/* 548 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ShellFolder getShellFolder(File paramFile) throws FileNotFoundException {
/* 556 */     if (!(paramFile instanceof ShellFolder) && !(paramFile instanceof FileSystemRoot) && isFileSystemRoot(paramFile)) {
/* 557 */       paramFile = createFileSystemRoot(paramFile);
/*     */     }
/*     */     
/*     */     try {
/* 561 */       return ShellFolder.getShellFolder(paramFile);
/* 562 */     } catch (InternalError internalError) {
/* 563 */       System.err.println("FileSystemView.getShellFolder: f=" + paramFile);
/* 564 */       internalError.printStackTrace();
/* 565 */       return null;
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
/*     */   protected File createFileSystemRoot(File paramFile) {
/* 579 */     return new FileSystemRoot(paramFile);
/*     */   }
/*     */   
/*     */   public abstract File createNewFolder(File paramFile) throws IOException;
/*     */   
/*     */   static class FileSystemRoot
/*     */     extends File {
/*     */     public FileSystemRoot(File param1File) {
/* 587 */       super(param1File, "");
/*     */     }
/*     */     
/*     */     public FileSystemRoot(String param1String) {
/* 591 */       super(param1String);
/*     */     }
/*     */     
/*     */     public boolean isDirectory() {
/* 595 */       return true;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 599 */       return getPath();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/filechooser/FileSystemView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */