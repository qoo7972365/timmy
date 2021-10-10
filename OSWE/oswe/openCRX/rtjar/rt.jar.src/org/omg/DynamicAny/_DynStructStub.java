/*      */ package org.omg.DynamicAny;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Properties;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.portable.Delegate;
/*      */ import org.omg.CORBA.portable.ObjectImpl;
/*      */ import org.omg.CORBA.portable.ServantObject;
/*      */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*      */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*      */ 
/*      */ public class _DynStructStub extends ObjectImpl implements DynStruct {
/*   18 */   public static final Class _opsClass = DynStructOperations.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String current_member_name() throws TypeMismatch, InvalidValue {
/*   32 */     ServantObject servantObject = _servant_preinvoke("current_member_name", _opsClass);
/*   33 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   36 */       return dynStructOperations.current_member_name();
/*      */     } finally {
/*   38 */       _servant_postinvoke(servantObject);
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
/*      */   public TCKind current_member_kind() throws TypeMismatch, InvalidValue {
/*   51 */     ServantObject servantObject = _servant_preinvoke("current_member_kind", _opsClass);
/*   52 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   55 */       return dynStructOperations.current_member_kind();
/*      */     } finally {
/*   57 */       _servant_postinvoke(servantObject);
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
/*      */   public NameValuePair[] get_members() {
/*   72 */     ServantObject servantObject = _servant_preinvoke("get_members", _opsClass);
/*   73 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   76 */       return dynStructOperations.get_members();
/*      */     } finally {
/*   78 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_members(NameValuePair[] paramArrayOfNameValuePair) throws TypeMismatch, InvalidValue {
/*   98 */     ServantObject servantObject = _servant_preinvoke("set_members", _opsClass);
/*   99 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  102 */       dynStructOperations.set_members(paramArrayOfNameValuePair);
/*      */     } finally {
/*  104 */       _servant_postinvoke(servantObject);
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
/*      */   public NameDynAnyPair[] get_members_as_dyn_any() {
/*  119 */     ServantObject servantObject = _servant_preinvoke("get_members_as_dyn_any", _opsClass);
/*  120 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  123 */       return dynStructOperations.get_members_as_dyn_any();
/*      */     } finally {
/*  125 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_members_as_dyn_any(NameDynAnyPair[] paramArrayOfNameDynAnyPair) throws TypeMismatch, InvalidValue {
/*  145 */     ServantObject servantObject = _servant_preinvoke("set_members_as_dyn_any", _opsClass);
/*  146 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  149 */       dynStructOperations.set_members_as_dyn_any(paramArrayOfNameDynAnyPair);
/*      */     } finally {
/*  151 */       _servant_postinvoke(servantObject);
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
/*  167 */     ServantObject servantObject = _servant_preinvoke("type", _opsClass);
/*  168 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  171 */       return dynStructOperations.type();
/*      */     } finally {
/*  173 */       _servant_postinvoke(servantObject);
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
/*  189 */     ServantObject servantObject = _servant_preinvoke("assign", _opsClass);
/*  190 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  193 */       dynStructOperations.assign(paramDynAny);
/*      */     } finally {
/*  195 */       _servant_postinvoke(servantObject);
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
/*  210 */     ServantObject servantObject = _servant_preinvoke("from_any", _opsClass);
/*  211 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  214 */       dynStructOperations.from_any(paramAny);
/*      */     } finally {
/*  216 */       _servant_postinvoke(servantObject);
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
/*  230 */     ServantObject servantObject = _servant_preinvoke("to_any", _opsClass);
/*  231 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  234 */       return dynStructOperations.to_any();
/*      */     } finally {
/*  236 */       _servant_postinvoke(servantObject);
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
/*  251 */     ServantObject servantObject = _servant_preinvoke("equal", _opsClass);
/*  252 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  255 */       return dynStructOperations.equal(paramDynAny);
/*      */     } finally {
/*  257 */       _servant_postinvoke(servantObject);
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
/*  278 */     ServantObject servantObject = _servant_preinvoke("destroy", _opsClass);
/*  279 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  282 */       dynStructOperations.destroy();
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
/*      */   public DynAny copy() {
/*  298 */     ServantObject servantObject = _servant_preinvoke("copy", _opsClass);
/*  299 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  302 */       return dynStructOperations.copy();
/*      */     } finally {
/*  304 */       _servant_postinvoke(servantObject);
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
/*  317 */     ServantObject servantObject = _servant_preinvoke("insert_boolean", _opsClass);
/*  318 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  321 */       dynStructOperations.insert_boolean(paramBoolean);
/*      */     } finally {
/*  323 */       _servant_postinvoke(servantObject);
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
/*  336 */     ServantObject servantObject = _servant_preinvoke("insert_octet", _opsClass);
/*  337 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  340 */       dynStructOperations.insert_octet(paramByte);
/*      */     } finally {
/*  342 */       _servant_postinvoke(servantObject);
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
/*  355 */     ServantObject servantObject = _servant_preinvoke("insert_char", _opsClass);
/*  356 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  359 */       dynStructOperations.insert_char(paramChar);
/*      */     } finally {
/*  361 */       _servant_postinvoke(servantObject);
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
/*  374 */     ServantObject servantObject = _servant_preinvoke("insert_short", _opsClass);
/*  375 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  378 */       dynStructOperations.insert_short(paramShort);
/*      */     } finally {
/*  380 */       _servant_postinvoke(servantObject);
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
/*  393 */     ServantObject servantObject = _servant_preinvoke("insert_ushort", _opsClass);
/*  394 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  397 */       dynStructOperations.insert_ushort(paramShort);
/*      */     } finally {
/*  399 */       _servant_postinvoke(servantObject);
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
/*  412 */     ServantObject servantObject = _servant_preinvoke("insert_long", _opsClass);
/*  413 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  416 */       dynStructOperations.insert_long(paramInt);
/*      */     } finally {
/*  418 */       _servant_postinvoke(servantObject);
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
/*  431 */     ServantObject servantObject = _servant_preinvoke("insert_ulong", _opsClass);
/*  432 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  435 */       dynStructOperations.insert_ulong(paramInt);
/*      */     } finally {
/*  437 */       _servant_postinvoke(servantObject);
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
/*  450 */     ServantObject servantObject = _servant_preinvoke("insert_float", _opsClass);
/*  451 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  454 */       dynStructOperations.insert_float(paramFloat);
/*      */     } finally {
/*  456 */       _servant_postinvoke(servantObject);
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
/*  469 */     ServantObject servantObject = _servant_preinvoke("insert_double", _opsClass);
/*  470 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  473 */       dynStructOperations.insert_double(paramDouble);
/*      */     } finally {
/*  475 */       _servant_postinvoke(servantObject);
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
/*  490 */     ServantObject servantObject = _servant_preinvoke("insert_string", _opsClass);
/*  491 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  494 */       dynStructOperations.insert_string(paramString);
/*      */     } finally {
/*  496 */       _servant_postinvoke(servantObject);
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
/*  509 */     ServantObject servantObject = _servant_preinvoke("insert_reference", _opsClass);
/*  510 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  513 */       dynStructOperations.insert_reference(paramObject);
/*      */     } finally {
/*  515 */       _servant_postinvoke(servantObject);
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
/*  528 */     ServantObject servantObject = _servant_preinvoke("insert_typecode", _opsClass);
/*  529 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  532 */       dynStructOperations.insert_typecode(paramTypeCode);
/*      */     } finally {
/*  534 */       _servant_postinvoke(servantObject);
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
/*  547 */     ServantObject servantObject = _servant_preinvoke("insert_longlong", _opsClass);
/*  548 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  551 */       dynStructOperations.insert_longlong(paramLong);
/*      */     } finally {
/*  553 */       _servant_postinvoke(servantObject);
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
/*  567 */     ServantObject servantObject = _servant_preinvoke("insert_ulonglong", _opsClass);
/*  568 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  571 */       dynStructOperations.insert_ulonglong(paramLong);
/*      */     } finally {
/*  573 */       _servant_postinvoke(servantObject);
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
/*  586 */     ServantObject servantObject = _servant_preinvoke("insert_wchar", _opsClass);
/*  587 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  590 */       dynStructOperations.insert_wchar(paramChar);
/*      */     } finally {
/*  592 */       _servant_postinvoke(servantObject);
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
/*  606 */     ServantObject servantObject = _servant_preinvoke("insert_wstring", _opsClass);
/*  607 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  610 */       dynStructOperations.insert_wstring(paramString);
/*      */     } finally {
/*  612 */       _servant_postinvoke(servantObject);
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
/*  625 */     ServantObject servantObject = _servant_preinvoke("insert_any", _opsClass);
/*  626 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  629 */       dynStructOperations.insert_any(paramAny);
/*      */     } finally {
/*  631 */       _servant_postinvoke(servantObject);
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
/*  644 */     ServantObject servantObject = _servant_preinvoke("insert_dyn_any", _opsClass);
/*  645 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  648 */       dynStructOperations.insert_dyn_any(paramDynAny);
/*      */     } finally {
/*  650 */       _servant_postinvoke(servantObject);
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
/*  664 */     ServantObject servantObject = _servant_preinvoke("insert_val", _opsClass);
/*  665 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  668 */       dynStructOperations.insert_val(paramSerializable);
/*      */     } finally {
/*  670 */       _servant_postinvoke(servantObject);
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
/*  685 */     ServantObject servantObject = _servant_preinvoke("get_boolean", _opsClass);
/*  686 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  689 */       return dynStructOperations.get_boolean();
/*      */     } finally {
/*  691 */       _servant_postinvoke(servantObject);
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
/*  706 */     ServantObject servantObject = _servant_preinvoke("get_octet", _opsClass);
/*  707 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  710 */       return dynStructOperations.get_octet();
/*      */     } finally {
/*  712 */       _servant_postinvoke(servantObject);
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
/*  727 */     ServantObject servantObject = _servant_preinvoke("get_char", _opsClass);
/*  728 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  731 */       return dynStructOperations.get_char();
/*      */     } finally {
/*  733 */       _servant_postinvoke(servantObject);
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
/*  748 */     ServantObject servantObject = _servant_preinvoke("get_short", _opsClass);
/*  749 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  752 */       return dynStructOperations.get_short();
/*      */     } finally {
/*  754 */       _servant_postinvoke(servantObject);
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
/*  769 */     ServantObject servantObject = _servant_preinvoke("get_ushort", _opsClass);
/*  770 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  773 */       return dynStructOperations.get_ushort();
/*      */     } finally {
/*  775 */       _servant_postinvoke(servantObject);
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
/*  790 */     ServantObject servantObject = _servant_preinvoke("get_long", _opsClass);
/*  791 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  794 */       return dynStructOperations.get_long();
/*      */     } finally {
/*  796 */       _servant_postinvoke(servantObject);
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
/*  811 */     ServantObject servantObject = _servant_preinvoke("get_ulong", _opsClass);
/*  812 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  815 */       return dynStructOperations.get_ulong();
/*      */     } finally {
/*  817 */       _servant_postinvoke(servantObject);
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
/*  832 */     ServantObject servantObject = _servant_preinvoke("get_float", _opsClass);
/*  833 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  836 */       return dynStructOperations.get_float();
/*      */     } finally {
/*  838 */       _servant_postinvoke(servantObject);
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
/*  853 */     ServantObject servantObject = _servant_preinvoke("get_double", _opsClass);
/*  854 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  857 */       return dynStructOperations.get_double();
/*      */     } finally {
/*  859 */       _servant_postinvoke(servantObject);
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
/*  875 */     ServantObject servantObject = _servant_preinvoke("get_string", _opsClass);
/*  876 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  879 */       return dynStructOperations.get_string();
/*      */     } finally {
/*  881 */       _servant_postinvoke(servantObject);
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
/*  896 */     ServantObject servantObject = _servant_preinvoke("get_reference", _opsClass);
/*  897 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  900 */       return dynStructOperations.get_reference();
/*      */     } finally {
/*  902 */       _servant_postinvoke(servantObject);
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
/*  917 */     ServantObject servantObject = _servant_preinvoke("get_typecode", _opsClass);
/*  918 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  921 */       return dynStructOperations.get_typecode();
/*      */     } finally {
/*  923 */       _servant_postinvoke(servantObject);
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
/*  938 */     ServantObject servantObject = _servant_preinvoke("get_longlong", _opsClass);
/*  939 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  942 */       return dynStructOperations.get_longlong();
/*      */     } finally {
/*  944 */       _servant_postinvoke(servantObject);
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
/*  960 */     ServantObject servantObject = _servant_preinvoke("get_ulonglong", _opsClass);
/*  961 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  964 */       return dynStructOperations.get_ulonglong();
/*      */     } finally {
/*  966 */       _servant_postinvoke(servantObject);
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
/*  981 */     ServantObject servantObject = _servant_preinvoke("get_wchar", _opsClass);
/*  982 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  985 */       return dynStructOperations.get_wchar();
/*      */     } finally {
/*  987 */       _servant_postinvoke(servantObject);
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
/* 1002 */     ServantObject servantObject = _servant_preinvoke("get_wstring", _opsClass);
/* 1003 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1006 */       return dynStructOperations.get_wstring();
/*      */     } finally {
/* 1008 */       _servant_postinvoke(servantObject);
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
/* 1023 */     ServantObject servantObject = _servant_preinvoke("get_any", _opsClass);
/* 1024 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1027 */       return dynStructOperations.get_any();
/*      */     } finally {
/* 1029 */       _servant_postinvoke(servantObject);
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
/* 1045 */     ServantObject servantObject = _servant_preinvoke("get_dyn_any", _opsClass);
/* 1046 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1049 */       return dynStructOperations.get_dyn_any();
/*      */     } finally {
/* 1051 */       _servant_postinvoke(servantObject);
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
/* 1067 */     ServantObject servantObject = _servant_preinvoke("get_val", _opsClass);
/* 1068 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1071 */       return dynStructOperations.get_val();
/*      */     } finally {
/* 1073 */       _servant_postinvoke(servantObject);
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
/* 1089 */     ServantObject servantObject = _servant_preinvoke("seek", _opsClass);
/* 1090 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1093 */       return dynStructOperations.seek(paramInt);
/*      */     } finally {
/* 1095 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rewind() {
/* 1105 */     ServantObject servantObject = _servant_preinvoke("rewind", _opsClass);
/* 1106 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1109 */       dynStructOperations.rewind();
/*      */     } finally {
/* 1111 */       _servant_postinvoke(servantObject);
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
/* 1124 */     ServantObject servantObject = _servant_preinvoke("next", _opsClass);
/* 1125 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1128 */       return dynStructOperations.next();
/*      */     } finally {
/* 1130 */       _servant_postinvoke(servantObject);
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
/* 1152 */     ServantObject servantObject = _servant_preinvoke("component_count", _opsClass);
/* 1153 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1156 */       return dynStructOperations.component_count();
/*      */     } finally {
/* 1158 */       _servant_postinvoke(servantObject);
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
/* 1183 */     ServantObject servantObject = _servant_preinvoke("current_component", _opsClass);
/* 1184 */     DynStructOperations dynStructOperations = (DynStructOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1187 */       return dynStructOperations.current_component();
/*      */     } finally {
/* 1189 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1194 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynStruct:1.0", "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _ids() {
/* 1200 */     return (String[])__ids.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 1205 */     String str = paramObjectInputStream.readUTF();
/* 1206 */     String[] arrayOfString = null;
/* 1207 */     Properties properties = null;
/* 1208 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1210 */       Object object = oRB.string_to_object(str);
/* 1211 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 1212 */       _set_delegate(delegate);
/*      */     } finally {
/* 1214 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1220 */     String[] arrayOfString = null;
/* 1221 */     Properties properties = null;
/* 1222 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1224 */       String str = oRB.object_to_string(this);
/* 1225 */       paramObjectOutputStream.writeUTF(str);
/*      */     } finally {
/* 1227 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynStructStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */