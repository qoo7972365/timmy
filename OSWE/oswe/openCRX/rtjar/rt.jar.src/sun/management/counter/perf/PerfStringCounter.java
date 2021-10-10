/*    */ package sun.management.counter.perf;
/*    */ 
/*    */ import java.io.ObjectStreamException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.charset.Charset;
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
/*    */ public class PerfStringCounter
/*    */   extends PerfByteArrayCounter
/*    */   implements StringCounter
/*    */ {
/* 36 */   private static Charset defaultCharset = Charset.defaultCharset();
/*    */   
/*    */   PerfStringCounter(String paramString, Variability paramVariability, int paramInt, ByteBuffer paramByteBuffer) {
/* 39 */     this(paramString, paramVariability, paramInt, paramByteBuffer.limit(), paramByteBuffer);
/*    */   }
/*    */   
/*    */   private static final long serialVersionUID = 6802913433363692452L;
/*    */   
/*    */   PerfStringCounter(String paramString, Variability paramVariability, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer) {
/* 45 */     super(paramString, Units.STRING, paramVariability, paramInt1, paramInt2, paramByteBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isVector() {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public int getVectorLength() {
/* 54 */     return 0;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 58 */     return stringValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public String stringValue() {
/* 63 */     String str = "";
/* 64 */     byte[] arrayOfByte = byteArrayValue();
/*    */     
/* 66 */     if (arrayOfByte == null || arrayOfByte.length <= 1) {
/* 67 */       return str;
/*    */     }
/*    */     
/*    */     byte b;
/* 71 */     for (b = 0; b < arrayOfByte.length && arrayOfByte[b] != 0; b++);
/*    */ 
/*    */ 
/*    */     
/* 75 */     return new String(arrayOfByte, 0, b, defaultCharset);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object writeReplace() throws ObjectStreamException {
/* 82 */     return new StringCounterSnapshot(getName(), 
/* 83 */         getUnits(), 
/* 84 */         getVariability(), 
/* 85 */         getFlags(), 
/* 86 */         stringValue());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/PerfStringCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */