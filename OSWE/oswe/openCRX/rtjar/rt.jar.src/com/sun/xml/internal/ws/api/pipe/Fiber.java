/*      */ package com.sun.xml.internal.ws.api.pipe;
/*      */ 
/*      */ import com.oracle.webservices.internal.api.message.PropertySet;
/*      */ import com.sun.istack.internal.NotNull;
/*      */ import com.sun.istack.internal.Nullable;
/*      */ import com.sun.xml.internal.ws.api.Cancelable;
/*      */ import com.sun.xml.internal.ws.api.Component;
/*      */ import com.sun.xml.internal.ws.api.ComponentRegistry;
/*      */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*      */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*      */ import com.sun.xml.internal.ws.api.message.Packet;
/*      */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*      */ import com.sun.xml.internal.ws.api.server.Container;
/*      */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.CopyOnWriteArraySet;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.ws.Holder;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Fiber
/*      */   implements Runnable, Cancelable, ComponentRegistry
/*      */ {
/*  134 */   private final List<Listener> _listeners = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addListener(Listener listener) {
/*  143 */     synchronized (this._listeners) {
/*  144 */       if (!this._listeners.contains(listener)) {
/*  145 */         this._listeners.add(listener);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeListener(Listener listener) {
/*  157 */     synchronized (this._listeners) {
/*  158 */       this._listeners.remove(listener);
/*      */     } 
/*      */   }
/*      */   
/*      */   List<Listener> getCurrentListeners() {
/*  163 */     synchronized (this._listeners) {
/*  164 */       return new ArrayList<>(this._listeners);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clearListeners() {
/*  169 */     synchronized (this._listeners) {
/*  170 */       this._listeners.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  178 */   private Tube[] conts = new Tube[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int contsSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tube next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Packet packet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Throwable throwable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Engine owner;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  218 */   private volatile int suspendedCount = 0;
/*      */ 
/*      */   
/*      */   private volatile boolean isInsideSuspendCallbacks = false;
/*      */ 
/*      */   
/*      */   private boolean synchronous;
/*      */ 
/*      */   
/*      */   private boolean interrupted;
/*      */ 
/*      */   
/*      */   private final int id;
/*      */ 
/*      */   
/*      */   private List<FiberContextSwitchInterceptor> interceptors;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private ClassLoader contextClassLoader;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private CompletionCallback completionCallback;
/*      */ 
/*      */   
/*      */   private boolean isDeliverThrowableInPacket = false;
/*      */   
/*      */   private Thread currentThread;
/*      */ 
/*      */   
/*      */   public void setDeliverThrowableInPacket(boolean isDeliverThrowableInPacket) {
/*  250 */     this.isDeliverThrowableInPacket = isDeliverThrowableInPacket;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  262 */   private final ReentrantLock lock = new ReentrantLock();
/*  263 */   private final Condition condition = this.lock.newCondition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile boolean isCanceled;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean started;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean startedSync;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void start(@NotNull Tube tubeline, @NotNull Packet request, @Nullable CompletionCallback completionCallback) {
/*  328 */     start(tubeline, request, completionCallback, false);
/*      */   }
/*      */   
/*      */   private void dumpFiberContext(String desc) {
/*  332 */     if (isTraceEnabled()) {
/*  333 */       String actionAndMsgDesc, tubeDesc, action = null;
/*  334 */       String msgId = null;
/*  335 */       if (this.packet != null) {
/*  336 */         for (SOAPVersion sv : SOAPVersion.values()) {
/*  337 */           for (AddressingVersion av : AddressingVersion.values()) {
/*  338 */             action = (this.packet.getMessage() != null) ? AddressingUtils.getAction(this.packet.getMessage().getHeaders(), av, sv) : null;
/*  339 */             msgId = (this.packet.getMessage() != null) ? AddressingUtils.getMessageID(this.packet.getMessage().getHeaders(), av, sv) : null;
/*  340 */             if (action != null || msgId != null) {
/*      */               break;
/*      */             }
/*      */           } 
/*  344 */           if (action != null || msgId != null) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  350 */       if (action == null && msgId == null) {
/*  351 */         actionAndMsgDesc = "NO ACTION or MSG ID";
/*      */       } else {
/*  353 */         actionAndMsgDesc = "'" + action + "' and msgId '" + msgId + "'";
/*      */       } 
/*      */ 
/*      */       
/*  357 */       if (this.next != null) {
/*  358 */         tubeDesc = this.next.toString() + ".processRequest()";
/*      */       } else {
/*  360 */         tubeDesc = peekCont() + ".processResponse()";
/*      */       } 
/*      */       
/*  363 */       LOGGER.log(Level.FINE, "{0} {1} with {2} and ''current'' tube {3} from thread {4} with Packet: {5}", new Object[] { getName(), desc, actionAndMsgDesc, tubeDesc, Thread.currentThread().getName(), (this.packet != null) ? this.packet.toShortString() : null });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void start(@NotNull Tube tubeline, @NotNull Packet request, @Nullable CompletionCallback completionCallback, boolean forceSync) {
/*  398 */     this.next = tubeline;
/*  399 */     this.packet = request;
/*  400 */     this.completionCallback = completionCallback;
/*      */     
/*  402 */     if (forceSync) {
/*  403 */       this.startedSync = true;
/*  404 */       dumpFiberContext("starting (sync)");
/*  405 */       run();
/*      */     } else {
/*  407 */       this.started = true;
/*  408 */       dumpFiberContext("starting (async)");
/*  409 */       this.owner.addRunnable(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Packet resumePacket) {
/*  437 */     resume(resumePacket, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Packet resumePacket, boolean forceSync) {
/*  452 */     resume(resumePacket, forceSync, (CompletionCallback)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Packet resumePacket, boolean forceSync, CompletionCallback callback) {
/*  463 */     this.lock.lock();
/*      */     try {
/*  465 */       if (callback != null) {
/*  466 */         setCompletionCallback(callback);
/*      */       }
/*  468 */       if (isTraceEnabled())
/*  469 */         LOGGER.log(Level.FINE, "{0} resuming. Will have suspendedCount={1}", new Object[] { getName(), Integer.valueOf(this.suspendedCount - 1) }); 
/*  470 */       this.packet = resumePacket;
/*  471 */       if (--this.suspendedCount == 0) {
/*  472 */         if (!this.isInsideSuspendCallbacks) {
/*  473 */           List<Listener> listeners = getCurrentListeners();
/*  474 */           for (Listener listener : listeners) {
/*      */             try {
/*  476 */               listener.fiberResumed(this);
/*  477 */             } catch (Throwable e) {
/*  478 */               if (isTraceEnabled()) {
/*  479 */                 LOGGER.log(Level.FINE, "Listener {0} threw exception: {1}", new Object[] { listener, e.getMessage() });
/*      */               }
/*      */             } 
/*      */           } 
/*  483 */           if (this.synchronous) {
/*  484 */             this.condition.signalAll();
/*  485 */           } else if (forceSync || this.startedSync) {
/*  486 */             run();
/*      */           } else {
/*  488 */             dumpFiberContext("resuming (async)");
/*  489 */             this.owner.addRunnable(this);
/*      */           }
/*      */         
/*      */         } 
/*  493 */       } else if (isTraceEnabled()) {
/*  494 */         LOGGER.log(Level.FINE, "{0} taking no action on resume because suspendedCount != 0: {1}", new Object[] { getName(), Integer.valueOf(this.suspendedCount) });
/*      */       } 
/*      */     } finally {
/*      */       
/*  498 */       this.lock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resumeAndReturn(@NotNull Packet resumePacket, boolean forceSync) {
/*  508 */     if (isTraceEnabled())
/*  509 */       LOGGER.log(Level.FINE, "{0} resumed with Return Packet", getName()); 
/*  510 */     this.next = null;
/*  511 */     resume(resumePacket, forceSync);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Throwable throwable) {
/*  531 */     resume(throwable, this.packet, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Throwable throwable, @NotNull Packet packet) {
/*  553 */     resume(throwable, packet, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Throwable error, boolean forceSync) {
/*  570 */     resume(error, this.packet, forceSync);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Throwable error, @NotNull Packet packet, boolean forceSync) {
/*  589 */     if (isTraceEnabled())
/*  590 */       LOGGER.log(Level.FINE, "{0} resumed with Return Throwable", getName()); 
/*  591 */     this.next = null;
/*  592 */     this.throwable = error;
/*  593 */     resume(packet, forceSync);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancel(boolean mayInterrupt) {
/*  604 */     this.isCanceled = true;
/*  605 */     if (mayInterrupt)
/*      */     {
/*  607 */       synchronized (this) {
/*  608 */         if (this.currentThread != null) {
/*  609 */           this.currentThread.interrupt();
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface Listener
/*      */   {
/*      */     void fiberSuspended(Fiber param1Fiber);
/*      */     
/*      */     void fiberResumed(Fiber param1Fiber);
/*      */   }
/*      */   
/*      */   private boolean suspend(Holder<Boolean> isRequireUnlock, Runnable onExitRunnable) {
/*  623 */     if (isTraceEnabled()) {
/*  624 */       LOGGER.log(Level.FINE, "{0} suspending. Will have suspendedCount={1}", new Object[] { getName(), Integer.valueOf(this.suspendedCount + 1) });
/*  625 */       if (this.suspendedCount > 0) {
/*  626 */         LOGGER.log(Level.FINE, "WARNING - {0} suspended more than resumed. Will require more than one resume to actually resume this fiber.", getName());
/*      */       }
/*      */     } 
/*      */     
/*  630 */     List<Listener> listeners = getCurrentListeners();
/*  631 */     if (++this.suspendedCount == 1) {
/*  632 */       this.isInsideSuspendCallbacks = true;
/*      */       try {
/*  634 */         for (Listener listener : listeners) {
/*      */           try {
/*  636 */             listener.fiberSuspended(this);
/*  637 */           } catch (Throwable e) {
/*  638 */             if (isTraceEnabled())
/*  639 */               LOGGER.log(Level.FINE, "Listener {0} threw exception: {1}", new Object[] { listener, e.getMessage() }); 
/*      */           } 
/*      */         } 
/*      */       } finally {
/*  643 */         this.isInsideSuspendCallbacks = false;
/*      */       } 
/*      */     } 
/*      */     
/*  647 */     if (this.suspendedCount <= 0) {
/*      */       
/*  649 */       for (Listener listener : listeners) {
/*      */         try {
/*  651 */           listener.fiberResumed(this);
/*  652 */         } catch (Throwable e) {
/*  653 */           if (isTraceEnabled()) {
/*  654 */             LOGGER.log(Level.FINE, "Listener {0} threw exception: {1}", new Object[] { listener, e.getMessage() });
/*      */           }
/*      */         } 
/*      */       } 
/*  658 */     } else if (onExitRunnable != null) {
/*      */       
/*  660 */       if (!this.synchronous) {
/*      */         
/*  662 */         synchronized (this) {
/*      */ 
/*      */           
/*  665 */           this.currentThread = null;
/*      */         } 
/*  667 */         this.lock.unlock();
/*  668 */         assert !this.lock.isHeldByCurrentThread();
/*  669 */         isRequireUnlock.value = Boolean.FALSE;
/*      */         
/*      */         try {
/*  672 */           onExitRunnable.run();
/*  673 */         } catch (Throwable t) {
/*  674 */           throw new OnExitRunnableException(t);
/*      */         } 
/*      */         
/*  677 */         return true;
/*      */       } 
/*      */ 
/*      */       
/*  681 */       if (isTraceEnabled())
/*  682 */         LOGGER.fine("onExitRunnable used with synchronous Fiber execution -- not exiting current thread"); 
/*  683 */       onExitRunnable.run();
/*      */     } 
/*      */ 
/*      */     
/*  687 */     return false;
/*      */   }
/*      */   
/*      */   public static interface CompletionCallback {
/*      */     void onCompletion(@NotNull Packet param1Packet);
/*      */     
/*      */     void onCompletion(@NotNull Throwable param1Throwable); }
/*      */   
/*      */   private static final class OnExitRunnableException extends RuntimeException { public OnExitRunnableException(Throwable target) {
/*  696 */       super((Throwable)null);
/*  697 */       this.target = target;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Throwable target; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addInterceptor(@NotNull FiberContextSwitchInterceptor interceptor) {
/*  721 */     if (this.interceptors == null) {
/*  722 */       this.interceptors = new ArrayList<>();
/*      */     } else {
/*  724 */       List<FiberContextSwitchInterceptor> l = new ArrayList<>();
/*  725 */       l.addAll(this.interceptors);
/*  726 */       this.interceptors = l;
/*      */     } 
/*  728 */     this.interceptors.add(interceptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean removeInterceptor(@NotNull FiberContextSwitchInterceptor interceptor) {
/*  755 */     if (this.interceptors != null) {
/*  756 */       boolean result = this.interceptors.remove(interceptor);
/*  757 */       if (this.interceptors.isEmpty()) {
/*  758 */         this.interceptors = null;
/*      */       } else {
/*  760 */         List<FiberContextSwitchInterceptor> l = new ArrayList<>();
/*  761 */         l.addAll(this.interceptors);
/*  762 */         this.interceptors = l;
/*      */       } 
/*  764 */       return result;
/*      */     } 
/*  766 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ClassLoader getContextClassLoader() {
/*  775 */     return this.contextClassLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassLoader setContextClassLoader(@Nullable ClassLoader contextClassLoader) {
/*  782 */     ClassLoader r = this.contextClassLoader;
/*  783 */     this.contextClassLoader = contextClassLoader;
/*  784 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void run() {
/*  794 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*      */     try {
/*  796 */       assert !this.synchronous;
/*      */       
/*  798 */       if (!doRun()) {
/*  799 */         if (this.startedSync && this.suspendedCount == 0 && (this.next != null || this.contsSize > 0)) {
/*      */ 
/*      */ 
/*      */           
/*  803 */           this.startedSync = false;
/*      */           
/*  805 */           dumpFiberContext("restarting (async) after startSync");
/*  806 */           this.owner.addRunnable(this);
/*      */         } else {
/*  808 */           completionCheck();
/*      */         } 
/*      */       }
/*      */     } finally {
/*  812 */       ContainerResolver.getDefault().exitContainer(old);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Packet runSync(@NotNull Tube tubeline, @NotNull Packet request) {
/*  845 */     this.lock.lock();
/*      */     
/*      */     try {
/*  848 */       Tube[] oldCont = this.conts;
/*  849 */       int oldContSize = this.contsSize;
/*  850 */       boolean oldSynchronous = this.synchronous;
/*  851 */       Tube oldNext = this.next;
/*      */       
/*  853 */       if (oldContSize > 0) {
/*  854 */         this.conts = new Tube[16];
/*  855 */         this.contsSize = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  891 */       this.lock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void completionCheck() {
/*  896 */     this.lock.lock();
/*      */     
/*      */     try {
/*  899 */       if (!this.isCanceled && this.contsSize == 0 && this.suspendedCount == 0) {
/*  900 */         if (isTraceEnabled())
/*  901 */           LOGGER.log(Level.FINE, "{0} completed", getName()); 
/*  902 */         clearListeners();
/*  903 */         this.condition.signalAll();
/*  904 */         if (this.completionCallback != null)
/*  905 */           if (this.throwable != null)
/*  906 */           { if (this.isDeliverThrowableInPacket) {
/*  907 */               this.packet.addSatellite((PropertySet)new ThrowableContainerPropertySet(this.throwable));
/*  908 */               this.completionCallback.onCompletion(this.packet);
/*      */             } else {
/*  910 */               this.completionCallback.onCompletion(this.throwable);
/*      */             }  }
/*  912 */           else { this.completionCallback.onCompletion(this.packet); }
/*      */            
/*      */       } 
/*      */     } finally {
/*  916 */       this.lock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class InterceptorHandler
/*      */     implements FiberContextSwitchInterceptor.Work<Tube, Tube>
/*      */   {
/*      */     private final Holder<Boolean> isUnlockRequired;
/*      */ 
/*      */     
/*      */     private final List<FiberContextSwitchInterceptor> ints;
/*      */     
/*      */     private int idx;
/*      */ 
/*      */     
/*      */     public InterceptorHandler(Holder<Boolean> isUnlockRequired, List<FiberContextSwitchInterceptor> ints) {
/*  934 */       this.isUnlockRequired = isUnlockRequired;
/*  935 */       this.ints = ints;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Tube invoke(Tube next) {
/*  942 */       this.idx = 0;
/*  943 */       return execute(next);
/*      */     }
/*      */ 
/*      */     
/*      */     public Tube execute(Tube next) {
/*  948 */       if (this.idx == this.ints.size()) {
/*  949 */         Fiber.this.next = next;
/*  950 */         if (Fiber.this.__doRun(this.isUnlockRequired, this.ints))
/*  951 */           return (Tube)Fiber.PLACEHOLDER; 
/*      */       } else {
/*  953 */         FiberContextSwitchInterceptor interceptor = this.ints.get(this.idx++);
/*  954 */         return interceptor.<Tube, Tube>execute(Fiber.this, next, this);
/*      */       } 
/*  956 */       return Fiber.this.next;
/*      */     }
/*      */   }
/*      */   
/*  960 */   private static final PlaceholderTube PLACEHOLDER = new PlaceholderTube();
/*      */   
/*      */   private static class PlaceholderTube extends AbstractTubeImpl {
/*      */     private PlaceholderTube() {}
/*      */     
/*      */     public NextAction processRequest(Packet request) {
/*  966 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public NextAction processResponse(Packet response) {
/*  971 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public NextAction processException(Throwable t) {
/*  976 */       return doThrow(t);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void preDestroy() {}
/*      */ 
/*      */     
/*      */     public PlaceholderTube copy(TubeCloner cloner) {
/*  985 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean doRun() {
/*  994 */     dumpFiberContext("running");
/*      */     
/*  996 */     if (serializeExecution) {
/*  997 */       serializedExecutionLock.lock();
/*      */       try {
/*  999 */         return _doRun(this.next);
/*      */       } finally {
/* 1001 */         serializedExecutionLock.unlock();
/*      */       } 
/*      */     } 
/* 1004 */     return _doRun(this.next);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _doRun(Tube next) {
/* 1010 */     Holder<Boolean> isRequireUnlock = new Holder(Boolean.TRUE);
/* 1011 */     this.lock.lock();
/*      */     try {
/*      */       List<FiberContextSwitchInterceptor> ints;
/*      */       ClassLoader old;
/* 1015 */       synchronized (this) {
/* 1016 */         ints = this.interceptors;
/*      */ 
/*      */ 
/*      */         
/* 1020 */         this.currentThread = Thread.currentThread();
/* 1021 */         if (isTraceEnabled()) {
/* 1022 */           LOGGER.log(Level.FINE, "Thread entering _doRun(): {0}", this.currentThread);
/*      */         }
/*      */         
/* 1025 */         old = this.currentThread.getContextClassLoader();
/* 1026 */         this.currentThread.setContextClassLoader(this.contextClassLoader);
/*      */       } 
/*      */       
/*      */       try {
/*      */         boolean needsToReenter;
/*      */         
/*      */         do {
/* 1033 */           if (ints == null) {
/* 1034 */             this.next = next;
/* 1035 */             if (__doRun(isRequireUnlock, null)) {
/* 1036 */               return true;
/*      */             }
/*      */           } else {
/* 1039 */             next = (new InterceptorHandler(isRequireUnlock, ints)).invoke(next);
/* 1040 */             if (next == PLACEHOLDER) {
/* 1041 */               return true;
/*      */             }
/*      */           } 
/*      */           
/* 1045 */           synchronized (this) {
/* 1046 */             needsToReenter = (ints != this.interceptors);
/* 1047 */             if (needsToReenter)
/* 1048 */               ints = this.interceptors; 
/*      */           } 
/* 1050 */         } while (needsToReenter);
/* 1051 */       } catch (OnExitRunnableException o) {
/*      */         OnExitRunnableException onExitRunnableException1;
/*      */         
/* 1054 */         Throwable t = onExitRunnableException1.target;
/* 1055 */         if (t instanceof WebServiceException)
/* 1056 */           throw (WebServiceException)t; 
/* 1057 */         throw new WebServiceException(t);
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 1062 */         Thread thread = Thread.currentThread();
/* 1063 */         thread.setContextClassLoader(old);
/* 1064 */         if (isTraceEnabled()) {
/* 1065 */           LOGGER.log(Level.FINE, "Thread leaving _doRun(): {0}", thread);
/*      */         }
/*      */       } 
/*      */       
/* 1069 */       return false;
/*      */     } finally {
/* 1071 */       if (((Boolean)isRequireUnlock.value).booleanValue()) {
/* 1072 */         synchronized (this) {
/* 1073 */           this.currentThread = null;
/*      */         } 
/* 1075 */         this.lock.unlock();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean __doRun(Holder<Boolean> isRequireUnlock, List<FiberContextSwitchInterceptor> originalInterceptors) {
/* 1086 */     assert this.lock.isHeldByCurrentThread();
/*      */     
/* 1088 */     Fiber old = CURRENT_FIBER.get();
/* 1089 */     CURRENT_FIBER.set(this);
/*      */ 
/*      */     
/* 1092 */     boolean traceEnabled = LOGGER.isLoggable(Level.FINER);
/*      */     
/*      */     try {
/* 1095 */       boolean abortResponse = false;
/* 1096 */       while (isReady(originalInterceptors)) {
/* 1097 */         if (this.isCanceled) {
/* 1098 */           this.next = null;
/* 1099 */           this.throwable = null;
/* 1100 */           this.contsSize = 0;
/*      */           
/*      */           break;
/*      */         } 
/*      */         try {
/*      */           NextAction na;
/*      */           Tube last;
/* 1107 */           if (this.throwable != null) {
/* 1108 */             if (this.contsSize == 0 || abortResponse) {
/* 1109 */               this.contsSize = 0;
/*      */               
/* 1111 */               return false;
/*      */             } 
/* 1113 */             last = popCont();
/* 1114 */             if (traceEnabled)
/* 1115 */               LOGGER.log(Level.FINER, "{0} {1}.processException({2})", new Object[] { getName(), last, this.throwable }); 
/* 1116 */             na = last.processException(this.throwable);
/*      */           }
/* 1118 */           else if (this.next != null) {
/* 1119 */             if (traceEnabled)
/* 1120 */               LOGGER.log(Level.FINER, "{0} {1}.processRequest({2})", new Object[] { getName(), this.next, (this.packet != null) ? ("Packet@" + Integer.toHexString(this.packet.hashCode())) : "null" }); 
/* 1121 */             na = this.next.processRequest(this.packet);
/* 1122 */             last = this.next;
/*      */           } else {
/* 1124 */             if (this.contsSize == 0 || abortResponse) {
/*      */               
/* 1126 */               this.contsSize = 0;
/* 1127 */               return false;
/*      */             } 
/* 1129 */             last = popCont();
/* 1130 */             if (traceEnabled)
/* 1131 */               LOGGER.log(Level.FINER, "{0} {1}.processResponse({2})", new Object[] { getName(), last, (this.packet != null) ? ("Packet@" + Integer.toHexString(this.packet.hashCode())) : "null" }); 
/* 1132 */             na = last.processResponse(this.packet);
/*      */           } 
/*      */ 
/*      */           
/* 1136 */           if (traceEnabled) {
/* 1137 */             LOGGER.log(Level.FINER, "{0} {1} returned with {2}", new Object[] { getName(), last, na });
/*      */           }
/*      */ 
/*      */           
/* 1141 */           if (na.kind != 4) {
/*      */             
/* 1143 */             if (na.kind != 3 && na.kind != 5)
/*      */             {
/* 1145 */               this.packet = na.packet; } 
/* 1146 */             this.throwable = na.throwable;
/*      */           } 
/*      */           
/* 1149 */           switch (na.kind) {
/*      */             case 0:
/*      */             case 7:
/* 1152 */               pushCont(last);
/*      */             
/*      */             case 1:
/* 1155 */               this.next = na.next;
/* 1156 */               if (na.kind == 7 && this.startedSync)
/*      */               {
/*      */                 
/* 1159 */                 return false;
/*      */               }
/*      */               break;
/*      */             case 5:
/*      */             case 6:
/* 1164 */               abortResponse = true;
/* 1165 */               if (isTraceEnabled()) {
/* 1166 */                 LOGGER.log(Level.FINE, "Fiber {0} is aborting a response due to exception: {1}", new Object[] { this, na.throwable });
/*      */               }
/*      */             case 2:
/*      */             case 3:
/* 1170 */               this.next = null;
/*      */               break;
/*      */             case 4:
/* 1173 */               if (this.next != null)
/*      */               {
/*      */                 
/* 1176 */                 pushCont(last);
/*      */               }
/* 1178 */               this.next = na.next;
/* 1179 */               if (suspend(isRequireUnlock, na.onExitRunnable))
/* 1180 */                 return true; 
/*      */               break;
/*      */             default:
/* 1183 */               throw new AssertionError();
/*      */           } 
/* 1185 */         } catch (RuntimeException t) {
/* 1186 */           if (traceEnabled)
/* 1187 */             LOGGER.log(Level.FINER, getName() + " Caught " + t + ". Start stack unwinding", t); 
/* 1188 */           this.throwable = t;
/* 1189 */         } catch (Error t) {
/* 1190 */           if (traceEnabled)
/* 1191 */             LOGGER.log(Level.FINER, getName() + " Caught " + t + ". Start stack unwinding", t); 
/* 1192 */           this.throwable = t;
/*      */         } 
/*      */         
/* 1195 */         dumpFiberContext("After tube execution");
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1202 */       CURRENT_FIBER.set(old);
/*      */     } 
/*      */     
/* 1205 */     return false;
/*      */   }
/*      */   
/*      */   private void pushCont(Tube tube) {
/* 1209 */     this.conts[this.contsSize++] = tube;
/*      */ 
/*      */     
/* 1212 */     int len = this.conts.length;
/* 1213 */     if (this.contsSize == len) {
/* 1214 */       Tube[] newBuf = new Tube[len * 2];
/* 1215 */       System.arraycopy(this.conts, 0, newBuf, 0, len);
/* 1216 */       this.conts = newBuf;
/*      */     } 
/*      */   }
/*      */   
/*      */   private Tube popCont() {
/* 1221 */     return this.conts[--this.contsSize];
/*      */   }
/*      */   
/*      */   private Tube peekCont() {
/* 1225 */     int index = this.contsSize - 1;
/* 1226 */     if (index >= 0 && index < this.conts.length) {
/* 1227 */       return this.conts[index];
/*      */     }
/* 1229 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetCont(Tube[] conts, int contsSize) {
/* 1238 */     this.conts = conts;
/* 1239 */     this.contsSize = contsSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isReady(List<FiberContextSwitchInterceptor> originalInterceptors) {
/* 1246 */     if (this.synchronous) {
/* 1247 */       while (this.suspendedCount == 1) {
/*      */         try {
/* 1249 */           if (isTraceEnabled()) {
/* 1250 */             LOGGER.log(Level.FINE, "{0} is blocking thread {1}", new Object[] { getName(), Thread.currentThread().getName() });
/*      */           }
/* 1252 */           this.condition.await();
/* 1253 */         } catch (InterruptedException e) {
/*      */ 
/*      */ 
/*      */           
/* 1257 */           this.interrupted = true;
/*      */         } 
/*      */       } 
/* 1260 */       synchronized (this) {
/* 1261 */         return (this.interceptors == originalInterceptors);
/*      */       } 
/*      */     } 
/*      */     
/* 1265 */     if (this.suspendedCount > 0)
/* 1266 */       return false; 
/* 1267 */     synchronized (this) {
/* 1268 */       return (this.interceptors == originalInterceptors);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private String getName() {
/* 1274 */     return "engine-" + this.owner.id + "fiber-" + this.id;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1279 */     return getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Packet getPacket() {
/* 1291 */     return this.packet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletionCallback getCompletionCallback() {
/* 1300 */     return this.completionCallback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCompletionCallback(CompletionCallback completionCallback) {
/* 1309 */     this.completionCallback = completionCallback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSynchronous() {
/* 1335 */     return (current()).synchronous;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStartedSync() {
/* 1348 */     return this.startedSync;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static Fiber current() {
/* 1362 */     Fiber fiber = CURRENT_FIBER.get();
/* 1363 */     if (fiber == null)
/* 1364 */       throw new IllegalStateException("Can be only used from fibers"); 
/* 1365 */     return fiber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Fiber getCurrentIfSet() {
/* 1372 */     return CURRENT_FIBER.get();
/*      */   }
/*      */   
/* 1375 */   private static final ThreadLocal<Fiber> CURRENT_FIBER = new ThreadLocal<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1380 */   private static final AtomicInteger iotaGen = new AtomicInteger();
/*      */   
/*      */   private static boolean isTraceEnabled() {
/* 1383 */     return LOGGER.isLoggable(Level.FINE);
/*      */   }
/*      */   
/* 1386 */   private static final Logger LOGGER = Logger.getLogger(Fiber.class.getName());
/*      */ 
/*      */   
/* 1389 */   private static final ReentrantLock serializedExecutionLock = new ReentrantLock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1395 */   public static volatile boolean serializeExecution = Boolean.getBoolean(Fiber.class.getName() + ".serialize"); private final Set<Component> components;
/*      */   
/* 1397 */   Fiber(Engine engine) { this.components = new CopyOnWriteArraySet<>(); this.owner = engine;
/*      */     this.id = iotaGen.incrementAndGet();
/*      */     if (isTraceEnabled())
/*      */       LOGGER.log(Level.FINE, "{0} created", getName()); 
/* 1401 */     this.contextClassLoader = Thread.currentThread().getContextClassLoader(); } public <S> S getSPI(Class<S> spiType) { for (Component c : this.components) {
/* 1402 */       S spi = (S)c.getSPI(spiType);
/* 1403 */       if (spi != null) {
/* 1404 */         return spi;
/*      */       }
/*      */     } 
/* 1407 */     return null; }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Component> getComponents() {
/* 1412 */     return this.components;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/Fiber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */