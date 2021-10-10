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
/*    */ public abstract class DoubleSeqHelper
/*    */ {
/* 52 */   private static String _id = "IDL:omg.org/CORBA/DoubleSeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, double[] paramArrayOfdouble) {
/* 56 */     OutputStream outputStream = paramAny.create_output_stream();
/* 57 */     paramAny.type(type());
/* 58 */     write(outputStream, paramArrayOfdouble);
/* 59 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static double[] extract(Any paramAny) {
/* 64 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 67 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 70 */     if (__typeCode == null) {
/*    */       
/* 72 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_double);
/* 73 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 74 */       __typeCode = ORB.init().create_alias_tc(id(), "DoubleSeq", __typeCode);
/*    */     } 
/* 76 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 81 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static double[] read(InputStream paramInputStream) {
/* 86 */     double[] arrayOfDouble = null;
/* 87 */     int i = paramInputStream.read_long();
/* 88 */     arrayOfDouble = new double[i];
/* 89 */     paramInputStream.read_double_array(arrayOfDouble, 0, i);
/* 90 */     return arrayOfDouble;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, double[] paramArrayOfdouble) {
/* 95 */     paramOutputStream.write_long(paramArrayOfdouble.length);
/* 96 */     paramOutputStream.write_double_array(paramArrayOfdouble, 0, paramArrayOfdouble.length);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/DoubleSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */