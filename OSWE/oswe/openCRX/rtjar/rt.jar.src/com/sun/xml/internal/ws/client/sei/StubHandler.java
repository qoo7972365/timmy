/*     */ package com.sun.xml.internal.ws.client.sei;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.JavaCallInfo;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.databinding.ClientCallBridge;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageContextFactory;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import com.sun.xml.internal.ws.model.WrapperParameter;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class StubHandler
/*     */   implements ClientCallBridge
/*     */ {
/*     */   private final BodyBuilder bodyBuilder;
/*     */   private final MessageFiller[] inFillers;
/*     */   protected final String soapAction;
/*     */   protected final boolean isOneWay;
/*     */   protected final JavaMethodImpl javaMethod;
/*     */   protected final Map<QName, CheckedExceptionImpl> checkedExceptions;
/*  80 */   protected SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */   
/*     */   protected ResponseBuilder responseBuilder;
/*     */   protected MessageContextFactory packetFactory;
/*     */   
/*     */   public StubHandler(JavaMethodImpl method, MessageContextFactory mcf) {
/*  86 */     this.checkedExceptions = new HashMap<>();
/*  87 */     for (CheckedExceptionImpl ce : method.getCheckedExceptions()) {
/*  88 */       this.checkedExceptions.put((ce.getBond().getTypeInfo()).tagName, ce);
/*     */     }
/*     */     
/*  91 */     String soapActionFromBinding = method.getBinding().getSOAPAction();
/*  92 */     if (method.getInputAction() != null && soapActionFromBinding != null && !soapActionFromBinding.equals("")) {
/*  93 */       this.soapAction = method.getInputAction();
/*     */     } else {
/*  95 */       this.soapAction = soapActionFromBinding;
/*     */     } 
/*  97 */     this.javaMethod = method;
/*  98 */     this.packetFactory = mcf;
/*     */     
/* 100 */     this.soapVersion = this.javaMethod.getBinding().getSOAPVersion();
/*     */ 
/*     */     
/* 103 */     List<ParameterImpl> rp = method.getRequestParameters();
/*     */     
/* 105 */     BodyBuilder bodyBuilder = null;
/* 106 */     List<MessageFiller> fillers = new ArrayList<>();
/*     */     
/* 108 */     for (ParameterImpl param : rp) {
/* 109 */       ValueGetter getter = getValueGetterFactory().get(param);
/*     */       
/* 111 */       switch ((param.getInBinding()).kind) {
/*     */         case SOAP_11:
/* 113 */           if (param.isWrapperStyle()) {
/* 114 */             if (param.getParent().getBinding().isRpcLit()) {
/* 115 */               bodyBuilder = new BodyBuilder.RpcLit((WrapperParameter)param, this.soapVersion, getValueGetterFactory()); continue;
/*     */             } 
/* 117 */             bodyBuilder = new BodyBuilder.DocLit((WrapperParameter)param, this.soapVersion, getValueGetterFactory()); continue;
/*     */           } 
/* 119 */           bodyBuilder = new BodyBuilder.Bare(param, this.soapVersion, getter);
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 123 */           fillers.add(new MessageFiller.Header(param
/* 124 */                 .getIndex(), param
/* 125 */                 .getXMLBridge(), getter));
/*     */           continue;
/*     */         
/*     */         case null:
/* 129 */           fillers.add(MessageFiller.AttachmentFiller.createAttachmentFiller(param, getter));
/*     */           continue;
/*     */         case null:
/*     */           continue;
/*     */       } 
/* 134 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 138 */     if (bodyBuilder == null)
/*     */     {
/* 140 */       switch (this.soapVersion) {
/*     */         case SOAP_11:
/* 142 */           bodyBuilder = BodyBuilder.EMPTY_SOAP11;
/*     */           break;
/*     */         case SOAP_12:
/* 145 */           bodyBuilder = BodyBuilder.EMPTY_SOAP12;
/*     */           break;
/*     */         default:
/* 148 */           throw new AssertionError();
/*     */       } 
/*     */     
/*     */     }
/* 152 */     this.bodyBuilder = bodyBuilder;
/* 153 */     this.inFillers = fillers.<MessageFiller>toArray(new MessageFiller[fillers.size()]);
/*     */ 
/*     */     
/* 156 */     this.isOneWay = method.getMEP().isOneWay();
/* 157 */     this.responseBuilder = buildResponseBuilder(method, ValueSetterFactory.SYNC);
/*     */   }
/*     */ 
/*     */   
/*     */   ResponseBuilder buildResponseBuilder(JavaMethodImpl method, ValueSetterFactory setterFactory) {
/* 162 */     List<ParameterImpl> rp = method.getResponseParameters();
/* 163 */     List<ResponseBuilder> builders = new ArrayList<>();
/*     */     
/* 165 */     for (ParameterImpl param : rp) {
/*     */       ValueSetter setter;
/* 167 */       switch ((param.getOutBinding()).kind) {
/*     */         case SOAP_11:
/* 169 */           if (param.isWrapperStyle()) {
/* 170 */             if (param.getParent().getBinding().isRpcLit()) {
/* 171 */               builders.add(new ResponseBuilder.RpcLit((WrapperParameter)param, setterFactory)); continue;
/*     */             } 
/* 173 */             builders.add(new ResponseBuilder.DocLit((WrapperParameter)param, setterFactory)); continue;
/*     */           } 
/* 175 */           setter = setterFactory.get(param);
/* 176 */           builders.add(new ResponseBuilder.Body(param.getXMLBridge(), setter));
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 180 */           setter = setterFactory.get(param);
/* 181 */           builders.add(new ResponseBuilder.Header(this.soapVersion, param, setter));
/*     */           continue;
/*     */         case null:
/* 184 */           setter = setterFactory.get(param);
/* 185 */           builders.add(ResponseBuilder.AttachmentBuilder.createAttachmentBuilder(param, setter));
/*     */           continue;
/*     */         case null:
/* 188 */           setter = setterFactory.get(param);
/* 189 */           builders.add(new ResponseBuilder.NullSetter(setter, 
/* 190 */                 ResponseBuilder.getVMUninitializedValue((param.getTypeInfo()).type)));
/*     */           continue;
/*     */       } 
/* 193 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 197 */     switch (builders.size())
/*     */     { case 0:
/* 199 */         rb = ResponseBuilder.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 207 */         return rb;case 1: rb = builders.get(0); return rb; }  ResponseBuilder rb = new ResponseBuilder.Composite(builders); return rb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet createRequestPacket(JavaCallInfo args) {
/* 217 */     Message msg = this.bodyBuilder.createMessage(args.getParameters());
/*     */     
/* 219 */     for (MessageFiller filler : this.inFillers) filler.fillIn(args.getParameters(), msg);
/*     */     
/* 221 */     Packet req = (Packet)this.packetFactory.createContext(msg);
/* 222 */     req.setState(Packet.State.ClientRequest);
/* 223 */     req.soapAction = this.soapAction;
/* 224 */     req.expectReply = Boolean.valueOf(!this.isOneWay);
/* 225 */     req.getMessage().assertOneWay(this.isOneWay);
/* 226 */     req.setWSDLOperation(getOperationName());
/* 227 */     return req;
/*     */   }
/*     */   
/*     */   ValueGetterFactory getValueGetterFactory() {
/* 231 */     return ValueGetterFactory.SYNC;
/*     */   }
/*     */   
/*     */   public JavaCallInfo readResponse(Packet p, JavaCallInfo call) throws Throwable {
/* 235 */     Message msg = p.getMessage();
/* 236 */     if (msg.isFault()) {
/* 237 */       SOAPFaultBuilder faultBuilder = SOAPFaultBuilder.create(msg);
/* 238 */       Throwable t = faultBuilder.createException(this.checkedExceptions);
/* 239 */       call.setException(t);
/* 240 */       throw t;
/*     */     } 
/* 242 */     initArgs(call.getParameters());
/* 243 */     Object ret = this.responseBuilder.readResponse(msg, call.getParameters());
/* 244 */     call.setReturnValue(ret);
/* 245 */     return call;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getOperationName() {
/* 251 */     return this.javaMethod.getOperationQName();
/*     */   }
/*     */   
/*     */   public String getSoapAction() {
/* 255 */     return this.soapAction;
/*     */   }
/*     */   
/*     */   public boolean isOneWay() {
/* 259 */     return this.isOneWay;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initArgs(Object[] args) throws Exception {}
/*     */   
/*     */   public Method getMethod() {
/* 266 */     return this.javaMethod.getMethod();
/*     */   }
/*     */   
/*     */   public JavaMethod getOperationModel() {
/* 270 */     return (JavaMethod)this.javaMethod;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/StubHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */