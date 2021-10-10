/*    */ package org.omg.PortableServer;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.ObjectHelper;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public abstract class ForwardRequestHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/PortableServer/ForwardRequest:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ForwardRequest paramForwardRequest) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramForwardRequest);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ForwardRequest extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   private static boolean __active = false;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 32 */     if (__typeCode == null)
/*    */     {
/* 34 */       synchronized (TypeCode.class) {
/*    */         
/* 36 */         if (__typeCode == null) {
/*    */           
/* 38 */           if (__active)
/*    */           {
/* 40 */             return ORB.init().create_recursive_tc(_id);
/*    */           }
/* 42 */           __active = true;
/* 43 */           StructMember[] arrayOfStructMember = new StructMember[1];
/* 44 */           TypeCode typeCode = null;
/* 45 */           typeCode = ObjectHelper.type();
/* 46 */           arrayOfStructMember[0] = new StructMember("forward_reference", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 50 */           __typeCode = ORB.init().create_exception_tc(id(), "ForwardRequest", arrayOfStructMember);
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
/*    */   public static ForwardRequest read(InputStream paramInputStream) {
/* 65 */     ForwardRequest forwardRequest = new ForwardRequest();
/*    */     
/* 67 */     paramInputStream.read_string();
/* 68 */     forwardRequest.forward_reference = ObjectHelper.read(paramInputStream);
/* 69 */     return forwardRequest;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ForwardRequest paramForwardRequest) {
/* 75 */     paramOutputStream.write_string(id());
/* 76 */     ObjectHelper.write(paramOutputStream, paramForwardRequest.forward_reference);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/ForwardRequestHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */