/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TagInfoset
/*     */ {
/*     */   @NotNull
/*     */   public final String[] ns;
/*     */   @NotNull
/*     */   public final AttributesImpl atts;
/*     */   @Nullable
/*     */   public final String prefix;
/*     */   @Nullable
/*     */   public final String nsUri;
/*     */   @NotNull
/*     */   public final String localName;
/*     */   @Nullable
/*     */   private String qname;
/*     */   
/*     */   public TagInfoset(String nsUri, String localName, String prefix, AttributesImpl atts, String... ns) {
/*  95 */     this.nsUri = nsUri;
/*  96 */     this.prefix = prefix;
/*  97 */     this.localName = localName;
/*  98 */     this.atts = atts;
/*  99 */     this.ns = ns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagInfoset(XMLStreamReader reader) {
/* 107 */     this.prefix = reader.getPrefix();
/* 108 */     this.nsUri = reader.getNamespaceURI();
/* 109 */     this.localName = reader.getLocalName();
/*     */     
/* 111 */     int nsc = reader.getNamespaceCount();
/* 112 */     if (nsc > 0) {
/* 113 */       this.ns = new String[nsc * 2];
/* 114 */       for (int i = 0; i < nsc; i++) {
/* 115 */         this.ns[i * 2] = fixNull(reader.getNamespacePrefix(i));
/* 116 */         this.ns[i * 2 + 1] = fixNull(reader.getNamespaceURI(i));
/*     */       } 
/*     */     } else {
/* 119 */       this.ns = EMPTY_ARRAY;
/*     */     } 
/*     */     
/* 122 */     int ac = reader.getAttributeCount();
/* 123 */     if (ac > 0) {
/* 124 */       this.atts = new AttributesImpl();
/* 125 */       StringBuilder sb = new StringBuilder();
/* 126 */       for (int i = 0; i < ac; i++) {
/* 127 */         String qname; sb.setLength(0);
/* 128 */         String prefix = reader.getAttributePrefix(i);
/* 129 */         String localName = reader.getAttributeLocalName(i);
/*     */ 
/*     */         
/* 132 */         if (prefix != null && prefix.length() != 0) {
/* 133 */           sb.append(prefix);
/* 134 */           sb.append(":");
/* 135 */           sb.append(localName);
/* 136 */           qname = sb.toString();
/*     */         } else {
/* 138 */           qname = localName;
/*     */         } 
/*     */         
/* 141 */         this.atts.addAttribute(
/* 142 */             fixNull(reader.getAttributeNamespace(i)), localName, qname, reader
/*     */ 
/*     */             
/* 145 */             .getAttributeType(i), reader
/* 146 */             .getAttributeValue(i));
/*     */       } 
/*     */     } else {
/* 149 */       this.atts = EMPTY_ATTRIBUTES;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStart(ContentHandler contentHandler) throws SAXException {
/* 157 */     for (int i = 0; i < this.ns.length; i += 2)
/* 158 */       contentHandler.startPrefixMapping(fixNull(this.ns[i]), fixNull(this.ns[i + 1])); 
/* 159 */     contentHandler.startElement(fixNull(this.nsUri), this.localName, getQName(), this.atts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEnd(ContentHandler contentHandler) throws SAXException {
/* 166 */     contentHandler.endElement(fixNull(this.nsUri), this.localName, getQName());
/* 167 */     for (int i = this.ns.length - 2; i >= 0; i -= 2) {
/* 168 */       contentHandler.endPrefixMapping(fixNull(this.ns[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStart(XMLStreamWriter w) throws XMLStreamException {
/* 177 */     if (this.prefix == null) {
/* 178 */       if (this.nsUri == null) {
/* 179 */         w.writeStartElement(this.localName);
/*     */       }
/*     */       else {
/*     */         
/* 183 */         w.writeStartElement("", this.localName, this.nsUri);
/*     */       } 
/*     */     } else {
/* 186 */       w.writeStartElement(this.prefix, this.localName, this.nsUri);
/*     */     } 
/*     */     int i;
/* 189 */     for (i = 0; i < this.ns.length; i += 2) {
/* 190 */       w.writeNamespace(this.ns[i], this.ns[i + 1]);
/*     */     }
/*     */     
/* 193 */     for (i = 0; i < this.atts.getLength(); i++) {
/* 194 */       String nsUri = this.atts.getURI(i);
/* 195 */       if (nsUri == null || nsUri.length() == 0) {
/* 196 */         w.writeAttribute(this.atts.getLocalName(i), this.atts.getValue(i));
/*     */       } else {
/* 198 */         String rawName = this.atts.getQName(i);
/* 199 */         String prefix = rawName.substring(0, rawName.indexOf(':'));
/* 200 */         w.writeAttribute(prefix, nsUri, this.atts.getLocalName(i), this.atts.getValue(i));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getQName() {
/* 206 */     if (this.qname != null) return this.qname;
/*     */     
/* 208 */     StringBuilder sb = new StringBuilder();
/* 209 */     if (this.prefix != null) {
/* 210 */       sb.append(this.prefix);
/* 211 */       sb.append(':');
/* 212 */       sb.append(this.localName);
/* 213 */       this.qname = sb.toString();
/*     */     } else {
/* 215 */       this.qname = this.localName;
/*     */     } 
/* 217 */     return this.qname;
/*     */   }
/*     */   private static String fixNull(String s) {
/* 220 */     if (s == null) return ""; 
/* 221 */     return s;
/*     */   }
/*     */   
/* 224 */   private static final String[] EMPTY_ARRAY = new String[0];
/* 225 */   private static final AttributesImpl EMPTY_ATTRIBUTES = new AttributesImpl();
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 228 */     int size = this.ns.length / 2;
/* 229 */     for (int i = 0; i < size; i++) {
/* 230 */       String p = this.ns[i * 2];
/* 231 */       String n = this.ns[i * 2 + 1];
/* 232 */       if (prefix.equals(p)) return n; 
/*     */     } 
/* 234 */     return null;
/*     */   }
/*     */   
/*     */   public String getPrefix(String namespaceURI) {
/* 238 */     int size = this.ns.length / 2;
/* 239 */     for (int i = 0; i < size; i++) {
/* 240 */       String p = this.ns[i * 2];
/* 241 */       String n = this.ns[i * 2 + 1];
/* 242 */       if (namespaceURI.equals(n)) return p; 
/*     */     } 
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   public List<String> allPrefixes(String namespaceURI) {
/* 248 */     int size = this.ns.length / 2;
/* 249 */     List<String> l = new ArrayList<>();
/* 250 */     for (int i = 0; i < size; i++) {
/* 251 */       String p = this.ns[i * 2];
/* 252 */       String n = this.ns[i * 2 + 1];
/* 253 */       if (namespaceURI.equals(n)) l.add(p); 
/*     */     } 
/* 255 */     return l;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/TagInfoset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */