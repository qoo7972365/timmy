/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LayoutStyle
/*     */ {
/*     */   public static void setInstance(LayoutStyle paramLayoutStyle) {
/*  52 */     synchronized (LayoutStyle.class) {
/*  53 */       if (paramLayoutStyle == null) {
/*  54 */         AppContext.getAppContext().remove(LayoutStyle.class);
/*     */       } else {
/*     */         
/*  57 */         AppContext.getAppContext().put(LayoutStyle.class, paramLayoutStyle);
/*     */       } 
/*     */     } 
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
/*     */   public static LayoutStyle getInstance() {
/*     */     LayoutStyle layoutStyle;
/*  72 */     synchronized (LayoutStyle.class) {
/*     */       
/*  74 */       layoutStyle = (LayoutStyle)AppContext.getAppContext().get(LayoutStyle.class);
/*     */     } 
/*  76 */     if (layoutStyle == null) {
/*  77 */       return UIManager.getLookAndFeel().getLayoutStyle();
/*     */     }
/*  79 */     return layoutStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getPreferredGap(JComponent paramJComponent1, JComponent paramJComponent2, ComponentPlacement paramComponentPlacement, int paramInt, Container paramContainer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getContainerGap(JComponent paramJComponent, int paramInt, Container paramContainer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum ComponentPlacement
/*     */   {
/* 103 */     RELATED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     UNRELATED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     INDENT;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/LayoutStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */