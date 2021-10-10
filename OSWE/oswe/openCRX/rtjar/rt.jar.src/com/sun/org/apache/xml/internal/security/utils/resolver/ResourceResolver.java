/*     */ package com.sun.org.apache.xml.internal.security.utils.resolver;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.implementations.ResolverDirectHTTP;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.implementations.ResolverFragment;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.implementations.ResolverLocalFilesystem;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.implementations.ResolverXPointer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Attr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceResolver
/*     */ {
/*  47 */   private static Logger log = Logger.getLogger(ResourceResolver.class.getName());
/*     */ 
/*     */   
/*  50 */   private static List<ResourceResolver> resolverList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ResourceResolverSpi resolverSpi;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceResolver(ResourceResolverSpi paramResourceResolverSpi) {
/*  61 */     this.resolverSpi = paramResourceResolverSpi;
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
/*     */   public static final ResourceResolver getInstance(Attr paramAttr, String paramString) throws ResourceResolverException {
/*  75 */     return getInstance(paramAttr, paramString, false);
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
/*     */   public static final ResourceResolver getInstance(Attr paramAttr, String paramString, boolean paramBoolean) throws ResourceResolverException {
/*  91 */     ResourceResolverContext resourceResolverContext = new ResourceResolverContext(paramAttr, paramString, paramBoolean);
/*  92 */     return internalGetInstance(resourceResolverContext);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <N> ResourceResolver internalGetInstance(ResourceResolverContext paramResourceResolverContext) throws ResourceResolverException {
/*  97 */     synchronized (resolverList) {
/*  98 */       for (ResourceResolver resourceResolver1 : resolverList) {
/*  99 */         ResourceResolver resourceResolver2 = resourceResolver1;
/* 100 */         if (!resourceResolver1.resolverSpi.engineIsThreadSafe()) {
/*     */           
/*     */           try {
/* 103 */             resourceResolver2 = new ResourceResolver((ResourceResolverSpi)resourceResolver1.resolverSpi.getClass().newInstance());
/* 104 */           } catch (InstantiationException instantiationException) {
/* 105 */             throw new ResourceResolverException("", instantiationException, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/* 106 */           } catch (IllegalAccessException illegalAccessException) {
/* 107 */             throw new ResourceResolverException("", illegalAccessException, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */           } 
/*     */         }
/*     */         
/* 111 */         if (log.isLoggable(Level.FINE)) {
/* 112 */           log.log(Level.FINE, "check resolvability by class " + resourceResolver2
/* 113 */               .getClass().getName());
/*     */         }
/*     */ 
/*     */         
/* 117 */         if (resourceResolver2 != null && resourceResolver2.canResolve(paramResourceResolverContext)) {
/*     */           
/* 119 */           if (paramResourceResolverContext.secureValidation && (resourceResolver2.resolverSpi instanceof ResolverLocalFilesystem || resourceResolver2.resolverSpi instanceof ResolverDirectHTTP)) {
/*     */ 
/*     */             
/* 122 */             Object[] arrayOfObject1 = { resourceResolver2.resolverSpi.getClass().getName() };
/* 123 */             throw new ResourceResolverException("signature.Reference.ForbiddenResolver", arrayOfObject1, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */           } 
/*     */ 
/*     */           
/* 127 */           return resourceResolver2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     Object[] arrayOfObject = { (paramResourceResolverContext.uriToResolve != null) ? paramResourceResolverContext.uriToResolve : "null", paramResourceResolverContext.baseUri };
/*     */ 
/*     */     
/* 135 */     throw new ResourceResolverException("utils.resolver.noClass", arrayOfObject, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
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
/*     */   public static ResourceResolver getInstance(Attr paramAttr, String paramString, List<ResourceResolver> paramList) throws ResourceResolverException {
/* 151 */     return getInstance(paramAttr, paramString, paramList, false);
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
/*     */   public static ResourceResolver getInstance(Attr paramAttr, String paramString, List<ResourceResolver> paramList, boolean paramBoolean) throws ResourceResolverException {
/* 168 */     if (log.isLoggable(Level.FINE)) {
/* 169 */       log.log(Level.FINE, "I was asked to create a ResourceResolver and got " + ((paramList == null) ? 0 : paramList
/*     */           
/* 171 */           .size()));
/*     */     }
/*     */ 
/*     */     
/* 175 */     ResourceResolverContext resourceResolverContext = new ResourceResolverContext(paramAttr, paramString, paramBoolean);
/*     */ 
/*     */     
/* 178 */     if (paramList != null) {
/* 179 */       for (byte b = 0; b < paramList.size(); b++) {
/* 180 */         ResourceResolver resourceResolver = paramList.get(b);
/*     */         
/* 182 */         if (resourceResolver != null) {
/* 183 */           if (log.isLoggable(Level.FINE)) {
/* 184 */             String str = resourceResolver.resolverSpi.getClass().getName();
/* 185 */             log.log(Level.FINE, "check resolvability by class " + str);
/*     */           } 
/*     */           
/* 188 */           if (resourceResolver.canResolve(resourceResolverContext)) {
/* 189 */             return resourceResolver;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 195 */     return internalGetInstance(resourceResolverContext);
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
/*     */   public static void register(String paramString) {
/* 208 */     JavaUtils.checkRegisterPermission();
/*     */     
/*     */     try {
/* 211 */       Class<?> clazz = Class.forName(paramString);
/* 212 */       register((Class)clazz, false);
/* 213 */     } catch (ClassNotFoundException classNotFoundException) {
/* 214 */       log.log(Level.WARNING, "Error loading resolver " + paramString + " disabling it");
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
/*     */   public static void registerAtStart(String paramString) {
/* 228 */     JavaUtils.checkRegisterPermission();
/*     */     
/*     */     try {
/* 231 */       Class<?> clazz = Class.forName(paramString);
/* 232 */       register((Class)clazz, true);
/* 233 */     } catch (ClassNotFoundException classNotFoundException) {
/* 234 */       log.log(Level.WARNING, "Error loading resolver " + paramString + " disabling it");
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
/*     */   public static void register(Class<? extends ResourceResolverSpi> paramClass, boolean paramBoolean) {
/* 247 */     JavaUtils.checkRegisterPermission();
/*     */     try {
/* 249 */       ResourceResolverSpi resourceResolverSpi = paramClass.newInstance();
/* 250 */       register(resourceResolverSpi, paramBoolean);
/* 251 */     } catch (IllegalAccessException illegalAccessException) {
/* 252 */       log.log(Level.WARNING, "Error loading resolver " + paramClass + " disabling it");
/* 253 */     } catch (InstantiationException instantiationException) {
/* 254 */       log.log(Level.WARNING, "Error loading resolver " + paramClass + " disabling it");
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
/*     */   public static void register(ResourceResolverSpi paramResourceResolverSpi, boolean paramBoolean) {
/* 267 */     JavaUtils.checkRegisterPermission();
/* 268 */     synchronized (resolverList) {
/* 269 */       if (paramBoolean) {
/* 270 */         resolverList.add(0, new ResourceResolver(paramResourceResolverSpi));
/*     */       } else {
/* 272 */         resolverList.add(new ResourceResolver(paramResourceResolverSpi));
/*     */       } 
/*     */     } 
/* 275 */     if (log.isLoggable(Level.FINE)) {
/* 276 */       log.log(Level.FINE, "Registered resolver: " + paramResourceResolverSpi.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerDefaultResolvers() {
/* 284 */     synchronized (resolverList) {
/* 285 */       resolverList.add(new ResourceResolver(new ResolverFragment()));
/* 286 */       resolverList.add(new ResourceResolver(new ResolverLocalFilesystem()));
/* 287 */       resolverList.add(new ResourceResolver(new ResolverXPointer()));
/* 288 */       resolverList.add(new ResourceResolver(new ResolverDirectHTTP()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public XMLSignatureInput resolve(Attr paramAttr, String paramString) throws ResourceResolverException {
/* 298 */     return resolve(paramAttr, paramString, true);
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
/*     */   public XMLSignatureInput resolve(Attr paramAttr, String paramString, boolean paramBoolean) throws ResourceResolverException {
/* 312 */     ResourceResolverContext resourceResolverContext = new ResourceResolverContext(paramAttr, paramString, paramBoolean);
/* 313 */     return this.resolverSpi.engineResolveURI(resourceResolverContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String paramString1, String paramString2) {
/* 323 */     this.resolverSpi.engineSetProperty(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProperty(String paramString) {
/* 333 */     return this.resolverSpi.engineGetProperty(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperties(Map<String, String> paramMap) {
/* 342 */     this.resolverSpi.engineAddProperies(paramMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getPropertyKeys() {
/* 351 */     return this.resolverSpi.engineGetPropertyKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean understandsProperty(String paramString) {
/* 361 */     return this.resolverSpi.understandsProperty(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canResolve(ResourceResolverContext paramResourceResolverContext) {
/* 372 */     return this.resolverSpi.engineCanResolveURI(paramResourceResolverContext);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/ResourceResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */