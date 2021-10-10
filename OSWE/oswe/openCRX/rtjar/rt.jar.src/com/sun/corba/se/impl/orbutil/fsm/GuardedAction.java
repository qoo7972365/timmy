/*    */ package com.sun.corba.se.impl.orbutil.fsm;
/*    */ 
/*    */ import com.sun.corba.se.spi.orbutil.fsm.Action;
/*    */ import com.sun.corba.se.spi.orbutil.fsm.FSM;
/*    */ import com.sun.corba.se.spi.orbutil.fsm.Guard;
/*    */ import com.sun.corba.se.spi.orbutil.fsm.GuardBase;
/*    */ import com.sun.corba.se.spi.orbutil.fsm.Input;
/*    */ import com.sun.corba.se.spi.orbutil.fsm.State;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuardedAction
/*    */ {
/* 36 */   private static Guard trueGuard = (Guard)new GuardBase("true")
/*    */     {
/*    */       public Guard.Result evaluate(FSM param1FSM, Input param1Input) {
/* 39 */         return Guard.Result.ENABLED;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private Guard guard;
/*    */   private Action action;
/*    */   private State nextState;
/*    */   
/*    */   public GuardedAction(Action paramAction, State paramState) {
/* 49 */     this.guard = trueGuard;
/* 50 */     this.action = paramAction;
/* 51 */     this.nextState = paramState;
/*    */   }
/*    */ 
/*    */   
/*    */   public GuardedAction(Guard paramGuard, Action paramAction, State paramState) {
/* 56 */     this.guard = paramGuard;
/* 57 */     this.action = paramAction;
/* 58 */     this.nextState = paramState;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "GuardedAction[action=" + this.action + " guard=" + this.guard + " nextState=" + this.nextState + "]";
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 67 */     return this.action;
/* 68 */   } public Guard getGuard() { return this.guard; } public State getNextState() {
/* 69 */     return this.nextState;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/fsm/GuardedAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */