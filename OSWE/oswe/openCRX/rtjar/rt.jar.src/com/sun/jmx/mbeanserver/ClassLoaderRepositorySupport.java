/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.MBeanPermission;
/*     */ import javax.management.ObjectName;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ClassLoaderRepositorySupport
/*     */   implements ModifiableClassLoaderRepository
/*     */ {
/*     */   private static class LoaderEntry
/*     */   {
/*     */     ObjectName name;
/*     */     ClassLoader loader;
/*     */     
/*     */     LoaderEntry(ObjectName param1ObjectName, ClassLoader param1ClassLoader) {
/*  64 */       this.name = param1ObjectName;
/*  65 */       this.loader = param1ClassLoader;
/*     */     }
/*     */   }
/*     */   
/*  69 */   private static final LoaderEntry[] EMPTY_LOADER_ARRAY = new LoaderEntry[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private LoaderEntry[] loaders = EMPTY_LOADER_ARRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized boolean add(ObjectName paramObjectName, ClassLoader paramClassLoader) {
/*  90 */     ArrayList<LoaderEntry> arrayList = new ArrayList(Arrays.asList((Object[])this.loaders));
/*  91 */     arrayList.add(new LoaderEntry(paramObjectName, paramClassLoader));
/*  92 */     this.loaders = arrayList.<LoaderEntry>toArray(EMPTY_LOADER_ARRAY);
/*  93 */     return true;
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
/*     */   private synchronized boolean remove(ObjectName paramObjectName, ClassLoader paramClassLoader) {
/* 109 */     int i = this.loaders.length;
/* 110 */     for (byte b = 0; b < i; b++) {
/* 111 */       LoaderEntry loaderEntry = this.loaders[b];
/*     */ 
/*     */ 
/*     */       
/* 115 */       boolean bool = (paramObjectName == null) ? ((paramClassLoader == loaderEntry.loader) ? true : false) : paramObjectName.equals(loaderEntry.name);
/* 116 */       if (bool) {
/* 117 */         LoaderEntry[] arrayOfLoaderEntry = new LoaderEntry[i - 1];
/* 118 */         System.arraycopy(this.loaders, 0, arrayOfLoaderEntry, 0, b);
/* 119 */         System.arraycopy(this.loaders, b + 1, arrayOfLoaderEntry, b, i - 1 - b);
/*     */         
/* 121 */         this.loaders = arrayOfLoaderEntry;
/* 122 */         return true;
/*     */       } 
/*     */     } 
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private final Map<String, List<ClassLoader>> search = new Hashtable<>(10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   private final Map<ObjectName, ClassLoader> loadersWithNames = new Hashtable<>(10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class<?> loadClass(String paramString) throws ClassNotFoundException {
/* 144 */     return loadClass(this.loaders, paramString, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class<?> loadClassWithout(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
/* 151 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 152 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, ClassLoaderRepositorySupport.class
/* 153 */           .getName(), "loadClassWithout", paramString + " without " + paramClassLoader);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (paramClassLoader == null) {
/* 160 */       return loadClass(this.loaders, paramString, null, null);
/*     */     }
/*     */ 
/*     */     
/* 164 */     startValidSearch(paramClassLoader, paramString);
/*     */     try {
/* 166 */       return loadClass(this.loaders, paramString, paramClassLoader, null);
/*     */     } finally {
/* 168 */       stopValidSearch(paramClassLoader, paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class<?> loadClassBefore(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
/* 175 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 176 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, ClassLoaderRepositorySupport.class
/* 177 */           .getName(), "loadClassBefore", paramString + " before " + paramClassLoader);
/*     */     }
/*     */ 
/*     */     
/* 181 */     if (paramClassLoader == null) {
/* 182 */       return loadClass(this.loaders, paramString, null, null);
/*     */     }
/* 184 */     startValidSearch(paramClassLoader, paramString);
/*     */     try {
/* 186 */       return loadClass(this.loaders, paramString, null, paramClassLoader);
/*     */     } finally {
/* 188 */       stopValidSearch(paramClassLoader, paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> loadClass(LoaderEntry[] paramArrayOfLoaderEntry, String paramString, ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) throws ClassNotFoundException {
/* 198 */     ReflectUtil.checkPackageAccess(paramString);
/* 199 */     int i = paramArrayOfLoaderEntry.length;
/* 200 */     for (byte b = 0; b < i; b++) {
/*     */       try {
/* 202 */         ClassLoader classLoader = (paramArrayOfLoaderEntry[b]).loader;
/* 203 */         if (classLoader == null)
/* 204 */           return Class.forName(paramString, false, null); 
/* 205 */         if (classLoader != paramClassLoader1)
/*     */         
/* 207 */         { if (classLoader == paramClassLoader2)
/*     */             break; 
/* 209 */           if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 210 */             JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, ClassLoaderRepositorySupport.class
/* 211 */                 .getName(), "loadClass", "Trying loader = " + classLoader);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 226 */           return Class.forName(paramString, false, classLoader); } 
/* 227 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 232 */     throw new ClassNotFoundException(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void startValidSearch(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
/* 240 */     List<ClassLoader> list = this.search.get(paramString);
/* 241 */     if (list != null && list.contains(paramClassLoader)) {
/* 242 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 243 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, ClassLoaderRepositorySupport.class
/* 244 */             .getName(), "startValidSearch", "Already requested loader = " + paramClassLoader + " class = " + paramString);
/*     */       }
/*     */ 
/*     */       
/* 248 */       throw new ClassNotFoundException(paramString);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 253 */     if (list == null) {
/* 254 */       list = new ArrayList(1);
/* 255 */       this.search.put(paramString, list);
/*     */     } 
/* 257 */     list.add(paramClassLoader);
/* 258 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 259 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, ClassLoaderRepositorySupport.class
/* 260 */           .getName(), "startValidSearch", "loader = " + paramClassLoader + " class = " + paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void stopValidSearch(ClassLoader paramClassLoader, String paramString) {
/* 271 */     List list = this.search.get(paramString);
/* 272 */     if (list != null) {
/* 273 */       list.remove(paramClassLoader);
/* 274 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 275 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, ClassLoaderRepositorySupport.class
/* 276 */             .getName(), "stopValidSearch", "loader = " + paramClassLoader + " class = " + paramString);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addClassLoader(ClassLoader paramClassLoader) {
/* 284 */     add(null, paramClassLoader);
/*     */   }
/*     */   
/*     */   public final void removeClassLoader(ClassLoader paramClassLoader) {
/* 288 */     remove(null, paramClassLoader);
/*     */   }
/*     */ 
/*     */   
/*     */   public final synchronized void addClassLoader(ObjectName paramObjectName, ClassLoader paramClassLoader) {
/* 293 */     this.loadersWithNames.put(paramObjectName, paramClassLoader);
/* 294 */     if (!(paramClassLoader instanceof javax.management.loading.PrivateClassLoader))
/* 295 */       add(paramObjectName, paramClassLoader); 
/*     */   }
/*     */   
/*     */   public final synchronized void removeClassLoader(ObjectName paramObjectName) {
/* 299 */     ClassLoader classLoader = this.loadersWithNames.remove(paramObjectName);
/* 300 */     if (!(classLoader instanceof javax.management.loading.PrivateClassLoader))
/* 301 */       remove(paramObjectName, classLoader); 
/*     */   }
/*     */   
/*     */   public final ClassLoader getClassLoader(ObjectName paramObjectName) {
/* 305 */     ClassLoader classLoader = this.loadersWithNames.get(paramObjectName);
/* 306 */     if (classLoader != null) {
/* 307 */       SecurityManager securityManager = System.getSecurityManager();
/* 308 */       if (securityManager != null) {
/*     */         
/* 310 */         MBeanPermission mBeanPermission = new MBeanPermission(classLoader.getClass().getName(), null, paramObjectName, "getClassLoader");
/*     */ 
/*     */ 
/*     */         
/* 314 */         securityManager.checkPermission(mBeanPermission);
/*     */       } 
/*     */     } 
/* 317 */     return classLoader;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/ClassLoaderRepositorySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */