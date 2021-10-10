/*     */ package com.sun.xml.internal.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.internal.bind.marshaller.NoEscapeHandler;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Base64Data;
/*     */ import com.sun.xml.internal.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import com.sun.xml.internal.org.jvnet.fastinfoset.VocabularyApplicationData;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public final class FastInfosetStreamWriterOutput
/*     */   extends XMLStreamWriterOutput
/*     */ {
/*     */   private final StAXDocumentSerializer fiout;
/*     */   private final Encoded[] localNames;
/*     */   private final TablesPerJAXBContext tables;
/*     */   
/*     */   static final class TablesPerJAXBContext
/*     */   {
/*     */     final int[] elementIndexes;
/*     */     final int[] elementIndexPrefixes;
/*     */     final int[] attributeIndexes;
/*     */     final int[] localNameIndexes;
/*     */     int indexOffset;
/*     */     int maxIndex;
/*     */     boolean requiresClear;
/*     */     
/*     */     TablesPerJAXBContext(JAXBContextImpl context, int initialIndexOffset) {
/* 109 */       this.elementIndexes = new int[context.getNumberOfElementNames()];
/* 110 */       this.elementIndexPrefixes = new int[context.getNumberOfElementNames()];
/* 111 */       this.attributeIndexes = new int[context.getNumberOfAttributeNames()];
/* 112 */       this.localNameIndexes = new int[context.getNumberOfLocalNames()];
/*     */       
/* 114 */       this.indexOffset = 1;
/* 115 */       this.maxIndex = initialIndexOffset + this.elementIndexes.length + this.attributeIndexes.length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void requireClearTables() {
/* 122 */       this.requiresClear = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clearOrResetTables(int intialIndexOffset) {
/* 132 */       if (this.requiresClear) {
/* 133 */         this.requiresClear = false;
/*     */ 
/*     */         
/* 136 */         this.indexOffset += this.maxIndex;
/*     */         
/* 138 */         this.maxIndex = intialIndexOffset + this.elementIndexes.length + this.attributeIndexes.length;
/*     */ 
/*     */         
/* 141 */         if (this.indexOffset + this.maxIndex < 0) {
/* 142 */           clearAll();
/*     */         }
/*     */       } else {
/*     */         
/* 146 */         this.maxIndex = intialIndexOffset + this.elementIndexes.length + this.attributeIndexes.length;
/*     */ 
/*     */         
/* 149 */         if (this.indexOffset + this.maxIndex < 0) {
/* 150 */           resetAll();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     private void clearAll() {
/* 156 */       clear(this.elementIndexes);
/* 157 */       clear(this.attributeIndexes);
/* 158 */       clear(this.localNameIndexes);
/* 159 */       this.indexOffset = 1;
/*     */     }
/*     */     
/*     */     private void clear(int[] array) {
/* 163 */       for (int i = 0; i < array.length; i++) {
/* 164 */         array[i] = 0;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void incrementMaxIndexValue() {
/* 175 */       this.maxIndex++;
/*     */ 
/*     */       
/* 178 */       if (this.indexOffset + this.maxIndex < 0) {
/* 179 */         resetAll();
/*     */       }
/*     */     }
/*     */     
/*     */     private void resetAll() {
/* 184 */       clear(this.elementIndexes);
/* 185 */       clear(this.attributeIndexes);
/* 186 */       clear(this.localNameIndexes);
/* 187 */       this.indexOffset = 1;
/*     */     }
/*     */     
/*     */     private void reset(int[] array) {
/* 191 */       for (int i = 0; i < array.length; i++) {
/* 192 */         if (array[i] > this.indexOffset) {
/* 193 */           array[i] = array[i] - this.indexOffset + 1;
/*     */         } else {
/* 195 */           array[i] = 0;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class AppData
/*     */     implements VocabularyApplicationData
/*     */   {
/* 209 */     final Map<JAXBContext, FastInfosetStreamWriterOutput.TablesPerJAXBContext> contexts = new WeakHashMap<>();
/*     */     
/* 211 */     final Collection<FastInfosetStreamWriterOutput.TablesPerJAXBContext> collectionOfContexts = this.contexts.values();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 217 */       for (FastInfosetStreamWriterOutput.TablesPerJAXBContext c : this.collectionOfContexts) {
/* 218 */         c.requireClearTables();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public FastInfosetStreamWriterOutput(StAXDocumentSerializer out, JAXBContextImpl context) {
/* 224 */     super((XMLStreamWriter)out, (CharacterEscapeHandler)NoEscapeHandler.theInstance);
/*     */     
/* 226 */     this.fiout = out;
/* 227 */     this.localNames = context.getUTF8NameTable();
/*     */     
/* 229 */     VocabularyApplicationData vocabAppData = this.fiout.getVocabularyApplicationData();
/* 230 */     AppData appData = null;
/* 231 */     if (vocabAppData == null || !(vocabAppData instanceof AppData)) {
/* 232 */       appData = new AppData();
/* 233 */       this.fiout.setVocabularyApplicationData(appData);
/*     */     } else {
/* 235 */       appData = (AppData)vocabAppData;
/*     */     } 
/*     */     
/* 238 */     TablesPerJAXBContext tablesPerContext = appData.contexts.get(context);
/* 239 */     if (tablesPerContext != null) {
/* 240 */       this.tables = tablesPerContext;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       this.tables.clearOrResetTables(out.getLocalNameIndex());
/*     */     } else {
/* 247 */       this.tables = new TablesPerJAXBContext(context, out.getLocalNameIndex());
/* 248 */       appData.contexts.put(context, this.tables);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/* 256 */     super.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/*     */     
/* 258 */     if (fragment) {
/* 259 */       this.fiout.initiateLowLevelWriting();
/*     */     }
/*     */   }
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/* 264 */     super.endDocument(fragment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginStartTag(Name name) throws IOException {
/* 269 */     this.fiout.writeLowLevelTerminationAndMark();
/*     */     
/* 271 */     if (this.nsContext.getCurrent().count() == 0) {
/* 272 */       int qNameIndex = this.tables.elementIndexes[name.qNameIndex] - this.tables.indexOffset;
/* 273 */       int prefixIndex = this.nsUriIndex2prefixIndex[name.nsUriIndex];
/*     */       
/* 275 */       if (qNameIndex >= 0 && this.tables.elementIndexPrefixes[name.qNameIndex] == prefixIndex) {
/*     */         
/* 277 */         this.fiout.writeLowLevelStartElementIndexed(0, qNameIndex);
/*     */       } else {
/* 279 */         this.tables.elementIndexes[name.qNameIndex] = this.fiout.getNextElementIndex() + this.tables.indexOffset;
/* 280 */         this.tables.elementIndexPrefixes[name.qNameIndex] = prefixIndex;
/* 281 */         writeLiteral(60, name, this.nsContext
/*     */             
/* 283 */             .getPrefix(prefixIndex), this.nsContext
/* 284 */             .getNamespaceURI(prefixIndex));
/*     */       } 
/*     */     } else {
/* 287 */       beginStartTagWithNamespaces(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void beginStartTagWithNamespaces(Name name) throws IOException {
/* 292 */     NamespaceContextImpl.Element nse = this.nsContext.getCurrent();
/*     */     
/* 294 */     this.fiout.writeLowLevelStartNamespaces();
/* 295 */     for (int i = nse.count() - 1; i >= 0; i--) {
/* 296 */       String uri = nse.getNsUri(i);
/* 297 */       if (uri.length() != 0 || nse.getBase() != 1)
/*     */       {
/* 299 */         this.fiout.writeLowLevelNamespace(nse.getPrefix(i), uri); } 
/*     */     } 
/* 301 */     this.fiout.writeLowLevelEndNamespaces();
/*     */     
/* 303 */     int qNameIndex = this.tables.elementIndexes[name.qNameIndex] - this.tables.indexOffset;
/* 304 */     int prefixIndex = this.nsUriIndex2prefixIndex[name.nsUriIndex];
/*     */     
/* 306 */     if (qNameIndex >= 0 && this.tables.elementIndexPrefixes[name.qNameIndex] == prefixIndex) {
/*     */       
/* 308 */       this.fiout.writeLowLevelStartElementIndexed(0, qNameIndex);
/*     */     } else {
/* 310 */       this.tables.elementIndexes[name.qNameIndex] = this.fiout.getNextElementIndex() + this.tables.indexOffset;
/* 311 */       this.tables.elementIndexPrefixes[name.qNameIndex] = prefixIndex;
/* 312 */       writeLiteral(60, name, this.nsContext
/*     */           
/* 314 */           .getPrefix(prefixIndex), this.nsContext
/* 315 */           .getNamespaceURI(prefixIndex));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void attribute(Name name, String value) throws IOException {
/* 321 */     this.fiout.writeLowLevelStartAttributes();
/*     */     
/* 323 */     int qNameIndex = this.tables.attributeIndexes[name.qNameIndex] - this.tables.indexOffset;
/* 324 */     if (qNameIndex >= 0) {
/* 325 */       this.fiout.writeLowLevelAttributeIndexed(qNameIndex);
/*     */     } else {
/* 327 */       this.tables.attributeIndexes[name.qNameIndex] = this.fiout.getNextAttributeIndex() + this.tables.indexOffset;
/*     */       
/* 329 */       int namespaceURIId = name.nsUriIndex;
/* 330 */       if (namespaceURIId == -1) {
/* 331 */         writeLiteral(120, name, "", "");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 336 */         int prefix = this.nsUriIndex2prefixIndex[namespaceURIId];
/* 337 */         writeLiteral(120, name, this.nsContext
/*     */             
/* 339 */             .getPrefix(prefix), this.nsContext
/* 340 */             .getNamespaceURI(prefix));
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     this.fiout.writeLowLevelAttributeValue(value);
/*     */   }
/*     */   
/*     */   private void writeLiteral(int type, Name name, String prefix, String namespaceURI) throws IOException {
/* 348 */     int localNameIndex = this.tables.localNameIndexes[name.localNameIndex] - this.tables.indexOffset;
/*     */     
/* 350 */     if (localNameIndex < 0) {
/* 351 */       this.tables.localNameIndexes[name.localNameIndex] = this.fiout.getNextLocalNameIndex() + this.tables.indexOffset;
/*     */       
/* 353 */       this.fiout.writeLowLevelStartNameLiteral(type, prefix, (this.localNames[name.localNameIndex]).buf, namespaceURI);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 359 */       this.fiout.writeLowLevelStartNameLiteral(type, prefix, localNameIndex, namespaceURI);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endStartTag() throws IOException {
/* 369 */     this.fiout.writeLowLevelEndStartElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTag(Name name) throws IOException {
/* 374 */     this.fiout.writeLowLevelEndElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException {
/* 379 */     this.fiout.writeLowLevelEndElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws IOException {
/* 385 */     if (needsSeparatingWhitespace) {
/* 386 */       this.fiout.writeLowLevelText(" ");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 391 */     if (!(value instanceof Base64Data)) {
/* 392 */       int len = value.length();
/* 393 */       if (len < this.buf.length) {
/* 394 */         value.writeTo(this.buf, 0);
/* 395 */         this.fiout.writeLowLevelText(this.buf, len);
/*     */       } else {
/* 397 */         this.fiout.writeLowLevelText(value.toString());
/*     */       } 
/*     */     } else {
/* 400 */       Base64Data dataValue = (Base64Data)value;
/*     */       
/* 402 */       this.fiout.writeLowLevelOctets(dataValue.get(), dataValue.getDataLen());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void text(String value, boolean needsSeparatingWhitespace) throws IOException {
/* 409 */     if (needsSeparatingWhitespace) {
/* 410 */       this.fiout.writeLowLevelText(" ");
/*     */     }
/* 412 */     this.fiout.writeLowLevelText(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException {
/* 418 */     this.fiout.writeLowLevelTerminationAndMark();
/*     */     
/* 420 */     int type = 0;
/* 421 */     if (this.nsContext.getCurrent().count() > 0) {
/* 422 */       NamespaceContextImpl.Element nse = this.nsContext.getCurrent();
/*     */       
/* 424 */       this.fiout.writeLowLevelStartNamespaces();
/* 425 */       for (int i = nse.count() - 1; i >= 0; i--) {
/* 426 */         String uri = nse.getNsUri(i);
/* 427 */         if (uri.length() != 0 || nse.getBase() != 1)
/*     */         {
/* 429 */           this.fiout.writeLowLevelNamespace(nse.getPrefix(i), uri); } 
/*     */       } 
/* 431 */       this.fiout.writeLowLevelEndNamespaces();
/*     */       
/* 433 */       type = 0;
/*     */     } 
/*     */     
/* 436 */     boolean isIndexed = this.fiout.writeLowLevelStartElement(type, this.nsContext
/*     */         
/* 438 */         .getPrefix(prefix), localName, this.nsContext
/*     */         
/* 440 */         .getNamespaceURI(prefix));
/*     */     
/* 442 */     if (!isIndexed)
/* 443 */       this.tables.incrementMaxIndexValue(); 
/*     */   }
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException {
/*     */     boolean isIndexed;
/* 448 */     this.fiout.writeLowLevelStartAttributes();
/*     */ 
/*     */     
/* 451 */     if (prefix == -1) {
/* 452 */       isIndexed = this.fiout.writeLowLevelAttribute("", "", localName);
/*     */     } else {
/* 454 */       isIndexed = this.fiout.writeLowLevelAttribute(this.nsContext
/* 455 */           .getPrefix(prefix), this.nsContext
/* 456 */           .getNamespaceURI(prefix), localName);
/*     */     } 
/*     */     
/* 459 */     if (!isIndexed) {
/* 460 */       this.tables.incrementMaxIndexValue();
/*     */     }
/* 462 */     this.fiout.writeLowLevelAttributeValue(value);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/output/FastInfosetStreamWriterOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */