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
/*     */ public class DragSourceDropEvent
/*     */   extends DragSourceEvent
/*     */ {
/*     */   private static final long serialVersionUID = -5571321229470821891L;
/*     */   private boolean dropSuccess;
/*     */   private int dropAction;
/*     */   
/*     */   public DragSourceDropEvent(DragSourceContext paramDragSourceContext, int paramInt, boolean paramBoolean) {
/*  71 */     super(paramDragSourceContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     this.dropAction = 0; this.dropSuccess = paramBoolean; this.dropAction = paramInt; } public DragSourceDropEvent(DragSourceContext paramDragSourceContext, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3) { super(paramDragSourceContext, paramInt2, paramInt3); this.dropAction = 0; this.dropSuccess = paramBoolean; this.dropAction = paramInt1; } public DragSourceDropEvent(DragSourceContext paramDragSourceContext) { super(paramDragSourceContext); this.dropAction = 0;
/*     */     this.dropSuccess = false; }
/*     */ 
/*     */   
/*     */   public boolean getDropSuccess() {
/*     */     return this.dropSuccess;
/*     */   }
/*     */   
/*     */   public int getDropAction() {
/*     */     return this.dropAction;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DragSourceDropEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */