/*      */ package com.sun.corba.se.impl.protocol;
/*      */ 
/*      */ import com.sun.corba.se.impl.encoding.CDRInputObject;
/*      */ import com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
/*      */ import com.sun.corba.se.impl.encoding.CodeSetConversion;
/*      */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.pept.broker.Broker;
/*      */ import com.sun.corba.se.pept.encoding.InputObject;
/*      */ import com.sun.corba.se.pept.encoding.OutputObject;
/*      */ import com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
/*      */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*      */ import com.sun.corba.se.pept.transport.Connection;
/*      */ import com.sun.corba.se.pept.transport.ContactInfo;
/*      */ import com.sun.corba.se.pept.transport.OutboundConnectionCache;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.iiop.CodeSetsComponent;
/*      */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*      */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.orb.ORBVersion;
/*      */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*      */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*      */ import com.sun.corba.se.spi.servicecontext.CodeSetServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.MaxStreamFormatVersionServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.ORBVersionServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.SendingContextServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.ServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.ServiceContexts;
/*      */ import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.UnknownServiceContext;
/*      */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*      */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*      */ import com.sun.corba.se.spi.transport.CorbaContactInfoListIterator;
/*      */ import java.io.IOException;
/*      */ import java.util.Iterator;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.SystemException;
/*      */ import org.omg.CORBA.portable.ApplicationException;
/*      */ import org.omg.CORBA.portable.InputStream;
/*      */ import org.omg.CORBA.portable.RemarshalException;
/*      */ import org.omg.CORBA.portable.UnknownException;
/*      */ import org.omg.CORBA_2_3.portable.InputStream;
/*      */ import sun.corba.EncapsInputStreamFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CorbaClientRequestDispatcherImpl
/*      */   implements ClientRequestDispatcher
/*      */ {
/*  129 */   private ConcurrentMap<ContactInfo, Object> locks = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputObject beginRequest(Object paramObject, String paramString, boolean paramBoolean, ContactInfo paramContactInfo) {
/*  135 */     ORB oRB = null;
/*      */     try {
/*  137 */       CorbaContactInfo corbaContactInfo = (CorbaContactInfo)paramContactInfo;
/*  138 */       oRB = (ORB)paramContactInfo.getBroker();
/*      */       
/*  140 */       if (oRB.subcontractDebugFlag) {
/*  141 */         dprint(".beginRequest->: op/" + paramString);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  148 */       oRB.getPIHandler().initiateClientPIRequest(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  154 */       CorbaConnection corbaConnection = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  162 */       Object object = this.locks.get(paramContactInfo);
/*      */       
/*  164 */       if (object == null) {
/*  165 */         Object object1 = new Object();
/*  166 */         object = this.locks.putIfAbsent(paramContactInfo, object1);
/*  167 */         if (object == null) {
/*  168 */           object = object1;
/*      */         }
/*      */       } 
/*      */       
/*  172 */       synchronized (object) {
/*  173 */         if (paramContactInfo.isConnectionBased()) {
/*  174 */           if (paramContactInfo.shouldCacheConnection())
/*      */           {
/*      */             
/*  177 */             corbaConnection = (CorbaConnection)oRB.getTransportManager().getOutboundConnectionCache(paramContactInfo).get(paramContactInfo);
/*      */           }
/*  179 */           if (corbaConnection != null) {
/*  180 */             if (oRB.subcontractDebugFlag) {
/*  181 */               dprint(".beginRequest: op/" + paramString + ": Using cached connection: " + corbaConnection);
/*      */             }
/*      */           } else {
/*      */ 
/*      */             
/*      */             try {
/*  187 */               corbaConnection = (CorbaConnection)paramContactInfo.createConnection();
/*  188 */               if (oRB.subcontractDebugFlag) {
/*  189 */                 dprint(".beginRequest: op/" + paramString + ": Using created connection: " + corbaConnection);
/*      */               }
/*      */             }
/*  192 */             catch (RuntimeException runtimeException) {
/*  193 */               if (oRB.subcontractDebugFlag) {
/*  194 */                 dprint(".beginRequest: op/" + paramString + ": failed to create connection: " + runtimeException);
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  199 */               boolean bool = getContactInfoListIterator(oRB).reportException(paramContactInfo, runtimeException);
/*      */ 
/*      */               
/*  202 */               if (bool) {
/*  203 */                 if (getContactInfoListIterator(oRB).hasNext()) {
/*      */                   
/*  205 */                   paramContactInfo = (ContactInfo)getContactInfoListIterator(oRB).next();
/*  206 */                   unregisterWaiter(oRB);
/*  207 */                   return beginRequest(paramObject, paramString, paramBoolean, paramContactInfo);
/*      */                 } 
/*      */                 
/*  210 */                 throw runtimeException;
/*      */               } 
/*      */               
/*  213 */               throw runtimeException;
/*      */             } 
/*      */             
/*  216 */             if (corbaConnection.shouldRegisterReadEvent()) {
/*      */               
/*  218 */               oRB.getTransportManager().getSelector(0)
/*  219 */                 .registerForEvent(corbaConnection.getEventHandler());
/*  220 */               corbaConnection.setState("ESTABLISHED");
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  225 */             if (paramContactInfo.shouldCacheConnection()) {
/*      */ 
/*      */               
/*  228 */               OutboundConnectionCache outboundConnectionCache = oRB.getTransportManager().getOutboundConnectionCache(paramContactInfo);
/*  229 */               outboundConnectionCache.stampTime((Connection)corbaConnection);
/*  230 */               outboundConnectionCache.put(paramContactInfo, (Connection)corbaConnection);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  238 */       CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramContactInfo.createMessageMediator((Broker)oRB, paramContactInfo, (Connection)corbaConnection, paramString, paramBoolean);
/*      */       
/*  240 */       if (oRB.subcontractDebugFlag) {
/*  241 */         dprint(".beginRequest: " + opAndId(corbaMessageMediator) + ": created message mediator: " + corbaMessageMediator);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  255 */       oRB.getInvocationInfo().setMessageMediator((MessageMediator)corbaMessageMediator);
/*      */       
/*  257 */       if (corbaConnection != null && corbaConnection.getCodeSetContext() == null) {
/*  258 */         performCodeSetNegotiation(corbaMessageMediator);
/*      */       }
/*      */       
/*  261 */       addServiceContexts(corbaMessageMediator);
/*      */ 
/*      */       
/*  264 */       OutputObject outputObject = paramContactInfo.createOutputObject((MessageMediator)corbaMessageMediator);
/*  265 */       if (oRB.subcontractDebugFlag) {
/*  266 */         dprint(".beginRequest: " + opAndId(corbaMessageMediator) + ": created output object: " + outputObject);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  274 */       registerWaiter(corbaMessageMediator);
/*      */ 
/*      */       
/*  277 */       synchronized (object) {
/*  278 */         if (paramContactInfo.isConnectionBased() && 
/*  279 */           paramContactInfo.shouldCacheConnection()) {
/*      */ 
/*      */           
/*  282 */           OutboundConnectionCache outboundConnectionCache = oRB.getTransportManager().getOutboundConnectionCache(paramContactInfo);
/*  283 */           outboundConnectionCache.reclaim();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  288 */       oRB.getPIHandler().setClientPIInfo(corbaMessageMediator);
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  293 */         oRB.getPIHandler().invokeClientPIStartingPoint();
/*  294 */       } catch (RemarshalException remarshalException) {
/*  295 */         if (oRB.subcontractDebugFlag) {
/*  296 */           dprint(".beginRequest: " + opAndId(corbaMessageMediator) + ": Remarshal");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  313 */         if (getContactInfoListIterator(oRB).hasNext()) {
/*  314 */           paramContactInfo = (ContactInfo)getContactInfoListIterator(oRB).next();
/*  315 */           if (oRB.subcontractDebugFlag) {
/*  316 */             dprint("RemarshalException: hasNext true\ncontact info " + paramContactInfo);
/*      */           }
/*      */ 
/*      */           
/*  320 */           oRB.getPIHandler().makeCompletedClientRequest(3, null);
/*      */           
/*  322 */           unregisterWaiter(oRB);
/*  323 */           oRB.getPIHandler().cleanupClientPIRequest();
/*      */           
/*  325 */           return beginRequest(paramObject, paramString, paramBoolean, paramContactInfo);
/*      */         } 
/*  327 */         if (oRB.subcontractDebugFlag) {
/*  328 */           dprint("RemarshalException: hasNext false");
/*      */         }
/*      */         
/*  331 */         ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(oRB, "rpc.protocol");
/*      */         
/*  333 */         throw oRBUtilSystemException.remarshalWithNowhereToGo();
/*      */       } 
/*      */ 
/*      */       
/*  337 */       corbaMessageMediator.initializeMessage();
/*  338 */       if (oRB.subcontractDebugFlag) {
/*  339 */         dprint(".beginRequest: " + opAndId(corbaMessageMediator) + ": initialized message");
/*      */       }
/*      */ 
/*      */       
/*  343 */       return outputObject;
/*      */     } finally {
/*      */       
/*  346 */       if (oRB.subcontractDebugFlag) {
/*  347 */         dprint(".beginRequest<-: op/" + paramString);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputObject marshalingComplete(Object paramObject, OutputObject paramOutputObject) throws ApplicationException, RemarshalException {
/*  358 */     ORB oRB = null;
/*  359 */     CorbaMessageMediator corbaMessageMediator = null;
/*      */     
/*      */     try {
/*  362 */       corbaMessageMediator = (CorbaMessageMediator)paramOutputObject.getMessageMediator();
/*      */       
/*  364 */       oRB = (ORB)corbaMessageMediator.getBroker();
/*      */       
/*  366 */       if (oRB.subcontractDebugFlag) {
/*  367 */         dprint(".marshalingComplete->: " + opAndId(corbaMessageMediator));
/*      */       }
/*      */ 
/*      */       
/*  371 */       InputObject inputObject = marshalingComplete1(oRB, corbaMessageMediator);
/*      */       
/*  373 */       return processResponse(oRB, corbaMessageMediator, inputObject);
/*      */     } finally {
/*      */       
/*  376 */       if (oRB.subcontractDebugFlag) {
/*  377 */         dprint(".marshalingComplete<-: " + opAndId(corbaMessageMediator));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputObject marshalingComplete1(ORB paramORB, CorbaMessageMediator paramCorbaMessageMediator) throws ApplicationException, RemarshalException {
/*      */     try {
/*  389 */       paramCorbaMessageMediator.finishSendingRequest();
/*      */       
/*  391 */       if (paramORB.subcontractDebugFlag) {
/*  392 */         dprint(".marshalingComplete: " + opAndId(paramCorbaMessageMediator) + ": finished sending request");
/*      */       }
/*      */ 
/*      */       
/*  396 */       return paramCorbaMessageMediator.waitForResponse();
/*      */     }
/*  398 */     catch (RuntimeException runtimeException) {
/*      */       
/*  400 */       if (paramORB.subcontractDebugFlag) {
/*  401 */         dprint(".marshalingComplete: " + opAndId(paramCorbaMessageMediator) + ": exception: " + runtimeException
/*  402 */             .toString());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  407 */       boolean bool = getContactInfoListIterator(paramORB).reportException(paramCorbaMessageMediator.getContactInfo(), runtimeException);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  413 */       Exception exception = paramORB.getPIHandler().invokeClientPIEndingPoint(2, runtimeException);
/*      */ 
/*      */       
/*  416 */       if (bool) {
/*  417 */         if (exception == runtimeException) {
/*  418 */           continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, (Exception)new RemarshalException());
/*      */         } else {
/*      */           
/*  421 */           continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception);
/*      */         } 
/*      */       } else {
/*      */         
/*  425 */         if (exception instanceof RuntimeException) {
/*  426 */           throw (RuntimeException)exception;
/*      */         }
/*  428 */         if (exception instanceof RemarshalException)
/*      */         {
/*  430 */           throw (RemarshalException)exception;
/*      */         }
/*      */ 
/*      */         
/*  434 */         throw runtimeException;
/*      */       } 
/*  436 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InputObject processResponse(ORB paramORB, CorbaMessageMediator paramCorbaMessageMediator, InputObject paramInputObject) throws ApplicationException, RemarshalException {
/*  448 */     ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*      */ 
/*      */     
/*  451 */     if (paramORB.subcontractDebugFlag) {
/*  452 */       dprint(".processResponse: " + opAndId(paramCorbaMessageMediator) + ": response received");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  458 */     if (paramCorbaMessageMediator.getConnection() != null) {
/*  459 */       ((CorbaConnection)paramCorbaMessageMediator.getConnection())
/*  460 */         .setPostInitialContexts();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  468 */     Exception exception = null;
/*      */     
/*  470 */     if (paramCorbaMessageMediator.isOneWay()) {
/*  471 */       getContactInfoListIterator(paramORB)
/*  472 */         .reportSuccess(paramCorbaMessageMediator.getContactInfo());
/*      */       
/*  474 */       exception = paramORB.getPIHandler().invokeClientPIEndingPoint(0, exception);
/*      */       
/*  476 */       continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception);
/*  477 */       return null;
/*      */     } 
/*      */     
/*  480 */     consumeServiceContexts(paramORB, paramCorbaMessageMediator);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  485 */     ((CDRInputObject)paramInputObject).performORBVersionSpecificInit();
/*      */     
/*  487 */     if (paramCorbaMessageMediator.isSystemExceptionReply()) {
/*      */       
/*  489 */       SystemException systemException = paramCorbaMessageMediator.getSystemExceptionReply();
/*      */       
/*  491 */       if (paramORB.subcontractDebugFlag) {
/*  492 */         dprint(".processResponse: " + opAndId(paramCorbaMessageMediator) + ": received system exception: " + systemException);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  498 */       boolean bool = getContactInfoListIterator(paramORB).reportException(paramCorbaMessageMediator.getContactInfo(), (RuntimeException)systemException);
/*      */       
/*  500 */       if (bool) {
/*      */ 
/*      */         
/*  503 */         exception = paramORB.getPIHandler().invokeClientPIEndingPoint(2, (Exception)systemException);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  508 */         if (systemException == exception) {
/*      */ 
/*      */           
/*  511 */           exception = null;
/*  512 */           continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, (Exception)new RemarshalException());
/*      */           
/*  514 */           throw oRBUtilSystemException.statementNotReachable1();
/*      */         } 
/*      */         
/*  517 */         continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception);
/*      */         
/*  519 */         throw oRBUtilSystemException.statementNotReachable2();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  526 */       ServiceContexts serviceContexts = paramCorbaMessageMediator.getReplyServiceContexts();
/*  527 */       if (serviceContexts != null) {
/*      */ 
/*      */         
/*  530 */         UEInfoServiceContext uEInfoServiceContext = (UEInfoServiceContext)serviceContexts.get(9);
/*      */         
/*  532 */         if (uEInfoServiceContext != null) {
/*  533 */           Throwable throwable = uEInfoServiceContext.getUE();
/*  534 */           UnknownException unknownException = new UnknownException(throwable);
/*      */ 
/*      */           
/*  537 */           exception = paramORB.getPIHandler().invokeClientPIEndingPoint(2, (Exception)unknownException);
/*      */ 
/*      */           
/*  540 */           continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception);
/*  541 */           throw oRBUtilSystemException.statementNotReachable3();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  549 */       exception = paramORB.getPIHandler().invokeClientPIEndingPoint(2, (Exception)systemException);
/*      */ 
/*      */       
/*  552 */       continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception);
/*      */ 
/*      */ 
/*      */       
/*  556 */       throw oRBUtilSystemException.statementNotReachable4();
/*  557 */     }  if (paramCorbaMessageMediator.isUserExceptionReply()) {
/*      */       ApplicationException applicationException;
/*  559 */       if (paramORB.subcontractDebugFlag) {
/*  560 */         dprint(".processResponse: " + opAndId(paramCorbaMessageMediator) + ": received user exception");
/*      */       }
/*      */ 
/*      */       
/*  564 */       getContactInfoListIterator(paramORB)
/*  565 */         .reportSuccess(paramCorbaMessageMediator.getContactInfo());
/*      */       
/*  567 */       String str = peekUserExceptionId(paramInputObject);
/*  568 */       Exception exception1 = null;
/*      */       
/*  570 */       if (paramCorbaMessageMediator.isDIIRequest()) {
/*  571 */         exception = paramCorbaMessageMediator.unmarshalDIIUserException(str, (InputStream)paramInputObject);
/*      */         
/*  573 */         exception1 = paramORB.getPIHandler().invokeClientPIEndingPoint(1, exception);
/*      */         
/*  575 */         paramCorbaMessageMediator.setDIIException(exception1);
/*      */       } else {
/*      */         
/*  578 */         ApplicationException applicationException1 = new ApplicationException(str, (InputStream)paramInputObject);
/*      */ 
/*      */ 
/*      */         
/*  582 */         applicationException = applicationException1;
/*  583 */         exception1 = paramORB.getPIHandler().invokeClientPIEndingPoint(1, (Exception)applicationException1);
/*      */       } 
/*      */ 
/*      */       
/*  587 */       if (exception1 != applicationException) {
/*  588 */         continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception1);
/*      */       }
/*      */       
/*  591 */       if (exception1 instanceof ApplicationException) {
/*  592 */         throw (ApplicationException)exception1;
/*      */       }
/*      */ 
/*      */       
/*  596 */       return paramInputObject;
/*      */     } 
/*  598 */     if (paramCorbaMessageMediator.isLocationForwardReply()) {
/*      */       
/*  600 */       if (paramORB.subcontractDebugFlag) {
/*  601 */         dprint(".processResponse: " + opAndId(paramCorbaMessageMediator) + ": received location forward");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  606 */       getContactInfoListIterator(paramORB).reportRedirect((CorbaContactInfo)paramCorbaMessageMediator
/*  607 */           .getContactInfo(), paramCorbaMessageMediator
/*  608 */           .getForwardedIOR());
/*      */ 
/*      */       
/*  611 */       Exception exception1 = paramORB.getPIHandler().invokeClientPIEndingPoint(3, null);
/*      */ 
/*      */       
/*  614 */       if (!(exception1 instanceof RemarshalException)) {
/*  615 */         exception = exception1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  621 */       if (exception != null) {
/*  622 */         continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception);
/*      */       }
/*  624 */       continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, (Exception)new RemarshalException());
/*      */       
/*  626 */       throw oRBUtilSystemException.statementNotReachable5();
/*      */     } 
/*  628 */     if (paramCorbaMessageMediator.isDifferentAddrDispositionRequestedReply()) {
/*      */       
/*  630 */       if (paramORB.subcontractDebugFlag) {
/*  631 */         dprint(".processResponse: " + opAndId(paramCorbaMessageMediator) + ": received different addressing dispostion request");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  636 */       getContactInfoListIterator(paramORB).reportAddrDispositionRetry((CorbaContactInfo)paramCorbaMessageMediator
/*  637 */           .getContactInfo(), paramCorbaMessageMediator
/*  638 */           .getAddrDispositionReply());
/*      */ 
/*      */       
/*  641 */       Exception exception1 = paramORB.getPIHandler().invokeClientPIEndingPoint(5, null);
/*      */ 
/*      */ 
/*      */       
/*  645 */       if (!(exception1 instanceof RemarshalException)) {
/*  646 */         exception = exception1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  652 */       if (exception != null) {
/*  653 */         continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception);
/*      */       }
/*  655 */       continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, (Exception)new RemarshalException());
/*      */       
/*  657 */       throw oRBUtilSystemException.statementNotReachable6();
/*      */     } 
/*      */     
/*  660 */     if (paramORB.subcontractDebugFlag) {
/*  661 */       dprint(".processResponse: " + opAndId(paramCorbaMessageMediator) + ": received normal response");
/*      */     }
/*      */ 
/*      */     
/*  665 */     getContactInfoListIterator(paramORB)
/*  666 */       .reportSuccess(paramCorbaMessageMediator.getContactInfo());
/*      */     
/*  668 */     paramCorbaMessageMediator.handleDIIReply((InputStream)paramInputObject);
/*      */ 
/*      */     
/*  671 */     exception = paramORB.getPIHandler().invokeClientPIEndingPoint(0, null);
/*      */ 
/*      */ 
/*      */     
/*  675 */     continueOrThrowSystemOrRemarshal(paramCorbaMessageMediator, exception);
/*      */     
/*  677 */     return paramInputObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void continueOrThrowSystemOrRemarshal(CorbaMessageMediator paramCorbaMessageMediator, Exception paramException) throws SystemException, RemarshalException {
/*  694 */     ORB oRB = (ORB)paramCorbaMessageMediator.getBroker();
/*      */     
/*  696 */     if (paramException == null) {
/*      */       return;
/*      */     }
/*      */     
/*  700 */     if (paramException instanceof RemarshalException) {
/*      */ 
/*      */       
/*  703 */       oRB.getInvocationInfo().setIsRetryInvocation(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  709 */       unregisterWaiter(oRB);
/*      */       
/*  711 */       if (oRB.subcontractDebugFlag) {
/*  712 */         dprint(".continueOrThrowSystemOrRemarshal: " + 
/*  713 */             opAndId(paramCorbaMessageMediator) + ": throwing Remarshal");
/*      */       }
/*      */ 
/*      */       
/*  717 */       throw (RemarshalException)paramException;
/*      */     } 
/*      */ 
/*      */     
/*  721 */     if (oRB.subcontractDebugFlag) {
/*  722 */       dprint(".continueOrThrowSystemOrRemarshal: " + 
/*  723 */           opAndId(paramCorbaMessageMediator) + ": throwing sex:" + paramException);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  728 */     throw (SystemException)paramException;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected CorbaContactInfoListIterator getContactInfoListIterator(ORB paramORB) {
/*  734 */     return (CorbaContactInfoListIterator)((CorbaInvocationInfo)paramORB
/*  735 */       .getInvocationInfo())
/*  736 */       .getContactInfoListIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerWaiter(CorbaMessageMediator paramCorbaMessageMediator) {
/*  741 */     if (paramCorbaMessageMediator.getConnection() != null) {
/*  742 */       paramCorbaMessageMediator.getConnection().registerWaiter((MessageMediator)paramCorbaMessageMediator);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void unregisterWaiter(ORB paramORB) {
/*  749 */     MessageMediator messageMediator = paramORB.getInvocationInfo().getMessageMediator();
/*  750 */     if (messageMediator != null && messageMediator.getConnection() != null)
/*      */     {
/*      */ 
/*      */       
/*  754 */       messageMediator.getConnection().unregisterWaiter(messageMediator);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addServiceContexts(CorbaMessageMediator paramCorbaMessageMediator) {
/*  760 */     ORB oRB = (ORB)paramCorbaMessageMediator.getBroker();
/*  761 */     CorbaConnection corbaConnection = (CorbaConnection)paramCorbaMessageMediator.getConnection();
/*  762 */     GIOPVersion gIOPVersion = paramCorbaMessageMediator.getGIOPVersion();
/*      */     
/*  764 */     ServiceContexts serviceContexts = paramCorbaMessageMediator.getRequestServiceContexts();
/*      */     
/*  766 */     addCodeSetServiceContext(corbaConnection, serviceContexts, gIOPVersion);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  772 */     serviceContexts.put((ServiceContext)MaxStreamFormatVersionServiceContext.singleton);
/*      */ 
/*      */ 
/*      */     
/*  776 */     ORBVersionServiceContext oRBVersionServiceContext = new ORBVersionServiceContext(ORBVersionFactory.getORBVersion());
/*  777 */     serviceContexts.put((ServiceContext)oRBVersionServiceContext);
/*      */ 
/*      */     
/*  780 */     if (corbaConnection != null && !corbaConnection.isPostInitialContexts()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  786 */       SendingContextServiceContext sendingContextServiceContext = new SendingContextServiceContext(oRB.getFVDCodeBaseIOR());
/*  787 */       serviceContexts.put((ServiceContext)sendingContextServiceContext);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void consumeServiceContexts(ORB paramORB, CorbaMessageMediator paramCorbaMessageMediator) {
/*  794 */     ServiceContexts serviceContexts = paramCorbaMessageMediator.getReplyServiceContexts();
/*      */     
/*  796 */     ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*      */ 
/*      */     
/*  799 */     if (serviceContexts == null) {
/*      */       return;
/*      */     }
/*      */     
/*  803 */     ServiceContext serviceContext = serviceContexts.get(6);
/*      */     
/*  805 */     if (serviceContext != null) {
/*  806 */       SendingContextServiceContext sendingContextServiceContext = (SendingContextServiceContext)serviceContext;
/*      */       
/*  808 */       IOR iOR = sendingContextServiceContext.getIOR();
/*      */ 
/*      */       
/*      */       try {
/*  812 */         if (paramCorbaMessageMediator.getConnection() != null) {
/*  813 */           ((CorbaConnection)paramCorbaMessageMediator.getConnection()).setCodeBaseIOR(iOR);
/*      */         }
/*  815 */       } catch (ThreadDeath threadDeath) {
/*  816 */         throw threadDeath;
/*  817 */       } catch (Throwable throwable) {
/*  818 */         throw oRBUtilSystemException.badStringifiedIor(throwable);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  824 */     serviceContext = serviceContexts.get(1313165056);
/*      */     
/*  826 */     if (serviceContext != null) {
/*  827 */       ORBVersionServiceContext oRBVersionServiceContext = (ORBVersionServiceContext)serviceContext;
/*      */ 
/*      */       
/*  830 */       ORBVersion oRBVersion = oRBVersionServiceContext.getVersion();
/*  831 */       paramORB.setORBVersion(oRBVersion);
/*      */     } 
/*      */     
/*  834 */     getExceptionDetailMessage(paramCorbaMessageMediator, oRBUtilSystemException);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void getExceptionDetailMessage(CorbaMessageMediator paramCorbaMessageMediator, ORBUtilSystemException paramORBUtilSystemException) {
/*  842 */     ServiceContext serviceContext = paramCorbaMessageMediator.getReplyServiceContexts().get(14);
/*  843 */     if (serviceContext == null) {
/*      */       return;
/*      */     }
/*  846 */     if (!(serviceContext instanceof UnknownServiceContext)) {
/*  847 */       throw paramORBUtilSystemException.badExceptionDetailMessageServiceContextType();
/*      */     }
/*  849 */     byte[] arrayOfByte = ((UnknownServiceContext)serviceContext).getData();
/*      */     
/*  851 */     EncapsInputStream encapsInputStream = EncapsInputStreamFactory.newEncapsInputStream((ORB)paramCorbaMessageMediator.getBroker(), arrayOfByte, arrayOfByte.length);
/*      */     
/*  853 */     encapsInputStream.consumeEndian();
/*      */ 
/*      */ 
/*      */     
/*  857 */     String str = "----------BEGIN server-side stack trace----------\n" + encapsInputStream.read_wstring() + "\n----------END server-side stack trace----------";
/*      */ 
/*      */     
/*  860 */     paramCorbaMessageMediator.setReplyExceptionDetailMessage(str);
/*      */   }
/*      */ 
/*      */   
/*      */   public void endRequest(Broker paramBroker, Object paramObject, InputObject paramInputObject) {
/*  865 */     ORB oRB = (ORB)paramBroker;
/*      */     
/*      */     try {
/*  868 */       if (oRB.subcontractDebugFlag) {
/*  869 */         dprint(".endRequest->");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  877 */       MessageMediator messageMediator = oRB.getInvocationInfo().getMessageMediator();
/*  878 */       if (messageMediator != null) {
/*      */         
/*  880 */         if (messageMediator.getConnection() != null)
/*      */         {
/*  882 */           ((CorbaMessageMediator)messageMediator)
/*  883 */             .sendCancelRequestIfFinalFragmentNotSent();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  888 */         InputObject inputObject = messageMediator.getInputObject();
/*  889 */         if (inputObject != null) {
/*  890 */           inputObject.close();
/*      */         }
/*      */         
/*  893 */         OutputObject outputObject = messageMediator.getOutputObject();
/*  894 */         if (outputObject != null) {
/*  895 */           outputObject.close();
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  909 */       unregisterWaiter(oRB);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  915 */       oRB.getPIHandler().cleanupClientPIRequest();
/*      */     
/*      */     }
/*  918 */     catch (IOException iOException) {
/*      */ 
/*      */       
/*  921 */       if (oRB.subcontractDebugFlag)
/*      */       {
/*  923 */         dprint(".endRequest: ignoring IOException - " + iOException.toString());
/*      */       }
/*      */     } finally {
/*  926 */       if (oRB.subcontractDebugFlag) {
/*  927 */         dprint(".endRequest<-");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void performCodeSetNegotiation(CorbaMessageMediator paramCorbaMessageMediator) {
/*  936 */     CorbaConnection corbaConnection = (CorbaConnection)paramCorbaMessageMediator.getConnection();
/*      */ 
/*      */     
/*  939 */     IOR iOR = ((CorbaContactInfo)paramCorbaMessageMediator.getContactInfo()).getEffectiveTargetIOR();
/*  940 */     GIOPVersion gIOPVersion = paramCorbaMessageMediator.getGIOPVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  946 */     if (corbaConnection != null && corbaConnection
/*  947 */       .getCodeSetContext() == null && 
/*  948 */       !gIOPVersion.equals(GIOPVersion.V1_0))
/*      */     {
/*  950 */       synchronized (corbaConnection) {
/*      */ 
/*      */ 
/*      */         
/*  954 */         if (corbaConnection.getCodeSetContext() != null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  962 */         IIOPProfileTemplate iIOPProfileTemplate = (IIOPProfileTemplate)iOR.getProfile().getTaggedProfileTemplate();
/*  963 */         Iterator<CodeSetsComponent> iterator = iIOPProfileTemplate.iteratorById(1);
/*  964 */         if (!iterator.hasNext()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  974 */         CodeSetComponentInfo codeSetComponentInfo = ((CodeSetsComponent)iterator.next()).getCodeSetComponentInfo();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  979 */         CodeSetComponentInfo.CodeSetContext codeSetContext = CodeSetConversion.impl().negotiate(corbaConnection
/*  980 */             .getBroker().getORBData().getCodeSetComponentInfo(), codeSetComponentInfo);
/*      */ 
/*      */         
/*  983 */         corbaConnection.setCodeSetContext(codeSetContext);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addCodeSetServiceContext(CorbaConnection paramCorbaConnection, ServiceContexts paramServiceContexts, GIOPVersion paramGIOPVersion) {
/* 1009 */     if (paramGIOPVersion.equals(GIOPVersion.V1_0) || paramCorbaConnection == null) {
/*      */       return;
/*      */     }
/* 1012 */     CodeSetComponentInfo.CodeSetContext codeSetContext = null;
/*      */     
/* 1014 */     if (paramCorbaConnection.getBroker().getORBData().alwaysSendCodeSetServiceContext() || 
/* 1015 */       !paramCorbaConnection.isPostInitialContexts())
/*      */     {
/*      */       
/* 1018 */       codeSetContext = paramCorbaConnection.getCodeSetContext();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1025 */     if (codeSetContext == null) {
/*      */       return;
/*      */     }
/* 1028 */     CodeSetServiceContext codeSetServiceContext = new CodeSetServiceContext(codeSetContext);
/* 1029 */     paramServiceContexts.put((ServiceContext)codeSetServiceContext);
/*      */   }
/*      */ 
/*      */   
/*      */   protected String peekUserExceptionId(InputObject paramInputObject) {
/* 1034 */     CDRInputObject cDRInputObject = (CDRInputObject)paramInputObject;
/*      */     
/* 1036 */     cDRInputObject.mark(2147483647);
/* 1037 */     String str = cDRInputObject.read_string();
/* 1038 */     cDRInputObject.reset();
/* 1039 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dprint(String paramString) {
/* 1044 */     ORBUtility.dprint("CorbaClientRequestDispatcherImpl", paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected String opAndId(CorbaMessageMediator paramCorbaMessageMediator) {
/* 1049 */     return ORBUtility.operationNameAndRequestId(paramCorbaMessageMediator);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/CorbaClientRequestDispatcherImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */