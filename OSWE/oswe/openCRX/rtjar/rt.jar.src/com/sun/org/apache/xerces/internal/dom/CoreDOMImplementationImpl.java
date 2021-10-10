/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.RevalidationHandler;
/*     */ import com.sun.org.apache.xerces.internal.parsers.DOMParserImpl;
/*     */ import com.sun.org.apache.xerces.internal.parsers.DTDConfiguration;
/*     */ import com.sun.org.apache.xerces.internal.parsers.XIncludeAwareParserConfiguration;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*     */ import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xml.internal.serialize.DOMSerializerImpl;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.ls.DOMImplementationLS;
/*     */ import org.w3c.dom.ls.LSInput;
/*     */ import org.w3c.dom.ls.LSOutput;
/*     */ import org.w3c.dom.ls.LSParser;
/*     */ import org.w3c.dom.ls.LSSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoreDOMImplementationImpl
/*     */   implements DOMImplementation, DOMImplementationLS
/*     */ {
/*     */   private static final int SIZE = 2;
/*  66 */   private RevalidationHandler[] validators = new RevalidationHandler[2];
/*     */   
/*  68 */   private RevalidationHandler[] dtdValidators = new RevalidationHandler[2];
/*  69 */   private int freeValidatorIndex = -1;
/*  70 */   private int freeDTDValidatorIndex = -1;
/*  71 */   private int currentSize = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private int docAndDoctypeCounter = 0;
/*     */ 
/*     */ 
/*     */   
/*  80 */   static CoreDOMImplementationImpl singleton = new CoreDOMImplementationImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DOMImplementation getDOMImplementation() {
/*  87 */     return singleton;
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
/*     */   public boolean hasFeature(String feature, String version) {
/* 109 */     boolean anyVersion = (version == null || version.length() == 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     if (feature.equalsIgnoreCase("+XPath") && (anyVersion || version
/* 118 */       .equals("3.0"))) {
/*     */       try {
/* 120 */         Class<?> xpathClass = ObjectFactory.findProviderClass("com.sun.org.apache.xpath.internal.domapi.XPathEvaluatorImpl", true);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 125 */         Class[] interfaces = xpathClass.getInterfaces();
/* 126 */         for (int i = 0; i < interfaces.length; i++) {
/* 127 */           if (interfaces[i].getName().equals("org.w3c.dom.xpath.XPathEvaluator"))
/*     */           {
/* 129 */             return true;
/*     */           }
/*     */         } 
/* 132 */       } catch (Exception e) {
/* 133 */         return false;
/*     */       } 
/* 135 */       return true;
/*     */     } 
/* 137 */     if (feature.startsWith("+")) {
/* 138 */       feature = feature.substring(1);
/*     */     }
/* 140 */     return ((feature
/* 141 */       .equalsIgnoreCase("Core") && (anyVersion || version
/*     */       
/* 143 */       .equals("1.0") || version
/* 144 */       .equals("2.0") || version
/* 145 */       .equals("3.0"))) || (feature
/* 146 */       .equalsIgnoreCase("XML") && (anyVersion || version
/*     */       
/* 148 */       .equals("1.0") || version
/* 149 */       .equals("2.0") || version
/* 150 */       .equals("3.0"))) || (feature
/* 151 */       .equalsIgnoreCase("LS") && (anyVersion || version
/* 152 */       .equals("3.0"))));
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
/*     */   public DocumentType createDocumentType(String qualifiedName, String publicID, String systemID) {
/* 171 */     checkQName(qualifiedName);
/* 172 */     return new DocumentTypeImpl(null, qualifiedName, publicID, systemID);
/*     */   }
/*     */   
/*     */   final void checkQName(String qname) {
/* 176 */     int index = qname.indexOf(':');
/* 177 */     int lastIndex = qname.lastIndexOf(':');
/* 178 */     int length = qname.length();
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (index == 0 || index == length - 1 || lastIndex != index) {
/*     */       
/* 184 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
/*     */ 
/*     */ 
/*     */       
/* 188 */       throw new DOMException((short)14, msg);
/*     */     } 
/* 190 */     int start = 0;
/*     */     
/* 192 */     if (index > 0) {
/*     */       
/* 194 */       if (!XMLChar.isNCNameStart(qname.charAt(start))) {
/*     */         
/* 196 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*     */ 
/*     */ 
/*     */         
/* 200 */         throw new DOMException((short)5, msg);
/*     */       } 
/* 202 */       for (int j = 1; j < index; j++) {
/* 203 */         if (!XMLChar.isNCName(qname.charAt(j))) {
/*     */           
/* 205 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*     */ 
/*     */ 
/*     */           
/* 209 */           throw new DOMException((short)5, msg);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 214 */       start = index + 1;
/*     */     } 
/*     */ 
/*     */     
/* 218 */     if (!XMLChar.isNCNameStart(qname.charAt(start))) {
/*     */ 
/*     */       
/* 221 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*     */ 
/*     */ 
/*     */       
/* 225 */       throw new DOMException((short)5, msg);
/*     */     } 
/* 227 */     for (int i = start + 1; i < length; i++) {
/* 228 */       if (!XMLChar.isNCName(qname.charAt(i))) {
/*     */         
/* 230 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*     */ 
/*     */ 
/*     */         
/* 234 */         throw new DOMException((short)5, msg);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) throws DOMException {
/* 265 */     if (doctype != null && doctype.getOwnerDocument() != null) {
/*     */       
/* 267 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
/*     */ 
/*     */ 
/*     */       
/* 271 */       throw new DOMException((short)4, msg);
/*     */     } 
/* 273 */     CoreDocumentImpl doc = new CoreDocumentImpl(doctype);
/* 274 */     Element e = doc.createElementNS(namespaceURI, qualifiedName);
/* 275 */     doc.appendChild(e);
/* 276 */     return doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFeature(String feature, String version) {
/* 283 */     if (singleton.hasFeature(feature, version)) {
/* 284 */       if (feature.equalsIgnoreCase("+XPath")) {
/*     */         try {
/* 286 */           Class<?> xpathClass = ObjectFactory.findProviderClass("com.sun.org.apache.xpath.internal.domapi.XPathEvaluatorImpl", true);
/*     */ 
/*     */ 
/*     */           
/* 290 */           Class[] interfaces = xpathClass.getInterfaces();
/* 291 */           for (int i = 0; i < interfaces.length; i++) {
/* 292 */             if (interfaces[i].getName().equals("org.w3c.dom.xpath.XPathEvaluator"))
/*     */             {
/* 294 */               return xpathClass.newInstance();
/*     */             }
/*     */           } 
/* 297 */         } catch (Exception e) {
/* 298 */           return null;
/*     */         } 
/*     */       } else {
/* 301 */         return singleton;
/*     */       } 
/*     */     }
/* 304 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LSParser createLSParser(short mode, String schemaType) throws DOMException {
/* 353 */     if (mode != 1 || (schemaType != null && 
/* 354 */       !"http://www.w3.org/2001/XMLSchema".equals(schemaType) && 
/* 355 */       !"http://www.w3.org/TR/REC-xml".equals(schemaType))) {
/*     */       
/* 357 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/*     */ 
/*     */ 
/*     */       
/* 361 */       throw new DOMException((short)9, msg);
/*     */     } 
/* 363 */     if (schemaType != null && schemaType
/* 364 */       .equals("http://www.w3.org/TR/REC-xml")) {
/* 365 */       return new DOMParserImpl(new DTDConfiguration(), schemaType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 370 */     return new DOMParserImpl(new XIncludeAwareParserConfiguration(), schemaType);
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
/*     */   public LSSerializer createLSSerializer() {
/* 389 */     return new DOMSerializerImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LSInput createLSInput() {
/* 397 */     return new DOMInputImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized RevalidationHandler getValidator(String schemaType) {
/* 406 */     if (schemaType == "http://www.w3.org/2001/XMLSchema") {
/*     */ 
/*     */ 
/*     */       
/* 410 */       if (this.freeValidatorIndex < 0) {
/* 411 */         return 
/* 412 */           (RevalidationHandler)ObjectFactory.newInstance("com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator", 
/*     */             
/* 414 */             ObjectFactory.findClassLoader(), true);
/*     */       }
/*     */ 
/*     */       
/* 418 */       RevalidationHandler val = this.validators[this.freeValidatorIndex];
/* 419 */       this.validators[this.freeValidatorIndex--] = null;
/* 420 */       return val;
/*     */     } 
/* 422 */     if (schemaType == "http://www.w3.org/TR/REC-xml") {
/* 423 */       if (this.freeDTDValidatorIndex < 0) {
/* 424 */         return 
/* 425 */           (RevalidationHandler)ObjectFactory.newInstance("com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDValidator", 
/*     */             
/* 427 */             ObjectFactory.findClassLoader(), true);
/*     */       }
/*     */ 
/*     */       
/* 431 */       RevalidationHandler val = this.dtdValidators[this.freeDTDValidatorIndex];
/* 432 */       this.dtdValidators[this.freeDTDValidatorIndex--] = null;
/* 433 */       return val;
/*     */     } 
/* 435 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void releaseValidator(String schemaType, RevalidationHandler validator) {
/* 442 */     if (schemaType == "http://www.w3.org/2001/XMLSchema") {
/* 443 */       this.freeValidatorIndex++;
/* 444 */       if (this.validators.length == this.freeValidatorIndex) {
/*     */         
/* 446 */         this.currentSize += 2;
/* 447 */         RevalidationHandler[] newarray = new RevalidationHandler[this.currentSize];
/* 448 */         System.arraycopy(this.validators, 0, newarray, 0, this.validators.length);
/* 449 */         this.validators = newarray;
/*     */       } 
/* 451 */       this.validators[this.freeValidatorIndex] = validator;
/*     */     }
/* 453 */     else if (schemaType == "http://www.w3.org/TR/REC-xml") {
/* 454 */       this.freeDTDValidatorIndex++;
/* 455 */       if (this.dtdValidators.length == this.freeDTDValidatorIndex) {
/*     */         
/* 457 */         this.currentSize += 2;
/* 458 */         RevalidationHandler[] newarray = new RevalidationHandler[this.currentSize];
/* 459 */         System.arraycopy(this.dtdValidators, 0, newarray, 0, this.dtdValidators.length);
/* 460 */         this.dtdValidators = newarray;
/*     */       } 
/* 462 */       this.dtdValidators[this.freeDTDValidatorIndex] = validator;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized int assignDocumentNumber() {
/* 468 */     return ++this.docAndDoctypeCounter;
/*     */   }
/*     */   
/*     */   protected synchronized int assignDocTypeNumber() {
/* 472 */     return ++this.docAndDoctypeCounter;
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
/*     */   public LSOutput createLSOutput() {
/* 485 */     return new DOMOutputImpl();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/CoreDOMImplementationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */