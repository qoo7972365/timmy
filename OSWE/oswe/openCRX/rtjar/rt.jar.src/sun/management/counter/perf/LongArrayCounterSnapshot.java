/*    */ package sun.management.counter.perf;
/*    */ 
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
/*    */ 
/*    */ class LongArrayCounterSnapshot
/*    */   extends AbstractCounter
/*    */   implements LongArrayCounter
/*    */ {
/*    */   long[] value;
/*    */   private static final long serialVersionUID = 3585870271405924292L;
/*    */   
/*    */   LongArrayCounterSnapshot(String paramString, Units paramUnits, Variability paramVariability, int paramInt1, int paramInt2, long[] paramArrayOflong) {
/* 41 */     super(paramString, paramUnits, paramVariability, paramInt1, paramInt2);
/* 42 */     this.value = paramArrayOflong;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 46 */     return this.value;
/*    */   }
/*    */   
/*    */   public long[] longArrayValue() {
/* 50 */     return this.value;
/*    */   }
/*    */   
/*    */   public long longAt(int paramInt) {
/* 54 */     return this.value[paramInt];
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/LongArrayCounterSnapshot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */