/*     */ package com.sun.xml.internal.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.server.AsyncProvider;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingHelper;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Provider;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.ServiceMode;
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
/*     */ final class ProviderEndpointModel<T>
/*     */ {
/*     */   final boolean isAsync;
/*     */   @NotNull
/*     */   final Service.Mode mode;
/*     */   @NotNull
/*     */   final Class datatype;
/*     */   @NotNull
/*     */   final Class implClass;
/*     */   
/*     */   ProviderEndpointModel(Class<T> implementorClass, WSBinding binding) {
/*  75 */     assert implementorClass != null;
/*  76 */     assert binding != null;
/*     */     
/*  78 */     this.implClass = implementorClass;
/*  79 */     this.mode = getServiceMode(implementorClass);
/*  80 */     Class otherClass = (binding instanceof javax.xml.ws.soap.SOAPBinding) ? SOAPMessage.class : DataSource.class;
/*     */     
/*  82 */     this.isAsync = AsyncProvider.class.isAssignableFrom(implementorClass);
/*     */ 
/*     */     
/*  85 */     Class<? extends Object> baseType = this.isAsync ? (Class)AsyncProvider.class : (Class)Provider.class;
/*  86 */     Type baseParam = BindingHelper.getBaseType(implementorClass, baseType);
/*  87 */     if (baseParam == null)
/*  88 */       throw new WebServiceException(ServerMessages.NOT_IMPLEMENT_PROVIDER(implementorClass.getName())); 
/*  89 */     if (!(baseParam instanceof ParameterizedType)) {
/*  90 */       throw new WebServiceException(ServerMessages.PROVIDER_NOT_PARAMETERIZED(implementorClass.getName()));
/*     */     }
/*  92 */     ParameterizedType pt = (ParameterizedType)baseParam;
/*  93 */     Type[] types = pt.getActualTypeArguments();
/*  94 */     if (!(types[0] instanceof Class))
/*  95 */       throw new WebServiceException(ServerMessages.PROVIDER_INVALID_PARAMETER_TYPE(implementorClass.getName(), types[0])); 
/*  96 */     this.datatype = (Class)types[0];
/*     */     
/*  98 */     if (this.mode == Service.Mode.PAYLOAD && this.datatype != Source.class)
/*     */     {
/*     */       
/* 101 */       throw new IllegalArgumentException("Illeagal combination - Mode.PAYLOAD and Provider<" + otherClass
/* 102 */           .getName() + ">");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Service.Mode getServiceMode(Class<?> c) {
/* 113 */     ServiceMode mode = c.<ServiceMode>getAnnotation(ServiceMode.class);
/* 114 */     return (mode == null) ? Service.Mode.PAYLOAD : mode.value();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/provider/ProviderEndpointModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */