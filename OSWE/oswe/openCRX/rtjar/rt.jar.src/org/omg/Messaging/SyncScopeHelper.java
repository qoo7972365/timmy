/*    */ package org.omg.Messaging;
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
/*    */ public abstract class SyncScopeHelper
/*    */ {
/* 15 */   private static String _id = "IDL:omg.org/Messaging/SyncScope:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, short paramShort) {
/* 19 */     OutputStream outputStream = paramAny.create_output_stream();
/* 20 */     paramAny.type(type());
/* 21 */     write(outputStream, paramShort);
/* 22 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static short extract(Any paramAny) {
/* 27 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 30 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 33 */     if (__typeCode == null) {
/*    */       
/* 35 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
/* 36 */       __typeCode = ORB.init().create_alias_tc(id(), "SyncScope", __typeCode);
/*    */     } 
/* 38 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 43 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static short read(InputStream paramInputStream) {
/* 48 */     short s = 0;
/* 49 */     s = paramInputStream.read_short();
/* 50 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, short paramShort) {
/* 55 */     paramOutputStream.write_short(paramShort);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/Messaging/SyncScopeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */