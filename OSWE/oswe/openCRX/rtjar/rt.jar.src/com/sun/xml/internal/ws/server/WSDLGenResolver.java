/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.WSDLResolver;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocument;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentSource;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.ws.Holder;
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
/*     */ final class WSDLGenResolver
/*     */   implements WSDLResolver
/*     */ {
/*     */   private final List<SDDocumentImpl> docs;
/*  54 */   private final List<SDDocumentSource> newDocs = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private SDDocumentSource concreteWsdlSource;
/*     */   
/*     */   private SDDocumentImpl abstractWsdl;
/*     */   
/*     */   private SDDocumentImpl concreteWsdl;
/*     */   
/*  63 */   private final Map<String, List<SDDocumentImpl>> nsMapping = new HashMap<>();
/*     */   
/*     */   private final QName serviceName;
/*     */   private final QName portTypeName;
/*     */   
/*     */   public WSDLGenResolver(@NotNull List<SDDocumentImpl> docs, QName serviceName, QName portTypeName) {
/*  69 */     this.docs = docs;
/*  70 */     this.serviceName = serviceName;
/*  71 */     this.portTypeName = portTypeName;
/*     */     
/*  73 */     for (SDDocumentImpl doc : docs) {
/*  74 */       if (doc.isWSDL()) {
/*  75 */         SDDocument.WSDL wsdl = (SDDocument.WSDL)doc;
/*  76 */         if (wsdl.hasPortType())
/*  77 */           this.abstractWsdl = doc; 
/*     */       } 
/*  79 */       if (doc.isSchema()) {
/*  80 */         SDDocument.Schema schema = (SDDocument.Schema)doc;
/*  81 */         List<SDDocumentImpl> sysIds = this.nsMapping.get(schema.getTargetNamespace());
/*  82 */         if (sysIds == null) {
/*  83 */           sysIds = new ArrayList<>();
/*  84 */           this.nsMapping.put(schema.getTargetNamespace(), sysIds);
/*     */         } 
/*  86 */         sysIds.add(doc);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result getWSDL(String filename) {
/*  97 */     URL url = createURL(filename);
/*  98 */     MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*  99 */     xsb.setSystemId(url.toExternalForm());
/* 100 */     this.concreteWsdlSource = SDDocumentSource.create(url, (XMLStreamBuffer)xsb);
/* 101 */     this.newDocs.add(this.concreteWsdlSource);
/* 102 */     XMLStreamBufferResult r = new XMLStreamBufferResult(xsb);
/* 103 */     r.setSystemId(filename);
/* 104 */     return (Result)r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URL createURL(String filename) {
/*     */     try {
/* 116 */       return new URL("file:///" + filename);
/* 117 */     } catch (MalformedURLException e) {
/*     */ 
/*     */       
/* 120 */       throw new WebServiceException(e);
/*     */     } 
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
/*     */   public Result getAbstractWSDL(Holder<String> filename) {
/* 133 */     if (this.abstractWsdl != null) {
/* 134 */       filename.value = this.abstractWsdl.getURL().toString();
/* 135 */       return null;
/*     */     } 
/* 137 */     URL url = createURL((String)filename.value);
/* 138 */     MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 139 */     xsb.setSystemId(url.toExternalForm());
/* 140 */     SDDocumentSource abstractWsdlSource = SDDocumentSource.create(url, (XMLStreamBuffer)xsb);
/* 141 */     this.newDocs.add(abstractWsdlSource);
/* 142 */     XMLStreamBufferResult r = new XMLStreamBufferResult(xsb);
/* 143 */     r.setSystemId((String)filename.value);
/* 144 */     return (Result)r;
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
/*     */   public Result getSchemaOutput(String namespace, Holder<String> filename) {
/* 156 */     List<SDDocumentImpl> schemas = this.nsMapping.get(namespace);
/* 157 */     if (schemas != null) {
/* 158 */       if (schemas.size() > 1) {
/* 159 */         throw new ServerRtException("server.rt.err", new Object[] { "More than one schema for the target namespace " + namespace });
/*     */       }
/*     */       
/* 162 */       filename.value = ((SDDocumentImpl)schemas.get(0)).getURL().toExternalForm();
/* 163 */       return null;
/*     */     } 
/*     */     
/* 166 */     URL url = createURL((String)filename.value);
/* 167 */     MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 168 */     xsb.setSystemId(url.toExternalForm());
/* 169 */     SDDocumentSource sd = SDDocumentSource.create(url, (XMLStreamBuffer)xsb);
/* 170 */     this.newDocs.add(sd);
/*     */     
/* 172 */     XMLStreamBufferResult r = new XMLStreamBufferResult(xsb);
/* 173 */     r.setSystemId((String)filename.value);
/* 174 */     return (Result)r;
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
/*     */   public SDDocumentImpl updateDocs() {
/* 186 */     for (SDDocumentSource doc : this.newDocs) {
/* 187 */       SDDocumentImpl docImpl = SDDocumentImpl.create(doc, this.serviceName, this.portTypeName);
/* 188 */       if (doc == this.concreteWsdlSource) {
/* 189 */         this.concreteWsdl = docImpl;
/*     */       }
/* 191 */       this.docs.add(docImpl);
/*     */     } 
/* 193 */     return this.concreteWsdl;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/WSDLGenResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */