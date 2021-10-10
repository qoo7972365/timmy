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
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.portable.Delegate;
/*      */ import org.omg.CORBA.portable.ObjectImpl;
/*      */ import org.omg.CORBA.portable.ServantObject;
/*      */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*      */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*      */ 
/*      */ public class _DynArrayStub extends ObjectImpl implements DynArray {
/*   19 */   public static final Class _opsClass = DynArrayOperations.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Any[] get_elements() {
/*   28 */     ServantObject servantObject = _servant_preinvoke("get_elements", _opsClass);
/*   29 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   32 */       return dynArrayOperations.get_elements();
/*      */     } finally {
/*   34 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_elements(Any[] paramArrayOfAny) throws TypeMismatch, InvalidValue {
/*   47 */     ServantObject servantObject = _servant_preinvoke("set_elements", _opsClass);
/*   48 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   51 */       dynArrayOperations.set_elements(paramArrayOfAny);
/*      */     } finally {
/*   53 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DynAny[] get_elements_as_dyn_any() {
/*   63 */     ServantObject servantObject = _servant_preinvoke("get_elements_as_dyn_any", _opsClass);
/*   64 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   67 */       return dynArrayOperations.get_elements_as_dyn_any();
/*      */     } finally {
/*   69 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_elements_as_dyn_any(DynAny[] paramArrayOfDynAny) throws TypeMismatch, InvalidValue {
/*   82 */     ServantObject servantObject = _servant_preinvoke("set_elements_as_dyn_any", _opsClass);
/*   83 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   86 */       dynArrayOperations.set_elements_as_dyn_any(paramArrayOfDynAny);
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
/*      */   public TypeCode type() {
/*  104 */     ServantObject servantObject = _servant_preinvoke("type", _opsClass);
/*  105 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  108 */       return dynArrayOperations.type();
/*      */     } finally {
/*  110 */       _servant_postinvoke(servantObject);
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
/*  126 */     ServantObject servantObject = _servant_preinvoke("assign", _opsClass);
/*  127 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  130 */       dynArrayOperations.assign(paramDynAny);
/*      */     } finally {
/*  132 */       _servant_postinvoke(servantObject);
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
/*  147 */     ServantObject servantObject = _servant_preinvoke("from_any", _opsClass);
/*  148 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  151 */       dynArrayOperations.from_any(paramAny);
/*      */     } finally {
/*  153 */       _servant_postinvoke(servantObject);
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
/*  167 */     ServantObject servantObject = _servant_preinvoke("to_any", _opsClass);
/*  168 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  171 */       return dynArrayOperations.to_any();
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
/*      */   public boolean equal(DynAny paramDynAny) {
/*  188 */     ServantObject servantObject = _servant_preinvoke("equal", _opsClass);
/*  189 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  192 */       return dynArrayOperations.equal(paramDynAny);
/*      */     } finally {
/*  194 */       _servant_postinvoke(servantObject);
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
/*  215 */     ServantObject servantObject = _servant_preinvoke("destroy", _opsClass);
/*  216 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  219 */       dynArrayOperations.destroy();
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
/*      */   public DynAny copy() {
/*  235 */     ServantObject servantObject = _servant_preinvoke("copy", _opsClass);
/*  236 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  239 */       return dynArrayOperations.copy();
/*      */     } finally {
/*  241 */       _servant_postinvoke(servantObject);
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
/*  254 */     ServantObject servantObject = _servant_preinvoke("insert_boolean", _opsClass);
/*  255 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  258 */       dynArrayOperations.insert_boolean(paramBoolean);
/*      */     } finally {
/*  260 */       _servant_postinvoke(servantObject);
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
/*  273 */     ServantObject servantObject = _servant_preinvoke("insert_octet", _opsClass);
/*  274 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  277 */       dynArrayOperations.insert_octet(paramByte);
/*      */     } finally {
/*  279 */       _servant_postinvoke(servantObject);
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
/*  292 */     ServantObject servantObject = _servant_preinvoke("insert_char", _opsClass);
/*  293 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  296 */       dynArrayOperations.insert_char(paramChar);
/*      */     } finally {
/*  298 */       _servant_postinvoke(servantObject);
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
/*  311 */     ServantObject servantObject = _servant_preinvoke("insert_short", _opsClass);
/*  312 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  315 */       dynArrayOperations.insert_short(paramShort);
/*      */     } finally {
/*  317 */       _servant_postinvoke(servantObject);
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
/*  330 */     ServantObject servantObject = _servant_preinvoke("insert_ushort", _opsClass);
/*  331 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  334 */       dynArrayOperations.insert_ushort(paramShort);
/*      */     } finally {
/*  336 */       _servant_postinvoke(servantObject);
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
/*  349 */     ServantObject servantObject = _servant_preinvoke("insert_long", _opsClass);
/*  350 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  353 */       dynArrayOperations.insert_long(paramInt);
/*      */     } finally {
/*  355 */       _servant_postinvoke(servantObject);
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
/*  368 */     ServantObject servantObject = _servant_preinvoke("insert_ulong", _opsClass);
/*  369 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  372 */       dynArrayOperations.insert_ulong(paramInt);
/*      */     } finally {
/*  374 */       _servant_postinvoke(servantObject);
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
/*  387 */     ServantObject servantObject = _servant_preinvoke("insert_float", _opsClass);
/*  388 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  391 */       dynArrayOperations.insert_float(paramFloat);
/*      */     } finally {
/*  393 */       _servant_postinvoke(servantObject);
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
/*  406 */     ServantObject servantObject = _servant_preinvoke("insert_double", _opsClass);
/*  407 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  410 */       dynArrayOperations.insert_double(paramDouble);
/*      */     } finally {
/*  412 */       _servant_postinvoke(servantObject);
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
/*  427 */     ServantObject servantObject = _servant_preinvoke("insert_string", _opsClass);
/*  428 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  431 */       dynArrayOperations.insert_string(paramString);
/*      */     } finally {
/*  433 */       _servant_postinvoke(servantObject);
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
/*  446 */     ServantObject servantObject = _servant_preinvoke("insert_reference", _opsClass);
/*  447 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  450 */       dynArrayOperations.insert_reference(paramObject);
/*      */     } finally {
/*  452 */       _servant_postinvoke(servantObject);
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
/*  465 */     ServantObject servantObject = _servant_preinvoke("insert_typecode", _opsClass);
/*  466 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  469 */       dynArrayOperations.insert_typecode(paramTypeCode);
/*      */     } finally {
/*  471 */       _servant_postinvoke(servantObject);
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
/*  484 */     ServantObject servantObject = _servant_preinvoke("insert_longlong", _opsClass);
/*  485 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  488 */       dynArrayOperations.insert_longlong(paramLong);
/*      */     } finally {
/*  490 */       _servant_postinvoke(servantObject);
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
/*  504 */     ServantObject servantObject = _servant_preinvoke("insert_ulonglong", _opsClass);
/*  505 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  508 */       dynArrayOperations.insert_ulonglong(paramLong);
/*      */     } finally {
/*  510 */       _servant_postinvoke(servantObject);
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
/*  523 */     ServantObject servantObject = _servant_preinvoke("insert_wchar", _opsClass);
/*  524 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  527 */       dynArrayOperations.insert_wchar(paramChar);
/*      */     } finally {
/*  529 */       _servant_postinvoke(servantObject);
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
/*  543 */     ServantObject servantObject = _servant_preinvoke("insert_wstring", _opsClass);
/*  544 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  547 */       dynArrayOperations.insert_wstring(paramString);
/*      */     } finally {
/*  549 */       _servant_postinvoke(servantObject);
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
/*  562 */     ServantObject servantObject = _servant_preinvoke("insert_any", _opsClass);
/*  563 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  566 */       dynArrayOperations.insert_any(paramAny);
/*      */     } finally {
/*  568 */       _servant_postinvoke(servantObject);
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
/*  581 */     ServantObject servantObject = _servant_preinvoke("insert_dyn_any", _opsClass);
/*  582 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  585 */       dynArrayOperations.insert_dyn_any(paramDynAny);
/*      */     } finally {
/*  587 */       _servant_postinvoke(servantObject);
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
/*  601 */     ServantObject servantObject = _servant_preinvoke("insert_val", _opsClass);
/*  602 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  605 */       dynArrayOperations.insert_val(paramSerializable);
/*      */     } finally {
/*  607 */       _servant_postinvoke(servantObject);
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
/*  622 */     ServantObject servantObject = _servant_preinvoke("get_boolean", _opsClass);
/*  623 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  626 */       return dynArrayOperations.get_boolean();
/*      */     } finally {
/*  628 */       _servant_postinvoke(servantObject);
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
/*  643 */     ServantObject servantObject = _servant_preinvoke("get_octet", _opsClass);
/*  644 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  647 */       return dynArrayOperations.get_octet();
/*      */     } finally {
/*  649 */       _servant_postinvoke(servantObject);
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
/*  664 */     ServantObject servantObject = _servant_preinvoke("get_char", _opsClass);
/*  665 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  668 */       return dynArrayOperations.get_char();
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
/*      */   public short get_short() throws TypeMismatch, InvalidValue {
/*  685 */     ServantObject servantObject = _servant_preinvoke("get_short", _opsClass);
/*  686 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  689 */       return dynArrayOperations.get_short();
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
/*      */   public short get_ushort() throws TypeMismatch, InvalidValue {
/*  706 */     ServantObject servantObject = _servant_preinvoke("get_ushort", _opsClass);
/*  707 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  710 */       return dynArrayOperations.get_ushort();
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
/*      */   public int get_long() throws TypeMismatch, InvalidValue {
/*  727 */     ServantObject servantObject = _servant_preinvoke("get_long", _opsClass);
/*  728 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  731 */       return dynArrayOperations.get_long();
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
/*      */   public int get_ulong() throws TypeMismatch, InvalidValue {
/*  748 */     ServantObject servantObject = _servant_preinvoke("get_ulong", _opsClass);
/*  749 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  752 */       return dynArrayOperations.get_ulong();
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
/*      */   public float get_float() throws TypeMismatch, InvalidValue {
/*  769 */     ServantObject servantObject = _servant_preinvoke("get_float", _opsClass);
/*  770 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  773 */       return dynArrayOperations.get_float();
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
/*      */   public double get_double() throws TypeMismatch, InvalidValue {
/*  790 */     ServantObject servantObject = _servant_preinvoke("get_double", _opsClass);
/*  791 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  794 */       return dynArrayOperations.get_double();
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
/*      */   
/*      */   public String get_string() throws TypeMismatch, InvalidValue {
/*  812 */     ServantObject servantObject = _servant_preinvoke("get_string", _opsClass);
/*  813 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  816 */       return dynArrayOperations.get_string();
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
/*      */   public Object get_reference() throws TypeMismatch, InvalidValue {
/*  833 */     ServantObject servantObject = _servant_preinvoke("get_reference", _opsClass);
/*  834 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  837 */       return dynArrayOperations.get_reference();
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
/*      */   public TypeCode get_typecode() throws TypeMismatch, InvalidValue {
/*  854 */     ServantObject servantObject = _servant_preinvoke("get_typecode", _opsClass);
/*  855 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  858 */       return dynArrayOperations.get_typecode();
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
/*      */   public long get_longlong() throws TypeMismatch, InvalidValue {
/*  875 */     ServantObject servantObject = _servant_preinvoke("get_longlong", _opsClass);
/*  876 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  879 */       return dynArrayOperations.get_longlong();
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
/*      */   
/*      */   public long get_ulonglong() throws TypeMismatch, InvalidValue {
/*  897 */     ServantObject servantObject = _servant_preinvoke("get_ulonglong", _opsClass);
/*  898 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  901 */       return dynArrayOperations.get_ulonglong();
/*      */     } finally {
/*  903 */       _servant_postinvoke(servantObject);
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
/*  918 */     ServantObject servantObject = _servant_preinvoke("get_wchar", _opsClass);
/*  919 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  922 */       return dynArrayOperations.get_wchar();
/*      */     } finally {
/*  924 */       _servant_postinvoke(servantObject);
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
/*  939 */     ServantObject servantObject = _servant_preinvoke("get_wstring", _opsClass);
/*  940 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  943 */       return dynArrayOperations.get_wstring();
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
/*      */   public Any get_any() throws TypeMismatch, InvalidValue {
/*  960 */     ServantObject servantObject = _servant_preinvoke("get_any", _opsClass);
/*  961 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  964 */       return dynArrayOperations.get_any();
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
/*      */   
/*      */   public DynAny get_dyn_any() throws TypeMismatch, InvalidValue {
/*  982 */     ServantObject servantObject = _servant_preinvoke("get_dyn_any", _opsClass);
/*  983 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  986 */       return dynArrayOperations.get_dyn_any();
/*      */     } finally {
/*  988 */       _servant_postinvoke(servantObject);
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
/* 1004 */     ServantObject servantObject = _servant_preinvoke("get_val", _opsClass);
/* 1005 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1008 */       return dynArrayOperations.get_val();
/*      */     } finally {
/* 1010 */       _servant_postinvoke(servantObject);
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
/* 1026 */     ServantObject servantObject = _servant_preinvoke("seek", _opsClass);
/* 1027 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1030 */       return dynArrayOperations.seek(paramInt);
/*      */     } finally {
/* 1032 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rewind() {
/* 1042 */     ServantObject servantObject = _servant_preinvoke("rewind", _opsClass);
/* 1043 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1046 */       dynArrayOperations.rewind();
/*      */     } finally {
/* 1048 */       _servant_postinvoke(servantObject);
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
/* 1061 */     ServantObject servantObject = _servant_preinvoke("next", _opsClass);
/* 1062 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1065 */       return dynArrayOperations.next();
/*      */     } finally {
/* 1067 */       _servant_postinvoke(servantObject);
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
/* 1089 */     ServantObject servantObject = _servant_preinvoke("component_count", _opsClass);
/* 1090 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1093 */       return dynArrayOperations.component_count();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1120 */     ServantObject servantObject = _servant_preinvoke("current_component", _opsClass);
/* 1121 */     DynArrayOperations dynArrayOperations = (DynArrayOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1124 */       return dynArrayOperations.current_component();
/*      */     } finally {
/* 1126 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1131 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynArray:1.0", "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _ids() {
/* 1137 */     return (String[])__ids.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 1142 */     String str = paramObjectInputStream.readUTF();
/* 1143 */     String[] arrayOfString = null;
/* 1144 */     Properties properties = null;
/* 1145 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1147 */       Object object = oRB.string_to_object(str);
/* 1148 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 1149 */       _set_delegate(delegate);
/*      */     } finally {
/* 1151 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1157 */     String[] arrayOfString = null;
/* 1158 */     Properties properties = null;
/* 1159 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1161 */       String str = oRB.object_to_string(this);
/* 1162 */       paramObjectOutputStream.writeUTF(str);
/*      */     } finally {
/* 1164 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynArrayStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */