/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XAtom
/*     */ {
/*  66 */   private static Unsafe unsafe = XlibWrapper.unsafe;
/*  67 */   private static XAtom[] emptyList = new XAtom[0];
/*     */   
/*     */   public static final long XA_PRIMARY = 1L;
/*     */   public static final long XA_SECONDARY = 2L;
/*     */   public static final long XA_ARC = 3L;
/*     */   public static final long XA_ATOM = 4L;
/*     */   public static final long XA_BITMAP = 5L;
/*     */   public static final long XA_CARDINAL = 6L;
/*     */   public static final long XA_COLORMAP = 7L;
/*     */   public static final long XA_CURSOR = 8L;
/*     */   public static final long XA_CUT_BUFFER0 = 9L;
/*     */   public static final long XA_CUT_BUFFER1 = 10L;
/*     */   public static final long XA_CUT_BUFFER2 = 11L;
/*     */   public static final long XA_CUT_BUFFER3 = 12L;
/*     */   public static final long XA_CUT_BUFFER4 = 13L;
/*     */   public static final long XA_CUT_BUFFER5 = 14L;
/*     */   public static final long XA_CUT_BUFFER6 = 15L;
/*     */   public static final long XA_CUT_BUFFER7 = 16L;
/*     */   public static final long XA_DRAWABLE = 17L;
/*     */   public static final long XA_FONT = 18L;
/*     */   public static final long XA_INTEGER = 19L;
/*     */   public static final long XA_PIXMAP = 20L;
/*     */   public static final long XA_POINT = 21L;
/*     */   public static final long XA_RECTANGLE = 22L;
/*     */   public static final long XA_RESOURCE_MANAGER = 23L;
/*     */   public static final long XA_RGB_COLOR_MAP = 24L;
/*     */   public static final long XA_RGB_BEST_MAP = 25L;
/*     */   public static final long XA_RGB_BLUE_MAP = 26L;
/*     */   public static final long XA_RGB_DEFAULT_MAP = 27L;
/*     */   public static final long XA_RGB_GRAY_MAP = 28L;
/*     */   public static final long XA_RGB_GREEN_MAP = 29L;
/*     */   public static final long XA_RGB_RED_MAP = 30L;
/*     */   public static final long XA_STRING = 31L;
/*     */   public static final long XA_VISUALID = 32L;
/*     */   public static final long XA_WINDOW = 33L;
/*     */   public static final long XA_WM_COMMAND = 34L;
/*     */   public static final long XA_WM_HINTS = 35L;
/*     */   public static final long XA_WM_CLIENT_MACHINE = 36L;
/*     */   public static final long XA_WM_ICON_NAME = 37L;
/*     */   public static final long XA_WM_ICON_SIZE = 38L;
/*     */   public static final long XA_WM_NAME = 39L;
/*     */   public static final long XA_WM_NORMAL_HINTS = 40L;
/*     */   public static final long XA_WM_SIZE_HINTS = 41L;
/*     */   public static final long XA_WM_ZOOM_HINTS = 42L;
/*     */   public static final long XA_MIN_SPACE = 43L;
/*     */   public static final long XA_NORM_SPACE = 44L;
/*     */   public static final long XA_MAX_SPACE = 45L;
/*     */   public static final long XA_END_SPACE = 46L;
/*     */   public static final long XA_SUPERSCRIPT_X = 47L;
/*     */   public static final long XA_SUPERSCRIPT_Y = 48L;
/*     */   public static final long XA_SUBSCRIPT_X = 49L;
/*     */   public static final long XA_SUBSCRIPT_Y = 50L;
/*     */   public static final long XA_UNDERLINE_POSITION = 51L;
/*     */   public static final long XA_UNDERLINE_THICKNESS = 52L;
/*     */   public static final long XA_STRIKEOUT_ASCENT = 53L;
/*     */   public static final long XA_STRIKEOUT_DESCENT = 54L;
/*     */   public static final long XA_ITALIC_ANGLE = 55L;
/*     */   public static final long XA_X_HEIGHT = 56L;
/*     */   public static final long XA_QUAD_WIDTH = 57L;
/*     */   public static final long XA_WEIGHT = 58L;
/*     */   public static final long XA_POINT_SIZE = 59L;
/*     */   public static final long XA_RESOLUTION = 60L;
/*     */   public static final long XA_COPYRIGHT = 61L;
/*     */   public static final long XA_NOTICE = 62L;
/*     */   public static final long XA_FONT_NAME = 63L;
/*     */   public static final long XA_FAMILY_NAME = 64L;
/*     */   public static final long XA_FULL_NAME = 65L;
/*     */   public static final long XA_CAP_HEIGHT = 66L;
/*     */   public static final long XA_WM_CLASS = 67L;
/*     */   public static final long XA_WM_TRANSIENT_FOR = 68L;
/*     */   public static final long XA_LAST_PREDEFINED = 68L;
/* 138 */   static HashMap<Long, XAtom> atomToAtom = new HashMap<>();
/* 139 */   static HashMap<String, XAtom> nameToAtom = new HashMap<>();
/*     */   static void register(XAtom paramXAtom) {
/* 141 */     if (paramXAtom == null) {
/*     */       return;
/*     */     }
/* 144 */     synchronized (XAtom.class) {
/* 145 */       if (paramXAtom.atom != 0L) {
/* 146 */         atomToAtom.put(Long.valueOf(paramXAtom.atom), paramXAtom);
/*     */       }
/* 148 */       if (paramXAtom.name != null)
/* 149 */         nameToAtom.put(paramXAtom.name, paramXAtom); 
/*     */     } 
/*     */   }
/*     */   
/*     */   static XAtom lookup(long paramLong) {
/* 154 */     synchronized (XAtom.class) {
/* 155 */       return atomToAtom.get(Long.valueOf(paramLong));
/*     */     } 
/*     */   }
/*     */   static XAtom lookup(String paramString) {
/* 159 */     synchronized (XAtom.class) {
/* 160 */       return nameToAtom.get(paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static XAtom get(long paramLong) {
/* 169 */     XAtom xAtom = lookup(paramLong);
/* 170 */     if (xAtom == null) {
/* 171 */       xAtom = new XAtom(XToolkit.getDisplay(), paramLong);
/*     */     }
/* 173 */     return xAtom;
/*     */   }
/*     */   public static XAtom get(String paramString) {
/* 176 */     XAtom xAtom = lookup(paramString);
/* 177 */     if (xAtom == null) {
/* 178 */       xAtom = new XAtom(XToolkit.getDisplay(), paramString);
/*     */     }
/* 180 */     return xAtom;
/*     */   }
/*     */   public final String getName() {
/* 183 */     if (this.name == null) {
/* 184 */       XToolkit.awtLock();
/*     */       try {
/* 186 */         this.name = XlibWrapper.XGetAtomName(this.display, this.atom);
/*     */       } finally {
/* 188 */         XToolkit.awtUnlock();
/*     */       } 
/* 190 */       register();
/*     */     } 
/* 192 */     return this.name;
/*     */   }
/*     */   static String asString(long paramLong) {
/* 195 */     XAtom xAtom = lookup(paramLong);
/* 196 */     if (xAtom == null) {
/* 197 */       return Long.toString(paramLong);
/*     */     }
/* 199 */     return xAtom.toString();
/*     */   }
/*     */   
/*     */   void register() {
/* 203 */     register(this);
/*     */   }
/*     */   public String toString() {
/* 206 */     if (this.name != null) {
/* 207 */       return this.name + ":" + this.atom;
/*     */     }
/* 209 */     return Long.toString(this.atom);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 214 */   long atom = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long display;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XAtom(long paramLong, String paramString) {
/* 232 */     this(paramLong, paramString, true);
/*     */   }
/*     */   
/*     */   public XAtom(String paramString, boolean paramBoolean) {
/* 236 */     this(XToolkit.getDisplay(), paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XAtom(long paramLong1, long paramLong2) {
/* 247 */     this.atom = paramLong2;
/* 248 */     this.display = paramLong1;
/* 249 */     register();
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
/*     */   private XAtom(long paramLong, String paramString, boolean paramBoolean) {
/* 262 */     this.name = paramString;
/* 263 */     this.display = paramLong;
/* 264 */     if (paramBoolean) {
/* 265 */       XToolkit.awtLock();
/*     */       try {
/* 267 */         this.atom = XlibWrapper.InternAtom(paramLong, paramString, 0);
/*     */       } finally {
/* 269 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/* 272 */     register();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XAtom() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(long paramLong, String paramString) {
/* 287 */     if (this.atom == 0L) {
/* 288 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 290 */     checkWindow(paramLong);
/* 291 */     XToolkit.awtLock();
/*     */     try {
/* 293 */       XlibWrapper.SetProperty(this.display, paramLong, this.atom, paramString);
/*     */     } finally {
/* 295 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyUTF8(long paramLong, String paramString) {
/* 303 */     XAtom xAtom = get("UTF8_STRING");
/* 304 */     if (this.atom == 0L) {
/* 305 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 307 */     checkWindow(paramLong);
/* 308 */     byte[] arrayOfByte = null;
/*     */     try {
/* 310 */       arrayOfByte = paramString.getBytes("UTF-8");
/* 311 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 312 */       unsupportedEncodingException.printStackTrace();
/*     */     } 
/* 314 */     if (arrayOfByte != null) {
/* 315 */       setAtomData(paramLong, xAtom.atom, arrayOfByte);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty8(long paramLong, String paramString) {
/* 323 */     if (this.atom == 0L) {
/* 324 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 326 */     checkWindow(paramLong);
/* 327 */     byte[] arrayOfByte = null;
/*     */     try {
/* 329 */       arrayOfByte = paramString.getBytes("ISO-8859-1");
/* 330 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 331 */       unsupportedEncodingException.printStackTrace();
/*     */     } 
/* 333 */     if (arrayOfByte != null) {
/* 334 */       setAtomData(paramLong, 31L, arrayOfByte);
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
/*     */   public String getProperty(long paramLong) {
/* 346 */     if (this.atom == 0L) {
/* 347 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 349 */     checkWindow(paramLong);
/* 350 */     XToolkit.awtLock();
/*     */     try {
/* 352 */       return XlibWrapper.GetProperty(this.display, paramLong, this.atom);
/*     */     } finally {
/* 354 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long get32Property(long paramLong1, long paramLong2) {
/* 364 */     if (this.atom == 0L) {
/* 365 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 367 */     checkWindow(paramLong1);
/* 368 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong1, this, 0L, 1L, false, paramLong2);
/*     */ 
/*     */     
/*     */     try {
/* 372 */       int i = windowPropertyGetter.execute();
/* 373 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/* 374 */         return 0L;
/*     */       }
/* 376 */       if (windowPropertyGetter.getActualType() != paramLong2 || windowPropertyGetter.getActualFormat() != 32) {
/* 377 */         return 0L;
/*     */       }
/* 379 */       return Native.getCard32(windowPropertyGetter.getData());
/*     */     } finally {
/* 381 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCard32Property(XBaseWindow paramXBaseWindow) {
/* 389 */     return get32Property(paramXBaseWindow.getWindow(), 6L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCard32Property(long paramLong1, long paramLong2) {
/* 396 */     if (this.atom == 0L) {
/* 397 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 399 */     checkWindow(paramLong1);
/* 400 */     XToolkit.awtLock();
/*     */     try {
/* 402 */       Native.putCard32(XlibWrapper.larg1, paramLong2);
/* 403 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong1, this.atom, 6L, 32, 0, XlibWrapper.larg1, 1);
/*     */     }
/*     */     finally {
/*     */       
/* 407 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCard32Property(XBaseWindow paramXBaseWindow, long paramLong) {
/* 415 */     setCard32Property(paramXBaseWindow.getWindow(), paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAtomData(long paramLong1, long paramLong2, int paramInt) {
/* 426 */     if (this.atom == 0L) {
/* 427 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 429 */     checkWindow(paramLong1);
/* 430 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong1, this, 0L, paramInt, false, this);
/*     */ 
/*     */     
/*     */     try {
/* 434 */       int i = windowPropertyGetter.execute();
/* 435 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/* 436 */         return false;
/*     */       }
/* 438 */       if (windowPropertyGetter.getActualType() != this.atom || windowPropertyGetter
/* 439 */         .getActualFormat() != 32 || windowPropertyGetter
/* 440 */         .getNumberOfItems() != paramInt)
/*     */       {
/*     */         
/* 443 */         return false;
/*     */       }
/* 445 */       XlibWrapper.memcpy(paramLong2, windowPropertyGetter.getData(), (paramInt * getAtomSize()));
/* 446 */       return true;
/*     */     } finally {
/* 448 */       windowPropertyGetter.dispose();
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
/*     */   public boolean getAtomData(long paramLong1, long paramLong2, long paramLong3, int paramInt) {
/* 460 */     if (this.atom == 0L) {
/* 461 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 463 */     checkWindow(paramLong1);
/* 464 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong1, this, 0L, paramInt, false, paramLong2);
/*     */ 
/*     */     
/*     */     try {
/* 468 */       int i = windowPropertyGetter.execute();
/* 469 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/* 470 */         return false;
/*     */       }
/* 472 */       if (windowPropertyGetter.getActualType() != paramLong2 || windowPropertyGetter
/* 473 */         .getActualFormat() != 32 || windowPropertyGetter
/* 474 */         .getNumberOfItems() != paramInt)
/*     */       {
/*     */         
/* 477 */         return false;
/*     */       }
/* 479 */       XlibWrapper.memcpy(paramLong3, windowPropertyGetter.getData(), (paramInt * getAtomSize()));
/* 480 */       return true;
/*     */     } finally {
/* 482 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAtomData(long paramLong1, long paramLong2, int paramInt) {
/* 493 */     if (this.atom == 0L) {
/* 494 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 496 */     checkWindow(paramLong1);
/* 497 */     XToolkit.awtLock();
/*     */     try {
/* 499 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong1, this.atom, this.atom, 32, 0, paramLong2, paramInt);
/*     */     }
/*     */     finally {
/*     */       
/* 503 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAtomData(long paramLong1, long paramLong2, long paramLong3, int paramInt) {
/* 514 */     if (this.atom == 0L) {
/* 515 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 517 */     checkWindow(paramLong1);
/* 518 */     XToolkit.awtLock();
/*     */     try {
/* 520 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong1, this.atom, paramLong2, 32, 0, paramLong3, paramInt);
/*     */     }
/*     */     finally {
/*     */       
/* 524 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAtomData8(long paramLong1, long paramLong2, long paramLong3, int paramInt) {
/* 535 */     if (this.atom == 0L) {
/* 536 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 538 */     checkWindow(paramLong1);
/* 539 */     XToolkit.awtLock();
/*     */     try {
/* 541 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong1, this.atom, paramLong2, 8, 0, paramLong3, paramInt);
/*     */     }
/*     */     finally {
/*     */       
/* 545 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void DeleteProperty(long paramLong) {
/* 553 */     if (this.atom == 0L) {
/* 554 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 556 */     checkWindow(paramLong);
/* 557 */     XToolkit.awtLock();
/*     */     try {
/* 559 */       XlibWrapper.XDeleteProperty(XToolkit.getDisplay(), paramLong, this.atom);
/*     */     } finally {
/* 561 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void DeleteProperty(XBaseWindow paramXBaseWindow) {
/* 569 */     if (this.atom == 0L) {
/* 570 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 572 */     checkWindow(paramXBaseWindow.getWindow());
/* 573 */     XToolkit.awtLock();
/*     */     try {
/* 575 */       XlibWrapper.XDeleteProperty(XToolkit.getDisplay(), paramXBaseWindow
/* 576 */           .getWindow(), this.atom);
/*     */     } finally {
/* 578 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAtomData(long paramLong1, long paramLong2, byte[] paramArrayOfbyte) {
/* 583 */     long l = Native.toData(paramArrayOfbyte);
/*     */     try {
/* 585 */       setAtomData8(paramLong1, paramLong2, l, paramArrayOfbyte.length);
/*     */     } finally {
/* 587 */       unsafe.freeMemory(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteArrayProperty(long paramLong1, long paramLong2) {
/* 596 */     if (this.atom == 0L) {
/* 597 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 599 */     checkWindow(paramLong1);
/* 600 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong1, this, 0L, 65535L, false, paramLong2);
/*     */ 
/*     */     
/*     */     try {
/* 604 */       int i = windowPropertyGetter.execute();
/* 605 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/* 606 */         return null;
/*     */       }
/* 608 */       if (windowPropertyGetter.getActualType() != paramLong2 || windowPropertyGetter.getActualFormat() != 8) {
/* 609 */         return null;
/*     */       }
/* 611 */       byte[] arrayOfByte = XlibWrapper.getStringBytes(windowPropertyGetter.getData());
/* 612 */       return arrayOfByte;
/*     */     } finally {
/* 614 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void intern(boolean paramBoolean) {
/* 622 */     XToolkit.awtLock();
/*     */     try {
/* 624 */       this.atom = XlibWrapper.InternAtom(this.display, this.name, paramBoolean ? 1 : 0);
/*     */     } finally {
/* 626 */       XToolkit.awtUnlock();
/*     */     } 
/* 628 */     register();
/*     */   }
/*     */   
/*     */   public boolean isInterned() {
/* 632 */     if (this.atom == 0L) {
/* 633 */       XToolkit.awtLock();
/*     */       try {
/* 635 */         this.atom = XlibWrapper.InternAtom(this.display, this.name, 1);
/*     */       } finally {
/* 637 */         XToolkit.awtUnlock();
/*     */       } 
/* 639 */       if (this.atom == 0L) {
/* 640 */         return false;
/*     */       }
/* 642 */       register();
/* 643 */       return true;
/*     */     } 
/*     */     
/* 646 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValues(long paramLong1, String paramString, long paramLong2) {
/* 651 */     this.display = paramLong1;
/* 652 */     this.atom = paramLong2;
/* 653 */     this.name = paramString;
/* 654 */     register();
/*     */   }
/*     */   
/*     */   static int getAtomSize() {
/* 658 */     return Native.getLongSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XAtom[] getAtomListProperty(long paramLong) {
/* 667 */     if (this.atom == 0L) {
/* 668 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 670 */     checkWindow(paramLong);
/*     */     
/* 672 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, this, 0L, 65535L, false, 4L);
/*     */ 
/*     */     
/*     */     try {
/* 676 */       int i = windowPropertyGetter.execute();
/* 677 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/* 678 */         return emptyList;
/*     */       }
/* 680 */       if (windowPropertyGetter.getActualType() != 4L || windowPropertyGetter.getActualFormat() != 32) {
/* 681 */         return emptyList;
/*     */       }
/*     */       
/* 684 */       int j = windowPropertyGetter.getNumberOfItems();
/* 685 */       if (j == 0) {
/* 686 */         return emptyList;
/*     */       }
/* 688 */       long l = windowPropertyGetter.getData();
/* 689 */       XAtom[] arrayOfXAtom = new XAtom[j];
/* 690 */       for (byte b = 0; b < j; b++) {
/* 691 */         arrayOfXAtom[b] = get(getAtom(l + (b * getAtomSize())));
/*     */       }
/* 693 */       return arrayOfXAtom;
/*     */     } finally {
/* 695 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XAtomList getAtomListPropertyList(long paramLong) {
/* 705 */     return new XAtomList(getAtomListProperty(paramLong));
/*     */   }
/*     */   XAtomList getAtomListPropertyList(XBaseWindow paramXBaseWindow) {
/* 708 */     return getAtomListPropertyList(paramXBaseWindow.getWindow());
/*     */   }
/*     */   XAtom[] getAtomListProperty(XBaseWindow paramXBaseWindow) {
/* 711 */     return getAtomListProperty(paramXBaseWindow.getWindow());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setAtomListProperty(long paramLong, XAtom[] paramArrayOfXAtom) {
/* 718 */     long l = toData(paramArrayOfXAtom);
/* 719 */     setAtomData(paramLong, 4L, l, paramArrayOfXAtom.length);
/* 720 */     unsafe.freeMemory(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setAtomListProperty(long paramLong, XAtomList paramXAtomList) {
/* 727 */     long l = paramXAtomList.getAtomsData();
/* 728 */     setAtomData(paramLong, 4L, l, paramXAtomList.size());
/* 729 */     unsafe.freeMemory(l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAtomListProperty(XBaseWindow paramXBaseWindow, XAtom[] paramArrayOfXAtom) {
/* 735 */     setAtomListProperty(paramXBaseWindow.getWindow(), paramArrayOfXAtom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAtomListProperty(XBaseWindow paramXBaseWindow, XAtomList paramXAtomList) {
/* 742 */     setAtomListProperty(paramXBaseWindow.getWindow(), paramXAtomList);
/*     */   }
/*     */   
/*     */   long getAtom() {
/* 746 */     return this.atom;
/*     */   }
/*     */   
/*     */   void putAtom(long paramLong) {
/* 750 */     Native.putLong(paramLong, this.atom);
/*     */   }
/*     */   
/*     */   static long getAtom(long paramLong) {
/* 754 */     return Native.getLong(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long toData(XAtom[] paramArrayOfXAtom) {
/* 761 */     long l = unsafe.allocateMemory((getAtomSize() * paramArrayOfXAtom.length));
/* 762 */     for (byte b = 0; b < paramArrayOfXAtom.length; b++) {
/* 763 */       if (paramArrayOfXAtom[b] != null) {
/* 764 */         paramArrayOfXAtom[b].putAtom(l + (b * getAtomSize()));
/*     */       }
/*     */     } 
/* 767 */     return l;
/*     */   }
/*     */   
/*     */   void checkWindow(long paramLong) {
/* 771 */     if (paramLong == 0L) {
/* 772 */       throw new IllegalArgumentException("Window must not be zero");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 777 */     if (!(paramObject instanceof XAtom)) {
/* 778 */       return false;
/*     */     }
/* 780 */     XAtom xAtom = (XAtom)paramObject;
/* 781 */     return (this.atom == xAtom.atom && this.display == xAtom.display);
/*     */   }
/*     */   public int hashCode() {
/* 784 */     return (int)((this.atom ^ this.display) & 0xFFFFL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWindowProperty(long paramLong1, long paramLong2) {
/* 792 */     if (this.atom == 0L) {
/* 793 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 795 */     checkWindow(paramLong1);
/* 796 */     XToolkit.awtLock();
/*     */     try {
/* 798 */       Native.putWindow(XlibWrapper.larg1, paramLong2);
/* 799 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong1, this.atom, 33L, 32, 0, XlibWrapper.larg1, 1);
/*     */     }
/*     */     finally {
/*     */       
/* 803 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   public void setWindowProperty(XBaseWindow paramXBaseWindow1, XBaseWindow paramXBaseWindow2) {
/* 807 */     setWindowProperty(paramXBaseWindow1.getWindow(), paramXBaseWindow2.getWindow());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWindowProperty(long paramLong) {
/* 815 */     if (this.atom == 0L) {
/* 816 */       throw new IllegalStateException("Atom should be initialized");
/*     */     }
/* 818 */     checkWindow(paramLong);
/* 819 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, this, 0L, 1L, false, 33L);
/*     */ 
/*     */     
/*     */     try {
/* 823 */       int i = windowPropertyGetter.execute();
/* 824 */       if (i != 0 || windowPropertyGetter.getData() == 0L) {
/* 825 */         return 0L;
/*     */       }
/* 827 */       if (windowPropertyGetter.getActualType() != 33L || windowPropertyGetter.getActualFormat() != 32) {
/* 828 */         return 0L;
/*     */       }
/* 830 */       return Native.getWindow(windowPropertyGetter.getData());
/*     */     } finally {
/* 832 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XAtom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */