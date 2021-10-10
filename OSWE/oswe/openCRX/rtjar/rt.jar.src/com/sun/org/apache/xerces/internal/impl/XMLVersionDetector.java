/*     */ package com.sun.org.apache.xerces.internal.impl;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*     */ import com.sun.xml.internal.stream.Entity;
/*     */ import java.io.EOFException;
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
/*     */ public class XMLVersionDetector
/*     */ {
/*  50 */   private static final char[] XML11_VERSION = new char[] { '1', '.', '1' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   protected static final String fVersionSymbol = "version".intern();
/*     */ 
/*     */   
/*  75 */   protected static final String fXMLSymbol = "[xml]".intern();
/*     */ 
/*     */   
/*     */   protected SymbolTable fSymbolTable;
/*     */ 
/*     */   
/*     */   protected XMLErrorReporter fErrorReporter;
/*     */ 
/*     */   
/*     */   protected XMLEntityManager fEntityManager;
/*     */   
/*  86 */   protected String fEncoding = null;
/*     */   
/*  88 */   private XMLString fVersionNum = new XMLString();
/*     */   
/*  90 */   private final char[] fExpectedVersionString = new char[] { '<', '?', 'x', 'm', 'l', ' ', 'v', 'e', 'r', 's', 'i', 'o', 'n', '=', ' ', ' ', ' ', ' ', ' ' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/* 105 */     this.fSymbolTable = (SymbolTable)componentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/* 106 */     this.fErrorReporter = (XMLErrorReporter)componentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/* 107 */     this.fEntityManager = (XMLEntityManager)componentManager.getProperty("http://apache.org/xml/properties/internal/entity-manager");
/* 108 */     for (int i = 14; i < this.fExpectedVersionString.length; i++) {
/* 109 */       this.fExpectedVersionString[i] = ' ';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocumentParsing(XMLEntityHandler scanner, short version) {
/* 120 */     if (version == 1) {
/* 121 */       this.fEntityManager.setScannerVersion((short)1);
/*     */     } else {
/*     */       
/* 124 */       this.fEntityManager.setScannerVersion((short)2);
/*     */     } 
/*     */     
/* 127 */     this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     this.fEntityManager.setEntityHandler(scanner);
/*     */     
/* 134 */     scanner.startEntity(fXMLSymbol, this.fEntityManager.getCurrentResourceIdentifier(), this.fEncoding, null);
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
/*     */   public short determineDocVersion(XMLInputSource inputSource) throws IOException {
/* 148 */     this.fEncoding = this.fEntityManager.setupCurrentEntity(false, fXMLSymbol, inputSource, false, true);
/*     */ 
/*     */ 
/*     */     
/* 152 */     this.fEntityManager.setScannerVersion((short)1);
/* 153 */     XMLEntityScanner scanner = this.fEntityManager.getEntityScanner();
/* 154 */     scanner.detectingVersion = true;
/*     */     try {
/* 156 */       if (!scanner.skipString("<?xml")) {
/*     */         
/* 158 */         scanner.detectingVersion = false;
/* 159 */         return 1;
/*     */       } 
/* 161 */       if (!scanner.skipDeclSpaces()) {
/* 162 */         fixupCurrentEntity(this.fEntityManager, this.fExpectedVersionString, 5);
/* 163 */         scanner.detectingVersion = false;
/* 164 */         return 1;
/*     */       } 
/* 166 */       if (!scanner.skipString("version")) {
/* 167 */         fixupCurrentEntity(this.fEntityManager, this.fExpectedVersionString, 6);
/* 168 */         scanner.detectingVersion = false;
/* 169 */         return 1;
/*     */       } 
/* 171 */       scanner.skipDeclSpaces();
/*     */       
/* 173 */       if (scanner.peekChar() != 61) {
/* 174 */         fixupCurrentEntity(this.fEntityManager, this.fExpectedVersionString, 13);
/* 175 */         scanner.detectingVersion = false;
/* 176 */         return 1;
/*     */       } 
/* 178 */       scanner.scanChar(null);
/* 179 */       scanner.skipDeclSpaces();
/* 180 */       int quoteChar = scanner.scanChar(null);
/* 181 */       this.fExpectedVersionString[14] = (char)quoteChar;
/* 182 */       for (int versionPos = 0; versionPos < XML11_VERSION.length; versionPos++) {
/* 183 */         this.fExpectedVersionString[15 + versionPos] = (char)scanner.scanChar(null);
/*     */       }
/*     */       
/* 186 */       this.fExpectedVersionString[18] = (char)scanner.scanChar(null);
/* 187 */       fixupCurrentEntity(this.fEntityManager, this.fExpectedVersionString, 19);
/* 188 */       int matched = 0;
/* 189 */       for (; matched < XML11_VERSION.length && 
/* 190 */         this.fExpectedVersionString[15 + matched] == XML11_VERSION[matched]; matched++);
/*     */ 
/*     */       
/* 193 */       scanner.detectingVersion = false;
/* 194 */       if (matched == XML11_VERSION.length)
/* 195 */         return 2; 
/* 196 */       return 1;
/*     */     
/*     */     }
/* 199 */     catch (EOFException e) {
/* 200 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "PrematureEOF", null, (short)2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 205 */       scanner.detectingVersion = false;
/* 206 */       return 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fixupCurrentEntity(XMLEntityManager manager, char[] scannedChars, int length) {
/* 214 */     Entity.ScannedEntity currentEntity = manager.getCurrentEntity();
/* 215 */     if (currentEntity.count - currentEntity.position + length > currentEntity.ch.length) {
/*     */       
/* 217 */       char[] tempCh = currentEntity.ch;
/* 218 */       currentEntity.ch = new char[length + currentEntity.count - currentEntity.position + 1];
/* 219 */       System.arraycopy(tempCh, 0, currentEntity.ch, 0, tempCh.length);
/*     */     } 
/* 221 */     if (currentEntity.position < length) {
/*     */       
/* 223 */       System.arraycopy(currentEntity.ch, currentEntity.position, currentEntity.ch, length, currentEntity.count - currentEntity.position);
/* 224 */       currentEntity.count += length - currentEntity.position;
/*     */     } else {
/*     */       
/* 227 */       for (int i = length; i < currentEntity.position; i++) {
/* 228 */         currentEntity.ch[i] = ' ';
/*     */       }
/*     */     } 
/* 231 */     System.arraycopy(scannedChars, 0, currentEntity.ch, 0, length);
/* 232 */     currentEntity.position = 0;
/* 233 */     currentEntity.baseCharOffset = 0;
/* 234 */     currentEntity.startPosition = 0;
/* 235 */     currentEntity.columnNumber = currentEntity.lineNumber = 1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLVersionDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */