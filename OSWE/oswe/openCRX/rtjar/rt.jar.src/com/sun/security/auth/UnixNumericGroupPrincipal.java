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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class UnixNumericGroupPrincipal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3941535899328403223L;
/*     */   private String name;
/*     */   private boolean primaryGroup;
/*     */   
/*     */   public UnixNumericGroupPrincipal(String paramString, boolean paramBoolean) {
/*  78 */     if (paramString == null) {
/*     */ 
/*     */       
/*  81 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("invalid.null.input.value", "sun.security.util.AuthResources"));
/*     */       
/*  83 */       Object[] arrayOfObject = { "name" };
/*  84 */       throw new NullPointerException(messageFormat.format(arrayOfObject));
/*     */     } 
/*     */     
/*  87 */     this.name = paramString;
/*  88 */     this.primaryGroup = paramBoolean;
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
/*     */   public UnixNumericGroupPrincipal(long paramLong, boolean paramBoolean) {
/* 105 */     this.name = (new Long(paramLong)).toString();
/* 106 */     this.primaryGroup = paramBoolean;
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
/* 119 */     return this.name;
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
/* 132 */     return (new Long(this.name)).longValue();
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
/*     */   public boolean isPrimaryGroup() {
/* 146 */     return this.primaryGroup;
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
/*     */   public String toString() {
/* 160 */     if (this.primaryGroup) {
/*     */ 
/*     */       
/* 163 */       MessageFormat messageFormat1 = new MessageFormat(ResourcesMgr.getString("UnixNumericGroupPrincipal.Primary.Group.name", "sun.security.util.AuthResources"));
/*     */       
/* 165 */       Object[] arrayOfObject1 = { this.name };
/* 166 */       return messageFormat1.format(arrayOfObject1);
/*     */     } 
/*     */ 
/*     */     
/* 170 */     MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("UnixNumericGroupPrincipal.Supplementary.Group.name", "sun.security.util.AuthResources"));
/*     */     
/* 172 */     Object[] arrayOfObject = { this.name };
/* 173 */     return messageFormat.format(arrayOfObject);
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
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 194 */     if (paramObject == null) {
/* 195 */       return false;
/*     */     }
/* 197 */     if (this == paramObject) {
/* 198 */       return true;
/*     */     }
/* 200 */     if (!(paramObject instanceof UnixNumericGroupPrincipal))
/* 201 */       return false; 
/* 202 */     UnixNumericGroupPrincipal unixNumericGroupPrincipal = (UnixNumericGroupPrincipal)paramObject;
/*     */     
/* 204 */     if (getName().equals(unixNumericGroupPrincipal.getName()) && 
/* 205 */       isPrimaryGroup() == unixNumericGroupPrincipal.isPrimaryGroup())
/* 206 */       return true; 
/* 207 */     return false;
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
/* 218 */     return toString().hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/UnixNumericGroupPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */