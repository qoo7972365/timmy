/*      */ package com.sun.corba.se.impl.orb;
/*      */ 
/*      */ import com.sun.corba.se.impl.copyobject.CopierManagerImpl;
/*      */ import com.sun.corba.se.impl.corba.AnyImpl;
/*      */ import com.sun.corba.se.impl.corba.AsynchInvoke;
/*      */ import com.sun.corba.se.impl.corba.ContextListImpl;
/*      */ import com.sun.corba.se.impl.corba.EnvironmentImpl;
/*      */ import com.sun.corba.se.impl.corba.ExceptionListImpl;
/*      */ import com.sun.corba.se.impl.corba.NVListImpl;
/*      */ import com.sun.corba.se.impl.corba.NamedValueImpl;
/*      */ import com.sun.corba.se.impl.corba.RequestImpl;
/*      */ import com.sun.corba.se.impl.corba.TypeCodeImpl;
/*      */ import com.sun.corba.se.impl.encoding.CachedCodeBase;
/*      */ import com.sun.corba.se.impl.interceptors.PIHandlerImpl;
/*      */ import com.sun.corba.se.impl.interceptors.PINoOpHandlerImpl;
/*      */ import com.sun.corba.se.impl.ior.IORTypeCheckRegistryImpl;
/*      */ import com.sun.corba.se.impl.ior.TaggedComponentFactoryFinderImpl;
/*      */ import com.sun.corba.se.impl.ior.TaggedProfileFactoryFinderImpl;
/*      */ import com.sun.corba.se.impl.ior.TaggedProfileTemplateFactoryFinderImpl;
/*      */ import com.sun.corba.se.impl.legacy.connection.LegacyServerSocketManagerImpl;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.oa.poa.BadServerIdHandler;
/*      */ import com.sun.corba.se.impl.oa.poa.POAFactory;
/*      */ import com.sun.corba.se.impl.oa.toa.TOAFactory;
/*      */ import com.sun.corba.se.impl.orbutil.ORBConstants;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.impl.orbutil.StackImpl;
/*      */ import com.sun.corba.se.impl.orbutil.threadpool.ThreadPoolManagerImpl;
/*      */ import com.sun.corba.se.impl.protocol.CorbaInvocationInfo;
/*      */ import com.sun.corba.se.impl.protocol.RequestDispatcherRegistryImpl;
/*      */ import com.sun.corba.se.impl.transport.CorbaTransportManagerImpl;
/*      */ import com.sun.corba.se.impl.util.Utility;
/*      */ import com.sun.corba.se.pept.protocol.ClientInvocationInfo;
/*      */ import com.sun.corba.se.pept.transport.TransportManager;
/*      */ import com.sun.corba.se.spi.copyobject.CopierManager;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.IORFactories;
/*      */ import com.sun.corba.se.spi.ior.IORTypeCheckRegistry;
/*      */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*      */ import com.sun.corba.se.spi.ior.ObjectKey;
/*      */ import com.sun.corba.se.spi.ior.ObjectKeyFactory;
/*      */ import com.sun.corba.se.spi.ior.TaggedComponentFactoryFinder;
/*      */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketManager;
/*      */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*      */ import com.sun.corba.se.spi.oa.ObjectAdapterFactory;
/*      */ import com.sun.corba.se.spi.orb.DataCollector;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.orb.ORBConfigurator;
/*      */ import com.sun.corba.se.spi.orb.ORBData;
/*      */ import com.sun.corba.se.spi.orb.ORBVersion;
/*      */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*      */ import com.sun.corba.se.spi.orb.Operation;
/*      */ import com.sun.corba.se.spi.orb.OperationFactory;
/*      */ import com.sun.corba.se.spi.orb.ParserImplBase;
/*      */ import com.sun.corba.se.spi.orb.PropertyParser;
/*      */ import com.sun.corba.se.spi.orbutil.closure.ClosureFactory;
/*      */ import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;
/*      */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*      */ import com.sun.corba.se.spi.protocol.ClientDelegateFactory;
/*      */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*      */ import com.sun.corba.se.spi.protocol.PIHandler;
/*      */ import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
/*      */ import com.sun.corba.se.spi.resolver.LocalResolver;
/*      */ import com.sun.corba.se.spi.resolver.Resolver;
/*      */ import com.sun.corba.se.spi.servicecontext.ServiceContextRegistry;
/*      */ import com.sun.corba.se.spi.transport.CorbaContactInfoListFactory;
/*      */ import com.sun.corba.se.spi.transport.CorbaTransportManager;
/*      */ import com.sun.org.omg.SendingContext.CodeBase;
/*      */ import java.applet.Applet;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.net.InetAddress;
/*      */ import java.net.UnknownHostException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.Security;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.WeakHashMap;
/*      */ import javax.rmi.CORBA.Util;
/*      */ import javax.rmi.CORBA.ValueHandler;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.BAD_PARAM;
/*      */ import org.omg.CORBA.CompletionStatus;
/*      */ import org.omg.CORBA.Context;
/*      */ import org.omg.CORBA.ContextList;
/*      */ import org.omg.CORBA.Current;
/*      */ import org.omg.CORBA.Environment;
/*      */ import org.omg.CORBA.ExceptionList;
/*      */ import org.omg.CORBA.MARSHAL;
/*      */ import org.omg.CORBA.NVList;
/*      */ import org.omg.CORBA.NamedValue;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.ORBPackage.InvalidName;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.Policy;
/*      */ import org.omg.CORBA.PolicyError;
/*      */ import org.omg.CORBA.Request;
/*      */ import org.omg.CORBA.StructMember;
/*      */ import org.omg.CORBA.TCKind;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.UnionMember;
/*      */ import org.omg.CORBA.ValueMember;
/*      */ import org.omg.CORBA.WrongTransaction;
/*      */ import org.omg.CORBA.portable.OutputStream;
/*      */ import org.omg.CORBA.portable.ValueFactory;
/*      */ import org.omg.PortableServer.Servant;
/*      */ import sun.corba.OutputStreamFactory;
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
/*      */ public class ORBImpl
/*      */   extends ORB
/*      */ {
/*      */   protected TransportManager transportManager;
/*      */   protected LegacyServerSocketManager legacyServerSocketManager;
/*      */   private ThreadLocal OAInvocationInfoStack;
/*      */   private ThreadLocal clientInvocationInfoStack;
/*      */   private static IOR codeBaseIOR;
/*      */   private Vector dynamicRequests;
/*      */   private SynchVariable svResponseReceived;
/*  159 */   private Object runObj = new Object();
/*  160 */   private Object shutdownObj = new Object();
/*  161 */   private Object waitForCompletionObj = new Object();
/*      */   private static final byte STATUS_OPERATING = 1;
/*      */   private static final byte STATUS_SHUTTING_DOWN = 2;
/*      */   private static final byte STATUS_SHUTDOWN = 3;
/*      */   private static final byte STATUS_DESTROYED = 4;
/*  166 */   private byte status = 1;
/*      */ 
/*      */   
/*  169 */   private Object invocationObj = new Object();
/*  170 */   private int numInvocations = 0;
/*      */ 
/*      */ 
/*      */   
/*  174 */   private ThreadLocal isProcessingInvocation = new ThreadLocal() {
/*      */       protected Object initialValue() {
/*  176 */         return Boolean.FALSE;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   private Map typeCodeForClassMap;
/*      */ 
/*      */   
/*  185 */   private Hashtable valueFactoryCache = new Hashtable<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private ThreadLocal orbVersionThreadLocal;
/*      */ 
/*      */ 
/*      */   
/*      */   private RequestDispatcherRegistry requestDispatcherRegistry;
/*      */ 
/*      */ 
/*      */   
/*      */   private CopierManager copierManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private int transientServerId;
/*      */ 
/*      */   
/*      */   private ServiceContextRegistry serviceContextRegistry;
/*      */ 
/*      */   
/*      */   private IORTypeCheckRegistry iorTypeCheckRegistry;
/*      */ 
/*      */   
/*      */   private TOAFactory toaFactory;
/*      */ 
/*      */   
/*      */   private POAFactory poaFactory;
/*      */ 
/*      */   
/*      */   private PIHandler pihandler;
/*      */ 
/*      */   
/*      */   private ORBData configData;
/*      */ 
/*      */   
/*      */   private BadServerIdHandler badServerIdHandler;
/*      */ 
/*      */   
/*      */   private ClientDelegateFactory clientDelegateFactory;
/*      */ 
/*      */   
/*      */   private CorbaContactInfoListFactory corbaContactInfoListFactory;
/*      */ 
/*      */   
/*      */   private Resolver resolver;
/*      */ 
/*      */   
/*      */   private LocalResolver localResolver;
/*      */ 
/*      */   
/*      */   private Operation urlOperation;
/*      */ 
/*      */   
/*  240 */   private final Object urlOperationLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*      */   private CorbaServerRequestDispatcher insNamingDelegate;
/*      */ 
/*      */ 
/*      */   
/*  248 */   private final Object resolverLock = new Object();
/*      */   
/*      */   private static final String IORTYPECHECKREGISTRY_FILTER_PROPNAME = "com.sun.CORBA.ORBIorTypeCheckRegistryFilter";
/*      */   
/*      */   private TaggedComponentFactoryFinder taggedComponentFactoryFinder;
/*      */   
/*      */   private IdentifiableFactoryFinder taggedProfileFactoryFinder;
/*      */   
/*      */   private IdentifiableFactoryFinder taggedProfileTemplateFactoryFinder;
/*      */   
/*      */   private ObjectKeyFactory objectKeyFactory;
/*      */   
/*      */   private boolean orbOwnsThreadPoolManager = false;
/*      */   
/*      */   private ThreadPoolManager threadpoolMgr;
/*      */   private Object badServerIdHandlerAccessLock;
/*      */   
/*      */   private void dprint(String paramString) {
/*  266 */     ORBUtility.dprint(this, paramString);
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
/*      */   public ORBData getORBData() {
/*  286 */     return this.configData;
/*      */   }
/*      */ 
/*      */   
/*      */   public PIHandler getPIHandler() {
/*  291 */     return this.pihandler;
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
/*      */   public ORBVersion getORBVersion() {
/*  305 */     synchronized (this) {
/*  306 */       checkShutdownState();
/*      */     } 
/*  308 */     return this.orbVersionThreadLocal.get();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setORBVersion(ORBVersion paramORBVersion) {
/*  313 */     synchronized (this) {
/*  314 */       checkShutdownState();
/*      */     } 
/*  316 */     this.orbVersionThreadLocal.set(paramORBVersion);
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
/*      */   private void preInit(String[] paramArrayOfString, Properties paramProperties) {
/*  334 */     this.pihandler = (PIHandler)new PINoOpHandlerImpl();
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
/*  349 */     this.transientServerId = (int)System.currentTimeMillis();
/*      */     
/*  351 */     this.orbVersionThreadLocal = new ThreadLocal()
/*      */       {
/*      */         protected Object initialValue() {
/*  354 */           return ORBVersionFactory.getORBVersion();
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  359 */     this.requestDispatcherRegistry = (RequestDispatcherRegistry)new RequestDispatcherRegistryImpl(this, 2);
/*      */     
/*  361 */     this.copierManager = (CopierManager)new CopierManagerImpl(this);
/*      */     
/*  363 */     this.taggedComponentFactoryFinder = (TaggedComponentFactoryFinder)new TaggedComponentFactoryFinderImpl(this);
/*      */     
/*  365 */     this.taggedProfileFactoryFinder = (IdentifiableFactoryFinder)new TaggedProfileFactoryFinderImpl(this);
/*      */     
/*  367 */     this.taggedProfileTemplateFactoryFinder = (IdentifiableFactoryFinder)new TaggedProfileTemplateFactoryFinderImpl(this);
/*      */ 
/*      */     
/*  370 */     this.dynamicRequests = new Vector();
/*  371 */     this.svResponseReceived = new SynchVariable();
/*      */     
/*  373 */     this.OAInvocationInfoStack = new ThreadLocal()
/*      */       {
/*      */         protected Object initialValue()
/*      */         {
/*  377 */           return new StackImpl();
/*      */         }
/*      */       };
/*      */     
/*  381 */     this.clientInvocationInfoStack = new ThreadLocal()
/*      */       {
/*      */         protected Object initialValue() {
/*  384 */           return new StackImpl();
/*      */         }
/*      */       };
/*      */     
/*  388 */     this.serviceContextRegistry = new ServiceContextRegistry(this);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initIORTypeCheckRegistry() {
/*  393 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */         {
/*      */           public String run() {
/*  396 */             String str = System.getProperty("com.sun.CORBA.ORBIorTypeCheckRegistryFilter");
/*  397 */             if (str == null)
/*      */             {
/*  399 */               str = Security.getProperty("com.sun.CORBA.ORBIorTypeCheckRegistryFilter");
/*      */             }
/*  401 */             return str;
/*      */           }
/*      */         });
/*  404 */     if (str != null) {
/*      */       try {
/*  406 */         this.iorTypeCheckRegistry = (IORTypeCheckRegistry)new IORTypeCheckRegistryImpl(str, this);
/*  407 */       } catch (Exception exception) {
/*  408 */         throw this.wrapper.bootstrapException(exception);
/*      */       } 
/*      */       
/*  411 */       if (this.orbInitDebugFlag) {
/*  412 */         dprint(".initIORTypeCheckRegistry, IORTypeCheckRegistryImpl created for properties == " + str);
/*      */       
/*      */       }
/*      */     }
/*  416 */     else if (this.orbInitDebugFlag) {
/*  417 */       dprint(".initIORTypeCheckRegistry, IORTypeCheckRegistryImpl NOT created for properties == ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDebugFlags(String[] paramArrayOfString) {
/*  424 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  425 */       String str = paramArrayOfString[b];
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  430 */         Field field = getClass().getField(str + "DebugFlag");
/*  431 */         int i = field.getModifiers();
/*  432 */         if (Modifier.isPublic(i) && !Modifier.isStatic(i) && 
/*  433 */           field.getType() == boolean.class)
/*  434 */           field.setBoolean(this, true); 
/*  435 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ConfigParser
/*      */     extends ParserImplBase
/*      */   {
/*  446 */     public Class configurator = ORBConfiguratorImpl.class;
/*      */ 
/*      */     
/*      */     public PropertyParser makeParser() {
/*  450 */       PropertyParser propertyParser = new PropertyParser();
/*  451 */       propertyParser.add("com.sun.CORBA.ORBConfigurator", 
/*  452 */           OperationFactory.classAction(), "configurator");
/*  453 */       return propertyParser;
/*      */     }
/*      */ 
/*      */     
/*      */     private ConfigParser() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private void postInit(String[] paramArrayOfString, DataCollector paramDataCollector) {
/*  462 */     this.configData = new ORBDataParserImpl(this, paramDataCollector);
/*      */ 
/*      */ 
/*      */     
/*  466 */     setDebugFlags(this.configData.getORBDebugFlags());
/*      */ 
/*      */ 
/*      */     
/*  470 */     getTransportManager();
/*  471 */     getLegacyServerSocketManager();
/*      */ 
/*      */     
/*  474 */     ConfigParser configParser = new ConfigParser();
/*  475 */     configParser.init(paramDataCollector);
/*      */     
/*  477 */     ORBConfigurator oRBConfigurator = null;
/*      */     
/*      */     try {
/*  480 */       oRBConfigurator = configParser.configurator.newInstance();
/*  481 */     } catch (Exception exception) {
/*  482 */       throw this.wrapper.badOrbConfigurator(exception, configParser.configurator.getName());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  489 */       oRBConfigurator.configure(paramDataCollector, this);
/*  490 */     } catch (Exception exception) {
/*  491 */       throw this.wrapper.orbConfiguratorError(exception);
/*      */     } 
/*      */ 
/*      */     
/*  495 */     this.pihandler = (PIHandler)new PIHandlerImpl(this, paramArrayOfString);
/*  496 */     this.pihandler.initialize();
/*      */ 
/*      */ 
/*      */     
/*  500 */     getThreadPoolManager();
/*      */     
/*  502 */     getByteBufferPool();
/*      */     
/*  504 */     initIORTypeCheckRegistry();
/*      */   }
/*      */ 
/*      */   
/*      */   private synchronized POAFactory getPOAFactory() {
/*  509 */     if (this.poaFactory == null) {
/*  510 */       this.poaFactory = (POAFactory)this.requestDispatcherRegistry.getObjectAdapterFactory(32);
/*      */     }
/*      */ 
/*      */     
/*  514 */     return this.poaFactory;
/*      */   }
/*      */ 
/*      */   
/*      */   private synchronized TOAFactory getTOAFactory() {
/*  519 */     if (this.toaFactory == null) {
/*  520 */       this.toaFactory = (TOAFactory)this.requestDispatcherRegistry.getObjectAdapterFactory(2);
/*      */     }
/*      */ 
/*      */     
/*  524 */     return this.toaFactory;
/*      */   }
/*      */ 
/*      */   
/*      */   public void set_parameters(Properties paramProperties) {
/*  529 */     synchronized (this) {
/*  530 */       checkShutdownState();
/*      */     } 
/*  532 */     preInit((String[])null, paramProperties);
/*      */     
/*  534 */     DataCollector dataCollector = DataCollectorFactory.create(paramProperties, getLocalHostName());
/*  535 */     postInit((String[])null, dataCollector);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void set_parameters(Applet paramApplet, Properties paramProperties) {
/*  540 */     preInit((String[])null, paramProperties);
/*      */     
/*  542 */     DataCollector dataCollector = DataCollectorFactory.create(paramApplet, paramProperties, getLocalHostName());
/*  543 */     postInit((String[])null, dataCollector);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void set_parameters(String[] paramArrayOfString, Properties paramProperties) {
/*  548 */     preInit(paramArrayOfString, paramProperties);
/*      */     
/*  550 */     DataCollector dataCollector = DataCollectorFactory.create(paramArrayOfString, paramProperties, getLocalHostName());
/*  551 */     postInit(paramArrayOfString, dataCollector);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized OutputStream create_output_stream() {
/*  560 */     checkShutdownState();
/*  561 */     return (OutputStream)OutputStreamFactory.newEncapsOutputStream(this);
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
/*      */   public synchronized Current get_current() {
/*  576 */     checkShutdownState();
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
/*  588 */     throw this.wrapper.genericNoImpl();
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
/*      */   public synchronized NVList create_list(int paramInt) {
/*  601 */     checkShutdownState();
/*  602 */     return (NVList)new NVListImpl(this, paramInt);
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
/*      */   public synchronized NVList create_operation_list(Object paramObject) {
/*  615 */     checkShutdownState();
/*  616 */     throw this.wrapper.genericNoImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized NamedValue create_named_value(String paramString, Any paramAny, int paramInt) {
/*  626 */     checkShutdownState();
/*  627 */     return (NamedValue)new NamedValueImpl(this, paramString, paramAny, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized ExceptionList create_exception_list() {
/*  637 */     checkShutdownState();
/*  638 */     return (ExceptionList)new ExceptionListImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized ContextList create_context_list() {
/*  648 */     checkShutdownState();
/*  649 */     return (ContextList)new ContextListImpl((ORB)this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Context get_default_context() {
/*  659 */     checkShutdownState();
/*  660 */     throw this.wrapper.genericNoImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Environment create_environment() {
/*  670 */     checkShutdownState();
/*  671 */     return (Environment)new EnvironmentImpl();
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void send_multiple_requests_oneway(Request[] paramArrayOfRequest) {
/*  676 */     checkShutdownState();
/*      */ 
/*      */     
/*  679 */     for (byte b = 0; b < paramArrayOfRequest.length; b++) {
/*  680 */       paramArrayOfRequest[b].send_oneway();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void send_multiple_requests_deferred(Request[] paramArrayOfRequest) {
/*  691 */     checkShutdownState();
/*      */     
/*      */     byte b;
/*  694 */     for (b = 0; b < paramArrayOfRequest.length; b++) {
/*  695 */       this.dynamicRequests.addElement(paramArrayOfRequest[b]);
/*      */     }
/*      */ 
/*      */     
/*  699 */     for (b = 0; b < paramArrayOfRequest.length; b++) {
/*  700 */       AsynchInvoke asynchInvoke = new AsynchInvoke(this, (RequestImpl)paramArrayOfRequest[b], true);
/*      */       
/*  702 */       (new Thread((Runnable)asynchInvoke)).start();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean poll_next_response() {
/*  711 */     checkShutdownState();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  716 */     Enumeration<Request> enumeration = this.dynamicRequests.elements();
/*  717 */     while (enumeration.hasMoreElements() == true) {
/*  718 */       Request request = enumeration.nextElement();
/*  719 */       if (request.poll_response() == true) {
/*  720 */         return true;
/*      */       }
/*      */     } 
/*  723 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Request get_next_response() throws WrongTransaction {
/*  734 */     synchronized (this) {
/*  735 */       checkShutdownState();
/*      */     } 
/*      */ 
/*      */     
/*      */     while (true) {
/*  740 */       synchronized (this.dynamicRequests) {
/*  741 */         Enumeration<Request> enumeration = this.dynamicRequests.elements();
/*  742 */         while (enumeration.hasMoreElements()) {
/*  743 */           Request request = enumeration.nextElement();
/*  744 */           if (request.poll_response()) {
/*      */             
/*  746 */             request.get_response();
/*  747 */             this.dynamicRequests.removeElement(request);
/*  748 */             return request;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  754 */       synchronized (this.svResponseReceived) {
/*  755 */         while (!this.svResponseReceived.value()) {
/*      */           try {
/*  757 */             this.svResponseReceived.wait();
/*  758 */           } catch (InterruptedException interruptedException) {}
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  763 */         this.svResponseReceived.reset();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyORB() {
/*  773 */     synchronized (this) {
/*  774 */       checkShutdownState();
/*      */     } 
/*  776 */     synchronized (this.svResponseReceived) {
/*  777 */       this.svResponseReceived.set();
/*  778 */       this.svResponseReceived.notify();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String object_to_string(Object paramObject) {
/*  789 */     checkShutdownState();
/*      */ 
/*      */     
/*  792 */     if (paramObject == null) {
/*  793 */       IOR iOR1 = IORFactories.makeIOR(this);
/*  794 */       return iOR1.stringify();
/*      */     } 
/*      */     
/*  797 */     IOR iOR = null;
/*      */     
/*      */     try {
/*  800 */       iOR = ORBUtility.connectAndGetIOR(this, paramObject);
/*  801 */     } catch (BAD_PARAM bAD_PARAM) {
/*      */       
/*  803 */       if (bAD_PARAM.minor == 1398079694) {
/*  804 */         throw this.omgWrapper.notAnObjectImpl(bAD_PARAM);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  809 */       throw bAD_PARAM;
/*      */     } 
/*      */     
/*  812 */     return iOR.stringify();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object string_to_object(String paramString) {
/*      */     Operation operation;
/*  824 */     synchronized (this) {
/*  825 */       checkShutdownState();
/*  826 */       operation = this.urlOperation;
/*      */     } 
/*      */     
/*  829 */     if (paramString == null) {
/*  830 */       throw this.wrapper.nullParam();
/*      */     }
/*  832 */     synchronized (this.urlOperationLock) {
/*  833 */       return (Object)operation.operate(paramString);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized IOR getFVDCodeBaseIOR() {
/*  842 */     checkShutdownState();
/*      */     
/*  844 */     if (codeBaseIOR != null) {
/*  845 */       return codeBaseIOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  850 */     ValueHandler valueHandler = ORBUtility.createValueHandler();
/*      */     
/*  852 */     CodeBase codeBase = (CodeBase)valueHandler.getRunTimeCodeBase();
/*  853 */     return ORBUtility.connectAndGetIOR(this, (Object)codeBase);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized TypeCode get_primitive_tc(TCKind paramTCKind) {
/*  864 */     checkShutdownState();
/*  865 */     return (TypeCode)get_primitive_tc(paramTCKind.value());
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
/*      */   public synchronized TypeCode create_struct_tc(String paramString1, String paramString2, StructMember[] paramArrayOfStructMember) {
/*  880 */     checkShutdownState();
/*  881 */     return (TypeCode)new TypeCodeImpl(this, 15, paramString1, paramString2, paramArrayOfStructMember);
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
/*      */   public synchronized TypeCode create_union_tc(String paramString1, String paramString2, TypeCode paramTypeCode, UnionMember[] paramArrayOfUnionMember) {
/*  899 */     checkShutdownState();
/*  900 */     return (TypeCode)new TypeCodeImpl(this, 16, paramString1, paramString2, paramTypeCode, paramArrayOfUnionMember);
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
/*      */   public synchronized TypeCode create_enum_tc(String paramString1, String paramString2, String[] paramArrayOfString) {
/*  920 */     checkShutdownState();
/*  921 */     return (TypeCode)new TypeCodeImpl(this, 17, paramString1, paramString2, paramArrayOfString);
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
/*      */   public synchronized TypeCode create_alias_tc(String paramString1, String paramString2, TypeCode paramTypeCode) {
/*  937 */     checkShutdownState();
/*  938 */     return (TypeCode)new TypeCodeImpl(this, 21, paramString1, paramString2, paramTypeCode);
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
/*      */   public synchronized TypeCode create_exception_tc(String paramString1, String paramString2, StructMember[] paramArrayOfStructMember) {
/*  953 */     checkShutdownState();
/*  954 */     return (TypeCode)new TypeCodeImpl(this, 22, paramString1, paramString2, paramArrayOfStructMember);
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
/*      */   public synchronized TypeCode create_interface_tc(String paramString1, String paramString2) {
/*  967 */     checkShutdownState();
/*  968 */     return (TypeCode)new TypeCodeImpl(this, 14, paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized TypeCode create_string_tc(int paramInt) {
/*  979 */     checkShutdownState();
/*  980 */     return (TypeCode)new TypeCodeImpl(this, 18, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized TypeCode create_wstring_tc(int paramInt) {
/*  991 */     checkShutdownState();
/*  992 */     return (TypeCode)new TypeCodeImpl(this, 27, paramInt);
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
/*      */   public synchronized TypeCode create_sequence_tc(int paramInt, TypeCode paramTypeCode) {
/* 1006 */     checkShutdownState();
/* 1007 */     return (TypeCode)new TypeCodeImpl(this, 19, paramInt, paramTypeCode);
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
/*      */   public synchronized TypeCode create_recursive_sequence_tc(int paramInt1, int paramInt2) {
/* 1022 */     checkShutdownState();
/* 1023 */     return (TypeCode)new TypeCodeImpl(this, 19, paramInt1, paramInt2);
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
/*      */   public synchronized TypeCode create_array_tc(int paramInt, TypeCode paramTypeCode) {
/* 1038 */     checkShutdownState();
/* 1039 */     return (TypeCode)new TypeCodeImpl(this, 20, paramInt, paramTypeCode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized TypeCode create_native_tc(String paramString1, String paramString2) {
/* 1046 */     checkShutdownState();
/* 1047 */     return (TypeCode)new TypeCodeImpl(this, 31, paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized TypeCode create_abstract_interface_tc(String paramString1, String paramString2) {
/* 1054 */     checkShutdownState();
/* 1055 */     return (TypeCode)new TypeCodeImpl(this, 32, paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized TypeCode create_fixed_tc(short paramShort1, short paramShort2) {
/* 1060 */     checkShutdownState();
/* 1061 */     return (TypeCode)new TypeCodeImpl(this, 28, paramShort1, paramShort2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized TypeCode create_value_tc(String paramString1, String paramString2, short paramShort, TypeCode paramTypeCode, ValueMember[] paramArrayOfValueMember) {
/* 1070 */     checkShutdownState();
/* 1071 */     return (TypeCode)new TypeCodeImpl(this, 29, paramString1, paramString2, paramShort, paramTypeCode, paramArrayOfValueMember);
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized TypeCode create_recursive_tc(String paramString) {
/* 1076 */     checkShutdownState();
/* 1077 */     return (TypeCode)new TypeCodeImpl(this, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized TypeCode create_value_box_tc(String paramString1, String paramString2, TypeCode paramTypeCode) {
/* 1084 */     checkShutdownState();
/* 1085 */     return (TypeCode)new TypeCodeImpl(this, 30, paramString1, paramString2, paramTypeCode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Any create_any() {
/* 1096 */     checkShutdownState();
/* 1097 */     return (Any)new AnyImpl(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setTypeCodeForClass(Class<?> paramClass, TypeCodeImpl paramTypeCodeImpl) {
/* 1108 */     checkShutdownState();
/*      */     
/* 1110 */     if (this.typeCodeForClassMap == null) {
/* 1111 */       this.typeCodeForClassMap = Collections.synchronizedMap(new WeakHashMap<>(64));
/*      */     }
/*      */     
/* 1114 */     if (!this.typeCodeForClassMap.containsKey(paramClass)) {
/* 1115 */       this.typeCodeForClassMap.put(paramClass, paramTypeCodeImpl);
/*      */     }
/*      */   }
/*      */   
/*      */   public synchronized TypeCodeImpl getTypeCodeForClass(Class paramClass) {
/* 1120 */     checkShutdownState();
/*      */     
/* 1122 */     if (this.typeCodeForClassMap == null)
/* 1123 */       return null; 
/* 1124 */     return (TypeCodeImpl)this.typeCodeForClassMap.get(paramClass);
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
/*      */   public String[] list_initial_services() {
/*      */     Resolver resolver;
/* 1145 */     synchronized (this) {
/* 1146 */       checkShutdownState();
/* 1147 */       resolver = this.resolver;
/*      */     } 
/*      */     
/* 1150 */     synchronized (this.resolverLock) {
/* 1151 */       Set set = resolver.list();
/* 1152 */       return (String[])set.toArray((Object[])new String[set.size()]);
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
/*      */   public Object resolve_initial_references(String paramString) throws InvalidName {
/*      */     Resolver resolver;
/* 1171 */     synchronized (this) {
/* 1172 */       checkShutdownState();
/* 1173 */       resolver = this.resolver;
/*      */     } 
/*      */     
/* 1176 */     synchronized (this.resolverLock) {
/* 1177 */       Object object = resolver.resolve(paramString);
/*      */       
/* 1179 */       if (object == null) {
/* 1180 */         throw new InvalidName();
/*      */       }
/* 1182 */       return object;
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
/*      */   public void register_initial_reference(String paramString, Object paramObject) throws InvalidName {
/*      */     CorbaServerRequestDispatcher corbaServerRequestDispatcher;
/* 1204 */     synchronized (this) {
/* 1205 */       checkShutdownState();
/*      */     } 
/*      */     
/* 1208 */     if (paramString == null || paramString.length() == 0) {
/* 1209 */       throw new InvalidName();
/*      */     }
/* 1211 */     synchronized (this) {
/* 1212 */       checkShutdownState();
/*      */     } 
/*      */     
/* 1215 */     synchronized (this.resolverLock) {
/* 1216 */       corbaServerRequestDispatcher = this.insNamingDelegate;
/*      */       
/* 1218 */       Object object = this.localResolver.resolve(paramString);
/* 1219 */       if (object != null) {
/* 1220 */         throw new InvalidName(paramString + " already registered");
/*      */       }
/* 1222 */       this.localResolver.register(paramString, ClosureFactory.makeConstant(paramObject));
/*      */     } 
/*      */     
/* 1225 */     synchronized (this) {
/* 1226 */       if (StubAdapter.isStub(paramObject))
/*      */       {
/* 1228 */         this.requestDispatcherRegistry.registerServerRequestDispatcher(corbaServerRequestDispatcher, paramString);
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
/*      */   public void run() {
/* 1240 */     synchronized (this) {
/* 1241 */       checkShutdownState();
/*      */     } 
/*      */     
/* 1244 */     synchronized (this.runObj) {
/*      */       try {
/* 1246 */         this.runObj.wait();
/* 1247 */       } catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */   }
/*      */   
/*      */   public void shutdown(boolean paramBoolean) {
/* 1252 */     boolean bool = false;
/*      */     
/* 1254 */     synchronized (this) {
/* 1255 */       checkShutdownState();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1261 */       if (paramBoolean && this.isProcessingInvocation
/* 1262 */         .get() == Boolean.TRUE) {
/* 1263 */         throw this.omgWrapper.shutdownWaitForCompletionDeadlock();
/*      */       }
/*      */       
/* 1266 */       if (this.status == 2) {
/* 1267 */         if (paramBoolean) {
/* 1268 */           bool = true;
/*      */         } else {
/*      */           return;
/*      */         } 
/*      */       }
/*      */       
/* 1274 */       this.status = 2;
/*      */     } 
/*      */ 
/*      */     
/* 1278 */     synchronized (this.shutdownObj) {
/*      */ 
/*      */ 
/*      */       
/* 1282 */       if (bool) {
/*      */         while (true) {
/* 1284 */           synchronized (this) {
/* 1285 */             if (this.status == 3) {
/*      */               break;
/*      */             }
/*      */           } 
/*      */           try {
/* 1290 */             this.shutdownObj.wait();
/* 1291 */           } catch (InterruptedException interruptedException) {}
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1297 */         shutdownServants(paramBoolean);
/*      */         
/* 1299 */         if (paramBoolean) {
/* 1300 */           synchronized (this.waitForCompletionObj) {
/* 1301 */             while (this.numInvocations > 0) {
/*      */               try {
/* 1303 */                 this.waitForCompletionObj.wait();
/* 1304 */               } catch (InterruptedException interruptedException) {}
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/* 1309 */         synchronized (this.runObj) {
/* 1310 */           this.runObj.notifyAll();
/*      */         } 
/*      */         
/* 1313 */         this.status = 3;
/*      */         
/* 1315 */         this.shutdownObj.notifyAll();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void shutdownServants(boolean paramBoolean) {
/*      */     HashSet hashSet;
/* 1325 */     synchronized (this) {
/* 1326 */       hashSet = new HashSet(this.requestDispatcherRegistry.getObjectAdapterFactories());
/*      */     } 
/*      */     
/* 1329 */     for (ObjectAdapterFactory objectAdapterFactory : hashSet) {
/* 1330 */       objectAdapterFactory.shutdown(paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkShutdownState() {
/* 1336 */     if (this.status == 4) {
/* 1337 */       throw this.wrapper.orbDestroyed();
/*      */     }
/*      */     
/* 1340 */     if (this.status == 3) {
/* 1341 */       throw this.omgWrapper.badOperationAfterShutdown();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDuringDispatch() {
/* 1347 */     synchronized (this) {
/* 1348 */       checkShutdownState();
/*      */     } 
/* 1350 */     Boolean bool = this.isProcessingInvocation.get();
/* 1351 */     return bool.booleanValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void startingDispatch() {
/* 1356 */     synchronized (this) {
/* 1357 */       checkShutdownState();
/*      */     } 
/* 1359 */     synchronized (this.invocationObj) {
/* 1360 */       this.isProcessingInvocation.set(Boolean.TRUE);
/* 1361 */       this.numInvocations++;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void finishedDispatch() {
/* 1367 */     synchronized (this) {
/* 1368 */       checkShutdownState();
/*      */     } 
/* 1370 */     synchronized (this.invocationObj) {
/* 1371 */       this.numInvocations--;
/* 1372 */       this.isProcessingInvocation.set(Boolean.valueOf(false));
/* 1373 */       if (this.numInvocations == 0) {
/* 1374 */         synchronized (this.waitForCompletionObj) {
/* 1375 */           this.waitForCompletionObj.notifyAll();
/*      */         } 
/* 1377 */       } else if (this.numInvocations < 0) {
/* 1378 */         throw this.wrapper.numInvocationsAlreadyZero(CompletionStatus.COMPLETED_YES);
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
/*      */   public void destroy() {
/* 1391 */     boolean bool = false;
/*      */     
/* 1393 */     synchronized (this) {
/* 1394 */       bool = (this.status == 1) ? true : false;
/*      */     } 
/*      */     
/* 1397 */     if (bool) {
/* 1398 */       shutdown(true);
/*      */     }
/*      */     
/* 1401 */     synchronized (this) {
/* 1402 */       if (this.status < 4) {
/* 1403 */         getCorbaTransportManager().close();
/* 1404 */         getPIHandler().destroyInterceptors();
/* 1405 */         this.status = 4;
/*      */       } 
/*      */     } 
/* 1408 */     synchronized (this.threadPoolManagerAccessLock) {
/* 1409 */       if (this.orbOwnsThreadPoolManager) {
/*      */         try {
/* 1411 */           this.threadpoolMgr.close();
/* 1412 */           this.threadpoolMgr = null;
/* 1413 */         } catch (IOException iOException) {
/* 1414 */           this.wrapper.ioExceptionOnClose(iOException);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/* 1420 */       this.monitoringManager.close();
/* 1421 */       this.monitoringManager = null;
/* 1422 */     } catch (IOException iOException) {
/* 1423 */       this.wrapper.ioExceptionOnClose(iOException);
/*      */     } 
/*      */     
/* 1426 */     CachedCodeBase.cleanCache(this);
/*      */     try {
/* 1428 */       this.pihandler.close();
/* 1429 */     } catch (IOException iOException) {
/* 1430 */       this.wrapper.ioExceptionOnClose(iOException);
/*      */     } 
/*      */     
/* 1433 */     super.destroy();
/*      */     
/* 1435 */     this.badServerIdHandlerAccessLock = null;
/* 1436 */     this.clientDelegateFactoryAccessorLock = null;
/* 1437 */     this.corbaContactInfoListFactoryAccessLock = null;
/*      */     
/* 1439 */     this.objectKeyFactoryAccessLock = null;
/* 1440 */     this.legacyServerSocketManagerAccessLock = null;
/* 1441 */     this.threadPoolManagerAccessLock = null;
/* 1442 */     this.transportManager = null;
/* 1443 */     this.legacyServerSocketManager = null;
/* 1444 */     this.OAInvocationInfoStack = null;
/* 1445 */     this.clientInvocationInfoStack = null;
/* 1446 */     codeBaseIOR = null;
/* 1447 */     this.dynamicRequests = null;
/* 1448 */     this.svResponseReceived = null;
/* 1449 */     this.runObj = null;
/* 1450 */     this.shutdownObj = null;
/* 1451 */     this.waitForCompletionObj = null;
/* 1452 */     this.invocationObj = null;
/* 1453 */     this.isProcessingInvocation = null;
/* 1454 */     this.typeCodeForClassMap = null;
/* 1455 */     this.valueFactoryCache = null;
/* 1456 */     this.orbVersionThreadLocal = null;
/* 1457 */     this.requestDispatcherRegistry = null;
/* 1458 */     this.copierManager = null;
/* 1459 */     this.toaFactory = null;
/* 1460 */     this.poaFactory = null;
/* 1461 */     this.pihandler = null;
/* 1462 */     this.configData = null;
/* 1463 */     this.badServerIdHandler = null;
/* 1464 */     this.clientDelegateFactory = null;
/* 1465 */     this.corbaContactInfoListFactory = null;
/* 1466 */     this.resolver = null;
/* 1467 */     this.localResolver = null;
/* 1468 */     this.insNamingDelegate = null;
/* 1469 */     this.urlOperation = null;
/* 1470 */     this.taggedComponentFactoryFinder = null;
/* 1471 */     this.taggedProfileFactoryFinder = null;
/* 1472 */     this.taggedProfileTemplateFactoryFinder = null;
/* 1473 */     this.objectKeyFactory = null;
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
/*      */   public synchronized ValueFactory register_value_factory(String paramString, ValueFactory paramValueFactory) {
/* 1488 */     checkShutdownState();
/*      */     
/* 1490 */     if (paramString == null || paramValueFactory == null) {
/* 1491 */       throw this.omgWrapper.unableRegisterValueFactory();
/*      */     }
/* 1493 */     return this.valueFactoryCache.put(paramString, paramValueFactory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void unregister_value_factory(String paramString) {
/* 1503 */     checkShutdownState();
/*      */     
/* 1505 */     if (this.valueFactoryCache.remove(paramString) == null) {
/* 1506 */       throw this.wrapper.nullParam();
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
/*      */   public synchronized ValueFactory lookup_value_factory(String paramString) {
/* 1520 */     checkShutdownState();
/*      */ 
/*      */     
/* 1523 */     ValueFactory valueFactory = (ValueFactory)this.valueFactoryCache.get(paramString);
/*      */     
/* 1525 */     if (valueFactory == null) {
/*      */       try {
/* 1527 */         valueFactory = Utility.getFactory(null, null, null, paramString);
/* 1528 */       } catch (MARSHAL mARSHAL) {
/* 1529 */         throw this.wrapper.unableFindValueFactory(mARSHAL);
/*      */       } 
/*      */     }
/*      */     
/* 1533 */     return valueFactory;
/*      */   }
/*      */ 
/*      */   
/*      */   public OAInvocationInfo peekInvocationInfo() {
/* 1538 */     synchronized (this) {
/* 1539 */       checkShutdownState();
/*      */     } 
/* 1541 */     StackImpl stackImpl = this.OAInvocationInfoStack.get();
/* 1542 */     return (OAInvocationInfo)stackImpl.peek();
/*      */   }
/*      */ 
/*      */   
/*      */   public void pushInvocationInfo(OAInvocationInfo paramOAInvocationInfo) {
/* 1547 */     synchronized (this) {
/* 1548 */       checkShutdownState();
/*      */     } 
/* 1550 */     StackImpl stackImpl = this.OAInvocationInfoStack.get();
/* 1551 */     stackImpl.push(paramOAInvocationInfo);
/*      */   }
/*      */   
/*      */   public OAInvocationInfo popInvocationInfo()
/*      */   {
/* 1556 */     synchronized (this) {
/* 1557 */       checkShutdownState();
/*      */     } 
/* 1559 */     StackImpl stackImpl = this.OAInvocationInfoStack.get();
/* 1560 */     return (OAInvocationInfo)stackImpl.pop();
/*      */   } public void initBadServerIdHandler() { synchronized (this) { checkShutdownState(); }  synchronized (this.badServerIdHandlerAccessLock) { Class clazz = this.configData.getBadServerIdHandler(); if (clazz != null) try { Class[] arrayOfClass = { ORB.class }; Object[] arrayOfObject = { this }; Constructor<BadServerIdHandler> constructor = clazz.getConstructor(arrayOfClass); this.badServerIdHandler = constructor.newInstance(arrayOfObject); } catch (Exception exception) { throw this.wrapper.errorInitBadserveridhandler(exception); }   }  } public void setBadServerIdHandler(BadServerIdHandler paramBadServerIdHandler) { synchronized (this) { checkShutdownState(); }  synchronized (this.badServerIdHandlerAccessLock) { this.badServerIdHandler = paramBadServerIdHandler; }  } public void handleBadServerId(ObjectKey paramObjectKey) { synchronized (this) { checkShutdownState(); }  synchronized (this.badServerIdHandlerAccessLock) { if (this.badServerIdHandler == null) throw this.wrapper.badServerId();  this.badServerIdHandler.handle(paramObjectKey); }  } public synchronized Policy create_policy(int paramInt, Any paramAny) throws PolicyError { checkShutdownState(); return this.pihandler.create_policy(paramInt, paramAny); } public synchronized void connect(Object paramObject) { checkShutdownState(); if (getTOAFactory() == null) throw this.wrapper.noToa();  try { String str = Util.getCodebase(paramObject.getClass()); getTOAFactory().getTOA(str).connect(paramObject); } catch (Exception exception) { throw this.wrapper.orbConnectError(exception); }  }
/*      */   public synchronized void disconnect(Object paramObject) { checkShutdownState(); if (getTOAFactory() == null) throw this.wrapper.noToa();  try { getTOAFactory().getTOA().disconnect(paramObject); } catch (Exception exception) { throw this.wrapper.orbConnectError(exception); }  }
/*      */   public int getTransientServerId() { synchronized (this) { checkShutdownState(); }  if (this.configData.getORBServerIdPropertySpecified()) return this.configData.getPersistentServerId();  return this.transientServerId; }
/*      */   public RequestDispatcherRegistry getRequestDispatcherRegistry() { synchronized (this) { checkShutdownState(); }  return this.requestDispatcherRegistry; }
/*      */   public ServiceContextRegistry getServiceContextRegistry() { synchronized (this) { checkShutdownState(); }  return this.serviceContextRegistry; }
/*      */   public boolean isLocalHost(String paramString) { synchronized (this) { checkShutdownState(); }  return (paramString.equals(this.configData.getORBServerHost()) || paramString.equals(getLocalHostName())); }
/*      */   public boolean isLocalServerId(int paramInt1, int paramInt2) { synchronized (this) { checkShutdownState(); }  if (paramInt1 < 32 || paramInt1 > 63) return (paramInt2 == getTransientServerId());  if (ORBConstants.isTransient(paramInt1)) return (paramInt2 == getTransientServerId());  if (this.configData.getPersistentServerIdInitialized()) return (paramInt2 == this.configData.getPersistentServerId());  return false; }
/* 1568 */   public ORBImpl() { this.badServerIdHandlerAccessLock = new Object();
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
/* 1861 */     this.clientDelegateFactoryAccessorLock = new Object();
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
/* 1883 */     this.corbaContactInfoListFactoryAccessLock = new Object();
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
/* 2013 */     this.objectKeyFactoryAccessLock = new Object();
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
/* 2035 */     this.transportManagerAccessorLock = new Object();
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
/* 2052 */     this.legacyServerSocketManagerAccessLock = new Object();
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
/* 2067 */     this.threadPoolManagerAccessLock = new Object(); }
/*      */   
/*      */   private String getHostName(String paramString) throws UnknownHostException {
/*      */     return InetAddress.getByName(paramString).getHostAddress();
/* 2071 */   } private static String localHostString = null; private Object clientDelegateFactoryAccessorLock; private Object corbaContactInfoListFactoryAccessLock; private Object objectKeyFactoryAccessLock; private Object transportManagerAccessorLock; private Object legacyServerSocketManagerAccessLock; private Object threadPoolManagerAccessLock; public void setThreadPoolManager(ThreadPoolManager paramThreadPoolManager) { synchronized (this) {
/* 2072 */       checkShutdownState();
/*      */     } 
/* 2074 */     synchronized (this.threadPoolManagerAccessLock)
/* 2075 */     { this.threadpoolMgr = paramThreadPoolManager; }  }
/*      */   private synchronized String getLocalHostName() { if (localHostString == null) try { localHostString = InetAddress.getLocalHost().getHostAddress(); } catch (Exception exception) { throw this.wrapper.getLocalHostFailed(exception); }   return localHostString; }
/*      */   public synchronized boolean work_pending() { checkShutdownState(); throw this.wrapper.genericNoImpl(); }
/*      */   public synchronized void perform_work() { checkShutdownState(); throw this.wrapper.genericNoImpl(); }
/*      */   public synchronized void set_delegate(Object paramObject) { checkShutdownState(); POAFactory pOAFactory = getPOAFactory(); if (pOAFactory != null) { ((Servant)paramObject)._set_delegate(pOAFactory.getDelegateImpl()); } else { throw this.wrapper.noPoa(); }  }
/*      */   public ClientInvocationInfo createOrIncrementInvocationInfo() { CorbaInvocationInfo corbaInvocationInfo; synchronized (this) { checkShutdownState(); }  StackImpl stackImpl = this.clientInvocationInfoStack.get(); ClientInvocationInfo clientInvocationInfo = null; if (!stackImpl.empty()) clientInvocationInfo = (ClientInvocationInfo)stackImpl.peek();  if (clientInvocationInfo == null || !clientInvocationInfo.isRetryInvocation()) { corbaInvocationInfo = new CorbaInvocationInfo(this); startingDispatch(); stackImpl.push(corbaInvocationInfo); }  corbaInvocationInfo.setIsRetryInvocation(false); corbaInvocationInfo.incrementEntryCount(); return (ClientInvocationInfo)corbaInvocationInfo; }
/* 2081 */   public void releaseOrDecrementInvocationInfo() { synchronized (this) { checkShutdownState(); }  int i = -1; ClientInvocationInfo clientInvocationInfo = null; StackImpl stackImpl = this.clientInvocationInfoStack.get(); if (!stackImpl.empty()) { clientInvocationInfo = (ClientInvocationInfo)stackImpl.peek(); } else { throw this.wrapper.invocationInfoStackEmpty(); }  clientInvocationInfo.decrementEntryCount(); i = clientInvocationInfo.getEntryCount(); if (clientInvocationInfo.getEntryCount() == 0) { if (!clientInvocationInfo.isRetryInvocation()) stackImpl.pop();  finishedDispatch(); }  } public ClientInvocationInfo getInvocationInfo() { synchronized (this) { checkShutdownState(); }  StackImpl stackImpl = this.clientInvocationInfoStack.get(); return (ClientInvocationInfo)stackImpl.peek(); } public void setClientDelegateFactory(ClientDelegateFactory paramClientDelegateFactory) { synchronized (this) { checkShutdownState(); }  synchronized (this.clientDelegateFactoryAccessorLock) { this.clientDelegateFactory = paramClientDelegateFactory; }  } public ClientDelegateFactory getClientDelegateFactory() { synchronized (this) { checkShutdownState(); }  synchronized (this.clientDelegateFactoryAccessorLock) { return this.clientDelegateFactory; }  } public void setCorbaContactInfoListFactory(CorbaContactInfoListFactory paramCorbaContactInfoListFactory) { synchronized (this) { checkShutdownState(); }  synchronized (this.corbaContactInfoListFactoryAccessLock) { this.corbaContactInfoListFactory = paramCorbaContactInfoListFactory; }  } public synchronized CorbaContactInfoListFactory getCorbaContactInfoListFactory() { checkShutdownState(); return this.corbaContactInfoListFactory; } public void setResolver(Resolver paramResolver) { synchronized (this) { checkShutdownState(); }  synchronized (this.resolverLock) { this.resolver = paramResolver; }  } public Resolver getResolver() { synchronized (this) { checkShutdownState(); }  synchronized (this.resolverLock) { return this.resolver; }  } public void setLocalResolver(LocalResolver paramLocalResolver) { synchronized (this) { checkShutdownState(); }  synchronized (this.resolverLock) { this.localResolver = paramLocalResolver; }  } public ThreadPoolManager getThreadPoolManager() { synchronized (this) {
/* 2082 */       checkShutdownState();
/*      */     } 
/* 2084 */     synchronized (this.threadPoolManagerAccessLock)
/* 2085 */     { if (this.threadpoolMgr == null) {
/* 2086 */         this.threadpoolMgr = (ThreadPoolManager)new ThreadPoolManagerImpl();
/* 2087 */         this.orbOwnsThreadPoolManager = true;
/*      */       } 
/* 2089 */       return this.threadpoolMgr; }  } public LocalResolver getLocalResolver() { synchronized (this) { checkShutdownState(); }  synchronized (this.resolverLock) { return this.localResolver; }  } public void setURLOperation(Operation paramOperation) { synchronized (this) { checkShutdownState(); }  synchronized (this.urlOperationLock) { this.urlOperation = paramOperation; }  } public Operation getURLOperation() { synchronized (this) { checkShutdownState(); }  synchronized (this.urlOperationLock) { return this.urlOperation; }  } public void setINSDelegate(CorbaServerRequestDispatcher paramCorbaServerRequestDispatcher) { synchronized (this) { checkShutdownState(); }  synchronized (this.resolverLock) { this.insNamingDelegate = paramCorbaServerRequestDispatcher; }  } public TaggedComponentFactoryFinder getTaggedComponentFactoryFinder() { synchronized (this) { checkShutdownState(); }  return this.taggedComponentFactoryFinder; } public IdentifiableFactoryFinder getTaggedProfileFactoryFinder() { synchronized (this) { checkShutdownState(); }  return this.taggedProfileFactoryFinder; } public IdentifiableFactoryFinder getTaggedProfileTemplateFactoryFinder() { synchronized (this) { checkShutdownState(); }  return this.taggedProfileTemplateFactoryFinder; }
/*      */   public ObjectKeyFactory getObjectKeyFactory() { synchronized (this) { checkShutdownState(); }  synchronized (this.objectKeyFactoryAccessLock) { return this.objectKeyFactory; }  }
/*      */   public void setObjectKeyFactory(ObjectKeyFactory paramObjectKeyFactory) { synchronized (this) { checkShutdownState(); }  synchronized (this.objectKeyFactoryAccessLock) { this.objectKeyFactory = paramObjectKeyFactory; }  }
/*      */   public TransportManager getTransportManager() { synchronized (this.transportManagerAccessorLock) { if (this.transportManager == null) this.transportManager = (TransportManager)new CorbaTransportManagerImpl(this);  return this.transportManager; }  }
/*      */   public CorbaTransportManager getCorbaTransportManager() { return (CorbaTransportManager)getTransportManager(); }
/*      */   public LegacyServerSocketManager getLegacyServerSocketManager() { synchronized (this) { checkShutdownState(); }  synchronized (this.legacyServerSocketManagerAccessLock) { if (this.legacyServerSocketManager == null) this.legacyServerSocketManager = (LegacyServerSocketManager)new LegacyServerSocketManagerImpl(this);  return this.legacyServerSocketManager; }  }
/* 2095 */   public CopierManager getCopierManager() { synchronized (this) {
/* 2096 */       checkShutdownState();
/*      */     } 
/* 2098 */     return this.copierManager; }
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateIORClass(String paramString) {
/* 2103 */     if (this.iorTypeCheckRegistry != null && 
/* 2104 */       !this.iorTypeCheckRegistry.isValidIORType(paramString))
/* 2105 */       throw ORBUtilSystemException.get(this, "oa.ior")
/* 2106 */         .badStringifiedIor(); 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/ORBImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */