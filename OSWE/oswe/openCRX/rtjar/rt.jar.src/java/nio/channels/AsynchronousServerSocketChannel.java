/*     */ package java.nio.channels;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketOption;
/*     */ import java.nio.channels.spi.AsynchronousChannelProvider;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AsynchronousServerSocketChannel
/*     */   implements AsynchronousChannel, NetworkChannel
/*     */ {
/*     */   private final AsynchronousChannelProvider provider;
/*     */   
/*     */   protected AsynchronousServerSocketChannel(AsynchronousChannelProvider paramAsynchronousChannelProvider) {
/* 106 */     this.provider = paramAsynchronousChannelProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AsynchronousChannelProvider provider() {
/* 115 */     return this.provider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AsynchronousServerSocketChannel open(AsynchronousChannelGroup paramAsynchronousChannelGroup) throws IOException {
/* 144 */     AsynchronousChannelProvider asynchronousChannelProvider = (paramAsynchronousChannelGroup == null) ? AsynchronousChannelProvider.provider() : paramAsynchronousChannelGroup.provider();
/* 145 */     return asynchronousChannelProvider.openAsynchronousServerSocketChannel(paramAsynchronousChannelGroup);
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
/*     */   public static AsynchronousServerSocketChannel open() throws IOException {
/* 166 */     return open(null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AsynchronousServerSocketChannel bind(SocketAddress paramSocketAddress) throws IOException {
/* 193 */     return bind(paramSocketAddress, 0);
/*     */   }
/*     */   
/*     */   public abstract AsynchronousServerSocketChannel bind(SocketAddress paramSocketAddress, int paramInt) throws IOException;
/*     */   
/*     */   public abstract <T> AsynchronousServerSocketChannel setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException;
/*     */   
/*     */   public abstract <A> void accept(A paramA, CompletionHandler<AsynchronousSocketChannel, ? super A> paramCompletionHandler);
/*     */   
/*     */   public abstract Future<AsynchronousSocketChannel> accept();
/*     */   
/*     */   public abstract SocketAddress getLocalAddress() throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/AsynchronousServerSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */