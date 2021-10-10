/*     */ package sun.security.jca;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  59 */   static final Debug debug = Debug.getInstance("jca", "ProviderList");
/*     */   
/*  61 */   private static final ProviderConfig[] PC0 = new ProviderConfig[0];
/*     */   
/*  63 */   private static final Provider[] P0 = new Provider[0];
/*     */ 
/*     */   
/*  66 */   static final ProviderList EMPTY = new ProviderList(PC0, true);
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final Provider EMPTY_PROVIDER = new Provider("##Empty##", 1.0D, "initialization in progress")
/*     */     {
/*     */       private static final long serialVersionUID = 1151354171352296389L;
/*     */       
/*     */       public Provider.Service getService(String param1String1, String param1String2) {
/*  75 */         return null;
/*     */       }
/*     */     };
/*     */   
/*     */   private final ProviderConfig[] configs;
/*     */   private volatile boolean allLoaded;
/*     */   
/*     */   static ProviderList fromSecurityProperties() {
/*  83 */     return AccessController.<ProviderList>doPrivileged(new PrivilegedAction<ProviderList>()
/*     */         {
/*     */           public ProviderList run() {
/*  86 */             return new ProviderList();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static ProviderList add(ProviderList paramProviderList, Provider paramProvider) {
/*  92 */     return insertAt(paramProviderList, paramProvider, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ProviderList insertAt(ProviderList paramProviderList, Provider paramProvider, int paramInt) {
/*  97 */     if (paramProviderList.getProvider(paramProvider.getName()) != null) {
/*  98 */       return paramProviderList;
/*     */     }
/*     */     
/* 101 */     ArrayList<ProviderConfig> arrayList = new ArrayList(Arrays.asList((Object[])paramProviderList.configs));
/* 102 */     int i = arrayList.size();
/* 103 */     if (paramInt < 0 || paramInt > i) {
/* 104 */       paramInt = i;
/*     */     }
/* 106 */     arrayList.add(paramInt, new ProviderConfig(paramProvider));
/* 107 */     return new ProviderList(arrayList.<ProviderConfig>toArray(PC0), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ProviderList remove(ProviderList paramProviderList, String paramString) {
/* 112 */     if (paramProviderList.getProvider(paramString) == null) {
/* 113 */       return paramProviderList;
/*     */     }
/*     */     
/* 116 */     ProviderConfig[] arrayOfProviderConfig = new ProviderConfig[paramProviderList.size() - 1];
/* 117 */     byte b = 0;
/* 118 */     for (ProviderConfig providerConfig : paramProviderList.configs) {
/* 119 */       if (!providerConfig.getProvider().getName().equals(paramString)) {
/* 120 */         arrayOfProviderConfig[b++] = providerConfig;
/*     */       }
/*     */     } 
/* 123 */     return new ProviderList(arrayOfProviderConfig, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProviderList newList(Provider... paramVarArgs) {
/* 129 */     ProviderConfig[] arrayOfProviderConfig = new ProviderConfig[paramVarArgs.length];
/* 130 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/* 131 */       arrayOfProviderConfig[b] = new ProviderConfig(paramVarArgs[b]);
/*     */     }
/* 133 */     return new ProviderList(arrayOfProviderConfig, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   private final List<Provider> userList = new AbstractList<Provider>() {
/*     */       public int size() {
/* 145 */         return ProviderList.this.configs.length;
/*     */       }
/*     */       public Provider get(int param1Int) {
/* 148 */         return ProviderList.this.getProvider(param1Int);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProviderList(ProviderConfig[] paramArrayOfProviderConfig, boolean paramBoolean) {
/* 156 */     this.configs = paramArrayOfProviderConfig;
/* 157 */     this.allLoaded = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProviderList() {
/* 164 */     ArrayList<ProviderConfig> arrayList = new ArrayList();
/* 165 */     for (byte b = 1;; b++) {
/* 166 */       ProviderConfig providerConfig; String str = Security.getProperty("security.provider." + b);
/* 167 */       if (str == null) {
/*     */         break;
/*     */       }
/* 170 */       str = str.trim();
/* 171 */       if (str.length() == 0) {
/* 172 */         System.err.println("invalid entry for security.provider." + b);
/*     */         
/*     */         break;
/*     */       } 
/* 176 */       int i = str.indexOf(' ');
/*     */       
/* 178 */       if (i == -1) {
/* 179 */         providerConfig = new ProviderConfig(str);
/*     */       } else {
/* 181 */         String str1 = str.substring(0, i);
/* 182 */         String str2 = str.substring(i + 1).trim();
/* 183 */         providerConfig = new ProviderConfig(str1, str2);
/*     */       } 
/*     */ 
/*     */       
/* 187 */       if (!arrayList.contains(providerConfig)) {
/* 188 */         arrayList.add(providerConfig);
/*     */       }
/*     */     } 
/* 191 */     this.configs = arrayList.<ProviderConfig>toArray(PC0);
/* 192 */     if (debug != null) {
/* 193 */       debug.println("provider configuration: " + arrayList);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ProviderList getJarList(String[] paramArrayOfString) {
/* 204 */     ArrayList<ProviderConfig> arrayList = new ArrayList();
/* 205 */     for (String str : paramArrayOfString) {
/* 206 */       ProviderConfig providerConfig = new ProviderConfig(str);
/* 207 */       for (ProviderConfig providerConfig1 : this.configs) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 213 */         if (providerConfig1.equals(providerConfig)) {
/* 214 */           providerConfig = providerConfig1;
/*     */           break;
/*     */         } 
/*     */       } 
/* 218 */       arrayList.add(providerConfig);
/*     */     } 
/* 220 */     ProviderConfig[] arrayOfProviderConfig = arrayList.<ProviderConfig>toArray(PC0);
/* 221 */     return new ProviderList(arrayOfProviderConfig, false);
/*     */   }
/*     */   
/*     */   public int size() {
/* 225 */     return this.configs.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Provider getProvider(int paramInt) {
/* 233 */     Provider provider = this.configs[paramInt].getProvider();
/* 234 */     return (provider != null) ? provider : EMPTY_PROVIDER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Provider> providers() {
/* 243 */     return this.userList;
/*     */   }
/*     */   
/*     */   private ProviderConfig getProviderConfig(String paramString) {
/* 247 */     int i = getIndex(paramString);
/* 248 */     return (i != -1) ? this.configs[i] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Provider getProvider(String paramString) {
/* 253 */     ProviderConfig providerConfig = getProviderConfig(paramString);
/* 254 */     return (providerConfig == null) ? null : providerConfig.getProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(String paramString) {
/* 262 */     for (byte b = 0; b < this.configs.length; b++) {
/* 263 */       Provider provider = getProvider(b);
/* 264 */       if (provider.getName().equals(paramString)) {
/* 265 */         return b;
/*     */       }
/*     */     } 
/* 268 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int loadAll() {
/* 273 */     if (this.allLoaded) {
/* 274 */       return this.configs.length;
/*     */     }
/* 276 */     if (debug != null) {
/* 277 */       debug.println("Loading all providers");
/* 278 */       (new Exception("Debug Info. Call trace:")).printStackTrace();
/*     */     } 
/* 280 */     byte b1 = 0;
/* 281 */     for (byte b2 = 0; b2 < this.configs.length; b2++) {
/* 282 */       Provider provider = this.configs[b2].getProvider();
/* 283 */       if (provider != null) {
/* 284 */         b1++;
/*     */       }
/*     */     } 
/* 287 */     if (b1 == this.configs.length) {
/* 288 */       this.allLoaded = true;
/*     */     }
/* 290 */     return b1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ProviderList removeInvalid() {
/* 299 */     int i = loadAll();
/* 300 */     if (i == this.configs.length) {
/* 301 */       return this;
/*     */     }
/* 303 */     ProviderConfig[] arrayOfProviderConfig = new ProviderConfig[i];
/* 304 */     for (byte b1 = 0, b2 = 0; b1 < this.configs.length; b1++) {
/* 305 */       ProviderConfig providerConfig = this.configs[b1];
/* 306 */       if (providerConfig.isLoaded()) {
/* 307 */         arrayOfProviderConfig[b2++] = providerConfig;
/*     */       }
/*     */     } 
/* 310 */     return new ProviderList(arrayOfProviderConfig, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Provider[] toArray() {
/* 315 */     return providers().<Provider>toArray(P0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 320 */     return Arrays.<ProviderConfig>asList(this.configs).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Provider.Service getService(String paramString1, String paramString2) {
/* 330 */     for (byte b = 0; b < this.configs.length; b++) {
/* 331 */       Provider provider = getProvider(b);
/* 332 */       Provider.Service service = provider.getService(paramString1, paramString2);
/* 333 */       if (service != null) {
/* 334 */         return service;
/*     */       }
/*     */     } 
/* 337 */     return null;
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
/*     */   public List<Provider.Service> getServices(String paramString1, String paramString2) {
/* 350 */     return new ServiceList(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<Provider.Service> getServices(String paramString, List<String> paramList) {
/* 360 */     ArrayList<ServiceId> arrayList = new ArrayList();
/* 361 */     for (String str : paramList) {
/* 362 */       arrayList.add(new ServiceId(paramString, str));
/*     */     }
/* 364 */     return getServices(arrayList);
/*     */   }
/*     */   
/*     */   public List<Provider.Service> getServices(List<ServiceId> paramList) {
/* 368 */     return new ServiceList(paramList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class ServiceList
/*     */     extends AbstractList<Provider.Service>
/*     */   {
/*     */     private final String type;
/*     */ 
/*     */ 
/*     */     
/*     */     private final String algorithm;
/*     */ 
/*     */ 
/*     */     
/*     */     private final List<ServiceId> ids;
/*     */ 
/*     */ 
/*     */     
/*     */     private Provider.Service firstService;
/*     */ 
/*     */     
/*     */     private List<Provider.Service> services;
/*     */ 
/*     */     
/*     */     private int providerIndex;
/*     */ 
/*     */ 
/*     */     
/*     */     ServiceList(String param1String1, String param1String2) {
/* 400 */       this.type = param1String1;
/* 401 */       this.algorithm = param1String2;
/* 402 */       this.ids = null;
/*     */     }
/*     */     
/*     */     ServiceList(List<ServiceId> param1List) {
/* 406 */       this.type = null;
/* 407 */       this.algorithm = null;
/* 408 */       this.ids = param1List;
/*     */     }
/*     */     
/*     */     private void addService(Provider.Service param1Service) {
/* 412 */       if (this.firstService == null) {
/* 413 */         this.firstService = param1Service;
/*     */       } else {
/* 415 */         if (this.services == null) {
/* 416 */           this.services = new ArrayList<>(4);
/* 417 */           this.services.add(this.firstService);
/*     */         } 
/* 419 */         this.services.add(param1Service);
/*     */       } 
/*     */     }
/*     */     
/*     */     private Provider.Service tryGet(int param1Int) {
/*     */       while (true) {
/* 425 */         if (param1Int == 0 && this.firstService != null)
/* 426 */           return this.firstService; 
/* 427 */         if (this.services != null && this.services.size() > param1Int) {
/* 428 */           return this.services.get(param1Int);
/*     */         }
/* 430 */         if (this.providerIndex >= ProviderList.this.configs.length) {
/* 431 */           return null;
/*     */         }
/*     */         
/* 434 */         Provider provider = ProviderList.this.getProvider(this.providerIndex++);
/* 435 */         if (this.type != null) {
/*     */           
/* 437 */           Provider.Service service = provider.getService(this.type, this.algorithm);
/* 438 */           if (service != null) {
/* 439 */             addService(service);
/*     */           }
/*     */           continue;
/*     */         } 
/* 443 */         for (ServiceId serviceId : this.ids) {
/* 444 */           Provider.Service service = provider.getService(serviceId.type, serviceId.algorithm);
/* 445 */           if (service != null) {
/* 446 */             addService(service);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Provider.Service get(int param1Int) {
/* 454 */       Provider.Service service = tryGet(param1Int);
/* 455 */       if (service == null) {
/* 456 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 458 */       return service;
/*     */     }
/*     */     
/*     */     public int size() {
/*     */       byte b;
/* 463 */       if (this.services != null) {
/* 464 */         b = this.services.size();
/*     */       } else {
/* 466 */         b = (this.firstService != null) ? 1 : 0;
/*     */       } 
/* 468 */       while (tryGet(b) != null) {
/* 469 */         b++;
/*     */       }
/* 471 */       return b;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 478 */       return (tryGet(0) == null);
/*     */     }
/*     */     
/*     */     public Iterator<Provider.Service> iterator() {
/* 482 */       return new Iterator<Provider.Service>() {
/*     */           int index;
/*     */           
/*     */           public boolean hasNext() {
/* 486 */             return (ProviderList.ServiceList.this.tryGet(this.index) != null);
/*     */           }
/*     */           
/*     */           public Provider.Service next() {
/* 490 */             Provider.Service service = ProviderList.ServiceList.this.tryGet(this.index);
/* 491 */             if (service == null) {
/* 492 */               throw new NoSuchElementException();
/*     */             }
/* 494 */             this.index++;
/* 495 */             return service;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 499 */             throw new UnsupportedOperationException();
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jca/ProviderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */