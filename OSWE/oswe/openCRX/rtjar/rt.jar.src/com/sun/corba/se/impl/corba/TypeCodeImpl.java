/*      */ package com.sun.corba.se.impl.corba;
/*      */ 
/*      */ import com.sun.corba.se.impl.encoding.CDRInputStream;
/*      */ import com.sun.corba.se.impl.encoding.CDROutputStream;
/*      */ import com.sun.corba.se.impl.encoding.TypeCodeInputStream;
/*      */ import com.sun.corba.se.impl.encoding.TypeCodeOutputStream;
/*      */ import com.sun.corba.se.impl.encoding.TypeCodeReader;
/*      */ import com.sun.corba.se.impl.encoding.WrapperInputStream;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.math.BigDecimal;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.StructMember;
/*      */ import org.omg.CORBA.TCKind;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*      */ import org.omg.CORBA.TypeCodePackage.Bounds;
/*      */ import org.omg.CORBA.UnionMember;
/*      */ import org.omg.CORBA.ValueMember;
/*      */ import org.omg.CORBA.portable.InputStream;
/*      */ import org.omg.CORBA.portable.OutputStream;
/*      */ import org.omg.CORBA_2_3.portable.InputStream;
/*      */ import org.omg.CORBA_2_3.portable.OutputStream;
/*      */ import sun.corba.OutputStreamFactory;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class TypeCodeImpl
/*      */   extends TypeCode
/*      */ {
/*      */   protected static final int tk_indirect = -1;
/*      */   private static final int EMPTY = 0;
/*      */   private static final int SIMPLE = 1;
/*      */   private static final int COMPLEX = 2;
/*   91 */   private static final int[] typeTable = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 1, 2, 2, 2, 2, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2 };
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
/*  129 */   static final String[] kindNames = new String[] { "null", "void", "short", "long", "ushort", "ulong", "float", "double", "boolean", "char", "octet", "any", "typecode", "principal", "objref", "struct", "union", "enum", "string", "sequence", "array", "alias", "exception", "longlong", "ulonglong", "longdouble", "wchar", "wstring", "fixed", "value", "valueBox", "native", "abstractInterface" };
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
/*  165 */   private int _kind = 0;
/*      */ 
/*      */   
/*  168 */   private String _id = "";
/*  169 */   private String _name = "";
/*  170 */   private int _memberCount = 0;
/*  171 */   private String[] _memberNames = null;
/*  172 */   private TypeCodeImpl[] _memberTypes = null;
/*  173 */   private AnyImpl[] _unionLabels = null;
/*  174 */   private TypeCodeImpl _discriminator = null;
/*  175 */   private int _defaultIndex = -1;
/*  176 */   private int _length = 0;
/*  177 */   private TypeCodeImpl _contentType = null;
/*      */   
/*  179 */   private short _digits = 0;
/*  180 */   private short _scale = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  185 */   private short _type_modifier = -1;
/*      */   
/*  187 */   private TypeCodeImpl _concrete_base = null;
/*  188 */   private short[] _memberAccess = null;
/*      */   
/*  190 */   private TypeCodeImpl _parent = null;
/*  191 */   private int _parentOffset = 0;
/*      */   
/*  193 */   private TypeCodeImpl _indirectType = null;
/*      */ 
/*      */   
/*  196 */   private byte[] outBuffer = null;
/*      */ 
/*      */   
/*      */   private boolean cachingEnabled = false;
/*      */ 
/*      */   
/*      */   private ORB _orb;
/*      */ 
/*      */   
/*      */   private ORBUtilSystemException wrapper;
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB) {
/*  210 */     this._orb = paramORB;
/*  211 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.presentation");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, TypeCode paramTypeCode) {
/*  219 */     this(paramORB);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  225 */     if (paramTypeCode instanceof TypeCodeImpl) {
/*  226 */       TypeCodeImpl typeCodeImpl = (TypeCodeImpl)paramTypeCode;
/*  227 */       if (typeCodeImpl._kind == -1)
/*  228 */         throw this.wrapper.badRemoteTypecode(); 
/*  229 */       if (typeCodeImpl._kind == 19 && typeCodeImpl._contentType == null) {
/*  230 */         throw this.wrapper.badRemoteTypecode();
/*      */       }
/*      */     } 
/*      */     
/*  234 */     this._kind = paramTypeCode.kind().value(); 
/*      */     try { TypeCode typeCode;
/*      */       byte b1;
/*      */       byte b2;
/*  238 */       switch (this._kind) {
/*      */         case 29:
/*  240 */           this._type_modifier = paramTypeCode.type_modifier();
/*      */           
/*  242 */           typeCode = paramTypeCode.concrete_base_type();
/*  243 */           if (typeCode != null) {
/*  244 */             this._concrete_base = convertToNative(this._orb, typeCode);
/*      */           } else {
/*  246 */             this._concrete_base = null;
/*      */           } 
/*      */ 
/*      */           
/*  250 */           this._memberAccess = new short[paramTypeCode.member_count()];
/*  251 */           for (b2 = 0; b2 < paramTypeCode.member_count(); b2++) {
/*  252 */             this._memberAccess[b2] = paramTypeCode.member_visibility(b2);
/*      */           }
/*      */         
/*      */         case 15:
/*      */         case 16:
/*      */         case 22:
/*  258 */           this._memberTypes = new TypeCodeImpl[paramTypeCode.member_count()];
/*  259 */           for (b2 = 0; b2 < paramTypeCode.member_count(); b2++) {
/*  260 */             this._memberTypes[b2] = convertToNative(this._orb, paramTypeCode.member_type(b2));
/*  261 */             this._memberTypes[b2].setParent(this);
/*      */           } 
/*      */         
/*      */         case 17:
/*  265 */           this._memberNames = new String[paramTypeCode.member_count()];
/*  266 */           for (b2 = 0; b2 < paramTypeCode.member_count(); b2++) {
/*  267 */             this._memberNames[b2] = paramTypeCode.member_name(b2);
/*      */           }
/*      */           
/*  270 */           this._memberCount = paramTypeCode.member_count();
/*      */         case 14:
/*      */         case 21:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*  276 */           setId(paramTypeCode.id());
/*  277 */           this._name = paramTypeCode.name();
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/*  282 */       switch (this._kind) {
/*      */         case 16:
/*  284 */           this._discriminator = convertToNative(this._orb, paramTypeCode.discriminator_type());
/*  285 */           this._defaultIndex = paramTypeCode.default_index();
/*  286 */           this._unionLabels = new AnyImpl[this._memberCount];
/*  287 */           for (b1 = 0; b1 < this._memberCount; b1++) {
/*  288 */             this._unionLabels[b1] = new AnyImpl(this._orb, paramTypeCode.member_label(b1));
/*      */           }
/*      */           break;
/*      */       } 
/*      */       
/*  293 */       switch (this._kind) {
/*      */         case 18:
/*      */         case 19:
/*      */         case 20:
/*      */         case 27:
/*  298 */           this._length = paramTypeCode.length();
/*      */           break;
/*      */       } 
/*      */       
/*  302 */       switch (this._kind) {
/*      */         case 19:
/*      */         case 20:
/*      */         case 21:
/*      */         case 30:
/*  307 */           this._contentType = convertToNative(this._orb, paramTypeCode.content_type()); break;
/*      */       }  }
/*  309 */     catch (Bounds bounds) {  } catch (BadKind badKind) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt) {
/*  317 */     this(paramORB);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  322 */     this._kind = paramInt;
/*      */ 
/*      */     
/*  325 */     switch (this._kind) {
/*      */ 
/*      */       
/*      */       case 14:
/*  329 */         setId("IDL:omg.org/CORBA/Object:1.0");
/*  330 */         this._name = "Object";
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 18:
/*      */       case 27:
/*  337 */         this._length = 0;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 29:
/*  343 */         this._concrete_base = null;
/*      */         break;
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
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt, String paramString1, String paramString2, StructMember[] paramArrayOfStructMember) {
/*  356 */     this(paramORB);
/*      */     
/*  358 */     if (paramInt == 15 || paramInt == 22) {
/*  359 */       this._kind = paramInt;
/*  360 */       setId(paramString1);
/*  361 */       this._name = paramString2;
/*  362 */       this._memberCount = paramArrayOfStructMember.length;
/*      */       
/*  364 */       this._memberNames = new String[this._memberCount];
/*  365 */       this._memberTypes = new TypeCodeImpl[this._memberCount];
/*      */       
/*  367 */       for (byte b = 0; b < this._memberCount; b++) {
/*  368 */         this._memberNames[b] = (paramArrayOfStructMember[b]).name;
/*  369 */         this._memberTypes[b] = convertToNative(this._orb, (paramArrayOfStructMember[b]).type);
/*  370 */         this._memberTypes[b].setParent(this);
/*      */       } 
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
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt, String paramString1, String paramString2, TypeCode paramTypeCode, UnionMember[] paramArrayOfUnionMember) {
/*  383 */     this(paramORB);
/*      */     
/*  385 */     if (paramInt == 16) {
/*  386 */       this._kind = paramInt;
/*  387 */       setId(paramString1);
/*  388 */       this._name = paramString2;
/*  389 */       this._memberCount = paramArrayOfUnionMember.length;
/*  390 */       this._discriminator = convertToNative(this._orb, paramTypeCode);
/*      */       
/*  392 */       this._memberNames = new String[this._memberCount];
/*  393 */       this._memberTypes = new TypeCodeImpl[this._memberCount];
/*  394 */       this._unionLabels = new AnyImpl[this._memberCount];
/*      */       
/*  396 */       for (byte b = 0; b < this._memberCount; b++) {
/*  397 */         this._memberNames[b] = (paramArrayOfUnionMember[b]).name;
/*  398 */         this._memberTypes[b] = convertToNative(this._orb, (paramArrayOfUnionMember[b]).type);
/*  399 */         this._memberTypes[b].setParent(this);
/*  400 */         this._unionLabels[b] = new AnyImpl(this._orb, (paramArrayOfUnionMember[b]).label);
/*      */         
/*  402 */         if (this._unionLabels[b].type().kind() == TCKind.tk_octet && 
/*  403 */           this._unionLabels[b].extract_octet() == 0) {
/*  404 */           this._defaultIndex = b;
/*      */         }
/*      */       } 
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
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt, String paramString1, String paramString2, short paramShort, TypeCode paramTypeCode, ValueMember[] paramArrayOfValueMember) {
/*  420 */     this(paramORB);
/*      */     
/*  422 */     if (paramInt == 29) {
/*  423 */       this._kind = paramInt;
/*  424 */       setId(paramString1);
/*  425 */       this._name = paramString2;
/*  426 */       this._type_modifier = paramShort;
/*  427 */       if (paramTypeCode != null) {
/*  428 */         this._concrete_base = convertToNative(this._orb, paramTypeCode);
/*      */       }
/*  430 */       this._memberCount = paramArrayOfValueMember.length;
/*      */       
/*  432 */       this._memberNames = new String[this._memberCount];
/*  433 */       this._memberTypes = new TypeCodeImpl[this._memberCount];
/*  434 */       this._memberAccess = new short[this._memberCount];
/*      */       
/*  436 */       for (byte b = 0; b < this._memberCount; b++) {
/*  437 */         this._memberNames[b] = (paramArrayOfValueMember[b]).name;
/*  438 */         this._memberTypes[b] = convertToNative(this._orb, (paramArrayOfValueMember[b]).type);
/*  439 */         this._memberTypes[b].setParent(this);
/*  440 */         this._memberAccess[b] = (paramArrayOfValueMember[b]).access;
/*      */       } 
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
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt, String paramString1, String paramString2, String[] paramArrayOfString) {
/*  453 */     this(paramORB);
/*      */     
/*  455 */     if (paramInt == 17) {
/*      */       
/*  457 */       this._kind = paramInt;
/*  458 */       setId(paramString1);
/*  459 */       this._name = paramString2;
/*  460 */       this._memberCount = paramArrayOfString.length;
/*      */       
/*  462 */       this._memberNames = new String[this._memberCount];
/*      */       
/*  464 */       for (byte b = 0; b < this._memberCount; b++) {
/*  465 */         this._memberNames[b] = paramArrayOfString[b];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt, String paramString1, String paramString2, TypeCode paramTypeCode) {
/*  476 */     this(paramORB);
/*      */     
/*  478 */     if (paramInt == 21 || paramInt == 30) {
/*      */       
/*  480 */       this._kind = paramInt;
/*  481 */       setId(paramString1);
/*  482 */       this._name = paramString2;
/*  483 */       this._contentType = convertToNative(this._orb, paramTypeCode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt, String paramString1, String paramString2) {
/*  494 */     this(paramORB);
/*      */     
/*  496 */     if (paramInt == 14 || paramInt == 31 || paramInt == 32) {
/*      */ 
/*      */ 
/*      */       
/*  500 */       this._kind = paramInt;
/*  501 */       setId(paramString1);
/*  502 */       this._name = paramString2;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt1, int paramInt2) {
/*  512 */     this(paramORB);
/*      */     
/*  514 */     if (paramInt2 < 0) {
/*  515 */       throw this.wrapper.negativeBounds();
/*      */     }
/*  517 */     if (paramInt1 == 18 || paramInt1 == 27) {
/*  518 */       this._kind = paramInt1;
/*  519 */       this._length = paramInt2;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt1, int paramInt2, TypeCode paramTypeCode) {
/*  529 */     this(paramORB);
/*      */     
/*  531 */     if (paramInt1 == 19 || paramInt1 == 20) {
/*  532 */       this._kind = paramInt1;
/*  533 */       this._length = paramInt2;
/*  534 */       this._contentType = convertToNative(this._orb, paramTypeCode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt1, int paramInt2, int paramInt3) {
/*  544 */     this(paramORB);
/*      */     
/*  546 */     if (paramInt1 == 19) {
/*  547 */       this._kind = paramInt1;
/*  548 */       this._length = paramInt2;
/*  549 */       this._parentOffset = paramInt3;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, String paramString) {
/*  557 */     this(paramORB);
/*      */     
/*  559 */     this._kind = -1;
/*      */     
/*  561 */     this._id = paramString;
/*      */ 
/*      */     
/*  564 */     tryIndirectType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCodeImpl(ORB paramORB, int paramInt, short paramShort1, short paramShort2) {
/*  573 */     this(paramORB);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  578 */     if (paramInt == 28) {
/*  579 */       this._kind = paramInt;
/*  580 */       this._digits = paramShort1;
/*  581 */       this._scale = paramShort2;
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
/*      */   protected static TypeCodeImpl convertToNative(ORB paramORB, TypeCode paramTypeCode) {
/*  596 */     if (paramTypeCode instanceof TypeCodeImpl) {
/*  597 */       return (TypeCodeImpl)paramTypeCode;
/*      */     }
/*  599 */     return new TypeCodeImpl(paramORB, paramTypeCode);
/*      */   }
/*      */ 
/*      */   
/*      */   public static CDROutputStream newOutputStream(ORB paramORB) {
/*  604 */     return (CDROutputStream)OutputStreamFactory.newTypeCodeOutputStream(paramORB);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TypeCodeImpl indirectType() {
/*  613 */     this._indirectType = tryIndirectType();
/*  614 */     if (this._indirectType == null)
/*      */     {
/*  616 */       throw this.wrapper.unresolvedRecursiveTypecode();
/*      */     }
/*  618 */     return this._indirectType;
/*      */   }
/*      */ 
/*      */   
/*      */   private TypeCodeImpl tryIndirectType() {
/*  623 */     if (this._indirectType != null) {
/*  624 */       return this._indirectType;
/*      */     }
/*  626 */     setIndirectType(this._orb.getTypeCode(this._id));
/*      */     
/*  628 */     return this._indirectType;
/*      */   }
/*      */   
/*      */   private void setIndirectType(TypeCodeImpl paramTypeCodeImpl) {
/*  632 */     this._indirectType = paramTypeCodeImpl;
/*  633 */     if (this._indirectType != null) {
/*      */       try {
/*  635 */         this._id = this._indirectType.id();
/*  636 */       } catch (BadKind badKind) {
/*      */         
/*  638 */         throw this.wrapper.badkindCannotOccur();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void setId(String paramString) {
/*  644 */     this._id = paramString;
/*  645 */     if (this._orb instanceof TypeCodeFactory) {
/*  646 */       this._orb.setTypeCode(this._id, this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setParent(TypeCodeImpl paramTypeCodeImpl) {
/*  653 */     this._parent = paramTypeCodeImpl;
/*      */   }
/*      */   
/*      */   private TypeCodeImpl getParentAtLevel(int paramInt) {
/*  657 */     if (paramInt == 0) {
/*  658 */       return this;
/*      */     }
/*  660 */     if (this._parent == null) {
/*  661 */       throw this.wrapper.unresolvedRecursiveTypecode();
/*      */     }
/*  663 */     return this._parent.getParentAtLevel(paramInt - 1);
/*      */   }
/*      */   
/*      */   private TypeCodeImpl lazy_content_type() {
/*  667 */     if (this._contentType == null && 
/*  668 */       this._kind == 19 && this._parentOffset > 0 && this._parent != null) {
/*      */ 
/*      */       
/*  671 */       TypeCodeImpl typeCodeImpl = getParentAtLevel(this._parentOffset);
/*  672 */       if (typeCodeImpl != null && typeCodeImpl._id != null)
/*      */       {
/*      */ 
/*      */         
/*  676 */         this._contentType = new TypeCodeImpl(this._orb, typeCodeImpl._id);
/*      */       }
/*      */     } 
/*      */     
/*  680 */     return this._contentType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private TypeCode realType(TypeCode paramTypeCode) {
/*  686 */     TypeCode typeCode = paramTypeCode;
/*      */     
/*      */     try {
/*  689 */       while (typeCode.kind().value() == 21) {
/*  690 */         typeCode = typeCode.content_type();
/*      */       }
/*  692 */     } catch (BadKind badKind) {
/*      */       
/*  694 */       throw this.wrapper.badkindCannotOccur();
/*      */     } 
/*  696 */     return typeCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean equal(TypeCode paramTypeCode) {
/*  706 */     if (paramTypeCode == this)
/*  707 */       return true; 
/*      */     
/*      */     try { byte b;
/*      */       TypeCode typeCode;
/*  711 */       if (this._kind == -1) {
/*      */         
/*  713 */         if (this._id != null && paramTypeCode.id() != null)
/*  714 */           return this._id.equals(paramTypeCode.id()); 
/*  715 */         return (this._id == null && paramTypeCode.id() == null);
/*      */       } 
/*      */ 
/*      */       
/*  719 */       if (this._kind != paramTypeCode.kind().value()) {
/*  720 */         return false;
/*      */       }
/*      */       
/*  723 */       switch (typeTable[this._kind]) {
/*      */         
/*      */         case 0:
/*  726 */           return true;
/*      */         
/*      */         case 1:
/*  729 */           switch (this._kind) {
/*      */             
/*      */             case 18:
/*      */             case 27:
/*  733 */               return (this._length == paramTypeCode.length());
/*      */             
/*      */             case 28:
/*  736 */               return (this._digits == paramTypeCode.fixed_digits() && this._scale == paramTypeCode.fixed_scale());
/*      */           } 
/*  738 */           return false;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/*  743 */           switch (this._kind) {
/*      */ 
/*      */ 
/*      */             
/*      */             case 14:
/*  748 */               if (this._id.compareTo(paramTypeCode.id()) == 0) {
/*  749 */                 return true;
/*      */               }
/*      */               
/*  752 */               if (this._id.compareTo(this._orb
/*  753 */                   .get_primitive_tc(this._kind).id()) == 0)
/*      */               {
/*  755 */                 return true;
/*      */               }
/*      */               
/*  758 */               if (paramTypeCode.id().compareTo(this._orb
/*  759 */                   .get_primitive_tc(this._kind).id()) == 0)
/*      */               {
/*  761 */                 return true;
/*      */               }
/*      */               
/*  764 */               return false;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 31:
/*      */             case 32:
/*  771 */               if (this._id.compareTo(paramTypeCode.id()) != 0) {
/*  772 */                 return false;
/*      */               }
/*      */ 
/*      */               
/*  776 */               return true;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 15:
/*      */             case 22:
/*  783 */               if (this._memberCount != paramTypeCode.member_count()) {
/*  784 */                 return false;
/*      */               }
/*  786 */               if (this._id.compareTo(paramTypeCode.id()) != 0) {
/*  787 */                 return false;
/*      */               }
/*  789 */               for (b = 0; b < this._memberCount; b++) {
/*  790 */                 if (!this._memberTypes[b].equal(paramTypeCode.member_type(b)))
/*  791 */                   return false; 
/*      */               } 
/*  793 */               return true;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 16:
/*  799 */               if (this._memberCount != paramTypeCode.member_count()) {
/*  800 */                 return false;
/*      */               }
/*  802 */               if (this._id.compareTo(paramTypeCode.id()) != 0) {
/*  803 */                 return false;
/*      */               }
/*  805 */               if (this._defaultIndex != paramTypeCode.default_index()) {
/*  806 */                 return false;
/*      */               }
/*  808 */               if (!this._discriminator.equal(paramTypeCode.discriminator_type())) {
/*  809 */                 return false;
/*      */               }
/*  811 */               for (b = 0; b < this._memberCount; b++) {
/*  812 */                 if (!this._unionLabels[b].equal(paramTypeCode.member_label(b)))
/*  813 */                   return false; 
/*      */               } 
/*  815 */               for (b = 0; b < this._memberCount; b++) {
/*  816 */                 if (!this._memberTypes[b].equal(paramTypeCode.member_type(b)))
/*  817 */                   return false; 
/*      */               } 
/*  819 */               return true;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 17:
/*  825 */               if (this._id.compareTo(paramTypeCode.id()) != 0) {
/*  826 */                 return false;
/*      */               }
/*  828 */               if (this._memberCount != paramTypeCode.member_count()) {
/*  829 */                 return false;
/*      */               }
/*  831 */               return true;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 19:
/*      */             case 20:
/*  838 */               if (this._length != paramTypeCode.length()) {
/*  839 */                 return false;
/*      */               }
/*      */               
/*  842 */               if (!lazy_content_type().equal(paramTypeCode.content_type())) {
/*  843 */                 return false;
/*      */               }
/*      */               
/*  846 */               return true;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 29:
/*  852 */               if (this._memberCount != paramTypeCode.member_count()) {
/*  853 */                 return false;
/*      */               }
/*  855 */               if (this._id.compareTo(paramTypeCode.id()) != 0) {
/*  856 */                 return false;
/*      */               }
/*  858 */               for (b = 0; b < this._memberCount; b++) {
/*  859 */                 if (this._memberAccess[b] != paramTypeCode.member_visibility(b) || 
/*  860 */                   !this._memberTypes[b].equal(paramTypeCode.member_type(b)))
/*  861 */                   return false; 
/*  862 */               }  if (this._type_modifier == paramTypeCode.type_modifier()) {
/*  863 */                 return false;
/*      */               }
/*  865 */               typeCode = paramTypeCode.concrete_base_type();
/*  866 */               if ((this._concrete_base == null && typeCode != null) || (this._concrete_base != null && typeCode == null) || 
/*      */                 
/*  868 */                 !this._concrete_base.equal(typeCode))
/*      */               {
/*  870 */                 return false;
/*      */               }
/*      */               
/*  873 */               return true;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 21:
/*      */             case 30:
/*  880 */               if (this._id.compareTo(paramTypeCode.id()) != 0) {
/*  881 */                 return false;
/*      */               }
/*      */               
/*  884 */               return this._contentType.equal(paramTypeCode.content_type());
/*      */           } 
/*      */           break;
/*      */       }  }
/*  888 */     catch (Bounds bounds) {  } catch (BadKind badKind) {}
/*      */ 
/*      */     
/*  891 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equivalent(TypeCode paramTypeCode) {
/*  899 */     if (paramTypeCode == this) {
/*  900 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  907 */     TypeCodeImpl typeCodeImpl = (this._kind == -1) ? indirectType() : this;
/*  908 */     TypeCode typeCode1 = realType(typeCodeImpl);
/*  909 */     TypeCode typeCode2 = realType(paramTypeCode);
/*      */ 
/*      */ 
/*      */     
/*  913 */     if (typeCode1.kind().value() != typeCode2.kind().value()) {
/*  914 */       return false;
/*      */     }
/*      */     
/*  917 */     String str1 = null;
/*  918 */     String str2 = null;
/*      */     try {
/*  920 */       str1 = id();
/*  921 */       str2 = paramTypeCode.id();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  927 */       if (str1 != null && str2 != null) {
/*  928 */         return str1.equals(str2);
/*      */       }
/*  930 */     } catch (BadKind badKind) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  937 */     int i = typeCode1.kind().value();
/*      */     try {
/*  939 */       if (i == 15 || i == 16 || i == 17 || i == 22 || i == 29)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  945 */         if (typeCode1.member_count() != typeCode2.member_count())
/*  946 */           return false; 
/*      */       }
/*  948 */       if (i == 16)
/*      */       {
/*  950 */         if (typeCode1.default_index() != typeCode2.default_index())
/*  951 */           return false; 
/*      */       }
/*  953 */       if (i == 18 || i == 27 || i == 19 || i == 20)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  958 */         if (typeCode1.length() != typeCode2.length())
/*  959 */           return false; 
/*      */       }
/*  961 */       if (i == 28)
/*      */       {
/*  963 */         if (typeCode1.fixed_digits() != typeCode2.fixed_digits() || typeCode1
/*  964 */           .fixed_scale() != typeCode2.fixed_scale())
/*  965 */           return false; 
/*      */       }
/*  967 */       if (i == 16) {
/*      */         
/*  969 */         for (byte b = 0; b < typeCode1.member_count(); b++) {
/*  970 */           if (typeCode1.member_label(b) != typeCode2.member_label(b))
/*  971 */             return false; 
/*      */         } 
/*  973 */         if (!typeCode1.discriminator_type().equivalent(typeCode2
/*  974 */             .discriminator_type()))
/*  975 */           return false; 
/*      */       } 
/*  977 */       if (i == 21 || i == 30 || i == 19 || i == 20)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  982 */         if (!typeCode1.content_type().equivalent(typeCode2.content_type()))
/*  983 */           return false; 
/*      */       }
/*  985 */       if (i == 15 || i == 16 || i == 22 || i == 29)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  990 */         for (byte b = 0; b < typeCode1.member_count(); b++) {
/*  991 */           if (!typeCode1.member_type(b).equivalent(typeCode2
/*  992 */               .member_type(b)))
/*  993 */             return false; 
/*      */         } 
/*      */       }
/*  996 */     } catch (BadKind badKind) {
/*      */       
/*  998 */       throw this.wrapper.badkindCannotOccur();
/*  999 */     } catch (Bounds bounds) {
/*      */       
/* 1001 */       throw this.wrapper.boundsCannotOccur();
/*      */     } 
/*      */ 
/*      */     
/* 1005 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCode get_compact_typecode() {
/* 1012 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public TCKind kind() {
/* 1017 */     if (this._kind == -1)
/* 1018 */       return indirectType().kind(); 
/* 1019 */     return TCKind.from_int(this._kind);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean is_recursive() {
/* 1026 */     return (this._kind == -1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String id() throws BadKind {
/* 1032 */     switch (this._kind) {
/*      */ 
/*      */ 
/*      */       
/*      */       case -1:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 21:
/*      */       case 22:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/* 1047 */         return this._id;
/*      */     } 
/*      */     
/* 1050 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String name() throws BadKind {
/* 1057 */     switch (this._kind) {
/*      */       case -1:
/* 1059 */         return indirectType().name();
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 21:
/*      */       case 22:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/* 1070 */         return this._name;
/*      */     } 
/* 1072 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int member_count() throws BadKind {
/* 1079 */     switch (this._kind) {
/*      */       case -1:
/* 1081 */         return indirectType().member_count();
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 22:
/*      */       case 29:
/* 1087 */         return this._memberCount;
/*      */     } 
/* 1089 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String member_name(int paramInt) throws BadKind, Bounds {
/* 1096 */     switch (this._kind) {
/*      */       case -1:
/* 1098 */         return indirectType().member_name(paramInt);
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 22:
/*      */       case 29:
/*      */         try {
/* 1105 */           return this._memberNames[paramInt];
/* 1106 */         } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 1107 */           throw new Bounds();
/*      */         } 
/*      */     } 
/* 1110 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCode member_type(int paramInt) throws BadKind, Bounds {
/* 1117 */     switch (this._kind) {
/*      */       case -1:
/* 1119 */         return indirectType().member_type(paramInt);
/*      */       case 15:
/*      */       case 16:
/*      */       case 22:
/*      */       case 29:
/*      */         try {
/* 1125 */           return this._memberTypes[paramInt];
/* 1126 */         } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 1127 */           throw new Bounds();
/*      */         } 
/*      */     } 
/* 1130 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Any member_label(int paramInt) throws BadKind, Bounds {
/* 1137 */     switch (this._kind) {
/*      */       case -1:
/* 1139 */         return indirectType().member_label(paramInt);
/*      */       
/*      */       case 16:
/*      */         try {
/* 1143 */           return new AnyImpl(this._orb, this._unionLabels[paramInt]);
/* 1144 */         } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 1145 */           throw new Bounds();
/*      */         } 
/*      */     } 
/* 1148 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCode discriminator_type() throws BadKind {
/* 1155 */     switch (this._kind) {
/*      */       case -1:
/* 1157 */         return indirectType().discriminator_type();
/*      */       case 16:
/* 1159 */         return this._discriminator;
/*      */     } 
/* 1161 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int default_index() throws BadKind {
/* 1168 */     switch (this._kind) {
/*      */       case -1:
/* 1170 */         return indirectType().default_index();
/*      */       case 16:
/* 1172 */         return this._defaultIndex;
/*      */     } 
/* 1174 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() throws BadKind {
/* 1181 */     switch (this._kind) {
/*      */       case -1:
/* 1183 */         return indirectType().length();
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 27:
/* 1188 */         return this._length;
/*      */     } 
/* 1190 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCode content_type() throws BadKind {
/* 1197 */     switch (this._kind) {
/*      */       case -1:
/* 1199 */         return indirectType().content_type();
/*      */       case 19:
/* 1201 */         return lazy_content_type();
/*      */       case 20:
/*      */       case 21:
/*      */       case 30:
/* 1205 */         return this._contentType;
/*      */     } 
/* 1207 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */   
/*      */   public short fixed_digits() throws BadKind {
/* 1212 */     switch (this._kind) {
/*      */       case 28:
/* 1214 */         return this._digits;
/*      */     } 
/* 1216 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */   
/*      */   public short fixed_scale() throws BadKind {
/* 1221 */     switch (this._kind) {
/*      */       case 28:
/* 1223 */         return this._scale;
/*      */     } 
/* 1225 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short member_visibility(int paramInt) throws BadKind, Bounds {
/* 1231 */     switch (this._kind) {
/*      */       case -1:
/* 1233 */         return indirectType().member_visibility(paramInt);
/*      */       case 29:
/*      */         try {
/* 1236 */           return this._memberAccess[paramInt];
/* 1237 */         } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 1238 */           throw new Bounds();
/*      */         } 
/*      */     } 
/* 1241 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */   
/*      */   public short type_modifier() throws BadKind {
/* 1246 */     switch (this._kind) {
/*      */       case -1:
/* 1248 */         return indirectType().type_modifier();
/*      */       case 29:
/* 1250 */         return this._type_modifier;
/*      */     } 
/* 1252 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */   
/*      */   public TypeCode concrete_base_type() throws BadKind {
/* 1257 */     switch (this._kind) {
/*      */       case -1:
/* 1259 */         return indirectType().concrete_base_type();
/*      */       case 29:
/* 1261 */         return this._concrete_base;
/*      */     } 
/* 1263 */     throw new BadKind();
/*      */   }
/*      */ 
/*      */   
/*      */   public void read_value(InputStream paramInputStream) {
/* 1268 */     if (paramInputStream instanceof TypeCodeReader) {
/*      */       
/* 1270 */       if (read_value_kind((TypeCodeReader)paramInputStream))
/* 1271 */         read_value_body(paramInputStream); 
/* 1272 */     } else if (paramInputStream instanceof CDRInputStream) {
/* 1273 */       WrapperInputStream wrapperInputStream = new WrapperInputStream((CDRInputStream)paramInputStream);
/*      */ 
/*      */       
/* 1276 */       if (read_value_kind((TypeCodeReader)wrapperInputStream))
/* 1277 */         read_value_body((InputStream)wrapperInputStream); 
/*      */     } else {
/* 1279 */       read_value_kind(paramInputStream);
/* 1280 */       read_value_body(paramInputStream);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void read_value_recursive(TypeCodeInputStream paramTypeCodeInputStream) {
/* 1286 */     if (paramTypeCodeInputStream instanceof TypeCodeReader) {
/* 1287 */       if (read_value_kind((TypeCodeReader)paramTypeCodeInputStream))
/* 1288 */         read_value_body((InputStream)paramTypeCodeInputStream); 
/*      */     } else {
/* 1290 */       read_value_kind((InputStream)paramTypeCodeInputStream);
/* 1291 */       read_value_body((InputStream)paramTypeCodeInputStream);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean read_value_kind(TypeCodeReader paramTypeCodeReader) {
/* 1297 */     this._kind = paramTypeCodeReader.read_long();
/*      */ 
/*      */     
/* 1300 */     int i = paramTypeCodeReader.getTopLevelPosition() - 4;
/*      */ 
/*      */     
/* 1303 */     if ((this._kind < 0 || this._kind > typeTable.length) && this._kind != -1) {
/* 1304 */       throw this.wrapper.cannotMarshalBadTckind();
/*      */     }
/*      */ 
/*      */     
/* 1308 */     if (this._kind == 31) {
/* 1309 */       throw this.wrapper.cannotMarshalNative();
/*      */     }
/*      */ 
/*      */     
/* 1313 */     TypeCodeReader typeCodeReader = paramTypeCodeReader.getTopLevelStream();
/*      */     
/* 1315 */     if (this._kind == -1) {
/* 1316 */       int j = paramTypeCodeReader.read_long();
/* 1317 */       if (j > -4) {
/* 1318 */         throw this.wrapper.invalidIndirection(new Integer(j));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1323 */       int k = paramTypeCodeReader.getTopLevelPosition();
/*      */       
/* 1325 */       int m = k - 4 + j;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1332 */       TypeCodeImpl typeCodeImpl = typeCodeReader.getTypeCodeAtPosition(m);
/* 1333 */       if (typeCodeImpl == null)
/* 1334 */         throw this.wrapper.indirectionNotFound(new Integer(m)); 
/* 1335 */       setIndirectType(typeCodeImpl);
/* 1336 */       return false;
/*      */     } 
/*      */     
/* 1339 */     typeCodeReader.addTypeCodeAtPosition(this, i);
/* 1340 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   void read_value_kind(InputStream paramInputStream) {
/* 1345 */     this._kind = paramInputStream.read_long();
/*      */ 
/*      */     
/* 1348 */     if ((this._kind < 0 || this._kind > typeTable.length) && this._kind != -1) {
/* 1349 */       throw this.wrapper.cannotMarshalBadTckind();
/*      */     }
/*      */     
/* 1352 */     if (this._kind == 31) {
/* 1353 */       throw this.wrapper.cannotMarshalNative();
/*      */     }
/* 1355 */     if (this._kind == -1) {
/* 1356 */       throw this.wrapper.recursiveTypecodeError();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void read_value_body(InputStream paramInputStream) {
/*      */     TypeCodeInputStream typeCodeInputStream;
/*      */     byte b;
/* 1364 */     switch (typeTable[this._kind]) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1370 */         switch (this._kind) {
/*      */           case 18:
/*      */           case 27:
/* 1373 */             this._length = paramInputStream.read_long();
/*      */             break;
/*      */           case 28:
/* 1376 */             this._digits = paramInputStream.read_ushort();
/* 1377 */             this._scale = paramInputStream.read_short();
/*      */             break;
/*      */         } 
/* 1380 */         throw this.wrapper.invalidSimpleTypecode();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1386 */         typeCodeInputStream = TypeCodeInputStream.readEncapsulation(paramInputStream, paramInputStream
/* 1387 */             .orb());
/*      */         
/* 1389 */         switch (this._kind) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 14:
/*      */           case 32:
/* 1395 */             setId(typeCodeInputStream.read_string());
/*      */             
/* 1397 */             this._name = typeCodeInputStream.read_string();
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 16:
/* 1404 */             setId(typeCodeInputStream.read_string());
/*      */ 
/*      */             
/* 1407 */             this._name = typeCodeInputStream.read_string();
/*      */ 
/*      */             
/* 1410 */             this._discriminator = new TypeCodeImpl((ORB)paramInputStream.orb());
/* 1411 */             this._discriminator.read_value_recursive(typeCodeInputStream);
/*      */ 
/*      */             
/* 1414 */             this._defaultIndex = typeCodeInputStream.read_long();
/*      */ 
/*      */             
/* 1417 */             this._memberCount = typeCodeInputStream.read_long();
/*      */ 
/*      */             
/* 1420 */             this._unionLabels = new AnyImpl[this._memberCount];
/* 1421 */             this._memberNames = new String[this._memberCount];
/* 1422 */             this._memberTypes = new TypeCodeImpl[this._memberCount];
/*      */ 
/*      */             
/* 1425 */             for (b = 0; b < this._memberCount; b++) {
/* 1426 */               this._unionLabels[b] = new AnyImpl((ORB)paramInputStream.orb());
/* 1427 */               if (b == this._defaultIndex) {
/*      */                 
/* 1429 */                 this._unionLabels[b].insert_octet(typeCodeInputStream.read_octet());
/*      */               } else {
/* 1431 */                 switch (realType(this._discriminator).kind().value()) {
/*      */                   case 2:
/* 1433 */                     this._unionLabels[b].insert_short(typeCodeInputStream.read_short());
/*      */                     break;
/*      */                   case 3:
/* 1436 */                     this._unionLabels[b].insert_long(typeCodeInputStream.read_long());
/*      */                     break;
/*      */                   case 4:
/* 1439 */                     this._unionLabels[b].insert_ushort(typeCodeInputStream.read_short());
/*      */                     break;
/*      */                   case 5:
/* 1442 */                     this._unionLabels[b].insert_ulong(typeCodeInputStream.read_long());
/*      */                     break;
/*      */                   case 6:
/* 1445 */                     this._unionLabels[b].insert_float(typeCodeInputStream.read_float());
/*      */                     break;
/*      */                   case 7:
/* 1448 */                     this._unionLabels[b].insert_double(typeCodeInputStream.read_double());
/*      */                     break;
/*      */                   case 8:
/* 1451 */                     this._unionLabels[b].insert_boolean(typeCodeInputStream.read_boolean());
/*      */                     break;
/*      */                   case 9:
/* 1454 */                     this._unionLabels[b].insert_char(typeCodeInputStream.read_char());
/*      */                     break;
/*      */                   case 17:
/* 1457 */                     this._unionLabels[b].type(this._discriminator);
/* 1458 */                     this._unionLabels[b].insert_long(typeCodeInputStream.read_long());
/*      */                     break;
/*      */                   case 23:
/* 1461 */                     this._unionLabels[b].insert_longlong(typeCodeInputStream.read_longlong());
/*      */                     break;
/*      */                   case 24:
/* 1464 */                     this._unionLabels[b].insert_ulonglong(typeCodeInputStream.read_longlong());
/*      */                     break;
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   case 26:
/* 1471 */                     this._unionLabels[b].insert_wchar(typeCodeInputStream.read_wchar());
/*      */                     break;
/*      */                   default:
/* 1474 */                     throw this.wrapper.invalidComplexTypecode();
/*      */                 } 
/*      */               } 
/* 1477 */               this._memberNames[b] = typeCodeInputStream.read_string();
/* 1478 */               this._memberTypes[b] = new TypeCodeImpl((ORB)paramInputStream.orb());
/* 1479 */               this._memberTypes[b].read_value_recursive(typeCodeInputStream);
/* 1480 */               this._memberTypes[b].setParent(this);
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 17:
/* 1488 */             setId(typeCodeInputStream.read_string());
/*      */ 
/*      */             
/* 1491 */             this._name = typeCodeInputStream.read_string();
/*      */ 
/*      */             
/* 1494 */             this._memberCount = typeCodeInputStream.read_long();
/*      */ 
/*      */             
/* 1497 */             this._memberNames = new String[this._memberCount];
/*      */ 
/*      */             
/* 1500 */             for (b = 0; b < this._memberCount; b++) {
/* 1501 */               this._memberNames[b] = typeCodeInputStream.read_string();
/*      */             }
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 19:
/* 1508 */             this._contentType = new TypeCodeImpl((ORB)paramInputStream.orb());
/* 1509 */             this._contentType.read_value_recursive(typeCodeInputStream);
/*      */ 
/*      */             
/* 1512 */             this._length = typeCodeInputStream.read_long();
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 20:
/* 1519 */             this._contentType = new TypeCodeImpl((ORB)paramInputStream.orb());
/* 1520 */             this._contentType.read_value_recursive(typeCodeInputStream);
/*      */ 
/*      */             
/* 1523 */             this._length = typeCodeInputStream.read_long();
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 21:
/*      */           case 30:
/* 1531 */             setId(typeCodeInputStream.read_string());
/*      */ 
/*      */             
/* 1534 */             this._name = typeCodeInputStream.read_string();
/*      */ 
/*      */             
/* 1537 */             this._contentType = new TypeCodeImpl((ORB)paramInputStream.orb());
/* 1538 */             this._contentType.read_value_recursive(typeCodeInputStream);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 15:
/*      */           case 22:
/* 1546 */             setId(typeCodeInputStream.read_string());
/*      */ 
/*      */             
/* 1549 */             this._name = typeCodeInputStream.read_string();
/*      */ 
/*      */             
/* 1552 */             this._memberCount = typeCodeInputStream.read_long();
/*      */ 
/*      */             
/* 1555 */             this._memberNames = new String[this._memberCount];
/* 1556 */             this._memberTypes = new TypeCodeImpl[this._memberCount];
/*      */ 
/*      */             
/* 1559 */             for (b = 0; b < this._memberCount; b++) {
/* 1560 */               this._memberNames[b] = typeCodeInputStream.read_string();
/* 1561 */               this._memberTypes[b] = new TypeCodeImpl((ORB)paramInputStream.orb());
/*      */ 
/*      */               
/* 1564 */               this._memberTypes[b].read_value_recursive(typeCodeInputStream);
/* 1565 */               this._memberTypes[b].setParent(this);
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 29:
/* 1573 */             setId(typeCodeInputStream.read_string());
/*      */ 
/*      */             
/* 1576 */             this._name = typeCodeInputStream.read_string();
/*      */ 
/*      */             
/* 1579 */             this._type_modifier = typeCodeInputStream.read_short();
/*      */ 
/*      */             
/* 1582 */             this._concrete_base = new TypeCodeImpl((ORB)paramInputStream.orb());
/* 1583 */             this._concrete_base.read_value_recursive(typeCodeInputStream);
/* 1584 */             if (this._concrete_base.kind().value() == 0) {
/* 1585 */               this._concrete_base = null;
/*      */             }
/*      */ 
/*      */             
/* 1589 */             this._memberCount = typeCodeInputStream.read_long();
/*      */ 
/*      */             
/* 1592 */             this._memberNames = new String[this._memberCount];
/* 1593 */             this._memberTypes = new TypeCodeImpl[this._memberCount];
/* 1594 */             this._memberAccess = new short[this._memberCount];
/*      */ 
/*      */             
/* 1597 */             for (b = 0; b < this._memberCount; b++) {
/* 1598 */               this._memberNames[b] = typeCodeInputStream.read_string();
/* 1599 */               this._memberTypes[b] = new TypeCodeImpl((ORB)paramInputStream.orb());
/*      */ 
/*      */               
/* 1602 */               this._memberTypes[b].read_value_recursive(typeCodeInputStream);
/* 1603 */               this._memberTypes[b].setParent(this);
/* 1604 */               this._memberAccess[b] = typeCodeInputStream.read_short();
/*      */             } 
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/* 1610 */         throw this.wrapper.invalidTypecodeKindMarshal();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write_value(OutputStream paramOutputStream) {
/* 1620 */     if (paramOutputStream instanceof TypeCodeOutputStream) {
/* 1621 */       write_value((TypeCodeOutputStream)paramOutputStream);
/*      */     } else {
/* 1623 */       TypeCodeOutputStream typeCodeOutputStream = null;
/*      */       
/* 1625 */       if (this.outBuffer == null) {
/* 1626 */         typeCodeOutputStream = TypeCodeOutputStream.wrapOutputStream(paramOutputStream);
/* 1627 */         write_value(typeCodeOutputStream);
/* 1628 */         if (this.cachingEnabled)
/*      */         {
/* 1630 */           this.outBuffer = typeCodeOutputStream.getTypeCodeBuffer();
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
/* 1641 */       if (this.cachingEnabled && this.outBuffer != null) {
/* 1642 */         paramOutputStream.write_long(this._kind);
/* 1643 */         paramOutputStream.write_octet_array(this.outBuffer, 0, this.outBuffer.length);
/*      */       } else {
/*      */         
/* 1646 */         typeCodeOutputStream.writeRawBuffer((OutputStream)paramOutputStream, this._kind);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void write_value(TypeCodeOutputStream paramTypeCodeOutputStream) {
/*      */     TypeCodeOutputStream typeCodeOutputStream2;
/*      */     byte b;
/* 1654 */     if (this._kind == 31) {
/* 1655 */       throw this.wrapper.cannotMarshalNative();
/*      */     }
/* 1657 */     TypeCodeOutputStream typeCodeOutputStream1 = paramTypeCodeOutputStream.getTopLevelStream();
/*      */ 
/*      */     
/* 1660 */     if (this._kind == -1) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1665 */       int i = typeCodeOutputStream1.getPositionForID(this._id);
/* 1666 */       int j = paramTypeCodeOutputStream.getTopLevelPosition();
/*      */ 
/*      */ 
/*      */       
/* 1670 */       paramTypeCodeOutputStream.writeIndirection(-1, i);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1679 */     paramTypeCodeOutputStream.write_long(this._kind);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1688 */     typeCodeOutputStream1.addIDAtPosition(this._id, paramTypeCodeOutputStream.getTopLevelPosition() - 4);
/*      */     
/* 1690 */     switch (typeTable[this._kind]) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1696 */         switch (this._kind) {
/*      */           
/*      */           case 18:
/*      */           case 27:
/* 1700 */             paramTypeCodeOutputStream.write_long(this._length);
/*      */             break;
/*      */           case 28:
/* 1703 */             paramTypeCodeOutputStream.write_ushort(this._digits);
/* 1704 */             paramTypeCodeOutputStream.write_short(this._scale);
/*      */             break;
/*      */         } 
/*      */         
/* 1708 */         throw this.wrapper.invalidSimpleTypecode();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1715 */         typeCodeOutputStream2 = paramTypeCodeOutputStream.createEncapsulation(paramTypeCodeOutputStream.orb());
/*      */         
/* 1717 */         switch (this._kind) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 14:
/*      */           case 32:
/* 1723 */             typeCodeOutputStream2.write_string(this._id);
/*      */ 
/*      */             
/* 1726 */             typeCodeOutputStream2.write_string(this._name);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 16:
/* 1733 */             typeCodeOutputStream2.write_string(this._id);
/*      */ 
/*      */             
/* 1736 */             typeCodeOutputStream2.write_string(this._name);
/*      */ 
/*      */             
/* 1739 */             this._discriminator.write_value(typeCodeOutputStream2);
/*      */ 
/*      */             
/* 1742 */             typeCodeOutputStream2.write_long(this._defaultIndex);
/*      */ 
/*      */             
/* 1745 */             typeCodeOutputStream2.write_long(this._memberCount);
/*      */ 
/*      */             
/* 1748 */             for (b = 0; b < this._memberCount; b++) {
/*      */ 
/*      */               
/* 1751 */               if (b == this._defaultIndex) {
/* 1752 */                 typeCodeOutputStream2.write_octet(this._unionLabels[b].extract_octet());
/*      */               } else {
/*      */                 
/* 1755 */                 switch (realType(this._discriminator).kind().value()) {
/*      */                   case 2:
/* 1757 */                     typeCodeOutputStream2.write_short(this._unionLabels[b].extract_short());
/*      */                     break;
/*      */                   case 3:
/* 1760 */                     typeCodeOutputStream2.write_long(this._unionLabels[b].extract_long());
/*      */                     break;
/*      */                   case 4:
/* 1763 */                     typeCodeOutputStream2.write_short(this._unionLabels[b].extract_ushort());
/*      */                     break;
/*      */                   case 5:
/* 1766 */                     typeCodeOutputStream2.write_long(this._unionLabels[b].extract_ulong());
/*      */                     break;
/*      */                   case 6:
/* 1769 */                     typeCodeOutputStream2.write_float(this._unionLabels[b].extract_float());
/*      */                     break;
/*      */                   case 7:
/* 1772 */                     typeCodeOutputStream2.write_double(this._unionLabels[b].extract_double());
/*      */                     break;
/*      */                   case 8:
/* 1775 */                     typeCodeOutputStream2.write_boolean(this._unionLabels[b].extract_boolean());
/*      */                     break;
/*      */                   case 9:
/* 1778 */                     typeCodeOutputStream2.write_char(this._unionLabels[b].extract_char());
/*      */                     break;
/*      */                   case 17:
/* 1781 */                     typeCodeOutputStream2.write_long(this._unionLabels[b].extract_long());
/*      */                     break;
/*      */                   case 23:
/* 1784 */                     typeCodeOutputStream2.write_longlong(this._unionLabels[b].extract_longlong());
/*      */                     break;
/*      */                   case 24:
/* 1787 */                     typeCodeOutputStream2.write_longlong(this._unionLabels[b].extract_ulonglong());
/*      */                     break;
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   case 26:
/* 1794 */                     typeCodeOutputStream2.write_wchar(this._unionLabels[b].extract_wchar());
/*      */                     break;
/*      */                   default:
/* 1797 */                     throw this.wrapper.invalidComplexTypecode();
/*      */                 } 
/*      */               } 
/* 1800 */               typeCodeOutputStream2.write_string(this._memberNames[b]);
/* 1801 */               this._memberTypes[b].write_value(typeCodeOutputStream2);
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 17:
/* 1809 */             typeCodeOutputStream2.write_string(this._id);
/*      */ 
/*      */             
/* 1812 */             typeCodeOutputStream2.write_string(this._name);
/*      */ 
/*      */             
/* 1815 */             typeCodeOutputStream2.write_long(this._memberCount);
/*      */ 
/*      */             
/* 1818 */             for (b = 0; b < this._memberCount; b++) {
/* 1819 */               typeCodeOutputStream2.write_string(this._memberNames[b]);
/*      */             }
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 19:
/* 1826 */             lazy_content_type().write_value(typeCodeOutputStream2);
/*      */ 
/*      */             
/* 1829 */             typeCodeOutputStream2.write_long(this._length);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 20:
/* 1836 */             this._contentType.write_value(typeCodeOutputStream2);
/*      */ 
/*      */             
/* 1839 */             typeCodeOutputStream2.write_long(this._length);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 21:
/*      */           case 30:
/* 1847 */             typeCodeOutputStream2.write_string(this._id);
/*      */ 
/*      */             
/* 1850 */             typeCodeOutputStream2.write_string(this._name);
/*      */ 
/*      */             
/* 1853 */             this._contentType.write_value(typeCodeOutputStream2);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 15:
/*      */           case 22:
/* 1861 */             typeCodeOutputStream2.write_string(this._id);
/*      */ 
/*      */             
/* 1864 */             typeCodeOutputStream2.write_string(this._name);
/*      */ 
/*      */             
/* 1867 */             typeCodeOutputStream2.write_long(this._memberCount);
/*      */ 
/*      */             
/* 1870 */             for (b = 0; b < this._memberCount; b++) {
/* 1871 */               typeCodeOutputStream2.write_string(this._memberNames[b]);
/*      */ 
/*      */               
/* 1874 */               this._memberTypes[b].write_value(typeCodeOutputStream2);
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 29:
/* 1882 */             typeCodeOutputStream2.write_string(this._id);
/*      */ 
/*      */             
/* 1885 */             typeCodeOutputStream2.write_string(this._name);
/*      */ 
/*      */             
/* 1888 */             typeCodeOutputStream2.write_short(this._type_modifier);
/*      */ 
/*      */             
/* 1891 */             if (this._concrete_base == null) {
/* 1892 */               this._orb.get_primitive_tc(0).write_value(typeCodeOutputStream2);
/*      */             } else {
/* 1894 */               this._concrete_base.write_value(typeCodeOutputStream2);
/*      */             } 
/*      */ 
/*      */             
/* 1898 */             typeCodeOutputStream2.write_long(this._memberCount);
/*      */ 
/*      */             
/* 1901 */             for (b = 0; b < this._memberCount; b++) {
/* 1902 */               typeCodeOutputStream2.write_string(this._memberNames[b]);
/*      */ 
/*      */               
/* 1905 */               this._memberTypes[b].write_value(typeCodeOutputStream2);
/* 1906 */               typeCodeOutputStream2.write_short(this._memberAccess[b]);
/*      */             } 
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/* 1912 */             throw this.wrapper.invalidTypecodeKindMarshal();
/*      */         } 
/*      */ 
/*      */         
/* 1916 */         typeCodeOutputStream2.writeOctetSequenceTo((OutputStream)paramTypeCodeOutputStream); break;
/*      */     }  } protected void copy(InputStream paramInputStream, OutputStream paramOutputStream) { String str;
/*      */     Any any;
/*      */     byte b;
/*      */     AnyImpl anyImpl;
/*      */     int i;
/*      */     TypeCodeImpl typeCodeImpl;
/*      */     short s;
/*      */     int k;
/*      */     float f;
/*      */     double d;
/*      */     boolean bool;
/*      */     char c2;
/*      */     int j;
/*      */     long l;
/*      */     char c1;
/* 1932 */     switch (this._kind) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 31:
/*      */       case 32:
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 4:
/* 1942 */         paramOutputStream.write_short(paramInputStream.read_short());
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 5:
/* 1947 */         paramOutputStream.write_long(paramInputStream.read_long());
/*      */ 
/*      */       
/*      */       case 6:
/* 1951 */         paramOutputStream.write_float(paramInputStream.read_float());
/*      */ 
/*      */       
/*      */       case 7:
/* 1955 */         paramOutputStream.write_double(paramInputStream.read_double());
/*      */ 
/*      */       
/*      */       case 23:
/*      */       case 24:
/* 1960 */         paramOutputStream.write_longlong(paramInputStream.read_longlong());
/*      */ 
/*      */       
/*      */       case 25:
/* 1964 */         throw this.wrapper.tkLongDoubleNotSupported();
/*      */       
/*      */       case 8:
/* 1967 */         paramOutputStream.write_boolean(paramInputStream.read_boolean());
/*      */ 
/*      */       
/*      */       case 9:
/* 1971 */         paramOutputStream.write_char(paramInputStream.read_char());
/*      */ 
/*      */       
/*      */       case 26:
/* 1975 */         paramOutputStream.write_wchar(paramInputStream.read_wchar());
/*      */ 
/*      */       
/*      */       case 10:
/* 1979 */         paramOutputStream.write_octet(paramInputStream.read_octet());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 18:
/* 1985 */         str = paramInputStream.read_string();
/*      */         
/* 1987 */         if (this._length != 0 && str.length() > this._length) {
/* 1988 */           throw this.wrapper.badStringBounds(new Integer(str.length()), new Integer(this._length));
/*      */         }
/* 1990 */         paramOutputStream.write_string(str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 27:
/* 1997 */         str = paramInputStream.read_wstring();
/*      */         
/* 1999 */         if (this._length != 0 && str.length() > this._length) {
/* 2000 */           throw this.wrapper.badStringBounds(new Integer(str.length()), new Integer(this._length));
/*      */         }
/* 2002 */         paramOutputStream.write_wstring(str);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 28:
/* 2008 */         paramOutputStream.write_ushort(paramInputStream.read_ushort());
/* 2009 */         paramOutputStream.write_short(paramInputStream.read_short());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 11:
/* 2016 */         any = ((CDRInputStream)paramInputStream).orb().create_any();
/* 2017 */         typeCodeImpl = new TypeCodeImpl((ORB)paramOutputStream.orb());
/* 2018 */         typeCodeImpl.read_value((InputStream)paramInputStream);
/* 2019 */         typeCodeImpl.write_value((OutputStream)paramOutputStream);
/* 2020 */         any.read_value(paramInputStream, typeCodeImpl);
/* 2021 */         any.write_value(paramOutputStream);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 12:
/* 2027 */         paramOutputStream.write_TypeCode(paramInputStream.read_TypeCode());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 13:
/* 2033 */         paramOutputStream.write_Principal(paramInputStream.read_Principal());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 14:
/* 2039 */         paramOutputStream.write_Object(paramInputStream.read_Object());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 22:
/* 2045 */         paramOutputStream.write_string(paramInputStream.read_string());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 15:
/*      */       case 29:
/* 2053 */         for (b = 0; b < this._memberTypes.length; b++) {
/* 2054 */           this._memberTypes[b].copy(paramInputStream, paramOutputStream);
/*      */         }
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
/*      */       case 16:
/* 2079 */         anyImpl = new AnyImpl((ORB)paramInputStream.orb());
/*      */         
/* 2081 */         switch (realType(this._discriminator).kind().value()) {
/*      */           
/*      */           case 2:
/* 2084 */             s = paramInputStream.read_short();
/* 2085 */             anyImpl.insert_short(s);
/* 2086 */             paramOutputStream.write_short(s);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/* 2091 */             k = paramInputStream.read_long();
/* 2092 */             anyImpl.insert_long(k);
/* 2093 */             paramOutputStream.write_long(k);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 4:
/* 2098 */             k = paramInputStream.read_short();
/* 2099 */             anyImpl.insert_ushort(k);
/* 2100 */             paramOutputStream.write_short(k);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 5:
/* 2105 */             k = paramInputStream.read_long();
/* 2106 */             anyImpl.insert_ulong(k);
/* 2107 */             paramOutputStream.write_long(k);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 6:
/* 2112 */             f = paramInputStream.read_float();
/* 2113 */             anyImpl.insert_float(f);
/* 2114 */             paramOutputStream.write_float(f);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 7:
/* 2119 */             d = paramInputStream.read_double();
/* 2120 */             anyImpl.insert_double(d);
/* 2121 */             paramOutputStream.write_double(d);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 8:
/* 2126 */             bool = paramInputStream.read_boolean();
/* 2127 */             anyImpl.insert_boolean(bool);
/* 2128 */             paramOutputStream.write_boolean(bool);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 9:
/* 2133 */             c2 = paramInputStream.read_char();
/* 2134 */             anyImpl.insert_char(c2);
/* 2135 */             paramOutputStream.write_char(c2);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 17:
/* 2140 */             j = paramInputStream.read_long();
/* 2141 */             anyImpl.type(this._discriminator);
/* 2142 */             anyImpl.insert_long(j);
/* 2143 */             paramOutputStream.write_long(j);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 23:
/* 2148 */             l = paramInputStream.read_longlong();
/* 2149 */             anyImpl.insert_longlong(l);
/* 2150 */             paramOutputStream.write_longlong(l);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 24:
/* 2155 */             l = paramInputStream.read_longlong();
/* 2156 */             anyImpl.insert_ulonglong(l);
/* 2157 */             paramOutputStream.write_longlong(l);
/*      */             break;
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
/*      */           case 26:
/* 2170 */             c1 = paramInputStream.read_wchar();
/* 2171 */             anyImpl.insert_wchar(c1);
/* 2172 */             paramOutputStream.write_wchar(c1);
/*      */             break;
/*      */           
/*      */           default:
/* 2176 */             throw this.wrapper.illegalUnionDiscriminatorType();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2183 */         for (c1 = Character.MIN_VALUE; c1 < this._unionLabels.length; c1++) {
/*      */           
/* 2185 */           if (anyImpl.equal(this._unionLabels[c1])) {
/* 2186 */             this._memberTypes[c1].copy(paramInputStream, paramOutputStream);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 2191 */         if (c1 == this._unionLabels.length)
/*      */         {
/* 2193 */           if (this._defaultIndex != -1)
/*      */           {
/* 2195 */             this._memberTypes[this._defaultIndex].copy(paramInputStream, paramOutputStream);
/*      */           }
/*      */         }
/*      */ 
/*      */       
/*      */       case 17:
/* 2201 */         paramOutputStream.write_long(paramInputStream.read_long());
/*      */ 
/*      */ 
/*      */       
/*      */       case 19:
/* 2206 */         i = paramInputStream.read_long();
/*      */ 
/*      */         
/* 2209 */         if (this._length != 0 && i > this._length) {
/* 2210 */           throw this.wrapper.badSequenceBounds(new Integer(i), new Integer(this._length));
/*      */         }
/*      */ 
/*      */         
/* 2214 */         paramOutputStream.write_long(i);
/*      */ 
/*      */         
/* 2217 */         lazy_content_type();
/* 2218 */         for (c1 = Character.MIN_VALUE; c1 < i; c1++) {
/* 2219 */           this._contentType.copy(paramInputStream, paramOutputStream);
/*      */         }
/*      */ 
/*      */       
/*      */       case 20:
/* 2224 */         for (c1 = Character.MIN_VALUE; c1 < this._length; c1++) {
/* 2225 */           this._contentType.copy(paramInputStream, paramOutputStream);
/*      */         }
/*      */ 
/*      */       
/*      */       case 21:
/*      */       case 30:
/* 2231 */         this._contentType.copy(paramInputStream, paramOutputStream);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case -1:
/* 2239 */         indirectType().copy(paramInputStream, paramOutputStream);
/*      */     } 
/*      */ 
/*      */     
/* 2243 */     throw this.wrapper.invalidTypecodeKindMarshal(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static short digits(BigDecimal paramBigDecimal) {
/* 2249 */     if (paramBigDecimal == null)
/* 2250 */       return 0; 
/* 2251 */     short s = (short)paramBigDecimal.unscaledValue().toString().length();
/* 2252 */     if (paramBigDecimal.signum() == -1)
/* 2253 */       s = (short)(s - 1); 
/* 2254 */     return s;
/*      */   }
/*      */   
/*      */   protected static short scale(BigDecimal paramBigDecimal) {
/* 2258 */     if (paramBigDecimal == null)
/* 2259 */       return 0; 
/* 2260 */     return (short)paramBigDecimal.scale();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int currentUnionMemberIndex(Any paramAny) throws BadKind {
/* 2269 */     if (this._kind != 16) {
/* 2270 */       throw new BadKind();
/*      */     }
/*      */     
/* 2273 */     try { for (byte b = 0; b < member_count(); b++) {
/* 2274 */         if (member_label(b).equal(paramAny)) {
/* 2275 */           return b;
/*      */         }
/*      */       } 
/* 2278 */       if (this._defaultIndex != -1) {
/* 2279 */         return this._defaultIndex;
/*      */       } }
/* 2281 */     catch (BadKind badKind) {  }
/* 2282 */     catch (Bounds bounds) {}
/*      */     
/* 2284 */     return -1;
/*      */   }
/*      */   
/*      */   public String description() {
/* 2288 */     return "TypeCodeImpl with kind " + this._kind + " and id " + this._id;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 2292 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
/* 2293 */     PrintStream printStream = new PrintStream(byteArrayOutputStream, true);
/* 2294 */     printStream(printStream);
/* 2295 */     return super.toString() + " =\n" + byteArrayOutputStream.toString();
/*      */   }
/*      */   
/*      */   public void printStream(PrintStream paramPrintStream) {
/* 2299 */     printStream(paramPrintStream, 0);
/*      */   }
/*      */   private void printStream(PrintStream paramPrintStream, int paramInt) {
/*      */     byte b;
/* 2303 */     if (this._kind == -1) {
/* 2304 */       paramPrintStream.print("indirect " + this._id);
/*      */       
/*      */       return;
/*      */     } 
/* 2308 */     switch (this._kind) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 31:
/* 2329 */         paramPrintStream.print(kindNames[this._kind] + " " + this._name);
/*      */         return;
/*      */       
/*      */       case 15:
/*      */       case 22:
/*      */       case 29:
/* 2335 */         paramPrintStream.println(kindNames[this._kind] + " " + this._name + " = {");
/* 2336 */         for (b = 0; b < this._memberCount; b++) {
/*      */           
/* 2338 */           paramPrintStream.print(indent(paramInt + 1));
/* 2339 */           if (this._memberTypes[b] != null) {
/* 2340 */             this._memberTypes[b].printStream(paramPrintStream, paramInt + 1);
/*      */           } else {
/* 2342 */             paramPrintStream.print("<unknown type>");
/* 2343 */           }  paramPrintStream.println(" " + this._memberNames[b] + ";");
/*      */         } 
/* 2345 */         paramPrintStream.print(indent(paramInt) + "}");
/*      */         return;
/*      */       
/*      */       case 16:
/* 2349 */         paramPrintStream.print("union " + this._name + "...");
/*      */         return;
/*      */       
/*      */       case 17:
/* 2353 */         paramPrintStream.print("enum " + this._name + "...");
/*      */         return;
/*      */       
/*      */       case 18:
/* 2357 */         if (this._length == 0) {
/* 2358 */           paramPrintStream.print("unbounded string " + this._name);
/*      */         } else {
/* 2360 */           paramPrintStream.print("bounded string(" + this._length + ") " + this._name);
/*      */         } 
/*      */         return;
/*      */       case 19:
/*      */       case 20:
/* 2365 */         paramPrintStream.println(kindNames[this._kind] + "[" + this._length + "] " + this._name + " = {");
/* 2366 */         paramPrintStream.print(indent(paramInt + 1));
/* 2367 */         if (lazy_content_type() != null) {
/* 2368 */           lazy_content_type().printStream(paramPrintStream, paramInt + 1);
/*      */         }
/* 2370 */         paramPrintStream.println(indent(paramInt) + "}");
/*      */         return;
/*      */       
/*      */       case 21:
/* 2374 */         paramPrintStream.print("alias " + this._name + " = " + ((this._contentType != null) ? this._contentType._name : "<unresolved>"));
/*      */         return;
/*      */ 
/*      */       
/*      */       case 27:
/* 2379 */         paramPrintStream.print("wstring[" + this._length + "] " + this._name);
/*      */         return;
/*      */       
/*      */       case 28:
/* 2383 */         paramPrintStream.print("fixed(" + this._digits + ", " + this._scale + ") " + this._name);
/*      */         return;
/*      */       
/*      */       case 30:
/* 2387 */         paramPrintStream.print("valueBox " + this._name + "...");
/*      */         return;
/*      */       
/*      */       case 32:
/* 2391 */         paramPrintStream.print("abstractInterface " + this._name + "...");
/*      */         return;
/*      */     } 
/*      */     
/* 2395 */     paramPrintStream.print("<unknown type>");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String indent(int paramInt) {
/* 2401 */     String str = "";
/* 2402 */     for (byte b = 0; b < paramInt; b++) {
/* 2403 */       str = str + "  ";
/*      */     }
/* 2405 */     return str;
/*      */   }
/*      */   
/*      */   protected void setCaching(boolean paramBoolean) {
/* 2409 */     this.cachingEnabled = paramBoolean;
/* 2410 */     if (!paramBoolean)
/* 2411 */       this.outBuffer = null; 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/TypeCodeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */