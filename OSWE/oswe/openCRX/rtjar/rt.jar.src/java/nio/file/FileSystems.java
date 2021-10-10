/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.URI;
/*     */ import java.nio.file.spi.FileSystemProvider;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.ServiceLoader;
/*     */ import sun.nio.fs.DefaultFileSystemProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FileSystems
/*     */ {
/*     */   private static class DefaultFileSystemHolder
/*     */   {
/*  90 */     static final FileSystem defaultFileSystem = defaultFileSystem();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static FileSystem defaultFileSystem() {
/*  96 */       FileSystemProvider fileSystemProvider = AccessController.<FileSystemProvider>doPrivileged(new PrivilegedAction<FileSystemProvider>() {
/*     */             public FileSystemProvider run() {
/*  98 */               return FileSystems.DefaultFileSystemHolder.getDefaultProvider();
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 103 */       return fileSystemProvider.getFileSystem(URI.create("file:///"));
/*     */     }
/*     */ 
/*     */     
/*     */     private static FileSystemProvider getDefaultProvider() {
/* 108 */       FileSystemProvider fileSystemProvider = DefaultFileSystemProvider.create();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       String str = System.getProperty("java.nio.file.spi.DefaultFileSystemProvider");
/* 114 */       if (str != null) {
/* 115 */         for (String str1 : str.split(",")) {
/*     */           
/*     */           try {
/* 118 */             Class<?> clazz = Class.forName(str1, true, ClassLoader.getSystemClassLoader());
/*     */             
/* 120 */             Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[] { FileSystemProvider.class });
/* 121 */             fileSystemProvider = (FileSystemProvider)constructor.newInstance(new Object[] { fileSystemProvider });
/*     */ 
/*     */             
/* 124 */             if (!fileSystemProvider.getScheme().equals("file")) {
/* 125 */               throw new Error("Default provider must use scheme 'file'");
/*     */             }
/* 127 */           } catch (Exception exception) {
/* 128 */             throw new Error(exception);
/*     */           } 
/*     */         } 
/*     */       }
/* 132 */       return fileSystemProvider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileSystem getDefault() {
/* 176 */     return DefaultFileSystemHolder.defaultFileSystem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileSystem getFileSystem(URI paramURI) {
/* 218 */     String str = paramURI.getScheme();
/* 219 */     for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
/* 220 */       if (str.equalsIgnoreCase(fileSystemProvider.getScheme())) {
/* 221 */         return fileSystemProvider.getFileSystem(paramURI);
/*     */       }
/*     */     } 
/* 224 */     throw new ProviderNotFoundException("Provider \"" + str + "\" not found");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileSystem newFileSystem(URI paramURI, Map<String, ?> paramMap) throws IOException {
/* 276 */     return newFileSystem(paramURI, paramMap, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileSystem newFileSystem(URI paramURI, Map<String, ?> paramMap, ClassLoader paramClassLoader) throws IOException {
/* 321 */     String str = paramURI.getScheme();
/*     */ 
/*     */     
/* 324 */     for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
/* 325 */       if (str.equalsIgnoreCase(fileSystemProvider.getScheme())) {
/* 326 */         return fileSystemProvider.newFileSystem(paramURI, paramMap);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 331 */     if (paramClassLoader != null) {
/*     */       
/* 333 */       ServiceLoader<FileSystemProvider> serviceLoader = ServiceLoader.load(FileSystemProvider.class, paramClassLoader);
/* 334 */       for (FileSystemProvider fileSystemProvider : serviceLoader) {
/* 335 */         if (str.equalsIgnoreCase(fileSystemProvider.getScheme())) {
/* 336 */           return fileSystemProvider.newFileSystem(paramURI, paramMap);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 341 */     throw new ProviderNotFoundException("Provider \"" + str + "\" not found");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileSystem newFileSystem(Path paramPath, ClassLoader paramClassLoader) throws IOException {
/* 383 */     if (paramPath == null)
/* 384 */       throw new NullPointerException(); 
/* 385 */     Map<?, ?> map = Collections.emptyMap();
/*     */ 
/*     */     
/* 388 */     for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
/*     */       try {
/* 390 */         return fileSystemProvider.newFileSystem(paramPath, (Map)map);
/* 391 */       } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 396 */     if (paramClassLoader != null) {
/*     */       
/* 398 */       ServiceLoader<FileSystemProvider> serviceLoader = ServiceLoader.load(FileSystemProvider.class, paramClassLoader);
/* 399 */       for (FileSystemProvider fileSystemProvider : serviceLoader) {
/*     */         try {
/* 401 */           return fileSystemProvider.newFileSystem(paramPath, (Map)map);
/* 402 */         } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 407 */     throw new ProviderNotFoundException("Provider not found");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/FileSystems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */