/*    */ package com.sun.org.apache.xerces.internal.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Status
/*    */ {
/* 35 */   SET((short)-3, false),
/* 36 */   UNKNOWN((short)-2, false),
/* 37 */   RECOGNIZED((short)-1, false),
/* 38 */   NOT_SUPPORTED((short)0, true),
/* 39 */   NOT_RECOGNIZED((short)1, true),
/* 40 */   NOT_ALLOWED((short)2, true);
/*    */   
/*    */   private final short type;
/*    */   
/*    */   private boolean isExceptional;
/*    */ 
/*    */   
/*    */   Status(short type, boolean isExceptional) {
/* 48 */     this.type = type;
/* 49 */     this.isExceptional = isExceptional;
/*    */   }
/*    */   
/*    */   public short getType() {
/* 53 */     return this.type;
/*    */   }
/*    */   
/*    */   public boolean isExceptional() {
/* 57 */     return this.isExceptional;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/Status.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */