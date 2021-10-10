/*    */ package org.omg.IOP;
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
/*    */ public abstract class MultipleComponentProfileHelper
/*    */ {
/* 15 */   private static String _id = "IDL:omg.org/IOP/MultipleComponentProfile:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, TaggedComponent[] paramArrayOfTaggedComponent) {
/* 19 */     OutputStream outputStream = paramAny.create_output_stream();
/* 20 */     paramAny.type(type());
/* 21 */     write(outputStream, paramArrayOfTaggedComponent);
/* 22 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static TaggedComponent[] extract(Any paramAny) {
/* 27 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 30 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 33 */     if (__typeCode == null) {
/*    */       
/* 35 */       __typeCode = TaggedComponentHelper.type();
/* 36 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 37 */       __typeCode = ORB.init().create_alias_tc(id(), "MultipleComponentProfile", __typeCode);
/*    */     } 
/* 39 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 44 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static TaggedComponent[] read(InputStream paramInputStream) {
/* 49 */     TaggedComponent[] arrayOfTaggedComponent = null;
/* 50 */     int i = paramInputStream.read_long();
/* 51 */     arrayOfTaggedComponent = new TaggedComponent[i];
/* 52 */     for (byte b = 0; b < arrayOfTaggedComponent.length; b++)
/* 53 */       arrayOfTaggedComponent[b] = TaggedComponentHelper.read(paramInputStream); 
/* 54 */     return arrayOfTaggedComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, TaggedComponent[] paramArrayOfTaggedComponent) {
/* 59 */     paramOutputStream.write_long(paramArrayOfTaggedComponent.length);
/* 60 */     for (byte b = 0; b < paramArrayOfTaggedComponent.length; b++)
/* 61 */       TaggedComponentHelper.write(paramOutputStream, paramArrayOfTaggedComponent[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/MultipleComponentProfileHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */