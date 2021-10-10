/*     */ package javax.xml.crypto.dsig;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.NoSuchMechanismException;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XMLSignatureFactory
/*     */ {
/*     */   private String mechanismType;
/*     */   private Provider provider;
/*     */   
/*     */   public static XMLSignatureFactory getInstance(String paramString) {
/*     */     GetInstance.Instance instance;
/* 190 */     if (paramString == null) {
/* 191 */       throw new NullPointerException("mechanismType cannot be null");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 196 */       instance = GetInstance.getInstance("XMLSignatureFactory", (Class<?>)null, paramString);
/* 197 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 198 */       throw new NoSuchMechanismException(noSuchAlgorithmException);
/*     */     } 
/* 200 */     XMLSignatureFactory xMLSignatureFactory = (XMLSignatureFactory)instance.impl;
/* 201 */     xMLSignatureFactory.mechanismType = paramString;
/* 202 */     xMLSignatureFactory.provider = instance.provider;
/* 203 */     return xMLSignatureFactory;
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
/*     */   public static XMLSignatureFactory getInstance(String paramString, Provider paramProvider) {
/*     */     GetInstance.Instance instance;
/* 229 */     if (paramString == null)
/* 230 */       throw new NullPointerException("mechanismType cannot be null"); 
/* 231 */     if (paramProvider == null) {
/* 232 */       throw new NullPointerException("provider cannot be null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 238 */       instance = GetInstance.getInstance("XMLSignatureFactory", (Class<?>)null, paramString, paramProvider);
/* 239 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 240 */       throw new NoSuchMechanismException(noSuchAlgorithmException);
/*     */     } 
/* 242 */     XMLSignatureFactory xMLSignatureFactory = (XMLSignatureFactory)instance.impl;
/* 243 */     xMLSignatureFactory.mechanismType = paramString;
/* 244 */     xMLSignatureFactory.provider = instance.provider;
/* 245 */     return xMLSignatureFactory;
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
/*     */   public static XMLSignatureFactory getInstance(String paramString1, String paramString2) throws NoSuchProviderException {
/*     */     GetInstance.Instance instance;
/* 275 */     if (paramString1 == null)
/* 276 */       throw new NullPointerException("mechanismType cannot be null"); 
/* 277 */     if (paramString2 == null)
/* 278 */       throw new NullPointerException("provider cannot be null"); 
/* 279 */     if (paramString2.length() == 0) {
/* 280 */       throw new NoSuchProviderException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 286 */       instance = GetInstance.getInstance("XMLSignatureFactory", (Class<?>)null, paramString1, paramString2);
/* 287 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 288 */       throw new NoSuchMechanismException(noSuchAlgorithmException);
/*     */     } 
/* 290 */     XMLSignatureFactory xMLSignatureFactory = (XMLSignatureFactory)instance.impl;
/* 291 */     xMLSignatureFactory.mechanismType = paramString1;
/* 292 */     xMLSignatureFactory.provider = instance.provider;
/* 293 */     return xMLSignatureFactory;
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
/*     */   public static XMLSignatureFactory getInstance() {
/* 318 */     return getInstance("DOM");
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
/* 329 */     return this.mechanismType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 338 */     return this.provider;
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
/*     */   public abstract XMLSignature newXMLSignature(SignedInfo paramSignedInfo, KeyInfo paramKeyInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract XMLSignature newXMLSignature(SignedInfo paramSignedInfo, KeyInfo paramKeyInfo, List paramList, String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Reference newReference(String paramString, DigestMethod paramDigestMethod);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Reference newReference(String paramString1, DigestMethod paramDigestMethod, List paramList, String paramString2, String paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Reference newReference(String paramString1, DigestMethod paramDigestMethod, List paramList, String paramString2, String paramString3, byte[] paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Reference newReference(String paramString1, DigestMethod paramDigestMethod, List paramList1, Data paramData, List paramList2, String paramString2, String paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SignedInfo newSignedInfo(CanonicalizationMethod paramCanonicalizationMethod, SignatureMethod paramSignatureMethod, List paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SignedInfo newSignedInfo(CanonicalizationMethod paramCanonicalizationMethod, SignatureMethod paramSignatureMethod, List paramList, String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract XMLObject newXMLObject(List paramList, String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Manifest newManifest(List paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Manifest newManifest(List paramList, String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SignatureProperty newSignatureProperty(List paramList, String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SignatureProperties newSignatureProperties(List paramList, String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract DigestMethod newDigestMethod(String paramString, DigestMethodParameterSpec paramDigestMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SignatureMethod newSignatureMethod(String paramString, SignatureMethodParameterSpec paramSignatureMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Transform newTransform(String paramString, TransformParameterSpec paramTransformParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Transform newTransform(String paramString, XMLStructure paramXMLStructure) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract CanonicalizationMethod newCanonicalizationMethod(String paramString, C14NMethodParameterSpec paramC14NMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract CanonicalizationMethod newCanonicalizationMethod(String paramString, XMLStructure paramXMLStructure) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final KeyInfoFactory getKeyInfoFactory() {
/* 747 */     return KeyInfoFactory.getInstance(getMechanismType(), getProvider());
/*     */   }
/*     */   
/*     */   public abstract XMLSignature unmarshalXMLSignature(XMLValidateContext paramXMLValidateContext) throws MarshalException;
/*     */   
/*     */   public abstract XMLSignature unmarshalXMLSignature(XMLStructure paramXMLStructure) throws MarshalException;
/*     */   
/*     */   public abstract boolean isFeatureSupported(String paramString);
/*     */   
/*     */   public abstract URIDereferencer getURIDereferencer();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/XMLSignatureFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */