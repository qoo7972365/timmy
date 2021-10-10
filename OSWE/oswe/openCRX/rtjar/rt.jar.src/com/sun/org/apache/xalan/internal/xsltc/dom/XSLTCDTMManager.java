/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.trax.DOM2SAX;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.trax.StAXEvent2SAX;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.trax.StAXStream2SAX;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMException;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault;
/*     */ import com.sun.org.apache.xml.internal.res.XMLMessages;
/*     */ import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
/*     */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stax.StAXSource;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XSLTCDTMManager
/*     */   extends DTMManagerDefault
/*     */ {
/*     */   private static final boolean DUMPTREE = false;
/*     */   private static final boolean DEBUG = false;
/*     */   
/*     */   public static XSLTCDTMManager newInstance() {
/*  78 */     return new XSLTCDTMManager();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XSLTCDTMManager createNewDTMManagerInstance() {
/*  87 */     return newInstance();
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
/*     */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing) {
/* 116 */     return getDTM(source, unique, whiteSpaceFilter, incremental, doIndexing, false, 0, true, false);
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
/*     */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing, boolean buildIdIndex) {
/* 146 */     return getDTM(source, unique, whiteSpaceFilter, incremental, doIndexing, false, 0, buildIdIndex, false);
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
/*     */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing, boolean buildIdIndex, boolean newNameTable) {
/* 179 */     return getDTM(source, unique, whiteSpaceFilter, incremental, doIndexing, false, 0, buildIdIndex, newNameTable);
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
/*     */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing, boolean hasUserReader, int size, boolean buildIdIndex) {
/* 215 */     return getDTM(source, unique, whiteSpaceFilter, incremental, doIndexing, hasUserReader, size, buildIdIndex, false);
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
/*     */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing, boolean hasUserReader, int size, boolean buildIdIndex, boolean newNameTable) {
/* 260 */     int dtmPos = getFirstFreeDTMID();
/* 261 */     int documentID = dtmPos << 16;
/*     */     
/* 263 */     if (null != source && source instanceof StAXSource) {
/* 264 */       SAXImpl dtm; StAXSource staxSource = (StAXSource)source;
/* 265 */       StAXEvent2SAX staxevent2sax = null;
/* 266 */       StAXStream2SAX staxStream2SAX = null;
/* 267 */       if (staxSource.getXMLEventReader() != null) {
/* 268 */         XMLEventReader xmlEventReader = staxSource.getXMLEventReader();
/* 269 */         staxevent2sax = new StAXEvent2SAX(xmlEventReader);
/* 270 */       } else if (staxSource.getXMLStreamReader() != null) {
/* 271 */         XMLStreamReader xmlStreamReader = staxSource.getXMLStreamReader();
/* 272 */         staxStream2SAX = new StAXStream2SAX(xmlStreamReader);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 277 */       if (size <= 0) {
/* 278 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, 512, buildIdIndex, newNameTable);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 283 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, size, buildIdIndex, newNameTable);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 288 */       dtm.setDocumentURI(source.getSystemId());
/*     */       
/* 290 */       addDTM(dtm, dtmPos, 0);
/*     */       
/*     */       try {
/* 293 */         if (staxevent2sax != null) {
/* 294 */           staxevent2sax.setContentHandler(dtm);
/* 295 */           staxevent2sax.parse();
/*     */         }
/* 297 */         else if (staxStream2SAX != null) {
/* 298 */           staxStream2SAX.setContentHandler(dtm);
/* 299 */           staxStream2SAX.parse();
/*     */         }
/*     */       
/*     */       }
/* 303 */       catch (RuntimeException re) {
/* 304 */         throw re;
/*     */       }
/* 306 */       catch (Exception e) {
/* 307 */         throw new WrappedRuntimeException(e);
/*     */       } 
/*     */       
/* 310 */       return dtm;
/* 311 */     }  if (null != source && source instanceof DOMSource) {
/* 312 */       SAXImpl dtm; DOMSource domsrc = (DOMSource)source;
/* 313 */       Node node = domsrc.getNode();
/* 314 */       DOM2SAX dom2sax = new DOM2SAX(node);
/*     */ 
/*     */ 
/*     */       
/* 318 */       if (size <= 0) {
/* 319 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, 512, buildIdIndex, newNameTable);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 324 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, size, buildIdIndex, newNameTable);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 329 */       dtm.setDocumentURI(source.getSystemId());
/*     */       
/* 331 */       addDTM(dtm, dtmPos, 0);
/*     */       
/* 333 */       dom2sax.setContentHandler(dtm);
/*     */       
/*     */       try {
/* 336 */         dom2sax.parse();
/*     */       }
/* 338 */       catch (RuntimeException re) {
/* 339 */         throw re;
/*     */       }
/* 341 */       catch (Exception e) {
/* 342 */         throw new WrappedRuntimeException(e);
/*     */       } 
/*     */       
/* 345 */       return dtm;
/*     */     } 
/*     */ 
/*     */     
/* 349 */     boolean isSAXSource = (null != source) ? (source instanceof SAXSource) : true;
/*     */     
/* 351 */     boolean isStreamSource = (null != source) ? (source instanceof javax.xml.transform.stream.StreamSource) : false;
/*     */ 
/*     */     
/* 354 */     if (isSAXSource || isStreamSource) {
/*     */       XMLReader reader;
/*     */       InputSource xmlSource;
/*     */       SAXImpl dtm;
/* 358 */       if (null == source) {
/* 359 */         xmlSource = null;
/* 360 */         reader = null;
/* 361 */         hasUserReader = false;
/*     */       } else {
/*     */         
/* 364 */         reader = getXMLReader(source);
/* 365 */         xmlSource = SAXSource.sourceToInputSource(source);
/*     */         
/* 367 */         String urlOfSource = xmlSource.getSystemId();
/*     */         
/* 369 */         if (null != urlOfSource) {
/*     */           try {
/* 371 */             urlOfSource = SystemIDResolver.getAbsoluteURI(urlOfSource);
/*     */           }
/* 373 */           catch (Exception e) {
/*     */             
/* 375 */             System.err.println("Can not absolutize URL: " + urlOfSource);
/*     */           } 
/*     */           
/* 378 */           xmlSource.setSystemId(urlOfSource);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 384 */       if (size <= 0) {
/* 385 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, 512, buildIdIndex, newNameTable);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 390 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, size, buildIdIndex, newNameTable);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 397 */       addDTM(dtm, dtmPos, 0);
/*     */       
/* 399 */       if (null == reader)
/*     */       {
/* 401 */         return dtm;
/*     */       }
/*     */       
/* 404 */       reader.setContentHandler(dtm.getBuilder());
/*     */       
/* 406 */       if (!hasUserReader || null == reader.getDTDHandler()) {
/* 407 */         reader.setDTDHandler(dtm);
/*     */       }
/*     */       
/* 410 */       if (!hasUserReader || null == reader.getErrorHandler()) {
/* 411 */         reader.setErrorHandler(dtm);
/*     */       }
/*     */ 
/*     */       
/* 415 */       try { reader.setProperty("http://xml.org/sax/properties/lexical-handler", dtm); }
/*     */       
/* 417 */       catch (SAXNotRecognizedException sAXNotRecognizedException) {  }
/* 418 */       catch (SAXNotSupportedException sAXNotSupportedException) {}
/*     */       
/*     */       try {
/* 421 */         reader.parse(xmlSource);
/*     */       }
/* 423 */       catch (RuntimeException re) {
/* 424 */         throw re;
/*     */       }
/* 426 */       catch (Exception e) {
/* 427 */         throw new WrappedRuntimeException(e);
/*     */       } finally {
/* 429 */         if (!hasUserReader) {
/* 430 */           releaseXMLReader(reader);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 439 */       return dtm;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 444 */     throw new DTMException(XMLMessages.createXMLMessage("ER_NOT_SUPPORTED", new Object[] { source }));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/XSLTCDTMManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */