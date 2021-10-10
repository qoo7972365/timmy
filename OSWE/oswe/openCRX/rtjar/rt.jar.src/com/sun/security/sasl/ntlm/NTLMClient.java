/*     */ package com.sun.security.sasl.ntlm;
/*     */ 
/*     */ import com.sun.security.ntlm.Client;
/*     */ import com.sun.security.ntlm.NTLMException;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.sasl.RealmCallback;
/*     */ import javax.security.sasl.SaslClient;
/*     */ import javax.security.sasl.SaslException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class NTLMClient
/*     */   implements SaslClient
/*     */ {
/*     */   private static final String NTLM_VERSION = "com.sun.security.sasl.ntlm.version";
/*     */   private static final String NTLM_RANDOM = "com.sun.security.sasl.ntlm.random";
/*     */   private static final String NTLM_DOMAIN = "com.sun.security.sasl.ntlm.domain";
/*     */   private static final String NTLM_HOSTNAME = "com.sun.security.sasl.ntlm.hostname";
/*     */   private final Client client;
/*     */   private final String mech;
/*     */   private final Random random;
/* 102 */   private int step = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NTLMClient(String paramString1, String paramString2, String paramString3, String paramString4, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/* 116 */     this.mech = paramString1;
/* 117 */     String str1 = null;
/* 118 */     Random random = null;
/* 119 */     String str2 = null;
/*     */     
/* 121 */     if (paramMap != null) {
/* 122 */       String str = (String)paramMap.get("javax.security.sasl.qop");
/* 123 */       if (str != null && !str.equals("auth")) {
/* 124 */         throw new SaslException("NTLM only support auth");
/*     */       }
/* 126 */       str1 = (String)paramMap.get("com.sun.security.sasl.ntlm.version");
/* 127 */       random = (Random)paramMap.get("com.sun.security.sasl.ntlm.random");
/* 128 */       str2 = (String)paramMap.get("com.sun.security.sasl.ntlm.hostname");
/*     */     } 
/* 130 */     this.random = (random != null) ? random : new Random();
/*     */     
/* 132 */     if (str1 == null) {
/* 133 */       str1 = System.getProperty("ntlm.version");
/*     */     }
/*     */     
/* 136 */     RealmCallback realmCallback = (paramString4 != null && !paramString4.isEmpty()) ? new RealmCallback("Realm: ", paramString4) : new RealmCallback("Realm: ");
/*     */ 
/*     */     
/* 139 */     NameCallback nameCallback = (paramString2 != null && !paramString2.isEmpty()) ? new NameCallback("User name: ", paramString2) : new NameCallback("User name: ");
/*     */ 
/*     */     
/* 142 */     PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
/*     */ 
/*     */     
/*     */     try {
/* 146 */       paramCallbackHandler.handle(new Callback[] { realmCallback, nameCallback, passwordCallback });
/* 147 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 148 */       throw new SaslException("NTLM: Cannot perform callback to acquire realm, username or password", unsupportedCallbackException);
/*     */     }
/* 150 */     catch (IOException iOException) {
/* 151 */       throw new SaslException("NTLM: Error acquiring realm, username or password", iOException);
/*     */     } 
/*     */ 
/*     */     
/* 155 */     if (str2 == null) {
/*     */       try {
/* 157 */         str2 = InetAddress.getLocalHost().getCanonicalHostName();
/* 158 */       } catch (UnknownHostException unknownHostException) {
/* 159 */         str2 = "localhost";
/*     */       } 
/*     */     }
/*     */     try {
/* 163 */       String str3 = nameCallback.getName();
/* 164 */       if (str3 == null) {
/* 165 */         str3 = paramString2;
/*     */       }
/* 167 */       String str4 = realmCallback.getText();
/* 168 */       if (str4 == null) {
/* 169 */         str4 = paramString4;
/*     */       }
/* 171 */       this
/*     */ 
/*     */         
/* 174 */         .client = new Client(str1, str2, str3, str4, passwordCallback.getPassword());
/* 175 */     } catch (NTLMException nTLMException) {
/* 176 */       throw new SaslException("NTLM: client creation failure", nTLMException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMechanismName() {
/* 183 */     return this.mech;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/* 188 */     return (this.step >= 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/* 194 */     throw new IllegalStateException("Not supported.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/* 200 */     throw new IllegalStateException("Not supported.");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getNegotiatedProperty(String paramString) {
/* 205 */     if (!isComplete()) {
/* 206 */       throw new IllegalStateException("authentication not complete");
/*     */     }
/* 208 */     switch (paramString) {
/*     */       case "javax.security.sasl.qop":
/* 210 */         return "auth";
/*     */       case "com.sun.security.sasl.ntlm.domain":
/* 212 */         return this.client.getDomain();
/*     */     } 
/* 214 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() throws SaslException {
/* 220 */     this.client.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasInitialResponse() {
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] evaluateChallenge(byte[] paramArrayOfbyte) throws SaslException {
/* 230 */     this.step++;
/* 231 */     if (this.step == 1) {
/* 232 */       return this.client.type1();
/*     */     }
/*     */     try {
/* 235 */       byte[] arrayOfByte = new byte[8];
/* 236 */       this.random.nextBytes(arrayOfByte);
/* 237 */       return this.client.type3(paramArrayOfbyte, arrayOfByte);
/* 238 */     } catch (NTLMException nTLMException) {
/* 239 */       throw new SaslException("Type3 creation failed", nTLMException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/ntlm/NTLMClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */