/*      */ package com.sun.org.apache.xpath.internal;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
/*      */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMFilter;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2RTFDTM;
/*      */ import com.sun.org.apache.xml.internal.utils.DefaultErrorHandler;
/*      */ import com.sun.org.apache.xml.internal.utils.IntStack;
/*      */ import com.sun.org.apache.xml.internal.utils.NodeVector;
/*      */ import com.sun.org.apache.xml.internal.utils.ObjectStack;
/*      */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*      */ import com.sun.org.apache.xml.internal.utils.QName;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLString;
/*      */ import com.sun.org.apache.xpath.internal.axes.OneStepIteratorForward;
/*      */ import com.sun.org.apache.xpath.internal.axes.SubContextList;
/*      */ import com.sun.org.apache.xpath.internal.objects.DTMXRTreeFrag;
/*      */ import com.sun.org.apache.xpath.internal.objects.XMLStringFactoryImpl;
/*      */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*      */ import com.sun.org.apache.xpath.internal.objects.XString;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ import javax.xml.transform.ErrorListener;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.SourceLocator;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.URIResolver;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.traversal.NodeIterator;
/*      */ import org.xml.sax.XMLReader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XPathContext
/*      */   extends DTMManager
/*      */ {
/*   63 */   IntStack m_last_pushed_rtfdtm = new IntStack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   private Vector m_rtfdtm_stack = null;
/*      */   
/*   76 */   private int m_which_rtfdtm = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   82 */   private SAX2RTFDTM m_global_rtfdtm = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   88 */   private HashMap m_DTMXRTreeFrags = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean m_isSecureProcessing = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean m_overrideDefaultParser;
/*      */ 
/*      */ 
/*      */   
/*  102 */   protected DTMManager m_dtmManager = null; ObjectStack m_saxLocations; private Object m_owner; private Method m_ownerGetErrorListener; private VariableStack m_variableStacks; private SourceTreeManager m_sourceTreeManager; private ErrorListener m_errorListener; private ErrorListener m_defaultErrorListener; private URIResolver m_uriResolver; public XMLReader m_primaryReader; private Stack m_contextNodeLists; public static final int RECURSIONLIMIT = 4096; private IntStack m_currentNodes;
/*      */   private NodeVector m_iteratorRoots;
/*      */   private NodeVector m_predicateRoots;
/*      */   private IntStack m_currentExpressionNodes;
/*      */   private IntStack m_predicatePos;
/*      */   private ObjectStack m_prefixResolvers;
/*      */   private Stack m_axesIteratorStack;
/*      */   XPathExpressionContext expressionContext;
/*      */   
/*      */   public DTMManager getDTMManager() {
/*  112 */     return this.m_dtmManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecureProcessing(boolean flag) {
/*  120 */     this.m_isSecureProcessing = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSecureProcessing() {
/*  128 */     return this.m_isSecureProcessing;
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
/*      */   public DTM getDTM(Source source, boolean unique, DTMWSFilter wsfilter, boolean incremental, boolean doIndexing) {
/*  157 */     return this.m_dtmManager.getDTM(source, unique, wsfilter, incremental, doIndexing);
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
/*      */   public DTM getDTM(int nodeHandle) {
/*  170 */     return this.m_dtmManager.getDTM(nodeHandle);
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
/*      */   public int getDTMHandleFromNode(Node node) {
/*  183 */     return this.m_dtmManager.getDTMHandleFromNode(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDTMIdentity(DTM dtm) {
/*  192 */     return this.m_dtmManager.getDTMIdentity(dtm);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTM createDocumentFragment() {
/*  201 */     return this.m_dtmManager.createDocumentFragment();
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
/*      */   public boolean release(DTM dtm, boolean shouldHardDelete) {
/*  220 */     if (this.m_rtfdtm_stack != null && this.m_rtfdtm_stack.contains(dtm))
/*      */     {
/*  222 */       return false;
/*      */     }
/*      */     
/*  225 */     return this.m_dtmManager.release(dtm, shouldHardDelete);
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
/*      */   public DTMIterator createDTMIterator(Object xpathCompiler, int pos) {
/*  242 */     return this.m_dtmManager.createDTMIterator(xpathCompiler, pos);
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
/*      */   public DTMIterator createDTMIterator(String xpathString, PrefixResolver presolver) {
/*  261 */     return this.m_dtmManager.createDTMIterator(xpathString, presolver);
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
/*      */   public DTMIterator createDTMIterator(int whatToShow, DTMFilter filter, boolean entityReferenceExpansion) {
/*  284 */     return this.m_dtmManager.createDTMIterator(whatToShow, filter, entityReferenceExpansion);
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
/*      */   public DTMIterator createDTMIterator(int node) {
/*  297 */     DTMIterator iter = new OneStepIteratorForward(13);
/*  298 */     iter.setRoot(node, this);
/*  299 */     return iter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XPathContext() {
/*  308 */     this(false);
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
/*      */   private void init(boolean overrideDefaultParser) {
/*  331 */     this.m_prefixResolvers.push((Object)null);
/*  332 */     this.m_currentNodes.push(-1);
/*  333 */     this.m_currentExpressionNodes.push(-1);
/*  334 */     this.m_saxLocations.push((Object)null);
/*  335 */     this.m_overrideDefaultParser = overrideDefaultParser;
/*  336 */     this.m_dtmManager = DTMManager.newInstance(
/*  337 */         XMLStringFactoryImpl.getFactory());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  346 */     releaseDTMXRTreeFrags();
/*      */     
/*  348 */     if (this.m_rtfdtm_stack != null)
/*  349 */       for (Enumeration<DTM> e = this.m_rtfdtm_stack.elements(); e.hasMoreElements();) {
/*  350 */         this.m_dtmManager.release(e.nextElement(), true);
/*      */       } 
/*  352 */     this.m_rtfdtm_stack = null;
/*  353 */     this.m_which_rtfdtm = -1;
/*      */     
/*  355 */     if (this.m_global_rtfdtm != null)
/*  356 */       this.m_dtmManager.release(this.m_global_rtfdtm, true); 
/*  357 */     this.m_global_rtfdtm = null;
/*      */ 
/*      */     
/*  360 */     this.m_dtmManager = DTMManager.newInstance(
/*  361 */         XMLStringFactoryImpl.getFactory());
/*      */ 
/*      */     
/*  364 */     this.m_saxLocations.removeAllElements();
/*  365 */     this.m_axesIteratorStack.removeAllElements();
/*  366 */     this.m_contextNodeLists.removeAllElements();
/*  367 */     this.m_currentExpressionNodes.removeAllElements();
/*  368 */     this.m_currentNodes.removeAllElements();
/*  369 */     this.m_iteratorRoots.RemoveAllNoClear();
/*  370 */     this.m_predicatePos.removeAllElements();
/*  371 */     this.m_predicateRoots.RemoveAllNoClear();
/*  372 */     this.m_prefixResolvers.removeAllElements();
/*      */     
/*  374 */     this.m_prefixResolvers.push((Object)null);
/*  375 */     this.m_currentNodes.push(-1);
/*  376 */     this.m_currentExpressionNodes.push(-1);
/*  377 */     this.m_saxLocations.push((Object)null);
/*      */   }
/*      */   
/*      */   public XPathContext(boolean overrideDefaultParser) {
/*  381 */     this.m_saxLocations = new ObjectStack(4096);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  459 */     this.m_variableStacks = new VariableStack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  487 */     this.m_sourceTreeManager = new SourceTreeManager();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  627 */     this.m_contextNodeLists = new Stack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  681 */     this.m_currentNodes = new IntStack(4096);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  812 */     this.m_iteratorRoots = new NodeVector();
/*      */ 
/*      */     
/*  815 */     this.m_predicateRoots = new NodeVector();
/*      */ 
/*      */     
/*  818 */     this.m_currentExpressionNodes = new IntStack(4096);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  824 */     this.m_predicatePos = new IntStack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  870 */     this.m_prefixResolvers = new ObjectStack(4096);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  930 */     this.m_axesIteratorStack = new Stack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1016 */     this.expressionContext = new XPathExpressionContext(); init(overrideDefaultParser); } public void setSAXLocator(SourceLocator location) { this.m_saxLocations.setTop(location); } public void pushSAXLocator(SourceLocator location) { this.m_saxLocations.push(location); } public void pushSAXLocatorNull() { this.m_saxLocations.push((Object)null); } public void popSAXLocator() { this.m_saxLocations.pop(); } public SourceLocator getSAXLocator() { return (SourceLocator)this.m_saxLocations.peek(); } public Object getOwnerObject() { return this.m_owner; } public final VariableStack getVarStack() { return this.m_variableStacks; } public final void setVarStack(VariableStack varStack) { this.m_variableStacks = varStack; } public final SourceTreeManager getSourceTreeManager() { return this.m_sourceTreeManager; } public void setSourceTreeManager(SourceTreeManager mgr) { this.m_sourceTreeManager = mgr; } public final ErrorListener getErrorListener() { if (null != this.m_errorListener) return this.m_errorListener;  ErrorListener retval = null; try { if (null != this.m_ownerGetErrorListener) retval = (ErrorListener)this.m_ownerGetErrorListener.invoke(this.m_owner, new Object[0]);  } catch (Exception exception) {} if (null == retval) { if (null == this.m_defaultErrorListener) this.m_defaultErrorListener = new DefaultErrorHandler();  retval = this.m_defaultErrorListener; }  return retval; } public void setErrorListener(ErrorListener listener) throws IllegalArgumentException { if (listener == null) throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", null));  this.m_errorListener = listener; } public final URIResolver getURIResolver() { return this.m_uriResolver; } public void setURIResolver(URIResolver resolver) { this.m_uriResolver = resolver; } public XPathContext(Object owner) { this.m_saxLocations = new ObjectStack(4096); this.m_variableStacks = new VariableStack(); this.m_sourceTreeManager = new SourceTreeManager(); this.m_contextNodeLists = new Stack(); this.m_currentNodes = new IntStack(4096); this.m_iteratorRoots = new NodeVector(); this.m_predicateRoots = new NodeVector(); this.m_currentExpressionNodes = new IntStack(4096); this.m_predicatePos = new IntStack(); this.m_prefixResolvers = new ObjectStack(4096); this.m_axesIteratorStack = new Stack(); this.expressionContext = new XPathExpressionContext(); this.m_owner = owner; try { this.m_ownerGetErrorListener = this.m_owner.getClass().getMethod("getErrorListener", new Class[0]); } catch (NoSuchMethodException noSuchMethodException) {} init(false); }
/*      */   public final XMLReader getPrimaryReader() { return this.m_primaryReader; }
/*      */   public void setPrimaryReader(XMLReader reader) { this.m_primaryReader = reader; }
/*      */   public Stack getContextNodeListsStack() { return this.m_contextNodeLists; }
/*      */   public void setContextNodeListsStack(Stack s) { this.m_contextNodeLists = s; }
/*      */   public final DTMIterator getContextNodeList() { if (this.m_contextNodeLists.size() > 0) return this.m_contextNodeLists.peek();  return null; }
/*      */   public final void pushContextNodeList(DTMIterator nl) { this.m_contextNodeLists.push(nl); }
/*      */   public final void popContextNodeList() { if (this.m_contextNodeLists.isEmpty()) { System.err.println("Warning: popContextNodeList when stack is empty!"); } else { this.m_contextNodeLists.pop(); }  }
/*      */   public IntStack getCurrentNodeStack() { return this.m_currentNodes; }
/* 1025 */   public void setCurrentNodeStack(IntStack nv) { this.m_currentNodes = nv; } public final int getCurrentNode() { return this.m_currentNodes.peek(); } public final void pushCurrentNodeAndExpression(int cn, int en) { this.m_currentNodes.push(cn); this.m_currentExpressionNodes.push(cn); } public final void popCurrentNodeAndExpression() { this.m_currentNodes.quickPop(1); this.m_currentExpressionNodes.quickPop(1); } public final void pushExpressionState(int cn, int en, PrefixResolver nc) { this.m_currentNodes.push(cn); this.m_currentExpressionNodes.push(cn); this.m_prefixResolvers.push(nc); } public final void popExpressionState() { this.m_currentNodes.quickPop(1); this.m_currentExpressionNodes.quickPop(1); this.m_prefixResolvers.pop(); } public final void pushCurrentNode(int n) { this.m_currentNodes.push(n); } public final void popCurrentNode() { this.m_currentNodes.quickPop(1); } public final void pushPredicateRoot(int n) { this.m_predicateRoots.push(n); } public final void popPredicateRoot() { this.m_predicateRoots.popQuick(); } public final int getPredicateRoot() { return this.m_predicateRoots.peepOrNull(); } public final void pushIteratorRoot(int n) { this.m_iteratorRoots.push(n); } public final void popIteratorRoot() { this.m_iteratorRoots.popQuick(); } public final int getIteratorRoot() { return this.m_iteratorRoots.peepOrNull(); } public IntStack getCurrentExpressionNodeStack() { return this.m_currentExpressionNodes; } public void setCurrentExpressionNodeStack(IntStack nv) { this.m_currentExpressionNodes = nv; } public final int getPredicatePos() { return this.m_predicatePos.peek(); } public final void pushPredicatePos(int n) { this.m_predicatePos.push(n); } public final void popPredicatePos() { this.m_predicatePos.pop(); } public final int getCurrentExpressionNode() { return this.m_currentExpressionNodes.peek(); } public final void pushCurrentExpressionNode(int n) { this.m_currentExpressionNodes.push(n); } public ExpressionContext getExpressionContext() { return this.expressionContext; }
/*      */   public final void popCurrentExpressionNode() { this.m_currentExpressionNodes.quickPop(1); }
/*      */   public final PrefixResolver getNamespaceContext() { return (PrefixResolver)this.m_prefixResolvers.peek(); }
/*      */   public final void setNamespaceContext(PrefixResolver pr) { this.m_prefixResolvers.setTop(pr); }
/*      */   public final void pushNamespaceContext(PrefixResolver pr) { this.m_prefixResolvers.push(pr); }
/*      */   public final void pushNamespaceContextNull() { this.m_prefixResolvers.push((Object)null); }
/*      */   public final void popNamespaceContext() { this.m_prefixResolvers.pop(); }
/*      */   public Stack getAxesIteratorStackStacks() { return this.m_axesIteratorStack; }
/*      */   public void setAxesIteratorStackStacks(Stack s) { this.m_axesIteratorStack = s; }
/*      */   public final void pushSubContextList(SubContextList iter) { this.m_axesIteratorStack.push(iter); }
/*      */   public final void popSubContextList() { this.m_axesIteratorStack.pop(); }
/*      */   public SubContextList getSubContextList() { return this.m_axesIteratorStack.isEmpty() ? null : this.m_axesIteratorStack.peek(); }
/*      */   public SubContextList getCurrentNodeList() { return this.m_axesIteratorStack.isEmpty() ? null : this.m_axesIteratorStack.elementAt(0); } public final int getContextNode() { return getCurrentNode(); } public final DTMIterator getContextNodes() { try { DTMIterator cnl = getContextNodeList(); if (null != cnl) return cnl.cloneWithReset();  return null; } catch (CloneNotSupportedException cnse) { return null; }  } public class XPathExpressionContext implements ExpressionContext
/*      */   {
/* 1039 */     public XPathContext getXPathContext() { return XPathContext.this; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMManager getDTMManager() {
/* 1050 */       return XPathContext.this.m_dtmManager;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Node getContextNode() {
/* 1059 */       int context = XPathContext.this.getCurrentNode();
/*      */       
/* 1061 */       return XPathContext.this.getDTM(context).getNode(context);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NodeIterator getContextNodes() {
/* 1071 */       return new DTMNodeIterator(XPathContext.this.getContextNodeList());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ErrorListener getErrorListener() {
/* 1080 */       return XPathContext.this.getErrorListener();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean overrideDefaultParser() {
/* 1086 */       return XPathContext.this.m_overrideDefaultParser;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setOverrideDefaultParser(boolean flag) {
/* 1093 */       XPathContext.this.m_overrideDefaultParser = flag;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double toNumber(Node n) {
/* 1104 */       int nodeHandle = XPathContext.this.getDTMHandleFromNode(n);
/* 1105 */       DTM dtm = XPathContext.this.getDTM(nodeHandle);
/* 1106 */       XString xobj = (XString)dtm.getStringValue(nodeHandle);
/* 1107 */       return xobj.num();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString(Node n) {
/* 1118 */       int nodeHandle = XPathContext.this.getDTMHandleFromNode(n);
/* 1119 */       DTM dtm = XPathContext.this.getDTM(nodeHandle);
/* 1120 */       XMLString strVal = dtm.getStringValue(nodeHandle);
/* 1121 */       return strVal.toString();
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
/*      */     public final XObject getVariableOrParam(QName qname) throws TransformerException {
/* 1134 */       return XPathContext.this.m_variableStacks.getVariableOrParam(XPathContext.this, qname);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public DTM getGlobalRTFDTM() {
/* 1170 */     if (this.m_global_rtfdtm == null || this.m_global_rtfdtm.isTreeIncomplete())
/*      */     {
/* 1172 */       this.m_global_rtfdtm = (SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false);
/*      */     }
/* 1174 */     return this.m_global_rtfdtm;
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
/*      */   public DTM getRTFDTM() {
/*      */     SAX2RTFDTM rtfdtm;
/* 1203 */     if (this.m_rtfdtm_stack == null) {
/*      */       
/* 1205 */       this.m_rtfdtm_stack = new Vector();
/* 1206 */       rtfdtm = (SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false);
/* 1207 */       this.m_rtfdtm_stack.addElement(rtfdtm);
/* 1208 */       this.m_which_rtfdtm++;
/*      */     }
/* 1210 */     else if (this.m_which_rtfdtm < 0) {
/*      */       
/* 1212 */       rtfdtm = this.m_rtfdtm_stack.elementAt(++this.m_which_rtfdtm);
/*      */     }
/*      */     else {
/*      */       
/* 1216 */       rtfdtm = this.m_rtfdtm_stack.elementAt(this.m_which_rtfdtm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1224 */       if (rtfdtm.isTreeIncomplete())
/*      */       {
/* 1226 */         if (++this.m_which_rtfdtm < this.m_rtfdtm_stack.size()) {
/* 1227 */           rtfdtm = this.m_rtfdtm_stack.elementAt(this.m_which_rtfdtm);
/*      */         } else {
/*      */           
/* 1230 */           rtfdtm = (SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false);
/* 1231 */           this.m_rtfdtm_stack.addElement(rtfdtm);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1236 */     return rtfdtm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pushRTFContext() {
/* 1245 */     this.m_last_pushed_rtfdtm.push(this.m_which_rtfdtm);
/* 1246 */     if (null != this.m_rtfdtm_stack) {
/* 1247 */       ((SAX2RTFDTM)getRTFDTM()).pushRewindMark();
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
/*      */   public void popRTFContext() {
/* 1266 */     int previous = this.m_last_pushed_rtfdtm.pop();
/* 1267 */     if (null == this.m_rtfdtm_stack) {
/*      */       return;
/*      */     }
/* 1270 */     if (this.m_which_rtfdtm == previous) {
/*      */       
/* 1272 */       if (previous >= 0)
/*      */       {
/* 1274 */         boolean bool = ((SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(previous)).popRewindMark();
/*      */       }
/*      */     } else {
/* 1277 */       while (this.m_which_rtfdtm != previous) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1282 */         boolean isEmpty = ((SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(this.m_which_rtfdtm)).popRewindMark();
/* 1283 */         this.m_which_rtfdtm--;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMXRTreeFrag getDTMXRTreeFrag(int dtmIdentity) {
/* 1295 */     if (this.m_DTMXRTreeFrags == null) {
/* 1296 */       this.m_DTMXRTreeFrags = new HashMap<>();
/*      */     }
/*      */     
/* 1299 */     if (this.m_DTMXRTreeFrags.containsKey(new Integer(dtmIdentity))) {
/* 1300 */       return (DTMXRTreeFrag)this.m_DTMXRTreeFrags.get(new Integer(dtmIdentity));
/*      */     }
/* 1302 */     DTMXRTreeFrag frag = new DTMXRTreeFrag(dtmIdentity, this);
/* 1303 */     this.m_DTMXRTreeFrags.put(new Integer(dtmIdentity), frag);
/* 1304 */     return frag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void releaseDTMXRTreeFrags() {
/* 1313 */     if (this.m_DTMXRTreeFrags == null) {
/*      */       return;
/*      */     }
/* 1316 */     Iterator<DTMXRTreeFrag> iter = this.m_DTMXRTreeFrags.values().iterator();
/* 1317 */     while (iter.hasNext()) {
/* 1318 */       DTMXRTreeFrag frag = iter.next();
/* 1319 */       frag.destruct();
/* 1320 */       iter.remove();
/*      */     } 
/* 1322 */     this.m_DTMXRTreeFrags = null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/XPathContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */