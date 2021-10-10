/*    */ package com.sun.corba.se.spi.activation;
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
/*    */ public abstract class ServerHelper
/*    */ {
/* 16 */   private static String _id = "IDL:activation/Server:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Server paramServer) {
/* 20 */     OutputStream outputStream = paramAny.create_output_stream();
/* 21 */     paramAny.type(type());
/* 22 */     write(outputStream, paramServer);
/* 23 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Server extract(Any paramAny) {
/* 28 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 31 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 34 */     if (__typeCode == null)
/*    */     {
/* 36 */       __typeCode = ORB.init().create_interface_tc(id(), "Server");
/*    */     }
/* 38 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 43 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Server read(InputStream paramInputStream) {
/* 48 */     return narrow(paramInputStream.read_Object(_ServerStub.class));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Server paramServer) {
/* 53 */     paramOutputStream.write_Object(paramServer);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Server narrow(Object paramObject) {
/* 58 */     if (paramObject == null)
/* 59 */       return null; 
/* 60 */     if (paramObject instanceof Server)
/* 61 */       return (Server)paramObject; 
/* 62 */     if (!paramObject._is_a(id())) {
/* 63 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 66 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 67 */     _ServerStub _ServerStub = new _ServerStub();
/* 68 */     _ServerStub._set_delegate(delegate);
/* 69 */     return _ServerStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Server unchecked_narrow(Object paramObject) {
/* 75 */     if (paramObject == null)
/* 76 */       return null; 
/* 77 */     if (paramObject instanceof Server) {
/* 78 */       return (Server)paramObject;
/*    */     }
/*    */     
/* 81 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 82 */     _ServerStub _ServerStub = new _ServerStub();
/* 83 */     _ServerStub._set_delegate(delegate);
/* 84 */     return _ServerStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */