/*     */ package com.sun.xml.internal.ws.util.xml;
/*     */ 
/*     */ import com.sun.xml.internal.org.jvnet.staxex.Base64Data;
/*     */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamReaderEx;
/*     */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamWriterEx;
/*     */ import com.sun.xml.internal.ws.streaming.MtomStreamWriter;
/*     */ import java.io.IOException;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamReaderToXMLStreamWriter
/*     */ {
/*     */   private static final int BUF_SIZE = 4096;
/*     */   protected XMLStreamReader in;
/*     */   protected XMLStreamWriter out;
/*     */   private char[] buf;
/*     */   boolean optimizeBase64Data = false;
/*     */   AttachmentMarshaller mtomAttachmentMarshaller;
/*     */   
/*     */   public void bridge(XMLStreamReader in, XMLStreamWriter out) throws XMLStreamException {
/*  73 */     assert in != null && out != null;
/*  74 */     this.in = in;
/*  75 */     this.out = out;
/*     */     
/*  77 */     this.optimizeBase64Data = in instanceof XMLStreamReaderEx;
/*     */     
/*  79 */     if (out instanceof XMLStreamWriterEx && out instanceof MtomStreamWriter) {
/*  80 */       this.mtomAttachmentMarshaller = ((MtomStreamWriter)out).getAttachmentMarshaller();
/*     */     }
/*     */     
/*  83 */     int depth = 0;
/*     */     
/*  85 */     this.buf = new char[4096];
/*     */ 
/*     */     
/*  88 */     int event = in.getEventType();
/*  89 */     if (event == 7)
/*     */     {
/*  91 */       while (!in.isStartElement()) {
/*  92 */         event = in.next();
/*  93 */         if (event == 5) {
/*  94 */           handleComment();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  99 */     if (event != 1) {
/* 100 */       throw new IllegalStateException("The current event is not START_ELEMENT\n but " + event);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 106 */       switch (event) {
/*     */         case 1:
/* 108 */           depth++;
/* 109 */           handleStartElement();
/*     */           break;
/*     */         case 2:
/* 112 */           handleEndElement();
/* 113 */           depth--;
/* 114 */           if (depth == 0)
/*     */             return; 
/*     */           break;
/*     */         case 4:
/* 118 */           handleCharacters();
/*     */           break;
/*     */         case 9:
/* 121 */           handleEntityReference();
/*     */           break;
/*     */         case 3:
/* 124 */           handlePI();
/*     */           break;
/*     */         case 5:
/* 127 */           handleComment();
/*     */           break;
/*     */         case 11:
/* 130 */           handleDTD();
/*     */           break;
/*     */         case 12:
/* 133 */           handleCDATA();
/*     */           break;
/*     */         case 6:
/* 136 */           handleSpace();
/*     */           break;
/*     */         case 8:
/* 139 */           throw new XMLStreamException("Malformed XML at depth=" + depth + ", Reached EOF. Event=" + event);
/*     */         default:
/* 141 */           throw new XMLStreamException("Cannot process event: " + event);
/*     */       } 
/*     */       
/* 144 */       event = in.next();
/* 145 */     } while (depth != 0);
/*     */   }
/*     */   
/*     */   protected void handlePI() throws XMLStreamException {
/* 149 */     this.out.writeProcessingInstruction(this.in
/* 150 */         .getPITarget(), this.in
/* 151 */         .getPIData());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleCharacters() throws XMLStreamException {
/* 157 */     CharSequence c = null;
/*     */     
/* 159 */     if (this.optimizeBase64Data) {
/* 160 */       c = ((XMLStreamReaderEx)this.in).getPCDATA();
/*     */     }
/*     */     
/* 163 */     if (c != null && c instanceof Base64Data) {
/* 164 */       if (this.mtomAttachmentMarshaller != null) {
/* 165 */         Base64Data b64d = (Base64Data)c;
/* 166 */         ((XMLStreamWriterEx)this.out).writeBinary(b64d.getDataHandler());
/*     */       } else {
/*     */         try {
/* 169 */           ((Base64Data)c).writeTo(this.out);
/* 170 */         } catch (IOException e) {
/* 171 */           throw new XMLStreamException(e);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 175 */       for (int start = 0, read = this.buf.length; read == this.buf.length; start += this.buf.length) {
/* 176 */         read = this.in.getTextCharacters(start, this.buf, 0, this.buf.length);
/* 177 */         this.out.writeCharacters(this.buf, 0, read);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleEndElement() throws XMLStreamException {
/* 183 */     this.out.writeEndElement();
/*     */   }
/*     */   
/*     */   protected void handleStartElement() throws XMLStreamException {
/* 187 */     String nsUri = this.in.getNamespaceURI();
/* 188 */     if (nsUri == null) {
/* 189 */       this.out.writeStartElement(this.in.getLocalName());
/*     */     } else {
/* 191 */       this.out.writeStartElement(
/* 192 */           fixNull(this.in.getPrefix()), this.in
/* 193 */           .getLocalName(), nsUri);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 198 */     int nsCount = this.in.getNamespaceCount();
/* 199 */     for (int i = 0; i < nsCount; i++) {
/* 200 */       this.out.writeNamespace(this.in
/* 201 */           .getNamespacePrefix(i), 
/* 202 */           fixNull(this.in.getNamespaceURI(i)));
/*     */     }
/*     */ 
/*     */     
/* 206 */     int attCount = this.in.getAttributeCount();
/* 207 */     for (int j = 0; j < attCount; j++) {
/* 208 */       handleAttribute(j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleAttribute(int i) throws XMLStreamException {
/* 219 */     String nsUri = this.in.getAttributeNamespace(i);
/* 220 */     String prefix = this.in.getAttributePrefix(i);
/* 221 */     if (fixNull(nsUri).equals("http://www.w3.org/2000/xmlns/")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 226 */     if (nsUri == null || prefix == null || prefix.equals("")) {
/* 227 */       this.out.writeAttribute(this.in
/* 228 */           .getAttributeLocalName(i), this.in
/* 229 */           .getAttributeValue(i));
/*     */     } else {
/*     */       
/* 232 */       this.out.writeAttribute(prefix, nsUri, this.in
/*     */ 
/*     */           
/* 235 */           .getAttributeLocalName(i), this.in
/* 236 */           .getAttributeValue(i));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleDTD() throws XMLStreamException {
/* 242 */     this.out.writeDTD(this.in.getText());
/*     */   }
/*     */   
/*     */   protected void handleComment() throws XMLStreamException {
/* 246 */     this.out.writeComment(this.in.getText());
/*     */   }
/*     */   
/*     */   protected void handleEntityReference() throws XMLStreamException {
/* 250 */     this.out.writeEntityRef(this.in.getText());
/*     */   }
/*     */   
/*     */   protected void handleSpace() throws XMLStreamException {
/* 254 */     handleCharacters();
/*     */   }
/*     */   
/*     */   protected void handleCDATA() throws XMLStreamException {
/* 258 */     this.out.writeCData(this.in.getText());
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/* 262 */     if (s == null) return ""; 
/* 263 */     return s;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/XMLStreamReaderToXMLStreamWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */