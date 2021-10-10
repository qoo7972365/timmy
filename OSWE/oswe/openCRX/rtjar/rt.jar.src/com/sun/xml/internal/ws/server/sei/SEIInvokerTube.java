/*    */ package com.sun.xml.internal.ws.server.sei;
/*    */ 
/*    */ import com.oracle.webservices.internal.api.databinding.JavaCallInfo;
/*    */ import com.oracle.webservices.internal.api.message.MessageContext;
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*    */ import com.sun.xml.internal.ws.api.server.Invoker;
/*    */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*    */ import com.sun.xml.internal.ws.server.InvokerTube;
/*    */ import com.sun.xml.internal.ws.wsdl.DispatchException;
/*    */ import java.lang.reflect.InvocationTargetException;
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
/*    */ public class SEIInvokerTube
/*    */   extends InvokerTube
/*    */ {
/*    */   private final WSBinding binding;
/*    */   private final AbstractSEIModelImpl model;
/*    */   
/*    */   public SEIInvokerTube(AbstractSEIModelImpl model, Invoker invoker, WSBinding binding) {
/* 56 */     super(invoker);
/* 57 */     this.binding = binding;
/* 58 */     this.model = model;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public NextAction processRequest(@NotNull Packet req) {
/* 67 */     JavaCallInfo call = this.model.getDatabinding().deserializeRequest((MessageContext)req);
/* 68 */     if (call.getException() == null) {
/*    */       try {
/* 70 */         if (req.getMessage().isOneWay(this.model.getPort()) && req.transportBackChannel != null) {
/* 71 */           req.transportBackChannel.close();
/*    */         }
/* 73 */         Object ret = getInvoker(req).invoke(req, call.getMethod(), call.getParameters());
/* 74 */         call.setReturnValue(ret);
/* 75 */       } catch (InvocationTargetException e) {
/* 76 */         call.setException(e);
/* 77 */       } catch (Exception e) {
/* 78 */         call.setException(e);
/*    */       } 
/* 80 */     } else if (call.getException() instanceof DispatchException) {
/* 81 */       DispatchException e = (DispatchException)call.getException();
/* 82 */       return doReturnWith(req.createServerResponse(e.fault, this.model.getPort(), null, this.binding));
/*    */     } 
/* 84 */     Packet res = (Packet)this.model.getDatabinding().serializeResponse(call);
/* 85 */     res = req.relateServerResponse(res, req.endpoint.getPort(), (SEIModel)this.model, req.endpoint.getBinding());
/* 86 */     assert res != null;
/* 87 */     return doReturnWith(res);
/*    */   }
/*    */   @NotNull
/*    */   public NextAction processResponse(@NotNull Packet response) {
/* 91 */     return doReturnWith(response);
/*    */   }
/*    */   @NotNull
/*    */   public NextAction processException(@NotNull Throwable t) {
/* 95 */     return doThrow(t);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/sei/SEIInvokerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */