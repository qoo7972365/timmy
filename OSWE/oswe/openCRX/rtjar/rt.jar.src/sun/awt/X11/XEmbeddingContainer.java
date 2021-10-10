/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.util.HashMap;
/*     */ import sun.awt.AWTAccessor;
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
/*     */ public class XEmbeddingContainer
/*     */   extends XEmbedHelper
/*     */   implements XEventDispatcher
/*     */ {
/*  35 */   HashMap children = new HashMap<>();
/*     */ 
/*     */   
/*     */   XWindow embedder;
/*     */ 
/*     */   
/*     */   void install(XWindow paramXWindow) {
/*  42 */     this.embedder = paramXWindow;
/*  43 */     XToolkit.addEventDispatcher(paramXWindow.getWindow(), this);
/*     */   }
/*     */   void deinstall() {
/*  46 */     XToolkit.removeEventDispatcher(this.embedder.getWindow(), this);
/*     */   }
/*     */   
/*     */   void add(long paramLong) {
/*  50 */     if (checkXEmbed(paramLong)) {
/*  51 */       Component component = createChildProxy(paramLong);
/*  52 */       ((Container)this.embedder.getTarget()).add("Center", component);
/*  53 */       if (component.getPeer() != null) {
/*  54 */         this.children.put(Long.valueOf(paramLong), component.getPeer());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   Component createChildProxy(long paramLong) {
/*  60 */     return new XEmbedChildProxy(this, paramLong);
/*     */   }
/*     */   void notifyChildEmbedded(long paramLong) {
/*  63 */     sendMessage(paramLong, 0, this.embedder.getWindow(), 0L, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   void childResized(Component paramComponent) {}
/*     */   
/*     */   boolean checkXEmbed(long paramLong) {
/*  70 */     long l = unsafe.allocateMemory(8L);
/*     */     try {
/*  72 */       if (XEmbedInfo.getAtomData(paramLong, l, 2)) {
/*  73 */         int i = unsafe.getInt(l);
/*  74 */         int j = unsafe.getInt(l);
/*  75 */         return true;
/*     */       } 
/*     */     } finally {
/*  78 */       unsafe.freeMemory(l);
/*     */     } 
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void detachChild(long paramLong) {
/*  91 */     XToolkit.awtLock();
/*     */     try {
/*  93 */       XlibWrapper.XUnmapWindow(XToolkit.getDisplay(), paramLong);
/*  94 */       XlibWrapper.XReparentWindow(XToolkit.getDisplay(), paramLong, XToolkit.getDefaultRootWindow(), 0, 0);
/*     */     } finally {
/*     */       
/*  97 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void focusGained(long paramLong) {
/* 102 */     sendMessage(paramLong, 4, 0L, 0L, 0L);
/*     */   }
/*     */   void focusLost(long paramLong) {
/* 105 */     sendMessage(paramLong, 5);
/*     */   }
/*     */   
/*     */   XEmbedChildProxyPeer getChild(long paramLong) {
/* 109 */     return (XEmbedChildProxyPeer)this.children.get(Long.valueOf(paramLong));
/*     */   }
/*     */   public void handleClientMessage(XEvent paramXEvent) {
/* 112 */     XClientMessageEvent xClientMessageEvent = paramXEvent.get_xclient();
/* 113 */     if (xClientMessageEvent.get_message_type() == XEmbed.getAtom()) {
/* 114 */       long l; switch ((int)xClientMessageEvent.get_data(1)) {
/*     */         case 3:
/* 116 */           l = xClientMessageEvent.get_data(2);
/* 117 */           getChild(l).requestXEmbedFocus();
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void dispatchEvent(XEvent paramXEvent) {
/* 123 */     switch (paramXEvent.get_type()) {
/*     */       case 33:
/* 125 */         handleClientMessage(paramXEvent);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   void forwardKeyEvent(long paramLong, KeyEvent paramKeyEvent) {
/* 131 */     byte[] arrayOfByte = AWTAccessor.getAWTEventAccessor().getBData(paramKeyEvent);
/* 132 */     long l = Native.toData(arrayOfByte);
/* 133 */     if (l == 0L) {
/*     */       return;
/*     */     }
/* 136 */     XKeyEvent xKeyEvent = new XKeyEvent(l);
/* 137 */     xKeyEvent.set_window(paramLong);
/* 138 */     XToolkit.awtLock();
/*     */     try {
/* 140 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), paramLong, false, 0L, l);
/*     */     } finally {
/*     */       
/* 143 */       XToolkit.awtUnlock();
/*     */     } 
/* 145 */     XlibWrapper.unsafe.freeMemory(l);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbeddingContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */