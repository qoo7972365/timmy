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
/*     */ @Exported
/*     */ public class UnixNumericUserPrincipal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4329764253802397821L;
/*     */   private String name;
/*     */   
/*     */   public UnixNumericUserPrincipal(String paramString) {
/*  68 */     if (paramString == null) {
/*     */ 
/*     */       
/*  71 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("invalid.null.input.value", "sun.security.util.AuthResources"));
/*     */       
/*  73 */       Object[] arrayOfObject = { "name" };
/*  74 */       throw new NullPointerException(messageFormat.format(arrayOfObject));
/*     */     } 
/*     */     
/*  77 */     this.name = paramString;
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
/*     */   public UnixNumericUserPrincipal(long paramLong) {
/*  90 */     this.name = (new Long(paramLong)).toString();
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
/*     */   public String getName() {
/* 103 */     return this.name;
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
/*     */   public long longValue() {
/* 116 */     return (new Long(this.name)).longValue();
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
/*     */   public String toString() {
/* 131 */     MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("UnixNumericUserPrincipal.name", "sun.security.util.AuthResources"));
/*     */     
/* 133 */     Object[] arrayOfObject = { this.name };
/* 134 */     return messageFormat.format(arrayOfObject);
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
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 154 */     if (paramObject == null) {
/* 155 */       return false;
/*     */     }
/* 157 */     if (this == paramObject) {
/* 158 */       return true;
/*     */     }
/* 160 */     if (!(paramObject instanceof UnixNumericUserPrincipal))
/* 161 */       return false; 
/* 162 */     UnixNumericUserPrincipal unixNumericUserPrincipal = (UnixNumericUserPrincipal)paramObject;
/*     */     
/* 164 */     if (getName().equals(unixNumericUserPrincipal.getName()))
/* 165 */       return true; 
/* 166 */     return false;
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
/* 177 */     return this.name.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/UnixNumericUserPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */