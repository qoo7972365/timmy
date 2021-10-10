/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.ServerSocketChannel;
/*    */ import java.nio.channels.SocketChannel;
/*    */ import java.nio.channels.spi.SelectorProvider;
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
/*    */ public final class Secrets
/*    */ {
/*    */   private static SelectorProvider provider() {
/* 42 */     SelectorProvider selectorProvider = SelectorProvider.provider();
/* 43 */     if (!(selectorProvider instanceof SelectorProviderImpl))
/* 44 */       throw new UnsupportedOperationException(); 
/* 45 */     return selectorProvider;
/*    */   }
/*    */   
/*    */   public static SocketChannel newSocketChannel(FileDescriptor paramFileDescriptor) {
/*    */     try {
/* 50 */       return new SocketChannelImpl(provider(), paramFileDescriptor, false);
/* 51 */     } catch (IOException iOException) {
/* 52 */       throw new AssertionError(iOException);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ServerSocketChannel newServerSocketChannel(FileDescriptor paramFileDescriptor) {
/*    */     try {
/* 58 */       return new ServerSocketChannelImpl(provider(), paramFileDescriptor, false);
/* 59 */     } catch (IOException iOException) {
/* 60 */       throw new AssertionError(iOException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/Secrets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */