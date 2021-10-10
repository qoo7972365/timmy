/*      */ package com.sun.org.apache.xerces.internal.xpointer;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.xinclude.XIncludeHandler;
/*      */ import com.sun.org.apache.xerces.internal.xinclude.XIncludeNamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class XPointerHandler
/*      */   extends XIncludeHandler
/*      */   implements XPointerProcessor
/*      */ {
/*   68 */   protected Vector fXPointerParts = null;
/*      */ 
/*      */   
/*   71 */   protected XPointerPart fXPointerPart = null;
/*      */ 
/*      */   
/*      */   protected boolean fFoundMatchingPtrPart = false;
/*      */ 
/*      */   
/*      */   protected XMLErrorReporter fXPointerErrorReporter;
/*      */ 
/*      */   
/*      */   protected XMLErrorHandler fErrorHandler;
/*      */ 
/*      */   
/*   83 */   protected SymbolTable fSymbolTable = null;
/*      */ 
/*      */   
/*   86 */   private final String ELEMENT_SCHEME_NAME = "element";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fIsXPointerResolved = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fFixupBase = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fFixupLang = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XPointerHandler() {
/*  105 */     this.fXPointerParts = new Vector();
/*  106 */     this.fSymbolTable = new SymbolTable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XPointerHandler(SymbolTable symbolTable, XMLErrorHandler errorHandler, XMLErrorReporter errorReporter) {
/*  113 */     this.fXPointerParts = new Vector();
/*  114 */     this.fSymbolTable = symbolTable;
/*  115 */     this.fErrorHandler = errorHandler;
/*  116 */     this.fXPointerErrorReporter = errorReporter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void parseXPointer(String xpointer) throws XNIException {
/*  132 */     init();
/*      */ 
/*      */     
/*  135 */     Tokens tokens = new Tokens(this.fSymbolTable);
/*      */ 
/*      */     
/*  138 */     Scanner scanner = new Scanner(this.fSymbolTable)
/*      */       {
/*      */         protected void addToken(XPointerHandler.Tokens tokens, int token) throws XNIException {
/*  141 */           if (token == 0 || token == 1 || token == 3 || token == 4 || token == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  146 */             super.addToken(tokens, token);
/*      */             return;
/*      */           } 
/*  149 */           XPointerHandler.this.reportError("InvalidXPointerToken", new Object[] {
/*  150 */                 XPointerHandler.Tokens.access$200(tokens, token)
/*      */               });
/*      */         }
/*      */       };
/*      */     
/*  155 */     int length = xpointer.length();
/*  156 */     boolean success = scanner.scanExpr(this.fSymbolTable, tokens, xpointer, 0, length);
/*      */ 
/*      */     
/*  159 */     if (!success) {
/*  160 */       reportError("InvalidXPointerExpression", new Object[] { xpointer });
/*      */     }
/*  162 */     while (tokens.hasMore()) {
/*  163 */       String shortHandPointerName, prefix; XPointerPart shortHandPointer; String localName, schemeName; int openParenCount, closeParenCount; String openParen, schemeData, closeParen; int token = tokens.nextToken();
/*      */       
/*  165 */       switch (token) {
/*      */ 
/*      */         
/*      */         case 2:
/*  169 */           token = tokens.nextToken();
/*  170 */           shortHandPointerName = tokens.getTokenString(token);
/*      */           
/*  172 */           if (shortHandPointerName == null) {
/*  173 */             reportError("InvalidXPointerExpression", new Object[] { xpointer });
/*      */           }
/*      */ 
/*      */           
/*  177 */           shortHandPointer = new ShortHandPointer(this.fSymbolTable);
/*      */           
/*  179 */           shortHandPointer.setSchemeName(shortHandPointerName);
/*  180 */           this.fXPointerParts.add(shortHandPointer);
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case 3:
/*  186 */           token = tokens.nextToken();
/*  187 */           prefix = tokens.getTokenString(token);
/*  188 */           token = tokens.nextToken();
/*  189 */           localName = tokens.getTokenString(token);
/*      */           
/*  191 */           schemeName = prefix + localName;
/*      */ 
/*      */           
/*  194 */           openParenCount = 0;
/*  195 */           closeParenCount = 0;
/*      */           
/*  197 */           token = tokens.nextToken();
/*  198 */           openParen = tokens.getTokenString(token);
/*  199 */           if (openParen != "XPTRTOKEN_OPEN_PAREN")
/*      */           {
/*      */             
/*  202 */             if (token == 2) {
/*  203 */               reportError("MultipleShortHandPointers", new Object[] { xpointer });
/*      */             } else {
/*      */               
/*  206 */               reportError("InvalidXPointerExpression", new Object[] { xpointer });
/*      */             } 
/*      */           }
/*      */           
/*  210 */           openParenCount++;
/*      */ 
/*      */           
/*  213 */           schemeData = null;
/*  214 */           while (tokens.hasMore()) {
/*  215 */             token = tokens.nextToken();
/*  216 */             schemeData = tokens.getTokenString(token);
/*  217 */             if (schemeData != "XPTRTOKEN_OPEN_PAREN") {
/*      */               break;
/*      */             }
/*  220 */             openParenCount++;
/*      */           } 
/*  222 */           token = tokens.nextToken();
/*  223 */           schemeData = tokens.getTokenString(token);
/*      */ 
/*      */           
/*  226 */           token = tokens.nextToken();
/*  227 */           closeParen = tokens.getTokenString(token);
/*  228 */           if (closeParen != "XPTRTOKEN_CLOSE_PAREN") {
/*  229 */             reportError("SchemeDataNotFollowedByCloseParenthesis", new Object[] { xpointer });
/*      */           }
/*      */           
/*  232 */           closeParenCount++;
/*      */           
/*  234 */           while (tokens.hasMore() && 
/*  235 */             tokens.getTokenString(tokens.peekToken()) == "XPTRTOKEN_OPEN_PAREN")
/*      */           {
/*      */             
/*  238 */             closeParenCount++;
/*      */           }
/*      */ 
/*      */           
/*  242 */           if (openParenCount != closeParenCount) {
/*  243 */             reportError("UnbalancedParenthesisInXPointerExpression", new Object[] { xpointer, new Integer(openParenCount), new Integer(closeParenCount) });
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  250 */           if (schemeName.equals("element")) {
/*  251 */             XPointerPart elementSchemePointer = new ElementSchemePointer(this.fSymbolTable, this.fErrorReporter);
/*      */             
/*  253 */             elementSchemePointer.setSchemeName(schemeName);
/*  254 */             elementSchemePointer.setSchemeData(schemeData);
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  259 */               elementSchemePointer.parseXPointer(schemeData);
/*  260 */               this.fXPointerParts.add(elementSchemePointer);
/*  261 */             } catch (XNIException e) {
/*      */               
/*  263 */               throw new XNIException(e);
/*      */             } 
/*      */             
/*      */             continue;
/*      */           } 
/*  268 */           reportWarning("SchemeUnsupported", new Object[] { schemeName });
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  275 */       reportError("InvalidXPointerExpression", new Object[] { xpointer });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean resolveXPointer(QName element, XMLAttributes attributes, Augmentations augs, int event) throws XNIException {
/*  288 */     boolean resolved = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  297 */     if (!this.fFoundMatchingPtrPart) {
/*      */ 
/*      */ 
/*      */       
/*  301 */       for (int i = 0; i < this.fXPointerParts.size(); i++) {
/*      */         
/*  303 */         this.fXPointerPart = this.fXPointerParts.get(i);
/*      */         
/*  305 */         if (this.fXPointerPart.resolveXPointer(element, attributes, augs, event))
/*      */         {
/*  307 */           this.fFoundMatchingPtrPart = true;
/*  308 */           resolved = true;
/*      */         }
/*      */       
/*      */       } 
/*  312 */     } else if (this.fXPointerPart.resolveXPointer(element, attributes, augs, event)) {
/*  313 */       resolved = true;
/*      */     } 
/*      */ 
/*      */     
/*  317 */     if (!this.fIsXPointerResolved) {
/*  318 */       this.fIsXPointerResolved = resolved;
/*      */     }
/*      */     
/*  321 */     return resolved;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFragmentResolved() throws XNIException {
/*  330 */     boolean resolved = (this.fXPointerPart != null) ? this.fXPointerPart.isFragmentResolved() : false;
/*      */ 
/*      */     
/*  333 */     if (!this.fIsXPointerResolved) {
/*  334 */       this.fIsXPointerResolved = resolved;
/*      */     }
/*      */     
/*  337 */     return resolved;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChildFragmentResolved() throws XNIException {
/*  349 */     boolean resolved = (this.fXPointerPart != null) ? this.fXPointerPart.isChildFragmentResolved() : false;
/*  350 */     return resolved;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isXPointerResolved() throws XNIException {
/*  359 */     return this.fIsXPointerResolved;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XPointerPart getXPointerPart() {
/*  368 */     return this.fXPointerPart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reportError(String key, Object[] arguments) throws XNIException {
/*  382 */     throw new XNIException(this.fErrorReporter
/*  383 */         .getMessageFormatter("http://www.w3.org/TR/XPTR")
/*  384 */         .formatMessage(this.fErrorReporter.getLocale(), key, arguments));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reportWarning(String key, Object[] arguments) throws XNIException {
/*  393 */     this.fXPointerErrorReporter.reportError("http://www.w3.org/TR/XPTR", key, arguments, (short)0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initErrorReporter() {
/*  403 */     if (this.fXPointerErrorReporter == null) {
/*  404 */       this.fXPointerErrorReporter = new XMLErrorReporter();
/*      */     }
/*  406 */     if (this.fErrorHandler == null) {
/*  407 */       this.fErrorHandler = new XPointerErrorHandler();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  413 */     this.fXPointerErrorReporter.putMessageFormatter("http://www.w3.org/TR/XPTR", new XPointerMessageFormatter());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void init() {
/*  422 */     this.fXPointerParts.clear();
/*  423 */     this.fXPointerPart = null;
/*  424 */     this.fFoundMatchingPtrPart = false;
/*  425 */     this.fIsXPointerResolved = false;
/*      */ 
/*      */ 
/*      */     
/*  429 */     initErrorReporter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getPointerParts() {
/*  438 */     return this.fXPointerParts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Tokens
/*      */   {
/*      */     private static final int XPTRTOKEN_OPEN_PAREN = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int XPTRTOKEN_CLOSE_PAREN = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int XPTRTOKEN_SHORTHAND = 2;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int XPTRTOKEN_SCHEMENAME = 3;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int XPTRTOKEN_SCHEMEDATA = 4;
/*      */ 
/*      */ 
/*      */     
/*  467 */     private final String[] fgTokenNames = new String[] { "XPTRTOKEN_OPEN_PAREN", "XPTRTOKEN_CLOSE_PAREN", "XPTRTOKEN_SHORTHAND", "XPTRTOKEN_SCHEMENAME", "XPTRTOKEN_SCHEMEDATA" };
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int INITIAL_TOKEN_COUNT = 256;
/*      */ 
/*      */     
/*  474 */     private int[] fTokens = new int[256];
/*      */     
/*  476 */     private int fTokenCount = 0;
/*      */ 
/*      */     
/*      */     private int fCurrentTokenIndex;
/*      */     
/*      */     private SymbolTable fSymbolTable;
/*      */     
/*  483 */     private Hashtable fTokenNames = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Tokens(SymbolTable symbolTable) {
/*  491 */       this.fSymbolTable = symbolTable;
/*      */       
/*  493 */       this.fTokenNames.put(new Integer(0), "XPTRTOKEN_OPEN_PAREN");
/*      */       
/*  495 */       this.fTokenNames.put(new Integer(1), "XPTRTOKEN_CLOSE_PAREN");
/*      */       
/*  497 */       this.fTokenNames.put(new Integer(2), "XPTRTOKEN_SHORTHAND");
/*      */       
/*  499 */       this.fTokenNames.put(new Integer(3), "XPTRTOKEN_SCHEMENAME");
/*      */       
/*  501 */       this.fTokenNames.put(new Integer(4), "XPTRTOKEN_SCHEMEDATA");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String getTokenString(int token) {
/*  511 */       return (String)this.fTokenNames.get(new Integer(token));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void addToken(String tokenStr) {
/*  520 */       Integer tokenInt = (Integer)this.fTokenNames.get(tokenStr);
/*  521 */       if (tokenInt == null) {
/*  522 */         tokenInt = new Integer(this.fTokenNames.size());
/*  523 */         this.fTokenNames.put(tokenInt, tokenStr);
/*      */       } 
/*  525 */       addToken(tokenInt.intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void addToken(int token) {
/*      */       try {
/*  535 */         this.fTokens[this.fTokenCount] = token;
/*  536 */       } catch (ArrayIndexOutOfBoundsException ex) {
/*  537 */         int[] oldList = this.fTokens;
/*  538 */         this.fTokens = new int[this.fTokenCount << 1];
/*  539 */         System.arraycopy(oldList, 0, this.fTokens, 0, this.fTokenCount);
/*  540 */         this.fTokens[this.fTokenCount] = token;
/*      */       } 
/*  542 */       this.fTokenCount++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void rewind() {
/*  549 */       this.fCurrentTokenIndex = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean hasMore() {
/*  557 */       return (this.fCurrentTokenIndex < this.fTokenCount);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int nextToken() throws XNIException {
/*  568 */       if (this.fCurrentTokenIndex == this.fTokenCount) {
/*  569 */         XPointerHandler.this.reportError("XPointerProcessingError", (Object[])null);
/*      */       }
/*  571 */       return this.fTokens[this.fCurrentTokenIndex++];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int peekToken() throws XNIException {
/*  582 */       if (this.fCurrentTokenIndex == this.fTokenCount) {
/*  583 */         XPointerHandler.this.reportError("XPointerProcessingError", (Object[])null);
/*      */       }
/*  585 */       return this.fTokens[this.fCurrentTokenIndex];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String nextTokenAsString() throws XNIException {
/*  597 */       String tokenStrint = getTokenString(nextToken());
/*  598 */       if (tokenStrint == null) {
/*  599 */         XPointerHandler.this.reportError("XPointerProcessingError", (Object[])null);
/*      */       }
/*  601 */       return tokenStrint;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class Scanner
/*      */   {
/*      */     private static final byte CHARTYPE_INVALID = 0;
/*      */ 
/*      */     
/*      */     private static final byte CHARTYPE_OTHER = 1;
/*      */ 
/*      */     
/*      */     private static final byte CHARTYPE_WHITESPACE = 2;
/*      */ 
/*      */     
/*      */     private static final byte CHARTYPE_CARRET = 3;
/*      */ 
/*      */     
/*      */     private static final byte CHARTYPE_OPEN_PAREN = 4;
/*      */     
/*      */     private static final byte CHARTYPE_CLOSE_PAREN = 5;
/*      */     
/*      */     private static final byte CHARTYPE_MINUS = 6;
/*      */     
/*      */     private static final byte CHARTYPE_PERIOD = 7;
/*      */     
/*      */     private static final byte CHARTYPE_SLASH = 8;
/*      */     
/*      */     private static final byte CHARTYPE_DIGIT = 9;
/*      */     
/*      */     private static final byte CHARTYPE_COLON = 10;
/*      */     
/*      */     private static final byte CHARTYPE_EQUAL = 11;
/*      */     
/*      */     private static final byte CHARTYPE_LETTER = 12;
/*      */     
/*      */     private static final byte CHARTYPE_UNDERSCORE = 13;
/*      */     
/*      */     private static final byte CHARTYPE_NONASCII = 14;
/*      */     
/*  643 */     private final byte[] fASCIICharMap = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 1, 1, 1, 1, 1, 4, 5, 1, 1, 1, 6, 7, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 1, 1, 11, 1, 1, 1, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 1, 1, 1, 3, 13, 1, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 1, 1, 1, 1, 1 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private SymbolTable fSymbolTable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Scanner(SymbolTable symbolTable) {
/*  665 */       this.fSymbolTable = symbolTable;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean scanExpr(SymbolTable symbolTable, XPointerHandler.Tokens tokens, String data, int currentOffset, int endOffset) throws XNIException {
/*  678 */       int openParen = 0;
/*  679 */       int closeParen = 0;
/*      */       
/*  681 */       boolean isQName = false;
/*  682 */       String name = null;
/*  683 */       String prefix = null;
/*  684 */       String schemeData = null;
/*  685 */       StringBuffer schemeDataBuff = new StringBuffer();
/*      */ 
/*      */ 
/*      */       
/*  689 */       while (currentOffset != endOffset) {
/*      */ 
/*      */         
/*  692 */         int ch = data.charAt(currentOffset);
/*      */ 
/*      */         
/*  695 */         while ((ch == 32 || ch == 10 || ch == 9 || ch == 13) && 
/*  696 */           ++currentOffset != endOffset)
/*      */         {
/*      */           
/*  699 */           ch = data.charAt(currentOffset);
/*      */         }
/*  701 */         if (currentOffset == endOffset) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  721 */         byte chartype = (ch >= 128) ? 14 : this.fASCIICharMap[ch];
/*      */ 
/*      */         
/*  724 */         switch (chartype) {
/*      */           
/*      */           case 4:
/*  727 */             addToken(tokens, 0);
/*  728 */             openParen++;
/*  729 */             currentOffset++;
/*      */ 
/*      */           
/*      */           case 5:
/*  733 */             addToken(tokens, 1);
/*  734 */             closeParen++;
/*  735 */             currentOffset++;
/*      */ 
/*      */ 
/*      */           
/*      */           case 1:
/*      */           case 2:
/*      */           case 3:
/*      */           case 6:
/*      */           case 7:
/*      */           case 8:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 12:
/*      */           case 13:
/*      */           case 14:
/*  751 */             if (openParen == 0) {
/*  752 */               int nameOffset = currentOffset;
/*  753 */               currentOffset = scanNCName(data, endOffset, currentOffset);
/*      */ 
/*      */               
/*  756 */               if (currentOffset == nameOffset) {
/*  757 */                 XPointerHandler.this.reportError("InvalidShortHandPointer", new Object[] { data });
/*      */                 
/*  759 */                 return false;
/*      */               } 
/*      */               
/*  762 */               if (currentOffset < endOffset) {
/*  763 */                 ch = data.charAt(currentOffset);
/*      */               } else {
/*  765 */                 ch = -1;
/*      */               } 
/*      */               
/*  768 */               name = symbolTable.addSymbol(data.substring(nameOffset, currentOffset));
/*      */               
/*  770 */               prefix = XMLSymbols.EMPTY_STRING;
/*      */ 
/*      */               
/*  773 */               if (ch == 58) {
/*  774 */                 if (++currentOffset == endOffset) {
/*  775 */                   return false;
/*      */                 }
/*      */                 
/*  778 */                 ch = data.charAt(currentOffset);
/*  779 */                 prefix = name;
/*  780 */                 nameOffset = currentOffset;
/*  781 */                 currentOffset = scanNCName(data, endOffset, currentOffset);
/*      */ 
/*      */                 
/*  784 */                 if (currentOffset == nameOffset) {
/*  785 */                   return false;
/*      */                 }
/*      */                 
/*  788 */                 if (currentOffset < endOffset) {
/*  789 */                   ch = data.charAt(currentOffset);
/*      */                 } else {
/*  791 */                   ch = -1;
/*      */                 } 
/*      */                 
/*  794 */                 isQName = true;
/*  795 */                 name = symbolTable.addSymbol(data.substring(nameOffset, currentOffset));
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  800 */               if (currentOffset != endOffset) {
/*  801 */                 addToken(tokens, 3);
/*  802 */                 tokens.addToken(prefix);
/*  803 */                 tokens.addToken(name);
/*  804 */                 isQName = false;
/*  805 */               } else if (currentOffset == endOffset) {
/*      */                 
/*  807 */                 addToken(tokens, 2);
/*  808 */                 tokens.addToken(name);
/*  809 */                 isQName = false;
/*      */               } 
/*      */ 
/*      */               
/*  813 */               closeParen = 0;
/*      */               
/*      */               continue;
/*      */             } 
/*  817 */             if (openParen > 0 && closeParen == 0 && name != null) {
/*      */               
/*  819 */               int dataOffset = currentOffset;
/*  820 */               currentOffset = scanData(data, schemeDataBuff, endOffset, currentOffset);
/*      */ 
/*      */               
/*  823 */               if (currentOffset == dataOffset) {
/*  824 */                 XPointerHandler.this.reportError("InvalidSchemeDataInXPointer", new Object[] { data });
/*      */                 
/*  826 */                 return false;
/*      */               } 
/*      */               
/*  829 */               if (currentOffset < endOffset) {
/*  830 */                 ch = data.charAt(currentOffset);
/*      */               } else {
/*  832 */                 ch = -1;
/*      */               } 
/*      */               
/*  835 */               schemeData = symbolTable.addSymbol(schemeDataBuff
/*  836 */                   .toString());
/*  837 */               addToken(tokens, 4);
/*  838 */               tokens.addToken(schemeData);
/*      */ 
/*      */               
/*  841 */               openParen = 0;
/*  842 */               schemeDataBuff.delete(0, schemeDataBuff.length());
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/*  847 */             return false;
/*      */         } 
/*      */       
/*      */       } 
/*  851 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scanNCName(String data, int endOffset, int currentOffset) {
/*  865 */       int ch = data.charAt(currentOffset);
/*  866 */       if (ch >= 128) {
/*  867 */         if (!XMLChar.isNameStart(ch)) {
/*  868 */           return currentOffset;
/*      */         }
/*      */       } else {
/*  871 */         byte chartype = this.fASCIICharMap[ch];
/*  872 */         if (chartype != 12 && chartype != 13)
/*      */         {
/*  874 */           return currentOffset;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  879 */       while (++currentOffset < endOffset) {
/*  880 */         ch = data.charAt(currentOffset);
/*  881 */         if (ch >= 128) {
/*  882 */           if (!XMLChar.isName(ch))
/*      */             break; 
/*      */           continue;
/*      */         } 
/*  886 */         byte chartype = this.fASCIICharMap[ch];
/*  887 */         if (chartype != 12 && chartype != 9 && chartype != 7 && chartype != 6 && chartype != 13) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  896 */       return currentOffset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scanData(String data, StringBuffer schemeData, int endOffset, int currentOffset) {
/*  911 */       while (currentOffset != endOffset) {
/*      */ 
/*      */ 
/*      */         
/*  915 */         int ch = data.charAt(currentOffset);
/*  916 */         byte chartype = (ch >= 128) ? 14 : this.fASCIICharMap[ch];
/*      */ 
/*      */         
/*  919 */         if (chartype == 4) {
/*  920 */           schemeData.append(ch);
/*      */           
/*  922 */           currentOffset = scanData(data, schemeData, endOffset, ++currentOffset);
/*      */           
/*  924 */           if (currentOffset == endOffset) {
/*  925 */             return currentOffset;
/*      */           }
/*      */           
/*  928 */           ch = data.charAt(currentOffset);
/*  929 */           chartype = (ch >= 128) ? 14 : this.fASCIICharMap[ch];
/*      */ 
/*      */           
/*  932 */           if (chartype != 5) {
/*  933 */             return endOffset;
/*      */           }
/*  935 */           schemeData.append((char)ch);
/*  936 */           currentOffset++; continue;
/*      */         } 
/*  938 */         if (chartype == 5) {
/*  939 */           return currentOffset;
/*      */         }
/*  941 */         if (chartype == 3) {
/*  942 */           ch = data.charAt(++currentOffset);
/*  943 */           chartype = (ch >= 128) ? 14 : this.fASCIICharMap[ch];
/*      */ 
/*      */           
/*  946 */           if (chartype != 3 && chartype != 4 && chartype != 5) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  951 */           schemeData.append((char)ch);
/*  952 */           currentOffset++;
/*      */           continue;
/*      */         } 
/*  955 */         schemeData.append((char)ch);
/*  956 */         currentOffset++;
/*      */       } 
/*      */ 
/*      */       
/*  960 */       return currentOffset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void addToken(XPointerHandler.Tokens tokens, int token) throws XNIException {
/*  976 */       tokens.addToken(token);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void comment(XMLString text, Augmentations augs) throws XNIException {
/*  994 */     if (!isChildFragmentResolved()) {
/*      */       return;
/*      */     }
/*  997 */     super.comment(text, augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processingInstruction(String target, XMLString data, Augmentations augs) throws XNIException {
/* 1020 */     if (!isChildFragmentResolved()) {
/*      */       return;
/*      */     }
/* 1023 */     super.processingInstruction(target, data, augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 1038 */     if (!resolveXPointer(element, attributes, augs, 0)) {
/*      */ 
/*      */ 
/*      */       
/* 1042 */       if (this.fFixupBase) {
/* 1043 */         processXMLBaseAttributes(attributes);
/*      */       }
/* 1045 */       if (this.fFixupLang) {
/* 1046 */         processXMLLangAttributes(attributes);
/*      */       }
/*      */ 
/*      */       
/* 1050 */       this.fNamespaceContext.setContextInvalid();
/*      */       
/*      */       return;
/*      */     } 
/* 1054 */     super.startElement(element, attributes, augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 1069 */     if (!resolveXPointer(element, attributes, augs, 2)) {
/*      */ 
/*      */       
/* 1072 */       if (this.fFixupBase) {
/* 1073 */         processXMLBaseAttributes(attributes);
/*      */       }
/* 1075 */       if (this.fFixupLang) {
/* 1076 */         processXMLLangAttributes(attributes);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1081 */       this.fNamespaceContext.setContextInvalid();
/*      */       return;
/*      */     } 
/* 1084 */     super.emptyElement(element, attributes, augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void characters(XMLString text, Augmentations augs) throws XNIException {
/* 1098 */     if (!isChildFragmentResolved()) {
/*      */       return;
/*      */     }
/* 1101 */     super.characters(text, augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
/* 1120 */     if (!isChildFragmentResolved()) {
/*      */       return;
/*      */     }
/* 1123 */     super.ignorableWhitespace(text, augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement(QName element, Augmentations augs) throws XNIException {
/* 1137 */     if (!resolveXPointer(element, (XMLAttributes)null, augs, 1)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1143 */     super.endElement(element, augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startCDATA(Augmentations augs) throws XNIException {
/* 1155 */     if (!isChildFragmentResolved()) {
/*      */       return;
/*      */     }
/* 1158 */     super.startCDATA(augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endCDATA(Augmentations augs) throws XNIException {
/* 1170 */     if (!isChildFragmentResolved()) {
/*      */       return;
/*      */     }
/* 1173 */     super.endCDATA(augs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/* 1200 */     if (propertyId == "http://apache.org/xml/properties/internal/error-reporter")
/*      */     {
/* 1202 */       if (value != null) {
/* 1203 */         this.fXPointerErrorReporter = (XMLErrorReporter)value;
/*      */       } else {
/* 1205 */         this.fXPointerErrorReporter = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1210 */     if (propertyId == "http://apache.org/xml/properties/internal/error-handler")
/*      */     {
/* 1212 */       if (value != null) {
/* 1213 */         this.fErrorHandler = (XMLErrorHandler)value;
/*      */       } else {
/* 1215 */         this.fErrorHandler = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1220 */     if (propertyId == "http://apache.org/xml/features/xinclude/fixup-language")
/*      */     {
/* 1222 */       if (value != null) {
/* 1223 */         this.fFixupLang = ((Boolean)value).booleanValue();
/*      */       } else {
/* 1225 */         this.fFixupLang = false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1230 */     if (propertyId == "http://apache.org/xml/features/xinclude/fixup-base-uris")
/*      */     {
/* 1232 */       if (value != null) {
/* 1233 */         this.fFixupBase = ((Boolean)value).booleanValue();
/*      */       } else {
/* 1235 */         this.fFixupBase = false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1240 */     if (propertyId == "http://apache.org/xml/properties/internal/namespace-context")
/*      */     {
/* 1242 */       this.fNamespaceContext = (XIncludeNamespaceSupport)value;
/*      */     }
/*      */     
/* 1245 */     super.setProperty(propertyId, value);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xpointer/XPointerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */