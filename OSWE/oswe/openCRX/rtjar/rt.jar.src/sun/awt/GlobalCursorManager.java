/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.IllegalComponentStateException;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.InvocationEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GlobalCursorManager
/*     */ {
/*     */   class NativeUpdater
/*     */     implements Runnable
/*     */   {
/*     */     boolean pending = false;
/*     */     
/*     */     public void run() {
/*  44 */       boolean bool = false;
/*  45 */       synchronized (this) {
/*  46 */         if (this.pending) {
/*  47 */           this.pending = false;
/*  48 */           bool = true;
/*     */         } 
/*     */       } 
/*  51 */       if (bool) {
/*  52 */         GlobalCursorManager.this._updateCursor(false);
/*     */       }
/*     */     }
/*     */     
/*     */     public void postIfNotPending(Component param1Component, InvocationEvent param1InvocationEvent) {
/*  57 */       boolean bool = false;
/*  58 */       synchronized (this) {
/*  59 */         if (!this.pending) {
/*  60 */           this.pending = bool = true;
/*     */         }
/*     */       } 
/*  63 */       if (bool) {
/*  64 */         SunToolkit.postEvent(SunToolkit.targetToAppContext(param1Component), param1InvocationEvent);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final NativeUpdater nativeUpdater = new NativeUpdater();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastUpdateMillis;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private final Object lastUpdateLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCursorImmediately() {
/*  92 */     synchronized (this.nativeUpdater) {
/*  93 */       this.nativeUpdater.pending = false;
/*     */     } 
/*  95 */     _updateCursor(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCursorImmediately(InputEvent paramInputEvent) {
/*     */     boolean bool;
/* 106 */     synchronized (this.lastUpdateLock) {
/* 107 */       bool = (paramInputEvent.getWhen() >= this.lastUpdateMillis) ? true : false;
/*     */     } 
/* 109 */     if (bool) {
/* 110 */       _updateCursor(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCursorLater(Component paramComponent) {
/* 119 */     this.nativeUpdater.postIfNotPending(paramComponent, new InvocationEvent(
/* 120 */           Toolkit.getDefaultToolkit(), this.nativeUpdater));
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
/*     */   private void _updateCursor(boolean paramBoolean) {
/* 170 */     synchronized (this.lastUpdateLock) {
/* 171 */       this.lastUpdateMillis = System.currentTimeMillis();
/*     */     } 
/*     */     
/* 174 */     Point point1 = null, point2 = null;
/*     */ 
/*     */     
/*     */     try {
/* 178 */       Component component = findHeavyweightUnderCursor(paramBoolean);
/* 179 */       if (component == null) {
/* 180 */         updateCursorOutOfJava();
/*     */         
/*     */         return;
/*     */       } 
/* 184 */       if (component instanceof java.awt.Window) {
/* 185 */         point2 = AWTAccessor.getComponentAccessor().getLocation(component);
/* 186 */       } else if (component instanceof Container) {
/* 187 */         point2 = getLocationOnScreen(component);
/*     */       } 
/* 189 */       if (point2 != null) {
/* 190 */         point1 = new Point();
/* 191 */         getCursorPos(point1);
/*     */         
/* 193 */         Component component1 = AWTAccessor.getContainerAccessor().findComponentAt((Container)component, point1.x - point2.x, point1.y - point2.y, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 200 */         if (component1 != null) {
/* 201 */           component = component1;
/*     */         }
/*     */       } 
/*     */       
/* 205 */       setCursor(component, AWTAccessor.getComponentAccessor().getCursor(component), paramBoolean);
/*     */     }
/* 207 */     catch (IllegalComponentStateException illegalComponentStateException) {}
/*     */   }
/*     */   
/*     */   protected void updateCursorOutOfJava() {}
/*     */   
/*     */   protected abstract void setCursor(Component paramComponent, Cursor paramCursor, boolean paramBoolean);
/*     */   
/*     */   protected abstract void getCursorPos(Point paramPoint);
/*     */   
/*     */   protected abstract Point getLocationOnScreen(Component paramComponent);
/*     */   
/*     */   protected abstract Component findHeavyweightUnderCursor(boolean paramBoolean);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/GlobalCursorManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */