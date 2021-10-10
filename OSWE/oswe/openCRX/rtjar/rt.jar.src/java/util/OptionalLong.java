/*     */ package java.util;
/*     */ 
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.function.LongSupplier;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OptionalLong
/*     */ {
/*  53 */   private static final OptionalLong EMPTY = new OptionalLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isPresent;
/*     */ 
/*     */ 
/*     */   
/*     */   private final long value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OptionalLong() {
/*  68 */     this.isPresent = false;
/*  69 */     this.value = 0L;
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
/*     */   public static OptionalLong empty() {
/*  84 */     return EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OptionalLong(long paramLong) {
/*  93 */     this.isPresent = true;
/*  94 */     this.value = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OptionalLong of(long paramLong) {
/* 104 */     return new OptionalLong(paramLong);
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
/*     */   public long getAsLong() {
/* 117 */     if (!this.isPresent) {
/* 118 */       throw new NoSuchElementException("No value present");
/*     */     }
/* 120 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPresent() {
/* 129 */     return this.isPresent;
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
/*     */   public void ifPresent(LongConsumer paramLongConsumer) {
/* 141 */     if (this.isPresent) {
/* 142 */       paramLongConsumer.accept(this.value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long orElse(long paramLong) {
/* 152 */     return this.isPresent ? this.value : paramLong;
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
/*     */   public long orElseGet(LongSupplier paramLongSupplier) {
/* 166 */     return this.isPresent ? this.value : paramLongSupplier.getAsLong();
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
/*     */   public <X extends Throwable> long orElseThrow(Supplier<X> paramSupplier) throws X {
/* 186 */     if (this.isPresent) {
/* 187 */       return this.value;
/*     */     }
/* 189 */     throw (X)paramSupplier.get();
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
/*     */   public boolean equals(Object paramObject) {
/* 208 */     if (this == paramObject) {
/* 209 */       return true;
/*     */     }
/*     */     
/* 212 */     if (!(paramObject instanceof OptionalLong)) {
/* 213 */       return false;
/*     */     }
/*     */     
/* 216 */     OptionalLong optionalLong = (OptionalLong)paramObject;
/* 217 */     return (this.isPresent && optionalLong.isPresent) ? ((this.value == optionalLong.value)) : ((this.isPresent == optionalLong.isPresent));
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
/*     */   public int hashCode() {
/* 230 */     return this.isPresent ? Long.hashCode(this.value) : 0;
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
/*     */   public String toString() {
/* 248 */     return this.isPresent ? 
/* 249 */       String.format("OptionalLong[%s]", new Object[] { Long.valueOf(this.value) }) : "OptionalLong.empty";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/OptionalLong.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */