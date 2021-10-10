/*     */ package java.util;
/*     */ 
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.DoubleSupplier;
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
/*     */ public final class OptionalDouble
/*     */ {
/*  53 */   private static final OptionalDouble EMPTY = new OptionalDouble();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isPresent;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OptionalDouble() {
/*  68 */     this.isPresent = false;
/*  69 */     this.value = Double.NaN;
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
/*     */   public static OptionalDouble empty() {
/*  84 */     return EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OptionalDouble(double paramDouble) {
/*  93 */     this.isPresent = true;
/*  94 */     this.value = paramDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OptionalDouble of(double paramDouble) {
/* 104 */     return new OptionalDouble(paramDouble);
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
/*     */   public double getAsDouble() {
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
/*     */   public void ifPresent(DoubleConsumer paramDoubleConsumer) {
/* 141 */     if (this.isPresent) {
/* 142 */       paramDoubleConsumer.accept(this.value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double orElse(double paramDouble) {
/* 152 */     return this.isPresent ? this.value : paramDouble;
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
/*     */   public double orElseGet(DoubleSupplier paramDoubleSupplier) {
/* 166 */     return this.isPresent ? this.value : paramDoubleSupplier.getAsDouble();
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
/*     */   public <X extends Throwable> double orElseThrow(Supplier<X> paramSupplier) throws X {
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
/* 212 */     if (!(paramObject instanceof OptionalDouble)) {
/* 213 */       return false;
/*     */     }
/*     */     
/* 216 */     OptionalDouble optionalDouble = (OptionalDouble)paramObject;
/* 217 */     return (this.isPresent && optionalDouble.isPresent) ? (
/* 218 */       (Double.compare(this.value, optionalDouble.value) == 0)) : ((this.isPresent == optionalDouble.isPresent));
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
/*     */   public int hashCode() {
/* 230 */     return this.isPresent ? Double.hashCode(this.value) : 0;
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
/* 249 */       String.format("OptionalDouble[%s]", new Object[] { Double.valueOf(this.value) }) : "OptionalDouble.empty";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/OptionalDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */