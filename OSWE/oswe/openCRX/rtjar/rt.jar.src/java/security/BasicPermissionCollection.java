/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BasicPermissionCollection
/*     */   extends PermissionCollection
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 739301742472979399L;
/*     */   private transient Map<String, Permission> perms;
/*     */   private boolean all_allowed;
/*     */   private Class<?> permClass;
/*     */   
/*     */   public BasicPermissionCollection(Class<?> paramClass) {
/* 335 */     this.perms = new HashMap<>(11);
/* 336 */     this.all_allowed = false;
/* 337 */     this.permClass = paramClass;
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
/*     */   public void add(Permission paramPermission) {
/* 356 */     if (!(paramPermission instanceof BasicPermission)) {
/* 357 */       throw new IllegalArgumentException("invalid permission: " + paramPermission);
/*     */     }
/* 359 */     if (isReadOnly()) {
/* 360 */       throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
/*     */     }
/* 362 */     BasicPermission basicPermission = (BasicPermission)paramPermission;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     if (this.permClass == null) {
/*     */       
/* 369 */       this.permClass = basicPermission.getClass();
/*     */     }
/* 371 */     else if (basicPermission.getClass() != this.permClass) {
/* 372 */       throw new IllegalArgumentException("invalid permission: " + paramPermission);
/*     */     } 
/*     */ 
/*     */     
/* 376 */     synchronized (this) {
/* 377 */       this.perms.put(basicPermission.getCanonicalName(), paramPermission);
/*     */     } 
/*     */ 
/*     */     
/* 381 */     if (!this.all_allowed && 
/* 382 */       basicPermission.getCanonicalName().equals("*")) {
/* 383 */       this.all_allowed = true;
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
/*     */   public boolean implies(Permission paramPermission) {
/*     */     Permission permission;
/* 397 */     if (!(paramPermission instanceof BasicPermission)) {
/* 398 */       return false;
/*     */     }
/* 400 */     BasicPermission basicPermission = (BasicPermission)paramPermission;
/*     */ 
/*     */     
/* 403 */     if (basicPermission.getClass() != this.permClass) {
/* 404 */       return false;
/*     */     }
/*     */     
/* 407 */     if (this.all_allowed) {
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 414 */     String str = basicPermission.getCanonicalName();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     synchronized (this) {
/* 420 */       permission = this.perms.get(str);
/*     */     } 
/*     */     
/* 423 */     if (permission != null)
/*     */     {
/* 425 */       return permission.implies(paramPermission);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 431 */     int j = str.length() - 1;
/*     */     int i;
/* 433 */     while ((i = str.lastIndexOf(".", j)) != -1) {
/*     */       
/* 435 */       str = str.substring(0, i + 1) + "*";
/*     */ 
/*     */       
/* 438 */       synchronized (this) {
/* 439 */         permission = this.perms.get(str);
/*     */       } 
/*     */       
/* 442 */       if (permission != null) {
/* 443 */         return permission.implies(paramPermission);
/*     */       }
/* 445 */       j = i - 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 450 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<Permission> elements() {
/* 461 */     synchronized (this) {
/* 462 */       return Collections.enumeration(this.perms.values());
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
/* 485 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("permissions", Hashtable.class), new ObjectStreamField("all_allowed", boolean.class), new ObjectStreamField("permClass", Class.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 504 */     Hashtable<Object, Object> hashtable = new Hashtable<>(this.perms.size() * 2);
/*     */     
/* 506 */     synchronized (this) {
/* 507 */       hashtable.putAll(this.perms);
/*     */     } 
/*     */ 
/*     */     
/* 511 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 512 */     putField.put("all_allowed", this.all_allowed);
/* 513 */     putField.put("permissions", hashtable);
/* 514 */     putField.put("permClass", this.permClass);
/* 515 */     paramObjectOutputStream.writeFields();
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 528 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     Hashtable<? extends String, ? extends Permission> hashtable = (Hashtable)getField.get("permissions", (Object)null);
/* 536 */     this.perms = new HashMap<>(hashtable.size() * 2);
/* 537 */     this.perms.putAll(hashtable);
/*     */ 
/*     */     
/* 540 */     this.all_allowed = getField.get("all_allowed", false);
/*     */ 
/*     */     
/* 543 */     this.permClass = (Class)getField.get("permClass", (Object)null);
/*     */     
/* 545 */     if (this.permClass == null) {
/*     */       
/* 547 */       Enumeration<? extends Permission> enumeration = hashtable.elements();
/* 548 */       if (enumeration.hasMoreElements()) {
/* 549 */         Permission permission = enumeration.nextElement();
/* 550 */         this.permClass = permission.getClass();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/BasicPermissionCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */