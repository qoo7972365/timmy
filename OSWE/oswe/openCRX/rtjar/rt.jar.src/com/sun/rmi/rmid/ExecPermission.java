/*     */ package com.sun.rmi.rmid;
/*     */ 
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExecPermission
/*     */   extends Permission
/*     */ {
/*     */   private static final long serialVersionUID = -6208470287358147919L;
/*     */   private transient FilePermission fp;
/*     */   
/*     */   public ExecPermission(String paramString) {
/*  86 */     super(paramString);
/*  87 */     init(paramString);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ExecPermission(String paramString1, String paramString2) {
/* 111 */     this(paramString1);
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
/*     */   public boolean implies(Permission paramPermission) {
/* 131 */     if (!(paramPermission instanceof ExecPermission)) {
/* 132 */       return false;
/*     */     }
/* 134 */     ExecPermission execPermission = (ExecPermission)paramPermission;
/*     */     
/* 136 */     return this.fp.implies(execPermission.fp);
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
/*     */   public boolean equals(Object paramObject) {
/* 149 */     if (paramObject == this) {
/* 150 */       return true;
/*     */     }
/* 152 */     if (!(paramObject instanceof ExecPermission)) {
/* 153 */       return false;
/*     */     }
/* 155 */     ExecPermission execPermission = (ExecPermission)paramObject;
/*     */     
/* 157 */     return this.fp.equals(execPermission.fp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 166 */     return this.fp.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActions() {
/* 175 */     return "";
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 194 */     return new ExecPermissionCollection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 204 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 206 */     init(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String paramString) {
/* 214 */     this.fp = new FilePermission(paramString, "execute");
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
/*     */   private static class ExecPermissionCollection
/*     */     extends PermissionCollection
/*     */     implements Serializable
/*     */   {
/* 238 */     private Vector<Permission> permissions = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = -3352558508888368273L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(Permission param1Permission) {
/* 254 */       if (!(param1Permission instanceof ExecPermission)) {
/* 255 */         throw new IllegalArgumentException("invalid permission: " + param1Permission);
/*     */       }
/* 257 */       if (isReadOnly()) {
/* 258 */         throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
/*     */       }
/* 260 */       this.permissions.addElement(param1Permission);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean implies(Permission param1Permission) {
/* 274 */       if (!(param1Permission instanceof ExecPermission)) {
/* 275 */         return false;
/*     */       }
/* 277 */       Enumeration<Permission> enumeration = this.permissions.elements();
/*     */       
/* 279 */       while (enumeration.hasMoreElements()) {
/* 280 */         ExecPermission execPermission = (ExecPermission)enumeration.nextElement();
/* 281 */         if (execPermission.implies(param1Permission)) {
/* 282 */           return true;
/*     */         }
/*     */       } 
/* 285 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Enumeration<Permission> elements() {
/* 296 */       return this.permissions.elements();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rmi/rmid/ExecPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */