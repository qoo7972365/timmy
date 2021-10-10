/*     */ package com.sun.xml.internal.ws.api;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.spi.ServiceDelegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSService
/*     */   extends ServiceDelegate
/*     */   implements ComponentRegistry
/*     */ {
/*  73 */   private final Set<Component> components = new CopyOnWriteArraySet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> T getPort(WSEndpointReference paramWSEndpointReference, Class<T> paramClass, WebServiceFeature... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> Dispatch<T> createDispatch(QName paramQName, WSEndpointReference paramWSEndpointReference, Class<T> paramClass, Service.Mode paramMode, WebServiceFeature... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Dispatch<Object> createDispatch(QName paramQName, WSEndpointReference paramWSEndpointReference, JAXBContext paramJAXBContext, Service.Mode paramMode, WebServiceFeature... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract Container getContainer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <S> S getSPI(@NotNull Class<S> spiType) {
/* 110 */     for (Component c : this.components) {
/* 111 */       S s = c.getSPI(spiType);
/* 112 */       if (s != null) {
/* 113 */         return s;
/*     */       }
/*     */     } 
/* 116 */     return (S)getContainer().getSPI(spiType);
/*     */   }
/*     */   @NotNull
/*     */   public Set<Component> getComponents() {
/* 120 */     return this.components;
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
/*     */   public static WSService create(URL wsdlDocumentLocation, QName serviceName) {
/* 136 */     return (WSService)new WSServiceDelegate(wsdlDocumentLocation, serviceName, Service.class, new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSService create(QName serviceName) {
/* 147 */     return create(null, serviceName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSService create() {
/* 154 */     return create(null, new QName(WSService.class.getName(), "dummy"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class InitParams
/*     */   {
/*     */     private Container container;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setContainer(Container c) {
/* 170 */       this.container = c;
/*     */     }
/*     */     public Container getContainer() {
/* 173 */       return this.container;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   protected static final ThreadLocal<InitParams> INIT_PARAMS = new ThreadLocal<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   protected static final InitParams EMPTY_PARAMS = new InitParams();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Service create(URL wsdlDocumentLocation, QName serviceName, InitParams properties) {
/* 206 */     if (INIT_PARAMS.get() != null)
/* 207 */       throw new IllegalStateException("someone left non-null InitParams"); 
/* 208 */     INIT_PARAMS.set(properties);
/*     */     try {
/* 210 */       Service svc = Service.create(wsdlDocumentLocation, serviceName);
/* 211 */       if (INIT_PARAMS.get() != null)
/* 212 */         throw new IllegalStateException("Service " + svc + " didn't recognize InitParams"); 
/* 213 */       return svc;
/*     */     } finally {
/*     */       
/* 216 */       INIT_PARAMS.set(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSService unwrap(final Service svc) {
/* 227 */     return AccessController.<WSService>doPrivileged(new PrivilegedAction<WSService>() {
/*     */           public WSService run() {
/*     */             try {
/* 230 */               Field f = svc.getClass().getField("delegate");
/* 231 */               f.setAccessible(true);
/* 232 */               Object delegate = f.get(svc);
/* 233 */               if (!(delegate instanceof WSService))
/* 234 */                 throw new IllegalArgumentException(); 
/* 235 */               return (WSService)delegate;
/* 236 */             } catch (NoSuchFieldException e) {
/* 237 */               AssertionError x = new AssertionError("Unexpected service API implementation");
/* 238 */               x.initCause(e);
/* 239 */               throw x;
/* 240 */             } catch (IllegalAccessException e) {
/* 241 */               IllegalAccessError x = new IllegalAccessError(e.getMessage());
/* 242 */               x.initCause(e);
/* 243 */               throw x;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/WSService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */