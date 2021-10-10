/*    */ package sun.security.krb5.internal;
/*    */ 
/*    */ import sun.security.krb5.Confounder;
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
/*    */ public class LocalSeqNumber
/*    */   implements SeqNumber
/*    */ {
/*    */   private int lastSeqNumber;
/*    */   
/*    */   public LocalSeqNumber() {
/* 40 */     randInit();
/*    */   }
/*    */   
/*    */   public LocalSeqNumber(int paramInt) {
/* 44 */     init(paramInt);
/*    */   }
/*    */   
/*    */   public LocalSeqNumber(Integer paramInteger) {
/* 48 */     init(paramInteger.intValue());
/*    */   }
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
/*    */   public synchronized void randInit() {
/* 61 */     byte[] arrayOfByte = Confounder.bytes(4);
/* 62 */     arrayOfByte[0] = (byte)(arrayOfByte[0] & 0x3F);
/* 63 */     int i = arrayOfByte[3] & 0xFF | (arrayOfByte[2] & 0xFF) << 8 | (arrayOfByte[1] & 0xFF) << 16 | (arrayOfByte[0] & 0xFF) << 24;
/*    */ 
/*    */ 
/*    */     
/* 67 */     if (i == 0) {
/* 68 */       i = 1;
/*    */     }
/* 70 */     this.lastSeqNumber = i;
/*    */   }
/*    */   
/*    */   public synchronized void init(int paramInt) {
/* 74 */     this.lastSeqNumber = paramInt;
/*    */   }
/*    */   
/*    */   public synchronized int current() {
/* 78 */     return this.lastSeqNumber;
/*    */   }
/*    */   
/*    */   public synchronized int next() {
/* 82 */     return this.lastSeqNumber + 1;
/*    */   }
/*    */   
/*    */   public synchronized int step() {
/* 86 */     return ++this.lastSeqNumber;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/LocalSeqNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */