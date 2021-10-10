/*     */ package sun.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IntHashtable
/*     */ {
/*     */   private int defaultValue;
/*     */   private int primeIndex;
/*     */   private static final float HIGH_WATER_FACTOR = 0.4F;
/*     */   private int highWaterMark;
/*     */   private static final float LOW_WATER_FACTOR = 0.0F;
/*     */   private int lowWaterMark;
/*     */   private int count;
/*     */   private int[] values;
/*     */   private int[] keyList;
/*     */   private static final int EMPTY = -2147483648;
/*     */   private static final int DELETED = -2147483647;
/*     */   private static final int MAX_UNUSED = -2147483647;
/*     */   
/*     */   public IntHashtable() {
/* 144 */     this.defaultValue = 0; initialize(3); } public IntHashtable(int paramInt) { this.defaultValue = 0;
/*     */     initialize(leastGreaterPrimeIndex((int)(paramInt / 0.4F))); }
/*     */   
/*     */   public int size() {
/*     */     return this.count;
/*     */   } public boolean isEmpty() {
/*     */     return (this.count == 0);
/*     */   } public void put(int paramInt1, int paramInt2) {
/*     */     if (this.count > this.highWaterMark)
/*     */       rehash(); 
/*     */     int i = find(paramInt1);
/*     */     if (this.keyList[i] <= -2147483647) {
/*     */       this.keyList[i] = paramInt1;
/*     */       this.count++;
/*     */     } 
/*     */     this.values[i] = paramInt2;
/*     */   } public int get(int paramInt) {
/*     */     return this.values[find(paramInt)];
/*     */   } public void remove(int paramInt) {
/*     */     int i = find(paramInt);
/*     */     if (this.keyList[i] > -2147483647) {
/*     */       this.keyList[i] = -2147483647;
/*     */       this.values[i] = this.defaultValue;
/*     */       this.count--;
/*     */       if (this.count < this.lowWaterMark)
/*     */         rehash(); 
/*     */     } 
/* 171 */   } private void initialize(int paramInt) { if (paramInt < 0) {
/* 172 */       paramInt = 0;
/* 173 */     } else if (paramInt >= PRIMES.length) {
/* 174 */       System.out.println("TOO BIG");
/* 175 */       paramInt = PRIMES.length - 1;
/*     */     } 
/*     */     
/* 178 */     this.primeIndex = paramInt;
/* 179 */     int i = PRIMES[paramInt];
/* 180 */     this.values = new int[i];
/* 181 */     this.keyList = new int[i];
/* 182 */     for (byte b = 0; b < i; b++) {
/* 183 */       this.keyList[b] = Integer.MIN_VALUE;
/* 184 */       this.values[b] = this.defaultValue;
/*     */     } 
/* 186 */     this.count = 0;
/* 187 */     this.lowWaterMark = (int)(i * 0.0F);
/* 188 */     this.highWaterMark = (int)(i * 0.4F); }
/*     */   public int getDefaultValue() { return this.defaultValue; }
/*     */   public void setDefaultValue(int paramInt) { this.defaultValue = paramInt; rehash(); }
/*     */   public boolean equals(Object paramObject) { if (paramObject.getClass() != getClass()) return false;  IntHashtable intHashtable = (IntHashtable)paramObject; if (intHashtable.size() != this.count || intHashtable.defaultValue != this.defaultValue) return false;  for (byte b = 0; b < this.keyList.length; b++) { int i = this.keyList[b]; if (i > -2147483647 && intHashtable.get(i) != this.values[b]) return false;  }  return true; }
/* 192 */   public int hashCode() { int i = 465; int j = 1362796821; byte b; for (b = 0; b < this.keyList.length; b++) { i = i * j + 1; i += this.keyList[b]; }  for (b = 0; b < this.values.length; b++) { i = i * j + 1; i += this.values[b]; }  return i; } public Object clone() throws CloneNotSupportedException { IntHashtable intHashtable = (IntHashtable)super.clone(); this.values = (int[])this.values.clone(); this.keyList = (int[])this.keyList.clone(); return intHashtable; } private void rehash() { int[] arrayOfInt1 = this.values;
/* 193 */     int[] arrayOfInt2 = this.keyList;
/* 194 */     int i = this.primeIndex;
/* 195 */     if (this.count > this.highWaterMark) {
/* 196 */       i++;
/* 197 */     } else if (this.count < this.lowWaterMark) {
/* 198 */       i -= 2;
/*     */     } 
/* 200 */     initialize(i);
/* 201 */     for (int j = arrayOfInt1.length - 1; j >= 0; j--) {
/* 202 */       int k = arrayOfInt2[j];
/* 203 */       if (k > -2147483647) {
/* 204 */         putInternal(k, arrayOfInt1[j]);
/*     */       }
/*     */     }  }
/*     */ 
/*     */   
/*     */   public void putInternal(int paramInt1, int paramInt2) {
/* 210 */     int i = find(paramInt1);
/* 211 */     if (this.keyList[i] < -2147483647) {
/* 212 */       this.keyList[i] = paramInt1;
/* 213 */       this.count++;
/*     */     } 
/* 215 */     this.values[i] = paramInt2;
/*     */   }
/*     */   
/*     */   private int find(int paramInt) {
/* 219 */     if (paramInt <= -2147483647)
/* 220 */       throw new IllegalArgumentException("key can't be less than 0xFFFFFFFE"); 
/* 221 */     int i = -1;
/* 222 */     int j = (paramInt ^ 0x4000000) % this.keyList.length;
/* 223 */     if (j < 0) j = -j; 
/* 224 */     int k = 0;
/*     */     do {
/* 226 */       int m = this.keyList[j];
/* 227 */       if (m == paramInt)
/* 228 */         return j; 
/* 229 */       if (m <= -2147483647) {
/*     */         
/* 231 */         if (m == Integer.MIN_VALUE) {
/* 232 */           if (i >= 0) {
/* 233 */             j = i;
/*     */           }
/* 235 */           return j;
/* 236 */         }  if (i < 0)
/* 237 */           i = j; 
/*     */       } 
/* 239 */       if (!k) {
/* 240 */         k = paramInt % (this.keyList.length - 1);
/* 241 */         if (k < 0) k = -k; 
/* 242 */         k++;
/*     */       } 
/*     */       
/* 245 */       j = (j + k) % this.keyList.length;
/* 246 */     } while (j != i);
/*     */     
/* 248 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int leastGreaterPrimeIndex(int paramInt) {
/*     */     byte b;
/* 255 */     for (b = 0; b < PRIMES.length && 
/* 256 */       paramInt >= PRIMES[b]; b++);
/*     */ 
/*     */ 
/*     */     
/* 260 */     return (b == 0) ? 0 : (b - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 265 */   private static final int[] PRIMES = new int[] { 17, 37, 67, 131, 257, 521, 1031, 2053, 4099, 8209, 16411, 32771, 65537, 131101, 262147, 524309, 1048583, 2097169, 4194319, 8388617, 16777259, 33554467, 67108879, 134217757, 268435459, 536870923, 1073741827, Integer.MAX_VALUE };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/IntHashtable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */