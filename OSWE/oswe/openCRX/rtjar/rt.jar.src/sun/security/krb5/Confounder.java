/*    */ package sun.security.krb5;
/*    */ 
/*    */ import java.security.SecureRandom;
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
/*    */ public final class Confounder
/*    */ {
/* 37 */   private static SecureRandom srand = new SecureRandom();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] bytes(int paramInt) {
/* 43 */     byte[] arrayOfByte = new byte[paramInt];
/* 44 */     srand.nextBytes(arrayOfByte);
/* 45 */     return arrayOfByte;
/*    */   }
/*    */   
/*    */   public static int intValue() {
/* 49 */     return srand.nextInt();
/*    */   }
/*    */   
/*    */   public static long longValue() {
/* 53 */     return srand.nextLong();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/Confounder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */