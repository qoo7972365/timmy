/*      */ package sun.security.krb5;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.InetAddress;
/*      */ import java.net.UnknownHostException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import sun.net.dns.ResolverConfiguration;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.security.krb5.internal.Krb5;
/*      */ import sun.security.krb5.internal.crypto.EType;
/*      */ import sun.security.util.SecurityProperties;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Config
/*      */ {
/*      */   public static final boolean DISABLE_REFERRALS;
/*      */   public static final int MAX_REFERRALS;
/*      */   
/*      */   static {
/*   75 */     String str1 = SecurityProperties.privilegedGetOverridable("sun.security.krb5.disableReferrals");
/*      */     
/*   77 */     if (str1 != null) {
/*   78 */       DISABLE_REFERRALS = "true".equalsIgnoreCase(str1);
/*      */     } else {
/*   80 */       DISABLE_REFERRALS = false;
/*      */     } 
/*      */     
/*   83 */     int i = 5;
/*      */     
/*   85 */     String str2 = SecurityProperties.privilegedGetOverridable("sun.security.krb5.maxReferrals");
/*      */     
/*      */     try {
/*   88 */       i = Integer.parseInt(str2);
/*   89 */     } catch (NumberFormatException numberFormatException) {}
/*      */     
/*   91 */     MAX_REFERRALS = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   97 */   private static Config singleton = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  102 */   private Hashtable<String, Object> stanzaTable = new Hashtable<>();
/*      */   
/*  104 */   private static boolean DEBUG = Krb5.DEBUG;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BASE16_0 = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BASE16_1 = 16;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BASE16_2 = 256;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BASE16_3 = 4096;
/*      */ 
/*      */   
/*      */   private final String defaultRealm;
/*      */ 
/*      */   
/*      */   private final String defaultKDC;
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized Config getInstance() throws KrbException {
/*  131 */     if (singleton == null) {
/*  132 */       singleton = new Config();
/*      */     }
/*  134 */     return singleton;
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
/*      */   public static synchronized void refresh() throws KrbException {
/*  150 */     singleton = new Config();
/*  151 */     KdcComm.initStatic();
/*  152 */     EType.initStatic();
/*  153 */     Checksum.initStatic();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isMacosLionOrBetter() {
/*  159 */     String str1 = getProperty("os.name");
/*  160 */     if (!str1.contains("OS X")) {
/*  161 */       return false;
/*      */     }
/*      */     
/*  164 */     String str2 = getProperty("os.version");
/*  165 */     String[] arrayOfString = str2.split("\\.");
/*      */ 
/*      */     
/*  168 */     if (!arrayOfString[0].equals("10")) return false; 
/*  169 */     if (arrayOfString.length < 2) return false;
/*      */ 
/*      */     
/*      */     try {
/*  173 */       int i = Integer.parseInt(arrayOfString[1]);
/*  174 */       if (i >= 7) return true; 
/*  175 */     } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */     
/*  179 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Config() throws KrbException {
/*  189 */     String str = getProperty("java.security.krb5.kdc");
/*  190 */     if (str != null) {
/*      */       
/*  192 */       this.defaultKDC = str.replace(':', ' ');
/*      */     } else {
/*  194 */       this.defaultKDC = null;
/*      */     } 
/*  196 */     this.defaultRealm = getProperty("java.security.krb5.realm");
/*  197 */     if ((this.defaultKDC == null && this.defaultRealm != null) || (this.defaultRealm == null && this.defaultKDC != null))
/*      */     {
/*  199 */       throw new KrbException("System property java.security.krb5.kdc and java.security.krb5.realm both must be set or neither must be set.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  208 */       String str1 = getJavaFileName();
/*  209 */       if (str1 != null) {
/*  210 */         List<String> list = loadConfigFile(str1);
/*  211 */         this.stanzaTable = parseStanzaTable(list);
/*  212 */         if (DEBUG) {
/*  213 */           System.out.println("Loaded from Java config");
/*      */         }
/*      */       } else {
/*  216 */         boolean bool = false;
/*  217 */         if (isMacosLionOrBetter()) {
/*      */           try {
/*  219 */             this.stanzaTable = SCDynamicStoreConfig.getConfig();
/*  220 */             if (DEBUG) {
/*  221 */               System.out.println("Loaded from SCDynamicStoreConfig");
/*      */             }
/*  223 */             bool = true;
/*  224 */           } catch (IOException iOException) {}
/*      */         }
/*      */ 
/*      */         
/*  228 */         if (!bool) {
/*  229 */           str1 = getNativeFileName();
/*  230 */           List<String> list = loadConfigFile(str1);
/*  231 */           this.stanzaTable = parseStanzaTable(list);
/*  232 */           if (DEBUG) {
/*  233 */             System.out.println("Loaded from native config");
/*      */           }
/*      */         } 
/*      */       } 
/*  237 */     } catch (IOException iOException) {}
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
/*      */   public String get(String... paramVarArgs) {
/*  263 */     Vector<String> vector = getString0(paramVarArgs);
/*  264 */     if (vector == null) return null; 
/*  265 */     return vector.lastElement();
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
/*      */   private Boolean getBooleanObject(String... paramVarArgs) {
/*  279 */     String str = get(paramVarArgs);
/*  280 */     if (str == null) {
/*  281 */       return null;
/*      */     }
/*  283 */     switch (str.toLowerCase(Locale.US)) { case "yes":
/*      */       case "true":
/*  285 */         return Boolean.TRUE;
/*      */       case "no": case "false":
/*  287 */         return Boolean.FALSE; }
/*      */     
/*  289 */     return null;
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
/*      */   public String getAll(String... paramVarArgs) {
/*  303 */     Vector<String> vector = getString0(paramVarArgs);
/*  304 */     if (vector == null) return null; 
/*  305 */     StringBuilder stringBuilder = new StringBuilder();
/*  306 */     boolean bool = true;
/*  307 */     for (String str : vector) {
/*  308 */       str = str.replaceAll("[\\s,]+", " ");
/*  309 */       if (bool) {
/*  310 */         stringBuilder.append(str);
/*  311 */         bool = false; continue;
/*      */       } 
/*  313 */       stringBuilder.append(' ').append(str);
/*      */     } 
/*      */     
/*  316 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exists(String... paramVarArgs) {
/*  325 */     return (get0(paramVarArgs) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector<String> getString0(String... paramVarArgs) {
/*      */     try {
/*  332 */       return (Vector<String>)get0(paramVarArgs);
/*  333 */     } catch (ClassCastException classCastException) {
/*  334 */       throw new IllegalArgumentException(classCastException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object get0(String... paramVarArgs) {
/*  343 */     Hashtable<String, Object> hashtable = this.stanzaTable;
/*      */     try {
/*  345 */       for (String str : paramVarArgs) {
/*  346 */         hashtable = (Hashtable<String, Object>)hashtable.get(str);
/*  347 */         if (hashtable == null) return null; 
/*      */       } 
/*  349 */       return hashtable;
/*  350 */     } catch (ClassCastException classCastException) {
/*  351 */       throw new IllegalArgumentException(classCastException);
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
/*      */   public static int duration(String paramString) throws KrbException {
/*  368 */     if (paramString.isEmpty()) {
/*  369 */       throw new KrbException("Duration cannot be empty");
/*      */     }
/*      */ 
/*      */     
/*  373 */     if (paramString.matches("\\d+")) {
/*  374 */       return Integer.parseInt(paramString);
/*      */     }
/*      */ 
/*      */     
/*  378 */     Matcher matcher = Pattern.compile("(\\d+):(\\d+)(:(\\d+))?").matcher(paramString);
/*  379 */     if (matcher.matches()) {
/*  380 */       int i = Integer.parseInt(matcher.group(1));
/*  381 */       int j = Integer.parseInt(matcher.group(2));
/*  382 */       if (j >= 60) {
/*  383 */         throw new KrbException("Illegal duration format " + paramString);
/*      */       }
/*  385 */       int k = i * 3600 + j * 60;
/*  386 */       if (matcher.group(4) != null) {
/*  387 */         int m = Integer.parseInt(matcher.group(4));
/*  388 */         if (m >= 60) {
/*  389 */           throw new KrbException("Illegal duration format " + paramString);
/*      */         }
/*  391 */         k += m;
/*      */       } 
/*  393 */       return k;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  400 */     matcher = Pattern.compile("((\\d+)d)?\\s*((\\d+)h)?\\s*((\\d+)m)?\\s*((\\d+)s)?", 2).matcher(paramString);
/*  401 */     if (matcher.matches()) {
/*  402 */       int i = 0;
/*  403 */       if (matcher.group(2) != null) {
/*  404 */         i += 86400 * Integer.parseInt(matcher.group(2));
/*      */       }
/*  406 */       if (matcher.group(4) != null) {
/*  407 */         i += 3600 * Integer.parseInt(matcher.group(4));
/*      */       }
/*  409 */       if (matcher.group(6) != null) {
/*  410 */         i += 60 * Integer.parseInt(matcher.group(6));
/*      */       }
/*  412 */       if (matcher.group(8) != null) {
/*  413 */         i += Integer.parseInt(matcher.group(8));
/*      */       }
/*  415 */       return i;
/*      */     } 
/*      */     
/*  418 */     throw new KrbException("Illegal duration format " + paramString);
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
/*      */   public int getIntValue(String... paramVarArgs) {
/*  430 */     String str = get(paramVarArgs);
/*  431 */     int i = Integer.MIN_VALUE;
/*  432 */     if (str != null) {
/*      */       try {
/*  434 */         i = parseIntValue(str);
/*  435 */       } catch (NumberFormatException numberFormatException) {
/*  436 */         if (DEBUG) {
/*  437 */           System.out.println("Exception in getting value of " + 
/*  438 */               Arrays.toString((Object[])paramVarArgs) + " " + numberFormatException
/*  439 */               .getMessage());
/*  440 */           System.out.println("Setting " + Arrays.toString((Object[])paramVarArgs) + " to minimum value");
/*      */         } 
/*      */         
/*  443 */         i = Integer.MIN_VALUE;
/*      */       } 
/*      */     }
/*  446 */     return i;
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
/*      */   public boolean getBooleanValue(String... paramVarArgs) {
/*  458 */     String str = get(paramVarArgs);
/*  459 */     if (str != null && str.equalsIgnoreCase("true")) {
/*  460 */       return true;
/*      */     }
/*  462 */     return false;
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
/*      */   private int parseIntValue(String paramString) throws NumberFormatException {
/*  478 */     int i = 0;
/*  479 */     if (paramString.startsWith("+")) {
/*  480 */       String str = paramString.substring(1);
/*  481 */       return Integer.parseInt(str);
/*  482 */     }  if (paramString.startsWith("0x")) {
/*  483 */       String str = paramString.substring(2);
/*  484 */       char[] arrayOfChar = str.toCharArray();
/*  485 */       if (arrayOfChar.length > 8) {
/*  486 */         throw new NumberFormatException();
/*      */       }
/*  488 */       for (byte b = 0; b < arrayOfChar.length; b++) {
/*  489 */         int j = arrayOfChar.length - b - 1;
/*  490 */         switch (arrayOfChar[b]) {
/*      */           case '0':
/*  492 */             i += false;
/*      */             break;
/*      */           case '1':
/*  495 */             i += 1 * getBase(j);
/*      */             break;
/*      */           case '2':
/*  498 */             i += 2 * getBase(j);
/*      */             break;
/*      */           case '3':
/*  501 */             i += 3 * getBase(j);
/*      */             break;
/*      */           case '4':
/*  504 */             i += 4 * getBase(j);
/*      */             break;
/*      */           case '5':
/*  507 */             i += 5 * getBase(j);
/*      */             break;
/*      */           case '6':
/*  510 */             i += 6 * getBase(j);
/*      */             break;
/*      */           case '7':
/*  513 */             i += 7 * getBase(j);
/*      */             break;
/*      */           case '8':
/*  516 */             i += 8 * getBase(j);
/*      */             break;
/*      */           case '9':
/*  519 */             i += 9 * getBase(j);
/*      */             break;
/*      */           case 'A':
/*      */           case 'a':
/*  523 */             i += 10 * getBase(j);
/*      */             break;
/*      */           case 'B':
/*      */           case 'b':
/*  527 */             i += 11 * getBase(j);
/*      */             break;
/*      */           case 'C':
/*      */           case 'c':
/*  531 */             i += 12 * getBase(j);
/*      */             break;
/*      */           case 'D':
/*      */           case 'd':
/*  535 */             i += 13 * getBase(j);
/*      */             break;
/*      */           case 'E':
/*      */           case 'e':
/*  539 */             i += 14 * getBase(j);
/*      */             break;
/*      */           case 'F':
/*      */           case 'f':
/*  543 */             i += 15 * getBase(j);
/*      */             break;
/*      */           default:
/*  546 */             throw new NumberFormatException("Invalid numerical format");
/*      */         } 
/*      */       
/*      */       } 
/*  550 */       if (i < 0) {
/*  551 */         throw new NumberFormatException("Data overflow.");
/*      */       }
/*      */     } else {
/*  554 */       i = Integer.parseInt(paramString);
/*      */     } 
/*  556 */     return i;
/*      */   }
/*      */   
/*      */   private int getBase(int paramInt) {
/*  560 */     int i = 16;
/*  561 */     switch (paramInt)
/*      */     { case 0:
/*  563 */         i = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  579 */         return i;case 1: i = 16; return i;case 2: i = 256; return i;case 3: i = 4096; return i; }  for (byte b = 1; b < paramInt; b++) i *= 16;  return i;
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
/*      */   private List<String> loadConfigFile(final String fileName) throws IOException, KrbException {
/*      */     try {
/*  621 */       ArrayList<String> arrayList = new ArrayList();
/*  622 */       try (BufferedReader null = new BufferedReader(new InputStreamReader(
/*  623 */               AccessController.<InputStream>doPrivileged((PrivilegedExceptionAction)new PrivilegedExceptionAction<FileInputStream>()
/*      */                 {
/*      */                   public FileInputStream run() throws IOException {
/*  626 */                     return new FileInputStream(fileName);
/*      */                   }
/*      */                 })))) {
/*      */         
/*  630 */         String str2 = null; String str1;
/*  631 */         while ((str1 = bufferedReader.readLine()) != null) {
/*  632 */           str1 = str1.trim();
/*  633 */           if (str1.isEmpty() || str1.startsWith("#") || str1.startsWith(";")) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  654 */           if (str1.startsWith("[")) {
/*  655 */             if (!str1.endsWith("]")) {
/*  656 */               throw new KrbException("Illegal config content:" + str1);
/*      */             }
/*      */             
/*  659 */             if (str2 != null) {
/*  660 */               arrayList.add(str2);
/*  661 */               arrayList.add("}");
/*      */             } 
/*      */             
/*  664 */             String str = str1.substring(1, str1.length() - 1).trim();
/*  665 */             if (str.isEmpty()) {
/*  666 */               throw new KrbException("Illegal config content:" + str1);
/*      */             }
/*      */             
/*  669 */             str2 = str + " = {"; continue;
/*  670 */           }  if (str1.startsWith("{")) {
/*  671 */             if (str2 == null) {
/*  672 */               throw new KrbException("Config file should not start with \"{\"");
/*      */             }
/*      */             
/*  675 */             str2 = str2 + " {";
/*  676 */             if (str1.length() > 1) {
/*      */               
/*  678 */               arrayList.add(str2);
/*  679 */               str2 = str1.substring(1).trim();
/*      */             } 
/*      */             continue;
/*      */           } 
/*  683 */           if (str2 != null) {
/*  684 */             arrayList.add(str2);
/*  685 */             str2 = str1;
/*      */           } 
/*      */         } 
/*      */         
/*  689 */         if (str2 != null) {
/*  690 */           arrayList.add(str2);
/*  691 */           arrayList.add("}");
/*      */         } 
/*      */       } 
/*  694 */       return arrayList;
/*  695 */     } catch (PrivilegedActionException privilegedActionException) {
/*  696 */       throw (IOException)privilegedActionException.getException();
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
/*      */   private Hashtable<String, Object> parseStanzaTable(List<String> paramList) throws KrbException {
/*      */     Hashtable<Object, Object> hashtable;
/*  724 */     Hashtable<String, Object> hashtable1 = this.stanzaTable;
/*  725 */     for (String str1 : paramList) {
/*      */       Vector<String> vector;
/*      */ 
/*      */ 
/*      */       
/*  730 */       if (str1.equals("}")) {
/*      */         
/*  732 */         hashtable1 = (Hashtable<String, Object>)hashtable1.remove(" PARENT ");
/*  733 */         if (hashtable1 == null)
/*  734 */           throw new KrbException("Unmatched close brace"); 
/*      */         continue;
/*      */       } 
/*  737 */       int i = str1.indexOf('=');
/*  738 */       if (i < 0) {
/*  739 */         throw new KrbException("Illegal config content:" + str1);
/*      */       }
/*  741 */       String str2 = str1.substring(0, i).trim();
/*  742 */       String str3 = trimmed(str1.substring(i + 1));
/*  743 */       if (str3.equals("{")) {
/*      */         
/*  745 */         if (hashtable1 == this.stanzaTable) {
/*  746 */           str2 = str2.toLowerCase(Locale.US);
/*      */         }
/*  748 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  749 */         hashtable1.put(str2, hashtable2);
/*      */ 
/*      */         
/*  752 */         hashtable2.put(" PARENT ", hashtable1);
/*  753 */         hashtable = hashtable2;
/*      */         continue;
/*      */       } 
/*  756 */       if (hashtable.containsKey(str2)) {
/*  757 */         Object object = hashtable.get(str2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  762 */         if (!(object instanceof Vector)) {
/*  763 */           throw new KrbException("Key " + str2 + "used for both value and section");
/*      */         }
/*      */         
/*  766 */         vector = (Vector)hashtable.get(str2);
/*      */       } else {
/*  768 */         vector = new Vector();
/*  769 */         hashtable.put(str2, vector);
/*      */       } 
/*  771 */       vector.add(str3);
/*      */     } 
/*      */ 
/*      */     
/*  775 */     if (hashtable != this.stanzaTable) {
/*  776 */       throw new KrbException("Not closed");
/*      */     }
/*  778 */     return (Hashtable)hashtable;
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
/*      */   private String getJavaFileName() {
/*  792 */     String str = getProperty("java.security.krb5.conf");
/*  793 */     if (str == null) {
/*  794 */       str = getProperty("java.home") + File.separator + "lib" + File.separator + "security" + File.separator + "krb5.conf";
/*      */ 
/*      */       
/*  797 */       if (!fileExists(str)) {
/*  798 */         str = null;
/*      */       }
/*      */     } 
/*  801 */     if (DEBUG) {
/*  802 */       System.out.println("Java config name: " + str);
/*      */     }
/*  804 */     return str;
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
/*      */   private String getNativeFileName() {
/*  825 */     String str1 = null;
/*  826 */     String str2 = getProperty("os.name");
/*  827 */     if (str2.startsWith("Windows")) {
/*      */       try {
/*  829 */         Credentials.ensureLoaded();
/*  830 */       } catch (Exception exception) {}
/*      */ 
/*      */       
/*  833 */       if (Credentials.alreadyLoaded) {
/*  834 */         String str = getWindowsDirectory(false);
/*  835 */         if (str != null) {
/*  836 */           if (str.endsWith("\\")) {
/*  837 */             str = str + "krb5.ini";
/*      */           } else {
/*  839 */             str = str + "\\krb5.ini";
/*      */           } 
/*  841 */           if (fileExists(str)) {
/*  842 */             str1 = str;
/*      */           }
/*      */         } 
/*  845 */         if (str1 == null) {
/*  846 */           str = getWindowsDirectory(true);
/*  847 */           if (str != null) {
/*  848 */             if (str.endsWith("\\")) {
/*  849 */               str = str + "krb5.ini";
/*      */             } else {
/*  851 */               str = str + "\\krb5.ini";
/*      */             } 
/*  853 */             str1 = str;
/*      */           } 
/*      */         } 
/*      */       } 
/*  857 */       if (str1 == null) {
/*  858 */         str1 = "c:\\winnt\\krb5.ini";
/*      */       }
/*  860 */     } else if (str2.startsWith("SunOS")) {
/*  861 */       str1 = "/etc/krb5/krb5.conf";
/*  862 */     } else if (str2.contains("OS X")) {
/*  863 */       str1 = findMacosConfigFile();
/*      */     } else {
/*  865 */       str1 = "/etc/krb5.conf";
/*      */     } 
/*  867 */     if (DEBUG) {
/*  868 */       System.out.println("Native config name: " + str1);
/*      */     }
/*  870 */     return str1;
/*      */   }
/*      */   
/*      */   private static String getProperty(String paramString) {
/*  874 */     return AccessController.<String>doPrivileged(new GetPropertyAction(paramString));
/*      */   }
/*      */ 
/*      */   
/*      */   private String findMacosConfigFile() {
/*  879 */     String str1 = getProperty("user.home");
/*      */     
/*  881 */     String str2 = str1 + "/Library/Preferences/edu.mit.Kerberos";
/*      */     
/*  883 */     if (fileExists(str2)) {
/*  884 */       return str2;
/*      */     }
/*      */     
/*  887 */     if (fileExists("/Library/Preferences/edu.mit.Kerberos")) {
/*  888 */       return "/Library/Preferences/edu.mit.Kerberos";
/*      */     }
/*      */     
/*  891 */     return "/etc/krb5.conf";
/*      */   }
/*      */   
/*      */   private static String trimmed(String paramString) {
/*  895 */     paramString = paramString.trim();
/*  896 */     if (paramString.length() >= 2 && ((paramString
/*  897 */       .charAt(0) == '"' && paramString.charAt(paramString.length() - 1) == '"') || (paramString
/*  898 */       .charAt(0) == '\'' && paramString.charAt(paramString.length() - 1) == '\''))) {
/*  899 */       paramString = paramString.substring(1, paramString.length() - 1).trim();
/*      */     }
/*  901 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void listTable() {
/*  909 */     System.out.println(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] defaultEtype(String paramString) throws KrbException {
/*      */     int[] arrayOfInt;
/*  919 */     String str = get(new String[] { "libdefaults", paramString });
/*      */     
/*  921 */     if (str == null) {
/*  922 */       if (DEBUG) {
/*  923 */         System.out.println("Using builtin default etypes for " + paramString);
/*      */       }
/*      */       
/*  926 */       arrayOfInt = EType.getBuiltInDefaults();
/*      */     } else {
/*  928 */       String str1 = " ";
/*      */       int i;
/*  930 */       for (i = 0; i < str.length(); i++) {
/*  931 */         if (str.substring(i, i + 1).equals(",")) {
/*      */ 
/*      */           
/*  934 */           str1 = ",";
/*      */           break;
/*      */         } 
/*      */       } 
/*  938 */       StringTokenizer stringTokenizer = new StringTokenizer(str, str1);
/*  939 */       i = stringTokenizer.countTokens();
/*  940 */       ArrayList<Integer> arrayList = new ArrayList(i);
/*      */       byte b;
/*  942 */       for (b = 0; b < i; b++) {
/*  943 */         int j = getType(stringTokenizer.nextToken());
/*  944 */         if (j != -1 && EType.isSupported(j)) {
/*  945 */           arrayList.add(Integer.valueOf(j));
/*      */         }
/*      */       } 
/*  948 */       if (arrayList.isEmpty()) {
/*  949 */         throw new KrbException("no supported default etypes for " + paramString);
/*      */       }
/*      */       
/*  952 */       arrayOfInt = new int[arrayList.size()];
/*  953 */       for (b = 0; b < arrayOfInt.length; b++) {
/*  954 */         arrayOfInt[b] = ((Integer)arrayList.get(b)).intValue();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  959 */     if (DEBUG) {
/*  960 */       System.out.print("default etypes for " + paramString + ":");
/*  961 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/*  962 */         System.out.print(" " + arrayOfInt[b]);
/*      */       }
/*  964 */       System.out.println(".");
/*      */     } 
/*  966 */     return arrayOfInt;
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
/*      */   public static int getType(String paramString) {
/*  981 */     short s = -1;
/*  982 */     if (paramString == null) {
/*  983 */       return s;
/*      */     }
/*  985 */     if (paramString.startsWith("d") || paramString.startsWith("D")) {
/*  986 */       if (paramString.equalsIgnoreCase("des-cbc-crc")) {
/*  987 */         s = 1;
/*  988 */       } else if (paramString.equalsIgnoreCase("des-cbc-md5")) {
/*  989 */         s = 3;
/*  990 */       } else if (paramString.equalsIgnoreCase("des-mac")) {
/*  991 */         s = 4;
/*  992 */       } else if (paramString.equalsIgnoreCase("des-mac-k")) {
/*  993 */         s = 5;
/*  994 */       } else if (paramString.equalsIgnoreCase("des-cbc-md4")) {
/*  995 */         s = 2;
/*  996 */       } else if (paramString.equalsIgnoreCase("des3-cbc-sha1") || paramString
/*  997 */         .equalsIgnoreCase("des3-hmac-sha1") || paramString
/*  998 */         .equalsIgnoreCase("des3-cbc-sha1-kd") || paramString
/*  999 */         .equalsIgnoreCase("des3-cbc-hmac-sha1-kd")) {
/* 1000 */         s = 16;
/*      */       } 
/* 1002 */     } else if (paramString.startsWith("a") || paramString.startsWith("A")) {
/*      */       
/* 1004 */       if (paramString.equalsIgnoreCase("aes128-cts") || paramString
/* 1005 */         .equalsIgnoreCase("aes128-cts-hmac-sha1-96")) {
/* 1006 */         s = 17;
/* 1007 */       } else if (paramString.equalsIgnoreCase("aes256-cts") || paramString
/* 1008 */         .equalsIgnoreCase("aes256-cts-hmac-sha1-96")) {
/* 1009 */         s = 18;
/*      */       }
/* 1011 */       else if (paramString.equalsIgnoreCase("arcfour-hmac") || paramString
/* 1012 */         .equalsIgnoreCase("arcfour-hmac-md5")) {
/* 1013 */         s = 23;
/*      */       }
/*      */     
/* 1016 */     } else if (paramString.equalsIgnoreCase("rc4-hmac")) {
/* 1017 */       s = 23;
/* 1018 */     } else if (paramString.equalsIgnoreCase("CRC32")) {
/* 1019 */       s = 1;
/* 1020 */     } else if (paramString.startsWith("r") || paramString.startsWith("R")) {
/* 1021 */       if (paramString.equalsIgnoreCase("rsa-md5")) {
/* 1022 */         s = 7;
/* 1023 */       } else if (paramString.equalsIgnoreCase("rsa-md5-des")) {
/* 1024 */         s = 8;
/*      */       } 
/* 1026 */     } else if (paramString.equalsIgnoreCase("hmac-sha1-des3-kd")) {
/* 1027 */       s = 12;
/* 1028 */     } else if (paramString.equalsIgnoreCase("hmac-sha1-96-aes128")) {
/* 1029 */       s = 15;
/* 1030 */     } else if (paramString.equalsIgnoreCase("hmac-sha1-96-aes256")) {
/* 1031 */       s = 16;
/* 1032 */     } else if (paramString.equalsIgnoreCase("hmac-md5-rc4") || paramString
/* 1033 */       .equalsIgnoreCase("hmac-md5-arcfour") || paramString
/* 1034 */       .equalsIgnoreCase("hmac-md5-enc")) {
/* 1035 */       s = -138;
/* 1036 */     } else if (paramString.equalsIgnoreCase("NULL")) {
/* 1037 */       s = 0;
/*      */     } 
/*      */     
/* 1040 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetDefaultRealm(String paramString) {
/* 1050 */     if (DEBUG) {
/* 1051 */       System.out.println(">>> Config try resetting default kdc " + paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean useAddresses() {
/* 1060 */     boolean bool = false;
/*      */     
/* 1062 */     String str = get(new String[] { "libdefaults", "no_addresses" });
/* 1063 */     bool = (str != null && str.equalsIgnoreCase("false")) ? true : false;
/* 1064 */     if (!bool) {
/*      */       
/* 1066 */       str = get(new String[] { "libdefaults", "noaddresses" });
/* 1067 */       bool = (str != null && str.equalsIgnoreCase("false")) ? true : false;
/*      */     } 
/* 1069 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useDNS(String paramString, boolean paramBoolean) {
/* 1076 */     Boolean bool = getBooleanObject(new String[] { "libdefaults", paramString });
/* 1077 */     if (bool != null) {
/* 1078 */       return bool.booleanValue();
/*      */     }
/* 1080 */     bool = getBooleanObject(new String[] { "libdefaults", "dns_fallback" });
/* 1081 */     if (bool != null) {
/* 1082 */       return bool.booleanValue();
/*      */     }
/* 1084 */     return paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useDNS_KDC() {
/* 1091 */     return useDNS("dns_lookup_kdc", true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useDNS_Realm() {
/* 1098 */     return useDNS("dns_lookup_realm", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultRealm() throws KrbException {
/* 1107 */     if (this.defaultRealm != null) {
/* 1108 */       return this.defaultRealm;
/*      */     }
/* 1110 */     KrbException krbException = null;
/* 1111 */     String str = get(new String[] { "libdefaults", "default_realm" });
/* 1112 */     if (str == null && useDNS_Realm()) {
/*      */       
/*      */       try {
/* 1115 */         str = getRealmFromDNS();
/* 1116 */       } catch (KrbException krbException1) {
/* 1117 */         krbException = krbException1;
/*      */       } 
/*      */     }
/* 1120 */     if (str == null) {
/* 1121 */       str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */           {
/*      */             public String run()
/*      */             {
/* 1125 */               String str = System.getProperty("os.name");
/* 1126 */               if (str.startsWith("Windows")) {
/* 1127 */                 return System.getenv("USERDNSDOMAIN");
/*      */               }
/* 1129 */               return null;
/*      */             }
/*      */           });
/*      */     }
/* 1133 */     if (str == null) {
/* 1134 */       KrbException krbException1 = new KrbException("Cannot locate default realm");
/* 1135 */       if (krbException != null) {
/* 1136 */         krbException1.initCause(krbException);
/*      */       }
/* 1138 */       throw krbException1;
/*      */     } 
/* 1140 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKDCList(String paramString) throws KrbException {
/* 1151 */     if (paramString == null) {
/* 1152 */       paramString = getDefaultRealm();
/*      */     }
/* 1154 */     if (paramString.equalsIgnoreCase(this.defaultRealm)) {
/* 1155 */       return this.defaultKDC;
/*      */     }
/* 1157 */     KrbException krbException = null;
/* 1158 */     String str = getAll(new String[] { "realms", paramString, "kdc" });
/* 1159 */     if (str == null && useDNS_KDC()) {
/*      */       
/*      */       try {
/* 1162 */         str = getKDCFromDNS(paramString);
/* 1163 */       } catch (KrbException krbException1) {
/* 1164 */         krbException = krbException1;
/*      */       } 
/*      */     }
/* 1167 */     if (str == null) {
/* 1168 */       str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */           {
/*      */             public String run()
/*      */             {
/* 1172 */               String str = System.getProperty("os.name");
/* 1173 */               if (str.startsWith("Windows")) {
/* 1174 */                 String str1 = System.getenv("LOGONSERVER");
/* 1175 */                 if (str1 != null && str1
/* 1176 */                   .startsWith("\\\\")) {
/* 1177 */                   str1 = str1.substring(2);
/*      */                 }
/* 1179 */                 return str1;
/*      */               } 
/* 1181 */               return null;
/*      */             }
/*      */           });
/*      */     }
/* 1185 */     if (str == null) {
/* 1186 */       if (this.defaultKDC != null) {
/* 1187 */         return this.defaultKDC;
/*      */       }
/* 1189 */       KrbException krbException1 = new KrbException("Cannot locate KDC");
/* 1190 */       if (krbException != null) {
/* 1191 */         krbException1.initCause(krbException);
/*      */       }
/* 1193 */       throw krbException1;
/*      */     } 
/* 1195 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getRealmFromDNS() throws KrbException {
/* 1205 */     String str1 = null;
/* 1206 */     String str2 = null;
/*      */     try {
/* 1208 */       str2 = InetAddress.getLocalHost().getCanonicalHostName();
/* 1209 */     } catch (UnknownHostException unknownHostException) {
/*      */       
/* 1211 */       KrbException krbException = new KrbException(60, "Unable to locate Kerberos realm: " + unknownHostException.getMessage());
/* 1212 */       krbException.initCause(unknownHostException);
/* 1213 */       throw krbException;
/*      */     } 
/*      */     
/* 1216 */     String str3 = PrincipalName.mapHostToRealm(str2);
/* 1217 */     if (str3 == null) {
/*      */       
/* 1219 */       List<String> list = ResolverConfiguration.open().searchlist();
/* 1220 */       for (String str : list) {
/* 1221 */         str1 = checkRealm(str);
/* 1222 */         if (str1 != null) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } else {
/* 1227 */       str1 = checkRealm(str3);
/*      */     } 
/* 1229 */     if (str1 == null) {
/* 1230 */       throw new KrbException(60, "Unable to locate Kerberos realm");
/*      */     }
/*      */     
/* 1233 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String checkRealm(String paramString) {
/* 1241 */     if (DEBUG) {
/* 1242 */       System.out.println("getRealmFromDNS: trying " + paramString);
/*      */     }
/* 1244 */     String[] arrayOfString = null;
/* 1245 */     String str = paramString;
/* 1246 */     while (arrayOfString == null && str != null) {
/*      */       
/* 1248 */       arrayOfString = KrbServiceLocator.getKerberosService(str);
/* 1249 */       str = Realm.parseRealmComponent(str);
/*      */     } 
/*      */     
/* 1252 */     if (arrayOfString != null) {
/* 1253 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1254 */         if (arrayOfString[b].equalsIgnoreCase(paramString)) {
/* 1255 */           return arrayOfString[b];
/*      */         }
/*      */       } 
/*      */     }
/* 1259 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getKDCFromDNS(String paramString) throws KrbException {
/* 1270 */     String str = "";
/* 1271 */     String[] arrayOfString = null;
/*      */     
/* 1273 */     if (DEBUG) {
/* 1274 */       System.out.println("getKDCFromDNS using UDP");
/*      */     }
/* 1276 */     arrayOfString = KrbServiceLocator.getKerberosService(paramString, "_udp");
/* 1277 */     if (arrayOfString == null) {
/*      */       
/* 1279 */       if (DEBUG) {
/* 1280 */         System.out.println("getKDCFromDNS using TCP");
/*      */       }
/* 1282 */       arrayOfString = KrbServiceLocator.getKerberosService(paramString, "_tcp");
/*      */     } 
/* 1284 */     if (arrayOfString == null)
/*      */     {
/* 1286 */       throw new KrbException(60, "Unable to locate KDC for realm " + paramString);
/*      */     }
/*      */     
/* 1289 */     if (arrayOfString.length == 0) {
/* 1290 */       return null;
/*      */     }
/* 1292 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1293 */       str = str + arrayOfString[b].trim() + " ";
/*      */     }
/* 1295 */     str = str.trim();
/* 1296 */     if (str.equals("")) {
/* 1297 */       return null;
/*      */     }
/* 1299 */     return str;
/*      */   }
/*      */   
/*      */   private boolean fileExists(String paramString) {
/* 1303 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new FileExistsAction(paramString))).booleanValue();
/*      */   }
/*      */ 
/*      */   
/*      */   static class FileExistsAction
/*      */     implements PrivilegedAction<Boolean>
/*      */   {
/*      */     private String fileName;
/*      */     
/*      */     public FileExistsAction(String param1String) {
/* 1313 */       this.fileName = param1String;
/*      */     }
/*      */     
/*      */     public Boolean run() {
/* 1317 */       return Boolean.valueOf((new File(this.fileName)).exists());
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
/*      */   public String toString() {
/* 1336 */     StringBuffer stringBuffer = new StringBuffer();
/* 1337 */     toStringInternal("", this.stanzaTable, stringBuffer);
/* 1338 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private static void toStringInternal(String paramString, Object paramObject, StringBuffer paramStringBuffer) {
/* 1342 */     if (paramObject instanceof String) {
/*      */       
/* 1344 */       paramStringBuffer.append(paramObject).append('\n');
/* 1345 */     } else if (paramObject instanceof Hashtable) {
/*      */       
/* 1347 */       Hashtable hashtable = (Hashtable)paramObject;
/* 1348 */       paramStringBuffer.append("{\n");
/* 1349 */       for (Object object : hashtable.keySet()) {
/*      */         
/* 1351 */         paramStringBuffer.append(paramString).append("    ").append(object).append(" = ");
/*      */         
/* 1353 */         toStringInternal(paramString + "    ", hashtable.get(object), paramStringBuffer);
/*      */       } 
/* 1355 */       paramStringBuffer.append(paramString).append("}\n");
/* 1356 */     } else if (paramObject instanceof Vector) {
/*      */       
/* 1358 */       Vector vector = (Vector)paramObject;
/* 1359 */       paramStringBuffer.append("[");
/* 1360 */       boolean bool = true;
/* 1361 */       for (Object object : vector.toArray()) {
/* 1362 */         if (!bool) paramStringBuffer.append(","); 
/* 1363 */         paramStringBuffer.append(object);
/* 1364 */         bool = false;
/*      */       } 
/* 1366 */       paramStringBuffer.append("]\n");
/*      */     } 
/*      */   }
/*      */   
/*      */   private static native String getWindowsDirectory(boolean paramBoolean);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */