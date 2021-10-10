/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import sun.util.logging.PlatformLogger;
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
/*     */ public abstract class SunGraphicsCallback
/*     */ {
/*     */   public static final int HEAVYWEIGHTS = 1;
/*     */   public static final int LIGHTWEIGHTS = 2;
/*     */   public static final int TWO_PASSES = 4;
/*  37 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.SunGraphicsCallback");
/*     */   
/*     */   public abstract void run(Component paramComponent, Graphics paramGraphics);
/*     */   
/*     */   protected void constrainGraphics(Graphics paramGraphics, Rectangle paramRectangle) {
/*  42 */     if (paramGraphics instanceof ConstrainableGraphics) {
/*  43 */       ((ConstrainableGraphics)paramGraphics).constrain(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */     } else {
/*  45 */       paramGraphics.translate(paramRectangle.x, paramRectangle.y);
/*     */     } 
/*  47 */     paramGraphics.clipRect(0, 0, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void runOneComponent(Component paramComponent, Rectangle paramRectangle, Graphics paramGraphics, Shape paramShape, int paramInt) {
/*  54 */     if (paramComponent == null || paramComponent.getPeer() == null || !paramComponent.isVisible()) {
/*     */       return;
/*     */     }
/*  57 */     boolean bool = paramComponent.isLightweight();
/*  58 */     if ((bool && (paramInt & 0x2) == 0) || (!bool && (paramInt & 0x1) == 0)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  63 */     if (paramRectangle == null) {
/*  64 */       paramRectangle = paramComponent.getBounds();
/*     */     }
/*     */     
/*  67 */     if (paramShape == null || paramShape.intersects(paramRectangle)) {
/*  68 */       Graphics graphics = paramGraphics.create();
/*     */       try {
/*  70 */         constrainGraphics(graphics, paramRectangle);
/*  71 */         graphics.setFont(paramComponent.getFont());
/*  72 */         graphics.setColor(paramComponent.getForeground());
/*  73 */         if (graphics instanceof Graphics2D) {
/*  74 */           ((Graphics2D)graphics).setBackground(paramComponent.getBackground());
/*  75 */         } else if (graphics instanceof Graphics2Delegate) {
/*  76 */           ((Graphics2Delegate)graphics).setBackground(paramComponent
/*  77 */               .getBackground());
/*     */         } 
/*  79 */         run(paramComponent, graphics);
/*     */       } finally {
/*  81 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void runComponents(Component[] paramArrayOfComponent, Graphics paramGraphics, int paramInt) {
/*  88 */     int i = paramArrayOfComponent.length;
/*  89 */     Shape shape = paramGraphics.getClip();
/*     */     
/*  91 */     if (log.isLoggable(PlatformLogger.Level.FINER) && shape != null) {
/*  92 */       Rectangle rectangle = shape.getBounds();
/*  93 */       log.finer("x = " + rectangle.x + ", y = " + rectangle.y + ", width = " + rectangle.width + ", height = " + rectangle.height);
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
/*     */ 
/*     */ 
/*     */     
/* 107 */     if ((paramInt & 0x4) != 0) {
/* 108 */       int j; for (j = i - 1; j >= 0; j--) {
/* 109 */         runOneComponent(paramArrayOfComponent[j], null, paramGraphics, shape, 2);
/*     */       }
/* 111 */       for (j = i - 1; j >= 0; j--) {
/* 112 */         runOneComponent(paramArrayOfComponent[j], null, paramGraphics, shape, 1);
/*     */       }
/*     */     } else {
/* 115 */       for (int j = i - 1; j >= 0; j--) {
/* 116 */         runOneComponent(paramArrayOfComponent[j], null, paramGraphics, shape, paramInt);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static final class PaintHeavyweightComponentsCallback
/*     */     extends SunGraphicsCallback
/*     */   {
/* 124 */     private static PaintHeavyweightComponentsCallback instance = new PaintHeavyweightComponentsCallback();
/*     */ 
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/* 129 */       if (!param1Component.isLightweight()) {
/* 130 */         param1Component.paintAll(param1Graphics);
/* 131 */       } else if (param1Component instanceof Container) {
/* 132 */         runComponents(((Container)param1Component).getComponents(), param1Graphics, 3);
/*     */       } 
/*     */     }
/*     */     
/*     */     public static PaintHeavyweightComponentsCallback getInstance() {
/* 137 */       return instance;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class PrintHeavyweightComponentsCallback
/*     */     extends SunGraphicsCallback {
/* 143 */     private static PrintHeavyweightComponentsCallback instance = new PrintHeavyweightComponentsCallback();
/*     */ 
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/* 148 */       if (!param1Component.isLightweight()) {
/* 149 */         param1Component.printAll(param1Graphics);
/* 150 */       } else if (param1Component instanceof Container) {
/* 151 */         runComponents(((Container)param1Component).getComponents(), param1Graphics, 3);
/*     */       } 
/*     */     }
/*     */     
/*     */     public static PrintHeavyweightComponentsCallback getInstance() {
/* 156 */       return instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/SunGraphicsCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */