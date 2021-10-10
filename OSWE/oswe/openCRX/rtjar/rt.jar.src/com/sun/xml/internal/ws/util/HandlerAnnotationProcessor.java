/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.api.server.AsyncProvider;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.handler.HandlerChainsModel;
/*     */ import com.sun.xml.internal.ws.model.ReflectAnnotationReader;
/*     */ import com.sun.xml.internal.ws.server.EndpointFactory;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.HandlerChain;
/*     */ import javax.jws.WebService;
/*     */ import javax.jws.soap.SOAPMessageHandlers;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.Provider;
/*     */ import javax.xml.ws.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HandlerAnnotationProcessor
/*     */ {
/*  74 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.util");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HandlerAnnotationInfo buildHandlerInfo(@NotNull Class<?> clazz, QName serviceName, QName portName, WSBinding binding) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/*  94 */     MetadataReader metadataReader = EndpointFactory.getExternalMetadatReader(clazz, binding);
/*  95 */     if (metadataReader == null) {
/*  96 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */ 
/*     */     
/* 100 */     HandlerChain handlerChain = (HandlerChain)reflectAnnotationReader.getAnnotation(HandlerChain.class, clazz);
/* 101 */     if (handlerChain == null) {
/* 102 */       clazz = getSEI(clazz, (MetadataReader)reflectAnnotationReader);
/* 103 */       if (clazz != null)
/* 104 */         handlerChain = (HandlerChain)reflectAnnotationReader.getAnnotation(HandlerChain.class, clazz); 
/* 105 */       if (handlerChain == null) {
/* 106 */         return null;
/*     */       }
/*     */     } 
/* 109 */     if (clazz.getAnnotation(SOAPMessageHandlers.class) != null) {
/* 110 */       throw new UtilException("util.handler.cannot.combine.soapmessagehandlers", new Object[0]);
/*     */     }
/*     */     
/* 113 */     InputStream iStream = getFileAsStream(clazz, handlerChain);
/*     */     
/* 115 */     XMLStreamReader reader = XMLStreamReaderFactory.create(null, iStream, true);
/* 116 */     XMLStreamReaderUtil.nextElementContent(reader);
/* 117 */     HandlerAnnotationInfo handlerAnnInfo = HandlerChainsModel.parseHandlerFile(reader, clazz.getClassLoader(), serviceName, portName, binding);
/*     */     
/*     */     try {
/* 120 */       reader.close();
/* 121 */       iStream.close();
/* 122 */     } catch (XMLStreamException e) {
/* 123 */       e.printStackTrace();
/* 124 */       throw new UtilException(e.getMessage(), new Object[0]);
/* 125 */     } catch (IOException e) {
/* 126 */       e.printStackTrace();
/* 127 */       throw new UtilException(e.getMessage(), new Object[0]);
/*     */     } 
/* 129 */     return handlerAnnInfo;
/*     */   }
/*     */   
/*     */   public static HandlerChainsModel buildHandlerChainsModel(Class<?> clazz) {
/* 133 */     if (clazz == null) {
/* 134 */       return null;
/*     */     }
/*     */     
/* 137 */     HandlerChain handlerChain = clazz.<HandlerChain>getAnnotation(HandlerChain.class);
/* 138 */     if (handlerChain == null)
/* 139 */       return null; 
/* 140 */     InputStream iStream = getFileAsStream(clazz, handlerChain);
/*     */     
/* 142 */     XMLStreamReader reader = XMLStreamReaderFactory.create(null, iStream, true);
/* 143 */     XMLStreamReaderUtil.nextElementContent(reader);
/* 144 */     HandlerChainsModel handlerChainsModel = HandlerChainsModel.parseHandlerConfigFile(clazz, reader);
/*     */     try {
/* 146 */       reader.close();
/* 147 */       iStream.close();
/* 148 */     } catch (XMLStreamException e) {
/* 149 */       e.printStackTrace();
/* 150 */       throw new UtilException(e.getMessage(), new Object[0]);
/* 151 */     } catch (IOException e) {
/* 152 */       e.printStackTrace();
/* 153 */       throw new UtilException(e.getMessage(), new Object[0]);
/*     */     } 
/* 155 */     return handlerChainsModel;
/*     */   }
/*     */   
/*     */   static Class getClass(String className) {
/*     */     try {
/* 160 */       return Thread.currentThread().getContextClassLoader().loadClass(className);
/*     */     }
/* 162 */     catch (ClassNotFoundException e) {
/* 163 */       throw new UtilException("util.handler.class.not.found", new Object[] { className });
/*     */     } 
/*     */   }
/*     */   
/*     */   static Class getSEI(Class<?> clazz, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/* 169 */     if (metadataReader == null) {
/* 170 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 173 */     if (Provider.class.isAssignableFrom(clazz) || AsyncProvider.class.isAssignableFrom(clazz))
/*     */     {
/* 175 */       return null;
/*     */     }
/* 177 */     if (Service.class.isAssignableFrom(clazz))
/*     */     {
/* 179 */       return null;
/*     */     }
/*     */     
/* 182 */     WebService webService = (WebService)reflectAnnotationReader.getAnnotation(WebService.class, clazz);
/* 183 */     if (webService == null) {
/* 184 */       throw new UtilException("util.handler.no.webservice.annotation", new Object[] { clazz.getCanonicalName() });
/*     */     }
/*     */     
/* 187 */     String ei = webService.endpointInterface();
/* 188 */     if (ei.length() > 0) {
/* 189 */       clazz = getClass(webService.endpointInterface());
/* 190 */       WebService ws = (WebService)reflectAnnotationReader.getAnnotation(WebService.class, clazz);
/* 191 */       if (ws == null) {
/* 192 */         throw new UtilException("util.handler.endpoint.interface.no.webservice", new Object[] { webService
/* 193 */               .endpointInterface() });
/*     */       }
/* 195 */       return clazz;
/*     */     } 
/* 197 */     return null;
/*     */   }
/*     */   
/*     */   static InputStream getFileAsStream(Class clazz, HandlerChain chain) {
/* 201 */     URL url = clazz.getResource(chain.file());
/* 202 */     if (url == null)
/*     */     {
/* 204 */       url = Thread.currentThread().getContextClassLoader().getResource(chain.file());
/*     */     }
/* 206 */     if (url == null) {
/* 207 */       String tmp = clazz.getPackage().getName();
/* 208 */       tmp = tmp.replace('.', '/');
/* 209 */       tmp = tmp + "/" + chain.file();
/*     */       
/* 211 */       url = Thread.currentThread().getContextClassLoader().getResource(tmp);
/*     */     } 
/* 213 */     if (url == null) {
/* 214 */       throw new UtilException("util.failed.to.find.handlerchain.file", new Object[] { clazz
/* 215 */             .getName(), chain.file() });
/*     */     }
/*     */     try {
/* 218 */       return url.openStream();
/* 219 */     } catch (IOException e) {
/* 220 */       throw new UtilException("util.failed.to.parse.handlerchain.file", new Object[] { clazz
/* 221 */             .getName(), chain.file() });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/HandlerAnnotationProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */