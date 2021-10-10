/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class UnresolvedPermissionCollection
/*     */   extends PermissionCollection
/*     */   implements Serializable
/*     */ {
/*  64 */   private transient Map<String, List<UnresolvedPermission>> perms = new HashMap<>(11);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -7176153071733132400L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Permission paramPermission) {
/*     */     List<UnresolvedPermission> list;
/*  76 */     if (!(paramPermission instanceof UnresolvedPermission)) {
/*  77 */       throw new IllegalArgumentException("invalid permission: " + paramPermission);
/*     */     }
/*  79 */     UnresolvedPermission unresolvedPermission = (UnresolvedPermission)paramPermission;
/*     */ 
/*     */     
/*  82 */     synchronized (this) {
/*  83 */       list = this.perms.get(unresolvedPermission.getName());
/*  84 */       if (list == null) {
/*  85 */         list = new ArrayList();
/*  86 */         this.perms.put(unresolvedPermission.getName(), list);
/*     */       } 
/*     */     } 
/*  89 */     synchronized (list) {
/*  90 */       list.add(unresolvedPermission);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<UnresolvedPermission> getUnresolvedPermissions(Permission paramPermission) {
/*  99 */     synchronized (this) {
/* 100 */       return this.perms.get(paramPermission.getClass().getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean implies(Permission paramPermission) {
/* 110 */     return false;
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
/* 121 */     ArrayList<Permission> arrayList = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/* 125 */     synchronized (this) {
/* 126 */       for (List<UnresolvedPermission> list : this.perms.values()) {
/* 127 */         synchronized (list) {
/* 128 */           arrayList.addAll(list);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return Collections.enumeration(arrayList);
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
/* 147 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("permissions", Hashtable.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 164 */     Hashtable<Object, Object> hashtable = new Hashtable<>(this.perms.size() * 2);
/*     */ 
/*     */     
/* 167 */     synchronized (this) {
/* 168 */       Set<Map.Entry<String, List<UnresolvedPermission>>> set = this.perms.entrySet();
/* 169 */       for (Map.Entry<String, List<UnresolvedPermission>> entry : set) {
/*     */         
/* 171 */         List list = (List)entry.getValue();
/* 172 */         Vector vector = new Vector(list.size());
/* 173 */         synchronized (list) {
/* 174 */           vector.addAll(list);
/*     */         } 
/*     */ 
/*     */         
/* 178 */         hashtable.put(entry.getKey(), vector);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 183 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 184 */     putField.put("permissions", hashtable);
/* 185 */     paramObjectOutputStream.writeFields();
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
/* 197 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     Hashtable hashtable = (Hashtable)getField.get("permissions", (Object)null);
/* 206 */     this.perms = new HashMap<>(hashtable.size() * 2);
/*     */ 
/*     */     
/* 209 */     Set set = hashtable.entrySet();
/* 210 */     for (Map.Entry entry : set) {
/*     */       
/* 212 */       Vector vector = (Vector)entry.getValue();
/* 213 */       ArrayList<UnresolvedPermission> arrayList = new ArrayList(vector.size());
/* 214 */       arrayList.addAll(vector);
/*     */ 
/*     */       
/* 217 */       this.perms.put((String)entry.getKey(), arrayList);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/UnresolvedPermissionCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */