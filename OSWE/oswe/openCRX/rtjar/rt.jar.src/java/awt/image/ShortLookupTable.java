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
/*     */ public class ShortLookupTable
/*     */   extends LookupTable
/*     */ {
/*     */   short[][] data;
/*     */   
/*     */   public ShortLookupTable(int paramInt, short[][] paramArrayOfshort) {
/*  64 */     super(paramInt, paramArrayOfshort.length);
/*  65 */     this.numComponents = paramArrayOfshort.length;
/*  66 */     this.numEntries = (paramArrayOfshort[0]).length;
/*  67 */     this.data = new short[this.numComponents][];
/*     */     
/*  69 */     for (byte b = 0; b < this.numComponents; b++) {
/*  70 */       this.data[b] = paramArrayOfshort[b];
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
/*     */   public ShortLookupTable(int paramInt, short[] paramArrayOfshort) {
/*  85 */     super(paramInt, paramArrayOfshort.length);
/*  86 */     this.numComponents = 1;
/*  87 */     this.numEntries = paramArrayOfshort.length;
/*  88 */     this.data = new short[1][];
/*  89 */     this.data[0] = paramArrayOfshort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final short[][] getTable() {
/*  99 */     return this.data;
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
/* 122 */     if (paramArrayOfint2 == null)
/*     */     {
/* 124 */       paramArrayOfint2 = new int[paramArrayOfint1.length];
/*     */     }
/*     */     
/* 127 */     if (this.numComponents == 1) {
/*     */       
/* 129 */       for (byte b = 0; b < paramArrayOfint1.length; b++) {
/* 130 */         int i = (paramArrayOfint1[b] & 0xFFFF) - this.offset;
/* 131 */         if (i < 0) {
/* 132 */           throw new ArrayIndexOutOfBoundsException("src[" + b + "]-offset is less than zero");
/*     */         }
/*     */ 
/*     */         
/* 136 */         paramArrayOfint2[b] = this.data[0][i];
/*     */       } 
/*     */     } else {
/*     */       
/* 140 */       for (byte b = 0; b < paramArrayOfint1.length; b++) {
/* 141 */         int i = (paramArrayOfint1[b] & 0xFFFF) - this.offset;
/* 142 */         if (i < 0) {
/* 143 */           throw new ArrayIndexOutOfBoundsException("src[" + b + "]-offset is less than zero");
/*     */         }
/*     */ 
/*     */         
/* 147 */         paramArrayOfint2[b] = this.data[b][i];
/*     */       } 
/*     */     } 
/* 150 */     return paramArrayOfint2;
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
/*     */   public short[] lookupPixel(short[] paramArrayOfshort1, short[] paramArrayOfshort2) {
/* 173 */     if (paramArrayOfshort2 == null)
/*     */     {
/* 175 */       paramArrayOfshort2 = new short[paramArrayOfshort1.length];
/*     */     }
/*     */     
/* 178 */     if (this.numComponents == 1) {
/*     */       
/* 180 */       for (byte b = 0; b < paramArrayOfshort1.length; b++) {
/* 181 */         int i = (paramArrayOfshort1[b] & 0xFFFF) - this.offset;
/* 182 */         if (i < 0) {
/* 183 */           throw new ArrayIndexOutOfBoundsException("src[" + b + "]-offset is less than zero");
/*     */         }
/*     */ 
/*     */         
/* 187 */         paramArrayOfshort2[b] = this.data[0][i];
/*     */       } 
/*     */     } else {
/*     */       
/* 191 */       for (byte b = 0; b < paramArrayOfshort1.length; b++) {
/* 192 */         int i = (paramArrayOfshort1[b] & 0xFFFF) - this.offset;
/* 193 */         if (i < 0) {
/* 194 */           throw new ArrayIndexOutOfBoundsException("src[" + b + "]-offset is less than zero");
/*     */         }
/*     */ 
/*     */         
/* 198 */         paramArrayOfshort2[b] = this.data[b][i];
/*     */       } 
/*     */     } 
/* 201 */     return paramArrayOfshort2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/ShortLookupTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */