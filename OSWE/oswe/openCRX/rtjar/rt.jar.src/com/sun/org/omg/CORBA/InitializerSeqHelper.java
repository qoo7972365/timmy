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
/*    */ public final class InitializerSeqHelper
/*    */ {
/* 37 */   private static String _id = "IDL:omg.org/CORBA/InitializerSeq:1.0";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Initializer[] paramArrayOfInitializer) {
/* 45 */     OutputStream outputStream = paramAny.create_output_stream();
/* 46 */     paramAny.type(type());
/* 47 */     write(outputStream, paramArrayOfInitializer);
/* 48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Initializer[] extract(Any paramAny) {
/* 53 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 56 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 59 */     if (__typeCode == null) {
/*    */       
/* 61 */       __typeCode = InitializerHelper.type();
/* 62 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 63 */       __typeCode = ORB.init().create_alias_tc(id(), "InitializerSeq", __typeCode);
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
/*    */   public static Initializer[] read(InputStream paramInputStream) {
/* 75 */     Initializer[] arrayOfInitializer = null;
/* 76 */     int i = paramInputStream.read_long();
/* 77 */     arrayOfInitializer = new Initializer[i];
/* 78 */     for (byte b = 0; b < arrayOfInitializer.length; b++)
/* 79 */       arrayOfInitializer[b] = InitializerHelper.read(paramInputStream); 
/* 80 */     return arrayOfInitializer;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Initializer[] paramArrayOfInitializer) {
/* 85 */     paramOutputStream.write_long(paramArrayOfInitializer.length);
/* 86 */     for (byte b = 0; b < paramArrayOfInitializer.length; b++)
/* 87 */       InitializerHelper.write(paramOutputStream, paramArrayOfInitializer[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/InitializerSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */