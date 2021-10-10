/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.ldap.Control;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DigestClientId
/*     */   extends SimpleClientId
/*     */ {
/*  45 */   private static final String[] SASL_PROPS = new String[] { "java.naming.security.sasl.authorizationId", "java.naming.security.sasl.realm", "javax.security.sasl.qop", "javax.security.sasl.strength", "javax.security.sasl.reuse", "javax.security.sasl.server.authentication", "javax.security.sasl.maxbuffer", "javax.security.sasl.policy.noplaintext", "javax.security.sasl.policy.noactive", "javax.security.sasl.policy.nodictionary", "javax.security.sasl.policy.noanonymous", "javax.security.sasl.policy.forward", "javax.security.sasl.policy.credentials" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] propvals;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int myHash;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DigestClientId(int paramInt1, String paramString1, int paramInt2, String paramString2, Control[] paramArrayOfControl, OutputStream paramOutputStream, String paramString3, String paramString4, Object paramObject, Hashtable<?, ?> paramHashtable) {
/*  69 */     super(paramInt1, paramString1, paramInt2, paramString2, paramArrayOfControl, paramOutputStream, paramString3, paramString4, paramObject);
/*     */ 
/*     */     
/*  72 */     if (paramHashtable == null) {
/*  73 */       this.propvals = null;
/*     */     }
/*     */     else {
/*     */       
/*  77 */       this.propvals = new String[SASL_PROPS.length];
/*  78 */       for (byte b = 0; b < SASL_PROPS.length; b++) {
/*  79 */         this.propvals[b] = (String)paramHashtable.get(SASL_PROPS[b]);
/*     */       }
/*     */     } 
/*  82 */     this.myHash = super.hashCode() ^ Arrays.hashCode((Object[])this.propvals);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  86 */     if (!(paramObject instanceof DigestClientId)) {
/*  87 */       return false;
/*     */     }
/*  89 */     DigestClientId digestClientId = (DigestClientId)paramObject;
/*  90 */     return (this.myHash == digestClientId.myHash && super
/*  91 */       .equals(paramObject) && 
/*  92 */       Arrays.equals((Object[])this.propvals, (Object[])digestClientId.propvals));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  96 */     return this.myHash;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 100 */     if (this.propvals != null) {
/* 101 */       StringBuffer stringBuffer = new StringBuffer();
/* 102 */       for (byte b = 0; b < this.propvals.length; b++) {
/* 103 */         stringBuffer.append(':');
/* 104 */         if (this.propvals[b] != null) {
/* 105 */           stringBuffer.append(this.propvals[b]);
/*     */         }
/*     */       } 
/* 108 */       return super.toString() + stringBuffer.toString();
/*     */     } 
/* 110 */     return super.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/DigestClientId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */