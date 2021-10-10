/*     */ package sun.security.krb5.internal.ktab;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyTab
/*     */   implements KeyTabConstants
/*     */ {
/*  66 */   private static final boolean DEBUG = Krb5.DEBUG;
/*  67 */   private static String defaultTabName = null;
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static Map<String, KeyTab> map = new HashMap<>();
/*     */   
/*     */   private boolean isMissing = false;
/*     */   
/*     */   private boolean isValid = true;
/*     */   
/*     */   private final String tabName;
/*     */   
/*     */   private long lastModified;
/*     */   
/*  81 */   private int kt_vno = 1282;
/*     */   
/*  83 */   private Vector<KeyTabEntry> entries = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private KeyTab(String paramString) {
/*  93 */     this.tabName = paramString;
/*     */     try {
/*  95 */       this.lastModified = (new File(this.tabName)).lastModified();
/*  96 */       try (KeyTabInputStream null = new KeyTabInputStream(new FileInputStream(paramString))) {
/*     */         
/*  98 */         load(keyTabInputStream);
/*     */       } 
/* 100 */     } catch (FileNotFoundException fileNotFoundException) {
/* 101 */       this.entries.clear();
/* 102 */       this.isMissing = true;
/* 103 */     } catch (Exception exception) {
/* 104 */       this.entries.clear();
/* 105 */       this.isValid = false;
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
/*     */   private static synchronized KeyTab getInstance0(String paramString) {
/* 120 */     long l = (new File(paramString)).lastModified();
/* 121 */     KeyTab keyTab1 = map.get(paramString);
/* 122 */     if (keyTab1 != null && keyTab1.isValid() && keyTab1.lastModified == l) {
/* 123 */       return keyTab1;
/*     */     }
/* 125 */     KeyTab keyTab2 = new KeyTab(paramString);
/* 126 */     if (keyTab2.isValid()) {
/* 127 */       map.put(paramString, keyTab2);
/* 128 */       return keyTab2;
/* 129 */     }  if (keyTab1 != null) {
/* 130 */       return keyTab1;
/*     */     }
/* 132 */     return keyTab2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTab getInstance(String paramString) {
/* 142 */     if (paramString == null) {
/* 143 */       return getInstance();
/*     */     }
/* 145 */     return getInstance0(normalize(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTab getInstance(File paramFile) {
/* 155 */     if (paramFile == null) {
/* 156 */       return getInstance();
/*     */     }
/* 158 */     return getInstance0(paramFile.getPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTab getInstance() {
/* 167 */     return getInstance(getDefaultTabName());
/*     */   }
/*     */   
/*     */   public boolean isMissing() {
/* 171 */     return this.isMissing;
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 175 */     return this.isValid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getDefaultTabName() {
/* 185 */     if (defaultTabName != null) {
/* 186 */       return defaultTabName;
/*     */     }
/* 188 */     String str = null;
/*     */     
/*     */     try {
/* 191 */       String str1 = Config.getInstance().get(new String[] { "libdefaults", "default_keytab_name" });
/* 192 */       if (str1 != null) {
/* 193 */         StringTokenizer stringTokenizer = new StringTokenizer(str1, " ");
/*     */         do {
/* 195 */           str = normalize(stringTokenizer.nextToken());
/* 196 */         } while (stringTokenizer.hasMoreTokens() && !(new File(str)).exists());
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 201 */     catch (KrbException krbException) {
/* 202 */       str = null;
/*     */     } 
/*     */     
/* 205 */     if (str == null) {
/*     */       
/* 207 */       String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("user.home"));
/*     */ 
/*     */       
/* 210 */       if (str1 == null)
/*     */       {
/* 212 */         str1 = AccessController.<String>doPrivileged(new GetPropertyAction("user.dir"));
/*     */       }
/*     */ 
/*     */       
/* 216 */       str = str1 + File.separator + "krb5.keytab";
/*     */     } 
/* 218 */     defaultTabName = str;
/* 219 */     return str;
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
/*     */   public static String normalize(String paramString) {
/*     */     String str;
/* 232 */     if (paramString.length() >= 5 && paramString
/* 233 */       .substring(0, 5).equalsIgnoreCase("FILE:")) {
/* 234 */       str = paramString.substring(5);
/* 235 */     } else if (paramString.length() >= 9 && paramString
/* 236 */       .substring(0, 9).equalsIgnoreCase("ANY:FILE:")) {
/*     */       
/* 238 */       str = paramString.substring(9);
/* 239 */     } else if (paramString.length() >= 7 && paramString
/* 240 */       .substring(0, 7).equalsIgnoreCase("SRVTAB:")) {
/*     */       
/* 242 */       str = paramString.substring(7);
/*     */     } else {
/* 244 */       str = paramString;
/* 245 */     }  return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void load(KeyTabInputStream paramKeyTabInputStream) throws IOException, RealmException {
/* 251 */     this.entries.clear();
/* 252 */     this.kt_vno = paramKeyTabInputStream.readVersion();
/* 253 */     if (this.kt_vno == 1281) {
/* 254 */       paramKeyTabInputStream.setNativeByteOrder();
/*     */     }
/* 256 */     int i = 0;
/*     */     
/* 258 */     while (paramKeyTabInputStream.available() > 0) {
/* 259 */       i = paramKeyTabInputStream.readEntryLength();
/* 260 */       KeyTabEntry keyTabEntry = paramKeyTabInputStream.readEntry(i, this.kt_vno);
/* 261 */       if (DEBUG) {
/* 262 */         System.out.println(">>> KeyTab: load() entry length: " + i + "; type: " + ((keyTabEntry != null) ? keyTabEntry.keyType : 0));
/*     */       }
/*     */ 
/*     */       
/* 266 */       if (keyTabEntry != null) {
/* 267 */         this.entries.addElement(keyTabEntry);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrincipalName getOneName() {
/* 276 */     int i = this.entries.size();
/* 277 */     return (i > 0) ? ((KeyTabEntry)this.entries.elementAt(i - 1)).service : null;
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
/*     */   public EncryptionKey[] readServiceKeys(PrincipalName paramPrincipalName) {
/* 289 */     int i = this.entries.size();
/* 290 */     ArrayList<EncryptionKey> arrayList = new ArrayList(i);
/* 291 */     if (DEBUG) {
/* 292 */       System.out.println("Looking for keys for: " + paramPrincipalName);
/*     */     }
/* 294 */     for (int j = i - 1; j >= 0; j--) {
/* 295 */       KeyTabEntry keyTabEntry = this.entries.elementAt(j);
/* 296 */       if (keyTabEntry.service.match(paramPrincipalName)) {
/* 297 */         if (EType.isSupported(keyTabEntry.keyType)) {
/* 298 */           EncryptionKey encryptionKey = new EncryptionKey(keyTabEntry.keyblock, keyTabEntry.keyType, new Integer(keyTabEntry.keyVersion));
/*     */ 
/*     */           
/* 301 */           arrayList.add(encryptionKey);
/* 302 */           if (DEBUG) {
/* 303 */             System.out.println("Added key: " + keyTabEntry.keyType + "version: " + keyTabEntry.keyVersion);
/*     */           }
/*     */         }
/* 306 */         else if (DEBUG) {
/* 307 */           System.out.println("Found unsupported keytype (" + keyTabEntry.keyType + ") for " + paramPrincipalName);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 312 */     i = arrayList.size();
/* 313 */     EncryptionKey[] arrayOfEncryptionKey = arrayList.<EncryptionKey>toArray(new EncryptionKey[i]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     Arrays.sort(arrayOfEncryptionKey, new Comparator<EncryptionKey>()
/*     */         {
/*     */           public int compare(EncryptionKey param1EncryptionKey1, EncryptionKey param1EncryptionKey2) {
/* 321 */             return param1EncryptionKey2.getKeyVersionNumber().intValue() - param1EncryptionKey1
/* 322 */               .getKeyVersionNumber().intValue();
/*     */           }
/*     */         });
/*     */     
/* 326 */     return arrayOfEncryptionKey;
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
/*     */   public boolean findServiceEntry(PrincipalName paramPrincipalName) {
/* 340 */     for (byte b = 0; b < this.entries.size(); b++) {
/* 341 */       KeyTabEntry keyTabEntry = this.entries.elementAt(b);
/* 342 */       if (keyTabEntry.service.match(paramPrincipalName)) {
/* 343 */         if (EType.isSupported(keyTabEntry.keyType))
/* 344 */           return true; 
/* 345 */         if (DEBUG) {
/* 346 */           System.out.println("Found unsupported keytype (" + keyTabEntry.keyType + ") for " + paramPrincipalName);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 351 */     return false;
/*     */   }
/*     */   
/*     */   public String tabName() {
/* 355 */     return this.tabName;
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
/*     */   public void addEntry(PrincipalName paramPrincipalName, char[] paramArrayOfchar, int paramInt, boolean paramBoolean) throws KrbException {
/* 371 */     addEntry(paramPrincipalName, paramPrincipalName.getSalt(), paramArrayOfchar, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(PrincipalName paramPrincipalName, String paramString, char[] paramArrayOfchar, int paramInt, boolean paramBoolean) throws KrbException {
/* 378 */     EncryptionKey[] arrayOfEncryptionKey = EncryptionKey.acquireSecretKeys(paramArrayOfchar, paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     int i = 0; int j;
/* 385 */     for (j = this.entries.size() - 1; j >= 0; j--) {
/* 386 */       KeyTabEntry keyTabEntry = this.entries.get(j);
/* 387 */       if (keyTabEntry.service.match(paramPrincipalName)) {
/* 388 */         if (keyTabEntry.keyVersion > i) {
/* 389 */           i = keyTabEntry.keyVersion;
/*     */         }
/* 391 */         if (!paramBoolean || keyTabEntry.keyVersion == paramInt) {
/* 392 */           this.entries.removeElementAt(j);
/*     */         }
/*     */       } 
/*     */     } 
/* 396 */     if (paramInt == -1) {
/* 397 */       paramInt = i + 1;
/*     */     }
/*     */     
/* 400 */     for (j = 0; arrayOfEncryptionKey != null && j < arrayOfEncryptionKey.length; j++) {
/* 401 */       int k = arrayOfEncryptionKey[j].getEType();
/* 402 */       byte[] arrayOfByte = arrayOfEncryptionKey[j].getBytes();
/*     */ 
/*     */ 
/*     */       
/* 406 */       KeyTabEntry keyTabEntry = new KeyTabEntry(paramPrincipalName, paramPrincipalName.getRealm(), new KerberosTime(System.currentTimeMillis()), paramInt, k, arrayOfByte);
/*     */       
/* 408 */       this.entries.addElement(keyTabEntry);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyTabEntry[] getEntries() {
/* 417 */     KeyTabEntry[] arrayOfKeyTabEntry = new KeyTabEntry[this.entries.size()];
/* 418 */     for (byte b = 0; b < arrayOfKeyTabEntry.length; b++) {
/* 419 */       arrayOfKeyTabEntry[b] = this.entries.elementAt(b);
/*     */     }
/* 421 */     return arrayOfKeyTabEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized KeyTab create() throws IOException, RealmException {
/* 429 */     String str = getDefaultTabName();
/* 430 */     return create(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized KeyTab create(String paramString) throws IOException, RealmException {
/* 439 */     try (KeyTabOutputStream null = new KeyTabOutputStream(new FileOutputStream(paramString))) {
/*     */       
/* 441 */       keyTabOutputStream.writeVersion(1282);
/*     */     } 
/* 443 */     return new KeyTab(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void save() throws IOException {
/* 450 */     try (KeyTabOutputStream null = new KeyTabOutputStream(new FileOutputStream(this.tabName))) {
/*     */       
/* 452 */       keyTabOutputStream.writeVersion(this.kt_vno);
/* 453 */       for (byte b = 0; b < this.entries.size(); b++) {
/* 454 */         keyTabOutputStream.writeEntry(this.entries.elementAt(b));
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
/*     */   public int deleteEntries(PrincipalName paramPrincipalName, int paramInt1, int paramInt2) {
/* 467 */     byte b = 0;
/*     */ 
/*     */     
/* 470 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     int i;
/* 472 */     for (i = this.entries.size() - 1; i >= 0; i--) {
/* 473 */       KeyTabEntry keyTabEntry = this.entries.get(i);
/* 474 */       if (paramPrincipalName.match(keyTabEntry.getService()) && (
/* 475 */         paramInt1 == -1 || keyTabEntry.keyType == paramInt1)) {
/* 476 */         if (paramInt2 == -2) {
/*     */ 
/*     */           
/* 479 */           if (hashMap.containsKey(Integer.valueOf(keyTabEntry.keyType))) {
/* 480 */             int j = ((Integer)hashMap.get(Integer.valueOf(keyTabEntry.keyType))).intValue();
/* 481 */             if (keyTabEntry.keyVersion > j) {
/* 482 */               hashMap.put(Integer.valueOf(keyTabEntry.keyType), Integer.valueOf(keyTabEntry.keyVersion));
/*     */             }
/*     */           } else {
/* 485 */             hashMap.put(Integer.valueOf(keyTabEntry.keyType), Integer.valueOf(keyTabEntry.keyVersion));
/*     */           } 
/* 487 */         } else if (paramInt2 == -1 || keyTabEntry.keyVersion == paramInt2) {
/* 488 */           this.entries.removeElementAt(i);
/* 489 */           b++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 496 */     if (paramInt2 == -2) {
/* 497 */       for (i = this.entries.size() - 1; i >= 0; i--) {
/* 498 */         KeyTabEntry keyTabEntry = this.entries.get(i);
/* 499 */         if (paramPrincipalName.match(keyTabEntry.getService()) && (
/* 500 */           paramInt1 == -1 || keyTabEntry.keyType == paramInt1)) {
/* 501 */           int j = ((Integer)hashMap.get(Integer.valueOf(keyTabEntry.keyType))).intValue();
/* 502 */           if (keyTabEntry.keyVersion != j) {
/* 503 */             this.entries.removeElementAt(i);
/* 504 */             b++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 510 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void createVersion(File paramFile) throws IOException {
/* 519 */     try (KeyTabOutputStream null = new KeyTabOutputStream(new FileOutputStream(paramFile))) {
/*     */       
/* 521 */       keyTabOutputStream.write16(1282);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ktab/KeyTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */