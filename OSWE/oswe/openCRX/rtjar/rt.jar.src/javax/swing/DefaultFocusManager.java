/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.FocusTraversalPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultFocusManager
/*     */   extends FocusManager
/*     */ {
/*  51 */   final FocusTraversalPolicy gluePolicy = new LegacyGlueFocusTraversalPolicy(this);
/*     */   
/*  53 */   private final FocusTraversalPolicy layoutPolicy = new LegacyLayoutFocusTraversalPolicy(this);
/*     */   
/*  55 */   private final LayoutComparator comparator = new LayoutComparator();
/*     */ 
/*     */   
/*     */   public DefaultFocusManager() {
/*  59 */     setDefaultFocusTraversalPolicy(this.gluePolicy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponentAfter(Container paramContainer, Component paramComponent) {
/*  67 */     Container container = paramContainer.isFocusCycleRoot() ? paramContainer : paramContainer.getFocusCycleRootAncestor();
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (container != null) {
/*  72 */       FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/*  73 */       if (focusTraversalPolicy != this.gluePolicy) {
/*  74 */         return focusTraversalPolicy.getComponentAfter(container, paramComponent);
/*     */       }
/*     */       
/*  77 */       this.comparator.setComponentOrientation(container.getComponentOrientation());
/*  78 */       return this.layoutPolicy.getComponentAfter(container, paramComponent);
/*     */     } 
/*     */     
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponentBefore(Container paramContainer, Component paramComponent) {
/*  89 */     Container container = paramContainer.isFocusCycleRoot() ? paramContainer : paramContainer.getFocusCycleRootAncestor();
/*     */ 
/*     */ 
/*     */     
/*  93 */     if (container != null) {
/*  94 */       FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/*  95 */       if (focusTraversalPolicy != this.gluePolicy) {
/*  96 */         return focusTraversalPolicy.getComponentBefore(container, paramComponent);
/*     */       }
/*     */       
/*  99 */       this.comparator.setComponentOrientation(container.getComponentOrientation());
/* 100 */       return this.layoutPolicy.getComponentBefore(container, paramComponent);
/*     */     } 
/*     */     
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getFirstComponent(Container paramContainer) {
/* 109 */     Container container = paramContainer.isFocusCycleRoot() ? paramContainer : paramContainer.getFocusCycleRootAncestor();
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (container != null) {
/* 114 */       FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 115 */       if (focusTraversalPolicy != this.gluePolicy) {
/* 116 */         return focusTraversalPolicy.getFirstComponent(container);
/*     */       }
/*     */       
/* 119 */       this.comparator.setComponentOrientation(container.getComponentOrientation());
/* 120 */       return this.layoutPolicy.getFirstComponent(container);
/*     */     } 
/*     */     
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getLastComponent(Container paramContainer) {
/* 129 */     Container container = paramContainer.isFocusCycleRoot() ? paramContainer : paramContainer.getFocusCycleRootAncestor();
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (container != null) {
/* 134 */       FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 135 */       if (focusTraversalPolicy != this.gluePolicy) {
/* 136 */         return focusTraversalPolicy.getLastComponent(container);
/*     */       }
/*     */       
/* 139 */       this.comparator.setComponentOrientation(container.getComponentOrientation());
/* 140 */       return this.layoutPolicy.getLastComponent(container);
/*     */     } 
/*     */     
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public boolean compareTabOrder(Component paramComponent1, Component paramComponent2) {
/* 147 */     return (this.comparator.compare(paramComponent1, paramComponent2) < 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/DefaultFocusManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */