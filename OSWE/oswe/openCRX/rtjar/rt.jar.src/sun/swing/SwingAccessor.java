/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import javax.swing.RepaintManager;
/*     */ import javax.swing.TransferHandler;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SwingAccessor
/*     */ {
/*  44 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JTextComponentAccessor jtextComponentAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JLightweightFrameAccessor jLightweightFrameAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RepaintManagerAccessor repaintManagerAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setJTextComponentAccessor(JTextComponentAccessor paramJTextComponentAccessor) {
/* 102 */     jtextComponentAccessor = paramJTextComponentAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JTextComponentAccessor getJTextComponentAccessor() {
/* 109 */     if (jtextComponentAccessor == null) {
/* 110 */       unsafe.ensureClassInitialized(JTextComponent.class);
/*     */     }
/*     */     
/* 113 */     return jtextComponentAccessor;
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
/*     */   public static void setJLightweightFrameAccessor(JLightweightFrameAccessor paramJLightweightFrameAccessor) {
/* 125 */     jLightweightFrameAccessor = paramJLightweightFrameAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JLightweightFrameAccessor getJLightweightFrameAccessor() {
/* 132 */     if (jLightweightFrameAccessor == null) {
/* 133 */       unsafe.ensureClassInitialized(JLightweightFrame.class);
/*     */     }
/* 135 */     return jLightweightFrameAccessor;
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
/*     */   public static void setRepaintManagerAccessor(RepaintManagerAccessor paramRepaintManagerAccessor) {
/* 147 */     repaintManagerAccessor = paramRepaintManagerAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RepaintManagerAccessor getRepaintManagerAccessor() {
/* 154 */     if (repaintManagerAccessor == null) {
/* 155 */       unsafe.ensureClassInitialized(RepaintManager.class);
/*     */     }
/* 157 */     return repaintManagerAccessor;
/*     */   }
/*     */   
/*     */   public static interface RepaintManagerAccessor {
/*     */     void addRepaintListener(RepaintManager param1RepaintManager, SwingUtilities2.RepaintListener param1RepaintListener);
/*     */     
/*     */     void removeRepaintListener(RepaintManager param1RepaintManager, SwingUtilities2.RepaintListener param1RepaintListener);
/*     */   }
/*     */   
/*     */   public static interface JLightweightFrameAccessor {
/*     */     void updateCursor(JLightweightFrame param1JLightweightFrame);
/*     */   }
/*     */   
/*     */   public static interface JTextComponentAccessor {
/*     */     TransferHandler.DropLocation dropLocationForPoint(JTextComponent param1JTextComponent, Point param1Point);
/*     */     
/*     */     Object setDropLocation(JTextComponent param1JTextComponent, TransferHandler.DropLocation param1DropLocation, Object param1Object, boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/SwingAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */