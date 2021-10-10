/*    */ package javax.swing.plaf.basic;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.plaf.UIResource;
/*    */ import sun.swing.MenuItemLayoutHelper;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultMenuLayout
/*    */   extends BoxLayout
/*    */   implements UIResource
/*    */ {
/*    */   public DefaultMenuLayout(Container paramContainer, int paramInt) {
/* 45 */     super(paramContainer, paramInt);
/*    */   }
/*    */   
/*    */   public Dimension preferredLayoutSize(Container paramContainer) {
/* 49 */     if (paramContainer instanceof JPopupMenu) {
/* 50 */       JPopupMenu jPopupMenu = (JPopupMenu)paramContainer;
/* 51 */       MenuItemLayoutHelper.clearUsedClientProperties(jPopupMenu);
/* 52 */       if (jPopupMenu.getComponentCount() == 0) {
/* 53 */         return new Dimension(0, 0);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 58 */     invalidateLayout(paramContainer);
/*    */     
/* 60 */     return super.preferredLayoutSize(paramContainer);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/DefaultMenuLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */