/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicProgressBarUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalProgressBarUI
/*     */   extends BasicProgressBarUI
/*     */ {
/*     */   private Rectangle innards;
/*     */   private Rectangle box;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  53 */     return new MetalProgressBarUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintDeterminate(Graphics paramGraphics, JComponent paramJComponent) {
/*  63 */     super.paintDeterminate(paramGraphics, paramJComponent);
/*     */     
/*  65 */     if (!(paramGraphics instanceof Graphics2D)) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     if (this.progressBar.isBorderPainted()) {
/*  70 */       Insets insets = this.progressBar.getInsets();
/*  71 */       int i = this.progressBar.getWidth() - insets.left + insets.right;
/*  72 */       int j = this.progressBar.getHeight() - insets.top + insets.bottom;
/*  73 */       int k = getAmountFull(insets, i, j);
/*  74 */       boolean bool = MetalUtils.isLeftToRight(paramJComponent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       int m = insets.left;
/*  81 */       int n = insets.top;
/*  82 */       int i1 = insets.left + i - 1;
/*  83 */       int i2 = insets.top + j - 1;
/*     */       
/*  85 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  86 */       graphics2D.setStroke(new BasicStroke(1.0F));
/*     */       
/*  88 */       if (this.progressBar.getOrientation() == 0) {
/*     */         
/*  90 */         graphics2D.setColor(MetalLookAndFeel.getControlShadow());
/*  91 */         graphics2D.drawLine(m, n, i1, n);
/*     */         
/*  93 */         if (k > 0) {
/*     */           
/*  95 */           graphics2D.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/*     */           
/*  97 */           if (bool) {
/*  98 */             graphics2D.drawLine(m, n, m + k - 1, n);
/*     */           } else {
/*     */             
/* 101 */             graphics2D.drawLine(i1, n, i1 - k + 1, n);
/*     */             
/* 103 */             if (this.progressBar.getPercentComplete() != 1.0D) {
/* 104 */               graphics2D.setColor(MetalLookAndFeel.getControlShadow());
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 110 */         graphics2D.drawLine(m, n, m, i2);
/*     */       }
/*     */       else {
/*     */         
/* 114 */         graphics2D.setColor(MetalLookAndFeel.getControlShadow());
/* 115 */         graphics2D.drawLine(m, n, m, i2);
/*     */         
/* 117 */         if (k > 0) {
/*     */           
/* 119 */           graphics2D.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 120 */           graphics2D.drawLine(m, i2, m, i2 - k + 1);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 125 */         graphics2D.setColor(MetalLookAndFeel.getControlShadow());
/*     */         
/* 127 */         if (this.progressBar.getPercentComplete() == 1.0D) {
/* 128 */           graphics2D.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/*     */         }
/* 130 */         graphics2D.drawLine(m, n, i1, n);
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
/*     */   public void paintIndeterminate(Graphics paramGraphics, JComponent paramJComponent) {
/* 143 */     super.paintIndeterminate(paramGraphics, paramJComponent);
/*     */     
/* 145 */     if (!this.progressBar.isBorderPainted() || !(paramGraphics instanceof Graphics2D)) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     Insets insets = this.progressBar.getInsets();
/* 150 */     int i = this.progressBar.getWidth() - insets.left + insets.right;
/* 151 */     int j = this.progressBar.getHeight() - insets.top + insets.bottom;
/* 152 */     int k = getAmountFull(insets, i, j);
/* 153 */     boolean bool = MetalUtils.isLeftToRight(paramJComponent);
/*     */     
/* 155 */     Rectangle rectangle = null;
/* 156 */     rectangle = getBox(rectangle);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     int m = insets.left;
/* 162 */     int n = insets.top;
/* 163 */     int i1 = insets.left + i - 1;
/* 164 */     int i2 = insets.top + j - 1;
/*     */     
/* 166 */     Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 167 */     graphics2D.setStroke(new BasicStroke(1.0F));
/*     */     
/* 169 */     if (this.progressBar.getOrientation() == 0) {
/*     */       
/* 171 */       graphics2D.setColor(MetalLookAndFeel.getControlShadow());
/* 172 */       graphics2D.drawLine(m, n, i1, n);
/* 173 */       graphics2D.drawLine(m, n, m, i2);
/*     */ 
/*     */       
/* 176 */       graphics2D.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 177 */       graphics2D.drawLine(rectangle.x, n, rectangle.x + rectangle.width - 1, n);
/*     */     }
/*     */     else {
/*     */       
/* 181 */       graphics2D.setColor(MetalLookAndFeel.getControlShadow());
/* 182 */       graphics2D.drawLine(m, n, m, i2);
/* 183 */       graphics2D.drawLine(m, n, i1, n);
/*     */ 
/*     */       
/* 186 */       graphics2D.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 187 */       graphics2D.drawLine(m, rectangle.y, m, rectangle.y + rectangle.height - 1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalProgressBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */