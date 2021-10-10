/*     */ package com.sun.xml.internal.stream.buffer.stax;
/*     */ 
/*     */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamReaderEx;
/*     */ import com.sun.xml.internal.stream.buffer.MutableXMLStreamBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamReaderBufferCreator
/*     */   extends StreamBufferCreator
/*     */ {
/*     */   private int _eventType;
/*     */   private boolean _storeInScopeNamespacesOnElementFragment;
/*     */   private Map<String, Integer> _inScopePrefixes;
/*     */   
/*     */   public StreamReaderBufferCreator() {}
/*     */   
/*     */   public StreamReaderBufferCreator(MutableXMLStreamBuffer buffer) {
/*  63 */     setBuffer(buffer);
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
/*     */   public MutableXMLStreamBuffer create(XMLStreamReader reader) throws XMLStreamException {
/*  85 */     if (this._buffer == null) {
/*  86 */       createBuffer();
/*     */     }
/*  88 */     store(reader);
/*     */     
/*  90 */     return getXMLStreamBuffer();
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
/*     */   public MutableXMLStreamBuffer createElementFragment(XMLStreamReader reader, boolean storeInScopeNamespaces) throws XMLStreamException {
/* 111 */     if (this._buffer == null) {
/* 112 */       createBuffer();
/*     */     }
/*     */     
/* 115 */     if (!reader.hasNext()) {
/* 116 */       return this._buffer;
/*     */     }
/*     */     
/* 119 */     this._storeInScopeNamespacesOnElementFragment = storeInScopeNamespaces;
/*     */     
/* 121 */     this._eventType = reader.getEventType();
/* 122 */     if (this._eventType != 1) {
/*     */       do {
/* 124 */         this._eventType = reader.next();
/* 125 */       } while (this._eventType != 1 && this._eventType != 8);
/*     */     }
/*     */     
/* 128 */     if (storeInScopeNamespaces) {
/* 129 */       this._inScopePrefixes = new HashMap<>();
/*     */     }
/*     */     
/* 132 */     storeElementAndChildren(reader);
/*     */     
/* 134 */     return getXMLStreamBuffer();
/*     */   }
/*     */   
/*     */   private void store(XMLStreamReader reader) throws XMLStreamException {
/* 138 */     if (!reader.hasNext()) {
/*     */       return;
/*     */     }
/*     */     
/* 142 */     this._eventType = reader.getEventType();
/* 143 */     switch (this._eventType) {
/*     */       case 7:
/* 145 */         storeDocumentAndChildren(reader);
/*     */         break;
/*     */       case 1:
/* 148 */         storeElementAndChildren(reader);
/*     */         break;
/*     */       default:
/* 151 */         throw new XMLStreamException("XMLStreamReader not positioned at a document or element");
/*     */     } 
/*     */     
/* 154 */     increaseTreeCount();
/*     */   }
/*     */   
/*     */   private void storeDocumentAndChildren(XMLStreamReader reader) throws XMLStreamException {
/* 158 */     storeStructure(16);
/*     */     
/* 160 */     this._eventType = reader.next();
/* 161 */     while (this._eventType != 8) {
/* 162 */       switch (this._eventType) {
/*     */         case 1:
/* 164 */           storeElementAndChildren(reader);
/*     */           continue;
/*     */         case 5:
/* 167 */           storeComment(reader);
/*     */           break;
/*     */         case 3:
/* 170 */           storeProcessingInstruction(reader);
/*     */           break;
/*     */       } 
/* 173 */       this._eventType = reader.next();
/*     */     } 
/*     */     
/* 176 */     storeStructure(144);
/*     */   }
/*     */   
/*     */   private void storeElementAndChildren(XMLStreamReader reader) throws XMLStreamException {
/* 180 */     if (reader instanceof XMLStreamReaderEx) {
/* 181 */       storeElementAndChildrenEx((XMLStreamReaderEx)reader);
/*     */     } else {
/* 183 */       storeElementAndChildrenNoEx(reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void storeElementAndChildrenEx(XMLStreamReaderEx reader) throws XMLStreamException {
/* 188 */     int depth = 1;
/* 189 */     if (this._storeInScopeNamespacesOnElementFragment) {
/* 190 */       storeElementWithInScopeNamespaces((XMLStreamReader)reader);
/*     */     } else {
/* 192 */       storeElement((XMLStreamReader)reader);
/*     */     } 
/*     */     
/* 195 */     while (depth > 0) {
/* 196 */       CharSequence c; this._eventType = reader.next();
/* 197 */       switch (this._eventType) {
/*     */         case 1:
/* 199 */           depth++;
/* 200 */           storeElement((XMLStreamReader)reader);
/*     */         
/*     */         case 2:
/* 203 */           depth--;
/* 204 */           storeStructure(144);
/*     */         
/*     */         case 13:
/* 207 */           storeNamespaceAttributes((XMLStreamReader)reader);
/*     */         
/*     */         case 10:
/* 210 */           storeAttributes((XMLStreamReader)reader);
/*     */         
/*     */         case 4:
/*     */         case 6:
/*     */         case 12:
/* 215 */           c = reader.getPCDATA();
/* 216 */           if (c instanceof com.sun.xml.internal.org.jvnet.staxex.Base64Data) {
/* 217 */             storeStructure(92);
/*     */             
/* 219 */             storeContentObject(c); continue;
/*     */           } 
/* 221 */           storeContentCharacters(80, reader
/* 222 */               .getTextCharacters(), reader.getTextStart(), reader
/* 223 */               .getTextLength());
/*     */ 
/*     */ 
/*     */         
/*     */         case 5:
/* 228 */           storeComment((XMLStreamReader)reader);
/*     */         
/*     */         case 3:
/* 231 */           storeProcessingInstruction((XMLStreamReader)reader);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 240 */     this._eventType = reader.next();
/*     */   }
/*     */   
/*     */   private void storeElementAndChildrenNoEx(XMLStreamReader reader) throws XMLStreamException {
/* 244 */     int depth = 1;
/* 245 */     if (this._storeInScopeNamespacesOnElementFragment) {
/* 246 */       storeElementWithInScopeNamespaces(reader);
/*     */     } else {
/* 248 */       storeElement(reader);
/*     */     } 
/*     */     
/* 251 */     while (depth > 0) {
/* 252 */       this._eventType = reader.next();
/* 253 */       switch (this._eventType) {
/*     */         case 1:
/* 255 */           depth++;
/* 256 */           storeElement(reader);
/*     */         
/*     */         case 2:
/* 259 */           depth--;
/* 260 */           storeStructure(144);
/*     */         
/*     */         case 13:
/* 263 */           storeNamespaceAttributes(reader);
/*     */         
/*     */         case 10:
/* 266 */           storeAttributes(reader);
/*     */         
/*     */         case 4:
/*     */         case 6:
/*     */         case 12:
/* 271 */           storeContentCharacters(80, reader
/* 272 */               .getTextCharacters(), reader.getTextStart(), reader
/* 273 */               .getTextLength());
/*     */ 
/*     */         
/*     */         case 5:
/* 277 */           storeComment(reader);
/*     */         
/*     */         case 3:
/* 280 */           storeProcessingInstruction(reader);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 289 */     this._eventType = reader.next();
/*     */   }
/*     */   
/*     */   private void storeElementWithInScopeNamespaces(XMLStreamReader reader) {
/* 293 */     storeQualifiedName(32, reader
/* 294 */         .getPrefix(), reader.getNamespaceURI(), reader.getLocalName());
/*     */     
/* 296 */     if (reader.getNamespaceCount() > 0) {
/* 297 */       storeNamespaceAttributes(reader);
/*     */     }
/*     */     
/* 300 */     if (reader.getAttributeCount() > 0) {
/* 301 */       storeAttributes(reader);
/*     */     }
/*     */   }
/*     */   
/*     */   private void storeElement(XMLStreamReader reader) {
/* 306 */     storeQualifiedName(32, reader
/* 307 */         .getPrefix(), reader.getNamespaceURI(), reader.getLocalName());
/*     */     
/* 309 */     if (reader.getNamespaceCount() > 0) {
/* 310 */       storeNamespaceAttributes(reader);
/*     */     }
/*     */     
/* 313 */     if (reader.getAttributeCount() > 0) {
/* 314 */       storeAttributes(reader);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeElement(String nsURI, String localName, String prefix, String[] ns) {
/* 336 */     storeQualifiedName(32, prefix, nsURI, localName);
/* 337 */     storeNamespaceAttributes(ns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeEndElement() {
/* 348 */     storeStructure(144);
/*     */   }
/*     */   
/*     */   private void storeNamespaceAttributes(XMLStreamReader reader) {
/* 352 */     int count = reader.getNamespaceCount();
/* 353 */     for (int i = 0; i < count; i++) {
/* 354 */       storeNamespaceAttribute(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void storeNamespaceAttributes(String[] ns) {
/* 362 */     for (int i = 0; i < ns.length; i += 2) {
/* 363 */       storeNamespaceAttribute(ns[i], ns[i + 1]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void storeAttributes(XMLStreamReader reader) {
/* 368 */     int count = reader.getAttributeCount();
/* 369 */     for (int i = 0; i < count; i++) {
/* 370 */       storeAttribute(reader.getAttributePrefix(i), reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader
/* 371 */           .getAttributeType(i), reader.getAttributeValue(i));
/*     */     }
/*     */   }
/*     */   
/*     */   private void storeComment(XMLStreamReader reader) {
/* 376 */     storeContentCharacters(96, reader
/* 377 */         .getTextCharacters(), reader.getTextStart(), reader.getTextLength());
/*     */   }
/*     */   
/*     */   private void storeProcessingInstruction(XMLStreamReader reader) {
/* 381 */     storeProcessingInstruction(reader.getPITarget(), reader.getPIData());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/buffer/stax/StreamReaderBufferCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */