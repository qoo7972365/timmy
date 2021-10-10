/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ObjectUtility;
/*     */ import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IDLNameTranslatorImpl
/*     */   implements IDLNameTranslator
/*     */ {
/*  59 */   private static String[] IDL_KEYWORDS = new String[] { "abstract", "any", "attribute", "boolean", "case", "char", "const", "context", "custom", "default", "double", "enum", "exception", "factory", "FALSE", "fixed", "float", "in", "inout", "interface", "long", "module", "native", "Object", "octet", "oneway", "out", "private", "public", "raises", "readonly", "sequence", "short", "string", "struct", "supports", "switch", "TRUE", "truncatable", "typedef", "unsigned", "union", "ValueBase", "valuetype", "void", "wchar", "wstring" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static char[] HEX_DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String UNDERSCORE = "_";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String INNER_CLASS_SEPARATOR = "__";
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static final String[] BASE_IDL_ARRAY_MODULE_TYPE = new String[] { "org", "omg", "boxedRMI" };
/*     */ 
/*     */   
/*     */   private static final String BASE_IDL_ARRAY_ELEMENT_TYPE = "seq";
/*     */ 
/*     */   
/*     */   private static final String LEADING_UNDERSCORE_CHAR = "J";
/*     */ 
/*     */   
/*     */   private static final String ID_CONTAINER_CLASH_CHAR = "_";
/*     */ 
/*     */   
/*     */   private static final String OVERLOADED_TYPE_SEPARATOR = "__";
/*     */ 
/*     */   
/*     */   private static final String ATTRIBUTE_METHOD_CLASH_MANGLE_CHARS = "__";
/*     */ 
/*     */   
/*     */   private static final String GET_ATTRIBUTE_PREFIX = "_get_";
/*     */ 
/*     */   
/*     */   private static final String SET_ATTRIBUTE_PREFIX = "_set_";
/*     */ 
/*     */   
/*     */   private static final String IS_ATTRIBUTE_PREFIX = "_get_";
/*     */ 
/*     */   
/* 111 */   private static Set idlKeywords_ = new HashSet(); static {
/* 112 */     for (byte b = 0; b < IDL_KEYWORDS.length; b++) {
/* 113 */       String str1 = IDL_KEYWORDS[b];
/*     */ 
/*     */       
/* 116 */       String str2 = str1.toUpperCase();
/* 117 */       idlKeywords_.add(str2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class[] interf_;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map methodToIDLNameMap_;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map IDLNameToMethodMap_;
/*     */ 
/*     */ 
/*     */   
/*     */   private Method[] methods_;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IDLNameTranslator get(Class paramClass) {
/* 145 */     return new IDLNameTranslatorImpl(new Class[] { paramClass });
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
/*     */   public static IDLNameTranslator get(Class[] paramArrayOfClass) {
/* 158 */     return new IDLNameTranslatorImpl(paramArrayOfClass);
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
/*     */   public static String getExceptionId(Class paramClass) {
/* 172 */     IDLType iDLType = classToIDLType(paramClass);
/* 173 */     return iDLType.getExceptionName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class[] getInterfaces() {
/* 178 */     return this.interf_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Method[] getMethods() {
/* 183 */     return this.methods_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Method getMethod(String paramString) {
/* 188 */     return (Method)this.IDLNameToMethodMap_.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIDLName(Method paramMethod) {
/* 193 */     return (String)this.methodToIDLNameMap_.get(paramMethod);
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
/*     */   private IDLNameTranslatorImpl(Class[] paramArrayOfClass) {
/* 205 */     SecurityManager securityManager = System.getSecurityManager();
/* 206 */     if (securityManager != null) {
/* 207 */       securityManager.checkPermission(new DynamicAccessPermission("access"));
/*     */     }
/*     */     try {
/* 210 */       IDLTypesUtil iDLTypesUtil = new IDLTypesUtil();
/* 211 */       for (byte b = 0; b < paramArrayOfClass.length; b++)
/* 212 */         iDLTypesUtil.validateRemoteInterface(paramArrayOfClass[b]); 
/* 213 */       this.interf_ = paramArrayOfClass;
/* 214 */       buildNameTranslation();
/* 215 */     } catch (IDLTypeException iDLTypeException) {
/* 216 */       String str = iDLTypeException.getMessage();
/* 217 */       IllegalStateException illegalStateException = new IllegalStateException(str);
/* 218 */       illegalStateException.initCause(iDLTypeException);
/* 219 */       throw illegalStateException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildNameTranslation() {
/* 226 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/* 228 */     for (byte b2 = 0; b2 < this.interf_.length; b2++) {
/* 229 */       Class clazz = this.interf_[b2];
/*     */       
/* 231 */       IDLTypesUtil iDLTypesUtil = new IDLTypesUtil();
/* 232 */       final Method[] methods = clazz.getMethods();
/*     */       
/* 234 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Object run() {
/* 236 */               Method.setAccessible((AccessibleObject[])methods, true);
/* 237 */               return null;
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 244 */       for (byte b = 0; b < arrayOfMethod.length; b++) {
/*     */         
/* 246 */         Method method = arrayOfMethod[b];
/*     */         
/* 248 */         IDLMethodInfo iDLMethodInfo = new IDLMethodInfo();
/*     */         
/* 250 */         iDLMethodInfo.method = method;
/*     */         
/* 252 */         if (iDLTypesUtil.isPropertyAccessorMethod(method, clazz)) {
/* 253 */           iDLMethodInfo.isProperty = true;
/*     */           
/* 255 */           String str = iDLTypesUtil.getAttributeNameForProperty(method.getName());
/* 256 */           iDLMethodInfo.originalName = str;
/* 257 */           iDLMethodInfo.mangledName = str;
/*     */         } else {
/* 259 */           iDLMethodInfo.isProperty = false;
/* 260 */           iDLMethodInfo.originalName = method.getName();
/* 261 */           iDLMethodInfo.mangledName = method.getName();
/*     */         } 
/*     */         
/* 264 */         hashMap.put(method, iDLMethodInfo);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     Iterator<IDLMethodInfo> iterator2 = hashMap.values().iterator();
/* 276 */     while (iterator2.hasNext()) {
/* 277 */       IDLMethodInfo iDLMethodInfo = iterator2.next();
/* 278 */       Iterator<IDLMethodInfo> iterator = hashMap.values().iterator();
/* 279 */       while (iterator.hasNext()) {
/* 280 */         IDLMethodInfo iDLMethodInfo1 = iterator.next();
/*     */         
/* 282 */         if (iDLMethodInfo != iDLMethodInfo1 && 
/* 283 */           !iDLMethodInfo.originalName.equals(iDLMethodInfo1.originalName) && iDLMethodInfo.originalName
/* 284 */           .equalsIgnoreCase(iDLMethodInfo1.originalName)) {
/* 285 */           iDLMethodInfo
/* 286 */             .mangledName = mangleCaseSensitiveCollision(iDLMethodInfo.originalName);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 293 */     iterator2 = hashMap.values().iterator();
/* 294 */     while (iterator2.hasNext()) {
/* 295 */       IDLMethodInfo iDLMethodInfo = iterator2.next();
/* 296 */       iDLMethodInfo
/* 297 */         .mangledName = mangleIdentifier(iDLMethodInfo.mangledName, iDLMethodInfo.isProperty);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     iterator2 = hashMap.values().iterator();
/* 304 */     while (iterator2.hasNext()) {
/* 305 */       IDLMethodInfo iDLMethodInfo = iterator2.next();
/* 306 */       if (iDLMethodInfo.isProperty) {
/*     */         continue;
/*     */       }
/* 309 */       Iterator<IDLMethodInfo> iterator = hashMap.values().iterator();
/* 310 */       while (iterator.hasNext()) {
/* 311 */         IDLMethodInfo iDLMethodInfo1 = iterator.next();
/*     */         
/* 313 */         if (iDLMethodInfo != iDLMethodInfo1 && !iDLMethodInfo1.isProperty && iDLMethodInfo.originalName
/*     */           
/* 315 */           .equals(iDLMethodInfo1.originalName)) {
/* 316 */           iDLMethodInfo
/* 317 */             .mangledName = mangleOverloadedMethod(iDLMethodInfo.mangledName, iDLMethodInfo.method);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     iterator2 = hashMap.values().iterator();
/* 327 */     while (iterator2.hasNext()) {
/* 328 */       IDLMethodInfo iDLMethodInfo = iterator2.next();
/* 329 */       if (!iDLMethodInfo.isProperty) {
/*     */         continue;
/*     */       }
/* 332 */       Iterator<IDLMethodInfo> iterator = hashMap.values().iterator();
/* 333 */       while (iterator.hasNext()) {
/* 334 */         IDLMethodInfo iDLMethodInfo1 = iterator.next();
/* 335 */         if (iDLMethodInfo != iDLMethodInfo1 && !iDLMethodInfo1.isProperty && iDLMethodInfo.mangledName
/*     */           
/* 337 */           .equals(iDLMethodInfo1.mangledName)) {
/* 338 */           iDLMethodInfo.mangledName += "__";
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     for (byte b1 = 0; b1 < this.interf_.length; b1++) {
/* 350 */       Class clazz = this.interf_[b1];
/* 351 */       String str = getMappedContainerName(clazz);
/* 352 */       Iterator<IDLMethodInfo> iterator = hashMap.values().iterator();
/* 353 */       while (iterator.hasNext()) {
/* 354 */         IDLMethodInfo iDLMethodInfo = iterator.next();
/* 355 */         if (!iDLMethodInfo.isProperty && 
/* 356 */           identifierClashesWithContainer(str, iDLMethodInfo.mangledName))
/*     */         {
/* 358 */           iDLMethodInfo.mangledName = mangleContainerClash(iDLMethodInfo.mangledName);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     this.methodToIDLNameMap_ = new HashMap<>();
/* 367 */     this.IDLNameToMethodMap_ = new HashMap<>();
/* 368 */     this.methods_ = (Method[])hashMap.keySet().toArray((Object[])new Method[0]);
/*     */ 
/*     */     
/* 371 */     Iterator<IDLMethodInfo> iterator1 = hashMap.values().iterator();
/* 372 */     while (iterator1.hasNext()) {
/* 373 */       IDLMethodInfo iDLMethodInfo = iterator1.next();
/* 374 */       String str = iDLMethodInfo.mangledName;
/* 375 */       if (iDLMethodInfo.isProperty) {
/* 376 */         String str1 = iDLMethodInfo.method.getName();
/* 377 */         String str2 = "";
/*     */         
/* 379 */         if (str1.startsWith("get")) {
/* 380 */           str2 = "_get_";
/* 381 */         } else if (str1.startsWith("set")) {
/* 382 */           str2 = "_set_";
/*     */         } else {
/* 384 */           str2 = "_get_";
/*     */         } 
/*     */         
/* 387 */         str = str2 + iDLMethodInfo.mangledName;
/*     */       } 
/*     */       
/* 390 */       this.methodToIDLNameMap_.put(iDLMethodInfo.method, str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 397 */       if (this.IDLNameToMethodMap_.containsKey(str)) {
/*     */         
/* 399 */         Method method = (Method)this.IDLNameToMethodMap_.get(str);
/* 400 */         throw new IllegalStateException("Error : methods " + method + " and " + iDLMethodInfo.method + " both result in IDL name '" + str + "'");
/*     */       } 
/*     */ 
/*     */       
/* 404 */       this.IDLNameToMethodMap_.put(str, iDLMethodInfo.method);
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
/*     */   private static String mangleIdentifier(String paramString) {
/* 423 */     return mangleIdentifier(paramString, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String mangleIdentifier(String paramString, boolean paramBoolean) {
/* 428 */     String str = paramString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 435 */     if (hasLeadingUnderscore(str)) {
/* 436 */       str = mangleLeadingUnderscore(str);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 446 */     if (!paramBoolean && isIDLKeyword(str)) {
/* 447 */       str = mangleIDLKeywordClash(str);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     if (!isIDLIdentifier(str)) {
/* 455 */       str = mangleUnicodeChars(str);
/*     */     }
/*     */     
/* 458 */     return str;
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
/*     */   static boolean isIDLKeyword(String paramString) {
/* 483 */     String str = paramString.toUpperCase();
/*     */     
/* 485 */     return idlKeywords_.contains(str);
/*     */   }
/*     */   
/*     */   static String mangleIDLKeywordClash(String paramString) {
/* 489 */     return "_" + paramString;
/*     */   }
/*     */   
/*     */   private static String mangleLeadingUnderscore(String paramString) {
/* 493 */     return "J" + paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasLeadingUnderscore(String paramString) {
/* 501 */     return paramString.startsWith("_");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String mangleUnicodeChars(String paramString) {
/* 510 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 512 */     for (byte b = 0; b < paramString.length(); b++) {
/* 513 */       char c = paramString.charAt(b);
/* 514 */       if (isIDLIdentifierChar(c)) {
/* 515 */         stringBuffer.append(c);
/*     */       } else {
/* 517 */         String str = charToUnicodeRepresentation(c);
/* 518 */         stringBuffer.append(str);
/*     */       } 
/*     */     } 
/*     */     
/* 522 */     return stringBuffer.toString();
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
/*     */   String mangleCaseSensitiveCollision(String paramString) {
/* 540 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/*     */ 
/*     */ 
/*     */     
/* 544 */     stringBuffer.append("_");
/*     */     
/* 546 */     boolean bool = false;
/* 547 */     for (byte b = 0; b < paramString.length(); b++) {
/* 548 */       char c = paramString.charAt(b);
/* 549 */       if (Character.isUpperCase(c)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 555 */         if (bool) {
/* 556 */           stringBuffer.append("_");
/*     */         }
/* 558 */         stringBuffer.append(b);
/* 559 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 563 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private static String mangleContainerClash(String paramString) {
/* 567 */     return paramString + "_";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean identifierClashesWithContainer(String paramString1, String paramString2) {
/* 578 */     return paramString2.equalsIgnoreCase(paramString1);
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
/*     */   public static String charToUnicodeRepresentation(char paramChar) {
/* 594 */     char c = paramChar;
/* 595 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 597 */     int i = c;
/*     */     
/* 599 */     while (i > 0) {
/* 600 */       int k = i / 16;
/* 601 */       int m = i % 16;
/* 602 */       stringBuffer.insert(0, HEX_DIGITS[m]);
/* 603 */       i = k;
/*     */     } 
/*     */     
/* 606 */     int j = 4 - stringBuffer.length();
/* 607 */     for (byte b = 0; b < j; b++) {
/* 608 */       stringBuffer.insert(0, "0");
/*     */     }
/*     */     
/* 611 */     stringBuffer.insert(0, "U");
/* 612 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isIDLIdentifier(String paramString) {
/* 617 */     boolean bool = true;
/*     */     
/* 619 */     for (byte b = 0; b < paramString.length(); b++) {
/* 620 */       char c = paramString.charAt(b);
/*     */ 
/*     */ 
/*     */       
/* 624 */       bool = (b == 0) ? isIDLAlphabeticChar(c) : isIDLIdentifierChar(c);
/* 625 */       if (!bool) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 630 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isIDLIdentifierChar(char paramChar) {
/* 635 */     return (isIDLAlphabeticChar(paramChar) || 
/* 636 */       isIDLDecimalDigit(paramChar) || 
/* 637 */       isUnderscore(paramChar));
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
/*     */   private static boolean isIDLAlphabeticChar(char paramChar) {
/* 652 */     return ((paramChar >= 'A' && paramChar <= 'Z') || (paramChar >= 'a' && paramChar <= 'z') || (paramChar >= 'À' && paramChar <= 'ÿ' && paramChar != '×' && paramChar != '÷'));
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
/*     */   private static boolean isIDLDecimalDigit(char paramChar) {
/* 677 */     return (paramChar >= '0' && paramChar <= '9');
/*     */   }
/*     */   
/*     */   private static boolean isUnderscore(char paramChar) {
/* 681 */     return (paramChar == '_');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String mangleOverloadedMethod(String paramString, Method paramMethod) {
/* 692 */     IDLTypesUtil iDLTypesUtil = new IDLTypesUtil();
/*     */ 
/*     */     
/* 695 */     String str = paramString + "__";
/*     */     
/* 697 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/*     */     
/* 699 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 700 */       Class clazz = arrayOfClass[b];
/*     */       
/* 702 */       if (b > 0) {
/* 703 */         str = str + "__";
/*     */       }
/* 705 */       IDLType iDLType = classToIDLType(clazz);
/*     */       
/* 707 */       String str1 = iDLType.getModuleName();
/* 708 */       String str2 = iDLType.getMemberName();
/*     */       
/* 710 */       String str3 = (str1.length() > 0) ? (str1 + "_" + str2) : str2;
/*     */ 
/*     */       
/* 713 */       if (!iDLTypesUtil.isPrimitive(clazz) && iDLTypesUtil
/* 714 */         .getSpecialCaseIDLTypeMapping(clazz) == null && 
/*     */         
/* 716 */         isIDLKeyword(str3)) {
/* 717 */         str3 = mangleIDLKeywordClash(str3);
/*     */       }
/*     */       
/* 720 */       str3 = mangleUnicodeChars(str3);
/*     */       
/* 722 */       str = str + str3;
/*     */     } 
/*     */     
/* 725 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IDLType classToIDLType(Class paramClass) {
/* 731 */     IDLType iDLType = null;
/* 732 */     IDLTypesUtil iDLTypesUtil = new IDLTypesUtil();
/*     */     
/* 734 */     if (iDLTypesUtil.isPrimitive(paramClass)) {
/*     */       
/* 736 */       iDLType = iDLTypesUtil.getPrimitiveIDLTypeMapping(paramClass);
/*     */     }
/* 738 */     else if (paramClass.isArray()) {
/*     */ 
/*     */       
/* 741 */       Class<?> clazz = paramClass.getComponentType();
/* 742 */       byte b = 1;
/* 743 */       while (clazz.isArray()) {
/* 744 */         clazz = clazz.getComponentType();
/* 745 */         b++;
/*     */       } 
/* 747 */       IDLType iDLType1 = classToIDLType(clazz);
/*     */       
/* 749 */       String[] arrayOfString = BASE_IDL_ARRAY_MODULE_TYPE;
/* 750 */       if (iDLType1.hasModule()) {
/* 751 */         arrayOfString = (String[])ObjectUtility.concatenateArrays(arrayOfString, iDLType1
/* 752 */             .getModules());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 757 */       String str = "seq" + b + "_" + iDLType1.getMemberName();
/*     */       
/* 759 */       iDLType = new IDLType(paramClass, arrayOfString, str);
/*     */     } else {
/*     */       
/* 762 */       iDLType = iDLTypesUtil.getSpecialCaseIDLTypeMapping(paramClass);
/*     */       
/* 764 */       if (iDLType == null) {
/*     */ 
/*     */         
/* 767 */         String str1 = getUnmappedContainerName(paramClass);
/*     */ 
/*     */         
/* 770 */         str1 = str1.replaceAll("\\$", "__");
/*     */ 
/*     */         
/* 773 */         if (hasLeadingUnderscore(str1)) {
/* 774 */           str1 = mangleLeadingUnderscore(str1);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 780 */         String str2 = getPackageName(paramClass);
/*     */         
/* 782 */         if (str2 == null) {
/* 783 */           iDLType = new IDLType(paramClass, str1);
/*     */         }
/*     */         else {
/*     */           
/* 787 */           if (iDLTypesUtil.isEntity(paramClass)) {
/* 788 */             str2 = "org.omg.boxedIDL." + str2;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 798 */           StringTokenizer stringTokenizer = new StringTokenizer(str2, ".");
/*     */ 
/*     */           
/* 801 */           String[] arrayOfString = new String[stringTokenizer.countTokens()];
/* 802 */           byte b = 0;
/* 803 */           while (stringTokenizer.hasMoreElements()) {
/* 804 */             String str3 = stringTokenizer.nextToken();
/*     */             
/* 806 */             String str4 = hasLeadingUnderscore(str3) ? mangleLeadingUnderscore(str3) : str3;
/*     */             
/* 808 */             arrayOfString[b++] = str4;
/*     */           } 
/*     */           
/* 811 */           iDLType = new IDLType(paramClass, arrayOfString, str1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 816 */     return iDLType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getPackageName(Class paramClass) {
/* 823 */     Package package_ = paramClass.getPackage();
/* 824 */     String str = null;
/*     */ 
/*     */ 
/*     */     
/* 828 */     if (package_ != null) {
/* 829 */       str = package_.getName();
/*     */     } else {
/*     */       
/* 832 */       String str1 = paramClass.getName();
/* 833 */       int i = str1.indexOf('.');
/*     */       
/* 835 */       str = (i == -1) ? null : str1.substring(0, i);
/*     */     } 
/* 837 */     return str;
/*     */   }
/*     */   
/*     */   private static String getMappedContainerName(Class paramClass) {
/* 841 */     String str = getUnmappedContainerName(paramClass);
/*     */     
/* 843 */     return mangleIdentifier(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getUnmappedContainerName(Class paramClass) {
/* 851 */     String str1 = null;
/* 852 */     String str2 = getPackageName(paramClass);
/*     */     
/* 854 */     String str3 = paramClass.getName();
/*     */     
/* 856 */     if (str2 != null) {
/* 857 */       int i = str2.length();
/* 858 */       str1 = str3.substring(i + 1);
/*     */     } else {
/* 860 */       str1 = str3;
/*     */     } 
/*     */ 
/*     */     
/* 864 */     return str1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class IDLMethodInfo
/*     */   {
/*     */     public Method method;
/*     */ 
/*     */     
/*     */     public boolean isProperty;
/*     */ 
/*     */     
/*     */     public String originalName;
/*     */ 
/*     */     
/*     */     public String mangledName;
/*     */ 
/*     */     
/*     */     private IDLMethodInfo() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 888 */     StringBuffer stringBuffer = new StringBuffer();
/* 889 */     stringBuffer.append("IDLNameTranslator[");
/* 890 */     for (byte b = 0; b < this.interf_.length; b++) {
/* 891 */       if (b != 0)
/* 892 */         stringBuffer.append(" "); 
/* 893 */       stringBuffer.append(this.interf_[b].getName());
/*     */     } 
/* 895 */     stringBuffer.append("]\n");
/* 896 */     Iterator<Method> iterator = this.methodToIDLNameMap_.keySet().iterator();
/* 897 */     while (iterator.hasNext()) {
/*     */       
/* 899 */       Method method = iterator.next();
/* 900 */       String str = (String)this.methodToIDLNameMap_.get(method);
/*     */       
/* 902 */       stringBuffer.append(str + ":" + method + "\n");
/*     */     } 
/*     */ 
/*     */     
/* 906 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/IDLNameTranslatorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */