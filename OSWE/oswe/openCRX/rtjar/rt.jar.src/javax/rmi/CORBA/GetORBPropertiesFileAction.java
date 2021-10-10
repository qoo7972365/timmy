/*     */ package javax.rmi.CORBA;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GetORBPropertiesFileAction
/*     */   implements PrivilegedAction
/*     */ {
/*     */   private boolean debug = false;
/*     */   
/*     */   private String getSystemProperty(final String name) {
/*  58 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public Object run() {
/*  61 */             return System.getProperty(name);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getPropertiesFromFile(Properties paramProperties, String paramString) {
/*     */     try {
/*  72 */       File file = new File(paramString);
/*  73 */       if (!file.exists()) {
/*     */         return;
/*     */       }
/*  76 */       FileInputStream fileInputStream = new FileInputStream(file);
/*     */       
/*     */       try {
/*  79 */         paramProperties.load(fileInputStream);
/*     */       } finally {
/*  81 */         fileInputStream.close();
/*     */       } 
/*  83 */     } catch (Exception exception) {
/*  84 */       if (this.debug) {
/*  85 */         System.out.println("ORB properties file " + paramString + " not found: " + exception);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object run() {
/*  92 */     Properties properties1 = new Properties();
/*     */     
/*  94 */     String str1 = getSystemProperty("java.home");
/*  95 */     String str2 = str1 + File.separator + "lib" + File.separator + "orb.properties";
/*     */ 
/*     */     
/*  98 */     getPropertiesFromFile(properties1, str2);
/*     */     
/* 100 */     Properties properties2 = new Properties(properties1);
/*     */     
/* 102 */     String str3 = getSystemProperty("user.home");
/* 103 */     str2 = str3 + File.separator + "orb.properties";
/*     */     
/* 105 */     getPropertiesFromFile(properties2, str2);
/* 106 */     return properties2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/rmi/CORBA/GetORBPropertiesFileAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */