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
/*    */ 
/*    */ 
/*    */ public class MissingFormatArgumentException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 19190115L;
/*    */   private String s;
/*    */   
/*    */   public MissingFormatArgumentException(String paramString) {
/* 53 */     if (paramString == null)
/* 54 */       throw new NullPointerException(); 
/* 55 */     this.s = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFormatSpecifier() {
/* 64 */     return this.s;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 68 */     return "Format specifier '" + this.s + "'";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/MissingFormatArgumentException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */