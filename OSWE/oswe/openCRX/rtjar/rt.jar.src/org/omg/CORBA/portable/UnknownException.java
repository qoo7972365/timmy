/*    */ package org.omg.CORBA.portable;
/*    */ 
/*    */ import org.omg.CORBA.CompletionStatus;
/*    */ import org.omg.CORBA.SystemException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnknownException
/*    */   extends SystemException
/*    */ {
/*    */   public Throwable originalEx;
/*    */   
/*    */   public UnknownException(Throwable paramThrowable) {
/* 55 */     super("", 0, CompletionStatus.COMPLETED_MAYBE);
/* 56 */     this.originalEx = paramThrowable;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/portable/UnknownException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */