/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.SecurityPermission;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaUtils
/*     */ {
/*  41 */   private static Logger log = Logger.getLogger(JavaUtils.class.getName());
/*     */   
/*  43 */   private static final SecurityPermission REGISTER_PERMISSION = new SecurityPermission("com.sun.org.apache.xml.internal.security.register");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBytesFromFile(String paramString) throws FileNotFoundException, IOException {
/*  63 */     byte[] arrayOfByte = null;
/*     */     
/*  65 */     FileInputStream fileInputStream = null;
/*  66 */     UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = null;
/*     */     try {
/*  68 */       fileInputStream = new FileInputStream(paramString);
/*  69 */       unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
/*  70 */       byte[] arrayOfByte1 = new byte[1024];
/*     */       
/*     */       int i;
/*  73 */       while ((i = fileInputStream.read(arrayOfByte1)) > 0) {
/*  74 */         unsyncByteArrayOutputStream.write(arrayOfByte1, 0, i);
/*     */       }
/*     */       
/*  77 */       arrayOfByte = unsyncByteArrayOutputStream.toByteArray();
/*     */     } finally {
/*  79 */       if (unsyncByteArrayOutputStream != null) {
/*  80 */         unsyncByteArrayOutputStream.close();
/*     */       }
/*  82 */       if (fileInputStream != null) {
/*  83 */         fileInputStream.close();
/*     */       }
/*     */     } 
/*     */     
/*  87 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeBytesToFilename(String paramString, byte[] paramArrayOfbyte) {
/*  97 */     FileOutputStream fileOutputStream = null;
/*     */     try {
/*  99 */       if (paramString != null && paramArrayOfbyte != null) {
/* 100 */         File file = new File(paramString);
/*     */         
/* 102 */         fileOutputStream = new FileOutputStream(file);
/*     */         
/* 104 */         fileOutputStream.write(paramArrayOfbyte);
/* 105 */         fileOutputStream.close();
/*     */       }
/* 107 */       else if (log.isLoggable(Level.FINE)) {
/* 108 */         log.log(Level.FINE, "writeBytesToFilename got null byte[] pointed");
/*     */       }
/*     */     
/* 111 */     } catch (IOException iOException) {
/* 112 */       if (fileOutputStream != null) {
/*     */         try {
/* 114 */           fileOutputStream.close();
/* 115 */         } catch (IOException iOException1) {
/* 116 */           if (log.isLoggable(Level.FINE)) {
/* 117 */             log.log(Level.FINE, iOException1.getMessage(), iOException1);
/*     */           }
/*     */         } 
/*     */       }
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
/*     */   public static byte[] getBytesFromStream(InputStream paramInputStream) throws IOException {
/* 135 */     UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = null;
/*     */     
/* 137 */     byte[] arrayOfByte = null;
/*     */     try {
/* 139 */       unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
/* 140 */       byte[] arrayOfByte1 = new byte[4096];
/*     */       
/*     */       int i;
/* 143 */       while ((i = paramInputStream.read(arrayOfByte1)) > 0) {
/* 144 */         unsyncByteArrayOutputStream.write(arrayOfByte1, 0, i);
/*     */       }
/* 146 */       arrayOfByte = unsyncByteArrayOutputStream.toByteArray();
/*     */     } finally {
/* 148 */       unsyncByteArrayOutputStream.close();
/*     */     } 
/*     */     
/* 151 */     return arrayOfByte;
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
/*     */   public static byte[] convertDsaASN1toXMLDSIG(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 170 */     if (paramArrayOfbyte[0] != 48 || paramArrayOfbyte[1] != paramArrayOfbyte.length - 2 || paramArrayOfbyte[2] != 2)
/*     */     {
/* 172 */       throw new IOException("Invalid ASN.1 format of DSA signature");
/*     */     }
/*     */     
/* 175 */     byte b1 = paramArrayOfbyte[3];
/*     */     byte b2;
/* 177 */     for (b2 = b1; b2 > 0 && paramArrayOfbyte[4 + b1 - b2] == 0; b2--);
/*     */     
/* 179 */     byte b3 = paramArrayOfbyte[5 + b1];
/*     */     
/* 181 */     byte b4 = b3;
/* 182 */     for (; b4 > 0 && paramArrayOfbyte[6 + b1 + b3 - b4] == 0; b4--);
/*     */     
/* 184 */     if (b2 > paramInt || paramArrayOfbyte[4 + b1] != 2 || b4 > paramInt) {
/* 185 */       throw new IOException("Invalid ASN.1 format of DSA signature");
/*     */     }
/* 187 */     byte[] arrayOfByte = new byte[paramInt * 2];
/* 188 */     System.arraycopy(paramArrayOfbyte, 4 + b1 - b2, arrayOfByte, paramInt - b2, b2);
/*     */     
/* 190 */     System.arraycopy(paramArrayOfbyte, 6 + b1 + b3 - b4, arrayOfByte, paramInt * 2 - b4, b4);
/*     */     
/* 192 */     return arrayOfByte;
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
/*     */   public static byte[] convertDsaXMLDSIGtoASN1(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 212 */     int i = paramInt * 2;
/* 213 */     if (paramArrayOfbyte.length != i) {
/* 214 */       throw new IOException("Invalid XMLDSIG format of DSA signature");
/*     */     }
/*     */     
/*     */     int j;
/* 218 */     for (j = paramInt; j > 0 && paramArrayOfbyte[paramInt - j] == 0; j--);
/*     */     
/* 220 */     int k = j;
/* 221 */     if (paramArrayOfbyte[paramInt - j] < 0) {
/* 222 */       k++;
/*     */     }
/*     */     
/*     */     int m;
/* 226 */     for (m = paramInt; m > 0 && paramArrayOfbyte[i - m] == 0; m--);
/*     */     
/* 228 */     int n = m;
/* 229 */     if (paramArrayOfbyte[i - m] < 0) {
/* 230 */       n++;
/*     */     }
/*     */     
/* 233 */     byte[] arrayOfByte = new byte[6 + k + n];
/* 234 */     arrayOfByte[0] = 48;
/* 235 */     arrayOfByte[1] = (byte)(4 + k + n);
/* 236 */     arrayOfByte[2] = 2;
/* 237 */     arrayOfByte[3] = (byte)k;
/* 238 */     System.arraycopy(paramArrayOfbyte, paramInt - j, arrayOfByte, 4 + k - j, j);
/*     */     
/* 240 */     arrayOfByte[4 + k] = 2;
/* 241 */     arrayOfByte[5 + k] = (byte)n;
/* 242 */     System.arraycopy(paramArrayOfbyte, i - m, arrayOfByte, 6 + k + n - m, m);
/*     */ 
/*     */     
/* 245 */     return arrayOfByte;
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
/*     */   public static void checkRegisterPermission() {
/* 259 */     SecurityManager securityManager = System.getSecurityManager();
/* 260 */     if (securityManager != null)
/* 261 */       securityManager.checkPermission(REGISTER_PERMISSION); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/JavaUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */