/*     */ package com.sun.corba.se.impl.orb;
/*     */ 
/*     */ import com.sun.corba.se.impl.dynamicany.DynAnyFactoryImpl;
/*     */ import com.sun.corba.se.impl.legacy.connection.SocketFactoryAcceptorImpl;
/*     */ import com.sun.corba.se.impl.legacy.connection.SocketFactoryContactInfoListImpl;
/*     */ import com.sun.corba.se.impl.legacy.connection.USLPort;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBConstants;
/*     */ import com.sun.corba.se.impl.transport.SocketOrChannelAcceptorImpl;
/*     */ import com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
/*     */ import com.sun.corba.se.pept.transport.Acceptor;
/*     */ import com.sun.corba.se.spi.activation.Activator;
/*     */ import com.sun.corba.se.spi.activation.ActivatorHelper;
/*     */ import com.sun.corba.se.spi.activation.EndPointInfo;
/*     */ import com.sun.corba.se.spi.activation.Locator;
/*     */ import com.sun.corba.se.spi.activation.LocatorHelper;
/*     */ import com.sun.corba.se.spi.copyobject.CopierManager;
/*     */ import com.sun.corba.se.spi.copyobject.CopyobjectDefaults;
/*     */ import com.sun.corba.se.spi.copyobject.ObjectCopierFactory;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.TaggedComponentFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPFactories;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;
/*     */ import com.sun.corba.se.spi.legacy.connection.ORBSocketFactory;
/*     */ import com.sun.corba.se.spi.oa.OADefault;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapterFactory;
/*     */ import com.sun.corba.se.spi.orb.DataCollector;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBConfigurator;
/*     */ import com.sun.corba.se.spi.orb.ORBData;
/*     */ import com.sun.corba.se.spi.orb.Operation;
/*     */ import com.sun.corba.se.spi.orb.OperationFactory;
/*     */ import com.sun.corba.se.spi.orb.ParserImplBase;
/*     */ import com.sun.corba.se.spi.orb.PropertyParser;
/*     */ import com.sun.corba.se.spi.orbutil.closure.Closure;
/*     */ import com.sun.corba.se.spi.orbutil.closure.ClosureFactory;
/*     */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*     */ import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcherFactory;
/*     */ import com.sun.corba.se.spi.protocol.RequestDispatcherDefault;
/*     */ import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
/*     */ import com.sun.corba.se.spi.resolver.LocalResolver;
/*     */ import com.sun.corba.se.spi.resolver.Resolver;
/*     */ import com.sun.corba.se.spi.resolver.ResolverDefault;
/*     */ import com.sun.corba.se.spi.servicecontext.CodeSetServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.MaxStreamFormatVersionServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.ORBVersionServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.SendingContextServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContextRegistry;
/*     */ import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoListFactory;
/*     */ import com.sun.corba.se.spi.transport.TransportDefault;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ORBConfiguratorImpl
/*     */   implements ORBConfigurator
/*     */ {
/*     */   private ORBUtilSystemException wrapper;
/*     */   private static final int ORB_STREAM = 0;
/*     */   
/*     */   public static class ConfigParser
/*     */     extends ParserImplBase
/*     */   {
/* 122 */     public Class[] userConfigurators = null;
/*     */ 
/*     */     
/*     */     public PropertyParser makeParser() {
/* 126 */       PropertyParser propertyParser = new PropertyParser();
/* 127 */       Operation operation = OperationFactory.compose(
/* 128 */           OperationFactory.suffixAction(), 
/* 129 */           OperationFactory.classAction());
/*     */       
/* 131 */       propertyParser.addPrefix("com.sun.CORBA.ORBUserConfigurators", operation, "userConfigurators", Class.class);
/*     */       
/* 133 */       return propertyParser;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void configure(DataCollector paramDataCollector, ORB paramORB) {
/* 139 */     ORB oRB = paramORB;
/* 140 */     this.wrapper = ORBUtilSystemException.get(paramORB, "orb.lifecycle");
/*     */     
/* 142 */     initObjectCopiers(oRB);
/* 143 */     initIORFinders(oRB);
/*     */     
/* 145 */     oRB.setClientDelegateFactory(
/*     */         
/* 147 */         TransportDefault.makeClientDelegateFactory(oRB));
/*     */     
/* 149 */     initializeTransport(oRB);
/*     */     
/* 151 */     initializeNaming(oRB);
/* 152 */     initServiceContextRegistry(oRB);
/* 153 */     initRequestDispatcherRegistry(oRB);
/* 154 */     registerInitialReferences(oRB);
/*     */     
/* 156 */     persistentServerInitialization(oRB);
/*     */     
/* 158 */     runUserConfigurators(paramDataCollector, oRB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void runUserConfigurators(DataCollector paramDataCollector, ORB paramORB) {
/* 166 */     ConfigParser configParser = new ConfigParser();
/* 167 */     configParser.init(paramDataCollector);
/* 168 */     if (configParser.userConfigurators != null) {
/* 169 */       for (byte b = 0; b < configParser.userConfigurators.length; b++) {
/* 170 */         Class<ORBConfigurator> clazz = configParser.userConfigurators[b];
/*     */         try {
/* 172 */           ORBConfigurator oRBConfigurator = clazz.newInstance();
/* 173 */           oRBConfigurator.configure(paramDataCollector, paramORB);
/* 174 */         } catch (Exception exception) {}
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void persistentServerInitialization(ORB paramORB) {
/* 184 */     ORBData oRBData = paramORB.getORBData();
/*     */ 
/*     */ 
/*     */     
/* 188 */     if (oRBData.getServerIsORBActivated()) {
/*     */       try {
/* 190 */         Locator locator = LocatorHelper.narrow(paramORB
/* 191 */             .resolve_initial_references("ServerLocator"));
/*     */         
/* 193 */         Activator activator = ActivatorHelper.narrow(paramORB
/* 194 */             .resolve_initial_references("ServerActivator"));
/*     */ 
/*     */         
/* 197 */         Collection collection = paramORB.getCorbaTransportManager().getAcceptors(null, null);
/*     */         
/* 199 */         EndPointInfo[] arrayOfEndPointInfo = new EndPointInfo[collection.size()];
/* 200 */         Iterator<Object> iterator = collection.iterator();
/* 201 */         byte b = 0;
/* 202 */         while (iterator.hasNext()) {
/* 203 */           LegacyServerSocketEndPointInfo legacyServerSocketEndPointInfo1 = (LegacyServerSocketEndPointInfo)iterator.next();
/* 204 */           if (!(legacyServerSocketEndPointInfo1 instanceof LegacyServerSocketEndPointInfo)) {
/*     */             continue;
/*     */           }
/* 207 */           LegacyServerSocketEndPointInfo legacyServerSocketEndPointInfo2 = legacyServerSocketEndPointInfo1;
/*     */ 
/*     */           
/* 210 */           int i = locator.getEndpoint(legacyServerSocketEndPointInfo2.getType());
/* 211 */           if (i == -1) {
/* 212 */             i = locator.getEndpoint("IIOP_CLEAR_TEXT");
/* 213 */             if (i == -1) {
/* 214 */               throw new Exception("ORBD must support IIOP_CLEAR_TEXT");
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 219 */           legacyServerSocketEndPointInfo2.setLocatorPort(i);
/*     */           
/* 221 */           arrayOfEndPointInfo[b++] = new EndPointInfo(legacyServerSocketEndPointInfo2
/* 222 */               .getType(), legacyServerSocketEndPointInfo2.getPort());
/*     */         } 
/*     */         
/* 225 */         activator.registerEndpoints(oRBData
/* 226 */             .getPersistentServerId(), oRBData.getORBId(), arrayOfEndPointInfo);
/*     */       }
/* 228 */       catch (Exception exception) {
/* 229 */         throw this.wrapper.persistentServerInitError(CompletionStatus.COMPLETED_MAYBE, exception);
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
/*     */   private void initializeTransport(final ORB orb) {
/* 242 */     ORBData oRBData = orb.getORBData();
/*     */ 
/*     */     
/* 245 */     CorbaContactInfoListFactory corbaContactInfoListFactory = oRBData.getCorbaContactInfoListFactory();
/* 246 */     Acceptor[] arrayOfAcceptor = oRBData.getAcceptors();
/*     */ 
/*     */     
/* 249 */     ORBSocketFactory oRBSocketFactory = oRBData.getLegacySocketFactory();
/* 250 */     USLPort[] arrayOfUSLPort1 = oRBData.getUserSpecifiedListenPorts();
/* 251 */     setLegacySocketFactoryORB(orb, oRBSocketFactory);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     if (oRBSocketFactory != null && corbaContactInfoListFactory != null) {
/* 259 */       throw this.wrapper.socketFactoryAndContactInfoListAtSameTime();
/*     */     }
/*     */     
/* 262 */     if (arrayOfAcceptor.length != 0 && oRBSocketFactory != null) {
/* 263 */       throw this.wrapper.acceptorsAndLegacySocketFactoryAtSameTime();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     oRBData.getSocketFactory().setORB(orb);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     if (oRBSocketFactory != null) {
/*     */ 
/*     */ 
/*     */       
/* 281 */       corbaContactInfoListFactory = new CorbaContactInfoListFactory() {
/*     */           public void setORB(ORB param1ORB) {}
/*     */           
/*     */           public CorbaContactInfoList create(IOR param1IOR) {
/* 285 */             return (CorbaContactInfoList)new SocketFactoryContactInfoListImpl(orb, param1IOR);
/*     */           }
/*     */         };
/*     */     
/*     */     }
/* 290 */     else if (corbaContactInfoListFactory != null) {
/*     */       
/* 292 */       corbaContactInfoListFactory.setORB(orb);
/*     */     }
/*     */     else {
/*     */       
/* 296 */       corbaContactInfoListFactory = TransportDefault.makeCorbaContactInfoListFactory(orb);
/*     */     } 
/* 298 */     orb.setCorbaContactInfoListFactory(corbaContactInfoListFactory);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     int i = -1;
/* 316 */     if (oRBData.getORBServerPort() != 0) {
/* 317 */       i = oRBData.getORBServerPort();
/* 318 */     } else if (oRBData.getPersistentPortInitialized()) {
/* 319 */       i = oRBData.getPersistentServerPort();
/* 320 */     } else if (arrayOfAcceptor.length == 0) {
/* 321 */       i = 0;
/*     */     } 
/* 323 */     if (i != -1) {
/* 324 */       createAndRegisterAcceptor(orb, oRBSocketFactory, i, "DEFAULT_ENDPOINT", "IIOP_CLEAR_TEXT");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     for (byte b = 0; b < arrayOfAcceptor.length; b++) {
/* 331 */       orb.getCorbaTransportManager().registerAcceptor(arrayOfAcceptor[b]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 336 */     USLPort[] arrayOfUSLPort2 = oRBData.getUserSpecifiedListenPorts();
/* 337 */     if (arrayOfUSLPort2 != null) {
/* 338 */       for (byte b1 = 0; b1 < arrayOfUSLPort2.length; b1++) {
/* 339 */         createAndRegisterAcceptor(orb, oRBSocketFactory, arrayOfUSLPort2[b1]
/* 340 */             .getPort(), "NO_NAME", arrayOfUSLPort2[b1]
/*     */             
/* 342 */             .getType());
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
/*     */   private void createAndRegisterAcceptor(ORB paramORB, ORBSocketFactory paramORBSocketFactory, int paramInt, String paramString1, String paramString2) {
/*     */     SocketFactoryAcceptorImpl socketFactoryAcceptorImpl;
/* 357 */     if (paramORBSocketFactory == null) {
/* 358 */       SocketOrChannelAcceptorImpl socketOrChannelAcceptorImpl = new SocketOrChannelAcceptorImpl(paramORB, paramInt, paramString1, paramString2);
/*     */     } else {
/*     */       
/* 361 */       socketFactoryAcceptorImpl = new SocketFactoryAcceptorImpl(paramORB, paramInt, paramString1, paramString2);
/*     */     } 
/*     */     
/* 364 */     paramORB.getTransportManager().registerAcceptor((Acceptor)socketFactoryAcceptorImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLegacySocketFactoryORB(final ORB orb, final ORBSocketFactory legacySocketFactory) {
/* 370 */     if (legacySocketFactory == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 380 */       AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */           {
/*     */             
/*     */             public Object run() throws InstantiationException, IllegalAccessException
/*     */             {
/*     */               try {
/* 386 */                 Class[] arrayOfClass = { ORB.class };
/*     */                 
/* 388 */                 Method method = legacySocketFactory.getClass().getMethod("setORB", arrayOfClass);
/*     */                 
/* 390 */                 Object[] arrayOfObject = { orb };
/* 391 */                 method.invoke(legacySocketFactory, arrayOfObject);
/* 392 */               } catch (NoSuchMethodException noSuchMethodException) {
/*     */ 
/*     */               
/*     */               }
/* 396 */               catch (IllegalAccessException illegalAccessException) {
/* 397 */                 RuntimeException runtimeException = new RuntimeException();
/* 398 */                 runtimeException.initCause(illegalAccessException);
/* 399 */                 throw runtimeException;
/* 400 */               } catch (InvocationTargetException invocationTargetException) {
/* 401 */                 RuntimeException runtimeException = new RuntimeException();
/* 402 */                 runtimeException.initCause(invocationTargetException);
/* 403 */                 throw runtimeException;
/*     */               } 
/* 405 */               return null;
/*     */             }
/*     */           });
/*     */     }
/* 409 */     catch (Throwable throwable) {
/* 410 */       throw this.wrapper.unableToSetSocketFactoryOrb(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeNaming(ORB paramORB) {
/* 416 */     LocalResolver localResolver = ResolverDefault.makeLocalResolver();
/* 417 */     paramORB.setLocalResolver(localResolver);
/*     */     
/* 419 */     Resolver resolver1 = ResolverDefault.makeBootstrapResolver(paramORB, paramORB
/* 420 */         .getORBData().getORBInitialHost(), paramORB
/* 421 */         .getORBData().getORBInitialPort());
/*     */     
/* 423 */     Operation operation = ResolverDefault.makeINSURLOperation(paramORB, resolver1);
/*     */     
/* 425 */     paramORB.setURLOperation(operation);
/*     */     
/* 427 */     Resolver resolver2 = ResolverDefault.makeORBInitRefResolver(operation, paramORB
/* 428 */         .getORBData().getORBInitialReferences());
/*     */     
/* 430 */     Resolver resolver3 = ResolverDefault.makeORBDefaultInitRefResolver(operation, paramORB
/* 431 */         .getORBData().getORBDefaultInitialReference());
/*     */ 
/*     */     
/* 434 */     Resolver resolver4 = ResolverDefault.makeCompositeResolver((Resolver)localResolver, 
/* 435 */         ResolverDefault.makeCompositeResolver(resolver2, 
/* 436 */           ResolverDefault.makeCompositeResolver(resolver3, resolver1)));
/*     */     
/* 438 */     paramORB.setResolver(resolver4);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initServiceContextRegistry(ORB paramORB) {
/* 443 */     ServiceContextRegistry serviceContextRegistry = paramORB.getServiceContextRegistry();
/*     */     
/* 445 */     serviceContextRegistry.register(UEInfoServiceContext.class);
/* 446 */     serviceContextRegistry.register(CodeSetServiceContext.class);
/* 447 */     serviceContextRegistry.register(SendingContextServiceContext.class);
/* 448 */     serviceContextRegistry.register(ORBVersionServiceContext.class);
/* 449 */     serviceContextRegistry.register(MaxStreamFormatVersionServiceContext.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerInitialReferences(final ORB orb) {
/* 455 */     Closure closure1 = new Closure() {
/*     */         public Object evaluate() {
/* 457 */           return new DynAnyFactoryImpl(orb);
/*     */         }
/*     */       };
/*     */     
/* 461 */     Closure closure2 = ClosureFactory.makeFuture(closure1);
/* 462 */     orb.getLocalResolver().register("DynAnyFactory", closure2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initObjectCopiers(ORB paramORB) {
/* 472 */     ObjectCopierFactory objectCopierFactory = CopyobjectDefaults.makeORBStreamObjectCopierFactory(paramORB);
/*     */     
/* 474 */     CopierManager copierManager = paramORB.getCopierManager();
/* 475 */     copierManager.setDefaultId(0);
/*     */     
/* 477 */     copierManager.registerObjectCopierFactory(objectCopierFactory, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initIORFinders(ORB paramORB) {
/* 483 */     IdentifiableFactoryFinder identifiableFactoryFinder1 = paramORB.getTaggedProfileFactoryFinder();
/* 484 */     identifiableFactoryFinder1.registerFactory(IIOPFactories.makeIIOPProfileFactory());
/*     */ 
/*     */     
/* 487 */     IdentifiableFactoryFinder identifiableFactoryFinder2 = paramORB.getTaggedProfileTemplateFactoryFinder();
/* 488 */     identifiableFactoryFinder2.registerFactory(
/* 489 */         IIOPFactories.makeIIOPProfileTemplateFactory());
/*     */ 
/*     */     
/* 492 */     TaggedComponentFactoryFinder taggedComponentFactoryFinder = paramORB.getTaggedComponentFactoryFinder();
/* 493 */     taggedComponentFactoryFinder.registerFactory(
/* 494 */         IIOPFactories.makeCodeSetsComponentFactory());
/* 495 */     taggedComponentFactoryFinder.registerFactory(
/* 496 */         IIOPFactories.makeJavaCodebaseComponentFactory());
/* 497 */     taggedComponentFactoryFinder.registerFactory(
/* 498 */         IIOPFactories.makeORBTypeComponentFactory());
/* 499 */     taggedComponentFactoryFinder.registerFactory(
/* 500 */         IIOPFactories.makeMaxStreamFormatVersionComponentFactory());
/* 501 */     taggedComponentFactoryFinder.registerFactory(
/* 502 */         IIOPFactories.makeAlternateIIOPAddressComponentFactory());
/* 503 */     taggedComponentFactoryFinder.registerFactory(
/* 504 */         IIOPFactories.makeRequestPartitioningComponentFactory());
/* 505 */     taggedComponentFactoryFinder.registerFactory(
/* 506 */         IIOPFactories.makeJavaSerializationComponentFactory());
/*     */ 
/*     */     
/* 509 */     IORFactories.registerValueFactories(paramORB);
/*     */ 
/*     */     
/* 512 */     paramORB.setObjectKeyFactory(IORFactories.makeObjectKeyFactory(paramORB));
/*     */   }
/*     */ 
/*     */   
/*     */   private void initRequestDispatcherRegistry(ORB paramORB) {
/* 517 */     RequestDispatcherRegistry requestDispatcherRegistry = paramORB.getRequestDispatcherRegistry();
/*     */ 
/*     */ 
/*     */     
/* 521 */     ClientRequestDispatcher clientRequestDispatcher = RequestDispatcherDefault.makeClientRequestDispatcher();
/* 522 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, 2);
/*     */     
/* 524 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, 32);
/*     */     
/* 526 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, ORBConstants.PERSISTENT_SCID);
/*     */     
/* 528 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, 36);
/*     */     
/* 530 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, ORBConstants.SC_PERSISTENT_SCID);
/*     */     
/* 532 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, 40);
/*     */     
/* 534 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, ORBConstants.IISC_PERSISTENT_SCID);
/*     */     
/* 536 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, 44);
/*     */     
/* 538 */     requestDispatcherRegistry.registerClientRequestDispatcher(clientRequestDispatcher, ORBConstants.MINSC_PERSISTENT_SCID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 543 */     CorbaServerRequestDispatcher corbaServerRequestDispatcher1 = RequestDispatcherDefault.makeServerRequestDispatcher(paramORB);
/* 544 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, 2);
/*     */     
/* 546 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, 32);
/*     */     
/* 548 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, ORBConstants.PERSISTENT_SCID);
/*     */     
/* 550 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, 36);
/*     */     
/* 552 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, ORBConstants.SC_PERSISTENT_SCID);
/*     */     
/* 554 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, 40);
/*     */     
/* 556 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, ORBConstants.IISC_PERSISTENT_SCID);
/*     */     
/* 558 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, 44);
/*     */     
/* 560 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher1, ORBConstants.MINSC_PERSISTENT_SCID);
/*     */ 
/*     */     
/* 563 */     paramORB.setINSDelegate(
/* 564 */         RequestDispatcherDefault.makeINSServerRequestDispatcher(paramORB));
/*     */ 
/*     */ 
/*     */     
/* 568 */     LocalClientRequestDispatcherFactory localClientRequestDispatcherFactory = RequestDispatcherDefault.makeJIDLLocalClientRequestDispatcherFactory(paramORB);
/*     */     
/* 570 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, 2);
/*     */ 
/*     */ 
/*     */     
/* 574 */     localClientRequestDispatcherFactory = RequestDispatcherDefault.makePOALocalClientRequestDispatcherFactory(paramORB);
/*     */     
/* 576 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, 32);
/*     */     
/* 578 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, ORBConstants.PERSISTENT_SCID);
/*     */ 
/*     */ 
/*     */     
/* 582 */     localClientRequestDispatcherFactory = RequestDispatcherDefault.makeFullServantCacheLocalClientRequestDispatcherFactory(paramORB);
/* 583 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, 36);
/*     */     
/* 585 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, ORBConstants.SC_PERSISTENT_SCID);
/*     */ 
/*     */ 
/*     */     
/* 589 */     localClientRequestDispatcherFactory = RequestDispatcherDefault.makeInfoOnlyServantCacheLocalClientRequestDispatcherFactory(paramORB);
/* 590 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, 40);
/*     */     
/* 592 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, ORBConstants.IISC_PERSISTENT_SCID);
/*     */ 
/*     */ 
/*     */     
/* 596 */     localClientRequestDispatcherFactory = RequestDispatcherDefault.makeMinimalServantCacheLocalClientRequestDispatcherFactory(paramORB);
/* 597 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, 44);
/*     */     
/* 599 */     requestDispatcherRegistry.registerLocalClientRequestDispatcherFactory(localClientRequestDispatcherFactory, ORBConstants.MINSC_PERSISTENT_SCID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     CorbaServerRequestDispatcher corbaServerRequestDispatcher2 = RequestDispatcherDefault.makeBootstrapServerRequestDispatcher(paramORB);
/*     */     
/* 609 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher2, "INIT");
/* 610 */     requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher2, "TINI");
/*     */ 
/*     */     
/* 613 */     ObjectAdapterFactory objectAdapterFactory = OADefault.makeTOAFactory(paramORB);
/* 614 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, 2);
/*     */     
/* 616 */     objectAdapterFactory = OADefault.makePOAFactory(paramORB);
/* 617 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, 32);
/* 618 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, ORBConstants.PERSISTENT_SCID);
/* 619 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, 36);
/* 620 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, ORBConstants.SC_PERSISTENT_SCID);
/* 621 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, 40);
/* 622 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, ORBConstants.IISC_PERSISTENT_SCID);
/* 623 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, 44);
/* 624 */     requestDispatcherRegistry.registerObjectAdapterFactory(objectAdapterFactory, ORBConstants.MINSC_PERSISTENT_SCID);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/ORBConfiguratorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */