/*     */ package com.sun.org.apache.xerces.internal.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.util.XML11Char;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XML11DTDScannerImpl
/*     */   extends XMLDTDScannerImpl
/*     */ {
/*  60 */   private XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XML11DTDScannerImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XML11DTDScannerImpl(SymbolTable symbolTable, XMLErrorReporter errorReporter, XMLEntityManager entityManager) {
/*  72 */     super(symbolTable, errorReporter, entityManager);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean scanPubidLiteral(XMLString literal) throws IOException, XNIException {
/* 108 */     int quote = this.fEntityScanner.scanChar(null);
/* 109 */     if (quote != 39 && quote != 34) {
/* 110 */       reportFatalError("QuoteRequiredInPublicID", null);
/* 111 */       return false;
/*     */     } 
/*     */     
/* 114 */     this.fStringBuffer.clear();
/*     */     
/* 116 */     boolean skipSpace = true;
/* 117 */     boolean dataok = true;
/*     */     while (true) {
/* 119 */       int c = this.fEntityScanner.scanChar(null);
/*     */       
/* 121 */       if (c == 32 || c == 10 || c == 13 || c == 133 || c == 8232) {
/* 122 */         if (!skipSpace) {
/*     */           
/* 124 */           this.fStringBuffer.append(' ');
/* 125 */           skipSpace = true;
/*     */         }  continue;
/*     */       } 
/* 128 */       if (c == quote) {
/* 129 */         if (skipSpace)
/*     */         {
/* 131 */           this.fStringBuffer.length--;
/*     */         }
/* 133 */         literal.setValues(this.fStringBuffer);
/*     */         break;
/*     */       } 
/* 136 */       if (XMLChar.isPubid(c)) {
/* 137 */         this.fStringBuffer.append((char)c);
/* 138 */         skipSpace = false; continue;
/*     */       } 
/* 140 */       if (c == -1) {
/* 141 */         reportFatalError("PublicIDUnterminated", null);
/* 142 */         return false;
/*     */       } 
/*     */       
/* 145 */       dataok = false;
/* 146 */       reportFatalError("InvalidCharInPublicID", new Object[] {
/* 147 */             Integer.toHexString(c)
/*     */           });
/*     */     } 
/* 150 */     return dataok;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void normalizeWhitespace(XMLString value) {
/* 158 */     int end = value.offset + value.length;
/* 159 */     for (int i = value.offset; i < end; i++) {
/* 160 */       int c = value.ch[i];
/* 161 */       if (XMLChar.isSpace(c)) {
/* 162 */         value.ch[i] = ' ';
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void normalizeWhitespace(XMLString value, int fromIndex) {
/* 172 */     int end = value.offset + value.length;
/* 173 */     for (int i = value.offset + fromIndex; i < end; i++) {
/* 174 */       int c = value.ch[i];
/* 175 */       if (XMLChar.isSpace(c)) {
/* 176 */         value.ch[i] = ' ';
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
/*     */   protected int isUnchangedByNormalization(XMLString value) {
/* 189 */     int end = value.offset + value.length;
/* 190 */     for (int i = value.offset; i < end; i++) {
/* 191 */       int c = value.ch[i];
/* 192 */       if (XMLChar.isSpace(c)) {
/* 193 */         return i - value.offset;
/*     */       }
/*     */     } 
/* 196 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInvalid(int value) {
/* 203 */     return !XML11Char.isXML11Valid(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInvalidLiteral(int value) {
/* 210 */     return !XML11Char.isXML11ValidLiteral(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidNameChar(int value) {
/* 217 */     return XML11Char.isXML11Name(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidNameStartChar(int value) {
/* 224 */     return XML11Char.isXML11NameStart(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidNCName(int value) {
/* 231 */     return XML11Char.isXML11NCName(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidNameStartHighSurrogate(int value) {
/* 239 */     return XML11Char.isXML11NameHighSurrogate(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean versionSupported(String version) {
/* 246 */     return (version.equals("1.1") || version.equals("1.0"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVersionNotSupportedKey() {
/* 253 */     return "VersionNotSupported11";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XML11DTDScannerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */