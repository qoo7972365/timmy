/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Set;
/*     */ import sun.awt.X11GraphicsEnvironment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XlibUtil
/*     */ {
/*     */   public static long getRootWindow(int paramInt) {
/*  60 */     XToolkit.awtLock();
/*     */ 
/*     */     
/*     */     try {
/*  64 */       X11GraphicsEnvironment x11GraphicsEnvironment = (X11GraphicsEnvironment)GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  65 */       if (x11GraphicsEnvironment.runningXinerama())
/*     */       {
/*     */         
/*  68 */         return XlibWrapper.RootWindow(XToolkit.getDisplay(), 0L);
/*     */       }
/*     */ 
/*     */       
/*  72 */       return XlibWrapper.RootWindow(XToolkit.getDisplay(), paramInt);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/*  77 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isRoot(long paramLong1, long paramLong2) {
/*     */     long l;
/*  88 */     XToolkit.awtLock();
/*     */     
/*     */     try {
/*  91 */       l = XlibWrapper.RootWindow(XToolkit.getDisplay(), paramLong2);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/*  96 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/*  99 */     return (l == paramLong1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Rectangle getWindowGeometry(long paramLong) {
/* 107 */     XToolkit.awtLock();
/*     */     
/*     */     try {
/* 110 */       int i = XlibWrapper.XGetGeometry(XToolkit.getDisplay(), paramLong, XlibWrapper.larg1, XlibWrapper.larg2, XlibWrapper.larg3, XlibWrapper.larg4, XlibWrapper.larg5, XlibWrapper.larg6, XlibWrapper.larg7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       if (i == 0)
/*     */       {
/* 121 */         return null;
/*     */       }
/*     */       
/* 124 */       int j = Native.getInt(XlibWrapper.larg2);
/* 125 */       int k = Native.getInt(XlibWrapper.larg3);
/* 126 */       long l1 = Native.getUInt(XlibWrapper.larg4);
/* 127 */       long l2 = Native.getUInt(XlibWrapper.larg5);
/*     */       
/* 129 */       return new Rectangle(j, k, (int)l1, (int)l2);
/*     */     }
/*     */     finally {
/*     */       
/* 133 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Point translateCoordinates(long paramLong1, long paramLong2, Point paramPoint) {
/* 143 */     Point point = null;
/*     */     
/* 145 */     XToolkit.awtLock();
/*     */     
/*     */     try {
/* 148 */       XTranslateCoordinates xTranslateCoordinates = new XTranslateCoordinates(paramLong1, paramLong2, paramPoint.x, paramPoint.y);
/*     */ 
/*     */       
/*     */       try {
/* 152 */         int i = xTranslateCoordinates.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 153 */         if (i != 0 && (XErrorHandlerUtil.saved_error == null || XErrorHandlerUtil.saved_error
/*     */           
/* 155 */           .get_error_code() == 0))
/*     */         {
/* 157 */           point = new Point(xTranslateCoordinates.get_dest_x(), xTranslateCoordinates.get_dest_y());
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 162 */         xTranslateCoordinates.dispose();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 167 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 170 */     return point;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Rectangle translateCoordinates(long paramLong1, long paramLong2, Rectangle paramRectangle) {
/* 179 */     Point point = translateCoordinates(paramLong1, paramLong2, paramRectangle.getLocation());
/* 180 */     if (point == null)
/*     */     {
/* 182 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 186 */     return new Rectangle(point, paramRectangle.getSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getParentWindow(long paramLong) {
/* 195 */     XToolkit.awtLock();
/*     */     
/*     */     try {
/* 198 */       XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramLong);
/* 199 */       if (xBaseWindow != null) {
/*     */         
/* 201 */         XBaseWindow xBaseWindow1 = xBaseWindow.getParentWindow();
/* 202 */         if (xBaseWindow1 != null)
/*     */         {
/* 204 */           return xBaseWindow1.getWindow();
/*     */         }
/*     */       } 
/*     */       
/* 208 */       XQueryTree xQueryTree = new XQueryTree(paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 227 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Set<Long> getChildWindows(long paramLong) {
/* 236 */     XToolkit.awtLock();
/*     */     
/*     */     try {
/* 239 */       XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramLong);
/* 240 */       if (xBaseWindow != null)
/*     */       {
/* 242 */         return xBaseWindow.getChildren();
/*     */       }
/*     */       
/* 245 */       XQueryTree xQueryTree = new XQueryTree(paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isXAWTToplevelWindow(long paramLong) {
/* 288 */     return XToolkit.windowToXWindow(paramLong) instanceof XWindowPeer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isToplevelWindow(long paramLong) {
/* 296 */     if (XToolkit.windowToXWindow(paramLong) instanceof XDecoratedPeer)
/*     */     {
/* 298 */       return true;
/*     */     }
/*     */     
/* 301 */     XToolkit.awtLock();
/*     */     
/*     */     try {
/* 304 */       WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, XWM.XA_WM_STATE, 0L, 1L, false, XWM.XA_WM_STATE);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 309 */         windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 310 */         if (windowPropertyGetter.getActualType() == XWM.XA_WM_STATE.getAtom())
/*     */         {
/* 312 */           return true;
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 317 */         windowPropertyGetter.dispose();
/*     */       } 
/*     */       
/* 320 */       return false;
/*     */     }
/*     */     finally {
/*     */       
/* 324 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isTrueToplevelWindow(long paramLong) {
/* 334 */     if (XToolkit.windowToXWindow(paramLong) instanceof XEmbeddedFramePeer)
/*     */     {
/* 336 */       return false;
/*     */     }
/*     */     
/* 339 */     return isToplevelWindow(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   static int getWindowMapState(long paramLong) {
/* 344 */     XToolkit.awtLock();
/* 345 */     XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*     */     
/*     */     try {
/* 348 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/* 349 */       int i = XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), paramLong, xWindowAttributes.pData);
/*     */       
/* 351 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/* 352 */       if (i != 0 && (XErrorHandlerUtil.saved_error == null || XErrorHandlerUtil.saved_error
/*     */         
/* 354 */         .get_error_code() == 0))
/*     */       {
/* 356 */         return xWindowAttributes.get_map_state();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 361 */       xWindowAttributes.dispose();
/* 362 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 365 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 375 */   static Boolean isShapingSupported = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized boolean isShapingSupported() {
/* 383 */     if (isShapingSupported == null) {
/* 384 */       XToolkit.awtLock();
/*     */       
/*     */       try {
/* 387 */         isShapingSupported = Boolean.valueOf(XlibWrapper.XShapeQueryExtension(
/* 388 */               XToolkit.getDisplay(), XlibWrapper.larg1, XlibWrapper.larg2));
/*     */       }
/*     */       finally {
/*     */         
/* 392 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/*     */     
/* 396 */     return isShapingSupported.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getButtonMask(int paramInt) {
/* 403 */     if (paramInt <= 0 || paramInt > 5) {
/* 404 */       return 0;
/*     */     }
/* 406 */     return 1 << 7 + paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XlibUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */