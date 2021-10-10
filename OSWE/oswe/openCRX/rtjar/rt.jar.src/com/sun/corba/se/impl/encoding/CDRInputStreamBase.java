/*     */ package com.sun.corba.se.impl.encoding;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.omg.CORBA.NO_IMPLEMENT;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ 
/*     */ abstract class CDRInputStreamBase extends InputStream {
/*     */   protected CDRInputStream parent;
/*     */   
/*     */   public abstract void init(ORB paramORB, ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean, BufferManagerRead paramBufferManagerRead);
/*     */   
/*     */   public abstract boolean read_boolean();
/*     */   
/*     */   public abstract char read_char();
/*     */   
/*     */   public abstract char read_wchar();
/*     */   
/*     */   public abstract byte read_octet();
/*     */   
/*     */   public abstract short read_short();
/*     */   
/*     */   public abstract short read_ushort();
/*     */   
/*     */   public abstract int read_long();
/*     */   
/*     */   public abstract int read_ulong();
/*     */   
/*     */   public abstract long read_longlong();
/*     */   
/*     */   public abstract long read_ulonglong();
/*     */   
/*     */   public abstract float read_float();
/*     */   
/*     */   public abstract double read_double();
/*     */   
/*     */   public abstract String read_string();
/*     */   
/*     */   public abstract String read_wstring();
/*     */   
/*     */   public abstract void read_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */   
/*     */   public void setParent(CDRInputStream paramCDRInputStream) {
/*  51 */     this.parent = paramCDRInputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void read_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public abstract void read_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public abstract void read_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public abstract void read_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public abstract void read_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public abstract void read_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public abstract void read_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public abstract void read_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   public abstract Object read_Object();
/*     */ 
/*     */   
/*     */   public abstract TypeCode read_TypeCode();
/*     */ 
/*     */   
/*     */   public abstract Any read_any();
/*     */ 
/*     */   
/*     */   public abstract Principal read_Principal();
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*  92 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */   public abstract BigDecimal read_fixed();
/*     */   public Context read_Context() {
/*  96 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */   public abstract Object read_Object(Class paramClass);
/*     */   public abstract ORB orb();
/*     */   
/*     */   public abstract Serializable read_value();
/*     */   
/*     */   public abstract Serializable read_value(Class paramClass);
/*     */   
/*     */   public abstract Serializable read_value(BoxedValueHelper paramBoxedValueHelper);
/*     */   
/*     */   public abstract Serializable read_value(String paramString);
/*     */   
/*     */   public abstract Serializable read_value(Serializable paramSerializable);
/*     */   
/*     */   public abstract Object read_abstract_interface();
/*     */   
/*     */   public abstract Object read_abstract_interface(Class paramClass);
/*     */   
/*     */   public abstract void consumeEndian();
/*     */   
/*     */   public abstract int getPosition();
/*     */   
/*     */   public abstract Object read_Abstract();
/*     */   
/*     */   public abstract Serializable read_Value();
/*     */   
/*     */   public abstract void read_any_array(AnySeqHolder paramAnySeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_boolean_array(BooleanSeqHolder paramBooleanSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_char_array(CharSeqHolder paramCharSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_wchar_array(WCharSeqHolder paramWCharSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_octet_array(OctetSeqHolder paramOctetSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_short_array(ShortSeqHolder paramShortSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_ushort_array(UShortSeqHolder paramUShortSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_long_array(LongSeqHolder paramLongSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_ulong_array(ULongSeqHolder paramULongSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_ulonglong_array(ULongLongSeqHolder paramULongLongSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_longlong_array(LongLongSeqHolder paramLongLongSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_float_array(FloatSeqHolder paramFloatSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void read_double_array(DoubleSeqHolder paramDoubleSeqHolder, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract String[] _truncatable_ids();
/*     */   
/*     */   public abstract void mark(int paramInt);
/*     */   
/*     */   public abstract void reset();
/*     */   
/*     */   public boolean markSupported() {
/* 156 */     return false;
/*     */   }
/*     */   
/*     */   public abstract CDRInputStreamBase dup();
/*     */   
/*     */   public abstract BigDecimal read_fixed(short paramShort1, short paramShort2);
/*     */   
/*     */   public abstract boolean isLittleEndian();
/*     */   
/*     */   abstract void setHeaderPadding(boolean paramBoolean);
/*     */   
/*     */   public abstract ByteBuffer getByteBuffer();
/*     */   
/*     */   public abstract void setByteBuffer(ByteBuffer paramByteBuffer);
/*     */   
/*     */   public abstract void setByteBufferWithInfo(ByteBufferWithInfo paramByteBufferWithInfo);
/*     */   
/*     */   public abstract int getBufferLength();
/*     */   
/*     */   public abstract void setBufferLength(int paramInt);
/*     */   
/*     */   public abstract int getIndex();
/*     */   
/*     */   public abstract void setIndex(int paramInt);
/*     */   
/*     */   public abstract void orb(ORB paramORB);
/*     */   
/*     */   public abstract BufferManagerRead getBufferManager();
/*     */   
/*     */   public abstract GIOPVersion getGIOPVersion();
/*     */   
/*     */   abstract CodeBase getCodeBase();
/*     */   
/*     */   abstract void printBuffer();
/*     */   
/*     */   abstract void alignOnBoundary(int paramInt);
/*     */   
/*     */   abstract void performORBVersionSpecificInit();
/*     */   
/*     */   public abstract void resetCodeSetConverters();
/*     */   
/*     */   public abstract void start_value();
/*     */   
/*     */   public abstract void end_value();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDRInputStreamBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */