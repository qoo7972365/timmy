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
/*      */ public class _DynEnumStub extends ObjectImpl implements DynEnum {
/*   18 */   public static final Class _opsClass = DynEnumOperations.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String get_as_string() {
/*   27 */     ServantObject servantObject = _servant_preinvoke("get_as_string", _opsClass);
/*   28 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   31 */       return dynEnumOperations.get_as_string();
/*      */     } finally {
/*   33 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_as_string(String paramString) throws InvalidValue {
/*   46 */     ServantObject servantObject = _servant_preinvoke("set_as_string", _opsClass);
/*   47 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   50 */       dynEnumOperations.set_as_string(paramString);
/*      */     } finally {
/*   52 */       _servant_postinvoke(servantObject);
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
/*      */   public int get_as_ulong() {
/*   64 */     ServantObject servantObject = _servant_preinvoke("get_as_ulong", _opsClass);
/*   65 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   68 */       return dynEnumOperations.get_as_ulong();
/*      */     } finally {
/*   70 */       _servant_postinvoke(servantObject);
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
/*      */   public void set_as_ulong(int paramInt) throws InvalidValue {
/*   83 */     ServantObject servantObject = _servant_preinvoke("set_as_ulong", _opsClass);
/*   84 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   87 */       dynEnumOperations.set_as_ulong(paramInt);
/*      */     } finally {
/*   89 */       _servant_postinvoke(servantObject);
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
/*  105 */     ServantObject servantObject = _servant_preinvoke("type", _opsClass);
/*  106 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  109 */       return dynEnumOperations.type();
/*      */     } finally {
/*  111 */       _servant_postinvoke(servantObject);
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
/*  127 */     ServantObject servantObject = _servant_preinvoke("assign", _opsClass);
/*  128 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  131 */       dynEnumOperations.assign(paramDynAny);
/*      */     } finally {
/*  133 */       _servant_postinvoke(servantObject);
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
/*  148 */     ServantObject servantObject = _servant_preinvoke("from_any", _opsClass);
/*  149 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  152 */       dynEnumOperations.from_any(paramAny);
/*      */     } finally {
/*  154 */       _servant_postinvoke(servantObject);
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
/*  168 */     ServantObject servantObject = _servant_preinvoke("to_any", _opsClass);
/*  169 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  172 */       return dynEnumOperations.to_any();
/*      */     } finally {
/*  174 */       _servant_postinvoke(servantObject);
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
/*  189 */     ServantObject servantObject = _servant_preinvoke("equal", _opsClass);
/*  190 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  193 */       return dynEnumOperations.equal(paramDynAny);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy() {
/*  216 */     ServantObject servantObject = _servant_preinvoke("destroy", _opsClass);
/*  217 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  220 */       dynEnumOperations.destroy();
/*      */     } finally {
/*  222 */       _servant_postinvoke(servantObject);
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
/*  236 */     ServantObject servantObject = _servant_preinvoke("copy", _opsClass);
/*  237 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  240 */       return dynEnumOperations.copy();
/*      */     } finally {
/*  242 */       _servant_postinvoke(servantObject);
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
/*  255 */     ServantObject servantObject = _servant_preinvoke("insert_boolean", _opsClass);
/*  256 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  259 */       dynEnumOperations.insert_boolean(paramBoolean);
/*      */     } finally {
/*  261 */       _servant_postinvoke(servantObject);
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
/*  274 */     ServantObject servantObject = _servant_preinvoke("insert_octet", _opsClass);
/*  275 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  278 */       dynEnumOperations.insert_octet(paramByte);
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
/*      */   public void insert_char(char paramChar) throws TypeMismatch, InvalidValue {
/*  293 */     ServantObject servantObject = _servant_preinvoke("insert_char", _opsClass);
/*  294 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  297 */       dynEnumOperations.insert_char(paramChar);
/*      */     } finally {
/*  299 */       _servant_postinvoke(servantObject);
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
/*  312 */     ServantObject servantObject = _servant_preinvoke("insert_short", _opsClass);
/*  313 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  316 */       dynEnumOperations.insert_short(paramShort);
/*      */     } finally {
/*  318 */       _servant_postinvoke(servantObject);
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
/*  331 */     ServantObject servantObject = _servant_preinvoke("insert_ushort", _opsClass);
/*  332 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  335 */       dynEnumOperations.insert_ushort(paramShort);
/*      */     } finally {
/*  337 */       _servant_postinvoke(servantObject);
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
/*  350 */     ServantObject servantObject = _servant_preinvoke("insert_long", _opsClass);
/*  351 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  354 */       dynEnumOperations.insert_long(paramInt);
/*      */     } finally {
/*  356 */       _servant_postinvoke(servantObject);
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
/*  369 */     ServantObject servantObject = _servant_preinvoke("insert_ulong", _opsClass);
/*  370 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  373 */       dynEnumOperations.insert_ulong(paramInt);
/*      */     } finally {
/*  375 */       _servant_postinvoke(servantObject);
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
/*  388 */     ServantObject servantObject = _servant_preinvoke("insert_float", _opsClass);
/*  389 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  392 */       dynEnumOperations.insert_float(paramFloat);
/*      */     } finally {
/*  394 */       _servant_postinvoke(servantObject);
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
/*  407 */     ServantObject servantObject = _servant_preinvoke("insert_double", _opsClass);
/*  408 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  411 */       dynEnumOperations.insert_double(paramDouble);
/*      */     } finally {
/*  413 */       _servant_postinvoke(servantObject);
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
/*  428 */     ServantObject servantObject = _servant_preinvoke("insert_string", _opsClass);
/*  429 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  432 */       dynEnumOperations.insert_string(paramString);
/*      */     } finally {
/*  434 */       _servant_postinvoke(servantObject);
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
/*  447 */     ServantObject servantObject = _servant_preinvoke("insert_reference", _opsClass);
/*  448 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  451 */       dynEnumOperations.insert_reference(paramObject);
/*      */     } finally {
/*  453 */       _servant_postinvoke(servantObject);
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
/*  466 */     ServantObject servantObject = _servant_preinvoke("insert_typecode", _opsClass);
/*  467 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  470 */       dynEnumOperations.insert_typecode(paramTypeCode);
/*      */     } finally {
/*  472 */       _servant_postinvoke(servantObject);
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
/*  485 */     ServantObject servantObject = _servant_preinvoke("insert_longlong", _opsClass);
/*  486 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  489 */       dynEnumOperations.insert_longlong(paramLong);
/*      */     } finally {
/*  491 */       _servant_postinvoke(servantObject);
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
/*  505 */     ServantObject servantObject = _servant_preinvoke("insert_ulonglong", _opsClass);
/*  506 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  509 */       dynEnumOperations.insert_ulonglong(paramLong);
/*      */     } finally {
/*  511 */       _servant_postinvoke(servantObject);
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
/*  524 */     ServantObject servantObject = _servant_preinvoke("insert_wchar", _opsClass);
/*  525 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  528 */       dynEnumOperations.insert_wchar(paramChar);
/*      */     } finally {
/*  530 */       _servant_postinvoke(servantObject);
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
/*  544 */     ServantObject servantObject = _servant_preinvoke("insert_wstring", _opsClass);
/*  545 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  548 */       dynEnumOperations.insert_wstring(paramString);
/*      */     } finally {
/*  550 */       _servant_postinvoke(servantObject);
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
/*  563 */     ServantObject servantObject = _servant_preinvoke("insert_any", _opsClass);
/*  564 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  567 */       dynEnumOperations.insert_any(paramAny);
/*      */     } finally {
/*  569 */       _servant_postinvoke(servantObject);
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
/*  582 */     ServantObject servantObject = _servant_preinvoke("insert_dyn_any", _opsClass);
/*  583 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  586 */       dynEnumOperations.insert_dyn_any(paramDynAny);
/*      */     } finally {
/*  588 */       _servant_postinvoke(servantObject);
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
/*  602 */     ServantObject servantObject = _servant_preinvoke("insert_val", _opsClass);
/*  603 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  606 */       dynEnumOperations.insert_val(paramSerializable);
/*      */     } finally {
/*  608 */       _servant_postinvoke(servantObject);
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
/*  623 */     ServantObject servantObject = _servant_preinvoke("get_boolean", _opsClass);
/*  624 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  627 */       return dynEnumOperations.get_boolean();
/*      */     } finally {
/*  629 */       _servant_postinvoke(servantObject);
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
/*  644 */     ServantObject servantObject = _servant_preinvoke("get_octet", _opsClass);
/*  645 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  648 */       return dynEnumOperations.get_octet();
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
/*      */   
/*      */   public char get_char() throws TypeMismatch, InvalidValue {
/*  665 */     ServantObject servantObject = _servant_preinvoke("get_char", _opsClass);
/*  666 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  669 */       return dynEnumOperations.get_char();
/*      */     } finally {
/*  671 */       _servant_postinvoke(servantObject);
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
/*  686 */     ServantObject servantObject = _servant_preinvoke("get_short", _opsClass);
/*  687 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  690 */       return dynEnumOperations.get_short();
/*      */     } finally {
/*  692 */       _servant_postinvoke(servantObject);
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
/*  707 */     ServantObject servantObject = _servant_preinvoke("get_ushort", _opsClass);
/*  708 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  711 */       return dynEnumOperations.get_ushort();
/*      */     } finally {
/*  713 */       _servant_postinvoke(servantObject);
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
/*  728 */     ServantObject servantObject = _servant_preinvoke("get_long", _opsClass);
/*  729 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  732 */       return dynEnumOperations.get_long();
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
/*      */   public int get_ulong() throws TypeMismatch, InvalidValue {
/*  749 */     ServantObject servantObject = _servant_preinvoke("get_ulong", _opsClass);
/*  750 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  753 */       return dynEnumOperations.get_ulong();
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
/*      */   public float get_float() throws TypeMismatch, InvalidValue {
/*  770 */     ServantObject servantObject = _servant_preinvoke("get_float", _opsClass);
/*  771 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  774 */       return dynEnumOperations.get_float();
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
/*      */   public double get_double() throws TypeMismatch, InvalidValue {
/*  791 */     ServantObject servantObject = _servant_preinvoke("get_double", _opsClass);
/*  792 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  795 */       return dynEnumOperations.get_double();
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
/*      */   
/*      */   public String get_string() throws TypeMismatch, InvalidValue {
/*  813 */     ServantObject servantObject = _servant_preinvoke("get_string", _opsClass);
/*  814 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  817 */       return dynEnumOperations.get_string();
/*      */     } finally {
/*  819 */       _servant_postinvoke(servantObject);
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
/*  834 */     ServantObject servantObject = _servant_preinvoke("get_reference", _opsClass);
/*  835 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  838 */       return dynEnumOperations.get_reference();
/*      */     } finally {
/*  840 */       _servant_postinvoke(servantObject);
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
/*  855 */     ServantObject servantObject = _servant_preinvoke("get_typecode", _opsClass);
/*  856 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  859 */       return dynEnumOperations.get_typecode();
/*      */     } finally {
/*  861 */       _servant_postinvoke(servantObject);
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
/*  876 */     ServantObject servantObject = _servant_preinvoke("get_longlong", _opsClass);
/*  877 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  880 */       return dynEnumOperations.get_longlong();
/*      */     } finally {
/*  882 */       _servant_postinvoke(servantObject);
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
/*  898 */     ServantObject servantObject = _servant_preinvoke("get_ulonglong", _opsClass);
/*  899 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  902 */       return dynEnumOperations.get_ulonglong();
/*      */     } finally {
/*  904 */       _servant_postinvoke(servantObject);
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
/*  919 */     ServantObject servantObject = _servant_preinvoke("get_wchar", _opsClass);
/*  920 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  923 */       return dynEnumOperations.get_wchar();
/*      */     } finally {
/*  925 */       _servant_postinvoke(servantObject);
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
/*  940 */     ServantObject servantObject = _servant_preinvoke("get_wstring", _opsClass);
/*  941 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  944 */       return dynEnumOperations.get_wstring();
/*      */     } finally {
/*  946 */       _servant_postinvoke(servantObject);
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
/*  961 */     ServantObject servantObject = _servant_preinvoke("get_any", _opsClass);
/*  962 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  965 */       return dynEnumOperations.get_any();
/*      */     } finally {
/*  967 */       _servant_postinvoke(servantObject);
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
/*  983 */     ServantObject servantObject = _servant_preinvoke("get_dyn_any", _opsClass);
/*  984 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  987 */       return dynEnumOperations.get_dyn_any();
/*      */     } finally {
/*  989 */       _servant_postinvoke(servantObject);
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
/* 1005 */     ServantObject servantObject = _servant_preinvoke("get_val", _opsClass);
/* 1006 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1009 */       return dynEnumOperations.get_val();
/*      */     } finally {
/* 1011 */       _servant_postinvoke(servantObject);
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
/* 1027 */     ServantObject servantObject = _servant_preinvoke("seek", _opsClass);
/* 1028 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1031 */       return dynEnumOperations.seek(paramInt);
/*      */     } finally {
/* 1033 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rewind() {
/* 1043 */     ServantObject servantObject = _servant_preinvoke("rewind", _opsClass);
/* 1044 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1047 */       dynEnumOperations.rewind();
/*      */     } finally {
/* 1049 */       _servant_postinvoke(servantObject);
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
/* 1062 */     ServantObject servantObject = _servant_preinvoke("next", _opsClass);
/* 1063 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1066 */       return dynEnumOperations.next();
/*      */     } finally {
/* 1068 */       _servant_postinvoke(servantObject);
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
/* 1090 */     ServantObject servantObject = _servant_preinvoke("component_count", _opsClass);
/* 1091 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1094 */       return dynEnumOperations.component_count();
/*      */     } finally {
/* 1096 */       _servant_postinvoke(servantObject);
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
/* 1121 */     ServantObject servantObject = _servant_preinvoke("current_component", _opsClass);
/* 1122 */     DynEnumOperations dynEnumOperations = (DynEnumOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1125 */       return dynEnumOperations.current_component();
/*      */     } finally {
/* 1127 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1132 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynEnum:1.0", "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _ids() {
/* 1138 */     return (String[])__ids.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 1143 */     String str = paramObjectInputStream.readUTF();
/* 1144 */     String[] arrayOfString = null;
/* 1145 */     Properties properties = null;
/* 1146 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1148 */       Object object = oRB.string_to_object(str);
/* 1149 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 1150 */       _set_delegate(delegate);
/*      */     } finally {
/* 1152 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1158 */     String[] arrayOfString = null;
/* 1159 */     Properties properties = null;
/* 1160 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1162 */       String str = oRB.object_to_string(this);
/* 1163 */       paramObjectOutputStream.writeUTF(str);
/*      */     } finally {
/* 1165 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynEnumStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */