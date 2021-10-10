/*     */ package java.security;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import sun.security.jca.GetInstance;
/*     */ import sun.security.provider.PolicyFile;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Policy
/*     */ {
/*  92 */   public static final PermissionCollection UNSUPPORTED_EMPTY_COLLECTION = new UnsupportedEmptyCollection();
/*     */ 
/*     */   
/*     */   private static class PolicyInfo
/*     */   {
/*     */     final Policy policy;
/*     */     
/*     */     final boolean initialized;
/*     */ 
/*     */     
/*     */     PolicyInfo(Policy param1Policy, boolean param1Boolean) {
/* 103 */       this.policy = param1Policy;
/* 104 */       this.initialized = param1Boolean;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 109 */   private static AtomicReference<PolicyInfo> policy = new AtomicReference<>(new PolicyInfo(null, false));
/*     */ 
/*     */   
/* 112 */   private static final Debug debug = Debug.getInstance("policy");
/*     */ 
/*     */   
/*     */   private WeakHashMap<ProtectionDomain.Key, PermissionCollection> pdMapping;
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isSet() {
/* 120 */     PolicyInfo policyInfo = policy.get();
/* 121 */     return (policyInfo.policy != null && policyInfo.initialized == true);
/*     */   }
/*     */   
/*     */   private static void checkPermission(String paramString) {
/* 125 */     SecurityManager securityManager = System.getSecurityManager();
/* 126 */     if (securityManager != null) {
/* 127 */       securityManager.checkPermission(new SecurityPermission("createPolicy." + paramString));
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
/*     */   public static Policy getPolicy() {
/* 151 */     SecurityManager securityManager = System.getSecurityManager();
/* 152 */     if (securityManager != null)
/* 153 */       securityManager.checkPermission(SecurityConstants.GET_POLICY_PERMISSION); 
/* 154 */     return getPolicyNoCheck();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Policy getPolicyNoCheck() {
/* 165 */     PolicyInfo policyInfo = policy.get();
/*     */ 
/*     */     
/* 168 */     if (!policyInfo.initialized || policyInfo.policy == null) {
/* 169 */       synchronized (Policy.class) {
/* 170 */         PolicyInfo policyInfo1 = policy.get();
/* 171 */         if (policyInfo1.policy == null) {
/* 172 */           String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */               {
/*     */                 public String run() {
/* 175 */                   return Security.getProperty("policy.provider");
/*     */                 }
/*     */               });
/* 178 */           if (str == null) {
/* 179 */             str = "sun.security.provider.PolicyFile";
/*     */           }
/*     */ 
/*     */           
/*     */           try {
/* 184 */             policyInfo1 = new PolicyInfo((Policy)Class.forName(str).newInstance(), true);
/*     */           }
/* 186 */           catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 196 */             PolicyFile policyFile = new PolicyFile();
/* 197 */             policyInfo1 = new PolicyInfo(policyFile, false);
/* 198 */             policy.set(policyInfo1);
/*     */             
/* 200 */             final String pc = str;
/* 201 */             Policy policy = AccessController.<Policy>doPrivileged(new PrivilegedAction<Policy>()
/*     */                 {
/*     */                   public Policy run()
/*     */                   {
/*     */                     try {
/* 206 */                       ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
/*     */                       
/* 208 */                       ClassLoader classLoader2 = null;
/* 209 */                       while (classLoader1 != null) {
/* 210 */                         classLoader2 = classLoader1;
/* 211 */                         classLoader1 = classLoader1.getParent();
/*     */                       } 
/* 213 */                       return (classLoader2 != null) ? (Policy)Class.forName(pc, true, classLoader2)
/* 214 */                         .newInstance() : null;
/* 215 */                     } catch (Exception exception) {
/* 216 */                       if (Policy.debug != null) {
/* 217 */                         Policy.debug.println("policy provider " + pc + " not available");
/*     */ 
/*     */                         
/* 220 */                         exception.printStackTrace();
/*     */                       } 
/* 222 */                       return null;
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 230 */             if (policy != null) {
/* 231 */               policyInfo1 = new PolicyInfo(policy, true);
/*     */             } else {
/* 233 */               if (debug != null) {
/* 234 */                 debug.println("using sun.security.provider.PolicyFile");
/*     */               }
/* 236 */               policyInfo1 = new PolicyInfo(policyFile, true);
/*     */             } 
/*     */           } 
/* 239 */           policy.set(policyInfo1);
/*     */         } 
/* 241 */         return policyInfo1.policy;
/*     */       } 
/*     */     }
/* 244 */     return policyInfo.policy;
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
/*     */   public static void setPolicy(Policy paramPolicy) {
/* 266 */     SecurityManager securityManager = System.getSecurityManager();
/* 267 */     if (securityManager != null) securityManager.checkPermission(new SecurityPermission("setPolicy"));
/*     */     
/* 269 */     if (paramPolicy != null) {
/* 270 */       initPolicy(paramPolicy);
/*     */     }
/* 272 */     synchronized (Policy.class) {
/* 273 */       policy.set(new PolicyInfo(paramPolicy, (paramPolicy != null)));
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initPolicy(final Policy p) {
/* 306 */     ProtectionDomain protectionDomain = AccessController.<ProtectionDomain>doPrivileged(new PrivilegedAction<ProtectionDomain>() {
/*     */           public ProtectionDomain run() {
/* 308 */             return p.getClass().getProtectionDomain();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 317 */     PermissionCollection permissionCollection = null;
/* 318 */     synchronized (p) {
/* 319 */       if (p.pdMapping == null) {
/* 320 */         p.pdMapping = new WeakHashMap<>();
/*     */       }
/*     */     } 
/*     */     
/* 324 */     if (protectionDomain.getCodeSource() != null) {
/* 325 */       Policy policy = ((PolicyInfo)policy.get()).policy;
/* 326 */       if (policy != null) {
/* 327 */         permissionCollection = policy.getPermissions(protectionDomain);
/*     */       }
/*     */       
/* 330 */       if (permissionCollection == null) {
/* 331 */         permissionCollection = new Permissions();
/* 332 */         permissionCollection.add(SecurityConstants.ALL_PERMISSION);
/*     */       } 
/*     */       
/* 335 */       synchronized (p.pdMapping) {
/*     */         
/* 337 */         p.pdMapping.put(protectionDomain.key, permissionCollection);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Policy getInstance(String paramString, Parameters paramParameters) throws NoSuchAlgorithmException {
/* 384 */     checkPermission(paramString);
/*     */     try {
/* 386 */       GetInstance.Instance instance = GetInstance.getInstance("Policy", PolicySpi.class, paramString, paramParameters);
/*     */ 
/*     */ 
/*     */       
/* 390 */       return new PolicyDelegate((PolicySpi)instance.impl, instance.provider, paramString, paramParameters);
/*     */ 
/*     */     
/*     */     }
/* 394 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 395 */       return handleException(noSuchAlgorithmException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Policy getInstance(String paramString1, Parameters paramParameters, String paramString2) throws NoSuchProviderException, NoSuchAlgorithmException {
/* 446 */     if (paramString2 == null || paramString2.length() == 0) {
/* 447 */       throw new IllegalArgumentException("missing provider");
/*     */     }
/*     */     
/* 450 */     checkPermission(paramString1);
/*     */     try {
/* 452 */       GetInstance.Instance instance = GetInstance.getInstance("Policy", PolicySpi.class, paramString1, paramParameters, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 457 */       return new PolicyDelegate((PolicySpi)instance.impl, instance.provider, paramString1, paramParameters);
/*     */ 
/*     */     
/*     */     }
/* 461 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 462 */       return handleException(noSuchAlgorithmException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Policy getInstance(String paramString, Parameters paramParameters, Provider paramProvider) throws NoSuchAlgorithmException {
/* 506 */     if (paramProvider == null) {
/* 507 */       throw new IllegalArgumentException("missing provider");
/*     */     }
/*     */     
/* 510 */     checkPermission(paramString);
/*     */     try {
/* 512 */       GetInstance.Instance instance = GetInstance.getInstance("Policy", PolicySpi.class, paramString, paramParameters, paramProvider);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 517 */       return new PolicyDelegate((PolicySpi)instance.impl, instance.provider, paramString, paramParameters);
/*     */ 
/*     */     
/*     */     }
/* 521 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 522 */       return handleException(noSuchAlgorithmException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Policy handleException(NoSuchAlgorithmException paramNoSuchAlgorithmException) throws NoSuchAlgorithmException {
/* 528 */     Throwable throwable = paramNoSuchAlgorithmException.getCause();
/* 529 */     if (throwable instanceof IllegalArgumentException) {
/* 530 */       throw (IllegalArgumentException)throwable;
/*     */     }
/* 532 */     throw paramNoSuchAlgorithmException;
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
/*     */   public Provider getProvider() {
/* 547 */     return null;
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
/*     */   public String getType() {
/* 562 */     return null;
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
/*     */   public Parameters getParameters() {
/* 577 */     return null;
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
/*     */   public PermissionCollection getPermissions(CodeSource paramCodeSource) {
/* 607 */     return UNSUPPORTED_EMPTY_COLLECTION;
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
/*     */   public PermissionCollection getPermissions(ProtectionDomain paramProtectionDomain) {
/* 645 */     PermissionCollection permissionCollection = null;
/*     */     
/* 647 */     if (paramProtectionDomain == null) {
/* 648 */       return new Permissions();
/*     */     }
/* 650 */     if (this.pdMapping == null) {
/* 651 */       initPolicy(this);
/*     */     }
/*     */     
/* 654 */     synchronized (this.pdMapping) {
/* 655 */       permissionCollection = this.pdMapping.get(paramProtectionDomain.key);
/*     */     } 
/*     */     
/* 658 */     if (permissionCollection != null) {
/* 659 */       Permissions permissions = new Permissions();
/* 660 */       synchronized (permissionCollection) {
/* 661 */         for (Enumeration<Permission> enumeration = permissionCollection.elements(); enumeration.hasMoreElements();) {
/* 662 */           permissions.add(enumeration.nextElement());
/*     */         }
/*     */       } 
/* 665 */       return permissions;
/*     */     } 
/*     */     
/* 668 */     permissionCollection = getPermissions(paramProtectionDomain.getCodeSource());
/* 669 */     if (permissionCollection == null || permissionCollection == UNSUPPORTED_EMPTY_COLLECTION) {
/* 670 */       permissionCollection = new Permissions();
/*     */     }
/*     */     
/* 673 */     addStaticPerms(permissionCollection, paramProtectionDomain.getPermissions());
/* 674 */     return permissionCollection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addStaticPerms(PermissionCollection paramPermissionCollection1, PermissionCollection paramPermissionCollection2) {
/* 682 */     if (paramPermissionCollection2 != null) {
/* 683 */       synchronized (paramPermissionCollection2) {
/* 684 */         Enumeration<Permission> enumeration = paramPermissionCollection2.elements();
/* 685 */         while (enumeration.hasMoreElements()) {
/* 686 */           paramPermissionCollection1.add(enumeration.nextElement());
/*     */         }
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
/*     */   public boolean implies(ProtectionDomain paramProtectionDomain, Permission paramPermission) {
/* 709 */     if (this.pdMapping == null) {
/* 710 */       initPolicy(this);
/*     */     }
/*     */     
/* 713 */     synchronized (this.pdMapping) {
/* 714 */       permissionCollection = this.pdMapping.get(paramProtectionDomain.key);
/*     */     } 
/*     */     
/* 717 */     if (permissionCollection != null) {
/* 718 */       return permissionCollection.implies(paramPermission);
/*     */     }
/*     */     
/* 721 */     PermissionCollection permissionCollection = getPermissions(paramProtectionDomain);
/* 722 */     if (permissionCollection == null) {
/* 723 */       return false;
/*     */     }
/*     */     
/* 726 */     synchronized (this.pdMapping) {
/*     */       
/* 728 */       this.pdMapping.put(paramProtectionDomain.key, permissionCollection);
/*     */     } 
/*     */     
/* 731 */     return permissionCollection.implies(paramPermission);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PolicyDelegate
/*     */     extends Policy
/*     */   {
/*     */     private PolicySpi spi;
/*     */ 
/*     */     
/*     */     private Provider p;
/*     */ 
/*     */     
/*     */     private String type;
/*     */ 
/*     */     
/*     */     private Policy.Parameters params;
/*     */ 
/*     */ 
/*     */     
/*     */     private PolicyDelegate(PolicySpi param1PolicySpi, Provider param1Provider, String param1String, Policy.Parameters param1Parameters) {
/* 758 */       this.spi = param1PolicySpi;
/* 759 */       this.p = param1Provider;
/* 760 */       this.type = param1String;
/* 761 */       this.params = param1Parameters;
/*     */     }
/*     */     public String getType() {
/* 764 */       return this.type;
/*     */     } public Policy.Parameters getParameters() {
/* 766 */       return this.params;
/*     */     } public Provider getProvider() {
/* 768 */       return this.p;
/*     */     }
/*     */     
/*     */     public PermissionCollection getPermissions(CodeSource param1CodeSource) {
/* 772 */       return this.spi.engineGetPermissions(param1CodeSource);
/*     */     }
/*     */     
/*     */     public PermissionCollection getPermissions(ProtectionDomain param1ProtectionDomain) {
/* 776 */       return this.spi.engineGetPermissions(param1ProtectionDomain);
/*     */     }
/*     */     
/*     */     public boolean implies(ProtectionDomain param1ProtectionDomain, Permission param1Permission) {
/* 780 */       return this.spi.engineImplies(param1ProtectionDomain, param1Permission);
/*     */     }
/*     */     
/*     */     public void refresh() {
/* 784 */       this.spi.engineRefresh();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UnsupportedEmptyCollection
/*     */     extends PermissionCollection
/*     */   {
/*     */     private static final long serialVersionUID = -8492269157353014774L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Permissions perms;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public UnsupportedEmptyCollection() {
/* 813 */       this.perms = new Permissions();
/* 814 */       this.perms.setReadOnly();
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
/*     */     public void add(Permission param1Permission) {
/* 827 */       this.perms.add(param1Permission);
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
/*     */     public boolean implies(Permission param1Permission) {
/* 840 */       return this.perms.implies(param1Permission);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Enumeration<Permission> elements() {
/* 850 */       return this.perms.elements();
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Parameters {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/Policy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */