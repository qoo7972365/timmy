/*     */ package javax.sound.midi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetaMessage
/*     */   extends MidiMessage
/*     */ {
/*     */   public static final int META = 255;
/*  79 */   private int dataLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long mask = 127L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaMessage() {
/*  90 */     this(new byte[] { -1, 0 });
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
/*     */   public MetaMessage(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws InvalidMidiDataException {
/* 112 */     super(null);
/* 113 */     setMessage(paramInt1, paramArrayOfbyte, paramInt2);
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
/*     */   protected MetaMessage(byte[] paramArrayOfbyte) {
/* 125 */     super(paramArrayOfbyte);
/*     */     
/* 127 */     if (paramArrayOfbyte.length >= 3) {
/* 128 */       this.dataLength = paramArrayOfbyte.length - 3;
/* 129 */       byte b = 2;
/* 130 */       while (b < paramArrayOfbyte.length && (paramArrayOfbyte[b] & 0x80) != 0) {
/* 131 */         this.dataLength--; b++;
/*     */       } 
/*     */     } 
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
/*     */   public void setMessage(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws InvalidMidiDataException {
/* 157 */     if (paramInt1 >= 128 || paramInt1 < 0) {
/* 158 */       throw new InvalidMidiDataException("Invalid meta event with type " + paramInt1);
/*     */     }
/* 160 */     if ((paramInt2 > 0 && paramInt2 > paramArrayOfbyte.length) || paramInt2 < 0) {
/* 161 */       throw new InvalidMidiDataException("length out of bounds: " + paramInt2);
/*     */     }
/*     */     
/* 164 */     this.length = 2 + getVarIntLength(paramInt2) + paramInt2;
/* 165 */     this.dataLength = paramInt2;
/* 166 */     this.data = new byte[this.length];
/* 167 */     this.data[0] = -1;
/* 168 */     this.data[1] = (byte)paramInt1;
/* 169 */     writeVarInt(this.data, 2, paramInt2);
/* 170 */     if (paramInt2 > 0) {
/* 171 */       System.arraycopy(paramArrayOfbyte, 0, this.data, this.length - this.dataLength, this.dataLength);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 181 */     if (this.length >= 2) {
/* 182 */       return this.data[1] & 0xFF;
/*     */     }
/* 184 */     return 0;
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
/*     */   public byte[] getData() {
/* 200 */     byte[] arrayOfByte = new byte[this.dataLength];
/* 201 */     System.arraycopy(this.data, this.length - this.dataLength, arrayOfByte, 0, this.dataLength);
/* 202 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 212 */     byte[] arrayOfByte = new byte[this.length];
/* 213 */     System.arraycopy(this.data, 0, arrayOfByte, 0, arrayOfByte.length);
/*     */     
/* 215 */     return new MetaMessage(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getVarIntLength(long paramLong) {
/* 222 */     byte b = 0;
/*     */     while (true) {
/* 224 */       paramLong >>= 7L;
/* 225 */       b++;
/* 226 */       if (paramLong <= 0L) {
/* 227 */         return b;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeVarInt(byte[] paramArrayOfbyte, int paramInt, long paramLong) {
/* 233 */     byte b = 63;
/*     */     
/* 235 */     for (; b > 0 && (paramLong & 127L << b) == 0L; b -= 7);
/*     */     
/* 237 */     while (b > 0) {
/* 238 */       paramArrayOfbyte[paramInt++] = (byte)(int)((paramLong & 127L << b) >> b | 0x80L);
/* 239 */       b -= 7;
/*     */     } 
/* 241 */     paramArrayOfbyte[paramInt] = (byte)(int)(paramLong & 0x7FL);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/MetaMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */