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
/*    */ public abstract class VisibilityHelper
/*    */ {
/* 42 */   private static String _id = "IDL:omg.org/CORBA/Visibility:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, short paramShort) {
/* 46 */     OutputStream outputStream = paramAny.create_output_stream();
/* 47 */     paramAny.type(type());
/* 48 */     write(outputStream, paramShort);
/* 49 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static short extract(Any paramAny) {
/* 54 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 57 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 60 */     if (__typeCode == null) {
/*    */       
/* 62 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
/* 63 */       __typeCode = ORB.init().create_alias_tc(id(), "Visibility", __typeCode);
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
/*    */   public static short read(InputStream paramInputStream) {
/* 75 */     short s = 0;
/* 76 */     s = paramInputStream.read_short();
/* 77 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, short paramShort) {
/* 82 */     paramOutputStream.write_short(paramShort);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/VisibilityHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */