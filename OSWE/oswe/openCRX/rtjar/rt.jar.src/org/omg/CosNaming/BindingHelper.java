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
/*    */ public abstract class BindingHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/CosNaming/Binding:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Binding paramBinding) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramBinding);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Binding extract(Any paramAny) {
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
/* 45 */           typeCode = NameComponentHelper.type();
/* 46 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 47 */           typeCode = ORB.init().create_alias_tc(NameHelper.id(), "Name", typeCode);
/* 48 */           arrayOfStructMember[0] = new StructMember("binding_name", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 52 */           typeCode = BindingTypeHelper.type();
/* 53 */           arrayOfStructMember[1] = new StructMember("binding_type", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 57 */           __typeCode = ORB.init().create_struct_tc(id(), "Binding", arrayOfStructMember);
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
/*    */   public static Binding read(InputStream paramInputStream) {
/* 72 */     Binding binding = new Binding();
/* 73 */     binding.binding_name = NameHelper.read(paramInputStream);
/* 74 */     binding.binding_type = BindingTypeHelper.read(paramInputStream);
/* 75 */     return binding;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Binding paramBinding) {
/* 80 */     NameHelper.write(paramOutputStream, paramBinding.binding_name);
/* 81 */     BindingTypeHelper.write(paramOutputStream, paramBinding.binding_type);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/BindingHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */