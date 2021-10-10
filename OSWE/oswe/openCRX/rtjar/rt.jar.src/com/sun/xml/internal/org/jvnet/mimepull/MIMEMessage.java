/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class MIMEMessage
/*     */ {
/*  44 */   private static final Logger LOGGER = Logger.getLogger(MIMEMessage.class.getName());
/*     */   
/*     */   MIMEConfig config;
/*     */   
/*     */   private final InputStream in;
/*     */   
/*     */   private final List<MIMEPart> partsList;
/*     */   
/*     */   private final Map<String, MIMEPart> partsMap;
/*     */   
/*     */   private final Iterator<MIMEEvent> it;
/*     */   private boolean parsed;
/*     */   private MIMEPart currentPart;
/*     */   private int currentIndex;
/*     */   
/*     */   public MIMEMessage(InputStream in, String boundary) {
/*  60 */     this(in, boundary, new MIMEConfig());
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
/*     */   public MIMEMessage(InputStream in, String boundary, MIMEConfig config) {
/*  72 */     this.in = in;
/*  73 */     this.config = config;
/*  74 */     MIMEParser parser = new MIMEParser(in, boundary, config);
/*  75 */     this.it = parser.iterator();
/*     */     
/*  77 */     this.partsList = new ArrayList<>();
/*  78 */     this.partsMap = new HashMap<>();
/*  79 */     if (config.isParseEagerly()) {
/*  80 */       parseAll();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MIMEPart> getAttachments() {
/*  91 */     if (!this.parsed) {
/*  92 */       parseAll();
/*     */     }
/*  94 */     return this.partsList;
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
/*     */   public MIMEPart getPart(int index) {
/* 108 */     LOGGER.log(Level.FINE, "index={0}", Integer.valueOf(index));
/* 109 */     MIMEPart part = (index < this.partsList.size()) ? this.partsList.get(index) : null;
/* 110 */     if (this.parsed && part == null) {
/* 111 */       throw new MIMEParsingException("There is no " + index + " attachment part ");
/*     */     }
/* 113 */     if (part == null) {
/*     */       
/* 115 */       part = new MIMEPart(this);
/* 116 */       this.partsList.add(index, part);
/*     */     } 
/* 118 */     LOGGER.log(Level.FINE, "Got attachment at index={0} attachment={1}", new Object[] { Integer.valueOf(index), part });
/* 119 */     return part;
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
/*     */   public MIMEPart getPart(String contentId) {
/* 132 */     LOGGER.log(Level.FINE, "Content-ID={0}", contentId);
/* 133 */     MIMEPart part = getDecodedCidPart(contentId);
/* 134 */     if (this.parsed && part == null) {
/* 135 */       throw new MIMEParsingException("There is no attachment part with Content-ID = " + contentId);
/*     */     }
/* 137 */     if (part == null) {
/*     */       
/* 139 */       part = new MIMEPart(this, contentId);
/* 140 */       this.partsMap.put(contentId, part);
/*     */     } 
/* 142 */     LOGGER.log(Level.FINE, "Got attachment for Content-ID={0} attachment={1}", new Object[] { contentId, part });
/* 143 */     return part;
/*     */   }
/*     */ 
/*     */   
/*     */   private MIMEPart getDecodedCidPart(String cid) {
/* 148 */     MIMEPart part = this.partsMap.get(cid);
/* 149 */     if (part == null && 
/* 150 */       cid.indexOf('%') != -1) {
/*     */       try {
/* 152 */         String tempCid = URLDecoder.decode(cid, "utf-8");
/* 153 */         part = this.partsMap.get(tempCid);
/* 154 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 159 */     return part;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void parseAll() {
/* 167 */     while (makeProgress());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean makeProgress() {
/*     */     MIMEEvent.Headers headers;
/*     */     InternetHeaders ih;
/*     */     List<String> cids;
/*     */     String cid;
/*     */     MIMEPart listPart, mapPart;
/*     */     MIMEEvent.Content content;
/*     */     ByteBuffer buf;
/* 180 */     if (!this.it.hasNext()) {
/* 181 */       return false;
/*     */     }
/*     */     
/* 184 */     MIMEEvent event = this.it.next();
/*     */     
/* 186 */     switch (event.getEventType()) {
/*     */       case START_MESSAGE:
/* 188 */         LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.START_MESSAGE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 248 */         return true;case START_PART: LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.START_PART); return true;case HEADERS: LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.HEADERS); headers = (MIMEEvent.Headers)event; ih = headers.getHeaders(); cids = ih.getHeader("content-id"); cid = (cids != null) ? cids.get(0) : (this.currentIndex + ""); if (cid.length() > 2 && cid.charAt(0) == '<') cid = cid.substring(1, cid.length() - 1);  listPart = (this.currentIndex < this.partsList.size()) ? this.partsList.get(this.currentIndex) : null; mapPart = getDecodedCidPart(cid); if (listPart == null && mapPart == null) { this.currentPart = getPart(cid); this.partsList.add(this.currentIndex, this.currentPart); } else if (listPart == null) { this.currentPart = mapPart; this.partsList.add(this.currentIndex, mapPart); } else if (mapPart == null) { this.currentPart = listPart; this.currentPart.setContentId(cid); this.partsMap.put(cid, this.currentPart); } else if (listPart != mapPart) { throw new MIMEParsingException("Created two different attachments using Content-ID and index"); }  this.currentPart.setHeaders(ih); return true;case CONTENT: LOGGER.log(Level.FINER, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.CONTENT); content = (MIMEEvent.Content)event; buf = content.getData(); this.currentPart.addBody(buf); return true;case END_PART: LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.END_PART); this.currentPart.doneParsing(); this.currentIndex++; return true;case END_MESSAGE: LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.END_MESSAGE); this.parsed = true; try { this.in.close(); } catch (IOException ioe) { throw new MIMEParsingException(ioe); }  return true;
/*     */     } 
/*     */     throw new MIMEParsingException("Unknown Parser state = " + event.getEventType());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/MIMEMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */