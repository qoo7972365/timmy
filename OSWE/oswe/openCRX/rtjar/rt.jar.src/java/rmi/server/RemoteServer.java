/*     */ package java.rmi.server;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import sun.rmi.server.UnicastServerRef;
/*     */ import sun.rmi.transport.tcp.TCPTransport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RemoteServer
/*     */   extends RemoteObject
/*     */ {
/*     */   private static final long serialVersionUID = -4100238210092549637L;
/*     */   
/*     */   protected RemoteServer() {}
/*     */   
/*     */   protected RemoteServer(RemoteRef paramRemoteRef) {
/*  62 */     super(paramRemoteRef);
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
/*     */   public static String getClientHost() throws ServerNotActiveException {
/*  77 */     return TCPTransport.getClientHost();
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
/*     */   public static void setLog(OutputStream paramOutputStream) {
/*  98 */     logNull = (paramOutputStream == null);
/*  99 */     UnicastServerRef.callLog.setOutputStream(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrintStream getLog() {
/* 110 */     return logNull ? null : UnicastServerRef.callLog.getPrintStream();
/*     */   }
/*     */ 
/*     */   
/* 114 */   private static boolean logNull = !UnicastServerRef.logCalls;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/server/RemoteServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */