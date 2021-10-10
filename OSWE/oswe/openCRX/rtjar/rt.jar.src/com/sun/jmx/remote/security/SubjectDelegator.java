/*     */ package com.sun.jmx.remote.security;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.management.remote.SubjectDelegationPermission;
/*     */ import javax.security.auth.Subject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubjectDelegator
/*     */ {
/*     */   public AccessControlContext delegatedContext(AccessControlContext paramAccessControlContext, Subject paramSubject, boolean paramBoolean) throws SecurityException {
/*  50 */     if (System.getSecurityManager() != null && paramAccessControlContext == null) {
/*  51 */       throw new SecurityException("Illegal AccessControlContext: null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     Collection<Principal> collection = getSubjectPrincipals(paramSubject);
/*  59 */     final ArrayList<SubjectDelegationPermission> permissions = new ArrayList(collection.size());
/*  60 */     for (Principal principal : collection) {
/*  61 */       String str = principal.getClass().getName() + "." + principal.getName();
/*  62 */       arrayList.add(new SubjectDelegationPermission(str));
/*     */     } 
/*  64 */     PrivilegedAction<Void> privilegedAction = new PrivilegedAction<Void>()
/*     */       {
/*     */         public Void run() {
/*  67 */           for (Permission permission : permissions) {
/*  68 */             AccessController.checkPermission(permission);
/*     */           }
/*  70 */           return null;
/*     */         }
/*     */       };
/*  73 */     AccessController.doPrivileged(privilegedAction, paramAccessControlContext);
/*     */     
/*  75 */     return getDelegatedAcc(paramSubject, paramBoolean);
/*     */   }
/*     */   
/*     */   private AccessControlContext getDelegatedAcc(Subject paramSubject, boolean paramBoolean) {
/*  79 */     if (paramBoolean) {
/*  80 */       return JMXSubjectDomainCombiner.getDomainCombinerContext(paramSubject);
/*     */     }
/*  82 */     return JMXSubjectDomainCombiner.getContext(paramSubject);
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
/*     */   public static synchronized boolean checkRemoveCallerContext(Subject paramSubject) {
/*     */     try {
/*  98 */       for (Principal principal : getSubjectPrincipals(paramSubject)) {
/*     */         
/* 100 */         String str = principal.getClass().getName() + "." + principal.getName();
/* 101 */         SubjectDelegationPermission subjectDelegationPermission = new SubjectDelegationPermission(str);
/*     */         
/* 103 */         AccessController.checkPermission(subjectDelegationPermission);
/*     */       } 
/* 105 */     } catch (SecurityException securityException) {
/* 106 */       return false;
/*     */     } 
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Collection<Principal> getSubjectPrincipals(Subject paramSubject) {
/* 118 */     if (paramSubject.isReadOnly()) {
/* 119 */       return paramSubject.getPrincipals();
/*     */     }
/*     */     
/* 122 */     List<?> list = Arrays.asList(paramSubject.getPrincipals().toArray((Object[])new Principal[0]));
/* 123 */     return (Collection)Collections.unmodifiableList(list);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/security/SubjectDelegator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */