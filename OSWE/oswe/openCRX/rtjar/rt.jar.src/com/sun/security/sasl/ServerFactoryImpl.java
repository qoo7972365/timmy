/*    */ package com.sun.security.sasl;
/*    */ 
/*    */ import com.sun.security.sasl.util.PolicyUtils;
/*    */ import java.util.Map;
/*    */ import javax.security.auth.callback.CallbackHandler;
/*    */ import javax.security.sasl.SaslException;
/*    */ import javax.security.sasl.SaslServer;
/*    */ import javax.security.sasl.SaslServerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerFactoryImpl
/*    */   implements SaslServerFactory
/*    */ {
/* 44 */   private static final String[] myMechs = new String[] { "CRAM-MD5" };
/*    */ 
/*    */ 
/*    */   
/* 48 */   private static final int[] mechPolicies = new int[] { 17 };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final int CRAMMD5 = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SaslServer createSaslServer(String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/* 63 */     if (paramString1.equals(myMechs[0]) && 
/* 64 */       PolicyUtils.checkPolicy(mechPolicies[0], paramMap)) {
/*    */       
/* 66 */       if (paramCallbackHandler == null) {
/* 67 */         throw new SaslException("Callback handler with support for AuthorizeCallback required");
/*    */       }
/*    */       
/* 70 */       return new CramMD5Server(paramString2, paramString3, paramMap, paramCallbackHandler);
/*    */     } 
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   public String[] getMechanismNames(Map<String, ?> paramMap) {
/* 76 */     return PolicyUtils.filterMechs(myMechs, mechPolicies, paramMap);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/ServerFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */