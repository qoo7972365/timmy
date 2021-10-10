/*     */ package com.sun.xml.internal.ws.runtime.config;
/*     */ 
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.config.metro.dev.FeatureReader;
/*     */ import com.sun.xml.internal.ws.config.metro.util.ParserUtil;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TubelineFeatureReader
/*     */   implements FeatureReader
/*     */ {
/*  49 */   private static final Logger LOGGER = Logger.getLogger(TubelineFeatureReader.class);
/*  50 */   private static final QName NAME_ATTRIBUTE_NAME = new QName("name");
/*     */ 
/*     */   
/*     */   public TubelineFeature parse(XMLEventReader reader) throws WebServiceException {
/*     */     try {
/*  55 */       StartElement element = reader.nextEvent().asStartElement();
/*  56 */       boolean attributeEnabled = true;
/*  57 */       Iterator<Attribute> iterator = element.getAttributes();
/*  58 */       while (iterator.hasNext()) {
/*  59 */         Attribute nextAttribute = iterator.next();
/*  60 */         QName attributeName = nextAttribute.getName();
/*  61 */         if (ENABLED_ATTRIBUTE_NAME.equals(attributeName)) {
/*  62 */           attributeEnabled = ParserUtil.parseBooleanValue(nextAttribute.getValue()); continue;
/*  63 */         }  if (NAME_ATTRIBUTE_NAME.equals(attributeName)) {
/*     */           continue;
/*     */         }
/*     */         
/*  67 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Unexpected attribute"));
/*     */       } 
/*     */       
/*  70 */       return parseFactories(attributeEnabled, element, reader);
/*  71 */     } catch (XMLStreamException e) {
/*  72 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private TubelineFeature parseFactories(boolean enabled, StartElement element, XMLEventReader reader) throws WebServiceException {
/*  78 */     int elementRead = 0;
/*     */     
/*  80 */     while (reader.hasNext()) {
/*     */       try {
/*  82 */         XMLEvent event = reader.nextEvent();
/*  83 */         switch (event.getEventType()) {
/*     */           case 5:
/*     */             continue;
/*     */           case 4:
/*  87 */             if (event.asCharacters().isWhiteSpace()) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/*  92 */             throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("No character data allowed, was " + event.asCharacters()));
/*     */ 
/*     */           
/*     */           case 1:
/*  96 */             elementRead++;
/*     */             continue;
/*     */           case 2:
/*  99 */             elementRead--;
/* 100 */             if (elementRead < 0) {
/* 101 */               EndElement endElement = event.asEndElement();
/* 102 */               if (!element.getName().equals(endElement.getName()))
/*     */               {
/* 104 */                 throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("End element does not match " + endElement));
/*     */               }
/*     */               break;
/*     */             } 
/*     */             continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 113 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Unexpected event, was " + event));
/*     */       }
/* 115 */       catch (XMLStreamException e) {
/*     */         
/* 117 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 122 */     return new TubelineFeature(enabled);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/runtime/config/TubelineFeatureReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */