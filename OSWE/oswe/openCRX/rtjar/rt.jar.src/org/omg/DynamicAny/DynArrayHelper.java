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
/*    */ public abstract class DynArrayHelper
/*    */ {
/* 19 */   private static String _id = "IDL:omg.org/DynamicAny/DynArray:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DynArray paramDynArray) {
/* 23 */     OutputStream outputStream = paramAny.create_output_stream();
/* 24 */     paramAny.type(type());
/* 25 */     write(outputStream, paramDynArray);
/* 26 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynArray extract(Any paramAny) {
/* 31 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 34 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 37 */     if (__typeCode == null)
/*    */     {
/* 39 */       __typeCode = ORB.init().create_interface_tc(id(), "DynArray");
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
/*    */   public static DynArray read(InputStream paramInputStream) {
/* 51 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DynArray paramDynArray) {
/* 56 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynArray narrow(Object paramObject) {
/* 61 */     if (paramObject == null)
/* 62 */       return null; 
/* 63 */     if (paramObject instanceof DynArray)
/* 64 */       return (DynArray)paramObject; 
/* 65 */     if (!paramObject._is_a(id())) {
/* 66 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 69 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 70 */     _DynArrayStub _DynArrayStub = new _DynArrayStub();
/* 71 */     _DynArrayStub._set_delegate(delegate);
/* 72 */     return _DynArrayStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DynArray unchecked_narrow(Object paramObject) {
/* 78 */     if (paramObject == null)
/* 79 */       return null; 
/* 80 */     if (paramObject instanceof DynArray) {
/* 81 */       return (DynArray)paramObject;
/*    */     }
/*    */     
/* 84 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 85 */     _DynArrayStub _DynArrayStub = new _DynArrayStub();
/* 86 */     _DynArrayStub._set_delegate(delegate);
/* 87 */     return _DynArrayStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynArrayHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */