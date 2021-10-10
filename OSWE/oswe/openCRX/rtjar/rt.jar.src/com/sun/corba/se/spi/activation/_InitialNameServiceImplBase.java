/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import com.sun.corba.se.spi.activation.InitialNameServicePackage.NameAlreadyBound;
/*    */ import com.sun.corba.se.spi.activation.InitialNameServicePackage.NameAlreadyBoundHelper;
/*    */ import java.util.Hashtable;
/*    */ import org.omg.CORBA.BAD_OPERATION;
/*    */ import org.omg.CORBA.CompletionStatus;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.ObjectHelper;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.InvokeHandler;
/*    */ import org.omg.CORBA.portable.ObjectImpl;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.ResponseHandler;
/*    */ 
/*    */ public abstract class _InitialNameServiceImplBase
/*    */   extends ObjectImpl
/*    */   implements InitialNameService, InvokeHandler
/*    */ {
/* 20 */   private static Hashtable _methods = new Hashtable<>();
/*    */   
/*    */   static {
/* 23 */     _methods.put("bind", new Integer(0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/* 30 */     OutputStream outputStream = null;
/* 31 */     Integer integer = (Integer)_methods.get(paramString);
/* 32 */     if (integer == null) {
/* 33 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*    */     }
/* 35 */     switch (integer.intValue()) {
/*    */       
/*    */       case 0:
/*    */         
/*    */         try {
/*    */ 
/*    */           
/* 42 */           String str = paramInputStream.read_string();
/* 43 */           Object object = ObjectHelper.read(paramInputStream);
/* 44 */           boolean bool = paramInputStream.read_boolean();
/* 45 */           bind(str, object, bool);
/* 46 */           outputStream = paramResponseHandler.createReply();
/* 47 */         } catch (NameAlreadyBound nameAlreadyBound) {
/* 48 */           outputStream = paramResponseHandler.createExceptionReply();
/* 49 */           NameAlreadyBoundHelper.write(outputStream, nameAlreadyBound);
/*    */         } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 58 */         return outputStream;
/*    */     } 
/*    */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*    */   }
/* 62 */   private static String[] __ids = new String[] { "IDL:activation/InitialNameService:1.0" };
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] _ids() {
/* 67 */     return (String[])__ids.clone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_InitialNameServiceImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */