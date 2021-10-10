/*     */ package com.sun.xml.internal.ws.api.message.saaj;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.binding.SOAPBindingImpl;
/*     */ import com.sun.xml.internal.ws.message.saaj.SAAJHeader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAAJMessageHeaders
/*     */   implements MessageHeaders
/*     */ {
/*     */   SOAPMessage sm;
/*     */   Map<SOAPHeaderElement, Header> nonSAAJHeaders;
/*     */   Map<QName, Integer> notUnderstoodCount;
/*     */   SOAPVersion soapVersion;
/*     */   private Set<QName> understoodHeaders;
/*     */   
/*     */   public SAAJMessageHeaders(SOAPMessage sm, SOAPVersion version) {
/*  58 */     this.sm = sm;
/*  59 */     this.soapVersion = version;
/*  60 */     initHeaderUnderstanding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initHeaderUnderstanding() {
/*  67 */     SOAPHeader soapHeader = ensureSOAPHeader();
/*  68 */     if (soapHeader == null) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     Iterator<SOAPHeaderElement> allHeaders = soapHeader.examineAllHeaderElements();
/*  73 */     while (allHeaders.hasNext()) {
/*  74 */       SOAPHeaderElement nextHdrElem = allHeaders.next();
/*  75 */       if (nextHdrElem == null) {
/*     */         continue;
/*     */       }
/*  78 */       if (nextHdrElem.getMustUnderstand()) {
/*  79 */         notUnderstood(nextHdrElem.getElementQName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void understood(Header header) {
/*  90 */     understood(header.getNamespaceURI(), header.getLocalPart());
/*     */   }
/*     */ 
/*     */   
/*     */   public void understood(String nsUri, String localName) {
/*  95 */     understood(new QName(nsUri, localName));
/*     */   }
/*     */ 
/*     */   
/*     */   public void understood(QName qName) {
/* 100 */     if (this.notUnderstoodCount == null) {
/* 101 */       this.notUnderstoodCount = new HashMap<>();
/*     */     }
/*     */     
/* 104 */     Integer count = this.notUnderstoodCount.get(qName);
/* 105 */     if (count != null && count.intValue() > 0) {
/*     */       
/* 107 */       count = Integer.valueOf(count.intValue() - 1);
/* 108 */       if (count.intValue() <= 0) {
/*     */ 
/*     */         
/* 111 */         this.notUnderstoodCount.remove(qName);
/*     */       } else {
/* 113 */         this.notUnderstoodCount.put(qName, count);
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     if (this.understoodHeaders == null) {
/* 118 */       this.understoodHeaders = new HashSet<>();
/*     */     }
/*     */     
/* 121 */     this.understoodHeaders.add(qName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(Header header) {
/* 127 */     return isUnderstood(header.getNamespaceURI(), header.getLocalPart());
/*     */   }
/*     */   
/*     */   public boolean isUnderstood(String nsUri, String localName) {
/* 131 */     return isUnderstood(new QName(nsUri, localName));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(QName name) {
/* 136 */     if (this.understoodHeaders == null) {
/* 137 */       return false;
/*     */     }
/* 139 */     return this.understoodHeaders.contains(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(int index) {
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Header get(String nsUri, String localName, boolean markAsUnderstood) {
/* 149 */     SOAPHeaderElement h = find(nsUri, localName);
/* 150 */     if (h != null) {
/* 151 */       if (markAsUnderstood) {
/* 152 */         understood(nsUri, localName);
/*     */       }
/* 154 */       return (Header)new SAAJHeader(h);
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Header get(QName name, boolean markAsUnderstood) {
/* 161 */     return get(name.getNamespaceURI(), name.getLocalPart(), markAsUnderstood);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders(QName headerName, boolean markAsUnderstood) {
/* 167 */     return getHeaders(headerName.getNamespaceURI(), headerName.getLocalPart(), markAsUnderstood);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders(String nsUri, String localName, boolean markAsUnderstood) {
/* 173 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 174 */     if (soapHeader == null) {
/* 175 */       return null;
/*     */     }
/* 177 */     Iterator<SOAPHeaderElement> allHeaders = soapHeader.examineAllHeaderElements();
/* 178 */     if (markAsUnderstood) {
/*     */ 
/*     */       
/* 181 */       List<Header> headers = new ArrayList<>();
/* 182 */       while (allHeaders.hasNext()) {
/* 183 */         SOAPHeaderElement nextHdr = allHeaders.next();
/* 184 */         if (nextHdr != null && nextHdr
/* 185 */           .getNamespaceURI().equals(nsUri) && (
/* 186 */           localName == null || nextHdr
/* 187 */           .getLocalName().equals(localName))) {
/* 188 */           understood(nextHdr.getNamespaceURI(), nextHdr.getLocalName());
/* 189 */           headers.add(new SAAJHeader(nextHdr));
/*     */         } 
/*     */       } 
/*     */       
/* 193 */       return headers.iterator();
/*     */     } 
/*     */ 
/*     */     
/* 197 */     return new HeaderReadIterator(allHeaders, nsUri, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders(String nsUri, boolean markAsUnderstood) {
/* 202 */     return getHeaders(nsUri, null, markAsUnderstood);
/*     */   }
/*     */   
/*     */   public boolean add(Header header) {
/*     */     try {
/* 207 */       header.writeTo(this.sm);
/* 208 */     } catch (SOAPException e) {
/*     */       
/* 210 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 214 */     notUnderstood(new QName(header.getNamespaceURI(), header.getLocalPart()));
/*     */ 
/*     */     
/* 217 */     if (isNonSAAJHeader(header))
/*     */     {
/* 219 */       addNonSAAJHeader(find(header.getNamespaceURI(), header.getLocalPart()), header);
/*     */     }
/*     */ 
/*     */     
/* 223 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Header remove(QName name) {
/* 228 */     return remove(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */   
/*     */   public Header remove(String nsUri, String localName) {
/* 233 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 234 */     if (soapHeader == null) {
/* 235 */       return null;
/*     */     }
/* 237 */     SOAPHeaderElement headerElem = find(nsUri, localName);
/* 238 */     if (headerElem == null) {
/* 239 */       return null;
/*     */     }
/* 241 */     headerElem = (SOAPHeaderElement)soapHeader.removeChild((Node)headerElem);
/*     */ 
/*     */     
/* 244 */     removeNonSAAJHeader(headerElem);
/*     */ 
/*     */     
/* 247 */     QName hdrName = (nsUri == null) ? new QName(localName) : new QName(nsUri, localName);
/* 248 */     if (this.understoodHeaders != null) {
/* 249 */       this.understoodHeaders.remove(hdrName);
/*     */     }
/* 251 */     removeNotUnderstood(hdrName);
/*     */     
/* 253 */     return (Header)new SAAJHeader(headerElem);
/*     */   }
/*     */   
/*     */   private void removeNotUnderstood(QName hdrName) {
/* 257 */     if (this.notUnderstoodCount == null) {
/*     */       return;
/*     */     }
/* 260 */     Integer notUnderstood = this.notUnderstoodCount.get(hdrName);
/* 261 */     if (notUnderstood != null) {
/* 262 */       int intNotUnderstood = notUnderstood.intValue();
/* 263 */       intNotUnderstood--;
/* 264 */       if (intNotUnderstood <= 0) {
/* 265 */         this.notUnderstoodCount.remove(hdrName);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private SOAPHeaderElement find(QName qName) {
/* 272 */     return find(qName.getNamespaceURI(), qName.getLocalPart());
/*     */   }
/*     */   
/*     */   private SOAPHeaderElement find(String nsUri, String localName) {
/* 276 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 277 */     if (soapHeader == null) {
/* 278 */       return null;
/*     */     }
/* 280 */     Iterator<SOAPHeaderElement> allHeaders = soapHeader.examineAllHeaderElements();
/* 281 */     while (allHeaders.hasNext()) {
/* 282 */       SOAPHeaderElement nextHdrElem = allHeaders.next();
/* 283 */       if (nextHdrElem.getNamespaceURI().equals(nsUri) && nextHdrElem
/* 284 */         .getLocalName().equals(localName)) {
/* 285 */         return nextHdrElem;
/*     */       }
/*     */     } 
/* 288 */     return null;
/*     */   }
/*     */   
/*     */   private void notUnderstood(QName qName) {
/* 292 */     if (this.notUnderstoodCount == null) {
/* 293 */       this.notUnderstoodCount = new HashMap<>();
/*     */     }
/* 295 */     Integer count = this.notUnderstoodCount.get(qName);
/* 296 */     if (count == null) {
/* 297 */       this.notUnderstoodCount.put(qName, Integer.valueOf(1));
/*     */     } else {
/* 299 */       this.notUnderstoodCount.put(qName, Integer.valueOf(count.intValue() + 1));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 304 */     if (this.understoodHeaders != null) {
/* 305 */       this.understoodHeaders.remove(qName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPHeader ensureSOAPHeader() {
/*     */     try {
/* 316 */       SOAPHeader header = this.sm.getSOAPPart().getEnvelope().getHeader();
/* 317 */       if (header != null) {
/* 318 */         return header;
/*     */       }
/* 320 */       return this.sm.getSOAPPart().getEnvelope().addHeader();
/*     */     }
/* 322 */     catch (Exception e) {
/* 323 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isNonSAAJHeader(Header header) {
/* 328 */     return !(header instanceof SAAJHeader);
/*     */   }
/*     */   
/*     */   private void addNonSAAJHeader(SOAPHeaderElement headerElem, Header header) {
/* 332 */     if (this.nonSAAJHeaders == null) {
/* 333 */       this.nonSAAJHeaders = new HashMap<>();
/*     */     }
/* 335 */     this.nonSAAJHeaders.put(headerElem, header);
/*     */   }
/*     */   
/*     */   private void removeNonSAAJHeader(SOAPHeaderElement headerElem) {
/* 339 */     if (this.nonSAAJHeaders != null) {
/* 340 */       this.nonSAAJHeaders.remove(headerElem);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addOrReplace(Header header) {
/* 346 */     remove(header.getNamespaceURI(), header.getLocalPart());
/* 347 */     return add(header);
/*     */   }
/*     */ 
/*     */   
/*     */   public void replace(Header old, Header header) {
/* 352 */     if (remove(old.getNamespaceURI(), old.getLocalPart()) == null)
/* 353 */       throw new IllegalArgumentException(); 
/* 354 */     add(header);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<QName> getUnderstoodHeaders() {
/* 359 */     return this.understoodHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<QName> getNotUnderstoodHeaders(Set<String> roles, Set<QName> knownHeaders, WSBinding binding) {
/* 365 */     Set<QName> notUnderstoodHeaderNames = new HashSet<>();
/* 366 */     if (this.notUnderstoodCount == null) {
/* 367 */       return notUnderstoodHeaderNames;
/*     */     }
/* 369 */     for (QName headerName : this.notUnderstoodCount.keySet()) {
/* 370 */       int count = ((Integer)this.notUnderstoodCount.get(headerName)).intValue();
/* 371 */       if (count <= 0) {
/*     */         continue;
/*     */       }
/* 374 */       SOAPHeaderElement hdrElem = find(headerName);
/* 375 */       if (!hdrElem.getMustUnderstand()) {
/*     */         continue;
/*     */       }
/* 378 */       SAAJHeader hdr = new SAAJHeader(hdrElem);
/*     */ 
/*     */       
/* 381 */       boolean understood = false;
/* 382 */       if (roles != null) {
/* 383 */         understood = !roles.contains(hdr.getRole(this.soapVersion));
/*     */       }
/* 385 */       if (understood) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 390 */       if (binding != null && binding instanceof SOAPBindingImpl) {
/* 391 */         understood = ((SOAPBindingImpl)binding).understandsHeader(headerName);
/* 392 */         if (!understood && 
/* 393 */           knownHeaders != null && knownHeaders.contains(headerName)) {
/* 394 */           understood = true;
/*     */         }
/*     */       } 
/*     */       
/* 398 */       if (!understood) {
/* 399 */         notUnderstoodHeaderNames.add(headerName);
/*     */       }
/*     */     } 
/* 402 */     return notUnderstoodHeaderNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders() {
/* 407 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 408 */     if (soapHeader == null) {
/* 409 */       return null;
/*     */     }
/* 411 */     Iterator allHeaders = soapHeader.examineAllHeaderElements();
/* 412 */     return new HeaderReadIterator(allHeaders, null, null);
/*     */   }
/*     */   
/*     */   private static class HeaderReadIterator
/*     */     implements Iterator<Header> {
/*     */     SOAPHeaderElement current;
/*     */     Iterator soapHeaders;
/*     */     String myNsUri;
/*     */     String myLocalName;
/*     */     
/*     */     public HeaderReadIterator(Iterator allHeaders, String nsUri, String localName) {
/* 423 */       this.soapHeaders = allHeaders;
/* 424 */       this.myNsUri = nsUri;
/* 425 */       this.myLocalName = localName;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 430 */       if (this.current == null) {
/* 431 */         advance();
/*     */       }
/* 433 */       return (this.current != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Header next() {
/* 438 */       if (!hasNext()) {
/* 439 */         return null;
/*     */       }
/* 441 */       if (this.current == null) {
/* 442 */         return null;
/*     */       }
/*     */       
/* 445 */       SAAJHeader ret = new SAAJHeader(this.current);
/* 446 */       this.current = null;
/* 447 */       return (Header)ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 452 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     private void advance() {
/* 456 */       while (this.soapHeaders.hasNext()) {
/* 457 */         SOAPHeaderElement nextHdr = this.soapHeaders.next();
/* 458 */         if (nextHdr != null && (this.myNsUri == null || nextHdr
/* 459 */           .getNamespaceURI().equals(this.myNsUri)) && (this.myLocalName == null || nextHdr
/* 460 */           .getLocalName().equals(this.myLocalName))) {
/* 461 */           this.current = nextHdr;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 467 */       this.current = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/* 474 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 475 */     if (soapHeader == null) {
/* 476 */       return false;
/*     */     }
/*     */     
/* 479 */     Iterator allHeaders = soapHeader.examineAllHeaderElements();
/* 480 */     return allHeaders.hasNext();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Header> asList() {
/* 485 */     SOAPHeader soapHeader = ensureSOAPHeader();
/* 486 */     if (soapHeader == null) {
/* 487 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 490 */     Iterator<SOAPHeaderElement> allHeaders = soapHeader.examineAllHeaderElements();
/* 491 */     List<Header> headers = new ArrayList<>();
/* 492 */     while (allHeaders.hasNext()) {
/* 493 */       SOAPHeaderElement nextHdr = allHeaders.next();
/* 494 */       headers.add(new SAAJHeader(nextHdr));
/*     */     } 
/* 496 */     return headers;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/saaj/SAAJMessageHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */