/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Image;
/*     */ import java.awt.geom.Dimension2D;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiResolutionCachedImage
/*     */   extends AbstractMultiResolutionImage
/*     */ {
/*     */   private final int baseImageWidth;
/*     */   private final int baseImageHeight;
/*     */   private final Dimension2D[] sizes;
/*     */   private final BiFunction<Integer, Integer, Image> mapper;
/*     */   private int availableInfo;
/*     */   
/*     */   public MultiResolutionCachedImage(int paramInt1, int paramInt2, BiFunction<Integer, Integer, Image> paramBiFunction) {
/*  47 */     this(paramInt1, paramInt2, (Dimension2D[])new Dimension[] { new Dimension(paramInt1, paramInt2) }paramBiFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiResolutionCachedImage(int paramInt1, int paramInt2, Dimension2D[] paramArrayOfDimension2D, BiFunction<Integer, Integer, Image> paramBiFunction) {
/*  54 */     this.baseImageWidth = paramInt1;
/*  55 */     this.baseImageHeight = paramInt2;
/*  56 */     this.sizes = (paramArrayOfDimension2D == null) ? null : Arrays.<Dimension2D>copyOf(paramArrayOfDimension2D, paramArrayOfDimension2D.length);
/*  57 */     this.mapper = paramBiFunction;
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getResolutionVariant(int paramInt1, int paramInt2) {
/*  62 */     ImageCache imageCache = ImageCache.getInstance();
/*  63 */     ImageCacheKey imageCacheKey = new ImageCacheKey(this, paramInt1, paramInt2);
/*  64 */     Image image = imageCache.getImage(imageCacheKey);
/*  65 */     if (image == null) {
/*  66 */       image = this.mapper.apply(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
/*  67 */       imageCache.setImage(imageCacheKey, image);
/*     */     } 
/*  69 */     preload(image, this.availableInfo);
/*  70 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Image> getResolutionVariants() {
/*  75 */     return (List<Image>)Arrays.<Dimension2D>stream(this.sizes).map(paramDimension2D -> getResolutionVariant((int)paramDimension2D.getWidth(), (int)paramDimension2D.getHeight()))
/*     */       
/*  77 */       .collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public MultiResolutionCachedImage map(Function<Image, Image> paramFunction) {
/*  81 */     return new MultiResolutionCachedImage(this.baseImageWidth, this.baseImageHeight, this.sizes, (paramInteger1, paramInteger2) -> (Image)paramFunction.apply(getResolutionVariant(paramInteger1.intValue(), paramInteger2.intValue())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(ImageObserver paramImageObserver) {
/*  88 */     updateInfo(paramImageObserver, 1);
/*  89 */     return super.getWidth(paramImageObserver);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight(ImageObserver paramImageObserver) {
/*  94 */     updateInfo(paramImageObserver, 2);
/*  95 */     return super.getHeight(paramImageObserver);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(String paramString, ImageObserver paramImageObserver) {
/* 100 */     updateInfo(paramImageObserver, 4);
/* 101 */     return super.getProperty(paramString, paramImageObserver);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Image getBaseImage() {
/* 106 */     return getResolutionVariant(this.baseImageWidth, this.baseImageHeight);
/*     */   }
/*     */   
/*     */   private void updateInfo(ImageObserver paramImageObserver, int paramInt) {
/* 110 */     this.availableInfo |= (paramImageObserver == null) ? 32 : paramInt;
/*     */   }
/*     */   
/*     */   private static int getInfo(Image paramImage) {
/* 114 */     if (paramImage instanceof ToolkitImage) {
/* 115 */       return ((ToolkitImage)paramImage).getImageRep().check((paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5) -> false);
/*     */     }
/*     */     
/* 118 */     return 0;
/*     */   }
/*     */   
/*     */   private static void preload(Image paramImage, final int availableInfo) {
/* 122 */     if (availableInfo != 0 && paramImage instanceof ToolkitImage) {
/* 123 */       ((ToolkitImage)paramImage).preload(new ImageObserver() {
/* 124 */             int flags = availableInfo;
/*     */ 
/*     */ 
/*     */             
/*     */             public boolean imageUpdate(Image param1Image, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 129 */               this.flags &= param1Int1 ^ 0xFFFFFFFF;
/* 130 */               return (this.flags != 0 && (param1Int1 & 0xC0) == 0);
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ImageCacheKey
/*     */     implements ImageCache.PixelsKey
/*     */   {
/*     */     private final int pixelCount;
/*     */     
/*     */     private final int hash;
/*     */     private final int w;
/*     */     private final int h;
/*     */     private final Image baseImage;
/*     */     
/*     */     ImageCacheKey(Image param1Image, int param1Int1, int param1Int2) {
/* 148 */       this.baseImage = param1Image;
/* 149 */       this.w = param1Int1;
/* 150 */       this.h = param1Int2;
/* 151 */       this.pixelCount = param1Int1 * param1Int2;
/* 152 */       this.hash = hash();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPixelCount() {
/* 157 */       return this.pixelCount;
/*     */     }
/*     */     
/*     */     private int hash() {
/* 161 */       int i = this.baseImage.hashCode();
/* 162 */       i = 31 * i + this.w;
/* 163 */       i = 31 * i + this.h;
/* 164 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 169 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 174 */       if (param1Object instanceof ImageCacheKey) {
/* 175 */         ImageCacheKey imageCacheKey = (ImageCacheKey)param1Object;
/* 176 */         return (this.baseImage == imageCacheKey.baseImage && this.w == imageCacheKey.w && this.h == imageCacheKey.h);
/*     */       } 
/* 178 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/MultiResolutionCachedImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */