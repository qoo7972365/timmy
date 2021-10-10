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
/*    */ public abstract class DynFixedHelper
/*    */ {
/* 19 */   private static String _id = "IDL:omg.org/DynamicAny/DynFixed:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DynFixed paramDynFixed) {
/* 23 */     OutputStream outputStream = paramAny.create_output_stream();
/* 24 */     paramAny.type(type());
/* 25 */     write(outputStream, paramDynFixed);
/* 26 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynFixed extract(Any paramAny) {
/* 31 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 34 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 37 */     if (__typeCode == null)
/*    */     {
/* 39 */       __typeCode = ORB.init().create_interface_tc(id(), "DynFixed");
/*    */     }
/* 41 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 46 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynFixed read(InputStream paramInputStream) {
/* 51 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DynFixed paramDynFixed) {
/* 56 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynFixed narrow(Object paramObject) {
/* 61 */     if (paramObject == null)
/* 62 */       return null; 
/* 63 */     if (paramObject instanceof DynFixed)
/* 64 */       return (DynFixed)paramObject; 
/* 65 */     if (!paramObject._is_a(id())) {
/* 66 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 69 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 70 */     _DynFixedStub _DynFixedStub = new _DynFixedStub();
/* 71 */     _DynFixedStub._set_delegate(delegate);
/* 72 */     return _DynFixedStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DynFixed unchecked_narrow(Object paramObject) {
/* 78 */     if (paramObject == null)
/* 79 */       return null; 
/* 80 */     if (paramObject instanceof DynFixed) {
/* 81 */       return (DynFixed)paramObject;
/*    */     }
/*    */     
/* 84 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 85 */     _DynFixedStub _DynFixedStub = new _DynFixedStub();
/* 86 */     _DynFixedStub._set_delegate(delegate);
/* 87 */     return _DynFixedStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynFixedHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */