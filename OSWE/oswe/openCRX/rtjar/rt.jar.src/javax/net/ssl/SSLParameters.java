/*     */ package javax.net.ssl;
/*     */ 
/*     */ import java.security.AlgorithmConstraints;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SSLParameters
/*     */ {
/*     */   private String[] cipherSuites;
/*     */   private String[] protocols;
/*     */   private boolean wantClientAuth;
/*     */   private boolean needClientAuth;
/*     */   private String identificationAlgorithm;
/*     */   private AlgorithmConstraints algorithmConstraints;
/*  74 */   private Map<Integer, SNIServerName> sniNames = null;
/*  75 */   private Map<Integer, SNIMatcher> sniMatchers = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean preferLocalCipherSuites;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SSLParameters() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SSLParameters(String[] paramArrayOfString) {
/* 100 */     setCipherSuites(paramArrayOfString);
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
/*     */   public SSLParameters(String[] paramArrayOfString1, String[] paramArrayOfString2) {
/* 115 */     setCipherSuites(paramArrayOfString1);
/* 116 */     setProtocols(paramArrayOfString2);
/*     */   }
/*     */   
/*     */   private static String[] clone(String[] paramArrayOfString) {
/* 120 */     return (paramArrayOfString == null) ? null : (String[])paramArrayOfString.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getCipherSuites() {
/* 131 */     return clone(this.cipherSuites);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCipherSuites(String[] paramArrayOfString) {
/* 140 */     this.cipherSuites = clone(paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getProtocols() {
/* 151 */     return clone(this.protocols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProtocols(String[] paramArrayOfString) {
/* 160 */     this.protocols = clone(paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWantClientAuth() {
/* 169 */     return this.wantClientAuth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWantClientAuth(boolean paramBoolean) {
/* 179 */     this.wantClientAuth = paramBoolean;
/* 180 */     this.needClientAuth = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getNeedClientAuth() {
/* 189 */     return this.needClientAuth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNeedClientAuth(boolean paramBoolean) {
/* 199 */     this.wantClientAuth = false;
/* 200 */     this.needClientAuth = paramBoolean;
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
/*     */   public AlgorithmConstraints getAlgorithmConstraints() {
/* 214 */     return this.algorithmConstraints;
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
/*     */   public void setAlgorithmConstraints(AlgorithmConstraints paramAlgorithmConstraints) {
/* 231 */     this.algorithmConstraints = paramAlgorithmConstraints;
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
/*     */   public String getEndpointIdentificationAlgorithm() {
/* 246 */     return this.identificationAlgorithm;
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
/*     */   public void setEndpointIdentificationAlgorithm(String paramString) {
/* 267 */     this.identificationAlgorithm = paramString;
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
/*     */   public final void setServerNames(List<SNIServerName> paramList) {
/* 294 */     if (paramList != null) {
/* 295 */       if (!paramList.isEmpty()) {
/* 296 */         this.sniNames = new LinkedHashMap<>(paramList.size());
/* 297 */         for (SNIServerName sNIServerName : paramList) {
/* 298 */           if (this.sniNames.put(Integer.valueOf(sNIServerName.getType()), sNIServerName) != null)
/*     */           {
/* 300 */             throw new IllegalArgumentException("Duplicated server name of type " + sNIServerName
/*     */                 
/* 302 */                 .getType());
/*     */           }
/*     */         } 
/*     */       } else {
/* 306 */         this.sniNames = Collections.emptyMap();
/*     */       } 
/*     */     } else {
/* 309 */       this.sniNames = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<SNIServerName> getServerNames() {
/* 352 */     if (this.sniNames != null) {
/* 353 */       if (!this.sniNames.isEmpty()) {
/* 354 */         return Collections.unmodifiableList(new ArrayList<>(this.sniNames
/* 355 */               .values()));
/*     */       }
/* 357 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/* 361 */     return null;
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
/*     */   public final void setSNIMatchers(Collection<SNIMatcher> paramCollection) {
/* 389 */     if (paramCollection != null) {
/* 390 */       if (!paramCollection.isEmpty()) {
/* 391 */         this.sniMatchers = new HashMap<>(paramCollection.size());
/* 392 */         for (SNIMatcher sNIMatcher : paramCollection) {
/* 393 */           if (this.sniMatchers.put(Integer.valueOf(sNIMatcher.getType()), sNIMatcher) != null)
/*     */           {
/* 395 */             throw new IllegalArgumentException("Duplicated server name of type " + sNIMatcher
/*     */                 
/* 397 */                 .getType());
/*     */           }
/*     */         } 
/*     */       } else {
/* 401 */         this.sniMatchers = Collections.emptyMap();
/*     */       } 
/*     */     } else {
/* 404 */       this.sniMatchers = null;
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
/*     */   public final Collection<SNIMatcher> getSNIMatchers() {
/* 427 */     if (this.sniMatchers != null) {
/* 428 */       if (!this.sniMatchers.isEmpty()) {
/* 429 */         return Collections.unmodifiableList(new ArrayList<>(this.sniMatchers
/* 430 */               .values()));
/*     */       }
/* 432 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/* 436 */     return null;
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
/*     */   public final void setUseCipherSuitesOrder(boolean paramBoolean) {
/* 451 */     this.preferLocalCipherSuites = paramBoolean;
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
/*     */   public final boolean getUseCipherSuitesOrder() {
/* 465 */     return this.preferLocalCipherSuites;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SSLParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */