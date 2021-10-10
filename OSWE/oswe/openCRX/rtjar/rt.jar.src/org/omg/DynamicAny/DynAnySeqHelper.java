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
/*    */ public abstract class DynAnySeqHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/DynamicAny/DynAnySeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DynAny[] paramArrayOfDynAny) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramArrayOfDynAny);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DynAny[] extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 31 */     if (__typeCode == null) {
/*    */       
/* 33 */       __typeCode = DynAnyHelper.type();
/* 34 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 35 */       __typeCode = ORB.init().create_alias_tc(id(), "DynAnySeq", __typeCode);
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
/*    */   public static DynAny[] read(InputStream paramInputStream) {
/* 47 */     DynAny[] arrayOfDynAny = null;
/* 48 */     int i = paramInputStream.read_long();
/* 49 */     arrayOfDynAny = new DynAny[i];
/* 50 */     for (byte b = 0; b < arrayOfDynAny.length; b++)
/* 51 */       arrayOfDynAny[b] = DynAnyHelper.read(paramInputStream); 
/* 52 */     return arrayOfDynAny;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DynAny[] paramArrayOfDynAny) {
/* 57 */     paramOutputStream.write_long(paramArrayOfDynAny.length);
/* 58 */     for (byte b = 0; b < paramArrayOfDynAny.length; b++)
/* 59 */       DynAnyHelper.write(paramOutputStream, paramArrayOfDynAny[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynAnySeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */