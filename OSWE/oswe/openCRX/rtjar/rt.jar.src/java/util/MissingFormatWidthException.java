/*    */ package java.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MissingFormatWidthException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 15560123L;
/*    */   private String s;
/*    */   
/*    */   public MissingFormatWidthException(String paramString) {
/* 51 */     if (paramString == null)
/* 52 */       throw new NullPointerException(); 
/* 53 */     this.s = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFormatSpecifier() {
/* 62 */     return this.s;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 66 */     return this.s;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/MissingFormatWidthException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */