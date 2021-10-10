/*    */ package org.omg.DynamicAny;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public abstract class NameValuePairHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/DynamicAny/NameValuePair:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, NameValuePair paramNameValuePair) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramNameValuePair);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static NameValuePair extract(Any paramAny) {
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
/* 46 */           typeCode = ORB.init().create_alias_tc(FieldNameHelper.id(), "FieldName", typeCode);
/* 47 */           arrayOfStructMember[0] = new StructMember("id", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 51 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_any);
/* 52 */           arrayOfStructMember[1] = new StructMember("value", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 56 */           __typeCode = ORB.init().create_struct_tc(id(), "NameValuePair", arrayOfStructMember);
/* 57 */           __active = false;
/*    */         } 
/*    */       } 
/*    */     }
/* 61 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 66 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static NameValuePair read(InputStream paramInputStream) {
/* 71 */     NameValuePair nameValuePair = new NameValuePair();
/* 72 */     nameValuePair.id = paramInputStream.read_string();
/* 73 */     nameValuePair.value = paramInputStream.read_any();
/* 74 */     return nameValuePair;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, NameValuePair paramNameValuePair) {
/* 79 */     paramOutputStream.write_string(paramNameValuePair.id);
/* 80 */     paramOutputStream.write_any(paramNameValuePair.value);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/NameValuePairHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */