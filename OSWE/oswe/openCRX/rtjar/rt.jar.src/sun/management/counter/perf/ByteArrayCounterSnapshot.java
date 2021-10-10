/*    */ package sun.management.counter.perf;
/*    */ 
/*    */ import sun.management.counter.AbstractCounter;
/*    */ import sun.management.counter.ByteArrayCounter;
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
/*    */ class ByteArrayCounterSnapshot
/*    */   extends AbstractCounter
/*    */   implements ByteArrayCounter
/*    */ {
/*    */   byte[] value;
/*    */   private static final long serialVersionUID = 1444793459838438979L;
/*    */   
/*    */   ByteArrayCounterSnapshot(String paramString, Units paramUnits, Variability paramVariability, int paramInt1, int paramInt2, byte[] paramArrayOfbyte) {
/* 41 */     super(paramString, paramUnits, paramVariability, paramInt1, paramInt2);
/* 42 */     this.value = paramArrayOfbyte;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 46 */     return this.value;
/*    */   }
/*    */   
/*    */   public byte[] byteArrayValue() {
/* 50 */     return this.value;
/*    */   }
/*    */   
/*    */   public byte byteAt(int paramInt) {
/* 54 */     return this.value[paramInt];
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/ByteArrayCounterSnapshot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */