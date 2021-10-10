/*    */ package com.sun.corba.se.spi.orbutil.fsm;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Guard
/*    */ {
/*    */   Result evaluate(FSM paramFSM, Input paramInput);
/*    */   
/*    */   public static final class Complement
/*    */     extends GuardBase
/*    */   {
/*    */     private Guard guard;
/*    */     
/*    */     public Complement(GuardBase param1GuardBase) {
/* 39 */       super("not(" + param1GuardBase.getName() + ")");
/* 40 */       this.guard = param1GuardBase;
/*    */     }
/*    */ 
/*    */     
/*    */     public Guard.Result evaluate(FSM param1FSM, Input param1Input) {
/* 45 */       return this.guard.evaluate(param1FSM, param1Input).complement();
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class Result
/*    */   {
/*    */     private String name;
/*    */     
/*    */     private Result(String param1String) {
/* 54 */       this.name = param1String;
/*    */     }
/*    */ 
/*    */     
/*    */     public static Result convert(boolean param1Boolean) {
/* 59 */       return param1Boolean ? ENABLED : DISABLED;
/*    */     }
/*    */ 
/*    */     
/*    */     public Result complement() {
/* 64 */       if (this == ENABLED)
/* 65 */         return DISABLED; 
/* 66 */       if (this == DISABLED) {
/* 67 */         return ENABLED;
/*    */       }
/* 69 */       return DEFERED;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 74 */       return "Guard.Result[" + this.name + "]";
/*    */     }
/*    */     
/* 77 */     public static final Result ENABLED = new Result("ENABLED");
/* 78 */     public static final Result DISABLED = new Result("DISABLED");
/* 79 */     public static final Result DEFERED = new Result("DEFERED");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/fsm/Guard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */