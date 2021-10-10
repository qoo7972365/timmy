/*     */ package com.sun.security.auth;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.Principal;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.ldap.LdapName;
/*     */ import jdk.Exported;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class LdapPrincipal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6820120005580754861L;
/*     */   private final String nameString;
/*     */   private final LdapName name;
/*     */   
/*     */   public LdapPrincipal(String paramString) throws InvalidNameException {
/*  76 */     if (paramString == null) {
/*  77 */       throw new NullPointerException("null name is illegal");
/*     */     }
/*  79 */     this.name = getLdapName(paramString);
/*  80 */     this.nameString = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  90 */     if (this == paramObject) {
/*  91 */       return true;
/*     */     }
/*  93 */     if (paramObject instanceof LdapPrincipal) {
/*     */       
/*     */       try {
/*  96 */         return this.name
/*  97 */           .equals(getLdapName(((LdapPrincipal)paramObject).getName()));
/*     */       }
/*  99 */       catch (InvalidNameException invalidNameException) {
/* 100 */         return false;
/*     */       } 
/*     */     }
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 112 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 121 */     return this.nameString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 132 */     return this.name.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private LdapName getLdapName(String paramString) throws InvalidNameException {
/* 137 */     return new LdapName(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/LdapPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */