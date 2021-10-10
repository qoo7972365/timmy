/*    */ package com.sun.corba.se.spi.activation.LocatorPackage;
/*    */ 
/*    */ import com.sun.corba.se.spi.activation.EndPointInfoHelper;
/*    */ import com.sun.corba.se.spi.activation.EndpointInfoListHelper;
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public abstract class ServerLocationPerORBHelper {
/* 13 */   private static String _id = "IDL:activation/Locator/ServerLocationPerORB:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ServerLocationPerORB paramServerLocationPerORB) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramServerLocationPerORB);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ServerLocationPerORB extract(Any paramAny) {
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
/* 45 */           typeCode = ORB.init().create_string_tc(0);
/* 46 */           arrayOfStructMember[0] = new StructMember("hostname", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 50 */           typeCode = EndPointInfoHelper.type();
/* 51 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 52 */           typeCode = ORB.init().create_alias_tc(EndpointInfoListHelper.id(), "EndpointInfoList", typeCode);
/* 53 */           arrayOfStructMember[1] = new StructMember("ports", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 57 */           __typeCode = ORB.init().create_struct_tc(id(), "ServerLocationPerORB", arrayOfStructMember);
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
/*    */   public static ServerLocationPerORB read(InputStream paramInputStream) {
/* 72 */     ServerLocationPerORB serverLocationPerORB = new ServerLocationPerORB();
/* 73 */     serverLocationPerORB.hostname = paramInputStream.read_string();
/* 74 */     serverLocationPerORB.ports = EndpointInfoListHelper.read(paramInputStream);
/* 75 */     return serverLocationPerORB;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ServerLocationPerORB paramServerLocationPerORB) {
/* 80 */     paramOutputStream.write_string(paramServerLocationPerORB.hostname);
/* 81 */     EndpointInfoListHelper.write(paramOutputStream, paramServerLocationPerORB.ports);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/LocatorPackage/ServerLocationPerORBHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */