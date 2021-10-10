/*    */ package org.omg.CosNaming;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
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
/*    */ public abstract class BindingTypeHelper
/*    */ {
/* 18 */   private static String _id = "IDL:omg.org/CosNaming/BindingType:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, BindingType paramBindingType) {
/* 22 */     OutputStream outputStream = paramAny.create_output_stream();
/* 23 */     paramAny.type(type());
/* 24 */     write(outputStream, paramBindingType);
/* 25 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static BindingType extract(Any paramAny) {
/* 30 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 33 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 36 */     if (__typeCode == null)
/*    */     {
/* 38 */       __typeCode = ORB.init().create_enum_tc(id(), "BindingType", new String[] { "nobject", "ncontext" });
/*    */     }
/* 40 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 45 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BindingType read(InputStream paramInputStream) {
/* 50 */     return BindingType.from_int(paramInputStream.read_long());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, BindingType paramBindingType) {
/* 55 */     paramOutputStream.write_long(paramBindingType.value());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/BindingTypeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */