/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.WatchService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LinuxFileSystem
/*     */   extends UnixFileSystem
/*     */ {
/*     */   LinuxFileSystem(UnixFileSystemProvider paramUnixFileSystemProvider, String paramString) {
/*  39 */     super(paramUnixFileSystemProvider, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WatchService newWatchService() throws IOException {
/*  47 */     return new LinuxWatchService(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SupportedFileFileAttributeViewsHolder
/*     */   {
/*  54 */     static final Set<String> supportedFileAttributeViews = supportedFileAttributeViews();
/*     */     private static Set<String> supportedFileAttributeViews() {
/*  56 */       HashSet<String> hashSet = new HashSet();
/*  57 */       hashSet.addAll(UnixFileSystem.standardFileAttributeViews());
/*     */       
/*  59 */       hashSet.add("dos");
/*  60 */       hashSet.add("user");
/*  61 */       return Collections.unmodifiableSet(hashSet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> supportedFileAttributeViews() {
/*  67 */     return SupportedFileFileAttributeViewsHolder.supportedFileAttributeViews;
/*     */   }
/*     */ 
/*     */   
/*     */   void copyNonPosixAttributes(int paramInt1, int paramInt2) {
/*  72 */     LinuxUserDefinedFileAttributeView.copyExtendedAttributes(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<UnixMountEntry> getMountEntries(String paramString) {
/*  79 */     ArrayList<UnixMountEntry> arrayList = new ArrayList();
/*     */     try {
/*  81 */       long l = LinuxNativeDispatcher.setmntent(Util.toBytes(paramString), Util.toBytes("r"));
/*     */       try {
/*     */         while (true) {
/*  84 */           UnixMountEntry unixMountEntry = new UnixMountEntry();
/*  85 */           int i = LinuxNativeDispatcher.getmntent(l, unixMountEntry);
/*  86 */           if (i < 0)
/*     */             break; 
/*  88 */           arrayList.add(unixMountEntry);
/*     */         } 
/*     */       } finally {
/*  91 */         LinuxNativeDispatcher.endmntent(l);
/*     */       }
/*     */     
/*  94 */     } catch (UnixException unixException) {}
/*     */ 
/*     */     
/*  97 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<UnixMountEntry> getMountEntries() {
/* 105 */     return getMountEntries("/etc/mtab");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FileStore getFileStore(UnixMountEntry paramUnixMountEntry) throws IOException {
/* 112 */     return new LinuxFileStore(this, paramUnixMountEntry);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/LinuxFileSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */