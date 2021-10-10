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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FormatFlagsConversionMismatchException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 19120414L;
/*    */   private String f;
/*    */   private char c;
/*    */   
/*    */   public FormatFlagsConversionMismatchException(String paramString, char paramChar) {
/* 57 */     if (paramString == null)
/* 58 */       throw new NullPointerException(); 
/* 59 */     this.f = paramString;
/* 60 */     this.c = paramChar;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFlags() {
/* 69 */     return this.f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public char getConversion() {
/* 78 */     return this.c;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 82 */     return "Conversion = " + this.c + ", Flags = " + this.f;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/FormatFlagsConversionMismatchException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */