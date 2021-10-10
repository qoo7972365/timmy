/*     */ package javax.management.relation;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoleInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long oldSerialVersionUID = 7227256952085334351L;
/*     */   private static final long newSerialVersionUID = 2504952983494636987L;
/*  65 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("myName", String.class), new ObjectStreamField("myIsReadableFlg", boolean.class), new ObjectStreamField("myIsWritableFlg", boolean.class), new ObjectStreamField("myDescription", String.class), new ObjectStreamField("myMinDegree", int.class), new ObjectStreamField("myMaxDegree", int.class), new ObjectStreamField("myRefMBeanClassName", String.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("name", String.class), new ObjectStreamField("isReadable", boolean.class), new ObjectStreamField("isWritable", boolean.class), new ObjectStreamField("description", String.class), new ObjectStreamField("minDegree", int.class), new ObjectStreamField("maxDegree", int.class), new ObjectStreamField("referencedMBeanClassName", String.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean compat = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ROLE_CARDINALITY_INFINITY = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 103 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/* 104 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 105 */       compat = (str != null && str.equals("1.0"));
/* 106 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 109 */     if (compat) {
/* 110 */       serialPersistentFields = oldSerialPersistentFields;
/* 111 */       serialVersionUID = 7227256952085334351L;
/*     */     } else {
/* 113 */       serialPersistentFields = newSerialPersistentFields;
/* 114 */       serialVersionUID = 2504952983494636987L;
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
/* 136 */   private String name = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isReadable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isWritable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   private String description = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int minDegree;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int maxDegree;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   private String referencedMBeanClassName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleInfo(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, String paramString3) throws IllegalArgumentException, InvalidRoleInfoException, ClassNotFoundException, NotCompliantMBeanException {
/* 215 */     init(paramString1, paramString2, paramBoolean1, paramBoolean2, paramInt1, paramInt2, paramString3);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleInfo(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2) throws IllegalArgumentException, ClassNotFoundException, NotCompliantMBeanException {
/*     */     try {
/* 258 */       init(paramString1, paramString2, paramBoolean1, paramBoolean2, 1, 1, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 265 */     catch (InvalidRoleInfoException invalidRoleInfoException) {}
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
/*     */   public RoleInfo(String paramString1, String paramString2) throws IllegalArgumentException, ClassNotFoundException, NotCompliantMBeanException {
/*     */     try {
/* 301 */       init(paramString1, paramString2, true, true, 1, 1, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 308 */     catch (InvalidRoleInfoException invalidRoleInfoException) {}
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
/*     */   public RoleInfo(RoleInfo paramRoleInfo) throws IllegalArgumentException {
/* 326 */     if (paramRoleInfo == null) {
/*     */       
/* 328 */       String str = "Invalid parameter.";
/* 329 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/*     */     try {
/* 333 */       init(paramRoleInfo.getName(), paramRoleInfo
/* 334 */           .getRefMBeanClassName(), paramRoleInfo
/* 335 */           .isReadable(), paramRoleInfo
/* 336 */           .isWritable(), paramRoleInfo
/* 337 */           .getMinDegree(), paramRoleInfo
/* 338 */           .getMaxDegree(), paramRoleInfo
/* 339 */           .getDescription());
/* 340 */     } catch (InvalidRoleInfoException invalidRoleInfoException) {}
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
/*     */   public String getName() {
/* 357 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadable() {
/* 366 */     return this.isReadable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/* 375 */     return this.isWritable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 384 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinDegree() {
/* 393 */     return this.minDegree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDegree() {
/* 402 */     return this.maxDegree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRefMBeanClassName() {
/* 412 */     return this.referencedMBeanClassName;
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
/*     */   public boolean checkMinDegree(int paramInt) {
/* 424 */     if (paramInt >= -1 && (this.minDegree == -1 || paramInt >= this.minDegree))
/*     */     {
/*     */       
/* 427 */       return true;
/*     */     }
/* 429 */     return false;
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
/*     */   public boolean checkMaxDegree(int paramInt) {
/* 442 */     if (paramInt >= -1 && (this.maxDegree == -1 || (paramInt != -1 && paramInt <= this.maxDegree)))
/*     */     {
/*     */ 
/*     */       
/* 446 */       return true;
/*     */     }
/* 448 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 458 */     StringBuilder stringBuilder = new StringBuilder();
/* 459 */     stringBuilder.append("role info name: " + this.name);
/* 460 */     stringBuilder.append("; isReadable: " + this.isReadable);
/* 461 */     stringBuilder.append("; isWritable: " + this.isWritable);
/* 462 */     stringBuilder.append("; description: " + this.description);
/* 463 */     stringBuilder.append("; minimum degree: " + this.minDegree);
/* 464 */     stringBuilder.append("; maximum degree: " + this.maxDegree);
/* 465 */     stringBuilder.append("; MBean class: " + this.referencedMBeanClassName);
/* 466 */     return stringBuilder.toString();
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
/*     */   private void init(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, String paramString3) throws IllegalArgumentException, InvalidRoleInfoException {
/* 484 */     if (paramString1 == null || paramString2 == null) {
/*     */ 
/*     */       
/* 487 */       String str = "Invalid parameter.";
/* 488 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 491 */     this.name = paramString1;
/* 492 */     this.isReadable = paramBoolean1;
/* 493 */     this.isWritable = paramBoolean2;
/* 494 */     if (paramString3 != null) {
/* 495 */       this.description = paramString3;
/*     */     }
/*     */     
/* 498 */     boolean bool = false;
/* 499 */     StringBuilder stringBuilder = new StringBuilder();
/* 500 */     if (paramInt2 != -1 && (paramInt1 == -1 || paramInt1 > paramInt2)) {
/*     */ 
/*     */ 
/*     */       
/* 504 */       stringBuilder.append("Minimum degree ");
/* 505 */       stringBuilder.append(paramInt1);
/* 506 */       stringBuilder.append(" is greater than maximum degree ");
/* 507 */       stringBuilder.append(paramInt2);
/* 508 */       bool = true;
/*     */     }
/* 510 */     else if (paramInt1 < -1 || paramInt2 < -1) {
/*     */ 
/*     */       
/* 513 */       stringBuilder.append("Minimum or maximum degree has an illegal value, must be [0, ROLE_CARDINALITY_INFINITY].");
/* 514 */       bool = true;
/*     */     } 
/* 516 */     if (bool) {
/* 517 */       throw new InvalidRoleInfoException(stringBuilder.toString());
/*     */     }
/* 519 */     this.minDegree = paramInt1;
/* 520 */     this.maxDegree = paramInt2;
/*     */     
/* 522 */     this.referencedMBeanClassName = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 532 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 536 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 537 */       this.name = (String)getField.get("myName", (Object)null);
/* 538 */       if (getField.defaulted("myName"))
/*     */       {
/* 540 */         throw new NullPointerException("myName");
/*     */       }
/* 542 */       this.isReadable = getField.get("myIsReadableFlg", false);
/* 543 */       if (getField.defaulted("myIsReadableFlg"))
/*     */       {
/* 545 */         throw new NullPointerException("myIsReadableFlg");
/*     */       }
/* 547 */       this.isWritable = getField.get("myIsWritableFlg", false);
/* 548 */       if (getField.defaulted("myIsWritableFlg"))
/*     */       {
/* 550 */         throw new NullPointerException("myIsWritableFlg");
/*     */       }
/* 552 */       this.description = (String)getField.get("myDescription", (Object)null);
/* 553 */       if (getField.defaulted("myDescription"))
/*     */       {
/* 555 */         throw new NullPointerException("myDescription");
/*     */       }
/* 557 */       this.minDegree = getField.get("myMinDegree", 0);
/* 558 */       if (getField.defaulted("myMinDegree"))
/*     */       {
/* 560 */         throw new NullPointerException("myMinDegree");
/*     */       }
/* 562 */       this.maxDegree = getField.get("myMaxDegree", 0);
/* 563 */       if (getField.defaulted("myMaxDegree"))
/*     */       {
/* 565 */         throw new NullPointerException("myMaxDegree");
/*     */       }
/* 567 */       this.referencedMBeanClassName = (String)getField.get("myRefMBeanClassName", (Object)null);
/* 568 */       if (getField.defaulted("myRefMBeanClassName"))
/*     */       {
/* 570 */         throw new NullPointerException("myRefMBeanClassName");
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 577 */       paramObjectInputStream.defaultReadObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 587 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 591 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 592 */       putField.put("myName", this.name);
/* 593 */       putField.put("myIsReadableFlg", this.isReadable);
/* 594 */       putField.put("myIsWritableFlg", this.isWritable);
/* 595 */       putField.put("myDescription", this.description);
/* 596 */       putField.put("myMinDegree", this.minDegree);
/* 597 */       putField.put("myMaxDegree", this.maxDegree);
/* 598 */       putField.put("myRefMBeanClassName", this.referencedMBeanClassName);
/* 599 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 605 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RoleInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */