/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.POASystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.protocol.PIHandler;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.LocalObject;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.POAManager;
/*     */ import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
/*     */ import org.omg.PortableServer.POAManagerPackage.State;
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
/*     */ public class POAManagerImpl
/*     */   extends LocalObject
/*     */   implements POAManager
/*     */ {
/*     */   private final POAFactory factory;
/*     */   private PIHandler pihandler;
/*     */   private State state;
/*  64 */   private Set poas = new HashSet(4);
/*  65 */   private int nInvocations = 0;
/*  66 */   private int nWaiters = 0;
/*     */   
/*  68 */   private int myId = 0;
/*     */   
/*     */   private boolean debug;
/*     */   
/*     */   private boolean explicitStateChange;
/*     */ 
/*     */   
/*     */   private String stateToString(State paramState) {
/*  76 */     switch (paramState.value()) { case 0:
/*  77 */         return "State[HOLDING]";
/*  78 */       case 1: return "State[ACTIVE]";
/*  79 */       case 2: return "State[DISCARDING]";
/*  80 */       case 3: return "State[INACTIVE]"; }
/*     */ 
/*     */     
/*  83 */     return "State[UNKNOWN]";
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  88 */     return "POAManagerImpl[myId=" + this.myId + " state=" + 
/*  89 */       stateToString(this.state) + " nInvocations=" + this.nInvocations + " nWaiters=" + this.nWaiters + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   POAFactory getFactory() {
/*  96 */     return this.factory;
/*     */   }
/*     */ 
/*     */   
/*     */   PIHandler getPIHandler() {
/* 101 */     return this.pihandler;
/*     */   }
/*     */ 
/*     */   
/*     */   private void countedWait() {
/*     */     try {
/* 107 */       if (this.debug) {
/* 108 */         ORBUtility.dprint(this, "Calling countedWait on POAManager " + this + " nWaiters=" + this.nWaiters);
/*     */       }
/*     */ 
/*     */       
/* 112 */       this.nWaiters++;
/* 113 */       wait();
/* 114 */     } catch (InterruptedException interruptedException) {
/*     */     
/*     */     } finally {
/* 117 */       this.nWaiters--;
/*     */       
/* 119 */       if (this.debug) {
/* 120 */         ORBUtility.dprint(this, "Exiting countedWait on POAManager " + this + " nWaiters=" + this.nWaiters);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyWaiters() {
/* 128 */     if (this.debug) {
/* 129 */       ORBUtility.dprint(this, "Calling notifyWaiters on POAManager " + this + " nWaiters=" + this.nWaiters);
/*     */     }
/*     */ 
/*     */     
/* 133 */     if (this.nWaiters > 0) {
/* 134 */       notifyAll();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getManagerId() {
/* 139 */     return this.myId;
/*     */   }
/*     */ 
/*     */   
/*     */   POAManagerImpl(POAFactory paramPOAFactory, PIHandler paramPIHandler) {
/* 144 */     this.factory = paramPOAFactory;
/* 145 */     paramPOAFactory.addPoaManager(this);
/* 146 */     this.pihandler = paramPIHandler;
/* 147 */     this.myId = paramPOAFactory.newPOAManagerId();
/* 148 */     this.state = State.HOLDING;
/* 149 */     this.debug = (paramPOAFactory.getORB()).poaDebugFlag;
/* 150 */     this.explicitStateChange = false;
/*     */     
/* 152 */     if (this.debug) {
/* 153 */       ORBUtility.dprint(this, "Creating POAManagerImpl " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void addPOA(POA paramPOA) {
/* 160 */     if (this.state.value() == 3) {
/* 161 */       POASystemException pOASystemException = this.factory.getWrapper();
/* 162 */       throw pOASystemException.addPoaInactive(CompletionStatus.COMPLETED_NO);
/*     */     } 
/*     */     
/* 165 */     this.poas.add(paramPOA);
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void removePOA(POA paramPOA) {
/* 170 */     this.poas.remove(paramPOA);
/* 171 */     if (this.poas.isEmpty()) {
/* 172 */       this.factory.removePoaManager(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public short getORTState() {
/* 178 */     switch (this.state.value()) { case 0:
/* 179 */         return 0;
/* 180 */       case 1: return 1;
/* 181 */       case 3: return 3;
/* 182 */       case 2: return 2; }
/* 183 */      return 4;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void activate() throws AdapterInactive {
/* 212 */     this.explicitStateChange = true;
/*     */     
/* 214 */     if (this.debug) {
/* 215 */       ORBUtility.dprint(this, "Calling activate on POAManager " + this);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 220 */       if (this.state.value() == 3) {
/* 221 */         throw new AdapterInactive();
/*     */       }
/*     */       
/* 224 */       this.state = State.ACTIVE;
/*     */       
/* 226 */       this.pihandler.adapterManagerStateChanged(this.myId, getORTState());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       notifyWaiters();
/*     */     } finally {
/* 233 */       if (this.debug) {
/* 234 */         ORBUtility.dprint(this, "Exiting activate on POAManager " + this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void hold_requests(boolean paramBoolean) throws AdapterInactive {
/* 247 */     this.explicitStateChange = true;
/*     */     
/* 249 */     if (this.debug) {
/* 250 */       ORBUtility.dprint(this, "Calling hold_requests on POAManager " + this);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 255 */       if (this.state.value() == 3) {
/* 256 */         throw new AdapterInactive();
/*     */       }
/* 258 */       this.state = State.HOLDING;
/*     */       
/* 260 */       this.pihandler.adapterManagerStateChanged(this.myId, getORTState());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 265 */       notifyWaiters();
/*     */       
/* 267 */       if (paramBoolean) {
/* 268 */         while (this.state.value() == 0 && this.nInvocations > 0) {
/* 269 */           countedWait();
/*     */         }
/*     */       }
/*     */     } finally {
/* 273 */       if (this.debug) {
/* 274 */         ORBUtility.dprint(this, "Exiting hold_requests on POAManager " + this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void discard_requests(boolean paramBoolean) throws AdapterInactive {
/* 287 */     this.explicitStateChange = true;
/*     */     
/* 289 */     if (this.debug) {
/* 290 */       ORBUtility.dprint(this, "Calling hold_requests on POAManager " + this);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 295 */       if (this.state.value() == 3) {
/* 296 */         throw new AdapterInactive();
/*     */       }
/*     */       
/* 299 */       this.state = State.DISCARDING;
/*     */       
/* 301 */       this.pihandler.adapterManagerStateChanged(this.myId, getORTState());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 307 */       notifyWaiters();
/*     */       
/* 309 */       if (paramBoolean) {
/* 310 */         while (this.state.value() == 2 && this.nInvocations > 0) {
/* 311 */           countedWait();
/*     */         }
/*     */       }
/*     */     } finally {
/* 315 */       if (this.debug) {
/* 316 */         ORBUtility.dprint(this, "Exiting hold_requests on POAManager " + this);
/*     */       }
/*     */     } 
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
/*     */   public void deactivate(boolean paramBoolean1, boolean paramBoolean2) throws AdapterInactive {
/* 331 */     this.explicitStateChange = true;
/*     */     
/*     */     try {
/* 334 */       synchronized (this) {
/* 335 */         if (this.debug) {
/* 336 */           ORBUtility.dprint(this, "Calling deactivate on POAManager " + this);
/*     */         }
/*     */ 
/*     */         
/* 340 */         if (this.state.value() == 3) {
/* 341 */           throw new AdapterInactive();
/*     */         }
/* 343 */         this.state = State.INACTIVE;
/*     */         
/* 345 */         this.pihandler.adapterManagerStateChanged(this.myId, getORTState());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 351 */         notifyWaiters();
/*     */       } 
/*     */       
/* 354 */       POAManagerDeactivator pOAManagerDeactivator = new POAManagerDeactivator(this, paramBoolean1, this.debug);
/*     */ 
/*     */       
/* 357 */       if (paramBoolean2) {
/* 358 */         pOAManagerDeactivator.run();
/*     */       } else {
/* 360 */         Thread thread = new Thread(pOAManagerDeactivator);
/* 361 */         thread.start();
/*     */       } 
/*     */     } finally {
/* 364 */       synchronized (this) {
/* 365 */         if (this.debug) {
/* 366 */           ORBUtility.dprint(this, "Exiting deactivate on POAManager " + this);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private class POAManagerDeactivator
/*     */     implements Runnable
/*     */   {
/*     */     private boolean etherealize_objects;
/*     */     
/*     */     private POAManagerImpl pmi;
/*     */     private boolean debug;
/*     */     
/*     */     POAManagerDeactivator(POAManagerImpl param1POAManagerImpl1, boolean param1Boolean1, boolean param1Boolean2) {
/* 382 */       this.etherealize_objects = param1Boolean1;
/* 383 */       this.pmi = param1POAManagerImpl1;
/* 384 */       this.debug = param1Boolean2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 390 */         synchronized (this.pmi) {
/* 391 */           if (this.debug) {
/* 392 */             ORBUtility.dprint(this, "Calling run with etherealize_objects=" + this.etherealize_objects + " pmi=" + this.pmi);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 397 */           while (this.pmi.nInvocations > 0) {
/* 398 */             POAManagerImpl.this.countedWait();
/*     */           }
/*     */         } 
/*     */         
/* 402 */         if (this.etherealize_objects) {
/* 403 */           Iterator<?> iterator = null;
/*     */ 
/*     */           
/* 406 */           synchronized (this.pmi) {
/* 407 */             if (this.debug) {
/* 408 */               ORBUtility.dprint(this, "run: Preparing to etherealize with pmi=" + this.pmi);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 413 */             iterator = (new HashSet(this.pmi.poas)).iterator();
/*     */           } 
/*     */           
/* 416 */           while (iterator.hasNext())
/*     */           {
/*     */             
/* 419 */             ((POAImpl)iterator.next()).etherealizeAll();
/*     */           }
/*     */           
/* 422 */           synchronized (this.pmi) {
/* 423 */             if (this.debug) {
/* 424 */               ORBUtility.dprint(this, "run: removing POAManager and clearing poas with pmi=" + this.pmi);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 429 */             POAManagerImpl.this.factory.removePoaManager(this.pmi);
/* 430 */             POAManagerImpl.this.poas.clear();
/*     */           } 
/*     */         } 
/*     */       } finally {
/* 434 */         if (this.debug) {
/* 435 */           synchronized (this.pmi) {
/* 436 */             ORBUtility.dprint(this, "Exiting run");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State get_state() {
/* 449 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void checkIfActive() {
/*     */     try {
/* 461 */       if (this.debug) {
/* 462 */         ORBUtility.dprint(this, "Calling checkIfActive for POAManagerImpl " + this);
/*     */       }
/*     */ 
/*     */       
/* 466 */       checkState();
/*     */     } finally {
/* 468 */       if (this.debug) {
/* 469 */         ORBUtility.dprint(this, "Exiting checkIfActive for POAManagerImpl " + this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkState() {
/* 477 */     while (this.state.value() != 1) {
/* 478 */       switch (this.state.value()) {
/*     */         case 0:
/* 480 */           while (this.state.value() == 0) {
/* 481 */             countedWait();
/*     */           }
/*     */ 
/*     */         
/*     */         case 2:
/* 486 */           throw this.factory.getWrapper().poaDiscarding();
/*     */         
/*     */         case 3:
/* 489 */           throw this.factory.getWrapper().poaInactive();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void enter() {
/*     */     try {
/* 497 */       if (this.debug) {
/* 498 */         ORBUtility.dprint(this, "Calling enter for POAManagerImpl " + this);
/*     */       }
/*     */ 
/*     */       
/* 502 */       checkState();
/* 503 */       this.nInvocations++;
/*     */     } finally {
/* 505 */       if (this.debug) {
/* 506 */         ORBUtility.dprint(this, "Exiting enter for POAManagerImpl " + this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void exit() {
/*     */     try {
/* 515 */       if (this.debug) {
/* 516 */         ORBUtility.dprint(this, "Calling exit for POAManagerImpl " + this);
/*     */       }
/*     */ 
/*     */       
/* 520 */       this.nInvocations--;
/*     */       
/* 522 */       if (this.nInvocations == 0)
/*     */       {
/*     */         
/* 525 */         notifyWaiters();
/*     */       }
/*     */     } finally {
/* 528 */       if (this.debug) {
/* 529 */         ORBUtility.dprint(this, "Exiting exit for POAManagerImpl " + this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void implicitActivation() {
/* 540 */     if (!this.explicitStateChange)
/*     */       try {
/* 542 */         activate();
/* 543 */       } catch (AdapterInactive adapterInactive) {} 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */