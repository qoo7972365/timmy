/*     */ package javax.management.relation;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ public class RelationTypeSupport
/*     */   implements RelationType
/*     */ {
/*     */   private static final long oldSerialVersionUID = -8179019472410837190L;
/*     */   private static final long newSerialVersionUID = 4611072955724144607L;
/*  82 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("myTypeName", String.class), new ObjectStreamField("myRoleName2InfoMap", HashMap.class), new ObjectStreamField("myIsInRelServFlg", boolean.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("typeName", String.class), new ObjectStreamField("roleName2InfoMap", Map.class), new ObjectStreamField("isInRelationService", boolean.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean compat = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 110 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/* 111 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 112 */       compat = (str != null && str.equals("1.0"));
/* 113 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 116 */     if (compat) {
/* 117 */       serialPersistentFields = oldSerialPersistentFields;
/* 118 */       serialVersionUID = -8179019472410837190L;
/*     */     } else {
/* 120 */       serialPersistentFields = newSerialPersistentFields;
/* 121 */       serialVersionUID = 4611072955724144607L;
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
/* 134 */   private String typeName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private Map<String, RoleInfo> roleName2InfoMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInRelationService = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RelationTypeSupport(String paramString, RoleInfo[] paramArrayOfRoleInfo) throws IllegalArgumentException, InvalidRelationTypeException {
/* 171 */     if (paramString == null || paramArrayOfRoleInfo == null) {
/* 172 */       String str = "Invalid parameter.";
/* 173 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 176 */     JmxProperties.RELATION_LOGGER.entering(RelationTypeSupport.class.getName(), "RelationTypeSupport", paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     initMembers(paramString, paramArrayOfRoleInfo);
/*     */     
/* 183 */     JmxProperties.RELATION_LOGGER.exiting(RelationTypeSupport.class.getName(), "RelationTypeSupport");
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
/*     */   protected RelationTypeSupport(String paramString) {
/* 197 */     if (paramString == null) {
/* 198 */       String str = "Invalid parameter.";
/* 199 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 202 */     JmxProperties.RELATION_LOGGER.entering(RelationTypeSupport.class.getName(), "RelationTypeSupport", paramString);
/*     */ 
/*     */     
/* 205 */     this.typeName = paramString;
/*     */     
/* 207 */     JmxProperties.RELATION_LOGGER.exiting(RelationTypeSupport.class.getName(), "RelationTypeSupport");
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
/*     */   public String getRelationTypeName() {
/* 222 */     return this.typeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoleInfo> getRoleInfos() {
/* 229 */     return new ArrayList<>(this.roleName2InfoMap.values());
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
/*     */   public RoleInfo getRoleInfo(String paramString) throws IllegalArgumentException, RoleInfoNotFoundException {
/* 249 */     if (paramString == null) {
/* 250 */       String str = "Invalid parameter.";
/* 251 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 254 */     JmxProperties.RELATION_LOGGER.entering(RelationTypeSupport.class.getName(), "getRoleInfo", paramString);
/*     */ 
/*     */ 
/*     */     
/* 258 */     RoleInfo roleInfo = this.roleName2InfoMap.get(paramString);
/*     */     
/* 260 */     if (roleInfo == null) {
/* 261 */       StringBuilder stringBuilder = new StringBuilder();
/* 262 */       String str = "No role info for role ";
/* 263 */       stringBuilder.append(str);
/* 264 */       stringBuilder.append(paramString);
/* 265 */       throw new RoleInfoNotFoundException(stringBuilder.toString());
/*     */     } 
/*     */     
/* 268 */     JmxProperties.RELATION_LOGGER.exiting(RelationTypeSupport.class.getName(), "getRoleInfo");
/*     */     
/* 270 */     return roleInfo;
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
/*     */   protected void addRoleInfo(RoleInfo paramRoleInfo) throws IllegalArgumentException, InvalidRelationTypeException {
/* 295 */     if (paramRoleInfo == null) {
/* 296 */       String str1 = "Invalid parameter.";
/* 297 */       throw new IllegalArgumentException(str1);
/*     */     } 
/*     */     
/* 300 */     JmxProperties.RELATION_LOGGER.entering(RelationTypeSupport.class.getName(), "addRoleInfo", paramRoleInfo);
/*     */ 
/*     */     
/* 303 */     if (this.isInRelationService) {
/*     */       
/* 305 */       String str1 = "Relation type cannot be updated as it is declared in the Relation Service.";
/* 306 */       throw new RuntimeException(str1);
/*     */     } 
/*     */     
/* 309 */     String str = paramRoleInfo.getName();
/*     */ 
/*     */     
/* 312 */     if (this.roleName2InfoMap.containsKey(str)) {
/* 313 */       StringBuilder stringBuilder = new StringBuilder();
/* 314 */       String str1 = "Two role infos provided for role ";
/* 315 */       stringBuilder.append(str1);
/* 316 */       stringBuilder.append(str);
/* 317 */       throw new InvalidRelationTypeException(stringBuilder.toString());
/*     */     } 
/*     */     
/* 320 */     this.roleName2InfoMap.put(str, new RoleInfo(paramRoleInfo));
/*     */     
/* 322 */     JmxProperties.RELATION_LOGGER.exiting(RelationTypeSupport.class.getName(), "addRoleInfo");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setRelationServiceFlag(boolean paramBoolean) {
/* 330 */     this.isInRelationService = paramBoolean;
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
/*     */   private void initMembers(String paramString, RoleInfo[] paramArrayOfRoleInfo) throws IllegalArgumentException, InvalidRelationTypeException {
/* 349 */     if (paramString == null || paramArrayOfRoleInfo == null) {
/* 350 */       String str = "Invalid parameter.";
/* 351 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 354 */     JmxProperties.RELATION_LOGGER.entering(RelationTypeSupport.class.getName(), "initMembers", paramString);
/*     */ 
/*     */     
/* 357 */     this.typeName = paramString;
/*     */ 
/*     */ 
/*     */     
/* 361 */     checkRoleInfos(paramArrayOfRoleInfo);
/*     */     
/* 363 */     for (byte b = 0; b < paramArrayOfRoleInfo.length; b++) {
/* 364 */       RoleInfo roleInfo = paramArrayOfRoleInfo[b];
/* 365 */       this.roleName2InfoMap.put(roleInfo.getName(), new RoleInfo(roleInfo));
/*     */     } 
/*     */ 
/*     */     
/* 369 */     JmxProperties.RELATION_LOGGER.exiting(RelationTypeSupport.class.getName(), "initMembers");
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
/*     */   static void checkRoleInfos(RoleInfo[] paramArrayOfRoleInfo) throws IllegalArgumentException, InvalidRelationTypeException {
/* 390 */     if (paramArrayOfRoleInfo == null) {
/* 391 */       String str = "Invalid parameter.";
/* 392 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 395 */     if (paramArrayOfRoleInfo.length == 0) {
/*     */       
/* 397 */       String str = "No role info provided.";
/* 398 */       throw new InvalidRelationTypeException(str);
/*     */     } 
/*     */ 
/*     */     
/* 402 */     HashSet<String> hashSet = new HashSet();
/*     */     
/* 404 */     for (byte b = 0; b < paramArrayOfRoleInfo.length; b++) {
/* 405 */       RoleInfo roleInfo = paramArrayOfRoleInfo[b];
/*     */       
/* 407 */       if (roleInfo == null) {
/* 408 */         String str1 = "Null role info provided.";
/* 409 */         throw new InvalidRelationTypeException(str1);
/*     */       } 
/*     */       
/* 412 */       String str = roleInfo.getName();
/*     */ 
/*     */       
/* 415 */       if (hashSet.contains(str)) {
/* 416 */         StringBuilder stringBuilder = new StringBuilder();
/* 417 */         String str1 = "Two role infos provided for role ";
/* 418 */         stringBuilder.append(str1);
/* 419 */         stringBuilder.append(str);
/* 420 */         throw new InvalidRelationTypeException(stringBuilder.toString());
/*     */       } 
/* 422 */       hashSet.add(str);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 434 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 438 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 439 */       this.typeName = (String)getField.get("myTypeName", (Object)null);
/* 440 */       if (getField.defaulted("myTypeName"))
/*     */       {
/* 442 */         throw new NullPointerException("myTypeName");
/*     */       }
/* 444 */       this.roleName2InfoMap = Util.<Map<String, RoleInfo>>cast(getField.get("myRoleName2InfoMap", (Object)null));
/* 445 */       if (getField.defaulted("myRoleName2InfoMap"))
/*     */       {
/* 447 */         throw new NullPointerException("myRoleName2InfoMap");
/*     */       }
/* 449 */       this.isInRelationService = getField.get("myIsInRelServFlg", false);
/* 450 */       if (getField.defaulted("myIsInRelServFlg"))
/*     */       {
/* 452 */         throw new NullPointerException("myIsInRelServFlg");
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 459 */       paramObjectInputStream.defaultReadObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 469 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 473 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 474 */       putField.put("myTypeName", this.typeName);
/* 475 */       putField.put("myRoleName2InfoMap", this.roleName2InfoMap);
/* 476 */       putField.put("myIsInRelServFlg", this.isInRelationService);
/* 477 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 483 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RelationTypeSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */