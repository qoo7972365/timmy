/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.TypeCodeImpl;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.util.Utility;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Principal;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.BoxedValueHelper;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class IDLJavaSerializationOutputStream
/*     */   extends CDROutputStreamBase
/*     */ {
/*     */   private ORB orb;
/*     */   private byte encodingVersion;
/*     */   private ObjectOutputStream os;
/*     */   private _ByteArrayOutputStream bos;
/*     */   private BufferManagerWrite bufferManager;
/*  78 */   private final int directWriteLength = 16;
/*     */   protected ORBUtilSystemException wrapper;
/*     */   
/*     */   class _ByteArrayOutputStream
/*     */     extends ByteArrayOutputStream
/*     */   {
/*     */     _ByteArrayOutputStream(int param1Int) {
/*  85 */       super(param1Int);
/*     */     }
/*     */     
/*     */     byte[] getByteArray() {
/*  89 */       return this.buf;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class MarshalObjectOutputStream
/*     */     extends ObjectOutputStream
/*     */   {
/*     */     ORB orb;
/*     */     
/*     */     MarshalObjectOutputStream(OutputStream param1OutputStream, ORB param1ORB) throws IOException {
/* 100 */       super(param1OutputStream);
/* 101 */       this.orb = param1ORB;
/* 102 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run()
/*     */             {
/* 106 */               IDLJavaSerializationOutputStream.MarshalObjectOutputStream.this.enableReplaceObject(true);
/* 107 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final Object replaceObject(Object param1Object) throws IOException {
/*     */       try {
/* 119 */         if (param1Object instanceof java.rmi.Remote && 
/* 120 */           !StubAdapter.isStub(param1Object)) {
/* 121 */           return Utility.autoConnect(param1Object, (ORB)this.orb, true);
/*     */         }
/* 123 */       } catch (Exception exception) {
/* 124 */         IOException iOException = new IOException("replaceObject failed");
/* 125 */         iOException.initCause(exception);
/* 126 */         throw iOException;
/*     */       } 
/* 128 */       return param1Object;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IDLJavaSerializationOutputStream(byte paramByte) {
/* 134 */     this.encodingVersion = paramByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(ORB paramORB, boolean paramBoolean1, BufferManagerWrite paramBufferManagerWrite, byte paramByte, boolean paramBoolean2) {
/* 141 */     this.orb = (ORB)paramORB;
/* 142 */     this.bufferManager = paramBufferManagerWrite;
/* 143 */     this.wrapper = ORBUtilSystemException.get((ORB)paramORB, "rpc.encoding");
/*     */     
/* 145 */     this.bos = new _ByteArrayOutputStream(1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initObjectOutputStream() {
/* 152 */     if (this.os != null) {
/* 153 */       throw this.wrapper.javaStreamInitFailed();
/*     */     }
/*     */     try {
/* 156 */       this.os = new MarshalObjectOutputStream(this.bos, this.orb);
/* 157 */     } catch (Exception exception) {
/* 158 */       throw this.wrapper.javaStreamInitFailed(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_boolean(boolean paramBoolean) {
/*     */     try {
/* 168 */       this.os.writeBoolean(paramBoolean);
/* 169 */     } catch (Exception exception) {
/* 170 */       throw this.wrapper.javaSerializationException(exception, "write_boolean");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write_char(char paramChar) {
/*     */     try {
/* 176 */       this.os.writeChar(paramChar);
/* 177 */     } catch (Exception exception) {
/* 178 */       throw this.wrapper.javaSerializationException(exception, "write_char");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write_wchar(char paramChar) {
/* 183 */     write_char(paramChar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_octet(byte paramByte) {
/* 189 */     if (this.bos.size() < 16) {
/* 190 */       this.bos.write(paramByte);
/* 191 */       if (this.bos.size() == 16) {
/* 192 */         initObjectOutputStream();
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 198 */       this.os.writeByte(paramByte);
/* 199 */     } catch (Exception exception) {
/* 200 */       throw this.wrapper.javaSerializationException(exception, "write_octet");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write_short(short paramShort) {
/*     */     try {
/* 206 */       this.os.writeShort(paramShort);
/* 207 */     } catch (Exception exception) {
/* 208 */       throw this.wrapper.javaSerializationException(exception, "write_short");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write_ushort(short paramShort) {
/* 213 */     write_short(paramShort);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_long(int paramInt) {
/* 219 */     if (this.bos.size() < 16) {
/*     */ 
/*     */ 
/*     */       
/* 223 */       this.bos.write((byte)(paramInt >>> 24 & 0xFF));
/* 224 */       this.bos.write((byte)(paramInt >>> 16 & 0xFF));
/* 225 */       this.bos.write((byte)(paramInt >>> 8 & 0xFF));
/* 226 */       this.bos.write((byte)(paramInt >>> 0 & 0xFF));
/*     */       
/* 228 */       if (this.bos.size() == 16) {
/* 229 */         initObjectOutputStream();
/* 230 */       } else if (this.bos.size() > 16) {
/*     */ 
/*     */         
/* 233 */         this.wrapper.javaSerializationException("write_long");
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 239 */       this.os.writeInt(paramInt);
/* 240 */     } catch (Exception exception) {
/* 241 */       throw this.wrapper.javaSerializationException(exception, "write_long");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write_ulong(int paramInt) {
/* 246 */     write_long(paramInt);
/*     */   }
/*     */   
/*     */   public final void write_longlong(long paramLong) {
/*     */     try {
/* 251 */       this.os.writeLong(paramLong);
/* 252 */     } catch (Exception exception) {
/* 253 */       throw this.wrapper.javaSerializationException(exception, "write_longlong");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write_ulonglong(long paramLong) {
/* 258 */     write_longlong(paramLong);
/*     */   }
/*     */   
/*     */   public final void write_float(float paramFloat) {
/*     */     try {
/* 263 */       this.os.writeFloat(paramFloat);
/* 264 */     } catch (Exception exception) {
/* 265 */       throw this.wrapper.javaSerializationException(exception, "write_float");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write_double(double paramDouble) {
/*     */     try {
/* 271 */       this.os.writeDouble(paramDouble);
/* 272 */     } catch (Exception exception) {
/* 273 */       throw this.wrapper.javaSerializationException(exception, "write_double");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_string(String paramString) {
/*     */     try {
/* 281 */       this.os.writeUTF(paramString);
/* 282 */     } catch (Exception exception) {
/* 283 */       throw this.wrapper.javaSerializationException(exception, "write_string");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write_wstring(String paramString) {
/*     */     try {
/* 289 */       this.os.writeObject(paramString);
/* 290 */     } catch (Exception exception) {
/* 291 */       throw this.wrapper.javaSerializationException(exception, "write_wstring");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) {
/* 299 */     for (byte b = 0; b < paramInt2; b++) {
/* 300 */       write_boolean(paramArrayOfboolean[paramInt1 + b]);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void write_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 305 */     for (byte b = 0; b < paramInt2; b++) {
/* 306 */       write_char(paramArrayOfchar[paramInt1 + b]);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void write_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 311 */     write_char_array(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public final void write_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*     */     try {
/* 316 */       this.os.write(paramArrayOfbyte, paramInt1, paramInt2);
/* 317 */     } catch (Exception exception) {
/* 318 */       throw this.wrapper.javaSerializationException(exception, "write_octet_array");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 324 */     for (byte b = 0; b < paramInt2; b++) {
/* 325 */       write_short(paramArrayOfshort[paramInt1 + b]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 331 */     write_short_array(paramArrayOfshort, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public final void write_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 335 */     for (byte b = 0; b < paramInt2; b++) {
/* 336 */       write_long(paramArrayOfint[paramInt1 + b]);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void write_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 341 */     write_long_array(paramArrayOfint, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 346 */     for (byte b = 0; b < paramInt2; b++) {
/* 347 */       write_longlong(paramArrayOflong[paramInt1 + b]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 353 */     write_longlong_array(paramArrayOflong, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 358 */     for (byte b = 0; b < paramInt2; b++) {
/* 359 */       write_float(paramArrayOffloat[paramInt1 + b]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 365 */     for (byte b = 0; b < paramInt2; b++) {
/* 366 */       write_double(paramArrayOfdouble[paramInt1 + b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_Object(Object paramObject) {
/* 373 */     if (paramObject == null) {
/* 374 */       IOR iOR1 = IORFactories.makeIOR(this.orb);
/* 375 */       iOR1.write(this.parent);
/*     */       
/*     */       return;
/*     */     } 
/* 379 */     if (paramObject instanceof org.omg.CORBA.LocalObject) {
/* 380 */       throw this.wrapper.writeLocalObject(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/* 382 */     IOR iOR = ORBUtility.connectAndGetIOR(this.orb, paramObject);
/* 383 */     iOR.write(this.parent);
/*     */   }
/*     */   
/*     */   public final void write_TypeCode(TypeCode paramTypeCode) {
/*     */     TypeCodeImpl typeCodeImpl;
/* 388 */     if (paramTypeCode == null) {
/* 389 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*     */     
/* 392 */     if (paramTypeCode instanceof TypeCodeImpl) {
/* 393 */       typeCodeImpl = (TypeCodeImpl)paramTypeCode;
/*     */     } else {
/* 395 */       typeCodeImpl = new TypeCodeImpl(this.orb, paramTypeCode);
/*     */     } 
/* 397 */     typeCodeImpl.write_value(this.parent);
/*     */   }
/*     */   
/*     */   public final void write_any(Any paramAny) {
/* 401 */     if (paramAny == null) {
/* 402 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/* 404 */     write_TypeCode(paramAny.type());
/* 405 */     paramAny.write_value((OutputStream)this.parent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_Principal(Principal paramPrincipal) {
/* 411 */     write_long((paramPrincipal.name()).length);
/* 412 */     write_octet_array(paramPrincipal.name(), 0, (paramPrincipal.name()).length);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_fixed(BigDecimal paramBigDecimal) {
/* 417 */     write_fixed(paramBigDecimal.toString(), paramBigDecimal.signum());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void write_fixed(String paramString, int paramInt) {
/* 423 */     int i = paramString.length();
/*     */ 
/*     */     
/* 426 */     byte b = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 431 */     byte b1 = 0; byte b2;
/* 432 */     for (b2 = 0; b2 < i; b2++) {
/* 433 */       char c = paramString.charAt(b2);
/* 434 */       if (c != '-' && c != '+' && c != '.')
/*     */       {
/* 436 */         b1++;
/*     */       }
/*     */     } 
/* 439 */     for (b2 = 0; b2 < i; b2++) {
/* 440 */       char c = paramString.charAt(b2);
/* 441 */       if (c != '-' && c != '+' && c != '.') {
/*     */         
/* 443 */         byte b3 = (byte)Character.digit(c, 10);
/* 444 */         if (b3 == -1) {
/* 445 */           throw this.wrapper.badDigitInFixed(CompletionStatus.COMPLETED_MAYBE);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 452 */         if (b1 % 2 == 0) {
/* 453 */           b = (byte)(b | b3);
/* 454 */           write_octet(b);
/* 455 */           b = 0;
/*     */         } else {
/* 457 */           b = (byte)(b | b3 << 4);
/*     */         } 
/* 459 */         b1--;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 464 */     if (paramInt == -1) {
/* 465 */       b = (byte)(b | 0xD);
/*     */     } else {
/* 467 */       b = (byte)(b | 0xC);
/*     */     } 
/* 469 */     write_octet(b);
/*     */   }
/*     */   
/*     */   public final ORB orb() {
/* 473 */     return (ORB)this.orb;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_value(Serializable paramSerializable) {
/* 479 */     write_value(paramSerializable, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_value(Serializable paramSerializable, Class paramClass) {
/* 484 */     write_value(paramSerializable);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_value(Serializable paramSerializable, String paramString) {
/*     */     try {
/* 490 */       this.os.writeObject(paramSerializable);
/* 491 */     } catch (Exception exception) {
/* 492 */       throw this.wrapper.javaSerializationException(exception, "write_value");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_value(Serializable paramSerializable, BoxedValueHelper paramBoxedValueHelper) {
/* 498 */     write_value(paramSerializable, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_abstract_interface(Object paramObject) {
/* 503 */     boolean bool = false;
/* 504 */     Object object = null;
/*     */ 
/*     */     
/* 507 */     if (paramObject != null && paramObject instanceof Object) {
/* 508 */       object = (Object)paramObject;
/* 509 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 513 */     write_boolean(bool);
/*     */ 
/*     */     
/* 516 */     if (bool) {
/* 517 */       write_Object(object);
/*     */     } else {
/*     */       try {
/* 520 */         write_value((Serializable)paramObject);
/* 521 */       } catch (ClassCastException classCastException) {
/* 522 */         if (paramObject instanceof Serializable) {
/* 523 */           throw classCastException;
/*     */         }
/* 525 */         ORBUtility.throwNotSerializableForCorba(paramObject
/* 526 */             .getClass().getName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void start_block() {
/* 535 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */   
/*     */   public final void end_block() {
/* 539 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */   
/*     */   public final void putEndian() {
/* 543 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream paramOutputStream) throws IOException {
/*     */     try {
/* 548 */       this.os.flush();
/* 549 */       this.bos.writeTo(paramOutputStream);
/* 550 */     } catch (Exception exception) {
/* 551 */       throw this.wrapper.javaSerializationException(exception, "writeTo");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final byte[] toByteArray() {
/*     */     try {
/* 557 */       this.os.flush();
/* 558 */       return this.bos.toByteArray();
/* 559 */     } catch (Exception exception) {
/* 560 */       throw this.wrapper.javaSerializationException(exception, "toByteArray");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_Abstract(Object paramObject) {
/* 567 */     write_abstract_interface(paramObject);
/*     */   }
/*     */   
/*     */   public final void write_Value(Serializable paramSerializable) {
/* 571 */     write_value(paramSerializable);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write_any_array(Any[] paramArrayOfAny, int paramInt1, int paramInt2) {
/* 576 */     for (byte b = 0; b < paramInt2; b++) {
/* 577 */       write_any(paramArrayOfAny[paramInt1 + b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String[] _truncatable_ids() {
/* 584 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getSize() {
/*     */     try {
/* 591 */       this.os.flush();
/* 592 */       return this.bos.size();
/* 593 */     } catch (Exception exception) {
/* 594 */       throw this.wrapper.javaSerializationException(exception, "write_boolean");
/*     */     } 
/*     */   }
/*     */   
/*     */   public final int getIndex() {
/* 599 */     return getSize();
/*     */   }
/*     */   
/*     */   protected int getRealIndex(int paramInt) {
/* 603 */     return getSize();
/*     */   }
/*     */   
/*     */   public final void setIndex(int paramInt) {
/* 607 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */   
/*     */   public final ByteBuffer getByteBuffer() {
/* 611 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */   
/*     */   public final void setByteBuffer(ByteBuffer paramByteBuffer) {
/* 615 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isLittleEndian() {
/* 620 */     return false;
/*     */   }
/*     */   
/*     */   public ByteBufferWithInfo getByteBufferWithInfo() {
/*     */     try {
/* 625 */       this.os.flush();
/* 626 */     } catch (Exception exception) {
/* 627 */       throw this.wrapper.javaSerializationException(exception, "getByteBufferWithInfo");
/*     */     } 
/*     */     
/* 630 */     ByteBuffer byteBuffer = ByteBuffer.wrap(this.bos.getByteArray());
/* 631 */     byteBuffer.limit(this.bos.size());
/* 632 */     return new ByteBufferWithInfo((ORB)this.orb, byteBuffer, this.bos.size());
/*     */   }
/*     */   
/*     */   public void setByteBufferWithInfo(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 636 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */   
/*     */   public final BufferManagerWrite getBufferManager() {
/* 640 */     return this.bufferManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write_fixed(BigDecimal paramBigDecimal, short paramShort1, short paramShort2) {
/* 650 */     String str2, str3, str1 = paramBigDecimal.toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 656 */     if (str1.charAt(0) == '-' || str1.charAt(0) == '+') {
/* 657 */       str1 = str1.substring(1);
/*     */     }
/*     */ 
/*     */     
/* 661 */     int i = str1.indexOf('.');
/* 662 */     if (i == -1) {
/* 663 */       str2 = str1;
/* 664 */       str3 = null;
/* 665 */     } else if (i == 0) {
/* 666 */       str2 = null;
/* 667 */       str3 = str1;
/*     */     } else {
/* 669 */       str2 = str1.substring(0, i);
/* 670 */       str3 = str1.substring(i + 1);
/*     */     } 
/*     */ 
/*     */     
/* 674 */     StringBuffer stringBuffer = new StringBuffer(paramShort1);
/* 675 */     if (str3 != null) {
/* 676 */       stringBuffer.append(str3);
/*     */     }
/* 678 */     while (stringBuffer.length() < paramShort2) {
/* 679 */       stringBuffer.append('0');
/*     */     }
/* 681 */     if (str2 != null) {
/* 682 */       stringBuffer.insert(0, str2);
/*     */     }
/* 684 */     while (stringBuffer.length() < paramShort1) {
/* 685 */       stringBuffer.insert(0, '0');
/*     */     }
/*     */ 
/*     */     
/* 689 */     write_fixed(stringBuffer.toString(), paramBigDecimal.signum());
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeOctetSequenceTo(OutputStream paramOutputStream) {
/* 694 */     byte[] arrayOfByte = toByteArray();
/* 695 */     paramOutputStream.write_long(arrayOfByte.length);
/* 696 */     paramOutputStream.write_octet_array(arrayOfByte, 0, arrayOfByte.length);
/*     */   }
/*     */   
/*     */   public final GIOPVersion getGIOPVersion() {
/* 700 */     return GIOPVersion.V1_2;
/*     */   }
/*     */   
/*     */   public final void writeIndirection(int paramInt1, int paramInt2) {
/* 704 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */   
/*     */   void freeInternalCaches() {}
/*     */   
/*     */   void printBuffer() {
/* 710 */     byte[] arrayOfByte = toByteArray();
/*     */     
/* 712 */     System.out.println("+++++++ Output Buffer ++++++++");
/* 713 */     System.out.println();
/* 714 */     System.out.println("Current position: " + arrayOfByte.length);
/*     */     
/* 716 */     System.out.println();
/*     */     
/* 718 */     char[] arrayOfChar = new char[16];
/*     */ 
/*     */     
/*     */     try {
/* 722 */       for (byte b = 0; b < arrayOfByte.length; b += 16) {
/*     */         
/* 724 */         byte b1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 730 */         while (b1 < 16 && b1 + b < arrayOfByte.length) {
/* 731 */           int i = arrayOfByte[b + b1];
/* 732 */           if (i < 0)
/* 733 */             i = 256 + i; 
/* 734 */           String str = Integer.toHexString(i);
/* 735 */           if (str.length() == 1)
/* 736 */             str = "0" + str; 
/* 737 */           System.out.print(str + " ");
/* 738 */           b1++;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 744 */         while (b1 < 16) {
/* 745 */           System.out.print("   ");
/* 746 */           b1++;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 751 */         byte b2 = 0;
/*     */         
/* 753 */         while (b2 < 16 && b2 + b < arrayOfByte.length) {
/* 754 */           if (ORBUtility.isPrintable((char)arrayOfByte[b + b2])) {
/* 755 */             arrayOfChar[b2] = (char)arrayOfByte[b + b2];
/*     */           } else {
/* 757 */             arrayOfChar[b2] = '.';
/*     */           } 
/* 759 */           b2++;
/*     */         } 
/* 761 */         System.out.println(new String(arrayOfChar, 0, b2));
/*     */       } 
/* 763 */     } catch (Throwable throwable) {
/* 764 */       throwable.printStackTrace();
/*     */     } 
/* 766 */     System.out.println("++++++++++++++++++++++++++++++");
/*     */   }
/*     */   
/*     */   public void alignOnBoundary(int paramInt) {
/* 770 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeaderPadding(boolean paramBoolean) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start_value(String paramString) {
/* 782 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */   
/*     */   public void end_value() {
/* 786 */     throw this.wrapper.giopVersionError();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/IDLJavaSerializationOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */