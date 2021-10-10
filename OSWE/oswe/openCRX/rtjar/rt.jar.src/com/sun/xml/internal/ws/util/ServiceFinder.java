/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.ComponentEx;
/*     */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Array;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServiceFinder<T>
/*     */   implements Iterable<T>
/*     */ {
/*     */   private static final String prefix = "META-INF/services/";
/* 142 */   private static WeakHashMap<ClassLoader, ConcurrentHashMap<String, ServiceName[]>> serviceNameCache = new WeakHashMap<>();
/*     */   private final Class<T> serviceClass;
/*     */   @Nullable
/*     */   private final ClassLoader classLoader;
/*     */   @Nullable
/*     */   private final ComponentEx component;
/*     */   
/*     */   private static class ServiceName {
/*     */     final String className;
/*     */     
/*     */     public ServiceName(String className, URL config) {
/* 153 */       this.className = className;
/* 154 */       this.config = config;
/*     */     }
/*     */     final URL config; }
/*     */   
/*     */   public static <T> ServiceFinder<T> find(@NotNull Class<T> service, @Nullable ClassLoader loader, Component component) {
/* 159 */     return new ServiceFinder<>(service, loader, component);
/*     */   }
/*     */   
/*     */   public static <T> ServiceFinder<T> find(@NotNull Class<T> service, Component component) {
/* 163 */     return find(service, Thread.currentThread().getContextClassLoader(), component);
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
/*     */   public static <T> ServiceFinder<T> find(@NotNull Class<T> service, @Nullable ClassLoader loader) {
/* 192 */     return find(service, loader, (Component)ContainerResolver.getInstance().getContainer());
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
/*     */   public static <T> ServiceFinder<T> find(Class<T> service) {
/* 212 */     return find(service, Thread.currentThread().getContextClassLoader());
/*     */   }
/*     */   
/*     */   private ServiceFinder(Class<T> service, ClassLoader loader, Component component) {
/* 216 */     this.serviceClass = service;
/* 217 */     this.classLoader = loader;
/* 218 */     this.component = getComponentEx(component);
/*     */   }
/*     */   
/*     */   private static ServiceName[] serviceClassNames(Class serviceClass, ClassLoader classLoader) {
/* 222 */     ArrayList<ServiceName> l = new ArrayList<>();
/* 223 */     for (Iterator<ServiceName> it = new ServiceNameIterator(serviceClass, classLoader); it.hasNext(); l.add(it.next()));
/* 224 */     return l.<ServiceName>toArray(new ServiceName[l.size()]);
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
/*     */   public Iterator<T> iterator() {
/* 238 */     Iterator<T> it = new LazyIterator<>(this.serviceClass, this.classLoader);
/* 239 */     return (this.component != null) ? new CompositeIterator<>((Iterator<T>[])new Iterator[] { this.component
/*     */           
/* 241 */           .getIterableSPI(this.serviceClass).iterator(), it }) : it;
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
/*     */   public T[] toArray() {
/* 254 */     List<T> result = new ArrayList<>();
/* 255 */     for (T t : this) {
/* 256 */       result.add(t);
/*     */     }
/* 258 */     return result.toArray((T[])Array.newInstance(this.serviceClass, result.size()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fail(Class service, String msg, Throwable cause) throws ServiceConfigurationError {
/* 264 */     ServiceConfigurationError sce = new ServiceConfigurationError(service.getName() + ": " + msg);
/* 265 */     sce.initCause(cause);
/* 266 */     throw sce;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fail(Class service, String msg) throws ServiceConfigurationError {
/* 271 */     throw new ServiceConfigurationError(service.getName() + ": " + msg);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fail(Class service, URL u, int line, String msg) throws ServiceConfigurationError {
/* 276 */     fail(service, u + ":" + line + ": " + msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseLine(Class service, URL u, BufferedReader r, int lc, List<String> names, Set<String> returned) throws IOException, ServiceConfigurationError {
/* 287 */     String ln = r.readLine();
/* 288 */     if (ln == null) {
/* 289 */       return -1;
/*     */     }
/* 291 */     int ci = ln.indexOf('#');
/* 292 */     if (ci >= 0) ln = ln.substring(0, ci); 
/* 293 */     ln = ln.trim();
/* 294 */     int n = ln.length();
/* 295 */     if (n != 0) {
/* 296 */       if (ln.indexOf(' ') >= 0 || ln.indexOf('\t') >= 0)
/* 297 */         fail(service, u, lc, "Illegal configuration-file syntax"); 
/* 298 */       int cp = ln.codePointAt(0);
/* 299 */       if (!Character.isJavaIdentifierStart(cp))
/* 300 */         fail(service, u, lc, "Illegal provider-class name: " + ln);  int i;
/* 301 */       for (i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
/* 302 */         cp = ln.codePointAt(i);
/* 303 */         if (!Character.isJavaIdentifierPart(cp) && cp != 46)
/* 304 */           fail(service, u, lc, "Illegal provider-class name: " + ln); 
/*     */       } 
/* 306 */       if (!returned.contains(ln)) {
/* 307 */         names.add(ln);
/* 308 */         returned.add(ln);
/*     */       } 
/*     */     } 
/* 311 */     return lc + 1;
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
/*     */   private static Iterator<String> parse(Class service, URL u, Set<String> returned) throws ServiceConfigurationError {
/* 332 */     InputStream in = null;
/* 333 */     BufferedReader r = null;
/* 334 */     ArrayList<String> names = new ArrayList<>();
/*     */     try {
/* 336 */       in = u.openStream();
/* 337 */       r = new BufferedReader(new InputStreamReader(in, "utf-8"));
/* 338 */       int lc = 1;
/* 339 */       while ((lc = parseLine(service, u, r, lc, names, returned)) >= 0);
/* 340 */     } catch (IOException x) {
/* 341 */       fail(service, ": " + x);
/*     */     } finally {
/*     */       try {
/* 344 */         if (r != null) r.close(); 
/* 345 */         if (in != null) in.close(); 
/* 346 */       } catch (IOException y) {
/* 347 */         fail(service, ": " + y);
/*     */       } 
/*     */     } 
/* 350 */     return names.iterator();
/*     */   }
/*     */   
/*     */   private static ComponentEx getComponentEx(Component component) {
/* 354 */     if (component instanceof ComponentEx) {
/* 355 */       return (ComponentEx)component;
/*     */     }
/* 357 */     return (component != null) ? new ComponentExWrapper(component) : null;
/*     */   }
/*     */   
/*     */   private static class ComponentExWrapper implements ComponentEx {
/*     */     private final Component component;
/*     */     
/*     */     public ComponentExWrapper(Component component) {
/* 364 */       this.component = component;
/*     */     }
/*     */     
/*     */     public <S> S getSPI(Class<S> spiType) {
/* 368 */       return (S)this.component.getSPI(spiType);
/*     */     }
/*     */     
/*     */     public <S> Iterable<S> getIterableSPI(Class<S> spiType) {
/* 372 */       S item = getSPI(spiType);
/* 373 */       if (item != null) {
/* 374 */         Collection<S> c = Collections.singletonList(item);
/* 375 */         return c;
/*     */       } 
/* 377 */       return Collections.emptySet();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CompositeIterator<T> implements Iterator<T> {
/*     */     private final Iterator<Iterator<T>> it;
/* 383 */     private Iterator<T> current = null;
/*     */     
/*     */     public CompositeIterator(Iterator<T>... iterators) {
/* 386 */       this.it = Arrays.<Iterator<T>>asList(iterators).iterator();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 390 */       if (this.current != null && this.current.hasNext()) {
/* 391 */         return true;
/*     */       }
/* 393 */       while (this.it.hasNext()) {
/* 394 */         this.current = this.it.next();
/* 395 */         if (this.current.hasNext()) {
/* 396 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 400 */       return false;
/*     */     }
/*     */     
/*     */     public T next() {
/* 404 */       if (!hasNext()) {
/* 405 */         throw new NoSuchElementException();
/*     */       }
/* 407 */       return this.current.next();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 411 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ServiceNameIterator
/*     */     implements Iterator<ServiceName>
/*     */   {
/*     */     Class service;
/*     */     @Nullable
/*     */     ClassLoader loader;
/* 421 */     Enumeration<URL> configs = null;
/* 422 */     Iterator<String> pending = null;
/* 423 */     Set<String> returned = new TreeSet<>();
/* 424 */     String nextName = null;
/* 425 */     URL currentConfig = null;
/*     */     
/*     */     private ServiceNameIterator(Class service, ClassLoader loader) {
/* 428 */       this.service = service;
/* 429 */       this.loader = loader;
/*     */     }
/*     */     
/*     */     public boolean hasNext() throws ServiceConfigurationError {
/* 433 */       if (this.nextName != null) {
/* 434 */         return true;
/*     */       }
/* 436 */       if (this.configs == null) {
/*     */         try {
/* 438 */           String fullName = "META-INF/services/" + this.service.getName();
/* 439 */           if (this.loader == null)
/* 440 */           { this.configs = ClassLoader.getSystemResources(fullName); }
/*     */           else
/* 442 */           { this.configs = this.loader.getResources(fullName); } 
/* 443 */         } catch (IOException x) {
/* 444 */           ServiceFinder.fail(this.service, ": " + x);
/*     */         } 
/*     */       }
/* 447 */       while (this.pending == null || !this.pending.hasNext()) {
/* 448 */         if (!this.configs.hasMoreElements()) {
/* 449 */           return false;
/*     */         }
/* 451 */         this.currentConfig = this.configs.nextElement();
/* 452 */         this.pending = ServiceFinder.parse(this.service, this.currentConfig, this.returned);
/*     */       } 
/* 454 */       this.nextName = this.pending.next();
/* 455 */       return true;
/*     */     }
/*     */     
/*     */     public ServiceFinder.ServiceName next() throws ServiceConfigurationError {
/* 459 */       if (!hasNext()) {
/* 460 */         throw new NoSuchElementException();
/*     */       }
/* 462 */       String cn = this.nextName;
/* 463 */       this.nextName = null;
/* 464 */       return new ServiceFinder.ServiceName(cn, this.currentConfig);
/*     */     }
/*     */     
/*     */     public void remove() {
/* 468 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   private static class LazyIterator<T> implements Iterator<T> {
/*     */     Class<T> service;
/*     */     @Nullable
/*     */     ClassLoader loader;
/*     */     ServiceFinder.ServiceName[] names;
/*     */     int index;
/*     */     
/*     */     private LazyIterator(Class<T> service, ClassLoader loader) {
/* 479 */       this.service = service;
/* 480 */       this.loader = loader;
/* 481 */       this.names = null;
/* 482 */       this.index = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 487 */       if (this.names == null) {
/* 488 */         ConcurrentHashMap<String, ServiceFinder.ServiceName[]> nameMap = null;
/* 489 */         synchronized (ServiceFinder.serviceNameCache) { nameMap = (ConcurrentHashMap<String, ServiceFinder.ServiceName[]>)ServiceFinder.serviceNameCache.get(this.loader); }
/* 490 */          this.names = (nameMap != null) ? nameMap.get(this.service.getName()) : null;
/* 491 */         if (this.names == null) {
/* 492 */           this.names = ServiceFinder.serviceClassNames(this.service, this.loader);
/* 493 */           if (nameMap == null) nameMap = (ConcurrentHashMap)new ConcurrentHashMap<>(); 
/* 494 */           nameMap.put(this.service.getName(), this.names);
/* 495 */           synchronized (ServiceFinder.serviceNameCache) { ServiceFinder.serviceNameCache.put(this.loader, nameMap); }
/*     */         
/*     */         } 
/* 498 */       }  return (this.index < this.names.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public T next() {
/* 503 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 504 */       ServiceFinder.ServiceName sn = this.names[this.index++];
/* 505 */       String cn = sn.className;
/* 506 */       URL currentConfig = sn.config;
/*     */       try {
/* 508 */         return this.service.cast(Class.forName(cn, true, this.loader).newInstance());
/* 509 */       } catch (ClassNotFoundException x) {
/* 510 */         ServiceFinder.fail(this.service, "Provider " + cn + " is specified in " + currentConfig + " but not found");
/* 511 */       } catch (Exception x) {
/* 512 */         ServiceFinder.fail(this.service, "Provider " + cn + " is specified in " + currentConfig + "but could not be instantiated: " + x, x);
/*     */       } 
/* 514 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 519 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/ServiceFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */