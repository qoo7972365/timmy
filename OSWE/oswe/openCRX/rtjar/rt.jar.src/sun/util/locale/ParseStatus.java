/*    */ package sun.util.locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParseStatus
/*    */ {
/*    */   int parseLength;
/*    */   int errorIndex;
/*    */   String errorMsg;
/*    */   
/*    */   public ParseStatus() {
/* 40 */     reset();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 44 */     this.parseLength = 0;
/* 45 */     this.errorIndex = -1;
/* 46 */     this.errorMsg = null;
/*    */   }
/*    */   
/*    */   public boolean isError() {
/* 50 */     return (this.errorIndex >= 0);
/*    */   }
/*    */   
/*    */   public int getErrorIndex() {
/* 54 */     return this.errorIndex;
/*    */   }
/*    */   
/*    */   public int getParseLength() {
/* 58 */     return this.parseLength;
/*    */   }
/*    */   
/*    */   public String getErrorMessage() {
/* 62 */     return this.errorMsg;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/ParseStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */