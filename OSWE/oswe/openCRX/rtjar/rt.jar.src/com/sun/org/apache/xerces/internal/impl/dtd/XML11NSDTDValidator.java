/*     */ package com.sun.org.apache.xerces.internal.impl.dtd;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XML11NSDTDValidator
/*     */   extends XML11DTDValidator
/*     */ {
/* 107 */   private QName fAttributeQName = new QName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void startNamespaceScope(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 114 */     this.fNamespaceContext.pushContext();
/*     */     
/* 116 */     if (element.prefix == XMLSymbols.PREFIX_XMLNS) {
/* 117 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[] { element.rawname }, (short)2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     int length = attributes.getLength();
/* 126 */     for (int i = 0; i < length; i++) {
/* 127 */       String localpart = attributes.getLocalName(i);
/* 128 */       String str1 = attributes.getPrefix(i);
/*     */ 
/*     */       
/* 131 */       if (str1 == XMLSymbols.PREFIX_XMLNS || (str1 == XMLSymbols.EMPTY_STRING && localpart == XMLSymbols.PREFIX_XMLNS)) {
/*     */ 
/*     */ 
/*     */         
/* 135 */         String uri = this.fSymbolTable.addSymbol(attributes.getValue(i));
/*     */ 
/*     */         
/* 138 */         if (str1 == XMLSymbols.PREFIX_XMLNS && localpart == XMLSymbols.PREFIX_XMLNS) {
/* 139 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { attributes
/*     */ 
/*     */                 
/* 142 */                 .getQName(i) }, (short)2);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 147 */         if (uri == NamespaceContext.XMLNS_URI) {
/* 148 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { attributes
/*     */ 
/*     */                 
/* 151 */                 .getQName(i) }, (short)2);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 156 */         if (localpart == XMLSymbols.PREFIX_XML) {
/* 157 */           if (uri != NamespaceContext.XML_URI) {
/* 158 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { attributes
/*     */ 
/*     */                   
/* 161 */                   .getQName(i) }, (short)2);
/*     */ 
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 167 */         else if (uri == NamespaceContext.XML_URI) {
/* 168 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { attributes
/*     */ 
/*     */                 
/* 171 */                 .getQName(i) }, (short)2);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 176 */         str1 = (localpart != XMLSymbols.PREFIX_XMLNS) ? localpart : XMLSymbols.EMPTY_STRING;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 181 */         this.fNamespaceContext.declarePrefix(str1, (uri.length() != 0) ? uri : null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 186 */     String prefix = (element.prefix != null) ? element.prefix : XMLSymbols.EMPTY_STRING;
/* 187 */     element.uri = this.fNamespaceContext.getURI(prefix);
/* 188 */     if (element.prefix == null && element.uri != null) {
/* 189 */       element.prefix = XMLSymbols.EMPTY_STRING;
/*     */     }
/* 191 */     if (element.prefix != null && element.uri == null) {
/* 192 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[] { element.prefix, element.rawname }, (short)2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     for (int j = 0; j < length; j++) {
/* 201 */       attributes.getName(j, this.fAttributeQName);
/* 202 */       String aprefix = (this.fAttributeQName.prefix != null) ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
/* 203 */       String arawname = this.fAttributeQName.rawname;
/* 204 */       if (arawname == XMLSymbols.PREFIX_XMLNS) {
/* 205 */         this.fAttributeQName.uri = this.fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS);
/* 206 */         attributes.setName(j, this.fAttributeQName);
/* 207 */       } else if (aprefix != XMLSymbols.EMPTY_STRING) {
/* 208 */         this.fAttributeQName.uri = this.fNamespaceContext.getURI(aprefix);
/* 209 */         if (this.fAttributeQName.uri == null) {
/* 210 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[] { element.rawname, arawname, aprefix }, (short)2);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 216 */         attributes.setName(j, this.fAttributeQName);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     int attrCount = attributes.getLength();
/* 223 */     for (int k = 0; k < attrCount - 1; k++) {
/* 224 */       String auri = attributes.getURI(k);
/* 225 */       if (auri != null && auri != NamespaceContext.XMLNS_URI) {
/*     */ 
/*     */         
/* 228 */         String alocalpart = attributes.getLocalName(k);
/* 229 */         for (int m = k + 1; m < attrCount; m++) {
/* 230 */           String blocalpart = attributes.getLocalName(m);
/* 231 */           String buri = attributes.getURI(m);
/* 232 */           if (alocalpart == blocalpart && auri == buri) {
/* 233 */             this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[] { element.rawname, alocalpart, auri }, (short)2);
/*     */           }
/*     */         } 
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
/*     */   protected void endNamespaceScope(QName element, Augmentations augs, boolean isEmpty) throws XNIException {
/* 249 */     String eprefix = (element.prefix != null) ? element.prefix : XMLSymbols.EMPTY_STRING;
/* 250 */     element.uri = this.fNamespaceContext.getURI(eprefix);
/* 251 */     if (element.uri != null) {
/* 252 */       element.prefix = eprefix;
/*     */     }
/*     */ 
/*     */     
/* 256 */     if (this.fDocumentHandler != null && 
/* 257 */       !isEmpty) {
/* 258 */       this.fDocumentHandler.endElement(element, augs);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 263 */     this.fNamespaceContext.popContext();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/XML11NSDTDValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */