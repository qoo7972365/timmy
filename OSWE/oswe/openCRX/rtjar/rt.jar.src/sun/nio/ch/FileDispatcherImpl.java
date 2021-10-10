/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectableChannel;
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
/*     */ class FileDispatcherImpl
/*     */   extends FileDispatcher
/*     */ {
/*     */   static {
/*  34 */     IOUtil.load();
/*  35 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   FileDispatcherImpl(boolean paramBoolean) {}
/*     */ 
/*     */   
/*     */   FileDispatcherImpl() {}
/*     */ 
/*     */   
/*     */   int read(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/*  46 */     return read0(paramFileDescriptor, paramLong, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int pread(FileDescriptor paramFileDescriptor, long paramLong1, int paramInt, long paramLong2) throws IOException {
/*  52 */     return pread0(paramFileDescriptor, paramLong1, paramInt, paramLong2);
/*     */   }
/*     */   
/*     */   long readv(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/*  56 */     return readv0(paramFileDescriptor, paramLong, paramInt);
/*     */   }
/*     */   
/*     */   int write(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/*  60 */     return write0(paramFileDescriptor, paramLong, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int pwrite(FileDescriptor paramFileDescriptor, long paramLong1, int paramInt, long paramLong2) throws IOException {
/*  66 */     return pwrite0(paramFileDescriptor, paramLong1, paramInt, paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long writev(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException {
/*  72 */     return writev0(paramFileDescriptor, paramLong, paramInt);
/*     */   }
/*     */   
/*     */   long seek(FileDescriptor paramFileDescriptor, long paramLong) throws IOException {
/*  76 */     return seek0(paramFileDescriptor, paramLong);
/*     */   }
/*     */   
/*     */   int force(FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException {
/*  80 */     return force0(paramFileDescriptor, paramBoolean);
/*     */   }
/*     */   
/*     */   int truncate(FileDescriptor paramFileDescriptor, long paramLong) throws IOException {
/*  84 */     return truncate0(paramFileDescriptor, paramLong);
/*     */   }
/*     */   
/*     */   long size(FileDescriptor paramFileDescriptor) throws IOException {
/*  88 */     return size0(paramFileDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int lock(FileDescriptor paramFileDescriptor, boolean paramBoolean1, long paramLong1, long paramLong2, boolean paramBoolean2) throws IOException {
/*  94 */     return lock0(paramFileDescriptor, paramBoolean1, paramLong1, paramLong2, paramBoolean2);
/*     */   }
/*     */   
/*     */   void release(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2) throws IOException {
/*  98 */     release0(paramFileDescriptor, paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   void close(FileDescriptor paramFileDescriptor) throws IOException {
/* 102 */     close0(paramFileDescriptor);
/*     */   }
/*     */   
/*     */   void preClose(FileDescriptor paramFileDescriptor) throws IOException {
/* 106 */     preClose0(paramFileDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileDescriptor duplicateForMapping(FileDescriptor paramFileDescriptor) {
/* 112 */     return new FileDescriptor();
/*     */   }
/*     */   
/*     */   boolean canTransferToDirectly(SelectableChannel paramSelectableChannel) {
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   boolean transferToDirectlyNeedsPositionLock() {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   static native int read0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException;
/*     */   
/*     */   static native int pread0(FileDescriptor paramFileDescriptor, long paramLong1, int paramInt, long paramLong2) throws IOException;
/*     */   
/*     */   static native long readv0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException;
/*     */   
/*     */   static native int write0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException;
/*     */   
/*     */   static native int pwrite0(FileDescriptor paramFileDescriptor, long paramLong1, int paramInt, long paramLong2) throws IOException;
/*     */   
/*     */   static native long writev0(FileDescriptor paramFileDescriptor, long paramLong, int paramInt) throws IOException;
/*     */   
/*     */   static native int force0(FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException;
/*     */   
/*     */   static native long seek0(FileDescriptor paramFileDescriptor, long paramLong) throws IOException;
/*     */   
/*     */   static native int truncate0(FileDescriptor paramFileDescriptor, long paramLong) throws IOException;
/*     */   
/*     */   static native long size0(FileDescriptor paramFileDescriptor) throws IOException;
/*     */   
/*     */   static native int lock0(FileDescriptor paramFileDescriptor, boolean paramBoolean1, long paramLong1, long paramLong2, boolean paramBoolean2) throws IOException;
/*     */   
/*     */   static native void release0(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2) throws IOException;
/*     */   
/*     */   static native void close0(FileDescriptor paramFileDescriptor) throws IOException;
/*     */   
/*     */   static native void preClose0(FileDescriptor paramFileDescriptor) throws IOException;
/*     */   
/*     */   static native void closeIntFD(int paramInt) throws IOException;
/*     */   
/*     */   static native void init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/FileDispatcherImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */