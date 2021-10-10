/*     */ package com.sun.corba.se.impl.protocol;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.ServerRequestImpl;
/*     */ import com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
/*     */ import com.sun.corba.se.impl.encoding.MarshalInputStream;
/*     */ import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.logging.POASystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.encoding.OutputObject;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.oa.NullServant;
/*     */ import com.sun.corba.se.spi.oa.OADestroyed;
/*     */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapterFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBVersion;
/*     */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*     */ import com.sun.corba.se.spi.protocol.ForwardException;
/*     */ import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
/*     */ import com.sun.corba.se.spi.servicecontext.CodeSetServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.ORBVersionServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.SendingContextServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContexts;
/*     */ import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.DynamicImplementation;
/*     */ import org.omg.CORBA.ServerRequest;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*     */ import org.omg.CORBA.UNKNOWN;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.InvokeHandler;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ResponseHandler;
/*     */ import org.omg.CORBA.portable.UnknownException;
/*     */ import org.omg.PortableServer.DynamicImplementation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CorbaServerRequestDispatcherImpl
/*     */   implements CorbaServerRequestDispatcher
/*     */ {
/*     */   protected ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   private POASystemException poaWrapper;
/*     */   
/*     */   public CorbaServerRequestDispatcherImpl(ORB paramORB) {
/* 106 */     this.orb = paramORB;
/* 107 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */     
/* 109 */     this.poaWrapper = POASystemException.get(paramORB, "rpc.protocol");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IOR locate(ObjectKey paramObjectKey) {
/*     */     try {
/* 139 */       if (this.orb.subcontractDebugFlag) {
/* 140 */         dprint(".locate->");
/*     */       }
/* 142 */       ObjectKeyTemplate objectKeyTemplate = paramObjectKey.getTemplate();
/*     */       
/*     */       try {
/* 145 */         checkServerId(paramObjectKey);
/* 146 */       } catch (ForwardException forwardException) {
/* 147 */         return forwardException.getIOR();
/*     */       } 
/*     */ 
/*     */       
/* 151 */       findObjectAdapter(objectKeyTemplate);
/*     */       
/* 153 */       return null;
/*     */     } finally {
/* 155 */       if (this.orb.subcontractDebugFlag) {
/* 156 */         dprint(".locate<-");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispatch(MessageMediator paramMessageMediator) {
/* 162 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */     try {
/* 164 */       if (this.orb.subcontractDebugFlag) {
/* 165 */         dprint(".dispatch->: " + opAndId(corbaMessageMediator));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 170 */       consumeServiceContexts(corbaMessageMediator);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       ((MarshalInputStream)corbaMessageMediator.getInputObject())
/* 176 */         .performORBVersionSpecificInit();
/*     */       
/* 178 */       ObjectKey objectKey = corbaMessageMediator.getObjectKey();
/*     */ 
/*     */       
/*     */       try {
/* 182 */         checkServerId(objectKey);
/* 183 */       } catch (ForwardException forwardException) {
/* 184 */         if (this.orb.subcontractDebugFlag) {
/* 185 */           dprint(".dispatch: " + opAndId(corbaMessageMediator) + ": bad server id");
/*     */         }
/*     */ 
/*     */         
/* 189 */         corbaMessageMediator.getProtocolHandler()
/* 190 */           .createLocationForward(corbaMessageMediator, forwardException.getIOR(), null);
/*     */         
/*     */         return;
/*     */       } 
/* 194 */       String str = corbaMessageMediator.getOperationName();
/* 195 */       ObjectAdapter objectAdapter = null;
/*     */       
/*     */       try {
/* 198 */         byte[] arrayOfByte = objectKey.getId().getId();
/* 199 */         ObjectKeyTemplate objectKeyTemplate = objectKey.getTemplate();
/* 200 */         objectAdapter = findObjectAdapter(objectKeyTemplate);
/*     */         
/* 202 */         Object object = getServantWithPI(corbaMessageMediator, objectAdapter, arrayOfByte, objectKeyTemplate, str);
/*     */ 
/*     */         
/* 205 */         dispatchToServant(object, corbaMessageMediator, arrayOfByte, objectAdapter);
/* 206 */       } catch (ForwardException forwardException) {
/* 207 */         if (this.orb.subcontractDebugFlag) {
/* 208 */           dprint(".dispatch: " + opAndId(corbaMessageMediator) + ": ForwardException caught");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 214 */         corbaMessageMediator.getProtocolHandler()
/* 215 */           .createLocationForward(corbaMessageMediator, forwardException.getIOR(), null);
/* 216 */       } catch (OADestroyed oADestroyed) {
/* 217 */         if (this.orb.subcontractDebugFlag) {
/* 218 */           dprint(".dispatch: " + opAndId(corbaMessageMediator) + ": OADestroyed exception caught");
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
/* 230 */         dispatch((MessageMediator)corbaMessageMediator);
/* 231 */       } catch (RequestCanceledException requestCanceledException) {
/* 232 */         if (this.orb.subcontractDebugFlag) {
/* 233 */           dprint(".dispatch: " + opAndId(corbaMessageMediator) + ": RequestCanceledException caught");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 241 */         throw requestCanceledException;
/* 242 */       } catch (UnknownException unknownException) {
/* 243 */         if (this.orb.subcontractDebugFlag) {
/* 244 */           dprint(".dispatch: " + opAndId(corbaMessageMediator) + ": UnknownException caught " + unknownException);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 254 */         if (unknownException.originalEx instanceof RequestCanceledException) {
/* 255 */           throw (RequestCanceledException)unknownException.originalEx;
/*     */         }
/*     */         
/* 258 */         ServiceContexts serviceContexts = new ServiceContexts(this.orb);
/* 259 */         UEInfoServiceContext uEInfoServiceContext = new UEInfoServiceContext(unknownException.originalEx);
/*     */ 
/*     */         
/* 262 */         serviceContexts.put((ServiceContext)uEInfoServiceContext);
/*     */         
/* 264 */         UNKNOWN uNKNOWN = this.wrapper.unknownExceptionInDispatch(CompletionStatus.COMPLETED_MAYBE, (Throwable)unknownException);
/*     */         
/* 266 */         corbaMessageMediator.getProtocolHandler()
/* 267 */           .createSystemExceptionResponse(corbaMessageMediator, (SystemException)uNKNOWN, serviceContexts);
/*     */       }
/* 269 */       catch (Throwable throwable) {
/* 270 */         if (this.orb.subcontractDebugFlag) {
/* 271 */           dprint(".dispatch: " + opAndId(corbaMessageMediator) + ": other exception " + throwable);
/*     */         }
/*     */         
/* 274 */         corbaMessageMediator.getProtocolHandler()
/* 275 */           .handleThrowableDuringServerDispatch(corbaMessageMediator, throwable, CompletionStatus.COMPLETED_MAYBE);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } finally {
/* 280 */       if (this.orb.subcontractDebugFlag) {
/* 281 */         dprint(".dispatch<-: " + opAndId(corbaMessageMediator));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void releaseServant(ObjectAdapter paramObjectAdapter) {
/*     */     try {
/* 289 */       if (this.orb.subcontractDebugFlag) {
/* 290 */         dprint(".releaseServant->");
/*     */       }
/*     */       
/* 293 */       if (paramObjectAdapter == null) {
/* 294 */         if (this.orb.subcontractDebugFlag) {
/* 295 */           dprint(".releaseServant: null object adapter");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 301 */         paramObjectAdapter.returnServant();
/*     */       } finally {
/* 303 */         paramObjectAdapter.exit();
/* 304 */         this.orb.popInvocationInfo();
/*     */       } 
/*     */     } finally {
/* 307 */       if (this.orb.subcontractDebugFlag) {
/* 308 */         dprint(".releaseServant<-");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getServant(ObjectAdapter paramObjectAdapter, byte[] paramArrayOfbyte, String paramString) throws OADestroyed {
/*     */     try {
/* 319 */       if (this.orb.subcontractDebugFlag) {
/* 320 */         dprint(".getServant->");
/*     */       }
/*     */       
/* 323 */       OAInvocationInfo oAInvocationInfo = paramObjectAdapter.makeInvocationInfo(paramArrayOfbyte);
/* 324 */       oAInvocationInfo.setOperation(paramString);
/* 325 */       this.orb.pushInvocationInfo(oAInvocationInfo);
/* 326 */       paramObjectAdapter.getInvocationServant(oAInvocationInfo);
/* 327 */       return oAInvocationInfo.getServantContainer();
/*     */     } finally {
/* 329 */       if (this.orb.subcontractDebugFlag) {
/* 330 */         dprint(".getServant<-");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getServantWithPI(CorbaMessageMediator paramCorbaMessageMediator, ObjectAdapter paramObjectAdapter, byte[] paramArrayOfbyte, ObjectKeyTemplate paramObjectKeyTemplate, String paramString) throws OADestroyed {
/*     */     try {
/* 341 */       if (this.orb.subcontractDebugFlag) {
/* 342 */         dprint(".getServantWithPI->");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 348 */       this.orb.getPIHandler().initializeServerPIInfo(paramCorbaMessageMediator, paramObjectAdapter, paramArrayOfbyte, paramObjectKeyTemplate);
/*     */       
/* 350 */       this.orb.getPIHandler().invokeServerPIStartingPoint();
/*     */       
/* 352 */       paramObjectAdapter.enter();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       if (paramCorbaMessageMediator != null) {
/* 358 */         paramCorbaMessageMediator.setExecuteReturnServantInResponseConstructor(true);
/*     */       }
/* 360 */       Object object = getServant(paramObjectAdapter, paramArrayOfbyte, paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 367 */       String str = "unknown";
/*     */       
/* 369 */       if (object instanceof NullServant) {
/* 370 */         handleNullServant(paramString, (NullServant)object);
/*     */       } else {
/* 372 */         str = paramObjectAdapter.getInterfaces(object, paramArrayOfbyte)[0];
/*     */       } 
/* 374 */       this.orb.getPIHandler().setServerPIInfo(object, str);
/*     */       
/* 376 */       if ((object != null && !(object instanceof DynamicImplementation) && !(object instanceof DynamicImplementation)) || 
/*     */ 
/*     */         
/* 379 */         SpecialMethod.getSpecialMethod(paramString) != null) {
/* 380 */         this.orb.getPIHandler().invokeServerPIIntermediatePoint();
/*     */       }
/*     */       
/* 383 */       return object;
/*     */     } finally {
/* 385 */       if (this.orb.subcontractDebugFlag) {
/* 386 */         dprint(".getServantWithPI<-");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkServerId(ObjectKey paramObjectKey) {
/*     */     try {
/* 394 */       if (this.orb.subcontractDebugFlag) {
/* 395 */         dprint(".checkServerId->");
/*     */       }
/*     */       
/* 398 */       ObjectKeyTemplate objectKeyTemplate = paramObjectKey.getTemplate();
/* 399 */       int i = objectKeyTemplate.getServerId();
/* 400 */       int j = objectKeyTemplate.getSubcontractId();
/*     */       
/* 402 */       if (!this.orb.isLocalServerId(j, i)) {
/* 403 */         if (this.orb.subcontractDebugFlag) {
/* 404 */           dprint(".checkServerId: bad server id");
/*     */         }
/*     */         
/* 407 */         this.orb.handleBadServerId(paramObjectKey);
/*     */       } 
/*     */     } finally {
/* 410 */       if (this.orb.subcontractDebugFlag) {
/* 411 */         dprint(".checkServerId<-");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ObjectAdapter findObjectAdapter(ObjectKeyTemplate paramObjectKeyTemplate) {
/*     */     try {
/* 419 */       if (this.orb.subcontractDebugFlag) {
/* 420 */         dprint(".findObjectAdapter->");
/*     */       }
/*     */       
/* 423 */       RequestDispatcherRegistry requestDispatcherRegistry = this.orb.getRequestDispatcherRegistry();
/* 424 */       int i = paramObjectKeyTemplate.getSubcontractId();
/* 425 */       ObjectAdapterFactory objectAdapterFactory = requestDispatcherRegistry.getObjectAdapterFactory(i);
/* 426 */       if (objectAdapterFactory == null) {
/* 427 */         if (this.orb.subcontractDebugFlag) {
/* 428 */           dprint(".findObjectAdapter: failed to find ObjectAdapterFactory");
/*     */         }
/*     */         
/* 431 */         throw this.wrapper.noObjectAdapterFactory();
/*     */       } 
/*     */       
/* 434 */       ObjectAdapterId objectAdapterId = paramObjectKeyTemplate.getObjectAdapterId();
/* 435 */       ObjectAdapter objectAdapter = objectAdapterFactory.find(objectAdapterId);
/*     */       
/* 437 */       if (objectAdapter == null) {
/* 438 */         if (this.orb.subcontractDebugFlag) {
/* 439 */           dprint(".findObjectAdapter: failed to find ObjectAdaptor");
/*     */         }
/*     */         
/* 442 */         throw this.wrapper.badAdapterId();
/*     */       } 
/*     */       
/* 445 */       return objectAdapter;
/*     */     } finally {
/* 447 */       if (this.orb.subcontractDebugFlag) {
/* 448 */         dprint(".findObjectAdapter<-");
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
/*     */   protected void handleNullServant(String paramString, NullServant paramNullServant) {
/*     */     try {
/* 462 */       if (this.orb.subcontractDebugFlag) {
/* 463 */         dprint(".handleNullServant->: " + paramString);
/*     */       }
/*     */ 
/*     */       
/* 467 */       SpecialMethod specialMethod = SpecialMethod.getSpecialMethod(paramString);
/*     */       
/* 469 */       if (specialMethod == null || 
/* 470 */         !specialMethod.isNonExistentMethod()) {
/* 471 */         if (this.orb.subcontractDebugFlag) {
/* 472 */           dprint(".handleNullServant: " + paramString + ": throwing OBJECT_NOT_EXIST");
/*     */         }
/*     */ 
/*     */         
/* 476 */         throw paramNullServant.getException();
/*     */       } 
/*     */     } finally {
/* 479 */       if (this.orb.subcontractDebugFlag) {
/* 480 */         dprint(".handleNullServant<-: " + paramString);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void consumeServiceContexts(CorbaMessageMediator paramCorbaMessageMediator) {
/*     */     try {
/* 488 */       if (this.orb.subcontractDebugFlag) {
/* 489 */         dprint(".consumeServiceContexts->: " + 
/* 490 */             opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */       
/* 493 */       ServiceContexts serviceContexts = paramCorbaMessageMediator.getRequestServiceContexts();
/*     */ 
/*     */       
/* 496 */       GIOPVersion gIOPVersion = paramCorbaMessageMediator.getGIOPVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 502 */       boolean bool = processCodeSetContext(paramCorbaMessageMediator, serviceContexts);
/*     */       
/* 504 */       if (this.orb.subcontractDebugFlag) {
/* 505 */         dprint(".consumeServiceContexts: " + opAndId(paramCorbaMessageMediator) + ": GIOP version: " + gIOPVersion);
/*     */         
/* 507 */         dprint(".consumeServiceContexts: " + opAndId(paramCorbaMessageMediator) + ": as code set context? " + bool);
/*     */       } 
/*     */ 
/*     */       
/* 511 */       ServiceContext serviceContext = serviceContexts.get(6);
/*     */ 
/*     */       
/* 514 */       if (serviceContext != null) {
/* 515 */         SendingContextServiceContext sendingContextServiceContext = (SendingContextServiceContext)serviceContext;
/*     */         
/* 517 */         IOR iOR = sendingContextServiceContext.getIOR();
/*     */         
/*     */         try {
/* 520 */           ((CorbaConnection)paramCorbaMessageMediator.getConnection())
/* 521 */             .setCodeBaseIOR(iOR);
/* 522 */         } catch (ThreadDeath threadDeath) {
/* 523 */           throw threadDeath;
/* 524 */         } catch (Throwable throwable) {
/* 525 */           throw this.wrapper.badStringifiedIor(throwable);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 545 */       boolean bool1 = false;
/*     */       
/* 547 */       if (gIOPVersion.equals(GIOPVersion.V1_0) && bool) {
/* 548 */         if (this.orb.subcontractDebugFlag) {
/* 549 */           dprint(".consumeServiceCOntexts: " + opAndId(paramCorbaMessageMediator) + ": Determined to be an old Sun ORB");
/*     */         }
/*     */ 
/*     */         
/* 553 */         this.orb.setORBVersion(ORBVersionFactory.getOLD());
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 558 */         bool1 = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 563 */       serviceContext = serviceContexts.get(1313165056);
/* 564 */       if (serviceContext != null) {
/* 565 */         ORBVersionServiceContext oRBVersionServiceContext = (ORBVersionServiceContext)serviceContext;
/*     */ 
/*     */         
/* 568 */         ORBVersion oRBVersion = oRBVersionServiceContext.getVersion();
/* 569 */         this.orb.setORBVersion(oRBVersion);
/*     */         
/* 571 */         bool1 = false;
/*     */       } 
/*     */       
/* 574 */       if (bool1) {
/* 575 */         if (this.orb.subcontractDebugFlag) {
/* 576 */           dprint(".consumeServiceContexts: " + opAndId(paramCorbaMessageMediator) + ": Determined to be a foreign ORB");
/*     */         }
/*     */ 
/*     */         
/* 580 */         this.orb.setORBVersion(ORBVersionFactory.getFOREIGN());
/*     */       } 
/*     */     } finally {
/* 583 */       if (this.orb.subcontractDebugFlag) {
/* 584 */         dprint(".consumeServiceContexts<-: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CorbaMessageMediator dispatchToServant(Object paramObject, CorbaMessageMediator paramCorbaMessageMediator, byte[] paramArrayOfbyte, ObjectAdapter paramObjectAdapter) {
/*     */     try {
/* 595 */       if (this.orb.subcontractDebugFlag) {
/* 596 */         dprint(".dispatchToServant->: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */       
/* 599 */       CorbaMessageMediator corbaMessageMediator = null;
/*     */       
/* 601 */       String str = paramCorbaMessageMediator.getOperationName();
/*     */       
/* 603 */       SpecialMethod specialMethod = SpecialMethod.getSpecialMethod(str);
/* 604 */       if (specialMethod != null) {
/* 605 */         if (this.orb.subcontractDebugFlag) {
/* 606 */           dprint(".dispatchToServant: " + opAndId(paramCorbaMessageMediator) + ": Handling special method");
/*     */         }
/*     */ 
/*     */         
/* 610 */         corbaMessageMediator = specialMethod.invoke(paramObject, paramCorbaMessageMediator, paramArrayOfbyte, paramObjectAdapter);
/* 611 */         return corbaMessageMediator;
/*     */       } 
/*     */ 
/*     */       
/* 615 */       if (paramObject instanceof DynamicImplementation) {
/* 616 */         if (this.orb.subcontractDebugFlag) {
/* 617 */           dprint(".dispatchToServant: " + opAndId(paramCorbaMessageMediator) + ": Handling old style DSI type servant");
/*     */         }
/*     */ 
/*     */         
/* 621 */         DynamicImplementation dynamicImplementation = (DynamicImplementation)paramObject;
/*     */         
/* 623 */         ServerRequestImpl serverRequestImpl = new ServerRequestImpl(paramCorbaMessageMediator, this.orb);
/*     */ 
/*     */ 
/*     */         
/* 627 */         dynamicImplementation.invoke((ServerRequest)serverRequestImpl);
/*     */         
/* 629 */         corbaMessageMediator = handleDynamicResult(serverRequestImpl, paramCorbaMessageMediator);
/* 630 */       } else if (paramObject instanceof DynamicImplementation) {
/* 631 */         if (this.orb.subcontractDebugFlag) {
/* 632 */           dprint(".dispatchToServant: " + opAndId(paramCorbaMessageMediator) + ": Handling POA DSI type servant");
/*     */         }
/*     */ 
/*     */         
/* 636 */         DynamicImplementation dynamicImplementation = (DynamicImplementation)paramObject;
/*     */         
/* 638 */         ServerRequestImpl serverRequestImpl = new ServerRequestImpl(paramCorbaMessageMediator, this.orb);
/*     */ 
/*     */ 
/*     */         
/* 642 */         dynamicImplementation.invoke((ServerRequest)serverRequestImpl);
/*     */         
/* 644 */         corbaMessageMediator = handleDynamicResult(serverRequestImpl, paramCorbaMessageMediator);
/*     */       } else {
/* 646 */         if (this.orb.subcontractDebugFlag) {
/* 647 */           dprint(".dispatchToServant: " + opAndId(paramCorbaMessageMediator) + ": Handling invoke handler type servant");
/*     */         }
/*     */ 
/*     */         
/* 651 */         InvokeHandler invokeHandler = (InvokeHandler)paramObject;
/*     */ 
/*     */         
/* 654 */         OutputStream outputStream = invokeHandler._invoke(str, (InputStream)paramCorbaMessageMediator
/*     */             
/* 656 */             .getInputObject(), (ResponseHandler)paramCorbaMessageMediator);
/*     */ 
/*     */         
/* 659 */         corbaMessageMediator = (CorbaMessageMediator)((OutputObject)outputStream).getMessageMediator();
/*     */       } 
/*     */       
/* 662 */       return corbaMessageMediator;
/*     */     } finally {
/* 664 */       if (this.orb.subcontractDebugFlag) {
/* 665 */         dprint(".dispatchToServant<-: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CorbaMessageMediator handleDynamicResult(ServerRequestImpl paramServerRequestImpl, CorbaMessageMediator paramCorbaMessageMediator) {
/*     */     try {
/* 675 */       if (this.orb.subcontractDebugFlag) {
/* 676 */         dprint(".handleDynamicResult->: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */       
/* 679 */       CorbaMessageMediator corbaMessageMediator = null;
/*     */ 
/*     */       
/* 682 */       Any any = paramServerRequestImpl.checkResultCalled();
/*     */       
/* 684 */       if (any == null) {
/* 685 */         if (this.orb.subcontractDebugFlag) {
/* 686 */           dprint(".handleDynamicResult: " + opAndId(paramCorbaMessageMediator) + ": handling normal result");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 691 */         corbaMessageMediator = sendingReply(paramCorbaMessageMediator);
/* 692 */         OutputStream outputStream = (OutputStream)corbaMessageMediator.getOutputObject();
/* 693 */         paramServerRequestImpl.marshalReplyParams(outputStream);
/*     */       } else {
/* 695 */         if (this.orb.subcontractDebugFlag) {
/* 696 */           dprint(".handleDynamicResult: " + opAndId(paramCorbaMessageMediator) + ": handling error");
/*     */         }
/*     */ 
/*     */         
/* 700 */         corbaMessageMediator = sendingReply(paramCorbaMessageMediator, any);
/*     */       } 
/*     */       
/* 703 */       return corbaMessageMediator;
/*     */     } finally {
/* 705 */       if (this.orb.subcontractDebugFlag) {
/* 706 */         dprint(".handleDynamicResult<-: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected CorbaMessageMediator sendingReply(CorbaMessageMediator paramCorbaMessageMediator) {
/*     */     try {
/* 714 */       if (this.orb.subcontractDebugFlag) {
/* 715 */         dprint(".sendingReply->: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */       
/* 718 */       ServiceContexts serviceContexts = new ServiceContexts(this.orb);
/* 719 */       return paramCorbaMessageMediator.getProtocolHandler().createResponse(paramCorbaMessageMediator, serviceContexts);
/*     */     } finally {
/* 721 */       if (this.orb.subcontractDebugFlag) {
/* 722 */         dprint(".sendingReply<-: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CorbaMessageMediator sendingReply(CorbaMessageMediator paramCorbaMessageMediator, Any paramAny) {
/*     */     try {
/*     */       CorbaMessageMediator corbaMessageMediator;
/* 734 */       if (this.orb.subcontractDebugFlag) {
/* 735 */         dprint(".sendingReply/Any->: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */       
/* 738 */       ServiceContexts serviceContexts = new ServiceContexts(this.orb);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 743 */       String str = null;
/*     */       try {
/* 745 */         str = paramAny.type().id();
/* 746 */       } catch (BadKind badKind) {
/* 747 */         throw this.wrapper.problemWithExceptionTypecode(badKind);
/*     */       } 
/*     */       
/* 750 */       if (ORBUtility.isSystemException(str)) {
/* 751 */         if (this.orb.subcontractDebugFlag) {
/* 752 */           dprint(".sendingReply/Any: " + opAndId(paramCorbaMessageMediator) + ": handling system exception");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 757 */         InputStream inputStream = paramAny.create_input_stream();
/* 758 */         SystemException systemException = ORBUtility.readSystemException(inputStream);
/*     */ 
/*     */         
/* 761 */         corbaMessageMediator = paramCorbaMessageMediator.getProtocolHandler().createSystemExceptionResponse(paramCorbaMessageMediator, systemException, serviceContexts);
/*     */       } else {
/* 763 */         if (this.orb.subcontractDebugFlag) {
/* 764 */           dprint(".sendingReply/Any: " + opAndId(paramCorbaMessageMediator) + ": handling user exception");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 769 */         corbaMessageMediator = paramCorbaMessageMediator.getProtocolHandler().createUserExceptionResponse(paramCorbaMessageMediator, serviceContexts);
/* 770 */         OutputStream outputStream = (OutputStream)corbaMessageMediator.getOutputObject();
/* 771 */         paramAny.write_value(outputStream);
/*     */       } 
/*     */       
/* 774 */       return corbaMessageMediator;
/*     */     } finally {
/* 776 */       if (this.orb.subcontractDebugFlag) {
/* 777 */         dprint(".sendingReply/Any<-: " + opAndId(paramCorbaMessageMediator));
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
/*     */   protected boolean processCodeSetContext(CorbaMessageMediator paramCorbaMessageMediator, ServiceContexts paramServiceContexts) {
/*     */     try {
/* 791 */       if (this.orb.subcontractDebugFlag) {
/* 792 */         dprint(".processCodeSetContext->: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */       
/* 795 */       ServiceContext serviceContext = paramServiceContexts.get(1);
/*     */       
/* 797 */       if (serviceContext != null) {
/*     */         
/* 799 */         if (paramCorbaMessageMediator.getConnection() == null) {
/* 800 */           return true;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 806 */         if (paramCorbaMessageMediator.getGIOPVersion().equals(GIOPVersion.V1_0)) {
/* 807 */           return true;
/*     */         }
/*     */         
/* 810 */         CodeSetServiceContext codeSetServiceContext = (CodeSetServiceContext)serviceContext;
/* 811 */         CodeSetComponentInfo.CodeSetContext codeSetContext = codeSetServiceContext.getCodeSetContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 826 */         if (((CorbaConnection)paramCorbaMessageMediator.getConnection())
/* 827 */           .getCodeSetContext() == null) {
/*     */ 
/*     */ 
/*     */           
/* 831 */           if (this.orb.subcontractDebugFlag) {
/* 832 */             dprint(".processCodeSetContext: " + opAndId(paramCorbaMessageMediator) + ": Setting code sets to: " + codeSetContext);
/*     */           }
/*     */ 
/*     */           
/* 836 */           ((CorbaConnection)paramCorbaMessageMediator.getConnection())
/* 837 */             .setCodeSetContext(codeSetContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 851 */           if (codeSetContext.getCharCodeSet() != OSFCodeSetRegistry.ISO_8859_1
/* 852 */             .getNumber()) {
/* 853 */             ((MarshalInputStream)paramCorbaMessageMediator.getInputObject())
/* 854 */               .resetCodeSetConverters();
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 866 */       return (serviceContext != null);
/*     */     } finally {
/* 868 */       if (this.orb.subcontractDebugFlag) {
/* 869 */         dprint(".processCodeSetContext<-: " + opAndId(paramCorbaMessageMediator));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 876 */     ORBUtility.dprint("CorbaServerRequestDispatcherImpl", paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String opAndId(CorbaMessageMediator paramCorbaMessageMediator) {
/* 881 */     return ORBUtility.operationNameAndRequestId(paramCorbaMessageMediator);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/CorbaServerRequestDispatcherImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */