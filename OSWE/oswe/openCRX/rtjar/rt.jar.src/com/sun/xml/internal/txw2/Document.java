/*     */ package com.sun.xml.internal.txw2;
/*     */ 
/*     */ import com.sun.xml.internal.txw2.output.XmlSerializer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Document
/*     */ {
/*     */   private final XmlSerializer out;
/*     */   private boolean started = false;
/*  56 */   private Content current = null;
/*     */   
/*  58 */   private final Map<Class, DatatypeWriter> datatypeWriters = (Map)new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private int iota = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private final NamespaceSupport inscopeNamespace = new NamespaceSupport();
/*     */ 
/*     */   
/*     */   private NamespaceDecl activeNamespaces;
/*     */ 
/*     */   
/*     */   private final ContentVisitor visitor;
/*     */   
/*     */   private final StringBuilder prefixSeed;
/*     */   
/*     */   private int prefixIota;
/*     */   
/*     */   static final char MAGIC = '\000';
/*     */ 
/*     */   
/*     */   void flush() {
/*  84 */     this.out.flush();
/*     */   }
/*     */   
/*     */   void setFirstContent(Content c) {
/*  88 */     assert this.current == null;
/*  89 */     this.current = new StartDocument();
/*  90 */     this.current.setNext(this, c);
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
/*     */   public void addDatatypeWriter(DatatypeWriter<?> dw) {
/* 105 */     this.datatypeWriters.put(dw.getType(), dw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void run() {
/*     */     while (true) {
/* 113 */       Content next = this.current.getNext();
/* 114 */       if (next == null || !next.isReadyToCommit())
/*     */         return; 
/* 116 */       next.accept(this.visitor);
/* 117 */       next.written();
/* 118 */       this.current = next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void writeValue(Object obj, NamespaceResolver nsResolver, StringBuilder buf) {
/* 129 */     if (obj == null) {
/* 130 */       throw new IllegalArgumentException("argument contains null");
/*     */     }
/* 132 */     if (obj instanceof Object[]) {
/* 133 */       for (Object o : (Object[])obj)
/* 134 */         writeValue(o, nsResolver, buf); 
/*     */       return;
/*     */     } 
/* 137 */     if (obj instanceof Iterable) {
/* 138 */       for (Object o : obj) {
/* 139 */         writeValue(o, nsResolver, buf);
/*     */       }
/*     */       return;
/*     */     } 
/* 143 */     if (buf.length() > 0) {
/* 144 */       buf.append(' ');
/*     */     }
/* 146 */     Class<?> c = obj.getClass();
/* 147 */     while (c != null) {
/* 148 */       DatatypeWriter<Object> dw = this.datatypeWriters.get(c);
/* 149 */       if (dw != null) {
/* 150 */         dw.print(obj, nsResolver, buf);
/*     */         return;
/*     */       } 
/* 153 */       c = c.getSuperclass();
/*     */     } 
/*     */ 
/*     */     
/* 157 */     buf.append(obj);
/*     */   }
/*     */   
/*     */   Document(XmlSerializer out) {
/* 161 */     this.visitor = new ContentVisitor()
/*     */       {
/*     */         
/*     */         public void onStartDocument()
/*     */         {
/* 166 */           throw new IllegalStateException();
/*     */         }
/*     */         
/*     */         public void onEndDocument() {
/* 170 */           Document.this.out.endDocument();
/*     */         }
/*     */         
/*     */         public void onEndTag() {
/* 174 */           Document.this.out.endTag();
/* 175 */           Document.this.inscopeNamespace.popContext();
/* 176 */           Document.this.activeNamespaces = null;
/*     */         }
/*     */         
/*     */         public void onPcdata(StringBuilder buffer) {
/* 180 */           if (Document.this.activeNamespaces != null)
/* 181 */             buffer = Document.this.fixPrefix(buffer); 
/* 182 */           Document.this.out.text(buffer);
/*     */         }
/*     */         
/*     */         public void onCdata(StringBuilder buffer) {
/* 186 */           if (Document.this.activeNamespaces != null)
/* 187 */             buffer = Document.this.fixPrefix(buffer); 
/* 188 */           Document.this.out.cdata(buffer);
/*     */         }
/*     */         
/*     */         public void onComment(StringBuilder buffer) {
/* 192 */           if (Document.this.activeNamespaces != null)
/* 193 */             buffer = Document.this.fixPrefix(buffer); 
/* 194 */           Document.this.out.comment(buffer);
/*     */         }
/*     */         
/*     */         public void onStartTag(String nsUri, String localName, Attribute attributes, NamespaceDecl namespaces) {
/* 198 */           assert nsUri != null;
/* 199 */           assert localName != null;
/*     */           
/* 201 */           Document.this.activeNamespaces = namespaces;
/*     */           
/* 203 */           if (!Document.this.started) {
/* 204 */             Document.this.started = true;
/* 205 */             Document.this.out.startDocument();
/*     */           } 
/*     */           
/* 208 */           Document.this.inscopeNamespace.pushContext();
/*     */           
/*     */           NamespaceDecl ns;
/* 211 */           for (ns = namespaces; ns != null; ns = ns.next) {
/* 212 */             ns.declared = false;
/*     */             
/* 214 */             if (ns.prefix != null) {
/* 215 */               String uri = Document.this.inscopeNamespace.getURI(ns.prefix);
/* 216 */               if (uri == null || !uri.equals(ns.uri)) {
/*     */ 
/*     */ 
/*     */                 
/* 220 */                 Document.this.inscopeNamespace.declarePrefix(ns.prefix, ns.uri);
/* 221 */                 ns.declared = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 227 */           for (ns = namespaces; ns != null; ns = ns.next) {
/* 228 */             if (ns.prefix == null) {
/* 229 */               if (Document.this.inscopeNamespace.getURI("").equals(ns.uri)) {
/* 230 */                 ns.prefix = "";
/*     */               } else {
/* 232 */                 String p = Document.this.inscopeNamespace.getPrefix(ns.uri);
/* 233 */                 if (p == null) {
/*     */                   
/* 235 */                   while (Document.this.inscopeNamespace.getURI(p = Document.this.newPrefix()) != null);
/*     */                   
/* 237 */                   ns.declared = true;
/* 238 */                   Document.this.inscopeNamespace.declarePrefix(p, ns.uri);
/*     */                 } 
/* 240 */                 ns.prefix = p;
/*     */               } 
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 246 */           assert namespaces.uri.equals(nsUri);
/* 247 */           assert namespaces.prefix != null : "a prefix must have been all allocated";
/* 248 */           Document.this.out.beginStartTag(nsUri, localName, namespaces.prefix);
/*     */ 
/*     */           
/* 251 */           for (ns = namespaces; ns != null; ns = ns.next) {
/* 252 */             if (ns.declared) {
/* 253 */               Document.this.out.writeXmlns(ns.prefix, ns.uri);
/*     */             }
/*     */           } 
/*     */           
/* 257 */           for (Attribute a = attributes; a != null; a = a.next) {
/*     */             String prefix;
/* 259 */             if (a.nsUri.length() == 0) { prefix = ""; }
/* 260 */             else { prefix = Document.this.inscopeNamespace.getPrefix(a.nsUri); }
/* 261 */              Document.this.out.writeAttribute(a.nsUri, a.localName, prefix, Document.this.fixPrefix(a.value));
/*     */           } 
/*     */           
/* 264 */           Document.this.out.endStartTag(nsUri, localName, namespaces.prefix);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     this.prefixSeed = new StringBuilder("ns");
/*     */     
/* 273 */     this.prefixIota = 0;
/*     */     this.out = out;
/*     */     for (DatatypeWriter<?> dw : DatatypeWriter.BUILTIN)
/*     */       this.datatypeWriters.put(dw.getType(), dw); 
/*     */   }
/*     */   private String newPrefix() {
/* 279 */     this.prefixSeed.setLength(2);
/* 280 */     this.prefixSeed.append(++this.prefixIota);
/* 281 */     return this.prefixSeed.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuilder fixPrefix(StringBuilder buf) {
/* 292 */     assert this.activeNamespaces != null;
/*     */ 
/*     */     
/* 295 */     int len = buf.length(); int i;
/* 296 */     for (i = 0; i < len && 
/* 297 */       buf.charAt(i) != '\000'; i++);
/*     */ 
/*     */ 
/*     */     
/* 301 */     if (i == len) {
/* 302 */       return buf;
/*     */     }
/* 304 */     while (i < len) {
/* 305 */       char uriIdx = buf.charAt(i + 1);
/* 306 */       NamespaceDecl ns = this.activeNamespaces;
/* 307 */       while (ns != null && ns.uniqueId != uriIdx)
/* 308 */         ns = ns.next; 
/* 309 */       if (ns == null) {
/* 310 */         throw new IllegalStateException("Unexpected use of prefixes " + buf);
/*     */       }
/* 312 */       int length = 2;
/* 313 */       String prefix = ns.prefix;
/* 314 */       if (prefix.length() == 0) {
/* 315 */         if (buf.length() <= i + 2 || buf.charAt(i + 2) != ':')
/* 316 */           throw new IllegalStateException("Unexpected use of prefixes " + buf); 
/* 317 */         length = 3;
/*     */       } 
/*     */       
/* 320 */       buf.replace(i, i + length, prefix);
/* 321 */       len += prefix.length() - length;
/*     */       
/* 323 */       while (i < len && buf.charAt(i) != '\000') {
/* 324 */         i++;
/*     */       }
/*     */     } 
/* 327 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   char assignNewId() {
/* 336 */     return (char)this.iota++;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/Document.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */