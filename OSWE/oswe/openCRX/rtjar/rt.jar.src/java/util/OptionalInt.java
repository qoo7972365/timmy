/*     */ package java.util;
/*     */ 
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntSupplier;
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
/*     */ public final class OptionalInt
/*     */ {
/*  53 */   private static final OptionalInt EMPTY = new OptionalInt();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isPresent;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OptionalInt() {
/*  68 */     this.isPresent = false;
/*  69 */     this.value = 0;
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
/*     */   public static OptionalInt empty() {
/*  84 */     return EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OptionalInt(int paramInt) {
/*  93 */     this.isPresent = true;
/*  94 */     this.value = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OptionalInt of(int paramInt) {
/* 104 */     return new OptionalInt(paramInt);
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
/*     */   public int getAsInt() {
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
/*     */   public void ifPresent(IntConsumer paramIntConsumer) {
/* 141 */     if (this.isPresent) {
/* 142 */       paramIntConsumer.accept(this.value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int orElse(int paramInt) {
/* 152 */     return this.isPresent ? this.value : paramInt;
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
/*     */   public int orElseGet(IntSupplier paramIntSupplier) {
/* 166 */     return this.isPresent ? this.value : paramIntSupplier.getAsInt();
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
/*     */   public <X extends Throwable> int orElseThrow(Supplier<X> paramSupplier) throws X {
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
/* 212 */     if (!(paramObject instanceof OptionalInt)) {
/* 213 */       return false;
/*     */     }
/*     */     
/* 216 */     OptionalInt optionalInt = (OptionalInt)paramObject;
/* 217 */     return (this.isPresent && optionalInt.isPresent) ? ((this.value == optionalInt.value)) : ((this.isPresent == optionalInt.isPresent));
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
/* 230 */     return this.isPresent ? Integer.hashCode(this.value) : 0;
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
/* 249 */       String.format("OptionalInt[%s]", new Object[] { Integer.valueOf(this.value) }) : "OptionalInt.empty";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/OptionalInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */