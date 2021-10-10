/*     */ package javax.management.loading;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class DefaultLoaderRepository
/*     */ {
/*     */   public static Class<?> loadClass(String paramString) throws ClassNotFoundException {
/*  74 */     JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, DefaultLoaderRepository.class
/*  75 */         .getName(), "loadClass", paramString);
/*     */     
/*  77 */     return load(null, paramString);
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
/*     */   public static Class<?> loadClassWithout(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
/*  99 */     JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, DefaultLoaderRepository.class
/* 100 */         .getName(), "loadClassWithout", paramString);
/*     */     
/* 102 */     return load(paramClassLoader, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<?> load(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
/* 107 */     ArrayList<MBeanServer> arrayList = MBeanServerFactory.findMBeanServer(null);
/*     */     
/* 109 */     for (MBeanServer mBeanServer : arrayList) {
/* 110 */       ClassLoaderRepository classLoaderRepository = mBeanServer.getClassLoaderRepository();
/*     */       try {
/* 112 */         return classLoaderRepository.loadClassWithout(paramClassLoader, paramString);
/* 113 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */     } 
/*     */ 
/*     */     
/* 117 */     throw new ClassNotFoundException(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/loading/DefaultLoaderRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */