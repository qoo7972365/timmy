/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.RequestCanceledException;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*     */ import com.sun.corba.se.pept.transport.ByteBufferPool;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import org.omg.CORBA.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferManagerReadStream
/*     */   implements BufferManagerRead, MarkAndResetHandler
/*     */ {
/*     */   private boolean receivedCancel = false;
/*  42 */   private int cancelReqId = 0;
/*     */   
/*     */   private boolean endOfStream = true;
/*     */   
/*  46 */   private BufferQueue fragmentQueue = new BufferQueue();
/*  47 */   private long FRAGMENT_TIMEOUT = 60000L;
/*     */ 
/*     */   
/*     */   private ORB orb;
/*     */   
/*     */   private ORBUtilSystemException wrapper;
/*     */   
/*     */   private boolean debug = false;
/*     */   
/*     */   private boolean markEngaged;
/*     */   
/*     */   private LinkedList fragmentStack;
/*     */   
/*     */   private RestorableInputStream inputStream;
/*     */   
/*     */   private Object streamMemento;
/*     */ 
/*     */   
/*     */   public void cancelProcessing(int paramInt) {
/*  66 */     synchronized (this.fragmentQueue) {
/*  67 */       this.receivedCancel = true;
/*  68 */       this.cancelReqId = paramInt;
/*  69 */       this.fragmentQueue.notify();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processFragment(ByteBuffer paramByteBuffer, FragmentMessage paramFragmentMessage) {
/*  76 */     ByteBufferWithInfo byteBufferWithInfo = new ByteBufferWithInfo((ORB)this.orb, paramByteBuffer, paramFragmentMessage.getHeaderLength());
/*     */     
/*  78 */     synchronized (this.fragmentQueue) {
/*  79 */       if (this.debug) {
/*     */ 
/*     */         
/*  82 */         int i = System.identityHashCode(paramByteBuffer);
/*  83 */         StringBuffer stringBuffer = new StringBuffer(80);
/*  84 */         stringBuffer.append("processFragment() - queueing ByteBuffer id (");
/*  85 */         stringBuffer.append(i).append(") to fragment queue.");
/*  86 */         String str = stringBuffer.toString();
/*  87 */         dprint(str);
/*     */       } 
/*  89 */       this.fragmentQueue.enqueue(byteBufferWithInfo);
/*  90 */       this.endOfStream = !paramFragmentMessage.moreFragmentsToFollow();
/*  91 */       this.fragmentQueue.notify();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufferWithInfo underflow(ByteBufferWithInfo paramByteBufferWithInfo) {
/*  98 */     ByteBufferWithInfo byteBufferWithInfo = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     synchronized (this.fragmentQueue) {
/*     */       
/* 105 */       if (this.receivedCancel) {
/* 106 */         throw new RequestCanceledException(this.cancelReqId);
/*     */       }
/*     */       
/* 109 */       while (this.fragmentQueue.size() == 0) {
/*     */         
/* 111 */         if (this.endOfStream) {
/* 112 */           throw this.wrapper.endOfStream();
/*     */         }
/*     */         
/* 115 */         boolean bool = false;
/*     */         try {
/* 117 */           this.fragmentQueue.wait(this.FRAGMENT_TIMEOUT);
/* 118 */         } catch (InterruptedException interruptedException) {
/* 119 */           bool = true;
/*     */         } 
/*     */         
/* 122 */         if (!bool && this.fragmentQueue.size() == 0) {
/* 123 */           throw this.wrapper.bufferReadManagerTimeout();
/*     */         }
/*     */         
/* 126 */         if (this.receivedCancel) {
/* 127 */           throw new RequestCanceledException(this.cancelReqId);
/*     */         }
/*     */       } 
/*     */       
/* 131 */       byteBufferWithInfo = this.fragmentQueue.dequeue();
/* 132 */       byteBufferWithInfo.fragmented = true;
/*     */       
/* 134 */       if (this.debug) {
/*     */ 
/*     */         
/* 137 */         int i = System.identityHashCode(byteBufferWithInfo.byteBuffer);
/* 138 */         StringBuffer stringBuffer = new StringBuffer(80);
/* 139 */         stringBuffer.append("underflow() - dequeued ByteBuffer id (");
/* 140 */         stringBuffer.append(i).append(") from fragment queue.");
/* 141 */         String str = stringBuffer.toString();
/* 142 */         dprint(str);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       if (!this.markEngaged && paramByteBufferWithInfo != null && paramByteBufferWithInfo.byteBuffer != null) {
/*     */         
/* 150 */         ByteBufferPool byteBufferPool = getByteBufferPool();
/*     */         
/* 152 */         if (this.debug) {
/*     */ 
/*     */           
/* 155 */           int i = System.identityHashCode(paramByteBufferWithInfo.byteBuffer);
/* 156 */           StringBuffer stringBuffer = new StringBuffer(80);
/* 157 */           stringBuffer.append("underflow() - releasing ByteBuffer id (");
/* 158 */           stringBuffer.append(i).append(") to ByteBufferPool.");
/* 159 */           String str = stringBuffer.toString();
/* 160 */           dprint(str);
/*     */         } 
/*     */         
/* 163 */         byteBufferPool.releaseByteBuffer(paramByteBufferWithInfo.byteBuffer);
/* 164 */         paramByteBufferWithInfo.byteBuffer = null;
/* 165 */         paramByteBufferWithInfo = null;
/*     */       } 
/*     */     } 
/* 168 */     return byteBufferWithInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(Message paramMessage) {
/* 175 */     if (paramMessage != null) {
/* 176 */       this.endOfStream = !paramMessage.moreFragmentsToFollow();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 183 */     int i = 0;
/*     */ 
/*     */     
/* 186 */     if (this.fragmentQueue != null) {
/*     */       
/* 188 */       synchronized (this.fragmentQueue) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 197 */         if (paramByteBufferWithInfo != null)
/*     */         {
/* 199 */           i = System.identityHashCode(paramByteBufferWithInfo.byteBuffer);
/*     */         }
/*     */         
/* 202 */         ByteBufferWithInfo byteBufferWithInfo = null;
/* 203 */         ByteBufferPool byteBufferPool = getByteBufferPool();
/* 204 */         while (this.fragmentQueue.size() != 0) {
/*     */           
/* 206 */           byteBufferWithInfo = this.fragmentQueue.dequeue();
/* 207 */           if (byteBufferWithInfo != null && byteBufferWithInfo.byteBuffer != null) {
/*     */             
/* 209 */             int j = System.identityHashCode(byteBufferWithInfo.byteBuffer);
/* 210 */             if (i != j)
/*     */             {
/* 212 */               if (this.debug) {
/*     */ 
/*     */                 
/* 215 */                 StringBuffer stringBuffer = new StringBuffer(80);
/* 216 */                 stringBuffer.append("close() - fragmentQueue is ")
/* 217 */                   .append("releasing ByteBuffer id (")
/* 218 */                   .append(j).append(") to ")
/* 219 */                   .append("ByteBufferPool.");
/* 220 */                 String str = stringBuffer.toString();
/* 221 */                 dprint(str);
/*     */               } 
/*     */             }
/* 224 */             byteBufferPool.releaseByteBuffer(byteBufferWithInfo.byteBuffer);
/*     */           } 
/*     */         } 
/*     */       } 
/* 228 */       this.fragmentQueue = null;
/*     */     } 
/*     */ 
/*     */     
/* 232 */     if (this.fragmentStack != null && this.fragmentStack.size() != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 241 */       if (paramByteBufferWithInfo != null)
/*     */       {
/* 243 */         i = System.identityHashCode(paramByteBufferWithInfo.byteBuffer);
/*     */       }
/*     */       
/* 246 */       ByteBufferWithInfo byteBufferWithInfo = null;
/* 247 */       ByteBufferPool byteBufferPool = getByteBufferPool();
/* 248 */       ListIterator<ByteBufferWithInfo> listIterator = this.fragmentStack.listIterator();
/* 249 */       while (listIterator.hasNext()) {
/*     */         
/* 251 */         byteBufferWithInfo = listIterator.next();
/*     */         
/* 253 */         if (byteBufferWithInfo != null && byteBufferWithInfo.byteBuffer != null) {
/*     */           
/* 255 */           int j = System.identityHashCode(byteBufferWithInfo.byteBuffer);
/* 256 */           if (i != j) {
/*     */             
/* 258 */             if (this.debug) {
/*     */ 
/*     */               
/* 261 */               StringBuffer stringBuffer = new StringBuffer(80);
/* 262 */               stringBuffer.append("close() - fragmentStack - releasing ")
/* 263 */                 .append("ByteBuffer id (" + j + ") to ")
/* 264 */                 .append("ByteBufferPool.");
/* 265 */               String str = stringBuffer.toString();
/* 266 */               dprint(str);
/*     */             } 
/* 268 */             byteBufferPool.releaseByteBuffer(byteBufferWithInfo.byteBuffer);
/*     */           } 
/*     */         } 
/*     */       } 
/* 272 */       this.fragmentStack = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBufferPool getByteBufferPool() {
/* 279 */     return this.orb.getByteBufferPool();
/*     */   }
/*     */ 
/*     */   
/*     */   private void dprint(String paramString) {
/* 284 */     ORBUtility.dprint("BufferManagerReadStream", paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   BufferManagerReadStream(ORB paramORB) {
/* 289 */     this.markEngaged = false;
/*     */ 
/*     */ 
/*     */     
/* 293 */     this.fragmentStack = null;
/* 294 */     this.inputStream = null;
/*     */ 
/*     */     
/* 297 */     this.streamMemento = null;
/*     */     this.orb = paramORB;
/*     */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*     */     this.debug = paramORB.transportDebugFlag; } public void mark(RestorableInputStream paramRestorableInputStream) {
/* 301 */     this.inputStream = paramRestorableInputStream;
/* 302 */     this.markEngaged = true;
/*     */ 
/*     */ 
/*     */     
/* 306 */     this.streamMemento = paramRestorableInputStream.createStreamMemento();
/*     */     
/* 308 */     if (this.fragmentStack != null) {
/* 309 */       this.fragmentStack.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fragmentationOccured(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 316 */     if (!this.markEngaged) {
/*     */       return;
/*     */     }
/* 319 */     if (this.fragmentStack == null) {
/* 320 */       this.fragmentStack = new LinkedList();
/*     */     }
/* 322 */     this.fragmentStack.addFirst(new ByteBufferWithInfo(paramByteBufferWithInfo));
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 327 */     if (!this.markEngaged) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 332 */     this.markEngaged = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     if (this.fragmentStack != null && this.fragmentStack.size() != 0) {
/* 338 */       ListIterator<ByteBufferWithInfo> listIterator = this.fragmentStack.listIterator();
/*     */       
/* 340 */       synchronized (this.fragmentQueue) {
/* 341 */         while (listIterator.hasNext()) {
/* 342 */           this.fragmentQueue.push(listIterator.next());
/*     */         }
/*     */       } 
/*     */       
/* 346 */       this.fragmentStack.clear();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 351 */     this.inputStream.restoreInternalState(this.streamMemento);
/*     */   }
/*     */   
/*     */   public MarkAndResetHandler getMarkAndResetHandler() {
/* 355 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/BufferManagerReadStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */