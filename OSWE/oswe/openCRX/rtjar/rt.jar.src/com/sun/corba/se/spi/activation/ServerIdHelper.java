/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ public abstract class ServerIdHelper
/*    */ {
/* 13 */   private static String _id = "IDL:activation/ServerId:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, int paramInt) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramInt);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null) {
/*    */       
/* 33 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_long);
/* 34 */       __typeCode = ORB.init().create_alias_tc(id(), "ServerId", __typeCode);
/*    */     } 
/* 36 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 41 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int read(InputStream paramInputStream) {
/* 46 */     int i = 0;
/* 47 */     i = paramInputStream.read_long();
/* 48 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, int paramInt) {
/* 53 */     paramOutputStream.write_long(paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerIdHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */