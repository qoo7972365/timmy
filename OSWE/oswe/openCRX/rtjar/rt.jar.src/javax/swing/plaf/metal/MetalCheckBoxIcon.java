/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.plaf.UIResource;
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
/*     */ public class MetalCheckBoxIcon
/*     */   implements Icon, UIResource, Serializable
/*     */ {
/*     */   protected int getControlSize() {
/*  51 */     return 13;
/*     */   }
/*     */   
/*     */   public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/*  55 */     JCheckBox jCheckBox = (JCheckBox)paramComponent;
/*  56 */     ButtonModel buttonModel = jCheckBox.getModel();
/*  57 */     int i = getControlSize();
/*     */     
/*  59 */     boolean bool = buttonModel.isSelected();
/*     */     
/*  61 */     if (buttonModel.isEnabled()) {
/*  62 */       if (jCheckBox.isBorderPaintedFlat()) {
/*  63 */         paramGraphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/*  64 */         paramGraphics.drawRect(paramInt1 + 1, paramInt2, i - 1, i - 1);
/*     */       } 
/*  66 */       if (buttonModel.isPressed() && buttonModel.isArmed()) {
/*  67 */         if (jCheckBox.isBorderPaintedFlat()) {
/*  68 */           paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/*  69 */           paramGraphics.fillRect(paramInt1 + 2, paramInt2 + 1, i - 2, i - 2);
/*     */         } else {
/*  71 */           paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/*  72 */           paramGraphics.fillRect(paramInt1, paramInt2, i - 1, i - 1);
/*  73 */           MetalUtils.drawPressed3DBorder(paramGraphics, paramInt1, paramInt2, i, i);
/*     */         } 
/*  75 */       } else if (!jCheckBox.isBorderPaintedFlat()) {
/*  76 */         MetalUtils.drawFlush3DBorder(paramGraphics, paramInt1, paramInt2, i, i);
/*     */       } 
/*  78 */       paramGraphics.setColor(MetalLookAndFeel.getControlInfo());
/*     */     } else {
/*  80 */       paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/*  81 */       paramGraphics.drawRect(paramInt1, paramInt2, i - 1, i - 1);
/*     */     } 
/*     */ 
/*     */     
/*  85 */     if (bool) {
/*  86 */       if (jCheckBox.isBorderPaintedFlat()) {
/*  87 */         paramInt1++;
/*     */       }
/*  89 */       drawCheck(paramComponent, paramGraphics, paramInt1, paramInt2);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawCheck(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/*  94 */     int i = getControlSize();
/*  95 */     paramGraphics.fillRect(paramInt1 + 3, paramInt2 + 5, 2, i - 8);
/*  96 */     paramGraphics.drawLine(paramInt1 + i - 4, paramInt2 + 3, paramInt1 + 5, paramInt2 + i - 6);
/*  97 */     paramGraphics.drawLine(paramInt1 + i - 4, paramInt2 + 4, paramInt1 + 5, paramInt2 + i - 5);
/*     */   }
/*     */   
/*     */   public int getIconWidth() {
/* 101 */     return getControlSize();
/*     */   }
/*     */   
/*     */   public int getIconHeight() {
/* 105 */     return getControlSize();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalCheckBoxIcon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */