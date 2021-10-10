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
/*    */ public abstract class ServerNotRegisteredHelper
/*    */ {
/* 13 */   private static String _id = "IDL:activation/ServerNotRegistered:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, ServerNotRegistered paramServerNotRegistered) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramServerNotRegistered);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ServerNotRegistered extract(Any paramAny) {
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
/* 45 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_long);
/* 46 */           typeCode = ORB.init().create_alias_tc(ServerIdHelper.id(), "ServerId", typeCode);
/* 47 */           arrayOfStructMember[0] = new StructMember("serverId", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 51 */           __typeCode = ORB.init().create_exception_tc(id(), "ServerNotRegistered", arrayOfStructMember);
/* 52 */           __active = false;
/*    */         } 
/*    */       } 
/*    */     }
/* 56 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 61 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ServerNotRegistered read(InputStream paramInputStream) {
/* 66 */     ServerNotRegistered serverNotRegistered = new ServerNotRegistered();
/*    */     
/* 68 */     paramInputStream.read_string();
/* 69 */     serverNotRegistered.serverId = paramInputStream.read_long();
/* 70 */     return serverNotRegistered;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, ServerNotRegistered paramServerNotRegistered) {
/* 76 */     paramOutputStream.write_string(id());
/* 77 */     paramOutputStream.write_long(paramServerNotRegistered.serverId);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerNotRegisteredHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */