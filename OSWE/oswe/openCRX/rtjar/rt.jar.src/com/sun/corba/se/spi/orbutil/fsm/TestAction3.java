/*     */ package com.sun.corba.se.spi.orbutil.fsm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TestAction3
/*     */   implements Action
/*     */ {
/*     */   private State oldState;
/*     */   private Input label;
/*     */   
/*     */   public void doIt(FSM paramFSM, Input paramInput) {
/* 111 */     System.out.println("TestAction1:");
/* 112 */     System.out.println("\tlabel    = " + this.label);
/* 113 */     System.out.println("\toldState = " + this.oldState);
/* 114 */     if (this.label != paramInput) {
/* 115 */       throw new Error("Unexcepted Input " + paramInput);
/*     */     }
/*     */   }
/*     */   
/*     */   public TestAction3(State paramState, Input paramInput) {
/* 120 */     this.oldState = paramState;
/* 121 */     this.label = paramInput;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/fsm/TestAction3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */