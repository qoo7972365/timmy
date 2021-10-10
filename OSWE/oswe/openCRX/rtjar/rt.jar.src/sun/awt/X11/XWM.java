/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import sun.awt.IconInfo;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class XWM
/*      */ {
/*   53 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XWM");
/*   54 */   private static final PlatformLogger insLog = PlatformLogger.getLogger("sun.awt.X11.insets.XWM");
/*   55 */   private static final PlatformLogger stateLog = PlatformLogger.getLogger("sun.awt.X11.states.XWM");
/*      */   
/*   57 */   static final XAtom XA_MWM_HINTS = new XAtom();
/*      */   
/*   59 */   private static Unsafe unsafe = XlibWrapper.unsafe;
/*      */ 
/*      */ 
/*      */   
/*   63 */   static XAtom XA_WM_STATE = new XAtom();
/*      */ 
/*      */   
/*   66 */   XAtom XA_UTF8_STRING = XAtom.get("UTF8_STRING");
/*      */ 
/*      */   
/*      */   static final int AWT_NET_N_KNOWN_STATES = 2;
/*      */ 
/*      */   
/*   72 */   static final XAtom XA_E_FRAME_SIZE = new XAtom();
/*      */ 
/*      */   
/*   75 */   static final XAtom XA_KDE_NET_WM_FRAME_STRUT = new XAtom();
/*      */ 
/*      */   
/*   78 */   static final XAtom XA_KWM_WIN_ICONIFIED = new XAtom();
/*   79 */   static final XAtom XA_KWM_WIN_MAXIMIZED = new XAtom();
/*      */ 
/*      */   
/*   82 */   static final XAtom XA_OL_DECOR_DEL = new XAtom();
/*   83 */   static final XAtom XA_OL_DECOR_HEADER = new XAtom();
/*   84 */   static final XAtom XA_OL_DECOR_RESIZE = new XAtom();
/*   85 */   static final XAtom XA_OL_DECOR_PIN = new XAtom();
/*   86 */   static final XAtom XA_OL_DECOR_CLOSE = new XAtom();
/*      */ 
/*      */   
/*   89 */   static final XAtom XA_NET_FRAME_EXTENTS = new XAtom();
/*   90 */   static final XAtom XA_NET_REQUEST_FRAME_EXTENTS = new XAtom();
/*      */   static final int UNDETERMINED_WM = 1;
/*      */   static final int NO_WM = 2;
/*      */   static final int OTHER_WM = 3;
/*      */   static final int OPENLOOK_WM = 4;
/*      */   static final int MOTIF_WM = 5;
/*      */   static final int CDE_WM = 6;
/*      */   static final int ENLIGHTEN_WM = 7;
/*      */   static final int KDE2_WM = 8;
/*      */   static final int SAWFISH_WM = 9;
/*      */   static final int ICE_WM = 10;
/*      */   static final int METACITY_WM = 11;
/*      */   static final int COMPIZ_WM = 12;
/*      */   static final int LG3D_WM = 13;
/*      */   static final int CWM_WM = 14;
/*      */   static final int MUTTER_WM = 15;
/*      */   static final int OTHER_NONREPARENTING_WM = 16;
/*      */   int WMID;
/*      */   
/*      */   public String toString() {
/*  110 */     switch (this.WMID) {
/*      */       case 2:
/*  112 */         return "NO WM";
/*      */       case 3:
/*  114 */         return "Other WM";
/*      */       case 4:
/*  116 */         return "OPENLOOK";
/*      */       case 5:
/*  118 */         return "MWM";
/*      */       case 6:
/*  120 */         return "DTWM";
/*      */       case 7:
/*  122 */         return "Enlightenment";
/*      */       case 8:
/*  124 */         return "KWM2";
/*      */       case 9:
/*  126 */         return "Sawfish";
/*      */       case 10:
/*  128 */         return "IceWM";
/*      */       case 11:
/*  130 */         return "Metacity";
/*      */       case 12:
/*  132 */         return "Compiz";
/*      */       case 13:
/*  134 */         return "LookingGlass";
/*      */       case 14:
/*  136 */         return "CWM";
/*      */       case 15:
/*  138 */         return "Mutter";
/*      */     } 
/*      */     
/*  141 */     return "Undetermined WM";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   static final Insets zeroInsets = new Insets(0, 0, 0, 0);
/*  148 */   static final Insets defaultInsets = new Insets(25, 5, 5, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getID() {
/*  158 */     return this.WMID;
/*      */   }
/*      */ 
/*      */   
/*      */   static Insets normalize(Insets paramInsets) {
/*  163 */     if (paramInsets.top > 64 || paramInsets.top < 0) {
/*  164 */       paramInsets.top = 28;
/*      */     }
/*  166 */     if (paramInsets.left > 32 || paramInsets.left < 0) {
/*  167 */       paramInsets.left = 6;
/*      */     }
/*  169 */     if (paramInsets.right > 32 || paramInsets.right < 0) {
/*  170 */       paramInsets.right = 6;
/*      */     }
/*  172 */     if (paramInsets.bottom > 32 || paramInsets.bottom < 0) {
/*  173 */       paramInsets.bottom = 6;
/*      */     }
/*  175 */     return paramInsets;
/*      */   }
/*      */   
/*  178 */   static XNETProtocol g_net_protocol = null;
/*  179 */   static XWINProtocol g_win_protocol = null;
/*      */   static boolean isNetWMName(String paramString) {
/*  181 */     if (g_net_protocol != null) {
/*  182 */       return g_net_protocol.isWMName(paramString);
/*      */     }
/*  184 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static void initAtoms() {
/*  189 */     Object[][] arrayOfObject = { { XA_WM_STATE, "WM_STATE" }, { XA_KDE_NET_WM_FRAME_STRUT, "_KDE_NET_WM_FRAME_STRUT" }, { XA_E_FRAME_SIZE, "_E_FRAME_SIZE" }, { XA_KWM_WIN_ICONIFIED, "KWM_WIN_ICONIFIED" }, { XA_KWM_WIN_MAXIMIZED, "KWM_WIN_MAXIMIZED" }, { XA_OL_DECOR_DEL, "_OL_DECOR_DEL" }, { XA_OL_DECOR_HEADER, "_OL_DECOR_HEADER" }, { XA_OL_DECOR_RESIZE, "_OL_DECOR_RESIZE" }, { XA_OL_DECOR_PIN, "_OL_DECOR_PIN" }, { XA_OL_DECOR_CLOSE, "_OL_DECOR_CLOSE" }, { XA_MWM_HINTS, "_MOTIF_WM_HINTS" }, { XA_NET_FRAME_EXTENTS, "_NET_FRAME_EXTENTS" }, { XA_NET_REQUEST_FRAME_EXTENTS, "_NET_REQUEST_FRAME_EXTENTS" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  209 */     String[] arrayOfString = new String[arrayOfObject.length]; int i;
/*  210 */     for (i = 0; i < arrayOfString.length; i++) {
/*  211 */       arrayOfString[i] = (String)arrayOfObject[i][1];
/*      */     }
/*      */     
/*  214 */     i = XAtom.getAtomSize();
/*  215 */     long l = unsafe.allocateMemory((arrayOfString.length * i));
/*  216 */     XToolkit.awtLock();
/*      */     try {
/*  218 */       int j = XlibWrapper.XInternAtoms(XToolkit.getDisplay(), arrayOfString, false, l);
/*  219 */       if (j == 0)
/*      */         return; 
/*      */       int k;
/*  222 */       for (byte b = 0; b < arrayOfString.length; b++, k += i) {
/*  223 */         ((XAtom)arrayOfObject[b][0]).setValues(XToolkit.getDisplay(), arrayOfString[b], XAtom.getAtom(l + k));
/*      */       }
/*      */     } finally {
/*  226 */       XToolkit.awtUnlock();
/*  227 */       unsafe.freeMemory(l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNoWM() {
/*  252 */     String str = XlibWrapper.ServerVendor(XToolkit.getDisplay());
/*  253 */     if (str.indexOf("eXcursion") != -1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  259 */       if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  260 */         insLog.finer("eXcursion means NO_WM");
/*      */       }
/*  262 */       return true;
/*      */     } 
/*      */     
/*  265 */     XSetWindowAttributes xSetWindowAttributes = new XSetWindowAttributes();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  271 */       long l1 = XlibWrapper.DefaultScreen(XToolkit.getDisplay());
/*  272 */       String str1 = "WM_S" + l1;
/*      */ 
/*      */       
/*  275 */       long l2 = XlibWrapper.XGetSelectionOwner(XToolkit.getDisplay(), 
/*  276 */           XAtom.get(str1).getAtom());
/*  277 */       if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  278 */         insLog.finer("selection owner of " + str1 + " is " + l2);
/*      */       }
/*      */ 
/*      */       
/*  282 */       if (l2 != 0L) {
/*  283 */         return false;
/*      */       }
/*      */       
/*  286 */       winmgr_running = false;
/*  287 */       xSetWindowAttributes.set_event_mask(1048576L);
/*      */       
/*  289 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(detectWMHandler);
/*  290 */       XlibWrapper.XChangeWindowAttributes(XToolkit.getDisplay(), 
/*  291 */           XToolkit.getDefaultRootWindow(), 2048L, xSetWindowAttributes.pData);
/*      */ 
/*      */       
/*  294 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  300 */       if (!winmgr_running) {
/*  301 */         xSetWindowAttributes.set_event_mask(0L);
/*  302 */         XlibWrapper.XChangeWindowAttributes(XToolkit.getDisplay(), 
/*  303 */             XToolkit.getDefaultRootWindow(), 2048L, xSetWindowAttributes.pData);
/*      */ 
/*      */         
/*  306 */         if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  307 */           insLog.finer("It looks like there is no WM thus NO_WM");
/*      */         }
/*      */       } 
/*      */       
/*  311 */       return !winmgr_running;
/*      */     } finally {
/*  313 */       xSetWindowAttributes.dispose();
/*      */     } 
/*      */   }
/*      */   
/*  317 */   static XAtom XA_ENLIGHTENMENT_COMMS = new XAtom("ENLIGHTENMENT_COMMS", false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long getECommsWindowIDProperty(long paramLong) {
/*  326 */     if (!XA_ENLIGHTENMENT_COMMS.isInterned()) {
/*  327 */       return 0L;
/*      */     }
/*      */     
/*  330 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, XA_ENLIGHTENMENT_COMMS, 0L, 14L, false, 31L);
/*      */ 
/*      */     
/*      */     try {
/*  334 */       int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*  335 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/*  336 */         return 0L;
/*      */       }
/*      */       
/*  339 */       if (windowPropertyGetter.getActualType() != 31L || windowPropertyGetter
/*  340 */         .getActualFormat() != 8 || windowPropertyGetter
/*  341 */         .getNumberOfItems() != 14 || windowPropertyGetter.getBytesAfter() != 0L)
/*      */       {
/*  343 */         return 0L;
/*      */       }
/*      */ 
/*      */       
/*  347 */       byte[] arrayOfByte = XlibWrapper.getStringBytes(windowPropertyGetter.getData());
/*  348 */       String str = new String(arrayOfByte);
/*      */       
/*  350 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  351 */         log.finer("ENLIGHTENMENT_COMMS is " + str);
/*      */       }
/*      */ 
/*      */       
/*  355 */       Pattern pattern = Pattern.compile("WINID\\s+(\\p{XDigit}{0,8})");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  382 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isEnlightenment() {
/*  392 */     long l1 = getECommsWindowIDProperty(XToolkit.getDefaultRootWindow());
/*  393 */     if (l1 == 0L) {
/*  394 */       return false;
/*      */     }
/*      */     
/*  397 */     long l2 = getECommsWindowIDProperty(l1);
/*  398 */     if (l2 != l1) {
/*  399 */       return false;
/*      */     }
/*      */     
/*  402 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  417 */   static final XAtom XA_DT_SM_WINDOW_INFO = new XAtom("_DT_SM_WINDOW_INFO", false);
/*  418 */   static final XAtom XA_DT_SM_STATE_INFO = new XAtom("_DT_SM_STATE_INFO", false);
/*      */   
/*      */   static boolean isCDE() {
/*  421 */     if (!XA_DT_SM_WINDOW_INFO.isInterned()) {
/*  422 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  423 */         log.finer("{0} is not interned", new Object[] { XA_DT_SM_WINDOW_INFO });
/*      */       }
/*  425 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  429 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(XToolkit.getDefaultRootWindow(), XA_DT_SM_WINDOW_INFO, 0L, 2L, false, XA_DT_SM_WINDOW_INFO);
/*      */ 
/*      */     
/*      */     try {
/*  433 */       int i = windowPropertyGetter.execute();
/*  434 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/*  435 */         log.finer("Getting of _DT_SM_WINDOW_INFO is not successfull");
/*  436 */         return false;
/*      */       } 
/*  438 */       if (windowPropertyGetter.getActualType() != XA_DT_SM_WINDOW_INFO.getAtom() || windowPropertyGetter
/*  439 */         .getActualFormat() != 32 || windowPropertyGetter
/*  440 */         .getNumberOfItems() != 2 || windowPropertyGetter.getBytesAfter() != 0L) {
/*      */         
/*  442 */         log.finer("Wrong format of _DT_SM_WINDOW_INFO");
/*  443 */         return false;
/*      */       } 
/*      */       
/*  446 */       long l = Native.getWindow(windowPropertyGetter.getData(), 1);
/*      */       
/*  448 */       if (l == 0L) {
/*  449 */         log.fine("WARNING: DT_SM_WINDOW_INFO exists but returns zero windows");
/*  450 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  454 */       if (!XA_DT_SM_STATE_INFO.isInterned()) {
/*  455 */         if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  456 */           log.finer("{0} is not interned", new Object[] { XA_DT_SM_STATE_INFO });
/*      */         }
/*  458 */         return false;
/*      */       } 
/*  460 */       WindowPropertyGetter windowPropertyGetter1 = new WindowPropertyGetter(l, XA_DT_SM_STATE_INFO, 0L, 1L, false, XA_DT_SM_STATE_INFO);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  483 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  494 */   static final XAtom XA_MOTIF_WM_INFO = new XAtom("_MOTIF_WM_INFO", false);
/*  495 */   static final XAtom XA_DT_WORKSPACE_CURRENT = new XAtom("_DT_WORKSPACE_CURRENT", false);
/*      */   
/*      */   static boolean isMotif() {
/*  498 */     if (!XA_MOTIF_WM_INFO.isInterned()) {
/*  499 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  503 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(XToolkit.getDefaultRootWindow(), XA_MOTIF_WM_INFO, 0L, 2L, false, XA_MOTIF_WM_INFO);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  508 */       int i = windowPropertyGetter.execute();
/*      */       
/*  510 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/*  511 */         return false;
/*      */       }
/*      */       
/*  514 */       if (windowPropertyGetter.getActualType() != XA_MOTIF_WM_INFO.getAtom() || windowPropertyGetter
/*  515 */         .getActualFormat() != 32 || windowPropertyGetter
/*  516 */         .getNumberOfItems() != 2 || windowPropertyGetter
/*  517 */         .getBytesAfter() != 0L)
/*      */       {
/*  519 */         return false;
/*      */       }
/*      */       
/*  522 */       long l = Native.getLong(windowPropertyGetter.getData(), 1);
/*  523 */       if (l != 0L) {
/*  524 */         if (XA_DT_WORKSPACE_CURRENT.isInterned()) {
/*      */           
/*  526 */           XAtom[] arrayOfXAtom = XA_DT_WORKSPACE_CURRENT.getAtomListProperty(l);
/*  527 */           if (arrayOfXAtom.length == 0) {
/*  528 */             return false;
/*      */           }
/*  530 */           return true;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  535 */         WindowPropertyGetter windowPropertyGetter1 = new WindowPropertyGetter(l, XA_WM_STATE, 0L, 1L, false, XA_WM_STATE);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  541 */           if (windowPropertyGetter1.execute() == 0 && windowPropertyGetter1
/*  542 */             .getData() != 0L && windowPropertyGetter1
/*  543 */             .getActualType() == XA_WM_STATE.getAtom())
/*      */           {
/*  545 */             return true;
/*      */           }
/*      */         } finally {
/*  548 */           windowPropertyGetter1.dispose();
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  553 */       windowPropertyGetter.dispose();
/*      */     } 
/*  555 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isSawfish() {
/*  562 */     return isNetWMName("Sawfish");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isKDE2() {
/*  569 */     return isNetWMName("KWin");
/*      */   }
/*      */   
/*      */   static boolean isCompiz() {
/*  573 */     return isNetWMName("compiz");
/*      */   }
/*      */   
/*      */   static boolean isLookingGlass() {
/*  577 */     return isNetWMName("LG3D");
/*      */   }
/*      */   
/*      */   static boolean isCWM() {
/*  581 */     return isNetWMName("CWM");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isMetacity() {
/*  588 */     return isNetWMName("Metacity");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isMutter() {
/*  596 */     return (isNetWMName("Mutter") || isNetWMName("GNOME Shell"));
/*      */   }
/*      */   
/*  599 */   static int awtWMNonReparenting = -1;
/*      */   static boolean isNonReparentingWM() {
/*  601 */     if (awtWMNonReparenting == -1) {
/*  602 */       awtWMNonReparenting = (XToolkit.getEnv("_JAVA_AWT_WM_NONREPARENTING") != null) ? 1 : 0;
/*      */     }
/*  604 */     return (awtWMNonReparenting == 1 || getWMID() == 12 || 
/*  605 */       getWMID() == 13 || getWMID() == 14 || 
/*  606 */       getWMID() == 16);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  622 */   static final XAtom XA_ICEWM_WINOPTHINT = new XAtom("_ICEWM_WINOPTHINT", false);
/*  623 */   static final char[] opt = new char[] { 'A', 'W', 'T', '_', 'I', 'C', 'E', 'W', 'M', '_', 'T', 'E', 'S', 'T', Character.MIN_VALUE, 'a', 'l', 'l', 'W', 'o', 'r', 'k', 's', 'p', 'a', 'c', 'e', 's', Character.MIN_VALUE, '0', Character.MIN_VALUE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean prepareIsIceWM() {
/*  634 */     if (!XA_ICEWM_WINOPTHINT.isInterned()) {
/*  635 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  636 */         log.finer("{0} is not interned", new Object[] { XA_ICEWM_WINOPTHINT });
/*      */       }
/*  638 */       return false;
/*      */     } 
/*      */     
/*  641 */     XToolkit.awtLock();
/*      */     try {
/*  643 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/*  644 */       XlibWrapper.XChangePropertyS(XToolkit.getDisplay(), XToolkit.getDefaultRootWindow(), XA_ICEWM_WINOPTHINT
/*  645 */           .getAtom(), XA_ICEWM_WINOPTHINT
/*  646 */           .getAtom(), 8, 0, new String(opt));
/*      */ 
/*      */       
/*  649 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*      */       
/*  651 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/*  652 */         .get_error_code() != 0) {
/*  653 */         log.finer("Erorr getting XA_ICEWM_WINOPTHINT property");
/*  654 */         return false;
/*      */       } 
/*  656 */       log.finer("Prepared for IceWM detection");
/*  657 */       return true;
/*      */     } finally {
/*  659 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isIceWM() {
/*  670 */     if (!XA_ICEWM_WINOPTHINT.isInterned()) {
/*  671 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  672 */         log.finer("{0} is not interned", new Object[] { XA_ICEWM_WINOPTHINT });
/*      */       }
/*  674 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  678 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(XToolkit.getDefaultRootWindow(), XA_ICEWM_WINOPTHINT, 0L, 65535L, true, XA_ICEWM_WINOPTHINT);
/*      */ 
/*      */     
/*      */     try {
/*  682 */       int i = windowPropertyGetter.execute();
/*  683 */       boolean bool = (i == 0 && windowPropertyGetter.getActualType() != 0L) ? true : false;
/*  684 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  685 */         log.finer("Status getting XA_ICEWM_WINOPTHINT: " + (!bool ? 1 : 0));
/*      */       }
/*  687 */       return (!bool || isNetWMName("IceWM"));
/*      */     } finally {
/*  689 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  699 */   static final XAtom XA_SUN_WM_PROTOCOLS = new XAtom("_SUN_WM_PROTOCOLS", false);
/*      */   static boolean isOpenLook() {
/*  701 */     if (!XA_SUN_WM_PROTOCOLS.isInterned()) {
/*  702 */       return false;
/*      */     }
/*      */     
/*  705 */     XAtom[] arrayOfXAtom = XA_SUN_WM_PROTOCOLS.getAtomListProperty(XToolkit.getDefaultRootWindow());
/*  706 */     return (arrayOfXAtom.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean winmgr_running = false;
/*      */ 
/*      */   
/*  714 */   private static XErrorHandler detectWMHandler = new XErrorHandler.XBaseErrorHandler()
/*      */     {
/*      */       public int handleError(long param1Long, XErrorEvent param1XErrorEvent) {
/*  717 */         if (param1XErrorEvent.get_request_code() == 2 && param1XErrorEvent
/*  718 */           .get_error_code() == 10) {
/*      */           
/*  720 */           XWM.winmgr_running = true;
/*  721 */           return 0;
/*      */         } 
/*  723 */         return super.handleError(param1Long, param1XErrorEvent);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  731 */   static int awt_wmgr = 1; static XWM wm; private HashMap<Class<?>, Collection<?>> protocolsMap;
/*      */   
/*      */   static XWM getWM() {
/*  734 */     if (wm == null) {
/*  735 */       wm = new XWM(awt_wmgr = getWMID());
/*      */     }
/*  737 */     return wm;
/*      */   }
/*      */   static int getWMID() {
/*  740 */     if (insLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  741 */       insLog.finest("awt_wmgr = " + awt_wmgr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  748 */     if (awt_wmgr != 1) {
/*  749 */       return awt_wmgr;
/*      */     }
/*      */     
/*  752 */     XSetWindowAttributes xSetWindowAttributes = new XSetWindowAttributes();
/*  753 */     XToolkit.awtLock();
/*      */     try {
/*  755 */       if (isNoWM()) {
/*  756 */         awt_wmgr = 2;
/*  757 */         return awt_wmgr;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  762 */       XNETProtocol xNETProtocol = g_net_protocol = new XNETProtocol();
/*  763 */       xNETProtocol.detect();
/*  764 */       if (log.isLoggable(PlatformLogger.Level.FINE) && xNETProtocol.active()) {
/*  765 */         log.fine("_NET_WM_NAME is " + xNETProtocol.getWMName());
/*      */       }
/*  767 */       XWINProtocol xWINProtocol = g_win_protocol = new XWINProtocol();
/*  768 */       xWINProtocol.detect();
/*      */ 
/*      */       
/*  771 */       boolean bool = prepareIsIceWM();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  777 */       if (isEnlightenment()) {
/*  778 */         awt_wmgr = 7;
/*  779 */       } else if (isMetacity()) {
/*  780 */         awt_wmgr = 11;
/*  781 */       } else if (isMutter()) {
/*  782 */         awt_wmgr = 15;
/*  783 */       } else if (isSawfish()) {
/*  784 */         awt_wmgr = 9;
/*  785 */       } else if (isKDE2()) {
/*  786 */         awt_wmgr = 8;
/*  787 */       } else if (isCompiz()) {
/*  788 */         awt_wmgr = 12;
/*  789 */       } else if (isLookingGlass()) {
/*  790 */         awt_wmgr = 13;
/*  791 */       } else if (isCWM()) {
/*  792 */         awt_wmgr = 14;
/*  793 */       } else if (bool && isIceWM()) {
/*  794 */         awt_wmgr = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  800 */       else if (xNETProtocol.active()) {
/*  801 */         if (XToolkit.getEnv("_JAVA_AWT_WM_NONREPARENTING") != null) {
/*  802 */           awt_wmgr = 16;
/*      */         } else {
/*  804 */           awt_wmgr = 3;
/*      */         } 
/*  806 */       } else if (xWINProtocol.active()) {
/*  807 */         if (XToolkit.getEnv("_JAVA_AWT_WM_NONREPARENTING") != null) {
/*  808 */           awt_wmgr = 16;
/*      */         } else {
/*  810 */           awt_wmgr = 3;
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*  816 */       else if (isCDE()) {
/*  817 */         awt_wmgr = 6;
/*  818 */       } else if (isMotif()) {
/*  819 */         awt_wmgr = 5;
/*  820 */       } else if (isOpenLook()) {
/*  821 */         awt_wmgr = 4;
/*  822 */       } else if (XToolkit.getEnv("_JAVA_AWT_WM_NONREPARENTING") != null) {
/*  823 */         awt_wmgr = 16;
/*      */       } else {
/*  825 */         awt_wmgr = 3;
/*      */       } 
/*      */       
/*  828 */       return awt_wmgr;
/*      */     } finally {
/*  830 */       XToolkit.awtUnlock();
/*  831 */       xSetWindowAttributes.dispose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void removeSizeHints(XDecoratedPeer paramXDecoratedPeer, long paramLong) {
/*  848 */     paramLong &= 0x30L;
/*      */     
/*  850 */     XToolkit.awtLock();
/*      */     try {
/*  852 */       XSizeHints xSizeHints = paramXDecoratedPeer.getHints();
/*  853 */       if ((xSizeHints.get_flags() & paramLong) == 0L) {
/*      */         return;
/*      */       }
/*      */       
/*  857 */       xSizeHints.set_flags(xSizeHints.get_flags() & (paramLong ^ 0xFFFFFFFFFFFFFFFFL));
/*  858 */       if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  859 */         insLog.finer("Setting hints, flags " + XlibWrapper.hintsToString(xSizeHints.get_flags()));
/*      */       }
/*  861 */       XlibWrapper.XSetWMNormalHints(XToolkit.getDisplay(), paramXDecoratedPeer
/*  862 */           .getWindow(), xSizeHints.pData);
/*      */     } finally {
/*      */       
/*  865 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int normalizeMotifDecor(int paramInt) {
/*  876 */     if ((paramInt & 0x1) == 0) {
/*  877 */       return paramInt;
/*      */     }
/*  879 */     int i = 126;
/*      */ 
/*      */ 
/*      */     
/*  883 */     i &= paramInt ^ 0xFFFFFFFF;
/*  884 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int normalizeMotifFunc(int paramInt) {
/*  894 */     if ((paramInt & 0x1) == 0) {
/*  895 */       return paramInt;
/*      */     }
/*  897 */     int i = 62;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  902 */     i &= paramInt ^ 0xFFFFFFFF;
/*  903 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void setOLDecor(XWindow paramXWindow, boolean paramBoolean, int paramInt) {
/*  911 */     if (paramXWindow == null) {
/*      */       return;
/*      */     }
/*      */     
/*  915 */     XAtomList xAtomList = new XAtomList();
/*  916 */     paramInt = normalizeMotifDecor(paramInt);
/*  917 */     if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  918 */       insLog.finer("Setting OL_DECOR to " + Integer.toBinaryString(paramInt));
/*      */     }
/*  920 */     if ((paramInt & 0x8) == 0) {
/*  921 */       xAtomList.add(XA_OL_DECOR_HEADER);
/*      */     }
/*  923 */     if ((paramInt & 0x44) == 0) {
/*  924 */       xAtomList.add(XA_OL_DECOR_RESIZE);
/*      */     }
/*  926 */     if ((paramInt & 0x70) == 0)
/*      */     {
/*      */ 
/*      */       
/*  930 */       xAtomList.add(XA_OL_DECOR_CLOSE);
/*      */     }
/*  932 */     if (xAtomList.size() == 0) {
/*  933 */       insLog.finer("Deleting OL_DECOR");
/*  934 */       XA_OL_DECOR_DEL.DeleteProperty(paramXWindow);
/*      */     } else {
/*  936 */       if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  937 */         insLog.finer("Setting OL_DECOR to " + xAtomList);
/*      */       }
/*  939 */       XA_OL_DECOR_DEL.setAtomListProperty(paramXWindow, xAtomList);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void setMotifDecor(XWindow paramXWindow, boolean paramBoolean, int paramInt1, int paramInt2) {
/*  948 */     if ((paramInt1 & 0x1) != 0 && paramInt1 != 1)
/*      */     {
/*      */       
/*  951 */       paramInt1 = normalizeMotifDecor(paramInt1);
/*      */     }
/*  953 */     if ((paramInt2 & 0x1) != 0 && paramInt2 != 1)
/*      */     {
/*      */       
/*  956 */       paramInt2 = normalizeMotifFunc(paramInt2);
/*      */     }
/*      */     
/*  959 */     PropMwmHints propMwmHints = paramXWindow.getMWMHints();
/*  960 */     propMwmHints.set_flags(propMwmHints.get_flags() | 0x1L | 0x2L);
/*      */ 
/*      */     
/*  963 */     propMwmHints.set_functions(paramInt2);
/*  964 */     propMwmHints.set_decorations(paramInt1);
/*      */     
/*  966 */     if (stateLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  967 */       stateLog.finer("Setting MWM_HINTS to " + propMwmHints);
/*      */     }
/*  969 */     paramXWindow.setMWMHints(propMwmHints);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean needRemap(XDecoratedPeer paramXDecoratedPeer) {
/*  991 */     return !paramXDecoratedPeer.isEmbedded();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void setShellDecor(XDecoratedPeer paramXDecoratedPeer) {
/*  999 */     int i = paramXDecoratedPeer.getDecorations();
/* 1000 */     int j = paramXDecoratedPeer.getFunctions();
/* 1001 */     boolean bool = paramXDecoratedPeer.isResizable();
/*      */     
/* 1003 */     if (!bool) {
/* 1004 */       if ((i & 0x1) != 0) {
/* 1005 */         i |= 0x44;
/*      */       } else {
/* 1007 */         i &= 0xFFFFFFBB;
/*      */       } 
/*      */     }
/* 1010 */     setMotifDecor(paramXDecoratedPeer, bool, i, j);
/* 1011 */     setOLDecor(paramXDecoratedPeer, bool, i);
/*      */ 
/*      */     
/* 1014 */     if (paramXDecoratedPeer.isShowing() && needRemap(paramXDecoratedPeer)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1020 */       paramXDecoratedPeer.xSetVisible(false);
/* 1021 */       XToolkit.XSync();
/* 1022 */       paramXDecoratedPeer.xSetVisible(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void setShellResizable(XDecoratedPeer paramXDecoratedPeer) {
/* 1030 */     if (insLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1031 */       insLog.fine("Setting shell resizable " + paramXDecoratedPeer);
/*      */     }
/* 1033 */     XToolkit.awtLock();
/*      */     try {
/* 1035 */       Rectangle rectangle = paramXDecoratedPeer.getShellBounds();
/* 1036 */       rectangle.translate(-paramXDecoratedPeer.currentInsets.left, -paramXDecoratedPeer.currentInsets.top);
/* 1037 */       paramXDecoratedPeer.updateSizeHints(paramXDecoratedPeer.getDimensions());
/* 1038 */       requestWMExtents(paramXDecoratedPeer.getWindow());
/* 1039 */       XlibWrapper.XMoveResizeWindow(XToolkit.getDisplay(), paramXDecoratedPeer.getShell(), rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1044 */       removeSizeHints(paramXDecoratedPeer, 32L);
/* 1045 */       paramXDecoratedPeer.updateMinimumSize();
/*      */ 
/*      */       
/* 1048 */       setShellDecor(paramXDecoratedPeer);
/*      */     } finally {
/* 1050 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void setShellNotResizable(XDecoratedPeer paramXDecoratedPeer, WindowDimensions paramWindowDimensions, Rectangle paramRectangle, boolean paramBoolean) {
/* 1062 */     if (insLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1063 */       insLog.fine("Setting non-resizable shell " + paramXDecoratedPeer + ", dimensions " + paramWindowDimensions + ", shellBounds " + paramRectangle + ", just change size: " + paramBoolean);
/*      */     }
/*      */     
/* 1066 */     XToolkit.awtLock();
/*      */ 
/*      */     
/* 1069 */     try { if (!paramRectangle.isEmpty()) {
/* 1070 */         paramXDecoratedPeer.updateSizeHints(paramWindowDimensions);
/* 1071 */         requestWMExtents(paramXDecoratedPeer.getWindow());
/* 1072 */         XToolkit.XSync();
/* 1073 */         XlibWrapper.XMoveResizeWindow(XToolkit.getDisplay(), paramXDecoratedPeer.getShell(), paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */       } 
/*      */       
/* 1076 */       if (!paramBoolean) {
/* 1077 */         setShellDecor(paramXDecoratedPeer);
/*      */       } }
/*      */     finally
/* 1080 */     { XToolkit.awtUnlock(); } 
/*      */   } <T> Collection<T> getProtocols(Class<T> paramClass) { Collection<T> collection = (Collection)this.protocolsMap.get(paramClass); if (collection != null)
/*      */       return collection;  return new LinkedList<>(); } private <T> void addProtocol(Class<T> paramClass, T paramT) { Collection<T> collection = getProtocols(paramClass); collection.add(paramT); this.protocolsMap.put(paramClass, collection); }
/*      */   boolean supportsDynamicLayout() { int i = getWMID(); switch (i) { case 7: case 8: case 9: case 10: case 11: return true;case 4: case 5: case 6: return false; }  return false; }
/*      */   boolean supportsExtendedState(int paramInt) { switch (paramInt) { case 2: case 4: if (getWMID() == 11)
/*      */           return false; case 6: for (XStateProtocol xStateProtocol : getProtocols(XStateProtocol.class)) { if (xStateProtocol.supportsState(paramInt))
/*      */             return true;  }  break; }  return false; }
/* 1087 */   XWM(int paramInt) { this.protocolsMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1340 */     this.storedInsets = new HashMap<>(); this.WMID = paramInt; initializeProtocols(); if (log.isLoggable(PlatformLogger.Level.FINE)) log.fine("Window manager: " + toString());  }
/*      */   int getExtendedState(XWindowPeer paramXWindowPeer) { int i = 0; for (XStateProtocol xStateProtocol : getProtocols(XStateProtocol.class)) i |= xStateProtocol.getState(paramXWindowPeer);  if (i != 0)
/* 1342 */       return i;  return 0; } Insets guessInsets(XDecoratedPeer paramXDecoratedPeer) { Insets insets = (Insets)this.storedInsets.get(paramXDecoratedPeer.getClass());
/* 1343 */     if (insets == null)
/* 1344 */       switch (this.WMID) {
/*      */         case 7:
/* 1346 */           insets = new Insets(19, 4, 4, 4);
/*      */           break;
/*      */         case 6:
/* 1349 */           insets = new Insets(28, 6, 6, 6);
/*      */           break;
/*      */         case 2:
/*      */         case 13:
/*      */         case 16:
/* 1354 */           insets = zeroInsets;
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/* 1359 */           insets = defaultInsets;
/*      */           break;
/*      */       }  
/* 1362 */     if (insLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1363 */       insLog.finest("WM guessed insets: " + insets);
/*      */     }
/* 1365 */     return insets; }
/*      */   boolean isStateChange(XDecoratedPeer paramXDecoratedPeer, XPropertyEvent paramXPropertyEvent) { if (!paramXDecoratedPeer.isShowing()) { stateLog.finer("Window is not showing"); return false; }  int i = paramXDecoratedPeer.getWMState(); if (i == 0) { stateLog.finer("WithdrawnState"); return false; }  if (stateLog.isLoggable(PlatformLogger.Level.FINER))
/*      */       stateLog.finer("Window WM_STATE is " + i);  boolean bool = false; if (paramXPropertyEvent.get_atom() == XA_WM_STATE.getAtom())
/*      */       bool = true;  for (XStateProtocol xStateProtocol : getProtocols(XStateProtocol.class)) { bool |= xStateProtocol.isStateChange(paramXPropertyEvent); if (stateLog.isLoggable(PlatformLogger.Level.FINEST))
/*      */         stateLog.finest(xStateProtocol + ": is state changed = " + bool);  }  return bool; }
/*      */   int getState(XDecoratedPeer paramXDecoratedPeer) { int i = 0; int j = paramXDecoratedPeer.getWMState(); if (j == 3) { i = 1; } else { i = 0; }  i |= getExtendedState(paramXDecoratedPeer); return i; }
/*      */   void setLayer(XWindowPeer paramXWindowPeer, int paramInt) { for (XLayerProtocol xLayerProtocol : getProtocols(XLayerProtocol.class)) { if (xLayerProtocol.supportsLayer(paramInt))
/*      */         xLayerProtocol.setLayer(paramXWindowPeer, paramInt);  }  XToolkit.XSync(); }
/*      */   void setExtendedState(XWindowPeer paramXWindowPeer, int paramInt) { for (XStateProtocol xStateProtocol : getProtocols(XStateProtocol.class)) { if (xStateProtocol.supportsState(paramInt)) { xStateProtocol.setState(paramXWindowPeer, paramInt); break; }  }  if (!paramXWindowPeer.isShowing()) { XToolkit.awtLock(); try { XlibWrapper.XDeleteProperty(XToolkit.getDisplay(), paramXWindowPeer.getWindow(), XA_KWM_WIN_ICONIFIED.getAtom()); XlibWrapper.XDeleteProperty(XToolkit.getDisplay(), paramXWindowPeer.getWindow(), XA_KWM_WIN_MAXIMIZED.getAtom()); } finally { XToolkit.awtUnlock(); }
/*      */        }
/*      */      XToolkit.XSync(); }
/*      */   void unshadeKludge(XDecoratedPeer paramXDecoratedPeer) { assert paramXDecoratedPeer.isShowing(); for (XStateProtocol xStateProtocol : getProtocols(XStateProtocol.class))
/* 1377 */       xStateProtocol.unshadeKludge(paramXDecoratedPeer);  XToolkit.XSync(); } static int awtWMStaticGravity = -1; static boolean inited = false; HashMap storedInsets; static void init() { if (inited) return;  initAtoms(); getWM(); inited = true; }
/*      */   void initializeProtocols() { XNETProtocol xNETProtocol = g_net_protocol; if (xNETProtocol != null) if (!xNETProtocol.active()) { xNETProtocol = null; } else { if (xNETProtocol.doStateProtocol()) addProtocol(XStateProtocol.class, xNETProtocol);  if (xNETProtocol.doLayerProtocol()) addProtocol(XLayerProtocol.class, xNETProtocol);  }   XWINProtocol xWINProtocol = g_win_protocol; if (xWINProtocol != null && xWINProtocol.active()) { if (xWINProtocol.doStateProtocol()) addProtocol(XStateProtocol.class, xWINProtocol);  if (xWINProtocol.doLayerProtocol())
/*      */         addProtocol(XLayerProtocol.class, xWINProtocol);  }  }
/* 1380 */   static boolean configureGravityBuggy() { if (awtWMStaticGravity == -1) {
/* 1381 */       awtWMStaticGravity = (XToolkit.getEnv("_JAVA_AWT_WM_STATIC_GRAVITY") != null) ? 1 : 0;
/*      */     }
/*      */     
/* 1384 */     if (awtWMStaticGravity == 1) {
/* 1385 */       return true;
/*      */     }
/*      */     
/* 1388 */     switch (getWMID()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/* 1398 */         if (g_net_protocol != null) {
/* 1399 */           String str = g_net_protocol.getWMName();
/* 1400 */           Pattern pattern = Pattern.compile("^IceWM (\\d+)\\.(\\d+)\\.(\\d+).*$");
/*      */           try {
/* 1402 */             Matcher matcher = pattern.matcher(str);
/* 1403 */             if (matcher.matches()) {
/* 1404 */               int i = Integer.parseInt(matcher.group(1));
/* 1405 */               int j = Integer.parseInt(matcher.group(2));
/* 1406 */               int k = Integer.parseInt(matcher.group(3));
/* 1407 */               return (i <= 1 && (i != 1 || (j <= 2 && (j != 2 || k < 2))));
/*      */             } 
/* 1409 */           } catch (Exception exception) {
/* 1410 */             return true;
/*      */           } 
/*      */         } 
/* 1413 */         return true;
/*      */       
/*      */       case 7:
/* 1416 */         return true;
/*      */     } 
/* 1418 */     return false; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Insets getInsetsFromExtents(long paramLong) {
/* 1427 */     if (paramLong == 0L) {
/* 1428 */       return null;
/*      */     }
/* 1430 */     XNETProtocol xNETProtocol = getWM().getNETProtocol();
/* 1431 */     if (xNETProtocol != null && xNETProtocol.active()) {
/* 1432 */       Insets insets = getInsetsFromProp(paramLong, XA_NET_FRAME_EXTENTS);
/* 1433 */       if (insLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1434 */         insLog.fine("_NET_FRAME_EXTENTS: {0}", new Object[] { insets });
/*      */       }
/*      */       
/* 1437 */       if (insets != null) {
/* 1438 */         return insets;
/*      */       }
/*      */     } 
/* 1441 */     switch (getWMID()) {
/*      */       case 8:
/* 1443 */         return getInsetsFromProp(paramLong, XA_KDE_NET_WM_FRAME_STRUT);
/*      */       case 7:
/* 1445 */         return getInsetsFromProp(paramLong, XA_E_FRAME_SIZE);
/*      */     } 
/* 1447 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Insets getInsetsFromProp(long paramLong, XAtom paramXAtom) {
/* 1456 */     if (paramLong == 0L) {
/* 1457 */       return null;
/*      */     }
/*      */     
/* 1460 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, paramXAtom, 0L, 4L, false, 6L);
/*      */ 
/*      */     
/*      */     try {
/* 1464 */       if (windowPropertyGetter.execute() != 0 || windowPropertyGetter
/* 1465 */         .getData() == 0L || windowPropertyGetter
/* 1466 */         .getActualType() != 6L || windowPropertyGetter
/* 1467 */         .getActualFormat() != 32)
/*      */       {
/* 1469 */         return null;
/*      */       }
/* 1471 */       return new Insets((int)Native.getCard32(windowPropertyGetter.getData(), 2), 
/* 1472 */           (int)Native.getCard32(windowPropertyGetter.getData(), 0), 
/* 1473 */           (int)Native.getCard32(windowPropertyGetter.getData(), 3), 
/* 1474 */           (int)Native.getCard32(windowPropertyGetter.getData(), 1));
/*      */     } finally {
/*      */       
/* 1477 */       windowPropertyGetter.dispose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void requestWMExtents(long paramLong) {
/* 1485 */     if (paramLong == 0L) {
/*      */       return;
/*      */     }
/*      */     
/* 1489 */     log.fine("Requesting FRAME_EXTENTS");
/*      */     
/* 1491 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/* 1492 */     xClientMessageEvent.zero();
/* 1493 */     xClientMessageEvent.set_type(33);
/* 1494 */     xClientMessageEvent.set_display(XToolkit.getDisplay());
/* 1495 */     xClientMessageEvent.set_window(paramLong);
/* 1496 */     xClientMessageEvent.set_format(32);
/* 1497 */     XToolkit.awtLock();
/*      */     try {
/* 1499 */       XNETProtocol xNETProtocol = getWM().getNETProtocol();
/* 1500 */       if (xNETProtocol != null && xNETProtocol.active()) {
/* 1501 */         xClientMessageEvent.set_message_type(XA_NET_REQUEST_FRAME_EXTENTS.getAtom());
/* 1502 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), XToolkit.getDefaultRootWindow(), false, 1572864L, xClientMessageEvent
/*      */ 
/*      */             
/* 1505 */             .getPData());
/*      */       } 
/* 1507 */       if (getWMID() == 8) {
/* 1508 */         xClientMessageEvent.set_message_type(XA_KDE_NET_WM_FRAME_STRUT.getAtom());
/* 1509 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), XToolkit.getDefaultRootWindow(), false, 1572864L, xClientMessageEvent
/*      */ 
/*      */             
/* 1512 */             .getPData());
/*      */       } 
/*      */     } finally {
/*      */       
/* 1516 */       XToolkit.awtUnlock();
/* 1517 */       xClientMessageEvent.dispose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean syncTopLevelPos(long paramLong, XWindowAttributes paramXWindowAttributes) {
/* 1531 */     byte b = 0;
/* 1532 */     XToolkit.awtLock();
/*      */     try {
/*      */       do {
/* 1535 */         XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), paramLong, paramXWindowAttributes.pData);
/* 1536 */         if (paramXWindowAttributes.get_x() != 0 || paramXWindowAttributes.get_y() != 0) {
/* 1537 */           return true;
/*      */         }
/* 1539 */         b++;
/* 1540 */         XToolkit.XSync();
/* 1541 */       } while (b < 50);
/*      */     } finally {
/*      */       
/* 1544 */       XToolkit.awtUnlock();
/*      */     } 
/* 1546 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Insets getInsets(XDecoratedPeer paramXDecoratedPeer, long paramLong1, long paramLong2) {
/* 1575 */     Insets insets = getInsetsFromExtents(paramLong1);
/* 1576 */     if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1577 */       insLog.finer("Got insets from property: {0}", new Object[] { insets });
/*      */     }
/*      */     
/* 1580 */     if (insets == null) {
/* 1581 */       insets = new Insets(0, 0, 0, 0);
/*      */       
/* 1583 */       insets.top = -1;
/* 1584 */       insets.left = -1;
/*      */       
/* 1586 */       XWindowAttributes xWindowAttributes1 = new XWindowAttributes();
/* 1587 */       XWindowAttributes xWindowAttributes2 = new XWindowAttributes(); try {
/*      */         Insets insets1; int i;
/* 1589 */         switch (getWMID()) {
/*      */ 
/*      */           
/*      */           case 7:
/* 1593 */             syncTopLevelPos(paramLong2, xWindowAttributes1);
/* 1594 */             insets.left = xWindowAttributes1.get_x();
/* 1595 */             insets.top = xWindowAttributes1.get_y();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1602 */             XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), 
/* 1603 */                 XlibUtil.getParentWindow(paramLong2), xWindowAttributes2.pData);
/*      */             
/* 1605 */             insets
/* 1606 */               .right = xWindowAttributes2.get_width() - xWindowAttributes1.get_width() + insets.left;
/* 1607 */             insets
/* 1608 */               .bottom = xWindowAttributes2.get_height() - xWindowAttributes1.get_height() + insets.top;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 5:
/*      */           case 6:
/*      */           case 8:
/*      */           case 10:
/* 1617 */             if (syncTopLevelPos(paramLong2, xWindowAttributes1)) {
/* 1618 */               insets.top = xWindowAttributes1.get_y();
/* 1619 */               insets.left = xWindowAttributes1.get_x();
/* 1620 */               insets.right = insets.left;
/* 1621 */               insets.bottom = insets.left; break;
/*      */             } 
/* 1623 */             return null;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 4:
/*      */           case 9:
/* 1630 */             syncTopLevelPos(paramLong1, xWindowAttributes1);
/* 1631 */             insets.top = xWindowAttributes1.get_y();
/* 1632 */             insets.left = xWindowAttributes1.get_x();
/* 1633 */             insets.right = insets.left;
/* 1634 */             insets.bottom = insets.left;
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/* 1639 */             if (insLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1640 */               insLog.finest("Getting correct insets for OTHER_WM/default, parent: {0}", new Object[] { Long.valueOf(paramLong2) });
/*      */             }
/* 1642 */             syncTopLevelPos(paramLong2, xWindowAttributes1);
/* 1643 */             i = XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), paramLong1, xWindowAttributes1.pData);
/*      */             
/* 1645 */             i = XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), paramLong2, xWindowAttributes2.pData);
/*      */             
/* 1647 */             if (xWindowAttributes1.get_root() == paramLong2) {
/* 1648 */               insLog.finest("our parent is root so insets should be zero");
/* 1649 */               insets = new Insets(0, 0, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1664 */             if (xWindowAttributes1.get_x() == 0 && xWindowAttributes1.get_y() == 0 && xWindowAttributes1
/* 1665 */               .get_width() + 2 * xWindowAttributes1.get_border_width() == xWindowAttributes2.get_width() && xWindowAttributes1
/* 1666 */               .get_height() + 2 * xWindowAttributes1.get_border_width() == xWindowAttributes2.get_height()) {
/*      */               
/* 1668 */               if (insLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1669 */                 insLog.finest("Double reparenting detected, pattr({2})={0}, lwinAttr({3})={1}", new Object[] { xWindowAttributes1, xWindowAttributes2, 
/* 1670 */                       Long.valueOf(paramLong2), Long.valueOf(paramLong1) });
/*      */               }
/* 1672 */               xWindowAttributes1.set_x(xWindowAttributes2.get_x());
/* 1673 */               xWindowAttributes1.set_y(xWindowAttributes2.get_y());
/* 1674 */               xWindowAttributes1.set_border_width(xWindowAttributes1.get_border_width() + xWindowAttributes2.get_border_width());
/*      */               
/* 1676 */               long l = XlibUtil.getParentWindow(paramLong2);
/*      */               
/* 1678 */               if (l == xWindowAttributes1.get_root())
/*      */               {
/*      */ 
/*      */ 
/*      */                 
/* 1683 */                 return null;
/*      */               }
/* 1685 */               paramLong2 = l;
/* 1686 */               XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), paramLong2, xWindowAttributes2.pData);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1698 */             if (insLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1699 */               insLog.finest("Attrs before calculation: pattr({2})={0}, lwinAttr({3})={1}", new Object[] { xWindowAttributes1, xWindowAttributes2, 
/* 1700 */                     Long.valueOf(paramLong2), Long.valueOf(paramLong1) });
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1705 */             insets = new Insets(xWindowAttributes1.get_y() + xWindowAttributes1.get_border_width(), xWindowAttributes1.get_x() + xWindowAttributes1.get_border_width(), xWindowAttributes2.get_height() - xWindowAttributes1.get_y() + xWindowAttributes1.get_height() + 2 * xWindowAttributes1.get_border_width(), xWindowAttributes2.get_width() - xWindowAttributes1.get_x() + xWindowAttributes1.get_width() + 2 * xWindowAttributes1.get_border_width());
/*      */             break;
/*      */         } 
/*      */       
/*      */       } finally {
/* 1710 */         xWindowAttributes1.dispose();
/* 1711 */         xWindowAttributes2.dispose();
/*      */       } 
/*      */     } 
/* 1714 */     if (this.storedInsets.get(paramXDecoratedPeer.getClass()) == null) {
/* 1715 */       this.storedInsets.put(paramXDecoratedPeer.getClass(), insets);
/*      */     }
/* 1717 */     return insets;
/*      */   }
/*      */   boolean isDesktopWindow(long paramLong) {
/* 1720 */     if (g_net_protocol != null) {
/* 1721 */       XAtomList xAtomList = XAtom.get("_NET_WM_WINDOW_TYPE").getAtomListPropertyList(paramLong);
/* 1722 */       return xAtomList.contains(XAtom.get("_NET_WM_WINDOW_TYPE_DESKTOP"));
/*      */     } 
/* 1724 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public XNETProtocol getNETProtocol() {
/* 1729 */     return g_net_protocol;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setNetWMIcon(XWindowPeer paramXWindowPeer, List<IconInfo> paramList) {
/* 1742 */     if (g_net_protocol != null && g_net_protocol.active()) {
/* 1743 */       g_net_protocol.setWMIcons(paramXWindowPeer, paramList);
/* 1744 */       return (getWMID() != 10);
/*      */     } 
/* 1746 */     return false;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XWM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */