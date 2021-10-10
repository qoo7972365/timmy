/*     */ package com.sun.xml.internal.ws.client.sei;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import com.sun.xml.internal.ws.model.WrapperParameter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ abstract class SEIMethodHandler
/*     */   extends MethodHandler
/*     */ {
/*     */   private BodyBuilder bodyBuilder;
/*     */   private MessageFiller[] inFillers;
/*     */   protected String soapAction;
/*     */   protected boolean isOneWay;
/*     */   protected JavaMethodImpl javaMethod;
/*     */   protected Map<QName, CheckedExceptionImpl> checkedExceptions;
/*     */   
/*     */   SEIMethodHandler(SEIStub owner) {
/*  76 */     super(owner, null);
/*     */   }
/*     */   
/*     */   SEIMethodHandler(SEIStub owner, JavaMethodImpl method) {
/*  80 */     super(owner, null);
/*     */ 
/*     */     
/*  83 */     this.checkedExceptions = new HashMap<>();
/*  84 */     for (CheckedExceptionImpl ce : method.getCheckedExceptions()) {
/*  85 */       this.checkedExceptions.put((ce.getBond().getTypeInfo()).tagName, ce);
/*     */     }
/*     */     
/*  88 */     if (method.getInputAction() != null && !method.getBinding().getSOAPAction().equals("")) {
/*  89 */       this.soapAction = method.getInputAction();
/*     */     } else {
/*  91 */       this.soapAction = method.getBinding().getSOAPAction();
/*     */     } 
/*  93 */     this.javaMethod = method;
/*     */ 
/*     */     
/*  96 */     List<ParameterImpl> rp = method.getRequestParameters();
/*     */     
/*  98 */     BodyBuilder tmpBodyBuilder = null;
/*  99 */     List<MessageFiller> fillers = new ArrayList<>();
/*     */     
/* 101 */     for (ParameterImpl param : rp) {
/* 102 */       ValueGetter getter = getValueGetterFactory().get(param);
/*     */       
/* 104 */       switch ((param.getInBinding()).kind) {
/*     */         case SOAP_11:
/* 106 */           if (param.isWrapperStyle()) {
/* 107 */             if (param.getParent().getBinding().isRpcLit()) {
/* 108 */               tmpBodyBuilder = new BodyBuilder.RpcLit((WrapperParameter)param, owner.soapVersion, getValueGetterFactory()); continue;
/*     */             } 
/* 110 */             tmpBodyBuilder = new BodyBuilder.DocLit((WrapperParameter)param, owner.soapVersion, getValueGetterFactory()); continue;
/*     */           } 
/* 112 */           tmpBodyBuilder = new BodyBuilder.Bare(param, owner.soapVersion, getter);
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 116 */           fillers.add(new MessageFiller.Header(param
/* 117 */                 .getIndex(), param
/* 118 */                 .getXMLBridge(), getter));
/*     */           continue;
/*     */         
/*     */         case null:
/* 122 */           fillers.add(MessageFiller.AttachmentFiller.createAttachmentFiller(param, getter));
/*     */           continue;
/*     */         case null:
/*     */           continue;
/*     */       } 
/* 127 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 131 */     if (tmpBodyBuilder == null)
/*     */     {
/* 133 */       switch (owner.soapVersion) {
/*     */         case SOAP_11:
/* 135 */           tmpBodyBuilder = BodyBuilder.EMPTY_SOAP11;
/*     */           break;
/*     */         case SOAP_12:
/* 138 */           tmpBodyBuilder = BodyBuilder.EMPTY_SOAP12;
/*     */           break;
/*     */         default:
/* 141 */           throw new AssertionError();
/*     */       } 
/*     */     
/*     */     }
/* 145 */     this.bodyBuilder = tmpBodyBuilder;
/* 146 */     this.inFillers = fillers.<MessageFiller>toArray(new MessageFiller[fillers.size()]);
/*     */ 
/*     */     
/* 149 */     this.isOneWay = method.getMEP().isOneWay();
/*     */   }
/*     */ 
/*     */   
/*     */   ResponseBuilder buildResponseBuilder(JavaMethodImpl method, ValueSetterFactory setterFactory) {
/* 154 */     List<ParameterImpl> rp = method.getResponseParameters();
/* 155 */     List<ResponseBuilder> builders = new ArrayList<>();
/*     */     
/* 157 */     for (ParameterImpl param : rp) {
/*     */       ValueSetter setter;
/* 159 */       switch ((param.getOutBinding()).kind) {
/*     */         case SOAP_11:
/* 161 */           if (param.isWrapperStyle()) {
/* 162 */             if (param.getParent().getBinding().isRpcLit()) {
/* 163 */               builders.add(new ResponseBuilder.RpcLit((WrapperParameter)param, setterFactory)); continue;
/*     */             } 
/* 165 */             builders.add(new ResponseBuilder.DocLit((WrapperParameter)param, setterFactory)); continue;
/*     */           } 
/* 167 */           setter = setterFactory.get(param);
/* 168 */           builders.add(new ResponseBuilder.Body(param.getXMLBridge(), setter));
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 172 */           setter = setterFactory.get(param);
/* 173 */           builders.add(new ResponseBuilder.Header(this.owner.soapVersion, param, setter));
/*     */           continue;
/*     */         case null:
/* 176 */           setter = setterFactory.get(param);
/* 177 */           builders.add(ResponseBuilder.AttachmentBuilder.createAttachmentBuilder(param, setter));
/*     */           continue;
/*     */         case null:
/* 180 */           setter = setterFactory.get(param);
/* 181 */           builders.add(new ResponseBuilder.NullSetter(setter, 
/* 182 */                 ResponseBuilder.getVMUninitializedValue((param.getTypeInfo()).type)));
/*     */           continue;
/*     */       } 
/* 185 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 189 */     switch (builders.size())
/*     */     { case 0:
/* 191 */         rb = ResponseBuilder.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 199 */         return rb;case 1: rb = builders.get(0); return rb; }  ResponseBuilder rb = new ResponseBuilder.Composite(builders); return rb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Message createRequestMessage(Object[] args) {
/* 209 */     Message msg = this.bodyBuilder.createMessage(args);
/*     */     
/* 211 */     for (MessageFiller filler : this.inFillers) {
/* 212 */       filler.fillIn(args, msg);
/*     */     }
/* 214 */     return msg;
/*     */   }
/*     */   
/*     */   abstract ValueGetterFactory getValueGetterFactory();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/SEIMethodHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */