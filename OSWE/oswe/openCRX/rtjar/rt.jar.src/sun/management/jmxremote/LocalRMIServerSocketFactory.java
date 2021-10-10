/*     */ package sun.management.jmxremote;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LocalRMIServerSocketFactory
/*     */   implements RMIServerSocketFactory
/*     */ {
/*     */   public ServerSocket createServerSocket(int paramInt) throws IOException {
/*  49 */     return new ServerSocket(paramInt) {
/*     */         public Socket accept() throws IOException {
/*     */           Enumeration<NetworkInterface> enumeration;
/*  52 */           Socket socket = super.accept();
/*  53 */           InetAddress inetAddress = socket.getInetAddress();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  59 */           if (inetAddress == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  64 */             String str = "";
/*  65 */             if (socket.isClosed()) {
/*  66 */               str = " Socket is closed.";
/*  67 */             } else if (!socket.isConnected()) {
/*  68 */               str = " Socket is not connected";
/*     */             } 
/*     */             try {
/*  71 */               socket.close();
/*  72 */             } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */             
/*  76 */             throw new IOException("The server sockets created using the LocalRMIServerSocketFactory only accept connections from clients running on the host where the RMI remote objects have been exported. Couldn't determine client address." + str);
/*     */           } 
/*     */           
/*  79 */           if (inetAddress.isLoopbackAddress())
/*     */           {
/*  81 */             return socket;
/*     */           }
/*     */ 
/*     */           
/*     */           try {
/*  86 */             enumeration = NetworkInterface.getNetworkInterfaces();
/*  87 */           } catch (SocketException socketException) {
/*     */             try {
/*  89 */               socket.close();
/*  90 */             } catch (IOException iOException) {}
/*     */ 
/*     */             
/*  93 */             throw new IOException("The server sockets created using the LocalRMIServerSocketFactory only accept connections from clients running on the host where the RMI remote objects have been exported.", socketException);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*  98 */           while (enumeration.hasMoreElements()) {
/*  99 */             NetworkInterface networkInterface = enumeration.nextElement();
/* 100 */             Enumeration<InetAddress> enumeration1 = networkInterface.getInetAddresses();
/* 101 */             while (enumeration1.hasMoreElements()) {
/* 102 */               InetAddress inetAddress1 = enumeration1.nextElement();
/* 103 */               if (inetAddress1.equals(inetAddress)) {
/* 104 */                 return socket;
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/*     */           try {
/* 110 */             socket.close();
/* 111 */           } catch (IOException iOException) {}
/*     */ 
/*     */           
/* 114 */           throw new IOException("The server sockets created using the LocalRMIServerSocketFactory only accept connections from clients running on the host where the RMI remote objects have been exported.");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 125 */     return paramObject instanceof LocalRMIServerSocketFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 133 */     return getClass().hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/jmxremote/LocalRMIServerSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */