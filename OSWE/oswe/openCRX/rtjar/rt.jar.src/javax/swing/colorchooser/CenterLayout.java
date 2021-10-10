/*    */ package javax.swing.colorchooser;
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
/* 46 */       dimension.width += insets.left + insets.right;
/* 47 */       dimension.height += insets.top + insets.bottom;
/* 48 */       return dimension;
/*    */     } 
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
/*    */     try {
/* 61 */       Component component = paramContainer.getComponent(0);
/*    */       
/* 63 */       component.setSize(component.getPreferredSize());
/* 64 */       Dimension dimension1 = component.getSize();
/* 65 */       Dimension dimension2 = paramContainer.getSize();
/* 66 */       Insets insets = paramContainer.getInsets();
/* 67 */       dimension2.width -= insets.left + insets.right;
/* 68 */       dimension2.height -= insets.top + insets.bottom;
/* 69 */       int i = dimension2.width / 2 - dimension1.width / 2;
/* 70 */       int j = dimension2.height / 2 - dimension1.height / 2;
/* 71 */       i += insets.left;
/* 72 */       j += insets.top;
/*    */       
/* 74 */       component.setBounds(i, j, dimension1.width, dimension1.height);
/*    */     }
/* 76 */     catch (Exception exception) {}
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/CenterLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */