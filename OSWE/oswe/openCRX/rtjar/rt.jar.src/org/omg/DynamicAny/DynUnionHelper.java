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
/*    */ public abstract class DynUnionHelper
/*    */ {
/* 24 */   private static String _id = "IDL:omg.org/DynamicAny/DynUnion:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DynUnion paramDynUnion) {
/* 28 */     OutputStream outputStream = paramAny.create_output_stream();
/* 29 */     paramAny.type(type());
/* 30 */     write(outputStream, paramDynUnion);
/* 31 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynUnion extract(Any paramAny) {
/* 36 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 39 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 42 */     if (__typeCode == null)
/*    */     {
/* 44 */       __typeCode = ORB.init().create_interface_tc(id(), "DynUnion");
/*    */     }
/* 46 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 51 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynUnion read(InputStream paramInputStream) {
/* 56 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DynUnion paramDynUnion) {
/* 61 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynUnion narrow(Object paramObject) {
/* 66 */     if (paramObject == null)
/* 67 */       return null; 
/* 68 */     if (paramObject instanceof DynUnion)
/* 69 */       return (DynUnion)paramObject; 
/* 70 */     if (!paramObject._is_a(id())) {
/* 71 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 74 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 75 */     _DynUnionStub _DynUnionStub = new _DynUnionStub();
/* 76 */     _DynUnionStub._set_delegate(delegate);
/* 77 */     return _DynUnionStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DynUnion unchecked_narrow(Object paramObject) {
/* 83 */     if (paramObject == null)
/* 84 */       return null; 
/* 85 */     if (paramObject instanceof DynUnion) {
/* 86 */       return (DynUnion)paramObject;
/*    */     }
/*    */     
/* 89 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 90 */     _DynUnionStub _DynUnionStub = new _DynUnionStub();
/* 91 */     _DynUnionStub._set_delegate(delegate);
/* 92 */     return _DynUnionStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynUnionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */