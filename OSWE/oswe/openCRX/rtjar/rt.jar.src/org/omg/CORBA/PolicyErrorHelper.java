/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PolicyErrorHelper
/*    */ {
/* 18 */   private static String _id = "IDL:omg.org/CORBA/PolicyError:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, PolicyError paramPolicyError) {
/* 22 */     OutputStream outputStream = paramAny.create_output_stream();
/* 23 */     paramAny.type(type());
/* 24 */     write(outputStream, paramPolicyError);
/* 25 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static PolicyError extract(Any paramAny) {
/* 30 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 33 */   private static TypeCode __typeCode = null;
/*    */   private static boolean __active = false;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 37 */     if (__typeCode == null)
/*    */     {
/* 39 */       synchronized (TypeCode.class) {
/*    */         
/* 41 */         if (__typeCode == null) {
/*    */           
/* 43 */           if (__active)
/*    */           {
/* 45 */             return ORB.init().create_recursive_tc(_id);
/*    */           }
/* 47 */           __active = true;
/* 48 */           StructMember[] arrayOfStructMember = new StructMember[1];
/* 49 */           TypeCode typeCode = null;
/* 50 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
/* 51 */           typeCode = ORB.init().create_alias_tc(PolicyErrorCodeHelper.id(), "PolicyErrorCode", typeCode);
/* 52 */           arrayOfStructMember[0] = new StructMember("reason", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 56 */           __typeCode = ORB.init().create_exception_tc(id(), "PolicyError", arrayOfStructMember);
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
/*    */   public static PolicyError read(InputStream paramInputStream) {
/* 71 */     PolicyError policyError = new PolicyError();
/*    */     
/* 73 */     paramInputStream.read_string();
/* 74 */     policyError.reason = paramInputStream.read_short();
/* 75 */     return policyError;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, PolicyError paramPolicyError) {
/* 81 */     paramOutputStream.write_string(id());
/* 82 */     paramOutputStream.write_short(paramPolicyError.reason);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/PolicyErrorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */