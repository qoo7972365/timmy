/*     */ package javax.management.relation;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoleResult
/*     */   implements Serializable
/*     */ {
/*     */   private static final long oldSerialVersionUID = 3786616013762091099L;
/*     */   private static final long newSerialVersionUID = -6304063118040985512L;
/*  64 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("myRoleList", RoleList.class), new ObjectStreamField("myRoleUnresList", RoleUnresolvedList.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("roleList", RoleList.class), new ObjectStreamField("unresolvedRoleList", RoleUnresolvedList.class) };
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
/*  87 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  88 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  89 */       compat = (str != null && str.equals("1.0"));
/*  90 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  93 */     if (compat) {
/*  94 */       serialPersistentFields = oldSerialPersistentFields;
/*  95 */       serialVersionUID = 3786616013762091099L;
/*     */     } else {
/*  97 */       serialPersistentFields = newSerialPersistentFields;
/*  98 */       serialVersionUID = -6304063118040985512L;
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
/* 111 */   private RoleList roleList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   private RoleUnresolvedList unresolvedRoleList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleResult(RoleList paramRoleList, RoleUnresolvedList paramRoleUnresolvedList) {
/* 132 */     setRoles(paramRoleList);
/* 133 */     setRolesUnresolved(paramRoleUnresolvedList);
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
/*     */   public RoleList getRoles() {
/* 149 */     return this.roleList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleUnresolvedList getRolesUnresolved() {
/* 160 */     return this.unresolvedRoleList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoles(RoleList paramRoleList) {
/* 171 */     if (paramRoleList != null) {
/*     */       
/* 173 */       this.roleList = new RoleList();
/*     */       
/* 175 */       Iterator<Role> iterator = paramRoleList.iterator();
/* 176 */       while (iterator.hasNext()) {
/* 177 */         Role role = iterator.next();
/* 178 */         this.roleList.add((Role)role.clone());
/*     */       } 
/*     */     } else {
/* 181 */       this.roleList = null;
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
/*     */   public void setRolesUnresolved(RoleUnresolvedList paramRoleUnresolvedList) {
/* 194 */     if (paramRoleUnresolvedList != null) {
/*     */       
/* 196 */       this.unresolvedRoleList = new RoleUnresolvedList();
/*     */       
/* 198 */       Iterator<RoleUnresolved> iterator = paramRoleUnresolvedList.iterator();
/* 199 */       while (iterator.hasNext()) {
/*     */         
/* 201 */         RoleUnresolved roleUnresolved = iterator.next();
/* 202 */         this.unresolvedRoleList.add((RoleUnresolved)roleUnresolved.clone());
/*     */       } 
/*     */     } else {
/* 205 */       this.unresolvedRoleList = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 215 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 219 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 220 */       this.roleList = (RoleList)getField.get("myRoleList", (Object)null);
/* 221 */       if (getField.defaulted("myRoleList"))
/*     */       {
/* 223 */         throw new NullPointerException("myRoleList");
/*     */       }
/* 225 */       this.unresolvedRoleList = (RoleUnresolvedList)getField.get("myRoleUnresList", (Object)null);
/* 226 */       if (getField.defaulted("myRoleUnresList"))
/*     */       {
/* 228 */         throw new NullPointerException("myRoleUnresList");
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 235 */       paramObjectInputStream.defaultReadObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 245 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 249 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 250 */       putField.put("myRoleList", this.roleList);
/* 251 */       putField.put("myRoleUnresList", this.unresolvedRoleList);
/* 252 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 258 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RoleResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */