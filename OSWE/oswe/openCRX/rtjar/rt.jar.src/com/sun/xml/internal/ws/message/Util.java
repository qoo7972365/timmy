/*    */ package com.sun.xml.internal.ws.message;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Util
/*    */ {
/*    */   public static boolean parseBool(String value) {
/* 41 */     if (value.length() == 0) {
/* 42 */       return false;
/*    */     }
/* 44 */     char ch = value.charAt(0);
/* 45 */     return (ch == 't' || ch == '1');
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */