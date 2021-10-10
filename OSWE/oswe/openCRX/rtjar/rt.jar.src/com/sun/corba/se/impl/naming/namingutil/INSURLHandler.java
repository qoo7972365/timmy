/*    */ package com.sun.corba.se.impl.naming.namingutil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class INSURLHandler
/*    */ {
/* 38 */   private static INSURLHandler insURLHandler = null;
/*    */ 
/*    */ 
/*    */   
/*    */   private static final int CORBALOC_PREFIX_LENGTH = 9;
/*    */ 
/*    */   
/*    */   private static final int CORBANAME_PREFIX_LENGTH = 10;
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized INSURLHandler getINSURLHandler() {
/* 50 */     if (insURLHandler == null) {
/* 51 */       insURLHandler = new INSURLHandler();
/*    */     }
/* 53 */     return insURLHandler;
/*    */   }
/*    */   
/*    */   public INSURL parseURL(String paramString) {
/* 57 */     String str = paramString;
/* 58 */     if (str.startsWith("corbaloc:") == true)
/* 59 */       return new CorbalocURL(str.substring(9)); 
/* 60 */     if (str.startsWith("corbaname:") == true) {
/* 61 */       return new CorbanameURL(str.substring(10));
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/namingutil/INSURLHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */