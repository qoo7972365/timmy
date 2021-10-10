/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.util.Map;
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
/*     */ abstract class XDragSourceProtocol
/*     */ {
/*     */   private final XDragSourceProtocolListener listener;
/*     */   private boolean initialized = false;
/*  46 */   private long targetWindow = 0L;
/*  47 */   private long targetProxyWindow = 0L;
/*  48 */   private int targetProtocolVersion = 0;
/*  49 */   private long targetWindowMask = 0L;
/*     */ 
/*     */   
/*     */   static long getDragSourceWindow() {
/*  53 */     return XWindow.getXAWTRootWindow().getWindow();
/*     */   }
/*     */   
/*     */   protected XDragSourceProtocol(XDragSourceProtocolListener paramXDragSourceProtocolListener) {
/*  57 */     if (paramXDragSourceProtocolListener == null) {
/*  58 */       throw new NullPointerException("Null XDragSourceProtocolListener");
/*     */     }
/*  60 */     this.listener = paramXDragSourceProtocolListener;
/*     */   }
/*     */   
/*     */   protected final XDragSourceProtocolListener getProtocolListener() {
/*  64 */     return this.listener;
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
/*     */ 
/*     */   
/*     */   public abstract String getProtocolName();
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
/*     */   public final void initializeDrag(int paramInt, Transferable paramTransferable, Map paramMap, long[] paramArrayOflong) throws InvalidDnDOperationException, IllegalArgumentException, XException {
/*  90 */     XToolkit.awtLock();
/*     */     try {
/*     */       try {
/*  93 */         if (this.initialized) {
/*  94 */           throw new InvalidDnDOperationException("Already initialized");
/*     */         }
/*     */         
/*  97 */         initializeDragImpl(paramInt, paramTransferable, paramMap, paramArrayOflong);
/*     */         
/*  99 */         this.initialized = true;
/*     */       } finally {
/* 101 */         if (!this.initialized) {
/* 102 */           cleanup();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 106 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void initializeDragImpl(int paramInt, Transferable paramTransferable, Map paramMap, long[] paramArrayOflong) throws InvalidDnDOperationException, IllegalArgumentException, XException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 123 */     this.initialized = false;
/* 124 */     cleanupTargetInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanupTargetInfo() {
/* 133 */     this.targetWindow = 0L;
/* 134 */     this.targetProxyWindow = 0L;
/* 135 */     this.targetProtocolVersion = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean processClientMessage(XClientMessageEvent paramXClientMessageEvent) throws XException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean attachTargetWindow(long paramLong1, long paramLong2) {
/* 148 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 150 */     TargetWindowInfo targetWindowInfo = getTargetWindowInfo(paramLong1);
/* 151 */     if (targetWindowInfo == null) {
/* 152 */       return false;
/*     */     }
/* 154 */     this.targetWindow = paramLong1;
/* 155 */     this.targetProxyWindow = targetWindowInfo.getProxyWindow();
/* 156 */     this.targetProtocolVersion = targetWindowInfo.getProtocolVersion();
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract TargetWindowInfo getTargetWindowInfo(long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void sendEnterMessage(long[] paramArrayOflong, int paramInt1, int paramInt2, long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void sendMoveMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void sendLeaveMessage(long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void sendDropMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
/*     */ 
/*     */   
/*     */   public final void initiateDrop(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong) {
/* 182 */     XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*     */     try {
/* 184 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 185 */       int i = XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), this.targetWindow, xWindowAttributes.pData);
/*     */ 
/*     */       
/* 188 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       
/* 190 */       if (i == 0 || (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*     */         
/* 192 */         .get_error_code() != 0)) {
/* 193 */         throw new XException("XGetWindowAttributes failed");
/*     */       }
/*     */       
/* 196 */       this.targetWindowMask = xWindowAttributes.get_your_event_mask();
/*     */     } finally {
/* 198 */       xWindowAttributes.dispose();
/*     */     } 
/*     */     
/* 201 */     XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 202 */     XlibWrapper.XSelectInput(XToolkit.getDisplay(), this.targetWindow, this.targetWindowMask | 0x20000L);
/*     */ 
/*     */ 
/*     */     
/* 206 */     XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */     
/* 208 */     if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 209 */       .get_error_code() != 0) {
/* 210 */       throw new XException("XSelectInput failed");
/*     */     }
/*     */     
/* 213 */     sendDropMessage(paramInt1, paramInt2, paramInt3, paramInt4, paramLong);
/*     */   }
/*     */   
/*     */   protected final void finalizeDrop() {
/* 217 */     XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 218 */     XlibWrapper.XSelectInput(XToolkit.getDisplay(), this.targetWindow, this.targetWindowMask);
/*     */     
/* 220 */     XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean processProxyModeEvent(XClientMessageEvent paramXClientMessageEvent, long paramLong);
/*     */   
/*     */   protected final long getTargetWindow() {
/* 227 */     return this.targetWindow;
/*     */   }
/*     */   
/*     */   protected final long getTargetProxyWindow() {
/* 231 */     if (this.targetProxyWindow != 0L) {
/* 232 */       return this.targetProxyWindow;
/*     */     }
/* 234 */     return this.targetWindow;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getTargetProtocolVersion() {
/* 239 */     return this.targetProtocolVersion;
/*     */   }
/*     */   
/*     */   public static class TargetWindowInfo {
/*     */     private final long proxyWindow;
/*     */     
/*     */     public TargetWindowInfo(long param1Long, int param1Int) {
/* 246 */       this.proxyWindow = param1Long;
/* 247 */       this.protocolVersion = param1Int;
/*     */     } private final int protocolVersion;
/*     */     public long getProxyWindow() {
/* 250 */       return this.proxyWindow;
/*     */     }
/*     */     public int getProtocolVersion() {
/* 253 */       return this.protocolVersion;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDragSourceProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */