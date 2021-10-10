/*     */ package javax.xml.crypto.dsig.keyinfo;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.KeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.NoSuchMechanismException;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import sun.security.jca.GetInstance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyInfoFactory
/*     */ {
/*     */   private String mechanismType;
/*     */   private Provider provider;
/*     */   
/*     */   public static KeyInfoFactory getInstance(String paramString) {
/*     */     GetInstance.Instance instance;
/* 145 */     if (paramString == null) {
/* 146 */       throw new NullPointerException("mechanismType cannot be null");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 151 */       instance = GetInstance.getInstance("KeyInfoFactory", (Class<?>)null, paramString);
/* 152 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 153 */       throw new NoSuchMechanismException(noSuchAlgorithmException);
/*     */     } 
/* 155 */     KeyInfoFactory keyInfoFactory = (KeyInfoFactory)instance.impl;
/* 156 */     keyInfoFactory.mechanismType = paramString;
/* 157 */     keyInfoFactory.provider = instance.provider;
/* 158 */     return keyInfoFactory;
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
/*     */   public static KeyInfoFactory getInstance(String paramString, Provider paramProvider) {
/*     */     GetInstance.Instance instance;
/* 184 */     if (paramString == null)
/* 185 */       throw new NullPointerException("mechanismType cannot be null"); 
/* 186 */     if (paramProvider == null) {
/* 187 */       throw new NullPointerException("provider cannot be null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 193 */       instance = GetInstance.getInstance("KeyInfoFactory", (Class<?>)null, paramString, paramProvider);
/* 194 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 195 */       throw new NoSuchMechanismException(noSuchAlgorithmException);
/*     */     } 
/* 197 */     KeyInfoFactory keyInfoFactory = (KeyInfoFactory)instance.impl;
/* 198 */     keyInfoFactory.mechanismType = paramString;
/* 199 */     keyInfoFactory.provider = instance.provider;
/* 200 */     return keyInfoFactory;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyInfoFactory getInstance(String paramString1, String paramString2) throws NoSuchProviderException {
/*     */     GetInstance.Instance instance;
/* 230 */     if (paramString1 == null)
/* 231 */       throw new NullPointerException("mechanismType cannot be null"); 
/* 232 */     if (paramString2 == null)
/* 233 */       throw new NullPointerException("provider cannot be null"); 
/* 234 */     if (paramString2.length() == 0) {
/* 235 */       throw new NoSuchProviderException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 241 */       instance = GetInstance.getInstance("KeyInfoFactory", (Class<?>)null, paramString1, paramString2);
/* 242 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 243 */       throw new NoSuchMechanismException(noSuchAlgorithmException);
/*     */     } 
/* 245 */     KeyInfoFactory keyInfoFactory = (KeyInfoFactory)instance.impl;
/* 246 */     keyInfoFactory.mechanismType = paramString1;
/* 247 */     keyInfoFactory.provider = instance.provider;
/* 248 */     return keyInfoFactory;
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
/*     */   public static KeyInfoFactory getInstance() {
/* 272 */     return getInstance("DOM");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getMechanismType() {
/* 283 */     return this.mechanismType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 292 */     return this.provider;
/*     */   }
/*     */   
/*     */   public abstract KeyInfo newKeyInfo(List paramList);
/*     */   
/*     */   public abstract KeyInfo newKeyInfo(List paramList, String paramString);
/*     */   
/*     */   public abstract KeyName newKeyName(String paramString);
/*     */   
/*     */   public abstract KeyValue newKeyValue(PublicKey paramPublicKey) throws KeyException;
/*     */   
/*     */   public abstract PGPData newPGPData(byte[] paramArrayOfbyte);
/*     */   
/*     */   public abstract PGPData newPGPData(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, List paramList);
/*     */   
/*     */   public abstract PGPData newPGPData(byte[] paramArrayOfbyte, List paramList);
/*     */   
/*     */   public abstract RetrievalMethod newRetrievalMethod(String paramString);
/*     */   
/*     */   public abstract RetrievalMethod newRetrievalMethod(String paramString1, String paramString2, List paramList);
/*     */   
/*     */   public abstract X509Data newX509Data(List paramList);
/*     */   
/*     */   public abstract X509IssuerSerial newX509IssuerSerial(String paramString, BigInteger paramBigInteger);
/*     */   
/*     */   public abstract boolean isFeatureSupported(String paramString);
/*     */   
/*     */   public abstract URIDereferencer getURIDereferencer();
/*     */   
/*     */   public abstract KeyInfo unmarshalKeyInfo(XMLStructure paramXMLStructure) throws MarshalException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/keyinfo/KeyInfoFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */