/*     */ package com.sun.org.apache.xpath.internal;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*     */ import com.sun.org.apache.xml.internal.utils.DefaultErrorHandler;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*     */ import com.sun.org.apache.xpath.internal.compiler.Compiler;
/*     */ import com.sun.org.apache.xpath.internal.compiler.FunctionTable;
/*     */ import com.sun.org.apache.xpath.internal.compiler.XPathParser;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ import javax.xml.transform.ErrorListener;
/*     */ import javax.xml.transform.SourceLocator;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPath
/*     */   implements Serializable, ExpressionOwner
/*     */ {
/*     */   static final long serialVersionUID = 3976493477939110553L;
/*     */   private Expression m_mainExp;
/*  58 */   private transient FunctionTable m_funcTable = null;
/*     */   
/*     */   String m_patternString;
/*     */   public static final int SELECT = 0;
/*     */   
/*     */   private void initFunctionTable() {
/*  64 */     this.m_funcTable = new FunctionTable();
/*     */   }
/*     */   public static final int MATCH = 1;
/*     */   private static final boolean DEBUG_MATCHES = false;
/*     */   public static final double MATCH_SCORE_NONE = -InfinityD;
/*     */   public static final double MATCH_SCORE_QNAME = 0.0D;
/*     */   public static final double MATCH_SCORE_NSWILD = -0.25D;
/*     */   public static final double MATCH_SCORE_NODETEST = -0.5D;
/*     */   public static final double MATCH_SCORE_OTHER = 0.5D;
/*     */   
/*     */   public Expression getExpression() {
/*  75 */     return this.m_mainExp;
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
/*     */   public void fixupVariables(Vector vars, int globalsSize) {
/*  90 */     this.m_mainExp.fixupVariables(vars, globalsSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpression(Expression exp) {
/* 101 */     if (null != this.m_mainExp)
/* 102 */       exp.exprSetParent(this.m_mainExp.exprGetParent()); 
/* 103 */     this.m_mainExp = exp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceLocator getLocator() {
/* 114 */     return this.m_mainExp;
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
/*     */   public String getPatternString() {
/* 142 */     return this.m_patternString;
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
/*     */   public XPath(String exprString, SourceLocator locator, PrefixResolver prefixResolver, int type, ErrorListener errorListener) throws TransformerException {
/* 170 */     initFunctionTable();
/* 171 */     if (null == errorListener) {
/* 172 */       errorListener = new DefaultErrorHandler();
/*     */     }
/* 174 */     this.m_patternString = exprString;
/*     */     
/* 176 */     XPathParser parser = new XPathParser(errorListener, locator);
/* 177 */     Compiler compiler = new Compiler(errorListener, locator, this.m_funcTable);
/*     */     
/* 179 */     if (0 == type) {
/* 180 */       parser.initXPath(compiler, exprString, prefixResolver);
/* 181 */     } else if (1 == type) {
/* 182 */       parser.initMatchPattern(compiler, exprString, prefixResolver);
/*     */     } else {
/* 184 */       throw new RuntimeException(XSLMessages.createXPATHMessage("ER_CANNOT_DEAL_XPATH_TYPE", new Object[] {
/*     */               
/* 186 */               Integer.toString(type)
/*     */             }));
/*     */     } 
/* 189 */     Expression expr = compiler.compileExpression(0);
/*     */ 
/*     */     
/* 192 */     setExpression(expr);
/*     */     
/* 194 */     if (null != locator && locator instanceof ExpressionNode)
/*     */     {
/* 196 */       expr.exprSetParent((ExpressionNode)locator);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPath(String exprString, SourceLocator locator, PrefixResolver prefixResolver, int type, ErrorListener errorListener, FunctionTable aTable) throws TransformerException {
/* 221 */     this.m_funcTable = aTable;
/* 222 */     if (null == errorListener) {
/* 223 */       errorListener = new DefaultErrorHandler();
/*     */     }
/* 225 */     this.m_patternString = exprString;
/*     */     
/* 227 */     XPathParser parser = new XPathParser(errorListener, locator);
/* 228 */     Compiler compiler = new Compiler(errorListener, locator, this.m_funcTable);
/*     */     
/* 230 */     if (0 == type) {
/* 231 */       parser.initXPath(compiler, exprString, prefixResolver);
/* 232 */     } else if (1 == type) {
/* 233 */       parser.initMatchPattern(compiler, exprString, prefixResolver);
/*     */     } else {
/* 235 */       throw new RuntimeException(XSLMessages.createXPATHMessage("ER_CANNOT_DEAL_XPATH_TYPE", new Object[] {
/*     */               
/* 237 */               Integer.toString(type)
/*     */             }));
/*     */     } 
/*     */     
/* 241 */     Expression expr = compiler.compileExpression(0);
/*     */ 
/*     */     
/* 244 */     setExpression(expr);
/*     */     
/* 246 */     if (null != locator && locator instanceof ExpressionNode)
/*     */     {
/* 248 */       expr.exprSetParent((ExpressionNode)locator);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPath(String exprString, SourceLocator locator, PrefixResolver prefixResolver, int type) throws TransformerException {
/* 270 */     this(exprString, locator, prefixResolver, type, null);
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
/*     */   public XPath(Expression expr) {
/* 282 */     setExpression(expr);
/* 283 */     initFunctionTable();
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
/*     */   public XObject execute(XPathContext xctxt, Node contextNode, PrefixResolver namespaceContext) throws TransformerException {
/* 307 */     return execute(xctxt, xctxt
/* 308 */         .getDTMHandleFromNode(contextNode), namespaceContext);
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
/*     */   public XObject execute(XPathContext xctxt, int contextNode, PrefixResolver namespaceContext) throws TransformerException {
/* 333 */     xctxt.pushNamespaceContext(namespaceContext);
/*     */     
/* 335 */     xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);
/*     */     
/* 337 */     XObject xobj = null;
/*     */ 
/*     */     
/*     */     try {
/* 341 */       xobj = this.m_mainExp.execute(xctxt);
/*     */     }
/* 343 */     catch (TransformerException te) {
/*     */       
/* 345 */       te.setLocator(getLocator());
/* 346 */       ErrorListener el = xctxt.getErrorListener();
/* 347 */       if (null != el) {
/*     */         
/* 349 */         el.error(te);
/*     */       } else {
/*     */         
/* 352 */         throw te;
/*     */       } 
/* 354 */     } catch (Exception e) {
/*     */       
/* 356 */       while (e instanceof WrappedRuntimeException)
/*     */       {
/* 358 */         e = ((WrappedRuntimeException)e).getException();
/*     */       }
/*     */ 
/*     */       
/* 362 */       String msg = e.getMessage();
/*     */       
/* 364 */       if (msg == null || msg.length() == 0) {
/* 365 */         msg = XSLMessages.createXPATHMessage("ER_XPATH_ERROR", null);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 370 */       TransformerException te = new TransformerException(msg, getLocator(), e);
/* 371 */       ErrorListener el = xctxt.getErrorListener();
/*     */       
/* 373 */       if (null != el) {
/*     */         
/* 375 */         el.fatalError(te);
/*     */       } else {
/*     */         
/* 378 */         throw te;
/*     */       } 
/*     */     } finally {
/*     */       
/* 382 */       xctxt.popNamespaceContext();
/*     */       
/* 384 */       xctxt.popCurrentNodeAndExpression();
/*     */     } 
/*     */     
/* 387 */     return xobj;
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
/*     */   public boolean bool(XPathContext xctxt, int contextNode, PrefixResolver namespaceContext) throws TransformerException {
/* 410 */     xctxt.pushNamespaceContext(namespaceContext);
/*     */     
/* 412 */     xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);
/*     */ 
/*     */     
/*     */     try {
/* 416 */       return this.m_mainExp.bool(xctxt);
/*     */     }
/* 418 */     catch (TransformerException te) {
/*     */       
/* 420 */       te.setLocator(getLocator());
/* 421 */       ErrorListener el = xctxt.getErrorListener();
/* 422 */       if (null != el) {
/*     */         
/* 424 */         el.error(te);
/*     */       } else {
/*     */         
/* 427 */         throw te;
/*     */       } 
/* 429 */     } catch (Exception e) {
/*     */       
/* 431 */       while (e instanceof WrappedRuntimeException)
/*     */       {
/* 433 */         e = ((WrappedRuntimeException)e).getException();
/*     */       }
/*     */ 
/*     */       
/* 437 */       String msg = e.getMessage();
/*     */       
/* 439 */       if (msg == null || msg.length() == 0) {
/* 440 */         msg = XSLMessages.createXPATHMessage("ER_XPATH_ERROR", null);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 446 */       TransformerException te = new TransformerException(msg, getLocator(), e);
/* 447 */       ErrorListener el = xctxt.getErrorListener();
/*     */       
/* 449 */       if (null != el) {
/*     */         
/* 451 */         el.fatalError(te);
/*     */       } else {
/*     */         
/* 454 */         throw te;
/*     */       } 
/*     */     } finally {
/*     */       
/* 458 */       xctxt.popNamespaceContext();
/*     */       
/* 460 */       xctxt.popCurrentNodeAndExpression();
/*     */     } 
/*     */     
/* 463 */     return false;
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
/*     */   public double getMatchScore(XPathContext xctxt, int context) throws TransformerException {
/* 486 */     xctxt.pushCurrentNode(context);
/* 487 */     xctxt.pushCurrentExpressionNode(context);
/*     */ 
/*     */     
/*     */     try {
/* 491 */       XObject score = this.m_mainExp.execute(xctxt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 501 */       return score.num();
/*     */     }
/*     */     finally {
/*     */       
/* 505 */       xctxt.popCurrentNode();
/* 506 */       xctxt.popCurrentExpressionNode();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void warn(XPathContext xctxt, int sourceNode, String msg, Object[] args) throws TransformerException {
/* 532 */     String fmsg = XSLMessages.createXPATHWarning(msg, args);
/* 533 */     ErrorListener ehandler = xctxt.getErrorListener();
/*     */     
/* 535 */     if (null != ehandler)
/*     */     {
/*     */ 
/*     */       
/* 539 */       ehandler.warning(new TransformerException(fmsg, xctxt.getSAXLocator()));
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
/*     */   public void assertion(boolean b, String msg) {
/* 555 */     if (!b) {
/*     */       
/* 557 */       String fMsg = XSLMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { msg });
/*     */ 
/*     */ 
/*     */       
/* 561 */       throw new RuntimeException(fMsg);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(XPathContext xctxt, int sourceNode, String msg, Object[] args) throws TransformerException {
/* 585 */     String fmsg = XSLMessages.createXPATHMessage(msg, args);
/* 586 */     ErrorListener ehandler = xctxt.getErrorListener();
/*     */     
/* 588 */     if (null != ehandler) {
/*     */       
/* 590 */       ehandler.fatalError(new TransformerException(fmsg, xctxt
/* 591 */             .getSAXLocator()));
/*     */     }
/*     */     else {
/*     */       
/* 595 */       SourceLocator slocator = xctxt.getSAXLocator();
/* 596 */       System.out.println(fmsg + "; file " + slocator.getSystemId() + "; line " + slocator
/* 597 */           .getLineNumber() + "; column " + slocator
/* 598 */           .getColumnNumber());
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
/*     */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor) {
/* 613 */     this.m_mainExp.callVisitors(this, visitor);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/XPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */