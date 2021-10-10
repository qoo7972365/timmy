/*      */ package com.sun.org.apache.xpath.internal.compiler;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*      */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*      */ import com.sun.org.apache.xpath.internal.XPathProcessorException;
/*      */ import com.sun.org.apache.xpath.internal.domapi.XPathStylesheetDOM3Exception;
/*      */ import com.sun.org.apache.xpath.internal.objects.XNumber;
/*      */ import com.sun.org.apache.xpath.internal.objects.XString;
/*      */ import javax.xml.transform.ErrorListener;
/*      */ import javax.xml.transform.SourceLocator;
/*      */ import javax.xml.transform.TransformerException;
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
/*      */ public class XPathParser
/*      */ {
/*      */   public static final String CONTINUE_AFTER_FATAL_ERROR = "CONTINUE_AFTER_FATAL_ERROR";
/*      */   private OpMap m_ops;
/*      */   transient String m_token;
/*   63 */   transient char m_tokenChar = Character.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   int m_queueMark = 0;
/*      */   
/*      */   protected static final int FILTER_MATCH_FAILED = 0;
/*      */   
/*      */   protected static final int FILTER_MATCH_PRIMARY = 1;
/*      */   
/*      */   protected static final int FILTER_MATCH_PREDICATES = 2;
/*      */   
/*      */   private int countPredicate;
/*      */   
/*      */   PrefixResolver m_namespaceContext;
/*      */   
/*      */   private ErrorListener m_errorListener;
/*      */   SourceLocator m_sourceLocator;
/*      */   private FunctionTable m_functionTable;
/*      */   
/*      */   public XPathParser(ErrorListener errorListener, SourceLocator sourceLocator) {
/*   85 */     this.m_errorListener = errorListener;
/*   86 */     this.m_sourceLocator = sourceLocator;
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
/*      */   public void initXPath(Compiler compiler, String expression, PrefixResolver namespaceContext) throws TransformerException {
/*  111 */     this.m_ops = compiler;
/*  112 */     this.m_namespaceContext = namespaceContext;
/*  113 */     this.m_functionTable = compiler.getFunctionTable();
/*      */     
/*  115 */     Lexer lexer = new Lexer(compiler, namespaceContext, this);
/*      */     
/*  117 */     lexer.tokenize(expression);
/*      */     
/*  119 */     this.m_ops.setOp(0, 1);
/*  120 */     this.m_ops.setOp(1, 2);
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
/*      */     try {
/*  133 */       nextToken();
/*  134 */       Expr();
/*      */       
/*  136 */       if (null != this.m_token)
/*      */       {
/*  138 */         String extraTokens = "";
/*      */         
/*  140 */         while (null != this.m_token) {
/*      */           
/*  142 */           extraTokens = extraTokens + "'" + this.m_token + "'";
/*      */           
/*  144 */           nextToken();
/*      */           
/*  146 */           if (null != this.m_token) {
/*  147 */             extraTokens = extraTokens + ", ";
/*      */           }
/*      */         } 
/*  150 */         error("ER_EXTRA_ILLEGAL_TOKENS", new Object[] { extraTokens });
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  155 */     catch (XPathProcessorException e) {
/*      */       
/*  157 */       if ("CONTINUE_AFTER_FATAL_ERROR".equals(e.getMessage()))
/*      */       
/*      */       { 
/*      */ 
/*      */         
/*  162 */         initXPath(compiler, "/..", namespaceContext); }
/*      */       else
/*      */       
/*  165 */       { throw e; } 
/*  166 */     } catch (StackOverflowError sof) {
/*  167 */       error("ER_PREDICATE_TOO_MANY_OPEN", new Object[] { this.m_token, 
/*  168 */             Integer.valueOf(this.m_queueMark), Integer.valueOf(this.countPredicate) });
/*      */     } 
/*      */     
/*  171 */     compiler.shrink();
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
/*      */   public void initMatchPattern(Compiler compiler, String expression, PrefixResolver namespaceContext) throws TransformerException {
/*  190 */     this.m_ops = compiler;
/*  191 */     this.m_namespaceContext = namespaceContext;
/*  192 */     this.m_functionTable = compiler.getFunctionTable();
/*      */     
/*  194 */     Lexer lexer = new Lexer(compiler, namespaceContext, this);
/*      */     
/*  196 */     lexer.tokenize(expression);
/*      */     
/*  198 */     this.m_ops.setOp(0, 30);
/*  199 */     this.m_ops.setOp(1, 2);
/*      */     
/*  201 */     nextToken();
/*      */     try {
/*  203 */       Pattern();
/*  204 */     } catch (StackOverflowError sof) {
/*  205 */       error("ER_PREDICATE_TOO_MANY_OPEN", new Object[] { this.m_token, 
/*  206 */             Integer.valueOf(this.m_queueMark), Integer.valueOf(this.countPredicate) });
/*      */     } 
/*      */     
/*  209 */     if (null != this.m_token) {
/*      */       
/*  211 */       String extraTokens = "";
/*      */       
/*  213 */       while (null != this.m_token) {
/*      */         
/*  215 */         extraTokens = extraTokens + "'" + this.m_token + "'";
/*      */         
/*  217 */         nextToken();
/*      */         
/*  219 */         if (null != this.m_token) {
/*  220 */           extraTokens = extraTokens + ", ";
/*      */         }
/*      */       } 
/*  223 */       error("ER_EXTRA_ILLEGAL_TOKENS", new Object[] { extraTokens });
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  228 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/*  229 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */     
/*  231 */     this.m_ops.shrink();
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
/*      */   public void setErrorHandler(ErrorListener handler) {
/*  254 */     this.m_errorListener = handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ErrorListener getErrorListener() {
/*  264 */     return this.m_errorListener;
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
/*      */   final boolean tokenIs(String s) {
/*  277 */     return (this.m_token != null) ? this.m_token.equals(s) : ((s == null));
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
/*      */   final boolean tokenIs(char c) {
/*  290 */     return (this.m_token != null) ? ((this.m_tokenChar == c)) : false;
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
/*      */   final boolean lookahead(char c, int n) {
/*      */     boolean b;
/*  306 */     int pos = this.m_queueMark + n;
/*      */ 
/*      */     
/*  309 */     if (pos <= this.m_ops.getTokenQueueSize() && pos > 0 && this.m_ops
/*  310 */       .getTokenQueueSize() != 0) {
/*      */       
/*  312 */       String tok = (String)this.m_ops.m_tokenQueue.elementAt(pos - 1);
/*      */       
/*  314 */       b = (tok.length() == 1) ? ((tok.charAt(0) == c)) : false;
/*      */     }
/*      */     else {
/*      */       
/*  318 */       b = false;
/*      */     } 
/*      */     
/*  321 */     return b;
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
/*      */   private final boolean lookbehind(char c, int n) {
/*      */     boolean isToken;
/*  342 */     int lookBehindPos = this.m_queueMark - n + 1;
/*      */     
/*  344 */     if (lookBehindPos >= 0) {
/*      */       
/*  346 */       String lookbehind = (String)this.m_ops.m_tokenQueue.elementAt(lookBehindPos);
/*      */       
/*  348 */       if (lookbehind.length() == 1)
/*      */       {
/*  350 */         char c0 = (lookbehind == null) ? '|' : lookbehind.charAt(0);
/*      */         
/*  352 */         isToken = (c0 == '|') ? false : ((c0 == c));
/*      */       }
/*      */       else
/*      */       {
/*  356 */         isToken = false;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  361 */       isToken = false;
/*      */     } 
/*      */     
/*  364 */     return isToken;
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
/*      */   private final boolean lookbehindHasToken(int n) {
/*      */     boolean hasToken;
/*  384 */     if (this.m_queueMark - n > 0) {
/*      */       
/*  386 */       String lookbehind = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark - n - 1);
/*  387 */       char c0 = (lookbehind == null) ? '|' : lookbehind.charAt(0);
/*      */       
/*  389 */       hasToken = !(c0 == '|');
/*      */     }
/*      */     else {
/*      */       
/*  393 */       hasToken = false;
/*      */     } 
/*      */     
/*  396 */     return hasToken;
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
/*      */   private final boolean lookahead(String s, int n) {
/*      */     boolean isToken;
/*  415 */     if (this.m_queueMark + n <= this.m_ops.getTokenQueueSize()) {
/*      */       
/*  417 */       String lookahead = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark + n - 1);
/*      */       
/*  419 */       isToken = (lookahead != null) ? lookahead.equals(s) : ((s == null));
/*      */     }
/*      */     else {
/*      */       
/*  423 */       isToken = (null == s);
/*      */     } 
/*      */     
/*  426 */     return isToken;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void nextToken() {
/*  436 */     if (this.m_queueMark < this.m_ops.getTokenQueueSize()) {
/*      */       
/*  438 */       this.m_token = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark++);
/*  439 */       this.m_tokenChar = this.m_token.charAt(0);
/*      */     }
/*      */     else {
/*      */       
/*  443 */       this.m_token = null;
/*  444 */       this.m_tokenChar = Character.MIN_VALUE;
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
/*      */   private final String getTokenRelative(int i) {
/*      */     String tok;
/*  460 */     int relative = this.m_queueMark + i;
/*      */     
/*  462 */     if (relative > 0 && relative < this.m_ops.getTokenQueueSize()) {
/*      */       
/*  464 */       tok = (String)this.m_ops.m_tokenQueue.elementAt(relative);
/*      */     }
/*      */     else {
/*      */       
/*  468 */       tok = null;
/*      */     } 
/*      */     
/*  471 */     return tok;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void prevToken() {
/*  481 */     if (this.m_queueMark > 0) {
/*      */       
/*  483 */       this.m_queueMark--;
/*      */       
/*  485 */       this.m_token = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark);
/*  486 */       this.m_tokenChar = this.m_token.charAt(0);
/*      */     }
/*      */     else {
/*      */       
/*  490 */       this.m_token = null;
/*  491 */       this.m_tokenChar = Character.MIN_VALUE;
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
/*      */   private final void consumeExpected(String expected) throws TransformerException {
/*  507 */     if (tokenIs(expected)) {
/*      */       
/*  509 */       nextToken();
/*      */     }
/*      */     else {
/*      */       
/*  513 */       error("ER_EXPECTED_BUT_FOUND", new Object[] { expected, this.m_token });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  519 */       throw new XPathProcessorException("CONTINUE_AFTER_FATAL_ERROR");
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
/*      */   private final void consumeExpected(char expected) throws TransformerException {
/*  535 */     if (tokenIs(expected)) {
/*      */       
/*  537 */       nextToken();
/*      */     }
/*      */     else {
/*      */       
/*  541 */       error("ER_EXPECTED_BUT_FOUND", new Object[] {
/*  542 */             String.valueOf(expected), this.m_token
/*      */           });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  548 */       throw new XPathProcessorException("CONTINUE_AFTER_FATAL_ERROR");
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
/*      */   
/*      */   void warn(String msg, Object[] args) throws TransformerException {
/*  567 */     String fmsg = XSLMessages.createXPATHWarning(msg, args);
/*  568 */     ErrorListener ehandler = getErrorListener();
/*      */     
/*  570 */     if (null != ehandler) {
/*      */ 
/*      */       
/*  573 */       ehandler.warning(new TransformerException(fmsg, this.m_sourceLocator));
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  578 */       System.err.println(fmsg);
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
/*      */   private void assertion(boolean b, String msg) {
/*  594 */     if (!b) {
/*      */       
/*  596 */       String fMsg = XSLMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { msg });
/*      */ 
/*      */ 
/*      */       
/*  600 */       throw new RuntimeException(fMsg);
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
/*      */ 
/*      */   
/*      */   void error(String msg, Object[] args) throws TransformerException {
/*  620 */     String fmsg = XSLMessages.createXPATHMessage(msg, args);
/*  621 */     ErrorListener ehandler = getErrorListener();
/*      */     
/*  623 */     TransformerException te = new TransformerException(fmsg, this.m_sourceLocator);
/*  624 */     if (null != ehandler) {
/*      */ 
/*      */       
/*  627 */       ehandler.fatalError(te);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  632 */       throw te;
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
/*      */   void errorForDOM3(String msg, Object[] args) throws TransformerException {
/*  665 */     String fmsg = XSLMessages.createXPATHMessage(msg, args);
/*  666 */     ErrorListener ehandler = getErrorListener();
/*      */     
/*  668 */     XPathStylesheetDOM3Exception xPathStylesheetDOM3Exception = new XPathStylesheetDOM3Exception(fmsg, this.m_sourceLocator);
/*  669 */     if (null != ehandler) {
/*      */ 
/*      */       
/*  672 */       ehandler.fatalError((TransformerException)xPathStylesheetDOM3Exception);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  677 */       throw xPathStylesheetDOM3Exception;
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
/*      */   protected String dumpRemainingTokenQueue() {
/*      */     String returnMsg;
/*  690 */     int q = this.m_queueMark;
/*      */ 
/*      */     
/*  693 */     if (q < this.m_ops.getTokenQueueSize()) {
/*      */       
/*  695 */       String msg = "\n Remaining tokens: (";
/*      */       
/*  697 */       while (q < this.m_ops.getTokenQueueSize()) {
/*      */         
/*  699 */         String t = (String)this.m_ops.m_tokenQueue.elementAt(q++);
/*      */         
/*  701 */         msg = msg + " '" + t + "'";
/*      */       } 
/*      */       
/*  704 */       returnMsg = msg + ")";
/*      */     }
/*      */     else {
/*      */       
/*  708 */       returnMsg = "";
/*      */     } 
/*      */     
/*  711 */     return returnMsg;
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
/*      */   final int getFunctionToken(String key) {
/*      */     int tok;
/*      */     try {
/*  734 */       Object id = Keywords.lookupNodeTest(key);
/*  735 */       if (null == id) id = this.m_functionTable.getFunctionID(key); 
/*  736 */       tok = ((Integer)id).intValue();
/*      */     }
/*  738 */     catch (NullPointerException npe) {
/*      */       
/*  740 */       tok = -1;
/*      */     }
/*  742 */     catch (ClassCastException cce) {
/*      */       
/*  744 */       tok = -1;
/*      */     } 
/*      */     
/*  747 */     return tok;
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
/*      */   void insertOp(int pos, int length, int op) {
/*  762 */     int totalLen = this.m_ops.getOp(1);
/*      */     
/*  764 */     for (int i = totalLen - 1; i >= pos; i--)
/*      */     {
/*  766 */       this.m_ops.setOp(i + length, this.m_ops.getOp(i));
/*      */     }
/*      */     
/*  769 */     this.m_ops.setOp(pos, op);
/*  770 */     this.m_ops.setOp(1, totalLen + length);
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
/*      */   void appendOp(int length, int op) {
/*  784 */     int totalLen = this.m_ops.getOp(1);
/*      */     
/*  786 */     this.m_ops.setOp(totalLen, op);
/*  787 */     this.m_ops.setOp(totalLen + 1, length);
/*  788 */     this.m_ops.setOp(1, totalLen + length);
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
/*      */   protected void Expr() throws TransformerException {
/*  803 */     OrExpr();
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
/*      */   protected void OrExpr() throws TransformerException {
/*  818 */     int opPos = this.m_ops.getOp(1);
/*      */     
/*  820 */     AndExpr();
/*      */     
/*  822 */     if (null != this.m_token && tokenIs("or")) {
/*      */       
/*  824 */       nextToken();
/*  825 */       insertOp(opPos, 2, 2);
/*  826 */       OrExpr();
/*      */       
/*  828 */       this.m_ops.setOp(opPos + 1, this.m_ops
/*  829 */           .getOp(1) - opPos);
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
/*      */   protected void AndExpr() throws TransformerException {
/*  845 */     int opPos = this.m_ops.getOp(1);
/*      */     
/*  847 */     EqualityExpr(-1);
/*      */     
/*  849 */     if (null != this.m_token && tokenIs("and")) {
/*      */       
/*  851 */       nextToken();
/*  852 */       insertOp(opPos, 2, 3);
/*  853 */       AndExpr();
/*      */       
/*  855 */       this.m_ops.setOp(opPos + 1, this.m_ops
/*  856 */           .getOp(1) - opPos);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int EqualityExpr(int addPos) throws TransformerException {
/*  878 */     int opPos = this.m_ops.getOp(1);
/*      */     
/*  880 */     if (-1 == addPos) {
/*  881 */       addPos = opPos;
/*      */     }
/*  883 */     RelationalExpr(-1);
/*      */     
/*  885 */     if (null != this.m_token)
/*      */     {
/*  887 */       if (tokenIs('!') && lookahead('=', 1)) {
/*      */         
/*  889 */         nextToken();
/*  890 */         nextToken();
/*  891 */         insertOp(addPos, 2, 4);
/*      */         
/*  893 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/*  895 */         addPos = EqualityExpr(addPos);
/*  896 */         this.m_ops.setOp(addPos + 1, this.m_ops
/*  897 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  898 */         addPos += 2;
/*      */       }
/*  900 */       else if (tokenIs('=')) {
/*      */         
/*  902 */         nextToken();
/*  903 */         insertOp(addPos, 2, 5);
/*      */         
/*  905 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/*  907 */         addPos = EqualityExpr(addPos);
/*  908 */         this.m_ops.setOp(addPos + 1, this.m_ops
/*  909 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  910 */         addPos += 2;
/*      */       } 
/*      */     }
/*      */     
/*  914 */     return addPos;
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
/*      */   protected int RelationalExpr(int addPos) throws TransformerException {
/*  938 */     int opPos = this.m_ops.getOp(1);
/*      */     
/*  940 */     if (-1 == addPos) {
/*  941 */       addPos = opPos;
/*      */     }
/*  943 */     AdditiveExpr(-1);
/*      */     
/*  945 */     if (null != this.m_token)
/*      */     {
/*  947 */       if (tokenIs('<')) {
/*      */         
/*  949 */         nextToken();
/*      */         
/*  951 */         if (tokenIs('=')) {
/*      */           
/*  953 */           nextToken();
/*  954 */           insertOp(addPos, 2, 6);
/*      */         }
/*      */         else {
/*      */           
/*  958 */           insertOp(addPos, 2, 7);
/*      */         } 
/*      */         
/*  961 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/*  963 */         addPos = RelationalExpr(addPos);
/*  964 */         this.m_ops.setOp(addPos + 1, this.m_ops
/*  965 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  966 */         addPos += 2;
/*      */       }
/*  968 */       else if (tokenIs('>')) {
/*      */         
/*  970 */         nextToken();
/*      */         
/*  972 */         if (tokenIs('=')) {
/*      */           
/*  974 */           nextToken();
/*  975 */           insertOp(addPos, 2, 8);
/*      */         }
/*      */         else {
/*      */           
/*  979 */           insertOp(addPos, 2, 9);
/*      */         } 
/*      */         
/*  982 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/*  984 */         addPos = RelationalExpr(addPos);
/*  985 */         this.m_ops.setOp(addPos + 1, this.m_ops
/*  986 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/*  987 */         addPos += 2;
/*      */       } 
/*      */     }
/*      */     
/*  991 */     return addPos;
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
/*      */   protected int AdditiveExpr(int addPos) throws TransformerException {
/* 1013 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1015 */     if (-1 == addPos) {
/* 1016 */       addPos = opPos;
/*      */     }
/* 1018 */     MultiplicativeExpr(-1);
/*      */     
/* 1020 */     if (null != this.m_token)
/*      */     {
/* 1022 */       if (tokenIs('+')) {
/*      */         
/* 1024 */         nextToken();
/* 1025 */         insertOp(addPos, 2, 10);
/*      */         
/* 1027 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/* 1029 */         addPos = AdditiveExpr(addPos);
/* 1030 */         this.m_ops.setOp(addPos + 1, this.m_ops
/* 1031 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/* 1032 */         addPos += 2;
/*      */       }
/* 1034 */       else if (tokenIs('-')) {
/*      */         
/* 1036 */         nextToken();
/* 1037 */         insertOp(addPos, 2, 11);
/*      */         
/* 1039 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/* 1041 */         addPos = AdditiveExpr(addPos);
/* 1042 */         this.m_ops.setOp(addPos + 1, this.m_ops
/* 1043 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/* 1044 */         addPos += 2;
/*      */       } 
/*      */     }
/*      */     
/* 1048 */     return addPos;
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
/*      */   protected int MultiplicativeExpr(int addPos) throws TransformerException {
/* 1071 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1073 */     if (-1 == addPos) {
/* 1074 */       addPos = opPos;
/*      */     }
/* 1076 */     UnaryExpr();
/*      */     
/* 1078 */     if (null != this.m_token)
/*      */     {
/* 1080 */       if (tokenIs('*')) {
/*      */         
/* 1082 */         nextToken();
/* 1083 */         insertOp(addPos, 2, 12);
/*      */         
/* 1085 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/* 1087 */         addPos = MultiplicativeExpr(addPos);
/* 1088 */         this.m_ops.setOp(addPos + 1, this.m_ops
/* 1089 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/* 1090 */         addPos += 2;
/*      */       }
/* 1092 */       else if (tokenIs("div")) {
/*      */         
/* 1094 */         nextToken();
/* 1095 */         insertOp(addPos, 2, 13);
/*      */         
/* 1097 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/* 1099 */         addPos = MultiplicativeExpr(addPos);
/* 1100 */         this.m_ops.setOp(addPos + 1, this.m_ops
/* 1101 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/* 1102 */         addPos += 2;
/*      */       }
/* 1104 */       else if (tokenIs("mod")) {
/*      */         
/* 1106 */         nextToken();
/* 1107 */         insertOp(addPos, 2, 14);
/*      */         
/* 1109 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/* 1111 */         addPos = MultiplicativeExpr(addPos);
/* 1112 */         this.m_ops.setOp(addPos + 1, this.m_ops
/* 1113 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/* 1114 */         addPos += 2;
/*      */       }
/* 1116 */       else if (tokenIs("quo")) {
/*      */         
/* 1118 */         nextToken();
/* 1119 */         insertOp(addPos, 2, 15);
/*      */         
/* 1121 */         int opPlusLeftHandLen = this.m_ops.getOp(1) - addPos;
/*      */         
/* 1123 */         addPos = MultiplicativeExpr(addPos);
/* 1124 */         this.m_ops.setOp(addPos + 1, this.m_ops
/* 1125 */             .getOp(addPos + opPlusLeftHandLen + 1) + opPlusLeftHandLen);
/* 1126 */         addPos += 2;
/*      */       } 
/*      */     }
/*      */     
/* 1130 */     return addPos;
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
/*      */   protected void UnaryExpr() throws TransformerException {
/* 1144 */     int opPos = this.m_ops.getOp(1);
/* 1145 */     boolean isNeg = false;
/*      */     
/* 1147 */     if (this.m_tokenChar == '-') {
/*      */       
/* 1149 */       nextToken();
/* 1150 */       appendOp(2, 16);
/*      */       
/* 1152 */       isNeg = true;
/*      */     } 
/*      */     
/* 1155 */     UnionExpr();
/*      */     
/* 1157 */     if (isNeg) {
/* 1158 */       this.m_ops.setOp(opPos + 1, this.m_ops
/* 1159 */           .getOp(1) - opPos);
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
/*      */   protected void StringExpr() throws TransformerException {
/* 1172 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1174 */     appendOp(2, 17);
/* 1175 */     Expr();
/*      */     
/* 1177 */     this.m_ops.setOp(opPos + 1, this.m_ops
/* 1178 */         .getOp(1) - opPos);
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
/*      */   protected void BooleanExpr() throws TransformerException {
/* 1192 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1194 */     appendOp(2, 18);
/* 1195 */     Expr();
/*      */     
/* 1197 */     int opLen = this.m_ops.getOp(1) - opPos;
/*      */     
/* 1199 */     if (opLen == 2)
/*      */     {
/* 1201 */       error("ER_BOOLEAN_ARG_NO_LONGER_OPTIONAL", null);
/*      */     }
/*      */     
/* 1204 */     this.m_ops.setOp(opPos + 1, opLen);
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
/*      */   protected void NumberExpr() throws TransformerException {
/* 1218 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1220 */     appendOp(2, 19);
/* 1221 */     Expr();
/*      */     
/* 1223 */     this.m_ops.setOp(opPos + 1, this.m_ops
/* 1224 */         .getOp(1) - opPos);
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
/*      */   protected void UnionExpr() throws TransformerException {
/* 1243 */     int opPos = this.m_ops.getOp(1);
/* 1244 */     boolean continueOrLoop = true;
/* 1245 */     boolean foundUnion = false;
/*      */ 
/*      */     
/*      */     while (true) {
/* 1249 */       PathExpr();
/*      */       
/* 1251 */       if (tokenIs('|'))
/*      */       
/* 1253 */       { if (false == foundUnion) {
/*      */           
/* 1255 */           foundUnion = true;
/*      */           
/* 1257 */           insertOp(opPos, 2, 20);
/*      */         } 
/*      */         
/* 1260 */         nextToken();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1269 */         if (!continueOrLoop)
/*      */           break;  continue; }  break;
/* 1271 */     }  this.m_ops.setOp(opPos + 1, this.m_ops
/* 1272 */         .getOp(1) - opPos);
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
/*      */   protected void PathExpr() throws TransformerException {
/* 1289 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1291 */     int filterExprMatch = FilterExpr();
/*      */     
/* 1293 */     if (filterExprMatch != 0) {
/*      */ 
/*      */ 
/*      */       
/* 1297 */       boolean locationPathStarted = (filterExprMatch == 2);
/*      */       
/* 1299 */       if (tokenIs('/')) {
/*      */         
/* 1301 */         nextToken();
/*      */         
/* 1303 */         if (!locationPathStarted) {
/*      */ 
/*      */           
/* 1306 */           insertOp(opPos, 2, 28);
/*      */           
/* 1308 */           locationPathStarted = true;
/*      */         } 
/*      */         
/* 1311 */         if (!RelativeLocationPath())
/*      */         {
/*      */           
/* 1314 */           error("ER_EXPECTED_REL_LOC_PATH", null);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1320 */       if (locationPathStarted)
/*      */       {
/* 1322 */         this.m_ops.setOp(this.m_ops.getOp(1), -1);
/* 1323 */         this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1324 */         this.m_ops.setOp(opPos + 1, this.m_ops
/* 1325 */             .getOp(1) - opPos);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1330 */       LocationPath();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int FilterExpr() throws TransformerException {
/* 1354 */     int filterMatch, opPos = this.m_ops.getOp(1);
/*      */ 
/*      */ 
/*      */     
/* 1358 */     if (PrimaryExpr()) {
/*      */       
/* 1360 */       if (tokenIs('['))
/*      */       {
/*      */ 
/*      */         
/* 1364 */         insertOp(opPos, 2, 28);
/*      */         
/* 1366 */         while (tokenIs('['))
/*      */         {
/* 1368 */           Predicate();
/*      */         }
/*      */         
/* 1371 */         filterMatch = 2;
/*      */       }
/*      */       else
/*      */       {
/* 1375 */         filterMatch = 1;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1380 */       filterMatch = 0;
/*      */     } 
/*      */     
/* 1383 */     return filterMatch;
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
/*      */   protected boolean PrimaryExpr() throws TransformerException {
/*      */     boolean matchFound;
/* 1411 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1413 */     if (this.m_tokenChar == '\'' || this.m_tokenChar == '"') {
/*      */       
/* 1415 */       appendOp(2, 21);
/* 1416 */       Literal();
/*      */       
/* 1418 */       this.m_ops.setOp(opPos + 1, this.m_ops
/* 1419 */           .getOp(1) - opPos);
/*      */       
/* 1421 */       matchFound = true;
/*      */     }
/* 1423 */     else if (this.m_tokenChar == '$') {
/*      */       
/* 1425 */       nextToken();
/* 1426 */       appendOp(2, 22);
/* 1427 */       QName();
/*      */       
/* 1429 */       this.m_ops.setOp(opPos + 1, this.m_ops
/* 1430 */           .getOp(1) - opPos);
/*      */       
/* 1432 */       matchFound = true;
/*      */     }
/* 1434 */     else if (this.m_tokenChar == '(') {
/*      */       
/* 1436 */       nextToken();
/* 1437 */       appendOp(2, 23);
/* 1438 */       Expr();
/* 1439 */       consumeExpected(')');
/*      */       
/* 1441 */       this.m_ops.setOp(opPos + 1, this.m_ops
/* 1442 */           .getOp(1) - opPos);
/*      */       
/* 1444 */       matchFound = true;
/*      */     }
/* 1446 */     else if (null != this.m_token && (('.' == this.m_tokenChar && this.m_token.length() > 1 && Character.isDigit(this.m_token
/* 1447 */         .charAt(1))) || Character.isDigit(this.m_tokenChar))) {
/*      */       
/* 1449 */       appendOp(2, 27);
/* 1450 */       Number();
/*      */       
/* 1452 */       this.m_ops.setOp(opPos + 1, this.m_ops
/* 1453 */           .getOp(1) - opPos);
/*      */       
/* 1455 */       matchFound = true;
/*      */     }
/* 1457 */     else if (lookahead('(', 1) || (lookahead(':', 1) && lookahead('(', 3))) {
/*      */       
/* 1459 */       matchFound = FunctionCall();
/*      */     }
/*      */     else {
/*      */       
/* 1463 */       matchFound = false;
/*      */     } 
/*      */     
/* 1466 */     return matchFound;
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
/*      */   protected void Argument() throws TransformerException {
/* 1479 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1481 */     appendOp(2, 26);
/* 1482 */     Expr();
/*      */     
/* 1484 */     this.m_ops.setOp(opPos + 1, this.m_ops
/* 1485 */         .getOp(1) - opPos);
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
/*      */   protected boolean FunctionCall() throws TransformerException {
/* 1499 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1501 */     if (lookahead(':', 1)) {
/*      */       
/* 1503 */       appendOp(4, 24);
/*      */       
/* 1505 */       this.m_ops.setOp(opPos + 1 + 1, this.m_queueMark - 1);
/*      */       
/* 1507 */       nextToken();
/* 1508 */       consumeExpected(':');
/*      */       
/* 1510 */       this.m_ops.setOp(opPos + 1 + 2, this.m_queueMark - 1);
/*      */       
/* 1512 */       nextToken();
/*      */     }
/*      */     else {
/*      */       
/* 1516 */       int funcTok = getFunctionToken(this.m_token);
/*      */       
/* 1518 */       if (-1 == funcTok)
/*      */       {
/* 1520 */         error("ER_COULDNOT_FIND_FUNCTION", new Object[] { this.m_token });
/*      */       }
/*      */ 
/*      */       
/* 1524 */       switch (funcTok) {
/*      */ 
/*      */         
/*      */         case 1030:
/*      */         case 1031:
/*      */         case 1032:
/*      */         case 1033:
/* 1531 */           return false;
/*      */       } 
/* 1533 */       appendOp(3, 25);
/*      */       
/* 1535 */       this.m_ops.setOp(opPos + 1 + 1, funcTok);
/*      */ 
/*      */       
/* 1538 */       nextToken();
/*      */     } 
/*      */     
/* 1541 */     consumeExpected('(');
/*      */     
/* 1543 */     while (!tokenIs(')') && this.m_token != null) {
/*      */       
/* 1545 */       if (tokenIs(','))
/*      */       {
/* 1547 */         error("ER_FOUND_COMMA_BUT_NO_PRECEDING_ARG", null);
/*      */       }
/*      */       
/* 1550 */       Argument();
/*      */       
/* 1552 */       if (!tokenIs(')')) {
/*      */         
/* 1554 */         consumeExpected(',');
/*      */         
/* 1556 */         if (tokenIs(')'))
/*      */         {
/* 1558 */           error("ER_FOUND_COMMA_BUT_NO_FOLLOWING_ARG", null);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1564 */     consumeExpected(')');
/*      */ 
/*      */     
/* 1567 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/* 1568 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1569 */     this.m_ops.setOp(opPos + 1, this.m_ops
/* 1570 */         .getOp(1) - opPos);
/*      */     
/* 1572 */     return true;
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
/*      */   protected void LocationPath() throws TransformerException {
/* 1588 */     int opPos = this.m_ops.getOp(1);
/*      */ 
/*      */     
/* 1591 */     appendOp(2, 28);
/*      */     
/* 1593 */     boolean seenSlash = tokenIs('/');
/*      */     
/* 1595 */     if (seenSlash) {
/*      */       
/* 1597 */       appendOp(4, 50);
/*      */ 
/*      */       
/* 1600 */       this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/* 1601 */       this.m_ops.setOp(this.m_ops.getOp(1) - 1, 35);
/*      */       
/* 1603 */       nextToken();
/* 1604 */     } else if (this.m_token == null) {
/* 1605 */       error("ER_EXPECTED_LOC_PATH_AT_END_EXPR", null);
/*      */     } 
/*      */     
/* 1608 */     if (this.m_token != null)
/*      */     {
/* 1610 */       if (!RelativeLocationPath() && !seenSlash)
/*      */       {
/*      */ 
/*      */         
/* 1614 */         error("ER_EXPECTED_LOC_PATH", new Object[] { this.m_token });
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1620 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/* 1621 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1622 */     this.m_ops.setOp(opPos + 1, this.m_ops
/* 1623 */         .getOp(1) - opPos);
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
/*      */   protected boolean RelativeLocationPath() throws TransformerException {
/* 1639 */     if (!Step())
/*      */     {
/* 1641 */       return false;
/*      */     }
/*      */     
/* 1644 */     while (tokenIs('/')) {
/*      */       
/* 1646 */       nextToken();
/*      */       
/* 1648 */       if (!Step())
/*      */       {
/*      */ 
/*      */         
/* 1652 */         error("ER_EXPECTED_LOC_STEP", null);
/*      */       }
/*      */     } 
/*      */     
/* 1656 */     return true;
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
/*      */   protected boolean Step() throws TransformerException {
/* 1670 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1672 */     boolean doubleSlash = tokenIs('/');
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1677 */     if (doubleSlash) {
/*      */       
/* 1679 */       nextToken();
/*      */       
/* 1681 */       appendOp(2, 42);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1689 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1690 */       this.m_ops.setOp(this.m_ops.getOp(1), 1033);
/* 1691 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */ 
/*      */       
/* 1694 */       this.m_ops.setOp(opPos + 1 + 1, this.m_ops
/* 1695 */           .getOp(1) - opPos);
/*      */ 
/*      */       
/* 1698 */       this.m_ops.setOp(opPos + 1, this.m_ops
/* 1699 */           .getOp(1) - opPos);
/*      */       
/* 1701 */       opPos = this.m_ops.getOp(1);
/*      */     } 
/*      */     
/* 1704 */     if (tokenIs(".")) {
/*      */       
/* 1706 */       nextToken();
/*      */       
/* 1708 */       if (tokenIs('['))
/*      */       {
/* 1710 */         error("ER_PREDICATE_ILLEGAL_SYNTAX", null);
/*      */       }
/*      */       
/* 1713 */       appendOp(4, 48);
/*      */ 
/*      */       
/* 1716 */       this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/* 1717 */       this.m_ops.setOp(this.m_ops.getOp(1) - 1, 1033);
/*      */     }
/* 1719 */     else if (tokenIs("..")) {
/*      */       
/* 1721 */       nextToken();
/* 1722 */       appendOp(4, 45);
/*      */ 
/*      */       
/* 1725 */       this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/* 1726 */       this.m_ops.setOp(this.m_ops.getOp(1) - 1, 1033);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1732 */     else if (tokenIs('*') || tokenIs('@') || tokenIs('_') || (this.m_token != null && 
/* 1733 */       Character.isLetter(this.m_token.charAt(0)))) {
/*      */       
/* 1735 */       Basis();
/*      */       
/* 1737 */       while (tokenIs('['))
/*      */       {
/* 1739 */         Predicate();
/*      */       }
/*      */ 
/*      */       
/* 1743 */       this.m_ops.setOp(opPos + 1, this.m_ops
/* 1744 */           .getOp(1) - opPos);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1749 */       if (doubleSlash)
/*      */       {
/*      */         
/* 1752 */         error("ER_EXPECTED_LOC_STEP", null);
/*      */       }
/*      */       
/* 1755 */       return false;
/*      */     } 
/*      */     
/* 1758 */     return true;
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
/*      */   protected void Basis() throws TransformerException {
/* 1771 */     int axesType, opPos = this.m_ops.getOp(1);
/*      */ 
/*      */ 
/*      */     
/* 1775 */     if (lookahead("::", 1)) {
/*      */       
/* 1777 */       axesType = AxisName();
/*      */       
/* 1779 */       nextToken();
/* 1780 */       nextToken();
/*      */     }
/* 1782 */     else if (tokenIs('@')) {
/*      */       
/* 1784 */       axesType = 39;
/*      */       
/* 1786 */       appendOp(2, axesType);
/* 1787 */       nextToken();
/*      */     }
/*      */     else {
/*      */       
/* 1791 */       axesType = 40;
/*      */       
/* 1793 */       appendOp(2, axesType);
/*      */     } 
/*      */ 
/*      */     
/* 1797 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */     
/* 1799 */     NodeTest(axesType);
/*      */ 
/*      */     
/* 1802 */     this.m_ops.setOp(opPos + 1 + 1, this.m_ops
/* 1803 */         .getOp(1) - opPos);
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
/*      */   protected int AxisName() throws TransformerException {
/* 1818 */     Object val = Keywords.getAxisName(this.m_token);
/*      */     
/* 1820 */     if (null == val)
/*      */     {
/* 1822 */       error("ER_ILLEGAL_AXIS_NAME", new Object[] { this.m_token });
/*      */     }
/*      */ 
/*      */     
/* 1826 */     int axesType = ((Integer)val).intValue();
/*      */     
/* 1828 */     appendOp(2, axesType);
/*      */     
/* 1830 */     return axesType;
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
/*      */   protected void NodeTest(int axesType) throws TransformerException {
/* 1846 */     if (lookahead('(', 1)) {
/*      */       
/* 1848 */       Object nodeTestOp = Keywords.getNodeType(this.m_token);
/*      */       
/* 1850 */       if (null == nodeTestOp)
/*      */       {
/* 1852 */         error("ER_UNKNOWN_NODETYPE", new Object[] { this.m_token });
/*      */       
/*      */       }
/*      */       else
/*      */       {
/* 1857 */         nextToken();
/*      */         
/* 1859 */         int nt = ((Integer)nodeTestOp).intValue();
/*      */         
/* 1861 */         this.m_ops.setOp(this.m_ops.getOp(1), nt);
/* 1862 */         this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */         
/* 1864 */         consumeExpected('(');
/*      */         
/* 1866 */         if (1032 == nt)
/*      */         {
/* 1868 */           if (!tokenIs(')'))
/*      */           {
/* 1870 */             Literal();
/*      */           }
/*      */         }
/*      */         
/* 1874 */         consumeExpected(')');
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1881 */       this.m_ops.setOp(this.m_ops.getOp(1), 34);
/* 1882 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */       
/* 1884 */       if (lookahead(':', 1)) {
/*      */         
/* 1886 */         if (tokenIs('*')) {
/*      */           
/* 1888 */           this.m_ops.setOp(this.m_ops.getOp(1), -3);
/*      */         }
/*      */         else {
/*      */           
/* 1892 */           this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/*      */ 
/*      */ 
/*      */           
/* 1896 */           if (!Character.isLetter(this.m_tokenChar) && !tokenIs('_'))
/*      */           {
/*      */             
/* 1899 */             error("ER_EXPECTED_NODE_TEST", null);
/*      */           }
/*      */         } 
/*      */         
/* 1903 */         nextToken();
/* 1904 */         consumeExpected(':');
/*      */       }
/*      */       else {
/*      */         
/* 1908 */         this.m_ops.setOp(this.m_ops.getOp(1), -2);
/*      */       } 
/*      */       
/* 1911 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */       
/* 1913 */       if (tokenIs('*')) {
/*      */         
/* 1915 */         this.m_ops.setOp(this.m_ops.getOp(1), -3);
/*      */       }
/*      */       else {
/*      */         
/* 1919 */         this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/*      */ 
/*      */ 
/*      */         
/* 1923 */         if (!Character.isLetter(this.m_tokenChar) && !tokenIs('_'))
/*      */         {
/*      */           
/* 1926 */           error("ER_EXPECTED_NODE_TEST", null);
/*      */         }
/*      */       } 
/*      */       
/* 1930 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */       
/* 1932 */       nextToken();
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
/*      */   protected void Predicate() throws TransformerException {
/* 1945 */     if (tokenIs('[')) {
/*      */       
/* 1947 */       this.countPredicate++;
/* 1948 */       nextToken();
/* 1949 */       PredicateExpr();
/* 1950 */       this.countPredicate--;
/* 1951 */       consumeExpected(']');
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
/*      */   protected void PredicateExpr() throws TransformerException {
/* 1965 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 1967 */     appendOp(2, 29);
/* 1968 */     Expr();
/*      */ 
/*      */     
/* 1971 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/* 1972 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 1973 */     this.m_ops.setOp(opPos + 1, this.m_ops
/* 1974 */         .getOp(1) - opPos);
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
/*      */   protected void QName() throws TransformerException {
/* 1987 */     if (lookahead(':', 1)) {
/*      */       
/* 1989 */       this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 1990 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */       
/* 1992 */       nextToken();
/* 1993 */       consumeExpected(':');
/*      */     }
/*      */     else {
/*      */       
/* 1997 */       this.m_ops.setOp(this.m_ops.getOp(1), -2);
/* 1998 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */     } 
/*      */ 
/*      */     
/* 2002 */     this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 2003 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */     
/* 2005 */     nextToken();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void NCName() {
/* 2015 */     this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 2016 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */     
/* 2018 */     nextToken();
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
/*      */   protected void Literal() throws TransformerException {
/* 2034 */     int last = this.m_token.length() - 1;
/* 2035 */     char c0 = this.m_tokenChar;
/* 2036 */     char cX = this.m_token.charAt(last);
/*      */     
/* 2038 */     if ((c0 == '"' && cX == '"') || (c0 == '\'' && cX == '\'')) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2043 */       int tokenQueuePos = this.m_queueMark - 1;
/*      */       
/* 2045 */       this.m_ops.m_tokenQueue.setElementAt(null, tokenQueuePos);
/*      */       
/* 2047 */       Object obj = new XString(this.m_token.substring(1, last));
/*      */       
/* 2049 */       this.m_ops.m_tokenQueue.setElementAt(obj, tokenQueuePos);
/*      */ 
/*      */       
/* 2052 */       this.m_ops.setOp(this.m_ops.getOp(1), tokenQueuePos);
/* 2053 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */       
/* 2055 */       nextToken();
/*      */     }
/*      */     else {
/*      */       
/* 2059 */       error("ER_PATTERN_LITERAL_NEEDS_BE_QUOTED", new Object[] { this.m_token });
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
/*      */   protected void Number() throws TransformerException {
/* 2074 */     if (null != this.m_token) {
/*      */       double num;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2084 */         if (this.m_token.indexOf('e') > -1 || this.m_token.indexOf('E') > -1)
/* 2085 */           throw new NumberFormatException(); 
/* 2086 */         num = Double.valueOf(this.m_token).doubleValue();
/*      */       }
/* 2088 */       catch (NumberFormatException nfe) {
/*      */         
/* 2090 */         num = 0.0D;
/*      */         
/* 2092 */         error("ER_COULDNOT_BE_FORMATTED_TO_NUMBER", new Object[] { this.m_token });
/*      */       } 
/*      */ 
/*      */       
/* 2096 */       this.m_ops.m_tokenQueue.setElementAt(new XNumber(num), this.m_queueMark - 1);
/* 2097 */       this.m_ops.setOp(this.m_ops.getOp(1), this.m_queueMark - 1);
/* 2098 */       this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */       
/* 2100 */       nextToken();
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
/*      */   protected void Pattern() throws TransformerException {
/*      */     while (true) {
/* 2119 */       LocationPathPattern();
/*      */       
/* 2121 */       if (tokenIs('|')) {
/*      */         
/* 2123 */         nextToken();
/*      */         continue;
/*      */       } 
/*      */       break;
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
/*      */   
/*      */   protected void LocationPathPattern() throws TransformerException {
/* 2145 */     int opPos = this.m_ops.getOp(1);
/*      */     
/* 2147 */     int RELATIVE_PATH_NOT_PERMITTED = 0;
/* 2148 */     int RELATIVE_PATH_PERMITTED = 1;
/* 2149 */     int RELATIVE_PATH_REQUIRED = 2;
/*      */     
/* 2151 */     int relativePathStatus = 0;
/*      */     
/* 2153 */     appendOp(2, 31);
/*      */     
/* 2155 */     if (lookahead('(', 1) && (
/* 2156 */       tokenIs("id") || 
/* 2157 */       tokenIs("key"))) {
/*      */       
/* 2159 */       IdKeyPattern();
/*      */       
/* 2161 */       if (tokenIs('/'))
/*      */       {
/* 2163 */         nextToken();
/*      */         
/* 2165 */         if (tokenIs('/')) {
/*      */           
/* 2167 */           appendOp(4, 52);
/*      */           
/* 2169 */           nextToken();
/*      */         }
/*      */         else {
/*      */           
/* 2173 */           appendOp(4, 53);
/*      */         } 
/*      */ 
/*      */         
/* 2177 */         this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/* 2178 */         this.m_ops.setOp(this.m_ops.getOp(1) - 1, 1034);
/*      */         
/* 2180 */         relativePathStatus = 2;
/*      */       }
/*      */     
/* 2183 */     } else if (tokenIs('/')) {
/*      */       
/* 2185 */       if (lookahead('/', 1)) {
/*      */         
/* 2187 */         appendOp(4, 52);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2193 */         nextToken();
/*      */         
/* 2195 */         relativePathStatus = 2;
/*      */       }
/*      */       else {
/*      */         
/* 2199 */         appendOp(4, 50);
/*      */         
/* 2201 */         relativePathStatus = 1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2206 */       this.m_ops.setOp(this.m_ops.getOp(1) - 2, 4);
/* 2207 */       this.m_ops.setOp(this.m_ops.getOp(1) - 1, 35);
/*      */       
/* 2209 */       nextToken();
/*      */     }
/*      */     else {
/*      */       
/* 2213 */       relativePathStatus = 2;
/*      */     } 
/*      */     
/* 2216 */     if (relativePathStatus != 0)
/*      */     {
/* 2218 */       if (!tokenIs('|') && null != this.m_token) {
/*      */         
/* 2220 */         RelativePathPattern();
/*      */       }
/* 2222 */       else if (relativePathStatus == 2) {
/*      */ 
/*      */         
/* 2225 */         error("ER_EXPECTED_REL_PATH_PATTERN", null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2230 */     this.m_ops.setOp(this.m_ops.getOp(1), -1);
/* 2231 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/* 2232 */     this.m_ops.setOp(opPos + 1, this.m_ops
/* 2233 */         .getOp(1) - opPos);
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
/*      */   protected void IdKeyPattern() throws TransformerException {
/* 2247 */     FunctionCall();
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
/*      */   protected void RelativePathPattern() throws TransformerException {
/* 2264 */     boolean trailingSlashConsumed = StepPattern(false);
/*      */     
/* 2266 */     while (tokenIs('/')) {
/*      */       
/* 2268 */       nextToken();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2273 */       trailingSlashConsumed = StepPattern(!trailingSlashConsumed);
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
/*      */   protected boolean StepPattern(boolean isLeadingSlashPermitted) throws TransformerException {
/* 2291 */     return AbbreviatedNodeTestStep(isLeadingSlashPermitted);
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
/*      */   protected boolean AbbreviatedNodeTestStep(boolean isLeadingSlashPermitted) throws TransformerException {
/*      */     int axesType;
/*      */     boolean trailingSlashConsumed;
/* 2309 */     int opPos = this.m_ops.getOp(1);
/*      */ 
/*      */ 
/*      */     
/* 2313 */     int matchTypePos = -1;
/*      */     
/* 2315 */     if (tokenIs('@')) {
/*      */       
/* 2317 */       axesType = 51;
/*      */       
/* 2319 */       appendOp(2, axesType);
/* 2320 */       nextToken();
/*      */     }
/* 2322 */     else if (lookahead("::", 1)) {
/*      */       
/* 2324 */       if (tokenIs("attribute")) {
/*      */         
/* 2326 */         axesType = 51;
/*      */         
/* 2328 */         appendOp(2, axesType);
/*      */       }
/* 2330 */       else if (tokenIs("child")) {
/*      */         
/* 2332 */         matchTypePos = this.m_ops.getOp(1);
/* 2333 */         axesType = 53;
/*      */         
/* 2335 */         appendOp(2, axesType);
/*      */       }
/*      */       else {
/*      */         
/* 2339 */         axesType = -1;
/*      */         
/* 2341 */         error("ER_AXES_NOT_ALLOWED", new Object[] { this.m_token });
/*      */       } 
/*      */ 
/*      */       
/* 2345 */       nextToken();
/* 2346 */       nextToken();
/*      */     }
/* 2348 */     else if (tokenIs('/')) {
/*      */       
/* 2350 */       if (!isLeadingSlashPermitted)
/*      */       {
/*      */         
/* 2353 */         error("ER_EXPECTED_STEP_PATTERN", null);
/*      */       }
/* 2355 */       axesType = 52;
/*      */       
/* 2357 */       appendOp(2, axesType);
/* 2358 */       nextToken();
/*      */     }
/*      */     else {
/*      */       
/* 2362 */       matchTypePos = this.m_ops.getOp(1);
/* 2363 */       axesType = 53;
/*      */       
/* 2365 */       appendOp(2, axesType);
/*      */     } 
/*      */ 
/*      */     
/* 2369 */     this.m_ops.setOp(1, this.m_ops.getOp(1) + 1);
/*      */     
/* 2371 */     NodeTest(axesType);
/*      */ 
/*      */     
/* 2374 */     this.m_ops.setOp(opPos + 1 + 1, this.m_ops
/* 2375 */         .getOp(1) - opPos);
/*      */     
/* 2377 */     while (tokenIs('['))
/*      */     {
/* 2379 */       Predicate();
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
/*      */ 
/*      */     
/* 2396 */     if (matchTypePos > -1 && tokenIs('/') && lookahead('/', 1)) {
/*      */       
/* 2398 */       this.m_ops.setOp(matchTypePos, 52);
/*      */       
/* 2400 */       nextToken();
/*      */       
/* 2402 */       trailingSlashConsumed = true;
/*      */     }
/*      */     else {
/*      */       
/* 2406 */       trailingSlashConsumed = false;
/*      */     } 
/*      */ 
/*      */     
/* 2410 */     this.m_ops.setOp(opPos + 1, this.m_ops
/* 2411 */         .getOp(1) - opPos);
/*      */     
/* 2413 */     return trailingSlashConsumed;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/compiler/XPathParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */