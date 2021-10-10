/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ImageFetcher
/*     */   extends Thread
/*     */ {
/*     */   static final int HIGH_PRIORITY = 8;
/*     */   static final int LOW_PRIORITY = 3;
/*     */   static final int ANIM_PRIORITY = 2;
/*     */   static final int TIMEOUT = 5000;
/*     */   
/*     */   private ImageFetcher(ThreadGroup paramThreadGroup, int paramInt) {
/*  57 */     super(paramThreadGroup, "Image Fetcher " + paramInt);
/*  58 */     setDaemon(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean add(ImageFetchable paramImageFetchable) {
/*  68 */     FetcherInfo fetcherInfo = FetcherInfo.getFetcherInfo();
/*  69 */     synchronized (fetcherInfo.waitList) {
/*  70 */       if (!fetcherInfo.waitList.contains(paramImageFetchable)) {
/*  71 */         fetcherInfo.waitList.addElement(paramImageFetchable);
/*  72 */         if (fetcherInfo.numWaiting == 0 && fetcherInfo.numFetchers < fetcherInfo.fetchers.length)
/*     */         {
/*  74 */           createFetchers(fetcherInfo);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         if (fetcherInfo.numFetchers > 0) {
/*  85 */           fetcherInfo.waitList.notify();
/*     */         } else {
/*  87 */           fetcherInfo.waitList.removeElement(paramImageFetchable);
/*  88 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void remove(ImageFetchable paramImageFetchable) {
/*  99 */     FetcherInfo fetcherInfo = FetcherInfo.getFetcherInfo();
/* 100 */     synchronized (fetcherInfo.waitList) {
/* 101 */       if (fetcherInfo.waitList.contains(paramImageFetchable)) {
/* 102 */         fetcherInfo.waitList.removeElement(paramImageFetchable);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFetcher(Thread paramThread) {
/* 111 */     FetcherInfo fetcherInfo = FetcherInfo.getFetcherInfo();
/* 112 */     synchronized (fetcherInfo.waitList) {
/* 113 */       for (byte b = 0; b < fetcherInfo.fetchers.length; b++) {
/* 114 */         if (fetcherInfo.fetchers[b] == paramThread) {
/* 115 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean amFetcher() {
/* 126 */     return isFetcher(Thread.currentThread());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ImageFetchable nextImage() {
/* 135 */     FetcherInfo fetcherInfo = FetcherInfo.getFetcherInfo();
/* 136 */     synchronized (fetcherInfo.waitList) {
/* 137 */       ImageFetchable imageFetchable = null;
/* 138 */       long l = System.currentTimeMillis() + 5000L;
/* 139 */       while (imageFetchable == null) {
/* 140 */         while (fetcherInfo.waitList.size() == 0) {
/* 141 */           long l1 = System.currentTimeMillis();
/* 142 */           if (l1 >= l) {
/* 143 */             return null;
/*     */           }
/*     */           try {
/* 146 */             fetcherInfo.numWaiting++;
/* 147 */             fetcherInfo.waitList.wait(l - l1);
/* 148 */           } catch (InterruptedException interruptedException) {
/*     */             
/* 150 */             return null;
/*     */           } finally {
/* 152 */             fetcherInfo.numWaiting--;
/*     */           } 
/*     */         } 
/* 155 */         imageFetchable = fetcherInfo.waitList.elementAt(0);
/* 156 */         fetcherInfo.waitList.removeElement(imageFetchable);
/*     */       } 
/* 158 */       return imageFetchable;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 167 */     FetcherInfo fetcherInfo = FetcherInfo.getFetcherInfo();
/*     */     try {
/* 169 */       fetchloop();
/* 170 */     } catch (Exception exception) {
/* 171 */       exception.printStackTrace();
/*     */     } finally {
/* 173 */       synchronized (fetcherInfo.waitList) {
/* 174 */         Thread thread = Thread.currentThread();
/* 175 */         for (byte b = 0; b < fetcherInfo.fetchers.length; b++) {
/* 176 */           if (fetcherInfo.fetchers[b] == thread) {
/* 177 */             fetcherInfo.fetchers[b] = null;
/* 178 */             fetcherInfo.numFetchers--;
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
/*     */   private void fetchloop() {
/* 191 */     Thread thread = Thread.currentThread();
/* 192 */     while (isFetcher(thread)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       Thread.interrupted();
/* 199 */       thread.setPriority(8);
/* 200 */       ImageFetchable imageFetchable = nextImage();
/* 201 */       if (imageFetchable == null) {
/*     */         return;
/*     */       }
/*     */       try {
/* 205 */         imageFetchable.doFetch();
/* 206 */       } catch (Exception exception) {
/* 207 */         System.err.println("Uncaught error fetching image:");
/* 208 */         exception.printStackTrace();
/*     */       } 
/* 210 */       stoppingAnimation(thread);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void startingAnimation() {
/* 221 */     FetcherInfo fetcherInfo = FetcherInfo.getFetcherInfo();
/* 222 */     Thread thread = Thread.currentThread();
/* 223 */     synchronized (fetcherInfo.waitList) {
/* 224 */       for (byte b = 0; b < fetcherInfo.fetchers.length; b++) {
/* 225 */         if (fetcherInfo.fetchers[b] == thread) {
/* 226 */           fetcherInfo.fetchers[b] = null;
/* 227 */           fetcherInfo.numFetchers--;
/* 228 */           thread.setName("Image Animator " + b);
/* 229 */           if (fetcherInfo.waitList.size() > fetcherInfo.numWaiting) {
/* 230 */             createFetchers(fetcherInfo);
/*     */           }
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 236 */     thread.setPriority(2);
/* 237 */     thread.setName("Image Animator");
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
/*     */   private static void stoppingAnimation(Thread paramThread) {
/* 249 */     FetcherInfo fetcherInfo = FetcherInfo.getFetcherInfo();
/* 250 */     synchronized (fetcherInfo.waitList) {
/* 251 */       byte b = -1;
/* 252 */       for (byte b1 = 0; b1 < fetcherInfo.fetchers.length; b1++) {
/* 253 */         if (fetcherInfo.fetchers[b1] == paramThread) {
/*     */           return;
/*     */         }
/* 256 */         if (fetcherInfo.fetchers[b1] == null) {
/* 257 */           b = b1;
/*     */         }
/*     */       } 
/* 260 */       if (b >= 0) {
/* 261 */         fetcherInfo.fetchers[b] = paramThread;
/* 262 */         fetcherInfo.numFetchers++;
/* 263 */         paramThread.setName("Image Fetcher " + b);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void createFetchers(final FetcherInfo info) {
/*     */     ThreadGroup threadGroup2;
/* 276 */     AppContext appContext = AppContext.getAppContext();
/* 277 */     ThreadGroup threadGroup1 = appContext.getThreadGroup();
/*     */     
/*     */     try {
/* 280 */       if (threadGroup1.getParent() != null) {
/*     */         
/* 282 */         threadGroup2 = threadGroup1;
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 289 */         threadGroup1 = Thread.currentThread().getThreadGroup();
/* 290 */         ThreadGroup threadGroup = threadGroup1.getParent();
/* 291 */         while (threadGroup != null && threadGroup
/* 292 */           .getParent() != null) {
/* 293 */           threadGroup1 = threadGroup;
/* 294 */           threadGroup = threadGroup1.getParent();
/*     */         } 
/* 296 */         threadGroup2 = threadGroup1;
/*     */       } 
/* 298 */     } catch (SecurityException securityException) {
/*     */ 
/*     */       
/* 301 */       threadGroup2 = appContext.getThreadGroup();
/*     */     } 
/* 303 */     final ThreadGroup fetcherGroup = threadGroup2;
/*     */     
/* 305 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/* 308 */             for (byte b = 0; b < info.fetchers.length; b++) {
/* 309 */               if (info.fetchers[b] == null) {
/* 310 */                 ImageFetcher imageFetcher = new ImageFetcher(fetcherGroup, b);
/*     */                 
/*     */                 try {
/* 313 */                   imageFetcher.start();
/* 314 */                   info.fetchers[b] = imageFetcher;
/* 315 */                   info.numFetchers++;
/*     */                   break;
/* 317 */                 } catch (Error error) {}
/*     */               } 
/*     */             } 
/*     */             
/* 321 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ImageFetcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */