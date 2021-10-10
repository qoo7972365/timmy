/*    */ package org.omg.PortableInterceptor;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StringSeqHelper;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AdapterNameHelper
/*    */ {
/* 17 */   private static String _id = "IDL:omg.org/PortableInterceptor/AdapterName:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, String[] paramArrayOfString) {
/* 21 */     OutputStream outputStream = paramAny.create_output_stream();
/* 22 */     paramAny.type(type());
/* 23 */     write(outputStream, paramArrayOfString);
/* 24 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] extract(Any paramAny) {
/* 29 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 32 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 35 */     if (__typeCode == null) {
/*    */       
/* 37 */       __typeCode = ORB.init().create_string_tc(0);
/* 38 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 39 */       __typeCode = ORB.init().create_alias_tc(StringSeqHelper.id(), "StringSeq", __typeCode);
/* 40 */       __typeCode = ORB.init().create_alias_tc(id(), "AdapterName", __typeCode);
/*    */     } 
/* 42 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 47 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] read(InputStream paramInputStream) {
/* 52 */     String[] arrayOfString = null;
/* 53 */     arrayOfString = StringSeqHelper.read(paramInputStream);
/* 54 */     return arrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, String[] paramArrayOfString) {
/* 59 */     StringSeqHelper.write(paramOutputStream, paramArrayOfString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/AdapterNameHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */