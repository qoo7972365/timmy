/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.utils.XMLReaderManager;
/*     */ import java.io.IOException;
/*     */ import javax.xml.transform.ErrorListener;
/*     */ import javax.xml.transform.Templates;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
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
/*     */ public class TrAXFilter
/*     */   extends XMLFilterImpl
/*     */ {
/*     */   private Templates _templates;
/*     */   private TransformerImpl _transformer;
/*     */   private TransformerHandlerImpl _transformerHandler;
/*     */   private boolean _overrideDefaultParser;
/*     */   
/*     */   public TrAXFilter(Templates templates) throws TransformerConfigurationException {
/*  57 */     this._templates = templates;
/*  58 */     this._transformer = (TransformerImpl)templates.newTransformer();
/*  59 */     this._transformerHandler = new TransformerHandlerImpl(this._transformer);
/*  60 */     this._overrideDefaultParser = this._transformer.overrideDefaultParser();
/*     */   }
/*     */   
/*     */   public Transformer getTransformer() {
/*  64 */     return this._transformer;
/*     */   }
/*     */   
/*     */   private void createParent() throws SAXException {
/*  68 */     XMLReader parent = JdkXmlUtils.getXMLReader(this._overrideDefaultParser, this._transformer
/*  69 */         .isSecureProcessing());
/*     */ 
/*     */     
/*  72 */     setParent(parent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(InputSource input) throws SAXException, IOException {
/*  78 */     XMLReader managedReader = null;
/*     */     
/*     */     try {
/*  81 */       if (getParent() == null) {
/*     */         
/*     */         try {
/*  84 */           managedReader = XMLReaderManager.getInstance(this._overrideDefaultParser).getXMLReader();
/*  85 */           setParent(managedReader);
/*  86 */         } catch (SAXException e) {
/*  87 */           throw new SAXException(e.toString());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  92 */       getParent().parse(input);
/*     */     } finally {
/*  94 */       if (managedReader != null) {
/*  95 */         XMLReaderManager.getInstance(this._overrideDefaultParser).releaseXMLReader(managedReader);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(String systemId) throws SAXException, IOException {
/* 102 */     parse(new InputSource(systemId));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentHandler(ContentHandler handler) {
/* 107 */     this._transformerHandler.setResult(new SAXResult(handler));
/* 108 */     if (getParent() == null) {
/*     */       try {
/* 110 */         createParent();
/*     */       }
/* 112 */       catch (SAXException e) {
/*     */         return;
/*     */       } 
/*     */     }
/* 116 */     getParent().setContentHandler(this._transformerHandler);
/*     */   }
/*     */   
/*     */   public void setErrorListener(ErrorListener handler) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/TrAXFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */