/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MIMEPart
/*     */ {
/*  47 */   private static final Logger LOGGER = Logger.getLogger(MIMEPart.class.getName());
/*     */   
/*     */   private volatile InternetHeaders headers;
/*     */   
/*     */   private volatile String contentId;
/*     */   private String contentType;
/*     */   private String contentTransferEncoding;
/*     */   volatile boolean parsed;
/*     */   final MIMEMessage msg;
/*     */   private final DataHead dataHead;
/*     */   
/*     */   MIMEPart(MIMEMessage msg) {
/*  59 */     this.msg = msg;
/*  60 */     this.dataHead = new DataHead(this);
/*     */   }
/*     */   
/*     */   MIMEPart(MIMEMessage msg, String contentId) {
/*  64 */     this(msg);
/*  65 */     this.contentId = contentId;
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
/*     */   public InputStream read() {
/*  78 */     InputStream is = null;
/*     */     try {
/*  80 */       is = MimeUtility.decode(this.dataHead.read(), this.contentTransferEncoding);
/*  81 */     } catch (DecodingException ex) {
/*  82 */       if (LOGGER.isLoggable(Level.WARNING)) {
/*  83 */         LOGGER.log(Level.WARNING, (String)null, ex);
/*     */       }
/*     */     } 
/*  86 */     return is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*  95 */     this.dataHead.close();
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
/*     */   public InputStream readOnce() {
/* 111 */     InputStream is = null;
/*     */     try {
/* 113 */       is = MimeUtility.decode(this.dataHead.readOnce(), this.contentTransferEncoding);
/* 114 */     } catch (DecodingException ex) {
/* 115 */       if (LOGGER.isLoggable(Level.WARNING)) {
/* 116 */         LOGGER.log(Level.WARNING, (String)null, ex);
/*     */       }
/*     */     } 
/* 119 */     return is;
/*     */   }
/*     */   
/*     */   public void moveTo(File f) {
/* 123 */     this.dataHead.moveTo(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentId() {
/* 132 */     if (this.contentId == null) {
/* 133 */       getHeaders();
/*     */     }
/* 135 */     return this.contentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentTransferEncoding() {
/* 144 */     if (this.contentTransferEncoding == null) {
/* 145 */       getHeaders();
/*     */     }
/* 147 */     return this.contentTransferEncoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 156 */     if (this.contentType == null) {
/* 157 */       getHeaders();
/*     */     }
/* 159 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   private void getHeaders() {
/* 164 */     while (this.headers == null) {
/* 165 */       if (!this.msg.makeProgress() && 
/* 166 */         this.headers == null) {
/* 167 */         throw new IllegalStateException("Internal Error. Didn't get Headers even after complete parsing.");
/*     */       }
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
/*     */   public List<String> getHeader(String name) {
/* 182 */     getHeaders();
/* 183 */     assert this.headers != null;
/* 184 */     return this.headers.getHeader(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<? extends Header> getAllHeaders() {
/* 193 */     getHeaders();
/* 194 */     assert this.headers != null;
/* 195 */     return this.headers.getAllHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setHeaders(InternetHeaders headers) {
/* 204 */     this.headers = headers;
/* 205 */     List<String> ct = getHeader("Content-Type");
/* 206 */     this.contentType = (ct == null) ? "application/octet-stream" : ct.get(0);
/* 207 */     List<String> cte = getHeader("Content-Transfer-Encoding");
/* 208 */     this.contentTransferEncoding = (cte == null) ? "binary" : cte.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addBody(ByteBuffer buf) {
/* 217 */     this.dataHead.addBody(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void doneParsing() {
/* 225 */     this.parsed = true;
/* 226 */     this.dataHead.doneParsing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setContentId(String cid) {
/* 234 */     this.contentId = cid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setContentTransferEncoding(String cte) {
/* 242 */     this.contentTransferEncoding = cte;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 247 */     return "Part=" + this.contentId + ":" + this.contentTransferEncoding;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/MIMEPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */