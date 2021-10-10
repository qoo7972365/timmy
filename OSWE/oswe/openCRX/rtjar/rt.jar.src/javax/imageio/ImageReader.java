/*      */ package javax.imageio;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import javax.imageio.event.IIOReadProgressListener;
/*      */ import javax.imageio.event.IIOReadUpdateListener;
/*      */ import javax.imageio.event.IIOReadWarningListener;
/*      */ import javax.imageio.metadata.IIOMetadata;
/*      */ import javax.imageio.spi.ImageReaderSpi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ImageReader
/*      */ {
/*      */   protected ImageReaderSpi originatingProvider;
/*   88 */   protected Object input = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean seekForwardOnly = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean ignoreMetadata = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  119 */   protected int minIndex = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   protected Locale[] availableLocales = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   protected Locale locale = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  140 */   protected List<IIOReadWarningListener> warningListeners = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   protected List<Locale> warningLocales = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   protected List<IIOReadProgressListener> progressListeners = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   protected List<IIOReadUpdateListener> updateListeners = null;
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
/*      */   protected ImageReader(ImageReaderSpi paramImageReaderSpi) {
/*  186 */     this.originatingProvider = paramImageReaderSpi;
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
/*      */   public String getFormatName() throws IOException {
/*  205 */     return this.originatingProvider.getFormatNames()[0];
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
/*      */   public ImageReaderSpi getOriginatingProvider() {
/*  217 */     return this.originatingProvider;
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
/*      */   public void setInput(Object paramObject, boolean paramBoolean1, boolean paramBoolean2) {
/*  291 */     if (paramObject != null) {
/*  292 */       boolean bool = false;
/*  293 */       if (this.originatingProvider != null) {
/*  294 */         Class[] arrayOfClass = this.originatingProvider.getInputTypes();
/*  295 */         for (byte b = 0; b < arrayOfClass.length; b++) {
/*  296 */           if (arrayOfClass[b].isInstance(paramObject)) {
/*  297 */             bool = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*  302 */       } else if (paramObject instanceof javax.imageio.stream.ImageInputStream) {
/*  303 */         bool = true;
/*      */       } 
/*      */       
/*  306 */       if (!bool) {
/*  307 */         throw new IllegalArgumentException("Incorrect input type!");
/*      */       }
/*      */       
/*  310 */       this.seekForwardOnly = paramBoolean1;
/*  311 */       this.ignoreMetadata = paramBoolean2;
/*  312 */       this.minIndex = 0;
/*      */     } 
/*      */     
/*  315 */     this.input = paramObject;
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
/*      */   public void setInput(Object paramObject, boolean paramBoolean) {
/*  355 */     setInput(paramObject, paramBoolean, false);
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
/*      */   public void setInput(Object paramObject) {
/*  380 */     setInput(paramObject, false, false);
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
/*      */   public Object getInput() {
/*  395 */     return this.input;
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
/*      */   public boolean isSeekForwardOnly() {
/*  410 */     return this.seekForwardOnly;
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
/*      */   public boolean isIgnoringMetadata() {
/*  424 */     return this.ignoreMetadata;
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
/*      */   public int getMinIndex() {
/*  438 */     return this.minIndex;
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
/*  457 */     if (this.availableLocales == null) {
/*  458 */       return null;
/*      */     }
/*  460 */     return (Locale[])this.availableLocales.clone();
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
/*      */   public void setLocale(Locale paramLocale) {
/*  480 */     if (paramLocale != null) {
/*  481 */       Locale[] arrayOfLocale = getAvailableLocales();
/*  482 */       boolean bool = false;
/*  483 */       if (arrayOfLocale != null) {
/*  484 */         for (byte b = 0; b < arrayOfLocale.length; b++) {
/*  485 */           if (paramLocale.equals(arrayOfLocale[b])) {
/*  486 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*  491 */       if (!bool) {
/*  492 */         throw new IllegalArgumentException("Invalid locale!");
/*      */       }
/*      */     } 
/*  495 */     this.locale = paramLocale;
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
/*      */   public Locale getLocale() {
/*  507 */     return this.locale;
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
/*      */   public abstract int getNumImages(boolean paramBoolean) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getWidth(int paramInt) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getHeight(int paramInt) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRandomAccessEasy(int paramInt) throws IOException {
/*  628 */     return false;
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
/*      */   public float getAspectRatio(int paramInt) throws IOException {
/*  653 */     return getWidth(paramInt) / getHeight(paramInt);
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
/*      */   public ImageTypeSpecifier getRawImageType(int paramInt) throws IOException {
/*  681 */     return getImageTypes(paramInt).next();
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
/*      */   public abstract Iterator<ImageTypeSpecifier> getImageTypes(int paramInt) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ImageReadParam getDefaultReadParam() {
/*  731 */     return new ImageReadParam();
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
/*      */   public abstract IIOMetadata getStreamMetadata() throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOMetadata getStreamMetadata(String paramString, Set<String> paramSet) throws IOException {
/*  792 */     return getMetadata(paramString, paramSet, true, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IIOMetadata getMetadata(String paramString, Set paramSet, boolean paramBoolean, int paramInt) throws IOException {
/*  799 */     if (paramString == null) {
/*  800 */       throw new IllegalArgumentException("formatName == null!");
/*      */     }
/*  802 */     if (paramSet == null) {
/*  803 */       throw new IllegalArgumentException("nodeNames == null!");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  808 */     IIOMetadata iIOMetadata = paramBoolean ? getStreamMetadata() : getImageMetadata(paramInt);
/*  809 */     if (iIOMetadata != null) {
/*  810 */       if (iIOMetadata.isStandardMetadataFormatSupported() && paramString
/*      */         
/*  812 */         .equals("javax_imageio_1.0")) {
/*  813 */         return iIOMetadata;
/*      */       }
/*  815 */       String str = iIOMetadata.getNativeMetadataFormatName();
/*  816 */       if (str != null && paramString.equals(str)) {
/*  817 */         return iIOMetadata;
/*      */       }
/*  819 */       String[] arrayOfString = iIOMetadata.getExtraMetadataFormatNames();
/*  820 */       if (arrayOfString != null) {
/*  821 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*  822 */           if (paramString.equals(arrayOfString[b])) {
/*  823 */             return iIOMetadata;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*  828 */     return null;
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
/*      */   public abstract IIOMetadata getImageMetadata(int paramInt) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOMetadata getImageMetadata(int paramInt, String paramString, Set<String> paramSet) throws IOException {
/*  903 */     return getMetadata(paramString, paramSet, false, paramInt);
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
/*      */   public BufferedImage read(int paramInt) throws IOException {
/*  939 */     return read(paramInt, null);
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
/*      */   public abstract BufferedImage read(int paramInt, ImageReadParam paramImageReadParam) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOImage readAll(int paramInt, ImageReadParam paramImageReadParam) throws IOException {
/* 1062 */     if (paramInt < getMinIndex()) {
/* 1063 */       throw new IndexOutOfBoundsException("imageIndex < getMinIndex()!");
/*      */     }
/*      */     
/* 1066 */     BufferedImage bufferedImage = read(paramInt, paramImageReadParam);
/*      */     
/* 1068 */     ArrayList<BufferedImage> arrayList = null;
/* 1069 */     int i = getNumThumbnails(paramInt);
/* 1070 */     if (i > 0) {
/* 1071 */       arrayList = new ArrayList();
/* 1072 */       for (byte b = 0; b < i; b++) {
/* 1073 */         arrayList.add(readThumbnail(paramInt, b));
/*      */       }
/*      */     } 
/*      */     
/* 1077 */     IIOMetadata iIOMetadata = getImageMetadata(paramInt);
/* 1078 */     return new IIOImage(bufferedImage, arrayList, iIOMetadata);
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
/*      */   public Iterator<IIOImage> readAll(Iterator<? extends ImageReadParam> paramIterator) throws IOException {
/* 1159 */     ArrayList<IIOImage> arrayList = new ArrayList();
/*      */     
/* 1161 */     int i = getMinIndex();
/*      */ 
/*      */     
/* 1164 */     processSequenceStarted(i);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1170 */       ImageReadParam imageReadParam = null;
/* 1171 */       if (paramIterator != null && paramIterator.hasNext()) {
/* 1172 */         ImageReadParam imageReadParam1 = (ImageReadParam)paramIterator.next();
/* 1173 */         if (imageReadParam1 != null) {
/* 1174 */           if (imageReadParam1 instanceof ImageReadParam) {
/* 1175 */             imageReadParam = imageReadParam1;
/*      */           } else {
/* 1177 */             throw new IllegalArgumentException("Non-ImageReadParam supplied as part of params!");
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1183 */       BufferedImage bufferedImage = null;
/*      */       try {
/* 1185 */         bufferedImage = read(i, imageReadParam);
/* 1186 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*      */         break;
/*      */       } 
/*      */       
/* 1190 */       ArrayList<BufferedImage> arrayList1 = null;
/* 1191 */       int j = getNumThumbnails(i);
/* 1192 */       if (j > 0) {
/* 1193 */         arrayList1 = new ArrayList();
/* 1194 */         for (byte b = 0; b < j; b++) {
/* 1195 */           arrayList1.add(readThumbnail(i, b));
/*      */         }
/*      */       } 
/*      */       
/* 1199 */       IIOMetadata iIOMetadata = getImageMetadata(i);
/* 1200 */       IIOImage iIOImage = new IIOImage(bufferedImage, arrayList1, iIOMetadata);
/* 1201 */       arrayList.add(iIOImage);
/*      */       
/* 1203 */       i++;
/*      */     } 
/*      */ 
/*      */     
/* 1207 */     processSequenceComplete();
/*      */     
/* 1209 */     return arrayList.iterator();
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
/*      */   public boolean canReadRaster() {
/* 1228 */     return false;
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
/*      */   public Raster readRaster(int paramInt, ImageReadParam paramImageReadParam) throws IOException {
/* 1285 */     throw new UnsupportedOperationException("readRaster not supported!");
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
/*      */   public boolean isImageTiled(int paramInt) throws IOException {
/* 1322 */     return false;
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
/*      */   public int getTileWidth(int paramInt) throws IOException {
/* 1343 */     return getWidth(paramInt);
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
/*      */   public int getTileHeight(int paramInt) throws IOException {
/* 1364 */     return getHeight(paramInt);
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
/*      */   public int getTileGridXOffset(int paramInt) throws IOException {
/* 1394 */     return 0;
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
/*      */   public int getTileGridYOffset(int paramInt) throws IOException {
/* 1424 */     return 0;
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
/*      */   public BufferedImage readTile(int paramInt1, int paramInt2, int paramInt3) throws IOException {
/* 1469 */     if (paramInt2 != 0 || paramInt3 != 0) {
/* 1470 */       throw new IllegalArgumentException("Invalid tile indices");
/*      */     }
/* 1472 */     return read(paramInt1);
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
/*      */   public Raster readTileRaster(int paramInt1, int paramInt2, int paramInt3) throws IOException {
/* 1515 */     if (!canReadRaster()) {
/* 1516 */       throw new UnsupportedOperationException("readTileRaster not supported!");
/*      */     }
/*      */     
/* 1519 */     if (paramInt2 != 0 || paramInt3 != 0) {
/* 1520 */       throw new IllegalArgumentException("Invalid tile indices");
/*      */     }
/* 1522 */     return readRaster(paramInt1, null);
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
/*      */   public RenderedImage readAsRenderedImage(int paramInt, ImageReadParam paramImageReadParam) throws IOException {
/* 1574 */     return read(paramInt, paramImageReadParam);
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
/*      */   public boolean readerSupportsThumbnails() {
/* 1597 */     return false;
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
/*      */   public boolean hasThumbnails(int paramInt) throws IOException {
/* 1622 */     return (getNumThumbnails(paramInt) > 0);
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
/*      */   public int getNumThumbnails(int paramInt) throws IOException {
/* 1649 */     return 0;
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
/*      */   public int getThumbnailWidth(int paramInt1, int paramInt2) throws IOException {
/* 1682 */     return readThumbnail(paramInt1, paramInt2).getWidth();
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
/*      */   public int getThumbnailHeight(int paramInt1, int paramInt2) throws IOException {
/* 1715 */     return readThumbnail(paramInt1, paramInt2).getHeight();
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
/*      */   public BufferedImage readThumbnail(int paramInt1, int paramInt2) throws IOException {
/* 1752 */     throw new UnsupportedOperationException("Thumbnails not supported!");
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
/* 1766 */     this.abortFlag = true;
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
/* 1781 */     return this.abortFlag;
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
/* 1793 */     this.abortFlag = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static List addToList(List<Object> paramList, Object paramObject) {
/* 1801 */     if (paramList == null) {
/* 1802 */       paramList = new ArrayList();
/*      */     }
/* 1804 */     paramList.add(paramObject);
/* 1805 */     return paramList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static List removeFromList(List paramList, Object paramObject) {
/* 1812 */     if (paramList == null) {
/* 1813 */       return paramList;
/*      */     }
/* 1815 */     paramList.remove(paramObject);
/* 1816 */     if (paramList.size() == 0) {
/* 1817 */       paramList = null;
/*      */     }
/* 1819 */     return paramList;
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
/*      */   public void addIIOReadWarningListener(IIOReadWarningListener paramIIOReadWarningListener) {
/* 1836 */     if (paramIIOReadWarningListener == null) {
/*      */       return;
/*      */     }
/* 1839 */     this.warningListeners = addToList(this.warningListeners, paramIIOReadWarningListener);
/* 1840 */     this.warningLocales = addToList(this.warningLocales, getLocale());
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
/*      */   public void removeIIOReadWarningListener(IIOReadWarningListener paramIIOReadWarningListener) {
/* 1854 */     if (paramIIOReadWarningListener == null || this.warningListeners == null) {
/*      */       return;
/*      */     }
/* 1857 */     int i = this.warningListeners.indexOf(paramIIOReadWarningListener);
/* 1858 */     if (i != -1) {
/* 1859 */       this.warningListeners.remove(i);
/* 1860 */       this.warningLocales.remove(i);
/* 1861 */       if (this.warningListeners.size() == 0) {
/* 1862 */         this.warningListeners = null;
/* 1863 */         this.warningLocales = null;
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
/*      */   public void removeAllIIOReadWarningListeners() {
/* 1877 */     this.warningListeners = null;
/* 1878 */     this.warningLocales = null;
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
/*      */   public void addIIOReadProgressListener(IIOReadProgressListener paramIIOReadProgressListener) {
/* 1892 */     if (paramIIOReadProgressListener == null) {
/*      */       return;
/*      */     }
/* 1895 */     this.progressListeners = addToList(this.progressListeners, paramIIOReadProgressListener);
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
/*      */   public void removeIIOReadProgressListener(IIOReadProgressListener paramIIOReadProgressListener) {
/* 1911 */     if (paramIIOReadProgressListener == null || this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 1914 */     this.progressListeners = removeFromList(this.progressListeners, paramIIOReadProgressListener);
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
/*      */   public void removeAllIIOReadProgressListeners() {
/* 1926 */     this.progressListeners = null;
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
/*      */   public void addIIOReadUpdateListener(IIOReadUpdateListener paramIIOReadUpdateListener) {
/* 1964 */     if (paramIIOReadUpdateListener == null) {
/*      */       return;
/*      */     }
/* 1967 */     this.updateListeners = addToList(this.updateListeners, paramIIOReadUpdateListener);
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
/*      */   public void removeIIOReadUpdateListener(IIOReadUpdateListener paramIIOReadUpdateListener) {
/* 1982 */     if (paramIIOReadUpdateListener == null || this.updateListeners == null) {
/*      */       return;
/*      */     }
/* 1985 */     this.updateListeners = removeFromList(this.updateListeners, paramIIOReadUpdateListener);
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
/*      */   public void removeAllIIOReadUpdateListeners() {
/* 1997 */     this.updateListeners = null;
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
/*      */   protected void processSequenceStarted(int paramInt) {
/* 2009 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2012 */     int i = this.progressListeners.size();
/* 2013 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2015 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2016 */       iIOReadProgressListener.sequenceStarted(this, paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processSequenceComplete() {
/* 2027 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2030 */     int i = this.progressListeners.size();
/* 2031 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2033 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2034 */       iIOReadProgressListener.sequenceComplete(this);
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
/*      */   protected void processImageStarted(int paramInt) {
/* 2047 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2050 */     int i = this.progressListeners.size();
/* 2051 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2053 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2054 */       iIOReadProgressListener.imageStarted(this, paramInt);
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
/* 2068 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2071 */     int i = this.progressListeners.size();
/* 2072 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2074 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2075 */       iIOReadProgressListener.imageProgress(this, paramFloat);
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
/* 2086 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2089 */     int i = this.progressListeners.size();
/* 2090 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2092 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2093 */       iIOReadProgressListener.imageComplete(this);
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
/* 2109 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2112 */     int i = this.progressListeners.size();
/* 2113 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2115 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2116 */       iIOReadProgressListener.thumbnailStarted(this, paramInt1, paramInt2);
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
/* 2130 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2133 */     int i = this.progressListeners.size();
/* 2134 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2136 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2137 */       iIOReadProgressListener.thumbnailProgress(this, paramFloat);
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
/* 2148 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2151 */     int i = this.progressListeners.size();
/* 2152 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2154 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2155 */       iIOReadProgressListener.thumbnailComplete(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processReadAborted() {
/* 2166 */     if (this.progressListeners == null) {
/*      */       return;
/*      */     }
/* 2169 */     int i = this.progressListeners.size();
/* 2170 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2172 */       IIOReadProgressListener iIOReadProgressListener = this.progressListeners.get(b);
/* 2173 */       iIOReadProgressListener.readAborted(this);
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
/*      */   protected void processPassStarted(BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfint) {
/* 2202 */     if (this.updateListeners == null) {
/*      */       return;
/*      */     }
/* 2205 */     int i = this.updateListeners.size();
/* 2206 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2208 */       IIOReadUpdateListener iIOReadUpdateListener = this.updateListeners.get(b);
/* 2209 */       iIOReadUpdateListener.passStarted(this, paramBufferedImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramArrayOfint);
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
/*      */   protected void processImageUpdate(BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/* 2243 */     if (this.updateListeners == null) {
/*      */       return;
/*      */     }
/* 2246 */     int i = this.updateListeners.size();
/* 2247 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2249 */       IIOReadUpdateListener iIOReadUpdateListener = this.updateListeners.get(b);
/* 2250 */       iIOReadUpdateListener.imageUpdate(this, paramBufferedImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
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
/*      */   protected void processPassComplete(BufferedImage paramBufferedImage) {
/* 2268 */     if (this.updateListeners == null) {
/*      */       return;
/*      */     }
/* 2271 */     int i = this.updateListeners.size();
/* 2272 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2274 */       IIOReadUpdateListener iIOReadUpdateListener = this.updateListeners.get(b);
/* 2275 */       iIOReadUpdateListener.passComplete(this, paramBufferedImage);
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
/*      */   protected void processThumbnailPassStarted(BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfint) {
/* 2305 */     if (this.updateListeners == null) {
/*      */       return;
/*      */     }
/* 2308 */     int i = this.updateListeners.size();
/* 2309 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2311 */       IIOReadUpdateListener iIOReadUpdateListener = this.updateListeners.get(b);
/* 2312 */       iIOReadUpdateListener.thumbnailPassStarted(this, paramBufferedImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramArrayOfint);
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
/*      */   
/*      */   protected void processThumbnailUpdate(BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint) {
/* 2347 */     if (this.updateListeners == null) {
/*      */       return;
/*      */     }
/* 2350 */     int i = this.updateListeners.size();
/* 2351 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2353 */       IIOReadUpdateListener iIOReadUpdateListener = this.updateListeners.get(b);
/* 2354 */       iIOReadUpdateListener.thumbnailUpdate(this, paramBufferedImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfint);
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
/*      */   protected void processThumbnailPassComplete(BufferedImage paramBufferedImage) {
/* 2373 */     if (this.updateListeners == null) {
/*      */       return;
/*      */     }
/* 2376 */     int i = this.updateListeners.size();
/* 2377 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2379 */       IIOReadUpdateListener iIOReadUpdateListener = this.updateListeners.get(b);
/* 2380 */       iIOReadUpdateListener.thumbnailPassComplete(this, paramBufferedImage);
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
/*      */   protected void processWarningOccurred(String paramString) {
/* 2396 */     if (this.warningListeners == null) {
/*      */       return;
/*      */     }
/* 2399 */     if (paramString == null) {
/* 2400 */       throw new IllegalArgumentException("warning == null!");
/*      */     }
/* 2402 */     int i = this.warningListeners.size();
/* 2403 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2405 */       IIOReadWarningListener iIOReadWarningListener = this.warningListeners.get(b);
/*      */       
/* 2407 */       iIOReadWarningListener.warningOccurred(this, paramString);
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
/*      */   protected void processWarningOccurred(String paramString1, String paramString2) {
/* 2438 */     if (this.warningListeners == null) {
/*      */       return;
/*      */     }
/* 2441 */     if (paramString1 == null) {
/* 2442 */       throw new IllegalArgumentException("baseName == null!");
/*      */     }
/* 2444 */     if (paramString2 == null) {
/* 2445 */       throw new IllegalArgumentException("keyword == null!");
/*      */     }
/* 2447 */     int i = this.warningListeners.size();
/* 2448 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 2450 */       IIOReadWarningListener iIOReadWarningListener = this.warningListeners.get(b);
/* 2451 */       Locale locale = this.warningLocales.get(b);
/* 2452 */       if (locale == null) {
/* 2453 */         locale = Locale.getDefault();
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
/* 2465 */       ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*      */           {
/*      */             public Object run() {
/* 2468 */               return Thread.currentThread().getContextClassLoader();
/*      */             }
/*      */           });
/*      */       
/* 2472 */       ResourceBundle resourceBundle = null;
/*      */       try {
/* 2474 */         resourceBundle = ResourceBundle.getBundle(paramString1, locale, classLoader);
/* 2475 */       } catch (MissingResourceException missingResourceException) {
/*      */         try {
/* 2477 */           resourceBundle = ResourceBundle.getBundle(paramString1, locale);
/* 2478 */         } catch (MissingResourceException missingResourceException1) {
/* 2479 */           throw new IllegalArgumentException("Bundle not found!");
/*      */         } 
/*      */       } 
/*      */       
/* 2483 */       String str = null;
/*      */       try {
/* 2485 */         str = resourceBundle.getString(paramString2);
/* 2486 */       } catch (ClassCastException classCastException) {
/* 2487 */         throw new IllegalArgumentException("Resource is not a String!");
/* 2488 */       } catch (MissingResourceException missingResourceException) {
/* 2489 */         throw new IllegalArgumentException("Resource is missing!");
/*      */       } 
/*      */       
/* 2492 */       iIOReadWarningListener.warningOccurred(this, str);
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
/*      */   public void reset() {
/* 2509 */     setInput(null, false, false);
/* 2510 */     setLocale(null);
/* 2511 */     removeAllIIOReadUpdateListeners();
/* 2512 */     removeAllIIOReadProgressListeners();
/* 2513 */     removeAllIIOReadWarningListeners();
/* 2514 */     clearAbortRequest();
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
/*      */   public void dispose() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Rectangle getSourceRegion(ImageReadParam paramImageReadParam, int paramInt1, int paramInt2) {
/* 2557 */     Rectangle rectangle = new Rectangle(0, 0, paramInt1, paramInt2);
/* 2558 */     if (paramImageReadParam != null) {
/* 2559 */       Rectangle rectangle1 = paramImageReadParam.getSourceRegion();
/* 2560 */       if (rectangle1 != null) {
/* 2561 */         rectangle = rectangle.intersection(rectangle1);
/*      */       }
/*      */       
/* 2564 */       int i = paramImageReadParam.getSubsamplingXOffset();
/* 2565 */       int j = paramImageReadParam.getSubsamplingYOffset();
/* 2566 */       rectangle.x += i;
/* 2567 */       rectangle.y += j;
/* 2568 */       rectangle.width -= i;
/* 2569 */       rectangle.height -= j;
/*      */     } 
/*      */     
/* 2572 */     return rectangle;
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
/*      */   protected static void computeRegions(ImageReadParam paramImageReadParam, int paramInt1, int paramInt2, BufferedImage paramBufferedImage, Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 2625 */     if (paramRectangle1 == null) {
/* 2626 */       throw new IllegalArgumentException("srcRegion == null!");
/*      */     }
/* 2628 */     if (paramRectangle2 == null) {
/* 2629 */       throw new IllegalArgumentException("destRegion == null!");
/*      */     }
/*      */ 
/*      */     
/* 2633 */     paramRectangle1.setBounds(0, 0, paramInt1, paramInt2);
/*      */ 
/*      */ 
/*      */     
/* 2637 */     paramRectangle2.setBounds(0, 0, paramInt1, paramInt2);
/*      */ 
/*      */     
/* 2640 */     int i = 1;
/* 2641 */     int j = 1;
/* 2642 */     int k = 0;
/* 2643 */     int m = 0;
/* 2644 */     if (paramImageReadParam != null) {
/* 2645 */       Rectangle rectangle = paramImageReadParam.getSourceRegion();
/* 2646 */       if (rectangle != null) {
/* 2647 */         paramRectangle1.setBounds(paramRectangle1.intersection(rectangle));
/*      */       }
/* 2649 */       i = paramImageReadParam.getSourceXSubsampling();
/* 2650 */       j = paramImageReadParam.getSourceYSubsampling();
/* 2651 */       k = paramImageReadParam.getSubsamplingXOffset();
/* 2652 */       m = paramImageReadParam.getSubsamplingYOffset();
/* 2653 */       paramRectangle1.translate(k, m);
/* 2654 */       paramRectangle1.width -= k;
/* 2655 */       paramRectangle1.height -= m;
/* 2656 */       paramRectangle2.setLocation(paramImageReadParam.getDestinationOffset());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2661 */     if (paramRectangle2.x < 0) {
/* 2662 */       int i2 = -paramRectangle2.x * i;
/* 2663 */       paramRectangle1.x += i2;
/* 2664 */       paramRectangle1.width -= i2;
/* 2665 */       paramRectangle2.x = 0;
/*      */     } 
/* 2667 */     if (paramRectangle2.y < 0) {
/* 2668 */       int i2 = -paramRectangle2.y * j;
/* 2669 */       paramRectangle1.y += i2;
/* 2670 */       paramRectangle1.height -= i2;
/* 2671 */       paramRectangle2.y = 0;
/*      */     } 
/*      */ 
/*      */     
/* 2675 */     int n = (paramRectangle1.width + i - 1) / i;
/* 2676 */     int i1 = (paramRectangle1.height + j - 1) / j;
/* 2677 */     paramRectangle2.width = n;
/* 2678 */     paramRectangle2.height = i1;
/*      */ 
/*      */ 
/*      */     
/* 2682 */     if (paramBufferedImage != null) {
/*      */ 
/*      */       
/* 2685 */       Rectangle rectangle = new Rectangle(0, 0, paramBufferedImage.getWidth(), paramBufferedImage.getHeight());
/* 2686 */       paramRectangle2.setBounds(paramRectangle2.intersection(rectangle));
/* 2687 */       if (paramRectangle2.isEmpty()) {
/* 2688 */         throw new IllegalArgumentException("Empty destination region!");
/*      */       }
/*      */ 
/*      */       
/* 2692 */       int i2 = paramRectangle2.x + n - paramBufferedImage.getWidth();
/* 2693 */       if (i2 > 0) {
/* 2694 */         paramRectangle1.width -= i2 * i;
/*      */       }
/* 2696 */       int i3 = paramRectangle2.y + i1 - paramBufferedImage.getHeight();
/* 2697 */       if (i3 > 0) {
/* 2698 */         paramRectangle1.height -= i3 * j;
/*      */       }
/*      */     } 
/* 2701 */     if (paramRectangle1.isEmpty() || paramRectangle2.isEmpty()) {
/* 2702 */       throw new IllegalArgumentException("Empty region!");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void checkReadParamBandSettings(ImageReadParam paramImageReadParam, int paramInt1, int paramInt2) {
/* 2746 */     int[] arrayOfInt1 = null;
/* 2747 */     int[] arrayOfInt2 = null;
/* 2748 */     if (paramImageReadParam != null) {
/* 2749 */       arrayOfInt1 = paramImageReadParam.getSourceBands();
/* 2750 */       arrayOfInt2 = paramImageReadParam.getDestinationBands();
/*      */     } 
/*      */     
/* 2753 */     int i = (arrayOfInt1 == null) ? paramInt1 : arrayOfInt1.length;
/*      */     
/* 2755 */     int j = (arrayOfInt2 == null) ? paramInt2 : arrayOfInt2.length;
/*      */ 
/*      */     
/* 2758 */     if (i != j) {
/* 2759 */       throw new IllegalArgumentException("ImageReadParam num source & dest bands differ!");
/*      */     }
/*      */     
/* 2762 */     if (arrayOfInt1 != null) {
/* 2763 */       for (byte b = 0; b < arrayOfInt1.length; b++) {
/* 2764 */         if (arrayOfInt1[b] >= paramInt1) {
/* 2765 */           throw new IllegalArgumentException("ImageReadParam source bands contains a value >= the number of source bands!");
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2770 */     if (arrayOfInt2 != null) {
/* 2771 */       for (byte b = 0; b < arrayOfInt2.length; b++) {
/* 2772 */         if (arrayOfInt2[b] >= paramInt2) {
/* 2773 */           throw new IllegalArgumentException("ImageReadParam dest bands contains a value >= the number of dest bands!");
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static BufferedImage getDestination(ImageReadParam paramImageReadParam, Iterator<ImageTypeSpecifier> paramIterator, int paramInt1, int paramInt2) throws IIOException {
/* 2832 */     if (paramIterator == null || !paramIterator.hasNext()) {
/* 2833 */       throw new IllegalArgumentException("imageTypes null or empty!");
/*      */     }
/* 2835 */     if (paramInt1 * paramInt2 > 2147483647L) {
/* 2836 */       throw new IllegalArgumentException("width*height > Integer.MAX_VALUE!");
/*      */     }
/*      */ 
/*      */     
/* 2840 */     BufferedImage bufferedImage = null;
/* 2841 */     ImageTypeSpecifier imageTypeSpecifier = null;
/*      */ 
/*      */     
/* 2844 */     if (paramImageReadParam != null) {
/*      */       
/* 2846 */       bufferedImage = paramImageReadParam.getDestination();
/* 2847 */       if (bufferedImage != null) {
/* 2848 */         return bufferedImage;
/*      */       }
/*      */ 
/*      */       
/* 2852 */       imageTypeSpecifier = paramImageReadParam.getDestinationType();
/*      */     } 
/*      */ 
/*      */     
/* 2856 */     if (imageTypeSpecifier == null) {
/* 2857 */       ImageTypeSpecifier imageTypeSpecifier1 = (ImageTypeSpecifier)paramIterator.next();
/* 2858 */       if (!(imageTypeSpecifier1 instanceof ImageTypeSpecifier)) {
/* 2859 */         throw new IllegalArgumentException("Non-ImageTypeSpecifier retrieved from imageTypes!");
/*      */       }
/*      */       
/* 2862 */       imageTypeSpecifier = imageTypeSpecifier1;
/*      */     } else {
/* 2864 */       boolean bool = false;
/* 2865 */       while (paramIterator.hasNext()) {
/*      */         
/* 2867 */         ImageTypeSpecifier imageTypeSpecifier1 = paramIterator.next();
/* 2868 */         if (imageTypeSpecifier1.equals(imageTypeSpecifier)) {
/* 2869 */           bool = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 2874 */       if (!bool) {
/* 2875 */         throw new IIOException("Destination type from ImageReadParam does not match!");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2880 */     Rectangle rectangle1 = new Rectangle(0, 0, 0, 0);
/* 2881 */     Rectangle rectangle2 = new Rectangle(0, 0, 0, 0);
/* 2882 */     computeRegions(paramImageReadParam, paramInt1, paramInt2, null, rectangle1, rectangle2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2889 */     int i = rectangle2.x + rectangle2.width;
/* 2890 */     int j = rectangle2.y + rectangle2.height;
/*      */     
/* 2892 */     return imageTypeSpecifier.createBufferedImage(i, j);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/ImageReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */