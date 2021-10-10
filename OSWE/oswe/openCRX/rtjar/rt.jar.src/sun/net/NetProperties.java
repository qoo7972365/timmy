/*     */ package sun.net;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetProperties
/*     */ {
/*  42 */   private static Properties props = new Properties();
/*     */   static {
/*  44 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  47 */             NetProperties.loadDefaultProperties();
/*  48 */             return null;
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
/*     */   private static void loadDefaultProperties() {
/*  60 */     String str = System.getProperty("java.home");
/*  61 */     if (str == null) {
/*  62 */       throw new Error("Can't find java.home ??");
/*     */     }
/*     */     try {
/*  65 */       File file = new File(str, "lib");
/*  66 */       file = new File(file, "net.properties");
/*  67 */       str = file.getCanonicalPath();
/*  68 */       FileInputStream fileInputStream = new FileInputStream(str);
/*  69 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
/*  70 */       props.load(bufferedInputStream);
/*  71 */       bufferedInputStream.close();
/*  72 */     } catch (Exception exception) {}
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
/*     */   public static String get(String paramString) {
/*  90 */     String str = props.getProperty(paramString);
/*     */     
/*  92 */     try { return System.getProperty(paramString, str); }
/*  93 */     catch (IllegalArgumentException illegalArgumentException) {  }
/*  94 */     catch (NullPointerException nullPointerException) {}
/*     */     
/*  96 */     return null;
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
/*     */   public static Integer getInteger(String paramString, int paramInt) {
/* 112 */     String str = null;
/*     */ 
/*     */     
/* 115 */     try { str = System.getProperty(paramString, props.getProperty(paramString)); }
/* 116 */     catch (IllegalArgumentException illegalArgumentException) {  }
/* 117 */     catch (NullPointerException nullPointerException) {}
/*     */ 
/*     */     
/* 120 */     if (str != null) {
/*     */       try {
/* 122 */         return Integer.decode(str);
/* 123 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */     
/* 126 */     return new Integer(paramInt);
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
/*     */   public static Boolean getBoolean(String paramString) {
/* 141 */     String str = null;
/*     */ 
/*     */     
/* 144 */     try { str = System.getProperty(paramString, props.getProperty(paramString)); }
/* 145 */     catch (IllegalArgumentException illegalArgumentException) {  }
/* 146 */     catch (NullPointerException nullPointerException) {}
/*     */ 
/*     */     
/* 149 */     if (str != null) {
/*     */       try {
/* 151 */         return Boolean.valueOf(str);
/* 152 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */     
/* 155 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/NetProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */