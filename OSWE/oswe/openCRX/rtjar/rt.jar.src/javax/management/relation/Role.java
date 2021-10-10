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
/*     */ public class Role
/*     */   implements Serializable
/*     */ {
/*     */   private static final long oldSerialVersionUID = -1959486389343113026L;
/*     */   private static final long newSerialVersionUID = -279985518429862552L;
/*  70 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("myName", String.class), new ObjectStreamField("myObjNameList", ArrayList.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("name", String.class), new ObjectStreamField("objectNameList", List.class) };
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
/*     */   
/*     */   static {
/*     */     try {
/*  93 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  94 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  95 */       compat = (str != null && str.equals("1.0"));
/*  96 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  99 */     if (compat) {
/* 100 */       serialPersistentFields = oldSerialPersistentFields;
/* 101 */       serialVersionUID = -1959486389343113026L;
/*     */     } else {
/* 103 */       serialPersistentFields = newSerialPersistentFields;
/* 104 */       serialVersionUID = -279985518429862552L;
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
/* 117 */   private String name = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   private List<ObjectName> objectNameList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Role(String paramString, List<ObjectName> paramList) throws IllegalArgumentException {
/* 143 */     if (paramString == null || paramList == null) {
/* 144 */       String str = "Invalid parameter";
/* 145 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 148 */     setRoleName(paramString);
/* 149 */     setRoleValue(paramList);
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
/*     */   public String getRoleName() {
/* 166 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ObjectName> getRoleValue() {
/* 177 */     return this.objectNameList;
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
/* 192 */     if (paramString == null) {
/* 193 */       String str = "Invalid parameter.";
/* 194 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 197 */     this.name = paramString;
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
/*     */   public void setRoleValue(List<ObjectName> paramList) throws IllegalArgumentException {
/* 214 */     if (paramList == null) {
/* 215 */       String str = "Invalid parameter.";
/* 216 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 219 */     this.objectNameList = new ArrayList<>(paramList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 229 */     StringBuilder stringBuilder = new StringBuilder();
/* 230 */     stringBuilder.append("role name: " + this.name + "; role value: ");
/* 231 */     Iterator<ObjectName> iterator = this.objectNameList.iterator();
/* 232 */     while (iterator.hasNext()) {
/* 233 */       ObjectName objectName = iterator.next();
/* 234 */       stringBuilder.append(objectName.toString());
/* 235 */       if (iterator.hasNext()) {
/* 236 */         stringBuilder.append(", ");
/*     */       }
/*     */     } 
/* 239 */     return stringBuilder.toString();
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
/*     */   public Object clone() {
/*     */     try {
/* 254 */       return new Role(this.name, this.objectNameList);
/* 255 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 256 */       return null;
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
/*     */   public static String roleValueToString(List<ObjectName> paramList) throws IllegalArgumentException {
/* 273 */     if (paramList == null) {
/* 274 */       String str = "Invalid parameter";
/* 275 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 278 */     StringBuilder stringBuilder = new StringBuilder();
/* 279 */     for (ObjectName objectName : paramList) {
/* 280 */       if (stringBuilder.length() > 0)
/* 281 */         stringBuilder.append("\n"); 
/* 282 */       stringBuilder.append(objectName.toString());
/*     */     } 
/* 284 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 292 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 296 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 297 */       this.name = (String)getField.get("myName", (Object)null);
/* 298 */       if (getField.defaulted("myName"))
/*     */       {
/* 300 */         throw new NullPointerException("myName");
/*     */       }
/* 302 */       this.objectNameList = Util.<List<ObjectName>>cast(getField.get("myObjNameList", (Object)null));
/* 303 */       if (getField.defaulted("myObjNameList"))
/*     */       {
/* 305 */         throw new NullPointerException("myObjNameList");
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 312 */       paramObjectInputStream.defaultReadObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 322 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 326 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 327 */       putField.put("myName", this.name);
/* 328 */       putField.put("myObjNameList", this.objectNameList);
/* 329 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 335 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/Role.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */