/*    */ package com.sun.org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
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
/*    */ public final class VersionSpecHelper
/*    */ {
/* 37 */   private static String _id = "IDL:omg.org/CORBA/VersionSpec:1.0";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, String paramString) {
/* 45 */     OutputStream outputStream = paramAny.create_output_stream();
/* 46 */     paramAny.type(type());
/* 47 */     write(outputStream, paramString);
/* 48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static String extract(Any paramAny) {
/* 53 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 56 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 59 */     if (__typeCode == null) {
/*    */       
/* 61 */       __typeCode = ORB.init().create_string_tc(0);
/* 62 */       __typeCode = ORB.init().create_alias_tc(id(), "VersionSpec", __typeCode);
/*    */     } 
/* 64 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 69 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String read(InputStream paramInputStream) {
/* 74 */     String str = null;
/* 75 */     str = paramInputStream.read_string();
/* 76 */     return str;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, String paramString) {
/* 81 */     paramOutputStream.write_string(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/VersionSpecHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */