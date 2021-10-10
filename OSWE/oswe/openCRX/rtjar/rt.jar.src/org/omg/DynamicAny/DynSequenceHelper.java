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
/*    */ public abstract class DynSequenceHelper
/*    */ {
/* 17 */   private static String _id = "IDL:omg.org/DynamicAny/DynSequence:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DynSequence paramDynSequence) {
/* 21 */     OutputStream outputStream = paramAny.create_output_stream();
/* 22 */     paramAny.type(type());
/* 23 */     write(outputStream, paramDynSequence);
/* 24 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynSequence extract(Any paramAny) {
/* 29 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 32 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 35 */     if (__typeCode == null)
/*    */     {
/* 37 */       __typeCode = ORB.init().create_interface_tc(id(), "DynSequence");
/*    */     }
/* 39 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 44 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynSequence read(InputStream paramInputStream) {
/* 49 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DynSequence paramDynSequence) {
/* 54 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynSequence narrow(Object paramObject) {
/* 59 */     if (paramObject == null)
/* 60 */       return null; 
/* 61 */     if (paramObject instanceof DynSequence)
/* 62 */       return (DynSequence)paramObject; 
/* 63 */     if (!paramObject._is_a(id())) {
/* 64 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 67 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 68 */     _DynSequenceStub _DynSequenceStub = new _DynSequenceStub();
/* 69 */     _DynSequenceStub._set_delegate(delegate);
/* 70 */     return _DynSequenceStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DynSequence unchecked_narrow(Object paramObject) {
/* 76 */     if (paramObject == null)
/* 77 */       return null; 
/* 78 */     if (paramObject instanceof DynSequence) {
/* 79 */       return (DynSequence)paramObject;
/*    */     }
/*    */     
/* 82 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 83 */     _DynSequenceStub _DynSequenceStub = new _DynSequenceStub();
/* 84 */     _DynSequenceStub._set_delegate(delegate);
/* 85 */     return _DynSequenceStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynSequenceHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */