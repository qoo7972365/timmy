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
/*    */ public abstract class VersionSpecHelper
/*    */ {
/* 40 */   private static String _id = "IDL:omg.org/CORBA/VersionSpec:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, String paramString) {
/* 44 */     OutputStream outputStream = paramAny.create_output_stream();
/* 45 */     paramAny.type(type());
/* 46 */     write(outputStream, paramString);
/* 47 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static String extract(Any paramAny) {
/* 52 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 55 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 58 */     if (__typeCode == null) {
/*    */       
/* 60 */       __typeCode = ORB.init().create_string_tc(0);
/* 61 */       __typeCode = ORB.init().create_alias_tc(id(), "VersionSpec", __typeCode);
/*    */     } 
/* 63 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 68 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String read(InputStream paramInputStream) {
/* 73 */     String str = null;
/* 74 */     str = paramInputStream.read_string();
/* 75 */     return str;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, String paramString) {
/* 80 */     paramOutputStream.write_string(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/VersionSpecHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */