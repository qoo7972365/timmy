/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Permissions
/*     */   extends PermissionCollection
/*     */   implements Serializable
/*     */ {
/*     */   private transient Map<Class<?>, PermissionCollection> permsMap;
/*     */   private transient boolean hasUnresolved = false;
/*     */   PermissionCollection allPermission;
/*     */   private static final long serialVersionUID = 4858622370623524688L;
/*     */   
/*     */   public Permissions() {
/* 102 */     this.permsMap = new HashMap<>(11);
/* 103 */     this.allPermission = null;
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
/*     */   public void add(Permission paramPermission) {
/*     */     PermissionCollection permissionCollection;
/* 125 */     if (isReadOnly()) {
/* 126 */       throw new SecurityException("attempt to add a Permission to a readonly Permissions object");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 131 */     synchronized (this) {
/* 132 */       permissionCollection = getPermissionCollection(paramPermission, true);
/* 133 */       permissionCollection.add(paramPermission);
/*     */     } 
/*     */ 
/*     */     
/* 137 */     if (paramPermission instanceof AllPermission) {
/* 138 */       this.allPermission = permissionCollection;
/*     */     }
/* 140 */     if (paramPermission instanceof UnresolvedPermission) {
/* 141 */       this.hasUnresolved = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean implies(Permission paramPermission) {
/* 175 */     if (this.allPermission != null) {
/* 176 */       return true;
/*     */     }
/* 178 */     synchronized (this) {
/* 179 */       PermissionCollection permissionCollection = getPermissionCollection(paramPermission, false);
/*     */       
/* 181 */       if (permissionCollection != null) {
/* 182 */         return permissionCollection.implies(paramPermission);
/*     */       }
/*     */       
/* 185 */       return false;
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
/*     */   public Enumeration<Permission> elements() {
/* 202 */     synchronized (this) {
/* 203 */       return new PermissionsEnumerator(this.permsMap.values().iterator());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PermissionCollection getPermissionCollection(Permission paramPermission, boolean paramBoolean) {
/* 240 */     Class<?> clazz = paramPermission.getClass();
/*     */     
/* 242 */     PermissionCollection permissionCollection = this.permsMap.get(clazz);
/*     */     
/* 244 */     if (!this.hasUnresolved && !paramBoolean)
/* 245 */       return permissionCollection; 
/* 246 */     if (permissionCollection == null) {
/*     */ 
/*     */       
/* 249 */       permissionCollection = this.hasUnresolved ? getUnresolvedPermissions(paramPermission) : null;
/*     */ 
/*     */       
/* 252 */       if (permissionCollection == null && paramBoolean) {
/*     */         
/* 254 */         permissionCollection = paramPermission.newPermissionCollection();
/*     */ 
/*     */ 
/*     */         
/* 258 */         if (permissionCollection == null) {
/* 259 */           permissionCollection = new PermissionsHash();
/*     */         }
/*     */       } 
/* 262 */       if (permissionCollection != null) {
/* 263 */         this.permsMap.put(clazz, permissionCollection);
/*     */       }
/*     */     } 
/* 266 */     return permissionCollection;
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
/*     */   private PermissionCollection getUnresolvedPermissions(Permission paramPermission) {
/* 283 */     UnresolvedPermissionCollection unresolvedPermissionCollection = (UnresolvedPermissionCollection)this.permsMap.get(UnresolvedPermission.class);
/*     */ 
/*     */     
/* 286 */     if (unresolvedPermissionCollection == null) {
/* 287 */       return null;
/*     */     }
/*     */     
/* 290 */     List<UnresolvedPermission> list = unresolvedPermissionCollection.getUnresolvedPermissions(paramPermission);
/*     */ 
/*     */     
/* 293 */     if (list == null) {
/* 294 */       return null;
/*     */     }
/* 296 */     Certificate[] arrayOfCertificate = null;
/*     */     
/* 298 */     Object[] arrayOfObject = paramPermission.getClass().getSigners();
/*     */     
/* 300 */     byte b = 0;
/* 301 */     if (arrayOfObject != null) {
/* 302 */       byte b1; for (b1 = 0; b1 < arrayOfObject.length; b1++) {
/* 303 */         if (arrayOfObject[b1] instanceof Certificate) {
/* 304 */           b++;
/*     */         }
/*     */       } 
/* 307 */       arrayOfCertificate = new Certificate[b];
/* 308 */       b = 0;
/* 309 */       for (b1 = 0; b1 < arrayOfObject.length; b1++) {
/* 310 */         if (arrayOfObject[b1] instanceof Certificate) {
/* 311 */           arrayOfCertificate[b++] = (Certificate)arrayOfObject[b1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     PermissionCollection permissionCollection = null;
/* 317 */     synchronized (list) {
/* 318 */       int i = list.size();
/* 319 */       for (byte b1 = 0; b1 < i; b1++) {
/* 320 */         UnresolvedPermission unresolvedPermission = list.get(b1);
/* 321 */         Permission permission = unresolvedPermission.resolve(paramPermission, arrayOfCertificate);
/* 322 */         if (permission != null) {
/* 323 */           if (permissionCollection == null) {
/* 324 */             permissionCollection = paramPermission.newPermissionCollection();
/* 325 */             if (permissionCollection == null)
/* 326 */               permissionCollection = new PermissionsHash(); 
/*     */           } 
/* 328 */           permissionCollection.add(permission);
/*     */         } 
/*     */       } 
/*     */     } 
/* 332 */     return permissionCollection;
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
/* 346 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("perms", Hashtable.class), new ObjectStreamField("allPermission", PermissionCollection.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 364 */     Hashtable<Object, Object> hashtable = new Hashtable<>(this.permsMap.size() * 2);
/* 365 */     synchronized (this) {
/* 366 */       hashtable.putAll(this.permsMap);
/*     */     } 
/*     */ 
/*     */     
/* 370 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/*     */     
/* 372 */     putField.put("allPermission", this.allPermission);
/* 373 */     putField.put("perms", hashtable);
/* 374 */     paramObjectOutputStream.writeFields();
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 386 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */     
/* 389 */     this.allPermission = (PermissionCollection)getField.get("allPermission", (Object)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     Hashtable<? extends Class<?>, ? extends PermissionCollection> hashtable = (Hashtable)getField.get("perms", (Object)null);
/* 397 */     this.permsMap = new HashMap<>(hashtable.size() * 2);
/* 398 */     this.permsMap.putAll(hashtable);
/*     */ 
/*     */ 
/*     */     
/* 402 */     UnresolvedPermissionCollection unresolvedPermissionCollection = (UnresolvedPermissionCollection)this.permsMap.get(UnresolvedPermission.class);
/* 403 */     this.hasUnresolved = (unresolvedPermissionCollection != null && unresolvedPermissionCollection.elements().hasMoreElements());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/Permissions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */