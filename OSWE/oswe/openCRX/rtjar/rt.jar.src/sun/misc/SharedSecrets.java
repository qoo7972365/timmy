/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.Console;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.net.HttpCookie;
/*     */ import java.nio.ByteOrder;
/*     */ import java.security.AccessController;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipFile;
/*     */ import javax.crypto.SealedObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SharedSecrets
/*     */ {
/*  47 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   private static JavaUtilJarAccess javaUtilJarAccess;
/*     */   private static JavaLangAccess javaLangAccess;
/*     */   private static JavaLangRefAccess javaLangRefAccess;
/*     */   private static JavaIOAccess javaIOAccess;
/*     */   private static JavaNetAccess javaNetAccess;
/*     */   private static JavaNetHttpCookieAccess javaNetHttpCookieAccess;
/*     */   private static JavaNioAccess javaNioAccess;
/*     */   private static JavaIOFileDescriptorAccess javaIOFileDescriptorAccess;
/*     */   private static JavaSecurityProtectionDomainAccess javaSecurityProtectionDomainAccess;
/*     */   private static JavaSecurityAccess javaSecurityAccess;
/*     */   private static JavaUtilZipFileAccess javaUtilZipFileAccess;
/*     */   private static JavaAWTAccess javaAWTAccess;
/*     */   private static JavaOISAccess javaOISAccess;
/*     */   private static JavaxCryptoSealedObjectAccess javaxCryptoSealedObjectAccess;
/*     */   private static JavaObjectInputStreamReadString javaObjectInputStreamReadString;
/*     */   private static JavaObjectInputStreamAccess javaObjectInputStreamAccess;
/*     */   
/*     */   public static JavaUtilJarAccess javaUtilJarAccess() {
/*  66 */     if (javaUtilJarAccess == null)
/*     */     {
/*     */       
/*  69 */       unsafe.ensureClassInitialized(JarFile.class);
/*     */     }
/*  71 */     return javaUtilJarAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaUtilJarAccess(JavaUtilJarAccess paramJavaUtilJarAccess) {
/*  75 */     javaUtilJarAccess = paramJavaUtilJarAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaLangAccess(JavaLangAccess paramJavaLangAccess) {
/*  79 */     javaLangAccess = paramJavaLangAccess;
/*     */   }
/*     */   
/*     */   public static JavaLangAccess getJavaLangAccess() {
/*  83 */     return javaLangAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaLangRefAccess(JavaLangRefAccess paramJavaLangRefAccess) {
/*  87 */     javaLangRefAccess = paramJavaLangRefAccess;
/*     */   }
/*     */   
/*     */   public static JavaLangRefAccess getJavaLangRefAccess() {
/*  91 */     return javaLangRefAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaNetAccess(JavaNetAccess paramJavaNetAccess) {
/*  95 */     javaNetAccess = paramJavaNetAccess;
/*     */   }
/*     */   
/*     */   public static JavaNetAccess getJavaNetAccess() {
/*  99 */     return javaNetAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaNetHttpCookieAccess(JavaNetHttpCookieAccess paramJavaNetHttpCookieAccess) {
/* 103 */     javaNetHttpCookieAccess = paramJavaNetHttpCookieAccess;
/*     */   }
/*     */   
/*     */   public static JavaNetHttpCookieAccess getJavaNetHttpCookieAccess() {
/* 107 */     if (javaNetHttpCookieAccess == null)
/* 108 */       unsafe.ensureClassInitialized(HttpCookie.class); 
/* 109 */     return javaNetHttpCookieAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaNioAccess(JavaNioAccess paramJavaNioAccess) {
/* 113 */     javaNioAccess = paramJavaNioAccess;
/*     */   }
/*     */   
/*     */   public static JavaNioAccess getJavaNioAccess() {
/* 117 */     if (javaNioAccess == null)
/*     */     {
/*     */ 
/*     */       
/* 121 */       unsafe.ensureClassInitialized(ByteOrder.class);
/*     */     }
/* 123 */     return javaNioAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaIOAccess(JavaIOAccess paramJavaIOAccess) {
/* 127 */     javaIOAccess = paramJavaIOAccess;
/*     */   }
/*     */   
/*     */   public static JavaIOAccess getJavaIOAccess() {
/* 131 */     if (javaIOAccess == null) {
/* 132 */       unsafe.ensureClassInitialized(Console.class);
/*     */     }
/* 134 */     return javaIOAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaIOFileDescriptorAccess(JavaIOFileDescriptorAccess paramJavaIOFileDescriptorAccess) {
/* 138 */     javaIOFileDescriptorAccess = paramJavaIOFileDescriptorAccess;
/*     */   }
/*     */   
/*     */   public static JavaIOFileDescriptorAccess getJavaIOFileDescriptorAccess() {
/* 142 */     if (javaIOFileDescriptorAccess == null) {
/* 143 */       unsafe.ensureClassInitialized(FileDescriptor.class);
/*     */     }
/* 145 */     return javaIOFileDescriptorAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaOISAccess(JavaOISAccess paramJavaOISAccess) {
/* 149 */     javaOISAccess = paramJavaOISAccess;
/*     */   }
/*     */   
/*     */   public static JavaOISAccess getJavaOISAccess() {
/* 153 */     if (javaOISAccess == null) {
/* 154 */       unsafe.ensureClassInitialized(ObjectInputStream.class);
/*     */     }
/* 156 */     return javaOISAccess;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setJavaSecurityProtectionDomainAccess(JavaSecurityProtectionDomainAccess paramJavaSecurityProtectionDomainAccess) {
/* 162 */     javaSecurityProtectionDomainAccess = paramJavaSecurityProtectionDomainAccess;
/*     */   }
/*     */ 
/*     */   
/*     */   public static JavaSecurityProtectionDomainAccess getJavaSecurityProtectionDomainAccess() {
/* 167 */     if (javaSecurityProtectionDomainAccess == null)
/* 168 */       unsafe.ensureClassInitialized(ProtectionDomain.class); 
/* 169 */     return javaSecurityProtectionDomainAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaSecurityAccess(JavaSecurityAccess paramJavaSecurityAccess) {
/* 173 */     javaSecurityAccess = paramJavaSecurityAccess;
/*     */   }
/*     */   
/*     */   public static JavaSecurityAccess getJavaSecurityAccess() {
/* 177 */     if (javaSecurityAccess == null) {
/* 178 */       unsafe.ensureClassInitialized(AccessController.class);
/*     */     }
/* 180 */     return javaSecurityAccess;
/*     */   }
/*     */   
/*     */   public static JavaUtilZipFileAccess getJavaUtilZipFileAccess() {
/* 184 */     if (javaUtilZipFileAccess == null)
/* 185 */       unsafe.ensureClassInitialized(ZipFile.class); 
/* 186 */     return javaUtilZipFileAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaUtilZipFileAccess(JavaUtilZipFileAccess paramJavaUtilZipFileAccess) {
/* 190 */     javaUtilZipFileAccess = paramJavaUtilZipFileAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaAWTAccess(JavaAWTAccess paramJavaAWTAccess) {
/* 194 */     javaAWTAccess = paramJavaAWTAccess;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaAWTAccess getJavaAWTAccess() {
/* 200 */     if (javaAWTAccess == null) {
/* 201 */       return null;
/*     */     }
/* 203 */     return javaAWTAccess;
/*     */   }
/*     */   
/*     */   public static JavaObjectInputStreamReadString getJavaObjectInputStreamReadString() {
/* 207 */     if (javaObjectInputStreamReadString == null) {
/* 208 */       unsafe.ensureClassInitialized(ObjectInputStream.class);
/*     */     }
/* 210 */     return javaObjectInputStreamReadString;
/*     */   }
/*     */   
/*     */   public static void setJavaObjectInputStreamReadString(JavaObjectInputStreamReadString paramJavaObjectInputStreamReadString) {
/* 214 */     javaObjectInputStreamReadString = paramJavaObjectInputStreamReadString;
/*     */   }
/*     */   
/*     */   public static JavaObjectInputStreamAccess getJavaObjectInputStreamAccess() {
/* 218 */     if (javaObjectInputStreamAccess == null) {
/* 219 */       unsafe.ensureClassInitialized(ObjectInputStream.class);
/*     */     }
/* 221 */     return javaObjectInputStreamAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaObjectInputStreamAccess(JavaObjectInputStreamAccess paramJavaObjectInputStreamAccess) {
/* 225 */     javaObjectInputStreamAccess = paramJavaObjectInputStreamAccess;
/*     */   }
/*     */   
/*     */   public static void setJavaxCryptoSealedObjectAccess(JavaxCryptoSealedObjectAccess paramJavaxCryptoSealedObjectAccess) {
/* 229 */     javaxCryptoSealedObjectAccess = paramJavaxCryptoSealedObjectAccess;
/*     */   }
/*     */   
/*     */   public static JavaxCryptoSealedObjectAccess getJavaxCryptoSealedObjectAccess() {
/* 233 */     if (javaxCryptoSealedObjectAccess == null) {
/* 234 */       unsafe.ensureClassInitialized(SealedObject.class);
/*     */     }
/* 236 */     return javaxCryptoSealedObjectAccess;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/SharedSecrets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */