/*     */ package javax.swing.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import javax.swing.JInternalFrame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InternalFrameEvent
/*     */   extends AWTEvent
/*     */ {
/*     */   public static final int INTERNAL_FRAME_FIRST = 25549;
/*     */   public static final int INTERNAL_FRAME_LAST = 25555;
/*     */   public static final int INTERNAL_FRAME_OPENED = 25549;
/*     */   public static final int INTERNAL_FRAME_CLOSING = 25550;
/*     */   public static final int INTERNAL_FRAME_CLOSED = 25551;
/*     */   public static final int INTERNAL_FRAME_ICONIFIED = 25552;
/*     */   public static final int INTERNAL_FRAME_DEICONIFIED = 25553;
/*     */   public static final int INTERNAL_FRAME_ACTIVATED = 25554;
/*     */   public static final int INTERNAL_FRAME_DEACTIVATED = 25555;
/*     */   
/*     */   public InternalFrameEvent(JInternalFrame paramJInternalFrame, int paramInt) {
/* 140 */     super(paramJInternalFrame, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String paramString() {
/* 151 */     switch (this.id)
/*     */     { case 25549:
/* 153 */         str = "INTERNAL_FRAME_OPENED";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 176 */         return str;case 25550: str = "INTERNAL_FRAME_CLOSING"; return str;case 25551: str = "INTERNAL_FRAME_CLOSED"; return str;case 25552: str = "INTERNAL_FRAME_ICONIFIED"; return str;case 25553: str = "INTERNAL_FRAME_DEICONIFIED"; return str;case 25554: str = "INTERNAL_FRAME_ACTIVATED"; return str;case 25555: str = "INTERNAL_FRAME_DEACTIVATED"; return str; }  String str = "unknown type"; return str;
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
/*     */   public JInternalFrame getInternalFrame() {
/* 188 */     return (this.source instanceof JInternalFrame) ? (JInternalFrame)this.source : null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/InternalFrameEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */