/*     */ package com.sun.xml.internal.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
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
/*     */ public class StAXEventWriter
/*     */   implements XMLEventWriter
/*     */ {
/*     */   private XMLStreamWriter _streamWriter;
/*     */   
/*     */   public StAXEventWriter(XMLStreamWriter streamWriter) {
/*  48 */     this._streamWriter = streamWriter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {
/*  56 */     this._streamWriter.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {
/*  63 */     this._streamWriter.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLEventReader eventReader) throws XMLStreamException {
/*  72 */     if (eventReader == null) throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.nullEventReader")); 
/*  73 */     while (eventReader.hasNext())
/*  74 */       add(eventReader.nextEvent());  } public void add(XMLEvent event) throws XMLStreamException { DTD dtd;
/*     */     StartDocument startDocument;
/*     */     StartElement startElement;
/*     */     Namespace namespace;
/*     */     Comment comment;
/*     */     ProcessingInstruction processingInstruction;
/*     */     Characters characters1;
/*     */     EntityReference entityReference;
/*     */     Attribute attribute;
/*     */     Characters characters;
/*     */     QName qname;
/*     */     Iterator<Namespace> iterator;
/*     */     Iterator<Attribute> attributes;
/*  87 */     int type = event.getEventType();
/*  88 */     switch (type) {
/*     */       case 11:
/*  90 */         dtd = (DTD)event;
/*  91 */         this._streamWriter.writeDTD(dtd.getDocumentTypeDeclaration());
/*     */         return;
/*     */       
/*     */       case 7:
/*  95 */         startDocument = (StartDocument)event;
/*  96 */         this._streamWriter.writeStartDocument(startDocument.getCharacterEncodingScheme(), startDocument.getVersion());
/*     */         return;
/*     */       
/*     */       case 1:
/* 100 */         startElement = event.asStartElement();
/* 101 */         qname = startElement.getName();
/* 102 */         this._streamWriter.writeStartElement(qname.getPrefix(), qname.getLocalPart(), qname.getNamespaceURI());
/*     */         
/* 104 */         iterator = startElement.getNamespaces();
/* 105 */         while (iterator.hasNext()) {
/* 106 */           Namespace namespace1 = iterator.next();
/* 107 */           this._streamWriter.writeNamespace(namespace1.getPrefix(), namespace1.getNamespaceURI());
/*     */         } 
/*     */         
/* 110 */         attributes = startElement.getAttributes();
/* 111 */         while (attributes.hasNext()) {
/* 112 */           Attribute attribute1 = attributes.next();
/* 113 */           QName name = attribute1.getName();
/* 114 */           this._streamWriter.writeAttribute(name.getPrefix(), name.getNamespaceURI(), name
/* 115 */               .getLocalPart(), attribute1.getValue());
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 13:
/* 120 */         namespace = (Namespace)event;
/* 121 */         this._streamWriter.writeNamespace(namespace.getPrefix(), namespace.getNamespaceURI());
/*     */         return;
/*     */       
/*     */       case 5:
/* 125 */         comment = (Comment)event;
/* 126 */         this._streamWriter.writeComment(comment.getText());
/*     */         return;
/*     */       
/*     */       case 3:
/* 130 */         processingInstruction = (ProcessingInstruction)event;
/* 131 */         this._streamWriter.writeProcessingInstruction(processingInstruction.getTarget(), processingInstruction.getData());
/*     */         return;
/*     */       
/*     */       case 4:
/* 135 */         characters1 = event.asCharacters();
/*     */         
/* 137 */         if (characters1.isCData()) {
/* 138 */           this._streamWriter.writeCData(characters1.getData());
/*     */         } else {
/*     */           
/* 141 */           this._streamWriter.writeCharacters(characters1.getData());
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 9:
/* 146 */         entityReference = (EntityReference)event;
/* 147 */         this._streamWriter.writeEntityRef(entityReference.getName());
/*     */         return;
/*     */       
/*     */       case 10:
/* 151 */         attribute = (Attribute)event;
/* 152 */         qname = attribute.getName();
/* 153 */         this._streamWriter.writeAttribute(qname.getPrefix(), qname.getNamespaceURI(), qname.getLocalPart(), attribute.getValue());
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 12:
/* 159 */         characters = (Characters)event;
/* 160 */         if (characters.isCData()) {
/* 161 */           this._streamWriter.writeCData(characters.getData());
/*     */         }
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/* 167 */         this._streamWriter.writeEndElement();
/*     */         return;
/*     */       
/*     */       case 8:
/* 171 */         this._streamWriter.writeEndDocument();
/*     */         return;
/*     */     } 
/*     */     
/* 175 */     throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.eventTypeNotSupported", new Object[] { Util.getEventTypeString(type) })); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 187 */     return this._streamWriter.getPrefix(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 196 */     return this._streamWriter.getNamespaceContext();
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
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 210 */     this._streamWriter.setDefaultNamespace(uri);
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
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 224 */     this._streamWriter.setNamespaceContext(namespaceContext);
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
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 236 */     this._streamWriter.setPrefix(prefix, uri);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/StAXEventWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */