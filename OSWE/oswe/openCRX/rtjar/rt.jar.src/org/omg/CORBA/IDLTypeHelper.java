/*    */ package org.omg.CORBA;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class IDLTypeHelper
/*    */ {
/* 40 */   private static String _id = "IDL:omg.org/CORBA/IDLType:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, IDLType paramIDLType) {
/* 44 */     OutputStream outputStream = paramAny.create_output_stream();
/* 45 */     paramAny.type(type());
/* 46 */     write(outputStream, paramIDLType);
/* 47 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static IDLType extract(Any paramAny) {
/* 52 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 55 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 58 */     if (__typeCode == null)
/*    */     {
/* 60 */       __typeCode = ORB.init().create_interface_tc(id(), "IDLType");
/*    */     }
/* 62 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 67 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static IDLType read(InputStream paramInputStream) {
/* 72 */     return narrow(paramInputStream.read_Object(_IDLTypeStub.class));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, IDLType paramIDLType) {
/* 77 */     paramOutputStream.write_Object(paramIDLType);
/*    */   }
/*    */ 
/*    */   
/*    */   public static IDLType narrow(Object paramObject) {
/* 82 */     if (paramObject == null)
/* 83 */       return null; 
/* 84 */     if (paramObject instanceof IDLType)
/* 85 */       return (IDLType)paramObject; 
/* 86 */     if (!paramObject._is_a(id())) {
/* 87 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 90 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 91 */     return new _IDLTypeStub(delegate);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/IDLTypeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */