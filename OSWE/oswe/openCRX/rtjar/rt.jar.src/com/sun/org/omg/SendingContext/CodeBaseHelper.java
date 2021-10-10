/*    */ package com.sun.org.omg.SendingContext;
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
/*    */ public final class CodeBaseHelper
/*    */ {
/* 37 */   private static String _id = "IDL:omg.org/SendingContext/CodeBase:1.0";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, CodeBase paramCodeBase) {
/* 45 */     OutputStream outputStream = paramAny.create_output_stream();
/* 46 */     paramAny.type(type());
/* 47 */     write(outputStream, paramCodeBase);
/* 48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static CodeBase extract(Any paramAny) {
/* 53 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 56 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 59 */     if (__typeCode == null)
/*    */     {
/* 61 */       __typeCode = ORB.init().create_interface_tc(id(), "CodeBase");
/*    */     }
/* 63 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 68 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static CodeBase read(InputStream paramInputStream) {
/* 73 */     return narrow(paramInputStream.read_Object(_CodeBaseStub.class));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, CodeBase paramCodeBase) {
/* 78 */     paramOutputStream.write_Object((Object)paramCodeBase);
/*    */   }
/*    */ 
/*    */   
/*    */   public static CodeBase narrow(Object paramObject) {
/* 83 */     if (paramObject == null)
/* 84 */       return null; 
/* 85 */     if (paramObject instanceof CodeBase)
/* 86 */       return (CodeBase)paramObject; 
/* 87 */     if (!paramObject._is_a(id())) {
/* 88 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 91 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 92 */     return new _CodeBaseStub(delegate);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/SendingContext/CodeBaseHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */