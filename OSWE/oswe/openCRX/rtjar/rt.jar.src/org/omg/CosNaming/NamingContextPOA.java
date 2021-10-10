/*     */ package org.omg.CosNaming;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.ObjectHelper;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.InvokeHandler;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ResponseHandler;
/*     */ import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
/*     */ import org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper;
/*     */ import org.omg.CosNaming.NamingContextPackage.CannotProceed;
/*     */ import org.omg.CosNaming.NamingContextPackage.CannotProceedHelper;
/*     */ import org.omg.CosNaming.NamingContextPackage.InvalidName;
/*     */ import org.omg.CosNaming.NamingContextPackage.InvalidNameHelper;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotEmpty;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotEmptyHelper;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotFound;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotFoundHelper;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.Servant;
/*     */ 
/*     */ public abstract class NamingContextPOA extends Servant implements NamingContextOperations, InvokeHandler {
/*  27 */   private static Hashtable _methods = new Hashtable<>();
/*     */   
/*     */   static {
/*  30 */     _methods.put("bind", new Integer(0));
/*  31 */     _methods.put("bind_context", new Integer(1));
/*  32 */     _methods.put("rebind", new Integer(2));
/*  33 */     _methods.put("rebind_context", new Integer(3));
/*  34 */     _methods.put("resolve", new Integer(4));
/*  35 */     _methods.put("unbind", new Integer(5));
/*  36 */     _methods.put("list", new Integer(6));
/*  37 */     _methods.put("new_context", new Integer(7));
/*  38 */     _methods.put("bind_new_context", new Integer(8));
/*  39 */     _methods.put("destroy", new Integer(9));
/*     */   }
/*     */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/*     */     int i;
/*     */     NamingContext namingContext;
/*     */     BindingListHolder bindingListHolder;
/*     */     BindingIteratorHolder bindingIteratorHolder;
/*  46 */     OutputStream outputStream = null;
/*  47 */     Integer integer = (Integer)_methods.get(paramString);
/*  48 */     if (integer == null) {
/*  49 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*  51 */     switch (integer.intValue()) {
/*     */       
/*     */       case 0:
/*     */         
/*     */         try {
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
/*  80 */           NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream);
/*  81 */           Object object = ObjectHelper.read(paramInputStream);
/*  82 */           bind(arrayOfNameComponent, object);
/*  83 */           outputStream = paramResponseHandler.createReply();
/*  84 */         } catch (NotFound notFound) {
/*  85 */           outputStream = paramResponseHandler.createExceptionReply();
/*  86 */           NotFoundHelper.write(outputStream, notFound);
/*  87 */         } catch (CannotProceed cannotProceed) {
/*  88 */           outputStream = paramResponseHandler.createExceptionReply();
/*  89 */           CannotProceedHelper.write(outputStream, cannotProceed);
/*  90 */         } catch (InvalidName invalidName) {
/*  91 */           outputStream = paramResponseHandler.createExceptionReply();
/*  92 */           InvalidNameHelper.write(outputStream, invalidName);
/*  93 */         } catch (AlreadyBound alreadyBound) {
/*  94 */           outputStream = paramResponseHandler.createExceptionReply();
/*  95 */           AlreadyBoundHelper.write(outputStream, alreadyBound);
/*     */         } 
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
/* 411 */         return outputStream;case 1: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); NamingContext namingContext1 = NamingContextHelper.read(paramInputStream); bind_context(arrayOfNameComponent, namingContext1); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); } catch (AlreadyBound alreadyBound) { outputStream = paramResponseHandler.createExceptionReply(); AlreadyBoundHelper.write(outputStream, alreadyBound); }  return outputStream;case 2: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); Object object = ObjectHelper.read(paramInputStream); rebind(arrayOfNameComponent, object); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 3: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); NamingContext namingContext1 = NamingContextHelper.read(paramInputStream); rebind_context(arrayOfNameComponent, namingContext1); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 4: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); Object object = null; object = resolve(arrayOfNameComponent); outputStream = paramResponseHandler.createReply(); ObjectHelper.write(outputStream, object); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 5: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); unbind(arrayOfNameComponent); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 6: i = paramInputStream.read_ulong(); bindingListHolder = new BindingListHolder(); bindingIteratorHolder = new BindingIteratorHolder(); list(i, bindingListHolder, bindingIteratorHolder); outputStream = paramResponseHandler.createReply(); BindingListHelper.write(outputStream, bindingListHolder.value); BindingIteratorHelper.write(outputStream, bindingIteratorHolder.value); return outputStream;case 7: namingContext = null; namingContext = new_context(); outputStream = paramResponseHandler.createReply(); NamingContextHelper.write(outputStream, namingContext); return outputStream;case 8: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); bindingListHolder = null; NamingContext namingContext1 = bind_new_context(arrayOfNameComponent); outputStream = paramResponseHandler.createReply(); NamingContextHelper.write(outputStream, namingContext1); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (AlreadyBound alreadyBound) { outputStream = paramResponseHandler.createExceptionReply(); AlreadyBoundHelper.write(outputStream, alreadyBound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 9: try { destroy(); outputStream = paramResponseHandler.createReply(); } catch (NotEmpty notEmpty) { outputStream = paramResponseHandler.createExceptionReply(); NotEmptyHelper.write(outputStream, notEmpty); }  return outputStream;
/*     */     } 
/*     */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/* 415 */   private static String[] __ids = new String[] { "IDL:omg.org/CosNaming/NamingContext:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _all_interfaces(POA paramPOA, byte[] paramArrayOfbyte) {
/* 420 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingContext _this() {
/* 425 */     return NamingContextHelper.narrow(
/* 426 */         _this_object());
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingContext _this(ORB paramORB) {
/* 431 */     return NamingContextHelper.narrow(
/* 432 */         _this_object(paramORB));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/NamingContextPOA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */