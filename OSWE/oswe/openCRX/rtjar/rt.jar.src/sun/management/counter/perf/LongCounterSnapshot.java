/*    */ package sun.management.counter.perf;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class LongCounterSnapshot
/*    */   extends AbstractCounter
/*    */   implements LongCounter
/*    */ {
/*    */   long value;
/*    */   private static final long serialVersionUID = 2054263861474565758L;
/*    */   
/*    */   LongCounterSnapshot(String paramString, Units paramUnits, Variability paramVariability, int paramInt, long paramLong) {
/* 41 */     super(paramString, paramUnits, paramVariability, paramInt);
/* 42 */     this.value = paramLong;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 46 */     return new Long(this.value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long longValue() {
/* 53 */     return this.value;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/LongCounterSnapshot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */