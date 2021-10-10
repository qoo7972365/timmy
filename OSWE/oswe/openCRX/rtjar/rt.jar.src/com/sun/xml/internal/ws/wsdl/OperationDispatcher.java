/*    */ package com.sun.xml.internal.ws.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*    */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class OperationDispatcher
/*    */ {
/*    */   private List<WSDLOperationFinder> opFinders;
/*    */   private WSBinding binding;
/*    */   
/*    */   public OperationDispatcher(@NotNull WSDLPort wsdlModel, @NotNull WSBinding binding, @Nullable SEIModel seiModel) {
/* 58 */     this.binding = binding;
/* 59 */     this.opFinders = new ArrayList<>();
/* 60 */     if (binding.getAddressingVersion() != null) {
/* 61 */       this.opFinders.add(new ActionBasedOperationFinder(wsdlModel, binding, seiModel));
/*    */     }
/* 63 */     this.opFinders.add(new PayloadQNameBasedOperationFinder(wsdlModel, binding, seiModel));
/* 64 */     this.opFinders.add(new SOAPActionBasedOperationFinder(wsdlModel, binding, seiModel));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public QName getWSDLOperationQName(Packet request) throws DispatchException {
/* 75 */     WSDLOperationMapping m = getWSDLOperationMapping(request);
/* 76 */     return (m != null) ? m.getOperationName() : null;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/* 81 */     for (WSDLOperationFinder finder : this.opFinders) {
/* 82 */       WSDLOperationMapping opName = finder.getWSDLOperationMapping(request);
/* 83 */       if (opName != null) {
/* 84 */         return opName;
/*    */       }
/*    */     } 
/* 87 */     String err = MessageFormat.format("Request=[SOAPAction={0},Payload='{'{1}'}'{2}]", new Object[] { request.soapAction, request
/* 88 */           .getMessage().getPayloadNamespaceURI(), request
/* 89 */           .getMessage().getPayloadLocalPart() });
/* 90 */     String faultString = ServerMessages.DISPATCH_CANNOT_FIND_METHOD(err);
/* 91 */     Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(this.binding
/* 92 */         .getSOAPVersion(), faultString, (this.binding.getSOAPVersion()).faultCodeClient);
/* 93 */     throw new DispatchException(faultMsg);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/OperationDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */