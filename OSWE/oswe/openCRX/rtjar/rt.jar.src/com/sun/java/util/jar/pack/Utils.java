/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.JarInputStream;
/*     */ import java.util.jar.JarOutputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Utils
/*     */ {
/*     */   static final String COM_PREFIX = "com.sun.java.util.jar.pack.";
/*     */   static final String METAINF = "META-INF";
/*     */   static final String DEBUG_VERBOSE = "com.sun.java.util.jar.pack.verbose";
/*     */   static final String DEBUG_DISABLE_NATIVE = "com.sun.java.util.jar.pack.disable.native";
/*     */   static final String PACK_DEFAULT_TIMEZONE = "com.sun.java.util.jar.pack.default.timezone";
/*     */   static final String UNPACK_MODIFICATION_TIME = "com.sun.java.util.jar.pack.unpack.modification.time";
/*     */   static final String UNPACK_STRIP_DEBUG = "com.sun.java.util.jar.pack.unpack.strip.debug";
/*     */   static final String UNPACK_REMOVE_PACKFILE = "com.sun.java.util.jar.pack.unpack.remove.packfile";
/*     */   static final String NOW = "now";
/*     */   static final String PACK_KEEP_CLASS_ORDER = "com.sun.java.util.jar.pack.keep.class.order";
/*     */   static final String PACK_ZIP_ARCHIVE_MARKER_COMMENT = "PACK200";
/*     */   static final String CLASS_FORMAT_ERROR = "com.sun.java.util.jar.pack.class.format.error";
/* 135 */   static final ThreadLocal<TLGlobals> currentInstance = new ThreadLocal<>();
/*     */   
/*     */   private static TimeZone tz;
/* 138 */   private static int workingPackerCount = 0;
/*     */ 
/*     */   
/*     */   static TLGlobals getTLGlobals() {
/* 142 */     return currentInstance.get();
/*     */   }
/*     */   
/*     */   static PropMap currentPropMap() {
/* 146 */     Object object = currentInstance.get();
/* 147 */     if (object instanceof PackerImpl)
/* 148 */       return ((PackerImpl)object).props; 
/* 149 */     if (object instanceof UnpackerImpl)
/* 150 */       return ((UnpackerImpl)object).props; 
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 155 */   static final boolean nolog = Boolean.getBoolean("com.sun.java.util.jar.pack.nolog");
/*     */ 
/*     */   
/* 158 */   static final boolean SORT_MEMBERS_DESCR_MAJOR = Boolean.getBoolean("com.sun.java.util.jar.pack.sort.members.descr.major");
/*     */ 
/*     */   
/* 161 */   static final boolean SORT_HANDLES_KIND_MAJOR = Boolean.getBoolean("com.sun.java.util.jar.pack.sort.handles.kind.major");
/*     */ 
/*     */   
/* 164 */   static final boolean SORT_INDY_BSS_MAJOR = Boolean.getBoolean("com.sun.java.util.jar.pack.sort.indy.bss.major");
/*     */ 
/*     */   
/* 167 */   static final boolean SORT_BSS_BSM_MAJOR = Boolean.getBoolean("com.sun.java.util.jar.pack.sort.bss.bsm.major");
/*     */   
/*     */   static class Pack200Logger { private final String name;
/*     */     private PlatformLogger log;
/*     */     
/*     */     Pack200Logger(String param1String) {
/* 173 */       this.name = param1String;
/*     */     }
/*     */     
/*     */     private synchronized PlatformLogger getLogger() {
/* 177 */       if (this.log == null) {
/* 178 */         this.log = PlatformLogger.getLogger(this.name);
/*     */       }
/* 180 */       return this.log;
/*     */     }
/*     */     
/*     */     public void warning(String param1String, Object param1Object) {
/* 184 */       getLogger().warning(param1String, new Object[] { param1Object });
/*     */     }
/*     */     
/*     */     public void warning(String param1String) {
/* 188 */       warning(param1String, null);
/*     */     }
/*     */     
/*     */     public void info(String param1String) {
/* 192 */       int i = Utils.currentPropMap().getInteger("com.sun.java.util.jar.pack.verbose");
/* 193 */       if (i > 0) {
/* 194 */         if (Utils.nolog) {
/* 195 */           System.out.println(param1String);
/*     */         } else {
/* 197 */           getLogger().info(param1String);
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public void fine(String param1String) {
/* 203 */       int i = Utils.currentPropMap().getInteger("com.sun.java.util.jar.pack.verbose");
/* 204 */       if (i > 0) {
/* 205 */         System.out.println(param1String);
/*     */       }
/*     */     } }
/*     */ 
/*     */   
/*     */   static synchronized void changeDefaultTimeZoneToUtc() {
/* 211 */     if (workingPackerCount++ == 0) {
/*     */       
/* 213 */       tz = TimeZone.getDefault();
/* 214 */       TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
/*     */     } 
/*     */   }
/*     */   
/*     */   static synchronized void restoreDefaultTimeZone() {
/* 219 */     if (--workingPackerCount == 0) {
/*     */       
/* 221 */       if (tz != null) {
/* 222 */         TimeZone.setDefault(tz);
/*     */       }
/* 224 */       tz = null;
/*     */     } 
/*     */   }
/*     */   
/* 228 */   static final Pack200Logger log = new Pack200Logger("java.util.jar.Pack200");
/*     */ 
/*     */ 
/*     */   
/*     */   static String getVersionString() {
/* 233 */     return "Pack200, Vendor: " + 
/* 234 */       System.getProperty("java.vendor") + ", Version: " + Constants.MAX_PACKAGE_VERSION;
/*     */   }
/*     */ 
/*     */   
/*     */   static void markJarFile(JarOutputStream paramJarOutputStream) throws IOException {
/* 239 */     paramJarOutputStream.setComment("PACK200");
/*     */   }
/*     */ 
/*     */   
/*     */   static void copyJarFile(JarInputStream paramJarInputStream, JarOutputStream paramJarOutputStream) throws IOException {
/* 244 */     if (paramJarInputStream.getManifest() != null) {
/* 245 */       ZipEntry zipEntry = new ZipEntry("META-INF/MANIFEST.MF");
/* 246 */       paramJarOutputStream.putNextEntry(zipEntry);
/* 247 */       paramJarInputStream.getManifest().write(paramJarOutputStream);
/* 248 */       paramJarOutputStream.closeEntry();
/*     */     } 
/* 250 */     byte[] arrayOfByte = new byte[16384]; JarEntry jarEntry;
/* 251 */     while ((jarEntry = paramJarInputStream.getNextJarEntry()) != null) {
/* 252 */       paramJarOutputStream.putNextEntry(jarEntry); int i;
/* 253 */       while (0 < (i = paramJarInputStream.read(arrayOfByte))) {
/* 254 */         paramJarOutputStream.write(arrayOfByte, 0, i);
/*     */       }
/*     */     } 
/* 257 */     paramJarInputStream.close();
/* 258 */     markJarFile(paramJarOutputStream);
/*     */   }
/*     */   static void copyJarFile(JarFile paramJarFile, JarOutputStream paramJarOutputStream) throws IOException {
/* 261 */     byte[] arrayOfByte = new byte[16384];
/* 262 */     for (JarEntry jarEntry : Collections.<JarEntry>list(paramJarFile.entries())) {
/* 263 */       paramJarOutputStream.putNextEntry(jarEntry);
/* 264 */       InputStream inputStream = paramJarFile.getInputStream(jarEntry); int i;
/* 265 */       while (0 < (i = inputStream.read(arrayOfByte))) {
/* 266 */         paramJarOutputStream.write(arrayOfByte, 0, i);
/*     */       }
/*     */     } 
/* 269 */     paramJarFile.close();
/* 270 */     markJarFile(paramJarOutputStream);
/*     */   }
/*     */   
/*     */   static void copyJarFile(JarInputStream paramJarInputStream, OutputStream paramOutputStream) throws IOException {
/* 274 */     paramOutputStream = new BufferedOutputStream(paramOutputStream);
/* 275 */     paramOutputStream = new NonCloser(paramOutputStream);
/* 276 */     try (JarOutputStream null = new JarOutputStream(paramOutputStream)) {
/* 277 */       copyJarFile(paramJarInputStream, jarOutputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void copyJarFile(JarFile paramJarFile, OutputStream paramOutputStream) throws IOException {
/* 283 */     paramOutputStream = new BufferedOutputStream(paramOutputStream);
/* 284 */     paramOutputStream = new NonCloser(paramOutputStream);
/* 285 */     try (JarOutputStream null = new JarOutputStream(paramOutputStream)) {
/* 286 */       copyJarFile(paramJarFile, jarOutputStream);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class NonCloser extends FilterOutputStream
/*     */   {
/* 292 */     NonCloser(OutputStream param1OutputStream) { super(param1OutputStream); } public void close() throws IOException {
/* 293 */       flush();
/*     */     } }
/*     */   static String getJarEntryName(String paramString) {
/* 296 */     if (paramString == null) return null; 
/* 297 */     return paramString.replace(File.separatorChar, '/');
/*     */   }
/*     */ 
/*     */   
/*     */   static String zeString(ZipEntry paramZipEntry) {
/* 302 */     boolean bool = (paramZipEntry.getCompressedSize() > 0L) ? (int)((1.0D - paramZipEntry.getCompressedSize() / paramZipEntry.getSize()) * 100.0D) : false;
/*     */ 
/*     */     
/* 305 */     return paramZipEntry.getSize() + "\t" + paramZipEntry.getMethod() + "\t" + paramZipEntry
/* 306 */       .getCompressedSize() + "\t" + bool + "%\t" + new Date(paramZipEntry
/*     */         
/* 308 */         .getTime()) + "\t" + 
/* 309 */       Long.toHexString(paramZipEntry.getCrc()) + "\t" + paramZipEntry
/* 310 */       .getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] readMagic(BufferedInputStream paramBufferedInputStream) throws IOException {
/* 316 */     paramBufferedInputStream.mark(4);
/* 317 */     byte[] arrayOfByte = new byte[4];
/* 318 */     for (byte b = 0; b < arrayOfByte.length; b++) {
/*     */       
/* 320 */       if (1 != paramBufferedInputStream.read(arrayOfByte, b, 1))
/*     */         break; 
/*     */     } 
/* 323 */     paramBufferedInputStream.reset();
/* 324 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isJarMagic(byte[] paramArrayOfbyte) {
/* 329 */     return (paramArrayOfbyte[0] == 80 && paramArrayOfbyte[1] == 75 && paramArrayOfbyte[2] >= 1 && paramArrayOfbyte[2] < 8 && paramArrayOfbyte[3] == paramArrayOfbyte[2] + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isPackMagic(byte[] paramArrayOfbyte) {
/* 336 */     return (paramArrayOfbyte[0] == -54 && paramArrayOfbyte[1] == -2 && paramArrayOfbyte[2] == -48 && paramArrayOfbyte[3] == 13);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isGZIPMagic(byte[] paramArrayOfbyte) {
/* 342 */     return (paramArrayOfbyte[0] == 31 && paramArrayOfbyte[1] == -117 && paramArrayOfbyte[2] == 8);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */