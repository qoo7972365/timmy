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
/*     */ import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
/*     */ import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
/*     */ import org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper;
/*     */ import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
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
/*     */ public abstract class NamingContextExtPOA extends Servant implements NamingContextExtOperations, InvokeHandler {
/*  31 */   private static Hashtable _methods = new Hashtable<>();
/*     */   
/*     */   static {
/*  34 */     _methods.put("to_string", new Integer(0));
/*  35 */     _methods.put("to_name", new Integer(1));
/*  36 */     _methods.put("to_url", new Integer(2));
/*  37 */     _methods.put("resolve_str", new Integer(3));
/*  38 */     _methods.put("bind", new Integer(4));
/*  39 */     _methods.put("bind_context", new Integer(5));
/*  40 */     _methods.put("rebind", new Integer(6));
/*  41 */     _methods.put("rebind_context", new Integer(7));
/*  42 */     _methods.put("resolve", new Integer(8));
/*  43 */     _methods.put("unbind", new Integer(9));
/*  44 */     _methods.put("list", new Integer(10));
/*  45 */     _methods.put("new_context", new Integer(11));
/*  46 */     _methods.put("bind_new_context", new Integer(12));
/*  47 */     _methods.put("destroy", new Integer(13));
/*     */   }
/*     */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/*     */     int i;
/*     */     NamingContext namingContext;
/*     */     BindingListHolder bindingListHolder;
/*     */     BindingIteratorHolder bindingIteratorHolder;
/*  54 */     OutputStream outputStream = null;
/*  55 */     Integer integer = (Integer)_methods.get(paramString);
/*  56 */     if (integer == null) {
/*  57 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*  59 */     switch (integer.intValue()) {
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
/*  75 */           NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream);
/*  76 */           String str = null;
/*  77 */           str = to_string(arrayOfNameComponent);
/*  78 */           outputStream = paramResponseHandler.createReply();
/*  79 */           outputStream.write_string(str);
/*  80 */         } catch (InvalidName invalidName) {
/*  81 */           outputStream = paramResponseHandler.createExceptionReply();
/*  82 */           InvalidNameHelper.write(outputStream, invalidName);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 541 */         return outputStream;case 1: try { String str = StringNameHelper.read(paramInputStream); NameComponent[] arrayOfNameComponent = null; arrayOfNameComponent = to_name(str); outputStream = paramResponseHandler.createReply(); NameHelper.write(outputStream, arrayOfNameComponent); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 2: try { String str1 = AddressHelper.read(paramInputStream); String str2 = StringNameHelper.read(paramInputStream); String str3 = null; str3 = to_url(str1, str2); outputStream = paramResponseHandler.createReply(); outputStream.write_string(str3); } catch (InvalidAddress invalidAddress) { outputStream = paramResponseHandler.createExceptionReply(); InvalidAddressHelper.write(outputStream, invalidAddress); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 3: try { String str = StringNameHelper.read(paramInputStream); Object object = null; object = resolve_str(str); outputStream = paramResponseHandler.createReply(); ObjectHelper.write(outputStream, object); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 4: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); Object object = ObjectHelper.read(paramInputStream); bind(arrayOfNameComponent, object); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); } catch (AlreadyBound alreadyBound) { outputStream = paramResponseHandler.createExceptionReply(); AlreadyBoundHelper.write(outputStream, alreadyBound); }  return outputStream;case 5: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); NamingContext namingContext1 = NamingContextHelper.read(paramInputStream); bind_context(arrayOfNameComponent, namingContext1); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); } catch (AlreadyBound alreadyBound) { outputStream = paramResponseHandler.createExceptionReply(); AlreadyBoundHelper.write(outputStream, alreadyBound); }  return outputStream;case 6: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); Object object = ObjectHelper.read(paramInputStream); rebind(arrayOfNameComponent, object); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 7: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); NamingContext namingContext1 = NamingContextHelper.read(paramInputStream); rebind_context(arrayOfNameComponent, namingContext1); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 8: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); Object object = null; object = resolve(arrayOfNameComponent); outputStream = paramResponseHandler.createReply(); ObjectHelper.write(outputStream, object); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 9: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); unbind(arrayOfNameComponent); outputStream = paramResponseHandler.createReply(); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 10: i = paramInputStream.read_ulong(); bindingListHolder = new BindingListHolder(); bindingIteratorHolder = new BindingIteratorHolder(); list(i, bindingListHolder, bindingIteratorHolder); outputStream = paramResponseHandler.createReply(); BindingListHelper.write(outputStream, bindingListHolder.value); BindingIteratorHelper.write(outputStream, bindingIteratorHolder.value); return outputStream;case 11: namingContext = null; namingContext = new_context(); outputStream = paramResponseHandler.createReply(); NamingContextHelper.write(outputStream, namingContext); return outputStream;case 12: try { NameComponent[] arrayOfNameComponent = NameHelper.read(paramInputStream); bindingListHolder = null; NamingContext namingContext1 = bind_new_context(arrayOfNameComponent); outputStream = paramResponseHandler.createReply(); NamingContextHelper.write(outputStream, namingContext1); } catch (NotFound notFound) { outputStream = paramResponseHandler.createExceptionReply(); NotFoundHelper.write(outputStream, notFound); } catch (AlreadyBound alreadyBound) { outputStream = paramResponseHandler.createExceptionReply(); AlreadyBoundHelper.write(outputStream, alreadyBound); } catch (CannotProceed cannotProceed) { outputStream = paramResponseHandler.createExceptionReply(); CannotProceedHelper.write(outputStream, cannotProceed); } catch (InvalidName invalidName) { outputStream = paramResponseHandler.createExceptionReply(); InvalidNameHelper.write(outputStream, invalidName); }  return outputStream;case 13: try { destroy(); outputStream = paramResponseHandler.createReply(); } catch (NotEmpty notEmpty) { outputStream = paramResponseHandler.createExceptionReply(); NotEmptyHelper.write(outputStream, notEmpty); }  return outputStream;
/*     */     } 
/*     */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/* 545 */   private static String[] __ids = new String[] { "IDL:omg.org/CosNaming/NamingContextExt:1.0", "IDL:omg.org/CosNaming/NamingContext:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _all_interfaces(POA paramPOA, byte[] paramArrayOfbyte) {
/* 551 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingContextExt _this() {
/* 556 */     return NamingContextExtHelper.narrow(
/* 557 */         _this_object());
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingContextExt _this(ORB paramORB) {
/* 562 */     return NamingContextExtHelper.narrow(
/* 563 */         _this_object(paramORB));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/NamingContextExtPOA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */