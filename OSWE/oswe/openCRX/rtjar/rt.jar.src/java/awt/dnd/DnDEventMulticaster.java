/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.AWTEventMulticaster;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EventListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DnDEventMulticaster
/*     */   extends AWTEventMulticaster
/*     */   implements DragSourceListener, DragSourceMotionListener
/*     */ {
/*     */   protected DnDEventMulticaster(EventListener paramEventListener1, EventListener paramEventListener2) {
/*  56 */     super(paramEventListener1, paramEventListener2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragEnter(DragSourceDragEvent paramDragSourceDragEvent) {
/*  66 */     ((DragSourceListener)this.a).dragEnter(paramDragSourceDragEvent);
/*  67 */     ((DragSourceListener)this.b).dragEnter(paramDragSourceDragEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragOver(DragSourceDragEvent paramDragSourceDragEvent) {
/*  77 */     ((DragSourceListener)this.a).dragOver(paramDragSourceDragEvent);
/*  78 */     ((DragSourceListener)this.b).dragOver(paramDragSourceDragEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropActionChanged(DragSourceDragEvent paramDragSourceDragEvent) {
/*  88 */     ((DragSourceListener)this.a).dropActionChanged(paramDragSourceDragEvent);
/*  89 */     ((DragSourceListener)this.b).dropActionChanged(paramDragSourceDragEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragExit(DragSourceEvent paramDragSourceEvent) {
/*  99 */     ((DragSourceListener)this.a).dragExit(paramDragSourceEvent);
/* 100 */     ((DragSourceListener)this.b).dragExit(paramDragSourceEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragDropEnd(DragSourceDropEvent paramDragSourceDropEvent) {
/* 110 */     ((DragSourceListener)this.a).dragDropEnd(paramDragSourceDropEvent);
/* 111 */     ((DragSourceListener)this.b).dragDropEnd(paramDragSourceDropEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragMouseMoved(DragSourceDragEvent paramDragSourceDragEvent) {
/* 121 */     ((DragSourceMotionListener)this.a).dragMouseMoved(paramDragSourceDragEvent);
/* 122 */     ((DragSourceMotionListener)this.b).dragMouseMoved(paramDragSourceDragEvent);
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
/*     */   public static DragSourceListener add(DragSourceListener paramDragSourceListener1, DragSourceListener paramDragSourceListener2) {
/* 134 */     return (DragSourceListener)addInternal(paramDragSourceListener1, paramDragSourceListener2);
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
/*     */   public static DragSourceMotionListener add(DragSourceMotionListener paramDragSourceMotionListener1, DragSourceMotionListener paramDragSourceMotionListener2) {
/* 146 */     return (DragSourceMotionListener)addInternal(paramDragSourceMotionListener1, paramDragSourceMotionListener2);
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
/*     */   public static DragSourceListener remove(DragSourceListener paramDragSourceListener1, DragSourceListener paramDragSourceListener2) {
/* 158 */     return (DragSourceListener)removeInternal(paramDragSourceListener1, paramDragSourceListener2);
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
/*     */   public static DragSourceMotionListener remove(DragSourceMotionListener paramDragSourceMotionListener1, DragSourceMotionListener paramDragSourceMotionListener2) {
/* 171 */     return (DragSourceMotionListener)removeInternal(paramDragSourceMotionListener1, paramDragSourceMotionListener2);
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
/*     */   protected static EventListener addInternal(EventListener paramEventListener1, EventListener paramEventListener2) {
/* 185 */     if (paramEventListener1 == null) return paramEventListener2; 
/* 186 */     if (paramEventListener2 == null) return paramEventListener1; 
/* 187 */     return new DnDEventMulticaster(paramEventListener1, paramEventListener2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EventListener remove(EventListener paramEventListener) {
/* 196 */     if (paramEventListener == this.a) return this.b; 
/* 197 */     if (paramEventListener == this.b) return this.a; 
/* 198 */     EventListener eventListener1 = removeInternal(this.a, paramEventListener);
/* 199 */     EventListener eventListener2 = removeInternal(this.b, paramEventListener);
/* 200 */     if (eventListener1 == this.a && eventListener2 == this.b) {
/* 201 */       return this;
/*     */     }
/* 203 */     return addInternal(eventListener1, eventListener2);
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
/*     */   protected static EventListener removeInternal(EventListener paramEventListener1, EventListener paramEventListener2) {
/* 218 */     if (paramEventListener1 == paramEventListener2 || paramEventListener1 == null)
/* 219 */       return null; 
/* 220 */     if (paramEventListener1 instanceof DnDEventMulticaster) {
/* 221 */       return ((DnDEventMulticaster)paramEventListener1).remove(paramEventListener2);
/*     */     }
/* 223 */     return paramEventListener1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void save(ObjectOutputStream paramObjectOutputStream, String paramString, EventListener paramEventListener) throws IOException {
/* 229 */     AWTEventMulticaster.save(paramObjectOutputStream, paramString, paramEventListener);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DnDEventMulticaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */