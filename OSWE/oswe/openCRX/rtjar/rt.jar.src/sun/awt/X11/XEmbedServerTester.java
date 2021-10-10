/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Robot;
/*     */ import java.util.LinkedList;
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
/*     */ public class XEmbedServerTester
/*     */   implements XEventDispatcher
/*     */ {
/*  40 */   private static final PlatformLogger xembedLog = PlatformLogger.getLogger("sun.awt.X11.xembed.XEmbedServerTester");
/*  41 */   private final Object EVENT_LOCK = new Object(); static final int SYSTEM_EVENT_MASK = 32768;
/*     */   int my_version;
/*     */   int server_version;
/*  44 */   XEmbedHelper xembed = new XEmbedHelper();
/*     */   boolean focused;
/*     */   int focusedKind;
/*     */   int focusedServerComponent;
/*     */   boolean reparent;
/*     */   long parent;
/*     */   boolean windowActive;
/*     */   boolean xembedActive;
/*     */   XBaseWindow window;
/*  53 */   volatile int eventWaited = -1; volatile int eventReceived = -1; int mapped; int accel_key;
/*     */   int accel_keysym;
/*     */   int accel_mods;
/*  56 */   static Rectangle initialBounds = new Rectangle(0, 0, 100, 100);
/*     */   
/*     */   Robot robot;
/*     */   Rectangle[] serverBounds;
/*     */   private static final int SERVER_BOUNDS = 0;
/*  61 */   LinkedList<Integer> events = new LinkedList<>(); private static final int OTHER_FRAME = 1; private static final int SERVER_FOCUS = 2; private static final int SERVER_MODAL = 3; private static final int MODAL_CLOSE = 4;
/*     */   
/*     */   private XEmbedServerTester(Rectangle[] paramArrayOfRectangle, long paramLong) {
/*  64 */     this.parent = paramLong;
/*  65 */     this.focusedKind = -1;
/*  66 */     this.focusedServerComponent = -1;
/*  67 */     this.reparent = false;
/*  68 */     this.windowActive = false;
/*  69 */     this.xembedActive = false;
/*  70 */     this.my_version = 0;
/*  71 */     this.mapped = 1;
/*  72 */     this.serverBounds = paramArrayOfRectangle;
/*  73 */     if (paramArrayOfRectangle.length < 5) {
/*  74 */       throw new IllegalArgumentException("There must be at least five areas: server-activation, server-deactivation, server-focus, server-modal show, modal-close");
/*     */     }
/*     */     
/*     */     try {
/*  78 */       this.robot = new Robot();
/*  79 */       this.robot.setAutoDelay(100);
/*  80 */     } catch (Exception exception) {
/*  81 */       throw new RuntimeException("Can't create robot");
/*     */     } 
/*  83 */     initAccel();
/*  84 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  85 */       xembedLog.finer("XEmbed client(tester), embedder window: " + Long.toHexString(paramLong));
/*     */     }
/*     */   }
/*     */   
/*     */   public static XEmbedServerTester getTester(Rectangle[] paramArrayOfRectangle, long paramLong) {
/*  90 */     return new XEmbedServerTester(paramArrayOfRectangle, paramLong);
/*     */   }
/*     */   
/*     */   private void dumpReceivedEvents() {
/*  94 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  95 */       xembedLog.finer("Events received so far:");
/*  96 */       byte b = 0;
/*  97 */       for (Integer integer : this.events) {
/*  98 */         xembedLog.finer(b++ + ":" + XEmbedHelper.msgidToString(integer.intValue()));
/*     */       }
/* 100 */       xembedLog.finer("End of event dump");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void test1_1() {
/* 105 */     int i = embedCompletely();
/* 106 */     waitWindowActivated(i);
/* 107 */     requestFocus();
/* 108 */     deactivateServer();
/* 109 */     i = activateServer(getEventPos());
/* 110 */     waitFocusGained(i);
/* 111 */     checkFocusGained(0);
/*     */   }
/*     */   
/*     */   public void test1_2() {
/* 115 */     int i = embedCompletely();
/* 116 */     waitWindowActivated(i);
/* 117 */     requestFocus();
/* 118 */     checkFocusGained(0);
/*     */   }
/*     */   
/*     */   public void test1_3() {
/* 122 */     embedCompletely();
/* 123 */     deactivateServer();
/* 124 */     requestFocusNoWait();
/* 125 */     checkNotFocused();
/*     */   }
/*     */   
/*     */   public void test1_4() {
/* 129 */     embedCompletely();
/* 130 */     deactivateServer();
/* 131 */     requestFocusNoWait();
/* 132 */     checkNotFocused();
/* 133 */     int i = getEventPos();
/* 134 */     activateServer(i);
/* 135 */     waitFocusGained(i);
/* 136 */     checkFocusGained(0);
/*     */   }
/*     */   
/*     */   public void test1_5() {
/* 140 */     int i = embedCompletely();
/* 141 */     waitWindowActivated(i);
/* 142 */     checkWindowActivated();
/*     */   }
/*     */   
/*     */   public void test1_6() {
/* 146 */     int i = embedCompletely();
/* 147 */     waitWindowActivated(i);
/* 148 */     requestFocus();
/* 149 */     i = deactivateServer();
/* 150 */     checkFocused();
/*     */   }
/*     */   
/*     */   public void test1_7() {
/* 154 */     int i = embedCompletely();
/* 155 */     waitWindowActivated(i);
/* 156 */     requestFocus();
/* 157 */     focusServer();
/* 158 */     checkFocusLost();
/*     */   }
/*     */   
/*     */   public void test2_5() {
/* 162 */     int i = embedCompletely();
/* 163 */     waitWindowActivated(i);
/* 164 */     requestFocus();
/* 165 */     focusServerNext();
/* 166 */     checkFocusedServerNext();
/* 167 */     checkFocusLost();
/*     */   }
/*     */   
/*     */   public void test2_6() {
/* 171 */     int i = embedCompletely();
/* 172 */     waitWindowActivated(i);
/* 173 */     requestFocus();
/* 174 */     focusServerPrev();
/* 175 */     checkFocusedServerPrev();
/* 176 */     checkFocusLost();
/*     */   }
/*     */   
/*     */   public void test3_1() {
/* 180 */     this.reparent = false;
/* 181 */     embedCompletely();
/*     */   }
/*     */   
/*     */   public void test3_3() {
/* 185 */     this.reparent = true;
/* 186 */     embedCompletely();
/*     */   }
/*     */   
/*     */   public void test3_4() {
/* 190 */     this.my_version = 10;
/* 191 */     embedCompletely();
/* 192 */     if (this.server_version != 0) {
/* 193 */       throw new RuntimeException("Version " + this.server_version + " is not minimal");
/*     */     }
/*     */   }
/*     */   
/*     */   public void test3_5() {
/* 198 */     embedCompletely();
/*     */     
/* 200 */     this.window.destroy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     sleep(1000);
/*     */   }
/*     */   
/*     */   public void test3_6() {
/* 210 */     embedCompletely();
/*     */     
/* 212 */     sleep(1000);
/* 213 */     XToolkit.awtLock();
/*     */     try {
/* 215 */       XlibWrapper.XUnmapWindow(XToolkit.getDisplay(), this.window.getWindow());
/* 216 */       XlibWrapper.XReparentWindow(XToolkit.getDisplay(), this.window.getWindow(), XToolkit.getDefaultRootWindow(), 0, 0);
/*     */     } finally {
/* 218 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 221 */     int i = getEventPos();
/*     */     
/* 223 */     activateServerNoWait(i);
/*     */     
/* 225 */     sleep(1000);
/* 226 */     if (checkEventList(i, 1) != -1) {
/* 227 */       throw new RuntimeException("Focus was been given to the client after XEmbed has ended");
/*     */     }
/*     */   }
/*     */   
/*     */   public void test4_1() {
/* 232 */     this.mapped = 1;
/* 233 */     int i = getEventPos();
/* 234 */     embedCompletely();
/* 235 */     sleep(1000);
/* 236 */     checkMapped();
/*     */   }
/*     */   
/*     */   public void test4_2() {
/* 240 */     this.mapped = 0;
/* 241 */     embedCompletely();
/* 242 */     sleep(1000);
/*     */     
/* 244 */     int i = getEventPos();
/* 245 */     this.mapped = 1;
/* 246 */     updateEmbedInfo();
/* 247 */     sleep(1000);
/* 248 */     checkMapped();
/*     */   }
/*     */   
/*     */   public void test4_3() {
/* 252 */     int i = getEventPos();
/* 253 */     this.mapped = 1;
/* 254 */     embedCompletely();
/*     */     
/* 256 */     i = getEventPos();
/* 257 */     this.mapped = 0;
/* 258 */     updateEmbedInfo();
/* 259 */     sleep(1000);
/* 260 */     checkNotMapped();
/*     */   }
/*     */   
/*     */   public void test4_4() {
/* 264 */     this.mapped = 0;
/* 265 */     embedCompletely();
/* 266 */     sleep(1000);
/* 267 */     if (XlibUtil.getWindowMapState(this.window.getWindow()) != 0) {
/* 268 */       throw new RuntimeException("Client has been mapped");
/*     */     }
/*     */   }
/*     */   
/*     */   public void test6_1_1() {
/* 273 */     embedCompletely();
/* 274 */     registerAccelerator();
/* 275 */     focusServer();
/* 276 */     int i = pressAccelKey();
/* 277 */     waitForEvent(i, 14);
/*     */   }
/*     */   
/*     */   public void test6_1_2() {
/* 281 */     embedCompletely();
/* 282 */     registerAccelerator();
/* 283 */     focusServer();
/* 284 */     deactivateServer();
/* 285 */     int i = pressAccelKey();
/* 286 */     sleep(1000);
/* 287 */     if (checkEventList(i, 14) != -1) {
/* 288 */       throw new RuntimeException("Accelerator has been activated in inactive embedder");
/*     */     }
/*     */   }
/*     */   
/*     */   public void test6_1_3() {
/* 293 */     embedCompletely();
/* 294 */     registerAccelerator();
/* 295 */     focusServer();
/* 296 */     deactivateServer();
/* 297 */     unregisterAccelerator();
/* 298 */     int i = pressAccelKey();
/* 299 */     sleep(1000);
/* 300 */     if (checkEventList(i, 14) != -1) {
/* 301 */       throw new RuntimeException("Accelerator has been activated after unregistering");
/*     */     }
/*     */   }
/*     */   
/*     */   public void test6_1_4() {
/* 306 */     embedCompletely();
/* 307 */     registerAccelerator();
/* 308 */     requestFocus();
/* 309 */     int i = pressAccelKey();
/* 310 */     sleep(1000);
/* 311 */     if (checkEventList(i, 14) != -1)
/* 312 */       throw new RuntimeException("Accelerator has been activated in focused client"); 
/*     */   }
/*     */   
/*     */   public void test6_2_1() {
/* 316 */     embedCompletely();
/* 317 */     grabKey();
/* 318 */     focusServer();
/* 319 */     int i = pressAccelKey();
/* 320 */     waitSystemEvent(i, 2);
/*     */   }
/*     */   
/*     */   public void test6_2_2() {
/* 324 */     embedCompletely();
/* 325 */     grabKey();
/* 326 */     focusServer();
/* 327 */     deactivateServer();
/* 328 */     int i = pressAccelKey();
/* 329 */     sleep(1000);
/* 330 */     if (checkEventList(i, 32770) != -1) {
/* 331 */       throw new RuntimeException("Accelerator has been activated in inactive embedder");
/*     */     }
/*     */   }
/*     */   
/*     */   public void test6_2_3() {
/* 336 */     embedCompletely();
/* 337 */     grabKey();
/* 338 */     focusServer();
/* 339 */     deactivateServer();
/* 340 */     ungrabKey();
/* 341 */     int i = pressAccelKey();
/* 342 */     sleep(1000);
/* 343 */     if (checkEventList(i, 32770) != -1) {
/* 344 */       throw new RuntimeException("Accelerator has been activated after unregistering");
/*     */     }
/*     */   }
/*     */   
/*     */   public void test6_2_4() {
/* 349 */     embedCompletely();
/* 350 */     grabKey();
/* 351 */     requestFocus();
/* 352 */     int i = pressAccelKey();
/* 353 */     sleep(1000);
/* 354 */     int j = checkEventList(i, 32770);
/* 355 */     if (j != -1) {
/* 356 */       j = checkEventList(j + 1, 32770);
/* 357 */       if (j != -1) {
/* 358 */         throw new RuntimeException("Accelerator has been activated in focused client");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void test7_1() {
/* 364 */     embedCompletely();
/* 365 */     int i = showModalDialog();
/* 366 */     waitForEvent(i, 10);
/*     */   }
/*     */   
/*     */   public void test7_2() {
/* 370 */     embedCompletely();
/* 371 */     int i = showModalDialog();
/* 372 */     waitForEvent(i, 10);
/* 373 */     i = hideModalDialog();
/* 374 */     waitForEvent(i, 11);
/*     */   }
/*     */   
/*     */   public void test9_1() {
/* 378 */     embedCompletely();
/* 379 */     requestFocus();
/* 380 */     int i = pressAccelKey();
/* 381 */     waitForEvent(i, 32770);
/*     */   }
/*     */   
/*     */   private int embed() {
/* 385 */     int i = getEventPos();
/* 386 */     XToolkit.awtLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 394 */       XCreateWindowParams xCreateWindowParams = new XCreateWindowParams(new Object[] { "parent window", Long.valueOf(this.reparent ? XToolkit.getDefaultRootWindow() : this.parent), "bounds", initialBounds, "embedded", Boolean.TRUE, "visible", Boolean.valueOf((this.mapped == 1)), "event mask", Long.valueOf(720897L) });
/*     */       
/* 396 */       this.window = new XBaseWindow(xCreateWindowParams);
/*     */       
/* 398 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 399 */         xembedLog.finer("Created tester window: " + this.window);
/*     */       }
/*     */       
/* 402 */       XToolkit.addEventDispatcher(this.window.getWindow(), this);
/* 403 */       updateEmbedInfo();
/* 404 */       if (this.reparent) {
/* 405 */         xembedLog.finer("Reparenting to embedder");
/* 406 */         XlibWrapper.XReparentWindow(XToolkit.getDisplay(), this.window.getWindow(), this.parent, 0, 0);
/*     */       } 
/*     */     } finally {
/* 409 */       XToolkit.awtUnlock();
/*     */     } 
/* 411 */     return i;
/*     */   }
/*     */   
/*     */   private void updateEmbedInfo() {
/* 415 */     long[] arrayOfLong = { this.my_version, this.mapped };
/* 416 */     long l = Native.card32ToData(arrayOfLong);
/*     */     try {
/* 418 */       XEmbedHelper.XEmbedInfo.setAtomData(this.window.getWindow(), l, arrayOfLong.length);
/*     */     } finally {
/* 420 */       XEmbedHelper.unsafe.freeMemory(l);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getEventPos() {
/* 425 */     synchronized (this.EVENT_LOCK) {
/* 426 */       return this.events.size();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int embedCompletely() {
/* 431 */     xembedLog.fine("Embedding completely");
/* 432 */     int i = getEventPos();
/* 433 */     embed();
/* 434 */     waitEmbeddedNotify(i);
/* 435 */     return i;
/*     */   }
/*     */   private int requestFocus() {
/* 438 */     xembedLog.fine("Requesting focus");
/* 439 */     int i = getEventPos();
/* 440 */     sendMessage(3);
/* 441 */     waitFocusGained(i);
/* 442 */     return i;
/*     */   }
/*     */   private int requestFocusNoWait() {
/* 445 */     xembedLog.fine("Requesting focus without wait");
/* 446 */     int i = getEventPos();
/* 447 */     sendMessage(3);
/* 448 */     return i;
/*     */   }
/*     */   private int activateServer(int paramInt) {
/* 451 */     int i = activateServerNoWait(paramInt);
/* 452 */     waitWindowActivated(i);
/* 453 */     return i;
/*     */   }
/*     */   private int activateServerNoWait(int paramInt) {
/* 456 */     xembedLog.fine("Activating server");
/* 457 */     int i = getEventPos();
/* 458 */     if (checkEventList(paramInt, 1) != -1) {
/* 459 */       xembedLog.fine("Activation already received");
/* 460 */       return i;
/*     */     } 
/* 462 */     Point point = this.serverBounds[0].getLocation();
/* 463 */     point.x = (int)(point.x + this.serverBounds[0].getWidth() / 2.0D);
/* 464 */     point.y += 5;
/* 465 */     this.robot.mouseMove(point.x, point.y);
/* 466 */     this.robot.mousePress(16);
/* 467 */     this.robot.mouseRelease(16);
/* 468 */     return i;
/*     */   }
/*     */   private int deactivateServer() {
/* 471 */     xembedLog.fine("Deactivating server");
/* 472 */     int i = getEventPos();
/* 473 */     Point point = this.serverBounds[1].getLocation();
/* 474 */     point.x = (int)(point.x + this.serverBounds[1].getWidth() / 2.0D);
/* 475 */     point.y = (int)(point.y + this.serverBounds[1].getHeight() / 2.0D);
/* 476 */     this.robot.mouseMove(point.x, point.y);
/* 477 */     this.robot.mousePress(16);
/* 478 */     this.robot.delay(50);
/* 479 */     this.robot.mouseRelease(16);
/* 480 */     waitWindowDeactivated(i);
/* 481 */     return i;
/*     */   }
/*     */   private int focusServer() {
/* 484 */     xembedLog.fine("Focusing server");
/* 485 */     boolean bool = this.focused;
/* 486 */     int i = getEventPos();
/* 487 */     Point point = this.serverBounds[2].getLocation();
/* 488 */     point.x += 5;
/* 489 */     point.y += 5;
/* 490 */     this.robot.mouseMove(point.x, point.y);
/* 491 */     this.robot.mousePress(16);
/* 492 */     this.robot.delay(50);
/* 493 */     this.robot.mouseRelease(16);
/* 494 */     if (bool) {
/* 495 */       waitFocusLost(i);
/*     */     }
/* 497 */     return i;
/*     */   }
/*     */   private int focusServerNext() {
/* 500 */     xembedLog.fine("Focusing next server component");
/* 501 */     int i = getEventPos();
/* 502 */     sendMessage(6);
/* 503 */     waitFocusLost(i);
/* 504 */     return i;
/*     */   }
/*     */   private int focusServerPrev() {
/* 507 */     xembedLog.fine("Focusing previous server component");
/* 508 */     int i = getEventPos();
/* 509 */     sendMessage(7);
/* 510 */     waitFocusLost(i);
/* 511 */     return i;
/*     */   }
/*     */   
/*     */   private void waitEmbeddedNotify(int paramInt) {
/* 515 */     waitForEvent(paramInt, 0);
/*     */   }
/*     */   private void waitFocusGained(int paramInt) {
/* 518 */     waitForEvent(paramInt, 4);
/*     */   }
/*     */   private void waitFocusLost(int paramInt) {
/* 521 */     waitForEvent(paramInt, 5);
/*     */   }
/*     */   private void waitWindowActivated(int paramInt) {
/* 524 */     waitForEvent(paramInt, 1);
/*     */   }
/*     */   private void waitWindowDeactivated(int paramInt) {
/* 527 */     waitForEvent(paramInt, 2);
/*     */   }
/*     */   
/*     */   private void waitSystemEvent(int paramInt1, int paramInt2) {
/* 531 */     waitForEvent(paramInt1, paramInt2 | 0x8000);
/*     */   }
/*     */   
/*     */   private void waitForEvent(int paramInt1, int paramInt2) {
/* 535 */     synchronized (this.EVENT_LOCK) {
/*     */       
/* 537 */       if (checkEventList(paramInt1, paramInt2) != -1) {
/* 538 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 539 */           xembedLog.finer("The event " + XEmbedHelper.msgidToString(paramInt2) + " has already been received");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 544 */       if (this.eventReceived == paramInt2) {
/*     */         
/* 546 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 547 */           xembedLog.finer("Already received " + XEmbedHelper.msgidToString(paramInt2));
/*     */         }
/*     */         return;
/*     */       } 
/* 551 */       this.eventReceived = -1;
/* 552 */       this.eventWaited = paramInt2;
/* 553 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 554 */         xembedLog.finer("Waiting for " + XEmbedHelper.msgidToString(paramInt2) + " starting from " + paramInt1);
/*     */       }
/*     */       try {
/* 557 */         this.EVENT_LOCK.wait(3000L);
/* 558 */       } catch (InterruptedException interruptedException) {
/* 559 */         xembedLog.warning("Event wait interrupted", interruptedException);
/*     */       } 
/* 561 */       this.eventWaited = -1;
/* 562 */       if (checkEventList(paramInt1, paramInt2) == -1) {
/* 563 */         dumpReceivedEvents();
/* 564 */         throw new RuntimeException("Didn't receive event " + XEmbedHelper.msgidToString(paramInt2) + " but recevied " + XEmbedHelper.msgidToString(this.eventReceived));
/*     */       } 
/* 566 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 567 */         xembedLog.finer("Successfully recevied " + XEmbedHelper.msgidToString(paramInt2));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int checkEventList(int paramInt1, int paramInt2) {
/* 576 */     if (paramInt1 == -1) {
/* 577 */       return -1;
/*     */     }
/* 579 */     synchronized (this.EVENT_LOCK) {
/* 580 */       for (int i = paramInt1; i < this.events.size(); i++) {
/* 581 */         if (((Integer)this.events.get(i)).intValue() == paramInt2) {
/* 582 */           return i;
/*     */         }
/*     */       } 
/* 585 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkFocusedServerNext() {
/* 590 */     if (this.focusedServerComponent != 0)
/* 591 */       throw new RuntimeException("Wrong focused server component, should be 0, but it is " + this.focusedServerComponent); 
/*     */   }
/*     */   
/*     */   private void checkFocusedServerPrev() {
/* 595 */     if (this.focusedServerComponent != 2)
/* 596 */       throw new RuntimeException("Wrong focused server component, should be 2, but it is " + this.focusedServerComponent); 
/*     */   }
/*     */   
/*     */   private void checkFocusGained(int paramInt) {
/* 600 */     if (!this.focused) {
/* 601 */       throw new RuntimeException("Didn't receive FOCUS_GAINED");
/*     */     }
/* 603 */     if (this.focusedKind != paramInt)
/* 604 */       throw new RuntimeException("Kinds don't match, required: " + paramInt + ", current: " + this.focusedKind); 
/*     */   }
/*     */   
/*     */   private void checkNotFocused() {
/* 608 */     if (this.focused)
/* 609 */       throw new RuntimeException("Focused"); 
/*     */   }
/*     */   
/*     */   private void checkFocused() {
/* 613 */     if (!this.focused) {
/* 614 */       throw new RuntimeException("Not Focused");
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkFocusLost() {
/* 619 */     checkNotFocused();
/* 620 */     if (this.focusedKind != 5)
/* 621 */       throw new RuntimeException("Didn't receive FOCUS_LOST"); 
/*     */   }
/*     */   
/*     */   private void checkWindowActivated() {
/* 625 */     if (!this.windowActive)
/* 626 */       throw new RuntimeException("Window is not active"); 
/*     */   }
/*     */   
/*     */   private void checkMapped() {
/* 630 */     if (XlibUtil.getWindowMapState(this.window.getWindow()) == 0)
/* 631 */       throw new RuntimeException("Client is not mapped"); 
/*     */   }
/*     */   
/*     */   private void checkNotMapped() {
/* 635 */     if (XlibUtil.getWindowMapState(this.window.getWindow()) != 0) {
/* 636 */       throw new RuntimeException("Client is mapped");
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendMessage(int paramInt) {
/* 641 */     this.xembed.sendMessage(this.parent, paramInt);
/*     */   }
/*     */   private void sendMessage(int paramInt1, int paramInt2, long paramLong1, long paramLong2) {
/* 644 */     this.xembed.sendMessage(this.parent, paramInt1, paramInt2, paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   public void dispatchEvent(XEvent paramXEvent) {
/* 648 */     if (paramXEvent.get_type() == 33) {
/* 649 */       XClientMessageEvent xClientMessageEvent = paramXEvent.get_xclient();
/* 650 */       if (xClientMessageEvent.get_message_type() == XEmbedHelper.XEmbed.getAtom()) {
/* 651 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 652 */           xembedLog.fine("Embedded message: " + XEmbedHelper.msgidToString((int)xClientMessageEvent.get_data(1)));
/*     */         }
/* 654 */         switch ((int)xClientMessageEvent.get_data(1)) {
/*     */           case 0:
/* 656 */             this.xembedActive = true;
/* 657 */             this.server_version = (int)xClientMessageEvent.get_data(3);
/*     */             break;
/*     */           case 1:
/* 660 */             this.windowActive = true;
/*     */             break;
/*     */           case 2:
/* 663 */             this.windowActive = false;
/*     */             break;
/*     */           case 4:
/* 666 */             this.focused = true;
/* 667 */             this.focusedKind = (int)xClientMessageEvent.get_data(2);
/*     */             break;
/*     */           case 5:
/* 670 */             this.focused = false;
/* 671 */             this.focusedKind = 5;
/* 672 */             this.focusedServerComponent = (int)xClientMessageEvent.get_data(2);
/*     */             break;
/*     */         } 
/* 675 */         synchronized (this.EVENT_LOCK) {
/* 676 */           this.events.add(Integer.valueOf((int)xClientMessageEvent.get_data(1)));
/*     */           
/* 678 */           if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 679 */             xembedLog.finer("Tester is waiting for " + XEmbedHelper.msgidToString(this.eventWaited));
/*     */           }
/* 681 */           if ((int)xClientMessageEvent.get_data(1) == this.eventWaited) {
/* 682 */             this.eventReceived = (int)xClientMessageEvent.get_data(1);
/* 683 */             if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 684 */               xembedLog.finer("Notifying waiting object for event " + System.identityHashCode(this.EVENT_LOCK));
/*     */             }
/* 686 */             this.EVENT_LOCK.notifyAll();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 691 */       synchronized (this.EVENT_LOCK) {
/* 692 */         int i = paramXEvent.get_type() | 0x8000;
/* 693 */         this.events.add(Integer.valueOf(i));
/*     */         
/* 695 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 696 */           xembedLog.finer("Tester is waiting for " + XEmbedHelper.msgidToString(this.eventWaited) + ", but we received " + paramXEvent + "(" + XEmbedHelper.msgidToString(i) + ")");
/*     */         }
/* 698 */         if (i == this.eventWaited) {
/* 699 */           this.eventReceived = i;
/* 700 */           if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 701 */             xembedLog.finer("Notifying waiting object" + System.identityHashCode(this.EVENT_LOCK));
/*     */           }
/* 703 */           this.EVENT_LOCK.notifyAll();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sleep(int paramInt) {
/*     */     try {
/* 711 */       Thread.sleep(paramInt);
/* 712 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerAccelerator() {
/* 717 */     sendMessage(12, 1, this.accel_keysym, this.accel_mods);
/*     */   }
/*     */   
/*     */   private void unregisterAccelerator() {
/* 721 */     sendMessage(13, 1, 0L, 0L);
/*     */   }
/*     */   
/*     */   private int pressAccelKey() {
/* 725 */     int i = getEventPos();
/* 726 */     this.robot.keyPress(this.accel_key);
/* 727 */     this.robot.keyRelease(this.accel_key);
/* 728 */     return i;
/*     */   }
/*     */   
/*     */   private void initAccel() {
/* 732 */     this.accel_key = 65;
/* 733 */     this.accel_keysym = XWindow.getKeySymForAWTKeyCode(this.accel_key);
/* 734 */     this.accel_mods = 0;
/*     */   }
/*     */   
/*     */   private void grabKey() {
/* 738 */     sendMessage(108, 0, this.accel_keysym, this.accel_mods);
/*     */   }
/*     */   private void ungrabKey() {
/* 741 */     sendMessage(109, 0, this.accel_keysym, this.accel_mods);
/*     */   }
/*     */   private int showModalDialog() {
/* 744 */     xembedLog.fine("Showing modal dialog");
/* 745 */     int i = getEventPos();
/* 746 */     Point point = this.serverBounds[3].getLocation();
/* 747 */     point.x += 5;
/* 748 */     point.y += 5;
/* 749 */     this.robot.mouseMove(point.x, point.y);
/* 750 */     this.robot.mousePress(16);
/* 751 */     this.robot.delay(50);
/* 752 */     this.robot.mouseRelease(16);
/* 753 */     return i;
/*     */   }
/*     */   private int hideModalDialog() {
/* 756 */     xembedLog.fine("Hide modal dialog");
/* 757 */     int i = getEventPos();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 765 */     this.robot.keyPress(32);
/* 766 */     this.robot.keyRelease(32);
/* 767 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbedServerTester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */