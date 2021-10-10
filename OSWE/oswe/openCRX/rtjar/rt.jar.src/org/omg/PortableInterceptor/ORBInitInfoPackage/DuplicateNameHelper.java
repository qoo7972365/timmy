/*    */ package org.omg.PortableInterceptor.ORBInitInfoPackage;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ public abstract class DuplicateNameHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/PortableInterceptor/ORBInitInfo/DuplicateName:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DuplicateName paramDuplicateName) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramDuplicateName);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DuplicateName extract(Any paramAny) {
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
/* 45 */           typeCode = ORB.init().create_string_tc(0);
/* 46 */           arrayOfStructMember[0] = new StructMember("name", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 50 */           __typeCode = ORB.init().create_exception_tc(id(), "DuplicateName", arrayOfStructMember);
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
/*    */   public static DuplicateName read(InputStream paramInputStream) {
/* 65 */     DuplicateName duplicateName = new DuplicateName();
/*    */     
/* 67 */     paramInputStream.read_string();
/* 68 */     duplicateName.name = paramInputStream.read_string();
/* 69 */     return duplicateName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DuplicateName paramDuplicateName) {
/* 75 */     paramOutputStream.write_string(id());
/* 76 */     paramOutputStream.write_string(paramDuplicateName.name);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ORBInitInfoPackage/DuplicateNameHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */