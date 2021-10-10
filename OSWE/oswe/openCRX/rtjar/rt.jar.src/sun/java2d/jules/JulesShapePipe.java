/*    */ package sun.java2d.jules;
/*    */ 
/*    */ import java.awt.BasicStroke;
/*    */ import java.awt.Shape;
/*    */ import sun.awt.SunToolkit;
/*    */ import sun.java2d.SunGraphics2D;
/*    */ import sun.java2d.pipe.ShapeDrawPipe;
/*    */ import sun.java2d.xr.XRCompositeManager;
/*    */ import sun.java2d.xr.XRSurfaceData;
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
/*    */ public class JulesShapePipe
/*    */   implements ShapeDrawPipe
/*    */ {
/*    */   XRCompositeManager compMan;
/* 37 */   JulesPathBuf buf = new JulesPathBuf();
/*    */   
/*    */   public JulesShapePipe(XRCompositeManager paramXRCompositeManager) {
/* 40 */     this.compMan = paramXRCompositeManager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final void validateSurface(SunGraphics2D paramSunGraphics2D) {
/* 48 */     XRSurfaceData xRSurfaceData = (XRSurfaceData)paramSunGraphics2D.surfaceData;
/* 49 */     xRSurfaceData.validateAsDestination(paramSunGraphics2D, paramSunGraphics2D.getCompClip());
/* 50 */     xRSurfaceData.maskBuffer.validateCompositeState(paramSunGraphics2D.composite, paramSunGraphics2D.transform, paramSunGraphics2D.paint, paramSunGraphics2D);
/*    */   }
/*    */   
/*    */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/*    */     try {
/*    */       BasicStroke basicStroke;
/* 56 */       SunToolkit.awtLock();
/* 57 */       validateSurface(paramSunGraphics2D);
/* 58 */       XRSurfaceData xRSurfaceData = (XRSurfaceData)paramSunGraphics2D.surfaceData;
/*    */ 
/*    */ 
/*    */       
/* 62 */       if (paramSunGraphics2D.stroke instanceof BasicStroke) {
/* 63 */         basicStroke = (BasicStroke)paramSunGraphics2D.stroke;
/*    */       } else {
/* 65 */         paramShape = paramSunGraphics2D.stroke.createStrokedShape(paramShape);
/* 66 */         basicStroke = null;
/*    */       } 
/*    */       
/* 69 */       boolean bool1 = (basicStroke != null && paramSunGraphics2D.strokeHint != 2) ? true : false;
/*    */       
/* 71 */       boolean bool2 = (paramSunGraphics2D.strokeState <= 1) ? true : false;
/*    */ 
/*    */       
/* 74 */       TrapezoidList trapezoidList = this.buf.tesselateStroke(paramShape, basicStroke, bool2, bool1, true, paramSunGraphics2D.transform, paramSunGraphics2D
/* 75 */           .getCompClip());
/* 76 */       this.compMan.XRCompositeTraps(xRSurfaceData.picture, paramSunGraphics2D.transX, paramSunGraphics2D.transY, trapezoidList);
/*    */ 
/*    */       
/* 79 */       this.buf.clear();
/*    */     } finally {
/*    */       
/* 82 */       SunToolkit.awtUnlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/*    */     try {
/* 88 */       SunToolkit.awtLock();
/* 89 */       validateSurface(paramSunGraphics2D);
/*    */       
/* 91 */       XRSurfaceData xRSurfaceData = (XRSurfaceData)paramSunGraphics2D.surfaceData;
/*    */       
/* 93 */       TrapezoidList trapezoidList = this.buf.tesselateFill(paramShape, paramSunGraphics2D.transform, paramSunGraphics2D
/* 94 */           .getCompClip());
/* 95 */       this.compMan.XRCompositeTraps(xRSurfaceData.picture, 0, 0, trapezoidList);
/*    */       
/* 97 */       this.buf.clear();
/*    */     } finally {
/* 99 */       SunToolkit.awtUnlock();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/jules/JulesShapePipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */