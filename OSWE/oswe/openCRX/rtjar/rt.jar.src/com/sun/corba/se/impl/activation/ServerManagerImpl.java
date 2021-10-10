/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ActivationSystemException;
/*     */ import com.sun.corba.se.impl.oa.poa.BadServerIdHandler;
/*     */ import com.sun.corba.se.spi.activation.EndPointInfo;
/*     */ import com.sun.corba.se.spi.activation.InvalidORBid;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocation;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocationPerORB;
/*     */ import com.sun.corba.se.spi.activation.NoSuchEndPoint;
/*     */ import com.sun.corba.se.spi.activation.ORBAlreadyRegistered;
/*     */ import com.sun.corba.se.spi.activation.ORBPortInfo;
/*     */ import com.sun.corba.se.spi.activation.Repository;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;
/*     */ import com.sun.corba.se.spi.activation.Server;
/*     */ import com.sun.corba.se.spi.activation.ServerAlreadyActive;
/*     */ import com.sun.corba.se.spi.activation.ServerAlreadyInstalled;
/*     */ import com.sun.corba.se.spi.activation.ServerAlreadyUninstalled;
/*     */ import com.sun.corba.se.spi.activation.ServerHeldDown;
/*     */ import com.sun.corba.se.spi.activation.ServerNotActive;
/*     */ import com.sun.corba.se.spi.activation.ServerNotRegistered;
/*     */ import com.sun.corba.se.spi.activation._ServerManagerImplBase;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPAddress;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.ForwardException;
/*     */ import com.sun.corba.se.spi.transport.CorbaTransportManager;
/*     */ import com.sun.corba.se.spi.transport.SocketOrChannelAcceptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.omg.CORBA.Object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerManagerImpl
/*     */   extends _ServerManagerImplBase
/*     */   implements BadServerIdHandler
/*     */ {
/*     */   HashMap serverTable;
/*     */   Repository repository;
/*     */   CorbaTransportManager transportManager;
/*     */   int initialPort;
/*     */   ORB orb;
/*     */   ActivationSystemException wrapper;
/*     */   String dbDirName;
/*     */   boolean debug = false;
/*     */   private int serverStartupDelay;
/*     */   
/*     */   ServerManagerImpl(ORB paramORB, CorbaTransportManager paramCorbaTransportManager, Repository paramRepository, String paramString, boolean paramBoolean) {
/* 107 */     this.orb = paramORB;
/* 108 */     this.wrapper = ActivationSystemException.get(paramORB, "orbd.activator");
/*     */     
/* 110 */     this.transportManager = paramCorbaTransportManager;
/* 111 */     this.repository = paramRepository;
/* 112 */     this.dbDirName = paramString;
/* 113 */     this.debug = paramBoolean;
/*     */ 
/*     */ 
/*     */     
/* 117 */     LegacyServerSocketEndPointInfo legacyServerSocketEndPointInfo = paramORB.getLegacyServerSocketManager().legacyGetEndpoint("BOOT_NAMING");
/*     */     
/* 119 */     this
/* 120 */       .initialPort = ((SocketOrChannelAcceptor)legacyServerSocketEndPointInfo).getServerSocket().getLocalPort();
/* 121 */     this.serverTable = new HashMap<>(256);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.serverStartupDelay = 1000;
/* 127 */     String str = System.getProperty("com.sun.CORBA.activation.ServerStartupDelay");
/* 128 */     if (str != null) {
/*     */       try {
/* 130 */         this.serverStartupDelay = Integer.parseInt(str);
/* 131 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 136 */     Class clazz = paramORB.getORBData().getBadServerIdHandler();
/* 137 */     if (clazz == null) {
/* 138 */       paramORB.setBadServerIdHandler(this);
/*     */     } else {
/* 140 */       paramORB.initBadServerIdHandler();
/*     */     } 
/*     */     
/* 143 */     paramORB.connect((Object)this);
/* 144 */     ProcessMonitorThread.start(this.serverTable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void activate(int paramInt) throws ServerAlreadyActive, ServerNotRegistered, ServerHeldDown {
/*     */     ServerTableEntry serverTableEntry;
/* 153 */     Integer integer = new Integer(paramInt);
/*     */     
/* 155 */     synchronized (this.serverTable) {
/* 156 */       serverTableEntry = (ServerTableEntry)this.serverTable.get(integer);
/*     */     } 
/*     */     
/* 159 */     if (serverTableEntry != null && serverTableEntry.isActive()) {
/* 160 */       if (this.debug) {
/* 161 */         System.out.println("ServerManagerImpl: activate for server Id " + paramInt + " failed because server is already active. entry = " + serverTableEntry);
/*     */       }
/*     */ 
/*     */       
/* 165 */       throw new ServerAlreadyActive(paramInt);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 174 */       serverTableEntry = getEntry(paramInt);
/*     */       
/* 176 */       if (this.debug) {
/* 177 */         System.out.println("ServerManagerImpl: locateServer called with  serverId=" + paramInt + " endpointType=" + "IIOP_CLEAR_TEXT" + " block=false");
/*     */       }
/*     */ 
/*     */       
/* 181 */       ServerLocation serverLocation = locateServer(serverTableEntry, "IIOP_CLEAR_TEXT", false);
/*     */       
/* 183 */       if (this.debug) {
/* 184 */         System.out.println("ServerManagerImpl: activate for server Id " + paramInt + " found location " + serverLocation.hostname + " and activated it");
/*     */       }
/*     */     }
/* 187 */     catch (NoSuchEndPoint noSuchEndPoint) {
/* 188 */       if (this.debug) {
/* 189 */         System.out.println("ServerManagerImpl: activate for server Id  threw NoSuchEndpoint exception, which was ignored");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void active(int paramInt, Server paramServer) throws ServerNotRegistered {
/* 197 */     Integer integer = new Integer(paramInt);
/*     */     
/* 199 */     synchronized (this.serverTable) {
/* 200 */       ServerTableEntry serverTableEntry = (ServerTableEntry)this.serverTable.get(integer);
/*     */       
/* 202 */       if (serverTableEntry == null) {
/* 203 */         if (this.debug) {
/* 204 */           System.out.println("ServerManagerImpl: active for server Id " + paramInt + " called, but no such server is registered.");
/*     */         }
/*     */         
/* 207 */         throw this.wrapper.serverNotExpectedToRegister();
/*     */       } 
/* 209 */       if (this.debug) {
/* 210 */         System.out.println("ServerManagerImpl: active for server Id " + paramInt + " called.  This server is now active.");
/*     */       }
/*     */       
/* 213 */       serverTableEntry.register(paramServer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEndpoints(int paramInt, String paramString, EndPointInfo[] paramArrayOfEndPointInfo) throws NoSuchEndPoint, ServerNotRegistered, ORBAlreadyRegistered {
/* 224 */     Integer integer = new Integer(paramInt);
/*     */     
/* 226 */     synchronized (this.serverTable) {
/* 227 */       ServerTableEntry serverTableEntry = (ServerTableEntry)this.serverTable.get(integer);
/*     */       
/* 229 */       if (serverTableEntry == null) {
/* 230 */         if (this.debug) {
/* 231 */           System.out.println("ServerManagerImpl: registerEndpoint for server Id " + paramInt + " called, but no such server is registered.");
/*     */         }
/*     */ 
/*     */         
/* 235 */         throw this.wrapper.serverNotExpectedToRegister();
/*     */       } 
/* 237 */       if (this.debug) {
/* 238 */         System.out.println("ServerManagerImpl: registerEndpoints for server Id " + paramInt + " called.  This server is now active.");
/*     */       }
/*     */ 
/*     */       
/* 242 */       serverTableEntry.registerPorts(paramString, paramArrayOfEndPointInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getActiveServers() {
/* 251 */     int[] arrayOfInt = null;
/*     */     
/* 253 */     synchronized (this.serverTable) {
/*     */ 
/*     */       
/* 256 */       ArrayList<ServerTableEntry> arrayList = new ArrayList(0);
/*     */       
/* 258 */       Iterator<Integer> iterator = this.serverTable.keySet().iterator();
/*     */       
/*     */       try {
/* 261 */         while (iterator.hasNext()) {
/* 262 */           Integer integer = iterator.next();
/*     */           
/* 264 */           ServerTableEntry serverTableEntry = (ServerTableEntry)this.serverTable.get(integer);
/*     */           
/* 266 */           if (serverTableEntry.isValid() && serverTableEntry.isActive()) {
/* 267 */             arrayList.add(serverTableEntry);
/*     */           }
/*     */         } 
/* 270 */       } catch (NoSuchElementException noSuchElementException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 275 */       arrayOfInt = new int[arrayList.size()];
/* 276 */       for (byte b = 0; b < arrayList.size(); b++) {
/* 277 */         ServerTableEntry serverTableEntry = arrayList.get(b);
/* 278 */         arrayOfInt[b] = serverTableEntry.getServerId();
/*     */       } 
/*     */     } 
/*     */     
/* 282 */     if (this.debug) {
/* 283 */       StringBuffer stringBuffer = new StringBuffer();
/* 284 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 285 */         stringBuffer.append(' ');
/* 286 */         stringBuffer.append(arrayOfInt[b]);
/*     */       } 
/*     */       
/* 289 */       System.out.println("ServerManagerImpl: getActiveServers returns" + stringBuffer
/* 290 */           .toString());
/*     */     } 
/*     */     
/* 293 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown(int paramInt) throws ServerNotActive {
/* 299 */     Integer integer = new Integer(paramInt);
/*     */     
/* 301 */     synchronized (this.serverTable) {
/* 302 */       ServerTableEntry serverTableEntry = (ServerTableEntry)this.serverTable.remove(integer);
/*     */       
/* 304 */       if (serverTableEntry == null) {
/* 305 */         if (this.debug) {
/* 306 */           System.out.println("ServerManagerImpl: shutdown for server Id " + paramInt + " throws ServerNotActive.");
/*     */         }
/*     */         
/* 309 */         throw new ServerNotActive(paramInt);
/*     */       } 
/*     */       
/*     */       try {
/* 313 */         serverTableEntry.destroy();
/*     */         
/* 315 */         if (this.debug) {
/* 316 */           System.out.println("ServerManagerImpl: shutdown for server Id " + paramInt + " completed.");
/*     */         }
/* 318 */       } catch (Exception exception) {
/* 319 */         if (this.debug) {
/* 320 */           System.out.println("ServerManagerImpl: shutdown for server Id " + paramInt + " threw exception " + exception);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerTableEntry getEntry(int paramInt) throws ServerNotRegistered {
/* 329 */     Integer integer = new Integer(paramInt);
/* 330 */     ServerTableEntry serverTableEntry = null;
/*     */     
/* 332 */     synchronized (this.serverTable) {
/* 333 */       serverTableEntry = (ServerTableEntry)this.serverTable.get(integer);
/*     */       
/* 335 */       if (this.debug) {
/* 336 */         if (serverTableEntry == null) {
/* 337 */           System.out.println("ServerManagerImpl: getEntry: no active server found.");
/*     */         } else {
/*     */           
/* 340 */           System.out.println("ServerManagerImpl: getEntry:  active server found " + serverTableEntry + ".");
/*     */         } 
/*     */       }
/*     */       
/* 344 */       if (serverTableEntry != null && !serverTableEntry.isValid()) {
/* 345 */         this.serverTable.remove(integer);
/* 346 */         serverTableEntry = null;
/*     */       } 
/*     */       
/* 349 */       if (serverTableEntry == null) {
/* 350 */         ServerDef serverDef = this.repository.getServer(paramInt);
/*     */         
/* 352 */         serverTableEntry = new ServerTableEntry(this.wrapper, paramInt, serverDef, this.initialPort, this.dbDirName, false, this.debug);
/*     */         
/* 354 */         this.serverTable.put(integer, serverTableEntry);
/* 355 */         serverTableEntry.activate();
/*     */       } 
/*     */     } 
/*     */     
/* 359 */     return serverTableEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerLocation locateServer(ServerTableEntry paramServerTableEntry, String paramString, boolean paramBoolean) throws NoSuchEndPoint, ServerNotRegistered, ServerHeldDown {
/* 366 */     ServerLocation serverLocation = new ServerLocation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     if (paramBoolean) {
/*     */       ORBPortInfo[] arrayOfORBPortInfo; byte b1; try {
/* 374 */         arrayOfORBPortInfo = paramServerTableEntry.lookup(paramString);
/* 375 */       } catch (Exception exception) {
/* 376 */         if (this.debug) {
/* 377 */           System.out.println("ServerManagerImpl: locateServer: server held down");
/*     */         }
/*     */         
/* 380 */         throw new ServerHeldDown(paramServerTableEntry.getServerId());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 385 */       String str = this.orb.getLegacyServerSocketManager().legacyGetEndpoint("DEFAULT_ENDPOINT").getHostName();
/* 386 */       serverLocation.hostname = str;
/*     */       
/* 388 */       if (arrayOfORBPortInfo != null) {
/* 389 */         b1 = arrayOfORBPortInfo.length;
/*     */       } else {
/* 391 */         b1 = 0;
/*     */       } 
/* 393 */       serverLocation.ports = new ORBPortInfo[b1];
/* 394 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 395 */         serverLocation.ports[b2] = new ORBPortInfo((arrayOfORBPortInfo[b2]).orbId, (arrayOfORBPortInfo[b2]).port);
/*     */ 
/*     */         
/* 398 */         if (this.debug) {
/* 399 */           System.out.println("ServerManagerImpl: locateServer: server located at location " + serverLocation.hostname + " ORBid  " + (arrayOfORBPortInfo[b2]).orbId + " Port " + (arrayOfORBPortInfo[b2]).port);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     return serverLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerLocationPerORB locateServerForORB(ServerTableEntry paramServerTableEntry, String paramString, boolean paramBoolean) throws InvalidORBid, ServerNotRegistered, ServerHeldDown {
/* 414 */     ServerLocationPerORB serverLocationPerORB = new ServerLocationPerORB();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 420 */     if (paramBoolean) {
/*     */       EndPointInfo[] arrayOfEndPointInfo; byte b1; try {
/* 422 */         arrayOfEndPointInfo = paramServerTableEntry.lookupForORB(paramString);
/* 423 */       } catch (InvalidORBid invalidORBid) {
/* 424 */         throw invalidORBid;
/* 425 */       } catch (Exception exception) {
/* 426 */         if (this.debug) {
/* 427 */           System.out.println("ServerManagerImpl: locateServerForORB: server held down");
/*     */         }
/*     */         
/* 430 */         throw new ServerHeldDown(paramServerTableEntry.getServerId());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 435 */       String str = this.orb.getLegacyServerSocketManager().legacyGetEndpoint("DEFAULT_ENDPOINT").getHostName();
/* 436 */       serverLocationPerORB.hostname = str;
/*     */       
/* 438 */       if (arrayOfEndPointInfo != null) {
/* 439 */         b1 = arrayOfEndPointInfo.length;
/*     */       } else {
/* 441 */         b1 = 0;
/*     */       } 
/* 443 */       serverLocationPerORB.ports = new EndPointInfo[b1];
/* 444 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 445 */         serverLocationPerORB.ports[b2] = new EndPointInfo((arrayOfEndPointInfo[b2]).endpointType, (arrayOfEndPointInfo[b2]).port);
/*     */ 
/*     */         
/* 448 */         if (this.debug) {
/* 449 */           System.out.println("ServerManagerImpl: locateServer: server located at location " + serverLocationPerORB.hostname + " endpointType  " + (arrayOfEndPointInfo[b2]).endpointType + " Port " + (arrayOfEndPointInfo[b2]).port);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 457 */     return serverLocationPerORB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getORBNames(int paramInt) throws ServerNotRegistered {
/*     */     try {
/* 464 */       ServerTableEntry serverTableEntry = getEntry(paramInt);
/* 465 */       return serverTableEntry.getORBList();
/* 466 */     } catch (Exception exception) {
/* 467 */       throw new ServerNotRegistered(paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerTableEntry getRunningEntry(int paramInt) throws ServerNotRegistered {
/* 474 */     ServerTableEntry serverTableEntry = getEntry(paramInt);
/*     */ 
/*     */     
/*     */     try {
/* 478 */       ORBPortInfo[] arrayOfORBPortInfo = serverTableEntry.lookup("IIOP_CLEAR_TEXT");
/* 479 */     } catch (Exception exception) {
/* 480 */       return null;
/*     */     } 
/* 482 */     return serverTableEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void install(int paramInt) throws ServerNotRegistered, ServerHeldDown, ServerAlreadyInstalled {
/* 489 */     ServerTableEntry serverTableEntry = getRunningEntry(paramInt);
/* 490 */     if (serverTableEntry != null) {
/* 491 */       this.repository.install(paramInt);
/* 492 */       serverTableEntry.install();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstall(int paramInt) throws ServerNotRegistered, ServerHeldDown, ServerAlreadyUninstalled {
/* 500 */     ServerTableEntry serverTableEntry = (ServerTableEntry)this.serverTable.get(new Integer(paramInt));
/*     */     
/* 502 */     if (serverTableEntry != null) {
/*     */ 
/*     */       
/* 505 */       serverTableEntry = (ServerTableEntry)this.serverTable.remove(new Integer(paramInt));
/*     */       
/* 507 */       if (serverTableEntry == null) {
/* 508 */         if (this.debug) {
/* 509 */           System.out.println("ServerManagerImpl: shutdown for server Id " + paramInt + " throws ServerNotActive.");
/*     */         }
/*     */         
/* 512 */         throw new ServerHeldDown(paramInt);
/*     */       } 
/*     */       
/* 515 */       serverTableEntry.uninstall();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerLocation locateServer(int paramInt, String paramString) throws NoSuchEndPoint, ServerNotRegistered, ServerHeldDown {
/* 522 */     ServerTableEntry serverTableEntry = getEntry(paramInt);
/* 523 */     if (this.debug) {
/* 524 */       System.out.println("ServerManagerImpl: locateServer called with  serverId=" + paramInt + " endpointType=" + paramString + " block=true");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 531 */     return locateServer(serverTableEntry, paramString, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerLocationPerORB locateServerForORB(int paramInt, String paramString) throws InvalidORBid, ServerNotRegistered, ServerHeldDown {
/* 540 */     ServerTableEntry serverTableEntry = getEntry(paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 545 */     if (this.debug) {
/* 546 */       System.out.println("ServerManagerImpl: locateServerForORB called with  serverId=" + paramInt + " orbId=" + paramString + " block=true");
/*     */     }
/*     */     
/* 549 */     return locateServerForORB(serverTableEntry, paramString, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(ObjectKey paramObjectKey) {
/* 555 */     IOR iOR = null;
/*     */ 
/*     */ 
/*     */     
/* 559 */     ObjectKeyTemplate objectKeyTemplate = paramObjectKey.getTemplate();
/* 560 */     int i = objectKeyTemplate.getServerId();
/* 561 */     String str = objectKeyTemplate.getORBId();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 566 */       ServerTableEntry serverTableEntry = getEntry(i);
/* 567 */       ServerLocationPerORB serverLocationPerORB = locateServerForORB(serverTableEntry, str, true);
/*     */       
/* 569 */       if (this.debug) {
/* 570 */         System.out.println("ServerManagerImpl: handle called for server id" + i + "  orbid  " + str);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 578 */       int j = 0;
/* 579 */       EndPointInfo[] arrayOfEndPointInfo = serverLocationPerORB.ports;
/* 580 */       for (byte b = 0; b < arrayOfEndPointInfo.length; b++) {
/* 581 */         if ((arrayOfEndPointInfo[b]).endpointType.equals("IIOP_CLEAR_TEXT")) {
/* 582 */           j = (arrayOfEndPointInfo[b]).port;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 589 */       IIOPAddress iIOPAddress = IIOPFactories.makeIIOPAddress(this.orb, serverLocationPerORB.hostname, j);
/*     */ 
/*     */       
/* 592 */       IIOPProfileTemplate iIOPProfileTemplate = IIOPFactories.makeIIOPProfileTemplate(this.orb, GIOPVersion.V1_2, iIOPAddress);
/*     */       
/* 594 */       if (GIOPVersion.V1_2.supportsIORIIOPProfileComponents()) {
/* 595 */         iIOPProfileTemplate.add(IIOPFactories.makeCodeSetsComponent(this.orb));
/* 596 */         iIOPProfileTemplate.add(IIOPFactories.makeMaxStreamFormatVersionComponent());
/*     */       } 
/* 598 */       IORTemplate iORTemplate = IORFactories.makeIORTemplate(objectKeyTemplate);
/* 599 */       iORTemplate.add(iIOPProfileTemplate);
/*     */       
/* 601 */       iOR = iORTemplate.makeIOR(this.orb, "IDL:org/omg/CORBA/Object:1.0", paramObjectKey
/* 602 */           .getId());
/* 603 */     } catch (Exception exception) {
/* 604 */       throw this.wrapper.errorInBadServerIdHandler(exception);
/*     */     } 
/*     */     
/* 607 */     if (this.debug) {
/* 608 */       System.out.println("ServerManagerImpl: handle throws ForwardException");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 617 */       Thread.sleep(this.serverStartupDelay);
/* 618 */     } catch (Exception exception) {
/* 619 */       System.out.println("Exception = " + exception);
/* 620 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 623 */     throw new ForwardException(this.orb, iOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEndpoint(String paramString) throws NoSuchEndPoint {
/* 628 */     return this.orb.getLegacyServerSocketManager()
/* 629 */       .legacyGetTransientServerPort(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getServerPortForType(ServerLocationPerORB paramServerLocationPerORB, String paramString) throws NoSuchEndPoint {
/* 636 */     EndPointInfo[] arrayOfEndPointInfo = paramServerLocationPerORB.ports;
/* 637 */     for (byte b = 0; b < arrayOfEndPointInfo.length; b++) {
/* 638 */       if ((arrayOfEndPointInfo[b]).endpointType.equals(paramString)) {
/* 639 */         return (arrayOfEndPointInfo[b]).port;
/*     */       }
/*     */     } 
/* 642 */     throw new NoSuchEndPoint();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/ServerManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */