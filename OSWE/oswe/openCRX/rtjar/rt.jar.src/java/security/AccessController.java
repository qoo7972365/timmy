/*     */ package java.security;
/*     */ 
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AccessController
/*     */ {
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction);
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> paramPrivilegedAction) {
/* 327 */     AccessControlContext accessControlContext = getStackAccessControlContext();
/* 328 */     if (accessControlContext == null) {
/* 329 */       return doPrivileged(paramPrivilegedAction);
/*     */     }
/* 331 */     DomainCombiner domainCombiner = accessControlContext.getAssignedCombiner();
/* 332 */     return doPrivileged(paramPrivilegedAction, 
/* 333 */         preserveCombiner(domainCombiner, Reflection.getCallerClass()));
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
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivileged(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs) {
/* 423 */     AccessControlContext accessControlContext = getContext();
/* 424 */     if (paramVarArgs == null) {
/* 425 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 427 */     Class<?> clazz = Reflection.getCallerClass();
/* 428 */     DomainCombiner domainCombiner = (paramAccessControlContext == null) ? null : paramAccessControlContext.getCombiner();
/* 429 */     return doPrivileged(paramPrivilegedAction, createWrapper(domainCombiner, clazz, accessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> paramPrivilegedAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs) {
/* 486 */     AccessControlContext accessControlContext = getContext();
/* 487 */     DomainCombiner domainCombiner = accessControlContext.getCombiner();
/* 488 */     if (domainCombiner == null && paramAccessControlContext != null) {
/* 489 */       domainCombiner = paramAccessControlContext.getCombiner();
/*     */     }
/* 491 */     if (paramVarArgs == null) {
/* 492 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 494 */     Class<?> clazz = Reflection.getCallerClass();
/* 495 */     return doPrivileged(paramPrivilegedAction, createWrapper(domainCombiner, clazz, accessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction) throws PrivilegedActionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction) throws PrivilegedActionException {
/* 564 */     AccessControlContext accessControlContext = getStackAccessControlContext();
/* 565 */     if (accessControlContext == null) {
/* 566 */       return doPrivileged(paramPrivilegedExceptionAction);
/*     */     }
/* 568 */     DomainCombiner domainCombiner = accessControlContext.getAssignedCombiner();
/* 569 */     return doPrivileged(paramPrivilegedExceptionAction, 
/* 570 */         preserveCombiner(domainCombiner, Reflection.getCallerClass()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AccessControlContext preserveCombiner(DomainCombiner paramDomainCombiner, Class<?> paramClass) {
/* 579 */     return createWrapper(paramDomainCombiner, paramClass, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AccessControlContext createWrapper(DomainCombiner paramDomainCombiner, Class<?> paramClass, AccessControlContext paramAccessControlContext1, AccessControlContext paramAccessControlContext2, Permission[] paramArrayOfPermission) {
/* 590 */     ProtectionDomain protectionDomain = getCallerPD(paramClass);
/*     */     
/* 592 */     if (paramAccessControlContext2 != null && !paramAccessControlContext2.isAuthorized() && 
/* 593 */       System.getSecurityManager() != null && 
/* 594 */       !protectionDomain.impliesCreateAccessControlContext()) {
/*     */       
/* 596 */       ProtectionDomain protectionDomain1 = new ProtectionDomain(null, null);
/* 597 */       return new AccessControlContext(new ProtectionDomain[] { protectionDomain1 });
/*     */     } 
/* 599 */     return new AccessControlContext(protectionDomain, paramDomainCombiner, paramAccessControlContext1, paramAccessControlContext2, paramArrayOfPermission);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtectionDomain getCallerPD(final Class<?> caller) {
/* 606 */     return doPrivileged(new PrivilegedAction<ProtectionDomain>() {
/*     */           public ProtectionDomain run() {
/* 608 */             return caller.getProtectionDomain();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static native <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext) throws PrivilegedActionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivileged(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs) throws PrivilegedActionException {
/* 709 */     AccessControlContext accessControlContext = getContext();
/* 710 */     if (paramVarArgs == null) {
/* 711 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 713 */     Class<?> clazz = Reflection.getCallerClass();
/* 714 */     DomainCombiner domainCombiner = (paramAccessControlContext == null) ? null : paramAccessControlContext.getCombiner();
/* 715 */     return doPrivileged(paramPrivilegedExceptionAction, createWrapper(domainCombiner, clazz, accessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> paramPrivilegedExceptionAction, AccessControlContext paramAccessControlContext, Permission... paramVarArgs) throws PrivilegedActionException {
/* 775 */     AccessControlContext accessControlContext = getContext();
/* 776 */     DomainCombiner domainCombiner = accessControlContext.getCombiner();
/* 777 */     if (domainCombiner == null && paramAccessControlContext != null) {
/* 778 */       domainCombiner = paramAccessControlContext.getCombiner();
/*     */     }
/* 780 */     if (paramVarArgs == null) {
/* 781 */       throw new NullPointerException("null permissions parameter");
/*     */     }
/* 783 */     Class<?> clazz = Reflection.getCallerClass();
/* 784 */     return doPrivileged(paramPrivilegedExceptionAction, createWrapper(domainCombiner, clazz, accessControlContext, paramAccessControlContext, paramVarArgs));
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
/*     */   private static native AccessControlContext getStackAccessControlContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native AccessControlContext getInheritedAccessControlContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AccessControlContext getContext() {
/* 822 */     AccessControlContext accessControlContext = getStackAccessControlContext();
/* 823 */     if (accessControlContext == null)
/*     */     {
/*     */       
/* 826 */       return new AccessControlContext(null, true);
/*     */     }
/* 828 */     return accessControlContext.optimize();
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
/*     */   public static void checkPermission(Permission paramPermission) throws AccessControlException {
/* 856 */     if (paramPermission == null) {
/* 857 */       throw new NullPointerException("permission can't be null");
/*     */     }
/*     */     
/* 860 */     AccessControlContext accessControlContext1 = getStackAccessControlContext();
/*     */     
/* 862 */     if (accessControlContext1 == null) {
/* 863 */       Debug debug = AccessControlContext.getDebug();
/* 864 */       int i = 0;
/* 865 */       if (debug != null) {
/* 866 */         i = !Debug.isOn("codebase=") ? 1 : 0;
/* 867 */         i &= (!Debug.isOn("permission=") || 
/* 868 */           Debug.isOn("permission=" + paramPermission.getClass().getCanonicalName())) ? 1 : 0;
/*     */       } 
/*     */       
/* 871 */       if (i != 0 && Debug.isOn("stack")) {
/* 872 */         Thread.dumpStack();
/*     */       }
/*     */       
/* 875 */       if (i != 0 && Debug.isOn("domain")) {
/* 876 */         debug.println("domain (context is null)");
/*     */       }
/*     */       
/* 879 */       if (i != 0) {
/* 880 */         debug.println("access allowed " + paramPermission);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 885 */     AccessControlContext accessControlContext2 = accessControlContext1.optimize();
/* 886 */     accessControlContext2.checkPermission(paramPermission);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/AccessController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */