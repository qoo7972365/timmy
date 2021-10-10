/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.AsynchronousChannelGroup;
/*    */ import java.nio.channels.AsynchronousServerSocketChannel;
/*    */ import java.nio.channels.AsynchronousSocketChannel;
/*    */ import java.nio.channels.IllegalChannelGroupException;
/*    */ import java.nio.channels.spi.AsynchronousChannelProvider;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.ThreadFactory;
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
/*    */ public class LinuxAsynchronousChannelProvider
/*    */   extends AsynchronousChannelProvider
/*    */ {
/*    */   private static volatile EPollPort defaultPort;
/*    */   
/*    */   private EPollPort defaultEventPort() throws IOException {
/* 40 */     if (defaultPort == null) {
/* 41 */       synchronized (LinuxAsynchronousChannelProvider.class) {
/* 42 */         if (defaultPort == null) {
/* 43 */           defaultPort = (new EPollPort(this, ThreadPool.getDefault())).start();
/*    */         }
/*    */       } 
/*    */     }
/* 47 */     return defaultPort;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AsynchronousChannelGroup openAsynchronousChannelGroup(int paramInt, ThreadFactory paramThreadFactory) throws IOException {
/* 57 */     return (new EPollPort(this, ThreadPool.create(paramInt, paramThreadFactory))).start();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AsynchronousChannelGroup openAsynchronousChannelGroup(ExecutorService paramExecutorService, int paramInt) throws IOException {
/* 64 */     return (new EPollPort(this, ThreadPool.wrap(paramExecutorService, paramInt))).start();
/*    */   }
/*    */   
/*    */   private Port toPort(AsynchronousChannelGroup paramAsynchronousChannelGroup) throws IOException {
/* 68 */     if (paramAsynchronousChannelGroup == null) {
/* 69 */       return defaultEventPort();
/*    */     }
/* 71 */     if (!(paramAsynchronousChannelGroup instanceof EPollPort))
/* 72 */       throw new IllegalChannelGroupException(); 
/* 73 */     return (Port)paramAsynchronousChannelGroup;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AsynchronousServerSocketChannel openAsynchronousServerSocketChannel(AsynchronousChannelGroup paramAsynchronousChannelGroup) throws IOException {
/* 81 */     return new UnixAsynchronousServerSocketChannelImpl(toPort(paramAsynchronousChannelGroup));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AsynchronousSocketChannel openAsynchronousSocketChannel(AsynchronousChannelGroup paramAsynchronousChannelGroup) throws IOException {
/* 88 */     return new UnixAsynchronousSocketChannelImpl(toPort(paramAsynchronousChannelGroup));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/LinuxAsynchronousChannelProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */