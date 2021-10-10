/*     */ package javax.swing.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Container;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AncestorEvent
/*     */   extends AWTEvent
/*     */ {
/*     */   public static final int ANCESTOR_ADDED = 1;
/*     */   public static final int ANCESTOR_REMOVED = 2;
/*     */   public static final int ANCESTOR_MOVED = 3;
/*     */   Container ancestor;
/*     */   Container ancestorParent;
/*     */   
/*     */   public AncestorEvent(JComponent paramJComponent, int paramInt, Container paramContainer1, Container paramContainer2) {
/*  77 */     super(paramJComponent, paramInt);
/*  78 */     this.ancestor = paramContainer1;
/*  79 */     this.ancestorParent = paramContainer2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container getAncestor() {
/*  86 */     return this.ancestor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container getAncestorParent() {
/*  95 */     return this.ancestorParent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JComponent getComponent() {
/* 102 */     return (JComponent)getSource();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/AncestorEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */