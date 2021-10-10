/*    */ package org.omg.CORBA;
/*    */ 
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
/*    */ public abstract class ParameterModeHelper
/*    */ {
/* 22 */   private static String _id = "IDL:omg.org/CORBA/ParameterMode:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ParameterMode paramParameterMode) {
/* 26 */     OutputStream outputStream = paramAny.create_output_stream();
/* 27 */     paramAny.type(type());
/* 28 */     write(outputStream, paramParameterMode);
/* 29 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ParameterMode extract(Any paramAny) {
/* 34 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 37 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 40 */     if (__typeCode == null)
/*    */     {
/* 42 */       __typeCode = ORB.init().create_enum_tc(id(), "ParameterMode", new String[] { "PARAM_IN", "PARAM_OUT", "PARAM_INOUT" });
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
/*    */   public static ParameterMode read(InputStream paramInputStream) {
/* 54 */     return ParameterMode.from_int(paramInputStream.read_long());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ParameterMode paramParameterMode) {
/* 59 */     paramOutputStream.write_long(paramParameterMode.value());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ParameterModeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */