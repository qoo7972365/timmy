/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.UNIXToolkit;
/*     */ import sun.awt.datatransfer.DataTransferer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class XSelection
/*     */ {
/*  48 */   private static final Hashtable<XAtom, XSelection> table = new Hashtable<>();
/*     */   
/*  50 */   private static final Object lock = new Object();
/*     */   
/*  52 */   private static final XAtom selectionPropertyAtom = XAtom.get("XAWT_SELECTION");
/*     */ 
/*     */   
/*     */   public static final long MAX_LENGTH = 1000000L;
/*     */   
/*     */   public static final int MAX_PROPERTY_SIZE;
/*     */ 
/*     */   
/*     */   static {
/*  61 */     XToolkit.awtLock();
/*     */     
/*     */     try {
/*  64 */       MAX_PROPERTY_SIZE = (int)(XlibWrapper.XMaxRequestSize(XToolkit.getDisplay()) * 4L - 100L);
/*     */     } finally {
/*  66 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  71 */   private static final XEventDispatcher incrementalTransferHandler = new IncrementalTransferHandler();
/*     */ 
/*     */   
/*  74 */   private static WindowPropertyGetter propertyGetter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final XAtom selectionAtom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private Transferable contents = null;
/*     */   
/*  90 */   private Map formatMap = null;
/*     */   
/*  92 */   private long[] formats = null;
/*     */   
/*  94 */   private AppContext appContext = null;
/*     */ 
/*     */   
/*     */   private static long lastRequestServerTime;
/*     */   
/*  99 */   private long ownershipTime = 0L;
/*     */   
/*     */   private boolean isOwner;
/* 102 */   private OwnershipListener ownershipListener = null;
/* 103 */   private final Object stateLock = new Object();
/*     */   
/*     */   static {
/* 106 */     XToolkit.addEventDispatcher(XWindow.getXAWTRootWindow().getWindow(), new SelectionEventHandler());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static XSelection getSelection(XAtom paramXAtom) {
/* 115 */     return table.get(paramXAtom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XSelection(XAtom paramXAtom) {
/* 125 */     if (paramXAtom == null) {
/* 126 */       throw new NullPointerException("Null atom");
/*     */     }
/* 128 */     this.selectionAtom = paramXAtom;
/* 129 */     table.put(this.selectionAtom, this);
/*     */   }
/*     */   
/*     */   public XAtom getSelectionAtom() {
/* 133 */     return this.selectionAtom;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized boolean setOwner(Transferable paramTransferable, Map paramMap, long[] paramArrayOflong, long paramLong) {
/* 139 */     long l1 = XWindow.getXAWTRootWindow().getWindow();
/* 140 */     long l2 = this.selectionAtom.getAtom();
/*     */ 
/*     */     
/* 143 */     if (paramLong == 0L) {
/* 144 */       paramLong = XToolkit.getCurrentServerTime();
/*     */     }
/*     */     
/* 147 */     this.contents = paramTransferable;
/* 148 */     this.formatMap = paramMap;
/* 149 */     this.formats = paramArrayOflong;
/* 150 */     this.appContext = AppContext.getAppContext();
/* 151 */     this.ownershipTime = paramLong;
/*     */     
/* 153 */     XToolkit.awtLock();
/*     */     try {
/* 155 */       XlibWrapper.XSetSelectionOwner(XToolkit.getDisplay(), l2, l1, paramLong);
/*     */       
/* 157 */       if (XlibWrapper.XGetSelectionOwner(XToolkit.getDisplay(), l2) != l1) {
/*     */ 
/*     */         
/* 160 */         reset();
/* 161 */         return false;
/*     */       } 
/* 163 */       setOwnerProp(true);
/* 164 */       return true;
/*     */     } finally {
/* 166 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void waitForSelectionNotify(WindowPropertyGetter paramWindowPropertyGetter) throws InterruptedException {
/* 174 */     long l = System.currentTimeMillis();
/* 175 */     XToolkit.awtLock();
/*     */     try {
/*     */       do {
/* 178 */         DataTransferer.getInstance().processDataConversionRequests();
/* 179 */         XToolkit.awtLockWait(250L);
/* 180 */       } while (propertyGetter == paramWindowPropertyGetter && System.currentTimeMillis() < l + UNIXToolkit.getDatatransferTimeout());
/*     */     } finally {
/* 182 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] getTargets(long paramLong) {
/* 191 */     if (XToolkit.isToolkitThread()) {
/* 192 */       throw new Error("UNIMPLEMENTED");
/*     */     }
/*     */     
/* 195 */     long[] arrayOfLong = null;
/*     */     
/* 197 */     synchronized (lock) {
/*     */       
/* 199 */       WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(XWindow.getXAWTRootWindow().getWindow(), selectionPropertyAtom, 0L, 1000000L, true, 0L);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 204 */         XToolkit.awtLock();
/*     */         try {
/* 206 */           propertyGetter = windowPropertyGetter;
/* 207 */           lastRequestServerTime = paramLong;
/*     */           
/* 209 */           XlibWrapper.XConvertSelection(XToolkit.getDisplay(), 
/* 210 */               getSelectionAtom().getAtom(), XDataTransferer.TARGETS_ATOM
/* 211 */               .getAtom(), selectionPropertyAtom
/* 212 */               .getAtom(), 
/* 213 */               XWindow.getXAWTRootWindow().getWindow(), paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 219 */             waitForSelectionNotify(windowPropertyGetter);
/* 220 */           } catch (InterruptedException interruptedException) {
/* 221 */             return new long[0];
/*     */           } finally {
/* 223 */             propertyGetter = null;
/*     */           } 
/*     */         } finally {
/* 226 */           XToolkit.awtUnlock();
/*     */         } 
/* 228 */         arrayOfLong = getFormats(windowPropertyGetter);
/*     */       } finally {
/* 230 */         windowPropertyGetter.dispose();
/*     */       } 
/*     */     } 
/* 233 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   static long[] getFormats(WindowPropertyGetter paramWindowPropertyGetter) {
/* 237 */     long[] arrayOfLong = null;
/*     */     
/* 239 */     if (paramWindowPropertyGetter.isExecuted() && !paramWindowPropertyGetter.isDisposed() && (paramWindowPropertyGetter
/* 240 */       .getActualType() == 4L || paramWindowPropertyGetter
/* 241 */       .getActualType() == XDataTransferer.TARGETS_ATOM.getAtom()) && paramWindowPropertyGetter
/* 242 */       .getActualFormat() == 32) {
/*     */ 
/*     */ 
/*     */       
/* 246 */       int i = paramWindowPropertyGetter.getNumberOfItems();
/* 247 */       if (i > 0) {
/* 248 */         long l = paramWindowPropertyGetter.getData();
/* 249 */         arrayOfLong = new long[i];
/* 250 */         for (byte b = 0; b < i; b++) {
/* 251 */           arrayOfLong[b] = 
/* 252 */             Native.getLong(l + (b * XAtom.getAtomSize()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     return (arrayOfLong != null) ? arrayOfLong : new long[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getData(long paramLong1, long paramLong2) throws IOException {
/* 265 */     if (XToolkit.isToolkitThread()) {
/* 266 */       throw new Error("UNIMPLEMENTED");
/*     */     }
/*     */     
/* 269 */     byte[] arrayOfByte = null;
/*     */     
/* 271 */     synchronized (lock) {
/*     */       
/* 273 */       WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(XWindow.getXAWTRootWindow().getWindow(), selectionPropertyAtom, 0L, 1000000L, false, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 279 */         XToolkit.awtLock();
/*     */         try {
/* 281 */           propertyGetter = windowPropertyGetter;
/* 282 */           lastRequestServerTime = paramLong2;
/*     */           
/* 284 */           XlibWrapper.XConvertSelection(XToolkit.getDisplay(), 
/* 285 */               getSelectionAtom().getAtom(), paramLong1, selectionPropertyAtom
/*     */               
/* 287 */               .getAtom(), 
/* 288 */               XWindow.getXAWTRootWindow().getWindow(), paramLong2);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 294 */             waitForSelectionNotify(windowPropertyGetter);
/* 295 */           } catch (InterruptedException interruptedException) {
/* 296 */             return new byte[0];
/*     */           } finally {
/* 298 */             propertyGetter = null;
/*     */           } 
/*     */         } finally {
/* 301 */           XToolkit.awtUnlock();
/*     */         } 
/*     */         
/* 304 */         validateDataGetter(windowPropertyGetter);
/*     */ 
/*     */         
/* 307 */         if (windowPropertyGetter.getActualType() == XDataTransferer.INCR_ATOM
/* 308 */           .getAtom()) {
/*     */           
/* 310 */           if (windowPropertyGetter.getActualFormat() != 32) {
/* 311 */             throw new IOException("Unsupported INCR format: " + windowPropertyGetter
/* 312 */                 .getActualFormat());
/*     */           }
/*     */           
/* 315 */           int i = windowPropertyGetter.getNumberOfItems();
/*     */           
/* 317 */           if (i <= 0) {
/* 318 */             throw new IOException("INCR data is missed.");
/*     */           }
/*     */           
/* 321 */           long l1 = windowPropertyGetter.getData();
/*     */           
/* 323 */           int j = 0;
/*     */ 
/*     */ 
/*     */           
/* 327 */           long l2 = Native.getLong(l1, i - 1);
/*     */           
/* 329 */           if (l2 <= 0L) {
/* 330 */             return new byte[0];
/*     */           }
/*     */           
/* 333 */           if (l2 > 2147483647L) {
/* 334 */             throw new IOException("Can't handle large data block: " + l2 + " bytes");
/*     */           }
/*     */ 
/*     */           
/* 338 */           j = (int)l2;
/*     */ 
/*     */           
/* 341 */           windowPropertyGetter.dispose();
/*     */           
/* 343 */           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(j);
/*     */ 
/*     */           
/*     */           while (true) {
/* 347 */             WindowPropertyGetter windowPropertyGetter1 = new WindowPropertyGetter(XWindow.getXAWTRootWindow().getWindow(), selectionPropertyAtom, 0L, 1000000L, false, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 353 */             try { XToolkit.awtLock();
/* 354 */               XToolkit.addEventDispatcher(XWindow.getXAWTRootWindow().getWindow(), incrementalTransferHandler);
/*     */ 
/*     */               
/* 357 */               propertyGetter = windowPropertyGetter1;
/*     */ 
/*     */               
/* 360 */               try { XlibWrapper.XDeleteProperty(XToolkit.getDisplay(), 
/* 361 */                     XWindow.getXAWTRootWindow().getWindow(), selectionPropertyAtom
/* 362 */                     .getAtom());
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 367 */                 waitForSelectionNotify(windowPropertyGetter1); }
/* 368 */               catch (InterruptedException interruptedException)
/*     */               
/*     */               { 
/* 371 */                 propertyGetter = null; break; } finally { propertyGetter = null;
/* 372 */                 XToolkit.removeEventDispatcher(XWindow.getXAWTRootWindow().getWindow(), incrementalTransferHandler);
/*     */                 
/* 374 */                 XToolkit.awtUnlock(); }
/*     */ 
/*     */               
/* 377 */               validateDataGetter(windowPropertyGetter1);
/*     */               
/* 379 */               if (windowPropertyGetter1.getActualFormat() != 8) {
/* 380 */                 throw new IOException("Unsupported data format: " + windowPropertyGetter1
/* 381 */                     .getActualFormat());
/*     */               }
/*     */               
/* 384 */               i = windowPropertyGetter1.getNumberOfItems();
/*     */               
/* 386 */               if (i == 0)
/*     */               
/*     */               { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 400 */                 windowPropertyGetter1.dispose(); break; }  if (i > 0) { l1 = windowPropertyGetter1.getData(); for (byte b = 0; b < i; b++) byteArrayOutputStream.write(Native.getByte(l1 + b));  }  arrayOfByte = byteArrayOutputStream.toByteArray(); } finally { windowPropertyGetter1.dispose(); }
/*     */           
/*     */           } 
/*     */         } else {
/* 404 */           XToolkit.awtLock();
/*     */           try {
/* 406 */             XlibWrapper.XDeleteProperty(XToolkit.getDisplay(), 
/* 407 */                 XWindow.getXAWTRootWindow().getWindow(), selectionPropertyAtom
/* 408 */                 .getAtom());
/*     */           } finally {
/* 410 */             XToolkit.awtUnlock();
/*     */           } 
/*     */           
/* 413 */           if (windowPropertyGetter.getActualFormat() != 8) {
/* 414 */             throw new IOException("Unsupported data format: " + windowPropertyGetter
/* 415 */                 .getActualFormat());
/*     */           }
/*     */           
/* 418 */           int i = windowPropertyGetter.getNumberOfItems();
/* 419 */           if (i > 0) {
/* 420 */             arrayOfByte = new byte[i];
/* 421 */             long l = windowPropertyGetter.getData();
/* 422 */             for (byte b = 0; b < i; b++) {
/* 423 */               arrayOfByte[b] = Native.getByte(l + b);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } finally {
/* 428 */         windowPropertyGetter.dispose();
/*     */       } 
/*     */     } 
/*     */     
/* 432 */     return (arrayOfByte != null) ? arrayOfByte : new byte[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateDataGetter(WindowPropertyGetter paramWindowPropertyGetter) throws IOException {
/* 442 */     if (paramWindowPropertyGetter.isDisposed()) {
/* 443 */       throw new IOException("Owner failed to convert data");
/*     */     }
/*     */ 
/*     */     
/* 447 */     if (!paramWindowPropertyGetter.isExecuted()) {
/* 448 */       throw new IOException("Owner timed out");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isOwner() {
/* 454 */     return this.isOwner;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setOwnerProp(boolean paramBoolean) {
/* 459 */     this.isOwner = paramBoolean;
/* 460 */     fireOwnershipChanges(this.isOwner);
/*     */   }
/*     */   
/*     */   private void lostOwnership() {
/* 464 */     setOwnerProp(false);
/*     */   }
/*     */   
/*     */   public synchronized void reset() {
/* 468 */     this.contents = null;
/* 469 */     this.formatMap = null;
/* 470 */     this.formats = null;
/* 471 */     this.appContext = null;
/* 472 */     this.ownershipTime = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean convertAndStore(long paramLong1, long paramLong2, long paramLong3) {
/* 479 */     byte b = 8;
/* 480 */     byte[] arrayOfByte = null;
/* 481 */     long l = 0L;
/* 482 */     int i = 0;
/*     */     
/*     */     try {
/* 485 */       SunToolkit.insertTargetMapping(this, this.appContext);
/*     */       
/* 487 */       arrayOfByte = DataTransferer.getInstance().convertData(this, this.contents, paramLong2, this.formatMap, 
/*     */ 
/*     */ 
/*     */           
/* 491 */           XToolkit.isToolkitThread());
/* 492 */     } catch (IOException iOException) {
/* 493 */       return false;
/*     */     } 
/*     */     
/* 496 */     if (arrayOfByte == null) {
/* 497 */       return false;
/*     */     }
/*     */     
/* 500 */     i = arrayOfByte.length;
/*     */     
/*     */     try {
/* 503 */       if (i > 0) {
/* 504 */         if (i <= MAX_PROPERTY_SIZE) {
/* 505 */           l = Native.toData(arrayOfByte);
/*     */         } else {
/*     */           
/* 508 */           new IncrementalDataProvider(paramLong1, paramLong3, paramLong2, 8, arrayOfByte);
/*     */ 
/*     */ 
/*     */           
/* 512 */           l = XlibWrapper.unsafe.allocateMemory(XAtom.getAtomSize());
/*     */           
/* 514 */           Native.putLong(l, i);
/*     */           
/* 516 */           paramLong2 = XDataTransferer.INCR_ATOM.getAtom();
/* 517 */           b = 32;
/* 518 */           i = 1;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 523 */       XToolkit.awtLock();
/*     */       try {
/* 525 */         XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong1, paramLong3, paramLong2, b, 0, l, i);
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 530 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/* 533 */       if (l != 0L) {
/* 534 */         XlibWrapper.unsafe.freeMemory(l);
/* 535 */         l = 0L;
/*     */       } 
/*     */     } 
/*     */     
/* 539 */     return true;
/*     */   }
/*     */   
/*     */   private void handleSelectionRequest(XSelectionRequestEvent paramXSelectionRequestEvent) {
/* 543 */     long l1 = paramXSelectionRequestEvent.get_property();
/* 544 */     long l2 = paramXSelectionRequestEvent.get_requestor();
/* 545 */     long l3 = paramXSelectionRequestEvent.get_time();
/* 546 */     long l4 = paramXSelectionRequestEvent.get_target();
/* 547 */     boolean bool = false;
/*     */     
/* 549 */     if (this.ownershipTime != 0L && (l3 == 0L || l3 >= this.ownershipTime))
/*     */     {
/*     */ 
/*     */       
/* 553 */       if (l4 == XDataTransferer.MULTIPLE_ATOM.getAtom()) {
/* 554 */         bool = handleMultipleRequest(l2, l1);
/*     */       } else {
/*     */         
/* 557 */         if (l1 == 0L) {
/* 558 */           l1 = l4;
/*     */         }
/*     */         
/* 561 */         if (l4 == XDataTransferer.TARGETS_ATOM.getAtom()) {
/* 562 */           bool = handleTargetsRequest(l1, l2);
/*     */         } else {
/* 564 */           bool = convertAndStore(l2, l4, l1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 569 */     if (!bool)
/*     */     {
/* 571 */       l1 = 0L;
/*     */     }
/*     */     
/* 574 */     XSelectionEvent xSelectionEvent = new XSelectionEvent();
/*     */     try {
/* 576 */       xSelectionEvent.set_type(31);
/* 577 */       xSelectionEvent.set_send_event(true);
/* 578 */       xSelectionEvent.set_requestor(l2);
/* 579 */       xSelectionEvent.set_selection(this.selectionAtom.getAtom());
/* 580 */       xSelectionEvent.set_target(l4);
/* 581 */       xSelectionEvent.set_property(l1);
/* 582 */       xSelectionEvent.set_time(l3);
/*     */       
/* 584 */       XToolkit.awtLock();
/*     */       try {
/* 586 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), l2, false, 0L, xSelectionEvent.pData);
/*     */       } finally {
/*     */         
/* 589 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/* 592 */       xSelectionEvent.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean handleMultipleRequest(long paramLong1, long paramLong2) {
/* 597 */     if (0L == paramLong2)
/*     */     {
/* 599 */       return false;
/*     */     }
/*     */     
/* 602 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */     
/* 606 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong1, XAtom.get(paramLong2), 0L, 1000000L, false, 0L);
/*     */ 
/*     */     
/*     */     try {
/* 610 */       windowPropertyGetter.execute();
/*     */       
/* 612 */       if (windowPropertyGetter.getActualFormat() == 32 && windowPropertyGetter.getNumberOfItems() % 2 == 0) {
/* 613 */         long l1 = (windowPropertyGetter.getNumberOfItems() / 2);
/* 614 */         long l2 = windowPropertyGetter.getData();
/* 615 */         boolean bool1 = false;
/*     */         
/* 617 */         for (byte b = 0; b < l1; b++) {
/* 618 */           long l3 = Native.getLong(l2, 2 * b);
/* 619 */           long l4 = Native.getLong(l2, 2 * b + 1);
/*     */           
/* 621 */           if (!convertAndStore(paramLong1, l3, l4)) {
/*     */ 
/*     */             
/* 624 */             Native.putLong(l2, 2 * b, 0L);
/* 625 */             bool1 = true;
/*     */           } 
/*     */         } 
/* 628 */         if (bool1) {
/* 629 */           XToolkit.awtLock();
/*     */           try {
/* 631 */             XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong1, paramLong2, windowPropertyGetter
/*     */ 
/*     */                 
/* 634 */                 .getActualType(), windowPropertyGetter
/* 635 */                 .getActualFormat(), 0, windowPropertyGetter
/*     */                 
/* 637 */                 .getData(), windowPropertyGetter
/* 638 */                 .getNumberOfItems());
/*     */           } finally {
/* 640 */             XToolkit.awtUnlock();
/*     */           } 
/*     */         } 
/* 643 */         bool = true;
/*     */       } 
/*     */     } finally {
/* 646 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */     
/* 649 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean handleTargetsRequest(long paramLong1, long paramLong2) throws IllegalStateException {
/* 655 */     boolean bool = false;
/*     */     
/* 657 */     long[] arrayOfLong = this.formats;
/*     */     
/* 659 */     if (arrayOfLong == null) {
/* 660 */       throw new IllegalStateException("Not an owner.");
/*     */     }
/*     */     
/* 663 */     long l = 0L;
/*     */     
/*     */     try {
/* 666 */       int i = arrayOfLong.length;
/*     */ 
/*     */       
/* 669 */       if (i > 0) {
/* 670 */         l = Native.allocateLongArray(i);
/* 671 */         Native.put(l, arrayOfLong);
/*     */       } 
/*     */       
/* 674 */       bool = true;
/*     */       
/* 676 */       XToolkit.awtLock();
/*     */       try {
/* 678 */         XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong2, paramLong1, 4L, 32, 0, l, i);
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 683 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/* 686 */       if (l != 0L) {
/* 687 */         XlibWrapper.unsafe.freeMemory(l);
/* 688 */         l = 0L;
/*     */       } 
/*     */     } 
/* 691 */     return bool;
/*     */   }
/*     */   
/*     */   private void fireOwnershipChanges(boolean paramBoolean) {
/* 695 */     OwnershipListener ownershipListener = null;
/* 696 */     synchronized (this.stateLock) {
/* 697 */       ownershipListener = this.ownershipListener;
/*     */     } 
/* 699 */     if (null != ownershipListener) {
/* 700 */       ownershipListener.ownershipChanged(paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   void registerOwershipListener(OwnershipListener paramOwnershipListener) {
/* 705 */     synchronized (this.stateLock) {
/* 706 */       this.ownershipListener = paramOwnershipListener;
/*     */     } 
/*     */   }
/*     */   
/*     */   void unregisterOwnershipListener() {
/* 711 */     synchronized (this.stateLock) {
/* 712 */       this.ownershipListener = null;
/*     */     }  } private static class SelectionEventHandler implements XEventDispatcher { private SelectionEventHandler() {} public void dispatchEvent(XEvent param1XEvent) {
/*     */       XSelectionRequestEvent xSelectionRequestEvent;
/*     */       XSelectionClearEvent xSelectionClearEvent;
/*     */       long l;
/*     */       XSelection xSelection;
/* 718 */       switch (param1XEvent.get_type()) {
/*     */         case 31:
/* 720 */           XToolkit.awtLock();
/*     */           try {
/* 722 */             XSelectionEvent xSelectionEvent = param1XEvent.get_xselection();
/*     */             
/* 724 */             if (XSelection.propertyGetter != null && xSelectionEvent.get_time() == XSelection.lastRequestServerTime)
/*     */             {
/* 726 */               if (xSelectionEvent.get_property() == XSelection.selectionPropertyAtom.getAtom()) {
/* 727 */                 XSelection.propertyGetter.execute();
/* 728 */                 XSelection.propertyGetter = null;
/* 729 */               } else if (xSelectionEvent.get_property() == 0L) {
/* 730 */                 XSelection.propertyGetter.dispose();
/* 731 */                 XSelection.propertyGetter = null;
/*     */               } 
/*     */             }
/* 734 */             XToolkit.awtLockNotifyAll();
/*     */           } finally {
/* 736 */             XToolkit.awtUnlock();
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 30:
/* 741 */           xSelectionRequestEvent = param1XEvent.get_xselectionrequest();
/* 742 */           l = xSelectionRequestEvent.get_selection();
/* 743 */           xSelection = XSelection.getSelection(XAtom.get(l));
/*     */           
/* 745 */           if (xSelection != null) {
/* 746 */             xSelection.handleSelectionRequest(xSelectionRequestEvent);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 29:
/* 751 */           xSelectionClearEvent = param1XEvent.get_xselectionclear();
/* 752 */           l = xSelectionClearEvent.get_selection();
/* 753 */           xSelection = XSelection.getSelection(XAtom.get(l));
/*     */           
/* 755 */           if (xSelection != null) {
/* 756 */             xSelection.lostOwnership();
/*     */           }
/*     */           
/* 759 */           XToolkit.awtLock();
/*     */           try {
/* 761 */             XToolkit.awtLockNotifyAll();
/*     */           } finally {
/* 763 */             XToolkit.awtUnlock();
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class IncrementalDataProvider
/*     */     implements XEventDispatcher {
/*     */     private final long requestor;
/*     */     private final long property;
/*     */     private final long target;
/*     */     private final int format;
/*     */     private final byte[] data;
/* 777 */     private int offset = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     public IncrementalDataProvider(long param1Long1, long param1Long2, long param1Long3, int param1Int, byte[] param1ArrayOfbyte) {
/* 782 */       if (param1Int != 8) {
/* 783 */         throw new IllegalArgumentException("Unsupported format: " + param1Int);
/*     */       }
/*     */       
/* 786 */       this.requestor = param1Long1;
/* 787 */       this.property = param1Long2;
/* 788 */       this.target = param1Long3;
/* 789 */       this.format = param1Int;
/* 790 */       this.data = param1ArrayOfbyte;
/*     */       
/* 792 */       XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*     */       try {
/* 794 */         XToolkit.awtLock();
/*     */         try {
/* 796 */           XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), param1Long1, xWindowAttributes.pData);
/*     */           
/* 798 */           XlibWrapper.XSelectInput(XToolkit.getDisplay(), param1Long1, xWindowAttributes
/* 799 */               .get_your_event_mask() | 0x400000L);
/*     */         } finally {
/*     */           
/* 802 */           XToolkit.awtUnlock();
/*     */         } 
/*     */       } finally {
/* 805 */         xWindowAttributes.dispose();
/*     */       } 
/* 807 */       XToolkit.addEventDispatcher(param1Long1, this);
/*     */     }
/*     */     public void dispatchEvent(XEvent param1XEvent) {
/*     */       XPropertyEvent xPropertyEvent;
/* 811 */       switch (param1XEvent.get_type()) {
/*     */         case 28:
/* 813 */           xPropertyEvent = param1XEvent.get_xproperty();
/* 814 */           if (xPropertyEvent.get_window() == this.requestor && xPropertyEvent
/* 815 */             .get_state() == 1 && xPropertyEvent
/* 816 */             .get_atom() == this.property) {
/*     */             
/* 818 */             int i = this.data.length - this.offset;
/* 819 */             long l = 0L;
/* 820 */             if (i > XSelection.MAX_PROPERTY_SIZE) {
/* 821 */               i = XSelection.MAX_PROPERTY_SIZE;
/*     */             }
/*     */             
/* 824 */             if (i > 0) {
/* 825 */               l = XlibWrapper.unsafe.allocateMemory(i);
/* 826 */               for (byte b = 0; b < i; b++) {
/* 827 */                 Native.putByte(l + b, this.data[this.offset + b]);
/*     */               }
/*     */             } else {
/* 830 */               assert i == 0;
/*     */ 
/*     */               
/* 833 */               XToolkit.removeEventDispatcher(this.requestor, this);
/*     */             } 
/*     */             
/* 836 */             XToolkit.awtLock();
/*     */             try {
/* 838 */               XlibWrapper.XChangeProperty(XToolkit.getDisplay(), this.requestor, this.property, this.target, this.format, 0, l, i);
/*     */             
/*     */             }
/*     */             finally {
/*     */ 
/*     */               
/* 844 */               XToolkit.awtUnlock();
/*     */             } 
/* 846 */             if (l != 0L) {
/* 847 */               XlibWrapper.unsafe.freeMemory(l);
/* 848 */               l = 0L;
/*     */             } 
/*     */             
/* 851 */             this.offset += i;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } }
/*     */   private static class IncrementalTransferHandler implements XEventDispatcher { private IncrementalTransferHandler() {}
/*     */     public void dispatchEvent(XEvent param1XEvent) {
/*     */       XPropertyEvent xPropertyEvent;
/* 859 */       switch (param1XEvent.get_type()) {
/*     */         case 28:
/* 861 */           xPropertyEvent = param1XEvent.get_xproperty();
/* 862 */           if (xPropertyEvent.get_state() == 0 && xPropertyEvent
/* 863 */             .get_atom() == XSelection.selectionPropertyAtom.getAtom()) {
/* 864 */             XToolkit.awtLock();
/*     */             try {
/* 866 */               if (XSelection.propertyGetter != null) {
/* 867 */                 XSelection.propertyGetter.execute();
/* 868 */                 XSelection.propertyGetter = null;
/*     */               } 
/* 870 */               XToolkit.awtLockNotifyAll();
/*     */             } finally {
/* 872 */               XToolkit.awtUnlock();
/*     */             } 
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */