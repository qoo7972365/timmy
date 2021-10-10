/*      */ package java.security;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Function;
/*      */ import sun.security.util.Debug;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Provider
/*      */   extends Properties
/*      */ {
/*      */   static final long serialVersionUID = -4298000515446427739L;
/*   95 */   private static final Debug debug = Debug.getInstance("provider", "Provider");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String info;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double version;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  119 */   private transient Set<Map.Entry<Object, Object>> entrySet = null;
/*  120 */   private transient int entrySetCallCount = 0;
/*      */   
/*      */   private transient boolean initialized;
/*      */   
/*      */   private transient boolean legacyChanged;
/*      */   
/*      */   private transient boolean servicesChanged;
/*      */   private transient Map<String, String> legacyStrings;
/*      */   private transient Map<ServiceKey, Service> serviceMap;
/*      */   private transient Map<ServiceKey, Service> legacyMap;
/*      */   private transient Set<Service> serviceSet;
/*      */   private static final String ALIAS_PREFIX = "Alg.Alias.";
/*      */   private static final String ALIAS_PREFIX_LOWER = "alg.alias.";
/*      */   
/*      */   protected Provider(String paramString1, double paramDouble, String paramString2) {
/*  135 */     this.name = paramString1;
/*  136 */     this.version = paramDouble;
/*  137 */     this.info = paramString2;
/*  138 */     putId();
/*  139 */     this.initialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  148 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getVersion() {
/*  157 */     return this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getInfo() {
/*  167 */     return this.info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  178 */     return this.name + " version " + this.version;
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
/*      */   public synchronized void clear() {
/*  205 */     check("clearProviderProperties." + this.name);
/*  206 */     if (debug != null) {
/*  207 */       debug.println("Remove " + this.name + " provider properties");
/*      */     }
/*  209 */     implClear();
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
/*      */   public synchronized void load(InputStream paramInputStream) throws IOException {
/*  222 */     check("putProviderProperty." + this.name);
/*  223 */     if (debug != null) {
/*  224 */       debug.println("Load " + this.name + " provider properties");
/*      */     }
/*  226 */     Properties properties = new Properties();
/*  227 */     properties.load(paramInputStream);
/*  228 */     implPutAll(properties);
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
/*      */   public synchronized void putAll(Map<?, ?> paramMap) {
/*  240 */     check("putProviderProperty." + this.name);
/*  241 */     if (debug != null) {
/*  242 */       debug.println("Put all " + this.name + " provider properties");
/*      */     }
/*  244 */     implPutAll(paramMap);
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
/*      */   public synchronized Set<Map.Entry<Object, Object>> entrySet() {
/*  256 */     checkInitialized();
/*  257 */     if (this.entrySet == null) {
/*  258 */       if (this.entrySetCallCount++ == 0) {
/*  259 */         this.entrySet = Collections.<Object, Object>unmodifiableMap(this).entrySet();
/*      */       } else {
/*  261 */         return super.entrySet();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  269 */     if (this.entrySetCallCount != 2) {
/*  270 */       throw new RuntimeException("Internal error.");
/*      */     }
/*  272 */     return this.entrySet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Object> keySet() {
/*  283 */     checkInitialized();
/*  284 */     return Collections.unmodifiableSet(super.keySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<Object> values() {
/*  295 */     checkInitialized();
/*  296 */     return Collections.unmodifiableCollection(super.values());
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
/*      */   public synchronized Object put(Object paramObject1, Object paramObject2) {
/*  317 */     check("putProviderProperty." + this.name);
/*  318 */     if (debug != null) {
/*  319 */       debug.println("Set " + this.name + " provider property [" + paramObject1 + "/" + paramObject2 + "]");
/*      */     }
/*      */     
/*  322 */     return implPut(paramObject1, paramObject2);
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
/*      */   public synchronized Object putIfAbsent(Object paramObject1, Object paramObject2) {
/*  344 */     check("putProviderProperty." + this.name);
/*  345 */     if (debug != null) {
/*  346 */       debug.println("Set " + this.name + " provider property [" + paramObject1 + "/" + paramObject2 + "]");
/*      */     }
/*      */     
/*  349 */     return implPutIfAbsent(paramObject1, paramObject2);
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
/*      */   public synchronized Object remove(Object paramObject) {
/*  370 */     check("removeProviderProperty." + this.name);
/*  371 */     if (debug != null) {
/*  372 */       debug.println("Remove " + this.name + " provider property " + paramObject);
/*      */     }
/*  374 */     return implRemove(paramObject);
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
/*      */   public synchronized boolean remove(Object paramObject1, Object paramObject2) {
/*  395 */     check("removeProviderProperty." + this.name);
/*  396 */     if (debug != null) {
/*  397 */       debug.println("Remove " + this.name + " provider property " + paramObject1);
/*      */     }
/*  399 */     return implRemove(paramObject1, paramObject2);
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
/*      */   public synchronized boolean replace(Object paramObject1, Object paramObject2, Object paramObject3) {
/*  421 */     check("putProviderProperty." + this.name);
/*      */     
/*  423 */     if (debug != null) {
/*  424 */       debug.println("Replace " + this.name + " provider property " + paramObject1);
/*      */     }
/*  426 */     return implReplace(paramObject1, paramObject2, paramObject3);
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
/*      */   public synchronized Object replace(Object paramObject1, Object paramObject2) {
/*  447 */     check("putProviderProperty." + this.name);
/*      */     
/*  449 */     if (debug != null) {
/*  450 */       debug.println("Replace " + this.name + " provider property " + paramObject1);
/*      */     }
/*  452 */     return implReplace(paramObject1, paramObject2);
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
/*      */   public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  475 */     check("putProviderProperty." + this.name);
/*      */     
/*  477 */     if (debug != null) {
/*  478 */       debug.println("ReplaceAll " + this.name + " provider property ");
/*      */     }
/*  480 */     implReplaceAll(paramBiFunction);
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
/*      */   public synchronized Object compute(Object paramObject, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  504 */     check("putProviderProperty." + this.name);
/*  505 */     check("removeProviderProperty" + this.name);
/*      */     
/*  507 */     if (debug != null) {
/*  508 */       debug.println("Compute " + this.name + " provider property " + paramObject);
/*      */     }
/*  510 */     return implCompute(paramObject, paramBiFunction);
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
/*      */   public synchronized Object computeIfAbsent(Object paramObject, Function<? super Object, ? extends Object> paramFunction) {
/*  534 */     check("putProviderProperty." + this.name);
/*  535 */     check("removeProviderProperty" + this.name);
/*      */     
/*  537 */     if (debug != null) {
/*  538 */       debug.println("ComputeIfAbsent " + this.name + " provider property " + paramObject);
/*      */     }
/*      */     
/*  541 */     return implComputeIfAbsent(paramObject, paramFunction);
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
/*      */   public synchronized Object computeIfPresent(Object paramObject, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  563 */     check("putProviderProperty." + this.name);
/*  564 */     check("removeProviderProperty" + this.name);
/*      */     
/*  566 */     if (debug != null) {
/*  567 */       debug.println("ComputeIfPresent " + this.name + " provider property " + paramObject);
/*      */     }
/*      */     
/*  570 */     return implComputeIfPresent(paramObject, paramBiFunction);
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
/*      */   public synchronized Object merge(Object paramObject1, Object paramObject2, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  595 */     check("putProviderProperty." + this.name);
/*  596 */     check("removeProviderProperty" + this.name);
/*      */     
/*  598 */     if (debug != null) {
/*  599 */       debug.println("Merge " + this.name + " provider property " + paramObject1);
/*      */     }
/*  601 */     return implMerge(paramObject1, paramObject2, paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(Object paramObject) {
/*  607 */     checkInitialized();
/*  608 */     return super.get(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getOrDefault(Object paramObject1, Object paramObject2) {
/*  615 */     checkInitialized();
/*  616 */     return super.getOrDefault(paramObject1, paramObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void forEach(BiConsumer<? super Object, ? super Object> paramBiConsumer) {
/*  624 */     checkInitialized();
/*  625 */     super.forEach(paramBiConsumer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<Object> keys() {
/*  631 */     checkInitialized();
/*  632 */     return super.keys();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<Object> elements() {
/*  638 */     checkInitialized();
/*  639 */     return super.elements();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getProperty(String paramString) {
/*  644 */     checkInitialized();
/*  645 */     return super.getProperty(paramString);
/*      */   }
/*      */   
/*      */   private void checkInitialized() {
/*  649 */     if (!this.initialized) {
/*  650 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */   
/*      */   private void check(String paramString) {
/*  655 */     checkInitialized();
/*  656 */     SecurityManager securityManager = System.getSecurityManager();
/*  657 */     if (securityManager != null) {
/*  658 */       securityManager.checkSecurityAccess(paramString);
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
/*      */   private void putId() {
/*  687 */     super.put("Provider.id name", String.valueOf(this.name));
/*  688 */     super.put("Provider.id version", String.valueOf(this.version));
/*  689 */     super.put("Provider.id info", String.valueOf(this.info));
/*  690 */     super.put("Provider.id className", getClass().getName());
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  695 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  696 */     for (Map.Entry<Object, Object> entry : super.entrySet()) {
/*  697 */       hashMap.put(entry.getKey(), entry.getValue());
/*      */     }
/*  699 */     this.defaults = null;
/*  700 */     paramObjectInputStream.defaultReadObject();
/*  701 */     implClear();
/*  702 */     this.initialized = true;
/*  703 */     putAll(hashMap);
/*      */   }
/*      */   
/*      */   private boolean checkLegacy(Object paramObject) {
/*  707 */     String str = (String)paramObject;
/*  708 */     if (str.startsWith("Provider.")) {
/*  709 */       return false;
/*      */     }
/*      */     
/*  712 */     this.legacyChanged = true;
/*  713 */     if (this.legacyStrings == null) {
/*  714 */       this.legacyStrings = new LinkedHashMap<>();
/*      */     }
/*  716 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void implPutAll(Map<?, ?> paramMap) {
/*  725 */     for (Map.Entry<?, ?> entry : paramMap.entrySet()) {
/*  726 */       implPut(entry.getKey(), entry.getValue());
/*      */     }
/*      */   }
/*      */   
/*      */   private Object implRemove(Object paramObject) {
/*  731 */     if (paramObject instanceof String) {
/*  732 */       if (!checkLegacy(paramObject)) {
/*  733 */         return null;
/*      */       }
/*  735 */       this.legacyStrings.remove(paramObject);
/*      */     } 
/*  737 */     return super.remove(paramObject);
/*      */   }
/*      */   
/*      */   private boolean implRemove(Object paramObject1, Object paramObject2) {
/*  741 */     if (paramObject1 instanceof String && paramObject2 instanceof String) {
/*  742 */       if (!checkLegacy(paramObject1)) {
/*  743 */         return false;
/*      */       }
/*  745 */       this.legacyStrings.remove(paramObject1, paramObject2);
/*      */     } 
/*  747 */     return super.remove(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   private boolean implReplace(Object paramObject1, Object paramObject2, Object paramObject3) {
/*  751 */     if (paramObject1 instanceof String && paramObject2 instanceof String && paramObject3 instanceof String) {
/*      */       
/*  753 */       if (!checkLegacy(paramObject1)) {
/*  754 */         return false;
/*      */       }
/*  756 */       this.legacyStrings.replace((String)paramObject1, (String)paramObject2, (String)paramObject3);
/*      */     } 
/*      */     
/*  759 */     return super.replace(paramObject1, paramObject2, paramObject3);
/*      */   }
/*      */   
/*      */   private Object implReplace(Object paramObject1, Object paramObject2) {
/*  763 */     if (paramObject1 instanceof String && paramObject2 instanceof String) {
/*  764 */       if (!checkLegacy(paramObject1)) {
/*  765 */         return null;
/*      */       }
/*  767 */       this.legacyStrings.replace((String)paramObject1, (String)paramObject2);
/*      */     } 
/*  769 */     return super.replace(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   private void implReplaceAll(BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  773 */     this.legacyChanged = true;
/*  774 */     if (this.legacyStrings == null) {
/*  775 */       this.legacyStrings = new LinkedHashMap<>();
/*      */     } else {
/*  777 */       this.legacyStrings.replaceAll(paramBiFunction);
/*      */     } 
/*  779 */     super.replaceAll(paramBiFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   private Object implMerge(Object paramObject1, Object paramObject2, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  784 */     if (paramObject1 instanceof String && paramObject2 instanceof String) {
/*  785 */       if (!checkLegacy(paramObject1)) {
/*  786 */         return null;
/*      */       }
/*  788 */       this.legacyStrings.merge((String)paramObject1, paramObject2, paramBiFunction);
/*      */     } 
/*      */     
/*  791 */     return super.merge(paramObject1, paramObject2, paramBiFunction);
/*      */   }
/*      */   
/*      */   private Object implCompute(Object paramObject, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  795 */     if (paramObject instanceof String) {
/*  796 */       if (!checkLegacy(paramObject)) {
/*  797 */         return null;
/*      */       }
/*  799 */       this.legacyStrings.computeIfAbsent((String)paramObject, (Function)paramBiFunction);
/*      */     } 
/*      */     
/*  802 */     return super.compute(paramObject, paramBiFunction);
/*      */   }
/*      */   
/*      */   private Object implComputeIfAbsent(Object paramObject, Function<? super Object, ? extends Object> paramFunction) {
/*  806 */     if (paramObject instanceof String) {
/*  807 */       if (!checkLegacy(paramObject)) {
/*  808 */         return null;
/*      */       }
/*  810 */       this.legacyStrings.computeIfAbsent(paramObject, paramFunction);
/*      */     } 
/*      */     
/*  813 */     return super.computeIfAbsent(paramObject, paramFunction);
/*      */   }
/*      */   
/*      */   private Object implComputeIfPresent(Object paramObject, BiFunction<? super Object, ? super Object, ? extends Object> paramBiFunction) {
/*  817 */     if (paramObject instanceof String) {
/*  818 */       if (!checkLegacy(paramObject)) {
/*  819 */         return null;
/*      */       }
/*  821 */       this.legacyStrings.computeIfPresent(paramObject, paramBiFunction);
/*      */     } 
/*      */     
/*  824 */     return super.computeIfPresent(paramObject, paramBiFunction);
/*      */   }
/*      */   
/*      */   private Object implPut(Object paramObject1, Object paramObject2) {
/*  828 */     if (paramObject1 instanceof String && paramObject2 instanceof String) {
/*  829 */       if (!checkLegacy(paramObject1)) {
/*  830 */         return null;
/*      */       }
/*  832 */       this.legacyStrings.put((String)paramObject1, (String)paramObject2);
/*      */     } 
/*  834 */     return super.put(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   private Object implPutIfAbsent(Object paramObject1, Object paramObject2) {
/*  838 */     if (paramObject1 instanceof String && paramObject2 instanceof String) {
/*  839 */       if (!checkLegacy(paramObject1)) {
/*  840 */         return null;
/*      */       }
/*  842 */       this.legacyStrings.putIfAbsent((String)paramObject1, (String)paramObject2);
/*      */     } 
/*  844 */     return super.putIfAbsent(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   private void implClear() {
/*  848 */     if (this.legacyStrings != null) {
/*  849 */       this.legacyStrings.clear();
/*      */     }
/*  851 */     if (this.legacyMap != null) {
/*  852 */       this.legacyMap.clear();
/*      */     }
/*  854 */     if (this.serviceMap != null) {
/*  855 */       this.serviceMap.clear();
/*      */     }
/*  857 */     this.legacyChanged = false;
/*  858 */     this.servicesChanged = false;
/*  859 */     this.serviceSet = null;
/*  860 */     super.clear();
/*  861 */     putId();
/*      */   }
/*      */   
/*      */   private static class ServiceKey {
/*      */     private final String type;
/*      */     private final String algorithm;
/*      */     private final String originalAlgorithm;
/*      */     
/*      */     private ServiceKey(String param1String1, String param1String2, boolean param1Boolean) {
/*  870 */       this.type = param1String1;
/*  871 */       this.originalAlgorithm = param1String2;
/*  872 */       param1String2 = param1String2.toUpperCase(Locale.ENGLISH);
/*  873 */       this.algorithm = param1Boolean ? param1String2.intern() : param1String2;
/*      */     }
/*      */     public int hashCode() {
/*  876 */       return this.type.hashCode() + this.algorithm.hashCode();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/*  879 */       if (this == param1Object) {
/*  880 */         return true;
/*      */       }
/*  882 */       if (!(param1Object instanceof ServiceKey)) {
/*  883 */         return false;
/*      */       }
/*  885 */       ServiceKey serviceKey = (ServiceKey)param1Object;
/*  886 */       return (this.type.equals(serviceKey.type) && this.algorithm
/*  887 */         .equals(serviceKey.algorithm));
/*      */     }
/*      */     boolean matches(String param1String1, String param1String2) {
/*  890 */       return (this.type == param1String1 && this.originalAlgorithm == param1String2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureLegacyParsed() {
/*  899 */     if (!this.legacyChanged || this.legacyStrings == null) {
/*      */       return;
/*      */     }
/*  902 */     this.serviceSet = null;
/*  903 */     if (this.legacyMap == null) {
/*  904 */       this.legacyMap = new LinkedHashMap<>();
/*      */     } else {
/*  906 */       this.legacyMap.clear();
/*      */     } 
/*  908 */     for (Map.Entry<String, String> entry : this.legacyStrings.entrySet()) {
/*  909 */       parseLegacyPut((String)entry.getKey(), (String)entry.getValue());
/*      */     }
/*  911 */     removeInvalidServices(this.legacyMap);
/*  912 */     this.legacyChanged = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeInvalidServices(Map<ServiceKey, Service> paramMap) {
/*  921 */     for (Iterator<Map.Entry> iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
/*  922 */       Service service = (Service)((Map.Entry)iterator.next()).getValue();
/*  923 */       if (!service.isValid()) {
/*  924 */         iterator.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private String[] getTypeAndAlgorithm(String paramString) {
/*  930 */     int i = paramString.indexOf(".");
/*  931 */     if (i < 1) {
/*  932 */       if (debug != null) {
/*  933 */         debug.println("Ignoring invalid entry in provider " + this.name + ":" + paramString);
/*      */       }
/*      */       
/*  936 */       return null;
/*      */     } 
/*  938 */     String str1 = paramString.substring(0, i);
/*  939 */     String str2 = paramString.substring(i + 1);
/*  940 */     return new String[] { str1, str2 };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  945 */   private static final int ALIAS_LENGTH = "Alg.Alias.".length();
/*      */   
/*      */   private void parseLegacyPut(String paramString1, String paramString2) {
/*  948 */     if (paramString1.toLowerCase(Locale.ENGLISH).startsWith("alg.alias.")) {
/*      */ 
/*      */       
/*  951 */       String str1 = paramString2;
/*  952 */       String str2 = paramString1.substring(ALIAS_LENGTH);
/*  953 */       String[] arrayOfString = getTypeAndAlgorithm(str2);
/*  954 */       if (arrayOfString == null) {
/*      */         return;
/*      */       }
/*  957 */       String str3 = getEngineName(arrayOfString[0]);
/*  958 */       String str4 = arrayOfString[1].intern();
/*  959 */       ServiceKey serviceKey = new ServiceKey(str3, str1, true);
/*  960 */       Service service = this.legacyMap.get(serviceKey);
/*  961 */       if (service == null) {
/*  962 */         service = new Service(this);
/*  963 */         service.type = str3;
/*  964 */         service.algorithm = str1;
/*  965 */         this.legacyMap.put(serviceKey, service);
/*      */       } 
/*  967 */       this.legacyMap.put(new ServiceKey(str3, str4, true), service);
/*  968 */       service.addAlias(str4);
/*      */     } else {
/*  970 */       String[] arrayOfString = getTypeAndAlgorithm(paramString1);
/*  971 */       if (arrayOfString == null) {
/*      */         return;
/*      */       }
/*  974 */       int i = arrayOfString[1].indexOf(' ');
/*  975 */       if (i == -1) {
/*      */         
/*  977 */         String str1 = getEngineName(arrayOfString[0]);
/*  978 */         String str2 = arrayOfString[1].intern();
/*  979 */         String str3 = paramString2;
/*  980 */         ServiceKey serviceKey = new ServiceKey(str1, str2, true);
/*  981 */         Service service = this.legacyMap.get(serviceKey);
/*  982 */         if (service == null) {
/*  983 */           service = new Service(this);
/*  984 */           service.type = str1;
/*  985 */           service.algorithm = str2;
/*  986 */           this.legacyMap.put(serviceKey, service);
/*      */         } 
/*  988 */         service.className = str3;
/*      */       } else {
/*      */         
/*  991 */         String str1 = paramString2;
/*  992 */         String str2 = getEngineName(arrayOfString[0]);
/*  993 */         String str3 = arrayOfString[1];
/*  994 */         String str4 = str3.substring(0, i).intern();
/*  995 */         String str5 = str3.substring(i + 1);
/*      */         
/*  997 */         while (str5.startsWith(" ")) {
/*  998 */           str5 = str5.substring(1);
/*      */         }
/* 1000 */         str5 = str5.intern();
/* 1001 */         ServiceKey serviceKey = new ServiceKey(str2, str4, true);
/* 1002 */         Service service = this.legacyMap.get(serviceKey);
/* 1003 */         if (service == null) {
/* 1004 */           service = new Service(this);
/* 1005 */           service.type = str2;
/* 1006 */           service.algorithm = str4;
/* 1007 */           this.legacyMap.put(serviceKey, service);
/*      */         } 
/* 1009 */         service.addAttribute(str5, str1);
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
/*      */   public synchronized Service getService(String paramString1, String paramString2) {
/* 1035 */     checkInitialized();
/*      */     
/* 1037 */     ServiceKey serviceKey = previousKey;
/* 1038 */     if (!serviceKey.matches(paramString1, paramString2)) {
/* 1039 */       serviceKey = new ServiceKey(paramString1, paramString2, false);
/* 1040 */       previousKey = serviceKey;
/*      */     } 
/* 1042 */     if (this.serviceMap != null) {
/* 1043 */       Service service = this.serviceMap.get(serviceKey);
/* 1044 */       if (service != null) {
/* 1045 */         return service;
/*      */       }
/*      */     } 
/* 1048 */     ensureLegacyParsed();
/* 1049 */     return (this.legacyMap != null) ? this.legacyMap.get(serviceKey) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1058 */   private static volatile ServiceKey previousKey = new ServiceKey("", "", false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Set<Service> getServices() {
/* 1071 */     checkInitialized();
/* 1072 */     if (this.legacyChanged || this.servicesChanged) {
/* 1073 */       this.serviceSet = null;
/*      */     }
/* 1075 */     if (this.serviceSet == null) {
/* 1076 */       ensureLegacyParsed();
/* 1077 */       LinkedHashSet<? extends Service> linkedHashSet = new LinkedHashSet();
/* 1078 */       if (this.serviceMap != null) {
/* 1079 */         linkedHashSet.addAll(this.serviceMap.values());
/*      */       }
/* 1081 */       if (this.legacyMap != null) {
/* 1082 */         linkedHashSet.addAll(this.legacyMap.values());
/*      */       }
/* 1084 */       this.serviceSet = Collections.unmodifiableSet(linkedHashSet);
/* 1085 */       this.servicesChanged = false;
/*      */     } 
/* 1087 */     return this.serviceSet;
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
/*      */   protected synchronized void putService(Service paramService) {
/* 1120 */     check("putProviderProperty." + this.name);
/* 1121 */     if (debug != null) {
/* 1122 */       debug.println(this.name + ".putService(): " + paramService);
/*      */     }
/* 1124 */     if (paramService == null) {
/* 1125 */       throw new NullPointerException();
/*      */     }
/* 1127 */     if (paramService.getProvider() != this) {
/* 1128 */       throw new IllegalArgumentException("service.getProvider() must match this Provider object");
/*      */     }
/*      */     
/* 1131 */     if (this.serviceMap == null) {
/* 1132 */       this.serviceMap = new LinkedHashMap<>();
/*      */     }
/* 1134 */     this.servicesChanged = true;
/* 1135 */     String str1 = paramService.getType();
/* 1136 */     String str2 = paramService.getAlgorithm();
/* 1137 */     ServiceKey serviceKey = new ServiceKey(str1, str2, true);
/*      */     
/* 1139 */     implRemoveService(this.serviceMap.get(serviceKey));
/* 1140 */     this.serviceMap.put(serviceKey, paramService);
/* 1141 */     for (String str : paramService.getAliases()) {
/* 1142 */       this.serviceMap.put(new ServiceKey(str1, str, true), paramService);
/*      */     }
/* 1144 */     putPropertyStrings(paramService);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void putPropertyStrings(Service paramService) {
/* 1152 */     String str1 = paramService.getType();
/* 1153 */     String str2 = paramService.getAlgorithm();
/*      */     
/* 1155 */     super.put(str1 + "." + str2, paramService.getClassName());
/* 1156 */     for (String str : paramService.getAliases()) {
/* 1157 */       super.put("Alg.Alias." + str1 + "." + str, str2);
/*      */     }
/* 1159 */     for (Map.Entry entry : paramService.attributes.entrySet()) {
/* 1160 */       String str = str1 + "." + str2 + " " + entry.getKey();
/* 1161 */       super.put(str, entry.getValue());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removePropertyStrings(Service paramService) {
/* 1170 */     String str1 = paramService.getType();
/* 1171 */     String str2 = paramService.getAlgorithm();
/*      */     
/* 1173 */     super.remove(str1 + "." + str2);
/* 1174 */     for (String str : paramService.getAliases()) {
/* 1175 */       super.remove("Alg.Alias." + str1 + "." + str);
/*      */     }
/* 1177 */     for (Map.Entry entry : paramService.attributes.entrySet()) {
/* 1178 */       String str = str1 + "." + str2 + " " + entry.getKey();
/* 1179 */       super.remove(str);
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
/*      */   protected synchronized void removeService(Service paramService) {
/* 1212 */     check("removeProviderProperty." + this.name);
/* 1213 */     if (debug != null) {
/* 1214 */       debug.println(this.name + ".removeService(): " + paramService);
/*      */     }
/* 1216 */     if (paramService == null) {
/* 1217 */       throw new NullPointerException();
/*      */     }
/* 1219 */     implRemoveService(paramService);
/*      */   }
/*      */   
/*      */   private void implRemoveService(Service paramService) {
/* 1223 */     if (paramService == null || this.serviceMap == null) {
/*      */       return;
/*      */     }
/* 1226 */     String str1 = paramService.getType();
/* 1227 */     String str2 = paramService.getAlgorithm();
/* 1228 */     ServiceKey serviceKey = new ServiceKey(str1, str2, false);
/* 1229 */     Service service = this.serviceMap.get(serviceKey);
/* 1230 */     if (paramService != service) {
/*      */       return;
/*      */     }
/* 1233 */     this.servicesChanged = true;
/* 1234 */     this.serviceMap.remove(serviceKey);
/* 1235 */     for (String str : paramService.getAliases()) {
/* 1236 */       this.serviceMap.remove(new ServiceKey(str1, str, false));
/*      */     }
/* 1238 */     removePropertyStrings(paramService);
/*      */   }
/*      */   
/*      */   private static class UString
/*      */   {
/*      */     final String string;
/*      */     final String lowerString;
/*      */     
/*      */     UString(String param1String) {
/* 1247 */       this.string = param1String;
/* 1248 */       this.lowerString = param1String.toLowerCase(Locale.ENGLISH);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1252 */       return this.lowerString.hashCode();
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1256 */       if (this == param1Object) {
/* 1257 */         return true;
/*      */       }
/* 1259 */       if (!(param1Object instanceof UString)) {
/* 1260 */         return false;
/*      */       }
/* 1262 */       UString uString = (UString)param1Object;
/* 1263 */       return this.lowerString.equals(uString.lowerString);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1267 */       return this.string;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class EngineDescription
/*      */   {
/*      */     final String name;
/*      */     final boolean supportsParameter;
/*      */     final String constructorParameterClassName;
/*      */     private volatile Class<?> constructorParameterClass;
/*      */     
/*      */     EngineDescription(String param1String1, boolean param1Boolean, String param1String2) {
/* 1279 */       this.name = param1String1;
/* 1280 */       this.supportsParameter = param1Boolean;
/* 1281 */       this.constructorParameterClassName = param1String2;
/*      */     }
/*      */     Class<?> getConstructorParameterClass() throws ClassNotFoundException {
/* 1284 */       Class<?> clazz = this.constructorParameterClass;
/* 1285 */       if (clazz == null) {
/* 1286 */         clazz = Class.forName(this.constructorParameterClassName);
/* 1287 */         this.constructorParameterClass = clazz;
/*      */       } 
/* 1289 */       return clazz;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addEngine(String paramString1, boolean paramBoolean, String paramString2) {
/* 1297 */     EngineDescription engineDescription = new EngineDescription(paramString1, paramBoolean, paramString2);
/*      */     
/* 1299 */     knownEngines.put(paramString1.toLowerCase(Locale.ENGLISH), engineDescription);
/* 1300 */     knownEngines.put(paramString1, engineDescription);
/*      */   }
/*      */ 
/*      */   
/* 1304 */   private static final Map<String, EngineDescription> knownEngines = new HashMap<>();
/*      */   static {
/* 1306 */     addEngine("AlgorithmParameterGenerator", false, null);
/* 1307 */     addEngine("AlgorithmParameters", false, null);
/* 1308 */     addEngine("KeyFactory", false, null);
/* 1309 */     addEngine("KeyPairGenerator", false, null);
/* 1310 */     addEngine("KeyStore", false, null);
/* 1311 */     addEngine("MessageDigest", false, null);
/* 1312 */     addEngine("SecureRandom", false, null);
/* 1313 */     addEngine("Signature", true, null);
/* 1314 */     addEngine("CertificateFactory", false, null);
/* 1315 */     addEngine("CertPathBuilder", false, null);
/* 1316 */     addEngine("CertPathValidator", false, null);
/* 1317 */     addEngine("CertStore", false, "java.security.cert.CertStoreParameters");
/*      */ 
/*      */     
/* 1320 */     addEngine("Cipher", true, null);
/* 1321 */     addEngine("ExemptionMechanism", false, null);
/* 1322 */     addEngine("Mac", true, null);
/* 1323 */     addEngine("KeyAgreement", true, null);
/* 1324 */     addEngine("KeyGenerator", false, null);
/* 1325 */     addEngine("SecretKeyFactory", false, null);
/*      */     
/* 1327 */     addEngine("KeyManagerFactory", false, null);
/* 1328 */     addEngine("SSLContext", false, null);
/* 1329 */     addEngine("TrustManagerFactory", false, null);
/*      */     
/* 1331 */     addEngine("GssApiMechanism", false, null);
/*      */     
/* 1333 */     addEngine("SaslClientFactory", false, null);
/* 1334 */     addEngine("SaslServerFactory", false, null);
/*      */     
/* 1336 */     addEngine("Policy", false, "java.security.Policy$Parameters");
/*      */ 
/*      */     
/* 1339 */     addEngine("Configuration", false, "javax.security.auth.login.Configuration$Parameters");
/*      */ 
/*      */     
/* 1342 */     addEngine("XMLSignatureFactory", false, null);
/* 1343 */     addEngine("KeyInfoFactory", false, null);
/* 1344 */     addEngine("TransformService", false, null);
/*      */     
/* 1346 */     addEngine("TerminalFactory", false, "java.lang.Object");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getEngineName(String paramString) {
/* 1354 */     EngineDescription engineDescription = knownEngines.get(paramString);
/* 1355 */     if (engineDescription == null) {
/* 1356 */       engineDescription = knownEngines.get(paramString.toLowerCase(Locale.ENGLISH));
/*      */     }
/* 1358 */     return (engineDescription == null) ? paramString : engineDescription.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Service
/*      */   {
/*      */     private String type;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String algorithm;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String className;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Provider provider;
/*      */ 
/*      */ 
/*      */     
/*      */     private List<String> aliases;
/*      */ 
/*      */ 
/*      */     
/*      */     private Map<Provider.UString, String> attributes;
/*      */ 
/*      */ 
/*      */     
/*      */     private volatile Reference<Class<?>> classRef;
/*      */ 
/*      */ 
/*      */     
/*      */     private volatile Boolean hasKeyAttributes;
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] supportedFormats;
/*      */ 
/*      */ 
/*      */     
/*      */     private Class[] supportedClasses;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean registered;
/*      */ 
/*      */ 
/*      */     
/* 1413 */     private static final Class<?>[] CLASS0 = new Class[0];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Service(Provider param1Provider) {
/* 1419 */       this.provider = param1Provider;
/* 1420 */       this.aliases = Collections.emptyList();
/* 1421 */       this.attributes = Collections.emptyMap();
/*      */     }
/*      */     
/*      */     private boolean isValid() {
/* 1425 */       return (this.type != null && this.algorithm != null && this.className != null);
/*      */     }
/*      */     
/*      */     private void addAlias(String param1String) {
/* 1429 */       if (this.aliases.isEmpty()) {
/* 1430 */         this.aliases = new ArrayList<>(2);
/*      */       }
/* 1432 */       this.aliases.add(param1String);
/*      */     }
/*      */     
/*      */     void addAttribute(String param1String1, String param1String2) {
/* 1436 */       if (this.attributes.isEmpty()) {
/* 1437 */         this.attributes = new HashMap<>(8);
/*      */       }
/* 1439 */       this.attributes.put(new Provider.UString(param1String1), param1String2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Service(Provider param1Provider, String param1String1, String param1String2, String param1String3, List<String> param1List, Map<String, String> param1Map) {
/* 1459 */       if (param1Provider == null || param1String1 == null || param1String2 == null || param1String3 == null)
/*      */       {
/* 1461 */         throw new NullPointerException();
/*      */       }
/* 1463 */       this.provider = param1Provider;
/* 1464 */       this.type = Provider.getEngineName(param1String1);
/* 1465 */       this.algorithm = param1String2;
/* 1466 */       this.className = param1String3;
/* 1467 */       if (param1List == null) {
/* 1468 */         this.aliases = Collections.emptyList();
/*      */       } else {
/* 1470 */         this.aliases = new ArrayList<>(param1List);
/*      */       } 
/* 1472 */       if (param1Map == null) {
/* 1473 */         this.attributes = Collections.emptyMap();
/*      */       } else {
/* 1475 */         this.attributes = new HashMap<>();
/* 1476 */         for (Map.Entry<String, String> entry : param1Map.entrySet()) {
/* 1477 */           this.attributes.put(new Provider.UString((String)entry.getKey()), (String)entry.getValue());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final String getType() {
/* 1488 */       return this.type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final String getAlgorithm() {
/* 1498 */       return this.algorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Provider getProvider() {
/* 1507 */       return this.provider;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final String getClassName() {
/* 1516 */       return this.className;
/*      */     }
/*      */ 
/*      */     
/*      */     private final List<String> getAliases() {
/* 1521 */       return this.aliases;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final String getAttribute(String param1String) {
/* 1536 */       if (param1String == null) {
/* 1537 */         throw new NullPointerException();
/*      */       }
/* 1539 */       return this.attributes.get(new Provider.UString(param1String));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object newInstance(Object param1Object) throws NoSuchAlgorithmException {
/* 1570 */       if (!this.registered) {
/* 1571 */         if (this.provider.getService(this.type, this.algorithm) != this) {
/* 1572 */           throw new NoSuchAlgorithmException("Service not registered with Provider " + this.provider
/*      */               
/* 1574 */               .getName() + ": " + this);
/*      */         }
/* 1576 */         this.registered = true;
/*      */       } 
/*      */       try {
/* 1579 */         Provider.EngineDescription engineDescription = (Provider.EngineDescription)Provider.knownEngines.get(this.type);
/* 1580 */         if (engineDescription == null)
/*      */         {
/*      */ 
/*      */           
/* 1584 */           return newInstanceGeneric(param1Object);
/*      */         }
/* 1586 */         if (engineDescription.constructorParameterClassName == null) {
/* 1587 */           if (param1Object != null) {
/* 1588 */             throw new InvalidParameterException("constructorParameter not used with " + this.type + " engines");
/*      */           }
/*      */ 
/*      */           
/* 1592 */           Class<?> clazz = getImplClass();
/* 1593 */           Class[] arrayOfClass = new Class[0];
/* 1594 */           Constructor<?> constructor1 = clazz.getConstructor(arrayOfClass);
/* 1595 */           return constructor1.newInstance(new Object[0]);
/*      */         } 
/* 1597 */         Class<?> clazz1 = engineDescription.getConstructorParameterClass();
/* 1598 */         if (param1Object != null) {
/* 1599 */           Class<?> clazz = param1Object.getClass();
/* 1600 */           if (!clazz1.isAssignableFrom(clazz)) {
/* 1601 */             throw new InvalidParameterException("constructorParameter must be instanceof " + engineDescription.constructorParameterClassName
/*      */                 
/* 1603 */                 .replace('$', '.') + " for engine type " + this.type);
/*      */           }
/*      */         } 
/*      */         
/* 1607 */         Class<?> clazz2 = getImplClass();
/* 1608 */         Constructor<?> constructor = clazz2.getConstructor(new Class[] { clazz1 });
/* 1609 */         return constructor.newInstance(new Object[] { param1Object });
/*      */       }
/* 1611 */       catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 1612 */         throw noSuchAlgorithmException;
/* 1613 */       } catch (InvocationTargetException invocationTargetException) {
/* 1614 */         throw new NoSuchAlgorithmException("Error constructing implementation (algorithm: " + this.algorithm + ", provider: " + this.provider
/*      */             
/* 1616 */             .getName() + ", class: " + this.className + ")", invocationTargetException
/* 1617 */             .getCause());
/* 1618 */       } catch (Exception exception) {
/* 1619 */         throw new NoSuchAlgorithmException("Error constructing implementation (algorithm: " + this.algorithm + ", provider: " + this.provider
/*      */             
/* 1621 */             .getName() + ", class: " + this.className + ")", exception);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private Class<?> getImplClass() throws NoSuchAlgorithmException {
/*      */       try {
/* 1629 */         Reference<Class<?>> reference = this.classRef;
/* 1630 */         Class<?> clazz = (reference == null) ? null : reference.get();
/* 1631 */         if (clazz == null) {
/* 1632 */           ClassLoader classLoader = this.provider.getClass().getClassLoader();
/* 1633 */           if (classLoader == null) {
/* 1634 */             clazz = Class.forName(this.className);
/*      */           } else {
/* 1636 */             clazz = classLoader.loadClass(this.className);
/*      */           } 
/* 1638 */           if (!Modifier.isPublic(clazz.getModifiers())) {
/* 1639 */             throw new NoSuchAlgorithmException("class configured for " + this.type + " (provider: " + this.provider
/*      */                 
/* 1641 */                 .getName() + ") is not public.");
/*      */           }
/* 1643 */           this.classRef = new WeakReference<>(clazz);
/*      */         } 
/* 1645 */         return clazz;
/* 1646 */       } catch (ClassNotFoundException classNotFoundException) {
/* 1647 */         throw new NoSuchAlgorithmException("class configured for " + this.type + " (provider: " + this.provider
/*      */             
/* 1649 */             .getName() + ") cannot be found.", classNotFoundException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object newInstanceGeneric(Object param1Object) throws Exception {
/* 1660 */       Class<?> clazz1 = getImplClass();
/* 1661 */       if (param1Object == null) {
/*      */         
/*      */         try {
/* 1664 */           Class[] arrayOfClass = new Class[0];
/* 1665 */           Constructor<?> constructor = clazz1.getConstructor(arrayOfClass);
/* 1666 */           return constructor.newInstance(new Object[0]);
/* 1667 */         } catch (NoSuchMethodException noSuchMethodException) {
/* 1668 */           throw new NoSuchAlgorithmException("No public no-arg constructor found in class " + this.className);
/*      */         } 
/*      */       }
/*      */       
/* 1672 */       Class<?> clazz2 = param1Object.getClass();
/* 1673 */       Constructor[] arrayOfConstructor = (Constructor[])clazz1.getConstructors();
/*      */ 
/*      */       
/* 1676 */       for (Constructor constructor : arrayOfConstructor) {
/* 1677 */         Class[] arrayOfClass = constructor.getParameterTypes();
/* 1678 */         if (arrayOfClass.length == 1)
/*      */         {
/*      */           
/* 1681 */           if (arrayOfClass[0].isAssignableFrom(clazz2))
/*      */           {
/*      */             
/* 1684 */             return constructor.newInstance(new Object[] { param1Object }); }  } 
/*      */       } 
/* 1686 */       throw new NoSuchAlgorithmException("No public constructor matching " + clazz2
/* 1687 */           .getName() + " found in class " + this.className);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean supportsParameter(Object param1Object) {
/* 1718 */       Provider.EngineDescription engineDescription = (Provider.EngineDescription)Provider.knownEngines.get(this.type);
/* 1719 */       if (engineDescription == null)
/*      */       {
/* 1721 */         return true;
/*      */       }
/* 1723 */       if (!engineDescription.supportsParameter) {
/* 1724 */         throw new InvalidParameterException("supportsParameter() not used with " + this.type + " engines");
/*      */       }
/*      */ 
/*      */       
/* 1728 */       if (param1Object != null && !(param1Object instanceof Key)) {
/* 1729 */         throw new InvalidParameterException("Parameter must be instanceof Key for engine " + this.type);
/*      */       }
/*      */       
/* 1732 */       if (!hasKeyAttributes()) {
/* 1733 */         return true;
/*      */       }
/* 1735 */       if (param1Object == null) {
/* 1736 */         return false;
/*      */       }
/* 1738 */       Key key = (Key)param1Object;
/* 1739 */       if (supportsKeyFormat(key)) {
/* 1740 */         return true;
/*      */       }
/* 1742 */       if (supportsKeyClass(key)) {
/* 1743 */         return true;
/*      */       }
/* 1745 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean hasKeyAttributes() {
/* 1753 */       Boolean bool = this.hasKeyAttributes;
/* 1754 */       if (bool == null) {
/* 1755 */         synchronized (this) {
/*      */           
/* 1757 */           String str = getAttribute("SupportedKeyFormats");
/* 1758 */           if (str != null) {
/* 1759 */             this.supportedFormats = str.split("\\|");
/*      */           }
/* 1761 */           str = getAttribute("SupportedKeyClasses");
/* 1762 */           if (str != null) {
/* 1763 */             String[] arrayOfString = str.split("\\|");
/* 1764 */             ArrayList<Class<?>> arrayList = new ArrayList(arrayOfString.length);
/*      */             
/* 1766 */             for (String str1 : arrayOfString) {
/* 1767 */               Class<?> clazz = getKeyClass(str1);
/* 1768 */               if (clazz != null) {
/* 1769 */                 arrayList.add(clazz);
/*      */               }
/*      */             } 
/* 1772 */             this.supportedClasses = (Class[])arrayList.<Class<?>[]>toArray((Class<?>[][])CLASS0);
/*      */           } 
/* 1774 */           boolean bool1 = (this.supportedFormats != null || this.supportedClasses != null) ? true : false;
/*      */           
/* 1776 */           bool = Boolean.valueOf(bool1);
/* 1777 */           this.hasKeyAttributes = bool;
/*      */         } 
/*      */       }
/* 1780 */       return bool.booleanValue();
/*      */     }
/*      */ 
/*      */     
/*      */     private Class<?> getKeyClass(String param1String) {
/*      */       try {
/* 1786 */         return Class.forName(param1String);
/* 1787 */       } catch (ClassNotFoundException classNotFoundException) {
/*      */ 
/*      */         
/*      */         try {
/* 1791 */           ClassLoader classLoader = this.provider.getClass().getClassLoader();
/* 1792 */           if (classLoader != null) {
/* 1793 */             return classLoader.loadClass(param1String);
/*      */           }
/* 1795 */         } catch (ClassNotFoundException classNotFoundException1) {}
/*      */ 
/*      */         
/* 1798 */         return null;
/*      */       } 
/*      */     }
/*      */     private boolean supportsKeyFormat(Key param1Key) {
/* 1802 */       if (this.supportedFormats == null) {
/* 1803 */         return false;
/*      */       }
/* 1805 */       String str = param1Key.getFormat();
/* 1806 */       if (str == null) {
/* 1807 */         return false;
/*      */       }
/* 1809 */       for (String str1 : this.supportedFormats) {
/* 1810 */         if (str1.equals(str)) {
/* 1811 */           return true;
/*      */         }
/*      */       } 
/* 1814 */       return false;
/*      */     }
/*      */     
/*      */     private boolean supportsKeyClass(Key param1Key) {
/* 1818 */       if (this.supportedClasses == null) {
/* 1819 */         return false;
/*      */       }
/* 1821 */       Class<?> clazz = param1Key.getClass();
/* 1822 */       for (Class clazz1 : this.supportedClasses) {
/* 1823 */         if (clazz1.isAssignableFrom(clazz)) {
/* 1824 */           return true;
/*      */         }
/*      */       } 
/* 1827 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1837 */       String str1 = this.aliases.isEmpty() ? "" : ("\r\n  aliases: " + this.aliases.toString());
/*      */       
/* 1839 */       String str2 = this.attributes.isEmpty() ? "" : ("\r\n  attributes: " + this.attributes.toString());
/* 1840 */       return this.provider.getName() + ": " + this.type + "." + this.algorithm + " -> " + this.className + str1 + str2 + "\r\n";
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/Provider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */