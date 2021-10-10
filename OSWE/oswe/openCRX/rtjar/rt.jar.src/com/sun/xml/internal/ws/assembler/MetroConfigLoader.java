/*     */ package com.sun.xml.internal.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.api.ResourceLoader;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.resources.TubelineassemblyMessages;
/*     */ import com.sun.xml.internal.ws.runtime.config.MetroConfig;
/*     */ import com.sun.xml.internal.ws.runtime.config.TubeFactoryList;
/*     */ import com.sun.xml.internal.ws.runtime.config.TubelineDefinition;
/*     */ import com.sun.xml.internal.ws.runtime.config.TubelineMapping;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ReflectPermission;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MetroConfigLoader
/*     */ {
/*  68 */   private static final Logger LOGGER = Logger.getLogger(MetroConfigLoader.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private MetroConfigName defaultTubesConfigNames;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final TubeFactoryListResolver ENDPOINT_SIDE_RESOLVER = new TubeFactoryListResolver()
/*     */     {
/*     */       public TubeFactoryList getFactories(TubelineDefinition td) {
/*  80 */         return (td != null) ? td.getEndpointSide() : null;
/*     */       }
/*     */     };
/*  83 */   private static final TubeFactoryListResolver CLIENT_SIDE_RESOLVER = new TubeFactoryListResolver()
/*     */     {
/*     */       public TubeFactoryList getFactories(TubelineDefinition td) {
/*  86 */         return (td != null) ? td.getClientSide() : null;
/*     */       }
/*     */     };
/*     */   
/*     */   private MetroConfig defaultConfig;
/*     */   private URL defaultConfigUrl;
/*     */   private MetroConfig appConfig;
/*     */   private URL appConfigUrl;
/*     */   
/*     */   MetroConfigLoader(Container container, MetroConfigName defaultTubesConfigNames) {
/*  96 */     this.defaultTubesConfigNames = defaultTubesConfigNames;
/*  97 */     ResourceLoader spiResourceLoader = null;
/*  98 */     if (container != null) {
/*  99 */       spiResourceLoader = (ResourceLoader)container.getSPI(ResourceLoader.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 104 */     init(container, new ResourceLoader[] { spiResourceLoader, new MetroConfigUrlLoader(container) });
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(Container container, ResourceLoader... loaders) {
/* 109 */     String appFileName = null;
/* 110 */     String defaultFileName = null;
/* 111 */     if (container != null) {
/* 112 */       MetroConfigName mcn = (MetroConfigName)container.getSPI(MetroConfigName.class);
/* 113 */       if (mcn != null) {
/* 114 */         appFileName = mcn.getAppFileName();
/* 115 */         defaultFileName = mcn.getDefaultFileName();
/*     */       } 
/*     */     } 
/* 118 */     if (appFileName == null) {
/* 119 */       appFileName = this.defaultTubesConfigNames.getAppFileName();
/*     */     }
/*     */     
/* 122 */     if (defaultFileName == null) {
/* 123 */       defaultFileName = this.defaultTubesConfigNames.getDefaultFileName();
/*     */     }
/* 125 */     this.defaultConfigUrl = locateResource(defaultFileName, loaders);
/* 126 */     if (this.defaultConfigUrl == null) {
/* 127 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(TubelineassemblyMessages.MASM_0001_DEFAULT_CFG_FILE_NOT_FOUND(defaultFileName)));
/*     */     }
/*     */     
/* 130 */     LOGGER.config(TubelineassemblyMessages.MASM_0002_DEFAULT_CFG_FILE_LOCATED(defaultFileName, this.defaultConfigUrl));
/* 131 */     this.defaultConfig = loadMetroConfig(this.defaultConfigUrl);
/* 132 */     if (this.defaultConfig == null) {
/* 133 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(TubelineassemblyMessages.MASM_0003_DEFAULT_CFG_FILE_NOT_LOADED(defaultFileName)));
/*     */     }
/* 135 */     if (this.defaultConfig.getTubelines() == null) {
/* 136 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(TubelineassemblyMessages.MASM_0004_NO_TUBELINES_SECTION_IN_DEFAULT_CFG_FILE(defaultFileName)));
/*     */     }
/* 138 */     if (this.defaultConfig.getTubelines().getDefault() == null) {
/* 139 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(TubelineassemblyMessages.MASM_0005_NO_DEFAULT_TUBELINE_IN_DEFAULT_CFG_FILE(defaultFileName)));
/*     */     }
/*     */     
/* 142 */     this.appConfigUrl = locateResource(appFileName, loaders);
/* 143 */     if (this.appConfigUrl != null) {
/* 144 */       LOGGER.config(TubelineassemblyMessages.MASM_0006_APP_CFG_FILE_LOCATED(this.appConfigUrl));
/* 145 */       this.appConfig = loadMetroConfig(this.appConfigUrl);
/*     */     } else {
/* 147 */       LOGGER.config(TubelineassemblyMessages.MASM_0007_APP_CFG_FILE_NOT_FOUND());
/* 148 */       this.appConfig = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   TubeFactoryList getEndpointSideTubeFactories(URI endpointReference) {
/* 153 */     return getTubeFactories(endpointReference, ENDPOINT_SIDE_RESOLVER);
/*     */   }
/*     */   
/*     */   TubeFactoryList getClientSideTubeFactories(URI endpointReference) {
/* 157 */     return getTubeFactories(endpointReference, CLIENT_SIDE_RESOLVER);
/*     */   }
/*     */   
/*     */   private TubeFactoryList getTubeFactories(URI endpointReference, TubeFactoryListResolver resolver) {
/* 161 */     if (this.appConfig != null && this.appConfig.getTubelines() != null) {
/* 162 */       for (TubelineMapping mapping : this.appConfig.getTubelines().getTubelineMappings()) {
/* 163 */         if (mapping.getEndpointRef().equals(endpointReference.toString())) {
/* 164 */           TubeFactoryList list = resolver.getFactories(getTubeline(this.appConfig, resolveReference(mapping.getTubelineRef())));
/* 165 */           if (list != null) {
/* 166 */             return list;
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 173 */       if (this.appConfig.getTubelines().getDefault() != null) {
/* 174 */         TubeFactoryList list = resolver.getFactories(getTubeline(this.appConfig, resolveReference(this.appConfig.getTubelines().getDefault())));
/* 175 */         if (list != null) {
/* 176 */           return list;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     for (TubelineMapping mapping : this.defaultConfig.getTubelines().getTubelineMappings()) {
/* 182 */       if (mapping.getEndpointRef().equals(endpointReference.toString())) {
/* 183 */         TubeFactoryList list = resolver.getFactories(getTubeline(this.defaultConfig, resolveReference(mapping.getTubelineRef())));
/* 184 */         if (list != null) {
/* 185 */           return list;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     return resolver.getFactories(getTubeline(this.defaultConfig, resolveReference(this.defaultConfig.getTubelines().getDefault())));
/*     */   }
/*     */   
/*     */   TubelineDefinition getTubeline(MetroConfig config, URI tubelineDefinitionUri) {
/* 196 */     if (config != null && config.getTubelines() != null) {
/* 197 */       for (TubelineDefinition td : config.getTubelines().getTubelineDefinitions()) {
/* 198 */         if (td.getName().equals(tubelineDefinitionUri.getFragment())) {
/* 199 */           return td;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 204 */     return null;
/*     */   }
/*     */   
/*     */   private static URI resolveReference(String reference) {
/*     */     try {
/* 209 */       return new URI(reference);
/* 210 */     } catch (URISyntaxException ex) {
/* 211 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(TubelineassemblyMessages.MASM_0008_INVALID_URI_REFERENCE(reference), ex));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static URL locateResource(String resource, ResourceLoader loader) {
/* 217 */     if (loader == null) return null;
/*     */     
/*     */     try {
/* 220 */       return loader.getResource(resource);
/* 221 */     } catch (MalformedURLException ex) {
/* 222 */       LOGGER.severe(TubelineassemblyMessages.MASM_0009_CANNOT_FORM_VALID_URL(resource), ex);
/*     */       
/* 224 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static URL locateResource(String resource, ResourceLoader[] loaders) {
/* 229 */     for (ResourceLoader loader : loaders) {
/* 230 */       URL url = locateResource(resource, loader);
/* 231 */       if (url != null) {
/* 232 */         return url;
/*     */       }
/*     */     } 
/* 235 */     return null;
/*     */   }
/*     */   
/*     */   private static MetroConfig loadMetroConfig(@NotNull URL resourceUrl) {
/* 239 */     MetroConfig result = null;
/*     */     try {
/* 241 */       JAXBContext jaxbContext = createJAXBContext();
/* 242 */       Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
/* 243 */       XMLInputFactory factory = XmlUtil.newXMLInputFactory(true);
/* 244 */       JAXBElement<MetroConfig> configElement = unmarshaller.unmarshal(factory.createXMLStreamReader(resourceUrl.openStream()), MetroConfig.class);
/* 245 */       result = (MetroConfig)configElement.getValue();
/* 246 */     } catch (Exception e) {
/* 247 */       LOGGER.warning(TubelineassemblyMessages.MASM_0010_ERROR_READING_CFG_FILE_FROM_LOCATION(resourceUrl.toString()), e);
/*     */     } 
/* 249 */     return result;
/*     */   }
/*     */   
/*     */   private static JAXBContext createJAXBContext() throws Exception {
/* 253 */     if (isJDKInternal())
/*     */     {
/* 255 */       return AccessController.<JAXBContext>doPrivileged(new PrivilegedExceptionAction<JAXBContext>()
/*     */           {
/*     */             public JAXBContext run() throws Exception
/*     */             {
/* 259 */               return JAXBContext.newInstance(MetroConfig.class.getPackage().getName());
/*     */             }
/* 261 */           },  createSecurityContext());
/*     */     }
/*     */ 
/*     */     
/* 265 */     return JAXBContext.newInstance(MetroConfig.class.getPackage().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   private static AccessControlContext createSecurityContext() {
/* 270 */     PermissionCollection perms = new Permissions();
/* 271 */     perms.add(new RuntimePermission("accessClassInPackage.com.sun.xml.internal.ws.runtime.config"));
/* 272 */     perms.add(new ReflectPermission("suppressAccessChecks"));
/* 273 */     return new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, perms) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isJDKInternal() {
/* 281 */     return MetroConfigLoader.class.getName().startsWith("com.sun.xml.internal.ws");
/*     */   }
/*     */   
/*     */   private static interface TubeFactoryListResolver {
/*     */     TubeFactoryList getFactories(TubelineDefinition param1TubelineDefinition); }
/*     */   
/*     */   private static class MetroConfigUrlLoader extends ResourceLoader { Container container;
/*     */     
/*     */     MetroConfigUrlLoader(ResourceLoader parentLoader) {
/* 290 */       this.parentLoader = parentLoader;
/*     */     }
/*     */     ResourceLoader parentLoader;
/*     */     MetroConfigUrlLoader(Container container) {
/* 294 */       this((container != null) ? (ResourceLoader)container.getSPI(ResourceLoader.class) : null);
/* 295 */       this.container = container;
/*     */     }
/*     */ 
/*     */     
/*     */     public URL getResource(String resource) throws MalformedURLException {
/* 300 */       MetroConfigLoader.LOGGER.entering(new Object[] { resource });
/* 301 */       URL resourceUrl = null;
/*     */       try {
/* 303 */         if (this.parentLoader != null) {
/* 304 */           if (MetroConfigLoader.LOGGER.isLoggable(Level.FINE)) {
/* 305 */             MetroConfigLoader.LOGGER.fine(TubelineassemblyMessages.MASM_0011_LOADING_RESOURCE(resource, this.parentLoader));
/*     */           }
/*     */           
/* 308 */           resourceUrl = this.parentLoader.getResource(resource);
/*     */         } 
/*     */         
/* 311 */         if (resourceUrl == null) {
/* 312 */           resourceUrl = loadViaClassLoaders("com/sun/xml/internal/ws/assembler/" + resource);
/*     */         }
/*     */         
/* 315 */         if (resourceUrl == null && this.container != null)
/*     */         {
/* 317 */           resourceUrl = loadFromServletContext(resource);
/*     */         }
/*     */         
/* 320 */         return resourceUrl;
/*     */       } finally {
/* 322 */         MetroConfigLoader.LOGGER.exiting(resourceUrl);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static URL loadViaClassLoaders(String resource) {
/* 327 */       URL resourceUrl = tryLoadFromClassLoader(resource, Thread.currentThread().getContextClassLoader());
/* 328 */       if (resourceUrl == null) {
/* 329 */         resourceUrl = tryLoadFromClassLoader(resource, MetroConfigLoader.class.getClassLoader());
/* 330 */         if (resourceUrl == null) {
/* 331 */           return ClassLoader.getSystemResource(resource);
/*     */         }
/*     */       } 
/*     */       
/* 335 */       return resourceUrl;
/*     */     }
/*     */     
/*     */     private static URL tryLoadFromClassLoader(String resource, ClassLoader loader) {
/* 339 */       return (loader != null) ? loader.getResource(resource) : null;
/*     */     }
/*     */     
/*     */     private URL loadFromServletContext(String resource) throws RuntimeException {
/* 343 */       Object context = null;
/*     */       try {
/* 345 */         Class<?> contextClass = Class.forName("javax.servlet.ServletContext");
/* 346 */         context = this.container.getSPI(contextClass);
/* 347 */         if (context != null) {
/* 348 */           if (MetroConfigLoader.LOGGER.isLoggable(Level.FINE)) {
/* 349 */             MetroConfigLoader.LOGGER.fine(TubelineassemblyMessages.MASM_0012_LOADING_VIA_SERVLET_CONTEXT(resource, context));
/*     */           }
/*     */           try {
/* 352 */             Method method = context.getClass().getMethod("getResource", new Class[] { String.class });
/* 353 */             Object result = method.invoke(context, new Object[] { "/WEB-INF/" + resource });
/* 354 */             return URL.class.cast(result);
/* 355 */           } catch (Exception e) {
/* 356 */             throw (RuntimeException)MetroConfigLoader.LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0013_ERROR_INVOKING_SERVLET_CONTEXT_METHOD("getResource()")), e);
/*     */           } 
/*     */         } 
/* 359 */       } catch (ClassNotFoundException e) {
/* 360 */         if (MetroConfigLoader.LOGGER.isLoggable(Level.FINE)) {
/* 361 */           MetroConfigLoader.LOGGER.fine(TubelineassemblyMessages.MASM_0014_UNABLE_TO_LOAD_CLASS("javax.servlet.ServletContext"));
/*     */         }
/*     */       } 
/* 364 */       return null;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/MetroConfigLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */