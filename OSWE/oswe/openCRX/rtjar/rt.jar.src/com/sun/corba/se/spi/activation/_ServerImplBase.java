/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import java.util.Hashtable;
/*    */ import org.omg.CORBA.BAD_OPERATION;
/*    */ import org.omg.CORBA.CompletionStatus;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.InvokeHandler;
/*    */ import org.omg.CORBA.portable.ObjectImpl;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.ResponseHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class _ServerImplBase
/*    */   extends ObjectImpl
/*    */   implements Server, InvokeHandler
/*    */ {
/* 23 */   private static Hashtable _methods = new Hashtable<>();
/*    */   
/*    */   static {
/* 26 */     _methods.put("shutdown", new Integer(0));
/* 27 */     _methods.put("install", new Integer(1));
/* 28 */     _methods.put("uninstall", new Integer(2));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/* 35 */     OutputStream outputStream = null;
/* 36 */     Integer integer = (Integer)_methods.get(paramString);
/* 37 */     if (integer == null) {
/* 38 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*    */     }
/* 40 */     switch (integer.intValue()) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       case 0:
/* 47 */         shutdown();
/* 48 */         outputStream = paramResponseHandler.createReply();
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
/* 78 */         return outputStream;case 1: install(); outputStream = paramResponseHandler.createReply(); return outputStream;case 2: uninstall(); outputStream = paramResponseHandler.createReply(); return outputStream;
/*    */     } 
/*    */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*    */   }
/* 82 */   private static String[] __ids = new String[] { "IDL:activation/Server:1.0" };
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] _ids() {
/* 87 */     return (String[])__ids.clone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_ServerImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */