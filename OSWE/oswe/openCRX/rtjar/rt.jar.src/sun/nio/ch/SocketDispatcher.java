/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.FileDescriptor;
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
/*    */ class SocketDispatcher
/*    */   extends NativeDispatcher
/*    */ {
/*    */   int read(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/* 39 */     return FileDispatcherImpl.read0(paramFileDescriptor, paramLong, paramInt);
/*    */   }
/*    */   
/*    */   long readv(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/* 43 */     return FileDispatcherImpl.readv0(paramFileDescriptor, paramLong, paramInt);
/*    */   }
/*    */   
/*    */   int write(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/* 47 */     return FileDispatcherImpl.write0(paramFileDescriptor, paramLong, paramInt);
/*    */   }
/*    */   
/*    */   long writev(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/* 51 */     return FileDispatcherImpl.writev0(paramFileDescriptor, paramLong, paramInt);
/*    */   }
/*    */   
/*    */   void close(FileDescriptor paramFileDescriptor) throws IOException {
/* 55 */     FileDispatcherImpl.close0(paramFileDescriptor);
/*    */   }
/*    */   
/*    */   void preClose(FileDescriptor paramFileDescriptor) throws IOException {
/* 59 */     FileDispatcherImpl.preClose0(paramFileDescriptor);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/SocketDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */