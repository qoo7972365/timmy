/*    */ package org.omg.DynamicAny;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NameValuePairSeqHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/DynamicAny/NameValuePairSeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, NameValuePair[] paramArrayOfNameValuePair) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramArrayOfNameValuePair);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static NameValuePair[] extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null) {
/*    */       
/* 33 */       __typeCode = NameValuePairHelper.type();
/* 34 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 35 */       __typeCode = ORB.init().create_alias_tc(id(), "NameValuePairSeq", __typeCode);
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
/*    */   public static NameValuePair[] read(InputStream paramInputStream) {
/* 47 */     NameValuePair[] arrayOfNameValuePair = null;
/* 48 */     int i = paramInputStream.read_long();
/* 49 */     arrayOfNameValuePair = new NameValuePair[i];
/* 50 */     for (byte b = 0; b < arrayOfNameValuePair.length; b++)
/* 51 */       arrayOfNameValuePair[b] = NameValuePairHelper.read(paramInputStream); 
/* 52 */     return arrayOfNameValuePair;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, NameValuePair[] paramArrayOfNameValuePair) {
/* 57 */     paramOutputStream.write_long(paramArrayOfNameValuePair.length);
/* 58 */     for (byte b = 0; b < paramArrayOfNameValuePair.length; b++)
/* 59 */       NameValuePairHelper.write(paramOutputStream, paramArrayOfNameValuePair[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/NameValuePairSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */