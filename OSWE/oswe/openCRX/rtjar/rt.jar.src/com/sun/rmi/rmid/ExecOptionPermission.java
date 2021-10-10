/*     */ package com.sun.rmi.rmid;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExecOptionPermission
/*     */   extends Permission
/*     */ {
/*     */   private transient boolean wildcard;
/*     */   private transient String name;
/*     */   private static final long serialVersionUID = 5842294756823092756L;
/*     */   
/*     */   public ExecOptionPermission(String paramString) {
/*  59 */     super(paramString);
/*  60 */     init(paramString);
/*     */   }
/*     */   
/*     */   public ExecOptionPermission(String paramString1, String paramString2) {
/*  64 */     this(paramString1);
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
/*     */   public boolean implies(Permission paramPermission) {
/*  86 */     if (!(paramPermission instanceof ExecOptionPermission)) {
/*  87 */       return false;
/*     */     }
/*  89 */     ExecOptionPermission execOptionPermission = (ExecOptionPermission)paramPermission;
/*     */     
/*  91 */     if (this.wildcard) {
/*  92 */       if (execOptionPermission.wildcard)
/*     */       {
/*  94 */         return execOptionPermission.name.startsWith(this.name);
/*     */       }
/*     */       
/*  97 */       return (execOptionPermission.name.length() > this.name.length() && execOptionPermission.name
/*  98 */         .startsWith(this.name));
/*     */     } 
/*     */     
/* 101 */     if (execOptionPermission.wildcard)
/*     */     {
/* 103 */       return false;
/*     */     }
/* 105 */     return this.name.equals(execOptionPermission.name);
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
/*     */   public boolean equals(Object paramObject) {
/* 120 */     if (paramObject == this) {
/* 121 */       return true;
/*     */     }
/* 123 */     if (paramObject == null || paramObject.getClass() != getClass()) {
/* 124 */       return false;
/*     */     }
/* 126 */     ExecOptionPermission execOptionPermission = (ExecOptionPermission)paramObject;
/*     */     
/* 128 */     return getName().equals(execOptionPermission.getName());
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
/*     */   public int hashCode() {
/* 141 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActions() {
/* 150 */     return "";
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
/* 169 */     return new ExecOptionPermissionCollection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 179 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 181 */     init(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String paramString) {
/* 190 */     if (paramString == null) {
/* 191 */       throw new NullPointerException("name can't be null");
/*     */     }
/* 193 */     if (paramString.equals("")) {
/* 194 */       throw new IllegalArgumentException("name can't be empty");
/*     */     }
/*     */     
/* 197 */     if (paramString.endsWith(".*") || paramString.endsWith("=*") || paramString.equals("*")) {
/* 198 */       this.wildcard = true;
/* 199 */       if (paramString.length() == 1) {
/* 200 */         this.name = "";
/*     */       } else {
/* 202 */         this.name = paramString.substring(0, paramString.length() - 1);
/*     */       } 
/*     */     } else {
/* 205 */       this.name = paramString;
/*     */     } 
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
/*     */   
/*     */   private static class ExecOptionPermissionCollection
/*     */     extends PermissionCollection
/*     */     implements Serializable
/*     */   {
/* 234 */     private Hashtable<String, Permission> permissions = new Hashtable<>(11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean all_allowed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = -1242475729790124375L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(Permission param1Permission) {
/* 253 */       if (!(param1Permission instanceof ExecOptionPermission)) {
/* 254 */         throw new IllegalArgumentException("invalid permission: " + param1Permission);
/*     */       }
/* 256 */       if (isReadOnly()) {
/* 257 */         throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
/*     */       }
/* 259 */       ExecOptionPermission execOptionPermission = (ExecOptionPermission)param1Permission;
/*     */       
/* 261 */       this.permissions.put(execOptionPermission.getName(), param1Permission);
/* 262 */       if (!this.all_allowed && 
/* 263 */         execOptionPermission.getName().equals("*")) {
/* 264 */         this.all_allowed = true;
/*     */       }
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
/* 279 */       if (!(param1Permission instanceof ExecOptionPermission)) {
/* 280 */         return false;
/*     */       }
/* 282 */       ExecOptionPermission execOptionPermission = (ExecOptionPermission)param1Permission;
/*     */ 
/*     */       
/* 285 */       if (this.all_allowed) {
/* 286 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       String str = execOptionPermission.getName();
/*     */       
/* 294 */       Permission permission = this.permissions.get(str);
/*     */       
/* 296 */       if (permission != null)
/*     */       {
/* 298 */         return permission.implies(param1Permission);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 304 */       int j = str.length() - 1;
/*     */       int i;
/* 306 */       while ((i = str.lastIndexOf(".", j)) != -1) {
/*     */         
/* 308 */         str = str.substring(0, i + 1) + "*";
/* 309 */         permission = this.permissions.get(str);
/*     */         
/* 311 */         if (permission != null) {
/* 312 */           return permission.implies(param1Permission);
/*     */         }
/* 314 */         j = i - 1;
/*     */       } 
/*     */ 
/*     */       
/* 318 */       str = execOptionPermission.getName();
/* 319 */       j = str.length() - 1;
/*     */       
/* 321 */       while ((i = str.lastIndexOf("=", j)) != -1) {
/*     */         
/* 323 */         str = str.substring(0, i + 1) + "*";
/* 324 */         permission = this.permissions.get(str);
/*     */         
/* 326 */         if (permission != null) {
/* 327 */           return permission.implies(param1Permission);
/*     */         }
/* 329 */         j = i - 1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 334 */       return false;
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
/*     */     public Enumeration<Permission> elements() {
/* 346 */       return this.permissions.elements();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rmi/rmid/ExecOptionPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */