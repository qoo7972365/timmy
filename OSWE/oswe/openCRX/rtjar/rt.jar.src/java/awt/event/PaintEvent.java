/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PaintEvent
/*     */   extends ComponentEvent
/*     */ {
/*     */   public static final int PAINT_FIRST = 800;
/*     */   public static final int PAINT_LAST = 801;
/*     */   public static final int PAINT = 800;
/*     */   public static final int UPDATE = 801;
/*     */   Rectangle updateRect;
/*     */   private static final long serialVersionUID = 1267492026433337593L;
/*     */   
/*     */   public PaintEvent(Component paramComponent, int paramInt, Rectangle paramRectangle) {
/* 104 */     super(paramComponent, paramInt);
/* 105 */     this.updateRect = paramRectangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getUpdateRect() {
/* 113 */     return this.updateRect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUpdateRect(Rectangle paramRectangle) {
/* 122 */     this.updateRect = paramRectangle;
/*     */   }
/*     */   
/*     */   public String paramString() {
/*     */     String str;
/* 127 */     switch (this.id) {
/*     */       case 800:
/* 129 */         str = "PAINT";
/*     */         break;
/*     */       case 801:
/* 132 */         str = "UPDATE";
/*     */         break;
/*     */       default:
/* 135 */         str = "unknown type"; break;
/*     */     } 
/* 137 */     return str + ",updateRect=" + ((this.updateRect != null) ? this.updateRect.toString() : "null");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/PaintEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */