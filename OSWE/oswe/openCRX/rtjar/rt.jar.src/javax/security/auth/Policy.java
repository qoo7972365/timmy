/*     */ package javax.security.auth;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSource;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Security;
/*     */ import java.util.Objects;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.ResourcesMgr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class Policy
/*     */ {
/*     */   private static Policy policy;
/*     */   private static final String AUTH_POLICY = "sun.security.provider.AuthPolicyFile";
/* 166 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isCustomPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Policy getPolicy() {
/* 195 */     SecurityManager securityManager = System.getSecurityManager();
/* 196 */     if (securityManager != null) securityManager.checkPermission(new AuthPermission("getPolicy")); 
/* 197 */     return getPolicyNoCheck();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Policy getPolicyNoCheck() {
/* 207 */     if (policy == null)
/*     */     {
/* 209 */       synchronized (Policy.class) {
/*     */         
/* 211 */         if (policy == null) {
/* 212 */           String str = null;
/*     */           
/* 214 */           str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */                 public String run() {
/* 216 */                   return 
/* 217 */                     Security.getProperty("auth.policy.provider");
/*     */                 }
/*     */               });
/* 220 */           if (str == null) {
/* 221 */             str = "sun.security.provider.AuthPolicyFile";
/*     */           }
/*     */           
/*     */           try {
/* 225 */             final String finalClass = str;
/*     */             
/* 227 */             final Policy untrustedImpl = AccessController.<Policy>doPrivileged(new PrivilegedExceptionAction<Policy>()
/*     */                 {
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   public Policy run() throws ClassNotFoundException, InstantiationException, IllegalAccessException
/*     */                   {
/* 235 */                     Class<? extends Policy> clazz = Class.forName(finalClass, false, Thread.currentThread().getContextClassLoader()).asSubclass(Policy.class);
/* 236 */                     return clazz.newInstance();
/*     */                   }
/*     */                 });
/* 239 */             AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */                 {
/*     */                   public Void run() {
/* 242 */                     Policy.setPolicy(untrustedImpl);
/* 243 */                     Policy.isCustomPolicy = !finalClass.equals("sun.security.provider.AuthPolicyFile");
/* 244 */                     return null;
/*     */                   }
/* 246 */                 },  Objects.<AccessControlContext>requireNonNull(policy.acc));
/*     */           }
/* 248 */           catch (Exception exception) {
/* 249 */             throw new SecurityException(
/*     */                 
/* 251 */                 ResourcesMgr.getString("unable.to.instantiate.Subject.based.policy"));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 256 */     return policy;
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
/*     */   public static void setPolicy(Policy paramPolicy) {
/* 276 */     SecurityManager securityManager = System.getSecurityManager();
/* 277 */     if (securityManager != null) securityManager.checkPermission(new AuthPermission("setPolicy")); 
/* 278 */     policy = paramPolicy;
/*     */     
/* 280 */     isCustomPolicy = (paramPolicy != null);
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
/*     */   static boolean isCustomPolicySet(Debug paramDebug) {
/* 294 */     if (policy != null) {
/* 295 */       if (paramDebug != null && isCustomPolicy) {
/* 296 */         paramDebug.println("Providing backwards compatibility for javax.security.auth.policy implementation: " + policy
/*     */             
/* 298 */             .toString());
/*     */       }
/* 300 */       return isCustomPolicy;
/*     */     } 
/*     */ 
/*     */     
/* 304 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/* 306 */             return Security.getProperty("auth.policy.provider");
/*     */           }
/*     */         });
/* 309 */     if (str != null && !str.equals("sun.security.provider.AuthPolicyFile")) {
/* 310 */       if (paramDebug != null) {
/* 311 */         paramDebug.println("Providing backwards compatibility for javax.security.auth.policy implementation: " + str);
/*     */       }
/*     */ 
/*     */       
/* 315 */       return true;
/*     */     } 
/* 317 */     return false;
/*     */   }
/*     */   
/*     */   public abstract PermissionCollection getPermissions(Subject paramSubject, CodeSource paramCodeSource);
/*     */   
/*     */   public abstract void refresh();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/Policy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */