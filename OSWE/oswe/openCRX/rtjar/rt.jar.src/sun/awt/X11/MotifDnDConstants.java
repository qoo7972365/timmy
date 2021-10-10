/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Arrays;
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
/*     */ class MotifDnDConstants
/*     */ {
/*  46 */   private static final Unsafe unsafe = XlibWrapper.unsafe;
/*  47 */   static final XAtom XA_MOTIF_ATOM_0 = XAtom.get("_MOTIF_ATOM_0");
/*  48 */   static final XAtom XA_MOTIF_DRAG_WINDOW = XAtom.get("_MOTIF_DRAG_WINDOW");
/*  49 */   static final XAtom XA_MOTIF_DRAG_TARGETS = XAtom.get("_MOTIF_DRAG_TARGETS");
/*     */   
/*  51 */   static final XAtom XA_MOTIF_DRAG_INITIATOR_INFO = XAtom.get("_MOTIF_DRAG_INITIATOR_INFO");
/*     */   
/*  53 */   static final XAtom XA_MOTIF_DRAG_RECEIVER_INFO = XAtom.get("_MOTIF_DRAG_RECEIVER_INFO");
/*     */   
/*  55 */   static final XAtom XA_MOTIF_DRAG_AND_DROP_MESSAGE = XAtom.get("_MOTIF_DRAG_AND_DROP_MESSAGE");
/*     */   
/*  57 */   static final XAtom XA_XmTRANSFER_SUCCESS = XAtom.get("XmTRANSFER_SUCCESS");
/*     */   
/*  59 */   static final XAtom XA_XmTRANSFER_FAILURE = XAtom.get("XmTRANSFER_FAILURE");
/*  60 */   static final XSelection MotifDnDSelection = new XSelection(XA_MOTIF_ATOM_0);
/*     */   
/*     */   public static final byte MOTIF_DND_PROTOCOL_VERSION = 0;
/*     */   
/*     */   public static final int MOTIF_PREFER_PREREGISTER_STYLE = 2;
/*     */   
/*     */   public static final int MOTIF_PREFER_DYNAMIC_STYLE = 4;
/*     */   
/*     */   public static final int MOTIF_DYNAMIC_STYLE = 5;
/*     */   
/*     */   public static final int MOTIF_PREFER_RECEIVER_STYLE = 6;
/*     */   
/*     */   public static final int MOTIF_INITIATOR_INFO_SIZE = 8;
/*     */   
/*     */   public static final int MOTIF_RECEIVER_INFO_SIZE = 16;
/*     */   
/*     */   public static final byte MOTIF_MESSAGE_REASON_MASK = 127;
/*     */   
/*     */   public static final byte MOTIF_MESSAGE_SENDER_MASK = -128;
/*     */   
/*     */   public static final byte MOTIF_MESSAGE_FROM_RECEIVER = -128;
/*     */   
/*     */   public static final byte MOTIF_MESSAGE_FROM_INITIATOR = 0;
/*     */   
/*     */   public static final int MOTIF_DND_ACTION_MASK = 15;
/*     */   
/*     */   public static final int MOTIF_DND_ACTION_SHIFT = 0;
/*     */   
/*     */   public static final int MOTIF_DND_STATUS_MASK = 240;
/*     */   
/*     */   public static final int MOTIF_DND_STATUS_SHIFT = 4;
/*     */   public static final int MOTIF_DND_ACTIONS_MASK = 3840;
/*     */   public static final int MOTIF_DND_ACTIONS_SHIFT = 8;
/*     */   public static final byte TOP_LEVEL_ENTER = 0;
/*     */   public static final byte TOP_LEVEL_LEAVE = 1;
/*     */   public static final byte DRAG_MOTION = 2;
/*     */   public static final byte DROP_SITE_ENTER = 3;
/*     */   public static final byte DROP_SITE_LEAVE = 4;
/*     */   public static final byte DROP_START = 5;
/*     */   public static final byte DROP_FINISH = 6;
/*     */   public static final byte DRAG_DROP_FINISH = 7;
/*     */   public static final byte OPERATION_CHANGED = 8;
/*     */   public static final int MOTIF_DND_NOOP = 0;
/*     */   public static final int MOTIF_DND_MOVE = 1;
/*     */   public static final int MOTIF_DND_COPY = 2;
/*     */   public static final int MOTIF_DND_LINK = 4;
/*     */   public static final byte MOTIF_NO_DROP_SITE = 1;
/*     */   public static final byte MOTIF_INVALID_DROP_SITE = 2;
/*     */   public static final byte MOTIF_VALID_DROP_SITE = 3;
/*     */   
/*     */   private static long readMotifWindow() throws XException {
/* 111 */     long l1 = XlibWrapper.DefaultScreen(XToolkit.getDisplay());
/*     */     
/* 113 */     long l2 = XlibWrapper.RootWindow(XToolkit.getDisplay(), l1);
/*     */     
/* 115 */     long l3 = 0L;
/*     */     
/* 117 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(l2, XA_MOTIF_DRAG_WINDOW, 0L, 1L, false, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 123 */       int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*     */       
/* 125 */       if (i == 0 && windowPropertyGetter
/* 126 */         .getData() != 0L && windowPropertyGetter
/* 127 */         .getActualType() == 33L && windowPropertyGetter
/* 128 */         .getActualFormat() == 32 && windowPropertyGetter
/* 129 */         .getNumberOfItems() == 1) {
/* 130 */         long l = windowPropertyGetter.getData();
/*     */         
/* 132 */         l3 = Native.getLong(l);
/*     */       } 
/*     */       
/* 135 */       return l3;
/*     */     } finally {
/* 137 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static long createMotifWindow() throws XException {
/* 142 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */ 
/*     */     
/* 145 */     long l1 = XlibWrapper.DefaultScreen(XToolkit.getDisplay());
/*     */     
/* 147 */     long l2 = XlibWrapper.RootWindow(XToolkit.getDisplay(), l1);
/*     */     
/* 149 */     long l3 = 0L;
/*     */     
/* 151 */     long l4 = XlibWrapper.XDisplayString(XToolkit.getDisplay());
/*     */     
/* 153 */     if (l4 == 0L) {
/* 154 */       throw new XException("XDisplayString returns NULL");
/*     */     }
/*     */     
/* 157 */     long l5 = XlibWrapper.XOpenDisplay(l4);
/*     */     
/* 159 */     if (l5 == 0L) {
/* 160 */       throw new XException("XOpenDisplay returns NULL");
/*     */     }
/*     */     
/* 163 */     XlibWrapper.XGrabServer(l5);
/*     */     
/*     */     try {
/* 166 */       XlibWrapper.XSetCloseDownMode(l5, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 216 */       XlibWrapper.XUngrabServer(l5);
/* 217 */       XlibWrapper.XCloseDisplay(l5);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long getMotifWindow() throws XException {
/* 227 */     long l = readMotifWindow();
/* 228 */     if (l == 0L) {
/* 229 */       l = createMotifWindow();
/*     */     }
/* 231 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Swapper
/*     */   {
/*     */     public static short swap(short param1Short) {
/* 239 */       return (short)((param1Short & 0xFF00) >>> 8 | (param1Short & 0xFF) << 8);
/*     */     }
/*     */     public static int swap(int param1Int) {
/* 242 */       return (param1Int & 0xFF000000) >>> 24 | (param1Int & 0xFF0000) >>> 8 | (param1Int & 0xFF00) << 8 | (param1Int & 0xFF) << 24;
/*     */     }
/*     */ 
/*     */     
/*     */     public static short getShort(long param1Long, byte param1Byte) {
/* 247 */       short s = MotifDnDConstants.unsafe.getShort(param1Long);
/* 248 */       if (param1Byte != MotifDnDConstants.getByteOrderByte()) {
/* 249 */         return swap(s);
/*     */       }
/* 251 */       return s;
/*     */     }
/*     */     
/*     */     public static int getInt(long param1Long, byte param1Byte) {
/* 255 */       int i = MotifDnDConstants.unsafe.getInt(param1Long);
/* 256 */       if (param1Byte != MotifDnDConstants.getByteOrderByte()) {
/* 257 */         return swap(i);
/*     */       }
/* 259 */       return i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long[][] getTargetListTable(long paramLong) throws XException {
/* 281 */     WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, XA_MOTIF_DRAG_TARGETS, 0L, 100000L, false, XA_MOTIF_DRAG_TARGETS.getAtom());
/*     */     try {
/* 283 */       int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*     */       
/* 285 */       if (i != 0 || windowPropertyGetter
/* 286 */         .getActualType() != XA_MOTIF_DRAG_TARGETS.getAtom() || windowPropertyGetter
/* 287 */         .getData() == 0L)
/*     */       {
/* 289 */         return (long[][])null;
/*     */       }
/*     */       
/* 292 */       long l1 = windowPropertyGetter.getData();
/*     */       
/* 294 */       if (unsafe.getByte(l1 + 1L) != 0) {
/* 295 */         return (long[][])null;
/*     */       }
/*     */       
/* 298 */       boolean bool = (unsafe.getByte(l1 + 0L) != getByteOrderByte()) ? true : false;
/*     */       
/* 300 */       short s = unsafe.getShort(l1 + 2L);
/*     */       
/* 302 */       if (bool) {
/* 303 */         s = Swapper.swap(s);
/*     */       }
/*     */       
/* 306 */       long[][] arrayOfLong = new long[s][];
/* 307 */       ByteOrder byteOrder = ByteOrder.nativeOrder();
/* 308 */       if (bool) {
/* 309 */         byteOrder = (byteOrder == ByteOrder.LITTLE_ENDIAN) ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
/*     */       }
/*     */ 
/*     */       
/* 313 */       long l2 = l1 + 8L; short s1;
/* 314 */       for (s1 = 0; s1 < s; s1 = (short)(s1 + 1)) {
/* 315 */         short s2 = unsafe.getShort(l2);
/* 316 */         l2 += 2L;
/* 317 */         if (bool) {
/* 318 */           s2 = Swapper.swap(s2);
/*     */         }
/*     */         
/* 321 */         arrayOfLong[s1] = new long[s2];
/*     */         short s3;
/* 323 */         for (s3 = 0; s3 < s2; s3 = (short)(s3 + 1)) {
/*     */ 
/*     */           
/* 326 */           int j = 0;
/* 327 */           if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
/* 328 */             for (byte b = 0; b < 4; b++) {
/* 329 */               j |= unsafe.getByte(l2 + b) << 8 * b & 255 << 8 * b;
/*     */             }
/*     */           } else {
/*     */             
/* 333 */             for (byte b = 0; b < 4; b++) {
/* 334 */               j |= unsafe.getByte(l2 + b) << 8 * (3 - b) & 255 << 8 * (3 - b);
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 340 */           arrayOfLong[s1][s3] = j;
/* 341 */           l2 += 4L;
/*     */         } 
/*     */       } 
/* 344 */       return arrayOfLong;
/*     */     } finally {
/* 346 */       windowPropertyGetter.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void putTargetListTable(long paramLong, long[][] paramArrayOflong) throws XException {
/* 352 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 354 */     int i = 8;
/*     */     
/* 356 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 357 */       i += (paramArrayOflong[b]).length * 4 + 2;
/*     */     }
/*     */     
/* 360 */     long l = unsafe.allocateMemory(i);
/*     */ 
/*     */     
/*     */     try {
/* 364 */       unsafe.putByte(l + 0L, getByteOrderByte());
/*     */       
/* 366 */       unsafe.putByte(l + 1L, (byte)0);
/*     */       
/* 368 */       unsafe.putShort(l + 2L, (short)paramArrayOflong.length);
/*     */       
/* 370 */       unsafe.putInt(l + 4L, i);
/*     */       
/* 372 */       long l1 = l + 8L;
/*     */       
/* 374 */       for (byte b1 = 0; b1 < paramArrayOflong.length; b1++) {
/* 375 */         unsafe.putShort(l1, (short)(paramArrayOflong[b1]).length);
/* 376 */         l1 += 2L;
/*     */         
/* 378 */         for (byte b2 = 0; b2 < (paramArrayOflong[b1]).length; b2++) {
/* 379 */           int j = (int)paramArrayOflong[b1][b2];
/*     */ 
/*     */           
/* 382 */           if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/* 383 */             for (byte b3 = 0; b3 < 4; b3++) {
/* 384 */               byte b4 = (byte)((j & 255 << 8 * b3) >> 8 * b3);
/* 385 */               unsafe.putByte(l1 + b3, b4);
/*     */             } 
/*     */           } else {
/* 388 */             for (byte b3 = 0; b3 < 4; b3++) {
/* 389 */               byte b4 = (byte)((j & 255 << 8 * b3) >> 8 * b3);
/* 390 */               unsafe.putByte(l1 + (3 - b3), b4);
/*     */             } 
/*     */           } 
/* 393 */           l1 += 4L;
/*     */         } 
/*     */       } 
/*     */       
/* 397 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/* 398 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong, XA_MOTIF_DRAG_TARGETS
/*     */           
/* 400 */           .getAtom(), XA_MOTIF_DRAG_TARGETS
/* 401 */           .getAtom(), 8, 0, l, i);
/*     */ 
/*     */ 
/*     */       
/* 405 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       
/* 407 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 408 */         .get_error_code() != 0) {
/*     */ 
/*     */         
/* 411 */         paramLong = createMotifWindow();
/*     */         
/* 413 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/* 414 */         XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong, XA_MOTIF_DRAG_TARGETS
/*     */             
/* 416 */             .getAtom(), XA_MOTIF_DRAG_TARGETS
/* 417 */             .getAtom(), 8, 0, l, i);
/*     */ 
/*     */ 
/*     */         
/* 421 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */         
/* 423 */         if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 424 */           .get_error_code() != 0) {
/* 425 */           throw new XException("Cannot write motif drag targets property.");
/*     */         }
/*     */       } 
/*     */     } finally {
/* 429 */       unsafe.freeMemory(l);
/*     */     } 
/*     */   }
/*     */   
/*     */   static int getIndexForTargetList(long[] paramArrayOflong) throws XException {
/* 434 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 436 */     if (paramArrayOflong.length > 0) {
/*     */       
/* 438 */       paramArrayOflong = (long[])paramArrayOflong.clone();
/*     */       
/* 440 */       Arrays.sort(paramArrayOflong);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 451 */     long l = getMotifWindow();
/*     */     
/* 453 */     XlibWrapper.XGrabServer(XToolkit.getDisplay());
/*     */     
/*     */     try {
/* 456 */       long[][] arrayOfLong1 = getTargetListTable(l);
/*     */       
/* 458 */       if (arrayOfLong1 != null) {
/* 459 */         for (byte b = 0; b < arrayOfLong1.length; b++) {
/* 460 */           boolean bool = true;
/* 461 */           if ((arrayOfLong1[b]).length == paramArrayOflong.length) {
/* 462 */             for (byte b1 = 0; b1 < (arrayOfLong1[b]).length; b1++) {
/* 463 */               if (arrayOfLong1[b][b1] != paramArrayOflong[b1]) {
/* 464 */                 bool = false;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } else {
/* 469 */             bool = false;
/*     */           } 
/*     */           
/* 472 */           if (bool) {
/* 473 */             XlibWrapper.XUngrabServer(XToolkit.getDisplay());
/* 474 */             return b;
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 481 */         arrayOfLong1 = new long[2][];
/* 482 */         (new long[1])[0] = 0L; arrayOfLong1[0] = new long[1];
/* 483 */         (new long[1])[0] = 31L; arrayOfLong1[1] = new long[1];
/*     */       } 
/*     */ 
/*     */       
/* 487 */       long[][] arrayOfLong2 = new long[arrayOfLong1.length + 1][];
/*     */       
/*     */       int i;
/* 490 */       for (i = 0; i < arrayOfLong1.length; i++) {
/* 491 */         arrayOfLong2[i] = arrayOfLong1[i];
/*     */       }
/*     */ 
/*     */       
/* 495 */       arrayOfLong2[arrayOfLong2.length - 1] = paramArrayOflong;
/*     */       
/* 497 */       putTargetListTable(l, arrayOfLong2);
/*     */       
/* 499 */       i = arrayOfLong2.length - 1; return i;
/*     */     } finally {
/* 501 */       XlibWrapper.XUngrabServer(XToolkit.getDisplay());
/*     */     } 
/*     */   }
/*     */   
/*     */   static long[] getTargetListForIndex(int paramInt) {
/* 506 */     long l = getMotifWindow();
/* 507 */     long[][] arrayOfLong = getTargetListTable(l);
/*     */     
/* 509 */     if (paramInt < 0 || paramInt >= arrayOfLong.length) {
/* 510 */       return new long[0];
/*     */     }
/* 512 */     return arrayOfLong[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static byte getByteOrderByte() {
/* 518 */     return (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? 108 : 66;
/*     */   }
/*     */ 
/*     */   
/*     */   static void writeDragInitiatorInfoStruct(long paramLong, int paramInt) throws XException {
/* 523 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 525 */     long l = unsafe.allocateMemory(8L);
/*     */ 
/*     */     
/*     */     try {
/* 529 */       unsafe.putByte(l, getByteOrderByte());
/*     */       
/* 531 */       unsafe.putByte(l + 1L, (byte)0);
/*     */       
/* 533 */       unsafe.putShort(l + 2L, (short)paramInt);
/*     */       
/* 535 */       unsafe.putInt(l + 4L, (int)XA_MOTIF_ATOM_0.getAtom());
/*     */       
/* 537 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/* 538 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong, XA_MOTIF_ATOM_0
/* 539 */           .getAtom(), XA_MOTIF_DRAG_INITIATOR_INFO
/* 540 */           .getAtom(), 8, 0, l, 8);
/*     */ 
/*     */       
/* 543 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       
/* 545 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 546 */         .get_error_code() != 0) {
/* 547 */         throw new XException("Cannot write drag initiator info");
/*     */       }
/*     */     } finally {
/* 550 */       unsafe.freeMemory(l);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void writeDragReceiverInfoStruct(long paramLong) throws XException {
/* 555 */     assert XToolkit.isAWTLockHeldByCurrentThread();
/*     */     
/* 557 */     byte b = 16;
/* 558 */     long l = unsafe.allocateMemory(b);
/*     */     
/*     */     try {
/* 561 */       unsafe.putByte(l, getByteOrderByte());
/* 562 */       unsafe.putByte(l + 1L, (byte)0);
/* 563 */       unsafe.putByte(l + 2L, (byte)5);
/* 564 */       unsafe.putByte(l + 3L, (byte)0);
/* 565 */       unsafe.putInt(l + 4L, (int)paramLong);
/* 566 */       unsafe.putShort(l + 8L, (short)0);
/* 567 */       unsafe.putShort(l + 10L, (short)0);
/* 568 */       unsafe.putInt(l + 12L, b);
/*     */       
/* 570 */       XErrorHandlerUtil.WITH_XERROR_HANDLER(XErrorHandler.VerifyChangePropertyHandler.getInstance());
/* 571 */       XlibWrapper.XChangeProperty(XToolkit.getDisplay(), paramLong, XA_MOTIF_DRAG_RECEIVER_INFO
/* 572 */           .getAtom(), XA_MOTIF_DRAG_RECEIVER_INFO
/* 573 */           .getAtom(), 8, 0, l, b);
/*     */ 
/*     */       
/* 576 */       XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       
/* 578 */       if (XErrorHandlerUtil.saved_error != null && XErrorHandlerUtil.saved_error
/* 579 */         .get_error_code() != 0) {
/* 580 */         throw new XException("Cannot write Motif receiver info property");
/*     */       }
/*     */     } finally {
/* 583 */       unsafe.freeMemory(l);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getMotifActionsForJavaActions(int paramInt) {
/* 588 */     int i = 0;
/*     */     
/* 590 */     if ((paramInt & 0x2) != 0) {
/* 591 */       i |= 0x1;
/*     */     }
/* 593 */     if ((paramInt & 0x1) != 0) {
/* 594 */       i |= 0x2;
/*     */     }
/* 596 */     if ((paramInt & 0x40000000) != 0) {
/* 597 */       i |= 0x4;
/*     */     }
/*     */     
/* 600 */     return i;
/*     */   }
/*     */   
/*     */   public static int getJavaActionsForMotifActions(int paramInt) {
/* 604 */     int i = 0;
/*     */     
/* 606 */     if ((paramInt & 0x1) != 0) {
/* 607 */       i |= 0x2;
/*     */     }
/* 609 */     if ((paramInt & 0x2) != 0) {
/* 610 */       i |= 0x1;
/*     */     }
/* 612 */     if ((paramInt & 0x4) != 0) {
/* 613 */       i |= 0x40000000;
/*     */     }
/*     */     
/* 616 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/MotifDnDConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */