/*     */ package com.sun.org.apache.xalan.internal.xsltc.runtime;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOMCache;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.Translet;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.DOMAdapter;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.KeyIndex;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.output.TransletOutputHandlerFactory;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*     */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Templates;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractTranslet
/*     */   implements Translet
/*     */ {
/*  66 */   public String _version = "1.0";
/*  67 */   public String _method = null;
/*  68 */   public String _encoding = "UTF-8";
/*     */   public boolean _omitHeader = false;
/*  70 */   public String _standalone = null;
/*     */   
/*     */   public boolean _isStandalone = false;
/*  73 */   public String _doctypePublic = null;
/*  74 */   public String _doctypeSystem = null;
/*     */   public boolean _indent = false;
/*  76 */   public String _mediaType = null;
/*  77 */   public Vector _cdata = null;
/*  78 */   public int _indentamount = -1;
/*     */ 
/*     */   
/*     */   public static final int FIRST_TRANSLET_VERSION = 100;
/*     */ 
/*     */   
/*     */   public static final int VER_SPLIT_NAMES_ARRAY = 101;
/*     */ 
/*     */   
/*     */   public static final int CURRENT_TRANSLET_VERSION = 101;
/*     */   
/*  89 */   protected int transletVersion = 100;
/*     */   
/*     */   protected String[] namesArray;
/*     */   
/*     */   protected String[] urisArray;
/*     */   
/*     */   protected int[] typesArray;
/*     */   
/*     */   protected String[] namespaceArray;
/*  98 */   protected Templates _templates = null;
/*     */ 
/*     */   
/*     */   protected boolean _hasIdCall = false;
/*     */ 
/*     */   
/* 104 */   protected StringValueHandler stringValueHandler = new StringValueHandler();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String EMPTYSTRING = "";
/*     */ 
/*     */   
/*     */   private static final String ID_INDEX_NAME = "##id";
/*     */ 
/*     */   
/*     */   private boolean _overrideDefaultParser;
/*     */ 
/*     */   
/* 117 */   private String _accessExternalStylesheet = "all";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printInternalState() {
/* 123 */     System.out.println("-------------------------------------");
/* 124 */     System.out.println("AbstractTranslet this = " + this);
/* 125 */     System.out.println("pbase = " + this.pbase);
/* 126 */     System.out.println("vframe = " + this.pframe);
/* 127 */     System.out.println("paramsStack.size() = " + this.paramsStack.size());
/* 128 */     System.out.println("namesArray.size = " + this.namesArray.length);
/* 129 */     System.out.println("namespaceArray.size = " + this.namespaceArray.length);
/* 130 */     System.out.println("");
/* 131 */     System.out.println("Total memory = " + Runtime.getRuntime().totalMemory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DOMAdapter makeDOMAdapter(DOM dom) throws TransletException {
/* 141 */     setRootForKeys(dom.getDocument());
/* 142 */     return new DOMAdapter(dom, this.namesArray, this.urisArray, this.typesArray, this.namespaceArray);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   protected int pbase = 0; protected int pframe = 0;
/* 152 */   protected ArrayList paramsStack = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void pushParamFrame() {
/* 158 */     this.paramsStack.add(this.pframe, new Integer(this.pbase));
/* 159 */     this.pbase = ++this.pframe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void popParamFrame() {
/* 166 */     if (this.pbase > 0) {
/* 167 */       int oldpbase = ((Integer)this.paramsStack.get(--this.pbase)).intValue();
/* 168 */       for (int i = this.pframe - 1; i >= this.pbase; i--) {
/* 169 */         this.paramsStack.remove(i);
/*     */       }
/* 171 */       this.pframe = this.pbase; this.pbase = oldpbase;
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
/*     */   public final Object addParameter(String name, Object value) {
/* 184 */     name = BasisLibrary.mapQNameToJavaName(name);
/* 185 */     return addParameter(name, value, false);
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
/*     */   public final Object addParameter(String name, Object value, boolean isDefault) {
/* 198 */     for (int i = this.pframe - 1; i >= this.pbase; i--) {
/* 199 */       Parameter param = this.paramsStack.get(i);
/*     */       
/* 201 */       if (param._name.equals(name)) {
/*     */ 
/*     */         
/* 204 */         if (param._isDefault || !isDefault) {
/* 205 */           param._value = value;
/* 206 */           param._isDefault = isDefault;
/* 207 */           return value;
/*     */         } 
/* 209 */         return param._value;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 214 */     this.paramsStack.add(this.pframe++, new Parameter(name, value, isDefault));
/* 215 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearParameters() {
/* 222 */     this.pbase = this.pframe = 0;
/* 223 */     this.paramsStack.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object getParameter(String name) {
/* 232 */     name = BasisLibrary.mapQNameToJavaName(name);
/*     */     
/* 234 */     for (int i = this.pframe - 1; i >= this.pbase; i--) {
/* 235 */       Parameter param = this.paramsStack.get(i);
/* 236 */       if (param._name.equals(name)) return param._value; 
/*     */     } 
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 248 */   private MessageHandler _msgHandler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMessageHandler(MessageHandler handler) {
/* 254 */     this._msgHandler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void displayMessage(String msg) {
/* 261 */     if (this._msgHandler == null) {
/* 262 */       System.err.println(msg);
/*     */     } else {
/*     */       
/* 265 */       this._msgHandler.displayMessage(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 274 */   public Map<String, DecimalFormat> _formatSymbols = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDecimalFormat(String name, DecimalFormatSymbols symbols) {
/* 282 */     if (this._formatSymbols == null) this._formatSymbols = new HashMap<>();
/*     */ 
/*     */     
/* 285 */     if (name == null) name = "";
/*     */ 
/*     */     
/* 288 */     DecimalFormat df = new DecimalFormat();
/* 289 */     if (symbols != null) {
/* 290 */       df.setDecimalFormatSymbols(symbols);
/*     */     }
/* 292 */     this._formatSymbols.put(name, df);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DecimalFormat getDecimalFormat(String name) {
/* 300 */     if (this._formatSymbols != null) {
/*     */       
/* 302 */       if (name == null) name = "";
/*     */       
/* 304 */       DecimalFormat df = this._formatSymbols.get(name);
/* 305 */       if (df == null) df = this._formatSymbols.get(""); 
/* 306 */       return df;
/*     */     } 
/* 308 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void prepassDocument(DOM document) {
/* 318 */     setIndexSize(document.getSize());
/* 319 */     buildIDIndex(document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void buildIDIndex(DOM document) {
/* 328 */     setRootForKeys(document.getDocument());
/*     */     
/* 330 */     if (document instanceof DOMEnhancedForDTM) {
/* 331 */       DOMEnhancedForDTM enhancedDOM = (DOMEnhancedForDTM)document;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 336 */       if (enhancedDOM.hasDOMSource()) {
/* 337 */         buildKeyIndex("##id", document);
/*     */         
/*     */         return;
/*     */       } 
/* 341 */       Map<String, Integer> elementsByID = enhancedDOM.getElementsWithIDs();
/*     */       
/* 343 */       if (elementsByID == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 350 */       boolean hasIDValues = false;
/* 351 */       for (Map.Entry<String, Integer> entry : elementsByID.entrySet()) {
/* 352 */         int element = document.getNodeHandle(((Integer)entry.getValue()).intValue());
/* 353 */         buildKeyIndex("##id", element, entry.getKey());
/* 354 */         hasIDValues = true;
/*     */       } 
/*     */       
/* 357 */       if (hasIDValues) {
/* 358 */         setKeyIndexDom("##id", document);
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
/*     */   public final void postInitialization() {
/* 371 */     if (this.transletVersion < 101) {
/* 372 */       int arraySize = this.namesArray.length;
/* 373 */       String[] newURIsArray = new String[arraySize];
/* 374 */       String[] newNamesArray = new String[arraySize];
/* 375 */       int[] newTypesArray = new int[arraySize];
/*     */       
/* 377 */       for (int i = 0; i < arraySize; i++) {
/* 378 */         String name = this.namesArray[i];
/* 379 */         int colonIndex = name.lastIndexOf(':');
/* 380 */         int lNameStartIdx = colonIndex + 1;
/*     */         
/* 382 */         if (colonIndex > -1) {
/* 383 */           newURIsArray[i] = name.substring(0, colonIndex);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 388 */         if (name.charAt(lNameStartIdx) == '@') {
/* 389 */           lNameStartIdx++;
/* 390 */           newTypesArray[i] = 2;
/* 391 */         } else if (name.charAt(lNameStartIdx) == '?') {
/* 392 */           lNameStartIdx++;
/* 393 */           newTypesArray[i] = 13;
/*     */         } else {
/* 395 */           newTypesArray[i] = 1;
/*     */         } 
/* 397 */         newNamesArray[i] = (lNameStartIdx == 0) ? name : name
/*     */           
/* 399 */           .substring(lNameStartIdx);
/*     */       } 
/*     */       
/* 402 */       this.namesArray = newNamesArray;
/* 403 */       this.urisArray = newURIsArray;
/* 404 */       this.typesArray = newTypesArray;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     if (this.transletVersion > 101) {
/* 411 */       BasisLibrary.runTimeError("UNKNOWN_TRANSLET_VERSION_ERR", 
/* 412 */           getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 421 */   private Map<String, KeyIndex> _keyIndexes = null;
/* 422 */   private KeyIndex _emptyKeyIndex = null;
/* 423 */   private int _indexSize = 0;
/* 424 */   private int _currentRootForKeys = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndexSize(int size) {
/* 431 */     if (size > this._indexSize) this._indexSize = size;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyIndex createKeyIndex() {
/* 438 */     return new KeyIndex(this._indexSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildKeyIndex(String name, int node, String value) {
/* 448 */     KeyIndex index = buildKeyIndexHelper(name);
/* 449 */     index.add(value, node, this._currentRootForKeys);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildKeyIndex(String name, DOM dom) {
/* 458 */     KeyIndex index = buildKeyIndexHelper(name);
/* 459 */     index.setDom(dom, dom.getDocument());
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
/*     */   private KeyIndex buildKeyIndexHelper(String name) {
/* 471 */     if (this._keyIndexes == null) this._keyIndexes = new HashMap<>();
/*     */     
/* 473 */     KeyIndex index = this._keyIndexes.get(name);
/* 474 */     if (index == null) {
/* 475 */       this._keyIndexes.put(name, index = new KeyIndex(this._indexSize));
/*     */     }
/* 477 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyIndex getKeyIndex(String name) {
/* 488 */     if (this._keyIndexes == null) {
/* 489 */       return (this._emptyKeyIndex != null) ? this._emptyKeyIndex : (this._emptyKeyIndex = new KeyIndex(1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 495 */     KeyIndex index = this._keyIndexes.get(name);
/*     */ 
/*     */     
/* 498 */     if (index == null) {
/* 499 */       return (this._emptyKeyIndex != null) ? this._emptyKeyIndex : (this._emptyKeyIndex = new KeyIndex(1));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 504 */     return index;
/*     */   }
/*     */   
/*     */   private void setRootForKeys(int root) {
/* 508 */     this._currentRootForKeys = root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildKeys(DOM document, DTMAxisIterator iterator, SerializationHandler handler, int root) throws TransletException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyIndexDom(String name, DOM document) {
/* 526 */     getKeyIndex(name).setDom(document, document.getDocument());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 534 */   private DOMCache _domCache = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDOMCache(DOMCache cache) {
/* 541 */     this._domCache = cache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMCache getDOMCache() {
/* 549 */     return this._domCache;
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
/*     */   public SerializationHandler openOutputHandler(String filename, boolean append) throws TransletException {
/*     */     try {
/* 562 */       TransletOutputHandlerFactory factory = TransletOutputHandlerFactory.newInstance(this._overrideDefaultParser);
/*     */       
/* 564 */       String dirStr = (new File(filename)).getParent();
/* 565 */       if (null != dirStr && dirStr.length() > 0) {
/* 566 */         File dir = new File(dirStr);
/* 567 */         dir.mkdirs();
/*     */       } 
/*     */       
/* 570 */       factory.setEncoding(this._encoding);
/* 571 */       factory.setOutputMethod(this._method);
/* 572 */       factory.setOutputStream(new BufferedOutputStream(new FileOutputStream(filename, append)));
/* 573 */       factory.setOutputType(0);
/*     */ 
/*     */       
/* 576 */       SerializationHandler handler = factory.getSerializationHandler();
/*     */       
/* 578 */       transferOutputSettings(handler);
/* 579 */       handler.startDocument();
/* 580 */       return handler;
/*     */     }
/* 582 */     catch (Exception e) {
/* 583 */       throw new TransletException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializationHandler openOutputHandler(String filename) throws TransletException {
/* 590 */     return openOutputHandler(filename, false);
/*     */   }
/*     */   
/*     */   public void closeOutputHandler(SerializationHandler handler) {
/*     */     try {
/* 595 */       handler.endDocument();
/* 596 */       handler.close();
/*     */     }
/* 598 */     catch (Exception exception) {}
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
/*     */   public abstract void transform(DOM paramDOM, DTMAxisIterator paramDTMAxisIterator, SerializationHandler paramSerializationHandler) throws TransletException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void transform(DOM document, SerializationHandler handler) throws TransletException {
/*     */     try {
/* 620 */       transform(document, document.getIterator(), handler);
/*     */     } finally {
/* 622 */       this._keyIndexes = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void characters(String string, SerializationHandler handler) throws TransletException {
/* 633 */     if (string != null) {
/*     */       
/*     */       try {
/* 636 */         handler.characters(string);
/* 637 */       } catch (Exception e) {
/* 638 */         throw new TransletException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCdataElement(String name) {
/* 647 */     if (this._cdata == null) {
/* 648 */       this._cdata = new Vector();
/*     */     }
/*     */     
/* 651 */     int lastColon = name.lastIndexOf(':');
/*     */     
/* 653 */     if (lastColon > 0) {
/* 654 */       String uri = name.substring(0, lastColon);
/* 655 */       String localName = name.substring(lastColon + 1);
/* 656 */       this._cdata.addElement(uri);
/* 657 */       this._cdata.addElement(localName);
/*     */     } else {
/* 659 */       this._cdata.addElement(null);
/* 660 */       this._cdata.addElement(name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void transferOutputSettings(SerializationHandler handler) {
/* 668 */     if (this._method != null) {
/* 669 */       if (this._method.equals("xml")) {
/* 670 */         if (this._standalone != null) {
/* 671 */           handler.setStandalone(this._standalone);
/*     */         }
/* 673 */         if (this._omitHeader) {
/* 674 */           handler.setOmitXMLDeclaration(true);
/*     */         }
/* 676 */         handler.setCdataSectionElements(this._cdata);
/* 677 */         if (this._version != null) {
/* 678 */           handler.setVersion(this._version);
/*     */         }
/* 680 */         handler.setIndent(this._indent);
/* 681 */         handler.setIndentAmount(this._indentamount);
/* 682 */         if (this._doctypeSystem != null) {
/* 683 */           handler.setDoctype(this._doctypeSystem, this._doctypePublic);
/*     */         }
/* 685 */         handler.setIsStandalone(this._isStandalone);
/*     */       }
/* 687 */       else if (this._method.equals("html")) {
/* 688 */         handler.setIndent(this._indent);
/* 689 */         handler.setDoctype(this._doctypeSystem, this._doctypePublic);
/* 690 */         if (this._mediaType != null) {
/* 691 */           handler.setMediaType(this._mediaType);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 696 */       handler.setCdataSectionElements(this._cdata);
/* 697 */       if (this._version != null) {
/* 698 */         handler.setVersion(this._version);
/*     */       }
/* 700 */       if (this._standalone != null) {
/* 701 */         handler.setStandalone(this._standalone);
/*     */       }
/* 703 */       if (this._omitHeader) {
/* 704 */         handler.setOmitXMLDeclaration(true);
/*     */       }
/* 706 */       handler.setIndent(this._indent);
/* 707 */       handler.setDoctype(this._doctypeSystem, this._doctypePublic);
/* 708 */       handler.setIsStandalone(this._isStandalone);
/*     */     } 
/*     */   }
/*     */   
/* 712 */   private Map<String, Class<?>> _auxClasses = null;
/*     */   
/*     */   public void addAuxiliaryClass(Class<?> auxClass) {
/* 715 */     if (this._auxClasses == null) this._auxClasses = new HashMap<>(); 
/* 716 */     this._auxClasses.put(auxClass.getName(), auxClass);
/*     */   }
/*     */   
/*     */   public void setAuxiliaryClasses(Map<String, Class<?>> auxClasses) {
/* 720 */     this._auxClasses = auxClasses;
/*     */   }
/*     */   
/*     */   public Class getAuxiliaryClass(String className) {
/* 724 */     if (this._auxClasses == null) return null; 
/* 725 */     return this._auxClasses.get(className);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getNamesArray() {
/* 730 */     return this.namesArray;
/*     */   }
/*     */   
/*     */   public String[] getUrisArray() {
/* 734 */     return this.urisArray;
/*     */   }
/*     */   
/*     */   public int[] getTypesArray() {
/* 738 */     return this.typesArray;
/*     */   }
/*     */   
/*     */   public String[] getNamespaceArray() {
/* 742 */     return this.namespaceArray;
/*     */   }
/*     */   
/*     */   public boolean hasIdCall() {
/* 746 */     return this._hasIdCall;
/*     */   }
/*     */   
/*     */   public Templates getTemplates() {
/* 750 */     return this._templates;
/*     */   }
/*     */   
/*     */   public void setTemplates(Templates templates) {
/* 754 */     this._templates = templates;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overrideDefaultParser() {
/* 760 */     return this._overrideDefaultParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOverrideDefaultParser(boolean flag) {
/* 767 */     this._overrideDefaultParser = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAllowedProtocols() {
/* 774 */     return this._accessExternalStylesheet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowedProtocols(String protocols) {
/* 781 */     this._accessExternalStylesheet = protocols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 787 */   protected DOMImplementation _domImplementation = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public Document newDocument(String uri, String qname) throws ParserConfigurationException {
/* 792 */     if (this._domImplementation == null) {
/* 793 */       DocumentBuilderFactory dbf = JdkXmlUtils.getDOMFactory(this._overrideDefaultParser);
/* 794 */       this._domImplementation = dbf.newDocumentBuilder().getDOMImplementation();
/*     */     } 
/* 796 */     return this._domImplementation.createDocument(uri, qname, null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/runtime/AbstractTranslet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */