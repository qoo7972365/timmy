/*    */ package java.net;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import jdk.net.ExtendedSocketOptions;
/*    */ import jdk.net.SocketFlow;
/*    */ import sun.net.ExtendedOptionsImpl;
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
/*    */ class PlainSocketImpl
/*    */   extends AbstractPlainSocketImpl
/*    */ {
/*    */   static {
/* 45 */     initProto();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   PlainSocketImpl() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   PlainSocketImpl(FileDescriptor paramFileDescriptor) {
/* 57 */     this.fd = paramFileDescriptor;
/*    */   }
/*    */   
/*    */   protected <T> void setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 61 */     if (!paramSocketOption.equals(ExtendedSocketOptions.SO_FLOW_SLA)) {
/* 62 */       super.setOption(paramSocketOption, paramT);
/*    */     } else {
/* 64 */       if (isClosedOrPending()) {
/* 65 */         throw new SocketException("Socket closed");
/*    */       }
/* 67 */       ExtendedOptionsImpl.checkSetOptionPermission(paramSocketOption);
/* 68 */       ExtendedOptionsImpl.checkValueType(paramT, SocketFlow.class);
/* 69 */       ExtendedOptionsImpl.setFlowOption(getFileDescriptor(), (SocketFlow)paramT);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/* 74 */     if (!paramSocketOption.equals(ExtendedSocketOptions.SO_FLOW_SLA)) {
/* 75 */       return super.getOption(paramSocketOption);
/*    */     }
/* 77 */     if (isClosedOrPending()) {
/* 78 */       throw new SocketException("Socket closed");
/*    */     }
/* 80 */     ExtendedOptionsImpl.checkGetOptionPermission(paramSocketOption);
/* 81 */     SocketFlow socketFlow = SocketFlow.create();
/* 82 */     ExtendedOptionsImpl.getFlowOption(getFileDescriptor(), socketFlow);
/* 83 */     return (T)socketFlow;
/*    */   }
/*    */   
/*    */   protected void socketSetOption(int paramInt, boolean paramBoolean, Object paramObject) throws SocketException {
/*    */     try {
/* 88 */       socketSetOption0(paramInt, paramBoolean, paramObject);
/* 89 */     } catch (SocketException socketException) {
/* 90 */       if (this.socket == null || !this.socket.isConnected())
/* 91 */         throw socketException; 
/*    */     } 
/*    */   }
/*    */   
/*    */   native void socketCreate(boolean paramBoolean) throws IOException;
/*    */   
/*    */   native void socketConnect(InetAddress paramInetAddress, int paramInt1, int paramInt2) throws IOException;
/*    */   
/*    */   native void socketBind(InetAddress paramInetAddress, int paramInt) throws IOException;
/*    */   
/*    */   native void socketListen(int paramInt) throws IOException;
/*    */   
/*    */   native void socketAccept(SocketImpl paramSocketImpl) throws IOException;
/*    */   
/*    */   native int socketAvailable() throws IOException;
/*    */   
/*    */   native void socketClose0(boolean paramBoolean) throws IOException;
/*    */   
/*    */   native void socketShutdown(int paramInt) throws IOException;
/*    */   
/*    */   static native void initProto();
/*    */   
/*    */   native void socketSetOption0(int paramInt, boolean paramBoolean, Object paramObject) throws SocketException;
/*    */   
/*    */   native int socketGetOption(int paramInt, Object paramObject) throws SocketException;
/*    */   
/*    */   native void socketSendUrgentData(int paramInt) throws IOException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/PlainSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */