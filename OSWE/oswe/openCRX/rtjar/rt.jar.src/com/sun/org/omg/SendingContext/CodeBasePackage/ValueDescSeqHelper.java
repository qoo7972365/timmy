/*    */ package com.sun.org.omg.SendingContext.CodeBasePackage;
/*    */ 
/*    */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
/*    */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
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
/*    */ public final class ValueDescSeqHelper
/*    */ {
/* 37 */   private static String _id = "IDL:omg.org/SendingContext/CodeBase/ValueDescSeq:1.0";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, FullValueDescription[] paramArrayOfFullValueDescription) {
/* 45 */     OutputStream outputStream = paramAny.create_output_stream();
/* 46 */     paramAny.type(type());
/* 47 */     write(outputStream, paramArrayOfFullValueDescription);
/* 48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static FullValueDescription[] extract(Any paramAny) {
/* 53 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 56 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 59 */     if (__typeCode == null) {
/*    */       
/* 61 */       __typeCode = FullValueDescriptionHelper.type();
/* 62 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 63 */       __typeCode = ORB.init().create_alias_tc(id(), "ValueDescSeq", __typeCode);
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
/*    */   public static FullValueDescription[] read(InputStream paramInputStream) {
/* 75 */     FullValueDescription[] arrayOfFullValueDescription = null;
/* 76 */     int i = paramInputStream.read_long();
/* 77 */     arrayOfFullValueDescription = new FullValueDescription[i];
/* 78 */     for (byte b = 0; b < arrayOfFullValueDescription.length; b++)
/* 79 */       arrayOfFullValueDescription[b] = FullValueDescriptionHelper.read(paramInputStream); 
/* 80 */     return arrayOfFullValueDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, FullValueDescription[] paramArrayOfFullValueDescription) {
/* 85 */     paramOutputStream.write_long(paramArrayOfFullValueDescription.length);
/* 86 */     for (byte b = 0; b < paramArrayOfFullValueDescription.length; b++)
/* 87 */       FullValueDescriptionHelper.write(paramOutputStream, paramArrayOfFullValueDescription[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/SendingContext/CodeBasePackage/ValueDescSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */