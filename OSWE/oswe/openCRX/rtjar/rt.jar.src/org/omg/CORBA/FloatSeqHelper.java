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
/*    */ public abstract class FloatSeqHelper
/*    */ {
/* 52 */   private static String _id = "IDL:omg.org/CORBA/FloatSeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, float[] paramArrayOffloat) {
/* 56 */     OutputStream outputStream = paramAny.create_output_stream();
/* 57 */     paramAny.type(type());
/* 58 */     write(outputStream, paramArrayOffloat);
/* 59 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static float[] extract(Any paramAny) {
/* 64 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 67 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 70 */     if (__typeCode == null) {
/*    */       
/* 72 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_float);
/* 73 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 74 */       __typeCode = ORB.init().create_alias_tc(id(), "FloatSeq", __typeCode);
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
/*    */   public static float[] read(InputStream paramInputStream) {
/* 86 */     float[] arrayOfFloat = null;
/* 87 */     int i = paramInputStream.read_long();
/* 88 */     arrayOfFloat = new float[i];
/* 89 */     paramInputStream.read_float_array(arrayOfFloat, 0, i);
/* 90 */     return arrayOfFloat;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, float[] paramArrayOffloat) {
/* 95 */     paramOutputStream.write_long(paramArrayOffloat.length);
/* 96 */     paramOutputStream.write_float_array(paramArrayOffloat, 0, paramArrayOffloat.length);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/FloatSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */