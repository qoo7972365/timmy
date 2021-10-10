/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTKeyStroke;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Window;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.X11GraphicsConfig;
/*     */ import sun.awt.X11GraphicsDevice;
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
/*     */ public class XEmbedClientHelper
/*     */   extends XEmbedHelper
/*     */   implements XEventDispatcher
/*     */ {
/*  43 */   private static final PlatformLogger xembedLog = PlatformLogger.getLogger("sun.awt.X11.xembed.XEmbedClientHelper");
/*     */ 
/*     */   
/*     */   private XEmbeddedFramePeer embedded;
/*     */   
/*     */   private long server;
/*     */   
/*     */   private boolean active;
/*     */   
/*     */   private boolean applicationActive;
/*     */ 
/*     */   
/*     */   void setClient(XEmbeddedFramePeer paramXEmbeddedFramePeer) {
/*  56 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  57 */       xembedLog.fine("XEmbed client: " + paramXEmbeddedFramePeer);
/*     */     }
/*  59 */     if (this.embedded != null) {
/*  60 */       XToolkit.removeEventDispatcher(this.embedded.getWindow(), this);
/*  61 */       this.active = false;
/*     */     } 
/*  63 */     this.embedded = paramXEmbeddedFramePeer;
/*  64 */     if (this.embedded != null) {
/*  65 */       XToolkit.addEventDispatcher(this.embedded.getWindow(), this);
/*     */     }
/*     */   }
/*     */   
/*     */   void install() {
/*  70 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  71 */       xembedLog.fine("Installing xembedder on " + this.embedded);
/*     */     }
/*  73 */     long[] arrayOfLong = { 0L, 1L };
/*  74 */     long l1 = Native.card32ToData(arrayOfLong);
/*     */     try {
/*  76 */       XEmbedInfo.setAtomData(this.embedded.getWindow(), l1, 2);
/*     */     } finally {
/*  78 */       unsafe.freeMemory(l1);
/*     */     } 
/*     */ 
/*     */     
/*  82 */     long l2 = this.embedded.getParentWindowHandle();
/*  83 */     if (l2 != 0L) {
/*  84 */       XToolkit.awtLock();
/*     */       try {
/*  86 */         XlibWrapper.XReparentWindow(XToolkit.getDisplay(), this.embedded
/*  87 */             .getWindow(), l2, 0, 0);
/*     */       }
/*     */       finally {
/*     */         
/*  91 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void handleClientMessage(XEvent paramXEvent) {
/*  97 */     XClientMessageEvent xClientMessageEvent = paramXEvent.get_xclient();
/*  98 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  99 */       xembedLog.fine(xClientMessageEvent.toString());
/*     */     }
/* 101 */     if (xClientMessageEvent.get_message_type() == XEmbed.getAtom()) {
/* 102 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 103 */         xembedLog.fine("Embedded message: " + msgidToString((int)xClientMessageEvent.get_data(1)));
/*     */       }
/* 105 */       switch ((int)xClientMessageEvent.get_data(1)) {
/*     */         case 0:
/* 107 */           this.active = true;
/* 108 */           this.server = getEmbedder(this.embedded, xClientMessageEvent);
/*     */ 
/*     */           
/* 111 */           if (!this.embedded.isReparented()) {
/* 112 */             this.embedded.setReparented(true);
/* 113 */             this.embedded.updateSizeHints();
/*     */           } 
/* 115 */           this.embedded.notifyStarted();
/*     */           break;
/*     */         case 1:
/* 118 */           this.applicationActive = true;
/*     */           break;
/*     */         case 2:
/* 121 */           if (this.applicationActive) {
/* 122 */             this.applicationActive = false;
/* 123 */             handleWindowFocusOut();
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 4:
/* 128 */           handleFocusIn((int)xClientMessageEvent.get_data(2));
/*     */           break;
/*     */         case 5:
/* 131 */           if (this.applicationActive)
/* 132 */             handleWindowFocusOut(); 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void handleFocusIn(int paramInt) {
/* 139 */     if (this.embedded.focusAllowedFor()) {
/* 140 */       this.embedded.handleWindowFocusIn(0L);
/*     */     }
/* 142 */     switch (paramInt) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 147 */         SunToolkit.executeOnEventHandlerThread(this.embedded.target, new Runnable() {
/*     */               public void run() {
/* 149 */                 Component component = ((Container)XEmbedClientHelper.this.embedded.target).getFocusTraversalPolicy().getFirstComponent((Container)XEmbedClientHelper.this.embedded.target);
/* 150 */                 if (component != null)
/* 151 */                   component.requestFocusInWindow(); 
/*     */               }
/*     */             });
/*     */         break;
/*     */       case 2:
/* 156 */         SunToolkit.executeOnEventHandlerThread(this.embedded.target, new Runnable() {
/*     */               public void run() {
/* 158 */                 Component component = ((Container)XEmbedClientHelper.this.embedded.target).getFocusTraversalPolicy().getLastComponent((Container)XEmbedClientHelper.this.embedded.target);
/* 159 */                 if (component != null)
/* 160 */                   component.requestFocusInWindow(); 
/*     */               }
/*     */             });
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispatchEvent(XEvent paramXEvent) {
/* 168 */     switch (paramXEvent.get_type()) {
/*     */       case 33:
/* 170 */         handleClientMessage(paramXEvent);
/*     */         break;
/*     */       case 21:
/* 173 */         handleReparentNotify(paramXEvent);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void handleReparentNotify(XEvent paramXEvent) {
/* 178 */     XReparentEvent xReparentEvent = paramXEvent.get_xreparent();
/* 179 */     long l = xReparentEvent.get_parent();
/* 180 */     if (this.active) {
/*     */       
/* 182 */       this.embedded.notifyStopped();
/*     */       
/* 184 */       X11GraphicsConfig x11GraphicsConfig = (X11GraphicsConfig)this.embedded.getGraphicsConfiguration();
/* 185 */       X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsConfig.getDevice();
/* 186 */       if (l == XlibUtil.getRootWindow(x11GraphicsDevice.getScreen()) || l == 
/* 187 */         XToolkit.getDefaultRootWindow()) {
/*     */ 
/*     */         
/* 190 */         this.active = false;
/*     */       } else {
/*     */         
/* 193 */         this.server = l;
/* 194 */         this.embedded.notifyStarted();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   boolean requestFocus() {
/* 199 */     if (this.active && this.embedded.focusAllowedFor()) {
/* 200 */       sendMessage(this.server, 3);
/* 201 */       return true;
/*     */     } 
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleWindowFocusOut() {
/* 211 */     if (XKeyboardFocusManagerPeer.getInstance().getCurrentFocusedWindow() == this.embedded.target) {
/* 212 */       this.embedded.handleWindowFocusOut((Window)null, 0L);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   long getEmbedder(XWindowPeer paramXWindowPeer, XClientMessageEvent paramXClientMessageEvent) {
/* 218 */     return XlibUtil.getParentWindow(paramXWindowPeer.getWindow());
/*     */   }
/*     */   
/*     */   boolean isApplicationActive() {
/* 222 */     return this.applicationActive;
/*     */   }
/*     */   
/*     */   boolean isActive() {
/* 226 */     return this.active;
/*     */   }
/*     */   
/*     */   void traverseOutForward() {
/* 230 */     if (this.active) {
/* 231 */       sendMessage(this.server, 6);
/*     */     }
/*     */   }
/*     */   
/*     */   void traverseOutBackward() {
/* 236 */     if (this.active) {
/* 237 */       sendMessage(this.server, 7);
/*     */     }
/*     */   }
/*     */   
/*     */   void registerAccelerator(AWTKeyStroke paramAWTKeyStroke, int paramInt) {
/* 242 */     if (this.active) {
/* 243 */       long l1 = getX11KeySym(paramAWTKeyStroke);
/* 244 */       long l2 = getX11Mods(paramAWTKeyStroke);
/* 245 */       sendMessage(this.server, 12, paramInt, l1, l2);
/*     */     } 
/*     */   }
/*     */   void unregisterAccelerator(int paramInt) {
/* 249 */     if (this.active) {
/* 250 */       sendMessage(this.server, 13, paramInt, 0L, 0L);
/*     */     }
/*     */   }
/*     */   
/*     */   long getX11KeySym(AWTKeyStroke paramAWTKeyStroke) {
/* 255 */     XToolkit.awtLock();
/*     */     try {
/* 257 */       return XWindow.getKeySymForAWTKeyCode(paramAWTKeyStroke.getKeyCode());
/*     */     } finally {
/* 259 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   long getX11Mods(AWTKeyStroke paramAWTKeyStroke) {
/* 264 */     return XWindow.getXModifiers(paramAWTKeyStroke);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbedClientHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */