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
/*    */ public abstract class PolicyErrorCodeHelper
/*    */ {
/* 19 */   private static String _id = "IDL:omg.org/CORBA/PolicyErrorCode:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, short paramShort) {
/* 23 */     OutputStream outputStream = paramAny.create_output_stream();
/* 24 */     paramAny.type(type());
/* 25 */     write(outputStream, paramShort);
/* 26 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static short extract(Any paramAny) {
/* 31 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 34 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 37 */     if (__typeCode == null) {
/*    */       
/* 39 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
/* 40 */       __typeCode = ORB.init().create_alias_tc(id(), "PolicyErrorCode", __typeCode);
/*    */     } 
/* 42 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 47 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static short read(InputStream paramInputStream) {
/* 52 */     short s = 0;
/* 53 */     s = paramInputStream.read_short();
/* 54 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, short paramShort) {
/* 59 */     paramOutputStream.write_short(paramShort);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/PolicyErrorCodeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */