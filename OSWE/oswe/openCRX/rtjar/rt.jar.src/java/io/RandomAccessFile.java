/*      */ package java.io;
/*      */ 
/*      */ import java.nio.channels.FileChannel;
/*      */ import sun.nio.ch.FileChannelImpl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RandomAccessFile
/*      */   implements DataOutput, DataInput, Closeable
/*      */ {
/*      */   private FileDescriptor fd;
/*   62 */   private FileChannel channel = null;
/*      */ 
/*      */   
/*      */   private boolean rw;
/*      */ 
/*      */   
/*      */   private final String path;
/*      */ 
/*      */   
/*   71 */   private Object closeLock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile boolean closed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int O_RDONLY = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int O_RDWR = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int O_SYNC = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int O_DSYNC = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RandomAccessFile(String paramString1, String paramString2) throws FileNotFoundException {
/*  124 */     this((paramString1 != null) ? new File(paramString1) : null, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RandomAccessFile(File paramFile, String paramString) throws FileNotFoundException {
/*  206 */     String str = (paramFile != null) ? paramFile.getPath() : null;
/*  207 */     int i = -1;
/*  208 */     if (paramString.equals("r")) {
/*  209 */       i = 1;
/*  210 */     } else if (paramString.startsWith("rw")) {
/*  211 */       i = 2;
/*  212 */       this.rw = true;
/*  213 */       if (paramString.length() > 2)
/*  214 */         if (paramString.equals("rws")) {
/*  215 */           i |= 0x4;
/*  216 */         } else if (paramString.equals("rwd")) {
/*  217 */           i |= 0x8;
/*      */         } else {
/*  219 */           i = -1;
/*      */         }  
/*      */     } 
/*  222 */     if (i < 0) {
/*  223 */       throw new IllegalArgumentException("Illegal mode \"" + paramString + "\" must be one of \"r\", \"rw\", \"rws\", or \"rwd\"");
/*      */     }
/*      */ 
/*      */     
/*  227 */     SecurityManager securityManager = System.getSecurityManager();
/*  228 */     if (securityManager != null) {
/*  229 */       securityManager.checkRead(str);
/*  230 */       if (this.rw) {
/*  231 */         securityManager.checkWrite(str);
/*      */       }
/*      */     } 
/*  234 */     if (str == null) {
/*  235 */       throw new NullPointerException();
/*      */     }
/*  237 */     if (paramFile.isInvalid()) {
/*  238 */       throw new FileNotFoundException("Invalid file path");
/*      */     }
/*  240 */     this.fd = new FileDescriptor();
/*  241 */     this.fd.attach(this);
/*  242 */     this.path = str;
/*  243 */     open(str, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final FileDescriptor getFD() throws IOException {
/*  255 */     if (this.fd != null) {
/*  256 */       return this.fd;
/*      */     }
/*  258 */     throw new IOException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final FileChannel getChannel() {
/*  280 */     synchronized (this) {
/*  281 */       if (this.channel == null) {
/*  282 */         this.channel = FileChannelImpl.open(this.fd, this.path, true, this.rw, this);
/*      */       }
/*  284 */       return this.channel;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void open(String paramString, int paramInt) throws FileNotFoundException {
/*  316 */     open0(paramString, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int read() throws IOException {
/*  337 */     return read0();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  377 */     return readBytes(paramArrayOfbyte, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int read(byte[] paramArrayOfbyte) throws IOException {
/*  400 */     return readBytes(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void readFully(byte[] paramArrayOfbyte) throws IOException {
/*  416 */     readFully(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  434 */     int i = 0;
/*      */     do {
/*  436 */       int j = read(paramArrayOfbyte, paramInt1 + i, paramInt2 - i);
/*  437 */       if (j < 0)
/*  438 */         throw new EOFException(); 
/*  439 */       i += j;
/*  440 */     } while (i < paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int skipBytes(int paramInt) throws IOException {
/*  464 */     if (paramInt <= 0) {
/*  465 */       return 0;
/*      */     }
/*  467 */     long l1 = getFilePointer();
/*  468 */     long l2 = length();
/*  469 */     long l3 = l1 + paramInt;
/*  470 */     if (l3 > l2) {
/*  471 */       l3 = l2;
/*      */     }
/*  473 */     seek(l3);
/*      */ 
/*      */     
/*  476 */     return (int)(l3 - l1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(int paramInt) throws IOException {
/*  489 */     write0(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(byte[] paramArrayOfbyte) throws IOException {
/*  512 */     writeBytes(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  525 */     writeBytes(paramArrayOfbyte, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void seek(long paramLong) throws IOException {
/*  554 */     if (paramLong < 0L) {
/*  555 */       throw new IOException("Negative seek offset");
/*      */     }
/*  557 */     seek0(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*  607 */     synchronized (this.closeLock) {
/*  608 */       if (this.closed) {
/*      */         return;
/*      */       }
/*  611 */       this.closed = true;
/*      */     } 
/*  613 */     if (this.channel != null) {
/*  614 */       this.channel.close();
/*      */     }
/*      */     
/*  617 */     this.fd.closeAll(new Closeable() {
/*      */           public void close() throws IOException {
/*  619 */             RandomAccessFile.this.close0();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean readBoolean() throws IOException {
/*  642 */     int i = read();
/*  643 */     if (i < 0)
/*  644 */       throw new EOFException(); 
/*  645 */     return (i != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte readByte() throws IOException {
/*  667 */     int i = read();
/*  668 */     if (i < 0)
/*  669 */       throw new EOFException(); 
/*  670 */     return (byte)i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int readUnsignedByte() throws IOException {
/*  687 */     int i = read();
/*  688 */     if (i < 0)
/*  689 */       throw new EOFException(); 
/*  690 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final short readShort() throws IOException {
/*  714 */     int i = read();
/*  715 */     int j = read();
/*  716 */     if ((i | j) < 0)
/*  717 */       throw new EOFException(); 
/*  718 */     return (short)((i << 8) + (j << 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int readUnsignedShort() throws IOException {
/*  742 */     int i = read();
/*  743 */     int j = read();
/*  744 */     if ((i | j) < 0)
/*  745 */       throw new EOFException(); 
/*  746 */     return (i << 8) + (j << 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final char readChar() throws IOException {
/*  770 */     int i = read();
/*  771 */     int j = read();
/*  772 */     if ((i | j) < 0)
/*  773 */       throw new EOFException(); 
/*  774 */     return (char)((i << 8) + (j << 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int readInt() throws IOException {
/*  798 */     int i = read();
/*  799 */     int j = read();
/*  800 */     int k = read();
/*  801 */     int m = read();
/*  802 */     if ((i | j | k | m) < 0)
/*  803 */       throw new EOFException(); 
/*  804 */     return (i << 24) + (j << 16) + (k << 8) + (m << 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long readLong() throws IOException {
/*  836 */     return (readInt() << 32L) + (readInt() & 0xFFFFFFFFL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float readFloat() throws IOException {
/*  859 */     return Float.intBitsToFloat(readInt());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double readDouble() throws IOException {
/*  882 */     return Double.longBitsToDouble(readLong());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String readLine() throws IOException {
/*  910 */     StringBuffer stringBuffer = new StringBuffer();
/*  911 */     int i = -1;
/*  912 */     boolean bool = false;
/*      */     
/*  914 */     while (!bool) {
/*  915 */       long l; switch (i = read()) {
/*      */         case -1:
/*      */         case 10:
/*  918 */           bool = true;
/*      */           continue;
/*      */         case 13:
/*  921 */           bool = true;
/*  922 */           l = getFilePointer();
/*  923 */           if (read() != 10) {
/*  924 */             seek(l);
/*      */           }
/*      */           continue;
/*      */       } 
/*  928 */       stringBuffer.append((char)i);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  933 */     if (i == -1 && stringBuffer.length() == 0) {
/*  934 */       return null;
/*      */     }
/*  936 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String readUTF() throws IOException {
/*  965 */     return DataInputStream.readUTF(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeBoolean(boolean paramBoolean) throws IOException {
/*  979 */     write(paramBoolean ? 1 : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeByte(int paramInt) throws IOException {
/*  991 */     write(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeShort(int paramInt) throws IOException {
/* 1003 */     write(paramInt >>> 8 & 0xFF);
/* 1004 */     write(paramInt >>> 0 & 0xFF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeChar(int paramInt) throws IOException {
/* 1017 */     write(paramInt >>> 8 & 0xFF);
/* 1018 */     write(paramInt >>> 0 & 0xFF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeInt(int paramInt) throws IOException {
/* 1030 */     write(paramInt >>> 24 & 0xFF);
/* 1031 */     write(paramInt >>> 16 & 0xFF);
/* 1032 */     write(paramInt >>> 8 & 0xFF);
/* 1033 */     write(paramInt >>> 0 & 0xFF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeLong(long paramLong) throws IOException {
/* 1045 */     write((int)(paramLong >>> 56L) & 0xFF);
/* 1046 */     write((int)(paramLong >>> 48L) & 0xFF);
/* 1047 */     write((int)(paramLong >>> 40L) & 0xFF);
/* 1048 */     write((int)(paramLong >>> 32L) & 0xFF);
/* 1049 */     write((int)(paramLong >>> 24L) & 0xFF);
/* 1050 */     write((int)(paramLong >>> 16L) & 0xFF);
/* 1051 */     write((int)(paramLong >>> 8L) & 0xFF);
/* 1052 */     write((int)(paramLong >>> 0L) & 0xFF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeFloat(float paramFloat) throws IOException {
/* 1068 */     writeInt(Float.floatToIntBits(paramFloat));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeDouble(double paramDouble) throws IOException {
/* 1083 */     writeLong(Double.doubleToLongBits(paramDouble));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeBytes(String paramString) throws IOException {
/* 1097 */     int i = paramString.length();
/* 1098 */     byte[] arrayOfByte = new byte[i];
/* 1099 */     paramString.getBytes(0, i, arrayOfByte, 0);
/* 1100 */     writeBytes(arrayOfByte, 0, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeChars(String paramString) throws IOException {
/* 1114 */     int i = paramString.length();
/* 1115 */     int j = 2 * i;
/* 1116 */     byte[] arrayOfByte = new byte[j];
/* 1117 */     char[] arrayOfChar = new char[i];
/* 1118 */     paramString.getChars(0, i, arrayOfChar, 0);
/* 1119 */     for (byte b1 = 0, b2 = 0; b1 < i; b1++) {
/* 1120 */       arrayOfByte[b2++] = (byte)(arrayOfChar[b1] >>> 8);
/* 1121 */       arrayOfByte[b2++] = (byte)(arrayOfChar[b1] >>> 0);
/*      */     } 
/* 1123 */     writeBytes(arrayOfByte, 0, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeUTF(String paramString) throws IOException {
/* 1143 */     DataOutputStream.writeUTF(paramString, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/* 1151 */     initIDs();
/*      */   }
/*      */   
/*      */   private native void open0(String paramString, int paramInt) throws FileNotFoundException;
/*      */   
/*      */   private native int read0() throws IOException;
/*      */   
/*      */   private native int readBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*      */   
/*      */   private native void write0(int paramInt) throws IOException;
/*      */   
/*      */   private native void writeBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*      */   
/*      */   public native long getFilePointer() throws IOException;
/*      */   
/*      */   private native void seek0(long paramLong) throws IOException;
/*      */   
/*      */   public native long length() throws IOException;
/*      */   
/*      */   public native void setLength(long paramLong) throws IOException;
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   private native void close0() throws IOException;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/RandomAccessFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */