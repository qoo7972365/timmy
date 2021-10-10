/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.util.Map;
/*     */ import sun.misc.Unsafe;
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
/*     */ class MotifDnDDragSourceProtocol
/*     */   extends XDragSourceProtocol
/*     */   implements XEventDispatcher
/*     */ {
/*  45 */   private static final Unsafe unsafe = XlibWrapper.unsafe;
/*     */   
/*  47 */   private long targetEnterServerTime = 0L;
/*     */   
/*     */   protected MotifDnDDragSourceProtocol(XDragSourceProtocolListener paramXDragSourceProtocolListener) {
/*  50 */     super(paramXDragSourceProtocolListener);
/*  51 */     XToolkit.addEventDispatcher(XWindow.getXAWTRootWindow().getWindow(), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static XDragSourceProtocol createInstance(XDragSourceProtocolListener paramXDragSourceProtocolListener) {
/*  60 */     return new MotifDnDDragSourceProtocol(paramXDragSourceProtocolListener);
/*     */   }
/*     */   
/*     */   public String getProtocolName() {
/*  64 */     return "MotifDnD";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeDragImpl(int paramInt, Transferable paramTransferable, Map paramMap, long[] paramArrayOflong) throws InvalidDnDOperationException, IllegalArgumentException, XException {
/*  72 */     long l = XDragSourceProtocol.getDragSourceWindow();
/*     */ 
/*     */     
/*     */     try {
/*  76 */       int i = MotifDnDConstants.getIndexForTargetList(paramArrayOflong);
/*     */       
/*  78 */       MotifDnDConstants.writeDragInitiatorInfoStruct(l, i);
/*  79 */     } catch (XException xException) {
/*  80 */       cleanup();
/*  81 */       throw xException;
/*  82 */     } catch (InvalidDnDOperationException invalidDnDOperationException) {
/*  83 */       cleanup();
/*  84 */       throw invalidDnDOperationException;
/*     */     } 
/*     */     
/*  87 */     if (!MotifDnDConstants.MotifDnDSelection.setOwner(paramTransferable, paramMap, paramArrayOflong, 0L)) {
/*     */ 
/*     */       
/*  90 */       cleanup();
/*  91 */       throw new InvalidDnDOperationException("Cannot acquire selection ownership");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processClientMessage(XClientMessageEvent paramXClientMessageEvent) {
/* 101 */     if (paramXClientMessageEvent.get_message_type() != MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE
/* 102 */       .getAtom()) {
/* 103 */       return false;
/*     */     }
/*     */     
/* 106 */     long l1 = paramXClientMessageEvent.get_data();
/* 107 */     byte b1 = (byte)(unsafe.getByte(l1) & Byte.MAX_VALUE);
/*     */     
/* 109 */     byte b2 = (byte)(unsafe.getByte(l1) & Byte.MIN_VALUE);
/*     */     
/* 111 */     byte b3 = unsafe.getByte(l1 + 1L);
/* 112 */     boolean bool = (b3 != MotifDnDConstants.getByteOrderByte()) ? true : false;
/* 113 */     int i = 0;
/* 114 */     short s1 = 0;
/* 115 */     short s2 = 0;
/*     */ 
/*     */     
/* 118 */     if (b2 != Byte.MIN_VALUE) {
/* 119 */       return false;
/*     */     }
/*     */     
/* 122 */     switch (b1) {
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 8:
/*     */         break;
/*     */       
/*     */       default:
/* 130 */         return false;
/*     */     } 
/*     */     
/* 133 */     int j = unsafe.getInt(l1 + 4L);
/* 134 */     if (bool) {
/* 135 */       j = MotifDnDConstants.Swapper.swap(j);
/*     */     }
/* 137 */     long l2 = j & 0xFFFFFFFFL;
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (this.targetEnterServerTime == 0L || l2 < this.targetEnterServerTime)
/*     */     {
/* 143 */       return true;
/*     */     }
/*     */     
/* 146 */     if (b1 != 4) {
/* 147 */       short s3 = unsafe.getShort(l1 + 2L);
/* 148 */       if (bool) {
/* 149 */         s3 = MotifDnDConstants.Swapper.swap(s3);
/*     */       }
/*     */       
/* 152 */       byte b4 = (byte)((s3 & 0xF0) >> 4);
/*     */       
/* 154 */       byte b5 = (byte)((s3 & 0xF) >> 0);
/*     */ 
/*     */       
/* 157 */       if (b4 == 3) {
/* 158 */         i = MotifDnDConstants.getJavaActionsForMotifActions(b5);
/*     */       } else {
/* 160 */         i = 0;
/*     */       } 
/*     */       
/* 163 */       short s4 = unsafe.getShort(l1 + 8L);
/* 164 */       short s5 = unsafe.getShort(l1 + 10L);
/* 165 */       if (bool) {
/* 166 */         s4 = MotifDnDConstants.Swapper.swap(s4);
/* 167 */         s5 = MotifDnDConstants.Swapper.swap(s5);
/*     */       } 
/* 169 */       s1 = s4;
/* 170 */       s2 = s5;
/*     */     } 
/*     */     
/* 173 */     getProtocolListener().handleDragReply(i, s1, s2);
/*     */     
/* 175 */     return true;
/*     */   }
/*     */   
/*     */   public XDragSourceProtocol.TargetWindowInfo getTargetWindowInfo(long paramLong) {
/* 179 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 181 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO, 0L, 65535L, false, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 188 */       int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
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
/* 204 */       if (i == 0 && windowPropertyGetter.getData() != 0L && windowPropertyGetter
/* 205 */         .getActualType() != 0L && windowPropertyGetter.getActualFormat() == 8 && windowPropertyGetter
/* 206 */         .getNumberOfItems() >= 16) {
/*     */         int j;
/*     */         byte b3;
/* 209 */         long l = windowPropertyGetter.getData();
/* 210 */         byte b1 = unsafe.getByte(l);
/* 211 */         byte b2 = unsafe.getByte(l + 2L);
/* 212 */         switch (b2) {
/*     */           case 2:
/*     */           case 4:
/*     */           case 5:
/*     */           case 6:
/* 217 */             j = unsafe.getInt(l + 4L);
/* 218 */             if (b1 != MotifDnDConstants.getByteOrderByte()) {
/* 219 */               j = MotifDnDConstants.Swapper.swap(j);
/*     */             }
/*     */             
/* 222 */             b3 = unsafe.getByte(l + 1L);
/*     */             
/* 224 */             targetWindowInfo = new XDragSourceProtocol.TargetWindowInfo(j, b3); return targetWindowInfo;
/*     */         } 
/*     */         
/* 227 */         XDragSourceProtocol.TargetWindowInfo targetWindowInfo = null; return targetWindowInfo;
/*     */       } 
/*     */       
/* 230 */       return null;
/*     */     } finally {
/*     */       
/* 233 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendEnterMessage(long[] paramArrayOflong, int paramInt1, int paramInt2, long paramLong) {
/* 239 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/* 240 */     assert getTargetWindow() != 0L;
/* 241 */     assert paramArrayOflong != null;
/*     */     
/* 243 */     this.targetEnterServerTime = paramLong;
/*     */     
/* 245 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 247 */       xClientMessageEvent.set_type(33);
/* 248 */       xClientMessageEvent.set_window(getTargetWindow());
/* 249 */       xClientMessageEvent.set_format(8);
/* 250 */       xClientMessageEvent.set_message_type(MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE.getAtom());
/*     */       
/* 252 */       long l = xClientMessageEvent.get_data();
/*     */ 
/*     */ 
/*     */       
/* 256 */       int i = MotifDnDConstants.getMotifActionsForJavaActions(paramInt1) << 0 | MotifDnDConstants.getMotifActionsForJavaActions(paramInt2) << 8;
/*     */ 
/*     */       
/* 259 */       unsafe.putByte(l, (byte)0);
/*     */ 
/*     */       
/* 262 */       unsafe.putByte(l + 1L, 
/* 263 */           MotifDnDConstants.getByteOrderByte());
/* 264 */       unsafe.putShort(l + 2L, (short)i);
/* 265 */       unsafe.putInt(l + 4L, (int)paramLong);
/* 266 */       unsafe.putInt(l + 8L, (int)XDragSourceProtocol.getDragSourceWindow());
/* 267 */       unsafe.putInt(l + 12L, (int)MotifDnDConstants.XA_MOTIF_ATOM_0.getAtom());
/*     */       
/* 269 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 270 */           getTargetProxyWindow(), false, 0L, xClientMessageEvent.pData);
/*     */     }
/*     */     finally {
/*     */       
/* 274 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMoveMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong) {
/* 280 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/* 281 */     assert getTargetWindow() != 0L;
/*     */     
/* 283 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 285 */       xClientMessageEvent.set_type(33);
/* 286 */       xClientMessageEvent.set_window(getTargetWindow());
/* 287 */       xClientMessageEvent.set_format(8);
/* 288 */       xClientMessageEvent.set_message_type(MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE.getAtom());
/*     */       
/* 290 */       long l = xClientMessageEvent.get_data();
/*     */ 
/*     */ 
/*     */       
/* 294 */       int i = MotifDnDConstants.getMotifActionsForJavaActions(paramInt3) << 0 | MotifDnDConstants.getMotifActionsForJavaActions(paramInt4) << 8;
/*     */ 
/*     */       
/* 297 */       unsafe.putByte(l, (byte)2);
/*     */ 
/*     */       
/* 300 */       unsafe.putByte(l + 1L, 
/* 301 */           MotifDnDConstants.getByteOrderByte());
/* 302 */       unsafe.putShort(l + 2L, (short)i);
/* 303 */       unsafe.putInt(l + 4L, (int)paramLong);
/* 304 */       unsafe.putShort(l + 8L, (short)paramInt1);
/* 305 */       unsafe.putShort(l + 10L, (short)paramInt2);
/*     */       
/* 307 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 308 */           getTargetProxyWindow(), false, 0L, xClientMessageEvent.pData);
/*     */     }
/*     */     finally {
/*     */       
/* 312 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendLeaveMessage(long paramLong) {
/* 317 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/* 318 */     assert getTargetWindow() != 0L;
/*     */     
/* 320 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 322 */       xClientMessageEvent.set_type(33);
/* 323 */       xClientMessageEvent.set_window(getTargetWindow());
/* 324 */       xClientMessageEvent.set_format(8);
/* 325 */       xClientMessageEvent.set_message_type(MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE.getAtom());
/*     */       
/* 327 */       long l = xClientMessageEvent.get_data();
/*     */       
/* 329 */       unsafe.putByte(l, (byte)1);
/*     */ 
/*     */       
/* 332 */       unsafe.putByte(l + 1L, 
/* 333 */           MotifDnDConstants.getByteOrderByte());
/* 334 */       unsafe.putShort(l + 2L, (short)0);
/* 335 */       unsafe.putInt(l + 4L, (int)paramLong);
/* 336 */       unsafe.putInt(l + 8L, (int)XDragSourceProtocol.getDragSourceWindow());
/*     */       
/* 338 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 339 */           getTargetProxyWindow(), false, 0L, xClientMessageEvent.pData);
/*     */     }
/*     */     finally {
/*     */       
/* 343 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendDropMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong) {
/* 350 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/* 351 */     assert getTargetWindow() != 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     sendLeaveMessage(paramLong);
/*     */     
/* 358 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 360 */       xClientMessageEvent.set_type(33);
/* 361 */       xClientMessageEvent.set_window(getTargetWindow());
/* 362 */       xClientMessageEvent.set_format(8);
/* 363 */       xClientMessageEvent.set_message_type(MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE.getAtom());
/*     */       
/* 365 */       long l = xClientMessageEvent.get_data();
/*     */ 
/*     */ 
/*     */       
/* 369 */       int i = MotifDnDConstants.getMotifActionsForJavaActions(paramInt3) << 0 | MotifDnDConstants.getMotifActionsForJavaActions(paramInt4) << 8;
/*     */ 
/*     */       
/* 372 */       unsafe.putByte(l, (byte)5);
/*     */ 
/*     */       
/* 375 */       unsafe.putByte(l + 1L, 
/* 376 */           MotifDnDConstants.getByteOrderByte());
/* 377 */       unsafe.putShort(l + 2L, (short)i);
/* 378 */       unsafe.putInt(l + 4L, (int)paramLong);
/* 379 */       unsafe.putShort(l + 8L, (short)paramInt1);
/* 380 */       unsafe.putShort(l + 10L, (short)paramInt2);
/* 381 */       unsafe.putInt(l + 12L, (int)MotifDnDConstants.XA_MOTIF_ATOM_0.getAtom());
/* 382 */       unsafe.putInt(l + 16L, (int)XDragSourceProtocol.getDragSourceWindow());
/*     */       
/* 384 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 385 */           getTargetProxyWindow(), false, 0L, xClientMessageEvent.pData);
/*     */     }
/*     */     finally {
/*     */       
/* 389 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processProxyModeEvent(XClientMessageEvent paramXClientMessageEvent, long paramLong) {
/* 396 */     return false;
/*     */   }
/*     */   
/*     */   public void cleanupTargetInfo() {
/* 400 */     super.cleanupTargetInfo();
/* 401 */     this.targetEnterServerTime = 0L;
/*     */   } public void dispatchEvent(XEvent paramXEvent) {
/*     */     XSelectionRequestEvent xSelectionRequestEvent;
/*     */     long l;
/* 405 */     switch (paramXEvent.get_type()) {
/*     */       case 30:
/* 407 */         xSelectionRequestEvent = paramXEvent.get_xselectionrequest();
/* 408 */         l = xSelectionRequestEvent.get_selection();
/*     */         
/* 410 */         if (l == MotifDnDConstants.XA_MOTIF_ATOM_0.getAtom()) {
/* 411 */           long l1 = xSelectionRequestEvent.get_target();
/* 412 */           if (l1 == MotifDnDConstants.XA_XmTRANSFER_SUCCESS.getAtom()) {
/* 413 */             getProtocolListener().handleDragFinished(true); break;
/* 414 */           }  if (l1 == MotifDnDConstants.XA_XmTRANSFER_FAILURE.getAtom())
/* 415 */             getProtocolListener().handleDragFinished(false); 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/MotifDnDDragSourceProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */