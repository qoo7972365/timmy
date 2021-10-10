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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DynValueHelper
/*    */ {
/* 26 */   private static String _id = "IDL:omg.org/DynamicAny/DynValue:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DynValue paramDynValue) {
/* 30 */     OutputStream outputStream = paramAny.create_output_stream();
/* 31 */     paramAny.type(type());
/* 32 */     write(outputStream, paramDynValue);
/* 33 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynValue extract(Any paramAny) {
/* 38 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 41 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 44 */     if (__typeCode == null)
/*    */     {
/* 46 */       __typeCode = ORB.init().create_interface_tc(id(), "DynValue");
/*    */     }
/* 48 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 53 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynValue read(InputStream paramInputStream) {
/* 58 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DynValue paramDynValue) {
/* 63 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynValue narrow(Object paramObject) {
/* 68 */     if (paramObject == null)
/* 69 */       return null; 
/* 70 */     if (paramObject instanceof DynValue)
/* 71 */       return (DynValue)paramObject; 
/* 72 */     if (!paramObject._is_a(id())) {
/* 73 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 76 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 77 */     _DynValueStub _DynValueStub = new _DynValueStub();
/* 78 */     _DynValueStub._set_delegate(delegate);
/* 79 */     return _DynValueStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DynValue unchecked_narrow(Object paramObject) {
/* 85 */     if (paramObject == null)
/* 86 */       return null; 
/* 87 */     if (paramObject instanceof DynValue) {
/* 88 */       return (DynValue)paramObject;
/*    */     }
/*    */     
/* 91 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 92 */     _DynValueStub _DynValueStub = new _DynValueStub();
/* 93 */     _DynValueStub._set_delegate(delegate);
/* 94 */     return _DynValueStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynValueHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */