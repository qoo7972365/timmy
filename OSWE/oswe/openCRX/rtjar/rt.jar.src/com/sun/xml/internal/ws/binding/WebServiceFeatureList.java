/*     */ package com.sun.xml.internal.ws.binding;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.EnvelopeStyleFeature;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.bind.util.Which;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.FeatureConstructor;
/*     */ import com.sun.xml.internal.ws.api.FeatureListValidator;
/*     */ import com.sun.xml.internal.ws.api.FeatureListValidatorAnnotation;
/*     */ import com.sun.xml.internal.ws.api.ImpliesWebServiceFeature;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFeaturedObject;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.model.RuntimeModelerException;
/*     */ import com.sun.xml.internal.ws.resources.ModelerMessages;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.RespectBinding;
/*     */ import javax.xml.ws.RespectBindingFeature;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.Addressing;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ import javax.xml.ws.soap.MTOM;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ import javax.xml.ws.spi.WebServiceFeatureAnnotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WebServiceFeatureList
/*     */   extends AbstractMap<Class<? extends WebServiceFeature>, WebServiceFeature>
/*     */   implements WSFeatureList
/*     */ {
/*     */   public static WebServiceFeatureList toList(Iterable<WebServiceFeature> features) {
/*  71 */     if (features instanceof WebServiceFeatureList)
/*  72 */       return (WebServiceFeatureList)features; 
/*  73 */     WebServiceFeatureList w = new WebServiceFeatureList();
/*  74 */     if (features != null)
/*  75 */       w.addAll(features); 
/*  76 */     return w;
/*     */   }
/*     */   
/*  79 */   private Map<Class<? extends WebServiceFeature>, WebServiceFeature> wsfeatures = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValidating = false;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private WSDLFeaturedObject parent;
/*     */ 
/*     */ 
/*     */   
/*     */   public WebServiceFeatureList(@NotNull WebServiceFeature... features) {
/*  92 */     if (features != null) {
/*  93 */       for (WebServiceFeature f : features) {
/*  94 */         addNoValidate(f);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void validate() {
/* 100 */     if (!this.isValidating) {
/* 101 */       this.isValidating = true;
/*     */ 
/*     */       
/* 104 */       for (WebServiceFeature ff : this) {
/* 105 */         validate(ff);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void validate(WebServiceFeature feature) {
/* 112 */     FeatureListValidatorAnnotation fva = feature.getClass().<FeatureListValidatorAnnotation>getAnnotation(FeatureListValidatorAnnotation.class);
/* 113 */     if (fva != null) {
/* 114 */       Class<? extends FeatureListValidator> beanClass = fva.bean();
/*     */       try {
/* 116 */         FeatureListValidator validator = beanClass.newInstance();
/* 117 */         validator.validate(this);
/* 118 */       } catch (InstantiationException e) {
/* 119 */         throw new WebServiceException(e);
/* 120 */       } catch (IllegalAccessException e) {
/* 121 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public WebServiceFeatureList(WebServiceFeatureList features) {
/* 127 */     if (features != null) {
/* 128 */       this.wsfeatures.putAll(features.wsfeatures);
/* 129 */       this.parent = features.parent;
/* 130 */       this.isValidating = features.isValidating;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebServiceFeatureList(@NotNull Class<?> endpointClass) {
/* 138 */     parseAnnotations(endpointClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseAnnotations(Iterable<Annotation> annIt) {
/* 148 */     for (Annotation ann : annIt) {
/* 149 */       WebServiceFeature feature = getFeature(ann);
/* 150 */       if (feature != null) {
/* 151 */         add(feature);
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
/*     */   public static WebServiceFeature getFeature(Annotation a) {
/* 164 */     WebServiceFeature ftr = null;
/* 165 */     if (!a.annotationType().isAnnotationPresent((Class)WebServiceFeatureAnnotation.class)) {
/* 166 */       ftr = null;
/* 167 */     } else if (a instanceof Addressing) {
/* 168 */       Addressing addAnn = (Addressing)a;
/*     */       try {
/* 170 */         AddressingFeature addressingFeature = new AddressingFeature(addAnn.enabled(), addAnn.required(), addAnn.responses());
/* 171 */       } catch (NoSuchMethodError e) {
/*     */         
/* 173 */         throw new RuntimeModelerException(ModelerMessages.RUNTIME_MODELER_ADDRESSING_RESPONSES_NOSUCHMETHOD(toJar(Which.which(Addressing.class))), new Object[0]);
/*     */       } 
/* 175 */     } else if (a instanceof MTOM) {
/* 176 */       MTOM mtomAnn = (MTOM)a;
/* 177 */       MTOMFeature mTOMFeature = new MTOMFeature(mtomAnn.enabled(), mtomAnn.threshold());
/* 178 */     } else if (a instanceof RespectBinding) {
/* 179 */       RespectBinding rbAnn = (RespectBinding)a;
/* 180 */       RespectBindingFeature respectBindingFeature = new RespectBindingFeature(rbAnn.enabled());
/*     */     } else {
/* 182 */       ftr = getWebServiceFeatureBean(a);
/*     */     } 
/* 184 */     return ftr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseAnnotations(Class<?> endpointClass) {
/* 192 */     for (Annotation a : endpointClass.getAnnotations()) {
/* 193 */       WebServiceFeature ftr = getFeature(a);
/* 194 */       if (ftr != null) {
/* 195 */         if (ftr instanceof MTOMFeature) {
/*     */           
/* 197 */           BindingID bindingID = BindingID.parse(endpointClass);
/* 198 */           MTOMFeature bindingMtomSetting = bindingID.createBuiltinFeatureList().<MTOMFeature>get(MTOMFeature.class);
/* 199 */           if (bindingMtomSetting != null && (bindingMtomSetting.isEnabled() ^ ftr.isEnabled()) != 0) {
/* 200 */             throw new RuntimeModelerException(
/* 201 */                 ModelerMessages.RUNTIME_MODELER_MTOM_CONFLICT(bindingID, Boolean.valueOf(ftr.isEnabled())), new Object[0]);
/*     */           }
/*     */         } 
/* 204 */         add(ftr);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toJar(String url) {
/* 213 */     if (!url.startsWith("jar:"))
/* 214 */       return url; 
/* 215 */     url = url.substring(4);
/* 216 */     return url.substring(0, url.lastIndexOf('!'));
/*     */   }
/*     */   private static WebServiceFeature getWebServiceFeatureBean(Annotation a) {
/*     */     WebServiceFeature bean;
/* 220 */     WebServiceFeatureAnnotation wsfa = a.annotationType().<WebServiceFeatureAnnotation>getAnnotation(WebServiceFeatureAnnotation.class);
/* 221 */     Class<? extends WebServiceFeature> beanClass = wsfa.bean();
/*     */ 
/*     */     
/* 224 */     Constructor<FeatureConstructor> ftrCtr = null;
/* 225 */     String[] paramNames = null;
/* 226 */     for (Constructor<FeatureConstructor> con : beanClass.getConstructors()) {
/* 227 */       FeatureConstructor ftrCtrAnn = con.<FeatureConstructor>getAnnotation(FeatureConstructor.class);
/* 228 */       if (ftrCtrAnn != null) {
/* 229 */         if (ftrCtr == null) {
/* 230 */           ftrCtr = con;
/* 231 */           paramNames = ftrCtrAnn.value();
/*     */         } else {
/* 233 */           throw new WebServiceException(
/* 234 */               ModelerMessages.RUNTIME_MODELER_WSFEATURE_MORETHANONE_FTRCONSTRUCTOR(a, beanClass));
/*     */         } 
/*     */       }
/*     */     } 
/* 238 */     if (ftrCtr == null) {
/* 239 */       bean = getWebServiceFeatureBeanViaBuilder(a, beanClass);
/* 240 */       if (bean != null) {
/* 241 */         return bean;
/*     */       }
/* 243 */       throw new WebServiceException(
/* 244 */           ModelerMessages.RUNTIME_MODELER_WSFEATURE_NO_FTRCONSTRUCTOR(a, beanClass));
/*     */     } 
/*     */     
/* 247 */     if ((ftrCtr.getParameterTypes()).length != paramNames.length) {
/* 248 */       throw new WebServiceException(
/* 249 */           ModelerMessages.RUNTIME_MODELER_WSFEATURE_ILLEGAL_FTRCONSTRUCTOR(a, beanClass));
/*     */     }
/*     */     
/*     */     try {
/* 253 */       Object[] params = new Object[paramNames.length];
/* 254 */       for (int i = 0; i < paramNames.length; i++) {
/* 255 */         Method m = a.annotationType().getDeclaredMethod(paramNames[i], new Class[0]);
/* 256 */         params[i] = m.invoke(a, new Object[0]);
/*     */       } 
/* 258 */       bean = (WebServiceFeature)ftrCtr.newInstance(params);
/* 259 */     } catch (Exception e) {
/* 260 */       throw new WebServiceException(e);
/*     */     } 
/* 262 */     return bean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static WebServiceFeature getWebServiceFeatureBeanViaBuilder(Annotation annotation, Class<? extends WebServiceFeature> beanClass) {
/*     */     try {
/* 270 */       Method featureBuilderMethod = beanClass.getDeclaredMethod("builder", new Class[0]);
/* 271 */       Object builder = featureBuilderMethod.invoke(beanClass, new Object[0]);
/* 272 */       Method buildMethod = builder.getClass().getDeclaredMethod("build", new Class[0]);
/*     */       
/* 274 */       for (Method builderMethod : builder.getClass().getDeclaredMethods()) {
/* 275 */         if (!builderMethod.equals(buildMethod)) {
/* 276 */           String methodName = builderMethod.getName();
/* 277 */           Method annotationMethod = annotation.annotationType().getDeclaredMethod(methodName, new Class[0]);
/* 278 */           Object annotationFieldValue = annotationMethod.invoke(annotation, new Object[0]);
/* 279 */           Object[] arg = { annotationFieldValue };
/* 280 */           if (!skipDuringOrgJvnetWsToComOracleWebservicesPackageMove(builderMethod, annotationFieldValue))
/*     */           {
/*     */             
/* 283 */             builderMethod.invoke(builder, arg);
/*     */           }
/*     */         } 
/*     */       } 
/* 287 */       Object result = buildMethod.invoke(builder, new Object[0]);
/* 288 */       if (result instanceof WebServiceFeature) {
/* 289 */         return (WebServiceFeature)result;
/*     */       }
/* 291 */       throw new WebServiceException("Not a WebServiceFeature: " + result);
/*     */     }
/* 293 */     catch (NoSuchMethodException e) {
/* 294 */       return null;
/* 295 */     } catch (IllegalAccessException e) {
/* 296 */       throw new WebServiceException(e);
/* 297 */     } catch (InvocationTargetException e) {
/* 298 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean skipDuringOrgJvnetWsToComOracleWebservicesPackageMove(Method builderMethod, Object annotationFieldValue) {
/* 307 */     Class<?> annotationFieldValueClass = annotationFieldValue.getClass();
/* 308 */     if (!annotationFieldValueClass.isEnum()) {
/* 309 */       return false;
/*     */     }
/* 311 */     Class<?>[] builderMethodParameterTypes = builderMethod.getParameterTypes();
/* 312 */     if (builderMethodParameterTypes.length != 1) {
/* 313 */       throw new WebServiceException("expected only 1 parameter");
/*     */     }
/* 315 */     String builderParameterTypeName = builderMethodParameterTypes[0].getName();
/* 316 */     if (!builderParameterTypeName.startsWith("com.oracle.webservices.internal.test.features_annotations_enums.apinew") && 
/* 317 */       !builderParameterTypeName.startsWith("com.oracle.webservices.internal.api")) {
/* 318 */       return false;
/*     */     }
/* 320 */     return false;
/*     */   }
/*     */   
/*     */   public Iterator<WebServiceFeature> iterator() {
/* 324 */     if (this.parent != null)
/* 325 */       return new MergedFeatures(this.parent.getFeatures()); 
/* 326 */     return this.wsfeatures.values().iterator();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WebServiceFeature[] toArray() {
/* 331 */     if (this.parent != null)
/* 332 */       return (new MergedFeatures(this.parent.getFeatures())).toArray(); 
/* 333 */     return (WebServiceFeature[])this.wsfeatures.values().toArray((Object[])new WebServiceFeature[0]);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(@NotNull Class<? extends WebServiceFeature> feature) {
/* 337 */     WebServiceFeature ftr = get((Class)feature);
/* 338 */     return (ftr != null && ftr.isEnabled());
/*     */   }
/*     */   
/*     */   public boolean contains(@NotNull Class<? extends WebServiceFeature> feature) {
/* 342 */     WebServiceFeature ftr = get((Class)feature);
/* 343 */     return (ftr != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <F extends WebServiceFeature> F get(@NotNull Class<F> featureType) {
/* 348 */     WebServiceFeature f = (WebServiceFeature)featureType.cast(this.wsfeatures.get(featureType));
/* 349 */     if (f == null && this.parent != null) {
/* 350 */       return (F)this.parent.getFeatures().get(featureType);
/*     */     }
/* 352 */     return (F)f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(@NotNull WebServiceFeature f) {
/* 359 */     if (addNoValidate(f) && this.isValidating)
/* 360 */       validate(f); 
/*     */   }
/*     */   
/*     */   private boolean addNoValidate(@NotNull WebServiceFeature f) {
/* 364 */     if (!this.wsfeatures.containsKey(f.getClass())) {
/* 365 */       this.wsfeatures.put(f.getClass(), f);
/*     */       
/* 367 */       if (f instanceof ImpliesWebServiceFeature) {
/* 368 */         ((ImpliesWebServiceFeature)f).implyFeatures(this);
/*     */       }
/* 370 */       return true;
/*     */     } 
/*     */     
/* 373 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(@NotNull Iterable<WebServiceFeature> list) {
/* 380 */     for (WebServiceFeature f : list) {
/* 381 */       add(f);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setMTOMEnabled(boolean b) {
/* 390 */     this.wsfeatures.put(MTOMFeature.class, new MTOMFeature(b));
/*     */   }
/*     */   
/*     */   public boolean equals(Object other) {
/* 394 */     if (!(other instanceof WebServiceFeatureList)) {
/* 395 */       return false;
/*     */     }
/* 397 */     WebServiceFeatureList w = (WebServiceFeatureList)other;
/* 398 */     return (this.wsfeatures.equals(w.wsfeatures) && this.parent == w.parent);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 402 */     return this.wsfeatures.toString();
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
/*     */   public void mergeFeatures(@NotNull Iterable<WebServiceFeature> features, boolean reportConflicts) {
/* 416 */     for (WebServiceFeature wsdlFtr : features) {
/* 417 */       if (get(wsdlFtr.getClass()) == null) {
/* 418 */         add(wsdlFtr); continue;
/* 419 */       }  if (reportConflicts && 
/* 420 */         isEnabled((Class)wsdlFtr.getClass()) != wsdlFtr.isEnabled()) {
/* 421 */         LOGGER.warning(ModelerMessages.RUNTIME_MODELER_FEATURE_CONFLICT(
/* 422 */               get(wsdlFtr.getClass()), wsdlFtr));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mergeFeatures(WebServiceFeature[] features, boolean reportConflicts) {
/* 429 */     for (WebServiceFeature wsdlFtr : features) {
/* 430 */       if (get(wsdlFtr.getClass()) == null) {
/* 431 */         add(wsdlFtr);
/* 432 */       } else if (reportConflicts && 
/* 433 */         isEnabled((Class)wsdlFtr.getClass()) != wsdlFtr.isEnabled()) {
/* 434 */         LOGGER.warning(ModelerMessages.RUNTIME_MODELER_FEATURE_CONFLICT(
/* 435 */               get(wsdlFtr.getClass()), wsdlFtr));
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
/*     */   public void mergeFeatures(@NotNull WSDLPort wsdlPort, boolean honorWsdlRequired, boolean reportConflicts) {
/* 459 */     if (honorWsdlRequired && !isEnabled((Class)RespectBindingFeature.class))
/*     */       return; 
/* 461 */     if (!honorWsdlRequired) {
/* 462 */       addAll((Iterable<WebServiceFeature>)wsdlPort.getFeatures());
/*     */       
/*     */       return;
/*     */     } 
/* 466 */     for (WebServiceFeature wsdlFtr : wsdlPort.getFeatures()) {
/* 467 */       if (get(wsdlFtr.getClass()) == null) {
/*     */ 
/*     */         
/*     */         try {
/* 471 */           Method m = wsdlFtr.getClass().getMethod("isRequired", new Class[0]);
/*     */           try {
/* 473 */             boolean required = ((Boolean)m.invoke(wsdlFtr, new Object[0])).booleanValue();
/* 474 */             if (required)
/* 475 */               add(wsdlFtr); 
/* 476 */           } catch (IllegalAccessException e) {
/* 477 */             throw new WebServiceException(e);
/* 478 */           } catch (InvocationTargetException e) {
/* 479 */             throw new WebServiceException(e);
/*     */           } 
/* 481 */         } catch (NoSuchMethodException e) {
/*     */           
/* 483 */           add(wsdlFtr);
/*     */         }  continue;
/* 485 */       }  if (reportConflicts && 
/* 486 */         isEnabled((Class)wsdlFtr.getClass()) != wsdlFtr.isEnabled()) {
/* 487 */         LOGGER.warning(ModelerMessages.RUNTIME_MODELER_FEATURE_CONFLICT(
/* 488 */               get(wsdlFtr.getClass()), wsdlFtr));
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
/*     */   public void setParentFeaturedObject(@NotNull WSDLFeaturedObject parent) {
/* 500 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <F extends WebServiceFeature> F getFeature(@NotNull WebServiceFeature[] features, @NotNull Class<F> featureType) {
/* 505 */     for (WebServiceFeature f : features) {
/* 506 */       if (f.getClass() == featureType)
/* 507 */         return (F)f; 
/*     */     } 
/* 509 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private final class MergedFeatures
/*     */     implements Iterator<WebServiceFeature>
/*     */   {
/* 516 */     private final Stack<WebServiceFeature> features = new Stack<>();
/*     */ 
/*     */     
/*     */     public MergedFeatures(WSFeatureList parent) {
/* 520 */       for (WebServiceFeature f : WebServiceFeatureList.this.wsfeatures.values()) {
/* 521 */         this.features.push(f);
/*     */       }
/*     */       
/* 524 */       for (WebServiceFeature f : parent) {
/* 525 */         if (!WebServiceFeatureList.this.wsfeatures.containsKey(f.getClass())) {
/* 526 */           this.features.push(f);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 532 */       return !this.features.empty();
/*     */     }
/*     */     
/*     */     public WebServiceFeature next() {
/* 536 */       if (!this.features.empty()) {
/* 537 */         return this.features.pop();
/*     */       }
/* 539 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 543 */       if (!this.features.empty()) {
/* 544 */         this.features.pop();
/*     */       }
/*     */     }
/*     */     
/*     */     public WebServiceFeature[] toArray() {
/* 549 */       return (WebServiceFeature[])this.features.toArray((Object[])new WebServiceFeature[0]);
/*     */     }
/*     */   }
/*     */   
/* 553 */   private static final Logger LOGGER = Logger.getLogger(WebServiceFeatureList.class.getName());
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<Class<? extends WebServiceFeature>, WebServiceFeature>> entrySet() {
/* 557 */     return this.wsfeatures.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public WebServiceFeature put(Class<? extends WebServiceFeature> key, WebServiceFeature value) {
/* 562 */     return this.wsfeatures.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SOAPVersion getSoapVersion(WSFeatureList features) {
/* 567 */     EnvelopeStyleFeature env = (EnvelopeStyleFeature)features.get(EnvelopeStyleFeature.class);
/* 568 */     if (env != null) {
/* 569 */       return SOAPVersion.from(env);
/*     */     }
/*     */     
/* 572 */     env = (EnvelopeStyleFeature)features.get(EnvelopeStyleFeature.class);
/* 573 */     return (env != null) ? SOAPVersion.from(env) : null;
/*     */   }
/*     */   
/*     */   public static boolean isFeatureEnabled(Class<? extends WebServiceFeature> type, WebServiceFeature[] features) {
/* 577 */     WebServiceFeature ftr = getFeature(features, (Class)type);
/* 578 */     return (ftr != null && ftr.isEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   public static WebServiceFeature[] toFeatureArray(WSBinding binding) {
/* 583 */     if (!binding.isFeatureEnabled(EnvelopeStyleFeature.class)) {
/* 584 */       WebServiceFeature[] f = { (WebServiceFeature)binding.getSOAPVersion().toFeature() };
/* 585 */       binding.getFeatures().mergeFeatures(f, false);
/*     */     } 
/* 587 */     return binding.getFeatures().toArray();
/*     */   }
/*     */   
/*     */   public WebServiceFeatureList() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/binding/WebServiceFeatureList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */