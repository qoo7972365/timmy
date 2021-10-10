/*    */ package javax.swing;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.event.PaintEvent;
/*    */ import java.security.AccessController;
/*    */ import sun.awt.AppContext;
/*    */ import sun.awt.PaintEventDispatcher;
/*    */ import sun.awt.SunToolkit;
/*    */ import sun.awt.event.IgnorePaintEvent;
/*    */ import sun.security.action.GetBooleanAction;
/*    */ import sun.security.action.GetPropertyAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SwingPaintEventDispatcher
/*    */   extends PaintEventDispatcher
/*    */ {
/* 49 */   private static final boolean SHOW_FROM_DOUBLE_BUFFER = "true".equals(AccessController.doPrivileged(new GetPropertyAction("swing.showFromDoubleBuffer", "true")));
/*    */   
/* 51 */   private static final boolean ERASE_BACKGROUND = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("swing.nativeErase"))).booleanValue();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PaintEvent createPaintEvent(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 57 */     if (paramComponent instanceof RootPaneContainer) {
/* 58 */       AppContext appContext = SunToolkit.targetToAppContext(paramComponent);
/* 59 */       RepaintManager repaintManager = RepaintManager.currentManager(appContext);
/* 60 */       if (!SHOW_FROM_DOUBLE_BUFFER || 
/* 61 */         !repaintManager.show((Container)paramComponent, paramInt1, paramInt2, paramInt3, paramInt4)) {
/* 62 */         repaintManager.nativeAddDirtyRegion(appContext, (Container)paramComponent, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */       }
/*    */ 
/*    */ 
/*    */       
/* 67 */       return new IgnorePaintEvent(paramComponent, 800, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*    */     } 
/*    */     
/* 70 */     if (paramComponent instanceof SwingHeavyWeight) {
/* 71 */       AppContext appContext = SunToolkit.targetToAppContext(paramComponent);
/* 72 */       RepaintManager repaintManager = RepaintManager.currentManager(appContext);
/* 73 */       repaintManager.nativeAddDirtyRegion(appContext, (Container)paramComponent, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */       
/* 75 */       return new IgnorePaintEvent(paramComponent, 800, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*    */     } 
/*    */     
/* 78 */     return super.createPaintEvent(paramComponent, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */   
/*    */   public boolean shouldDoNativeBackgroundErase(Component paramComponent) {
/* 82 */     return (ERASE_BACKGROUND || !(paramComponent instanceof RootPaneContainer));
/*    */   }
/*    */   
/*    */   public boolean queueSurfaceDataReplacing(Component paramComponent, Runnable paramRunnable) {
/* 86 */     if (paramComponent instanceof RootPaneContainer) {
/* 87 */       AppContext appContext = SunToolkit.targetToAppContext(paramComponent);
/* 88 */       RepaintManager.currentManager(appContext)
/* 89 */         .nativeQueueSurfaceDataRunnable(appContext, paramComponent, paramRunnable);
/* 90 */       return true;
/*    */     } 
/* 92 */     return super.queueSurfaceDataReplacing(paramComponent, paramRunnable);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/SwingPaintEventDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */