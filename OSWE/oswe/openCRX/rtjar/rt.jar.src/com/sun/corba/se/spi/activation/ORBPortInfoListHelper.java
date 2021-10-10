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
/*    */ public abstract class ORBPortInfoListHelper
/*    */ {
/* 13 */   private static String _id = "IDL:activation/ORBPortInfoList:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ORBPortInfo[] paramArrayOfORBPortInfo) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramArrayOfORBPortInfo);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ORBPortInfo[] extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null) {
/*    */       
/* 33 */       __typeCode = ORBPortInfoHelper.type();
/* 34 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 35 */       __typeCode = ORB.init().create_alias_tc(id(), "ORBPortInfoList", __typeCode);
/*    */     } 
/* 37 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 42 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ORBPortInfo[] read(InputStream paramInputStream) {
/* 47 */     ORBPortInfo[] arrayOfORBPortInfo = null;
/* 48 */     int i = paramInputStream.read_long();
/* 49 */     arrayOfORBPortInfo = new ORBPortInfo[i];
/* 50 */     for (byte b = 0; b < arrayOfORBPortInfo.length; b++)
/* 51 */       arrayOfORBPortInfo[b] = ORBPortInfoHelper.read(paramInputStream); 
/* 52 */     return arrayOfORBPortInfo;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ORBPortInfo[] paramArrayOfORBPortInfo) {
/* 57 */     paramOutputStream.write_long(paramArrayOfORBPortInfo.length);
/* 58 */     for (byte b = 0; b < paramArrayOfORBPortInfo.length; b++)
/* 59 */       ORBPortInfoHelper.write(paramOutputStream, paramArrayOfORBPortInfo[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ORBPortInfoListHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */