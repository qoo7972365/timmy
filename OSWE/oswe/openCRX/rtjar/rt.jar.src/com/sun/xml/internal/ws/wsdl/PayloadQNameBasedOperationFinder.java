/*     */ package com.sun.xml.internal.ws.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import com.sun.xml.internal.ws.util.QNameMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PayloadQNameBasedOperationFinder
/*     */   extends WSDLOperationFinder
/*     */ {
/*  60 */   private static final Logger LOGGER = Logger.getLogger(PayloadQNameBasedOperationFinder.class.getName());
/*     */   
/*     */   public static final String EMPTY_PAYLOAD_LOCAL = "";
/*     */   public static final String EMPTY_PAYLOAD_NSURI = "";
/*  64 */   public static final QName EMPTY_PAYLOAD = new QName("", "");
/*     */   
/*  66 */   private final QNameMap<WSDLOperationMapping> methodHandlers = new QNameMap();
/*  67 */   private final QNameMap<List<String>> unique = new QNameMap();
/*     */ 
/*     */   
/*     */   public PayloadQNameBasedOperationFinder(WSDLPort wsdlModel, WSBinding binding, @Nullable SEIModel seiModel) {
/*  71 */     super(wsdlModel, binding, seiModel);
/*  72 */     if (seiModel != null) {
/*     */       
/*  74 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/*  75 */         if ((m.getMEP()).isAsync)
/*     */           continue; 
/*  77 */         QName name = m.getRequestPayloadName();
/*  78 */         if (name == null)
/*  79 */           name = EMPTY_PAYLOAD; 
/*  80 */         List<String> methods = (List<String>)this.unique.get(name);
/*  81 */         if (methods == null) {
/*  82 */           methods = new ArrayList<>();
/*  83 */           this.unique.put(name, methods);
/*     */         } 
/*  85 */         methods.add(m.getMethod().getName());
/*     */       } 
/*     */ 
/*     */       
/*  89 */       for (QNameMap.Entry<List<String>> e : (Iterable<QNameMap.Entry<List<String>>>)this.unique.entrySet()) {
/*  90 */         if (((List)e.getValue()).size() > 1) {
/*  91 */           LOGGER.warning(ServerMessages.NON_UNIQUE_DISPATCH_QNAME(e.getValue(), e.createQName()));
/*     */         }
/*     */       } 
/*     */       
/*  95 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/*  96 */         QName name = m.getRequestPayloadName();
/*  97 */         if (name == null) {
/*  98 */           name = EMPTY_PAYLOAD;
/*     */         }
/*     */         
/* 101 */         if (((List)this.unique.get(name)).size() == 1) {
/* 102 */           this.methodHandlers.put(name, wsdlOperationMapping(m));
/*     */         }
/*     */       } 
/*     */     } else {
/* 106 */       for (WSDLBoundOperation wsdlOp : wsdlModel.getBinding().getBindingOperations()) {
/* 107 */         QName name = wsdlOp.getRequestPayloadName();
/* 108 */         if (name == null)
/* 109 */           name = EMPTY_PAYLOAD; 
/* 110 */         this.methodHandlers.put(name, wsdlOperationMapping(wsdlOp));
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
/*     */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/*     */     String nsUri;
/* 126 */     Message message = request.getMessage();
/* 127 */     String localPart = message.getPayloadLocalPart();
/*     */     
/* 129 */     if (localPart == null) {
/* 130 */       localPart = "";
/* 131 */       nsUri = "";
/*     */     } else {
/* 133 */       nsUri = message.getPayloadNamespaceURI();
/* 134 */       if (nsUri == null)
/* 135 */         nsUri = ""; 
/*     */     } 
/* 137 */     WSDLOperationMapping op = (WSDLOperationMapping)this.methodHandlers.get(nsUri, localPart);
/*     */ 
/*     */     
/* 140 */     if (op == null && !this.unique.containsKey(nsUri, localPart)) {
/* 141 */       String dispatchKey = "{" + nsUri + "}" + localPart;
/* 142 */       String faultString = ServerMessages.DISPATCH_CANNOT_FIND_METHOD(dispatchKey);
/* 143 */       throw new DispatchException(SOAPFaultBuilder.createSOAPFaultMessage(this.binding
/* 144 */             .getSOAPVersion(), faultString, (this.binding.getSOAPVersion()).faultCodeClient));
/*     */     } 
/* 146 */     return op;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/PayloadQNameBasedOperationFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */