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
/*    */ public abstract class ServerIdsHelper
/*    */ {
/* 13 */   private static String _id = "IDL:activation/ServerIds:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, int[] paramArrayOfint) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramArrayOfint);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int[] extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null) {
/*    */       
/* 33 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_long);
/* 34 */       __typeCode = ORB.init().create_alias_tc(ServerIdHelper.id(), "ServerId", __typeCode);
/* 35 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 36 */       __typeCode = ORB.init().create_alias_tc(id(), "ServerIds", __typeCode);
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
/*    */   public static int[] read(InputStream paramInputStream) {
/* 48 */     int[] arrayOfInt = null;
/* 49 */     int i = paramInputStream.read_long();
/* 50 */     arrayOfInt = new int[i];
/* 51 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 52 */       arrayOfInt[b] = ServerIdHelper.read(paramInputStream); 
/* 53 */     return arrayOfInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, int[] paramArrayOfint) {
/* 58 */     paramOutputStream.write_long(paramArrayOfint.length);
/* 59 */     for (byte b = 0; b < paramArrayOfint.length; b++)
/* 60 */       ServerIdHelper.write(paramOutputStream, paramArrayOfint[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerIdsHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */