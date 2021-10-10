/*     */ package javax.management.relation;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class RoleUnresolved
/*     */   implements Serializable
/*     */ {
/*     */   private static final long oldSerialVersionUID = -9026457686611660144L;
/*     */   private static final long newSerialVersionUID = -48350262537070138L;
/*  71 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("myRoleName", String.class), new ObjectStreamField("myRoleValue", ArrayList.class), new ObjectStreamField("myPbType", int.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("roleName", String.class), new ObjectStreamField("roleValue", List.class), new ObjectStreamField("problemType", int.class) };
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
/*     */   private static boolean compat = false;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  96 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  97 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  98 */       compat = (str != null && str.equals("1.0"));
/*  99 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 102 */     if (compat) {
/* 103 */       serialPersistentFields = oldSerialPersistentFields;
/* 104 */       serialVersionUID = -9026457686611660144L;
/*     */     } else {
/* 106 */       serialPersistentFields = newSerialPersistentFields;
/* 107 */       serialVersionUID = -48350262537070138L;
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
/* 120 */   private String roleName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   private List<ObjectName> roleValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int problemType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleUnresolved(String paramString, List<ObjectName> paramList, int paramInt) throws IllegalArgumentException {
/* 153 */     if (paramString == null) {
/* 154 */       String str = "Invalid parameter.";
/* 155 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 158 */     setRoleName(paramString);
/* 159 */     setRoleValue(paramList);
/*     */     
/* 161 */     setProblemType(paramInt);
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
/*     */   public String getRoleName() {
/* 177 */     return this.roleName;
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
/*     */   public List<ObjectName> getRoleValue() {
/* 190 */     return this.roleValue;
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
/*     */   public int getProblemType() {
/* 202 */     return this.problemType;
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
/*     */   public void setRoleName(String paramString) throws IllegalArgumentException {
/* 217 */     if (paramString == null) {
/* 218 */       String str = "Invalid parameter.";
/* 219 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 222 */     this.roleName = paramString;
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
/*     */   public void setRoleValue(List<ObjectName> paramList) {
/* 236 */     if (paramList != null) {
/* 237 */       this.roleValue = new ArrayList<>(paramList);
/*     */     } else {
/* 239 */       this.roleValue = null;
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
/*     */   public void setProblemType(int paramInt) throws IllegalArgumentException {
/* 257 */     if (!RoleStatus.isRoleStatus(paramInt)) {
/* 258 */       String str = "Incorrect problem type.";
/* 259 */       throw new IllegalArgumentException(str);
/*     */     } 
/* 261 */     this.problemType = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 272 */       return new RoleUnresolved(this.roleName, this.roleValue, this.problemType);
/* 273 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 274 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 284 */     StringBuilder stringBuilder = new StringBuilder();
/* 285 */     stringBuilder.append("role name: " + this.roleName);
/* 286 */     if (this.roleValue != null) {
/* 287 */       stringBuilder.append("; value: ");
/* 288 */       Iterator<ObjectName> iterator = this.roleValue.iterator();
/* 289 */       while (iterator.hasNext()) {
/* 290 */         ObjectName objectName = iterator.next();
/* 291 */         stringBuilder.append(objectName.toString());
/* 292 */         if (iterator.hasNext()) {
/* 293 */           stringBuilder.append(", ");
/*     */         }
/*     */       } 
/*     */     } 
/* 297 */     stringBuilder.append("; problem type: " + this.problemType);
/* 298 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 306 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 310 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 311 */       this.roleName = (String)getField.get("myRoleName", (Object)null);
/* 312 */       if (getField.defaulted("myRoleName"))
/*     */       {
/* 314 */         throw new NullPointerException("myRoleName");
/*     */       }
/* 316 */       this.roleValue = Util.<List<ObjectName>>cast(getField.get("myRoleValue", (Object)null));
/* 317 */       if (getField.defaulted("myRoleValue"))
/*     */       {
/* 319 */         throw new NullPointerException("myRoleValue");
/*     */       }
/* 321 */       this.problemType = getField.get("myPbType", 0);
/* 322 */       if (getField.defaulted("myPbType"))
/*     */       {
/* 324 */         throw new NullPointerException("myPbType");
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 331 */       paramObjectInputStream.defaultReadObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 341 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 345 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 346 */       putField.put("myRoleName", this.roleName);
/* 347 */       putField.put("myRoleValue", this.roleValue);
/* 348 */       putField.put("myPbType", this.problemType);
/* 349 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 355 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RoleUnresolved.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */