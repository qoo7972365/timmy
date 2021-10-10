/*     */ package com.sun.jndi.ldap.sasl;
/*     */ 
/*     */ import com.sun.jndi.ldap.Connection;
/*     */ import com.sun.jndi.ldap.LdapClient;
/*     */ import com.sun.jndi.ldap.LdapResult;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.naming.AuthenticationException;
/*     */ import javax.naming.AuthenticationNotSupportedException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.sasl.Sasl;
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
/*     */ public final class LdapSasl
/*     */ {
/*     */   private static final String SASL_CALLBACK = "java.naming.security.sasl.callback";
/*     */   private static final String SASL_AUTHZ_ID = "java.naming.security.sasl.authorizationId";
/*     */   private static final String SASL_REALM = "java.naming.security.sasl.realm";
/*     */   private static final int LDAP_SUCCESS = 0;
/*     */   private static final int LDAP_SASL_BIND_IN_PROGRESS = 14;
/*     */   
/*     */   public static LdapResult saslBind(LdapClient paramLdapClient, Connection paramConnection, String paramString1, String paramString2, Object paramObject, String paramString3, Hashtable<?, ?> paramHashtable, Control[] paramArrayOfControl) throws IOException, NamingException {
/*  98 */     SaslClient saslClient = null;
/*  99 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */     
/* 103 */     CallbackHandler callbackHandler = (paramHashtable != null) ? (CallbackHandler)paramHashtable.get("java.naming.security.sasl.callback") : null;
/* 104 */     if (callbackHandler == null) {
/* 105 */       callbackHandler = new DefaultCallbackHandler(paramString2, paramObject, (String)paramHashtable.get("java.naming.security.sasl.realm"));
/* 106 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 110 */     String str = (paramHashtable != null) ? (String)paramHashtable.get("java.naming.security.sasl.authorizationId") : null;
/* 111 */     String[] arrayOfString = getSaslMechanismNames(paramString3);
/*     */ 
/*     */     
/*     */     try {
/* 115 */       saslClient = Sasl.createSaslClient(arrayOfString, str, "ldap", paramString1, (Map)paramHashtable, callbackHandler);
/*     */ 
/*     */       
/* 118 */       if (saslClient == null) {
/* 119 */         throw new AuthenticationNotSupportedException(paramString3);
/*     */       }
/*     */ 
/*     */       
/* 123 */       String str1 = saslClient.getMechanismName();
/*     */       
/* 125 */       byte[] arrayOfByte = saslClient.hasInitialResponse() ? saslClient.evaluateChallenge(NO_BYTES) : null;
/*     */       
/* 127 */       LdapResult ldapResult = paramLdapClient.ldapBind(null, arrayOfByte, paramArrayOfControl, str1, true);
/*     */       
/* 129 */       while (!saslClient.isComplete() && (ldapResult.status == 14 || ldapResult.status == 0)) {
/*     */ 
/*     */ 
/*     */         
/* 133 */         arrayOfByte = saslClient.evaluateChallenge((ldapResult.serverCreds != null) ? ldapResult.serverCreds : NO_BYTES);
/*     */         
/* 135 */         if (ldapResult.status == 0) {
/* 136 */           if (arrayOfByte != null) {
/* 137 */             throw new AuthenticationException("SASL client generated response after success");
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/* 142 */         ldapResult = paramLdapClient.ldapBind(null, arrayOfByte, paramArrayOfControl, str1, true);
/*     */       } 
/*     */       
/* 145 */       if (ldapResult.status == 0) {
/* 146 */         if (!saslClient.isComplete()) {
/* 147 */           throw new AuthenticationException("SASL authentication not complete despite server claims");
/*     */         }
/*     */ 
/*     */         
/* 151 */         String str2 = (String)saslClient.getNegotiatedProperty("javax.security.sasl.qop");
/*     */ 
/*     */         
/* 154 */         if (str2 != null && (str2.equalsIgnoreCase("auth-int") || str2
/* 155 */           .equalsIgnoreCase("auth-conf"))) {
/*     */           
/* 157 */           SaslInputStream saslInputStream = new SaslInputStream(saslClient, paramConnection.inStream);
/*     */           
/* 159 */           SaslOutputStream saslOutputStream = new SaslOutputStream(saslClient, paramConnection.outStream);
/*     */ 
/*     */           
/* 162 */           paramConnection.replaceStreams(saslInputStream, saslOutputStream);
/*     */         } else {
/* 164 */           saslClient.dispose();
/*     */         } 
/*     */       } 
/* 167 */       return ldapResult;
/* 168 */     } catch (SaslException saslException) {
/* 169 */       AuthenticationException authenticationException = new AuthenticationException(paramString3);
/*     */       
/* 171 */       authenticationException.setRootCause(saslException);
/* 172 */       throw authenticationException;
/*     */     } finally {
/* 174 */       if (bool) {
/* 175 */         ((DefaultCallbackHandler)callbackHandler).clearPassword();
/*     */       }
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
/*     */   private static String[] getSaslMechanismNames(String paramString) {
/* 188 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString);
/* 189 */     Vector<String> vector = new Vector(10);
/* 190 */     while (stringTokenizer.hasMoreTokens()) {
/* 191 */       vector.addElement(stringTokenizer.nextToken());
/*     */     }
/* 193 */     String[] arrayOfString = new String[vector.size()];
/* 194 */     for (byte b = 0; b < vector.size(); b++) {
/* 195 */       arrayOfString[b] = vector.elementAt(b);
/*     */     }
/* 197 */     return arrayOfString;
/*     */   }
/*     */   
/* 200 */   private static final byte[] NO_BYTES = new byte[0];
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/sasl/LdapSasl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */