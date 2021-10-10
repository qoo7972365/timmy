/*    */ package javax.swing.plaf.basic;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Insets;
/*    */ import java.awt.LayoutManager;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CenterLayout
/*    */   implements LayoutManager, Serializable
/*    */ {
/*    */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*    */   
/*    */   public void removeLayoutComponent(Component paramComponent) {}
/*    */   
/*    */   public Dimension preferredLayoutSize(Container paramContainer) {
/* 42 */     Component component = paramContainer.getComponent(0);
/* 43 */     if (component != null) {
/* 44 */       Dimension dimension = component.getPreferredSize();
/* 45 */       Insets insets = paramContainer.getInsets();
/*    */       
/* 47 */       return new Dimension(dimension.width + insets.left + insets.right, dimension.height + insets.top + insets.bottom);
/*    */     } 
/*    */ 
/*    */     
/* 51 */     return new Dimension(0, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Dimension minimumLayoutSize(Container paramContainer) {
/* 56 */     return preferredLayoutSize(paramContainer);
/*    */   }
/*    */   
/*    */   public void layoutContainer(Container paramContainer) {
/* 60 */     if (paramContainer.getComponentCount() > 0) {
/* 61 */       Component component = paramContainer.getComponent(0);
/* 62 */       Dimension dimension = component.getPreferredSize();
/* 63 */       int i = paramContainer.getWidth();
/* 64 */       int j = paramContainer.getHeight();
/* 65 */       Insets insets = paramContainer.getInsets();
/*    */       
/* 67 */       i -= insets.left + insets.right;
/*    */       
/* 69 */       j -= insets.top + insets.bottom;
/*    */ 
/*    */       
/* 72 */       int k = (i - dimension.width) / 2 + insets.left;
/*    */       
/* 74 */       int m = (j - dimension.height) / 2 + insets.top;
/*    */ 
/*    */       
/* 77 */       component.setBounds(k, m, dimension.width, dimension.height);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/CenterLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */