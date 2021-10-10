/*    */ package org.omg.PortableInterceptor;
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
/*    */ public abstract class ObjectReferenceTemplateSeqHelper
/*    */ {
/* 17 */   private static String _id = "IDL:omg.org/PortableInterceptor/ObjectReferenceTemplateSeq:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ObjectReferenceTemplate[] paramArrayOfObjectReferenceTemplate) {
/* 21 */     OutputStream outputStream = paramAny.create_output_stream();
/* 22 */     paramAny.type(type());
/* 23 */     write(outputStream, paramArrayOfObjectReferenceTemplate);
/* 24 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ObjectReferenceTemplate[] extract(Any paramAny) {
/* 29 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 32 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 35 */     if (__typeCode == null) {
/*    */       
/* 37 */       __typeCode = ObjectReferenceTemplateHelper.type();
/* 38 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 39 */       __typeCode = ORB.init().create_alias_tc(id(), "ObjectReferenceTemplateSeq", __typeCode);
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
/*    */   public static ObjectReferenceTemplate[] read(InputStream paramInputStream) {
/* 51 */     ObjectReferenceTemplate[] arrayOfObjectReferenceTemplate = null;
/* 52 */     int i = paramInputStream.read_long();
/* 53 */     arrayOfObjectReferenceTemplate = new ObjectReferenceTemplate[i];
/* 54 */     for (byte b = 0; b < arrayOfObjectReferenceTemplate.length; b++)
/* 55 */       arrayOfObjectReferenceTemplate[b] = ObjectReferenceTemplateHelper.read(paramInputStream); 
/* 56 */     return arrayOfObjectReferenceTemplate;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ObjectReferenceTemplate[] paramArrayOfObjectReferenceTemplate) {
/* 61 */     paramOutputStream.write_long(paramArrayOfObjectReferenceTemplate.length);
/* 62 */     for (byte b = 0; b < paramArrayOfObjectReferenceTemplate.length; b++)
/* 63 */       ObjectReferenceTemplateHelper.write(paramOutputStream, paramArrayOfObjectReferenceTemplate[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ObjectReferenceTemplateSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */