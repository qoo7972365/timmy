/*    */ package org.omg.PortableInterceptor;
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
/*    */ public abstract class AdapterManagerIdHelper
/*    */ {
/* 20 */   private static String _id = "IDL:omg.org/PortableInterceptor/AdapterManagerId:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, int paramInt) {
/* 24 */     OutputStream outputStream = paramAny.create_output_stream();
/* 25 */     paramAny.type(type());
/* 26 */     write(outputStream, paramInt);
/* 27 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int extract(Any paramAny) {
/* 32 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 35 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 38 */     if (__typeCode == null) {
/*    */       
/* 40 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_long);
/* 41 */       __typeCode = ORB.init().create_alias_tc(id(), "AdapterManagerId", __typeCode);
/*    */     } 
/* 43 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 48 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int read(InputStream paramInputStream) {
/* 53 */     int i = 0;
/* 54 */     i = paramInputStream.read_long();
/* 55 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, int paramInt) {
/* 60 */     paramOutputStream.write_long(paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/AdapterManagerIdHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */