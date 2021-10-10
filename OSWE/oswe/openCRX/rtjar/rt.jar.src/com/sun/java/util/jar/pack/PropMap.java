/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PropMap
/*     */   implements SortedMap<String, String>
/*     */ {
/*  52 */   private final TreeMap<String, String> theMap = new TreeMap<>();
/*     */ 
/*     */   
/*  55 */   private final List<Object> listenerList = new ArrayList(1);
/*     */   
/*     */   void addListener(Object paramObject) {
/*  58 */     assert Beans.isPropertyChangeListener(paramObject);
/*  59 */     this.listenerList.add(paramObject);
/*     */   }
/*     */   private static Map<String, String> defaultProps;
/*     */   void removeListener(Object paramObject) {
/*  63 */     assert Beans.isPropertyChangeListener(paramObject);
/*  64 */     this.listenerList.remove(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public String put(String paramString1, String paramString2) {
/*  69 */     String str = this.theMap.put(paramString1, paramString2);
/*  70 */     if (paramString2 != str && !this.listenerList.isEmpty()) {
/*  71 */       assert Beans.isBeansPresent();
/*     */       
/*  73 */       Object object = Beans.newPropertyChangeEvent(this, paramString1, str, paramString2);
/*  74 */       for (Object object1 : this.listenerList) {
/*  75 */         Beans.invokePropertyChange(object1, object);
/*     */       }
/*     */     } 
/*  78 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  86 */     Properties properties = new Properties();
/*     */ 
/*     */     
/*  89 */     properties.put("com.sun.java.util.jar.pack.disable.native", 
/*  90 */         String.valueOf(Boolean.getBoolean("com.sun.java.util.jar.pack.disable.native")));
/*     */ 
/*     */     
/*  93 */     properties.put("com.sun.java.util.jar.pack.verbose", 
/*  94 */         String.valueOf(Integer.getInteger("com.sun.java.util.jar.pack.verbose", 0)));
/*     */ 
/*     */     
/*  97 */     properties.put("com.sun.java.util.jar.pack.default.timezone", 
/*  98 */         String.valueOf(Boolean.getBoolean("com.sun.java.util.jar.pack.default.timezone")));
/*     */ 
/*     */     
/* 101 */     properties.put("pack.segment.limit", "-1");
/*     */ 
/*     */     
/* 104 */     properties.put("pack.keep.file.order", "true");
/*     */ 
/*     */     
/* 107 */     properties.put("pack.modification.time", "keep");
/*     */ 
/*     */     
/* 110 */     properties.put("pack.deflate.hint", "keep");
/*     */ 
/*     */     
/* 113 */     properties.put("pack.unknown.attribute", "pass");
/*     */ 
/*     */ 
/*     */     
/* 117 */     properties.put("com.sun.java.util.jar.pack.class.format.error", 
/* 118 */         System.getProperty("com.sun.java.util.jar.pack.class.format.error", "pass"));
/*     */ 
/*     */     
/* 121 */     properties.put("pack.effort", "5");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     String str = "intrinsic.properties";
/*     */     
/* 128 */     try (InputStream null = PackerImpl.class.getResourceAsStream(str)) {
/* 129 */       if (inputStream == null) {
/* 130 */         throw new RuntimeException(str + " cannot be loaded");
/*     */       }
/* 132 */       properties.load(inputStream);
/* 133 */     } catch (IOException iOException) {
/* 134 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */     
/* 137 */     for (Map.Entry<Object, Object> entry : properties.entrySet()) {
/* 138 */       String str1 = (String)entry.getKey();
/* 139 */       String str2 = (String)entry.getValue();
/* 140 */       if (str1.startsWith("attribute.")) {
/* 141 */         entry.setValue(Attribute.normalizeLayoutString(str2));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 146 */     HashMap<Object, Object> hashMap = new HashMap<>(properties);
/* 147 */     defaultProps = (Map)hashMap;
/*     */   }
/*     */   
/*     */   PropMap() {
/* 151 */     this.theMap.putAll(defaultProps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SortedMap<String, String> prefixMap(String paramString) {
/* 158 */     int i = paramString.length();
/* 159 */     if (i == 0)
/* 160 */       return this; 
/* 161 */     char c = (char)(paramString.charAt(i - 1) + 1);
/* 162 */     String str = paramString.substring(0, i - 1) + c;
/*     */     
/* 164 */     return subMap(paramString, str);
/*     */   }
/*     */   
/*     */   String getProperty(String paramString) {
/* 168 */     return get(paramString);
/*     */   }
/*     */   String getProperty(String paramString1, String paramString2) {
/* 171 */     String str = getProperty(paramString1);
/* 172 */     if (str == null)
/* 173 */       return paramString2; 
/* 174 */     return str;
/*     */   }
/*     */   String setProperty(String paramString1, String paramString2) {
/* 177 */     return put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   List<String> getProperties(String paramString) {
/* 182 */     Collection<String> collection = prefixMap(paramString).values();
/* 183 */     ArrayList<String> arrayList = new ArrayList(collection.size());
/* 184 */     arrayList.addAll(collection);
/* 185 */     while (arrayList.remove((Object)null));
/* 186 */     return arrayList;
/*     */   }
/*     */   
/*     */   private boolean toBoolean(String paramString) {
/* 190 */     return Boolean.valueOf(paramString).booleanValue();
/*     */   }
/*     */   boolean getBoolean(String paramString) {
/* 193 */     return toBoolean(getProperty(paramString));
/*     */   }
/*     */   boolean setBoolean(String paramString, boolean paramBoolean) {
/* 196 */     return toBoolean(setProperty(paramString, String.valueOf(paramBoolean)));
/*     */   }
/*     */   int toInteger(String paramString) {
/* 199 */     return toInteger(paramString, 0);
/*     */   }
/*     */   int toInteger(String paramString, int paramInt) {
/* 202 */     if (paramString == null) return paramInt; 
/* 203 */     if ("true".equals(paramString)) return 1; 
/* 204 */     if ("false".equals(paramString)) return 0; 
/* 205 */     return Integer.parseInt(paramString);
/*     */   }
/*     */   int getInteger(String paramString, int paramInt) {
/* 208 */     return toInteger(getProperty(paramString), paramInt);
/*     */   }
/*     */   int getInteger(String paramString) {
/* 211 */     return toInteger(getProperty(paramString));
/*     */   }
/*     */   int setInteger(String paramString, int paramInt) {
/* 214 */     return toInteger(setProperty(paramString, String.valueOf(paramInt)));
/*     */   }
/*     */   
/*     */   long toLong(String paramString) {
/*     */     try {
/* 219 */       return (paramString == null) ? 0L : Long.parseLong(paramString);
/* 220 */     } catch (NumberFormatException numberFormatException) {
/* 221 */       throw new IllegalArgumentException("Invalid value");
/*     */     } 
/*     */   }
/*     */   long getLong(String paramString) {
/* 225 */     return toLong(getProperty(paramString));
/*     */   }
/*     */   long setLong(String paramString, long paramLong) {
/* 228 */     return toLong(setProperty(paramString, String.valueOf(paramLong)));
/*     */   }
/*     */   
/*     */   int getTime(String paramString) {
/* 232 */     String str = getProperty(paramString, "0");
/* 233 */     if ("now".equals(str)) {
/* 234 */       return (int)((System.currentTimeMillis() + 500L) / 1000L);
/*     */     }
/* 236 */     long l = toLong(str);
/*     */ 
/*     */     
/* 239 */     if (l < 10000000000L && !"0".equals(str)) {
/* 240 */       Utils.log.warning("Supplied modtime appears to be seconds rather than milliseconds: " + str);
/*     */     }
/* 242 */     return (int)((l + 500L) / 1000L);
/*     */   }
/*     */   
/*     */   void list(PrintStream paramPrintStream) {
/* 246 */     PrintWriter printWriter = new PrintWriter(paramPrintStream);
/* 247 */     list(printWriter);
/* 248 */     printWriter.flush();
/*     */   }
/*     */   void list(PrintWriter paramPrintWriter) {
/* 251 */     paramPrintWriter.println("#PACK200[");
/* 252 */     Set<Map.Entry<String, String>> set = defaultProps.entrySet();
/* 253 */     for (Map.Entry<String, String> entry : this.theMap.entrySet()) {
/* 254 */       if (set.contains(entry))
/* 255 */         continue;  paramPrintWriter.println("  " + (String)entry.getKey() + " = " + (String)entry.getValue());
/*     */     } 
/* 257 */     paramPrintWriter.println("#]");
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 262 */     return this.theMap.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 267 */     return this.theMap.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object paramObject) {
/* 272 */     return this.theMap.containsKey(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object paramObject) {
/* 277 */     return this.theMap.containsValue(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(Object paramObject) {
/* 282 */     return this.theMap.get(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public String remove(Object paramObject) {
/* 287 */     return this.theMap.remove(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends String> paramMap) {
/* 292 */     this.theMap.putAll(paramMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 297 */     this.theMap.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 302 */     return this.theMap.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> values() {
/* 307 */     return this.theMap.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, String>> entrySet() {
/* 312 */     return this.theMap.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super String> comparator() {
/* 317 */     return this.theMap.comparator();
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedMap<String, String> subMap(String paramString1, String paramString2) {
/* 322 */     return this.theMap.subMap(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedMap<String, String> headMap(String paramString) {
/* 327 */     return this.theMap.headMap(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedMap<String, String> tailMap(String paramString) {
/* 332 */     return this.theMap.tailMap(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String firstKey() {
/* 337 */     return this.theMap.firstKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public String lastKey() {
/* 342 */     return this.theMap.lastKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Beans
/*     */   {
/* 354 */     private static final Class<?> propertyChangeListenerClass = getClass("java.beans.PropertyChangeListener");
/*     */ 
/*     */     
/* 357 */     private static final Class<?> propertyChangeEventClass = getClass("java.beans.PropertyChangeEvent");
/*     */ 
/*     */     
/* 360 */     private static final Method propertyChangeMethod = getMethod(propertyChangeListenerClass, "propertyChange", new Class[] { propertyChangeEventClass });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     private static final Constructor<?> propertyEventCtor = getConstructor(propertyChangeEventClass, new Class[] { Object.class, String.class, Object.class, Object.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Class<?> getClass(String param1String) {
/*     */       try {
/* 373 */         return Class.forName(param1String, true, Beans.class.getClassLoader());
/* 374 */       } catch (ClassNotFoundException classNotFoundException) {
/* 375 */         return null;
/*     */       } 
/*     */     }
/*     */     private static Constructor<?> getConstructor(Class<?> param1Class, Class<?>... param1VarArgs) {
/*     */       try {
/* 380 */         return (param1Class == null) ? null : param1Class.getDeclaredConstructor(param1VarArgs);
/* 381 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 382 */         throw new AssertionError(noSuchMethodException);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static Method getMethod(Class<?> param1Class, String param1String, Class<?>... param1VarArgs) {
/*     */       try {
/* 388 */         return (param1Class == null) ? null : param1Class.getMethod(param1String, param1VarArgs);
/* 389 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 390 */         throw new AssertionError(noSuchMethodException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean isBeansPresent() {
/* 398 */       return (propertyChangeListenerClass != null && propertyChangeEventClass != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean isPropertyChangeListener(Object param1Object) {
/* 406 */       if (propertyChangeListenerClass == null) {
/* 407 */         return false;
/*     */       }
/* 409 */       return propertyChangeListenerClass.isInstance(param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Object newPropertyChangeEvent(Object param1Object1, String param1String, Object param1Object2, Object param1Object3) {
/*     */       try {
/* 421 */         return propertyEventCtor.newInstance(new Object[] { param1Object1, param1String, param1Object2, param1Object3 });
/* 422 */       } catch (InstantiationException|IllegalAccessException instantiationException) {
/* 423 */         throw new AssertionError(instantiationException);
/* 424 */       } catch (InvocationTargetException invocationTargetException) {
/* 425 */         Throwable throwable = invocationTargetException.getCause();
/* 426 */         if (throwable instanceof Error)
/* 427 */           throw (Error)throwable; 
/* 428 */         if (throwable instanceof RuntimeException)
/* 429 */           throw (RuntimeException)throwable; 
/* 430 */         throw new AssertionError(invocationTargetException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static void invokePropertyChange(Object param1Object1, Object param1Object2) {
/*     */       try {
/* 440 */         propertyChangeMethod.invoke(param1Object1, new Object[] { param1Object2 });
/* 441 */       } catch (IllegalAccessException illegalAccessException) {
/* 442 */         throw new AssertionError(illegalAccessException);
/* 443 */       } catch (InvocationTargetException invocationTargetException) {
/* 444 */         Throwable throwable = invocationTargetException.getCause();
/* 445 */         if (throwable instanceof Error)
/* 446 */           throw (Error)throwable; 
/* 447 */         if (throwable instanceof RuntimeException)
/* 448 */           throw (RuntimeException)throwable; 
/* 449 */         throw new AssertionError(invocationTargetException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/PropMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */