/*    */ package com.sun.corba.se.spi.activation.LocatorPackage;
/*    */ 
/*    */ import com.sun.corba.se.spi.activation.ORBPortInfoHelper;
/*    */ import com.sun.corba.se.spi.activation.ORBPortInfoListHelper;
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public abstract class ServerLocationHelper {
/* 13 */   private static String _id = "IDL:activation/Locator/ServerLocation:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ServerLocation paramServerLocation) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramServerLocation);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ServerLocation extract(Any paramAny) {
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
/* 50 */           typeCode = ORBPortInfoHelper.type();
/* 51 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 52 */           typeCode = ORB.init().create_alias_tc(ORBPortInfoListHelper.id(), "ORBPortInfoList", typeCode);
/* 53 */           arrayOfStructMember[1] = new StructMember("ports", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 57 */           __typeCode = ORB.init().create_struct_tc(id(), "ServerLocation", arrayOfStructMember);
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
/*    */   public static ServerLocation read(InputStream paramInputStream) {
/* 72 */     ServerLocation serverLocation = new ServerLocation();
/* 73 */     serverLocation.hostname = paramInputStream.read_string();
/* 74 */     serverLocation.ports = ORBPortInfoListHelper.read(paramInputStream);
/* 75 */     return serverLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ServerLocation paramServerLocation) {
/* 80 */     paramOutputStream.write_string(paramServerLocation.hostname);
/* 81 */     ORBPortInfoListHelper.write(paramOutputStream, paramServerLocation.ports);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/LocatorPackage/ServerLocationHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */