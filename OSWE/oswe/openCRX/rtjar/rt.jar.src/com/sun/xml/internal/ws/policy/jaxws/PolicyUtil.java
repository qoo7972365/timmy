/*     */ package com.sun.xml.internal.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.internal.ws.addressing.policy.AddressingFeatureConfigurator;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.internal.ws.encoding.policy.FastInfosetFeatureConfigurator;
/*     */ import com.sun.xml.internal.ws.encoding.policy.MtomFeatureConfigurator;
/*     */ import com.sun.xml.internal.ws.encoding.policy.SelectOptimalEncodingFeatureConfigurator;
/*     */ import com.sun.xml.internal.ws.policy.PolicyException;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.internal.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyUtil
/*     */ {
/*  56 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyUtil.class);
/*  57 */   private static final Collection<PolicyFeatureConfigurator> CONFIGURATORS = new LinkedList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  62 */     CONFIGURATORS.add(new AddressingFeatureConfigurator());
/*  63 */     CONFIGURATORS.add(new MtomFeatureConfigurator());
/*  64 */     CONFIGURATORS.add(new FastInfosetFeatureConfigurator());
/*  65 */     CONFIGURATORS.add(new SelectOptimalEncodingFeatureConfigurator());
/*     */ 
/*     */     
/*  68 */     addServiceProviders(CONFIGURATORS, PolicyFeatureConfigurator.class);
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
/*     */   public static <T> void addServiceProviders(Collection<T> providers, Class<T> service) {
/*  80 */     Iterator<T> foundProviders = ServiceFinder.find(service).iterator();
/*  81 */     while (foundProviders.hasNext()) {
/*  82 */       providers.add(foundProviders.next());
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
/*     */   public static void configureModel(WSDLModel model, PolicyMap policyMap) throws PolicyException {
/*  96 */     LOGGER.entering(new Object[] { model, policyMap });
/*  97 */     for (WSDLService service : model.getServices().values()) {
/*  98 */       for (WSDLPort port : service.getPorts()) {
/*  99 */         Collection<WebServiceFeature> features = getPortScopedFeatures(policyMap, service.getName(), port.getName());
/* 100 */         for (WebServiceFeature feature : features) {
/* 101 */           port.addFeature(feature);
/* 102 */           port.getBinding().addFeature(feature);
/*     */         } 
/*     */       } 
/*     */     } 
/* 106 */     LOGGER.exiting();
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
/*     */   public static Collection<WebServiceFeature> getPortScopedFeatures(PolicyMap policyMap, QName serviceName, QName portName) {
/* 119 */     LOGGER.entering(new Object[] { policyMap, serviceName, portName });
/* 120 */     Collection<WebServiceFeature> features = new ArrayList<>();
/*     */     try {
/* 122 */       PolicyMapKey key = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
/* 123 */       for (PolicyFeatureConfigurator configurator : CONFIGURATORS) {
/* 124 */         Collection<WebServiceFeature> additionalFeatures = configurator.getFeatures(key, policyMap);
/* 125 */         if (additionalFeatures != null) {
/* 126 */           features.addAll(additionalFeatures);
/*     */         }
/*     */       } 
/* 129 */     } catch (PolicyException e) {
/* 130 */       throw new WebServiceException(e);
/*     */     } 
/* 132 */     LOGGER.exiting(features);
/* 133 */     return features;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/jaxws/PolicyUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */