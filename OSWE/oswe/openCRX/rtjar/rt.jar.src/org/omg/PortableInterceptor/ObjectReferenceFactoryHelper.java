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
/*    */ public abstract class ObjectReferenceFactoryHelper
/*    */ {
/* 17 */   private static String _id = "IDL:omg.org/PortableInterceptor/ObjectReferenceFactory:1.0";
/*    */ 
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ObjectReferenceFactory paramObjectReferenceFactory) {
/* 22 */     OutputStream outputStream = paramAny.create_output_stream();
/* 23 */     paramAny.type(type());
/* 24 */     write(outputStream, paramObjectReferenceFactory);
/* 25 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ObjectReferenceFactory extract(Any paramAny) {
/* 30 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 33 */   private static TypeCode __typeCode = null;
/*    */   private static boolean __active = false;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 37 */     if (__typeCode == null)
/*    */     {
/* 39 */       synchronized (TypeCode.class) {
/*    */         
/* 41 */         if (__typeCode == null) {
/*    */           
/* 43 */           if (__active)
/*    */           {
/* 45 */             return ORB.init().create_recursive_tc(_id);
/*    */           }
/* 47 */           __active = true;
/* 48 */           ValueMember[] arrayOfValueMember = new ValueMember[0];
/* 49 */           Object object = null;
/* 50 */           __typeCode = ORB.init().create_value_tc(_id, "ObjectReferenceFactory", (short)2, null, arrayOfValueMember);
/* 51 */           __active = false;
/*    */         } 
/*    */       } 
/*    */     }
/* 55 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 60 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ObjectReferenceFactory read(InputStream paramInputStream) {
/* 65 */     return (ObjectReferenceFactory)((InputStream)paramInputStream).read_value(id());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ObjectReferenceFactory paramObjectReferenceFactory) {
/* 70 */     ((OutputStream)paramOutputStream).write_value((Serializable)paramObjectReferenceFactory, id());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ObjectReferenceFactoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */