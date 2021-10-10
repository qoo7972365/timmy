/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDRInputObject;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.oa.poa.Policies;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*     */ import com.sun.corba.se.pept.broker.Broker;
/*     */ import com.sun.corba.se.pept.encoding.InputObject;
/*     */ import com.sun.corba.se.pept.encoding.OutputObject;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.pept.transport.Acceptor;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.pept.transport.EventHandler;
/*     */ import com.sun.corba.se.pept.transport.InboundConnectionCache;
/*     */ import com.sun.corba.se.pept.transport.Selector;
/*     */ import com.sun.corba.se.spi.extension.RequestPartitioningPolicy;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfileTemplate;
/*     */ import com.sun.corba.se.spi.ior.iiop.AlternateIIOPAddressComponent;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPAddress;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.Work;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.transport.CorbaAcceptor;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import com.sun.corba.se.spi.transport.SocketInfo;
/*     */ import com.sun.corba.se.spi.transport.SocketOrChannelAcceptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SocketOrChannelAcceptorImpl
/*     */   extends EventHandlerBase
/*     */   implements CorbaAcceptor, SocketOrChannelAcceptor, Work, SocketInfo, LegacyServerSocketEndPointInfo
/*     */ {
/*     */   protected ServerSocketChannel serverSocketChannel;
/*     */   protected ServerSocket serverSocket;
/*     */   protected int port;
/*     */   protected long enqueueTime;
/*     */   protected boolean initialized;
/*     */   protected ORBUtilSystemException wrapper;
/*     */   protected InboundConnectionCache connectionCache;
/* 101 */   protected String type = "";
/* 102 */   protected String name = "";
/*     */   
/*     */   protected String hostname;
/*     */   
/*     */   protected int locatorPort;
/*     */   
/*     */   public SocketOrChannelAcceptorImpl(ORB paramORB) {
/* 109 */     this.orb = paramORB;
/* 110 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.transport");
/*     */ 
/*     */     
/* 113 */     setWork(this);
/* 114 */     this.initialized = false;
/*     */ 
/*     */     
/* 117 */     this.hostname = paramORB.getORBData().getORBServerHost();
/* 118 */     this.name = "NO_NAME";
/* 119 */     this.locatorPort = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketOrChannelAcceptorImpl(ORB paramORB, int paramInt) {
/* 125 */     this(paramORB);
/* 126 */     this.port = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketOrChannelAcceptorImpl(ORB paramORB, int paramInt, String paramString1, String paramString2) {
/* 133 */     this(paramORB, paramInt);
/* 134 */     this.name = paramString1;
/* 135 */     this.type = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean initialize() {
/* 146 */     if (this.initialized) {
/* 147 */       return false;
/*     */     }
/* 149 */     if (this.orb.transportDebugFlag) {
/* 150 */       dprint(".initialize: " + this);
/*     */     }
/* 152 */     InetSocketAddress inetSocketAddress = null;
/*     */     try {
/* 154 */       if (this.orb.getORBData().getListenOnAllInterfaces().equals("com.sun.CORBA.INTERNAL USE ONLY: listen on all interfaces")) {
/* 155 */         inetSocketAddress = new InetSocketAddress(this.port);
/*     */       } else {
/* 157 */         String str = this.orb.getORBData().getORBServerHost();
/* 158 */         inetSocketAddress = new InetSocketAddress(str, this.port);
/*     */       } 
/* 160 */       this
/* 161 */         .serverSocket = this.orb.getORBData().getSocketFactory().createServerSocket(this.type, inetSocketAddress);
/* 162 */       internalInitialize();
/* 163 */     } catch (Throwable throwable) {
/* 164 */       throw this.wrapper.createListenerFailed(throwable, Integer.toString(this.port));
/*     */     } 
/* 166 */     this.initialized = true;
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void internalInitialize() throws Exception {
/* 177 */     this.port = this.serverSocket.getLocalPort();
/*     */ 
/*     */ 
/*     */     
/* 181 */     this.orb.getCorbaTransportManager().getInboundConnectionCache((Acceptor)this);
/*     */ 
/*     */ 
/*     */     
/* 185 */     this.serverSocketChannel = this.serverSocket.getChannel();
/*     */     
/* 187 */     if (this.serverSocketChannel != null) {
/* 188 */       setUseSelectThreadToWait(this.orb
/* 189 */           .getORBData().acceptorSocketUseSelectThreadToWait());
/* 190 */       this.serverSocketChannel.configureBlocking(
/* 191 */           !this.orb.getORBData().acceptorSocketUseSelectThreadToWait());
/*     */     } else {
/*     */       
/* 194 */       setUseSelectThreadToWait(false);
/*     */     } 
/* 196 */     setUseWorkerThreadForEvent(this.orb
/* 197 */         .getORBData().acceptorSocketUseWorkerThreadForEvent());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean initialized() {
/* 203 */     return this.initialized;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConnectionCacheType() {
/* 208 */     return getClass().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setConnectionCache(InboundConnectionCache paramInboundConnectionCache) {
/* 213 */     this.connectionCache = paramInboundConnectionCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public InboundConnectionCache getConnectionCache() {
/* 218 */     return this.connectionCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRegisterAcceptEvent() {
/* 223 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept() {
/*     */     try {
/* 229 */       SocketChannel socketChannel = null;
/* 230 */       Socket socket = null;
/* 231 */       if (this.serverSocketChannel == null) {
/* 232 */         socket = this.serverSocket.accept();
/*     */       } else {
/* 234 */         socketChannel = this.serverSocketChannel.accept();
/* 235 */         socket = socketChannel.socket();
/*     */       } 
/* 237 */       this.orb.getORBData().getSocketFactory()
/* 238 */         .setAcceptedSocketOptions((Acceptor)this, this.serverSocket, socket);
/* 239 */       if (this.orb.transportDebugFlag) {
/* 240 */         dprint(".accept: " + ((this.serverSocketChannel == null) ? this.serverSocket
/*     */             
/* 242 */             .toString() : this.serverSocketChannel
/* 243 */             .toString()));
/*     */       }
/*     */       
/* 246 */       SocketOrChannelConnectionImpl socketOrChannelConnectionImpl = new SocketOrChannelConnectionImpl(this.orb, (Acceptor)this, socket);
/*     */       
/* 248 */       if (this.orb.transportDebugFlag) {
/* 249 */         dprint(".accept: new: " + socketOrChannelConnectionImpl);
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
/* 262 */       getConnectionCache().stampTime((Connection)socketOrChannelConnectionImpl);
/* 263 */       getConnectionCache().put((Acceptor)this, (Connection)socketOrChannelConnectionImpl);
/*     */       
/* 265 */       if (socketOrChannelConnectionImpl.shouldRegisterServerReadEvent()) {
/* 266 */         Selector selector = this.orb.getTransportManager().getSelector(0);
/* 267 */         if (selector != null) {
/* 268 */           if (this.orb.transportDebugFlag) {
/* 269 */             dprint(".accept: registerForEvent: " + socketOrChannelConnectionImpl);
/*     */           }
/* 271 */           selector.registerForEvent(socketOrChannelConnectionImpl.getEventHandler());
/*     */         } 
/*     */       } 
/*     */       
/* 275 */       getConnectionCache().reclaim();
/*     */     }
/* 277 */     catch (IOException iOException) {
/* 278 */       if (this.orb.transportDebugFlag) {
/* 279 */         dprint(".accept:", iOException);
/*     */       }
/* 281 */       Selector selector = this.orb.getTransportManager().getSelector(0);
/* 282 */       if (selector != null) {
/* 283 */         selector.unregisterForEvent(this);
/*     */         
/* 285 */         selector.registerForEvent(this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 296 */       if (this.orb.transportDebugFlag) {
/* 297 */         dprint(".close->:");
/*     */       }
/* 299 */       Selector selector = this.orb.getTransportManager().getSelector(0);
/* 300 */       if (selector != null) {
/* 301 */         selector.unregisterForEvent(this);
/*     */       }
/* 303 */       if (this.serverSocketChannel != null) {
/* 304 */         this.serverSocketChannel.close();
/*     */       }
/* 306 */       if (this.serverSocket != null) {
/* 307 */         this.serverSocket.close();
/*     */       }
/* 309 */     } catch (IOException iOException) {
/* 310 */       if (this.orb.transportDebugFlag) {
/* 311 */         dprint(".close:", iOException);
/*     */       }
/*     */     } finally {
/* 314 */       if (this.orb.transportDebugFlag) {
/* 315 */         dprint(".close<-:");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EventHandler getEventHandler() {
/* 322 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getObjectAdapterId() {
/* 332 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getObjectAdapterManagerId() {
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToIORTemplate(IORTemplate paramIORTemplate, Policies paramPolicies, String paramString) {
/* 344 */     Iterator<TaggedProfileTemplate> iterator = paramIORTemplate.iteratorById(0);
/*     */ 
/*     */     
/* 347 */     String str = this.orb.getORBData().getORBServerHost();
/*     */     
/* 349 */     if (iterator.hasNext()) {
/*     */ 
/*     */       
/* 352 */       IIOPAddress iIOPAddress = IIOPFactories.makeIIOPAddress(this.orb, str, this.port);
/*     */       
/* 354 */       AlternateIIOPAddressComponent alternateIIOPAddressComponent = IIOPFactories.makeAlternateIIOPAddressComponent(iIOPAddress);
/*     */       
/* 356 */       while (iterator.hasNext()) {
/*     */         
/* 358 */         TaggedProfileTemplate taggedProfileTemplate = iterator.next();
/* 359 */         taggedProfileTemplate.add(alternateIIOPAddressComponent);
/*     */       } 
/*     */     } else {
/* 362 */       int i; GIOPVersion gIOPVersion = this.orb.getORBData().getGIOPVersion();
/*     */       
/* 364 */       if (paramPolicies.forceZeroPort()) {
/* 365 */         i = 0;
/* 366 */       } else if (paramPolicies.isTransient()) {
/* 367 */         i = this.port;
/*     */       } else {
/*     */         
/* 370 */         i = this.orb.getLegacyServerSocketManager().legacyGetPersistentServerPort("IIOP_CLEAR_TEXT");
/*     */       } 
/*     */       
/* 373 */       IIOPAddress iIOPAddress = IIOPFactories.makeIIOPAddress(this.orb, str, i);
/*     */       
/* 375 */       IIOPProfileTemplate iIOPProfileTemplate = IIOPFactories.makeIIOPProfileTemplate(this.orb, gIOPVersion, iIOPAddress);
/* 376 */       if (gIOPVersion.supportsIORIIOPProfileComponents()) {
/* 377 */         iIOPProfileTemplate.add(IIOPFactories.makeCodeSetsComponent(this.orb));
/* 378 */         iIOPProfileTemplate.add(IIOPFactories.makeMaxStreamFormatVersionComponent());
/*     */         
/* 380 */         RequestPartitioningPolicy requestPartitioningPolicy = (RequestPartitioningPolicy)paramPolicies.get_effective_policy(1398079491);
/*     */         
/* 382 */         if (requestPartitioningPolicy != null) {
/* 383 */           iIOPProfileTemplate.add(
/* 384 */               IIOPFactories.makeRequestPartitioningComponent(requestPartitioningPolicy
/* 385 */                 .getValue()));
/*     */         }
/* 387 */         if (paramString != null && paramString != "") {
/* 388 */           iIOPProfileTemplate.add(IIOPFactories.makeJavaCodebaseComponent(paramString));
/*     */         }
/* 390 */         if (this.orb.getORBData().isJavaSerializationEnabled()) {
/* 391 */           iIOPProfileTemplate.add(
/* 392 */               IIOPFactories.makeJavaSerializationComponent());
/*     */         }
/*     */       } 
/* 395 */       paramIORTemplate.add(iIOPProfileTemplate);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMonitoringName() {
/* 401 */     return "AcceptedConnections";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectableChannel getChannel() {
/* 411 */     return this.serverSocketChannel;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInterestOps() {
/* 416 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public Acceptor getAcceptor() {
/* 421 */     return (Acceptor)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/* 426 */     throw new RuntimeException("Should not happen.");
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
/*     */   public void doWork() {
/*     */     try {
/* 444 */       if (this.orb.transportDebugFlag) {
/* 445 */         dprint(".doWork->: " + this);
/*     */       }
/* 447 */       if (this.selectionKey.isAcceptable()) {
/* 448 */         accept();
/*     */       }
/* 450 */       else if (this.orb.transportDebugFlag) {
/* 451 */         dprint(".doWork: ! selectionKey.isAcceptable: " + this);
/*     */       }
/*     */     
/* 454 */     } catch (SecurityException securityException) {
/* 455 */       if (this.orb.transportDebugFlag) {
/* 456 */         dprint(".doWork: ignoring SecurityException: " + securityException + " " + this);
/*     */       }
/*     */ 
/*     */       
/* 460 */       String str = ORBUtility.getClassSecurityInfo(getClass());
/* 461 */       this.wrapper.securityExceptionInAccept(securityException, str);
/* 462 */     } catch (Exception exception) {
/* 463 */       if (this.orb.transportDebugFlag) {
/* 464 */         dprint(".doWork: ignoring Exception: " + exception + " " + this);
/*     */       }
/*     */ 
/*     */       
/* 468 */       this.wrapper.exceptionInAccept(exception);
/* 469 */     } catch (Throwable throwable) {
/* 470 */       if (this.orb.transportDebugFlag) {
/* 471 */         dprint(".doWork: ignoring Throwable: " + throwable + " " + this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 492 */       Selector selector = this.orb.getTransportManager().getSelector(0);
/* 493 */       if (selector != null) {
/* 494 */         selector.registerInterestOps(this);
/*     */       }
/*     */       
/* 497 */       if (this.orb.transportDebugFlag) {
/* 498 */         dprint(".doWork<-:" + this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnqueueTime(long paramLong) {
/* 505 */     this.enqueueTime = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getEnqueueTime() {
/* 510 */     return this.enqueueTime;
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
/*     */   public MessageMediator createMessageMediator(Broker paramBroker, Connection paramConnection) {
/* 524 */     SocketOrChannelContactInfoImpl socketOrChannelContactInfoImpl = new SocketOrChannelContactInfoImpl();
/* 525 */     return socketOrChannelContactInfoImpl.createMessageMediator(paramBroker, paramConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageMediator finishCreatingMessageMediator(Broker paramBroker, Connection paramConnection, MessageMediator paramMessageMediator) {
/* 535 */     SocketOrChannelContactInfoImpl socketOrChannelContactInfoImpl = new SocketOrChannelContactInfoImpl();
/* 536 */     return socketOrChannelContactInfoImpl.finishCreatingMessageMediator(paramBroker, paramConnection, paramMessageMediator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputObject createInputObject(Broker paramBroker, MessageMediator paramMessageMediator) {
/* 543 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */     
/* 545 */     return (InputObject)new CDRInputObject((ORB)paramBroker, (CorbaConnection)paramMessageMediator
/* 546 */         .getConnection(), corbaMessageMediator
/* 547 */         .getDispatchBuffer(), corbaMessageMediator
/* 548 */         .getDispatchHeader());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputObject createOutputObject(Broker paramBroker, MessageMediator paramMessageMediator) {
/* 554 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */     
/* 556 */     return (OutputObject)OutputStreamFactory.newCDROutputObject((ORB)paramBroker, (MessageMediator)corbaMessageMediator, (Message)corbaMessageMediator
/* 557 */         .getReplyHeader(), corbaMessageMediator
/* 558 */         .getStreamFormatVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket getServerSocket() {
/* 568 */     return this.serverSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     String str;
/* 579 */     if (this.serverSocketChannel == null) {
/* 580 */       if (this.serverSocket == null) {
/* 581 */         str = "(not initialized)";
/*     */       } else {
/* 583 */         str = this.serverSocket.toString();
/*     */       } 
/*     */     } else {
/* 586 */       str = this.serverSocketChannel.toString();
/*     */     } 
/*     */     
/* 589 */     return 
/* 590 */       toStringName() + "[" + str + " " + this.type + " " + 
/*     */ 
/*     */ 
/*     */       
/* 594 */       shouldUseSelectThreadToWait() + " " + 
/* 595 */       shouldUseWorkerThreadForEvent() + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String toStringName() {
/* 601 */     return "SocketOrChannelAcceptorImpl";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 606 */     ORBUtility.dprint(toStringName(), paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString, Throwable paramThrowable) {
/* 611 */     dprint(paramString);
/* 612 */     paramThrowable.printStackTrace(System.out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 623 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHostName() {
/* 628 */     return this.hostname;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 633 */     return this.hostname;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 638 */     return this.port;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLocatorPort() {
/* 643 */     return this.locatorPort;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocatorPort(int paramInt) {
/* 648 */     this.locatorPort = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 656 */     return 
/* 657 */       this.name.equals("NO_NAME") ? 
/* 658 */       toString() : this.name;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/SocketOrChannelAcceptorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */