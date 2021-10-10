/*    */ package org.omg.PortableInterceptor;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.OctetSeqHelper;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ObjectIdHelper
/*    */ {
/* 17 */   private static String _id = "IDL:omg.org/PortableInterceptor/ObjectId:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, byte[] paramArrayOfbyte) {
/* 21 */     OutputStream outputStream = paramAny.create_output_stream();
/* 22 */     paramAny.type(type());
/* 23 */     write(outputStream, paramArrayOfbyte);
/* 24 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] extract(Any paramAny) {
/* 29 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 32 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 35 */     if (__typeCode == null) {
/*    */       
/* 37 */       __typeCode = ORB.init().get_primitive_tc(TCKind.tk_octet);
/* 38 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 39 */       __typeCode = ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", __typeCode);
/* 40 */       __typeCode = ORB.init().create_alias_tc(id(), "ObjectId", __typeCode);
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
/*    */   public static byte[] read(InputStream paramInputStream) {
/* 52 */     byte[] arrayOfByte = null;
/* 53 */     arrayOfByte = OctetSeqHelper.read(paramInputStream);
/* 54 */     return arrayOfByte;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, byte[] paramArrayOfbyte) {
/* 59 */     OctetSeqHelper.write(paramOutputStream, paramArrayOfbyte);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ObjectIdHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */