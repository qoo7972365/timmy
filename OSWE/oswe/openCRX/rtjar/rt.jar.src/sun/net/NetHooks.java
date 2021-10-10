/*    */ package sun.net;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import sun.net.sdp.SdpProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NetHooks
/*    */ {
/*    */   public static abstract class Provider
/*    */   {
/*    */     public abstract void implBeforeTcpBind(FileDescriptor param1FileDescriptor, InetAddress param1InetAddress, int param1Int) throws IOException;
/*    */     
/*    */     public abstract void implBeforeTcpConnect(FileDescriptor param1FileDescriptor, InetAddress param1InetAddress, int param1Int) throws IOException;
/*    */   }
/*    */   
/* 76 */   private static final Provider provider = new SdpProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void beforeTcpBind(FileDescriptor paramFileDescriptor, InetAddress paramInetAddress, int paramInt) throws IOException {
/* 86 */     provider.implBeforeTcpBind(paramFileDescriptor, paramInetAddress, paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void beforeTcpConnect(FileDescriptor paramFileDescriptor, InetAddress paramInetAddress, int paramInt) throws IOException {
/* 97 */     provider.implBeforeTcpConnect(paramFileDescriptor, paramInetAddress, paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/NetHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */