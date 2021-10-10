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
/*      */ 
/*      */ 
/*      */ public class _DynValueStub
/*      */   extends ObjectImpl
/*      */   implements DynValue
/*      */ {
/*   26 */   public static final Class _opsClass = DynValueOperations.class;
/*      */ 
/*      */ 
/*      */ 
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
/*   40 */     ServantObject servantObject = _servant_preinvoke("current_member_name", _opsClass);
/*   41 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   44 */       return dynValueOperations.current_member_name();
/*      */     } finally {
/*   46 */       _servant_postinvoke(servantObject);
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
/*   59 */     ServantObject servantObject = _servant_preinvoke("current_member_kind", _opsClass);
/*   60 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   63 */       return dynValueOperations.current_member_kind();
/*      */     } finally {
/*   65 */       _servant_postinvoke(servantObject);
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
/*      */   public NameValuePair[] get_members() throws InvalidValue {
/*   82 */     ServantObject servantObject = _servant_preinvoke("get_members", _opsClass);
/*   83 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   86 */       return dynValueOperations.get_members();
/*      */     } finally {
/*   88 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_members(NameValuePair[] paramArrayOfNameValuePair) throws TypeMismatch, InvalidValue {
/*  109 */     ServantObject servantObject = _servant_preinvoke("set_members", _opsClass);
/*  110 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  113 */       dynValueOperations.set_members(paramArrayOfNameValuePair);
/*      */     } finally {
/*  115 */       _servant_postinvoke(servantObject);
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
/*      */   public NameDynAnyPair[] get_members_as_dyn_any() throws InvalidValue {
/*  132 */     ServantObject servantObject = _servant_preinvoke("get_members_as_dyn_any", _opsClass);
/*  133 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  136 */       return dynValueOperations.get_members_as_dyn_any();
/*      */     } finally {
/*  138 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_members_as_dyn_any(NameDynAnyPair[] paramArrayOfNameDynAnyPair) throws TypeMismatch, InvalidValue {
/*  159 */     ServantObject servantObject = _servant_preinvoke("set_members_as_dyn_any", _opsClass);
/*  160 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  163 */       dynValueOperations.set_members_as_dyn_any(paramArrayOfNameDynAnyPair);
/*      */     } finally {
/*  165 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean is_null() {
/*  175 */     ServantObject servantObject = _servant_preinvoke("is_null", _opsClass);
/*  176 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  179 */       return dynValueOperations.is_null();
/*      */     } finally {
/*  181 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set_to_null() {
/*  191 */     ServantObject servantObject = _servant_preinvoke("set_to_null", _opsClass);
/*  192 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  195 */       dynValueOperations.set_to_null();
/*      */     } finally {
/*  197 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_to_value() {
/*  209 */     ServantObject servantObject = _servant_preinvoke("set_to_value", _opsClass);
/*  210 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  213 */       dynValueOperations.set_to_value();
/*      */     } finally {
/*  215 */       _servant_postinvoke(servantObject);
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
/*  231 */     ServantObject servantObject = _servant_preinvoke("type", _opsClass);
/*  232 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  235 */       return dynValueOperations.type();
/*      */     } finally {
/*  237 */       _servant_postinvoke(servantObject);
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
/*  253 */     ServantObject servantObject = _servant_preinvoke("assign", _opsClass);
/*  254 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  257 */       dynValueOperations.assign(paramDynAny);
/*      */     } finally {
/*  259 */       _servant_postinvoke(servantObject);
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
/*  274 */     ServantObject servantObject = _servant_preinvoke("from_any", _opsClass);
/*  275 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  278 */       dynValueOperations.from_any(paramAny);
/*      */     } finally {
/*  280 */       _servant_postinvoke(servantObject);
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
/*  294 */     ServantObject servantObject = _servant_preinvoke("to_any", _opsClass);
/*  295 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  298 */       return dynValueOperations.to_any();
/*      */     } finally {
/*  300 */       _servant_postinvoke(servantObject);
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
/*  315 */     ServantObject servantObject = _servant_preinvoke("equal", _opsClass);
/*  316 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  319 */       return dynValueOperations.equal(paramDynAny);
/*      */     } finally {
/*  321 */       _servant_postinvoke(servantObject);
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
/*  342 */     ServantObject servantObject = _servant_preinvoke("destroy", _opsClass);
/*  343 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  346 */       dynValueOperations.destroy();
/*      */     } finally {
/*  348 */       _servant_postinvoke(servantObject);
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
/*  362 */     ServantObject servantObject = _servant_preinvoke("copy", _opsClass);
/*  363 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  366 */       return dynValueOperations.copy();
/*      */     } finally {
/*  368 */       _servant_postinvoke(servantObject);
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
/*  381 */     ServantObject servantObject = _servant_preinvoke("insert_boolean", _opsClass);
/*  382 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  385 */       dynValueOperations.insert_boolean(paramBoolean);
/*      */     } finally {
/*  387 */       _servant_postinvoke(servantObject);
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
/*  400 */     ServantObject servantObject = _servant_preinvoke("insert_octet", _opsClass);
/*  401 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  404 */       dynValueOperations.insert_octet(paramByte);
/*      */     } finally {
/*  406 */       _servant_postinvoke(servantObject);
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
/*  419 */     ServantObject servantObject = _servant_preinvoke("insert_char", _opsClass);
/*  420 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  423 */       dynValueOperations.insert_char(paramChar);
/*      */     } finally {
/*  425 */       _servant_postinvoke(servantObject);
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
/*  438 */     ServantObject servantObject = _servant_preinvoke("insert_short", _opsClass);
/*  439 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  442 */       dynValueOperations.insert_short(paramShort);
/*      */     } finally {
/*  444 */       _servant_postinvoke(servantObject);
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
/*  457 */     ServantObject servantObject = _servant_preinvoke("insert_ushort", _opsClass);
/*  458 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  461 */       dynValueOperations.insert_ushort(paramShort);
/*      */     } finally {
/*  463 */       _servant_postinvoke(servantObject);
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
/*  476 */     ServantObject servantObject = _servant_preinvoke("insert_long", _opsClass);
/*  477 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  480 */       dynValueOperations.insert_long(paramInt);
/*      */     } finally {
/*  482 */       _servant_postinvoke(servantObject);
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
/*  495 */     ServantObject servantObject = _servant_preinvoke("insert_ulong", _opsClass);
/*  496 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  499 */       dynValueOperations.insert_ulong(paramInt);
/*      */     } finally {
/*  501 */       _servant_postinvoke(servantObject);
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
/*  514 */     ServantObject servantObject = _servant_preinvoke("insert_float", _opsClass);
/*  515 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  518 */       dynValueOperations.insert_float(paramFloat);
/*      */     } finally {
/*  520 */       _servant_postinvoke(servantObject);
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
/*  533 */     ServantObject servantObject = _servant_preinvoke("insert_double", _opsClass);
/*  534 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  537 */       dynValueOperations.insert_double(paramDouble);
/*      */     } finally {
/*  539 */       _servant_postinvoke(servantObject);
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
/*  554 */     ServantObject servantObject = _servant_preinvoke("insert_string", _opsClass);
/*  555 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  558 */       dynValueOperations.insert_string(paramString);
/*      */     } finally {
/*  560 */       _servant_postinvoke(servantObject);
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
/*  573 */     ServantObject servantObject = _servant_preinvoke("insert_reference", _opsClass);
/*  574 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  577 */       dynValueOperations.insert_reference(paramObject);
/*      */     } finally {
/*  579 */       _servant_postinvoke(servantObject);
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
/*  592 */     ServantObject servantObject = _servant_preinvoke("insert_typecode", _opsClass);
/*  593 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  596 */       dynValueOperations.insert_typecode(paramTypeCode);
/*      */     } finally {
/*  598 */       _servant_postinvoke(servantObject);
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
/*  611 */     ServantObject servantObject = _servant_preinvoke("insert_longlong", _opsClass);
/*  612 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  615 */       dynValueOperations.insert_longlong(paramLong);
/*      */     } finally {
/*  617 */       _servant_postinvoke(servantObject);
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
/*  631 */     ServantObject servantObject = _servant_preinvoke("insert_ulonglong", _opsClass);
/*  632 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  635 */       dynValueOperations.insert_ulonglong(paramLong);
/*      */     } finally {
/*  637 */       _servant_postinvoke(servantObject);
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
/*  650 */     ServantObject servantObject = _servant_preinvoke("insert_wchar", _opsClass);
/*  651 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  654 */       dynValueOperations.insert_wchar(paramChar);
/*      */     } finally {
/*  656 */       _servant_postinvoke(servantObject);
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
/*  670 */     ServantObject servantObject = _servant_preinvoke("insert_wstring", _opsClass);
/*  671 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  674 */       dynValueOperations.insert_wstring(paramString);
/*      */     } finally {
/*  676 */       _servant_postinvoke(servantObject);
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
/*  689 */     ServantObject servantObject = _servant_preinvoke("insert_any", _opsClass);
/*  690 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  693 */       dynValueOperations.insert_any(paramAny);
/*      */     } finally {
/*  695 */       _servant_postinvoke(servantObject);
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
/*  708 */     ServantObject servantObject = _servant_preinvoke("insert_dyn_any", _opsClass);
/*  709 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  712 */       dynValueOperations.insert_dyn_any(paramDynAny);
/*      */     } finally {
/*  714 */       _servant_postinvoke(servantObject);
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
/*  728 */     ServantObject servantObject = _servant_preinvoke("insert_val", _opsClass);
/*  729 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  732 */       dynValueOperations.insert_val(paramSerializable);
/*      */     } finally {
/*  734 */       _servant_postinvoke(servantObject);
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
/*  749 */     ServantObject servantObject = _servant_preinvoke("get_boolean", _opsClass);
/*  750 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  753 */       return dynValueOperations.get_boolean();
/*      */     } finally {
/*  755 */       _servant_postinvoke(servantObject);
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
/*  770 */     ServantObject servantObject = _servant_preinvoke("get_octet", _opsClass);
/*  771 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  774 */       return dynValueOperations.get_octet();
/*      */     } finally {
/*  776 */       _servant_postinvoke(servantObject);
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
/*  791 */     ServantObject servantObject = _servant_preinvoke("get_char", _opsClass);
/*  792 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  795 */       return dynValueOperations.get_char();
/*      */     } finally {
/*  797 */       _servant_postinvoke(servantObject);
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
/*  812 */     ServantObject servantObject = _servant_preinvoke("get_short", _opsClass);
/*  813 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  816 */       return dynValueOperations.get_short();
/*      */     } finally {
/*  818 */       _servant_postinvoke(servantObject);
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
/*  833 */     ServantObject servantObject = _servant_preinvoke("get_ushort", _opsClass);
/*  834 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  837 */       return dynValueOperations.get_ushort();
/*      */     } finally {
/*  839 */       _servant_postinvoke(servantObject);
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
/*  854 */     ServantObject servantObject = _servant_preinvoke("get_long", _opsClass);
/*  855 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  858 */       return dynValueOperations.get_long();
/*      */     } finally {
/*  860 */       _servant_postinvoke(servantObject);
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
/*  875 */     ServantObject servantObject = _servant_preinvoke("get_ulong", _opsClass);
/*  876 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  879 */       return dynValueOperations.get_ulong();
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
/*      */   public float get_float() throws TypeMismatch, InvalidValue {
/*  896 */     ServantObject servantObject = _servant_preinvoke("get_float", _opsClass);
/*  897 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  900 */       return dynValueOperations.get_float();
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
/*      */   public double get_double() throws TypeMismatch, InvalidValue {
/*  917 */     ServantObject servantObject = _servant_preinvoke("get_double", _opsClass);
/*  918 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  921 */       return dynValueOperations.get_double();
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
/*      */   
/*      */   public String get_string() throws TypeMismatch, InvalidValue {
/*  939 */     ServantObject servantObject = _servant_preinvoke("get_string", _opsClass);
/*  940 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  943 */       return dynValueOperations.get_string();
/*      */     } finally {
/*  945 */       _servant_postinvoke(servantObject);
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
/*  960 */     ServantObject servantObject = _servant_preinvoke("get_reference", _opsClass);
/*  961 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  964 */       return dynValueOperations.get_reference();
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
/*      */   public TypeCode get_typecode() throws TypeMismatch, InvalidValue {
/*  981 */     ServantObject servantObject = _servant_preinvoke("get_typecode", _opsClass);
/*  982 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  985 */       return dynValueOperations.get_typecode();
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
/*      */   public long get_longlong() throws TypeMismatch, InvalidValue {
/* 1002 */     ServantObject servantObject = _servant_preinvoke("get_longlong", _opsClass);
/* 1003 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1006 */       return dynValueOperations.get_longlong();
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
/*      */   
/*      */   public long get_ulonglong() throws TypeMismatch, InvalidValue {
/* 1024 */     ServantObject servantObject = _servant_preinvoke("get_ulonglong", _opsClass);
/* 1025 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1028 */       return dynValueOperations.get_ulonglong();
/*      */     } finally {
/* 1030 */       _servant_postinvoke(servantObject);
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
/* 1045 */     ServantObject servantObject = _servant_preinvoke("get_wchar", _opsClass);
/* 1046 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1049 */       return dynValueOperations.get_wchar();
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
/*      */   public String get_wstring() throws TypeMismatch, InvalidValue {
/* 1066 */     ServantObject servantObject = _servant_preinvoke("get_wstring", _opsClass);
/* 1067 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1070 */       return dynValueOperations.get_wstring();
/*      */     } finally {
/* 1072 */       _servant_postinvoke(servantObject);
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
/* 1087 */     ServantObject servantObject = _servant_preinvoke("get_any", _opsClass);
/* 1088 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1091 */       return dynValueOperations.get_any();
/*      */     } finally {
/* 1093 */       _servant_postinvoke(servantObject);
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
/* 1109 */     ServantObject servantObject = _servant_preinvoke("get_dyn_any", _opsClass);
/* 1110 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1113 */       return dynValueOperations.get_dyn_any();
/*      */     } finally {
/* 1115 */       _servant_postinvoke(servantObject);
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
/* 1131 */     ServantObject servantObject = _servant_preinvoke("get_val", _opsClass);
/* 1132 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1135 */       return dynValueOperations.get_val();
/*      */     } finally {
/* 1137 */       _servant_postinvoke(servantObject);
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
/* 1153 */     ServantObject servantObject = _servant_preinvoke("seek", _opsClass);
/* 1154 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1157 */       return dynValueOperations.seek(paramInt);
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
/*      */   public void rewind() {
/* 1169 */     ServantObject servantObject = _servant_preinvoke("rewind", _opsClass);
/* 1170 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1173 */       dynValueOperations.rewind();
/*      */     } finally {
/* 1175 */       _servant_postinvoke(servantObject);
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
/* 1188 */     ServantObject servantObject = _servant_preinvoke("next", _opsClass);
/* 1189 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1192 */       return dynValueOperations.next();
/*      */     } finally {
/* 1194 */       _servant_postinvoke(servantObject);
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
/* 1216 */     ServantObject servantObject = _servant_preinvoke("component_count", _opsClass);
/* 1217 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1220 */       return dynValueOperations.component_count();
/*      */     } finally {
/* 1222 */       _servant_postinvoke(servantObject);
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
/* 1247 */     ServantObject servantObject = _servant_preinvoke("current_component", _opsClass);
/* 1248 */     DynValueOperations dynValueOperations = (DynValueOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1251 */       return dynValueOperations.current_component();
/*      */     } finally {
/* 1253 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1258 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynValue:1.0", "IDL:omg.org/DynamicAny/DynValueCommon:1.0", "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _ids() {
/* 1265 */     return (String[])__ids.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 1270 */     String str = paramObjectInputStream.readUTF();
/* 1271 */     String[] arrayOfString = null;
/* 1272 */     Properties properties = null;
/* 1273 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1275 */       Object object = oRB.string_to_object(str);
/* 1276 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 1277 */       _set_delegate(delegate);
/*      */     } finally {
/* 1279 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1285 */     String[] arrayOfString = null;
/* 1286 */     Properties properties = null;
/* 1287 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1289 */       String str = oRB.object_to_string(this);
/* 1290 */       paramObjectOutputStream.writeUTF(str);
/*      */     } finally {
/* 1292 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynValueStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */