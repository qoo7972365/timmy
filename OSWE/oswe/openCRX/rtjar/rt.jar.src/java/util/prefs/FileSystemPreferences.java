/*      */ package java.util.prefs;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Timer;
/*      */ import java.util.TimerTask;
/*      */ import java.util.TreeMap;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class FileSystemPreferences
/*      */   extends AbstractPreferences
/*      */ {
/*   54 */   private static final int SYNC_INTERVAL = Math.max(1, 
/*   55 */       Integer.parseInt(
/*   56 */         AccessController.<String>doPrivileged(new GetPropertyAction("java.util.prefs.syncInterval", "30"))));
/*      */   private static File systemRootDir;
/*      */   private static boolean isSystemRootWritable;
/*      */   private static File userRootDir;
/*      */   private static boolean isUserRootWritable;
/*      */   private static volatile Preferences userRoot;
/*      */   private static volatile Preferences systemRoot;
/*      */   
/*      */   private static PlatformLogger getLogger() {
/*   65 */     return PlatformLogger.getLogger("java.util.prefs");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int USER_READ_WRITE = 384;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int USER_RW_ALL_READ = 420;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int USER_RWX_ALL_RX = 493;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int USER_RWX = 448;
/*      */ 
/*      */   
/*      */   static File userLockFile;
/*      */ 
/*      */   
/*      */   static File systemLockFile;
/*      */ 
/*      */ 
/*      */   
/*      */   static Preferences getUserRoot() {
/*   94 */     Preferences preferences = userRoot;
/*   95 */     if (preferences == null) {
/*   96 */       synchronized (FileSystemPreferences.class) {
/*   97 */         preferences = userRoot;
/*   98 */         if (preferences == null) {
/*   99 */           setupUserRoot();
/*  100 */           userRoot = preferences = new FileSystemPreferences(true);
/*      */         } 
/*      */       } 
/*      */     }
/*  104 */     return preferences;
/*      */   }
/*      */   
/*      */   private static void setupUserRoot() {
/*  108 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/*  110 */             FileSystemPreferences.userRootDir = new File(
/*  111 */                 System.getProperty("java.util.prefs.userRoot", 
/*  112 */                   System.getProperty("user.home")), ".java/.userPrefs");
/*      */             
/*  114 */             if (!FileSystemPreferences.userRootDir.exists()) {
/*  115 */               if (FileSystemPreferences.userRootDir.mkdirs()) {
/*      */                 try {
/*  117 */                   FileSystemPreferences.chmod(FileSystemPreferences.userRootDir.getCanonicalPath(), 448);
/*  118 */                 } catch (IOException iOException) {
/*  119 */                   FileSystemPreferences.getLogger().warning("Could not change permissions on userRoot directory. ");
/*      */                 } 
/*      */                 
/*  122 */                 FileSystemPreferences.getLogger().info("Created user preferences directory.");
/*      */               } else {
/*      */                 
/*  125 */                 FileSystemPreferences.getLogger().warning("Couldn't create user preferences directory. User preferences are unusable.");
/*      */               } 
/*      */             }
/*  128 */             FileSystemPreferences.isUserRootWritable = FileSystemPreferences.userRootDir.canWrite();
/*  129 */             String str = System.getProperty("user.name");
/*  130 */             FileSystemPreferences.userLockFile = new File(FileSystemPreferences.userRootDir, ".user.lock." + str);
/*  131 */             FileSystemPreferences.userRootModFile = new File(FileSystemPreferences.userRootDir, ".userRootModFile." + str);
/*      */             
/*  133 */             if (!FileSystemPreferences.userRootModFile.exists())
/*      */               
/*      */               try {
/*  136 */                 FileSystemPreferences.userRootModFile.createNewFile();
/*      */                 
/*  138 */                 int i = FileSystemPreferences.chmod(FileSystemPreferences.userRootModFile.getCanonicalPath(), 384);
/*      */                 
/*  140 */                 if (i != 0) {
/*  141 */                   FileSystemPreferences.getLogger().warning("Problem creating userRoot mod file. Chmod failed on " + FileSystemPreferences
/*      */                       
/*  143 */                       .userRootModFile.getCanonicalPath() + " Unix error code " + i);
/*      */                 }
/*  145 */               } catch (IOException iOException) {
/*  146 */                 FileSystemPreferences.getLogger().warning(iOException.toString());
/*      */               }  
/*  148 */             FileSystemPreferences.userRootModTime = FileSystemPreferences.userRootModFile.lastModified();
/*  149 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Preferences getSystemRoot() {
/*  161 */     Preferences preferences = systemRoot;
/*  162 */     if (preferences == null) {
/*  163 */       synchronized (FileSystemPreferences.class) {
/*  164 */         preferences = systemRoot;
/*  165 */         if (preferences == null) {
/*  166 */           setupSystemRoot();
/*  167 */           systemRoot = preferences = new FileSystemPreferences(false);
/*      */         } 
/*      */       } 
/*      */     }
/*  171 */     return preferences;
/*      */   }
/*      */   
/*      */   private static void setupSystemRoot() {
/*  175 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */         {
/*      */           public Void run() {
/*  178 */             String str = System.getProperty("java.util.prefs.systemRoot", "/etc/.java");
/*  179 */             FileSystemPreferences.systemRootDir = new File(str, ".systemPrefs");
/*      */ 
/*      */             
/*  182 */             if (!FileSystemPreferences.systemRootDir.exists()) {
/*      */ 
/*      */               
/*  185 */               FileSystemPreferences.systemRootDir = new File(
/*  186 */                   System.getProperty("java.home"), ".systemPrefs");
/*      */               
/*  188 */               if (!FileSystemPreferences.systemRootDir.exists()) {
/*  189 */                 if (FileSystemPreferences.systemRootDir.mkdirs()) {
/*  190 */                   FileSystemPreferences.getLogger().info("Created system preferences directory in java.home.");
/*      */ 
/*      */                   
/*      */                   try {
/*  194 */                     FileSystemPreferences.chmod(FileSystemPreferences.systemRootDir.getCanonicalPath(), 493);
/*      */                   }
/*  196 */                   catch (IOException iOException) {}
/*      */                 } else {
/*      */                   
/*  199 */                   FileSystemPreferences.getLogger().warning("Could not create system preferences directory. System preferences are unusable.");
/*      */                 } 
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/*  205 */             FileSystemPreferences.isSystemRootWritable = FileSystemPreferences.systemRootDir.canWrite();
/*  206 */             FileSystemPreferences.systemLockFile = new File(FileSystemPreferences.systemRootDir, ".system.lock");
/*  207 */             FileSystemPreferences.systemRootModFile = new File(FileSystemPreferences
/*  208 */                 .systemRootDir, ".systemRootModFile");
/*  209 */             if (!FileSystemPreferences.systemRootModFile.exists() && FileSystemPreferences.isSystemRootWritable)
/*      */               
/*      */               try {
/*  212 */                 FileSystemPreferences.systemRootModFile.createNewFile();
/*  213 */                 int i = FileSystemPreferences.chmod(FileSystemPreferences.systemRootModFile.getCanonicalPath(), 420);
/*      */                 
/*  215 */                 if (i != 0)
/*  216 */                   FileSystemPreferences.getLogger().warning("Chmod failed on " + FileSystemPreferences
/*  217 */                       .systemRootModFile.getCanonicalPath() + " Unix error code " + i); 
/*      */               } catch (IOException iOException) {
/*  219 */                 FileSystemPreferences.getLogger().warning(iOException.toString());
/*      */               }  
/*  221 */             FileSystemPreferences.systemRootModTime = FileSystemPreferences.systemRootModFile.lastModified();
/*  222 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  257 */   private static int userRootLockHandle = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  264 */   private static int systemRootLockHandle = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final File dir;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final File prefsFile;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final File tmpFile;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static File userRootModFile;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isUserRootModified = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long userRootModTime;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static File systemRootModFile;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isSystemRootModified = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long systemRootModTime;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  333 */   private Map<String, String> prefsCache = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  344 */   private long lastSyncTime = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int EAGAIN = 11;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int EACCES = 13;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int LOCK_HANDLE = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ERROR_CODE = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  369 */   final List<Change> changeLog = new ArrayList<>();
/*      */ 
/*      */   
/*      */   private abstract class Change
/*      */   {
/*      */     private Change() {}
/*      */ 
/*      */     
/*      */     abstract void replay();
/*      */   }
/*      */ 
/*      */   
/*      */   private class Put
/*      */     extends Change
/*      */   {
/*      */     String key;
/*      */     String value;
/*      */     
/*      */     Put(String param1String1, String param1String2) {
/*  388 */       this.key = param1String1;
/*  389 */       this.value = param1String2;
/*      */     }
/*      */     
/*      */     void replay() {
/*  393 */       FileSystemPreferences.this.prefsCache.put(this.key, this.value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class Remove
/*      */     extends Change
/*      */   {
/*      */     String key;
/*      */     
/*      */     Remove(String param1String) {
/*  404 */       this.key = param1String;
/*      */     }
/*      */     
/*      */     void replay() {
/*  408 */       FileSystemPreferences.this.prefsCache.remove(this.key);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class NodeCreate
/*      */     extends Change
/*      */   {
/*      */     private NodeCreate() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void replay() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  428 */   NodeCreate nodeCreate = null;
/*      */ 
/*      */   
/*      */   private void replayChanges() {
/*      */     byte b;
/*      */     int i;
/*  434 */     for (b = 0, i = this.changeLog.size(); b < i; b++)
/*  435 */       ((Change)this.changeLog.get(b)).replay(); 
/*      */   }
/*      */   
/*  438 */   private static Timer syncTimer = new Timer(true);
/*      */   private final boolean isUserNode;
/*      */   
/*      */   static {
/*  442 */     syncTimer.schedule(new TimerTask() {
/*      */           public void run() {
/*  444 */             FileSystemPreferences.syncWorld();
/*      */           }
/*      */         },  (SYNC_INTERVAL * 1000), (SYNC_INTERVAL * 1000));
/*      */ 
/*      */     
/*  449 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/*  451 */             Runtime.getRuntime().addShutdownHook(new Thread() {
/*      */                   public void run() {
/*  453 */                     FileSystemPreferences.syncTimer.cancel();
/*  454 */                     FileSystemPreferences.syncWorld();
/*      */                   }
/*      */                 });
/*  457 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void syncWorld() {
/*      */     Preferences preferences1;
/*      */     Preferences preferences2;
/*  469 */     synchronized (FileSystemPreferences.class) {
/*  470 */       preferences1 = userRoot;
/*  471 */       preferences2 = systemRoot;
/*      */     } 
/*      */     
/*      */     try {
/*  475 */       if (preferences1 != null)
/*  476 */         preferences1.flush(); 
/*  477 */     } catch (BackingStoreException backingStoreException) {
/*  478 */       getLogger().warning("Couldn't flush user prefs: " + backingStoreException);
/*      */     } 
/*      */     
/*      */     try {
/*  482 */       if (preferences2 != null)
/*  483 */         preferences2.flush(); 
/*  484 */     } catch (BackingStoreException backingStoreException) {
/*  485 */       getLogger().warning("Couldn't flush system prefs: " + backingStoreException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FileSystemPreferences(boolean paramBoolean) {
/*  496 */     super(null, "");
/*  497 */     this.isUserNode = paramBoolean;
/*  498 */     this.dir = paramBoolean ? userRootDir : systemRootDir;
/*  499 */     this.prefsFile = new File(this.dir, "prefs.xml");
/*  500 */     this.tmpFile = new File(this.dir, "prefs.tmp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FileSystemPreferences(FileSystemPreferences paramFileSystemPreferences, String paramString) {
/*  509 */     super(paramFileSystemPreferences, paramString);
/*  510 */     this.isUserNode = paramFileSystemPreferences.isUserNode;
/*  511 */     this.dir = new File(paramFileSystemPreferences.dir, dirName(paramString));
/*  512 */     this.prefsFile = new File(this.dir, "prefs.xml");
/*  513 */     this.tmpFile = new File(this.dir, "prefs.tmp");
/*  514 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/*  516 */             FileSystemPreferences.this.newNode = !FileSystemPreferences.this.dir.exists();
/*  517 */             return null;
/*      */           }
/*      */         });
/*  520 */     if (this.newNode) {
/*      */       
/*  522 */       this.prefsCache = new TreeMap<>();
/*  523 */       this.nodeCreate = new NodeCreate();
/*  524 */       this.changeLog.add(this.nodeCreate);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isUserNode() {
/*  529 */     return this.isUserNode;
/*      */   }
/*      */   
/*      */   protected void putSpi(String paramString1, String paramString2) {
/*  533 */     initCacheIfNecessary();
/*  534 */     this.changeLog.add(new Put(paramString1, paramString2));
/*  535 */     this.prefsCache.put(paramString1, paramString2);
/*      */   }
/*      */   
/*      */   protected String getSpi(String paramString) {
/*  539 */     initCacheIfNecessary();
/*  540 */     return this.prefsCache.get(paramString);
/*      */   }
/*      */   
/*      */   protected void removeSpi(String paramString) {
/*  544 */     initCacheIfNecessary();
/*  545 */     this.changeLog.add(new Remove(paramString));
/*  546 */     this.prefsCache.remove(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initCacheIfNecessary() {
/*  558 */     if (this.prefsCache != null) {
/*      */       return;
/*      */     }
/*      */     try {
/*  562 */       loadCache();
/*  563 */     } catch (Exception exception) {
/*      */       
/*  565 */       this.prefsCache = new TreeMap<>();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadCache() throws BackingStoreException {
/*      */     try {
/*  579 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */           {
/*      */             public Void run() throws BackingStoreException {
/*  582 */               TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  583 */               long l = 0L;
/*      */               try {
/*  585 */                 l = FileSystemPreferences.this.prefsFile.lastModified();
/*  586 */                 try (FileInputStream null = new FileInputStream(FileSystemPreferences.this.prefsFile)) {
/*  587 */                   XmlSupport.importMap(fileInputStream, (Map)treeMap);
/*      */                 } 
/*  589 */               } catch (Exception exception) {
/*  590 */                 if (exception instanceof InvalidPreferencesFormatException) {
/*  591 */                   FileSystemPreferences.getLogger().warning("Invalid preferences format in " + FileSystemPreferences.this
/*  592 */                       .prefsFile.getPath());
/*  593 */                   FileSystemPreferences.this.prefsFile.renameTo(new File(FileSystemPreferences.this
/*  594 */                         .prefsFile.getParentFile(), "IncorrectFormatPrefs.xml"));
/*      */                   
/*  596 */                   treeMap = new TreeMap<>();
/*  597 */                 } else if (exception instanceof java.io.FileNotFoundException) {
/*  598 */                   FileSystemPreferences.getLogger().warning("Prefs file removed in background " + FileSystemPreferences.this
/*  599 */                       .prefsFile.getPath());
/*      */                 } else {
/*  601 */                   throw new BackingStoreException(exception);
/*      */                 } 
/*      */               } 
/*      */               
/*  605 */               FileSystemPreferences.this.prefsCache = (Map)treeMap;
/*  606 */               FileSystemPreferences.this.lastSyncTime = l;
/*  607 */               return null;
/*      */             }
/*      */           });
/*  610 */     } catch (PrivilegedActionException privilegedActionException) {
/*  611 */       throw (BackingStoreException)privilegedActionException.getException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeBackCache() throws BackingStoreException {
/*      */     try {
/*  626 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */           {
/*      */             public Void run() throws BackingStoreException {
/*      */               try {
/*  630 */                 if (!FileSystemPreferences.this.dir.exists() && !FileSystemPreferences.this.dir.mkdirs()) {
/*  631 */                   throw new BackingStoreException(FileSystemPreferences.this.dir + " create failed.");
/*      */                 }
/*  633 */                 try (FileOutputStream null = new FileOutputStream(FileSystemPreferences.this.tmpFile)) {
/*  634 */                   XmlSupport.exportMap(fileOutputStream, FileSystemPreferences.this.prefsCache);
/*      */                 } 
/*  636 */                 if (!FileSystemPreferences.this.tmpFile.renameTo(FileSystemPreferences.this.prefsFile))
/*  637 */                   throw new BackingStoreException("Can't rename " + FileSystemPreferences.this
/*  638 */                       .tmpFile + " to " + FileSystemPreferences.this.prefsFile); 
/*  639 */               } catch (Exception exception) {
/*  640 */                 if (exception instanceof BackingStoreException)
/*  641 */                   throw (BackingStoreException)exception; 
/*  642 */                 throw new BackingStoreException(exception);
/*      */               } 
/*  644 */               return null;
/*      */             }
/*      */           });
/*  647 */     } catch (PrivilegedActionException privilegedActionException) {
/*  648 */       throw (BackingStoreException)privilegedActionException.getException();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String[] keysSpi() {
/*  653 */     initCacheIfNecessary();
/*  654 */     return (String[])this.prefsCache.keySet().toArray((Object[])new String[this.prefsCache.size()]);
/*      */   }
/*      */   
/*      */   protected String[] childrenNamesSpi() {
/*  658 */     return AccessController.<String[]>doPrivileged((PrivilegedAction)new PrivilegedAction<String[]>()
/*      */         {
/*      */           public String[] run() {
/*  661 */             ArrayList<String> arrayList = new ArrayList();
/*  662 */             File[] arrayOfFile = FileSystemPreferences.this.dir.listFiles();
/*  663 */             if (arrayOfFile != null)
/*  664 */               for (byte b = 0; b < arrayOfFile.length; b++) {
/*  665 */                 if (arrayOfFile[b].isDirectory())
/*  666 */                   arrayList.add(FileSystemPreferences.nodeName(arrayOfFile[b].getName())); 
/*      */               }  
/*  668 */             return arrayList.<String>toArray(FileSystemPreferences.EMPTY_STRING_ARRAY);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*  673 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/*      */   
/*      */   protected AbstractPreferences childSpi(String paramString) {
/*  676 */     return new FileSystemPreferences(this, paramString);
/*      */   }
/*      */   
/*      */   public void removeNode() throws BackingStoreException {
/*  680 */     synchronized (isUserNode() ? userLockFile : systemLockFile) {
/*      */       
/*  682 */       if (!lockFile(false))
/*  683 */         throw new BackingStoreException("Couldn't get file lock."); 
/*      */       try {
/*  685 */         super.removeNode();
/*      */       } finally {
/*  687 */         unlockFile();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeNodeSpi() throws BackingStoreException {
/*      */     try {
/*  697 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */           {
/*      */             public Void run() throws BackingStoreException {
/*  700 */               if (FileSystemPreferences.this.changeLog.contains(FileSystemPreferences.this.nodeCreate)) {
/*  701 */                 FileSystemPreferences.this.changeLog.remove(FileSystemPreferences.this.nodeCreate);
/*  702 */                 FileSystemPreferences.this.nodeCreate = null;
/*  703 */                 return null;
/*      */               } 
/*  705 */               if (!FileSystemPreferences.this.dir.exists())
/*  706 */                 return null; 
/*  707 */               FileSystemPreferences.this.prefsFile.delete();
/*  708 */               FileSystemPreferences.this.tmpFile.delete();
/*      */               
/*  710 */               File[] arrayOfFile = FileSystemPreferences.this.dir.listFiles();
/*  711 */               if (arrayOfFile.length != 0) {
/*  712 */                 FileSystemPreferences.getLogger().warning("Found extraneous files when removing node: " + 
/*      */                     
/*  714 */                     Arrays.asList(arrayOfFile));
/*  715 */                 for (byte b = 0; b < arrayOfFile.length; b++)
/*  716 */                   arrayOfFile[b].delete(); 
/*      */               } 
/*  718 */               if (!FileSystemPreferences.this.dir.delete())
/*  719 */                 throw new BackingStoreException("Couldn't delete dir: " + FileSystemPreferences.this
/*  720 */                     .dir); 
/*  721 */               return null;
/*      */             }
/*      */           });
/*  724 */     } catch (PrivilegedActionException privilegedActionException) {
/*  725 */       throw (BackingStoreException)privilegedActionException.getException();
/*      */     } 
/*      */   }
/*      */   public synchronized void sync() throws BackingStoreException {
/*      */     boolean bool1;
/*  730 */     boolean bool = isUserNode();
/*      */ 
/*      */     
/*  733 */     if (bool) {
/*  734 */       bool1 = false;
/*      */     }
/*      */     else {
/*      */       
/*  738 */       bool1 = !isSystemRootWritable ? true : false;
/*      */     } 
/*  740 */     synchronized (isUserNode() ? userLockFile : systemLockFile) {
/*  741 */       if (!lockFile(bool1)) {
/*  742 */         throw new BackingStoreException("Couldn't get file lock.");
/*      */       }
/*  744 */       final Long newModTime = AccessController.<Long>doPrivileged(new PrivilegedAction<Long>()
/*      */           {
/*      */             public Long run() {
/*      */               long l;
/*  748 */               if (FileSystemPreferences.this.isUserNode()) {
/*  749 */                 l = FileSystemPreferences.userRootModFile.lastModified();
/*  750 */                 FileSystemPreferences.isUserRootModified = (FileSystemPreferences.userRootModTime == l);
/*      */               } else {
/*  752 */                 l = FileSystemPreferences.systemRootModFile.lastModified();
/*  753 */                 FileSystemPreferences.isSystemRootModified = (FileSystemPreferences.systemRootModTime == l);
/*      */               } 
/*  755 */               return new Long(l);
/*      */             }
/*      */           });
/*      */       try {
/*  759 */         super.sync();
/*  760 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */               public Void run() {
/*  762 */                 if (FileSystemPreferences.this.isUserNode()) {
/*  763 */                   FileSystemPreferences.userRootModTime = newModTime.longValue() + 1000L;
/*  764 */                   FileSystemPreferences.userRootModFile.setLastModified(FileSystemPreferences.userRootModTime);
/*      */                 } else {
/*  766 */                   FileSystemPreferences.systemRootModTime = newModTime.longValue() + 1000L;
/*  767 */                   FileSystemPreferences.systemRootModFile.setLastModified(FileSystemPreferences.systemRootModTime);
/*      */                 } 
/*  769 */                 return null;
/*      */               }
/*      */             });
/*      */       } finally {
/*  773 */         unlockFile();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void syncSpi() throws BackingStoreException {
/*      */     try {
/*  780 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */           {
/*      */             public Void run() throws BackingStoreException {
/*  783 */               FileSystemPreferences.this.syncSpiPrivileged();
/*  784 */               return null;
/*      */             }
/*      */           });
/*  787 */     } catch (PrivilegedActionException privilegedActionException) {
/*  788 */       throw (BackingStoreException)privilegedActionException.getException();
/*      */     } 
/*      */   }
/*      */   private void syncSpiPrivileged() throws BackingStoreException {
/*  792 */     if (isRemoved())
/*  793 */       throw new IllegalStateException("Node has been removed"); 
/*  794 */     if (this.prefsCache == null) {
/*      */       return;
/*      */     }
/*  797 */     if (isUserNode() ? isUserRootModified : isSystemRootModified) {
/*  798 */       long l = this.prefsFile.lastModified();
/*  799 */       if (l != this.lastSyncTime) {
/*      */ 
/*      */         
/*  802 */         loadCache();
/*  803 */         replayChanges();
/*  804 */         this.lastSyncTime = l;
/*      */       } 
/*  806 */     } else if (this.lastSyncTime != 0L && !this.dir.exists()) {
/*      */ 
/*      */       
/*  809 */       this.prefsCache = new TreeMap<>();
/*  810 */       replayChanges();
/*      */     } 
/*  812 */     if (!this.changeLog.isEmpty()) {
/*  813 */       writeBackCache();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  819 */       long l = this.prefsFile.lastModified();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  825 */       if (this.lastSyncTime <= l) {
/*  826 */         this.lastSyncTime = l + 1000L;
/*  827 */         this.prefsFile.setLastModified(this.lastSyncTime);
/*      */       } 
/*  829 */       this.changeLog.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void flush() throws BackingStoreException {
/*  834 */     if (isRemoved())
/*      */       return; 
/*  836 */     sync();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void flushSpi() throws BackingStoreException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isDirChar(char paramChar) {
/*  850 */     return (paramChar > '\037' && paramChar < '' && paramChar != '/' && paramChar != '.' && paramChar != '_');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String dirName(String paramString) {
/*      */     byte b;
/*      */     int i;
/*  860 */     for (b = 0, i = paramString.length(); b < i; b++) {
/*  861 */       if (!isDirChar(paramString.charAt(b)))
/*  862 */         return "_" + Base64.byteArrayToAltBase64(byteArray(paramString)); 
/*  863 */     }  return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] byteArray(String paramString) {
/*  871 */     int i = paramString.length();
/*  872 */     byte[] arrayOfByte = new byte[2 * i];
/*  873 */     for (byte b1 = 0, b2 = 0; b1 < i; b1++) {
/*  874 */       char c = paramString.charAt(b1);
/*  875 */       arrayOfByte[b2++] = (byte)(c >> 8);
/*  876 */       arrayOfByte[b2++] = (byte)c;
/*      */     } 
/*  878 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String nodeName(String paramString) {
/*  886 */     if (paramString.charAt(0) != '_')
/*  887 */       return paramString; 
/*  888 */     byte[] arrayOfByte = Base64.altBase64ToByteArray(paramString.substring(1));
/*  889 */     StringBuffer stringBuffer = new StringBuffer(arrayOfByte.length / 2);
/*  890 */     for (byte b = 0; b < arrayOfByte.length; ) {
/*  891 */       int i = arrayOfByte[b++] & 0xFF;
/*  892 */       int j = arrayOfByte[b++] & 0xFF;
/*  893 */       stringBuffer.append((char)(i << 8 | j));
/*      */     } 
/*  895 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean lockFile(boolean paramBoolean) throws SecurityException {
/*  906 */     boolean bool = isUserNode();
/*      */     
/*  908 */     int i = 0;
/*  909 */     File file = bool ? userLockFile : systemLockFile;
/*  910 */     long l = INIT_SLEEP_TIME;
/*  911 */     for (byte b = 0; b < MAX_ATTEMPTS; b++) {
/*      */       try {
/*  913 */         char c = bool ? 'ƀ' : 'Ƥ';
/*  914 */         int[] arrayOfInt = lockFile0(file.getCanonicalPath(), c, paramBoolean);
/*      */         
/*  916 */         i = arrayOfInt[1];
/*  917 */         if (arrayOfInt[0] != 0) {
/*  918 */           if (bool) {
/*  919 */             userRootLockHandle = arrayOfInt[0];
/*      */           } else {
/*  921 */             systemRootLockHandle = arrayOfInt[0];
/*      */           } 
/*  923 */           return true;
/*      */         } 
/*  925 */       } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  930 */         Thread.sleep(l);
/*  931 */       } catch (InterruptedException interruptedException) {
/*  932 */         checkLockFile0ErrorCode(i);
/*  933 */         return false;
/*      */       } 
/*  935 */       l *= 2L;
/*      */     } 
/*  937 */     checkLockFile0ErrorCode(i);
/*  938 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkLockFile0ErrorCode(int paramInt) throws SecurityException {
/*  947 */     if (paramInt == 13) {
/*  948 */       throw new SecurityException("Could not lock " + (
/*  949 */           isUserNode() ? "User prefs." : "System prefs.") + " Lock file access denied.");
/*      */     }
/*  951 */     if (paramInt != 11) {
/*  952 */       getLogger().warning("Could not lock " + (
/*  953 */           isUserNode() ? "User prefs. " : "System prefs.") + " Unix error code " + paramInt + ".");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  981 */   private static int INIT_SLEEP_TIME = 50;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  986 */   private static int MAX_ATTEMPTS = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unlockFile() {
/*  994 */     boolean bool = isUserNode();
/*  995 */     File file = bool ? userLockFile : systemLockFile;
/*  996 */     int j = bool ? userRootLockHandle : systemRootLockHandle;
/*  997 */     if (j == 0) {
/*  998 */       getLogger().warning("Unlock: zero lockHandle for " + (bool ? "user" : "system") + " preferences.)");
/*      */       
/*      */       return;
/*      */     } 
/* 1002 */     int i = unlockFile0(j);
/* 1003 */     if (i != 0) {
/* 1004 */       getLogger().warning("Could not drop file-lock on " + (
/* 1005 */           isUserNode() ? "user" : "system") + " preferences. Unix error code " + i + ".");
/*      */       
/* 1007 */       if (i == 13) {
/* 1008 */         throw new SecurityException("Could not unlock" + (
/* 1009 */             isUserNode() ? "User prefs." : "System prefs.") + " Lock file access denied.");
/*      */       }
/*      */     } 
/* 1012 */     if (isUserNode()) {
/* 1013 */       userRootLockHandle = 0;
/*      */     } else {
/* 1015 */       systemRootLockHandle = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static native int[] lockFile0(String paramString, int paramInt, boolean paramBoolean);
/*      */   
/*      */   private static native int unlockFile0(int paramInt);
/*      */   
/*      */   private static native int chmod(String paramString, int paramInt);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/prefs/FileSystemPreferences.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */