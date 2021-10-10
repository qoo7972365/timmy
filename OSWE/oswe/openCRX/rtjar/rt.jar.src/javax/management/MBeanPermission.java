/*      */ package javax.management;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.security.Permission;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MBeanPermission
/*      */   extends Permission
/*      */ {
/*      */   private static final long serialVersionUID = -2416928705275160661L;
/*      */   private static final int AddNotificationListener = 1;
/*      */   private static final int GetAttribute = 2;
/*      */   private static final int GetClassLoader = 4;
/*      */   private static final int GetClassLoaderFor = 8;
/*      */   private static final int GetClassLoaderRepository = 16;
/*      */   private static final int GetDomains = 32;
/*      */   private static final int GetMBeanInfo = 64;
/*      */   private static final int GetObjectInstance = 128;
/*      */   private static final int Instantiate = 256;
/*      */   private static final int Invoke = 512;
/*      */   private static final int IsInstanceOf = 1024;
/*      */   private static final int QueryMBeans = 2048;
/*      */   private static final int QueryNames = 4096;
/*      */   private static final int RegisterMBean = 8192;
/*      */   private static final int RemoveNotificationListener = 16384;
/*      */   private static final int SetAttribute = 32768;
/*      */   private static final int UnregisterMBean = 65536;
/*      */   private static final int NONE = 0;
/*      */   private static final int ALL = 131071;
/*      */   private String actions;
/*      */   private transient int mask;
/*      */   private transient String classNamePrefix;
/*      */   private transient boolean classNameExactMatch;
/*      */   private transient String member;
/*      */   private transient ObjectName objectName;
/*      */   
/*      */   private void parseActions() {
/*  256 */     if (this.actions == null) {
/*  257 */       throw new IllegalArgumentException("MBeanPermission: actions can't be null");
/*      */     }
/*  259 */     if (this.actions.equals("")) {
/*  260 */       throw new IllegalArgumentException("MBeanPermission: actions can't be empty");
/*      */     }
/*      */     
/*  263 */     int i = getMask(this.actions);
/*      */     
/*  265 */     if ((i & 0x1FFFF) != i)
/*  266 */       throw new IllegalArgumentException("Invalid actions mask"); 
/*  267 */     if (i == 0)
/*  268 */       throw new IllegalArgumentException("Invalid actions mask"); 
/*  269 */     this.mask = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseName() {
/*  276 */     String str = getName();
/*      */     
/*  278 */     if (str == null) {
/*  279 */       throw new IllegalArgumentException("MBeanPermission name cannot be null");
/*      */     }
/*      */     
/*  282 */     if (str.equals("")) {
/*  283 */       throw new IllegalArgumentException("MBeanPermission name cannot be empty");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  293 */     int i = str.indexOf("[");
/*  294 */     if (i == -1) {
/*      */ 
/*      */       
/*  297 */       this.objectName = ObjectName.WILDCARD;
/*      */     } else {
/*  299 */       if (!str.endsWith("]")) {
/*  300 */         throw new IllegalArgumentException("MBeanPermission: The ObjectName in the target name must be included in square brackets");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  311 */         String str1 = str.substring(i + 1, str
/*  312 */             .length() - 1);
/*  313 */         if (str1.equals(""))
/*  314 */         { this.objectName = ObjectName.WILDCARD; }
/*  315 */         else if (str1.equals("-"))
/*  316 */         { this.objectName = null; }
/*      */         else
/*  318 */         { this.objectName = new ObjectName(str1); } 
/*  319 */       } catch (MalformedObjectNameException malformedObjectNameException) {
/*  320 */         throw new IllegalArgumentException("MBeanPermission: The target name does not specify a valid ObjectName", malformedObjectNameException);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  327 */       str = str.substring(0, i);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  332 */     int j = str.indexOf("#");
/*      */     
/*  334 */     if (j == -1) {
/*  335 */       setMember("*");
/*      */     } else {
/*  337 */       String str1 = str.substring(j + 1);
/*  338 */       setMember(str1);
/*  339 */       str = str.substring(0, j);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  344 */     setClassName(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initName(String paramString1, String paramString2, ObjectName paramObjectName) {
/*  353 */     setClassName(paramString1);
/*  354 */     setMember(paramString2);
/*  355 */     this.objectName = paramObjectName;
/*      */   }
/*      */   
/*      */   private void setClassName(String paramString) {
/*  359 */     if (paramString == null || paramString.equals("-")) {
/*  360 */       this.classNamePrefix = null;
/*  361 */       this.classNameExactMatch = false;
/*  362 */     } else if (paramString.equals("") || paramString.equals("*")) {
/*  363 */       this.classNamePrefix = "";
/*  364 */       this.classNameExactMatch = false;
/*  365 */     } else if (paramString.endsWith(".*")) {
/*      */       
/*  367 */       this.classNamePrefix = paramString.substring(0, paramString.length() - 1);
/*  368 */       this.classNameExactMatch = false;
/*      */     } else {
/*  370 */       this.classNamePrefix = paramString;
/*  371 */       this.classNameExactMatch = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setMember(String paramString) {
/*  376 */     if (paramString == null || paramString.equals("-")) {
/*  377 */       this.member = null;
/*  378 */     } else if (paramString.equals("")) {
/*  379 */       this.member = "*";
/*      */     } else {
/*  381 */       this.member = paramString;
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
/*      */   public MBeanPermission(String paramString1, String paramString2) {
/*  403 */     super(paramString1);
/*      */     
/*  405 */     parseName();
/*      */     
/*  407 */     this.actions = paramString2;
/*  408 */     parseActions();
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
/*      */   public MBeanPermission(String paramString1, String paramString2, ObjectName paramObjectName, String paramString3) {
/*  443 */     super(makeName(paramString1, paramString2, paramObjectName));
/*  444 */     initName(paramString1, paramString2, paramObjectName);
/*      */     
/*  446 */     this.actions = paramString3;
/*  447 */     parseActions();
/*      */   }
/*      */ 
/*      */   
/*      */   private static String makeName(String paramString1, String paramString2, ObjectName paramObjectName) {
/*  452 */     StringBuilder stringBuilder = new StringBuilder();
/*  453 */     if (paramString1 == null)
/*  454 */       paramString1 = "-"; 
/*  455 */     stringBuilder.append(paramString1);
/*  456 */     if (paramString2 == null)
/*  457 */       paramString2 = "-"; 
/*  458 */     stringBuilder.append("#" + paramString2);
/*  459 */     if (paramObjectName == null) {
/*  460 */       stringBuilder.append("[-]");
/*      */     } else {
/*  462 */       stringBuilder.append("[").append(paramObjectName.getCanonicalName()).append("]");
/*      */     } 
/*      */ 
/*      */     
/*  466 */     if (stringBuilder.length() == 0) {
/*  467 */       return "*";
/*      */     }
/*  469 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getActions() {
/*  480 */     if (this.actions == null) {
/*  481 */       this.actions = getActions(this.mask);
/*      */     }
/*  483 */     return this.actions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getActions(int paramInt) {
/*  491 */     StringBuilder stringBuilder = new StringBuilder();
/*  492 */     boolean bool = false;
/*      */     
/*  494 */     if ((paramInt & 0x1) == 1) {
/*  495 */       bool = true;
/*  496 */       stringBuilder.append("addNotificationListener");
/*      */     } 
/*      */     
/*  499 */     if ((paramInt & 0x2) == 2) {
/*  500 */       if (bool) { stringBuilder.append(','); }
/*  501 */       else { bool = true; }
/*  502 */        stringBuilder.append("getAttribute");
/*      */     } 
/*      */     
/*  505 */     if ((paramInt & 0x4) == 4) {
/*  506 */       if (bool) { stringBuilder.append(','); }
/*  507 */       else { bool = true; }
/*  508 */        stringBuilder.append("getClassLoader");
/*      */     } 
/*      */     
/*  511 */     if ((paramInt & 0x8) == 8) {
/*  512 */       if (bool) { stringBuilder.append(','); }
/*  513 */       else { bool = true; }
/*  514 */        stringBuilder.append("getClassLoaderFor");
/*      */     } 
/*      */     
/*  517 */     if ((paramInt & 0x10) == 16) {
/*  518 */       if (bool) { stringBuilder.append(','); }
/*  519 */       else { bool = true; }
/*  520 */        stringBuilder.append("getClassLoaderRepository");
/*      */     } 
/*      */     
/*  523 */     if ((paramInt & 0x20) == 32) {
/*  524 */       if (bool) { stringBuilder.append(','); }
/*  525 */       else { bool = true; }
/*  526 */        stringBuilder.append("getDomains");
/*      */     } 
/*      */     
/*  529 */     if ((paramInt & 0x40) == 64) {
/*  530 */       if (bool) { stringBuilder.append(','); }
/*  531 */       else { bool = true; }
/*  532 */        stringBuilder.append("getMBeanInfo");
/*      */     } 
/*      */     
/*  535 */     if ((paramInt & 0x80) == 128) {
/*  536 */       if (bool) { stringBuilder.append(','); }
/*  537 */       else { bool = true; }
/*  538 */        stringBuilder.append("getObjectInstance");
/*      */     } 
/*      */     
/*  541 */     if ((paramInt & 0x100) == 256) {
/*  542 */       if (bool) { stringBuilder.append(','); }
/*  543 */       else { bool = true; }
/*  544 */        stringBuilder.append("instantiate");
/*      */     } 
/*      */     
/*  547 */     if ((paramInt & 0x200) == 512) {
/*  548 */       if (bool) { stringBuilder.append(','); }
/*  549 */       else { bool = true; }
/*  550 */        stringBuilder.append("invoke");
/*      */     } 
/*      */     
/*  553 */     if ((paramInt & 0x400) == 1024) {
/*  554 */       if (bool) { stringBuilder.append(','); }
/*  555 */       else { bool = true; }
/*  556 */        stringBuilder.append("isInstanceOf");
/*      */     } 
/*      */     
/*  559 */     if ((paramInt & 0x800) == 2048) {
/*  560 */       if (bool) { stringBuilder.append(','); }
/*  561 */       else { bool = true; }
/*  562 */        stringBuilder.append("queryMBeans");
/*      */     } 
/*      */     
/*  565 */     if ((paramInt & 0x1000) == 4096) {
/*  566 */       if (bool) { stringBuilder.append(','); }
/*  567 */       else { bool = true; }
/*  568 */        stringBuilder.append("queryNames");
/*      */     } 
/*      */     
/*  571 */     if ((paramInt & 0x2000) == 8192) {
/*  572 */       if (bool) { stringBuilder.append(','); }
/*  573 */       else { bool = true; }
/*  574 */        stringBuilder.append("registerMBean");
/*      */     } 
/*      */     
/*  577 */     if ((paramInt & 0x4000) == 16384) {
/*  578 */       if (bool) { stringBuilder.append(','); }
/*  579 */       else { bool = true; }
/*  580 */        stringBuilder.append("removeNotificationListener");
/*      */     } 
/*      */     
/*  583 */     if ((paramInt & 0x8000) == 32768) {
/*  584 */       if (bool) { stringBuilder.append(','); }
/*  585 */       else { bool = true; }
/*  586 */        stringBuilder.append("setAttribute");
/*      */     } 
/*      */     
/*  589 */     if ((paramInt & 0x10000) == 65536) {
/*  590 */       if (bool) { stringBuilder.append(','); }
/*  591 */       else { bool = true; }
/*  592 */        stringBuilder.append("unregisterMBean");
/*      */     } 
/*      */     
/*  595 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  604 */     return getName().hashCode() + getActions().hashCode();
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
/*      */   private static int getMask(String paramString) {
/*  627 */     int i = 0;
/*      */     
/*  629 */     if (paramString == null) {
/*  630 */       return i;
/*      */     }
/*      */     
/*  633 */     if (paramString.equals("*")) {
/*  634 */       return 131071;
/*      */     }
/*      */     
/*  637 */     char[] arrayOfChar = paramString.toCharArray();
/*      */     
/*  639 */     int j = arrayOfChar.length - 1;
/*  640 */     if (j < 0) {
/*  641 */       return i;
/*      */     }
/*  643 */     while (j != -1) {
/*      */       byte b;
/*      */       
/*      */       char c;
/*  647 */       while (j != -1 && ((c = arrayOfChar[j]) == ' ' || c == '\r' || c == '\n' || c == '\f' || c == '\t'))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  652 */         j--;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  657 */       if (j >= 25 && arrayOfChar[j - 25] == 'r' && arrayOfChar[j - 24] == 'e' && arrayOfChar[j - 23] == 'm' && arrayOfChar[j - 22] == 'o' && arrayOfChar[j - 21] == 'v' && arrayOfChar[j - 20] == 'e' && arrayOfChar[j - 19] == 'N' && arrayOfChar[j - 18] == 'o' && arrayOfChar[j - 17] == 't' && arrayOfChar[j - 16] == 'i' && arrayOfChar[j - 15] == 'f' && arrayOfChar[j - 14] == 'i' && arrayOfChar[j - 13] == 'c' && arrayOfChar[j - 12] == 'a' && arrayOfChar[j - 11] == 't' && arrayOfChar[j - 10] == 'i' && arrayOfChar[j - 9] == 'o' && arrayOfChar[j - 8] == 'n' && arrayOfChar[j - 7] == 'L' && arrayOfChar[j - 6] == 'i' && arrayOfChar[j - 5] == 's' && arrayOfChar[j - 4] == 't' && arrayOfChar[j - 3] == 'e' && arrayOfChar[j - 2] == 'n' && arrayOfChar[j - 1] == 'e' && arrayOfChar[j] == 'r') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  684 */         b = 26;
/*  685 */         i |= 0x4000;
/*  686 */       } else if (j >= 23 && arrayOfChar[j - 23] == 'g' && arrayOfChar[j - 22] == 'e' && arrayOfChar[j - 21] == 't' && arrayOfChar[j - 20] == 'C' && arrayOfChar[j - 19] == 'l' && arrayOfChar[j - 18] == 'a' && arrayOfChar[j - 17] == 's' && arrayOfChar[j - 16] == 's' && arrayOfChar[j - 15] == 'L' && arrayOfChar[j - 14] == 'o' && arrayOfChar[j - 13] == 'a' && arrayOfChar[j - 12] == 'd' && arrayOfChar[j - 11] == 'e' && arrayOfChar[j - 10] == 'r' && arrayOfChar[j - 9] == 'R' && arrayOfChar[j - 8] == 'e' && arrayOfChar[j - 7] == 'p' && arrayOfChar[j - 6] == 'o' && arrayOfChar[j - 5] == 's' && arrayOfChar[j - 4] == 'i' && arrayOfChar[j - 3] == 't' && arrayOfChar[j - 2] == 'o' && arrayOfChar[j - 1] == 'r' && arrayOfChar[j] == 'y') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  711 */         b = 24;
/*  712 */         i |= 0x10;
/*  713 */       } else if (j >= 22 && arrayOfChar[j - 22] == 'a' && arrayOfChar[j - 21] == 'd' && arrayOfChar[j - 20] == 'd' && arrayOfChar[j - 19] == 'N' && arrayOfChar[j - 18] == 'o' && arrayOfChar[j - 17] == 't' && arrayOfChar[j - 16] == 'i' && arrayOfChar[j - 15] == 'f' && arrayOfChar[j - 14] == 'i' && arrayOfChar[j - 13] == 'c' && arrayOfChar[j - 12] == 'a' && arrayOfChar[j - 11] == 't' && arrayOfChar[j - 10] == 'i' && arrayOfChar[j - 9] == 'o' && arrayOfChar[j - 8] == 'n' && arrayOfChar[j - 7] == 'L' && arrayOfChar[j - 6] == 'i' && arrayOfChar[j - 5] == 's' && arrayOfChar[j - 4] == 't' && arrayOfChar[j - 3] == 'e' && arrayOfChar[j - 2] == 'n' && arrayOfChar[j - 1] == 'e' && arrayOfChar[j] == 'r') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  737 */         b = 23;
/*  738 */         i |= 0x1;
/*  739 */       } else if (j >= 16 && arrayOfChar[j - 16] == 'g' && arrayOfChar[j - 15] == 'e' && arrayOfChar[j - 14] == 't' && arrayOfChar[j - 13] == 'C' && arrayOfChar[j - 12] == 'l' && arrayOfChar[j - 11] == 'a' && arrayOfChar[j - 10] == 's' && arrayOfChar[j - 9] == 's' && arrayOfChar[j - 8] == 'L' && arrayOfChar[j - 7] == 'o' && arrayOfChar[j - 6] == 'a' && arrayOfChar[j - 5] == 'd' && arrayOfChar[j - 4] == 'e' && arrayOfChar[j - 3] == 'r' && arrayOfChar[j - 2] == 'F' && arrayOfChar[j - 1] == 'o' && arrayOfChar[j] == 'r') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  757 */         b = 17;
/*  758 */         i |= 0x8;
/*  759 */       } else if (j >= 16 && arrayOfChar[j - 16] == 'g' && arrayOfChar[j - 15] == 'e' && arrayOfChar[j - 14] == 't' && arrayOfChar[j - 13] == 'O' && arrayOfChar[j - 12] == 'b' && arrayOfChar[j - 11] == 'j' && arrayOfChar[j - 10] == 'e' && arrayOfChar[j - 9] == 'c' && arrayOfChar[j - 8] == 't' && arrayOfChar[j - 7] == 'I' && arrayOfChar[j - 6] == 'n' && arrayOfChar[j - 5] == 's' && arrayOfChar[j - 4] == 't' && arrayOfChar[j - 3] == 'a' && arrayOfChar[j - 2] == 'n' && arrayOfChar[j - 1] == 'c' && arrayOfChar[j] == 'e') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  777 */         b = 17;
/*  778 */         i |= 0x80;
/*  779 */       } else if (j >= 14 && arrayOfChar[j - 14] == 'u' && arrayOfChar[j - 13] == 'n' && arrayOfChar[j - 12] == 'r' && arrayOfChar[j - 11] == 'e' && arrayOfChar[j - 10] == 'g' && arrayOfChar[j - 9] == 'i' && arrayOfChar[j - 8] == 's' && arrayOfChar[j - 7] == 't' && arrayOfChar[j - 6] == 'e' && arrayOfChar[j - 5] == 'r' && arrayOfChar[j - 4] == 'M' && arrayOfChar[j - 3] == 'B' && arrayOfChar[j - 2] == 'e' && arrayOfChar[j - 1] == 'a' && arrayOfChar[j] == 'n') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  795 */         b = 15;
/*  796 */         i |= 0x10000;
/*  797 */       } else if (j >= 13 && arrayOfChar[j - 13] == 'g' && arrayOfChar[j - 12] == 'e' && arrayOfChar[j - 11] == 't' && arrayOfChar[j - 10] == 'C' && arrayOfChar[j - 9] == 'l' && arrayOfChar[j - 8] == 'a' && arrayOfChar[j - 7] == 's' && arrayOfChar[j - 6] == 's' && arrayOfChar[j - 5] == 'L' && arrayOfChar[j - 4] == 'o' && arrayOfChar[j - 3] == 'a' && arrayOfChar[j - 2] == 'd' && arrayOfChar[j - 1] == 'e' && arrayOfChar[j] == 'r') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  812 */         b = 14;
/*  813 */         i |= 0x4;
/*  814 */       } else if (j >= 12 && arrayOfChar[j - 12] == 'r' && arrayOfChar[j - 11] == 'e' && arrayOfChar[j - 10] == 'g' && arrayOfChar[j - 9] == 'i' && arrayOfChar[j - 8] == 's' && arrayOfChar[j - 7] == 't' && arrayOfChar[j - 6] == 'e' && arrayOfChar[j - 5] == 'r' && arrayOfChar[j - 4] == 'M' && arrayOfChar[j - 3] == 'B' && arrayOfChar[j - 2] == 'e' && arrayOfChar[j - 1] == 'a' && arrayOfChar[j] == 'n') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  828 */         b = 13;
/*  829 */         i |= 0x2000;
/*  830 */       } else if (j >= 11 && arrayOfChar[j - 11] == 'g' && arrayOfChar[j - 10] == 'e' && arrayOfChar[j - 9] == 't' && arrayOfChar[j - 8] == 'A' && arrayOfChar[j - 7] == 't' && arrayOfChar[j - 6] == 't' && arrayOfChar[j - 5] == 'r' && arrayOfChar[j - 4] == 'i' && arrayOfChar[j - 3] == 'b' && arrayOfChar[j - 2] == 'u' && arrayOfChar[j - 1] == 't' && arrayOfChar[j] == 'e') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  843 */         b = 12;
/*  844 */         i |= 0x2;
/*  845 */       } else if (j >= 11 && arrayOfChar[j - 11] == 'g' && arrayOfChar[j - 10] == 'e' && arrayOfChar[j - 9] == 't' && arrayOfChar[j - 8] == 'M' && arrayOfChar[j - 7] == 'B' && arrayOfChar[j - 6] == 'e' && arrayOfChar[j - 5] == 'a' && arrayOfChar[j - 4] == 'n' && arrayOfChar[j - 3] == 'I' && arrayOfChar[j - 2] == 'n' && arrayOfChar[j - 1] == 'f' && arrayOfChar[j] == 'o') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  858 */         b = 12;
/*  859 */         i |= 0x40;
/*  860 */       } else if (j >= 11 && arrayOfChar[j - 11] == 'i' && arrayOfChar[j - 10] == 's' && arrayOfChar[j - 9] == 'I' && arrayOfChar[j - 8] == 'n' && arrayOfChar[j - 7] == 's' && arrayOfChar[j - 6] == 't' && arrayOfChar[j - 5] == 'a' && arrayOfChar[j - 4] == 'n' && arrayOfChar[j - 3] == 'c' && arrayOfChar[j - 2] == 'e' && arrayOfChar[j - 1] == 'O' && arrayOfChar[j] == 'f') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  873 */         b = 12;
/*  874 */         i |= 0x400;
/*  875 */       } else if (j >= 11 && arrayOfChar[j - 11] == 's' && arrayOfChar[j - 10] == 'e' && arrayOfChar[j - 9] == 't' && arrayOfChar[j - 8] == 'A' && arrayOfChar[j - 7] == 't' && arrayOfChar[j - 6] == 't' && arrayOfChar[j - 5] == 'r' && arrayOfChar[j - 4] == 'i' && arrayOfChar[j - 3] == 'b' && arrayOfChar[j - 2] == 'u' && arrayOfChar[j - 1] == 't' && arrayOfChar[j] == 'e') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  888 */         b = 12;
/*  889 */         i |= 0x8000;
/*  890 */       } else if (j >= 10 && arrayOfChar[j - 10] == 'i' && arrayOfChar[j - 9] == 'n' && arrayOfChar[j - 8] == 's' && arrayOfChar[j - 7] == 't' && arrayOfChar[j - 6] == 'a' && arrayOfChar[j - 5] == 'n' && arrayOfChar[j - 4] == 't' && arrayOfChar[j - 3] == 'i' && arrayOfChar[j - 2] == 'a' && arrayOfChar[j - 1] == 't' && arrayOfChar[j] == 'e') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  902 */         b = 11;
/*  903 */         i |= 0x100;
/*  904 */       } else if (j >= 10 && arrayOfChar[j - 10] == 'q' && arrayOfChar[j - 9] == 'u' && arrayOfChar[j - 8] == 'e' && arrayOfChar[j - 7] == 'r' && arrayOfChar[j - 6] == 'y' && arrayOfChar[j - 5] == 'M' && arrayOfChar[j - 4] == 'B' && arrayOfChar[j - 3] == 'e' && arrayOfChar[j - 2] == 'a' && arrayOfChar[j - 1] == 'n' && arrayOfChar[j] == 's') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  916 */         b = 11;
/*  917 */         i |= 0x800;
/*  918 */       } else if (j >= 9 && arrayOfChar[j - 9] == 'g' && arrayOfChar[j - 8] == 'e' && arrayOfChar[j - 7] == 't' && arrayOfChar[j - 6] == 'D' && arrayOfChar[j - 5] == 'o' && arrayOfChar[j - 4] == 'm' && arrayOfChar[j - 3] == 'a' && arrayOfChar[j - 2] == 'i' && arrayOfChar[j - 1] == 'n' && arrayOfChar[j] == 's') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  929 */         b = 10;
/*  930 */         i |= 0x20;
/*  931 */       } else if (j >= 9 && arrayOfChar[j - 9] == 'q' && arrayOfChar[j - 8] == 'u' && arrayOfChar[j - 7] == 'e' && arrayOfChar[j - 6] == 'r' && arrayOfChar[j - 5] == 'y' && arrayOfChar[j - 4] == 'N' && arrayOfChar[j - 3] == 'a' && arrayOfChar[j - 2] == 'm' && arrayOfChar[j - 1] == 'e' && arrayOfChar[j] == 's') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  942 */         b = 10;
/*  943 */         i |= 0x1000;
/*  944 */       } else if (j >= 5 && arrayOfChar[j - 5] == 'i' && arrayOfChar[j - 4] == 'n' && arrayOfChar[j - 3] == 'v' && arrayOfChar[j - 2] == 'o' && arrayOfChar[j - 1] == 'k' && arrayOfChar[j] == 'e') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  951 */         b = 6;
/*  952 */         i |= 0x200;
/*      */       } else {
/*      */         
/*  955 */         throw new IllegalArgumentException("Invalid permission: " + paramString);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  961 */       boolean bool = false;
/*  962 */       while (j >= b && !bool) {
/*  963 */         switch (arrayOfChar[j - b]) {
/*      */           case ',':
/*  965 */             bool = true; break;
/*      */           case '\t': case '\n': case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */             break;
/*      */           default:
/*  971 */             throw new IllegalArgumentException("Invalid permission: " + paramString);
/*      */         } 
/*      */         
/*  974 */         j--;
/*      */       } 
/*      */ 
/*      */       
/*  978 */       j -= b;
/*      */     } 
/*      */     
/*  981 */     return i;
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
/*      */   public boolean implies(Permission paramPermission) {
/* 1027 */     if (!(paramPermission instanceof MBeanPermission)) {
/* 1028 */       return false;
/*      */     }
/* 1030 */     MBeanPermission mBeanPermission = (MBeanPermission)paramPermission;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1039 */     if ((this.mask & 0x800) == 2048) {
/* 1040 */       if (((this.mask | 0x1000) & mBeanPermission.mask) != mBeanPermission.mask)
/*      */       {
/* 1042 */         return false;
/*      */       }
/*      */     }
/* 1045 */     else if ((this.mask & mBeanPermission.mask) != mBeanPermission.mask) {
/*      */       
/* 1047 */       return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1079 */     if (mBeanPermission.classNamePrefix != null) {
/*      */       
/* 1081 */       if (this.classNamePrefix == null)
/*      */       {
/* 1083 */         return false; } 
/* 1084 */       if (this.classNameExactMatch) {
/* 1085 */         if (!mBeanPermission.classNameExactMatch)
/* 1086 */           return false; 
/* 1087 */         if (!mBeanPermission.classNamePrefix.equals(this.classNamePrefix)) {
/* 1088 */           return false;
/*      */         
/*      */         }
/*      */       }
/* 1092 */       else if (!mBeanPermission.classNamePrefix.startsWith(this.classNamePrefix)) {
/* 1093 */         return false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1098 */     if (mBeanPermission.member != null) {
/*      */       
/* 1100 */       if (this.member == null)
/*      */       {
/* 1102 */         return false; } 
/* 1103 */       if (!this.member.equals("*"))
/*      */       {
/* 1105 */         if (!this.member.equals(mBeanPermission.member)) {
/* 1106 */           return false;
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 1111 */     if (mBeanPermission.objectName != null) {
/*      */       
/* 1113 */       if (this.objectName == null)
/*      */       {
/* 1115 */         return false; } 
/* 1116 */       if (!this.objectName.apply(mBeanPermission.objectName))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1121 */         if (!this.objectName.equals(mBeanPermission.objectName))
/* 1122 */           return false; 
/*      */       }
/*      */     } 
/* 1125 */     return true;
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
/*      */   public boolean equals(Object paramObject) {
/* 1138 */     if (paramObject == this) {
/* 1139 */       return true;
/*      */     }
/* 1141 */     if (!(paramObject instanceof MBeanPermission)) {
/* 1142 */       return false;
/*      */     }
/* 1144 */     MBeanPermission mBeanPermission = (MBeanPermission)paramObject;
/*      */     
/* 1146 */     return (this.mask == mBeanPermission.mask && 
/* 1147 */       getName().equals(mBeanPermission.getName()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1155 */     paramObjectInputStream.defaultReadObject();
/* 1156 */     parseName();
/* 1157 */     parseActions();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */