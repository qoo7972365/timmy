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
/*      */ public class _DynFixedStub extends ObjectImpl implements DynFixed {
/*   19 */   public static final Class _opsClass = DynFixedOperations.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String get_value() {
/*   28 */     ServantObject servantObject = _servant_preinvoke("get_value", _opsClass);
/*   29 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   32 */       return dynFixedOperations.get_value();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean set_value(String paramString) throws TypeMismatch, InvalidValue {
/*   53 */     ServantObject servantObject = _servant_preinvoke("set_value", _opsClass);
/*   54 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   57 */       return dynFixedOperations.set_value(paramString);
/*      */     } finally {
/*   59 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*   75 */     ServantObject servantObject = _servant_preinvoke("type", _opsClass);
/*   76 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   79 */       return dynFixedOperations.type();
/*      */     } finally {
/*   81 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*   97 */     ServantObject servantObject = _servant_preinvoke("assign", _opsClass);
/*   98 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  101 */       dynFixedOperations.assign(paramDynAny);
/*      */     } finally {
/*  103 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  118 */     ServantObject servantObject = _servant_preinvoke("from_any", _opsClass);
/*  119 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  122 */       dynFixedOperations.from_any(paramAny);
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
/*      */   public Any to_any() {
/*  138 */     ServantObject servantObject = _servant_preinvoke("to_any", _opsClass);
/*  139 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  142 */       return dynFixedOperations.to_any();
/*      */     } finally {
/*  144 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  159 */     ServantObject servantObject = _servant_preinvoke("equal", _opsClass);
/*  160 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  163 */       return dynFixedOperations.equal(paramDynAny);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy() {
/*  186 */     ServantObject servantObject = _servant_preinvoke("destroy", _opsClass);
/*  187 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  190 */       dynFixedOperations.destroy();
/*      */     } finally {
/*  192 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  206 */     ServantObject servantObject = _servant_preinvoke("copy", _opsClass);
/*  207 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  210 */       return dynFixedOperations.copy();
/*      */     } finally {
/*  212 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  225 */     ServantObject servantObject = _servant_preinvoke("insert_boolean", _opsClass);
/*  226 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  229 */       dynFixedOperations.insert_boolean(paramBoolean);
/*      */     } finally {
/*  231 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  244 */     ServantObject servantObject = _servant_preinvoke("insert_octet", _opsClass);
/*  245 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  248 */       dynFixedOperations.insert_octet(paramByte);
/*      */     } finally {
/*  250 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  263 */     ServantObject servantObject = _servant_preinvoke("insert_char", _opsClass);
/*  264 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  267 */       dynFixedOperations.insert_char(paramChar);
/*      */     } finally {
/*  269 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  282 */     ServantObject servantObject = _servant_preinvoke("insert_short", _opsClass);
/*  283 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  286 */       dynFixedOperations.insert_short(paramShort);
/*      */     } finally {
/*  288 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  301 */     ServantObject servantObject = _servant_preinvoke("insert_ushort", _opsClass);
/*  302 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  305 */       dynFixedOperations.insert_ushort(paramShort);
/*      */     } finally {
/*  307 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  320 */     ServantObject servantObject = _servant_preinvoke("insert_long", _opsClass);
/*  321 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  324 */       dynFixedOperations.insert_long(paramInt);
/*      */     } finally {
/*  326 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  339 */     ServantObject servantObject = _servant_preinvoke("insert_ulong", _opsClass);
/*  340 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  343 */       dynFixedOperations.insert_ulong(paramInt);
/*      */     } finally {
/*  345 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  358 */     ServantObject servantObject = _servant_preinvoke("insert_float", _opsClass);
/*  359 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  362 */       dynFixedOperations.insert_float(paramFloat);
/*      */     } finally {
/*  364 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  377 */     ServantObject servantObject = _servant_preinvoke("insert_double", _opsClass);
/*  378 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  381 */       dynFixedOperations.insert_double(paramDouble);
/*      */     } finally {
/*  383 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  398 */     ServantObject servantObject = _servant_preinvoke("insert_string", _opsClass);
/*  399 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  402 */       dynFixedOperations.insert_string(paramString);
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
/*      */   public void insert_reference(Object paramObject) throws TypeMismatch, InvalidValue {
/*  417 */     ServantObject servantObject = _servant_preinvoke("insert_reference", _opsClass);
/*  418 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  421 */       dynFixedOperations.insert_reference(paramObject);
/*      */     } finally {
/*  423 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  436 */     ServantObject servantObject = _servant_preinvoke("insert_typecode", _opsClass);
/*  437 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  440 */       dynFixedOperations.insert_typecode(paramTypeCode);
/*      */     } finally {
/*  442 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  455 */     ServantObject servantObject = _servant_preinvoke("insert_longlong", _opsClass);
/*  456 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  459 */       dynFixedOperations.insert_longlong(paramLong);
/*      */     } finally {
/*  461 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  475 */     ServantObject servantObject = _servant_preinvoke("insert_ulonglong", _opsClass);
/*  476 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  479 */       dynFixedOperations.insert_ulonglong(paramLong);
/*      */     } finally {
/*  481 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  494 */     ServantObject servantObject = _servant_preinvoke("insert_wchar", _opsClass);
/*  495 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  498 */       dynFixedOperations.insert_wchar(paramChar);
/*      */     } finally {
/*  500 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  514 */     ServantObject servantObject = _servant_preinvoke("insert_wstring", _opsClass);
/*  515 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  518 */       dynFixedOperations.insert_wstring(paramString);
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
/*      */   public void insert_any(Any paramAny) throws TypeMismatch, InvalidValue {
/*  533 */     ServantObject servantObject = _servant_preinvoke("insert_any", _opsClass);
/*  534 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  537 */       dynFixedOperations.insert_any(paramAny);
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
/*      */   public void insert_dyn_any(DynAny paramDynAny) throws TypeMismatch, InvalidValue {
/*  552 */     ServantObject servantObject = _servant_preinvoke("insert_dyn_any", _opsClass);
/*  553 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  556 */       dynFixedOperations.insert_dyn_any(paramDynAny);
/*      */     } finally {
/*  558 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  572 */     ServantObject servantObject = _servant_preinvoke("insert_val", _opsClass);
/*  573 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  576 */       dynFixedOperations.insert_val(paramSerializable);
/*      */     } finally {
/*  578 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  593 */     ServantObject servantObject = _servant_preinvoke("get_boolean", _opsClass);
/*  594 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  597 */       return dynFixedOperations.get_boolean();
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
/*      */   public byte get_octet() throws TypeMismatch, InvalidValue {
/*  614 */     ServantObject servantObject = _servant_preinvoke("get_octet", _opsClass);
/*  615 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  618 */       return dynFixedOperations.get_octet();
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
/*      */   public char get_char() throws TypeMismatch, InvalidValue {
/*  635 */     ServantObject servantObject = _servant_preinvoke("get_char", _opsClass);
/*  636 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  639 */       return dynFixedOperations.get_char();
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
/*      */   public short get_short() throws TypeMismatch, InvalidValue {
/*  656 */     ServantObject servantObject = _servant_preinvoke("get_short", _opsClass);
/*  657 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  660 */       return dynFixedOperations.get_short();
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
/*      */   public short get_ushort() throws TypeMismatch, InvalidValue {
/*  677 */     ServantObject servantObject = _servant_preinvoke("get_ushort", _opsClass);
/*  678 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  681 */       return dynFixedOperations.get_ushort();
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
/*      */   public int get_long() throws TypeMismatch, InvalidValue {
/*  698 */     ServantObject servantObject = _servant_preinvoke("get_long", _opsClass);
/*  699 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  702 */       return dynFixedOperations.get_long();
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
/*      */   public int get_ulong() throws TypeMismatch, InvalidValue {
/*  719 */     ServantObject servantObject = _servant_preinvoke("get_ulong", _opsClass);
/*  720 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  723 */       return dynFixedOperations.get_ulong();
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
/*      */   public float get_float() throws TypeMismatch, InvalidValue {
/*  740 */     ServantObject servantObject = _servant_preinvoke("get_float", _opsClass);
/*  741 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  744 */       return dynFixedOperations.get_float();
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
/*      */   public double get_double() throws TypeMismatch, InvalidValue {
/*  761 */     ServantObject servantObject = _servant_preinvoke("get_double", _opsClass);
/*  762 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  765 */       return dynFixedOperations.get_double();
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
/*      */   
/*      */   public String get_string() throws TypeMismatch, InvalidValue {
/*  783 */     ServantObject servantObject = _servant_preinvoke("get_string", _opsClass);
/*  784 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  787 */       return dynFixedOperations.get_string();
/*      */     } finally {
/*  789 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  804 */     ServantObject servantObject = _servant_preinvoke("get_reference", _opsClass);
/*  805 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  808 */       return dynFixedOperations.get_reference();
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
/*      */   public TypeCode get_typecode() throws TypeMismatch, InvalidValue {
/*  825 */     ServantObject servantObject = _servant_preinvoke("get_typecode", _opsClass);
/*  826 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  829 */       return dynFixedOperations.get_typecode();
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
/*      */   public long get_longlong() throws TypeMismatch, InvalidValue {
/*  846 */     ServantObject servantObject = _servant_preinvoke("get_longlong", _opsClass);
/*  847 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  850 */       return dynFixedOperations.get_longlong();
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
/*      */   
/*      */   public long get_ulonglong() throws TypeMismatch, InvalidValue {
/*  868 */     ServantObject servantObject = _servant_preinvoke("get_ulonglong", _opsClass);
/*  869 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  872 */       return dynFixedOperations.get_ulonglong();
/*      */     } finally {
/*  874 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  889 */     ServantObject servantObject = _servant_preinvoke("get_wchar", _opsClass);
/*  890 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  893 */       return dynFixedOperations.get_wchar();
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
/*      */   public String get_wstring() throws TypeMismatch, InvalidValue {
/*  910 */     ServantObject servantObject = _servant_preinvoke("get_wstring", _opsClass);
/*  911 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  914 */       return dynFixedOperations.get_wstring();
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
/*      */   public Any get_any() throws TypeMismatch, InvalidValue {
/*  931 */     ServantObject servantObject = _servant_preinvoke("get_any", _opsClass);
/*  932 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  935 */       return dynFixedOperations.get_any();
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
/*      */   
/*      */   public DynAny get_dyn_any() throws TypeMismatch, InvalidValue {
/*  953 */     ServantObject servantObject = _servant_preinvoke("get_dyn_any", _opsClass);
/*  954 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  957 */       return dynFixedOperations.get_dyn_any();
/*      */     } finally {
/*  959 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  975 */     ServantObject servantObject = _servant_preinvoke("get_val", _opsClass);
/*  976 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  979 */       return dynFixedOperations.get_val();
/*      */     } finally {
/*  981 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  997 */     ServantObject servantObject = _servant_preinvoke("seek", _opsClass);
/*  998 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1001 */       return dynFixedOperations.seek(paramInt);
/*      */     } finally {
/* 1003 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rewind() {
/* 1013 */     ServantObject servantObject = _servant_preinvoke("rewind", _opsClass);
/* 1014 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1017 */       dynFixedOperations.rewind();
/*      */     } finally {
/* 1019 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/* 1032 */     ServantObject servantObject = _servant_preinvoke("next", _opsClass);
/* 1033 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1036 */       return dynFixedOperations.next();
/*      */     } finally {
/* 1038 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1060 */     ServantObject servantObject = _servant_preinvoke("component_count", _opsClass);
/* 1061 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1064 */       return dynFixedOperations.component_count();
/*      */     } finally {
/* 1066 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1091 */     ServantObject servantObject = _servant_preinvoke("current_component", _opsClass);
/* 1092 */     DynFixedOperations dynFixedOperations = (DynFixedOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1095 */       return dynFixedOperations.current_component();
/*      */     } finally {
/* 1097 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1102 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynFixed:1.0", "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _ids() {
/* 1108 */     return (String[])__ids.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 1113 */     String str = paramObjectInputStream.readUTF();
/* 1114 */     String[] arrayOfString = null;
/* 1115 */     Properties properties = null;
/* 1116 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1118 */       Object object = oRB.string_to_object(str);
/* 1119 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 1120 */       _set_delegate(delegate);
/*      */     } finally {
/* 1122 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1128 */     String[] arrayOfString = null;
/* 1129 */     Properties properties = null;
/* 1130 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1132 */       String str = oRB.object_to_string(this);
/* 1133 */       paramObjectOutputStream.writeUTF(str);
/*      */     } finally {
/* 1135 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynFixedStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */