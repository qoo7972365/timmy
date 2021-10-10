/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class InternetHeaders
/*     */ {
/*  62 */   private final FinalArrayList<Hdr> headers = new FinalArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InternetHeaders(MIMEParser.LineInputStream lis) {
/*  79 */     String prevline = null;
/*     */     
/*  81 */     StringBuilder lineBuffer = new StringBuilder();
/*     */     
/*     */     try {
/*     */       String line;
/*     */       do {
/*  86 */         line = lis.readLine();
/*  87 */         if (line != null && (line
/*  88 */           .startsWith(" ") || line.startsWith("\t"))) {
/*     */           
/*  90 */           if (prevline != null) {
/*  91 */             lineBuffer.append(prevline);
/*  92 */             prevline = null;
/*     */           } 
/*  94 */           lineBuffer.append("\r\n");
/*  95 */           lineBuffer.append(line);
/*     */         } else {
/*     */           
/*  98 */           if (prevline != null) {
/*  99 */             addHeaderLine(prevline);
/* 100 */           } else if (lineBuffer.length() > 0) {
/*     */             
/* 102 */             addHeaderLine(lineBuffer.toString());
/* 103 */             lineBuffer.setLength(0);
/*     */           } 
/* 105 */           prevline = line;
/*     */         } 
/* 107 */       } while (line != null && line.length() > 0);
/* 108 */     } catch (IOException ioex) {
/* 109 */       throw new MIMEParsingException("Error in input stream", ioex);
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
/*     */   List<String> getHeader(String name) {
/* 123 */     FinalArrayList<String> v = new FinalArrayList<>();
/*     */     
/* 125 */     int len = this.headers.size();
/* 126 */     for (int i = 0; i < len; i++) {
/* 127 */       Hdr h = this.headers.get(i);
/* 128 */       if (name.equalsIgnoreCase(h.name)) {
/* 129 */         v.add(h.getValue());
/*     */       }
/*     */     } 
/* 132 */     return (v.size() == 0) ? null : v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FinalArrayList<? extends Header> getAllHeaders() {
/* 142 */     return (FinalArrayList)this.headers;
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
/*     */   void addHeaderLine(String line) {
/*     */     try {
/* 156 */       char c = line.charAt(0);
/* 157 */       if (c == ' ' || c == '\t') {
/* 158 */         Hdr h = this.headers.get(this.headers.size() - 1);
/* 159 */         h.line += "\r\n" + line;
/*     */       } else {
/* 161 */         this.headers.add(new Hdr(line));
/*     */       } 
/* 163 */     } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
/*     */     
/* 165 */     } catch (NoSuchElementException noSuchElementException) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/InternetHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */