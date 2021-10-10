/*    */ package com.sun.org.apache.regexp.internal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class REUtil
/*    */ {
/*    */   private static final String complexPrefix = "complex:";
/*    */   
/*    */   public static RE createRE(String expression, int matchFlags) throws RESyntaxException {
/* 43 */     if (expression.startsWith("complex:"))
/*    */     {
/* 45 */       return new RE(expression.substring("complex:".length()), matchFlags);
/*    */     }
/* 47 */     return new RE(RE.simplePatternToFullRegularExpression(expression), matchFlags);
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
/*    */   public static RE createRE(String expression) throws RESyntaxException {
/* 59 */     return createRE(expression, 0);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/regexp/internal/REUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */