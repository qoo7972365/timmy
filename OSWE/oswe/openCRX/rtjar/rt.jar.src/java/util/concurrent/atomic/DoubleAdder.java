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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleAdder
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   
/*     */   public void add(double paramDouble) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield cells : [Ljava/util/concurrent/atomic/Striped64$Cell;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: ifnonnull -> 33
/*     */     //   9: aload_0
/*     */     //   10: aload_0
/*     */     //   11: getfield base : J
/*     */     //   14: dup2
/*     */     //   15: lstore #4
/*     */     //   17: lload #4
/*     */     //   19: invokestatic longBitsToDouble : (J)D
/*     */     //   22: dload_1
/*     */     //   23: dadd
/*     */     //   24: invokestatic doubleToRawLongBits : (D)J
/*     */     //   27: invokevirtual casBase : (JJ)Z
/*     */     //   30: ifne -> 101
/*     */     //   33: iconst_1
/*     */     //   34: istore #10
/*     */     //   36: aload_3
/*     */     //   37: ifnull -> 93
/*     */     //   40: aload_3
/*     */     //   41: arraylength
/*     */     //   42: iconst_1
/*     */     //   43: isub
/*     */     //   44: dup
/*     */     //   45: istore #8
/*     */     //   47: iflt -> 93
/*     */     //   50: aload_3
/*     */     //   51: invokestatic getProbe : ()I
/*     */     //   54: iload #8
/*     */     //   56: iand
/*     */     //   57: aaload
/*     */     //   58: dup
/*     */     //   59: astore #9
/*     */     //   61: ifnull -> 93
/*     */     //   64: aload #9
/*     */     //   66: aload #9
/*     */     //   68: getfield value : J
/*     */     //   71: dup2
/*     */     //   72: lstore #6
/*     */     //   74: lload #6
/*     */     //   76: invokestatic longBitsToDouble : (J)D
/*     */     //   79: dload_1
/*     */     //   80: dadd
/*     */     //   81: invokestatic doubleToRawLongBits : (D)J
/*     */     //   84: invokevirtual cas : (JJ)Z
/*     */     //   87: dup
/*     */     //   88: istore #10
/*     */     //   90: ifne -> 101
/*     */     //   93: aload_0
/*     */     //   94: dload_1
/*     */     //   95: aconst_null
/*     */     //   96: iload #10
/*     */     //   98: invokevirtual doubleAccumulate : (DLjava/util/function/DoubleBinaryOperator;Z)V
/*     */     //   101: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #90	-> 0
/*     */     //   #93	-> 19
/*     */     //   #91	-> 27
/*     */     //   #94	-> 33
/*     */     //   #95	-> 36
/*     */     //   #96	-> 51
/*     */     //   #99	-> 76
/*     */     //   #97	-> 84
/*     */     //   #100	-> 93
/*     */     //   #102	-> 101
/*     */   }
/*     */   
/*     */   public double sum() {
/* 117 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 118 */     double d = Double.longBitsToDouble(this.base);
/* 119 */     if (arrayOfCell != null)
/* 120 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 121 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 122 */           d += Double.longBitsToDouble(cell.value);
/*     */         }
/*     */       }  
/* 125 */     return d;
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
/* 136 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 137 */     this.base = 0L;
/* 138 */     if (arrayOfCell != null) {
/* 139 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 140 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 141 */           cell.value = 0L;
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
/*     */   public double sumThenReset() {
/* 157 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 158 */     double d = Double.longBitsToDouble(this.base);
/* 159 */     this.base = 0L;
/* 160 */     if (arrayOfCell != null) {
/* 161 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 162 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 163 */           long l = cell.value;
/* 164 */           cell.value = 0L;
/* 165 */           d += Double.longBitsToDouble(l);
/*     */         } 
/*     */       } 
/*     */     }
/* 169 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 177 */     return Double.toString(sum());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 186 */     return sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 194 */     return (long)sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 202 */     return (int)sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 210 */     return (float)sum();
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
/*     */     private final double value;
/*     */ 
/*     */ 
/*     */     
/*     */     SerializationProxy(DoubleAdder param1DoubleAdder) {
/* 228 */       this.value = param1DoubleAdder.sum();
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
/* 239 */       DoubleAdder doubleAdder = new DoubleAdder();
/* 240 */       doubleAdder.base = Double.doubleToRawLongBits(this.value);
/* 241 */       return doubleAdder;
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
/* 255 */     return new SerializationProxy(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 264 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/DoubleAdder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */