/*     */ package com.sun.corba.se.impl.io;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.UtilSystemException;
/*     */ import com.sun.corba.se.impl.util.RepositoryId;
/*     */ import com.sun.corba.se.impl.util.Utility;
/*     */ import com.sun.org.omg.SendingContext.CodeBase;
/*     */ import com.sun.org.omg.SendingContext.CodeBaseHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.rmi.Remote;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Hashtable;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import javax.rmi.CORBA.ValueHandler;
/*     */ import javax.rmi.CORBA.ValueHandlerMultiFormat;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.portable.IndirectionException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import org.omg.SendingContext.RunTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ValueHandlerImpl
/*     */   implements ValueHandlerMultiFormat
/*     */ {
/*     */   public static final String FORMAT_VERSION_PROPERTY = "com.sun.CORBA.MaxStreamFormatVersion";
/*     */   private static final byte MAX_SUPPORTED_FORMAT_VERSION = 2;
/*     */   private static final byte STREAM_FORMAT_VERSION_1 = 1;
/*  70 */   private static final byte MAX_STREAM_FORMAT_VERSION = getMaxStreamFormatVersion();
/*     */   
/*     */   public static final short kRemoteType = 0;
/*     */   
/*     */   public static final short kAbstractType = 1;
/*     */   
/*     */   public static final short kValueType = 2;
/*     */ 
/*     */   
/*     */   private static byte getMaxStreamFormatVersion() {
/*     */     try {
/*  81 */       String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */           {
/*     */             public Object run() {
/*  84 */               return System.getProperty("com.sun.CORBA.MaxStreamFormatVersion");
/*     */             }
/*     */           });
/*     */ 
/*     */       
/*  89 */       if (str == null) {
/*  90 */         return 2;
/*     */       }
/*  92 */       byte b = Byte.parseByte(str);
/*     */ 
/*     */ 
/*     */       
/*  96 */       if (b < 1 || b > 2)
/*     */       {
/*  98 */         throw new ExceptionInInitializerError("Invalid stream format version: " + b + ".  Valid range is 1 through " + '\002');
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 103 */       return b;
/*     */     }
/* 105 */     catch (Exception exception) {
/*     */ 
/*     */ 
/*     */       
/* 109 */       ExceptionInInitializerError exceptionInInitializerError = new ExceptionInInitializerError(exception);
/* 110 */       exceptionInInitializerError.initCause(exception);
/* 111 */       throw exceptionInInitializerError;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   private Hashtable inputStreamPairs = null;
/* 120 */   private Hashtable outputStreamPairs = null;
/* 121 */   private CodeBase codeBase = null;
/*     */   private boolean useHashtables = true;
/*     */   private boolean isInputStream = true;
/* 124 */   private IIOPOutputStream outputStreamBridge = null;
/* 125 */   private IIOPInputStream inputStreamBridge = null;
/* 126 */   private OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
/*     */   
/* 128 */   private UtilSystemException utilWrapper = UtilSystemException.get("rpc.encoding");
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getMaximumStreamFormatVersion() {
/* 133 */     return MAX_STREAM_FORMAT_VERSION;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeValue(OutputStream paramOutputStream, Serializable paramSerializable, byte paramByte) {
/* 141 */     if (paramByte == 2) {
/* 142 */       if (!(paramOutputStream instanceof org.omg.CORBA.portable.ValueOutputStream)) {
/* 143 */         throw this.omgWrapper.notAValueoutputstream();
/*     */       }
/* 145 */     } else if (paramByte != 1) {
/* 146 */       throw this.omgWrapper.invalidStreamFormatVersion(new Integer(paramByte));
/*     */     } 
/*     */ 
/*     */     
/* 150 */     writeValueWithVersion(paramOutputStream, paramSerializable, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ValueHandlerImpl(boolean paramBoolean) {
/* 156 */     this();
/* 157 */     this.useHashtables = false;
/* 158 */     this.isInputStream = paramBoolean;
/*     */   }
/*     */   
/*     */   static ValueHandlerImpl getInstance() {
/* 162 */     return new ValueHandlerImpl();
/*     */   }
/*     */   
/*     */   static ValueHandlerImpl getInstance(boolean paramBoolean) {
/* 166 */     return new ValueHandlerImpl(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeValue(OutputStream paramOutputStream, Serializable paramSerializable) {
/* 176 */     writeValueWithVersion(paramOutputStream, paramSerializable, (byte)1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeValueWithVersion(OutputStream paramOutputStream, Serializable paramSerializable, byte paramByte) {
/* 183 */     OutputStream outputStream = (OutputStream)paramOutputStream;
/*     */ 
/*     */     
/* 186 */     if (!this.useHashtables) {
/* 187 */       if (this.outputStreamBridge == null) {
/* 188 */         this.outputStreamBridge = createOutputStream();
/* 189 */         this.outputStreamBridge.setOrbStream(outputStream);
/*     */       } 
/*     */       
/*     */       try {
/* 193 */         this.outputStreamBridge.increaseRecursionDepth();
/* 194 */         writeValueInternal(this.outputStreamBridge, outputStream, paramSerializable, paramByte);
/*     */       } finally {
/* 196 */         this.outputStreamBridge.decreaseRecursionDepth();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 202 */     IIOPOutputStream iIOPOutputStream = null;
/*     */     
/* 204 */     if (this.outputStreamPairs == null) {
/* 205 */       this.outputStreamPairs = new Hashtable<>();
/*     */     }
/* 207 */     iIOPOutputStream = (IIOPOutputStream)this.outputStreamPairs.get(paramOutputStream);
/*     */     
/* 209 */     if (iIOPOutputStream == null) {
/* 210 */       iIOPOutputStream = createOutputStream();
/* 211 */       iIOPOutputStream.setOrbStream(outputStream);
/* 212 */       this.outputStreamPairs.put(paramOutputStream, iIOPOutputStream);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 217 */       iIOPOutputStream.increaseRecursionDepth();
/* 218 */       writeValueInternal(iIOPOutputStream, outputStream, paramSerializable, paramByte);
/*     */     } finally {
/* 220 */       if (iIOPOutputStream.decreaseRecursionDepth() == 0) {
/* 221 */         this.outputStreamPairs.remove(paramOutputStream);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeValueInternal(IIOPOutputStream paramIIOPOutputStream, OutputStream paramOutputStream, Serializable paramSerializable, byte paramByte) {
/* 231 */     Class<?> clazz = paramSerializable.getClass();
/*     */     
/* 233 */     if (clazz.isArray()) {
/* 234 */       write_Array(paramOutputStream, paramSerializable, clazz.getComponentType());
/*     */     } else {
/* 236 */       paramIIOPOutputStream.simpleWriteObject(paramSerializable, paramByte);
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
/*     */   public Serializable readValue(InputStream paramInputStream, int paramInt, Class paramClass, String paramString, RunTime paramRunTime) {
/* 253 */     CodeBase codeBase = CodeBaseHelper.narrow((Object)paramRunTime);
/*     */     
/* 255 */     InputStream inputStream = (InputStream)paramInputStream;
/*     */ 
/*     */     
/* 258 */     if (!this.useHashtables) {
/* 259 */       if (this.inputStreamBridge == null) {
/* 260 */         this.inputStreamBridge = createInputStream();
/* 261 */         this.inputStreamBridge.setOrbStream(inputStream);
/* 262 */         this.inputStreamBridge.setSender(codeBase);
/*     */         
/* 264 */         this.inputStreamBridge.setValueHandler((ValueHandler)this);
/*     */       } 
/*     */       
/* 267 */       Serializable serializable1 = null;
/*     */ 
/*     */       
/*     */       try {
/* 271 */         this.inputStreamBridge.increaseRecursionDepth();
/* 272 */         serializable1 = readValueInternal(this.inputStreamBridge, inputStream, paramInt, paramClass, paramString, codeBase);
/*     */       }
/*     */       finally {
/*     */         
/* 276 */         if (this.inputStreamBridge.decreaseRecursionDepth() == 0);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       return serializable1;
/*     */     } 
/*     */     
/* 286 */     IIOPInputStream iIOPInputStream = null;
/* 287 */     if (this.inputStreamPairs == null) {
/* 288 */       this.inputStreamPairs = new Hashtable<>();
/*     */     }
/* 290 */     iIOPInputStream = (IIOPInputStream)this.inputStreamPairs.get(paramInputStream);
/*     */     
/* 292 */     if (iIOPInputStream == null) {
/*     */       
/* 294 */       iIOPInputStream = createInputStream();
/* 295 */       iIOPInputStream.setOrbStream(inputStream);
/* 296 */       iIOPInputStream.setSender(codeBase);
/*     */       
/* 298 */       iIOPInputStream.setValueHandler((ValueHandler)this);
/* 299 */       this.inputStreamPairs.put(paramInputStream, iIOPInputStream);
/*     */     } 
/*     */     
/* 302 */     Serializable serializable = null;
/*     */ 
/*     */     
/*     */     try {
/* 306 */       iIOPInputStream.increaseRecursionDepth();
/* 307 */       serializable = readValueInternal(iIOPInputStream, inputStream, paramInt, paramClass, paramString, codeBase);
/*     */     }
/*     */     finally {
/*     */       
/* 311 */       if (iIOPInputStream.decreaseRecursionDepth() == 0) {
/* 312 */         this.inputStreamPairs.remove(paramInputStream);
/*     */       }
/*     */     } 
/*     */     
/* 316 */     return serializable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Serializable readValueInternal(IIOPInputStream paramIIOPInputStream, InputStream paramInputStream, int paramInt, Class paramClass, String paramString, CodeBase paramCodeBase) {
/* 326 */     Serializable serializable = null;
/*     */     
/* 328 */     if (paramClass == null) {
/*     */       
/* 330 */       if (isArray(paramString)) {
/* 331 */         read_Array(paramIIOPInputStream, paramInputStream, null, paramCodeBase, paramInt);
/*     */       } else {
/* 333 */         paramIIOPInputStream.simpleSkipObject(paramString, paramCodeBase);
/*     */       } 
/* 335 */       return serializable;
/*     */     } 
/*     */     
/* 338 */     if (paramClass.isArray()) {
/* 339 */       serializable = (Serializable)read_Array(paramIIOPInputStream, paramInputStream, paramClass, paramCodeBase, paramInt);
/*     */     } else {
/* 341 */       serializable = (Serializable)paramIIOPInputStream.simpleReadObject(paramClass, paramString, paramCodeBase, paramInt);
/*     */     } 
/*     */     
/* 344 */     return serializable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRMIRepositoryID(Class paramClass) {
/* 353 */     return RepositoryId.createForJavaType(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCustomMarshaled(Class<?> paramClass) {
/* 364 */     return ObjectStreamClass.lookup(paramClass).isCustomMarshaled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RunTime getRunTimeCodeBase() {
/* 375 */     if (this.codeBase != null) {
/* 376 */       return (RunTime)this.codeBase;
/*     */     }
/* 378 */     this.codeBase = (CodeBase)new FVDCodeBaseImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     FVDCodeBaseImpl fVDCodeBaseImpl = (FVDCodeBaseImpl)this.codeBase;
/* 384 */     fVDCodeBaseImpl.setValueHandler((ValueHandler)this);
/* 385 */     return (RunTime)this.codeBase;
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
/*     */   public boolean useFullValueDescription(Class paramClass, String paramString) throws IOException {
/* 402 */     return RepositoryId.useFullValueDescription(paramClass, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClassName(String paramString) {
/* 407 */     RepositoryId repositoryId = RepositoryId.cache.getId(paramString);
/* 408 */     return repositoryId.getClassName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassFromType(String paramString) throws ClassNotFoundException {
/* 414 */     RepositoryId repositoryId = RepositoryId.cache.getId(paramString);
/* 415 */     return repositoryId.getClassFromType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getAnyClassFromType(String paramString) throws ClassNotFoundException {
/* 421 */     RepositoryId repositoryId = RepositoryId.cache.getId(paramString);
/* 422 */     return repositoryId.getAnyClassFromType();
/*     */   }
/*     */ 
/*     */   
/*     */   public String createForAnyType(Class paramClass) {
/* 427 */     return RepositoryId.createForAnyType(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefinedInId(String paramString) {
/* 432 */     RepositoryId repositoryId = RepositoryId.cache.getId(paramString);
/* 433 */     return repositoryId.getDefinedInId();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUnqualifiedName(String paramString) {
/* 438 */     RepositoryId repositoryId = RepositoryId.cache.getId(paramString);
/* 439 */     return repositoryId.getUnqualifiedName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerialVersionUID(String paramString) {
/* 444 */     RepositoryId repositoryId = RepositoryId.cache.getId(paramString);
/* 445 */     return repositoryId.getSerialVersionUID();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbstractBase(Class paramClass) {
/* 451 */     return RepositoryId.isAbstractBase(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSequence(String paramString) {
/* 456 */     RepositoryId repositoryId = RepositoryId.cache.getId(paramString);
/* 457 */     return repositoryId.isSequence();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable writeReplace(Serializable paramSerializable) {
/* 466 */     return ObjectStreamClass.lookup(paramSerializable.getClass()).writeReplace(paramSerializable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeCharArray(OutputStream paramOutputStream, char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 474 */     paramOutputStream.write_wchar_array(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void write_Array(OutputStream paramOutputStream, Serializable paramSerializable, Class<int> paramClass) {
/* 481 */     if (paramClass.isPrimitive()) {
/* 482 */       if (paramClass == int.class) {
/* 483 */         int[] arrayOfInt = (int[])paramSerializable;
/* 484 */         int i = arrayOfInt.length;
/* 485 */         paramOutputStream.write_ulong(i);
/* 486 */         paramOutputStream.write_long_array(arrayOfInt, 0, i);
/* 487 */       } else if (paramClass == byte.class) {
/* 488 */         byte[] arrayOfByte = (byte[])paramSerializable;
/* 489 */         int i = arrayOfByte.length;
/* 490 */         paramOutputStream.write_ulong(i);
/* 491 */         paramOutputStream.write_octet_array(arrayOfByte, 0, i);
/* 492 */       } else if (paramClass == long.class) {
/* 493 */         long[] arrayOfLong = (long[])paramSerializable;
/* 494 */         int i = arrayOfLong.length;
/* 495 */         paramOutputStream.write_ulong(i);
/* 496 */         paramOutputStream.write_longlong_array(arrayOfLong, 0, i);
/* 497 */       } else if (paramClass == float.class) {
/* 498 */         float[] arrayOfFloat = (float[])paramSerializable;
/* 499 */         int i = arrayOfFloat.length;
/* 500 */         paramOutputStream.write_ulong(i);
/* 501 */         paramOutputStream.write_float_array(arrayOfFloat, 0, i);
/* 502 */       } else if (paramClass == double.class) {
/* 503 */         double[] arrayOfDouble = (double[])paramSerializable;
/* 504 */         int i = arrayOfDouble.length;
/* 505 */         paramOutputStream.write_ulong(i);
/* 506 */         paramOutputStream.write_double_array(arrayOfDouble, 0, i);
/* 507 */       } else if (paramClass == short.class) {
/* 508 */         short[] arrayOfShort = (short[])paramSerializable;
/* 509 */         int i = arrayOfShort.length;
/* 510 */         paramOutputStream.write_ulong(i);
/* 511 */         paramOutputStream.write_short_array(arrayOfShort, 0, i);
/* 512 */       } else if (paramClass == char.class) {
/* 513 */         char[] arrayOfChar = (char[])paramSerializable;
/* 514 */         int i = arrayOfChar.length;
/* 515 */         paramOutputStream.write_ulong(i);
/* 516 */         writeCharArray(paramOutputStream, arrayOfChar, 0, i);
/* 517 */       } else if (paramClass == boolean.class) {
/* 518 */         boolean[] arrayOfBoolean = (boolean[])paramSerializable;
/* 519 */         int i = arrayOfBoolean.length;
/* 520 */         paramOutputStream.write_ulong(i);
/* 521 */         paramOutputStream.write_boolean_array(arrayOfBoolean, 0, i);
/*     */       } else {
/*     */         
/* 524 */         throw new Error("Invalid primitive type : " + paramSerializable
/* 525 */             .getClass().getName());
/*     */       } 
/* 527 */     } else if (paramClass == Object.class) {
/* 528 */       Object[] arrayOfObject = (Object[])paramSerializable;
/* 529 */       int i = arrayOfObject.length;
/* 530 */       paramOutputStream.write_ulong(i);
/* 531 */       for (byte b = 0; b < i; b++) {
/* 532 */         Util.writeAny((OutputStream)paramOutputStream, arrayOfObject[b]);
/*     */       }
/*     */     } else {
/* 535 */       Object[] arrayOfObject = (Object[])paramSerializable;
/* 536 */       int i = arrayOfObject.length;
/* 537 */       paramOutputStream.write_ulong(i);
/* 538 */       byte b2 = 2;
/*     */       
/* 540 */       if (paramClass.isInterface()) {
/* 541 */         String str = paramClass.getName();
/*     */         
/* 543 */         if (Remote.class.isAssignableFrom(paramClass)) {
/*     */           
/* 545 */           b2 = 0;
/* 546 */         } else if (Object.class.isAssignableFrom(paramClass)) {
/*     */           
/* 548 */           b2 = 0;
/* 549 */         } else if (RepositoryId.isAbstractBase(paramClass)) {
/*     */           
/* 551 */           b2 = 1;
/* 552 */         } else if (ObjectStreamClassCorbaExt.isAbstractInterface(paramClass)) {
/* 553 */           b2 = 1;
/*     */         } 
/*     */       } 
/*     */       
/* 557 */       for (byte b1 = 0; b1 < i; b1++) {
/* 558 */         switch (b2) {
/*     */           case 0:
/* 560 */             Util.writeRemoteObject((OutputStream)paramOutputStream, arrayOfObject[b1]);
/*     */             break;
/*     */           case 1:
/* 563 */             Util.writeAbstractObject((OutputStream)paramOutputStream, arrayOfObject[b1]);
/*     */             break;
/*     */           case 2:
/*     */             try {
/* 567 */               paramOutputStream.write_value((Serializable)arrayOfObject[b1]);
/* 568 */             } catch (ClassCastException classCastException) {
/* 569 */               if (arrayOfObject[b1] instanceof Serializable) {
/* 570 */                 throw classCastException;
/*     */               }
/* 572 */               Utility.throwNotSerializableForCorba(arrayOfObject[b1]
/* 573 */                   .getClass().getName());
/*     */             } 
/*     */             break;
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
/*     */   private void readCharArray(InputStream paramInputStream, char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 587 */     paramInputStream.read_wchar_array(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object read_Array(IIOPInputStream paramIIOPInputStream, InputStream paramInputStream, Class paramClass, CodeBase paramCodeBase, int paramInt) {
/*     */     try {
/* 598 */       int i = paramInputStream.read_ulong();
/*     */ 
/*     */       
/* 601 */       if (paramClass == null) {
/* 602 */         for (byte b = 0; b < i; b++) {
/* 603 */           paramInputStream.read_value();
/*     */         }
/* 605 */         return null;
/*     */       } 
/*     */       
/* 608 */       Class<?> clazz1 = paramClass.getComponentType();
/* 609 */       Class<?> clazz2 = clazz1;
/*     */ 
/*     */       
/* 612 */       if (clazz1.isPrimitive()) {
/* 613 */         if (clazz1 == int.class) {
/* 614 */           int[] arrayOfInt = new int[i];
/* 615 */           paramInputStream.read_long_array(arrayOfInt, 0, i);
/* 616 */           return arrayOfInt;
/* 617 */         }  if (clazz1 == byte.class) {
/* 618 */           byte[] arrayOfByte = new byte[i];
/* 619 */           paramInputStream.read_octet_array(arrayOfByte, 0, i);
/* 620 */           return arrayOfByte;
/* 621 */         }  if (clazz1 == long.class) {
/* 622 */           long[] arrayOfLong = new long[i];
/* 623 */           paramInputStream.read_longlong_array(arrayOfLong, 0, i);
/* 624 */           return arrayOfLong;
/* 625 */         }  if (clazz1 == float.class) {
/* 626 */           float[] arrayOfFloat = new float[i];
/* 627 */           paramInputStream.read_float_array(arrayOfFloat, 0, i);
/* 628 */           return arrayOfFloat;
/* 629 */         }  if (clazz1 == double.class) {
/* 630 */           double[] arrayOfDouble = new double[i];
/* 631 */           paramInputStream.read_double_array(arrayOfDouble, 0, i);
/* 632 */           return arrayOfDouble;
/* 633 */         }  if (clazz1 == short.class) {
/* 634 */           short[] arrayOfShort = new short[i];
/* 635 */           paramInputStream.read_short_array(arrayOfShort, 0, i);
/* 636 */           return arrayOfShort;
/* 637 */         }  if (clazz1 == char.class) {
/* 638 */           char[] arrayOfChar = new char[i];
/* 639 */           readCharArray(paramInputStream, arrayOfChar, 0, i);
/* 640 */           return arrayOfChar;
/* 641 */         }  if (clazz1 == boolean.class) {
/* 642 */           boolean[] arrayOfBoolean = new boolean[i];
/* 643 */           paramInputStream.read_boolean_array(arrayOfBoolean, 0, i);
/* 644 */           return arrayOfBoolean;
/*     */         } 
/*     */         
/* 647 */         throw new Error("Invalid primitive componentType : " + paramClass.getName());
/*     */       } 
/* 649 */       if (clazz1 == Object.class) {
/* 650 */         Object[] arrayOfObject1 = (Object[])Array.newInstance(clazz1, i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 656 */         paramIIOPInputStream.activeRecursionMgr.addObject(paramInt, arrayOfObject1);
/*     */         
/* 658 */         for (byte b = 0; b < i; b++) {
/* 659 */           Object object = null;
/*     */           try {
/* 661 */             object = Util.readAny((InputStream)paramInputStream);
/* 662 */           } catch (IndirectionException indirectionException) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */               
/* 668 */               object = paramIIOPInputStream.activeRecursionMgr.getObject(indirectionException.offset);
/*     */             }
/* 670 */             catch (IOException iOException) {
/*     */ 
/*     */ 
/*     */               
/* 674 */               throw this.utilWrapper.invalidIndirection(iOException, new Integer(indirectionException.offset));
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 679 */           arrayOfObject1[b] = object;
/*     */         } 
/* 681 */         return arrayOfObject1;
/*     */       } 
/* 683 */       Object[] arrayOfObject = (Object[])Array.newInstance(clazz1, i);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 688 */       paramIIOPInputStream.activeRecursionMgr.addObject(paramInt, arrayOfObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 694 */       byte b2 = 2;
/* 695 */       boolean bool = false;
/*     */       
/* 697 */       if (clazz1.isInterface()) {
/* 698 */         boolean bool1 = false;
/*     */ 
/*     */         
/* 701 */         if (Remote.class.isAssignableFrom(clazz1)) {
/*     */ 
/*     */           
/* 704 */           b2 = 0;
/*     */ 
/*     */ 
/*     */           
/* 708 */           bool1 = true;
/* 709 */         } else if (Object.class.isAssignableFrom(clazz1)) {
/*     */           
/* 711 */           b2 = 0;
/* 712 */           bool1 = true;
/* 713 */         } else if (RepositoryId.isAbstractBase(clazz1)) {
/*     */           
/* 715 */           b2 = 1;
/* 716 */           bool1 = true;
/* 717 */         } else if (ObjectStreamClassCorbaExt.isAbstractInterface(clazz1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 722 */           b2 = 1;
/*     */         } 
/*     */         
/* 725 */         if (bool1) {
/*     */           try {
/* 727 */             String str1 = Util.getCodebase(clazz1);
/* 728 */             String str2 = RepositoryId.createForAnyType(clazz1);
/*     */             
/* 730 */             Class<?> clazz = Utility.loadStubClass(str2, str1, clazz1);
/* 731 */             clazz2 = clazz;
/* 732 */           } catch (ClassNotFoundException classNotFoundException) {
/* 733 */             bool = true;
/*     */           } 
/*     */         } else {
/* 736 */           bool = true;
/*     */         } 
/*     */       } 
/*     */       
/* 740 */       for (byte b1 = 0; b1 < i; b1++) {
/*     */         
/*     */         try {
/* 743 */           switch (b2) {
/*     */             case 0:
/* 745 */               if (!bool) {
/* 746 */                 arrayOfObject[b1] = paramInputStream.read_Object(clazz2); break;
/*     */               } 
/* 748 */               arrayOfObject[b1] = Utility.readObjectAndNarrow((InputStream)paramInputStream, clazz2);
/*     */               break;
/*     */ 
/*     */             
/*     */             case 1:
/* 753 */               if (!bool) {
/* 754 */                 arrayOfObject[b1] = paramInputStream.read_abstract_interface(clazz2); break;
/*     */               } 
/* 756 */               arrayOfObject[b1] = Utility.readAbstractAndNarrow(paramInputStream, clazz2);
/*     */               break;
/*     */             
/*     */             case 2:
/* 760 */               arrayOfObject[b1] = paramInputStream.read_value(clazz2);
/*     */               break;
/*     */           } 
/* 763 */         } catch (IndirectionException indirectionException) {
/*     */ 
/*     */           
/*     */           try {
/*     */             
/* 768 */             arrayOfObject[b1] = paramIIOPInputStream.activeRecursionMgr.getObject(indirectionException.offset);
/*     */           }
/* 770 */           catch (IOException iOException) {
/*     */ 
/*     */ 
/*     */             
/* 774 */             throw this.utilWrapper.invalidIndirection(iOException, new Integer(indirectionException.offset));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 781 */       return arrayOfObject;
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 788 */       paramIIOPInputStream.activeRecursionMgr.removeObject(paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isArray(String paramString) {
/* 793 */     return RepositoryId.cache.getId(paramString).isSequence();
/*     */   }
/*     */   
/*     */   private String getOutputStreamClassName() {
/* 797 */     return "com.sun.corba.se.impl.io.IIOPOutputStream";
/*     */   }
/*     */   
/*     */   private IIOPOutputStream createOutputStream() {
/* 801 */     String str = getOutputStreamClassName();
/*     */     try {
/* 803 */       IIOPOutputStream iIOPOutputStream = createOutputStreamBuiltIn(str);
/* 804 */       if (iIOPOutputStream != null) {
/* 805 */         return iIOPOutputStream;
/*     */       }
/* 807 */       return createCustom(IIOPOutputStream.class, str);
/* 808 */     } catch (Throwable throwable) {
/*     */       
/* 810 */       InternalError internalError = new InternalError("Error loading " + str);
/*     */ 
/*     */       
/* 813 */       internalError.initCause(throwable);
/* 814 */       throw internalError;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IIOPOutputStream createOutputStreamBuiltIn(final String name) throws Throwable {
/*     */     try {
/* 826 */       return AccessController.<IIOPOutputStream>doPrivileged(new PrivilegedExceptionAction<IIOPOutputStream>()
/*     */           {
/*     */             public IIOPOutputStream run() throws IOException {
/* 829 */               return ValueHandlerImpl.this.createOutputStreamBuiltInNoPriv(name);
/*     */             }
/*     */           });
/*     */     }
/* 833 */     catch (PrivilegedActionException privilegedActionException) {
/* 834 */       throw privilegedActionException.getCause();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IIOPOutputStream createOutputStreamBuiltInNoPriv(String paramString) throws IOException {
/* 844 */     return paramString.equals(IIOPOutputStream.class.getName()) ? new IIOPOutputStream() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getInputStreamClassName() {
/* 849 */     return "com.sun.corba.se.impl.io.IIOPInputStream";
/*     */   }
/*     */   
/*     */   private IIOPInputStream createInputStream() {
/* 853 */     String str = getInputStreamClassName();
/*     */     try {
/* 855 */       IIOPInputStream iIOPInputStream = createInputStreamBuiltIn(str);
/* 856 */       if (iIOPInputStream != null) {
/* 857 */         return iIOPInputStream;
/*     */       }
/* 859 */       return createCustom(IIOPInputStream.class, str);
/* 860 */     } catch (Throwable throwable) {
/*     */       
/* 862 */       InternalError internalError = new InternalError("Error loading " + str);
/*     */ 
/*     */       
/* 865 */       internalError.initCause(throwable);
/* 866 */       throw internalError;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IIOPInputStream createInputStreamBuiltIn(final String name) throws Throwable {
/*     */     try {
/* 878 */       return AccessController.<IIOPInputStream>doPrivileged(new PrivilegedExceptionAction<IIOPInputStream>()
/*     */           {
/*     */             public IIOPInputStream run() throws IOException {
/* 881 */               return ValueHandlerImpl.this.createInputStreamBuiltInNoPriv(name);
/*     */             }
/*     */           });
/*     */     }
/* 885 */     catch (PrivilegedActionException privilegedActionException) {
/* 886 */       throw privilegedActionException.getCause();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IIOPInputStream createInputStreamBuiltInNoPriv(String paramString) throws IOException {
/* 896 */     return paramString.equals(IIOPInputStream.class.getName()) ? new IIOPInputStream() : null;
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
/*     */   private <T> T createCustom(Class<T> paramClass, String paramString) throws Throwable {
/* 910 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 911 */     if (classLoader == null) {
/* 912 */       classLoader = ClassLoader.getSystemClassLoader();
/*     */     }
/* 914 */     Class<?> clazz = classLoader.loadClass(paramString);
/* 915 */     Class<? extends T> clazz1 = clazz.asSubclass(paramClass);
/*     */ 
/*     */ 
/*     */     
/* 919 */     return clazz1.newInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   TCKind getJavaCharTCKind() {
/* 924 */     return TCKind.tk_wchar;
/*     */   }
/*     */   
/*     */   private ValueHandlerImpl() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/ValueHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */