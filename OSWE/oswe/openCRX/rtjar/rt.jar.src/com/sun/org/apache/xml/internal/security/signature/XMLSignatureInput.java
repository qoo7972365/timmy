/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_OmitComments;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315OmitComments;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityRuntimeException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler;
/*     */ import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLSignatureInput
/*     */ {
/*  71 */   private InputStream inputOctetStreamProxy = null;
/*     */ 
/*     */ 
/*     */   
/*  75 */   private Set<Node> inputNodeSet = null;
/*     */ 
/*     */ 
/*     */   
/*  79 */   private Node subNode = null;
/*     */ 
/*     */ 
/*     */   
/*  83 */   private Node excludeNode = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean excludeComments = false;
/*     */ 
/*     */   
/*     */   private boolean isNodeSet = false;
/*     */ 
/*     */   
/*  93 */   private byte[] bytes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private String mimeType = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private String sourceURI = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private List<NodeFilter> nodeFilters = new ArrayList<>();
/*     */   
/*     */   private boolean needsToBeExpanded = false;
/* 117 */   private OutputStream outputStream = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DocumentBuilderFactory dfactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput(byte[] paramArrayOfbyte) {
/* 131 */     this.bytes = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput(InputStream paramInputStream) {
/* 141 */     this.inputOctetStreamProxy = paramInputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput(Node paramNode) {
/* 151 */     this.subNode = paramNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput(Set<Node> paramSet) {
/* 160 */     this.inputNodeSet = paramSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNeedsToBeExpanded() {
/* 168 */     return this.needsToBeExpanded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNeedsToBeExpanded(boolean paramBoolean) {
/* 176 */     this.needsToBeExpanded = paramBoolean;
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
/*     */   public Set<Node> getNodeSet() throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
/* 191 */     return getNodeSet(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Node> getInputNodeSet() {
/* 199 */     return this.inputNodeSet;
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
/*     */   public Set<Node> getNodeSet(boolean paramBoolean) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
/* 215 */     if (this.inputNodeSet != null) {
/* 216 */       return this.inputNodeSet;
/*     */     }
/* 218 */     if (this.inputOctetStreamProxy == null && this.subNode != null) {
/* 219 */       if (paramBoolean) {
/* 220 */         XMLUtils.circumventBug2650(XMLUtils.getOwnerDocument(this.subNode));
/*     */       }
/* 222 */       this.inputNodeSet = new LinkedHashSet<>();
/* 223 */       XMLUtils.getSet(this.subNode, this.inputNodeSet, this.excludeNode, this.excludeComments);
/* 224 */       return this.inputNodeSet;
/* 225 */     }  if (isOctetStream()) {
/* 226 */       convertToNodes();
/* 227 */       LinkedHashSet<Node> linkedHashSet = new LinkedHashSet();
/* 228 */       XMLUtils.getSet(this.subNode, linkedHashSet, null, false);
/* 229 */       return linkedHashSet;
/*     */     } 
/*     */     
/* 232 */     throw new RuntimeException("getNodeSet() called but no input data present");
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
/*     */   public InputStream getOctetStream() throws IOException {
/* 244 */     if (this.inputOctetStreamProxy != null) {
/* 245 */       return this.inputOctetStreamProxy;
/*     */     }
/*     */     
/* 248 */     if (this.bytes != null) {
/* 249 */       this.inputOctetStreamProxy = new ByteArrayInputStream(this.bytes);
/* 250 */       return this.inputOctetStreamProxy;
/*     */     } 
/*     */     
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getOctetStreamReal() {
/* 260 */     return this.inputOctetStreamProxy;
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
/*     */   public byte[] getBytes() throws IOException, CanonicalizationException {
/* 274 */     byte[] arrayOfByte = getBytesFromInputStream();
/* 275 */     if (arrayOfByte != null) {
/* 276 */       return arrayOfByte;
/*     */     }
/* 278 */     Canonicalizer20010315OmitComments canonicalizer20010315OmitComments = new Canonicalizer20010315OmitComments();
/* 279 */     this.bytes = canonicalizer20010315OmitComments.engineCanonicalize(this);
/* 280 */     return this.bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNodeSet() {
/* 289 */     return ((this.inputOctetStreamProxy == null && this.inputNodeSet != null) || this.isNodeSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isElement() {
/* 299 */     return (this.inputOctetStreamProxy == null && this.subNode != null && this.inputNodeSet == null && !this.isNodeSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOctetStream() {
/* 309 */     return ((this.inputOctetStreamProxy != null || this.bytes != null) && this.inputNodeSet == null && this.subNode == null);
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
/*     */   public boolean isOutputStreamSet() {
/* 321 */     return (this.outputStream != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isByteArray() {
/* 330 */     return (this.bytes != null && this.inputNodeSet == null && this.subNode == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 339 */     return (isOctetStream() || isNodeSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMIMEType() {
/* 348 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMIMEType(String paramString) {
/* 357 */     this.mimeType = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSourceURI() {
/* 366 */     return this.sourceURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceURI(String paramString) {
/* 375 */     this.sourceURI = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 383 */     if (isNodeSet()) {
/* 384 */       return "XMLSignatureInput/NodeSet/" + this.inputNodeSet.size() + " nodes/" + 
/* 385 */         getSourceURI();
/*     */     }
/* 387 */     if (isElement()) {
/* 388 */       return "XMLSignatureInput/Element/" + this.subNode + " exclude " + this.excludeNode + " comments:" + this.excludeComments + "/" + 
/*     */         
/* 390 */         getSourceURI();
/*     */     }
/*     */     try {
/* 393 */       return "XMLSignatureInput/OctetStream/" + (getBytes()).length + " octets/" + 
/* 394 */         getSourceURI();
/* 395 */     } catch (IOException iOException) {
/* 396 */       return "XMLSignatureInput/OctetStream//" + getSourceURI();
/* 397 */     } catch (CanonicalizationException canonicalizationException) {
/* 398 */       return "XMLSignatureInput/OctetStream//" + getSourceURI();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHTMLRepresentation() throws XMLSignatureException {
/* 409 */     XMLSignatureInputDebugger xMLSignatureInputDebugger = new XMLSignatureInputDebugger(this);
/* 410 */     return xMLSignatureInputDebugger.getHTMLRepresentation();
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
/*     */   public String getHTMLRepresentation(Set<String> paramSet) throws XMLSignatureException {
/* 422 */     XMLSignatureInputDebugger xMLSignatureInputDebugger = new XMLSignatureInputDebugger(this, paramSet);
/*     */     
/* 424 */     return xMLSignatureInputDebugger.getHTMLRepresentation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getExcludeNode() {
/* 432 */     return this.excludeNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExcludeNode(Node paramNode) {
/* 440 */     this.excludeNode = paramNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getSubNode() {
/* 448 */     return this.subNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExcludeComments() {
/* 455 */     return this.excludeComments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExcludeComments(boolean paramBoolean) {
/* 462 */     this.excludeComments = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateOutputStream(OutputStream paramOutputStream) throws CanonicalizationException, IOException {
/* 472 */     updateOutputStream(paramOutputStream, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateOutputStream(OutputStream paramOutputStream, boolean paramBoolean) throws CanonicalizationException, IOException {
/* 477 */     if (paramOutputStream == this.outputStream) {
/*     */       return;
/*     */     }
/* 480 */     if (this.bytes != null) {
/* 481 */       paramOutputStream.write(this.bytes);
/* 482 */     } else if (this.inputOctetStreamProxy == null) {
/* 483 */       Canonicalizer20010315OmitComments canonicalizer20010315OmitComments; Canonicalizer11_OmitComments canonicalizer11_OmitComments = null;
/* 484 */       if (paramBoolean) {
/* 485 */         canonicalizer11_OmitComments = new Canonicalizer11_OmitComments();
/*     */       } else {
/* 487 */         canonicalizer20010315OmitComments = new Canonicalizer20010315OmitComments();
/*     */       } 
/* 489 */       canonicalizer20010315OmitComments.setWriter(paramOutputStream);
/* 490 */       canonicalizer20010315OmitComments.engineCanonicalize(this);
/*     */     } else {
/* 492 */       byte[] arrayOfByte = new byte[4096];
/* 493 */       int i = 0;
/*     */       try {
/* 495 */         while ((i = this.inputOctetStreamProxy.read(arrayOfByte)) != -1) {
/* 496 */           paramOutputStream.write(arrayOfByte, 0, i);
/*     */         }
/* 498 */       } catch (IOException iOException) {
/* 499 */         this.inputOctetStreamProxy.close();
/* 500 */         throw iOException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputStream(OutputStream paramOutputStream) {
/* 509 */     this.outputStream = paramOutputStream;
/*     */   }
/*     */   
/*     */   private byte[] getBytesFromInputStream() throws IOException {
/* 513 */     if (this.bytes != null) {
/* 514 */       return this.bytes;
/*     */     }
/* 516 */     if (this.inputOctetStreamProxy == null) {
/* 517 */       return null;
/*     */     }
/*     */     try {
/* 520 */       this.bytes = JavaUtils.getBytesFromStream(this.inputOctetStreamProxy);
/*     */     } finally {
/* 522 */       this.inputOctetStreamProxy.close();
/*     */     } 
/* 524 */     return this.bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNodeFilter(NodeFilter paramNodeFilter) {
/* 531 */     if (isOctetStream()) {
/*     */       try {
/* 533 */         convertToNodes();
/* 534 */       } catch (Exception exception) {
/* 535 */         throw new XMLSecurityRuntimeException("signature.XMLSignatureInput.nodesetReference", exception);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 540 */     this.nodeFilters.add(paramNodeFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<NodeFilter> getNodeFilters() {
/* 547 */     return this.nodeFilters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNodeSet(boolean paramBoolean) {
/* 554 */     this.isNodeSet = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   void convertToNodes() throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
/* 559 */     if (this.dfactory == null) {
/* 560 */       this.dfactory = DocumentBuilderFactory.newInstance();
/* 561 */       this.dfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/* 562 */       this.dfactory.setValidating(false);
/* 563 */       this.dfactory.setNamespaceAware(true);
/*     */     } 
/* 565 */     DocumentBuilder documentBuilder = this.dfactory.newDocumentBuilder();
/*     */     
/*     */     try {
/* 568 */       documentBuilder.setErrorHandler(new IgnoreAllErrorHandler());
/*     */       
/* 570 */       Document document = documentBuilder.parse(getOctetStream());
/* 571 */       this.subNode = document;
/* 572 */     } catch (SAXException sAXException) {
/*     */       
/* 574 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*     */       
/* 576 */       byteArrayOutputStream.write("<container>".getBytes("UTF-8"));
/* 577 */       byteArrayOutputStream.write(getBytes());
/* 578 */       byteArrayOutputStream.write("</container>".getBytes("UTF-8"));
/*     */       
/* 580 */       byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
/* 581 */       Document document = documentBuilder.parse(new ByteArrayInputStream(arrayOfByte));
/* 582 */       this.subNode = document.getDocumentElement().getFirstChild().getFirstChild();
/*     */     } finally {
/* 584 */       if (this.inputOctetStreamProxy != null) {
/* 585 */         this.inputOctetStreamProxy.close();
/*     */       }
/* 587 */       this.inputOctetStreamProxy = null;
/* 588 */       this.bytes = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/XMLSignatureInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */