/*    */ package org.w3c.dom.ranges;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RangeException
/*    */   extends RuntimeException
/*    */ {
/*    */   public short code;
/*    */   public static final short BAD_BOUNDARYPOINTS_ERR = 1;
/*    */   public static final short INVALID_NODE_TYPE_ERR = 2;
/*    */   
/*    */   public RangeException(short code, String message) {
/* 52 */     super(message);
/* 53 */     this.code = code;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/ranges/RangeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */