/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.spi.AsynchronousChannelProvider;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class EPollPort
/*     */   extends Port
/*     */ {
/*     */   private static final int MAX_EPOLL_EVENTS = 512;
/*     */   private static final int ENOENT = 2;
/*     */   private final int epfd;
/*     */   private boolean closed;
/*     */   private final int[] sp;
/*  58 */   private final AtomicInteger wakeupCount = new AtomicInteger();
/*     */   
/*     */   private final long address;
/*     */   private final ArrayBlockingQueue<Event> queue;
/*     */   
/*     */   static class Event
/*     */   {
/*     */     final Port.PollableChannel channel;
/*     */     final int events;
/*     */     
/*     */     Event(Port.PollableChannel param1PollableChannel, int param1Int) {
/*  69 */       this.channel = param1PollableChannel;
/*  70 */       this.events = param1Int;
/*     */     }
/*     */     
/*  73 */     Port.PollableChannel channel() { return this.channel; } int events() {
/*  74 */       return this.events;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  80 */   private final Event NEED_TO_POLL = new Event(null, 0);
/*  81 */   private final Event EXECUTE_TASK_OR_SHUTDOWN = new Event(null, 0);
/*     */ 
/*     */ 
/*     */   
/*     */   EPollPort(AsynchronousChannelProvider paramAsynchronousChannelProvider, ThreadPool paramThreadPool) throws IOException {
/*  86 */     super(paramAsynchronousChannelProvider, paramThreadPool);
/*     */ 
/*     */     
/*  89 */     this.epfd = EPoll.epollCreate();
/*     */ 
/*     */     
/*  92 */     int[] arrayOfInt = new int[2];
/*     */     try {
/*  94 */       socketpair(arrayOfInt);
/*     */       
/*  96 */       EPoll.epollCtl(this.epfd, 1, arrayOfInt[0], Net.POLLIN);
/*  97 */     } catch (IOException iOException) {
/*  98 */       close0(this.epfd);
/*  99 */       throw iOException;
/*     */     } 
/* 101 */     this.sp = arrayOfInt;
/*     */ 
/*     */     
/* 104 */     this.address = EPoll.allocatePollArray(512);
/*     */ 
/*     */ 
/*     */     
/* 108 */     this.queue = new ArrayBlockingQueue<>(512);
/* 109 */     this.queue.offer(this.NEED_TO_POLL);
/*     */   }
/*     */   
/*     */   EPollPort start() {
/* 113 */     startThreads(new EventHandlerTask());
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void implClose() {
/* 121 */     synchronized (this) {
/* 122 */       if (this.closed)
/*     */         return; 
/* 124 */       this.closed = true;
/*     */     } 
/* 126 */     EPoll.freePollArray(this.address);
/* 127 */     close0(this.sp[0]);
/* 128 */     close0(this.sp[1]);
/* 129 */     close0(this.epfd);
/*     */   }
/*     */   
/*     */   private void wakeup() {
/* 133 */     if (this.wakeupCount.incrementAndGet() == 1) {
/*     */       
/*     */       try {
/* 136 */         interrupt(this.sp[1]);
/* 137 */       } catch (IOException iOException) {
/* 138 */         throw new AssertionError(iOException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void executeOnHandlerTask(Runnable paramRunnable) {
/* 145 */     synchronized (this) {
/* 146 */       if (this.closed)
/* 147 */         throw new RejectedExecutionException(); 
/* 148 */       offerTask(paramRunnable);
/* 149 */       wakeup();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void shutdownHandlerTasks() {
/* 159 */     int i = threadCount();
/* 160 */     if (i == 0) {
/* 161 */       implClose();
/*     */     } else {
/*     */       
/* 164 */       while (i-- > 0) {
/* 165 */         wakeup();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startPoll(int paramInt1, int paramInt2) {
/* 174 */     int i = EPoll.epollCtl(this.epfd, 3, paramInt1, paramInt2 | 0x40000000);
/* 175 */     if (i == 2)
/* 176 */       i = EPoll.epollCtl(this.epfd, 1, paramInt1, paramInt2 | 0x40000000); 
/* 177 */     if (i != 0) {
/* 178 */       throw new AssertionError();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class EventHandlerTask
/*     */     implements Runnable
/*     */   {
/*     */     private EventHandlerTask() {}
/*     */ 
/*     */ 
/*     */     
/*     */     private EPollPort.Event poll() throws IOException {
/*     */       try {
/*     */         while (true) {
/* 194 */           int i = EPoll.epollWait(EPollPort.this.epfd, EPollPort.this.address, 512);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 201 */           EPollPort.this.fdToChannelLock.readLock().lock();
/*     */           try {
/* 203 */             while (i-- > 0) {
/* 204 */               long l = EPoll.getEvent(EPollPort.this.address, i);
/* 205 */               int j = EPoll.getDescriptor(l);
/*     */ 
/*     */               
/* 208 */               if (j == EPollPort.this.sp[0]) {
/* 209 */                 if (EPollPort.this.wakeupCount.decrementAndGet() == 0)
/*     */                 {
/* 211 */                   EPollPort.drain1(EPollPort.this.sp[0]);
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/* 216 */                 if (i > 0) {
/* 217 */                   EPollPort.this.queue.offer(EPollPort.this.EXECUTE_TASK_OR_SHUTDOWN);
/*     */                   continue;
/*     */                 } 
/* 220 */                 return EPollPort.this.EXECUTE_TASK_OR_SHUTDOWN;
/*     */               } 
/*     */               
/* 223 */               Port.PollableChannel pollableChannel = EPollPort.this.fdToChannel.get(Integer.valueOf(j));
/* 224 */               if (pollableChannel != null) {
/* 225 */                 int k = EPoll.getEvents(l);
/* 226 */                 EPollPort.Event event = new EPollPort.Event(pollableChannel, k);
/*     */ 
/*     */ 
/*     */                 
/* 230 */                 if (i > 0) {
/* 231 */                   EPollPort.this.queue.offer(event); continue;
/*     */                 } 
/* 233 */                 return event;
/*     */               } 
/*     */             } 
/*     */           } finally {
/*     */             
/* 238 */             EPollPort.this.fdToChannelLock.readLock().unlock();
/*     */           }
/*     */         
/*     */         } 
/*     */       } finally {
/*     */         
/* 244 */         EPollPort.this.queue.offer(EPollPort.this.NEED_TO_POLL);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       boolean bool2;
/* 250 */       Invoker.GroupAndInvokeCount groupAndInvokeCount = Invoker.getGroupAndInvokeCount();
/* 251 */       boolean bool1 = (groupAndInvokeCount != null) ? true : false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 321 */     IOUtil.load();
/*     */   }
/*     */   
/*     */   private static native void socketpair(int[] paramArrayOfint) throws IOException;
/*     */   
/*     */   private static native void interrupt(int paramInt) throws IOException;
/*     */   
/*     */   private static native void drain1(int paramInt) throws IOException;
/*     */   
/*     */   private static native void close0(int paramInt);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/EPollPort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */