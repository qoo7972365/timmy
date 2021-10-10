/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleAccumulator
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   private final DoubleBinaryOperator function;
/*     */   private final long identity;
/*     */   
/*     */   public DoubleAccumulator(DoubleBinaryOperator paramDoubleBinaryOperator, double paramDouble) {
/*  92 */     this.function = paramDoubleBinaryOperator;
/*  93 */     this.base = this.identity = Double.doubleToRawLongBits(paramDouble);
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
/*     */   public void accumulate(double paramDouble) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield cells : [Ljava/util/concurrent/atomic/Striped64$Cell;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: ifnonnull -> 52
/*     */     //   9: aload_0
/*     */     //   10: getfield function : Ljava/util/function/DoubleBinaryOperator;
/*     */     //   13: aload_0
/*     */     //   14: getfield base : J
/*     */     //   17: dup2
/*     */     //   18: lstore #4
/*     */     //   20: invokestatic longBitsToDouble : (J)D
/*     */     //   23: dload_1
/*     */     //   24: invokeinterface applyAsDouble : (DD)D
/*     */     //   29: invokestatic doubleToRawLongBits : (D)J
/*     */     //   32: dup2
/*     */     //   33: lstore #8
/*     */     //   35: lload #4
/*     */     //   37: lcmp
/*     */     //   38: ifeq -> 150
/*     */     //   41: aload_0
/*     */     //   42: lload #4
/*     */     //   44: lload #8
/*     */     //   46: invokevirtual casBase : (JJ)Z
/*     */     //   49: ifne -> 150
/*     */     //   52: iconst_1
/*     */     //   53: istore #12
/*     */     //   55: aload_3
/*     */     //   56: ifnull -> 139
/*     */     //   59: aload_3
/*     */     //   60: arraylength
/*     */     //   61: iconst_1
/*     */     //   62: isub
/*     */     //   63: dup
/*     */     //   64: istore #10
/*     */     //   66: iflt -> 139
/*     */     //   69: aload_3
/*     */     //   70: invokestatic getProbe : ()I
/*     */     //   73: iload #10
/*     */     //   75: iand
/*     */     //   76: aaload
/*     */     //   77: dup
/*     */     //   78: astore #11
/*     */     //   80: ifnull -> 139
/*     */     //   83: aload_0
/*     */     //   84: getfield function : Ljava/util/function/DoubleBinaryOperator;
/*     */     //   87: aload #11
/*     */     //   89: getfield value : J
/*     */     //   92: dup2
/*     */     //   93: lstore #6
/*     */     //   95: invokestatic longBitsToDouble : (J)D
/*     */     //   98: dload_1
/*     */     //   99: invokeinterface applyAsDouble : (DD)D
/*     */     //   104: invokestatic doubleToRawLongBits : (D)J
/*     */     //   107: dup2
/*     */     //   108: lstore #8
/*     */     //   110: lload #6
/*     */     //   112: lcmp
/*     */     //   113: ifeq -> 128
/*     */     //   116: aload #11
/*     */     //   118: lload #6
/*     */     //   120: lload #8
/*     */     //   122: invokevirtual cas : (JJ)Z
/*     */     //   125: ifeq -> 132
/*     */     //   128: iconst_1
/*     */     //   129: goto -> 133
/*     */     //   132: iconst_0
/*     */     //   133: dup
/*     */     //   134: istore #12
/*     */     //   136: ifne -> 150
/*     */     //   139: aload_0
/*     */     //   140: dload_1
/*     */     //   141: aload_0
/*     */     //   142: getfield function : Ljava/util/function/DoubleBinaryOperator;
/*     */     //   145: iload #12
/*     */     //   147: invokevirtual doubleAccumulate : (DLjava/util/function/DoubleBinaryOperator;Z)V
/*     */     //   150: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #103	-> 0
/*     */     //   #106	-> 20
/*     */     //   #105	-> 29
/*     */     //   #106	-> 46
/*     */     //   #107	-> 52
/*     */     //   #108	-> 55
/*     */     //   #109	-> 70
/*     */     //   #113	-> 95
/*     */     //   #112	-> 104
/*     */     //   #114	-> 122
/*     */     //   #115	-> 139
/*     */     //   #117	-> 150
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
/*     */   public double get() {
/* 129 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 130 */     double d = Double.longBitsToDouble(this.base);
/* 131 */     if (arrayOfCell != null)
/* 132 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 133 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null)
/*     */         {
/* 135 */           d = this.function.applyAsDouble(d, Double.longBitsToDouble(cell.value));
/*     */         }
/*     */       }  
/* 138 */     return d;
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
/* 150 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 151 */     this.base = this.identity;
/* 152 */     if (arrayOfCell != null) {
/* 153 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 154 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 155 */           cell.value = this.identity;
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
/*     */   public double getThenReset() {
/* 171 */     Striped64.Cell[] arrayOfCell = this.cells;
/* 172 */     double d = Double.longBitsToDouble(this.base);
/* 173 */     this.base = this.identity;
/* 174 */     if (arrayOfCell != null) {
/* 175 */       for (byte b = 0; b < arrayOfCell.length; b++) {
/* 176 */         Striped64.Cell cell; if ((cell = arrayOfCell[b]) != null) {
/* 177 */           double d1 = Double.longBitsToDouble(cell.value);
/* 178 */           cell.value = this.identity;
/* 179 */           d = this.function.applyAsDouble(d, d1);
/*     */         } 
/*     */       } 
/*     */     }
/* 183 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 191 */     return Double.toString(get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 200 */     return get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 208 */     return (long)get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 216 */     return (int)get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 224 */     return (float)get();
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
/*     */     private final double value;
/*     */ 
/*     */ 
/*     */     
/*     */     private final DoubleBinaryOperator function;
/*     */ 
/*     */ 
/*     */     
/*     */     private final long identity;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SerializationProxy(DoubleAccumulator param1DoubleAccumulator) {
/* 252 */       this.function = param1DoubleAccumulator.function;
/* 253 */       this.identity = param1DoubleAccumulator.identity;
/* 254 */       this.value = param1DoubleAccumulator.get();
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
/* 265 */       double d = Double.longBitsToDouble(this.identity);
/* 266 */       DoubleAccumulator doubleAccumulator = new DoubleAccumulator(this.function, d);
/* 267 */       doubleAccumulator.base = Double.doubleToRawLongBits(this.value);
/* 268 */       return doubleAccumulator;
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
/* 282 */     return new SerializationProxy(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 291 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/DoubleAccumulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */