/*     */ package sun.awt.dnd;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.dnd.DragGestureEvent;
/*     */ import java.awt.dnd.DragSourceContext;
/*     */ import java.awt.dnd.DragSourceDragEvent;
/*     */ import java.awt.dnd.DragSourceDropEvent;
/*     */ import java.awt.dnd.DragSourceEvent;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.awt.dnd.peer.DragSourceContextPeer;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.datatransfer.DataTransferer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SunDragSourceContextPeer
/*     */   implements DragSourceContextPeer
/*     */ {
/*     */   private DragGestureEvent trigger;
/*     */   private Component component;
/*     */   private Cursor cursor;
/*     */   private Image dragImage;
/*     */   private Point dragImageOffset;
/*     */   private long nativeCtxt;
/*     */   private DragSourceContext dragSourceContext;
/*     */   private int sourceActions;
/*     */   private static boolean dragDropInProgress = false;
/*     */   private static boolean discardingMouseEvents = false;
/*     */   protected static final int DISPATCH_ENTER = 1;
/*     */   protected static final int DISPATCH_MOTION = 2;
/*     */   protected static final int DISPATCH_CHANGED = 3;
/*     */   protected static final int DISPATCH_EXIT = 4;
/*     */   protected static final int DISPATCH_FINISH = 5;
/*     */   protected static final int DISPATCH_MOUSE_MOVED = 6;
/*     */   
/*     */   public SunDragSourceContextPeer(DragGestureEvent paramDragGestureEvent) {
/*  96 */     this.trigger = paramDragGestureEvent;
/*  97 */     if (this.trigger != null) {
/*  98 */       this.component = this.trigger.getComponent();
/*     */     } else {
/* 100 */       this.component = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startSecondaryEventLoop() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void quitSecondaryEventLoop() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDrag(DragSourceContext paramDragSourceContext, Cursor paramCursor, Image paramImage, Point paramPoint) throws InvalidDnDOperationException {
/* 119 */     if (getTrigger().getTriggerEvent() == null) {
/* 120 */       throw new InvalidDnDOperationException("DragGestureEvent has a null trigger");
/*     */     }
/*     */     
/* 123 */     this.dragSourceContext = paramDragSourceContext;
/* 124 */     this.cursor = paramCursor;
/* 125 */     this.sourceActions = getDragSourceContext().getSourceActions();
/* 126 */     this.dragImage = paramImage;
/* 127 */     this.dragImageOffset = paramPoint;
/*     */     
/* 129 */     Transferable transferable = getDragSourceContext().getTransferable();
/*     */     
/* 131 */     SortedMap<Long, DataFlavor> sortedMap = DataTransferer.getInstance().getFormatsForTransferable(transferable, 
/* 132 */         DataTransferer.adaptFlavorMap(getTrigger().getDragSource().getFlavorMap()));
/* 133 */     DataTransferer.getInstance();
/* 134 */     long[] arrayOfLong = DataTransferer.keysToLongArray(sortedMap);
/* 135 */     startDrag(transferable, arrayOfLong, sortedMap);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     discardingMouseEvents = true;
/* 142 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 144 */             SunDragSourceContextPeer.discardingMouseEvents = false;
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
/*     */   public void setCursor(Cursor paramCursor) throws InvalidDnDOperationException {
/* 157 */     synchronized (this) {
/* 158 */       if (this.cursor == null || !this.cursor.equals(paramCursor)) {
/* 159 */         this.cursor = paramCursor;
/*     */ 
/*     */         
/* 162 */         setNativeCursor(getNativeContext(), paramCursor, (paramCursor != null) ? paramCursor
/* 163 */             .getType() : 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cursor getCursor() {
/* 173 */     return this.cursor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getDragImage() {
/* 183 */     return this.dragImage;
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
/*     */   public Point getDragImageOffset() {
/* 195 */     if (this.dragImageOffset == null) {
/* 196 */       return new Point(0, 0);
/*     */     }
/* 198 */     return new Point(this.dragImageOffset);
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
/*     */   protected synchronized void setTrigger(DragGestureEvent paramDragGestureEvent) {
/* 210 */     this.trigger = paramDragGestureEvent;
/* 211 */     if (this.trigger != null) {
/* 212 */       this.component = this.trigger.getComponent();
/*     */     } else {
/* 214 */       this.component = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected DragGestureEvent getTrigger() {
/* 219 */     return this.trigger;
/*     */   }
/*     */   
/*     */   protected Component getComponent() {
/* 223 */     return this.component;
/*     */   }
/*     */   
/*     */   protected synchronized void setNativeContext(long paramLong) {
/* 227 */     this.nativeCtxt = paramLong;
/*     */   }
/*     */   
/*     */   protected synchronized long getNativeContext() {
/* 231 */     return this.nativeCtxt;
/*     */   }
/*     */   
/*     */   protected DragSourceContext getDragSourceContext() {
/* 235 */     return this.dragSourceContext;
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
/*     */   public void transferablesFlavorsChanged() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void postDragSourceDragEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 258 */     int i = convertModifiersToDropAction(paramInt2, this.sourceActions);
/*     */ 
/*     */ 
/*     */     
/* 262 */     DragSourceDragEvent dragSourceDragEvent = new DragSourceDragEvent(getDragSourceContext(), i, paramInt1 & this.sourceActions, paramInt2, paramInt3, paramInt4);
/*     */ 
/*     */ 
/*     */     
/* 266 */     EventDispatcher eventDispatcher = new EventDispatcher(paramInt5, dragSourceDragEvent);
/*     */     
/* 268 */     SunToolkit.invokeLaterOnAppContext(
/* 269 */         SunToolkit.targetToAppContext(getComponent()), eventDispatcher);
/*     */     
/* 271 */     startSecondaryEventLoop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dragEnter(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 281 */     postDragSourceDragEvent(paramInt1, paramInt2, paramInt3, paramInt4, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dragMotion(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 291 */     postDragSourceDragEvent(paramInt1, paramInt2, paramInt3, paramInt4, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void operationChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 301 */     postDragSourceDragEvent(paramInt1, paramInt2, paramInt3, paramInt4, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void dragExit(int paramInt1, int paramInt2) {
/* 310 */     DragSourceEvent dragSourceEvent = new DragSourceEvent(getDragSourceContext(), paramInt1, paramInt2);
/* 311 */     EventDispatcher eventDispatcher = new EventDispatcher(4, dragSourceEvent);
/*     */ 
/*     */     
/* 314 */     SunToolkit.invokeLaterOnAppContext(
/* 315 */         SunToolkit.targetToAppContext(getComponent()), eventDispatcher);
/*     */     
/* 317 */     startSecondaryEventLoop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dragMouseMoved(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 327 */     postDragSourceDragEvent(paramInt1, paramInt2, paramInt3, paramInt4, 6);
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
/*     */   protected final void dragDropFinished(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) {
/* 339 */     DragSourceDropEvent dragSourceDropEvent = new DragSourceDropEvent(getDragSourceContext(), paramInt1 & this.sourceActions, paramBoolean, paramInt2, paramInt3);
/*     */ 
/*     */     
/* 342 */     EventDispatcher eventDispatcher = new EventDispatcher(5, dragSourceDropEvent);
/*     */ 
/*     */     
/* 345 */     SunToolkit.invokeLaterOnAppContext(
/* 346 */         SunToolkit.targetToAppContext(getComponent()), eventDispatcher);
/*     */     
/* 348 */     startSecondaryEventLoop();
/* 349 */     setNativeContext(0L);
/* 350 */     this.dragImage = null;
/* 351 */     this.dragImageOffset = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setDragDropInProgress(boolean paramBoolean) throws InvalidDnDOperationException {
/* 356 */     synchronized (SunDragSourceContextPeer.class) {
/* 357 */       if (dragDropInProgress == paramBoolean) {
/* 358 */         throw new InvalidDnDOperationException(getExceptionMessage(paramBoolean));
/*     */       }
/* 360 */       dragDropInProgress = paramBoolean;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkEvent(AWTEvent paramAWTEvent) {
/* 369 */     if (discardingMouseEvents && paramAWTEvent instanceof MouseEvent) {
/* 370 */       MouseEvent mouseEvent = (MouseEvent)paramAWTEvent;
/* 371 */       if (!(mouseEvent instanceof SunDropTargetEvent)) {
/* 372 */         return false;
/*     */       }
/*     */     } 
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void checkDragDropInProgress() throws InvalidDnDOperationException {
/* 380 */     if (dragDropInProgress) {
/* 381 */       throw new InvalidDnDOperationException(getExceptionMessage(true));
/*     */     }
/*     */   }
/*     */   
/*     */   private static String getExceptionMessage(boolean paramBoolean) {
/* 386 */     return paramBoolean ? "Drag and drop in progress" : "No drag in progress";
/*     */   }
/*     */ 
/*     */   
/*     */   public static int convertModifiersToDropAction(int paramInt1, int paramInt2) {
/* 391 */     int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 404 */     switch (paramInt1 & 0xC0)
/*     */     
/*     */     { case 192:
/* 407 */         i = 1073741824;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 422 */         return i & paramInt2;case 128: i = 1; return i & paramInt2;case 64: i = 2; return i & paramInt2; }  if ((paramInt2 & 0x2) != 0) { i = 2; } else if ((paramInt2 & 0x1) != 0) { i = 1; } else if ((paramInt2 & 0x40000000) != 0) { i = 1073741824; }  return i & paramInt2;
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 426 */     this.trigger = null;
/* 427 */     this.component = null;
/* 428 */     this.cursor = null;
/* 429 */     this.dragSourceContext = null;
/* 430 */     SunDropTargetContextPeer.setCurrentJVMLocalSourceTransferable(null);
/* 431 */     setDragDropInProgress(false);
/*     */   }
/*     */   protected abstract void startDrag(Transferable paramTransferable, long[] paramArrayOflong, Map paramMap);
/*     */   
/*     */   protected abstract void setNativeCursor(long paramLong, Cursor paramCursor, int paramInt);
/*     */   
/*     */   private class EventDispatcher implements Runnable { private final int dispatchType;
/*     */     private final DragSourceEvent event;
/*     */     
/*     */     EventDispatcher(int param1Int, DragSourceEvent param1DragSourceEvent) {
/* 441 */       switch (param1Int) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 6:
/* 446 */           if (!(param1DragSourceEvent instanceof DragSourceDragEvent)) {
/* 447 */             throw new IllegalArgumentException("Event: " + param1DragSourceEvent);
/*     */           }
/*     */           break;
/*     */         case 4:
/*     */           break;
/*     */         case 5:
/* 453 */           if (!(param1DragSourceEvent instanceof DragSourceDropEvent)) {
/* 454 */             throw new IllegalArgumentException("Event: " + param1DragSourceEvent);
/*     */           }
/*     */           break;
/*     */         default:
/* 458 */           throw new IllegalArgumentException("Dispatch type: " + param1Int);
/*     */       } 
/*     */ 
/*     */       
/* 462 */       this.dispatchType = param1Int;
/* 463 */       this.event = param1DragSourceEvent;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 468 */       DragSourceContext dragSourceContext = SunDragSourceContextPeer.this.getDragSourceContext();
/*     */       try {
/* 470 */         switch (this.dispatchType) {
/*     */           case 1:
/* 472 */             dragSourceContext.dragEnter((DragSourceDragEvent)this.event);
/*     */             break;
/*     */           case 2:
/* 475 */             dragSourceContext.dragOver((DragSourceDragEvent)this.event);
/*     */             break;
/*     */           case 3:
/* 478 */             dragSourceContext.dropActionChanged((DragSourceDragEvent)this.event);
/*     */             break;
/*     */           case 4:
/* 481 */             dragSourceContext.dragExit(this.event);
/*     */             break;
/*     */           case 6:
/* 484 */             dragSourceContext.dragMouseMoved((DragSourceDragEvent)this.event);
/*     */             break;
/*     */           case 5:
/*     */             try {
/* 488 */               dragSourceContext.dragDropEnd((DragSourceDropEvent)this.event);
/*     */             } finally {
/* 490 */               SunDragSourceContextPeer.this.cleanup();
/*     */             } 
/*     */             break;
/*     */           default:
/* 494 */             throw new IllegalStateException("Dispatch type: " + this.dispatchType);
/*     */         } 
/*     */       
/*     */       } finally {
/* 498 */         SunDragSourceContextPeer.this.quitSecondaryEventLoop();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/dnd/SunDragSourceContextPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */