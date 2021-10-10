/*     */ package com.sun.corba.se.spi.orb;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.TypeCodeFactory;
/*     */ import com.sun.corba.se.impl.corba.TypeCodeImpl;
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.oa.poa.BadServerIdHandler;
/*     */ import com.sun.corba.se.impl.presentation.rmi.PresentationManagerImpl;
/*     */ import com.sun.corba.se.impl.transport.ByteBufferPoolImpl;
/*     */ import com.sun.corba.se.org.omg.CORBA.ORB;
/*     */ import com.sun.corba.se.pept.broker.Broker;
/*     */ import com.sun.corba.se.pept.transport.ByteBufferPool;
/*     */ import com.sun.corba.se.spi.copyobject.CopierManager;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyFactory;
/*     */ import com.sun.corba.se.spi.ior.TaggedComponentFactoryFinder;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketManager;
/*     */ import com.sun.corba.se.spi.logging.LogWrapperBase;
/*     */ import com.sun.corba.se.spi.logging.LogWrapperFactory;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoringFactories;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoringManager;
/*     */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;
/*     */ import com.sun.corba.se.spi.presentation.rmi.PresentationDefaults;
/*     */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*     */ import com.sun.corba.se.spi.protocol.ClientDelegateFactory;
/*     */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*     */ import com.sun.corba.se.spi.protocol.PIHandler;
/*     */ import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
/*     */ import com.sun.corba.se.spi.resolver.LocalResolver;
/*     */ import com.sun.corba.se.spi.resolver.Resolver;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContextRegistry;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoListFactory;
/*     */ import com.sun.corba.se.spi.transport.CorbaTransportManager;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Logger;
/*     */ import sun.awt.AppContext;
/*     */ import sun.corba.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ORB
/*     */   extends ORB
/*     */   implements Broker, TypeCodeFactory
/*     */ {
/*     */   public static boolean ORBInitDebug = false;
/*     */   public boolean transportDebugFlag = false;
/*     */   public boolean subcontractDebugFlag = false;
/*     */   public boolean poaDebugFlag = false;
/*     */   public boolean poaConcurrencyDebugFlag = false;
/*     */   public boolean poaFSMDebugFlag = false;
/*     */   public boolean orbdDebugFlag = false;
/*     */   public boolean namingDebugFlag = false;
/*     */   public boolean serviceContextDebugFlag = false;
/*     */   public boolean transientObjectManagerDebugFlag = false;
/*     */   public boolean giopVersionDebugFlag = false;
/*     */   public boolean shutdownDebugFlag = false;
/*     */   public boolean giopDebugFlag = false;
/*     */   public boolean invocationTimingDebugFlag = false;
/*     */   public boolean orbInitDebugFlag = false;
/*     */   protected static ORBUtilSystemException staticWrapper;
/*     */   protected ORBUtilSystemException wrapper;
/*     */   protected OMGSystemException omgWrapper;
/*     */   private Map typeCodeMap;
/*     */   private TypeCodeImpl[] primitiveTypeCodeConstants;
/*     */   ByteBufferPool byteBufferPool;
/*     */   private Map wrapperMap;
/*     */   
/*     */   static class Holder
/*     */   {
/* 171 */     static final PresentationManager defaultPresentationManager = ORB.setupPresentationManager();
/*     */   }
/* 173 */   private static final Object pmLock = new Object();
/*     */   
/* 175 */   private static Map staticWrapperMap = new ConcurrentHashMap<>();
/*     */   
/*     */   protected MonitoringManager monitoringManager;
/*     */   
/*     */   private static PresentationManager setupPresentationManager() {
/* 180 */     staticWrapper = ORBUtilSystemException.get("rpc.presentation");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     boolean bool = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() { public Object run() { return Boolean.valueOf(Boolean.getBoolean("com.sun.CORBA.ORBUseDynamicStub")); } })).booleanValue();
/*     */ 
/*     */     
/* 194 */     PresentationManager.StubFactoryFactory stubFactoryFactory = AccessController.<PresentationManager.StubFactoryFactory>doPrivileged(new PrivilegedAction<PresentationManager.StubFactoryFactory>()
/*     */         {
/*     */           public Object run()
/*     */           {
/* 198 */             PresentationManager.StubFactoryFactory stubFactoryFactory = PresentationDefaults.getProxyStubFactoryFactory();
/*     */             
/* 200 */             String str = System.getProperty("com.sun.CORBA.ORBDynamicStubFactoryFactoryClass", "com.sun.corba.se.impl.presentation.rmi.bcel.StubFactoryFactoryBCELImpl");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 206 */               Class<PresentationManager.StubFactoryFactory> clazz = SharedSecrets.getJavaCorbaAccess().loadClass(str);
/* 207 */               stubFactoryFactory = clazz.newInstance();
/* 208 */             } catch (Exception exception) {
/*     */               
/* 210 */               ORB.staticWrapper.errorInSettingDynamicStubFactoryFactory(exception, str);
/*     */             } 
/*     */ 
/*     */             
/* 214 */             return stubFactoryFactory;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 219 */     PresentationManagerImpl presentationManagerImpl = new PresentationManagerImpl(bool);
/* 220 */     presentationManagerImpl.setStubFactoryFactory(false, 
/* 221 */         PresentationDefaults.getStaticStubFactoryFactory());
/* 222 */     presentationManagerImpl.setStubFactoryFactory(true, stubFactoryFactory);
/* 223 */     return (PresentationManager)presentationManagerImpl;
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 227 */     this.wrapper = null;
/* 228 */     this.omgWrapper = null;
/* 229 */     this.typeCodeMap = null;
/* 230 */     this.primitiveTypeCodeConstants = null;
/* 231 */     this.byteBufferPool = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PresentationManager getPresentationManager() {
/* 240 */     SecurityManager securityManager = System.getSecurityManager();
/* 241 */     if (securityManager != null && AppContext.getAppContexts().size() > 0) {
/* 242 */       AppContext appContext = AppContext.getAppContext();
/* 243 */       if (appContext != null) {
/* 244 */         synchronized (pmLock) {
/*     */           
/* 246 */           PresentationManager presentationManager = (PresentationManager)appContext.get(PresentationManager.class);
/* 247 */           if (presentationManager == null) {
/* 248 */             presentationManager = setupPresentationManager();
/* 249 */             appContext.put(PresentationManager.class, presentationManager);
/*     */           } 
/* 251 */           return presentationManager;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 257 */     return Holder.defaultPresentationManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PresentationManager.StubFactoryFactory getStubFactoryFactory() {
/* 267 */     PresentationManager presentationManager = getPresentationManager();
/* 268 */     boolean bool = presentationManager.useDynamicStubs();
/* 269 */     return presentationManager.getStubFactoryFactory(bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ORB() {
/* 276 */     this.wrapperMap = new ConcurrentHashMap<>();
/* 277 */     this.wrapper = ORBUtilSystemException.get(this, "rpc.presentation");
/*     */     
/* 279 */     this.omgWrapper = OMGSystemException.get(this, "rpc.presentation");
/*     */ 
/*     */     
/* 282 */     this.typeCodeMap = new HashMap<>();
/*     */     
/* 284 */     this.primitiveTypeCodeConstants = new TypeCodeImpl[] { new TypeCodeImpl(this, 0), new TypeCodeImpl(this, 1), new TypeCodeImpl(this, 2), new TypeCodeImpl(this, 3), new TypeCodeImpl(this, 4), new TypeCodeImpl(this, 5), new TypeCodeImpl(this, 6), new TypeCodeImpl(this, 7), new TypeCodeImpl(this, 8), new TypeCodeImpl(this, 9), new TypeCodeImpl(this, 10), new TypeCodeImpl(this, 11), new TypeCodeImpl(this, 12), new TypeCodeImpl(this, 13), new TypeCodeImpl(this, 14), null, null, null, new TypeCodeImpl(this, 18), null, null, null, null, new TypeCodeImpl(this, 23), new TypeCodeImpl(this, 24), new TypeCodeImpl(this, 25), new TypeCodeImpl(this, 26), new TypeCodeImpl(this, 27), new TypeCodeImpl(this, 28), new TypeCodeImpl(this, 29), new TypeCodeImpl(this, 30), new TypeCodeImpl(this, 31), new TypeCodeImpl(this, 32) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     this
/*     */       
/* 322 */       .monitoringManager = MonitoringFactories.getMonitoringManagerFactory().createMonitoringManager("orb", "ORB Management and Monitoring Root");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCodeImpl get_primitive_tc(int paramInt) {
/* 330 */     synchronized (this) {
/* 331 */       checkShutdownState();
/*     */     } 
/*     */     try {
/* 334 */       return this.primitiveTypeCodeConstants[paramInt];
/* 335 */     } catch (Throwable throwable) {
/* 336 */       throw this.wrapper.invalidTypecodeKind(throwable, new Integer(paramInt));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setTypeCode(String paramString, TypeCodeImpl paramTypeCodeImpl) {
/* 342 */     checkShutdownState();
/* 343 */     this.typeCodeMap.put(paramString, paramTypeCodeImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized TypeCodeImpl getTypeCode(String paramString) {
/* 348 */     checkShutdownState();
/* 349 */     return (TypeCodeImpl)this.typeCodeMap.get(paramString);
/*     */   }
/*     */   
/*     */   public MonitoringManager getMonitoringManager() {
/* 353 */     synchronized (this) {
/* 354 */       checkShutdownState();
/*     */     } 
/* 356 */     return this.monitoringManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Logger getLogger(String paramString) {
/*     */     String str;
/* 470 */     synchronized (this) {
/* 471 */       checkShutdownState();
/*     */     } 
/* 473 */     ORBData oRBData = getORBData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 485 */     if (oRBData == null) {
/* 486 */       str = "_INITIALIZING_";
/*     */     } else {
/* 488 */       str = oRBData.getORBId();
/* 489 */       if (str.equals("")) {
/* 490 */         str = "_DEFAULT_";
/*     */       }
/*     */     } 
/* 493 */     return getCORBALogger(str, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Logger staticGetLogger(String paramString) {
/* 498 */     return getCORBALogger("_CORBA_", paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Logger getCORBALogger(String paramString1, String paramString2) {
/* 503 */     String str = "javax.enterprise.resource.corba." + paramString1 + "." + paramString2;
/*     */ 
/*     */     
/* 506 */     return Logger.getLogger(str, "com.sun.corba.se.impl.logging.LogStrings");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogWrapperBase getLogWrapper(String paramString1, String paramString2, LogWrapperFactory paramLogWrapperFactory) {
/* 515 */     StringPair stringPair = new StringPair(paramString1, paramString2);
/*     */     
/* 517 */     LogWrapperBase logWrapperBase = (LogWrapperBase)this.wrapperMap.get(stringPair);
/* 518 */     if (logWrapperBase == null) {
/* 519 */       logWrapperBase = paramLogWrapperFactory.create(getLogger(paramString1));
/* 520 */       this.wrapperMap.put(stringPair, logWrapperBase);
/*     */     } 
/*     */     
/* 523 */     return logWrapperBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LogWrapperBase staticGetLogWrapper(String paramString1, String paramString2, LogWrapperFactory paramLogWrapperFactory) {
/* 532 */     StringPair stringPair = new StringPair(paramString1, paramString2);
/*     */     
/* 534 */     LogWrapperBase logWrapperBase = (LogWrapperBase)staticWrapperMap.get(stringPair);
/* 535 */     if (logWrapperBase == null) {
/* 536 */       logWrapperBase = paramLogWrapperFactory.create(staticGetLogger(paramString1));
/* 537 */       staticWrapperMap.put(stringPair, logWrapperBase);
/*     */     } 
/*     */     
/* 540 */     return logWrapperBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufferPool getByteBufferPool() {
/* 549 */     synchronized (this) {
/* 550 */       checkShutdownState();
/*     */     } 
/* 552 */     if (this.byteBufferPool == null) {
/* 553 */       this.byteBufferPool = (ByteBufferPool)new ByteBufferPoolImpl(this);
/*     */     }
/* 555 */     return this.byteBufferPool;
/*     */   }
/*     */   
/*     */   public abstract boolean isLocalHost(String paramString);
/*     */   
/*     */   public abstract boolean isLocalServerId(int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract OAInvocationInfo peekInvocationInfo();
/*     */   
/*     */   public abstract void pushInvocationInfo(OAInvocationInfo paramOAInvocationInfo);
/*     */   
/*     */   public abstract OAInvocationInfo popInvocationInfo();
/*     */   
/*     */   public abstract CorbaTransportManager getCorbaTransportManager();
/*     */   
/*     */   public abstract LegacyServerSocketManager getLegacyServerSocketManager();
/*     */   
/*     */   public abstract void set_parameters(Properties paramProperties);
/*     */   
/*     */   public abstract ORBVersion getORBVersion();
/*     */   
/*     */   public abstract void setORBVersion(ORBVersion paramORBVersion);
/*     */   
/*     */   public abstract IOR getFVDCodeBaseIOR();
/*     */   
/*     */   public abstract void handleBadServerId(ObjectKey paramObjectKey);
/*     */   
/*     */   public abstract void setBadServerIdHandler(BadServerIdHandler paramBadServerIdHandler);
/*     */   
/*     */   public abstract void initBadServerIdHandler();
/*     */   
/*     */   public abstract void notifyORB();
/*     */   
/*     */   public abstract PIHandler getPIHandler();
/*     */   
/*     */   public abstract void checkShutdownState();
/*     */   
/*     */   public abstract boolean isDuringDispatch();
/*     */   
/*     */   public abstract void startingDispatch();
/*     */   
/*     */   public abstract void finishedDispatch();
/*     */   
/*     */   public abstract int getTransientServerId();
/*     */   
/*     */   public abstract ServiceContextRegistry getServiceContextRegistry();
/*     */   
/*     */   public abstract RequestDispatcherRegistry getRequestDispatcherRegistry();
/*     */   
/*     */   public abstract ORBData getORBData();
/*     */   
/*     */   public abstract void setClientDelegateFactory(ClientDelegateFactory paramClientDelegateFactory);
/*     */   
/*     */   public abstract ClientDelegateFactory getClientDelegateFactory();
/*     */   
/*     */   public abstract void setCorbaContactInfoListFactory(CorbaContactInfoListFactory paramCorbaContactInfoListFactory);
/*     */   
/*     */   public abstract CorbaContactInfoListFactory getCorbaContactInfoListFactory();
/*     */   
/*     */   public abstract void setResolver(Resolver paramResolver);
/*     */   
/*     */   public abstract Resolver getResolver();
/*     */   
/*     */   public abstract void setLocalResolver(LocalResolver paramLocalResolver);
/*     */   
/*     */   public abstract LocalResolver getLocalResolver();
/*     */   
/*     */   public abstract void setURLOperation(Operation paramOperation);
/*     */   
/*     */   public abstract Operation getURLOperation();
/*     */   
/*     */   public abstract void setINSDelegate(CorbaServerRequestDispatcher paramCorbaServerRequestDispatcher);
/*     */   
/*     */   public abstract TaggedComponentFactoryFinder getTaggedComponentFactoryFinder();
/*     */   
/*     */   public abstract IdentifiableFactoryFinder getTaggedProfileFactoryFinder();
/*     */   
/*     */   public abstract IdentifiableFactoryFinder getTaggedProfileTemplateFactoryFinder();
/*     */   
/*     */   public abstract ObjectKeyFactory getObjectKeyFactory();
/*     */   
/*     */   public abstract void setObjectKeyFactory(ObjectKeyFactory paramObjectKeyFactory);
/*     */   
/*     */   public abstract void setThreadPoolManager(ThreadPoolManager paramThreadPoolManager);
/*     */   
/*     */   public abstract ThreadPoolManager getThreadPoolManager();
/*     */   
/*     */   public abstract CopierManager getCopierManager();
/*     */   
/*     */   public abstract void validateIORClass(String paramString);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orb/ORB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */