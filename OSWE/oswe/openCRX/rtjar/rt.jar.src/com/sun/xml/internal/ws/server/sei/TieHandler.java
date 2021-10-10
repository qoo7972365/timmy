/*     */ package com.sun.xml.internal.ws.server.sei;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.JavaCallInfo;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.databinding.EndpointCallBridge;
/*     */ import com.sun.xml.internal.ws.api.databinding.JavaCallInfo;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageContextFactory;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import com.sun.xml.internal.ws.model.WrapperParameter;
/*     */ import com.sun.xml.internal.ws.wsdl.DispatchException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TieHandler
/*     */   implements EndpointCallBridge
/*     */ {
/*     */   private final SOAPVersion soapVersion;
/*     */   private final Method method;
/*     */   private final int noOfArgs;
/*     */   private final JavaMethodImpl javaMethodModel;
/*     */   private final Boolean isOneWay;
/*     */   private final EndpointArgumentsBuilder argumentsBuilder;
/*     */   private final EndpointResponseMessageBuilder bodyBuilder;
/*     */   private final MessageFiller[] outFillers;
/*     */   protected MessageContextFactory packetFactory;
/*     */   
/*     */   public TieHandler(JavaMethodImpl method, WSBinding binding, MessageContextFactory mcf) {
/*  97 */     this.soapVersion = binding.getSOAPVersion();
/*  98 */     this.method = method.getMethod();
/*  99 */     this.javaMethodModel = method;
/* 100 */     this.argumentsBuilder = createArgumentsBuilder();
/* 101 */     List<MessageFiller> fillers = new ArrayList<>();
/* 102 */     this.bodyBuilder = createResponseMessageBuilder(fillers);
/* 103 */     this.outFillers = fillers.<MessageFiller>toArray(new MessageFiller[fillers.size()]);
/* 104 */     this.isOneWay = Boolean.valueOf(method.getMEP().isOneWay());
/* 105 */     this.noOfArgs = (this.method.getParameterTypes()).length;
/* 106 */     this.packetFactory = mcf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EndpointArgumentsBuilder createArgumentsBuilder() {
/* 117 */     List<ParameterImpl> rp = this.javaMethodModel.getRequestParameters();
/* 118 */     List<EndpointArgumentsBuilder> builders = new ArrayList<>();
/*     */     
/* 120 */     for (ParameterImpl param : rp) {
/* 121 */       EndpointValueSetter setter = EndpointValueSetter.get(param);
/* 122 */       switch ((param.getInBinding()).kind) {
/*     */         case SOAP_11:
/* 124 */           if (param.isWrapperStyle()) {
/* 125 */             if (param.getParent().getBinding().isRpcLit()) {
/* 126 */               builders.add(new EndpointArgumentsBuilder.RpcLit((WrapperParameter)param)); continue;
/*     */             } 
/* 128 */             builders.add(new EndpointArgumentsBuilder.DocLit((WrapperParameter)param, WebParam.Mode.OUT)); continue;
/*     */           } 
/* 130 */           builders.add(new EndpointArgumentsBuilder.Body(param.getXMLBridge(), setter));
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 134 */           builders.add(new EndpointArgumentsBuilder.Header(this.soapVersion, param, setter));
/*     */           continue;
/*     */         case null:
/* 137 */           builders.add(EndpointArgumentsBuilder.AttachmentBuilder.createAttachmentBuilder(param, setter));
/*     */           continue;
/*     */         case null:
/* 140 */           builders.add(new EndpointArgumentsBuilder.NullSetter(setter, 
/* 141 */                 EndpointArgumentsBuilder.getVMUninitializedValue((param.getTypeInfo()).type)));
/*     */           continue;
/*     */       } 
/* 144 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 149 */     List<ParameterImpl> resp = this.javaMethodModel.getResponseParameters();
/* 150 */     for (ParameterImpl param : resp) {
/* 151 */       if (param.isWrapperStyle()) {
/* 152 */         WrapperParameter wp = (WrapperParameter)param;
/* 153 */         List<ParameterImpl> children = wp.getWrapperChildren();
/* 154 */         for (ParameterImpl p : children) {
/* 155 */           if (p.isOUT() && p.getIndex() != -1) {
/* 156 */             EndpointValueSetter setter = EndpointValueSetter.get(p);
/* 157 */             builders.add(new EndpointArgumentsBuilder.NullSetter(setter, null));
/*     */           } 
/*     */         }  continue;
/* 160 */       }  if (param.isOUT() && param.getIndex() != -1) {
/* 161 */         EndpointValueSetter setter = EndpointValueSetter.get(param);
/* 162 */         builders.add(new EndpointArgumentsBuilder.NullSetter(setter, null));
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     switch (builders.size())
/*     */     { case 0:
/* 168 */         argsBuilder = EndpointArgumentsBuilder.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 176 */         return argsBuilder;case 1: argsBuilder = builders.get(0); return argsBuilder; }  EndpointArgumentsBuilder argsBuilder = new EndpointArgumentsBuilder.Composite(builders); return argsBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EndpointResponseMessageBuilder createResponseMessageBuilder(List<MessageFiller> fillers) {
/* 184 */     EndpointResponseMessageBuilder tmpBodyBuilder = null;
/* 185 */     List<ParameterImpl> rp = this.javaMethodModel.getResponseParameters();
/*     */     
/* 187 */     for (ParameterImpl param : rp) {
/* 188 */       ValueGetter getter = ValueGetter.get(param);
/*     */       
/* 190 */       switch ((param.getOutBinding()).kind) {
/*     */         case SOAP_11:
/* 192 */           if (param.isWrapperStyle()) {
/* 193 */             if (param.getParent().getBinding().isRpcLit()) {
/* 194 */               tmpBodyBuilder = new EndpointResponseMessageBuilder.RpcLit((WrapperParameter)param, this.soapVersion);
/*     */               continue;
/*     */             } 
/* 197 */             tmpBodyBuilder = new EndpointResponseMessageBuilder.DocLit((WrapperParameter)param, this.soapVersion);
/*     */             
/*     */             continue;
/*     */           } 
/* 201 */           tmpBodyBuilder = new EndpointResponseMessageBuilder.Bare(param, this.soapVersion);
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 205 */           fillers.add(new MessageFiller.Header(param.getIndex(), param.getXMLBridge(), getter));
/*     */           continue;
/*     */         case null:
/* 208 */           fillers.add(MessageFiller.AttachmentFiller.createAttachmentFiller(param, getter));
/*     */           continue;
/*     */         case null:
/*     */           continue;
/*     */       } 
/* 213 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 217 */     if (tmpBodyBuilder == null)
/*     */     
/* 219 */     { switch (this.soapVersion)
/*     */       { case SOAP_11:
/* 221 */           tmpBodyBuilder = EndpointResponseMessageBuilder.EMPTY_SOAP11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 230 */           return tmpBodyBuilder;case SOAP_12: tmpBodyBuilder = EndpointResponseMessageBuilder.EMPTY_SOAP12; return tmpBodyBuilder; }  throw new AssertionError(); }  return tmpBodyBuilder;
/*     */   }
/*     */   
/*     */   public Object[] readRequest(Message reqMsg) {
/* 234 */     Object[] args = new Object[this.noOfArgs];
/*     */     try {
/* 236 */       this.argumentsBuilder.readRequest(reqMsg, args);
/* 237 */     } catch (JAXBException e) {
/* 238 */       throw new WebServiceException(e);
/* 239 */     } catch (XMLStreamException e) {
/* 240 */       throw new WebServiceException(e);
/*     */     } 
/* 242 */     return args;
/*     */   }
/*     */   
/*     */   public Message createResponse(JavaCallInfo call) {
/*     */     Message responseMessage;
/* 247 */     if (call.getException() == null) {
/* 248 */       responseMessage = this.isOneWay.booleanValue() ? null : createResponseMessage(call.getParameters(), call.getReturnValue());
/*     */     } else {
/* 250 */       Throwable e = call.getException();
/* 251 */       Throwable serviceException = getServiceException(e);
/* 252 */       if (e instanceof java.lang.reflect.InvocationTargetException || serviceException != null) {
/*     */ 
/*     */         
/* 255 */         if (serviceException != null) {
/*     */           
/* 257 */           LOGGER.log(Level.FINE, serviceException.getMessage(), serviceException);
/* 258 */           responseMessage = SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, this.javaMethodModel
/* 259 */               .getCheckedException(serviceException.getClass()), serviceException);
/*     */         } else {
/* 261 */           Throwable cause = e.getCause();
/* 262 */           if (cause instanceof javax.xml.ws.ProtocolException) {
/*     */             
/* 264 */             LOGGER.log(Level.FINE, cause.getMessage(), cause);
/*     */           } else {
/*     */             
/* 267 */             LOGGER.log(Level.SEVERE, cause.getMessage(), cause);
/*     */           } 
/* 269 */           responseMessage = SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, cause);
/*     */         } 
/* 271 */       } else if (e instanceof DispatchException) {
/* 272 */         responseMessage = ((DispatchException)e).fault;
/*     */       } else {
/* 274 */         LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 275 */         responseMessage = SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 280 */     return responseMessage;
/*     */   }
/*     */   
/*     */   Throwable getServiceException(Throwable throwable) {
/* 284 */     if (this.javaMethodModel.getCheckedException(throwable.getClass()) != null) return throwable; 
/* 285 */     if (throwable.getCause() != null) {
/* 286 */       Throwable cause = throwable.getCause();
/*     */       
/* 288 */       if (this.javaMethodModel.getCheckedException(cause.getClass()) != null) return cause;
/*     */     
/*     */     } 
/*     */     
/* 292 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Message createResponseMessage(Object[] args, Object returnValue) {
/* 301 */     Message msg = this.bodyBuilder.createMessage(args, returnValue);
/*     */     
/* 303 */     for (MessageFiller filler : this.outFillers) {
/* 304 */       filler.fillIn(args, returnValue, msg);
/*     */     }
/* 306 */     return msg;
/*     */   }
/*     */   
/*     */   public Method getMethod() {
/* 310 */     return this.method;
/*     */   }
/*     */   
/* 313 */   private static final Logger LOGGER = Logger.getLogger(TieHandler.class.getName());
/*     */ 
/*     */   
/*     */   public JavaCallInfo deserializeRequest(Packet req) {
/* 317 */     JavaCallInfo call = new JavaCallInfo();
/* 318 */     call.setMethod(getMethod());
/* 319 */     Object[] args = readRequest(req.getMessage());
/* 320 */     call.setParameters(args);
/* 321 */     return (JavaCallInfo)call;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet serializeResponse(JavaCallInfo call) {
/* 326 */     Message msg = createResponse(call);
/* 327 */     Packet p = (msg == null) ? (Packet)this.packetFactory.createContext() : (Packet)this.packetFactory.createContext(msg);
/* 328 */     p.setState(Packet.State.ServerResponse);
/* 329 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaMethod getOperationModel() {
/* 334 */     return (JavaMethod)this.javaMethodModel;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/sei/TieHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */