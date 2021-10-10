/*     */ package java.nio.channels.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.AsynchronousChannelGroup;
/*     */ import java.nio.channels.AsynchronousServerSocketChannel;
/*     */ import java.nio.channels.AsynchronousSocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import sun.nio.ch.DefaultAsynchronousChannelProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AsynchronousChannelProvider
/*     */ {
/*     */   private static Void checkPermission() {
/*  55 */     SecurityManager securityManager = System.getSecurityManager();
/*  56 */     if (securityManager != null)
/*  57 */       securityManager.checkPermission(new RuntimePermission("asynchronousChannelProvider")); 
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AsynchronousChannelProvider(Void paramVoid) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AsynchronousChannelProvider() {
/*  70 */     this(checkPermission());
/*     */   }
/*     */   
/*     */   private static class ProviderHolder
/*     */   {
/*  75 */     static final AsynchronousChannelProvider provider = load();
/*     */     
/*     */     private static AsynchronousChannelProvider load() {
/*  78 */       return 
/*  79 */         AccessController.<AsynchronousChannelProvider>doPrivileged(new PrivilegedAction<AsynchronousChannelProvider>()
/*     */           {
/*     */             public AsynchronousChannelProvider run() {
/*  82 */               AsynchronousChannelProvider asynchronousChannelProvider = AsynchronousChannelProvider.ProviderHolder.loadProviderFromProperty();
/*  83 */               if (asynchronousChannelProvider != null)
/*  84 */                 return asynchronousChannelProvider; 
/*  85 */               asynchronousChannelProvider = AsynchronousChannelProvider.ProviderHolder.loadProviderAsService();
/*  86 */               if (asynchronousChannelProvider != null)
/*  87 */                 return asynchronousChannelProvider; 
/*  88 */               return DefaultAsynchronousChannelProvider.create();
/*     */             }
/*     */           });
/*     */     }
/*     */     private static AsynchronousChannelProvider loadProviderFromProperty() {
/*  93 */       String str = System.getProperty("java.nio.channels.spi.AsynchronousChannelProvider");
/*  94 */       if (str == null)
/*  95 */         return null; 
/*     */       try {
/*  97 */         Class<?> clazz = Class.forName(str, true, 
/*  98 */             ClassLoader.getSystemClassLoader());
/*  99 */         return (AsynchronousChannelProvider)clazz.newInstance();
/* 100 */       } catch (ClassNotFoundException classNotFoundException) {
/* 101 */         throw new ServiceConfigurationError(null, classNotFoundException);
/* 102 */       } catch (IllegalAccessException illegalAccessException) {
/* 103 */         throw new ServiceConfigurationError(null, illegalAccessException);
/* 104 */       } catch (InstantiationException instantiationException) {
/* 105 */         throw new ServiceConfigurationError(null, instantiationException);
/* 106 */       } catch (SecurityException securityException) {
/* 107 */         throw new ServiceConfigurationError(null, securityException);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private static AsynchronousChannelProvider loadProviderAsService() {
/* 113 */       ServiceLoader<AsynchronousChannelProvider> serviceLoader = ServiceLoader.load(AsynchronousChannelProvider.class, 
/* 114 */           ClassLoader.getSystemClassLoader());
/* 115 */       Iterator<AsynchronousChannelProvider> iterator = serviceLoader.iterator();
/*     */       while (true) {
/*     */         try {
/* 118 */           return iterator.hasNext() ? iterator.next() : null;
/* 119 */         } catch (ServiceConfigurationError serviceConfigurationError) {
/* 120 */           if (serviceConfigurationError.getCause() instanceof SecurityException)
/*     */             continue;  break;
/*     */         } 
/*     */       } 
/* 124 */       throw serviceConfigurationError;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AsynchronousChannelProvider provider() {
/* 166 */     return ProviderHolder.provider;
/*     */   }
/*     */   
/*     */   public abstract AsynchronousChannelGroup openAsynchronousChannelGroup(int paramInt, ThreadFactory paramThreadFactory) throws IOException;
/*     */   
/*     */   public abstract AsynchronousChannelGroup openAsynchronousChannelGroup(ExecutorService paramExecutorService, int paramInt) throws IOException;
/*     */   
/*     */   public abstract AsynchronousServerSocketChannel openAsynchronousServerSocketChannel(AsynchronousChannelGroup paramAsynchronousChannelGroup) throws IOException;
/*     */   
/*     */   public abstract AsynchronousSocketChannel openAsynchronousSocketChannel(AsynchronousChannelGroup paramAsynchronousChannelGroup) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/spi/AsynchronousChannelProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */