/*    */ package javax.swing.plaf;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.io.Serializable;
/*    */ import javax.swing.Icon;
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
/*    */ public class IconUIResource
/*    */   implements Icon, UIResource, Serializable
/*    */ {
/*    */   private Icon delegate;
/*    */   
/*    */   public IconUIResource(Icon paramIcon) {
/* 65 */     if (paramIcon == null) {
/* 66 */       throw new IllegalArgumentException("null delegate icon argument");
/*    */     }
/* 68 */     this.delegate = paramIcon;
/*    */   }
/*    */   
/*    */   public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 72 */     this.delegate.paintIcon(paramComponent, paramGraphics, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public int getIconWidth() {
/* 76 */     return this.delegate.getIconWidth();
/*    */   }
/*    */   
/*    */   public int getIconHeight() {
/* 80 */     return this.delegate.getIconHeight();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/IconUIResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */