/*     */ package com.sun.xml.internal.ws.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.resources.AddressingMessages;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ final class ActionBasedOperationFinder
/*     */   extends WSDLOperationFinder
/*     */ {
/*  65 */   private static final Logger LOGGER = Logger.getLogger(ActionBasedOperationFinder.class.getName());
/*     */   private final Map<ActionBasedOperationSignature, WSDLOperationMapping> uniqueOpSignatureMap;
/*     */   private final Map<String, WSDLOperationMapping> actionMap;
/*     */   @NotNull
/*     */   private final AddressingVersion av;
/*     */   
/*     */   public ActionBasedOperationFinder(WSDLPort wsdlModel, WSBinding binding, @Nullable SEIModel seiModel) {
/*  72 */     super(wsdlModel, binding, seiModel);
/*     */     
/*  74 */     assert binding.getAddressingVersion() != null;
/*  75 */     this.av = binding.getAddressingVersion();
/*  76 */     this.uniqueOpSignatureMap = new HashMap<>();
/*  77 */     this.actionMap = new HashMap<>();
/*     */     
/*  79 */     if (seiModel != null) {
/*  80 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/*  81 */         if ((m.getMEP()).isAsync) {
/*     */           continue;
/*     */         }
/*  84 */         String action = m.getInputAction();
/*  85 */         QName payloadName = m.getRequestPayloadName();
/*  86 */         if (payloadName == null) {
/*  87 */           payloadName = PayloadQNameBasedOperationFinder.EMPTY_PAYLOAD;
/*     */         }
/*  89 */         if ((action == null || action.equals("")) && 
/*  90 */           m.getOperation() != null) action = m.getOperation().getOperation().getInput().getAction();
/*     */ 
/*     */         
/*  93 */         if (action != null) {
/*  94 */           ActionBasedOperationSignature opSignature = new ActionBasedOperationSignature(action, payloadName);
/*  95 */           if (this.uniqueOpSignatureMap.get(opSignature) != null) {
/*  96 */             LOGGER.warning(AddressingMessages.NON_UNIQUE_OPERATION_SIGNATURE(this.uniqueOpSignatureMap
/*  97 */                   .get(opSignature), m.getOperationQName(), action, payloadName));
/*     */           }
/*  99 */           this.uniqueOpSignatureMap.put(opSignature, wsdlOperationMapping(m));
/* 100 */           this.actionMap.put(action, wsdlOperationMapping(m));
/*     */         } 
/*     */       } 
/*     */     } else {
/* 104 */       for (WSDLBoundOperation wsdlOp : wsdlModel.getBinding().getBindingOperations()) {
/* 105 */         QName payloadName = wsdlOp.getRequestPayloadName();
/* 106 */         if (payloadName == null)
/* 107 */           payloadName = PayloadQNameBasedOperationFinder.EMPTY_PAYLOAD; 
/* 108 */         String action = wsdlOp.getOperation().getInput().getAction();
/* 109 */         ActionBasedOperationSignature opSignature = new ActionBasedOperationSignature(action, payloadName);
/*     */         
/* 111 */         if (this.uniqueOpSignatureMap.get(opSignature) != null) {
/* 112 */           LOGGER.warning(AddressingMessages.NON_UNIQUE_OPERATION_SIGNATURE(this.uniqueOpSignatureMap
/* 113 */                 .get(opSignature), wsdlOp.getName(), action, payloadName));
/*     */         }
/*     */         
/* 116 */         this.uniqueOpSignatureMap.put(opSignature, wsdlOperationMapping(wsdlOp));
/* 117 */         this.actionMap.put(action, wsdlOperationMapping(wsdlOp));
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
/*     */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/*     */     QName payloadName;
/* 136 */     MessageHeaders hl = request.getMessage().getHeaders();
/* 137 */     String action = AddressingUtils.getAction(hl, this.av, this.binding.getSOAPVersion());
/*     */     
/* 139 */     if (action == null)
/*     */     {
/* 141 */       return null;
/*     */     }
/* 143 */     Message message = request.getMessage();
/*     */     
/* 145 */     String localPart = message.getPayloadLocalPart();
/* 146 */     if (localPart == null) {
/* 147 */       payloadName = PayloadQNameBasedOperationFinder.EMPTY_PAYLOAD;
/*     */     } else {
/* 149 */       String nsUri = message.getPayloadNamespaceURI();
/* 150 */       if (nsUri == null)
/* 151 */         nsUri = ""; 
/* 152 */       payloadName = new QName(nsUri, localPart);
/*     */     } 
/*     */     
/* 155 */     WSDLOperationMapping opMapping = this.uniqueOpSignatureMap.get(new ActionBasedOperationSignature(action, payloadName));
/* 156 */     if (opMapping != null) {
/* 157 */       return opMapping;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 162 */     opMapping = this.actionMap.get(action);
/* 163 */     if (opMapping != null) {
/* 164 */       return opMapping;
/*     */     }
/*     */     
/* 167 */     Message result = Messages.create(action, this.av, this.binding.getSOAPVersion());
/*     */     
/* 169 */     throw new DispatchException(result);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/ActionBasedOperationFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */