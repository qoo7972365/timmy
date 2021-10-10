/*     */ package com.sun.security.sasl.gsskerb;
/*     */ 
/*     */ import com.sun.security.sasl.util.AbstractSaslImpl;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.sasl.SaslException;
/*     */ import org.ietf.jgss.GSSContext;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import org.ietf.jgss.Oid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class GssKrb5Base
/*     */   extends AbstractSaslImpl
/*     */ {
/*     */   private static final String KRB5_OID_STR = "1.2.840.113554.1.2.2";
/*     */   protected static Oid KRB5_OID;
/*  39 */   protected static final byte[] EMPTY = new byte[0];
/*     */   
/*     */   static {
/*     */     try {
/*  43 */       KRB5_OID = new Oid("1.2.840.113554.1.2.2");
/*  44 */     } catch (GSSException gSSException) {}
/*     */   }
/*     */   
/*  47 */   protected GSSContext secCtx = null;
/*     */   
/*     */   protected static final int JGSS_QOP = 0;
/*     */   
/*     */   protected GssKrb5Base(Map<String, ?> paramMap, String paramString) throws SaslException {
/*  52 */     super(paramMap, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMechanismName() {
/*  61 */     return "GSSAPI";
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/*  66 */     if (!this.completed) {
/*  67 */       throw new IllegalStateException("GSSAPI authentication not completed");
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (!this.integrity) {
/*  72 */       throw new IllegalStateException("No security layer negotiated");
/*     */     }
/*     */     
/*     */     try {
/*  76 */       MessageProp messageProp = new MessageProp(0, false);
/*  77 */       byte[] arrayOfByte = this.secCtx.unwrap(paramArrayOfbyte, paramInt1, paramInt2, messageProp);
/*  78 */       if (this.privacy && !messageProp.getPrivacy()) {
/*  79 */         throw new SaslException("Privacy not protected");
/*     */       }
/*  81 */       checkMessageProp("", messageProp);
/*  82 */       if (logger.isLoggable(Level.FINEST)) {
/*  83 */         traceOutput(this.myClassName, "KRB501:Unwrap", "incoming: ", paramArrayOfbyte, paramInt1, paramInt2);
/*     */         
/*  85 */         traceOutput(this.myClassName, "KRB502:Unwrap", "unwrapped: ", arrayOfByte, 0, arrayOfByte.length);
/*     */       } 
/*     */       
/*  88 */       return arrayOfByte;
/*  89 */     } catch (GSSException gSSException) {
/*  90 */       throw new SaslException("Problems unwrapping SASL buffer", gSSException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/*  95 */     if (!this.completed) {
/*  96 */       throw new IllegalStateException("GSSAPI authentication not completed");
/*     */     }
/*     */ 
/*     */     
/* 100 */     if (!this.integrity) {
/* 101 */       throw new IllegalStateException("No security layer negotiated");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 106 */       MessageProp messageProp = new MessageProp(0, this.privacy);
/* 107 */       byte[] arrayOfByte = this.secCtx.wrap(paramArrayOfbyte, paramInt1, paramInt2, messageProp);
/* 108 */       if (logger.isLoggable(Level.FINEST)) {
/* 109 */         traceOutput(this.myClassName, "KRB503:Wrap", "outgoing: ", paramArrayOfbyte, paramInt1, paramInt2);
/*     */         
/* 111 */         traceOutput(this.myClassName, "KRB504:Wrap", "wrapped: ", arrayOfByte, 0, arrayOfByte.length);
/*     */       } 
/*     */       
/* 114 */       return arrayOfByte;
/*     */     }
/* 116 */     catch (GSSException gSSException) {
/* 117 */       throw new SaslException("Problem performing GSS wrap", gSSException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose() throws SaslException {
/* 122 */     if (this.secCtx != null) {
/*     */       try {
/* 124 */         this.secCtx.dispose();
/* 125 */       } catch (GSSException gSSException) {
/* 126 */         throw new SaslException("Problem disposing GSS context", gSSException);
/*     */       } 
/* 128 */       this.secCtx = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 133 */     dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   void checkMessageProp(String paramString, MessageProp paramMessageProp) throws SaslException {
/* 138 */     if (paramMessageProp.isDuplicateToken()) {
/* 139 */       throw new SaslException(paramString + "Duplicate token");
/*     */     }
/* 141 */     if (paramMessageProp.isGapToken()) {
/* 142 */       throw new SaslException(paramString + "Gap token");
/*     */     }
/* 144 */     if (paramMessageProp.isOldToken()) {
/* 145 */       throw new SaslException(paramString + "Old token");
/*     */     }
/* 147 */     if (paramMessageProp.isUnseqToken())
/* 148 */       throw new SaslException(paramString + "Token not in sequence"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/gsskerb/GssKrb5Base.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */