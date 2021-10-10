/*      */ package com.sun.corba.se.impl.corba;
/*      */ 
/*      */ import com.sun.corba.se.impl.encoding.CDRInputStream;
/*      */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*      */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*      */ import com.sun.corba.se.impl.io.ValueUtility;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;
/*      */ import com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*      */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.CompletionStatus;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.Principal;
/*      */ import org.omg.CORBA.TCKind;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*      */ import org.omg.CORBA.TypeCodePackage.Bounds;
/*      */ import org.omg.CORBA.portable.InputStream;
/*      */ import org.omg.CORBA.portable.OutputStream;
/*      */ import org.omg.CORBA.portable.Streamable;
/*      */ import org.omg.CORBA_2_3.portable.InputStream;
/*      */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*      */ public class AnyImpl
/*      */   extends Any
/*      */ {
/*      */   private TypeCodeImpl typeCode;
/*      */   protected ORB orb;
/*      */   private ORBUtilSystemException wrapper;
/*      */   private CDRInputStream stream;
/*      */   private long value;
/*      */   private Object object;
/*      */   
/*      */   private static final class AnyInputStream
/*      */     extends EncapsInputStream
/*      */   {
/*      */     public AnyInputStream(EncapsInputStream param1EncapsInputStream) {
/*   74 */       super(param1EncapsInputStream);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class AnyOutputStream
/*      */     extends EncapsOutputStream
/*      */   {
/*      */     public AnyOutputStream(ORB param1ORB) {
/*   82 */       super(param1ORB);
/*      */     }
/*      */ 
/*      */     
/*      */     public InputStream create_input_stream() {
/*   87 */       final InputStream is = super.create_input_stream();
/*      */       
/*   89 */       return (InputStream)AccessController.<AnyImpl.AnyInputStream>doPrivileged(new PrivilegedAction<AnyImpl.AnyInputStream>()
/*      */           {
/*      */             public AnyImpl.AnyInputStream run() {
/*   92 */               return new AnyImpl.AnyInputStream((EncapsInputStream)is);
/*      */             }
/*      */           });
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
/*      */   private boolean isInitialized = false;
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
/*      */   private static final int DEFAULT_BUFFER_SIZE = 32;
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
/*  130 */   static boolean[] isStreamed = new boolean[] { 
/*      */       false, false, false, false, false, false, false, false, false, false, 
/*      */       false, false, false, true, false, true, true, false, false, true, 
/*      */       true, true, true, false, false, false, false, false, false, false, 
/*      */       false, false, false };
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
/*      */   static AnyImpl convertToNative(ORB paramORB, Any paramAny) {
/*  167 */     if (paramAny instanceof AnyImpl) {
/*  168 */       return (AnyImpl)paramAny;
/*      */     }
/*  170 */     AnyImpl anyImpl = new AnyImpl(paramORB, paramAny);
/*  171 */     anyImpl.typeCode = TypeCodeImpl.convertToNative(paramORB, anyImpl.typeCode);
/*  172 */     return anyImpl;
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
/*      */   public AnyImpl(ORB paramORB) {
/*  186 */     this.orb = paramORB;
/*  187 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.presentation");
/*      */ 
/*      */     
/*  190 */     this.typeCode = paramORB.get_primitive_tc(0);
/*  191 */     this.stream = null;
/*  192 */     this.object = null;
/*  193 */     this.value = 0L;
/*      */     
/*  195 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnyImpl(ORB paramORB, Any paramAny) {
/*  202 */     this(paramORB);
/*      */     
/*  204 */     if (paramAny instanceof AnyImpl) {
/*  205 */       AnyImpl anyImpl = (AnyImpl)paramAny;
/*  206 */       this.typeCode = anyImpl.typeCode;
/*  207 */       this.value = anyImpl.value;
/*  208 */       this.object = anyImpl.object;
/*  209 */       this.isInitialized = anyImpl.isInitialized;
/*      */       
/*  211 */       if (anyImpl.stream != null) {
/*  212 */         this.stream = anyImpl.stream.dup();
/*      */       }
/*      */     } else {
/*  215 */       read_value(paramAny.create_input_stream(), paramAny.type());
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
/*      */   public TypeCode type() {
/*  228 */     return this.typeCode;
/*      */   }
/*      */   
/*      */   private TypeCode realType() {
/*  232 */     return realType(this.typeCode);
/*      */   }
/*      */   
/*      */   private TypeCode realType(TypeCode paramTypeCode) {
/*  236 */     TypeCode typeCode = paramTypeCode;
/*      */     
/*      */     try {
/*  239 */       while (typeCode.kind().value() == 21) {
/*  240 */         typeCode = typeCode.content_type();
/*      */       }
/*  242 */     } catch (BadKind badKind) {
/*  243 */       throw this.wrapper.badkindCannotOccur(badKind);
/*      */     } 
/*  245 */     return typeCode;
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
/*      */   public void type(TypeCode paramTypeCode) {
/*  257 */     this.typeCode = TypeCodeImpl.convertToNative(this.orb, paramTypeCode);
/*      */     
/*  259 */     this.stream = null;
/*  260 */     this.value = 0L;
/*  261 */     this.object = null;
/*      */     
/*  263 */     this.isInitialized = (paramTypeCode.kind().value() == 0);
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
/*      */   public boolean equal(Any paramAny) {
/*      */     InputStream inputStream1, inputStream2;
/*  276 */     if (paramAny == this) {
/*  277 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  281 */     if (!this.typeCode.equal(paramAny.type())) {
/*  282 */       return false;
/*      */     }
/*      */     
/*  285 */     TypeCode typeCode = realType();
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
/*  299 */     switch (typeCode.kind().value()) {
/*      */       
/*      */       case 0:
/*      */       case 1:
/*  303 */         return true;
/*      */       case 2:
/*  305 */         return (extract_short() == paramAny.extract_short());
/*      */       case 3:
/*  307 */         return (extract_long() == paramAny.extract_long());
/*      */       case 4:
/*  309 */         return (extract_ushort() == paramAny.extract_ushort());
/*      */       case 5:
/*  311 */         return (extract_ulong() == paramAny.extract_ulong());
/*      */       case 6:
/*  313 */         return (extract_float() == paramAny.extract_float());
/*      */       case 7:
/*  315 */         return (extract_double() == paramAny.extract_double());
/*      */       case 8:
/*  317 */         return (extract_boolean() == paramAny.extract_boolean());
/*      */       case 9:
/*  319 */         return (extract_char() == paramAny.extract_char());
/*      */       case 26:
/*  321 */         return (extract_wchar() == paramAny.extract_wchar());
/*      */       case 10:
/*  323 */         return (extract_octet() == paramAny.extract_octet());
/*      */       case 11:
/*  325 */         return extract_any().equal(paramAny.extract_any());
/*      */       case 12:
/*  327 */         return extract_TypeCode().equal(paramAny.extract_TypeCode());
/*      */       case 18:
/*  329 */         return extract_string().equals(paramAny.extract_string());
/*      */       case 27:
/*  331 */         return extract_wstring().equals(paramAny.extract_wstring());
/*      */       case 23:
/*  333 */         return (extract_longlong() == paramAny.extract_longlong());
/*      */       case 24:
/*  335 */         return (extract_ulonglong() == paramAny.extract_ulonglong());
/*      */       
/*      */       case 14:
/*  338 */         return extract_Object().equals(paramAny.extract_Object());
/*      */       case 13:
/*  340 */         return extract_Principal().equals(paramAny.extract_Principal());
/*      */       
/*      */       case 17:
/*  343 */         return (extract_long() == paramAny.extract_long());
/*      */       case 28:
/*  345 */         return (extract_fixed().compareTo(paramAny.extract_fixed()) == 0);
/*      */       case 15:
/*      */       case 16:
/*      */       case 19:
/*      */       case 20:
/*      */       case 22:
/*  351 */         inputStream1 = create_input_stream();
/*  352 */         inputStream2 = paramAny.create_input_stream();
/*  353 */         return equalMember(typeCode, inputStream1, inputStream2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 29:
/*      */       case 30:
/*  360 */         return extract_Value().equals(paramAny.extract_Value());
/*      */       
/*      */       case 21:
/*  363 */         throw this.wrapper.errorResolvingAlias();
/*      */ 
/*      */       
/*      */       case 25:
/*  367 */         throw this.wrapper.tkLongDoubleNotSupported();
/*      */     } 
/*      */     
/*  370 */     throw this.wrapper.typecodeNotSupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean equalMember(TypeCode paramTypeCode, InputStream paramInputStream1, InputStream paramInputStream2) {
/*  378 */     TypeCode typeCode = realType(paramTypeCode); try {
/*      */       int j; Any any1; int i; InputStream inputStream1; byte b2; Any any2; byte b1; InputStream inputStream2; TypeCodeImpl typeCodeImpl;
/*      */       int k;
/*  381 */       switch (typeCode.kind().value()) {
/*      */         
/*      */         case 0:
/*      */         case 1:
/*  385 */           return true;
/*      */         case 2:
/*  387 */           return (paramInputStream1.read_short() == paramInputStream2.read_short());
/*      */         case 3:
/*  389 */           return (paramInputStream1.read_long() == paramInputStream2.read_long());
/*      */         case 4:
/*  391 */           return (paramInputStream1.read_ushort() == paramInputStream2.read_ushort());
/*      */         case 5:
/*  393 */           return (paramInputStream1.read_ulong() == paramInputStream2.read_ulong());
/*      */         case 6:
/*  395 */           return (paramInputStream1.read_float() == paramInputStream2.read_float());
/*      */         case 7:
/*  397 */           return (paramInputStream1.read_double() == paramInputStream2.read_double());
/*      */         case 8:
/*  399 */           return (paramInputStream1.read_boolean() == paramInputStream2.read_boolean());
/*      */         case 9:
/*  401 */           return (paramInputStream1.read_char() == paramInputStream2.read_char());
/*      */         case 26:
/*  403 */           return (paramInputStream1.read_wchar() == paramInputStream2.read_wchar());
/*      */         case 10:
/*  405 */           return (paramInputStream1.read_octet() == paramInputStream2.read_octet());
/*      */         case 11:
/*  407 */           return paramInputStream1.read_any().equal(paramInputStream2.read_any());
/*      */         case 12:
/*  409 */           return paramInputStream1.read_TypeCode().equal(paramInputStream2.read_TypeCode());
/*      */         case 18:
/*  411 */           return paramInputStream1.read_string().equals(paramInputStream2.read_string());
/*      */         case 27:
/*  413 */           return paramInputStream1.read_wstring().equals(paramInputStream2.read_wstring());
/*      */         case 23:
/*  415 */           return (paramInputStream1.read_longlong() == paramInputStream2.read_longlong());
/*      */         case 24:
/*  417 */           return (paramInputStream1.read_ulonglong() == paramInputStream2.read_ulonglong());
/*      */         
/*      */         case 14:
/*  420 */           return paramInputStream1.read_Object().equals(paramInputStream2.read_Object());
/*      */         case 13:
/*  422 */           return paramInputStream1.read_Principal().equals(paramInputStream2.read_Principal());
/*      */         
/*      */         case 17:
/*  425 */           return (paramInputStream1.read_long() == paramInputStream2.read_long());
/*      */         case 28:
/*  427 */           return (paramInputStream1.read_fixed().compareTo(paramInputStream2.read_fixed()) == 0);
/*      */         case 15:
/*      */         case 22:
/*  430 */           j = typeCode.member_count();
/*  431 */           for (b2 = 0; b2 < j; b2++) {
/*  432 */             if (!equalMember(typeCode.member_type(b2), paramInputStream1, paramInputStream2)) {
/*  433 */               return false;
/*      */             }
/*      */           } 
/*  436 */           return true;
/*      */         
/*      */         case 16:
/*  439 */           any1 = this.orb.create_any();
/*  440 */           any2 = this.orb.create_any();
/*  441 */           any1.read_value(paramInputStream1, typeCode.discriminator_type());
/*  442 */           any2.read_value(paramInputStream2, typeCode.discriminator_type());
/*      */           
/*  444 */           if (!any1.equal(any2)) {
/*  445 */             return false;
/*      */           }
/*  447 */           typeCodeImpl = TypeCodeImpl.convertToNative(this.orb, typeCode);
/*  448 */           k = typeCodeImpl.currentUnionMemberIndex(any1);
/*  449 */           if (k == -1) {
/*  450 */             throw this.wrapper.unionDiscriminatorError();
/*      */           }
/*  452 */           if (!equalMember(typeCode.member_type(k), paramInputStream1, paramInputStream2)) {
/*  453 */             return false;
/*      */           }
/*  455 */           return true;
/*      */         
/*      */         case 19:
/*  458 */           i = paramInputStream1.read_long();
/*  459 */           paramInputStream2.read_long();
/*  460 */           for (b1 = 0; b1 < i; b1++) {
/*  461 */             if (!equalMember(typeCode.content_type(), paramInputStream1, paramInputStream2)) {
/*  462 */               return false;
/*      */             }
/*      */           } 
/*  465 */           return true;
/*      */         
/*      */         case 20:
/*  468 */           i = typeCode.member_count();
/*  469 */           for (b1 = 0; b1 < i; b1++) {
/*  470 */             if (!equalMember(typeCode.content_type(), paramInputStream1, paramInputStream2)) {
/*  471 */               return false;
/*      */             }
/*      */           } 
/*  474 */           return true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 29:
/*      */         case 30:
/*  482 */           inputStream1 = (InputStream)paramInputStream1;
/*      */           
/*  484 */           inputStream2 = (InputStream)paramInputStream2;
/*      */           
/*  486 */           return inputStream1.read_value().equals(inputStream2.read_value());
/*      */ 
/*      */         
/*      */         case 21:
/*  490 */           throw this.wrapper.errorResolvingAlias();
/*      */         
/*      */         case 25:
/*  493 */           throw this.wrapper.tkLongDoubleNotSupported();
/*      */       } 
/*      */       
/*  496 */       throw this.wrapper.typecodeNotSupported();
/*      */     }
/*  498 */     catch (BadKind badKind) {
/*  499 */       throw this.wrapper.badkindCannotOccur();
/*  500 */     } catch (Bounds bounds) {
/*  501 */       throw this.wrapper.boundsCannotOccur();
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
/*      */   public OutputStream create_output_stream() {
/*  516 */     final ORB finalorb = this.orb;
/*  517 */     return AccessController.<OutputStream>doPrivileged((PrivilegedAction)new PrivilegedAction<AnyOutputStream>()
/*      */         {
/*      */           public AnyImpl.AnyOutputStream run() {
/*  520 */             return new AnyImpl.AnyOutputStream(finalorb);
/*      */           }
/*      */         });
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
/*      */   public InputStream create_input_stream() {
/*  537 */     if (isStreamed[realType().kind().value()]) {
/*  538 */       return (InputStream)this.stream.dup();
/*      */     }
/*  540 */     OutputStream outputStream = this.orb.create_output_stream();
/*  541 */     TCUtility.marshalIn(outputStream, realType(), this.value, this.object);
/*      */     
/*  543 */     return outputStream.create_input_stream();
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
/*      */   
/*      */   public void read_value(InputStream paramInputStream, TypeCode paramTypeCode) {
/*  569 */     this.typeCode = TypeCodeImpl.convertToNative(this.orb, paramTypeCode);
/*  570 */     int i = realType().kind().value();
/*  571 */     if (i >= isStreamed.length) {
/*  572 */       throw this.wrapper.invalidIsstreamedTckind(CompletionStatus.COMPLETED_MAYBE, new Integer(i));
/*      */     }
/*      */ 
/*      */     
/*  576 */     if (isStreamed[i]) {
/*  577 */       if (paramInputStream instanceof AnyInputStream) {
/*      */         
/*  579 */         this.stream = (CDRInputStream)paramInputStream;
/*      */       } else {
/*      */         
/*  582 */         OutputStream outputStream = (OutputStream)this.orb.create_output_stream();
/*  583 */         this.typeCode.copy(paramInputStream, (OutputStream)outputStream);
/*  584 */         this.stream = (CDRInputStream)outputStream.create_input_stream();
/*      */       } 
/*      */     } else {
/*  587 */       Object[] arrayOfObject = new Object[1];
/*  588 */       arrayOfObject[0] = this.object;
/*  589 */       long[] arrayOfLong = new long[1];
/*  590 */       TCUtility.unmarshalIn(paramInputStream, realType(), arrayOfLong, arrayOfObject);
/*  591 */       this.value = arrayOfLong[0];
/*  592 */       this.object = arrayOfObject[0];
/*  593 */       this.stream = null;
/*      */     } 
/*  595 */     this.isInitialized = true;
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
/*      */   public void write_value(OutputStream paramOutputStream) {
/*  609 */     if (isStreamed[realType().kind().value()]) {
/*  610 */       this.typeCode.copy((InputStream)this.stream.dup(), paramOutputStream);
/*      */     } else {
/*      */       
/*  613 */       TCUtility.marshalIn(paramOutputStream, realType(), this.value, this.object);
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
/*      */   public void insert_Streamable(Streamable paramStreamable) {
/*  625 */     this.typeCode = TypeCodeImpl.convertToNative(this.orb, paramStreamable._type());
/*  626 */     this.object = paramStreamable;
/*  627 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Streamable extract_Streamable() {
/*  633 */     return (Streamable)this.object;
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
/*      */   public void insert_short(short paramShort) {
/*  645 */     this.typeCode = this.orb.get_primitive_tc(2);
/*  646 */     this.value = paramShort;
/*  647 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private String getTCKindName(int paramInt) {
/*  652 */     if (paramInt >= 0 && paramInt < TypeCodeImpl.kindNames.length) {
/*  653 */       return TypeCodeImpl.kindNames[paramInt];
/*      */     }
/*  655 */     return "UNKNOWN(" + paramInt + ")";
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkExtractBadOperation(int paramInt) {
/*  660 */     if (!this.isInitialized) {
/*  661 */       throw this.wrapper.extractNotInitialized();
/*      */     }
/*  663 */     int i = realType().kind().value();
/*  664 */     if (i != paramInt) {
/*  665 */       String str1 = getTCKindName(i);
/*  666 */       String str2 = getTCKindName(paramInt);
/*  667 */       throw this.wrapper.extractWrongType(str2, str1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkExtractBadOperationList(int[] paramArrayOfint) {
/*  673 */     if (!this.isInitialized) {
/*  674 */       throw this.wrapper.extractNotInitialized();
/*      */     }
/*  676 */     int i = realType().kind().value();
/*  677 */     for (byte b1 = 0; b1 < paramArrayOfint.length; b1++) {
/*  678 */       if (i == paramArrayOfint[b1])
/*      */         return; 
/*      */     } 
/*  681 */     ArrayList<String> arrayList = new ArrayList();
/*  682 */     for (byte b2 = 0; b2 < paramArrayOfint.length; b2++) {
/*  683 */       arrayList.add(getTCKindName(paramArrayOfint[b2]));
/*      */     }
/*  685 */     String str = getTCKindName(i);
/*  686 */     throw this.wrapper.extractWrongTypeList(arrayList, str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short extract_short() {
/*  695 */     checkExtractBadOperation(2);
/*  696 */     return (short)(int)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_long(int paramInt) {
/*  707 */     int i = realType().kind().value();
/*  708 */     if (i != 3 && i != 17) {
/*  709 */       this.typeCode = this.orb.get_primitive_tc(3);
/*      */     }
/*  711 */     this.value = paramInt;
/*  712 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int extract_long() {
/*  721 */     checkExtractBadOperationList(new int[] { 3, 17 });
/*  722 */     return (int)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_ushort(short paramShort) {
/*  731 */     this.typeCode = this.orb.get_primitive_tc(4);
/*  732 */     this.value = paramShort;
/*  733 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short extract_ushort() {
/*  742 */     checkExtractBadOperation(4);
/*  743 */     return (short)(int)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_ulong(int paramInt) {
/*  752 */     this.typeCode = this.orb.get_primitive_tc(5);
/*  753 */     this.value = paramInt;
/*  754 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int extract_ulong() {
/*  763 */     checkExtractBadOperation(5);
/*  764 */     return (int)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_float(float paramFloat) {
/*  773 */     this.typeCode = this.orb.get_primitive_tc(6);
/*  774 */     this.value = Float.floatToIntBits(paramFloat);
/*  775 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float extract_float() {
/*  784 */     checkExtractBadOperation(6);
/*  785 */     return Float.intBitsToFloat((int)this.value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_double(double paramDouble) {
/*  794 */     this.typeCode = this.orb.get_primitive_tc(7);
/*  795 */     this.value = Double.doubleToLongBits(paramDouble);
/*  796 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double extract_double() {
/*  805 */     checkExtractBadOperation(7);
/*  806 */     return Double.longBitsToDouble(this.value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_longlong(long paramLong) {
/*  815 */     this.typeCode = this.orb.get_primitive_tc(23);
/*  816 */     this.value = paramLong;
/*  817 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long extract_longlong() {
/*  826 */     checkExtractBadOperation(23);
/*  827 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_ulonglong(long paramLong) {
/*  836 */     this.typeCode = this.orb.get_primitive_tc(24);
/*  837 */     this.value = paramLong;
/*  838 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long extract_ulonglong() {
/*  847 */     checkExtractBadOperation(24);
/*  848 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_boolean(boolean paramBoolean) {
/*  857 */     this.typeCode = this.orb.get_primitive_tc(8);
/*  858 */     this.value = paramBoolean ? 1L : 0L;
/*  859 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean extract_boolean() {
/*  868 */     checkExtractBadOperation(8);
/*  869 */     return !(this.value == 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_char(char paramChar) {
/*  878 */     this.typeCode = this.orb.get_primitive_tc(9);
/*  879 */     this.value = paramChar;
/*  880 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char extract_char() {
/*  889 */     checkExtractBadOperation(9);
/*  890 */     return (char)(int)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_wchar(char paramChar) {
/*  899 */     this.typeCode = this.orb.get_primitive_tc(26);
/*  900 */     this.value = paramChar;
/*  901 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char extract_wchar() {
/*  910 */     checkExtractBadOperation(26);
/*  911 */     return (char)(int)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_octet(byte paramByte) {
/*  921 */     this.typeCode = this.orb.get_primitive_tc(10);
/*  922 */     this.value = paramByte;
/*  923 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte extract_octet() {
/*  932 */     checkExtractBadOperation(10);
/*  933 */     return (byte)(int)this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_string(String paramString) {
/*  943 */     if (this.typeCode.kind() == TCKind.tk_string) {
/*  944 */       int i = 0;
/*      */       try {
/*  946 */         i = this.typeCode.length();
/*  947 */       } catch (BadKind badKind) {
/*  948 */         throw this.wrapper.badkindCannotOccur();
/*      */       } 
/*      */ 
/*      */       
/*  952 */       if (i != 0 && paramString != null && paramString.length() > i) {
/*  953 */         throw this.wrapper.badStringBounds(new Integer(paramString.length()), new Integer(i));
/*      */       }
/*      */     } else {
/*      */       
/*  957 */       this.typeCode = this.orb.get_primitive_tc(18);
/*      */     } 
/*  959 */     this.object = paramString;
/*  960 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String extract_string() {
/*  969 */     checkExtractBadOperation(18);
/*  970 */     return (String)this.object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_wstring(String paramString) {
/*  980 */     if (this.typeCode.kind() == TCKind.tk_wstring) {
/*  981 */       int i = 0;
/*      */       try {
/*  983 */         i = this.typeCode.length();
/*  984 */       } catch (BadKind badKind) {
/*  985 */         throw this.wrapper.badkindCannotOccur();
/*      */       } 
/*      */ 
/*      */       
/*  989 */       if (i != 0 && paramString != null && paramString.length() > i) {
/*  990 */         throw this.wrapper.badStringBounds(new Integer(paramString.length()), new Integer(i));
/*      */       }
/*      */     } else {
/*      */       
/*  994 */       this.typeCode = this.orb.get_primitive_tc(27);
/*      */     } 
/*  996 */     this.object = paramString;
/*  997 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String extract_wstring() {
/* 1006 */     checkExtractBadOperation(27);
/* 1007 */     return (String)this.object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_any(Any paramAny) {
/* 1016 */     this.typeCode = this.orb.get_primitive_tc(11);
/* 1017 */     this.object = paramAny;
/* 1018 */     this.stream = null;
/* 1019 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Any extract_any() {
/* 1028 */     checkExtractBadOperation(11);
/* 1029 */     return (Any)this.object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_Object(Object paramObject) {
/* 1038 */     if (paramObject == null) {
/* 1039 */       this.typeCode = this.orb.get_primitive_tc(14);
/*      */     }
/* 1041 */     else if (StubAdapter.isStub(paramObject)) {
/* 1042 */       String[] arrayOfString = StubAdapter.getTypeIds(paramObject);
/* 1043 */       this.typeCode = new TypeCodeImpl(this.orb, 14, arrayOfString[0], "");
/*      */     } else {
/* 1045 */       throw this.wrapper.badInsertobjParam(CompletionStatus.COMPLETED_MAYBE, paramObject
/* 1046 */           .getClass().getName());
/*      */     } 
/*      */ 
/*      */     
/* 1050 */     this.object = paramObject;
/* 1051 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_Object(Object paramObject, TypeCode paramTypeCode) {
/*      */     try {
/* 1062 */       if (paramTypeCode.id().equals("IDL:omg.org/CORBA/Object:1.0") || paramObject._is_a(paramTypeCode.id())) {
/*      */         
/* 1064 */         this.typeCode = TypeCodeImpl.convertToNative(this.orb, paramTypeCode);
/* 1065 */         this.object = paramObject;
/*      */       } else {
/*      */         
/* 1068 */         throw this.wrapper.insertObjectIncompatible();
/*      */       } 
/* 1070 */     } catch (Exception exception) {
/* 1071 */       throw this.wrapper.insertObjectFailed(exception);
/*      */     } 
/* 1073 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object extract_Object() {
/* 1082 */     if (!this.isInitialized) {
/* 1083 */       throw this.wrapper.extractNotInitialized();
/*      */     }
/*      */     
/* 1086 */     Object object = null;
/*      */     try {
/* 1088 */       object = (Object)this.object;
/* 1089 */       if (this.typeCode.id().equals("IDL:omg.org/CORBA/Object:1.0") || object._is_a(this.typeCode.id())) {
/* 1090 */         return object;
/*      */       }
/* 1092 */       throw this.wrapper.extractObjectIncompatible();
/*      */     }
/* 1094 */     catch (Exception exception) {
/* 1095 */       throw this.wrapper.extractObjectFailed(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_TypeCode(TypeCode paramTypeCode) {
/* 1105 */     this.typeCode = this.orb.get_primitive_tc(12);
/* 1106 */     this.object = paramTypeCode;
/* 1107 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCode extract_TypeCode() {
/* 1116 */     checkExtractBadOperation(12);
/* 1117 */     return (TypeCode)this.object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void insert_Principal(Principal paramPrincipal) {
/* 1126 */     this.typeCode = this.orb.get_primitive_tc(13);
/* 1127 */     this.object = paramPrincipal;
/* 1128 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Principal extract_Principal() {
/* 1137 */     checkExtractBadOperation(13);
/* 1138 */     return (Principal)this.object;
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
/*      */   public Serializable extract_Value() {
/* 1150 */     checkExtractBadOperationList(new int[] { 29, 30, 32 });
/*      */     
/* 1152 */     return (Serializable)this.object;
/*      */   }
/*      */ 
/*      */   
/*      */   public void insert_Value(Serializable paramSerializable) {
/*      */     TypeCode typeCode;
/* 1158 */     this.object = paramSerializable;
/*      */ 
/*      */ 
/*      */     
/* 1162 */     if (paramSerializable == null) {
/* 1163 */       typeCode = this.orb.get_primitive_tc(TCKind.tk_value);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1174 */       typeCode = createTypeCodeForClass(paramSerializable.getClass(), (ORB)ORB.init());
/*      */     } 
/*      */     
/* 1177 */     this.typeCode = TypeCodeImpl.convertToNative(this.orb, typeCode);
/* 1178 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert_Value(Serializable paramSerializable, TypeCode paramTypeCode) {
/* 1184 */     this.object = paramSerializable;
/* 1185 */     this.typeCode = TypeCodeImpl.convertToNative(this.orb, paramTypeCode);
/* 1186 */     this.isInitialized = true;
/*      */   }
/*      */   
/*      */   public void insert_fixed(BigDecimal paramBigDecimal) {
/* 1190 */     this.typeCode = TypeCodeImpl.convertToNative(this.orb, this.orb
/* 1191 */         .create_fixed_tc(TypeCodeImpl.digits(paramBigDecimal), TypeCodeImpl.scale(paramBigDecimal)));
/* 1192 */     this.object = paramBigDecimal;
/* 1193 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void insert_fixed(BigDecimal paramBigDecimal, TypeCode paramTypeCode) {
/*      */     try {
/* 1199 */       if (TypeCodeImpl.digits(paramBigDecimal) > paramTypeCode.fixed_digits() || 
/* 1200 */         TypeCodeImpl.scale(paramBigDecimal) > paramTypeCode.fixed_scale())
/*      */       {
/* 1202 */         throw this.wrapper.fixedNotMatch();
/*      */       }
/* 1204 */     } catch (BadKind badKind) {
/*      */       
/* 1206 */       throw this.wrapper.fixedBadTypecode(badKind);
/*      */     } 
/* 1208 */     this.typeCode = TypeCodeImpl.convertToNative(this.orb, paramTypeCode);
/* 1209 */     this.object = paramBigDecimal;
/* 1210 */     this.isInitialized = true;
/*      */   }
/*      */   
/*      */   public BigDecimal extract_fixed() {
/* 1214 */     checkExtractBadOperation(28);
/* 1215 */     return (BigDecimal)this.object;
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
/*      */   public TypeCode createTypeCodeForClass(Class<String> paramClass, ORB paramORB) {
/* 1227 */     TypeCodeImpl typeCodeImpl = paramORB.getTypeCodeForClass(paramClass);
/* 1228 */     if (typeCodeImpl != null) {
/* 1229 */       return typeCodeImpl;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1236 */     RepositoryIdStrings repositoryIdStrings = RepositoryIdFactory.getRepIdStringsFactory();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1241 */     if (paramClass.isArray()) {
/*      */       TypeCode typeCode1;
/* 1243 */       Class<?> clazz = paramClass.getComponentType();
/*      */       
/* 1245 */       if (clazz.isPrimitive()) {
/* 1246 */         typeCode1 = getPrimitiveTypeCodeForClass(clazz, paramORB);
/*      */       } else {
/*      */         
/* 1249 */         typeCode1 = createTypeCodeForClass(clazz, paramORB);
/*      */       } 
/*      */       
/* 1252 */       TypeCode typeCode2 = paramORB.create_sequence_tc(0, typeCode1);
/*      */       
/* 1254 */       String str = repositoryIdStrings.createForJavaType(paramClass);
/*      */       
/* 1256 */       return paramORB.create_value_box_tc(str, "Sequence", typeCode2);
/* 1257 */     }  if (paramClass == String.class) {
/*      */       
/* 1259 */       TypeCode typeCode = paramORB.create_string_tc(0);
/*      */       
/* 1261 */       String str = repositoryIdStrings.createForJavaType(paramClass);
/*      */       
/* 1263 */       return paramORB.create_value_box_tc(str, "StringValue", typeCode);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1268 */     typeCodeImpl = (TypeCodeImpl)ValueUtility.createTypeCodeForClass((ORB)paramORB, paramClass, 
/* 1269 */         ORBUtility.createValueHandler());
/*      */     
/* 1271 */     typeCodeImpl.setCaching(true);
/*      */     
/* 1273 */     paramORB.setTypeCodeForClass(paramClass, typeCodeImpl);
/* 1274 */     return typeCodeImpl;
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
/*      */   private TypeCode getPrimitiveTypeCodeForClass(Class<int> paramClass, ORB paramORB) {
/* 1289 */     if (paramClass == int.class)
/* 1290 */       return paramORB.get_primitive_tc(TCKind.tk_long); 
/* 1291 */     if (paramClass == byte.class)
/* 1292 */       return paramORB.get_primitive_tc(TCKind.tk_octet); 
/* 1293 */     if (paramClass == long.class)
/* 1294 */       return paramORB.get_primitive_tc(TCKind.tk_longlong); 
/* 1295 */     if (paramClass == float.class)
/* 1296 */       return paramORB.get_primitive_tc(TCKind.tk_float); 
/* 1297 */     if (paramClass == double.class)
/* 1298 */       return paramORB.get_primitive_tc(TCKind.tk_double); 
/* 1299 */     if (paramClass == short.class)
/* 1300 */       return paramORB.get_primitive_tc(TCKind.tk_short); 
/* 1301 */     if (paramClass == char.class) {
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
/* 1313 */       if (ORBVersionFactory.getFOREIGN().compareTo(paramORB.getORBVersion()) == 0 || 
/* 1314 */         ORBVersionFactory.getNEWER().compareTo(paramORB.getORBVersion()) <= 0) {
/* 1315 */         return paramORB.get_primitive_tc(TCKind.tk_wchar);
/*      */       }
/* 1317 */       return paramORB.get_primitive_tc(TCKind.tk_char);
/* 1318 */     }  if (paramClass == boolean.class) {
/* 1319 */       return paramORB.get_primitive_tc(TCKind.tk_boolean);
/*      */     }
/*      */     
/* 1322 */     return paramORB.get_primitive_tc(TCKind.tk_any);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Any extractAny(TypeCode paramTypeCode, ORB paramORB) {
/* 1330 */     Any any = paramORB.create_any();
/* 1331 */     OutputStream outputStream = any.create_output_stream();
/* 1332 */     TypeCodeImpl.convertToNative(paramORB, paramTypeCode).copy((InputStream)this.stream, outputStream);
/* 1333 */     any.read_value(outputStream.create_input_stream(), paramTypeCode);
/* 1334 */     return any;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Any extractAnyFromStream(TypeCode paramTypeCode, InputStream paramInputStream, ORB paramORB) {
/* 1340 */     Any any = paramORB.create_any();
/* 1341 */     OutputStream outputStream = any.create_output_stream();
/* 1342 */     TypeCodeImpl.convertToNative(paramORB, paramTypeCode).copy(paramInputStream, outputStream);
/* 1343 */     any.read_value(outputStream.create_input_stream(), paramTypeCode);
/* 1344 */     return any;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInitialized() {
/* 1349 */     return this.isInitialized;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/AnyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */