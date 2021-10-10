/*     */ package com.sun.corba.se.impl.orb;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.AnyImpl;
/*     */ import com.sun.corba.se.impl.corba.ContextListImpl;
/*     */ import com.sun.corba.se.impl.corba.EnvironmentImpl;
/*     */ import com.sun.corba.se.impl.corba.ExceptionListImpl;
/*     */ import com.sun.corba.se.impl.corba.NVListImpl;
/*     */ import com.sun.corba.se.impl.corba.NamedValueImpl;
/*     */ import com.sun.corba.se.impl.corba.TypeCodeImpl;
/*     */ import com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
/*     */ import com.sun.corba.se.impl.oa.poa.BadServerIdHandler;
/*     */ import com.sun.corba.se.pept.protocol.ClientInvocationInfo;
/*     */ import com.sun.corba.se.pept.transport.ConnectionCache;
/*     */ import com.sun.corba.se.pept.transport.ContactInfo;
/*     */ import com.sun.corba.se.pept.transport.Selector;
/*     */ import com.sun.corba.se.pept.transport.TransportManager;
/*     */ import com.sun.corba.se.spi.copyobject.CopierManager;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyFactory;
/*     */ import com.sun.corba.se.spi.ior.TaggedComponentFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketManager;
/*     */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBData;
/*     */ import com.sun.corba.se.spi.orb.ORBVersion;
/*     */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*     */ import com.sun.corba.se.spi.orb.Operation;
/*     */ import com.sun.corba.se.spi.orbutil.closure.Closure;
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
/*     */ import java.applet.Applet;
/*     */ import java.net.URL;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Context;
/*     */ import org.omg.CORBA.ContextList;
/*     */ import org.omg.CORBA.Current;
/*     */ import org.omg.CORBA.Environment;
/*     */ import org.omg.CORBA.ExceptionList;
/*     */ import org.omg.CORBA.NO_IMPLEMENT;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.NamedValue;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.ORBPackage.InvalidName;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.CORBA.PolicyError;
/*     */ import org.omg.CORBA.Request;
/*     */ import org.omg.CORBA.StructMember;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.UnionMember;
/*     */ import org.omg.CORBA.ValueMember;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ValueFactory;
/*     */ import sun.corba.OutputStreamFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ORBSingleton
/*     */   extends ORB
/*     */ {
/*     */   private ORB fullORB;
/* 116 */   private static PresentationManager.StubFactoryFactory staticStubFactoryFactory = PresentationDefaults.getStaticStubFactoryFactory();
/*     */ 
/*     */   
/*     */   public void set_parameters(Properties paramProperties) {}
/*     */ 
/*     */   
/*     */   protected void set_parameters(Applet paramApplet, Properties paramProperties) {}
/*     */ 
/*     */   
/*     */   protected void set_parameters(String[] paramArrayOfString, Properties paramProperties) {}
/*     */   
/*     */   public OutputStream create_output_stream() {
/* 128 */     return (OutputStream)OutputStreamFactory.newEncapsOutputStream(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_struct_tc(String paramString1, String paramString2, StructMember[] paramArrayOfStructMember) {
/* 135 */     return (TypeCode)new TypeCodeImpl(this, 15, paramString1, paramString2, paramArrayOfStructMember);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_union_tc(String paramString1, String paramString2, TypeCode paramTypeCode, UnionMember[] paramArrayOfUnionMember) {
/* 143 */     return (TypeCode)new TypeCodeImpl(this, 16, paramString1, paramString2, paramTypeCode, paramArrayOfUnionMember);
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
/*     */   public TypeCode create_enum_tc(String paramString1, String paramString2, String[] paramArrayOfString) {
/* 155 */     return (TypeCode)new TypeCodeImpl(this, 17, paramString1, paramString2, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_alias_tc(String paramString1, String paramString2, TypeCode paramTypeCode) {
/* 162 */     return (TypeCode)new TypeCodeImpl(this, 21, paramString1, paramString2, paramTypeCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_exception_tc(String paramString1, String paramString2, StructMember[] paramArrayOfStructMember) {
/* 169 */     return (TypeCode)new TypeCodeImpl(this, 22, paramString1, paramString2, paramArrayOfStructMember);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_interface_tc(String paramString1, String paramString2) {
/* 175 */     return (TypeCode)new TypeCodeImpl(this, 14, paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public TypeCode create_string_tc(int paramInt) {
/* 179 */     return (TypeCode)new TypeCodeImpl(this, 18, paramInt);
/*     */   }
/*     */   
/*     */   public TypeCode create_wstring_tc(int paramInt) {
/* 183 */     return (TypeCode)new TypeCodeImpl(this, 27, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_sequence_tc(int paramInt, TypeCode paramTypeCode) {
/* 189 */     return (TypeCode)new TypeCodeImpl(this, 19, paramInt, paramTypeCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_recursive_sequence_tc(int paramInt1, int paramInt2) {
/* 195 */     return (TypeCode)new TypeCodeImpl(this, 19, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_array_tc(int paramInt, TypeCode paramTypeCode) {
/* 201 */     return (TypeCode)new TypeCodeImpl(this, 20, paramInt, paramTypeCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_native_tc(String paramString1, String paramString2) {
/* 207 */     return (TypeCode)new TypeCodeImpl(this, 31, paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_abstract_interface_tc(String paramString1, String paramString2) {
/* 214 */     return (TypeCode)new TypeCodeImpl(this, 32, paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeCode create_fixed_tc(short paramShort1, short paramShort2) {
/* 219 */     return (TypeCode)new TypeCodeImpl(this, 28, paramShort1, paramShort2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_value_tc(String paramString1, String paramString2, short paramShort, TypeCode paramTypeCode, ValueMember[] paramArrayOfValueMember) {
/* 230 */     return (TypeCode)new TypeCodeImpl(this, 29, paramString1, paramString2, paramShort, paramTypeCode, paramArrayOfValueMember);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeCode create_recursive_tc(String paramString) {
/* 235 */     return (TypeCode)new TypeCodeImpl(this, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode create_value_box_tc(String paramString1, String paramString2, TypeCode paramTypeCode) {
/* 242 */     return (TypeCode)new TypeCodeImpl(this, 30, paramString1, paramString2, paramTypeCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeCode get_primitive_tc(TCKind paramTCKind) {
/* 247 */     return (TypeCode)get_primitive_tc(paramTCKind.value());
/*     */   }
/*     */   
/*     */   public Any create_any() {
/* 251 */     return (Any)new AnyImpl(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NVList create_list(int paramInt) {
/* 262 */     return (NVList)new NVListImpl(this, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public NVList create_operation_list(Object paramObject) {
/* 267 */     throw this.wrapper.genericNoImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public NamedValue create_named_value(String paramString, Any paramAny, int paramInt) {
/* 272 */     return (NamedValue)new NamedValueImpl(this, paramString, paramAny, paramInt);
/*     */   }
/*     */   
/*     */   public ExceptionList create_exception_list() {
/* 276 */     return (ExceptionList)new ExceptionListImpl();
/*     */   }
/*     */   
/*     */   public ContextList create_context_list() {
/* 280 */     return (ContextList)new ContextListImpl((ORB)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Context get_default_context() {
/* 285 */     throw this.wrapper.genericNoImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public Environment create_environment() {
/* 290 */     return (Environment)new EnvironmentImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public Current get_current() {
/* 295 */     throw this.wrapper.genericNoImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] list_initial_services() {
/* 304 */     throw this.wrapper.genericNoImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object resolve_initial_references(String paramString) throws InvalidName {
/* 310 */     throw this.wrapper.genericNoImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register_initial_reference(String paramString, Object paramObject) throws InvalidName {
/* 316 */     throw this.wrapper.genericNoImpl();
/*     */   }
/*     */   
/*     */   public void send_multiple_requests_oneway(Request[] paramArrayOfRequest) {
/* 320 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public void send_multiple_requests_deferred(Request[] paramArrayOfRequest) {
/* 324 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public boolean poll_next_response() {
/* 328 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public Request get_next_response() {
/* 332 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public String object_to_string(Object paramObject) {
/* 336 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public Object string_to_object(String paramString) {
/* 340 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote string_to_remote(String paramString) throws RemoteException {
/* 346 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public void connect(Object paramObject) {
/* 350 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public void disconnect(Object paramObject) {
/* 354 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 359 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown(boolean paramBoolean) {
/* 364 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   protected void shutdownServants(boolean paramBoolean) {
/* 368 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   protected void destroyConnections() {
/* 372 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 376 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean work_pending() {
/* 381 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public void perform_work() {
/* 386 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueFactory register_value_factory(String paramString, ValueFactory paramValueFactory) {
/* 392 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregister_value_factory(String paramString) {
/* 397 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueFactory lookup_value_factory(String paramString) {
/* 402 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public TransportManager getTransportManager() {
/* 407 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public CorbaTransportManager getCorbaTransportManager() {
/* 412 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public LegacyServerSocketManager getLegacyServerSocketManager() {
/* 417 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized ORB getFullORB() {
/* 426 */     if (this.fullORB == null) {
/* 427 */       Properties properties = new Properties();
/* 428 */       this.fullORB = new ORBImpl();
/* 429 */       this.fullORB.set_parameters(properties);
/*     */     } 
/*     */     
/* 432 */     return this.fullORB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestDispatcherRegistry getRequestDispatcherRegistry() {
/* 439 */     return getFullORB().getRequestDispatcherRegistry();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceContextRegistry getServiceContextRegistry() {
/* 447 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransientServerId() {
/* 455 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getORBInitialPort() {
/* 463 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getORBInitialHost() {
/* 471 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getORBServerHost() {
/* 476 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getORBServerPort() {
/* 481 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public CodeSetComponentInfo getCodeSetComponentInfo() {
/* 486 */     return new CodeSetComponentInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalHost(String paramString) {
/* 492 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalServerId(int paramInt1, int paramInt2) {
/* 498 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ORBVersion getORBVersion() {
/* 508 */     return ORBVersionFactory.getORBVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setORBVersion(ORBVersion paramORBVersion) {
/* 513 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAppletHost() {
/* 518 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public URL getAppletCodeBase() {
/* 523 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public int getHighWaterMark() {
/* 527 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public int getLowWaterMark() {
/* 531 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public int getNumberToReclaim() {
/* 535 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */   
/*     */   public int getGIOPFragmentSize() {
/* 539 */     return 1024;
/*     */   }
/*     */   
/*     */   public int getGIOPBuffMgrStrategy(GIOPVersion paramGIOPVersion) {
/* 543 */     return 0;
/*     */   }
/*     */   
/*     */   public IOR getFVDCodeBaseIOR() {
/* 547 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */   
/*     */   public Policy create_policy(int paramInt, Any paramAny) throws PolicyError {
/* 552 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */   
/*     */   public LegacyServerSocketEndPointInfo getServerEndpoint() {
/* 557 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentServerId(int paramInt) {}
/*     */ 
/*     */   
/*     */   public TypeCodeImpl getTypeCodeForClass(Class paramClass) {
/* 566 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTypeCodeForClass(Class paramClass, TypeCodeImpl paramTypeCodeImpl) {}
/*     */ 
/*     */   
/*     */   public boolean alwaysSendCodeSetServiceContext() {
/* 575 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDuringDispatch() {
/* 580 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyORB() {}
/*     */   
/*     */   public PIHandler getPIHandler() {
/* 587 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkShutdownState() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void startingDispatch() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void finishedDispatch() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInitialReference(String paramString, Closure paramClosure) {}
/*     */ 
/*     */   
/*     */   public ORBData getORBData() {
/* 608 */     return getFullORB().getORBData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClientDelegateFactory(ClientDelegateFactory paramClientDelegateFactory) {}
/*     */ 
/*     */   
/*     */   public ClientDelegateFactory getClientDelegateFactory() {
/* 617 */     return getFullORB().getClientDelegateFactory();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCorbaContactInfoListFactory(CorbaContactInfoListFactory paramCorbaContactInfoListFactory) {}
/*     */ 
/*     */   
/*     */   public CorbaContactInfoListFactory getCorbaContactInfoListFactory() {
/* 626 */     return getFullORB().getCorbaContactInfoListFactory();
/*     */   }
/*     */ 
/*     */   
/*     */   public Operation getURLOperation() {
/* 631 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setINSDelegate(CorbaServerRequestDispatcher paramCorbaServerRequestDispatcher) {}
/*     */ 
/*     */   
/*     */   public TaggedComponentFactoryFinder getTaggedComponentFactoryFinder() {
/* 640 */     return getFullORB().getTaggedComponentFactoryFinder();
/*     */   }
/*     */ 
/*     */   
/*     */   public IdentifiableFactoryFinder getTaggedProfileFactoryFinder() {
/* 645 */     return getFullORB().getTaggedProfileFactoryFinder();
/*     */   }
/*     */ 
/*     */   
/*     */   public IdentifiableFactoryFinder getTaggedProfileTemplateFactoryFinder() {
/* 650 */     return getFullORB().getTaggedProfileTemplateFactoryFinder();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectKeyFactory getObjectKeyFactory() {
/* 655 */     return getFullORB().getObjectKeyFactory();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setObjectKeyFactory(ObjectKeyFactory paramObjectKeyFactory) {
/* 660 */     throw new SecurityException("ORBSingleton: access denied");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleBadServerId(ObjectKey paramObjectKey) {}
/*     */ 
/*     */   
/*     */   public OAInvocationInfo peekInvocationInfo() {
/* 669 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushInvocationInfo(OAInvocationInfo paramOAInvocationInfo) {}
/*     */ 
/*     */   
/*     */   public OAInvocationInfo popInvocationInfo() {
/* 678 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientInvocationInfo createOrIncrementInvocationInfo() {
/* 683 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseOrDecrementInvocationInfo() {}
/*     */ 
/*     */   
/*     */   public ClientInvocationInfo getInvocationInfo() {
/* 692 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConnectionCache getConnectionCache(ContactInfo paramContactInfo) {
/* 697 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResolver(Resolver paramResolver) {}
/*     */ 
/*     */   
/*     */   public Resolver getResolver() {
/* 706 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocalResolver(LocalResolver paramLocalResolver) {}
/*     */ 
/*     */   
/*     */   public LocalResolver getLocalResolver() {
/* 715 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURLOperation(Operation paramOperation) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBadServerIdHandler(BadServerIdHandler paramBadServerIdHandler) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initBadServerIdHandler() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Selector getSelector(int paramInt) {
/* 734 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThreadPoolManager(ThreadPoolManager paramThreadPoolManager) {}
/*     */   
/*     */   public ThreadPoolManager getThreadPoolManager() {
/* 741 */     return null;
/*     */   }
/*     */   
/*     */   public CopierManager getCopierManager() {
/* 745 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateIORClass(String paramString) {
/* 750 */     getFullORB().validateIORClass(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/ORBSingleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */