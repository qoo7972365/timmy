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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported(false)
/*     */ @Deprecated
/*     */ public class SolarisNumericGroupPrincipal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2345199581042573224L;
/*     */   
/*  58 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>() {
/*     */         public ResourceBundle run() {
/*  60 */           return 
/*  61 */             ResourceBundle.getBundle("sun.security.util.AuthResources");
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
/*     */   private boolean primaryGroup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolarisNumericGroupPrincipal(String paramString, boolean paramBoolean) {
/*  92 */     if (paramString == null) {
/*  93 */       throw new NullPointerException(rb.getString("provided.null.name"));
/*     */     }
/*  95 */     this.name = paramString;
/*  96 */     this.primaryGroup = paramBoolean;
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
/*     */   public SolarisNumericGroupPrincipal(long paramLong, boolean paramBoolean) {
/* 113 */     this.name = (new Long(paramLong)).toString();
/* 114 */     this.primaryGroup = paramBoolean;
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
/* 127 */     return this.name;
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
/* 140 */     return (new Long(this.name)).longValue();
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
/* 154 */     return this.primaryGroup;
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
/* 167 */     return this.primaryGroup ? (rb
/*     */       
/* 169 */       .getString("SolarisNumericGroupPrincipal.Primary.Group.") + this.name) : (rb
/*     */       
/* 171 */       .getString("SolarisNumericGroupPrincipal.Supplementary.Group.") + this.name);
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
/* 191 */     if (paramObject == null) {
/* 192 */       return false;
/*     */     }
/* 194 */     if (this == paramObject) {
/* 195 */       return true;
/*     */     }
/* 197 */     if (!(paramObject instanceof SolarisNumericGroupPrincipal))
/* 198 */       return false; 
/* 199 */     SolarisNumericGroupPrincipal solarisNumericGroupPrincipal = (SolarisNumericGroupPrincipal)paramObject;
/*     */     
/* 201 */     if (getName().equals(solarisNumericGroupPrincipal.getName()) && 
/* 202 */       isPrimaryGroup() == solarisNumericGroupPrincipal.isPrimaryGroup())
/* 203 */       return true; 
/* 204 */     return false;
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
/* 215 */     return toString().hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/SolarisNumericGroupPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */