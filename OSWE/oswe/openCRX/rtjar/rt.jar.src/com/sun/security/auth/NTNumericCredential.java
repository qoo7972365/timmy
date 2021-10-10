/*     */ package com.sun.security.auth;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import jdk.Exported;
/*     */ import sun.security.util.ResourcesMgr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class NTNumericCredential
/*     */ {
/*     */   private long impersonationToken;
/*     */   
/*     */   public NTNumericCredential(long paramLong) {
/*  48 */     this.impersonationToken = paramLong;
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
/*     */   public long getToken() {
/*  61 */     return this.impersonationToken;
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
/*     */   public String toString() {
/*  74 */     MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("NTNumericCredential.name", "sun.security.util.AuthResources"));
/*     */     
/*  76 */     Object[] arrayOfObject = { Long.toString(this.impersonationToken) };
/*  77 */     return messageFormat.format(arrayOfObject);
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
/*     */   public boolean equals(Object paramObject) {
/*  95 */     if (paramObject == null) {
/*  96 */       return false;
/*     */     }
/*  98 */     if (this == paramObject) {
/*  99 */       return true;
/*     */     }
/* 101 */     if (!(paramObject instanceof NTNumericCredential))
/* 102 */       return false; 
/* 103 */     NTNumericCredential nTNumericCredential = (NTNumericCredential)paramObject;
/*     */     
/* 105 */     if (this.impersonationToken == nTNumericCredential.getToken())
/* 106 */       return true; 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 118 */     return (int)this.impersonationToken;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/NTNumericCredential.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */