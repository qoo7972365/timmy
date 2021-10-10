/*     */ package com.sun.xml.internal.ws.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
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
/*     */ public abstract class WSDLOperationFinder
/*     */ {
/*     */   protected final WSDLPort wsdlModel;
/*     */   protected final WSBinding binding;
/*     */   protected final SEIModel seiModel;
/*     */   
/*     */   public WSDLOperationFinder(@NotNull WSDLPort wsdlModel, @NotNull WSBinding binding, @Nullable SEIModel seiModel) {
/*  55 */     this.wsdlModel = wsdlModel;
/*  56 */     this.binding = binding;
/*  57 */     this.seiModel = seiModel;
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
/*     */   public QName getWSDLOperationQName(Packet request) throws DispatchException {
/*  74 */     WSDLOperationMapping m = getWSDLOperationMapping(request);
/*  75 */     return (m != null) ? m.getOperationName() : null;
/*     */   }
/*     */   
/*     */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/*  79 */     return null;
/*     */   }
/*     */   
/*     */   protected WSDLOperationMapping wsdlOperationMapping(JavaMethodImpl j) {
/*  83 */     return new WSDLOperationMappingImpl(j.getOperation(), j);
/*     */   }
/*     */   
/*     */   protected WSDLOperationMapping wsdlOperationMapping(WSDLBoundOperation o) {
/*  87 */     return new WSDLOperationMappingImpl(o, null);
/*     */   }
/*     */   
/*     */   static class WSDLOperationMappingImpl implements WSDLOperationMapping {
/*     */     private WSDLBoundOperation wsdlOperation;
/*     */     private JavaMethod javaMethod;
/*     */     private QName operationName;
/*     */     
/*     */     WSDLOperationMappingImpl(WSDLBoundOperation wsdlOperation, JavaMethodImpl javaMethod) {
/*  96 */       this.wsdlOperation = wsdlOperation;
/*  97 */       this.javaMethod = (JavaMethod)javaMethod;
/*  98 */       this.operationName = (javaMethod != null) ? javaMethod.getOperationQName() : wsdlOperation.getName();
/*     */     }
/*     */     
/*     */     public WSDLBoundOperation getWSDLBoundOperation() {
/* 102 */       return this.wsdlOperation;
/*     */     }
/*     */     
/*     */     public JavaMethod getJavaMethod() {
/* 106 */       return this.javaMethod;
/*     */     }
/*     */     
/*     */     public QName getOperationName() {
/* 110 */       return this.operationName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/WSDLOperationFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */