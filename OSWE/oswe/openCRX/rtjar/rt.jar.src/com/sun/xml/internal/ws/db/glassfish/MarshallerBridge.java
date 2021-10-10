/*     */ package com.sun.xml.internal.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.api.TypeReference;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.MarshallerImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MarshallerBridge
/*     */   extends Bridge
/*     */   implements XMLBridge
/*     */ {
/*     */   protected MarshallerBridge(JAXBContextImpl context) {
/*  56 */     super(context);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, XMLStreamWriter output) throws JAXBException {
/*  60 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  62 */       m.marshal(object, output);
/*     */     } finally {
/*  64 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/*  69 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  71 */       ((MarshallerImpl)m).marshal(object, output, nsContext);
/*     */     } finally {
/*  73 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, Node output) throws JAXBException {
/*  78 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  80 */       m.marshal(object, output);
/*     */     } finally {
/*  82 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, ContentHandler contentHandler) throws JAXBException {
/*  87 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  89 */       m.marshal(object, contentHandler);
/*     */     } finally {
/*  91 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, Result result) throws JAXBException {
/*  96 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  98 */       m.marshal(object, result);
/*     */     } finally {
/* 100 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, XMLStreamReader in) {
/* 105 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, Source in) {
/* 109 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, InputStream in) {
/* 113 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, Node n) {
/* 117 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/* 121 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public TypeReference getTypeReference() {
/* 124 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public BindingContext context() {
/* 127 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public boolean supportOutputStream() {
/* 130 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/glassfish/MarshallerBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */