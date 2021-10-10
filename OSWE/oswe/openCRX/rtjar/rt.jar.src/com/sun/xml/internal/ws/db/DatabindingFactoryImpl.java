/*     */ package com.sun.xml.internal.ws.db;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.Databinding;
/*     */ import com.oracle.webservices.internal.api.databinding.DatabindingModeFeature;
/*     */ import com.oracle.webservices.internal.api.databinding.WSDLGenerator;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.internal.ws.api.databinding.DatabindingFactory;
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.spi.db.DatabindingProvider;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.xml.sax.EntityResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabindingFactoryImpl
/*     */   extends DatabindingFactory
/*     */ {
/*     */   static final String WsRuntimeFactoryDefaultImpl = "com.sun.xml.internal.ws.db.DatabindingProviderImpl";
/*  64 */   protected Map<String, Object> properties = new HashMap<>();
/*     */   protected DatabindingProvider defaultRuntimeFactory;
/*     */   protected List<DatabindingProvider> providers;
/*     */   
/*     */   private static List<DatabindingProvider> providers() {
/*  69 */     List<DatabindingProvider> factories = new ArrayList<>();
/*  70 */     for (DatabindingProvider p : ServiceFinder.find(DatabindingProvider.class)) {
/*  71 */       factories.add(p);
/*     */     }
/*  73 */     return factories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> properties() {
/*  80 */     return this.properties;
/*     */   }
/*     */   
/*     */   <T> T property(Class<T> propType, String propName) {
/*  84 */     if (propName == null) propName = propType.getName(); 
/*  85 */     return propType.cast(this.properties.get(propName));
/*     */   }
/*     */   
/*     */   public DatabindingProvider provider(DatabindingConfig config) {
/*  89 */     String mode = databindingMode(config);
/*  90 */     if (this.providers == null)
/*  91 */       this.providers = providers(); 
/*  92 */     DatabindingProvider provider = null;
/*  93 */     if (this.providers != null)
/*  94 */       for (DatabindingProvider p : this.providers) {
/*  95 */         if (p.isFor(mode))
/*  96 */           provider = p; 
/*  97 */       }   if (provider == null) {
/*  98 */       provider = new DatabindingProviderImpl();
/*     */     }
/* 100 */     return provider;
/*     */   }
/*     */   
/*     */   public Databinding createRuntime(DatabindingConfig config) {
/* 104 */     DatabindingProvider provider = provider(config);
/* 105 */     return provider.create(config);
/*     */   }
/*     */   
/*     */   public WSDLGenerator createWsdlGen(DatabindingConfig config) {
/* 109 */     DatabindingProvider provider = provider(config);
/* 110 */     return provider.wsdlGen(config);
/*     */   }
/*     */   
/*     */   String databindingMode(DatabindingConfig config) {
/* 114 */     if (config.getMappingInfo() != null && config
/* 115 */       .getMappingInfo().getDatabindingMode() != null)
/* 116 */       return config.getMappingInfo().getDatabindingMode(); 
/* 117 */     if (config.getFeatures() != null) for (WebServiceFeature f : config.getFeatures()) {
/* 118 */         if (f instanceof DatabindingModeFeature) {
/* 119 */           DatabindingModeFeature dmf = (DatabindingModeFeature)f;
/* 120 */           config.properties().putAll(dmf.getProperties());
/* 121 */           return dmf.getMode();
/*     */         } 
/*     */       }  
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   ClassLoader classLoader() {
/* 128 */     ClassLoader classLoader = property(ClassLoader.class, null);
/* 129 */     if (classLoader == null) classLoader = Thread.currentThread().getContextClassLoader(); 
/* 130 */     return classLoader;
/*     */   }
/*     */   
/*     */   Properties loadPropertiesFile(String fileName) {
/* 134 */     ClassLoader classLoader = classLoader();
/* 135 */     Properties p = new Properties();
/*     */     try {
/* 137 */       InputStream is = null;
/* 138 */       if (classLoader == null) {
/* 139 */         is = ClassLoader.getSystemResourceAsStream(fileName);
/*     */       } else {
/* 141 */         is = classLoader.getResourceAsStream(fileName);
/*     */       } 
/* 143 */       if (is != null) {
/* 144 */         p.load(is);
/*     */       }
/* 146 */     } catch (Exception e) {
/* 147 */       throw new WebServiceException(e);
/*     */     } 
/* 149 */     return p;
/*     */   }
/*     */   
/*     */   public Databinding.Builder createBuilder(Class<?> contractClass, Class<?> endpointClass) {
/* 153 */     return new ConfigBuilder(this, contractClass, endpointClass);
/*     */   }
/*     */   
/*     */   static class ConfigBuilder implements Databinding.Builder {
/*     */     DatabindingConfig config;
/*     */     DatabindingFactoryImpl factory;
/*     */     
/*     */     ConfigBuilder(DatabindingFactoryImpl f, Class<?> contractClass, Class<?> implBeanClass) {
/* 161 */       this.factory = f;
/* 162 */       this.config = new DatabindingConfig();
/* 163 */       this.config.setContractClass(contractClass);
/* 164 */       this.config.setEndpointClass(implBeanClass);
/*     */     }
/*     */     public Databinding.Builder targetNamespace(String targetNamespace) {
/* 167 */       this.config.getMappingInfo().setTargetNamespace(targetNamespace);
/* 168 */       return this;
/*     */     }
/*     */     public Databinding.Builder serviceName(QName serviceName) {
/* 171 */       this.config.getMappingInfo().setServiceName(serviceName);
/* 172 */       return this;
/*     */     }
/*     */     public Databinding.Builder portName(QName portName) {
/* 175 */       this.config.getMappingInfo().setPortName(portName);
/* 176 */       return this;
/*     */     }
/*     */     public Databinding.Builder wsdlURL(URL wsdlURL) {
/* 179 */       this.config.setWsdlURL(wsdlURL);
/* 180 */       return this;
/*     */     }
/*     */     public Databinding.Builder wsdlSource(Source wsdlSource) {
/* 183 */       this.config.setWsdlSource(wsdlSource);
/* 184 */       return this;
/*     */     }
/*     */     public Databinding.Builder entityResolver(EntityResolver entityResolver) {
/* 187 */       this.config.setEntityResolver(entityResolver);
/* 188 */       return this;
/*     */     }
/*     */     public Databinding.Builder classLoader(ClassLoader classLoader) {
/* 191 */       this.config.setClassLoader(classLoader);
/* 192 */       return this;
/*     */     }
/*     */     public Databinding.Builder feature(WebServiceFeature... f) {
/* 195 */       this.config.setFeatures(f);
/* 196 */       return this;
/*     */     }
/*     */     public Databinding.Builder property(String name, Object value) {
/* 199 */       this.config.properties().put(name, value);
/* 200 */       if (isfor(BindingID.class, name, value)) {
/* 201 */         this.config.getMappingInfo().setBindingID((BindingID)value);
/*     */       }
/* 203 */       if (isfor(WSBinding.class, name, value)) {
/* 204 */         this.config.setWSBinding((WSBinding)value);
/*     */       }
/* 206 */       if (isfor(WSDLPort.class, name, value)) {
/* 207 */         this.config.setWsdlPort((WSDLPort)value);
/*     */       }
/* 209 */       if (isfor(MetadataReader.class, name, value)) {
/* 210 */         this.config.setMetadataReader((MetadataReader)value);
/*     */       }
/* 212 */       return this;
/*     */     }
/*     */     boolean isfor(Class<?> type, String name, Object value) {
/* 215 */       return (type.getName().equals(name) && type.isInstance(value));
/*     */     }
/*     */     
/*     */     public Databinding build() {
/* 219 */       return this.factory.createRuntime(this.config);
/*     */     }
/*     */     public WSDLGenerator createWSDLGenerator() {
/* 222 */       return this.factory.createWsdlGen(this.config);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/DatabindingFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */