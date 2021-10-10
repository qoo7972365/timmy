/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.MappedByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.misc.VM;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*  48 */   private static final int TEMP_BUF_POOL_SIZE = IOUtil.IOV_MAX;
/*     */ 
/*     */   
/*  51 */   private static final long MAX_CACHED_BUFFER_SIZE = getMaxCachedBufferSize();
/*     */ 
/*     */   
/*  54 */   private static ThreadLocal<BufferCache> bufferCache = new ThreadLocal<BufferCache>()
/*     */     {
/*     */       
/*     */       protected Util.BufferCache initialValue()
/*     */       {
/*  59 */         return new Util.BufferCache();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long getMaxCachedBufferSize() {
/*  71 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/*  75 */             return System.getProperty("jdk.nio.maxCachedBufferSize");
/*     */           }
/*     */         });
/*  78 */     if (str != null) {
/*     */       try {
/*  80 */         long l = Long.parseLong(str);
/*  81 */         if (l >= 0L) {
/*  82 */           return l;
/*     */         
/*     */         }
/*     */       }
/*  86 */       catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */     
/*  90 */     return Long.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isBufferTooLarge(int paramInt) {
/*  98 */     return (paramInt > MAX_CACHED_BUFFER_SIZE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isBufferTooLarge(ByteBuffer paramByteBuffer) {
/* 106 */     return isBufferTooLarge(paramByteBuffer.capacity());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class BufferCache
/*     */   {
/*     */     private ByteBuffer[] buffers;
/*     */ 
/*     */     
/*     */     private int count;
/*     */ 
/*     */     
/*     */     private int start;
/*     */ 
/*     */     
/*     */     private int next(int param1Int) {
/* 123 */       return (param1Int + 1) % Util.TEMP_BUF_POOL_SIZE;
/*     */     }
/*     */     
/*     */     BufferCache() {
/* 127 */       this.buffers = new ByteBuffer[Util.TEMP_BUF_POOL_SIZE];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ByteBuffer get(int param1Int) {
/* 136 */       assert !Util.isBufferTooLarge(param1Int);
/*     */       
/* 138 */       if (this.count == 0) {
/* 139 */         return null;
/*     */       }
/* 141 */       ByteBuffer[] arrayOfByteBuffer = this.buffers;
/*     */ 
/*     */       
/* 144 */       ByteBuffer byteBuffer = arrayOfByteBuffer[this.start];
/* 145 */       if (byteBuffer.capacity() < param1Int) {
/* 146 */         byteBuffer = null;
/* 147 */         int i = this.start;
/* 148 */         while ((i = next(i)) != this.start) {
/* 149 */           ByteBuffer byteBuffer1 = arrayOfByteBuffer[i];
/* 150 */           if (byteBuffer1 == null)
/*     */             break; 
/* 152 */           if (byteBuffer1.capacity() >= param1Int) {
/* 153 */             byteBuffer = byteBuffer1;
/*     */             break;
/*     */           } 
/*     */         } 
/* 157 */         if (byteBuffer == null) {
/* 158 */           return null;
/*     */         }
/* 160 */         arrayOfByteBuffer[i] = arrayOfByteBuffer[this.start];
/*     */       } 
/*     */ 
/*     */       
/* 164 */       arrayOfByteBuffer[this.start] = null;
/* 165 */       this.start = next(this.start);
/* 166 */       this.count--;
/*     */ 
/*     */       
/* 169 */       byteBuffer.rewind();
/* 170 */       byteBuffer.limit(param1Int);
/* 171 */       return byteBuffer;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean offerFirst(ByteBuffer param1ByteBuffer) {
/* 176 */       assert !Util.isBufferTooLarge(param1ByteBuffer);
/*     */       
/* 178 */       if (this.count >= Util.TEMP_BUF_POOL_SIZE) {
/* 179 */         return false;
/*     */       }
/* 181 */       this.start = (this.start + Util.TEMP_BUF_POOL_SIZE - 1) % Util.TEMP_BUF_POOL_SIZE;
/* 182 */       this.buffers[this.start] = param1ByteBuffer;
/* 183 */       this.count++;
/* 184 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     boolean offerLast(ByteBuffer param1ByteBuffer) {
/* 190 */       assert !Util.isBufferTooLarge(param1ByteBuffer);
/*     */       
/* 192 */       if (this.count >= Util.TEMP_BUF_POOL_SIZE) {
/* 193 */         return false;
/*     */       }
/* 195 */       int i = (this.start + this.count) % Util.TEMP_BUF_POOL_SIZE;
/* 196 */       this.buffers[i] = param1ByteBuffer;
/* 197 */       this.count++;
/* 198 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isEmpty() {
/* 203 */       return (this.count == 0);
/*     */     }
/*     */     
/*     */     ByteBuffer removeFirst() {
/* 207 */       assert this.count > 0;
/* 208 */       ByteBuffer byteBuffer = this.buffers[this.start];
/* 209 */       this.buffers[this.start] = null;
/* 210 */       this.start = next(this.start);
/* 211 */       this.count--;
/* 212 */       return byteBuffer;
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
/*     */   public static ByteBuffer getTemporaryDirectBuffer(int paramInt) {
/* 225 */     if (isBufferTooLarge(paramInt)) {
/* 226 */       return ByteBuffer.allocateDirect(paramInt);
/*     */     }
/*     */     
/* 229 */     BufferCache bufferCache = bufferCache.get();
/* 230 */     ByteBuffer byteBuffer = bufferCache.get(paramInt);
/* 231 */     if (byteBuffer != null) {
/* 232 */       return byteBuffer;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 237 */     if (!bufferCache.isEmpty()) {
/* 238 */       byteBuffer = bufferCache.removeFirst();
/* 239 */       free(byteBuffer);
/*     */     } 
/* 241 */     return ByteBuffer.allocateDirect(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void releaseTemporaryDirectBuffer(ByteBuffer paramByteBuffer) {
/* 249 */     offerFirstTemporaryDirectBuffer(paramByteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void offerFirstTemporaryDirectBuffer(ByteBuffer paramByteBuffer) {
/* 260 */     if (isBufferTooLarge(paramByteBuffer)) {
/* 261 */       free(paramByteBuffer);
/*     */       
/*     */       return;
/*     */     } 
/* 265 */     assert paramByteBuffer != null;
/* 266 */     BufferCache bufferCache = bufferCache.get();
/* 267 */     if (!bufferCache.offerFirst(paramByteBuffer))
/*     */     {
/* 269 */       free(paramByteBuffer);
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
/*     */   static void offerLastTemporaryDirectBuffer(ByteBuffer paramByteBuffer) {
/* 282 */     if (isBufferTooLarge(paramByteBuffer)) {
/* 283 */       free(paramByteBuffer);
/*     */       
/*     */       return;
/*     */     } 
/* 287 */     assert paramByteBuffer != null;
/* 288 */     BufferCache bufferCache = bufferCache.get();
/* 289 */     if (!bufferCache.offerLast(paramByteBuffer))
/*     */     {
/* 291 */       free(paramByteBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void free(ByteBuffer paramByteBuffer) {
/* 299 */     ((DirectBuffer)paramByteBuffer).cleaner().clean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuffer[] subsequence(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) {
/* 306 */     if (paramInt1 == 0 && paramInt2 == paramArrayOfByteBuffer.length)
/* 307 */       return paramArrayOfByteBuffer; 
/* 308 */     int i = paramInt2;
/* 309 */     ByteBuffer[] arrayOfByteBuffer = new ByteBuffer[i];
/* 310 */     for (byte b = 0; b < i; b++)
/* 311 */       arrayOfByteBuffer[b] = paramArrayOfByteBuffer[paramInt1 + b]; 
/* 312 */     return arrayOfByteBuffer;
/*     */   }
/*     */   
/*     */   static <E> Set<E> ungrowableSet(final Set<E> s) {
/* 316 */     return new Set<E>() {
/*     */         public int size() {
/* 318 */           return s.size();
/* 319 */         } public boolean isEmpty() { return s.isEmpty(); }
/* 320 */         public boolean contains(Object param1Object) { return s.contains(param1Object); }
/* 321 */         public Object[] toArray() { return s.toArray(); }
/* 322 */         public <T> T[] toArray(T[] param1ArrayOfT) { return (T[])s.toArray((Object[])param1ArrayOfT); }
/* 323 */         public String toString() { return s.toString(); }
/* 324 */         public Iterator<E> iterator() { return s.iterator(); }
/* 325 */         public boolean equals(Object param1Object) { return s.equals(param1Object); }
/* 326 */         public int hashCode() { return s.hashCode(); }
/* 327 */         public void clear() { s.clear(); } public boolean remove(Object param1Object) {
/* 328 */           return s.remove(param1Object);
/*     */         }
/*     */         public boolean containsAll(Collection<?> param1Collection) {
/* 331 */           return s.containsAll(param1Collection);
/*     */         }
/*     */         public boolean removeAll(Collection<?> param1Collection) {
/* 334 */           return s.removeAll(param1Collection);
/*     */         }
/*     */         public boolean retainAll(Collection<?> param1Collection) {
/* 337 */           return s.retainAll(param1Collection);
/*     */         }
/*     */         
/*     */         public boolean add(E param1E) {
/* 341 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         public boolean addAll(Collection<? extends E> param1Collection) {
/* 344 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 353 */   private static Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   private static byte _get(long paramLong) {
/* 356 */     return unsafe.getByte(paramLong);
/*     */   }
/*     */   
/*     */   private static void _put(long paramLong, byte paramByte) {
/* 360 */     unsafe.putByte(paramLong, paramByte);
/*     */   }
/*     */   
/*     */   static void erase(ByteBuffer paramByteBuffer) {
/* 364 */     unsafe.setMemory(((DirectBuffer)paramByteBuffer).address(), paramByteBuffer.capacity(), (byte)0);
/*     */   }
/*     */   
/*     */   static Unsafe unsafe() {
/* 368 */     return unsafe;
/*     */   }
/*     */   
/* 371 */   private static int pageSize = -1;
/*     */   
/*     */   static int pageSize() {
/* 374 */     if (pageSize == -1)
/* 375 */       pageSize = unsafe().pageSize(); 
/* 376 */     return pageSize;
/*     */   }
/*     */   
/* 379 */   private static volatile Constructor<?> directByteBufferConstructor = null;
/*     */   
/*     */   private static void initDBBConstructor() {
/* 382 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*     */             try {
/* 385 */               Class<?> clazz = Class.forName("java.nio.DirectByteBuffer");
/* 386 */               Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[] { int.class, long.class, FileDescriptor.class, Runnable.class });
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 391 */               constructor.setAccessible(true);
/* 392 */               Util.directByteBufferConstructor = constructor;
/* 393 */             } catch (ClassNotFoundException|NoSuchMethodException|IllegalArgumentException|ClassCastException classNotFoundException) {
/*     */ 
/*     */ 
/*     */               
/* 397 */               throw new InternalError(classNotFoundException);
/*     */             } 
/* 399 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static MappedByteBuffer newMappedByteBuffer(int paramInt, long paramLong, FileDescriptor paramFileDescriptor, Runnable paramRunnable) {
/*     */     MappedByteBuffer mappedByteBuffer;
/* 408 */     if (directByteBufferConstructor == null)
/* 409 */       initDBBConstructor(); 
/*     */     try {
/* 411 */       mappedByteBuffer = (MappedByteBuffer)directByteBufferConstructor.newInstance(new Object[] { new Integer(paramInt), new Long(paramLong), paramFileDescriptor, paramRunnable });
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 416 */     catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException instantiationException) {
/*     */ 
/*     */       
/* 419 */       throw new InternalError(instantiationException);
/*     */     } 
/* 421 */     return mappedByteBuffer;
/*     */   }
/*     */   
/* 424 */   private static volatile Constructor<?> directByteBufferRConstructor = null;
/*     */   
/*     */   private static void initDBBRConstructor() {
/* 427 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*     */             try {
/* 430 */               Class<?> clazz = Class.forName("java.nio.DirectByteBufferR");
/* 431 */               Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[] { int.class, long.class, FileDescriptor.class, Runnable.class });
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 436 */               constructor.setAccessible(true);
/* 437 */               Util.directByteBufferRConstructor = constructor;
/* 438 */             } catch (ClassNotFoundException|NoSuchMethodException|IllegalArgumentException|ClassCastException classNotFoundException) {
/*     */ 
/*     */ 
/*     */               
/* 442 */               throw new InternalError(classNotFoundException);
/*     */             } 
/* 444 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static MappedByteBuffer newMappedByteBufferR(int paramInt, long paramLong, FileDescriptor paramFileDescriptor, Runnable paramRunnable) {
/*     */     MappedByteBuffer mappedByteBuffer;
/* 453 */     if (directByteBufferRConstructor == null)
/* 454 */       initDBBRConstructor(); 
/*     */     try {
/* 456 */       mappedByteBuffer = (MappedByteBuffer)directByteBufferRConstructor.newInstance(new Object[] { new Integer(paramInt), new Long(paramLong), paramFileDescriptor, paramRunnable });
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 461 */     catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException instantiationException) {
/*     */ 
/*     */       
/* 464 */       throw new InternalError(instantiationException);
/*     */     } 
/* 466 */     return mappedByteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 472 */   private static volatile String bugLevel = null;
/*     */   
/*     */   static boolean atBugLevel(String paramString) {
/* 475 */     if (bugLevel == null) {
/* 476 */       if (!VM.isBooted())
/* 477 */         return false; 
/* 478 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.nio.ch.bugLevel"));
/*     */       
/* 480 */       bugLevel = (str != null) ? str : "";
/*     */     } 
/* 482 */     return bugLevel.equals(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */