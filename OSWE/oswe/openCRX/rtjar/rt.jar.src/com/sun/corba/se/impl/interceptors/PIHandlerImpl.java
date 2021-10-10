/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.RequestImpl;
/*     */ import com.sun.corba.se.impl.logging.InterceptorsSystemException;
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orbutil.closure.ClosureFactory;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.protocol.ForwardException;
/*     */ import com.sun.corba.se.spi.protocol.PIHandler;
/*     */ import com.sun.corba.se.spi.protocol.RetryType;
/*     */ import java.util.HashMap;
/*     */ import java.util.Stack;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.CORBA.PolicyError;
/*     */ import org.omg.CORBA.Request;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ import org.omg.IOP.CodecFactory;
/*     */ import org.omg.PortableInterceptor.Current;
/*     */ import org.omg.PortableInterceptor.Interceptor;
/*     */ import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
/*     */ import org.omg.PortableInterceptor.ORBInitializer;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceTemplate;
/*     */ import org.omg.PortableInterceptor.PolicyFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PIHandlerImpl
/*     */   implements PIHandler
/*     */ {
/*     */   boolean printPushPopEnabled = false;
/*  92 */   int pushLevel = 0; private ORB orb; InterceptorsSystemException wrapper;
/*     */   
/*     */   private void printPush() {
/*  95 */     if (!this.printPushPopEnabled)
/*  96 */       return;  printSpaces(this.pushLevel);
/*  97 */     this.pushLevel++;
/*  98 */     System.out.println("PUSH");
/*     */   }
/*     */   ORBUtilSystemException orbutilWrapper; OMGSystemException omgWrapper;
/*     */   private void printPop() {
/* 102 */     if (!this.printPushPopEnabled)
/* 103 */       return;  this.pushLevel--;
/* 104 */     printSpaces(this.pushLevel);
/* 105 */     System.out.println("POP");
/*     */   }
/*     */   
/*     */   private void printSpaces(int paramInt) {
/* 109 */     for (byte b = 0; b < paramInt; b++) {
/* 110 */       System.out.print(" ");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   private int serverRequestIdCounter = 0;
/*     */ 
/*     */   
/* 124 */   CodecFactory codecFactory = null;
/*     */ 
/*     */ 
/*     */   
/* 128 */   String[] arguments = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private InterceptorList interceptorList;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasIORInterceptors;
/*     */ 
/*     */   
/*     */   private boolean hasClientInterceptors;
/*     */ 
/*     */   
/*     */   private boolean hasServerInterceptors;
/*     */ 
/*     */   
/*     */   private InterceptorInvoker interceptorInvoker;
/*     */ 
/*     */   
/*     */   private PICurrent current;
/*     */ 
/*     */   
/*     */   private HashMap policyFactoryTable;
/*     */ 
/*     */   
/* 154 */   private static final short[] REPLY_MESSAGE_TO_PI_REPLY_STATUS = new short[] { 0, 2, 1, 3, 3, 4 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   private ThreadLocal threadLocalClientRequestInfoStack = new ThreadLocal()
/*     */     {
/*     */       protected Object initialValue() {
/* 168 */         return new PIHandlerImpl.RequestInfoStack();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 173 */   private ThreadLocal threadLocalServerRequestInfoStack = new ThreadLocal()
/*     */     {
/*     */       protected Object initialValue() {
/* 176 */         return new PIHandlerImpl.RequestInfoStack();
/*     */       }
/*     */     };
/*     */   
/*     */   public void close() {
/* 181 */     this.orb = null;
/* 182 */     this.wrapper = null;
/* 183 */     this.orbutilWrapper = null;
/* 184 */     this.omgWrapper = null;
/* 185 */     this.codecFactory = null;
/* 186 */     this.arguments = null;
/* 187 */     this.interceptorList = null;
/* 188 */     this.interceptorInvoker = null;
/* 189 */     this.current = null;
/* 190 */     this.policyFactoryTable = null;
/* 191 */     this.threadLocalClientRequestInfoStack = null;
/* 192 */     this.threadLocalServerRequestInfoStack = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class RequestInfoStack
/*     */     extends Stack
/*     */   {
/*     */     private RequestInfoStack() {}
/*     */ 
/*     */ 
/*     */     
/* 205 */     public int disableCount = 0;
/*     */   }
/*     */   
/*     */   public PIHandlerImpl(ORB paramORB, String[] paramArrayOfString) {
/* 209 */     this.orb = paramORB;
/* 210 */     this.wrapper = InterceptorsSystemException.get(paramORB, "rpc.protocol");
/*     */     
/* 212 */     this.orbutilWrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */     
/* 214 */     this.omgWrapper = OMGSystemException.get(paramORB, "rpc.protocol");
/*     */     
/* 216 */     this.arguments = paramArrayOfString;
/*     */ 
/*     */     
/* 219 */     this.codecFactory = new CodecFactoryImpl((ORB)paramORB);
/*     */ 
/*     */     
/* 222 */     this.interceptorList = new InterceptorList(this.wrapper);
/*     */ 
/*     */     
/* 225 */     this.current = new PICurrent(paramORB);
/*     */ 
/*     */     
/* 228 */     this.interceptorInvoker = new InterceptorInvoker(paramORB, this.interceptorList, this.current);
/*     */ 
/*     */ 
/*     */     
/* 232 */     paramORB.getLocalResolver().register("PICurrent", 
/* 233 */         ClosureFactory.makeConstant(this.current));
/* 234 */     paramORB.getLocalResolver().register("CodecFactory", 
/* 235 */         ClosureFactory.makeConstant(this.codecFactory));
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 240 */     if (this.orb.getORBData().getORBInitializers() != null) {
/*     */       
/* 242 */       ORBInitInfoImpl oRBInitInfoImpl = createORBInitInfo();
/*     */ 
/*     */ 
/*     */       
/* 246 */       this.current.setORBInitializing(true);
/*     */ 
/*     */       
/* 249 */       preInitORBInitializers(oRBInitInfoImpl);
/*     */ 
/*     */       
/* 252 */       postInitORBInitializers(oRBInitInfoImpl);
/*     */ 
/*     */       
/* 255 */       this.interceptorList.sortInterceptors();
/*     */ 
/*     */ 
/*     */       
/* 259 */       this.current.setORBInitializing(false);
/*     */ 
/*     */       
/* 262 */       oRBInitInfoImpl.setStage(2);
/*     */ 
/*     */ 
/*     */       
/* 266 */       this.hasIORInterceptors = this.interceptorList.hasInterceptorsOfType(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 275 */       this.hasClientInterceptors = true;
/* 276 */       this.hasServerInterceptors = this.interceptorList.hasInterceptorsOfType(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 282 */       this.interceptorInvoker.setEnabled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroyInterceptors() {
/* 296 */     this.interceptorList.destroyAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public void objectAdapterCreated(ObjectAdapter paramObjectAdapter) {
/* 301 */     if (!this.hasIORInterceptors) {
/*     */       return;
/*     */     }
/* 304 */     this.interceptorInvoker.objectAdapterCreated(paramObjectAdapter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void adapterManagerStateChanged(int paramInt, short paramShort) {
/* 310 */     if (!this.hasIORInterceptors) {
/*     */       return;
/*     */     }
/* 313 */     this.interceptorInvoker.adapterManagerStateChanged(paramInt, paramShort);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void adapterStateChanged(ObjectReferenceTemplate[] paramArrayOfObjectReferenceTemplate, short paramShort) {
/* 319 */     if (!this.hasIORInterceptors) {
/*     */       return;
/*     */     }
/* 322 */     this.interceptorInvoker.adapterStateChanged(paramArrayOfObjectReferenceTemplate, paramShort);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableInterceptorsThisThread() {
/* 331 */     if (!this.hasClientInterceptors) {
/*     */       return;
/*     */     }
/* 334 */     RequestInfoStack requestInfoStack = this.threadLocalClientRequestInfoStack.get();
/* 335 */     requestInfoStack.disableCount++;
/*     */   }
/*     */   
/*     */   public void enableInterceptorsThisThread() {
/* 339 */     if (!this.hasClientInterceptors) {
/*     */       return;
/*     */     }
/* 342 */     RequestInfoStack requestInfoStack = this.threadLocalClientRequestInfoStack.get();
/* 343 */     requestInfoStack.disableCount--;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeClientPIStartingPoint() throws RemarshalException {
/* 349 */     if (!this.hasClientInterceptors)
/* 350 */       return;  if (!isClientPIEnabledForThisThread()) {
/*     */       return;
/*     */     }
/*     */     
/* 354 */     ClientRequestInfoImpl clientRequestInfoImpl = peekClientRequestInfoImplStack();
/* 355 */     this.interceptorInvoker.invokeClientInterceptorStartingPoint(clientRequestInfoImpl);
/*     */ 
/*     */ 
/*     */     
/* 359 */     short s = clientRequestInfoImpl.getReplyStatus();
/* 360 */     if (s == 1 || s == 3) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 366 */       Exception exception = invokeClientPIEndingPoint(
/* 367 */           convertPIReplyStatusToReplyMessage(s), clientRequestInfoImpl
/* 368 */           .getException());
/* 369 */       if (exception == null);
/*     */ 
/*     */       
/* 372 */       if (exception instanceof SystemException)
/* 373 */         throw (SystemException)exception; 
/* 374 */       if (exception instanceof RemarshalException)
/* 375 */         throw (RemarshalException)exception; 
/* 376 */       if (exception instanceof org.omg.CORBA.UserException || exception instanceof org.omg.CORBA.portable.ApplicationException)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 382 */         throw this.wrapper.exceptionInvalid();
/*     */       }
/*     */     }
/* 385 */     else if (s != -1) {
/* 386 */       throw this.wrapper.replyStatusNotInit();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception makeCompletedClientRequest(int paramInt, Exception paramException) {
/* 396 */     return handleClientPIEndingPoint(paramInt, paramException, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception invokeClientPIEndingPoint(int paramInt, Exception paramException) {
/* 403 */     return handleClientPIEndingPoint(paramInt, paramException, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Exception handleClientPIEndingPoint(int paramInt, Exception paramException, boolean paramBoolean) {
/* 408 */     if (!this.hasClientInterceptors) return paramException; 
/* 409 */     if (!isClientPIEnabledForThisThread()) return paramException;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 414 */     short s = REPLY_MESSAGE_TO_PI_REPLY_STATUS[paramInt];
/*     */ 
/*     */ 
/*     */     
/* 418 */     ClientRequestInfoImpl clientRequestInfoImpl = peekClientRequestInfoImplStack();
/* 419 */     clientRequestInfoImpl.setReplyStatus(s);
/* 420 */     clientRequestInfoImpl.setException(paramException);
/*     */     
/* 422 */     if (paramBoolean) {
/*     */       
/* 424 */       this.interceptorInvoker.invokeClientInterceptorEndingPoint(clientRequestInfoImpl);
/* 425 */       s = clientRequestInfoImpl.getReplyStatus();
/*     */     } 
/*     */ 
/*     */     
/* 429 */     if (s == 3 || s == 4) {
/*     */ 
/*     */ 
/*     */       
/* 433 */       clientRequestInfoImpl.reset();
/*     */ 
/*     */       
/* 436 */       if (paramBoolean) {
/* 437 */         clientRequestInfoImpl.setRetryRequest(RetryType.AFTER_RESPONSE);
/*     */       } else {
/* 439 */         clientRequestInfoImpl.setRetryRequest(RetryType.BEFORE_RESPONSE);
/*     */       } 
/*     */ 
/*     */       
/* 443 */       RemarshalException remarshalException = new RemarshalException();
/* 444 */     } else if (s == 1 || s == 2) {
/*     */       
/* 446 */       paramException = clientRequestInfoImpl.getException();
/*     */     } 
/*     */     
/* 449 */     return paramException;
/*     */   }
/*     */   
/*     */   public void initiateClientPIRequest(boolean paramBoolean) {
/* 453 */     if (!this.hasClientInterceptors)
/* 454 */       return;  if (!isClientPIEnabledForThisThread()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 459 */     RequestInfoStack requestInfoStack = this.threadLocalClientRequestInfoStack.get();
/* 460 */     ClientRequestInfoImpl clientRequestInfoImpl = null;
/*     */     
/* 462 */     if (!requestInfoStack.empty()) {
/* 463 */       clientRequestInfoImpl = (ClientRequestInfoImpl)requestInfoStack.peek();
/*     */     }
/*     */     
/* 466 */     if (!paramBoolean && clientRequestInfoImpl != null && clientRequestInfoImpl.isDIIInitiate()) {
/*     */ 
/*     */       
/* 469 */       clientRequestInfoImpl.setDIIInitiate(false);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 475 */       if (clientRequestInfoImpl == null || !clientRequestInfoImpl.getRetryRequest().isRetry()) {
/* 476 */         clientRequestInfoImpl = new ClientRequestInfoImpl(this.orb);
/* 477 */         requestInfoStack.push((E)clientRequestInfoImpl);
/* 478 */         printPush();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 485 */       clientRequestInfoImpl.setRetryRequest(RetryType.NONE);
/* 486 */       clientRequestInfoImpl.incrementEntryCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 492 */       clientRequestInfoImpl.setReplyStatus((short)-1);
/*     */ 
/*     */       
/* 495 */       if (paramBoolean) {
/* 496 */         clientRequestInfoImpl.setDIIInitiate(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cleanupClientPIRequest() {
/* 502 */     if (!this.hasClientInterceptors)
/* 503 */       return;  if (!isClientPIEnabledForThisThread())
/*     */       return; 
/* 505 */     ClientRequestInfoImpl clientRequestInfoImpl = peekClientRequestInfoImplStack();
/* 506 */     RetryType retryType = clientRequestInfoImpl.getRetryRequest();
/*     */ 
/*     */     
/* 509 */     if (!retryType.equals(RetryType.BEFORE_RESPONSE)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 519 */       short s = clientRequestInfoImpl.getReplyStatus();
/* 520 */       if (s == -1) {
/* 521 */         invokeClientPIEndingPoint(2, (Exception)this.wrapper
/* 522 */             .unknownRequestInvoke(CompletionStatus.COMPLETED_MAYBE));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 528 */     clientRequestInfoImpl.decrementEntryCount();
/*     */ 
/*     */     
/* 531 */     if (clientRequestInfoImpl.getEntryCount() == 0 && !clientRequestInfoImpl.getRetryRequest().isRetry()) {
/*     */ 
/*     */ 
/*     */       
/* 535 */       RequestInfoStack requestInfoStack = this.threadLocalClientRequestInfoStack.get();
/* 536 */       requestInfoStack.pop();
/* 537 */       printPop();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientPIInfo(CorbaMessageMediator paramCorbaMessageMediator) {
/* 543 */     if (!this.hasClientInterceptors)
/* 544 */       return;  if (!isClientPIEnabledForThisThread())
/*     */       return; 
/* 546 */     peekClientRequestInfoImplStack().setInfo((MessageMediator)paramCorbaMessageMediator);
/*     */   }
/*     */   
/*     */   public void setClientPIInfo(RequestImpl paramRequestImpl) {
/* 550 */     if (!this.hasClientInterceptors)
/* 551 */       return;  if (!isClientPIEnabledForThisThread())
/*     */       return; 
/* 553 */     peekClientRequestInfoImplStack().setDIIRequest((Request)paramRequestImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeServerPIStartingPoint() {
/* 563 */     if (!this.hasServerInterceptors)
/*     */       return; 
/* 565 */     ServerRequestInfoImpl serverRequestInfoImpl = peekServerRequestInfoImplStack();
/* 566 */     this.interceptorInvoker.invokeServerInterceptorStartingPoint(serverRequestInfoImpl);
/*     */ 
/*     */     
/* 569 */     serverPIHandleExceptions(serverRequestInfoImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invokeServerPIIntermediatePoint() {
/* 574 */     if (!this.hasServerInterceptors)
/*     */       return; 
/* 576 */     ServerRequestInfoImpl serverRequestInfoImpl = peekServerRequestInfoImplStack();
/* 577 */     this.interceptorInvoker.invokeServerInterceptorIntermediatePoint(serverRequestInfoImpl);
/*     */ 
/*     */ 
/*     */     
/* 581 */     serverRequestInfoImpl.releaseServant();
/*     */ 
/*     */     
/* 584 */     serverPIHandleExceptions(serverRequestInfoImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invokeServerPIEndingPoint(ReplyMessage paramReplyMessage) {
/* 589 */     if (!this.hasServerInterceptors)
/* 590 */       return;  ServerRequestInfoImpl serverRequestInfoImpl = peekServerRequestInfoImplStack();
/*     */ 
/*     */     
/* 593 */     serverRequestInfoImpl.setReplyMessage(paramReplyMessage);
/*     */ 
/*     */ 
/*     */     
/* 597 */     serverRequestInfoImpl.setCurrentExecutionPoint(2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 602 */     if (!serverRequestInfoImpl.getAlreadyExecuted()) {
/* 603 */       int i = paramReplyMessage.getReplyStatus();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 609 */       short s1 = REPLY_MESSAGE_TO_PI_REPLY_STATUS[i];
/*     */ 
/*     */ 
/*     */       
/* 613 */       if (s1 == 3 || s1 == 4)
/*     */       {
/*     */         
/* 616 */         serverRequestInfoImpl.setForwardRequest(paramReplyMessage.getIOR());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 624 */       Exception exception1 = serverRequestInfoImpl.getException();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 629 */       if (!serverRequestInfoImpl.isDynamic() && s1 == 2)
/*     */       {
/*     */         
/* 632 */         serverRequestInfoImpl.setException((Exception)this.omgWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 637 */       serverRequestInfoImpl.setReplyStatus(s1);
/* 638 */       this.interceptorInvoker.invokeServerInterceptorEndingPoint(serverRequestInfoImpl);
/* 639 */       short s2 = serverRequestInfoImpl.getReplyStatus();
/* 640 */       Exception exception2 = serverRequestInfoImpl.getException();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 645 */       if (s2 == 1 && exception2 != exception1)
/*     */       {
/*     */         
/* 648 */         throw (SystemException)exception2;
/*     */       }
/*     */ 
/*     */       
/* 652 */       if (s2 == 3) {
/* 653 */         if (s1 != 3) {
/*     */           
/* 655 */           IOR iOR = serverRequestInfoImpl.getForwardRequestIOR();
/* 656 */           throw new ForwardException(this.orb, iOR);
/*     */         } 
/* 658 */         if (serverRequestInfoImpl.isForwardRequestRaisedInEnding())
/*     */         {
/* 660 */           paramReplyMessage.setIOR(serverRequestInfoImpl.getForwardRequestIOR());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setServerPIInfo(Exception paramException) {
/* 667 */     if (!this.hasServerInterceptors)
/*     */       return; 
/* 669 */     ServerRequestInfoImpl serverRequestInfoImpl = peekServerRequestInfoImplStack();
/* 670 */     serverRequestInfoImpl.setException(paramException);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServerPIInfo(NVList paramNVList) {
/* 675 */     if (!this.hasServerInterceptors)
/*     */       return; 
/* 677 */     ServerRequestInfoImpl serverRequestInfoImpl = peekServerRequestInfoImplStack();
/* 678 */     serverRequestInfoImpl.setDSIArguments(paramNVList);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServerPIExceptionInfo(Any paramAny) {
/* 683 */     if (!this.hasServerInterceptors)
/*     */       return; 
/* 685 */     ServerRequestInfoImpl serverRequestInfoImpl = peekServerRequestInfoImplStack();
/* 686 */     serverRequestInfoImpl.setDSIException(paramAny);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServerPIInfo(Any paramAny) {
/* 691 */     if (!this.hasServerInterceptors)
/*     */       return; 
/* 693 */     ServerRequestInfoImpl serverRequestInfoImpl = peekServerRequestInfoImplStack();
/* 694 */     serverRequestInfoImpl.setDSIResult(paramAny);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeServerPIInfo(CorbaMessageMediator paramCorbaMessageMediator, ObjectAdapter paramObjectAdapter, byte[] paramArrayOfbyte, ObjectKeyTemplate paramObjectKeyTemplate) {
/* 700 */     if (!this.hasServerInterceptors) {
/*     */       return;
/*     */     }
/* 703 */     RequestInfoStack requestInfoStack = this.threadLocalServerRequestInfoStack.get();
/* 704 */     ServerRequestInfoImpl serverRequestInfoImpl = new ServerRequestInfoImpl(this.orb);
/* 705 */     requestInfoStack.push((E)serverRequestInfoImpl);
/* 706 */     printPush();
/*     */ 
/*     */ 
/*     */     
/* 710 */     paramCorbaMessageMediator.setExecutePIInResponseConstructor(true);
/*     */     
/* 712 */     serverRequestInfoImpl.setInfo(paramCorbaMessageMediator, paramObjectAdapter, paramArrayOfbyte, paramObjectKeyTemplate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerPIInfo(Object paramObject, String paramString) {
/* 718 */     if (!this.hasServerInterceptors)
/*     */       return; 
/* 720 */     ServerRequestInfoImpl serverRequestInfoImpl = peekServerRequestInfoImplStack();
/* 721 */     serverRequestInfoImpl.setInfo(paramObject, paramString);
/*     */   }
/*     */   
/*     */   public void cleanupServerPIRequest() {
/* 725 */     if (!this.hasServerInterceptors) {
/*     */       return;
/*     */     }
/* 728 */     RequestInfoStack requestInfoStack = this.threadLocalServerRequestInfoStack.get();
/* 729 */     requestInfoStack.pop();
/* 730 */     printPop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void serverPIHandleExceptions(ServerRequestInfoImpl paramServerRequestInfoImpl) {
/* 747 */     int i = paramServerRequestInfoImpl.getEndingPointCall();
/* 748 */     if (i == 1)
/*     */     {
/* 750 */       throw (SystemException)paramServerRequestInfoImpl.getException();
/*     */     }
/* 752 */     if (i == 2 && paramServerRequestInfoImpl
/* 753 */       .getForwardRequestException() != null) {
/*     */ 
/*     */ 
/*     */       
/* 757 */       IOR iOR = paramServerRequestInfoImpl.getForwardRequestIOR();
/* 758 */       throw new ForwardException(this.orb, iOR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int convertPIReplyStatusToReplyMessage(short paramShort) {
/* 770 */     byte b1 = 0;
/* 771 */     for (byte b2 = 0; b2 < REPLY_MESSAGE_TO_PI_REPLY_STATUS.length; b2++) {
/* 772 */       if (REPLY_MESSAGE_TO_PI_REPLY_STATUS[b2] == paramShort) {
/* 773 */         b1 = b2;
/*     */         break;
/*     */       } 
/*     */     } 
/* 777 */     return b1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientRequestInfoImpl peekClientRequestInfoImplStack() {
/* 787 */     RequestInfoStack requestInfoStack = this.threadLocalClientRequestInfoStack.get();
/* 788 */     ClientRequestInfoImpl clientRequestInfoImpl = null;
/* 789 */     if (!requestInfoStack.empty()) {
/* 790 */       clientRequestInfoImpl = (ClientRequestInfoImpl)requestInfoStack.peek();
/*     */     } else {
/* 792 */       throw this.wrapper.clientInfoStackNull();
/*     */     } 
/*     */     
/* 795 */     return clientRequestInfoImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerRequestInfoImpl peekServerRequestInfoImplStack() {
/* 804 */     RequestInfoStack requestInfoStack = this.threadLocalServerRequestInfoStack.get();
/* 805 */     ServerRequestInfoImpl serverRequestInfoImpl = null;
/*     */     
/* 807 */     if (!requestInfoStack.empty()) {
/* 808 */       serverRequestInfoImpl = (ServerRequestInfoImpl)requestInfoStack.peek();
/*     */     } else {
/* 810 */       throw this.wrapper.serverInfoStackNull();
/*     */     } 
/*     */     
/* 813 */     return serverRequestInfoImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isClientPIEnabledForThisThread() {
/* 822 */     RequestInfoStack requestInfoStack = this.threadLocalClientRequestInfoStack.get();
/* 823 */     return (requestInfoStack.disableCount == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void preInitORBInitializers(ORBInitInfoImpl paramORBInitInfoImpl) {
/* 832 */     paramORBInitInfoImpl.setStage(0);
/*     */ 
/*     */ 
/*     */     
/* 836 */     for (byte b = 0; b < (this.orb.getORBData().getORBInitializers()).length; 
/* 837 */       b++) {
/* 838 */       ORBInitializer oRBInitializer = this.orb.getORBData().getORBInitializers()[b];
/* 839 */       if (oRBInitializer != null) {
/*     */         try {
/* 841 */           oRBInitializer.pre_init(paramORBInitInfoImpl);
/*     */         }
/* 843 */         catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void postInitORBInitializers(ORBInitInfoImpl paramORBInitInfoImpl) {
/* 857 */     paramORBInitInfoImpl.setStage(1);
/*     */ 
/*     */ 
/*     */     
/* 861 */     for (byte b = 0; b < (this.orb.getORBData().getORBInitializers()).length; 
/* 862 */       b++) {
/* 863 */       ORBInitializer oRBInitializer = this.orb.getORBData().getORBInitializers()[b];
/* 864 */       if (oRBInitializer != null) {
/*     */         try {
/* 866 */           oRBInitializer.post_init(paramORBInitInfoImpl);
/*     */         }
/* 868 */         catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ORBInitInfoImpl createORBInitInfo() {
/* 881 */     ORBInitInfoImpl oRBInitInfoImpl = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 888 */     String str = this.orb.getORBData().getORBId();
/*     */     
/* 890 */     oRBInitInfoImpl = new ORBInitInfoImpl(this.orb, this.arguments, str, this.codecFactory);
/*     */     
/* 892 */     return oRBInitInfoImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register_interceptor(Interceptor paramInterceptor, int paramInt) throws DuplicateName {
/* 912 */     if (paramInt >= 3 || paramInt < 0) {
/* 913 */       throw this.wrapper.typeOutOfRange(new Integer(paramInt));
/*     */     }
/*     */     
/* 916 */     String str = paramInterceptor.name();
/*     */     
/* 918 */     if (str == null) {
/* 919 */       throw this.wrapper.nameNull();
/*     */     }
/*     */ 
/*     */     
/* 923 */     this.interceptorList.register_interceptor(paramInterceptor, paramInt);
/*     */   }
/*     */   
/*     */   public Current getPICurrent() {
/* 927 */     return this.current;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nullParam() throws BAD_PARAM {
/* 937 */     throw this.orbutilWrapper.nullParam();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Policy create_policy(int paramInt, Any paramAny) throws PolicyError {
/* 951 */     if (paramAny == null) {
/* 952 */       nullParam();
/*     */     }
/* 954 */     if (this.policyFactoryTable == null) {
/* 955 */       throw new PolicyError("There is no PolicyFactory Registered for type " + paramInt, (short)0);
/*     */     }
/*     */ 
/*     */     
/* 959 */     PolicyFactory policyFactory = (PolicyFactory)this.policyFactoryTable.get(new Integer(paramInt));
/*     */     
/* 961 */     if (policyFactory == null) {
/* 962 */       throw new PolicyError(" Could Not Find PolicyFactory for the Type " + paramInt, (short)0);
/*     */     }
/*     */ 
/*     */     
/* 966 */     return policyFactory.create_policy(paramInt, paramAny);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerPolicyFactory(int paramInt, PolicyFactory paramPolicyFactory) {
/* 975 */     if (this.policyFactoryTable == null) {
/* 976 */       this.policyFactoryTable = new HashMap<>();
/*     */     }
/* 978 */     Integer integer = new Integer(paramInt);
/* 979 */     Object object = this.policyFactoryTable.get(integer);
/* 980 */     if (object == null) {
/* 981 */       this.policyFactoryTable.put(integer, paramPolicyFactory);
/*     */     } else {
/*     */       
/* 984 */       throw this.omgWrapper.policyFactoryRegFailed(new Integer(paramInt));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int allocateServerRequestId() {
/* 990 */     return this.serverRequestIdCounter++;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/PIHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */