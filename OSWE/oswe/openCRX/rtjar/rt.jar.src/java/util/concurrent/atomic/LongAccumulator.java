/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongAccumulator
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   private final LongBinaryOperator function;
/*     */   private final long identity;
/*     */   
/*     */   public LongAccumulator(LongBinaryOperator paramLongBinaryOperator, long paramLong) {
/*  94 */     this.function = paramLongBinaryOperator;
/*  95 */     this.base = this.identity = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accumulate(long paramLong) {
/*     */     boolean bool;
/*     */     Striped64.Cell[] arrayOfCell;
/*     */     long l1, l2;
/* 105 */     if ((arrayOfCell = this.cells) != null || ((
/* 106 */       l2 = this.function.applyAsLong(l1 = this.base, paramLong)) != l1 && !casBase(l1, l2)))
/* 107 */     { bool = true; int i; Striped64.Cell cell;
/* 108 */       if (arrayOfCell != null && (i = arrayOfCell.length - 1) >= 0 && (
/* 109 */         cell = arrayOfCell[getProbe() & i]) != null)
/*     */       { long l;
/*     */         
/* 112 */         if (!(bool = ((l2 = this.function.applyAsLong(l = cell.value, paramLong)) == l || cell.cas(l, l2)) ? true : false))
/* 113 */         { longAccumulate(paramLong, this.function, bool); return; }  return; }  } else { return; }  longAccumulate(paramLong, this.function, bool);
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
/*     */   public long get() {
/* 127 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 128 */     long l = this.base;
/* 129 */     if (arrayOfCell != null)
/* 130 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 131 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 132 */           l = this.function.applyAsLong(l, cell.value);
/*     */         }
/*     */       }  
/* 135 */     return l;
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
/*     */   public void reset() {
/* 147 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 148 */     this.base = this.identity;
/* 149 */     if (arrayOfCell != null) {
/* 150 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 151 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 152 */           cell.value = this.identity;
/*     */         }
/*     */       } 
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
/*     */   public long getThenReset() {
/* 168 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 169 */     long l = this.base;
/* 170 */     this.base = this.identity;
/* 171 */     if (arrayOfCell != null) {
/* 172 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 173 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 174 */           long l1 = cell.value;
/* 175 */           cell.value = this.identity;
/* 176 */           l = this.function.applyAsLong(l, l1);
/*     */         } 
/*     */       } 
/*     */     }
/* 180 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 188 */     return Long.toString(get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 197 */     return get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 205 */     return (int)get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 213 */     return (float)get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 221 */     return get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializationProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7249069246863182397L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final long value;
/*     */ 
/*     */ 
/*     */     
/*     */     private final LongBinaryOperator function;
/*     */ 
/*     */ 
/*     */     
/*     */     private final long identity;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SerializationProxy(LongAccumulator param1LongAccumulator) {
/* 249 */       this.function = param1LongAccumulator.function;
/* 250 */       this.identity = param1LongAccumulator.identity;
/* 251 */       this.value = param1LongAccumulator.get();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 262 */       LongAccumulator longAccumulator = new LongAccumulator(this.function, this.identity);
/* 263 */       longAccumulator.base = this.value;
/* 264 */       return longAccumulator;
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
/*     */   private Object writeReplace() {
/* 278 */     return new SerializationProxy(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 287 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/LongAccumulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */