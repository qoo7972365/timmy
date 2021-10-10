/*     */ package com.sun.xml.internal.ws.transport.http;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import java.util.AbstractList;
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
/*     */ public abstract class HttpAdapterList<T extends HttpAdapter>
/*     */   extends AbstractList<T>
/*     */   implements DeploymentDescriptorParser.AdapterFactory<T>
/*     */ {
/*  58 */   private final List<T> adapters = new ArrayList<>();
/*  59 */   private final Map<PortInfo, String> addressMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public T createAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
/*  64 */     T t = createHttpAdapter(name, urlPattern, endpoint);
/*  65 */     this.adapters.add(t);
/*  66 */     WSDLPort port = endpoint.getPort();
/*  67 */     if (port != null) {
/*  68 */       PortInfo portInfo = new PortInfo(port.getOwner().getName(), port.getName().getLocalPart(), endpoint.getImplementationClass());
/*  69 */       this.addressMap.put(portInfo, getValidPath(urlPattern));
/*     */     } 
/*  71 */     return t;
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
/*     */   private String getValidPath(@NotNull String urlPattern) {
/*  84 */     if (urlPattern.endsWith("/*")) {
/*  85 */       return urlPattern.substring(0, urlPattern.length() - 2);
/*     */     }
/*  87 */     return urlPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PortAddressResolver createPortAddressResolver(final String baseAddress, final Class<?> endpointImpl) {
/*  97 */     return new PortAddressResolver()
/*     */       {
/*     */         public String getAddressFor(@NotNull QName serviceName, @NotNull String portName) {
/* 100 */           String urlPattern = (String)HttpAdapterList.this.addressMap.get(new HttpAdapterList.PortInfo(serviceName, portName, endpointImpl));
/* 101 */           if (urlPattern == null)
/*     */           {
/*     */             
/* 104 */             for (Map.Entry<HttpAdapterList.PortInfo, String> e : (Iterable<Map.Entry<HttpAdapterList.PortInfo, String>>)HttpAdapterList.this.addressMap.entrySet()) {
/* 105 */               if (serviceName.equals((e.getKey()).serviceName) && portName.equals((e.getKey()).portName)) {
/* 106 */                 urlPattern = e.getValue();
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/* 111 */           return (urlPattern == null) ? null : (baseAddress + urlPattern);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(int index) {
/* 119 */     return this.adapters.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 124 */     return this.adapters.size();
/*     */   }
/*     */   
/*     */   protected abstract T createHttpAdapter(String paramString1, String paramString2, WSEndpoint<?> paramWSEndpoint);
/*     */   
/*     */   private static class PortInfo {
/*     */     private final QName serviceName;
/*     */     
/*     */     PortInfo(@NotNull QName serviceName, @NotNull String portName, Class<?> implClass) {
/* 133 */       this.serviceName = serviceName;
/* 134 */       this.portName = portName;
/* 135 */       this.implClass = implClass;
/*     */     }
/*     */     private final String portName; private final Class<?> implClass;
/*     */     
/*     */     public boolean equals(Object portInfo) {
/* 140 */       if (portInfo instanceof PortInfo) {
/* 141 */         PortInfo that = (PortInfo)portInfo;
/* 142 */         if (this.implClass == null) {
/* 143 */           return (this.serviceName.equals(that.serviceName) && this.portName.equals(that.portName) && that.implClass == null);
/*     */         }
/* 145 */         return (this.serviceName.equals(that.serviceName) && this.portName.equals(that.portName) && this.implClass.equals(that.implClass));
/*     */       } 
/* 147 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 152 */       int retVal = this.serviceName.hashCode() + this.portName.hashCode();
/* 153 */       return (this.implClass != null) ? (retVal + this.implClass.hashCode()) : retVal;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/HttpAdapterList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */