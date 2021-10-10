/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class XDropTargetRegistry
/*     */ {
/*  46 */   private static final PlatformLogger logger = PlatformLogger.getLogger("sun.awt.X11.xembed.xdnd.XDropTargetRegistry");
/*     */   
/*     */   private static final long DELAYED_REGISTRATION_PERIOD = 200L;
/*     */   
/*  50 */   private static final XDropTargetRegistry theInstance = new XDropTargetRegistry();
/*     */ 
/*     */   
/*  53 */   private final HashMap<Long, Runnable> delayedRegistrationMap = new HashMap<>();
/*     */   private final HashMap<Long, EmbeddedDropSiteEntry> embeddedDropSiteRegistry;
/*     */   private static final boolean XEMBED_PROTOCOLS = true;
/*     */   private static final boolean NON_XEMBED_PROTOCOLS = false;
/*     */   
/*     */   static XDropTargetRegistry getRegistry() {
/*  59 */     return theInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getToplevelWindow(long paramLong) {
/*  67 */     XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramLong);
/*  68 */     if (xBaseWindow != null) {
/*  69 */       XWindowPeer xWindowPeer = xBaseWindow.getToplevelXWindow();
/*  70 */       if (xWindowPeer != null && !(xWindowPeer instanceof XEmbeddedFramePeer)) {
/*  71 */         return xWindowPeer.getWindow();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/*  78 */       if (XlibUtil.isTrueToplevelWindow(paramLong)) {
/*  79 */         return paramLong;
/*     */       }
/*     */       
/*  82 */       paramLong = XlibUtil.getParentWindow(paramLong);
/*     */     }
/*  84 */     while (paramLong != 0L);
/*     */     
/*  86 */     return paramLong;
/*     */   }
/*     */   
/*     */   static final long getDnDProxyWindow() {
/*  90 */     return XWindow.getXAWTRootWindow().getWindow();
/*     */   }
/*     */   
/*     */   private static final class EmbeddedDropSiteEntry {
/*     */     private final long root;
/*     */     private final long event_mask;
/*     */     private List<XDropTargetProtocol> supportedProtocols;
/*  97 */     private final HashSet<Long> nonXEmbedClientSites = new HashSet<>();
/*  98 */     private final List<Long> sites = new ArrayList<>();
/*     */ 
/*     */     
/*     */     public EmbeddedDropSiteEntry(long param1Long1, long param1Long2, List<XDropTargetProtocol> param1List) {
/* 102 */       if (param1List == null) {
/* 103 */         throw new NullPointerException("Null supportedProtocols");
/*     */       }
/* 105 */       this.root = param1Long1;
/* 106 */       this.event_mask = param1Long2;
/* 107 */       this.supportedProtocols = param1List;
/*     */     }
/*     */     
/*     */     public long getRoot() {
/* 111 */       return this.root;
/*     */     }
/*     */     public long getEventMask() {
/* 114 */       return this.event_mask;
/*     */     }
/*     */     public boolean hasNonXEmbedClientSites() {
/* 117 */       return !this.nonXEmbedClientSites.isEmpty();
/*     */     }
/*     */     public synchronized void addSite(long param1Long, boolean param1Boolean) {
/* 120 */       Long long_ = Long.valueOf(param1Long);
/* 121 */       if (!this.sites.contains(long_)) {
/* 122 */         this.sites.add(long_);
/*     */       }
/* 124 */       if (!param1Boolean)
/* 125 */         this.nonXEmbedClientSites.add(long_); 
/*     */     }
/*     */     
/*     */     public synchronized void removeSite(long param1Long) {
/* 129 */       Long long_ = Long.valueOf(param1Long);
/* 130 */       this.sites.remove(long_);
/* 131 */       this.nonXEmbedClientSites.remove(long_);
/*     */     }
/*     */     public void setSupportedProtocols(List<XDropTargetProtocol> param1List) {
/* 134 */       this.supportedProtocols = param1List;
/*     */     }
/*     */     public List<XDropTargetProtocol> getSupportedProtocols() {
/* 137 */       return this.supportedProtocols;
/*     */     }
/*     */     public boolean hasSites() {
/* 140 */       return !this.sites.isEmpty();
/*     */     }
/*     */     public long[] getSites() {
/* 143 */       long[] arrayOfLong = new long[this.sites.size()];
/* 144 */       Iterator<Long> iterator = this.sites.iterator();
/* 145 */       byte b = 0;
/* 146 */       while (iterator.hasNext()) {
/* 147 */         Long long_ = iterator.next();
/* 148 */         arrayOfLong[b++] = long_.longValue();
/*     */       } 
/* 150 */       return arrayOfLong;
/*     */     }
/*     */     public long getSite(int param1Int1, int param1Int2) {
/* 153 */       assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */       
/* 155 */       Iterator<Long> iterator = this.sites.iterator();
/* 156 */       while (iterator.hasNext()) {
/* 157 */         Long long_ = iterator.next();
/* 158 */         long l = long_.longValue();
/*     */         
/* 160 */         Point point = XBaseWindow.toOtherWindow(getRoot(), l, param1Int1, param1Int2);
/*     */         
/* 162 */         if (point == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 166 */         int i = point.x;
/* 167 */         int j = point.y;
/* 168 */         if (i >= 0 && j >= 0) {
/* 169 */           XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*     */           
/* 171 */           try { XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 172 */             int k = XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), l, xWindowAttributes.pData);
/*     */             
/* 174 */             XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */             
/* 176 */             if (k == 0 || (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*     */               
/* 178 */               .get_error_code() != 0))
/*     */             
/*     */             { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 188 */               xWindowAttributes.dispose(); continue; }  if (xWindowAttributes.get_map_state() != 0 && i < xWindowAttributes.get_width() && j < xWindowAttributes.get_height()) return l;  } finally { xWindowAttributes.dispose(); }
/*     */         
/*     */         } 
/*     */       } 
/* 192 */       return 0L;
/*     */     } }
/*     */   
/*     */   private XDropTargetRegistry() {
/* 196 */     this.embeddedDropSiteRegistry = new HashMap<>();
/*     */   }
/*     */   
/*     */   private EmbeddedDropSiteEntry registerEmbedderDropSite(long paramLong) {
/* 200 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */ 
/*     */     
/* 203 */     Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */     
/* 205 */     ArrayList<XDropTargetProtocol> arrayList = new ArrayList();
/*     */     
/* 207 */     while (iterator.hasNext()) {
/*     */       
/* 209 */       XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 210 */       if (xDropTargetProtocol.isProtocolSupported(paramLong)) {
/* 211 */         arrayList.add(xDropTargetProtocol);
/*     */       }
/*     */     } 
/*     */     
/* 215 */     List<XDropTargetProtocol> list = Collections.unmodifiableList(arrayList);
/*     */ 
/*     */ 
/*     */     
/* 219 */     XlibWrapper.XGrabServer(XToolkit.getDisplay());
/*     */     try {
/* 221 */       long l1 = 0L;
/* 222 */       long l2 = 0L;
/* 223 */       XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*     */       try {
/* 225 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 226 */         int i = XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), paramLong, xWindowAttributes.pData);
/*     */         
/* 228 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */         
/* 230 */         if (i == 0 || (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*     */           
/* 232 */           .get_error_code() != 0)) {
/* 233 */           throw new XException("XGetWindowAttributes failed");
/*     */         }
/*     */         
/* 236 */         l2 = xWindowAttributes.get_your_event_mask();
/* 237 */         l1 = xWindowAttributes.get_root();
/*     */       } finally {
/* 239 */         xWindowAttributes.dispose();
/*     */       } 
/*     */       
/* 242 */       if ((l2 & 0x400000L) == 0L) {
/* 243 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 244 */         XlibWrapper.XSelectInput(XToolkit.getDisplay(), paramLong, l2 | 0x400000L);
/*     */         
/* 246 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */         
/* 248 */         if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 249 */           .get_error_code() != 0) {
/* 250 */           throw new XException("XSelectInput failed");
/*     */         }
/*     */       } 
/*     */       
/* 254 */       return new EmbeddedDropSiteEntry(l1, l2, list);
/*     */     } finally {
/* 256 */       XlibWrapper.XUngrabServer(XToolkit.getDisplay());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerProtocols(long paramLong, boolean paramBoolean, List<XDropTargetProtocol> paramList) {
/* 265 */     Iterator<XDropTargetProtocol> iterator = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     if (!paramList.isEmpty()) {
/* 281 */       iterator = paramList.iterator();
/*     */     } else {
/*     */       
/* 284 */       iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 289 */     XlibWrapper.XGrabServer(XToolkit.getDisplay());
/*     */     try {
/* 291 */       while (iterator.hasNext()) {
/*     */         
/* 293 */         XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 294 */         if (((paramBoolean == true)) == xDropTargetProtocol
/* 295 */           .isXEmbedSupported()) {
/* 296 */           xDropTargetProtocol.registerEmbedderDropSite(paramLong);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 300 */       XlibWrapper.XUngrabServer(XToolkit.getDisplay());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateEmbedderDropSite(long paramLong) {
/* 305 */     XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramLong);
/*     */     
/* 307 */     if (xBaseWindow != null) {
/*     */       return;
/*     */     }
/*     */     
/* 311 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */ 
/*     */     
/* 314 */     Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */     
/* 316 */     ArrayList<XDropTargetProtocol> arrayList = new ArrayList();
/*     */     
/* 318 */     while (iterator.hasNext()) {
/*     */       
/* 320 */       XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 321 */       if (xDropTargetProtocol.isProtocolSupported(paramLong)) {
/* 322 */         arrayList.add(xDropTargetProtocol);
/*     */       }
/*     */     } 
/*     */     
/* 326 */     List<XDropTargetProtocol> list = Collections.unmodifiableList(arrayList);
/*     */     
/* 328 */     Long long_ = Long.valueOf(paramLong);
/* 329 */     boolean bool = false;
/* 330 */     synchronized (this) {
/*     */       
/* 332 */       EmbeddedDropSiteEntry embeddedDropSiteEntry = this.embeddedDropSiteRegistry.get(long_);
/* 333 */       if (embeddedDropSiteEntry == null) {
/*     */         return;
/*     */       }
/* 336 */       embeddedDropSiteEntry.setSupportedProtocols(list);
/* 337 */       bool = !embeddedDropSiteEntry.hasNonXEmbedClientSites() ? true : false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 353 */     if (!list.isEmpty()) {
/* 354 */       iterator = list.iterator();
/*     */     } else {
/*     */       
/* 357 */       iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 362 */     XlibWrapper.XGrabServer(XToolkit.getDisplay());
/*     */     try {
/* 364 */       while (iterator.hasNext()) {
/*     */         
/* 366 */         XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 367 */         if (!bool || !xDropTargetProtocol.isXEmbedSupported()) {
/* 368 */           xDropTargetProtocol.registerEmbedderDropSite(paramLong);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 372 */       XlibWrapper.XUngrabServer(XToolkit.getDisplay());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void unregisterEmbedderDropSite(long paramLong, EmbeddedDropSiteEntry paramEmbeddedDropSiteEntry) {
/* 378 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */ 
/*     */     
/* 381 */     Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */ 
/*     */ 
/*     */     
/* 385 */     XlibWrapper.XGrabServer(XToolkit.getDisplay());
/*     */     try {
/* 387 */       while (iterator.hasNext()) {
/*     */         
/* 389 */         XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 390 */         xDropTargetProtocol.unregisterEmbedderDropSite(paramLong);
/*     */       } 
/*     */       
/* 393 */       long l = paramEmbeddedDropSiteEntry.getEventMask();
/*     */ 
/*     */       
/* 396 */       if ((l & 0x400000L) == 0L) {
/* 397 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 398 */         XlibWrapper.XSelectInput(XToolkit.getDisplay(), paramLong, l);
/*     */         
/* 400 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */         
/* 402 */         if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 403 */           .get_error_code() != 0) {
/* 404 */           throw new XException("XSelectInput failed");
/*     */         }
/*     */       } 
/*     */     } finally {
/* 408 */       XlibWrapper.XUngrabServer(XToolkit.getDisplay());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerEmbeddedDropSite(long paramLong1, long paramLong2) {
/* 413 */     XBaseWindow xBaseWindow1 = XToolkit.windowToXWindow(paramLong2);
/*     */ 
/*     */     
/* 416 */     boolean bool = (xBaseWindow1 instanceof XEmbeddedFramePeer && ((XEmbeddedFramePeer)xBaseWindow1).isXEmbedActive()) ? true : false;
/*     */     
/* 418 */     XEmbedCanvasPeer xEmbedCanvasPeer = null;
/*     */     
/* 420 */     XBaseWindow xBaseWindow2 = XToolkit.windowToXWindow(paramLong1);
/* 421 */     if (xBaseWindow2 != null) {
/* 422 */       if (xBaseWindow2 instanceof XEmbedCanvasPeer) {
/* 423 */         xEmbedCanvasPeer = (XEmbedCanvasPeer)xBaseWindow2;
/*     */       } else {
/* 425 */         throw new UnsupportedOperationException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 430 */     Long long_ = Long.valueOf(paramLong1);
/* 431 */     EmbeddedDropSiteEntry embeddedDropSiteEntry = null;
/* 432 */     synchronized (this) {
/*     */       
/* 434 */       embeddedDropSiteEntry = this.embeddedDropSiteRegistry.get(long_);
/* 435 */       if (embeddedDropSiteEntry == null) {
/* 436 */         if (xEmbedCanvasPeer != null) {
/*     */ 
/*     */           
/* 439 */           xEmbedCanvasPeer.setXEmbedDropTarget();
/*     */ 
/*     */           
/* 442 */           embeddedDropSiteEntry = new EmbeddedDropSiteEntry(0L, 0L, Collections.emptyList());
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 448 */           embeddedDropSiteEntry = registerEmbedderDropSite(paramLong1);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 453 */           registerProtocols(paramLong1, false, embeddedDropSiteEntry
/* 454 */               .getSupportedProtocols());
/*     */         } 
/* 456 */         this.embeddedDropSiteRegistry.put(long_, embeddedDropSiteEntry);
/*     */       } 
/*     */     } 
/*     */     
/* 460 */     assert embeddedDropSiteEntry != null;
/*     */     
/* 462 */     synchronized (embeddedDropSiteEntry) {
/*     */       
/* 464 */       if (xEmbedCanvasPeer == null) {
/* 465 */         if (!bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 472 */           registerProtocols(paramLong1, true, embeddedDropSiteEntry
/* 473 */               .getSupportedProtocols());
/*     */         } else {
/*     */           
/* 476 */           Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */ 
/*     */ 
/*     */           
/* 480 */           while (iterator.hasNext()) {
/*     */             
/* 482 */             XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 483 */             if (xDropTargetProtocol.isXEmbedSupported()) {
/* 484 */               xDropTargetProtocol.registerEmbedderDropSite(paramLong2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 490 */       embeddedDropSiteEntry.addSite(paramLong2, bool);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unregisterEmbeddedDropSite(long paramLong1, long paramLong2) {
/* 495 */     Long long_ = Long.valueOf(paramLong1);
/* 496 */     EmbeddedDropSiteEntry embeddedDropSiteEntry = null;
/* 497 */     synchronized (this) {
/*     */       
/* 499 */       embeddedDropSiteEntry = this.embeddedDropSiteRegistry.get(long_);
/* 500 */       if (embeddedDropSiteEntry == null) {
/*     */         return;
/*     */       }
/* 503 */       embeddedDropSiteEntry.removeSite(paramLong2);
/* 504 */       if (!embeddedDropSiteEntry.hasSites()) {
/* 505 */         this.embeddedDropSiteRegistry.remove(long_);
/*     */         
/* 507 */         XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramLong1);
/* 508 */         if (xBaseWindow != null) {
/* 509 */           if (xBaseWindow instanceof XEmbedCanvasPeer) {
/* 510 */             XEmbedCanvasPeer xEmbedCanvasPeer = (XEmbedCanvasPeer)xBaseWindow;
/*     */             
/* 512 */             xEmbedCanvasPeer.removeXEmbedDropTarget();
/*     */           } else {
/* 514 */             throw new UnsupportedOperationException();
/*     */           } 
/*     */         } else {
/* 517 */           unregisterEmbedderDropSite(paramLong1, embeddedDropSiteEntry);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getEmbeddedDropSite(long paramLong, int paramInt1, int paramInt2) {
/* 528 */     Long long_ = Long.valueOf(paramLong);
/*     */     
/* 530 */     EmbeddedDropSiteEntry embeddedDropSiteEntry = this.embeddedDropSiteRegistry.get(long_);
/* 531 */     if (embeddedDropSiteEntry == null) {
/* 532 */       return 0L;
/*     */     }
/* 534 */     return embeddedDropSiteEntry.getSite(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerDropSite(long paramLong) {
/* 541 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 543 */     if (paramLong == 0L) {
/* 544 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 547 */     XDropTargetEventProcessor.activate();
/*     */     
/* 549 */     long l = getToplevelWindow(paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 559 */     if (l == 0L) {
/* 560 */       addDelayedRegistrationEntry(paramLong);
/*     */       
/*     */       return;
/*     */     } 
/* 564 */     if (l == paramLong) {
/*     */       
/* 566 */       Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */       
/* 568 */       while (iterator.hasNext()) {
/*     */         
/* 570 */         XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 571 */         xDropTargetProtocol.registerDropTarget(l);
/*     */       } 
/*     */     } else {
/* 574 */       registerEmbeddedDropSite(l, paramLong);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterDropSite(long paramLong) {
/* 582 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 584 */     if (paramLong == 0L) {
/* 585 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 588 */     long l = getToplevelWindow(paramLong);
/*     */     
/* 590 */     if (l == paramLong) {
/*     */       
/* 592 */       Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */       
/* 594 */       removeDelayedRegistrationEntry(paramLong);
/*     */       
/* 596 */       while (iterator.hasNext()) {
/* 597 */         XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 598 */         xDropTargetProtocol.unregisterDropTarget(paramLong);
/*     */       } 
/*     */     } else {
/* 601 */       unregisterEmbeddedDropSite(l, paramLong);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerXEmbedClient(long paramLong1, long paramLong2) {
/* 611 */     XDragSourceProtocol xDragSourceProtocol = XDragAndDropProtocols.getDragSourceProtocol("XDnD");
/*     */     
/* 613 */     XDragSourceProtocol.TargetWindowInfo targetWindowInfo = xDragSourceProtocol.getTargetWindowInfo(paramLong2);
/* 614 */     if (targetWindowInfo != null && targetWindowInfo
/* 615 */       .getProtocolVersion() >= 3) {
/*     */       
/* 617 */       if (logger.isLoggable(PlatformLogger.Level.FINE)) {
/* 618 */         logger.fine("        XEmbed drop site will be registered for " + Long.toHexString(paramLong2));
/*     */       }
/* 620 */       registerEmbeddedDropSite(paramLong1, paramLong2);
/*     */ 
/*     */       
/* 623 */       Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */       
/* 625 */       while (iterator.hasNext()) {
/*     */         
/* 627 */         XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 628 */         xDropTargetProtocol.registerEmbeddedDropSite(paramLong2);
/*     */       } 
/*     */       
/* 631 */       if (logger.isLoggable(PlatformLogger.Level.FINE)) {
/* 632 */         logger.fine("        XEmbed drop site has been registered for " + Long.toHexString(paramLong2));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unregisterXEmbedClient(long paramLong1, long paramLong2) {
/* 638 */     if (logger.isLoggable(PlatformLogger.Level.FINE)) {
/* 639 */       logger.fine("        XEmbed drop site will be unregistered for " + Long.toHexString(paramLong2));
/*     */     }
/*     */     
/* 642 */     Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */     
/* 644 */     while (iterator.hasNext()) {
/*     */       
/* 646 */       XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 647 */       xDropTargetProtocol.unregisterEmbeddedDropSite(paramLong2);
/*     */     } 
/*     */     
/* 650 */     unregisterEmbeddedDropSite(paramLong1, paramLong2);
/*     */     
/* 652 */     if (logger.isLoggable(PlatformLogger.Level.FINE)) {
/* 653 */       logger.fine("        XEmbed drop site has beed unregistered for " + Long.toHexString(paramLong2));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addDelayedRegistrationEntry(final long window) {
/* 660 */     Long long_ = Long.valueOf(window);
/* 661 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 663 */           XDropTargetRegistry.this.removeDelayedRegistrationEntry(window);
/* 664 */           XDropTargetRegistry.this.registerDropSite(window);
/*     */         }
/*     */       };
/*     */     
/* 668 */     XToolkit.awtLock();
/*     */     try {
/* 670 */       removeDelayedRegistrationEntry(window);
/* 671 */       this.delayedRegistrationMap.put(long_, runnable);
/* 672 */       XToolkit.schedule(runnable, 200L);
/*     */     } finally {
/* 674 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeDelayedRegistrationEntry(long paramLong) {
/* 679 */     Long long_ = Long.valueOf(paramLong);
/*     */     
/* 681 */     XToolkit.awtLock();
/*     */     try {
/* 683 */       Runnable runnable = this.delayedRegistrationMap.remove(long_);
/* 684 */       if (runnable != null) {
/* 685 */         XToolkit.remove(runnable);
/*     */       }
/*     */     } finally {
/* 688 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDropTargetRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */