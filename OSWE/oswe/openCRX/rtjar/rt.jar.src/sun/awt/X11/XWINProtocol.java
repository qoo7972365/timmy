/*     */ package sun.awt.X11;
/*     */ 
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
/*     */ class XWINProtocol
/*     */   extends XProtocol
/*     */   implements XStateProtocol, XLayerProtocol
/*     */ {
/*  33 */   static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XWINProtocol");
/*     */ 
/*     */   
/*  36 */   XAtom XA_WIN_SUPPORTING_WM_CHECK = XAtom.get("_WIN_SUPPORTING_WM_CHECK");
/*  37 */   XAtom XA_WIN_PROTOCOLS = XAtom.get("_WIN_PROTOCOLS");
/*  38 */   XAtom XA_WIN_STATE = XAtom.get("_WIN_STATE");
/*     */   
/*     */   public boolean supportsState(int paramInt) {
/*  41 */     return doStateProtocol();
/*     */   }
/*     */   
/*     */   public void setState(XWindowPeer paramXWindowPeer, int paramInt) {
/*  45 */     if (paramXWindowPeer.isShowing()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  50 */       long l = 0L;
/*     */       
/*  52 */       if ((paramInt & 0x4) != 0) {
/*  53 */         l |= 0x4L;
/*     */       }
/*  55 */       if ((paramInt & 0x2) != 0) {
/*  56 */         l |= 0x8L;
/*     */       }
/*     */       
/*  59 */       XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*  60 */       xClientMessageEvent.set_type(33);
/*  61 */       xClientMessageEvent.set_window(paramXWindowPeer.getWindow());
/*  62 */       xClientMessageEvent.set_message_type(this.XA_WIN_STATE.getAtom());
/*  63 */       xClientMessageEvent.set_format(32);
/*  64 */       xClientMessageEvent.set_data(0, 12L);
/*  65 */       xClientMessageEvent.set_data(1, l);
/*  66 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  67 */         log.fine("Sending WIN_STATE to root to change the state to " + l);
/*     */       }
/*     */       try {
/*  70 */         XToolkit.awtLock();
/*  71 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/*  72 */             XlibWrapper.RootWindow(XToolkit.getDisplay(), paramXWindowPeer
/*  73 */               .getScreenNumber()), false, 1572864L, xClientMessageEvent.pData);
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/*  79 */         XToolkit.awtUnlock();
/*     */       } 
/*  81 */       xClientMessageEvent.dispose();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/*  89 */       long l1 = this.XA_WIN_STATE.getCard32Property(paramXWindowPeer);
/*  90 */       long l2 = l1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       if ((paramInt & 0x1) != 0) {
/*  99 */         l1 |= 0x2L;
/*     */       } else {
/* 101 */         l1 &= 0xFFFFFFFFFFFFFFFDL;
/*     */       } 
/*     */       
/* 104 */       if ((paramInt & 0x4) != 0) {
/* 105 */         l1 |= 0x4L;
/*     */       } else {
/* 107 */         l1 &= 0xFFFFFFFFFFFFFFFBL;
/*     */       } 
/*     */       
/* 110 */       if ((paramInt & 0x2) != 0) {
/* 111 */         l1 |= 0x8L;
/*     */       } else {
/* 113 */         l1 &= 0xFFFFFFFFFFFFFFF7L;
/*     */       } 
/* 115 */       if ((l2 ^ l1) != 0L) {
/* 116 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 117 */           log.fine("Setting WIN_STATE on " + paramXWindowPeer + " to change the state to " + l1);
/*     */         }
/* 119 */         this.XA_WIN_STATE.setCard32Property(paramXWindowPeer, l1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getState(XWindowPeer paramXWindowPeer) {
/* 125 */     long l = this.XA_WIN_STATE.getCard32Property(paramXWindowPeer);
/* 126 */     int i = 0;
/* 127 */     if ((l & 0x4L) != 0L) {
/* 128 */       i |= 0x4;
/*     */     }
/* 130 */     if ((l & 0x8L) != 0L) {
/* 131 */       i |= 0x2;
/*     */     }
/* 133 */     return i;
/*     */   }
/*     */   
/*     */   public boolean isStateChange(XPropertyEvent paramXPropertyEvent) {
/* 137 */     return (doStateProtocol() && paramXPropertyEvent.get_atom() == this.XA_WIN_STATE.getAtom());
/*     */   }
/*     */   
/*     */   public void unshadeKludge(XWindowPeer paramXWindowPeer) {
/* 141 */     long l = this.XA_WIN_STATE.getCard32Property(paramXWindowPeer);
/* 142 */     if ((l & 0x20L) == 0L) {
/*     */       return;
/*     */     }
/* 145 */     l &= 0xFFFFFFFFFFFFFFDFL;
/* 146 */     this.XA_WIN_STATE.setCard32Property(paramXWindowPeer, l);
/*     */   }
/*     */   
/*     */   public boolean supportsLayer(int paramInt) {
/* 150 */     return ((paramInt == 1 || paramInt == 0) && doLayerProtocol());
/*     */   }
/*     */   
/*     */   public void setLayer(XWindowPeer paramXWindowPeer, int paramInt) {
/* 154 */     if (paramXWindowPeer.isShowing()) {
/* 155 */       XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/* 156 */       xClientMessageEvent.set_type(33);
/* 157 */       xClientMessageEvent.set_window(paramXWindowPeer.getWindow());
/* 158 */       xClientMessageEvent.set_message_type(this.XA_WIN_LAYER.getAtom());
/* 159 */       xClientMessageEvent.set_format(32);
/* 160 */       xClientMessageEvent.set_data(0, (paramInt == 0) ? 4L : 6L);
/* 161 */       xClientMessageEvent.set_data(1, 0L);
/* 162 */       xClientMessageEvent.set_data(2, 0L);
/* 163 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 164 */         log.fine("Setting layer " + paramInt + " by root message : " + xClientMessageEvent);
/*     */       }
/* 166 */       XToolkit.awtLock();
/*     */       try {
/* 168 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 169 */             XlibWrapper.RootWindow(XToolkit.getDisplay(), paramXWindowPeer
/* 170 */               .getScreenNumber()), false, 524288L, xClientMessageEvent.pData);
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 176 */         XToolkit.awtUnlock();
/*     */       } 
/* 178 */       xClientMessageEvent.dispose();
/*     */     } else {
/* 180 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 181 */         log.fine("Setting layer property to " + paramInt);
/*     */       }
/* 183 */       this.XA_WIN_LAYER.setCard32Property(paramXWindowPeer, (paramInt == 0) ? 4L : 6L);
/*     */     } 
/*     */   }
/*     */   
/* 187 */   XAtom XA_WIN_LAYER = XAtom.get("_WIN_LAYER");
/*     */   
/*     */   static final int WIN_STATE_STICKY = 1;
/*     */   
/*     */   static final int WIN_STATE_MINIMIZED = 2;
/*     */   
/*     */   static final int WIN_STATE_MAXIMIZED_VERT = 4;
/*     */   
/*     */   static final int WIN_STATE_MAXIMIZED_HORIZ = 8;
/*     */   static final int WIN_STATE_HIDDEN = 16;
/*     */   static final int WIN_STATE_SHADED = 32;
/*     */   static final int WIN_LAYER_ONTOP = 6;
/*     */   static final int WIN_LAYER_NORMAL = 4;
/* 200 */   long WinWindow = 0L;
/*     */   
/*     */   void detect() {
/* 203 */     if (this.supportChecked) {
/*     */       return;
/*     */     }
/* 206 */     this.WinWindow = checkAnchor(this.XA_WIN_SUPPORTING_WM_CHECK, 6L);
/* 207 */     this.supportChecked = true;
/* 208 */     if (log.isLoggable(PlatformLogger.Level.FINE))
/* 209 */       log.fine("### " + this + " is active: " + ((this.WinWindow != 0L) ? 1 : 0)); 
/*     */   }
/*     */   boolean supportChecked = false;
/*     */   
/*     */   boolean active() {
/* 214 */     detect();
/* 215 */     return (this.WinWindow != 0L);
/*     */   }
/*     */   boolean doStateProtocol() {
/* 218 */     boolean bool = (active() && checkProtocol(this.XA_WIN_PROTOCOLS, this.XA_WIN_STATE)) ? true : false;
/* 219 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 220 */       log.fine("### " + this + " supports state: " + bool);
/*     */     }
/* 222 */     return bool;
/*     */   }
/*     */   
/*     */   boolean doLayerProtocol() {
/* 226 */     boolean bool = (active() && checkProtocol(this.XA_WIN_PROTOCOLS, this.XA_WIN_LAYER)) ? true : false;
/* 227 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 228 */       log.fine("### " + this + " supports layer: " + bool);
/*     */     }
/* 230 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XWINProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */