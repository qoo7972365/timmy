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
/*    */ public abstract class ShortSeqHelper
/*    */ {
/* 52 */   private static String _id = "IDL:omg.org/CORBA/ShortSeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, short[] paramArrayOfshort) {
/* 56 */     OutputStream outputStream = paramAny.create_output_stream();
/* 57 */     paramAny.type(type());
/* 58 */     write(outputStream, paramArrayOfshort);
/* 59 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static short[] extract(Any paramAny) {
/* 64 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 67 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 70 */     if (__typeCode == null) {
/*    */       
/* 72 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
/* 73 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 74 */       __typeCode = ORB.init().create_alias_tc(id(), "ShortSeq", __typeCode);
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
/*    */   public static short[] read(InputStream paramInputStream) {
/* 86 */     short[] arrayOfShort = null;
/* 87 */     int i = paramInputStream.read_long();
/* 88 */     arrayOfShort = new short[i];
/* 89 */     paramInputStream.read_short_array(arrayOfShort, 0, i);
/* 90 */     return arrayOfShort;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, short[] paramArrayOfshort) {
/* 95 */     paramOutputStream.write_long(paramArrayOfshort.length);
/* 96 */     paramOutputStream.write_short_array(paramArrayOfshort, 0, paramArrayOfshort.length);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ShortSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */