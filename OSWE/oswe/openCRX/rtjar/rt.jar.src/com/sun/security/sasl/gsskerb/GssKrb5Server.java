/*     */ package com.sun.security.sasl.gsskerb;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.sasl.AuthorizeCallback;
/*     */ import javax.security.sasl.SaslException;
/*     */ import javax.security.sasl.SaslServer;
/*     */ import org.ietf.jgss.GSSCredential;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSManager;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class GssKrb5Server
/*     */   extends GssKrb5Base
/*     */   implements SaslServer
/*     */ {
/*  66 */   private static final String MY_CLASS_NAME = GssKrb5Server.class.getName();
/*     */ 
/*     */   
/*     */   private int handshakeStage;
/*     */ 
/*     */   
/*     */   private String peer;
/*     */ 
/*     */   
/*     */   private String me;
/*     */ 
/*     */   
/*     */   private String authzid;
/*     */   
/*     */   private CallbackHandler cbh;
/*     */   
/*     */   private final String protocolSaved;
/*     */ 
/*     */   
/*     */   GssKrb5Server(String paramString1, String paramString2, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/*  86 */     super(paramMap, MY_CLASS_NAME); String str;
/*     */     this.handshakeStage = 0;
/*  88 */     this.cbh = paramCallbackHandler;
/*     */ 
/*     */     
/*  91 */     if (paramString2 == null) {
/*  92 */       this.protocolSaved = paramString1;
/*  93 */       str = null;
/*     */     } else {
/*  95 */       this.protocolSaved = null;
/*  96 */       str = paramString1 + "@" + paramString2;
/*     */     } 
/*     */     
/*  99 */     logger.log(Level.FINE, "KRB5SRV01:Using service name: {0}", str);
/*     */     
/*     */     try {
/* 102 */       GSSManager gSSManager = GSSManager.getInstance();
/*     */ 
/*     */ 
/*     */       
/* 106 */       GSSName gSSName = (str == null) ? null : gSSManager.createName(str, GSSName.NT_HOSTBASED_SERVICE, KRB5_OID);
/*     */       
/* 108 */       GSSCredential gSSCredential = gSSManager.createCredential(gSSName, 2147483647, KRB5_OID, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       this.secCtx = gSSManager.createContext(gSSCredential);
/*     */       
/* 115 */       if ((this.allQop & 0x2) != 0)
/*     */       {
/* 117 */         this.secCtx.requestInteg(true);
/*     */       }
/*     */       
/* 120 */       if ((this.allQop & 0x4) != 0)
/*     */       {
/* 122 */         this.secCtx.requestConf(true);
/*     */       }
/* 124 */     } catch (GSSException gSSException) {
/* 125 */       throw new SaslException("Failure to initialize security context", gSSException);
/*     */     } 
/* 127 */     logger.log(Level.FINE, "KRB5SRV02:Initialization complete");
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
/*     */   public byte[] evaluateResponse(byte[] paramArrayOfbyte) throws SaslException {
/* 146 */     if (this.completed) {
/* 147 */       throw new SaslException("SASL authentication already complete");
/*     */     }
/*     */ 
/*     */     
/* 151 */     if (logger.isLoggable(Level.FINER)) {
/* 152 */       traceOutput(MY_CLASS_NAME, "evaluateResponse", "KRB5SRV03:Response [raw]:", paramArrayOfbyte);
/*     */     }
/*     */ 
/*     */     
/* 156 */     switch (this.handshakeStage) {
/*     */       case 1:
/* 158 */         return doHandshake1(paramArrayOfbyte);
/*     */       
/*     */       case 2:
/* 161 */         return doHandshake2(paramArrayOfbyte);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 167 */       byte[] arrayOfByte = this.secCtx.acceptSecContext(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */ 
/*     */       
/* 170 */       if (logger.isLoggable(Level.FINER)) {
/* 171 */         traceOutput(MY_CLASS_NAME, "evaluateResponse", "KRB5SRV04:Challenge: [after acceptSecCtx]", arrayOfByte);
/*     */       }
/*     */ 
/*     */       
/* 175 */       if (this.secCtx.isEstablished()) {
/* 176 */         this.handshakeStage = 1;
/*     */         
/* 178 */         this.peer = this.secCtx.getSrcName().toString();
/* 179 */         this.me = this.secCtx.getTargName().toString();
/*     */         
/* 181 */         logger.log(Level.FINE, "KRB5SRV05:Peer name is : {0}, my name is : {1}", new Object[] { this.peer, this.me });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 186 */         if (this.protocolSaved != null && 
/* 187 */           !this.protocolSaved.equalsIgnoreCase(this.me.split("[/@]")[0])) {
/* 188 */           throw new SaslException("GSS context targ name protocol error: " + this.me);
/*     */         }
/*     */ 
/*     */         
/* 192 */         if (arrayOfByte == null) {
/* 193 */           return doHandshake1(EMPTY);
/*     */         }
/*     */       } 
/*     */       
/* 197 */       return arrayOfByte;
/* 198 */     } catch (GSSException gSSException) {
/* 199 */       throw new SaslException("GSS initiate failed", gSSException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] doHandshake1(byte[] paramArrayOfbyte) throws SaslException {
/*     */     try {
/* 208 */       if (paramArrayOfbyte != null && paramArrayOfbyte.length > 0) {
/* 209 */         throw new SaslException("Handshake expecting no response data from server");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       byte[] arrayOfByte1 = new byte[4];
/* 218 */       arrayOfByte1[0] = this.allQop;
/* 219 */       intToNetworkByteOrder(this.recvMaxBufSize, arrayOfByte1, 1, 3);
/*     */       
/* 221 */       if (logger.isLoggable(Level.FINE)) {
/* 222 */         logger.log(Level.FINE, "KRB5SRV06:Supported protections: {0}; recv max buf size: {1}", new Object[] { new Byte(this.allQop), new Integer(this.recvMaxBufSize) });
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       this.handshakeStage = 2;
/*     */       
/* 230 */       if (logger.isLoggable(Level.FINER)) {
/* 231 */         traceOutput(MY_CLASS_NAME, "doHandshake1", "KRB5SRV07:Challenge [raw]", arrayOfByte1);
/*     */       }
/*     */ 
/*     */       
/* 235 */       byte[] arrayOfByte2 = this.secCtx.wrap(arrayOfByte1, 0, arrayOfByte1.length, new MessageProp(0, false));
/*     */ 
/*     */       
/* 238 */       if (logger.isLoggable(Level.FINER)) {
/* 239 */         traceOutput(MY_CLASS_NAME, "doHandshake1", "KRB5SRV08:Challenge [after wrap]", arrayOfByte2);
/*     */       }
/*     */       
/* 242 */       return arrayOfByte2;
/*     */     }
/* 244 */     catch (GSSException gSSException) {
/* 245 */       throw new SaslException("Problem wrapping handshake1", gSSException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] doHandshake2(byte[] paramArrayOfbyte) throws SaslException {
/*     */     try {
/* 253 */       MessageProp messageProp = new MessageProp(false);
/* 254 */       byte[] arrayOfByte = this.secCtx.unwrap(paramArrayOfbyte, 0, paramArrayOfbyte.length, messageProp);
/*     */       
/* 256 */       checkMessageProp("Handshake failure: ", messageProp);
/*     */       
/* 258 */       if (logger.isLoggable(Level.FINER)) {
/* 259 */         traceOutput(MY_CLASS_NAME, "doHandshake2", "KRB5SRV09:Response [after unwrap]", arrayOfByte);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 264 */       byte b = arrayOfByte[0];
/* 265 */       if ((b & this.allQop) == 0) {
/* 266 */         throw new SaslException("Client selected unsupported protection: " + b);
/*     */       }
/*     */       
/* 269 */       if ((b & 0x4) != 0) {
/* 270 */         this.privacy = true;
/* 271 */         this.integrity = true;
/* 272 */       } else if ((b & 0x2) != 0) {
/* 273 */         this.integrity = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 279 */       int i = networkByteOrderToInt(arrayOfByte, 1, 3);
/*     */ 
/*     */ 
/*     */       
/* 283 */       this
/* 284 */         .sendMaxBufSize = (this.sendMaxBufSize == 0) ? i : Math.min(this.sendMaxBufSize, i);
/*     */ 
/*     */       
/* 287 */       this.rawSendSize = this.secCtx.getWrapSizeLimit(0, this.privacy, this.sendMaxBufSize);
/*     */ 
/*     */       
/* 290 */       if (logger.isLoggable(Level.FINE)) {
/* 291 */         logger.log(Level.FINE, "KRB5SRV10:Selected protection: {0}; privacy: {1}; integrity: {2}", new Object[] { new Byte(b), 
/*     */ 
/*     */               
/* 294 */               Boolean.valueOf(this.privacy), 
/* 295 */               Boolean.valueOf(this.integrity) });
/* 296 */         logger.log(Level.FINE, "KRB5SRV11:Client max recv size: {0}; server max send size: {1}; rawSendSize: {2}", new Object[] { new Integer(i), new Integer(this.sendMaxBufSize), new Integer(this.rawSendSize) });
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 304 */       if (arrayOfByte.length > 4) {
/*     */         try {
/* 306 */           this.authzid = new String(arrayOfByte, 4, arrayOfByte.length - 4, "UTF-8");
/*     */         }
/* 308 */         catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 309 */           throw new SaslException("Cannot decode authzid", unsupportedEncodingException);
/*     */         } 
/*     */       } else {
/* 312 */         this.authzid = this.peer;
/*     */       } 
/* 314 */       logger.log(Level.FINE, "KRB5SRV12:Authzid: {0}", this.authzid);
/*     */       
/* 316 */       AuthorizeCallback authorizeCallback = new AuthorizeCallback(this.peer, this.authzid);
/*     */ 
/*     */       
/* 319 */       this.cbh.handle(new Callback[] { authorizeCallback });
/* 320 */       if (authorizeCallback.isAuthorized()) {
/* 321 */         this.authzid = authorizeCallback.getAuthorizedID();
/* 322 */         this.completed = true;
/*     */       } else {
/*     */         
/* 325 */         throw new SaslException(this.peer + " is not authorized to connect as " + this.authzid);
/*     */       } 
/*     */ 
/*     */       
/* 329 */       return null;
/* 330 */     } catch (GSSException gSSException) {
/* 331 */       throw new SaslException("Final handshake step failed", gSSException);
/* 332 */     } catch (IOException iOException) {
/* 333 */       throw new SaslException("Problem with callback handler", iOException);
/* 334 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 335 */       throw new SaslException("Problem with callback handler", unsupportedCallbackException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getAuthorizationID() {
/* 340 */     if (this.completed) {
/* 341 */       return this.authzid;
/*     */     }
/* 343 */     throw new IllegalStateException("Authentication incomplete");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getNegotiatedProperty(String paramString) {
/* 348 */     if (!this.completed) {
/* 349 */       throw new IllegalStateException("Authentication incomplete");
/*     */     }
/*     */ 
/*     */     
/* 353 */     switch (paramString)
/*     */     
/*     */     { case "javax.security.sasl.bound.server.name":
/*     */         try {
/* 357 */           object = this.me.split("[/@]")[1];
/* 358 */         } catch (Exception exception) {
/* 359 */           object = null;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 365 */         return object; }  Object object = super.getNegotiatedProperty(paramString); return object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/gsskerb/GssKrb5Server.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */