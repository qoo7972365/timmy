/*     */ package com.sun.security.sasl.ntlm;
/*     */ 
/*     */ import com.sun.security.ntlm.NTLMException;
/*     */ import com.sun.security.ntlm.Server;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.sasl.RealmCallback;
/*     */ import javax.security.sasl.SaslException;
/*     */ import javax.security.sasl.SaslServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class NTLMServer
/*     */   implements SaslServer
/*     */ {
/*     */   private static final String NTLM_VERSION = "com.sun.security.sasl.ntlm.version";
/*     */   private static final String NTLM_DOMAIN = "com.sun.security.sasl.ntlm.domain";
/*     */   private static final String NTLM_HOSTNAME = "com.sun.security.sasl.ntlm.hostname";
/*     */   private static final String NTLM_RANDOM = "com.sun.security.sasl.ntlm.random";
/*     */   private final Random random;
/*     */   private final Server server;
/*     */   private byte[] nonce;
/*  98 */   private int step = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private String authzId;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String mech;
/*     */ 
/*     */   
/*     */   private String hostname;
/*     */ 
/*     */   
/*     */   private String target;
/*     */ 
/*     */ 
/*     */   
/*     */   NTLMServer(String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, final CallbackHandler cbh) throws SaslException {
/* 117 */     this.mech = paramString1;
/* 118 */     String str1 = null;
/* 119 */     String str2 = null;
/* 120 */     Random random = null;
/*     */     
/* 122 */     if (paramMap != null) {
/* 123 */       str2 = (String)paramMap.get("com.sun.security.sasl.ntlm.domain");
/* 124 */       str1 = (String)paramMap.get("com.sun.security.sasl.ntlm.version");
/* 125 */       random = (Random)paramMap.get("com.sun.security.sasl.ntlm.random");
/*     */     } 
/* 127 */     this.random = (random != null) ? random : new Random();
/*     */     
/* 129 */     if (str1 == null) {
/* 130 */       str1 = System.getProperty("ntlm.version");
/*     */     }
/* 132 */     if (str2 == null) {
/* 133 */       str2 = paramString3;
/*     */     }
/* 135 */     if (str2 == null) {
/* 136 */       throw new SaslException("Domain must be provided as the serverName argument or in props");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 141 */       this.server = new Server(str1, str2)
/*     */         {
/*     */           public char[] getPassword(String param1String1, String param1String2) {
/*     */             try {
/* 145 */               RealmCallback realmCallback = (param1String1 == null || param1String1.isEmpty()) ? new RealmCallback("Domain: ") : new RealmCallback("Domain: ", param1String1);
/*     */ 
/*     */               
/* 148 */               NameCallback nameCallback = new NameCallback("Name: ", param1String2);
/*     */               
/* 150 */               PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
/*     */               
/* 152 */               cbh.handle(new Callback[] { realmCallback, nameCallback, passwordCallback });
/* 153 */               char[] arrayOfChar = passwordCallback.getPassword();
/* 154 */               passwordCallback.clearPassword();
/* 155 */               return arrayOfChar;
/* 156 */             } catch (IOException iOException) {
/* 157 */               return null;
/* 158 */             } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 159 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/* 163 */     } catch (NTLMException nTLMException) {
/* 164 */       throw new SaslException("NTLM: server creation failure", nTLMException);
/*     */     } 
/*     */     
/* 167 */     this.nonce = new byte[8];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMechanismName() {
/* 172 */     return this.mech;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] evaluateResponse(byte[] paramArrayOfbyte) throws SaslException {
/*     */     try {
/* 178 */       this.step++;
/* 179 */       if (this.step == 1) {
/* 180 */         this.random.nextBytes(this.nonce);
/* 181 */         return this.server.type2(paramArrayOfbyte, this.nonce);
/*     */       } 
/* 183 */       String[] arrayOfString = this.server.verify(paramArrayOfbyte, this.nonce);
/* 184 */       this.authzId = arrayOfString[0];
/* 185 */       this.hostname = arrayOfString[1];
/* 186 */       this.target = arrayOfString[2];
/* 187 */       return null;
/*     */     }
/* 189 */     catch (NTLMException nTLMException) {
/* 190 */       throw new SaslException("NTLM: generate response failure", nTLMException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/* 196 */     return (this.step >= 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAuthorizationID() {
/* 201 */     if (!isComplete()) {
/* 202 */       throw new IllegalStateException("authentication not complete");
/*     */     }
/* 204 */     return this.authzId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/* 210 */     throw new IllegalStateException("Not supported yet.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/* 216 */     throw new IllegalStateException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getNegotiatedProperty(String paramString) {
/* 221 */     if (!isComplete()) {
/* 222 */       throw new IllegalStateException("authentication not complete");
/*     */     }
/* 224 */     switch (paramString) {
/*     */       case "javax.security.sasl.qop":
/* 226 */         return "auth";
/*     */       case "javax.security.sasl.bound.server.name":
/* 228 */         return this.target;
/*     */       case "com.sun.security.sasl.ntlm.hostname":
/* 230 */         return this.hostname;
/*     */     } 
/* 232 */     return null;
/*     */   }
/*     */   
/*     */   public void dispose() throws SaslException {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/ntlm/NTLMServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */