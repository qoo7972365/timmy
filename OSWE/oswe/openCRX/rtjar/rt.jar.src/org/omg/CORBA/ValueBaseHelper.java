/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ public abstract class ValueBaseHelper
/*    */ {
/* 45 */   private static String _id = "IDL:omg.org/CORBA/ValueBase:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Serializable paramSerializable) {
/* 49 */     OutputStream outputStream = paramAny.create_output_stream();
/* 50 */     paramAny.type(type());
/* 51 */     write(outputStream, paramSerializable);
/* 52 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Serializable extract(Any paramAny) {
/* 57 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 60 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 63 */     if (__typeCode == null)
/*    */     {
/* 65 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_value);
/*    */     }
/* 67 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 72 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Serializable read(InputStream paramInputStream) {
/* 77 */     return ((InputStream)paramInputStream).read_value();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Serializable paramSerializable) {
/* 82 */     ((OutputStream)paramOutputStream).write_value(paramSerializable);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ValueBaseHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */