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
/*    */ public class IllegalFormatConversionException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 17000126L;
/*    */   private char c;
/*    */   private Class<?> arg;
/*    */   
/*    */   public IllegalFormatConversionException(char paramChar, Class<?> paramClass) {
/* 56 */     if (paramClass == null)
/* 57 */       throw new NullPointerException(); 
/* 58 */     this.c = paramChar;
/* 59 */     this.arg = paramClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public char getConversion() {
/* 68 */     return this.c;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> getArgumentClass() {
/* 77 */     return this.arg;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 82 */     return String.format("%c != %s", new Object[] { Character.valueOf(this.c), this.arg.getName() });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/IllegalFormatConversionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */