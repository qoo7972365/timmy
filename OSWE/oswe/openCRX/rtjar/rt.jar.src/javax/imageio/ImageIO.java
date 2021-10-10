/*      */ package javax.imageio;
/*      */ 
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.io.File;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import javax.imageio.spi.IIORegistry;
/*      */ import javax.imageio.spi.ImageInputStreamSpi;
/*      */ import javax.imageio.spi.ImageOutputStreamSpi;
/*      */ import javax.imageio.spi.ImageReaderSpi;
/*      */ import javax.imageio.spi.ImageReaderWriterSpi;
/*      */ import javax.imageio.spi.ImageTranscoderSpi;
/*      */ import javax.imageio.spi.ImageWriterSpi;
/*      */ import javax.imageio.spi.ServiceRegistry;
/*      */ import javax.imageio.stream.ImageInputStream;
/*      */ import javax.imageio.stream.ImageOutputStream;
/*      */ import sun.awt.AppContext;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ImageIO
/*      */ {
/*   66 */   private static final IIORegistry theRegistry = IIORegistry.getDefaultInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method readerFormatNamesMethod;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method readerFileSuffixesMethod;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method readerMIMETypesMethod;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method writerFormatNamesMethod;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method writerFileSuffixesMethod;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method writerMIMETypesMethod;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void scanForPlugins() {
/*  110 */     theRegistry.registerApplicationClasspathSpis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class CacheInfo
/*      */   {
/*      */     boolean useCache = true;
/*      */ 
/*      */ 
/*      */     
/*  122 */     File cacheDirectory = null;
/*  123 */     Boolean hasPermission = null;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getUseCache() {
/*  128 */       return this.useCache;
/*      */     }
/*      */     
/*      */     public void setUseCache(boolean param1Boolean) {
/*  132 */       this.useCache = param1Boolean;
/*      */     }
/*      */     
/*      */     public File getCacheDirectory() {
/*  136 */       return this.cacheDirectory;
/*      */     }
/*      */     
/*      */     public void setCacheDirectory(File param1File) {
/*  140 */       this.cacheDirectory = param1File;
/*      */     }
/*      */     
/*      */     public Boolean getHasPermission() {
/*  144 */       return this.hasPermission;
/*      */     }
/*      */     
/*      */     public void setHasPermission(Boolean param1Boolean) {
/*  148 */       this.hasPermission = param1Boolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized CacheInfo getCacheInfo() {
/*  157 */     AppContext appContext = AppContext.getAppContext();
/*  158 */     CacheInfo cacheInfo = (CacheInfo)appContext.get(CacheInfo.class);
/*  159 */     if (cacheInfo == null) {
/*  160 */       cacheInfo = new CacheInfo();
/*  161 */       appContext.put(CacheInfo.class, cacheInfo);
/*      */     } 
/*  163 */     return cacheInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getTempDir() {
/*  171 */     GetPropertyAction getPropertyAction = new GetPropertyAction("java.io.tmpdir");
/*  172 */     return AccessController.<String>doPrivileged(getPropertyAction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasCachePermission() {
/*  183 */     Boolean bool = getCacheInfo().getHasPermission();
/*      */     
/*  185 */     if (bool != null) {
/*  186 */       return bool.booleanValue();
/*      */     }
/*      */     try {
/*  189 */       SecurityManager securityManager = System.getSecurityManager();
/*  190 */       if (securityManager != null) {
/*  191 */         String str1; File file = getCacheDirectory();
/*      */ 
/*      */         
/*  194 */         if (file != null) {
/*  195 */           str1 = file.getPath();
/*      */         } else {
/*  197 */           str1 = getTempDir();
/*      */           
/*  199 */           if (str1 == null || str1.isEmpty()) {
/*  200 */             getCacheInfo().setHasPermission(Boolean.FALSE);
/*  201 */             return false;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  208 */         String str2 = str1;
/*  209 */         if (!str2.endsWith(File.separator)) {
/*  210 */           str2 = str2 + File.separator;
/*      */         }
/*  212 */         str2 = str2 + "*";
/*      */         
/*  214 */         securityManager.checkPermission(new FilePermission(str2, "read, write, delete"));
/*      */       } 
/*  216 */     } catch (SecurityException securityException) {
/*  217 */       getCacheInfo().setHasPermission(Boolean.FALSE);
/*  218 */       return false;
/*      */     } 
/*      */     
/*  221 */     getCacheInfo().setHasPermission(Boolean.TRUE);
/*  222 */     return true;
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
/*      */   public static void setUseCache(boolean paramBoolean) {
/*  253 */     getCacheInfo().setUseCache(paramBoolean);
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
/*      */   public static boolean getUseCache() {
/*  267 */     return getCacheInfo().getUseCache();
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
/*      */   public static void setCacheDirectory(File paramFile) {
/*  288 */     if (paramFile != null && !paramFile.isDirectory()) {
/*  289 */       throw new IllegalArgumentException("Not a directory!");
/*      */     }
/*  291 */     getCacheInfo().setCacheDirectory(paramFile);
/*  292 */     getCacheInfo().setHasPermission(null);
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
/*      */   public static File getCacheDirectory() {
/*  307 */     return getCacheInfo().getCacheDirectory();
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
/*      */   public static ImageInputStream createImageInputStream(Object paramObject) throws IOException {
/*      */     Iterator<ImageInputStreamSpi> iterator;
/*  338 */     if (paramObject == null) {
/*  339 */       throw new IllegalArgumentException("input == null!");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  345 */       iterator = theRegistry.getServiceProviders(ImageInputStreamSpi.class, true);
/*      */     }
/*  347 */     catch (IllegalArgumentException illegalArgumentException) {
/*  348 */       return null;
/*      */     } 
/*      */     
/*  351 */     boolean bool = (getUseCache() && hasCachePermission()) ? true : false;
/*      */     
/*  353 */     while (iterator.hasNext()) {
/*  354 */       ImageInputStreamSpi imageInputStreamSpi = iterator.next();
/*  355 */       if (imageInputStreamSpi.getInputClass().isInstance(paramObject)) {
/*      */         try {
/*  357 */           return imageInputStreamSpi.createInputStreamInstance(paramObject, bool, 
/*      */               
/*  359 */               getCacheDirectory());
/*  360 */         } catch (IOException iOException) {
/*  361 */           throw new IIOException("Can't create cache file!", iOException);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  366 */     return null;
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
/*      */   public static ImageOutputStream createImageOutputStream(Object paramObject) throws IOException {
/*      */     Iterator<ImageOutputStreamSpi> iterator;
/*  400 */     if (paramObject == null) {
/*  401 */       throw new IllegalArgumentException("output == null!");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  407 */       iterator = theRegistry.getServiceProviders(ImageOutputStreamSpi.class, true);
/*      */     }
/*  409 */     catch (IllegalArgumentException illegalArgumentException) {
/*  410 */       return null;
/*      */     } 
/*      */     
/*  413 */     boolean bool = (getUseCache() && hasCachePermission()) ? true : false;
/*      */     
/*  415 */     while (iterator.hasNext()) {
/*  416 */       ImageOutputStreamSpi imageOutputStreamSpi = iterator.next();
/*  417 */       if (imageOutputStreamSpi.getOutputClass().isInstance(paramObject)) {
/*      */         try {
/*  419 */           return imageOutputStreamSpi.createOutputStreamInstance(paramObject, bool, 
/*      */               
/*  421 */               getCacheDirectory());
/*  422 */         } catch (IOException iOException) {
/*  423 */           throw new IIOException("Can't create cache file!", iOException);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  428 */     return null;
/*      */   }
/*      */   
/*      */   private enum SpiInfo {
/*  432 */     FORMAT_NAMES
/*      */     {
/*      */       String[] info(ImageReaderWriterSpi param2ImageReaderWriterSpi) {
/*  435 */         return param2ImageReaderWriterSpi.getFormatNames();
/*      */       }
/*      */     },
/*  438 */     MIME_TYPES
/*      */     {
/*      */       String[] info(ImageReaderWriterSpi param2ImageReaderWriterSpi) {
/*  441 */         return param2ImageReaderWriterSpi.getMIMETypes();
/*      */       }
/*      */     },
/*  444 */     FILE_SUFFIXES
/*      */     {
/*      */       String[] info(ImageReaderWriterSpi param2ImageReaderWriterSpi) {
/*  447 */         return param2ImageReaderWriterSpi.getFileSuffixes();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */     
/*      */     abstract String[] info(ImageReaderWriterSpi param1ImageReaderWriterSpi);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <S extends ImageReaderWriterSpi> String[] getReaderWriterInfo(Class<S> paramClass, SpiInfo paramSpiInfo) {
/*      */     Iterator<S> iterator;
/*      */     try {
/*  460 */       iterator = theRegistry.getServiceProviders(paramClass, true);
/*  461 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  462 */       return new String[0];
/*      */     } 
/*      */     
/*  465 */     HashSet<? super String> hashSet = new HashSet();
/*  466 */     while (iterator.hasNext()) {
/*  467 */       ImageReaderWriterSpi imageReaderWriterSpi = (ImageReaderWriterSpi)iterator.next();
/*  468 */       Collections.addAll(hashSet, paramSpiInfo.info(imageReaderWriterSpi));
/*      */     } 
/*      */     
/*  471 */     return hashSet.<String>toArray(new String[hashSet.size()]);
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
/*      */   public static String[] getReaderFormatNames() {
/*  484 */     return getReaderWriterInfo(ImageReaderSpi.class, SpiInfo.FORMAT_NAMES);
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
/*      */   public static String[] getReaderMIMETypes() {
/*  496 */     return getReaderWriterInfo(ImageReaderSpi.class, SpiInfo.MIME_TYPES);
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
/*      */   public static String[] getReaderFileSuffixes() {
/*  509 */     return getReaderWriterInfo(ImageReaderSpi.class, SpiInfo.FILE_SUFFIXES);
/*      */   }
/*      */   
/*      */   static class ImageReaderIterator
/*      */     implements Iterator<ImageReader>
/*      */   {
/*      */     public Iterator iter;
/*      */     
/*      */     public ImageReaderIterator(Iterator param1Iterator) {
/*  518 */       this.iter = param1Iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  522 */       return this.iter.hasNext();
/*      */     }
/*      */     
/*      */     public ImageReader next() {
/*  526 */       ImageReaderSpi imageReaderSpi = null;
/*      */       try {
/*  528 */         imageReaderSpi = this.iter.next();
/*  529 */         return imageReaderSpi.createReaderInstance();
/*  530 */       } catch (IOException iOException) {
/*      */ 
/*      */         
/*  533 */         ImageIO.theRegistry.deregisterServiceProvider(imageReaderSpi, ImageReaderSpi.class);
/*      */         
/*  535 */         return null;
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  539 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   static class CanDecodeInputFilter
/*      */     implements ServiceRegistry.Filter
/*      */   {
/*      */     Object input;
/*      */     
/*      */     public CanDecodeInputFilter(Object param1Object) {
/*  549 */       this.input = param1Object;
/*      */     }
/*      */     
/*      */     public boolean filter(Object param1Object) {
/*      */       try {
/*  554 */         ImageReaderSpi imageReaderSpi = (ImageReaderSpi)param1Object;
/*  555 */         ImageInputStream imageInputStream = null;
/*  556 */         if (this.input instanceof ImageInputStream) {
/*  557 */           imageInputStream = (ImageInputStream)this.input;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  563 */         boolean bool = false;
/*  564 */         if (imageInputStream != null) {
/*  565 */           imageInputStream.mark();
/*      */         }
/*  567 */         bool = imageReaderSpi.canDecodeInput(this.input);
/*  568 */         if (imageInputStream != null) {
/*  569 */           imageInputStream.reset();
/*      */         }
/*      */         
/*  572 */         return bool;
/*  573 */       } catch (IOException iOException) {
/*  574 */         return false;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CanEncodeImageAndFormatFilter
/*      */     implements ServiceRegistry.Filter
/*      */   {
/*      */     ImageTypeSpecifier type;
/*      */     String formatName;
/*      */     
/*      */     public CanEncodeImageAndFormatFilter(ImageTypeSpecifier param1ImageTypeSpecifier, String param1String) {
/*  587 */       this.type = param1ImageTypeSpecifier;
/*  588 */       this.formatName = param1String;
/*      */     }
/*      */     
/*      */     public boolean filter(Object param1Object) {
/*  592 */       ImageWriterSpi imageWriterSpi = (ImageWriterSpi)param1Object;
/*  593 */       return (Arrays.<String>asList(imageWriterSpi.getFormatNames()).contains(this.formatName) && imageWriterSpi
/*  594 */         .canEncodeImage(this.type));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ContainsFilter
/*      */     implements ServiceRegistry.Filter
/*      */   {
/*      */     Method method;
/*      */     
/*      */     String name;
/*      */     
/*      */     public ContainsFilter(Method param1Method, String param1String) {
/*  607 */       this.method = param1Method;
/*  608 */       this.name = param1String;
/*      */     }
/*      */     
/*      */     public boolean filter(Object param1Object) {
/*      */       try {
/*  613 */         return ImageIO.contains((String[])this.method.invoke(param1Object, new Object[0]), this.name);
/*  614 */       } catch (Exception exception) {
/*  615 */         return false;
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
/*      */   public static Iterator<ImageReader> getImageReaders(Object paramObject) {
/*      */     Iterator<ImageReaderSpi> iterator;
/*  640 */     if (paramObject == null) {
/*  641 */       throw new IllegalArgumentException("input == null!");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  646 */       iterator = theRegistry.getServiceProviders(ImageReaderSpi.class, new CanDecodeInputFilter(paramObject), true);
/*      */     
/*      */     }
/*  649 */     catch (IllegalArgumentException illegalArgumentException) {
/*  650 */       return Collections.emptyIterator();
/*      */     } 
/*      */     
/*  653 */     return new ImageReaderIterator(iterator);
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
/*      */   static {
/*      */     try {
/*  666 */       readerFormatNamesMethod = ImageReaderSpi.class.getMethod("getFormatNames", new Class[0]);
/*      */       
/*  668 */       readerFileSuffixesMethod = ImageReaderSpi.class.getMethod("getFileSuffixes", new Class[0]);
/*      */       
/*  670 */       readerMIMETypesMethod = ImageReaderSpi.class.getMethod("getMIMETypes", new Class[0]);
/*      */ 
/*      */       
/*  673 */       writerFormatNamesMethod = ImageWriterSpi.class.getMethod("getFormatNames", new Class[0]);
/*      */       
/*  675 */       writerFileSuffixesMethod = ImageWriterSpi.class.getMethod("getFileSuffixes", new Class[0]);
/*      */       
/*  677 */       writerMIMETypesMethod = ImageWriterSpi.class.getMethod("getMIMETypes", new Class[0]);
/*  678 */     } catch (NoSuchMethodException noSuchMethodException) {
/*  679 */       noSuchMethodException.printStackTrace();
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
/*      */   public static Iterator<ImageReader> getImageReadersByFormatName(String paramString) {
/*      */     Iterator<ImageReaderSpi> iterator;
/*  702 */     if (paramString == null) {
/*  703 */       throw new IllegalArgumentException("formatName == null!");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  708 */       iterator = theRegistry.getServiceProviders(ImageReaderSpi.class, new ContainsFilter(readerFormatNamesMethod, paramString), true);
/*      */ 
/*      */     
/*      */     }
/*  712 */     catch (IllegalArgumentException illegalArgumentException) {
/*  713 */       return Collections.emptyIterator();
/*      */     } 
/*  715 */     return new ImageReaderIterator(iterator);
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
/*      */   public static Iterator<ImageReader> getImageReadersBySuffix(String paramString) {
/*      */     Iterator<ImageReaderSpi> iterator;
/*  737 */     if (paramString == null) {
/*  738 */       throw new IllegalArgumentException("fileSuffix == null!");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  743 */       iterator = theRegistry.getServiceProviders(ImageReaderSpi.class, new ContainsFilter(readerFileSuffixesMethod, paramString), true);
/*      */ 
/*      */     
/*      */     }
/*  747 */     catch (IllegalArgumentException illegalArgumentException) {
/*  748 */       return Collections.emptyIterator();
/*      */     } 
/*  750 */     return new ImageReaderIterator(iterator);
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
/*      */   public static Iterator<ImageReader> getImageReadersByMIMEType(String paramString) {
/*      */     Iterator<ImageReaderSpi> iterator;
/*  772 */     if (paramString == null) {
/*  773 */       throw new IllegalArgumentException("MIMEType == null!");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  778 */       iterator = theRegistry.getServiceProviders(ImageReaderSpi.class, new ContainsFilter(readerMIMETypesMethod, paramString), true);
/*      */ 
/*      */     
/*      */     }
/*  782 */     catch (IllegalArgumentException illegalArgumentException) {
/*  783 */       return Collections.emptyIterator();
/*      */     } 
/*  785 */     return new ImageReaderIterator(iterator);
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
/*      */   public static String[] getWriterFormatNames() {
/*  798 */     return getReaderWriterInfo(ImageWriterSpi.class, SpiInfo.FORMAT_NAMES);
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
/*      */   public static String[] getWriterMIMETypes() {
/*  810 */     return getReaderWriterInfo(ImageWriterSpi.class, SpiInfo.MIME_TYPES);
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
/*      */   public static String[] getWriterFileSuffixes() {
/*  823 */     return getReaderWriterInfo(ImageWriterSpi.class, SpiInfo.FILE_SUFFIXES);
/*      */   }
/*      */   
/*      */   static class ImageWriterIterator
/*      */     implements Iterator<ImageWriter>
/*      */   {
/*      */     public Iterator iter;
/*      */     
/*      */     public ImageWriterIterator(Iterator param1Iterator) {
/*  832 */       this.iter = param1Iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  836 */       return this.iter.hasNext();
/*      */     }
/*      */     
/*      */     public ImageWriter next() {
/*  840 */       ImageWriterSpi imageWriterSpi = null;
/*      */       try {
/*  842 */         imageWriterSpi = this.iter.next();
/*  843 */         return imageWriterSpi.createWriterInstance();
/*  844 */       } catch (IOException iOException) {
/*      */         
/*  846 */         ImageIO.theRegistry.deregisterServiceProvider(imageWriterSpi, ImageWriterSpi.class);
/*      */         
/*  848 */         return null;
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  852 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean contains(String[] paramArrayOfString, String paramString) {
/*  857 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  858 */       if (paramString.equalsIgnoreCase(paramArrayOfString[b])) {
/*  859 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  863 */     return false;
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
/*      */   public static Iterator<ImageWriter> getImageWritersByFormatName(String paramString) {
/*      */     Iterator<ImageWriterSpi> iterator;
/*  885 */     if (paramString == null) {
/*  886 */       throw new IllegalArgumentException("formatName == null!");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  891 */       iterator = theRegistry.getServiceProviders(ImageWriterSpi.class, new ContainsFilter(writerFormatNamesMethod, paramString), true);
/*      */ 
/*      */     
/*      */     }
/*  895 */     catch (IllegalArgumentException illegalArgumentException) {
/*  896 */       return Collections.emptyIterator();
/*      */     } 
/*  898 */     return new ImageWriterIterator(iterator);
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
/*      */   public static Iterator<ImageWriter> getImageWritersBySuffix(String paramString) {
/*      */     Iterator<ImageWriterSpi> iterator;
/*  919 */     if (paramString == null) {
/*  920 */       throw new IllegalArgumentException("fileSuffix == null!");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  925 */       iterator = theRegistry.getServiceProviders(ImageWriterSpi.class, new ContainsFilter(writerFileSuffixesMethod, paramString), true);
/*      */ 
/*      */     
/*      */     }
/*  929 */     catch (IllegalArgumentException illegalArgumentException) {
/*  930 */       return Collections.emptyIterator();
/*      */     } 
/*  932 */     return new ImageWriterIterator(iterator);
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
/*      */   public static Iterator<ImageWriter> getImageWritersByMIMEType(String paramString) {
/*      */     Iterator<ImageWriterSpi> iterator;
/*  953 */     if (paramString == null) {
/*  954 */       throw new IllegalArgumentException("MIMEType == null!");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  959 */       iterator = theRegistry.getServiceProviders(ImageWriterSpi.class, new ContainsFilter(writerMIMETypesMethod, paramString), true);
/*      */ 
/*      */     
/*      */     }
/*  963 */     catch (IllegalArgumentException illegalArgumentException) {
/*  964 */       return Collections.emptyIterator();
/*      */     } 
/*  966 */     return new ImageWriterIterator(iterator);
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
/*      */   public static ImageWriter getImageWriter(ImageReader paramImageReader) {
/*  999 */     if (paramImageReader == null) {
/* 1000 */       throw new IllegalArgumentException("reader == null!");
/*      */     }
/*      */     
/* 1003 */     ImageReaderSpi imageReaderSpi = paramImageReader.getOriginatingProvider();
/* 1004 */     if (imageReaderSpi == null) {
/*      */       Iterator<ImageReaderSpi> iterator;
/*      */ 
/*      */       
/*      */       try {
/* 1009 */         iterator = theRegistry.getServiceProviders(ImageReaderSpi.class, false);
/*      */       }
/* 1011 */       catch (IllegalArgumentException illegalArgumentException) {
/* 1012 */         return null;
/*      */       } 
/*      */       
/* 1015 */       while (iterator.hasNext()) {
/* 1016 */         ImageReaderSpi imageReaderSpi1 = iterator.next();
/* 1017 */         if (imageReaderSpi1.isOwnReader(paramImageReader)) {
/* 1018 */           imageReaderSpi = imageReaderSpi1;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1022 */       if (imageReaderSpi == null) {
/* 1023 */         return null;
/*      */       }
/*      */     } 
/*      */     
/* 1027 */     String[] arrayOfString = imageReaderSpi.getImageWriterSpiNames();
/* 1028 */     if (arrayOfString == null) {
/* 1029 */       return null;
/*      */     }
/*      */     
/* 1032 */     Class<?> clazz = null;
/*      */     try {
/* 1034 */       clazz = Class.forName(arrayOfString[0], true, 
/* 1035 */           ClassLoader.getSystemClassLoader());
/* 1036 */     } catch (ClassNotFoundException classNotFoundException) {
/* 1037 */       return null;
/*      */     } 
/*      */ 
/*      */     
/* 1041 */     ImageWriterSpi imageWriterSpi = (ImageWriterSpi)theRegistry.getServiceProviderByClass(clazz);
/* 1042 */     if (imageWriterSpi == null) {
/* 1043 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1047 */       return imageWriterSpi.createWriterInstance();
/* 1048 */     } catch (IOException iOException) {
/*      */       
/* 1050 */       theRegistry.deregisterServiceProvider(imageWriterSpi, ImageWriterSpi.class);
/*      */       
/* 1052 */       return null;
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
/*      */   public static ImageReader getImageReader(ImageWriter paramImageWriter) {
/* 1079 */     if (paramImageWriter == null) {
/* 1080 */       throw new IllegalArgumentException("writer == null!");
/*      */     }
/*      */     
/* 1083 */     ImageWriterSpi imageWriterSpi = paramImageWriter.getOriginatingProvider();
/* 1084 */     if (imageWriterSpi == null) {
/*      */       Iterator<ImageWriterSpi> iterator;
/*      */ 
/*      */       
/*      */       try {
/* 1089 */         iterator = theRegistry.getServiceProviders(ImageWriterSpi.class, false);
/*      */       }
/* 1091 */       catch (IllegalArgumentException illegalArgumentException) {
/* 1092 */         return null;
/*      */       } 
/*      */       
/* 1095 */       while (iterator.hasNext()) {
/* 1096 */         ImageWriterSpi imageWriterSpi1 = iterator.next();
/* 1097 */         if (imageWriterSpi1.isOwnWriter(paramImageWriter)) {
/* 1098 */           imageWriterSpi = imageWriterSpi1;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1102 */       if (imageWriterSpi == null) {
/* 1103 */         return null;
/*      */       }
/*      */     } 
/*      */     
/* 1107 */     String[] arrayOfString = imageWriterSpi.getImageReaderSpiNames();
/* 1108 */     if (arrayOfString == null) {
/* 1109 */       return null;
/*      */     }
/*      */     
/* 1112 */     Class<?> clazz = null;
/*      */     try {
/* 1114 */       clazz = Class.forName(arrayOfString[0], true, 
/* 1115 */           ClassLoader.getSystemClassLoader());
/* 1116 */     } catch (ClassNotFoundException classNotFoundException) {
/* 1117 */       return null;
/*      */     } 
/*      */ 
/*      */     
/* 1121 */     ImageReaderSpi imageReaderSpi = (ImageReaderSpi)theRegistry.getServiceProviderByClass(clazz);
/* 1122 */     if (imageReaderSpi == null) {
/* 1123 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1127 */       return imageReaderSpi.createReaderInstance();
/* 1128 */     } catch (IOException iOException) {
/*      */       
/* 1130 */       theRegistry.deregisterServiceProvider(imageReaderSpi, ImageReaderSpi.class);
/*      */       
/* 1132 */       return null;
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
/*      */   public static Iterator<ImageWriter> getImageWriters(ImageTypeSpecifier paramImageTypeSpecifier, String paramString) {
/*      */     Iterator<ImageWriterSpi> iterator;
/* 1156 */     if (paramImageTypeSpecifier == null) {
/* 1157 */       throw new IllegalArgumentException("type == null!");
/*      */     }
/* 1159 */     if (paramString == null) {
/* 1160 */       throw new IllegalArgumentException("formatName == null!");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1166 */       iterator = theRegistry.getServiceProviders(ImageWriterSpi.class, new CanEncodeImageAndFormatFilter(paramImageTypeSpecifier, paramString), true);
/*      */ 
/*      */     
/*      */     }
/* 1170 */     catch (IllegalArgumentException illegalArgumentException) {
/* 1171 */       return Collections.emptyIterator();
/*      */     } 
/*      */     
/* 1174 */     return new ImageWriterIterator(iterator);
/*      */   }
/*      */ 
/*      */   
/*      */   static class ImageTranscoderIterator
/*      */     implements Iterator<ImageTranscoder>
/*      */   {
/*      */     public Iterator iter;
/*      */     
/*      */     public ImageTranscoderIterator(Iterator param1Iterator) {
/* 1184 */       this.iter = param1Iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1188 */       return this.iter.hasNext();
/*      */     }
/*      */     
/*      */     public ImageTranscoder next() {
/* 1192 */       ImageTranscoderSpi imageTranscoderSpi = null;
/* 1193 */       imageTranscoderSpi = this.iter.next();
/* 1194 */       return imageTranscoderSpi.createTranscoderInstance();
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1198 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class TranscoderFilter
/*      */     implements ServiceRegistry.Filter
/*      */   {
/*      */     String readerSpiName;
/*      */     String writerSpiName;
/*      */     
/*      */     public TranscoderFilter(ImageReaderSpi param1ImageReaderSpi, ImageWriterSpi param1ImageWriterSpi) {
/* 1210 */       this.readerSpiName = param1ImageReaderSpi.getClass().getName();
/* 1211 */       this.writerSpiName = param1ImageWriterSpi.getClass().getName();
/*      */     }
/*      */     
/*      */     public boolean filter(Object param1Object) {
/* 1215 */       ImageTranscoderSpi imageTranscoderSpi = (ImageTranscoderSpi)param1Object;
/* 1216 */       String str1 = imageTranscoderSpi.getReaderServiceProviderName();
/* 1217 */       String str2 = imageTranscoderSpi.getWriterServiceProviderName();
/* 1218 */       return (str1.equals(this.readerSpiName) && str2
/* 1219 */         .equals(this.writerSpiName));
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
/*      */   public static Iterator<ImageTranscoder> getImageTranscoders(ImageReader paramImageReader, ImageWriter paramImageWriter) {
/*      */     Iterator<ImageTranscoderSpi> iterator;
/* 1241 */     if (paramImageReader == null) {
/* 1242 */       throw new IllegalArgumentException("reader == null!");
/*      */     }
/* 1244 */     if (paramImageWriter == null) {
/* 1245 */       throw new IllegalArgumentException("writer == null!");
/*      */     }
/* 1247 */     ImageReaderSpi imageReaderSpi = paramImageReader.getOriginatingProvider();
/* 1248 */     ImageWriterSpi imageWriterSpi = paramImageWriter.getOriginatingProvider();
/* 1249 */     TranscoderFilter transcoderFilter = new TranscoderFilter(imageReaderSpi, imageWriterSpi);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1255 */       iterator = theRegistry.getServiceProviders(ImageTranscoderSpi.class, transcoderFilter, true);
/*      */     }
/* 1257 */     catch (IllegalArgumentException illegalArgumentException) {
/* 1258 */       return Collections.emptyIterator();
/*      */     } 
/* 1260 */     return new ImageTranscoderIterator(iterator);
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
/*      */   public static BufferedImage read(File paramFile) throws IOException {
/* 1298 */     if (paramFile == null) {
/* 1299 */       throw new IllegalArgumentException("input == null!");
/*      */     }
/* 1301 */     if (!paramFile.canRead()) {
/* 1302 */       throw new IIOException("Can't read input file!");
/*      */     }
/*      */     
/* 1305 */     ImageInputStream imageInputStream = createImageInputStream(paramFile);
/* 1306 */     if (imageInputStream == null) {
/* 1307 */       throw new IIOException("Can't create an ImageInputStream!");
/*      */     }
/* 1309 */     BufferedImage bufferedImage = read(imageInputStream);
/* 1310 */     if (bufferedImage == null) {
/* 1311 */       imageInputStream.close();
/*      */     }
/* 1313 */     return bufferedImage;
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
/*      */   public static BufferedImage read(InputStream paramInputStream) throws IOException {
/* 1349 */     if (paramInputStream == null) {
/* 1350 */       throw new IllegalArgumentException("input == null!");
/*      */     }
/*      */     
/* 1353 */     ImageInputStream imageInputStream = createImageInputStream(paramInputStream);
/* 1354 */     if (imageInputStream == null) {
/* 1355 */       throw new IIOException("Can't create an ImageInputStream!");
/*      */     }
/* 1357 */     BufferedImage bufferedImage = read(imageInputStream);
/* 1358 */     if (bufferedImage == null) {
/* 1359 */       imageInputStream.close();
/*      */     }
/* 1361 */     return bufferedImage;
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
/*      */   public static BufferedImage read(URL paramURL) throws IOException {
/*      */     BufferedImage bufferedImage;
/* 1393 */     if (paramURL == null) {
/* 1394 */       throw new IllegalArgumentException("input == null!");
/*      */     }
/*      */     
/* 1397 */     InputStream inputStream = null;
/*      */     try {
/* 1399 */       inputStream = paramURL.openStream();
/* 1400 */     } catch (IOException iOException) {
/* 1401 */       throw new IIOException("Can't get input stream from URL!", iOException);
/*      */     } 
/* 1403 */     ImageInputStream imageInputStream = createImageInputStream(inputStream);
/* 1404 */     if (imageInputStream == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1409 */       inputStream.close();
/* 1410 */       throw new IIOException("Can't create an ImageInputStream!");
/*      */     } 
/*      */     
/*      */     try {
/* 1414 */       bufferedImage = read(imageInputStream);
/* 1415 */       if (bufferedImage == null) {
/* 1416 */         imageInputStream.close();
/*      */       }
/*      */     } finally {
/* 1419 */       inputStream.close();
/*      */     } 
/* 1421 */     return bufferedImage;
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
/*      */   public static BufferedImage read(ImageInputStream paramImageInputStream) throws IOException {
/*      */     BufferedImage bufferedImage;
/* 1448 */     if (paramImageInputStream == null) {
/* 1449 */       throw new IllegalArgumentException("stream == null!");
/*      */     }
/*      */     
/* 1452 */     Iterator<ImageReader> iterator = getImageReaders(paramImageInputStream);
/* 1453 */     if (!iterator.hasNext()) {
/* 1454 */       return null;
/*      */     }
/*      */     
/* 1457 */     ImageReader imageReader = iterator.next();
/* 1458 */     ImageReadParam imageReadParam = imageReader.getDefaultReadParam();
/* 1459 */     imageReader.setInput(paramImageInputStream, true, true);
/*      */     
/*      */     try {
/* 1462 */       bufferedImage = imageReader.read(0, imageReadParam);
/*      */     } finally {
/* 1464 */       imageReader.dispose();
/* 1465 */       paramImageInputStream.close();
/*      */     } 
/* 1467 */     return bufferedImage;
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
/*      */   public static boolean write(RenderedImage paramRenderedImage, String paramString, ImageOutputStream paramImageOutputStream) throws IOException {
/* 1496 */     if (paramRenderedImage == null) {
/* 1497 */       throw new IllegalArgumentException("im == null!");
/*      */     }
/* 1499 */     if (paramString == null) {
/* 1500 */       throw new IllegalArgumentException("formatName == null!");
/*      */     }
/* 1502 */     if (paramImageOutputStream == null) {
/* 1503 */       throw new IllegalArgumentException("output == null!");
/*      */     }
/*      */     
/* 1506 */     return doWrite(paramRenderedImage, getWriter(paramRenderedImage, paramString), paramImageOutputStream);
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
/*      */   public static boolean write(RenderedImage paramRenderedImage, String paramString, File paramFile) throws IOException {
/* 1530 */     if (paramFile == null) {
/* 1531 */       throw new IllegalArgumentException("output == null!");
/*      */     }
/*      */     
/* 1534 */     ImageWriter imageWriter = getWriter(paramRenderedImage, paramString);
/* 1535 */     if (imageWriter == null)
/*      */     {
/*      */ 
/*      */       
/* 1539 */       return false;
/*      */     }
/*      */     
/* 1542 */     paramFile.delete();
/* 1543 */     ImageOutputStream imageOutputStream = createImageOutputStream(paramFile);
/* 1544 */     if (imageOutputStream == null) {
/* 1545 */       throw new IIOException("Can't create an ImageOutputStream!");
/*      */     }
/*      */     try {
/* 1548 */       return doWrite(paramRenderedImage, imageWriter, imageOutputStream);
/*      */     } finally {
/* 1550 */       imageOutputStream.close();
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
/*      */   public static boolean write(RenderedImage paramRenderedImage, String paramString, OutputStream paramOutputStream) throws IOException {
/* 1580 */     if (paramOutputStream == null) {
/* 1581 */       throw new IllegalArgumentException("output == null!");
/*      */     }
/* 1583 */     ImageOutputStream imageOutputStream = createImageOutputStream(paramOutputStream);
/* 1584 */     if (imageOutputStream == null) {
/* 1585 */       throw new IIOException("Can't create an ImageOutputStream!");
/*      */     }
/*      */     try {
/* 1588 */       return doWrite(paramRenderedImage, getWriter(paramRenderedImage, paramString), imageOutputStream);
/*      */     } finally {
/* 1590 */       imageOutputStream.close();
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
/*      */   private static ImageWriter getWriter(RenderedImage paramRenderedImage, String paramString) {
/* 1602 */     ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromRenderedImage(paramRenderedImage);
/* 1603 */     Iterator<ImageWriter> iterator = getImageWriters(imageTypeSpecifier, paramString);
/*      */     
/* 1605 */     if (iterator.hasNext()) {
/* 1606 */       return iterator.next();
/*      */     }
/* 1608 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean doWrite(RenderedImage paramRenderedImage, ImageWriter paramImageWriter, ImageOutputStream paramImageOutputStream) throws IOException {
/* 1617 */     if (paramImageWriter == null) {
/* 1618 */       return false;
/*      */     }
/* 1620 */     paramImageWriter.setOutput(paramImageOutputStream);
/*      */     try {
/* 1622 */       paramImageWriter.write(paramRenderedImage);
/*      */     } finally {
/* 1624 */       paramImageWriter.dispose();
/* 1625 */       paramImageOutputStream.flush();
/*      */     } 
/* 1627 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/ImageIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */