/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.io.IOException;
/*      */ import sun.misc.Unsafe;
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
/*      */ class MotifDnDDropTargetProtocol
/*      */   extends XDropTargetProtocol
/*      */ {
/*   44 */   private static final Unsafe unsafe = XlibWrapper.unsafe;
/*      */   
/*   46 */   private long sourceWindow = 0L;
/*   47 */   private long sourceWindowMask = 0L;
/*   48 */   private int sourceProtocolVersion = 0;
/*   49 */   private int sourceActions = 0;
/*   50 */   private long[] sourceFormats = null;
/*   51 */   private long sourceAtom = 0L;
/*   52 */   private int userAction = 0;
/*   53 */   private int sourceX = 0;
/*   54 */   private int sourceY = 0;
/*   55 */   private XWindow targetXWindow = null;
/*      */   private boolean topLevelLeavePostponed = false;
/*      */   
/*      */   protected MotifDnDDropTargetProtocol(XDropTargetProtocolListener paramXDropTargetProtocolListener) {
/*   59 */     super(paramXDropTargetProtocolListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static XDropTargetProtocol createInstance(XDropTargetProtocolListener paramXDropTargetProtocolListener) {
/*   68 */     return new MotifDnDDropTargetProtocol(paramXDropTargetProtocolListener);
/*      */   }
/*      */   
/*      */   public String getProtocolName() {
/*   72 */     return "MotifDnD";
/*      */   }
/*      */   
/*      */   public void registerDropTarget(long paramLong) {
/*   76 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*   78 */     MotifDnDConstants.writeDragReceiverInfoStruct(paramLong);
/*      */   }
/*      */   
/*      */   public void unregisterDropTarget(long paramLong) {
/*   82 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*   84 */     MotifDnDConstants.XA_MOTIF_ATOM_0.DeleteProperty(paramLong);
/*      */   }
/*      */   
/*      */   public void registerEmbedderDropSite(long paramLong) {
/*   88 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*   90 */     boolean bool1 = false;
/*   91 */     boolean bool2 = false;
/*   92 */     long l1 = 0L;
/*   93 */     long l2 = XDropTargetRegistry.getDnDProxyWindow();
/*   94 */     int i = 0;
/*   95 */     long l3 = 0L;
/*   96 */     int j = 16;
/*      */     
/*   98 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO, 0L, 65535L, false, 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  105 */       i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
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
/*  121 */       if (i == 0 && windowPropertyGetter.getData() != 0L && windowPropertyGetter
/*  122 */         .getActualType() != 0L && windowPropertyGetter.getActualFormat() == 8 && windowPropertyGetter
/*  123 */         .getNumberOfItems() >= 16) {
/*      */ 
/*      */         
/*  126 */         bool1 = true;
/*  127 */         l3 = windowPropertyGetter.getData();
/*  128 */         j = windowPropertyGetter.getNumberOfItems();
/*      */         
/*  130 */         byte b = unsafe.getByte(l3);
/*      */ 
/*      */         
/*  133 */         int k = unsafe.getInt(l3 + 4L);
/*  134 */         if (b != MotifDnDConstants.getByteOrderByte()) {
/*  135 */           k = MotifDnDConstants.Swapper.swap(k);
/*      */         }
/*  137 */         l1 = k;
/*      */ 
/*      */         
/*  140 */         if (l1 == l2) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  146 */         k = (int)l2;
/*  147 */         if (b != MotifDnDConstants.getByteOrderByte()) {
/*  148 */           k = MotifDnDConstants.Swapper.swap(k);
/*      */         }
/*  150 */         unsafe.putInt(l3 + 4L, k);
/*      */       } else {
/*      */         
/*  153 */         l3 = unsafe.allocateMemory(j);
/*      */         
/*  155 */         unsafe.putByte(l3, MotifDnDConstants.getByteOrderByte());
/*  156 */         unsafe.putByte(l3 + 1L, (byte)0);
/*  157 */         unsafe.putByte(l3 + 2L, (byte)5);
/*  158 */         unsafe.putByte(l3 + 3L, (byte)0);
/*  159 */         unsafe.putInt(l3 + 4L, (int)l2);
/*  160 */         unsafe.putShort(l3 + 8L, (short)0);
/*  161 */         unsafe.putShort(l3 + 10L, (short)0);
/*  162 */         unsafe.putInt(l3 + 12L, j);
/*      */       } 
/*      */       
/*  165 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  166 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong, MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO
/*  167 */           .getAtom(), MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO
/*  168 */           .getAtom(), 8, 0, l3, j);
/*      */ 
/*      */       
/*  171 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*  173 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  174 */         .get_error_code() != 0) {
/*  175 */         throw new XException("Cannot write Motif receiver info property");
/*      */       }
/*      */     } finally {
/*  178 */       if (!bool1) {
/*  179 */         unsafe.freeMemory(l3);
/*  180 */         l3 = 0L;
/*      */       } 
/*  182 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */     
/*  185 */     putEmbedderRegistryEntry(paramLong, bool1, bool2, l1);
/*      */   }
/*      */   
/*      */   public void unregisterEmbedderDropSite(long paramLong) {
/*  189 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  191 */     XDropTargetProtocol.EmbedderRegistryEntry embedderRegistryEntry = getEmbedderRegistryEntry(paramLong);
/*      */     
/*  193 */     if (embedderRegistryEntry == null) {
/*      */       return;
/*      */     }
/*      */     
/*  197 */     if (embedderRegistryEntry.isOverriden()) {
/*  198 */       int i = 0;
/*      */       
/*  200 */       WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO, 0L, 65535L, false, 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  207 */         i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
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
/*  223 */         if (i == 0 && windowPropertyGetter.getData() != 0L && windowPropertyGetter
/*  224 */           .getActualType() != 0L && windowPropertyGetter.getActualFormat() == 8 && windowPropertyGetter
/*  225 */           .getNumberOfItems() >= 16) {
/*      */ 
/*      */           
/*  228 */           byte b = 16;
/*  229 */           long l = windowPropertyGetter.getData();
/*  230 */           byte b1 = unsafe.getByte(l);
/*      */           
/*  232 */           int j = (int)embedderRegistryEntry.getProxy();
/*  233 */           if (MotifDnDConstants.getByteOrderByte() != b1) {
/*  234 */             j = MotifDnDConstants.Swapper.swap(j);
/*      */           }
/*      */           
/*  237 */           unsafe.putInt(l + 4L, j);
/*      */           
/*  239 */           XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  240 */           XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong, MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO
/*  241 */               .getAtom(), MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO
/*  242 */               .getAtom(), 8, 0, l, b);
/*      */ 
/*      */           
/*  245 */           XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */           
/*  247 */           if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  248 */             .get_error_code() != 0) {
/*  249 */             throw new XException("Cannot write Motif receiver info property");
/*      */           }
/*      */         } 
/*      */       } finally {
/*  253 */         windowPropertyGetter.dispose();
/*      */       } 
/*      */     } else {
/*  256 */       MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO.DeleteProperty(paramLong);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerEmbeddedDropSite(long paramLong) {
/*  265 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  267 */     boolean bool1 = false;
/*  268 */     boolean bool2 = false;
/*  269 */     long l = 0L;
/*  270 */     int i = 0;
/*      */     
/*  272 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO, 0L, 65535L, false, 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  279 */       i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
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
/*  295 */       if (i == 0 && windowPropertyGetter.getData() != 0L && windowPropertyGetter
/*  296 */         .getActualType() != 0L && windowPropertyGetter.getActualFormat() == 8 && windowPropertyGetter
/*  297 */         .getNumberOfItems() >= 16) {
/*      */ 
/*      */         
/*  300 */         bool1 = true;
/*  301 */         long l1 = windowPropertyGetter.getData();
/*      */         
/*  303 */         byte b = unsafe.getByte(l1);
/*      */ 
/*      */         
/*  306 */         int j = unsafe.getInt(l1 + 4L);
/*  307 */         if (b != MotifDnDConstants.getByteOrderByte()) {
/*  308 */           j = MotifDnDConstants.Swapper.swap(j);
/*      */         }
/*  310 */         l = j;
/*      */       } 
/*      */     } finally {
/*      */       
/*  314 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */     
/*  317 */     putEmbedderRegistryEntry(paramLong, bool1, bool2, l);
/*      */   }
/*      */   
/*      */   public boolean isProtocolSupported(long paramLong) {
/*  321 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO, 0L, 65535L, false, 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  328 */       int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */       
/*  330 */       if (i == 0 && windowPropertyGetter.getData() != 0L && windowPropertyGetter
/*  331 */         .getActualType() != 0L && windowPropertyGetter.getActualFormat() == 8 && windowPropertyGetter
/*  332 */         .getNumberOfItems() >= 16)
/*      */       {
/*  334 */         return true;
/*      */       }
/*  336 */       return false;
/*      */     } finally {
/*      */       
/*  339 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean processTopLevelEnter(XClientMessageEvent paramXClientMessageEvent) {
/*  344 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  346 */     if (this.targetXWindow != null || this.sourceWindow != 0L) {
/*  347 */       return false;
/*      */     }
/*      */     
/*  350 */     if (!(XToolkit.windowToXWindow(paramXClientMessageEvent.get_window()) instanceof XWindow) && 
/*  351 */       getEmbedderRegistryEntry(paramXClientMessageEvent.get_window()) == null) {
/*  352 */       return false;
/*      */     }
/*      */     
/*  355 */     long l1 = 0L;
/*  356 */     long l2 = 0L;
/*  357 */     byte b = 0;
/*  358 */     long l3 = 0L;
/*  359 */     long[] arrayOfLong = null;
/*      */ 
/*      */     
/*  362 */     long l4 = paramXClientMessageEvent.get_data();
/*  363 */     byte b1 = unsafe.getByte(l4 + 1L);
/*  364 */     l1 = MotifDnDConstants.Swapper.getInt(l4 + 8L, b1);
/*  365 */     l3 = MotifDnDConstants.Swapper.getInt(l4 + 12L, b1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  375 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(l1, XAtom.get(l3), 0L, 65535L, false, MotifDnDConstants.XA_MOTIF_DRAG_INITIATOR_INFO.getAtom());
/*      */     
/*      */     try {
/*  378 */       int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*      */       
/*  380 */       if (i == 0 && windowPropertyGetter.getData() != 0L && windowPropertyGetter
/*  381 */         .getActualType() == MotifDnDConstants.XA_MOTIF_DRAG_INITIATOR_INFO
/*  382 */         .getAtom() && windowPropertyGetter
/*  383 */         .getActualFormat() == 8 && windowPropertyGetter
/*  384 */         .getNumberOfItems() == 8) {
/*      */ 
/*      */         
/*  387 */         long l = windowPropertyGetter.getData();
/*  388 */         byte b2 = unsafe.getByte(l);
/*      */         
/*  390 */         b = unsafe.getByte(l + 1L);
/*      */         
/*  392 */         if (b != 0)
/*      */         {
/*  394 */           return false;
/*      */         }
/*      */ 
/*      */         
/*  398 */         short s = MotifDnDConstants.Swapper.getShort(l + 2L, b2);
/*      */         
/*  400 */         arrayOfLong = MotifDnDConstants.getTargetListForIndex(s);
/*      */       } else {
/*  402 */         arrayOfLong = new long[0];
/*      */       } 
/*      */     } finally {
/*  405 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  413 */     XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*      */     try {
/*  415 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*  416 */       int i = XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), l1, xWindowAttributes.pData);
/*      */ 
/*      */       
/*  419 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*  421 */       if (i == 0 || (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*      */         
/*  423 */         .get_error_code() != 0)) {
/*  424 */         throw new XException("XGetWindowAttributes failed");
/*      */       }
/*      */       
/*  427 */       l2 = xWindowAttributes.get_your_event_mask();
/*      */     } finally {
/*  429 */       xWindowAttributes.dispose();
/*      */     } 
/*      */     
/*  432 */     XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*  433 */     XlibWrapper.XSelectInput(XToolkit.getDisplay(), l1, l2 | 0x20000L);
/*      */ 
/*      */ 
/*      */     
/*  437 */     XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */     
/*  439 */     if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  440 */       .get_error_code() != 0) {
/*  441 */       throw new XException("XSelectInput failed");
/*      */     }
/*      */     
/*  444 */     this.sourceWindow = l1;
/*  445 */     this.sourceWindowMask = l2;
/*  446 */     this.sourceProtocolVersion = b;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  451 */     this.sourceActions = 0;
/*  452 */     this.sourceFormats = arrayOfLong;
/*  453 */     this.sourceAtom = l3;
/*      */     
/*  455 */     return true;
/*      */   }
/*      */   
/*      */   private boolean processDragMotion(XClientMessageEvent paramXClientMessageEvent) {
/*  459 */     long l = paramXClientMessageEvent.get_data();
/*  460 */     byte b1 = unsafe.getByte(l + 1L);
/*  461 */     byte b2 = (byte)(unsafe.getByte(l) & Byte.MAX_VALUE);
/*      */     
/*  463 */     int i = 0;
/*  464 */     int j = 0;
/*      */     
/*  466 */     short s = MotifDnDConstants.Swapper.getShort(l + 2L, b1);
/*      */     
/*  468 */     int k = (s & 0xF) >> 0;
/*      */     
/*  470 */     int m = (s & 0xF00) >> 8;
/*      */ 
/*      */     
/*  473 */     int n = MotifDnDConstants.getJavaActionsForMotifActions(k);
/*  474 */     int i1 = MotifDnDConstants.getJavaActionsForMotifActions(m);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  479 */     int i2 = (int)this.sourceWindow;
/*  480 */     if (b1 != MotifDnDConstants.getByteOrderByte()) {
/*  481 */       i2 = MotifDnDConstants.Swapper.swap(i2);
/*      */     }
/*  483 */     unsafe.putInt(l + 12L, i2);
/*      */ 
/*      */     
/*  486 */     XWindow xWindow = null;
/*      */     
/*  488 */     XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramXClientMessageEvent.get_window());
/*  489 */     if (xBaseWindow instanceof XWindow) {
/*  490 */       xWindow = (XWindow)xBaseWindow;
/*      */     }
/*      */ 
/*      */     
/*  494 */     if (b2 == 8) {
/*      */ 
/*      */       
/*  497 */       i = this.sourceX;
/*  498 */       j = this.sourceY;
/*      */       
/*  500 */       if (xWindow == null) {
/*  501 */         xWindow = this.targetXWindow;
/*      */       }
/*      */     } else {
/*  504 */       i = MotifDnDConstants.Swapper.getShort(l + 8L, b1);
/*  505 */       j = MotifDnDConstants.Swapper.getShort(l + 10L, b1);
/*      */       
/*  507 */       if (xWindow == null) {
/*      */         
/*  509 */         long l1 = XDropTargetRegistry.getRegistry().getEmbeddedDropSite(paramXClientMessageEvent
/*  510 */             .get_window(), i, j);
/*      */         
/*  512 */         if (l1 != 0L) {
/*  513 */           XBaseWindow xBaseWindow1 = XToolkit.windowToXWindow(l1);
/*  514 */           if (xBaseWindow1 instanceof XWindow) {
/*  515 */             xWindow = (XWindow)xBaseWindow1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  520 */       if (xWindow != null) {
/*  521 */         Point point = xWindow.toLocal(i, j);
/*  522 */         i = point.x;
/*  523 */         j = point.y;
/*      */       } 
/*      */     } 
/*      */     
/*  527 */     if (xWindow == null) {
/*  528 */       if (this.targetXWindow != null) {
/*  529 */         notifyProtocolListener(this.targetXWindow, i, j, 0, i1, paramXClientMessageEvent, 505);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  534 */       char c = Character.MIN_VALUE;
/*      */       
/*  536 */       if (this.targetXWindow == null) {
/*  537 */         c = 'Ǹ';
/*      */       } else {
/*  539 */         c = 'Ǻ';
/*      */       } 
/*      */       
/*  542 */       notifyProtocolListener(xWindow, i, j, n, i1, paramXClientMessageEvent, c);
/*      */     } 
/*      */ 
/*      */     
/*  546 */     this.sourceActions = i1;
/*  547 */     this.userAction = n;
/*  548 */     this.sourceX = i;
/*  549 */     this.sourceY = j;
/*  550 */     this.targetXWindow = xWindow;
/*      */     
/*  552 */     return true;
/*      */   }
/*      */   private boolean processTopLevelLeave(XClientMessageEvent paramXClientMessageEvent) {
/*      */     long l3;
/*  556 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*      */     
/*  558 */     long l1 = paramXClientMessageEvent.get_data();
/*  559 */     byte b = unsafe.getByte(l1 + 1L);
/*      */     
/*  561 */     long l2 = MotifDnDConstants.Swapper.getInt(l1 + 8L, b);
/*      */ 
/*      */     
/*  564 */     if (l2 != this.sourceWindow) {
/*  565 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  574 */     this.topLevelLeavePostponed = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     if (getEmbedderRegistryEntry(paramXClientMessageEvent.get_window()) != null) {
/*  585 */       l3 = XDropTargetRegistry.getDnDProxyWindow();
/*      */     } else {
/*  587 */       l3 = paramXClientMessageEvent.get_window();
/*      */     } 
/*      */     
/*  590 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*      */     
/*      */     try {
/*  593 */       xClientMessageEvent.set_type(33);
/*  594 */       xClientMessageEvent.set_window(paramXClientMessageEvent.get_window());
/*  595 */       xClientMessageEvent.set_format(32);
/*  596 */       xClientMessageEvent.set_message_type(0L);
/*  597 */       xClientMessageEvent.set_data(0, 0L);
/*  598 */       xClientMessageEvent.set_data(1, 0L);
/*  599 */       xClientMessageEvent.set_data(2, 0L);
/*  600 */       xClientMessageEvent.set_data(3, 0L);
/*  601 */       xClientMessageEvent.set_data(4, 0L);
/*  602 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), l3, false, 0L, xClientMessageEvent.pData);
/*      */     }
/*      */     finally {
/*      */       
/*  606 */       xClientMessageEvent.dispose();
/*      */     } 
/*      */     
/*  609 */     return true;
/*      */   }
/*      */   
/*      */   private boolean processDropStart(XClientMessageEvent paramXClientMessageEvent) {
/*  613 */     long l1 = paramXClientMessageEvent.get_data();
/*  614 */     byte b = unsafe.getByte(l1 + 1L);
/*      */ 
/*      */     
/*  617 */     long l2 = MotifDnDConstants.Swapper.getInt(l1 + 16L, b);
/*      */ 
/*      */     
/*  620 */     if (l2 != this.sourceWindow) {
/*  621 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  625 */     long l3 = MotifDnDConstants.Swapper.getInt(l1 + 12L, b);
/*      */ 
/*      */     
/*  628 */     short s = MotifDnDConstants.Swapper.getShort(l1 + 2L, b);
/*      */     
/*  630 */     int i = (s & 0xF) >> 0;
/*      */     
/*  632 */     int j = (s & 0xF00) >> 8;
/*      */ 
/*      */     
/*  635 */     int k = MotifDnDConstants.getJavaActionsForMotifActions(i);
/*  636 */     int m = MotifDnDConstants.getJavaActionsForMotifActions(j);
/*      */     
/*  638 */     int n = MotifDnDConstants.Swapper.getShort(l1 + 8L, b);
/*  639 */     int i1 = MotifDnDConstants.Swapper.getShort(l1 + 10L, b);
/*      */     
/*  641 */     XWindow xWindow = null;
/*      */     
/*  643 */     XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramXClientMessageEvent.get_window());
/*  644 */     if (xBaseWindow instanceof XWindow) {
/*  645 */       xWindow = (XWindow)xBaseWindow;
/*      */     }
/*      */ 
/*      */     
/*  649 */     if (xWindow == null) {
/*      */       
/*  651 */       long l = XDropTargetRegistry.getRegistry().getEmbeddedDropSite(paramXClientMessageEvent
/*  652 */           .get_window(), n, i1);
/*      */       
/*  654 */       if (l != 0L) {
/*  655 */         XBaseWindow xBaseWindow1 = XToolkit.windowToXWindow(l);
/*  656 */         if (xBaseWindow1 instanceof XWindow) {
/*  657 */           xWindow = (XWindow)xBaseWindow1;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  662 */     if (xWindow != null) {
/*  663 */       Point point = xWindow.toLocal(n, i1);
/*  664 */       n = point.x;
/*  665 */       i1 = point.y;
/*      */     } 
/*      */     
/*  668 */     if (xWindow != null) {
/*  669 */       notifyProtocolListener(xWindow, n, i1, k, m, paramXClientMessageEvent, 502);
/*      */     }
/*  671 */     else if (this.targetXWindow != null) {
/*  672 */       notifyProtocolListener(this.targetXWindow, n, i1, 0, m, paramXClientMessageEvent, 505);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  677 */     return true;
/*      */   }
/*      */   
/*      */   public int getMessageType(XClientMessageEvent paramXClientMessageEvent) {
/*  681 */     if (paramXClientMessageEvent.get_message_type() != MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE
/*  682 */       .getAtom())
/*      */     {
/*  684 */       return 0;
/*      */     }
/*      */     
/*  687 */     long l = paramXClientMessageEvent.get_data();
/*  688 */     byte b = (byte)(unsafe.getByte(l) & Byte.MAX_VALUE);
/*      */ 
/*      */     
/*  691 */     switch (b) {
/*      */       case 0:
/*  693 */         return 1;
/*      */       case 2:
/*      */       case 8:
/*  696 */         return 2;
/*      */       case 1:
/*  698 */         return 3;
/*      */       case 5:
/*  700 */         return 4;
/*      */     } 
/*  702 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean processClientMessageImpl(XClientMessageEvent paramXClientMessageEvent) {
/*  707 */     if (paramXClientMessageEvent.get_message_type() != MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE
/*  708 */       .getAtom()) {
/*  709 */       if (this.topLevelLeavePostponed) {
/*  710 */         this.topLevelLeavePostponed = false;
/*  711 */         cleanup();
/*      */       } 
/*      */       
/*  714 */       return false;
/*      */     } 
/*      */     
/*  717 */     long l = paramXClientMessageEvent.get_data();
/*  718 */     byte b1 = (byte)(unsafe.getByte(l) & Byte.MAX_VALUE);
/*      */     
/*  720 */     byte b2 = (byte)(unsafe.getByte(l) & Byte.MIN_VALUE);
/*      */ 
/*      */     
/*  723 */     if (this.topLevelLeavePostponed) {
/*  724 */       this.topLevelLeavePostponed = false;
/*  725 */       if (b1 != 5) {
/*  726 */         cleanup();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  731 */     if (b2 != 0) {
/*  732 */       return false;
/*      */     }
/*      */     
/*  735 */     switch (b1) {
/*      */       case 0:
/*  737 */         return processTopLevelEnter(paramXClientMessageEvent);
/*      */       case 2:
/*      */       case 8:
/*  740 */         return processDragMotion(paramXClientMessageEvent);
/*      */       case 1:
/*  742 */         return processTopLevelLeave(paramXClientMessageEvent);
/*      */       case 5:
/*  744 */         return processDropStart(paramXClientMessageEvent);
/*      */     } 
/*  746 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void sendEnterMessageToToplevel(long paramLong, XClientMessageEvent paramXClientMessageEvent) {
/*  756 */     throw new Error("UNIMPLEMENTED");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendLeaveMessageToToplevel(long paramLong, XClientMessageEvent paramXClientMessageEvent) {
/*  761 */     throw new Error("UNIMPLEMENTED");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forwardEventToEmbedded(long paramLong1, long paramLong2, int paramInt) {
/*  767 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isXEmbedSupported() {
/*  771 */     return false;
/*      */   }
/*      */   
/*      */   public boolean sendResponse(long paramLong, int paramInt1, int paramInt2) {
/*  775 */     XClientMessageEvent xClientMessageEvent1 = new XClientMessageEvent(paramLong);
/*  776 */     if (xClientMessageEvent1.get_message_type() != MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE
/*  777 */       .getAtom()) {
/*  778 */       return false;
/*      */     }
/*      */     
/*  781 */     long l = xClientMessageEvent1.get_data();
/*  782 */     byte b1 = (byte)(unsafe.getByte(l) & Byte.MAX_VALUE);
/*      */     
/*  784 */     byte b2 = (byte)(unsafe.getByte(l) & Byte.MIN_VALUE);
/*      */     
/*  786 */     byte b3 = unsafe.getByte(l + 1L);
/*  787 */     byte b = 0;
/*      */ 
/*      */     
/*  790 */     if (b2 != 0) {
/*  791 */       return false;
/*      */     }
/*      */     
/*  794 */     switch (b1) {
/*      */       
/*      */       case 0:
/*      */       case 1:
/*  798 */         return false;
/*      */       case 2:
/*  800 */         switch (paramInt1) {
/*      */           case 504:
/*  802 */             b = 3;
/*      */             break;
/*      */           case 506:
/*  805 */             b = 2;
/*      */             break;
/*      */           case 505:
/*  808 */             b = 4;
/*      */             break;
/*      */         } 
/*      */         break;
/*      */       case 5:
/*      */       case 8:
/*  814 */         b = b1;
/*      */         break;
/*      */       
/*      */       default:
/*      */         assert false;
/*      */         break;
/*      */     } 
/*  821 */     XClientMessageEvent xClientMessageEvent2 = new XClientMessageEvent();
/*      */     
/*      */     try {
/*  824 */       xClientMessageEvent2.set_type(33);
/*  825 */       xClientMessageEvent2.set_window(MotifDnDConstants.Swapper.getInt(l + 12L, b3));
/*  826 */       xClientMessageEvent2.set_format(8);
/*  827 */       xClientMessageEvent2.set_message_type(MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE.getAtom());
/*      */       
/*  829 */       long l1 = xClientMessageEvent2.get_data();
/*      */       
/*  831 */       unsafe.putByte(l1, (byte)(b | 0xFFFFFF80));
/*      */       
/*  833 */       unsafe.putByte(l1 + 1L, MotifDnDConstants.getByteOrderByte());
/*      */       
/*  835 */       int i = 0;
/*      */       
/*  837 */       if (b != 4) {
/*  838 */         short s = MotifDnDConstants.Swapper.getShort(l + 2L, b3);
/*      */         
/*  840 */         byte b4 = (paramInt2 == 0) ? 2 : 3;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  845 */         i = s & 0xFFFFFFF0 & 0xFFFFFF0F;
/*      */ 
/*      */ 
/*      */         
/*  849 */         i |= 
/*  850 */           MotifDnDConstants.getMotifActionsForJavaActions(paramInt2) << 0;
/*      */         
/*  852 */         i |= b4 << 4;
/*      */       } else {
/*      */         
/*  855 */         i = 0;
/*      */       } 
/*      */       
/*  858 */       unsafe.putShort(l1 + 2L, (short)i);
/*      */ 
/*      */       
/*  861 */       int j = MotifDnDConstants.Swapper.getInt(l + 4L, b3);
/*  862 */       unsafe.putInt(l1 + 4L, j);
/*      */ 
/*      */       
/*  865 */       if (b != 4) {
/*  866 */         short s1 = MotifDnDConstants.Swapper.getShort(l + 8L, b3);
/*      */         
/*  868 */         short s2 = MotifDnDConstants.Swapper.getShort(l + 10L, b3);
/*      */         
/*  870 */         unsafe.putShort(l1 + 8L, s1);
/*  871 */         unsafe.putShort(l1 + 10L, s2);
/*      */       } else {
/*  873 */         unsafe.putShort(l1 + 8L, (short)0);
/*  874 */         unsafe.putShort(l1 + 10L, (short)0);
/*      */       } 
/*      */       
/*  877 */       XToolkit.awtLock();
/*      */       try {
/*  879 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), xClientMessageEvent2
/*  880 */             .get_window(), false, 0L, xClientMessageEvent2.pData);
/*      */       }
/*      */       finally {
/*      */         
/*  884 */         XToolkit.awtUnlock();
/*      */       } 
/*      */     } finally {
/*  887 */       xClientMessageEvent2.dispose();
/*      */     } 
/*      */     
/*  890 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getData(long paramLong1, long paramLong2) throws IllegalArgumentException, IOException {
/*  895 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent(paramLong1);
/*      */     
/*  897 */     if (xClientMessageEvent.get_message_type() != MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE
/*  898 */       .getAtom()) {
/*  899 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  902 */     long l1 = xClientMessageEvent.get_data();
/*  903 */     byte b1 = (byte)(unsafe.getByte(l1) & Byte.MAX_VALUE);
/*      */     
/*  905 */     byte b2 = (byte)(unsafe.getByte(l1) & Byte.MIN_VALUE);
/*      */     
/*  907 */     byte b3 = unsafe.getByte(l1 + 1L);
/*      */     
/*  909 */     if (b2 != 0) {
/*  910 */       throw new IOException("Cannot get data: corrupted context");
/*      */     }
/*      */     
/*  913 */     long l2 = 0L;
/*      */     
/*  915 */     switch (b1) {
/*      */       case 2:
/*      */       case 8:
/*  918 */         l2 = this.sourceAtom;
/*      */         break;
/*      */       case 5:
/*  921 */         l2 = MotifDnDConstants.Swapper.getInt(l1 + 12L, b3);
/*      */         break;
/*      */       default:
/*  924 */         throw new IOException("Cannot get data: invalid message reason");
/*      */     } 
/*      */     
/*  927 */     if (l2 == 0L) {
/*  928 */       throw new IOException("Cannot get data: drag source property atom unavailable");
/*      */     }
/*      */     
/*  931 */     long l3 = MotifDnDConstants.Swapper.getInt(l1 + 4L, b3) & 0xFFFFFFFFL;
/*      */ 
/*      */     
/*  934 */     XAtom xAtom = XAtom.get(l2);
/*      */     
/*  936 */     XSelection xSelection = XSelection.getSelection(xAtom);
/*  937 */     if (xSelection == null) {
/*  938 */       xSelection = new XSelection(xAtom);
/*      */     }
/*      */     
/*  941 */     return xSelection.getData(paramLong2, l3);
/*      */   }
/*      */   
/*      */   public boolean sendDropDone(long paramLong, boolean paramBoolean, int paramInt) {
/*  945 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent(paramLong);
/*      */     
/*  947 */     if (xClientMessageEvent.get_message_type() != MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE
/*  948 */       .getAtom()) {
/*  949 */       return false;
/*      */     }
/*      */     
/*  952 */     long l1 = xClientMessageEvent.get_data();
/*  953 */     byte b1 = (byte)(unsafe.getByte(l1) & Byte.MAX_VALUE);
/*      */     
/*  955 */     byte b2 = (byte)(unsafe.getByte(l1) & Byte.MIN_VALUE);
/*      */     
/*  957 */     byte b3 = unsafe.getByte(l1 + 1L);
/*      */     
/*  959 */     if (b2 != 0) {
/*  960 */       return false;
/*      */     }
/*      */     
/*  963 */     if (b1 != 5) {
/*  964 */       return false;
/*      */     }
/*      */     
/*  967 */     long l2 = MotifDnDConstants.Swapper.getInt(l1 + 4L, b3) & 0xFFFFFFFFL;
/*      */ 
/*      */     
/*  970 */     long l3 = MotifDnDConstants.Swapper.getInt(l1 + 12L, b3);
/*      */     
/*  972 */     long l4 = 0L;
/*      */     
/*  974 */     if (paramBoolean) {
/*  975 */       l4 = MotifDnDConstants.XA_XmTRANSFER_SUCCESS.getAtom();
/*      */     } else {
/*  977 */       l4 = MotifDnDConstants.XA_XmTRANSFER_FAILURE.getAtom();
/*      */     } 
/*      */     
/*  980 */     XToolkit.awtLock();
/*      */     try {
/*  982 */       XlibWrapper.XConvertSelection(XToolkit.getDisplay(), l3, l4, MotifDnDConstants.XA_MOTIF_ATOM_0
/*      */ 
/*      */           
/*  985 */           .getAtom(), 
/*  986 */           XWindow.getXAWTRootWindow().getWindow(), l2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  993 */       XlibWrapper.XFlush(XToolkit.getDisplay());
/*      */     } finally {
/*  995 */       XToolkit.awtUnlock();
/*      */     } 
/*      */ 
/*      */     
/*  999 */     this.targetXWindow = null;
/*      */ 
/*      */ 
/*      */     
/* 1003 */     cleanup();
/* 1004 */     return true;
/*      */   }
/*      */   
/*      */   public final long getSourceWindow() {
/* 1008 */     return this.sourceWindow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/* 1016 */     XDropTargetEventProcessor.reset();
/*      */     
/* 1018 */     if (this.targetXWindow != null) {
/* 1019 */       notifyProtocolListener(this.targetXWindow, 0, 0, 0, this.sourceActions, (XClientMessageEvent)null, 505);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1024 */     if (this.sourceWindow != 0L) {
/* 1025 */       XToolkit.awtLock();
/*      */       try {
/* 1027 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 1028 */         XlibWrapper.XSelectInput(XToolkit.getDisplay(), this.sourceWindow, this.sourceWindowMask);
/*      */         
/* 1030 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       } finally {
/* 1032 */         XToolkit.awtUnlock();
/*      */       } 
/*      */     } 
/*      */     
/* 1036 */     this.sourceWindow = 0L;
/* 1037 */     this.sourceWindowMask = 0L;
/* 1038 */     this.sourceProtocolVersion = 0;
/* 1039 */     this.sourceActions = 0;
/* 1040 */     this.sourceFormats = null;
/* 1041 */     this.sourceAtom = 0L;
/* 1042 */     this.userAction = 0;
/* 1043 */     this.sourceX = 0;
/* 1044 */     this.sourceY = 0;
/* 1045 */     this.targetXWindow = null;
/* 1046 */     this.topLevelLeavePostponed = false;
/*      */   }
/*      */   
/*      */   public boolean isDragOverComponent() {
/* 1050 */     return (this.targetXWindow != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void notifyProtocolListener(XWindow paramXWindow, int paramInt1, int paramInt2, int paramInt3, int paramInt4, XClientMessageEvent paramXClientMessageEvent, int paramInt5) {
/* 1057 */     long l = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1062 */     if (paramXClientMessageEvent != null) {
/* 1063 */       int i = XClientMessageEvent.getSize();
/*      */       
/* 1065 */       l = unsafe.allocateMemory((i + 4 * Native.getLongSize()));
/*      */       
/* 1067 */       unsafe.copyMemory(paramXClientMessageEvent.pData, l, i);
/*      */     } 
/*      */     
/* 1070 */     getProtocolListener().handleDropTargetNotification(paramXWindow, paramInt1, paramInt2, paramInt3, paramInt4, this.sourceFormats, l, paramInt5);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/MotifDnDDropTargetProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */