/*     */ package jdk.internal.util.xml.impl;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import jdk.internal.util.xml.XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLWriter
/*     */ {
/*     */   private Writer _writer;
/*  51 */   private CharsetEncoder _encoder = null;
/*     */   
/*     */   public XMLWriter(OutputStream paramOutputStream, String paramString, Charset paramCharset) throws XMLStreamException {
/*  54 */     this._encoder = paramCharset.newEncoder();
/*     */     try {
/*  56 */       this._writer = getWriter(paramOutputStream, paramString, paramCharset);
/*  57 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  58 */       throw new XMLStreamException(unsupportedEncodingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canEncode(char paramChar) {
/*  64 */     if (this._encoder == null) {
/*  65 */       return false;
/*     */     }
/*  67 */     return this._encoder.canEncode(paramChar);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(String paramString) throws XMLStreamException {
/*     */     try {
/*  73 */       this._writer.write(paramString.toCharArray());
/*     */     }
/*  75 */     catch (IOException iOException) {
/*  76 */       throw new XMLStreamException("I/O error", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(String paramString, int paramInt1, int paramInt2) throws XMLStreamException {
/*     */     try {
/*  83 */       this._writer.write(paramString, paramInt1, paramInt2);
/*  84 */     } catch (IOException iOException) {
/*  85 */       throw new XMLStreamException("I/O error", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws XMLStreamException {
/*     */     try {
/*  93 */       this._writer.write(paramArrayOfchar, paramInt1, paramInt2);
/*  94 */     } catch (IOException iOException) {
/*  95 */       throw new XMLStreamException("I/O error", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void write(int paramInt) throws XMLStreamException {
/*     */     try {
/* 103 */       this._writer.write(paramInt);
/* 104 */     } catch (IOException iOException) {
/* 105 */       throw new XMLStreamException("I/O error", iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   void flush() throws XMLStreamException {
/*     */     try {
/* 111 */       this._writer.flush();
/* 112 */     } catch (IOException iOException) {
/* 113 */       throw new XMLStreamException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   void close() throws XMLStreamException {
/*     */     try {
/* 119 */       this._writer.close();
/* 120 */     } catch (IOException iOException) {
/* 121 */       throw new XMLStreamException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void nl() throws XMLStreamException {
/* 126 */     String str = System.getProperty("line.separator");
/*     */     try {
/* 128 */       this._writer.write(str);
/* 129 */     } catch (IOException iOException) {
/* 130 */       throw new XMLStreamException("I/O error", iOException);
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
/*     */ 
/*     */   
/*     */   private Writer getWriter(OutputStream paramOutputStream, String paramString, Charset paramCharset) throws XMLStreamException, UnsupportedEncodingException {
/* 146 */     if (paramCharset != null) {
/* 147 */       return new OutputStreamWriter(new BufferedOutputStream(paramOutputStream), paramCharset);
/*     */     }
/*     */     
/* 150 */     return new OutputStreamWriter(new BufferedOutputStream(paramOutputStream), paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/XMLWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */