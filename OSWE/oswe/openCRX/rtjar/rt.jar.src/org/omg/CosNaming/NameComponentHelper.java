/*    */ package org.omg.CosNaming;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ public abstract class NameComponentHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/CosNaming/NameComponent:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, NameComponent paramNameComponent) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramNameComponent);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static NameComponent extract(Any paramAny) {
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
/* 46 */           typeCode = ORB.init().create_alias_tc(IstringHelper.id(), "Istring", typeCode);
/* 47 */           arrayOfStructMember[0] = new StructMember("id", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 51 */           typeCode = ORB.init().create_string_tc(0);
/* 52 */           typeCode = ORB.init().create_alias_tc(IstringHelper.id(), "Istring", typeCode);
/* 53 */           arrayOfStructMember[1] = new StructMember("kind", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 57 */           __typeCode = ORB.init().create_struct_tc(id(), "NameComponent", arrayOfStructMember);
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
/*    */   public static NameComponent read(InputStream paramInputStream) {
/* 72 */     NameComponent nameComponent = new NameComponent();
/* 73 */     nameComponent.id = paramInputStream.read_string();
/* 74 */     nameComponent.kind = paramInputStream.read_string();
/* 75 */     return nameComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, NameComponent paramNameComponent) {
/* 80 */     paramOutputStream.write_string(paramNameComponent.id);
/* 81 */     paramOutputStream.write_string(paramNameComponent.kind);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/NameComponentHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */