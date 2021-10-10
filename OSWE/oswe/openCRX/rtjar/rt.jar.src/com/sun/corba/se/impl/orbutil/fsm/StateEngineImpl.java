/*     */ package com.sun.corba.se.impl.orbutil.fsm;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.Action;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.ActionBase;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.FSM;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.FSMImpl;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.Guard;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.Input;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.State;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.StateEngine;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.StateImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.omg.CORBA.INTERNAL;
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
/*     */ public class StateEngineImpl
/*     */   implements StateEngine
/*     */ {
/*  57 */   private static Action emptyAction = (Action)new ActionBase("Empty")
/*     */     {
/*     */       public void doIt(FSM param1FSM, Input param1Input) {}
/*     */     };
/*     */ 
/*     */   
/*     */   private boolean initializing;
/*     */   
/*     */   private Action defaultAction;
/*     */ 
/*     */   
/*     */   public StateEngineImpl() {
/*  69 */     this.initializing = true;
/*  70 */     this.defaultAction = (Action)new ActionBase("Invalid Transition")
/*     */       {
/*     */         public void doIt(FSM param1FSM, Input param1Input)
/*     */         {
/*  74 */           throw new INTERNAL("Invalid transition attempted from " + param1FSM
/*     */               
/*  76 */               .getState() + " under " + param1Input);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateEngine add(State paramState1, Input paramInput, Guard paramGuard, Action paramAction, State paramState2) throws IllegalArgumentException, IllegalStateException {
/*  85 */     mustBeInitializing();
/*     */     
/*  87 */     StateImpl stateImpl = (StateImpl)paramState1;
/*  88 */     GuardedAction guardedAction = new GuardedAction(paramGuard, paramAction, paramState2);
/*  89 */     stateImpl.addGuardedAction(paramInput, guardedAction);
/*     */     
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateEngine add(State paramState1, Input paramInput, Action paramAction, State paramState2) throws IllegalArgumentException, IllegalStateException {
/*  98 */     mustBeInitializing();
/*     */     
/* 100 */     StateImpl stateImpl = (StateImpl)paramState1;
/* 101 */     GuardedAction guardedAction = new GuardedAction(paramAction, paramState2);
/* 102 */     stateImpl.addGuardedAction(paramInput, guardedAction);
/*     */     
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StateEngine setDefault(State paramState1, Action paramAction, State paramState2) throws IllegalArgumentException, IllegalStateException {
/* 110 */     mustBeInitializing();
/*     */     
/* 112 */     StateImpl stateImpl = (StateImpl)paramState1;
/* 113 */     stateImpl.setDefaultAction(paramAction);
/* 114 */     stateImpl.setDefaultNextState(paramState2);
/*     */     
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StateEngine setDefault(State paramState1, State paramState2) throws IllegalArgumentException, IllegalStateException {
/* 122 */     return setDefault(paramState1, emptyAction, paramState2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StateEngine setDefault(State paramState) throws IllegalArgumentException, IllegalStateException {
/* 128 */     return setDefault(paramState, paramState);
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() throws IllegalStateException {
/* 133 */     mustBeInitializing();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     this.initializing = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultAction(Action paramAction) throws IllegalStateException {
/* 145 */     mustBeInitializing();
/* 146 */     this.defaultAction = paramAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doIt(FSM paramFSM, Input paramInput, boolean paramBoolean) {
/* 154 */     if (paramBoolean) {
/* 155 */       ORBUtility.dprint(this, "doIt enter: currentState = " + paramFSM
/* 156 */           .getState() + " in = " + paramInput);
/*     */     }
/*     */     try {
/* 159 */       innerDoIt(paramFSM, paramInput, paramBoolean);
/*     */     } finally {
/* 161 */       if (paramBoolean) {
/* 162 */         ORBUtility.dprint(this, "doIt exit");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private StateImpl getDefaultNextState(StateImpl paramStateImpl) {
/* 170 */     StateImpl stateImpl = (StateImpl)paramStateImpl.getDefaultNextState();
/* 171 */     if (stateImpl == null)
/*     */     {
/* 173 */       stateImpl = paramStateImpl;
/*     */     }
/* 175 */     return stateImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   private Action getDefaultAction(StateImpl paramStateImpl) {
/* 180 */     Action action = paramStateImpl.getDefaultAction();
/* 181 */     if (action == null) {
/* 182 */       action = this.defaultAction;
/*     */     }
/* 184 */     return action;
/*     */   }
/*     */ 
/*     */   
/*     */   private void innerDoIt(FSM paramFSM, Input paramInput, boolean paramBoolean) {
/* 189 */     if (paramBoolean) {
/* 190 */       ORBUtility.dprint(this, "Calling innerDoIt with input " + paramInput);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 195 */     StateImpl stateImpl1 = null;
/* 196 */     StateImpl stateImpl2 = null;
/* 197 */     Action action = null;
/*     */ 
/*     */     
/* 200 */     boolean bool = false;
/*     */     do {
/* 202 */       bool = false;
/* 203 */       stateImpl1 = (StateImpl)paramFSM.getState();
/* 204 */       stateImpl2 = getDefaultNextState(stateImpl1);
/* 205 */       action = getDefaultAction(stateImpl1);
/*     */       
/* 207 */       if (paramBoolean) {
/* 208 */         ORBUtility.dprint(this, "currentState      = " + stateImpl1);
/* 209 */         ORBUtility.dprint(this, "in                = " + paramInput);
/* 210 */         ORBUtility.dprint(this, "default nextState = " + stateImpl2);
/* 211 */         ORBUtility.dprint(this, "default action    = " + action);
/*     */       } 
/*     */       
/* 214 */       Set set = stateImpl1.getGuardedActions(paramInput);
/* 215 */       if (set == null)
/* 216 */         continue;  Iterator<GuardedAction> iterator = set.iterator();
/*     */ 
/*     */ 
/*     */       
/* 220 */       while (iterator.hasNext()) {
/* 221 */         GuardedAction guardedAction = iterator.next();
/* 222 */         Guard.Result result = guardedAction.getGuard().evaluate(paramFSM, paramInput);
/* 223 */         if (paramBoolean) {
/* 224 */           ORBUtility.dprint(this, "doIt: evaluated " + guardedAction + " with result " + result);
/*     */         }
/*     */         
/* 227 */         if (result == Guard.Result.ENABLED) {
/*     */           
/* 229 */           stateImpl2 = (StateImpl)guardedAction.getNextState();
/* 230 */           action = guardedAction.getAction();
/* 231 */           if (paramBoolean) {
/* 232 */             ORBUtility.dprint(this, "nextState = " + stateImpl2);
/* 233 */             ORBUtility.dprint(this, "action    = " + action);
/*     */           }  break;
/*     */         } 
/* 236 */         if (result == Guard.Result.DEFERED) {
/* 237 */           bool = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 242 */     } while (bool);
/*     */     
/* 244 */     performStateTransition(paramFSM, paramInput, stateImpl2, action, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void performStateTransition(FSM paramFSM, Input paramInput, StateImpl paramStateImpl, Action paramAction, boolean paramBoolean) {
/* 250 */     StateImpl stateImpl = (StateImpl)paramFSM.getState();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     boolean bool = !stateImpl.equals(paramStateImpl) ? true : false;
/*     */     
/* 257 */     if (bool) {
/* 258 */       if (paramBoolean) {
/* 259 */         ORBUtility.dprint(this, "doIt: executing postAction for state " + stateImpl);
/*     */       }
/*     */       try {
/* 262 */         stateImpl.postAction(paramFSM);
/* 263 */       } catch (Throwable throwable) {
/* 264 */         if (paramBoolean) {
/* 265 */           ORBUtility.dprint(this, "doIt: postAction threw " + throwable);
/*     */         }
/*     */         
/* 268 */         if (throwable instanceof ThreadDeath) {
/* 269 */           throw (ThreadDeath)throwable;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 279 */       if (paramAction != null)
/* 280 */         paramAction.doIt(paramFSM, paramInput); 
/*     */     } finally {
/* 282 */       if (bool) {
/* 283 */         if (paramBoolean) {
/* 284 */           ORBUtility.dprint(this, "doIt: executing preAction for state " + paramStateImpl);
/*     */         }
/*     */         
/*     */         try {
/* 288 */           paramStateImpl.preAction(paramFSM);
/* 289 */         } catch (Throwable throwable) {
/* 290 */           if (paramBoolean) {
/* 291 */             ORBUtility.dprint(this, "doIt: preAction threw " + throwable);
/*     */           }
/*     */           
/* 294 */           if (throwable instanceof ThreadDeath) {
/* 295 */             throw (ThreadDeath)throwable;
/*     */           }
/*     */         } 
/* 298 */         ((FSMImpl)paramFSM).internalSetState((State)paramStateImpl);
/*     */       } 
/*     */       
/* 301 */       if (paramBoolean) {
/* 302 */         ORBUtility.dprint(this, "doIt: state is now " + paramStateImpl);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public FSM makeFSM(State paramState) throws IllegalStateException {
/* 308 */     mustNotBeInitializing();
/*     */     
/* 310 */     return (FSM)new FSMImpl(this, paramState);
/*     */   }
/*     */ 
/*     */   
/*     */   private void mustBeInitializing() throws IllegalStateException {
/* 315 */     if (!this.initializing) {
/* 316 */       throw new IllegalStateException("Invalid method call after initialization completed");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void mustNotBeInitializing() throws IllegalStateException {
/* 322 */     if (this.initializing)
/* 323 */       throw new IllegalStateException("Invalid method call before initialization completed"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/fsm/StateEngineImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */