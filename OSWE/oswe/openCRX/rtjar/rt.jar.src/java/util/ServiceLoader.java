/*     */ package java.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServiceLoader<S>
/*     */   implements Iterable<S>
/*     */ {
/*     */   private static final String PREFIX = "META-INF/services/";
/*     */   private final Class<S> service;
/*     */   private final ClassLoader loader;
/*     */   private final AccessControlContext acc;
/* 201 */   private LinkedHashMap<String, S> providers = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LazyIterator lookupIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reload() {
/* 218 */     this.providers.clear();
/* 219 */     this.lookupIterator = new LazyIterator(this.service, this.loader);
/*     */   }
/*     */   
/*     */   private ServiceLoader(Class<S> paramClass, ClassLoader paramClassLoader) {
/* 223 */     this.service = Objects.<Class<S>>requireNonNull(paramClass, "Service interface cannot be null");
/* 224 */     this.loader = (paramClassLoader == null) ? ClassLoader.getSystemClassLoader() : paramClassLoader;
/* 225 */     this.acc = (System.getSecurityManager() != null) ? AccessController.getContext() : null;
/* 226 */     reload();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fail(Class<?> paramClass, String paramString, Throwable paramThrowable) throws ServiceConfigurationError {
/* 232 */     throw new ServiceConfigurationError(paramClass.getName() + ": " + paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fail(Class<?> paramClass, String paramString) throws ServiceConfigurationError {
/* 239 */     throw new ServiceConfigurationError(paramClass.getName() + ": " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fail(Class<?> paramClass, URL paramURL, int paramInt, String paramString) throws ServiceConfigurationError {
/* 245 */     fail(paramClass, paramURL + ":" + paramInt + ": " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parseLine(Class<?> paramClass, URL paramURL, BufferedReader paramBufferedReader, int paramInt, List<String> paramList) throws IOException, ServiceConfigurationError {
/* 255 */     String str = paramBufferedReader.readLine();
/* 256 */     if (str == null) {
/* 257 */       return -1;
/*     */     }
/* 259 */     int i = str.indexOf('#');
/* 260 */     if (i >= 0) str = str.substring(0, i); 
/* 261 */     str = str.trim();
/* 262 */     int j = str.length();
/* 263 */     if (j != 0) {
/* 264 */       if (str.indexOf(' ') >= 0 || str.indexOf('\t') >= 0)
/* 265 */         fail(paramClass, paramURL, paramInt, "Illegal configuration-file syntax"); 
/* 266 */       int k = str.codePointAt(0);
/* 267 */       if (!Character.isJavaIdentifierStart(k))
/* 268 */         fail(paramClass, paramURL, paramInt, "Illegal provider-class name: " + str);  int m;
/* 269 */       for (m = Character.charCount(k); m < j; m += Character.charCount(k)) {
/* 270 */         k = str.codePointAt(m);
/* 271 */         if (!Character.isJavaIdentifierPart(k) && k != 46)
/* 272 */           fail(paramClass, paramURL, paramInt, "Illegal provider-class name: " + str); 
/*     */       } 
/* 274 */       if (!this.providers.containsKey(str) && !paramList.contains(str))
/* 275 */         paramList.add(str); 
/*     */     } 
/* 277 */     return paramInt + 1;
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
/*     */   private Iterator<String> parse(Class<?> paramClass, URL paramURL) throws ServiceConfigurationError {
/* 300 */     InputStream inputStream = null;
/* 301 */     BufferedReader bufferedReader = null;
/* 302 */     ArrayList<String> arrayList = new ArrayList();
/*     */     try {
/* 304 */       inputStream = paramURL.openStream();
/* 305 */       bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
/* 306 */       int i = 1;
/* 307 */       while ((i = parseLine(paramClass, paramURL, bufferedReader, i, arrayList)) >= 0);
/* 308 */     } catch (IOException iOException) {
/* 309 */       fail(paramClass, "Error reading configuration file", iOException);
/*     */     } finally {
/*     */       try {
/* 312 */         if (bufferedReader != null) bufferedReader.close(); 
/* 313 */         if (inputStream != null) inputStream.close(); 
/* 314 */       } catch (IOException iOException) {
/* 315 */         fail(paramClass, "Error closing configuration file", iOException);
/*     */       } 
/*     */     } 
/* 318 */     return arrayList.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private class LazyIterator
/*     */     implements Iterator<S>
/*     */   {
/*     */     Class<S> service;
/*     */     
/*     */     ClassLoader loader;
/*     */     
/* 329 */     Enumeration<URL> configs = null;
/* 330 */     Iterator<String> pending = null;
/* 331 */     String nextName = null;
/*     */     
/*     */     private LazyIterator(Class<S> param1Class, ClassLoader param1ClassLoader) {
/* 334 */       this.service = param1Class;
/* 335 */       this.loader = param1ClassLoader;
/*     */     }
/*     */     
/*     */     private boolean hasNextService() {
/* 339 */       if (this.nextName != null) {
/* 340 */         return true;
/*     */       }
/* 342 */       if (this.configs == null) {
/*     */         try {
/* 344 */           String str = "META-INF/services/" + this.service.getName();
/* 345 */           if (this.loader == null)
/* 346 */           { this.configs = ClassLoader.getSystemResources(str); }
/*     */           else
/* 348 */           { this.configs = this.loader.getResources(str); } 
/* 349 */         } catch (IOException iOException) {
/* 350 */           ServiceLoader.fail(this.service, "Error locating configuration files", iOException);
/*     */         } 
/*     */       }
/* 353 */       while (this.pending == null || !this.pending.hasNext()) {
/* 354 */         if (!this.configs.hasMoreElements()) {
/* 355 */           return false;
/*     */         }
/* 357 */         this.pending = ServiceLoader.this.parse(this.service, this.configs.nextElement());
/*     */       } 
/* 359 */       this.nextName = this.pending.next();
/* 360 */       return true;
/*     */     }
/*     */     
/*     */     private S nextService() {
/* 364 */       if (!hasNextService())
/* 365 */         throw new NoSuchElementException(); 
/* 366 */       String str = this.nextName;
/* 367 */       this.nextName = null;
/* 368 */       Class<?> clazz = null;
/*     */       try {
/* 370 */         clazz = Class.forName(str, false, this.loader);
/* 371 */       } catch (ClassNotFoundException classNotFoundException) {
/* 372 */         ServiceLoader.fail(this.service, "Provider " + str + " not found");
/*     */       } 
/*     */       
/* 375 */       if (!this.service.isAssignableFrom(clazz)) {
/* 376 */         ServiceLoader.fail(this.service, "Provider " + str + " not a subtype");
/*     */       }
/*     */       
/*     */       try {
/* 380 */         S s = this.service.cast(clazz.newInstance());
/* 381 */         ServiceLoader.this.providers.put(str, s);
/* 382 */         return s;
/* 383 */       } catch (Throwable throwable) {
/* 384 */         ServiceLoader.fail(this.service, "Provider " + str + " could not be instantiated", throwable);
/*     */ 
/*     */ 
/*     */         
/* 388 */         throw new Error();
/*     */       } 
/*     */     }
/*     */     public boolean hasNext() {
/* 392 */       if (ServiceLoader.this.acc == null) {
/* 393 */         return hasNextService();
/*     */       }
/* 395 */       PrivilegedAction<Boolean> privilegedAction = new PrivilegedAction<Boolean>() {
/* 396 */           public Boolean run() { return Boolean.valueOf(ServiceLoader.LazyIterator.this.hasNextService()); }
/*     */         };
/* 398 */       return ((Boolean)AccessController.<Boolean>doPrivileged(privilegedAction, ServiceLoader.this.acc)).booleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public S next() {
/* 403 */       if (ServiceLoader.this.acc == null) {
/* 404 */         return nextService();
/*     */       }
/* 406 */       PrivilegedAction<S> privilegedAction = new PrivilegedAction<S>() {
/* 407 */           public S run() { return ServiceLoader.LazyIterator.this.nextService(); }
/*     */         };
/* 409 */       return AccessController.doPrivileged(privilegedAction, ServiceLoader.this.acc);
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 414 */       throw new UnsupportedOperationException();
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
/*     */   public Iterator<S> iterator() {
/* 466 */     return new Iterator<S>()
/*     */       {
/* 468 */         Iterator<Map.Entry<String, S>> knownProviders = ServiceLoader.this
/* 469 */           .providers.entrySet().iterator();
/*     */         
/*     */         public boolean hasNext() {
/* 472 */           if (this.knownProviders.hasNext())
/* 473 */             return true; 
/* 474 */           return ServiceLoader.this.lookupIterator.hasNext();
/*     */         }
/*     */         
/*     */         public S next() {
/* 478 */           if (this.knownProviders.hasNext())
/* 479 */             return (S)((Map.Entry)this.knownProviders.next()).getValue(); 
/* 480 */           return ServiceLoader.this.lookupIterator.next();
/*     */         }
/*     */         
/*     */         public void remove() {
/* 484 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
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
/*     */   public static <S> ServiceLoader<S> load(Class<S> paramClass, ClassLoader paramClassLoader) {
/* 510 */     return new ServiceLoader<>(paramClass, paramClassLoader);
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
/*     */   public static <S> ServiceLoader<S> load(Class<S> paramClass) {
/* 537 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 538 */     return load(paramClass, classLoader);
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
/*     */   public static <S> ServiceLoader<S> loadInstalled(Class<S> paramClass) {
/* 568 */     ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
/* 569 */     ClassLoader classLoader2 = null;
/* 570 */     while (classLoader1 != null) {
/* 571 */       classLoader2 = classLoader1;
/* 572 */       classLoader1 = classLoader1.getParent();
/*     */     } 
/* 574 */     return load(paramClass, classLoader2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 583 */     return "java.util.ServiceLoader[" + this.service.getName() + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/ServiceLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */