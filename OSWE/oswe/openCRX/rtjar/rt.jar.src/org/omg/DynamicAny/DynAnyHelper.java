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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DynAnyHelper
/*     */ {
/*  81 */   private static String _id = "IDL:omg.org/DynamicAny/DynAny:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, DynAny paramDynAny) {
/*  85 */     OutputStream outputStream = paramAny.create_output_stream();
/*  86 */     paramAny.type(type());
/*  87 */     write(outputStream, paramDynAny);
/*  88 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static DynAny extract(Any paramAny) {
/*  93 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  96 */   private static TypeCode __typeCode = null;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  99 */     if (__typeCode == null)
/*     */     {
/* 101 */       __typeCode = ORB.init().create_interface_tc(id(), "DynAny");
/*     */     }
/* 103 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 108 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DynAny read(InputStream paramInputStream) {
/* 113 */     throw new MARSHAL();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, DynAny paramDynAny) {
/* 118 */     throw new MARSHAL();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DynAny narrow(Object paramObject) {
/* 123 */     if (paramObject == null)
/* 124 */       return null; 
/* 125 */     if (paramObject instanceof DynAny)
/* 126 */       return (DynAny)paramObject; 
/* 127 */     if (!paramObject._is_a(id())) {
/* 128 */       throw new BAD_PARAM();
/*     */     }
/*     */     
/* 131 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 132 */     _DynAnyStub _DynAnyStub = new _DynAnyStub();
/* 133 */     _DynAnyStub._set_delegate(delegate);
/* 134 */     return _DynAnyStub;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynAny unchecked_narrow(Object paramObject) {
/* 140 */     if (paramObject == null)
/* 141 */       return null; 
/* 142 */     if (paramObject instanceof DynAny) {
/* 143 */       return (DynAny)paramObject;
/*     */     }
/*     */     
/* 146 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 147 */     _DynAnyStub _DynAnyStub = new _DynAnyStub();
/* 148 */     _DynAnyStub._set_delegate(delegate);
/* 149 */     return _DynAnyStub;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynAnyHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */