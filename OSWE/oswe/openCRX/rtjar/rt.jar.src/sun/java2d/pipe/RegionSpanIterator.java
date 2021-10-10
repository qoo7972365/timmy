/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionSpanIterator
/*     */   implements SpanIterator
/*     */ {
/*     */   RegionIterator ri;
/*     */   int lox;
/*     */   int loy;
/*     */   int hix;
/*     */   int hiy;
/*     */   int curloy;
/*     */   int curhiy;
/*     */   boolean done = false;
/*     */   boolean isrect;
/*     */   
/*     */   public RegionSpanIterator(Region paramRegion) {
/*  65 */     int[] arrayOfInt = new int[4];
/*     */     
/*  67 */     paramRegion.getBounds(arrayOfInt);
/*  68 */     this.lox = arrayOfInt[0];
/*  69 */     this.loy = arrayOfInt[1];
/*  70 */     this.hix = arrayOfInt[2];
/*  71 */     this.hiy = arrayOfInt[3];
/*  72 */     this.isrect = paramRegion.isRectangular();
/*     */     
/*  74 */     this.ri = paramRegion.getIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getPathBox(int[] paramArrayOfint) {
/*  81 */     paramArrayOfint[0] = this.lox;
/*  82 */     paramArrayOfint[1] = this.loy;
/*  83 */     paramArrayOfint[2] = this.hix;
/*  84 */     paramArrayOfint[3] = this.hiy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void intersectClipBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  92 */     if (paramInt1 > this.lox) {
/*  93 */       this.lox = paramInt1;
/*     */     }
/*  95 */     if (paramInt2 > this.loy) {
/*  96 */       this.loy = paramInt2;
/*     */     }
/*  98 */     if (paramInt3 < this.hix) {
/*  99 */       this.hix = paramInt3;
/*     */     }
/* 101 */     if (paramInt4 < this.hiy) {
/* 102 */       this.hiy = paramInt4;
/*     */     }
/* 104 */     this.done = (this.lox >= this.hix || this.loy >= this.hiy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextSpan(int[] paramArrayOfint) {
/*     */     int i, j;
/* 114 */     if (this.done) {
/* 115 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (this.isrect) {
/* 123 */       getPathBox(paramArrayOfint);
/* 124 */       this.done = true;
/* 125 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     int k = this.curloy;
/* 131 */     int m = this.curhiy;
/*     */     
/*     */     do {
/* 134 */       while (!this.ri.nextXBand(paramArrayOfint)) {
/* 135 */         if (!this.ri.nextYRange(paramArrayOfint)) {
/* 136 */           this.done = true;
/* 137 */           return false;
/*     */         } 
/*     */         
/* 140 */         k = paramArrayOfint[1];
/* 141 */         m = paramArrayOfint[3];
/* 142 */         if (k < this.loy) {
/* 143 */           k = this.loy;
/*     */         }
/* 145 */         if (m > this.hiy) {
/* 146 */           m = this.hiy;
/*     */         }
/*     */         
/* 149 */         if (k >= this.hiy) {
/* 150 */           this.done = true;
/* 151 */           return false;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 156 */       i = paramArrayOfint[0];
/* 157 */       j = paramArrayOfint[2];
/* 158 */       if (i < this.lox) {
/* 159 */         i = this.lox;
/*     */       }
/* 161 */       if (j <= this.hix)
/* 162 */         continue;  j = this.hix;
/*     */     
/*     */     }
/* 165 */     while (i >= j || k >= m);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     paramArrayOfint[0] = i;
/* 172 */     paramArrayOfint[1] = this.curloy = k;
/* 173 */     paramArrayOfint[2] = j;
/* 174 */     paramArrayOfint[3] = this.curhiy = m;
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipDownTo(int paramInt) {
/* 183 */     this.loy = paramInt;
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
/*     */   public long getNativeIterator() {
/* 198 */     return 0L;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/RegionSpanIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */