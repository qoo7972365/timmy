/*     */ package sun.management.jdp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.UUID;
/*     */ import sun.management.VMManagement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JdpController
/*     */ {
/*     */   private static class JDPControllerRunner
/*     */     implements Runnable
/*     */   {
/*     */     private final JdpJmxPacket packet;
/*     */     private final JdpBroadcaster bcast;
/*     */     private final int pause;
/*     */     private volatile boolean shutdown = false;
/*     */     
/*     */     private JDPControllerRunner(JdpBroadcaster param1JdpBroadcaster, JdpJmxPacket param1JdpJmxPacket, int param1Int) {
/*  75 */       this.bcast = param1JdpBroadcaster;
/*  76 */       this.packet = param1JdpJmxPacket;
/*  77 */       this.pause = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/*  83 */         while (!this.shutdown) {
/*  84 */           this.bcast.sendPacket(this.packet);
/*     */           try {
/*  86 */             Thread.sleep(this.pause);
/*  87 */           } catch (InterruptedException interruptedException) {}
/*     */         
/*     */         }
/*     */       
/*     */       }
/*  92 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  99 */         stop();
/* 100 */         this.bcast.shutdown();
/* 101 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void stop() {
/* 107 */       this.shutdown = true;
/*     */     }
/*     */   }
/* 110 */   private static JDPControllerRunner controller = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getInteger(String paramString1, int paramInt, String paramString2) throws JdpException {
/*     */     try {
/* 120 */       return (paramString1 == null) ? paramInt : Integer.parseInt(paramString1);
/* 121 */     } catch (NumberFormatException numberFormatException) {
/* 122 */       throw new JdpException(paramString2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static InetAddress getInetAddress(String paramString1, InetAddress paramInetAddress, String paramString2) throws JdpException {
/*     */     try {
/* 129 */       return (paramString1 == null) ? paramInetAddress : InetAddress.getByName(paramString1);
/* 130 */     } catch (UnknownHostException unknownHostException) {
/* 131 */       throw new JdpException(paramString2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Integer getProcessId() {
/*     */     try {
/* 139 */       RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
/* 140 */       Field field = runtimeMXBean.getClass().getDeclaredField("jvm");
/* 141 */       field.setAccessible(true);
/*     */       
/* 143 */       VMManagement vMManagement = (VMManagement)field.get(runtimeMXBean);
/* 144 */       Method method = vMManagement.getClass().getDeclaredMethod("getProcessId", new Class[0]);
/* 145 */       method.setAccessible(true);
/* 146 */       return (Integer)method.invoke(vMManagement, new Object[0]);
/*     */     }
/* 148 */     catch (Exception exception) {
/* 149 */       return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void startDiscoveryService(InetAddress paramInetAddress, int paramInt, String paramString1, String paramString2) throws IOException, JdpException {
/* 167 */     int i = getInteger(
/* 168 */         System.getProperty("com.sun.management.jdp.ttl"), 1, "Invalid jdp packet ttl");
/*     */ 
/*     */ 
/*     */     
/* 172 */     int j = getInteger(
/* 173 */         System.getProperty("com.sun.management.jdp.pause"), 5, "Invalid jdp pause");
/*     */ 
/*     */ 
/*     */     
/* 177 */     j *= 1000;
/*     */ 
/*     */     
/* 180 */     InetAddress inetAddress = getInetAddress(
/* 181 */         System.getProperty("com.sun.management.jdp.source_addr"), null, "Invalid source address provided");
/*     */ 
/*     */ 
/*     */     
/* 185 */     UUID uUID = UUID.randomUUID();
/*     */     
/* 187 */     JdpJmxPacket jdpJmxPacket = new JdpJmxPacket(uUID, paramString2);
/*     */ 
/*     */ 
/*     */     
/* 191 */     String str1 = System.getProperty("sun.java.command");
/* 192 */     if (str1 != null) {
/* 193 */       String[] arrayOfString = str1.split(" ", 2);
/* 194 */       jdpJmxPacket.setMainClass(arrayOfString[0]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 199 */     jdpJmxPacket.setInstanceName(paramString1);
/*     */ 
/*     */ 
/*     */     
/* 203 */     String str2 = System.getProperty("java.rmi.server.hostname");
/* 204 */     jdpJmxPacket.setRmiHostname(str2);
/*     */ 
/*     */     
/* 207 */     jdpJmxPacket.setBroadcastInterval((new Integer(j)).toString());
/*     */ 
/*     */     
/* 210 */     Integer integer = getProcessId();
/* 211 */     if (integer != null) {
/* 212 */       jdpJmxPacket.setProcessId(integer.toString());
/*     */     }
/*     */     
/* 215 */     JdpBroadcaster jdpBroadcaster = new JdpBroadcaster(paramInetAddress, inetAddress, paramInt, i);
/*     */ 
/*     */     
/* 218 */     stopDiscoveryService();
/*     */     
/* 220 */     controller = new JDPControllerRunner(jdpBroadcaster, jdpJmxPacket, j);
/*     */     
/* 222 */     Thread thread = new Thread(controller, "JDP broadcaster");
/* 223 */     thread.setDaemon(true);
/* 224 */     thread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void stopDiscoveryService() {
/* 232 */     if (controller != null) {
/* 233 */       controller.stop();
/* 234 */       controller = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/jdp/JdpController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */