/*     */ package sun.rmi.transport.tcp;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.rmi.ConnectException;
/*     */ import java.rmi.ConnectIOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnknownHostException;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.rmi.server.RMISocketFactory;
/*     */ import java.security.AccessController;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.NewThreadAction;
/*     */ import sun.rmi.transport.Channel;
/*     */ import sun.rmi.transport.Endpoint;
/*     */ import sun.rmi.transport.Target;
/*     */ import sun.rmi.transport.Transport;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCPEndpoint
/*     */   implements Endpoint
/*     */ {
/*     */   private String host;
/*     */   private int port;
/*     */   private final RMIClientSocketFactory csf;
/*     */   private final RMIServerSocketFactory ssf;
/*  75 */   private int listenPort = -1;
/*     */   
/*  77 */   private TCPTransport transport = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static String localHost;
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getInt(String paramString, int paramInt) {
/*  86 */     return ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction(paramString, paramInt))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(String paramString) {
/*  91 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction(paramString))).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getHostnameProperty() {
/*  98 */     return AccessController.<String>doPrivileged(new GetPropertyAction("java.rmi.server.hostname"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean localHostKnown = true;
/*     */   
/*     */   private static final Map<TCPEndpoint, LinkedList<TCPEndpoint>> localEndpoints;
/*     */   private static final int FORMAT_HOST_PORT = 0;
/*     */   private static final int FORMAT_HOST_PORT_FACTORY = 1;
/*     */   
/*     */   static {
/* 109 */     localHost = getHostnameProperty();
/*     */ 
/*     */     
/* 112 */     if (localHost == null) {
/*     */       try {
/* 114 */         InetAddress inetAddress = InetAddress.getLocalHost();
/* 115 */         byte[] arrayOfByte = inetAddress.getAddress();
/* 116 */         if (arrayOfByte[0] == Byte.MAX_VALUE && arrayOfByte[1] == 0 && arrayOfByte[2] == 0 && arrayOfByte[3] == 1)
/*     */         {
/*     */ 
/*     */           
/* 120 */           localHostKnown = false;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 126 */         if (getBoolean("java.rmi.server.useLocalHostName")) {
/* 127 */           localHost = FQDN.attemptFQDN(inetAddress);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 132 */           localHost = inetAddress.getHostAddress();
/*     */         } 
/* 134 */       } catch (Exception exception) {
/* 135 */         localHostKnown = false;
/* 136 */         localHost = null;
/*     */       } 
/*     */     }
/*     */     
/* 140 */     if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 141 */       TCPTransport.tcpLog.log(Log.BRIEF, "localHostKnown = " + localHostKnown + ", localHost = " + localHost);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     localEndpoints = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TCPEndpoint(String paramString, int paramInt) {
/* 160 */     this(paramString, paramInt, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TCPEndpoint(String paramString, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/* 171 */     if (paramString == null)
/* 172 */       paramString = ""; 
/* 173 */     this.host = paramString;
/* 174 */     this.port = paramInt;
/* 175 */     this.csf = paramRMIClientSocketFactory;
/* 176 */     this.ssf = paramRMIServerSocketFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TCPEndpoint getLocalEndpoint(int paramInt) {
/* 185 */     return getLocalEndpoint(paramInt, null, null);
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
/*     */   public static TCPEndpoint getLocalEndpoint(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/* 197 */     TCPEndpoint tCPEndpoint = null;
/*     */     
/* 199 */     synchronized (localEndpoints) {
/* 200 */       TCPEndpoint tCPEndpoint1 = new TCPEndpoint(null, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/* 201 */       LinkedList<TCPEndpoint> linkedList = localEndpoints.get(tCPEndpoint1);
/* 202 */       String str = resampleLocalHost();
/*     */       
/* 204 */       if (linkedList == null) {
/*     */ 
/*     */ 
/*     */         
/* 208 */         tCPEndpoint = new TCPEndpoint(str, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/* 209 */         linkedList = new LinkedList();
/* 210 */         linkedList.add(tCPEndpoint);
/* 211 */         tCPEndpoint.listenPort = paramInt;
/* 212 */         tCPEndpoint.transport = new TCPTransport(linkedList);
/* 213 */         localEndpoints.put(tCPEndpoint1, linkedList);
/*     */         
/* 215 */         if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 216 */           TCPTransport.tcpLog.log(Log.BRIEF, "created local endpoint for socket factory " + paramRMIServerSocketFactory + " on port " + paramInt);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 221 */         synchronized (linkedList) {
/* 222 */           tCPEndpoint = linkedList.getLast();
/* 223 */           String str1 = tCPEndpoint.host;
/* 224 */           int i = tCPEndpoint.port;
/* 225 */           TCPTransport tCPTransport = tCPEndpoint.transport;
/*     */           
/* 227 */           if (str != null && !str.equals(str1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 232 */             if (i != 0)
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 237 */               linkedList.clear();
/*     */             }
/* 239 */             tCPEndpoint = new TCPEndpoint(str, i, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/* 240 */             tCPEndpoint.listenPort = paramInt;
/* 241 */             tCPEndpoint.transport = tCPTransport;
/* 242 */             linkedList.add(tCPEndpoint);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     return tCPEndpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String resampleLocalHost() {
/* 257 */     String str = getHostnameProperty();
/*     */     
/* 259 */     synchronized (localEndpoints) {
/*     */ 
/*     */       
/* 262 */       if (str != null) {
/* 263 */         if (!localHostKnown) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 268 */           setLocalHost(str);
/* 269 */         } else if (!str.equals(localHost)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 274 */           localHost = str;
/*     */           
/* 276 */           if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 277 */             TCPTransport.tcpLog.log(Log.BRIEF, "updated local hostname to: " + localHost);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 282 */       return localHost;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setLocalHost(String paramString) {
/* 292 */     synchronized (localEndpoints) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       if (!localHostKnown) {
/* 298 */         localHost = paramString;
/* 299 */         localHostKnown = true;
/*     */         
/* 301 */         if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 302 */           TCPTransport.tcpLog.log(Log.BRIEF, "local host set to " + paramString);
/*     */         }
/*     */         
/* 305 */         for (LinkedList<TCPEndpoint> linkedList : localEndpoints.values()) {
/*     */           
/* 307 */           synchronized (linkedList) {
/* 308 */             for (TCPEndpoint tCPEndpoint : linkedList) {
/* 309 */               tCPEndpoint.host = paramString;
/*     */             }
/*     */           } 
/*     */         } 
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
/*     */   static void setDefaultPort(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) {
/* 326 */     TCPEndpoint tCPEndpoint = new TCPEndpoint(null, 0, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/*     */     
/* 328 */     synchronized (localEndpoints) {
/* 329 */       LinkedList<TCPEndpoint> linkedList = localEndpoints.get(tCPEndpoint);
/*     */       
/* 331 */       synchronized (linkedList) {
/* 332 */         int i = linkedList.size();
/* 333 */         TCPEndpoint tCPEndpoint2 = linkedList.getLast();
/*     */         
/* 335 */         for (TCPEndpoint tCPEndpoint3 : linkedList) {
/* 336 */           tCPEndpoint3.port = paramInt;
/*     */         }
/* 338 */         if (i > 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 343 */           linkedList.clear();
/* 344 */           linkedList.add(tCPEndpoint2);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 352 */       TCPEndpoint tCPEndpoint1 = new TCPEndpoint(null, paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory);
/* 353 */       localEndpoints.put(tCPEndpoint1, linkedList);
/*     */       
/* 355 */       if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 356 */         TCPTransport.tcpLog.log(Log.BRIEF, "default port for server socket factory " + paramRMIServerSocketFactory + " and client socket factory " + paramRMIClientSocketFactory + " set to " + paramInt);
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
/*     */   public Transport getOutboundTransport() {
/* 369 */     TCPEndpoint tCPEndpoint = getLocalEndpoint(0, null, null);
/* 370 */     return tCPEndpoint.transport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Collection<TCPTransport> allKnownTransports() {
/*     */     HashSet<TCPTransport> hashSet;
/* 382 */     synchronized (localEndpoints) {
/*     */       
/* 384 */       hashSet = new HashSet(localEndpoints.size());
/* 385 */       for (LinkedList<TCPEndpoint> linkedList : localEndpoints.values()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 391 */         TCPEndpoint tCPEndpoint = linkedList.getFirst();
/* 392 */         hashSet.add(tCPEndpoint.transport);
/*     */       } 
/*     */     } 
/* 395 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shedConnectionCaches() {
/* 403 */     for (TCPTransport tCPTransport : allKnownTransports()) {
/* 404 */       tCPTransport.shedConnectionCaches();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exportObject(Target paramTarget) throws RemoteException {
/* 412 */     this.transport.exportObject(paramTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Channel getChannel() {
/* 419 */     return getOutboundTransport().getChannel(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 426 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 437 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListenPort() {
/* 448 */     return this.listenPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transport getInboundTransport() {
/* 457 */     return this.transport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RMIClientSocketFactory getClientSocketFactory() {
/* 464 */     return this.csf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RMIServerSocketFactory getServerSocketFactory() {
/* 471 */     return this.ssf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 478 */     return "[" + this.host + ":" + this.port + ((this.ssf != null) ? ("," + this.ssf) : "") + ((this.csf != null) ? ("," + this.csf) : "") + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 485 */     return this.port;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 489 */     if (paramObject != null && paramObject instanceof TCPEndpoint) {
/* 490 */       TCPEndpoint tCPEndpoint = (TCPEndpoint)paramObject;
/* 491 */       if (this.port != tCPEndpoint.port || !this.host.equals(tCPEndpoint.host))
/* 492 */         return false; 
/* 493 */       if ((((this.csf == null) ? 1 : 0) ^ ((tCPEndpoint.csf == null) ? 1 : 0)) == 0) if ((((this.ssf == null) ? 1 : 0) ^ ((tCPEndpoint.ssf == null) ? 1 : 0)) == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 502 */           if (this.csf != null && (this.csf
/* 503 */             .getClass() != tCPEndpoint.csf.getClass() || !this.csf.equals(tCPEndpoint.csf)))
/* 504 */             return false; 
/* 505 */           if (this.ssf != null && (this.ssf
/* 506 */             .getClass() != tCPEndpoint.ssf.getClass() || !this.ssf.equals(tCPEndpoint.ssf)))
/* 507 */             return false; 
/* 508 */           return true;
/*     */         }   return false;
/* 510 */     }  return false;
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
/*     */   public void write(ObjectOutput paramObjectOutput) throws IOException {
/* 522 */     if (this.csf == null) {
/* 523 */       paramObjectOutput.writeByte(0);
/* 524 */       paramObjectOutput.writeUTF(this.host);
/* 525 */       paramObjectOutput.writeInt(this.port);
/*     */     } else {
/* 527 */       paramObjectOutput.writeByte(1);
/* 528 */       paramObjectOutput.writeUTF(this.host);
/* 529 */       paramObjectOutput.writeInt(this.port);
/* 530 */       paramObjectOutput.writeObject(this.csf);
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
/*     */   public static TCPEndpoint read(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/*     */     String str;
/*     */     int i;
/* 544 */     RMIClientSocketFactory rMIClientSocketFactory = null;
/*     */     
/* 546 */     byte b = paramObjectInput.readByte();
/* 547 */     switch (b) {
/*     */       case 0:
/* 549 */         str = paramObjectInput.readUTF();
/* 550 */         i = paramObjectInput.readInt();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 565 */         return new TCPEndpoint(str, i, rMIClientSocketFactory, null);case 1: str = paramObjectInput.readUTF(); i = paramObjectInput.readInt(); rMIClientSocketFactory = (RMIClientSocketFactory)paramObjectInput.readObject(); if (Proxy.isProxyClass(rMIClientSocketFactory.getClass())) throw new IOException("Invalid SocketFactory");  return new TCPEndpoint(str, i, rMIClientSocketFactory, null);
/*     */     } 
/*     */     throw new IOException("invalid endpoint format");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeHostPortFormat(DataOutput paramDataOutput) throws IOException {
/* 573 */     if (this.csf != null) {
/* 574 */       throw new InternalError("TCPEndpoint.writeHostPortFormat: called for endpoint with non-null socket factory");
/*     */     }
/*     */     
/* 577 */     paramDataOutput.writeUTF(this.host);
/* 578 */     paramDataOutput.writeInt(this.port);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TCPEndpoint readHostPortFormat(DataInput paramDataInput) throws IOException {
/* 588 */     String str = paramDataInput.readUTF();
/* 589 */     int i = paramDataInput.readInt();
/* 590 */     return new TCPEndpoint(str, i);
/*     */   }
/*     */   
/*     */   private static RMISocketFactory chooseFactory() {
/* 594 */     RMISocketFactory rMISocketFactory = RMISocketFactory.getSocketFactory();
/* 595 */     if (rMISocketFactory == null) {
/* 596 */       rMISocketFactory = TCPTransport.defaultSocketFactory;
/*     */     }
/* 598 */     return rMISocketFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Socket newSocket() throws RemoteException {
/*     */     Socket socket;
/* 605 */     if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 606 */       TCPTransport.tcpLog.log(Log.VERBOSE, "opening socket to " + this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 613 */       RMIClientSocketFactory rMIClientSocketFactory = this.csf;
/* 614 */       if (rMIClientSocketFactory == null) {
/* 615 */         rMIClientSocketFactory = chooseFactory();
/*     */       }
/* 617 */       socket = rMIClientSocketFactory.createSocket(this.host, this.port);
/*     */     }
/* 619 */     catch (UnknownHostException unknownHostException) {
/* 620 */       throw new UnknownHostException("Unknown host: " + this.host, unknownHostException);
/*     */     }
/* 622 */     catch (ConnectException connectException) {
/* 623 */       throw new ConnectException("Connection refused to host: " + this.host, connectException);
/*     */     }
/* 625 */     catch (IOException iOException) {
/*     */       
/*     */       try {
/* 628 */         shedConnectionCaches();
/*     */       }
/* 630 */       catch (OutOfMemoryError|Exception outOfMemoryError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 635 */       throw new ConnectIOException("Exception creating connection to: " + this.host, iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 642 */       socket.setTcpNoDelay(true);
/* 643 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 649 */       socket.setKeepAlive(true);
/* 650 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 654 */     return socket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ServerSocket newServerSocket() throws IOException {
/* 661 */     if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 662 */       TCPTransport.tcpLog.log(Log.VERBOSE, "creating server socket on " + this);
/*     */     }
/*     */ 
/*     */     
/* 666 */     RMIServerSocketFactory rMIServerSocketFactory = this.ssf;
/* 667 */     if (rMIServerSocketFactory == null) {
/* 668 */       rMIServerSocketFactory = chooseFactory();
/*     */     }
/* 670 */     ServerSocket serverSocket = rMIServerSocketFactory.createServerSocket(this.listenPort);
/*     */ 
/*     */ 
/*     */     
/* 674 */     if (this.listenPort == 0) {
/* 675 */       setDefaultPort(serverSocket.getLocalPort(), this.csf, this.ssf);
/*     */     }
/* 677 */     return serverSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FQDN
/*     */     implements Runnable
/*     */   {
/*     */     private String reverseLookup;
/*     */ 
/*     */ 
/*     */     
/*     */     private String hostAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private FQDN(String param1String) {
/* 697 */       this.hostAddress = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static String attemptFQDN(InetAddress param1InetAddress) throws UnknownHostException {
/* 721 */       String str = param1InetAddress.getHostName();
/*     */       
/* 723 */       if (str.indexOf('.') < 0) {
/*     */         
/* 725 */         String str1 = param1InetAddress.getHostAddress();
/* 726 */         FQDN fQDN = new FQDN(str1);
/*     */ 
/*     */         
/* 729 */         int i = TCPEndpoint.getInt("sun.rmi.transport.tcp.localHostNameTimeOut", 10000);
/*     */ 
/*     */         
/*     */         try {
/* 733 */           synchronized (fQDN) {
/* 734 */             fQDN.getFQDN();
/*     */ 
/*     */             
/* 737 */             fQDN.wait(i);
/*     */           } 
/* 739 */         } catch (InterruptedException interruptedException) {
/*     */           
/* 741 */           Thread.currentThread().interrupt();
/*     */         } 
/* 743 */         str = fQDN.getHost();
/*     */         
/* 745 */         if (str == null || str.equals("") || str
/* 746 */           .indexOf('.') < 0)
/*     */         {
/* 748 */           str = str1;
/*     */         }
/*     */       } 
/* 751 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void getFQDN() {
/* 763 */       Thread thread = AccessController.<Thread>doPrivileged(new NewThreadAction(this, "FQDN Finder", true));
/*     */       
/* 765 */       thread.start();
/*     */     }
/*     */     
/*     */     private synchronized String getHost() {
/* 769 */       return this.reverseLookup;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 777 */       String str = null;
/*     */ 
/*     */       
/* 780 */       try { str = InetAddress.getByName(this.hostAddress).getHostName(); }
/* 781 */       catch (UnknownHostException unknownHostException) {  }
/*     */       finally
/* 783 */       { synchronized (this) {
/* 784 */           this.reverseLookup = str;
/* 785 */           notify();
/*     */         }  }
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/tcp/TCPEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */