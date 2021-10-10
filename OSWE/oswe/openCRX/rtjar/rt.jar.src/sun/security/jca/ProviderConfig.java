/*     */ package sun.security.jca;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.security.AccessController;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.security.ProviderException;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.PropertyExpander;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ProviderConfig
/*     */ {
/*  46 */   private static final Debug debug = Debug.getInstance("jca", "ProviderConfig");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String P11_SOL_NAME = "sun.security.pkcs11.SunPKCS11";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String P11_SOL_ARG = "${java.home}/lib/security/sunpkcs11-solaris.cfg";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_LOAD_TRIES = 30;
/*     */ 
/*     */   
/*  61 */   private static final Class[] CL_STRING = new Class[] { String.class };
/*     */ 
/*     */   
/*     */   private final String className;
/*     */ 
/*     */   
/*     */   private final String argument;
/*     */ 
/*     */   
/*     */   private int tries;
/*     */ 
/*     */   
/*     */   private volatile Provider provider;
/*     */ 
/*     */   
/*     */   private boolean isLoading;
/*     */ 
/*     */ 
/*     */   
/*     */   ProviderConfig(String paramString1, String paramString2) {
/*  81 */     if (paramString1.equals("sun.security.pkcs11.SunPKCS11") && paramString2.equals("${java.home}/lib/security/sunpkcs11-solaris.cfg")) {
/*  82 */       checkSunPKCS11Solaris();
/*     */     }
/*  84 */     this.className = paramString1;
/*  85 */     this.argument = expand(paramString2);
/*     */   }
/*     */   
/*     */   ProviderConfig(String paramString) {
/*  89 */     this(paramString, "");
/*     */   }
/*     */   
/*     */   ProviderConfig(Provider paramProvider) {
/*  93 */     this.className = paramProvider.getClass().getName();
/*  94 */     this.argument = "";
/*  95 */     this.provider = paramProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkSunPKCS11Solaris() {
/* 102 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/* 105 */             File file = new File("/usr/lib/libpkcs11.so");
/* 106 */             if (!file.exists()) {
/* 107 */               return Boolean.FALSE;
/*     */             }
/* 109 */             if ("false".equalsIgnoreCase(
/* 110 */                 System.getProperty("sun.security.pkcs11.enable-solaris"))) {
/* 111 */               return Boolean.FALSE;
/*     */             }
/* 113 */             return Boolean.TRUE;
/*     */           }
/*     */         });
/* 116 */     if (bool == Boolean.FALSE) {
/* 117 */       this.tries = 30;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean hasArgument() {
/* 122 */     return (this.argument.length() != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldLoad() {
/* 127 */     return (this.tries < 30);
/*     */   }
/*     */ 
/*     */   
/*     */   private void disableLoad() {
/* 132 */     this.tries = 30;
/*     */   }
/*     */   
/*     */   boolean isLoaded() {
/* 136 */     return (this.provider != null);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 140 */     if (this == paramObject) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (!(paramObject instanceof ProviderConfig)) {
/* 144 */       return false;
/*     */     }
/* 146 */     ProviderConfig providerConfig = (ProviderConfig)paramObject;
/* 147 */     return (this.className.equals(providerConfig.className) && this.argument
/* 148 */       .equals(providerConfig.argument));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 152 */     return this.className.hashCode() + this.argument.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 156 */     if (hasArgument()) {
/* 157 */       return this.className + "('" + this.argument + "')";
/*     */     }
/* 159 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized Provider getProvider() {
/* 168 */     Provider provider = this.provider;
/* 169 */     if (provider != null) {
/* 170 */       return provider;
/*     */     }
/* 172 */     if (!shouldLoad()) {
/* 173 */       return null;
/*     */     }
/* 175 */     if (this.isLoading) {
/*     */ 
/*     */       
/* 178 */       if (debug != null) {
/* 179 */         debug.println("Recursion loading provider: " + this);
/* 180 */         (new Exception("Call trace")).printStackTrace();
/*     */       } 
/* 182 */       return null;
/*     */     } 
/*     */     try {
/* 185 */       this.isLoading = true;
/* 186 */       this.tries++;
/* 187 */       provider = doLoadProvider();
/*     */     } finally {
/* 189 */       this.isLoading = false;
/*     */     } 
/* 191 */     this.provider = provider;
/* 192 */     return provider;
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
/*     */   private Provider doLoadProvider() {
/* 206 */     return AccessController.<Provider>doPrivileged(new PrivilegedAction<Provider>() {
/*     */           public Provider run() {
/* 208 */             if (ProviderConfig.debug != null)
/* 209 */               ProviderConfig.debug.println("Loading provider: " + ProviderConfig.this);  try {
/*     */               Class<?> clazz;
/*     */               Object object;
/* 212 */               ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/*     */               
/* 214 */               if (classLoader != null) {
/* 215 */                 clazz = classLoader.loadClass(ProviderConfig.this.className);
/*     */               } else {
/* 217 */                 clazz = Class.forName(ProviderConfig.this.className);
/*     */               } 
/*     */               
/* 220 */               if (!ProviderConfig.this.hasArgument()) {
/* 221 */                 object = clazz.newInstance();
/*     */               } else {
/* 223 */                 Constructor<?> constructor = clazz.getConstructor(ProviderConfig.CL_STRING);
/* 224 */                 object = constructor.newInstance(new Object[] { ProviderConfig.access$400(this.this$0) });
/*     */               } 
/* 226 */               if (object instanceof Provider) {
/* 227 */                 if (ProviderConfig.debug != null) {
/* 228 */                   ProviderConfig.debug.println("Loaded provider " + object);
/*     */                 }
/* 230 */                 return (Provider)object;
/*     */               } 
/* 232 */               if (ProviderConfig.debug != null) {
/* 233 */                 ProviderConfig.debug.println(ProviderConfig.this.className + " is not a provider");
/*     */               }
/* 235 */               ProviderConfig.this.disableLoad();
/* 236 */               return null;
/*     */             }
/* 238 */             catch (Exception exception) {
/*     */               Throwable throwable;
/* 240 */               if (exception instanceof InvocationTargetException) {
/* 241 */                 throwable = ((InvocationTargetException)exception).getCause();
/*     */               } else {
/* 243 */                 throwable = exception;
/*     */               } 
/* 245 */               if (ProviderConfig.debug != null) {
/* 246 */                 ProviderConfig.debug.println("Error loading provider " + ProviderConfig.this);
/* 247 */                 throwable.printStackTrace();
/*     */               } 
/*     */               
/* 250 */               if (throwable instanceof ProviderException) {
/* 251 */                 throw (ProviderException)throwable;
/*     */               }
/*     */               
/* 254 */               if (throwable instanceof UnsupportedOperationException) {
/* 255 */                 ProviderConfig.this.disableLoad();
/*     */               }
/* 257 */               return null;
/* 258 */             } catch (ExceptionInInitializerError exceptionInInitializerError) {
/*     */ 
/*     */               
/* 261 */               if (ProviderConfig.debug != null) {
/* 262 */                 ProviderConfig.debug.println("Error loading provider " + ProviderConfig.this);
/* 263 */                 exceptionInInitializerError.printStackTrace();
/*     */               } 
/* 265 */               ProviderConfig.this.disableLoad();
/* 266 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String expand(final String value) {
/* 279 */     if (!value.contains("${")) {
/* 280 */       return value;
/*     */     }
/* 282 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/*     */             try {
/* 285 */               return PropertyExpander.expand(value);
/* 286 */             } catch (GeneralSecurityException generalSecurityException) {
/* 287 */               throw new ProviderException(generalSecurityException);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jca/ProviderConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */