/*     */ package sun.security.smartcardio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PlatformPCSC
/*     */ {
/*  45 */   static final Debug debug = Debug.getInstance("pcsc");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   static final Throwable initException = AccessController.<Throwable>doPrivileged(new PrivilegedAction<Throwable>() {
/*     */         public Throwable run() {
/*     */           try {
/*  64 */             System.loadLibrary("j2pcsc");
/*  65 */             String str = PlatformPCSC.getLibraryName();
/*  66 */             if (PlatformPCSC.debug != null) {
/*  67 */               PlatformPCSC.debug.println("Using PC/SC library: " + str);
/*     */             }
/*  69 */             PlatformPCSC.initialize(str);
/*  70 */             return null;
/*  71 */           } catch (Throwable throwable) {
/*  72 */             return throwable;
/*     */           } 
/*     */         }
/*     */       });
/*     */   private static final String PROP_NAME = "sun.security.smartcardio.library"; private static final String LIB0 = "libpcsclite.so.1"; private static final String LIB1 = "/usr/$LIBISA/libpcsclite.so";
/*     */   
/*     */   private static String expand(String paramString) {
/*     */     String str3;
/*  80 */     int i = paramString.indexOf("$LIBISA");
/*  81 */     if (i == -1) {
/*  82 */       return paramString;
/*     */     }
/*  84 */     String str1 = paramString.substring(0, i);
/*  85 */     String str2 = paramString.substring(i + 7);
/*     */     
/*  87 */     if ("64".equals(System.getProperty("sun.arch.data.model"))) {
/*  88 */       if ("SunOS".equals(System.getProperty("os.name"))) {
/*  89 */         str3 = "lib/64";
/*     */       } else {
/*     */         
/*  92 */         str3 = "lib64";
/*     */       } 
/*     */     } else {
/*     */       
/*  96 */       str3 = "lib";
/*     */     } 
/*  98 */     return str1 + str3 + str2;
/*     */   }
/*     */   private static final String LIB2 = "/usr/local/$LIBISA/libpcsclite.so"; private static final String PCSC_FRAMEWORK = "/System/Library/Frameworks/PCSC.framework/Versions/Current/PCSC"; static final int SCARD_PROTOCOL_T0 = 1; static final int SCARD_PROTOCOL_T1 = 2; static final int SCARD_PROTOCOL_RAW = 4; static final int SCARD_UNKNOWN = 1; static final int SCARD_ABSENT = 2; static final int SCARD_PRESENT = 4; static final int SCARD_SWALLOWED = 8; static final int SCARD_POWERED = 16; static final int SCARD_NEGOTIABLE = 32;
/*     */   static final int SCARD_SPECIFIC = 64;
/*     */   
/*     */   private static String getLibraryName() throws IOException {
/* 104 */     String str = expand(System.getProperty("sun.security.smartcardio.library", "").trim());
/* 105 */     if (str.length() != 0) {
/* 106 */       return str;
/*     */     }
/*     */     
/* 109 */     str = "libpcsclite.so.1";
/* 110 */     return str;
/*     */   }
/*     */   
/*     */   private static native void initialize(String paramString);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/smartcardio/PlatformPCSC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */