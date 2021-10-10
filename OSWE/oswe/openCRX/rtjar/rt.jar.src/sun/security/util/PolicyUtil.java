/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Arrays;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyUtil
/*     */ {
/*     */   private static final String P11KEYSTORE = "PKCS11";
/*     */   private static final String NONE = "NONE";
/*     */   
/*     */   public static InputStream getInputStream(URL paramURL) throws IOException {
/*  57 */     if ("file".equals(paramURL.getProtocol())) {
/*  58 */       String str = paramURL.getFile().replace('/', File.separatorChar);
/*  59 */       str = ParseUtil.decode(str);
/*  60 */       return new FileInputStream(str);
/*     */     } 
/*  62 */     return paramURL.openStream();
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
/*     */   public static KeyStore getKeyStore(URL paramURL, String paramString1, String paramString2, String paramString3, String paramString4, Debug paramDebug) throws KeyStoreException, MalformedURLException, IOException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException {
/*  81 */     if (paramString1 == null) {
/*  82 */       throw new IllegalArgumentException("null KeyStore name");
/*     */     }
/*     */     
/*  85 */     char[] arrayOfChar = null;
/*     */     try {
/*     */       KeyStore keyStore;
/*  88 */       if (paramString2 == null) {
/*  89 */         paramString2 = KeyStore.getDefaultType();
/*     */       }
/*     */       
/*  92 */       if ("PKCS11".equalsIgnoreCase(paramString2) && 
/*  93 */         !"NONE".equals(paramString1)) {
/*  94 */         throw new IllegalArgumentException("Invalid value (" + paramString1 + ") for keystore URL.  If the keystore type is \"" + "PKCS11" + "\", the keystore url must be \"" + "NONE" + "\"");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       if (paramString3 != null) {
/* 105 */         keyStore = KeyStore.getInstance(paramString2, paramString3);
/*     */       } else {
/* 107 */         keyStore = KeyStore.getInstance(paramString2);
/*     */       } 
/*     */       
/* 110 */       if (paramString4 != null) {
/*     */         URL uRL1;
/*     */         try {
/* 113 */           uRL1 = new URL(paramString4);
/*     */         }
/* 115 */         catch (MalformedURLException malformedURLException) {
/*     */           
/* 117 */           if (paramURL == null) {
/* 118 */             throw malformedURLException;
/*     */           }
/* 120 */           uRL1 = new URL(paramURL, paramString4);
/*     */         } 
/*     */         
/* 123 */         if (paramDebug != null) {
/* 124 */           paramDebug.println("reading password" + uRL1);
/*     */         }
/*     */         
/* 127 */         InputStream inputStream = null;
/*     */         try {
/* 129 */           inputStream = uRL1.openStream();
/* 130 */           arrayOfChar = Password.readPassword(inputStream);
/*     */         } finally {
/* 132 */           if (inputStream != null) {
/* 133 */             inputStream.close();
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 138 */       if ("NONE".equals(paramString1)) {
/* 139 */         keyStore.load(null, arrayOfChar);
/* 140 */         return keyStore;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       URL uRL = null;
/*     */       try {
/* 148 */         uRL = new URL(paramString1);
/*     */       }
/* 150 */       catch (MalformedURLException malformedURLException) {
/*     */         
/* 152 */         if (paramURL == null) {
/* 153 */           throw malformedURLException;
/*     */         }
/* 155 */         uRL = new URL(paramURL, paramString1);
/*     */       } 
/*     */       
/* 158 */       if (paramDebug != null) {
/* 159 */         paramDebug.println("reading keystore" + uRL);
/*     */       }
/*     */       
/* 162 */       BufferedInputStream bufferedInputStream = null;
/*     */       
/*     */       try {
/* 165 */         bufferedInputStream = new BufferedInputStream(getInputStream(uRL));
/* 166 */         keyStore.load(bufferedInputStream, arrayOfChar);
/*     */       } finally {
/* 168 */         bufferedInputStream.close();
/*     */       } 
/* 170 */       return keyStore;
/*     */     } finally {
/*     */       
/* 173 */       if (arrayOfChar != null)
/* 174 */         Arrays.fill(arrayOfChar, ' '); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/PolicyUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */