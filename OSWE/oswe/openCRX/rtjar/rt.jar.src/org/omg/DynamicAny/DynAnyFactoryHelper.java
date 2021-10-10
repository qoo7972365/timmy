/*     */ package org.omg.DynamicAny;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DynAnyFactoryHelper
/*     */ {
/*  35 */   private static String _id = "IDL:omg.org/DynamicAny/DynAnyFactory:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, DynAnyFactory paramDynAnyFactory) {
/*  39 */     OutputStream outputStream = paramAny.create_output_stream();
/*  40 */     paramAny.type(type());
/*  41 */     write(outputStream, paramDynAnyFactory);
/*  42 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static DynAnyFactory extract(Any paramAny) {
/*  47 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  50 */   private static TypeCode __typeCode = null;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  53 */     if (__typeCode == null)
/*     */     {
/*  55 */       __typeCode = ORB.init().create_interface_tc(id(), "DynAnyFactory");
/*     */     }
/*  57 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  62 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DynAnyFactory read(InputStream paramInputStream) {
/*  67 */     throw new MARSHAL();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, DynAnyFactory paramDynAnyFactory) {
/*  72 */     throw new MARSHAL();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DynAnyFactory narrow(Object paramObject) {
/*  77 */     if (paramObject == null)
/*  78 */       return null; 
/*  79 */     if (paramObject instanceof DynAnyFactory)
/*  80 */       return (DynAnyFactory)paramObject; 
/*  81 */     if (!paramObject._is_a(id())) {
/*  82 */       throw new BAD_PARAM();
/*     */     }
/*     */     
/*  85 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/*  86 */     _DynAnyFactoryStub _DynAnyFactoryStub = new _DynAnyFactoryStub();
/*  87 */     _DynAnyFactoryStub._set_delegate(delegate);
/*  88 */     return _DynAnyFactoryStub;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynAnyFactory unchecked_narrow(Object paramObject) {
/*  94 */     if (paramObject == null)
/*  95 */       return null; 
/*  96 */     if (paramObject instanceof DynAnyFactory) {
/*  97 */       return (DynAnyFactory)paramObject;
/*     */     }
/*     */     
/* 100 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 101 */     _DynAnyFactoryStub _DynAnyFactoryStub = new _DynAnyFactoryStub();
/* 102 */     _DynAnyFactoryStub._set_delegate(delegate);
/* 103 */     return _DynAnyFactoryStub;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynAnyFactoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */