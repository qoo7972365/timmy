/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactorySpi;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.interfaces.DSAPrivateKey;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.security.spec.DSAPrivateKeySpec;
/*     */ import java.security.spec.DSAPublicKeySpec;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.KeySpec;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DSAKeyFactory
/*     */   extends KeyFactorySpi
/*     */ {
/*     */   static final boolean SERIAL_INTEROP;
/*     */   private static final String SERIAL_PROP = "sun.security.key.serial.interop";
/*     */   
/*     */   static {
/*  74 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.security.key.serial.interop", null));
/*  75 */     SERIAL_INTEROP = "true".equalsIgnoreCase(str);
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
/*     */   protected PublicKey engineGeneratePublic(KeySpec paramKeySpec) throws InvalidKeySpecException {
/*     */     try {
/*  92 */       if (paramKeySpec instanceof DSAPublicKeySpec) {
/*  93 */         DSAPublicKeySpec dSAPublicKeySpec = (DSAPublicKeySpec)paramKeySpec;
/*  94 */         if (SERIAL_INTEROP) {
/*  95 */           return new DSAPublicKey(dSAPublicKeySpec.getY(), dSAPublicKeySpec
/*  96 */               .getP(), dSAPublicKeySpec
/*  97 */               .getQ(), dSAPublicKeySpec
/*  98 */               .getG());
/*     */         }
/* 100 */         return new DSAPublicKeyImpl(dSAPublicKeySpec.getY(), dSAPublicKeySpec
/* 101 */             .getP(), dSAPublicKeySpec
/* 102 */             .getQ(), dSAPublicKeySpec
/* 103 */             .getG());
/*     */       } 
/* 105 */       if (paramKeySpec instanceof X509EncodedKeySpec) {
/* 106 */         if (SERIAL_INTEROP) {
/* 107 */           return new DSAPublicKey(((X509EncodedKeySpec)paramKeySpec)
/* 108 */               .getEncoded());
/*     */         }
/* 110 */         return new DSAPublicKeyImpl(((X509EncodedKeySpec)paramKeySpec)
/* 111 */             .getEncoded());
/*     */       } 
/*     */       
/* 114 */       throw new InvalidKeySpecException("Inappropriate key specification");
/*     */     
/*     */     }
/* 117 */     catch (InvalidKeyException invalidKeyException) {
/* 118 */       throw new InvalidKeySpecException("Inappropriate key specification: " + invalidKeyException
/* 119 */           .getMessage());
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
/*     */ 
/*     */   
/*     */   protected PrivateKey engineGeneratePrivate(KeySpec paramKeySpec) throws InvalidKeySpecException {
/*     */     try {
/* 137 */       if (paramKeySpec instanceof DSAPrivateKeySpec) {
/* 138 */         DSAPrivateKeySpec dSAPrivateKeySpec = (DSAPrivateKeySpec)paramKeySpec;
/* 139 */         return new DSAPrivateKey(dSAPrivateKeySpec.getX(), dSAPrivateKeySpec
/* 140 */             .getP(), dSAPrivateKeySpec
/* 141 */             .getQ(), dSAPrivateKeySpec
/* 142 */             .getG());
/*     */       } 
/* 144 */       if (paramKeySpec instanceof PKCS8EncodedKeySpec) {
/* 145 */         return new DSAPrivateKey(((PKCS8EncodedKeySpec)paramKeySpec)
/* 146 */             .getEncoded());
/*     */       }
/*     */       
/* 149 */       throw new InvalidKeySpecException("Inappropriate key specification");
/*     */     
/*     */     }
/* 152 */     catch (InvalidKeyException invalidKeyException) {
/* 153 */       throw new InvalidKeySpecException("Inappropriate key specification: " + invalidKeyException
/* 154 */           .getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T extends KeySpec> T engineGetKeySpec(Key paramKey, Class<T> paramClass) throws InvalidKeySpecException {
/*     */     try {
/* 182 */       if (paramKey instanceof DSAPublicKey) {
/*     */ 
/*     */ 
/*     */         
/* 186 */         Class<?> clazz1 = Class.forName("java.security.spec.DSAPublicKeySpec");
/*     */         
/* 188 */         Class<?> clazz2 = Class.forName("java.security.spec.X509EncodedKeySpec");
/*     */         
/* 190 */         if (clazz1.isAssignableFrom(paramClass)) {
/* 191 */           DSAPublicKey dSAPublicKey = (DSAPublicKey)paramKey;
/*     */           
/* 193 */           DSAParams dSAParams = dSAPublicKey.getParams();
/* 194 */           return paramClass.cast(new DSAPublicKeySpec(dSAPublicKey.getY(), dSAParams
/* 195 */                 .getP(), dSAParams
/* 196 */                 .getQ(), dSAParams
/* 197 */                 .getG()));
/*     */         } 
/* 199 */         if (clazz2.isAssignableFrom(paramClass)) {
/* 200 */           return paramClass.cast(new X509EncodedKeySpec(paramKey.getEncoded()));
/*     */         }
/*     */         
/* 203 */         throw new InvalidKeySpecException("Inappropriate key specification");
/*     */       } 
/*     */ 
/*     */       
/* 207 */       if (paramKey instanceof DSAPrivateKey) {
/*     */ 
/*     */ 
/*     */         
/* 211 */         Class<?> clazz1 = Class.forName("java.security.spec.DSAPrivateKeySpec");
/*     */         
/* 213 */         Class<?> clazz2 = Class.forName("java.security.spec.PKCS8EncodedKeySpec");
/*     */         
/* 215 */         if (clazz1.isAssignableFrom(paramClass)) {
/* 216 */           DSAPrivateKey dSAPrivateKey = (DSAPrivateKey)paramKey;
/*     */           
/* 218 */           DSAParams dSAParams = dSAPrivateKey.getParams();
/* 219 */           return paramClass.cast(new DSAPrivateKeySpec(dSAPrivateKey.getX(), dSAParams
/* 220 */                 .getP(), dSAParams
/* 221 */                 .getQ(), dSAParams
/* 222 */                 .getG()));
/*     */         } 
/* 224 */         if (clazz2.isAssignableFrom(paramClass)) {
/* 225 */           return paramClass.cast(new PKCS8EncodedKeySpec(paramKey.getEncoded()));
/*     */         }
/*     */         
/* 228 */         throw new InvalidKeySpecException("Inappropriate key specification");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 233 */       throw new InvalidKeySpecException("Inappropriate key type");
/*     */     
/*     */     }
/* 236 */     catch (ClassNotFoundException classNotFoundException) {
/* 237 */       throw new InvalidKeySpecException("Unsupported key specification: " + classNotFoundException
/* 238 */           .getMessage());
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected Key engineTranslateKey(Key paramKey) throws InvalidKeyException {
/*     */     try {
/* 257 */       if (paramKey instanceof DSAPublicKey) {
/*     */         
/* 259 */         if (paramKey instanceof DSAPublicKey) {
/* 260 */           return paramKey;
/*     */         }
/*     */ 
/*     */         
/* 264 */         DSAPublicKeySpec dSAPublicKeySpec = engineGetKeySpec(paramKey, DSAPublicKeySpec.class);
/*     */         
/* 266 */         return engineGeneratePublic(dSAPublicKeySpec);
/*     */       } 
/* 268 */       if (paramKey instanceof DSAPrivateKey) {
/*     */         
/* 270 */         if (paramKey instanceof DSAPrivateKey) {
/* 271 */           return paramKey;
/*     */         }
/*     */ 
/*     */         
/* 275 */         DSAPrivateKeySpec dSAPrivateKeySpec = engineGetKeySpec(paramKey, DSAPrivateKeySpec.class);
/*     */         
/* 277 */         return engineGeneratePrivate(dSAPrivateKeySpec);
/*     */       } 
/*     */       
/* 280 */       throw new InvalidKeyException("Wrong algorithm type");
/*     */     
/*     */     }
/* 283 */     catch (InvalidKeySpecException invalidKeySpecException) {
/* 284 */       throw new InvalidKeyException("Cannot translate key: " + invalidKeySpecException
/* 285 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DSAKeyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */