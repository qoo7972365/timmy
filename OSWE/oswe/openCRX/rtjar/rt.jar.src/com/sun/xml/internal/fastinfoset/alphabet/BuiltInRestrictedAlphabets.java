/*    */ package com.sun.xml.internal.fastinfoset.alphabet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BuiltInRestrictedAlphabets
/*    */ {
/* 34 */   public static final char[][] table = new char[2][];
/*    */ 
/*    */   
/*    */   static {
/* 38 */     table[0] = "0123456789-+.E ".toCharArray();
/* 39 */     table[1] = "0123456789-:TZ ".toCharArray();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/alphabet/BuiltInRestrictedAlphabets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */