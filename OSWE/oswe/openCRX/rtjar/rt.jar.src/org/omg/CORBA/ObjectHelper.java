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
/*    */ public abstract class ObjectHelper
/*    */ {
/* 43 */   private static String _id = "";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Object paramObject) {
/* 47 */     OutputStream outputStream = paramAny.create_output_stream();
/* 48 */     paramAny.type(type());
/* 49 */     write(outputStream, paramObject);
/* 50 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Object extract(Any paramAny) {
/* 55 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 58 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 61 */     if (__typeCode == null)
/*    */     {
/* 63 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_objref);
/*    */     }
/* 65 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 70 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Object read(InputStream paramInputStream) {
/* 75 */     return paramInputStream.read_Object();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Object paramObject) {
/* 80 */     paramOutputStream.write_Object(paramObject);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ObjectHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */