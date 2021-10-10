/*     */ package java.nio.channels.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ProtocolFamily;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.Pipe;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import sun.nio.ch.DefaultSelectorProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SelectorProvider
/*     */ {
/*  71 */   private static final Object lock = new Object();
/*  72 */   private static SelectorProvider provider = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SelectorProvider() {
/*  82 */     SecurityManager securityManager = System.getSecurityManager();
/*  83 */     if (securityManager != null)
/*  84 */       securityManager.checkPermission(new RuntimePermission("selectorProvider")); 
/*     */   }
/*     */   
/*     */   private static boolean loadProviderFromProperty() {
/*  88 */     String str = System.getProperty("java.nio.channels.spi.SelectorProvider");
/*  89 */     if (str == null)
/*  90 */       return false; 
/*     */     try {
/*  92 */       Class<?> clazz = Class.forName(str, true, 
/*  93 */           ClassLoader.getSystemClassLoader());
/*  94 */       provider = (SelectorProvider)clazz.newInstance();
/*  95 */       return true;
/*  96 */     } catch (ClassNotFoundException classNotFoundException) {
/*  97 */       throw new ServiceConfigurationError(null, classNotFoundException);
/*  98 */     } catch (IllegalAccessException illegalAccessException) {
/*  99 */       throw new ServiceConfigurationError(null, illegalAccessException);
/* 100 */     } catch (InstantiationException instantiationException) {
/* 101 */       throw new ServiceConfigurationError(null, instantiationException);
/* 102 */     } catch (SecurityException securityException) {
/* 103 */       throw new ServiceConfigurationError(null, securityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean loadProviderAsService() {
/* 110 */     ServiceLoader<SelectorProvider> serviceLoader = ServiceLoader.load(SelectorProvider.class, 
/* 111 */         ClassLoader.getSystemClassLoader());
/* 112 */     Iterator<SelectorProvider> iterator = serviceLoader.iterator();
/*     */     while (true) {
/*     */       try {
/* 115 */         if (!iterator.hasNext())
/* 116 */           return false; 
/* 117 */         provider = iterator.next();
/* 118 */         return true;
/* 119 */       } catch (ServiceConfigurationError serviceConfigurationError) {
/* 120 */         if (serviceConfigurationError.getCause() instanceof SecurityException)
/*     */           continue;  break;
/*     */       } 
/*     */     } 
/* 124 */     throw serviceConfigurationError;
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
/*     */   public static SelectorProvider provider() {
/* 165 */     synchronized (lock) {
/* 166 */       if (provider != null)
/* 167 */         return provider; 
/* 168 */       return AccessController.<SelectorProvider>doPrivileged(new PrivilegedAction<SelectorProvider>()
/*     */           {
/*     */             public SelectorProvider run() {
/* 171 */               if (SelectorProvider.loadProviderFromProperty())
/* 172 */                 return SelectorProvider.provider; 
/* 173 */               if (SelectorProvider.loadProviderAsService())
/* 174 */                 return SelectorProvider.provider; 
/* 175 */               SelectorProvider.provider = DefaultSelectorProvider.create();
/* 176 */               return SelectorProvider.provider;
/*     */             }
/*     */           });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Channel inheritedChannel() throws IOException {
/* 314 */     return null;
/*     */   }
/*     */   
/*     */   public abstract DatagramChannel openDatagramChannel() throws IOException;
/*     */   
/*     */   public abstract DatagramChannel openDatagramChannel(ProtocolFamily paramProtocolFamily) throws IOException;
/*     */   
/*     */   public abstract Pipe openPipe() throws IOException;
/*     */   
/*     */   public abstract AbstractSelector openSelector() throws IOException;
/*     */   
/*     */   public abstract ServerSocketChannel openServerSocketChannel() throws IOException;
/*     */   
/*     */   public abstract SocketChannel openSocketChannel() throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/spi/SelectorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */