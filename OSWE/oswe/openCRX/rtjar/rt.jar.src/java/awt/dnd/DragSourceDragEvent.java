/*     */ package java.awt.dnd;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragSourceDragEvent
/*     */   extends DragSourceEvent
/*     */ {
/*     */   private static final long serialVersionUID = 481346297933902471L;
/*     */   private static final int JDK_1_3_MODIFIERS = 63;
/*     */   private static final int JDK_1_4_MODIFIERS = 16320;
/*     */   private int targetActions;
/*     */   private int dropAction;
/*     */   private int gestureModifiers;
/*     */   private boolean invalidModifiers;
/*     */   
/*     */   public DragSourceDragEvent(DragSourceContext paramDragSourceContext, int paramInt1, int paramInt2, int paramInt3) {
/* 114 */     super(paramDragSourceContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     this.targetActions = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     this.dropAction = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     this.gestureModifiers = 0; this.targetActions = paramInt2; this.gestureModifiers = paramInt3; this.dropAction = paramInt1; if ((paramInt3 & 0xFFFFC000) != 0) { this.invalidModifiers = true; } else if (getGestureModifiers() != 0 && getGestureModifiersEx() == 0) { setNewModifiers(); } else if (getGestureModifiers() == 0 && getGestureModifiersEx() != 0) { setOldModifiers(); } else { this.invalidModifiers = true; }  } public DragSourceDragEvent(DragSourceContext paramDragSourceContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) { super(paramDragSourceContext, paramInt4, paramInt5); this.targetActions = 0; this.dropAction = 0; this.gestureModifiers = 0;
/*     */     this.targetActions = paramInt2;
/*     */     this.gestureModifiers = paramInt3;
/*     */     this.dropAction = paramInt1;
/*     */     if ((paramInt3 & 0xFFFFC000) != 0) {
/*     */       this.invalidModifiers = true;
/*     */     } else if (getGestureModifiers() != 0 && getGestureModifiersEx() == 0) {
/*     */       setNewModifiers();
/*     */     } else if (getGestureModifiers() == 0 && getGestureModifiersEx() != 0) {
/*     */       setOldModifiers();
/*     */     } else {
/*     */       this.invalidModifiers = true;
/*     */     }  }
/*     */   public int getTargetActions() { return this.targetActions; }
/*     */   public int getGestureModifiers() { return this.invalidModifiers ? this.gestureModifiers : (this.gestureModifiers & 0x3F); } private void setNewModifiers() {
/* 285 */     if ((this.gestureModifiers & 0x10) != 0) {
/* 286 */       this.gestureModifiers |= 0x400;
/*     */     }
/* 288 */     if ((this.gestureModifiers & 0x8) != 0) {
/* 289 */       this.gestureModifiers |= 0x800;
/*     */     }
/* 291 */     if ((this.gestureModifiers & 0x4) != 0) {
/* 292 */       this.gestureModifiers |= 0x1000;
/*     */     }
/* 294 */     if ((this.gestureModifiers & 0x1) != 0) {
/* 295 */       this.gestureModifiers |= 0x40;
/*     */     }
/* 297 */     if ((this.gestureModifiers & 0x2) != 0) {
/* 298 */       this.gestureModifiers |= 0x80;
/*     */     }
/* 300 */     if ((this.gestureModifiers & 0x20) != 0)
/* 301 */       this.gestureModifiers |= 0x2000; 
/*     */   } public int getGestureModifiersEx() {
/*     */     return this.invalidModifiers ? this.gestureModifiers : (this.gestureModifiers & 0x3FC0);
/*     */   } public int getUserAction() {
/*     */     return this.dropAction;
/*     */   } public int getDropAction() {
/*     */     return this.targetActions & getDragSourceContext().getSourceActions();
/*     */   } private void setOldModifiers() {
/* 309 */     if ((this.gestureModifiers & 0x400) != 0) {
/* 310 */       this.gestureModifiers |= 0x10;
/*     */     }
/* 312 */     if ((this.gestureModifiers & 0x800) != 0) {
/* 313 */       this.gestureModifiers |= 0x8;
/*     */     }
/* 315 */     if ((this.gestureModifiers & 0x1000) != 0) {
/* 316 */       this.gestureModifiers |= 0x4;
/*     */     }
/* 318 */     if ((this.gestureModifiers & 0x40) != 0) {
/* 319 */       this.gestureModifiers |= 0x1;
/*     */     }
/* 321 */     if ((this.gestureModifiers & 0x80) != 0) {
/* 322 */       this.gestureModifiers |= 0x2;
/*     */     }
/* 324 */     if ((this.gestureModifiers & 0x2000) != 0)
/* 325 */       this.gestureModifiers |= 0x20; 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DragSourceDragEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */