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
/*    */ public abstract class PolicyTypeHelper
/*    */ {
/* 42 */   private static String _id = "IDL:omg.org/CORBA/PolicyType:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, int paramInt) {
/* 46 */     OutputStream outputStream = paramAny.create_output_stream();
/* 47 */     paramAny.type(type());
/* 48 */     write(outputStream, paramInt);
/* 49 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int extract(Any paramAny) {
/* 54 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 57 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 60 */     if (__typeCode == null) {
/*    */       
/* 62 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_ulong);
/* 63 */       __typeCode = ORB.init().create_alias_tc(id(), "PolicyType", __typeCode);
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
/*    */   public static int read(InputStream paramInputStream) {
/* 75 */     int i = 0;
/* 76 */     i = paramInputStream.read_ulong();
/* 77 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, int paramInt) {
/* 82 */     paramOutputStream.write_ulong(paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/PolicyTypeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */