/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PropertyPermissionCollection
/*     */   extends PermissionCollection
/*     */   implements Serializable
/*     */ {
/* 447 */   private transient Map<String, PropertyPermission> perms = new HashMap<>(32);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean all_allowed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 7015263904581634791L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Permission paramPermission) {
/* 464 */     if (!(paramPermission instanceof PropertyPermission)) {
/* 465 */       throw new IllegalArgumentException("invalid permission: " + paramPermission);
/*     */     }
/* 467 */     if (isReadOnly()) {
/* 468 */       throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
/*     */     }
/*     */     
/* 471 */     PropertyPermission propertyPermission = (PropertyPermission)paramPermission;
/* 472 */     String str = propertyPermission.getName();
/*     */     
/* 474 */     synchronized (this) {
/* 475 */       PropertyPermission propertyPermission1 = this.perms.get(str);
/*     */       
/* 477 */       if (propertyPermission1 != null) {
/* 478 */         int i = propertyPermission1.getMask();
/* 479 */         int j = propertyPermission.getMask();
/* 480 */         if (i != j) {
/* 481 */           int k = i | j;
/* 482 */           String str1 = PropertyPermission.getActions(k);
/* 483 */           this.perms.put(str, new PropertyPermission(str, str1));
/*     */         } 
/*     */       } else {
/* 486 */         this.perms.put(str, propertyPermission);
/*     */       } 
/*     */     } 
/*     */     
/* 490 */     if (!this.all_allowed && 
/* 491 */       str.equals("*")) {
/* 492 */       this.all_allowed = true;
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
/*     */     PropertyPermission propertyPermission2;
/* 506 */     if (!(paramPermission instanceof PropertyPermission)) {
/* 507 */       return false;
/*     */     }
/* 509 */     PropertyPermission propertyPermission1 = (PropertyPermission)paramPermission;
/*     */ 
/*     */     
/* 512 */     int i = propertyPermission1.getMask();
/* 513 */     int j = 0;
/*     */ 
/*     */     
/* 516 */     if (this.all_allowed) {
/* 517 */       synchronized (this) {
/* 518 */         propertyPermission2 = this.perms.get("*");
/*     */       } 
/* 520 */       if (propertyPermission2 != null) {
/* 521 */         j |= propertyPermission2.getMask();
/* 522 */         if ((j & i) == i) {
/* 523 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 531 */     String str = propertyPermission1.getName();
/*     */ 
/*     */     
/* 534 */     synchronized (this) {
/* 535 */       propertyPermission2 = this.perms.get(str);
/*     */     } 
/*     */     
/* 538 */     if (propertyPermission2 != null) {
/*     */       
/* 540 */       j |= propertyPermission2.getMask();
/* 541 */       if ((j & i) == i) {
/* 542 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 548 */     int m = str.length() - 1;
/*     */     int k;
/* 550 */     while ((k = str.lastIndexOf(".", m)) != -1) {
/*     */       
/* 552 */       str = str.substring(0, k + 1) + "*";
/*     */       
/* 554 */       synchronized (this) {
/* 555 */         propertyPermission2 = this.perms.get(str);
/*     */       } 
/*     */       
/* 558 */       if (propertyPermission2 != null) {
/* 559 */         j |= propertyPermission2.getMask();
/* 560 */         if ((j & i) == i)
/* 561 */           return true; 
/*     */       } 
/* 563 */       m = k - 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 568 */     return false;
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
/*     */   public Enumeration<Permission> elements() {
/* 580 */     synchronized (this) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 585 */       return Collections.enumeration(this.perms.values());
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
/* 605 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("permissions", Hashtable.class), new ObjectStreamField("all_allowed", boolean.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 623 */     Hashtable<Object, Object> hashtable = new Hashtable<>(this.perms.size() * 2);
/* 624 */     synchronized (this) {
/* 625 */       hashtable.putAll(this.perms);
/*     */     } 
/*     */ 
/*     */     
/* 629 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 630 */     putField.put("all_allowed", this.all_allowed);
/* 631 */     putField.put("permissions", hashtable);
/* 632 */     paramObjectOutputStream.writeFields();
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
/* 645 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */     
/* 648 */     this.all_allowed = getField.get("all_allowed", false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 653 */     Hashtable<? extends String, ? extends PropertyPermission> hashtable = (Hashtable)getField.get("permissions", (Object)null);
/* 654 */     this.perms = new HashMap<>(hashtable.size() * 2);
/* 655 */     this.perms.putAll(hashtable);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/PropertyPermissionCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */