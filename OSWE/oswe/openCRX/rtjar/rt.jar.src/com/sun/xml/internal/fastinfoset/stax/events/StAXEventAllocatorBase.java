/*     */ package com.sun.xml.internal.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.stream.util.XMLEventAllocator;
/*     */ import javax.xml.stream.util.XMLEventConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXEventAllocatorBase
/*     */   implements XMLEventAllocator
/*     */ {
/*     */   XMLEventFactory factory;
/*     */   
/*     */   public StAXEventAllocatorBase() {
/*  60 */     if (System.getProperty("javax.xml.stream.XMLEventFactory") == null) {
/*  61 */       System.setProperty("javax.xml.stream.XMLEventFactory", "com.sun.xml.internal.fastinfoset.stax.factory.StAXEventFactory");
/*     */     }
/*     */     
/*  64 */     this.factory = XMLEventFactory.newInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventAllocator newInstance() {
/*  74 */     return new StAXEventAllocatorBase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEvent allocate(XMLStreamReader streamReader) throws XMLStreamException {
/*  85 */     if (streamReader == null)
/*  86 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.nullReader")); 
/*  87 */     return getXMLEvent(streamReader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void allocate(XMLStreamReader streamReader, XMLEventConsumer consumer) throws XMLStreamException {
/*  98 */     consumer.add(getXMLEvent(streamReader));
/*     */   }
/*     */   XMLEvent getXMLEvent(XMLStreamReader reader) {
/*     */     StartElementEvent startElement;
/*     */     EndElementEvent endElement;
/*     */     StartDocumentEvent docEvent;
/*     */     EndDocumentEvent endDocumentEvent;
/* 105 */     XMLEvent event = null;
/*     */     
/* 107 */     int eventType = reader.getEventType();
/*     */     
/* 109 */     this.factory.setLocation(reader.getLocation());
/* 110 */     switch (eventType) {
/*     */ 
/*     */       
/*     */       case 1:
/* 114 */         startElement = (StartElementEvent)this.factory.createStartElement(reader.getPrefix(), reader
/* 115 */             .getNamespaceURI(), reader.getLocalName());
/*     */         
/* 117 */         addAttributes(startElement, reader);
/* 118 */         addNamespaces(startElement, reader);
/*     */ 
/*     */         
/* 121 */         event = startElement;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 126 */         endElement = (EndElementEvent)this.factory.createEndElement(reader
/* 127 */             .getPrefix(), reader.getNamespaceURI(), reader.getLocalName());
/* 128 */         addNamespaces(endElement, reader);
/* 129 */         event = endElement;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 134 */         event = this.factory.createProcessingInstruction(reader.getPITarget(), reader.getPIData());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 139 */         if (reader.isWhiteSpace()) {
/* 140 */           event = this.factory.createSpace(reader.getText()); break;
/*     */         } 
/* 142 */         event = this.factory.createCharacters(reader.getText());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 148 */         event = this.factory.createComment(reader.getText());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 153 */         docEvent = (StartDocumentEvent)this.factory.createStartDocument(reader
/* 154 */             .getVersion(), reader.getEncoding(), reader.isStandalone());
/* 155 */         if (reader.getCharacterEncodingScheme() != null) {
/* 156 */           docEvent.setDeclaredEncoding(true);
/*     */         } else {
/* 158 */           docEvent.setDeclaredEncoding(false);
/*     */         } 
/* 160 */         event = docEvent;
/*     */         break;
/*     */       
/*     */       case 8:
/* 164 */         endDocumentEvent = new EndDocumentEvent();
/* 165 */         event = endDocumentEvent;
/*     */         break;
/*     */       
/*     */       case 9:
/* 169 */         event = this.factory.createEntityReference(reader.getLocalName(), new EntityDeclarationImpl(reader
/* 170 */               .getLocalName(), reader.getText()));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 10:
/* 175 */         event = null;
/*     */         break;
/*     */       
/*     */       case 11:
/* 179 */         event = this.factory.createDTD(reader.getText());
/*     */         break;
/*     */       
/*     */       case 12:
/* 183 */         event = this.factory.createCData(reader.getText());
/*     */         break;
/*     */       
/*     */       case 6:
/* 187 */         event = this.factory.createSpace(reader.getText());
/*     */         break;
/*     */     } 
/*     */     
/* 191 */     return event;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAttributes(StartElementEvent event, XMLStreamReader streamReader) {
/* 196 */     AttributeBase attr = null;
/* 197 */     for (int i = 0; i < streamReader.getAttributeCount(); i++) {
/* 198 */       attr = (AttributeBase)this.factory.createAttribute(streamReader.getAttributeName(i), streamReader
/* 199 */           .getAttributeValue(i));
/* 200 */       attr.setAttributeType(streamReader.getAttributeType(i));
/* 201 */       attr.setSpecified(streamReader.isAttributeSpecified(i));
/* 202 */       event.addAttribute(attr);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addNamespaces(StartElementEvent event, XMLStreamReader streamReader) {
/* 208 */     Namespace namespace = null;
/* 209 */     for (int i = 0; i < streamReader.getNamespaceCount(); i++) {
/* 210 */       namespace = this.factory.createNamespace(streamReader.getNamespacePrefix(i), streamReader
/* 211 */           .getNamespaceURI(i));
/* 212 */       event.addNamespace(namespace);
/*     */     } 
/*     */   }
/*     */   protected void addNamespaces(EndElementEvent event, XMLStreamReader streamReader) {
/* 216 */     Namespace namespace = null;
/* 217 */     for (int i = 0; i < streamReader.getNamespaceCount(); i++) {
/* 218 */       namespace = this.factory.createNamespace(streamReader.getNamespacePrefix(i), streamReader
/* 219 */           .getNamespaceURI(i));
/* 220 */       event.addNamespace(namespace);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/StAXEventAllocatorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */