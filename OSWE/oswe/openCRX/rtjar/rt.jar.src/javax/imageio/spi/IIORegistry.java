/*     */ package javax.imageio.spi;
/*     */ 
/*     */ import com.sun.imageio.plugins.bmp.BMPImageReaderSpi;
/*     */ import com.sun.imageio.plugins.bmp.BMPImageWriterSpi;
/*     */ import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
/*     */ import com.sun.imageio.plugins.gif.GIFImageWriterSpi;
/*     */ import com.sun.imageio.plugins.jpeg.JPEGImageReaderSpi;
/*     */ import com.sun.imageio.plugins.jpeg.JPEGImageWriterSpi;
/*     */ import com.sun.imageio.plugins.png.PNGImageReaderSpi;
/*     */ import com.sun.imageio.plugins.png.PNGImageWriterSpi;
/*     */ import com.sun.imageio.plugins.wbmp.WBMPImageReaderSpi;
/*     */ import com.sun.imageio.plugins.wbmp.WBMPImageWriterSpi;
/*     */ import com.sun.imageio.spi.FileImageInputStreamSpi;
/*     */ import com.sun.imageio.spi.FileImageOutputStreamSpi;
/*     */ import com.sun.imageio.spi.InputStreamImageInputStreamSpi;
/*     */ import com.sun.imageio.spi.OutputStreamImageOutputStreamSpi;
/*     */ import com.sun.imageio.spi.RAFImageInputStreamSpi;
/*     */ import com.sun.imageio.spi.RAFImageOutputStreamSpi;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ public final class IIORegistry
/*     */   extends ServiceRegistry
/*     */ {
/* 118 */   private static final Vector initialCategories = new Vector(5);
/*     */   
/*     */   static {
/* 121 */     initialCategories.add(ImageReaderSpi.class);
/* 122 */     initialCategories.add(ImageWriterSpi.class);
/* 123 */     initialCategories.add(ImageTranscoderSpi.class);
/* 124 */     initialCategories.add(ImageInputStreamSpi.class);
/* 125 */     initialCategories.add(ImageOutputStreamSpi.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IIORegistry() {
/* 136 */     super(initialCategories.iterator());
/* 137 */     registerStandardSpis();
/* 138 */     registerApplicationClasspathSpis();
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
/*     */   public static IIORegistry getDefaultInstance() {
/* 154 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/* 156 */     IIORegistry iIORegistry = (IIORegistry)appContext.get(IIORegistry.class);
/* 157 */     if (iIORegistry == null) {
/*     */       
/* 159 */       iIORegistry = new IIORegistry();
/* 160 */       appContext.put(IIORegistry.class, iIORegistry);
/*     */     } 
/* 162 */     return iIORegistry;
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerStandardSpis() {
/* 167 */     registerServiceProvider(new GIFImageReaderSpi());
/* 168 */     registerServiceProvider(new GIFImageWriterSpi());
/* 169 */     registerServiceProvider(new BMPImageReaderSpi());
/* 170 */     registerServiceProvider(new BMPImageWriterSpi());
/* 171 */     registerServiceProvider(new WBMPImageReaderSpi());
/* 172 */     registerServiceProvider(new WBMPImageWriterSpi());
/* 173 */     registerServiceProvider(new PNGImageReaderSpi());
/* 174 */     registerServiceProvider(new PNGImageWriterSpi());
/* 175 */     registerServiceProvider(new JPEGImageReaderSpi());
/* 176 */     registerServiceProvider(new JPEGImageWriterSpi());
/* 177 */     registerServiceProvider(new FileImageInputStreamSpi());
/* 178 */     registerServiceProvider(new FileImageOutputStreamSpi());
/* 179 */     registerServiceProvider(new InputStreamImageInputStreamSpi());
/* 180 */     registerServiceProvider(new OutputStreamImageOutputStreamSpi());
/* 181 */     registerServiceProvider(new RAFImageInputStreamSpi());
/* 182 */     registerServiceProvider(new RAFImageOutputStreamSpi());
/*     */     
/* 184 */     registerInstalledProviders();
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
/*     */   public void registerApplicationClasspathSpis() {
/* 199 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */     
/* 201 */     Iterator<Class<?>> iterator = getCategories();
/* 202 */     while (iterator.hasNext()) {
/* 203 */       Class<?> clazz = iterator.next();
/*     */       
/* 205 */       Iterator<IIOServiceProvider> iterator1 = ServiceLoader.load(clazz, classLoader).iterator();
/* 206 */       while (iterator1.hasNext()) {
/*     */ 
/*     */         
/*     */         try {
/* 210 */           IIOServiceProvider iIOServiceProvider = iterator1.next();
/* 211 */           registerServiceProvider(iIOServiceProvider);
/* 212 */         } catch (ServiceConfigurationError serviceConfigurationError) {
/* 213 */           if (System.getSecurityManager() != null) {
/*     */ 
/*     */             
/* 216 */             serviceConfigurationError.printStackTrace();
/*     */             
/*     */             continue;
/*     */           } 
/* 220 */           throw serviceConfigurationError;
/*     */         } 
/*     */       } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerInstalledProviders() {
/* 237 */     PrivilegedAction<?> privilegedAction = new PrivilegedAction()
/*     */       {
/*     */         public Object run() {
/* 240 */           Iterator<Class<?>> iterator = IIORegistry.this.getCategories();
/* 241 */           while (iterator.hasNext()) {
/* 242 */             Class<?> clazz = iterator.next();
/* 243 */             for (IIOServiceProvider iIOServiceProvider : ServiceLoader.loadInstalled(clazz)) {
/* 244 */               IIORegistry.this.registerServiceProvider(iIOServiceProvider);
/*     */             }
/*     */           } 
/* 247 */           return this;
/*     */         }
/*     */       };
/*     */     
/* 251 */     AccessController.doPrivileged(privilegedAction);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/spi/IIORegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */