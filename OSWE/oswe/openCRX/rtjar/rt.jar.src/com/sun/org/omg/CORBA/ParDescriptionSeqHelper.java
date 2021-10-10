/*    */ package com.sun.org.omg.CORBA;
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
/*    */ public final class ParDescriptionSeqHelper
/*    */ {
/* 37 */   private static String _id = "IDL:omg.org/CORBA/ParDescriptionSeq:1.0";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ParameterDescription[] paramArrayOfParameterDescription) {
/* 45 */     OutputStream outputStream = paramAny.create_output_stream();
/* 46 */     paramAny.type(type());
/* 47 */     write(outputStream, paramArrayOfParameterDescription);
/* 48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ParameterDescription[] extract(Any paramAny) {
/* 53 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 56 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 59 */     if (__typeCode == null) {
/*    */       
/* 61 */       __typeCode = ParameterDescriptionHelper.type();
/* 62 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 63 */       __typeCode = ORB.init().create_alias_tc(id(), "ParDescriptionSeq", __typeCode);
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
/*    */   public static ParameterDescription[] read(InputStream paramInputStream) {
/* 75 */     ParameterDescription[] arrayOfParameterDescription = null;
/* 76 */     int i = paramInputStream.read_long();
/* 77 */     arrayOfParameterDescription = new ParameterDescription[i];
/* 78 */     for (byte b = 0; b < arrayOfParameterDescription.length; b++)
/* 79 */       arrayOfParameterDescription[b] = ParameterDescriptionHelper.read(paramInputStream); 
/* 80 */     return arrayOfParameterDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ParameterDescription[] paramArrayOfParameterDescription) {
/* 85 */     paramOutputStream.write_long(paramArrayOfParameterDescription.length);
/* 86 */     for (byte b = 0; b < paramArrayOfParameterDescription.length; b++)
/* 87 */       ParameterDescriptionHelper.write(paramOutputStream, paramArrayOfParameterDescription[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/ParDescriptionSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */