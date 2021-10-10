/*    */ package org.omg.PortableInterceptor;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.ValueMember;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ObjectReferenceTemplateHelper
/*    */ {
/* 21 */   private static String _id = "IDL:omg.org/PortableInterceptor/ObjectReferenceTemplate:1.0";
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ObjectReferenceTemplate paramObjectReferenceTemplate) {
/* 26 */     OutputStream outputStream = paramAny.create_output_stream();
/* 27 */     paramAny.type(type());
/* 28 */     write(outputStream, paramObjectReferenceTemplate);
/* 29 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ObjectReferenceTemplate extract(Any paramAny) {
/* 34 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 37 */   private static TypeCode __typeCode = null;
/*    */   private static boolean __active = false;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 41 */     if (__typeCode == null)
/*    */     {
/* 43 */       synchronized (TypeCode.class) {
/*    */         
/* 45 */         if (__typeCode == null) {
/*    */           
/* 47 */           if (__active)
/*    */           {
/* 49 */             return ORB.init().create_recursive_tc(_id);
/*    */           }
/* 51 */           __active = true;
/* 52 */           ValueMember[] arrayOfValueMember = new ValueMember[0];
/* 53 */           Object object = null;
/* 54 */           __typeCode = ORB.init().create_value_tc(_id, "ObjectReferenceTemplate", (short)2, null, arrayOfValueMember);
/* 55 */           __active = false;
/*    */         } 
/*    */       } 
/*    */     }
/* 59 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 64 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ObjectReferenceTemplate read(InputStream paramInputStream) {
/* 69 */     return (ObjectReferenceTemplate)((InputStream)paramInputStream).read_value(id());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ObjectReferenceTemplate paramObjectReferenceTemplate) {
/* 74 */     ((OutputStream)paramOutputStream).write_value((Serializable)paramObjectReferenceTemplate, id());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ObjectReferenceTemplateHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */