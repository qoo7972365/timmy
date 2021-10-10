/*      */ package com.sun.org.apache.xml.internal.serialize;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.dom.AbortException;
/*      */ import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMErrorImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMLocatorImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMNormalizer;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMStringListImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*      */ import com.sun.org.apache.xerces.internal.util.DOMUtil;
/*      */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XML11Char;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.StringWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Comment;
/*      */ import org.w3c.dom.DOMConfiguration;
/*      */ import org.w3c.dom.DOMErrorHandler;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.DOMStringList;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentFragment;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.ProcessingInstruction;
/*      */ import org.w3c.dom.ls.LSException;
/*      */ import org.w3c.dom.ls.LSOutput;
/*      */ import org.w3c.dom.ls.LSSerializer;
/*      */ import org.w3c.dom.ls.LSSerializerFilter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DOMSerializerImpl
/*      */   implements LSSerializer, DOMConfiguration
/*      */ {
/*      */   private XMLSerializer serializer;
/*      */   private XML11Serializer xml11Serializer;
/*      */   private DOMStringList fRecognizedParameters;
/*  102 */   protected short features = 0;
/*      */   
/*      */   protected static final short NAMESPACES = 1;
/*      */   
/*      */   protected static final short WELLFORMED = 2;
/*      */   
/*      */   protected static final short ENTITIES = 4;
/*      */   protected static final short CDATA = 8;
/*      */   protected static final short SPLITCDATA = 16;
/*      */   protected static final short COMMENTS = 32;
/*      */   protected static final short DISCARDDEFAULT = 64;
/*      */   protected static final short INFOSET = 128;
/*      */   protected static final short XMLDECL = 256;
/*      */   protected static final short NSDECL = 512;
/*      */   protected static final short DOM_ELEMENT_CONTENT_WHITESPACE = 1024;
/*      */   protected static final short FORMAT_PRETTY_PRINT = 2048;
/*  118 */   private DOMErrorHandler fErrorHandler = null;
/*  119 */   private final DOMErrorImpl fError = new DOMErrorImpl();
/*  120 */   private final DOMLocatorImpl fLocator = new DOMLocatorImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DOMSerializerImpl() {
/*  130 */     this.features = (short)(this.features | 0x1);
/*  131 */     this.features = (short)(this.features | 0x4);
/*  132 */     this.features = (short)(this.features | 0x20);
/*  133 */     this.features = (short)(this.features | 0x8);
/*  134 */     this.features = (short)(this.features | 0x10);
/*  135 */     this.features = (short)(this.features | 0x2);
/*  136 */     this.features = (short)(this.features | 0x200);
/*  137 */     this.features = (short)(this.features | 0x400);
/*  138 */     this.features = (short)(this.features | 0x40);
/*  139 */     this.features = (short)(this.features | 0x100);
/*      */     
/*  141 */     this.serializer = new XMLSerializer();
/*  142 */     initSerializer(this.serializer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DOMConfiguration getDomConfig() {
/*  152 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParameter(String name, Object value) throws DOMException {
/*  159 */     if (value instanceof Boolean)
/*  160 */     { boolean state = ((Boolean)value).booleanValue();
/*  161 */       if (name.equalsIgnoreCase("infoset")) {
/*  162 */         if (state) {
/*  163 */           this.features = (short)(this.features & 0xFFFFFFFB);
/*  164 */           this.features = (short)(this.features & 0xFFFFFFF7);
/*  165 */           this.features = (short)(this.features | 0x1);
/*  166 */           this.features = (short)(this.features | 0x200);
/*  167 */           this.features = (short)(this.features | 0x2);
/*  168 */           this.features = (short)(this.features | 0x20);
/*      */         }
/*      */       
/*  171 */       } else if (name.equalsIgnoreCase("xml-declaration")) {
/*  172 */         this.features = (short)(state ? (this.features | 0x100) : (this.features & 0xFFFFFEFF));
/*      */       }
/*  174 */       else if (name.equalsIgnoreCase("namespaces")) {
/*  175 */         this.features = (short)(state ? (this.features | 0x1) : (this.features & 0xFFFFFFFE));
/*      */ 
/*      */ 
/*      */         
/*  179 */         this.serializer.fNamespaces = state;
/*  180 */       } else if (name.equalsIgnoreCase("split-cdata-sections")) {
/*  181 */         this.features = (short)(state ? (this.features | 0x10) : (this.features & 0xFFFFFFEF));
/*      */ 
/*      */       
/*      */       }
/*  185 */       else if (name.equalsIgnoreCase("discard-default-content")) {
/*  186 */         this.features = (short)(state ? (this.features | 0x40) : (this.features & 0xFFFFFFBF));
/*      */ 
/*      */       
/*      */       }
/*  190 */       else if (name.equalsIgnoreCase("well-formed")) {
/*  191 */         this.features = (short)(state ? (this.features | 0x2) : (this.features & 0xFFFFFFFD));
/*      */ 
/*      */       
/*      */       }
/*  195 */       else if (name.equalsIgnoreCase("entities")) {
/*  196 */         this.features = (short)(state ? (this.features | 0x4) : (this.features & 0xFFFFFFFB));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  201 */       else if (name.equalsIgnoreCase("cdata-sections")) {
/*  202 */         this.features = (short)(state ? (this.features | 0x8) : (this.features & 0xFFFFFFF7));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  207 */       else if (name.equalsIgnoreCase("comments")) {
/*  208 */         this.features = (short)(state ? (this.features | 0x20) : (this.features & 0xFFFFFFDF));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  213 */       else if (name.equalsIgnoreCase("format-pretty-print")) {
/*  214 */         this.features = (short)(state ? (this.features | 0x800) : (this.features & 0xFFFFF7FF));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  219 */       else if (name.equalsIgnoreCase("canonical-form") || name
/*  220 */         .equalsIgnoreCase("validate-if-schema") || name
/*  221 */         .equalsIgnoreCase("validate") || name
/*  222 */         .equalsIgnoreCase("check-character-normalization") || name
/*  223 */         .equalsIgnoreCase("datatype-normalization")) {
/*      */ 
/*      */         
/*  226 */         if (state) {
/*      */           
/*  228 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  232 */           throw new DOMException((short)9, msg);
/*      */         } 
/*  234 */       } else if (name
/*  235 */         .equalsIgnoreCase("namespace-declarations")) {
/*      */         
/*  237 */         this.features = (short)(state ? (this.features | 0x200) : (this.features & 0xFFFFFDFF));
/*      */ 
/*      */ 
/*      */         
/*  241 */         this.serializer.fNamespacePrefixes = state;
/*  242 */       } else if (name.equalsIgnoreCase("element-content-whitespace") || name
/*  243 */         .equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
/*      */         
/*  245 */         if (!state) {
/*      */           
/*  247 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */           
/*  251 */           throw new DOMException((short)9, msg);
/*      */         } 
/*      */       } else {
/*      */         
/*  255 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { name });
/*      */ 
/*      */ 
/*      */         
/*  259 */         throw new DOMException((short)9, msg);
/*      */       }  }
/*  261 */     else if (name.equalsIgnoreCase("error-handler"))
/*  262 */     { if (value == null || value instanceof DOMErrorHandler) {
/*  263 */         this.fErrorHandler = (DOMErrorHandler)value;
/*      */       } else {
/*      */         
/*  266 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { name });
/*      */ 
/*      */ 
/*      */         
/*  270 */         throw new DOMException((short)17, msg);
/*      */       }  }
/*  272 */     else { if (name
/*  273 */         .equalsIgnoreCase("resource-resolver") || name
/*  274 */         .equalsIgnoreCase("schema-location") || name
/*  275 */         .equalsIgnoreCase("schema-type") || (name
/*  276 */         .equalsIgnoreCase("normalize-characters") && value != null)) {
/*      */ 
/*      */         
/*  279 */         String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */         
/*  283 */         throw new DOMException((short)9, str);
/*      */       } 
/*      */       
/*  286 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { name });
/*      */ 
/*      */ 
/*      */       
/*  290 */       throw new DOMException((short)8, msg); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSetParameter(String name, Object state) {
/*  299 */     if (state == null) {
/*  300 */       return true;
/*      */     }
/*      */     
/*  303 */     if (state instanceof Boolean) {
/*  304 */       boolean value = ((Boolean)state).booleanValue();
/*      */       
/*  306 */       if (name.equalsIgnoreCase("namespaces") || name
/*  307 */         .equalsIgnoreCase("split-cdata-sections") || name
/*  308 */         .equalsIgnoreCase("discard-default-content") || name
/*  309 */         .equalsIgnoreCase("xml-declaration") || name
/*  310 */         .equalsIgnoreCase("well-formed") || name
/*  311 */         .equalsIgnoreCase("infoset") || name
/*  312 */         .equalsIgnoreCase("entities") || name
/*  313 */         .equalsIgnoreCase("cdata-sections") || name
/*  314 */         .equalsIgnoreCase("comments") || name
/*  315 */         .equalsIgnoreCase("namespace-declarations") || name
/*  316 */         .equalsIgnoreCase("format-pretty-print"))
/*      */       {
/*  318 */         return true; } 
/*  319 */       if (name.equalsIgnoreCase("canonical-form") || name
/*  320 */         .equalsIgnoreCase("validate-if-schema") || name
/*  321 */         .equalsIgnoreCase("validate") || name
/*  322 */         .equalsIgnoreCase("check-character-normalization") || name
/*  323 */         .equalsIgnoreCase("datatype-normalization"))
/*      */       {
/*      */         
/*  326 */         return !value; } 
/*  327 */       if (name.equalsIgnoreCase("element-content-whitespace") || name
/*  328 */         .equalsIgnoreCase("ignore-unknown-character-denormalizations"))
/*      */       {
/*  330 */         return value;
/*      */       }
/*  332 */     } else if ((name.equalsIgnoreCase("error-handler") && state == null) || state instanceof DOMErrorHandler) {
/*      */       
/*  334 */       return true;
/*      */     } 
/*      */     
/*  337 */     return false;
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
/*      */   public DOMStringList getParameterNames() {
/*  350 */     if (this.fRecognizedParameters == null) {
/*  351 */       Vector<String> parameters = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  356 */       parameters.add("namespaces");
/*  357 */       parameters.add("split-cdata-sections");
/*  358 */       parameters.add("discard-default-content");
/*  359 */       parameters.add("xml-declaration");
/*  360 */       parameters.add("canonical-form");
/*  361 */       parameters.add("validate-if-schema");
/*  362 */       parameters.add("validate");
/*  363 */       parameters.add("check-character-normalization");
/*  364 */       parameters.add("datatype-normalization");
/*  365 */       parameters.add("format-pretty-print");
/*      */       
/*  367 */       parameters.add("well-formed");
/*  368 */       parameters.add("infoset");
/*  369 */       parameters.add("namespace-declarations");
/*  370 */       parameters.add("element-content-whitespace");
/*  371 */       parameters.add("entities");
/*  372 */       parameters.add("cdata-sections");
/*  373 */       parameters.add("comments");
/*  374 */       parameters.add("ignore-unknown-character-denormalizations");
/*  375 */       parameters.add("error-handler");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  381 */       this.fRecognizedParameters = new DOMStringListImpl(parameters);
/*      */     } 
/*      */ 
/*      */     
/*  385 */     return this.fRecognizedParameters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getParameter(String name) throws DOMException {
/*  393 */     if (name.equalsIgnoreCase("normalize-characters"))
/*  394 */       return null; 
/*  395 */     if (name.equalsIgnoreCase("comments"))
/*  396 */       return ((this.features & 0x20) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  397 */     if (name.equalsIgnoreCase("namespaces"))
/*  398 */       return ((this.features & 0x1) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  399 */     if (name.equalsIgnoreCase("xml-declaration"))
/*  400 */       return ((this.features & 0x100) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  401 */     if (name.equalsIgnoreCase("cdata-sections"))
/*  402 */       return ((this.features & 0x8) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  403 */     if (name.equalsIgnoreCase("entities"))
/*  404 */       return ((this.features & 0x4) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  405 */     if (name.equalsIgnoreCase("split-cdata-sections"))
/*  406 */       return ((this.features & 0x10) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  407 */     if (name.equalsIgnoreCase("well-formed"))
/*  408 */       return ((this.features & 0x2) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  409 */     if (name.equalsIgnoreCase("namespace-declarations"))
/*  410 */       return ((this.features & 0x200) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  411 */     if (name.equalsIgnoreCase("format-pretty-print"))
/*  412 */       return ((this.features & 0x800) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  413 */     if (name.equalsIgnoreCase("element-content-whitespace") || name
/*  414 */       .equalsIgnoreCase("ignore-unknown-character-denormalizations"))
/*  415 */       return Boolean.TRUE; 
/*  416 */     if (name.equalsIgnoreCase("discard-default-content"))
/*  417 */       return ((this.features & 0x40) != 0) ? Boolean.TRUE : Boolean.FALSE; 
/*  418 */     if (name.equalsIgnoreCase("infoset")) {
/*  419 */       if ((this.features & 0x4) == 0 && (this.features & 0x8) == 0 && (this.features & 0x1) != 0 && (this.features & 0x200) != 0 && (this.features & 0x2) != 0 && (this.features & 0x20) != 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  425 */         return Boolean.TRUE;
/*      */       }
/*  427 */       return Boolean.FALSE;
/*  428 */     }  if (name.equalsIgnoreCase("canonical-form") || name
/*  429 */       .equalsIgnoreCase("validate-if-schema") || name
/*  430 */       .equalsIgnoreCase("check-character-normalization") || name
/*  431 */       .equalsIgnoreCase("validate") || name
/*  432 */       .equalsIgnoreCase("validate-if-schema") || name
/*  433 */       .equalsIgnoreCase("datatype-normalization"))
/*  434 */       return Boolean.FALSE; 
/*  435 */     if (name.equalsIgnoreCase("error-handler"))
/*  436 */       return this.fErrorHandler; 
/*  437 */     if (name
/*  438 */       .equalsIgnoreCase("resource-resolver") || name
/*  439 */       .equalsIgnoreCase("schema-location") || name
/*  440 */       .equalsIgnoreCase("schema-type")) {
/*      */       
/*  442 */       String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { name });
/*      */ 
/*      */ 
/*      */       
/*  446 */       throw new DOMException((short)9, str);
/*      */     } 
/*      */     
/*  449 */     String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { name });
/*      */ 
/*      */ 
/*      */     
/*  453 */     throw new DOMException((short)8, msg);
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
/*      */   public String writeToString(Node wnode) throws DOMException, LSException {
/*  479 */     Document doc = (wnode.getNodeType() == 9) ? (Document)wnode : wnode.getOwnerDocument();
/*  480 */     Method getVersion = null;
/*  481 */     XMLSerializer ser = null;
/*  482 */     String ver = null;
/*      */     
/*      */     try {
/*  485 */       getVersion = doc.getClass().getMethod("getXmlVersion", new Class[0]);
/*  486 */       if (getVersion != null) {
/*  487 */         ver = (String)getVersion.invoke(doc, (Object[])null);
/*      */       }
/*  489 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/*  493 */     if (ver != null && ver.equals("1.1")) {
/*  494 */       if (this.xml11Serializer == null) {
/*  495 */         this.xml11Serializer = new XML11Serializer();
/*  496 */         initSerializer(this.xml11Serializer);
/*      */       } 
/*      */       
/*  499 */       copySettings(this.serializer, this.xml11Serializer);
/*  500 */       ser = this.xml11Serializer;
/*      */     } else {
/*  502 */       ser = this.serializer;
/*      */     } 
/*      */     
/*  505 */     StringWriter destination = new StringWriter();
/*      */     try {
/*  507 */       prepareForSerialization(ser, wnode);
/*  508 */       ser._format.setEncoding("UTF-16");
/*  509 */       ser.setOutputCharStream(destination);
/*  510 */       if (wnode.getNodeType() == 9) {
/*  511 */         ser.serialize((Document)wnode);
/*      */       }
/*  513 */       else if (wnode.getNodeType() == 11) {
/*  514 */         ser.serialize((DocumentFragment)wnode);
/*      */       }
/*  516 */       else if (wnode.getNodeType() == 1) {
/*  517 */         ser.serialize((Element)wnode);
/*      */       }
/*  519 */       else if (wnode.getNodeType() == 3 || wnode
/*  520 */         .getNodeType() == 8 || wnode
/*  521 */         .getNodeType() == 5 || wnode
/*  522 */         .getNodeType() == 4 || wnode
/*  523 */         .getNodeType() == 7) {
/*  524 */         ser.serialize(wnode);
/*      */       } else {
/*      */         
/*  527 */         String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "unable-to-serialize-node", null);
/*      */ 
/*      */         
/*  530 */         if (ser.fDOMErrorHandler != null) {
/*  531 */           DOMErrorImpl error = new DOMErrorImpl();
/*  532 */           error.fType = "unable-to-serialize-node";
/*  533 */           error.fMessage = msg;
/*  534 */           error.fSeverity = 3;
/*  535 */           ser.fDOMErrorHandler.handleError(error);
/*      */         } 
/*  537 */         throw new LSException((short)82, msg);
/*      */       } 
/*  539 */     } catch (LSException lse) {
/*      */       
/*  541 */       throw lse;
/*  542 */     } catch (AbortException e) {
/*  543 */       return null;
/*  544 */     } catch (RuntimeException e) {
/*  545 */       throw (LSException)(new LSException((short)82, e.toString())).initCause(e);
/*  546 */     } catch (IOException ioe) {
/*      */ 
/*      */ 
/*      */       
/*  550 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "STRING_TOO_LONG", new Object[] { ioe
/*      */ 
/*      */             
/*  553 */             .getMessage() });
/*  554 */       throw (DOMException)(new DOMException((short)2, msg)).initCause(ioe);
/*      */     } 
/*      */     
/*  557 */     return destination.toString();
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
/*      */   public void setNewLine(String newLine) {
/*  584 */     this.serializer._format.setLineSeparator(newLine);
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
/*      */   public String getNewLine() {
/*  612 */     return this.serializer._format.getLineSeparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LSSerializerFilter getFilter() {
/*  623 */     return this.serializer.fDOMFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFilter(LSSerializerFilter filter) {
/*  632 */     this.serializer.fDOMFilter = filter;
/*      */   }
/*      */ 
/*      */   
/*      */   private void initSerializer(XMLSerializer ser) {
/*  637 */     ser.fNSBinder = new NamespaceSupport();
/*  638 */     ser.fLocalNSBinder = new NamespaceSupport();
/*  639 */     ser.fSymbolTable = new SymbolTable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void copySettings(XMLSerializer src, XMLSerializer dest) {
/*  648 */     dest.fDOMErrorHandler = this.fErrorHandler;
/*  649 */     dest._format.setEncoding(src._format.getEncoding());
/*  650 */     dest._format.setLineSeparator(src._format.getLineSeparator());
/*  651 */     dest.fDOMFilter = src.fDOMFilter;
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
/*      */   public boolean write(Node node, LSOutput destination) throws LSException {
/*  686 */     if (node == null) {
/*  687 */       return false;
/*      */     }
/*  689 */     Method getVersion = null;
/*  690 */     XMLSerializer ser = null;
/*  691 */     String ver = null;
/*      */ 
/*      */     
/*  694 */     Document fDocument = (node.getNodeType() == 9) ? (Document)node : node.getOwnerDocument();
/*      */     
/*      */     try {
/*  697 */       getVersion = fDocument.getClass().getMethod("getXmlVersion", new Class[0]);
/*  698 */       if (getVersion != null) {
/*  699 */         ver = (String)getVersion.invoke(fDocument, (Object[])null);
/*      */       }
/*  701 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  706 */     if (ver != null && ver.equals("1.1")) {
/*  707 */       if (this.xml11Serializer == null) {
/*  708 */         this.xml11Serializer = new XML11Serializer();
/*  709 */         initSerializer(this.xml11Serializer);
/*      */       } 
/*      */       
/*  712 */       copySettings(this.serializer, this.xml11Serializer);
/*  713 */       ser = this.xml11Serializer;
/*      */     } else {
/*  715 */       ser = this.serializer;
/*      */     } 
/*      */     
/*  718 */     String encoding = null;
/*  719 */     if ((encoding = destination.getEncoding()) == null) {
/*      */       
/*      */       try {
/*  722 */         Method getEncoding = fDocument.getClass().getMethod("getInputEncoding", new Class[0]);
/*  723 */         if (getEncoding != null) {
/*  724 */           encoding = (String)getEncoding.invoke(fDocument, (Object[])null);
/*      */         }
/*  726 */       } catch (Exception exception) {}
/*      */ 
/*      */       
/*  729 */       if (encoding == null) {
/*      */         
/*      */         try {
/*  732 */           Method getEncoding = fDocument.getClass().getMethod("getXmlEncoding", new Class[0]);
/*  733 */           if (getEncoding != null) {
/*  734 */             encoding = (String)getEncoding.invoke(fDocument, (Object[])null);
/*      */           }
/*  736 */         } catch (Exception exception) {}
/*      */ 
/*      */         
/*  739 */         if (encoding == null) {
/*  740 */           encoding = "UTF-8";
/*      */         }
/*      */       } 
/*      */     } 
/*      */     try {
/*  745 */       prepareForSerialization(ser, node);
/*  746 */       ser._format.setEncoding(encoding);
/*  747 */       OutputStream outputStream = destination.getByteStream();
/*  748 */       Writer writer = destination.getCharacterStream();
/*  749 */       String uri = destination.getSystemId();
/*  750 */       if (writer == null) {
/*  751 */         if (outputStream == null) {
/*  752 */           if (uri == null) {
/*  753 */             String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "no-output-specified", null);
/*      */ 
/*      */             
/*  756 */             if (ser.fDOMErrorHandler != null) {
/*  757 */               DOMErrorImpl error = new DOMErrorImpl();
/*  758 */               error.fType = "no-output-specified";
/*  759 */               error.fMessage = msg;
/*  760 */               error.fSeverity = 3;
/*  761 */               ser.fDOMErrorHandler.handleError(error);
/*      */             } 
/*  763 */             throw new LSException((short)82, msg);
/*      */           } 
/*      */ 
/*      */           
/*  767 */           String expanded = XMLEntityManager.expandSystemId(uri, null, true);
/*  768 */           URL url = new URL((expanded != null) ? expanded : uri);
/*  769 */           OutputStream out = null;
/*  770 */           String protocol = url.getProtocol();
/*  771 */           String host = url.getHost();
/*      */           
/*  773 */           if (protocol.equals("file") && (host == null || host
/*  774 */             .length() == 0 || host.equals("localhost"))) {
/*  775 */             out = new FileOutputStream(getPathWithoutEscapes(url.getFile()));
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  780 */             URLConnection urlCon = url.openConnection();
/*  781 */             urlCon.setDoInput(false);
/*  782 */             urlCon.setDoOutput(true);
/*  783 */             urlCon.setUseCaches(false);
/*  784 */             if (urlCon instanceof HttpURLConnection) {
/*      */ 
/*      */               
/*  787 */               HttpURLConnection httpCon = (HttpURLConnection)urlCon;
/*  788 */               httpCon.setRequestMethod("PUT");
/*      */             } 
/*  790 */             out = urlCon.getOutputStream();
/*      */           } 
/*  792 */           ser.setOutputByteStream(out);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  797 */           ser.setOutputByteStream(outputStream);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  802 */         ser.setOutputCharStream(writer);
/*      */       } 
/*      */       
/*  805 */       if (node.getNodeType() == 9)
/*  806 */       { ser.serialize((Document)node); }
/*  807 */       else if (node.getNodeType() == 11)
/*  808 */       { ser.serialize((DocumentFragment)node); }
/*  809 */       else if (node.getNodeType() == 1)
/*  810 */       { ser.serialize((Element)node); }
/*  811 */       else if (node.getNodeType() == 3 || node
/*  812 */         .getNodeType() == 8 || node
/*  813 */         .getNodeType() == 5 || node
/*  814 */         .getNodeType() == 4 || node
/*  815 */         .getNodeType() == 7)
/*  816 */       { ser.serialize(node); }
/*      */       else
/*      */       
/*  819 */       { return false; } 
/*  820 */     } catch (UnsupportedEncodingException ue) {
/*  821 */       if (ser.fDOMErrorHandler != null) {
/*  822 */         DOMErrorImpl error = new DOMErrorImpl();
/*  823 */         error.fException = ue;
/*  824 */         error.fType = "unsupported-encoding";
/*  825 */         error.fMessage = ue.getMessage();
/*  826 */         error.fSeverity = 3;
/*  827 */         ser.fDOMErrorHandler.handleError(error);
/*      */       } 
/*  829 */       throw new LSException((short)82, 
/*  830 */           DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "unsupported-encoding", null));
/*      */ 
/*      */     
/*      */     }
/*  834 */     catch (LSException lse) {
/*      */       
/*  836 */       throw lse;
/*  837 */     } catch (AbortException e) {
/*  838 */       return false;
/*  839 */     } catch (RuntimeException e) {
/*  840 */       throw (LSException)DOMUtil.createLSException((short)82, e).fillInStackTrace();
/*  841 */     } catch (Exception e) {
/*  842 */       if (ser.fDOMErrorHandler != null) {
/*  843 */         DOMErrorImpl error = new DOMErrorImpl();
/*  844 */         error.fException = e;
/*  845 */         error.fMessage = e.getMessage();
/*  846 */         error.fSeverity = 2;
/*  847 */         ser.fDOMErrorHandler.handleError(error);
/*      */       } 
/*      */       
/*  850 */       throw (LSException)DOMUtil.createLSException((short)82, e).fillInStackTrace();
/*      */     } 
/*  852 */     return true;
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
/*      */   public boolean writeToURI(Node node, String URI) throws LSException {
/*  882 */     if (node == null) {
/*  883 */       return false;
/*      */     }
/*      */     
/*  886 */     Method getXmlVersion = null;
/*  887 */     XMLSerializer ser = null;
/*  888 */     String ver = null;
/*  889 */     String encoding = null;
/*      */ 
/*      */ 
/*      */     
/*  893 */     Document fDocument = (node.getNodeType() == 9) ? (Document)node : node.getOwnerDocument();
/*      */ 
/*      */     
/*      */     try {
/*  897 */       getXmlVersion = fDocument.getClass().getMethod("getXmlVersion", new Class[0]);
/*  898 */       if (getXmlVersion != null) {
/*  899 */         ver = (String)getXmlVersion.invoke(fDocument, (Object[])null);
/*      */       }
/*  901 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/*  905 */     if (ver != null && ver.equals("1.1")) {
/*  906 */       if (this.xml11Serializer == null) {
/*  907 */         this.xml11Serializer = new XML11Serializer();
/*  908 */         initSerializer(this.xml11Serializer);
/*      */       } 
/*      */       
/*  911 */       copySettings(this.serializer, this.xml11Serializer);
/*  912 */       ser = this.xml11Serializer;
/*      */     } else {
/*  914 */       ser = this.serializer;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  919 */       Method getEncoding = fDocument.getClass().getMethod("getInputEncoding", new Class[0]);
/*  920 */       if (getEncoding != null) {
/*  921 */         encoding = (String)getEncoding.invoke(fDocument, (Object[])null);
/*      */       }
/*  923 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  926 */     if (encoding == null) {
/*      */       
/*      */       try {
/*  929 */         Method getEncoding = fDocument.getClass().getMethod("getXmlEncoding", new Class[0]);
/*  930 */         if (getEncoding != null) {
/*  931 */           encoding = (String)getEncoding.invoke(fDocument, (Object[])null);
/*      */         }
/*  933 */       } catch (Exception exception) {}
/*      */ 
/*      */       
/*  936 */       if (encoding == null) {
/*  937 */         encoding = "UTF-8";
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  942 */       prepareForSerialization(ser, node);
/*  943 */       ser._format.setEncoding(encoding);
/*      */ 
/*      */       
/*  946 */       String expanded = XMLEntityManager.expandSystemId(URI, null, true);
/*  947 */       URL url = new URL((expanded != null) ? expanded : URI);
/*  948 */       OutputStream out = null;
/*  949 */       String protocol = url.getProtocol();
/*  950 */       String host = url.getHost();
/*      */       
/*  952 */       if (protocol.equals("file") && (host == null || host
/*  953 */         .length() == 0 || host.equals("localhost"))) {
/*  954 */         out = new FileOutputStream(getPathWithoutEscapes(url.getFile()));
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  959 */         URLConnection urlCon = url.openConnection();
/*  960 */         urlCon.setDoInput(false);
/*  961 */         urlCon.setDoOutput(true);
/*  962 */         urlCon.setUseCaches(false);
/*  963 */         if (urlCon instanceof HttpURLConnection) {
/*      */ 
/*      */           
/*  966 */           HttpURLConnection httpCon = (HttpURLConnection)urlCon;
/*  967 */           httpCon.setRequestMethod("PUT");
/*      */         } 
/*  969 */         out = urlCon.getOutputStream();
/*      */       } 
/*  971 */       ser.setOutputByteStream(out);
/*      */       
/*  973 */       if (node.getNodeType() == 9)
/*  974 */       { ser.serialize((Document)node); }
/*  975 */       else if (node.getNodeType() == 11)
/*  976 */       { ser.serialize((DocumentFragment)node); }
/*  977 */       else if (node.getNodeType() == 1)
/*  978 */       { ser.serialize((Element)node); }
/*  979 */       else if (node.getNodeType() == 3 || node
/*  980 */         .getNodeType() == 8 || node
/*  981 */         .getNodeType() == 5 || node
/*  982 */         .getNodeType() == 4 || node
/*  983 */         .getNodeType() == 7)
/*  984 */       { ser.serialize(node); }
/*      */       else
/*      */       
/*  987 */       { return false; } 
/*  988 */     } catch (LSException lse) {
/*      */       
/*  990 */       throw lse;
/*  991 */     } catch (AbortException e) {
/*  992 */       return false;
/*  993 */     } catch (RuntimeException e) {
/*  994 */       throw (LSException)DOMUtil.createLSException((short)82, e).fillInStackTrace();
/*  995 */     } catch (Exception e) {
/*  996 */       if (ser.fDOMErrorHandler != null) {
/*  997 */         DOMErrorImpl error = new DOMErrorImpl();
/*  998 */         error.fException = e;
/*  999 */         error.fMessage = e.getMessage();
/* 1000 */         error.fSeverity = 2;
/* 1001 */         ser.fDOMErrorHandler.handleError(error);
/*      */       } 
/* 1003 */       throw (LSException)DOMUtil.createLSException((short)82, e).fillInStackTrace();
/*      */     } 
/* 1005 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void prepareForSerialization(XMLSerializer ser, Node node) {
/* 1014 */     ser.reset();
/* 1015 */     ser.features = this.features;
/* 1016 */     ser.fDOMErrorHandler = this.fErrorHandler;
/* 1017 */     ser.fNamespaces = ((this.features & 0x1) != 0);
/* 1018 */     ser.fNamespacePrefixes = ((this.features & 0x200) != 0);
/* 1019 */     ser._format.setOmitComments(((this.features & 0x20) == 0));
/* 1020 */     ser._format.setOmitXMLDeclaration(((this.features & 0x100) == 0));
/* 1021 */     ser._format.setIndenting(((this.features & 0x800) != 0));
/*      */     
/* 1023 */     if ((this.features & 0x2) != 0) {
/*      */ 
/*      */ 
/*      */       
/* 1027 */       Node root = node;
/*      */       
/* 1029 */       boolean verifyNames = true;
/*      */ 
/*      */       
/* 1032 */       Document document = (node.getNodeType() == 9) ? (Document)node : node.getOwnerDocument();
/*      */       try {
/* 1034 */         Method versionChanged = document.getClass().getMethod("isXMLVersionChanged()", new Class[0]);
/* 1035 */         if (versionChanged != null) {
/* 1036 */           verifyNames = ((Boolean)versionChanged.invoke(document, (Object[])null)).booleanValue();
/*      */         }
/* 1038 */       } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */       
/* 1042 */       if (node.getFirstChild() != null) {
/* 1043 */         while (node != null) {
/* 1044 */           verify(node, verifyNames, false);
/*      */           
/* 1046 */           Node next = node.getFirstChild();
/*      */           
/* 1048 */           while (next == null) {
/*      */             
/* 1050 */             next = node.getNextSibling();
/* 1051 */             if (next == null) {
/* 1052 */               node = node.getParentNode();
/* 1053 */               if (root == node) {
/* 1054 */                 next = null;
/*      */                 break;
/*      */               } 
/* 1057 */               next = node.getNextSibling();
/*      */             } 
/*      */           } 
/* 1060 */           node = next;
/*      */         } 
/*      */       } else {
/*      */         
/* 1064 */         verify(node, verifyNames, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void verify(Node node, boolean verifyNames, boolean xml11Version) {
/*      */     NamedNodeMap attributes;
/*      */     ProcessingInstruction pinode;
/*      */     String target;
/* 1072 */     int type = node.getNodeType();
/* 1073 */     this.fLocator.fRelatedNode = node;
/*      */     
/* 1075 */     switch (type) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1083 */         if (verifyNames) {
/* 1084 */           boolean wellformed; if ((this.features & 0x1) != 0) {
/* 1085 */             wellformed = CoreDocumentImpl.isValidQName(node.getPrefix(), node.getLocalName(), xml11Version);
/*      */           } else {
/*      */             
/* 1088 */             wellformed = CoreDocumentImpl.isXMLName(node.getNodeName(), xml11Version);
/*      */           } 
/* 1090 */           if (!wellformed && 
/* 1091 */             !wellformed && 
/* 1092 */             this.fErrorHandler != null) {
/* 1093 */             String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] { "Element", node
/*      */ 
/*      */                   
/* 1096 */                   .getNodeName() });
/* 1097 */             DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)3, "wf-invalid-character-in-node-name");
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1105 */         attributes = node.hasAttributes() ? node.getAttributes() : null;
/* 1106 */         if (attributes != null) {
/* 1107 */           for (int i = 0; i < attributes.getLength(); i++) {
/* 1108 */             Attr attr = (Attr)attributes.item(i);
/* 1109 */             this.fLocator.fRelatedNode = attr;
/* 1110 */             DOMNormalizer.isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, attributes, attr, attr
/* 1111 */                 .getValue(), xml11Version);
/* 1112 */             if (verifyNames) {
/* 1113 */               boolean wellformed = CoreDocumentImpl.isXMLName(attr.getNodeName(), xml11Version);
/* 1114 */               if (!wellformed) {
/*      */                 
/* 1116 */                 String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] {
/*      */ 
/*      */                       
/* 1119 */                       "Attr", node.getNodeName() });
/* 1120 */                 DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)3, "wf-invalid-character-in-node-name");
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 1133 */         if ((this.features & 0x20) != 0) {
/* 1134 */           DOMNormalizer.isCommentWF(this.fErrorHandler, this.fError, this.fLocator, ((Comment)node).getData(), xml11Version);
/*      */         }
/*      */         break;
/*      */       
/*      */       case 5:
/* 1139 */         if (verifyNames && (this.features & 0x4) != 0) {
/* 1140 */           CoreDocumentImpl.isXMLName(node.getNodeName(), xml11Version);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1147 */         DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, node.getNodeValue(), xml11Version);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1152 */         DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, node.getNodeValue(), xml11Version);
/*      */         break;
/*      */       
/*      */       case 7:
/* 1156 */         pinode = (ProcessingInstruction)node;
/* 1157 */         target = pinode.getTarget();
/* 1158 */         if (verifyNames) {
/* 1159 */           boolean wellformed; if (xml11Version) {
/* 1160 */             wellformed = XML11Char.isXML11ValidName(target);
/*      */           } else {
/* 1162 */             wellformed = XMLChar.isValidName(target);
/*      */           } 
/*      */           
/* 1165 */           if (!wellformed) {
/*      */             
/* 1167 */             String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] {
/*      */ 
/*      */                   
/* 1170 */                   "Element", node.getNodeName() });
/* 1171 */             DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)3, "wf-invalid-character-in-node-name");
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1180 */         DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, pinode.getData(), xml11Version);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getPathWithoutEscapes(String origPath) {
/* 1188 */     if (origPath != null && origPath.length() != 0 && origPath.indexOf('%') != -1) {
/*      */       
/* 1190 */       StringTokenizer tokenizer = new StringTokenizer(origPath, "%");
/* 1191 */       StringBuffer result = new StringBuffer(origPath.length());
/* 1192 */       int size = tokenizer.countTokens();
/* 1193 */       result.append(tokenizer.nextToken());
/* 1194 */       for (int i = 1; i < size; i++) {
/* 1195 */         String token = tokenizer.nextToken();
/*      */         
/* 1197 */         result.append((char)Integer.valueOf(token.substring(0, 2), 16).intValue());
/* 1198 */         result.append(token.substring(2));
/*      */       } 
/* 1200 */       return result.toString();
/*      */     } 
/* 1202 */     return origPath;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/DOMSerializerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */