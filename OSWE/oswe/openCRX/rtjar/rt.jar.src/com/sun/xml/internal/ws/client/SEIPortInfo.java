/*    */ package com.sun.xml.internal.ws.client;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*    */ import com.sun.xml.internal.ws.binding.SOAPBindingImpl;
/*    */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*    */ import com.sun.xml.internal.ws.model.SOAPSEIModel;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SEIPortInfo
/*    */   extends PortInfo
/*    */ {
/*    */   public final Class sei;
/*    */   public final SOAPSEIModel model;
/*    */   
/*    */   public SEIPortInfo(WSServiceDelegate owner, Class sei, SOAPSEIModel model, @NotNull WSDLPort portModel) {
/* 60 */     super(owner, portModel);
/* 61 */     this.sei = sei;
/* 62 */     this.model = model;
/* 63 */     assert sei != null && model != null;
/*    */   }
/*    */ 
/*    */   
/*    */   public BindingImpl createBinding(WebServiceFeature[] webServiceFeatures, Class<?> portInterface) {
/* 68 */     BindingImpl binding = super.createBinding(webServiceFeatures, portInterface);
/* 69 */     return setKnownHeaders(binding);
/*    */   }
/*    */ 
/*    */   
/*    */   public BindingImpl createBinding(WebServiceFeatureList webServiceFeatures, Class<?> portInterface) {
/* 74 */     BindingImpl binding = createBinding(webServiceFeatures, portInterface, null);
/* 75 */     return setKnownHeaders(binding);
/*    */   }
/*    */   
/*    */   private BindingImpl setKnownHeaders(BindingImpl binding) {
/* 79 */     if (binding instanceof SOAPBindingImpl) {
/* 80 */       ((SOAPBindingImpl)binding).setPortKnownHeaders(this.model.getKnownHeaders());
/*    */     }
/* 82 */     return binding;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/SEIPortInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */