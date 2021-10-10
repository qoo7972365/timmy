/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UntrustedCertificates
/*     */ {
/*  47 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */   
/*     */   private static final String ALGORITHM_KEY = "Algorithm";
/*  50 */   private static final Properties props = new Properties();
/*     */ 
/*     */   
/*     */   static {
/*  54 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  57 */             File file = new File(System.getProperty("java.home"), "lib/security/blacklisted.certs");
/*     */             
/*  59 */             try (FileInputStream null = new FileInputStream(file)) {
/*  60 */               UntrustedCertificates.props.load(fileInputStream);
/*     */               
/*  62 */               for (Map.Entry<Object, Object> entry : UntrustedCertificates.props.entrySet()) {
/*  63 */                 entry.setValue(UntrustedCertificates.stripColons(entry.getValue()));
/*     */               }
/*  65 */             } catch (IOException iOException) {
/*  66 */               if (UntrustedCertificates.debug != null) {
/*  67 */                 UntrustedCertificates.debug.println("Error parsing blacklisted.certs");
/*     */               }
/*     */             } 
/*  70 */             return null;
/*     */           }
/*     */         });
/*  73 */   } private static final String algorithm = props.getProperty("Algorithm");
/*     */ 
/*     */   
/*     */   private static String stripColons(Object paramObject) {
/*  77 */     String str = (String)paramObject;
/*  78 */     char[] arrayOfChar = str.toCharArray();
/*  79 */     byte b1 = 0;
/*  80 */     for (byte b2 = 0; b2 < arrayOfChar.length; b2++) {
/*  81 */       if (arrayOfChar[b2] != ':') {
/*  82 */         if (b2 != b1) {
/*  83 */           arrayOfChar[b1] = arrayOfChar[b2];
/*     */         }
/*  85 */         b1++;
/*     */       } 
/*     */     } 
/*  88 */     if (b1 == arrayOfChar.length) return str; 
/*  89 */     return new String(arrayOfChar, 0, b1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUntrusted(X509Certificate paramX509Certificate) {
/*     */     String str;
/*  98 */     if (algorithm == null) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     if (paramX509Certificate instanceof X509CertImpl) {
/* 103 */       str = ((X509CertImpl)paramX509Certificate).getFingerprint(algorithm);
/*     */     } else {
/*     */       try {
/* 106 */         str = (new X509CertImpl(paramX509Certificate.getEncoded())).getFingerprint(algorithm);
/* 107 */       } catch (CertificateException certificateException) {
/* 108 */         return false;
/*     */       } 
/*     */     } 
/* 111 */     return props.containsKey(str);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/UntrustedCertificates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */