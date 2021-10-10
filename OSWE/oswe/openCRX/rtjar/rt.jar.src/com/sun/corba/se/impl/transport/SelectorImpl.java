/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.Acceptor;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.pept.transport.EventHandler;
/*     */ import com.sun.corba.se.pept.transport.ListenerThread;
/*     */ import com.sun.corba.se.pept.transport.ReaderThread;
/*     */ import com.sun.corba.se.pept.transport.Selector;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.NoSuchWorkQueueException;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ClosedSelectorException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SelectorImpl
/*     */   extends Thread
/*     */   implements Selector
/*     */ {
/*     */   private ORB orb;
/*     */   private Selector selector;
/*     */   private long timeout;
/*     */   private List deferredRegistrations;
/*     */   private List interestOpsList;
/*     */   private HashMap listenerThreads;
/*     */   private Map readerThreads;
/*     */   private boolean selectorStarted;
/*     */   private volatile boolean closed;
/*     */   private ORBUtilSystemException wrapper;
/*     */   
/*     */   public SelectorImpl(ORB paramORB) {
/*  82 */     this.orb = paramORB;
/*  83 */     this.selector = null;
/*  84 */     this.selectorStarted = false;
/*  85 */     this.timeout = 60000L;
/*  86 */     this.deferredRegistrations = new ArrayList();
/*  87 */     this.interestOpsList = new ArrayList();
/*  88 */     this.listenerThreads = new HashMap<>();
/*  89 */     this.readerThreads = Collections.synchronizedMap(new HashMap<>());
/*  90 */     this.closed = false;
/*  91 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.transport");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTimeout(long paramLong) {
/*  96 */     this.timeout = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTimeout() {
/* 101 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerInterestOps(EventHandler paramEventHandler) {
/* 106 */     if (this.orb.transportDebugFlag) {
/* 107 */       dprint(".registerInterestOps:-> " + paramEventHandler);
/*     */     }
/*     */     
/* 110 */     SelectionKey selectionKey = paramEventHandler.getSelectionKey();
/* 111 */     if (selectionKey.isValid()) {
/* 112 */       int i = paramEventHandler.getInterestOps();
/* 113 */       SelectionKeyAndOp selectionKeyAndOp = new SelectionKeyAndOp(selectionKey, i);
/* 114 */       synchronized (this.interestOpsList) {
/* 115 */         this.interestOpsList.add(selectionKeyAndOp);
/*     */       } 
/*     */       
/*     */       try {
/* 119 */         if (this.selector != null)
/*     */         {
/* 121 */           this.selector.wakeup();
/*     */         }
/* 123 */       } catch (Throwable throwable) {
/* 124 */         if (this.orb.transportDebugFlag) {
/* 125 */           dprint(".registerInterestOps: selector.wakeup: ", throwable);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 130 */       this.wrapper.selectionKeyInvalid(paramEventHandler.toString());
/* 131 */       if (this.orb.transportDebugFlag) {
/* 132 */         dprint(".registerInterestOps: EventHandler SelectionKey not valid " + paramEventHandler);
/*     */       }
/*     */     } 
/*     */     
/* 136 */     if (this.orb.transportDebugFlag) {
/* 137 */       dprint(".registerInterestOps:<- ");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerForEvent(EventHandler paramEventHandler) {
/* 143 */     if (this.orb.transportDebugFlag) {
/* 144 */       dprint(".registerForEvent: " + paramEventHandler);
/*     */     }
/*     */     
/* 147 */     if (isClosed()) {
/* 148 */       if (this.orb.transportDebugFlag) {
/* 149 */         dprint(".registerForEvent: closed: " + paramEventHandler);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 154 */     if (paramEventHandler.shouldUseSelectThreadToWait()) {
/* 155 */       synchronized (this.deferredRegistrations) {
/* 156 */         this.deferredRegistrations.add(paramEventHandler);
/*     */       } 
/* 158 */       if (!this.selectorStarted) {
/* 159 */         startSelector();
/*     */       }
/* 161 */       this.selector.wakeup();
/*     */       
/*     */       return;
/*     */     } 
/* 165 */     switch (paramEventHandler.getInterestOps()) {
/*     */       case 16:
/* 167 */         createListenerThread(paramEventHandler);
/*     */         return;
/*     */       case 1:
/* 170 */         createReaderThread(paramEventHandler);
/*     */         return;
/*     */     } 
/* 173 */     if (this.orb.transportDebugFlag) {
/* 174 */       dprint(".registerForEvent: default: " + paramEventHandler);
/*     */     }
/* 176 */     throw new RuntimeException("SelectorImpl.registerForEvent: unknown interest ops");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterForEvent(EventHandler paramEventHandler) {
/* 183 */     if (this.orb.transportDebugFlag) {
/* 184 */       dprint(".unregisterForEvent: " + paramEventHandler);
/*     */     }
/*     */     
/* 187 */     if (isClosed()) {
/* 188 */       if (this.orb.transportDebugFlag) {
/* 189 */         dprint(".unregisterForEvent: closed: " + paramEventHandler);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 194 */     if (paramEventHandler.shouldUseSelectThreadToWait()) {
/*     */       SelectionKey selectionKey;
/* 196 */       synchronized (this.deferredRegistrations) {
/* 197 */         selectionKey = paramEventHandler.getSelectionKey();
/*     */       } 
/* 199 */       if (selectionKey != null) {
/* 200 */         selectionKey.cancel();
/*     */       }
/* 202 */       if (this.selector != null) {
/* 203 */         this.selector.wakeup();
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 208 */     switch (paramEventHandler.getInterestOps()) {
/*     */       case 16:
/* 210 */         destroyListenerThread(paramEventHandler);
/*     */         return;
/*     */       case 1:
/* 213 */         destroyReaderThread(paramEventHandler);
/*     */         return;
/*     */     } 
/* 216 */     if (this.orb.transportDebugFlag) {
/* 217 */       dprint(".unregisterForEvent: default: " + paramEventHandler);
/*     */     }
/* 219 */     throw new RuntimeException("SelectorImpl.uregisterForEvent: unknown interest ops");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 226 */     if (this.orb.transportDebugFlag) {
/* 227 */       dprint(".close");
/*     */     }
/*     */     
/* 230 */     if (isClosed()) {
/* 231 */       if (this.orb.transportDebugFlag) {
/* 232 */         dprint(".close: already closed");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 237 */     setClosed(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     Iterator<ListenerThread> iterator = this.listenerThreads.values().iterator();
/* 244 */     while (iterator.hasNext()) {
/* 245 */       ListenerThread listenerThread = iterator.next();
/* 246 */       listenerThread.close();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 251 */     iterator = this.readerThreads.values().iterator();
/* 252 */     while (iterator.hasNext()) {
/* 253 */       ReaderThread readerThread = (ReaderThread)iterator.next();
/* 254 */       readerThread.close();
/*     */     } 
/*     */     
/* 257 */     clearDeferredRegistrations();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 262 */       if (this.selector != null)
/*     */       {
/* 264 */         this.selector.wakeup();
/*     */       }
/* 266 */     } catch (Throwable throwable) {
/* 267 */       if (this.orb.transportDebugFlag) {
/* 268 */         dprint(".close: selector.wakeup: ", throwable);
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
/*     */   public void run() {
/* 280 */     setName("SelectorThread");
/* 281 */     while (!this.closed) {
/*     */       try {
/* 283 */         int i = 0;
/* 284 */         if (this.timeout == 0L && this.orb.transportDebugFlag) {
/* 285 */           dprint(".run: Beginning of selection cycle");
/*     */         }
/* 287 */         handleDeferredRegistrations();
/* 288 */         enableInterestOps();
/*     */         try {
/* 290 */           i = this.selector.select(this.timeout);
/* 291 */         } catch (IOException iOException) {
/* 292 */           if (this.orb.transportDebugFlag) {
/* 293 */             dprint(".run: selector.select: ", iOException);
/*     */           }
/* 295 */         } catch (ClosedSelectorException closedSelectorException) {
/* 296 */           if (this.orb.transportDebugFlag) {
/* 297 */             dprint(".run: selector.select: ", closedSelectorException);
/*     */           }
/*     */           break;
/*     */         } 
/* 301 */         if (this.closed) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 312 */         Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
/* 313 */         if (this.orb.transportDebugFlag && 
/* 314 */           iterator.hasNext()) {
/* 315 */           dprint(".run: n = " + i);
/*     */         }
/*     */         
/* 318 */         while (iterator.hasNext()) {
/* 319 */           SelectionKey selectionKey = iterator.next();
/* 320 */           iterator.remove();
/*     */           
/* 322 */           EventHandler eventHandler = (EventHandler)selectionKey.attachment();
/*     */           try {
/* 324 */             eventHandler.handleEvent();
/* 325 */           } catch (Throwable throwable) {
/* 326 */             if (this.orb.transportDebugFlag) {
/* 327 */               dprint(".run: eventHandler.handleEvent", throwable);
/*     */             }
/*     */           } 
/*     */         } 
/* 331 */         if (this.timeout == 0L && this.orb.transportDebugFlag) {
/* 332 */           dprint(".run: End of selection cycle");
/*     */         }
/* 334 */       } catch (Throwable throwable) {
/*     */ 
/*     */         
/* 337 */         if (this.orb.transportDebugFlag) {
/* 338 */           dprint(".run: ignoring", throwable);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     try {
/* 343 */       if (this.selector != null) {
/* 344 */         if (this.orb.transportDebugFlag) {
/* 345 */           dprint(".run: selector.close ");
/*     */         }
/* 347 */         this.selector.close();
/*     */       } 
/* 349 */     } catch (Throwable throwable) {
/* 350 */       if (this.orb.transportDebugFlag) {
/* 351 */         dprint(".run: selector.close: ", throwable);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void clearDeferredRegistrations() {
/* 362 */     synchronized (this.deferredRegistrations) {
/* 363 */       int i = this.deferredRegistrations.size();
/* 364 */       if (this.orb.transportDebugFlag) {
/* 365 */         dprint(".clearDeferredRegistrations:deferred list size == " + i);
/*     */       }
/* 367 */       for (byte b = 0; b < i; b++) {
/*     */         
/* 369 */         EventHandler eventHandler = this.deferredRegistrations.get(b);
/* 370 */         if (this.orb.transportDebugFlag) {
/* 371 */           dprint(".clearDeferredRegistrations: " + eventHandler);
/*     */         }
/* 373 */         SelectableChannel selectableChannel = eventHandler.getChannel();
/* 374 */         SelectionKey selectionKey = null;
/*     */         
/*     */         try {
/* 377 */           if (this.orb.transportDebugFlag) {
/* 378 */             dprint(".clearDeferredRegistrations:close channel == " + selectableChannel);
/*     */             
/* 380 */             dprint(".clearDeferredRegistrations:close channel class == " + selectableChannel
/* 381 */                 .getClass().getName());
/*     */           } 
/* 383 */           selectableChannel.close();
/* 384 */           selectionKey = eventHandler.getSelectionKey();
/* 385 */           if (selectionKey != null) {
/* 386 */             selectionKey.cancel();
/* 387 */             selectionKey.attach(null);
/*     */           } 
/* 389 */         } catch (IOException iOException) {
/* 390 */           if (this.orb.transportDebugFlag) {
/* 391 */             dprint(".clearDeferredRegistrations: ", iOException);
/*     */           }
/*     */         } 
/*     */       } 
/* 395 */       this.deferredRegistrations.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized boolean isClosed() {
/* 401 */     return this.closed;
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void setClosed(boolean paramBoolean) {
/* 406 */     this.closed = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   private void startSelector() {
/*     */     try {
/* 412 */       this.selector = Selector.open();
/* 413 */     } catch (IOException iOException) {
/* 414 */       if (this.orb.transportDebugFlag) {
/* 415 */         dprint(".startSelector: Selector.open: IOException: ", iOException);
/*     */       }
/*     */       
/* 418 */       RuntimeException runtimeException = new RuntimeException(".startSelector: Selector.open exception");
/*     */       
/* 420 */       runtimeException.initCause(iOException);
/* 421 */       throw runtimeException;
/*     */     } 
/* 423 */     setDaemon(true);
/* 424 */     start();
/* 425 */     this.selectorStarted = true;
/* 426 */     if (this.orb.transportDebugFlag) {
/* 427 */       dprint(".startSelector: selector.start completed.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleDeferredRegistrations() {
/* 433 */     synchronized (this.deferredRegistrations) {
/* 434 */       int i = this.deferredRegistrations.size();
/* 435 */       for (byte b = 0; b < i; b++) {
/*     */         
/* 437 */         EventHandler eventHandler = this.deferredRegistrations.get(b);
/* 438 */         if (this.orb.transportDebugFlag) {
/* 439 */           dprint(".handleDeferredRegistrations: " + eventHandler);
/*     */         }
/* 441 */         SelectableChannel selectableChannel = eventHandler.getChannel();
/* 442 */         SelectionKey selectionKey = null;
/*     */         
/*     */         try {
/* 445 */           selectionKey = selectableChannel.register(this.selector, eventHandler
/* 446 */               .getInterestOps(), eventHandler);
/*     */         }
/* 448 */         catch (ClosedChannelException closedChannelException) {
/* 449 */           if (this.orb.transportDebugFlag) {
/* 450 */             dprint(".handleDeferredRegistrations: ", closedChannelException);
/*     */           }
/*     */         } 
/* 453 */         eventHandler.setSelectionKey(selectionKey);
/*     */       } 
/* 455 */       this.deferredRegistrations.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void enableInterestOps() {
/* 461 */     synchronized (this.interestOpsList) {
/* 462 */       int i = this.interestOpsList.size();
/* 463 */       if (i > 0) {
/* 464 */         if (this.orb.transportDebugFlag) {
/* 465 */           dprint(".enableInterestOps:->");
/*     */         }
/* 467 */         SelectionKey selectionKey = null;
/* 468 */         SelectionKeyAndOp selectionKeyAndOp = null;
/* 469 */         int j = 0;
/* 470 */         for (byte b = 0; b < i; b++) {
/* 471 */           selectionKeyAndOp = this.interestOpsList.get(b);
/* 472 */           selectionKey = selectionKeyAndOp.selectionKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 484 */           if (selectionKey.isValid()) {
/* 485 */             if (this.orb.transportDebugFlag) {
/* 486 */               dprint(".enableInterestOps: " + selectionKeyAndOp);
/*     */             }
/* 488 */             int k = selectionKeyAndOp.keyOp;
/* 489 */             j = selectionKey.interestOps();
/* 490 */             selectionKey.interestOps(j | k);
/*     */           } 
/*     */         } 
/* 493 */         this.interestOpsList.clear();
/* 494 */         if (this.orb.transportDebugFlag) {
/* 495 */           dprint(".enableInterestOps:<-");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createListenerThread(EventHandler paramEventHandler) {
/*     */     NoSuchWorkQueueException noSuchWorkQueueException;
/* 503 */     if (this.orb.transportDebugFlag) {
/* 504 */       dprint(".createListenerThread: " + paramEventHandler);
/*     */     }
/* 506 */     Acceptor acceptor = paramEventHandler.getAcceptor();
/* 507 */     ListenerThreadImpl listenerThreadImpl = new ListenerThreadImpl(this.orb, acceptor, this);
/*     */     
/* 509 */     this.listenerThreads.put(paramEventHandler, listenerThreadImpl);
/* 510 */     NoSuchThreadPoolException noSuchThreadPoolException = null;
/*     */     try {
/* 512 */       this.orb.getThreadPoolManager().getThreadPool(0)
/* 513 */         .getWorkQueue(0).addWork(listenerThreadImpl);
/* 514 */     } catch (NoSuchThreadPoolException noSuchThreadPoolException1) {
/* 515 */       noSuchThreadPoolException = noSuchThreadPoolException1;
/* 516 */     } catch (NoSuchWorkQueueException noSuchWorkQueueException1) {
/* 517 */       noSuchWorkQueueException = noSuchWorkQueueException1;
/*     */     } 
/* 519 */     if (noSuchWorkQueueException != null) {
/* 520 */       RuntimeException runtimeException = new RuntimeException(noSuchWorkQueueException.toString());
/* 521 */       runtimeException.initCause((Throwable)noSuchWorkQueueException);
/* 522 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void destroyListenerThread(EventHandler paramEventHandler) {
/* 528 */     if (this.orb.transportDebugFlag) {
/* 529 */       dprint(".destroyListenerThread: " + paramEventHandler);
/*     */     }
/*     */     
/* 532 */     ListenerThread listenerThread = (ListenerThread)this.listenerThreads.get(paramEventHandler);
/* 533 */     if (listenerThread == null) {
/* 534 */       if (this.orb.transportDebugFlag) {
/* 535 */         dprint(".destroyListenerThread: cannot find ListenerThread - ignoring.");
/*     */       }
/*     */       return;
/*     */     } 
/* 539 */     this.listenerThreads.remove(paramEventHandler);
/* 540 */     listenerThread.close();
/*     */   }
/*     */   
/*     */   private void createReaderThread(EventHandler paramEventHandler) {
/*     */     NoSuchWorkQueueException noSuchWorkQueueException;
/* 545 */     if (this.orb.transportDebugFlag) {
/* 546 */       dprint(".createReaderThread: " + paramEventHandler);
/*     */     }
/* 548 */     Connection connection = paramEventHandler.getConnection();
/* 549 */     ReaderThreadImpl readerThreadImpl = new ReaderThreadImpl(this.orb, connection, this);
/*     */     
/* 551 */     this.readerThreads.put(paramEventHandler, readerThreadImpl);
/* 552 */     NoSuchThreadPoolException noSuchThreadPoolException = null;
/*     */     try {
/* 554 */       this.orb.getThreadPoolManager().getThreadPool(0)
/* 555 */         .getWorkQueue(0).addWork(readerThreadImpl);
/* 556 */     } catch (NoSuchThreadPoolException noSuchThreadPoolException1) {
/* 557 */       noSuchThreadPoolException = noSuchThreadPoolException1;
/* 558 */     } catch (NoSuchWorkQueueException noSuchWorkQueueException1) {
/* 559 */       noSuchWorkQueueException = noSuchWorkQueueException1;
/*     */     } 
/* 561 */     if (noSuchWorkQueueException != null) {
/* 562 */       RuntimeException runtimeException = new RuntimeException(noSuchWorkQueueException.toString());
/* 563 */       runtimeException.initCause((Throwable)noSuchWorkQueueException);
/* 564 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void destroyReaderThread(EventHandler paramEventHandler) {
/* 570 */     if (this.orb.transportDebugFlag) {
/* 571 */       dprint(".destroyReaderThread: " + paramEventHandler);
/*     */     }
/*     */     
/* 574 */     ReaderThread readerThread = (ReaderThread)this.readerThreads.get(paramEventHandler);
/* 575 */     if (readerThread == null) {
/* 576 */       if (this.orb.transportDebugFlag) {
/* 577 */         dprint(".destroyReaderThread: cannot find ReaderThread - ignoring.");
/*     */       }
/*     */       return;
/*     */     } 
/* 581 */     this.readerThreads.remove(paramEventHandler);
/* 582 */     readerThread.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private void dprint(String paramString) {
/* 587 */     ORBUtility.dprint("SelectorImpl", paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString, Throwable paramThrowable) {
/* 592 */     dprint(paramString);
/* 593 */     paramThrowable.printStackTrace(System.out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SelectionKeyAndOp
/*     */   {
/*     */     public int keyOp;
/*     */ 
/*     */     
/*     */     public SelectionKey selectionKey;
/*     */ 
/*     */ 
/*     */     
/*     */     public SelectionKeyAndOp(SelectionKey param1SelectionKey, int param1Int) {
/* 609 */       this.selectionKey = param1SelectionKey;
/* 610 */       this.keyOp = param1Int;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/SelectorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */