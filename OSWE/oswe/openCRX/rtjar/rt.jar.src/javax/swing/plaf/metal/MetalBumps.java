/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.Icon;
/*     */ import sun.awt.AppContext;
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
/*     */ class MetalBumps
/*     */   implements Icon
/*     */ {
/*  45 */   static final Color ALPHA = new Color(0, 0, 0, 0);
/*     */   
/*     */   protected int xBumps;
/*     */   
/*     */   protected int yBumps;
/*     */   protected Color topColor;
/*     */   protected Color shadowColor;
/*     */   protected Color backColor;
/*  53 */   private static final Object METAL_BUMPS = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   protected BumpBuffer buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetalBumps(int paramInt1, int paramInt2, Color paramColor1, Color paramColor2, Color paramColor3) {
/*  63 */     setBumpArea(paramInt1, paramInt2);
/*  64 */     setBumpColors(paramColor1, paramColor2, paramColor3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BumpBuffer createBuffer(GraphicsConfiguration paramGraphicsConfiguration, Color paramColor1, Color paramColor2, Color paramColor3) {
/*  69 */     AppContext appContext = AppContext.getAppContext();
/*  70 */     List<BumpBuffer> list = (List)appContext.get(METAL_BUMPS);
/*  71 */     if (list == null) {
/*  72 */       list = new ArrayList();
/*  73 */       appContext.put(METAL_BUMPS, list);
/*     */     } 
/*  75 */     for (BumpBuffer bumpBuffer1 : list) {
/*  76 */       if (bumpBuffer1.hasSameConfiguration(paramGraphicsConfiguration, paramColor1, paramColor2, paramColor3)) {
/*  77 */         return bumpBuffer1;
/*     */       }
/*     */     } 
/*  80 */     BumpBuffer bumpBuffer = new BumpBuffer(paramGraphicsConfiguration, paramColor1, paramColor2, paramColor3);
/*  81 */     list.add(bumpBuffer);
/*  82 */     return bumpBuffer;
/*     */   }
/*     */   
/*     */   public void setBumpArea(Dimension paramDimension) {
/*  86 */     setBumpArea(paramDimension.width, paramDimension.height);
/*     */   }
/*     */   
/*     */   public void setBumpArea(int paramInt1, int paramInt2) {
/*  90 */     this.xBumps = paramInt1 / 2;
/*  91 */     this.yBumps = paramInt2 / 2;
/*     */   }
/*     */   
/*     */   public void setBumpColors(Color paramColor1, Color paramColor2, Color paramColor3) {
/*  95 */     this.topColor = paramColor1;
/*  96 */     this.shadowColor = paramColor2;
/*  97 */     if (paramColor3 == null) {
/*  98 */       this.backColor = ALPHA;
/*     */     } else {
/*     */       
/* 101 */       this.backColor = paramColor3;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 107 */     GraphicsConfiguration graphicsConfiguration = (paramGraphics instanceof Graphics2D) ? ((Graphics2D)paramGraphics).getDeviceConfiguration() : null;
/*     */     
/* 109 */     if (this.buffer == null || !this.buffer.hasSameConfiguration(graphicsConfiguration, this.topColor, this.shadowColor, this.backColor)) {
/* 110 */       this.buffer = createBuffer(graphicsConfiguration, this.topColor, this.shadowColor, this.backColor);
/*     */     }
/*     */     
/* 113 */     byte b1 = 64;
/* 114 */     byte b2 = 64;
/* 115 */     int i = getIconWidth();
/* 116 */     int j = getIconHeight();
/* 117 */     int k = paramInt1 + i;
/* 118 */     int m = paramInt2 + j;
/* 119 */     int n = paramInt1;
/*     */     
/* 121 */     while (paramInt2 < m) {
/* 122 */       int i1 = Math.min(m - paramInt2, b2);
/* 123 */       for (paramInt1 = n; paramInt1 < k; paramInt1 += b1) {
/* 124 */         int i2 = Math.min(k - paramInt1, b1);
/* 125 */         paramGraphics.drawImage(this.buffer.getImage(), paramInt1, paramInt2, paramInt1 + i2, paramInt2 + i1, 0, 0, i2, i1, null);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 130 */       paramInt2 += b2;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getIconWidth() {
/* 135 */     return this.xBumps * 2;
/*     */   }
/*     */   
/*     */   public int getIconHeight() {
/* 139 */     return this.yBumps * 2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalBumps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */