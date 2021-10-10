/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.Window;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowEvent
/*     */   extends ComponentEvent
/*     */ {
/*     */   public static final int WINDOW_FIRST = 200;
/*     */   public static final int WINDOW_OPENED = 200;
/*     */   public static final int WINDOW_CLOSING = 201;
/*     */   public static final int WINDOW_CLOSED = 202;
/*     */   public static final int WINDOW_ICONIFIED = 203;
/*     */   public static final int WINDOW_DEICONIFIED = 204;
/*     */   public static final int WINDOW_ACTIVATED = 205;
/*     */   public static final int WINDOW_DEACTIVATED = 206;
/*     */   public static final int WINDOW_GAINED_FOCUS = 207;
/*     */   public static final int WINDOW_LOST_FOCUS = 208;
/*     */   public static final int WINDOW_STATE_CHANGED = 209;
/*     */   public static final int WINDOW_LAST = 209;
/*     */   transient Window opposite;
/*     */   int oldState;
/*     */   int newState;
/*     */   private static final long serialVersionUID = -1567959133147912127L;
/*     */   
/*     */   public WindowEvent(Window paramWindow1, int paramInt1, Window paramWindow2, int paramInt2, int paramInt3) {
/* 206 */     super(paramWindow1, paramInt1);
/* 207 */     this.opposite = paramWindow2;
/* 208 */     this.oldState = paramInt2;
/* 209 */     this.newState = paramInt3;
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
/*     */   public WindowEvent(Window paramWindow1, int paramInt, Window paramWindow2) {
/* 251 */     this(paramWindow1, paramInt, paramWindow2, 0, 0);
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
/*     */   public WindowEvent(Window paramWindow, int paramInt1, int paramInt2, int paramInt3) {
/* 284 */     this(paramWindow, paramInt1, (Window)null, paramInt2, paramInt3);
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
/*     */   public WindowEvent(Window paramWindow, int paramInt) {
/* 302 */     this(paramWindow, paramInt, (Window)null, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Window getWindow() {
/* 311 */     return (this.source instanceof Window) ? (Window)this.source : null;
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
/*     */   public Window getOppositeWindow() {
/* 328 */     if (this.opposite == null) {
/* 329 */       return null;
/*     */     }
/*     */     
/* 332 */     return 
/* 333 */       (SunToolkit.targetToAppContext(this.opposite) == AppContext.getAppContext()) ? this.opposite : null;
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
/*     */   public int getOldState() {
/* 358 */     return this.oldState;
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
/*     */   public int getNewState() {
/* 381 */     return this.newState;
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
/* 392 */     switch (this.id)
/*     */     { case 200:
/* 394 */         str = "WINDOW_OPENED";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 426 */         str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState;
/*     */ 
/*     */         
/* 429 */         return str;case 201: str = "WINDOW_CLOSING"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;case 202: str = "WINDOW_CLOSED"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;case 203: str = "WINDOW_ICONIFIED"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;case 204: str = "WINDOW_DEICONIFIED"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;case 205: str = "WINDOW_ACTIVATED"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;case 206: str = "WINDOW_DEACTIVATED"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;case 207: str = "WINDOW_GAINED_FOCUS"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;case 208: str = "WINDOW_LOST_FOCUS"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;case 209: str = "WINDOW_STATE_CHANGED"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str; }  String str = "unknown type"; str = str + ",opposite=" + getOppositeWindow() + ",oldState=" + this.oldState + ",newState=" + this.newState; return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/WindowEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */