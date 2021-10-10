/*     */ package sun.security.tools.policytool;
/*     */ 
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Permission;
/*     */ import java.security.PublicKey;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.text.Collator;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import sun.security.provider.PolicyParser;
/*     */ import sun.security.util.PolicyUtil;
/*     */ import sun.security.util.PropertyExpander;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyTool
/*     */ {
/*  74 */   static final ResourceBundle rb = ResourceBundle.getBundle("sun.security.tools.policytool.Resources");
/*     */   
/*  76 */   static final Collator collator = Collator.getInstance();
/*     */   
/*     */   static {
/*  79 */     collator.setStrength(0);
/*     */ 
/*     */     
/*  82 */     if (System.getProperty("apple.laf.useScreenMenuBar") == null) {
/*  83 */       System.setProperty("apple.laf.useScreenMenuBar", "true");
/*     */     }
/*  85 */     System.setProperty("apple.awt.application.name", getMessage("Policy.Tool"));
/*     */ 
/*     */     
/*  88 */     if (System.getProperty("swing.defaultlaf") == null) {
/*     */       try {
/*  90 */         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*  91 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Vector<String> warnings;
/*     */ 
/*     */   
/*     */   boolean newWarning = false;
/*     */   
/*     */   boolean modified = false;
/*     */   
/*     */   private static final boolean testing = false;
/*     */   
/* 106 */   private static final Class<?>[] TWOPARAMS = new Class[] { String.class, String.class };
/* 107 */   private static final Class<?>[] ONEPARAMS = new Class[] { String.class };
/* 108 */   private static final Class<?>[] NOPARAMS = new Class[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   private static String policyFileName = null;
/* 117 */   private Vector<PolicyEntry> policyEntries = null;
/* 118 */   private PolicyParser parser = null;
/*     */ 
/*     */   
/* 121 */   private KeyStore keyStore = null;
/* 122 */   private String keyStoreName = " ";
/* 123 */   private String keyStoreType = " ";
/* 124 */   private String keyStoreProvider = " ";
/* 125 */   private String keyStorePwdURL = " ";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String P11KEYSTORE = "PKCS11";
/*     */ 
/*     */   
/*     */   private static final String NONE = "NONE";
/*     */ 
/*     */ 
/*     */   
/*     */   private PolicyTool() {
/* 137 */     this.policyEntries = new Vector<>();
/* 138 */     this.parser = new PolicyParser();
/* 139 */     this.warnings = new Vector<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getPolicyFileName() {
/* 146 */     return policyFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setPolicyFileName(String paramString) {
/* 153 */     policyFileName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clearKeyStoreInfo() {
/* 160 */     this.keyStoreName = null;
/* 161 */     this.keyStoreType = null;
/* 162 */     this.keyStoreProvider = null;
/* 163 */     this.keyStorePwdURL = null;
/*     */     
/* 165 */     this.keyStore = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getKeyStoreName() {
/* 172 */     return this.keyStoreName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getKeyStoreType() {
/* 179 */     return this.keyStoreType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getKeyStoreProvider() {
/* 186 */     return this.keyStoreProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getKeyStorePwdURL() {
/* 193 */     return this.keyStorePwdURL;
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
/*     */   void openPolicy(String paramString) throws FileNotFoundException, PolicyParser.ParsingException, KeyStoreException, CertificateException, InstantiationException, MalformedURLException, IOException, NoSuchAlgorithmException, IllegalAccessException, NoSuchMethodException, UnrecoverableKeyException, NoSuchProviderException, ClassNotFoundException, PropertyExpander.ExpandException, InvocationTargetException {
/* 215 */     this.newWarning = false;
/*     */ 
/*     */     
/* 218 */     this.policyEntries = new Vector<>();
/* 219 */     this.parser = new PolicyParser();
/* 220 */     this.warnings = new Vector<>();
/* 221 */     setPolicyFileName(null);
/* 222 */     clearKeyStoreInfo();
/*     */ 
/*     */     
/* 225 */     if (paramString == null) {
/* 226 */       this.modified = false;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     setPolicyFileName(paramString);
/* 236 */     this.parser.read(new FileReader(paramString));
/*     */ 
/*     */     
/* 239 */     openKeyStore(this.parser.getKeyStoreUrl(), this.parser.getKeyStoreType(), this.parser
/* 240 */         .getKeyStoreProvider(), this.parser.getStorePassURL());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     Enumeration<PolicyParser.GrantEntry> enumeration = this.parser.grantElements();
/* 246 */     while (enumeration.hasMoreElements()) {
/* 247 */       PolicyParser.GrantEntry grantEntry = enumeration.nextElement();
/*     */ 
/*     */       
/* 250 */       if (grantEntry.signedBy != null) {
/*     */         
/* 252 */         String[] arrayOfString = parseSigners(grantEntry.signedBy);
/* 253 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 254 */           PublicKey publicKey = getPublicKeyAlias(arrayOfString[b]);
/* 255 */           if (publicKey == null) {
/* 256 */             this.newWarning = true;
/*     */             
/* 258 */             MessageFormat messageFormat = new MessageFormat(getMessage("Warning.A.public.key.for.alias.signers.i.does.not.exist.Make.sure.a.KeyStore.is.properly.configured."));
/* 259 */             Object[] arrayOfObject = { arrayOfString[b] };
/* 260 */             this.warnings.addElement(messageFormat.format(arrayOfObject));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 267 */       ListIterator<PolicyParser.PrincipalEntry> listIterator = grantEntry.principals.listIterator(0);
/* 268 */       while (listIterator.hasNext()) {
/* 269 */         PolicyParser.PrincipalEntry principalEntry = listIterator.next();
/*     */         try {
/* 271 */           verifyPrincipal(principalEntry.getPrincipalClass(), principalEntry
/* 272 */               .getPrincipalName());
/* 273 */         } catch (ClassNotFoundException classNotFoundException) {
/* 274 */           this.newWarning = true;
/*     */           
/* 276 */           MessageFormat messageFormat = new MessageFormat(getMessage("Warning.Class.not.found.class"));
/* 277 */           Object[] arrayOfObject = { principalEntry.getPrincipalClass() };
/* 278 */           this.warnings.addElement(messageFormat.format(arrayOfObject));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 284 */       Enumeration<PolicyParser.PermissionEntry> enumeration1 = grantEntry.permissionElements();
/* 285 */       while (enumeration1.hasMoreElements()) {
/* 286 */         PolicyParser.PermissionEntry permissionEntry = enumeration1.nextElement();
/*     */         try {
/* 288 */           verifyPermission(permissionEntry.permission, permissionEntry.name, permissionEntry.action);
/* 289 */         } catch (ClassNotFoundException classNotFoundException) {
/* 290 */           this.newWarning = true;
/*     */           
/* 292 */           MessageFormat messageFormat = new MessageFormat(getMessage("Warning.Class.not.found.class"));
/* 293 */           Object[] arrayOfObject = { permissionEntry.permission };
/* 294 */           this.warnings.addElement(messageFormat.format(arrayOfObject));
/* 295 */         } catch (InvocationTargetException invocationTargetException) {
/* 296 */           this.newWarning = true;
/*     */           
/* 298 */           MessageFormat messageFormat = new MessageFormat(getMessage("Warning.Invalid.argument.s.for.constructor.arg"));
/* 299 */           Object[] arrayOfObject = { permissionEntry.permission };
/* 300 */           this.warnings.addElement(messageFormat.format(arrayOfObject));
/*     */         } 
/*     */ 
/*     */         
/* 304 */         if (permissionEntry.signedBy != null) {
/*     */           
/* 306 */           String[] arrayOfString = parseSigners(permissionEntry.signedBy);
/*     */           
/* 308 */           for (byte b = 0; b < arrayOfString.length; b++) {
/* 309 */             PublicKey publicKey = getPublicKeyAlias(arrayOfString[b]);
/* 310 */             if (publicKey == null) {
/* 311 */               this.newWarning = true;
/*     */               
/* 313 */               MessageFormat messageFormat = new MessageFormat(getMessage("Warning.A.public.key.for.alias.signers.i.does.not.exist.Make.sure.a.KeyStore.is.properly.configured."));
/* 314 */               Object[] arrayOfObject = { arrayOfString[b] };
/* 315 */               this.warnings.addElement(messageFormat.format(arrayOfObject));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 320 */       PolicyEntry policyEntry = new PolicyEntry(this, grantEntry);
/* 321 */       this.policyEntries.addElement(policyEntry);
/*     */     } 
/*     */ 
/*     */     
/* 325 */     this.modified = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void savePolicy(String paramString) throws FileNotFoundException, IOException {
/* 335 */     this.parser.setKeyStoreUrl(this.keyStoreName);
/* 336 */     this.parser.setKeyStoreType(this.keyStoreType);
/* 337 */     this.parser.setKeyStoreProvider(this.keyStoreProvider);
/* 338 */     this.parser.setStorePassURL(this.keyStorePwdURL);
/* 339 */     this.parser.write(new FileWriter(paramString));
/* 340 */     this.modified = false;
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
/*     */   void openKeyStore(String paramString1, String paramString2, String paramString3, String paramString4) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, CertificateException, NoSuchProviderException, PropertyExpander.ExpandException {
/* 357 */     if (paramString1 == null && paramString2 == null && paramString3 == null && paramString4 == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 363 */       this.keyStoreName = null;
/* 364 */       this.keyStoreType = null;
/* 365 */       this.keyStoreProvider = null;
/* 366 */       this.keyStorePwdURL = null;
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 373 */     URL uRL = null;
/* 374 */     if (policyFileName != null) {
/* 375 */       File file = new File(policyFileName);
/* 376 */       uRL = new URL("file:" + file.getCanonicalPath());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     if (paramString1 != null && paramString1.length() > 0)
/*     */     {
/* 386 */       paramString1 = PropertyExpander.expand(paramString1).replace(File.separatorChar, '/');
/*     */     }
/* 388 */     if (paramString2 == null || paramString2.length() == 0) {
/* 389 */       paramString2 = KeyStore.getDefaultType();
/*     */     }
/* 391 */     if (paramString4 != null && paramString4.length() > 0)
/*     */     {
/* 393 */       paramString4 = PropertyExpander.expand(paramString4).replace(File.separatorChar, '/');
/*     */     }
/*     */     
/*     */     try {
/* 397 */       this.keyStore = PolicyUtil.getKeyStore(uRL, paramString1, paramString2, paramString3, paramString4, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 403 */     catch (IOException iOException) {
/*     */ 
/*     */       
/* 406 */       String str = "no password provided, and no callback handler available for retrieving password";
/*     */ 
/*     */       
/* 409 */       Throwable throwable = iOException.getCause();
/* 410 */       if (throwable != null && throwable instanceof javax.security.auth.login.LoginException && str
/*     */         
/* 412 */         .equals(throwable.getMessage()))
/*     */       {
/*     */         
/* 415 */         throw new IOException(str);
/*     */       }
/* 417 */       throw iOException;
/*     */     } 
/*     */ 
/*     */     
/* 421 */     this.keyStoreName = paramString1;
/* 422 */     this.keyStoreType = paramString2;
/* 423 */     this.keyStoreProvider = paramString3;
/* 424 */     this.keyStorePwdURL = paramString4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean addEntry(PolicyEntry paramPolicyEntry, int paramInt) {
/* 435 */     if (paramInt < 0) {
/*     */       
/* 437 */       this.policyEntries.addElement(paramPolicyEntry);
/* 438 */       this.parser.add(paramPolicyEntry.getGrantEntry());
/*     */     } else {
/*     */       
/* 441 */       PolicyEntry policyEntry = this.policyEntries.elementAt(paramInt);
/* 442 */       this.parser.replace(policyEntry.getGrantEntry(), paramPolicyEntry.getGrantEntry());
/* 443 */       this.policyEntries.setElementAt(paramPolicyEntry, paramInt);
/*     */     } 
/* 445 */     return true;
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
/*     */   boolean addPrinEntry(PolicyEntry paramPolicyEntry, PolicyParser.PrincipalEntry paramPrincipalEntry, int paramInt) {
/* 459 */     PolicyParser.GrantEntry grantEntry = paramPolicyEntry.getGrantEntry();
/* 460 */     if (grantEntry.contains(paramPrincipalEntry) == true) {
/* 461 */       return false;
/*     */     }
/* 463 */     LinkedList<PolicyParser.PrincipalEntry> linkedList = grantEntry.principals;
/*     */     
/* 465 */     if (paramInt != -1) {
/* 466 */       linkedList.set(paramInt, paramPrincipalEntry);
/*     */     } else {
/* 468 */       linkedList.add(paramPrincipalEntry);
/*     */     } 
/* 470 */     this.modified = true;
/* 471 */     return true;
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
/*     */   boolean addPermEntry(PolicyEntry paramPolicyEntry, PolicyParser.PermissionEntry paramPermissionEntry, int paramInt) {
/* 485 */     PolicyParser.GrantEntry grantEntry = paramPolicyEntry.getGrantEntry();
/* 486 */     if (grantEntry.contains(paramPermissionEntry) == true) {
/* 487 */       return false;
/*     */     }
/* 489 */     Vector<PolicyParser.PermissionEntry> vector = grantEntry.permissionEntries;
/*     */     
/* 491 */     if (paramInt != -1) {
/* 492 */       vector.setElementAt(paramPermissionEntry, paramInt);
/*     */     } else {
/* 494 */       vector.addElement(paramPermissionEntry);
/*     */     } 
/* 496 */     this.modified = true;
/* 497 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean removePermEntry(PolicyEntry paramPolicyEntry, PolicyParser.PermissionEntry paramPermissionEntry) {
/* 507 */     PolicyParser.GrantEntry grantEntry = paramPolicyEntry.getGrantEntry();
/* 508 */     this.modified = grantEntry.remove(paramPermissionEntry);
/* 509 */     return this.modified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean removeEntry(PolicyEntry paramPolicyEntry) {
/* 517 */     this.parser.remove(paramPolicyEntry.getGrantEntry());
/* 518 */     this.modified = true;
/* 519 */     return this.policyEntries.removeElement(paramPolicyEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PolicyEntry[] getEntry() {
/* 527 */     if (this.policyEntries.size() > 0) {
/* 528 */       PolicyEntry[] arrayOfPolicyEntry = new PolicyEntry[this.policyEntries.size()];
/* 529 */       for (byte b = 0; b < this.policyEntries.size(); b++)
/* 530 */         arrayOfPolicyEntry[b] = this.policyEntries.elementAt(b); 
/* 531 */       return arrayOfPolicyEntry;
/*     */     } 
/* 533 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PublicKey getPublicKeyAlias(String paramString) throws KeyStoreException {
/* 541 */     if (this.keyStore == null) {
/* 542 */       return null;
/*     */     }
/*     */     
/* 545 */     Certificate certificate = this.keyStore.getCertificate(paramString);
/* 546 */     if (certificate == null) {
/* 547 */       return null;
/*     */     }
/* 549 */     return certificate.getPublicKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String[] getPublicKeyAlias() throws KeyStoreException {
/* 558 */     byte b = 0;
/* 559 */     String[] arrayOfString = null;
/*     */     
/* 561 */     if (this.keyStore == null) {
/* 562 */       return null;
/*     */     }
/* 564 */     Enumeration<String> enumeration = this.keyStore.aliases();
/*     */ 
/*     */     
/* 567 */     while (enumeration.hasMoreElements()) {
/* 568 */       enumeration.nextElement();
/* 569 */       b++;
/*     */     } 
/*     */     
/* 572 */     if (b > 0) {
/*     */       
/* 574 */       arrayOfString = new String[b];
/* 575 */       b = 0;
/* 576 */       enumeration = this.keyStore.aliases();
/* 577 */       while (enumeration.hasMoreElements()) {
/* 578 */         arrayOfString[b] = new String(enumeration.nextElement());
/* 579 */         b++;
/*     */       } 
/*     */     } 
/* 582 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String[] parseSigners(String paramString) {
/* 591 */     String[] arrayOfString = null;
/* 592 */     byte b1 = 1;
/* 593 */     int i = 0;
/* 594 */     int j = 0;
/* 595 */     byte b2 = 0;
/*     */ 
/*     */     
/* 598 */     while (j) {
/* 599 */       j = paramString.indexOf(',', i);
/* 600 */       if (j >= 0) {
/* 601 */         b1++;
/* 602 */         i = j + 1;
/*     */       } 
/*     */     } 
/* 605 */     arrayOfString = new String[b1];
/*     */ 
/*     */     
/* 608 */     j = 0;
/* 609 */     i = 0;
/* 610 */     while (j >= 0) {
/* 611 */       if ((j = paramString.indexOf(',', i)) >= 0) {
/*     */         
/* 613 */         arrayOfString[b2] = paramString
/* 614 */           .substring(i, j).trim();
/* 615 */         b2++;
/* 616 */         i = j + 1;
/*     */         continue;
/*     */       } 
/* 619 */       arrayOfString[b2] = paramString.substring(i).trim();
/*     */     } 
/*     */     
/* 622 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void verifyPrincipal(String paramString1, String paramString2) throws ClassNotFoundException, InstantiationException {
/* 632 */     if (paramString1.equals("WILDCARD_PRINCIPAL_CLASS") || paramString1
/* 633 */       .equals("PolicyParser.REPLACE_NAME")) {
/*     */       return;
/*     */     }
/* 636 */     Class<?> clazz1 = Class.forName("java.security.Principal");
/* 637 */     Class<?> clazz2 = Class.forName(paramString1, true, 
/* 638 */         Thread.currentThread().getContextClassLoader());
/* 639 */     if (!clazz1.isAssignableFrom(clazz2)) {
/*     */       
/* 641 */       MessageFormat messageFormat = new MessageFormat(getMessage("Illegal.Principal.Type.type"));
/* 642 */       Object[] arrayOfObject = { paramString1 };
/* 643 */       throw new InstantiationException(messageFormat.format(arrayOfObject));
/*     */     } 
/*     */     
/* 646 */     if ("javax.security.auth.x500.X500Principal".equals(clazz2.getName()))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 652 */       X500Principal x500Principal = new X500Principal(paramString2);
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
/*     */   void verifyPermission(String paramString1, String paramString2, String paramString3) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
/* 671 */     Class<?> clazz = Class.forName(paramString1, true, 
/* 672 */         Thread.currentThread().getContextClassLoader());
/* 673 */     Constructor<?> constructor = null;
/* 674 */     Vector<String> vector = new Vector(2);
/* 675 */     if (paramString2 != null) vector.add(paramString2); 
/* 676 */     if (paramString3 != null) vector.add(paramString3); 
/* 677 */     switch (vector.size()) {
/*     */       case 0:
/*     */         try {
/* 680 */           constructor = clazz.getConstructor(NOPARAMS);
/*     */           break;
/* 682 */         } catch (NoSuchMethodException noSuchMethodException) {
/*     */           
/* 684 */           vector.add(null);
/*     */         } 
/*     */       
/*     */       case 1:
/*     */         try {
/* 689 */           constructor = clazz.getConstructor(ONEPARAMS);
/*     */           break;
/* 691 */         } catch (NoSuchMethodException noSuchMethodException) {
/*     */           
/* 693 */           vector.add(null);
/*     */         } 
/*     */       
/*     */       case 2:
/* 697 */         constructor = clazz.getConstructor(TWOPARAMS);
/*     */         break;
/*     */     } 
/* 700 */     Object[] arrayOfObject = vector.toArray();
/* 701 */     Permission permission = (Permission)constructor.newInstance(arrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void parseArgs(String[] paramArrayOfString) {
/* 709 */     byte b = 0;
/*     */     
/* 711 */     for (b = 0; b < paramArrayOfString.length && paramArrayOfString[b].startsWith("-"); b++) {
/*     */       
/* 713 */       String str = paramArrayOfString[b];
/*     */       
/* 715 */       if (collator.compare(str, "-file") == 0) {
/* 716 */         if (++b == paramArrayOfString.length) usage(); 
/* 717 */         policyFileName = paramArrayOfString[b];
/*     */       } else {
/*     */         
/* 720 */         MessageFormat messageFormat = new MessageFormat(getMessage("Illegal.option.option"));
/* 721 */         Object[] arrayOfObject = { str };
/* 722 */         System.err.println(messageFormat.format(arrayOfObject));
/* 723 */         usage();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void usage() {
/* 729 */     System.out.println(getMessage("Usage.policytool.options."));
/* 730 */     System.out.println();
/* 731 */     System.out.println(
/* 732 */         getMessage(".file.file.policy.file.location"));
/* 733 */     System.out.println();
/*     */     
/* 735 */     System.exit(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(final String[] args) {
/* 742 */     parseArgs(args);
/* 743 */     SwingUtilities.invokeLater(new Runnable() {
/*     */           public void run() {
/* 745 */             ToolWindow toolWindow = new ToolWindow(new PolicyTool());
/* 746 */             toolWindow.displayToolWindow(args);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String splitToWords(String paramString) {
/* 755 */     return paramString.replaceAll("([A-Z])", " $1");
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
/*     */   static String getMessage(String paramString) {
/* 768 */     return removeMnemonicAmpersand(rb.getString(paramString));
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
/*     */   static int getMnemonicInt(String paramString) {
/* 780 */     String str = rb.getString(paramString);
/* 781 */     return findMnemonicInt(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getDisplayedMnemonicIndex(String paramString) {
/* 792 */     String str = rb.getString(paramString);
/* 793 */     return findMnemonicIndex(str);
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
/*     */   private static int findMnemonicInt(String paramString) {
/* 806 */     for (byte b = 0; b < paramString.length() - 1; b++) {
/* 807 */       if (paramString.charAt(b) == '&') {
/* 808 */         if (paramString.charAt(b + 1) != '&') {
/* 809 */           return KeyEvent.getExtendedKeyCodeForChar(paramString.charAt(b + 1));
/*     */         }
/* 811 */         b++;
/*     */       } 
/*     */     } 
/*     */     
/* 815 */     return 0;
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
/*     */   private static int findMnemonicIndex(String paramString) {
/* 828 */     for (byte b = 0; b < paramString.length() - 1; b++) {
/* 829 */       if (paramString.charAt(b) == '&') {
/* 830 */         if (paramString.charAt(b + 1) != '&')
/*     */         {
/* 832 */           return b;
/*     */         }
/* 834 */         b++;
/*     */       } 
/*     */     } 
/*     */     
/* 838 */     return -1;
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
/*     */   private static String removeMnemonicAmpersand(String paramString) {
/* 850 */     StringBuilder stringBuilder = new StringBuilder();
/* 851 */     for (byte b = 0; b < paramString.length(); b++) {
/* 852 */       char c = paramString.charAt(b);
/* 853 */       if (c != '&' || b == paramString.length() - 1 || paramString
/* 854 */         .charAt(b + 1) == '&') {
/* 855 */         stringBuilder.append(c);
/*     */       }
/*     */     } 
/* 858 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/tools/policytool/PolicyTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */