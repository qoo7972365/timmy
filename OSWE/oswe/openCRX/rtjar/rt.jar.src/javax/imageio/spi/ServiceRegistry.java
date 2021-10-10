/*     */ package javax.imageio.spi;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceRegistry
/*     */ {
/* 105 */   private Map categoryMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceRegistry(Iterator<Class<?>> paramIterator) {
/* 119 */     if (paramIterator == null) {
/* 120 */       throw new IllegalArgumentException("categories == null!");
/*     */     }
/* 122 */     while (paramIterator.hasNext()) {
/* 123 */       Class<?> clazz = paramIterator.next();
/* 124 */       SubRegistry subRegistry = new SubRegistry(this, clazz);
/* 125 */       this.categoryMap.put(clazz, subRegistry);
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
/*     */   public static <T> Iterator<T> lookupProviders(Class<T> paramClass, ClassLoader paramClassLoader) {
/* 177 */     if (paramClass == null) {
/* 178 */       throw new IllegalArgumentException("providerClass == null!");
/*     */     }
/* 180 */     return ServiceLoader.<T>load(paramClass, paramClassLoader).iterator();
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
/*     */   public static <T> Iterator<T> lookupProviders(Class<T> paramClass) {
/* 208 */     if (paramClass == null) {
/* 209 */       throw new IllegalArgumentException("providerClass == null!");
/*     */     }
/* 211 */     return ServiceLoader.<T>load(paramClass).iterator();
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
/*     */   public Iterator<Class<?>> getCategories() {
/* 223 */     Set<Class<?>> set = this.categoryMap.keySet();
/* 224 */     return set.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Iterator getSubRegistries(Object paramObject) {
/* 232 */     ArrayList<SubRegistry> arrayList = new ArrayList();
/* 233 */     Iterator<Class<?>> iterator = this.categoryMap.keySet().iterator();
/* 234 */     while (iterator.hasNext()) {
/* 235 */       Class clazz = iterator.next();
/* 236 */       if (clazz.isAssignableFrom(paramObject.getClass())) {
/* 237 */         arrayList.add((SubRegistry)this.categoryMap.get(clazz));
/*     */       }
/*     */     } 
/* 240 */     return arrayList.iterator();
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
/*     */   public <T> boolean registerServiceProvider(T paramT, Class<T> paramClass) {
/* 271 */     if (paramT == null) {
/* 272 */       throw new IllegalArgumentException("provider == null!");
/*     */     }
/* 274 */     SubRegistry subRegistry = (SubRegistry)this.categoryMap.get(paramClass);
/* 275 */     if (subRegistry == null) {
/* 276 */       throw new IllegalArgumentException("category unknown!");
/*     */     }
/* 278 */     if (!paramClass.isAssignableFrom(paramT.getClass())) {
/* 279 */       throw new ClassCastException();
/*     */     }
/*     */     
/* 282 */     return subRegistry.registerServiceProvider(paramT);
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
/*     */   public void registerServiceProvider(Object paramObject) {
/* 304 */     if (paramObject == null) {
/* 305 */       throw new IllegalArgumentException("provider == null!");
/*     */     }
/* 307 */     Iterator<SubRegistry> iterator = getSubRegistries(paramObject);
/* 308 */     while (iterator.hasNext()) {
/* 309 */       SubRegistry subRegistry = iterator.next();
/* 310 */       subRegistry.registerServiceProvider(paramObject);
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
/*     */   public void registerServiceProviders(Iterator<?> paramIterator) {
/* 335 */     if (paramIterator == null) {
/* 336 */       throw new IllegalArgumentException("provider == null!");
/*     */     }
/* 338 */     while (paramIterator.hasNext()) {
/* 339 */       registerServiceProvider(paramIterator.next());
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
/*     */   public <T> boolean deregisterServiceProvider(T paramT, Class<T> paramClass) {
/* 374 */     if (paramT == null) {
/* 375 */       throw new IllegalArgumentException("provider == null!");
/*     */     }
/* 377 */     SubRegistry subRegistry = (SubRegistry)this.categoryMap.get(paramClass);
/* 378 */     if (subRegistry == null) {
/* 379 */       throw new IllegalArgumentException("category unknown!");
/*     */     }
/* 381 */     if (!paramClass.isAssignableFrom(paramT.getClass())) {
/* 382 */       throw new ClassCastException();
/*     */     }
/* 384 */     return subRegistry.deregisterServiceProvider(paramT);
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
/*     */   public void deregisterServiceProvider(Object paramObject) {
/* 397 */     if (paramObject == null) {
/* 398 */       throw new IllegalArgumentException("provider == null!");
/*     */     }
/* 400 */     Iterator<SubRegistry> iterator = getSubRegistries(paramObject);
/* 401 */     while (iterator.hasNext()) {
/* 402 */       SubRegistry subRegistry = iterator.next();
/* 403 */       subRegistry.deregisterServiceProvider(paramObject);
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
/*     */   public boolean contains(Object paramObject) {
/* 420 */     if (paramObject == null) {
/* 421 */       throw new IllegalArgumentException("provider == null!");
/*     */     }
/* 423 */     Iterator<SubRegistry> iterator = getSubRegistries(paramObject);
/* 424 */     while (iterator.hasNext()) {
/* 425 */       SubRegistry subRegistry = iterator.next();
/* 426 */       if (subRegistry.contains(paramObject)) {
/* 427 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 431 */     return false;
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
/*     */   public <T> Iterator<T> getServiceProviders(Class<T> paramClass, boolean paramBoolean) {
/* 457 */     SubRegistry subRegistry = (SubRegistry)this.categoryMap.get(paramClass);
/* 458 */     if (subRegistry == null) {
/* 459 */       throw new IllegalArgumentException("category unknown!");
/*     */     }
/* 461 */     return subRegistry.getServiceProviders(paramBoolean);
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
/*     */   public <T> Iterator<T> getServiceProviders(Class<T> paramClass, Filter paramFilter, boolean paramBoolean) {
/* 514 */     SubRegistry subRegistry = (SubRegistry)this.categoryMap.get(paramClass);
/* 515 */     if (subRegistry == null) {
/* 516 */       throw new IllegalArgumentException("category unknown!");
/*     */     }
/* 518 */     Iterator<T> iterator = getServiceProviders(paramClass, paramBoolean);
/* 519 */     return new FilterIterator<>(iterator, paramFilter);
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
/*     */   public <T> T getServiceProviderByClass(Class<T> paramClass) {
/* 541 */     if (paramClass == null) {
/* 542 */       throw new IllegalArgumentException("providerClass == null!");
/*     */     }
/* 544 */     Iterator<Class<?>> iterator = this.categoryMap.keySet().iterator();
/* 545 */     while (iterator.hasNext()) {
/* 546 */       Class clazz = iterator.next();
/* 547 */       if (clazz.isAssignableFrom(paramClass)) {
/* 548 */         SubRegistry subRegistry = (SubRegistry)this.categoryMap.get(clazz);
/* 549 */         T t = (T)subRegistry.getServiceProviderByClass((Class)paramClass);
/* 550 */         if (t != null) {
/* 551 */           return t;
/*     */         }
/*     */       } 
/*     */     } 
/* 555 */     return null;
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
/*     */   public <T> boolean setOrdering(Class<T> paramClass, T paramT1, T paramT2) {
/* 589 */     if (paramT1 == null || paramT2 == null) {
/* 590 */       throw new IllegalArgumentException("provider is null!");
/*     */     }
/* 592 */     if (paramT1 == paramT2) {
/* 593 */       throw new IllegalArgumentException("providers are the same!");
/*     */     }
/* 595 */     SubRegistry subRegistry = (SubRegistry)this.categoryMap.get(paramClass);
/* 596 */     if (subRegistry == null) {
/* 597 */       throw new IllegalArgumentException("category unknown!");
/*     */     }
/* 599 */     if (subRegistry.contains(paramT1) && subRegistry
/* 600 */       .contains(paramT2)) {
/* 601 */       return subRegistry.setOrdering(paramT1, paramT2);
/*     */     }
/* 603 */     return false;
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
/*     */   public <T> boolean unsetOrdering(Class<T> paramClass, T paramT1, T paramT2) {
/* 635 */     if (paramT1 == null || paramT2 == null) {
/* 636 */       throw new IllegalArgumentException("provider is null!");
/*     */     }
/* 638 */     if (paramT1 == paramT2) {
/* 639 */       throw new IllegalArgumentException("providers are the same!");
/*     */     }
/* 641 */     SubRegistry subRegistry = (SubRegistry)this.categoryMap.get(paramClass);
/* 642 */     if (subRegistry == null) {
/* 643 */       throw new IllegalArgumentException("category unknown!");
/*     */     }
/* 645 */     if (subRegistry.contains(paramT1) && subRegistry
/* 646 */       .contains(paramT2)) {
/* 647 */       return subRegistry.unsetOrdering(paramT1, paramT2);
/*     */     }
/* 649 */     return false;
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
/*     */   public void deregisterAll(Class<?> paramClass) {
/* 662 */     SubRegistry subRegistry = (SubRegistry)this.categoryMap.get(paramClass);
/* 663 */     if (subRegistry == null) {
/* 664 */       throw new IllegalArgumentException("category unknown!");
/*     */     }
/* 666 */     subRegistry.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deregisterAll() {
/* 674 */     Iterator<SubRegistry> iterator = this.categoryMap.values().iterator();
/* 675 */     while (iterator.hasNext()) {
/* 676 */       SubRegistry subRegistry = iterator.next();
/* 677 */       subRegistry.clear();
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
/*     */   public void finalize() throws Throwable {
/* 691 */     deregisterAll();
/* 692 */     super.finalize();
/*     */   }
/*     */   
/*     */   public static interface Filter {
/*     */     boolean filter(Object param1Object);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/spi/ServiceRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */