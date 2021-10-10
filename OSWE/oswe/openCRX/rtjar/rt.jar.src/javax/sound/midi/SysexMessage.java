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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SysexMessage
/*     */   extends MidiMessage
/*     */ {
/*     */   public static final int SYSTEM_EXCLUSIVE = 240;
/*     */   public static final int SPECIAL_SYSTEM_EXCLUSIVE = 247;
/*     */   
/*     */   public SysexMessage() {
/* 117 */     this(new byte[2]);
/*     */     
/* 119 */     this.data[0] = -16;
/* 120 */     this.data[1] = -9;
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
/*     */   public SysexMessage(byte[] paramArrayOfbyte, int paramInt) throws InvalidMidiDataException {
/* 143 */     super(null);
/* 144 */     setMessage(paramArrayOfbyte, paramInt);
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
/*     */   public SysexMessage(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws InvalidMidiDataException {
/* 167 */     super(null);
/* 168 */     setMessage(paramInt1, paramArrayOfbyte, paramInt2);
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
/*     */   protected SysexMessage(byte[] paramArrayOfbyte) {
/* 180 */     super(paramArrayOfbyte);
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
/*     */   public void setMessage(byte[] paramArrayOfbyte, int paramInt) throws InvalidMidiDataException {
/* 193 */     int i = paramArrayOfbyte[0] & 0xFF;
/* 194 */     if (i != 240 && i != 247) {
/* 195 */       throw new InvalidMidiDataException("Invalid status byte for sysex message: 0x" + Integer.toHexString(i));
/*     */     }
/* 197 */     super.setMessage(paramArrayOfbyte, paramInt);
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
/*     */   public void setMessage(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws InvalidMidiDataException {
/* 210 */     if (paramInt1 != 240 && paramInt1 != 247) {
/* 211 */       throw new InvalidMidiDataException("Invalid status byte for sysex message: 0x" + Integer.toHexString(paramInt1));
/*     */     }
/* 213 */     if (paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length) {
/* 214 */       throw new IndexOutOfBoundsException("length out of bounds: " + paramInt2);
/*     */     }
/* 216 */     this.length = paramInt2 + 1;
/*     */     
/* 218 */     if (this.data == null || this.data.length < this.length) {
/* 219 */       this.data = new byte[this.length];
/*     */     }
/*     */     
/* 222 */     this.data[0] = (byte)(paramInt1 & 0xFF);
/* 223 */     if (paramInt2 > 0) {
/* 224 */       System.arraycopy(paramArrayOfbyte, 0, this.data, 1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getData() {
/* 235 */     byte[] arrayOfByte = new byte[this.length - 1];
/* 236 */     System.arraycopy(this.data, 1, arrayOfByte, 0, this.length - 1);
/* 237 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 247 */     byte[] arrayOfByte = new byte[this.length];
/* 248 */     System.arraycopy(this.data, 0, arrayOfByte, 0, arrayOfByte.length);
/* 249 */     return new SysexMessage(arrayOfByte);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/SysexMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */