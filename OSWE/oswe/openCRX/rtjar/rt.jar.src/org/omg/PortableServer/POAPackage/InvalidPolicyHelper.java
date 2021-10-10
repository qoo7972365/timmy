/*    */ package org.omg.PortableServer.POAPackage;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public abstract class InvalidPolicyHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/PortableServer/POA/InvalidPolicy:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, InvalidPolicy paramInvalidPolicy) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramInvalidPolicy);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static InvalidPolicy extract(Any paramAny) {
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
/* 45 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_ushort);
/* 46 */           arrayOfStructMember[0] = new StructMember("index", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 50 */           __typeCode = ORB.init().create_exception_tc(id(), "InvalidPolicy", arrayOfStructMember);
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
/*    */   public static InvalidPolicy read(InputStream paramInputStream) {
/* 65 */     InvalidPolicy invalidPolicy = new InvalidPolicy();
/*    */     
/* 67 */     paramInputStream.read_string();
/* 68 */     invalidPolicy.index = paramInputStream.read_ushort();
/* 69 */     return invalidPolicy;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, InvalidPolicy paramInvalidPolicy) {
/* 75 */     paramOutputStream.write_string(id());
/* 76 */     paramOutputStream.write_ushort(paramInvalidPolicy.index);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/InvalidPolicyHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */