/*     */ package sun.security.krb5.internal.ccache;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.LoginOptions;
/*     */ import sun.security.util.SecurityProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileCredentialsCache
/*     */   extends CredentialsCache
/*     */   implements FileCCacheConstants
/*     */ {
/*     */   public int version;
/*     */   public Tag tag;
/*     */   public PrincipalName primaryPrincipal;
/*     */   private Vector<Credentials> credentialsList;
/*     */   private static String dir;
/*  70 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   private List<CredentialsCache.ConfigEntry> configEntries;
/*     */   
/*     */   public static synchronized FileCredentialsCache acquireInstance(PrincipalName paramPrincipalName, String paramString) {
/*     */     try {
/*  75 */       FileCredentialsCache fileCredentialsCache = new FileCredentialsCache();
/*  76 */       if (paramString == null) {
/*  77 */         cacheName = getDefaultCacheName();
/*     */       } else {
/*  79 */         cacheName = checkValidation(paramString);
/*     */       } 
/*  81 */       if (cacheName == null || !(new File(cacheName)).exists())
/*     */       {
/*  83 */         return null;
/*     */       }
/*  85 */       if (paramPrincipalName != null) {
/*  86 */         fileCredentialsCache.primaryPrincipal = paramPrincipalName;
/*     */       }
/*  88 */       fileCredentialsCache.load(cacheName);
/*  89 */       return fileCredentialsCache;
/*  90 */     } catch (IOException iOException) {
/*     */       
/*  92 */       if (DEBUG) {
/*  93 */         iOException.printStackTrace();
/*     */       }
/*  95 */     } catch (KrbException krbException) {
/*     */       
/*  97 */       if (DEBUG) {
/*  98 */         krbException.printStackTrace();
/*     */       }
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public static FileCredentialsCache acquireInstance() {
/* 105 */     return acquireInstance((PrincipalName)null, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   static synchronized FileCredentialsCache New(PrincipalName paramPrincipalName, String paramString) {
/*     */     try {
/* 111 */       FileCredentialsCache fileCredentialsCache = new FileCredentialsCache();
/* 112 */       cacheName = checkValidation(paramString);
/* 113 */       if (cacheName == null)
/*     */       {
/* 115 */         return null;
/*     */       }
/* 117 */       fileCredentialsCache.init(paramPrincipalName, cacheName);
/* 118 */       return fileCredentialsCache;
/*     */     }
/* 120 */     catch (IOException iOException) {
/*     */     
/* 122 */     } catch (KrbException krbException) {}
/*     */     
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   static synchronized FileCredentialsCache New(PrincipalName paramPrincipalName) {
/*     */     try {
/* 129 */       FileCredentialsCache fileCredentialsCache = new FileCredentialsCache();
/* 130 */       cacheName = getDefaultCacheName();
/* 131 */       fileCredentialsCache.init(paramPrincipalName, cacheName);
/* 132 */       return fileCredentialsCache;
/*     */     }
/* 134 */     catch (IOException iOException) {
/* 135 */       if (DEBUG) {
/* 136 */         iOException.printStackTrace();
/*     */       }
/* 138 */     } catch (KrbException krbException) {
/* 139 */       if (DEBUG) {
/* 140 */         krbException.printStackTrace();
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return null;
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
/*     */   boolean exists(String paramString) {
/*     */     File file = new File(paramString);
/*     */     if (file.exists()) {
/*     */       return true;
/*     */     }
/*     */     return false;
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
/*     */   synchronized void init(PrincipalName paramPrincipalName, String paramString) throws IOException, KrbException {
/*     */     this.primaryPrincipal = paramPrincipalName;
/*     */     try(FileOutputStream null = new FileOutputStream(paramString); CCacheOutputStream null = new CCacheOutputStream(fileOutputStream)) {
/*     */       this.version = 1283;
/*     */       cCacheOutputStream.writeHeader(this.primaryPrincipal, this.version);
/*     */     } 
/*     */     load(paramString);
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
/*     */   synchronized void load(String paramString) throws IOException, KrbException {
/*     */     try(FileInputStream null = new FileInputStream(paramString); CCacheInputStream null = new CCacheInputStream(fileInputStream)) {
/*     */       this.version = cCacheInputStream.readVersion();
/*     */       if (this.version == 1284) {
/*     */         this.tag = cCacheInputStream.readTag();
/*     */       } else {
/*     */         this.tag = null;
/*     */         if (this.version == 1281 || this.version == 1282) {
/*     */           cCacheInputStream.setNativeByteOrder();
/*     */         }
/*     */       } 
/*     */       PrincipalName principalName = cCacheInputStream.readPrincipal(this.version);
/*     */       if (this.primaryPrincipal != null) {
/*     */         if (!this.primaryPrincipal.match(principalName)) {
/*     */           throw new IOException("Primary principals don't match.");
/*     */         }
/*     */       } else {
/*     */         this.primaryPrincipal = principalName;
/*     */       } 
/*     */       this.credentialsList = new Vector<>();
/*     */       while (cCacheInputStream.available() > 0) {
/*     */         Object object = cCacheInputStream.readCred(this.version);
/*     */         if (object != null) {
/*     */           if (object instanceof Credentials) {
/*     */             this.credentialsList.addElement((Credentials)object);
/*     */             continue;
/*     */           } 
/*     */           addConfigEntry((CredentialsCache.ConfigEntry)object);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FileCredentialsCache() {
/* 323 */     this.configEntries = new ArrayList<>();
/*     */   } public synchronized void update(Credentials paramCredentials) { if (this.credentialsList != null)
/*     */       if (this.credentialsList.isEmpty()) { this.credentialsList.addElement(paramCredentials); } else { Credentials credentials = null; boolean bool = false; for (byte b = 0; b < this.credentialsList.size(); b++) { credentials = this.credentialsList.elementAt(b); if (match(paramCredentials.sname.getNameStrings(), credentials.sname.getNameStrings()) && paramCredentials.sname.getRealmString().equalsIgnoreCase(credentials.sname.getRealmString())) { bool = true; if (paramCredentials.endtime.getTime() >= credentials.endtime.getTime()) { if (DEBUG)
/*     */                 System.out.println(" >>> FileCredentialsCache Ticket matched, overwrite the old one.");  this.credentialsList.removeElementAt(b); this.credentialsList.addElement(paramCredentials); }  }  }  if (!bool) { if (DEBUG)
/* 327 */             System.out.println(" >>> FileCredentialsCache Ticket not exactly matched, add new one into cache.");  this.credentialsList.addElement(paramCredentials); }  }   } public void addConfigEntry(CredentialsCache.ConfigEntry paramConfigEntry) { this.configEntries.add(paramConfigEntry); } public synchronized PrincipalName getPrimaryPrincipal() { return this.primaryPrincipal; }
/*     */   public synchronized void save() throws IOException, Asn1Exception { try(FileOutputStream null = new FileOutputStream(cacheName); CCacheOutputStream null = new CCacheOutputStream(fileOutputStream)) { cCacheOutputStream.writeHeader(this.primaryPrincipal, this.version); Credentials[] arrayOfCredentials = null; if ((arrayOfCredentials = getCredsList()) != null)
/*     */         for (byte b = 0; b < arrayOfCredentials.length; b++)
/*     */           cCacheOutputStream.addCreds(arrayOfCredentials[b]);   for (CredentialsCache.ConfigEntry configEntry : getConfigEntries())
/*     */         cCacheOutputStream.addConfigEntry(this.primaryPrincipal, configEntry);  }  }
/* 332 */   public List<CredentialsCache.ConfigEntry> getConfigEntries() { return Collections.unmodifiableList(this.configEntries); }
/*     */   boolean match(String[] paramArrayOfString1, String[] paramArrayOfString2) { if (paramArrayOfString1.length != paramArrayOfString2.length)
/*     */       return false; 
/*     */     for (byte b = 0; b < paramArrayOfString1.length; b++) {
/*     */       if (!paramArrayOfString1[b].equalsIgnoreCase(paramArrayOfString2[b]))
/*     */         return false; 
/*     */     } 
/*     */     return true; }
/* 340 */   public Credentials getCreds(PrincipalName paramPrincipalName) { Credentials[] arrayOfCredentials = getCredsList();
/* 341 */     if (arrayOfCredentials == null) {
/* 342 */       return null;
/*     */     }
/* 344 */     for (byte b = 0; b < arrayOfCredentials.length; b++) {
/* 345 */       if (paramPrincipalName.match((arrayOfCredentials[b]).sname)) {
/* 346 */         return arrayOfCredentials[b];
/*     */       }
/*     */     } 
/*     */     
/* 350 */     return null; }
/*     */   public synchronized Credentials[] getCredsList() { if (this.credentialsList == null || this.credentialsList.isEmpty()) return null;  Credentials[] arrayOfCredentials = new Credentials[this.credentialsList.size()]; for (byte b = 0; b < this.credentialsList.size(); b++) arrayOfCredentials[b] = this.credentialsList.elementAt(b);  return arrayOfCredentials; }
/*     */   public Credentials getCreds(LoginOptions paramLoginOptions, PrincipalName paramPrincipalName) { if (paramLoginOptions == null)
/*     */       return getCreds(paramPrincipalName);  Credentials[] arrayOfCredentials = getCredsList(); if (arrayOfCredentials == null)
/*     */       return null;  for (byte b = 0; b < arrayOfCredentials.length; b++) { if (paramPrincipalName.match((arrayOfCredentials[b]).sname) && (arrayOfCredentials[b]).flags.match(paramLoginOptions))
/* 355 */         return arrayOfCredentials[b];  }  return null; } public Credentials getInitialCreds() { boolean bool; Credentials credentials = getDefaultCreds();
/* 356 */     if (credentials == null) {
/* 357 */       return null;
/*     */     }
/* 359 */     Credentials credentials1 = credentials.setKrbCreds();
/*     */     
/* 361 */     CredentialsCache.ConfigEntry configEntry = getConfigEntry("proxy_impersonator");
/* 362 */     if (configEntry == null) {
/* 363 */       if (DEBUG) {
/* 364 */         System.out.println("get normal credential");
/*     */       }
/* 366 */       return credentials1;
/*     */     } 
/*     */ 
/*     */     
/* 370 */     String str = SecurityProperties.privilegedGetOverridable("jdk.security.krb5.default.initiate.credential");
/*     */     
/* 372 */     if (str == null) {
/* 373 */       str = "always-impersonate";
/*     */     }
/* 375 */     switch (str) {
/*     */       case "no-impersonate":
/* 377 */         if (DEBUG) {
/* 378 */           System.out.println("get normal credential");
/*     */         }
/* 380 */         return credentials1;
/*     */       case "try-impersonate":
/* 382 */         bool = false;
/*     */         break;
/*     */       case "always-impersonate":
/* 385 */         bool = true;
/*     */         break;
/*     */       default:
/* 388 */         throw new RuntimeException("Invalid jdk.security.krb5.default.initiate.credential");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 394 */       PrincipalName principalName1 = new PrincipalName(new String(configEntry.getData(), StandardCharsets.UTF_8));
/* 395 */       if (!credentials1.getClient().equals(principalName1)) {
/* 396 */         if (DEBUG) {
/* 397 */           System.out.println("proxy_impersonator does not match service name");
/*     */         }
/* 399 */         return bool ? null : credentials1;
/*     */       } 
/* 401 */       PrincipalName principalName2 = getPrimaryPrincipal();
/* 402 */       Credentials credentials2 = null;
/* 403 */       for (Credentials credentials3 : getCredsList()) {
/* 404 */         if (credentials3.getClientPrincipal().equals(principalName2) && credentials3
/* 405 */           .getServicePrincipal().equals(principalName1)) {
/* 406 */           credentials2 = credentials3;
/*     */           break;
/*     */         } 
/*     */       } 
/* 410 */       if (credentials2 == null) {
/* 411 */         if (DEBUG) {
/* 412 */           System.out.println("Cannot find evidence ticket in ccache");
/*     */         }
/* 414 */         return bool ? null : credentials1;
/*     */       } 
/* 416 */       if (DEBUG) {
/* 417 */         System.out.println("Get proxied credential");
/*     */       }
/* 419 */       return credentials1.setProxy(credentials2.setKrbCreds());
/* 420 */     } catch (KrbException krbException) {
/* 421 */       if (DEBUG) {
/* 422 */         System.out.println("Impersonation with ccache failed");
/*     */       }
/* 424 */       return bool ? null : credentials1;
/*     */     }  }
/*     */ 
/*     */   
/*     */   public Credentials getDefaultCreds() {
/* 429 */     Credentials[] arrayOfCredentials = getCredsList();
/* 430 */     if (arrayOfCredentials == null) {
/* 431 */       return null;
/*     */     }
/* 433 */     for (int i = arrayOfCredentials.length - 1; i >= 0; i--) {
/* 434 */       if ((arrayOfCredentials[i]).sname.toString().startsWith("krbtgt")) {
/* 435 */         String[] arrayOfString = (arrayOfCredentials[i]).sname.getNameStrings();
/*     */         
/* 437 */         if (arrayOfString[1].equals((arrayOfCredentials[i]).sname.getRealm().toString())) {
/* 438 */           return arrayOfCredentials[i];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 443 */     return null;
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
/*     */   public static String getDefaultCacheName() {
/* 458 */     String str1 = "krb5cc";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 463 */     String str2 = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/* 467 */             String str = System.getenv("KRB5CCNAME");
/* 468 */             if (str != null && str
/* 469 */               .length() >= 5 && str
/* 470 */               .regionMatches(true, 0, "FILE:", 0, 5)) {
/* 471 */               str = str.substring(5);
/*     */             }
/* 473 */             return str;
/*     */           }
/*     */         });
/* 476 */     if (str2 != null) {
/* 477 */       if (DEBUG) {
/* 478 */         System.out.println(">>>KinitOptions cache name is " + str2);
/*     */       }
/* 480 */       return str2;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 485 */     String str3 = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 499 */     if (str3 != null) {
/* 500 */       Object object1 = null;
/* 501 */       Object object2 = null;
/* 502 */       long l = 0L;
/*     */       
/* 504 */       if (!str3.startsWith("Windows")) {
/*     */         
/*     */         try {
/* 507 */           Class<?> clazz = Class.forName("com.sun.security.auth.module.UnixSystem");
/* 508 */           Constructor<?> constructor = clazz.getConstructor(new Class[0]);
/* 509 */           Object object = constructor.newInstance(new Object[0]);
/* 510 */           Method method = clazz.getMethod("getUid", new Class[0]);
/* 511 */           l = ((Long)method.invoke(object, new Object[0])).longValue();
/* 512 */           str2 = File.separator + "tmp" + File.separator + str1 + "_" + l;
/*     */           
/* 514 */           if (DEBUG) {
/* 515 */             System.out.println(">>>KinitOptions cache name is " + str2);
/*     */           }
/*     */           
/* 518 */           return str2;
/* 519 */         } catch (Exception exception) {
/* 520 */           if (DEBUG) {
/* 521 */             System.out.println("Exception in obtaining uid for Unix platforms Using user's home directory");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 526 */             exception.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 536 */     String str4 = AccessController.<String>doPrivileged(new GetPropertyAction("user.name"));
/*     */ 
/*     */ 
/*     */     
/* 540 */     String str5 = AccessController.<String>doPrivileged(new GetPropertyAction("user.home"));
/*     */ 
/*     */     
/* 543 */     if (str5 == null)
/*     */     {
/* 545 */       str5 = AccessController.<String>doPrivileged(new GetPropertyAction("user.dir"));
/*     */     }
/*     */ 
/*     */     
/* 549 */     if (str4 != null) {
/* 550 */       str2 = str5 + File.separator + str1 + "_" + str4;
/*     */     } else {
/*     */       
/* 553 */       str2 = str5 + File.separator + str1;
/*     */     } 
/*     */     
/* 556 */     if (DEBUG) {
/* 557 */       System.out.println(">>>KinitOptions cache name is " + str2);
/*     */     }
/*     */     
/* 560 */     return str2;
/*     */   }
/*     */   
/*     */   public static String checkValidation(String paramString) {
/* 564 */     String str = null;
/* 565 */     if (paramString == null) {
/* 566 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 570 */       str = (new File(paramString)).getCanonicalPath();
/* 571 */       File file = new File(str);
/* 572 */       if (!file.exists()) {
/*     */         
/* 574 */         File file1 = new File(file.getParent());
/*     */         
/* 576 */         if (!file1.isDirectory())
/* 577 */           str = null; 
/* 578 */         file1 = null;
/*     */       } 
/* 580 */       file = null;
/*     */     }
/* 582 */     catch (IOException iOException) {
/* 583 */       str = null;
/*     */     } 
/* 585 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String exec(String paramString) {
/* 590 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString);
/* 591 */     Vector<String> vector = new Vector();
/* 592 */     while (stringTokenizer.hasMoreTokens()) {
/* 593 */       vector.addElement(stringTokenizer.nextToken());
/*     */     }
/* 595 */     final String[] command = new String[vector.size()];
/* 596 */     vector.copyInto((Object[])arrayOfString);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 601 */       Process process = AccessController.<Process>doPrivileged(new PrivilegedAction<Process>() {
/*     */             public Process run() {
/*     */               try {
/* 604 */                 return Runtime.getRuntime().exec(command);
/* 605 */               } catch (IOException iOException) {
/* 606 */                 if (FileCredentialsCache.DEBUG) {
/* 607 */                   iOException.printStackTrace();
/*     */                 }
/* 609 */                 return null;
/*     */               } 
/*     */             }
/*     */           });
/* 613 */       if (process == null)
/*     */       {
/* 615 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 620 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "8859_1"));
/* 621 */       String str = null;
/* 622 */       if (arrayOfString.length == 1 && arrayOfString[0]
/* 623 */         .equals("/usr/bin/env")) {
/* 624 */         while ((str = bufferedReader.readLine()) != null) {
/* 625 */           if (str.length() >= 11 && 
/* 626 */             str.substring(0, 11)
/* 627 */             .equalsIgnoreCase("KRB5CCNAME=")) {
/* 628 */             str = str.substring(11);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 633 */         str = bufferedReader.readLine();
/* 634 */       }  bufferedReader.close();
/* 635 */       return str;
/* 636 */     } catch (Exception exception) {
/* 637 */       if (DEBUG) {
/* 638 */         exception.printStackTrace();
/*     */       }
/*     */       
/* 641 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ccache/FileCredentialsCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */