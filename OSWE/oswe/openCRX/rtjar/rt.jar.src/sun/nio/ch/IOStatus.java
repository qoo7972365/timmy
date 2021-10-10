/*    */ package sun.nio.ch;
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
/*    */ public final class IOStatus
/*    */ {
/*    */   public static final int EOF = -1;
/*    */   public static final int UNAVAILABLE = -2;
/*    */   public static final int INTERRUPTED = -3;
/*    */   public static final int UNSUPPORTED = -4;
/*    */   public static final int THROWN = -5;
/*    */   public static final int UNSUPPORTED_CASE = -6;
/*    */   
/*    */   public static int normalize(int paramInt) {
/* 60 */     if (paramInt == -2)
/* 61 */       return 0; 
/* 62 */     return paramInt;
/*    */   }
/*    */   
/*    */   public static boolean check(int paramInt) {
/* 66 */     return (paramInt >= -2);
/*    */   }
/*    */   
/*    */   public static long normalize(long paramLong) {
/* 70 */     if (paramLong == -2L)
/* 71 */       return 0L; 
/* 72 */     return paramLong;
/*    */   }
/*    */   
/*    */   public static boolean check(long paramLong) {
/* 76 */     return (paramLong >= -2L);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean checkAll(long paramLong) {
/* 81 */     return (paramLong > -1L || paramLong < -6L);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/IOStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */