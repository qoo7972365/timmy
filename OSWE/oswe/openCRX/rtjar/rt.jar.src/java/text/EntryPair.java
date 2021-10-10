/*    */ package java.text;
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
/*    */ final class EntryPair
/*    */ {
/*    */   public String entryName;
/*    */   public int value;
/*    */   public boolean fwd;
/*    */   
/*    */   public EntryPair(String paramString, int paramInt) {
/* 53 */     this(paramString, paramInt, true);
/*    */   }
/*    */   public EntryPair(String paramString, int paramInt, boolean paramBoolean) {
/* 56 */     this.entryName = paramString;
/* 57 */     this.value = paramInt;
/* 58 */     this.fwd = paramBoolean;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/EntryPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */