/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderBuffer
/*     */ {
/*     */   protected static final long SIZEOF_BYTE = 1L;
/*     */   protected static final long SIZEOF_SHORT = 2L;
/*     */   protected static final long SIZEOF_INT = 4L;
/*     */   protected static final long SIZEOF_FLOAT = 4L;
/*     */   protected static final long SIZEOF_LONG = 8L;
/*     */   protected static final long SIZEOF_DOUBLE = 8L;
/*     */   private static final int COPY_FROM_ARRAY_THRESHOLD = 6;
/*     */   protected final Unsafe unsafe;
/*     */   protected final long baseAddress;
/*     */   protected final long endAddress;
/*     */   protected long curAddress;
/*     */   protected final int capacity;
/*     */   
/*     */   protected RenderBuffer(int paramInt) {
/*  76 */     this.unsafe = Unsafe.getUnsafe();
/*  77 */     this.curAddress = this.baseAddress = this.unsafe.allocateMemory(paramInt);
/*  78 */     this.endAddress = this.baseAddress + paramInt;
/*  79 */     this.capacity = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RenderBuffer allocate(int paramInt) {
/*  86 */     return new RenderBuffer(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getAddress() {
/*  93 */     return this.baseAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int capacity() {
/* 102 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public final int remaining() {
/* 106 */     return (int)(this.endAddress - this.curAddress);
/*     */   }
/*     */   
/*     */   public final int position() {
/* 110 */     return (int)(this.curAddress - this.baseAddress);
/*     */   }
/*     */   
/*     */   public final void position(long paramLong) {
/* 114 */     this.curAddress = this.baseAddress + paramLong;
/*     */   }
/*     */   
/*     */   public final void clear() {
/* 118 */     this.curAddress = this.baseAddress;
/*     */   }
/*     */   
/*     */   public final RenderBuffer skip(long paramLong) {
/* 122 */     this.curAddress += paramLong;
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderBuffer putByte(byte paramByte) {
/* 131 */     this.unsafe.putByte(this.curAddress, paramByte);
/* 132 */     this.curAddress++;
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public RenderBuffer put(byte[] paramArrayOfbyte) {
/* 137 */     return put(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   public RenderBuffer put(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 141 */     if (paramInt2 > 6) {
/* 142 */       long l1 = paramInt1 * 1L + Unsafe.ARRAY_BYTE_BASE_OFFSET;
/* 143 */       long l2 = paramInt2 * 1L;
/* 144 */       this.unsafe.copyMemory(paramArrayOfbyte, l1, null, this.curAddress, l2);
/* 145 */       position(position() + l2);
/*     */     } else {
/* 147 */       int i = paramInt1 + paramInt2;
/* 148 */       for (int j = paramInt1; j < i; j++) {
/* 149 */         putByte(paramArrayOfbyte[j]);
/*     */       }
/*     */     } 
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderBuffer putShort(short paramShort) {
/* 161 */     this.unsafe.putShort(this.curAddress, paramShort);
/* 162 */     this.curAddress += 2L;
/* 163 */     return this;
/*     */   }
/*     */   
/*     */   public RenderBuffer put(short[] paramArrayOfshort) {
/* 167 */     return put(paramArrayOfshort, 0, paramArrayOfshort.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderBuffer put(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 172 */     if (paramInt2 > 6) {
/* 173 */       long l1 = paramInt1 * 2L + Unsafe.ARRAY_SHORT_BASE_OFFSET;
/* 174 */       long l2 = paramInt2 * 2L;
/* 175 */       this.unsafe.copyMemory(paramArrayOfshort, l1, null, this.curAddress, l2);
/* 176 */       position(position() + l2);
/*     */     } else {
/* 178 */       int i = paramInt1 + paramInt2;
/* 179 */       for (int j = paramInt1; j < i; j++) {
/* 180 */         putShort(paramArrayOfshort[j]);
/*     */       }
/*     */     } 
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderBuffer putInt(int paramInt1, int paramInt2) {
/* 192 */     this.unsafe.putInt(this.baseAddress + paramInt1, paramInt2);
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final RenderBuffer putInt(int paramInt) {
/* 198 */     this.unsafe.putInt(this.curAddress, paramInt);
/* 199 */     this.curAddress += 4L;
/* 200 */     return this;
/*     */   }
/*     */   
/*     */   public RenderBuffer put(int[] paramArrayOfint) {
/* 204 */     return put(paramArrayOfint, 0, paramArrayOfint.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderBuffer put(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 209 */     if (paramInt2 > 6) {
/* 210 */       long l1 = paramInt1 * 4L + Unsafe.ARRAY_INT_BASE_OFFSET;
/* 211 */       long l2 = paramInt2 * 4L;
/* 212 */       this.unsafe.copyMemory(paramArrayOfint, l1, null, this.curAddress, l2);
/* 213 */       position(position() + l2);
/*     */     } else {
/* 215 */       int i = paramInt1 + paramInt2;
/* 216 */       for (int j = paramInt1; j < i; j++) {
/* 217 */         putInt(paramArrayOfint[j]);
/*     */       }
/*     */     } 
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderBuffer putFloat(float paramFloat) {
/* 229 */     this.unsafe.putFloat(this.curAddress, paramFloat);
/* 230 */     this.curAddress += 4L;
/* 231 */     return this;
/*     */   }
/*     */   
/*     */   public RenderBuffer put(float[] paramArrayOffloat) {
/* 235 */     return put(paramArrayOffloat, 0, paramArrayOffloat.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderBuffer put(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 240 */     if (paramInt2 > 6) {
/* 241 */       long l1 = paramInt1 * 4L + Unsafe.ARRAY_FLOAT_BASE_OFFSET;
/* 242 */       long l2 = paramInt2 * 4L;
/* 243 */       this.unsafe.copyMemory(paramArrayOffloat, l1, null, this.curAddress, l2);
/* 244 */       position(position() + l2);
/*     */     } else {
/* 246 */       int i = paramInt1 + paramInt2;
/* 247 */       for (int j = paramInt1; j < i; j++) {
/* 248 */         putFloat(paramArrayOffloat[j]);
/*     */       }
/*     */     } 
/* 251 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderBuffer putLong(long paramLong) {
/* 260 */     this.unsafe.putLong(this.curAddress, paramLong);
/* 261 */     this.curAddress += 8L;
/* 262 */     return this;
/*     */   }
/*     */   
/*     */   public RenderBuffer put(long[] paramArrayOflong) {
/* 266 */     return put(paramArrayOflong, 0, paramArrayOflong.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderBuffer put(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 271 */     if (paramInt2 > 6) {
/* 272 */       long l1 = paramInt1 * 8L + Unsafe.ARRAY_LONG_BASE_OFFSET;
/* 273 */       long l2 = paramInt2 * 8L;
/* 274 */       this.unsafe.copyMemory(paramArrayOflong, l1, null, this.curAddress, l2);
/* 275 */       position(position() + l2);
/*     */     } else {
/* 277 */       int i = paramInt1 + paramInt2;
/* 278 */       for (int j = paramInt1; j < i; j++) {
/* 279 */         putLong(paramArrayOflong[j]);
/*     */       }
/*     */     } 
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderBuffer putDouble(double paramDouble) {
/* 291 */     this.unsafe.putDouble(this.curAddress, paramDouble);
/* 292 */     this.curAddress += 8L;
/* 293 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/RenderBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */