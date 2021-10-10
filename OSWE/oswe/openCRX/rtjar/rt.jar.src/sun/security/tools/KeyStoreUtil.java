/*     */ package sun.security.tools;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.text.Collator;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyStoreUtil
/*     */ {
/*     */   private static final String JKS = "jks";
/*  56 */   private static final Collator collator = Collator.getInstance();
/*     */   
/*     */   static {
/*  59 */     collator.setStrength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSelfSigned(X509Certificate paramX509Certificate) {
/*  66 */     return signedBy(paramX509Certificate, paramX509Certificate);
/*     */   }
/*     */   
/*     */   public static boolean signedBy(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2) {
/*  70 */     if (!paramX509Certificate2.getSubjectX500Principal().equals(paramX509Certificate1.getIssuerX500Principal())) {
/*  71 */       return false;
/*     */     }
/*     */     try {
/*  74 */       paramX509Certificate1.verify(paramX509Certificate2.getPublicKey());
/*  75 */       return true;
/*  76 */     } catch (Exception exception) {
/*  77 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWindowsKeyStore(String paramString) {
/*  86 */     return (paramString != null && (paramString
/*  87 */       .equalsIgnoreCase("Windows-MY") || paramString
/*  88 */       .equalsIgnoreCase("Windows-ROOT")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String niceStoreTypeName(String paramString) {
/*  95 */     if (paramString.equalsIgnoreCase("Windows-MY"))
/*  96 */       return "Windows-MY"; 
/*  97 */     if (paramString.equalsIgnoreCase("Windows-ROOT")) {
/*  98 */       return "Windows-ROOT";
/*     */     }
/* 100 */     return paramString.toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyStore getCacertsKeyStore() throws Exception {
/* 110 */     String str = File.separator;
/* 111 */     File file = new File(System.getProperty("java.home") + str + "lib" + str + "security" + str + "cacerts");
/*     */ 
/*     */     
/* 114 */     if (!file.exists()) {
/* 115 */       return null;
/*     */     }
/* 117 */     KeyStore keyStore = null;
/* 118 */     try (FileInputStream null = new FileInputStream(file)) {
/* 119 */       keyStore = KeyStore.getInstance("jks");
/* 120 */       keyStore.load(fileInputStream, null);
/*     */     } 
/* 122 */     return keyStore;
/*     */   }
/*     */ 
/*     */   
/*     */   public static char[] getPassWithModifier(String paramString1, String paramString2, ResourceBundle paramResourceBundle) {
/* 127 */     if (paramString1 == null)
/* 128 */       return paramString2.toCharArray(); 
/* 129 */     if (collator.compare(paramString1, "env") == 0) {
/* 130 */       String str = System.getenv(paramString2);
/* 131 */       if (str == null) {
/* 132 */         System.err.println(paramResourceBundle.getString("Cannot.find.environment.variable.") + paramString2);
/*     */         
/* 134 */         return null;
/*     */       } 
/* 136 */       return str.toCharArray();
/*     */     } 
/* 138 */     if (collator.compare(paramString1, "file") == 0) {
/*     */       try {
/* 140 */         URL uRL = null;
/*     */         try {
/* 142 */           uRL = new URL(paramString2);
/* 143 */         } catch (MalformedURLException malformedURLException) {
/* 144 */           File file = new File(paramString2);
/* 145 */           if (file.exists()) {
/* 146 */             uRL = file.toURI().toURL();
/*     */           } else {
/* 148 */             System.err.println(paramResourceBundle.getString("Cannot.find.file.") + paramString2);
/*     */             
/* 150 */             return null;
/*     */           } 
/*     */         } 
/*     */         
/* 154 */         try (BufferedReader null = new BufferedReader(new InputStreamReader(uRL
/*     */                 
/* 156 */                 .openStream()))) {
/* 157 */           String str = bufferedReader.readLine();
/*     */           
/* 159 */           if (str == null) {
/* 160 */             return new char[0];
/*     */           }
/*     */           
/* 163 */           return str.toCharArray();
/*     */         } 
/* 165 */       } catch (IOException iOException) {
/* 166 */         System.err.println(iOException);
/* 167 */         return null;
/*     */       } 
/*     */     }
/* 170 */     System.err.println(paramResourceBundle.getString("Unknown.password.type.") + paramString1);
/*     */     
/* 172 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/tools/KeyStoreUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */