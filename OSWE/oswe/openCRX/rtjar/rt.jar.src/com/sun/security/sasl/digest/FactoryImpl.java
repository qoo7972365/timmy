/*     */ package com.sun.security.sasl.digest;
/*     */ 
/*     */ import com.sun.security.sasl.util.PolicyUtils;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.sasl.SaslClient;
/*     */ import javax.security.sasl.SaslClientFactory;
/*     */ import javax.security.sasl.SaslException;
/*     */ import javax.security.sasl.SaslServer;
/*     */ import javax.security.sasl.SaslServerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FactoryImpl
/*     */   implements SaslClientFactory, SaslServerFactory
/*     */ {
/*  47 */   private static final String[] myMechs = new String[] { "DIGEST-MD5" };
/*     */   private static final int DIGEST_MD5 = 0;
/*  49 */   private static final int[] mechPolicies = new int[] { 17 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SaslClient createSaslClient(String[] paramArrayOfString, String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/*  70 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  71 */       if (paramArrayOfString[b].equals(myMechs[0]) && 
/*  72 */         PolicyUtils.checkPolicy(mechPolicies[0], paramMap)) {
/*     */         
/*  74 */         if (paramCallbackHandler == null) {
/*  75 */           throw new SaslException("Callback handler with support for RealmChoiceCallback, RealmCallback, NameCallback, and PasswordCallback required");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  81 */         return new DigestMD5Client(paramString1, paramString2, paramString3, paramMap, paramCallbackHandler);
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return null;
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
/*     */   public SaslServer createSaslServer(String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/*  99 */     if (paramString1.equals(myMechs[0]) && 
/* 100 */       PolicyUtils.checkPolicy(mechPolicies[0], paramMap)) {
/*     */       
/* 102 */       if (paramCallbackHandler == null) {
/* 103 */         throw new SaslException("Callback handler with support for AuthorizeCallback, RealmCallback, NameCallback, and PasswordCallback required");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 109 */       return new DigestMD5Server(paramString2, paramString3, paramMap, paramCallbackHandler);
/*     */     } 
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getMechanismNames(Map<String, ?> paramMap) {
/* 121 */     return PolicyUtils.filterMechs(myMechs, mechPolicies, paramMap);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/digest/FactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */