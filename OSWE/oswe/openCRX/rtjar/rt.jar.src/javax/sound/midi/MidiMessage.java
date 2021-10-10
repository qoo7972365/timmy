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
/*     */ public abstract class MidiMessage
/*     */   implements Cloneable
/*     */ {
/*     */   protected byte[] data;
/*  93 */   protected int length = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MidiMessage(byte[] paramArrayOfbyte) {
/* 109 */     this.data = paramArrayOfbyte;
/* 110 */     if (paramArrayOfbyte != null) {
/* 111 */       this.length = paramArrayOfbyte.length;
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
/*     */   protected void setMessage(byte[] paramArrayOfbyte, int paramInt) throws InvalidMidiDataException {
/* 127 */     if (paramInt < 0 || (paramInt > 0 && paramInt > paramArrayOfbyte.length)) {
/* 128 */       throw new IndexOutOfBoundsException("length out of bounds: " + paramInt);
/*     */     }
/* 130 */     this.length = paramInt;
/*     */     
/* 132 */     if (this.data == null || this.data.length < this.length) {
/* 133 */       this.data = new byte[this.length];
/*     */     }
/* 135 */     System.arraycopy(paramArrayOfbyte, 0, this.data, 0, paramInt);
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
/*     */   public byte[] getMessage() {
/* 150 */     byte[] arrayOfByte = new byte[this.length];
/* 151 */     System.arraycopy(this.data, 0, arrayOfByte, 0, this.length);
/* 152 */     return arrayOfByte;
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
/*     */   public int getStatus() {
/* 165 */     if (this.length > 0) {
/* 166 */       return this.data[0] & 0xFF;
/*     */     }
/* 168 */     return 0;
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
/*     */   public int getLength() {
/* 182 */     return this.length;
/*     */   }
/*     */   
/*     */   public abstract Object clone();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/MidiMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */