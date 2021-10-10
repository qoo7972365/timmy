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
/*    */ public final class RepositoryHelper
/*    */ {
/* 29 */   private static String _id = "IDL:com.sun.omg.org/CORBA/Repository:3.0";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Repository paramRepository) {
/* 37 */     OutputStream outputStream = paramAny.create_output_stream();
/* 38 */     paramAny.type(type());
/* 39 */     write(outputStream, paramRepository);
/* 40 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Repository extract(Any paramAny) {
/* 45 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 48 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 51 */     if (__typeCode == null) {
/*    */       
/* 53 */       __typeCode = ORB.init().create_string_tc(0);
/* 54 */       __typeCode = ORB.init().create_alias_tc(id(), "Repository", __typeCode);
/*    */     } 
/* 56 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 61 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Repository read(InputStream paramInputStream) {
/* 66 */     String str = null;
/* 67 */     str = paramInputStream.read_string();
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Repository paramRepository) {
/* 73 */     paramOutputStream.write_string(null);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/RepositoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */