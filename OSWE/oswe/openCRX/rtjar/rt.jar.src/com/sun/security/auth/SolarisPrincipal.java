/*     */ package com.sun.security.auth;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ResourceBundle;
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
/*     */ @Exported(false)
/*     */ @Deprecated
/*     */ public class SolarisPrincipal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7840670002439379038L;
/*     */   
/*  55 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>() {
/*     */         public ResourceBundle run() {
/*  57 */           return 
/*  58 */             ResourceBundle.getBundle("sun.security.util.AuthResources");
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolarisPrincipal(String paramString) {
/*  79 */     if (paramString == null) {
/*  80 */       throw new NullPointerException(rb.getString("provided.null.name"));
/*     */     }
/*  82 */     this.name = paramString;
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
/*  93 */     return this.name;
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
/* 104 */     return rb.getString("SolarisPrincipal.") + this.name;
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
/* 122 */     if (paramObject == null) {
/* 123 */       return false;
/*     */     }
/* 125 */     if (this == paramObject) {
/* 126 */       return true;
/*     */     }
/* 128 */     if (!(paramObject instanceof SolarisPrincipal))
/* 129 */       return false; 
/* 130 */     SolarisPrincipal solarisPrincipal = (SolarisPrincipal)paramObject;
/*     */     
/* 132 */     if (getName().equals(solarisPrincipal.getName()))
/* 133 */       return true; 
/* 134 */     return false;
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
/* 145 */     return this.name.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/SolarisPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */