/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.BasicFileAttributeView;
/*     */ import java.nio.file.attribute.FileAttributeView;
/*     */ import java.nio.file.attribute.FileOwnerAttributeView;
/*     */ import java.nio.file.attribute.PosixFileAttributeView;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class UnixFileStore
/*     */   extends FileStore
/*     */ {
/*     */   private final UnixPath file;
/*     */   private final long dev;
/*     */   private final UnixMountEntry entry;
/*     */   
/*     */   private static long devFor(UnixPath paramUnixPath) throws IOException {
/*     */     try {
/*  55 */       return UnixFileAttributes.get(paramUnixPath, true).dev();
/*  56 */     } catch (UnixException unixException) {
/*  57 */       unixException.rethrowAsIOException(paramUnixPath);
/*  58 */       return 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   UnixFileStore(UnixPath paramUnixPath) throws IOException {
/*  63 */     this.file = paramUnixPath;
/*  64 */     this.dev = devFor(paramUnixPath);
/*  65 */     this.entry = findMountEntry();
/*     */   }
/*     */   
/*     */   UnixFileStore(UnixFileSystem paramUnixFileSystem, UnixMountEntry paramUnixMountEntry) throws IOException {
/*  69 */     this.file = new UnixPath(paramUnixFileSystem, paramUnixMountEntry.dir());
/*  70 */     this.dev = (paramUnixMountEntry.dev() == 0L) ? devFor(this.file) : paramUnixMountEntry.dev();
/*  71 */     this.entry = paramUnixMountEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   UnixPath file() {
/*  80 */     return this.file;
/*     */   }
/*     */   
/*     */   long dev() {
/*  84 */     return this.dev;
/*     */   }
/*     */   
/*     */   UnixMountEntry entry() {
/*  88 */     return this.entry;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  93 */     return this.entry.name();
/*     */   }
/*     */ 
/*     */   
/*     */   public String type() {
/*  98 */     return this.entry.fstype();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 103 */     return this.entry.isReadOnly();
/*     */   }
/*     */ 
/*     */   
/*     */   private UnixFileStoreAttributes readAttributes() throws IOException {
/*     */     try {
/* 109 */       return UnixFileStoreAttributes.get(this.file);
/* 110 */     } catch (UnixException unixException) {
/* 111 */       unixException.rethrowAsIOException(this.file);
/* 112 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTotalSpace() throws IOException {
/* 118 */     UnixFileStoreAttributes unixFileStoreAttributes = readAttributes();
/* 119 */     return unixFileStoreAttributes.blockSize() * unixFileStoreAttributes.totalBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUsableSpace() throws IOException {
/* 124 */     UnixFileStoreAttributes unixFileStoreAttributes = readAttributes();
/* 125 */     return unixFileStoreAttributes.blockSize() * unixFileStoreAttributes.availableBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUnallocatedSpace() throws IOException {
/* 130 */     UnixFileStoreAttributes unixFileStoreAttributes = readAttributes();
/* 131 */     return unixFileStoreAttributes.blockSize() * unixFileStoreAttributes.freeBlocks();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <V extends java.nio.file.attribute.FileStoreAttributeView> V getFileStoreAttributeView(Class<V> paramClass) {
/* 137 */     if (paramClass == null)
/* 138 */       throw new NullPointerException(); 
/* 139 */     return (V)null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getAttribute(String paramString) throws IOException {
/* 144 */     if (paramString.equals("totalSpace"))
/* 145 */       return Long.valueOf(getTotalSpace()); 
/* 146 */     if (paramString.equals("usableSpace"))
/* 147 */       return Long.valueOf(getUsableSpace()); 
/* 148 */     if (paramString.equals("unallocatedSpace"))
/* 149 */       return Long.valueOf(getUnallocatedSpace()); 
/* 150 */     throw new UnsupportedOperationException("'" + paramString + "' not recognized");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsFileAttributeView(Class<? extends FileAttributeView> paramClass) {
/* 155 */     if (paramClass == null)
/* 156 */       throw new NullPointerException(); 
/* 157 */     if (paramClass == BasicFileAttributeView.class)
/* 158 */       return true; 
/* 159 */     if (paramClass == PosixFileAttributeView.class || paramClass == FileOwnerAttributeView.class) {
/*     */ 
/*     */ 
/*     */       
/* 163 */       FeatureStatus featureStatus = checkIfFeaturePresent("posix");
/*     */       
/* 165 */       return (featureStatus != FeatureStatus.NOT_PRESENT);
/*     */     } 
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsFileAttributeView(String paramString) {
/* 172 */     if (paramString.equals("basic") || paramString.equals("unix"))
/* 173 */       return true; 
/* 174 */     if (paramString.equals("posix"))
/* 175 */       return supportsFileAttributeView((Class)PosixFileAttributeView.class); 
/* 176 */     if (paramString.equals("owner"))
/* 177 */       return supportsFileAttributeView((Class)FileOwnerAttributeView.class); 
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 183 */     if (paramObject == this)
/* 184 */       return true; 
/* 185 */     if (!(paramObject instanceof UnixFileStore))
/* 186 */       return false; 
/* 187 */     UnixFileStore unixFileStore = (UnixFileStore)paramObject;
/* 188 */     return (this.dev == unixFileStore.dev && 
/* 189 */       Arrays.equals(this.entry.dir(), unixFileStore.entry.dir()) && this.entry
/* 190 */       .name().equals(unixFileStore.entry.name()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 195 */     return (int)(this.dev ^ this.dev >>> 32L) ^ Arrays.hashCode(this.entry.dir());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 200 */     StringBuilder stringBuilder = new StringBuilder(Util.toString(this.entry.dir()));
/* 201 */     stringBuilder.append(" (");
/* 202 */     stringBuilder.append(this.entry.name());
/* 203 */     stringBuilder.append(")");
/* 204 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 209 */   private static final Object loadLock = new Object();
/*     */   private static volatile Properties props;
/*     */   
/*     */   enum FeatureStatus {
/* 213 */     PRESENT,
/* 214 */     NOT_PRESENT,
/* 215 */     UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FeatureStatus checkIfFeaturePresent(String paramString) {
/* 222 */     if (props == null) {
/* 223 */       synchronized (loadLock) {
/* 224 */         if (props == null) {
/* 225 */           props = AccessController.<Properties>doPrivileged(new PrivilegedAction<Properties>()
/*     */               {
/*     */                 public Properties run()
/*     */                 {
/* 229 */                   return UnixFileStore.loadProperties();
/*     */                 }
/*     */               });
/*     */         }
/*     */       } 
/*     */     }
/* 235 */     String str = props.getProperty(type());
/* 236 */     if (str != null) {
/* 237 */       String[] arrayOfString = str.split("\\s");
/* 238 */       for (String str1 : arrayOfString) {
/* 239 */         str1 = str1.trim().toLowerCase();
/* 240 */         if (str1.equals(paramString)) {
/* 241 */           return FeatureStatus.PRESENT;
/*     */         }
/* 243 */         if (str1.startsWith("no")) {
/* 244 */           str1 = str1.substring(2);
/* 245 */           if (str1.equals(paramString)) {
/* 246 */             return FeatureStatus.NOT_PRESENT;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 251 */     return FeatureStatus.UNKNOWN;
/*     */   }
/*     */   
/*     */   private static Properties loadProperties() {
/* 255 */     Properties properties = new Properties();
/* 256 */     String str = System.getProperty("java.home") + "/lib/fstypes.properties";
/* 257 */     Path path = Paths.get(str, new String[0]);
/*     */     
/* 259 */     try (SeekableByteChannel null = Files.newByteChannel(path, new java.nio.file.OpenOption[0])) {
/* 260 */       properties.load(Channels.newReader(seekableByteChannel, "UTF-8"));
/*     */     }
/* 262 */     catch (IOException iOException) {}
/*     */     
/* 264 */     return properties;
/*     */   }
/*     */   
/*     */   abstract UnixMountEntry findMountEntry() throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixFileStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */