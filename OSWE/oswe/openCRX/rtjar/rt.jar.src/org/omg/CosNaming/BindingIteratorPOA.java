/*     */ package org.omg.CosNaming;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.InvokeHandler;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ResponseHandler;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.Servant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BindingIteratorPOA
/*     */   extends Servant
/*     */   implements BindingIteratorOperations, InvokeHandler
/*     */ {
/*  26 */   private static Hashtable _methods = new Hashtable<>();
/*     */   
/*     */   static {
/*  29 */     _methods.put("next_one", new Integer(0));
/*  30 */     _methods.put("next_n", new Integer(1));
/*  31 */     _methods.put("destroy", new Integer(2));
/*     */   } public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/*     */     BindingHolder bindingHolder;
/*     */     int i;
/*     */     boolean bool1;
/*     */     BindingListHolder bindingListHolder;
/*     */     boolean bool2;
/*  38 */     OutputStream outputStream = null;
/*  39 */     Integer integer = (Integer)_methods.get(paramString);
/*  40 */     if (integer == null) {
/*  41 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*  43 */     switch (integer.intValue()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/*  54 */         bindingHolder = new BindingHolder();
/*  55 */         bool1 = false;
/*  56 */         bool1 = next_one(bindingHolder);
/*  57 */         outputStream = paramResponseHandler.createReply();
/*  58 */         outputStream.write_boolean(bool1);
/*  59 */         BindingHelper.write(outputStream, bindingHolder.value);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  98 */         return outputStream;case 1: i = paramInputStream.read_ulong(); bindingListHolder = new BindingListHolder(); bool2 = false; bool2 = next_n(i, bindingListHolder); outputStream = paramResponseHandler.createReply(); outputStream.write_boolean(bool2); BindingListHelper.write(outputStream, bindingListHolder.value); return outputStream;case 2: destroy(); outputStream = paramResponseHandler.createReply(); return outputStream;
/*     */     } 
/*     */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/* 102 */   private static String[] __ids = new String[] { "IDL:omg.org/CosNaming/BindingIterator:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _all_interfaces(POA paramPOA, byte[] paramArrayOfbyte) {
/* 107 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingIterator _this() {
/* 112 */     return BindingIteratorHelper.narrow(
/* 113 */         _this_object());
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingIterator _this(ORB paramORB) {
/* 118 */     return BindingIteratorHelper.narrow(
/* 119 */         _this_object(paramORB));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/BindingIteratorPOA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */