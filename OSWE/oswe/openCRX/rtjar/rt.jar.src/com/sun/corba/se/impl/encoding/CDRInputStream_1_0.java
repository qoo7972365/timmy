/*      */ package com.sun.corba.se.impl.encoding;
/*      */ 
/*      */ import com.sun.corba.se.impl.corba.CORBAObjectImpl;
/*      */ import com.sun.corba.se.impl.corba.PrincipalImpl;
/*      */ import com.sun.corba.se.impl.corba.TypeCodeImpl;
/*      */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.orbutil.CacheTable;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdInterface;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdUtility;
/*      */ import com.sun.corba.se.impl.util.RepositoryId;
/*      */ import com.sun.corba.se.impl.util.Utility;
/*      */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*      */ import com.sun.corba.se.pept.transport.ByteBufferPool;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.IORFactories;
/*      */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*      */ import com.sun.corba.se.spi.presentation.rmi.PresentationDefaults;
/*      */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*      */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*      */ import com.sun.corba.se.spi.protocol.CorbaClientDelegate;
/*      */ import com.sun.org.omg.CORBA.portable.ValueHelper;
/*      */ import com.sun.org.omg.SendingContext.CodeBase;
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.MalformedURLException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import javax.rmi.CORBA.Tie;
/*      */ import javax.rmi.CORBA.ValueHandler;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.AnySeqHolder;
/*      */ import org.omg.CORBA.BooleanSeqHolder;
/*      */ import org.omg.CORBA.CharSeqHolder;
/*      */ import org.omg.CORBA.CompletionStatus;
/*      */ import org.omg.CORBA.Context;
/*      */ import org.omg.CORBA.CustomMarshal;
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
/*      */ import org.omg.CORBA.SystemException;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*      */ import org.omg.CORBA.ULongLongSeqHolder;
/*      */ import org.omg.CORBA.ULongSeqHolder;
/*      */ import org.omg.CORBA.UShortSeqHolder;
/*      */ import org.omg.CORBA.WCharSeqHolder;
/*      */ import org.omg.CORBA.portable.BoxedValueHelper;
/*      */ import org.omg.CORBA.portable.CustomValue;
/*      */ import org.omg.CORBA.portable.Delegate;
/*      */ import org.omg.CORBA.portable.IDLEntity;
/*      */ import org.omg.CORBA.portable.IndirectionException;
/*      */ import org.omg.CORBA.portable.InputStream;
/*      */ import org.omg.CORBA.portable.StreamableValue;
/*      */ import org.omg.CORBA.portable.ValueBase;
/*      */ import org.omg.CORBA.portable.ValueFactory;
/*      */ import org.omg.SendingContext.RunTime;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CDRInputStream_1_0
/*      */   extends CDRInputStreamBase
/*      */   implements RestorableInputStream
/*      */ {
/*      */   private static final String kReadMethod = "read";
/*      */   private static final int maxBlockLength = 2147483392;
/*      */   protected BufferManagerRead bufferManagerRead;
/*      */   protected ByteBufferWithInfo bbwi;
/*      */   private boolean debug = false;
/*      */   protected boolean littleEndian;
/*      */   protected ORB orb;
/*      */   protected ORBUtilSystemException wrapper;
/*      */   protected OMGSystemException omgWrapper;
/*  131 */   protected ValueHandler valueHandler = null;
/*      */ 
/*      */   
/*  134 */   private CacheTable valueCache = null;
/*      */ 
/*      */   
/*  137 */   private CacheTable repositoryIdCache = null;
/*      */ 
/*      */   
/*  140 */   private CacheTable codebaseCache = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  146 */   protected int blockLength = 2147483392;
/*      */ 
/*      */   
/*  149 */   protected int end_flag = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   private int chunkedValueNestingLevel = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   protected int valueIndirection = 0;
/*      */ 
/*      */ 
/*      */   
/*  173 */   protected int stringIndirection = 0;
/*      */ 
/*      */   
/*      */   protected boolean isChunked = false;
/*      */ 
/*      */   
/*      */   private RepositoryIdUtility repIdUtil;
/*      */ 
/*      */   
/*      */   private RepositoryIdStrings repIdStrs;
/*      */ 
/*      */   
/*      */   private CodeSetConversion.BTCConverter charConverter;
/*      */ 
/*      */   
/*      */   private CodeSetConversion.BTCConverter wcharConverter;
/*      */ 
/*      */   
/*      */   private boolean specialNoOptionalDataState = false;
/*      */ 
/*      */   
/*      */   private static final String _id = "IDL:omg.org/CORBA/DataInputStream:1.0";
/*      */ 
/*      */   
/*      */   public CDRInputStreamBase dup() {
/*  198 */     CDRInputStreamBase cDRInputStreamBase = null;
/*      */     
/*      */     try {
/*  201 */       cDRInputStreamBase = (CDRInputStreamBase)getClass().newInstance();
/*  202 */     } catch (Exception exception) {
/*  203 */       throw this.wrapper.couldNotDuplicateCdrInputStream(exception);
/*      */     } 
/*  205 */     cDRInputStreamBase.init((ORB)this.orb, this.bbwi.byteBuffer, this.bbwi.buflen, this.littleEndian, this.bufferManagerRead);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  211 */     ((CDRInputStream_1_0)cDRInputStreamBase).bbwi.position(this.bbwi.position());
/*      */     
/*  213 */     ((CDRInputStream_1_0)cDRInputStreamBase).bbwi.byteBuffer.limit(this.bbwi.buflen);
/*      */     
/*  215 */     return cDRInputStreamBase;
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
/*      */   public void init(ORB paramORB, ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean, BufferManagerRead paramBufferManagerRead) {
/*  227 */     this.orb = (ORB)paramORB;
/*  228 */     this.wrapper = ORBUtilSystemException.get((ORB)paramORB, "rpc.encoding");
/*      */     
/*  230 */     this.omgWrapper = OMGSystemException.get((ORB)paramORB, "rpc.encoding");
/*      */     
/*  232 */     this.littleEndian = paramBoolean;
/*  233 */     this.bufferManagerRead = paramBufferManagerRead;
/*  234 */     this.bbwi = new ByteBufferWithInfo(paramORB, paramByteBuffer, 0);
/*  235 */     this.bbwi.buflen = paramInt;
/*  236 */     this.bbwi.byteBuffer.limit(this.bbwi.buflen);
/*  237 */     this.markAndResetHandler = this.bufferManagerRead.getMarkAndResetHandler();
/*      */     
/*  239 */     this.debug = ((ORB)paramORB).transportDebugFlag;
/*      */   }
/*      */ 
/*      */   
/*      */   void performORBVersionSpecificInit() {
/*  244 */     createRepositoryIdHandlers();
/*      */   }
/*      */ 
/*      */   
/*      */   private final void createRepositoryIdHandlers() {
/*  249 */     this.repIdUtil = RepositoryIdFactory.getRepIdUtility();
/*  250 */     this.repIdStrs = RepositoryIdFactory.getRepIdStringsFactory();
/*      */   }
/*      */   
/*      */   public GIOPVersion getGIOPVersion() {
/*  254 */     return GIOPVersion.V1_0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setHeaderPadding(boolean paramBoolean) {
/*  260 */     throw this.wrapper.giopVersionError();
/*      */   }
/*      */   
/*      */   protected final int computeAlignment(int paramInt1, int paramInt2) {
/*  264 */     if (paramInt2 > 1) {
/*  265 */       int i = paramInt1 & paramInt2 - 1;
/*  266 */       if (i != 0) {
/*  267 */         return paramInt2 - i;
/*      */       }
/*      */     } 
/*  270 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSize() {
/*  275 */     return this.bbwi.position();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkBlockLength(int paramInt1, int paramInt2) {
/*  283 */     if (!this.isChunked) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  293 */     if (this.specialNoOptionalDataState) {
/*  294 */       throw this.omgWrapper.rmiiiopOptionalDataIncompatible1();
/*      */     }
/*      */     
/*  297 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  306 */     if (this.blockLength == get_offset()) {
/*      */       
/*  308 */       this.blockLength = 2147483392;
/*  309 */       start_block();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  317 */       if (this.blockLength == 2147483392) {
/*  318 */         bool = true;
/*      */       }
/*      */     }
/*  321 */     else if (this.blockLength < get_offset()) {
/*      */ 
/*      */       
/*  324 */       throw this.wrapper.chunkOverflow();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  334 */     int i = computeAlignment(this.bbwi.position(), paramInt1) + paramInt2;
/*      */     
/*  336 */     if (this.blockLength != 2147483392 && this.blockLength < 
/*  337 */       get_offset() + i) {
/*  338 */       throw this.omgWrapper.rmiiiopOptionalDataIncompatible2();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  346 */     if (bool) {
/*  347 */       int j = read_long();
/*  348 */       this.bbwi.position(this.bbwi.position() - 4);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  353 */       if (j < 0) {
/*  354 */         throw this.omgWrapper.rmiiiopOptionalDataIncompatible3();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void alignAndCheck(int paramInt1, int paramInt2) {
/*  360 */     checkBlockLength(paramInt1, paramInt2);
/*      */ 
/*      */ 
/*      */     
/*  364 */     int i = computeAlignment(this.bbwi.position(), paramInt1);
/*  365 */     this.bbwi.position(this.bbwi.position() + i);
/*      */     
/*  367 */     if (this.bbwi.position() + paramInt2 > this.bbwi.buflen) {
/*  368 */       grow(paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void grow(int paramInt1, int paramInt2) {
/*  376 */     this.bbwi.needed = paramInt2;
/*      */     
/*  378 */     this.bbwi = this.bufferManagerRead.underflow(this.bbwi);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void consumeEndian() {
/*  387 */     this.littleEndian = read_boolean();
/*      */   }
/*      */ 
/*      */   
/*      */   public final double read_longdouble() {
/*  392 */     throw this.wrapper.longDoubleNotImplemented(CompletionStatus.COMPLETED_MAYBE);
/*      */   }
/*      */   
/*      */   public final boolean read_boolean() {
/*  396 */     return (read_octet() != 0);
/*      */   }
/*      */   
/*      */   public final char read_char() {
/*  400 */     alignAndCheck(1, 1);
/*      */     
/*  402 */     return getConvertedChars(1, getCharConverter())[0];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char read_wchar() {
/*      */     int i, j;
/*  409 */     if (ORBUtility.isForeignORB(this.orb)) {
/*  410 */       throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  417 */     alignAndCheck(2, 2);
/*      */     
/*  419 */     if (this.littleEndian) {
/*  420 */       j = this.bbwi.byteBuffer.get(this.bbwi.position()) & 0xFF;
/*  421 */       this.bbwi.position(this.bbwi.position() + 1);
/*  422 */       i = this.bbwi.byteBuffer.get(this.bbwi.position()) & 0xFF;
/*  423 */       this.bbwi.position(this.bbwi.position() + 1);
/*      */     } else {
/*  425 */       i = this.bbwi.byteBuffer.get(this.bbwi.position()) & 0xFF;
/*  426 */       this.bbwi.position(this.bbwi.position() + 1);
/*  427 */       j = this.bbwi.byteBuffer.get(this.bbwi.position()) & 0xFF;
/*  428 */       this.bbwi.position(this.bbwi.position() + 1);
/*      */     } 
/*      */     
/*  431 */     return (char)((i << 8) + (j << 0));
/*      */   }
/*      */ 
/*      */   
/*      */   public final byte read_octet() {
/*  436 */     alignAndCheck(1, 1);
/*      */     
/*  438 */     byte b = this.bbwi.byteBuffer.get(this.bbwi.position());
/*  439 */     this.bbwi.position(this.bbwi.position() + 1);
/*      */     
/*  441 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   public final short read_short() {
/*      */     int i, j;
/*  447 */     alignAndCheck(2, 2);
/*      */     
/*  449 */     if (this.littleEndian) {
/*  450 */       j = this.bbwi.byteBuffer.get(this.bbwi.position()) << 0 & 0xFF;
/*  451 */       this.bbwi.position(this.bbwi.position() + 1);
/*  452 */       i = this.bbwi.byteBuffer.get(this.bbwi.position()) << 8 & 0xFF00;
/*  453 */       this.bbwi.position(this.bbwi.position() + 1);
/*      */     } else {
/*  455 */       i = this.bbwi.byteBuffer.get(this.bbwi.position()) << 8 & 0xFF00;
/*  456 */       this.bbwi.position(this.bbwi.position() + 1);
/*  457 */       j = this.bbwi.byteBuffer.get(this.bbwi.position()) << 0 & 0xFF;
/*  458 */       this.bbwi.position(this.bbwi.position() + 1);
/*      */     } 
/*      */     
/*  461 */     return (short)(i | j);
/*      */   }
/*      */   
/*      */   public final short read_ushort() {
/*  465 */     return read_short();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int read_long() {
/*      */     int i, j, k, m;
/*  471 */     alignAndCheck(4, 4);
/*      */     
/*  473 */     int n = this.bbwi.position();
/*  474 */     if (this.littleEndian) {
/*  475 */       m = this.bbwi.byteBuffer.get(n++) & 0xFF;
/*  476 */       k = this.bbwi.byteBuffer.get(n++) & 0xFF;
/*  477 */       j = this.bbwi.byteBuffer.get(n++) & 0xFF;
/*  478 */       i = this.bbwi.byteBuffer.get(n++) & 0xFF;
/*      */     } else {
/*  480 */       i = this.bbwi.byteBuffer.get(n++) & 0xFF;
/*  481 */       j = this.bbwi.byteBuffer.get(n++) & 0xFF;
/*  482 */       k = this.bbwi.byteBuffer.get(n++) & 0xFF;
/*  483 */       m = this.bbwi.byteBuffer.get(n++) & 0xFF;
/*      */     } 
/*  485 */     this.bbwi.position(n);
/*      */     
/*  487 */     return i << 24 | j << 16 | k << 8 | m;
/*      */   }
/*      */   
/*      */   public final int read_ulong() {
/*  491 */     return read_long();
/*      */   }
/*      */ 
/*      */   
/*      */   public final long read_longlong() {
/*      */     long l1, l2;
/*  497 */     alignAndCheck(8, 8);
/*      */     
/*  499 */     if (this.littleEndian) {
/*  500 */       l2 = read_long() & 0xFFFFFFFFL;
/*  501 */       l1 = read_long() << 32L;
/*      */     } else {
/*  503 */       l1 = read_long() << 32L;
/*  504 */       l2 = read_long() & 0xFFFFFFFFL;
/*      */     } 
/*      */     
/*  507 */     return l1 | l2;
/*      */   }
/*      */   
/*      */   public final long read_ulonglong() {
/*  511 */     return read_longlong();
/*      */   }
/*      */   
/*      */   public final float read_float() {
/*  515 */     return Float.intBitsToFloat(read_long());
/*      */   }
/*      */   
/*      */   public final double read_double() {
/*  519 */     return Double.longBitsToDouble(read_longlong());
/*      */   }
/*      */   
/*      */   protected final void checkForNegativeLength(int paramInt) {
/*  523 */     if (paramInt < 0) {
/*  524 */       throw this.wrapper.negativeStringLength(CompletionStatus.COMPLETED_MAYBE, new Integer(paramInt));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final String readStringOrIndirection(boolean paramBoolean) {
/*  530 */     int i = read_long();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  535 */     if (paramBoolean) {
/*  536 */       if (i == -1) {
/*  537 */         return null;
/*      */       }
/*  539 */       this.stringIndirection = get_offset() - 4;
/*      */     } 
/*      */     
/*  542 */     checkForNegativeLength(i);
/*      */     
/*  544 */     return internalReadString(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String internalReadString(int paramInt) {
/*  554 */     if (paramInt == 0) {
/*  555 */       return new String("");
/*      */     }
/*  557 */     char[] arrayOfChar = getConvertedChars(paramInt - 1, getCharConverter());
/*      */ 
/*      */     
/*  560 */     read_octet();
/*      */     
/*  562 */     return new String(arrayOfChar, 0, getCharConverter().getNumChars());
/*      */   }
/*      */   
/*      */   public final String read_string() {
/*  566 */     return readStringOrIndirection(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String read_wstring() {
/*  572 */     if (ORBUtility.isForeignORB(this.orb)) {
/*  573 */       throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */     
/*  576 */     int i = read_long();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  586 */     if (i == 0) {
/*  587 */       return new String("");
/*      */     }
/*  589 */     checkForNegativeLength(i);
/*      */     
/*  591 */     i--;
/*  592 */     char[] arrayOfChar = new char[i];
/*      */     
/*  594 */     for (byte b = 0; b < i; b++) {
/*  595 */       arrayOfChar[b] = read_wchar();
/*      */     }
/*      */     
/*  598 */     read_wchar();
/*      */ 
/*      */     
/*  601 */     return new String(arrayOfChar);
/*      */   }
/*      */   
/*      */   public final void read_octet_array(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  605 */     if (paramArrayOfbyte == null) {
/*  606 */       throw this.wrapper.nullParam();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  613 */     if (paramInt2 == 0) {
/*      */       return;
/*      */     }
/*  616 */     alignAndCheck(1, 1);
/*      */     
/*  618 */     int i = paramInt1;
/*  619 */     while (i < paramInt2 + paramInt1) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  624 */       int j = this.bbwi.buflen - this.bbwi.position();
/*  625 */       if (j <= 0) {
/*  626 */         grow(1, 1);
/*  627 */         j = this.bbwi.buflen - this.bbwi.position();
/*      */       } 
/*  629 */       int m = paramInt2 + paramInt1 - i;
/*  630 */       int k = (m < j) ? m : j;
/*      */ 
/*      */       
/*  633 */       for (byte b = 0; b < k; b++) {
/*  634 */         paramArrayOfbyte[i + b] = this.bbwi.byteBuffer.get(this.bbwi.position() + b);
/*      */       }
/*      */       
/*  637 */       this.bbwi.position(this.bbwi.position() + k);
/*      */       
/*  639 */       i += k;
/*      */     } 
/*      */   }
/*      */   
/*      */   public Principal read_Principal() {
/*  644 */     int i = read_long();
/*  645 */     byte[] arrayOfByte = new byte[i];
/*  646 */     read_octet_array(arrayOfByte, 0, i);
/*      */     
/*  648 */     PrincipalImpl principalImpl = new PrincipalImpl();
/*  649 */     principalImpl.name(arrayOfByte);
/*  650 */     return (Principal)principalImpl;
/*      */   }
/*      */   
/*      */   public TypeCode read_TypeCode() {
/*  654 */     TypeCodeImpl typeCodeImpl = new TypeCodeImpl(this.orb);
/*  655 */     typeCodeImpl.read_value(this.parent);
/*  656 */     return (TypeCode)typeCodeImpl;
/*      */   }
/*      */   
/*      */   public Any read_any() {
/*  660 */     Any any = this.orb.create_any();
/*  661 */     TypeCodeImpl typeCodeImpl = new TypeCodeImpl(this.orb);
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
/*  672 */       typeCodeImpl.read_value(this.parent);
/*  673 */     } catch (MARSHAL mARSHAL) {
/*  674 */       if (typeCodeImpl.kind().value() != 29) {
/*  675 */         throw mARSHAL;
/*      */       }
/*      */       
/*  678 */       dprintThrowable((Throwable)mARSHAL);
/*      */     } 
/*      */     
/*  681 */     any.read_value((InputStream)this.parent, (TypeCode)typeCodeImpl);
/*      */     
/*  683 */     return any;
/*      */   }
/*      */   
/*      */   public Object read_Object() {
/*  687 */     return read_Object(null);
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
/*      */   public Object read_Object(Class<?> paramClass) {
/*  709 */     IOR iOR = IORFactories.makeIOR(this.parent);
/*  710 */     if (iOR.isNil()) {
/*  711 */       return null;
/*      */     }
/*      */     
/*  714 */     PresentationManager.StubFactoryFactory stubFactoryFactory = ORB.getStubFactoryFactory();
/*  715 */     String str = iOR.getProfile().getCodebase();
/*  716 */     PresentationManager.StubFactory stubFactory = null;
/*      */     
/*  718 */     if (paramClass == null) {
/*  719 */       RepositoryId repositoryId = RepositoryId.cache.getId(iOR.getTypeId());
/*  720 */       String str1 = repositoryId.getClassName();
/*  721 */       this.orb.validateIORClass(str1);
/*  722 */       boolean bool = repositoryId.isIDLType();
/*      */       
/*  724 */       if (str1 == null || str1.equals("")) {
/*  725 */         stubFactory = null;
/*      */       } else {
/*      */         
/*  728 */         try { stubFactory = stubFactoryFactory.createStubFactory(str1, bool, str, (Class)null, (ClassLoader)null);
/*      */            }
/*      */         
/*  731 */         catch (Exception exception)
/*      */         
/*      */         { 
/*      */ 
/*      */           
/*  736 */           stubFactory = null; } 
/*      */       } 
/*  738 */     } else if (StubAdapter.isStubClass(paramClass)) {
/*  739 */       stubFactory = PresentationDefaults.makeStaticStubFactory(paramClass);
/*      */     }
/*      */     else {
/*      */       
/*  743 */       boolean bool = IDLEntity.class.isAssignableFrom(paramClass);
/*  744 */       stubFactory = stubFactoryFactory.createStubFactory(paramClass.getName(), bool, str, paramClass, paramClass
/*  745 */           .getClassLoader());
/*      */     } 
/*  747 */     return internalIORToObject(iOR, stubFactory, this.orb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object internalIORToObject(IOR paramIOR, PresentationManager.StubFactory paramStubFactory, ORB paramORB) {
/*      */     CORBAObjectImpl cORBAObjectImpl;
/*  758 */     ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*      */ 
/*      */     
/*  761 */     Object object = paramIOR.getProfile().getServant();
/*  762 */     if (object != null) {
/*  763 */       if (object instanceof Tie) {
/*  764 */         String str = paramIOR.getProfile().getCodebase();
/*      */         
/*  766 */         Object object2 = (Object)Utility.loadStub((Tie)object, paramStubFactory, str, false);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  771 */         if (object2 != null) {
/*  772 */           return object2;
/*      */         }
/*  774 */         throw oRBUtilSystemException.readObjectException();
/*      */       } 
/*  776 */       if (object instanceof Object) {
/*  777 */         if (!(object instanceof org.omg.CORBA.portable.InvokeHandler))
/*      */         {
/*  779 */           return (Object)object;
/*      */         }
/*      */       } else {
/*  782 */         throw oRBUtilSystemException.badServantReadObject();
/*      */       } 
/*      */     } 
/*  785 */     CorbaClientDelegate corbaClientDelegate = ORBUtility.makeClientDelegate(paramIOR);
/*  786 */     Object object1 = null;
/*      */     try {
/*  788 */       object1 = paramStubFactory.makeStub();
/*  789 */     } catch (Throwable throwable) {
/*  790 */       oRBUtilSystemException.stubCreateError(throwable);
/*      */       
/*  792 */       if (throwable instanceof ThreadDeath) {
/*  793 */         throw (ThreadDeath)throwable;
/*      */       }
/*      */ 
/*      */       
/*  797 */       cORBAObjectImpl = new CORBAObjectImpl();
/*      */     } 
/*      */     
/*  800 */     StubAdapter.setDelegate(cORBAObjectImpl, (Delegate)corbaClientDelegate);
/*  801 */     return (Object)cORBAObjectImpl;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object read_abstract_interface() {
/*  806 */     return read_abstract_interface(null);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object read_abstract_interface(Class paramClass) {
/*  811 */     boolean bool = read_boolean();
/*      */     
/*  813 */     if (bool) {
/*  814 */       return read_Object(paramClass);
/*      */     }
/*  816 */     return read_value();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializable read_value() {
/*  822 */     return read_value((Class)null);
/*      */   }
/*      */   
/*      */   private Serializable handleIndirection() {
/*  826 */     int i = read_long() + get_offset() - 4;
/*  827 */     if (this.valueCache != null && this.valueCache.containsVal(i))
/*      */     {
/*  829 */       return (Serializable)this.valueCache
/*  830 */         .getKey(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  837 */     throw new IndirectionException(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readRepositoryIds(int paramInt, Class paramClass, String paramString) {
/*  844 */     return readRepositoryIds(paramInt, paramClass, paramString, null);
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
/*      */   private String readRepositoryIds(int paramInt, Class paramClass, String paramString, BoxedValueHelper paramBoxedValueHelper) {
/*  860 */     switch (this.repIdUtil.getTypeInfo(paramInt)) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/*  865 */         if (paramClass == null) {
/*  866 */           if (paramString != null)
/*  867 */             return paramString; 
/*  868 */           if (paramBoxedValueHelper != null) {
/*  869 */             return paramBoxedValueHelper.get_id();
/*      */           }
/*  871 */           throw this.wrapper.expectedTypeNullAndNoRepId(CompletionStatus.COMPLETED_MAYBE);
/*      */         } 
/*      */ 
/*      */         
/*  875 */         return this.repIdStrs.createForAnyType(paramClass);
/*      */       case 2:
/*  877 */         return read_repositoryId();
/*      */       case 6:
/*  879 */         return read_repositoryIds();
/*      */     } 
/*  881 */     throw this.wrapper.badValueTag(CompletionStatus.COMPLETED_MAYBE, 
/*  882 */         Integer.toHexString(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializable read_value(Class<?> paramClass) {
/*  889 */     int i = readValueTag();
/*      */ 
/*      */     
/*  892 */     if (i == 0) {
/*  893 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  897 */     if (i == -1) {
/*  898 */       return handleIndirection();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  903 */     int j = get_offset() - 4;
/*      */ 
/*      */ 
/*      */     
/*  907 */     boolean bool = this.isChunked;
/*      */     
/*  909 */     this.isChunked = this.repIdUtil.isChunkedEncoding(i);
/*      */     
/*  911 */     Serializable serializable = null;
/*      */     
/*  913 */     String str1 = null;
/*  914 */     if (this.repIdUtil.isCodeBasePresent(i)) {
/*  915 */       str1 = read_codebase_URL();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  920 */     String str2 = readRepositoryIds(i, paramClass, null);
/*      */ 
/*      */ 
/*      */     
/*  924 */     start_block();
/*      */ 
/*      */ 
/*      */     
/*  928 */     this.end_flag--;
/*  929 */     if (this.isChunked) {
/*  930 */       this.chunkedValueNestingLevel--;
/*      */     }
/*  932 */     if (str2.equals(this.repIdStrs.getWStringValueRepId())) {
/*  933 */       serializable = read_wstring();
/*      */     }
/*  935 */     else if (str2.equals(this.repIdStrs.getClassDescValueRepId())) {
/*      */ 
/*      */       
/*  938 */       Class clazz = readClass();
/*      */     } else {
/*      */       
/*  941 */       Class<?> clazz = paramClass;
/*      */ 
/*      */ 
/*      */       
/*  945 */       if (paramClass == null || 
/*  946 */         !str2.equals(this.repIdStrs.createForAnyType(paramClass)))
/*      */       {
/*  948 */         clazz = getClassFromString(str2, str1, paramClass);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  953 */       if (clazz == null)
/*      */       {
/*      */         
/*  956 */         throw this.wrapper.couldNotFindClass(CompletionStatus.COMPLETED_MAYBE, new ClassNotFoundException());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  961 */       if (clazz != null && IDLEntity.class
/*  962 */         .isAssignableFrom(clazz)) {
/*      */         
/*  964 */         Object object = readIDLValue(j, str2, clazz, str1);
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  974 */           if (this.valueHandler == null) {
/*  975 */             this.valueHandler = ORBUtility.createValueHandler();
/*      */           }
/*  977 */           serializable = this.valueHandler.readValue((InputStream)this.parent, j, clazz, str2, (RunTime)
/*      */ 
/*      */ 
/*      */               
/*  981 */               getCodeBase());
/*      */         }
/*  983 */         catch (SystemException systemException) {
/*      */ 
/*      */           
/*  986 */           throw systemException;
/*  987 */         } catch (Exception exception) {
/*  988 */           throw this.wrapper.valuehandlerReadException(CompletionStatus.COMPLETED_MAYBE, exception);
/*      */         }
/*  990 */         catch (Error error) {
/*  991 */           throw this.wrapper.valuehandlerReadError(CompletionStatus.COMPLETED_MAYBE, error);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1001 */     handleEndOfValue();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1006 */     readEndTag();
/*      */ 
/*      */     
/* 1009 */     if (this.valueCache == null)
/* 1010 */       this.valueCache = new CacheTable(this.orb, false); 
/* 1011 */     this.valueCache.put(serializable, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1018 */     this.isChunked = bool;
/* 1019 */     start_block();
/*      */     
/* 1021 */     return serializable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializable read_value(BoxedValueHelper paramBoxedValueHelper) {
/* 1027 */     int i = readValueTag();
/*      */     
/* 1029 */     if (i == 0)
/* 1030 */       return null; 
/* 1031 */     if (i == -1) {
/* 1032 */       int k = read_long() + get_offset() - 4;
/* 1033 */       if (this.valueCache != null && this.valueCache.containsVal(k))
/*      */       {
/* 1035 */         return (Serializable)this.valueCache
/* 1036 */           .getKey(k);
/*      */       }
/*      */ 
/*      */       
/* 1040 */       throw new IndirectionException(k);
/*      */     } 
/*      */ 
/*      */     
/* 1044 */     int j = get_offset() - 4;
/*      */ 
/*      */ 
/*      */     
/* 1048 */     boolean bool = this.isChunked;
/* 1049 */     this.isChunked = this.repIdUtil.isChunkedEncoding(i);
/*      */     
/* 1051 */     Object object = null;
/*      */     
/* 1053 */     String str1 = null;
/* 1054 */     if (this.repIdUtil.isCodeBasePresent(i)) {
/* 1055 */       str1 = read_codebase_URL();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1060 */     String str2 = readRepositoryIds(i, null, null, paramBoxedValueHelper);
/*      */ 
/*      */     
/* 1063 */     if (!str2.equals(paramBoxedValueHelper.get_id())) {
/* 1064 */       paramBoxedValueHelper = Utility.getHelper(null, str1, str2);
/*      */     }
/* 1066 */     start_block();
/* 1067 */     this.end_flag--;
/* 1068 */     if (this.isChunked) {
/* 1069 */       this.chunkedValueNestingLevel--;
/*      */     }
/* 1071 */     if (paramBoxedValueHelper instanceof ValueHelper) {
/* 1072 */       object = readIDLValueWithHelper((ValueHelper)paramBoxedValueHelper, j);
/*      */     } else {
/* 1074 */       this.valueIndirection = j;
/* 1075 */       object = paramBoxedValueHelper.read_value((InputStream)this.parent);
/*      */     } 
/*      */     
/* 1078 */     handleEndOfValue();
/* 1079 */     readEndTag();
/*      */ 
/*      */     
/* 1082 */     if (this.valueCache == null)
/* 1083 */       this.valueCache = new CacheTable(this.orb, false); 
/* 1084 */     this.valueCache.put(object, j);
/*      */ 
/*      */     
/* 1087 */     this.isChunked = bool;
/* 1088 */     start_block();
/*      */     
/* 1090 */     return (Serializable)object;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isCustomType(ValueHelper paramValueHelper) {
/*      */     try {
/* 1096 */       TypeCode typeCode = paramValueHelper.get_type();
/* 1097 */       int i = typeCode.kind().value();
/* 1098 */       if (i == 29) {
/* 1099 */         return (typeCode.type_modifier() == 1);
/*      */       }
/* 1101 */     } catch (BadKind badKind) {
/* 1102 */       throw this.wrapper.badKind(badKind);
/*      */     } 
/*      */     
/* 1105 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializable read_value(Serializable paramSerializable) {
/* 1115 */     if (this.valueCache == null)
/* 1116 */       this.valueCache = new CacheTable(this.orb, false); 
/* 1117 */     this.valueCache.put(paramSerializable, this.valueIndirection);
/*      */     
/* 1119 */     if (paramSerializable instanceof StreamableValue) {
/* 1120 */       ((StreamableValue)paramSerializable)._read((InputStream)this.parent);
/* 1121 */     } else if (paramSerializable instanceof CustomValue) {
/* 1122 */       ((CustomValue)paramSerializable).unmarshal(this.parent);
/*      */     } 
/* 1124 */     return paramSerializable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializable read_value(String paramString) {
/* 1133 */     int i = readValueTag();
/*      */     
/* 1135 */     if (i == 0)
/* 1136 */       return null; 
/* 1137 */     if (i == -1) {
/* 1138 */       int k = read_long() + get_offset() - 4;
/* 1139 */       if (this.valueCache != null && this.valueCache.containsVal(k))
/*      */       {
/* 1141 */         return (Serializable)this.valueCache
/* 1142 */           .getKey(k);
/*      */       }
/*      */ 
/*      */       
/* 1146 */       throw new IndirectionException(k);
/*      */     } 
/*      */ 
/*      */     
/* 1150 */     int j = get_offset() - 4;
/*      */ 
/*      */ 
/*      */     
/* 1154 */     boolean bool = this.isChunked;
/* 1155 */     this.isChunked = this.repIdUtil.isChunkedEncoding(i);
/*      */     
/* 1157 */     Serializable serializable = null;
/*      */     
/* 1159 */     String str1 = null;
/* 1160 */     if (this.repIdUtil.isCodeBasePresent(i)) {
/* 1161 */       str1 = read_codebase_URL();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1166 */     String str2 = readRepositoryIds(i, null, paramString);
/*      */ 
/*      */     
/* 1169 */     ValueFactory valueFactory = Utility.getFactory(null, str1, (ORB)this.orb, str2);
/*      */     
/* 1171 */     start_block();
/* 1172 */     this.end_flag--;
/* 1173 */     if (this.isChunked) {
/* 1174 */       this.chunkedValueNestingLevel--;
/*      */     }
/* 1176 */     this.valueIndirection = j;
/* 1177 */     serializable = valueFactory.read_value(this.parent);
/*      */     
/* 1179 */     handleEndOfValue();
/* 1180 */     readEndTag();
/*      */ 
/*      */     
/* 1183 */     if (this.valueCache == null)
/* 1184 */       this.valueCache = new CacheTable(this.orb, false); 
/* 1185 */     this.valueCache.put(serializable, j);
/*      */ 
/*      */     
/* 1188 */     this.isChunked = bool;
/* 1189 */     start_block();
/*      */     
/* 1191 */     return serializable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Class readClass() {
/* 1197 */     String str1 = null, str2 = null;
/*      */     
/* 1199 */     if (this.orb == null || 
/* 1200 */       ORBVersionFactory.getFOREIGN().equals(this.orb.getORBVersion()) || 
/* 1201 */       ORBVersionFactory.getNEWER().compareTo(this.orb.getORBVersion()) <= 0) {
/*      */       
/* 1203 */       str1 = (String)read_value(String.class);
/* 1204 */       str2 = (String)read_value(String.class);
/*      */     }
/*      */     else {
/*      */       
/* 1208 */       str2 = (String)read_value(String.class);
/* 1209 */       str1 = (String)read_value(String.class);
/*      */     } 
/*      */     
/* 1212 */     if (this.debug) {
/* 1213 */       dprint("readClass codebases: " + str1 + " rep Id: " + str2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1219 */     Class clazz = null;
/*      */ 
/*      */     
/* 1222 */     RepositoryIdInterface repositoryIdInterface = this.repIdStrs.getFromString(str2);
/*      */     
/*      */     try {
/* 1225 */       clazz = repositoryIdInterface.getClassFromType(str1);
/* 1226 */     } catch (ClassNotFoundException classNotFoundException) {
/* 1227 */       throw this.wrapper.cnfeReadClass(CompletionStatus.COMPLETED_MAYBE, classNotFoundException, repositoryIdInterface
/* 1228 */           .getClassName());
/* 1229 */     } catch (MalformedURLException malformedURLException) {
/* 1230 */       throw this.wrapper.malformedUrl(CompletionStatus.COMPLETED_MAYBE, malformedURLException, repositoryIdInterface
/* 1231 */           .getClassName(), str1);
/*      */     } 
/*      */     
/* 1234 */     return clazz;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Object readIDLValueWithHelper(ValueHelper paramValueHelper, int paramInt) {
/*      */     Method method;
/*      */     try {
/* 1242 */       Class[] arrayOfClass = { InputStream.class, paramValueHelper.get_class() };
/* 1243 */       method = paramValueHelper.getClass().getDeclaredMethod("read", arrayOfClass);
/*      */     }
/* 1245 */     catch (NoSuchMethodException noSuchMethodException) {
/* 1246 */       return paramValueHelper.read_value((InputStream)this.parent);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1252 */     CustomMarshal customMarshal = null;
/*      */     try {
/* 1254 */       customMarshal = (CustomMarshal)paramValueHelper.get_class().newInstance();
/* 1255 */     } catch (InstantiationException instantiationException) {
/* 1256 */       throw this.wrapper.couldNotInstantiateHelper(instantiationException, paramValueHelper
/* 1257 */           .get_class());
/* 1258 */     } catch (IllegalAccessException illegalAccessException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1265 */       return paramValueHelper.read_value((InputStream)this.parent);
/*      */     } 
/*      */ 
/*      */     
/* 1269 */     if (this.valueCache == null)
/* 1270 */       this.valueCache = new CacheTable(this.orb, false); 
/* 1271 */     this.valueCache.put(customMarshal, paramInt);
/*      */ 
/*      */     
/* 1274 */     if (customMarshal instanceof CustomMarshal && isCustomType(paramValueHelper)) {
/* 1275 */       ((CustomMarshal)customMarshal).unmarshal(this.parent);
/* 1276 */       return customMarshal;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1281 */       Object[] arrayOfObject = { this.parent, customMarshal };
/* 1282 */       method.invoke(paramValueHelper, arrayOfObject);
/* 1283 */       return customMarshal;
/* 1284 */     } catch (IllegalAccessException illegalAccessException) {
/* 1285 */       throw this.wrapper.couldNotInvokeHelperReadMethod(illegalAccessException, paramValueHelper.get_class());
/* 1286 */     } catch (InvocationTargetException invocationTargetException) {
/* 1287 */       throw this.wrapper.couldNotInvokeHelperReadMethod(invocationTargetException, paramValueHelper.get_class());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Object readBoxedIDLEntity(Class paramClass, String paramString) {
/* 1293 */     Class clazz = null;
/*      */     
/*      */     try {
/* 1296 */       ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader();
/*      */       
/* 1298 */       clazz = Utility.loadClassForClass(paramClass.getName() + "Helper", paramString, classLoader, paramClass, classLoader);
/*      */       
/* 1300 */       final Class helperClass = clazz;
/*      */       
/* 1302 */       final Class[] argTypes = { InputStream.class };
/*      */ 
/*      */ 
/*      */       
/* 1306 */       Method method = null;
/*      */       try {
/* 1308 */         method = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*      */             {
/*      */               public Object run() throws NoSuchMethodException {
/* 1311 */                 return helperClass.getDeclaredMethod("read", argTypes);
/*      */               }
/*      */             });
/*      */       }
/* 1315 */       catch (PrivilegedActionException privilegedActionException) {
/*      */         
/* 1317 */         throw (NoSuchMethodException)privilegedActionException.getException();
/*      */       } 
/*      */       
/* 1320 */       Object[] arrayOfObject = { this.parent };
/* 1321 */       return method.invoke(null, arrayOfObject);
/*      */     }
/* 1323 */     catch (ClassNotFoundException classNotFoundException) {
/* 1324 */       throw this.wrapper.couldNotInvokeHelperReadMethod(classNotFoundException, clazz);
/* 1325 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 1326 */       throw this.wrapper.couldNotInvokeHelperReadMethod(noSuchMethodException, clazz);
/* 1327 */     } catch (IllegalAccessException illegalAccessException) {
/* 1328 */       throw this.wrapper.couldNotInvokeHelperReadMethod(illegalAccessException, clazz);
/* 1329 */     } catch (InvocationTargetException invocationTargetException) {
/* 1330 */       throw this.wrapper.couldNotInvokeHelperReadMethod(invocationTargetException, clazz);
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
/*      */   private Object readIDLValue(int paramInt, String paramString1, Class<?> paramClass, String paramString2) {
/*      */     ValueFactory valueFactory;
/*      */     try {
/* 1349 */       valueFactory = Utility.getFactory(paramClass, paramString2, (ORB)this.orb, paramString1);
/* 1350 */     } catch (MARSHAL mARSHAL) {
/*      */ 
/*      */ 
/*      */       
/* 1354 */       if (!StreamableValue.class.isAssignableFrom(paramClass) && 
/* 1355 */         !CustomValue.class.isAssignableFrom(paramClass) && ValueBase.class
/* 1356 */         .isAssignableFrom(paramClass)) {
/*      */         
/* 1358 */         BoxedValueHelper boxedValueHelper = Utility.getHelper(paramClass, paramString2, paramString1);
/* 1359 */         if (boxedValueHelper instanceof ValueHelper) {
/* 1360 */           return readIDLValueWithHelper((ValueHelper)boxedValueHelper, paramInt);
/*      */         }
/* 1362 */         return boxedValueHelper.read_value((InputStream)this.parent);
/*      */       } 
/*      */ 
/*      */       
/* 1366 */       return readBoxedIDLEntity(paramClass, paramString2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1371 */     this.valueIndirection = paramInt;
/* 1372 */     return valueFactory.read_value(this.parent);
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
/*      */   private void readEndTag() {
/* 1386 */     if (this.isChunked) {
/*      */ 
/*      */       
/* 1389 */       int i = read_long();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1396 */       if (i >= 0) {
/* 1397 */         throw this.wrapper.positiveEndTag(CompletionStatus.COMPLETED_MAYBE, new Integer(i), new Integer(
/* 1398 */               get_offset() - 4));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1405 */       if (this.orb == null || 
/* 1406 */         ORBVersionFactory.getFOREIGN().equals(this.orb.getORBVersion()) || 
/* 1407 */         ORBVersionFactory.getNEWER().compareTo(this.orb.getORBVersion()) <= 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1412 */         if (i < this.chunkedValueNestingLevel) {
/* 1413 */           throw this.wrapper.unexpectedEnclosingValuetype(CompletionStatus.COMPLETED_MAYBE, new Integer(i), new Integer(this.chunkedValueNestingLevel));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1423 */         if (i != this.chunkedValueNestingLevel) {
/* 1424 */           this.bbwi.position(this.bbwi.position() - 4);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1433 */       else if (i != this.end_flag) {
/* 1434 */         this.bbwi.position(this.bbwi.position() - 4);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1440 */       this.chunkedValueNestingLevel++;
/*      */     } 
/*      */ 
/*      */     
/* 1444 */     this.end_flag++;
/*      */   }
/*      */   
/*      */   protected int get_offset() {
/* 1448 */     return this.bbwi.position();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void start_block() {
/* 1454 */     if (!this.isChunked) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1459 */     this.blockLength = 2147483392;
/*      */     
/* 1461 */     this.blockLength = read_long();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1466 */     if (this.blockLength > 0 && this.blockLength < 2147483392) {
/* 1467 */       this.blockLength += get_offset();
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 1475 */       this.blockLength = 2147483392;
/*      */       
/* 1477 */       this.bbwi.position(this.bbwi.position() - 4);
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
/*      */   private void handleEndOfValue() {
/* 1491 */     if (!this.isChunked) {
/*      */       return;
/*      */     }
/*      */     
/* 1495 */     while (this.blockLength != 2147483392) {
/* 1496 */       end_block();
/* 1497 */       start_block();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1507 */     int i = read_long();
/* 1508 */     this.bbwi.position(this.bbwi.position() - 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1515 */     if (i < 0) {
/*      */       return;
/*      */     }
/* 1518 */     if (i == 0 || i >= 2147483392) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1531 */       read_value();
/* 1532 */       handleEndOfValue();
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1538 */       throw this.wrapper.couldNotSkipBytes(CompletionStatus.COMPLETED_MAYBE, new Integer(i), new Integer(
/* 1539 */             get_offset()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void end_block() {
/* 1546 */     if (this.blockLength != 2147483392) {
/* 1547 */       if (this.blockLength == get_offset()) {
/*      */         
/* 1549 */         this.blockLength = 2147483392;
/*      */ 
/*      */       
/*      */       }
/* 1553 */       else if (this.blockLength > get_offset()) {
/* 1554 */         skipToOffset(this.blockLength);
/*      */       } else {
/* 1556 */         throw this.wrapper.badChunkLength(new Integer(this.blockLength), new Integer(
/* 1557 */               get_offset()));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int readValueTag() {
/* 1565 */     return read_long();
/*      */   }
/*      */   
/*      */   public ORB orb() {
/* 1569 */     return (ORB)this.orb;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void read_boolean_array(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) {
/* 1575 */     for (byte b = 0; b < paramInt2; b++) {
/* 1576 */       paramArrayOfboolean[b + paramInt1] = read_boolean();
/*      */     }
/*      */   }
/*      */   
/*      */   public final void read_char_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 1581 */     for (byte b = 0; b < paramInt2; b++) {
/* 1582 */       paramArrayOfchar[b + paramInt1] = read_char();
/*      */     }
/*      */   }
/*      */   
/*      */   public final void read_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 1587 */     for (byte b = 0; b < paramInt2; b++) {
/* 1588 */       paramArrayOfchar[b + paramInt1] = read_wchar();
/*      */     }
/*      */   }
/*      */   
/*      */   public final void read_short_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 1593 */     for (byte b = 0; b < paramInt2; b++) {
/* 1594 */       paramArrayOfshort[b + paramInt1] = read_short();
/*      */     }
/*      */   }
/*      */   
/*      */   public final void read_ushort_array(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 1599 */     read_short_array(paramArrayOfshort, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public final void read_long_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 1603 */     for (byte b = 0; b < paramInt2; b++) {
/* 1604 */       paramArrayOfint[b + paramInt1] = read_long();
/*      */     }
/*      */   }
/*      */   
/*      */   public final void read_ulong_array(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 1609 */     read_long_array(paramArrayOfint, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public final void read_longlong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 1613 */     for (byte b = 0; b < paramInt2; b++) {
/* 1614 */       paramArrayOflong[b + paramInt1] = read_longlong();
/*      */     }
/*      */   }
/*      */   
/*      */   public final void read_ulonglong_array(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 1619 */     read_longlong_array(paramArrayOflong, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public final void read_float_array(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 1623 */     for (byte b = 0; b < paramInt2; b++) {
/* 1624 */       paramArrayOffloat[b + paramInt1] = read_float();
/*      */     }
/*      */   }
/*      */   
/*      */   public final void read_double_array(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 1629 */     for (byte b = 0; b < paramInt2; b++) {
/* 1630 */       paramArrayOfdouble[b + paramInt1] = read_double();
/*      */     }
/*      */   }
/*      */   
/*      */   public final void read_any_array(Any[] paramArrayOfAny, int paramInt1, int paramInt2) {
/* 1635 */     for (byte b = 0; b < paramInt2; b++) {
/* 1636 */       paramArrayOfAny[b + paramInt1] = read_any();
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
/*      */   private String read_repositoryIds() {
/* 1658 */     int i = read_long();
/* 1659 */     if (i == -1) {
/* 1660 */       int k = read_long() + get_offset() - 4;
/* 1661 */       if (this.repositoryIdCache != null && this.repositoryIdCache.containsOrderedVal(k)) {
/* 1662 */         return (String)this.repositoryIdCache.getKey(k);
/*      */       }
/* 1664 */       throw this.wrapper.unableToLocateRepIdArray(new Integer(k));
/*      */     } 
/*      */ 
/*      */     
/* 1668 */     int j = get_offset();
/* 1669 */     String str = read_repositoryId();
/* 1670 */     if (this.repositoryIdCache == null)
/* 1671 */       this.repositoryIdCache = new CacheTable(this.orb, false); 
/* 1672 */     this.repositoryIdCache.put(str, j);
/*      */ 
/*      */ 
/*      */     
/* 1676 */     for (byte b = 1; b < i; b++) {
/* 1677 */       read_repositoryId();
/*      */     }
/*      */     
/* 1680 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final String read_repositoryId() {
/* 1686 */     String str = readStringOrIndirection(true);
/*      */     
/* 1688 */     if (str == null) {
/* 1689 */       int i = read_long() + get_offset() - 4;
/*      */       
/* 1691 */       if (this.repositoryIdCache != null && this.repositoryIdCache.containsOrderedVal(i)) {
/* 1692 */         return (String)this.repositoryIdCache.getKey(i);
/*      */       }
/* 1694 */       throw this.wrapper.badRepIdIndirection(CompletionStatus.COMPLETED_MAYBE, new Integer(this.bbwi
/* 1695 */             .position()));
/*      */     } 
/* 1697 */     if (this.repositoryIdCache == null)
/* 1698 */       this.repositoryIdCache = new CacheTable(this.orb, false); 
/* 1699 */     this.repositoryIdCache.put(str, this.stringIndirection);
/*      */ 
/*      */     
/* 1702 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   private final String read_codebase_URL() {
/* 1707 */     String str = readStringOrIndirection(true);
/*      */     
/* 1709 */     if (str == null) {
/* 1710 */       int i = read_long() + get_offset() - 4;
/*      */       
/* 1712 */       if (this.codebaseCache != null && this.codebaseCache.containsVal(i)) {
/* 1713 */         return (String)this.codebaseCache.getKey(i);
/*      */       }
/* 1715 */       throw this.wrapper.badCodebaseIndirection(CompletionStatus.COMPLETED_MAYBE, new Integer(this.bbwi
/*      */             
/* 1717 */             .position()));
/*      */     } 
/* 1719 */     if (this.codebaseCache == null)
/* 1720 */       this.codebaseCache = new CacheTable(this.orb, false); 
/* 1721 */     this.codebaseCache.put(str, this.stringIndirection);
/*      */ 
/*      */     
/* 1724 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object read_Abstract() {
/* 1730 */     return read_abstract_interface();
/*      */   }
/*      */   
/*      */   public Serializable read_Value() {
/* 1734 */     return read_value();
/*      */   }
/*      */   
/*      */   public void read_any_array(AnySeqHolder paramAnySeqHolder, int paramInt1, int paramInt2) {
/* 1738 */     read_any_array(paramAnySeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_boolean_array(BooleanSeqHolder paramBooleanSeqHolder, int paramInt1, int paramInt2) {
/* 1742 */     read_boolean_array(paramBooleanSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_char_array(CharSeqHolder paramCharSeqHolder, int paramInt1, int paramInt2) {
/* 1746 */     read_char_array(paramCharSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_wchar_array(WCharSeqHolder paramWCharSeqHolder, int paramInt1, int paramInt2) {
/* 1750 */     read_wchar_array(paramWCharSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_octet_array(OctetSeqHolder paramOctetSeqHolder, int paramInt1, int paramInt2) {
/* 1754 */     read_octet_array(paramOctetSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_short_array(ShortSeqHolder paramShortSeqHolder, int paramInt1, int paramInt2) {
/* 1758 */     read_short_array(paramShortSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_ushort_array(UShortSeqHolder paramUShortSeqHolder, int paramInt1, int paramInt2) {
/* 1762 */     read_ushort_array(paramUShortSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_long_array(LongSeqHolder paramLongSeqHolder, int paramInt1, int paramInt2) {
/* 1766 */     read_long_array(paramLongSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_ulong_array(ULongSeqHolder paramULongSeqHolder, int paramInt1, int paramInt2) {
/* 1770 */     read_ulong_array(paramULongSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_ulonglong_array(ULongLongSeqHolder paramULongLongSeqHolder, int paramInt1, int paramInt2) {
/* 1774 */     read_ulonglong_array(paramULongLongSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_longlong_array(LongLongSeqHolder paramLongLongSeqHolder, int paramInt1, int paramInt2) {
/* 1778 */     read_longlong_array(paramLongLongSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_float_array(FloatSeqHolder paramFloatSeqHolder, int paramInt1, int paramInt2) {
/* 1782 */     read_float_array(paramFloatSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void read_double_array(DoubleSeqHolder paramDoubleSeqHolder, int paramInt1, int paramInt2) {
/* 1786 */     read_double_array(paramDoubleSeqHolder.value, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public BigDecimal read_fixed(short paramShort1, short paramShort2) {
/* 1791 */     StringBuffer stringBuffer = read_fixed_buffer();
/* 1792 */     if (paramShort1 != stringBuffer.length())
/* 1793 */       throw this.wrapper.badFixed(new Integer(paramShort1), new Integer(stringBuffer
/* 1794 */             .length())); 
/* 1795 */     stringBuffer.insert(paramShort1 - paramShort2, '.');
/* 1796 */     return new BigDecimal(stringBuffer.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public BigDecimal read_fixed() {
/* 1801 */     return new BigDecimal(read_fixed_buffer().toString());
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
/* 1812 */     StringBuffer stringBuffer = new StringBuffer(64);
/*      */ 
/*      */ 
/*      */     
/* 1816 */     boolean bool1 = false;
/* 1817 */     boolean bool2 = true;
/* 1818 */     while (bool2) {
/* 1819 */       byte b = read_octet();
/* 1820 */       int i = (b & 0xF0) >> 4;
/* 1821 */       int j = b & 0xF;
/* 1822 */       if (bool1 || i != 0) {
/* 1823 */         stringBuffer.append(Character.forDigit(i, 10));
/* 1824 */         bool1 = true;
/*      */       } 
/* 1826 */       if (j == 12) {
/*      */         
/* 1828 */         if (!bool1)
/*      */         {
/* 1830 */           return new StringBuffer("0.0");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1835 */         bool2 = false; continue;
/* 1836 */       }  if (j == 13) {
/*      */         
/* 1838 */         stringBuffer.insert(0, '-');
/* 1839 */         bool2 = false; continue;
/*      */       } 
/* 1841 */       stringBuffer.append(Character.forDigit(j, 10));
/* 1842 */       bool1 = true;
/*      */     } 
/*      */     
/* 1845 */     return stringBuffer;
/*      */   }
/*      */ 
/*      */   
/* 1849 */   private static final String[] _ids = new String[] { "IDL:omg.org/CORBA/DataInputStream:1.0" };
/*      */   
/*      */   public String[] _truncatable_ids() {
/* 1852 */     if (_ids == null) {
/* 1853 */       return null;
/*      */     }
/* 1855 */     return (String[])_ids.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void printBuffer() {
/* 1861 */     printBuffer(this.bbwi);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void printBuffer(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 1866 */     System.out.println("----- Input Buffer -----");
/* 1867 */     System.out.println();
/* 1868 */     System.out.println("Current position: " + paramByteBufferWithInfo.position());
/* 1869 */     System.out.println("Total length : " + paramByteBufferWithInfo.buflen);
/* 1870 */     System.out.println();
/*      */ 
/*      */     
/*      */     try {
/* 1874 */       char[] arrayOfChar = new char[16];
/*      */       
/* 1876 */       for (byte b = 0; b < paramByteBufferWithInfo.buflen; b += 16)
/*      */       {
/* 1878 */         byte b1 = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1884 */         while (b1 < 16 && b1 + b < paramByteBufferWithInfo.buflen) {
/* 1885 */           int i = paramByteBufferWithInfo.byteBuffer.get(b + b1);
/* 1886 */           if (i < 0)
/* 1887 */             i = 256 + i; 
/* 1888 */           String str = Integer.toHexString(i);
/* 1889 */           if (str.length() == 1)
/* 1890 */             str = "0" + str; 
/* 1891 */           System.out.print(str + " ");
/* 1892 */           b1++;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1898 */         while (b1 < 16) {
/* 1899 */           System.out.print("   ");
/* 1900 */           b1++;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1905 */         byte b2 = 0;
/* 1906 */         while (b2 < 16 && b2 + b < paramByteBufferWithInfo.buflen) {
/* 1907 */           if (ORBUtility.isPrintable((char)paramByteBufferWithInfo.byteBuffer.get(b + b2))) {
/* 1908 */             arrayOfChar[b2] = (char)paramByteBufferWithInfo.byteBuffer.get(b + b2);
/*      */           } else {
/* 1910 */             arrayOfChar[b2] = '.';
/* 1911 */           }  b2++;
/*      */         } 
/* 1913 */         System.out.println(new String(arrayOfChar, 0, b2));
/*      */       }
/*      */     
/* 1916 */     } catch (Throwable throwable) {
/* 1917 */       throwable.printStackTrace();
/*      */     } 
/*      */     
/* 1920 */     System.out.println("------------------------");
/*      */   }
/*      */   
/*      */   public ByteBuffer getByteBuffer() {
/* 1924 */     ByteBuffer byteBuffer = null;
/* 1925 */     if (this.bbwi != null) {
/* 1926 */       byteBuffer = this.bbwi.byteBuffer;
/*      */     }
/* 1928 */     return byteBuffer;
/*      */   }
/*      */   
/*      */   public int getBufferLength() {
/* 1932 */     return this.bbwi.buflen;
/*      */   }
/*      */   
/*      */   public void setBufferLength(int paramInt) {
/* 1936 */     this.bbwi.buflen = paramInt;
/* 1937 */     this.bbwi.byteBuffer.limit(this.bbwi.buflen);
/*      */   }
/*      */   
/*      */   public void setByteBufferWithInfo(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 1941 */     this.bbwi = paramByteBufferWithInfo;
/*      */   }
/*      */   
/*      */   public void setByteBuffer(ByteBuffer paramByteBuffer) {
/* 1945 */     this.bbwi.byteBuffer = paramByteBuffer;
/*      */   }
/*      */   
/*      */   public int getIndex() {
/* 1949 */     return this.bbwi.position();
/*      */   }
/*      */   
/*      */   public void setIndex(int paramInt) {
/* 1953 */     this.bbwi.position(paramInt);
/*      */   }
/*      */   
/*      */   public boolean isLittleEndian() {
/* 1957 */     return this.littleEndian;
/*      */   }
/*      */   
/*      */   public void orb(ORB paramORB) {
/* 1961 */     this.orb = (ORB)paramORB;
/*      */   }
/*      */   
/*      */   public BufferManagerRead getBufferManager() {
/* 1965 */     return this.bufferManagerRead;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void skipToOffset(int paramInt) {
/* 1971 */     int i = paramInt - get_offset();
/*      */     
/* 1973 */     int j = 0;
/*      */     
/* 1975 */     while (j < i) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1980 */       int k = this.bbwi.buflen - this.bbwi.position();
/* 1981 */       if (k <= 0) {
/* 1982 */         grow(1, 1);
/* 1983 */         k = this.bbwi.buflen - this.bbwi.position();
/*      */       } 
/*      */       
/* 1986 */       int n = i - j;
/* 1987 */       int m = (n < k) ? n : k;
/* 1988 */       this.bbwi.position(this.bbwi.position() + m);
/* 1989 */       j += m;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1996 */   protected MarkAndResetHandler markAndResetHandler = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class StreamMemento
/*      */   {
/* 2014 */     private int blockLength_ = CDRInputStream_1_0.this.blockLength;
/* 2015 */     private int end_flag_ = CDRInputStream_1_0.this.end_flag;
/* 2016 */     private int chunkedValueNestingLevel_ = CDRInputStream_1_0.this.chunkedValueNestingLevel;
/* 2017 */     private int valueIndirection_ = CDRInputStream_1_0.this.valueIndirection;
/* 2018 */     private int stringIndirection_ = CDRInputStream_1_0.this.stringIndirection;
/* 2019 */     private boolean isChunked_ = CDRInputStream_1_0.this.isChunked;
/* 2020 */     private ValueHandler valueHandler_ = CDRInputStream_1_0.this.valueHandler;
/* 2021 */     private boolean specialNoOptionalDataState_ = CDRInputStream_1_0.this.specialNoOptionalDataState;
/* 2022 */     private ByteBufferWithInfo bbwi_ = new ByteBufferWithInfo(CDRInputStream_1_0.this.bbwi);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object createStreamMemento() {
/* 2027 */     return new StreamMemento();
/*      */   }
/*      */ 
/*      */   
/*      */   public void restoreInternalState(Object paramObject) {
/* 2032 */     StreamMemento streamMemento = (StreamMemento)paramObject;
/*      */     
/* 2034 */     this.blockLength = streamMemento.blockLength_;
/* 2035 */     this.end_flag = streamMemento.end_flag_;
/* 2036 */     this.chunkedValueNestingLevel = streamMemento.chunkedValueNestingLevel_;
/* 2037 */     this.valueIndirection = streamMemento.valueIndirection_;
/* 2038 */     this.stringIndirection = streamMemento.stringIndirection_;
/* 2039 */     this.isChunked = streamMemento.isChunked_;
/* 2040 */     this.valueHandler = streamMemento.valueHandler_;
/* 2041 */     this.specialNoOptionalDataState = streamMemento.specialNoOptionalDataState_;
/* 2042 */     this.bbwi = streamMemento.bbwi_;
/*      */   }
/*      */   
/*      */   public int getPosition() {
/* 2046 */     return get_offset();
/*      */   }
/*      */   
/*      */   public void mark(int paramInt) {
/* 2050 */     this.markAndResetHandler.mark(this);
/*      */   }
/*      */   
/*      */   public void reset() {
/* 2054 */     this.markAndResetHandler.reset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CodeBase getCodeBase() {
/* 2064 */     return this.parent.getCodeBase();
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
/*      */   private Class getClassFromString(String paramString1, String paramString2, Class paramClass) {
/* 2079 */     RepositoryIdInterface repositoryIdInterface = this.repIdStrs.getFromString(paramString1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2085 */       return repositoryIdInterface.getClassFromType(paramClass, paramString2);
/*      */     }
/* 2087 */     catch (ClassNotFoundException classNotFoundException) {
/*      */ 
/*      */       
/*      */       try {
/* 2091 */         if (getCodeBase() == null) {
/* 2092 */           return null;
/*      */         }
/*      */ 
/*      */         
/* 2096 */         paramString2 = getCodeBase().implementation(paramString1);
/*      */ 
/*      */ 
/*      */         
/* 2100 */         if (paramString2 == null) {
/* 2101 */           return null;
/*      */         }
/* 2103 */         return repositoryIdInterface.getClassFromType(paramClass, paramString2);
/*      */       }
/* 2105 */       catch (ClassNotFoundException classNotFoundException1) {
/* 2106 */         dprintThrowable(classNotFoundException1);
/*      */         
/* 2108 */         return null;
/*      */       }
/*      */     
/* 2111 */     } catch (MalformedURLException malformedURLException) {
/*      */       
/* 2113 */       throw this.wrapper.malformedUrl(CompletionStatus.COMPLETED_MAYBE, malformedURLException, paramString1, paramString2);
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
/*      */   private Class getClassFromString(String paramString1, String paramString2) {
/* 2128 */     RepositoryIdInterface repositoryIdInterface = this.repIdStrs.getFromString(paramString1);
/*      */     
/* 2130 */     for (byte b = 0; b < 3; b++) {
/*      */ 
/*      */       
/*      */       try {
/* 2134 */         switch (b) {
/*      */ 
/*      */           
/*      */           case 0:
/* 2138 */             return repositoryIdInterface.getClassFromType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 2:
/* 2146 */             paramString2 = getCodeBase().implementation(paramString1);
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/* 2151 */         if (paramString2 != null)
/*      */         {
/*      */           
/* 2154 */           return repositoryIdInterface.getClassFromType(paramString2);
/*      */         }
/* 2156 */       } catch (ClassNotFoundException classNotFoundException) {
/*      */ 
/*      */       
/* 2159 */       } catch (MalformedURLException malformedURLException) {
/* 2160 */         throw this.wrapper.malformedUrl(CompletionStatus.COMPLETED_MAYBE, malformedURLException, paramString1, paramString2);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2166 */     dprint("getClassFromString failed with rep id " + paramString1 + " and codebase " + paramString2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2171 */     return null;
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
/*      */   char[] getConvertedChars(int paramInt, CodeSetConversion.BTCConverter paramBTCConverter) {
/* 2183 */     if (this.bbwi.buflen - this.bbwi.position() >= paramInt) {
/*      */       byte[] arrayOfByte1;
/*      */ 
/*      */ 
/*      */       
/* 2188 */       if (this.bbwi.byteBuffer.hasArray()) {
/*      */         
/* 2190 */         arrayOfByte1 = this.bbwi.byteBuffer.array();
/*      */       }
/*      */       else {
/*      */         
/* 2194 */         arrayOfByte1 = new byte[this.bbwi.buflen];
/*      */ 
/*      */         
/* 2197 */         for (byte b = 0; b < this.bbwi.buflen; b++)
/* 2198 */           arrayOfByte1[b] = this.bbwi.byteBuffer.get(b); 
/*      */       } 
/* 2200 */       char[] arrayOfChar = paramBTCConverter.getChars(arrayOfByte1, this.bbwi.position(), paramInt);
/*      */       
/* 2202 */       this.bbwi.position(this.bbwi.position() + paramInt);
/* 2203 */       return arrayOfChar;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2208 */     byte[] arrayOfByte = new byte[paramInt];
/* 2209 */     read_octet_array(arrayOfByte, 0, arrayOfByte.length);
/*      */     
/* 2211 */     return paramBTCConverter.getChars(arrayOfByte, 0, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   protected CodeSetConversion.BTCConverter getCharConverter() {
/* 2216 */     if (this.charConverter == null) {
/* 2217 */       this.charConverter = this.parent.createCharBTCConverter();
/*      */     }
/* 2219 */     return this.charConverter;
/*      */   }
/*      */   
/*      */   protected CodeSetConversion.BTCConverter getWCharConverter() {
/* 2223 */     if (this.wcharConverter == null) {
/* 2224 */       this.wcharConverter = this.parent.createWCharBTCConverter();
/*      */     }
/* 2226 */     return this.wcharConverter;
/*      */   }
/*      */   
/*      */   protected void dprintThrowable(Throwable paramThrowable) {
/* 2230 */     if (this.debug && paramThrowable != null)
/* 2231 */       paramThrowable.printStackTrace(); 
/*      */   }
/*      */   
/*      */   protected void dprint(String paramString) {
/* 2235 */     if (this.debug) {
/* 2236 */       ORBUtility.dprint(this, paramString);
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
/*      */   void alignOnBoundary(int paramInt) {
/* 2248 */     int i = computeAlignment(this.bbwi.position(), paramInt);
/*      */     
/* 2250 */     if (this.bbwi.position() + i <= this.bbwi.buflen)
/*      */     {
/* 2252 */       this.bbwi.position(this.bbwi.position() + i);
/*      */     }
/*      */   }
/*      */   
/*      */   public void resetCodeSetConverters() {
/* 2257 */     this.charConverter = null;
/* 2258 */     this.wcharConverter = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void start_value() {
/* 2263 */     int i = readValueTag();
/*      */     
/* 2265 */     if (i == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2273 */       this.specialNoOptionalDataState = true;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2278 */     if (i == -1)
/*      */     {
/* 2280 */       throw this.wrapper.customWrapperIndirection(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */ 
/*      */     
/* 2284 */     if (this.repIdUtil.isCodeBasePresent(i)) {
/* 2285 */       throw this.wrapper.customWrapperWithCodebase(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */ 
/*      */     
/* 2289 */     if (this.repIdUtil.getTypeInfo(i) != 2)
/*      */     {
/* 2291 */       throw this.wrapper.customWrapperNotSingleRepid(CompletionStatus.COMPLETED_MAYBE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2298 */     read_repositoryId();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2304 */     start_block();
/* 2305 */     this.end_flag--;
/* 2306 */     this.chunkedValueNestingLevel--;
/*      */   }
/*      */ 
/*      */   
/*      */   public void end_value() {
/* 2311 */     if (this.specialNoOptionalDataState) {
/* 2312 */       this.specialNoOptionalDataState = false;
/*      */       
/*      */       return;
/*      */     } 
/* 2316 */     handleEndOfValue();
/* 2317 */     readEndTag();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2325 */     start_block();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/* 2332 */     getBufferManager().close(this.bbwi);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2340 */     if (this.bbwi != null && getByteBuffer() != null) {
/*      */       
/* 2342 */       MessageMediator messageMediator = this.parent.getMessageMediator();
/* 2343 */       if (messageMediator != null) {
/*      */ 
/*      */         
/* 2346 */         CDROutputObject cDROutputObject = (CDROutputObject)messageMediator.getOutputObject();
/* 2347 */         if (cDROutputObject != null)
/*      */         {
/* 2349 */           if (cDROutputObject.isSharing(getByteBuffer())) {
/*      */ 
/*      */ 
/*      */             
/* 2353 */             cDROutputObject.setByteBuffer(null);
/* 2354 */             cDROutputObject.setByteBufferWithInfo((ByteBufferWithInfo)null);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2360 */       ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
/* 2361 */       if (this.debug) {
/*      */ 
/*      */         
/* 2364 */         int i = System.identityHashCode(this.bbwi.byteBuffer);
/* 2365 */         StringBuffer stringBuffer = new StringBuffer(80);
/* 2366 */         stringBuffer.append(".close - releasing ByteBuffer id (");
/* 2367 */         stringBuffer.append(i).append(") to ByteBufferPool.");
/* 2368 */         String str = stringBuffer.toString();
/* 2369 */         dprint(str);
/*      */       } 
/* 2371 */       byteBufferPool.releaseByteBuffer(this.bbwi.byteBuffer);
/* 2372 */       this.bbwi.byteBuffer = null;
/* 2373 */       this.bbwi = null;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDRInputStream_1_0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */