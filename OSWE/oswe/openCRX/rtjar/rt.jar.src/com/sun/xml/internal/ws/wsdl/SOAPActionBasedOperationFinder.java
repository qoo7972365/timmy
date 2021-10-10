/*    */ package com.sun.xml.internal.ws.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*    */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ final class SOAPActionBasedOperationFinder
/*    */   extends WSDLOperationFinder
/*    */ {
/*    */   private final Map<String, WSDLOperationMapping> methodHandlers;
/*    */   
/*    */   public SOAPActionBasedOperationFinder(WSDLPort wsdlModel, WSBinding binding, @Nullable SEIModel seiModel) {
/* 56 */     super(wsdlModel, binding, seiModel);
/* 57 */     this.methodHandlers = new HashMap<>();
/*    */ 
/*    */     
/* 60 */     Map<String, Integer> unique = new HashMap<>();
/* 61 */     if (seiModel != null) {
/* 62 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/* 63 */         String soapAction = m.getSOAPAction();
/* 64 */         Integer count = unique.get(soapAction);
/* 65 */         if (count == null) {
/* 66 */           unique.put(soapAction, Integer.valueOf(1)); continue;
/*    */         } 
/* 68 */         unique.put(soapAction, count = Integer.valueOf(count.intValue() + 1));
/*    */       } 
/*    */ 
/*    */       
/* 72 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/* 73 */         String soapAction = m.getSOAPAction();
/*    */ 
/*    */         
/* 76 */         if (((Integer)unique.get(soapAction)).intValue() == 1) {
/* 77 */           this.methodHandlers.put('"' + soapAction + '"', wsdlOperationMapping(m));
/*    */         }
/*    */       } 
/*    */     } else {
/* 81 */       for (WSDLBoundOperation wsdlOp : wsdlModel.getBinding().getBindingOperations()) {
/* 82 */         this.methodHandlers.put(wsdlOp.getSOAPAction(), wsdlOperationMapping(wsdlOp));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/* 90 */     return (request.soapAction == null) ? null : this.methodHandlers.get(request.soapAction);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/SOAPActionBasedOperationFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */