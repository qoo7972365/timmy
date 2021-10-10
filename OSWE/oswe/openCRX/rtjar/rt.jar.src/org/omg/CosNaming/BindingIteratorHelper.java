/*    */ package org.omg.CosNaming;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.BAD_PARAM;
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
/*    */ public abstract class BindingIteratorHelper
/*    */ {
/* 22 */   private static String _id = "IDL:omg.org/CosNaming/BindingIterator:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, BindingIterator paramBindingIterator) {
/* 26 */     OutputStream outputStream = paramAny.create_output_stream();
/* 27 */     paramAny.type(type());
/* 28 */     write(outputStream, paramBindingIterator);
/* 29 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static BindingIterator extract(Any paramAny) {
/* 34 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 37 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 40 */     if (__typeCode == null)
/*    */     {
/* 42 */       __typeCode = ORB.init().create_interface_tc(id(), "BindingIterator");
/*    */     }
/* 44 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 49 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BindingIterator read(InputStream paramInputStream) {
/* 54 */     return narrow(paramInputStream.read_Object(_BindingIteratorStub.class));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, BindingIterator paramBindingIterator) {
/* 59 */     paramOutputStream.write_Object(paramBindingIterator);
/*    */   }
/*    */ 
/*    */   
/*    */   public static BindingIterator narrow(Object paramObject) {
/* 64 */     if (paramObject == null)
/* 65 */       return null; 
/* 66 */     if (paramObject instanceof BindingIterator)
/* 67 */       return (BindingIterator)paramObject; 
/* 68 */     if (!paramObject._is_a(id())) {
/* 69 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 72 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 73 */     _BindingIteratorStub _BindingIteratorStub = new _BindingIteratorStub();
/* 74 */     _BindingIteratorStub._set_delegate(delegate);
/* 75 */     return _BindingIteratorStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static BindingIterator unchecked_narrow(Object paramObject) {
/* 81 */     if (paramObject == null)
/* 82 */       return null; 
/* 83 */     if (paramObject instanceof BindingIterator) {
/* 84 */       return (BindingIterator)paramObject;
/*    */     }
/*    */     
/* 87 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 88 */     _BindingIteratorStub _BindingIteratorStub = new _BindingIteratorStub();
/* 89 */     _BindingIteratorStub._set_delegate(delegate);
/* 90 */     return _BindingIteratorStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/BindingIteratorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */