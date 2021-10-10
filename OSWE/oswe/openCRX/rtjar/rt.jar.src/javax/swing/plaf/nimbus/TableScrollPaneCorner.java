/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.Painter;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.plaf.UIResource;
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
/*    */ class TableScrollPaneCorner
/*    */   extends JComponent
/*    */   implements UIResource
/*    */ {
/*    */   protected void paintComponent(Graphics paramGraphics) {
/* 50 */     Painter<TableScrollPaneCorner> painter = (Painter)UIManager.get("TableHeader:\"TableHeader.renderer\"[Enabled].backgroundPainter");
/*    */     
/* 52 */     if (painter != null)
/* 53 */       if (paramGraphics instanceof Graphics2D) {
/* 54 */         painter.paint((Graphics2D)paramGraphics, this, getWidth() + 1, getHeight());
/*    */       }
/*    */       else {
/*    */         
/* 58 */         BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), 2);
/*    */         
/* 60 */         Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
/* 61 */         painter.paint(graphics2D, this, getWidth() + 1, getHeight());
/* 62 */         graphics2D.dispose();
/* 63 */         paramGraphics.drawImage(bufferedImage, 0, 0, null);
/* 64 */         bufferedImage = null;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/TableScrollPaneCorner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */