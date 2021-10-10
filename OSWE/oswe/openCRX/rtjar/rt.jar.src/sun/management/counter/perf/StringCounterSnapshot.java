/*    */ package sun.management.counter.perf;
/*    */ 
/*    */ import sun.management.counter.AbstractCounter;
/*    */ import sun.management.counter.StringCounter;
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
/*    */ class StringCounterSnapshot
/*    */   extends AbstractCounter
/*    */   implements StringCounter
/*    */ {
/*    */   String value;
/*    */   private static final long serialVersionUID = 1132921539085572034L;
/*    */   
/*    */   StringCounterSnapshot(String paramString1, Units paramUnits, Variability paramVariability, int paramInt, String paramString2) {
/* 41 */     super(paramString1, paramUnits, paramVariability, paramInt);
/* 42 */     this.value = paramString2;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 46 */     return this.value;
/*    */   }
/*    */   
/*    */   public String stringValue() {
/* 50 */     return this.value;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/StringCounterSnapshot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */