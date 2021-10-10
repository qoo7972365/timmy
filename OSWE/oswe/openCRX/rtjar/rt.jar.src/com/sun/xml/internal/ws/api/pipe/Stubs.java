/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.internal.ws.client.dispatch.DataSourceDispatch;
/*     */ import com.sun.xml.internal.ws.client.dispatch.DispatchImpl;
/*     */ import com.sun.xml.internal.ws.client.dispatch.JAXBDispatch;
/*     */ import com.sun.xml.internal.ws.client.dispatch.MessageDispatch;
/*     */ import com.sun.xml.internal.ws.client.dispatch.PacketDispatch;
/*     */ import com.sun.xml.internal.ws.client.dispatch.SOAPMessageDispatch;
/*     */ import com.sun.xml.internal.ws.client.sei.SEIStub;
/*     */ import com.sun.xml.internal.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.internal.ws.model.SOAPSEIModel;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.Service;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Stubs
/*     */ {
/*     */   @Deprecated
/*     */   public static Dispatch<SOAPMessage> createSAAJDispatch(QName portName, WSService owner, WSBinding binding, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 120 */     DispatchImpl.checkValidSOAPMessageDispatch(binding, mode);
/* 121 */     return (Dispatch<SOAPMessage>)new SOAPMessageDispatch(portName, mode, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<SOAPMessage> createSAAJDispatch(WSPortInfo portInfo, WSBinding binding, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 133 */     DispatchImpl.checkValidSOAPMessageDispatch(binding, mode);
/* 134 */     return (Dispatch<SOAPMessage>)new SOAPMessageDispatch(portInfo, mode, (BindingImpl)binding, epr);
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
/*     */   @Deprecated
/*     */   public static Dispatch<DataSource> createDataSourceDispatch(QName portName, WSService owner, WSBinding binding, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 147 */     DispatchImpl.checkValidDataSourceDispatch(binding, mode);
/* 148 */     return (Dispatch<DataSource>)new DataSourceDispatch(portName, mode, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<DataSource> createDataSourceDispatch(WSPortInfo portInfo, WSBinding binding, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 160 */     DispatchImpl.checkValidDataSourceDispatch(binding, mode);
/* 161 */     return (Dispatch<DataSource>)new DataSourceDispatch(portInfo, mode, (BindingImpl)binding, epr);
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
/*     */   @Deprecated
/*     */   public static Dispatch<Source> createSourceDispatch(QName portName, WSService owner, WSBinding binding, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 174 */     return DispatchImpl.createSourceDispatch(portName, mode, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<Source> createSourceDispatch(WSPortInfo portInfo, WSBinding binding, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 186 */     return DispatchImpl.createSourceDispatch(portInfo, mode, (BindingImpl)binding, epr);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Dispatch<T> createDispatch(QName portName, WSService owner, WSBinding binding, Class<T> clazz, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 216 */     if (clazz == SOAPMessage.class)
/* 217 */       return (Dispatch)createSAAJDispatch(portName, owner, binding, mode, next, epr); 
/* 218 */     if (clazz == Source.class)
/* 219 */       return (Dispatch)createSourceDispatch(portName, owner, binding, mode, next, epr); 
/* 220 */     if (clazz == DataSource.class)
/* 221 */       return (Dispatch)createDataSourceDispatch(portName, owner, binding, mode, next, epr); 
/* 222 */     if (clazz == Message.class) {
/* 223 */       if (mode == Service.Mode.MESSAGE) {
/* 224 */         return (Dispatch)createMessageDispatch(portName, owner, binding, next, epr);
/*     */       }
/* 226 */       throw new WebServiceException(mode + " not supported with Dispatch<Message>");
/* 227 */     }  if (clazz == Packet.class) {
/* 228 */       return (Dispatch)createPacketDispatch(portName, owner, binding, next, epr);
/*     */     }
/* 230 */     throw new WebServiceException("Unknown class type " + clazz.getName());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Dispatch<T> createDispatch(WSPortInfo portInfo, WSService owner, WSBinding binding, Class<T> clazz, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 257 */     if (clazz == SOAPMessage.class)
/* 258 */       return (Dispatch)createSAAJDispatch(portInfo, binding, mode, epr); 
/* 259 */     if (clazz == Source.class)
/* 260 */       return (Dispatch)createSourceDispatch(portInfo, binding, mode, epr); 
/* 261 */     if (clazz == DataSource.class)
/* 262 */       return (Dispatch)createDataSourceDispatch(portInfo, binding, mode, epr); 
/* 263 */     if (clazz == Message.class) {
/* 264 */       if (mode == Service.Mode.MESSAGE) {
/* 265 */         return (Dispatch)createMessageDispatch(portInfo, binding, epr);
/*     */       }
/* 267 */       throw new WebServiceException(mode + " not supported with Dispatch<Message>");
/* 268 */     }  if (clazz == Packet.class) {
/* 269 */       if (mode == Service.Mode.MESSAGE) {
/* 270 */         return (Dispatch)createPacketDispatch(portInfo, binding, epr);
/*     */       }
/* 272 */       throw new WebServiceException(mode + " not supported with Dispatch<Packet>");
/*     */     } 
/* 274 */     throw new WebServiceException("Unknown class type " + clazz.getName());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Dispatch<Object> createJAXBDispatch(QName portName, WSService owner, WSBinding binding, JAXBContext jaxbContext, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 301 */     return (Dispatch<Object>)new JAXBDispatch(portName, jaxbContext, mode, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<Object> createJAXBDispatch(WSPortInfo portInfo, WSBinding binding, JAXBContext jaxbContext, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 318 */     return (Dispatch<Object>)new JAXBDispatch(portInfo, jaxbContext, mode, (BindingImpl)binding, epr);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Dispatch<Message> createMessageDispatch(QName portName, WSService owner, WSBinding binding, Tube next, @Nullable WSEndpointReference epr) {
/* 341 */     return (Dispatch<Message>)new MessageDispatch(portName, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   
/*     */   public static Dispatch<Message> createMessageDispatch(WSPortInfo portInfo, WSBinding binding, @Nullable WSEndpointReference epr) {
/* 359 */     return (Dispatch<Message>)new MessageDispatch(portInfo, (BindingImpl)binding, epr);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dispatch<Packet> createPacketDispatch(QName portName, WSService owner, WSBinding binding, Tube next, @Nullable WSEndpointReference epr) {
/* 379 */     return (Dispatch<Packet>)new PacketDispatch(portName, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<Packet> createPacketDispatch(WSPortInfo portInfo, WSBinding binding, @Nullable WSEndpointReference epr) {
/* 396 */     return (Dispatch<Packet>)new PacketDispatch(portInfo, (BindingImpl)binding, epr);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T createPortProxy(WSService service, WSBinding binding, SEIModel model, Class<T> portInterface, Tube next, @Nullable WSEndpointReference epr) {
/* 420 */     SEIStub ps = new SEIStub((WSServiceDelegate)service, (BindingImpl)binding, (SOAPSEIModel)model, next, epr);
/* 421 */     return portInterface.cast(
/* 422 */         Proxy.newProxyInstance(portInterface.getClassLoader(), new Class[] { portInterface, WSBindingProvider.class }, (InvocationHandler)ps));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T createPortProxy(WSPortInfo portInfo, WSBinding binding, SEIModel model, Class<T> portInterface, @Nullable WSEndpointReference epr) {
/* 445 */     SEIStub ps = new SEIStub(portInfo, (BindingImpl)binding, (SOAPSEIModel)model, epr);
/* 446 */     return portInterface.cast(
/* 447 */         Proxy.newProxyInstance(portInterface.getClassLoader(), new Class[] { portInterface, WSBindingProvider.class }, (InvocationHandler)ps));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/Stubs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */