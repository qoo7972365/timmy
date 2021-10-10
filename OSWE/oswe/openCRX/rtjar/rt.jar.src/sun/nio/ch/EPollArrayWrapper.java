/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EPollArrayWrapper
/*     */ {
/*     */   private static final int EPOLLIN = 1;
/*     */   private static final int EPOLL_CTL_ADD = 1;
/*     */   private static final int EPOLL_CTL_DEL = 2;
/*     */   private static final int EPOLL_CTL_MOD = 3;
/*  69 */   private static final int SIZE_EPOLLEVENT = sizeofEPollEvent();
/*     */   private static final int EVENT_OFFSET = 0;
/*  71 */   private static final int DATA_OFFSET = offsetofData();
/*  72 */   private static final int FD_OFFSET = DATA_OFFSET;
/*  73 */   private static final int OPEN_MAX = IOUtil.fdLimit();
/*  74 */   private static final int NUM_EPOLLEVENTS = Math.min(OPEN_MAX, 8192);
/*     */ 
/*     */   
/*     */   private static final byte KILLED = -1;
/*     */ 
/*     */   
/*     */   private static final int INITIAL_PENDING_UPDATE_SIZE = 64;
/*     */ 
/*     */   
/*  83 */   private static final int MAX_UPDATE_ARRAY_SIZE = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.nio.ch.maxUpdateArraySize", 
/*  84 */         Math.min(OPEN_MAX, 65536)))).intValue();
/*     */ 
/*     */   
/*     */   private final int epfd;
/*     */ 
/*     */   
/*     */   private final AllocatedNativeObject pollArray;
/*     */ 
/*     */   
/*     */   private final long pollArrayAddress;
/*     */ 
/*     */   
/*     */   private int outgoingInterruptFD;
/*     */ 
/*     */   
/*     */   private int incomingInterruptFD;
/*     */ 
/*     */   
/*     */   private int interruptedIndex;
/*     */ 
/*     */   
/*     */   int updated;
/*     */ 
/*     */   
/* 108 */   private final Object updateLock = new Object();
/*     */ 
/*     */   
/*     */   private int updateCount;
/*     */ 
/*     */   
/* 114 */   private int[] updateDescriptors = new int[64];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private final byte[] eventsLow = new byte[MAX_UPDATE_ARRAY_SIZE];
/*     */ 
/*     */   
/*     */   private Map<Integer, Byte> eventsHigh;
/*     */   
/* 125 */   private final BitSet registered = new BitSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean interrupted;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void initInterrupt(int paramInt1, int paramInt2) {
/* 143 */     this.outgoingInterruptFD = paramInt2;
/* 144 */     this.incomingInterruptFD = paramInt1;
/* 145 */     epollCtl(this.epfd, 1, paramInt1, 1);
/*     */   }
/*     */   
/*     */   void putEventOps(int paramInt1, int paramInt2) {
/* 149 */     int i = SIZE_EPOLLEVENT * paramInt1 + 0;
/* 150 */     this.pollArray.putInt(i, paramInt2);
/*     */   }
/*     */   
/*     */   void putDescriptor(int paramInt1, int paramInt2) {
/* 154 */     int i = SIZE_EPOLLEVENT * paramInt1 + FD_OFFSET;
/* 155 */     this.pollArray.putInt(i, paramInt2);
/*     */   }
/*     */   
/*     */   int getEventOps(int paramInt) {
/* 159 */     int i = SIZE_EPOLLEVENT * paramInt + 0;
/* 160 */     return this.pollArray.getInt(i);
/*     */   }
/*     */   
/*     */   int getDescriptor(int paramInt) {
/* 164 */     int i = SIZE_EPOLLEVENT * paramInt + FD_OFFSET;
/* 165 */     return this.pollArray.getInt(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEventsHighKilled(Integer paramInteger) {
/* 173 */     assert paramInteger.intValue() >= MAX_UPDATE_ARRAY_SIZE;
/* 174 */     Byte byte_ = this.eventsHigh.get(paramInteger);
/* 175 */     return (byte_ != null && byte_.byteValue() == -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setUpdateEvents(int paramInt, byte paramByte, boolean paramBoolean) {
/* 184 */     if (paramInt < MAX_UPDATE_ARRAY_SIZE) {
/* 185 */       if (this.eventsLow[paramInt] != -1 || paramBoolean) {
/* 186 */         this.eventsLow[paramInt] = paramByte;
/*     */       }
/*     */     } else {
/* 189 */       Integer integer = Integer.valueOf(paramInt);
/* 190 */       if (!isEventsHighKilled(integer) || paramBoolean) {
/* 191 */         this.eventsHigh.put(integer, Byte.valueOf(paramByte));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte getUpdateEvents(int paramInt) {
/* 200 */     if (paramInt < MAX_UPDATE_ARRAY_SIZE) {
/* 201 */       return this.eventsLow[paramInt];
/*     */     }
/* 203 */     Byte byte_ = this.eventsHigh.get(Integer.valueOf(paramInt));
/*     */     
/* 205 */     return byte_.byteValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setInterest(int paramInt1, int paramInt2) {
/* 213 */     synchronized (this.updateLock) {
/*     */       
/* 215 */       int i = this.updateDescriptors.length;
/* 216 */       if (this.updateCount == i) {
/* 217 */         int j = i + 64;
/* 218 */         int[] arrayOfInt = new int[j];
/* 219 */         System.arraycopy(this.updateDescriptors, 0, arrayOfInt, 0, i);
/* 220 */         this.updateDescriptors = arrayOfInt;
/*     */       } 
/* 222 */       this.updateDescriptors[this.updateCount++] = paramInt1;
/*     */ 
/*     */       
/* 225 */       byte b = (byte)paramInt2;
/* 226 */       assert b == paramInt2 && b != -1;
/* 227 */       setUpdateEvents(paramInt1, b, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add(int paramInt) {
/* 237 */     synchronized (this.updateLock) {
/* 238 */       assert !this.registered.get(paramInt);
/* 239 */       setUpdateEvents(paramInt, (byte)0, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void remove(int paramInt) {
/* 247 */     synchronized (this.updateLock) {
/*     */       
/* 249 */       setUpdateEvents(paramInt, (byte)-1, false);
/*     */ 
/*     */       
/* 252 */       if (this.registered.get(paramInt)) {
/* 253 */         epollCtl(this.epfd, 2, paramInt, 0);
/* 254 */         this.registered.clear(paramInt);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void closeEPollFD() throws IOException {
/* 263 */     FileDispatcherImpl.closeIntFD(this.epfd);
/* 264 */     this.pollArray.free();
/*     */   }
/*     */   
/*     */   int poll(long paramLong) throws IOException {
/* 268 */     updateRegistrations();
/* 269 */     this.updated = epollWait(this.pollArrayAddress, NUM_EPOLLEVENTS, paramLong, this.epfd);
/* 270 */     for (byte b = 0; b < this.updated; b++) {
/* 271 */       if (getDescriptor(b) == this.incomingInterruptFD) {
/* 272 */         this.interruptedIndex = b;
/* 273 */         this.interrupted = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 277 */     return this.updated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateRegistrations() {
/* 284 */     synchronized (this.updateLock) {
/* 285 */       byte b = 0;
/* 286 */       while (b < this.updateCount) {
/* 287 */         int i = this.updateDescriptors[b];
/* 288 */         short s = (short)getUpdateEvents(i);
/* 289 */         boolean bool = this.registered.get(i);
/* 290 */         byte b1 = 0;
/*     */         
/* 292 */         if (s != -1) {
/* 293 */           if (bool) {
/* 294 */             b1 = (s != 0) ? 3 : 2;
/*     */           } else {
/* 296 */             b1 = (s != 0) ? 1 : 0;
/*     */           } 
/* 298 */           if (b1 != 0) {
/* 299 */             epollCtl(this.epfd, b1, i, s);
/* 300 */             if (b1 == 1) {
/* 301 */               this.registered.set(i);
/* 302 */             } else if (b1 == 2) {
/* 303 */               this.registered.clear(i);
/*     */             } 
/*     */           } 
/*     */         } 
/* 307 */         b++;
/*     */       } 
/* 309 */       this.updateCount = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   EPollArrayWrapper() throws IOException {
/* 314 */     this.interrupted = false; this.epfd = epollCreate(); int i = NUM_EPOLLEVENTS * SIZE_EPOLLEVENT; this.pollArray = new AllocatedNativeObject(i, true);
/*     */     this.pollArrayAddress = this.pollArray.address();
/*     */     if (OPEN_MAX > MAX_UPDATE_ARRAY_SIZE)
/* 317 */       this.eventsHigh = new HashMap<>();  } public void interrupt() { interrupt(this.outgoingInterruptFD); }
/*     */ 
/*     */   
/*     */   public int interruptedIndex() {
/* 321 */     return this.interruptedIndex;
/*     */   }
/*     */   
/*     */   boolean interrupted() {
/* 325 */     return this.interrupted;
/*     */   }
/*     */   
/*     */   void clearInterrupted() {
/* 329 */     this.interrupted = false;
/*     */   }
/*     */   
/*     */   static {
/* 333 */     IOUtil.load();
/* 334 */     init();
/*     */   }
/*     */   
/*     */   private native int epollCreate();
/*     */   
/*     */   private native void epollCtl(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   private native int epollWait(long paramLong1, int paramInt1, long paramLong2, int paramInt2) throws IOException;
/*     */   
/*     */   private static native int sizeofEPollEvent();
/*     */   
/*     */   private static native int offsetofData();
/*     */   
/*     */   private static native void interrupt(int paramInt);
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/EPollArrayWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */