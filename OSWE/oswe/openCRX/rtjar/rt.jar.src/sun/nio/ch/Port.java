/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.ShutdownChannelGroupException;
/*     */ import java.nio.channels.spi.AsynchronousChannelProvider;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class Port
/*     */   extends AsynchronousChannelGroupImpl
/*     */ {
/*  52 */   protected final ReadWriteLock fdToChannelLock = new ReentrantReadWriteLock();
/*  53 */   protected final Map<Integer, PollableChannel> fdToChannel = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   Port(AsynchronousChannelProvider paramAsynchronousChannelProvider, ThreadPool paramThreadPool) {
/*  58 */     super(paramAsynchronousChannelProvider, paramThreadPool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void register(int paramInt, PollableChannel paramPollableChannel) {
/*  65 */     this.fdToChannelLock.writeLock().lock();
/*     */     try {
/*  67 */       if (isShutdown())
/*  68 */         throw new ShutdownChannelGroupException(); 
/*  69 */       this.fdToChannel.put(Integer.valueOf(paramInt), paramPollableChannel);
/*     */     } finally {
/*  71 */       this.fdToChannelLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preUnregister(int paramInt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void unregister(int paramInt) {
/*  87 */     boolean bool = false;
/*     */     
/*  89 */     preUnregister(paramInt);
/*     */     
/*  91 */     this.fdToChannelLock.writeLock().lock();
/*     */     try {
/*  93 */       this.fdToChannel.remove(Integer.valueOf(paramInt));
/*     */ 
/*     */       
/*  96 */       if (this.fdToChannel.isEmpty()) {
/*  97 */         bool = true;
/*     */       }
/*     */     } finally {
/* 100 */       this.fdToChannelLock.writeLock().unlock();
/*     */     } 
/*     */ 
/*     */     
/* 104 */     if (bool && isShutdown()) {
/*     */       try {
/* 106 */         shutdownNow();
/* 107 */       } catch (IOException iOException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void startPoll(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean isEmpty() {
/* 118 */     this.fdToChannelLock.writeLock().lock();
/*     */     try {
/* 120 */       return this.fdToChannel.isEmpty();
/*     */     } finally {
/* 122 */       this.fdToChannelLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   final Object attachForeignChannel(final Channel channel, FileDescriptor paramFileDescriptor) {
/* 128 */     int i = IOUtil.fdVal(paramFileDescriptor);
/* 129 */     register(i, new PollableChannel()
/*     */         {
/*     */           public void close() throws IOException {
/* 132 */             channel.close();
/*     */           } public void onEvent(int param1Int, boolean param1Boolean) {}
/*     */         });
/* 135 */     return Integer.valueOf(i);
/*     */   }
/*     */ 
/*     */   
/*     */   final void detachForeignChannel(Object paramObject) {
/* 140 */     unregister(((Integer)paramObject).intValue());
/*     */   }
/*     */   
/*     */   static interface PollableChannel
/*     */     extends Closeable {
/*     */     void onEvent(int param1Int, boolean param1Boolean);
/*     */   }
/*     */   
/*     */   final void closeAllChannels() {
/*     */     byte b;
/* 150 */     PollableChannel[] arrayOfPollableChannel = new PollableChannel[128];
/*     */ 
/*     */     
/*     */     do {
/* 154 */       this.fdToChannelLock.writeLock().lock();
/* 155 */       b = 0;
/*     */       try {
/* 157 */         for (Integer integer : this.fdToChannel.keySet()) {
/* 158 */           arrayOfPollableChannel[b++] = this.fdToChannel.get(integer);
/* 159 */           if (b >= '')
/*     */             break; 
/*     */         } 
/*     */       } finally {
/* 163 */         this.fdToChannelLock.writeLock().unlock();
/*     */       } 
/*     */ 
/*     */       
/* 167 */       for (byte b1 = 0; b1 < b; b1++) {
/*     */         try {
/* 169 */           arrayOfPollableChannel[b1].close();
/* 170 */         } catch (IOException iOException) {}
/*     */       } 
/* 172 */     } while (b > 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/Port.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */