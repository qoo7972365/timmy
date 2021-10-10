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
/*    */ public abstract class CurrentHelper
/*    */ {
/* 40 */   private static String _id = "IDL:omg.org/CORBA/Current:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, Current paramCurrent) {
/* 44 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Current extract(Any paramAny) {
/* 49 */     throw new MARSHAL();
/*    */   }
/*    */   
/* 52 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 55 */     if (__typeCode == null)
/*    */     {
/* 57 */       __typeCode = ORB.init().create_interface_tc(id(), "Current");
/*    */     }
/* 59 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 64 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Current read(InputStream paramInputStream) {
/* 69 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, Current paramCurrent) {
/* 74 */     throw new MARSHAL();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Current narrow(Object paramObject) {
/* 79 */     if (paramObject == null)
/* 80 */       return null; 
/* 81 */     if (paramObject instanceof Current) {
/* 82 */       return (Current)paramObject;
/*    */     }
/* 84 */     throw new BAD_PARAM();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/CurrentHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */