/*    */ package com.sun.org.apache.xerces.internal.impl.xpath.regex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParseException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = -7012400318097691370L;
/*    */   int location;
/*    */   
/*    */   public ParseException(String mes, int location) {
/* 44 */     super(mes);
/* 45 */     this.location = location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLocation() {
/* 53 */     return this.location;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xpath/regex/ParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */