/*     */ package com.sun.xml.internal.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.internal.bind.marshaller.NoEscapeHandler;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.lang.reflect.Constructor;
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
/*     */ public class XMLStreamWriterOutput
/*     */   extends XmlOutputAbstractImpl
/*     */ {
/*     */   private final XMLStreamWriter out;
/*     */   private final CharacterEscapeHandler escapeHandler;
/*     */   private final XmlStreamOutWriterAdapter writerWrapper;
/*     */   
/*     */   public static XmlOutput create(XMLStreamWriter out, JAXBContextImpl context, CharacterEscapeHandler escapeHandler) {
/*  60 */     Class<?> writerClass = out.getClass();
/*  61 */     if (writerClass == FI_STAX_WRITER_CLASS) {
/*     */       try {
/*  63 */         return FI_OUTPUT_CTOR.newInstance(new Object[] { out, context });
/*  64 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/*  67 */     if (STAXEX_WRITER_CLASS != null && STAXEX_WRITER_CLASS.isAssignableFrom(writerClass)) {
/*     */       try {
/*  69 */         return STAXEX_OUTPUT_CTOR.newInstance(new Object[] { out });
/*  70 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/*  74 */     CharacterEscapeHandler xmlStreamEscapeHandler = (escapeHandler != null) ? escapeHandler : (CharacterEscapeHandler)NoEscapeHandler.theInstance;
/*     */ 
/*     */ 
/*     */     
/*  78 */     return new XMLStreamWriterOutput(out, xmlStreamEscapeHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   protected final char[] buf = new char[256];
/*     */   
/*     */   protected XMLStreamWriterOutput(XMLStreamWriter out, CharacterEscapeHandler escapeHandler) {
/*  91 */     this.out = out;
/*  92 */     this.escapeHandler = escapeHandler;
/*  93 */     this.writerWrapper = new XmlStreamOutWriterAdapter(out);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/*  99 */     super.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/* 100 */     if (!fragment) {
/* 101 */       this.out.writeStartDocument();
/*     */     }
/*     */   }
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/* 106 */     if (!fragment) {
/* 107 */       this.out.writeEndDocument();
/* 108 */       this.out.flush();
/*     */     } 
/* 110 */     super.endDocument(fragment);
/*     */   }
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException, XMLStreamException {
/* 114 */     this.out.writeStartElement(this.nsContext
/* 115 */         .getPrefix(prefix), localName, this.nsContext
/*     */         
/* 117 */         .getNamespaceURI(prefix));
/*     */     
/* 119 */     NamespaceContextImpl.Element nse = this.nsContext.getCurrent();
/* 120 */     if (nse.count() > 0)
/* 121 */       for (int i = nse.count() - 1; i >= 0; i--) {
/* 122 */         String uri = nse.getNsUri(i);
/* 123 */         if (uri.length() != 0 || nse.getBase() != 1)
/*     */         {
/* 125 */           this.out.writeNamespace(nse.getPrefix(i), uri);
/*     */         }
/*     */       }  
/*     */   }
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException, XMLStreamException {
/* 131 */     if (prefix == -1) {
/* 132 */       this.out.writeAttribute(localName, value);
/*     */     } else {
/* 134 */       this.out.writeAttribute(this.nsContext
/* 135 */           .getPrefix(prefix), this.nsContext
/* 136 */           .getNamespaceURI(prefix), localName, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endStartTag() throws IOException, SAXException {}
/*     */ 
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException, SAXException, XMLStreamException {
/* 145 */     this.out.writeEndElement();
/*     */   }
/*     */   
/*     */   public void text(String value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 149 */     if (needsSeparatingWhitespace)
/* 150 */       this.out.writeCharacters(" "); 
/* 151 */     this.escapeHandler.escape(value.toCharArray(), 0, value.length(), false, this.writerWrapper);
/*     */   }
/*     */   
/*     */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 155 */     if (needsSeparatingWhitespace) {
/* 156 */       this.out.writeCharacters(" ");
/*     */     }
/* 158 */     int len = value.length();
/* 159 */     if (len < this.buf.length) {
/* 160 */       value.writeTo(this.buf, 0);
/* 161 */       this.out.writeCharacters(this.buf, 0, len);
/*     */     } else {
/* 163 */       this.out.writeCharacters(value.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   private static final Class FI_STAX_WRITER_CLASS = initFIStAXWriterClass();
/* 171 */   private static final Constructor<? extends XmlOutput> FI_OUTPUT_CTOR = initFastInfosetOutputClass();
/*     */   
/*     */   private static Class initFIStAXWriterClass() {
/*     */     try {
/* 175 */       Class<?> llfisw = Class.forName("com.sun.xml.internal.org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter");
/* 176 */       Class<?> sds = Class.forName("com.sun.xml.internal.fastinfoset.stax.StAXDocumentSerializer");
/*     */       
/* 178 */       if (llfisw.isAssignableFrom(sds)) {
/* 179 */         return sds;
/*     */       }
/* 181 */       return null;
/* 182 */     } catch (Throwable e) {
/* 183 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<? extends XmlOutput> initFastInfosetOutputClass() {
/*     */     try {
/* 189 */       if (FI_STAX_WRITER_CLASS == null)
/* 190 */         return null; 
/* 191 */       Class<?> c = Class.forName("com.sun.xml.internal.bind.v2.runtime.output.FastInfosetStreamWriterOutput");
/* 192 */       return (Constructor)c.getConstructor(new Class[] { FI_STAX_WRITER_CLASS, JAXBContextImpl.class });
/* 193 */     } catch (Throwable e) {
/* 194 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   private static final Class STAXEX_WRITER_CLASS = initStAXExWriterClass();
/* 202 */   private static final Constructor<? extends XmlOutput> STAXEX_OUTPUT_CTOR = initStAXExOutputClass();
/*     */   
/*     */   private static Class initStAXExWriterClass() {
/*     */     try {
/* 206 */       return Class.forName("com.sun.xml.internal.org.jvnet.staxex.XMLStreamWriterEx");
/* 207 */     } catch (Throwable e) {
/* 208 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<? extends XmlOutput> initStAXExOutputClass() {
/*     */     try {
/* 214 */       Class<?> c = Class.forName("com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput");
/* 215 */       return (Constructor)c.getConstructor(new Class[] { STAXEX_WRITER_CLASS });
/* 216 */     } catch (Throwable e) {
/* 217 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class XmlStreamOutWriterAdapter
/*     */     extends Writer {
/*     */     private final XMLStreamWriter writer;
/*     */     
/*     */     private XmlStreamOutWriterAdapter(XMLStreamWriter writer) {
/* 226 */       this.writer = writer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(char[] cbuf, int off, int len) throws IOException {
/*     */       try {
/* 232 */         this.writer.writeCharacters(cbuf, off, len);
/* 233 */       } catch (XMLStreamException e) {
/* 234 */         throw new IOException("Error writing XML stream", e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeEntityRef(String entityReference) throws XMLStreamException {
/* 239 */       this.writer.writeEntityRef(entityReference);
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() throws IOException {
/*     */       try {
/* 245 */         this.writer.flush();
/* 246 */       } catch (XMLStreamException e) {
/* 247 */         throw new IOException("Error flushing XML stream", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/*     */       try {
/* 254 */         this.writer.close();
/* 255 */       } catch (XMLStreamException e) {
/* 256 */         throw new IOException("Error closing XML stream", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/output/XMLStreamWriterOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */