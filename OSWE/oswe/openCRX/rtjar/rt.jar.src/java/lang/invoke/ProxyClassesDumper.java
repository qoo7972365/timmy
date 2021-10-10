/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.FilePermission;
/*     */ import java.lang.invoke.ProxyClassesDumper;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.InvalidPathException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Objects;
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
/*     */ final class ProxyClassesDumper
/*     */ {
/*  47 */   private static final char[] HEX = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/*     */ 
/*     */   
/*  51 */   private static final char[] BAD_CHARS = new char[] { '\\', ':', '*', '?', '"', '<', '>', '|' };
/*     */ 
/*     */   
/*  54 */   private static final String[] REPLACEMENT = new String[] { "%5C", "%3A", "%2A", "%3F", "%22", "%3C", "%3E", "%7C" };
/*     */ 
/*     */   
/*     */   private final Path dumpDir;
/*     */ 
/*     */   
/*     */   public static ProxyClassesDumper getInstance(String paramString) {
/*  61 */     if (null == paramString) {
/*  62 */       return null;
/*     */     }
/*     */     try {
/*  65 */       paramString = paramString.trim();
/*  66 */       final Path dir = Paths.get((paramString.length() == 0) ? "." : paramString, new String[0]);
/*  67 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             public Void run() {
/*  70 */               ProxyClassesDumper.validateDumpDir(dir);
/*  71 */               return null;
/*     */             }
/*     */           },  (AccessControlContext)null, new Permission[] { new FilePermission("<<ALL FILES>>", "read, write") });
/*  74 */       return new ProxyClassesDumper(path);
/*  75 */     } catch (InvalidPathException invalidPathException) {
/*  76 */       PlatformLogger.getLogger(ProxyClassesDumper.class.getName())
/*  77 */         .warning("Path " + paramString + " is not valid - dumping disabled", invalidPathException);
/*  78 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  79 */       PlatformLogger.getLogger(ProxyClassesDumper.class.getName())
/*  80 */         .warning(illegalArgumentException.getMessage() + " - dumping disabled");
/*     */     } 
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   private ProxyClassesDumper(Path paramPath) {
/*  86 */     this.dumpDir = Objects.<Path>requireNonNull(paramPath);
/*     */   }
/*     */   
/*     */   private static void validateDumpDir(Path paramPath) {
/*  90 */     if (!Files.exists(paramPath, new java.nio.file.LinkOption[0]))
/*  91 */       throw new IllegalArgumentException("Directory " + paramPath + " does not exist"); 
/*  92 */     if (!Files.isDirectory(paramPath, new java.nio.file.LinkOption[0]))
/*  93 */       throw new IllegalArgumentException("Path " + paramPath + " is not a directory"); 
/*  94 */     if (!Files.isWritable(paramPath)) {
/*  95 */       throw new IllegalArgumentException("Directory " + paramPath + " is not writable");
/*     */     }
/*     */   }
/*     */   
/*     */   public static String encodeForFilename(String paramString) {
/* 100 */     int i = paramString.length();
/* 101 */     StringBuilder stringBuilder = new StringBuilder(i);
/*     */     
/* 103 */     for (byte b = 0; b < i; b++) {
/* 104 */       char c = paramString.charAt(b);
/*     */       
/* 106 */       if (c <= '\037') {
/* 107 */         stringBuilder.append('%');
/* 108 */         stringBuilder.append(HEX[c >> 4 & 0xF]);
/* 109 */         stringBuilder.append(HEX[c & 0xF]);
/*     */       } else {
/* 111 */         byte b1 = 0;
/* 112 */         for (; b1 < BAD_CHARS.length; b1++) {
/* 113 */           if (c == BAD_CHARS[b1]) {
/* 114 */             stringBuilder.append(REPLACEMENT[b1]);
/*     */             break;
/*     */           } 
/*     */         } 
/* 118 */         if (b1 >= BAD_CHARS.length) {
/* 119 */           stringBuilder.append(c);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void dumpClass(String paramString, byte[] paramArrayOfbyte) {
/*     */     Path path;
/*     */     try {
/* 130 */       path = this.dumpDir.resolve(encodeForFilename(paramString) + ".class");
/* 131 */     } catch (InvalidPathException invalidPathException) {
/* 132 */       PlatformLogger.getLogger(ProxyClassesDumper.class.getName())
/* 133 */         .warning("Invalid path for class " + paramString);
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 138 */       Path path1 = path.getParent();
/* 139 */       Files.createDirectories(path1, (FileAttribute<?>[])new FileAttribute[0]);
/* 140 */       Files.write(path, paramArrayOfbyte, new java.nio.file.OpenOption[0]);
/* 141 */     } catch (Exception exception) {
/* 142 */       PlatformLogger.getLogger(ProxyClassesDumper.class.getName())
/* 143 */         .warning("Exception writing to path at " + path.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/ProxyClassesDumper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */