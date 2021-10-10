/*    */ package sun.management.counter.perf;
/*    */ 
/*    */ import java.io.ObjectStreamException;
/*    */ import java.nio.LongBuffer;
/*    */ import sun.management.counter.AbstractCounter;
/*    */ import sun.management.counter.LongArrayCounter;
/*    */ import sun.management.counter.Units;
/*    */ import sun.management.counter.Variability;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PerfLongArrayCounter
/*    */   extends AbstractCounter
/*    */   implements LongArrayCounter
/*    */ {
/*    */   LongBuffer lb;
/*    */   private static final long serialVersionUID = -2733617913045487126L;
/*    */   
/*    */   PerfLongArrayCounter(String paramString, Units paramUnits, Variability paramVariability, int paramInt1, int paramInt2, LongBuffer paramLongBuffer) {
/* 42 */     super(paramString, paramUnits, paramVariability, paramInt1, paramInt2);
/* 43 */     this.lb = paramLongBuffer;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 47 */     return longArrayValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long[] longArrayValue() {
/* 55 */     this.lb.position(0);
/* 56 */     long[] arrayOfLong = new long[this.lb.limit()];
/*    */ 
/*    */     
/* 59 */     this.lb.get(arrayOfLong);
/*    */     
/* 61 */     return arrayOfLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long longAt(int paramInt) {
/* 68 */     this.lb.position(paramInt);
/* 69 */     return this.lb.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object writeReplace() throws ObjectStreamException {
/* 76 */     return new LongArrayCounterSnapshot(getName(), 
/* 77 */         getUnits(), 
/* 78 */         getVariability(), 
/* 79 */         getFlags(), 
/* 80 */         getVectorLength(), 
/* 81 */         longArrayValue());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/PerfLongArrayCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */