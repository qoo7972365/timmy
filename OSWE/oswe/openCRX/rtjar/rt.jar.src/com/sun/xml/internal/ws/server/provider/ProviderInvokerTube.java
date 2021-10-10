/*    */ package com.sun.xml.internal.ws.server.provider;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.server.Container;
/*    */ import com.sun.xml.internal.ws.api.server.Invoker;
/*    */ import com.sun.xml.internal.ws.api.server.ProviderInvokerTubeFactory;
/*    */ import com.sun.xml.internal.ws.binding.SOAPBindingImpl;
/*    */ import com.sun.xml.internal.ws.server.InvokerTube;
/*    */ import javax.xml.ws.Provider;
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
/*    */ public abstract class ProviderInvokerTube<T>
/*    */   extends InvokerTube<Provider<T>>
/*    */ {
/*    */   protected ProviderArgumentsBuilder<T> argsBuilder;
/*    */   
/*    */   ProviderInvokerTube(Invoker invoker, ProviderArgumentsBuilder<T> argsBuilder) {
/* 49 */     super(invoker);
/* 50 */     this.argsBuilder = argsBuilder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> ProviderInvokerTube<T> create(Class<T> implType, WSBinding binding, Invoker invoker, Container container) {
/* 56 */     ProviderEndpointModel<T> model = new ProviderEndpointModel<>(implType, binding);
/* 57 */     ProviderArgumentsBuilder<?> argsBuilder = ProviderArgumentsBuilder.create(model, binding);
/* 58 */     if (binding instanceof SOAPBindingImpl)
/*    */     {
/* 60 */       ((SOAPBindingImpl)binding).setMode(model.mode);
/*    */     }
/*    */     
/* 63 */     return ProviderInvokerTubeFactory.create(null, container, implType, invoker, argsBuilder, model.isAsync);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/provider/ProviderInvokerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */