/*     */ package javax.security.cert;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AccessController;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Security;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class X509Certificate
/*     */   extends Certificate
/*     */ {
/*     */   private static final String X509_PROVIDER = "cert.provider.x509v1";
/*     */   
/* 142 */   private static String X509Provider = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */       {
/*     */         public String run() {
/* 145 */           return Security.getProperty("cert.provider.x509v1");
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final X509Certificate getInstance(InputStream paramInputStream) throws CertificateException {
/* 175 */     return getInst(paramInputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final X509Certificate getInstance(byte[] paramArrayOfbyte) throws CertificateException {
/* 200 */     return getInst(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final X509Certificate getInst(Object paramObject) throws CertificateException {
/* 210 */     String str = X509Provider;
/* 211 */     if (str == null || str.length() == 0)
/*     */     {
/*     */       
/* 214 */       str = "com.sun.security.cert.internal.x509.X509V1CertImpl";
/*     */     }
/*     */     try {
/* 217 */       Class[] arrayOfClass = null;
/* 218 */       if (paramObject instanceof InputStream) {
/* 219 */         arrayOfClass = new Class[] { InputStream.class };
/* 220 */       } else if (paramObject instanceof byte[]) {
/* 221 */         arrayOfClass = new Class[] { paramObject.getClass() };
/*     */       } else {
/* 223 */         throw new CertificateException("Unsupported argument type");
/* 224 */       }  Class<?> clazz = Class.forName(str);
/*     */ 
/*     */       
/* 227 */       Constructor<?> constructor = clazz.getConstructor(arrayOfClass);
/*     */ 
/*     */       
/* 230 */       Object object = constructor.newInstance(new Object[] { paramObject });
/* 231 */       return (X509Certificate)object;
/*     */     }
/* 233 */     catch (ClassNotFoundException classNotFoundException) {
/* 234 */       throw new CertificateException("Could not find class: " + classNotFoundException);
/* 235 */     } catch (IllegalAccessException illegalAccessException) {
/* 236 */       throw new CertificateException("Could not access class: " + illegalAccessException);
/* 237 */     } catch (InstantiationException instantiationException) {
/* 238 */       throw new CertificateException("Problems instantiating: " + instantiationException);
/* 239 */     } catch (InvocationTargetException invocationTargetException) {
/* 240 */       throw new CertificateException("InvocationTargetException: " + invocationTargetException
/* 241 */           .getTargetException());
/* 242 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 243 */       throw new CertificateException("Could not find class method: " + noSuchMethodException
/* 244 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException;
/*     */   
/*     */   public abstract void checkValidity(Date paramDate) throws CertificateExpiredException, CertificateNotYetValidException;
/*     */   
/*     */   public abstract int getVersion();
/*     */   
/*     */   public abstract BigInteger getSerialNumber();
/*     */   
/*     */   public abstract Principal getIssuerDN();
/*     */   
/*     */   public abstract Principal getSubjectDN();
/*     */   
/*     */   public abstract Date getNotBefore();
/*     */   
/*     */   public abstract Date getNotAfter();
/*     */   
/*     */   public abstract String getSigAlgName();
/*     */   
/*     */   public abstract String getSigAlgOID();
/*     */   
/*     */   public abstract byte[] getSigAlgParams();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/cert/X509Certificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */