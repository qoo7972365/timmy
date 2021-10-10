/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.EventObject;
/*     */ import sun.awt.AWTAccessor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AWTEvent
/*     */   extends EventObject
/*     */ {
/*  81 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.AWTEvent");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] bdata;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean consumed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private volatile transient AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final AccessControlContext getAccessControlContext() {
/* 113 */     if (this.acc == null) {
/* 114 */       throw new SecurityException("AWTEvent is missing AccessControlContext");
/*     */     }
/* 116 */     return this.acc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   transient boolean focusManagerIsDispatching = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   transient boolean isPosted;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean isSystemGenerated;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long COMPONENT_EVENT_MASK = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long CONTAINER_EVENT_MASK = 2L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long FOCUS_EVENT_MASK = 4L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long KEY_EVENT_MASK = 8L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long MOUSE_EVENT_MASK = 16L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long MOUSE_MOTION_EVENT_MASK = 32L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long WINDOW_EVENT_MASK = 64L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long ACTION_EVENT_MASK = 128L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long ADJUSTMENT_EVENT_MASK = 256L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long ITEM_EVENT_MASK = 512L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long TEXT_EVENT_MASK = 1024L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long INPUT_METHOD_EVENT_MASK = 2048L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long INPUT_METHODS_ENABLED_MASK = 4096L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long PAINT_EVENT_MASK = 8192L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long INVOCATION_EVENT_MASK = 16384L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long HIERARCHY_EVENT_MASK = 32768L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long HIERARCHY_BOUNDS_EVENT_MASK = 65536L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long MOUSE_WHEEL_EVENT_MASK = 131072L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long WINDOW_STATE_EVENT_MASK = 262144L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long WINDOW_FOCUS_EVENT_MASK = 524288L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int RESERVED_ID_MAX = 1999;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   private static Field inputEvent_CanAccessSystemClipboard_Field = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -1825314779160409405L;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 254 */     Toolkit.loadLibraries();
/* 255 */     if (!GraphicsEnvironment.isHeadless()) {
/* 256 */       initIDs();
/*     */     }
/* 258 */     AWTAccessor.setAWTEventAccessor(new AWTAccessor.AWTEventAccessor()
/*     */         {
/*     */           public void setPosted(AWTEvent param1AWTEvent) {
/* 261 */             param1AWTEvent.isPosted = true;
/*     */           }
/*     */           
/*     */           public void setSystemGenerated(AWTEvent param1AWTEvent) {
/* 265 */             param1AWTEvent.isSystemGenerated = true;
/*     */           }
/*     */           
/*     */           public boolean isSystemGenerated(AWTEvent param1AWTEvent) {
/* 269 */             return param1AWTEvent.isSystemGenerated;
/*     */           }
/*     */           
/*     */           public AccessControlContext getAccessControlContext(AWTEvent param1AWTEvent) {
/* 273 */             return param1AWTEvent.getAccessControlContext();
/*     */           }
/*     */           
/*     */           public byte[] getBData(AWTEvent param1AWTEvent) {
/* 277 */             return param1AWTEvent.bdata;
/*     */           }
/*     */           
/*     */           public void setBData(AWTEvent param1AWTEvent, byte[] param1ArrayOfbyte) {
/* 281 */             param1AWTEvent.bdata = param1ArrayOfbyte;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized Field get_InputEvent_CanAccessSystemClipboard() {
/* 288 */     if (inputEvent_CanAccessSystemClipboard_Field == null)
/*     */     {
/* 290 */       inputEvent_CanAccessSystemClipboard_Field = AccessController.<Field>doPrivileged(new PrivilegedAction<Field>()
/*     */           {
/*     */             public Field run() {
/* 293 */               Field field = null;
/*     */               
/*     */               try {
/* 296 */                 field = InputEvent.class.getDeclaredField("canAccessSystemClipboard");
/* 297 */                 field.setAccessible(true);
/* 298 */                 return field;
/* 299 */               } catch (SecurityException securityException) {
/* 300 */                 if (AWTEvent.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 301 */                   AWTEvent.log.fine("AWTEvent.get_InputEvent_CanAccessSystemClipboard() got SecurityException ", securityException);
/*     */                 }
/* 303 */               } catch (NoSuchFieldException noSuchFieldException) {
/* 304 */                 if (AWTEvent.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 305 */                   AWTEvent.log.fine("AWTEvent.get_InputEvent_CanAccessSystemClipboard() got NoSuchFieldException ", noSuchFieldException);
/*     */                 }
/*     */               } 
/* 308 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 313 */     return inputEvent_CanAccessSystemClipboard_Field;
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
/*     */   public AWTEvent(Event paramEvent) {
/* 327 */     this(paramEvent.target, paramEvent.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AWTEvent(Object paramObject, int paramInt) {
/* 337 */     super(paramObject);
/* 338 */     this.id = paramInt;
/* 339 */     switch (paramInt) {
/*     */       case 601:
/*     */       case 701:
/*     */       case 900:
/*     */       case 1001:
/* 344 */         this.consumed = true;
/*     */         break;
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
/*     */   public void setSource(Object paramObject) {
/* 363 */     if (this.source == paramObject) {
/*     */       return;
/*     */     }
/*     */     
/* 367 */     Component component = null;
/* 368 */     if (paramObject instanceof Component) {
/* 369 */       component = (Component)paramObject;
/* 370 */       while (component != null && component.peer != null && component.peer instanceof java.awt.peer.LightweightPeer)
/*     */       {
/* 372 */         component = component.parent;
/*     */       }
/*     */     } 
/*     */     
/* 376 */     synchronized (this) {
/* 377 */       this.source = paramObject;
/* 378 */       if (component != null) {
/* 379 */         ComponentPeer componentPeer = component.peer;
/* 380 */         if (componentPeer != null) {
/* 381 */           nativeSetSource(componentPeer);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getID() {
/* 393 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 400 */     String str = null;
/* 401 */     if (this.source instanceof Component) {
/* 402 */       str = ((Component)this.source).getName();
/* 403 */     } else if (this.source instanceof MenuComponent) {
/* 404 */       str = ((MenuComponent)this.source).getName();
/*     */     } 
/* 406 */     return getClass().getName() + "[" + paramString() + "] on " + ((str != null) ? str : (String)this.source);
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
/*     */   public String paramString() {
/* 420 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consume() {
/* 428 */     switch (this.id) {
/*     */       case 401:
/*     */       case 402:
/*     */       case 501:
/*     */       case 502:
/*     */       case 503:
/*     */       case 504:
/*     */       case 505:
/*     */       case 506:
/*     */       case 507:
/*     */       case 1100:
/*     */       case 1101:
/* 440 */         this.consumed = true;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isConsumed() {
/* 451 */     return this.consumed;
/*     */   }
/*     */   
/*     */   Event convertToOld() {
/*     */     KeyEvent keyEvent;
/*     */     int j;
/*     */     MouseEvent mouseEvent;
/*     */     Event event;
/*     */     ActionEvent actionEvent;
/*     */     String str;
/*     */     ItemEvent itemEvent;
/*     */     Object object2;
/*     */     AdjustmentEvent adjustmentEvent;
/* 464 */     Object object1 = getSource();
/* 465 */     int i = this.id;
/*     */     
/* 467 */     switch (this.id) {
/*     */       case 401:
/*     */       case 402:
/* 470 */         keyEvent = (KeyEvent)this;
/* 471 */         if (keyEvent.isActionKey()) {
/* 472 */           i = (this.id == 401) ? 403 : 404;
/*     */         }
/*     */         
/* 475 */         j = keyEvent.getKeyCode();
/* 476 */         if (j == 16 || j == 17 || j == 18)
/*     */         {
/*     */           
/* 479 */           return null;
/*     */         }
/*     */         
/* 482 */         return new Event(object1, keyEvent.getWhen(), i, 0, 0, 
/* 483 */             Event.getOldEventKey(keyEvent), keyEvent
/* 484 */             .getModifiers() & 0xFFFFFFEF);
/*     */       
/*     */       case 501:
/*     */       case 502:
/*     */       case 503:
/*     */       case 504:
/*     */       case 505:
/*     */       case 506:
/* 492 */         mouseEvent = (MouseEvent)this;
/*     */ 
/*     */ 
/*     */         
/* 496 */         event = new Event(object1, mouseEvent.getWhen(), i, mouseEvent.getX(), mouseEvent.getY(), 0, mouseEvent.getModifiers() & 0xFFFFFFEF);
/* 497 */         event.clickCount = mouseEvent.getClickCount();
/* 498 */         return event;
/*     */       
/*     */       case 1004:
/* 501 */         return new Event(object1, 1004, null);
/*     */       
/*     */       case 1005:
/* 504 */         return new Event(object1, 1005, null);
/*     */       
/*     */       case 201:
/*     */       case 203:
/*     */       case 204:
/* 509 */         return new Event(object1, i, null);
/*     */       
/*     */       case 100:
/* 512 */         if (object1 instanceof Frame || object1 instanceof Dialog) {
/* 513 */           Point point = ((Component)object1).getLocation();
/* 514 */           return new Event(object1, 0L, 205, point.x, point.y, 0, 0);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 1001:
/* 519 */         actionEvent = (ActionEvent)this;
/*     */         
/* 521 */         if (object1 instanceof Button) {
/* 522 */           str = ((Button)object1).getLabel();
/* 523 */         } else if (object1 instanceof MenuItem) {
/* 524 */           str = ((MenuItem)object1).getLabel();
/*     */         } else {
/* 526 */           str = actionEvent.getActionCommand();
/*     */         } 
/* 528 */         return new Event(object1, 0L, i, 0, 0, 0, actionEvent.getModifiers(), str);
/*     */       
/*     */       case 701:
/* 531 */         itemEvent = (ItemEvent)this;
/*     */         
/* 533 */         if (object1 instanceof List) {
/* 534 */           i = (itemEvent.getStateChange() == 1) ? 701 : 702;
/*     */           
/* 536 */           object2 = itemEvent.getItem();
/*     */         } else {
/* 538 */           i = 1001;
/* 539 */           if (object1 instanceof Choice) {
/* 540 */             object2 = itemEvent.getItem();
/*     */           } else {
/*     */             
/* 543 */             object2 = Boolean.valueOf((itemEvent.getStateChange() == 1));
/*     */           } 
/*     */         } 
/* 546 */         return new Event(object1, i, object2);
/*     */       
/*     */       case 601:
/* 549 */         adjustmentEvent = (AdjustmentEvent)this;
/* 550 */         switch (adjustmentEvent.getAdjustmentType()) {
/*     */           case 1:
/* 552 */             i = 602;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 574 */             return new Event(object1, i, Integer.valueOf(adjustmentEvent.getValue()));case 2: i = 601; return new Event(object1, i, Integer.valueOf(adjustmentEvent.getValue()));case 4: i = 604; return new Event(object1, i, Integer.valueOf(adjustmentEvent.getValue()));case 3: i = 603; return new Event(object1, i, Integer.valueOf(adjustmentEvent.getValue()));case 5: if (adjustmentEvent.getValueIsAdjusting()) { i = 605; } else { i = 607; }  return new Event(object1, i, Integer.valueOf(adjustmentEvent.getValue()));
/*     */         } 
/*     */         return null;
/*     */     } 
/* 578 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void copyPrivateDataInto(AWTEvent paramAWTEvent) {
/* 588 */     paramAWTEvent.bdata = this.bdata;
/*     */     
/* 590 */     if (this instanceof InputEvent && paramAWTEvent instanceof InputEvent) {
/* 591 */       Field field = get_InputEvent_CanAccessSystemClipboard();
/* 592 */       if (field != null) {
/*     */         try {
/* 594 */           boolean bool = field.getBoolean(this);
/* 595 */           field.setBoolean(paramAWTEvent, bool);
/* 596 */         } catch (IllegalAccessException illegalAccessException) {
/* 597 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 598 */             log.fine("AWTEvent.copyPrivateDataInto() got IllegalAccessException ", illegalAccessException);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 603 */     paramAWTEvent.isSystemGenerated = this.isSystemGenerated;
/*     */   }
/*     */   
/*     */   void dispatched() {
/* 607 */     if (this instanceof InputEvent) {
/* 608 */       Field field = get_InputEvent_CanAccessSystemClipboard();
/* 609 */       if (field != null)
/*     */         try {
/* 611 */           field.setBoolean(this, false);
/* 612 */         } catch (IllegalAccessException illegalAccessException) {
/* 613 */           if (log.isLoggable(PlatformLogger.Level.FINE))
/* 614 */             log.fine("AWTEvent.dispatched() got IllegalAccessException ", illegalAccessException); 
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private native void nativeSetSource(ComponentPeer paramComponentPeer);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/AWTEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */