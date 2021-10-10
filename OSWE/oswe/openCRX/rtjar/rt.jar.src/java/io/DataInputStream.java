/*     */ package java.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataInputStream
/*     */   extends FilterInputStream
/*     */   implements DataInput
/*     */ {
/*     */   private byte[] bytearr;
/*     */   private char[] chararr;
/*     */   private byte[] readBuffer;
/*     */   private char[] lineBuffer;
/*     */   
/*     */   public final int read(byte[] paramArrayOfbyte) throws IOException {
/*     */     return this.in.read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   public final int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     return this.in.read(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public final void readFully(byte[] paramArrayOfbyte) throws IOException {
/*     */     readFully(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   public DataInputStream(InputStream paramInputStream) {
/*  52 */     super(paramInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.bytearr = new byte[80];
/*  59 */     this.chararr = new char[80];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     this.readBuffer = new byte[8];
/*     */   }
/*     */ 
/*     */   
/*     */   public final void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     if (paramInt2 < 0) {
/*     */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     int i = 0;
/*     */     while (i < paramInt2) {
/*     */       int j = this.in.read(paramArrayOfbyte, paramInt1 + i, paramInt2 - i);
/*     */       if (j < 0) {
/*     */         throw new EOFException();
/*     */       }
/*     */       i += j;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final long readLong() throws IOException {
/* 416 */     readFully(this.readBuffer, 0, 8);
/* 417 */     return (this.readBuffer[0] << 56L) + ((this.readBuffer[1] & 0xFF) << 48L) + ((this.readBuffer[2] & 0xFF) << 40L) + ((this.readBuffer[3] & 0xFF) << 32L) + ((this.readBuffer[4] & 0xFF) << 24L) + ((this.readBuffer[5] & 0xFF) << 16) + ((this.readBuffer[6] & 0xFF) << 8) + ((this.readBuffer[7] & 0xFF) << 0);
/*     */   } public final int skipBytes(int paramInt) throws IOException {
/*     */     int i = 0;
/*     */     int j = 0;
/*     */     while (i < paramInt && (j = (int)this.in.skip((paramInt - i))) > 0)
/*     */       i += j; 
/*     */     return i;
/*     */   } public final boolean readBoolean() throws IOException {
/*     */     int i = this.in.read();
/*     */     if (i < 0)
/*     */       throw new EOFException(); 
/*     */     return (i != 0);
/*     */   } public final byte readByte() throws IOException {
/*     */     int i = this.in.read();
/*     */     if (i < 0)
/*     */       throw new EOFException(); 
/*     */     return (byte)i;
/*     */   } public final int readUnsignedByte() throws IOException {
/*     */     int i = this.in.read();
/*     */     if (i < 0)
/*     */       throw new EOFException(); 
/*     */     return i;
/*     */   } public final short readShort() throws IOException {
/*     */     int i = this.in.read();
/*     */     int j = this.in.read();
/*     */     if ((i | j) < 0)
/*     */       throw new EOFException(); 
/*     */     return (short)((i << 8) + (j << 0));
/*     */   } public final float readFloat() throws IOException {
/* 446 */     return Float.intBitsToFloat(readInt());
/*     */   } public final int readUnsignedShort() throws IOException {
/*     */     int i = this.in.read();
/*     */     int j = this.in.read();
/*     */     if ((i | j) < 0)
/*     */       throw new EOFException(); 
/*     */     return (i << 8) + (j << 0);
/*     */   } public final char readChar() throws IOException {
/*     */     int i = this.in.read();
/*     */     int j = this.in.read();
/*     */     if ((i | j) < 0)
/*     */       throw new EOFException(); 
/*     */     return (char)((i << 8) + (j << 0));
/*     */   } public final int readInt() throws IOException {
/*     */     int i = this.in.read();
/*     */     int j = this.in.read();
/*     */     int k = this.in.read();
/*     */     int m = this.in.read();
/*     */     if ((i | j | k | m) < 0)
/*     */       throw new EOFException(); 
/*     */     return (i << 24) + (j << 16) + (k << 8) + (m << 0);
/*     */   } public final double readDouble() throws IOException {
/* 468 */     return Double.longBitsToDouble(readLong());
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final String readLine() throws IOException {
/*     */     int j;
/* 502 */     char[] arrayOfChar = this.lineBuffer;
/*     */     
/* 504 */     if (arrayOfChar == null) {
/* 505 */       arrayOfChar = this.lineBuffer = new char[128];
/*     */     }
/*     */     
/* 508 */     int i = arrayOfChar.length;
/* 509 */     byte b = 0;
/*     */     
/*     */     while (true) {
/*     */       int k;
/* 513 */       switch (j = this.in.read()) {
/*     */         case -1:
/*     */         case 10:
/*     */           break;
/*     */         
/*     */         case 13:
/* 519 */           k = this.in.read();
/* 520 */           if (k != 10 && k != -1) {
/* 521 */             if (!(this.in instanceof PushbackInputStream)) {
/* 522 */               this.in = new PushbackInputStream(this.in);
/*     */             }
/* 524 */             ((PushbackInputStream)this.in).unread(k);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 529 */       if (--i < 0) {
/* 530 */         arrayOfChar = new char[b + 128];
/* 531 */         i = arrayOfChar.length - b - 1;
/* 532 */         System.arraycopy(this.lineBuffer, 0, arrayOfChar, 0, b);
/* 533 */         this.lineBuffer = arrayOfChar;
/*     */       } 
/* 535 */       arrayOfChar[b++] = (char)j;
/*     */     } 
/*     */ 
/*     */     
/* 539 */     if (j == -1 && b == 0) {
/* 540 */       return null;
/*     */     }
/* 542 */     return String.copyValueOf(arrayOfChar, 0, b);
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
/*     */   public final String readUTF() throws IOException {
/* 564 */     return readUTF(this);
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
/*     */   public static final String readUTF(DataInput paramDataInput) throws IOException {
/* 589 */     int i = paramDataInput.readUnsignedShort();
/* 590 */     byte[] arrayOfByte = null;
/* 591 */     char[] arrayOfChar = null;
/* 592 */     if (paramDataInput instanceof DataInputStream) {
/* 593 */       DataInputStream dataInputStream = (DataInputStream)paramDataInput;
/* 594 */       if (dataInputStream.bytearr.length < i) {
/* 595 */         dataInputStream.bytearr = new byte[i * 2];
/* 596 */         dataInputStream.chararr = new char[i * 2];
/*     */       } 
/* 598 */       arrayOfChar = dataInputStream.chararr;
/* 599 */       arrayOfByte = dataInputStream.bytearr;
/*     */     } else {
/* 601 */       arrayOfByte = new byte[i];
/* 602 */       arrayOfChar = new char[i];
/*     */     } 
/*     */ 
/*     */     
/* 606 */     byte b1 = 0;
/* 607 */     byte b2 = 0;
/*     */     
/* 609 */     paramDataInput.readFully(arrayOfByte, 0, i);
/*     */     
/* 611 */     while (b1 < i) {
/* 612 */       int j = arrayOfByte[b1] & 0xFF;
/* 613 */       if (j > 127)
/* 614 */         break;  b1++;
/* 615 */       arrayOfChar[b2++] = (char)j;
/*     */     } 
/*     */     
/* 618 */     while (b1 < i) {
/* 619 */       byte b3, b4; int j = arrayOfByte[b1] & 0xFF;
/* 620 */       switch (j >> 4) { case 0: case 1: case 2: case 3: case 4: case 5:
/*     */         case 6:
/*     */         case 7:
/* 623 */           b1++;
/* 624 */           arrayOfChar[b2++] = (char)j;
/*     */           continue;
/*     */         case 12:
/*     */         case 13:
/* 628 */           b1 += 2;
/* 629 */           if (b1 > i) {
/* 630 */             throw new UTFDataFormatException("malformed input: partial character at end");
/*     */           }
/* 632 */           b3 = arrayOfByte[b1 - 1];
/* 633 */           if ((b3 & 0xC0) != 128) {
/* 634 */             throw new UTFDataFormatException("malformed input around byte " + b1);
/*     */           }
/* 636 */           arrayOfChar[b2++] = (char)((j & 0x1F) << 6 | b3 & 0x3F);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 14:
/* 641 */           b1 += 3;
/* 642 */           if (b1 > i) {
/* 643 */             throw new UTFDataFormatException("malformed input: partial character at end");
/*     */           }
/* 645 */           b3 = arrayOfByte[b1 - 2];
/* 646 */           b4 = arrayOfByte[b1 - 1];
/* 647 */           if ((b3 & 0xC0) != 128 || (b4 & 0xC0) != 128) {
/* 648 */             throw new UTFDataFormatException("malformed input around byte " + (b1 - 1));
/*     */           }
/* 650 */           arrayOfChar[b2++] = (char)((j & 0xF) << 12 | (b3 & 0x3F) << 6 | (b4 & 0x3F) << 0);
/*     */           continue; }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 656 */       throw new UTFDataFormatException("malformed input around byte " + b1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 661 */     return new String(arrayOfChar, 0, b2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/DataInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */