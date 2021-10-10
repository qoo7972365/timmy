/*    */ package org.omg.PortableInterceptor;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.BAD_PARAM;
/*    */ import org.omg.CORBA.MARSHAL;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
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
/*    */ public abstract class CurrentHelper
/*    */ {
/* 31 */   private static String _id = "IDL:omg.org/PortableInterceptor/Current:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Current paramCurrent) {
/* 35 */     OutputStream outputStream = paramAny.create_output_stream();
/* 36 */     paramAny.type(type());
/* 37 */     write(outputStream, paramCurrent);
/* 38 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Current extract(Any paramAny) {
/* 43 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 46 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 49 */     if (__typeCode == null)
/*    */     {
/* 51 */       __typeCode = ORB.init().create_interface_tc(id(), "Current");
/*    */     }
/* 53 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 58 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Current read(InputStream paramInputStream) {
/* 63 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Current paramCurrent) {
/* 68 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Current narrow(Object paramObject) {
/* 73 */     if (paramObject == null)
/* 74 */       return null; 
/* 75 */     if (paramObject instanceof Current) {
/* 76 */       return (Current)paramObject;
/*    */     }
/* 78 */     throw new BAD_PARAM();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Current unchecked_narrow(Object paramObject) {
/* 83 */     if (paramObject == null)
/* 84 */       return null; 
/* 85 */     if (paramObject instanceof Current) {
/* 86 */       return (Current)paramObject;
/*    */     }
/* 88 */     throw new BAD_PARAM();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/CurrentHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */