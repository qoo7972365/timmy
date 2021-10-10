/*    */ package java.net;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ class SocketSecrets
/*    */ {
/*    */   private static <T> void setOption(Object paramObject, SocketOption<T> paramSocketOption, T paramT) throws IOException {
/*    */     SocketImpl socketImpl;
/* 39 */     if (paramObject instanceof Socket) {
/* 40 */       socketImpl = ((Socket)paramObject).getImpl();
/* 41 */     } else if (paramObject instanceof ServerSocket) {
/* 42 */       socketImpl = ((ServerSocket)paramObject).getImpl();
/*    */     } else {
/* 44 */       throw new IllegalArgumentException();
/*    */     } 
/* 46 */     socketImpl.setOption(paramSocketOption, paramT);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> T getOption(Object paramObject, SocketOption<T> paramSocketOption) throws IOException {
/*    */     SocketImpl socketImpl;
/* 52 */     if (paramObject instanceof Socket) {
/* 53 */       socketImpl = ((Socket)paramObject).getImpl();
/* 54 */     } else if (paramObject instanceof ServerSocket) {
/* 55 */       socketImpl = ((ServerSocket)paramObject).getImpl();
/*    */     } else {
/* 57 */       throw new IllegalArgumentException();
/*    */     } 
/* 59 */     return socketImpl.getOption(paramSocketOption);
/*    */   }
/*    */   
/*    */   private static <T> void setOption(DatagramSocket paramDatagramSocket, SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 63 */     paramDatagramSocket.getImpl().setOption(paramSocketOption, paramT);
/*    */   }
/*    */   
/*    */   private static <T> T getOption(DatagramSocket paramDatagramSocket, SocketOption<T> paramSocketOption) throws IOException {
/* 67 */     return paramDatagramSocket.getImpl().getOption(paramSocketOption);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/SocketSecrets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */