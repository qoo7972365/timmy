/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.swing.AbstractListModel;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.ListDataEvent;
/*     */ import javax.swing.filechooser.FileSystemView;
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
/*     */ public class BasicDirectoryModel
/*     */   extends AbstractListModel<Object>
/*     */   implements PropertyChangeListener
/*     */ {
/*  45 */   private JFileChooser filechooser = null;
/*     */   
/*  47 */   private Vector<File> fileCache = new Vector<>(50);
/*  48 */   private LoadFilesThread loadThread = null;
/*  49 */   private Vector<File> files = null;
/*  50 */   private Vector<File> directories = null;
/*  51 */   private int fetchID = 0;
/*     */   
/*     */   private PropertyChangeSupport changeSupport;
/*     */   
/*     */   private boolean busy = false;
/*     */   
/*     */   public BasicDirectoryModel(JFileChooser paramJFileChooser) {
/*  58 */     this.filechooser = paramJFileChooser;
/*  59 */     validateFileCache();
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/*  63 */     String str = paramPropertyChangeEvent.getPropertyName();
/*  64 */     if (str == "directoryChanged" || str == "fileViewChanged" || str == "fileFilterChanged" || str == "FileHidingChanged" || str == "fileSelectionChanged") {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  69 */       validateFileCache();
/*  70 */     } else if ("UI".equals(str)) {
/*  71 */       Object object = paramPropertyChangeEvent.getOldValue();
/*  72 */       if (object instanceof BasicFileChooserUI) {
/*  73 */         BasicFileChooserUI basicFileChooserUI = (BasicFileChooserUI)object;
/*  74 */         BasicDirectoryModel basicDirectoryModel = basicFileChooserUI.getModel();
/*  75 */         if (basicDirectoryModel != null) {
/*  76 */           basicDirectoryModel.invalidateFileCache();
/*     */         }
/*     */       } 
/*  79 */     } else if ("JFileChooserDialogIsClosingProperty".equals(str)) {
/*  80 */       invalidateFileCache();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateFileCache() {
/*  88 */     if (this.loadThread != null) {
/*  89 */       this.loadThread.interrupt();
/*  90 */       this.loadThread.cancelRunnables();
/*  91 */       this.loadThread = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vector<File> getDirectories() {
/*  96 */     synchronized (this.fileCache) {
/*  97 */       if (this.directories != null) {
/*  98 */         return this.directories;
/*     */       }
/* 100 */       Vector<File> vector = getFiles();
/* 101 */       return this.directories;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vector<File> getFiles() {
/* 106 */     synchronized (this.fileCache) {
/* 107 */       if (this.files != null) {
/* 108 */         return this.files;
/*     */       }
/* 110 */       this.files = new Vector<>();
/* 111 */       this.directories = new Vector<>();
/* 112 */       this.directories.addElement(this.filechooser.getFileSystemView().createFileObject(this.filechooser
/* 113 */             .getCurrentDirectory(), ".."));
/*     */ 
/*     */       
/* 116 */       for (byte b = 0; b < getSize(); b++) {
/* 117 */         File file = this.fileCache.get(b);
/* 118 */         if (this.filechooser.isTraversable(file)) {
/* 119 */           this.directories.add(file);
/*     */         } else {
/* 121 */           this.files.add(file);
/*     */         } 
/*     */       } 
/* 124 */       return this.files;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void validateFileCache() {
/* 129 */     File file = this.filechooser.getCurrentDirectory();
/* 130 */     if (file == null) {
/*     */       return;
/*     */     }
/* 133 */     if (this.loadThread != null) {
/* 134 */       this.loadThread.interrupt();
/* 135 */       this.loadThread.cancelRunnables();
/*     */     } 
/*     */     
/* 138 */     setBusy(true, ++this.fetchID);
/*     */     
/* 140 */     this.loadThread = new LoadFilesThread(file, this.fetchID);
/* 141 */     this.loadThread.start();
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
/*     */   public boolean renameFile(File paramFile1, File paramFile2) {
/* 156 */     synchronized (this.fileCache) {
/* 157 */       if (paramFile1.renameTo(paramFile2)) {
/* 158 */         validateFileCache();
/* 159 */         return true;
/*     */       } 
/* 161 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireContentsChanged() {
/* 168 */     fireContentsChanged(this, 0, getSize() - 1);
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 172 */     return this.fileCache.size();
/*     */   }
/*     */   
/*     */   public boolean contains(Object paramObject) {
/* 176 */     return this.fileCache.contains(paramObject);
/*     */   }
/*     */   
/*     */   public int indexOf(Object paramObject) {
/* 180 */     return this.fileCache.indexOf(paramObject);
/*     */   }
/*     */   
/*     */   public Object getElementAt(int paramInt) {
/* 184 */     return this.fileCache.get(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void intervalAdded(ListDataEvent paramListDataEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void intervalRemoved(ListDataEvent paramListDataEvent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sort(Vector<? extends File> paramVector) {
/* 200 */     ShellFolder.sort(paramVector);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean lt(File paramFile1, File paramFile2) {
/* 206 */     int i = paramFile1.getName().toLowerCase().compareTo(paramFile2.getName().toLowerCase());
/* 207 */     if (i != 0) {
/* 208 */       return (i < 0);
/*     */     }
/*     */     
/* 211 */     return (paramFile1.getName().compareTo(paramFile2.getName()) < 0);
/*     */   }
/*     */   
/*     */   class LoadFilesThread
/*     */     extends Thread
/*     */   {
/* 217 */     File currentDirectory = null;
/*     */     int fid;
/* 219 */     Vector<BasicDirectoryModel.DoChangeContents> runnables = new Vector<>(10);
/*     */     
/*     */     public LoadFilesThread(File param1File, int param1Int) {
/* 222 */       super("Basic L&F File Loading Thread");
/* 223 */       this.currentDirectory = param1File;
/* 224 */       this.fid = param1Int;
/*     */     }
/*     */     
/*     */     public void run() {
/* 228 */       run0();
/* 229 */       BasicDirectoryModel.this.setBusy(false, this.fid);
/*     */     }
/*     */     
/*     */     public void run0() {
/* 233 */       FileSystemView fileSystemView = BasicDirectoryModel.this.filechooser.getFileSystemView();
/*     */       
/* 235 */       if (isInterrupted()) {
/*     */         return;
/*     */       }
/*     */       
/* 239 */       File[] arrayOfFile = fileSystemView.getFiles(this.currentDirectory, BasicDirectoryModel.this.filechooser.isFileHidingEnabled());
/*     */       
/* 241 */       if (isInterrupted()) {
/*     */         return;
/*     */       }
/*     */       
/* 245 */       final Vector<File> newFileCache = new Vector();
/* 246 */       Vector<File> vector2 = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 251 */       for (File file : arrayOfFile) {
/* 252 */         if (BasicDirectoryModel.this.filechooser.accept(file)) {
/* 253 */           boolean bool = BasicDirectoryModel.this.filechooser.isTraversable(file);
/*     */           
/* 255 */           if (bool) {
/* 256 */             vector1.addElement(file);
/* 257 */           } else if (BasicDirectoryModel.this.filechooser.isFileSelectionEnabled()) {
/* 258 */             vector2.addElement(file);
/*     */           } 
/*     */           
/* 261 */           if (isInterrupted()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 268 */       BasicDirectoryModel.this.sort(vector1);
/* 269 */       BasicDirectoryModel.this.sort(vector2);
/*     */       
/* 271 */       vector1.addAll(vector2);
/*     */ 
/*     */ 
/*     */       
/* 275 */       BasicDirectoryModel.DoChangeContents doChangeContents = ShellFolder.<BasicDirectoryModel.DoChangeContents>invoke(new Callable<BasicDirectoryModel.DoChangeContents>() {
/*     */             public BasicDirectoryModel.DoChangeContents call() {
/* 277 */               int i = newFileCache.size();
/* 278 */               int j = BasicDirectoryModel.this.fileCache.size();
/*     */               
/* 280 */               if (i > j) {
/*     */                 
/* 282 */                 int k = j;
/* 283 */                 int m = i;
/* 284 */                 for (byte b = 0; b < j; b++) {
/* 285 */                   if (!((File)newFileCache.get(b)).equals(BasicDirectoryModel.this.fileCache.get(b))) {
/* 286 */                     k = b;
/* 287 */                     for (byte b1 = b; b1 < i; b1++) {
/* 288 */                       if (((File)newFileCache.get(b1)).equals(BasicDirectoryModel.this.fileCache.get(b))) {
/* 289 */                         m = b1;
/*     */                         break;
/*     */                       } 
/*     */                     } 
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 296 */                 if (k >= 0 && m > k && newFileCache
/* 297 */                   .subList(m, i).equals(BasicDirectoryModel.this.fileCache.subList(k, j))) {
/* 298 */                   if (BasicDirectoryModel.LoadFilesThread.this.isInterrupted()) {
/* 299 */                     return null;
/*     */                   }
/* 301 */                   return new BasicDirectoryModel.DoChangeContents(newFileCache.subList(k, m), k, null, 0, BasicDirectoryModel.LoadFilesThread.this.fid);
/*     */                 } 
/* 303 */               } else if (i < j) {
/*     */                 
/* 305 */                 byte b = -1;
/* 306 */                 int k = -1;
/* 307 */                 for (byte b1 = 0; b1 < i; b1++) {
/* 308 */                   if (!((File)newFileCache.get(b1)).equals(BasicDirectoryModel.this.fileCache.get(b1))) {
/* 309 */                     b = b1;
/* 310 */                     k = b1 + j - i;
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 314 */                 if (b >= 0 && k > b && BasicDirectoryModel.this
/* 315 */                   .fileCache.subList(k, j).equals(newFileCache.subList(b, i))) {
/* 316 */                   if (BasicDirectoryModel.LoadFilesThread.this.isInterrupted()) {
/* 317 */                     return null;
/*     */                   }
/* 319 */                   return new BasicDirectoryModel.DoChangeContents(null, 0, new Vector<>(BasicDirectoryModel.this.fileCache.subList(b, k)), b, BasicDirectoryModel.LoadFilesThread.this.fid);
/*     */                 } 
/*     */               } 
/* 322 */               if (!BasicDirectoryModel.this.fileCache.equals(newFileCache)) {
/* 323 */                 if (BasicDirectoryModel.LoadFilesThread.this.isInterrupted()) {
/* 324 */                   BasicDirectoryModel.LoadFilesThread.this.cancelRunnables(BasicDirectoryModel.LoadFilesThread.this.runnables);
/*     */                 }
/* 326 */                 return new BasicDirectoryModel.DoChangeContents(newFileCache, 0, BasicDirectoryModel.this.fileCache, 0, BasicDirectoryModel.LoadFilesThread.this.fid);
/*     */               } 
/* 328 */               return null;
/*     */             }
/*     */           });
/*     */       
/* 332 */       if (doChangeContents != null) {
/* 333 */         this.runnables.addElement(doChangeContents);
/* 334 */         SwingUtilities.invokeLater(doChangeContents);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void cancelRunnables(Vector<BasicDirectoryModel.DoChangeContents> param1Vector) {
/* 340 */       for (BasicDirectoryModel.DoChangeContents doChangeContents : param1Vector) {
/* 341 */         doChangeContents.cancel();
/*     */       }
/*     */     }
/*     */     
/*     */     public void cancelRunnables() {
/* 346 */       cancelRunnables(this.runnables);
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
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 366 */     if (this.changeSupport == null) {
/* 367 */       this.changeSupport = new PropertyChangeSupport(this);
/*     */     }
/* 369 */     this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener);
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
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 385 */     if (this.changeSupport != null) {
/* 386 */       this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener);
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
/*     */   public PropertyChangeListener[] getPropertyChangeListeners() {
/* 405 */     if (this.changeSupport == null) {
/* 406 */       return new PropertyChangeListener[0];
/*     */     }
/* 408 */     return this.changeSupport.getPropertyChangeListeners();
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
/*     */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 425 */     if (this.changeSupport != null) {
/* 426 */       this.changeSupport.firePropertyChange(paramString, paramObject1, paramObject2);
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
/*     */   private synchronized void setBusy(final boolean busy, int paramInt) {
/* 438 */     if (paramInt == this.fetchID) {
/* 439 */       boolean bool = this.busy;
/* 440 */       this.busy = busy;
/*     */       
/* 442 */       if (this.changeSupport != null && busy != bool)
/* 443 */         SwingUtilities.invokeLater(new Runnable() {
/*     */               public void run() {
/* 445 */                 BasicDirectoryModel.this.firePropertyChange("busy", Boolean.valueOf(!busy), Boolean.valueOf(busy));
/*     */               }
/*     */             }); 
/*     */     } 
/*     */   }
/*     */   
/*     */   class DoChangeContents
/*     */     implements Runnable
/*     */   {
/*     */     private List<File> addFiles;
/*     */     private List<File> remFiles;
/*     */     private boolean doFire = true;
/*     */     private int fid;
/* 458 */     private int addStart = 0;
/* 459 */     private int remStart = 0;
/*     */     
/*     */     public DoChangeContents(List<File> param1List1, int param1Int1, List<File> param1List2, int param1Int2, int param1Int3) {
/* 462 */       this.addFiles = param1List1;
/* 463 */       this.addStart = param1Int1;
/* 464 */       this.remFiles = param1List2;
/* 465 */       this.remStart = param1Int2;
/* 466 */       this.fid = param1Int3;
/*     */     }
/*     */     
/*     */     synchronized void cancel() {
/* 470 */       this.doFire = false;
/*     */     }
/*     */     
/*     */     public synchronized void run() {
/* 474 */       if (BasicDirectoryModel.this.fetchID == this.fid && this.doFire) {
/* 475 */         byte b1 = (this.remFiles == null) ? 0 : this.remFiles.size();
/* 476 */         byte b2 = (this.addFiles == null) ? 0 : this.addFiles.size();
/* 477 */         synchronized (BasicDirectoryModel.this.fileCache) {
/* 478 */           if (b1) {
/* 479 */             BasicDirectoryModel.this.fileCache.removeAll(this.remFiles);
/*     */           }
/* 481 */           if (b2) {
/* 482 */             BasicDirectoryModel.this.fileCache.addAll(this.addStart, this.addFiles);
/*     */           }
/* 484 */           BasicDirectoryModel.this.files = null;
/* 485 */           BasicDirectoryModel.this.directories = null;
/*     */         } 
/* 487 */         if (b1 && !b2) {
/* 488 */           BasicDirectoryModel.this.fireIntervalRemoved(BasicDirectoryModel.this, this.remStart, this.remStart + b1 - 1);
/* 489 */         } else if (b2 && b1 == 0 && this.addStart + b2 <= BasicDirectoryModel.this.fileCache.size()) {
/* 490 */           BasicDirectoryModel.this.fireIntervalAdded(BasicDirectoryModel.this, this.addStart, this.addStart + b2 - 1);
/*     */         } else {
/* 492 */           BasicDirectoryModel.this.fireContentsChanged();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicDirectoryModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */