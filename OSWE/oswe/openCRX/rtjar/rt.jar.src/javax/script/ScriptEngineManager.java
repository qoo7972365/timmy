/*     */ package javax.script;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptEngineManager
/*     */ {
/*     */   private static final boolean DEBUG = false;
/*     */   private HashSet<ScriptEngineFactory> engineSpis;
/*     */   private HashMap<String, ScriptEngineFactory> nameAssociations;
/*     */   private HashMap<String, ScriptEngineFactory> extensionAssociations;
/*     */   private HashMap<String, ScriptEngineFactory> mimeTypeAssociations;
/*     */   private Bindings globalScope;
/*     */   
/*     */   public ScriptEngineManager() {
/*  60 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  61 */     init(classLoader);
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
/*     */   public ScriptEngineManager(ClassLoader paramClassLoader) {
/*  75 */     init(paramClassLoader);
/*     */   }
/*     */   
/*     */   private void init(ClassLoader paramClassLoader) {
/*  79 */     this.globalScope = new SimpleBindings();
/*  80 */     this.engineSpis = new HashSet<>();
/*  81 */     this.nameAssociations = new HashMap<>();
/*  82 */     this.extensionAssociations = new HashMap<>();
/*  83 */     this.mimeTypeAssociations = new HashMap<>();
/*  84 */     initEngines(paramClassLoader);
/*     */   }
/*     */   
/*     */   private ServiceLoader<ScriptEngineFactory> getServiceLoader(ClassLoader paramClassLoader) {
/*  88 */     if (paramClassLoader != null) {
/*  89 */       return ServiceLoader.load(ScriptEngineFactory.class, paramClassLoader);
/*     */     }
/*  91 */     return ServiceLoader.loadInstalled(ScriptEngineFactory.class);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initEngines(final ClassLoader loader) {
/*  96 */     Iterator<ScriptEngineFactory> iterator = null;
/*     */     try {
/*  98 */       ServiceLoader serviceLoader = AccessController.<ServiceLoader>doPrivileged((PrivilegedAction)new PrivilegedAction<ServiceLoader<ScriptEngineFactory>>()
/*     */           {
/*     */             public ServiceLoader<ScriptEngineFactory> run()
/*     */             {
/* 102 */               return ScriptEngineManager.this.getServiceLoader(loader);
/*     */             }
/*     */           });
/*     */       
/* 106 */       iterator = serviceLoader.iterator();
/* 107 */     } catch (ServiceConfigurationError serviceConfigurationError) {
/* 108 */       System.err.println("Can't find ScriptEngineFactory providers: " + serviceConfigurationError
/* 109 */           .getMessage());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 120 */       while (iterator.hasNext()) {
/*     */         try {
/* 122 */           ScriptEngineFactory scriptEngineFactory = iterator.next();
/* 123 */           this.engineSpis.add(scriptEngineFactory);
/* 124 */         } catch (ServiceConfigurationError serviceConfigurationError) {
/* 125 */           System.err.println("ScriptEngineManager providers.next(): " + serviceConfigurationError
/* 126 */               .getMessage());
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 134 */     catch (ServiceConfigurationError serviceConfigurationError) {
/* 135 */       System.err.println("ScriptEngineManager providers.hasNext(): " + serviceConfigurationError
/* 136 */           .getMessage());
/*     */       return;
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
/*     */   public void setBindings(Bindings paramBindings) {
/* 157 */     if (paramBindings == null) {
/* 158 */       throw new IllegalArgumentException("Global scope cannot be null.");
/*     */     }
/*     */     
/* 161 */     this.globalScope = paramBindings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bindings getBindings() {
/* 172 */     return this.globalScope;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String paramString, Object paramObject) {
/* 183 */     this.globalScope.put(paramString, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String paramString) {
/* 192 */     return this.globalScope.get(paramString);
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
/*     */   public ScriptEngine getEngineByName(String paramString) {
/* 212 */     if (paramString == null) throw new NullPointerException();
/*     */     
/*     */     ScriptEngineFactory scriptEngineFactory;
/* 215 */     if (null != (scriptEngineFactory = (ScriptEngineFactory)this.nameAssociations.get(paramString))) {
/* 216 */       ScriptEngineFactory scriptEngineFactory1 = scriptEngineFactory;
/*     */       try {
/* 218 */         ScriptEngine scriptEngine = scriptEngineFactory1.getScriptEngine();
/* 219 */         scriptEngine.setBindings(getBindings(), 200);
/* 220 */         return scriptEngine;
/* 221 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 226 */     for (ScriptEngineFactory scriptEngineFactory1 : this.engineSpis) {
/* 227 */       List<String> list = null;
/*     */       try {
/* 229 */         list = scriptEngineFactory1.getNames();
/* 230 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/* 234 */       if (list != null) {
/* 235 */         for (String str : list) {
/* 236 */           if (paramString.equals(str)) {
/*     */             try {
/* 238 */               ScriptEngine scriptEngine = scriptEngineFactory1.getScriptEngine();
/* 239 */               scriptEngine.setBindings(getBindings(), 200);
/* 240 */               return scriptEngine;
/* 241 */             } catch (Exception exception) {}
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 249 */     return null;
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
/*     */   public ScriptEngine getEngineByExtension(String paramString) {
/* 263 */     if (paramString == null) throw new NullPointerException();
/*     */     
/*     */     ScriptEngineFactory scriptEngineFactory;
/* 266 */     if (null != (scriptEngineFactory = (ScriptEngineFactory)this.extensionAssociations.get(paramString))) {
/* 267 */       ScriptEngineFactory scriptEngineFactory1 = scriptEngineFactory;
/*     */       try {
/* 269 */         ScriptEngine scriptEngine = scriptEngineFactory1.getScriptEngine();
/* 270 */         scriptEngine.setBindings(getBindings(), 200);
/* 271 */         return scriptEngine;
/* 272 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 277 */     for (ScriptEngineFactory scriptEngineFactory1 : this.engineSpis) {
/* 278 */       List<String> list = null;
/*     */       try {
/* 280 */         list = scriptEngineFactory1.getExtensions();
/* 281 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 284 */       if (list == null)
/* 285 */         continue;  for (String str : list) {
/* 286 */         if (paramString.equals(str)) {
/*     */           try {
/* 288 */             ScriptEngine scriptEngine = scriptEngineFactory1.getScriptEngine();
/* 289 */             scriptEngine.setBindings(getBindings(), 200);
/* 290 */             return scriptEngine;
/* 291 */           } catch (Exception exception) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 297 */     return null;
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
/*     */   public ScriptEngine getEngineByMimeType(String paramString) {
/* 311 */     if (paramString == null) throw new NullPointerException();
/*     */     
/*     */     ScriptEngineFactory scriptEngineFactory;
/* 314 */     if (null != (scriptEngineFactory = (ScriptEngineFactory)this.mimeTypeAssociations.get(paramString))) {
/* 315 */       ScriptEngineFactory scriptEngineFactory1 = scriptEngineFactory;
/*     */       try {
/* 317 */         ScriptEngine scriptEngine = scriptEngineFactory1.getScriptEngine();
/* 318 */         scriptEngine.setBindings(getBindings(), 200);
/* 319 */         return scriptEngine;
/* 320 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 325 */     for (ScriptEngineFactory scriptEngineFactory1 : this.engineSpis) {
/* 326 */       List<String> list = null;
/*     */       try {
/* 328 */         list = scriptEngineFactory1.getMimeTypes();
/* 329 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 332 */       if (list == null)
/* 333 */         continue;  for (String str : list) {
/* 334 */         if (paramString.equals(str)) {
/*     */           try {
/* 336 */             ScriptEngine scriptEngine = scriptEngineFactory1.getScriptEngine();
/* 337 */             scriptEngine.setBindings(getBindings(), 200);
/* 338 */             return scriptEngine;
/* 339 */           } catch (Exception exception) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 345 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ScriptEngineFactory> getEngineFactories() {
/* 354 */     ArrayList<ScriptEngineFactory> arrayList = new ArrayList(this.engineSpis.size());
/* 355 */     for (ScriptEngineFactory scriptEngineFactory : this.engineSpis) {
/* 356 */       arrayList.add(scriptEngineFactory);
/*     */     }
/* 358 */     return Collections.unmodifiableList(arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEngineName(String paramString, ScriptEngineFactory paramScriptEngineFactory) {
/* 369 */     if (paramString == null || paramScriptEngineFactory == null) throw new NullPointerException(); 
/* 370 */     this.nameAssociations.put(paramString, paramScriptEngineFactory);
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
/*     */   public void registerEngineMimeType(String paramString, ScriptEngineFactory paramScriptEngineFactory) {
/* 384 */     if (paramString == null || paramScriptEngineFactory == null) throw new NullPointerException(); 
/* 385 */     this.mimeTypeAssociations.put(paramString, paramScriptEngineFactory);
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
/*     */   public void registerEngineExtension(String paramString, ScriptEngineFactory paramScriptEngineFactory) {
/* 398 */     if (paramString == null || paramScriptEngineFactory == null) throw new NullPointerException(); 
/* 399 */     this.extensionAssociations.put(paramString, paramScriptEngineFactory);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/script/ScriptEngineManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */