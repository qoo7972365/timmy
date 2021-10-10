/*      */ package org.omg.DynamicAny;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Properties;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.TCKind;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.portable.Delegate;
/*      */ import org.omg.CORBA.portable.ObjectImpl;
/*      */ import org.omg.CORBA.portable.ServantObject;
/*      */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*      */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*      */ 
/*      */ 
/*      */ public class _DynUnionStub
/*      */   extends ObjectImpl
/*      */   implements DynUnion
/*      */ {
/*   24 */   public static final Class _opsClass = DynUnionOperations.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DynAny get_discriminator() {
/*   33 */     ServantObject servantObject = _servant_preinvoke("get_discriminator", _opsClass);
/*   34 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   37 */       return dynUnionOperations.get_discriminator();
/*      */     } finally {
/*   39 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_discriminator(DynAny paramDynAny) throws TypeMismatch {
/*   61 */     ServantObject servantObject = _servant_preinvoke("set_discriminator", _opsClass);
/*   62 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   65 */       dynUnionOperations.set_discriminator(paramDynAny);
/*      */     } finally {
/*   67 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_to_default_member() throws TypeMismatch {
/*   80 */     ServantObject servantObject = _servant_preinvoke("set_to_default_member", _opsClass);
/*   81 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   84 */       dynUnionOperations.set_to_default_member();
/*      */     } finally {
/*   86 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_to_no_active_member() throws TypeMismatch {
/*  100 */     ServantObject servantObject = _servant_preinvoke("set_to_no_active_member", _opsClass);
/*  101 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  104 */       dynUnionOperations.set_to_no_active_member();
/*      */     } finally {
/*  106 */       _servant_postinvoke(servantObject);
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
/*      */   public boolean has_no_active_member() {
/*  120 */     ServantObject servantObject = _servant_preinvoke("has_no_active_member", _opsClass);
/*  121 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  124 */       return dynUnionOperations.has_no_active_member();
/*      */     } finally {
/*  126 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TCKind discriminator_kind() {
/*  136 */     ServantObject servantObject = _servant_preinvoke("discriminator_kind", _opsClass);
/*  137 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  140 */       return dynUnionOperations.discriminator_kind();
/*      */     } finally {
/*  142 */       _servant_postinvoke(servantObject);
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
/*      */   public TCKind member_kind() throws InvalidValue {
/*  154 */     ServantObject servantObject = _servant_preinvoke("member_kind", _opsClass);
/*  155 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  158 */       return dynUnionOperations.member_kind();
/*      */     } finally {
/*  160 */       _servant_postinvoke(servantObject);
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
/*      */   public DynAny member() throws InvalidValue {
/*  174 */     ServantObject servantObject = _servant_preinvoke("member", _opsClass);
/*  175 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  178 */       return dynUnionOperations.member();
/*      */     } finally {
/*  180 */       _servant_postinvoke(servantObject);
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
/*      */   public String member_name() throws InvalidValue {
/*  193 */     ServantObject servantObject = _servant_preinvoke("member_name", _opsClass);
/*  194 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  197 */       return dynUnionOperations.member_name();
/*      */     } finally {
/*  199 */       _servant_postinvoke(servantObject);
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
/*      */   public TypeCode type() {
/*  215 */     ServantObject servantObject = _servant_preinvoke("type", _opsClass);
/*  216 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  219 */       return dynUnionOperations.type();
/*      */     } finally {
/*  221 */       _servant_postinvoke(servantObject);
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
/*      */   public void assign(DynAny paramDynAny) throws TypeMismatch {
/*  237 */     ServantObject servantObject = _servant_preinvoke("assign", _opsClass);
/*  238 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  241 */       dynUnionOperations.assign(paramDynAny);
/*      */     } finally {
/*  243 */       _servant_postinvoke(servantObject);
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
/*      */   public void from_any(Any paramAny) throws TypeMismatch, InvalidValue {
/*  258 */     ServantObject servantObject = _servant_preinvoke("from_any", _opsClass);
/*  259 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  262 */       dynUnionOperations.from_any(paramAny);
/*      */     } finally {
/*  264 */       _servant_postinvoke(servantObject);
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
/*      */   public Any to_any() {
/*  278 */     ServantObject servantObject = _servant_preinvoke("to_any", _opsClass);
/*  279 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  282 */       return dynUnionOperations.to_any();
/*      */     } finally {
/*  284 */       _servant_postinvoke(servantObject);
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
/*      */   public boolean equal(DynAny paramDynAny) {
/*  299 */     ServantObject servantObject = _servant_preinvoke("equal", _opsClass);
/*  300 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  303 */       return dynUnionOperations.equal(paramDynAny);
/*      */     } finally {
/*  305 */       _servant_postinvoke(servantObject);
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
/*      */   public void destroy() {
/*  326 */     ServantObject servantObject = _servant_preinvoke("destroy", _opsClass);
/*  327 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  330 */       dynUnionOperations.destroy();
/*      */     } finally {
/*  332 */       _servant_postinvoke(servantObject);
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
/*      */   public DynAny copy() {
/*  346 */     ServantObject servantObject = _servant_preinvoke("copy", _opsClass);
/*  347 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  350 */       return dynUnionOperations.copy();
/*      */     } finally {
/*  352 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_boolean(boolean paramBoolean) throws TypeMismatch, InvalidValue {
/*  365 */     ServantObject servantObject = _servant_preinvoke("insert_boolean", _opsClass);
/*  366 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  369 */       dynUnionOperations.insert_boolean(paramBoolean);
/*      */     } finally {
/*  371 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_octet(byte paramByte) throws TypeMismatch, InvalidValue {
/*  384 */     ServantObject servantObject = _servant_preinvoke("insert_octet", _opsClass);
/*  385 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  388 */       dynUnionOperations.insert_octet(paramByte);
/*      */     } finally {
/*  390 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_char(char paramChar) throws TypeMismatch, InvalidValue {
/*  403 */     ServantObject servantObject = _servant_preinvoke("insert_char", _opsClass);
/*  404 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  407 */       dynUnionOperations.insert_char(paramChar);
/*      */     } finally {
/*  409 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_short(short paramShort) throws TypeMismatch, InvalidValue {
/*  422 */     ServantObject servantObject = _servant_preinvoke("insert_short", _opsClass);
/*  423 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  426 */       dynUnionOperations.insert_short(paramShort);
/*      */     } finally {
/*  428 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_ushort(short paramShort) throws TypeMismatch, InvalidValue {
/*  441 */     ServantObject servantObject = _servant_preinvoke("insert_ushort", _opsClass);
/*  442 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  445 */       dynUnionOperations.insert_ushort(paramShort);
/*      */     } finally {
/*  447 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_long(int paramInt) throws TypeMismatch, InvalidValue {
/*  460 */     ServantObject servantObject = _servant_preinvoke("insert_long", _opsClass);
/*  461 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  464 */       dynUnionOperations.insert_long(paramInt);
/*      */     } finally {
/*  466 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_ulong(int paramInt) throws TypeMismatch, InvalidValue {
/*  479 */     ServantObject servantObject = _servant_preinvoke("insert_ulong", _opsClass);
/*  480 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  483 */       dynUnionOperations.insert_ulong(paramInt);
/*      */     } finally {
/*  485 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_float(float paramFloat) throws TypeMismatch, InvalidValue {
/*  498 */     ServantObject servantObject = _servant_preinvoke("insert_float", _opsClass);
/*  499 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  502 */       dynUnionOperations.insert_float(paramFloat);
/*      */     } finally {
/*  504 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_double(double paramDouble) throws TypeMismatch, InvalidValue {
/*  517 */     ServantObject servantObject = _servant_preinvoke("insert_double", _opsClass);
/*  518 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  521 */       dynUnionOperations.insert_double(paramDouble);
/*      */     } finally {
/*  523 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_string(String paramString) throws TypeMismatch, InvalidValue {
/*  538 */     ServantObject servantObject = _servant_preinvoke("insert_string", _opsClass);
/*  539 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  542 */       dynUnionOperations.insert_string(paramString);
/*      */     } finally {
/*  544 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_reference(Object paramObject) throws TypeMismatch, InvalidValue {
/*  557 */     ServantObject servantObject = _servant_preinvoke("insert_reference", _opsClass);
/*  558 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  561 */       dynUnionOperations.insert_reference(paramObject);
/*      */     } finally {
/*  563 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_typecode(TypeCode paramTypeCode) throws TypeMismatch, InvalidValue {
/*  576 */     ServantObject servantObject = _servant_preinvoke("insert_typecode", _opsClass);
/*  577 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  580 */       dynUnionOperations.insert_typecode(paramTypeCode);
/*      */     } finally {
/*  582 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_longlong(long paramLong) throws TypeMismatch, InvalidValue {
/*  595 */     ServantObject servantObject = _servant_preinvoke("insert_longlong", _opsClass);
/*  596 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  599 */       dynUnionOperations.insert_longlong(paramLong);
/*      */     } finally {
/*  601 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_ulonglong(long paramLong) throws TypeMismatch, InvalidValue {
/*  615 */     ServantObject servantObject = _servant_preinvoke("insert_ulonglong", _opsClass);
/*  616 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  619 */       dynUnionOperations.insert_ulonglong(paramLong);
/*      */     } finally {
/*  621 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_wchar(char paramChar) throws TypeMismatch, InvalidValue {
/*  634 */     ServantObject servantObject = _servant_preinvoke("insert_wchar", _opsClass);
/*  635 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  638 */       dynUnionOperations.insert_wchar(paramChar);
/*      */     } finally {
/*  640 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_wstring(String paramString) throws TypeMismatch, InvalidValue {
/*  654 */     ServantObject servantObject = _servant_preinvoke("insert_wstring", _opsClass);
/*  655 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  658 */       dynUnionOperations.insert_wstring(paramString);
/*      */     } finally {
/*  660 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_any(Any paramAny) throws TypeMismatch, InvalidValue {
/*  673 */     ServantObject servantObject = _servant_preinvoke("insert_any", _opsClass);
/*  674 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  677 */       dynUnionOperations.insert_any(paramAny);
/*      */     } finally {
/*  679 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_dyn_any(DynAny paramDynAny) throws TypeMismatch, InvalidValue {
/*  692 */     ServantObject servantObject = _servant_preinvoke("insert_dyn_any", _opsClass);
/*  693 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  696 */       dynUnionOperations.insert_dyn_any(paramDynAny);
/*      */     } finally {
/*  698 */       _servant_postinvoke(servantObject);
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
/*      */   public void insert_val(Serializable paramSerializable) throws TypeMismatch, InvalidValue {
/*  712 */     ServantObject servantObject = _servant_preinvoke("insert_val", _opsClass);
/*  713 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  716 */       dynUnionOperations.insert_val(paramSerializable);
/*      */     } finally {
/*  718 */       _servant_postinvoke(servantObject);
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
/*      */   public boolean get_boolean() throws TypeMismatch, InvalidValue {
/*  733 */     ServantObject servantObject = _servant_preinvoke("get_boolean", _opsClass);
/*  734 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  737 */       return dynUnionOperations.get_boolean();
/*      */     } finally {
/*  739 */       _servant_postinvoke(servantObject);
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
/*      */   public byte get_octet() throws TypeMismatch, InvalidValue {
/*  754 */     ServantObject servantObject = _servant_preinvoke("get_octet", _opsClass);
/*  755 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  758 */       return dynUnionOperations.get_octet();
/*      */     } finally {
/*  760 */       _servant_postinvoke(servantObject);
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
/*      */   public char get_char() throws TypeMismatch, InvalidValue {
/*  775 */     ServantObject servantObject = _servant_preinvoke("get_char", _opsClass);
/*  776 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  779 */       return dynUnionOperations.get_char();
/*      */     } finally {
/*  781 */       _servant_postinvoke(servantObject);
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
/*      */   public short get_short() throws TypeMismatch, InvalidValue {
/*  796 */     ServantObject servantObject = _servant_preinvoke("get_short", _opsClass);
/*  797 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  800 */       return dynUnionOperations.get_short();
/*      */     } finally {
/*  802 */       _servant_postinvoke(servantObject);
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
/*      */   public short get_ushort() throws TypeMismatch, InvalidValue {
/*  817 */     ServantObject servantObject = _servant_preinvoke("get_ushort", _opsClass);
/*  818 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  821 */       return dynUnionOperations.get_ushort();
/*      */     } finally {
/*  823 */       _servant_postinvoke(servantObject);
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
/*      */   public int get_long() throws TypeMismatch, InvalidValue {
/*  838 */     ServantObject servantObject = _servant_preinvoke("get_long", _opsClass);
/*  839 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  842 */       return dynUnionOperations.get_long();
/*      */     } finally {
/*  844 */       _servant_postinvoke(servantObject);
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
/*      */   public int get_ulong() throws TypeMismatch, InvalidValue {
/*  859 */     ServantObject servantObject = _servant_preinvoke("get_ulong", _opsClass);
/*  860 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  863 */       return dynUnionOperations.get_ulong();
/*      */     } finally {
/*  865 */       _servant_postinvoke(servantObject);
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
/*      */   public float get_float() throws TypeMismatch, InvalidValue {
/*  880 */     ServantObject servantObject = _servant_preinvoke("get_float", _opsClass);
/*  881 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  884 */       return dynUnionOperations.get_float();
/*      */     } finally {
/*  886 */       _servant_postinvoke(servantObject);
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
/*      */   public double get_double() throws TypeMismatch, InvalidValue {
/*  901 */     ServantObject servantObject = _servant_preinvoke("get_double", _opsClass);
/*  902 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  905 */       return dynUnionOperations.get_double();
/*      */     } finally {
/*  907 */       _servant_postinvoke(servantObject);
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
/*      */   public String get_string() throws TypeMismatch, InvalidValue {
/*  923 */     ServantObject servantObject = _servant_preinvoke("get_string", _opsClass);
/*  924 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  927 */       return dynUnionOperations.get_string();
/*      */     } finally {
/*  929 */       _servant_postinvoke(servantObject);
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
/*      */   public Object get_reference() throws TypeMismatch, InvalidValue {
/*  944 */     ServantObject servantObject = _servant_preinvoke("get_reference", _opsClass);
/*  945 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  948 */       return dynUnionOperations.get_reference();
/*      */     } finally {
/*  950 */       _servant_postinvoke(servantObject);
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
/*      */   public TypeCode get_typecode() throws TypeMismatch, InvalidValue {
/*  965 */     ServantObject servantObject = _servant_preinvoke("get_typecode", _opsClass);
/*  966 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  969 */       return dynUnionOperations.get_typecode();
/*      */     } finally {
/*  971 */       _servant_postinvoke(servantObject);
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
/*      */   public long get_longlong() throws TypeMismatch, InvalidValue {
/*  986 */     ServantObject servantObject = _servant_preinvoke("get_longlong", _opsClass);
/*  987 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  990 */       return dynUnionOperations.get_longlong();
/*      */     } finally {
/*  992 */       _servant_postinvoke(servantObject);
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
/*      */   public long get_ulonglong() throws TypeMismatch, InvalidValue {
/* 1008 */     ServantObject servantObject = _servant_preinvoke("get_ulonglong", _opsClass);
/* 1009 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1012 */       return dynUnionOperations.get_ulonglong();
/*      */     } finally {
/* 1014 */       _servant_postinvoke(servantObject);
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
/*      */   public char get_wchar() throws TypeMismatch, InvalidValue {
/* 1029 */     ServantObject servantObject = _servant_preinvoke("get_wchar", _opsClass);
/* 1030 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1033 */       return dynUnionOperations.get_wchar();
/*      */     } finally {
/* 1035 */       _servant_postinvoke(servantObject);
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
/*      */   public String get_wstring() throws TypeMismatch, InvalidValue {
/* 1050 */     ServantObject servantObject = _servant_preinvoke("get_wstring", _opsClass);
/* 1051 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1054 */       return dynUnionOperations.get_wstring();
/*      */     } finally {
/* 1056 */       _servant_postinvoke(servantObject);
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
/*      */   public Any get_any() throws TypeMismatch, InvalidValue {
/* 1071 */     ServantObject servantObject = _servant_preinvoke("get_any", _opsClass);
/* 1072 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1075 */       return dynUnionOperations.get_any();
/*      */     } finally {
/* 1077 */       _servant_postinvoke(servantObject);
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
/*      */   public DynAny get_dyn_any() throws TypeMismatch, InvalidValue {
/* 1093 */     ServantObject servantObject = _servant_preinvoke("get_dyn_any", _opsClass);
/* 1094 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1097 */       return dynUnionOperations.get_dyn_any();
/*      */     } finally {
/* 1099 */       _servant_postinvoke(servantObject);
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
/*      */   public Serializable get_val() throws TypeMismatch, InvalidValue {
/* 1115 */     ServantObject servantObject = _servant_preinvoke("get_val", _opsClass);
/* 1116 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1119 */       return dynUnionOperations.get_val();
/*      */     } finally {
/* 1121 */       _servant_postinvoke(servantObject);
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
/*      */   public boolean seek(int paramInt) {
/* 1137 */     ServantObject servantObject = _servant_preinvoke("seek", _opsClass);
/* 1138 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1141 */       return dynUnionOperations.seek(paramInt);
/*      */     } finally {
/* 1143 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rewind() {
/* 1153 */     ServantObject servantObject = _servant_preinvoke("rewind", _opsClass);
/* 1154 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1157 */       dynUnionOperations.rewind();
/*      */     } finally {
/* 1159 */       _servant_postinvoke(servantObject);
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
/*      */   public boolean next() {
/* 1172 */     ServantObject servantObject = _servant_preinvoke("next", _opsClass);
/* 1173 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1176 */       return dynUnionOperations.next();
/*      */     } finally {
/* 1178 */       _servant_postinvoke(servantObject);
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
/*      */   public int component_count() {
/* 1200 */     ServantObject servantObject = _servant_preinvoke("component_count", _opsClass);
/* 1201 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1204 */       return dynUnionOperations.component_count();
/*      */     } finally {
/* 1206 */       _servant_postinvoke(servantObject);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public DynAny current_component() throws TypeMismatch {
/* 1231 */     ServantObject servantObject = _servant_preinvoke("current_component", _opsClass);
/* 1232 */     DynUnionOperations dynUnionOperations = (DynUnionOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1235 */       return dynUnionOperations.current_component();
/*      */     } finally {
/* 1237 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1242 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynUnion:1.0", "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _ids() {
/* 1248 */     return (String[])__ids.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 1253 */     String str = paramObjectInputStream.readUTF();
/* 1254 */     String[] arrayOfString = null;
/* 1255 */     Properties properties = null;
/* 1256 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1258 */       Object object = oRB.string_to_object(str);
/* 1259 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 1260 */       _set_delegate(delegate);
/*      */     } finally {
/* 1262 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1268 */     String[] arrayOfString = null;
/* 1269 */     Properties properties = null;
/* 1270 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1272 */       String str = oRB.object_to_string(this);
/* 1273 */       paramObjectOutputStream.writeUTF(str);
/*      */     } finally {
/* 1275 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynUnionStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */