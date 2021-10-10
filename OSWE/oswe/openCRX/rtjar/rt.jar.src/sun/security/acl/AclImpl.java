/*     */ package sun.security.acl;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import java.security.acl.Acl;
/*     */ import java.security.acl.AclEntry;
/*     */ import java.security.acl.Group;
/*     */ import java.security.acl.NotOwnerException;
/*     */ import java.security.acl.Permission;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
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
/*     */ public class AclImpl
/*     */   extends OwnerImpl
/*     */   implements Acl
/*     */ {
/*  43 */   private Hashtable<Principal, AclEntry> allowedUsersTable = new Hashtable<>(23);
/*     */   
/*  45 */   private Hashtable<Principal, AclEntry> allowedGroupsTable = new Hashtable<>(23);
/*     */   
/*  47 */   private Hashtable<Principal, AclEntry> deniedUsersTable = new Hashtable<>(23);
/*     */   
/*  49 */   private Hashtable<Principal, AclEntry> deniedGroupsTable = new Hashtable<>(23);
/*     */   
/*  51 */   private String aclName = null;
/*  52 */   private Vector<Permission> zeroSet = new Vector<>(1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AclImpl(Principal paramPrincipal, String paramString) {
/*  59 */     super(paramPrincipal);
/*     */     try {
/*  61 */       setName(paramPrincipal, paramString);
/*  62 */     } catch (Exception exception) {}
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
/*     */   public void setName(Principal paramPrincipal, String paramString) throws NotOwnerException {
/*  75 */     if (!isOwner(paramPrincipal)) {
/*  76 */       throw new NotOwnerException();
/*     */     }
/*  78 */     this.aclName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  86 */     return this.aclName;
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
/*     */   public synchronized boolean addEntry(Principal paramPrincipal, AclEntry paramAclEntry) throws NotOwnerException {
/* 106 */     if (!isOwner(paramPrincipal)) {
/* 107 */       throw new NotOwnerException();
/*     */     }
/* 109 */     Hashtable<Principal, AclEntry> hashtable = findTable(paramAclEntry);
/* 110 */     Principal principal = paramAclEntry.getPrincipal();
/*     */     
/* 112 */     if (hashtable.get(principal) != null) {
/* 113 */       return false;
/*     */     }
/* 115 */     hashtable.put(principal, paramAclEntry);
/* 116 */     return true;
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
/*     */   public synchronized boolean removeEntry(Principal paramPrincipal, AclEntry paramAclEntry) throws NotOwnerException {
/* 132 */     if (!isOwner(paramPrincipal)) {
/* 133 */       throw new NotOwnerException();
/*     */     }
/* 135 */     Hashtable<Principal, AclEntry> hashtable = findTable(paramAclEntry);
/* 136 */     Principal principal = paramAclEntry.getPrincipal();
/*     */     
/* 138 */     AclEntry aclEntry = hashtable.remove(principal);
/* 139 */     return (aclEntry != null);
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
/*     */   public synchronized Enumeration<Permission> getPermissions(Principal paramPrincipal) {
/* 185 */     Enumeration<Permission> enumeration3 = subtract(getGroupPositive(paramPrincipal), getGroupNegative(paramPrincipal));
/*     */     
/* 187 */     Enumeration<Permission> enumeration4 = subtract(getGroupNegative(paramPrincipal), getGroupPositive(paramPrincipal));
/*     */     
/* 189 */     Enumeration<Permission> enumeration1 = subtract(getIndividualPositive(paramPrincipal), getIndividualNegative(paramPrincipal));
/*     */     
/* 191 */     Enumeration<Permission> enumeration2 = subtract(getIndividualNegative(paramPrincipal), getIndividualPositive(paramPrincipal));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     Enumeration<Permission> enumeration5 = subtract(enumeration3, enumeration2);
/*     */     
/* 200 */     Enumeration<Permission> enumeration6 = union(enumeration1, enumeration5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     enumeration1 = subtract(getIndividualPositive(paramPrincipal), getIndividualNegative(paramPrincipal));
/*     */     
/* 208 */     enumeration2 = subtract(getIndividualNegative(paramPrincipal), getIndividualPositive(paramPrincipal));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     enumeration5 = subtract(enumeration4, enumeration1);
/* 215 */     Enumeration<Permission> enumeration7 = union(enumeration2, enumeration5);
/*     */     
/* 217 */     return subtract(enumeration6, enumeration7);
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
/*     */   public boolean checkPermission(Principal paramPrincipal, Permission paramPermission) {
/* 233 */     Enumeration<Permission> enumeration = getPermissions(paramPrincipal);
/* 234 */     while (enumeration.hasMoreElements()) {
/* 235 */       Permission permission = enumeration.nextElement();
/* 236 */       if (permission.equals(paramPermission))
/* 237 */         return true; 
/*     */     } 
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Enumeration<AclEntry> entries() {
/* 246 */     return new AclEnumerator(this, this.allowedUsersTable, this.allowedGroupsTable, this.deniedUsersTable, this.deniedGroupsTable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 256 */     StringBuffer stringBuffer = new StringBuffer();
/* 257 */     Enumeration<AclEntry> enumeration = entries();
/* 258 */     while (enumeration.hasMoreElements()) {
/* 259 */       AclEntry aclEntry = enumeration.nextElement();
/* 260 */       stringBuffer.append(aclEntry.toString().trim());
/* 261 */       stringBuffer.append("\n");
/*     */     } 
/*     */     
/* 264 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable<Principal, AclEntry> findTable(AclEntry paramAclEntry) {
/* 275 */     Hashtable<Principal, AclEntry> hashtable = null;
/*     */     
/* 277 */     Principal principal = paramAclEntry.getPrincipal();
/* 278 */     if (principal instanceof Group) {
/* 279 */       if (paramAclEntry.isNegative()) {
/* 280 */         hashtable = this.deniedGroupsTable;
/*     */       } else {
/* 282 */         hashtable = this.allowedGroupsTable;
/*     */       } 
/* 284 */     } else if (paramAclEntry.isNegative()) {
/* 285 */       hashtable = this.deniedUsersTable;
/*     */     } else {
/* 287 */       hashtable = this.allowedUsersTable;
/*     */     } 
/* 289 */     return hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Enumeration<Permission> union(Enumeration<Permission> paramEnumeration1, Enumeration<Permission> paramEnumeration2) {
/* 297 */     Vector<Permission> vector = new Vector(20, 20);
/*     */     
/* 299 */     while (paramEnumeration1.hasMoreElements()) {
/* 300 */       vector.addElement(paramEnumeration1.nextElement());
/*     */     }
/* 302 */     while (paramEnumeration2.hasMoreElements()) {
/* 303 */       Permission permission = paramEnumeration2.nextElement();
/* 304 */       if (!vector.contains(permission)) {
/* 305 */         vector.addElement(permission);
/*     */       }
/*     */     } 
/* 308 */     return vector.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Enumeration<Permission> subtract(Enumeration<Permission> paramEnumeration1, Enumeration<Permission> paramEnumeration2) {
/* 316 */     Vector<Permission> vector = new Vector(20, 20);
/*     */     
/* 318 */     while (paramEnumeration1.hasMoreElements()) {
/* 319 */       vector.addElement(paramEnumeration1.nextElement());
/*     */     }
/* 321 */     while (paramEnumeration2.hasMoreElements()) {
/* 322 */       Permission permission = paramEnumeration2.nextElement();
/* 323 */       if (vector.contains(permission)) {
/* 324 */         vector.removeElement(permission);
/*     */       }
/*     */     } 
/* 327 */     return vector.elements();
/*     */   }
/*     */   
/*     */   private Enumeration<Permission> getGroupPositive(Principal paramPrincipal) {
/* 331 */     Enumeration<Permission> enumeration = this.zeroSet.elements();
/* 332 */     Enumeration<Principal> enumeration1 = this.allowedGroupsTable.keys();
/* 333 */     while (enumeration1.hasMoreElements()) {
/* 334 */       Group group = (Group)enumeration1.nextElement();
/* 335 */       if (group.isMember(paramPrincipal)) {
/* 336 */         AclEntry aclEntry = this.allowedGroupsTable.get(group);
/* 337 */         enumeration = union(aclEntry.permissions(), enumeration);
/*     */       } 
/*     */     } 
/* 340 */     return enumeration;
/*     */   }
/*     */   
/*     */   private Enumeration<Permission> getGroupNegative(Principal paramPrincipal) {
/* 344 */     Enumeration<Permission> enumeration = this.zeroSet.elements();
/* 345 */     Enumeration<Principal> enumeration1 = this.deniedGroupsTable.keys();
/* 346 */     while (enumeration1.hasMoreElements()) {
/* 347 */       Group group = (Group)enumeration1.nextElement();
/* 348 */       if (group.isMember(paramPrincipal)) {
/* 349 */         AclEntry aclEntry = this.deniedGroupsTable.get(group);
/* 350 */         enumeration = union(aclEntry.permissions(), enumeration);
/*     */       } 
/*     */     } 
/* 353 */     return enumeration;
/*     */   }
/*     */   
/*     */   private Enumeration<Permission> getIndividualPositive(Principal paramPrincipal) {
/* 357 */     Enumeration<Permission> enumeration = this.zeroSet.elements();
/* 358 */     AclEntry aclEntry = this.allowedUsersTable.get(paramPrincipal);
/* 359 */     if (aclEntry != null)
/* 360 */       enumeration = aclEntry.permissions(); 
/* 361 */     return enumeration;
/*     */   }
/*     */   
/*     */   private Enumeration<Permission> getIndividualNegative(Principal paramPrincipal) {
/* 365 */     Enumeration<Permission> enumeration = this.zeroSet.elements();
/* 366 */     AclEntry aclEntry = this.deniedUsersTable.get(paramPrincipal);
/* 367 */     if (aclEntry != null)
/* 368 */       enumeration = aclEntry.permissions(); 
/* 369 */     return enumeration;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/acl/AclImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */