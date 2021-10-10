/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.dnd.SunDropTargetContextPeer;
/*     */ import sun.awt.dnd.SunDropTargetEvent;
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
/*     */ 
/*     */ 
/*     */ final class XDropTargetContextPeer
/*     */   extends SunDropTargetContextPeer
/*     */ {
/*  53 */   private static final PlatformLogger logger = PlatformLogger.getLogger("sun.awt.X11.xembed.xdnd.XDropTargetContextPeer");
/*     */   
/*  55 */   private static final Unsafe unsafe = XlibWrapper.unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final Object DTCP_KEY = "DropTargetContextPeer";
/*     */ 
/*     */ 
/*     */   
/*     */   static XDropTargetContextPeer getPeer(AppContext paramAppContext) {
/*  65 */     synchronized (_globalLock) {
/*     */       
/*  67 */       XDropTargetContextPeer xDropTargetContextPeer = (XDropTargetContextPeer)paramAppContext.get(DTCP_KEY);
/*  68 */       if (xDropTargetContextPeer == null) {
/*  69 */         xDropTargetContextPeer = new XDropTargetContextPeer();
/*  70 */         paramAppContext.put(DTCP_KEY, xDropTargetContextPeer);
/*     */       } 
/*     */       
/*  73 */       return xDropTargetContextPeer;
/*     */     } 
/*     */   }
/*     */   
/*     */   static XDropTargetProtocolListener getXDropTargetProtocolListener() {
/*  78 */     return XDropTargetProtocolListenerImpl.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void eventProcessed(SunDropTargetEvent paramSunDropTargetEvent, int paramInt, boolean paramBoolean) {
/*  88 */     long l = getNativeDragContext();
/*     */     
/*     */     try {
/*  91 */       if (l != 0L && !paramSunDropTargetEvent.isConsumed()) {
/*     */         
/*  93 */         Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */         
/*  95 */         while (iterator.hasNext()) {
/*     */           
/*  97 */           XDropTargetProtocol xDropTargetProtocol = iterator.next();
/*  98 */           if (xDropTargetProtocol.sendResponse(l, paramSunDropTargetEvent.getID(), paramInt)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 105 */       if (paramBoolean && l != 0L) {
/* 106 */         unsafe.freeMemory(l);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDropDone(boolean paramBoolean1, int paramInt, boolean paramBoolean2) {
/* 115 */     long l = getNativeDragContext();
/*     */     
/* 117 */     if (l != 0L) {
/*     */       
/*     */       try {
/* 120 */         Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */         
/* 122 */         while (iterator.hasNext()) {
/*     */           
/* 124 */           XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 125 */           if (xDropTargetProtocol.sendDropDone(l, paramBoolean1, paramInt)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } finally {
/*     */         
/* 131 */         unsafe.freeMemory(l);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getNativeData(long paramLong) throws IOException {
/* 140 */     long l = getNativeDragContext();
/*     */     
/* 142 */     if (l != 0L) {
/*     */       
/* 144 */       Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */       
/* 146 */       while (iterator.hasNext()) {
/*     */         
/* 148 */         XDropTargetProtocol xDropTargetProtocol = iterator.next();
/*     */         
/*     */         try {
/* 151 */           return xDropTargetProtocol.getData(l, paramLong);
/* 152 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void cleanup() {}
/*     */   
/*     */   protected void processEnterMessage(SunDropTargetEvent paramSunDropTargetEvent) {
/* 164 */     if (!processSunDropTargetEvent(paramSunDropTargetEvent)) {
/* 165 */       super.processEnterMessage(paramSunDropTargetEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void processExitMessage(SunDropTargetEvent paramSunDropTargetEvent) {
/* 170 */     if (!processSunDropTargetEvent(paramSunDropTargetEvent)) {
/* 171 */       super.processExitMessage(paramSunDropTargetEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processMotionMessage(SunDropTargetEvent paramSunDropTargetEvent, boolean paramBoolean) {
/* 177 */     if (!processSunDropTargetEvent(paramSunDropTargetEvent)) {
/* 178 */       super.processMotionMessage(paramSunDropTargetEvent, paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void processDropMessage(SunDropTargetEvent paramSunDropTargetEvent) {
/* 183 */     if (!processSunDropTargetEvent(paramSunDropTargetEvent)) {
/* 184 */       super.processDropMessage(paramSunDropTargetEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean processSunDropTargetEvent(SunDropTargetEvent paramSunDropTargetEvent) {
/* 192 */     Object object = paramSunDropTargetEvent.getSource();
/*     */     
/* 194 */     if (object instanceof Component) {
/* 195 */       ComponentPeer componentPeer = ((Component)object).getPeer();
/* 196 */       if (componentPeer instanceof XEmbedCanvasPeer) {
/* 197 */         XEmbedCanvasPeer xEmbedCanvasPeer = (XEmbedCanvasPeer)componentPeer;
/*     */ 
/*     */         
/* 200 */         long l = getNativeDragContext();
/*     */         
/* 202 */         if (logger.isLoggable(PlatformLogger.Level.FINER)) {
/* 203 */           logger.finer("        processing " + paramSunDropTargetEvent + " ctxt=" + l + " consumed=" + paramSunDropTargetEvent
/* 204 */               .isConsumed());
/*     */         }
/*     */ 
/*     */         
/* 208 */         if (!paramSunDropTargetEvent.isConsumed())
/*     */         {
/* 210 */           if (xEmbedCanvasPeer.processXEmbedDnDEvent(l, paramSunDropTargetEvent
/* 211 */               .getID())) {
/* 212 */             paramSunDropTargetEvent.consume();
/* 213 */             return true;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forwardEventToEmbedded(long paramLong1, long paramLong2, int paramInt) {
/* 225 */     Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */     
/* 227 */     while (iterator.hasNext()) {
/*     */       
/* 229 */       XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 230 */       if (xDropTargetProtocol.forwardEventToEmbedded(paramLong1, paramLong2, paramInt)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static final class XDropTargetProtocolListenerImpl
/*     */     implements XDropTargetProtocolListener
/*     */   {
/* 240 */     private static final XDropTargetProtocolListener theInstance = new XDropTargetProtocolListenerImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static XDropTargetProtocolListener getInstance() {
/* 246 */       return theInstance;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleDropTargetNotification(XWindow param1XWindow, int param1Int1, int param1Int2, int param1Int3, int param1Int4, long[] param1ArrayOflong, long param1Long, int param1Int5) {
/* 253 */       Object object = param1XWindow.getTarget();
/*     */ 
/*     */       
/* 256 */       assert object instanceof Component;
/*     */       
/* 258 */       Component component = (Component)object;
/*     */       
/* 260 */       AppContext appContext = SunToolkit.targetToAppContext(object);
/*     */ 
/*     */       
/* 263 */       assert appContext != null;
/*     */       
/* 265 */       XDropTargetContextPeer xDropTargetContextPeer = XDropTargetContextPeer.getPeer(appContext);
/*     */       
/* 267 */       xDropTargetContextPeer.postDropTargetEvent(component, param1Int1, param1Int2, param1Int3, param1Int4, param1ArrayOflong, param1Long, param1Int5, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDropTargetContextPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */