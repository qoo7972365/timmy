/*    */ package org.omg.IOP;
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
/*    */ public abstract class CodecFactoryHelper
/*    */ {
/* 19 */   private static String _id = "IDL:omg.org/IOP/CodecFactory:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, CodecFactory paramCodecFactory) {
/* 23 */     OutputStream outputStream = paramAny.create_output_stream();
/* 24 */     paramAny.type(type());
/* 25 */     write(outputStream, paramCodecFactory);
/* 26 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static CodecFactory extract(Any paramAny) {
/* 31 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 34 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 37 */     if (__typeCode == null)
/*    */     {
/* 39 */       __typeCode = ORB.init().create_interface_tc(id(), "CodecFactory");
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
/*    */   public static CodecFactory read(InputStream paramInputStream) {
/* 51 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, CodecFactory paramCodecFactory) {
/* 56 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static CodecFactory narrow(Object paramObject) {
/* 61 */     if (paramObject == null)
/* 62 */       return null; 
/* 63 */     if (paramObject instanceof CodecFactory) {
/* 64 */       return (CodecFactory)paramObject;
/*    */     }
/* 66 */     throw new BAD_PARAM();
/*    */   }
/*    */ 
/*    */   
/*    */   public static CodecFactory unchecked_narrow(Object paramObject) {
/* 71 */     if (paramObject == null)
/* 72 */       return null; 
/* 73 */     if (paramObject instanceof CodecFactory) {
/* 74 */       return (CodecFactory)paramObject;
/*    */     }
/* 76 */     throw new BAD_PARAM();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/CodecFactoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */