/*    */ package sun.security.util;
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
/*    */ public final class ArrayUtil
/*    */ {
/*    */   private static void swap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 35 */     byte b = paramArrayOfbyte[paramInt1];
/* 36 */     paramArrayOfbyte[paramInt1] = paramArrayOfbyte[paramInt2];
/* 37 */     paramArrayOfbyte[paramInt2] = b;
/*    */   }
/*    */   
/*    */   public static void reverse(byte[] paramArrayOfbyte) {
/* 41 */     byte b = 0;
/* 42 */     int i = paramArrayOfbyte.length - 1;
/*    */     
/* 44 */     while (b < i) {
/* 45 */       swap(paramArrayOfbyte, b, i);
/* 46 */       b++;
/* 47 */       i--;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/ArrayUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */