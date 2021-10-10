/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOMCache;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.Translet;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
/*     */ import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
/*     */ import java.io.File;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.InputSource;
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
/*     */ public final class DocumentCache
/*     */   implements DOMCache
/*     */ {
/*     */   private int _size;
/*     */   private Map<String, CachedDocument> _references;
/*     */   private String[] _URIs;
/*     */   private int _count;
/*     */   private int _current;
/*     */   private SAXParser _parser;
/*     */   private XMLReader _reader;
/*     */   private XSLTCDTMManager _dtmManager;
/*     */   private static final int REFRESH_INTERVAL = 1000;
/*     */   
/*     */   public final class CachedDocument
/*     */   {
/*     */     private long _firstReferenced;
/*     */     private long _lastReferenced;
/*     */     private long _accessCount;
/*     */     private long _lastModified;
/*     */     private long _lastChecked;
/*     */     private long _buildTime;
/*  81 */     private DOMEnhancedForDTM _dom = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CachedDocument(String uri) {
/*  88 */       long stamp = System.currentTimeMillis();
/*  89 */       this._firstReferenced = stamp;
/*  90 */       this._lastReferenced = stamp;
/*  91 */       this._accessCount = 0L;
/*  92 */       loadDocument(uri);
/*     */       
/*  94 */       this._buildTime = System.currentTimeMillis() - stamp;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void loadDocument(String uri) {
/*     */       try {
/* 103 */         long stamp = System.currentTimeMillis();
/* 104 */         this._dom = (DOMEnhancedForDTM)DocumentCache.this._dtmManager.getDTM(new SAXSource(DocumentCache.this
/* 105 */               ._reader, new InputSource(uri)), false, null, true, false);
/*     */         
/* 107 */         this._dom.setDocumentURI(uri);
/*     */ 
/*     */ 
/*     */         
/* 111 */         long thisTime = System.currentTimeMillis() - stamp;
/* 112 */         if (this._buildTime > 0L) {
/* 113 */           this._buildTime = this._buildTime + thisTime >>> 1L;
/*     */         } else {
/* 115 */           this._buildTime = thisTime;
/*     */         } 
/* 117 */       } catch (Exception e) {
/* 118 */         this._dom = null;
/*     */       } 
/*     */     }
/*     */     public DOM getDocument() {
/* 122 */       return this._dom;
/*     */     } public long getFirstReferenced() {
/* 124 */       return this._firstReferenced;
/*     */     } public long getLastReferenced() {
/* 126 */       return this._lastReferenced;
/*     */     } public long getAccessCount() {
/* 128 */       return this._accessCount;
/*     */     } public void incAccessCount() {
/* 130 */       this._accessCount++;
/*     */     } public long getLastModified() {
/* 132 */       return this._lastModified;
/*     */     } public void setLastModified(long t) {
/* 134 */       this._lastModified = t;
/*     */     } public long getLatency() {
/* 136 */       return this._buildTime;
/*     */     } public long getLastChecked() {
/* 138 */       return this._lastChecked;
/*     */     } public void setLastChecked(long t) {
/* 140 */       this._lastChecked = t;
/*     */     }
/*     */     public long getEstimatedSize() {
/* 143 */       if (this._dom != null) {
/* 144 */         return (this._dom.getSize() << 5);
/*     */       }
/* 146 */       return 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentCache(int size) throws SAXException {
/* 155 */     this(size, null);
/*     */     try {
/* 157 */       this._dtmManager = XSLTCDTMManager.createNewDTMManagerInstance();
/* 158 */     } catch (Exception e) {
/* 159 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentCache(int size, XSLTCDTMManager dtmManager) throws SAXException {
/* 167 */     this._dtmManager = dtmManager;
/* 168 */     this._count = 0;
/* 169 */     this._current = 0;
/* 170 */     this._size = size;
/* 171 */     this._references = new HashMap<>(this._size + 2);
/* 172 */     this._URIs = new String[this._size];
/*     */ 
/*     */     
/*     */     try {
/* 176 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/*     */       try {
/* 178 */         factory.setFeature("http://xml.org/sax/features/namespaces", true);
/*     */       }
/* 180 */       catch (Exception e) {
/* 181 */         factory.setNamespaceAware(true);
/*     */       } 
/* 183 */       this._parser = factory.newSAXParser();
/* 184 */       this._reader = this._parser.getXMLReader();
/*     */     }
/* 186 */     catch (ParserConfigurationException e) {
/* 187 */       BasisLibrary.runTimeError("NAMESPACES_SUPPORT_ERR");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long getLastModified(String uri) {
/*     */     try {
/* 196 */       URL url = new URL(uri);
/* 197 */       URLConnection connection = url.openConnection();
/* 198 */       long timestamp = connection.getLastModified();
/*     */       
/* 200 */       if (timestamp == 0L && 
/* 201 */         "file".equals(url.getProtocol())) {
/* 202 */         File localfile = new File(URLDecoder.decode(url.getFile()));
/* 203 */         timestamp = localfile.lastModified();
/*     */       } 
/*     */       
/* 206 */       return timestamp;
/*     */     
/*     */     }
/* 209 */     catch (Exception e) {
/* 210 */       return System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CachedDocument lookupDocument(String uri) {
/* 218 */     return this._references.get(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void insertDocument(String uri, CachedDocument doc) {
/* 225 */     if (this._count < this._size) {
/*     */       
/* 227 */       this._URIs[this._count++] = uri;
/* 228 */       this._current = 0;
/*     */     }
/*     */     else {
/*     */       
/* 232 */       this._references.remove(this._URIs[this._current]);
/*     */       
/* 234 */       this._URIs[this._current] = uri;
/* 235 */       if (++this._current >= this._size) this._current = 0; 
/*     */     } 
/* 237 */     this._references.put(uri, doc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void replaceDocument(String uri, CachedDocument doc) {
/* 244 */     if (doc == null) {
/* 245 */       insertDocument(uri, doc);
/*     */     } else {
/* 247 */       this._references.put(uri, doc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOM retrieveDocument(String baseURI, String href, Translet trs) {
/* 258 */     String uri = href;
/* 259 */     if (baseURI != null && !baseURI.equals("")) {
/*     */       try {
/* 261 */         uri = SystemIDResolver.getAbsoluteURI(uri, baseURI);
/* 262 */       } catch (TransformerException transformerException) {}
/*     */     }
/*     */ 
/*     */     
/*     */     CachedDocument doc;
/*     */     
/* 268 */     if ((doc = lookupDocument(uri)) == null) {
/* 269 */       doc = new CachedDocument(uri);
/* 270 */       if (doc == null) return null; 
/* 271 */       doc.setLastModified(getLastModified(uri));
/* 272 */       insertDocument(uri, doc);
/*     */     }
/*     */     else {
/*     */       
/* 276 */       long now = System.currentTimeMillis();
/* 277 */       long chk = doc.getLastChecked();
/* 278 */       doc.setLastChecked(now);
/*     */       
/* 280 */       if (now > chk + 1000L) {
/* 281 */         doc.setLastChecked(now);
/* 282 */         long last = getLastModified(uri);
/*     */         
/* 284 */         if (last > doc.getLastModified()) {
/* 285 */           doc = new CachedDocument(uri);
/* 286 */           if (doc == null) return null; 
/* 287 */           doc.setLastModified(getLastModified(uri));
/* 288 */           replaceDocument(uri, doc);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 295 */     DOM dom = doc.getDocument();
/*     */ 
/*     */ 
/*     */     
/* 299 */     if (dom == null) return null;
/*     */     
/* 301 */     doc.incAccessCount();
/*     */     
/* 303 */     AbstractTranslet translet = (AbstractTranslet)trs;
/*     */ 
/*     */ 
/*     */     
/* 307 */     translet.prepassDocument(dom);
/*     */     
/* 309 */     return doc.getDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getStatistics(PrintWriter out) {
/* 316 */     out.println("<h2>DOM cache statistics</h2><center><table border=\"2\"><tr><td><b>Document URI</b></td><td><center><b>Build time</b></center></td><td><center><b>Access count</b></center></td><td><center><b>Last accessed</b></center></td><td><center><b>Last modified</b></center></td></tr>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     for (int i = 0; i < this._count; i++) {
/* 324 */       CachedDocument doc = this._references.get(this._URIs[i]);
/* 325 */       out.print("<tr><td><a href=\"" + this._URIs[i] + "\"><font size=-1>" + this._URIs[i] + "</font></a></td>");
/*     */       
/* 327 */       out.print("<td><center>" + doc.getLatency() + "ms</center></td>");
/* 328 */       out.print("<td><center>" + doc.getAccessCount() + "</center></td>");
/* 329 */       out.print("<td><center>" + new Date(doc.getLastReferenced()) + "</center></td>");
/*     */       
/* 331 */       out.print("<td><center>" + new Date(doc.getLastModified()) + "</center></td>");
/*     */       
/* 333 */       out.println("</tr>");
/*     */     } 
/*     */     
/* 336 */     out.println("</table></center>");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/DocumentCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */