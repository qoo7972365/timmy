/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Point;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViewportLayout
/*     */   implements LayoutManager, Serializable
/*     */ {
/*  62 */   static ViewportLayout SHARED_INSTANCE = new ViewportLayout();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLayoutComponent(Component paramComponent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension preferredLayoutSize(Container paramContainer) {
/*  88 */     Component component = ((JViewport)paramContainer).getView();
/*  89 */     if (component == null) {
/*  90 */       return new Dimension(0, 0);
/*     */     }
/*  92 */     if (component instanceof Scrollable) {
/*  93 */       return ((Scrollable)component).getPreferredScrollableViewportSize();
/*     */     }
/*     */     
/*  96 */     return component.getPreferredSize();
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
/*     */   public Dimension minimumLayoutSize(Container paramContainer) {
/* 111 */     return new Dimension(4, 4);
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
/*     */   public void layoutContainer(Container paramContainer) {
/* 125 */     JViewport jViewport = (JViewport)paramContainer;
/* 126 */     Component component = jViewport.getView();
/* 127 */     Scrollable scrollable = null;
/*     */     
/* 129 */     if (component == null) {
/*     */       return;
/*     */     }
/* 132 */     if (component instanceof Scrollable) {
/* 133 */       scrollable = (Scrollable)component;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     Insets insets = jViewport.getInsets();
/* 141 */     Dimension dimension1 = component.getPreferredSize();
/* 142 */     Dimension dimension2 = jViewport.getSize();
/* 143 */     Dimension dimension3 = jViewport.toViewCoordinates(dimension2);
/* 144 */     Dimension dimension4 = new Dimension(dimension1);
/*     */     
/* 146 */     if (scrollable != null) {
/* 147 */       if (scrollable.getScrollableTracksViewportWidth()) {
/* 148 */         dimension4.width = dimension2.width;
/*     */       }
/* 150 */       if (scrollable.getScrollableTracksViewportHeight()) {
/* 151 */         dimension4.height = dimension2.height;
/*     */       }
/*     */     } 
/*     */     
/* 155 */     Point point = jViewport.getViewPosition();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     if (scrollable == null || jViewport
/* 163 */       .getParent() == null || jViewport
/* 164 */       .getParent().getComponentOrientation().isLeftToRight()) {
/* 165 */       if (point.x + dimension3.width > dimension4.width) {
/* 166 */         point.x = Math.max(0, dimension4.width - dimension3.width);
/*     */       }
/*     */     }
/* 169 */     else if (dimension3.width > dimension4.width) {
/* 170 */       point.x = dimension4.width - dimension3.width;
/*     */     } else {
/* 172 */       point.x = Math.max(0, Math.min(dimension4.width - dimension3.width, point.x));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (point.y + dimension3.height > dimension4.height) {
/* 181 */       point.y = Math.max(0, dimension4.height - dimension3.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     if (scrollable == null) {
/* 193 */       if (point.x == 0 && dimension2.width > dimension1.width) {
/* 194 */         dimension4.width = dimension2.width;
/*     */       }
/* 196 */       if (point.y == 0 && dimension2.height > dimension1.height) {
/* 197 */         dimension4.height = dimension2.height;
/*     */       }
/*     */     } 
/* 200 */     jViewport.setViewPosition(point);
/* 201 */     jViewport.setViewSize(dimension4);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ViewportLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */