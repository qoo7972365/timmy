/*     */ package javax.security.auth;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSource;
/*     */ import java.security.DomainCombiner;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.Permissions;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.security.Security;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.misc.JavaSecurityProtectionDomainAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubjectDomainCombiner
/*     */   implements DomainCombiner
/*     */ {
/*     */   private Subject subject;
/*  52 */   private WeakKeyValueMap<ProtectionDomain, ProtectionDomain> cachedPDs = new WeakKeyValueMap<>();
/*     */   
/*     */   private Set<Principal> principalSet;
/*     */   
/*     */   private Principal[] principals;
/*     */   
/*  58 */   private static final Debug debug = Debug.getInstance("combiner", "\t[SubjectDomainCombiner]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final boolean useJavaxPolicy = Policy.isCustomPolicySet(debug);
/*     */ 
/*     */   
/*  67 */   private static final boolean allowCaching = (useJavaxPolicy && 
/*  68 */     cachePolicy());
/*     */ 
/*     */   
/*  71 */   private static final JavaSecurityProtectionDomainAccess pdAccess = SharedSecrets.getJavaSecurityProtectionDomainAccess();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectDomainCombiner(Subject paramSubject) {
/*  83 */     this.subject = paramSubject;
/*     */     
/*  85 */     if (paramSubject.isReadOnly()) {
/*  86 */       this.principalSet = paramSubject.getPrincipals();
/*  87 */       this
/*  88 */         .principals = this.principalSet.<Principal>toArray(new Principal[this.principalSet.size()]);
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
/*     */   public Subject getSubject() {
/* 108 */     SecurityManager securityManager = System.getSecurityManager();
/* 109 */     if (securityManager != null) {
/* 110 */       securityManager.checkPermission(new AuthPermission("getSubjectFromDomainCombiner"));
/*     */     }
/*     */     
/* 113 */     return this.subject;
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
/*     */   public ProtectionDomain[] combine(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2) {
/* 164 */     if (debug != null) {
/* 165 */       if (this.subject == null) {
/* 166 */         debug.println("null subject");
/*     */       } else {
/* 168 */         final Subject s = this.subject;
/*     */         
/* 170 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */               public Void run() {
/* 172 */                 SubjectDomainCombiner.debug.println(s.toString());
/* 173 */                 return null;
/*     */               }
/*     */             });
/*     */       } 
/* 177 */       printInputDomains(paramArrayOfProtectionDomain1, paramArrayOfProtectionDomain2);
/*     */     } 
/*     */     
/* 180 */     if (paramArrayOfProtectionDomain1 == null || paramArrayOfProtectionDomain1.length == 0)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 190 */       return paramArrayOfProtectionDomain2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     paramArrayOfProtectionDomain1 = optimize(paramArrayOfProtectionDomain1);
/* 199 */     if (debug != null) {
/* 200 */       debug.println("after optimize");
/* 201 */       printInputDomains(paramArrayOfProtectionDomain1, paramArrayOfProtectionDomain2);
/*     */     } 
/*     */     
/* 204 */     if (paramArrayOfProtectionDomain1 == null && paramArrayOfProtectionDomain2 == null) {
/* 205 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 210 */     if (useJavaxPolicy) {
/* 211 */       return combineJavaxPolicy(paramArrayOfProtectionDomain1, paramArrayOfProtectionDomain2);
/*     */     }
/*     */     
/* 214 */     byte b1 = (paramArrayOfProtectionDomain1 == null) ? 0 : paramArrayOfProtectionDomain1.length;
/* 215 */     byte b2 = (paramArrayOfProtectionDomain2 == null) ? 0 : paramArrayOfProtectionDomain2.length;
/*     */ 
/*     */ 
/*     */     
/* 219 */     ProtectionDomain[] arrayOfProtectionDomain = new ProtectionDomain[b1 + b2];
/*     */     
/* 221 */     boolean bool = true;
/* 222 */     synchronized (this.cachedPDs) {
/* 223 */       if (!this.subject.isReadOnly() && 
/* 224 */         !this.subject.getPrincipals().equals(this.principalSet)) {
/*     */ 
/*     */         
/* 227 */         Set<Principal> set = this.subject.getPrincipals();
/* 228 */         synchronized (set) {
/* 229 */           this.principalSet = new HashSet<>(set);
/*     */         } 
/* 231 */         this
/* 232 */           .principals = this.principalSet.<Principal>toArray(new Principal[this.principalSet.size()]);
/* 233 */         this.cachedPDs.clear();
/*     */         
/* 235 */         if (debug != null) {
/* 236 */           debug.println("Subject mutated - clearing cache");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 241 */       for (byte b = 0; b < b1; b++) {
/* 242 */         ProtectionDomain protectionDomain2 = paramArrayOfProtectionDomain1[b];
/*     */         
/* 244 */         ProtectionDomain protectionDomain1 = this.cachedPDs.getValue(protectionDomain2);
/*     */         
/* 246 */         if (protectionDomain1 == null) {
/* 247 */           if (pdAccess.getStaticPermissionsField(protectionDomain2)) {
/*     */ 
/*     */             
/* 250 */             protectionDomain1 = new ProtectionDomain(protectionDomain2.getCodeSource(), protectionDomain2.getPermissions());
/*     */           }
/*     */           else {
/*     */             
/* 254 */             protectionDomain1 = new ProtectionDomain(protectionDomain2.getCodeSource(), protectionDomain2.getPermissions(), protectionDomain2.getClassLoader(), this.principals);
/*     */           } 
/*     */           
/* 257 */           this.cachedPDs.putValue(protectionDomain2, protectionDomain1);
/*     */         } else {
/* 259 */           bool = false;
/*     */         } 
/* 261 */         arrayOfProtectionDomain[b] = protectionDomain1;
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     if (debug != null) {
/* 266 */       debug.println("updated current: ");
/* 267 */       for (byte b = 0; b < b1; b++) {
/* 268 */         debug.println("\tupdated[" + b + "] = " + 
/* 269 */             printDomain(arrayOfProtectionDomain[b]));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 274 */     if (b2 > 0) {
/* 275 */       System.arraycopy(paramArrayOfProtectionDomain2, 0, arrayOfProtectionDomain, b1, b2);
/*     */ 
/*     */       
/* 278 */       if (!bool) {
/* 279 */         arrayOfProtectionDomain = optimize(arrayOfProtectionDomain);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 285 */     if (debug != null) {
/* 286 */       if (arrayOfProtectionDomain == null || arrayOfProtectionDomain.length == 0) {
/* 287 */         debug.println("returning null");
/*     */       } else {
/* 289 */         debug.println("combinedDomains: ");
/* 290 */         for (byte b = 0; b < arrayOfProtectionDomain.length; b++) {
/* 291 */           debug.println("newDomain " + b + ": " + 
/* 292 */               printDomain(arrayOfProtectionDomain[b]));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 298 */     if (arrayOfProtectionDomain == null || arrayOfProtectionDomain.length == 0) {
/* 299 */       return null;
/*     */     }
/* 301 */     return arrayOfProtectionDomain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProtectionDomain[] combineJavaxPolicy(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2) {
/* 312 */     if (!allowCaching)
/*     */     {
/* 314 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             public Void run()
/*     */             {
/* 318 */               Policy.getPolicy().refresh();
/* 319 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 325 */     byte b1 = (paramArrayOfProtectionDomain1 == null) ? 0 : paramArrayOfProtectionDomain1.length;
/* 326 */     byte b2 = (paramArrayOfProtectionDomain2 == null) ? 0 : paramArrayOfProtectionDomain2.length;
/*     */ 
/*     */ 
/*     */     
/* 330 */     ProtectionDomain[] arrayOfProtectionDomain = new ProtectionDomain[b1 + b2];
/*     */     
/* 332 */     synchronized (this.cachedPDs) {
/* 333 */       if (!this.subject.isReadOnly() && 
/* 334 */         !this.subject.getPrincipals().equals(this.principalSet)) {
/*     */ 
/*     */         
/* 337 */         Set<Principal> set = this.subject.getPrincipals();
/* 338 */         synchronized (set) {
/* 339 */           this.principalSet = new HashSet<>(set);
/*     */         } 
/* 341 */         this
/* 342 */           .principals = this.principalSet.<Principal>toArray(new Principal[this.principalSet.size()]);
/* 343 */         this.cachedPDs.clear();
/*     */         
/* 345 */         if (debug != null) {
/* 346 */           debug.println("Subject mutated - clearing cache");
/*     */         }
/*     */       } 
/*     */       
/* 350 */       for (byte b = 0; b < b1; b++) {
/* 351 */         ProtectionDomain protectionDomain1 = paramArrayOfProtectionDomain1[b];
/* 352 */         ProtectionDomain protectionDomain2 = this.cachedPDs.getValue(protectionDomain1);
/*     */         
/* 354 */         if (protectionDomain2 == null) {
/* 355 */           if (pdAccess.getStaticPermissionsField(protectionDomain1)) {
/*     */ 
/*     */             
/* 358 */             protectionDomain2 = new ProtectionDomain(protectionDomain1.getCodeSource(), protectionDomain1.getPermissions());
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 367 */             Permissions permissions = new Permissions();
/* 368 */             PermissionCollection permissionCollection1 = protectionDomain1.getPermissions();
/*     */             
/* 370 */             if (permissionCollection1 != null) {
/* 371 */               synchronized (permissionCollection1) {
/* 372 */                 Enumeration<Permission> enumeration = permissionCollection1.elements();
/* 373 */                 while (enumeration.hasMoreElements()) {
/*     */                   
/* 375 */                   Permission permission = enumeration.nextElement();
/* 376 */                   permissions.add(permission);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/* 382 */             final CodeSource finalCs = protectionDomain1.getCodeSource();
/* 383 */             final Subject finalS = this.subject;
/*     */ 
/*     */             
/* 386 */             PermissionCollection permissionCollection2 = AccessController.<PermissionCollection>doPrivileged(new PrivilegedAction<PermissionCollection>()
/*     */                 {
/*     */                   public PermissionCollection run() {
/* 389 */                     return 
/* 390 */                       Policy.getPolicy()
/* 391 */                       .getPermissions(finalS, finalCs);
/*     */                   }
/*     */                 });
/*     */ 
/*     */ 
/*     */             
/* 397 */             synchronized (permissionCollection2) {
/* 398 */               Enumeration<Permission> enumeration = permissionCollection2.elements();
/* 399 */               while (enumeration.hasMoreElements()) {
/* 400 */                 Permission permission = enumeration.nextElement();
/* 401 */                 if (!permissions.implies(permission)) {
/* 402 */                   permissions.add(permission);
/* 403 */                   if (debug != null) {
/* 404 */                     debug.println("Adding perm " + permission + "\n");
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 410 */             protectionDomain2 = new ProtectionDomain(codeSource, permissions, protectionDomain1.getClassLoader(), this.principals);
/*     */           } 
/* 412 */           if (allowCaching)
/* 413 */             this.cachedPDs.putValue(protectionDomain1, protectionDomain2); 
/*     */         } 
/* 415 */         arrayOfProtectionDomain[b] = protectionDomain2;
/*     */       } 
/*     */     } 
/*     */     
/* 419 */     if (debug != null) {
/* 420 */       debug.println("updated current: ");
/* 421 */       for (byte b = 0; b < b1; b++) {
/* 422 */         debug.println("\tupdated[" + b + "] = " + arrayOfProtectionDomain[b]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 427 */     if (b2 > 0) {
/* 428 */       System.arraycopy(paramArrayOfProtectionDomain2, 0, arrayOfProtectionDomain, b1, b2);
/*     */     }
/*     */     
/* 431 */     if (debug != null) {
/* 432 */       if (arrayOfProtectionDomain == null || arrayOfProtectionDomain.length == 0) {
/* 433 */         debug.println("returning null");
/*     */       } else {
/* 435 */         debug.println("combinedDomains: ");
/* 436 */         for (byte b = 0; b < arrayOfProtectionDomain.length; b++) {
/* 437 */           debug.println("newDomain " + b + ": " + arrayOfProtectionDomain[b]
/* 438 */               .toString());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 444 */     if (arrayOfProtectionDomain == null || arrayOfProtectionDomain.length == 0) {
/* 445 */       return null;
/*     */     }
/* 447 */     return arrayOfProtectionDomain;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtectionDomain[] optimize(ProtectionDomain[] paramArrayOfProtectionDomain) {
/* 452 */     if (paramArrayOfProtectionDomain == null || paramArrayOfProtectionDomain.length == 0) {
/* 453 */       return null;
/*     */     }
/* 455 */     ProtectionDomain[] arrayOfProtectionDomain = new ProtectionDomain[paramArrayOfProtectionDomain.length];
/*     */     
/* 457 */     byte b1 = 0;
/* 458 */     for (byte b2 = 0; b2 < paramArrayOfProtectionDomain.length; b2++) {
/*     */       ProtectionDomain protectionDomain;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 467 */       if ((protectionDomain = paramArrayOfProtectionDomain[b2]) != null) {
/*     */ 
/*     */         
/* 470 */         boolean bool = false;
/* 471 */         for (byte b = 0; b < b1 && !bool; b++) {
/* 472 */           bool = (arrayOfProtectionDomain[b] == protectionDomain) ? true : false;
/*     */         }
/* 474 */         if (!bool) {
/* 475 */           arrayOfProtectionDomain[b1++] = protectionDomain;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 481 */     if (b1 > 0 && b1 < paramArrayOfProtectionDomain.length) {
/* 482 */       ProtectionDomain[] arrayOfProtectionDomain1 = new ProtectionDomain[b1];
/* 483 */       System.arraycopy(arrayOfProtectionDomain, 0, arrayOfProtectionDomain1, 0, arrayOfProtectionDomain1.length);
/* 484 */       arrayOfProtectionDomain = arrayOfProtectionDomain1;
/*     */     } 
/*     */     
/* 487 */     return (b1 == 0 || arrayOfProtectionDomain.length == 0) ? null : arrayOfProtectionDomain;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean cachePolicy() {
/* 492 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/* 494 */             return Security.getProperty("cache.auth.policy");
/*     */           }
/*     */         });
/* 497 */     if (str != null) {
/* 498 */       return Boolean.parseBoolean(str);
/*     */     }
/*     */ 
/*     */     
/* 502 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void printInputDomains(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2) {
/* 507 */     if (paramArrayOfProtectionDomain1 == null || paramArrayOfProtectionDomain1.length == 0) {
/* 508 */       debug.println("currentDomains null or 0 length");
/*     */     } else {
/* 510 */       for (byte b = 0; paramArrayOfProtectionDomain1 != null && b < paramArrayOfProtectionDomain1.length; 
/* 511 */         b++) {
/* 512 */         if (paramArrayOfProtectionDomain1[b] == null) {
/* 513 */           debug.println("currentDomain " + b + ": SystemDomain");
/*     */         } else {
/* 515 */           debug.println("currentDomain " + b + ": " + 
/* 516 */               printDomain(paramArrayOfProtectionDomain1[b]));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 521 */     if (paramArrayOfProtectionDomain2 == null || paramArrayOfProtectionDomain2.length == 0) {
/* 522 */       debug.println("assignedDomains null or 0 length");
/*     */     } else {
/* 524 */       debug.println("assignedDomains = ");
/* 525 */       for (byte b = 0; paramArrayOfProtectionDomain2 != null && b < paramArrayOfProtectionDomain2.length; 
/* 526 */         b++) {
/* 527 */         if (paramArrayOfProtectionDomain2[b] == null) {
/* 528 */           debug.println("assignedDomain " + b + ": SystemDomain");
/*     */         } else {
/* 530 */           debug.println("assignedDomain " + b + ": " + 
/* 531 */               printDomain(paramArrayOfProtectionDomain2[b]));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String printDomain(final ProtectionDomain pd) {
/* 538 */     if (pd == null) {
/* 539 */       return "null";
/*     */     }
/* 541 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/* 543 */             return pd.toString();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WeakKeyValueMap<K, V>
/*     */     extends WeakHashMap<K, WeakReference<V>>
/*     */   {
/*     */     private WeakKeyValueMap() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V getValue(K param1K) {
/* 568 */       WeakReference<V> weakReference = (WeakReference)get(param1K);
/* 569 */       if (weakReference != null) {
/* 570 */         return weakReference.get();
/*     */       }
/* 572 */       return null;
/*     */     }
/*     */     
/*     */     public V putValue(K param1K, V param1V) {
/* 576 */       WeakReference<V> weakReference = (WeakReference)put(param1K, (V)new WeakReference<>(param1V));
/* 577 */       if (weakReference != null) {
/* 578 */         return weakReference.get();
/*     */       }
/* 580 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/SubjectDomainCombiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */