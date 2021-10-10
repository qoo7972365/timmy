/*     */ package com.sun.jmx.remote.security;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSource;
/*     */ import java.security.Permissions;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.security.cert.Certificate;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.SubjectDomainCombiner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMXSubjectDomainCombiner
/*     */   extends SubjectDomainCombiner
/*     */ {
/*     */   public JMXSubjectDomainCombiner(Subject paramSubject) {
/*  49 */     super(paramSubject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtectionDomain[] combine(ProtectionDomain[] paramArrayOfProtectionDomain1, ProtectionDomain[] paramArrayOfProtectionDomain2) {
/*     */     ProtectionDomain[] arrayOfProtectionDomain;
/*  61 */     if (paramArrayOfProtectionDomain1 == null || paramArrayOfProtectionDomain1.length == 0) {
/*  62 */       arrayOfProtectionDomain = new ProtectionDomain[1];
/*  63 */       arrayOfProtectionDomain[0] = pdNoPerms;
/*     */     } else {
/*  65 */       arrayOfProtectionDomain = new ProtectionDomain[paramArrayOfProtectionDomain1.length + 1];
/*  66 */       for (byte b = 0; b < paramArrayOfProtectionDomain1.length; b++) {
/*  67 */         arrayOfProtectionDomain[b] = paramArrayOfProtectionDomain1[b];
/*     */       }
/*  69 */       arrayOfProtectionDomain[paramArrayOfProtectionDomain1.length] = pdNoPerms;
/*     */     } 
/*  71 */     return super.combine(arrayOfProtectionDomain, paramArrayOfProtectionDomain2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final CodeSource nullCodeSource = new CodeSource(null, (Certificate[])null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final ProtectionDomain pdNoPerms = new ProtectionDomain(nullCodeSource, new Permissions(), null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AccessControlContext getContext(Subject paramSubject) {
/*  90 */     return new AccessControlContext(AccessController.getContext(), new JMXSubjectDomainCombiner(paramSubject));
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
/*     */   public static AccessControlContext getDomainCombinerContext(Subject paramSubject) {
/* 102 */     return new AccessControlContext(new AccessControlContext(new ProtectionDomain[0]), new JMXSubjectDomainCombiner(paramSubject));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/security/JMXSubjectDomainCombiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */