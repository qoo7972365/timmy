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
/*    */ public class UnknownFormatConversionException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 19060418L;
/*    */   private String s;
/*    */   
/*    */   public UnknownFormatConversionException(String paramString) {
/* 50 */     if (paramString == null)
/* 51 */       throw new NullPointerException(); 
/* 52 */     this.s = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getConversion() {
/* 61 */     return this.s;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 66 */     return String.format("Conversion = '%s'", new Object[] { this.s });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/UnknownFormatConversionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */