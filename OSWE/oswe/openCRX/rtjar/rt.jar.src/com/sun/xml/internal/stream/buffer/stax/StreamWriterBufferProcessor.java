/*     */ package com.sun.xml.internal.stream.buffer.stax;
/*     */ 
/*     */ import com.sun.xml.internal.org.jvnet.staxex.Base64Data;
/*     */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamWriterEx;
/*     */ import com.sun.xml.internal.stream.buffer.AbstractProcessor;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamWriterBufferProcessor
/*     */   extends AbstractProcessor
/*     */ {
/*     */   public StreamWriterBufferProcessor() {}
/*     */   
/*     */   public StreamWriterBufferProcessor(XMLStreamBuffer buffer) {
/*  62 */     setXMLStreamBuffer(buffer, buffer.isFragment());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamWriterBufferProcessor(XMLStreamBuffer buffer, boolean produceFragmentEvent) {
/*  71 */     setXMLStreamBuffer(buffer, produceFragmentEvent);
/*     */   }
/*     */   
/*     */   public final void process(XMLStreamBuffer buffer, XMLStreamWriter writer) throws XMLStreamException {
/*  75 */     setXMLStreamBuffer(buffer, buffer.isFragment());
/*  76 */     process(writer);
/*     */   }
/*     */   
/*     */   public void process(XMLStreamWriter writer) throws XMLStreamException {
/*  80 */     if (this._fragmentMode) {
/*  81 */       writeFragment(writer);
/*     */     } else {
/*  83 */       write(writer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer) {
/*  92 */     setBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer, boolean produceFragmentEvent) {
/* 101 */     setBuffer(buffer, produceFragmentEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/*     */     int item;
/* 112 */     if (!this._fragmentMode) {
/* 113 */       if (this._treeCount > 1)
/* 114 */         throw new IllegalStateException("forest cannot be written as a full infoset"); 
/* 115 */       writer.writeStartDocument();
/*     */     }  while (true) {
/*     */       int length; char[] ch; int start;
/*     */       String comment;
/* 119 */       item = getEIIState(peekStructure());
/* 120 */       writer.flush();
/*     */       
/* 122 */       switch (item) {
/*     */         case 1:
/* 124 */           readStructure();
/*     */           continue;
/*     */         case 3:
/*     */         case 4:
/*     */         case 5:
/*     */         case 6:
/* 130 */           writeFragment(writer);
/*     */           continue;
/*     */         case 12:
/* 133 */           readStructure();
/* 134 */           length = readStructure();
/* 135 */           start = readContentCharactersBuffer(length);
/* 136 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 137 */           writer.writeComment(comment);
/*     */           continue;
/*     */         
/*     */         case 13:
/* 141 */           readStructure();
/* 142 */           length = readStructure16();
/* 143 */           start = readContentCharactersBuffer(length);
/* 144 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 145 */           writer.writeComment(comment);
/*     */           continue;
/*     */         
/*     */         case 14:
/* 149 */           readStructure();
/* 150 */           ch = readContentCharactersCopy();
/* 151 */           writer.writeComment(new String(ch));
/*     */           continue;
/*     */         
/*     */         case 16:
/* 155 */           readStructure();
/* 156 */           writer.writeProcessingInstruction(readStructureString(), readStructureString());
/*     */           continue;
/*     */         case 17:
/* 159 */           readStructure();
/* 160 */           writer.writeEndDocument(); return;
/*     */       }  break;
/*     */     } 
/* 163 */     throw new XMLStreamException("Invalid State " + item);
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
/*     */   public void writeFragment(XMLStreamWriter writer) throws XMLStreamException {
/* 178 */     if (writer instanceof XMLStreamWriterEx) {
/* 179 */       writeFragmentEx((XMLStreamWriterEx)writer);
/*     */     } else {
/* 181 */       writeFragmentNoEx(writer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeFragmentEx(XMLStreamWriterEx writer) throws XMLStreamException {
/* 186 */     int depth = 0;
/*     */     
/* 188 */     int item = getEIIState(peekStructure());
/* 189 */     if (item == 1)
/* 190 */       readStructure();  do {
/*     */       String str1, prefix, uri, localName; int i; char[] arrayOfChar1; String s; CharSequence c; int length; char[] ch; String str4, str3, str2;
/*     */       int start;
/*     */       String str6, str5, comment;
/* 194 */       item = readEiiState();
/*     */       
/* 196 */       switch (item) {
/*     */         case 1:
/* 198 */           throw new AssertionError();
/*     */         case 3:
/* 200 */           depth++;
/* 201 */           str1 = readStructureString();
/* 202 */           str4 = readStructureString();
/* 203 */           str6 = getPrefixFromQName(readStructureString());
/* 204 */           writer.writeStartElement(str6, str4, str1);
/* 205 */           writeAttributes((XMLStreamWriter)writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 4:
/* 209 */           depth++;
/* 210 */           prefix = readStructureString();
/* 211 */           str3 = readStructureString();
/* 212 */           str5 = readStructureString();
/* 213 */           writer.writeStartElement(prefix, str5, str3);
/* 214 */           writeAttributes((XMLStreamWriter)writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 5:
/* 218 */           depth++;
/* 219 */           uri = readStructureString();
/* 220 */           str2 = readStructureString();
/* 221 */           writer.writeStartElement("", str2, uri);
/* 222 */           writeAttributes((XMLStreamWriter)writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 6:
/* 226 */           depth++;
/* 227 */           localName = readStructureString();
/* 228 */           writer.writeStartElement(localName);
/* 229 */           writeAttributes((XMLStreamWriter)writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 7:
/* 233 */           i = readStructure();
/* 234 */           start = readContentCharactersBuffer(i);
/* 235 */           writer.writeCharacters(this._contentCharactersBuffer, start, i);
/*     */           break;
/*     */         
/*     */         case 8:
/* 239 */           i = readStructure16();
/* 240 */           start = readContentCharactersBuffer(i);
/* 241 */           writer.writeCharacters(this._contentCharactersBuffer, start, i);
/*     */           break;
/*     */         
/*     */         case 9:
/* 245 */           arrayOfChar1 = readContentCharactersCopy();
/* 246 */           writer.writeCharacters(arrayOfChar1, 0, arrayOfChar1.length);
/*     */           break;
/*     */         
/*     */         case 10:
/* 250 */           s = readContentString();
/* 251 */           writer.writeCharacters(s);
/*     */           break;
/*     */         
/*     */         case 11:
/* 255 */           c = (CharSequence)readContentObject();
/* 256 */           writer.writePCDATA(c);
/*     */           break;
/*     */         
/*     */         case 12:
/* 260 */           length = readStructure();
/* 261 */           start = readContentCharactersBuffer(length);
/* 262 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 263 */           writer.writeComment(comment);
/*     */           break;
/*     */         
/*     */         case 13:
/* 267 */           length = readStructure16();
/* 268 */           start = readContentCharactersBuffer(length);
/* 269 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 270 */           writer.writeComment(comment);
/*     */           break;
/*     */         
/*     */         case 14:
/* 274 */           ch = readContentCharactersCopy();
/* 275 */           writer.writeComment(new String(ch));
/*     */           break;
/*     */         
/*     */         case 16:
/* 279 */           writer.writeProcessingInstruction(readStructureString(), readStructureString());
/*     */           break;
/*     */         case 17:
/* 282 */           writer.writeEndElement();
/* 283 */           depth--;
/* 284 */           if (depth == 0)
/* 285 */             this._treeCount--; 
/*     */           break;
/*     */         default:
/* 288 */           throw new XMLStreamException("Invalid State " + item);
/*     */       } 
/* 290 */     } while (depth > 0 || this._treeCount > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFragmentNoEx(XMLStreamWriter writer) throws XMLStreamException {
/* 295 */     int depth = 0;
/*     */     
/* 297 */     int item = getEIIState(peekStructure());
/* 298 */     if (item == 1)
/* 299 */       readStructure();  do {
/*     */       String str1, prefix, uri, localName; int i; char[] arrayOfChar1; String s; CharSequence c; int length; char[] ch; String str4, str3, str2; int start;
/*     */       String str6, str5, comment;
/* 302 */       item = readEiiState();
/*     */       
/* 304 */       switch (item) {
/*     */         case 1:
/* 306 */           throw new AssertionError();
/*     */         case 3:
/* 308 */           depth++;
/* 309 */           str1 = readStructureString();
/* 310 */           str4 = readStructureString();
/* 311 */           str6 = getPrefixFromQName(readStructureString());
/* 312 */           writer.writeStartElement(str6, str4, str1);
/* 313 */           writeAttributes(writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 4:
/* 317 */           depth++;
/* 318 */           prefix = readStructureString();
/* 319 */           str3 = readStructureString();
/* 320 */           str5 = readStructureString();
/* 321 */           writer.writeStartElement(prefix, str5, str3);
/* 322 */           writeAttributes(writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 5:
/* 326 */           depth++;
/* 327 */           uri = readStructureString();
/* 328 */           str2 = readStructureString();
/* 329 */           writer.writeStartElement("", str2, uri);
/* 330 */           writeAttributes(writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 6:
/* 334 */           depth++;
/* 335 */           localName = readStructureString();
/* 336 */           writer.writeStartElement(localName);
/* 337 */           writeAttributes(writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 7:
/* 341 */           i = readStructure();
/* 342 */           start = readContentCharactersBuffer(i);
/* 343 */           writer.writeCharacters(this._contentCharactersBuffer, start, i);
/*     */           break;
/*     */         
/*     */         case 8:
/* 347 */           i = readStructure16();
/* 348 */           start = readContentCharactersBuffer(i);
/* 349 */           writer.writeCharacters(this._contentCharactersBuffer, start, i);
/*     */           break;
/*     */         
/*     */         case 9:
/* 353 */           arrayOfChar1 = readContentCharactersCopy();
/* 354 */           writer.writeCharacters(arrayOfChar1, 0, arrayOfChar1.length);
/*     */           break;
/*     */         
/*     */         case 10:
/* 358 */           s = readContentString();
/* 359 */           writer.writeCharacters(s);
/*     */           break;
/*     */         
/*     */         case 11:
/* 363 */           c = (CharSequence)readContentObject();
/* 364 */           if (c instanceof Base64Data) {
/*     */             try {
/* 366 */               Base64Data bd = (Base64Data)c;
/* 367 */               bd.writeTo(writer);
/* 368 */             } catch (IOException e) {
/* 369 */               throw new XMLStreamException(e);
/*     */             }  break;
/*     */           } 
/* 372 */           writer.writeCharacters(c.toString());
/*     */           break;
/*     */ 
/*     */         
/*     */         case 12:
/* 377 */           length = readStructure();
/* 378 */           start = readContentCharactersBuffer(length);
/* 379 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 380 */           writer.writeComment(comment);
/*     */           break;
/*     */         
/*     */         case 13:
/* 384 */           length = readStructure16();
/* 385 */           start = readContentCharactersBuffer(length);
/* 386 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 387 */           writer.writeComment(comment);
/*     */           break;
/*     */         
/*     */         case 14:
/* 391 */           ch = readContentCharactersCopy();
/* 392 */           writer.writeComment(new String(ch));
/*     */           break;
/*     */         
/*     */         case 16:
/* 396 */           writer.writeProcessingInstruction(readStructureString(), readStructureString());
/*     */           break;
/*     */         case 17:
/* 399 */           writer.writeEndElement();
/* 400 */           depth--;
/* 401 */           if (depth == 0)
/* 402 */             this._treeCount--; 
/*     */           break;
/*     */         default:
/* 405 */           throw new XMLStreamException("Invalid State " + item);
/*     */       } 
/* 407 */     } while (depth > 0 || this._treeCount > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isInscope(int depth) {
/* 412 */     return (this._buffer.getInscopeNamespaces().size() > 0 && depth == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeAttributes(XMLStreamWriter writer, boolean inscope) throws XMLStreamException {
/* 420 */     Set<String> prefixSet = inscope ? new HashSet<>() : Collections.<String>emptySet();
/* 421 */     int item = peekStructure();
/* 422 */     if ((item & 0xF0) == 64)
/*     */     {
/*     */       
/* 425 */       item = writeNamespaceAttributes(item, writer, inscope, prefixSet);
/*     */     }
/* 427 */     if (inscope) {
/* 428 */       writeInscopeNamespaces(writer, prefixSet);
/*     */     }
/* 430 */     if ((item & 0xF0) == 48) {
/* 431 */       writeAttributes(item, writer);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/* 436 */     if (s == null) return ""; 
/* 437 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeInscopeNamespaces(XMLStreamWriter writer, Set<String> prefixSet) throws XMLStreamException {
/* 444 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)this._buffer.getInscopeNamespaces().entrySet()) {
/* 445 */       String key = fixNull(e.getKey());
/*     */       
/* 447 */       if (!prefixSet.contains(key))
/* 448 */         writer.writeNamespace(key, e.getValue()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int writeNamespaceAttributes(int item, XMLStreamWriter writer, boolean collectPrefixes, Set<String> prefixSet) throws XMLStreamException {
/*     */     do {
/*     */       String prefix;
/* 455 */       switch (getNIIState(item)) {
/*     */         
/*     */         case 1:
/* 458 */           writer.writeDefaultNamespace("");
/* 459 */           if (collectPrefixes) {
/* 460 */             prefixSet.add("");
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 466 */           prefix = readStructureString();
/* 467 */           writer.writeNamespace(prefix, "");
/* 468 */           if (collectPrefixes) {
/* 469 */             prefixSet.add(prefix);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 474 */           prefix = readStructureString();
/* 475 */           writer.writeNamespace(prefix, readStructureString());
/* 476 */           if (collectPrefixes) {
/* 477 */             prefixSet.add(prefix);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 4:
/* 482 */           writer.writeDefaultNamespace(readStructureString());
/* 483 */           if (collectPrefixes) {
/* 484 */             prefixSet.add("");
/*     */           }
/*     */           break;
/*     */       } 
/* 488 */       readStructure();
/*     */       
/* 490 */       item = peekStructure();
/* 491 */     } while ((item & 0xF0) == 64);
/*     */     
/* 493 */     return item;
/*     */   }
/*     */   private void writeAttributes(int item, XMLStreamWriter writer) throws XMLStreamException {
/*     */     do {
/*     */       String uri, localName, prefix;
/* 498 */       switch (getAIIState(item)) {
/*     */         case 1:
/* 500 */           uri = readStructureString();
/* 501 */           localName = readStructureString();
/* 502 */           prefix = getPrefixFromQName(readStructureString());
/* 503 */           writer.writeAttribute(prefix, uri, localName, readContentString());
/*     */           break;
/*     */         
/*     */         case 2:
/* 507 */           writer.writeAttribute(readStructureString(), readStructureString(), 
/* 508 */               readStructureString(), readContentString());
/*     */           break;
/*     */         case 3:
/* 511 */           writer.writeAttribute(readStructureString(), readStructureString(), readContentString());
/*     */           break;
/*     */         case 4:
/* 514 */           writer.writeAttribute(readStructureString(), readContentString());
/*     */           break;
/*     */       } 
/*     */       
/* 518 */       readStructureString();
/*     */       
/* 520 */       readStructure();
/*     */       
/* 522 */       item = peekStructure();
/* 523 */     } while ((item & 0xF0) == 48);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/buffer/stax/StreamWriterBufferProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */