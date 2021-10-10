/*    */ package org.w3c.dom.ls;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LSException
/*    */   extends RuntimeException
/*    */ {
/*    */   public short code;
/*    */   public static final short PARSE_ERR = 81;
/*    */   public static final short SERIALIZE_ERR = 82;
/*    */   
/*    */   public LSException(short code, String message) {
/* 60 */     super(message);
/* 61 */     this.code = code;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/ls/LSException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */