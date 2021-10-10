/*     */ package com.sun.xml.internal.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.OutputUtil;
/*     */ import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.BitSet;
/*     */ import javax.activation.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BMMimeMultipart
/*     */   extends MimeMultipart
/*     */ {
/*     */   private boolean begining = true;
/*  82 */   int[] bcs = new int[256];
/*  83 */   int[] gss = null;
/*     */   private static final int BUFFER_SIZE = 4096;
/*  85 */   private byte[] buffer = new byte[4096];
/*  86 */   private byte[] prevBuffer = new byte[4096];
/*  87 */   private BitSet lastPartFound = new BitSet(1);
/*     */ 
/*     */   
/*  90 */   private InputStream in = null;
/*  91 */   private String boundary = null;
/*     */   
/*  93 */   int b = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean lazyAttachments = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] buf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BMMimeMultipart(String subtype)
/*     */   {
/* 121 */     super(subtype);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 698 */     this.buf = new byte[1024]; } public BMMimeMultipart(DataSource ds, ContentType ct) throws MessagingException { super(ds, ct); this.buf = new byte[1024]; this.boundary = ct.getParameter("boundary"); } public InputStream initStream() throws MessagingException { if (this.in == null) { try { this.in = this.ds.getInputStream(); if (!(this.in instanceof java.io.ByteArrayInputStream) && !(this.in instanceof BufferedInputStream) && !(this.in instanceof SharedInputStream)) this.in = new BufferedInputStream(this.in);  } catch (Exception ex) { throw new MessagingException("No inputstream from datasource"); }  if (!this.in.markSupported()) throw new MessagingException("InputStream does not support Marking");  }  return this.in; } protected void parse() throws MessagingException { if (this.parsed) return;  initStream(); SharedInputStream sin = null; if (this.in instanceof SharedInputStream) sin = (SharedInputStream)this.in;  String bnd = "--" + this.boundary; byte[] bndbytes = ASCIIUtility.getBytes(bnd); try { parse(this.in, bndbytes, sin); } catch (IOException ioex) { throw new MessagingException("IO Error", ioex); } catch (Exception ex) { throw new MessagingException("Error", ex); }  this.parsed = true; } public boolean lastBodyPartFound() { return this.lastPartFound.get(0); } public MimeBodyPart getNextPart(InputStream stream, byte[] pattern, SharedInputStream sin) throws Exception { if (!stream.markSupported()) throw new Exception("InputStream does not support Marking");  if (this.begining) { compile(pattern); if (!skipPreamble(stream, pattern, sin)) throw new Exception("Missing Start Boundary, or boundary does not start on a new line");  this.begining = false; }  if (lastBodyPartFound()) throw new Exception("No parts found in Multipart InputStream");  if (sin != null) { long start = sin.getPosition(); this.b = readHeaders(stream); if (this.b == -1) throw new Exception("End of Stream encountered while reading part headers");  long[] v = new long[1]; v[0] = -1L; this.b = readBody(stream, pattern, v, (ByteOutputStream)null, sin); if (!ignoreMissingEndBoundary && this.b == -1 && !lastBodyPartFound()) throw new MessagingException("Missing End Boundary for Mime Package : EOF while skipping headers");  long end = v[0]; MimeBodyPart mimeBodyPart = createMimeBodyPart(sin.newStream(start, end)); addBodyPart(mimeBodyPart); return mimeBodyPart; }  InternetHeaders headers = createInternetHeaders(stream); ByteOutputStream baos = new ByteOutputStream(); this.b = readBody(stream, pattern, (long[])null, baos, (SharedInputStream)null); if (!ignoreMissingEndBoundary && this.b == -1 && !lastBodyPartFound()) throw new MessagingException("Missing End Boundary for Mime Package : EOF while skipping headers");  MimeBodyPart mbp = createMimeBodyPart(headers, baos.getBytes(), baos.getCount()); addBodyPart(mbp); return mbp; } public boolean parse(InputStream stream, byte[] pattern, SharedInputStream sin) throws Exception { while (!this.lastPartFound.get(0) && this.b != -1) getNextPart(stream, pattern, sin);  return true; } private int readHeaders(InputStream is) throws Exception { int b = is.read(); while (b != -1) { if (b == 13) { b = is.read(); if (b == 10) { b = is.read(); if (b == 13) { b = is.read(); if (b == 10) return b;  }  }  continue; }  b = is.read(); }  if (b == -1) throw new Exception("End of inputstream while reading Mime-Part Headers");  return b; } public BMMimeMultipart() { this.buf = new byte[1024]; }
/*     */   private int readBody(InputStream is, byte[] pattern, long[] posVector, ByteOutputStream baos, SharedInputStream sin) throws Exception { if (!find(is, pattern, posVector, baos, sin)) throw new Exception("Missing boundary delimitier while reading Body Part");  return this.b; }
/*     */   private boolean skipPreamble(InputStream is, byte[] pattern, SharedInputStream sin) throws Exception { if (!find(is, pattern, sin)) return false;  if (this.lastPartFound.get(0))
/*     */       throw new Exception("Found closing boundary delimiter while trying to skip preamble");  return true; }
/*     */   public int readNext(InputStream is, byte[] buff, int patternLength, BitSet eof, long[] posVector, SharedInputStream sin) throws Exception { int bufferLength = is.read(this.buffer, 0, patternLength); if (bufferLength == -1) { eof.flip(0); } else if (bufferLength < patternLength) { int temp = 0; long pos = 0L; int i = bufferLength; for (; i < patternLength; i++) { if (sin != null)
/*     */           pos = sin.getPosition();  temp = is.read(); if (temp == -1) { eof.flip(0); if (sin != null)
/* 704 */             posVector[0] = pos;  break; }  this.buffer[i] = (byte)temp; }  bufferLength = i; }  return bufferLength; } public void writeTo(OutputStream os) throws IOException, MessagingException { if (this.in != null) {
/* 705 */       this.contentType.setParameter("boundary", this.boundary);
/*     */     }
/*     */     
/* 708 */     String bnd = "--" + this.contentType.getParameter("boundary");
/* 709 */     for (int i = 0; i < this.parts.size(); i++) {
/* 710 */       OutputUtil.writeln(bnd, os);
/* 711 */       ((MimeBodyPart)this.parts.get(i)).writeTo(os);
/* 712 */       OutputUtil.writeln(os);
/*     */     } 
/*     */     
/* 715 */     if (this.in != null)
/* 716 */     { OutputUtil.writeln(bnd, os);
/* 717 */       if (os instanceof ByteOutputStream && this.lazyAttachments) {
/* 718 */         ((ByteOutputStream)os).write(this.in);
/*     */       } else {
/* 720 */         ByteOutputStream baos = new ByteOutputStream(this.in.available());
/* 721 */         baos.write(this.in);
/* 722 */         baos.writeTo(os);
/*     */ 
/*     */         
/* 725 */         this.in = (InputStream)baos.newInputStream();
/*     */       }
/*     */        }
/*     */     
/*     */     else
/*     */     
/* 731 */     { OutputUtil.writeAsAscii(bnd, os);
/* 732 */       OutputUtil.writeAsAscii("--", os); }  }
/*     */   public boolean find(InputStream is, byte[] pattern, SharedInputStream sin) throws Exception { int l = pattern.length; int lx = l - 1; int bufferLength = 0; BitSet eof = new BitSet(1); long[] posVector = new long[1]; while (true) { is.mark(l); bufferLength = readNext(is, this.buffer, l, eof, posVector, sin); if (eof.get(0)) return false;  int i; for (i = lx; i >= 0 && this.buffer[i] == pattern[i]; i--); if (i < 0) { if (!skipLWSPAndCRLF(is)) throw new Exception("Boundary does not terminate with CRLF");  return true; }  int s = Math.max(i + 1 - this.bcs[this.buffer[i] & Byte.MAX_VALUE], this.gss[i]); is.reset(); is.skip(s); }  }
/*     */   public boolean find(InputStream is, byte[] pattern, long[] posVector, ByteOutputStream out, SharedInputStream sin) throws Exception { int l = pattern.length; int lx = l - 1; int bufferLength = 0; int s = 0; long endPos = -1L; byte[] tmp = null; boolean first = true; BitSet eof = new BitSet(1); while (true) { is.mark(l); if (!first) { tmp = this.prevBuffer; this.prevBuffer = this.buffer; this.buffer = tmp; }  if (sin != null) endPos = sin.getPosition();  bufferLength = readNext(is, this.buffer, l, eof, posVector, sin); if (bufferLength == -1) { this.b = -1; if (s == l && sin == null) out.write(this.prevBuffer, 0, s);  return true; }  if (bufferLength < l) { if (sin == null) out.write(this.buffer, 0, bufferLength);  this.b = -1; return true; }  int i; for (i = lx; i >= 0 && this.buffer[i] == pattern[i]; i--); if (i < 0) { if (s > 0) if (s <= 2) { if (s == 2) { if (this.prevBuffer[1] == 10) { if (this.prevBuffer[0] != 13 && this.prevBuffer[0] != 10) out.write(this.prevBuffer, 0, 1);  if (sin != null) posVector[0] = endPos;  } else { throw new Exception("Boundary characters encountered in part Body without a preceeding CRLF"); }  } else if (s == 1) { if (this.prevBuffer[0] != 10) throw new Exception("Boundary characters encountered in part Body without a preceeding CRLF");  if (sin != null) posVector[0] = endPos;  }  } else if (s > 2) { if (this.prevBuffer[s - 2] == 13 && this.prevBuffer[s - 1] == 10) { if (sin != null) { posVector[0] = endPos - 2L; } else { out.write(this.prevBuffer, 0, s - 2); }  } else if (this.prevBuffer[s - 1] == 10) { if (sin != null) { posVector[0] = endPos - 1L; } else { out.write(this.prevBuffer, 0, s - 1); }  } else { throw new Exception("Boundary characters encountered in part Body without a preceeding CRLF"); }  }   if (!skipLWSPAndCRLF(is)); return true; }  if (s > 0 && sin == null) if (this.prevBuffer[s - 1] == 13) { if (this.buffer[0] == 10) { int j = lx - 1; for (j = lx - 1; j > 0 && this.buffer[j + 1] == pattern[j]; j--); if (j == 0) { out.write(this.prevBuffer, 0, s - 1); } else { out.write(this.prevBuffer, 0, s); }  } else { out.write(this.prevBuffer, 0, s); }  } else { out.write(this.prevBuffer, 0, s); }   s = Math.max(i + 1 - this.bcs[this.buffer[i] & Byte.MAX_VALUE], this.gss[i]); is.reset(); is.skip(s); if (first) first = false;  }  }
/*     */   private boolean skipLWSPAndCRLF(InputStream is) throws Exception { this.b = is.read(); if (this.b == 10) return true;  if (this.b == 13) { this.b = is.read(); if (this.b == 13) this.b = is.read();  if (this.b == 10) return true;  throw new Exception("transport padding after a Mime Boundary  should end in a CRLF, found CR only"); }  if (this.b == 45) { this.b = is.read(); if (this.b != 45) throw new Exception("Unexpected singular '-' character after Mime Boundary");  this.lastPartFound.flip(0); this.b = is.read(); }  while (this.b != -1 && (this.b == 32 || this.b == 9)) { this.b = is.read(); if (this.b == 10) return true;  if (this.b == 13) { this.b = is.read(); if (this.b == 13) this.b = is.read();  if (this.b == 10) return true;  }  }  if (this.b == -1) { if (!this.lastPartFound.get(0)) throw new Exception("End of Multipart Stream before encountering  closing boundary delimiter");  return true; }  return false; }
/*     */   private void compile(byte[] pattern) { int l = pattern.length; int i; for (i = 0; i < l; i++) this.bcs[pattern[i]] = i + 1;  this.gss = new int[l]; for (i = l; i > 0; i--) { int j = l - 1; while (true) { if (j >= i) { if (pattern[j] == pattern[j - i]) { this.gss[j - 1] = i; j--; }  break; }  while (j > 0)
/* 737 */           this.gss[--j] = i;  break; }  }  this.gss[l - 1] = 1; } public void setInputStream(InputStream is) { this.in = is; }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/* 741 */     return this.in;
/*     */   }
/*     */   
/*     */   public void setBoundary(String bnd) {
/* 745 */     this.boundary = bnd;
/* 746 */     if (this.contentType != null)
/* 747 */       this.contentType.setParameter("boundary", bnd); 
/*     */   }
/*     */   
/*     */   public String getBoundary() {
/* 751 */     return this.boundary;
/*     */   }
/*     */   
/*     */   public boolean isEndOfStream() {
/* 755 */     return (this.b == -1);
/*     */   }
/*     */   
/*     */   public void setLazyAttachments(boolean flag) {
/* 759 */     this.lazyAttachments = flag;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/internet/BMMimeMultipart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */