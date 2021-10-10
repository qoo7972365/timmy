/*     */ package com.sun.xml.internal.stream.writers;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.Comment;
/*     */ import javax.xml.stream.events.DTD;
/*     */ import javax.xml.stream.events.EntityReference;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartDocument;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLEventWriterImpl
/*     */   implements XMLEventWriter
/*     */ {
/*     */   private XMLStreamWriter fStreamWriter;
/*     */   private static final boolean DEBUG = false;
/*     */   
/*     */   public XMLEventWriterImpl(XMLStreamWriter streamWriter) {
/*  59 */     this.fStreamWriter = streamWriter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLEventReader xMLEventReader) throws XMLStreamException {
/*  68 */     if (xMLEventReader == null) throw new XMLStreamException("Event reader shouldn't be null"); 
/*  69 */     while (xMLEventReader.hasNext())
/*  70 */       add(xMLEventReader.nextEvent());  } public void add(XMLEvent xMLEvent) throws XMLStreamException { DTD dtd; StartDocument startDocument; StartElement startElement; Namespace namespace;
/*     */     Comment comment;
/*     */     ProcessingInstruction processingInstruction;
/*     */     Characters characters1;
/*     */     EntityReference entityReference;
/*     */     Attribute attribute;
/*     */     Characters characters;
/*     */     QName qname;
/*     */     Iterator<Namespace> iterator;
/*     */     Iterator<Attribute> attributes;
/*  80 */     int type = xMLEvent.getEventType();
/*  81 */     switch (type) {
/*     */       case 11:
/*  83 */         dtd = (DTD)xMLEvent;
/*     */         
/*  85 */         this.fStreamWriter.writeDTD(dtd.getDocumentTypeDeclaration());
/*     */         break;
/*     */       
/*     */       case 7:
/*  89 */         startDocument = (StartDocument)xMLEvent;
/*     */         
/*     */         try {
/*  92 */           this.fStreamWriter.writeStartDocument(startDocument.getCharacterEncodingScheme(), startDocument.getVersion());
/*  93 */         } catch (XMLStreamException e) {
/*  94 */           this.fStreamWriter.writeStartDocument(startDocument.getVersion());
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 1:
/*  99 */         startElement = xMLEvent.asStartElement();
/*     */         
/* 101 */         qname = startElement.getName();
/* 102 */         this.fStreamWriter.writeStartElement(qname.getPrefix(), qname.getLocalPart(), qname.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 110 */         iterator = startElement.getNamespaces();
/* 111 */         while (iterator.hasNext()) {
/* 112 */           Namespace namespace1 = iterator.next();
/* 113 */           this.fStreamWriter.writeNamespace(namespace1.getPrefix(), namespace1.getNamespaceURI());
/*     */         } 
/*     */         
/* 116 */         attributes = startElement.getAttributes();
/* 117 */         while (attributes.hasNext()) {
/* 118 */           Attribute attribute1 = attributes.next();
/* 119 */           QName aqname = attribute1.getName();
/* 120 */           this.fStreamWriter.writeAttribute(aqname.getPrefix(), aqname.getNamespaceURI(), aqname.getLocalPart(), attribute1.getValue());
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 13:
/* 125 */         namespace = (Namespace)xMLEvent;
/*     */         
/* 127 */         this.fStreamWriter.writeNamespace(namespace.getPrefix(), namespace.getNamespaceURI());
/*     */         break;
/*     */       
/*     */       case 5:
/* 131 */         comment = (Comment)xMLEvent;
/*     */         
/* 133 */         this.fStreamWriter.writeComment(comment.getText());
/*     */         break;
/*     */       
/*     */       case 3:
/* 137 */         processingInstruction = (ProcessingInstruction)xMLEvent;
/*     */         
/* 139 */         this.fStreamWriter.writeProcessingInstruction(processingInstruction.getTarget(), processingInstruction.getData());
/*     */         break;
/*     */       
/*     */       case 4:
/* 143 */         characters1 = xMLEvent.asCharacters();
/*     */ 
/*     */         
/* 146 */         if (characters1.isCData()) {
/* 147 */           this.fStreamWriter.writeCData(characters1.getData());
/*     */           break;
/*     */         } 
/* 150 */         this.fStreamWriter.writeCharacters(characters1.getData());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 9:
/* 155 */         entityReference = (EntityReference)xMLEvent;
/*     */         
/* 157 */         this.fStreamWriter.writeEntityRef(entityReference.getName());
/*     */         break;
/*     */       
/*     */       case 10:
/* 161 */         attribute = (Attribute)xMLEvent;
/*     */         
/* 163 */         qname = attribute.getName();
/* 164 */         this.fStreamWriter.writeAttribute(qname.getPrefix(), qname.getNamespaceURI(), qname.getLocalPart(), attribute.getValue());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 12:
/* 170 */         characters = (Characters)xMLEvent;
/*     */         
/* 172 */         if (characters.isCData()) {
/* 173 */           this.fStreamWriter.writeCData(characters.getData());
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 184 */         this.fStreamWriter.writeEndElement();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 190 */         this.fStreamWriter.writeEndDocument();
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 203 */     this.fStreamWriter.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {
/* 212 */     this.fStreamWriter.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 220 */     return this.fStreamWriter.getNamespaceContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String namespaceURI) throws XMLStreamException {
/* 230 */     return this.fStreamWriter.getPrefix(namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 239 */     this.fStreamWriter.setDefaultNamespace(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 248 */     this.fStreamWriter.setNamespaceContext(namespaceContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 258 */     this.fStreamWriter.setPrefix(prefix, uri);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/writers/XMLEventWriterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */