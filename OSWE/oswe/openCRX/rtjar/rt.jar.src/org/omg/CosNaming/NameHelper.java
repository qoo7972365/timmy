/*    */ package org.omg.CosNaming;
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
/*    */ public abstract class NameHelper
/*    */ {
/* 17 */   private static String _id = "IDL:omg.org/CosNaming/Name:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, NameComponent[] paramArrayOfNameComponent) {
/* 21 */     OutputStream outputStream = paramAny.create_output_stream();
/* 22 */     paramAny.type(type());
/* 23 */     write(outputStream, paramArrayOfNameComponent);
/* 24 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static NameComponent[] extract(Any paramAny) {
/* 29 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 32 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 35 */     if (__typeCode == null) {
/*    */       
/* 37 */       __typeCode = NameComponentHelper.type();
/* 38 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/* 39 */       __typeCode = ORB.init().create_alias_tc(id(), "Name", __typeCode);
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
/*    */   public static NameComponent[] read(InputStream paramInputStream) {
/* 51 */     NameComponent[] arrayOfNameComponent = null;
/* 52 */     int i = paramInputStream.read_long();
/* 53 */     arrayOfNameComponent = new NameComponent[i];
/* 54 */     for (byte b = 0; b < arrayOfNameComponent.length; b++)
/* 55 */       arrayOfNameComponent[b] = NameComponentHelper.read(paramInputStream); 
/* 56 */     return arrayOfNameComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, NameComponent[] paramArrayOfNameComponent) {
/* 61 */     paramOutputStream.write_long(paramArrayOfNameComponent.length);
/* 62 */     for (byte b = 0; b < paramArrayOfNameComponent.length; b++)
/* 63 */       NameComponentHelper.write(paramOutputStream, paramArrayOfNameComponent[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/NameHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */