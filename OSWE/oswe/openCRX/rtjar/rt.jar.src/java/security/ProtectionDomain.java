/*     */ package java.security;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.misc.JavaSecurityAccess;
/*     */ import sun.misc.JavaSecurityProtectionDomainAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtectionDomain
/*     */ {
/*     */   private CodeSource codesource;
/*     */   private ClassLoader classloader;
/*     */   private Principal[] principals;
/*     */   private PermissionCollection permissions;
/*     */   
/*     */   private static class JavaSecurityAccessImpl
/*     */     implements JavaSecurityAccess
/*     */   {
/*     */     private JavaSecurityAccessImpl() {}
/*     */     
/*     */     public <T> T doIntersectionPrivilege(PrivilegedAction<T> param1PrivilegedAction, AccessControlContext param1AccessControlContext1, AccessControlContext param1AccessControlContext2) {
/*  70 */       if (param1PrivilegedAction == null) {
/*  71 */         throw new NullPointerException();
/*     */       }
/*     */       
/*  74 */       return AccessController.doPrivileged(param1PrivilegedAction, 
/*     */           
/*  76 */           getCombinedACC(param1AccessControlContext2, param1AccessControlContext1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T doIntersectionPrivilege(PrivilegedAction<T> param1PrivilegedAction, AccessControlContext param1AccessControlContext) {
/*  84 */       return doIntersectionPrivilege(param1PrivilegedAction, 
/*  85 */           AccessController.getContext(), param1AccessControlContext);
/*     */     }
/*     */     
/*     */     private static AccessControlContext getCombinedACC(AccessControlContext param1AccessControlContext1, AccessControlContext param1AccessControlContext2) {
/*  89 */       AccessControlContext accessControlContext = new AccessControlContext(param1AccessControlContext1, param1AccessControlContext2.getCombiner(), true);
/*     */       
/*  91 */       return (new AccessControlContext(param1AccessControlContext2.getContext(), accessControlContext)).optimize();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  97 */     SharedSecrets.setJavaSecurityAccess(new JavaSecurityAccessImpl());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasAllPerm = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean staticPermissions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   final Key key = new Key();
/*     */   
/* 124 */   private static final Debug debug = Debug.getInstance("domain");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtectionDomain(CodeSource paramCodeSource, PermissionCollection paramPermissionCollection) {
/* 138 */     this.codesource = paramCodeSource;
/* 139 */     if (paramPermissionCollection != null) {
/* 140 */       this.permissions = paramPermissionCollection;
/* 141 */       this.permissions.setReadOnly();
/* 142 */       if (paramPermissionCollection instanceof Permissions && ((Permissions)paramPermissionCollection).allPermission != null)
/*     */       {
/* 144 */         this.hasAllPerm = true;
/*     */       }
/*     */     } 
/* 147 */     this.classloader = null;
/* 148 */     this.principals = new Principal[0];
/* 149 */     this.staticPermissions = true;
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
/*     */   public ProtectionDomain(CodeSource paramCodeSource, PermissionCollection paramPermissionCollection, ClassLoader paramClassLoader, Principal[] paramArrayOfPrincipal) {
/* 185 */     this.codesource = paramCodeSource;
/* 186 */     if (paramPermissionCollection != null) {
/* 187 */       this.permissions = paramPermissionCollection;
/* 188 */       this.permissions.setReadOnly();
/* 189 */       if (paramPermissionCollection instanceof Permissions && ((Permissions)paramPermissionCollection).allPermission != null)
/*     */       {
/* 191 */         this.hasAllPerm = true;
/*     */       }
/*     */     } 
/* 194 */     this.classloader = paramClassLoader;
/* 195 */     this.principals = (paramArrayOfPrincipal != null) ? (Principal[])paramArrayOfPrincipal.clone() : new Principal[0];
/*     */     
/* 197 */     this.staticPermissions = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CodeSource getCodeSource() {
/* 206 */     return this.codesource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ClassLoader getClassLoader() {
/* 217 */     return this.classloader;
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
/*     */   public final Principal[] getPrincipals() {
/* 229 */     return (Principal[])this.principals.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PermissionCollection getPermissions() {
/* 240 */     return this.permissions;
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
/*     */   public boolean implies(Permission paramPermission) {
/* 272 */     if (this.hasAllPerm)
/*     */     {
/*     */       
/* 275 */       return true;
/*     */     }
/*     */     
/* 278 */     if (!this.staticPermissions && 
/* 279 */       Policy.getPolicyNoCheck().implies(this, paramPermission))
/* 280 */       return true; 
/* 281 */     if (this.permissions != null) {
/* 282 */       return this.permissions.implies(paramPermission);
/*     */     }
/* 284 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean impliesCreateAccessControlContext() {
/* 289 */     return implies(SecurityConstants.CREATE_ACC_PERMISSION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 296 */     String str = "<no principals>";
/* 297 */     if (this.principals != null && this.principals.length > 0) {
/* 298 */       StringBuilder stringBuilder = new StringBuilder("(principals ");
/*     */       
/* 300 */       for (byte b = 0; b < this.principals.length; b++) {
/* 301 */         stringBuilder.append(this.principals[b].getClass().getName() + " \"" + this.principals[b]
/* 302 */             .getName() + "\"");
/*     */         
/* 304 */         if (b < this.principals.length - 1) {
/* 305 */           stringBuilder.append(",\n");
/*     */         } else {
/* 307 */           stringBuilder.append(")\n");
/*     */         } 
/* 309 */       }  str = stringBuilder.toString();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     PermissionCollection permissionCollection = (Policy.isSet() && seeAllp()) ? mergePermissions() : getPermissions();
/*     */     
/* 318 */     return "ProtectionDomain  " + this.codesource + "\n " + this.classloader + "\n " + str + "\n " + permissionCollection + "\n";
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
/*     */   private static boolean seeAllp() {
/* 341 */     SecurityManager securityManager = System.getSecurityManager();
/*     */     
/* 343 */     if (securityManager == null) {
/* 344 */       return true;
/*     */     }
/* 346 */     if (debug != null) {
/* 347 */       if (securityManager.getClass().getClassLoader() == null && 
/* 348 */         Policy.getPolicyNoCheck().getClass().getClassLoader() == null)
/*     */       {
/* 350 */         return true;
/*     */       }
/*     */     } else {
/*     */       try {
/* 354 */         securityManager.checkPermission(SecurityConstants.GET_POLICY_PERMISSION);
/* 355 */         return true;
/* 356 */       } catch (SecurityException securityException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 362 */     return false;
/*     */   }
/*     */   
/*     */   private PermissionCollection mergePermissions() {
/* 366 */     if (this.staticPermissions) {
/* 367 */       return this.permissions;
/*     */     }
/*     */ 
/*     */     
/* 371 */     PermissionCollection permissionCollection = AccessController.<PermissionCollection>doPrivileged(new PrivilegedAction<PermissionCollection>() {
/*     */           public PermissionCollection run() {
/* 373 */             Policy policy = Policy.getPolicyNoCheck();
/* 374 */             return policy.getPermissions(ProtectionDomain.this);
/*     */           }
/*     */         });
/*     */     
/* 378 */     Permissions permissions = new Permissions();
/* 379 */     byte b1 = 32;
/* 380 */     byte b2 = 8;
/*     */     
/* 382 */     ArrayList<Permission> arrayList1 = new ArrayList(b2);
/* 383 */     ArrayList<Permission> arrayList2 = new ArrayList(b1);
/*     */ 
/*     */ 
/*     */     
/* 387 */     if (this.permissions != null) {
/* 388 */       synchronized (this.permissions) {
/* 389 */         Enumeration<Permission> enumeration = this.permissions.elements();
/* 390 */         while (enumeration.hasMoreElements()) {
/* 391 */           arrayList1.add(enumeration.nextElement());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 398 */     if (permissionCollection != null) {
/* 399 */       synchronized (permissionCollection) {
/* 400 */         Enumeration<Permission> enumeration = permissionCollection.elements();
/* 401 */         while (enumeration.hasMoreElements()) {
/* 402 */           arrayList2.add(enumeration.nextElement());
/* 403 */           b2++;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 408 */     if (permissionCollection != null && this.permissions != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 413 */       synchronized (this.permissions) {
/* 414 */         Enumeration<Permission> enumeration = this.permissions.elements();
/* 415 */         while (enumeration.hasMoreElements()) {
/* 416 */           Permission permission = enumeration.nextElement();
/* 417 */           Class<?> clazz = permission.getClass();
/* 418 */           String str1 = permission.getActions();
/* 419 */           String str2 = permission.getName();
/* 420 */           for (byte b = 0; b < arrayList2.size(); b++) {
/* 421 */             Permission permission1 = arrayList2.get(b);
/* 422 */             if (clazz.isInstance(permission1))
/*     */             {
/*     */ 
/*     */               
/* 426 */               if (str2.equals(permission1.getName()) && str1
/* 427 */                 .equals(permission1.getActions())) {
/* 428 */                 arrayList2.remove(b);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 437 */     if (permissionCollection != null)
/*     */     {
/*     */ 
/*     */       
/* 441 */       for (int i = arrayList2.size() - 1; i >= 0; i--) {
/* 442 */         permissions.add(arrayList2.get(i));
/*     */       }
/*     */     }
/* 445 */     if (this.permissions != null) {
/* 446 */       for (int i = arrayList1.size() - 1; i >= 0; i--) {
/* 447 */         permissions.add(arrayList1.get(i));
/*     */       }
/*     */     }
/*     */     
/* 451 */     return permissions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final class Key {}
/*     */ 
/*     */   
/*     */   static {
/* 460 */     SharedSecrets.setJavaSecurityProtectionDomainAccess(new JavaSecurityProtectionDomainAccess()
/*     */         {
/*     */           public JavaSecurityProtectionDomainAccess.ProtectionDomainCache getProtectionDomainCache()
/*     */           {
/* 464 */             return new JavaSecurityProtectionDomainAccess.ProtectionDomainCache()
/*     */               {
/*     */                 
/* 467 */                 private final Map<ProtectionDomain.Key, PermissionCollection> map = Collections.synchronizedMap(new WeakHashMap<>());
/*     */                 
/*     */                 public void put(ProtectionDomain param2ProtectionDomain, PermissionCollection param2PermissionCollection) {
/* 470 */                   this.map.put((param2ProtectionDomain == null) ? null : param2ProtectionDomain.key, param2PermissionCollection);
/*     */                 }
/*     */                 public PermissionCollection get(ProtectionDomain param2ProtectionDomain) {
/* 473 */                   return (param2ProtectionDomain == null) ? this.map.get(null) : this.map.get(param2ProtectionDomain.key);
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean getStaticPermissionsField(ProtectionDomain param1ProtectionDomain) {
/* 480 */             return param1ProtectionDomain.staticPermissions;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/ProtectionDomain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */