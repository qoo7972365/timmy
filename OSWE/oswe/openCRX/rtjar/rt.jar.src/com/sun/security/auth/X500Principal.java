/*     */ package com.sun.security.auth;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.NotActiveException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ResourceBundle;
/*     */ import jdk.Exported;
/*     */ import sun.security.x509.X500Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class X500Principal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8222422609431628648L;
/*     */   
/*  60 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>() {
/*     */         public ResourceBundle run() {
/*  62 */           return 
/*  63 */             ResourceBundle.getBundle("sun.security.util.AuthResources");
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
/*     */   private transient X500Name thisX500Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X500Principal(String paramString) {
/*  90 */     if (paramString == null) {
/*  91 */       throw new NullPointerException(rb.getString("provided.null.name"));
/*     */     }
/*     */     try {
/*  94 */       this.thisX500Name = new X500Name(paramString);
/*  95 */     } catch (Exception exception) {
/*  96 */       throw new IllegalArgumentException(exception.toString());
/*     */     } 
/*     */     
/*  99 */     this.name = paramString;
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
/* 110 */     return this.thisX500Name.getName();
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
/* 121 */     return this.thisX500Name.toString();
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
/*     */   public boolean equals(Object paramObject) {
/* 137 */     if (paramObject == null) {
/* 138 */       return false;
/*     */     }
/* 140 */     if (this == paramObject) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (paramObject instanceof X500Principal) {
/* 144 */       X500Principal x500Principal = (X500Principal)paramObject;
/*     */       try {
/* 146 */         X500Name x500Name = new X500Name(x500Principal.getName());
/* 147 */         return this.thisX500Name.equals(x500Name);
/* 148 */       } catch (Exception exception) {
/*     */         
/* 150 */         return false;
/*     */       } 
/* 152 */     }  if (paramObject instanceof Principal)
/*     */     {
/*     */       
/* 155 */       return paramObject.equals(this.thisX500Name);
/*     */     }
/*     */     
/* 158 */     return false;
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
/* 169 */     return this.thisX500Name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, NotActiveException, ClassNotFoundException {
/* 180 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 183 */     this.thisX500Name = new X500Name(this.name);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/X500Principal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */