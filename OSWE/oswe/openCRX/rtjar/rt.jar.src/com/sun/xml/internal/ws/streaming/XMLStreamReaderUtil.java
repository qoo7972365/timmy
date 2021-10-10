/*     */ package com.sun.xml.internal.ws.streaming;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamReaderUtil
/*     */ {
/*     */   public static void close(XMLStreamReader reader) {
/*     */     try {
/*  47 */       reader.close();
/*  48 */     } catch (XMLStreamException e) {
/*  49 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void readRest(XMLStreamReader reader) {
/*     */     try {
/*  55 */       while (reader.getEventType() != 8) {
/*  56 */         reader.next();
/*     */       }
/*  58 */     } catch (XMLStreamException e) {
/*  59 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int next(XMLStreamReader reader) {
/*     */     try {
/*  65 */       int readerEvent = reader.next();
/*     */       
/*  67 */       while (readerEvent != 8) {
/*  68 */         switch (readerEvent) {
/*     */           case 1:
/*     */           case 2:
/*     */           case 3:
/*     */           case 4:
/*     */           case 12:
/*  74 */             return readerEvent;
/*     */         } 
/*     */ 
/*     */         
/*  78 */         readerEvent = reader.next();
/*     */       } 
/*     */       
/*  81 */       return readerEvent;
/*     */     }
/*  83 */     catch (XMLStreamException e) {
/*  84 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int nextElementContent(XMLStreamReader reader) {
/*  89 */     int state = nextContent(reader);
/*  90 */     if (state == 4) {
/*  91 */       throw new XMLStreamReaderException("xmlreader.unexpectedCharacterContent", new Object[] { reader
/*  92 */             .getText() });
/*     */     }
/*  94 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void toNextTag(XMLStreamReader reader, QName name) {
/*  99 */     if (reader.getEventType() != 1 && reader
/* 100 */       .getEventType() != 2) {
/* 101 */       nextElementContent(reader);
/*     */     }
/* 103 */     if (reader.getEventType() == 2 && name.equals(reader.getName())) {
/* 104 */       nextElementContent(reader);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String nextWhiteSpaceContent(XMLStreamReader reader) {
/* 115 */     next(reader);
/* 116 */     return currentWhiteSpaceContent(reader);
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
/*     */   public static String currentWhiteSpaceContent(XMLStreamReader reader) {
/* 129 */     StringBuilder whiteSpaces = null;
/*     */     
/*     */     while (true) {
/* 132 */       switch (reader.getEventType()) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 8:
/* 136 */           return (whiteSpaces == null) ? null : whiteSpaces.toString();
/*     */         case 4:
/* 138 */           if (reader.isWhiteSpace()) {
/* 139 */             if (whiteSpaces == null) {
/* 140 */               whiteSpaces = new StringBuilder();
/*     */             }
/* 142 */             whiteSpaces.append(reader.getText()); break;
/*     */           } 
/* 144 */           throw new XMLStreamReaderException("xmlreader.unexpectedCharacterContent", new Object[] { reader
/* 145 */                 .getText() });
/*     */       } 
/*     */       
/* 148 */       next(reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int nextContent(XMLStreamReader reader) {
/*     */     while (true) {
/* 154 */       int state = next(reader);
/* 155 */       switch (state) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 8:
/* 159 */           return state;
/*     */         case 4:
/* 161 */           if (!reader.isWhiteSpace()) {
/* 162 */             return 4;
/*     */           }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void skipElement(XMLStreamReader reader) {
/* 173 */     assert reader.getEventType() == 1;
/* 174 */     skipTags(reader, true);
/* 175 */     assert reader.getEventType() == 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void skipSiblings(XMLStreamReader reader, QName parent) {
/* 183 */     skipTags(reader, reader.getName().equals(parent));
/* 184 */     assert reader.getEventType() == 2;
/*     */   }
/*     */   
/*     */   private static void skipTags(XMLStreamReader reader, boolean exitCondition) {
/*     */     try {
/* 189 */       int tags = 0; int state;
/* 190 */       while ((state = reader.next()) != 8) {
/* 191 */         if (state == 1) {
/* 192 */           tags++; continue;
/*     */         } 
/* 194 */         if (state == 2) {
/* 195 */           if (tags == 0 && exitCondition)
/* 196 */             return;  tags--;
/*     */         }
/*     */       
/*     */       } 
/* 200 */     } catch (XMLStreamException e) {
/* 201 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getElementText(XMLStreamReader reader) {
/*     */     try {
/* 210 */       return reader.getElementText();
/* 211 */     } catch (XMLStreamException e) {
/* 212 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QName getElementQName(XMLStreamReader reader) {
/*     */     try {
/* 223 */       String text = reader.getElementText().trim();
/* 224 */       String prefix = text.substring(0, text.indexOf(':'));
/* 225 */       String namespaceURI = reader.getNamespaceContext().getNamespaceURI(prefix);
/* 226 */       if (namespaceURI == null) {
/* 227 */         namespaceURI = "";
/*     */       }
/* 229 */       String localPart = text.substring(text
/* 230 */           .indexOf(':') + 1, text.length());
/* 231 */       return new QName(namespaceURI, localPart);
/* 232 */     } catch (XMLStreamException e) {
/* 233 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Attributes getAttributes(XMLStreamReader reader) {
/* 242 */     return (reader.getEventType() == 1 || reader
/* 243 */       .getEventType() == 10) ? new AttributesImpl(reader) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void verifyReaderState(XMLStreamReader reader, int expectedState) {
/* 248 */     int state = reader.getEventType();
/* 249 */     if (state != expectedState)
/* 250 */       throw new XMLStreamReaderException("xmlreader.unexpectedState", new Object[] {
/*     */             
/* 252 */             getStateName(expectedState), getStateName(state)
/*     */           }); 
/*     */   }
/*     */   
/*     */   public static void verifyTag(XMLStreamReader reader, String namespaceURI, String localName) {
/* 257 */     if (!localName.equals(reader.getLocalName()) || !namespaceURI.equals(reader.getNamespaceURI())) {
/* 258 */       throw new XMLStreamReaderException("xmlreader.unexpectedState.tag", new Object[] { "{" + namespaceURI + "}" + localName, "{" + reader
/*     */ 
/*     */             
/* 261 */             .getNamespaceURI() + "}" + reader.getLocalName() });
/*     */     }
/*     */   }
/*     */   
/*     */   public static void verifyTag(XMLStreamReader reader, QName name) {
/* 266 */     verifyTag(reader, name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */   
/*     */   public static String getStateName(XMLStreamReader reader) {
/* 270 */     return getStateName(reader.getEventType());
/*     */   }
/*     */   
/*     */   public static String getStateName(int state) {
/* 274 */     switch (state) {
/*     */       case 10:
/* 276 */         return "ATTRIBUTE";
/*     */       case 12:
/* 278 */         return "CDATA";
/*     */       case 4:
/* 280 */         return "CHARACTERS";
/*     */       case 5:
/* 282 */         return "COMMENT";
/*     */       case 11:
/* 284 */         return "DTD";
/*     */       case 8:
/* 286 */         return "END_DOCUMENT";
/*     */       case 2:
/* 288 */         return "END_ELEMENT";
/*     */       case 15:
/* 290 */         return "ENTITY_DECLARATION";
/*     */       case 9:
/* 292 */         return "ENTITY_REFERENCE";
/*     */       case 13:
/* 294 */         return "NAMESPACE";
/*     */       case 14:
/* 296 */         return "NOTATION_DECLARATION";
/*     */       case 3:
/* 298 */         return "PROCESSING_INSTRUCTION";
/*     */       case 6:
/* 300 */         return "SPACE";
/*     */       case 7:
/* 302 */         return "START_DOCUMENT";
/*     */       case 1:
/* 304 */         return "START_ELEMENT";
/*     */     } 
/* 306 */     return "UNKNOWN";
/*     */   }
/*     */ 
/*     */   
/*     */   private static XMLStreamReaderException wrapException(XMLStreamException e) {
/* 311 */     return new XMLStreamReaderException("xmlreader.ioException", new Object[] { e });
/*     */   }
/*     */ 
/*     */   
/*     */   public static class AttributesImpl
/*     */     implements Attributes
/*     */   {
/*     */     static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
/*     */     
/*     */     AttributeInfo[] atInfos;
/*     */ 
/*     */     
/*     */     static class AttributeInfo
/*     */     {
/*     */       private QName name;
/*     */       
/*     */       private String value;
/*     */       
/*     */       public AttributeInfo(QName name, String value) {
/* 330 */         this.name = name;
/* 331 */         if (value == null) {
/*     */           
/* 333 */           this.value = "";
/*     */         } else {
/* 335 */           this.value = value;
/*     */         } 
/*     */       }
/*     */       
/*     */       QName getName() {
/* 340 */         return this.name;
/*     */       }
/*     */       
/*     */       String getValue() {
/* 344 */         return this.value;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       String getLocalName() {
/* 351 */         if (isNamespaceDeclaration()) {
/* 352 */           if (this.name.getLocalPart().equals("")) {
/* 353 */             return "xmlns";
/*     */           }
/* 355 */           return "xmlns:" + this.name.getLocalPart();
/*     */         } 
/* 357 */         return this.name.getLocalPart();
/*     */       }
/*     */       
/*     */       boolean isNamespaceDeclaration() {
/* 361 */         return (this.name.getNamespaceURI() == "http://www.w3.org/2000/xmlns/");
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
/*     */     public AttributesImpl(XMLStreamReader reader) {
/* 373 */       if (reader == null) {
/*     */ 
/*     */ 
/*     */         
/* 377 */         this.atInfos = new AttributeInfo[0];
/*     */       }
/*     */       else {
/*     */         
/* 381 */         int index = 0;
/* 382 */         int namespaceCount = reader.getNamespaceCount();
/* 383 */         int attributeCount = reader.getAttributeCount();
/* 384 */         this.atInfos = new AttributeInfo[namespaceCount + attributeCount]; int i;
/* 385 */         for (i = 0; i < namespaceCount; i++) {
/* 386 */           String namespacePrefix = reader.getNamespacePrefix(i);
/*     */ 
/*     */           
/* 389 */           if (namespacePrefix == null) {
/* 390 */             namespacePrefix = "";
/*     */           }
/* 392 */           this.atInfos[index++] = new AttributeInfo(new QName("http://www.w3.org/2000/xmlns/", namespacePrefix, "xmlns"), reader
/*     */ 
/*     */ 
/*     */               
/* 396 */               .getNamespaceURI(i));
/*     */         } 
/* 398 */         for (i = 0; i < attributeCount; i++) {
/* 399 */           this.atInfos[index++] = new AttributeInfo(reader
/* 400 */               .getAttributeName(i), reader
/* 401 */               .getAttributeValue(i));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getLength() {
/* 407 */       return this.atInfos.length;
/*     */     }
/*     */     
/*     */     public String getLocalName(int index) {
/* 411 */       if (index >= 0 && index < this.atInfos.length) {
/* 412 */         return this.atInfos[index].getLocalName();
/*     */       }
/* 414 */       return null;
/*     */     }
/*     */     
/*     */     public QName getName(int index) {
/* 418 */       if (index >= 0 && index < this.atInfos.length) {
/* 419 */         return this.atInfos[index].getName();
/*     */       }
/* 421 */       return null;
/*     */     }
/*     */     
/*     */     public String getPrefix(int index) {
/* 425 */       if (index >= 0 && index < this.atInfos.length) {
/* 426 */         return this.atInfos[index].getName().getPrefix();
/*     */       }
/* 428 */       return null;
/*     */     }
/*     */     
/*     */     public String getURI(int index) {
/* 432 */       if (index >= 0 && index < this.atInfos.length) {
/* 433 */         return this.atInfos[index].getName().getNamespaceURI();
/*     */       }
/* 435 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(int index) {
/* 439 */       if (index >= 0 && index < this.atInfos.length) {
/* 440 */         return this.atInfos[index].getValue();
/*     */       }
/* 442 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(QName name) {
/* 446 */       int index = getIndex(name);
/* 447 */       if (index != -1) {
/* 448 */         return this.atInfos[index].getValue();
/*     */       }
/* 450 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(String localName) {
/* 454 */       int index = getIndex(localName);
/* 455 */       if (index != -1) {
/* 456 */         return this.atInfos[index].getValue();
/*     */       }
/* 458 */       return null;
/*     */     }
/*     */     
/*     */     public String getValue(String uri, String localName) {
/* 462 */       int index = getIndex(uri, localName);
/* 463 */       if (index != -1) {
/* 464 */         return this.atInfos[index].getValue();
/*     */       }
/* 466 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isNamespaceDeclaration(int index) {
/* 470 */       if (index >= 0 && index < this.atInfos.length) {
/* 471 */         return this.atInfos[index].isNamespaceDeclaration();
/*     */       }
/* 473 */       return false;
/*     */     }
/*     */     
/*     */     public int getIndex(QName name) {
/* 477 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 478 */         if (this.atInfos[i].getName().equals(name)) {
/* 479 */           return i;
/*     */         }
/*     */       } 
/* 482 */       return -1;
/*     */     }
/*     */     
/*     */     public int getIndex(String localName) {
/* 486 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 487 */         if (this.atInfos[i].getName().getLocalPart().equals(localName)) {
/* 488 */           return i;
/*     */         }
/*     */       } 
/* 491 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIndex(String uri, String localName) {
/* 496 */       for (int i = 0; i < this.atInfos.length; i++) {
/* 497 */         QName qName = this.atInfos[i].getName();
/* 498 */         if (qName.getNamespaceURI().equals(uri) && qName
/* 499 */           .getLocalPart().equals(localName))
/*     */         {
/* 501 */           return i;
/*     */         }
/*     */       } 
/* 504 */       return -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/streaming/XMLStreamReaderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */