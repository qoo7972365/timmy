/*     */ package com.sun.org.apache.xml.internal.serialize;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.AttributeList;
/*     */ import org.xml.sax.Attributes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HTMLSerializer
/*     */   extends BaseMarkupSerializer
/*     */ {
/*     */   private boolean _xhtml;
/*     */   public static final String XHTMLNamespace = "http://www.w3.org/1999/xhtml";
/* 108 */   private String fUserXHTMLNamespace = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HTMLSerializer(boolean xhtml, OutputFormat format) {
/* 120 */     super(format);
/* 121 */     this._xhtml = xhtml;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HTMLSerializer() {
/* 132 */     this(false, new OutputFormat("html", "ISO-8859-1", false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HTMLSerializer(OutputFormat format) {
/* 143 */     this(false, (format != null) ? format : new OutputFormat("html", "ISO-8859-1", false));
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
/*     */   public HTMLSerializer(Writer writer, OutputFormat format) {
/* 158 */     this(false, (format != null) ? format : new OutputFormat("html", "ISO-8859-1", false));
/* 159 */     setOutputCharStream(writer);
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
/*     */   public HTMLSerializer(OutputStream output, OutputFormat format) {
/* 173 */     this(false, (format != null) ? format : new OutputFormat("html", "ISO-8859-1", false));
/* 174 */     setOutputByteStream(output);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputFormat(OutputFormat format) {
/* 180 */     super.setOutputFormat((format != null) ? format : new OutputFormat("html", "ISO-8859-1", false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXHTMLNamespace(String newNamespace) {
/* 185 */     this.fUserXHTMLNamespace = newNamespace;
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
/*     */   public void startElement(String namespaceURI, String localName, String rawName, Attributes attrs) throws SAXException {
/* 203 */     boolean addNSAttr = false;
/*     */     try {
/*     */       String htmlName;
/* 206 */       if (this._printer == null) {
/* 207 */         throw new IllegalStateException(
/* 208 */             DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", null));
/*     */       }
/*     */ 
/*     */       
/* 212 */       ElementState state = getElementState();
/* 213 */       if (isDocumentState()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 218 */         if (!this._started) {
/* 219 */           startDocument((localName == null || localName.length() == 0) ? rawName : localName);
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 225 */         if (state.empty) {
/* 226 */           this._printer.printText('>');
/*     */         }
/*     */ 
/*     */         
/* 230 */         if (this._indenting && !state.preserveSpace && (state.empty || state.afterElement))
/*     */         {
/* 232 */           this._printer.breakLine(); } 
/*     */       } 
/* 234 */       boolean preserveSpace = state.preserveSpace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 242 */       boolean hasNamespaceURI = (namespaceURI != null && namespaceURI.length() != 0);
/*     */ 
/*     */ 
/*     */       
/* 246 */       if (rawName == null || rawName.length() == 0) {
/* 247 */         rawName = localName;
/* 248 */         if (hasNamespaceURI) {
/*     */           
/* 250 */           String prefix = getPrefix(namespaceURI);
/* 251 */           if (prefix != null && prefix.length() != 0)
/* 252 */             rawName = prefix + ":" + localName; 
/*     */         } 
/* 254 */         addNSAttr = true;
/*     */       } 
/* 256 */       if (!hasNamespaceURI) {
/* 257 */         htmlName = rawName;
/*     */       }
/* 259 */       else if (namespaceURI.equals("http://www.w3.org/1999/xhtml") || (this.fUserXHTMLNamespace != null && this.fUserXHTMLNamespace
/* 260 */         .equals(namespaceURI))) {
/* 261 */         htmlName = localName;
/*     */       } else {
/* 263 */         htmlName = null;
/*     */       } 
/*     */ 
/*     */       
/* 267 */       this._printer.printText('<');
/* 268 */       if (this._xhtml) {
/* 269 */         this._printer.printText(rawName.toLowerCase(Locale.ENGLISH));
/*     */       } else {
/* 271 */         this._printer.printText(rawName);
/* 272 */       }  this._printer.indent();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 277 */       if (attrs != null) {
/* 278 */         for (int i = 0; i < attrs.getLength(); i++) {
/* 279 */           this._printer.printSpace();
/* 280 */           String name = attrs.getQName(i).toLowerCase(Locale.ENGLISH);
/* 281 */           String value = attrs.getValue(i);
/* 282 */           if (this._xhtml || hasNamespaceURI) {
/*     */             
/* 284 */             if (value == null) {
/* 285 */               this._printer.printText(name);
/* 286 */               this._printer.printText("=\"\"");
/*     */             } else {
/* 288 */               this._printer.printText(name);
/* 289 */               this._printer.printText("=\"");
/* 290 */               printEscaped(value);
/* 291 */               this._printer.printText('"');
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 296 */             if (value == null) {
/* 297 */               value = "";
/*     */             }
/* 299 */             if (!this._format.getPreserveEmptyAttributes() && value.length() == 0) {
/* 300 */               this._printer.printText(name);
/* 301 */             } else if (HTMLdtd.isURI(rawName, name)) {
/* 302 */               this._printer.printText(name);
/* 303 */               this._printer.printText("=\"");
/* 304 */               this._printer.printText(escapeURI(value));
/* 305 */               this._printer.printText('"');
/* 306 */             } else if (HTMLdtd.isBoolean(rawName, name)) {
/* 307 */               this._printer.printText(name);
/*     */             } else {
/* 309 */               this._printer.printText(name);
/* 310 */               this._printer.printText("=\"");
/* 311 */               printEscaped(value);
/* 312 */               this._printer.printText('"');
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 317 */       if (htmlName != null && HTMLdtd.isPreserveSpace(htmlName)) {
/* 318 */         preserveSpace = true;
/*     */       }
/* 320 */       if (addNSAttr) {
/* 321 */         for (Map.Entry<String, String> entry : this._prefixes.entrySet()) {
/* 322 */           this._printer.printSpace();
/* 323 */           String value = entry.getKey();
/* 324 */           String name = entry.getValue();
/* 325 */           if (name.length() == 0) {
/* 326 */             this._printer.printText("xmlns=\"");
/* 327 */             printEscaped(value);
/* 328 */             this._printer.printText('"'); continue;
/*     */           } 
/* 330 */           this._printer.printText("xmlns:");
/* 331 */           this._printer.printText(name);
/* 332 */           this._printer.printText("=\"");
/* 333 */           printEscaped(value);
/* 334 */           this._printer.printText('"');
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 342 */       state = enterElementState(namespaceURI, localName, rawName, preserveSpace);
/*     */ 
/*     */ 
/*     */       
/* 346 */       if (htmlName != null && (htmlName.equalsIgnoreCase("A") || htmlName
/* 347 */         .equalsIgnoreCase("TD"))) {
/* 348 */         state.empty = false;
/* 349 */         this._printer.printText('>');
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 355 */       if (htmlName != null && (rawName.equalsIgnoreCase("SCRIPT") || rawName
/* 356 */         .equalsIgnoreCase("STYLE"))) {
/* 357 */         if (this._xhtml) {
/*     */           
/* 359 */           state.doCData = true;
/*     */         } else {
/*     */           
/* 362 */           state.unescaped = true;
/*     */         } 
/*     */       }
/* 365 */     } catch (IOException except) {
/* 366 */       throw new SAXException(except);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String rawName) throws SAXException {
/*     */     try {
/* 376 */       endElementIO(namespaceURI, localName, rawName);
/* 377 */     } catch (IOException except) {
/* 378 */       throw new SAXException(except);
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
/*     */   public void endElementIO(String namespaceURI, String localName, String rawName) throws IOException {
/*     */     String htmlName;
/* 393 */     this._printer.unindent();
/* 394 */     ElementState state = getElementState();
/*     */     
/* 396 */     if (state.namespaceURI == null || state.namespaceURI.length() == 0) {
/* 397 */       htmlName = state.rawName;
/*     */     }
/* 399 */     else if (state.namespaceURI.equals("http://www.w3.org/1999/xhtml") || (this.fUserXHTMLNamespace != null && this.fUserXHTMLNamespace
/* 400 */       .equals(state.namespaceURI))) {
/* 401 */       htmlName = state.localName;
/*     */     } else {
/* 403 */       htmlName = null;
/*     */     } 
/*     */     
/* 406 */     if (this._xhtml) {
/* 407 */       if (state.empty) {
/* 408 */         this._printer.printText(" />");
/*     */       } else {
/*     */         
/* 411 */         if (state.inCData) {
/* 412 */           this._printer.printText("]]>");
/*     */         }
/* 414 */         this._printer.printText("</");
/* 415 */         this._printer.printText(state.rawName.toLowerCase(Locale.ENGLISH));
/* 416 */         this._printer.printText('>');
/*     */       } 
/*     */     } else {
/* 419 */       if (state.empty) {
/* 420 */         this._printer.printText('>');
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 426 */       if (htmlName == null || !HTMLdtd.isOnlyOpening(htmlName)) {
/* 427 */         if (this._indenting && !state.preserveSpace && state.afterElement) {
/* 428 */           this._printer.breakLine();
/*     */         }
/* 430 */         if (state.inCData)
/* 431 */           this._printer.printText("]]>"); 
/* 432 */         this._printer.printText("</");
/* 433 */         this._printer.printText(state.rawName);
/* 434 */         this._printer.printText('>');
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 439 */     state = leaveElementState();
/*     */     
/* 441 */     if (htmlName == null || (!htmlName.equalsIgnoreCase("A") && 
/* 442 */       !htmlName.equalsIgnoreCase("TD")))
/*     */     {
/* 444 */       state.afterElement = true; } 
/* 445 */     state.empty = false;
/* 446 */     if (isDocumentState()) {
/* 447 */       this._printer.flush();
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
/*     */   public void characters(char[] chars, int start, int length) throws SAXException {
/*     */     try {
/* 463 */       ElementState state = content();
/* 464 */       state.doCData = false;
/* 465 */       super.characters(chars, start, length);
/* 466 */     } catch (IOException except) {
/* 467 */       throw new SAXException(except);
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
/*     */   public void startElement(String tagName, AttributeList attrs) throws SAXException {
/*     */     try {
/* 482 */       if (this._printer == null) {
/* 483 */         throw new IllegalStateException(
/* 484 */             DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", null));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 489 */       ElementState state = getElementState();
/* 490 */       if (isDocumentState()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 495 */         if (!this._started) {
/* 496 */           startDocument(tagName);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 501 */         if (state.empty) {
/* 502 */           this._printer.printText('>');
/*     */         }
/*     */ 
/*     */         
/* 506 */         if (this._indenting && !state.preserveSpace && (state.empty || state.afterElement))
/*     */         {
/* 508 */           this._printer.breakLine(); } 
/*     */       } 
/* 510 */       boolean preserveSpace = state.preserveSpace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 516 */       this._printer.printText('<');
/* 517 */       if (this._xhtml) {
/* 518 */         this._printer.printText(tagName.toLowerCase(Locale.ENGLISH));
/*     */       } else {
/* 520 */         this._printer.printText(tagName);
/* 521 */       }  this._printer.indent();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 526 */       if (attrs != null) {
/* 527 */         for (int i = 0; i < attrs.getLength(); i++) {
/* 528 */           this._printer.printSpace();
/* 529 */           String name = attrs.getName(i).toLowerCase(Locale.ENGLISH);
/* 530 */           String value = attrs.getValue(i);
/* 531 */           if (this._xhtml) {
/*     */             
/* 533 */             if (value == null) {
/* 534 */               this._printer.printText(name);
/* 535 */               this._printer.printText("=\"\"");
/*     */             } else {
/* 537 */               this._printer.printText(name);
/* 538 */               this._printer.printText("=\"");
/* 539 */               printEscaped(value);
/* 540 */               this._printer.printText('"');
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 545 */             if (value == null) {
/* 546 */               value = "";
/*     */             }
/* 548 */             if (!this._format.getPreserveEmptyAttributes() && value.length() == 0) {
/* 549 */               this._printer.printText(name);
/* 550 */             } else if (HTMLdtd.isURI(tagName, name)) {
/* 551 */               this._printer.printText(name);
/* 552 */               this._printer.printText("=\"");
/* 553 */               this._printer.printText(escapeURI(value));
/* 554 */               this._printer.printText('"');
/* 555 */             } else if (HTMLdtd.isBoolean(tagName, name)) {
/* 556 */               this._printer.printText(name);
/*     */             } else {
/* 558 */               this._printer.printText(name);
/* 559 */               this._printer.printText("=\"");
/* 560 */               printEscaped(value);
/* 561 */               this._printer.printText('"');
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 566 */       if (HTMLdtd.isPreserveSpace(tagName)) {
/* 567 */         preserveSpace = true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 572 */       state = enterElementState(null, null, tagName, preserveSpace);
/*     */ 
/*     */       
/* 575 */       if (tagName.equalsIgnoreCase("A") || tagName.equalsIgnoreCase("TD")) {
/* 576 */         state.empty = false;
/* 577 */         this._printer.printText('>');
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 583 */       if (tagName.equalsIgnoreCase("SCRIPT") || tagName
/* 584 */         .equalsIgnoreCase("STYLE")) {
/* 585 */         if (this._xhtml) {
/*     */           
/* 587 */           state.doCData = true;
/*     */         } else {
/*     */           
/* 590 */           state.unescaped = true;
/*     */         } 
/*     */       }
/* 593 */     } catch (IOException except) {
/* 594 */       throw new SAXException(except);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String tagName) throws SAXException {
/* 602 */     endElement((String)null, (String)null, tagName);
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
/*     */   protected void startDocument(String rootTagName) throws IOException {
/* 630 */     this._printer.leaveDTD();
/* 631 */     if (!this._started) {
/*     */ 
/*     */ 
/*     */       
/* 635 */       if (this._docTypePublicId == null && this._docTypeSystemId == null) {
/* 636 */         if (this._xhtml) {
/* 637 */           this._docTypePublicId = "-//W3C//DTD XHTML 1.0 Strict//EN";
/* 638 */           this._docTypeSystemId = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd";
/*     */         } else {
/* 640 */           this._docTypePublicId = "-//W3C//DTD HTML 4.01//EN";
/* 641 */           this._docTypeSystemId = "http://www.w3.org/TR/html4/strict.dtd";
/*     */         } 
/*     */       }
/*     */       
/* 645 */       if (!this._format.getOmitDocumentType())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 652 */         if (this._docTypePublicId != null && (!this._xhtml || this._docTypeSystemId != null)) {
/* 653 */           if (this._xhtml) {
/* 654 */             this._printer.printText("<!DOCTYPE html PUBLIC ");
/*     */           } else {
/*     */             
/* 657 */             this._printer.printText("<!DOCTYPE HTML PUBLIC ");
/*     */           } 
/* 659 */           printDoctypeURL(this._docTypePublicId);
/* 660 */           if (this._docTypeSystemId != null) {
/* 661 */             if (this._indenting) {
/* 662 */               this._printer.breakLine();
/* 663 */               this._printer.printText("                      ");
/*     */             } else {
/* 665 */               this._printer.printText(' ');
/* 666 */             }  printDoctypeURL(this._docTypeSystemId);
/*     */           } 
/* 668 */           this._printer.printText('>');
/* 669 */           this._printer.breakLine();
/* 670 */         } else if (this._docTypeSystemId != null) {
/* 671 */           if (this._xhtml) {
/* 672 */             this._printer.printText("<!DOCTYPE html SYSTEM ");
/*     */           } else {
/*     */             
/* 675 */             this._printer.printText("<!DOCTYPE HTML SYSTEM ");
/*     */           } 
/* 677 */           printDoctypeURL(this._docTypeSystemId);
/* 678 */           this._printer.printText('>');
/* 679 */           this._printer.breakLine();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 684 */     this._started = true;
/*     */     
/* 686 */     serializePreRoot();
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
/*     */   protected void serializeElement(Element elem) throws IOException {
/* 708 */     String tagName = elem.getTagName();
/* 709 */     ElementState state = getElementState();
/* 710 */     if (isDocumentState()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 715 */       if (!this._started) {
/* 716 */         startDocument(tagName);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 721 */       if (state.empty) {
/* 722 */         this._printer.printText('>');
/*     */       }
/*     */ 
/*     */       
/* 726 */       if (this._indenting && !state.preserveSpace && (state.empty || state.afterElement))
/*     */       {
/* 728 */         this._printer.breakLine(); } 
/*     */     } 
/* 730 */     boolean preserveSpace = state.preserveSpace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 736 */     this._printer.printText('<');
/* 737 */     if (this._xhtml) {
/* 738 */       this._printer.printText(tagName.toLowerCase(Locale.ENGLISH));
/*     */     } else {
/* 740 */       this._printer.printText(tagName);
/* 741 */     }  this._printer.indent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 748 */     NamedNodeMap attrMap = elem.getAttributes();
/* 749 */     if (attrMap != null) {
/* 750 */       for (int i = 0; i < attrMap.getLength(); i++) {
/* 751 */         Attr attr = (Attr)attrMap.item(i);
/* 752 */         String name = attr.getName().toLowerCase(Locale.ENGLISH);
/* 753 */         String value = attr.getValue();
/* 754 */         if (attr.getSpecified()) {
/* 755 */           this._printer.printSpace();
/* 756 */           if (this._xhtml) {
/*     */             
/* 758 */             if (value == null) {
/* 759 */               this._printer.printText(name);
/* 760 */               this._printer.printText("=\"\"");
/*     */             } else {
/* 762 */               this._printer.printText(name);
/* 763 */               this._printer.printText("=\"");
/* 764 */               printEscaped(value);
/* 765 */               this._printer.printText('"');
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 770 */             if (value == null) {
/* 771 */               value = "";
/*     */             }
/* 773 */             if (!this._format.getPreserveEmptyAttributes() && value.length() == 0) {
/* 774 */               this._printer.printText(name);
/* 775 */             } else if (HTMLdtd.isURI(tagName, name)) {
/* 776 */               this._printer.printText(name);
/* 777 */               this._printer.printText("=\"");
/* 778 */               this._printer.printText(escapeURI(value));
/* 779 */               this._printer.printText('"');
/* 780 */             } else if (HTMLdtd.isBoolean(tagName, name)) {
/* 781 */               this._printer.printText(name);
/*     */             } else {
/* 783 */               this._printer.printText(name);
/* 784 */               this._printer.printText("=\"");
/* 785 */               printEscaped(value);
/* 786 */               this._printer.printText('"');
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 792 */     if (HTMLdtd.isPreserveSpace(tagName)) {
/* 793 */       preserveSpace = true;
/*     */     }
/*     */ 
/*     */     
/* 797 */     if (elem.hasChildNodes() || !HTMLdtd.isEmptyTag(tagName)) {
/*     */ 
/*     */       
/* 800 */       state = enterElementState(null, null, tagName, preserveSpace);
/*     */ 
/*     */       
/* 803 */       if (tagName.equalsIgnoreCase("A") || tagName.equalsIgnoreCase("TD")) {
/* 804 */         state.empty = false;
/* 805 */         this._printer.printText('>');
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 811 */       if (tagName.equalsIgnoreCase("SCRIPT") || tagName
/* 812 */         .equalsIgnoreCase("STYLE")) {
/* 813 */         if (this._xhtml) {
/*     */           
/* 815 */           state.doCData = true;
/*     */         } else {
/*     */           
/* 818 */           state.unescaped = true;
/*     */         } 
/*     */       }
/* 821 */       Node child = elem.getFirstChild();
/* 822 */       while (child != null) {
/* 823 */         serializeNode(child);
/* 824 */         child = child.getNextSibling();
/*     */       } 
/* 826 */       endElementIO((String)null, (String)null, tagName);
/*     */     } else {
/* 828 */       this._printer.unindent();
/*     */ 
/*     */       
/* 831 */       if (this._xhtml) {
/* 832 */         this._printer.printText(" />");
/*     */       } else {
/* 834 */         this._printer.printText('>');
/*     */       } 
/* 836 */       state.afterElement = true;
/* 837 */       state.empty = false;
/* 838 */       if (isDocumentState()) {
/* 839 */         this._printer.flush();
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
/*     */   protected void characters(String text) throws IOException {
/* 851 */     ElementState state = content();
/* 852 */     super.characters(text);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getEntityRef(int ch) {
/* 858 */     return HTMLdtd.fromChar(ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String escapeURI(String uri) {
/* 868 */     int index = uri.indexOf("\"");
/* 869 */     if (index >= 0) {
/* 870 */       return uri.substring(0, index);
/*     */     }
/* 872 */     return uri;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/HTMLSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */