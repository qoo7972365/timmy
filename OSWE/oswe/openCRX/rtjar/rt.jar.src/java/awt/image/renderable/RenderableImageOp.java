/*     */ package java.awt.image.renderable;
/*     */ 
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.RenderedImage;
/*     */ import java.util.Vector;
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
/*     */ public class RenderableImageOp
/*     */   implements RenderableImage
/*     */ {
/*     */   ParameterBlock paramBlock;
/*     */   ContextualRenderedImageFactory myCRIF;
/*     */   Rectangle2D boundingBox;
/*     */   
/*     */   public RenderableImageOp(ContextualRenderedImageFactory paramContextualRenderedImageFactory, ParameterBlock paramParameterBlock) {
/*  74 */     this.myCRIF = paramContextualRenderedImageFactory;
/*  75 */     this.paramBlock = (ParameterBlock)paramParameterBlock.clone();
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
/*     */   public Vector<RenderableImage> getSources() {
/*  87 */     return getRenderableSources();
/*     */   }
/*     */   
/*     */   private Vector getRenderableSources() {
/*  91 */     Vector<RenderableImage> vector = null;
/*     */     
/*  93 */     if (this.paramBlock.getNumSources() > 0) {
/*  94 */       vector = new Vector();
/*  95 */       byte b = 0;
/*  96 */       while (b < this.paramBlock.getNumSources()) {
/*  97 */         Object object = this.paramBlock.getSource(b);
/*  98 */         if (object instanceof RenderableImage) {
/*  99 */           vector.add((RenderableImage)object);
/* 100 */           b++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return vector;
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
/*     */   public Object getProperty(String paramString) {
/* 119 */     return this.myCRIF.getProperty(this.paramBlock, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getPropertyNames() {
/* 127 */     return this.myCRIF.getPropertyNames();
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
/*     */   public boolean isDynamic() {
/* 141 */     return this.myCRIF.isDynamic();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 152 */     if (this.boundingBox == null) {
/* 153 */       this.boundingBox = this.myCRIF.getBounds2D(this.paramBlock);
/*     */     }
/* 155 */     return (float)this.boundingBox.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 165 */     if (this.boundingBox == null) {
/* 166 */       this.boundingBox = this.myCRIF.getBounds2D(this.paramBlock);
/*     */     }
/* 168 */     return (float)this.boundingBox.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinX() {
/* 175 */     if (this.boundingBox == null) {
/* 176 */       this.boundingBox = this.myCRIF.getBounds2D(this.paramBlock);
/*     */     }
/* 178 */     return (float)this.boundingBox.getMinX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinY() {
/* 185 */     if (this.boundingBox == null) {
/* 186 */       this.boundingBox = this.myCRIF.getBounds2D(this.paramBlock);
/*     */     }
/* 188 */     return (float)this.boundingBox.getMinY();
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
/*     */   public ParameterBlock setParameterBlock(ParameterBlock paramParameterBlock) {
/* 202 */     ParameterBlock parameterBlock = this.paramBlock;
/* 203 */     this.paramBlock = (ParameterBlock)paramParameterBlock.clone();
/* 204 */     return parameterBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterBlock getParameterBlock() {
/* 214 */     return this.paramBlock;
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
/*     */   public RenderedImage createScaledRendering(int paramInt1, int paramInt2, RenderingHints paramRenderingHints) {
/* 245 */     double d1 = paramInt1 / getWidth();
/* 246 */     double d2 = paramInt2 / getHeight();
/* 247 */     if (Math.abs(d1 / d2 - 1.0D) < 0.01D) {
/* 248 */       d1 = d2;
/*     */     }
/* 250 */     AffineTransform affineTransform = AffineTransform.getScaleInstance(d1, d2);
/* 251 */     RenderContext renderContext = new RenderContext(affineTransform, paramRenderingHints);
/* 252 */     return createRendering(renderContext);
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
/*     */   public RenderedImage createDefaultRendering() {
/* 266 */     AffineTransform affineTransform = new AffineTransform();
/* 267 */     RenderContext renderContext = new RenderContext(affineTransform);
/* 268 */     return createRendering(renderContext);
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
/*     */   public RenderedImage createRendering(RenderContext paramRenderContext) {
/* 310 */     Object object = null;
/* 311 */     RenderContext renderContext = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     ParameterBlock parameterBlock = (ParameterBlock)this.paramBlock.clone();
/* 317 */     Vector<RenderableImage> vector = getRenderableSources();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 323 */       if (vector != null) {
/* 324 */         Vector<RenderedImage> vector1 = new Vector();
/* 325 */         for (byte b = 0; b < vector.size(); b++) {
/* 326 */           renderContext = this.myCRIF.mapRenderContext(b, paramRenderContext, this.paramBlock, this);
/*     */ 
/*     */           
/* 329 */           RenderedImage renderedImage = ((RenderableImage)vector.elementAt(b)).createRendering(renderContext);
/* 330 */           if (renderedImage == null) {
/* 331 */             return null;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 336 */           vector1.addElement(renderedImage);
/*     */         } 
/*     */         
/* 339 */         if (vector1.size() > 0) {
/* 340 */           parameterBlock.setSources((Vector)vector1);
/*     */         }
/*     */       } 
/*     */       
/* 344 */       return this.myCRIF.create(paramRenderContext, parameterBlock);
/* 345 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/*     */       
/* 347 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/renderable/RenderableImageOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */