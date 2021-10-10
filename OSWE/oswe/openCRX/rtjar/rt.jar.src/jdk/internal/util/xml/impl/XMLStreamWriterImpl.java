/*     */ package jdk.internal.util.xml.impl;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import jdk.internal.util.xml.XMLStreamException;
/*     */ import jdk.internal.util.xml.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamWriterImpl
/*     */   implements XMLStreamWriter
/*     */ {
/*     */   static final int STATE_XML_DECL = 1;
/*     */   static final int STATE_PROLOG = 2;
/*     */   static final int STATE_DTD_DECL = 3;
/*     */   static final int STATE_ELEMENT = 4;
/*     */   static final int ELEMENT_STARTTAG_OPEN = 10;
/*     */   static final int ELEMENT_STARTTAG_CLOSE = 11;
/*     */   static final int ELEMENT_ENDTAG_OPEN = 12;
/*     */   static final int ELEMENT_ENDTAG_CLOSE = 13;
/*     */   public static final char CLOSE_START_TAG = '>';
/*     */   public static final char OPEN_START_TAG = '<';
/*     */   public static final String OPEN_END_TAG = "</";
/*     */   public static final char CLOSE_END_TAG = '>';
/*     */   public static final String START_CDATA = "<![CDATA[";
/*     */   public static final String END_CDATA = "]]>";
/*     */   public static final String CLOSE_EMPTY_ELEMENT = "/>";
/*     */   public static final String ENCODING_PREFIX = "&#x";
/*     */   public static final char SPACE = ' ';
/*     */   public static final char AMPERSAND = '&';
/*     */   public static final char DOUBLEQUOT = '"';
/*     */   public static final char SEMICOLON = ';';
/*  66 */   private int _state = 0;
/*     */ 
/*     */   
/*     */   private Element _currentEle;
/*     */   
/*     */   private XMLWriter _writer;
/*     */   
/*     */   private String _encoding;
/*     */   
/*     */   boolean _escapeCharacters = true;
/*     */   
/*     */   private boolean _doIndent = true;
/*     */   
/*  79 */   private char[] _lineSep = System.getProperty("line.separator").toCharArray();
/*     */   
/*     */   public XMLStreamWriterImpl(OutputStream paramOutputStream) throws XMLStreamException {
/*  82 */     this(paramOutputStream, "UTF-8");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamWriterImpl(OutputStream paramOutputStream, String paramString) throws XMLStreamException {
/*  88 */     Charset charset = null;
/*  89 */     if (paramString == null) {
/*  90 */       this._encoding = "UTF-8";
/*     */     } else {
/*     */       try {
/*  93 */         charset = getCharset(paramString);
/*  94 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  95 */         throw new XMLStreamException(unsupportedEncodingException);
/*     */       } 
/*     */       
/*  98 */       this._encoding = paramString;
/*     */     } 
/*     */     
/* 101 */     this._writer = new XMLWriter(paramOutputStream, paramString, charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 111 */     writeStartDocument(this._encoding, "1.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument(String paramString) throws XMLStreamException {
/* 121 */     writeStartDocument(this._encoding, paramString, null);
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
/*     */   public void writeStartDocument(String paramString1, String paramString2) throws XMLStreamException {
/* 135 */     writeStartDocument(paramString1, paramString2, null);
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
/*     */   public void writeStartDocument(String paramString1, String paramString2, String paramString3) throws XMLStreamException {
/* 152 */     if (this._state > 0) {
/* 153 */       throw new XMLStreamException("XML declaration must be as the first line in the XML document.");
/*     */     }
/* 155 */     this._state = 1;
/* 156 */     String str = paramString1;
/* 157 */     if (str == null) {
/* 158 */       str = this._encoding;
/*     */     } else {
/*     */       
/*     */       try {
/* 162 */         getCharset(paramString1);
/* 163 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 164 */         throw new XMLStreamException(unsupportedEncodingException);
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     if (paramString2 == null) {
/* 169 */       paramString2 = "1.0";
/*     */     }
/*     */     
/* 172 */     this._writer.write("<?xml version=\"");
/* 173 */     this._writer.write(paramString2);
/* 174 */     this._writer.write(34);
/*     */     
/* 176 */     if (str != null) {
/* 177 */       this._writer.write(" encoding=\"");
/* 178 */       this._writer.write(str);
/* 179 */       this._writer.write(34);
/*     */     } 
/*     */     
/* 182 */     if (paramString3 != null) {
/* 183 */       this._writer.write(" standalone=\"");
/* 184 */       this._writer.write(paramString3);
/* 185 */       this._writer.write(34);
/*     */     } 
/* 187 */     this._writer.write("?>");
/* 188 */     writeLineSeparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDTD(String paramString) throws XMLStreamException {
/* 199 */     if (this._currentEle != null && this._currentEle.getState() == 10) {
/* 200 */       closeStartTag();
/*     */     }
/* 202 */     this._writer.write(paramString);
/* 203 */     writeLineSeparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartElement(String paramString) throws XMLStreamException {
/* 212 */     if (paramString == null || paramString.length() == 0) {
/* 213 */       throw new XMLStreamException("Local Name cannot be null or empty");
/*     */     }
/*     */     
/* 216 */     this._state = 4;
/* 217 */     if (this._currentEle != null && this._currentEle.getState() == 10) {
/* 218 */       closeStartTag();
/*     */     }
/*     */     
/* 221 */     this._currentEle = new Element(this._currentEle, paramString, false);
/* 222 */     openStartTag();
/*     */     
/* 224 */     this._writer.write(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String paramString) throws XMLStreamException {
/* 233 */     if (this._currentEle != null && this._currentEle.getState() == 10) {
/* 234 */       closeStartTag();
/*     */     }
/*     */     
/* 237 */     this._currentEle = new Element(this._currentEle, paramString, true);
/*     */     
/* 239 */     openStartTag();
/* 240 */     this._writer.write(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String paramString1, String paramString2) throws XMLStreamException {
/* 251 */     if (this._currentEle.getState() != 10) {
/* 252 */       throw new XMLStreamException("Attribute not associated with any element");
/*     */     }
/*     */ 
/*     */     
/* 256 */     this._writer.write(32);
/* 257 */     this._writer.write(paramString1);
/* 258 */     this._writer.write("=\"");
/* 259 */     writeXMLContent(paramString2, true, true);
/*     */ 
/*     */ 
/*     */     
/* 263 */     this._writer.write(34);
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/* 267 */     if (this._currentEle != null && this._currentEle.getState() == 10) {
/* 268 */       closeStartTag();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     while (this._currentEle != null) {
/*     */       
/* 276 */       if (!this._currentEle.isEmpty()) {
/* 277 */         this._writer.write("</");
/* 278 */         this._writer.write(this._currentEle.getLocalName());
/* 279 */         this._writer.write(62);
/*     */       } 
/*     */       
/* 282 */       this._currentEle = this._currentEle.getParent();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 287 */     if (this._currentEle != null && this._currentEle.getState() == 10) {
/* 288 */       closeStartTag();
/*     */     }
/*     */     
/* 291 */     if (this._currentEle == null) {
/* 292 */       throw new XMLStreamException("No element was found to write");
/*     */     }
/*     */     
/* 295 */     if (this._currentEle.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 299 */     this._writer.write("</");
/* 300 */     this._writer.write(this._currentEle.getLocalName());
/* 301 */     this._writer.write(62);
/* 302 */     writeLineSeparator();
/*     */     
/* 304 */     this._currentEle = this._currentEle.getParent();
/*     */   }
/*     */   
/*     */   public void writeCData(String paramString) throws XMLStreamException {
/* 308 */     if (paramString == null) {
/* 309 */       throw new XMLStreamException("cdata cannot be null");
/*     */     }
/*     */     
/* 312 */     if (this._currentEle != null && this._currentEle.getState() == 10) {
/* 313 */       closeStartTag();
/*     */     }
/*     */     
/* 316 */     this._writer.write("<![CDATA[");
/* 317 */     this._writer.write(paramString);
/* 318 */     this._writer.write("]]>");
/*     */   }
/*     */   
/*     */   public void writeCharacters(String paramString) throws XMLStreamException {
/* 322 */     if (this._currentEle != null && this._currentEle.getState() == 10) {
/* 323 */       closeStartTag();
/*     */     }
/*     */     
/* 326 */     writeXMLContent(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCharacters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws XMLStreamException {
/* 331 */     if (this._currentEle != null && this._currentEle.getState() == 10) {
/* 332 */       closeStartTag();
/*     */     }
/*     */     
/* 335 */     writeXMLContent(paramArrayOfchar, paramInt1, paramInt2, this._escapeCharacters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 342 */     if (this._writer != null) {
/* 343 */       this._writer.close();
/*     */     }
/* 345 */     this._writer = null;
/* 346 */     this._currentEle = null;
/* 347 */     this._state = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {
/* 354 */     if (this._writer != null) {
/* 355 */       this._writer.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoIndent(boolean paramBoolean) {
/* 364 */     this._doIndent = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeXMLContent(char[] paramArrayOfchar, int paramInt1, int paramInt2, boolean paramBoolean) throws XMLStreamException {
/* 374 */     if (!paramBoolean) {
/* 375 */       this._writer.write(paramArrayOfchar, paramInt1, paramInt2);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 380 */     int i = paramInt1;
/*     */     
/* 382 */     int j = paramInt1 + paramInt2;
/*     */     
/* 384 */     for (int k = paramInt1; k < j; k++) {
/* 385 */       char c = paramArrayOfchar[k];
/*     */       
/* 387 */       if (!this._writer.canEncode(c)) {
/* 388 */         this._writer.write(paramArrayOfchar, i, k - i);
/*     */ 
/*     */         
/* 391 */         this._writer.write("&#x");
/* 392 */         this._writer.write(Integer.toHexString(c));
/* 393 */         this._writer.write(59);
/* 394 */         i = k + 1;
/*     */       }
/*     */       else {
/*     */         
/* 398 */         switch (c) {
/*     */           case '<':
/* 400 */             this._writer.write(paramArrayOfchar, i, k - i);
/* 401 */             this._writer.write("&lt;");
/* 402 */             i = k + 1;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '&':
/* 407 */             this._writer.write(paramArrayOfchar, i, k - i);
/* 408 */             this._writer.write("&amp;");
/* 409 */             i = k + 1;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '>':
/* 414 */             this._writer.write(paramArrayOfchar, i, k - i);
/* 415 */             this._writer.write("&gt;");
/* 416 */             i = k + 1;
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/*     */     } 
/* 423 */     this._writer.write(paramArrayOfchar, i, j - i);
/*     */   }
/*     */   
/*     */   private void writeXMLContent(String paramString) throws XMLStreamException {
/* 427 */     if (paramString != null && paramString.length() > 0) {
/* 428 */       writeXMLContent(paramString, this._escapeCharacters, false);
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
/*     */   private void writeXMLContent(String paramString, boolean paramBoolean1, boolean paramBoolean2) throws XMLStreamException {
/* 445 */     if (!paramBoolean1) {
/* 446 */       this._writer.write(paramString);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 452 */     int i = 0;
/*     */     
/* 454 */     int j = paramString.length();
/*     */     
/* 456 */     for (byte b = 0; b < j; b++) {
/* 457 */       char c = paramString.charAt(b);
/*     */       
/* 459 */       if (!this._writer.canEncode(c)) {
/* 460 */         this._writer.write(paramString, i, b - i);
/*     */ 
/*     */         
/* 463 */         this._writer.write("&#x");
/* 464 */         this._writer.write(Integer.toHexString(c));
/* 465 */         this._writer.write(59);
/* 466 */         i = b + 1;
/*     */       }
/*     */       else {
/*     */         
/* 470 */         switch (c) {
/*     */           case '<':
/* 472 */             this._writer.write(paramString, i, b - i);
/* 473 */             this._writer.write("&lt;");
/* 474 */             i = b + 1;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '&':
/* 479 */             this._writer.write(paramString, i, b - i);
/* 480 */             this._writer.write("&amp;");
/* 481 */             i = b + 1;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '>':
/* 486 */             this._writer.write(paramString, i, b - i);
/* 487 */             this._writer.write("&gt;");
/* 488 */             i = b + 1;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 493 */             this._writer.write(paramString, i, b - i);
/* 494 */             if (paramBoolean2) {
/* 495 */               this._writer.write("&quot;");
/*     */             } else {
/* 497 */               this._writer.write(34);
/*     */             } 
/* 499 */             i = b + 1;
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/*     */     } 
/* 506 */     this._writer.write(paramString, i, j - i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void openStartTag() throws XMLStreamException {
/* 513 */     this._currentEle.setState(10);
/* 514 */     this._writer.write(60);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeStartTag() throws XMLStreamException {
/* 521 */     if (this._currentEle.isEmpty()) {
/* 522 */       this._writer.write("/>");
/*     */     } else {
/* 524 */       this._writer.write(62);
/*     */     } 
/*     */ 
/*     */     
/* 528 */     if (this._currentEle.getParent() == null) {
/* 529 */       writeLineSeparator();
/*     */     }
/*     */     
/* 532 */     this._currentEle.setState(11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeLineSeparator() throws XMLStreamException {
/* 541 */     if (this._doIndent) {
/* 542 */       this._writer.write(this._lineSep, 0, this._lineSep.length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Charset getCharset(String paramString) throws UnsupportedEncodingException {
/*     */     Charset charset;
/* 553 */     if (paramString.equalsIgnoreCase("UTF-32")) {
/* 554 */       throw new UnsupportedEncodingException("The basic XMLWriter does not support " + paramString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 560 */       charset = Charset.forName(paramString);
/* 561 */     } catch (IllegalCharsetNameException|java.nio.charset.UnsupportedCharsetException illegalCharsetNameException) {
/* 562 */       throw new UnsupportedEncodingException(paramString);
/*     */     } 
/* 564 */     return charset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class Element
/*     */   {
/*     */     protected Element _parent;
/*     */ 
/*     */ 
/*     */     
/*     */     protected short _Depth;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean _isEmptyElement = false;
/*     */ 
/*     */ 
/*     */     
/*     */     String _localpart;
/*     */ 
/*     */ 
/*     */     
/*     */     int _state;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Element() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Element(Element param1Element, String param1String, boolean param1Boolean) {
/* 600 */       this._parent = param1Element;
/* 601 */       this._localpart = param1String;
/* 602 */       this._isEmptyElement = param1Boolean;
/*     */     }
/*     */     
/*     */     public Element getParent() {
/* 606 */       return this._parent;
/*     */     }
/*     */     
/*     */     public String getLocalName() {
/* 610 */       return this._localpart;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getState() {
/* 617 */       return this._state;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setState(int param1Int) {
/* 626 */       this._state = param1Int;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 630 */       return this._isEmptyElement;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/XMLStreamWriterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */