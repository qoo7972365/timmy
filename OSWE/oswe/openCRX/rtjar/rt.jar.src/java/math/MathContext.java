/*     */ package java.math;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.StreamCorruptedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MathContext
/*     */   implements Serializable
/*     */ {
/*     */   private static final int DEFAULT_DIGITS = 9;
/*  62 */   private static final RoundingMode DEFAULT_ROUNDINGMODE = RoundingMode.HALF_UP;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_DIGITS = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 5579720004786848255L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final MathContext UNLIMITED = new MathContext(0, RoundingMode.HALF_UP);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final MathContext DECIMAL32 = new MathContext(7, RoundingMode.HALF_EVEN);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static final MathContext DECIMAL64 = new MathContext(16, RoundingMode.HALF_EVEN);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public static final MathContext DECIMAL128 = new MathContext(34, RoundingMode.HALF_EVEN);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final RoundingMode roundingMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MathContext(int paramInt) {
/* 141 */     this(paramInt, DEFAULT_ROUNDINGMODE);
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
/*     */   public MathContext(int paramInt, RoundingMode paramRoundingMode) {
/* 157 */     if (paramInt < 0)
/* 158 */       throw new IllegalArgumentException("Digits < 0"); 
/* 159 */     if (paramRoundingMode == null) {
/* 160 */       throw new NullPointerException("null RoundingMode");
/*     */     }
/* 162 */     this.precision = paramInt;
/* 163 */     this.roundingMode = paramRoundingMode;
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
/*     */   public MathContext(String paramString) {
/*     */     int i;
/* 183 */     boolean bool = false;
/*     */     
/* 185 */     if (paramString == null)
/* 186 */       throw new NullPointerException("null String"); 
/*     */     try {
/* 188 */       if (!paramString.startsWith("precision=")) throw new RuntimeException(); 
/* 189 */       int j = paramString.indexOf(' ');
/* 190 */       int k = 10;
/* 191 */       i = Integer.parseInt(paramString.substring(10, j));
/*     */       
/* 193 */       if (!paramString.startsWith("roundingMode=", j + 1))
/* 194 */         throw new RuntimeException(); 
/* 195 */       k = j + 1 + 13;
/* 196 */       String str = paramString.substring(k, paramString.length());
/* 197 */       this.roundingMode = RoundingMode.valueOf(str);
/* 198 */     } catch (RuntimeException runtimeException) {
/* 199 */       throw new IllegalArgumentException("bad string format");
/*     */     } 
/*     */     
/* 202 */     if (i < 0) {
/* 203 */       throw new IllegalArgumentException("Digits < 0");
/*     */     }
/* 205 */     this.precision = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrecision() {
/* 216 */     return this.precision;
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
/*     */   public RoundingMode getRoundingMode() {
/* 236 */     return this.roundingMode;
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
/*     */   public boolean equals(Object paramObject) {
/* 251 */     if (!(paramObject instanceof MathContext))
/* 252 */       return false; 
/* 253 */     MathContext mathContext = (MathContext)paramObject;
/* 254 */     return (mathContext.precision == this.precision && mathContext.roundingMode == this.roundingMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 264 */     return this.precision + this.roundingMode.hashCode() * 59;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 300 */     return "precision=" + this.precision + " roundingMode=" + this.roundingMode
/* 301 */       .toString();
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 314 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 316 */     if (this.precision < 0) {
/* 317 */       String str = "MathContext: invalid digits in stream";
/* 318 */       throw new StreamCorruptedException(str);
/*     */     } 
/* 320 */     if (this.roundingMode == null) {
/* 321 */       String str = "MathContext: null roundingMode in stream";
/* 322 */       throw new StreamCorruptedException(str);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/math/MathContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */