/*    */ package com.sun.xml.internal.ws.client;
/*    */ 
/*    */ import com.sun.xml.internal.ws.util.JAXWSUtils;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.ArrayList;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.WebEndpoint;
/*    */ import javax.xml.ws.WebServiceClient;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ final class SCAnnotations
/*    */ {
/*    */   final ArrayList<QName> portQNames;
/*    */   final ArrayList<Class> classes;
/*    */   
/*    */   SCAnnotations(final Class<?> sc) {
/* 83 */     this.portQNames = new ArrayList<>();
/* 84 */     this.classes = new ArrayList<>();
/*    */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*    */           public Void run() {
/*    */             WebServiceClient wsc = (WebServiceClient)sc.getAnnotation(WebServiceClient.class);
/*    */             if (wsc == null)
/*    */               throw new WebServiceException("Service Interface Annotations required, exiting..."); 
/*    */             String tns = wsc.targetNamespace();
/*    */             try {
/*    */               JAXWSUtils.getFileOrURL(wsc.wsdlLocation());
/*    */             } catch (IOException e) {
/*    */               throw new WebServiceException(e);
/*    */             } 
/*    */             for (Method method : sc.getDeclaredMethods()) {
/*    */               WebEndpoint webEndpoint = method.<WebEndpoint>getAnnotation(WebEndpoint.class);
/*    */               if (webEndpoint != null) {
/*    */                 String endpointName = webEndpoint.name();
/*    */                 QName portQName = new QName(tns, endpointName);
/*    */                 SCAnnotations.this.portQNames.add(portQName);
/*    */               } 
/*    */               Class<?> seiClazz = method.getReturnType();
/*    */               if (seiClazz != void.class)
/*    */                 SCAnnotations.this.classes.add(seiClazz); 
/*    */             } 
/*    */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/SCAnnotations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */