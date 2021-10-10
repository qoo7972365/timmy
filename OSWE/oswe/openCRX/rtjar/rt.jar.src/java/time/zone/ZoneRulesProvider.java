/*     */ package java.time.zone;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Objects;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ZoneRulesProvider
/*     */ {
/* 134 */   private static final CopyOnWriteArrayList<ZoneRulesProvider> PROVIDERS = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */   
/* 138 */   private static final ConcurrentMap<String, ZoneRulesProvider> ZONES = new ConcurrentHashMap<>(512, 0.75F, 2);
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 143 */     final ArrayList<ZoneRulesProvider> loaded = new ArrayList();
/* 144 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/* 146 */             String str = System.getProperty("java.time.zone.DefaultZoneRulesProvider");
/* 147 */             if (str != null) {
/*     */               try {
/* 149 */                 Class<?> clazz = Class.forName(str, true, ClassLoader.getSystemClassLoader());
/* 150 */                 ZoneRulesProvider zoneRulesProvider = ZoneRulesProvider.class.cast(clazz.newInstance());
/* 151 */                 ZoneRulesProvider.registerProvider(zoneRulesProvider);
/* 152 */                 loaded.add(zoneRulesProvider);
/* 153 */               } catch (Exception exception) {
/* 154 */                 throw new Error(exception);
/*     */               } 
/*     */             } else {
/* 157 */               ZoneRulesProvider.registerProvider(new TzdbZoneRulesProvider());
/*     */             } 
/* 159 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 163 */     ServiceLoader<ZoneRulesProvider> serviceLoader = ServiceLoader.load(ZoneRulesProvider.class, ClassLoader.getSystemClassLoader());
/* 164 */     Iterator<ZoneRulesProvider> iterator = serviceLoader.iterator();
/* 165 */     while (iterator.hasNext()) {
/*     */       ZoneRulesProvider zoneRulesProvider;
/*     */       try {
/* 168 */         zoneRulesProvider = iterator.next();
/* 169 */       } catch (ServiceConfigurationError serviceConfigurationError) {
/* 170 */         if (serviceConfigurationError.getCause() instanceof SecurityException) {
/*     */           continue;
/*     */         }
/* 173 */         throw serviceConfigurationError;
/*     */       } 
/* 175 */       boolean bool = false;
/* 176 */       for (ZoneRulesProvider zoneRulesProvider1 : arrayList) {
/* 177 */         if (zoneRulesProvider1.getClass() == zoneRulesProvider.getClass()) {
/* 178 */           bool = true;
/*     */         }
/*     */       } 
/* 181 */       if (!bool) {
/* 182 */         registerProvider0(zoneRulesProvider);
/* 183 */         arrayList.add(zoneRulesProvider);
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     PROVIDERS.addAll(arrayList);
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
/*     */   public static Set<String> getAvailableZoneIds() {
/* 199 */     return new HashSet<>(ZONES.keySet());
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
/*     */   public static ZoneRules getRules(String paramString, boolean paramBoolean) {
/* 226 */     Objects.requireNonNull(paramString, "zoneId");
/* 227 */     return getProvider(paramString).provideRules(paramString, paramBoolean);
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
/*     */   public static NavigableMap<String, ZoneRules> getVersions(String paramString) {
/* 255 */     Objects.requireNonNull(paramString, "zoneId");
/* 256 */     return getProvider(paramString).provideVersions(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ZoneRulesProvider getProvider(String paramString) {
/* 267 */     ZoneRulesProvider zoneRulesProvider = ZONES.get(paramString);
/* 268 */     if (zoneRulesProvider == null) {
/* 269 */       if (ZONES.isEmpty()) {
/* 270 */         throw new ZoneRulesException("No time-zone data files registered");
/*     */       }
/* 272 */       throw new ZoneRulesException("Unknown time-zone ID: " + paramString);
/*     */     } 
/* 274 */     return zoneRulesProvider;
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
/*     */   public static void registerProvider(ZoneRulesProvider paramZoneRulesProvider) {
/* 294 */     Objects.requireNonNull(paramZoneRulesProvider, "provider");
/* 295 */     registerProvider0(paramZoneRulesProvider);
/* 296 */     PROVIDERS.add(paramZoneRulesProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerProvider0(ZoneRulesProvider paramZoneRulesProvider) {
/* 306 */     for (String str : paramZoneRulesProvider.provideZoneIds()) {
/* 307 */       Objects.requireNonNull(str, "zoneId");
/* 308 */       ZoneRulesProvider zoneRulesProvider = ZONES.putIfAbsent(str, paramZoneRulesProvider);
/* 309 */       if (zoneRulesProvider != null) {
/* 310 */         throw new ZoneRulesException("Unable to register zone as one already registered with that ID: " + str + ", currently loading from provider: " + paramZoneRulesProvider);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean refresh() {
/* 341 */     boolean bool = false;
/* 342 */     for (ZoneRulesProvider zoneRulesProvider : PROVIDERS) {
/* 343 */       bool |= zoneRulesProvider.provideRefresh();
/*     */     }
/* 345 */     return bool;
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
/*     */   protected boolean provideRefresh() {
/* 436 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract Set<String> provideZoneIds();
/*     */   
/*     */   protected abstract ZoneRules provideRules(String paramString, boolean paramBoolean);
/*     */   
/*     */   protected abstract NavigableMap<String, ZoneRules> provideVersions(String paramString);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/zone/ZoneRulesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */