/*    */ package com.sun.corba.se.spi.activation;
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
/*    */ public abstract class ActivatorHelper {
/* 13 */   private static String _id = "IDL:activation/Activator:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Activator paramActivator) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramActivator);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Activator extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null)
/*    */     {
/* 33 */       __typeCode = ORB.init().create_interface_tc(id(), "Activator");
/*    */     }
/* 35 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 40 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Activator read(InputStream paramInputStream) {
/* 45 */     return narrow(paramInputStream.read_Object(_ActivatorStub.class));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Activator paramActivator) {
/* 50 */     paramOutputStream.write_Object(paramActivator);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Activator narrow(Object paramObject) {
/* 55 */     if (paramObject == null)
/* 56 */       return null; 
/* 57 */     if (paramObject instanceof Activator)
/* 58 */       return (Activator)paramObject; 
/* 59 */     if (!paramObject._is_a(id())) {
/* 60 */       throw new BAD_PARAM();
/*    */     }
/*    */     
/* 63 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 64 */     _ActivatorStub _ActivatorStub = new _ActivatorStub();
/* 65 */     _ActivatorStub._set_delegate(delegate);
/* 66 */     return _ActivatorStub;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Activator unchecked_narrow(Object paramObject) {
/* 72 */     if (paramObject == null)
/* 73 */       return null; 
/* 74 */     if (paramObject instanceof Activator) {
/* 75 */       return (Activator)paramObject;
/*    */     }
/*    */     
/* 78 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/* 79 */     _ActivatorStub _ActivatorStub = new _ActivatorStub();
/* 80 */     _ActivatorStub._set_delegate(delegate);
/* 81 */     return _ActivatorStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ActivatorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */