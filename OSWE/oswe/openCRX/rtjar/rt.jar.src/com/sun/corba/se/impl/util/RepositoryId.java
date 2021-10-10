/*     */ package com.sun.corba.se.impl.util;
/*     */ 
/*     */ import com.sun.corba.se.impl.io.ObjectStreamClass;
/*     */ import com.sun.corba.se.impl.io.TypeMismatchException;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.rmi.Remote;
/*     */ import java.util.Hashtable;
/*     */ import javax.rmi.CORBA.ClassDesc;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.IDLEntity;
/*     */ import org.omg.CORBA.portable.ValueBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RepositoryId
/*     */ {
/*  60 */   private static final byte[] IDL_IDENTIFIER_CHARS = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 123456789L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static String defaultServerURL = null; private static boolean useCodebaseOnly = false; private static IdentityHashtable classToRepStr; private static IdentityHashtable classIDLToRepStr; private static IdentityHashtable classSeqToRepStr; private static final IdentityHashtable repStrToByteArray;
/*     */   private static Hashtable repStrToClass;
/*     */   
/*     */   static {
/*  88 */     if (defaultServerURL == null)
/*  89 */       defaultServerURL = JDKBridge.getLocalCodebase(); 
/*  90 */     useCodebaseOnly = JDKBridge.useCodebaseOnly();
/*     */ 
/*     */ 
/*     */     
/*  94 */     classToRepStr = new IdentityHashtable();
/*  95 */     classIDLToRepStr = new IdentityHashtable();
/*  96 */     classSeqToRepStr = new IdentityHashtable();
/*     */     
/*  98 */     repStrToByteArray = new IdentityHashtable();
/*  99 */     repStrToClass = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     kValuePrefixLength = "RMI:".length();
/* 125 */     kIDLPrefixLength = "IDL:".length();
/* 126 */     kSequencePrefixLength = "[".length();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     kPreComputed_StandardRMIUnchunked = computeValueTag(false, 2, false);
/* 138 */     kPreComputed_CodeBaseRMIUnchunked = computeValueTag(true, 2, false);
/* 139 */     kPreComputed_StandardRMIChunked = computeValueTag(false, 2, true);
/* 140 */     kPreComputed_CodeBaseRMIChunked = computeValueTag(true, 2, true);
/*     */     
/* 142 */     kPreComputed_StandardRMIUnchunked_NoRep = computeValueTag(false, 0, false);
/* 143 */     kPreComputed_CodeBaseRMIUnchunked_NoRep = computeValueTag(true, 0, false);
/* 144 */     kPreComputed_StandardRMIChunked_NoRep = computeValueTag(false, 0, true);
/* 145 */     kPreComputed_CodeBaseRMIChunked_NoRep = computeValueTag(true, 0, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     kClassDescValueHash = ":" + Long.toHexString(ObjectStreamClass.getActualSerialVersionUID(ClassDesc.class)).toUpperCase() + ":" + Long.toHexString(ObjectStreamClass.getSerialVersionUID(ClassDesc.class)).toUpperCase();
/*     */ 
/*     */     
/* 171 */     kClassDescValueRepID = "RMI:javax.rmi.CORBA.ClassDesc" + kClassDescValueHash;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     kSpecialArrayTypeStrings = new Hashtable<>();
/*     */ 
/*     */     
/* 198 */     kSpecialArrayTypeStrings.put("CORBA.WStringValue", new StringBuffer(String.class.getName()));
/* 199 */     kSpecialArrayTypeStrings.put("javax.rmi.CORBA.ClassDesc", new StringBuffer(Class.class.getName()));
/* 200 */     kSpecialArrayTypeStrings.put("CORBA.Object", new StringBuffer(Remote.class.getName()));
/*     */ 
/*     */ 
/*     */     
/* 204 */     kSpecialCasesRepIDs = new Hashtable<>();
/*     */ 
/*     */     
/* 207 */     kSpecialCasesRepIDs.put(String.class, "IDL:omg.org/CORBA/WStringValue:1.0");
/* 208 */     kSpecialCasesRepIDs.put(Class.class, kClassDescValueRepID);
/* 209 */     kSpecialCasesRepIDs.put(Remote.class, "");
/*     */ 
/*     */     
/* 212 */     kSpecialCasesStubValues = new Hashtable<>();
/*     */ 
/*     */     
/* 215 */     kSpecialCasesStubValues.put(String.class, "WStringValue");
/* 216 */     kSpecialCasesStubValues.put(Class.class, "ClassDesc");
/* 217 */     kSpecialCasesStubValues.put(Object.class, "Object");
/* 218 */     kSpecialCasesStubValues.put(Serializable.class, "Serializable");
/* 219 */     kSpecialCasesStubValues.put(Externalizable.class, "Externalizable");
/* 220 */     kSpecialCasesStubValues.put(Remote.class, "");
/*     */ 
/*     */ 
/*     */     
/* 224 */     kSpecialCasesVersions = new Hashtable<>();
/*     */ 
/*     */     
/* 227 */     kSpecialCasesVersions.put(String.class, ":1.0");
/* 228 */     kSpecialCasesVersions.put(Class.class, kClassDescValueHash);
/* 229 */     kSpecialCasesVersions.put(Object.class, ":1.0");
/* 230 */     kSpecialCasesVersions.put(Serializable.class, ":1.0");
/* 231 */     kSpecialCasesVersions.put(Externalizable.class, ":1.0");
/* 232 */     kSpecialCasesVersions.put(Remote.class, "");
/*     */ 
/*     */     
/* 235 */     kSpecialCasesClasses = new Hashtable<>();
/*     */ 
/*     */     
/* 238 */     kSpecialCasesClasses.put("omg.org/CORBA/WStringValue", String.class);
/* 239 */     kSpecialCasesClasses.put("javax.rmi.CORBA.ClassDesc", Class.class);
/* 240 */     kSpecialCasesClasses.put("", Remote.class);
/*     */     
/* 242 */     kSpecialCasesClasses.put("org.omg.CORBA.WStringValue", String.class);
/* 243 */     kSpecialCasesClasses.put("javax.rmi.CORBA.ClassDesc", Class.class);
/*     */ 
/*     */ 
/*     */     
/* 247 */     kSpecialCasesArrayPrefix = new Hashtable<>();
/*     */ 
/*     */     
/* 250 */     kSpecialCasesArrayPrefix.put(String.class, "RMI:[CORBA/");
/* 251 */     kSpecialCasesArrayPrefix.put(Class.class, "RMI:[javax/rmi/CORBA/");
/* 252 */     kSpecialCasesArrayPrefix.put(Object.class, "RMI:[java/lang/");
/* 253 */     kSpecialCasesArrayPrefix.put(Serializable.class, "RMI:[java/io/");
/* 254 */     kSpecialCasesArrayPrefix.put(Externalizable.class, "RMI:[java/io/");
/* 255 */     kSpecialCasesArrayPrefix.put(Remote.class, "RMI:[CORBA/");
/*     */ 
/*     */     
/* 258 */     kSpecialPrimitives = new Hashtable<>();
/*     */ 
/*     */     
/* 261 */     kSpecialPrimitives.put("int", "long");
/* 262 */     kSpecialPrimitives.put("long", "longlong");
/* 263 */     kSpecialPrimitives.put("byte", "octet");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     ASCII_HEX = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     cache = new RepositoryIdCache();
/*     */ 
/*     */     
/* 294 */     kjava_rmi_Remote = createForAnyType(Remote.class);
/* 295 */     korg_omg_CORBA_Object = createForAnyType(Object.class);
/*     */ 
/*     */     
/* 298 */     kNoParamTypes = new Class[0];
/* 299 */     kNoArgs = new Object[0];
/*     */   }
/*     */   private String repId = null; private boolean isSupportedFormat = true; private String typeString = null; private String versionString = null; private boolean isSequence = false; private boolean isRMIValueType = false; private boolean isIDLType = false; private String completeClassName = null; private String unqualifiedName = null; private String definedInId = null; private Class clazz = null; private String suid = null; private String actualSuid = null; private long suidLong = -1L; private long actualSuidLong = -1L; private static final String kSequenceKeyword = "seq"; private static final String kValuePrefix = "RMI:"; private static final String kIDLPrefix = "IDL:"; private static final String kIDLNamePrefix = "omg.org/"; private static final String kIDLClassnamePrefix = "org.omg."; private static final String kSequencePrefix = "["; private static final String kCORBAPrefix = "CORBA/"; private static final String kArrayPrefix = "RMI:[CORBA/"; private static final int kValuePrefixLength; private static final int kIDLPrefixLength; private static final int kSequencePrefixLength; private static final String kInterfaceHashCode = ":0000000000000000"; private static final String kInterfaceOnlyHashStr = "0000000000000000"; private static final String kExternalizableHashStr = "0000000000000001"; public static final int kInitialValueTag = 2147483392; public static final int kNoTypeInfo = 0; public static final int kSingleRepTypeInfo = 2; public static final int kPartialListTypeInfo = 6; public static final int kChunkedMask = 8;
/*     */   public static final int kPreComputed_StandardRMIUnchunked;
/*     */   public static final int kPreComputed_CodeBaseRMIUnchunked;
/*     */   public static final int kPreComputed_StandardRMIChunked;
/*     */   public static final int kPreComputed_CodeBaseRMIChunked;
/*     */   
/*     */   RepositoryId(String paramString) {
/* 308 */     init(paramString);
/*     */   }
/*     */   public static final int kPreComputed_StandardRMIUnchunked_NoRep; public static final int kPreComputed_CodeBaseRMIUnchunked_NoRep; public static final int kPreComputed_StandardRMIChunked_NoRep; public static final int kPreComputed_CodeBaseRMIChunked_NoRep; public static final String kWStringValueVersion = "1.0"; public static final String kWStringValueHash = ":1.0"; public static final String kWStringStubValue = "WStringValue"; public static final String kWStringTypeStr = "omg.org/CORBA/WStringValue"; public static final String kWStringValueRepID = "IDL:omg.org/CORBA/WStringValue:1.0"; public static final String kAnyRepID = "IDL:omg.org/CORBA/Any"; public static final String kClassDescValueHash; public static final String kClassDescStubValue = "ClassDesc"; public static final String kClassDescTypeStr = "javax.rmi.CORBA.ClassDesc"; public static final String kClassDescValueRepID; public static final String kObjectValueHash = ":1.0"; public static final String kObjectStubValue = "Object"; public static final String kSequenceValueHash = ":1.0"; public static final String kPrimitiveSequenceValueHash = ":0000000000000000"; public static final String kSerializableValueHash = ":1.0"; public static final String kSerializableStubValue = "Serializable"; public static final String kExternalizableValueHash = ":1.0"; public static final String kExternalizableStubValue = "Externalizable"; public static final String kRemoteValueHash = ""; public static final String kRemoteStubValue = ""; public static final String kRemoteTypeStr = ""; public static final String kRemoteValueRepID = ""; private static final Hashtable kSpecialArrayTypeStrings; private static final Hashtable kSpecialCasesRepIDs; private static final Hashtable kSpecialCasesStubValues; private static final Hashtable kSpecialCasesVersions; private static final Hashtable kSpecialCasesClasses; private static final Hashtable kSpecialCasesArrayPrefix; private static final Hashtable kSpecialPrimitives; private static final byte[] ASCII_HEX; public static final RepositoryIdCache cache; public static final String kjava_rmi_Remote; public static final String korg_omg_CORBA_Object; public static final Class[] kNoParamTypes; public static final Object[] kNoArgs;
/*     */   
/*     */   RepositoryId init(String paramString) {
/* 313 */     this.repId = paramString;
/*     */ 
/*     */     
/* 316 */     if (paramString.length() == 0) {
/* 317 */       this.clazz = Remote.class;
/* 318 */       this.typeString = "";
/* 319 */       this.isRMIValueType = true;
/* 320 */       this.suid = "0000000000000000";
/* 321 */       return this;
/* 322 */     }  if (paramString.equals("IDL:omg.org/CORBA/WStringValue:1.0")) {
/* 323 */       this.clazz = String.class;
/* 324 */       this.typeString = "omg.org/CORBA/WStringValue";
/* 325 */       this.isIDLType = true;
/*     */ 
/*     */       
/* 328 */       this.completeClassName = "java.lang.String";
/* 329 */       this.versionString = "1.0";
/* 330 */       return this;
/*     */     } 
/* 332 */     String str = convertFromISOLatin1(paramString);
/*     */     
/* 334 */     int i = str.indexOf(':');
/* 335 */     if (i == -1)
/* 336 */       throw new IllegalArgumentException("RepsitoryId must have the form <type>:<body>"); 
/* 337 */     int j = str.indexOf(':', i + 1);
/*     */     
/* 339 */     if (j == -1) {
/* 340 */       this.versionString = "";
/*     */     } else {
/* 342 */       this.versionString = str.substring(j);
/*     */     } 
/* 344 */     if (str.startsWith("IDL:")) {
/* 345 */       this
/* 346 */         .typeString = str.substring(kIDLPrefixLength, str.indexOf(':', kIDLPrefixLength));
/* 347 */       this.isIDLType = true;
/*     */       
/* 349 */       if (this.typeString.startsWith("omg.org/")) {
/* 350 */         this
/* 351 */           .completeClassName = "org.omg." + this.typeString.substring("omg.org/".length()).replace('/', '.');
/*     */       } else {
/* 353 */         this.completeClassName = this.typeString.replace('/', '.');
/*     */       } 
/* 355 */     } else if (str.startsWith("RMI:")) {
/* 356 */       this
/* 357 */         .typeString = str.substring(kValuePrefixLength, str.indexOf(':', kValuePrefixLength));
/* 358 */       this.isRMIValueType = true;
/*     */       
/* 360 */       if (this.versionString.indexOf('.') == -1) {
/* 361 */         this.actualSuid = this.versionString.substring(1);
/* 362 */         this.suid = this.actualSuid;
/*     */         
/* 364 */         if (this.actualSuid.indexOf(':') != -1)
/*     */         {
/* 366 */           int k = this.actualSuid.indexOf(':') + 1;
/*     */ 
/*     */           
/* 369 */           this.suid = this.actualSuid.substring(k);
/* 370 */           this.actualSuid = this.actualSuid.substring(0, k - 1);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 376 */       this.isSupportedFormat = false;
/* 377 */       this.typeString = "";
/*     */     } 
/*     */     
/* 380 */     if (this.typeString.startsWith("[")) {
/* 381 */       this.isSequence = true;
/*     */     }
/*     */     
/* 384 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getUnqualifiedName() {
/* 389 */     if (this.unqualifiedName == null) {
/* 390 */       String str = getClassName();
/* 391 */       int i = str.lastIndexOf('.');
/* 392 */       if (i == -1) {
/* 393 */         this.unqualifiedName = str;
/* 394 */         this.definedInId = "IDL::1.0";
/*     */       } else {
/*     */         
/* 397 */         this.unqualifiedName = str.substring(i);
/* 398 */         this.definedInId = "IDL:" + str.substring(0, i).replace('.', '/') + ":1.0";
/*     */       } 
/*     */     } 
/*     */     
/* 402 */     return this.unqualifiedName;
/*     */   }
/*     */   
/*     */   public final String getDefinedInId() {
/* 406 */     if (this.definedInId == null) {
/* 407 */       getUnqualifiedName();
/*     */     }
/*     */     
/* 410 */     return this.definedInId;
/*     */   }
/*     */   
/*     */   public final String getTypeString() {
/* 414 */     return this.typeString;
/*     */   }
/*     */   
/*     */   public final String getVersionString() {
/* 418 */     return this.versionString;
/*     */   }
/*     */   
/*     */   public final String getSerialVersionUID() {
/* 422 */     return this.suid;
/*     */   }
/*     */   
/*     */   public final String getActualSerialVersionUID() {
/* 426 */     return this.actualSuid;
/*     */   }
/*     */   public final long getSerialVersionUIDAsLong() {
/* 429 */     return this.suidLong;
/*     */   }
/*     */   
/*     */   public final long getActualSerialVersionUIDAsLong() {
/* 433 */     return this.actualSuidLong;
/*     */   }
/*     */   
/*     */   public final boolean isRMIValueType() {
/* 437 */     return this.isRMIValueType;
/*     */   }
/*     */   
/*     */   public final boolean isIDLType() {
/* 441 */     return this.isIDLType;
/*     */   }
/*     */   
/*     */   public final String getRepositoryId() {
/* 445 */     return this.repId;
/*     */   }
/*     */   
/*     */   public static byte[] getByteArray(String paramString) {
/* 449 */     synchronized (repStrToByteArray) {
/* 450 */       return (byte[])repStrToByteArray.get(paramString);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setByteArray(String paramString, byte[] paramArrayOfbyte) {
/* 455 */     synchronized (repStrToByteArray) {
/* 456 */       repStrToByteArray.put(paramString, paramArrayOfbyte);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final boolean isSequence() {
/* 461 */     return this.isSequence;
/*     */   }
/*     */   
/*     */   public final boolean isSupportedFormat() {
/* 465 */     return this.isSupportedFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getClassName() {
/* 473 */     if (this.isRMIValueType)
/* 474 */       return this.typeString; 
/* 475 */     if (this.isIDLType)
/* 476 */       return this.completeClassName; 
/* 477 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class getAnyClassFromType() throws ClassNotFoundException {
/*     */     try {
/* 486 */       return getClassFromType();
/* 487 */     } catch (ClassNotFoundException classNotFoundException) {
/* 488 */       Class clazz = (Class)repStrToClass.get(this.repId);
/* 489 */       if (clazz != null) {
/* 490 */         return clazz;
/*     */       }
/* 492 */       throw classNotFoundException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Class getClassFromType() throws ClassNotFoundException {
/* 498 */     if (this.clazz != null) {
/* 499 */       return this.clazz;
/*     */     }
/* 501 */     Class clazz = (Class)kSpecialCasesClasses.get(getClassName());
/*     */     
/* 503 */     if (clazz != null) {
/* 504 */       this.clazz = clazz;
/* 505 */       return clazz;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 510 */       return Util.loadClass(getClassName(), null, null);
/*     */     }
/* 512 */     catch (ClassNotFoundException classNotFoundException) {
/* 513 */       if (defaultServerURL != null) {
/*     */         try {
/* 515 */           return getClassFromType(defaultServerURL);
/*     */         }
/* 517 */         catch (MalformedURLException malformedURLException) {
/* 518 */           throw classNotFoundException;
/*     */         } 
/*     */       }
/* 521 */       throw classNotFoundException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class getClassFromType(Class paramClass, String paramString) throws ClassNotFoundException {
/* 529 */     if (this.clazz != null) {
/* 530 */       return this.clazz;
/*     */     }
/* 532 */     Class clazz = (Class)kSpecialCasesClasses.get(getClassName());
/*     */     
/* 534 */     if (clazz != null) {
/* 535 */       this.clazz = clazz;
/* 536 */       return clazz;
/*     */     } 
/* 538 */     ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader();
/* 539 */     return Utility.loadClassOfType(getClassName(), paramString, classLoader, paramClass, classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class getClassFromType(String paramString) throws ClassNotFoundException, MalformedURLException {
/* 550 */     return Util.loadClass(getClassName(), paramString, null);
/*     */   }
/*     */   
/*     */   public final String toString() {
/* 554 */     return this.repId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean useFullValueDescription(Class paramClass, String paramString) throws IOException {
/*     */     RepositoryId repositoryId1, repositoryId2;
/* 565 */     String str = createForAnyType(paramClass);
/*     */     
/* 567 */     if (str.equals(paramString)) {
/* 568 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 573 */     synchronized (cache) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 578 */       repositoryId1 = cache.getId(paramString);
/* 579 */       repositoryId2 = cache.getId(str);
/*     */     } 
/*     */ 
/*     */     
/* 583 */     if (repositoryId1.isRMIValueType() && repositoryId2.isRMIValueType()) {
/* 584 */       if (!repositoryId1.getSerialVersionUID().equals(repositoryId2.getSerialVersionUID())) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 589 */         String str1 = "Mismatched serialization UIDs : Source (Rep. ID" + repositoryId2 + ") = " + repositoryId2.getSerialVersionUID() + " whereas Target (Rep. ID " + paramString + ") = " + repositoryId1.getSerialVersionUID();
/*     */         
/* 591 */         throw new IOException(str1);
/*     */       } 
/*     */       
/* 594 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 599 */     throw new IOException("The repository ID is not of an RMI value type (Expected ID = " + str + "; Received ID = " + paramString + ")");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String createHashString(Serializable paramSerializable) {
/* 605 */     return createHashString(paramSerializable.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String createHashString(Class<?> paramClass) {
/* 610 */     if (paramClass.isInterface() || !Serializable.class.isAssignableFrom(paramClass)) {
/* 611 */       return ":0000000000000000";
/*     */     }
/*     */ 
/*     */     
/* 615 */     long l1 = ObjectStreamClass.getActualSerialVersionUID(paramClass);
/* 616 */     String str1 = null;
/* 617 */     if (l1 == 0L) {
/* 618 */       str1 = "0000000000000000";
/* 619 */     } else if (l1 == 1L) {
/* 620 */       str1 = "0000000000000001";
/*     */     } else {
/* 622 */       str1 = Long.toHexString(l1).toUpperCase();
/* 623 */     }  while (str1.length() < 16) {
/* 624 */       str1 = "0" + str1;
/*     */     }
/*     */     
/* 627 */     long l2 = ObjectStreamClass.getSerialVersionUID(paramClass);
/* 628 */     String str2 = null;
/* 629 */     if (l2 == 0L) {
/* 630 */       str2 = "0000000000000000";
/* 631 */     } else if (l2 == 1L) {
/* 632 */       str2 = "0000000000000001";
/*     */     } else {
/* 634 */       str2 = Long.toHexString(l2).toUpperCase();
/* 635 */     }  while (str2.length() < 16) {
/* 636 */       str2 = "0" + str2;
/*     */     }
/* 638 */     str1 = str1 + ":" + str2;
/*     */     
/* 640 */     return ":" + str1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String createSequenceRepID(Object paramObject) {
/* 651 */     return createSequenceRepID(paramObject.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String createSequenceRepID(Class<?> paramClass) {
/* 661 */     synchronized (classSeqToRepStr) {
/*     */       
/* 663 */       String str = (String)classSeqToRepStr.get(paramClass);
/* 664 */       if (str != null) {
/* 665 */         return str;
/*     */       }
/* 667 */       Class<?> clazz1 = paramClass;
/*     */       
/* 669 */       Class<?> clazz2 = null;
/* 670 */       byte b = 0;
/*     */       
/* 672 */       while ((clazz2 = paramClass.getComponentType()) != null) {
/* 673 */         b++;
/* 674 */         paramClass = clazz2;
/*     */       } 
/*     */       
/* 677 */       if (paramClass.isPrimitive()) {
/* 678 */         str = "RMI:" + clazz1.getName() + ":0000000000000000";
/*     */       } else {
/* 680 */         StringBuffer stringBuffer = new StringBuffer();
/* 681 */         stringBuffer.append("RMI:");
/* 682 */         while (b-- > 0) {
/* 683 */           stringBuffer.append("[");
/*     */         }
/* 685 */         stringBuffer.append("L");
/* 686 */         stringBuffer.append(convertToISOLatin1(paramClass.getName()));
/* 687 */         stringBuffer.append(";");
/* 688 */         stringBuffer.append(createHashString(paramClass));
/* 689 */         str = stringBuffer.toString();
/*     */       } 
/* 691 */       classSeqToRepStr.put(clazz1, str);
/* 692 */       return str;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String createForSpecialCase(Class paramClass) {
/* 699 */     if (paramClass.isArray()) {
/* 700 */       return createSequenceRepID(paramClass);
/*     */     }
/*     */     
/* 703 */     return (String)kSpecialCasesRepIDs.get(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String createForSpecialCase(Serializable paramSerializable) {
/* 708 */     Class<?> clazz = paramSerializable.getClass();
/* 709 */     if (clazz.isArray()) {
/* 710 */       return createSequenceRepID(paramSerializable);
/*     */     }
/*     */     
/* 713 */     return createForSpecialCase(clazz);
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
/*     */   public static String createForJavaType(Serializable paramSerializable) throws TypeMismatchException {
/* 725 */     synchronized (classToRepStr) {
/* 726 */       String str = createForSpecialCase(paramSerializable);
/* 727 */       if (str != null)
/* 728 */         return str; 
/* 729 */       Class<?> clazz = paramSerializable.getClass();
/* 730 */       str = (String)classToRepStr.get(clazz);
/*     */       
/* 732 */       if (str != null) {
/* 733 */         return str;
/*     */       }
/*     */       
/* 736 */       str = "RMI:" + convertToISOLatin1(clazz.getName()) + createHashString(clazz);
/*     */       
/* 738 */       classToRepStr.put(clazz, str);
/* 739 */       repStrToClass.put(str, clazz);
/* 740 */       return str;
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
/*     */   public static String createForJavaType(Class<?> paramClass) throws TypeMismatchException {
/* 753 */     synchronized (classToRepStr) {
/* 754 */       String str = createForSpecialCase(paramClass);
/* 755 */       if (str != null) {
/* 756 */         return str;
/*     */       }
/* 758 */       str = (String)classToRepStr.get(paramClass);
/* 759 */       if (str != null) {
/* 760 */         return str;
/*     */       }
/*     */       
/* 763 */       str = "RMI:" + convertToISOLatin1(paramClass.getName()) + createHashString(paramClass);
/*     */       
/* 765 */       classToRepStr.put(paramClass, str);
/* 766 */       repStrToClass.put(str, paramClass);
/* 767 */       return str;
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
/*     */   public static String createForIDLType(Class paramClass, int paramInt1, int paramInt2) throws TypeMismatchException {
/* 782 */     synchronized (classIDLToRepStr) {
/* 783 */       String str = (String)classIDLToRepStr.get(paramClass);
/* 784 */       if (str != null) {
/* 785 */         return str;
/*     */       }
/* 787 */       str = "IDL:" + convertToISOLatin1(paramClass.getName()).replace('.', '/') + ":" + paramInt1 + "." + paramInt2;
/*     */       
/* 789 */       classIDLToRepStr.put(paramClass, str);
/* 790 */       return str;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getIdFromHelper(Class paramClass) {
/*     */     try {
/* 796 */       Class clazz = Utility.loadClassForClass(paramClass.getName() + "Helper", null, paramClass
/* 797 */           .getClassLoader(), paramClass, paramClass.getClassLoader());
/* 798 */       Method method = clazz.getDeclaredMethod("id", kNoParamTypes);
/* 799 */       return (String)method.invoke(null, kNoArgs);
/*     */     }
/* 801 */     catch (ClassNotFoundException classNotFoundException) {
/*     */       
/* 803 */       throw new MARSHAL(classNotFoundException.toString());
/*     */     }
/* 805 */     catch (NoSuchMethodException noSuchMethodException) {
/*     */       
/* 807 */       throw new MARSHAL(noSuchMethodException.toString());
/*     */     }
/* 809 */     catch (InvocationTargetException invocationTargetException) {
/*     */       
/* 811 */       throw new MARSHAL(invocationTargetException.toString());
/*     */     }
/* 813 */     catch (IllegalAccessException illegalAccessException) {
/*     */       
/* 815 */       throw new MARSHAL(illegalAccessException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String createForAnyType(Class<?> paramClass) {
/*     */     try {
/* 827 */       if (paramClass.isArray())
/* 828 */         return createSequenceRepID(paramClass); 
/* 829 */       if (IDLEntity.class.isAssignableFrom(paramClass)) {
/*     */         
/*     */         try {
/* 832 */           return getIdFromHelper(paramClass);
/*     */         }
/* 834 */         catch (Throwable throwable) {
/* 835 */           return createForIDLType(paramClass, 1, 0);
/*     */         } 
/*     */       }
/* 838 */       return createForJavaType(paramClass);
/*     */     }
/* 840 */     catch (TypeMismatchException typeMismatchException) {
/* 841 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isAbstractBase(Class<?> paramClass) {
/* 847 */     return (paramClass.isInterface() && IDLEntity.class
/* 848 */       .isAssignableFrom(paramClass) && 
/* 849 */       !ValueBase.class.isAssignableFrom(paramClass) && 
/* 850 */       !Object.class.isAssignableFrom(paramClass));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isAnyRequired(Class<Object> paramClass) {
/* 855 */     return (paramClass == Object.class || paramClass == Serializable.class || paramClass == Externalizable.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static long fromHex(String paramString) {
/* 861 */     if (paramString.startsWith("0x"))
/* 862 */       return Long.valueOf(paramString.substring(2), 16).longValue(); 
/* 863 */     return Long.valueOf(paramString, 16).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertToISOLatin1(String paramString) {
/* 873 */     int i = paramString.length();
/* 874 */     if (i == 0) {
/* 875 */       return paramString;
/*     */     }
/* 877 */     StringBuffer stringBuffer = null;
/*     */     
/* 879 */     for (byte b = 0; b < i; b++) {
/*     */       
/* 881 */       char c = paramString.charAt(b);
/*     */       
/* 883 */       if (c > 'ÿ' || IDL_IDENTIFIER_CHARS[c] == 0) {
/*     */ 
/*     */ 
/*     */         
/* 887 */         if (stringBuffer == null)
/*     */         {
/*     */ 
/*     */           
/* 891 */           stringBuffer = new StringBuffer(paramString.substring(0, b));
/*     */         }
/*     */ 
/*     */         
/* 895 */         stringBuffer.append("\\U" + (char)ASCII_HEX[(c & 0xF000) >>> 12] + (char)ASCII_HEX[(c & 0xF00) >>> 8] + (char)ASCII_HEX[(c & 0xF0) >>> 4] + (char)ASCII_HEX[c & 0xF]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 903 */       else if (stringBuffer != null) {
/* 904 */         stringBuffer.append(c);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 909 */     if (stringBuffer != null) {
/* 910 */       paramString = stringBuffer.toString();
/*     */     }
/*     */     
/* 913 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String convertFromISOLatin1(String paramString) {
/* 923 */     int i = -1;
/* 924 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/*     */     
/* 926 */     while ((i = stringBuffer.toString().indexOf("\\U")) != -1) {
/* 927 */       String str = "0000" + stringBuffer.toString().substring(i + 2, i + 6);
/*     */ 
/*     */       
/* 930 */       byte[] arrayOfByte = new byte[(str.length() - 4) / 2];
/* 931 */       for (byte b1 = 4, b2 = 0; b1 < str.length(); b1 += 2, b2++) {
/* 932 */         arrayOfByte[b2] = (byte)(Utility.hexOf(str.charAt(b1)) << 4 & 0xF0);
/* 933 */         arrayOfByte[b2] = (byte)(arrayOfByte[b2] | (byte)(Utility.hexOf(str.charAt(b1 + 1)) << 0 & 0xF));
/*     */       } 
/* 935 */       stringBuffer = new StringBuffer(delete(stringBuffer.toString(), i, i + 6));
/* 936 */       stringBuffer.insert(i, (char)arrayOfByte[1]);
/*     */     } 
/*     */     
/* 939 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String delete(String paramString, int paramInt1, int paramInt2) {
/* 946 */     return paramString.substring(0, paramInt1) + paramString.substring(paramInt2, paramString.length());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String replace(String paramString1, String paramString2, String paramString3) {
/* 951 */     int i = 0;
/* 952 */     i = paramString1.indexOf(paramString2);
/*     */     
/* 954 */     while (i != -1) {
/*     */       
/* 956 */       String str1 = paramString1.substring(0, i);
/* 957 */       String str2 = paramString1.substring(i + paramString2.length());
/* 958 */       paramString1 = new String(str1 + paramString3 + str2);
/* 959 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 961 */     return paramString1;
/*     */   }
/*     */   
/*     */   public static int computeValueTag(boolean paramBoolean1, int paramInt, boolean paramBoolean2) {
/* 965 */     int i = 2147483392;
/*     */     
/* 967 */     if (paramBoolean1) {
/* 968 */       i |= 0x1;
/*     */     }
/* 970 */     i |= paramInt;
/*     */     
/* 972 */     if (paramBoolean2) {
/* 973 */       i |= 0x8;
/*     */     }
/* 975 */     return i;
/*     */   }
/*     */   
/*     */   public static boolean isCodeBasePresent(int paramInt) {
/* 979 */     return ((paramInt & 0x1) == 1);
/*     */   }
/*     */   
/*     */   public static int getTypeInfo(int paramInt) {
/* 983 */     return paramInt & 0x6;
/*     */   }
/*     */   
/*     */   public static boolean isChunkedEncoding(int paramInt) {
/* 987 */     return ((paramInt & 0x8) != 0);
/*     */   }
/*     */   
/*     */   public static String getServerURL() {
/* 991 */     return defaultServerURL;
/*     */   }
/*     */   
/*     */   RepositoryId() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/RepositoryId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */