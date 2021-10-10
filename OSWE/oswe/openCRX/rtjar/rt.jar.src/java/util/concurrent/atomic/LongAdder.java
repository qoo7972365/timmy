/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongAdder
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   
/*     */   public void add(long paramLong) {
/*     */     Striped64.Cell[] arrayOfCell;
/*     */     long l;
/*  86 */     if ((arrayOfCell = this.cells) != null || !casBase(l = this.base, l + paramLong)) {
/*  87 */       boolean bool = true; long l1; int i; Striped64.Cell cell;
/*  88 */       if (arrayOfCell == null || (i = arrayOfCell.length - 1) < 0 || (
/*  89 */         cell = arrayOfCell[getProbe() & i]) == null || 
/*  90 */         !(bool = cell.cas(l1 = cell.value, l1 + paramLong))) {
/*  91 */         longAccumulate(paramLong, null, bool);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment() {
/*  99 */     add(1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrement() {
/* 106 */     add(-1L);
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
/*     */   public long sum() {
/* 119 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 120 */     long l = this.base;
/* 121 */     if (arrayOfCell != null)
/* 122 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 123 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 124 */           l += cell.value;
/*     */         }
/*     */       }  
/* 127 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 138 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 139 */     this.base = 0L;
/* 140 */     if (arrayOfCell != null) {
/* 141 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 142 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 143 */           cell.value = 0L;
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
/*     */   public long sumThenReset() {
/* 159 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 160 */     long l = this.base;
/* 161 */     this.base = 0L;
/* 162 */     if (arrayOfCell != null) {
/* 163 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 164 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 165 */           l += cell.value;
/* 166 */           cell.value = 0L;
/*     */         } 
/*     */       } 
/*     */     }
/* 170 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 178 */     return Long.toString(sum());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 187 */     return sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 195 */     return (int)sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 203 */     return (float)sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 211 */     return sum();
/*     */   }
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
/*     */     SerializationProxy(LongAdder param1LongAdder) {
/* 229 */       this.value = param1LongAdder.sum();
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
/* 240 */       LongAdder longAdder = new LongAdder();
/* 241 */       longAdder.base = this.value;
/* 242 */       return longAdder;
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
/* 256 */     return new SerializationProxy(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 265 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/LongAdder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */