/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.nio.channels.AsynchronousChannel;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.nio.channels.ShutdownChannelGroupException;
/*     */ import java.security.AccessController;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import sun.misc.InnocuousThread;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Invoker
/*     */ {
/*  43 */   private static final int maxHandlerInvokeCount = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.nio.ch.maxCompletionHandlersOnStack", 16))).intValue();
/*     */ 
/*     */   
/*     */   static class GroupAndInvokeCount
/*     */   {
/*     */     private final AsynchronousChannelGroupImpl group;
/*     */     
/*     */     private int handlerInvokeCount;
/*     */     
/*     */     GroupAndInvokeCount(AsynchronousChannelGroupImpl param1AsynchronousChannelGroupImpl) {
/*  53 */       this.group = param1AsynchronousChannelGroupImpl;
/*     */     }
/*     */     AsynchronousChannelGroupImpl group() {
/*  56 */       return this.group;
/*     */     }
/*     */     int invokeCount() {
/*  59 */       return this.handlerInvokeCount;
/*     */     }
/*     */     void setInvokeCount(int param1Int) {
/*  62 */       this.handlerInvokeCount = param1Int;
/*     */     }
/*     */     void resetInvokeCount() {
/*  65 */       this.handlerInvokeCount = 0;
/*     */     }
/*     */     void incrementInvokeCount() {
/*  68 */       this.handlerInvokeCount++;
/*     */     } }
/*     */   
/*  71 */   private static final ThreadLocal<GroupAndInvokeCount> myGroupAndInvokeCount = new ThreadLocal<GroupAndInvokeCount>()
/*     */     {
/*     */       protected Invoker.GroupAndInvokeCount initialValue() {
/*  74 */         return null;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void bindToGroup(AsynchronousChannelGroupImpl paramAsynchronousChannelGroupImpl) {
/*  82 */     myGroupAndInvokeCount.set(new GroupAndInvokeCount(paramAsynchronousChannelGroupImpl));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static GroupAndInvokeCount getGroupAndInvokeCount() {
/*  89 */     return myGroupAndInvokeCount.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isBoundToAnyGroup() {
/*  96 */     return (myGroupAndInvokeCount.get() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean mayInvokeDirect(GroupAndInvokeCount paramGroupAndInvokeCount, AsynchronousChannelGroupImpl paramAsynchronousChannelGroupImpl) {
/* 107 */     if (paramGroupAndInvokeCount != null && paramGroupAndInvokeCount
/* 108 */       .group() == paramAsynchronousChannelGroupImpl && paramGroupAndInvokeCount
/* 109 */       .invokeCount() < maxHandlerInvokeCount)
/*     */     {
/* 111 */       return true;
/*     */     }
/* 113 */     return false;
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
/*     */   static <V, A> void invokeUnchecked(CompletionHandler<V, ? super A> paramCompletionHandler, A paramA, V paramV, Throwable paramThrowable) {
/* 125 */     if (paramThrowable == null) {
/* 126 */       paramCompletionHandler.completed(paramV, paramA);
/*     */     } else {
/* 128 */       paramCompletionHandler.failed(paramThrowable, paramA);
/*     */     } 
/*     */ 
/*     */     
/* 132 */     Thread.interrupted();
/*     */ 
/*     */     
/* 135 */     if (System.getSecurityManager() != null) {
/* 136 */       Thread thread = Thread.currentThread();
/* 137 */       if (thread instanceof InnocuousThread) {
/* 138 */         GroupAndInvokeCount groupAndInvokeCount = myGroupAndInvokeCount.get();
/* 139 */         ((InnocuousThread)thread).eraseThreadLocals();
/* 140 */         if (groupAndInvokeCount != null) {
/* 141 */           myGroupAndInvokeCount.set(groupAndInvokeCount);
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
/*     */ 
/*     */ 
/*     */   
/*     */   static <V, A> void invokeDirect(GroupAndInvokeCount paramGroupAndInvokeCount, CompletionHandler<V, ? super A> paramCompletionHandler, A paramA, V paramV, Throwable paramThrowable) {
/* 156 */     paramGroupAndInvokeCount.incrementInvokeCount();
/* 157 */     invokeUnchecked(paramCompletionHandler, paramA, paramV, paramThrowable);
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
/*     */   static <V, A> void invoke(AsynchronousChannel paramAsynchronousChannel, CompletionHandler<V, ? super A> paramCompletionHandler, A paramA, V paramV, Throwable paramThrowable) {
/* 171 */     boolean bool1 = false;
/* 172 */     boolean bool2 = false;
/* 173 */     GroupAndInvokeCount groupAndInvokeCount = myGroupAndInvokeCount.get();
/* 174 */     if (groupAndInvokeCount != null) {
/* 175 */       if (groupAndInvokeCount.group() == ((Groupable)paramAsynchronousChannel).group())
/* 176 */         bool2 = true; 
/* 177 */       if (bool2 && groupAndInvokeCount
/* 178 */         .invokeCount() < maxHandlerInvokeCount)
/*     */       {
/*     */         
/* 181 */         bool1 = true;
/*     */       }
/*     */     } 
/* 184 */     if (bool1) {
/* 185 */       invokeDirect(groupAndInvokeCount, paramCompletionHandler, paramA, paramV, paramThrowable);
/*     */     } else {
/*     */       try {
/* 188 */         invokeIndirectly(paramAsynchronousChannel, paramCompletionHandler, paramA, paramV, paramThrowable);
/* 189 */       } catch (RejectedExecutionException rejectedExecutionException) {
/*     */ 
/*     */         
/* 192 */         if (bool2) {
/* 193 */           invokeDirect(groupAndInvokeCount, paramCompletionHandler, paramA, paramV, paramThrowable);
/*     */         } else {
/*     */           
/* 196 */           throw new ShutdownChannelGroupException();
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
/*     */ 
/*     */ 
/*     */   
/*     */   static <V, A> void invokeIndirectly(AsynchronousChannel paramAsynchronousChannel, final CompletionHandler<V, ? super A> handler, final A attachment, final V result, final Throwable exc) {
/*     */     try {
/* 212 */       ((Groupable)paramAsynchronousChannel).group().executeOnPooledThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 215 */               Invoker.GroupAndInvokeCount groupAndInvokeCount = Invoker.myGroupAndInvokeCount.get();
/* 216 */               if (groupAndInvokeCount != null)
/* 217 */                 groupAndInvokeCount.setInvokeCount(1); 
/* 218 */               Invoker.invokeUnchecked(handler, attachment, result, exc);
/*     */             }
/*     */           });
/* 221 */     } catch (RejectedExecutionException rejectedExecutionException) {
/* 222 */       throw new ShutdownChannelGroupException();
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
/*     */   static <V, A> void invokeIndirectly(final CompletionHandler<V, ? super A> handler, final A attachment, final V value, final Throwable exc, Executor paramExecutor) {
/*     */     try {
/* 236 */       paramExecutor.execute(new Runnable() {
/*     */             public void run() {
/* 238 */               Invoker.invokeUnchecked(handler, attachment, value, exc);
/*     */             }
/*     */           });
/* 241 */     } catch (RejectedExecutionException rejectedExecutionException) {
/* 242 */       throw new ShutdownChannelGroupException();
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
/*     */   static void invokeOnThreadInThreadPool(Groupable paramGroupable, Runnable paramRunnable) {
/*     */     boolean bool;
/* 255 */     GroupAndInvokeCount groupAndInvokeCount = myGroupAndInvokeCount.get();
/* 256 */     AsynchronousChannelGroupImpl asynchronousChannelGroupImpl = paramGroupable.group();
/* 257 */     if (groupAndInvokeCount == null) {
/* 258 */       bool = false;
/*     */     } else {
/* 260 */       bool = (groupAndInvokeCount.group == asynchronousChannelGroupImpl) ? true : false;
/*     */     } 
/*     */     try {
/* 263 */       if (bool) {
/* 264 */         paramRunnable.run();
/*     */       } else {
/* 266 */         asynchronousChannelGroupImpl.executeOnPooledThread(paramRunnable);
/*     */       } 
/* 268 */     } catch (RejectedExecutionException rejectedExecutionException) {
/* 269 */       throw new ShutdownChannelGroupException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <V, A> void invokeUnchecked(PendingFuture<V, A> paramPendingFuture) {
/* 278 */     assert paramPendingFuture.isDone();
/* 279 */     CompletionHandler<V, ? super A> completionHandler = paramPendingFuture.handler();
/* 280 */     if (completionHandler != null) {
/* 281 */       invokeUnchecked(completionHandler, paramPendingFuture
/* 282 */           .attachment(), paramPendingFuture
/* 283 */           .value(), paramPendingFuture
/* 284 */           .exception());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <V, A> void invoke(PendingFuture<V, A> paramPendingFuture) {
/* 294 */     assert paramPendingFuture.isDone();
/* 295 */     CompletionHandler<V, ? super A> completionHandler = paramPendingFuture.handler();
/* 296 */     if (completionHandler != null) {
/* 297 */       invoke(paramPendingFuture.channel(), completionHandler, paramPendingFuture
/*     */           
/* 299 */           .attachment(), paramPendingFuture
/* 300 */           .value(), paramPendingFuture
/* 301 */           .exception());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <V, A> void invokeIndirectly(PendingFuture<V, A> paramPendingFuture) {
/* 310 */     assert paramPendingFuture.isDone();
/* 311 */     CompletionHandler<V, ? super A> completionHandler = paramPendingFuture.handler();
/* 312 */     if (completionHandler != null)
/* 313 */       invokeIndirectly(paramPendingFuture.channel(), completionHandler, paramPendingFuture
/*     */           
/* 315 */           .attachment(), paramPendingFuture
/* 316 */           .value(), paramPendingFuture
/* 317 */           .exception()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/Invoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */