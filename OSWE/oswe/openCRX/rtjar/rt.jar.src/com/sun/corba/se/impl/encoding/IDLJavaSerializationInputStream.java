/*      */ package com.sun.corba.se.impl.encoding;
/*      */ 
/*      */ import com.sun.corba.se.impl.corba.PrincipalImpl;
/*      */ import com.sun.corba.se.impl.corba.TypeCodeImpl;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.impl.util.RepositoryId;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.IORFactories;
/*      */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.presentation.rmi.PresentationDefaults;
/*      */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*      */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*      */ import com.sun.org.omg.SendingContext.CodeBase;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.rmi.RemoteException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.LinkedList;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.AnySeqHolder;
/*      */ import org.omg.CORBA.BooleanSeqHolder;
/*      */ import org.omg.CORBA.CharSeqHolder;
/*      */ import org.omg.CORBA.Context;
/*      */ import org.omg.CORBA.DoubleSeqHolder;
/*      */ import org.omg.CORBA.FloatSeqHolder;
/*      */ import org.omg.CORBA.LongLongSeqHolder;
/*      */ import org.omg.CORBA.LongSeqHolder;
/*      */ import org.omg.CORBA.MARSHAL;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.OctetSeqHolder;
/*      */ import org.omg.CORBA.Principal;
/*      */ import org.omg.CORBA.ShortSeqHolder;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.ULongLongSeqHolder;
/*      */ import org.omg.CORBA.ULongSeqHolder;
/*      */ import org.omg.CORBA.UShortSeqHolder;
/*      */ import org.omg.CORBA.WCharSeqHolder;
/*      */ import org.omg.CORBA.portable.BoxedValueHelper;
/*      */ import org.omg.CORBA.portable.IDLEntity;
/*      */ import org.omg.CORBA.portable.InputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IDLJavaSerializationInputStream
/*      */   extends CDRInputStreamBase
/*      */ {
/*      */   private ORB orb;
/*      */   private int bufSize;
/*      */   private ByteBuffer buffer;
/*      */   private byte encodingVersion;
/*      */   private ObjectInputStream is;
/*      */   private _ByteArrayInputStream bis;
/*      */   private BufferManagerRead bufferManager;
/*   87 */   private final int directReadLength = 16;
/*      */   
/*      */   private boolean markOn;
/*      */   private int peekIndex;
/*      */   private int peekCount;
/*   92 */   private LinkedList markedItemQ = new LinkedList();
/*      */   protected ORBUtilSystemException wrapper;
/*      */   
/*      */   class _ByteArrayInputStream
/*      */     extends ByteArrayInputStream
/*      */   {
/*      */     _ByteArrayInputStream(byte[] param1ArrayOfbyte) {
/*   99 */       super(param1ArrayOfbyte);
/*      */     }
/*      */     
/*      */     int getPosition() {
/*  103 */       return this.pos;
/*      */     }
/*      */     
/*      */     void setPosition(int param1Int) {
/*  107 */       if (param1Int < 0 || param1Int > this.count) {
/*  108 */         throw new IndexOutOfBoundsException();
/*      */       }
/*  110 */       this.pos = param1Int;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class MarshalObjectInputStream
/*      */     extends ObjectInputStream
/*      */   {
/*      */     ORB orb;
/*      */     
/*      */     MarshalObjectInputStream(InputStream param1InputStream, ORB param1ORB) throws IOException {
/*  121 */       super(param1InputStream);
/*  122 */       this.orb = param1ORB;
/*      */       
/*  124 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run()
/*      */             {
/*  128 */               IDLJavaSerializationInputStream.MarshalObjectInputStream.this.enableResolveObject(true);
/*  129 */               return null;
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final Object resolveObject(Object param1Object) throws IOException {
/*      */       try {
/*  140 */         if (StubAdapter.isStub(param1Object)) {
/*  141 */           StubAdapter.connect(param1Object, (ORB)this.orb);
/*      */         }
/*  143 */       } catch (RemoteException remoteException) {
/*  144 */         IOException iOException = new IOException("resolveObject failed");
/*  145 */         iOException.initCause(remoteException);
/*  146 */         throw iOException;
/*      */       } 
/*  148 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IDLJavaSerializationInputStream(byte paramByte) {
/*  154 */     this.encodingVersion = paramByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void init(ORB paramORB, ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean, BufferManagerRead paramBufferManagerRead) {
/*      */     byte[] arrayOfByte;
/*  162 */     this.orb = (ORB)paramORB;
/*  163 */     this.bufSize = paramInt;
/*  164 */     this.bufferManager = paramBufferManagerRead;
/*  165 */     this.buffer = paramByteBuffer;
/*  166 */     this
/*  167 */       .wrapper = ORBUtilSystemException.get((ORB)paramORB, "rpc.encoding");
/*      */ 
/*      */     
/*  170 */     if (this.buffer.hasArray()) {
/*  171 */       arrayOfByte = this.buffer.array();
/*      */     } else {
/*  173 */       arrayOfByte = new byte[paramInt];
/*  174 */       this.buffer.get(arrayOfByte);
/*      */     } 
/*      */ 
/*      */     
/*  178 */     this.bis = new _ByteArrayInputStream(arrayOfByte);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initObjectInputStream() {
/*  184 */     if (this.is != null) {
/*  185 */       throw this.wrapper.javaStreamInitFailed();
/*      */     }
/*      */     try {
/*  188 */       this.is = new MarshalObjectInputStream(this.bis, this.orb);
/*  189 */     } catch (Exception exception) {
/*  190 */       throw this.wrapper.javaStreamInitFailed(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean read_boolean() {
/*  199 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  200 */       return ((Boolean)this.markedItemQ.removeFirst()).booleanValue();
/*      */     }
/*  202 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  204 */       return ((Boolean)this.markedItemQ.get(this.peekIndex++)).booleanValue();
/*      */     }
/*      */     try {
/*  207 */       boolean bool = this.is.readBoolean();
/*  208 */       if (this.markOn) {
/*  209 */         this.markedItemQ.addLast(Boolean.valueOf(bool));
/*      */       }
/*  211 */       return bool;
/*  212 */     } catch (Exception exception) {
/*  213 */       throw this.wrapper.javaSerializationException(exception, "read_boolean");
/*      */     } 
/*      */   }
/*      */   
/*      */   public char read_char() {
/*  218 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  219 */       return ((Character)this.markedItemQ.removeFirst()).charValue();
/*      */     }
/*  221 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  223 */       return ((Character)this.markedItemQ.get(this.peekIndex++)).charValue();
/*      */     }
/*      */     try {
/*  226 */       char c = this.is.readChar();
/*  227 */       if (this.markOn) {
/*  228 */         this.markedItemQ.addLast(new Character(c));
/*      */       }
/*  230 */       return c;
/*  231 */     } catch (Exception exception) {
/*  232 */       throw this.wrapper.javaSerializationException(exception, "read_char");
/*      */     } 
/*      */   }
/*      */   
/*      */   public char read_wchar() {
/*  237 */     return read_char();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte read_octet() {
/*  243 */     if (this.bis.getPosition() < 16) {
/*  244 */       byte b = (byte)this.bis.read();
/*  245 */       if (this.bis.getPosition() == 16) {
/*  246 */         initObjectInputStream();
/*      */       }
/*  248 */       return b;
/*      */     } 
/*      */     
/*  251 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  252 */       return ((Byte)this.markedItemQ.removeFirst()).byteValue();
/*      */     }
/*      */     
/*  255 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  257 */       return ((Byte)this.markedItemQ.get(this.peekIndex++)).byteValue();
/*      */     }
/*      */     
/*      */     try {
/*  261 */       byte b = this.is.readByte();
/*  262 */       if (this.markOn)
/*      */       {
/*  264 */         this.markedItemQ.addLast(new Byte(b));
/*      */       }
/*  266 */       return b;
/*  267 */     } catch (Exception exception) {
/*  268 */       throw this.wrapper.javaSerializationException(exception, "read_octet");
/*      */     } 
/*      */   }
/*      */   
/*      */   public short read_short() {
/*  273 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  274 */       return ((Short)this.markedItemQ.removeFirst()).shortValue();
/*      */     }
/*  276 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  278 */       return ((Short)this.markedItemQ.get(this.peekIndex++)).shortValue();
/*      */     }
/*      */     
/*      */     try {
/*  282 */       short s = this.is.readShort();
/*  283 */       if (this.markOn) {
/*  284 */         this.markedItemQ.addLast(new Short(s));
/*      */       }
/*  286 */       return s;
/*  287 */     } catch (Exception exception) {
/*  288 */       throw this.wrapper.javaSerializationException(exception, "read_short");
/*      */     } 
/*      */   }
/*      */   
/*      */   public short read_ushort() {
/*  293 */     return read_short();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int read_long() {
/*  299 */     if (this.bis.getPosition() < 16) {
/*      */ 
/*      */ 
/*      */       
/*  303 */       int i = this.bis.read() << 24 & 0xFF000000;
/*  304 */       int j = this.bis.read() << 16 & 0xFF0000;
/*  305 */       int k = this.bis.read() << 8 & 0xFF00;
/*  306 */       int m = this.bis.read() << 0 & 0xFF;
/*      */       
/*  308 */       if (this.bis.getPosition() == 16) {
/*  309 */         initObjectInputStream();
/*  310 */       } else if (this.bis.getPosition() > 16) {
/*      */ 
/*      */         
/*  313 */         this.wrapper.javaSerializationException("read_long");
/*      */       } 
/*      */       
/*  316 */       return i | j | k | m;
/*      */     } 
/*      */     
/*  319 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  320 */       return ((Integer)this.markedItemQ.removeFirst()).intValue();
/*      */     }
/*      */     
/*  323 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  325 */       return ((Integer)this.markedItemQ.get(this.peekIndex++)).intValue();
/*      */     }
/*      */     
/*      */     try {
/*  329 */       int i = this.is.readInt();
/*  330 */       if (this.markOn) {
/*  331 */         this.markedItemQ.addLast(new Integer(i));
/*      */       }
/*  333 */       return i;
/*  334 */     } catch (Exception exception) {
/*  335 */       throw this.wrapper.javaSerializationException(exception, "read_long");
/*      */     } 
/*      */   }
/*      */   
/*      */   public int read_ulong() {
/*  340 */     return read_long();
/*      */   }
/*      */   
/*      */   public long read_longlong() {
/*  344 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  345 */       return ((Long)this.markedItemQ.removeFirst()).longValue();
/*      */     }
/*  347 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  349 */       return ((Long)this.markedItemQ.get(this.peekIndex++)).longValue();
/*      */     }
/*      */     
/*      */     try {
/*  353 */       long l = this.is.readLong();
/*  354 */       if (this.markOn) {
/*  355 */         this.markedItemQ.addLast(new Long(l));
/*      */       }
/*  357 */       return l;
/*  358 */     } catch (Exception exception) {
/*  359 */       throw this.wrapper.javaSerializationException(exception, "read_longlong");
/*      */     } 
/*      */   }
/*      */   
/*      */   public long read_ulonglong() {
/*  364 */     return read_longlong();
/*      */   }
/*      */   
/*      */   public float read_float() {
/*  368 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  369 */       return ((Float)this.markedItemQ.removeFirst()).floatValue();
/*      */     }
/*  371 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  373 */       return ((Float)this.markedItemQ.get(this.peekIndex++)).floatValue();
/*      */     }
/*      */     
/*      */     try {
/*  377 */       float f = this.is.readFloat();
/*  378 */       if (this.markOn) {
/*  379 */         this.markedItemQ.addLast(new Float(f));
/*      */       }
/*  381 */       return f;
/*  382 */     } catch (Exception exception) {
/*  383 */       throw this.wrapper.javaSerializationException(exception, "read_float");
/*      */     } 
/*      */   }
/*      */   
/*      */   public double read_double() {
/*  388 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  389 */       return ((Double)this.markedItemQ.removeFirst()).doubleValue();
/*      */     }
/*  391 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  393 */       return ((Double)this.markedItemQ.get(this.peekIndex++)).doubleValue();
/*      */     }
/*      */     
/*      */     try {
/*  397 */       double d = this.is.readDouble();
/*  398 */       if (this.markOn) {
/*  399 */         this.markedItemQ.addLast(new Double(d));
/*      */       }
/*  401 */       return d;
/*  402 */     } catch (Exception exception) {
/*  403 */       throw this.wrapper.javaSerializationException(exception, "read_double");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String read_string() {
/*  410 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  411 */       return this.markedItemQ.removeFirst();
/*      */     }
/*  413 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  415 */       return this.markedItemQ.get(this.peekIndex++);
/*      */     }
/*      */     try {
/*  418 */       String str = this.is.readUTF();
/*  419 */       if (this.markOn) {
/*  420 */         this.markedItemQ.addLast(str);
/*      */       }
/*  422 */       return str;
/*  423 */     } catch (Exception exception) {
/*  424 */       throw this.wrapper.javaSerializationException(exception, "read_string");
/*      */     } 
/*      */   }
/*      */   
/*      */   public String read_wstring() {
/*  429 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  430 */       return this.markedItemQ.removeFirst();
/*      */     }
/*  432 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  434 */       return this.markedItemQ.get(this.peekIndex++);
/*      */     }
/*      */     try {
/*  437 */       String str = (String)this.is.readObject();
/*  438 */       if (this.markOn) {
/*  439 */         this.markedItemQ.addLast(str);
/*      */       }
/*  441 */       return str;
/*  442 */     } catch (Exception exception) {
/*  443 */       throw this.wrapper.javaSerializationException(exception, "read_wstring");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void read_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) {
/*  450 */     for (byte b = 0; b < paramInt2; b++) {
/*  451 */       paramArrayOfboolean[b + paramInt1] = read_boolean();
/*      */     }
/*      */   }
/*      */   
/*      */   public void read_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  456 */     for (byte b = 0; b < paramInt2; b++) {
/*  457 */       paramArrayOfchar[b + paramInt1] = read_char();
/*      */     }
/*      */   }
/*      */   
/*      */   public void read_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  462 */     read_char_array(paramArrayOfchar, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  466 */     for (byte b = 0; b < paramInt2; b++) {
/*  467 */       paramArrayOfbyte[b + paramInt1] = read_octet();
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
/*      */   public void read_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/*  483 */     for (byte b = 0; b < paramInt2; b++) {
/*  484 */       paramArrayOfshort[b + paramInt1] = read_short();
/*      */     }
/*      */   }
/*      */   
/*      */   public void read_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/*  489 */     read_short_array(paramArrayOfshort, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  493 */     for (byte b = 0; b < paramInt2; b++) {
/*  494 */       paramArrayOfint[b + paramInt1] = read_long();
/*      */     }
/*      */   }
/*      */   
/*      */   public void read_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  499 */     read_long_array(paramArrayOfint, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/*  503 */     for (byte b = 0; b < paramInt2; b++) {
/*  504 */       paramArrayOflong[b + paramInt1] = read_longlong();
/*      */     }
/*      */   }
/*      */   
/*      */   public void read_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/*  509 */     read_longlong_array(paramArrayOflong, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/*  513 */     for (byte b = 0; b < paramInt2; b++) {
/*  514 */       paramArrayOffloat[b + paramInt1] = read_float();
/*      */     }
/*      */   }
/*      */   
/*      */   public void read_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/*  519 */     for (byte b = 0; b < paramInt2; b++) {
/*  520 */       paramArrayOfdouble[b + paramInt1] = read_double();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object read_Object() {
/*  527 */     return read_Object(null);
/*      */   }
/*      */   
/*      */   public TypeCode read_TypeCode() {
/*  531 */     TypeCodeImpl typeCodeImpl = new TypeCodeImpl(this.orb);
/*  532 */     typeCodeImpl.read_value(this.parent);
/*  533 */     return (TypeCode)typeCodeImpl;
/*      */   }
/*      */ 
/*      */   
/*      */   public Any read_any() {
/*  538 */     Any any = this.orb.create_any();
/*  539 */     TypeCodeImpl typeCodeImpl = new TypeCodeImpl(this.orb);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  550 */       typeCodeImpl.read_value(this.parent);
/*  551 */     } catch (MARSHAL mARSHAL) {
/*  552 */       if (typeCodeImpl.kind().value() != 29) {
/*  553 */         throw mARSHAL;
/*      */       }
/*      */ 
/*      */       
/*  557 */       mARSHAL.printStackTrace();
/*      */     } 
/*      */ 
/*      */     
/*  561 */     any.read_value((InputStream)this.parent, (TypeCode)typeCodeImpl);
/*      */     
/*  563 */     return any;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Principal read_Principal() {
/*  569 */     int i = read_long();
/*  570 */     byte[] arrayOfByte = new byte[i];
/*  571 */     read_octet_array(arrayOfByte, 0, i);
/*  572 */     PrincipalImpl principalImpl = new PrincipalImpl();
/*  573 */     principalImpl.name(arrayOfByte);
/*  574 */     return (Principal)principalImpl;
/*      */   }
/*      */   
/*      */   public BigDecimal read_fixed() {
/*  578 */     return new BigDecimal(read_fixed_buffer().toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuffer read_fixed_buffer() {
/*  589 */     StringBuffer stringBuffer = new StringBuffer(64);
/*      */ 
/*      */ 
/*      */     
/*  593 */     boolean bool1 = false;
/*  594 */     boolean bool2 = true;
/*  595 */     while (bool2) {
/*  596 */       byte b = read_octet();
/*  597 */       int i = (b & 0xF0) >> 4;
/*  598 */       int j = b & 0xF;
/*  599 */       if (bool1 || i != 0) {
/*  600 */         stringBuffer.append(Character.forDigit(i, 10));
/*  601 */         bool1 = true;
/*      */       } 
/*  603 */       if (j == 12) {
/*      */         
/*  605 */         if (!bool1)
/*      */         {
/*  607 */           return new StringBuffer("0.0");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  612 */         bool2 = false; continue;
/*  613 */       }  if (j == 13) {
/*      */         
/*  615 */         stringBuffer.insert(0, '-');
/*  616 */         bool2 = false; continue;
/*      */       } 
/*  618 */       stringBuffer.append(Character.forDigit(j, 10));
/*  619 */       bool1 = true;
/*      */     } 
/*      */     
/*  622 */     return stringBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object read_Object(Class<?> paramClass) {
/*  628 */     IOR iOR = IORFactories.makeIOR(this.parent);
/*  629 */     if (iOR.isNil()) {
/*  630 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  634 */     PresentationManager.StubFactoryFactory stubFactoryFactory = ORB.getStubFactoryFactory();
/*  635 */     String str = iOR.getProfile().getCodebase();
/*  636 */     PresentationManager.StubFactory stubFactory = null;
/*      */     
/*  638 */     if (paramClass == null) {
/*  639 */       RepositoryId repositoryId = RepositoryId.cache.getId(iOR.getTypeId());
/*  640 */       String str1 = repositoryId.getClassName();
/*  641 */       boolean bool = repositoryId.isIDLType();
/*      */       
/*  643 */       if (str1 == null || str1.equals("")) {
/*  644 */         stubFactory = null;
/*      */       } else {
/*      */         try {
/*  647 */           stubFactory = stubFactoryFactory.createStubFactory(str1, bool, str, (Class)null, (ClassLoader)null);
/*      */         
/*      */         }
/*  650 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  655 */           stubFactory = null;
/*      */         } 
/*      */       } 
/*  658 */     } else if (StubAdapter.isStubClass(paramClass)) {
/*  659 */       stubFactory = PresentationDefaults.makeStaticStubFactory(paramClass);
/*      */     } else {
/*      */       
/*  662 */       boolean bool = IDLEntity.class.isAssignableFrom(paramClass);
/*      */       
/*  664 */       stubFactory = stubFactoryFactory.createStubFactory(paramClass
/*  665 */           .getName(), bool, str, paramClass, paramClass.getClassLoader());
/*      */     } 
/*      */     
/*  668 */     return CDRInputStream_1_0.internalIORToObject(iOR, stubFactory, this.orb);
/*      */   }
/*      */   
/*      */   public ORB orb() {
/*  672 */     return (ORB)this.orb;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializable read_value() {
/*  678 */     if (!this.markOn && !this.markedItemQ.isEmpty()) {
/*  679 */       return this.markedItemQ.removeFirst();
/*      */     }
/*  681 */     if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount)
/*      */     {
/*  683 */       return this.markedItemQ.get(this.peekIndex++);
/*      */     }
/*      */     try {
/*  686 */       Serializable serializable = (Serializable)this.is.readObject();
/*  687 */       if (this.markOn) {
/*  688 */         this.markedItemQ.addLast(serializable);
/*      */       }
/*  690 */       return serializable;
/*  691 */     } catch (Exception exception) {
/*  692 */       throw this.wrapper.javaSerializationException(exception, "read_value");
/*      */     } 
/*      */   }
/*      */   
/*      */   public Serializable read_value(Class paramClass) {
/*  697 */     return read_value();
/*      */   }
/*      */ 
/*      */   
/*      */   public Serializable read_value(BoxedValueHelper paramBoxedValueHelper) {
/*  702 */     return read_value();
/*      */   }
/*      */   
/*      */   public Serializable read_value(String paramString) {
/*  706 */     return read_value();
/*      */   }
/*      */   
/*      */   public Serializable read_value(Serializable paramSerializable) {
/*  710 */     return read_value();
/*      */   }
/*      */   
/*      */   public Object read_abstract_interface() {
/*  714 */     return read_abstract_interface(null);
/*      */   }
/*      */   
/*      */   public Object read_abstract_interface(Class paramClass) {
/*  718 */     boolean bool = read_boolean();
/*  719 */     if (bool) {
/*  720 */       return read_Object(paramClass);
/*      */     }
/*  722 */     return read_value();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void consumeEndian() {
/*  728 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */   
/*      */   public int getPosition() {
/*      */     try {
/*  733 */       return this.bis.getPosition();
/*  734 */     } catch (Exception exception) {
/*  735 */       throw this.wrapper.javaSerializationException(exception, "getPosition");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Object read_Abstract() {
/*  741 */     return read_abstract_interface();
/*      */   }
/*      */   
/*      */   public Serializable read_Value() {
/*  745 */     return read_value();
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_any_array(AnySeqHolder paramAnySeqHolder, int paramInt1, int paramInt2) {
/*  750 */     read_any_array(paramAnySeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void read_any_array(Any[] paramArrayOfAny, int paramInt1, int paramInt2) {
/*  755 */     for (byte b = 0; b < paramInt2; b++) {
/*  756 */       paramArrayOfAny[b + paramInt1] = read_any();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_boolean_array(BooleanSeqHolder paramBooleanSeqHolder, int paramInt1, int paramInt2) {
/*  762 */     read_boolean_array(paramBooleanSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_char_array(CharSeqHolder paramCharSeqHolder, int paramInt1, int paramInt2) {
/*  767 */     read_char_array(paramCharSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_wchar_array(WCharSeqHolder paramWCharSeqHolder, int paramInt1, int paramInt2) {
/*  772 */     read_wchar_array(paramWCharSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_octet_array(OctetSeqHolder paramOctetSeqHolder, int paramInt1, int paramInt2) {
/*  777 */     read_octet_array(paramOctetSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_short_array(ShortSeqHolder paramShortSeqHolder, int paramInt1, int paramInt2) {
/*  782 */     read_short_array(paramShortSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_ushort_array(UShortSeqHolder paramUShortSeqHolder, int paramInt1, int paramInt2) {
/*  787 */     read_ushort_array(paramUShortSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_long_array(LongSeqHolder paramLongSeqHolder, int paramInt1, int paramInt2) {
/*  792 */     read_long_array(paramLongSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_ulong_array(ULongSeqHolder paramULongSeqHolder, int paramInt1, int paramInt2) {
/*  797 */     read_ulong_array(paramULongSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_ulonglong_array(ULongLongSeqHolder paramULongLongSeqHolder, int paramInt1, int paramInt2) {
/*  802 */     read_ulonglong_array(paramULongLongSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_longlong_array(LongLongSeqHolder paramLongLongSeqHolder, int paramInt1, int paramInt2) {
/*  807 */     read_longlong_array(paramLongLongSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_float_array(FloatSeqHolder paramFloatSeqHolder, int paramInt1, int paramInt2) {
/*  812 */     read_float_array(paramFloatSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_double_array(DoubleSeqHolder paramDoubleSeqHolder, int paramInt1, int paramInt2) {
/*  817 */     read_double_array(paramDoubleSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _truncatable_ids() {
/*  823 */     throw this.wrapper.giopVersionError();
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
/*      */   public void mark(int paramInt) {
/*  839 */     if (this.markOn || this.is == null) {
/*  840 */       throw this.wrapper.javaSerializationException("mark");
/*      */     }
/*  842 */     this.markOn = true;
/*  843 */     if (!this.markedItemQ.isEmpty()) {
/*  844 */       this.peekIndex = 0;
/*  845 */       this.peekCount = this.markedItemQ.size();
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
/*      */   public void reset() {
/*  857 */     this.markOn = false;
/*  858 */     this.peekIndex = 0;
/*  859 */     this.peekCount = 0;
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
/*      */   public boolean markSupported() {
/*  884 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CDRInputStreamBase dup() {
/*  890 */     CDRInputStreamBase cDRInputStreamBase = null;
/*      */     
/*      */     try {
/*  893 */       cDRInputStreamBase = (CDRInputStreamBase)getClass().newInstance();
/*  894 */     } catch (Exception exception) {
/*  895 */       throw this.wrapper.couldNotDuplicateCdrInputStream(exception);
/*      */     } 
/*      */     
/*  898 */     cDRInputStreamBase.init((ORB)this.orb, this.buffer, this.bufSize, false, null);
/*      */ 
/*      */     
/*  901 */     ((IDLJavaSerializationInputStream)cDRInputStreamBase).skipBytes(getPosition());
/*      */ 
/*      */     
/*  904 */     ((IDLJavaSerializationInputStream)cDRInputStreamBase)
/*  905 */       .setMarkData(this.markOn, this.peekIndex, this.peekCount, (LinkedList)this.markedItemQ
/*  906 */         .clone());
/*      */     
/*  908 */     return cDRInputStreamBase;
/*      */   }
/*      */ 
/*      */   
/*      */   void skipBytes(int paramInt) {
/*      */     try {
/*  914 */       this.is.skipBytes(paramInt);
/*  915 */     } catch (Exception exception) {
/*  916 */       throw this.wrapper.javaSerializationException(exception, "skipBytes");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setMarkData(boolean paramBoolean, int paramInt1, int paramInt2, LinkedList paramLinkedList) {
/*  923 */     this.markOn = paramBoolean;
/*  924 */     this.peekIndex = paramInt1;
/*  925 */     this.peekCount = paramInt2;
/*  926 */     this.markedItemQ = paramLinkedList;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal read_fixed(short paramShort1, short paramShort2) {
/*  932 */     StringBuffer stringBuffer = read_fixed_buffer();
/*  933 */     if (paramShort1 != stringBuffer.length())
/*  934 */       throw this.wrapper.badFixed(new Integer(paramShort1), new Integer(stringBuffer
/*  935 */             .length())); 
/*  936 */     stringBuffer.insert(paramShort1 - paramShort2, '.');
/*  937 */     return new BigDecimal(stringBuffer.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLittleEndian() {
/*  942 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setHeaderPadding(boolean paramBoolean) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer getByteBuffer() {
/*  954 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */   
/*      */   public void setByteBuffer(ByteBuffer paramByteBuffer) {
/*  958 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */   
/*      */   public void setByteBufferWithInfo(ByteBufferWithInfo paramByteBufferWithInfo) {
/*  962 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */   
/*      */   public int getBufferLength() {
/*  966 */     return this.bufSize;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBufferLength(int paramInt) {}
/*      */ 
/*      */   
/*      */   public int getIndex() {
/*  975 */     return this.bis.getPosition();
/*      */   }
/*      */   
/*      */   public void setIndex(int paramInt) {
/*      */     try {
/*  980 */       this.bis.setPosition(paramInt);
/*  981 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*  982 */       throw this.wrapper.javaSerializationException(indexOutOfBoundsException, "setIndex");
/*      */     } 
/*      */   }
/*      */   
/*      */   public void orb(ORB paramORB) {
/*  987 */     this.orb = (ORB)paramORB;
/*      */   }
/*      */   
/*      */   public BufferManagerRead getBufferManager() {
/*  991 */     return this.bufferManager;
/*      */   }
/*      */   
/*      */   public GIOPVersion getGIOPVersion() {
/*  995 */     return GIOPVersion.V1_2;
/*      */   }
/*      */   
/*      */   CodeBase getCodeBase() {
/*  999 */     return this.parent.getCodeBase();
/*      */   }
/*      */   
/*      */   void printBuffer() {
/* 1003 */     byte[] arrayOfByte = this.buffer.array();
/*      */     
/* 1005 */     System.out.println("+++++++ Input Buffer ++++++++");
/* 1006 */     System.out.println();
/* 1007 */     System.out.println("Current position: " + getPosition());
/* 1008 */     System.out.println("Total length : " + this.bufSize);
/* 1009 */     System.out.println();
/*      */     
/* 1011 */     char[] arrayOfChar = new char[16];
/*      */ 
/*      */     
/*      */     try {
/* 1015 */       for (byte b = 0; b < arrayOfByte.length; b += 16) {
/*      */         
/* 1017 */         byte b1 = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1023 */         while (b1 < 16 && b1 + b < arrayOfByte.length) {
/* 1024 */           int i = arrayOfByte[b + b1];
/* 1025 */           if (i < 0)
/* 1026 */             i = 256 + i; 
/* 1027 */           String str = Integer.toHexString(i);
/* 1028 */           if (str.length() == 1)
/* 1029 */             str = "0" + str; 
/* 1030 */           System.out.print(str + " ");
/* 1031 */           b1++;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1037 */         while (b1 < 16) {
/* 1038 */           System.out.print("   ");
/* 1039 */           b1++;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1044 */         byte b2 = 0;
/*      */         
/* 1046 */         while (b2 < 16 && b2 + b < arrayOfByte.length) {
/* 1047 */           if (ORBUtility.isPrintable((char)arrayOfByte[b + b2])) {
/* 1048 */             arrayOfChar[b2] = (char)arrayOfByte[b + b2];
/*      */           } else {
/* 1050 */             arrayOfChar[b2] = '.';
/*      */           } 
/* 1052 */           b2++;
/*      */         } 
/* 1054 */         System.out.println(new String(arrayOfChar, 0, b2));
/*      */       } 
/* 1056 */     } catch (Throwable throwable) {
/* 1057 */       throwable.printStackTrace();
/*      */     } 
/* 1059 */     System.out.println("++++++++++++++++++++++++++++++");
/*      */   }
/*      */   
/*      */   void alignOnBoundary(int paramInt) {
/* 1063 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void performORBVersionSpecificInit() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetCodeSetConverters() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void start_value() {
/* 1077 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */   
/*      */   public void end_value() {
/* 1081 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/IDLJavaSerializationInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */