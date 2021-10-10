/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.awt.peer.RobotPeer;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import sun.awt.ComponentFactory;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.image.SunWritableRaster;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.DisposerRecord;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Robot
/*     */ {
/*     */   private static final int MAX_DELAY = 60000;
/*     */   private RobotPeer peer;
/*     */   private boolean isAutoWaitForIdle = false;
/*  72 */   private int autoDelay = 0;
/*  73 */   private static int LEGAL_BUTTON_MASK = 0;
/*     */   
/*  75 */   private DirectColorModel screenCapCM = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Object anchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient RobotDisposer disposer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(GraphicsDevice paramGraphicsDevice) throws AWTException {
/* 131 */     checkRobotAllowed();
/* 132 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 133 */     if (toolkit instanceof ComponentFactory) {
/* 134 */       this.peer = ((ComponentFactory)toolkit).createRobot(this, paramGraphicsDevice);
/* 135 */       this.disposer = new RobotDisposer(this.peer);
/* 136 */       Disposer.addRecord(this.anchor, this.disposer);
/*     */     } 
/* 138 */     initLegalButtonMask();
/*     */   }
/*     */   
/*     */   private static synchronized void initLegalButtonMask() {
/* 142 */     if (LEGAL_BUTTON_MASK != 0)
/*     */       return; 
/* 144 */     int i = 0;
/* 145 */     if (Toolkit.getDefaultToolkit().areExtraMouseButtonsEnabled() && 
/* 146 */       Toolkit.getDefaultToolkit() instanceof SunToolkit) {
/* 147 */       int j = ((SunToolkit)Toolkit.getDefaultToolkit()).getNumberOfButtons();
/* 148 */       for (byte b = 0; b < j; b++) {
/* 149 */         i |= InputEvent.getMaskForButton(b + 1);
/*     */       }
/*     */     } 
/*     */     
/* 153 */     i |= 0x1C1C;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     LEGAL_BUTTON_MASK = i;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkRobotAllowed() {
/* 164 */     SecurityManager securityManager = System.getSecurityManager();
/* 165 */     if (securityManager != null) {
/* 166 */       securityManager.checkPermission(SecurityConstants.AWT.CREATE_ROBOT_PERMISSION);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkIsScreenDevice(GraphicsDevice paramGraphicsDevice) {
/* 172 */     if (paramGraphicsDevice == null || paramGraphicsDevice.getType() != 0)
/* 173 */       throw new IllegalArgumentException("not a valid screen device"); 
/*     */   }
/*     */   
/*     */   public Robot() throws AWTException {
/* 177 */     this.anchor = new Object(); if (GraphicsEnvironment.isHeadless()) throw new AWTException("headless environment");  init(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()); } public Robot(GraphicsDevice paramGraphicsDevice) throws AWTException { this.anchor = new Object();
/*     */     checkIsScreenDevice(paramGraphicsDevice);
/*     */     init(paramGraphicsDevice); }
/*     */    static class RobotDisposer implements DisposerRecord { private final RobotPeer peer;
/*     */     public RobotDisposer(RobotPeer param1RobotPeer) {
/* 182 */       this.peer = param1RobotPeer;
/*     */     }
/*     */     public void dispose() {
/* 185 */       if (this.peer != null) {
/* 186 */         this.peer.dispose();
/*     */       }
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void mouseMove(int paramInt1, int paramInt2) {
/* 199 */     this.peer.mouseMove(paramInt1, paramInt2);
/* 200 */     afterEvent();
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
/*     */   public synchronized void mousePress(int paramInt) {
/* 256 */     checkButtonsArgument(paramInt);
/* 257 */     this.peer.mousePress(paramInt);
/* 258 */     afterEvent();
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
/*     */   public synchronized void mouseRelease(int paramInt) {
/* 313 */     checkButtonsArgument(paramInt);
/* 314 */     this.peer.mouseRelease(paramInt);
/* 315 */     afterEvent();
/*     */   }
/*     */   
/*     */   private void checkButtonsArgument(int paramInt) {
/* 319 */     if ((paramInt | LEGAL_BUTTON_MASK) != LEGAL_BUTTON_MASK) {
/* 320 */       throw new IllegalArgumentException("Invalid combination of button flags");
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
/*     */   public synchronized void mouseWheel(int paramInt) {
/* 334 */     this.peer.mouseWheel(paramInt);
/* 335 */     afterEvent();
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
/*     */   public synchronized void keyPress(int paramInt) {
/* 353 */     checkKeycodeArgument(paramInt);
/* 354 */     this.peer.keyPress(paramInt);
/* 355 */     afterEvent();
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
/*     */   public synchronized void keyRelease(int paramInt) {
/* 372 */     checkKeycodeArgument(paramInt);
/* 373 */     this.peer.keyRelease(paramInt);
/* 374 */     afterEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkKeycodeArgument(int paramInt) {
/* 382 */     if (paramInt == 0) {
/* 383 */       throw new IllegalArgumentException("Invalid key code");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Color getPixelColor(int paramInt1, int paramInt2) {
/* 394 */     checkScreenCaptureAllowed();
/* 395 */     return new Color(this.peer.getRGBPixel(paramInt1, paramInt2));
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
/*     */   public synchronized BufferedImage createScreenCapture(Rectangle paramRectangle) {
/* 410 */     checkScreenCaptureAllowed();
/*     */     
/* 412 */     checkValidRect(paramRectangle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 418 */     if (this.screenCapCM == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 425 */       this.screenCapCM = new DirectColorModel(24, 16711680, 65280, 255);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 433 */     Toolkit.getDefaultToolkit().sync();
/*     */ 
/*     */     
/* 436 */     int[] arrayOfInt2 = new int[3];
/*     */     
/* 438 */     int[] arrayOfInt1 = this.peer.getRGBPixels(paramRectangle);
/* 439 */     DataBufferInt dataBufferInt = new DataBufferInt(arrayOfInt1, arrayOfInt1.length);
/*     */     
/* 441 */     arrayOfInt2[0] = this.screenCapCM.getRedMask();
/* 442 */     arrayOfInt2[1] = this.screenCapCM.getGreenMask();
/* 443 */     arrayOfInt2[2] = this.screenCapCM.getBlueMask();
/*     */     
/* 445 */     WritableRaster writableRaster = Raster.createPackedRaster(dataBufferInt, paramRectangle.width, paramRectangle.height, paramRectangle.width, arrayOfInt2, (Point)null);
/* 446 */     SunWritableRaster.makeTrackable(dataBufferInt);
/*     */     
/* 448 */     return new BufferedImage(this.screenCapCM, writableRaster, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkValidRect(Rectangle paramRectangle) {
/* 454 */     if (paramRectangle.width <= 0 || paramRectangle.height <= 0) {
/* 455 */       throw new IllegalArgumentException("Rectangle width and height must be > 0");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkScreenCaptureAllowed() {
/* 460 */     SecurityManager securityManager = System.getSecurityManager();
/* 461 */     if (securityManager != null) {
/* 462 */       securityManager.checkPermission(SecurityConstants.AWT.READ_DISPLAY_PIXELS_PERMISSION);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void afterEvent() {
/* 471 */     autoWaitForIdle();
/* 472 */     autoDelay();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isAutoWaitForIdle() {
/* 481 */     return this.isAutoWaitForIdle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setAutoWaitForIdle(boolean paramBoolean) {
/* 490 */     this.isAutoWaitForIdle = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void autoWaitForIdle() {
/* 497 */     if (this.isAutoWaitForIdle) {
/* 498 */       waitForIdle();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getAutoDelay() {
/* 506 */     return this.autoDelay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setAutoDelay(int paramInt) {
/* 514 */     checkDelayArgument(paramInt);
/* 515 */     this.autoDelay = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void autoDelay() {
/* 522 */     delay(this.autoDelay);
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
/*     */   public synchronized void delay(int paramInt) {
/* 534 */     checkDelayArgument(paramInt);
/*     */     try {
/* 536 */       Thread.sleep(paramInt);
/* 537 */     } catch (InterruptedException interruptedException) {
/* 538 */       interruptedException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkDelayArgument(int paramInt) {
/* 543 */     if (paramInt < 0 || paramInt > 60000) {
/* 544 */       throw new IllegalArgumentException("Delay must be to 0 to 60,000ms");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void waitForIdle() {
/* 553 */     checkNotDispatchThread();
/*     */ 
/*     */     
/*     */     try {
/* 557 */       SunToolkit.flushPendingEvents();
/* 558 */       EventQueue.invokeAndWait(new Runnable()
/*     */           {
/*     */             
/*     */             public void run() {}
/*     */           });
/* 563 */     } catch (InterruptedException interruptedException) {
/* 564 */       System.err.println("Robot.waitForIdle, non-fatal exception caught:");
/* 565 */       interruptedException.printStackTrace();
/* 566 */     } catch (InvocationTargetException invocationTargetException) {
/* 567 */       System.err.println("Robot.waitForIdle, non-fatal exception caught:");
/* 568 */       invocationTargetException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkNotDispatchThread() {
/* 573 */     if (EventQueue.isDispatchThread()) {
/* 574 */       throw new IllegalThreadStateException("Cannot call method from the event dispatcher thread");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 584 */     String str = "autoDelay = " + getAutoDelay() + ", autoWaitForIdle = " + isAutoWaitForIdle();
/* 585 */     return getClass().getName() + "[ " + str + " ]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Robot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */