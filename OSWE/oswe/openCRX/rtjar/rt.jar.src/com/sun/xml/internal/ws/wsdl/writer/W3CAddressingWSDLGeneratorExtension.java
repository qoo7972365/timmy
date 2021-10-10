/*     */ package com.sun.xml.internal.ws.wsdl.writer;
/*     */ 
/*     */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.model.CheckedException;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.wsdl.writer.WSDLGenExtnContext;
/*     */ import com.sun.xml.internal.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.Action;
/*     */ import javax.xml.ws.FaultAction;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CAddressingWSDLGeneratorExtension
/*     */   extends WSDLGeneratorExtension
/*     */ {
/*     */   private boolean enabled;
/*     */   private boolean required = false;
/*     */   
/*     */   public void start(WSDLGenExtnContext ctxt) {
/*  53 */     WSBinding binding = ctxt.getBinding();
/*  54 */     TypedXmlWriter root = ctxt.getRoot();
/*  55 */     this.enabled = binding.isFeatureEnabled(AddressingFeature.class);
/*  56 */     if (!this.enabled)
/*     */       return; 
/*  58 */     AddressingFeature ftr = (AddressingFeature)binding.getFeature(AddressingFeature.class);
/*  59 */     this.required = ftr.isRequired();
/*  60 */     root._namespace(AddressingVersion.W3C.wsdlNsUri, AddressingVersion.W3C.getWsdlPrefix());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
/*  65 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/*  68 */     Action a = method.getSEIMethod().<Action>getAnnotation(Action.class);
/*  69 */     if (a != null && !a.input().equals("")) {
/*  70 */       addAttribute(input, a.input());
/*     */     } else {
/*     */       
/*  73 */       String soapAction = method.getBinding().getSOAPAction();
/*     */       
/*  75 */       if (soapAction == null || soapAction.equals("")) {
/*     */         
/*  77 */         String defaultAction = getDefaultAction(method);
/*  78 */         addAttribute(input, defaultAction);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static final String getDefaultAction(JavaMethod method) {
/*  84 */     String tns = method.getOwner().getTargetNamespace();
/*  85 */     String delim = "/";
/*     */     
/*     */     try {
/*  88 */       URI uri = new URI(tns);
/*  89 */       if (uri.getScheme().equalsIgnoreCase("urn"))
/*  90 */         delim = ":"; 
/*  91 */     } catch (URISyntaxException e) {
/*  92 */       LOGGER.warning("TargetNamespace of WebService is not a valid URI");
/*     */     } 
/*  94 */     if (tns.endsWith(delim)) {
/*  95 */       tns = tns.substring(0, tns.length() - 1);
/*     */     }
/*     */ 
/*     */     
/*  99 */     String name = method.getMEP().isOneWay() ? method.getOperationName() : (method.getOperationName() + "Request");
/*     */     
/* 101 */     return tns + delim + method
/* 102 */       .getOwner().getPortTypeName().getLocalPart() + delim + name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
/* 108 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/* 111 */     Action a = method.getSEIMethod().<Action>getAnnotation(Action.class);
/* 112 */     if (a != null && !a.output().equals("")) {
/* 113 */       addAttribute(output, a.output());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
/* 119 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/* 122 */     Action a = method.getSEIMethod().<Action>getAnnotation(Action.class);
/* 123 */     Class[] exs = method.getSEIMethod().getExceptionTypes();
/*     */     
/* 125 */     if (exs == null) {
/*     */       return;
/*     */     }
/* 128 */     if (a != null && a.fault() != null) {
/* 129 */       for (FaultAction fa : a.fault()) {
/* 130 */         if (fa.className().getName().equals(ce.getExceptionClass().getName())) {
/* 131 */           if (fa.value().equals("")) {
/*     */             return;
/*     */           }
/* 134 */           addAttribute(fault, fa.value());
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void addAttribute(TypedXmlWriter writer, String attrValue) {
/* 142 */     writer._attribute(AddressingVersion.W3C.wsdlActionTag, attrValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindingExtension(TypedXmlWriter binding) {
/* 147 */     if (!this.enabled)
/*     */       return; 
/* 149 */     binding._element(AddressingVersion.W3C.wsdlExtensionTag, UsingAddressing.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   private static final Logger LOGGER = Logger.getLogger(W3CAddressingWSDLGeneratorExtension.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/writer/W3CAddressingWSDLGeneratorExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */