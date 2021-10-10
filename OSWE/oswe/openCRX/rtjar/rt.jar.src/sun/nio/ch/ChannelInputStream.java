/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.SeekableByteChannel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChannelInputStream
/*     */   extends InputStream
/*     */ {
/*     */   protected final ReadableByteChannel ch;
/*     */   
/*     */   public static int read(ReadableByteChannel paramReadableByteChannel, ByteBuffer paramByteBuffer, boolean paramBoolean) throws IOException {
/*  51 */     if (paramReadableByteChannel instanceof SelectableChannel) {
/*  52 */       SelectableChannel selectableChannel = (SelectableChannel)paramReadableByteChannel;
/*  53 */       synchronized (selectableChannel.blockingLock()) {
/*  54 */         boolean bool = selectableChannel.isBlocking();
/*  55 */         if (!bool)
/*  56 */           throw new IllegalBlockingModeException(); 
/*  57 */         if (bool != paramBoolean)
/*  58 */           selectableChannel.configureBlocking(paramBoolean); 
/*  59 */         int i = paramReadableByteChannel.read(paramByteBuffer);
/*  60 */         if (bool != paramBoolean)
/*  61 */           selectableChannel.configureBlocking(bool); 
/*  62 */         return i;
/*     */       } 
/*     */     } 
/*  65 */     return paramReadableByteChannel.read(paramByteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  70 */   private ByteBuffer bb = null;
/*  71 */   private byte[] bs = null;
/*  72 */   private byte[] b1 = null;
/*     */   
/*     */   public ChannelInputStream(ReadableByteChannel paramReadableByteChannel) {
/*  75 */     this.ch = paramReadableByteChannel;
/*     */   }
/*     */   
/*     */   public synchronized int read() throws IOException {
/*  79 */     if (this.b1 == null)
/*  80 */       this.b1 = new byte[1]; 
/*  81 */     int i = read(this.b1);
/*  82 */     if (i == 1)
/*  83 */       return this.b1[0] & 0xFF; 
/*  84 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  90 */     if (paramInt1 < 0 || paramInt1 > paramArrayOfbyte.length || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfbyte.length || paramInt1 + paramInt2 < 0)
/*     */     {
/*  92 */       throw new IndexOutOfBoundsException(); } 
/*  93 */     if (paramInt2 == 0) {
/*  94 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  98 */     ByteBuffer byteBuffer = (this.bs == paramArrayOfbyte) ? this.bb : ByteBuffer.wrap(paramArrayOfbyte);
/*  99 */     byteBuffer.limit(Math.min(paramInt1 + paramInt2, byteBuffer.capacity()));
/* 100 */     byteBuffer.position(paramInt1);
/* 101 */     this.bb = byteBuffer;
/* 102 */     this.bs = paramArrayOfbyte;
/* 103 */     return read(byteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int read(ByteBuffer paramByteBuffer) throws IOException {
/* 109 */     return read(this.ch, paramByteBuffer, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 114 */     if (this.ch instanceof SeekableByteChannel) {
/* 115 */       SeekableByteChannel seekableByteChannel = (SeekableByteChannel)this.ch;
/* 116 */       long l = Math.max(0L, seekableByteChannel.size() - seekableByteChannel.position());
/* 117 */       return (l > 2147483647L) ? Integer.MAX_VALUE : (int)l;
/*     */     } 
/* 119 */     return 0;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 123 */     this.ch.close();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/ChannelInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */