/*      */ package sun.security.provider;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.AllPermission;
/*      */ import java.security.CodeSource;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.Permission;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.Permissions;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.Security;
/*      */ import java.security.UnresolvedPermission;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.security.auth.AuthPermission;
/*      */ import javax.security.auth.Policy;
/*      */ import javax.security.auth.PrivateCredentialPermission;
/*      */ import javax.security.auth.Subject;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.PolicyUtil;
/*      */ import sun.security.util.PropertyExpander;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Deprecated
/*      */ public class AuthPolicyFile
/*      */   extends Policy
/*      */ {
/*   71 */   static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>() {
/*      */         public ResourceBundle run() {
/*   73 */           return 
/*   74 */             ResourceBundle.getBundle("sun.security.util.AuthResources");
/*      */         }
/*      */       });
/*      */   
/*   78 */   private static final Debug debug = Debug.getInstance("policy", "\t[Auth Policy]");
/*      */   
/*      */   private static final String AUTH_POLICY = "java.security.auth.policy";
/*      */   
/*      */   private static final String SECURITY_MANAGER = "java.security.manager";
/*      */   
/*      */   private static final String AUTH_POLICY_URL = "auth.policy.url.";
/*      */   
/*      */   private Vector<PolicyEntry> policyEntries;
/*      */   
/*      */   private Hashtable<Object, Object> aliasMapping;
/*      */   
/*      */   private boolean initialized = false;
/*      */   
/*      */   private boolean expandProperties = true;
/*      */   private boolean ignoreIdentityScope = true;
/*   94 */   private static final Class<?>[] PARAMS = new Class[] { String.class, String.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthPolicyFile() {
/*  103 */     String str = System.getProperty("java.security.auth.policy");
/*      */     
/*  105 */     if (str == null) {
/*  106 */       str = System.getProperty("java.security.manager");
/*      */     }
/*  108 */     if (str != null) {
/*  109 */       init();
/*      */     }
/*      */   }
/*      */   
/*      */   private synchronized void init() {
/*  114 */     if (this.initialized) {
/*      */       return;
/*      */     }
/*      */     
/*  118 */     this.policyEntries = new Vector<>();
/*  119 */     this.aliasMapping = new Hashtable<>(11);
/*      */     
/*  121 */     initPolicyFile();
/*  122 */     this.initialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void refresh() {
/*  128 */     SecurityManager securityManager = System.getSecurityManager();
/*  129 */     if (securityManager != null) {
/*  130 */       securityManager.checkPermission(new AuthPermission("refreshPolicy"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  145 */     this.initialized = false;
/*  146 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/*  148 */             AuthPolicyFile.this.init();
/*  149 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private KeyStore initKeyStore(URL paramURL, String paramString1, String paramString2) {
/*  156 */     if (paramString1 != null) {
/*      */       try {
/*      */         KeyStore keyStore;
/*      */ 
/*      */ 
/*      */         
/*  162 */         URL uRL = null;
/*      */         try {
/*  164 */           uRL = new URL(paramString1);
/*      */         }
/*  166 */         catch (MalformedURLException malformedURLException) {
/*      */           
/*  168 */           uRL = new URL(paramURL, paramString1);
/*      */         } 
/*      */         
/*  171 */         if (debug != null) {
/*  172 */           debug.println("reading keystore" + uRL);
/*      */         }
/*      */ 
/*      */         
/*  176 */         BufferedInputStream bufferedInputStream = new BufferedInputStream(PolicyUtil.getInputStream(uRL));
/*      */ 
/*      */         
/*  179 */         if (paramString2 != null) {
/*  180 */           keyStore = KeyStore.getInstance(paramString2);
/*      */         } else {
/*  182 */           keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
/*  183 */         }  keyStore.load(bufferedInputStream, null);
/*  184 */         bufferedInputStream.close();
/*  185 */         return keyStore;
/*  186 */       } catch (Exception exception) {
/*      */         
/*  188 */         if (debug != null) {
/*  189 */           debug.println("Debug info only. No keystore.");
/*  190 */           exception.printStackTrace();
/*      */         } 
/*  192 */         return null;
/*      */       } 
/*      */     }
/*  195 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void initPolicyFile() {
/*  200 */     String str1 = Security.getProperty("policy.expandProperties");
/*  201 */     if (str1 != null) {
/*  202 */       this.expandProperties = str1.equalsIgnoreCase("true");
/*      */     }
/*      */     
/*  205 */     String str2 = Security.getProperty("policy.ignoreIdentityScope");
/*  206 */     if (str2 != null) {
/*  207 */       this.ignoreIdentityScope = str2.equalsIgnoreCase("true");
/*      */     }
/*      */     
/*  210 */     String str3 = Security.getProperty("policy.allowSystemProperty");
/*  211 */     if (str3 != null && str3.equalsIgnoreCase("true")) {
/*  212 */       String str = System.getProperty("java.security.auth.policy");
/*  213 */       if (str != null) {
/*  214 */         boolean bool1 = false;
/*  215 */         if (str.startsWith("=")) {
/*  216 */           bool1 = true;
/*  217 */           str = str.substring(1);
/*      */         }  try {
/*      */           URL uRL;
/*  220 */           str = PropertyExpander.expand(str);
/*      */           
/*  222 */           File file = new File(str);
/*  223 */           if (file.exists()) {
/*      */             
/*  225 */             uRL = new URL("file:" + file.getCanonicalPath());
/*      */           } else {
/*  227 */             uRL = new URL(str);
/*      */           } 
/*  229 */           if (debug != null) {
/*  230 */             debug.println("reading " + uRL);
/*      */           }
/*  232 */           init(uRL);
/*  233 */         } catch (Exception exception) {
/*      */           
/*  235 */           if (debug != null) {
/*  236 */             debug.println("caught exception: " + exception);
/*      */           }
/*      */         } 
/*      */         
/*  240 */         if (bool1) {
/*  241 */           if (debug != null) {
/*  242 */             debug.println("overriding other policies!");
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  249 */     byte b = 1;
/*  250 */     boolean bool = false;
/*      */     
/*      */     String str4;
/*  253 */     while ((str4 = Security.getProperty("auth.policy.url." + b)) != null) {
/*      */       
/*      */       try {
/*  256 */         str4 = PropertyExpander.expand(str4).replace(File.separatorChar, '/');
/*  257 */         if (debug != null) {
/*  258 */           debug.println("reading " + str4);
/*      */         }
/*  260 */         init(new URL(str4));
/*  261 */         bool = true;
/*  262 */       } catch (Exception exception) {
/*  263 */         if (debug != null) {
/*  264 */           debug.println("Debug info only. Error reading policy " + exception);
/*  265 */           exception.printStackTrace();
/*      */         } 
/*      */       } 
/*      */       
/*  269 */       b++;
/*      */     } 
/*      */     
/*  272 */     if (!bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkForTrustedIdentity(Certificate paramCertificate) {
/*  283 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init(URL paramURL) {
/*  293 */     PolicyParser policyParser = new PolicyParser(this.expandProperties);
/*  294 */     try (InputStreamReader null = new InputStreamReader(
/*  295 */           PolicyUtil.getInputStream(paramURL))) {
/*  296 */       policyParser.read(inputStreamReader);
/*  297 */       KeyStore keyStore = initKeyStore(paramURL, policyParser.getKeyStoreUrl(), policyParser
/*  298 */           .getKeyStoreType());
/*  299 */       Enumeration<PolicyParser.GrantEntry> enumeration = policyParser.grantElements();
/*  300 */       while (enumeration.hasMoreElements()) {
/*  301 */         PolicyParser.GrantEntry grantEntry = enumeration.nextElement();
/*  302 */         addGrantEntry(grantEntry, keyStore);
/*      */       } 
/*  304 */     } catch (ParsingException parsingException) {
/*  305 */       System.err.println("java.security.auth.policy" + rb
/*  306 */           .getString(".error.parsing.") + paramURL);
/*  307 */       System.err.println("java.security.auth.policy" + rb.getString("COLON") + parsingException
/*  308 */           .getMessage());
/*  309 */       if (debug != null) {
/*  310 */         parsingException.printStackTrace();
/*      */       }
/*  312 */     } catch (Exception exception) {
/*  313 */       if (debug != null) {
/*  314 */         debug.println("error parsing " + paramURL);
/*  315 */         debug.println(exception.toString());
/*  316 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CodeSource getCodeSource(PolicyParser.GrantEntry paramGrantEntry, KeyStore paramKeyStore) throws MalformedURLException {
/*      */     URL uRL;
/*  329 */     Certificate[] arrayOfCertificate = null;
/*  330 */     if (paramGrantEntry.signedBy != null) {
/*  331 */       arrayOfCertificate = getCertificates(paramKeyStore, paramGrantEntry.signedBy);
/*  332 */       if (arrayOfCertificate == null) {
/*      */ 
/*      */         
/*  335 */         if (debug != null) {
/*  336 */           debug.println(" no certs for alias " + paramGrantEntry.signedBy + ", ignoring.");
/*      */         }
/*      */         
/*  339 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  344 */     if (paramGrantEntry.codeBase != null) {
/*  345 */       uRL = new URL(paramGrantEntry.codeBase);
/*      */     } else {
/*  347 */       uRL = null;
/*      */     } 
/*      */     
/*  350 */     if (paramGrantEntry.principals == null || paramGrantEntry.principals.size() == 0) {
/*  351 */       return 
/*  352 */         canonicalizeCodebase(new CodeSource(uRL, arrayOfCertificate), false);
/*      */     }
/*      */     
/*  355 */     return 
/*  356 */       canonicalizeCodebase(new SubjectCodeSource(null, paramGrantEntry.principals, uRL, arrayOfCertificate), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addGrantEntry(PolicyParser.GrantEntry paramGrantEntry, KeyStore paramKeyStore) {
/*  366 */     if (debug != null) {
/*  367 */       debug.println("Adding policy entry: ");
/*  368 */       debug.println("  signedBy " + paramGrantEntry.signedBy);
/*  369 */       debug.println("  codeBase " + paramGrantEntry.codeBase);
/*  370 */       if (paramGrantEntry.principals != null) {
/*  371 */         for (PolicyParser.PrincipalEntry principalEntry : paramGrantEntry.principals) {
/*  372 */           debug.println("  " + principalEntry.getPrincipalClass() + " " + principalEntry
/*  373 */               .getPrincipalName());
/*      */         }
/*      */       }
/*  376 */       debug.println();
/*      */     } 
/*      */     
/*      */     try {
/*  380 */       CodeSource codeSource = getCodeSource(paramGrantEntry, paramKeyStore);
/*      */       
/*  382 */       if (codeSource == null)
/*      */         return; 
/*  384 */       PolicyEntry policyEntry = new PolicyEntry(codeSource);
/*  385 */       Enumeration<PolicyParser.PermissionEntry> enumeration = paramGrantEntry.permissionElements();
/*  386 */       while (enumeration.hasMoreElements()) {
/*  387 */         PolicyParser.PermissionEntry permissionEntry = enumeration.nextElement();
/*      */         
/*      */         try {
/*      */           Permission permission;
/*  391 */           if (permissionEntry.permission
/*  392 */             .equals("javax.security.auth.PrivateCredentialPermission") && permissionEntry.name
/*  393 */             .endsWith(" self")) {
/*  394 */             permission = getInstance(permissionEntry.permission, permissionEntry.name + " \"self\"", permissionEntry.action);
/*      */           }
/*      */           else {
/*      */             
/*  398 */             permission = getInstance(permissionEntry.permission, permissionEntry.name, permissionEntry.action);
/*      */           } 
/*      */ 
/*      */           
/*  402 */           policyEntry.add(permission);
/*  403 */           if (debug != null) {
/*  404 */             debug.println("  " + permission);
/*      */           }
/*  406 */         } catch (ClassNotFoundException classNotFoundException) {
/*      */           Certificate[] arrayOfCertificate;
/*  408 */           if (permissionEntry.signedBy != null) {
/*  409 */             arrayOfCertificate = getCertificates(paramKeyStore, permissionEntry.signedBy);
/*      */           } else {
/*  411 */             arrayOfCertificate = null;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  416 */           if (arrayOfCertificate != null || permissionEntry.signedBy == null) {
/*  417 */             UnresolvedPermission unresolvedPermission = new UnresolvedPermission(permissionEntry.permission, permissionEntry.name, permissionEntry.action, arrayOfCertificate);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  422 */             policyEntry.add(unresolvedPermission);
/*  423 */             if (debug != null) {
/*  424 */               debug.println("  " + unresolvedPermission);
/*      */             }
/*      */           } 
/*  427 */         } catch (InvocationTargetException invocationTargetException) {
/*  428 */           System.err
/*  429 */             .println("java.security.auth.policy" + rb
/*  430 */               .getString(".error.adding.Permission.") + permissionEntry.permission + rb
/*      */               
/*  432 */               .getString("SPACE") + invocationTargetException
/*  433 */               .getTargetException());
/*  434 */         } catch (Exception exception) {
/*  435 */           System.err
/*  436 */             .println("java.security.auth.policy" + rb
/*  437 */               .getString(".error.adding.Permission.") + permissionEntry.permission + rb
/*      */               
/*  439 */               .getString("SPACE") + exception);
/*      */         } 
/*      */       } 
/*      */       
/*  443 */       this.policyEntries.addElement(policyEntry);
/*  444 */     } catch (Exception exception) {
/*  445 */       System.err
/*  446 */         .println("java.security.auth.policy" + rb
/*  447 */           .getString(".error.adding.Entry.") + paramGrantEntry + rb
/*      */           
/*  449 */           .getString("SPACE") + exception);
/*      */     } 
/*      */ 
/*      */     
/*  453 */     if (debug != null) {
/*  454 */       debug.println();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Permission getInstance(String paramString1, String paramString2, String paramString3) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
/*  497 */     Class<?> clazz = Class.forName(paramString1);
/*  498 */     Constructor<?> constructor = clazz.getConstructor(PARAMS);
/*  499 */     return (Permission)constructor.newInstance(new Object[] { paramString2, paramString3 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Certificate[] getCertificates(KeyStore paramKeyStore, String paramString) {
/*  507 */     Vector<Certificate> vector = null;
/*      */     
/*  509 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/*  510 */     byte b = 0;
/*      */     
/*  512 */     while (stringTokenizer.hasMoreTokens()) {
/*  513 */       String str = stringTokenizer.nextToken().trim();
/*  514 */       b++;
/*  515 */       Certificate certificate = null;
/*      */       
/*  517 */       certificate = (Certificate)this.aliasMapping.get(str);
/*  518 */       if (certificate == null && paramKeyStore != null) {
/*      */         
/*      */         try {
/*  521 */           certificate = paramKeyStore.getCertificate(str);
/*  522 */         } catch (KeyStoreException keyStoreException) {}
/*      */ 
/*      */ 
/*      */         
/*  526 */         if (certificate != null) {
/*  527 */           this.aliasMapping.put(str, certificate);
/*  528 */           this.aliasMapping.put(certificate, str);
/*      */         } 
/*      */       } 
/*      */       
/*  532 */       if (certificate != null) {
/*  533 */         if (vector == null) {
/*  534 */           vector = new Vector();
/*      */         }
/*  536 */         vector.addElement(certificate);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  541 */     if (vector != null && b == vector.size()) {
/*  542 */       Certificate[] arrayOfCertificate = new Certificate[vector.size()];
/*  543 */       vector.copyInto((Object[])arrayOfCertificate);
/*  544 */       return arrayOfCertificate;
/*      */     } 
/*  546 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final synchronized Enumeration<PolicyEntry> elements() {
/*  557 */     return this.policyEntries.elements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PermissionCollection getPermissions(final Subject subject, final CodeSource codesource) {
/*  572 */     return 
/*  573 */       AccessController.<PermissionCollection>doPrivileged(new PrivilegedAction<PermissionCollection>()
/*      */         {
/*      */           
/*      */           public PermissionCollection run()
/*      */           {
/*  578 */             SubjectCodeSource subjectCodeSource = new SubjectCodeSource(subject, null, (codesource == null) ? null : codesource.getLocation(), (codesource == null) ? null : codesource.getCertificates());
/*  579 */             if (AuthPolicyFile.this.initialized) {
/*  580 */               return AuthPolicyFile.this.getPermissions(new Permissions(), subjectCodeSource);
/*      */             }
/*  582 */             return new PolicyPermissions(AuthPolicyFile.this, subjectCodeSource);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PermissionCollection getPermissions(CodeSource paramCodeSource) {
/*  601 */     if (this.initialized) {
/*  602 */       return getPermissions(new Permissions(), paramCodeSource);
/*      */     }
/*  604 */     return new PolicyPermissions(this, paramCodeSource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Permissions getPermissions(Permissions paramPermissions, CodeSource paramCodeSource) {
/*  623 */     if (!this.initialized) {
/*  624 */       init();
/*      */     }
/*      */     
/*  627 */     CodeSource[] arrayOfCodeSource = { null };
/*      */     
/*  629 */     arrayOfCodeSource[0] = canonicalizeCodebase(paramCodeSource, true);
/*      */     
/*  631 */     if (debug != null) {
/*  632 */       debug.println("evaluate(" + arrayOfCodeSource[0] + ")\n");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  639 */     for (byte b = 0; b < this.policyEntries.size(); b++) {
/*      */       
/*  641 */       PolicyEntry policyEntry = this.policyEntries.elementAt(b);
/*      */       
/*  643 */       if (debug != null) {
/*  644 */         debug.println("PolicyFile CodeSource implies: " + policyEntry.codesource
/*  645 */             .toString() + "\n\n\t" + arrayOfCodeSource[0]
/*  646 */             .toString() + "\n\n");
/*      */       }
/*      */       
/*  649 */       if (policyEntry.codesource.implies(arrayOfCodeSource[0])) {
/*  650 */         for (byte b1 = 0; b1 < policyEntry.permissions.size(); b1++) {
/*  651 */           Permission permission = policyEntry.permissions.elementAt(b1);
/*  652 */           if (debug != null) {
/*  653 */             debug.println("  granting " + permission);
/*      */           }
/*  655 */           if (!addSelfPermissions(permission, policyEntry.codesource, arrayOfCodeSource[0], paramPermissions))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  661 */             paramPermissions.add(permission);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  669 */     if (!this.ignoreIdentityScope) {
/*  670 */       Certificate[] arrayOfCertificate = arrayOfCodeSource[0].getCertificates();
/*  671 */       if (arrayOfCertificate != null) {
/*  672 */         for (byte b1 = 0; b1 < arrayOfCertificate.length; b1++) {
/*  673 */           if (this.aliasMapping.get(arrayOfCertificate[b1]) == null && 
/*  674 */             checkForTrustedIdentity(arrayOfCertificate[b1]))
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  679 */             paramPermissions.add(new AllPermission());
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*  684 */     return paramPermissions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addSelfPermissions(Permission paramPermission, CodeSource paramCodeSource1, CodeSource paramCodeSource2, Permissions paramPermissions) {
/*  708 */     if (!(paramPermission instanceof PrivateCredentialPermission)) {
/*  709 */       return false;
/*      */     }
/*      */     
/*  712 */     if (!(paramCodeSource1 instanceof SubjectCodeSource)) {
/*  713 */       return false;
/*      */     }
/*      */     
/*  716 */     PrivateCredentialPermission privateCredentialPermission = (PrivateCredentialPermission)paramPermission;
/*  717 */     SubjectCodeSource subjectCodeSource = (SubjectCodeSource)paramCodeSource1;
/*      */ 
/*      */     
/*  720 */     String[][] arrayOfString = privateCredentialPermission.getPrincipals();
/*  721 */     if (arrayOfString.length <= 0 || 
/*  722 */       !arrayOfString[0][0].equalsIgnoreCase("self") || 
/*  723 */       !arrayOfString[0][1].equalsIgnoreCase("self"))
/*      */     {
/*      */       
/*  726 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  733 */     if (subjectCodeSource.getPrincipals() == null)
/*      */     {
/*  735 */       return true;
/*      */     }
/*      */     
/*  738 */     for (PolicyParser.PrincipalEntry principalEntry : subjectCodeSource.getPrincipals()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  750 */       String[][] arrayOfString1 = getPrincipalInfo(principalEntry, paramCodeSource2);
/*      */       
/*  752 */       for (byte b = 0; b < arrayOfString1.length; b++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  758 */         PrivateCredentialPermission privateCredentialPermission1 = new PrivateCredentialPermission(privateCredentialPermission.getCredentialClass() + " " + arrayOfString1[b][0] + " \"" + arrayOfString1[b][1] + "\"", "read");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  765 */         if (debug != null) {
/*  766 */           debug.println("adding SELF permission: " + privateCredentialPermission1
/*  767 */               .toString());
/*      */         }
/*      */         
/*  770 */         paramPermissions.add(privateCredentialPermission1);
/*      */       } 
/*      */     } 
/*      */     
/*  774 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[][] getPrincipalInfo(PolicyParser.PrincipalEntry paramPrincipalEntry, CodeSource paramCodeSource) {
/*  792 */     if (!paramPrincipalEntry.getPrincipalClass().equals("WILDCARD_PRINCIPAL_CLASS") && 
/*      */       
/*  794 */       !paramPrincipalEntry.getPrincipalName().equals("WILDCARD_PRINCIPAL_NAME")) {
/*      */ 
/*      */ 
/*      */       
/*  798 */       String[][] arrayOfString1 = new String[1][2];
/*  799 */       arrayOfString1[0][0] = paramPrincipalEntry.getPrincipalClass();
/*  800 */       arrayOfString1[0][1] = paramPrincipalEntry.getPrincipalName();
/*  801 */       return arrayOfString1;
/*      */     } 
/*      */     
/*  804 */     if (!paramPrincipalEntry.getPrincipalClass().equals("WILDCARD_PRINCIPAL_CLASS") && paramPrincipalEntry
/*  805 */       .getPrincipalName()
/*  806 */       .equals("WILDCARD_PRINCIPAL_NAME")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  813 */       SubjectCodeSource subjectCodeSource1 = (SubjectCodeSource)paramCodeSource;
/*      */       
/*  815 */       Set<?> set1 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  821 */         Class<?> clazz = Class.forName(paramPrincipalEntry.getPrincipalClass(), false, 
/*  822 */             ClassLoader.getSystemClassLoader());
/*  823 */         set1 = subjectCodeSource1.getSubject().getPrincipals(clazz);
/*  824 */       } catch (Exception exception) {
/*  825 */         if (debug != null) {
/*  826 */           debug.println("problem finding Principal Class when expanding SELF permission: " + exception
/*      */               
/*  828 */               .toString());
/*      */         }
/*      */       } 
/*      */       
/*  832 */       if (set1 == null)
/*      */       {
/*  834 */         return new String[0][0];
/*      */       }
/*      */       
/*  837 */       String[][] arrayOfString1 = new String[set1.size()][2];
/*      */       
/*  839 */       byte b1 = 0;
/*  840 */       for (Principal principal : set1) {
/*  841 */         arrayOfString1[b1][0] = principal.getClass().getName();
/*  842 */         arrayOfString1[b1][1] = principal.getName();
/*  843 */         b1++;
/*      */       } 
/*  845 */       return arrayOfString1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  854 */     SubjectCodeSource subjectCodeSource = (SubjectCodeSource)paramCodeSource;
/*  855 */     Set<Principal> set = subjectCodeSource.getSubject().getPrincipals();
/*      */     
/*  857 */     String[][] arrayOfString = new String[set.size()][2];
/*      */     
/*  859 */     byte b = 0;
/*  860 */     for (Principal principal : set) {
/*  861 */       arrayOfString[b][0] = principal.getClass().getName();
/*  862 */       arrayOfString[b][1] = principal.getName();
/*  863 */       b++;
/*      */     } 
/*  865 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Certificate[] getSignerCertificates(CodeSource paramCodeSource) {
/*  883 */     Certificate[] arrayOfCertificate1 = null;
/*  884 */     if ((arrayOfCertificate1 = paramCodeSource.getCertificates()) == null)
/*  885 */       return null; 
/*      */     byte b1;
/*  887 */     for (b1 = 0; b1 < arrayOfCertificate1.length; b1++) {
/*  888 */       if (!(arrayOfCertificate1[b1] instanceof X509Certificate)) {
/*  889 */         return paramCodeSource.getCertificates();
/*      */       }
/*      */     } 
/*      */     
/*  893 */     b1 = 0;
/*  894 */     byte b2 = 0;
/*  895 */     while (b1 < arrayOfCertificate1.length) {
/*  896 */       b2++;
/*  897 */       while (b1 + 1 < arrayOfCertificate1.length && ((X509Certificate)arrayOfCertificate1[b1])
/*  898 */         .getIssuerDN().equals(((X509Certificate)arrayOfCertificate1[b1 + 1])
/*  899 */           .getSubjectDN())) {
/*  900 */         b1++;
/*      */       }
/*  902 */       b1++;
/*      */     } 
/*  904 */     if (b2 == arrayOfCertificate1.length)
/*      */     {
/*  906 */       return arrayOfCertificate1;
/*      */     }
/*      */     
/*  909 */     ArrayList<Certificate> arrayList = new ArrayList();
/*  910 */     b1 = 0;
/*  911 */     while (b1 < arrayOfCertificate1.length) {
/*  912 */       arrayList.add(arrayOfCertificate1[b1]);
/*  913 */       while (b1 + 1 < arrayOfCertificate1.length && ((X509Certificate)arrayOfCertificate1[b1])
/*  914 */         .getIssuerDN().equals(((X509Certificate)arrayOfCertificate1[b1 + 1])
/*  915 */           .getSubjectDN())) {
/*  916 */         b1++;
/*      */       }
/*  918 */       b1++;
/*      */     } 
/*  920 */     Certificate[] arrayOfCertificate2 = new Certificate[arrayList.size()];
/*  921 */     arrayList.toArray(arrayOfCertificate2);
/*  922 */     return arrayOfCertificate2;
/*      */   }
/*      */ 
/*      */   
/*      */   private CodeSource canonicalizeCodebase(CodeSource paramCodeSource, boolean paramBoolean) {
/*  927 */     CodeSource codeSource = paramCodeSource;
/*  928 */     if (paramCodeSource.getLocation() != null && paramCodeSource
/*  929 */       .getLocation().getProtocol().equalsIgnoreCase("file")) {
/*      */       
/*      */       try {
/*  932 */         String str = paramCodeSource.getLocation().getFile().replace('/', File.separatorChar);
/*      */         
/*  934 */         URL uRL = null;
/*  935 */         if (str.endsWith("*")) {
/*      */ 
/*      */           
/*  938 */           str = str.substring(0, str.length() - 1);
/*  939 */           boolean bool = false;
/*  940 */           if (str.endsWith(File.separator)) {
/*  941 */             bool = true;
/*      */           }
/*  943 */           if (str.equals("")) {
/*  944 */             str = System.getProperty("user.dir");
/*      */           }
/*  946 */           File file = new File(str);
/*  947 */           str = file.getCanonicalPath();
/*  948 */           StringBuffer stringBuffer = new StringBuffer(str);
/*      */ 
/*      */ 
/*      */           
/*  952 */           if (!str.endsWith(File.separator) && (bool || file
/*  953 */             .isDirectory())) {
/*  954 */             stringBuffer.append(File.separatorChar);
/*      */           }
/*  956 */           stringBuffer.append('*');
/*  957 */           str = stringBuffer.toString();
/*      */         } else {
/*  959 */           str = (new File(str)).getCanonicalPath();
/*      */         } 
/*  961 */         uRL = (new File(str)).toURL();
/*      */         
/*  963 */         if (paramCodeSource instanceof SubjectCodeSource) {
/*  964 */           SubjectCodeSource subjectCodeSource = (SubjectCodeSource)paramCodeSource;
/*  965 */           if (paramBoolean)
/*      */           {
/*      */ 
/*      */             
/*  969 */             codeSource = new SubjectCodeSource(subjectCodeSource.getSubject(), subjectCodeSource.getPrincipals(), uRL, getSignerCertificates(subjectCodeSource));
/*      */           
/*      */           }
/*      */           else
/*      */           {
/*  974 */             codeSource = new SubjectCodeSource(subjectCodeSource.getSubject(), subjectCodeSource.getPrincipals(), uRL, subjectCodeSource.getCertificates());
/*      */           }
/*      */         
/*  977 */         } else if (paramBoolean) {
/*      */           
/*  979 */           codeSource = new CodeSource(uRL, getSignerCertificates(paramCodeSource));
/*      */         } else {
/*      */           
/*  982 */           codeSource = new CodeSource(uRL, paramCodeSource.getCertificates());
/*      */         }
/*      */       
/*  985 */       } catch (IOException iOException) {
/*      */ 
/*      */         
/*  988 */         if (paramBoolean) {
/*  989 */           if (!(paramCodeSource instanceof SubjectCodeSource)) {
/*      */             
/*  991 */             codeSource = new CodeSource(paramCodeSource.getLocation(), getSignerCertificates(paramCodeSource));
/*      */           } else {
/*  993 */             SubjectCodeSource subjectCodeSource = (SubjectCodeSource)paramCodeSource;
/*      */ 
/*      */ 
/*      */             
/*  997 */             codeSource = new SubjectCodeSource(subjectCodeSource.getSubject(), subjectCodeSource.getPrincipals(), subjectCodeSource.getLocation(), getSignerCertificates(subjectCodeSource));
/*      */           }
/*      */         
/*      */         }
/*      */       } 
/* 1002 */     } else if (paramBoolean) {
/* 1003 */       if (!(paramCodeSource instanceof SubjectCodeSource)) {
/*      */         
/* 1005 */         codeSource = new CodeSource(paramCodeSource.getLocation(), getSignerCertificates(paramCodeSource));
/*      */       } else {
/* 1007 */         SubjectCodeSource subjectCodeSource = (SubjectCodeSource)paramCodeSource;
/*      */ 
/*      */ 
/*      */         
/* 1011 */         codeSource = new SubjectCodeSource(subjectCodeSource.getSubject(), subjectCodeSource.getPrincipals(), subjectCodeSource.getLocation(), getSignerCertificates(subjectCodeSource));
/*      */       } 
/*      */     } 
/*      */     
/* 1015 */     return codeSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PolicyEntry
/*      */   {
/*      */     CodeSource codesource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Vector<Permission> permissions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     PolicyEntry(CodeSource param1CodeSource) {
/* 1079 */       this.codesource = param1CodeSource;
/* 1080 */       this.permissions = new Vector<>();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void add(Permission param1Permission) {
/* 1087 */       this.permissions.addElement(param1Permission);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CodeSource getCodeSource() {
/* 1094 */       return this.codesource;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1099 */       StringBuffer stringBuffer = new StringBuffer();
/* 1100 */       stringBuffer.append(AuthPolicyFile.rb.getString("LPARAM"));
/* 1101 */       stringBuffer.append(getCodeSource());
/* 1102 */       stringBuffer.append("\n");
/* 1103 */       for (byte b = 0; b < this.permissions.size(); b++) {
/* 1104 */         Permission permission = this.permissions.elementAt(b);
/* 1105 */         stringBuffer.append(AuthPolicyFile.rb.getString("SPACE"));
/* 1106 */         stringBuffer.append(AuthPolicyFile.rb.getString("SPACE"));
/* 1107 */         stringBuffer.append(permission);
/* 1108 */         stringBuffer.append(AuthPolicyFile.rb.getString("NEWLINE"));
/*      */       } 
/* 1110 */       stringBuffer.append(AuthPolicyFile.rb.getString("RPARAM"));
/* 1111 */       stringBuffer.append(AuthPolicyFile.rb.getString("NEWLINE"));
/* 1112 */       return stringBuffer.toString();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/AuthPolicyFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */