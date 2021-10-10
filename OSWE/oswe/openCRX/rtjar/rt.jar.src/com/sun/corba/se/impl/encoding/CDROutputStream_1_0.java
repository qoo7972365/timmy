/*      */ package com.sun.corba.se.impl.encoding;
/*      */ 
/*      */ import com.sun.corba.se.impl.corba.TypeCodeImpl;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.orbutil.CacheTable;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdUtility;
/*      */ import com.sun.corba.se.impl.util.Utility;
/*      */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*      */ import com.sun.corba.se.pept.transport.ByteBufferPool;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.IORFactories;
/*      */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*      */ import com.sun.org.omg.CORBA.portable.ValueHelper;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.math.BigDecimal;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import javax.rmi.CORBA.Util;
/*      */ import javax.rmi.CORBA.ValueHandler;
/*      */ import javax.rmi.CORBA.ValueHandlerMultiFormat;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.CompletionStatus;
/*      */ import org.omg.CORBA.Context;
/*      */ import org.omg.CORBA.ContextList;
/*      */ import org.omg.CORBA.CustomMarshal;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.Principal;
/*      */ import org.omg.CORBA.SystemException;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*      */ import org.omg.CORBA.portable.BoxedValueHelper;
/*      */ import org.omg.CORBA.portable.CustomValue;
/*      */ import org.omg.CORBA.portable.IDLEntity;
/*      */ import org.omg.CORBA.portable.OutputStream;
/*      */ import org.omg.CORBA.portable.StreamableValue;
/*      */ import org.omg.CORBA.portable.ValueBase;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CDROutputStream_1_0
/*      */   extends CDROutputStreamBase
/*      */ {
/*      */   private static final int INDIRECTION_TAG = -1;
/*      */   protected boolean littleEndian;
/*      */   protected BufferManagerWrite bufferManagerWrite;
/*      */   ByteBufferWithInfo bbwi;
/*      */   protected ORB orb;
/*      */   protected ORBUtilSystemException wrapper;
/*      */   protected boolean debug = false;
/*  113 */   protected int blockSizeIndex = -1;
/*  114 */   protected int blockSizePosition = 0;
/*      */   
/*      */   protected byte streamFormatVersion;
/*      */   
/*      */   private static final int DEFAULT_BUFFER_SIZE = 1024;
/*      */   
/*      */   private static final String kWriteMethod = "write";
/*      */   
/*  122 */   private CacheTable codebaseCache = null;
/*      */ 
/*      */   
/*  125 */   private CacheTable valueCache = null;
/*      */ 
/*      */   
/*  128 */   private CacheTable repositoryIdCache = null;
/*      */ 
/*      */   
/*  131 */   private int end_flag = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   private int chunkedValueNestingLevel = 0;
/*      */ 
/*      */   
/*      */   private boolean mustChunk = false;
/*      */ 
/*      */   
/*      */   protected boolean inBlock = false;
/*      */   
/*  146 */   private int end_flag_position = 0;
/*  147 */   private int end_flag_index = 0;
/*      */ 
/*      */   
/*  150 */   private ValueHandler valueHandler = null;
/*      */ 
/*      */   
/*      */   private RepositoryIdUtility repIdUtil;
/*      */ 
/*      */   
/*      */   private RepositoryIdStrings repIdStrs;
/*      */ 
/*      */   
/*      */   private CodeSetConversion.CTBConverter charConverter;
/*      */ 
/*      */   
/*      */   private CodeSetConversion.CTBConverter wcharConverter;
/*      */ 
/*      */   
/*      */   private static final String _id = "IDL:omg.org/CORBA/DataOutputStream:1.0";
/*      */ 
/*      */   
/*      */   public void init(ORB paramORB, boolean paramBoolean1, BufferManagerWrite paramBufferManagerWrite, byte paramByte, boolean paramBoolean2) {
/*  169 */     this.orb = (ORB)paramORB;
/*  170 */     this.wrapper = ORBUtilSystemException.get(this.orb, "rpc.encoding");
/*      */     
/*  172 */     this.debug = this.orb.transportDebugFlag;
/*      */     
/*  174 */     this.littleEndian = paramBoolean1;
/*  175 */     this.bufferManagerWrite = paramBufferManagerWrite;
/*  176 */     this.bbwi = new ByteBufferWithInfo(paramORB, paramBufferManagerWrite, paramBoolean2);
/*  177 */     this.streamFormatVersion = paramByte;
/*      */     
/*  179 */     createRepositoryIdHandlers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void init(ORB paramORB, boolean paramBoolean, BufferManagerWrite paramBufferManagerWrite, byte paramByte) {
/*  187 */     init(paramORB, paramBoolean, paramBufferManagerWrite, paramByte, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void createRepositoryIdHandlers() {
/*  192 */     this.repIdUtil = RepositoryIdFactory.getRepIdUtility();
/*  193 */     this.repIdStrs = RepositoryIdFactory.getRepIdStringsFactory();
/*      */   }
/*      */ 
/*      */   
/*      */   public BufferManagerWrite getBufferManager() {
/*  198 */     return this.bufferManagerWrite;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] toByteArray() {
/*  204 */     byte[] arrayOfByte = new byte[this.bbwi.position()];
/*      */ 
/*      */ 
/*      */     
/*  208 */     for (byte b = 0; b < this.bbwi.position(); b++) {
/*  209 */       arrayOfByte[b] = this.bbwi.byteBuffer.get(b);
/*      */     }
/*  211 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */   public GIOPVersion getGIOPVersion() {
/*  215 */     return GIOPVersion.V1_0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setHeaderPadding(boolean paramBoolean) {
/*  221 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleSpecialChunkBegin(int paramInt) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleSpecialChunkEnd() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int computeAlignment(int paramInt) {
/*  235 */     if (paramInt > 1) {
/*  236 */       int i = this.bbwi.position() & paramInt - 1;
/*  237 */       if (i != 0) {
/*  238 */         return paramInt - i;
/*      */       }
/*      */     } 
/*  241 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void alignAndReserve(int paramInt1, int paramInt2) {
/*  246 */     this.bbwi.position(this.bbwi.position() + computeAlignment(paramInt1));
/*      */     
/*  248 */     if (this.bbwi.position() + paramInt2 > this.bbwi.buflen) {
/*  249 */       grow(paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void grow(int paramInt1, int paramInt2) {
/*  259 */     this.bbwi.needed = paramInt2;
/*      */     
/*  261 */     this.bufferManagerWrite.overflow(this.bbwi);
/*      */   }
/*      */   
/*      */   public final void putEndian() throws SystemException {
/*  265 */     write_boolean(this.littleEndian);
/*      */   }
/*      */   
/*      */   public final boolean littleEndian() {
/*  269 */     return this.littleEndian;
/*      */   }
/*      */   
/*      */   void freeInternalCaches() {
/*  273 */     if (this.codebaseCache != null) {
/*  274 */       this.codebaseCache.done();
/*      */     }
/*  276 */     if (this.valueCache != null) {
/*  277 */       this.valueCache.done();
/*      */     }
/*  279 */     if (this.repositoryIdCache != null) {
/*  280 */       this.repositoryIdCache.done();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final void write_longdouble(double paramDouble) {
/*  286 */     throw this.wrapper.longDoubleNotImplemented(CompletionStatus.COMPLETED_MAYBE);
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
/*      */   public void write_octet(byte paramByte) {
/*  298 */     alignAndReserve(1, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  304 */     this.bbwi.byteBuffer.put(this.bbwi.position(), paramByte);
/*  305 */     this.bbwi.position(this.bbwi.position() + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void write_boolean(boolean paramBoolean) {
/*  311 */     write_octet(paramBoolean ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_char(char paramChar) {
/*  316 */     CodeSetConversion.CTBConverter cTBConverter = getCharConverter();
/*      */     
/*  318 */     cTBConverter.convert(paramChar);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  323 */     if (cTBConverter.getNumBytes() > 1) {
/*  324 */       throw this.wrapper.invalidSingleCharCtb(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*  326 */     write_octet(cTBConverter.getBytes()[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void writeLittleEndianWchar(char paramChar) {
/*  332 */     this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(paramChar & 0xFF));
/*  333 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(paramChar >>> 8 & 0xFF));
/*  334 */     this.bbwi.position(this.bbwi.position() + 2);
/*      */   }
/*      */   
/*      */   private final void writeBigEndianWchar(char paramChar) {
/*  338 */     this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(paramChar >>> 8 & 0xFF));
/*  339 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(paramChar & 0xFF));
/*  340 */     this.bbwi.position(this.bbwi.position() + 2);
/*      */   }
/*      */   
/*      */   private final void writeLittleEndianShort(short paramShort) {
/*  344 */     this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(paramShort & 0xFF));
/*  345 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(paramShort >>> 8 & 0xFF));
/*  346 */     this.bbwi.position(this.bbwi.position() + 2);
/*      */   }
/*      */   
/*      */   private final void writeBigEndianShort(short paramShort) {
/*  350 */     this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(paramShort >>> 8 & 0xFF));
/*  351 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(paramShort & 0xFF));
/*  352 */     this.bbwi.position(this.bbwi.position() + 2);
/*      */   }
/*      */   
/*      */   private final void writeLittleEndianLong(int paramInt) {
/*  356 */     this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(paramInt & 0xFF));
/*  357 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(paramInt >>> 8 & 0xFF));
/*  358 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 2, (byte)(paramInt >>> 16 & 0xFF));
/*  359 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 3, (byte)(paramInt >>> 24 & 0xFF));
/*  360 */     this.bbwi.position(this.bbwi.position() + 4);
/*      */   }
/*      */   
/*      */   private final void writeBigEndianLong(int paramInt) {
/*  364 */     this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(paramInt >>> 24 & 0xFF));
/*  365 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(paramInt >>> 16 & 0xFF));
/*  366 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 2, (byte)(paramInt >>> 8 & 0xFF));
/*  367 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 3, (byte)(paramInt & 0xFF));
/*  368 */     this.bbwi.position(this.bbwi.position() + 4);
/*      */   }
/*      */   
/*      */   private final void writeLittleEndianLongLong(long paramLong) {
/*  372 */     this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(int)(paramLong & 0xFFL));
/*  373 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(int)(paramLong >>> 8L & 0xFFL));
/*  374 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 2, (byte)(int)(paramLong >>> 16L & 0xFFL));
/*  375 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 3, (byte)(int)(paramLong >>> 24L & 0xFFL));
/*  376 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 4, (byte)(int)(paramLong >>> 32L & 0xFFL));
/*  377 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 5, (byte)(int)(paramLong >>> 40L & 0xFFL));
/*  378 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 6, (byte)(int)(paramLong >>> 48L & 0xFFL));
/*  379 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 7, (byte)(int)(paramLong >>> 56L & 0xFFL));
/*  380 */     this.bbwi.position(this.bbwi.position() + 8);
/*      */   }
/*      */   
/*      */   private final void writeBigEndianLongLong(long paramLong) {
/*  384 */     this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(int)(paramLong >>> 56L & 0xFFL));
/*  385 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(int)(paramLong >>> 48L & 0xFFL));
/*  386 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 2, (byte)(int)(paramLong >>> 40L & 0xFFL));
/*  387 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 3, (byte)(int)(paramLong >>> 32L & 0xFFL));
/*  388 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 4, (byte)(int)(paramLong >>> 24L & 0xFFL));
/*  389 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 5, (byte)(int)(paramLong >>> 16L & 0xFFL));
/*  390 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 6, (byte)(int)(paramLong >>> 8L & 0xFFL));
/*  391 */     this.bbwi.byteBuffer.put(this.bbwi.position() + 7, (byte)(int)(paramLong & 0xFFL));
/*  392 */     this.bbwi.position(this.bbwi.position() + 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write_wchar(char paramChar) {
/*  399 */     if (ORBUtility.isForeignORB(this.orb)) {
/*  400 */       throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */ 
/*      */     
/*  404 */     alignAndReserve(2, 2);
/*      */     
/*  406 */     if (this.littleEndian) {
/*  407 */       writeLittleEndianWchar(paramChar);
/*      */     } else {
/*  409 */       writeBigEndianWchar(paramChar);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_short(short paramShort) {
/*  415 */     alignAndReserve(2, 2);
/*      */     
/*  417 */     if (this.littleEndian) {
/*  418 */       writeLittleEndianShort(paramShort);
/*      */     } else {
/*  420 */       writeBigEndianShort(paramShort);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void write_ushort(short paramShort) {
/*  426 */     write_short(paramShort);
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_long(int paramInt) {
/*  431 */     alignAndReserve(4, 4);
/*      */     
/*  433 */     if (this.littleEndian) {
/*  434 */       writeLittleEndianLong(paramInt);
/*      */     } else {
/*  436 */       writeBigEndianLong(paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void write_ulong(int paramInt) {
/*  442 */     write_long(paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_longlong(long paramLong) {
/*  447 */     alignAndReserve(8, 8);
/*      */     
/*  449 */     if (this.littleEndian) {
/*  450 */       writeLittleEndianLongLong(paramLong);
/*      */     } else {
/*  452 */       writeBigEndianLongLong(paramLong);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void write_ulonglong(long paramLong) {
/*  458 */     write_longlong(paramLong);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void write_float(float paramFloat) {
/*  463 */     write_long(Float.floatToIntBits(paramFloat));
/*      */   }
/*      */ 
/*      */   
/*      */   public final void write_double(double paramDouble) {
/*  468 */     write_longlong(Double.doubleToLongBits(paramDouble));
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_string(String paramString) {
/*  473 */     writeString(paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected int writeString(String paramString) {
/*  478 */     if (paramString == null) {
/*  479 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/*  482 */     CodeSetConversion.CTBConverter cTBConverter = getCharConverter();
/*      */     
/*  484 */     cTBConverter.convert(paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  489 */     int i = cTBConverter.getNumBytes() + 1;
/*      */     
/*  491 */     handleSpecialChunkBegin(computeAlignment(4) + 4 + i);
/*      */     
/*  493 */     write_long(i);
/*  494 */     int j = get_offset() - 4;
/*      */     
/*  496 */     internalWriteOctetArray(cTBConverter.getBytes(), 0, cTBConverter.getNumBytes());
/*      */ 
/*      */     
/*  499 */     write_octet((byte)0);
/*      */     
/*  501 */     handleSpecialChunkEnd();
/*  502 */     return j;
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_wstring(String paramString) {
/*  507 */     if (paramString == null) {
/*  508 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */ 
/*      */     
/*  512 */     if (ORBUtility.isForeignORB(this.orb)) {
/*  513 */       throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */ 
/*      */     
/*  517 */     int i = paramString.length() + 1;
/*      */ 
/*      */     
/*  520 */     handleSpecialChunkBegin(4 + i * 2 + computeAlignment(4));
/*      */     
/*  522 */     write_long(i);
/*      */     
/*  524 */     for (byte b = 0; b < i - 1; b++) {
/*  525 */       write_wchar(paramString.charAt(b));
/*      */     }
/*      */     
/*  528 */     write_short((short)0);
/*      */ 
/*      */     
/*  531 */     handleSpecialChunkEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void internalWriteOctetArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  537 */     int i = paramInt1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  544 */     boolean bool = true;
/*      */     
/*  546 */     while (i < paramInt2 + paramInt1) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  551 */       if (this.bbwi.position() + 1 > this.bbwi.buflen || bool) {
/*  552 */         bool = false;
/*  553 */         alignAndReserve(1, 1);
/*      */       } 
/*  555 */       int j = this.bbwi.buflen - this.bbwi.position();
/*  556 */       int m = paramInt2 + paramInt1 - i;
/*  557 */       int k = (m < j) ? m : j;
/*  558 */       for (byte b = 0; b < k; b++)
/*  559 */         this.bbwi.byteBuffer.put(this.bbwi.position() + b, paramArrayOfbyte[i + b]); 
/*  560 */       this.bbwi.position(this.bbwi.position() + k);
/*  561 */       i += k;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void write_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  567 */     if (paramArrayOfbyte == null) {
/*  568 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/*  571 */     handleSpecialChunkBegin(paramInt2);
/*      */     
/*  573 */     internalWriteOctetArray(paramArrayOfbyte, paramInt1, paramInt2);
/*      */ 
/*      */     
/*  576 */     handleSpecialChunkEnd();
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_Principal(Principal paramPrincipal) {
/*  581 */     write_long((paramPrincipal.name()).length);
/*  582 */     write_octet_array(paramPrincipal.name(), 0, (paramPrincipal.name()).length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_any(Any paramAny) {
/*  587 */     if (paramAny == null) {
/*  588 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*  590 */     write_TypeCode(paramAny.type());
/*  591 */     paramAny.write_value((OutputStream)this.parent);
/*      */   }
/*      */   
/*      */   public void write_TypeCode(TypeCode paramTypeCode) {
/*      */     TypeCodeImpl typeCodeImpl;
/*  596 */     if (paramTypeCode == null) {
/*  597 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/*  600 */     if (paramTypeCode instanceof TypeCodeImpl) {
/*  601 */       typeCodeImpl = (TypeCodeImpl)paramTypeCode;
/*      */     } else {
/*      */       
/*  604 */       typeCodeImpl = new TypeCodeImpl(this.orb, paramTypeCode);
/*      */     } 
/*      */     
/*  607 */     typeCodeImpl.write_value(this.parent);
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_Object(Object paramObject) {
/*  612 */     if (paramObject == null) {
/*  613 */       IOR iOR1 = IORFactories.makeIOR(this.orb);
/*  614 */       iOR1.write(this.parent);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  619 */     if (paramObject instanceof org.omg.CORBA.LocalObject) {
/*  620 */       throw this.wrapper.writeLocalObject(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*  622 */     IOR iOR = ORBUtility.connectAndGetIOR(this.orb, paramObject);
/*  623 */     iOR.write(this.parent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write_abstract_interface(Object paramObject) {
/*  630 */     boolean bool = false;
/*  631 */     Object object = null;
/*      */ 
/*      */ 
/*      */     
/*  635 */     if (paramObject != null && paramObject instanceof Object) {
/*      */ 
/*      */ 
/*      */       
/*  639 */       object = (Object)paramObject;
/*  640 */       bool = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  645 */     write_boolean(bool);
/*      */ 
/*      */ 
/*      */     
/*  649 */     if (bool) {
/*  650 */       write_Object(object);
/*      */     } else {
/*      */       try {
/*  653 */         write_value((Serializable)paramObject);
/*  654 */       } catch (ClassCastException classCastException) {
/*  655 */         if (paramObject instanceof Serializable) {
/*  656 */           throw classCastException;
/*      */         }
/*  658 */         ORBUtility.throwNotSerializableForCorba(paramObject.getClass().getName());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_value(Serializable paramSerializable, Class paramClass) {
/*  665 */     write_value(paramSerializable);
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeWStringValue(String paramString) {
/*  670 */     int i = writeValueTag(this.mustChunk, true, null);
/*      */ 
/*      */     
/*  673 */     write_repositoryId(this.repIdStrs.getWStringValueRepId());
/*      */ 
/*      */     
/*  676 */     updateIndirectionTable(i, paramString, paramString);
/*      */ 
/*      */     
/*  679 */     if (this.mustChunk) {
/*  680 */       start_block();
/*  681 */       this.end_flag--;
/*  682 */       this.chunkedValueNestingLevel--;
/*      */     } else {
/*  684 */       this.end_flag--;
/*      */     } 
/*  686 */     write_wstring(paramString);
/*      */     
/*  688 */     if (this.mustChunk) {
/*  689 */       end_block();
/*      */     }
/*      */     
/*  692 */     writeEndTag(this.mustChunk);
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeArray(Serializable paramSerializable, Class paramClass) {
/*  697 */     if (this.valueHandler == null) {
/*  698 */       this.valueHandler = ORBUtility.createValueHandler();
/*      */     }
/*      */     
/*  701 */     int i = writeValueTag(this.mustChunk, true, 
/*  702 */         Util.getCodebase(paramClass));
/*      */ 
/*      */     
/*  705 */     write_repositoryId(this.repIdStrs.createSequenceRepID(paramClass));
/*      */ 
/*      */     
/*  708 */     updateIndirectionTable(i, paramSerializable, paramSerializable);
/*      */ 
/*      */     
/*  711 */     if (this.mustChunk) {
/*  712 */       start_block();
/*  713 */       this.end_flag--;
/*  714 */       this.chunkedValueNestingLevel--;
/*      */     } else {
/*  716 */       this.end_flag--;
/*      */     } 
/*  718 */     if (this.valueHandler instanceof ValueHandlerMultiFormat) {
/*  719 */       ValueHandlerMultiFormat valueHandlerMultiFormat = (ValueHandlerMultiFormat)this.valueHandler;
/*  720 */       valueHandlerMultiFormat.writeValue((OutputStream)this.parent, paramSerializable, this.streamFormatVersion);
/*      */     } else {
/*  722 */       this.valueHandler.writeValue((OutputStream)this.parent, paramSerializable);
/*      */     } 
/*  724 */     if (this.mustChunk) {
/*  725 */       end_block();
/*      */     }
/*      */     
/*  728 */     writeEndTag(this.mustChunk);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeValueBase(ValueBase paramValueBase, Class paramClass) {
/*  734 */     this.mustChunk = true;
/*      */ 
/*      */     
/*  737 */     int i = writeValueTag(true, true, Util.getCodebase(paramClass));
/*      */ 
/*      */     
/*  740 */     String str = paramValueBase._truncatable_ids()[0];
/*      */ 
/*      */     
/*  743 */     write_repositoryId(str);
/*      */ 
/*      */     
/*  746 */     updateIndirectionTable(i, paramValueBase, paramValueBase);
/*      */ 
/*      */     
/*  749 */     start_block();
/*  750 */     this.end_flag--;
/*  751 */     this.chunkedValueNestingLevel--;
/*  752 */     writeIDLValue((Serializable)paramValueBase, str);
/*  753 */     end_block();
/*      */ 
/*      */     
/*  756 */     writeEndTag(true);
/*      */   }
/*      */   
/*      */   private void writeRMIIIOPValueType(Serializable paramSerializable, Class<?> paramClass) {
/*  760 */     if (this.valueHandler == null) {
/*  761 */       this.valueHandler = ORBUtility.createValueHandler();
/*      */     }
/*  763 */     Serializable serializable = paramSerializable;
/*      */ 
/*      */ 
/*      */     
/*  767 */     paramSerializable = this.valueHandler.writeReplace(serializable);
/*      */     
/*  769 */     if (paramSerializable == null) {
/*      */       
/*  771 */       write_long(0);
/*      */       
/*      */       return;
/*      */     } 
/*  775 */     if (paramSerializable != serializable) {
/*  776 */       if (this.valueCache != null && this.valueCache.containsKey(paramSerializable)) {
/*  777 */         writeIndirection(-1, this.valueCache.getVal(paramSerializable));
/*      */         
/*      */         return;
/*      */       } 
/*  781 */       paramClass = paramSerializable.getClass();
/*      */     } 
/*      */     
/*  784 */     if (this.mustChunk || this.valueHandler.isCustomMarshaled(paramClass)) {
/*  785 */       this.mustChunk = true;
/*      */     }
/*      */ 
/*      */     
/*  789 */     int i = writeValueTag(this.mustChunk, true, Util.getCodebase(paramClass));
/*      */ 
/*      */     
/*  792 */     write_repositoryId(this.repIdStrs.createForJavaType(paramClass));
/*      */ 
/*      */     
/*  795 */     updateIndirectionTable(i, paramSerializable, serializable);
/*      */     
/*  797 */     if (this.mustChunk) {
/*      */       
/*  799 */       this.end_flag--;
/*  800 */       this.chunkedValueNestingLevel--;
/*  801 */       start_block();
/*      */     } else {
/*  803 */       this.end_flag--;
/*      */     } 
/*  805 */     if (this.valueHandler instanceof ValueHandlerMultiFormat) {
/*  806 */       ValueHandlerMultiFormat valueHandlerMultiFormat = (ValueHandlerMultiFormat)this.valueHandler;
/*  807 */       valueHandlerMultiFormat.writeValue((OutputStream)this.parent, paramSerializable, this.streamFormatVersion);
/*      */     } else {
/*  809 */       this.valueHandler.writeValue((OutputStream)this.parent, paramSerializable);
/*      */     } 
/*  811 */     if (this.mustChunk) {
/*  812 */       end_block();
/*      */     }
/*      */     
/*  815 */     writeEndTag(this.mustChunk);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void write_value(Serializable paramSerializable, String paramString) {
/*  821 */     if (paramSerializable == null) {
/*      */       
/*  823 */       write_long(0);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  828 */     if (this.valueCache != null && this.valueCache.containsKey(paramSerializable)) {
/*  829 */       writeIndirection(-1, this.valueCache.getVal(paramSerializable));
/*      */       
/*      */       return;
/*      */     } 
/*  833 */     Class<?> clazz = paramSerializable.getClass();
/*  834 */     boolean bool = this.mustChunk;
/*      */     
/*  836 */     if (this.mustChunk) {
/*  837 */       this.mustChunk = true;
/*      */     }
/*  839 */     if (this.inBlock) {
/*  840 */       end_block();
/*      */     }
/*  842 */     if (clazz.isArray()) {
/*      */       
/*  844 */       writeArray(paramSerializable, clazz);
/*  845 */     } else if (paramSerializable instanceof ValueBase) {
/*      */       
/*  847 */       writeValueBase((ValueBase)paramSerializable, clazz);
/*  848 */     } else if (shouldWriteAsIDLEntity(paramSerializable)) {
/*  849 */       writeIDLEntity((IDLEntity)paramSerializable);
/*  850 */     } else if (paramSerializable instanceof String) {
/*  851 */       writeWStringValue((String)paramSerializable);
/*  852 */     } else if (paramSerializable instanceof Class) {
/*  853 */       writeClass(paramString, (Class)paramSerializable);
/*      */     } else {
/*      */       
/*  856 */       writeRMIIIOPValueType(paramSerializable, clazz);
/*      */     } 
/*      */     
/*  859 */     this.mustChunk = bool;
/*      */ 
/*      */ 
/*      */     
/*  863 */     if (this.mustChunk) {
/*  864 */       start_block();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_value(Serializable paramSerializable) {
/*  870 */     write_value(paramSerializable, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void write_value(Serializable paramSerializable, BoxedValueHelper paramBoxedValueHelper) {
/*  876 */     if (paramSerializable == null) {
/*      */       
/*  878 */       write_long(0);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  883 */     if (this.valueCache != null && this.valueCache.containsKey(paramSerializable)) {
/*  884 */       writeIndirection(-1, this.valueCache.getVal(paramSerializable));
/*      */       
/*      */       return;
/*      */     } 
/*  888 */     boolean bool = this.mustChunk;
/*      */     
/*  890 */     boolean bool1 = false;
/*  891 */     if (paramBoxedValueHelper instanceof ValueHelper) {
/*      */       boolean bool2;
/*      */       try {
/*  894 */         bool2 = ((ValueHelper)paramBoxedValueHelper).get_type().type_modifier();
/*  895 */       } catch (BadKind badKind) {
/*  896 */         bool2 = false;
/*      */       } 
/*  898 */       if (paramSerializable instanceof CustomMarshal && bool2 == true) {
/*      */         
/*  900 */         bool1 = true;
/*  901 */         this.mustChunk = true;
/*      */       } 
/*  903 */       if (bool2 == 3) {
/*  904 */         this.mustChunk = true;
/*      */       }
/*      */     } 
/*  907 */     if (this.mustChunk) {
/*      */       
/*  909 */       if (this.inBlock) {
/*  910 */         end_block();
/*      */       }
/*      */       
/*  913 */       int i = writeValueTag(true, this.orb
/*  914 */           .getORBData().useRepId(), 
/*  915 */           Util.getCodebase(paramSerializable.getClass()));
/*      */ 
/*      */       
/*  918 */       if (this.orb.getORBData().useRepId()) {
/*  919 */         write_repositoryId(paramBoxedValueHelper.get_id());
/*      */       }
/*      */ 
/*      */       
/*  923 */       updateIndirectionTable(i, paramSerializable, paramSerializable);
/*      */ 
/*      */       
/*  926 */       start_block();
/*  927 */       this.end_flag--;
/*  928 */       this.chunkedValueNestingLevel--;
/*  929 */       if (bool1) {
/*  930 */         ((CustomMarshal)paramSerializable).marshal(this.parent);
/*      */       } else {
/*  932 */         paramBoxedValueHelper.write_value((OutputStream)this.parent, paramSerializable);
/*  933 */       }  end_block();
/*      */ 
/*      */       
/*  936 */       writeEndTag(true);
/*      */     }
/*      */     else {
/*      */       
/*  940 */       int i = writeValueTag(false, this.orb
/*  941 */           .getORBData().useRepId(), 
/*  942 */           Util.getCodebase(paramSerializable.getClass()));
/*      */ 
/*      */       
/*  945 */       if (this.orb.getORBData().useRepId()) {
/*  946 */         write_repositoryId(paramBoxedValueHelper.get_id());
/*      */       }
/*      */ 
/*      */       
/*  950 */       updateIndirectionTable(i, paramSerializable, paramSerializable);
/*      */ 
/*      */       
/*  953 */       this.end_flag--;
/*      */       
/*  955 */       paramBoxedValueHelper.write_value((OutputStream)this.parent, paramSerializable);
/*      */ 
/*      */       
/*  958 */       writeEndTag(false);
/*      */     } 
/*      */     
/*  961 */     this.mustChunk = bool;
/*      */ 
/*      */ 
/*      */     
/*  965 */     if (this.mustChunk) {
/*  966 */       start_block();
/*      */     }
/*      */   }
/*      */   
/*      */   public int get_offset() {
/*  971 */     return this.bbwi.position();
/*      */   }
/*      */   
/*      */   public void start_block() {
/*  975 */     if (this.debug) {
/*  976 */       dprint("CDROutputStream_1_0 start_block, position" + this.bbwi.position());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  985 */     write_long(0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  990 */     this.inBlock = true;
/*      */     
/*  992 */     this.blockSizePosition = get_offset();
/*      */ 
/*      */     
/*  995 */     this.blockSizeIndex = this.bbwi.position();
/*      */     
/*  997 */     if (this.debug) {
/*  998 */       dprint("CDROutputStream_1_0 start_block, blockSizeIndex " + this.blockSizeIndex);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeLongWithoutAlign(int paramInt) {
/* 1009 */     if (this.littleEndian) {
/* 1010 */       writeLittleEndianLong(paramInt);
/*      */     } else {
/* 1012 */       writeBigEndianLong(paramInt);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void end_block() {
/* 1017 */     if (this.debug) {
/* 1018 */       dprint("CDROutputStream_1_0.java end_block");
/*      */     }
/*      */     
/* 1021 */     if (!this.inBlock) {
/*      */       return;
/*      */     }
/* 1024 */     if (this.debug) {
/* 1025 */       dprint("CDROutputStream_1_0.java end_block, in a block");
/*      */     }
/*      */     
/* 1028 */     this.inBlock = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1034 */     if (get_offset() == this.blockSizePosition) {
/*      */ 
/*      */       
/* 1037 */       this.bbwi.position(this.bbwi.position() - 4);
/* 1038 */       this.blockSizeIndex = -1;
/* 1039 */       this.blockSizePosition = -1;
/*      */       
/*      */       return;
/*      */     } 
/* 1043 */     int i = this.bbwi.position();
/* 1044 */     this.bbwi.position(this.blockSizeIndex - 4);
/*      */     
/* 1046 */     writeLongWithoutAlign(i - this.blockSizeIndex);
/*      */     
/* 1048 */     this.bbwi.position(i);
/* 1049 */     this.blockSizeIndex = -1;
/* 1050 */     this.blockSizePosition = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ORB orb() {
/* 1057 */     return (ORB)this.orb;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void write_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) {
/* 1063 */     if (paramArrayOfboolean == null) {
/* 1064 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/* 1067 */     handleSpecialChunkBegin(paramInt2);
/*      */     
/* 1069 */     for (byte b = 0; b < paramInt2; b++) {
/* 1070 */       write_boolean(paramArrayOfboolean[paramInt1 + b]);
/*      */     }
/*      */     
/* 1073 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   public final void write_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 1077 */     if (paramArrayOfchar == null) {
/* 1078 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/* 1081 */     handleSpecialChunkBegin(paramInt2);
/*      */     
/* 1083 */     for (byte b = 0; b < paramInt2; b++) {
/* 1084 */       write_char(paramArrayOfchar[paramInt1 + b]);
/*      */     }
/*      */     
/* 1087 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   public void write_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 1091 */     if (paramArrayOfchar == null) {
/* 1092 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/* 1095 */     handleSpecialChunkBegin(computeAlignment(2) + paramInt2 * 2);
/*      */     
/* 1097 */     for (byte b = 0; b < paramInt2; b++) {
/* 1098 */       write_wchar(paramArrayOfchar[paramInt1 + b]);
/*      */     }
/*      */     
/* 1101 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   public final void write_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 1105 */     if (paramArrayOfshort == null) {
/* 1106 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/* 1109 */     handleSpecialChunkBegin(computeAlignment(2) + paramInt2 * 2);
/*      */     
/* 1111 */     for (byte b = 0; b < paramInt2; b++) {
/* 1112 */       write_short(paramArrayOfshort[paramInt1 + b]);
/*      */     }
/*      */     
/* 1115 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   public final void write_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 1119 */     write_short_array(paramArrayOfshort, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public final void write_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 1123 */     if (paramArrayOfint == null) {
/* 1124 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/* 1127 */     handleSpecialChunkBegin(computeAlignment(4) + paramInt2 * 4);
/*      */     
/* 1129 */     for (byte b = 0; b < paramInt2; b++) {
/* 1130 */       write_long(paramArrayOfint[paramInt1 + b]);
/*      */     }
/*      */     
/* 1133 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   public final void write_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 1137 */     write_long_array(paramArrayOfint, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public final void write_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 1141 */     if (paramArrayOflong == null) {
/* 1142 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/* 1145 */     handleSpecialChunkBegin(computeAlignment(8) + paramInt2 * 8);
/*      */     
/* 1147 */     for (byte b = 0; b < paramInt2; b++) {
/* 1148 */       write_longlong(paramArrayOflong[paramInt1 + b]);
/*      */     }
/*      */     
/* 1151 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   public final void write_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 1155 */     write_longlong_array(paramArrayOflong, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public final void write_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 1159 */     if (paramArrayOffloat == null) {
/* 1160 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/* 1163 */     handleSpecialChunkBegin(computeAlignment(4) + paramInt2 * 4);
/*      */     
/* 1165 */     for (byte b = 0; b < paramInt2; b++) {
/* 1166 */       write_float(paramArrayOffloat[paramInt1 + b]);
/*      */     }
/*      */     
/* 1169 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   public final void write_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 1173 */     if (paramArrayOfdouble == null) {
/* 1174 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/* 1177 */     handleSpecialChunkBegin(computeAlignment(8) + paramInt2 * 8);
/*      */     
/* 1179 */     for (byte b = 0; b < paramInt2; b++) {
/* 1180 */       write_double(paramArrayOfdouble[paramInt1 + b]);
/*      */     }
/*      */     
/* 1183 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   public void write_string_array(String[] paramArrayOfString, int paramInt1, int paramInt2) {
/* 1187 */     if (paramArrayOfString == null) {
/* 1188 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/* 1190 */     for (byte b = 0; b < paramInt2; b++)
/* 1191 */       write_string(paramArrayOfString[paramInt1 + b]); 
/*      */   }
/*      */   
/*      */   public void write_wstring_array(String[] paramArrayOfString, int paramInt1, int paramInt2) {
/* 1195 */     if (paramArrayOfString == null) {
/* 1196 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/* 1198 */     for (byte b = 0; b < paramInt2; b++) {
/* 1199 */       write_wstring(paramArrayOfString[paramInt1 + b]);
/*      */     }
/*      */   }
/*      */   
/*      */   public final void write_any_array(Any[] paramArrayOfAny, int paramInt1, int paramInt2) {
/* 1204 */     for (byte b = 0; b < paramInt2; b++) {
/* 1205 */       write_any(paramArrayOfAny[paramInt1 + b]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(OutputStream paramOutputStream) throws IOException {
/* 1215 */     byte[] arrayOfByte = null;
/*      */     
/* 1217 */     if (this.bbwi.byteBuffer.hasArray()) {
/*      */       
/* 1219 */       arrayOfByte = this.bbwi.byteBuffer.array();
/*      */     }
/*      */     else {
/*      */       
/* 1223 */       int i = this.bbwi.position();
/* 1224 */       arrayOfByte = new byte[i];
/*      */ 
/*      */       
/* 1227 */       for (byte b = 0; b < i; b++) {
/* 1228 */         arrayOfByte[b] = this.bbwi.byteBuffer.get(b);
/*      */       }
/*      */     } 
/* 1231 */     paramOutputStream.write(arrayOfByte, 0, this.bbwi.position());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeOctetSequenceTo(OutputStream paramOutputStream) {
/* 1236 */     byte[] arrayOfByte = null;
/*      */     
/* 1238 */     if (this.bbwi.byteBuffer.hasArray()) {
/*      */       
/* 1240 */       arrayOfByte = this.bbwi.byteBuffer.array();
/*      */     }
/*      */     else {
/*      */       
/* 1244 */       int i = this.bbwi.position();
/* 1245 */       arrayOfByte = new byte[i];
/*      */ 
/*      */       
/* 1248 */       for (byte b = 0; b < i; b++) {
/* 1249 */         arrayOfByte[b] = this.bbwi.byteBuffer.get(b);
/*      */       }
/*      */     } 
/* 1252 */     paramOutputStream.write_long(this.bbwi.position());
/* 1253 */     paramOutputStream.write_octet_array(arrayOfByte, 0, this.bbwi.position());
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getSize() {
/* 1258 */     return this.bbwi.position();
/*      */   }
/*      */   
/*      */   public int getIndex() {
/* 1262 */     return this.bbwi.position();
/*      */   }
/*      */   
/*      */   public boolean isLittleEndian() {
/* 1266 */     return this.littleEndian;
/*      */   }
/*      */   
/*      */   public void setIndex(int paramInt) {
/* 1270 */     this.bbwi.position(paramInt);
/*      */   }
/*      */   
/*      */   public ByteBufferWithInfo getByteBufferWithInfo() {
/* 1274 */     return this.bbwi;
/*      */   }
/*      */   
/*      */   public void setByteBufferWithInfo(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 1278 */     this.bbwi = paramByteBufferWithInfo;
/*      */   }
/*      */   
/*      */   public ByteBuffer getByteBuffer() {
/* 1282 */     ByteBuffer byteBuffer = null;
/* 1283 */     if (this.bbwi != null) {
/* 1284 */       byteBuffer = this.bbwi.byteBuffer;
/*      */     }
/* 1286 */     return byteBuffer;
/*      */   }
/*      */   
/*      */   public void setByteBuffer(ByteBuffer paramByteBuffer) {
/* 1290 */     this.bbwi.byteBuffer = paramByteBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void updateIndirectionTable(int paramInt, Object paramObject1, Object paramObject2) {
/* 1296 */     if (this.valueCache == null)
/* 1297 */       this.valueCache = new CacheTable(this.orb, true); 
/* 1298 */     this.valueCache.put(paramObject1, paramInt);
/* 1299 */     if (paramObject2 != paramObject1) {
/* 1300 */       this.valueCache.put(paramObject2, paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */   private final void write_repositoryId(String paramString) {
/* 1305 */     if (this.repositoryIdCache != null && this.repositoryIdCache.containsKey(paramString)) {
/* 1306 */       writeIndirection(-1, this.repositoryIdCache.getVal(paramString));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1316 */     int i = writeString(paramString);
/*      */ 
/*      */     
/* 1319 */     if (this.repositoryIdCache == null)
/* 1320 */       this.repositoryIdCache = new CacheTable(this.orb, true); 
/* 1321 */     this.repositoryIdCache.put(paramString, i);
/*      */   }
/*      */   
/*      */   private void write_codebase(String paramString, int paramInt) {
/* 1325 */     if (this.codebaseCache != null && this.codebaseCache.containsKey(paramString)) {
/* 1326 */       writeIndirection(-1, this.codebaseCache.getVal(paramString));
/*      */     } else {
/*      */       
/* 1329 */       write_string(paramString);
/* 1330 */       if (this.codebaseCache == null)
/* 1331 */         this.codebaseCache = new CacheTable(this.orb, true); 
/* 1332 */       this.codebaseCache.put(paramString, paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final int writeValueTag(boolean paramBoolean1, boolean paramBoolean2, String paramString) {
/* 1338 */     int i = 0;
/* 1339 */     if (paramBoolean1 && !paramBoolean2) {
/* 1340 */       if (paramString == null) {
/* 1341 */         write_long(this.repIdUtil.getStandardRMIChunkedNoRepStrId());
/* 1342 */         i = get_offset() - 4;
/*      */       } else {
/* 1344 */         write_long(this.repIdUtil.getCodeBaseRMIChunkedNoRepStrId());
/* 1345 */         i = get_offset() - 4;
/* 1346 */         write_codebase(paramString, get_offset());
/*      */       } 
/* 1348 */     } else if (paramBoolean1 && paramBoolean2) {
/* 1349 */       if (paramString == null) {
/* 1350 */         write_long(this.repIdUtil.getStandardRMIChunkedId());
/* 1351 */         i = get_offset() - 4;
/*      */       } else {
/* 1353 */         write_long(this.repIdUtil.getCodeBaseRMIChunkedId());
/* 1354 */         i = get_offset() - 4;
/* 1355 */         write_codebase(paramString, get_offset());
/*      */       } 
/* 1357 */     } else if (!paramBoolean1 && !paramBoolean2) {
/* 1358 */       if (paramString == null) {
/* 1359 */         write_long(this.repIdUtil.getStandardRMIUnchunkedNoRepStrId());
/* 1360 */         i = get_offset() - 4;
/*      */       } else {
/* 1362 */         write_long(this.repIdUtil.getCodeBaseRMIUnchunkedNoRepStrId());
/* 1363 */         i = get_offset() - 4;
/* 1364 */         write_codebase(paramString, get_offset());
/*      */       } 
/* 1366 */     } else if (!paramBoolean1 && paramBoolean2) {
/* 1367 */       if (paramString == null) {
/* 1368 */         write_long(this.repIdUtil.getStandardRMIUnchunkedId());
/* 1369 */         i = get_offset() - 4;
/*      */       } else {
/* 1371 */         write_long(this.repIdUtil.getCodeBaseRMIUnchunkedId());
/* 1372 */         i = get_offset() - 4;
/* 1373 */         write_codebase(paramString, get_offset());
/*      */       } 
/*      */     } 
/* 1376 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeIDLValue(Serializable paramSerializable, String paramString) {
/* 1381 */     if (paramSerializable instanceof StreamableValue) {
/* 1382 */       ((StreamableValue)paramSerializable)._write((OutputStream)this.parent);
/*      */     }
/* 1384 */     else if (paramSerializable instanceof CustomValue) {
/* 1385 */       ((CustomValue)paramSerializable).marshal(this.parent);
/*      */     } else {
/*      */       
/* 1388 */       BoxedValueHelper boxedValueHelper = Utility.getHelper(paramSerializable.getClass(), null, paramString);
/* 1389 */       boolean bool = false;
/* 1390 */       if (boxedValueHelper instanceof ValueHelper && paramSerializable instanceof CustomMarshal) {
/*      */         try {
/* 1392 */           if (((ValueHelper)boxedValueHelper).get_type().type_modifier() == 1)
/* 1393 */             bool = true; 
/* 1394 */         } catch (BadKind badKind) {
/* 1395 */           throw this.wrapper.badTypecodeForCustomValue(CompletionStatus.COMPLETED_MAYBE, badKind);
/*      */         } 
/*      */       }
/*      */       
/* 1399 */       if (bool) {
/* 1400 */         ((CustomMarshal)paramSerializable).marshal(this.parent);
/*      */       } else {
/* 1402 */         boxedValueHelper.write_value((OutputStream)this.parent, paramSerializable);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeEndTag(boolean paramBoolean) {
/* 1409 */     if (paramBoolean) {
/* 1410 */       if (get_offset() == this.end_flag_position)
/*      */       {
/* 1412 */         if (this.bbwi.position() == this.end_flag_index)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1417 */           this.bbwi.position(this.bbwi.position() - 4);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1430 */       writeNestingLevel();
/*      */ 
/*      */       
/* 1433 */       this.end_flag_index = this.bbwi.position();
/* 1434 */       this.end_flag_position = get_offset();
/*      */       
/* 1436 */       this.chunkedValueNestingLevel++;
/*      */     } 
/*      */ 
/*      */     
/* 1440 */     this.end_flag++;
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
/*      */   private void writeNestingLevel() {
/* 1456 */     if (this.orb == null || 
/* 1457 */       ORBVersionFactory.getFOREIGN().equals(this.orb.getORBVersion()) || 
/* 1458 */       ORBVersionFactory.getNEWER().compareTo(this.orb.getORBVersion()) <= 0) {
/*      */       
/* 1460 */       write_long(this.chunkedValueNestingLevel);
/*      */     } else {
/*      */       
/* 1463 */       write_long(this.end_flag);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeClass(String paramString, Class paramClass) {
/* 1469 */     if (paramString == null) {
/* 1470 */       paramString = this.repIdStrs.getClassDescValueRepId();
/*      */     }
/*      */     
/* 1473 */     int i = writeValueTag(this.mustChunk, true, null);
/* 1474 */     updateIndirectionTable(i, paramClass, paramClass);
/*      */     
/* 1476 */     write_repositoryId(paramString);
/*      */     
/* 1478 */     if (this.mustChunk) {
/*      */       
/* 1480 */       start_block();
/* 1481 */       this.end_flag--;
/* 1482 */       this.chunkedValueNestingLevel--;
/*      */     } else {
/* 1484 */       this.end_flag--;
/*      */     } 
/* 1486 */     writeClassBody(paramClass);
/*      */     
/* 1488 */     if (this.mustChunk) {
/* 1489 */       end_block();
/*      */     }
/*      */     
/* 1492 */     writeEndTag(this.mustChunk);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeClassBody(Class paramClass) {
/* 1499 */     if (this.orb == null || 
/* 1500 */       ORBVersionFactory.getFOREIGN().equals(this.orb.getORBVersion()) || 
/* 1501 */       ORBVersionFactory.getNEWER().compareTo(this.orb.getORBVersion()) <= 0) {
/*      */       
/* 1503 */       write_value(Util.getCodebase(paramClass));
/* 1504 */       write_value(this.repIdStrs.createForAnyType(paramClass));
/*      */     } else {
/*      */       
/* 1507 */       write_value(this.repIdStrs.createForAnyType(paramClass));
/* 1508 */       write_value(Util.getCodebase(paramClass));
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
/*      */   private boolean shouldWriteAsIDLEntity(Serializable paramSerializable) {
/* 1522 */     return (paramSerializable instanceof IDLEntity && !(paramSerializable instanceof ValueBase) && !(paramSerializable instanceof Object));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeIDLEntity(IDLEntity paramIDLEntity) {
/* 1530 */     this.mustChunk = true;
/*      */     
/* 1532 */     String str1 = this.repIdStrs.createForJavaType((Serializable)paramIDLEntity);
/* 1533 */     Class<?> clazz = paramIDLEntity.getClass();
/* 1534 */     String str2 = Util.getCodebase(clazz);
/*      */ 
/*      */     
/* 1537 */     int i = writeValueTag(true, true, str2);
/* 1538 */     updateIndirectionTable(i, paramIDLEntity, paramIDLEntity);
/*      */ 
/*      */     
/* 1541 */     write_repositoryId(str1);
/*      */ 
/*      */     
/* 1544 */     this.end_flag--;
/* 1545 */     this.chunkedValueNestingLevel--;
/* 1546 */     start_block();
/*      */ 
/*      */     
/*      */     try {
/* 1550 */       ClassLoader classLoader = (clazz == null) ? null : clazz.getClassLoader();
/* 1551 */       final Class helperClass = Utility.loadClassForClass(clazz.getName() + "Helper", str2, classLoader, clazz, classLoader);
/*      */       
/* 1553 */       final Class[] argTypes = { OutputStream.class, clazz };
/*      */ 
/*      */       
/* 1556 */       Method method = null;
/*      */       try {
/* 1558 */         method = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*      */             {
/*      */               public Object run() throws NoSuchMethodException {
/* 1561 */                 return helperClass.getDeclaredMethod("write", argTypes);
/*      */               }
/*      */             });
/*      */       }
/* 1565 */       catch (PrivilegedActionException privilegedActionException) {
/*      */         
/* 1567 */         throw (NoSuchMethodException)privilegedActionException.getException();
/*      */       } 
/* 1569 */       Object[] arrayOfObject = { this.parent, paramIDLEntity };
/* 1570 */       method.invoke(null, arrayOfObject);
/* 1571 */     } catch (ClassNotFoundException classNotFoundException) {
/* 1572 */       throw this.wrapper.errorInvokingHelperWrite(CompletionStatus.COMPLETED_MAYBE, classNotFoundException);
/* 1573 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 1574 */       throw this.wrapper.errorInvokingHelperWrite(CompletionStatus.COMPLETED_MAYBE, noSuchMethodException);
/* 1575 */     } catch (IllegalAccessException illegalAccessException) {
/* 1576 */       throw this.wrapper.errorInvokingHelperWrite(CompletionStatus.COMPLETED_MAYBE, illegalAccessException);
/* 1577 */     } catch (InvocationTargetException invocationTargetException) {
/* 1578 */       throw this.wrapper.errorInvokingHelperWrite(CompletionStatus.COMPLETED_MAYBE, invocationTargetException);
/*      */     } 
/* 1580 */     end_block();
/*      */ 
/*      */     
/* 1583 */     writeEndTag(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void write_Abstract(Object paramObject) {
/* 1589 */     write_abstract_interface(paramObject);
/*      */   }
/*      */   
/*      */   public void write_Value(Serializable paramSerializable) {
/* 1593 */     write_value(paramSerializable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write_fixed(BigDecimal paramBigDecimal, short paramShort1, short paramShort2) {
/* 1602 */     String str2, str3, str1 = paramBigDecimal.toString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1608 */     if (str1.charAt(0) == '-' || str1.charAt(0) == '+') {
/* 1609 */       str1 = str1.substring(1);
/*      */     }
/*      */ 
/*      */     
/* 1613 */     int i = str1.indexOf('.');
/* 1614 */     if (i == -1) {
/* 1615 */       str2 = str1;
/* 1616 */       str3 = null;
/* 1617 */     } else if (i == 0) {
/* 1618 */       str2 = null;
/* 1619 */       str3 = str1;
/*      */     } else {
/* 1621 */       str2 = str1.substring(0, i);
/* 1622 */       str3 = str1.substring(i + 1);
/*      */     } 
/*      */ 
/*      */     
/* 1626 */     StringBuffer stringBuffer = new StringBuffer(paramShort1);
/* 1627 */     if (str3 != null) {
/* 1628 */       stringBuffer.append(str3);
/*      */     }
/* 1630 */     while (stringBuffer.length() < paramShort2) {
/* 1631 */       stringBuffer.append('0');
/*      */     }
/* 1633 */     if (str2 != null) {
/* 1634 */       stringBuffer.insert(0, str2);
/*      */     }
/* 1636 */     while (stringBuffer.length() < paramShort1) {
/* 1637 */       stringBuffer.insert(0, '0');
/*      */     }
/*      */ 
/*      */     
/* 1641 */     write_fixed(stringBuffer.toString(), paramBigDecimal.signum());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write_fixed(BigDecimal paramBigDecimal) {
/* 1648 */     write_fixed(paramBigDecimal.toString(), paramBigDecimal.signum());
/*      */   }
/*      */ 
/*      */   
/*      */   public void write_fixed(String paramString, int paramInt) {
/* 1653 */     int i = paramString.length();
/*      */     
/* 1655 */     byte b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1660 */     byte b1 = 0; byte b2;
/* 1661 */     for (b2 = 0; b2 < i; b2++) {
/* 1662 */       char c = paramString.charAt(b2);
/* 1663 */       if (c != '-' && c != '+' && c != '.')
/*      */       {
/* 1665 */         b1++; } 
/*      */     } 
/* 1667 */     for (b2 = 0; b2 < i; b2++) {
/* 1668 */       char c = paramString.charAt(b2);
/* 1669 */       if (c != '-' && c != '+' && c != '.') {
/*      */         
/* 1671 */         byte b3 = (byte)Character.digit(c, 10);
/* 1672 */         if (b3 == -1) {
/* 1673 */           throw this.wrapper.badDigitInFixed(CompletionStatus.COMPLETED_MAYBE);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1679 */         if (b1 % 2 == 0) {
/* 1680 */           b = (byte)(b | b3);
/* 1681 */           write_octet(b);
/* 1682 */           b = 0;
/*      */         } else {
/* 1684 */           b = (byte)(b | b3 << 4);
/*      */         } 
/* 1686 */         b1--;
/*      */       } 
/*      */     } 
/*      */     
/* 1690 */     if (paramInt == -1) {
/* 1691 */       b = (byte)(b | 0xD);
/*      */     } else {
/* 1693 */       b = (byte)(b | 0xC);
/*      */     } 
/* 1695 */     write_octet(b);
/*      */   }
/*      */ 
/*      */   
/* 1699 */   private static final String[] _ids = new String[] { "IDL:omg.org/CORBA/DataOutputStream:1.0" };
/*      */   
/*      */   public String[] _truncatable_ids() {
/* 1702 */     if (_ids == null) {
/* 1703 */       return null;
/*      */     }
/* 1705 */     return (String[])_ids.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void printBuffer() {
/* 1711 */     printBuffer(this.bbwi);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void printBuffer(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 1716 */     System.out.println("+++++++ Output Buffer ++++++++");
/* 1717 */     System.out.println();
/* 1718 */     System.out.println("Current position: " + paramByteBufferWithInfo.position());
/* 1719 */     System.out.println("Total length : " + paramByteBufferWithInfo.buflen);
/* 1720 */     System.out.println();
/*      */     
/* 1722 */     char[] arrayOfChar = new char[16];
/*      */ 
/*      */     
/*      */     try {
/* 1726 */       for (byte b = 0; b < paramByteBufferWithInfo.position(); b += 16) {
/*      */         
/* 1728 */         byte b1 = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1734 */         while (b1 < 16 && b1 + b < paramByteBufferWithInfo.position()) {
/* 1735 */           int i = paramByteBufferWithInfo.byteBuffer.get(b + b1);
/* 1736 */           if (i < 0)
/* 1737 */             i = 256 + i; 
/* 1738 */           String str = Integer.toHexString(i);
/* 1739 */           if (str.length() == 1)
/* 1740 */             str = "0" + str; 
/* 1741 */           System.out.print(str + " ");
/* 1742 */           b1++;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1748 */         while (b1 < 16) {
/* 1749 */           System.out.print("   ");
/* 1750 */           b1++;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1755 */         byte b2 = 0;
/*      */         
/* 1757 */         while (b2 < 16 && b2 + b < paramByteBufferWithInfo.position()) {
/* 1758 */           if (ORBUtility.isPrintable((char)paramByteBufferWithInfo.byteBuffer.get(b + b2))) {
/* 1759 */             arrayOfChar[b2] = (char)paramByteBufferWithInfo.byteBuffer.get(b + b2);
/*      */           } else {
/* 1761 */             arrayOfChar[b2] = '.';
/* 1762 */           }  b2++;
/*      */         } 
/* 1764 */         System.out.println(new String(arrayOfChar, 0, b2));
/*      */       } 
/* 1766 */     } catch (Throwable throwable) {
/* 1767 */       throwable.printStackTrace();
/*      */     } 
/* 1769 */     System.out.println("++++++++++++++++++++++++++++++");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeIndirection(int paramInt1, int paramInt2) {
/* 1780 */     handleSpecialChunkBegin(computeAlignment(4) + 8);
/*      */ 
/*      */     
/* 1783 */     write_long(paramInt1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1792 */     write_long(paramInt2 - this.parent.getRealIndex(get_offset()));
/*      */     
/* 1794 */     handleSpecialChunkEnd();
/*      */   }
/*      */   
/*      */   protected CodeSetConversion.CTBConverter getCharConverter() {
/* 1798 */     if (this.charConverter == null) {
/* 1799 */       this.charConverter = this.parent.createCharCTBConverter();
/*      */     }
/* 1801 */     return this.charConverter;
/*      */   }
/*      */   
/*      */   protected CodeSetConversion.CTBConverter getWCharConverter() {
/* 1805 */     if (this.wcharConverter == null) {
/* 1806 */       this.wcharConverter = this.parent.createWCharCTBConverter();
/*      */     }
/* 1808 */     return this.wcharConverter;
/*      */   }
/*      */   
/*      */   protected void dprint(String paramString) {
/* 1812 */     if (this.debug)
/* 1813 */       ORBUtility.dprint(this, paramString); 
/*      */   }
/*      */   
/*      */   void alignOnBoundary(int paramInt) {
/* 1817 */     alignAndReserve(paramInt, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void start_value(String paramString) {
/* 1822 */     if (this.debug) {
/* 1823 */       dprint("start_value w/ rep id " + paramString + " called at pos " + 
/*      */ 
/*      */           
/* 1826 */           get_offset() + " position " + this.bbwi
/*      */           
/* 1828 */           .position());
/*      */     }
/*      */     
/* 1831 */     if (this.inBlock) {
/* 1832 */       end_block();
/*      */     }
/*      */     
/* 1835 */     writeValueTag(true, true, null);
/*      */ 
/*      */     
/* 1838 */     write_repositoryId(paramString);
/*      */ 
/*      */     
/* 1841 */     this.end_flag--;
/* 1842 */     this.chunkedValueNestingLevel--;
/*      */ 
/*      */     
/* 1845 */     start_block();
/*      */   }
/*      */ 
/*      */   
/*      */   public void end_value() {
/* 1850 */     if (this.debug) {
/* 1851 */       dprint("end_value called at pos " + 
/* 1852 */           get_offset() + " position " + this.bbwi
/*      */           
/* 1854 */           .position());
/*      */     }
/*      */     
/* 1857 */     end_block();
/*      */     
/* 1859 */     writeEndTag(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1873 */     if (this.debug) {
/* 1874 */       dprint("mustChunk is " + this.mustChunk);
/*      */     }
/*      */     
/* 1877 */     if (this.mustChunk) {
/* 1878 */       start_block();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/* 1885 */     getBufferManager().close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1893 */     if (getByteBufferWithInfo() != null && getByteBuffer() != null) {
/*      */       
/* 1895 */       MessageMediator messageMediator = this.parent.getMessageMediator();
/* 1896 */       if (messageMediator != null) {
/*      */ 
/*      */         
/* 1899 */         CDRInputObject cDRInputObject = (CDRInputObject)messageMediator.getInputObject();
/* 1900 */         if (cDRInputObject != null)
/*      */         {
/* 1902 */           if (cDRInputObject.isSharing(getByteBuffer())) {
/*      */ 
/*      */ 
/*      */             
/* 1906 */             cDRInputObject.setByteBuffer(null);
/* 1907 */             cDRInputObject.setByteBufferWithInfo(null);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1913 */       ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
/* 1914 */       if (this.debug) {
/*      */ 
/*      */         
/* 1917 */         int i = System.identityHashCode(this.bbwi.byteBuffer);
/* 1918 */         StringBuffer stringBuffer = new StringBuffer(80);
/* 1919 */         stringBuffer.append(".close - releasing ByteBuffer id (");
/* 1920 */         stringBuffer.append(i).append(") to ByteBufferPool.");
/* 1921 */         String str = stringBuffer.toString();
/* 1922 */         dprint(str);
/*      */       } 
/* 1924 */       byteBufferPool.releaseByteBuffer(getByteBuffer());
/* 1925 */       this.bbwi.byteBuffer = null;
/* 1926 */       this.bbwi = null;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDROutputStream_1_0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */