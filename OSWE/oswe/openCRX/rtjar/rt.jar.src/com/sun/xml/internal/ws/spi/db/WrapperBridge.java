/*     */ package com.sun.xml.internal.ws.spi.db;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperBridge<T>
/*     */   implements XMLBridge<T>
/*     */ {
/*     */   BindingContext parent;
/*     */   TypeInfo typeInfo;
/*     */   static final String WrapperPrefix = "w";
/*     */   static final String WrapperPrefixColon = "w:";
/*     */   
/*     */   public WrapperBridge(BindingContext p, TypeInfo ti) {
/*  68 */     this.parent = p;
/*  69 */     this.typeInfo = ti;
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext context() {
/*  74 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/*  79 */     return this.typeInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/*  84 */     WrapperComposite w = (WrapperComposite)object;
/*  85 */     Attributes att = new Attributes() {
/*  86 */         public int getLength() { return 0; }
/*  87 */         public String getURI(int index) { return null; }
/*  88 */         public String getLocalName(int index) { return null; }
/*  89 */         public String getQName(int index) { return null; }
/*  90 */         public String getType(int index) { return null; }
/*  91 */         public String getValue(int index) { return null; }
/*  92 */         public int getIndex(String uri, String localName) { return 0; }
/*  93 */         public int getIndex(String qName) { return 0; }
/*  94 */         public String getType(String uri, String localName) { return null; }
/*  95 */         public String getType(String qName) { return null; }
/*  96 */         public String getValue(String uri, String localName) { return null; }
/*  97 */         public String getValue(String qName) { return null; }
/*     */       };
/*     */     try {
/* 100 */       contentHandler.startPrefixMapping("w", this.typeInfo.tagName.getNamespaceURI());
/* 101 */       contentHandler.startElement(this.typeInfo.tagName.getNamespaceURI(), this.typeInfo.tagName.getLocalPart(), "w:" + this.typeInfo.tagName.getLocalPart(), att);
/* 102 */     } catch (SAXException e) {
/* 103 */       throw new JAXBException(e);
/*     */     } 
/* 105 */     if (w.bridges != null) for (int i = 0; i < w.bridges.length; i++) {
/* 106 */         if (w.bridges[i] instanceof RepeatedElementBridge) {
/* 107 */           RepeatedElementBridge rbridge = (RepeatedElementBridge)w.bridges[i];
/* 108 */           for (Iterator itr = rbridge.collectionHandler().iterator(w.values[i]); itr.hasNext();) {
/* 109 */             rbridge.marshal(itr.next(), contentHandler, am);
/*     */           }
/*     */         } else {
/* 112 */           w.bridges[i].marshal(w.values[i], contentHandler, am);
/*     */         } 
/*     */       }  
/*     */     try {
/* 116 */       contentHandler.endElement(this.typeInfo.tagName.getNamespaceURI(), this.typeInfo.tagName.getLocalPart(), null);
/* 117 */       contentHandler.endPrefixMapping("w");
/* 118 */     } catch (SAXException e) {
/* 119 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, Node output) throws JAXBException {
/* 126 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 138 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/* 144 */     WrapperComposite w = (WrapperComposite)object;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 150 */       String prefix = output.getPrefix(this.typeInfo.tagName.getNamespaceURI());
/* 151 */       if (prefix == null) prefix = "w"; 
/* 152 */       output.writeStartElement(prefix, this.typeInfo.tagName.getLocalPart(), this.typeInfo.tagName.getNamespaceURI());
/* 153 */       output.writeNamespace(prefix, this.typeInfo.tagName.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 160 */     catch (XMLStreamException e) {
/* 161 */       e.printStackTrace();
/* 162 */       throw new DatabindingException(e);
/*     */     } 
/* 164 */     if (w.bridges != null) for (int i = 0; i < w.bridges.length; i++) {
/* 165 */         if (w.bridges[i] instanceof RepeatedElementBridge) {
/* 166 */           RepeatedElementBridge rbridge = (RepeatedElementBridge)w.bridges[i];
/* 167 */           for (Iterator itr = rbridge.collectionHandler().iterator(w.values[i]); itr.hasNext();) {
/* 168 */             rbridge.marshal(itr.next(), output, am);
/*     */           }
/*     */         } else {
/* 171 */           w.bridges[i].marshal(w.values[i], output, am);
/*     */         } 
/*     */       }  
/*     */     try {
/* 175 */       output.writeEndElement();
/* 176 */     } catch (XMLStreamException e) {
/* 177 */       throw new DatabindingException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(InputStream in) throws JAXBException {
/* 184 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
/* 191 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
/* 198 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
/* 205 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportOutputStream() {
/* 211 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/WrapperBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */