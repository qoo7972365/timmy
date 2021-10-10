/*    */ package org.omg.IOP;
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
/*    */ public abstract class ComponentIdHelper
/*    */ {
/* 18 */   private static String _id = "IDL:omg.org/IOP/ComponentId:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, int paramInt) {
/* 22 */     OutputStream outputStream = paramAny.create_output_stream();
/* 23 */     paramAny.type(type());
/* 24 */     write(outputStream, paramInt);
/* 25 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int extract(Any paramAny) {
/* 30 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 33 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 36 */     if (__typeCode == null) {
/*    */       
/* 38 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_ulong);
/* 39 */       __typeCode = ORB.init().create_alias_tc(id(), "ComponentId", __typeCode);
/*    */     } 
/* 41 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 46 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int read(InputStream paramInputStream) {
/* 51 */     int i = 0;
/* 52 */     i = paramInputStream.read_ulong();
/* 53 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, int paramInt) {
/* 58 */     paramOutputStream.write_ulong(paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/ComponentIdHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */