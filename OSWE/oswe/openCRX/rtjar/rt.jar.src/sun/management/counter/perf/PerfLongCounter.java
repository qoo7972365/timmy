/*    */ package sun.management.counter.perf;
/*    */ 
/*    */ import java.io.ObjectStreamException;
/*    */ import java.nio.LongBuffer;
/*    */ import sun.management.counter.AbstractCounter;
/*    */ import sun.management.counter.LongCounter;
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
/*    */ public class PerfLongCounter
/*    */   extends AbstractCounter
/*    */   implements LongCounter
/*    */ {
/*    */   LongBuffer lb;
/*    */   private static final long serialVersionUID = 857711729279242948L;
/*    */   
/*    */   PerfLongCounter(String paramString, Units paramUnits, Variability paramVariability, int paramInt, LongBuffer paramLongBuffer) {
/* 40 */     super(paramString, paramUnits, paramVariability, paramInt);
/* 41 */     this.lb = paramLongBuffer;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 45 */     return new Long(this.lb.get(0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long longValue() {
/* 52 */     return this.lb.get(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object writeReplace() throws ObjectStreamException {
/* 59 */     return new LongCounterSnapshot(getName(), 
/* 60 */         getUnits(), 
/* 61 */         getVariability(), 
/* 62 */         getFlags(), 
/* 63 */         longValue());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/PerfLongCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */