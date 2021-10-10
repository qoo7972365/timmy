/*      */ package javax.imageio;
/*      */ 
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import javax.imageio.event.IIOWriteProgressListener;
/*      */ import javax.imageio.event.IIOWriteWarningListener;
/*      */ import javax.imageio.metadata.IIOMetadata;
/*      */ import javax.imageio.spi.ImageWriterSpi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ImageWriter
/*      */   implements ImageTranscoder
/*      */ {
/*   71 */   protected ImageWriterSpi originatingProvider = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   protected Object output = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   87 */   protected Locale[] availableLocales = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   protected Locale locale = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  102 */   protected List<IIOWriteWarningListener> warningListeners = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   protected List<Locale> warningLocales = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   protected List<IIOWriteProgressListener> progressListeners = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean abortFlag = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ImageWriter(ImageWriterSpi paramImageWriterSpi) {
/*  141 */     this.originatingProvider = paramImageWriterSpi;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ImageWriterSpi getOriginatingProvider() {
/*  157 */     return this.originatingProvider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutput(Object paramObject) {
/*  207 */     if (paramObject != null) {
/*  208 */       ImageWriterSpi imageWriterSpi = getOriginatingProvider();
/*  209 */       if (imageWriterSpi != null) {
/*  210 */         Class[] arrayOfClass = imageWriterSpi.getOutputTypes();
/*  211 */         boolean bool = false;
/*  212 */         for (byte b = 0; b < arrayOfClass.length; b++) {
/*  213 */           if (arrayOfClass[b].isInstance(paramObject)) {
/*  214 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*  218 */         if (!bool) {
/*  219 */           throw new IllegalArgumentException("Illegal output type!");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  224 */     this.output = paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getOutput() {
/*  242 */     return this.output;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale[] getAvailableLocales() {
/*  261 */     return (this.availableLocales == null) ? null : (Locale[])this.availableLocales
/*  262 */       .clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocale(Locale paramLocale) {
/*  288 */     if (paramLocale != null) {
/*  289 */       Locale[] arrayOfLocale = getAvailableLocales();
/*  290 */       boolean bool = false;
/*  291 */       if (arrayOfLocale != null) {
/*  292 */         for (byte b = 0; b < arrayOfLocale.length; b++) {
/*  293 */           if (paramLocale.equals(arrayOfLocale[b])) {
/*  294 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*  299 */       if (!bool) {
/*  300 */         throw new IllegalArgumentException("Invalid locale!");
/*      */       }
/*      */     } 
/*  303 */     this.locale = paramLocale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  318 */     return this.locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ImageWriteParam getDefaultWriteParam() {
/*  347 */     return new ImageWriteParam(getLocale());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract IIOMetadata getDefaultStreamMetadata(ImageWriteParam paramImageWriteParam);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract IIOMetadata convertStreamMetadata(IIOMetadata paramIIOMetadata, ImageWriteParam paramImageWriteParam);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract IIOMetadata convertImageMetadata(IIOMetadata paramIIOMetadata, ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumThumbnailsSupported(ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam, IIOMetadata paramIIOMetadata1, IIOMetadata paramIIOMetadata2) {
/*  459 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension[] getPreferredThumbnailSizes(ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam, IIOMetadata paramIIOMetadata1, IIOMetadata paramIIOMetadata2) {
/*  503 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWriteRasters() {
/*  521 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void write(IIOMetadata paramIIOMetadata, IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(IIOImage paramIIOImage) throws IOException {
/*  597 */     write(null, paramIIOImage, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(RenderedImage paramRenderedImage) throws IOException {
/*  615 */     write(null, new IIOImage(paramRenderedImage, null, null), null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void unsupported() {
/*  621 */     if (getOutput() == null) {
/*  622 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/*  624 */     throw new UnsupportedOperationException("Unsupported write variant!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWriteSequence() {
/*  643 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prepareWriteSequence(IIOMetadata paramIIOMetadata) throws IOException {
/*  685 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeToSequence(IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException {
/*  750 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endWriteSequence() throws IOException {
/*  779 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canReplaceStreamMetadata() throws IOException {
/*  800 */     if (getOutput() == null) {
/*  801 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/*  803 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceStreamMetadata(IIOMetadata paramIIOMetadata) throws IOException {
/*  835 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canReplaceImageMetadata(int paramInt) throws IOException {
/*  871 */     if (getOutput() == null) {
/*  872 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/*  874 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replaceImageMetadata(int paramInt, IIOMetadata paramIIOMetadata) throws IOException {
/*  906 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canInsertImage(int paramInt) throws IOException {
/*  942 */     if (getOutput() == null) {
/*  943 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/*  945 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeInsert(int paramInt, IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException {
/*  999 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRemoveImage(int paramInt) throws IOException {
/* 1033 */     if (getOutput() == null) {
/* 1034 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/* 1036 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeImage(int paramInt) throws IOException {
/* 1065 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWriteEmpty() throws IOException {
/* 1093 */     if (getOutput() == null) {
/* 1094 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/* 1096 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prepareWriteEmpty(IIOMetadata paramIIOMetadata1, ImageTypeSpecifier paramImageTypeSpecifier, int paramInt1, int paramInt2, IIOMetadata paramIIOMetadata2, List<? extends BufferedImage> paramList, ImageWriteParam paramImageWriteParam) throws IOException {
/* 1173 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endWriteEmpty() throws IOException {
/* 1205 */     if (getOutput() == null) {
/* 1206 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/* 1208 */     throw new IllegalStateException("No call to prepareWriteEmpty!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canInsertEmpty(int paramInt) throws IOException {
/* 1245 */     if (getOutput() == null) {
/* 1246 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/* 1248 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prepareInsertEmpty(int paramInt1, ImageTypeSpecifier paramImageTypeSpecifier, int paramInt2, int paramInt3, IIOMetadata paramIIOMetadata, List<? extends BufferedImage> paramList, ImageWriteParam paramImageWriteParam) throws IOException {
/* 1333 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endInsertEmpty() throws IOException {
/* 1362 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canReplacePixels(int paramInt) throws IOException {
/* 1395 */     if (getOutput() == null) {
/* 1396 */       throw new IllegalStateException("getOutput() == null!");
/*      */     }
/* 1398 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prepareReplacePixels(int paramInt, Rectangle paramRectangle) throws IOException {
/* 1438 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replacePixels(RenderedImage paramRenderedImage, ImageWriteParam paramImageWriteParam) throws IOException {
/* 1496 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replacePixels(Raster paramRaster, ImageWriteParam paramImageWriteParam) throws IOException {
/* 1557 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endReplacePixels() throws IOException {
/* 1583 */     unsupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void abort() {
/* 1597 */     this.abortFlag = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized boolean abortRequested() {
/* 1612 */     return this.abortFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void clearAbortRequest() {
/* 1624 */     this.abortFlag = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addIIOWriteWarningListener(IIOWriteWarningListener paramIIOWriteWarningListener) {
/* 1644 */     if (paramIIOWriteWarningListener == null) {
/*      */       return;
/*      */     }
/* 1647 */     this.warningListeners = ImageReader.addToList(this.warningListeners, paramIIOWriteWarningListener);
/* 1648 */     this.warningLocales = ImageReader.addToList(this.warningLocales, getLocale());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeIIOWriteWarningListener(IIOWriteWarningListener paramIIOWriteWarningListener) {
/* 1665 */     if (paramIIOWriteWarningListener == null || this.warningListeners == null) {
/*      */       return;
/*      */     }
/* 1668 */     int i = this.warningListeners.indexOf(paramIIOWriteWarningListener);
/* 1669 */     if (i != -1) {
/* 1670 */       this.warningListeners.remove(i);
/* 1671 */       this.warningLocales.remove(i);
/* 1672 */       if (this.warningListeners.size() == 0) {
/* 1673 */         this.warningListeners = null;
/* 1674 */         this.warningLocales = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllIIOWriteWarningListeners() {
/* 1688 */     this.warningListeners = null;
/* 1689 */     this.warningLocales = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addIIOWriteProgressListener(IIOWriteProgressListener paramIIOWriteProgressListener) {
/* 1705 */     if (paramIIOWriteProgressListener == null) {
/*      */       return;
/*      */     }
/* 1708 */     this.progressListeners = ImageReader.addToList(this.progressListeners, paramIIOWriteProgressListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeIIOWriteProgressListener(IIOWriteProgressListener paramIIOWriteProgressListener) {
/* 1725 */     if (paramIIOWriteProgressListener == null || this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1728 */     this
/* 1729 */       .progressListeners = ImageReader.removeFromList(this.progressListeners, paramIIOWriteProgressListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllIIOWriteProgressListeners() {
/* 1741 */     this.progressListeners = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processImageStarted(int paramInt) {
/* 1753 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1756 */     int i = this.progressListeners.size();
/* 1757 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1759 */       IIOWriteProgressListener iIOWriteProgressListener = this.progressListeners.get(b);
/* 1760 */       iIOWriteProgressListener.imageStarted(this, paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processImageProgress(float paramFloat) {
/* 1774 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1777 */     int i = this.progressListeners.size();
/* 1778 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1780 */       IIOWriteProgressListener iIOWriteProgressListener = this.progressListeners.get(b);
/* 1781 */       iIOWriteProgressListener.imageProgress(this, paramFloat);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processImageComplete() {
/* 1792 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1795 */     int i = this.progressListeners.size();
/* 1796 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1798 */       IIOWriteProgressListener iIOWriteProgressListener = this.progressListeners.get(b);
/* 1799 */       iIOWriteProgressListener.imageComplete(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processThumbnailStarted(int paramInt1, int paramInt2) {
/* 1815 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1818 */     int i = this.progressListeners.size();
/* 1819 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1821 */       IIOWriteProgressListener iIOWriteProgressListener = this.progressListeners.get(b);
/* 1822 */       iIOWriteProgressListener.thumbnailStarted(this, paramInt1, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processThumbnailProgress(float paramFloat) {
/* 1836 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1839 */     int i = this.progressListeners.size();
/* 1840 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1842 */       IIOWriteProgressListener iIOWriteProgressListener = this.progressListeners.get(b);
/* 1843 */       iIOWriteProgressListener.thumbnailProgress(this, paramFloat);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processThumbnailComplete() {
/* 1854 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1857 */     int i = this.progressListeners.size();
/* 1858 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1860 */       IIOWriteProgressListener iIOWriteProgressListener = this.progressListeners.get(b);
/* 1861 */       iIOWriteProgressListener.thumbnailComplete(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processWriteAborted() {
/* 1872 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1875 */     int i = this.progressListeners.size();
/* 1876 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1878 */       IIOWriteProgressListener iIOWriteProgressListener = this.progressListeners.get(b);
/* 1879 */       iIOWriteProgressListener.writeAborted(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processWarningOccurred(int paramInt, String paramString) {
/* 1898 */     if (this.warningListeners == null) {
/*      */       return;
/*      */     }
/* 1901 */     if (paramString == null) {
/* 1902 */       throw new IllegalArgumentException("warning == null!");
/*      */     }
/* 1904 */     int i = this.warningListeners.size();
/* 1905 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1907 */       IIOWriteWarningListener iIOWriteWarningListener = this.warningListeners.get(b);
/*      */       
/* 1909 */       iIOWriteWarningListener.warningOccurred(this, paramInt, paramString);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processWarningOccurred(int paramInt, String paramString1, String paramString2) {
/* 1943 */     if (this.warningListeners == null) {
/*      */       return;
/*      */     }
/* 1946 */     if (paramString1 == null) {
/* 1947 */       throw new IllegalArgumentException("baseName == null!");
/*      */     }
/* 1949 */     if (paramString2 == null) {
/* 1950 */       throw new IllegalArgumentException("keyword == null!");
/*      */     }
/* 1952 */     int i = this.warningListeners.size();
/* 1953 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 1955 */       IIOWriteWarningListener iIOWriteWarningListener = this.warningListeners.get(b);
/* 1956 */       Locale locale = this.warningLocales.get(b);
/* 1957 */       if (locale == null) {
/* 1958 */         locale = Locale.getDefault();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1970 */       ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*      */           {
/*      */             public Object run() {
/* 1973 */               return Thread.currentThread().getContextClassLoader();
/*      */             }
/*      */           });
/*      */       
/* 1977 */       ResourceBundle resourceBundle = null;
/*      */       try {
/* 1979 */         resourceBundle = ResourceBundle.getBundle(paramString1, locale, classLoader);
/* 1980 */       } catch (MissingResourceException missingResourceException) {
/*      */         try {
/* 1982 */           resourceBundle = ResourceBundle.getBundle(paramString1, locale);
/* 1983 */         } catch (MissingResourceException missingResourceException1) {
/* 1984 */           throw new IllegalArgumentException("Bundle not found!");
/*      */         } 
/*      */       } 
/*      */       
/* 1988 */       String str = null;
/*      */       try {
/* 1990 */         str = resourceBundle.getString(paramString2);
/* 1991 */       } catch (ClassCastException classCastException) {
/* 1992 */         throw new IllegalArgumentException("Resource is not a String!");
/* 1993 */       } catch (MissingResourceException missingResourceException) {
/* 1994 */         throw new IllegalArgumentException("Resource is missing!");
/*      */       } 
/*      */       
/* 1997 */       iIOWriteWarningListener.warningOccurred(this, paramInt, str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/* 2013 */     setOutput(null);
/* 2014 */     setLocale(null);
/* 2015 */     removeAllIIOWriteWarningListeners();
/* 2016 */     removeAllIIOWriteProgressListeners();
/* 2017 */     clearAbortRequest();
/*      */   }
/*      */   
/*      */   public void dispose() {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/ImageWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */