/*    */ package com.sun.org.apache.xalan.internal.xsltc.runtime;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Operators
/*    */ {
/*    */   public static final int EQ = 0;
/*    */   public static final int NE = 1;
/*    */   public static final int GT = 2;
/*    */   public static final int LT = 3;
/*    */   public static final int GE = 4;
/*    */   public static final int LE = 5;
/* 38 */   private static final String[] names = new String[] { "=", "!=", ">", "<", ">=", "<=" };
/*    */ 
/*    */ 
/*    */   
/*    */   public static final String getOpNames(int operator) {
/* 43 */     return names[operator];
/*    */   }
/*    */ 
/*    */   
/* 47 */   private static final int[] swapOpArray = new int[] { 0, 1, 3, 2, 5, 4 };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int swapOp(int operator) {
/* 57 */     return swapOpArray[operator];
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/runtime/Operators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */