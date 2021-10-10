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
/*      */ import org.omg.CORBA.portable.ObjectImpl;
/*      */ import org.omg.CORBA.portable.ServantObject;
/*      */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*      */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*      */ 
/*      */ public class _DynSequenceStub extends ObjectImpl implements DynSequence {
/*   17 */   public static final Class _opsClass = DynSequenceOperations.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int get_length() {
/*   26 */     ServantObject servantObject = _servant_preinvoke("get_length", _opsClass);
/*   27 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   30 */       return dynSequenceOperations.get_length();
/*      */     } finally {
/*   32 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set_length(int paramInt) throws InvalidValue {
/*   60 */     ServantObject servantObject = _servant_preinvoke("set_length", _opsClass);
/*   61 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   64 */       dynSequenceOperations.set_length(paramInt);
/*      */     } finally {
/*   66 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Any[] get_elements() {
/*   76 */     ServantObject servantObject = _servant_preinvoke("get_elements", _opsClass);
/*   77 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*   80 */       return dynSequenceOperations.get_elements();
/*      */     } finally {
/*   82 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*   98 */     ServantObject servantObject = _servant_preinvoke("set_elements", _opsClass);
/*   99 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  102 */       dynSequenceOperations.set_elements(paramArrayOfAny);
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
/*      */   public DynAny[] get_elements_as_dyn_any() {
/*  114 */     ServantObject servantObject = _servant_preinvoke("get_elements_as_dyn_any", _opsClass);
/*  115 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  118 */       return dynSequenceOperations.get_elements_as_dyn_any();
/*      */     } finally {
/*  120 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  136 */     ServantObject servantObject = _servant_preinvoke("set_elements_as_dyn_any", _opsClass);
/*  137 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  140 */       dynSequenceOperations.set_elements_as_dyn_any(paramArrayOfDynAny);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCode type() {
/*  158 */     ServantObject servantObject = _servant_preinvoke("type", _opsClass);
/*  159 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  162 */       return dynSequenceOperations.type();
/*      */     } finally {
/*  164 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  180 */     ServantObject servantObject = _servant_preinvoke("assign", _opsClass);
/*  181 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  184 */       dynSequenceOperations.assign(paramDynAny);
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
/*      */   public void from_any(Any paramAny) throws TypeMismatch, InvalidValue {
/*  201 */     ServantObject servantObject = _servant_preinvoke("from_any", _opsClass);
/*  202 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  205 */       dynSequenceOperations.from_any(paramAny);
/*      */     } finally {
/*  207 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  221 */     ServantObject servantObject = _servant_preinvoke("to_any", _opsClass);
/*  222 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  225 */       return dynSequenceOperations.to_any();
/*      */     } finally {
/*  227 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  242 */     ServantObject servantObject = _servant_preinvoke("equal", _opsClass);
/*  243 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  246 */       return dynSequenceOperations.equal(paramDynAny);
/*      */     } finally {
/*  248 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  269 */     ServantObject servantObject = _servant_preinvoke("destroy", _opsClass);
/*  270 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  273 */       dynSequenceOperations.destroy();
/*      */     } finally {
/*  275 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  289 */     ServantObject servantObject = _servant_preinvoke("copy", _opsClass);
/*  290 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  293 */       return dynSequenceOperations.copy();
/*      */     } finally {
/*  295 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  308 */     ServantObject servantObject = _servant_preinvoke("insert_boolean", _opsClass);
/*  309 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  312 */       dynSequenceOperations.insert_boolean(paramBoolean);
/*      */     } finally {
/*  314 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  327 */     ServantObject servantObject = _servant_preinvoke("insert_octet", _opsClass);
/*  328 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  331 */       dynSequenceOperations.insert_octet(paramByte);
/*      */     } finally {
/*  333 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  346 */     ServantObject servantObject = _servant_preinvoke("insert_char", _opsClass);
/*  347 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  350 */       dynSequenceOperations.insert_char(paramChar);
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
/*      */   public void insert_short(short paramShort) throws TypeMismatch, InvalidValue {
/*  365 */     ServantObject servantObject = _servant_preinvoke("insert_short", _opsClass);
/*  366 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  369 */       dynSequenceOperations.insert_short(paramShort);
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
/*      */   public void insert_ushort(short paramShort) throws TypeMismatch, InvalidValue {
/*  384 */     ServantObject servantObject = _servant_preinvoke("insert_ushort", _opsClass);
/*  385 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  388 */       dynSequenceOperations.insert_ushort(paramShort);
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
/*      */   public void insert_long(int paramInt) throws TypeMismatch, InvalidValue {
/*  403 */     ServantObject servantObject = _servant_preinvoke("insert_long", _opsClass);
/*  404 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  407 */       dynSequenceOperations.insert_long(paramInt);
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
/*      */   public void insert_ulong(int paramInt) throws TypeMismatch, InvalidValue {
/*  422 */     ServantObject servantObject = _servant_preinvoke("insert_ulong", _opsClass);
/*  423 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  426 */       dynSequenceOperations.insert_ulong(paramInt);
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
/*      */   public void insert_float(float paramFloat) throws TypeMismatch, InvalidValue {
/*  441 */     ServantObject servantObject = _servant_preinvoke("insert_float", _opsClass);
/*  442 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  445 */       dynSequenceOperations.insert_float(paramFloat);
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
/*      */   public void insert_double(double paramDouble) throws TypeMismatch, InvalidValue {
/*  460 */     ServantObject servantObject = _servant_preinvoke("insert_double", _opsClass);
/*  461 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  464 */       dynSequenceOperations.insert_double(paramDouble);
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
/*      */ 
/*      */   
/*      */   public void insert_string(String paramString) throws TypeMismatch, InvalidValue {
/*  481 */     ServantObject servantObject = _servant_preinvoke("insert_string", _opsClass);
/*  482 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  485 */       dynSequenceOperations.insert_string(paramString);
/*      */     } finally {
/*  487 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  500 */     ServantObject servantObject = _servant_preinvoke("insert_reference", _opsClass);
/*  501 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  504 */       dynSequenceOperations.insert_reference(paramObject);
/*      */     } finally {
/*  506 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  519 */     ServantObject servantObject = _servant_preinvoke("insert_typecode", _opsClass);
/*  520 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  523 */       dynSequenceOperations.insert_typecode(paramTypeCode);
/*      */     } finally {
/*  525 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  538 */     ServantObject servantObject = _servant_preinvoke("insert_longlong", _opsClass);
/*  539 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  542 */       dynSequenceOperations.insert_longlong(paramLong);
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
/*      */   
/*      */   public void insert_ulonglong(long paramLong) throws TypeMismatch, InvalidValue {
/*  558 */     ServantObject servantObject = _servant_preinvoke("insert_ulonglong", _opsClass);
/*  559 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  562 */       dynSequenceOperations.insert_ulonglong(paramLong);
/*      */     } finally {
/*  564 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  577 */     ServantObject servantObject = _servant_preinvoke("insert_wchar", _opsClass);
/*  578 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  581 */       dynSequenceOperations.insert_wchar(paramChar);
/*      */     } finally {
/*  583 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
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
/*  597 */     ServantObject servantObject = _servant_preinvoke("insert_wstring", _opsClass);
/*  598 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  601 */       dynSequenceOperations.insert_wstring(paramString);
/*      */     } finally {
/*  603 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  616 */     ServantObject servantObject = _servant_preinvoke("insert_any", _opsClass);
/*  617 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  620 */       dynSequenceOperations.insert_any(paramAny);
/*      */     } finally {
/*  622 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/*  635 */     ServantObject servantObject = _servant_preinvoke("insert_dyn_any", _opsClass);
/*  636 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  639 */       dynSequenceOperations.insert_dyn_any(paramDynAny);
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
/*      */   public void insert_val(Serializable paramSerializable) throws TypeMismatch, InvalidValue {
/*  655 */     ServantObject servantObject = _servant_preinvoke("insert_val", _opsClass);
/*  656 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  659 */       dynSequenceOperations.insert_val(paramSerializable);
/*      */     } finally {
/*  661 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  676 */     ServantObject servantObject = _servant_preinvoke("get_boolean", _opsClass);
/*  677 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  680 */       return dynSequenceOperations.get_boolean();
/*      */     } finally {
/*  682 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  697 */     ServantObject servantObject = _servant_preinvoke("get_octet", _opsClass);
/*  698 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  701 */       return dynSequenceOperations.get_octet();
/*      */     } finally {
/*  703 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  718 */     ServantObject servantObject = _servant_preinvoke("get_char", _opsClass);
/*  719 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  722 */       return dynSequenceOperations.get_char();
/*      */     } finally {
/*  724 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  739 */     ServantObject servantObject = _servant_preinvoke("get_short", _opsClass);
/*  740 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  743 */       return dynSequenceOperations.get_short();
/*      */     } finally {
/*  745 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  760 */     ServantObject servantObject = _servant_preinvoke("get_ushort", _opsClass);
/*  761 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  764 */       return dynSequenceOperations.get_ushort();
/*      */     } finally {
/*  766 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  781 */     ServantObject servantObject = _servant_preinvoke("get_long", _opsClass);
/*  782 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  785 */       return dynSequenceOperations.get_long();
/*      */     } finally {
/*  787 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  802 */     ServantObject servantObject = _servant_preinvoke("get_ulong", _opsClass);
/*  803 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  806 */       return dynSequenceOperations.get_ulong();
/*      */     } finally {
/*  808 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  823 */     ServantObject servantObject = _servant_preinvoke("get_float", _opsClass);
/*  824 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  827 */       return dynSequenceOperations.get_float();
/*      */     } finally {
/*  829 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  844 */     ServantObject servantObject = _servant_preinvoke("get_double", _opsClass);
/*  845 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  848 */       return dynSequenceOperations.get_double();
/*      */     } finally {
/*  850 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  866 */     ServantObject servantObject = _servant_preinvoke("get_string", _opsClass);
/*  867 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  870 */       return dynSequenceOperations.get_string();
/*      */     } finally {
/*  872 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  887 */     ServantObject servantObject = _servant_preinvoke("get_reference", _opsClass);
/*  888 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  891 */       return dynSequenceOperations.get_reference();
/*      */     } finally {
/*  893 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  908 */     ServantObject servantObject = _servant_preinvoke("get_typecode", _opsClass);
/*  909 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  912 */       return dynSequenceOperations.get_typecode();
/*      */     } finally {
/*  914 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  929 */     ServantObject servantObject = _servant_preinvoke("get_longlong", _opsClass);
/*  930 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  933 */       return dynSequenceOperations.get_longlong();
/*      */     } finally {
/*  935 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  951 */     ServantObject servantObject = _servant_preinvoke("get_ulonglong", _opsClass);
/*  952 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  955 */       return dynSequenceOperations.get_ulonglong();
/*      */     } finally {
/*  957 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  972 */     ServantObject servantObject = _servant_preinvoke("get_wchar", _opsClass);
/*  973 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  976 */       return dynSequenceOperations.get_wchar();
/*      */     } finally {
/*  978 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/*  993 */     ServantObject servantObject = _servant_preinvoke("get_wstring", _opsClass);
/*  994 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/*  997 */       return dynSequenceOperations.get_wstring();
/*      */     } finally {
/*  999 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
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
/* 1014 */     ServantObject servantObject = _servant_preinvoke("get_any", _opsClass);
/* 1015 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1018 */       return dynSequenceOperations.get_any();
/*      */     } finally {
/* 1020 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1036 */     ServantObject servantObject = _servant_preinvoke("get_dyn_any", _opsClass);
/* 1037 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1040 */       return dynSequenceOperations.get_dyn_any();
/*      */     } finally {
/* 1042 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1058 */     ServantObject servantObject = _servant_preinvoke("get_val", _opsClass);
/* 1059 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1062 */       return dynSequenceOperations.get_val();
/*      */     } finally {
/* 1064 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1080 */     ServantObject servantObject = _servant_preinvoke("seek", _opsClass);
/* 1081 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1084 */       return dynSequenceOperations.seek(paramInt);
/*      */     } finally {
/* 1086 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rewind() {
/* 1096 */     ServantObject servantObject = _servant_preinvoke("rewind", _opsClass);
/* 1097 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1100 */       dynSequenceOperations.rewind();
/*      */     } finally {
/* 1102 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
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
/* 1115 */     ServantObject servantObject = _servant_preinvoke("next", _opsClass);
/* 1116 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1119 */       return dynSequenceOperations.next();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int component_count() {
/* 1143 */     ServantObject servantObject = _servant_preinvoke("component_count", _opsClass);
/* 1144 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1147 */       return dynSequenceOperations.component_count();
/*      */     } finally {
/* 1149 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1174 */     ServantObject servantObject = _servant_preinvoke("current_component", _opsClass);
/* 1175 */     DynSequenceOperations dynSequenceOperations = (DynSequenceOperations)servantObject.servant;
/*      */     
/*      */     try {
/* 1178 */       return dynSequenceOperations.current_component();
/*      */     } finally {
/* 1180 */       _servant_postinvoke(servantObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1185 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynSequence:1.0", "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] _ids() {
/* 1191 */     return (String[])__ids.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 1196 */     String str = paramObjectInputStream.readUTF();
/* 1197 */     String[] arrayOfString = null;
/* 1198 */     Properties properties = null;
/* 1199 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1201 */       Object object = oRB.string_to_object(str);
/* 1202 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 1203 */       _set_delegate(delegate);
/*      */     } finally {
/* 1205 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1211 */     String[] arrayOfString = null;
/* 1212 */     Properties properties = null;
/* 1213 */     ORB oRB = ORB.init(arrayOfString, properties);
/*      */     try {
/* 1215 */       String str = oRB.object_to_string(this);
/* 1216 */       paramObjectOutputStream.writeUTF(str);
/*      */     } finally {
/* 1218 */       oRB.destroy();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynSequenceStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */