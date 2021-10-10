/*     */ package javax.management.relation;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import javax.management.Notification;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RelationNotification
/*     */   extends Notification
/*     */ {
/*     */   private static final long oldSerialVersionUID = -2126464566505527147L;
/*     */   private static final long newSerialVersionUID = -6871117877523310399L;
/*  77 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("myNewRoleValue", ArrayList.class), new ObjectStreamField("myOldRoleValue", ArrayList.class), new ObjectStreamField("myRelId", String.class), new ObjectStreamField("myRelObjName", ObjectName.class), new ObjectStreamField("myRelTypeName", String.class), new ObjectStreamField("myRoleName", String.class), new ObjectStreamField("myUnregMBeanList", ArrayList.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("newRoleValue", List.class), new ObjectStreamField("oldRoleValue", List.class), new ObjectStreamField("relationId", String.class), new ObjectStreamField("relationObjName", ObjectName.class), new ObjectStreamField("relationTypeName", String.class), new ObjectStreamField("roleName", String.class), new ObjectStreamField("unregisterMBeanList", List.class) };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID;
/*     */ 
/*     */   
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */ 
/*     */   
/*     */   private static boolean compat = false;
/*     */ 
/*     */   
/*     */   public static final String RELATION_BASIC_CREATION = "jmx.relation.creation.basic";
/*     */ 
/*     */   
/*     */   public static final String RELATION_MBEAN_CREATION = "jmx.relation.creation.mbean";
/*     */ 
/*     */   
/*     */   public static final String RELATION_BASIC_UPDATE = "jmx.relation.update.basic";
/*     */ 
/*     */   
/*     */   public static final String RELATION_MBEAN_UPDATE = "jmx.relation.update.mbean";
/*     */ 
/*     */   
/*     */   public static final String RELATION_BASIC_REMOVAL = "jmx.relation.removal.basic";
/*     */ 
/*     */   
/*     */   public static final String RELATION_MBEAN_REMOVAL = "jmx.relation.removal.mbean";
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 123 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/* 124 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 125 */       compat = (str != null && str.equals("1.0"));
/* 126 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 129 */     if (compat) {
/* 130 */       serialPersistentFields = oldSerialPersistentFields;
/* 131 */       serialVersionUID = -2126464566505527147L;
/*     */     } else {
/* 133 */       serialPersistentFields = newSerialPersistentFields;
/* 134 */       serialVersionUID = -6871117877523310399L;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   private String relationId = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   private String relationTypeName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   private ObjectName relationObjName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   private List<ObjectName> unregisterMBeanList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   private String roleName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   private List<ObjectName> oldRoleValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   private List<ObjectName> newRoleValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RelationNotification(String paramString1, Object paramObject, long paramLong1, long paramLong2, String paramString2, String paramString3, String paramString4, ObjectName paramObjectName, List<ObjectName> paramList) throws IllegalArgumentException {
/* 261 */     super(paramString1, paramObject, paramLong1, paramLong2, paramString2);
/*     */     
/* 263 */     if (!isValidBasicStrict(paramString1, paramObject, paramString3, paramString4) || !isValidCreate(paramString1)) {
/* 264 */       throw new IllegalArgumentException("Invalid parameter.");
/*     */     }
/*     */     
/* 267 */     this.relationId = paramString3;
/* 268 */     this.relationTypeName = paramString4;
/* 269 */     this.relationObjName = safeGetObjectName(paramObjectName);
/* 270 */     this.unregisterMBeanList = safeGetObjectNameList(paramList);
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
/*     */   public RelationNotification(String paramString1, Object paramObject, long paramLong1, long paramLong2, String paramString2, String paramString3, String paramString4, ObjectName paramObjectName, String paramString5, List<ObjectName> paramList1, List<ObjectName> paramList2) throws IllegalArgumentException {
/* 311 */     super(paramString1, paramObject, paramLong1, paramLong2, paramString2);
/*     */     
/* 313 */     if (!isValidBasicStrict(paramString1, paramObject, paramString3, paramString4) || !isValidUpdate(paramString1, paramString5, paramList1, paramList2)) {
/* 314 */       throw new IllegalArgumentException("Invalid parameter.");
/*     */     }
/*     */     
/* 317 */     this.relationId = paramString3;
/* 318 */     this.relationTypeName = paramString4;
/* 319 */     this.relationObjName = safeGetObjectName(paramObjectName);
/*     */     
/* 321 */     this.roleName = paramString5;
/* 322 */     this.oldRoleValue = safeGetObjectNameList(paramList2);
/* 323 */     this.newRoleValue = safeGetObjectNameList(paramList1);
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
/*     */   public String getRelationId() {
/* 336 */     return this.relationId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRelationTypeName() {
/* 345 */     return this.relationTypeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName() {
/* 355 */     return this.relationObjName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ObjectName> getMBeansToUnregister() {
/*     */     List<?> list;
/* 366 */     if (this.unregisterMBeanList != null) {
/* 367 */       list = new ArrayList<>(this.unregisterMBeanList);
/*     */     } else {
/* 369 */       list = Collections.emptyList();
/*     */     } 
/* 371 */     return (List)list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRoleName() {
/* 380 */     String str = null;
/* 381 */     if (this.roleName != null) {
/* 382 */       str = this.roleName;
/*     */     }
/* 384 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ObjectName> getOldRoleValue() {
/*     */     List<?> list;
/* 394 */     if (this.oldRoleValue != null) {
/* 395 */       list = new ArrayList<>(this.oldRoleValue);
/*     */     } else {
/* 397 */       list = Collections.emptyList();
/*     */     } 
/* 399 */     return (List)list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ObjectName> getNewRoleValue() {
/*     */     List<?> list;
/* 409 */     if (this.newRoleValue != null) {
/* 410 */       list = new ArrayList<>(this.newRoleValue);
/*     */     } else {
/* 412 */       list = Collections.emptyList();
/*     */     } 
/* 414 */     return (List)list;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValidBasicStrict(String paramString1, Object paramObject, String paramString2, String paramString3) {
/* 466 */     if (paramObject == null) {
/* 467 */       return false;
/*     */     }
/* 469 */     return isValidBasic(paramString1, paramObject, paramString2, paramString3);
/*     */   }
/*     */   
/*     */   private boolean isValidBasic(String paramString1, Object paramObject, String paramString2, String paramString3) {
/* 473 */     if (paramString1 == null || paramString2 == null || paramString3 == null) {
/* 474 */       return false;
/*     */     }
/*     */     
/* 477 */     if (paramObject != null && !(paramObject instanceof RelationService) && !(paramObject instanceof ObjectName))
/*     */     {
/*     */       
/* 480 */       return false;
/*     */     }
/*     */     
/* 483 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isValidCreate(String paramString) {
/* 487 */     String[] arrayOfString = { "jmx.relation.creation.basic", "jmx.relation.creation.mbean", "jmx.relation.removal.basic", "jmx.relation.removal.mbean" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 492 */     HashSet hashSet = new HashSet(Arrays.asList((Object[])arrayOfString));
/* 493 */     return hashSet.contains(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValidUpdate(String paramString1, String paramString2, List<ObjectName> paramList1, List<ObjectName> paramList2) {
/* 499 */     if (!paramString1.equals("jmx.relation.update.basic") && 
/* 500 */       !paramString1.equals("jmx.relation.update.mbean")) {
/* 501 */       return false;
/*     */     }
/*     */     
/* 504 */     if (paramString2 == null || paramList2 == null || paramList1 == null) {
/* 505 */       return false;
/*     */     }
/*     */     
/* 508 */     return true;
/*     */   }
/*     */   
/*     */   private ArrayList<ObjectName> safeGetObjectNameList(List<ObjectName> paramList) {
/* 512 */     ArrayList<ObjectName> arrayList = null;
/* 513 */     if (paramList != null) {
/* 514 */       arrayList = new ArrayList();
/* 515 */       for (ObjectName objectName : paramList)
/*     */       {
/* 517 */         arrayList.add(ObjectName.getInstance(objectName));
/*     */       }
/*     */     } 
/* 520 */     return arrayList;
/*     */   }
/*     */   
/*     */   private ObjectName safeGetObjectName(ObjectName paramObjectName) {
/* 524 */     ObjectName objectName = null;
/* 525 */     if (paramObjectName != null) {
/* 526 */       objectName = ObjectName.getInstance(paramObjectName);
/*     */     }
/* 528 */     return objectName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*     */     String str1, str2, str3;
/*     */     ObjectName objectName;
/*     */     List<ObjectName> list1, list2, list3;
/* 542 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */     
/* 544 */     if (compat) {
/* 545 */       str1 = (String)getField.get("myRelId", (Object)null);
/* 546 */       str2 = (String)getField.get("myRelTypeName", (Object)null);
/* 547 */       str3 = (String)getField.get("myRoleName", (Object)null);
/*     */       
/* 549 */       objectName = (ObjectName)getField.get("myRelObjName", (Object)null);
/* 550 */       list1 = Util.<List>cast(getField.get("myNewRoleValue", (Object)null));
/* 551 */       list2 = Util.<List>cast(getField.get("myOldRoleValue", (Object)null));
/* 552 */       list3 = Util.<List>cast(getField.get("myUnregMBeanList", (Object)null));
/*     */     } else {
/*     */       
/* 555 */       str1 = (String)getField.get("relationId", (Object)null);
/* 556 */       str2 = (String)getField.get("relationTypeName", (Object)null);
/* 557 */       str3 = (String)getField.get("roleName", (Object)null);
/*     */       
/* 559 */       objectName = (ObjectName)getField.get("relationObjName", (Object)null);
/* 560 */       list1 = Util.<List>cast(getField.get("newRoleValue", (Object)null));
/* 561 */       list2 = Util.<List>cast(getField.get("oldRoleValue", (Object)null));
/* 562 */       list3 = Util.<List>cast(getField.get("unregisterMBeanList", (Object)null));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 568 */     String str4 = getType();
/* 569 */     if (!isValidBasic(str4, getSource(), str1, str2) || (
/* 570 */       !isValidCreate(str4) && 
/* 571 */       !isValidUpdate(str4, str3, list1, list2))) {
/*     */       
/* 573 */       setSource((Object)null);
/* 574 */       throw new InvalidObjectException("Invalid object read");
/*     */     } 
/*     */ 
/*     */     
/* 578 */     this.relationObjName = safeGetObjectName(objectName);
/* 579 */     this.newRoleValue = safeGetObjectNameList(list1);
/* 580 */     this.oldRoleValue = safeGetObjectNameList(list2);
/* 581 */     this.unregisterMBeanList = safeGetObjectNameList(list3);
/*     */     
/* 583 */     this.relationId = str1;
/* 584 */     this.relationTypeName = str2;
/* 585 */     this.roleName = str3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 594 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 598 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 599 */       putField.put("myNewRoleValue", this.newRoleValue);
/* 600 */       putField.put("myOldRoleValue", this.oldRoleValue);
/* 601 */       putField.put("myRelId", this.relationId);
/* 602 */       putField.put("myRelObjName", this.relationObjName);
/* 603 */       putField.put("myRelTypeName", this.relationTypeName);
/* 604 */       putField.put("myRoleName", this.roleName);
/* 605 */       putField.put("myUnregMBeanList", this.unregisterMBeanList);
/* 606 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 612 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RelationNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */