/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.util.Map;
/*     */ import sun.misc.Unsafe;
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
/*     */ 
/*     */ 
/*     */ class XDnDDragSourceProtocol
/*     */   extends XDragSourceProtocol
/*     */ {
/*  46 */   private static final PlatformLogger logger = PlatformLogger.getLogger("sun.awt.X11.xembed.xdnd.XDnDDragSourceProtocol");
/*     */   
/*  48 */   private static final Unsafe unsafe = XlibWrapper.unsafe;
/*     */   
/*     */   protected XDnDDragSourceProtocol(XDragSourceProtocolListener paramXDragSourceProtocolListener) {
/*  51 */     super(paramXDragSourceProtocolListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static XDragSourceProtocol createInstance(XDragSourceProtocolListener paramXDragSourceProtocolListener) {
/*  60 */     return new XDnDDragSourceProtocol(paramXDragSourceProtocolListener);
/*     */   }
/*     */   
/*     */   public String getProtocolName() {
/*  64 */     return "XDnD";
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
/*     */   protected void initializeDragImpl(int paramInt, Transferable paramTransferable, Map paramMap, long[] paramArrayOflong) throws InvalidDnDOperationException, IllegalArgumentException, XException {
/*  76 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/*  78 */     long l1 = XDragSourceProtocol.getDragSourceWindow();
/*     */     
/*  80 */     long l2 = Native.allocateLongArray(3);
/*  81 */     byte b = 0;
/*     */     try {
/*  83 */       if ((paramInt & 0x1) != 0) {
/*  84 */         Native.putLong(l2, b, XDnDConstants.XA_XdndActionCopy
/*  85 */             .getAtom());
/*  86 */         b++;
/*     */       } 
/*  88 */       if ((paramInt & 0x2) != 0) {
/*  89 */         Native.putLong(l2, b, XDnDConstants.XA_XdndActionMove
/*  90 */             .getAtom());
/*  91 */         b++;
/*     */       } 
/*  93 */       if ((paramInt & 0x40000000) != 0) {
/*  94 */         Native.putLong(l2, b, XDnDConstants.XA_XdndActionLink
/*  95 */             .getAtom());
/*  96 */         b++;
/*     */       } 
/*     */       
/*  99 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/* 100 */       XDnDConstants.XA_XdndActionList.setAtomData(l1, 4L, l2, b);
/*     */ 
/*     */       
/* 103 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       
/* 105 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 106 */         .get_error_code() != 0) {
/* 107 */         cleanup();
/* 108 */         throw new XException("Cannot write XdndActionList property");
/*     */       } 
/*     */     } finally {
/* 111 */       unsafe.freeMemory(l2);
/* 112 */       l2 = 0L;
/*     */     } 
/*     */     
/* 115 */     l2 = Native.allocateLongArray(paramArrayOflong.length);
/*     */     
/*     */     try {
/* 118 */       Native.put(l2, paramArrayOflong);
/*     */       
/* 120 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/* 121 */       XDnDConstants.XA_XdndTypeList.setAtomData(l1, 4L, l2, paramArrayOflong.length);
/*     */ 
/*     */       
/* 124 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       
/* 126 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 127 */         .get_error_code() != 0) {
/* 128 */         cleanup();
/* 129 */         throw new XException("Cannot write XdndActionList property");
/*     */       } 
/*     */     } finally {
/* 132 */       unsafe.freeMemory(l2);
/* 133 */       l2 = 0L;
/*     */     } 
/*     */     
/* 136 */     if (!XDnDConstants.XDnDSelection.setOwner(paramTransferable, paramMap, paramArrayOflong, 0L)) {
/*     */       
/* 138 */       cleanup();
/* 139 */       throw new InvalidDnDOperationException("Cannot acquire selection ownership");
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean processXdndStatus(XClientMessageEvent paramXClientMessageEvent) {
/* 144 */     int i = 0;
/*     */ 
/*     */     
/* 147 */     if (paramXClientMessageEvent.get_data(0) != getTargetWindow()) {
/* 148 */       return true;
/*     */     }
/*     */     
/* 151 */     if ((paramXClientMessageEvent.get_data(1) & 0x1L) != 0L)
/*     */     {
/*     */       
/* 154 */       i = XDnDConstants.getJavaActionForXDnDAction(paramXClientMessageEvent.get_data(4));
/*     */     }
/*     */     
/* 157 */     getProtocolListener().handleDragReply(i);
/*     */     
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean processXdndFinished(XClientMessageEvent paramXClientMessageEvent) {
/* 164 */     if (paramXClientMessageEvent.get_data(0) != getTargetWindow()) {
/* 165 */       return true;
/*     */     }
/*     */     
/* 168 */     if (getTargetProtocolVersion() >= 5) {
/* 169 */       boolean bool = ((paramXClientMessageEvent.get_data(1) & 0x1L) != 0L) ? true : false;
/* 170 */       int i = XDnDConstants.getJavaActionForXDnDAction(paramXClientMessageEvent.get_data(2));
/* 171 */       getProtocolListener().handleDragFinished(bool, i);
/*     */     } else {
/* 173 */       getProtocolListener().handleDragFinished();
/*     */     } 
/*     */     
/* 176 */     finalizeDrop();
/*     */     
/* 178 */     return true;
/*     */   }
/*     */   
/*     */   public boolean processClientMessage(XClientMessageEvent paramXClientMessageEvent) {
/* 182 */     if (paramXClientMessageEvent.get_message_type() == XDnDConstants.XA_XdndStatus.getAtom())
/* 183 */       return processXdndStatus(paramXClientMessageEvent); 
/* 184 */     if (paramXClientMessageEvent.get_message_type() == XDnDConstants.XA_XdndFinished.getAtom()) {
/* 185 */       return processXdndFinished(paramXClientMessageEvent);
/*     */     }
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public XDragSourceProtocol.TargetWindowInfo getTargetWindowInfo(long paramLong) {
/* 192 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 194 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, XDnDConstants.XA_XdndAware, 0L, 1L, false, 0L);
/*     */ 
/*     */ 
/*     */     
/* 198 */     int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*     */     
/* 200 */     if (i == 0 && windowPropertyGetter
/* 201 */       .getData() != 0L && windowPropertyGetter.getActualType() == 4L) {
/*     */       
/* 203 */       int j = (int)Native.getLong(windowPropertyGetter.getData());
/*     */       
/* 205 */       windowPropertyGetter.dispose();
/*     */       
/* 207 */       if (j >= 3) {
/* 208 */         long l = 0L;
/* 209 */         boolean bool = (j < 5) ? j : true;
/*     */ 
/*     */ 
/*     */         
/* 213 */         WindowPropertyGetter windowPropertyGetter1 = new WindowPropertyGetter(paramLong, XDnDConstants.XA_XdndProxy, 0L, 1L, false, 33L);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 218 */           i = windowPropertyGetter1.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*     */           
/* 220 */           if (i == 0 && windowPropertyGetter1
/* 221 */             .getData() != 0L && windowPropertyGetter1
/* 222 */             .getActualType() == 33L)
/*     */           {
/* 224 */             l = Native.getLong(windowPropertyGetter1.getData());
/*     */           }
/*     */         } finally {
/* 227 */           windowPropertyGetter1.dispose();
/*     */         } 
/*     */         
/* 230 */         if (l != 0L) {
/* 231 */           WindowPropertyGetter windowPropertyGetter2 = new WindowPropertyGetter(l, XDnDConstants.XA_XdndProxy, 0L, 1L, false, 33L);
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 236 */             i = windowPropertyGetter2.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*     */             
/* 238 */             if (i != 0 || windowPropertyGetter2
/* 239 */               .getData() == 0L || windowPropertyGetter2
/* 240 */               .getActualType() != 33L || 
/* 241 */               Native.getLong(windowPropertyGetter2.getData()) != l) {
/*     */               
/* 243 */               l = 0L;
/*     */             } else {
/* 245 */               WindowPropertyGetter windowPropertyGetter3 = new WindowPropertyGetter(l, XDnDConstants.XA_XdndAware, 0L, 1L, false, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 252 */                 i = windowPropertyGetter3.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*     */                 
/* 254 */                 if (i != 0 || windowPropertyGetter3
/* 255 */                   .getData() == 0L || windowPropertyGetter3
/* 256 */                   .getActualType() != 4L)
/*     */                 {
/* 258 */                   l = 0L;
/*     */                 }
/*     */               } finally {
/* 261 */                 windowPropertyGetter3.dispose();
/*     */               } 
/*     */             } 
/*     */           } finally {
/* 265 */             windowPropertyGetter2.dispose();
/*     */           } 
/*     */         } 
/*     */         
/* 269 */         return new XDragSourceProtocol.TargetWindowInfo(l, bool);
/*     */       } 
/*     */     } else {
/* 272 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */     
/* 275 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendEnterMessage(long[] paramArrayOflong, int paramInt1, int paramInt2, long paramLong) {
/* 280 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/* 281 */     assert getTargetWindow() != 0L;
/* 282 */     assert paramArrayOflong != null;
/*     */     
/* 284 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 286 */       xClientMessageEvent.set_type(33);
/* 287 */       xClientMessageEvent.set_window(getTargetWindow());
/* 288 */       xClientMessageEvent.set_format(32);
/* 289 */       xClientMessageEvent.set_message_type(XDnDConstants.XA_XdndEnter.getAtom());
/* 290 */       xClientMessageEvent.set_data(0, XDragSourceProtocol.getDragSourceWindow());
/*     */       
/* 292 */       long l = (getTargetProtocolVersion() << 24);
/* 293 */       l |= (paramArrayOflong.length > 3) ? 1L : 0L;
/* 294 */       xClientMessageEvent.set_data(1, l);
/* 295 */       xClientMessageEvent.set_data(2, (paramArrayOflong.length > 0) ? paramArrayOflong[0] : 0L);
/* 296 */       xClientMessageEvent.set_data(3, (paramArrayOflong.length > 1) ? paramArrayOflong[1] : 0L);
/* 297 */       xClientMessageEvent.set_data(4, (paramArrayOflong.length > 2) ? paramArrayOflong[2] : 0L);
/* 298 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 299 */           getTargetProxyWindow(), false, 0L, xClientMessageEvent.pData);
/*     */     }
/*     */     finally {
/*     */       
/* 303 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMoveMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong) {
/* 309 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/* 310 */     assert getTargetWindow() != 0L;
/*     */     
/* 312 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 314 */       xClientMessageEvent.set_type(33);
/* 315 */       xClientMessageEvent.set_window(getTargetWindow());
/* 316 */       xClientMessageEvent.set_format(32);
/* 317 */       xClientMessageEvent.set_message_type(XDnDConstants.XA_XdndPosition.getAtom());
/* 318 */       xClientMessageEvent.set_data(0, XDragSourceProtocol.getDragSourceWindow());
/* 319 */       xClientMessageEvent.set_data(1, 0L);
/* 320 */       xClientMessageEvent.set_data(2, (paramInt1 << 16 | paramInt2));
/* 321 */       xClientMessageEvent.set_data(3, paramLong);
/* 322 */       xClientMessageEvent.set_data(4, XDnDConstants.getXDnDActionForJavaAction(paramInt3));
/* 323 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 324 */           getTargetProxyWindow(), false, 0L, xClientMessageEvent.pData);
/*     */     }
/*     */     finally {
/*     */       
/* 328 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendLeaveMessage(long paramLong) {
/* 333 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/* 334 */     assert getTargetWindow() != 0L;
/*     */     
/* 336 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 338 */       xClientMessageEvent.set_type(33);
/* 339 */       xClientMessageEvent.set_window(getTargetWindow());
/* 340 */       xClientMessageEvent.set_format(32);
/* 341 */       xClientMessageEvent.set_message_type(XDnDConstants.XA_XdndLeave.getAtom());
/* 342 */       xClientMessageEvent.set_data(0, XDragSourceProtocol.getDragSourceWindow());
/* 343 */       xClientMessageEvent.set_data(1, 0L);
/* 344 */       xClientMessageEvent.set_data(2, 0L);
/* 345 */       xClientMessageEvent.set_data(3, 0L);
/* 346 */       xClientMessageEvent.set_data(4, 0L);
/* 347 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 348 */           getTargetProxyWindow(), false, 0L, xClientMessageEvent.pData);
/*     */     }
/*     */     finally {
/*     */       
/* 352 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendDropMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong) {
/* 359 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/* 360 */     assert getTargetWindow() != 0L;
/*     */     
/* 362 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 364 */       xClientMessageEvent.set_type(33);
/* 365 */       xClientMessageEvent.set_window(getTargetWindow());
/* 366 */       xClientMessageEvent.set_format(32);
/* 367 */       xClientMessageEvent.set_message_type(XDnDConstants.XA_XdndDrop.getAtom());
/* 368 */       xClientMessageEvent.set_data(0, XDragSourceProtocol.getDragSourceWindow());
/* 369 */       xClientMessageEvent.set_data(1, 0L);
/* 370 */       xClientMessageEvent.set_data(2, paramLong);
/* 371 */       xClientMessageEvent.set_data(3, 0L);
/* 372 */       xClientMessageEvent.set_data(4, 0L);
/* 373 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 374 */           getTargetProxyWindow(), false, 0L, xClientMessageEvent.pData);
/*     */     }
/*     */     finally {
/*     */       
/* 378 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processProxyModeEvent(XClientMessageEvent paramXClientMessageEvent, long paramLong) {
/* 384 */     if (paramXClientMessageEvent.get_message_type() == XDnDConstants.XA_XdndStatus.getAtom() || paramXClientMessageEvent
/* 385 */       .get_message_type() == XDnDConstants.XA_XdndFinished.getAtom()) {
/*     */       
/* 387 */       if (paramXClientMessageEvent.get_message_type() == XDnDConstants.XA_XdndFinished.getAtom()) {
/* 388 */         XDragSourceContextPeer.setProxyModeSourceWindow(0L);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 394 */       if (paramXClientMessageEvent.get_window() == paramLong) {
/* 395 */         return false;
/*     */       }
/*     */       
/* 398 */       if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 399 */         logger.finest("        sourceWindow=" + paramLong + " get_window=" + paramXClientMessageEvent
/* 400 */             .get_window() + " xclient=" + paramXClientMessageEvent);
/*     */       }
/*     */       
/* 403 */       paramXClientMessageEvent.set_data(0, paramXClientMessageEvent.get_window());
/* 404 */       paramXClientMessageEvent.set_window(paramLong);
/*     */       
/* 406 */       assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */       
/* 408 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), paramLong, false, 0L, paramXClientMessageEvent.pData);
/*     */ 
/*     */ 
/*     */       
/* 412 */       return true;
/*     */     } 
/*     */     
/* 415 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 421 */     cleanup();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDnDDragSourceProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */