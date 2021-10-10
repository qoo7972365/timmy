/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
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
/*     */ class SocketOutputStream
/*     */   extends FileOutputStream
/*     */ {
/*     */   static {
/*  44 */     init();
/*     */   }
/*     */   
/*  47 */   private AbstractPlainSocketImpl impl = null;
/*  48 */   private byte[] temp = new byte[1];
/*  49 */   private Socket socket = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closing;
/*     */ 
/*     */ 
/*     */   
/*     */   SocketOutputStream(AbstractPlainSocketImpl paramAbstractPlainSocketImpl) throws IOException {
/*  58 */     super(paramAbstractPlainSocketImpl.getFileDescriptor());
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
/* 161 */     this.closing = false;
/*     */     this.impl = paramAbstractPlainSocketImpl;
/*     */     this.socket = paramAbstractPlainSocketImpl.getSocket(); } public void close() throws IOException {
/* 164 */     if (this.closing)
/*     */       return; 
/* 166 */     this.closing = true;
/* 167 */     if (this.socket != null) {
/* 168 */       if (!this.socket.isClosed())
/* 169 */         this.socket.close(); 
/*     */     } else {
/* 171 */       this.impl.close();
/* 172 */     }  this.closing = false;
/*     */   }
/*     */   
/*     */   public final FileChannel getChannel() {
/*     */     return null;
/*     */   }
/*     */   
/*     */   private void socketWrite(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     if (paramInt2 <= 0 || paramInt1 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1) {
/*     */       if (paramInt2 == 0)
/*     */         return; 
/*     */       throw new ArrayIndexOutOfBoundsException("len == " + paramInt2 + " off == " + paramInt1 + " buffer length == " + paramArrayOfbyte.length);
/*     */     } 
/*     */     FileDescriptor fileDescriptor = this.impl.acquireFD();
/*     */     try {
/*     */       socketWrite0(fileDescriptor, paramArrayOfbyte, paramInt1, paramInt2);
/*     */     } catch (SocketException socketException) {
/*     */       if (socketException instanceof sun.net.ConnectionResetException) {
/*     */         this.impl.setConnectionResetPending();
/*     */         socketException = new SocketException("Connection reset");
/*     */       } 
/*     */       if (this.impl.isClosedOrPending())
/*     */         throw new SocketException("Socket closed"); 
/*     */       throw socketException;
/*     */     } finally {
/*     */       this.impl.releaseFD();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/*     */     this.temp[0] = (byte)paramInt;
/*     */     socketWrite(this.temp, 0, 1);
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte) throws IOException {
/*     */     socketWrite(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     socketWrite(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   protected void finalize() {}
/*     */   
/*     */   private native void socketWrite0(FileDescriptor paramFileDescriptor, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/SocketOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */