/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ final class XlibWrapper
/*     */ {
/*  35 */   static Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int MAXSIZE = 32767;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int MINSIZE = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   static final String[] eventToString = new String[] { "<none:0>", "<none:1>", "KeyPress", "KeyRelease", "ButtonPress", "ButtonRelease", "MotionNotify", "EnterNotify", "LeaveNotify", "FocusIn", "FocusOut", "KeymapNotify", "Expose", "GraphicsExpose", "NoExpose", "VisibilityNotify", "CreateNotify", "DestroyNotify", "UnmapNotify", "MapNotify", "MapRequest", "ReparentNotify", "ConfigureNotify", "ConfigureRequest", "GravityNotify", "ResizeRequest", "CirculateNotify", "CirculateRequest", "PropertyNotify", "SelectionClear", "SelectionRequest", "SelectionNotify", "ColormapNotify", "ClientMessage", "MappingNotify", "LASTEvent" };
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
/*     */   static void XChangeProperty(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, int paramInt2, long paramLong5, int paramInt3) {
/* 323 */     if (XPropertyCache.isCachingSupported() && 
/* 324 */       XToolkit.windowToXWindow(paramLong2) != null && 
/* 325 */       WindowPropertyGetter.isCacheableProperty(XAtom.get(paramLong3)) && paramInt2 == 0) {
/*     */ 
/*     */       
/* 328 */       int i = paramInt1 / 8 * paramInt3;
/* 329 */       XPropertyCache.storeCache(new XPropertyCache.PropertyCacheEntry(paramInt1, paramInt3, 0L, paramLong5, i), paramLong2, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 336 */           XAtom.get(paramLong3));
/*     */     } 
/* 338 */     XChangePropertyImpl(paramLong1, paramLong2, paramLong3, paramLong4, paramInt1, paramInt2, paramLong5, paramInt3);
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
/* 569 */   static final long lbuffer = unsafe.allocateMemory(64L);
/* 570 */   static final long ibuffer = unsafe.allocateMemory(32L);
/*     */   
/* 572 */   static final long larg1 = lbuffer;
/* 573 */   static final long larg2 = larg1 + 8L;
/* 574 */   static final long larg3 = larg2 + 8L;
/* 575 */   static final long larg4 = larg3 + 8L;
/* 576 */   static final long larg5 = larg4 + 8L;
/* 577 */   static final long larg6 = larg5 + 8L;
/* 578 */   static final long larg7 = larg6 + 8L;
/* 579 */   static final long larg8 = larg7 + 8L;
/*     */   
/* 581 */   static final long iarg1 = ibuffer;
/* 582 */   static final long iarg2 = iarg1 + 4L;
/* 583 */   static final long iarg3 = iarg2 + 4L;
/* 584 */   static final long iarg4 = iarg3 + 4L;
/* 585 */   static final long iarg5 = iarg4 + 4L;
/* 586 */   static final long iarg6 = iarg5 + 4L;
/* 587 */   static final long iarg7 = iarg6 + 4L;
/* 588 */   static final long iarg8 = iarg7 + 4L;
/*     */ 
/*     */   
/*     */   static int dataModel;
/*     */ 
/*     */   
/*     */   static {
/* 595 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.arch.data.model"));
/*     */     
/*     */     try {
/* 598 */       dataModel = Integer.parseInt(str);
/* 599 */     } catch (Exception exception) {
/* 600 */       dataModel = 32;
/*     */     } 
/*     */   }
/* 603 */   static final boolean isBuildInternal = getBuildInternal();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getDataModel() {
/* 609 */     return dataModel;
/*     */   }
/*     */   
/*     */   static String hintsToString(long paramLong) {
/* 613 */     StringBuffer stringBuffer = new StringBuffer();
/* 614 */     if ((paramLong & 0x20L) != 0L) {
/* 615 */       stringBuffer.append("PMaxSize ");
/*     */     }
/* 617 */     if ((paramLong & 0x10L) != 0L) {
/* 618 */       stringBuffer.append("PMinSize ");
/*     */     }
/* 620 */     if ((paramLong & 0x2L) != 0L) {
/* 621 */       stringBuffer.append("USSize ");
/*     */     }
/* 623 */     if ((paramLong & 0x1L) != 0L) {
/* 624 */       stringBuffer.append("USPosition ");
/*     */     }
/* 626 */     if ((paramLong & 0x4L) != 0L) {
/* 627 */       stringBuffer.append("PPosition ");
/*     */     }
/* 629 */     if ((paramLong & 0x8L) != 0L) {
/* 630 */       stringBuffer.append("PSize ");
/*     */     }
/* 632 */     if ((paramLong & 0x200L) != 0L) {
/* 633 */       stringBuffer.append("PWinGravity ");
/*     */     }
/* 635 */     return stringBuffer.toString();
/*     */   }
/*     */   static String getEventToString(int paramInt) {
/* 638 */     if (paramInt >= 0 && paramInt < eventToString.length)
/* 639 */       return eventToString[paramInt]; 
/* 640 */     if (paramInt == XToolkit.getXKBBaseEventCode())
/*     */     {
/* 642 */       return "XkbEvent";
/*     */     }
/* 644 */     return eventToString[0];
/*     */   }
/*     */   
/*     */   private static boolean getBuildInternal() {
/* 648 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.version"));
/*     */     
/* 650 */     return (str != null && str.contains("internal"));
/*     */   }
/*     */   
/*     */   static native void XFree(long paramLong);
/*     */   
/*     */   static native void memcpy(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long getAddress(Object paramObject);
/*     */   
/*     */   static native void copyIntArray(long paramLong, Object paramObject, int paramInt);
/*     */   
/*     */   static native void copyLongArray(long paramLong, Object paramObject, int paramInt);
/*     */   
/*     */   static native byte[] getStringBytes(long paramLong);
/*     */   
/*     */   static native long XOpenDisplay(long paramLong);
/*     */   
/*     */   static native void XCloseDisplay(long paramLong);
/*     */   
/*     */   static native long XDisplayString(long paramLong);
/*     */   
/*     */   static native void XSetCloseDownMode(long paramLong, int paramInt);
/*     */   
/*     */   static native long DefaultScreen(long paramLong);
/*     */   
/*     */   static native long ScreenOfDisplay(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int DoesBackingStore(long paramLong);
/*     */   
/*     */   static native long DisplayWidth(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long DisplayWidthMM(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long DisplayHeight(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long DisplayHeightMM(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long RootWindow(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int ScreenCount(long paramLong);
/*     */   
/*     */   static native long XCreateWindow(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
/*     */   
/*     */   static native void XDestroyWindow(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int XGrabPointer(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong3, long paramLong4, long paramLong5);
/*     */   
/*     */   static native void XUngrabPointer(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int XGrabKeyboard(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3);
/*     */   
/*     */   static native void XUngrabKeyboard(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XGrabServer(long paramLong);
/*     */   
/*     */   static native void XUngrabServer(long paramLong);
/*     */   
/*     */   static native void XMapWindow(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XMapRaised(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XRaiseWindow(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XLowerWindow(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XRestackWindows(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native void XConfigureWindow(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void XSetInputFocus(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XSetInputFocus2(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long XGetInputFocus(long paramLong);
/*     */   
/*     */   static native void XUnmapWindow(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XSelectInput(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XNextEvent(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XMaskEvent(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XWindowEvent(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native boolean XFilterEvent(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean XSupportsLocale();
/*     */   
/*     */   static native String XSetLocaleModifiers(String paramString);
/*     */   
/*     */   static native int XTranslateCoordinates(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
/*     */   
/*     */   static native void XPeekEvent(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XFlush(long paramLong);
/*     */   
/*     */   static native void XSync(long paramLong, int paramInt);
/*     */   
/*     */   static native void XMoveResizeWindow(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   static native void XResizeWindow(long paramLong1, long paramLong2, int paramInt1, int paramInt2);
/*     */   
/*     */   static native void XMoveWindow(long paramLong1, long paramLong2, int paramInt1, int paramInt2);
/*     */   
/*     */   static native boolean XQueryPointer(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9);
/*     */   
/*     */   static native void XFreeCursor(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XSetWindowBackground(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native int XEventsQueued(long paramLong, int paramInt);
/*     */   
/*     */   static native int XInternAtoms(long paramLong1, String[] paramArrayOfString, boolean paramBoolean, long paramLong2);
/*     */   
/*     */   static native void SetProperty(long paramLong1, long paramLong2, long paramLong3, String paramString);
/*     */   
/*     */   static native String GetProperty(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long InternAtom(long paramLong, String paramString, int paramInt);
/*     */   
/*     */   static native int XGetWindowProperty(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12);
/*     */   
/*     */   static native void XChangePropertyImpl(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, int paramInt2, long paramLong5, int paramInt3);
/*     */   
/*     */   static native void XChangePropertyS(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, int paramInt2, String paramString);
/*     */   
/*     */   static native void XDeleteProperty(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XSetTransientFor(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XSetWMHints(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XGetWMHints(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long XAllocWMHints();
/*     */   
/*     */   static native int XGetPointerMapping(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native String XGetDefault(long paramLong, String paramString1, String paramString2);
/*     */   
/*     */   static native long getScreenOfWindow(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long XScreenNumberOfScreen(long paramLong);
/*     */   
/*     */   static native int XIconifyWindow(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native String ServerVendor(long paramLong);
/*     */   
/*     */   static native int VendorRelease(long paramLong);
/*     */   
/*     */   static native boolean IsXsunKPBehavior(long paramLong);
/*     */   
/*     */   static native boolean IsSunKeyboard(long paramLong);
/*     */   
/*     */   static native boolean IsKanaKeyboard(long paramLong);
/*     */   
/*     */   static native void XBell(long paramLong, int paramInt);
/*     */   
/*     */   static native int XCreateFontCursor(long paramLong, int paramInt);
/*     */   
/*     */   static native long XCreateBitmapFromData(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2);
/*     */   
/*     */   static native void XFreePixmap(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long XCreatePixmapCursor(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt1, int paramInt2);
/*     */   
/*     */   static native boolean XQueryBestCursor(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native boolean XAllocColor(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long SetToolkitErrorHandler();
/*     */   
/*     */   static native void XSetErrorHandler(long paramLong);
/*     */   
/*     */   static native int CallErrorHandler(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XChangeWindowAttributes(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native int XGetWindowAttributes(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native int XGetGeometry(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9);
/*     */   
/*     */   static native int XGetWMNormalHints(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void XSetWMNormalHints(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XSetMinMaxHints(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong3);
/*     */   
/*     */   static native long XAllocSizeHints();
/*     */   
/*     */   static native int XSendEvent(long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void XPutBackEvent(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int XQueryTree(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
/*     */   
/*     */   static native long XGetVisualInfo(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void XReparentWindow(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2);
/*     */   
/*     */   static native void XConvertSelection(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
/*     */   
/*     */   static native void XSetSelectionOwner(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native long XGetSelectionOwner(long paramLong1, long paramLong2);
/*     */   
/*     */   static native String XGetAtomName(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long XMaxRequestSize(long paramLong);
/*     */   
/*     */   static native long XCreatePixmap(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   static native long XCreateImage(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */   
/*     */   static native void XDestroyImage(long paramLong);
/*     */   
/*     */   static native void XPutImage(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   static native long XCreateGC(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void XFreeGC(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void XSetWindowBackgroundPixmap(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XClearWindow(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int XGetIconSizes(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native int XdbeQueryExtension(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native boolean XQueryExtension(long paramLong1, String paramString, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native boolean IsKeypadKey(long paramLong);
/*     */   
/*     */   static native long XdbeAllocateBackBufferName(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native int XdbeDeallocateBackBufferName(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int XdbeBeginIdiom(long paramLong);
/*     */   
/*     */   static native int XdbeEndIdiom(long paramLong);
/*     */   
/*     */   static native int XdbeSwapBuffers(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native void XQueryKeymap(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long XKeycodeToKeysym(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   static native int XKeysymToKeycode(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int XkbGetEffectiveGroup(long paramLong);
/*     */   
/*     */   static native long XkbKeycodeToKeysym(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   static native void XkbSelectEvents(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void XkbSelectEventDetails(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5);
/*     */   
/*     */   static native boolean XkbQueryExtension(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6);
/*     */   
/*     */   static native boolean XkbLibraryVersion(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long XkbGetMap(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long XkbGetUpdatedMap(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void XkbFreeKeyboard(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */   
/*     */   static native boolean XkbTranslateKeyCode(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4);
/*     */   
/*     */   static native void XkbSetDetectableAutoRepeat(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void XConvertCase(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native long XGetModifierMapping(long paramLong);
/*     */   
/*     */   static native void XFreeModifiermap(long paramLong);
/*     */   
/*     */   static native void XRefreshKeyboardMapping(long paramLong);
/*     */   
/*     */   static native void XChangeActivePointerGrab(long paramLong1, int paramInt, long paramLong2, long paramLong3);
/*     */   
/*     */   static native int XSynchronize(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native boolean XNextSecondaryLoopEvent(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void ExitSecondaryLoop();
/*     */   
/*     */   static native String[] XTextPropertyToStringList(byte[] paramArrayOfbyte, long paramLong);
/*     */   
/*     */   static native boolean XShapeQueryExtension(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void SetRectangularShape(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Region paramRegion);
/*     */   
/*     */   static native void SetBitmapShape(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int[] paramArrayOfint);
/*     */   
/*     */   static native void SetZOrder(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native void PrintXErrorEvent(long paramLong1, long paramLong2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XlibWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */