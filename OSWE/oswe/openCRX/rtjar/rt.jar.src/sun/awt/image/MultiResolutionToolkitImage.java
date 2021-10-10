/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import sun.misc.SoftCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiResolutionToolkitImage
/*     */   extends ToolkitImage
/*     */   implements MultiResolutionImage
/*     */ {
/*     */   Image resolutionVariant;
/*     */   private static final int BITS_INFO = 56;
/*     */   
/*     */   public MultiResolutionToolkitImage(Image paramImage1, Image paramImage2) {
/*  38 */     super(paramImage1.getSource());
/*  39 */     this.resolutionVariant = paramImage2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getResolutionVariant(int paramInt1, int paramInt2) {
/*  44 */     return (paramInt1 <= getWidth() && paramInt2 <= getHeight()) ? this : this.resolutionVariant;
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getResolutionVariant() {
/*  49 */     return this.resolutionVariant;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Image> getResolutionVariants() {
/*  54 */     return Arrays.asList(new Image[] { this, this.resolutionVariant });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ObserverCache
/*     */   {
/*  62 */     static final SoftCache INSTANCE = new SoftCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageObserver getResolutionVariantObserver(Image paramImage, ImageObserver paramImageObserver, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  69 */     return getResolutionVariantObserver(paramImage, paramImageObserver, paramInt1, paramInt2, paramInt3, paramInt4, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageObserver getResolutionVariantObserver(Image paramImage, ImageObserver paramImageObserver, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  78 */     if (paramImageObserver == null) {
/*  79 */       return null;
/*     */     }
/*     */     
/*  82 */     synchronized (ObserverCache.INSTANCE) {
/*  83 */       ImageObserver imageObserver = (ImageObserver)ObserverCache.INSTANCE.get(paramImageObserver);
/*     */       
/*  85 */       if (imageObserver == null) {
/*     */         
/*  87 */         imageObserver = ((paramImage2, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5) -> {
/*     */             if ((paramInt1 & 0x39) != 0) {
/*     */               paramInt4 = (paramInt4 + 1) / 2;
/*     */             }
/*     */ 
/*     */             
/*     */             if ((paramInt1 & 0x3A) != 0) {
/*     */               paramInt5 = (paramInt5 + 1) / 2;
/*     */             }
/*     */ 
/*     */             
/*     */             if ((paramInt1 & 0x38) != 0) {
/*     */               paramInt2 /= 2;
/*     */ 
/*     */               
/*     */               paramInt3 /= 2;
/*     */             } 
/*     */             
/*     */             if (paramBoolean) {
/*     */               paramInt1 &= ((ToolkitImage)paramImage1).getImageRep().check(null);
/*     */             }
/*     */             
/*     */             return paramImageObserver.imageUpdate(paramImage1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */           });
/*     */         
/* 112 */         ObserverCache.INSTANCE.put(paramImageObserver, imageObserver);
/*     */       } 
/* 114 */       return imageObserver;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/MultiResolutionToolkitImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */