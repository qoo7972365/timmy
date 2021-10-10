/*     */ package javax.xml.crypto.dsig;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
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
/*     */ public abstract class TransformService
/*     */   implements Transform
/*     */ {
/*     */   private String algorithm;
/*     */   private String mechanism;
/*     */   private Provider provider;
/*     */   
/*     */   public static TransformService getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException {
/* 153 */     if (paramString2 == null || paramString1 == null) {
/* 154 */       throw new NullPointerException();
/*     */     }
/* 156 */     boolean bool = false;
/* 157 */     if (paramString2.equals("DOM")) {
/* 158 */       bool = true;
/*     */     }
/* 160 */     List<Provider.Service> list = GetInstance.getServices("TransformService", paramString1);
/* 161 */     for (Provider.Service service : list) {
/*     */       
/* 163 */       String str = service.getAttribute("MechanismType");
/* 164 */       if ((str == null && bool) || (str != null && str
/* 165 */         .equals(paramString2))) {
/* 166 */         GetInstance.Instance instance = GetInstance.getInstance(service, null);
/* 167 */         TransformService transformService = (TransformService)instance.impl;
/* 168 */         transformService.algorithm = paramString1;
/* 169 */         transformService.mechanism = paramString2;
/* 170 */         transformService.provider = instance.provider;
/* 171 */         return transformService;
/*     */       } 
/*     */     } 
/* 174 */     throw new NoSuchAlgorithmException(paramString1 + " algorithm and " + paramString2 + " mechanism not available");
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
/*     */   public static TransformService getInstance(String paramString1, String paramString2, Provider paramProvider) throws NoSuchAlgorithmException {
/* 202 */     if (paramString2 == null || paramString1 == null || paramProvider == null) {
/* 203 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 206 */     boolean bool = false;
/* 207 */     if (paramString2.equals("DOM")) {
/* 208 */       bool = true;
/*     */     }
/*     */     
/* 211 */     Provider.Service service = GetInstance.getService("TransformService", paramString1, paramProvider);
/* 212 */     String str = service.getAttribute("MechanismType");
/* 213 */     if ((str == null && bool) || (str != null && str
/* 214 */       .equals(paramString2))) {
/* 215 */       GetInstance.Instance instance = GetInstance.getInstance(service, null);
/* 216 */       TransformService transformService = (TransformService)instance.impl;
/* 217 */       transformService.algorithm = paramString1;
/* 218 */       transformService.mechanism = paramString2;
/* 219 */       transformService.provider = instance.provider;
/* 220 */       return transformService;
/*     */     } 
/* 222 */     throw new NoSuchAlgorithmException(paramString1 + " algorithm and " + paramString2 + " mechanism not available");
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static TransformService getInstance(String paramString1, String paramString2, String paramString3) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 254 */     if (paramString2 == null || paramString1 == null || paramString3 == null)
/* 255 */       throw new NullPointerException(); 
/* 256 */     if (paramString3.length() == 0) {
/* 257 */       throw new NoSuchProviderException();
/*     */     }
/* 259 */     boolean bool = false;
/* 260 */     if (paramString2.equals("DOM")) {
/* 261 */       bool = true;
/*     */     }
/*     */     
/* 264 */     Provider.Service service = GetInstance.getService("TransformService", paramString1, paramString3);
/* 265 */     String str = service.getAttribute("MechanismType");
/* 266 */     if ((str == null && bool) || (str != null && str
/* 267 */       .equals(paramString2))) {
/* 268 */       GetInstance.Instance instance = GetInstance.getInstance(service, null);
/* 269 */       TransformService transformService = (TransformService)instance.impl;
/* 270 */       transformService.algorithm = paramString1;
/* 271 */       transformService.mechanism = paramString2;
/* 272 */       transformService.provider = instance.provider;
/* 273 */       return transformService;
/*     */     } 
/* 275 */     throw new NoSuchAlgorithmException(paramString1 + " algorithm and " + paramString2 + " mechanism not available");
/*     */   }
/*     */   
/*     */   private static class MechanismMapEntry
/*     */     implements Map.Entry<String, String> {
/*     */     private final String mechanism;
/*     */     private final String algorithm;
/*     */     private final String key;
/*     */     
/*     */     MechanismMapEntry(String param1String1, String param1String2) {
/* 285 */       this.algorithm = param1String1;
/* 286 */       this.mechanism = param1String2;
/* 287 */       this.key = "TransformService." + param1String1 + " MechanismType";
/*     */     }
/*     */     public boolean equals(Object param1Object) {
/* 290 */       if (!(param1Object instanceof Map.Entry)) {
/* 291 */         return false;
/*     */       }
/* 293 */       Map.Entry entry = (Map.Entry)param1Object;
/* 294 */       if ((getKey() == null) ? (entry
/* 295 */         .getKey() == null) : getKey().equals(entry.getKey()))
/* 296 */         if ((getValue() == null) ? (entry
/* 297 */           .getValue() == null) : getValue().equals(entry.getValue())); 
/*     */       return false;
/*     */     } public String getKey() {
/* 300 */       return this.key;
/*     */     }
/*     */     public String getValue() {
/* 303 */       return this.mechanism;
/*     */     }
/*     */     public String setValue(String param1String) {
/* 306 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public int hashCode() {
/* 309 */       return ((getKey() == null) ? 0 : getKey().hashCode()) ^ (
/* 310 */         (getValue() == null) ? 0 : getValue().hashCode());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getMechanismType() {
/* 320 */     return this.mechanism;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAlgorithm() {
/* 330 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 339 */     return this.provider;
/*     */   }
/*     */   
/*     */   public abstract void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException;
/*     */   
/*     */   public abstract void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException;
/*     */   
/*     */   public abstract void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/TransformService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */