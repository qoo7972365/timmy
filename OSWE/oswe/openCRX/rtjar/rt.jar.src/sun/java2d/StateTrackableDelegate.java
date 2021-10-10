/*     */ package sun.java2d;
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
/*     */ public final class StateTrackableDelegate
/*     */   implements StateTrackable
/*     */ {
/*  47 */   public static final StateTrackableDelegate UNTRACKABLE_DELEGATE = new StateTrackableDelegate(StateTrackable.State.UNTRACKABLE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final StateTrackableDelegate IMMUTABLE_DELEGATE = new StateTrackableDelegate(StateTrackable.State.IMMUTABLE);
/*     */ 
/*     */   
/*     */   private StateTrackable.State theState;
/*     */ 
/*     */   
/*     */   StateTracker theTracker;
/*     */ 
/*     */   
/*     */   private int numDynamicAgents;
/*     */ 
/*     */ 
/*     */   
/*     */   public static StateTrackableDelegate createInstance(StateTrackable.State paramState) {
/*  69 */     switch (paramState) {
/*     */       case UNTRACKABLE:
/*  71 */         return UNTRACKABLE_DELEGATE;
/*     */       case STABLE:
/*  73 */         return new StateTrackableDelegate(StateTrackable.State.STABLE);
/*     */       case DYNAMIC:
/*  75 */         return new StateTrackableDelegate(StateTrackable.State.DYNAMIC);
/*     */       case IMMUTABLE:
/*  77 */         return IMMUTABLE_DELEGATE;
/*     */     } 
/*  79 */     throw new InternalError("unknown state");
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
/*     */   private StateTrackableDelegate(StateTrackable.State paramState) {
/*  92 */     this.theState = paramState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateTrackable.State getState() {
/* 100 */     return this.theState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StateTracker getStateTracker() {
/* 108 */     StateTracker stateTracker = this.theTracker;
/* 109 */     if (stateTracker == null) {
/* 110 */       switch (this.theState) {
/*     */         case IMMUTABLE:
/* 112 */           stateTracker = StateTracker.ALWAYS_CURRENT;
/*     */           break;
/*     */         case STABLE:
/* 115 */           stateTracker = new StateTracker() {
/*     */               public boolean isCurrent() {
/* 117 */                 return (StateTrackableDelegate.this.theTracker == this);
/*     */               }
/*     */             };
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case UNTRACKABLE:
/*     */         case DYNAMIC:
/* 126 */           stateTracker = StateTracker.NEVER_CURRENT;
/*     */           break;
/*     */       } 
/* 129 */       this.theTracker = stateTracker;
/*     */     } 
/* 131 */     return stateTracker;
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
/*     */   public synchronized void setImmutable() {
/* 144 */     if (this.theState == StateTrackable.State.UNTRACKABLE || this.theState == StateTrackable.State.DYNAMIC) {
/* 145 */       throw new IllegalStateException("UNTRACKABLE or DYNAMIC objects cannot become IMMUTABLE");
/*     */     }
/*     */     
/* 148 */     this.theState = StateTrackable.State.IMMUTABLE;
/* 149 */     this.theTracker = null;
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
/*     */   public synchronized void setUntrackable() {
/* 164 */     if (this.theState == StateTrackable.State.IMMUTABLE) {
/* 165 */       throw new IllegalStateException("IMMUTABLE objects cannot become UNTRACKABLE");
/*     */     }
/*     */     
/* 168 */     this.theState = StateTrackable.State.UNTRACKABLE;
/* 169 */     this.theTracker = null;
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
/*     */   public synchronized void addDynamicAgent() {
/* 195 */     if (this.theState == StateTrackable.State.IMMUTABLE) {
/* 196 */       throw new IllegalStateException("Cannot change state from IMMUTABLE");
/*     */     }
/*     */     
/* 199 */     this.numDynamicAgents++;
/* 200 */     if (this.theState == StateTrackable.State.STABLE) {
/* 201 */       this.theState = StateTrackable.State.DYNAMIC;
/* 202 */       this.theTracker = null;
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
/*     */   protected synchronized void removeDynamicAgent() {
/* 230 */     if (--this.numDynamicAgents == 0 && this.theState == StateTrackable.State.DYNAMIC) {
/* 231 */       this.theState = StateTrackable.State.STABLE;
/* 232 */       this.theTracker = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void markDirty() {
/* 254 */     this.theTracker = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/StateTrackableDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */