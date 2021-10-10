/*     */ package com.sun.xml.internal.stream.buffer;
/*     */ 
/*     */ import com.sun.xml.internal.stream.buffer.sax.SAXBufferCreator;
/*     */ import com.sun.xml.internal.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.internal.stream.buffer.stax.StreamWriterBufferCreator;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.SAXException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MutableXMLStreamBuffer
/*     */   extends XMLStreamBuffer
/*     */ {
/*     */   public static final int DEFAULT_ARRAY_SIZE = 512;
/*     */   
/*     */   public MutableXMLStreamBuffer() {
/*  72 */     this(512);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSystemId(String systemId) {
/*  80 */     this.systemId = systemId;
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
/*     */   public MutableXMLStreamBuffer(int size) {
/*  93 */     this._structure = (FragmentedArray)new FragmentedArray<>(new byte[size]);
/*  94 */     this._structureStrings = (FragmentedArray)new FragmentedArray<>(new String[size]);
/*  95 */     this._contentCharactersBuffer = (FragmentedArray)new FragmentedArray<>(new char[4096]);
/*  96 */     this._contentObjects = new FragmentedArray(new Object[size]);
/*     */ 
/*     */ 
/*     */     
/* 100 */     ((byte[])this._structure.getArray())[0] = -112;
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
/*     */   public void createFromXMLStreamReader(XMLStreamReader reader) throws XMLStreamException {
/* 117 */     reset();
/* 118 */     StreamReaderBufferCreator c = new StreamReaderBufferCreator(this);
/* 119 */     c.create(reader);
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
/*     */   public XMLStreamWriter createFromXMLStreamWriter() {
/* 133 */     reset();
/* 134 */     return (XMLStreamWriter)new StreamWriterBufferCreator(this);
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
/*     */   public SAXBufferCreator createFromSAXBufferCreator() {
/* 150 */     reset();
/* 151 */     SAXBufferCreator c = new SAXBufferCreator();
/* 152 */     c.setBuffer(this);
/* 153 */     return c;
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
/*     */   public void createFromXMLReader(XMLReader reader, InputStream in) throws SAXException, IOException {
/* 172 */     createFromXMLReader(reader, in, (String)null);
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
/*     */   public void createFromXMLReader(XMLReader reader, InputStream in, String systemId) throws SAXException, IOException {
/* 193 */     reset();
/* 194 */     SAXBufferCreator c = new SAXBufferCreator(this);
/*     */     
/* 196 */     reader.setContentHandler((ContentHandler)c);
/* 197 */     reader.setDTDHandler((DTDHandler)c);
/* 198 */     reader.setProperty("http://xml.org/sax/properties/lexical-handler", c);
/*     */     
/* 200 */     c.create(reader, in, systemId);
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
/*     */   public void reset() {
/* 215 */     this._structurePtr = this._structureStringsPtr = this._contentCharactersBufferPtr = this._contentObjectsPtr = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     ((byte[])this._structure.getArray())[0] = -112;
/*     */ 
/*     */     
/* 225 */     this._contentObjects.setNext(null);
/* 226 */     Object[] o = this._contentObjects.getArray();
/* 227 */     for (int i = 0; i < o.length && 
/* 228 */       o[i] != null; i++) {
/* 229 */       o[i] = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     this.treeCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setHasInternedStrings(boolean hasInternedStrings) {
/* 245 */     this._hasInternedStrings = hasInternedStrings;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/buffer/MutableXMLStreamBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */