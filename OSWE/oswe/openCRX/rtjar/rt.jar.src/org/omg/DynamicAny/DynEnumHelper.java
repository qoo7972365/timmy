/*    */ package org.omg.DynamicAny;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.BAD_PARAM;
/*    */ import org.omg.CORBA.MARSHAL;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.Delegate;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.ObjectImpl;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DynEnumHelper
/*    */ {
/* 18 */   private static String _id = "IDL:omg.org/DynamicAny/DynEnum:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DynEnum paramDynEnum) {
/* 22 */     OutputStream outputStream = paramAny.create_output_stream();
/* 23 */     paramAny.type(type());
/* 24 */     write(outputStream, paramDynEnum);
/* 25 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynEnum extract(Any paramAny) {
/* 30 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 33 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 36 */     if (__typeCode == null)
/*    */     {
/* 38 */       __typeCode = ORB.init().create_interface_tc(id(), "DynEnum");
/*    */     }
/* 40 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 45 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynEnum read(InputStream paramInputStream) {
/* 50 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DynEnum paramDynEnum) {
/* 55 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynEnum narrow(Object paramObject) {
/* 60 */     if (paramObject == null)
/* 61 */       return null; 
/* 62 */     if (paramObject instanceof DynEnum)
/* 63 */       return (DynEnum)paramObject; 
/* 64 */     if (!paramObject._is_a(id())) {
/* 65 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 68 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 69 */     _DynEnumStub _DynEnumStub = new _DynEnumStub();
/* 70 */     _DynEnumStub._set_delegate(delegate);
/* 71 */     return _DynEnumStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DynEnum unchecked_narrow(Object paramObject) {
/* 77 */     if (paramObject == null)
/* 78 */       return null; 
/* 79 */     if (paramObject instanceof DynEnum) {
/* 80 */       return (DynEnum)paramObject;
/*    */     }
/*    */     
/* 83 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 84 */     _DynEnumStub _DynEnumStub = new _DynEnumStub();
/* 85 */     _DynEnumStub._set_delegate(delegate);
/* 86 */     return _DynEnumStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynEnumHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */