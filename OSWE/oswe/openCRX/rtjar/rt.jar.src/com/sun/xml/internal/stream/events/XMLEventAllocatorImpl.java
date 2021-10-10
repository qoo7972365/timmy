/*     */ package com.sun.xml.internal.stream.events;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.NamespaceContextWrapper;
/*     */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public class XMLEventAllocatorImpl
/*     */   implements XMLEventAllocator
/*     */ {
/*     */   public XMLEvent allocate(XMLStreamReader xMLStreamReader) throws XMLStreamException {
/*  49 */     if (xMLStreamReader == null) {
/*  50 */       throw new XMLStreamException("Reader cannot be null");
/*     */     }
/*     */     
/*  53 */     return getXMLEvent(xMLStreamReader);
/*     */   }
/*     */   
/*     */   public void allocate(XMLStreamReader xMLStreamReader, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
/*  57 */     XMLEvent currentEvent = getXMLEvent(xMLStreamReader);
/*  58 */     if (currentEvent != null) {
/*  59 */       xMLEventConsumer.add(currentEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLEventAllocator newInstance() {
/*  65 */     return new XMLEventAllocatorImpl(); } XMLEvent getXMLEvent(XMLStreamReader streamReader) { StartElementEvent startElementEvent; EndElementEvent endElementEvent; ProcessingInstructionEvent piEvent; CharacterEvent characterEvent1; CommentEvent commentEvent; StartDocumentEvent sdEvent; EndDocumentEvent endDocumentEvent;
/*     */     EntityReferenceEvent entityEvent;
/*     */     DTDEvent dtdEvent;
/*     */     CharacterEvent cDataEvent, spaceEvent;
/*     */     List entities, notations;
/*  70 */     XMLEvent event = null;
/*     */     
/*  72 */     int eventType = streamReader.getEventType();
/*  73 */     switch (eventType) {
/*     */       
/*     */       case 1:
/*  76 */         startElementEvent = new StartElementEvent(getQName(streamReader));
/*  77 */         fillAttributes(startElementEvent, streamReader);
/*     */ 
/*     */         
/*  80 */         if (((Boolean)streamReader.getProperty("javax.xml.stream.isNamespaceAware")).booleanValue()) {
/*  81 */           fillNamespaceAttributes(startElementEvent, streamReader);
/*  82 */           setNamespaceContext(startElementEvent, streamReader);
/*     */         } 
/*     */         
/*  85 */         startElementEvent.setLocation(streamReader.getLocation());
/*  86 */         event = startElementEvent;
/*     */         break;
/*     */       
/*     */       case 2:
/*  90 */         endElementEvent = new EndElementEvent(getQName(streamReader));
/*  91 */         endElementEvent.setLocation(streamReader.getLocation());
/*     */         
/*  93 */         if (((Boolean)streamReader.getProperty("javax.xml.stream.isNamespaceAware")).booleanValue()) {
/*  94 */           fillNamespaceAttributes(endElementEvent, streamReader);
/*     */         }
/*  96 */         event = endElementEvent;
/*     */         break;
/*     */       
/*     */       case 3:
/* 100 */         piEvent = new ProcessingInstructionEvent(streamReader.getPITarget(), streamReader.getPIData());
/* 101 */         piEvent.setLocation(streamReader.getLocation());
/* 102 */         event = piEvent;
/*     */         break;
/*     */       
/*     */       case 4:
/* 106 */         characterEvent1 = new CharacterEvent(streamReader.getText());
/* 107 */         characterEvent1.setLocation(streamReader.getLocation());
/* 108 */         event = characterEvent1;
/*     */         break;
/*     */       
/*     */       case 5:
/* 112 */         commentEvent = new CommentEvent(streamReader.getText());
/* 113 */         commentEvent.setLocation(streamReader.getLocation());
/* 114 */         event = commentEvent;
/*     */         break;
/*     */       
/*     */       case 7:
/* 118 */         sdEvent = new StartDocumentEvent();
/* 119 */         sdEvent.setVersion(streamReader.getVersion());
/* 120 */         sdEvent.setEncoding(streamReader.getEncoding());
/* 121 */         if (streamReader.getCharacterEncodingScheme() != null) {
/* 122 */           sdEvent.setDeclaredEncoding(true);
/*     */         } else {
/* 124 */           sdEvent.setDeclaredEncoding(false);
/*     */         } 
/* 126 */         sdEvent.setStandalone(streamReader.isStandalone());
/* 127 */         sdEvent.setLocation(streamReader.getLocation());
/* 128 */         event = sdEvent;
/*     */         break;
/*     */       
/*     */       case 8:
/* 132 */         endDocumentEvent = new EndDocumentEvent();
/* 133 */         endDocumentEvent.setLocation(streamReader.getLocation());
/* 134 */         event = endDocumentEvent;
/*     */         break;
/*     */       
/*     */       case 9:
/* 138 */         entityEvent = new EntityReferenceEvent(streamReader.getLocalName(), new EntityDeclarationImpl(streamReader.getLocalName(), streamReader.getText()));
/* 139 */         entityEvent.setLocation(streamReader.getLocation());
/* 140 */         event = entityEvent;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 10:
/* 145 */         event = null;
/*     */         break;
/*     */       
/*     */       case 11:
/* 149 */         dtdEvent = new DTDEvent(streamReader.getText());
/* 150 */         dtdEvent.setLocation(streamReader.getLocation());
/* 151 */         entities = (List)streamReader.getProperty("javax.xml.stream.entities");
/* 152 */         if (entities != null && entities.size() != 0) dtdEvent.setEntities(entities); 
/* 153 */         notations = (List)streamReader.getProperty("javax.xml.stream.notations");
/* 154 */         if (notations != null && notations.size() != 0) dtdEvent.setNotations(notations); 
/* 155 */         event = dtdEvent;
/*     */         break;
/*     */       
/*     */       case 12:
/* 159 */         cDataEvent = new CharacterEvent(streamReader.getText(), true);
/* 160 */         cDataEvent.setLocation(streamReader.getLocation());
/* 161 */         event = cDataEvent;
/*     */         break;
/*     */       
/*     */       case 6:
/* 165 */         spaceEvent = new CharacterEvent(streamReader.getText(), false, true);
/* 166 */         spaceEvent.setLocation(streamReader.getLocation());
/* 167 */         event = spaceEvent;
/*     */         break;
/*     */     } 
/*     */     
/* 171 */     return event; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLEvent getNextEvent(XMLStreamReader streamReader) throws XMLStreamException {
/* 177 */     streamReader.next();
/* 178 */     return getXMLEvent(streamReader);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fillAttributes(StartElementEvent event, XMLStreamReader xmlr) {
/* 183 */     int len = xmlr.getAttributeCount();
/* 184 */     QName qname = null;
/* 185 */     AttributeImpl attr = null;
/* 186 */     NamespaceImpl nattr = null;
/* 187 */     for (int i = 0; i < len; i++) {
/* 188 */       qname = xmlr.getAttributeName(i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       attr = new AttributeImpl();
/* 205 */       attr.setName(qname);
/* 206 */       attr.setAttributeType(xmlr.getAttributeType(i));
/* 207 */       attr.setSpecified(xmlr.isAttributeSpecified(i));
/* 208 */       attr.setValue(xmlr.getAttributeValue(i));
/* 209 */       event.addAttribute(attr);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void fillNamespaceAttributes(StartElementEvent event, XMLStreamReader xmlr) {
/* 214 */     int count = xmlr.getNamespaceCount();
/* 215 */     String uri = null;
/* 216 */     String prefix = null;
/* 217 */     NamespaceImpl attr = null;
/* 218 */     for (int i = 0; i < count; i++) {
/* 219 */       uri = xmlr.getNamespaceURI(i);
/* 220 */       prefix = xmlr.getNamespacePrefix(i);
/* 221 */       if (prefix == null) {
/* 222 */         prefix = "";
/*     */       }
/* 224 */       attr = new NamespaceImpl(prefix, uri);
/* 225 */       event.addNamespaceAttribute(attr);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void fillNamespaceAttributes(EndElementEvent event, XMLStreamReader xmlr) {
/* 230 */     int count = xmlr.getNamespaceCount();
/* 231 */     String uri = null;
/* 232 */     String prefix = null;
/* 233 */     NamespaceImpl attr = null;
/* 234 */     for (int i = 0; i < count; i++) {
/* 235 */       uri = xmlr.getNamespaceURI(i);
/* 236 */       prefix = xmlr.getNamespacePrefix(i);
/* 237 */       if (prefix == null) {
/* 238 */         prefix = "";
/*     */       }
/* 240 */       attr = new NamespaceImpl(prefix, uri);
/* 241 */       event.addNamespace(attr);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNamespaceContext(StartElementEvent event, XMLStreamReader xmlr) {
/* 248 */     NamespaceContextWrapper contextWrapper = (NamespaceContextWrapper)xmlr.getNamespaceContext();
/* 249 */     NamespaceSupport ns = new NamespaceSupport(contextWrapper.getNamespaceContext());
/* 250 */     event.setNamespaceContext(new NamespaceContextWrapper(ns));
/*     */   }
/*     */   
/*     */   private QName getQName(XMLStreamReader xmlr) {
/* 254 */     return new QName(xmlr.getNamespaceURI(), xmlr.getLocalName(), xmlr
/* 255 */         .getPrefix());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/events/XMLEventAllocatorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */