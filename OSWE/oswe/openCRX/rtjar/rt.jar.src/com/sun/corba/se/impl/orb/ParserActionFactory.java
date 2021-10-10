/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.Operation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParserActionFactory
/*    */ {
/*    */   public static ParserAction makeNormalAction(String paramString1, Operation paramOperation, String paramString2) {
/* 36 */     return new NormalParserAction(paramString1, paramOperation, paramString2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ParserAction makePrefixAction(String paramString1, Operation paramOperation, String paramString2, Class paramClass) {
/* 42 */     return new PrefixParserAction(paramString1, paramOperation, paramString2, paramClass);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/ParserActionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */