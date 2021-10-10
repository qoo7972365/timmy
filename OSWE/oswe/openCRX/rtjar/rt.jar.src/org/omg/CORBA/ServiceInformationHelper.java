/*    */ package org.omg.CORBA;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ public abstract class ServiceInformationHelper
/*    */ {
/*    */   private static TypeCode _tc;
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ServiceInformation paramServiceInformation) {
/* 39 */     paramOutputStream.write_long(paramServiceInformation.service_options.length);
/* 40 */     paramOutputStream.write_ulong_array(paramServiceInformation.service_options, 0, paramServiceInformation.service_options.length);
/* 41 */     paramOutputStream.write_long(paramServiceInformation.service_details.length);
/* 42 */     for (byte b = 0; b < paramServiceInformation.service_details.length; b++) {
/* 43 */       ServiceDetailHelper.write(paramOutputStream, paramServiceInformation.service_details[b]);
/*    */     }
/*    */   }
/*    */   
/*    */   public static ServiceInformation read(InputStream paramInputStream) {
/* 48 */     ServiceInformation serviceInformation = new ServiceInformation();
/*    */     
/* 50 */     int i = paramInputStream.read_long();
/* 51 */     serviceInformation.service_options = new int[i];
/* 52 */     paramInputStream.read_ulong_array(serviceInformation.service_options, 0, serviceInformation.service_options.length);
/*    */ 
/*    */     
/* 55 */     i = paramInputStream.read_long();
/* 56 */     serviceInformation.service_details = new ServiceDetail[i];
/* 57 */     for (byte b = 0; b < serviceInformation.service_details.length; b++) {
/* 58 */       serviceInformation.service_details[b] = ServiceDetailHelper.read(paramInputStream);
/*    */     }
/*    */     
/* 61 */     return serviceInformation;
/*    */   }
/*    */   public static ServiceInformation extract(Any paramAny) {
/* 64 */     InputStream inputStream = paramAny.create_input_stream();
/* 65 */     return read(inputStream);
/*    */   }
/*    */   public static void insert(Any paramAny, ServiceInformation paramServiceInformation) {
/* 68 */     OutputStream outputStream = paramAny.create_output_stream();
/* 69 */     write(outputStream, paramServiceInformation);
/* 70 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 74 */     byte b = 2;
/* 75 */     StructMember[] arrayOfStructMember = null;
/* 76 */     if (_tc == null) {
/* 77 */       arrayOfStructMember = new StructMember[2];
/* 78 */       arrayOfStructMember[0] = new StructMember("service_options", 
/*    */           
/* 80 */           ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.tk_ulong)), null);
/*    */ 
/*    */       
/* 83 */       arrayOfStructMember[1] = new StructMember("service_details", 
/*    */           
/* 85 */           ORB.init().create_sequence_tc(0, ServiceDetailHelper.type()), null);
/*    */       
/* 87 */       _tc = ORB.init().create_struct_tc(id(), "ServiceInformation", arrayOfStructMember);
/*    */     } 
/* 89 */     return _tc;
/*    */   }
/*    */   public static String id() {
/* 92 */     return "IDL:omg.org/CORBA/ServiceInformation:1.0";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ServiceInformationHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */