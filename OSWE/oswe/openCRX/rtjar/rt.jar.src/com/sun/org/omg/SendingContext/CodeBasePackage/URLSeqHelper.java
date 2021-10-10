/*    */ package com.sun.org.omg.SendingContext.CodeBasePackage;
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
/*    */ public final class URLSeqHelper
/*    */ {
/* 37 */   private static String _id = "IDL:omg.org/SendingContext/CodeBase/URLSeq:1.0";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, String[] paramArrayOfString) {
/* 45 */     OutputStream outputStream = paramAny.create_output_stream();
/* 46 */     paramAny.type(type());
/* 47 */     write(outputStream, paramArrayOfString);
/* 48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] extract(Any paramAny) {
/* 53 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 56 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 59 */     if (__typeCode == null) {
/*    */       
/* 61 */       __typeCode = ORB.init().create_string_tc(0);
/* 62 */       __typeCode = ORB.init().create_alias_tc(URLHelper.id(), "URL", __typeCode);
/* 63 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 64 */       __typeCode = ORB.init().create_alias_tc(id(), "URLSeq", __typeCode);
/*    */     } 
/* 66 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 71 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] read(InputStream paramInputStream) {
/* 76 */     String[] arrayOfString = null;
/* 77 */     int i = paramInputStream.read_long();
/* 78 */     arrayOfString = new String[i];
/* 79 */     for (byte b = 0; b < arrayOfString.length; b++)
/* 80 */       arrayOfString[b] = URLHelper.read(paramInputStream); 
/* 81 */     return arrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, String[] paramArrayOfString) {
/* 86 */     paramOutputStream.write_long(paramArrayOfString.length);
/* 87 */     for (byte b = 0; b < paramArrayOfString.length; b++)
/* 88 */       URLHelper.write(paramOutputStream, paramArrayOfString[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/SendingContext/CodeBasePackage/URLSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */