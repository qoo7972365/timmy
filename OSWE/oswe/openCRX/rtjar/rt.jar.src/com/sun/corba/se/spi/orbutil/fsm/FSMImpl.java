/*     */ package com.sun.corba.se.spi.orbutil.fsm;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.orbutil.fsm.StateEngineImpl;
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
/*     */ public class FSMImpl
/*     */   implements FSM
/*     */ {
/*     */   private boolean debug;
/*     */   private State state;
/*     */   private StateEngineImpl stateEngine;
/*     */   
/*     */   public FSMImpl(StateEngine paramStateEngine, State paramState) {
/*  55 */     this(paramStateEngine, paramState, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public FSMImpl(StateEngine paramStateEngine, State paramState, boolean paramBoolean) {
/*  60 */     this.state = paramState;
/*  61 */     this.stateEngine = (StateEngineImpl)paramStateEngine;
/*  62 */     this.debug = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState() {
/*  69 */     return this.state;
/*     */   }
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
/*     */   public void doIt(Input paramInput) {
/*  93 */     this.stateEngine.doIt(this, paramInput, this.debug);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void internalSetState(State paramState) {
/* 100 */     if (this.debug) {
/* 101 */       ORBUtility.dprint(this, "Calling internalSetState with nextState = " + paramState);
/*     */     }
/*     */ 
/*     */     
/* 105 */     this.state = paramState;
/*     */     
/* 107 */     if (this.debug)
/* 108 */       ORBUtility.dprint(this, "Exiting internalSetState with state = " + this.state); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/fsm/FSMImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */