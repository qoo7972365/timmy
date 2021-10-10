/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import sun.net.ConnectionResetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SocketInputStream
/*     */   extends FileInputStream
/*     */ {
/*     */   private boolean eof;
/*     */   
/*     */   static {
/*  46 */     init();
/*     */   }
/*     */ 
/*     */   
/*  50 */   private AbstractPlainSocketImpl impl = null;
/*     */   private byte[] temp;
/*  52 */   private Socket socket = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closing;
/*     */ 
/*     */ 
/*     */   
/*     */   SocketInputStream(AbstractPlainSocketImpl paramAbstractPlainSocketImpl) throws IOException {
/*  61 */     super(paramAbstractPlainSocketImpl.getFileDescriptor());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     this.closing = false; this.impl = paramAbstractPlainSocketImpl; this.socket = paramAbstractPlainSocketImpl.getSocket();
/*     */   }
/*     */   public final FileChannel getChannel() { return null; }
/* 268 */   private int socketRead(FileDescriptor paramFileDescriptor, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws IOException { return socketRead0(paramFileDescriptor, paramArrayOfbyte, paramInt1, paramInt2, paramInt3); } public int read(byte[] paramArrayOfbyte) throws IOException { return read(paramArrayOfbyte, 0, paramArrayOfbyte.length); } public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException { return read(paramArrayOfbyte, paramInt1, paramInt2, this.impl.getTimeout()); } public void close() throws IOException { if (this.closing)
/*     */       return; 
/* 270 */     this.closing = true;
/* 271 */     if (this.socket != null) {
/* 272 */       if (!this.socket.isClosed())
/* 273 */         this.socket.close(); 
/*     */     } else {
/* 275 */       this.impl.close();
/* 276 */     }  this.closing = false; }
/*     */   int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws IOException { if (this.eof) return -1;  if (this.impl.isConnectionReset()) throw new SocketException("Connection reset");  if (paramInt2 <= 0 || paramInt1 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1) { if (paramInt2 == 0) return 0;  throw new ArrayIndexOutOfBoundsException("length == " + paramInt2 + " off == " + paramInt1 + " buffer length == " + paramArrayOfbyte.length); }  boolean bool = false; FileDescriptor fileDescriptor = this.impl.acquireFD(); try { int i = socketRead(fileDescriptor, paramArrayOfbyte, paramInt1, paramInt2, paramInt3); if (i > 0) return i;  } catch (ConnectionResetException connectionResetException) { bool = true; } finally { this.impl.releaseFD(); }  if (bool) { this.impl.setConnectionResetPending(); this.impl.acquireFD(); try { int i = socketRead(fileDescriptor, paramArrayOfbyte, paramInt1, paramInt2, paramInt3); if (i > 0) return i;  } catch (ConnectionResetException connectionResetException) {  } finally { this.impl.releaseFD(); }  }  if (this.impl.isClosedOrPending()) throw new SocketException("Socket closed");  if (this.impl.isConnectionResetPending()) this.impl.setConnectionReset();  if (this.impl.isConnectionReset()) throw new SocketException("Connection reset");  this.eof = true; return -1; }
/*     */   public int read() throws IOException { if (this.eof) return -1;  this.temp = new byte[1]; int i = read(this.temp, 0, 1); if (i <= 0) return -1;  return this.temp[0] & 0xFF; }
/*     */   public long skip(long paramLong) throws IOException { if (paramLong <= 0L) return 0L;  long l = paramLong; int i = (int)Math.min(1024L, l); byte[] arrayOfByte = new byte[i]; while (l > 0L) { int j = read(arrayOfByte, 0, (int)Math.min(i, l)); if (j < 0) break;  l -= j; }  return paramLong - l; }
/* 280 */   public int available() throws IOException { return this.impl.available(); } void setEOF(boolean paramBoolean) { this.eof = paramBoolean; }
/*     */ 
/*     */   
/*     */   protected void finalize() {}
/*     */   
/*     */   private native int socketRead0(FileDescriptor paramFileDescriptor, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/SocketInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */