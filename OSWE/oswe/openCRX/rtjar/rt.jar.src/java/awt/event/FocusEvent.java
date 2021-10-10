/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.Component;
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
/*     */ public class FocusEvent
/*     */   extends ComponentEvent
/*     */ {
/*     */   public static final int FOCUS_FIRST = 1004;
/*     */   public static final int FOCUS_LAST = 1005;
/*     */   public static final int FOCUS_GAINED = 1004;
/*     */   public static final int FOCUS_LOST = 1005;
/*     */   boolean temporary;
/*     */   transient Component opposite;
/*     */   private static final long serialVersionUID = 523753786457416396L;
/*     */   
/*     */   public FocusEvent(Component paramComponent1, int paramInt, boolean paramBoolean, Component paramComponent2) {
/* 149 */     super(paramComponent1, paramInt);
/* 150 */     this.temporary = paramBoolean;
/* 151 */     this.opposite = paramComponent2;
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
/*     */   public FocusEvent(Component paramComponent, int paramInt, boolean paramBoolean) {
/* 173 */     this(paramComponent, paramInt, paramBoolean, null);
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
/*     */   public FocusEvent(Component paramComponent, int paramInt) {
/* 192 */     this(paramComponent, paramInt, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTemporary() {
/* 202 */     return this.temporary;
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
/*     */   public Component getOppositeComponent() {
/* 217 */     if (this.opposite == null) {
/* 218 */       return null;
/*     */     }
/*     */     
/* 221 */     return 
/* 222 */       (SunToolkit.targetToAppContext(this.opposite) == AppContext.getAppContext()) ? this.opposite : null;
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
/*     */   public String paramString() {
/*     */     String str;
/* 235 */     switch (this.id) {
/*     */       case 1004:
/* 237 */         str = "FOCUS_GAINED";
/*     */         break;
/*     */       case 1005:
/* 240 */         str = "FOCUS_LOST";
/*     */         break;
/*     */       default:
/* 243 */         str = "unknown type"; break;
/*     */     } 
/* 245 */     return str + (this.temporary ? ",temporary" : ",permanent") + ",opposite=" + 
/* 246 */       getOppositeComponent();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/FocusEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */