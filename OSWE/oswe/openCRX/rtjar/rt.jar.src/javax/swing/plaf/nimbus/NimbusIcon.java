/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.Painter;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.synth.SynthContext;
/*     */ import sun.swing.plaf.synth.SynthIcon;
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
/*     */ class NimbusIcon
/*     */   extends SynthIcon
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*     */   private String prefix;
/*     */   private String key;
/*     */   
/*     */   NimbusIcon(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/*  47 */     this.width = paramInt1;
/*  48 */     this.height = paramInt2;
/*  49 */     this.prefix = paramString1;
/*  50 */     this.key = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintIcon(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  56 */     Painter<JComponent> painter = null;
/*  57 */     if (paramSynthContext != null) {
/*  58 */       painter = (Painter)paramSynthContext.getStyle().get(paramSynthContext, this.key);
/*     */     }
/*  60 */     if (painter == null) {
/*  61 */       painter = (Painter)UIManager.get(this.prefix + "[Enabled]." + this.key);
/*     */     }
/*     */     
/*  64 */     if (painter != null && paramSynthContext != null) {
/*  65 */       JComponent jComponent = paramSynthContext.getComponent();
/*  66 */       boolean bool1 = false;
/*  67 */       boolean bool2 = false;
/*     */ 
/*     */ 
/*     */       
/*  71 */       byte b1 = 0;
/*  72 */       byte b2 = 0;
/*  73 */       if (jComponent instanceof JToolBar) {
/*  74 */         JToolBar jToolBar = (JToolBar)jComponent;
/*  75 */         bool1 = (jToolBar.getOrientation() == 1) ? true : false;
/*  76 */         bool2 = !jToolBar.getComponentOrientation().isLeftToRight() ? true : false;
/*  77 */         Object object = NimbusLookAndFeel.resolveToolbarConstraint(jToolBar);
/*     */ 
/*     */         
/*  80 */         if (jToolBar.getBorder() instanceof javax.swing.plaf.UIResource) {
/*  81 */           if (object == "South") {
/*  82 */             b2 = 1;
/*  83 */           } else if (object == "East") {
/*  84 */             b1 = 1;
/*     */           } 
/*     */         }
/*  87 */       } else if (jComponent instanceof javax.swing.JMenu) {
/*  88 */         bool2 = !jComponent.getComponentOrientation().isLeftToRight() ? true : false;
/*     */       } 
/*  90 */       if (paramGraphics instanceof Graphics2D) {
/*  91 */         Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  92 */         graphics2D.translate(paramInt1, paramInt2);
/*  93 */         graphics2D.translate(b1, b2);
/*  94 */         if (bool1) {
/*  95 */           graphics2D.rotate(Math.toRadians(90.0D));
/*  96 */           graphics2D.translate(0, -paramInt3);
/*  97 */           painter.paint(graphics2D, paramSynthContext.getComponent(), paramInt4, paramInt3);
/*  98 */           graphics2D.translate(0, paramInt3);
/*  99 */           graphics2D.rotate(Math.toRadians(-90.0D));
/* 100 */         } else if (bool2) {
/* 101 */           graphics2D.scale(-1.0D, 1.0D);
/* 102 */           graphics2D.translate(-paramInt3, 0);
/* 103 */           painter.paint(graphics2D, paramSynthContext.getComponent(), paramInt3, paramInt4);
/* 104 */           graphics2D.translate(paramInt3, 0);
/* 105 */           graphics2D.scale(-1.0D, 1.0D);
/*     */         } else {
/* 107 */           painter.paint(graphics2D, paramSynthContext.getComponent(), paramInt3, paramInt4);
/*     */         } 
/* 109 */         graphics2D.translate(-b1, -b2);
/* 110 */         graphics2D.translate(-paramInt1, -paramInt2);
/*     */       }
/*     */       else {
/*     */         
/* 114 */         BufferedImage bufferedImage = new BufferedImage(paramInt3, paramInt4, 2);
/*     */         
/* 116 */         Graphics2D graphics2D = bufferedImage.createGraphics();
/* 117 */         if (bool1) {
/* 118 */           graphics2D.rotate(Math.toRadians(90.0D));
/* 119 */           graphics2D.translate(0, -paramInt3);
/* 120 */           painter.paint(graphics2D, paramSynthContext.getComponent(), paramInt4, paramInt3);
/* 121 */         } else if (bool2) {
/* 122 */           graphics2D.scale(-1.0D, 1.0D);
/* 123 */           graphics2D.translate(-paramInt3, 0);
/* 124 */           painter.paint(graphics2D, paramSynthContext.getComponent(), paramInt3, paramInt4);
/*     */         } else {
/* 126 */           painter.paint(graphics2D, paramSynthContext.getComponent(), paramInt3, paramInt4);
/*     */         } 
/* 128 */         graphics2D.dispose();
/* 129 */         paramGraphics.drawImage(bufferedImage, paramInt1, paramInt2, null);
/* 130 */         bufferedImage = null;
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
/*     */   public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 143 */     Painter<JComponent> painter = (Painter)UIManager.get(this.prefix + "[Enabled]." + this.key);
/* 144 */     if (painter != null) {
/* 145 */       JComponent jComponent = (paramComponent instanceof JComponent) ? (JComponent)paramComponent : null;
/* 146 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 147 */       graphics2D.translate(paramInt1, paramInt2);
/* 148 */       painter.paint(graphics2D, jComponent, this.width, this.height);
/* 149 */       graphics2D.translate(-paramInt1, -paramInt2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconWidth(SynthContext paramSynthContext) {
/* 155 */     if (paramSynthContext == null) {
/* 156 */       return this.width;
/*     */     }
/* 158 */     JComponent jComponent = paramSynthContext.getComponent();
/* 159 */     if (jComponent instanceof JToolBar && ((JToolBar)jComponent).getOrientation() == 1) {
/*     */ 
/*     */       
/* 162 */       if (jComponent.getBorder() instanceof javax.swing.plaf.UIResource) {
/* 163 */         return jComponent.getWidth() - 1;
/*     */       }
/* 165 */       return jComponent.getWidth();
/*     */     } 
/*     */     
/* 168 */     return scale(paramSynthContext, this.width);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight(SynthContext paramSynthContext) {
/* 174 */     if (paramSynthContext == null) {
/* 175 */       return this.height;
/*     */     }
/* 177 */     JComponent jComponent = paramSynthContext.getComponent();
/* 178 */     if (jComponent instanceof JToolBar) {
/* 179 */       JToolBar jToolBar = (JToolBar)jComponent;
/* 180 */       if (jToolBar.getOrientation() == 0) {
/*     */ 
/*     */         
/* 183 */         if (jToolBar.getBorder() instanceof javax.swing.plaf.UIResource) {
/* 184 */           return jComponent.getHeight() - 1;
/*     */         }
/* 186 */         return jComponent.getHeight();
/*     */       } 
/*     */       
/* 189 */       return scale(paramSynthContext, this.width);
/*     */     } 
/*     */     
/* 192 */     return scale(paramSynthContext, this.height);
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
/*     */   private int scale(SynthContext paramSynthContext, int paramInt) {
/* 206 */     if (paramSynthContext == null || paramSynthContext.getComponent() == null) {
/* 207 */       return paramInt;
/*     */     }
/*     */     
/* 210 */     String str = (String)paramSynthContext.getComponent().getClientProperty("JComponent.sizeVariant");
/*     */     
/* 212 */     if (str != null) {
/* 213 */       if ("large".equals(str)) {
/* 214 */         paramInt = (int)(paramInt * 1.15D);
/* 215 */       } else if ("small".equals(str)) {
/* 216 */         paramInt = (int)(paramInt * 0.857D);
/* 217 */       } else if ("mini".equals(str)) {
/*     */ 
/*     */         
/* 220 */         paramInt = (int)(paramInt * 0.784D);
/*     */       } 
/*     */     }
/* 223 */     return paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/NimbusIcon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */