/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.FilteredImageSource;
/*     */ import java.awt.image.RGBImageFilter;
/*     */ import java.util.List;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import sun.swing.CachedPainter;
/*     */ import sun.swing.ImageIconUIResource;
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
/*     */ class MetalUtils
/*     */ {
/*     */   static void drawFlush3DBorder(Graphics paramGraphics, Rectangle paramRectangle) {
/*  46 */     drawFlush3DBorder(paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawFlush3DBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  53 */     paramGraphics.translate(paramInt1, paramInt2);
/*  54 */     paramGraphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/*  55 */     paramGraphics.drawRect(0, 0, paramInt3 - 2, paramInt4 - 2);
/*  56 */     paramGraphics.setColor(MetalLookAndFeel.getControlHighlight());
/*  57 */     paramGraphics.drawRect(1, 1, paramInt3 - 2, paramInt4 - 2);
/*  58 */     paramGraphics.setColor(MetalLookAndFeel.getControl());
/*  59 */     paramGraphics.drawLine(0, paramInt4 - 1, 1, paramInt4 - 2);
/*  60 */     paramGraphics.drawLine(paramInt3 - 1, 0, paramInt3 - 2, 1);
/*  61 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawPressed3DBorder(Graphics paramGraphics, Rectangle paramRectangle) {
/*  69 */     drawPressed3DBorder(paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */   
/*     */   static void drawDisabledBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  73 */     paramGraphics.translate(paramInt1, paramInt2);
/*  74 */     paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/*  75 */     paramGraphics.drawRect(0, 0, paramInt3 - 1, paramInt4 - 1);
/*  76 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawPressed3DBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  84 */     paramGraphics.translate(paramInt1, paramInt2);
/*     */     
/*  86 */     drawFlush3DBorder(paramGraphics, 0, 0, paramInt3, paramInt4);
/*     */     
/*  88 */     paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/*  89 */     paramGraphics.drawLine(1, 1, 1, paramInt4 - 2);
/*  90 */     paramGraphics.drawLine(1, 1, paramInt3 - 2, 1);
/*  91 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawDark3DBorder(Graphics paramGraphics, Rectangle paramRectangle) {
/* 100 */     drawDark3DBorder(paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawDark3DBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 109 */     paramGraphics.translate(paramInt1, paramInt2);
/*     */     
/* 111 */     drawFlush3DBorder(paramGraphics, 0, 0, paramInt3, paramInt4);
/*     */     
/* 113 */     paramGraphics.setColor(MetalLookAndFeel.getControl());
/* 114 */     paramGraphics.drawLine(1, 1, 1, paramInt4 - 2);
/* 115 */     paramGraphics.drawLine(1, 1, paramInt3 - 2, 1);
/* 116 */     paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/* 117 */     paramGraphics.drawLine(1, paramInt4 - 2, 1, paramInt4 - 2);
/* 118 */     paramGraphics.drawLine(paramInt3 - 2, 1, paramInt3 - 2, 1);
/* 119 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*     */   }
/*     */   
/*     */   static void drawButtonBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 123 */     if (paramBoolean) {
/* 124 */       drawActiveButtonBorder(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } else {
/* 126 */       drawFlush3DBorder(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void drawActiveButtonBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 131 */     drawFlush3DBorder(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/* 132 */     paramGraphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 133 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2 + 1, paramInt1 + 1, paramInt4 - 3);
/* 134 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2 + 1, paramInt3 - 3, paramInt1 + 1);
/* 135 */     paramGraphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 136 */     paramGraphics.drawLine(paramInt1 + 2, paramInt4 - 2, paramInt3 - 2, paramInt4 - 2);
/* 137 */     paramGraphics.drawLine(paramInt3 - 2, paramInt2 + 2, paramInt3 - 2, paramInt4 - 2);
/*     */   }
/*     */   
/*     */   static void drawDefaultButtonBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 141 */     drawButtonBorder(paramGraphics, paramInt1 + 1, paramInt2 + 1, paramInt3 - 1, paramInt4 - 1, paramBoolean);
/* 142 */     paramGraphics.translate(paramInt1, paramInt2);
/* 143 */     paramGraphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 144 */     paramGraphics.drawRect(0, 0, paramInt3 - 3, paramInt4 - 3);
/* 145 */     paramGraphics.drawLine(paramInt3 - 2, 0, paramInt3 - 2, 0);
/* 146 */     paramGraphics.drawLine(0, paramInt4 - 2, 0, paramInt4 - 2);
/* 147 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*     */   }
/*     */   
/*     */   static void drawDefaultButtonPressedBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 151 */     drawPressed3DBorder(paramGraphics, paramInt1 + 1, paramInt2 + 1, paramInt3 - 1, paramInt4 - 1);
/* 152 */     paramGraphics.translate(paramInt1, paramInt2);
/* 153 */     paramGraphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 154 */     paramGraphics.drawRect(0, 0, paramInt3 - 3, paramInt4 - 3);
/* 155 */     paramGraphics.drawLine(paramInt3 - 2, 0, paramInt3 - 2, 0);
/* 156 */     paramGraphics.drawLine(0, paramInt4 - 2, 0, paramInt4 - 2);
/* 157 */     paramGraphics.setColor(MetalLookAndFeel.getControl());
/* 158 */     paramGraphics.drawLine(paramInt3 - 1, 0, paramInt3 - 1, 0);
/* 159 */     paramGraphics.drawLine(0, paramInt4 - 1, 0, paramInt4 - 1);
/* 160 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isLeftToRight(Component paramComponent) {
/* 168 */     return paramComponent.getComponentOrientation().isLeftToRight();
/*     */   }
/*     */   
/*     */   static int getInt(Object paramObject, int paramInt) {
/* 172 */     Object object = UIManager.get(paramObject);
/*     */     
/* 174 */     if (object instanceof Integer) {
/* 175 */       return ((Integer)object).intValue();
/*     */     }
/* 177 */     if (object instanceof String) {
/*     */       try {
/* 179 */         return Integer.parseInt((String)object);
/* 180 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/* 182 */     return paramInt;
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
/*     */   static boolean drawGradient(Component paramComponent, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 213 */     List list = (List)UIManager.get(paramString);
/* 214 */     if (list == null || !(paramGraphics instanceof Graphics2D)) {
/* 215 */       return false;
/*     */     }
/*     */     
/* 218 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/* 219 */       return true;
/*     */     }
/*     */     
/* 222 */     GradientPainter.INSTANCE.paint(paramComponent, (Graphics2D)paramGraphics, list, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*     */     
/* 224 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class GradientPainter
/*     */     extends CachedPainter
/*     */   {
/* 233 */     public static final GradientPainter INSTANCE = new GradientPainter(8);
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int IMAGE_SIZE = 64;
/*     */ 
/*     */ 
/*     */     
/*     */     private int w;
/*     */ 
/*     */ 
/*     */     
/*     */     private int h;
/*     */ 
/*     */ 
/*     */     
/*     */     GradientPainter(int param1Int) {
/* 250 */       super(param1Int);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Component param1Component, Graphics2D param1Graphics2D, List param1List, int param1Int1, int param1Int2, int param1Int3, int param1Int4, boolean param1Boolean) {
/*     */       int i;
/*     */       byte b;
/* 258 */       if (param1Boolean) {
/* 259 */         i = 64;
/* 260 */         b = param1Int4;
/*     */       } else {
/*     */         
/* 263 */         i = param1Int3;
/* 264 */         b = 64;
/*     */       } 
/* 266 */       synchronized (param1Component.getTreeLock()) {
/* 267 */         this.w = param1Int3;
/* 268 */         this.h = param1Int4;
/* 269 */         paint(param1Component, param1Graphics2D, param1Int1, param1Int2, i, b, new Object[] { param1List, 
/* 270 */               Boolean.valueOf(param1Boolean) });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void paintToImage(Component param1Component, Image param1Image, Graphics param1Graphics, int param1Int1, int param1Int2, Object[] param1ArrayOfObject) {
/* 276 */       Graphics2D graphics2D = (Graphics2D)param1Graphics;
/* 277 */       List<Number> list = (List)param1ArrayOfObject[0];
/* 278 */       boolean bool = ((Boolean)param1ArrayOfObject[1]).booleanValue();
/*     */       
/* 280 */       if (bool) {
/* 281 */         drawVerticalGradient(graphics2D, ((Number)list
/* 282 */             .get(0)).floatValue(), ((Number)list
/* 283 */             .get(1)).floatValue(), (Color)list
/* 284 */             .get(2), (Color)list
/* 285 */             .get(3), (Color)list
/* 286 */             .get(4), param1Int1, param1Int2);
/*     */       } else {
/*     */         
/* 289 */         drawHorizontalGradient(graphics2D, ((Number)list
/* 290 */             .get(0)).floatValue(), ((Number)list
/* 291 */             .get(1)).floatValue(), (Color)list
/* 292 */             .get(2), (Color)list
/* 293 */             .get(3), (Color)list
/* 294 */             .get(4), param1Int1, param1Int2);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void paintImage(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, Image param1Image, Object[] param1ArrayOfObject) {
/* 301 */       boolean bool = ((Boolean)param1ArrayOfObject[1]).booleanValue();
/*     */       
/* 303 */       param1Graphics.translate(param1Int1, param1Int2);
/* 304 */       if (bool) {
/* 305 */         for (byte b = 0; b < this.w; b += 64) {
/* 306 */           int i = Math.min(64, this.w - b);
/* 307 */           param1Graphics.drawImage(param1Image, b, 0, b + i, this.h, 0, 0, i, this.h, null);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 312 */         for (byte b = 0; b < this.h; b += 64) {
/* 313 */           int i = Math.min(64, this.h - b);
/* 314 */           param1Graphics.drawImage(param1Image, 0, b, this.w, b + i, 0, 0, this.w, i, null);
/*     */         } 
/*     */       } 
/*     */       
/* 318 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void drawVerticalGradient(Graphics2D param1Graphics2D, float param1Float1, float param1Float2, Color param1Color1, Color param1Color2, Color param1Color3, int param1Int1, int param1Int2) {
/* 324 */       int i = (int)(param1Float1 * param1Int2);
/* 325 */       int j = (int)(param1Float2 * param1Int2);
/* 326 */       if (i > 0) {
/* 327 */         param1Graphics2D.setPaint(getGradient(0.0F, 0.0F, param1Color1, 0.0F, i, param1Color2));
/*     */         
/* 329 */         param1Graphics2D.fillRect(0, 0, param1Int1, i);
/*     */       } 
/* 331 */       if (j > 0) {
/* 332 */         param1Graphics2D.setColor(param1Color2);
/* 333 */         param1Graphics2D.fillRect(0, i, param1Int1, j);
/*     */       } 
/* 335 */       if (i > 0) {
/* 336 */         param1Graphics2D.setPaint(getGradient(0.0F, i + j, param1Color2, 0.0F, i * 2.0F + j, param1Color1));
/*     */         
/* 338 */         param1Graphics2D.fillRect(0, i + j, param1Int1, i);
/*     */       } 
/* 340 */       if (param1Int2 - i * 2 - j > 0) {
/* 341 */         param1Graphics2D.setPaint(getGradient(0.0F, i * 2.0F + j, param1Color1, 0.0F, param1Int2, param1Color3));
/*     */         
/* 343 */         param1Graphics2D.fillRect(0, i * 2 + j, param1Int1, param1Int2 - i * 2 - j);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void drawHorizontalGradient(Graphics2D param1Graphics2D, float param1Float1, float param1Float2, Color param1Color1, Color param1Color2, Color param1Color3, int param1Int1, int param1Int2) {
/* 350 */       int i = (int)(param1Float1 * param1Int1);
/* 351 */       int j = (int)(param1Float2 * param1Int1);
/* 352 */       if (i > 0) {
/* 353 */         param1Graphics2D.setPaint(getGradient(0.0F, 0.0F, param1Color1, i, 0.0F, param1Color2));
/*     */         
/* 355 */         param1Graphics2D.fillRect(0, 0, i, param1Int2);
/*     */       } 
/* 357 */       if (j > 0) {
/* 358 */         param1Graphics2D.setColor(param1Color2);
/* 359 */         param1Graphics2D.fillRect(i, 0, j, param1Int2);
/*     */       } 
/* 361 */       if (i > 0) {
/* 362 */         param1Graphics2D.setPaint(getGradient(i + j, 0.0F, param1Color2, i * 2.0F + j, 0.0F, param1Color1));
/*     */         
/* 364 */         param1Graphics2D.fillRect(i + j, 0, i, param1Int2);
/*     */       } 
/* 366 */       if (param1Int1 - i * 2 - j > 0) {
/* 367 */         param1Graphics2D.setPaint(getGradient(i * 2.0F + j, 0.0F, param1Color1, param1Int1, 0.0F, param1Color3));
/*     */         
/* 369 */         param1Graphics2D.fillRect(i * 2 + j, 0, param1Int1 - i * 2 - j, param1Int2);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private GradientPaint getGradient(float param1Float1, float param1Float2, Color param1Color1, float param1Float3, float param1Float4, Color param1Color2) {
/* 376 */       return new GradientPaint(param1Float1, param1Float2, param1Color1, param1Float3, param1Float4, param1Color2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isToolBarButton(JComponent paramJComponent) {
/* 385 */     return paramJComponent.getParent() instanceof javax.swing.JToolBar;
/*     */   }
/*     */   
/*     */   static Icon getOceanToolBarIcon(Image paramImage) {
/* 389 */     FilteredImageSource filteredImageSource = new FilteredImageSource(paramImage.getSource(), new OceanToolBarImageFilter());
/*     */     
/* 391 */     return new ImageIconUIResource(Toolkit.getDefaultToolkit().createImage(filteredImageSource));
/*     */   }
/*     */   
/*     */   static Icon getOceanDisabledButtonIcon(Image paramImage) {
/* 395 */     Object[] arrayOfObject = (Object[])UIManager.get("Button.disabledGrayRange");
/* 396 */     int i = 180;
/* 397 */     int j = 215;
/* 398 */     if (arrayOfObject != null) {
/* 399 */       i = ((Integer)arrayOfObject[0]).intValue();
/* 400 */       j = ((Integer)arrayOfObject[1]).intValue();
/*     */     } 
/* 402 */     FilteredImageSource filteredImageSource = new FilteredImageSource(paramImage.getSource(), new OceanDisabledButtonImageFilter(i, j));
/*     */     
/* 404 */     return new ImageIconUIResource(Toolkit.getDefaultToolkit().createImage(filteredImageSource));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class OceanDisabledButtonImageFilter
/*     */     extends RGBImageFilter
/*     */   {
/*     */     private float min;
/*     */     
/*     */     private float factor;
/*     */ 
/*     */     
/*     */     OceanDisabledButtonImageFilter(int param1Int1, int param1Int2) {
/* 418 */       this.canFilterIndexColorModel = true;
/* 419 */       this.min = param1Int1;
/* 420 */       this.factor = (param1Int2 - param1Int1) / 255.0F;
/*     */     }
/*     */ 
/*     */     
/*     */     public int filterRGB(int param1Int1, int param1Int2, int param1Int3) {
/* 425 */       int i = Math.min(255, (int)((0.2125F * (param1Int3 >> 16 & 0xFF) + 0.7154F * (param1Int3 >> 8 & 0xFF) + 0.0721F * (param1Int3 & 0xFF) + 0.5F) * this.factor + this.min));
/*     */ 
/*     */ 
/*     */       
/* 429 */       return param1Int3 & 0xFF000000 | i << 16 | i << 8 | i << 0;
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
/*     */   private static class OceanToolBarImageFilter
/*     */     extends RGBImageFilter
/*     */   {
/*     */     public int filterRGB(int param1Int1, int param1Int2, int param1Int3) {
/* 444 */       int i = param1Int3 >> 16 & 0xFF;
/* 445 */       int j = param1Int3 >> 8 & 0xFF;
/* 446 */       int k = param1Int3 & 0xFF;
/* 447 */       int m = Math.max(Math.max(i, j), k);
/* 448 */       return param1Int3 & 0xFF000000 | m << 16 | m << 8 | m << 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */