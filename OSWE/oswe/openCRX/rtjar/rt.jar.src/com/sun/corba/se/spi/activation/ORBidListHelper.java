/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ORBidListHelper
/*    */ {
/* 13 */   private static String _id = "IDL:activation/ORBidList:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, String[] paramArrayOfString) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramArrayOfString);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null) {
/*    */       
/* 33 */       __typeCode = ORB.init().create_string_tc(0);
/* 34 */       __typeCode = ORB.init().create_alias_tc(ORBidHelper.id(), "ORBid", __typeCode);
/* 35 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 36 */       __typeCode = ORB.init().create_alias_tc(id(), "ORBidList", __typeCode);
/*    */     } 
/* 38 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 43 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] read(InputStream paramInputStream) {
/* 48 */     String[] arrayOfString = null;
/* 49 */     int i = paramInputStream.read_long();
/* 50 */     arrayOfString = new String[i];
/* 51 */     for (byte b = 0; b < arrayOfString.length; b++)
/* 52 */       arrayOfString[b] = ORBidHelper.read(paramInputStream); 
/* 53 */     return arrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, String[] paramArrayOfString) {
/* 58 */     paramOutputStream.write_long(paramArrayOfString.length);
/* 59 */     for (byte b = 0; b < paramArrayOfString.length; b++)
/* 60 */       ORBidHelper.write(paramOutputStream, paramArrayOfString[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ORBidListHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */