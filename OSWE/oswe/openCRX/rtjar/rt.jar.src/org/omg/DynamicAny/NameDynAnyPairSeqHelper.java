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
/*    */ public abstract class NameDynAnyPairSeqHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/DynamicAny/NameDynAnyPairSeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, NameDynAnyPair[] paramArrayOfNameDynAnyPair) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramArrayOfNameDynAnyPair);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static NameDynAnyPair[] extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null) {
/*    */       
/* 33 */       __typeCode = NameDynAnyPairHelper.type();
/* 34 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 35 */       __typeCode = ORB.init().create_alias_tc(id(), "NameDynAnyPairSeq", __typeCode);
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
/*    */   public static NameDynAnyPair[] read(InputStream paramInputStream) {
/* 47 */     NameDynAnyPair[] arrayOfNameDynAnyPair = null;
/* 48 */     int i = paramInputStream.read_long();
/* 49 */     arrayOfNameDynAnyPair = new NameDynAnyPair[i];
/* 50 */     for (byte b = 0; b < arrayOfNameDynAnyPair.length; b++)
/* 51 */       arrayOfNameDynAnyPair[b] = NameDynAnyPairHelper.read(paramInputStream); 
/* 52 */     return arrayOfNameDynAnyPair;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, NameDynAnyPair[] paramArrayOfNameDynAnyPair) {
/* 57 */     paramOutputStream.write_long(paramArrayOfNameDynAnyPair.length);
/* 58 */     for (byte b = 0; b < paramArrayOfNameDynAnyPair.length; b++)
/* 59 */       NameDynAnyPairHelper.write(paramOutputStream, paramArrayOfNameDynAnyPair[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/NameDynAnyPairSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */