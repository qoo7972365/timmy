/*     */ package sun.security.jgss;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.jgss.spi.MechanismFactory;
/*     */ import sun.security.jgss.wrapper.NativeGSSFactory;
/*     */ import sun.security.jgss.wrapper.SunNativeProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ProviderList
/*     */ {
/*     */   private static final String PROV_PROP_PREFIX = "GssApiMechanism.";
/*  90 */   private static final int PROV_PROP_PREFIX_LEN = "GssApiMechanism."
/*  91 */     .length();
/*     */   
/*     */   private static final String SPI_MECH_FACTORY_TYPE = "sun.security.jgss.spi.MechanismFactory";
/*     */   
/*     */   private static final String DEFAULT_MECH_PROP = "sun.security.jgss.mechanism";
/*     */   
/*     */   public static final Oid DEFAULT_MECH_OID;
/*     */   
/*     */   private ArrayList<PreferencesEntry> preferences;
/*     */   
/*     */   private HashMap<PreferencesEntry, MechanismFactory> factories;
/*     */   
/*     */   private HashSet<Oid> mechs;
/*     */   
/*     */   private final GSSCaller caller;
/*     */   
/*     */   static {
/* 108 */     Oid oid = null;
/*     */     
/* 110 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.security.jgss.mechanism"));
/* 111 */     if (str != null) {
/* 112 */       oid = GSSUtil.createOid(str);
/*     */     }
/* 114 */     DEFAULT_MECH_OID = (oid == null) ? GSSUtil.GSS_KRB5_MECH_OID : oid;
/*     */   }
/*     */   public ProviderList(GSSCaller paramGSSCaller, boolean paramBoolean) {
/*     */     Provider[] arrayOfProvider;
/* 118 */     this.preferences = new ArrayList<>(5);
/*     */     
/* 120 */     this.factories = new HashMap<>(5);
/*     */     
/* 122 */     this.mechs = new HashSet<>(5);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     this.caller = paramGSSCaller;
/*     */     
/* 129 */     if (paramBoolean) {
/* 130 */       arrayOfProvider = new Provider[1];
/* 131 */       arrayOfProvider[0] = new SunNativeProvider();
/*     */     } else {
/* 133 */       arrayOfProvider = Security.getProviders();
/*     */     } 
/*     */     
/* 136 */     for (byte b = 0; b < arrayOfProvider.length; b++) {
/* 137 */       Provider provider = arrayOfProvider[b];
/*     */       try {
/* 139 */         addProviderAtEnd(provider, null);
/* 140 */       } catch (GSSException gSSException) {
/*     */         
/* 142 */         GSSUtil.debug("Error in adding provider " + provider
/* 143 */             .getName() + ": " + gSSException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMechFactoryProperty(String paramString) {
/* 154 */     return (paramString.startsWith("GssApiMechanism.") || paramString
/* 155 */       .regionMatches(true, 0, "GssApiMechanism.", 0, PROV_PROP_PREFIX_LEN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Oid getOidFromMechFactoryProperty(String paramString) throws GSSException {
/* 163 */     String str = paramString.substring(PROV_PROP_PREFIX_LEN);
/* 164 */     return new Oid(str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized MechanismFactory getMechFactory(Oid paramOid) throws GSSException {
/* 170 */     if (paramOid == null) paramOid = DEFAULT_MECH_OID; 
/* 171 */     return getMechFactory(paramOid, (Provider)null);
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
/*     */   public synchronized MechanismFactory getMechFactory(Oid paramOid, Provider paramProvider) throws GSSException {
/* 189 */     if (paramOid == null) paramOid = DEFAULT_MECH_OID;
/*     */     
/* 191 */     if (paramProvider == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 196 */       Iterator<PreferencesEntry> iterator = this.preferences.iterator();
/* 197 */       while (iterator.hasNext()) {
/* 198 */         PreferencesEntry preferencesEntry1 = iterator.next();
/* 199 */         if (preferencesEntry1.impliesMechanism(paramOid)) {
/* 200 */           MechanismFactory mechanismFactory = getMechFactory(preferencesEntry1, paramOid);
/* 201 */           if (mechanismFactory != null) return mechanismFactory; 
/*     */         } 
/*     */       } 
/* 204 */       throw new GSSExceptionImpl(2, paramOid);
/*     */     } 
/*     */ 
/*     */     
/* 208 */     PreferencesEntry preferencesEntry = new PreferencesEntry(paramProvider, paramOid);
/* 209 */     return getMechFactory(preferencesEntry, paramOid);
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
/*     */   private MechanismFactory getMechFactory(PreferencesEntry paramPreferencesEntry, Oid paramOid) throws GSSException {
/* 226 */     Provider provider = paramPreferencesEntry.getProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     PreferencesEntry preferencesEntry = new PreferencesEntry(provider, paramOid);
/* 233 */     MechanismFactory mechanismFactory = this.factories.get(preferencesEntry);
/* 234 */     if (mechanismFactory == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 239 */       String str1 = "GssApiMechanism." + paramOid.toString();
/* 240 */       String str2 = provider.getProperty(str1);
/* 241 */       if (str2 != null) {
/* 242 */         mechanismFactory = getMechFactoryImpl(provider, str2, paramOid, this.caller);
/* 243 */         this.factories.put(preferencesEntry, mechanismFactory);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 251 */       else if (paramPreferencesEntry.getOid() != null) {
/* 252 */         throw new GSSExceptionImpl(2, "Provider " + provider
/* 253 */             .getName() + " does not support mechanism " + paramOid);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 258 */     return mechanismFactory;
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
/*     */   private static MechanismFactory getMechFactoryImpl(Provider paramProvider, String paramString, Oid paramOid, GSSCaller paramGSSCaller) throws GSSException {
/*     */     try {
/* 278 */       Class<?> clazz2, clazz1 = Class.forName("sun.security.jgss.spi.MechanismFactory");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       ClassLoader classLoader = paramProvider.getClass().getClassLoader();
/*     */       
/* 292 */       if (classLoader != null) {
/* 293 */         clazz2 = classLoader.loadClass(paramString);
/*     */       } else {
/* 295 */         clazz2 = Class.forName(paramString);
/*     */       } 
/*     */       
/* 298 */       if (clazz1.isAssignableFrom(clazz2)) {
/*     */ 
/*     */         
/* 301 */         Constructor<?> constructor = clazz2.getConstructor(new Class[] { GSSCaller.class });
/* 302 */         MechanismFactory mechanismFactory = (MechanismFactory)constructor.newInstance(new Object[] { paramGSSCaller });
/*     */         
/* 304 */         if (mechanismFactory instanceof NativeGSSFactory) {
/* 305 */           ((NativeGSSFactory)mechanismFactory).setMech(paramOid);
/*     */         }
/* 307 */         return mechanismFactory;
/*     */       } 
/* 309 */       throw createGSSException(paramProvider, paramString, "is not a sun.security.jgss.spi.MechanismFactory", null);
/*     */     
/*     */     }
/* 312 */     catch (ClassNotFoundException classNotFoundException) {
/* 313 */       throw createGSSException(paramProvider, paramString, "cannot be created", classNotFoundException);
/* 314 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 315 */       throw createGSSException(paramProvider, paramString, "cannot be created", noSuchMethodException);
/* 316 */     } catch (InvocationTargetException invocationTargetException) {
/* 317 */       throw createGSSException(paramProvider, paramString, "cannot be created", invocationTargetException);
/* 318 */     } catch (InstantiationException instantiationException) {
/* 319 */       throw createGSSException(paramProvider, paramString, "cannot be created", instantiationException);
/* 320 */     } catch (IllegalAccessException illegalAccessException) {
/* 321 */       throw createGSSException(paramProvider, paramString, "cannot be created", illegalAccessException);
/* 322 */     } catch (SecurityException securityException) {
/* 323 */       throw createGSSException(paramProvider, paramString, "cannot be created", securityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GSSException createGSSException(Provider paramProvider, String paramString1, String paramString2, Exception paramException) {
/* 333 */     String str = paramString1 + " configured by " + paramProvider.getName() + " for GSS-API Mechanism Factory ";
/* 334 */     return new GSSExceptionImpl(2, str + paramString2, paramException);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid[] getMechs() {
/* 340 */     return this.mechs.<Oid>toArray(new Oid[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void addProviderAtFront(Provider paramProvider, Oid paramOid) throws GSSException {
/*     */     boolean bool;
/* 346 */     PreferencesEntry preferencesEntry = new PreferencesEntry(paramProvider, paramOid);
/*     */ 
/*     */ 
/*     */     
/* 350 */     Iterator<PreferencesEntry> iterator = this.preferences.iterator();
/* 351 */     while (iterator.hasNext()) {
/* 352 */       PreferencesEntry preferencesEntry1 = iterator.next();
/* 353 */       if (preferencesEntry.implies(preferencesEntry1)) {
/* 354 */         iterator.remove();
/*     */       }
/*     */     } 
/* 357 */     if (paramOid == null) {
/* 358 */       bool = addAllMechsFromProvider(paramProvider);
/*     */     } else {
/* 360 */       String str = paramOid.toString();
/* 361 */       if (paramProvider.getProperty("GssApiMechanism." + str) == null) {
/* 362 */         throw new GSSExceptionImpl(2, "Provider " + paramProvider
/* 363 */             .getName() + " does not support " + str);
/*     */       }
/*     */       
/* 366 */       this.mechs.add(paramOid);
/* 367 */       bool = true;
/*     */     } 
/*     */     
/* 370 */     if (bool) {
/* 371 */       this.preferences.add(0, preferencesEntry);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void addProviderAtEnd(Provider paramProvider, Oid paramOid) throws GSSException {
/*     */     boolean bool;
/* 378 */     PreferencesEntry preferencesEntry = new PreferencesEntry(paramProvider, paramOid);
/*     */ 
/*     */ 
/*     */     
/* 382 */     Iterator<PreferencesEntry> iterator = this.preferences.iterator();
/* 383 */     while (iterator.hasNext()) {
/* 384 */       PreferencesEntry preferencesEntry1 = iterator.next();
/* 385 */       if (preferencesEntry1.implies(preferencesEntry)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 391 */     if (paramOid == null) {
/* 392 */       bool = addAllMechsFromProvider(paramProvider);
/*     */     } else {
/* 394 */       String str = paramOid.toString();
/* 395 */       if (paramProvider.getProperty("GssApiMechanism." + str) == null) {
/* 396 */         throw new GSSExceptionImpl(2, "Provider " + paramProvider
/* 397 */             .getName() + " does not support " + str);
/*     */       }
/*     */       
/* 400 */       this.mechs.add(paramOid);
/* 401 */       bool = true;
/*     */     } 
/*     */     
/* 404 */     if (bool) {
/* 405 */       this.preferences.add(preferencesEntry);
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
/*     */   private boolean addAllMechsFromProvider(Provider paramProvider) {
/* 421 */     boolean bool = false;
/*     */ 
/*     */     
/* 424 */     Enumeration<Object> enumeration = paramProvider.keys();
/*     */ 
/*     */     
/* 427 */     while (enumeration.hasMoreElements()) {
/* 428 */       String str = (String)enumeration.nextElement();
/* 429 */       if (isMechFactoryProperty(str)) {
/*     */         
/*     */         try {
/* 432 */           Oid oid = getOidFromMechFactoryProperty(str);
/* 433 */           this.mechs.add(oid);
/* 434 */           bool = true;
/* 435 */         } catch (GSSException gSSException) {
/*     */           
/* 437 */           GSSUtil.debug("Ignore the invalid property " + str + " from provider " + paramProvider
/* 438 */               .getName());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 443 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class PreferencesEntry
/*     */   {
/*     */     private Provider p;
/*     */ 
/*     */ 
/*     */     
/*     */     private Oid oid;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PreferencesEntry(Provider param1Provider, Oid param1Oid) {
/* 461 */       this.p = param1Provider;
/* 462 */       this.oid = param1Oid;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 466 */       if (this == param1Object) {
/* 467 */         return true;
/*     */       }
/*     */       
/* 470 */       if (!(param1Object instanceof PreferencesEntry)) {
/* 471 */         return false;
/*     */       }
/*     */       
/* 474 */       PreferencesEntry preferencesEntry = (PreferencesEntry)param1Object;
/* 475 */       if (this.p.getName().equals(preferencesEntry.p.getName())) {
/* 476 */         if (this.oid != null && preferencesEntry.oid != null) {
/* 477 */           return this.oid.equals(preferencesEntry.oid);
/*     */         }
/* 479 */         return (this.oid == null && preferencesEntry.oid == null);
/*     */       } 
/*     */ 
/*     */       
/* 483 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 487 */       int i = 17;
/*     */       
/* 489 */       i = 37 * i + this.p.getName().hashCode();
/* 490 */       if (this.oid != null) {
/* 491 */         i = 37 * i + this.oid.hashCode();
/*     */       }
/*     */       
/* 494 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean implies(Object param1Object) {
/* 506 */       if (param1Object instanceof PreferencesEntry) {
/* 507 */         PreferencesEntry preferencesEntry = (PreferencesEntry)param1Object;
/* 508 */         return (equals(preferencesEntry) || (this.p
/* 509 */           .getName().equals(preferencesEntry.p.getName()) && this.oid == null));
/*     */       } 
/*     */       
/* 512 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     Provider getProvider() {
/* 517 */       return this.p;
/*     */     }
/*     */     
/*     */     Oid getOid() {
/* 521 */       return this.oid;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean impliesMechanism(Oid param1Oid) {
/* 534 */       return (this.oid == null || this.oid.equals(param1Oid));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 539 */       StringBuffer stringBuffer = new StringBuffer("<");
/* 540 */       stringBuffer.append(this.p.getName());
/* 541 */       stringBuffer.append(", ");
/* 542 */       stringBuffer.append(this.oid);
/* 543 */       stringBuffer.append(">");
/* 544 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/ProviderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */