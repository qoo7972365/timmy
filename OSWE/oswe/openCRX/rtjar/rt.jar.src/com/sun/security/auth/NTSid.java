/*     */ package com.sun.security.auth;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.Principal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class NTSid
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4412290580770249885L;
/*     */   private String sid;
/*     */   
/*     */   public NTSid(String paramString) {
/*  74 */     if (paramString == null) {
/*     */ 
/*     */       
/*  77 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("invalid.null.input.value", "sun.security.util.AuthResources"));
/*     */       
/*  79 */       Object[] arrayOfObject = { "stringSid" };
/*  80 */       throw new NullPointerException(messageFormat.format(arrayOfObject));
/*     */     } 
/*  82 */     if (paramString.length() == 0) {
/*  83 */       throw new IllegalArgumentException(
/*     */           
/*  85 */           ResourcesMgr.getString("Invalid.NTSid.value", "sun.security.util.AuthResources"));
/*     */     }
/*     */     
/*  88 */     this.sid = new String(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  99 */     return this.sid;
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
/* 112 */     MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("NTSid.name", "sun.security.util.AuthResources"));
/*     */     
/* 114 */     Object[] arrayOfObject = { this.sid };
/* 115 */     return messageFormat.format(arrayOfObject);
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
/* 133 */     if (paramObject == null) {
/* 134 */       return false;
/*     */     }
/* 136 */     if (this == paramObject) {
/* 137 */       return true;
/*     */     }
/* 139 */     if (!(paramObject instanceof NTSid))
/* 140 */       return false; 
/* 141 */     NTSid nTSid = (NTSid)paramObject;
/*     */     
/* 143 */     if (this.sid.equals(nTSid.sid)) {
/* 144 */       return true;
/*     */     }
/* 146 */     return false;
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
/* 157 */     return this.sid.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/NTSid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */