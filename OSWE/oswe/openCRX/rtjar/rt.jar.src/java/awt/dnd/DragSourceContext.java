/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.awt.dnd.peer.DragSourceContextPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.TooManyListenersException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragSourceContext
/*     */   implements DragSourceListener, DragSourceMotionListener, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -115407898692194719L;
/*     */   protected static final int DEFAULT = 0;
/*     */   protected static final int ENTER = 1;
/*     */   protected static final int OVER = 2;
/*     */   protected static final int CHANGED = 3;
/*     */   private static Transferable emptyTransferable;
/*     */   private transient DragSourceContextPeer peer;
/*     */   private DragGestureEvent trigger;
/*     */   private Cursor cursor;
/*     */   private transient Transferable transferable;
/*     */   private transient DragSourceListener listener;
/*     */   private boolean useCustomCursor;
/*     */   private int sourceActions;
/*     */   
/*     */   public DragSourceContext(DragSourceContextPeer paramDragSourceContextPeer, DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Image paramImage, Point paramPoint, Transferable paramTransferable, DragSourceListener paramDragSourceListener) {
/* 186 */     if (paramDragSourceContextPeer == null) {
/* 187 */       throw new NullPointerException("DragSourceContextPeer");
/*     */     }
/*     */     
/* 190 */     if (paramDragGestureEvent == null) {
/* 191 */       throw new NullPointerException("Trigger");
/*     */     }
/*     */     
/* 194 */     if (paramDragGestureEvent.getDragSource() == null) {
/* 195 */       throw new IllegalArgumentException("DragSource");
/*     */     }
/*     */     
/* 198 */     if (paramDragGestureEvent.getComponent() == null) {
/* 199 */       throw new IllegalArgumentException("Component");
/*     */     }
/*     */     
/* 202 */     if (paramDragGestureEvent.getSourceAsDragGestureRecognizer().getSourceActions() == 0)
/*     */     {
/* 204 */       throw new IllegalArgumentException("source actions");
/*     */     }
/*     */     
/* 207 */     if (paramDragGestureEvent.getDragAction() == 0) {
/* 208 */       throw new IllegalArgumentException("no drag action");
/*     */     }
/*     */     
/* 211 */     if (paramTransferable == null) {
/* 212 */       throw new NullPointerException("Transferable");
/*     */     }
/*     */     
/* 215 */     if (paramImage != null && paramPoint == null) {
/* 216 */       throw new NullPointerException("offset");
/*     */     }
/*     */     
/* 219 */     this.peer = paramDragSourceContextPeer;
/* 220 */     this.trigger = paramDragGestureEvent;
/* 221 */     this.cursor = paramCursor;
/* 222 */     this.transferable = paramTransferable;
/* 223 */     this.listener = paramDragSourceListener;
/* 224 */     this
/* 225 */       .sourceActions = paramDragGestureEvent.getSourceAsDragGestureRecognizer().getSourceActions();
/*     */     
/* 227 */     this.useCustomCursor = (paramCursor != null);
/*     */     
/* 229 */     updateCurrentCursor(paramDragGestureEvent.getDragAction(), getSourceActions(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DragSource getDragSource() {
/* 240 */     return this.trigger.getDragSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponent() {
/* 249 */     return this.trigger.getComponent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DragGestureEvent getTrigger() {
/* 258 */     return this.trigger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceActions() {
/* 268 */     return this.sourceActions;
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
/*     */   public synchronized void setCursor(Cursor paramCursor) {
/* 286 */     this.useCustomCursor = (paramCursor != null);
/* 287 */     setCursorImpl(paramCursor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cursor getCursor() {
/* 296 */     return this.cursor;
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
/*     */   public synchronized void addDragSourceListener(DragSourceListener paramDragSourceListener) throws TooManyListenersException {
/* 313 */     if (paramDragSourceListener == null)
/*     */       return; 
/* 315 */     if (equals(paramDragSourceListener)) throw new IllegalArgumentException("DragSourceContext may not be its own listener");
/*     */     
/* 317 */     if (this.listener != null) {
/* 318 */       throw new TooManyListenersException();
/*     */     }
/* 320 */     this.listener = paramDragSourceListener;
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
/*     */   public synchronized void removeDragSourceListener(DragSourceListener paramDragSourceListener) {
/* 333 */     if (this.listener != null && this.listener.equals(paramDragSourceListener)) {
/* 334 */       this.listener = null;
/*     */     } else {
/* 336 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transferablesFlavorsChanged() {
/* 345 */     if (this.peer != null) this.peer.transferablesFlavorsChanged();
/*     */   
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
/*     */   public void dragEnter(DragSourceDragEvent paramDragSourceDragEvent) {
/* 358 */     DragSourceListener dragSourceListener = this.listener;
/* 359 */     if (dragSourceListener != null) {
/* 360 */       dragSourceListener.dragEnter(paramDragSourceDragEvent);
/*     */     }
/* 362 */     getDragSource().processDragEnter(paramDragSourceDragEvent);
/*     */     
/* 364 */     updateCurrentCursor(getSourceActions(), paramDragSourceDragEvent.getTargetActions(), 1);
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
/*     */   public void dragOver(DragSourceDragEvent paramDragSourceDragEvent) {
/* 377 */     DragSourceListener dragSourceListener = this.listener;
/* 378 */     if (dragSourceListener != null) {
/* 379 */       dragSourceListener.dragOver(paramDragSourceDragEvent);
/*     */     }
/* 381 */     getDragSource().processDragOver(paramDragSourceDragEvent);
/*     */     
/* 383 */     updateCurrentCursor(getSourceActions(), paramDragSourceDragEvent.getTargetActions(), 2);
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
/*     */   public void dragExit(DragSourceEvent paramDragSourceEvent) {
/* 396 */     DragSourceListener dragSourceListener = this.listener;
/* 397 */     if (dragSourceListener != null) {
/* 398 */       dragSourceListener.dragExit(paramDragSourceEvent);
/*     */     }
/* 400 */     getDragSource().processDragExit(paramDragSourceEvent);
/*     */     
/* 402 */     updateCurrentCursor(0, 0, 0);
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
/*     */   public void dropActionChanged(DragSourceDragEvent paramDragSourceDragEvent) {
/* 415 */     DragSourceListener dragSourceListener = this.listener;
/* 416 */     if (dragSourceListener != null) {
/* 417 */       dragSourceListener.dropActionChanged(paramDragSourceDragEvent);
/*     */     }
/* 419 */     getDragSource().processDropActionChanged(paramDragSourceDragEvent);
/*     */     
/* 421 */     updateCurrentCursor(getSourceActions(), paramDragSourceDragEvent.getTargetActions(), 3);
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
/*     */   public void dragDropEnd(DragSourceDropEvent paramDragSourceDropEvent) {
/* 434 */     DragSourceListener dragSourceListener = this.listener;
/* 435 */     if (dragSourceListener != null) {
/* 436 */       dragSourceListener.dragDropEnd(paramDragSourceDropEvent);
/*     */     }
/* 438 */     getDragSource().processDragDropEnd(paramDragSourceDropEvent);
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
/*     */   public void dragMouseMoved(DragSourceDragEvent paramDragSourceDragEvent) {
/* 452 */     getDragSource().processDragMouseMoved(paramDragSourceDragEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transferable getTransferable() {
/* 461 */     return this.transferable;
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
/*     */   protected synchronized void updateCurrentCursor(int paramInt1, int paramInt2, int paramInt3) {
/* 481 */     if (this.useCustomCursor) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 487 */     Cursor cursor = null;
/*     */     
/* 489 */     switch (paramInt3)
/*     */     { default:
/* 491 */         paramInt2 = 0; break;
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/* 495 */         break; }  int i = paramInt1 & paramInt2;
/*     */     
/* 497 */     if (i == 0) {
/* 498 */       if ((paramInt1 & 0x40000000) == 1073741824) {
/* 499 */         cursor = DragSource.DefaultLinkNoDrop;
/* 500 */       } else if ((paramInt1 & 0x2) == 2) {
/* 501 */         cursor = DragSource.DefaultMoveNoDrop;
/*     */       } else {
/* 503 */         cursor = DragSource.DefaultCopyNoDrop;
/*     */       } 
/* 505 */     } else if ((i & 0x40000000) == 1073741824) {
/* 506 */       cursor = DragSource.DefaultLinkDrop;
/* 507 */     } else if ((i & 0x2) == 2) {
/* 508 */       cursor = DragSource.DefaultMoveDrop;
/*     */     } else {
/* 510 */       cursor = DragSource.DefaultCopyDrop;
/*     */     } 
/*     */ 
/*     */     
/* 514 */     setCursorImpl(cursor);
/*     */   }
/*     */   
/*     */   private void setCursorImpl(Cursor paramCursor) {
/* 518 */     if (this.cursor == null || !this.cursor.equals(paramCursor)) {
/* 519 */       this.cursor = paramCursor;
/* 520 */       if (this.peer != null) this.peer.setCursor(this.cursor);
/*     */     
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 543 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 545 */     paramObjectOutputStream.writeObject(SerializationTester.test(this.transferable) ? this.transferable : null);
/*     */     
/* 547 */     paramObjectOutputStream.writeObject(SerializationTester.test(this.listener) ? this.listener : null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 566 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */     
/* 568 */     DragGestureEvent dragGestureEvent = (DragGestureEvent)getField.get("trigger", (Object)null);
/* 569 */     if (dragGestureEvent == null) {
/* 570 */       throw new InvalidObjectException("Null trigger");
/*     */     }
/* 572 */     if (dragGestureEvent.getDragSource() == null) {
/* 573 */       throw new InvalidObjectException("Null DragSource");
/*     */     }
/* 575 */     if (dragGestureEvent.getComponent() == null) {
/* 576 */       throw new InvalidObjectException("Null trigger component");
/*     */     }
/*     */     
/* 579 */     int i = getField.get("sourceActions", 0) & 0x40000003;
/*     */     
/* 581 */     if (i == 0) {
/* 582 */       throw new InvalidObjectException("Invalid source actions");
/*     */     }
/* 584 */     int j = dragGestureEvent.getDragAction();
/* 585 */     if (j != 1 && j != 2 && j != 1073741824)
/*     */     {
/*     */       
/* 588 */       throw new InvalidObjectException("No drag action");
/*     */     }
/* 590 */     this.trigger = dragGestureEvent;
/*     */     
/* 592 */     this.cursor = (Cursor)getField.get("cursor", (Object)null);
/* 593 */     this.useCustomCursor = getField.get("useCustomCursor", false);
/* 594 */     this.sourceActions = i;
/*     */     
/* 596 */     this.transferable = (Transferable)paramObjectInputStream.readObject();
/* 597 */     this.listener = (DragSourceListener)paramObjectInputStream.readObject();
/*     */ 
/*     */     
/* 600 */     if (this.transferable == null) {
/* 601 */       if (emptyTransferable == null) {
/* 602 */         emptyTransferable = new Transferable() {
/*     */             public DataFlavor[] getTransferDataFlavors() {
/* 604 */               return new DataFlavor[0];
/*     */             }
/*     */             
/*     */             public boolean isDataFlavorSupported(DataFlavor param1DataFlavor) {
/* 608 */               return false;
/*     */             }
/*     */ 
/*     */             
/*     */             public Object getTransferData(DataFlavor param1DataFlavor) throws UnsupportedFlavorException {
/* 613 */               throw new UnsupportedFlavorException(param1DataFlavor);
/*     */             }
/*     */           };
/*     */       }
/* 617 */       this.transferable = emptyTransferable;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DragSourceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */