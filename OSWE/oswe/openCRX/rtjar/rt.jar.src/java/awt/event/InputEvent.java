/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Toolkit;
/*     */ import java.util.Arrays;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.security.util.SecurityConstants;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InputEvent
/*     */   extends ComponentEvent
/*     */ {
/*  61 */   private static final PlatformLogger logger = PlatformLogger.getLogger("java.awt.event.InputEvent");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SHIFT_MASK = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int CTRL_MASK = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int META_MASK = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALT_MASK = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALT_GRAPH_MASK = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BUTTON1_MASK = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BUTTON2_MASK = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BUTTON3_MASK = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SHIFT_DOWN_MASK = 64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int CTRL_DOWN_MASK = 128;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int META_DOWN_MASK = 256;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALT_DOWN_MASK = 512;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BUTTON1_DOWN_MASK = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BUTTON2_DOWN_MASK = 2048;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BUTTON3_DOWN_MASK = 4096;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALT_GRAPH_DOWN_MASK = 8192;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   private static final int[] BUTTON_DOWN_MASK = new int[] { 1024, 2048, 4096, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824 };
/*     */ 
/*     */   
/*     */   static final int FIRST_HIGH_BIT = -2147483648;
/*     */ 
/*     */   
/*     */   static final int JDK_1_3_MODIFIERS = 63;
/*     */ 
/*     */   
/*     */   static final int HIGH_MODIFIERS = -2147483648;
/*     */ 
/*     */   
/*     */   long when;
/*     */ 
/*     */   
/*     */   int modifiers;
/*     */ 
/*     */   
/*     */   private transient boolean canAccessSystemClipboard;
/*     */ 
/*     */   
/*     */   static final long serialVersionUID = -2482525981698309786L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] getButtonDownMasks() {
/* 193 */     return Arrays.copyOf(BUTTON_DOWN_MASK, BUTTON_DOWN_MASK.length);
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
/*     */   public static int getMaskForButton(int paramInt) {
/* 246 */     if (paramInt <= 0 || paramInt > BUTTON_DOWN_MASK.length) {
/* 247 */       throw new IllegalArgumentException("button doesn't exist " + paramInt);
/*     */     }
/* 249 */     return BUTTON_DOWN_MASK[paramInt - 1];
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
/*     */   static {
/* 291 */     NativeLibLoader.loadLibraries();
/* 292 */     if (!GraphicsEnvironment.isHeadless()) {
/* 293 */       initIDs();
/*     */     }
/* 295 */     AWTAccessor.setInputEventAccessor(new AWTAccessor.InputEventAccessor()
/*     */         {
/*     */           public int[] getButtonDownMasks() {
/* 298 */             return InputEvent.getButtonDownMasks();
/*     */           }
/*     */         });
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
/*     */   InputEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2) {
/* 341 */     super(paramComponent, paramInt1);
/* 342 */     this.when = paramLong;
/* 343 */     this.modifiers = paramInt2;
/* 344 */     this.canAccessSystemClipboard = canAccessSystemClipboard();
/*     */   }
/*     */   
/*     */   private boolean canAccessSystemClipboard() {
/* 348 */     boolean bool = false;
/*     */     
/* 350 */     if (!GraphicsEnvironment.isHeadless()) {
/* 351 */       SecurityManager securityManager = System.getSecurityManager();
/* 352 */       if (securityManager != null) {
/*     */         try {
/* 354 */           securityManager.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
/* 355 */           bool = true;
/* 356 */         } catch (SecurityException securityException) {
/* 357 */           if (logger.isLoggable(PlatformLogger.Level.FINE)) {
/* 358 */             logger.fine("InputEvent.canAccessSystemClipboard() got SecurityException ", securityException);
/*     */           }
/*     */         } 
/*     */       } else {
/* 362 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 366 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShiftDown() {
/* 373 */     return ((this.modifiers & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isControlDown() {
/* 380 */     return ((this.modifiers & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMetaDown() {
/* 387 */     return ((this.modifiers & 0x4) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAltDown() {
/* 394 */     return ((this.modifiers & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAltGraphDown() {
/* 401 */     return ((this.modifiers & 0x20) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWhen() {
/* 409 */     return this.when;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModifiers() {
/* 416 */     return this.modifiers & 0x8000003F;
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
/*     */   public int getModifiersEx() {
/* 457 */     return this.modifiers & 0xFFFFFFC0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void consume() {
/* 465 */     this.consumed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConsumed() {
/* 473 */     return this.consumed;
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
/*     */   public static String getModifiersExText(int paramInt) {
/* 498 */     StringBuilder stringBuilder = new StringBuilder();
/* 499 */     if ((paramInt & 0x100) != 0) {
/* 500 */       stringBuilder.append(Toolkit.getProperty("AWT.meta", "Meta"));
/* 501 */       stringBuilder.append("+");
/*     */     } 
/* 503 */     if ((paramInt & 0x80) != 0) {
/* 504 */       stringBuilder.append(Toolkit.getProperty("AWT.control", "Ctrl"));
/* 505 */       stringBuilder.append("+");
/*     */     } 
/* 507 */     if ((paramInt & 0x200) != 0) {
/* 508 */       stringBuilder.append(Toolkit.getProperty("AWT.alt", "Alt"));
/* 509 */       stringBuilder.append("+");
/*     */     } 
/* 511 */     if ((paramInt & 0x40) != 0) {
/* 512 */       stringBuilder.append(Toolkit.getProperty("AWT.shift", "Shift"));
/* 513 */       stringBuilder.append("+");
/*     */     } 
/* 515 */     if ((paramInt & 0x2000) != 0) {
/* 516 */       stringBuilder.append(Toolkit.getProperty("AWT.altGraph", "Alt Graph"));
/* 517 */       stringBuilder.append("+");
/*     */     } 
/*     */     
/* 520 */     byte b = 1;
/* 521 */     for (int i : BUTTON_DOWN_MASK) {
/* 522 */       if ((paramInt & i) != 0) {
/* 523 */         stringBuilder.append(Toolkit.getProperty("AWT.button" + b, "Button" + b));
/* 524 */         stringBuilder.append("+");
/*     */       } 
/* 526 */       b++;
/*     */     } 
/* 528 */     if (stringBuilder.length() > 0) {
/* 529 */       stringBuilder.setLength(stringBuilder.length() - 1);
/*     */     }
/* 531 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/InputEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */