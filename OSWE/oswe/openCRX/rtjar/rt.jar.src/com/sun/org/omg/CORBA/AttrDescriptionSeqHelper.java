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
/*    */ public final class AttrDescriptionSeqHelper
/*    */ {
/* 37 */   private static String _id = "IDL:omg.org/CORBA/AttrDescriptionSeq:1.0";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, AttributeDescription[] paramArrayOfAttributeDescription) {
/* 45 */     OutputStream outputStream = paramAny.create_output_stream();
/* 46 */     paramAny.type(type());
/* 47 */     write(outputStream, paramArrayOfAttributeDescription);
/* 48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static AttributeDescription[] extract(Any paramAny) {
/* 53 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 56 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 59 */     if (__typeCode == null) {
/*    */       
/* 61 */       __typeCode = AttributeDescriptionHelper.type();
/* 62 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 63 */       __typeCode = ORB.init().create_alias_tc(id(), "AttrDescriptionSeq", __typeCode);
/*    */     } 
/* 65 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 70 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static AttributeDescription[] read(InputStream paramInputStream) {
/* 75 */     AttributeDescription[] arrayOfAttributeDescription = null;
/* 76 */     int i = paramInputStream.read_long();
/* 77 */     arrayOfAttributeDescription = new AttributeDescription[i];
/* 78 */     for (byte b = 0; b < arrayOfAttributeDescription.length; b++)
/* 79 */       arrayOfAttributeDescription[b] = AttributeDescriptionHelper.read(paramInputStream); 
/* 80 */     return arrayOfAttributeDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, AttributeDescription[] paramArrayOfAttributeDescription) {
/* 85 */     paramOutputStream.write_long(paramArrayOfAttributeDescription.length);
/* 86 */     for (byte b = 0; b < paramArrayOfAttributeDescription.length; b++)
/* 87 */       AttributeDescriptionHelper.write(paramOutputStream, paramArrayOfAttributeDescription[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/AttrDescriptionSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */