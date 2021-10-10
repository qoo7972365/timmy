/*    */ package sun.management.counter.perf;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
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
/*    */ class PerfDataType
/*    */ {
/*    */   private final String name;
/*    */   private final byte value;
/*    */   private final int size;
/* 46 */   public static final PerfDataType BOOLEAN = new PerfDataType("boolean", "Z", 1);
/* 47 */   public static final PerfDataType CHAR = new PerfDataType("char", "C", 1);
/* 48 */   public static final PerfDataType FLOAT = new PerfDataType("float", "F", 8);
/* 49 */   public static final PerfDataType DOUBLE = new PerfDataType("double", "D", 8);
/* 50 */   public static final PerfDataType BYTE = new PerfDataType("byte", "B", 1);
/* 51 */   public static final PerfDataType SHORT = new PerfDataType("short", "S", 2);
/* 52 */   public static final PerfDataType INT = new PerfDataType("int", "I", 4);
/* 53 */   public static final PerfDataType LONG = new PerfDataType("long", "J", 8);
/* 54 */   public static final PerfDataType ILLEGAL = new PerfDataType("illegal", "X", 0);
/*    */   
/* 56 */   private static PerfDataType[] basicTypes = new PerfDataType[] { LONG, BYTE, BOOLEAN, CHAR, FLOAT, DOUBLE, SHORT, INT };
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 61 */     return this.name;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 65 */     return this.value;
/*    */   }
/*    */   
/*    */   public int size() {
/* 69 */     return this.size;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PerfDataType toPerfDataType(byte paramByte) {
/* 80 */     for (byte b = 0; b < basicTypes.length; b++) {
/* 81 */       if (basicTypes[b].byteValue() == paramByte) {
/* 82 */         return basicTypes[b];
/*    */       }
/*    */     } 
/* 85 */     return ILLEGAL;
/*    */   }
/*    */   
/*    */   private PerfDataType(String paramString1, String paramString2, int paramInt) {
/* 89 */     this.name = paramString1;
/* 90 */     this.size = paramInt;
/*    */     try {
/* 92 */       byte[] arrayOfByte = paramString2.getBytes("UTF-8");
/* 93 */       this.value = arrayOfByte[0];
/* 94 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*    */       
/* 96 */       throw new InternalError("Unknown encoding", unsupportedEncodingException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/perf/PerfDataType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */