/*      */ package sun.security.provider;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.NetPermission;
/*      */ import java.net.SocketPermission;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.AllPermission;
/*      */ import java.security.CodeSource;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.Permission;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.Permissions;
/*      */ import java.security.Policy;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.security.Security;
/*      */ import java.security.UnresolvedPermission;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.PropertyPermission;
/*      */ import java.util.Random;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.misc.JavaSecurityProtectionDomainAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.net.www.ParseUtil;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.PolicyUtil;
/*      */ import sun.security.util.PropertyExpander;
/*      */ import sun.security.util.ResourcesMgr;
/*      */ import sun.security.util.SecurityConstants;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PolicyFile
/*      */   extends Policy
/*      */ {
/*  259 */   private static final Debug debug = Debug.getInstance("policy");
/*      */   
/*      */   private static final String NONE = "NONE";
/*      */   
/*      */   private static final String P11KEYSTORE = "PKCS11";
/*      */   
/*      */   private static final String SELF = "${{self}}";
/*      */   
/*      */   private static final String X500PRINCIPAL = "javax.security.auth.x500.X500Principal";
/*      */   
/*      */   private static final String POLICY = "java.security.policy";
/*      */   
/*      */   private static final String SECURITY_MANAGER = "java.security.manager";
/*      */   
/*      */   private static final String POLICY_URL = "policy.url.";
/*      */   
/*      */   private static final String AUTH_POLICY = "java.security.auth.policy";
/*      */   
/*      */   private static final String AUTH_POLICY_URL = "auth.policy.url.";
/*      */   
/*      */   private static final int DEFAULT_CACHE_SIZE = 1;
/*      */   
/*      */   private volatile PolicyInfo policyInfo;
/*      */   private boolean constructed = false;
/*      */   private boolean expandProperties = true;
/*      */   private boolean ignoreIdentityScope = true;
/*      */   private boolean allowSystemProperties = true;
/*      */   private boolean notUtf8 = false;
/*      */   private URL url;
/*  288 */   private static final Class[] PARAMS0 = new Class[0];
/*  289 */   private static final Class[] PARAMS1 = new Class[] { String.class };
/*  290 */   private static final Class[] PARAMS2 = new Class[] { String.class, String.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolicyFile() {
/*  297 */     init((URL)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolicyFile(URL paramURL) {
/*  305 */     this.url = paramURL;
/*  306 */     init(paramURL);
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
/*      */   private void init(URL paramURL) {
/*      */     boolean bool;
/*  414 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*      */           public String run() {
/*  416 */             PolicyFile.this.expandProperties = "true"
/*  417 */               .equalsIgnoreCase(Security.getProperty("policy.expandProperties"));
/*  418 */             PolicyFile.this.ignoreIdentityScope = "true"
/*  419 */               .equalsIgnoreCase(Security.getProperty("policy.ignoreIdentityScope"));
/*  420 */             PolicyFile.this.allowSystemProperties = "true"
/*  421 */               .equalsIgnoreCase(Security.getProperty("policy.allowSystemProperty"));
/*  422 */             PolicyFile.this.notUtf8 = "false"
/*  423 */               .equalsIgnoreCase(System.getProperty("sun.security.policy.utf8"));
/*  424 */             return System.getProperty("sun.security.policy.numcaches");
/*      */           }
/*      */         });
/*      */     
/*  428 */     if (str != null) {
/*      */       try {
/*  430 */         bool = Integer.parseInt(str);
/*  431 */       } catch (NumberFormatException numberFormatException) {
/*  432 */         bool = true;
/*      */       } 
/*      */     } else {
/*  435 */       bool = true;
/*      */     } 
/*      */     
/*  438 */     PolicyInfo policyInfo = new PolicyInfo(bool);
/*  439 */     initPolicyFile(policyInfo, paramURL);
/*  440 */     this.policyInfo = policyInfo;
/*      */   }
/*      */ 
/*      */   
/*      */   private void initPolicyFile(final PolicyInfo newInfo, final URL url) {
/*  445 */     if (url != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  452 */       if (debug != null) {
/*  453 */         debug.println("reading " + url);
/*      */       }
/*  455 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */             public Void run() {
/*  457 */               if (!PolicyFile.this.init(url, newInfo))
/*      */               {
/*  459 */                 PolicyFile.this.initStaticPolicy(newInfo);
/*      */               }
/*  461 */               return null;
/*      */             }
/*      */           });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  480 */       boolean bool = initPolicyFile("java.security.policy", "policy.url.", newInfo);
/*      */ 
/*      */       
/*  483 */       if (!bool)
/*      */       {
/*  485 */         initStaticPolicy(newInfo);
/*      */       }
/*      */       
/*  488 */       initPolicyFile("java.security.auth.policy", "auth.policy.url.", newInfo);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean initPolicyFile(final String propname, final String urlname, final PolicyInfo newInfo) {
/*  495 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*      */           public Boolean run() {
/*  497 */             boolean bool = false;
/*      */             
/*  499 */             if (PolicyFile.this.allowSystemProperties) {
/*  500 */               String str1 = System.getProperty(propname);
/*  501 */               if (str1 != null) {
/*  502 */                 boolean bool1 = false;
/*  503 */                 if (str1.startsWith("=")) {
/*  504 */                   bool1 = true;
/*  505 */                   str1 = str1.substring(1);
/*      */                 } 
/*      */                 try {
/*      */                   URL uRL;
/*  509 */                   str1 = PropertyExpander.expand(str1);
/*      */ 
/*      */                   
/*  512 */                   File file = new File(str1);
/*  513 */                   if (file.exists()) {
/*      */                     
/*  515 */                     uRL = ParseUtil.fileToEncodedURL(new File(file.getCanonicalPath()));
/*      */                   } else {
/*  517 */                     uRL = new URL(str1);
/*      */                   } 
/*  519 */                   if (PolicyFile.debug != null)
/*  520 */                     PolicyFile.debug.println("reading " + uRL); 
/*  521 */                   if (PolicyFile.this.init(uRL, newInfo))
/*  522 */                     bool = true; 
/*  523 */                 } catch (Exception exception) {
/*      */                   
/*  525 */                   if (PolicyFile.debug != null) {
/*  526 */                     PolicyFile.debug.println("caught exception: " + exception);
/*      */                   }
/*      */                 } 
/*  529 */                 if (bool1) {
/*  530 */                   if (PolicyFile.debug != null) {
/*  531 */                     PolicyFile.debug.println("overriding other policies!");
/*      */                   }
/*  533 */                   return Boolean.valueOf(bool);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/*  538 */             byte b = 1;
/*      */             
/*      */             String str;
/*  541 */             while ((str = Security.getProperty(urlname + b)) != null) {
/*      */               try {
/*  543 */                 URL uRL = null;
/*      */                 
/*  545 */                 String str1 = PropertyExpander.expand(str).replace(File.separatorChar, '/');
/*      */                 
/*  547 */                 if (str.startsWith("file:${java.home}/") || str
/*  548 */                   .startsWith("file:${user.home}/")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  555 */                   uRL = (new File(str1.substring(5))).toURI().toURL();
/*      */                 } else {
/*  557 */                   uRL = (new URI(str1)).toURL();
/*      */                 } 
/*      */                 
/*  560 */                 if (PolicyFile.debug != null)
/*  561 */                   PolicyFile.debug.println("reading " + uRL); 
/*  562 */                 if (PolicyFile.this.init(uRL, newInfo))
/*  563 */                   bool = true; 
/*  564 */               } catch (Exception exception) {
/*  565 */                 if (PolicyFile.debug != null) {
/*  566 */                   PolicyFile.debug.println("Debug info only. Error reading policy " + exception);
/*      */                   
/*  568 */                   exception.printStackTrace();
/*      */                 } 
/*      */               } 
/*      */               
/*  572 */               b++;
/*      */             } 
/*  574 */             return Boolean.valueOf(bool);
/*      */           }
/*      */         });
/*      */     
/*  578 */     return bool.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean init(URL paramURL, PolicyInfo paramPolicyInfo) {
/*  588 */     boolean bool = false;
/*  589 */     PolicyParser policyParser = new PolicyParser(this.expandProperties);
/*  590 */     InputStreamReader inputStreamReader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  598 */       if (this.notUtf8) {
/*      */         
/*  600 */         inputStreamReader = new InputStreamReader(PolicyUtil.getInputStream(paramURL));
/*      */       } else {
/*      */         
/*  603 */         inputStreamReader = new InputStreamReader(PolicyUtil.getInputStream(paramURL), "UTF-8");
/*      */       } 
/*      */       
/*  606 */       policyParser.read(inputStreamReader);
/*      */       
/*  608 */       KeyStore keyStore = null;
/*      */       
/*      */       try {
/*  611 */         keyStore = PolicyUtil.getKeyStore(paramURL, policyParser
/*  612 */             .getKeyStoreUrl(), policyParser
/*  613 */             .getKeyStoreType(), policyParser
/*  614 */             .getKeyStoreProvider(), policyParser
/*  615 */             .getStorePassURL(), debug);
/*      */       }
/*  617 */       catch (Exception exception) {
/*      */         
/*  619 */         if (debug != null) {
/*  620 */           debug.println("Debug info only. Ignoring exception.");
/*  621 */           exception.printStackTrace();
/*      */         } 
/*      */       } 
/*      */       
/*  625 */       Enumeration<PolicyParser.GrantEntry> enumeration = policyParser.grantElements();
/*  626 */       while (enumeration.hasMoreElements()) {
/*  627 */         PolicyParser.GrantEntry grantEntry = enumeration.nextElement();
/*  628 */         addGrantEntry(grantEntry, keyStore, paramPolicyInfo);
/*      */       } 
/*  630 */     } catch (ParsingException parsingException) {
/*      */       
/*  632 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("java.security.policy.error.parsing.policy.message"));
/*  633 */       Object[] arrayOfObject = { paramURL, parsingException.getLocalizedMessage() };
/*  634 */       System.err.println(messageFormat.format(arrayOfObject));
/*  635 */       if (debug != null) {
/*  636 */         parsingException.printStackTrace();
/*      */       }
/*  638 */     } catch (Exception exception) {
/*  639 */       if (debug != null) {
/*  640 */         debug.println("error parsing " + paramURL);
/*  641 */         debug.println(exception.toString());
/*  642 */         exception.printStackTrace();
/*      */       } 
/*      */     } finally {
/*  645 */       if (inputStreamReader != null) {
/*      */         try {
/*  647 */           inputStreamReader.close();
/*  648 */           bool = true;
/*  649 */         } catch (IOException iOException) {}
/*      */       }
/*      */       else {
/*      */         
/*  653 */         bool = true;
/*      */       } 
/*      */     } 
/*      */     
/*  657 */     return bool;
/*      */   }
/*      */   
/*      */   private void initStaticPolicy(final PolicyInfo newInfo) {
/*  661 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/*  663 */             PolicyFile.PolicyEntry policyEntry = new PolicyFile.PolicyEntry(new CodeSource(null, (Certificate[])null));
/*      */             
/*  665 */             policyEntry.add(SecurityConstants.LOCAL_LISTEN_PERMISSION);
/*  666 */             policyEntry.add(new PropertyPermission("java.version", "read"));
/*      */             
/*  668 */             policyEntry.add(new PropertyPermission("java.vendor", "read"));
/*      */             
/*  670 */             policyEntry.add(new PropertyPermission("java.vendor.url", "read"));
/*      */             
/*  672 */             policyEntry.add(new PropertyPermission("java.class.version", "read"));
/*      */             
/*  674 */             policyEntry.add(new PropertyPermission("os.name", "read"));
/*      */             
/*  676 */             policyEntry.add(new PropertyPermission("os.version", "read"));
/*      */             
/*  678 */             policyEntry.add(new PropertyPermission("os.arch", "read"));
/*      */             
/*  680 */             policyEntry.add(new PropertyPermission("file.separator", "read"));
/*      */             
/*  682 */             policyEntry.add(new PropertyPermission("path.separator", "read"));
/*      */             
/*  684 */             policyEntry.add(new PropertyPermission("line.separator", "read"));
/*      */             
/*  686 */             policyEntry.add(new PropertyPermission("java.specification.version", "read"));
/*      */ 
/*      */             
/*  689 */             policyEntry.add(new PropertyPermission("java.specification.vendor", "read"));
/*      */ 
/*      */             
/*  692 */             policyEntry.add(new PropertyPermission("java.specification.name", "read"));
/*      */ 
/*      */             
/*  695 */             policyEntry.add(new PropertyPermission("java.vm.specification.version", "read"));
/*      */ 
/*      */             
/*  698 */             policyEntry.add(new PropertyPermission("java.vm.specification.vendor", "read"));
/*      */ 
/*      */             
/*  701 */             policyEntry.add(new PropertyPermission("java.vm.specification.name", "read"));
/*      */ 
/*      */             
/*  704 */             policyEntry.add(new PropertyPermission("java.vm.version", "read"));
/*      */             
/*  706 */             policyEntry.add(new PropertyPermission("java.vm.vendor", "read"));
/*      */             
/*  708 */             policyEntry.add(new PropertyPermission("java.vm.name", "read"));
/*      */ 
/*      */ 
/*      */             
/*  712 */             newInfo.policyEntries.add(policyEntry);
/*      */ 
/*      */             
/*  715 */             String[] arrayOfString = PolicyParser.parseExtDirs("${{java.ext.dirs}}", 0);
/*      */             
/*  717 */             if (arrayOfString != null && arrayOfString.length > 0) {
/*  718 */               for (byte b = 0; b < arrayOfString.length; b++) {
/*      */                 try {
/*  720 */                   policyEntry = new PolicyFile.PolicyEntry(PolicyFile.this.canonicalizeCodebase(new CodeSource(new URL(arrayOfString[b]), (Certificate[])null), false));
/*      */ 
/*      */                   
/*  723 */                   policyEntry.add(SecurityConstants.ALL_PERMISSION);
/*      */ 
/*      */ 
/*      */                   
/*  727 */                   newInfo.policyEntries.add(policyEntry);
/*  728 */                 } catch (Exception exception) {}
/*      */               } 
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  734 */             return null;
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
/*      */   private CodeSource getCodeSource(PolicyParser.GrantEntry paramGrantEntry, KeyStore paramKeyStore, PolicyInfo paramPolicyInfo) throws MalformedURLException {
/*      */     URL uRL;
/*  747 */     Certificate[] arrayOfCertificate = null;
/*  748 */     if (paramGrantEntry.signedBy != null) {
/*  749 */       arrayOfCertificate = getCertificates(paramKeyStore, paramGrantEntry.signedBy, paramPolicyInfo);
/*  750 */       if (arrayOfCertificate == null) {
/*      */ 
/*      */         
/*  753 */         if (debug != null) {
/*  754 */           debug.println("  -- No certs for alias '" + paramGrantEntry.signedBy + "' - ignoring entry");
/*      */         }
/*      */         
/*  757 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  763 */     if (paramGrantEntry.codeBase != null) {
/*  764 */       uRL = new URL(paramGrantEntry.codeBase);
/*      */     } else {
/*  766 */       uRL = null;
/*      */     } 
/*  768 */     return canonicalizeCodebase(new CodeSource(uRL, arrayOfCertificate), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addGrantEntry(PolicyParser.GrantEntry paramGrantEntry, KeyStore paramKeyStore, PolicyInfo paramPolicyInfo) {
/*  777 */     if (debug != null) {
/*  778 */       debug.println("Adding policy entry: ");
/*  779 */       debug.println("  signedBy " + paramGrantEntry.signedBy);
/*  780 */       debug.println("  codeBase " + paramGrantEntry.codeBase);
/*  781 */       if (paramGrantEntry.principals != null) {
/*  782 */         for (PolicyParser.PrincipalEntry principalEntry : paramGrantEntry.principals) {
/*  783 */           debug.println("  " + principalEntry.toString());
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  789 */       CodeSource codeSource = getCodeSource(paramGrantEntry, paramKeyStore, paramPolicyInfo);
/*      */       
/*  791 */       if (codeSource == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  797 */       if (!replacePrincipals(paramGrantEntry.principals, paramKeyStore))
/*      */         return; 
/*  799 */       PolicyEntry policyEntry = new PolicyEntry(codeSource, paramGrantEntry.principals);
/*      */       
/*  801 */       Enumeration<PolicyParser.PermissionEntry> enumeration = paramGrantEntry.permissionElements();
/*  802 */       while (enumeration.hasMoreElements()) {
/*  803 */         PolicyParser.PermissionEntry permissionEntry = enumeration.nextElement();
/*      */         
/*      */         try {
/*      */           Permission permission;
/*  807 */           expandPermissionName(permissionEntry, paramKeyStore);
/*      */ 
/*      */ 
/*      */           
/*  811 */           if (permissionEntry.permission
/*  812 */             .equals("javax.security.auth.PrivateCredentialPermission") && permissionEntry.name
/*  813 */             .endsWith(" self")) {
/*  814 */             permissionEntry.name = permissionEntry.name.substring(0, permissionEntry.name.indexOf("self")) + "${{self}}";
/*      */           }
/*      */ 
/*      */           
/*  818 */           if (permissionEntry.name != null && permissionEntry.name.indexOf("${{self}}") != -1) {
/*      */             Certificate[] arrayOfCertificate;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  824 */             if (permissionEntry.signedBy != null) {
/*  825 */               arrayOfCertificate = getCertificates(paramKeyStore, permissionEntry.signedBy, paramPolicyInfo);
/*      */             }
/*      */             else {
/*      */               
/*  829 */               arrayOfCertificate = null;
/*      */             } 
/*  831 */             permission = new SelfPermission(permissionEntry.permission, permissionEntry.name, permissionEntry.action, arrayOfCertificate);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  836 */             permission = getInstance(permissionEntry.permission, permissionEntry.name, permissionEntry.action);
/*      */           } 
/*      */ 
/*      */           
/*  840 */           policyEntry.add(permission);
/*  841 */           if (debug != null) {
/*  842 */             debug.println("  " + permission);
/*      */           }
/*  844 */         } catch (ClassNotFoundException classNotFoundException) {
/*      */           Certificate[] arrayOfCertificate;
/*  846 */           if (permissionEntry.signedBy != null) {
/*  847 */             arrayOfCertificate = getCertificates(paramKeyStore, permissionEntry.signedBy, paramPolicyInfo);
/*      */           }
/*      */           else {
/*      */             
/*  851 */             arrayOfCertificate = null;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  856 */           if (arrayOfCertificate != null || permissionEntry.signedBy == null) {
/*  857 */             UnresolvedPermission unresolvedPermission = new UnresolvedPermission(permissionEntry.permission, permissionEntry.name, permissionEntry.action, arrayOfCertificate);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  862 */             policyEntry.add(unresolvedPermission);
/*  863 */             if (debug != null) {
/*  864 */               debug.println("  " + unresolvedPermission);
/*      */             }
/*      */           } 
/*  867 */         } catch (InvocationTargetException invocationTargetException) {
/*      */ 
/*      */           
/*  870 */           MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("java.security.policy.error.adding.Permission.perm.message"));
/*      */ 
/*      */           
/*  873 */           Object[] arrayOfObject = { permissionEntry.permission, invocationTargetException.getTargetException().toString() };
/*  874 */           System.err.println(messageFormat.format(arrayOfObject));
/*  875 */         } catch (Exception exception) {
/*      */ 
/*      */           
/*  878 */           MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("java.security.policy.error.adding.Permission.perm.message"));
/*      */ 
/*      */           
/*  881 */           Object[] arrayOfObject = { permissionEntry.permission, exception.toString() };
/*  882 */           System.err.println(messageFormat.format(arrayOfObject));
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  887 */       paramPolicyInfo.policyEntries.add(policyEntry);
/*  888 */     } catch (Exception exception) {
/*      */       
/*  890 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("java.security.policy.error.adding.Entry.message"));
/*      */       
/*  892 */       Object[] arrayOfObject = { exception.toString() };
/*  893 */       System.err.println(messageFormat.format(arrayOfObject));
/*      */     } 
/*  895 */     if (debug != null) {
/*  896 */       debug.println();
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
/*  939 */     Class<?> clazz = Class.forName(paramString1, false, null);
/*  940 */     Permission permission = getKnownInstance(clazz, paramString2, paramString3);
/*  941 */     if (permission != null) {
/*  942 */       return permission;
/*      */     }
/*  944 */     if (!Permission.class.isAssignableFrom(clazz))
/*      */     {
/*  946 */       throw new ClassCastException(paramString1 + " is not a Permission");
/*      */     }
/*      */     
/*  949 */     if (paramString2 == null && paramString3 == null) {
/*      */       try {
/*  951 */         Constructor<?> constructor1 = clazz.getConstructor(PARAMS0);
/*  952 */         return (Permission)constructor1.newInstance(new Object[0]);
/*  953 */       } catch (NoSuchMethodException noSuchMethodException) {
/*      */         try {
/*  955 */           Constructor<?> constructor1 = clazz.getConstructor(PARAMS1);
/*  956 */           return (Permission)constructor1.newInstance(new Object[] { paramString2 });
/*      */         }
/*  958 */         catch (NoSuchMethodException noSuchMethodException1) {
/*  959 */           Constructor<?> constructor1 = clazz.getConstructor(PARAMS2);
/*  960 */           return (Permission)constructor1.newInstance(new Object[] { paramString2, paramString3 });
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  965 */     if (paramString2 != null && paramString3 == null) {
/*      */       try {
/*  967 */         Constructor<?> constructor1 = clazz.getConstructor(PARAMS1);
/*  968 */         return (Permission)constructor1.newInstance(new Object[] { paramString2 });
/*  969 */       } catch (NoSuchMethodException noSuchMethodException) {
/*  970 */         Constructor<?> constructor1 = clazz.getConstructor(PARAMS2);
/*  971 */         return (Permission)constructor1.newInstance(new Object[] { paramString2, paramString3 });
/*      */       } 
/*      */     }
/*      */     
/*  975 */     Constructor<?> constructor = clazz.getConstructor(PARAMS2);
/*  976 */     return (Permission)constructor.newInstance(new Object[] { paramString2, paramString3 });
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
/*      */   private static final Permission getKnownInstance(Class<?> paramClass, String paramString1, String paramString2) {
/*  989 */     if (paramClass.equals(FilePermission.class))
/*  990 */       return new FilePermission(paramString1, paramString2); 
/*  991 */     if (paramClass.equals(SocketPermission.class))
/*  992 */       return new SocketPermission(paramString1, paramString2); 
/*  993 */     if (paramClass.equals(RuntimePermission.class))
/*  994 */       return new RuntimePermission(paramString1, paramString2); 
/*  995 */     if (paramClass.equals(PropertyPermission.class))
/*  996 */       return new PropertyPermission(paramString1, paramString2); 
/*  997 */     if (paramClass.equals(NetPermission.class))
/*  998 */       return new NetPermission(paramString1, paramString2); 
/*  999 */     if (paramClass.equals(AllPermission.class)) {
/* 1000 */       return SecurityConstants.ALL_PERMISSION;
/*      */     }
/* 1002 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Certificate[] getCertificates(KeyStore paramKeyStore, String paramString, PolicyInfo paramPolicyInfo) {
/* 1012 */     ArrayList<Certificate> arrayList = null;
/*      */     
/* 1014 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/* 1015 */     byte b = 0;
/*      */     
/* 1017 */     while (stringTokenizer.hasMoreTokens()) {
/* 1018 */       String str = stringTokenizer.nextToken().trim();
/* 1019 */       b++;
/* 1020 */       Certificate certificate = null;
/*      */       
/* 1022 */       synchronized (paramPolicyInfo.aliasMapping) {
/* 1023 */         certificate = (Certificate)paramPolicyInfo.aliasMapping.get(str);
/*      */         
/* 1025 */         if (certificate == null && paramKeyStore != null) {
/*      */           
/*      */           try {
/* 1028 */             certificate = paramKeyStore.getCertificate(str);
/* 1029 */           } catch (KeyStoreException keyStoreException) {}
/*      */ 
/*      */ 
/*      */           
/* 1033 */           if (certificate != null) {
/* 1034 */             paramPolicyInfo.aliasMapping.put(str, certificate);
/* 1035 */             paramPolicyInfo.aliasMapping.put(certificate, str);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1040 */       if (certificate != null) {
/* 1041 */         if (arrayList == null)
/* 1042 */           arrayList = new ArrayList(); 
/* 1043 */         arrayList.add(certificate);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1048 */     if (arrayList != null && b == arrayList.size()) {
/* 1049 */       Certificate[] arrayOfCertificate = new Certificate[arrayList.size()];
/* 1050 */       arrayList.toArray(arrayOfCertificate);
/* 1051 */       return arrayOfCertificate;
/*      */     } 
/* 1053 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refresh() {
/* 1061 */     init(this.url);
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
/*      */   public boolean implies(ProtectionDomain paramProtectionDomain, Permission paramPermission) {
/* 1079 */     JavaSecurityProtectionDomainAccess.ProtectionDomainCache protectionDomainCache = this.policyInfo.getPdMapping();
/* 1080 */     PermissionCollection permissionCollection = protectionDomainCache.get(paramProtectionDomain);
/*      */     
/* 1082 */     if (permissionCollection != null) {
/* 1083 */       return permissionCollection.implies(paramPermission);
/*      */     }
/*      */     
/* 1086 */     permissionCollection = getPermissions(paramProtectionDomain);
/* 1087 */     if (permissionCollection == null) {
/* 1088 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1092 */     protectionDomainCache.put(paramProtectionDomain, permissionCollection);
/* 1093 */     return permissionCollection.implies(paramPermission);
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
/*      */   public PermissionCollection getPermissions(ProtectionDomain paramProtectionDomain) {
/* 1126 */     Permissions permissions = new Permissions();
/*      */     
/* 1128 */     if (paramProtectionDomain == null) {
/* 1129 */       return permissions;
/*      */     }
/*      */     
/* 1132 */     getPermissions(permissions, paramProtectionDomain);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1137 */     PermissionCollection permissionCollection = paramProtectionDomain.getPermissions();
/* 1138 */     if (permissionCollection != null) {
/* 1139 */       synchronized (permissionCollection) {
/* 1140 */         Enumeration<Permission> enumeration = permissionCollection.elements();
/* 1141 */         while (enumeration.hasMoreElements()) {
/* 1142 */           permissions.add(enumeration.nextElement());
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1147 */     return permissions;
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
/*      */   public PermissionCollection getPermissions(CodeSource paramCodeSource) {
/* 1162 */     return getPermissions(new Permissions(), paramCodeSource);
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
/*      */   private PermissionCollection getPermissions(Permissions paramPermissions, ProtectionDomain paramProtectionDomain) {
/* 1177 */     if (debug != null) {
/* 1178 */       debug.println("getPermissions:\n\t" + printPD(paramProtectionDomain));
/*      */     }
/*      */     
/* 1181 */     final CodeSource cs = paramProtectionDomain.getCodeSource();
/* 1182 */     if (codeSource1 == null) {
/* 1183 */       return paramPermissions;
/*      */     }
/* 1185 */     CodeSource codeSource2 = AccessController.<CodeSource>doPrivileged(new PrivilegedAction<CodeSource>()
/*      */         {
/*      */           public CodeSource run() {
/* 1188 */             return PolicyFile.this.canonicalizeCodebase(cs, true);
/*      */           }
/*      */         });
/* 1191 */     return getPermissions(paramPermissions, codeSource2, paramProtectionDomain.getPrincipals());
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
/*      */   private PermissionCollection getPermissions(Permissions paramPermissions, final CodeSource cs) {
/* 1209 */     if (cs == null) {
/* 1210 */       return paramPermissions;
/*      */     }
/* 1212 */     CodeSource codeSource = AccessController.<CodeSource>doPrivileged(new PrivilegedAction<CodeSource>()
/*      */         {
/*      */           public CodeSource run() {
/* 1215 */             return PolicyFile.this.canonicalizeCodebase(cs, true);
/*      */           }
/*      */         });
/*      */     
/* 1219 */     return getPermissions(paramPermissions, codeSource, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Permissions getPermissions(Permissions paramPermissions, CodeSource paramCodeSource, Principal[] paramArrayOfPrincipal) {
/* 1225 */     PolicyInfo policyInfo = this.policyInfo;
/*      */     
/* 1227 */     for (PolicyEntry policyEntry : policyInfo.policyEntries) {
/* 1228 */       addPermissions(paramPermissions, paramCodeSource, paramArrayOfPrincipal, policyEntry);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1233 */     synchronized (policyInfo.identityPolicyEntries) {
/* 1234 */       for (PolicyEntry policyEntry : policyInfo.identityPolicyEntries) {
/* 1235 */         addPermissions(paramPermissions, paramCodeSource, paramArrayOfPrincipal, policyEntry);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1240 */     if (!this.ignoreIdentityScope) {
/* 1241 */       Certificate[] arrayOfCertificate = paramCodeSource.getCertificates();
/* 1242 */       if (arrayOfCertificate != null) {
/* 1243 */         for (byte b = 0; b < arrayOfCertificate.length; b++) {
/* 1244 */           Object object = policyInfo.aliasMapping.get(arrayOfCertificate[b]);
/* 1245 */           if (object == null && 
/* 1246 */             checkForTrustedIdentity(arrayOfCertificate[b], policyInfo))
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 1251 */             paramPermissions.add(SecurityConstants.ALL_PERMISSION);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/* 1256 */     return paramPermissions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addPermissions(Permissions paramPermissions, final CodeSource cs, Principal[] paramArrayOfPrincipal, final PolicyEntry entry) {
/* 1264 */     if (debug != null) {
/* 1265 */       debug.println("evaluate codesources:\n\tPolicy CodeSource: " + entry
/* 1266 */           .getCodeSource() + "\n\tActive CodeSource: " + cs);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1272 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*      */           public Boolean run() {
/* 1274 */             return new Boolean(entry.getCodeSource().implies(cs));
/*      */           }
/*      */         });
/* 1277 */     if (!bool.booleanValue()) {
/* 1278 */       if (debug != null) {
/* 1279 */         debug.println("evaluation (codesource) failed");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1288 */     List<PolicyParser.PrincipalEntry> list = entry.getPrincipals();
/* 1289 */     if (debug != null) {
/* 1290 */       ArrayList<PolicyParser.PrincipalEntry> arrayList = new ArrayList();
/* 1291 */       if (paramArrayOfPrincipal != null) {
/* 1292 */         for (byte b = 0; b < paramArrayOfPrincipal.length; b++) {
/* 1293 */           arrayList.add(new PolicyParser.PrincipalEntry(paramArrayOfPrincipal[b]
/* 1294 */                 .getClass().getName(), paramArrayOfPrincipal[b]
/* 1295 */                 .getName()));
/*      */         }
/*      */       }
/* 1298 */       debug.println("evaluate principals:\n\tPolicy Principals: " + list + "\n\tActive Principals: " + arrayList);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1303 */     if (list == null || list.isEmpty()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1308 */       addPerms(paramPermissions, paramArrayOfPrincipal, entry);
/* 1309 */       if (debug != null) {
/* 1310 */         debug.println("evaluation (codesource/principals) passed");
/*      */       }
/*      */       return;
/*      */     } 
/* 1314 */     if (paramArrayOfPrincipal == null || paramArrayOfPrincipal.length == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1319 */       if (debug != null) {
/* 1320 */         debug.println("evaluation (principals) failed");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1329 */     for (PolicyParser.PrincipalEntry principalEntry : list) {
/*      */ 
/*      */       
/* 1332 */       if (principalEntry.isWildcardClass()) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1337 */       if (principalEntry.isWildcardName()) {
/*      */         
/* 1339 */         if (wildcardPrincipalNameImplies(principalEntry.principalClass, paramArrayOfPrincipal)) {
/*      */           continue;
/*      */         }
/*      */         
/* 1343 */         if (debug != null) {
/* 1344 */           debug.println("evaluation (principal name wildcard) failed");
/*      */         }
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1351 */       HashSet<? extends Principal> hashSet = new HashSet(Arrays.asList((Object[])paramArrayOfPrincipal));
/* 1352 */       Subject subject = new Subject(true, hashSet, Collections.EMPTY_SET, Collections.EMPTY_SET);
/*      */ 
/*      */       
/*      */       try {
/* 1356 */         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 1357 */         Class<?> clazz = Class.forName(principalEntry.principalClass, false, classLoader);
/* 1358 */         if (!Principal.class.isAssignableFrom(clazz))
/*      */         {
/* 1360 */           throw new ClassCastException(principalEntry.principalClass + " is not a Principal");
/*      */         }
/*      */ 
/*      */         
/* 1364 */         Constructor<?> constructor = clazz.getConstructor(PARAMS1);
/* 1365 */         Principal principal = (Principal)constructor.newInstance(new Object[] { principalEntry.principalName });
/*      */ 
/*      */         
/* 1368 */         if (debug != null) {
/* 1369 */           debug.println("found Principal " + principal.getClass().getName());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1374 */         if (!principal.implies(subject)) {
/* 1375 */           if (debug != null) {
/* 1376 */             debug.println("evaluation (principal implies) failed");
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/* 1383 */       } catch (Exception exception) {
/*      */ 
/*      */ 
/*      */         
/* 1387 */         if (debug != null) {
/* 1388 */           exception.printStackTrace();
/*      */         }
/*      */         
/* 1391 */         if (!principalEntry.implies(subject)) {
/* 1392 */           if (debug != null) {
/* 1393 */             debug.println("evaluation (default principal implies) failed");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1410 */     if (debug != null) {
/* 1411 */       debug.println("evaluation (codesource/principals) passed");
/*      */     }
/* 1413 */     addPerms(paramPermissions, paramArrayOfPrincipal, entry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean wildcardPrincipalNameImplies(String paramString, Principal[] paramArrayOfPrincipal) {
/* 1423 */     for (Principal principal : paramArrayOfPrincipal) {
/* 1424 */       if (paramString.equals(principal.getClass().getName())) {
/* 1425 */         return true;
/*      */       }
/*      */     } 
/* 1428 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addPerms(Permissions paramPermissions, Principal[] paramArrayOfPrincipal, PolicyEntry paramPolicyEntry) {
/* 1434 */     for (byte b = 0; b < paramPolicyEntry.permissions.size(); b++) {
/* 1435 */       Permission permission = paramPolicyEntry.permissions.get(b);
/* 1436 */       if (debug != null) {
/* 1437 */         debug.println("  granting " + permission);
/*      */       }
/*      */       
/* 1440 */       if (permission instanceof SelfPermission) {
/*      */         
/* 1442 */         expandSelf((SelfPermission)permission, paramPolicyEntry
/* 1443 */             .getPrincipals(), paramArrayOfPrincipal, paramPermissions);
/*      */       }
/*      */       else {
/*      */         
/* 1447 */         paramPermissions.add(permission);
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
/*      */   private void expandSelf(SelfPermission paramSelfPermission, List<PolicyParser.PrincipalEntry> paramList, Principal[] paramArrayOfPrincipal, Permissions paramPermissions) {
/* 1470 */     if (paramList == null || paramList.isEmpty()) {
/*      */       
/* 1472 */       if (debug != null) {
/* 1473 */         debug.println("Ignoring permission " + paramSelfPermission
/* 1474 */             .getSelfType() + " with target name (" + paramSelfPermission
/*      */             
/* 1476 */             .getSelfName() + ").  No Principal(s) specified in the grant clause.  SELF-based target names are only valid in the context of a Principal-based grant entry.");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1486 */     int i = 0;
/*      */     
/* 1488 */     StringBuilder stringBuilder = new StringBuilder(); int j;
/* 1489 */     while ((j = paramSelfPermission.getSelfName().indexOf("${{self}}", i)) != -1) {
/*      */ 
/*      */       
/* 1492 */       stringBuilder.append(paramSelfPermission.getSelfName().substring(i, j));
/*      */ 
/*      */       
/* 1495 */       Iterator<PolicyParser.PrincipalEntry> iterator = paramList.iterator();
/* 1496 */       while (iterator.hasNext()) {
/* 1497 */         PolicyParser.PrincipalEntry principalEntry = iterator.next();
/* 1498 */         String[][] arrayOfString = getPrincipalInfo(principalEntry, paramArrayOfPrincipal);
/* 1499 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 1500 */           if (b != 0) {
/* 1501 */             stringBuilder.append(", ");
/*      */           }
/* 1503 */           stringBuilder.append(arrayOfString[b][0] + " \"" + arrayOfString[b][1] + "\"");
/*      */         } 
/*      */         
/* 1506 */         if (iterator.hasNext()) {
/* 1507 */           stringBuilder.append(", ");
/*      */         }
/*      */       } 
/* 1510 */       i = j + "${{self}}".length();
/*      */     } 
/*      */     
/* 1513 */     stringBuilder.append(paramSelfPermission.getSelfName().substring(i));
/*      */     
/* 1515 */     if (debug != null) {
/* 1516 */       debug.println("  expanded:\n\t" + paramSelfPermission.getSelfName() + "\n  into:\n\t" + stringBuilder
/* 1517 */           .toString());
/*      */     }
/*      */     
/*      */     try {
/* 1521 */       paramPermissions.add(getInstance(paramSelfPermission.getSelfType(), stringBuilder
/* 1522 */             .toString(), paramSelfPermission
/* 1523 */             .getSelfActions()));
/* 1524 */     } catch (ClassNotFoundException classNotFoundException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1530 */       Class<?> clazz = null;
/* 1531 */       synchronized (paramPermissions) {
/* 1532 */         Enumeration<Permission> enumeration = paramPermissions.elements();
/* 1533 */         while (enumeration.hasMoreElements()) {
/* 1534 */           Permission permission = enumeration.nextElement();
/* 1535 */           if (permission.getClass().getName().equals(paramSelfPermission.getSelfType())) {
/* 1536 */             clazz = permission.getClass();
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1541 */       if (clazz == null) {
/*      */         
/* 1543 */         paramPermissions.add(new UnresolvedPermission(paramSelfPermission.getSelfType(), stringBuilder
/* 1544 */               .toString(), paramSelfPermission
/* 1545 */               .getSelfActions(), paramSelfPermission
/* 1546 */               .getCerts()));
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 1553 */           if (paramSelfPermission.getSelfActions() == null) {
/*      */             try {
/* 1555 */               Constructor<?> constructor = clazz.getConstructor(PARAMS1);
/* 1556 */               paramPermissions.add((Permission)constructor
/* 1557 */                   .newInstance(new Object[] { stringBuilder.toString() }));
/* 1558 */             } catch (NoSuchMethodException noSuchMethodException) {
/* 1559 */               Constructor<?> constructor = clazz.getConstructor(PARAMS2);
/* 1560 */               paramPermissions.add((Permission)constructor
/* 1561 */                   .newInstance(new Object[] { stringBuilder.toString(), paramSelfPermission
/* 1562 */                       .getSelfActions() }));
/*      */             } 
/*      */           } else {
/* 1565 */             Constructor<?> constructor = clazz.getConstructor(PARAMS2);
/* 1566 */             paramPermissions.add((Permission)constructor
/* 1567 */                 .newInstance(new Object[] { stringBuilder.toString(), paramSelfPermission
/* 1568 */                     .getSelfActions() }));
/*      */           } 
/* 1570 */         } catch (Exception exception) {
/* 1571 */           if (debug != null) {
/* 1572 */             debug.println("self entry expansion  instantiation failed: " + exception
/*      */                 
/* 1574 */                 .toString());
/*      */           }
/*      */         } 
/*      */       } 
/* 1578 */     } catch (Exception exception) {
/* 1579 */       if (debug != null) {
/* 1580 */         debug.println(exception.toString());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[][] getPrincipalInfo(PolicyParser.PrincipalEntry paramPrincipalEntry, Principal[] paramArrayOfPrincipal) {
/* 1599 */     if (!paramPrincipalEntry.isWildcardClass() && !paramPrincipalEntry.isWildcardName()) {
/*      */ 
/*      */ 
/*      */       
/* 1603 */       String[][] arrayOfString1 = new String[1][2];
/* 1604 */       arrayOfString1[0][0] = paramPrincipalEntry.principalClass;
/* 1605 */       arrayOfString1[0][1] = paramPrincipalEntry.principalName;
/* 1606 */       return arrayOfString1;
/*      */     } 
/* 1608 */     if (!paramPrincipalEntry.isWildcardClass() && paramPrincipalEntry.isWildcardName()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1613 */       ArrayList<Principal> arrayList = new ArrayList();
/* 1614 */       for (byte b1 = 0; b1 < paramArrayOfPrincipal.length; b1++) {
/* 1615 */         if (paramPrincipalEntry.principalClass.equals(paramArrayOfPrincipal[b1].getClass().getName()))
/* 1616 */           arrayList.add(paramArrayOfPrincipal[b1]); 
/*      */       } 
/* 1618 */       String[][] arrayOfString1 = new String[arrayList.size()][2];
/* 1619 */       byte b2 = 0;
/* 1620 */       for (Principal principal : arrayList) {
/* 1621 */         arrayOfString1[b2][0] = principal.getClass().getName();
/* 1622 */         arrayOfString1[b2][1] = principal.getName();
/* 1623 */         b2++;
/*      */       } 
/* 1625 */       return arrayOfString1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1632 */     String[][] arrayOfString = new String[paramArrayOfPrincipal.length][2];
/*      */     
/* 1634 */     for (byte b = 0; b < paramArrayOfPrincipal.length; b++) {
/* 1635 */       arrayOfString[b][0] = paramArrayOfPrincipal[b].getClass().getName();
/* 1636 */       arrayOfString[b][1] = paramArrayOfPrincipal[b].getName();
/*      */     } 
/* 1638 */     return arrayOfString;
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
/*      */   protected Certificate[] getSignerCertificates(CodeSource paramCodeSource) {
/* 1657 */     Certificate[] arrayOfCertificate1 = null;
/* 1658 */     if ((arrayOfCertificate1 = paramCodeSource.getCertificates()) == null)
/* 1659 */       return null;  byte b1;
/* 1660 */     for (b1 = 0; b1 < arrayOfCertificate1.length; b1++) {
/* 1661 */       if (!(arrayOfCertificate1[b1] instanceof X509Certificate)) {
/* 1662 */         return paramCodeSource.getCertificates();
/*      */       }
/*      */     } 
/*      */     
/* 1666 */     b1 = 0;
/* 1667 */     byte b2 = 0;
/* 1668 */     while (b1 < arrayOfCertificate1.length) {
/* 1669 */       b2++;
/* 1670 */       while (b1 + 1 < arrayOfCertificate1.length && ((X509Certificate)arrayOfCertificate1[b1])
/* 1671 */         .getIssuerDN().equals(((X509Certificate)arrayOfCertificate1[b1 + 1])
/* 1672 */           .getSubjectDN())) {
/* 1673 */         b1++;
/*      */       }
/* 1675 */       b1++;
/*      */     } 
/* 1677 */     if (b2 == arrayOfCertificate1.length)
/*      */     {
/* 1679 */       return arrayOfCertificate1;
/*      */     }
/* 1681 */     ArrayList<Certificate> arrayList = new ArrayList();
/* 1682 */     b1 = 0;
/* 1683 */     while (b1 < arrayOfCertificate1.length) {
/* 1684 */       arrayList.add(arrayOfCertificate1[b1]);
/* 1685 */       while (b1 + 1 < arrayOfCertificate1.length && ((X509Certificate)arrayOfCertificate1[b1])
/* 1686 */         .getIssuerDN().equals(((X509Certificate)arrayOfCertificate1[b1 + 1])
/* 1687 */           .getSubjectDN())) {
/* 1688 */         b1++;
/*      */       }
/* 1690 */       b1++;
/*      */     } 
/* 1692 */     Certificate[] arrayOfCertificate2 = new Certificate[arrayList.size()];
/* 1693 */     arrayList.toArray(arrayOfCertificate2);
/* 1694 */     return arrayOfCertificate2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private CodeSource canonicalizeCodebase(CodeSource paramCodeSource, boolean paramBoolean) {
/* 1700 */     String str = null;
/*      */     
/* 1702 */     CodeSource codeSource = paramCodeSource;
/* 1703 */     URL uRL = paramCodeSource.getLocation();
/* 1704 */     if (uRL != null) {
/* 1705 */       if (uRL.getProtocol().equals("jar")) {
/*      */         
/* 1707 */         String str1 = uRL.getFile();
/* 1708 */         int i = str1.indexOf("!/");
/* 1709 */         if (i != -1) {
/*      */           try {
/* 1711 */             uRL = new URL(str1.substring(0, i));
/* 1712 */           } catch (MalformedURLException malformedURLException) {}
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1718 */       if (uRL.getProtocol().equals("file")) {
/* 1719 */         boolean bool = false;
/* 1720 */         String str1 = uRL.getHost();
/*      */         
/* 1722 */         bool = (str1 == null || str1.equals("") || str1.equals("~") || str1.equalsIgnoreCase("localhost")) ? true : false;
/*      */         
/* 1724 */         if (bool) {
/* 1725 */           str = uRL.getFile().replace('/', File.separatorChar);
/* 1726 */           str = ParseUtil.decode(str);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1731 */     if (str != null) {
/*      */       try {
/* 1733 */         URL uRL1 = null;
/* 1734 */         str = canonPath(str);
/* 1735 */         uRL1 = ParseUtil.fileToEncodedURL(new File(str));
/*      */         
/* 1737 */         if (paramBoolean) {
/*      */           
/* 1739 */           codeSource = new CodeSource(uRL1, getSignerCertificates(paramCodeSource));
/*      */         } else {
/*      */           
/* 1742 */           codeSource = new CodeSource(uRL1, paramCodeSource.getCertificates());
/*      */         } 
/* 1744 */       } catch (IOException iOException) {
/*      */ 
/*      */         
/* 1747 */         if (paramBoolean)
/*      */         {
/* 1749 */           codeSource = new CodeSource(paramCodeSource.getLocation(), getSignerCertificates(paramCodeSource));
/*      */         }
/*      */       }
/*      */     
/* 1753 */     } else if (paramBoolean) {
/*      */       
/* 1755 */       codeSource = new CodeSource(paramCodeSource.getLocation(), getSignerCertificates(paramCodeSource));
/*      */     } 
/*      */     
/* 1758 */     return codeSource;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String canonPath(String paramString) throws IOException {
/* 1764 */     if (paramString.endsWith("*")) {
/* 1765 */       paramString = paramString.substring(0, paramString.length() - 1) + "-";
/* 1766 */       paramString = (new File(paramString)).getCanonicalPath();
/* 1767 */       return paramString.substring(0, paramString.length() - 1) + "*";
/*      */     } 
/* 1769 */     return (new File(paramString)).getCanonicalPath();
/*      */   }
/*      */ 
/*      */   
/*      */   private String printPD(ProtectionDomain paramProtectionDomain) {
/* 1774 */     Principal[] arrayOfPrincipal = paramProtectionDomain.getPrincipals();
/* 1775 */     String str = "<no principals>";
/* 1776 */     if (arrayOfPrincipal != null && arrayOfPrincipal.length > 0) {
/* 1777 */       StringBuilder stringBuilder = new StringBuilder("(principals ");
/* 1778 */       for (byte b = 0; b < arrayOfPrincipal.length; b++) {
/* 1779 */         stringBuilder.append(arrayOfPrincipal[b].getClass().getName() + " \"" + arrayOfPrincipal[b]
/* 1780 */             .getName() + "\"");
/*      */         
/* 1782 */         if (b < arrayOfPrincipal.length - 1) {
/* 1783 */           stringBuilder.append(", ");
/*      */         } else {
/* 1785 */           stringBuilder.append(")");
/*      */         } 
/* 1787 */       }  str = stringBuilder.toString();
/*      */     } 
/* 1789 */     return "PD CodeSource: " + paramProtectionDomain
/* 1790 */       .getCodeSource() + "\n\tPD ClassLoader: " + paramProtectionDomain
/*      */       
/* 1792 */       .getClassLoader() + "\n\tPD Principals: " + str;
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
/*      */   private boolean replacePrincipals(List<PolicyParser.PrincipalEntry> paramList, KeyStore paramKeyStore) {
/* 1804 */     if (paramList == null || paramList.isEmpty() || paramKeyStore == null) {
/* 1805 */       return true;
/*      */     }
/* 1807 */     for (PolicyParser.PrincipalEntry principalEntry : paramList) {
/* 1808 */       if (principalEntry.isReplaceName()) {
/*      */         String str;
/*      */ 
/*      */ 
/*      */         
/* 1813 */         if ((str = getDN(principalEntry.principalName, paramKeyStore)) == null) {
/* 1814 */           return false;
/*      */         }
/*      */         
/* 1817 */         if (debug != null) {
/* 1818 */           debug.println("  Replacing \"" + principalEntry.principalName + "\" with " + "javax.security.auth.x500.X500Principal" + "/\"" + str + "\"");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1826 */         principalEntry.principalClass = "javax.security.auth.x500.X500Principal";
/* 1827 */         principalEntry.principalName = str;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1832 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void expandPermissionName(PolicyParser.PermissionEntry paramPermissionEntry, KeyStore paramKeyStore) throws Exception {
/* 1838 */     if (paramPermissionEntry.name == null || paramPermissionEntry.name.indexOf("${{", 0) == -1) {
/*      */       return;
/*      */     }
/*      */     
/* 1842 */     int i = 0;
/*      */     
/* 1844 */     StringBuilder stringBuilder = new StringBuilder(); int j;
/* 1845 */     while ((j = paramPermissionEntry.name.indexOf("${{", i)) != -1) {
/* 1846 */       int k = paramPermissionEntry.name.indexOf("}}", j);
/* 1847 */       if (k < 1) {
/*      */         break;
/*      */       }
/* 1850 */       stringBuilder.append(paramPermissionEntry.name.substring(i, j));
/*      */ 
/*      */       
/* 1853 */       String str1 = paramPermissionEntry.name.substring(j + 3, k);
/*      */ 
/*      */ 
/*      */       
/* 1857 */       String str2 = str1;
/*      */       int m;
/* 1859 */       if ((m = str1.indexOf(":")) != -1) {
/* 1860 */         str2 = str1.substring(0, m);
/*      */       }
/*      */ 
/*      */       
/* 1864 */       if (str2.equalsIgnoreCase("self")) {
/*      */         
/* 1866 */         stringBuilder.append(paramPermissionEntry.name.substring(j, k + 2));
/* 1867 */         i = k + 2; continue;
/*      */       } 
/* 1869 */       if (str2.equalsIgnoreCase("alias")) {
/*      */         
/* 1871 */         if (m == -1) {
/*      */ 
/*      */           
/* 1874 */           MessageFormat messageFormat1 = new MessageFormat(ResourcesMgr.getString("alias.name.not.provided.pe.name."));
/* 1875 */           Object[] arrayOfObject1 = { paramPermissionEntry.name };
/* 1876 */           throw new Exception(messageFormat1.format(arrayOfObject1));
/*      */         } 
/* 1878 */         String str = str1.substring(m + 1);
/* 1879 */         if ((str = getDN(str, paramKeyStore)) == null) {
/*      */ 
/*      */           
/* 1882 */           MessageFormat messageFormat1 = new MessageFormat(ResourcesMgr.getString("unable.to.perform.substitution.on.alias.suffix"));
/* 1883 */           Object[] arrayOfObject1 = { str1.substring(m + 1) };
/* 1884 */           throw new Exception(messageFormat1.format(arrayOfObject1));
/*      */         } 
/*      */         
/* 1887 */         stringBuilder.append("javax.security.auth.x500.X500Principal \"" + str + "\"");
/* 1888 */         i = k + 2;
/*      */         
/*      */         continue;
/*      */       } 
/* 1892 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("substitution.value.prefix.unsupported"));
/* 1893 */       Object[] arrayOfObject = { str2 };
/* 1894 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1899 */     stringBuilder.append(paramPermissionEntry.name.substring(i));
/*      */ 
/*      */     
/* 1902 */     if (debug != null) {
/* 1903 */       debug.println("  Permission name expanded from:\n\t" + paramPermissionEntry.name + "\nto\n\t" + stringBuilder
/* 1904 */           .toString());
/*      */     }
/* 1906 */     paramPermissionEntry.name = stringBuilder.toString();
/*      */   }
/*      */   
/*      */   private String getDN(String paramString, KeyStore paramKeyStore) {
/* 1910 */     Certificate certificate = null;
/*      */     try {
/* 1912 */       certificate = paramKeyStore.getCertificate(paramString);
/* 1913 */     } catch (Exception exception) {
/* 1914 */       if (debug != null) {
/* 1915 */         debug.println("  Error retrieving certificate for '" + paramString + "': " + exception
/*      */ 
/*      */             
/* 1918 */             .toString());
/*      */       }
/* 1920 */       return null;
/*      */     } 
/*      */     
/* 1923 */     if (certificate == null || !(certificate instanceof X509Certificate)) {
/* 1924 */       if (debug != null) {
/* 1925 */         debug.println("  -- No certificate for '" + paramString + "' - ignoring entry");
/*      */       }
/*      */ 
/*      */       
/* 1929 */       return null;
/*      */     } 
/* 1931 */     X509Certificate x509Certificate = (X509Certificate)certificate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1938 */     X500Principal x500Principal = new X500Principal(x509Certificate.getSubjectX500Principal().toString());
/* 1939 */     return x500Principal.getName();
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
/*      */   private boolean checkForTrustedIdentity(Certificate paramCertificate, PolicyInfo paramPolicyInfo) {
/* 1951 */     return false;
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
/*      */   private static class PolicyEntry
/*      */   {
/*      */     private final CodeSource codesource;
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
/*      */     final List<Permission> permissions;
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
/*      */     private final List<PolicyParser.PrincipalEntry> principals;
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
/*      */     PolicyEntry(CodeSource param1CodeSource, List<PolicyParser.PrincipalEntry> param1List) {
/* 2019 */       this.codesource = param1CodeSource;
/* 2020 */       this.permissions = new ArrayList<>();
/* 2021 */       this.principals = param1List;
/*      */     }
/*      */ 
/*      */     
/*      */     PolicyEntry(CodeSource param1CodeSource) {
/* 2026 */       this(param1CodeSource, null);
/*      */     }
/*      */     
/*      */     List<PolicyParser.PrincipalEntry> getPrincipals() {
/* 2030 */       return this.principals;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void add(Permission param1Permission) {
/* 2039 */       this.permissions.add(param1Permission);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CodeSource getCodeSource() {
/* 2046 */       return this.codesource;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2050 */       StringBuilder stringBuilder = new StringBuilder();
/* 2051 */       stringBuilder.append(ResourcesMgr.getString("LPARAM"));
/* 2052 */       stringBuilder.append(getCodeSource());
/* 2053 */       stringBuilder.append("\n");
/* 2054 */       for (byte b = 0; b < this.permissions.size(); b++) {
/* 2055 */         Permission permission = this.permissions.get(b);
/* 2056 */         stringBuilder.append(ResourcesMgr.getString("SPACE"));
/* 2057 */         stringBuilder.append(ResourcesMgr.getString("SPACE"));
/* 2058 */         stringBuilder.append(permission);
/* 2059 */         stringBuilder.append(ResourcesMgr.getString("NEWLINE"));
/*      */       } 
/* 2061 */       stringBuilder.append(ResourcesMgr.getString("RPARAM"));
/* 2062 */       stringBuilder.append(ResourcesMgr.getString("NEWLINE"));
/* 2063 */       return stringBuilder.toString();
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
/*      */   private static class SelfPermission
/*      */     extends Permission
/*      */   {
/*      */     private static final long serialVersionUID = -8315562579967246806L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String type;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String actions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Certificate[] certs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SelfPermission(String param1String1, String param1String2, String param1String3, Certificate[] param1ArrayOfCertificate) {
/* 2116 */       super(param1String1);
/* 2117 */       if (param1String1 == null) {
/* 2118 */         throw new NullPointerException(
/* 2119 */             ResourcesMgr.getString("type.can.t.be.null"));
/*      */       }
/* 2121 */       this.type = param1String1;
/* 2122 */       this.name = param1String2;
/* 2123 */       this.actions = param1String3;
/* 2124 */       if (param1ArrayOfCertificate != null) {
/*      */         byte b;
/* 2126 */         for (b = 0; b < param1ArrayOfCertificate.length; b++) {
/* 2127 */           if (!(param1ArrayOfCertificate[b] instanceof X509Certificate)) {
/*      */ 
/*      */             
/* 2130 */             this.certs = (Certificate[])param1ArrayOfCertificate.clone();
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 2135 */         if (this.certs == null) {
/*      */ 
/*      */           
/* 2138 */           b = 0;
/* 2139 */           byte b1 = 0;
/* 2140 */           while (b < param1ArrayOfCertificate.length) {
/* 2141 */             b1++;
/* 2142 */             while (b + 1 < param1ArrayOfCertificate.length && ((X509Certificate)param1ArrayOfCertificate[b])
/* 2143 */               .getIssuerDN().equals(((X509Certificate)param1ArrayOfCertificate[b + 1])
/* 2144 */                 .getSubjectDN())) {
/* 2145 */               b++;
/*      */             }
/* 2147 */             b++;
/*      */           } 
/* 2149 */           if (b1 == param1ArrayOfCertificate.length)
/*      */           {
/*      */             
/* 2152 */             this.certs = (Certificate[])param1ArrayOfCertificate.clone();
/*      */           }
/*      */           
/* 2155 */           if (this.certs == null) {
/*      */             
/* 2157 */             ArrayList<Certificate> arrayList = new ArrayList();
/* 2158 */             b = 0;
/* 2159 */             while (b < param1ArrayOfCertificate.length) {
/* 2160 */               arrayList.add(param1ArrayOfCertificate[b]);
/* 2161 */               while (b + 1 < param1ArrayOfCertificate.length && ((X509Certificate)param1ArrayOfCertificate[b])
/* 2162 */                 .getIssuerDN().equals(((X509Certificate)param1ArrayOfCertificate[b + 1])
/* 2163 */                   .getSubjectDN())) {
/* 2164 */                 b++;
/*      */               }
/* 2166 */               b++;
/*      */             } 
/* 2168 */             this.certs = new Certificate[arrayList.size()];
/* 2169 */             arrayList.toArray(this.certs);
/*      */           } 
/*      */         } 
/*      */       } 
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
/*      */     public boolean implies(Permission param1Permission) {
/* 2185 */       return false;
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
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2202 */       if (param1Object == this) {
/* 2203 */         return true;
/*      */       }
/* 2205 */       if (!(param1Object instanceof SelfPermission))
/* 2206 */         return false; 
/* 2207 */       SelfPermission selfPermission = (SelfPermission)param1Object;
/*      */       
/* 2209 */       if (!this.type.equals(selfPermission.type) || 
/* 2210 */         !this.name.equals(selfPermission.name) || 
/* 2211 */         !this.actions.equals(selfPermission.actions)) {
/* 2212 */         return false;
/*      */       }
/* 2214 */       if (this.certs.length != selfPermission.certs.length) {
/* 2215 */         return false;
/*      */       }
/*      */       
/*      */       byte b;
/*      */       
/* 2220 */       for (b = 0; b < this.certs.length; b++) {
/* 2221 */         boolean bool = false;
/* 2222 */         for (byte b1 = 0; b1 < selfPermission.certs.length; b1++) {
/* 2223 */           if (this.certs[b].equals(selfPermission.certs[b1])) {
/* 2224 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/* 2228 */         if (!bool) return false;
/*      */       
/*      */       } 
/* 2231 */       for (b = 0; b < selfPermission.certs.length; b++) {
/* 2232 */         boolean bool = false;
/* 2233 */         for (byte b1 = 0; b1 < this.certs.length; b1++) {
/* 2234 */           if (selfPermission.certs[b].equals(this.certs[b1])) {
/* 2235 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/* 2239 */         if (!bool) return false; 
/*      */       } 
/* 2241 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 2250 */       int i = this.type.hashCode();
/* 2251 */       if (this.name != null)
/* 2252 */         i ^= this.name.hashCode(); 
/* 2253 */       if (this.actions != null)
/* 2254 */         i ^= this.actions.hashCode(); 
/* 2255 */       return i;
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
/*      */     public String getActions() {
/* 2269 */       return "";
/*      */     }
/*      */     
/*      */     public String getSelfType() {
/* 2273 */       return this.type;
/*      */     }
/*      */     
/*      */     public String getSelfName() {
/* 2277 */       return this.name;
/*      */     }
/*      */     
/*      */     public String getSelfActions() {
/* 2281 */       return this.actions;
/*      */     }
/*      */     
/*      */     public Certificate[] getCerts() {
/* 2285 */       return this.certs;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2296 */       return "(SelfPermission " + this.type + " " + this.name + " " + this.actions + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PolicyInfo
/*      */   {
/*      */     private static final boolean verbose = false;
/*      */ 
/*      */     
/*      */     final List<PolicyFile.PolicyEntry> policyEntries;
/*      */ 
/*      */     
/*      */     final List<PolicyFile.PolicyEntry> identityPolicyEntries;
/*      */ 
/*      */     
/*      */     final Map<Object, Object> aliasMapping;
/*      */     
/*      */     private final JavaSecurityProtectionDomainAccess.ProtectionDomainCache[] pdMapping;
/*      */     
/*      */     private Random random;
/*      */ 
/*      */     
/*      */     PolicyInfo(int param1Int) {
/* 2321 */       this.policyEntries = new ArrayList<>();
/* 2322 */       this
/* 2323 */         .identityPolicyEntries = Collections.synchronizedList(new ArrayList<>(2));
/* 2324 */       this.aliasMapping = Collections.synchronizedMap(new HashMap<>(11));
/*      */       
/* 2326 */       this.pdMapping = new JavaSecurityProtectionDomainAccess.ProtectionDomainCache[param1Int];
/*      */       
/* 2328 */       JavaSecurityProtectionDomainAccess javaSecurityProtectionDomainAccess = SharedSecrets.getJavaSecurityProtectionDomainAccess();
/* 2329 */       for (byte b = 0; b < param1Int; b++) {
/* 2330 */         this.pdMapping[b] = javaSecurityProtectionDomainAccess.getProtectionDomainCache();
/*      */       }
/* 2332 */       if (param1Int > 1)
/* 2333 */         this.random = new Random(); 
/*      */     }
/*      */     
/*      */     JavaSecurityProtectionDomainAccess.ProtectionDomainCache getPdMapping() {
/* 2337 */       if (this.pdMapping.length == 1) {
/* 2338 */         return this.pdMapping[0];
/*      */       }
/* 2340 */       int i = Math.abs(this.random.nextInt() % this.pdMapping.length);
/* 2341 */       return this.pdMapping[i];
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/PolicyFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */