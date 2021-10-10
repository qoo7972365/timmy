/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.concurrent.CondVar;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.Action;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.ActionBase;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.FSM;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.FSMImpl;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.Guard;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.GuardBase;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.Input;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.InputImpl;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.State;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.StateEngine;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.StateEngineFactory;
/*     */ import com.sun.corba.se.spi.orbutil.fsm.StateImpl;
/*     */ import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
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
/*     */ public class AOMEntry
/*     */   extends FSMImpl
/*     */ {
/*     */   private final Thread[] etherealizer;
/*     */   private final int[] counter;
/*     */   private final CondVar wait;
/*     */   final POAImpl poa;
/*  71 */   public static final State INVALID = (State)new StateImpl("Invalid");
/*  72 */   public static final State INCARN = (State)new StateImpl("Incarnating") {
/*     */       public void postAction(FSM param1FSM) {
/*  74 */         AOMEntry aOMEntry = (AOMEntry)param1FSM;
/*  75 */         aOMEntry.wait.broadcast();
/*     */       }
/*     */     };
/*  78 */   public static final State VALID = (State)new StateImpl("Valid");
/*  79 */   public static final State ETHP = (State)new StateImpl("EtherealizePending");
/*  80 */   public static final State ETH = (State)new StateImpl("Etherealizing") {
/*     */       public void preAction(FSM param1FSM) {
/*  82 */         AOMEntry aOMEntry = (AOMEntry)param1FSM;
/*  83 */         Thread thread = aOMEntry.etherealizer[0];
/*  84 */         if (thread != null)
/*  85 */           thread.start(); 
/*     */       }
/*     */       
/*     */       public void postAction(FSM param1FSM) {
/*  89 */         AOMEntry aOMEntry = (AOMEntry)param1FSM;
/*  90 */         aOMEntry.wait.broadcast();
/*     */       }
/*     */     };
/*  93 */   public static final State DESTROYED = (State)new StateImpl("Destroyed");
/*     */   
/*  95 */   static final Input START_ETH = (Input)new InputImpl("startEtherealize");
/*  96 */   static final Input ETH_DONE = (Input)new InputImpl("etherealizeDone");
/*  97 */   static final Input INC_DONE = (Input)new InputImpl("incarnateDone");
/*  98 */   static final Input INC_FAIL = (Input)new InputImpl("incarnateFailure");
/*  99 */   static final Input ACTIVATE = (Input)new InputImpl("activateObject");
/* 100 */   static final Input ENTER = (Input)new InputImpl("enter");
/* 101 */   static final Input EXIT = (Input)new InputImpl("exit");
/*     */   
/* 103 */   private static Action incrementAction = (Action)new ActionBase("increment") {
/*     */       public void doIt(FSM param1FSM, Input param1Input) {
/* 105 */         AOMEntry aOMEntry = (AOMEntry)param1FSM;
/* 106 */         aOMEntry.counter[0] = aOMEntry.counter[0] + 1;
/*     */       }
/*     */     };
/*     */   
/* 110 */   private static Action decrementAction = (Action)new ActionBase("decrement") {
/*     */       public void doIt(FSM param1FSM, Input param1Input) {
/* 112 */         AOMEntry aOMEntry = (AOMEntry)param1FSM;
/* 113 */         if (aOMEntry.counter[0] > 0) {
/* 114 */           aOMEntry.counter[0] = aOMEntry.counter[0] - 1;
/*     */         } else {
/* 116 */           throw aOMEntry.poa.lifecycleWrapper().aomEntryDecZero();
/*     */         } 
/*     */       }
/*     */     };
/* 120 */   private static Action throwIllegalStateExceptionAction = (Action)new ActionBase("throwIllegalStateException")
/*     */     {
/*     */       public void doIt(FSM param1FSM, Input param1Input) {
/* 123 */         throw new IllegalStateException("No transitions allowed from the DESTROYED state");
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 128 */   private static Action oaaAction = (Action)new ActionBase("throwObjectAlreadyActive") {
/*     */       public void doIt(FSM param1FSM, Input param1Input) {
/* 130 */         throw new RuntimeException(new ObjectAlreadyActive());
/*     */       }
/*     */     };
/*     */   
/* 134 */   private static Guard waitGuard = (Guard)new GuardBase("wait") {
/*     */       public Guard.Result evaluate(FSM param1FSM, Input param1Input) {
/* 136 */         AOMEntry aOMEntry = (AOMEntry)param1FSM;
/*     */         try {
/* 138 */           aOMEntry.wait.await();
/* 139 */         } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 144 */         return Guard.Result.DEFERED;
/*     */       }
/*     */     };
/*     */   
/*     */   private static class CounterGuard
/*     */     extends GuardBase
/*     */   {
/*     */     private int value;
/*     */     
/*     */     public CounterGuard(int param1Int) {
/* 154 */       super("counter>" + param1Int);
/* 155 */       this.value = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     public Guard.Result evaluate(FSM param1FSM, Input param1Input) {
/* 160 */       AOMEntry aOMEntry = (AOMEntry)param1FSM;
/* 161 */       return Guard.Result.convert((aOMEntry.counter[0] > this.value));
/*     */     }
/*     */   }
/*     */   
/* 165 */   private static GuardBase greaterZeroGuard = new CounterGuard(0);
/* 166 */   private static Guard zeroGuard = (Guard)new Guard.Complement(greaterZeroGuard);
/* 167 */   private static GuardBase greaterOneGuard = new CounterGuard(1);
/* 168 */   private static Guard oneGuard = (Guard)new Guard.Complement(greaterOneGuard);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   private static StateEngine engine = StateEngineFactory.create();
/*     */ 
/*     */   
/*     */   static {
/* 177 */     engine.add(INVALID, ENTER, incrementAction, INCARN);
/* 178 */     engine.add(INVALID, ACTIVATE, null, VALID);
/* 179 */     engine.setDefault(INVALID);
/*     */     
/* 181 */     engine.add(INCARN, ENTER, waitGuard, null, INCARN);
/* 182 */     engine.add(INCARN, EXIT, null, INCARN);
/* 183 */     engine.add(INCARN, START_ETH, waitGuard, null, INCARN);
/* 184 */     engine.add(INCARN, INC_DONE, null, VALID);
/* 185 */     engine.add(INCARN, INC_FAIL, decrementAction, INVALID);
/* 186 */     engine.add(INCARN, ACTIVATE, oaaAction, INCARN);
/*     */     
/* 188 */     engine.add(VALID, ENTER, incrementAction, VALID);
/* 189 */     engine.add(VALID, EXIT, decrementAction, VALID);
/* 190 */     engine.add(VALID, START_ETH, (Guard)greaterZeroGuard, null, ETHP);
/* 191 */     engine.add(VALID, START_ETH, zeroGuard, null, ETH);
/* 192 */     engine.add(VALID, ACTIVATE, oaaAction, VALID);
/*     */     
/* 194 */     engine.add(ETHP, ENTER, waitGuard, null, ETHP);
/* 195 */     engine.add(ETHP, START_ETH, null, ETHP);
/* 196 */     engine.add(ETHP, EXIT, (Guard)greaterOneGuard, decrementAction, ETHP);
/* 197 */     engine.add(ETHP, EXIT, oneGuard, decrementAction, ETH);
/* 198 */     engine.add(ETHP, ACTIVATE, oaaAction, ETHP);
/*     */     
/* 200 */     engine.add(ETH, START_ETH, null, ETH);
/* 201 */     engine.add(ETH, ETH_DONE, null, DESTROYED);
/* 202 */     engine.add(ETH, ACTIVATE, oaaAction, ETH);
/* 203 */     engine.add(ETH, ENTER, waitGuard, null, ETH);
/*     */     
/* 205 */     engine.setDefault(DESTROYED, throwIllegalStateExceptionAction, DESTROYED);
/*     */     
/* 207 */     engine.done();
/*     */   }
/*     */ 
/*     */   
/*     */   public AOMEntry(POAImpl paramPOAImpl) {
/* 212 */     super(engine, INVALID, (paramPOAImpl.getORB()).poaFSMDebugFlag);
/* 213 */     this.poa = paramPOAImpl;
/* 214 */     this.etherealizer = new Thread[1];
/* 215 */     this.etherealizer[0] = null;
/* 216 */     this.counter = new int[1];
/* 217 */     this.counter[0] = 0;
/* 218 */     this
/* 219 */       .wait = new CondVar(paramPOAImpl.poaMutex, (paramPOAImpl.getORB()).poaConcurrencyDebugFlag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEtherealize(Thread paramThread) {
/* 227 */     this.etherealizer[0] = paramThread;
/* 228 */     doIt(START_ETH);
/*     */   }
/*     */   
/* 231 */   public void etherealizeComplete() { doIt(ETH_DONE); }
/* 232 */   public void incarnateComplete() { doIt(INC_DONE); } public void incarnateFailure() {
/* 233 */     doIt(INC_FAIL);
/*     */   } public void activateObject() throws ObjectAlreadyActive {
/*     */     try {
/* 236 */       doIt(ACTIVATE);
/* 237 */     } catch (RuntimeException runtimeException) {
/* 238 */       Throwable throwable = runtimeException.getCause();
/* 239 */       if (throwable instanceof ObjectAlreadyActive) {
/* 240 */         throw (ObjectAlreadyActive)throwable;
/*     */       }
/* 242 */       throw runtimeException;
/*     */     } 
/*     */   }
/* 245 */   public void enter() { doIt(ENTER); } public void exit() {
/* 246 */     doIt(EXIT);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/AOMEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */