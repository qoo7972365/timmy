/*     */ package com.sun.org.apache.xml.internal.dtm.ref;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMException;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMFilter;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMIterator;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.dom2dtm.DOM2DTM;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2RTFDTM;
/*     */ import com.sun.org.apache.xml.internal.res.XMLMessages;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xml.internal.utils.SuballocatedIntVector;
/*     */ import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
/*     */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*     */ import com.sun.org.apache.xml.internal.utils.XMLReaderManager;
/*     */ import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTMManagerDefault
/*     */   extends DTMManager
/*     */ {
/*     */   private static final boolean DUMPTREE = false;
/*     */   private static final boolean DEBUG = false;
/*  96 */   protected DTM[] m_dtms = new DTM[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   int[] m_dtm_offsets = new int[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   protected XMLReaderManager m_readerManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   protected DefaultHandler m_defaultHandler = new DefaultHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addDTM(DTM dtm, int id) {
/* 132 */     addDTM(dtm, id, 0);
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
/*     */   public synchronized void addDTM(DTM dtm, int id, int offset) {
/* 147 */     if (id >= 65536)
/*     */     {
/*     */       
/* 150 */       throw new DTMException(XMLMessages.createXMLMessage("ER_NO_DTMIDS_AVAIL", null));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     int oldlen = this.m_dtms.length;
/* 159 */     if (oldlen <= id) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 166 */       int newlen = Math.min(id + 256, 65536);
/*     */       
/* 168 */       DTM[] new_m_dtms = new DTM[newlen];
/* 169 */       System.arraycopy(this.m_dtms, 0, new_m_dtms, 0, oldlen);
/* 170 */       this.m_dtms = new_m_dtms;
/* 171 */       int[] new_m_dtm_offsets = new int[newlen];
/* 172 */       System.arraycopy(this.m_dtm_offsets, 0, new_m_dtm_offsets, 0, oldlen);
/* 173 */       this.m_dtm_offsets = new_m_dtm_offsets;
/*     */     } 
/*     */     
/* 176 */     this.m_dtms[id] = dtm;
/* 177 */     this.m_dtm_offsets[id] = offset;
/* 178 */     dtm.documentRegistration();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getFirstFreeDTMID() {
/* 188 */     int n = this.m_dtms.length;
/* 189 */     for (int i = 1; i < n; i++) {
/*     */       
/* 191 */       if (null == this.m_dtms[i])
/*     */       {
/* 193 */         return i;
/*     */       }
/*     */     } 
/* 196 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   private ExpandedNameTable m_expandedNameTable = new ExpandedNameTable();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing) {
/* 249 */     XMLStringFactory xstringFactory = this.m_xsf;
/* 250 */     int dtmPos = getFirstFreeDTMID();
/* 251 */     int documentID = dtmPos << 16;
/*     */     
/* 253 */     if (null != source && source instanceof DOMSource) {
/*     */       
/* 255 */       DOM2DTM dtm = new DOM2DTM(this, (DOMSource)source, documentID, whiteSpaceFilter, xstringFactory, doIndexing);
/*     */ 
/*     */       
/* 258 */       addDTM(dtm, dtmPos, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 265 */       return dtm;
/*     */     } 
/*     */ 
/*     */     
/* 269 */     boolean isSAXSource = (null != source) ? (source instanceof SAXSource) : true;
/*     */     
/* 271 */     boolean isStreamSource = (null != source) ? (source instanceof javax.xml.transform.stream.StreamSource) : false;
/*     */ 
/*     */     
/* 274 */     if (isSAXSource || isStreamSource) {
/* 275 */       XMLReader reader = null;
/*     */       
/*     */       try {
/*     */         SAX2DTM dtm;
/*     */         
/*     */         InputSource xmlSource;
/* 281 */         if (null == source) {
/* 282 */           xmlSource = null;
/*     */         } else {
/* 284 */           reader = getXMLReader(source);
/* 285 */           xmlSource = SAXSource.sourceToInputSource(source);
/*     */           
/* 287 */           String urlOfSource = xmlSource.getSystemId();
/*     */           
/* 289 */           if (null != urlOfSource) {
/*     */             try {
/* 291 */               urlOfSource = SystemIDResolver.getAbsoluteURI(urlOfSource);
/* 292 */             } catch (Exception e) {
/*     */               
/* 294 */               System.err.println("Can not absolutize URL: " + urlOfSource);
/*     */             } 
/*     */             
/* 297 */             xmlSource.setSystemId(urlOfSource);
/*     */           } 
/*     */         } 
/*     */         
/* 301 */         if (source == null && unique && !incremental && !doIndexing) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 309 */           dtm = new SAX2RTFDTM(this, source, documentID, whiteSpaceFilter, xstringFactory, doIndexing);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 321 */           dtm = new SAX2DTM(this, source, documentID, whiteSpaceFilter, xstringFactory, doIndexing);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 328 */         addDTM(dtm, dtmPos, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 335 */         boolean haveXercesParser = (null != reader && reader.getClass().getName().equals("com.sun.org.apache.xerces.internal.parsers.SAXParser"));
/*     */         
/* 337 */         if (haveXercesParser) {
/* 338 */           incremental = true;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 343 */         if (this.m_incremental && incremental) {
/*     */           
/* 345 */           IncrementalSAXSource coParser = null;
/*     */           
/* 347 */           if (haveXercesParser) {
/*     */             
/*     */             try {
/*     */               
/* 351 */               coParser = (IncrementalSAXSource)Class.forName("com.sun.org.apache.xml.internal.dtm.ref.IncrementalSAXSource_Xerces").newInstance();
/* 352 */             } catch (Exception ex) {
/* 353 */               ex.printStackTrace();
/* 354 */               coParser = null;
/*     */             } 
/*     */           }
/*     */           
/* 358 */           if (coParser == null)
/*     */           {
/* 360 */             if (null == reader) {
/* 361 */               coParser = new IncrementalSAXSource_Filter();
/*     */             } else {
/* 363 */               IncrementalSAXSource_Filter filter = new IncrementalSAXSource_Filter();
/*     */               
/* 365 */               filter.setXMLReader(reader);
/* 366 */               coParser = filter;
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 390 */           dtm.setIncrementalSAXSource(coParser);
/*     */           
/* 392 */           if (null == xmlSource)
/*     */           {
/*     */             
/* 395 */             return dtm;
/*     */           }
/*     */           
/* 398 */           if (null == reader.getErrorHandler()) {
/* 399 */             reader.setErrorHandler(dtm);
/*     */           }
/* 401 */           reader.setDTDHandler(dtm);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 407 */             coParser.startParse(xmlSource);
/* 408 */           } catch (RuntimeException re) {
/*     */             
/* 410 */             dtm.clearCoRoutine();
/*     */             
/* 412 */             throw re;
/* 413 */           } catch (Exception e) {
/*     */             
/* 415 */             dtm.clearCoRoutine();
/*     */             
/* 417 */             throw new WrappedRuntimeException(e);
/*     */           } 
/*     */         } else {
/* 420 */           if (null == reader)
/*     */           {
/*     */             
/* 423 */             return dtm;
/*     */           }
/*     */ 
/*     */           
/* 427 */           reader.setContentHandler(dtm);
/* 428 */           reader.setDTDHandler(dtm);
/* 429 */           if (null == reader.getErrorHandler()) {
/* 430 */             reader.setErrorHandler(dtm);
/*     */           }
/*     */ 
/*     */           
/* 434 */           try { reader.setProperty("http://xml.org/sax/properties/lexical-handler", dtm);
/*     */              }
/*     */           
/* 437 */           catch (SAXNotRecognizedException sAXNotRecognizedException) {  }
/* 438 */           catch (SAXNotSupportedException sAXNotSupportedException) {}
/*     */           
/*     */           try {
/* 441 */             reader.parse(xmlSource);
/* 442 */           } catch (RuntimeException re) {
/* 443 */             dtm.clearCoRoutine();
/*     */             
/* 445 */             throw re;
/* 446 */           } catch (Exception e) {
/* 447 */             dtm.clearCoRoutine();
/*     */             
/* 449 */             throw new WrappedRuntimeException(e);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 458 */         return dtm;
/*     */       }
/*     */       finally {
/*     */         
/* 462 */         if (reader != null && (!this.m_incremental || !incremental)) {
/* 463 */           reader.setContentHandler(this.m_defaultHandler);
/* 464 */           reader.setDTDHandler(this.m_defaultHandler);
/* 465 */           reader.setErrorHandler(this.m_defaultHandler);
/*     */ 
/*     */           
/*     */           try {
/* 469 */             reader.setProperty("http://xml.org/sax/properties/lexical-handler", null);
/*     */           }
/* 471 */           catch (Exception exception) {}
/*     */         } 
/* 473 */         releaseXMLReader(reader);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 479 */     throw new DTMException(XMLMessages.createXMLMessage("ER_NOT_SUPPORTED", new Object[] { source }));
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
/*     */   public synchronized int getDTMHandleFromNode(Node node) {
/*     */     int handle;
/* 495 */     if (null == node) {
/* 496 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_NODE_NON_NULL", null));
/*     */     }
/* 498 */     if (node instanceof DTMNodeProxy) {
/* 499 */       return ((DTMNodeProxy)node).getDTMNodeNumber();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 524 */     int max = this.m_dtms.length;
/* 525 */     for (int i = 0; i < max; i++) {
/*     */       
/* 527 */       DTM thisDTM = this.m_dtms[i];
/* 528 */       if (null != thisDTM && thisDTM instanceof DOM2DTM) {
/*     */         
/* 530 */         int j = ((DOM2DTM)thisDTM).getHandleOfNode(node);
/* 531 */         if (j != -1) return j;
/*     */       
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 554 */     Node root = node;
/* 555 */     Node p = (root.getNodeType() == 2) ? ((Attr)root).getOwnerElement() : root.getParentNode();
/* 556 */     for (; p != null; p = p.getParentNode())
/*     */     {
/* 558 */       root = p;
/*     */     }
/*     */     
/* 561 */     DOM2DTM dtm = (DOM2DTM)getDTM(new DOMSource(root), false, (DTMWSFilter)null, true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 566 */     if (node instanceof com.sun.org.apache.xml.internal.dtm.ref.dom2dtm.DOM2DTMdefaultNamespaceDeclarationNode) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 571 */       handle = dtm.getHandleOfNode(((Attr)node).getOwnerElement());
/* 572 */       handle = dtm.getAttributeNode(handle, node.getNamespaceURI(), node.getLocalName());
/*     */     } else {
/*     */       
/* 575 */       handle = dtm.getHandleOfNode(node);
/*     */     } 
/* 577 */     if (-1 == handle) {
/* 578 */       throw new RuntimeException(XMLMessages.createXMLMessage("ER_COULD_NOT_RESOLVE_NODE", null));
/*     */     }
/* 580 */     return handle;
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
/*     */   public synchronized XMLReader getXMLReader(Source inputSource) {
/*     */     try {
/* 604 */       XMLReader reader = (inputSource instanceof SAXSource) ? ((SAXSource)inputSource).getXMLReader() : null;
/*     */ 
/*     */       
/* 607 */       if (null == reader) {
/* 608 */         if (this.m_readerManager == null) {
/* 609 */           this.m_readerManager = XMLReaderManager.getInstance(overrideDefaultParser());
/*     */         }
/*     */         
/* 612 */         reader = this.m_readerManager.getXMLReader();
/*     */       } 
/*     */       
/* 615 */       return reader;
/*     */     }
/* 617 */     catch (SAXException se) {
/* 618 */       throw new DTMException(se.getMessage(), se);
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
/*     */   public synchronized void releaseXMLReader(XMLReader reader) {
/* 633 */     if (this.m_readerManager != null) {
/* 634 */       this.m_readerManager.releaseXMLReader(reader);
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
/*     */   public synchronized DTM getDTM(int nodeHandle) {
/*     */     try {
/* 650 */       return this.m_dtms[nodeHandle >>> 16];
/*     */     }
/* 652 */     catch (ArrayIndexOutOfBoundsException e) {
/*     */       
/* 654 */       if (nodeHandle == -1) {
/* 655 */         return null;
/*     */       }
/* 657 */       throw e;
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
/*     */   public synchronized int getDTMIdentity(DTM dtm) {
/* 675 */     if (dtm instanceof DTMDefaultBase) {
/*     */       
/* 677 */       DTMDefaultBase dtmdb = (DTMDefaultBase)dtm;
/* 678 */       if (dtmdb.getManager() == this) {
/* 679 */         return dtmdb.getDTMIDs().elementAt(0);
/*     */       }
/* 681 */       return -1;
/*     */     } 
/*     */     
/* 684 */     int n = this.m_dtms.length;
/*     */     
/* 686 */     for (int i = 0; i < n; i++) {
/*     */       
/* 688 */       DTM tdtm = this.m_dtms[i];
/*     */       
/* 690 */       if (tdtm == dtm && this.m_dtm_offsets[i] == 0) {
/* 691 */         return i << 16;
/*     */       }
/*     */     } 
/* 694 */     return -1;
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
/*     */   public synchronized boolean release(DTM dtm, boolean shouldHardDelete) {
/* 725 */     if (dtm instanceof SAX2DTM)
/*     */     {
/* 727 */       ((SAX2DTM)dtm).clearCoRoutine();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 738 */     if (dtm instanceof DTMDefaultBase) {
/*     */       
/* 740 */       SuballocatedIntVector ids = ((DTMDefaultBase)dtm).getDTMIDs();
/* 741 */       for (int i = ids.size() - 1; i >= 0; i--) {
/* 742 */         this.m_dtms[ids.elementAt(i) >>> 16] = null;
/*     */       }
/*     */     } else {
/*     */       
/* 746 */       int i = getDTMIdentity(dtm);
/* 747 */       if (i >= 0)
/*     */       {
/* 749 */         this.m_dtms[i >>> 16] = null;
/*     */       }
/*     */     } 
/*     */     
/* 753 */     dtm.documentRelease();
/* 754 */     return true;
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
/*     */   public synchronized DTM createDocumentFragment() {
/*     */     try {
/* 768 */       DocumentBuilderFactory dbf = JdkXmlUtils.getDOMFactory(overrideDefaultParser());
/*     */       
/* 770 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 771 */       Document doc = db.newDocument();
/* 772 */       Node df = doc.createDocumentFragment();
/*     */       
/* 774 */       return getDTM(new DOMSource(df), true, (DTMWSFilter)null, false, false);
/*     */     }
/* 776 */     catch (Exception e) {
/*     */       
/* 778 */       throw new DTMException(e);
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
/*     */   public synchronized DTMIterator createDTMIterator(int whatToShow, DTMFilter filter, boolean entityReferenceExpansion) {
/* 797 */     return null;
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
/*     */   public synchronized DTMIterator createDTMIterator(String xpathString, PrefixResolver presolver) {
/* 814 */     return null;
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
/*     */   public synchronized DTMIterator createDTMIterator(int node) {
/* 829 */     return null;
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
/*     */   public synchronized DTMIterator createDTMIterator(Object xpathCompiler, int pos) {
/* 845 */     return null;
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
/*     */   public ExpandedNameTable getExpandedNameTable(DTM dtm) {
/* 857 */     return this.m_expandedNameTable;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/ref/DTMManagerDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */