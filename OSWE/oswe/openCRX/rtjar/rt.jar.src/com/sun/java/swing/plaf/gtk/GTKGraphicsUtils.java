/*     */ package com.sun.java.swing.plaf.gtk;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.synth.Region;
/*     */ import javax.swing.plaf.synth.SynthContext;
/*     */ import javax.swing.plaf.synth.SynthGraphicsUtils;
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
/*     */ class GTKGraphicsUtils
/*     */   extends SynthGraphicsUtils
/*     */ {
/*     */   public void paintText(SynthContext paramSynthContext, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  39 */     if (paramString == null || paramString.length() <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  44 */     if (paramSynthContext.getRegion() == Region.INTERNAL_FRAME_TITLE_PANE) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  49 */     int i = paramSynthContext.getComponentState();
/*  50 */     String str = GTKLookAndFeel.getGtkThemeName();
/*  51 */     if (str != null && str.startsWith("blueprint") && 
/*  52 */       shouldShadowText(paramSynthContext.getRegion(), i)) {
/*     */       
/*  54 */       paramGraphics.setColor(Color.BLACK);
/*  55 */       super.paintText(paramSynthContext, paramGraphics, paramString, paramInt1 + 1, paramInt2 + 1, paramInt3);
/*  56 */       paramGraphics.setColor(Color.WHITE);
/*     */     } 
/*  58 */     super.paintText(paramSynthContext, paramGraphics, paramString, paramInt1, paramInt2, paramInt3);
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
/*     */   
/*     */   public void paintText(SynthContext paramSynthContext, Graphics paramGraphics, String paramString, Rectangle paramRectangle, int paramInt) {
/*  74 */     if (paramString == null || paramString.length() <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  79 */     Region region = paramSynthContext.getRegion();
/*  80 */     if ((region == Region.RADIO_BUTTON || region == Region.CHECK_BOX || region == Region.TABBED_PANE_TAB) && (paramSynthContext
/*     */ 
/*     */       
/*  83 */       .getComponentState() & 0x100) != 0) {
/*     */       
/*  85 */       JComponent jComponent = paramSynthContext.getComponent();
/*  86 */       if (!(jComponent instanceof AbstractButton) || ((AbstractButton)jComponent)
/*  87 */         .isFocusPainted()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  95 */         int i = paramSynthContext.getComponentState();
/*  96 */         GTKStyle gTKStyle = (GTKStyle)paramSynthContext.getStyle();
/*     */         
/*  98 */         int j = gTKStyle.getClassSpecificIntValue(paramSynthContext, "focus-line-width", 1);
/*     */ 
/*     */         
/* 101 */         int k = gTKStyle.getClassSpecificIntValue(paramSynthContext, "focus-padding", 1);
/*     */         
/* 103 */         int m = j + k;
/* 104 */         int n = paramRectangle.x - m;
/* 105 */         int i1 = paramRectangle.y - m;
/* 106 */         int i2 = paramRectangle.width + 2 * m;
/* 107 */         int i3 = paramRectangle.height + 2 * m;
/*     */         
/* 109 */         Color color = paramGraphics.getColor();
/* 110 */         GTKPainter.INSTANCE.paintFocus(paramSynthContext, paramGraphics, region, i, "checkbutton", n, i1, i2, i3);
/*     */ 
/*     */         
/* 113 */         paramGraphics.setColor(color);
/*     */       } 
/*     */     } 
/* 116 */     super.paintText(paramSynthContext, paramGraphics, paramString, paramRectangle, paramInt);
/*     */   }
/*     */   
/*     */   private static boolean shouldShadowText(Region paramRegion, int paramInt) {
/* 120 */     int i = GTKLookAndFeel.synthStateToGTKState(paramRegion, paramInt);
/* 121 */     return (i == 2 && (paramRegion == Region.MENU || paramRegion == Region.MENU_ITEM || paramRegion == Region.CHECK_BOX_MENU_ITEM || paramRegion == Region.RADIO_BUTTON_MENU_ITEM));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKGraphicsUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */