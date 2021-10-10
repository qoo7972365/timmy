/*    */ package sun.text;
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
/*    */ 
/*    */ 
/*    */ public final class SupplementaryCharacterData
/*    */   implements Cloneable
/*    */ {
/*    */   private static final byte IGNORE = -1;
/*    */   private int[] dataTable;
/*    */   
/*    */   public SupplementaryCharacterData(int[] paramArrayOfint) {
/* 59 */     this.dataTable = paramArrayOfint;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getValue(int paramInt) {
/*    */     int k;
/* 67 */     assert paramInt >= 65536 && paramInt <= 1114111 : "Invalid code point:" + 
/*    */       
/* 69 */       Integer.toHexString(paramInt);
/*    */     
/* 71 */     int i = 0;
/* 72 */     int j = this.dataTable.length - 1;
/*    */ 
/*    */     
/*    */     while (true) {
/* 76 */       k = (i + j) / 2;
/*    */       
/* 78 */       int n = this.dataTable[k] >> 8;
/* 79 */       int i1 = this.dataTable[k + 1] >> 8;
/*    */       
/* 81 */       if (paramInt < n) {
/* 82 */         j = k; continue;
/* 83 */       }  if (paramInt > i1 - 1) {
/* 84 */         i = k; continue;
/*    */       }  break;
/* 86 */     }  int m = this.dataTable[k] & 0xFF;
/* 87 */     return (m == 255) ? -1 : m;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getArray() {
/* 96 */     return this.dataTable;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/SupplementaryCharacterData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */