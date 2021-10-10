/*    */ package sun.management.counter.perf;
/*    */ 
/*    */ import java.io.ObjectStreamException;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class PerfByteArrayCounter
/*    */   extends AbstractCounter
/*    */   implements ByteArrayCounter
/*    */ {
/*    */   ByteBuffer bb;
/*    */   private static final long serialVersionUID = 2545474036937279921L;
/*    */   
/*    */   PerfByteArrayCounter(String paramString, Units paramUnits, Variability paramVariability, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer) {
/* 40 */     super(paramString, paramUnits, paramVariability, paramInt1, paramInt2);
/* 41 */     this.bb = paramByteBuffer;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 45 */     return byteArrayValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] byteArrayValue() {
/* 53 */     this.bb.position(0);
/* 54 */     byte[] arrayOfByte = new byte[this.bb.limit()];
/*    */ 
/*    */     
/* 57 */     this.bb.get(arrayOfByte);
/*    */     
/* 59 */     return arrayOfByte;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte byteAt(int paramInt) {
/* 66 */     this.bb.position(paramInt);
/* 67 */     return this.bb.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     String str = getName() + ": " + new String(byteArrayValue()) + " " + getUnits();
/* 73 */     if (isInternal()) {
/* 74 */       return str + " [INTERNAL]";
/*    */     }
/* 76 */     return str;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object writeReplace() throws ObjectStreamException {
/* 84 */     return new ByteArrayCounterSnapshot(getName(), 
/* 85 */         getUnits(), 
/* 86 */         getVariability(), 
/* 87 */         getFlags(), 
/* 88 */         getVectorLength(), 
/* 89 */         byteArrayValue());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/PerfByteArrayCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */