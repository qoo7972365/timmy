/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
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
/*     */ abstract class XDropTargetProtocol
/*     */ {
/*  42 */   private static final PlatformLogger logger = PlatformLogger.getLogger("sun.awt.X11.xembed.xdnd.XDropTargetProtocol");
/*     */   
/*     */   private final XDropTargetProtocolListener listener;
/*     */   
/*     */   public static final int EMBEDDER_ALREADY_REGISTERED = 0;
/*     */   
/*     */   public static final int UNKNOWN_MESSAGE = 0;
/*     */   public static final int ENTER_MESSAGE = 1;
/*     */   public static final int MOTION_MESSAGE = 2;
/*     */   public static final int LEAVE_MESSAGE = 3;
/*     */   public static final int DROP_MESSAGE = 4;
/*     */   
/*     */   protected XDropTargetProtocol(XDropTargetProtocolListener paramXDropTargetProtocolListener) {
/*  55 */     if (paramXDropTargetProtocolListener == null) {
/*  56 */       throw new NullPointerException("Null XDropTargetProtocolListener");
/*     */     }
/*  58 */     this.listener = paramXDropTargetProtocolListener;
/*     */   }
/*     */   
/*     */   protected final XDropTargetProtocolListener getProtocolListener() {
/*  62 */     return this.listener;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getProtocolName();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void registerDropTarget(long paramLong);
/*     */ 
/*     */   
/*     */   public abstract void unregisterDropTarget(long paramLong);
/*     */ 
/*     */   
/*     */   public abstract void registerEmbedderDropSite(long paramLong);
/*     */ 
/*     */   
/*     */   public abstract void unregisterEmbedderDropSite(long paramLong);
/*     */ 
/*     */   
/*     */   public abstract void registerEmbeddedDropSite(long paramLong);
/*     */ 
/*     */   
/*     */   public final void unregisterEmbeddedDropSite(long paramLong) {
/*  87 */     removeEmbedderRegistryEntry(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean isProtocolSupported(long paramLong);
/*     */ 
/*     */   
/*     */   public abstract int getMessageType(XClientMessageEvent paramXClientMessageEvent);
/*     */ 
/*     */   
/*     */   public final boolean processClientMessage(XClientMessageEvent paramXClientMessageEvent) {
/*  98 */     int i = getMessageType(paramXClientMessageEvent);
/*  99 */     boolean bool = processClientMessageImpl(paramXClientMessageEvent);
/*     */     
/* 101 */     postProcessClientMessage(paramXClientMessageEvent, bool, i);
/*     */     
/* 103 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean processClientMessageImpl(XClientMessageEvent paramXClientMessageEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean forwardClientMessageToToplevel(long paramLong, XClientMessageEvent paramXClientMessageEvent) {
/* 117 */     EmbedderRegistryEntry embedderRegistryEntry = getEmbedderRegistryEntry(paramLong);
/*     */     
/* 119 */     if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 120 */       logger.finest("        entry={0}", new Object[] { embedderRegistryEntry });
/*     */     }
/*     */     
/* 123 */     if (embedderRegistryEntry == null) {
/* 124 */       return false;
/*     */     }
/*     */     
/* 127 */     if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 128 */       logger.finest("        entry.isOverriden()={0}", new Object[] { Boolean.valueOf(embedderRegistryEntry.isOverriden()) });
/*     */     }
/*     */ 
/*     */     
/* 132 */     if (!embedderRegistryEntry.isOverriden()) {
/* 133 */       return false;
/*     */     }
/*     */     
/* 136 */     adjustEventForForwarding(paramXClientMessageEvent, embedderRegistryEntry);
/*     */     
/* 138 */     long l = embedderRegistryEntry.getProxy();
/*     */     
/* 140 */     if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 141 */       logger.finest("        proxy={0} toplevel={1}", new Object[] { Long.valueOf(l), Long.valueOf(paramLong) });
/*     */     }
/* 143 */     if (l == 0L) {
/* 144 */       l = paramLong;
/*     */     }
/*     */     
/* 147 */     paramXClientMessageEvent.set_window(paramLong);
/*     */     
/* 149 */     XToolkit.awtLock();
/*     */     try {
/* 151 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), l, false, 0L, paramXClientMessageEvent.pData);
/*     */     } finally {
/*     */       
/* 154 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean motionPassedAlong = false;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void sendEnterMessageToToplevel(long paramLong, XClientMessageEvent paramXClientMessageEvent);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void sendLeaveMessageToToplevel(long paramLong, XClientMessageEvent paramXClientMessageEvent);
/*     */ 
/*     */   
/*     */   private void postProcessClientMessage(XClientMessageEvent paramXClientMessageEvent, boolean paramBoolean, int paramInt) {
/* 174 */     long l = paramXClientMessageEvent.get_window();
/*     */     
/* 176 */     if (getEmbedderRegistryEntry(l) != null)
/*     */     {
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
/* 200 */       if (!paramBoolean) {
/* 201 */         forwardClientMessageToToplevel(l, paramXClientMessageEvent);
/*     */       }
/*     */       else {
/*     */         
/* 205 */         boolean bool = (paramXClientMessageEvent.get_message_type() == MotifDnDConstants.XA_MOTIF_DRAG_AND_DROP_MESSAGE.getAtom()) ? true : false;
/*     */         
/* 207 */         switch (paramInt) {
/*     */           case 2:
/* 209 */             if (!isDragOverComponent()) {
/* 210 */               if (!this.motionPassedAlong && !bool) {
/* 211 */                 sendEnterMessageToToplevel(l, paramXClientMessageEvent);
/*     */               }
/* 213 */               forwardClientMessageToToplevel(l, paramXClientMessageEvent);
/* 214 */               this.motionPassedAlong = true; break;
/*     */             } 
/* 216 */             if (this.motionPassedAlong && !bool) {
/* 217 */               sendLeaveMessageToToplevel(l, paramXClientMessageEvent);
/*     */             }
/* 219 */             this.motionPassedAlong = false;
/*     */             break;
/*     */           
/*     */           case 4:
/* 223 */             if (!isDragOverComponent()) {
/* 224 */               forwardClientMessageToToplevel(l, paramXClientMessageEvent);
/*     */             }
/* 226 */             this.motionPassedAlong = false;
/*     */             break;
/*     */           case 1:
/*     */           case 3:
/* 230 */             if (bool) {
/* 231 */               forwardClientMessageToToplevel(l, paramXClientMessageEvent);
/*     */             }
/* 233 */             this.motionPassedAlong = false;
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean sendResponse(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Object getData(long paramLong1, long paramLong2) throws IllegalArgumentException, IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean sendDropDone(long paramLong, boolean paramBoolean, int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract long getSourceWindow();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void cleanup();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isDragOverComponent();
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustEventForForwarding(XClientMessageEvent paramXClientMessageEvent, EmbedderRegistryEntry paramEmbedderRegistryEntry) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final class EmbedderRegistryEntry
/*     */   {
/*     */     private final boolean overriden;
/*     */ 
/*     */     
/*     */     private final int version;
/*     */ 
/*     */     
/*     */     private final long proxy;
/*     */ 
/*     */     
/*     */     EmbedderRegistryEntry(boolean param1Boolean, int param1Int, long param1Long) {
/* 282 */       this.overriden = param1Boolean;
/* 283 */       this.version = param1Int;
/* 284 */       this.proxy = param1Long;
/*     */     }
/*     */     public boolean isOverriden() {
/* 287 */       return this.overriden;
/*     */     }
/*     */     public int getVersion() {
/* 290 */       return this.version;
/*     */     }
/*     */     public long getProxy() {
/* 293 */       return this.proxy;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 298 */   private final HashMap embedderRegistry = new HashMap<>();
/*     */   public abstract boolean forwardEventToEmbedded(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   public abstract boolean isXEmbedSupported();
/*     */   
/*     */   protected final void putEmbedderRegistryEntry(long paramLong1, boolean paramBoolean, int paramInt, long paramLong2) {
/* 304 */     synchronized (this) {
/* 305 */       this.embedderRegistry.put(Long.valueOf(paramLong1), new EmbedderRegistryEntry(paramBoolean, paramInt, paramLong2));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final EmbedderRegistryEntry getEmbedderRegistryEntry(long paramLong) {
/* 312 */     synchronized (this) {
/* 313 */       return (EmbedderRegistryEntry)this.embedderRegistry
/* 314 */         .get(Long.valueOf(paramLong));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void removeEmbedderRegistryEntry(long paramLong) {
/* 319 */     synchronized (this) {
/* 320 */       this.embedderRegistry.remove(Long.valueOf(paramLong));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDropTargetProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */