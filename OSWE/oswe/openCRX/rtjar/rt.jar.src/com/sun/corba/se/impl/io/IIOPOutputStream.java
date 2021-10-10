/*     */ package com.sun.corba.se.impl.io;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.UtilSystemException;
/*     */ import com.sun.corba.se.impl.util.RepositoryId;
/*     */ import com.sun.corba.se.impl.util.Utility;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidClassException;
/*     */ import java.io.NotActiveException;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.rmi.Remote;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Stack;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ValueOutputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import sun.corba.Bridge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IIOPOutputStream
/*     */   extends OutputStreamHook
/*     */ {
/*  77 */   private UtilSystemException wrapper = UtilSystemException.get("rpc.encoding");
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static Bridge bridge = AccessController.<Bridge>doPrivileged(new PrivilegedAction<Bridge>()
/*     */       {
/*     */         public Object run() {
/*  84 */           return Bridge.get();
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private OutputStream orbStream;
/*     */   
/*  91 */   private Object currentObject = null;
/*     */   
/*  93 */   private ObjectStreamClass currentClassDesc = null;
/*     */   
/*  95 */   private int recursionDepth = 0;
/*     */   
/*  97 */   private int simpleWriteDepth = 0;
/*     */   
/*  99 */   private IOException abortIOException = null;
/*     */   
/* 101 */   private Stack classDescStack = new Stack();
/*     */ 
/*     */   
/* 104 */   private Object[] writeObjectArgList = new Object[] { this };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void beginOptionalCustomData() {
/* 118 */     if (this.streamFormatVersion == 2) {
/*     */       
/* 120 */       ValueOutputStream valueOutputStream = (ValueOutputStream)this.orbStream;
/*     */ 
/*     */       
/* 123 */       valueOutputStream.start_value(this.currentClassDesc.getRMIIIOPOptionalDataRepId());
/*     */     } 
/*     */   }
/*     */   
/*     */   final void setOrbStream(OutputStream paramOutputStream) {
/* 128 */     this.orbStream = paramOutputStream;
/*     */   }
/*     */   
/*     */   final OutputStream getOrbStream() {
/* 132 */     return this.orbStream;
/*     */   }
/*     */   
/*     */   final void increaseRecursionDepth() {
/* 136 */     this.recursionDepth++;
/*     */   }
/*     */   
/*     */   final int decreaseRecursionDepth() {
/* 140 */     return --this.recursionDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeObjectOverride(Object paramObject) throws IOException {
/* 151 */     this.writeObjectState.writeData(this);
/*     */     
/* 153 */     Util.writeAbstractObject((OutputStream)this.orbStream, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void simpleWriteObject(Object paramObject, byte paramByte) {
/* 164 */     byte b = this.streamFormatVersion;
/*     */     
/* 166 */     this.streamFormatVersion = paramByte;
/*     */     
/* 168 */     Object object = this.currentObject;
/* 169 */     ObjectStreamClass objectStreamClass = this.currentClassDesc;
/* 170 */     this.simpleWriteDepth++;
/*     */ 
/*     */     
/*     */     try {
/* 174 */       outputObject(paramObject);
/*     */     }
/* 176 */     catch (IOException iOException1) {
/* 177 */       if (this.abortIOException == null) {
/* 178 */         this.abortIOException = iOException1;
/*     */       }
/*     */     } finally {
/* 181 */       this.streamFormatVersion = b;
/* 182 */       this.simpleWriteDepth--;
/* 183 */       this.currentObject = object;
/* 184 */       this.currentClassDesc = objectStreamClass;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     IOException iOException = this.abortIOException;
/* 191 */     if (this.simpleWriteDepth == 0)
/* 192 */       this.abortIOException = null; 
/* 193 */     if (iOException != null) {
/* 194 */       bridge.throwException(iOException);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   ObjectStreamField[] getFieldsNoCopy() {
/* 200 */     return this.currentClassDesc.getFieldsNoCopy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void defaultWriteObjectDelegate() {
/*     */     try {
/* 212 */       if (this.currentObject == null || this.currentClassDesc == null)
/*     */       {
/* 214 */         throw new NotActiveException("defaultWriteObjectDelegate");
/*     */       }
/*     */       
/* 217 */       ObjectStreamField[] arrayOfObjectStreamField = this.currentClassDesc.getFieldsNoCopy();
/* 218 */       if (arrayOfObjectStreamField.length > 0) {
/* 219 */         outputClassFields(this.currentObject, this.currentClassDesc.forClass(), arrayOfObjectStreamField);
/*     */       }
/*     */     }
/* 222 */     catch (IOException iOException) {
/* 223 */       bridge.throwException(iOException);
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
/*     */   public final boolean enableReplaceObjectDelegate(boolean paramBoolean) {
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void annotateClass(Class<?> paramClass) throws IOException {
/* 242 */     throw new IOException("Method annotateClass not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() throws IOException {}
/*     */ 
/*     */   
/*     */   protected final void drain() throws IOException {}
/*     */ 
/*     */   
/*     */   public final void flush() throws IOException {
/*     */     try {
/* 255 */       this.orbStream.flush();
/* 256 */     } catch (Error error) {
/* 257 */       IOException iOException = new IOException(error.getMessage());
/* 258 */       iOException.initCause(error);
/* 259 */       throw iOException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object replaceObject(Object paramObject) throws IOException {
/* 265 */     throw new IOException("Method replaceObject not supported");
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
/*     */   public final void reset() throws IOException {
/*     */     try {
/* 282 */       if (this.currentObject != null || this.currentClassDesc != null)
/*     */       {
/* 284 */         throw new IOException("Illegal call to reset");
/*     */       }
/* 286 */       this.abortIOException = null;
/*     */       
/* 288 */       if (this.classDescStack == null) {
/* 289 */         this.classDescStack = new Stack();
/*     */       } else {
/* 291 */         this.classDescStack.setSize(0);
/*     */       } 
/* 293 */     } catch (Error error) {
/* 294 */       IOException iOException = new IOException(error.getMessage());
/* 295 */       iOException.initCause(error);
/* 296 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write(byte[] paramArrayOfbyte) throws IOException {
/*     */     try {
/* 302 */       this.writeObjectState.writeData(this);
/*     */       
/* 304 */       this.orbStream.write_octet_array(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/* 305 */     } catch (Error error) {
/* 306 */       IOException iOException = new IOException(error.getMessage());
/* 307 */       iOException.initCause(error);
/* 308 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     try {
/* 314 */       this.writeObjectState.writeData(this);
/*     */       
/* 316 */       this.orbStream.write_octet_array(paramArrayOfbyte, paramInt1, paramInt2);
/* 317 */     } catch (Error error) {
/* 318 */       IOException iOException = new IOException(error.getMessage());
/* 319 */       iOException.initCause(error);
/* 320 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write(int paramInt) throws IOException {
/*     */     try {
/* 326 */       this.writeObjectState.writeData(this);
/*     */       
/* 328 */       this.orbStream.write_octet((byte)(paramInt & 0xFF));
/* 329 */     } catch (Error error) {
/* 330 */       IOException iOException = new IOException(error.getMessage());
/* 331 */       iOException.initCause(error);
/* 332 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeBoolean(boolean paramBoolean) throws IOException {
/*     */     try {
/* 338 */       this.writeObjectState.writeData(this);
/*     */       
/* 340 */       this.orbStream.write_boolean(paramBoolean);
/* 341 */     } catch (Error error) {
/* 342 */       IOException iOException = new IOException(error.getMessage());
/* 343 */       iOException.initCause(error);
/* 344 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeByte(int paramInt) throws IOException {
/*     */     try {
/* 350 */       this.writeObjectState.writeData(this);
/*     */       
/* 352 */       this.orbStream.write_octet((byte)paramInt);
/* 353 */     } catch (Error error) {
/* 354 */       IOException iOException = new IOException(error.getMessage());
/* 355 */       iOException.initCause(error);
/* 356 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeBytes(String paramString) throws IOException {
/*     */     try {
/* 362 */       this.writeObjectState.writeData(this);
/*     */       
/* 364 */       byte[] arrayOfByte = paramString.getBytes();
/* 365 */       this.orbStream.write_octet_array(arrayOfByte, 0, arrayOfByte.length);
/* 366 */     } catch (Error error) {
/* 367 */       IOException iOException = new IOException(error.getMessage());
/* 368 */       iOException.initCause(error);
/* 369 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeChar(int paramInt) throws IOException {
/*     */     try {
/* 375 */       this.writeObjectState.writeData(this);
/*     */       
/* 377 */       this.orbStream.write_wchar((char)paramInt);
/* 378 */     } catch (Error error) {
/* 379 */       IOException iOException = new IOException(error.getMessage());
/* 380 */       iOException.initCause(error);
/* 381 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeChars(String paramString) throws IOException {
/*     */     try {
/* 387 */       this.writeObjectState.writeData(this);
/*     */       
/* 389 */       char[] arrayOfChar = paramString.toCharArray();
/* 390 */       this.orbStream.write_wchar_array(arrayOfChar, 0, arrayOfChar.length);
/* 391 */     } catch (Error error) {
/* 392 */       IOException iOException = new IOException(error.getMessage());
/* 393 */       iOException.initCause(error);
/* 394 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeDouble(double paramDouble) throws IOException {
/*     */     try {
/* 400 */       this.writeObjectState.writeData(this);
/*     */       
/* 402 */       this.orbStream.write_double(paramDouble);
/* 403 */     } catch (Error error) {
/* 404 */       IOException iOException = new IOException(error.getMessage());
/* 405 */       iOException.initCause(error);
/* 406 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeFloat(float paramFloat) throws IOException {
/*     */     try {
/* 412 */       this.writeObjectState.writeData(this);
/*     */       
/* 414 */       this.orbStream.write_float(paramFloat);
/* 415 */     } catch (Error error) {
/* 416 */       IOException iOException = new IOException(error.getMessage());
/* 417 */       iOException.initCause(error);
/* 418 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeInt(int paramInt) throws IOException {
/*     */     try {
/* 424 */       this.writeObjectState.writeData(this);
/*     */       
/* 426 */       this.orbStream.write_long(paramInt);
/* 427 */     } catch (Error error) {
/* 428 */       IOException iOException = new IOException(error.getMessage());
/* 429 */       iOException.initCause(error);
/* 430 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeLong(long paramLong) throws IOException {
/*     */     try {
/* 436 */       this.writeObjectState.writeData(this);
/*     */       
/* 438 */       this.orbStream.write_longlong(paramLong);
/* 439 */     } catch (Error error) {
/* 440 */       IOException iOException = new IOException(error.getMessage());
/* 441 */       iOException.initCause(error);
/* 442 */       throw iOException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeShort(int paramInt) throws IOException {
/*     */     try {
/* 448 */       this.writeObjectState.writeData(this);
/*     */       
/* 450 */       this.orbStream.write_short((short)paramInt);
/* 451 */     } catch (Error error) {
/* 452 */       IOException iOException = new IOException(error.getMessage());
/* 453 */       iOException.initCause(error);
/* 454 */       throw iOException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeStreamHeader() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void internalWriteUTF(OutputStream paramOutputStream, String paramString) {
/* 471 */     paramOutputStream.write_wstring(paramString);
/*     */   }
/*     */   
/*     */   public final void writeUTF(String paramString) throws IOException {
/*     */     try {
/* 476 */       this.writeObjectState.writeData(this);
/*     */       
/* 478 */       internalWriteUTF((OutputStream)this.orbStream, paramString);
/* 479 */     } catch (Error error) {
/* 480 */       IOException iOException = new IOException(error.getMessage());
/* 481 */       iOException.initCause(error);
/* 482 */       throw iOException;
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
/*     */   private boolean checkSpecialClasses(Object paramObject) throws IOException {
/* 500 */     if (paramObject instanceof ObjectStreamClass)
/*     */     {
/* 502 */       throw new IOException("Serialization of ObjectStreamClass not supported");
/*     */     }
/*     */     
/* 505 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkSubstitutableSpecialClasses(Object paramObject) throws IOException {
/* 515 */     if (paramObject instanceof String) {
/* 516 */       this.orbStream.write_value((Serializable)paramObject);
/* 517 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 525 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void outputObject(Object paramObject) throws IOException {
/* 533 */     this.currentObject = paramObject;
/* 534 */     Class<?> clazz = paramObject.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 539 */     this.currentClassDesc = ObjectStreamClass.lookup(clazz);
/* 540 */     if (this.currentClassDesc == null)
/*     */     {
/* 542 */       throw new NotSerializableException(clazz.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 549 */     if (this.currentClassDesc.isExternalizable()) {
/*     */       
/* 551 */       this.orbStream.write_octet(this.streamFormatVersion);
/*     */       
/* 553 */       Externalizable externalizable = (Externalizable)paramObject;
/* 554 */       externalizable.writeExternal(this);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 562 */       if (this.currentClassDesc.forClass().getName().equals("java.lang.String")) {
/* 563 */         writeUTF((String)paramObject);
/*     */         return;
/*     */       } 
/* 566 */       int i = this.classDescStack.size();
/*     */       try {
/*     */         ObjectStreamClass objectStreamClass;
/* 569 */         while ((objectStreamClass = this.currentClassDesc.getSuperclass()) != null) {
/* 570 */           this.classDescStack.push(this.currentClassDesc);
/* 571 */           this.currentClassDesc = objectStreamClass;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         do {
/* 583 */           OutputStreamHook.WriteObjectState writeObjectState = this.writeObjectState;
/*     */ 
/*     */           
/*     */           try {
/* 587 */             setState(NOT_IN_WRITE_OBJECT);
/*     */             
/* 589 */             if (this.currentClassDesc.hasWriteObject()) {
/* 590 */               invokeObjectWriter(this.currentClassDesc, paramObject);
/*     */             } else {
/* 592 */               defaultWriteObjectDelegate();
/*     */             } 
/*     */           } finally {
/* 595 */             setState(writeObjectState);
/*     */           }
/*     */         
/* 598 */         } while (this.classDescStack.size() > i && (this
/* 599 */           .currentClassDesc = this.classDescStack.pop()) != null);
/*     */       } finally {
/* 601 */         this.classDescStack.setSize(i);
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
/*     */   private void invokeObjectWriter(ObjectStreamClass paramObjectStreamClass, Object paramObject) throws IOException {
/* 614 */     Class<?> clazz = paramObjectStreamClass.forClass();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 619 */       this.orbStream.write_octet(this.streamFormatVersion);
/*     */       
/* 621 */       this.writeObjectState.enterWriteObject(this);
/*     */ 
/*     */       
/* 624 */       paramObjectStreamClass.writeObjectMethod.invoke(paramObject, this.writeObjectArgList);
/*     */       
/* 626 */       this.writeObjectState.exitWriteObject(this);
/*     */     }
/* 628 */     catch (InvocationTargetException invocationTargetException) {
/* 629 */       Throwable throwable = invocationTargetException.getTargetException();
/* 630 */       if (throwable instanceof IOException)
/* 631 */         throw (IOException)throwable; 
/* 632 */       if (throwable instanceof RuntimeException)
/* 633 */         throw (RuntimeException)throwable; 
/* 634 */       if (throwable instanceof Error) {
/* 635 */         throw (Error)throwable;
/*     */       }
/*     */       
/* 638 */       throw new Error("invokeObjectWriter internal error", invocationTargetException);
/* 639 */     } catch (IllegalAccessException illegalAccessException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void writeField(ObjectStreamField paramObjectStreamField, Object paramObject) throws IOException {
/* 645 */     switch (paramObjectStreamField.getTypeCode()) {
/*     */       case 'B':
/* 647 */         if (paramObject == null) {
/* 648 */           this.orbStream.write_octet((byte)0);
/*     */         } else {
/* 650 */           this.orbStream.write_octet(((Byte)paramObject).byteValue());
/*     */         }  return;
/*     */       case 'C':
/* 653 */         if (paramObject == null) {
/* 654 */           this.orbStream.write_wchar(false);
/*     */         } else {
/* 656 */           this.orbStream.write_wchar(((Character)paramObject).charValue());
/*     */         }  return;
/*     */       case 'F':
/* 659 */         if (paramObject == null) {
/* 660 */           this.orbStream.write_float(0.0F);
/*     */         } else {
/* 662 */           this.orbStream.write_float(((Float)paramObject).floatValue());
/*     */         }  return;
/*     */       case 'D':
/* 665 */         if (paramObject == null) {
/* 666 */           this.orbStream.write_double(0.0D);
/*     */         } else {
/* 668 */           this.orbStream.write_double(((Double)paramObject).doubleValue());
/*     */         }  return;
/*     */       case 'I':
/* 671 */         if (paramObject == null) {
/* 672 */           this.orbStream.write_long(0);
/*     */         } else {
/* 674 */           this.orbStream.write_long(((Integer)paramObject).intValue());
/*     */         }  return;
/*     */       case 'J':
/* 677 */         if (paramObject == null) {
/* 678 */           this.orbStream.write_longlong(0L);
/*     */         } else {
/* 680 */           this.orbStream.write_longlong(((Long)paramObject).longValue());
/*     */         }  return;
/*     */       case 'S':
/* 683 */         if (paramObject == null) {
/* 684 */           this.orbStream.write_short((short)0);
/*     */         } else {
/* 686 */           this.orbStream.write_short(((Short)paramObject).shortValue());
/*     */         }  return;
/*     */       case 'Z':
/* 689 */         if (paramObject == null) {
/* 690 */           this.orbStream.write_boolean(false);
/*     */         } else {
/* 692 */           this.orbStream.write_boolean(((Boolean)paramObject).booleanValue());
/*     */         } 
/*     */         return;
/*     */       case 'L':
/*     */       case '[':
/* 697 */         writeObjectField(paramObjectStreamField, paramObject);
/*     */         return;
/*     */     } 
/*     */     
/* 701 */     throw new InvalidClassException(this.currentClassDesc.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObjectField(ObjectStreamField paramObjectStreamField, Object paramObject) throws IOException {
/* 708 */     if (ObjectStreamClassCorbaExt.isAny(paramObjectStreamField.getTypeString())) {
/* 709 */       Util.writeAny((OutputStream)this.orbStream, paramObject);
/*     */     } else {
/*     */       
/* 712 */       Class<?> clazz = paramObjectStreamField.getType();
/* 713 */       byte b = 2;
/*     */       
/* 715 */       if (clazz.isInterface()) {
/* 716 */         String str = clazz.getName();
/*     */         
/* 718 */         if (Remote.class.isAssignableFrom(clazz)) {
/*     */ 
/*     */ 
/*     */           
/* 722 */           b = 0;
/*     */         
/*     */         }
/* 725 */         else if (Object.class.isAssignableFrom(clazz)) {
/*     */ 
/*     */           
/* 728 */           b = 0;
/*     */         }
/* 730 */         else if (RepositoryId.isAbstractBase(clazz)) {
/*     */           
/* 732 */           b = 1;
/* 733 */         } else if (ObjectStreamClassCorbaExt.isAbstractInterface(clazz)) {
/* 734 */           b = 1;
/*     */         } 
/*     */       } 
/*     */       
/* 738 */       switch (b) {
/*     */         case 0:
/* 740 */           Util.writeRemoteObject((OutputStream)this.orbStream, paramObject);
/*     */           break;
/*     */         case 1:
/* 743 */           Util.writeAbstractObject((OutputStream)this.orbStream, paramObject);
/*     */           break;
/*     */         case 2:
/*     */           try {
/* 747 */             this.orbStream.write_value((Serializable)paramObject, clazz);
/*     */           }
/* 749 */           catch (ClassCastException classCastException) {
/* 750 */             if (paramObject instanceof Serializable) {
/* 751 */               throw classCastException;
/*     */             }
/* 753 */             Utility.throwNotSerializableForCorba(paramObject.getClass().getName());
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void outputClassFields(Object paramObject, Class paramClass, ObjectStreamField[] paramArrayOfObjectStreamField) throws IOException, InvalidClassException {
/* 766 */     for (byte b = 0; b < paramArrayOfObjectStreamField.length; b++) {
/* 767 */       if (paramArrayOfObjectStreamField[b].getField() == null)
/*     */       {
/* 769 */         throw new InvalidClassException(paramClass.getName(), "Nonexistent field " + paramArrayOfObjectStreamField[b]
/* 770 */             .getName()); }  try {
/*     */         byte b1; char c; float f; double d; int i; long l; short s; boolean bool;
/*     */         Object object;
/* 773 */         switch (paramArrayOfObjectStreamField[b].getTypeCode()) {
/*     */           case 'B':
/* 775 */             b1 = paramArrayOfObjectStreamField[b].getField().getByte(paramObject);
/* 776 */             this.orbStream.write_octet(b1);
/*     */             break;
/*     */           case 'C':
/* 779 */             c = paramArrayOfObjectStreamField[b].getField().getChar(paramObject);
/* 780 */             this.orbStream.write_wchar(c);
/*     */             break;
/*     */           case 'F':
/* 783 */             f = paramArrayOfObjectStreamField[b].getField().getFloat(paramObject);
/* 784 */             this.orbStream.write_float(f);
/*     */             break;
/*     */           case 'D':
/* 787 */             d = paramArrayOfObjectStreamField[b].getField().getDouble(paramObject);
/* 788 */             this.orbStream.write_double(d);
/*     */             break;
/*     */           case 'I':
/* 791 */             i = paramArrayOfObjectStreamField[b].getField().getInt(paramObject);
/* 792 */             this.orbStream.write_long(i);
/*     */             break;
/*     */           case 'J':
/* 795 */             l = paramArrayOfObjectStreamField[b].getField().getLong(paramObject);
/* 796 */             this.orbStream.write_longlong(l);
/*     */             break;
/*     */           case 'S':
/* 799 */             s = paramArrayOfObjectStreamField[b].getField().getShort(paramObject);
/* 800 */             this.orbStream.write_short(s);
/*     */             break;
/*     */           case 'Z':
/* 803 */             bool = paramArrayOfObjectStreamField[b].getField().getBoolean(paramObject);
/* 804 */             this.orbStream.write_boolean(bool);
/*     */             break;
/*     */           case 'L':
/*     */           case '[':
/* 808 */             object = paramArrayOfObjectStreamField[b].getField().get(paramObject);
/* 809 */             writeObjectField(paramArrayOfObjectStreamField[b], object);
/*     */             break;
/*     */           
/*     */           default:
/* 813 */             throw new InvalidClassException(paramClass.getName());
/*     */         } 
/* 815 */       } catch (IllegalAccessException illegalAccessException) {
/* 816 */         throw this.wrapper.illegalFieldAccess(illegalAccessException, paramArrayOfObjectStreamField[b].getName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/IIOPOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */