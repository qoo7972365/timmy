/*     */ package java.security;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AccessControlContext
/*     */ {
/*     */   private ProtectionDomain[] context;
/*     */   private boolean isPrivileged;
/*     */   private boolean isAuthorized = false;
/*     */   private AccessControlContext privilegedContext;
/*  89 */   private DomainCombiner combiner = null;
/*     */   
/*     */   private Permission[] permissions;
/*     */   
/*     */   private AccessControlContext parent;
/*     */   
/*     */   private boolean isWrapped;
/*     */   
/*     */   private boolean isLimited;
/*     */   
/*     */   private ProtectionDomain[] limitedContext;
/*     */   private static boolean debugInit = false;
/* 101 */   private static Debug debug = null;
/*     */ 
/*     */   
/*     */   static Debug getDebug() {
/* 105 */     if (debugInit) {
/* 106 */       return debug;
/*     */     }
/* 108 */     if (Policy.isSet()) {
/* 109 */       debug = Debug.getInstance("access");
/* 110 */       debugInit = true;
/*     */     } 
/* 112 */     return debug;
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
/*     */   public AccessControlContext(ProtectionDomain[] paramArrayOfProtectionDomain) {
/* 128 */     if (paramArrayOfProtectionDomain.length == 0) {
/* 129 */       this.context = null;
/* 130 */     } else if (paramArrayOfProtectionDomain.length == 1) {
/* 131 */       if (paramArrayOfProtectionDomain[0] != null) {
/* 132 */         this.context = (ProtectionDomain[])paramArrayOfProtectionDomain.clone();
/*     */       } else {
/* 134 */         this.context = null;
/*     */       } 
/*     */     } else {
/* 137 */       ArrayList<ProtectionDomain> arrayList = new ArrayList(paramArrayOfProtectionDomain.length);
/* 138 */       for (byte b = 0; b < paramArrayOfProtectionDomain.length; b++) {
/* 139 */         if (paramArrayOfProtectionDomain[b] != null && !arrayList.contains(paramArrayOfProtectionDomain[b]))
/* 140 */           arrayList.add(paramArrayOfProtectionDomain[b]); 
/*     */       } 
/* 142 */       if (!arrayList.isEmpty()) {
/* 143 */         this.context = new ProtectionDomain[arrayList.size()];
/* 144 */         this.context = arrayList.<ProtectionDomain>toArray(this.context);
/*     */       } 
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
/*     */   public AccessControlContext(AccessControlContext paramAccessControlContext, DomainCombiner paramDomainCombiner) {
/* 175 */     this(paramAccessControlContext, paramDomainCombiner, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AccessControlContext(AccessControlContext paramAccessControlContext, DomainCombiner paramDomainCombiner, boolean paramBoolean) {
/* 186 */     if (!paramBoolean) {
/* 187 */       SecurityManager securityManager = System.getSecurityManager();
/* 188 */       if (securityManager != null) {
/* 189 */         securityManager.checkPermission(SecurityConstants.CREATE_ACC_PERMISSION);
/* 190 */         this.isAuthorized = true;
/*     */       } 
/*     */     } else {
/* 193 */       this.isAuthorized = true;
/*     */     } 
/*     */     
/* 196 */     this.context = paramAccessControlContext.context;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     this.combiner = paramDomainCombiner;
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
/*     */   AccessControlContext(ProtectionDomain paramProtectionDomain, DomainCombiner paramDomainCombiner, AccessControlContext paramAccessControlContext1, AccessControlContext paramAccessControlContext2, Permission[] paramArrayOfPermission) {
/* 221 */     ProtectionDomain[] arrayOfProtectionDomain = null;
/* 222 */     if (paramProtectionDomain != null) {
/* 223 */       arrayOfProtectionDomain = new ProtectionDomain[] { paramProtectionDomain };
/*     */     }
/* 225 */     if (paramAccessControlContext2 != null) {
/* 226 */       if (paramDomainCombiner != null) {
/* 227 */         this.context = paramDomainCombiner.combine(arrayOfProtectionDomain, paramAccessControlContext2.context);
/*     */       } else {
/* 229 */         this.context = combine(arrayOfProtectionDomain, paramAccessControlContext2.context);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 235 */     else if (paramDomainCombiner != null) {
/* 236 */       this.context = paramDomainCombiner.combine(arrayOfProtectionDomain, null);
/*     */     } else {
/* 238 */       this.context = combine(arrayOfProtectionDomain, null);
/*     */     } 
/*     */     
/* 241 */     this.combiner = paramDomainCombiner;
/*     */     
/* 243 */     Permission[] arrayOfPermission = null;
/* 244 */     if (paramArrayOfPermission != null) {
/* 245 */       arrayOfPermission = new Permission[paramArrayOfPermission.length];
/* 246 */       for (byte b = 0; b < paramArrayOfPermission.length; b++) {
/* 247 */         if (paramArrayOfPermission[b] == null) {
/* 248 */           throw new NullPointerException("permission can't be null");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 255 */         if (paramArrayOfPermission[b].getClass() == AllPermission.class) {
/* 256 */           paramAccessControlContext1 = null;
/*     */         }
/* 258 */         arrayOfPermission[b] = paramArrayOfPermission[b];
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     if (paramAccessControlContext1 != null) {
/* 272 */       this.limitedContext = combine(paramAccessControlContext1.context, paramAccessControlContext1.limitedContext);
/* 273 */       this.isLimited = true;
/* 274 */       this.isWrapped = true;
/* 275 */       this.permissions = arrayOfPermission;
/* 276 */       this.parent = paramAccessControlContext1;
/* 277 */       this.privilegedContext = paramAccessControlContext2;
/*     */     } 
/* 279 */     this.isAuthorized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AccessControlContext(ProtectionDomain[] paramArrayOfProtectionDomain, boolean paramBoolean) {
/* 290 */     this.context = paramArrayOfProtectionDomain;
/* 291 */     this.isPrivileged = paramBoolean;
/* 292 */     this.isAuthorized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AccessControlContext(ProtectionDomain[] paramArrayOfProtectionDomain, AccessControlContext paramAccessControlContext) {
/* 301 */     this.context = paramArrayOfProtectionDomain;
/* 302 */     this.privilegedContext = paramAccessControlContext;
/* 303 */     this.isPrivileged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ProtectionDomain[] getContext() {
/* 310 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isPrivileged() {
/* 318 */     return this.isPrivileged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DomainCombiner getAssignedCombiner() {
/*     */     AccessControlContext accessControlContext;
/* 326 */     if (this.isPrivileged) {
/* 327 */       accessControlContext = this.privilegedContext;
/*     */     } else {
/* 329 */       accessControlContext = AccessController.getInheritedAccessControlContext();
/*     */     } 
/* 331 */     if (accessControlContext != null) {
/* 332 */       return accessControlContext.combiner;
/*     */     }
/* 334 */     return null;
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
/*     */   public DomainCombiner getDomainCombiner() {
/* 354 */     SecurityManager securityManager = System.getSecurityManager();
/* 355 */     if (securityManager != null) {
/* 356 */       securityManager.checkPermission(SecurityConstants.GET_COMBINER_PERMISSION);
/*     */     }
/* 358 */     return getCombiner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DomainCombiner getCombiner() {
/* 365 */     return this.combiner;
/*     */   }
/*     */   
/*     */   boolean isAuthorized() {
/* 369 */     return this.isAuthorized;
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
/*     */   public void checkPermission(Permission paramPermission) throws AccessControlException {
/* 394 */     int i = 0;
/*     */     
/* 396 */     if (paramPermission == null) {
/* 397 */       throw new NullPointerException("permission can't be null");
/*     */     }
/* 399 */     if (getDebug() != null) {
/*     */       
/* 401 */       i = !Debug.isOn("codebase=") ? 1 : 0;
/* 402 */       if (!i)
/*     */       {
/*     */         
/* 405 */         for (byte b1 = 0; this.context != null && b1 < this.context.length; b1++) {
/* 406 */           if (this.context[b1].getCodeSource() != null && this.context[b1]
/* 407 */             .getCodeSource().getLocation() != null && 
/* 408 */             Debug.isOn("codebase=" + this.context[b1].getCodeSource().getLocation().toString())) {
/* 409 */             i = 1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 415 */       i &= (!Debug.isOn("permission=") || 
/* 416 */         Debug.isOn("permission=" + paramPermission.getClass().getCanonicalName())) ? 1 : 0;
/*     */       
/* 418 */       if (i != 0 && Debug.isOn("stack")) {
/* 419 */         Thread.dumpStack();
/*     */       }
/*     */       
/* 422 */       if (i != 0 && Debug.isOn("domain")) {
/* 423 */         if (this.context == null) {
/* 424 */           debug.println("domain (context is null)");
/*     */         } else {
/* 426 */           for (byte b1 = 0; b1 < this.context.length; b1++) {
/* 427 */             debug.println("domain " + b1 + " " + this.context[b1]);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 444 */     if (this.context == null) {
/* 445 */       checkPermission2(paramPermission);
/*     */       
/*     */       return;
/*     */     } 
/* 449 */     for (byte b = 0; b < this.context.length; b++) {
/* 450 */       if (this.context[b] != null && !this.context[b].implies(paramPermission)) {
/* 451 */         if (i != 0) {
/* 452 */           debug.println("access denied " + paramPermission);
/*     */         }
/*     */         
/* 455 */         if (Debug.isOn("failure") && debug != null) {
/*     */ 
/*     */ 
/*     */           
/* 459 */           if (i == 0) {
/* 460 */             debug.println("access denied " + paramPermission);
/*     */           }
/* 462 */           Thread.dumpStack();
/* 463 */           final ProtectionDomain pd = this.context[b];
/* 464 */           final Debug db = debug;
/* 465 */           AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */                 public Void run() {
/* 467 */                   db.println("domain that failed " + pd);
/* 468 */                   return null;
/*     */                 }
/*     */               });
/*     */         } 
/* 472 */         throw new AccessControlException("access denied " + paramPermission, paramPermission);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 477 */     if (i != 0) {
/* 478 */       debug.println("access allowed " + paramPermission);
/*     */     }
/*     */     
/* 481 */     checkPermission2(paramPermission);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkPermission2(Permission paramPermission) {
/* 488 */     if (!this.isLimited) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 495 */     if (this.privilegedContext != null) {
/* 496 */       this.privilegedContext.checkPermission2(paramPermission);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 504 */     if (this.isWrapped) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 511 */     if (this.permissions != null) {
/* 512 */       Class<?> clazz = paramPermission.getClass();
/* 513 */       for (byte b = 0; b < this.permissions.length; b++) {
/* 514 */         Permission permission = this.permissions[b];
/* 515 */         if (permission.getClass().equals(clazz) && permission.implies(paramPermission)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 525 */     if (this.parent != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 535 */       if (this.permissions == null) {
/* 536 */         this.parent.checkPermission2(paramPermission);
/*     */       } else {
/* 538 */         this.parent.checkPermission(paramPermission);
/*     */       } 
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
/*     */   AccessControlContext optimize() {
/*     */     AccessControlContext accessControlContext1;
/*     */     ProtectionDomain[] arrayOfProtectionDomain2;
/* 554 */     DomainCombiner domainCombiner = null;
/* 555 */     AccessControlContext accessControlContext2 = null;
/* 556 */     Permission[] arrayOfPermission = null;
/*     */     
/* 558 */     if (this.isPrivileged) {
/* 559 */       accessControlContext1 = this.privilegedContext;
/* 560 */       if (accessControlContext1 != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 566 */         if (accessControlContext1.isWrapped) {
/* 567 */           arrayOfPermission = accessControlContext1.permissions;
/* 568 */           accessControlContext2 = accessControlContext1.parent;
/*     */         } 
/*     */       }
/*     */     } else {
/* 572 */       accessControlContext1 = AccessController.getInheritedAccessControlContext();
/* 573 */       if (accessControlContext1 != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 579 */         if (accessControlContext1.isLimited) {
/* 580 */           accessControlContext2 = accessControlContext1;
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 587 */     boolean bool1 = (this.context == null) ? true : false;
/*     */ 
/*     */ 
/*     */     
/* 591 */     boolean bool2 = (accessControlContext1 == null || accessControlContext1.context == null) ? true : false;
/* 592 */     ProtectionDomain[] arrayOfProtectionDomain1 = bool2 ? null : accessControlContext1.context;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 597 */     boolean bool3 = ((accessControlContext1 == null || !accessControlContext1.isWrapped) && accessControlContext2 == null) ? true : false;
/*     */     
/* 599 */     if (accessControlContext1 != null && accessControlContext1.combiner != null) {
/*     */       
/* 601 */       if (getDebug() != null) {
/* 602 */         debug.println("AccessControlContext invoking the Combiner");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 607 */       domainCombiner = accessControlContext1.combiner;
/* 608 */       arrayOfProtectionDomain2 = domainCombiner.combine(this.context, arrayOfProtectionDomain1);
/*     */     } else {
/* 610 */       if (bool1) {
/* 611 */         if (bool2) {
/* 612 */           calculateFields(accessControlContext1, accessControlContext2, arrayOfPermission);
/* 613 */           return this;
/* 614 */         }  if (bool3) {
/* 615 */           return accessControlContext1;
/*     */         }
/* 617 */       } else if (arrayOfProtectionDomain1 != null && 
/* 618 */         bool3) {
/*     */ 
/*     */ 
/*     */         
/* 622 */         if (this.context.length == 1 && this.context[0] == arrayOfProtectionDomain1[0]) {
/* 623 */           return accessControlContext1;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 628 */       arrayOfProtectionDomain2 = combine(this.context, arrayOfProtectionDomain1);
/* 629 */       if (bool3 && !bool2 && arrayOfProtectionDomain2 == arrayOfProtectionDomain1)
/* 630 */         return accessControlContext1; 
/* 631 */       if (bool2 && arrayOfProtectionDomain2 == this.context) {
/* 632 */         calculateFields(accessControlContext1, accessControlContext2, arrayOfPermission);
/* 633 */         return this;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 638 */     this.context = arrayOfProtectionDomain2;
/* 639 */     this.combiner = domainCombiner;
/* 640 */     this.isPrivileged = false;
/*     */     
/* 642 */     calculateFields(accessControlContext1, accessControlContext2, arrayOfPermission);
/* 643 */     return this;
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
/*     */   private static ProtectionDomain[] combine(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2) {
/* 655 */     boolean bool1 = (paramArrayOfProtectionDomain1 == null) ? true : false;
/*     */ 
/*     */ 
/*     */     
/* 659 */     boolean bool2 = (paramArrayOfProtectionDomain2 == null) ? true : false;
/*     */     
/* 661 */     byte b1 = bool1 ? 0 : paramArrayOfProtectionDomain1.length;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 666 */     if (bool2 && b1 <= 2) {
/* 667 */       return paramArrayOfProtectionDomain1;
/*     */     }
/*     */     
/* 670 */     byte b2 = bool2 ? 0 : paramArrayOfProtectionDomain2.length;
/*     */ 
/*     */     
/* 673 */     ProtectionDomain[] arrayOfProtectionDomain = new ProtectionDomain[b1 + b2];
/*     */ 
/*     */     
/* 676 */     if (!bool2) {
/* 677 */       System.arraycopy(paramArrayOfProtectionDomain2, 0, arrayOfProtectionDomain, 0, b2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 682 */     for (byte b3 = 0; b3 < b1; b3++) {
/* 683 */       ProtectionDomain protectionDomain = paramArrayOfProtectionDomain1[b3];
/* 684 */       if (protectionDomain != null) {
/* 685 */         byte b = 0; while (true) { if (b < b2) {
/* 686 */             if (protectionDomain == arrayOfProtectionDomain[b])
/*     */               break;  b++;
/*     */             continue;
/*     */           } 
/* 690 */           arrayOfProtectionDomain[b2++] = protectionDomain;
/*     */           break; }
/*     */       
/*     */       } 
/*     */     } 
/* 695 */     if (b2 != arrayOfProtectionDomain.length) {
/*     */       
/* 697 */       if (!bool2 && b2 == paramArrayOfProtectionDomain2.length)
/* 698 */         return paramArrayOfProtectionDomain2; 
/* 699 */       if (bool2 && b2 == b1) {
/* 700 */         return paramArrayOfProtectionDomain1;
/*     */       }
/* 702 */       ProtectionDomain[] arrayOfProtectionDomain1 = new ProtectionDomain[b2];
/* 703 */       System.arraycopy(arrayOfProtectionDomain, 0, arrayOfProtectionDomain1, 0, b2);
/* 704 */       arrayOfProtectionDomain = arrayOfProtectionDomain1;
/*     */     } 
/*     */     
/* 707 */     return arrayOfProtectionDomain;
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
/*     */   private void calculateFields(AccessControlContext paramAccessControlContext1, AccessControlContext paramAccessControlContext2, Permission[] paramArrayOfPermission) {
/* 721 */     ProtectionDomain[] arrayOfProtectionDomain1 = null;
/* 722 */     ProtectionDomain[] arrayOfProtectionDomain2 = null;
/*     */ 
/*     */     
/* 725 */     arrayOfProtectionDomain1 = (paramAccessControlContext2 != null) ? paramAccessControlContext2.limitedContext : null;
/* 726 */     arrayOfProtectionDomain2 = (paramAccessControlContext1 != null) ? paramAccessControlContext1.limitedContext : null;
/* 727 */     ProtectionDomain[] arrayOfProtectionDomain3 = combine(arrayOfProtectionDomain1, arrayOfProtectionDomain2);
/* 728 */     if (arrayOfProtectionDomain3 != null && (
/* 729 */       this.context == null || !containsAllPDs(arrayOfProtectionDomain3, this.context))) {
/* 730 */       this.limitedContext = arrayOfProtectionDomain3;
/* 731 */       this.permissions = paramArrayOfPermission;
/* 732 */       this.parent = paramAccessControlContext2;
/* 733 */       this.isLimited = true;
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
/*     */   public boolean equals(Object paramObject) {
/* 750 */     if (paramObject == this) {
/* 751 */       return true;
/*     */     }
/* 753 */     if (!(paramObject instanceof AccessControlContext)) {
/* 754 */       return false;
/*     */     }
/* 756 */     AccessControlContext accessControlContext = (AccessControlContext)paramObject;
/*     */     
/* 758 */     if (!equalContext(accessControlContext)) {
/* 759 */       return false;
/*     */     }
/* 761 */     if (!equalLimitedContext(accessControlContext)) {
/* 762 */       return false;
/*     */     }
/* 764 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean equalContext(AccessControlContext paramAccessControlContext) {
/* 772 */     if (!equalPDs(this.context, paramAccessControlContext.context)) {
/* 773 */       return false;
/*     */     }
/* 775 */     if (this.combiner == null && paramAccessControlContext.combiner != null) {
/* 776 */       return false;
/*     */     }
/* 778 */     if (this.combiner != null && !this.combiner.equals(paramAccessControlContext.combiner)) {
/* 779 */       return false;
/*     */     }
/* 781 */     return true;
/*     */   }
/*     */   
/*     */   private boolean equalPDs(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2) {
/* 785 */     if (paramArrayOfProtectionDomain1 == null) {
/* 786 */       return (paramArrayOfProtectionDomain2 == null);
/*     */     }
/*     */     
/* 789 */     if (paramArrayOfProtectionDomain2 == null) {
/* 790 */       return false;
/*     */     }
/* 792 */     if (!containsAllPDs(paramArrayOfProtectionDomain1, paramArrayOfProtectionDomain2) || !containsAllPDs(paramArrayOfProtectionDomain2, paramArrayOfProtectionDomain1)) {
/* 793 */       return false;
/*     */     }
/* 795 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean equalLimitedContext(AccessControlContext paramAccessControlContext) {
/* 804 */     if (paramAccessControlContext == null) {
/* 805 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 810 */     if (!this.isLimited && !paramAccessControlContext.isLimited) {
/* 811 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 816 */     if (!this.isLimited || !paramAccessControlContext.isLimited) {
/* 817 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 825 */     if ((this.isWrapped && !paramAccessControlContext.isWrapped) || (!this.isWrapped && paramAccessControlContext.isWrapped))
/*     */     {
/* 827 */       return false;
/*     */     }
/*     */     
/* 830 */     if (this.permissions == null && paramAccessControlContext.permissions != null) {
/* 831 */       return false;
/*     */     }
/* 833 */     if (this.permissions != null && paramAccessControlContext.permissions == null) {
/* 834 */       return false;
/*     */     }
/* 836 */     if (!containsAllLimits(paramAccessControlContext) || !paramAccessControlContext.containsAllLimits(this)) {
/* 837 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 842 */     AccessControlContext accessControlContext1 = getNextPC(this);
/* 843 */     AccessControlContext accessControlContext2 = getNextPC(paramAccessControlContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 851 */     if (accessControlContext1 == null && accessControlContext2 != null && accessControlContext2.isLimited) {
/* 852 */       return false;
/*     */     }
/* 854 */     if (accessControlContext1 != null && !accessControlContext1.equalLimitedContext(accessControlContext2)) {
/* 855 */       return false;
/*     */     }
/* 857 */     if (this.parent == null && paramAccessControlContext.parent != null) {
/* 858 */       return false;
/*     */     }
/* 860 */     if (this.parent != null && !this.parent.equals(paramAccessControlContext.parent)) {
/* 861 */       return false;
/*     */     }
/* 863 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AccessControlContext getNextPC(AccessControlContext paramAccessControlContext) {
/* 871 */     while (paramAccessControlContext != null && paramAccessControlContext.privilegedContext != null) {
/* 872 */       paramAccessControlContext = paramAccessControlContext.privilegedContext;
/* 873 */       if (!paramAccessControlContext.isWrapped)
/* 874 */         return paramAccessControlContext; 
/*     */     } 
/* 876 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean containsAllPDs(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2) {
/* 881 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 890 */     for (byte b = 0; b < paramArrayOfProtectionDomain1.length; b++) {
/* 891 */       bool = false; ProtectionDomain protectionDomain;
/* 892 */       if ((protectionDomain = paramArrayOfProtectionDomain1[b]) == null) {
/* 893 */         for (byte b1 = 0; b1 < paramArrayOfProtectionDomain2.length && !bool; b1++) {
/* 894 */           bool = (paramArrayOfProtectionDomain2[b1] == null) ? true : false;
/*     */         }
/*     */       } else {
/* 897 */         Class<?> clazz = protectionDomain.getClass();
/*     */         
/* 899 */         for (byte b1 = 0; b1 < paramArrayOfProtectionDomain2.length && !bool; b1++) {
/* 900 */           ProtectionDomain protectionDomain1 = paramArrayOfProtectionDomain2[b1];
/*     */ 
/*     */ 
/*     */           
/* 904 */           bool = (protectionDomain1 != null && clazz == protectionDomain1.getClass() && protectionDomain.equals(protectionDomain1)) ? true : false;
/*     */         } 
/*     */       } 
/* 907 */       if (!bool) return false; 
/*     */     } 
/* 909 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean containsAllLimits(AccessControlContext paramAccessControlContext) {
/* 913 */     boolean bool = false;
/*     */ 
/*     */     
/* 916 */     if (this.permissions == null && paramAccessControlContext.permissions == null) {
/* 917 */       return true;
/*     */     }
/* 919 */     for (byte b = 0; b < this.permissions.length; b++) {
/* 920 */       Permission permission = this.permissions[b];
/* 921 */       Class<?> clazz = permission.getClass();
/* 922 */       bool = false;
/* 923 */       for (byte b1 = 0; b1 < paramAccessControlContext.permissions.length && !bool; b1++) {
/* 924 */         Permission permission1 = paramAccessControlContext.permissions[b1];
/*     */         
/* 926 */         bool = (clazz.equals(permission1.getClass()) && permission.equals(permission1)) ? true : false;
/*     */       } 
/* 928 */       if (!bool) return false; 
/*     */     } 
/* 930 */     return bool;
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
/*     */   public int hashCode() {
/* 943 */     int i = 0;
/*     */     
/* 945 */     if (this.context == null) {
/* 946 */       return i;
/*     */     }
/* 948 */     for (byte b = 0; b < this.context.length; b++) {
/* 949 */       if (this.context[b] != null) {
/* 950 */         i ^= this.context[b].hashCode();
/*     */       }
/*     */     } 
/* 953 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/AccessControlContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */