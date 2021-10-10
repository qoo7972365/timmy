/*     */ package com.sun.corba.se.impl.orbutil;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.CORBAObjectImpl;
/*     */ import com.sun.corba.se.impl.ior.iiop.JavaSerializationComponent;
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.pept.transport.ContactInfoList;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfile;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import com.sun.corba.se.spi.protocol.CorbaClientDelegate;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import java.rmi.RemoteException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.Policy;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import javax.rmi.CORBA.ValueHandler;
/*     */ import javax.rmi.CORBA.ValueHandlerMultiFormat;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.StructMember;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*     */ import org.omg.CORBA.TypeCodePackage.Bounds;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import sun.corba.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ORBUtility
/*     */ {
/*  93 */   private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("util");
/*     */   
/*  95 */   private static OMGSystemException omgWrapper = OMGSystemException.get("util");
/*     */ 
/*     */   
/*  98 */   private static StructMember[] members = null;
/*     */   
/*     */   private static StructMember[] systemExceptionMembers(ORB paramORB) {
/* 101 */     if (members == null) {
/* 102 */       members = new StructMember[3];
/* 103 */       members[0] = new StructMember("id", paramORB.create_string_tc(0), null);
/* 104 */       members[1] = new StructMember("minor", paramORB.get_primitive_tc(TCKind.tk_long), null);
/* 105 */       members[2] = new StructMember("completed", paramORB.get_primitive_tc(TCKind.tk_long), null);
/*     */     } 
/* 107 */     return members;
/*     */   }
/*     */   
/*     */   private static TypeCode getSystemExceptionTypeCode(ORB paramORB, String paramString1, String paramString2) {
/* 111 */     synchronized (TypeCode.class) {
/* 112 */       return paramORB.create_exception_tc(paramString1, paramString2, systemExceptionMembers(paramORB));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isSystemExceptionTypeCode(TypeCode paramTypeCode, ORB paramORB) {
/* 117 */     StructMember[] arrayOfStructMember = systemExceptionMembers(paramORB);
/*     */     try {
/* 119 */       return (paramTypeCode.kind().value() == 22 && paramTypeCode
/* 120 */         .member_count() == 3 && paramTypeCode
/* 121 */         .member_type(0).equal((arrayOfStructMember[0]).type) && paramTypeCode
/* 122 */         .member_type(1).equal((arrayOfStructMember[1]).type) && paramTypeCode
/* 123 */         .member_type(2).equal((arrayOfStructMember[2]).type));
/* 124 */     } catch (BadKind badKind) {
/* 125 */       return false;
/* 126 */     } catch (Bounds bounds) {
/* 127 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insertSystemException(SystemException paramSystemException, Any paramAny) {
/* 136 */     OutputStream outputStream = paramAny.create_output_stream();
/* 137 */     ORB oRB = (ORB)outputStream.orb();
/* 138 */     String str1 = paramSystemException.getClass().getName();
/* 139 */     String str2 = repositoryIdOf(str1);
/* 140 */     outputStream.write_string(str2);
/* 141 */     outputStream.write_long(paramSystemException.minor);
/* 142 */     outputStream.write_long(paramSystemException.completed.value());
/* 143 */     paramAny.read_value(outputStream.create_input_stream(), 
/* 144 */         getSystemExceptionTypeCode(oRB, str2, str1));
/*     */   }
/*     */   
/*     */   public static SystemException extractSystemException(Any paramAny) {
/* 148 */     InputStream inputStream = paramAny.create_input_stream();
/* 149 */     ORB oRB = (ORB)inputStream.orb();
/* 150 */     if (!isSystemExceptionTypeCode(paramAny.type(), oRB)) {
/* 151 */       throw wrapper.unknownDsiSysex(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/* 153 */     return readSystemException(inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ValueHandler createValueHandler() {
/*     */     ValueHandler valueHandler;
/*     */     try {
/* 162 */       valueHandler = AccessController.<ValueHandler>doPrivileged(new PrivilegedExceptionAction<ValueHandler>() {
/*     */             public ValueHandler run() throws Exception {
/* 164 */               return Util.createValueHandler();
/*     */             }
/*     */           });
/* 167 */     } catch (PrivilegedActionException privilegedActionException) {
/* 168 */       throw new InternalError(privilegedActionException.getMessage());
/*     */     } 
/* 170 */     return valueHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isForeignORB(ORB paramORB) {
/* 180 */     if (paramORB == null) {
/* 181 */       return false;
/*     */     }
/*     */     try {
/* 184 */       return paramORB.getORBVersion().equals(ORBVersionFactory.getFOREIGN());
/* 185 */     } catch (SecurityException securityException) {
/* 186 */       return false;
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
/*     */   public static int bytesToInt(byte[] paramArrayOfbyte, int paramInt) {
/* 201 */     int i = paramArrayOfbyte[paramInt++] << 24 & 0xFF000000;
/* 202 */     int j = paramArrayOfbyte[paramInt++] << 16 & 0xFF0000;
/* 203 */     int k = paramArrayOfbyte[paramInt++] << 8 & 0xFF00;
/* 204 */     int m = paramArrayOfbyte[paramInt++] << 0 & 0xFF;
/*     */     
/* 206 */     return i | j | k | m;
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
/*     */   public static void intToBytes(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 218 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 24 & 0xFF);
/* 219 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 16 & 0xFF);
/* 220 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 8 & 0xFF);
/* 221 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 0 & 0xFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hexOf(char paramChar) {
/* 230 */     int i = paramChar - 48;
/* 231 */     if (i >= 0 && i <= 9) {
/* 232 */       return i;
/*     */     }
/* 234 */     i = paramChar - 97 + 10;
/* 235 */     if (i >= 10 && i <= 15) {
/* 236 */       return i;
/*     */     }
/* 238 */     i = paramChar - 65 + 10;
/* 239 */     if (i >= 10 && i <= 15) {
/* 240 */       return i;
/*     */     }
/* 242 */     throw wrapper.badHexDigit();
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
/*     */   public static void writeSystemException(SystemException paramSystemException, OutputStream paramOutputStream) {
/* 255 */     String str = repositoryIdOf(paramSystemException.getClass().getName());
/* 256 */     paramOutputStream.write_string(str);
/* 257 */     paramOutputStream.write_long(paramSystemException.minor);
/* 258 */     paramOutputStream.write_long(paramSystemException.completed.value());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SystemException readSystemException(InputStream paramInputStream) {
/*     */     try {
/* 268 */       String str = classNameOf(paramInputStream.read_string());
/*     */       
/* 270 */       SystemException systemException = SharedSecrets.getJavaCorbaAccess().loadClass(str).newInstance();
/* 271 */       systemException.minor = paramInputStream.read_long();
/* 272 */       systemException.completed = CompletionStatus.from_int(paramInputStream.read_long());
/* 273 */       return systemException;
/* 274 */     } catch (Exception exception) {
/* 275 */       throw wrapper.unknownSysex(CompletionStatus.COMPLETED_MAYBE, exception);
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
/*     */   public static String classNameOf(String paramString) {
/* 288 */     String str = null;
/*     */     
/* 290 */     str = (String)exceptionClassNames.get(paramString);
/* 291 */     if (str == null) {
/* 292 */       str = "org.omg.CORBA.UNKNOWN";
/*     */     }
/* 294 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSystemException(String paramString) {
/* 303 */     String str = null;
/*     */     
/* 305 */     str = (String)exceptionClassNames.get(paramString);
/* 306 */     if (str == null) {
/* 307 */       return false;
/*     */     }
/* 309 */     return true;
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
/*     */   public static byte getEncodingVersion(ORB paramORB, IOR paramIOR) {
/* 324 */     if (paramORB.getORBData().isJavaSerializationEnabled()) {
/* 325 */       IIOPProfile iIOPProfile = paramIOR.getProfile();
/*     */       
/* 327 */       IIOPProfileTemplate iIOPProfileTemplate = (IIOPProfileTemplate)iIOPProfile.getTaggedProfileTemplate();
/* 328 */       Iterator<JavaSerializationComponent> iterator = iIOPProfileTemplate.iteratorById(1398099458);
/*     */       
/* 330 */       if (iterator.hasNext()) {
/*     */         
/* 332 */         JavaSerializationComponent javaSerializationComponent = iterator.next();
/* 333 */         byte b = javaSerializationComponent.javaSerializationVersion();
/* 334 */         if (b >= 1)
/* 335 */           return 1; 
/* 336 */         if (b > 0) {
/* 337 */           return javaSerializationComponent.javaSerializationVersion();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 344 */     return 0;
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
/*     */   public static String repositoryIdOf(String paramString) {
/* 357 */     String str = (String)exceptionRepositoryIds.get(paramString);
/* 358 */     if (str == null) {
/* 359 */       str = "IDL:omg.org/CORBA/UNKNOWN:1.0";
/*     */     }
/* 361 */     return str;
/*     */   }
/*     */   
/* 364 */   private static final Hashtable exceptionClassNames = new Hashtable<>();
/* 365 */   private static final Hashtable exceptionRepositoryIds = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 372 */     exceptionClassNames.put("IDL:omg.org/CORBA/BAD_CONTEXT:1.0", "org.omg.CORBA.BAD_CONTEXT");
/*     */     
/* 374 */     exceptionClassNames.put("IDL:omg.org/CORBA/BAD_INV_ORDER:1.0", "org.omg.CORBA.BAD_INV_ORDER");
/*     */     
/* 376 */     exceptionClassNames.put("IDL:omg.org/CORBA/BAD_OPERATION:1.0", "org.omg.CORBA.BAD_OPERATION");
/*     */     
/* 378 */     exceptionClassNames.put("IDL:omg.org/CORBA/BAD_PARAM:1.0", "org.omg.CORBA.BAD_PARAM");
/*     */     
/* 380 */     exceptionClassNames.put("IDL:omg.org/CORBA/BAD_TYPECODE:1.0", "org.omg.CORBA.BAD_TYPECODE");
/*     */     
/* 382 */     exceptionClassNames.put("IDL:omg.org/CORBA/COMM_FAILURE:1.0", "org.omg.CORBA.COMM_FAILURE");
/*     */     
/* 384 */     exceptionClassNames.put("IDL:omg.org/CORBA/DATA_CONVERSION:1.0", "org.omg.CORBA.DATA_CONVERSION");
/*     */     
/* 386 */     exceptionClassNames.put("IDL:omg.org/CORBA/IMP_LIMIT:1.0", "org.omg.CORBA.IMP_LIMIT");
/*     */     
/* 388 */     exceptionClassNames.put("IDL:omg.org/CORBA/INTF_REPOS:1.0", "org.omg.CORBA.INTF_REPOS");
/*     */     
/* 390 */     exceptionClassNames.put("IDL:omg.org/CORBA/INTERNAL:1.0", "org.omg.CORBA.INTERNAL");
/*     */     
/* 392 */     exceptionClassNames.put("IDL:omg.org/CORBA/INV_FLAG:1.0", "org.omg.CORBA.INV_FLAG");
/*     */     
/* 394 */     exceptionClassNames.put("IDL:omg.org/CORBA/INV_IDENT:1.0", "org.omg.CORBA.INV_IDENT");
/*     */     
/* 396 */     exceptionClassNames.put("IDL:omg.org/CORBA/INV_OBJREF:1.0", "org.omg.CORBA.INV_OBJREF");
/*     */     
/* 398 */     exceptionClassNames.put("IDL:omg.org/CORBA/MARSHAL:1.0", "org.omg.CORBA.MARSHAL");
/*     */     
/* 400 */     exceptionClassNames.put("IDL:omg.org/CORBA/NO_MEMORY:1.0", "org.omg.CORBA.NO_MEMORY");
/*     */     
/* 402 */     exceptionClassNames.put("IDL:omg.org/CORBA/FREE_MEM:1.0", "org.omg.CORBA.FREE_MEM");
/*     */     
/* 404 */     exceptionClassNames.put("IDL:omg.org/CORBA/NO_IMPLEMENT:1.0", "org.omg.CORBA.NO_IMPLEMENT");
/*     */     
/* 406 */     exceptionClassNames.put("IDL:omg.org/CORBA/NO_PERMISSION:1.0", "org.omg.CORBA.NO_PERMISSION");
/*     */     
/* 408 */     exceptionClassNames.put("IDL:omg.org/CORBA/NO_RESOURCES:1.0", "org.omg.CORBA.NO_RESOURCES");
/*     */     
/* 410 */     exceptionClassNames.put("IDL:omg.org/CORBA/NO_RESPONSE:1.0", "org.omg.CORBA.NO_RESPONSE");
/*     */     
/* 412 */     exceptionClassNames.put("IDL:omg.org/CORBA/OBJ_ADAPTER:1.0", "org.omg.CORBA.OBJ_ADAPTER");
/*     */     
/* 414 */     exceptionClassNames.put("IDL:omg.org/CORBA/INITIALIZE:1.0", "org.omg.CORBA.INITIALIZE");
/*     */     
/* 416 */     exceptionClassNames.put("IDL:omg.org/CORBA/PERSIST_STORE:1.0", "org.omg.CORBA.PERSIST_STORE");
/*     */     
/* 418 */     exceptionClassNames.put("IDL:omg.org/CORBA/TRANSIENT:1.0", "org.omg.CORBA.TRANSIENT");
/*     */     
/* 420 */     exceptionClassNames.put("IDL:omg.org/CORBA/UNKNOWN:1.0", "org.omg.CORBA.UNKNOWN");
/*     */     
/* 422 */     exceptionClassNames.put("IDL:omg.org/CORBA/OBJECT_NOT_EXIST:1.0", "org.omg.CORBA.OBJECT_NOT_EXIST");
/*     */ 
/*     */ 
/*     */     
/* 426 */     exceptionClassNames.put("IDL:omg.org/CORBA/INVALID_TRANSACTION:1.0", "org.omg.CORBA.INVALID_TRANSACTION");
/*     */     
/* 428 */     exceptionClassNames.put("IDL:omg.org/CORBA/TRANSACTION_REQUIRED:1.0", "org.omg.CORBA.TRANSACTION_REQUIRED");
/*     */     
/* 430 */     exceptionClassNames.put("IDL:omg.org/CORBA/TRANSACTION_ROLLEDBACK:1.0", "org.omg.CORBA.TRANSACTION_ROLLEDBACK");
/*     */ 
/*     */ 
/*     */     
/* 434 */     exceptionClassNames.put("IDL:omg.org/CORBA/INV_POLICY:1.0", "org.omg.CORBA.INV_POLICY");
/*     */ 
/*     */ 
/*     */     
/* 438 */     exceptionClassNames
/* 439 */       .put("IDL:omg.org/CORBA/TRANSACTION_UNAVAILABLE:1.0", "org.omg.CORBA.TRANSACTION_UNAVAILABLE");
/*     */     
/* 441 */     exceptionClassNames.put("IDL:omg.org/CORBA/TRANSACTION_MODE:1.0", "org.omg.CORBA.TRANSACTION_MODE");
/*     */ 
/*     */ 
/*     */     
/* 445 */     exceptionClassNames.put("IDL:omg.org/CORBA/CODESET_INCOMPATIBLE:1.0", "org.omg.CORBA.CODESET_INCOMPATIBLE");
/*     */     
/* 447 */     exceptionClassNames.put("IDL:omg.org/CORBA/REBIND:1.0", "org.omg.CORBA.REBIND");
/*     */     
/* 449 */     exceptionClassNames.put("IDL:omg.org/CORBA/TIMEOUT:1.0", "org.omg.CORBA.TIMEOUT");
/*     */     
/* 451 */     exceptionClassNames.put("IDL:omg.org/CORBA/BAD_QOS:1.0", "org.omg.CORBA.BAD_QOS");
/*     */ 
/*     */ 
/*     */     
/* 455 */     exceptionClassNames.put("IDL:omg.org/CORBA/INVALID_ACTIVITY:1.0", "org.omg.CORBA.INVALID_ACTIVITY");
/*     */     
/* 457 */     exceptionClassNames.put("IDL:omg.org/CORBA/ACTIVITY_COMPLETED:1.0", "org.omg.CORBA.ACTIVITY_COMPLETED");
/*     */     
/* 459 */     exceptionClassNames.put("IDL:omg.org/CORBA/ACTIVITY_REQUIRED:1.0", "org.omg.CORBA.ACTIVITY_REQUIRED");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 465 */     Enumeration<Object> enumeration = exceptionClassNames.keys();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 471 */       while (enumeration.hasMoreElements()) {
/* 472 */         String str1 = (String)enumeration.nextElement();
/* 473 */         String str2 = str1;
/* 474 */         String str3 = (String)exceptionClassNames.get(str2);
/* 475 */         exceptionRepositoryIds.put(str3, str2);
/*     */       } 
/* 477 */     } catch (NoSuchElementException noSuchElementException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] parseVersion(String paramString) {
/* 485 */     if (paramString == null)
/* 486 */       return new int[0]; 
/* 487 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     
/* 489 */     int i = 0;
/* 490 */     for (; i < arrayOfChar.length && (arrayOfChar[i] < '0' || arrayOfChar[i] > '9'); i++) {
/* 491 */       if (i == arrayOfChar.length)
/* 492 */         return new int[0]; 
/* 493 */     }  int j = i + 1;
/* 494 */     byte b1 = 1;
/* 495 */     for (; j < arrayOfChar.length; j++) {
/* 496 */       if (arrayOfChar[j] == '.')
/* 497 */       { b1++; }
/* 498 */       else if (arrayOfChar[j] < '0' || arrayOfChar[j] > '9') { break; }
/*     */     
/* 500 */     }  int[] arrayOfInt = new int[b1];
/* 501 */     for (byte b2 = 0; b2 < b1; b2++) {
/* 502 */       int k = paramString.indexOf('.', i);
/* 503 */       if (k == -1 || k > j)
/* 504 */         k = j; 
/* 505 */       if (i >= k) {
/* 506 */         arrayOfInt[b2] = 0;
/*     */       } else {
/* 508 */         arrayOfInt[b2] = Integer.parseInt(paramString.substring(i, k));
/* 509 */       }  i = k + 1;
/*     */     } 
/* 511 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int compareVersion(int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 518 */     if (paramArrayOfint1 == null)
/* 519 */       paramArrayOfint1 = new int[0]; 
/* 520 */     if (paramArrayOfint2 == null)
/* 521 */       paramArrayOfint2 = new int[0]; 
/* 522 */     for (byte b = 0; b < paramArrayOfint1.length; b++) {
/* 523 */       if (b >= paramArrayOfint2.length || paramArrayOfint1[b] > paramArrayOfint2[b])
/* 524 */         return 1; 
/* 525 */       if (paramArrayOfint1[b] < paramArrayOfint2[b])
/* 526 */         return -1; 
/*     */     } 
/* 528 */     return (paramArrayOfint1.length == paramArrayOfint2.length) ? 0 : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int compareVersion(String paramString1, String paramString2) {
/* 535 */     return compareVersion(parseVersion(paramString1), parseVersion(paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String compressClassName(String paramString) {
/* 541 */     String str = "com.sun.corba.se.";
/* 542 */     if (paramString.startsWith(str)) {
/* 543 */       return "(ORB)." + paramString.substring(str.length());
/*     */     }
/* 545 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getThreadName(Thread paramThread) {
/* 553 */     if (paramThread == null) {
/* 554 */       return "null";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 560 */     String str = paramThread.getName();
/* 561 */     StringTokenizer stringTokenizer = new StringTokenizer(str);
/* 562 */     int i = stringTokenizer.countTokens();
/* 563 */     if (i != 5) {
/* 564 */       return str;
/*     */     }
/* 566 */     String[] arrayOfString = new String[i];
/* 567 */     for (byte b = 0; b < i; b++) {
/* 568 */       arrayOfString[b] = stringTokenizer.nextToken();
/*     */     }
/* 570 */     if (!arrayOfString[0].equals("SelectReaderThread")) {
/* 571 */       return str;
/*     */     }
/* 573 */     return "SelectReaderThread[" + arrayOfString[2] + ":" + arrayOfString[3] + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   private static String formatStackTraceElement(StackTraceElement paramStackTraceElement) {
/* 578 */     return compressClassName(paramStackTraceElement.getClassName()) + "." + paramStackTraceElement.getMethodName() + (
/* 579 */       paramStackTraceElement.isNativeMethod() ? "(Native Method)" : ((paramStackTraceElement
/* 580 */       .getFileName() != null && paramStackTraceElement.getLineNumber() >= 0) ? ("(" + paramStackTraceElement
/* 581 */       .getFileName() + ":" + paramStackTraceElement.getLineNumber() + ")") : (
/* 582 */       (paramStackTraceElement.getFileName() != null) ? ("(" + paramStackTraceElement.getFileName() + ")") : "(Unknown Source)")));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void printStackTrace(StackTraceElement[] paramArrayOfStackTraceElement) {
/* 587 */     System.out.println("    Stack Trace:");
/*     */ 
/*     */     
/* 590 */     for (byte b = 1; b < paramArrayOfStackTraceElement.length; b++) {
/* 591 */       System.out.print("        >");
/* 592 */       System.out.println(formatStackTraceElement(paramArrayOfStackTraceElement[b]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void dprint(Object paramObject, String paramString) {
/* 600 */     System.out.println(
/* 601 */         compressClassName(paramObject.getClass().getName()) + "(" + 
/* 602 */         getThreadName(Thread.currentThread()) + "): " + paramString);
/*     */   }
/*     */   
/*     */   public static synchronized void dprint(String paramString1, String paramString2) {
/* 606 */     System.out.println(
/* 607 */         compressClassName(paramString1) + "(" + 
/* 608 */         getThreadName(Thread.currentThread()) + "): " + paramString2);
/*     */   }
/*     */   
/*     */   public synchronized void dprint(String paramString) {
/* 612 */     dprint(this, paramString);
/*     */   }
/*     */   
/*     */   public static synchronized void dprintTrace(Object paramObject, String paramString) {
/* 616 */     dprint(paramObject, paramString);
/*     */     
/* 618 */     Throwable throwable = new Throwable();
/* 619 */     printStackTrace(throwable.getStackTrace());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void dprint(Object paramObject, String paramString, Throwable paramThrowable) {
/* 625 */     System.out.println(
/* 626 */         compressClassName(paramObject.getClass().getName()) + '(' + 
/* 627 */         Thread.currentThread() + "): " + paramString);
/*     */     
/* 629 */     if (paramThrowable != null) {
/* 630 */       printStackTrace(paramThrowable.getStackTrace());
/*     */     }
/*     */   }
/*     */   
/*     */   public static String[] concatenateStringArrays(String[] paramArrayOfString1, String[] paramArrayOfString2) {
/* 635 */     String[] arrayOfString = new String[paramArrayOfString1.length + paramArrayOfString2.length];
/*     */     
/*     */     byte b;
/* 638 */     for (b = 0; b < paramArrayOfString1.length; b++) {
/* 639 */       arrayOfString[b] = paramArrayOfString1[b];
/*     */     }
/* 641 */     for (b = 0; b < paramArrayOfString2.length; b++) {
/* 642 */       arrayOfString[b + paramArrayOfString1.length] = paramArrayOfString2[b];
/*     */     }
/* 644 */     return arrayOfString;
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
/*     */   public static void throwNotSerializableForCorba(String paramString) {
/* 660 */     throw omgWrapper.notSerializable(CompletionStatus.COMPLETED_MAYBE, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte getMaxStreamFormatVersion() {
/*     */     ValueHandler valueHandler;
/*     */     try {
/* 671 */       valueHandler = AccessController.<ValueHandler>doPrivileged(new PrivilegedExceptionAction<ValueHandler>() {
/*     */             public ValueHandler run() throws Exception {
/* 673 */               return Util.createValueHandler();
/*     */             }
/*     */           });
/* 676 */     } catch (PrivilegedActionException privilegedActionException) {
/* 677 */       throw new InternalError(privilegedActionException.getMessage());
/*     */     } 
/*     */     
/* 680 */     if (!(valueHandler instanceof ValueHandlerMultiFormat)) {
/* 681 */       return 1;
/*     */     }
/* 683 */     return ((ValueHandlerMultiFormat)valueHandler).getMaximumStreamFormatVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public static CorbaClientDelegate makeClientDelegate(IOR paramIOR) {
/* 688 */     ORB oRB = paramIOR.getORB();
/* 689 */     CorbaContactInfoList corbaContactInfoList = oRB.getCorbaContactInfoListFactory().create(paramIOR);
/* 690 */     return oRB.getClientDelegateFactory().create(corbaContactInfoList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object makeObjectReference(IOR paramIOR) {
/* 698 */     CorbaClientDelegate corbaClientDelegate = makeClientDelegate(paramIOR);
/* 699 */     CORBAObjectImpl cORBAObjectImpl = new CORBAObjectImpl();
/* 700 */     StubAdapter.setDelegate(cORBAObjectImpl, (Delegate)corbaClientDelegate);
/* 701 */     return (Object)cORBAObjectImpl;
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
/*     */   public static IOR getIOR(Object paramObject) {
/* 717 */     if (paramObject == null) {
/* 718 */       throw wrapper.nullObjectReference();
/*     */     }
/* 720 */     IOR iOR = null;
/* 721 */     if (StubAdapter.isStub(paramObject)) {
/* 722 */       Delegate delegate = StubAdapter.getDelegate(paramObject);
/*     */ 
/*     */       
/* 725 */       if (delegate instanceof CorbaClientDelegate) {
/* 726 */         CorbaClientDelegate corbaClientDelegate = (CorbaClientDelegate)delegate;
/* 727 */         ContactInfoList contactInfoList = corbaClientDelegate.getContactInfoList();
/*     */         
/* 729 */         if (contactInfoList instanceof CorbaContactInfoList) {
/* 730 */           CorbaContactInfoList corbaContactInfoList = (CorbaContactInfoList)contactInfoList;
/* 731 */           iOR = corbaContactInfoList.getTargetIOR();
/* 732 */           if (iOR == null) {
/* 733 */             throw wrapper.nullIor();
/*     */           }
/* 735 */           return iOR;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 743 */         throw new INTERNAL();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 754 */       throw wrapper.objrefFromForeignOrb();
/*     */     } 
/* 756 */     throw wrapper.localObjectNotAllowed();
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
/*     */   public static IOR connectAndGetIOR(ORB paramORB, Object paramObject) {
/*     */     IOR iOR;
/*     */     try {
/* 772 */       iOR = getIOR(paramObject);
/* 773 */     } catch (BAD_OPERATION bAD_OPERATION) {
/* 774 */       if (StubAdapter.isStub(paramObject)) {
/*     */         try {
/* 776 */           StubAdapter.connect(paramObject, (ORB)paramORB);
/* 777 */         } catch (RemoteException remoteException) {
/* 778 */           throw wrapper.connectingServant(remoteException);
/*     */         } 
/*     */       } else {
/* 781 */         paramORB.connect(paramObject);
/*     */       } 
/*     */       
/* 784 */       iOR = getIOR(paramObject);
/*     */     } 
/*     */     
/* 787 */     return iOR;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String operationNameAndRequestId(CorbaMessageMediator paramCorbaMessageMediator) {
/* 792 */     return "op/" + paramCorbaMessageMediator.getOperationName() + " id/" + paramCorbaMessageMediator.getRequestId();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPrintable(char paramChar) {
/* 797 */     if (Character.isJavaIdentifierStart(paramChar))
/*     */     {
/* 799 */       return true;
/*     */     }
/* 801 */     if (Character.isDigit(paramChar)) {
/* 802 */       return true;
/*     */     }
/* 804 */     switch (Character.getType(paramChar)) { case 27:
/* 805 */         return true;
/* 806 */       case 20: return true;
/* 807 */       case 25: return true;
/* 808 */       case 24: return true;
/* 809 */       case 21: return true;
/* 810 */       case 22: return true; }
/*     */     
/* 812 */     return false;
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
/*     */   public static String getClassSecurityInfo(final Class cl) {
/* 828 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/* 830 */             StringBuffer stringBuffer = new StringBuffer(500);
/* 831 */             ProtectionDomain protectionDomain = cl.getProtectionDomain();
/* 832 */             Policy policy = Policy.getPolicy();
/* 833 */             PermissionCollection permissionCollection = policy.getPermissions(protectionDomain);
/* 834 */             stringBuffer.append("\nPermissionCollection ");
/* 835 */             stringBuffer.append(permissionCollection.toString());
/*     */ 
/*     */             
/* 838 */             stringBuffer.append(protectionDomain.toString());
/* 839 */             return stringBuffer.toString();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/ORBUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */