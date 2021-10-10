/*    */ package org.omg.IOP;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public abstract class ServiceContextHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/IOP/ServiceContext:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ServiceContext paramServiceContext) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramServiceContext);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ServiceContext extract(Any paramAny) {
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
/* 43 */           StructMember[] arrayOfStructMember = new StructMember[2];
/* 44 */           TypeCode typeCode = null;
/* 45 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_ulong);
/* 46 */           typeCode = ORB.init().create_alias_tc(ServiceIdHelper.id(), "ServiceId", typeCode);
/* 47 */           arrayOfStructMember[0] = new StructMember("context_id", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 51 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_octet);
/* 52 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 53 */           arrayOfStructMember[1] = new StructMember("context_data", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 57 */           __typeCode = ORB.init().create_struct_tc(id(), "ServiceContext", arrayOfStructMember);
/* 58 */           __active = false;
/*    */         } 
/*    */       } 
/*    */     }
/* 62 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 67 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ServiceContext read(InputStream paramInputStream) {
/* 72 */     ServiceContext serviceContext = new ServiceContext();
/* 73 */     serviceContext.context_id = paramInputStream.read_ulong();
/* 74 */     int i = paramInputStream.read_long();
/* 75 */     serviceContext.context_data = new byte[i];
/* 76 */     paramInputStream.read_octet_array(serviceContext.context_data, 0, i);
/* 77 */     return serviceContext;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ServiceContext paramServiceContext) {
/* 82 */     paramOutputStream.write_ulong(paramServiceContext.context_id);
/* 83 */     paramOutputStream.write_long(paramServiceContext.context_data.length);
/* 84 */     paramOutputStream.write_octet_array(paramServiceContext.context_data, 0, paramServiceContext.context_data.length);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/ServiceContextHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */