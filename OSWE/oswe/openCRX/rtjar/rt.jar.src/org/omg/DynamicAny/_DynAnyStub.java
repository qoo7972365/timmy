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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class _DynAnyStub
/*      */   extends ObjectImpl
/*      */   implements DynAny
/*      */ {
/*   81 */   public static final Class _opsClass = DynAnyOperations.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*   96 */     ServantObject servantObject = _servant_preinvoke("type", _opsClass);
/*   97 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  100 */       return dynAnyOperations.type();
/*      */     } finally {
/*  102 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  118 */     ServantObject servantObject = _servant_preinvoke("assign", _opsClass);
/*  119 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  122 */       dynAnyOperations.assign(paramDynAny);
/*      */     } finally {
/*  124 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  139 */     ServantObject servantObject = _servant_preinvoke("from_any", _opsClass);
/*  140 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  143 */       dynAnyOperations.from_any(paramAny);
/*      */     } finally {
/*  145 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  159 */     ServantObject servantObject = _servant_preinvoke("to_any", _opsClass);
/*  160 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  163 */       return dynAnyOperations.to_any();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equal(DynAny paramDynAny) {
/*  180 */     ServantObject servantObject = _servant_preinvoke("equal", _opsClass);
/*  181 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  184 */       return dynAnyOperations.equal(paramDynAny);
/*      */     } finally {
/*  186 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  207 */     ServantObject servantObject = _servant_preinvoke("destroy", _opsClass);
/*  208 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  211 */       dynAnyOperations.destroy();
/*      */     } finally {
/*  213 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  227 */     ServantObject servantObject = _servant_preinvoke("copy", _opsClass);
/*  228 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  231 */       return dynAnyOperations.copy();
/*      */     } finally {
/*  233 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  246 */     ServantObject servantObject = _servant_preinvoke("insert_boolean", _opsClass);
/*  247 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  250 */       dynAnyOperations.insert_boolean(paramBoolean);
/*      */     } finally {
/*  252 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  265 */     ServantObject servantObject = _servant_preinvoke("insert_octet", _opsClass);
/*  266 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  269 */       dynAnyOperations.insert_octet(paramByte);
/*      */     } finally {
/*  271 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  284 */     ServantObject servantObject = _servant_preinvoke("insert_char", _opsClass);
/*  285 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  288 */       dynAnyOperations.insert_char(paramChar);
/*      */     } finally {
/*  290 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  303 */     ServantObject servantObject = _servant_preinvoke("insert_short", _opsClass);
/*  304 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  307 */       dynAnyOperations.insert_short(paramShort);
/*      */     } finally {
/*  309 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  322 */     ServantObject servantObject = _servant_preinvoke("insert_ushort", _opsClass);
/*  323 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  326 */       dynAnyOperations.insert_ushort(paramShort);
/*      */     } finally {
/*  328 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  341 */     ServantObject servantObject = _servant_preinvoke("insert_long", _opsClass);
/*  342 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  345 */       dynAnyOperations.insert_long(paramInt);
/*      */     } finally {
/*  347 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  360 */     ServantObject servantObject = _servant_preinvoke("insert_ulong", _opsClass);
/*  361 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  364 */       dynAnyOperations.insert_ulong(paramInt);
/*      */     } finally {
/*  366 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  379 */     ServantObject servantObject = _servant_preinvoke("insert_float", _opsClass);
/*  380 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  383 */       dynAnyOperations.insert_float(paramFloat);
/*      */     } finally {
/*  385 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  398 */     ServantObject servantObject = _servant_preinvoke("insert_double", _opsClass);
/*  399 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  402 */       dynAnyOperations.insert_double(paramDouble);
/*      */     } finally {
/*  404 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  419 */     ServantObject servantObject = _servant_preinvoke("insert_string", _opsClass);
/*  420 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  423 */       dynAnyOperations.insert_string(paramString);
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
/*      */   public void insert_reference(Object paramObject) throws TypeMismatch, InvalidValue {
/*  438 */     ServantObject servantObject = _servant_preinvoke("insert_reference", _opsClass);
/*  439 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  442 */       dynAnyOperations.insert_reference(paramObject);
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
/*      */   public void insert_typecode(TypeCode paramTypeCode) throws TypeMismatch, InvalidValue {
/*  457 */     ServantObject servantObject = _servant_preinvoke("insert_typecode", _opsClass);
/*  458 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  461 */       dynAnyOperations.insert_typecode(paramTypeCode);
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
/*      */   public void insert_longlong(long paramLong) throws TypeMismatch, InvalidValue {
/*  476 */     ServantObject servantObject = _servant_preinvoke("insert_longlong", _opsClass);
/*  477 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  480 */       dynAnyOperations.insert_longlong(paramLong);
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
/*      */   
/*      */   public void insert_ulonglong(long paramLong) throws TypeMismatch, InvalidValue {
/*  496 */     ServantObject servantObject = _servant_preinvoke("insert_ulonglong", _opsClass);
/*  497 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  500 */       dynAnyOperations.insert_ulonglong(paramLong);
/*      */     } finally {
/*  502 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  515 */     ServantObject servantObject = _servant_preinvoke("insert_wchar", _opsClass);
/*  516 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  519 */       dynAnyOperations.insert_wchar(paramChar);
/*      */     } finally {
/*  521 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  535 */     ServantObject servantObject = _servant_preinvoke("insert_wstring", _opsClass);
/*  536 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  539 */       dynAnyOperations.insert_wstring(paramString);
/*      */     } finally {
/*  541 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  554 */     ServantObject servantObject = _servant_preinvoke("insert_any", _opsClass);
/*  555 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  558 */       dynAnyOperations.insert_any(paramAny);
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
/*      */   public void insert_dyn_any(DynAny paramDynAny) throws TypeMismatch, InvalidValue {
/*  573 */     ServantObject servantObject = _servant_preinvoke("insert_dyn_any", _opsClass);
/*  574 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  577 */       dynAnyOperations.insert_dyn_any(paramDynAny);
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
/*      */   
/*      */   public void insert_val(Serializable paramSerializable) throws TypeMismatch, InvalidValue {
/*  593 */     ServantObject servantObject = _servant_preinvoke("insert_val", _opsClass);
/*  594 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  597 */       dynAnyOperations.insert_val(paramSerializable);
/*      */     } finally {
/*  599 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  614 */     ServantObject servantObject = _servant_preinvoke("get_boolean", _opsClass);
/*  615 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  618 */       return dynAnyOperations.get_boolean();
/*      */     } finally {
/*  620 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  635 */     ServantObject servantObject = _servant_preinvoke("get_octet", _opsClass);
/*  636 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  639 */       return dynAnyOperations.get_octet();
/*      */     } finally {
/*  641 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  656 */     ServantObject servantObject = _servant_preinvoke("get_char", _opsClass);
/*  657 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  660 */       return dynAnyOperations.get_char();
/*      */     } finally {
/*  662 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  677 */     ServantObject servantObject = _servant_preinvoke("get_short", _opsClass);
/*  678 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  681 */       return dynAnyOperations.get_short();
/*      */     } finally {
/*  683 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  698 */     ServantObject servantObject = _servant_preinvoke("get_ushort", _opsClass);
/*  699 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  702 */       return dynAnyOperations.get_ushort();
/*      */     } finally {
/*  704 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  719 */     ServantObject servantObject = _servant_preinvoke("get_long", _opsClass);
/*  720 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  723 */       return dynAnyOperations.get_long();
/*      */     } finally {
/*  725 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  740 */     ServantObject servantObject = _servant_preinvoke("get_ulong", _opsClass);
/*  741 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  744 */       return dynAnyOperations.get_ulong();
/*      */     } finally {
/*  746 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  761 */     ServantObject servantObject = _servant_preinvoke("get_float", _opsClass);
/*  762 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  765 */       return dynAnyOperations.get_float();
/*      */     } finally {
/*  767 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  782 */     ServantObject servantObject = _servant_preinvoke("get_double", _opsClass);
/*  783 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  786 */       return dynAnyOperations.get_double();
/*      */     } finally {
/*  788 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  804 */     ServantObject servantObject = _servant_preinvoke("get_string", _opsClass);
/*  805 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  808 */       return dynAnyOperations.get_string();
/*      */     } finally {
/*  810 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  825 */     ServantObject servantObject = _servant_preinvoke("get_reference", _opsClass);
/*  826 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  829 */       return dynAnyOperations.get_reference();
/*      */     } finally {
/*  831 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  846 */     ServantObject servantObject = _servant_preinvoke("get_typecode", _opsClass);
/*  847 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  850 */       return dynAnyOperations.get_typecode();
/*      */     } finally {
/*  852 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  867 */     ServantObject servantObject = _servant_preinvoke("get_longlong", _opsClass);
/*  868 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  871 */       return dynAnyOperations.get_longlong();
/*      */     } finally {
/*  873 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  889 */     ServantObject servantObject = _servant_preinvoke("get_ulonglong", _opsClass);
/*  890 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  893 */       return dynAnyOperations.get_ulonglong();
/*      */     } finally {
/*  895 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  910 */     ServantObject servantObject = _servant_preinvoke("get_wchar", _opsClass);
/*  911 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  914 */       return dynAnyOperations.get_wchar();
/*      */     } finally {
/*  916 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  931 */     ServantObject servantObject = _servant_preinvoke("get_wstring", _opsClass);
/*  932 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  935 */       return dynAnyOperations.get_wstring();
/*      */     } finally {
/*  937 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  952 */     ServantObject servantObject = _servant_preinvoke("get_any", _opsClass);
/*  953 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  956 */       return dynAnyOperations.get_any();
/*      */     } finally {
/*  958 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  974 */     ServantObject servantObject = _servant_preinvoke("get_dyn_any", _opsClass);
/*  975 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  978 */       return dynAnyOperations.get_dyn_any();
/*      */     } finally {
/*  980 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  996 */     ServantObject servantObject = _servant_preinvoke("get_val", _opsClass);
/*  997 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1000 */       return dynAnyOperations.get_val();
/*      */     } finally {
/* 1002 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1018 */     ServantObject servantObject = _servant_preinvoke("seek", _opsClass);
/* 1019 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1022 */       return dynAnyOperations.seek(paramInt);
/*      */     } finally {
/* 1024 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rewind() {
/* 1034 */     ServantObject servantObject = _servant_preinvoke("rewind", _opsClass);
/* 1035 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1038 */       dynAnyOperations.rewind();
/*      */     } finally {
/* 1040 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/* 1053 */     ServantObject servantObject = _servant_preinvoke("next", _opsClass);
/* 1054 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1057 */       return dynAnyOperations.next();
/*      */     } finally {
/* 1059 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1081 */     ServantObject servantObject = _servant_preinvoke("component_count", _opsClass);
/* 1082 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1085 */       return dynAnyOperations.component_count();
/*      */     } finally {
/* 1087 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1112 */     ServantObject servantObject = _servant_preinvoke("current_component", _opsClass);
/* 1113 */     DynAnyOperations dynAnyOperations = (DynAnyOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1116 */       return dynAnyOperations.current_component();
/*      */     } finally {
/* 1118 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1123 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _ids() {
/* 1128 */     return (String[])__ids.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 1133 */     String str = paramObjectInputStream.readUTF();
/* 1134 */     String[] arrayOfString = null;
/* 1135 */     Properties properties = null;
/* 1136 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1138 */       Object object = oRB.string_to_object(str);
/* 1139 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 1140 */       _set_delegate(delegate);
/*      */     } finally {
/* 1142 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1148 */     String[] arrayOfString = null;
/* 1149 */     Properties properties = null;
/* 1150 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1152 */       String str = oRB.object_to_string(this);
/* 1153 */       paramObjectOutputStream.writeUTF(str);
/*      */     } finally {
/* 1155 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynAnyStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */