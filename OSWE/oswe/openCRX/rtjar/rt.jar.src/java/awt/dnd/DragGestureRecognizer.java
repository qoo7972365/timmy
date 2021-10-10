/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
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
/*     */ public abstract class DragGestureRecognizer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8996673345831063337L;
/*     */   protected DragSource dragSource;
/*     */   protected Component component;
/*     */   protected transient DragGestureListener dragGestureListener;
/*     */   protected int sourceActions;
/*     */   
/*     */   protected DragGestureRecognizer(DragSource paramDragSource, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener) {
/* 126 */     if (paramDragSource == null) throw new IllegalArgumentException("null DragSource");
/*     */     
/* 128 */     this.dragSource = paramDragSource;
/* 129 */     this.component = paramComponent;
/* 130 */     this.sourceActions = paramInt & 0x40000003;
/*     */     
/*     */     try {
/* 133 */       if (paramDragGestureListener != null) addDragGestureListener(paramDragGestureListener); 
/* 134 */     } catch (TooManyListenersException tooManyListenersException) {}
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
/*     */   protected DragGestureRecognizer(DragSource paramDragSource, Component paramComponent, int paramInt) {
/* 167 */     this(paramDragSource, paramComponent, paramInt, null);
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
/*     */   protected DragGestureRecognizer(DragSource paramDragSource, Component paramComponent) {
/* 195 */     this(paramDragSource, paramComponent, 0);
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
/*     */   protected DragGestureRecognizer(DragSource paramDragSource) {
/* 212 */     this(paramDragSource, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void registerListeners();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void unregisterListeners();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DragSource getDragSource() {
/* 240 */     return this.dragSource;
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
/*     */   public synchronized Component getComponent() {
/* 252 */     return this.component;
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
/*     */   public synchronized void setComponent(Component paramComponent) {
/* 264 */     if (this.component != null && this.dragGestureListener != null) {
/* 265 */       unregisterListeners();
/*     */     }
/* 267 */     this.component = paramComponent;
/*     */     
/* 269 */     if (this.component != null && this.dragGestureListener != null) {
/* 270 */       registerListeners();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getSourceActions() {
/* 281 */     return this.sourceActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSourceActions(int paramInt) {
/* 291 */     this.sourceActions = paramInt & 0x40000003;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputEvent getTriggerEvent() {
/* 302 */     return this.events.isEmpty() ? null : this.events.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetRecognizer() {
/* 309 */     this.events.clear();
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
/*     */   public synchronized void addDragGestureListener(DragGestureListener paramDragGestureListener) throws TooManyListenersException {
/* 322 */     if (this.dragGestureListener != null) {
/* 323 */       throw new TooManyListenersException();
/*     */     }
/* 325 */     this.dragGestureListener = paramDragGestureListener;
/*     */     
/* 327 */     if (this.component != null) registerListeners();
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
/*     */ 
/*     */   
/*     */   public synchronized void removeDragGestureListener(DragGestureListener paramDragGestureListener) {
/* 342 */     if (this.dragGestureListener == null || !this.dragGestureListener.equals(paramDragGestureListener)) {
/* 343 */       throw new IllegalArgumentException();
/*     */     }
/* 345 */     this.dragGestureListener = null;
/*     */     
/* 347 */     if (this.component != null) unregisterListeners();
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
/*     */   protected synchronized void fireDragGestureRecognized(int paramInt, Point paramPoint) {
/*     */     try {
/* 360 */       if (this.dragGestureListener != null) {
/* 361 */         this.dragGestureListener.dragGestureRecognized(new DragGestureEvent(this, paramInt, paramPoint, this.events));
/*     */       }
/*     */     } finally {
/* 364 */       this.events.clear();
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
/*     */   
/*     */   protected synchronized void appendEvent(InputEvent paramInputEvent) {
/* 387 */     this.events.add(paramInputEvent);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 402 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 404 */     paramObjectOutputStream.writeObject(SerializationTester.test(this.dragGestureListener) ? this.dragGestureListener : null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 420 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */     
/* 422 */     DragSource dragSource = (DragSource)getField.get("dragSource", (Object)null);
/* 423 */     if (dragSource == null) {
/* 424 */       throw new InvalidObjectException("null DragSource");
/*     */     }
/* 426 */     this.dragSource = dragSource;
/*     */     
/* 428 */     this.component = (Component)getField.get("component", (Object)null);
/* 429 */     this.sourceActions = getField.get("sourceActions", 0) & 0x40000003;
/* 430 */     this.events = (ArrayList<InputEvent>)getField.get("events", new ArrayList(1));
/*     */     
/* 432 */     this.dragGestureListener = (DragGestureListener)paramObjectInputStream.readObject();
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
/* 478 */   protected ArrayList<InputEvent> events = new ArrayList<>(1);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DragGestureRecognizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */