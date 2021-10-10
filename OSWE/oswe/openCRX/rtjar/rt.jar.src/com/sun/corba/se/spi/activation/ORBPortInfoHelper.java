/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public abstract class ORBPortInfoHelper
/*    */ {
/* 13 */   private static String _id = "IDL:activation/ORBPortInfo:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ORBPortInfo paramORBPortInfo) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramORBPortInfo);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ORBPortInfo extract(Any paramAny) {
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
/* 46 */           typeCode = ORB.init().create_alias_tc(ORBidHelper.id(), "ORBid", typeCode);
/* 47 */           arrayOfStructMember[0] = new StructMember("orbId", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 51 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_long);
/* 52 */           typeCode = ORB.init().create_alias_tc(TCPPortHelper.id(), "TCPPort", typeCode);
/* 53 */           arrayOfStructMember[1] = new StructMember("port", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 57 */           __typeCode = ORB.init().create_struct_tc(id(), "ORBPortInfo", arrayOfStructMember);
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
/*    */   public static ORBPortInfo read(InputStream paramInputStream) {
/* 72 */     ORBPortInfo oRBPortInfo = new ORBPortInfo();
/* 73 */     oRBPortInfo.orbId = paramInputStream.read_string();
/* 74 */     oRBPortInfo.port = paramInputStream.read_long();
/* 75 */     return oRBPortInfo;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ORBPortInfo paramORBPortInfo) {
/* 80 */     paramOutputStream.write_string(paramORBPortInfo.orbId);
/* 81 */     paramOutputStream.write_long(paramORBPortInfo.port);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ORBPortInfoHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */