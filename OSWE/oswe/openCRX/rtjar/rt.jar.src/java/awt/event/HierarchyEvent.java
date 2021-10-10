/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HierarchyEvent
/*     */   extends AWTEvent
/*     */ {
/*     */   private static final long serialVersionUID = -5337576970038043990L;
/*     */   public static final int HIERARCHY_FIRST = 1400;
/*     */   public static final int HIERARCHY_CHANGED = 1400;
/*     */   public static final int ANCESTOR_MOVED = 1401;
/*     */   public static final int ANCESTOR_RESIZED = 1402;
/*     */   public static final int HIERARCHY_LAST = 1402;
/*     */   public static final int PARENT_CHANGED = 1;
/*     */   public static final int DISPLAYABILITY_CHANGED = 2;
/*     */   public static final int SHOWING_CHANGED = 4;
/*     */   Component changed;
/*     */   Container changedParent;
/*     */   long changeFlags;
/*     */   
/*     */   public HierarchyEvent(Component paramComponent1, int paramInt, Component paramComponent2, Container paramContainer) {
/* 195 */     super(paramComponent1, paramInt);
/* 196 */     this.changed = paramComponent2;
/* 197 */     this.changedParent = paramContainer;
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
/*     */   public HierarchyEvent(Component paramComponent1, int paramInt, Component paramComponent2, Container paramContainer, long paramLong) {
/* 232 */     super(paramComponent1, paramInt);
/* 233 */     this.changed = paramComponent2;
/* 234 */     this.changedParent = paramContainer;
/* 235 */     this.changeFlags = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponent() {
/* 246 */     return (this.source instanceof Component) ? (Component)this.source : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getChanged() {
/* 256 */     return this.changed;
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
/*     */   public Container getChangedParent() {
/* 273 */     return this.changedParent;
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
/*     */   public long getChangeFlags() {
/* 285 */     return this.changeFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String paramString() {
/*     */     boolean bool;
/* 296 */     switch (this.id)
/*     */     { case 1401:
/* 298 */         str = "ANCESTOR_MOVED (" + this.changed + "," + this.changedParent + ")";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 335 */         return str;case 1402: str = "ANCESTOR_RESIZED (" + this.changed + "," + this.changedParent + ")"; return str;case 1400: str = "HIERARCHY_CHANGED ("; bool = true; if ((this.changeFlags & 0x1L) != 0L) { bool = false; str = str + "PARENT_CHANGED"; }  if ((this.changeFlags & 0x2L) != 0L) { if (bool) { bool = false; } else { str = str + ","; }  str = str + "DISPLAYABILITY_CHANGED"; }  if ((this.changeFlags & 0x4L) != 0L) { if (bool) { bool = false; } else { str = str + ","; }  str = str + "SHOWING_CHANGED"; }  if (!bool) str = str + ",";  str = str + this.changed + "," + this.changedParent + ")"; return str; }  String str = "unknown type"; return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/HierarchyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */