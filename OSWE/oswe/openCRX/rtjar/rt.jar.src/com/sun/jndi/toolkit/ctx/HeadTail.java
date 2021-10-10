/*    */ package com.sun.jndi.toolkit.ctx;
/*    */ 
/*    */ import javax.naming.Name;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeadTail
/*    */ {
/*    */   private int status;
/*    */   private Name head;
/*    */   private Name tail;
/*    */   
/*    */   public HeadTail(Name paramName1, Name paramName2) {
/* 41 */     this(paramName1, paramName2, 0);
/*    */   }
/*    */   
/*    */   public HeadTail(Name paramName1, Name paramName2, int paramInt) {
/* 45 */     this.status = paramInt;
/* 46 */     this.head = paramName1;
/* 47 */     this.tail = paramName2;
/*    */   }
/*    */   
/*    */   public void setStatus(int paramInt) {
/* 51 */     this.status = paramInt;
/*    */   }
/*    */   
/*    */   public Name getHead() {
/* 55 */     return this.head;
/*    */   }
/*    */   
/*    */   public Name getTail() {
/* 59 */     return this.tail;
/*    */   }
/*    */   
/*    */   public int getStatus() {
/* 63 */     return this.status;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/ctx/HeadTail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */