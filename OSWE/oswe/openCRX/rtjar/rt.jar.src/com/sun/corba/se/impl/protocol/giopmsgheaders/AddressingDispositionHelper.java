/*    */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.TCKind;
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
/*    */ public abstract class AddressingDispositionHelper
/*    */ {
/* 38 */   private static String _id = "IDL:messages/AddressingDisposition:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, short paramShort) {
/* 42 */     OutputStream outputStream = paramAny.create_output_stream();
/* 43 */     paramAny.type(type());
/* 44 */     write(outputStream, paramShort);
/* 45 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static short extract(Any paramAny) {
/* 50 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 53 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 56 */     if (__typeCode == null) {
/*    */       
/* 58 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
/* 59 */       __typeCode = ORB.init().create_alias_tc(id(), "AddressingDisposition", __typeCode);
/*    */     } 
/* 61 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 66 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static short read(InputStream paramInputStream) {
/* 71 */     short s = 0;
/* 72 */     s = paramInputStream.read_short();
/* 73 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, short paramShort) {
/* 78 */     paramOutputStream.write_short(paramShort);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/AddressingDispositionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */