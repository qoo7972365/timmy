/*     */ package com.sun.org.apache.xerces.internal.impl;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XML11DocumentScannerImpl
/*     */   extends XMLDocumentScannerImpl
/*     */ {
/*  64 */   private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/*  65 */   private final XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
/*  66 */   private final XMLStringBuffer fStringBuffer3 = new XMLStringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int scanContent(XMLStringBuffer content) throws IOException, XNIException {
/*  88 */     this.fTempString.length = 0;
/*  89 */     int c = this.fEntityScanner.scanContent(this.fTempString);
/*  90 */     content.append(this.fTempString);
/*     */     
/*  92 */     if (c == 13 || c == 133 || c == 8232) {
/*     */ 
/*     */ 
/*     */       
/*  96 */       this.fEntityScanner.scanChar(null);
/*  97 */       content.append((char)c);
/*  98 */       c = -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     if (c == 93) {
/* 105 */       content.append((char)this.fEntityScanner.scanChar(null));
/*     */ 
/*     */ 
/*     */       
/* 109 */       this.fInScanContent = true;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       if (this.fEntityScanner.skipChar(93, null)) {
/* 115 */         content.append(']');
/* 116 */         while (this.fEntityScanner.skipChar(93, null)) {
/* 117 */           content.append(']');
/*     */         }
/* 119 */         if (this.fEntityScanner.skipChar(62, null)) {
/* 120 */           reportFatalError("CDEndInContent", null);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 126 */       this.fInScanContent = false;
/* 127 */       c = -1;
/*     */     } 
/* 129 */     return c;
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
/*     */   protected boolean scanAttributeValue(XMLString value, XMLString nonNormalizedValue, String atName, boolean checkEntities, String eleName, boolean isNSURI) throws IOException, XNIException {
/* 160 */     int quote = this.fEntityScanner.peekChar();
/* 161 */     if (quote != 39 && quote != 34) {
/* 162 */       reportFatalError("OpenQuoteExpected", new Object[] { eleName, atName });
/*     */     }
/*     */     
/* 165 */     this.fEntityScanner.scanChar(XMLScanner.NameType.ATTRIBUTE);
/* 166 */     int entityDepth = this.fEntityDepth;
/*     */     
/* 168 */     int c = this.fEntityScanner.scanLiteral(quote, value, isNSURI);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     int fromIndex = 0;
/* 175 */     if (c == quote && (fromIndex = isUnchangedByNormalization(value)) == -1) {
/*     */       
/* 177 */       nonNormalizedValue.setValues(value);
/* 178 */       int i = this.fEntityScanner.scanChar(XMLScanner.NameType.ATTRIBUTE);
/* 179 */       if (i != quote) {
/* 180 */         reportFatalError("CloseQuoteExpected", new Object[] { eleName, atName });
/*     */       }
/* 182 */       return true;
/*     */     } 
/* 184 */     this.fStringBuffer2.clear();
/* 185 */     this.fStringBuffer2.append(value);
/* 186 */     normalizeWhitespace(value, fromIndex);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (c != quote) {
/* 192 */       this.fScanningAttribute = true;
/* 193 */       this.fStringBuffer.clear();
/*     */       while (true) {
/* 195 */         this.fStringBuffer.append(value);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 200 */         if (c == 38) {
/* 201 */           this.fEntityScanner.skipChar(38, XMLScanner.NameType.REFERENCE);
/* 202 */           if (entityDepth == this.fEntityDepth) {
/* 203 */             this.fStringBuffer2.append('&');
/*     */           }
/* 205 */           if (this.fEntityScanner.skipChar(35, XMLScanner.NameType.REFERENCE)) {
/* 206 */             if (entityDepth == this.fEntityDepth) {
/* 207 */               this.fStringBuffer2.append('#');
/*     */             }
/* 209 */             int ch = scanCharReferenceValue(this.fStringBuffer, this.fStringBuffer2);
/* 210 */             if (ch != -1);
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 219 */             String entityName = this.fEntityScanner.scanName(XMLScanner.NameType.REFERENCE);
/* 220 */             if (entityName == null) {
/* 221 */               reportFatalError("NameRequiredInReference", null);
/*     */             }
/* 223 */             else if (entityDepth == this.fEntityDepth) {
/* 224 */               this.fStringBuffer2.append(entityName);
/*     */             } 
/* 226 */             if (!this.fEntityScanner.skipChar(59, XMLScanner.NameType.REFERENCE)) {
/* 227 */               reportFatalError("SemicolonRequiredInReference", new Object[] { entityName });
/*     */             
/*     */             }
/* 230 */             else if (entityDepth == this.fEntityDepth) {
/* 231 */               this.fStringBuffer2.append(';');
/*     */             } 
/* 233 */             if (resolveCharacter(entityName, this.fStringBuffer)) {
/* 234 */               checkEntityLimit(false, this.fEntityScanner.fCurrentEntity.name, 1);
/*     */             
/*     */             }
/* 237 */             else if (this.fEntityManager.isExternalEntity(entityName)) {
/* 238 */               reportFatalError("ReferenceToExternalEntity", new Object[] { entityName });
/*     */             }
/*     */             else {
/*     */               
/* 242 */               if (!this.fEntityManager.isDeclaredEntity(entityName))
/*     */               {
/* 244 */                 if (checkEntities) {
/* 245 */                   if (this.fValidation) {
/* 246 */                     this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[] { entityName }, (short)1);
/*     */                   
/*     */                   }
/*     */                 
/*     */                 }
/*     */                 else {
/*     */                   
/* 253 */                   reportFatalError("EntityNotDeclared", new Object[] { entityName });
/*     */                 } 
/*     */               }
/*     */               
/* 257 */               this.fEntityManager.startEntity(true, entityName, true);
/*     */             }
/*     */           
/*     */           }
/*     */         
/* 262 */         } else if (c == 60) {
/* 263 */           reportFatalError("LessthanInAttValue", new Object[] { eleName, atName });
/*     */           
/* 265 */           this.fEntityScanner.scanChar(null);
/* 266 */           if (entityDepth == this.fEntityDepth) {
/* 267 */             this.fStringBuffer2.append((char)c);
/*     */           }
/*     */         }
/* 270 */         else if (c == 37 || c == 93) {
/* 271 */           this.fEntityScanner.scanChar(null);
/* 272 */           this.fStringBuffer.append((char)c);
/* 273 */           if (entityDepth == this.fEntityDepth) {
/* 274 */             this.fStringBuffer2.append((char)c);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 284 */         else if (c == 10 || c == 13 || c == 133 || c == 8232) {
/* 285 */           this.fEntityScanner.scanChar(null);
/* 286 */           this.fStringBuffer.append(' ');
/* 287 */           if (entityDepth == this.fEntityDepth) {
/* 288 */             this.fStringBuffer2.append('\n');
/*     */           }
/*     */         }
/* 291 */         else if (c != -1 && XMLChar.isHighSurrogate(c)) {
/* 292 */           this.fStringBuffer3.clear();
/* 293 */           if (scanSurrogates(this.fStringBuffer3)) {
/* 294 */             this.fStringBuffer.append(this.fStringBuffer3);
/* 295 */             if (entityDepth == this.fEntityDepth) {
/* 296 */               this.fStringBuffer2.append(this.fStringBuffer3);
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 305 */         else if (c != -1 && isInvalidLiteral(c)) {
/* 306 */           reportFatalError("InvalidCharInAttValue", new Object[] { eleName, atName, 
/* 307 */                 Integer.toString(c, 16) });
/* 308 */           this.fEntityScanner.scanChar(null);
/* 309 */           if (entityDepth == this.fEntityDepth) {
/* 310 */             this.fStringBuffer2.append((char)c);
/*     */           }
/*     */         } 
/* 313 */         c = this.fEntityScanner.scanLiteral(quote, value, isNSURI);
/* 314 */         if (entityDepth == this.fEntityDepth) {
/* 315 */           this.fStringBuffer2.append(value);
/*     */         }
/* 317 */         normalizeWhitespace(value);
/* 318 */         if (c == quote && entityDepth == this.fEntityDepth)
/* 319 */         { this.fStringBuffer.append(value);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 324 */           value.setValues(this.fStringBuffer);
/* 325 */           this.fScanningAttribute = false; break; } 
/*     */       } 
/* 327 */     }  nonNormalizedValue.setValues(this.fStringBuffer2);
/*     */ 
/*     */     
/* 330 */     int cquote = this.fEntityScanner.scanChar(null);
/* 331 */     if (cquote != quote) {
/* 332 */       reportFatalError("CloseQuoteExpected", new Object[] { eleName, atName });
/*     */     }
/* 334 */     return nonNormalizedValue.equals(value.ch, value.offset, value.length);
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
/*     */   protected boolean scanPubidLiteral(XMLString literal) throws IOException, XNIException {
/* 365 */     int quote = this.fEntityScanner.scanChar(null);
/* 366 */     if (quote != 39 && quote != 34) {
/* 367 */       reportFatalError("QuoteRequiredInPublicID", null);
/* 368 */       return false;
/*     */     } 
/*     */     
/* 371 */     this.fStringBuffer.clear();
/*     */     
/* 373 */     boolean skipSpace = true;
/* 374 */     boolean dataok = true;
/*     */     while (true) {
/* 376 */       int c = this.fEntityScanner.scanChar(null);
/*     */       
/* 378 */       if (c == 32 || c == 10 || c == 13 || c == 133 || c == 8232) {
/* 379 */         if (!skipSpace) {
/*     */           
/* 381 */           this.fStringBuffer.append(' ');
/* 382 */           skipSpace = true;
/*     */         }  continue;
/*     */       } 
/* 385 */       if (c == quote) {
/* 386 */         if (skipSpace)
/*     */         {
/* 388 */           this.fStringBuffer.length--;
/*     */         }
/* 390 */         literal.setValues(this.fStringBuffer);
/*     */         break;
/*     */       } 
/* 393 */       if (XMLChar.isPubid(c)) {
/* 394 */         this.fStringBuffer.append((char)c);
/* 395 */         skipSpace = false; continue;
/*     */       } 
/* 397 */       if (c == -1) {
/* 398 */         reportFatalError("PublicIDUnterminated", null);
/* 399 */         return false;
/*     */       } 
/*     */       
/* 402 */       dataok = false;
/* 403 */       reportFatalError("InvalidCharInPublicID", new Object[] {
/* 404 */             Integer.toHexString(c)
/*     */           });
/*     */     } 
/* 407 */     return dataok;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void normalizeWhitespace(XMLString value) {
/* 415 */     int end = value.offset + value.length;
/* 416 */     for (int i = value.offset; i < end; i++) {
/* 417 */       int c = value.ch[i];
/* 418 */       if (XMLChar.isSpace(c)) {
/* 419 */         value.ch[i] = ' ';
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void normalizeWhitespace(XMLString value, int fromIndex) {
/* 429 */     int end = value.offset + value.length;
/* 430 */     for (int i = value.offset + fromIndex; i < end; i++) {
/* 431 */       int c = value.ch[i];
/* 432 */       if (XMLChar.isSpace(c)) {
/* 433 */         value.ch[i] = ' ';
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
/* 446 */     int end = value.offset + value.length;
/* 447 */     for (int i = value.offset; i < end; i++) {
/* 448 */       int c = value.ch[i];
/* 449 */       if (XMLChar.isSpace(c)) {
/* 450 */         return i - value.offset;
/*     */       }
/*     */     } 
/* 453 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInvalid(int value) {
/* 460 */     return XML11Char.isXML11Invalid(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInvalidLiteral(int value) {
/* 467 */     return !XML11Char.isXML11ValidLiteral(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidNameChar(int value) {
/* 474 */     return XML11Char.isXML11Name(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidNameStartChar(int value) {
/* 481 */     return XML11Char.isXML11NameStart(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidNCName(int value) {
/* 488 */     return XML11Char.isXML11NCName(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidNameStartHighSurrogate(int value) {
/* 496 */     return XML11Char.isXML11NameHighSurrogate(value);
/*     */   }
/*     */   
/*     */   protected boolean versionSupported(String version) {
/* 500 */     return (version.equals("1.1") || version.equals("1.0"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVersionNotSupportedKey() {
/* 507 */     return "VersionNotSupported11";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XML11DocumentScannerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */