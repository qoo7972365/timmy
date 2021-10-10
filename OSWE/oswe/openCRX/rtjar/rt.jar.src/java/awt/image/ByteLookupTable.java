/*     */ package java.awt.image;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteLookupTable
/*     */   extends LookupTable
/*     */ {
/*     */   byte[][] data;
/*     */   
/*     */   public ByteLookupTable(int paramInt, byte[][] paramArrayOfbyte) {
/*  67 */     super(paramInt, paramArrayOfbyte.length);
/*  68 */     this.numComponents = paramArrayOfbyte.length;
/*  69 */     this.numEntries = (paramArrayOfbyte[0]).length;
/*  70 */     this.data = new byte[this.numComponents][];
/*     */     
/*  72 */     for (byte b = 0; b < this.numComponents; b++) {
/*  73 */       this.data[b] = paramArrayOfbyte[b];
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
/*     */   public ByteLookupTable(int paramInt, byte[] paramArrayOfbyte) {
/*  91 */     super(paramInt, paramArrayOfbyte.length);
/*  92 */     this.numComponents = 1;
/*  93 */     this.numEntries = paramArrayOfbyte.length;
/*  94 */     this.data = new byte[1][];
/*  95 */     this.data[0] = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[][] getTable() {
/* 105 */     return this.data;
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
/*     */   public int[] lookupPixel(int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 128 */     if (paramArrayOfint2 == null)
/*     */     {
/* 130 */       paramArrayOfint2 = new int[paramArrayOfint1.length];
/*     */     }
/*     */     
/* 133 */     if (this.numComponents == 1) {
/*     */       
/* 135 */       for (byte b = 0; b < paramArrayOfint1.length; b++) {
/* 136 */         int i = paramArrayOfint1[b] - this.offset;
/* 137 */         if (i < 0) {
/* 138 */           throw new ArrayIndexOutOfBoundsException("src[" + b + "]-offset is less than zero");
/*     */         }
/*     */ 
/*     */         
/* 142 */         paramArrayOfint2[b] = this.data[0][i];
/*     */       } 
/*     */     } else {
/*     */       
/* 146 */       for (byte b = 0; b < paramArrayOfint1.length; b++) {
/* 147 */         int i = paramArrayOfint1[b] - this.offset;
/* 148 */         if (i < 0) {
/* 149 */           throw new ArrayIndexOutOfBoundsException("src[" + b + "]-offset is less than zero");
/*     */         }
/*     */ 
/*     */         
/* 153 */         paramArrayOfint2[b] = this.data[b][i];
/*     */       } 
/*     */     } 
/* 156 */     return paramArrayOfint2;
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
/*     */   public byte[] lookupPixel(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 179 */     if (paramArrayOfbyte2 == null)
/*     */     {
/* 181 */       paramArrayOfbyte2 = new byte[paramArrayOfbyte1.length];
/*     */     }
/*     */     
/* 184 */     if (this.numComponents == 1) {
/*     */       
/* 186 */       for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 187 */         int i = (paramArrayOfbyte1[b] & 0xFF) - this.offset;
/* 188 */         if (i < 0) {
/* 189 */           throw new ArrayIndexOutOfBoundsException("src[" + b + "]-offset is less than zero");
/*     */         }
/*     */ 
/*     */         
/* 193 */         paramArrayOfbyte2[b] = this.data[0][i];
/*     */       } 
/*     */     } else {
/*     */       
/* 197 */       for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 198 */         int i = (paramArrayOfbyte1[b] & 0xFF) - this.offset;
/* 199 */         if (i < 0) {
/* 200 */           throw new ArrayIndexOutOfBoundsException("src[" + b + "]-offset is less than zero");
/*     */         }
/*     */ 
/*     */         
/* 204 */         paramArrayOfbyte2[b] = this.data[b][i];
/*     */       } 
/*     */     } 
/* 207 */     return paramArrayOfbyte2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/ByteLookupTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */