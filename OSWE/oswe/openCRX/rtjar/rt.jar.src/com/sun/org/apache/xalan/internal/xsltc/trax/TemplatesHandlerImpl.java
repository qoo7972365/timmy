/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.CompilerException;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.SourceLoader;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.Stylesheet;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.SyntaxTreeNode;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.XSLTC;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Templates;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.URIResolver;
/*     */ import javax.xml.transform.sax.TemplatesHandler;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.Locator;
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
/*     */ public class TemplatesHandlerImpl
/*     */   implements ContentHandler, TemplatesHandler, SourceLoader
/*     */ {
/*     */   private String _systemId;
/*     */   private int _indentNumber;
/*  65 */   private URIResolver _uriResolver = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private TransformerFactoryImpl _tfactory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private Parser _parser = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private TemplatesImpl _templates = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TemplatesHandlerImpl(int indentNumber, TransformerFactoryImpl tfactory) {
/*  89 */     this._indentNumber = indentNumber;
/*  90 */     this._tfactory = tfactory;
/*     */ 
/*     */     
/*  93 */     XSLTC xsltc = new XSLTC(tfactory.getJdkXmlFeatures());
/*  94 */     if (tfactory.getFeature("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*  95 */       xsltc.setSecureProcessing(true);
/*     */     }
/*  97 */     xsltc.setProperty("http://javax.xml.XMLConstants/property/accessExternalStylesheet", tfactory
/*  98 */         .getAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet"));
/*  99 */     xsltc.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", tfactory
/* 100 */         .getAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD"));
/* 101 */     xsltc.setProperty("http://apache.org/xml/properties/security-manager", tfactory
/* 102 */         .getAttribute("http://apache.org/xml/properties/security-manager"));
/*     */ 
/*     */     
/* 105 */     if ("true".equals(tfactory.getAttribute("enable-inlining"))) {
/* 106 */       xsltc.setTemplateInlining(true);
/*     */     } else {
/* 108 */       xsltc.setTemplateInlining(false);
/*     */     } 
/* 110 */     this._parser = xsltc.getParser();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 120 */     return this._systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSystemId(String id) {
/* 130 */     this._systemId = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURIResolver(URIResolver resolver) {
/* 137 */     this._uriResolver = resolver;
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
/*     */   public Templates getTemplates() {
/* 150 */     return this._templates;
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
/*     */   public InputSource loadSource(String href, String context, XSLTC xsltc) {
/*     */     try {
/* 165 */       Source source = this._uriResolver.resolve(href, context);
/* 166 */       if (source != null) {
/* 167 */         return Util.getInputSource(xsltc, source);
/*     */       }
/*     */     }
/* 170 */     catch (TransformerException transformerException) {}
/*     */ 
/*     */     
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() {
/* 182 */     XSLTC xsltc = this._parser.getXSLTC();
/* 183 */     xsltc.init();
/* 184 */     xsltc.setOutputType(2);
/* 185 */     this._parser.startDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 192 */     this._parser.endDocument();
/*     */ 
/*     */     
/*     */     try {
/* 196 */       XSLTC xsltc = this._parser.getXSLTC();
/*     */ 
/*     */ 
/*     */       
/* 200 */       if (this._systemId != null) {
/* 201 */         transletName = Util.baseName(this._systemId);
/*     */       } else {
/*     */         
/* 204 */         transletName = (String)this._tfactory.getAttribute("translet-name");
/*     */       } 
/* 206 */       xsltc.setClassName(transletName);
/*     */ 
/*     */       
/* 209 */       String transletName = xsltc.getClassName();
/*     */       
/* 211 */       Stylesheet stylesheet = null;
/* 212 */       SyntaxTreeNode root = this._parser.getDocumentRoot();
/*     */ 
/*     */       
/* 215 */       if (!this._parser.errorsFound() && root != null) {
/*     */         
/* 217 */         stylesheet = this._parser.makeStylesheet(root);
/* 218 */         stylesheet.setSystemId(this._systemId);
/* 219 */         stylesheet.setParentStylesheet(null);
/*     */         
/* 221 */         if (xsltc.getTemplateInlining()) {
/* 222 */           stylesheet.setTemplateInlining(true);
/*     */         } else {
/* 224 */           stylesheet.setTemplateInlining(false);
/*     */         } 
/*     */         
/* 227 */         if (this._uriResolver != null) {
/* 228 */           stylesheet.setSourceLoader(this);
/*     */         }
/*     */         
/* 231 */         this._parser.setCurrentStylesheet(stylesheet);
/*     */ 
/*     */         
/* 234 */         xsltc.setStylesheet(stylesheet);
/*     */ 
/*     */         
/* 237 */         this._parser.createAST(stylesheet);
/*     */       } 
/*     */ 
/*     */       
/* 241 */       if (!this._parser.errorsFound() && stylesheet != null) {
/* 242 */         stylesheet.setMultiDocument(xsltc.isMultiDocument());
/* 243 */         stylesheet.setHasIdCall(xsltc.hasIdCall());
/*     */ 
/*     */         
/* 246 */         synchronized (xsltc.getClass()) {
/* 247 */           stylesheet.translate();
/*     */         } 
/*     */       } 
/*     */       
/* 251 */       if (!this._parser.errorsFound()) {
/*     */         
/* 253 */         byte[][] bytecodes = xsltc.getBytecodes();
/* 254 */         if (bytecodes != null) {
/* 255 */           this
/*     */             
/* 257 */             ._templates = new TemplatesImpl(xsltc.getBytecodes(), transletName, this._parser.getOutputProperties(), this._indentNumber, this._tfactory);
/*     */ 
/*     */           
/* 260 */           if (this._uriResolver != null) {
/* 261 */             this._templates.setURIResolver(this._uriResolver);
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/* 266 */         StringBuilder errorMessage = new StringBuilder();
/* 267 */         ArrayList<ErrorMsg> errors = this._parser.getErrors();
/* 268 */         int count = errors.size();
/* 269 */         for (int i = 0; i < count; i++) {
/* 270 */           if (errorMessage.length() > 0)
/* 271 */             errorMessage.append('\n'); 
/* 272 */           errorMessage.append(((ErrorMsg)errors.get(i)).toString());
/*     */         } 
/* 274 */         throw new SAXException("JAXP_COMPILE_ERR", new TransformerException(errorMessage.toString()));
/*     */       }
/*     */     
/* 277 */     } catch (CompilerException e) {
/* 278 */       throw new SAXException("JAXP_COMPILE_ERR", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) {
/* 286 */     this._parser.startPrefixMapping(prefix, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) {
/* 293 */     this._parser.endPrefixMapping(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localname, String qname, Attributes attributes) throws SAXException {
/* 302 */     this._parser.startElement(uri, localname, qname, attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localname, String qname) {
/* 309 */     this._parser.endElement(uri, localname, qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) {
/* 316 */     this._parser.characters(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String name, String value) {
/* 323 */     this._parser.processingInstruction(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) {
/* 330 */     this._parser.ignorableWhitespace(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) {
/* 337 */     this._parser.skippedEntity(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 344 */     setSystemId(locator.getSystemId());
/* 345 */     this._parser.setDocumentLocator(locator);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/TemplatesHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */