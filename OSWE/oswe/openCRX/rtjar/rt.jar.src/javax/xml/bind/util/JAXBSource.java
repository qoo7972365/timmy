/*     */ package javax.xml.bind.util;
/*     */ 
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLFilter;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.XMLFilterImpl;
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
/*     */ public class JAXBSource
/*     */   extends SAXSource
/*     */ {
/*     */   private final Marshaller marshaller;
/*     */   private final Object contentObject;
/*     */   
/*     */   public JAXBSource(JAXBContext context, Object contentObject) throws JAXBException {
/* 110 */     this((context == null) ? 
/*     */         
/* 112 */         assertionFailed(Messages.format("JAXBSource.NullContext")) : context
/* 113 */         .createMarshaller(), (contentObject == null) ? 
/*     */ 
/*     */         
/* 116 */         assertionFailed(Messages.format("JAXBSource.NullContent")) : contentObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBSource(Marshaller marshaller, Object contentObject) throws JAXBException {
/* 138 */     if (marshaller == null) {
/* 139 */       throw new JAXBException(
/* 140 */           Messages.format("JAXBSource.NullMarshaller"));
/*     */     }
/* 142 */     if (contentObject == null) {
/* 143 */       throw new JAXBException(
/* 144 */           Messages.format("JAXBSource.NullContent"));
/*     */     }
/* 146 */     this.marshaller = marshaller;
/* 147 */     this.contentObject = contentObject;
/*     */     
/* 149 */     setXMLReader(this.pseudoParser);
/*     */     
/* 151 */     setInputSource(new InputSource());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   private final XMLReader pseudoParser = new XMLReader() { private LexicalHandler lexicalHandler;
/*     */       public boolean getFeature(String name) throws SAXNotRecognizedException {
/* 162 */         if (name.equals("http://xml.org/sax/features/namespaces"))
/* 163 */           return true; 
/* 164 */         if (name.equals("http://xml.org/sax/features/namespace-prefixes"))
/* 165 */           return false; 
/* 166 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       private EntityResolver entityResolver; private DTDHandler dtdHandler;
/*     */       public void setFeature(String name, boolean value) throws SAXNotRecognizedException {
/* 170 */         if (name.equals("http://xml.org/sax/features/namespaces") && value)
/*     */           return; 
/* 172 */         if (name.equals("http://xml.org/sax/features/namespace-prefixes") && !value)
/*     */           return; 
/* 174 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       
/*     */       public Object getProperty(String name) throws SAXNotRecognizedException {
/* 178 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 179 */           return this.lexicalHandler;
/*     */         }
/* 181 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       
/*     */       public void setProperty(String name, Object value) throws SAXNotRecognizedException {
/* 185 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 186 */           this.lexicalHandler = (LexicalHandler)value;
/*     */           return;
/*     */         } 
/* 189 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void setEntityResolver(EntityResolver resolver) {
/* 197 */         this.entityResolver = resolver;
/*     */       }
/*     */       public EntityResolver getEntityResolver() {
/* 200 */         return this.entityResolver;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setDTDHandler(DTDHandler handler) {
/* 205 */         this.dtdHandler = handler;
/*     */       }
/*     */       public DTDHandler getDTDHandler() {
/* 208 */         return this.dtdHandler;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       private XMLFilter repeater = new XMLFilterImpl(); private ErrorHandler errorHandler;
/*     */       
/*     */       public void setContentHandler(ContentHandler handler) {
/* 217 */         this.repeater.setContentHandler(handler);
/*     */       }
/*     */       public ContentHandler getContentHandler() {
/* 220 */         return this.repeater.getContentHandler();
/*     */       }
/*     */ 
/*     */       
/*     */       public void setErrorHandler(ErrorHandler handler) {
/* 225 */         this.errorHandler = handler;
/*     */       }
/*     */       public ErrorHandler getErrorHandler() {
/* 228 */         return this.errorHandler;
/*     */       }
/*     */       
/*     */       public void parse(InputSource input) throws SAXException {
/* 232 */         parse();
/*     */       }
/*     */       
/*     */       public void parse(String systemId) throws SAXException {
/* 236 */         parse();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void parse() throws SAXException {
/*     */         try {
/* 244 */           JAXBSource.this.marshaller.marshal(JAXBSource.this.contentObject, (XMLFilterImpl)this.repeater);
/* 245 */         } catch (JAXBException e) {
/*     */ 
/*     */           
/* 248 */           SAXParseException se = new SAXParseException(e.getMessage(), null, null, -1, -1, (Exception)e);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 253 */           if (this.errorHandler != null) {
/* 254 */             this.errorHandler.fatalError(se);
/*     */           }
/*     */ 
/*     */           
/* 258 */           throw se;
/*     */         } 
/*     */       } }
/*     */   ;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Marshaller assertionFailed(String message) throws JAXBException {
/* 270 */     throw new JAXBException(message);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/util/JAXBSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */