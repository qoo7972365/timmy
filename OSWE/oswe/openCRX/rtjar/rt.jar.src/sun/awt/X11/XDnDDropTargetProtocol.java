/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.io.IOException;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class XDnDDropTargetProtocol
/*      */   extends XDropTargetProtocol
/*      */ {
/*   47 */   private static final PlatformLogger logger = PlatformLogger.getLogger("sun.awt.X11.xembed.xdnd.XDnDDropTargetProtocol");
/*      */   
/*   49 */   private static final Unsafe unsafe = XlibWrapper.unsafe;
/*      */   
/*   51 */   private long sourceWindow = 0L;
/*   52 */   private long sourceWindowMask = 0L;
/*   53 */   private int sourceProtocolVersion = 0;
/*   54 */   private int sourceActions = 0;
/*   55 */   private long[] sourceFormats = null;
/*      */   private boolean trackSourceActions = false;
/*   57 */   private int userAction = 0;
/*   58 */   private int sourceX = 0;
/*   59 */   private int sourceY = 0;
/*   60 */   private XWindow targetXWindow = null;
/*      */ 
/*      */   
/*   63 */   private long prevCtxt = 0L;
/*      */   private boolean overXEmbedClient = false;
/*      */   
/*      */   protected XDnDDropTargetProtocol(XDropTargetProtocolListener paramXDropTargetProtocolListener) {
/*   67 */     super(paramXDropTargetProtocolListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static XDropTargetProtocol createInstance(XDropTargetProtocolListener paramXDropTargetProtocolListener) {
/*   76 */     return new XDnDDropTargetProtocol(paramXDropTargetProtocolListener);
/*      */   }
/*      */   
/*      */   public String getProtocolName() {
/*   80 */     return "XDnD";
/*      */   }
/*      */   
/*      */   public void registerDropTarget(long paramLong) {
/*   84 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*   86 */     long l = Native.allocateLongArray(1);
/*      */     
/*      */     try {
/*   89 */       Native.putLong(l, 0, 5L);
/*      */       
/*   91 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*   92 */       XDnDConstants.XA_XdndAware.setAtomData(paramLong, 4L, l, 1);
/*   93 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*   95 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*   96 */         .get_error_code() != 0) {
/*   97 */         throw new XException("Cannot write XdndAware property");
/*      */       }
/*      */     } finally {
/*  100 */       unsafe.freeMemory(l);
/*  101 */       l = 0L;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void unregisterDropTarget(long paramLong) {
/*  106 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  108 */     XDnDConstants.XA_XdndAware.DeleteProperty(paramLong);
/*      */   }
/*      */   
/*      */   public void registerEmbedderDropSite(long paramLong) {
/*  112 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  114 */     boolean bool = false;
/*  115 */     int i = 0;
/*  116 */     long l1 = 0L;
/*  117 */     long l2 = XDropTargetRegistry.getDnDProxyWindow();
/*  118 */     int j = 0;
/*      */     
/*  120 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, XDnDConstants.XA_XdndAware, 0L, 1L, false, 0L);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  125 */       j = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */       
/*  127 */       if (j == 0 && windowPropertyGetter
/*  128 */         .getData() != 0L && windowPropertyGetter.getActualType() == 4L) {
/*      */         
/*  130 */         bool = true;
/*  131 */         i = (int)Native.getLong(windowPropertyGetter.getData());
/*      */       } 
/*      */     } finally {
/*  134 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */ 
/*      */     
/*  138 */     if (bool && i >= 4) {
/*  139 */       WindowPropertyGetter windowPropertyGetter1 = new WindowPropertyGetter(paramLong, XDnDConstants.XA_XdndProxy, 0L, 1L, false, 33L);
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  144 */         j = windowPropertyGetter1.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */         
/*  146 */         if (j == 0 && windowPropertyGetter1
/*  147 */           .getData() != 0L && windowPropertyGetter1
/*  148 */           .getActualType() == 33L)
/*      */         {
/*  150 */           l1 = Native.getLong(windowPropertyGetter1.getData());
/*      */         }
/*      */       } finally {
/*  153 */         windowPropertyGetter1.dispose();
/*      */       } 
/*      */       
/*  156 */       if (l1 != 0L) {
/*  157 */         WindowPropertyGetter windowPropertyGetter2 = new WindowPropertyGetter(l1, XDnDConstants.XA_XdndProxy, 0L, 1L, false, 33L);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  162 */           j = windowPropertyGetter2.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */           
/*  164 */           if (j != 0 || windowPropertyGetter2
/*  165 */             .getData() == 0L || windowPropertyGetter2
/*  166 */             .getActualType() != 33L || 
/*  167 */             Native.getLong(windowPropertyGetter2.getData()) != l1) {
/*      */             
/*  169 */             l1 = 0L;
/*      */           } else {
/*  171 */             WindowPropertyGetter windowPropertyGetter3 = new WindowPropertyGetter(l1, XDnDConstants.XA_XdndAware, 0L, 1L, false, 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  178 */               j = windowPropertyGetter3.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */               
/*  180 */               if (j != 0 || windowPropertyGetter3
/*  181 */                 .getData() == 0L || windowPropertyGetter3
/*  182 */                 .getActualType() != 4L)
/*      */               {
/*  184 */                 l1 = 0L;
/*      */               }
/*      */             } finally {
/*  187 */               windowPropertyGetter3.dispose();
/*      */             } 
/*      */           } 
/*      */         } finally {
/*  191 */           windowPropertyGetter2.dispose();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  196 */     if (l1 == l2) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  201 */     long l3 = Native.allocateLongArray(1);
/*      */     
/*      */     try {
/*  204 */       Native.putLong(l3, 0, 5L);
/*      */ 
/*      */ 
/*      */       
/*  208 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  209 */       XDnDConstants.XA_XdndAware.setAtomData(l2, 4L, l3, 1);
/*      */       
/*  211 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*  213 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  214 */         .get_error_code() != 0) {
/*  215 */         throw new XException("Cannot write XdndAware property");
/*      */       }
/*      */       
/*  218 */       Native.putLong(l3, 0, l2);
/*      */ 
/*      */       
/*  221 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  222 */       XDnDConstants.XA_XdndProxy.setAtomData(l2, 33L, l3, 1);
/*      */       
/*  224 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*  226 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  227 */         .get_error_code() != 0) {
/*  228 */         throw new XException("Cannot write XdndProxy property");
/*      */       }
/*      */       
/*  231 */       Native.putLong(l3, 0, 5L);
/*      */       
/*  233 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  234 */       XDnDConstants.XA_XdndAware.setAtomData(paramLong, 4L, l3, 1);
/*      */       
/*  236 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*  238 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  239 */         .get_error_code() != 0) {
/*  240 */         throw new XException("Cannot write XdndAware property");
/*      */       }
/*      */       
/*  243 */       Native.putLong(l3, 0, l2);
/*      */       
/*  245 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  246 */       XDnDConstants.XA_XdndProxy.setAtomData(paramLong, 33L, l3, 1);
/*      */       
/*  248 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*  250 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  251 */         .get_error_code() != 0) {
/*  252 */         throw new XException("Cannot write XdndProxy property");
/*      */       }
/*      */     } finally {
/*  255 */       unsafe.freeMemory(l3);
/*  256 */       l3 = 0L;
/*      */     } 
/*      */     
/*  259 */     putEmbedderRegistryEntry(paramLong, bool, i, l1);
/*      */   }
/*      */   
/*      */   public void unregisterEmbedderDropSite(long paramLong) {
/*  263 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  265 */     XDropTargetProtocol.EmbedderRegistryEntry embedderRegistryEntry = getEmbedderRegistryEntry(paramLong);
/*      */     
/*  267 */     if (embedderRegistryEntry == null) {
/*      */       return;
/*      */     }
/*      */     
/*  271 */     if (embedderRegistryEntry.isOverriden()) {
/*  272 */       long l = Native.allocateLongArray(1);
/*      */       
/*      */       try {
/*  275 */         Native.putLong(l, 0, embedderRegistryEntry.getVersion());
/*      */         
/*  277 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  278 */         XDnDConstants.XA_XdndAware.setAtomData(paramLong, 4L, l, 1);
/*      */         
/*  280 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */         
/*  282 */         if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  283 */           .get_error_code() != 0) {
/*  284 */           throw new XException("Cannot write XdndAware property");
/*      */         }
/*      */         
/*  287 */         Native.putLong(l, 0, (int)embedderRegistryEntry.getProxy());
/*      */         
/*  289 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  290 */         XDnDConstants.XA_XdndProxy.setAtomData(paramLong, 33L, l, 1);
/*      */         
/*  292 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */         
/*  294 */         if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  295 */           .get_error_code() != 0) {
/*  296 */           throw new XException("Cannot write XdndProxy property");
/*      */         }
/*      */       } finally {
/*  299 */         unsafe.freeMemory(l);
/*  300 */         l = 0L;
/*      */       } 
/*      */     } else {
/*  303 */       XDnDConstants.XA_XdndAware.DeleteProperty(paramLong);
/*  304 */       XDnDConstants.XA_XdndProxy.DeleteProperty(paramLong);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerEmbeddedDropSite(long paramLong) {
/*  313 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  315 */     boolean bool = false;
/*  316 */     int i = 0;
/*  317 */     long l1 = 0L;
/*  318 */     long l2 = XDropTargetRegistry.getDnDProxyWindow();
/*  319 */     int j = 0;
/*      */     
/*  321 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, XDnDConstants.XA_XdndAware, 0L, 1L, false, 0L);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  326 */       j = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */       
/*  328 */       if (j == 0 && windowPropertyGetter
/*  329 */         .getData() != 0L && windowPropertyGetter.getActualType() == 4L) {
/*      */         
/*  331 */         bool = true;
/*  332 */         i = (int)Native.getLong(windowPropertyGetter.getData());
/*      */       } 
/*      */     } finally {
/*  335 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */ 
/*      */     
/*  339 */     if (bool && i >= 4) {
/*  340 */       WindowPropertyGetter windowPropertyGetter1 = new WindowPropertyGetter(paramLong, XDnDConstants.XA_XdndProxy, 0L, 1L, false, 33L);
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  345 */         j = windowPropertyGetter1.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */         
/*  347 */         if (j == 0 && windowPropertyGetter1
/*  348 */           .getData() != 0L && windowPropertyGetter1
/*  349 */           .getActualType() == 33L)
/*      */         {
/*  351 */           l1 = Native.getLong(windowPropertyGetter1.getData());
/*      */         }
/*      */       } finally {
/*  354 */         windowPropertyGetter1.dispose();
/*      */       } 
/*      */       
/*  357 */       if (l1 != 0L) {
/*  358 */         WindowPropertyGetter windowPropertyGetter2 = new WindowPropertyGetter(l1, XDnDConstants.XA_XdndProxy, 0L, 1L, false, 33L);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  363 */           j = windowPropertyGetter2.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */           
/*  365 */           if (j != 0 || windowPropertyGetter2
/*  366 */             .getData() == 0L || windowPropertyGetter2
/*  367 */             .getActualType() != 33L || 
/*  368 */             Native.getLong(windowPropertyGetter2.getData()) != l1) {
/*      */             
/*  370 */             l1 = 0L;
/*      */           } else {
/*  372 */             WindowPropertyGetter windowPropertyGetter3 = new WindowPropertyGetter(l1, XDnDConstants.XA_XdndAware, 0L, 1L, false, 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  379 */               j = windowPropertyGetter3.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */               
/*  381 */               if (j != 0 || windowPropertyGetter3
/*  382 */                 .getData() == 0L || windowPropertyGetter3
/*  383 */                 .getActualType() != 4L)
/*      */               {
/*  385 */                 l1 = 0L;
/*      */               }
/*      */             } finally {
/*  388 */               windowPropertyGetter3.dispose();
/*      */             } 
/*      */           } 
/*      */         } finally {
/*  392 */           windowPropertyGetter2.dispose();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  397 */     putEmbedderRegistryEntry(paramLong, bool, i, l1);
/*      */   }
/*      */   
/*      */   public boolean isProtocolSupported(long paramLong) {
/*  401 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  403 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, XDnDConstants.XA_XdndAware, 0L, 1L, false, 0L);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  408 */       int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */       
/*  410 */       if (i == 0 && windowPropertyGetter
/*  411 */         .getData() != 0L && windowPropertyGetter.getActualType() == 4L)
/*      */       {
/*  413 */         return true;
/*      */       }
/*  415 */       return false;
/*      */     } finally {
/*      */       
/*  418 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean processXdndEnter(XClientMessageEvent paramXClientMessageEvent) {
/*  423 */     long l1 = 0L;
/*  424 */     long l2 = 0L;
/*  425 */     int i = 0;
/*  426 */     int j = 0;
/*  427 */     boolean bool = true;
/*  428 */     long[] arrayOfLong = null;
/*      */     
/*  430 */     if (getSourceWindow() != 0L) {
/*  431 */       return false;
/*      */     }
/*      */     
/*  434 */     if (!(XToolkit.windowToXWindow(paramXClientMessageEvent.get_window()) instanceof XWindow) && 
/*  435 */       getEmbedderRegistryEntry(paramXClientMessageEvent.get_window()) == null) {
/*  436 */       return false;
/*      */     }
/*      */     
/*  439 */     if (paramXClientMessageEvent.get_message_type() != XDnDConstants.XA_XdndEnter.getAtom()) {
/*  440 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  444 */     i = (int)((paramXClientMessageEvent.get_data(1) & 0xFFFFFFFFFF000000L) >> 24L);
/*      */ 
/*      */ 
/*      */     
/*  448 */     if (i < 3) {
/*  449 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  453 */     if (i > 5) {
/*  454 */       return false;
/*      */     }
/*      */     
/*  457 */     l1 = paramXClientMessageEvent.get_data(0);
/*      */ 
/*      */     
/*  460 */     if (i < 2) {
/*      */       
/*  462 */       j = 1;
/*      */     } else {
/*  464 */       WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(l1, XDnDConstants.XA_XdndActionList, 0L, 65535L, false, 4L);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  470 */         windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */         
/*  472 */         if (windowPropertyGetter.getActualType() == 4L && windowPropertyGetter
/*  473 */           .getActualFormat() == 32) {
/*  474 */           long l = windowPropertyGetter.getData();
/*      */           
/*  476 */           for (byte b = 0; b < windowPropertyGetter.getNumberOfItems(); b++) {
/*  477 */             j |= 
/*  478 */               XDnDConstants.getJavaActionForXDnDAction(Native.getLong(l, b));
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  486 */           j = 1;
/*  487 */           bool = true;
/*      */         } 
/*      */       } finally {
/*  490 */         windowPropertyGetter.dispose();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  495 */     if ((paramXClientMessageEvent.get_data(1) & 0x1L) != 0L) {
/*  496 */       WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(l1, XDnDConstants.XA_XdndTypeList, 0L, 65535L, false, 4L);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  502 */         windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */         
/*  504 */         if (windowPropertyGetter.getActualType() == 4L && windowPropertyGetter
/*  505 */           .getActualFormat() == 32) {
/*  506 */           arrayOfLong = Native.toLongs(windowPropertyGetter.getData(), windowPropertyGetter
/*  507 */               .getNumberOfItems());
/*      */         } else {
/*  509 */           arrayOfLong = new long[0];
/*      */         } 
/*      */       } finally {
/*  512 */         windowPropertyGetter.dispose();
/*      */       } 
/*      */     } else {
/*  515 */       byte b1 = 0;
/*  516 */       long[] arrayOfLong1 = new long[3];
/*      */       
/*  518 */       for (byte b2 = 0; b2 < 3; b2++) {
/*      */         long l;
/*  520 */         if ((l = paramXClientMessageEvent.get_data(2 + b2)) != 0L) {
/*  521 */           arrayOfLong1[b1++] = l;
/*      */         }
/*      */       } 
/*      */       
/*  525 */       arrayOfLong = new long[b1];
/*      */       
/*  527 */       System.arraycopy(arrayOfLong1, 0, arrayOfLong, 0, b1);
/*      */     } 
/*      */     
/*  530 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  536 */     XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*      */     try {
/*  538 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*  539 */       int k = XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), l1, xWindowAttributes.pData);
/*      */ 
/*      */       
/*  542 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*  544 */       if (k == 0 || (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*      */         
/*  546 */         .get_error_code() != 0)) {
/*  547 */         throw new XException("XGetWindowAttributes failed");
/*      */       }
/*      */       
/*  550 */       l2 = xWindowAttributes.get_your_event_mask();
/*      */     } finally {
/*  552 */       xWindowAttributes.dispose();
/*      */     } 
/*      */     
/*  555 */     XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*  556 */     XlibWrapper.XSelectInput(XToolkit.getDisplay(), l1, l2 | 0x20000L);
/*      */ 
/*      */ 
/*      */     
/*  560 */     XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */     
/*  562 */     if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  563 */       .get_error_code() != 0) {
/*  564 */       throw new XException("XSelectInput failed");
/*      */     }
/*      */     
/*  567 */     this.sourceWindow = l1;
/*  568 */     this.sourceWindowMask = l2;
/*  569 */     this.sourceProtocolVersion = i;
/*  570 */     this.sourceActions = j;
/*  571 */     this.sourceFormats = arrayOfLong;
/*  572 */     this.trackSourceActions = bool;
/*      */     
/*  574 */     return true;
/*      */   }
/*      */   
/*      */   private boolean processXdndPosition(XClientMessageEvent paramXClientMessageEvent) {
/*  578 */     long l1 = 0L;
/*  579 */     long l2 = 0L;
/*  580 */     int i = 0;
/*  581 */     int j = 0;
/*  582 */     int k = 0;
/*      */ 
/*      */     
/*  585 */     if (this.sourceWindow != paramXClientMessageEvent.get_data(0)) {
/*  586 */       return false;
/*      */     }
/*      */     
/*  589 */     XWindow xWindow = null;
/*      */     
/*  591 */     XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramXClientMessageEvent.get_window());
/*  592 */     if (xBaseWindow instanceof XWindow) {
/*  593 */       xWindow = (XWindow)xBaseWindow;
/*      */     }
/*      */ 
/*      */     
/*  597 */     j = (int)(paramXClientMessageEvent.get_data(2) >> 16L);
/*  598 */     k = (int)(paramXClientMessageEvent.get_data(2) & 0xFFFFL);
/*      */     
/*  600 */     if (xWindow == null) {
/*      */       
/*  602 */       long l = XDropTargetRegistry.getRegistry().getEmbeddedDropSite(paramXClientMessageEvent
/*  603 */           .get_window(), j, k);
/*      */       
/*  605 */       if (l != 0L) {
/*  606 */         XBaseWindow xBaseWindow1 = XToolkit.windowToXWindow(l);
/*  607 */         if (xBaseWindow1 instanceof XWindow) {
/*  608 */           xWindow = (XWindow)xBaseWindow1;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  613 */     if (xWindow != null) {
/*      */ 
/*      */       
/*  616 */       Point point = xWindow.toLocal(j, k);
/*  617 */       j = point.x;
/*  618 */       k = point.y;
/*      */     } 
/*      */ 
/*      */     
/*  622 */     if (this.sourceProtocolVersion > 0) {
/*  623 */       l1 = paramXClientMessageEvent.get_data(3);
/*      */     }
/*      */ 
/*      */     
/*  627 */     if (this.sourceProtocolVersion > 1) {
/*  628 */       l2 = paramXClientMessageEvent.get_data(4);
/*      */     } else {
/*      */       
/*  631 */       l2 = XDnDConstants.XA_XdndActionCopy.getAtom();
/*      */     } 
/*      */     
/*  634 */     i = XDnDConstants.getJavaActionForXDnDAction(l2);
/*      */     
/*  636 */     if (this.trackSourceActions) {
/*  637 */       this.sourceActions |= i;
/*      */     }
/*      */     
/*  640 */     if (xWindow == null) {
/*  641 */       if (this.targetXWindow != null) {
/*  642 */         notifyProtocolListener(this.targetXWindow, j, k, 0, paramXClientMessageEvent, 505);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  647 */       char c = Character.MIN_VALUE;
/*      */       
/*  649 */       if (this.targetXWindow == null) {
/*  650 */         c = 'Ǹ';
/*      */       } else {
/*  652 */         c = 'Ǻ';
/*      */       } 
/*      */       
/*  655 */       notifyProtocolListener(xWindow, j, k, i, paramXClientMessageEvent, c);
/*      */     } 
/*      */ 
/*      */     
/*  659 */     this.userAction = i;
/*  660 */     this.sourceX = j;
/*  661 */     this.sourceY = k;
/*  662 */     this.targetXWindow = xWindow;
/*      */     
/*  664 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean processXdndLeave(XClientMessageEvent paramXClientMessageEvent) {
/*  669 */     if (this.sourceWindow != paramXClientMessageEvent.get_data(0)) {
/*  670 */       return false;
/*      */     }
/*      */     
/*  673 */     cleanup();
/*      */     
/*  675 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean processXdndDrop(XClientMessageEvent paramXClientMessageEvent) {
/*  680 */     if (this.sourceWindow != paramXClientMessageEvent.get_data(0)) {
/*  681 */       return false;
/*      */     }
/*      */     
/*  684 */     if (this.targetXWindow != null) {
/*  685 */       notifyProtocolListener(this.targetXWindow, this.sourceX, this.sourceY, this.userAction, paramXClientMessageEvent, 502);
/*      */     }
/*      */ 
/*      */     
/*  689 */     return true;
/*      */   }
/*      */   
/*      */   public int getMessageType(XClientMessageEvent paramXClientMessageEvent) {
/*  693 */     long l = paramXClientMessageEvent.get_message_type();
/*      */     
/*  695 */     if (l == XDnDConstants.XA_XdndEnter.getAtom())
/*  696 */       return 1; 
/*  697 */     if (l == XDnDConstants.XA_XdndPosition.getAtom())
/*  698 */       return 2; 
/*  699 */     if (l == XDnDConstants.XA_XdndLeave.getAtom())
/*  700 */       return 3; 
/*  701 */     if (l == XDnDConstants.XA_XdndDrop.getAtom()) {
/*  702 */       return 4;
/*      */     }
/*  704 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean processClientMessageImpl(XClientMessageEvent paramXClientMessageEvent) {
/*  709 */     long l = paramXClientMessageEvent.get_message_type();
/*      */     
/*  711 */     if (l == XDnDConstants.XA_XdndEnter.getAtom())
/*  712 */       return processXdndEnter(paramXClientMessageEvent); 
/*  713 */     if (l == XDnDConstants.XA_XdndPosition.getAtom())
/*  714 */       return processXdndPosition(paramXClientMessageEvent); 
/*  715 */     if (l == XDnDConstants.XA_XdndLeave.getAtom())
/*  716 */       return processXdndLeave(paramXClientMessageEvent); 
/*  717 */     if (l == XDnDConstants.XA_XdndDrop.getAtom()) {
/*  718 */       return processXdndDrop(paramXClientMessageEvent);
/*      */     }
/*  720 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void sendEnterMessageToToplevel(long paramLong, XClientMessageEvent paramXClientMessageEvent) {
/*  727 */     long l1 = (this.sourceProtocolVersion << 24);
/*  728 */     if (this.sourceFormats != null && this.sourceFormats.length > 3) {
/*  729 */       l1 |= 0x1L;
/*      */     }
/*  731 */     long l2 = (this.sourceFormats.length > 0) ? this.sourceFormats[0] : 0L;
/*  732 */     long l3 = (this.sourceFormats.length > 1) ? this.sourceFormats[1] : 0L;
/*  733 */     long l4 = (this.sourceFormats.length > 2) ? this.sourceFormats[2] : 0L;
/*  734 */     sendEnterMessageToToplevelImpl(paramLong, paramXClientMessageEvent.get_data(0), l1, l2, l3, l4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendEnterMessageToToplevelImpl(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6) {
/*  743 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*      */     try {
/*  745 */       xClientMessageEvent.set_type(33);
/*  746 */       xClientMessageEvent.set_window(paramLong1);
/*  747 */       xClientMessageEvent.set_format(32);
/*  748 */       xClientMessageEvent.set_message_type(XDnDConstants.XA_XdndEnter.getAtom());
/*      */       
/*  750 */       xClientMessageEvent.set_data(0, paramLong2);
/*  751 */       xClientMessageEvent.set_data(1, paramLong3);
/*  752 */       xClientMessageEvent.set_data(2, paramLong4);
/*  753 */       xClientMessageEvent.set_data(3, paramLong5);
/*  754 */       xClientMessageEvent.set_data(4, paramLong6);
/*      */       
/*  756 */       forwardClientMessageToToplevel(paramLong1, xClientMessageEvent);
/*      */     } finally {
/*  758 */       xClientMessageEvent.dispose();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendLeaveMessageToToplevel(long paramLong, XClientMessageEvent paramXClientMessageEvent) {
/*  764 */     sendLeaveMessageToToplevelImpl(paramLong, paramXClientMessageEvent.get_data(0));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendLeaveMessageToToplevelImpl(long paramLong1, long paramLong2) {
/*  769 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*      */     try {
/*  771 */       xClientMessageEvent.set_type(33);
/*  772 */       xClientMessageEvent.set_window(paramLong1);
/*  773 */       xClientMessageEvent.set_format(32);
/*  774 */       xClientMessageEvent.set_message_type(XDnDConstants.XA_XdndLeave.getAtom());
/*      */       
/*  776 */       xClientMessageEvent.set_data(0, paramLong2);
/*      */       
/*  778 */       xClientMessageEvent.set_data(1, 0L);
/*      */       
/*  780 */       forwardClientMessageToToplevel(paramLong1, xClientMessageEvent);
/*      */     } finally {
/*  782 */       xClientMessageEvent.dispose();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean sendResponse(long paramLong, int paramInt1, int paramInt2) {
/*  787 */     XClientMessageEvent xClientMessageEvent1 = new XClientMessageEvent(paramLong);
/*      */     
/*  789 */     if (xClientMessageEvent1.get_message_type() != XDnDConstants.XA_XdndPosition
/*  790 */       .getAtom())
/*      */     {
/*  792 */       return false;
/*      */     }
/*      */     
/*  795 */     if (paramInt1 == 505) {
/*  796 */       paramInt2 = 0;
/*      */     }
/*      */     
/*  799 */     XClientMessageEvent xClientMessageEvent2 = new XClientMessageEvent();
/*      */     try {
/*  801 */       xClientMessageEvent2.set_type(33);
/*  802 */       xClientMessageEvent2.set_window(xClientMessageEvent1.get_data(0));
/*  803 */       xClientMessageEvent2.set_format(32);
/*  804 */       xClientMessageEvent2.set_message_type(XDnDConstants.XA_XdndStatus.getAtom());
/*      */       
/*  806 */       xClientMessageEvent2.set_data(0, xClientMessageEvent1.get_window());
/*      */       
/*  808 */       long l = 0L;
/*  809 */       if (paramInt2 != 0) {
/*  810 */         l |= 0x1L;
/*      */       }
/*  812 */       xClientMessageEvent2.set_data(1, l);
/*      */       
/*  814 */       xClientMessageEvent2.set_data(2, 0L);
/*  815 */       xClientMessageEvent2.set_data(3, 0L);
/*      */       
/*  817 */       xClientMessageEvent2.set_data(4, XDnDConstants.getXDnDActionForJavaAction(paramInt2));
/*      */       
/*  819 */       XToolkit.awtLock();
/*      */       try {
/*  821 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), xClientMessageEvent1
/*  822 */             .get_data(0), false, 0L, xClientMessageEvent2.pData);
/*      */       }
/*      */       finally {
/*      */         
/*  826 */         XToolkit.awtUnlock();
/*      */       } 
/*      */     } finally {
/*  829 */       xClientMessageEvent2.dispose();
/*      */     } 
/*      */     
/*  832 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getData(long paramLong1, long paramLong2) throws IllegalArgumentException, IOException {
/*  837 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent(paramLong1);
/*  838 */     long l1 = xClientMessageEvent.get_message_type();
/*  839 */     long l2 = 0L;
/*      */ 
/*      */ 
/*      */     
/*  843 */     if (l1 == XDnDConstants.XA_XdndPosition.getAtom()) {
/*      */       
/*  845 */       l2 = xClientMessageEvent.get_data(3) & 0xFFFFFFFFL;
/*  846 */     } else if (l1 == XDnDConstants.XA_XdndDrop.getAtom()) {
/*      */       
/*  848 */       l2 = xClientMessageEvent.get_data(2) & 0xFFFFFFFFL;
/*      */     } else {
/*  850 */       throw new IllegalArgumentException();
/*      */     } 
/*      */     
/*  853 */     return XDnDConstants.XDnDSelection.getData(paramLong2, l2);
/*      */   }
/*      */   
/*      */   public boolean sendDropDone(long paramLong, boolean paramBoolean, int paramInt) {
/*  857 */     XClientMessageEvent xClientMessageEvent1 = new XClientMessageEvent(paramLong);
/*      */     
/*  859 */     if (xClientMessageEvent1.get_message_type() != XDnDConstants.XA_XdndDrop
/*  860 */       .getAtom()) {
/*  861 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     if (paramInt == 2 && paramBoolean) {
/*      */       
/*  870 */       long l1 = xClientMessageEvent1.get_data(2);
/*      */       
/*  872 */       long l2 = XDnDConstants.XDnDSelection.getSelectionAtom().getAtom();
/*      */       
/*  874 */       XToolkit.awtLock();
/*      */       try {
/*  876 */         XlibWrapper.XConvertSelection(XToolkit.getDisplay(), l2, 
/*      */             
/*  878 */             XAtom.get("DELETE").getAtom(), 
/*  879 */             XAtom.get("XAWT_SELECTION").getAtom(), 
/*  880 */             XWindow.getXAWTRootWindow().getWindow(), l1);
/*      */       } finally {
/*      */         
/*  883 */         XToolkit.awtUnlock();
/*      */       } 
/*      */     } 
/*      */     
/*  887 */     XClientMessageEvent xClientMessageEvent2 = new XClientMessageEvent();
/*      */     try {
/*  889 */       xClientMessageEvent2.set_type(33);
/*  890 */       xClientMessageEvent2.set_window(xClientMessageEvent1.get_data(0));
/*  891 */       xClientMessageEvent2.set_format(32);
/*  892 */       xClientMessageEvent2.set_message_type(XDnDConstants.XA_XdndFinished.getAtom());
/*  893 */       xClientMessageEvent2.set_data(0, xClientMessageEvent1.get_window());
/*  894 */       xClientMessageEvent2.set_data(1, 0L);
/*      */       
/*  896 */       xClientMessageEvent2.set_data(2, 0L);
/*  897 */       if (this.sourceProtocolVersion >= 5) {
/*  898 */         if (paramBoolean) {
/*  899 */           xClientMessageEvent2.set_data(1, 1L);
/*      */         }
/*      */         
/*  902 */         xClientMessageEvent2.set_data(2, XDnDConstants.getXDnDActionForJavaAction(paramInt));
/*      */       } 
/*  904 */       xClientMessageEvent2.set_data(3, 0L);
/*  905 */       xClientMessageEvent2.set_data(4, 0L);
/*      */       
/*  907 */       XToolkit.awtLock();
/*      */       try {
/*  909 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), xClientMessageEvent1
/*  910 */             .get_data(0), false, 0L, xClientMessageEvent2.pData);
/*      */       }
/*      */       finally {
/*      */         
/*  914 */         XToolkit.awtUnlock();
/*      */       } 
/*      */     } finally {
/*  917 */       xClientMessageEvent2.dispose();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  924 */     XToolkit.awtLock();
/*      */     try {
/*  926 */       XlibWrapper.XFlush(XToolkit.getDisplay());
/*      */     } finally {
/*  928 */       XToolkit.awtUnlock();
/*      */     } 
/*      */ 
/*      */     
/*  932 */     this.targetXWindow = null;
/*      */ 
/*      */ 
/*      */     
/*  936 */     cleanup();
/*  937 */     return true;
/*      */   }
/*      */   
/*      */   public final long getSourceWindow() {
/*  941 */     return this.sourceWindow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/*  949 */     XDropTargetEventProcessor.reset();
/*      */     
/*  951 */     if (this.targetXWindow != null) {
/*  952 */       notifyProtocolListener(this.targetXWindow, 0, 0, 0, (XClientMessageEvent)null, 505);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  957 */     if (this.sourceWindow != 0L) {
/*  958 */       XToolkit.awtLock();
/*      */       try {
/*  960 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*  961 */         XlibWrapper.XSelectInput(XToolkit.getDisplay(), this.sourceWindow, this.sourceWindowMask);
/*      */         
/*  963 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       } finally {
/*  965 */         XToolkit.awtUnlock();
/*      */       } 
/*      */     } 
/*      */     
/*  969 */     this.sourceWindow = 0L;
/*  970 */     this.sourceWindowMask = 0L;
/*  971 */     this.sourceProtocolVersion = 0;
/*  972 */     this.sourceActions = 0;
/*  973 */     this.sourceFormats = null;
/*  974 */     this.trackSourceActions = false;
/*  975 */     this.userAction = 0;
/*  976 */     this.sourceX = 0;
/*  977 */     this.sourceY = 0;
/*  978 */     this.targetXWindow = null;
/*      */   }
/*      */   
/*      */   public boolean isDragOverComponent() {
/*  982 */     return (this.targetXWindow != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void adjustEventForForwarding(XClientMessageEvent paramXClientMessageEvent, XDropTargetProtocol.EmbedderRegistryEntry paramEmbedderRegistryEntry) {
/*  988 */     int i = paramEmbedderRegistryEntry.getVersion();
/*  989 */     if (paramXClientMessageEvent.get_message_type() == XDnDConstants.XA_XdndEnter.getAtom()) {
/*  990 */       int j = (this.sourceProtocolVersion < i) ? this.sourceProtocolVersion : i;
/*      */       
/*  992 */       long l = (j << 24);
/*  993 */       if (this.sourceFormats != null && this.sourceFormats.length > 3) {
/*  994 */         l |= 0x1L;
/*      */       }
/*  996 */       if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/*  997 */         logger.finest("          entryVersion=" + i + " sourceProtocolVersion=" + this.sourceProtocolVersion + " sourceFormats.length=" + ((this.sourceFormats != null) ? this.sourceFormats.length : 0));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1004 */       paramXClientMessageEvent.set_data(1, l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void notifyProtocolListener(XWindow paramXWindow, int paramInt1, int paramInt2, int paramInt3, XClientMessageEvent paramXClientMessageEvent, int paramInt4) {
/* 1012 */     long l = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1017 */     if (paramXClientMessageEvent != null) {
/* 1018 */       new XClientMessageEvent(l); int i = XClientMessageEvent.getSize();
/*      */       
/* 1020 */       l = unsafe.allocateMemory((i + 4 * Native.getLongSize()));
/*      */       
/* 1022 */       unsafe.copyMemory(paramXClientMessageEvent.pData, l, i);
/*      */       
/* 1024 */       long l1 = (this.sourceProtocolVersion << 24);
/* 1025 */       if (this.sourceFormats != null && this.sourceFormats.length > 3) {
/* 1026 */         l1 |= 0x1L;
/*      */       }
/*      */       
/* 1029 */       Native.putLong(l + i, l1);
/* 1030 */       Native.putLong(l + i + Native.getLongSize(), (this.sourceFormats.length > 0) ? this.sourceFormats[0] : 0L);
/*      */       
/* 1032 */       Native.putLong(l + i + (2 * Native.getLongSize()), (this.sourceFormats.length > 1) ? this.sourceFormats[1] : 0L);
/*      */       
/* 1034 */       Native.putLong(l + i + (3 * Native.getLongSize()), (this.sourceFormats.length > 2) ? this.sourceFormats[2] : 0L);
/*      */     } 
/*      */ 
/*      */     
/* 1038 */     getProtocolListener().handleDropTargetNotification(paramXWindow, paramInt1, paramInt2, paramInt3, this.sourceActions, this.sourceFormats, l, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forwardEventToEmbedded(long paramLong1, long paramLong2, int paramInt) {
/* 1055 */     if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1056 */       logger.finest("        ctxt=" + paramLong2 + " type=" + ((paramLong2 != 0L) ? 
/*      */           
/* 1058 */           getMessageType(new XClientMessageEvent(paramLong2)) : 0) + " prevCtxt=" + this.prevCtxt + " prevType=" + ((this.prevCtxt != 0L) ? 
/*      */ 
/*      */ 
/*      */           
/* 1062 */           getMessageType(new XClientMessageEvent(this.prevCtxt)) : 0));
/*      */     }
/*      */     
/* 1065 */     if ((paramLong2 == 0L || 
/* 1066 */       getMessageType(new XClientMessageEvent(paramLong2)) == 0) && (this.prevCtxt == 0L || 
/*      */       
/* 1068 */       getMessageType(new XClientMessageEvent(this.prevCtxt)) == 0)) {
/* 1069 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1073 */     int i = XClientMessageEvent.getSize();
/*      */     
/* 1075 */     if (paramLong2 != 0L) {
/* 1076 */       XClientMessageEvent xClientMessageEvent1 = new XClientMessageEvent(paramLong2);
/* 1077 */       if (!this.overXEmbedClient) {
/* 1078 */         long l1 = Native.getLong(paramLong2 + i);
/* 1079 */         long l2 = Native.getLong(paramLong2 + i + Native.getLongSize());
/* 1080 */         long l3 = Native.getLong(paramLong2 + i + (2 * Native.getLongSize()));
/* 1081 */         long l4 = Native.getLong(paramLong2 + i + (3 * Native.getLongSize()));
/*      */         
/* 1083 */         if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1084 */           logger.finest("         1  embedded=" + paramLong1 + " source=" + xClientMessageEvent1
/*      */               
/* 1086 */               .get_data(0) + " data1=" + l1 + " data2=" + l2 + " data3=" + l3 + " data4=" + l4);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1094 */         if ((l1 & 0x1L) != 0L) {
/*      */           
/* 1096 */           WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(xClientMessageEvent1.get_data(0), XDnDConstants.XA_XdndTypeList, 0L, 65535L, false, 4L);
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1101 */             windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */             
/* 1103 */             if (windowPropertyGetter.getActualType() == 4L && windowPropertyGetter
/* 1104 */               .getActualFormat() == 32) {
/*      */               
/* 1106 */               XToolkit.awtLock();
/*      */               try {
/* 1108 */                 XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/* 1109 */                 XDnDConstants.XA_XdndTypeList.setAtomData(xClientMessageEvent1.get_window(), 4L, windowPropertyGetter
/*      */                     
/* 1111 */                     .getData(), windowPropertyGetter
/* 1112 */                     .getNumberOfItems());
/* 1113 */                 XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */                 
/* 1115 */                 if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 1116 */                   .get_error_code() != 0 && 
/* 1117 */                   logger.isLoggable(PlatformLogger.Level.WARNING)) {
/* 1118 */                   logger.warning("Cannot set XdndTypeList on the proxy window");
/*      */                 }
/*      */               } finally {
/*      */                 
/* 1122 */                 XToolkit.awtUnlock();
/*      */               }
/*      */             
/* 1125 */             } else if (logger.isLoggable(PlatformLogger.Level.WARNING)) {
/* 1126 */               logger.warning("Cannot read XdndTypeList from the source window");
/*      */             } 
/*      */           } finally {
/*      */             
/* 1130 */             windowPropertyGetter.dispose();
/*      */           } 
/*      */         } 
/* 1133 */         XDragSourceContextPeer.setProxyModeSourceWindow(xClientMessageEvent1.get_data(0));
/*      */         
/* 1135 */         sendEnterMessageToToplevelImpl(paramLong1, xClientMessageEvent1.get_window(), l1, l2, l3, l4);
/*      */         
/* 1137 */         this.overXEmbedClient = true;
/*      */       } 
/*      */       
/* 1140 */       if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1141 */         logger.finest("         2  embedded=" + paramLong1 + " xclient=" + xClientMessageEvent1);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1149 */       XClientMessageEvent xClientMessageEvent2 = new XClientMessageEvent();
/* 1150 */       unsafe.copyMemory(xClientMessageEvent1.pData, xClientMessageEvent2.pData, XClientMessageEvent.getSize());
/*      */       
/* 1152 */       xClientMessageEvent2.set_data(0, xClientMessageEvent1.get_window());
/*      */       
/* 1154 */       forwardClientMessageToToplevel(paramLong1, xClientMessageEvent2);
/*      */     } 
/*      */ 
/*      */     
/* 1158 */     if (paramInt == 505 && 
/* 1159 */       this.overXEmbedClient) {
/* 1160 */       if (paramLong2 != 0L || this.prevCtxt != 0L) {
/*      */         
/* 1162 */         XClientMessageEvent xClientMessageEvent = (paramLong2 != 0L) ? new XClientMessageEvent(paramLong2) : new XClientMessageEvent(this.prevCtxt);
/*      */ 
/*      */         
/* 1165 */         sendLeaveMessageToToplevelImpl(paramLong1, xClientMessageEvent.get_window());
/*      */       } 
/* 1167 */       this.overXEmbedClient = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1178 */       XDragSourceContextPeer.setProxyModeSourceWindow(0L);
/*      */     } 
/*      */ 
/*      */     
/* 1182 */     if (paramInt == 502) {
/* 1183 */       this.overXEmbedClient = false;
/* 1184 */       cleanup();
/*      */     } 
/*      */     
/* 1187 */     if (this.prevCtxt != 0L) {
/* 1188 */       unsafe.freeMemory(this.prevCtxt);
/* 1189 */       this.prevCtxt = 0L;
/*      */     } 
/*      */     
/* 1192 */     if (paramLong2 != 0L && this.overXEmbedClient) {
/* 1193 */       this.prevCtxt = unsafe.allocateMemory((i + 4 * Native.getLongSize()));
/*      */       
/* 1195 */       unsafe.copyMemory(paramLong2, this.prevCtxt, (i + 4 * Native.getLongSize()));
/*      */     } 
/*      */     
/* 1198 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isXEmbedSupported() {
/* 1202 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDnDDropTargetProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */