/*      */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.DOMCache;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.Translet;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.DOMWSFilter;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.XSLTCDTMManager;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.runtime.output.TransletOutputHandlerFactory;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*      */ import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
/*      */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*      */ import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLReaderManager;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.net.UnknownServiceException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.stream.XMLEventReader;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.transform.ErrorListener;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.URIResolver;
/*      */ import javax.xml.transform.dom.DOMResult;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.sax.SAXResult;
/*      */ import javax.xml.transform.sax.SAXSource;
/*      */ import javax.xml.transform.stax.StAXResult;
/*      */ import javax.xml.transform.stax.StAXSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ import jdk.xml.internal.JdkXmlUtils;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.XMLReader;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class TransformerImpl
/*      */   extends Transformer
/*      */   implements DOMCache, ErrorListener
/*      */ {
/*      */   private static final String LEXICAL_HANDLER_PROPERTY = "http://xml.org/sax/properties/lexical-handler";
/*      */   private static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
/*  110 */   private AbstractTranslet _translet = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  115 */   private String _method = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  120 */   private String _encoding = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  125 */   private String _sourceSystemId = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  130 */   private ErrorListener _errorListener = this;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  135 */   private URIResolver _uriResolver = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private Properties _properties;
/*      */ 
/*      */   
/*      */   private Properties _propertiesClone;
/*      */ 
/*      */   
/*  145 */   private TransletOutputHandlerFactory _tohFactory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  150 */   private DOM _dom = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int _indentNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  161 */   private TransformerFactoryImpl _tfactory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  166 */   private OutputStream _ostream = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   private XSLTCDTMManager _dtmManager = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XMLReaderManager _readerManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _isIdentity = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _isSecureProcessing = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _overrideDefaultParser;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  203 */   private String _accessExternalDTD = "all";
/*      */ 
/*      */ 
/*      */   
/*      */   private XMLSecurityManager _securityManager;
/*      */ 
/*      */ 
/*      */   
/*  211 */   private Map<String, Object> _parameters = null;
/*      */ 
/*      */ 
/*      */   
/*      */   static class MessageHandler
/*      */     extends com.sun.org.apache.xalan.internal.xsltc.runtime.MessageHandler
/*      */   {
/*      */     private ErrorListener _errorListener;
/*      */ 
/*      */ 
/*      */     
/*      */     public MessageHandler(ErrorListener errorListener) {
/*  223 */       this._errorListener = errorListener;
/*      */     }
/*      */ 
/*      */     
/*      */     public void displayMessage(String msg) {
/*  228 */       if (this._errorListener == null) {
/*  229 */         System.err.println(msg);
/*      */       } else {
/*      */         
/*      */         try {
/*  233 */           this._errorListener.warning(new TransformerException(msg));
/*      */         }
/*  235 */         catch (TransformerException transformerException) {}
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TransformerImpl(Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory) {
/*  245 */     this(null, outputProperties, indentNumber, tfactory);
/*  246 */     this._isIdentity = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TransformerImpl(Translet translet, Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory) {
/*  253 */     this._translet = (AbstractTranslet)translet;
/*  254 */     this._properties = createOutputProperties(outputProperties);
/*  255 */     this._propertiesClone = (Properties)this._properties.clone();
/*  256 */     this._indentNumber = indentNumber;
/*  257 */     this._tfactory = tfactory;
/*  258 */     this._overrideDefaultParser = this._tfactory.overrideDefaultParser();
/*  259 */     this._accessExternalDTD = (String)this._tfactory.getAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD");
/*  260 */     this._securityManager = (XMLSecurityManager)this._tfactory.getAttribute("http://apache.org/xml/properties/security-manager");
/*  261 */     this._readerManager = XMLReaderManager.getInstance(this._overrideDefaultParser);
/*  262 */     this._readerManager.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", this._accessExternalDTD);
/*  263 */     this._readerManager.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", this._isSecureProcessing);
/*  264 */     this._readerManager.setProperty("http://apache.org/xml/properties/security-manager", this._securityManager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSecureProcessing() {
/*  272 */     return this._isSecureProcessing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecureProcessing(boolean flag) {
/*  279 */     this._isSecureProcessing = flag;
/*  280 */     this._readerManager.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", this._isSecureProcessing);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean overrideDefaultParser() {
/*  286 */     return this._overrideDefaultParser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOverrideDefaultParser(boolean flag) {
/*  293 */     this._overrideDefaultParser = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractTranslet getTranslet() {
/*  301 */     return this._translet;
/*      */   }
/*      */   
/*      */   public boolean isIdentity() {
/*  305 */     return this._isIdentity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transform(Source source, Result result) throws TransformerException {
/*  319 */     if (!this._isIdentity) {
/*  320 */       if (this._translet == null) {
/*  321 */         ErrorMsg err = new ErrorMsg("JAXP_NO_TRANSLET_ERR");
/*  322 */         throw new TransformerException(err.toString());
/*      */       } 
/*      */       
/*  325 */       transferOutputProperties(this._translet);
/*      */     } 
/*      */     
/*  328 */     SerializationHandler toHandler = getOutputHandler(result);
/*  329 */     if (toHandler == null) {
/*  330 */       ErrorMsg err = new ErrorMsg("JAXP_NO_HANDLER_ERR");
/*  331 */       throw new TransformerException(err.toString());
/*      */     } 
/*      */     
/*  334 */     if (this._uriResolver != null && !this._isIdentity) {
/*  335 */       this._translet.setDOMCache(this);
/*      */     }
/*      */ 
/*      */     
/*  339 */     if (this._isIdentity) {
/*  340 */       transferOutputProperties(toHandler);
/*      */     }
/*      */     
/*  343 */     transform(source, toHandler, this._encoding);
/*      */     try {
/*  345 */       if (result instanceof DOMResult) {
/*  346 */         ((DOMResult)result).setNode(this._tohFactory.getNode());
/*  347 */       } else if (result instanceof StAXResult) {
/*  348 */         if (((StAXResult)result).getXMLEventWriter() != null) {
/*      */           
/*  350 */           this._tohFactory.getXMLEventWriter().flush();
/*      */         }
/*  352 */         else if (((StAXResult)result).getXMLStreamWriter() != null) {
/*  353 */           this._tohFactory.getXMLStreamWriter().flush();
/*      */         }
/*      */       
/*      */       } 
/*  357 */     } catch (Exception e) {
/*  358 */       System.out.println("Result writing error");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SerializationHandler getOutputHandler(Result result) throws TransformerException {
/*  371 */     this._method = (String)this._properties.get("method");
/*      */ 
/*      */     
/*  374 */     this._encoding = this._properties.getProperty("encoding");
/*      */     
/*  376 */     this._tohFactory = TransletOutputHandlerFactory.newInstance(this._overrideDefaultParser);
/*  377 */     this._tohFactory.setEncoding(this._encoding);
/*  378 */     if (this._method != null) {
/*  379 */       this._tohFactory.setOutputMethod(this._method);
/*      */     }
/*      */ 
/*      */     
/*  383 */     if (this._indentNumber >= 0) {
/*  384 */       this._tohFactory.setIndentNumber(this._indentNumber);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  390 */       if (result instanceof SAXResult) {
/*  391 */         SAXResult target = (SAXResult)result;
/*  392 */         ContentHandler handler = target.getHandler();
/*      */         
/*  394 */         this._tohFactory.setHandler(handler);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  401 */         LexicalHandler lexicalHandler = target.getLexicalHandler();
/*      */         
/*  403 */         if (lexicalHandler != null) {
/*  404 */           this._tohFactory.setLexicalHandler(lexicalHandler);
/*      */         }
/*      */         
/*  407 */         this._tohFactory.setOutputType(1);
/*  408 */         return this._tohFactory.getSerializationHandler();
/*      */       } 
/*  410 */       if (result instanceof StAXResult) {
/*  411 */         if (((StAXResult)result).getXMLEventWriter() != null) {
/*  412 */           this._tohFactory.setXMLEventWriter(((StAXResult)result).getXMLEventWriter());
/*  413 */         } else if (((StAXResult)result).getXMLStreamWriter() != null) {
/*  414 */           this._tohFactory.setXMLStreamWriter(((StAXResult)result).getXMLStreamWriter());
/*  415 */         }  this._tohFactory.setOutputType(3);
/*  416 */         return this._tohFactory.getSerializationHandler();
/*      */       } 
/*  418 */       if (result instanceof DOMResult) {
/*  419 */         this._tohFactory.setNode(((DOMResult)result).getNode());
/*  420 */         this._tohFactory.setNextSibling(((DOMResult)result).getNextSibling());
/*  421 */         this._tohFactory.setOutputType(2);
/*  422 */         return this._tohFactory.getSerializationHandler();
/*      */       } 
/*  424 */       if (result instanceof StreamResult)
/*      */       {
/*  426 */         StreamResult target = (StreamResult)result;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  432 */         this._tohFactory.setOutputType(0);
/*      */ 
/*      */         
/*  435 */         Writer writer = target.getWriter();
/*  436 */         if (writer != null) {
/*  437 */           this._tohFactory.setWriter(writer);
/*  438 */           return this._tohFactory.getSerializationHandler();
/*      */         } 
/*      */ 
/*      */         
/*  442 */         OutputStream ostream = target.getOutputStream();
/*  443 */         if (ostream != null) {
/*  444 */           this._tohFactory.setOutputStream(ostream);
/*  445 */           return this._tohFactory.getSerializationHandler();
/*      */         } 
/*      */ 
/*      */         
/*  449 */         String systemId = result.getSystemId();
/*  450 */         if (systemId == null) {
/*  451 */           ErrorMsg err = new ErrorMsg("JAXP_NO_RESULT_ERR");
/*  452 */           throw new TransformerException(err.toString());
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  459 */         if (systemId.startsWith("file:")) {
/*      */ 
/*      */           
/*      */           try {
/*      */             
/*  464 */             URI uri = new URI(systemId);
/*  465 */             systemId = "file:";
/*      */             
/*  467 */             String host = uri.getHost();
/*  468 */             String path = uri.getPath();
/*  469 */             if (path == null) {
/*  470 */               path = "";
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  475 */             if (host != null) {
/*  476 */               systemId = systemId + "//" + host + path;
/*      */             } else {
/*  478 */               systemId = systemId + "//" + path;
/*      */             }
/*      */           
/*  481 */           } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */           
/*  485 */           URL url = new URL(systemId);
/*  486 */           this._ostream = new FileOutputStream(url.getFile());
/*  487 */           this._tohFactory.setOutputStream(this._ostream);
/*  488 */           return this._tohFactory.getSerializationHandler();
/*      */         } 
/*  490 */         if (systemId.startsWith("http:")) {
/*  491 */           URL url = new URL(systemId);
/*  492 */           URLConnection connection = url.openConnection();
/*  493 */           this._tohFactory.setOutputStream(this._ostream = connection.getOutputStream());
/*  494 */           return this._tohFactory.getSerializationHandler();
/*      */         } 
/*      */ 
/*      */         
/*  498 */         this._tohFactory.setOutputStream(this._ostream = new FileOutputStream(new File(systemId)));
/*      */         
/*  500 */         return this._tohFactory.getSerializationHandler();
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  505 */     catch (UnknownServiceException e) {
/*  506 */       throw new TransformerException(e);
/*      */     }
/*  508 */     catch (ParserConfigurationException e) {
/*  509 */       throw new TransformerException(e);
/*      */     
/*      */     }
/*  512 */     catch (IOException e) {
/*  513 */       throw new TransformerException(e);
/*      */     } 
/*  515 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDOM(DOM dom) {
/*  522 */     this._dom = dom;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DOM getDOM(Source source) throws TransformerException {
/*      */     try {
/*      */       DOM dom;
/*  532 */       if (source != null) {
/*      */         DTMWSFilter wsfilter;
/*  534 */         if (this._translet != null && this._translet instanceof com.sun.org.apache.xalan.internal.xsltc.StripFilter) {
/*  535 */           wsfilter = new DOMWSFilter(this._translet);
/*      */         } else {
/*  537 */           wsfilter = null;
/*      */         } 
/*      */         
/*  540 */         boolean hasIdCall = (this._translet != null) ? this._translet.hasIdCall() : false;
/*      */ 
/*      */         
/*  543 */         if (this._dtmManager == null) {
/*  544 */           this
/*  545 */             ._dtmManager = this._tfactory.createNewDTMManagerInstance();
/*  546 */           this._dtmManager.setOverrideDefaultParser(this._overrideDefaultParser);
/*      */         } 
/*  548 */         dom = (DOM)this._dtmManager.getDTM(source, false, wsfilter, true, false, false, 0, hasIdCall);
/*      */       }
/*  550 */       else if (this._dom != null) {
/*  551 */         dom = this._dom;
/*  552 */         this._dom = null;
/*      */       } else {
/*  554 */         return null;
/*      */       } 
/*      */       
/*  557 */       if (!this._isIdentity)
/*      */       {
/*      */         
/*  560 */         this._translet.prepassDocument(dom);
/*      */       }
/*      */       
/*  563 */       return dom;
/*      */     
/*      */     }
/*  566 */     catch (Exception e) {
/*  567 */       DOM dom; if (this._errorListener != null) {
/*  568 */         postErrorToListener(dom.getMessage());
/*      */       }
/*  570 */       throw new TransformerException(dom);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TransformerFactoryImpl getTransformerFactory() {
/*  579 */     return this._tfactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TransletOutputHandlerFactory getTransletOutputHandlerFactory() {
/*  587 */     return this._tohFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformIdentity(Source source, SerializationHandler handler) throws Exception {
/*  594 */     if (source != null) {
/*  595 */       this._sourceSystemId = source.getSystemId();
/*      */     }
/*      */     
/*  598 */     if (source instanceof StreamSource) {
/*  599 */       StreamSource stream = (StreamSource)source;
/*  600 */       InputStream streamInput = stream.getInputStream();
/*  601 */       Reader streamReader = stream.getReader();
/*  602 */       XMLReader reader = this._readerManager.getXMLReader();
/*      */       
/*      */       try {
/*      */         InputSource input;
/*      */         try {
/*  607 */           reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
/*  608 */           reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/*  609 */         } catch (SAXException sAXException) {}
/*      */ 
/*      */         
/*  612 */         reader.setContentHandler(handler);
/*      */ 
/*      */ 
/*      */         
/*  616 */         if (streamInput != null) {
/*  617 */           input = new InputSource(streamInput);
/*  618 */           input.setSystemId(this._sourceSystemId);
/*      */         }
/*  620 */         else if (streamReader != null) {
/*  621 */           input = new InputSource(streamReader);
/*  622 */           input.setSystemId(this._sourceSystemId);
/*      */         }
/*  624 */         else if (this._sourceSystemId != null) {
/*  625 */           input = new InputSource(this._sourceSystemId);
/*      */         } else {
/*      */           
/*  628 */           ErrorMsg err = new ErrorMsg("JAXP_NO_SOURCE_ERR");
/*  629 */           throw new TransformerException(err.toString());
/*      */         } 
/*      */         
/*  632 */         reader.parse(input);
/*      */       } finally {
/*  634 */         this._readerManager.releaseXMLReader(reader);
/*      */       } 
/*  636 */     } else if (source instanceof SAXSource) {
/*  637 */       SAXSource sax = (SAXSource)source;
/*  638 */       XMLReader reader = sax.getXMLReader();
/*  639 */       InputSource input = sax.getInputSource();
/*  640 */       boolean userReader = true;
/*      */ 
/*      */       
/*      */       try {
/*  644 */         if (reader == null) {
/*  645 */           reader = this._readerManager.getXMLReader();
/*  646 */           userReader = false;
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  651 */           reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
/*  652 */           reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/*  653 */         } catch (SAXException inputSource) {}
/*      */ 
/*      */         
/*  656 */         reader.setContentHandler(handler);
/*      */ 
/*      */         
/*  659 */         reader.parse(input);
/*      */       } finally {
/*  661 */         if (!userReader) {
/*  662 */           this._readerManager.releaseXMLReader(reader);
/*      */         }
/*      */       } 
/*  665 */     } else if (source instanceof StAXSource) {
/*  666 */       StAXSource staxSource = (StAXSource)source;
/*      */ 
/*      */       
/*  669 */       if (staxSource.getXMLEventReader() != null) {
/*  670 */         XMLEventReader xmlEventReader = staxSource.getXMLEventReader();
/*  671 */         StAXEvent2SAX staxevent2sax = new StAXEvent2SAX(xmlEventReader);
/*  672 */         staxevent2sax.setContentHandler(handler);
/*  673 */         staxevent2sax.parse();
/*  674 */         handler.flushPending();
/*  675 */       } else if (staxSource.getXMLStreamReader() != null) {
/*  676 */         XMLStreamReader xmlStreamReader = staxSource.getXMLStreamReader();
/*  677 */         StAXStream2SAX staxStream2SAX = new StAXStream2SAX(xmlStreamReader);
/*  678 */         staxStream2SAX.setContentHandler(handler);
/*  679 */         staxStream2SAX.parse();
/*  680 */         handler.flushPending();
/*      */       } 
/*  682 */     } else if (source instanceof DOMSource) {
/*  683 */       DOMSource domsrc = (DOMSource)source;
/*  684 */       (new DOM2TO(domsrc.getNode(), handler)).parse();
/*  685 */     } else if (source instanceof XSLTCSource) {
/*  686 */       DOM dom = ((XSLTCSource)source).getDOM(null, this._translet);
/*  687 */       ((SAXImpl)dom).copy(handler);
/*      */     } else {
/*  689 */       ErrorMsg err = new ErrorMsg("JAXP_NO_SOURCE_ERR");
/*  690 */       throw new TransformerException(err.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transform(Source source, SerializationHandler handler, String encoding) throws TransformerException {
/*      */     try {
/*  709 */       if ((source instanceof StreamSource && source.getSystemId() == null && ((StreamSource)source)
/*  710 */         .getInputStream() == null && ((StreamSource)source)
/*  711 */         .getReader() == null) || (source instanceof SAXSource && ((SAXSource)source)
/*      */         
/*  713 */         .getInputSource() == null && ((SAXSource)source)
/*  714 */         .getXMLReader() == null) || (source instanceof DOMSource && ((DOMSource)source)
/*      */         
/*  716 */         .getNode() == null)) {
/*  717 */         DocumentBuilderFactory builderF = JdkXmlUtils.getDOMFactory(this._overrideDefaultParser);
/*  718 */         DocumentBuilder builder = builderF.newDocumentBuilder();
/*  719 */         String systemID = source.getSystemId();
/*  720 */         source = new DOMSource(builder.newDocument());
/*      */ 
/*      */         
/*  723 */         if (systemID != null) {
/*  724 */           source.setSystemId(systemID);
/*      */         }
/*      */       } 
/*  727 */       if (this._isIdentity) {
/*  728 */         transformIdentity(source, handler);
/*      */       } else {
/*  730 */         this._translet.transform(getDOM(source), handler);
/*      */       } 
/*  732 */     } catch (TransletException e) {
/*  733 */       if (this._errorListener != null) postErrorToListener(e.getMessage()); 
/*  734 */       throw new TransformerException(e);
/*  735 */     } catch (RuntimeException e) {
/*  736 */       if (this._errorListener != null) postErrorToListener(e.getMessage()); 
/*  737 */       throw new TransformerException(e);
/*  738 */     } catch (Exception e) {
/*  739 */       if (this._errorListener != null) postErrorToListener(e.getMessage()); 
/*  740 */       throw new TransformerException(e);
/*      */     } finally {
/*  742 */       this._dtmManager = null;
/*      */     } 
/*      */ 
/*      */     
/*  746 */     if (this._ostream != null) {
/*      */       try {
/*  748 */         this._ostream.close();
/*      */       }
/*  750 */       catch (IOException iOException) {}
/*  751 */       this._ostream = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ErrorListener getErrorListener() {
/*  763 */     return this._errorListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setErrorListener(ErrorListener listener) throws IllegalArgumentException {
/*  778 */     if (listener == null) {
/*  779 */       ErrorMsg err = new ErrorMsg("ERROR_LISTENER_NULL_ERR", "Transformer");
/*      */       
/*  781 */       throw new IllegalArgumentException(err.toString());
/*      */     } 
/*  783 */     this._errorListener = listener;
/*      */ 
/*      */     
/*  786 */     if (this._translet != null) {
/*  787 */       this._translet.setMessageHandler(new MessageHandler(this._errorListener));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void postErrorToListener(String message) {
/*      */     try {
/*  795 */       this._errorListener.error(new TransformerException(message));
/*      */     }
/*  797 */     catch (TransformerException transformerException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void postWarningToListener(String message) {
/*      */     try {
/*  807 */       this._errorListener.warning(new TransformerException(message));
/*      */     }
/*  809 */     catch (TransformerException transformerException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Properties getOutputProperties() {
/*  827 */     return (Properties)this._properties.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOutputProperty(String name) throws IllegalArgumentException {
/*  843 */     if (!validOutputProperty(name)) {
/*  844 */       ErrorMsg err = new ErrorMsg("JAXP_UNKNOWN_PROP_ERR", name);
/*  845 */       throw new IllegalArgumentException(err.toString());
/*      */     } 
/*  847 */     return this._properties.getProperty(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputProperties(Properties properties) throws IllegalArgumentException {
/*  863 */     if (properties != null) {
/*  864 */       Enumeration<?> names = properties.propertyNames();
/*      */       
/*  866 */       while (names.hasMoreElements()) {
/*  867 */         String name = (String)names.nextElement();
/*      */ 
/*      */         
/*  870 */         if (isDefaultProperty(name, properties))
/*      */           continue; 
/*  872 */         if (validOutputProperty(name)) {
/*  873 */           this._properties.setProperty(name, properties.getProperty(name));
/*      */           continue;
/*      */         } 
/*  876 */         ErrorMsg err = new ErrorMsg("JAXP_UNKNOWN_PROP_ERR", name);
/*  877 */         throw new IllegalArgumentException(err.toString());
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  882 */       this._properties = this._propertiesClone;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputProperty(String name, String value) throws IllegalArgumentException {
/*  900 */     if (!validOutputProperty(name)) {
/*  901 */       ErrorMsg err = new ErrorMsg("JAXP_UNKNOWN_PROP_ERR", name);
/*  902 */       throw new IllegalArgumentException(err.toString());
/*      */     } 
/*  904 */     this._properties.setProperty(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transferOutputProperties(AbstractTranslet translet) {
/*  914 */     if (this._properties == null) {
/*      */       return;
/*      */     }
/*  917 */     Enumeration<?> names = this._properties.propertyNames();
/*  918 */     while (names.hasMoreElements()) {
/*      */       
/*  920 */       String name = (String)names.nextElement();
/*  921 */       String value = (String)this._properties.get(name);
/*      */ 
/*      */       
/*  924 */       if (value == null) {
/*      */         continue;
/*      */       }
/*  927 */       if (name.equals("encoding")) {
/*  928 */         translet._encoding = value; continue;
/*      */       } 
/*  930 */       if (name.equals("method")) {
/*  931 */         translet._method = value; continue;
/*      */       } 
/*  933 */       if (name.equals("doctype-public")) {
/*  934 */         translet._doctypePublic = value; continue;
/*      */       } 
/*  936 */       if (name.equals("doctype-system")) {
/*  937 */         translet._doctypeSystem = value; continue;
/*      */       } 
/*  939 */       if (name.equals("media-type")) {
/*  940 */         translet._mediaType = value; continue;
/*      */       } 
/*  942 */       if (name.equals("standalone")) {
/*  943 */         translet._standalone = value; continue;
/*      */       } 
/*  945 */       if (name.equals("version")) {
/*  946 */         translet._version = value; continue;
/*      */       } 
/*  948 */       if (name.equals("omit-xml-declaration")) {
/*  949 */         translet
/*  950 */           ._omitHeader = (value != null && value.toLowerCase().equals("yes")); continue;
/*      */       } 
/*  952 */       if (name.equals("indent")) {
/*  953 */         translet
/*  954 */           ._indent = (value != null && value.toLowerCase().equals("yes")); continue;
/*      */       } 
/*  956 */       if (name.equals("{http://xml.apache.org/xslt}indent-amount")) {
/*  957 */         if (value != null)
/*  958 */           translet._indentamount = Integer.parseInt(value); 
/*      */         continue;
/*      */       } 
/*  961 */       if (name.equals("{http://xml.apache.org/xalan}indent-amount")) {
/*  962 */         if (value != null)
/*  963 */           translet._indentamount = Integer.parseInt(value); 
/*      */         continue;
/*      */       } 
/*  966 */       if (name.equals("cdata-section-elements")) {
/*  967 */         if (value != null) {
/*  968 */           translet._cdata = null;
/*  969 */           StringTokenizer e = new StringTokenizer(value);
/*  970 */           while (e.hasMoreTokens())
/*  971 */             translet.addCdataElement(e.nextToken()); 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  975 */       if (name.equals("http://www.oracle.com/xml/is-standalone") && 
/*  976 */         value != null && value.equals("yes")) {
/*  977 */         translet._isStandalone = true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferOutputProperties(SerializationHandler handler) {
/*  990 */     if (this._properties == null)
/*      */       return; 
/*  992 */     String doctypePublic = null;
/*  993 */     String doctypeSystem = null;
/*      */ 
/*      */     
/*  996 */     Enumeration<?> names = this._properties.propertyNames();
/*  997 */     while (names.hasMoreElements()) {
/*      */       
/*  999 */       String name = (String)names.nextElement();
/* 1000 */       String value = (String)this._properties.get(name);
/*      */ 
/*      */       
/* 1003 */       if (value == null) {
/*      */         continue;
/*      */       }
/* 1006 */       if (name.equals("doctype-public")) {
/* 1007 */         doctypePublic = value; continue;
/*      */       } 
/* 1009 */       if (name.equals("doctype-system")) {
/* 1010 */         doctypeSystem = value; continue;
/*      */       } 
/* 1012 */       if (name.equals("media-type")) {
/* 1013 */         handler.setMediaType(value); continue;
/*      */       } 
/* 1015 */       if (name.equals("standalone")) {
/* 1016 */         handler.setStandalone(value); continue;
/*      */       } 
/* 1018 */       if (name.equals("version")) {
/* 1019 */         handler.setVersion(value); continue;
/*      */       } 
/* 1021 */       if (name.equals("omit-xml-declaration")) {
/* 1022 */         handler.setOmitXMLDeclaration((value != null && value
/* 1023 */             .toLowerCase().equals("yes"))); continue;
/*      */       } 
/* 1025 */       if (name.equals("indent")) {
/* 1026 */         handler.setIndent((value != null && value
/* 1027 */             .toLowerCase().equals("yes"))); continue;
/*      */       } 
/* 1029 */       if (name.equals("{http://xml.apache.org/xslt}indent-amount")) {
/* 1030 */         if (value != null)
/* 1031 */           handler.setIndentAmount(Integer.parseInt(value)); 
/*      */         continue;
/*      */       } 
/* 1034 */       if (name.equals("{http://xml.apache.org/xalan}indent-amount")) {
/* 1035 */         if (value != null)
/* 1036 */           handler.setIndentAmount(Integer.parseInt(value)); 
/*      */         continue;
/*      */       } 
/* 1039 */       if (name.equals("http://www.oracle.com/xml/is-standalone")) {
/* 1040 */         if (value != null && value.equals("yes"))
/* 1041 */           handler.setIsStandalone(true); 
/*      */         continue;
/*      */       } 
/* 1044 */       if (name.equals("cdata-section-elements") && 
/* 1045 */         value != null) {
/* 1046 */         StringTokenizer e = new StringTokenizer(value);
/* 1047 */         Vector<String> uriAndLocalNames = null;
/* 1048 */         while (e.hasMoreTokens()) {
/* 1049 */           String uri, localName, token = e.nextToken();
/*      */ 
/*      */ 
/*      */           
/* 1053 */           int lastcolon = token.lastIndexOf(':');
/*      */ 
/*      */           
/* 1056 */           if (lastcolon > 0) {
/* 1057 */             uri = token.substring(0, lastcolon);
/* 1058 */             localName = token.substring(lastcolon + 1);
/*      */           }
/*      */           else {
/*      */             
/* 1062 */             uri = null;
/* 1063 */             localName = token;
/*      */           } 
/*      */           
/* 1066 */           if (uriAndLocalNames == null) {
/* 1067 */             uriAndLocalNames = new Vector();
/*      */           }
/*      */           
/* 1070 */           uriAndLocalNames.addElement(uri);
/* 1071 */           uriAndLocalNames.addElement(localName);
/*      */         } 
/* 1073 */         handler.setCdataSectionElements(uriAndLocalNames);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1079 */     if (doctypePublic != null || doctypeSystem != null) {
/* 1080 */       handler.setDoctype(doctypeSystem, doctypePublic);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Properties createOutputProperties(Properties outputProperties) {
/* 1091 */     Properties defaults = new Properties();
/* 1092 */     setDefaults(defaults, "xml");
/*      */ 
/*      */     
/* 1095 */     Properties base = new Properties(defaults);
/* 1096 */     if (outputProperties != null) {
/* 1097 */       Enumeration<?> names = outputProperties.propertyNames();
/* 1098 */       while (names.hasMoreElements()) {
/* 1099 */         String name = (String)names.nextElement();
/* 1100 */         base.setProperty(name, outputProperties.getProperty(name));
/*      */       } 
/*      */     } else {
/*      */       
/* 1104 */       base.setProperty("encoding", this._translet._encoding);
/* 1105 */       if (this._translet._method != null) {
/* 1106 */         base.setProperty("method", this._translet._method);
/*      */       }
/*      */     } 
/*      */     
/* 1110 */     String method = base.getProperty("method");
/* 1111 */     if (method != null) {
/* 1112 */       if (method.equals("html")) {
/* 1113 */         setDefaults(defaults, "html");
/*      */       }
/* 1115 */       else if (method.equals("text")) {
/* 1116 */         setDefaults(defaults, "text");
/*      */       } 
/*      */     }
/*      */     
/* 1120 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDefaults(Properties props, String method) {
/* 1132 */     Properties method_props = OutputPropertiesFactory.getDefaultMethodProperties(method);
/*      */     
/* 1134 */     Enumeration<?> names = method_props.propertyNames();
/* 1135 */     while (names.hasMoreElements()) {
/*      */       
/* 1137 */       String name = (String)names.nextElement();
/* 1138 */       props.setProperty(name, method_props.getProperty(name));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean validOutputProperty(String name) {
/* 1147 */     return (name.equals("encoding") || name
/* 1148 */       .equals("method") || name
/* 1149 */       .equals("indent") || name
/* 1150 */       .equals("doctype-public") || name
/* 1151 */       .equals("doctype-system") || name
/* 1152 */       .equals("cdata-section-elements") || name
/* 1153 */       .equals("media-type") || name
/* 1154 */       .equals("omit-xml-declaration") || name
/* 1155 */       .equals("standalone") || name
/* 1156 */       .equals("version") || name
/* 1157 */       .equals("http://www.oracle.com/xml/is-standalone") || name
/* 1158 */       .charAt(0) == '{');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isDefaultProperty(String name, Properties properties) {
/* 1165 */     return (properties.get(name) == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParameter(String name, Object value) {
/* 1180 */     if (value == null) {
/* 1181 */       ErrorMsg err = new ErrorMsg("JAXP_INVALID_SET_PARAM_VALUE", name);
/* 1182 */       throw new IllegalArgumentException(err.toString());
/*      */     } 
/*      */     
/* 1185 */     if (this._isIdentity) {
/* 1186 */       if (this._parameters == null) {
/* 1187 */         this._parameters = new HashMap<>();
/*      */       }
/* 1189 */       this._parameters.put(name, value);
/*      */     } else {
/*      */       
/* 1192 */       this._translet.addParameter(name, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearParameters() {
/* 1203 */     if (this._isIdentity && this._parameters != null) {
/* 1204 */       this._parameters.clear();
/*      */     } else {
/*      */       
/* 1207 */       this._translet.clearParameters();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Object getParameter(String name) {
/* 1221 */     if (this._isIdentity) {
/* 1222 */       return (this._parameters != null) ? this._parameters.get(name) : null;
/*      */     }
/*      */     
/* 1225 */     return this._translet.getParameter(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URIResolver getURIResolver() {
/* 1237 */     return this._uriResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURIResolver(URIResolver resolver) {
/* 1248 */     this._uriResolver = resolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DOM retrieveDocument(String baseURI, String href, Translet translet) {
/*      */     try {
/* 1269 */       if (href.length() == 0) {
/* 1270 */         href = baseURI;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1281 */       Source resolvedSource = this._uriResolver.resolve(href, baseURI);
/* 1282 */       if (resolvedSource == null) {
/*      */         
/* 1284 */         StreamSource streamSource = new StreamSource(SystemIDResolver.getAbsoluteURI(href, baseURI));
/* 1285 */         return getDOM(streamSource);
/*      */       } 
/*      */       
/* 1288 */       return getDOM(resolvedSource);
/*      */     }
/* 1290 */     catch (TransformerException e) {
/* 1291 */       if (this._errorListener != null)
/* 1292 */         postErrorToListener("File not found: " + e.getMessage()); 
/* 1293 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void error(TransformerException e) throws TransformerException {
/* 1312 */     Throwable wrapped = e.getException();
/* 1313 */     if (wrapped != null) {
/* 1314 */       System.err.println(new ErrorMsg("ERROR_PLUS_WRAPPED_MSG", e
/* 1315 */             .getMessageAndLocation(), wrapped
/* 1316 */             .getMessage()));
/*      */     } else {
/* 1318 */       System.err.println(new ErrorMsg("ERROR_MSG", e
/* 1319 */             .getMessageAndLocation()));
/*      */     } 
/* 1321 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fatalError(TransformerException e) throws TransformerException {
/* 1341 */     Throwable wrapped = e.getException();
/* 1342 */     if (wrapped != null) {
/* 1343 */       System.err.println(new ErrorMsg("FATAL_ERR_PLUS_WRAPPED_MSG", e
/* 1344 */             .getMessageAndLocation(), wrapped
/* 1345 */             .getMessage()));
/*      */     } else {
/* 1347 */       System.err.println(new ErrorMsg("FATAL_ERR_MSG", e
/* 1348 */             .getMessageAndLocation()));
/*      */     } 
/* 1350 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void warning(TransformerException e) throws TransformerException {
/* 1370 */     Throwable wrapped = e.getException();
/* 1371 */     if (wrapped != null) {
/* 1372 */       System.err.println(new ErrorMsg("WARNING_PLUS_WRAPPED_MSG", e
/* 1373 */             .getMessageAndLocation(), wrapped
/* 1374 */             .getMessage()));
/*      */     } else {
/* 1376 */       System.err.println(new ErrorMsg("WARNING_MSG", e
/* 1377 */             .getMessageAndLocation()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/* 1390 */     this._method = null;
/* 1391 */     this._encoding = null;
/* 1392 */     this._sourceSystemId = null;
/* 1393 */     this._errorListener = this;
/* 1394 */     this._uriResolver = null;
/* 1395 */     this._dom = null;
/* 1396 */     this._parameters = null;
/* 1397 */     this._indentNumber = 0;
/* 1398 */     setOutputProperties(null);
/* 1399 */     this._tohFactory = null;
/* 1400 */     this._ostream = null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/TransformerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */