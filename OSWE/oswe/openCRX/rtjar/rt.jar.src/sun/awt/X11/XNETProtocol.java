/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.List;
/*     */ import sun.awt.IconInfo;
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
/*     */ final class XNETProtocol
/*     */   extends XProtocol
/*     */   implements XStateProtocol, XLayerProtocol
/*     */ {
/*  36 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XNETProtocol");
/*  37 */   private static final PlatformLogger iconLog = PlatformLogger.getLogger("sun.awt.X11.icon.XNETProtocol");
/*  38 */   private static PlatformLogger stateLog = PlatformLogger.getLogger("sun.awt.X11.states.XNETProtocol");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsState(int paramInt) {
/*  44 */     return doStateProtocol();
/*     */   }
/*     */   
/*     */   public void setState(XWindowPeer paramXWindowPeer, int paramInt) {
/*  48 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  49 */       log.fine("Setting state of " + paramXWindowPeer + " to " + paramInt);
/*     */     }
/*  51 */     if (paramXWindowPeer.isShowing()) {
/*  52 */       requestState(paramXWindowPeer, paramInt);
/*     */     } else {
/*  54 */       setInitialState(paramXWindowPeer, paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setInitialState(XWindowPeer paramXWindowPeer, int paramInt) {
/*  59 */     XAtomList xAtomList = paramXWindowPeer.getNETWMState();
/*  60 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  61 */       log.fine("Current state of the window {0} is {1}", new Object[] { paramXWindowPeer, xAtomList });
/*     */     }
/*  63 */     if ((paramInt & 0x4) != 0) {
/*  64 */       xAtomList.add(this.XA_NET_WM_STATE_MAXIMIZED_VERT);
/*     */     } else {
/*  66 */       xAtomList.remove(this.XA_NET_WM_STATE_MAXIMIZED_VERT);
/*     */     } 
/*  68 */     if ((paramInt & 0x2) != 0) {
/*  69 */       xAtomList.add(this.XA_NET_WM_STATE_MAXIMIZED_HORZ);
/*     */     } else {
/*  71 */       xAtomList.remove(this.XA_NET_WM_STATE_MAXIMIZED_HORZ);
/*     */     } 
/*  73 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  74 */       log.fine("Setting initial state of the window {0} to {1}", new Object[] { paramXWindowPeer, xAtomList });
/*     */     }
/*  76 */     paramXWindowPeer.setNETWMState(xAtomList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void requestState(XWindowPeer paramXWindowPeer, int paramInt) {
/*  85 */     int i = getState(paramXWindowPeer);
/*  86 */     int j = (paramInt ^ i) & 0x6;
/*     */     
/*  88 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/*  90 */       switch (j) {
/*     */         case 0:
/*     */           return;
/*     */         case 2:
/*  94 */           xClientMessageEvent.set_data(1, this.XA_NET_WM_STATE_MAXIMIZED_HORZ.getAtom());
/*  95 */           xClientMessageEvent.set_data(2, 0L);
/*     */           break;
/*     */         case 4:
/*  98 */           xClientMessageEvent.set_data(1, this.XA_NET_WM_STATE_MAXIMIZED_VERT.getAtom());
/*  99 */           xClientMessageEvent.set_data(2, 0L);
/*     */           break;
/*     */         case 6:
/* 102 */           xClientMessageEvent.set_data(1, this.XA_NET_WM_STATE_MAXIMIZED_HORZ.getAtom());
/* 103 */           xClientMessageEvent.set_data(2, this.XA_NET_WM_STATE_MAXIMIZED_VERT.getAtom());
/*     */           break;
/*     */         default:
/*     */           return;
/*     */       } 
/* 108 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 109 */         log.fine("Requesting state on " + paramXWindowPeer + " for " + paramInt);
/*     */       }
/* 111 */       xClientMessageEvent.set_type(33);
/* 112 */       xClientMessageEvent.set_window(paramXWindowPeer.getWindow());
/* 113 */       xClientMessageEvent.set_message_type(this.XA_NET_WM_STATE.getAtom());
/* 114 */       xClientMessageEvent.set_format(32);
/* 115 */       xClientMessageEvent.set_data(0, 2L);
/* 116 */       XToolkit.awtLock();
/*     */       try {
/* 118 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 119 */             XlibWrapper.RootWindow(XToolkit.getDisplay(), paramXWindowPeer.getScreenNumber()), false, 1572864L, xClientMessageEvent.pData);
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 125 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/* 128 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getState(XWindowPeer paramXWindowPeer) {
/* 133 */     return getStateImpl(paramXWindowPeer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getStateImpl(XWindowPeer paramXWindowPeer) {
/* 140 */     XAtomList xAtomList = paramXWindowPeer.getNETWMState();
/* 141 */     if (xAtomList.size() == 0) {
/* 142 */       return 0;
/*     */     }
/* 144 */     int i = 0;
/* 145 */     if (xAtomList.contains(this.XA_NET_WM_STATE_MAXIMIZED_VERT)) {
/* 146 */       i |= 0x4;
/*     */     }
/* 148 */     if (xAtomList.contains(this.XA_NET_WM_STATE_MAXIMIZED_HORZ)) {
/* 149 */       i |= 0x2;
/*     */     }
/* 151 */     return i;
/*     */   }
/*     */   
/*     */   public boolean isStateChange(XPropertyEvent paramXPropertyEvent) {
/* 155 */     boolean bool = (doStateProtocol() && paramXPropertyEvent.get_atom() == this.XA_NET_WM_STATE.getAtom()) ? true : false;
/*     */     
/* 157 */     if (bool) {
/*     */       
/* 159 */       XWindowPeer xWindowPeer = (XWindowPeer)XToolkit.windowToXWindow(paramXPropertyEvent.get_window());
/* 160 */       xWindowPeer.setNETWMState((XAtomList)null);
/*     */     } 
/* 162 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unshadeKludge(XWindowPeer paramXWindowPeer) {
/* 169 */     XAtomList xAtomList = paramXWindowPeer.getNETWMState();
/* 170 */     xAtomList.remove(this.XA_NET_WM_STATE_SHADED);
/* 171 */     paramXWindowPeer.setNETWMState(xAtomList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsLayer(int paramInt) {
/* 178 */     return ((paramInt == 1 || paramInt == 0) && doLayerProtocol());
/*     */   }
/*     */   
/*     */   public void requestState(XWindow paramXWindow, XAtom paramXAtom, boolean paramBoolean) {
/* 182 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     try {
/* 184 */       xClientMessageEvent.set_type(33);
/* 185 */       xClientMessageEvent.set_window(paramXWindow.getWindow());
/* 186 */       xClientMessageEvent.set_message_type(this.XA_NET_WM_STATE.getAtom());
/* 187 */       xClientMessageEvent.set_format(32);
/* 188 */       xClientMessageEvent.set_data(0, paramBoolean ? 1L : 0L);
/* 189 */       xClientMessageEvent.set_data(1, paramXAtom.getAtom());
/*     */       
/* 191 */       xClientMessageEvent.set_data(2, 0L);
/* 192 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 193 */         log.fine("Setting _NET_STATE atom {0} on {1} for {2}", new Object[] { paramXAtom, paramXWindow, Boolean.valueOf(paramBoolean) });
/*     */       }
/* 195 */       XToolkit.awtLock();
/*     */       try {
/* 197 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), 
/* 198 */             XlibWrapper.RootWindow(XToolkit.getDisplay(), paramXWindow.getScreenNumber()), false, 1572864L, xClientMessageEvent.pData);
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 204 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/* 207 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setStateHelper(XWindowPeer paramXWindowPeer, XAtom paramXAtom, boolean paramBoolean) {
/* 219 */     if (log.isLoggable(PlatformLogger.Level.FINER))
/* 220 */       log.finer("Window visibility is: withdrawn={0}, visible={1}, mapped={2} showing={3}", new Object[] {
/* 221 */             Boolean.valueOf(paramXWindowPeer.isWithdrawn()), Boolean.valueOf(paramXWindowPeer.isVisible()), 
/* 222 */             Boolean.valueOf(paramXWindowPeer.isMapped()), Boolean.valueOf(paramXWindowPeer.isShowing())
/*     */           }); 
/* 224 */     if (paramXWindowPeer.isShowing()) {
/* 225 */       requestState(paramXWindowPeer, paramXAtom, paramBoolean);
/*     */     } else {
/* 227 */       XAtomList xAtomList = paramXWindowPeer.getNETWMState();
/* 228 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 229 */         log.finer("Current state on {0} is {1}", new Object[] { paramXWindowPeer, xAtomList });
/*     */       }
/* 231 */       if (!paramBoolean) {
/* 232 */         xAtomList.remove(paramXAtom);
/*     */       } else {
/* 234 */         xAtomList.add(paramXAtom);
/*     */       } 
/* 236 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 237 */         log.fine("Setting states on {0} to {1}", new Object[] { paramXWindowPeer, xAtomList });
/*     */       }
/* 239 */       paramXWindowPeer.setNETWMState(xAtomList);
/*     */     } 
/* 241 */     XToolkit.XSync();
/*     */   }
/*     */   
/*     */   public void setLayer(XWindowPeer paramXWindowPeer, int paramInt) {
/* 245 */     setStateHelper(paramXWindowPeer, this.XA_NET_WM_STATE_ABOVE, (paramInt == 1));
/*     */   }
/*     */ 
/*     */   
/* 249 */   XAtom XA_UTF8_STRING = XAtom.get("UTF8_STRING");
/* 250 */   XAtom XA_NET_SUPPORTING_WM_CHECK = XAtom.get("_NET_SUPPORTING_WM_CHECK");
/* 251 */   XAtom XA_NET_SUPPORTED = XAtom.get("_NET_SUPPORTED");
/* 252 */   XAtom XA_NET_ACTIVE_WINDOW = XAtom.get("_NET_ACTIVE_WINDOW");
/* 253 */   XAtom XA_NET_WM_NAME = XAtom.get("_NET_WM_NAME");
/* 254 */   XAtom XA_NET_WM_STATE = XAtom.get("_NET_WM_STATE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 263 */   XAtom XA_NET_WM_STATE_MAXIMIZED_HORZ = XAtom.get("_NET_WM_STATE_MAXIMIZED_HORZ");
/* 264 */   XAtom XA_NET_WM_STATE_MAXIMIZED_VERT = XAtom.get("_NET_WM_STATE_MAXIMIZED_VERT");
/* 265 */   XAtom XA_NET_WM_STATE_SHADED = XAtom.get("_NET_WM_STATE_SHADED");
/* 266 */   XAtom XA_NET_WM_STATE_ABOVE = XAtom.get("_NET_WM_STATE_ABOVE");
/* 267 */   XAtom XA_NET_WM_STATE_MODAL = XAtom.get("_NET_WM_STATE_MODAL");
/* 268 */   XAtom XA_NET_WM_STATE_FULLSCREEN = XAtom.get("_NET_WM_STATE_FULLSCREEN");
/* 269 */   XAtom XA_NET_WM_STATE_BELOW = XAtom.get("_NET_WM_STATE_BELOW");
/* 270 */   XAtom XA_NET_WM_STATE_HIDDEN = XAtom.get("_NET_WM_STATE_HIDDEN");
/* 271 */   XAtom XA_NET_WM_STATE_SKIP_TASKBAR = XAtom.get("_NET_WM_STATE_SKIP_TASKBAR");
/* 272 */   XAtom XA_NET_WM_STATE_SKIP_PAGER = XAtom.get("_NET_WM_STATE_SKIP_PAGER");
/*     */   
/* 274 */   public final XAtom XA_NET_WM_WINDOW_TYPE = XAtom.get("_NET_WM_WINDOW_TYPE");
/* 275 */   public final XAtom XA_NET_WM_WINDOW_TYPE_NORMAL = XAtom.get("_NET_WM_WINDOW_TYPE_NORMAL");
/* 276 */   public final XAtom XA_NET_WM_WINDOW_TYPE_DIALOG = XAtom.get("_NET_WM_WINDOW_TYPE_DIALOG");
/* 277 */   public final XAtom XA_NET_WM_WINDOW_TYPE_UTILITY = XAtom.get("_NET_WM_WINDOW_TYPE_UTILITY");
/* 278 */   public final XAtom XA_NET_WM_WINDOW_TYPE_POPUP_MENU = XAtom.get("_NET_WM_WINDOW_TYPE_POPUP_MENU");
/*     */   
/* 280 */   XAtom XA_NET_WM_WINDOW_OPACITY = XAtom.get("_NET_WM_WINDOW_OPACITY");
/*     */   
/*     */   static final int _NET_WM_STATE_REMOVE = 0;
/*     */   
/*     */   static final int _NET_WM_STATE_ADD = 1;
/*     */   
/*     */   static final int _NET_WM_STATE_TOGGLE = 2;
/*     */   boolean supportChecked = false;
/* 288 */   long NetWindow = 0L;
/*     */   void detect() {
/* 290 */     if (this.supportChecked) {
/*     */       return;
/*     */     }
/*     */     
/* 294 */     this.NetWindow = checkAnchor(this.XA_NET_SUPPORTING_WM_CHECK, 33L);
/* 295 */     this.supportChecked = true;
/* 296 */     if (log.isLoggable(PlatformLogger.Level.FINE))
/* 297 */       log.fine("### " + this + " is active: " + ((this.NetWindow != 0L) ? 1 : 0)); 
/*     */   }
/*     */   String net_wm_name_cache;
/*     */   
/*     */   boolean active() {
/* 302 */     detect();
/* 303 */     return (this.NetWindow != 0L);
/*     */   }
/*     */   
/*     */   boolean doStateProtocol() {
/* 307 */     boolean bool = (active() && checkProtocol(this.XA_NET_SUPPORTED, this.XA_NET_WM_STATE)) ? true : false;
/* 308 */     if (stateLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 309 */       stateLog.finer("doStateProtocol() returns " + bool);
/*     */     }
/* 311 */     return bool;
/*     */   }
/*     */   
/*     */   boolean doLayerProtocol() {
/* 315 */     return (active() && checkProtocol(this.XA_NET_SUPPORTED, this.XA_NET_WM_STATE_ABOVE));
/*     */   }
/*     */ 
/*     */   
/*     */   boolean doModalityProtocol() {
/* 320 */     return (active() && checkProtocol(this.XA_NET_SUPPORTED, this.XA_NET_WM_STATE_MODAL));
/*     */   }
/*     */ 
/*     */   
/*     */   boolean doOpacityProtocol() {
/* 325 */     return (active() && checkProtocol(this.XA_NET_SUPPORTED, this.XA_NET_WM_WINDOW_OPACITY));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveWindow(XWindow paramXWindow) {
/* 330 */     if (!active() || !checkProtocol(this.XA_NET_SUPPORTED, this.XA_NET_ACTIVE_WINDOW)) {
/*     */       return;
/*     */     }
/*     */     
/* 334 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/* 335 */     xClientMessageEvent.zero();
/* 336 */     xClientMessageEvent.set_type(33);
/* 337 */     xClientMessageEvent.set_message_type(this.XA_NET_ACTIVE_WINDOW.getAtom());
/* 338 */     xClientMessageEvent.set_display(XToolkit.getDisplay());
/* 339 */     xClientMessageEvent.set_window(paramXWindow.getWindow());
/* 340 */     xClientMessageEvent.set_format(32);
/* 341 */     xClientMessageEvent.set_data(0, 1L);
/* 342 */     xClientMessageEvent.set_data(1, XToolkit.getCurrentServerTime());
/* 343 */     xClientMessageEvent.set_data(2, 0L);
/*     */     
/* 345 */     XToolkit.awtLock();
/*     */     try {
/* 347 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), XToolkit.getDefaultRootWindow(), false, 1572864L, xClientMessageEvent
/* 348 */           .getPData());
/*     */     } finally {
/* 350 */       XToolkit.awtUnlock();
/* 351 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isWMName(String paramString) {
/* 356 */     if (!active()) {
/* 357 */       return false;
/*     */     }
/* 359 */     String str = getWMName();
/* 360 */     if (str == null) {
/* 361 */       return false;
/*     */     }
/* 363 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 364 */       log.fine("### WM_NAME = " + str);
/*     */     }
/* 366 */     return str.startsWith(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWMName() {
/* 371 */     if (!active()) {
/* 372 */       return null;
/*     */     }
/*     */     
/* 375 */     if (this.net_wm_name_cache != null) {
/* 376 */       return this.net_wm_name_cache;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     String str = "UTF8";
/* 386 */     byte[] arrayOfByte = this.XA_NET_WM_NAME.getByteArrayProperty(this.NetWindow, this.XA_UTF8_STRING.getAtom());
/* 387 */     if (arrayOfByte == null) {
/* 388 */       arrayOfByte = this.XA_NET_WM_NAME.getByteArrayProperty(this.NetWindow, 31L);
/* 389 */       str = "ASCII";
/*     */     } 
/*     */     
/* 392 */     if (arrayOfByte == null) {
/* 393 */       return null;
/*     */     }
/*     */     try {
/* 396 */       this.net_wm_name_cache = new String(arrayOfByte, str);
/* 397 */       return this.net_wm_name_cache;
/* 398 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 399 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWMIcons(XWindowPeer paramXWindowPeer, List<IconInfo> paramList) {
/* 408 */     if (paramXWindowPeer == null)
/*     */       return; 
/* 410 */     XAtom xAtom = XAtom.get("_NET_WM_ICON");
/* 411 */     if (paramList == null) {
/* 412 */       xAtom.DeleteProperty(paramXWindowPeer);
/*     */       
/*     */       return;
/*     */     } 
/* 416 */     int i = 0;
/* 417 */     for (IconInfo iconInfo : paramList) {
/* 418 */       i += iconInfo.getRawLength();
/*     */     }
/* 420 */     byte b = (XlibWrapper.dataModel == 32) ? 4 : 8;
/* 421 */     int j = i * b;
/*     */     
/* 423 */     if (j != 0) {
/* 424 */       long l = XlibWrapper.unsafe.allocateMemory(j);
/*     */       try {
/* 426 */         long l1 = l;
/* 427 */         for (IconInfo iconInfo : paramList) {
/* 428 */           int k = iconInfo.getRawLength() * b;
/* 429 */           if (XlibWrapper.dataModel == 32) {
/* 430 */             XlibWrapper.copyIntArray(l1, iconInfo.getIntData(), k);
/*     */           } else {
/* 432 */             XlibWrapper.copyLongArray(l1, iconInfo.getLongData(), k);
/*     */           } 
/* 434 */           l1 += k;
/*     */         } 
/* 436 */         xAtom.setAtomData(paramXWindowPeer.getWindow(), 6L, l, j / Native.getCard32Size());
/*     */       } finally {
/* 438 */         XlibWrapper.unsafe.freeMemory(l);
/*     */       } 
/*     */     } else {
/* 441 */       xAtom.DeleteProperty(paramXWindowPeer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWMStateNetHidden(XWindowPeer paramXWindowPeer) {
/* 446 */     if (!doStateProtocol()) {
/* 447 */       return false;
/*     */     }
/* 449 */     XAtomList xAtomList = paramXWindowPeer.getNETWMState();
/* 450 */     return (xAtomList != null && xAtomList.size() != 0 && xAtomList.contains(this.XA_NET_WM_STATE_HIDDEN));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XNETProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */