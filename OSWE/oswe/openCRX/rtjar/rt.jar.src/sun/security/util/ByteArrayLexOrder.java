/*    */ package sun.security.util;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public class ByteArrayLexOrder
/*    */   implements Comparator<byte[]>
/*    */ {
/*    */   public final int compare(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 55 */     for (byte b = 0; b < paramArrayOfbyte1.length && b < paramArrayOfbyte2.length; b++) {
/* 56 */       int i = (paramArrayOfbyte1[b] & 0xFF) - (paramArrayOfbyte2[b] & 0xFF);
/* 57 */       if (i != 0) {
/* 58 */         return i;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 63 */     return paramArrayOfbyte1.length - paramArrayOfbyte2.length;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/ByteArrayLexOrder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */