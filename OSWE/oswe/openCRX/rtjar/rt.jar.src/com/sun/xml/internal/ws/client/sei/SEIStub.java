/*     */ package com.sun.xml.internal.ws.client.sei;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.databinding.Databinding;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.Headers;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.MEP;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.AsyncResponseImpl;
/*     */ import com.sun.xml.internal.ws.client.RequestContext;
/*     */ import com.sun.xml.internal.ws.client.ResponseContextReceiver;
/*     */ import com.sun.xml.internal.ws.client.Stub;
/*     */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.model.SOAPSEIModel;
/*     */ import com.sun.xml.internal.ws.wsdl.OperationDispatcher;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.HashMap;
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
/*     */ public final class SEIStub
/*     */   extends Stub
/*     */   implements InvocationHandler
/*     */ {
/*     */   Databinding databinding;
/*     */   public final SOAPSEIModel seiModel;
/*     */   public final SOAPVersion soapVersion;
/*     */   private final Map<Method, MethodHandler> methodHandlers;
/*     */   
/*     */   @Deprecated
/*     */   public SEIStub(WSServiceDelegate owner, BindingImpl binding, SOAPSEIModel seiModel, Tube master, WSEndpointReference epr) {
/*  75 */     super(owner, master, binding, seiModel.getPort(), seiModel.getPort().getAddress(), epr);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.methodHandlers = new HashMap<>(); this.seiModel = seiModel; this.soapVersion = binding.getSOAPVersion(); this.databinding = seiModel.getDatabinding(); initMethodHandlers(); } public SEIStub(WSPortInfo portInfo, BindingImpl binding, SOAPSEIModel seiModel, WSEndpointReference epr) { super(portInfo, binding, seiModel.getPort().getAddress(), epr); this.methodHandlers = new HashMap<>(); this.seiModel = seiModel; this.soapVersion = binding.getSOAPVersion(); this.databinding = seiModel.getDatabinding(); initMethodHandlers(); }
/*     */   private void initMethodHandlers() { Map<WSDLBoundOperation, JavaMethodImpl> syncs = new HashMap<>(); for (JavaMethodImpl m : this.seiModel.getJavaMethods()) { if (!(m.getMEP()).isAsync) { SyncMethodHandler handler = new SyncMethodHandler(this, m); syncs.put(m.getOperation(), m); this.methodHandlers.put(m.getMethod(), handler); }  }  for (JavaMethodImpl jm : this.seiModel.getJavaMethods()) { JavaMethodImpl sync = syncs.get(jm.getOperation()); if (jm.getMEP() == MEP.ASYNC_CALLBACK) { Method m = jm.getMethod(); CallbackMethodHandler handler = new CallbackMethodHandler(this, m, (m.getParameterTypes()).length - 1); this.methodHandlers.put(m, handler); }  if (jm.getMEP() == MEP.ASYNC_POLL) { Method m = jm.getMethod(); PollingMethodHandler handler = new PollingMethodHandler(this, m); this.methodHandlers.put(m, handler); }
/*     */        }
/* 142 */      } public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { validateInputs(proxy, method);
/* 143 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 145 */       MethodHandler handler = this.methodHandlers.get(method);
/* 146 */       if (handler != null) {
/* 147 */         return handler.invoke(proxy, args);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 162 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     }  }
/*     */   @Nullable
/*     */   public OperationDispatcher getOperationDispatcher() { if (this.operationDispatcher == null && this.wsdlPort != null)
/*     */       this.operationDispatcher = new OperationDispatcher(this.wsdlPort, (WSBinding)this.binding, (SEIModel)this.seiModel); 
/* 167 */     return this.operationDispatcher; } private void validateInputs(Object proxy, Method method) { if (proxy == null || !Proxy.isProxyClass(proxy.getClass())) {
/* 168 */       throw new IllegalStateException("Passed object is not proxy!");
/*     */     }
/* 170 */     Class<?> declaringClass = method.getDeclaringClass();
/* 171 */     if (method == null || declaringClass == null || 
/* 172 */       Modifier.isStatic(method.getModifiers())) {
/* 173 */       throw new IllegalStateException("Invoking static method is not allowed!");
/*     */     } }
/*     */ 
/*     */   
/*     */   public final Packet doProcess(Packet request, RequestContext rc, ResponseContextReceiver receiver) {
/* 178 */     return process(request, rc, receiver);
/*     */   }
/*     */   
/*     */   public final void doProcessAsync(AsyncResponseImpl<?> receiver, Packet request, RequestContext rc, Fiber.CompletionCallback callback) {
/* 182 */     processAsync(receiver, request, rc, callback);
/*     */   }
/*     */   @NotNull
/*     */   protected final QName getPortName() {
/* 186 */     return this.wsdlPort.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOutboundHeaders(Object... headers) {
/* 191 */     if (headers == null)
/* 192 */       throw new IllegalArgumentException(); 
/* 193 */     Header[] hl = new Header[headers.length];
/* 194 */     for (int i = 0; i < hl.length; i++) {
/* 195 */       if (headers[i] == null)
/* 196 */         throw new IllegalArgumentException(); 
/* 197 */       hl[i] = Headers.create(this.seiModel.getBindingContext(), headers[i]);
/*     */     } 
/* 199 */     setOutboundHeaders(hl);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/SEIStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */