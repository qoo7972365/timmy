/*     */ package java.security.cert;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PKIXRevocationChecker
/*     */   extends PKIXCertPathChecker
/*     */ {
/*     */   private URI ocspResponder;
/*     */   private X509Certificate ocspResponderCert;
/* 102 */   private List<Extension> ocspExtensions = Collections.emptyList();
/* 103 */   private Map<X509Certificate, byte[]> ocspResponses = (Map)Collections.emptyMap();
/* 104 */   private Set<Option> options = Collections.emptySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOcspResponder(URI paramURI) {
/* 120 */     this.ocspResponder = paramURI;
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
/*     */   public URI getOcspResponder() {
/* 133 */     return this.ocspResponder;
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
/*     */   public void setOcspResponderCert(X509Certificate paramX509Certificate) {
/* 145 */     this.ocspResponderCert = paramX509Certificate;
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
/*     */   public X509Certificate getOcspResponderCert() {
/* 159 */     return this.ocspResponderCert;
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
/*     */   public void setOcspExtensions(List<Extension> paramList) {
/* 171 */     this
/* 172 */       .ocspExtensions = (paramList == null) ? Collections.<Extension>emptyList() : new ArrayList<>(paramList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Extension> getOcspExtensions() {
/* 183 */     return Collections.unmodifiableList(this.ocspExtensions);
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
/*     */   public void setOcspResponses(Map<X509Certificate, byte[]> paramMap) {
/* 197 */     if (paramMap == null) {
/* 198 */       this.ocspResponses = Collections.emptyMap();
/*     */     } else {
/* 200 */       HashMap<Object, Object> hashMap = new HashMap<>(paramMap.size());
/* 201 */       for (Map.Entry<X509Certificate, byte> entry : paramMap.entrySet()) {
/* 202 */         hashMap.put(entry.getKey(), ((byte[])entry.getValue()).clone());
/*     */       }
/* 204 */       this.ocspResponses = (Map)hashMap;
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
/*     */   public Map<X509Certificate, byte[]> getOcspResponses() {
/* 219 */     HashMap<Object, Object> hashMap = new HashMap<>(this.ocspResponses.size());
/* 220 */     for (Map.Entry<X509Certificate, byte> entry : this.ocspResponses.entrySet()) {
/* 221 */       hashMap.put(entry.getKey(), ((byte[])entry.getValue()).clone());
/*     */     }
/* 223 */     return (Map)hashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOptions(Set<Option> paramSet) {
/* 233 */     this
/* 234 */       .options = (paramSet == null) ? Collections.<Option>emptySet() : new HashSet<>(paramSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Option> getOptions() {
/* 245 */     return Collections.unmodifiableSet(this.options);
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
/*     */   public PKIXRevocationChecker clone() {
/* 266 */     PKIXRevocationChecker pKIXRevocationChecker = (PKIXRevocationChecker)super.clone();
/* 267 */     pKIXRevocationChecker.ocspExtensions = new ArrayList<>(this.ocspExtensions);
/* 268 */     pKIXRevocationChecker.ocspResponses = (Map)new HashMap<>((Map)this.ocspResponses);
/*     */ 
/*     */     
/* 271 */     for (Map.Entry<X509Certificate, byte> entry : pKIXRevocationChecker.ocspResponses.entrySet()) {
/*     */       
/* 273 */       byte[] arrayOfByte = (byte[])entry.getValue();
/* 274 */       entry.setValue(arrayOfByte.clone());
/*     */     } 
/* 276 */     pKIXRevocationChecker.options = new HashSet<>(this.options);
/* 277 */     return pKIXRevocationChecker;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract List<CertPathValidatorException> getSoftFailExceptions();
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Option
/*     */   {
/* 288 */     ONLY_END_ENTITY,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     PREFER_CRLS,
/*     */ 
/*     */ 
/*     */     
/* 298 */     NO_FALLBACK,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     SOFT_FAIL;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/PKIXRevocationChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */