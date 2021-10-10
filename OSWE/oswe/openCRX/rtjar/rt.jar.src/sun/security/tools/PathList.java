/*     */ package sun.security.tools;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathList
/*     */ {
/*     */   public static String appendPath(String paramString1, String paramString2) {
/*  49 */     if (paramString1 == null || paramString1.length() == 0)
/*  50 */       return paramString2; 
/*  51 */     if (paramString2 == null || paramString2.length() == 0) {
/*  52 */       return paramString1;
/*     */     }
/*  54 */     return paramString1 + File.pathSeparator + paramString2;
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
/*     */   public static URL[] pathToURLs(String paramString) {
/*  66 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, File.pathSeparator);
/*  67 */     URL[] arrayOfURL = new URL[stringTokenizer.countTokens()];
/*  68 */     byte b = 0;
/*  69 */     while (stringTokenizer.hasMoreTokens()) {
/*  70 */       URL uRL = fileToURL(new File(stringTokenizer.nextToken()));
/*  71 */       if (uRL != null) {
/*  72 */         arrayOfURL[b++] = uRL;
/*     */       }
/*     */     } 
/*  75 */     if (arrayOfURL.length != b) {
/*  76 */       URL[] arrayOfURL1 = new URL[b];
/*  77 */       System.arraycopy(arrayOfURL, 0, arrayOfURL1, 0, b);
/*  78 */       arrayOfURL = arrayOfURL1;
/*     */     } 
/*  80 */     return arrayOfURL;
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
/*     */   private static URL fileToURL(File paramFile) {
/*     */     try {
/*  93 */       str = paramFile.getCanonicalPath();
/*  94 */     } catch (IOException iOException) {
/*  95 */       str = paramFile.getAbsolutePath();
/*     */     } 
/*  97 */     String str = str.replace(File.separatorChar, '/');
/*  98 */     if (!str.startsWith("/")) {
/*  99 */       str = "/" + str;
/*     */     }
/*     */     
/* 102 */     if (!paramFile.isFile()) {
/* 103 */       str = str + "/";
/*     */     }
/*     */     try {
/* 106 */       return new URL("file", "", str);
/* 107 */     } catch (MalformedURLException malformedURLException) {
/* 108 */       throw new IllegalArgumentException("file");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/tools/PathList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */