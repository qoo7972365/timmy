/*    */ package com.sun.org.apache.xalan.internal.xsltc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProcessorVersion
/*    */ {
/* 43 */   private static int MAJOR = 1;
/* 44 */   private static int MINOR = 0;
/* 45 */   private static int DELTA = 0;
/*    */   
/*    */   public static void main(String[] args) {
/* 48 */     System.out.println("XSLTC version " + MAJOR + "." + MINOR + ((DELTA > 0) ? ("." + DELTA) : ""));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/ProcessorVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */