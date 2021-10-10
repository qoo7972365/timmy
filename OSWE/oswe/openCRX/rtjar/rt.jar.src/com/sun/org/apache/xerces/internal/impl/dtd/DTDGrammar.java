/*      */ package com.sun.org.apache.xerces.internal.impl.dtd;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.CMBinOp;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.CMLeaf;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.CMNode;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.CMUniOp;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.ContentModelValidator;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.MixedContentModel;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.models.SimpleContentModel;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.DatatypeValidator;
/*      */ import com.sun.org.apache.xerces.internal.impl.validation.EntityState;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDContentModelSource;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDSource;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DTDGrammar
/*      */   implements XMLDTDHandler, XMLDTDContentModelHandler, EntityState, Grammar
/*      */ {
/*      */   public static final int TOP_LEVEL_SCOPE = -1;
/*      */   private static final int CHUNK_SHIFT = 8;
/*      */   private static final int CHUNK_SIZE = 256;
/*      */   private static final int CHUNK_MASK = 255;
/*      */   private static final int INITIAL_CHUNK_COUNT = 4;
/*      */   private static final short LIST_FLAG = 128;
/*      */   private static final short LIST_MASK = -129;
/*      */   private static final boolean DEBUG = false;
/*  104 */   protected XMLDTDSource fDTDSource = null;
/*  105 */   protected XMLDTDContentModelSource fDTDContentModelSource = null;
/*      */ 
/*      */   
/*      */   protected int fCurrentElementIndex;
/*      */ 
/*      */   
/*      */   protected int fCurrentAttributeIndex;
/*      */ 
/*      */   
/*      */   protected boolean fReadingExternalDTD = false;
/*      */ 
/*      */   
/*      */   private SymbolTable fSymbolTable;
/*      */ 
/*      */   
/*  120 */   protected XMLDTDDescription fGrammarDescription = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  125 */   private int fElementDeclCount = 0;
/*      */ 
/*      */   
/*  128 */   private QName[][] fElementDeclName = new QName[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  134 */   private short[][] fElementDeclType = new short[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  140 */   private int[][] fElementDeclContentSpecIndex = new int[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  146 */   private ContentModelValidator[][] fElementDeclContentModelValidator = new ContentModelValidator[4][];
/*      */ 
/*      */   
/*  149 */   private int[][] fElementDeclFirstAttributeDeclIndex = new int[4][];
/*      */ 
/*      */   
/*  152 */   private int[][] fElementDeclLastAttributeDeclIndex = new int[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   private int fAttributeDeclCount = 0;
/*      */ 
/*      */   
/*  160 */   private QName[][] fAttributeDeclName = new QName[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fIsImmutable = false;
/*      */ 
/*      */ 
/*      */   
/*  169 */   private short[][] fAttributeDeclType = new short[4][];
/*      */ 
/*      */   
/*  172 */   private String[][][] fAttributeDeclEnumeration = new String[4][][];
/*  173 */   private short[][] fAttributeDeclDefaultType = new short[4][];
/*  174 */   private DatatypeValidator[][] fAttributeDeclDatatypeValidator = new DatatypeValidator[4][];
/*  175 */   private String[][] fAttributeDeclDefaultValue = new String[4][];
/*  176 */   private String[][] fAttributeDeclNonNormalizedDefaultValue = new String[4][];
/*  177 */   private int[][] fAttributeDeclNextAttributeDeclIndex = new int[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  185 */   private int fContentSpecCount = 0;
/*  186 */   private short[][] fContentSpecType = new short[4][];
/*  187 */   private Object[][] fContentSpecValue = new Object[4][];
/*  188 */   private Object[][] fContentSpecOtherValue = new Object[4][];
/*      */ 
/*      */ 
/*      */   
/*  192 */   private int fEntityCount = 0;
/*  193 */   private String[][] fEntityName = new String[4][];
/*  194 */   private String[][] fEntityValue = new String[4][];
/*  195 */   private String[][] fEntityPublicId = new String[4][];
/*  196 */   private String[][] fEntitySystemId = new String[4][];
/*  197 */   private String[][] fEntityBaseSystemId = new String[4][];
/*  198 */   private String[][] fEntityNotation = new String[4][];
/*  199 */   private byte[][] fEntityIsPE = new byte[4][];
/*  200 */   private byte[][] fEntityInExternal = new byte[4][];
/*      */ 
/*      */ 
/*      */   
/*  204 */   private int fNotationCount = 0;
/*  205 */   private String[][] fNotationName = new String[4][];
/*  206 */   private String[][] fNotationPublicId = new String[4][];
/*  207 */   private String[][] fNotationSystemId = new String[4][];
/*  208 */   private String[][] fNotationBaseSystemId = new String[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  213 */   private final Map<String, Integer> fElementIndexMap = new HashMap<>();
/*      */ 
/*      */   
/*  216 */   private final Map<String, Integer> fEntityIndexMap = new HashMap<>();
/*      */ 
/*      */   
/*  219 */   private final Map<String, Integer> fNotationIndexMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fMixed;
/*      */ 
/*      */ 
/*      */   
/*  227 */   private final QName fQName = new QName();
/*      */ 
/*      */   
/*  230 */   private final QName fQName2 = new QName();
/*      */ 
/*      */   
/*  233 */   protected final XMLAttributeDecl fAttributeDecl = new XMLAttributeDecl();
/*      */ 
/*      */ 
/*      */   
/*  237 */   private int fLeafCount = 0;
/*  238 */   private int fEpsilonIndex = -1;
/*      */ 
/*      */   
/*  241 */   private XMLElementDecl fElementDecl = new XMLElementDecl();
/*      */ 
/*      */   
/*  244 */   private XMLEntityDecl fEntityDecl = new XMLEntityDecl();
/*      */ 
/*      */   
/*  247 */   private XMLSimpleType fSimpleType = new XMLSimpleType();
/*      */ 
/*      */   
/*  250 */   private XMLContentSpec fContentSpec = new XMLContentSpec();
/*      */ 
/*      */   
/*  253 */   Map<String, XMLElementDecl> fElementDeclTab = new HashMap<>();
/*      */ 
/*      */   
/*  256 */   private short[] fOpStack = null;
/*      */ 
/*      */   
/*  259 */   private int[] fNodeIndexStack = null;
/*      */ 
/*      */   
/*  262 */   private int[] fPrevNodeIndexStack = null;
/*      */ 
/*      */   
/*  265 */   private int fDepth = 0;
/*      */ 
/*      */   
/*  268 */   private boolean[] fPEntityStack = new boolean[4];
/*  269 */   private int fPEDepth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  274 */   private int[][] fElementDeclIsExternal = new int[4][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  280 */   private int[][] fAttributeDeclIsExternal = new int[4][];
/*      */ 
/*      */ 
/*      */   
/*  284 */   int valueIndex = -1;
/*  285 */   int prevNodeIndex = -1;
/*  286 */   int nodeIndex = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTDGrammar(SymbolTable symbolTable, XMLDTDDescription desc) {
/*  294 */     this.fSymbolTable = symbolTable;
/*  295 */     this.fGrammarDescription = desc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLGrammarDescription getGrammarDescription() {
/*  302 */     return this.fGrammarDescription;
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
/*      */   public boolean getElementDeclIsExternal(int elementDeclIndex) {
/*  316 */     if (elementDeclIndex < 0) {
/*  317 */       return false;
/*      */     }
/*      */     
/*  320 */     int chunk = elementDeclIndex >> 8;
/*  321 */     int index = elementDeclIndex & 0xFF;
/*  322 */     return (this.fElementDeclIsExternal[chunk][index] != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAttributeDeclIsExternal(int attributeDeclIndex) {
/*  333 */     if (attributeDeclIndex < 0) {
/*  334 */       return false;
/*      */     }
/*      */     
/*  337 */     int chunk = attributeDeclIndex >> 8;
/*  338 */     int index = attributeDeclIndex & 0xFF;
/*  339 */     return (this.fAttributeDeclIsExternal[chunk][index] != 0);
/*      */   }
/*      */   
/*      */   public int getAttributeDeclIndex(int elementDeclIndex, String attributeDeclName) {
/*  343 */     if (elementDeclIndex == -1) {
/*  344 */       return -1;
/*      */     }
/*  346 */     int attDefIndex = getFirstAttributeDeclIndex(elementDeclIndex);
/*  347 */     while (attDefIndex != -1) {
/*  348 */       getAttributeDecl(attDefIndex, this.fAttributeDecl);
/*      */       
/*  350 */       if (this.fAttributeDecl.name.rawname == attributeDeclName || attributeDeclName
/*  351 */         .equals(this.fAttributeDecl.name.rawname)) {
/*  352 */         return attDefIndex;
/*      */       }
/*  354 */       attDefIndex = getNextAttributeDeclIndex(attDefIndex);
/*      */     } 
/*  356 */     return -1;
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
/*      */   public void startDTD(XMLLocator locator, Augmentations augs) throws XNIException {
/*  379 */     this.fOpStack = null;
/*  380 */     this.fNodeIndexStack = null;
/*  381 */     this.fPrevNodeIndexStack = null;
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
/*      */   public void startParameterEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs) throws XNIException {
/*  410 */     if (this.fPEDepth == this.fPEntityStack.length) {
/*  411 */       boolean[] entityarray = new boolean[this.fPEntityStack.length * 2];
/*  412 */       System.arraycopy(this.fPEntityStack, 0, entityarray, 0, this.fPEntityStack.length);
/*  413 */       this.fPEntityStack = entityarray;
/*      */     } 
/*  415 */     this.fPEntityStack[this.fPEDepth] = this.fReadingExternalDTD;
/*  416 */     this.fPEDepth++;
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
/*      */   public void startExternalSubset(XMLResourceIdentifier identifier, Augmentations augs) throws XNIException {
/*  430 */     this.fReadingExternalDTD = true;
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
/*      */   public void endParameterEntity(String name, Augmentations augs) throws XNIException {
/*  449 */     this.fPEDepth--;
/*  450 */     this.fReadingExternalDTD = this.fPEntityStack[this.fPEDepth];
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
/*      */   public void endExternalSubset(Augmentations augs) throws XNIException {
/*  463 */     this.fReadingExternalDTD = false;
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
/*      */   public void elementDecl(String name, String contentModel, Augmentations augs) throws XNIException {
/*  478 */     XMLElementDecl tmpElementDecl = this.fElementDeclTab.get(name);
/*      */ 
/*      */     
/*  481 */     if (tmpElementDecl != null) {
/*  482 */       if (tmpElementDecl.type == -1) {
/*  483 */         this.fCurrentElementIndex = getElementDeclIndex(name);
/*      */       }
/*      */       else {
/*      */         
/*      */         return;
/*      */       } 
/*      */     } else {
/*      */       
/*  491 */       this.fCurrentElementIndex = createElementDecl();
/*      */     } 
/*      */     
/*  494 */     XMLElementDecl elementDecl = new XMLElementDecl();
/*      */     
/*  496 */     this.fQName.setValues(null, name, name, null);
/*      */     
/*  498 */     elementDecl.name.setValues(this.fQName);
/*      */     
/*  500 */     elementDecl.contentModelValidator = null;
/*  501 */     elementDecl.scope = -1;
/*  502 */     if (contentModel.equals("EMPTY")) {
/*  503 */       elementDecl.type = 1;
/*      */     }
/*  505 */     else if (contentModel.equals("ANY")) {
/*  506 */       elementDecl.type = 0;
/*      */     }
/*  508 */     else if (contentModel.startsWith("(")) {
/*  509 */       if (contentModel.indexOf("#PCDATA") > 0) {
/*  510 */         elementDecl.type = 2;
/*      */       } else {
/*      */         
/*  513 */         elementDecl.type = 3;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  519 */     this.fElementDeclTab.put(name, elementDecl);
/*      */     
/*  521 */     this.fElementDecl = elementDecl;
/*  522 */     addContentSpecToElement(elementDecl);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  529 */     setElementDecl(this.fCurrentElementIndex, this.fElementDecl);
/*      */     
/*  531 */     int chunk = this.fCurrentElementIndex >> 8;
/*  532 */     int index = this.fCurrentElementIndex & 0xFF;
/*  533 */     ensureElementDeclCapacity(chunk);
/*  534 */     this.fElementDeclIsExternal[chunk][index] = (this.fReadingExternalDTD || this.fPEDepth > 0) ? 1 : 0;
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
/*      */   public void attributeDecl(String elementName, String attributeName, String type, String[] enumeration, String defaultType, XMLString defaultValue, XMLString nonNormalizedDefaultValue, Augmentations augs) throws XNIException {
/*  568 */     if (!this.fElementDeclTab.containsKey(elementName)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  574 */       this.fCurrentElementIndex = createElementDecl();
/*      */       
/*  576 */       XMLElementDecl elementDecl = new XMLElementDecl();
/*  577 */       elementDecl.name.setValues(null, elementName, elementName, null);
/*      */       
/*  579 */       elementDecl.scope = -1;
/*      */ 
/*      */       
/*  582 */       this.fElementDeclTab.put(elementName, elementDecl);
/*      */ 
/*      */       
/*  585 */       setElementDecl(this.fCurrentElementIndex, elementDecl);
/*      */     } 
/*      */ 
/*      */     
/*  589 */     int elementIndex = getElementDeclIndex(elementName);
/*      */ 
/*      */ 
/*      */     
/*  593 */     if (getAttributeDeclIndex(elementIndex, attributeName) != -1) {
/*      */       return;
/*      */     }
/*      */     
/*  597 */     this.fCurrentAttributeIndex = createAttributeDecl();
/*      */     
/*  599 */     this.fSimpleType.clear();
/*  600 */     if (defaultType != null) {
/*  601 */       if (defaultType.equals("#FIXED")) {
/*  602 */         this.fSimpleType.defaultType = 1;
/*  603 */       } else if (defaultType.equals("#IMPLIED")) {
/*  604 */         this.fSimpleType.defaultType = 0;
/*  605 */       } else if (defaultType.equals("#REQUIRED")) {
/*  606 */         this.fSimpleType.defaultType = 2;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  612 */     this.fSimpleType.defaultValue = (defaultValue != null) ? defaultValue.toString() : null;
/*  613 */     this.fSimpleType.nonNormalizedDefaultValue = (nonNormalizedDefaultValue != null) ? nonNormalizedDefaultValue.toString() : null;
/*  614 */     this.fSimpleType.enumeration = enumeration;
/*      */     
/*  616 */     if (type.equals("CDATA")) {
/*  617 */       this.fSimpleType.type = 0;
/*      */     }
/*  619 */     else if (type.equals("ID")) {
/*  620 */       this.fSimpleType.type = 3;
/*      */     }
/*  622 */     else if (type.startsWith("IDREF")) {
/*  623 */       this.fSimpleType.type = 4;
/*  624 */       if (type.indexOf("S") > 0) {
/*  625 */         this.fSimpleType.list = true;
/*      */       }
/*      */     }
/*  628 */     else if (type.equals("ENTITIES")) {
/*  629 */       this.fSimpleType.type = 1;
/*  630 */       this.fSimpleType.list = true;
/*      */     }
/*  632 */     else if (type.equals("ENTITY")) {
/*  633 */       this.fSimpleType.type = 1;
/*      */     }
/*  635 */     else if (type.equals("NMTOKENS")) {
/*  636 */       this.fSimpleType.type = 5;
/*  637 */       this.fSimpleType.list = true;
/*      */     }
/*  639 */     else if (type.equals("NMTOKEN")) {
/*  640 */       this.fSimpleType.type = 5;
/*      */     }
/*  642 */     else if (type.startsWith("NOTATION")) {
/*  643 */       this.fSimpleType.type = 6;
/*      */     }
/*  645 */     else if (type.startsWith("ENUMERATION")) {
/*  646 */       this.fSimpleType.type = 2;
/*      */     }
/*      */     else {
/*      */       
/*  650 */       System.err.println("!!! unknown attribute type " + type);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  656 */     this.fQName.setValues(null, attributeName, attributeName, null);
/*  657 */     this.fAttributeDecl.setValues(this.fQName, this.fSimpleType, false);
/*      */     
/*  659 */     setAttributeDecl(elementIndex, this.fCurrentAttributeIndex, this.fAttributeDecl);
/*      */     
/*  661 */     int chunk = this.fCurrentAttributeIndex >> 8;
/*  662 */     int index = this.fCurrentAttributeIndex & 0xFF;
/*  663 */     ensureAttributeDeclCapacity(chunk);
/*  664 */     this.fAttributeDeclIsExternal[chunk][index] = (this.fReadingExternalDTD || this.fPEDepth > 0) ? 1 : 0;
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
/*      */   public void internalEntityDecl(String name, XMLString text, XMLString nonNormalizedText, Augmentations augs) throws XNIException {
/*  687 */     int entityIndex = getEntityDeclIndex(name);
/*  688 */     if (entityIndex == -1) {
/*  689 */       entityIndex = createEntityDecl();
/*  690 */       boolean isPE = name.startsWith("%");
/*  691 */       boolean inExternal = (this.fReadingExternalDTD || this.fPEDepth > 0);
/*  692 */       XMLEntityDecl entityDecl = new XMLEntityDecl();
/*  693 */       entityDecl.setValues(name, null, null, null, null, text
/*  694 */           .toString(), isPE, inExternal);
/*      */       
/*  696 */       setEntityDecl(entityIndex, entityDecl);
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
/*      */   public void externalEntityDecl(String name, XMLResourceIdentifier identifier, Augmentations augs) throws XNIException {
/*  717 */     int entityIndex = getEntityDeclIndex(name);
/*  718 */     if (entityIndex == -1) {
/*  719 */       entityIndex = createEntityDecl();
/*  720 */       boolean isPE = name.startsWith("%");
/*  721 */       boolean inExternal = (this.fReadingExternalDTD || this.fPEDepth > 0);
/*      */       
/*  723 */       XMLEntityDecl entityDecl = new XMLEntityDecl();
/*  724 */       entityDecl.setValues(name, identifier.getPublicId(), identifier.getLiteralSystemId(), identifier
/*  725 */           .getBaseSystemId(), null, null, isPE, inExternal);
/*      */ 
/*      */       
/*  728 */       setEntityDecl(entityIndex, entityDecl);
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
/*      */   public void unparsedEntityDecl(String name, XMLResourceIdentifier identifier, String notation, Augmentations augs) throws XNIException {
/*  747 */     XMLEntityDecl entityDecl = new XMLEntityDecl();
/*  748 */     boolean isPE = name.startsWith("%");
/*  749 */     boolean inExternal = (this.fReadingExternalDTD || this.fPEDepth > 0);
/*      */     
/*  751 */     entityDecl.setValues(name, identifier.getPublicId(), identifier.getLiteralSystemId(), identifier
/*  752 */         .getBaseSystemId(), notation, null, isPE, inExternal);
/*      */     
/*  754 */     int entityIndex = getEntityDeclIndex(name);
/*  755 */     if (entityIndex == -1) {
/*  756 */       entityIndex = createEntityDecl();
/*  757 */       setEntityDecl(entityIndex, entityDecl);
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
/*      */   public void notationDecl(String name, XMLResourceIdentifier identifier, Augmentations augs) throws XNIException {
/*  775 */     XMLNotationDecl notationDecl = new XMLNotationDecl();
/*  776 */     notationDecl.setValues(name, identifier.getPublicId(), identifier.getLiteralSystemId(), identifier
/*  777 */         .getBaseSystemId());
/*  778 */     int notationIndex = getNotationDeclIndex(name);
/*  779 */     if (notationIndex == -1) {
/*  780 */       notationIndex = createNotationDecl();
/*  781 */       setNotationDecl(notationIndex, notationDecl);
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
/*      */   public void endDTD(Augmentations augs) throws XNIException {
/*  794 */     this.fIsImmutable = true;
/*      */     
/*  796 */     if (this.fGrammarDescription.getRootName() == null) {
/*      */       
/*  798 */       int index = 0;
/*  799 */       String currName = null;
/*  800 */       int size = this.fElementDeclCount;
/*  801 */       ArrayList<String> elements = new ArrayList(size);
/*  802 */       for (int i = 0; i < size; i++) {
/*  803 */         int chunk = i >> 8;
/*  804 */         index = i & 0xFF;
/*  805 */         currName = (this.fElementDeclName[chunk][index]).rawname;
/*  806 */         elements.add(currName);
/*      */       } 
/*  808 */       this.fGrammarDescription.setPossibleRoots(elements);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDTDSource(XMLDTDSource source) {
/*  814 */     this.fDTDSource = source;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLDTDSource getDTDSource() {
/*  819 */     return this.fDTDSource;
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
/*      */   public void textDecl(String version, String encoding, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void comment(XMLString text, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processingInstruction(String target, XMLString data, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startAttlist(String elementName, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endAttlist(Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startConditional(short type, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ignoredCharacters(XMLString text, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endConditional(Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDContentModelSource(XMLDTDContentModelSource source) {
/*  930 */     this.fDTDContentModelSource = source;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLDTDContentModelSource getDTDContentModelSource() {
/*  935 */     return this.fDTDContentModelSource;
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
/*      */   public void startContentModel(String elementName, Augmentations augs) throws XNIException {
/*  951 */     XMLElementDecl elementDecl = this.fElementDeclTab.get(elementName);
/*  952 */     if (elementDecl != null) {
/*  953 */       this.fElementDecl = elementDecl;
/*      */     }
/*  955 */     this.fDepth = 0;
/*  956 */     initializeContentModelStack();
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
/*      */   public void startGroup(Augmentations augs) throws XNIException {
/*  974 */     this.fDepth++;
/*  975 */     initializeContentModelStack();
/*  976 */     this.fMixed = false;
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
/*      */   public void pcdata(Augmentations augs) throws XNIException {
/*  992 */     this.fMixed = true;
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
/*      */   public void element(String elementName, Augmentations augs) throws XNIException {
/* 1005 */     if (this.fMixed) {
/* 1006 */       if (this.fNodeIndexStack[this.fDepth] == -1) {
/* 1007 */         this.fNodeIndexStack[this.fDepth] = addUniqueLeafNode(elementName);
/*      */       } else {
/*      */         
/* 1010 */         this.fNodeIndexStack[this.fDepth] = addContentSpecNode((short)4, this.fNodeIndexStack[this.fDepth], 
/*      */             
/* 1012 */             addUniqueLeafNode(elementName));
/*      */       } 
/*      */     } else {
/*      */       
/* 1016 */       this.fNodeIndexStack[this.fDepth] = addContentSpecNode((short)0, elementName);
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
/*      */   public void separator(short separator, Augmentations augs) throws XNIException {
/* 1034 */     if (!this.fMixed) {
/* 1035 */       if (this.fOpStack[this.fDepth] != 5 && separator == 0) {
/* 1036 */         if (this.fPrevNodeIndexStack[this.fDepth] != -1) {
/* 1037 */           this.fNodeIndexStack[this.fDepth] = addContentSpecNode(this.fOpStack[this.fDepth], this.fPrevNodeIndexStack[this.fDepth], this.fNodeIndexStack[this.fDepth]);
/*      */         }
/* 1039 */         this.fPrevNodeIndexStack[this.fDepth] = this.fNodeIndexStack[this.fDepth];
/* 1040 */         this.fOpStack[this.fDepth] = 4;
/* 1041 */       } else if (this.fOpStack[this.fDepth] != 4 && separator == 1) {
/* 1042 */         if (this.fPrevNodeIndexStack[this.fDepth] != -1) {
/* 1043 */           this.fNodeIndexStack[this.fDepth] = addContentSpecNode(this.fOpStack[this.fDepth], this.fPrevNodeIndexStack[this.fDepth], this.fNodeIndexStack[this.fDepth]);
/*      */         }
/* 1045 */         this.fPrevNodeIndexStack[this.fDepth] = this.fNodeIndexStack[this.fDepth];
/* 1046 */         this.fOpStack[this.fDepth] = 5;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void occurrence(short occurrence, Augmentations augs) throws XNIException {
/* 1068 */     if (!this.fMixed) {
/* 1069 */       if (occurrence == 2) {
/* 1070 */         this.fNodeIndexStack[this.fDepth] = addContentSpecNode((short)1, this.fNodeIndexStack[this.fDepth], -1);
/* 1071 */       } else if (occurrence == 3) {
/* 1072 */         this.fNodeIndexStack[this.fDepth] = addContentSpecNode((short)2, this.fNodeIndexStack[this.fDepth], -1);
/* 1073 */       } else if (occurrence == 4) {
/* 1074 */         this.fNodeIndexStack[this.fDepth] = addContentSpecNode((short)3, this.fNodeIndexStack[this.fDepth], -1);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void endGroup(Augmentations augs) throws XNIException {
/* 1089 */     if (!this.fMixed) {
/* 1090 */       if (this.fPrevNodeIndexStack[this.fDepth] != -1) {
/* 1091 */         this.fNodeIndexStack[this.fDepth] = addContentSpecNode(this.fOpStack[this.fDepth], this.fPrevNodeIndexStack[this.fDepth], this.fNodeIndexStack[this.fDepth]);
/*      */       }
/* 1093 */       int nodeIndex = this.fNodeIndexStack[this.fDepth--];
/* 1094 */       this.fNodeIndexStack[this.fDepth] = nodeIndex;
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
/*      */   public void any(Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void empty(Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endContentModel(Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNamespaceAware() {
/* 1140 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public SymbolTable getSymbolTable() {
/* 1145 */     return this.fSymbolTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFirstElementDeclIndex() {
/* 1156 */     return (this.fElementDeclCount >= 0) ? 0 : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextElementDeclIndex(int elementDeclIndex) {
/* 1166 */     return (elementDeclIndex < this.fElementDeclCount - 1) ? (elementDeclIndex + 1) : -1;
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
/*      */   public int getElementDeclIndex(String elementDeclName) {
/* 1178 */     Integer mapping = this.fElementIndexMap.get(elementDeclName);
/* 1179 */     if (mapping == null) {
/* 1180 */       mapping = Integer.valueOf(-1);
/*      */     }
/*      */     
/* 1183 */     return mapping.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getElementDeclIndex(QName elementDeclQName) {
/* 1190 */     return getElementDeclIndex(elementDeclQName.rawname);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getContentSpecType(int elementIndex) {
/* 1198 */     if (elementIndex < 0 || elementIndex >= this.fElementDeclCount) {
/* 1199 */       return -1;
/*      */     }
/*      */     
/* 1202 */     int chunk = elementIndex >> 8;
/* 1203 */     int index = elementIndex & 0xFF;
/*      */     
/* 1205 */     if (this.fElementDeclType[chunk][index] == -1) {
/* 1206 */       return -1;
/*      */     }
/*      */     
/* 1209 */     return (short)(this.fElementDeclType[chunk][index] & 0xFFFFFF7F);
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
/*      */   public boolean getElementDecl(int elementDeclIndex, XMLElementDecl elementDecl) {
/* 1225 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/* 1226 */       return false;
/*      */     }
/*      */     
/* 1229 */     int chunk = elementDeclIndex >> 8;
/* 1230 */     int index = elementDeclIndex & 0xFF;
/*      */     
/* 1232 */     elementDecl.name.setValues(this.fElementDeclName[chunk][index]);
/*      */     
/* 1234 */     if (this.fElementDeclType[chunk][index] == -1) {
/* 1235 */       elementDecl.type = -1;
/* 1236 */       elementDecl.simpleType.list = false;
/*      */     } else {
/* 1238 */       elementDecl.type = (short)(this.fElementDeclType[chunk][index] & 0xFFFFFF7F);
/* 1239 */       elementDecl.simpleType.list = ((this.fElementDeclType[chunk][index] & 0x80) != 0);
/*      */     } 
/*      */ 
/*      */     
/* 1243 */     if (elementDecl.type == 3 || elementDecl.type == 2) {
/* 1244 */       elementDecl.contentModelValidator = getElementContentModelValidator(elementDeclIndex);
/*      */     }
/*      */     
/* 1247 */     elementDecl.simpleType.datatypeValidator = null;
/* 1248 */     elementDecl.simpleType.defaultType = -1;
/* 1249 */     elementDecl.simpleType.defaultValue = null;
/*      */     
/* 1251 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   QName getElementDeclName(int elementDeclIndex) {
/* 1256 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/* 1257 */       return null;
/*      */     }
/* 1259 */     int chunk = elementDeclIndex >> 8;
/* 1260 */     int index = elementDeclIndex & 0xFF;
/* 1261 */     return this.fElementDeclName[chunk][index];
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
/*      */   public int getFirstAttributeDeclIndex(int elementDeclIndex) {
/* 1274 */     int chunk = elementDeclIndex >> 8;
/* 1275 */     int index = elementDeclIndex & 0xFF;
/*      */     
/* 1277 */     return this.fElementDeclFirstAttributeDeclIndex[chunk][index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextAttributeDeclIndex(int attributeDeclIndex) {
/* 1288 */     int chunk = attributeDeclIndex >> 8;
/* 1289 */     int index = attributeDeclIndex & 0xFF;
/*      */     
/* 1291 */     return this.fAttributeDeclNextAttributeDeclIndex[chunk][index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAttributeDecl(int attributeDeclIndex, XMLAttributeDecl attributeDecl) {
/*      */     short attributeType;
/*      */     boolean isList;
/* 1303 */     if (attributeDeclIndex < 0 || attributeDeclIndex >= this.fAttributeDeclCount) {
/* 1304 */       return false;
/*      */     }
/* 1306 */     int chunk = attributeDeclIndex >> 8;
/* 1307 */     int index = attributeDeclIndex & 0xFF;
/*      */     
/* 1309 */     attributeDecl.name.setValues(this.fAttributeDeclName[chunk][index]);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1314 */     if (this.fAttributeDeclType[chunk][index] == -1) {
/*      */       
/* 1316 */       attributeType = -1;
/* 1317 */       isList = false;
/*      */     } else {
/* 1319 */       attributeType = (short)(this.fAttributeDeclType[chunk][index] & 0xFFFFFF7F);
/* 1320 */       isList = ((this.fAttributeDeclType[chunk][index] & 0x80) != 0);
/*      */     } 
/* 1322 */     attributeDecl.simpleType.setValues(attributeType, (this.fAttributeDeclName[chunk][index]).localpart, this.fAttributeDeclEnumeration[chunk][index], isList, this.fAttributeDeclDefaultType[chunk][index], this.fAttributeDeclDefaultValue[chunk][index], this.fAttributeDeclNonNormalizedDefaultValue[chunk][index], this.fAttributeDeclDatatypeValidator[chunk][index]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1328 */     return true;
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
/*      */   public boolean isCDATAAttribute(QName elName, QName atName) {
/* 1342 */     int elDeclIdx = getElementDeclIndex(elName);
/* 1343 */     if (getAttributeDecl(elDeclIdx, this.fAttributeDecl) && this.fAttributeDecl.simpleType.type != 0)
/*      */     {
/* 1345 */       return false;
/*      */     }
/* 1347 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEntityDeclIndex(String entityDeclName) {
/* 1358 */     if (entityDeclName == null || this.fEntityIndexMap.get(entityDeclName) == null) {
/* 1359 */       return -1;
/*      */     }
/*      */     
/* 1362 */     return ((Integer)this.fEntityIndexMap.get(entityDeclName)).intValue();
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
/*      */   public boolean getEntityDecl(int entityDeclIndex, XMLEntityDecl entityDecl) {
/* 1375 */     if (entityDeclIndex < 0 || entityDeclIndex >= this.fEntityCount) {
/* 1376 */       return false;
/*      */     }
/* 1378 */     int chunk = entityDeclIndex >> 8;
/* 1379 */     int index = entityDeclIndex & 0xFF;
/*      */     
/* 1381 */     entityDecl.setValues(this.fEntityName[chunk][index], this.fEntityPublicId[chunk][index], this.fEntitySystemId[chunk][index], this.fEntityBaseSystemId[chunk][index], this.fEntityNotation[chunk][index], this.fEntityValue[chunk][index], !(this.fEntityIsPE[chunk][index] == 0), !(this.fEntityInExternal[chunk][index] == 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1390 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNotationDeclIndex(String notationDeclName) {
/* 1401 */     if (notationDeclName == null || this.fNotationIndexMap.get(notationDeclName) == null) {
/* 1402 */       return -1;
/*      */     }
/*      */     
/* 1405 */     return ((Integer)this.fNotationIndexMap.get(notationDeclName)).intValue();
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
/*      */   public boolean getNotationDecl(int notationDeclIndex, XMLNotationDecl notationDecl) {
/* 1418 */     if (notationDeclIndex < 0 || notationDeclIndex >= this.fNotationCount) {
/* 1419 */       return false;
/*      */     }
/* 1421 */     int chunk = notationDeclIndex >> 8;
/* 1422 */     int index = notationDeclIndex & 0xFF;
/*      */     
/* 1424 */     notationDecl.setValues(this.fNotationName[chunk][index], this.fNotationPublicId[chunk][index], this.fNotationSystemId[chunk][index], this.fNotationBaseSystemId[chunk][index]);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1429 */     return true;
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
/*      */   public boolean getContentSpec(int contentSpecIndex, XMLContentSpec contentSpec) {
/* 1442 */     if (contentSpecIndex < 0 || contentSpecIndex >= this.fContentSpecCount) {
/* 1443 */       return false;
/*      */     }
/* 1445 */     int chunk = contentSpecIndex >> 8;
/* 1446 */     int index = contentSpecIndex & 0xFF;
/*      */     
/* 1448 */     contentSpec.type = this.fContentSpecType[chunk][index];
/* 1449 */     contentSpec.value = this.fContentSpecValue[chunk][index];
/* 1450 */     contentSpec.otherValue = this.fContentSpecOtherValue[chunk][index];
/* 1451 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getContentSpecIndex(int elementDeclIndex) {
/* 1460 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/* 1461 */       return -1;
/*      */     }
/* 1463 */     int chunk = elementDeclIndex >> 8;
/* 1464 */     int index = elementDeclIndex & 0xFF;
/* 1465 */     return this.fElementDeclContentSpecIndex[chunk][index];
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
/*      */   public String getContentSpecAsString(int elementDeclIndex) {
/* 1477 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/* 1478 */       return null;
/*      */     }
/*      */     
/* 1481 */     int chunk = elementDeclIndex >> 8;
/* 1482 */     int index = elementDeclIndex & 0xFF;
/*      */     
/* 1484 */     int contentSpecIndex = this.fElementDeclContentSpecIndex[chunk][index];
/*      */ 
/*      */     
/* 1487 */     XMLContentSpec contentSpec = new XMLContentSpec();
/*      */     
/* 1489 */     if (getContentSpec(contentSpecIndex, contentSpec)) {
/*      */       int nextContentSpec;
/*      */       
/* 1492 */       StringBuffer str = new StringBuffer();
/* 1493 */       int parentContentSpecType = contentSpec.type & 0xF;
/*      */       
/* 1495 */       switch (parentContentSpecType)
/*      */       { case 0:
/* 1497 */           str.append('(');
/* 1498 */           if (contentSpec.value == null && contentSpec.otherValue == null) {
/* 1499 */             str.append("#PCDATA");
/*      */           } else {
/*      */             
/* 1502 */             str.append(contentSpec.value);
/*      */           } 
/* 1504 */           str.append(')');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1626 */           return str.toString();case 1: getContentSpec(((int[])contentSpec.value)[0], contentSpec); nextContentSpec = contentSpec.type; if (nextContentSpec == 0) { str.append('('); str.append(contentSpec.value); str.append(')'); } else if (nextContentSpec == 3 || nextContentSpec == 2 || nextContentSpec == 1) { str.append('('); appendContentSpec(contentSpec, str, true, parentContentSpecType); str.append(')'); } else { appendContentSpec(contentSpec, str, true, parentContentSpecType); }  str.append('?'); return str.toString();case 2: getContentSpec(((int[])contentSpec.value)[0], contentSpec); nextContentSpec = contentSpec.type; if (nextContentSpec == 0) { str.append('('); if (contentSpec.value == null && contentSpec.otherValue == null) { str.append("#PCDATA"); } else if (contentSpec.otherValue != null) { str.append("##any:uri=").append(contentSpec.otherValue); } else if (contentSpec.value == null) { str.append("##any"); } else { appendContentSpec(contentSpec, str, true, parentContentSpecType); }  str.append(')'); } else if (nextContentSpec == 3 || nextContentSpec == 2 || nextContentSpec == 1) { str.append('('); appendContentSpec(contentSpec, str, true, parentContentSpecType); str.append(')'); } else { appendContentSpec(contentSpec, str, true, parentContentSpecType); }  str.append('*'); return str.toString();case 3: getContentSpec(((int[])contentSpec.value)[0], contentSpec); nextContentSpec = contentSpec.type; if (nextContentSpec == 0) { str.append('('); if (contentSpec.value == null && contentSpec.otherValue == null) { str.append("#PCDATA"); } else if (contentSpec.otherValue != null) { str.append("##any:uri=").append(contentSpec.otherValue); } else if (contentSpec.value == null) { str.append("##any"); } else { str.append(contentSpec.value); }  str.append(')'); } else if (nextContentSpec == 3 || nextContentSpec == 2 || nextContentSpec == 1) { str.append('('); appendContentSpec(contentSpec, str, true, parentContentSpecType); str.append(')'); } else { appendContentSpec(contentSpec, str, true, parentContentSpecType); }  str.append('+'); return str.toString();case 4: case 5: appendContentSpec(contentSpec, str, true, parentContentSpecType); return str.toString();case 6: str.append("##any"); if (contentSpec.otherValue != null) { str.append(":uri="); str.append(contentSpec.otherValue); }  return str.toString();case 7: str.append("##other:uri="); str.append(contentSpec.otherValue); return str.toString();case 8: str.append("##local"); return str.toString(); }  str.append("???"); return str.toString();
/*      */     } 
/*      */ 
/*      */     
/* 1630 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printElements() {
/* 1637 */     int elementDeclIndex = 0;
/* 1638 */     XMLElementDecl elementDecl = new XMLElementDecl();
/* 1639 */     while (getElementDecl(elementDeclIndex++, elementDecl))
/*      */     {
/* 1641 */       System.out.println("element decl: " + elementDecl.name + ", " + elementDecl.name.rawname);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printAttributes(int elementDeclIndex) {
/* 1649 */     int attributeDeclIndex = getFirstAttributeDeclIndex(elementDeclIndex);
/* 1650 */     System.out.print(elementDeclIndex);
/* 1651 */     System.out.print(" [");
/* 1652 */     while (attributeDeclIndex != -1) {
/* 1653 */       System.out.print(' ');
/* 1654 */       System.out.print(attributeDeclIndex);
/* 1655 */       printAttribute(attributeDeclIndex);
/* 1656 */       attributeDeclIndex = getNextAttributeDeclIndex(attributeDeclIndex);
/* 1657 */       if (attributeDeclIndex != -1) {
/* 1658 */         System.out.print(",");
/*      */       }
/*      */     } 
/* 1661 */     System.out.println(" ]");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addContentSpecToElement(XMLElementDecl elementDecl) {
/* 1672 */     if ((this.fDepth == 0 || (this.fDepth == 1 && elementDecl.type == 2)) && this.fNodeIndexStack != null) {
/*      */       
/* 1674 */       if (elementDecl.type == 2) {
/* 1675 */         int pcdata = addUniqueLeafNode(null);
/* 1676 */         if (this.fNodeIndexStack[0] == -1) {
/* 1677 */           this.fNodeIndexStack[0] = pcdata;
/*      */         } else {
/*      */           
/* 1680 */           this.fNodeIndexStack[0] = addContentSpecNode((short)4, pcdata, this.fNodeIndexStack[0]);
/*      */         } 
/*      */       } 
/*      */       
/* 1684 */       setContentSpecIndex(this.fCurrentElementIndex, this.fNodeIndexStack[this.fDepth]);
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
/*      */   protected ContentModelValidator getElementContentModelValidator(int elementDeclIndex) {
/* 1697 */     int chunk = elementDeclIndex >> 8;
/* 1698 */     int index = elementDeclIndex & 0xFF;
/*      */     
/* 1700 */     ContentModelValidator contentModel = this.fElementDeclContentModelValidator[chunk][index];
/*      */ 
/*      */     
/* 1703 */     if (contentModel != null) {
/* 1704 */       return contentModel;
/*      */     }
/*      */     
/* 1707 */     int contentType = this.fElementDeclType[chunk][index];
/* 1708 */     if (contentType == 4) {
/* 1709 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1713 */     int contentSpecIndex = this.fElementDeclContentSpecIndex[chunk][index];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1720 */     XMLContentSpec contentSpec = new XMLContentSpec();
/* 1721 */     getContentSpec(contentSpecIndex, contentSpec);
/*      */ 
/*      */     
/* 1724 */     if (contentType == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1729 */       ChildrenList children = new ChildrenList();
/* 1730 */       contentSpecTree(contentSpecIndex, contentSpec, children);
/* 1731 */       contentModel = new MixedContentModel(children.qname, children.type, 0, children.length, false);
/*      */ 
/*      */     
/*      */     }
/* 1735 */     else if (contentType == 3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1742 */       contentModel = createChildModel(contentSpecIndex);
/*      */     } else {
/* 1744 */       throw new RuntimeException("Unknown content type for a element decl in getElementContentModelValidator() in AbstractDTDGrammar class");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1749 */     this.fElementDeclContentModelValidator[chunk][index] = contentModel;
/*      */     
/* 1751 */     return contentModel;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int createElementDecl() {
/* 1756 */     int chunk = this.fElementDeclCount >> 8;
/* 1757 */     int index = this.fElementDeclCount & 0xFF;
/* 1758 */     ensureElementDeclCapacity(chunk);
/* 1759 */     this.fElementDeclName[chunk][index] = new QName();
/* 1760 */     this.fElementDeclType[chunk][index] = -1;
/* 1761 */     this.fElementDeclContentModelValidator[chunk][index] = null;
/* 1762 */     this.fElementDeclFirstAttributeDeclIndex[chunk][index] = -1;
/* 1763 */     this.fElementDeclLastAttributeDeclIndex[chunk][index] = -1;
/* 1764 */     return this.fElementDeclCount++;
/*      */   }
/*      */   
/*      */   protected void setElementDecl(int elementDeclIndex, XMLElementDecl elementDecl) {
/* 1768 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/*      */       return;
/*      */     }
/* 1771 */     int chunk = elementDeclIndex >> 8;
/* 1772 */     int index = elementDeclIndex & 0xFF;
/*      */     
/* 1774 */     this.fElementDeclName[chunk][index].setValues(elementDecl.name);
/* 1775 */     this.fElementDeclType[chunk][index] = elementDecl.type;
/*      */     
/* 1777 */     this.fElementDeclContentModelValidator[chunk][index] = elementDecl.contentModelValidator;
/*      */     
/* 1779 */     if (elementDecl.simpleType.list == true) {
/* 1780 */       this.fElementDeclType[chunk][index] = (short)(this.fElementDeclType[chunk][index] | 0x80);
/*      */     }
/*      */     
/* 1783 */     this.fElementIndexMap.put(elementDecl.name.rawname, Integer.valueOf(elementDeclIndex));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void putElementNameMapping(QName name, int scope, int elementDeclIndex) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFirstAttributeDeclIndex(int elementDeclIndex, int newFirstAttrIndex) {
/* 1795 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/*      */       return;
/*      */     }
/*      */     
/* 1799 */     int chunk = elementDeclIndex >> 8;
/* 1800 */     int index = elementDeclIndex & 0xFF;
/*      */     
/* 1802 */     this.fElementDeclFirstAttributeDeclIndex[chunk][index] = newFirstAttrIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setContentSpecIndex(int elementDeclIndex, int contentSpecIndex) {
/* 1807 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/*      */       return;
/*      */     }
/*      */     
/* 1811 */     int chunk = elementDeclIndex >> 8;
/* 1812 */     int index = elementDeclIndex & 0xFF;
/*      */     
/* 1814 */     this.fElementDeclContentSpecIndex[chunk][index] = contentSpecIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int createAttributeDecl() {
/* 1819 */     int chunk = this.fAttributeDeclCount >> 8;
/* 1820 */     int index = this.fAttributeDeclCount & 0xFF;
/*      */     
/* 1822 */     ensureAttributeDeclCapacity(chunk);
/* 1823 */     this.fAttributeDeclName[chunk][index] = new QName();
/* 1824 */     this.fAttributeDeclType[chunk][index] = -1;
/* 1825 */     this.fAttributeDeclDatatypeValidator[chunk][index] = null;
/* 1826 */     this.fAttributeDeclEnumeration[chunk][index] = null;
/* 1827 */     this.fAttributeDeclDefaultType[chunk][index] = 0;
/* 1828 */     this.fAttributeDeclDefaultValue[chunk][index] = null;
/* 1829 */     this.fAttributeDeclNonNormalizedDefaultValue[chunk][index] = null;
/* 1830 */     this.fAttributeDeclNextAttributeDeclIndex[chunk][index] = -1;
/* 1831 */     return this.fAttributeDeclCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAttributeDecl(int elementDeclIndex, int attributeDeclIndex, XMLAttributeDecl attributeDecl) {
/* 1837 */     int attrChunk = attributeDeclIndex >> 8;
/* 1838 */     int attrIndex = attributeDeclIndex & 0xFF;
/* 1839 */     this.fAttributeDeclName[attrChunk][attrIndex].setValues(attributeDecl.name);
/* 1840 */     this.fAttributeDeclType[attrChunk][attrIndex] = attributeDecl.simpleType.type;
/*      */     
/* 1842 */     if (attributeDecl.simpleType.list) {
/* 1843 */       this.fAttributeDeclType[attrChunk][attrIndex] = (short)(this.fAttributeDeclType[attrChunk][attrIndex] | 0x80);
/*      */     }
/* 1845 */     this.fAttributeDeclEnumeration[attrChunk][attrIndex] = attributeDecl.simpleType.enumeration;
/* 1846 */     this.fAttributeDeclDefaultType[attrChunk][attrIndex] = attributeDecl.simpleType.defaultType;
/* 1847 */     this.fAttributeDeclDatatypeValidator[attrChunk][attrIndex] = attributeDecl.simpleType.datatypeValidator;
/*      */     
/* 1849 */     this.fAttributeDeclDefaultValue[attrChunk][attrIndex] = attributeDecl.simpleType.defaultValue;
/* 1850 */     this.fAttributeDeclNonNormalizedDefaultValue[attrChunk][attrIndex] = attributeDecl.simpleType.nonNormalizedDefaultValue;
/*      */     
/* 1852 */     int elemChunk = elementDeclIndex >> 8;
/* 1853 */     int elemIndex = elementDeclIndex & 0xFF;
/* 1854 */     int index = this.fElementDeclFirstAttributeDeclIndex[elemChunk][elemIndex];
/* 1855 */     while (index != -1 && 
/* 1856 */       index != attributeDeclIndex) {
/*      */ 
/*      */       
/* 1859 */       attrChunk = index >> 8;
/* 1860 */       attrIndex = index & 0xFF;
/* 1861 */       index = this.fAttributeDeclNextAttributeDeclIndex[attrChunk][attrIndex];
/*      */     } 
/* 1863 */     if (index == -1) {
/* 1864 */       if (this.fElementDeclFirstAttributeDeclIndex[elemChunk][elemIndex] == -1) {
/* 1865 */         this.fElementDeclFirstAttributeDeclIndex[elemChunk][elemIndex] = attributeDeclIndex;
/*      */       } else {
/* 1867 */         index = this.fElementDeclLastAttributeDeclIndex[elemChunk][elemIndex];
/* 1868 */         attrChunk = index >> 8;
/* 1869 */         attrIndex = index & 0xFF;
/* 1870 */         this.fAttributeDeclNextAttributeDeclIndex[attrChunk][attrIndex] = attributeDeclIndex;
/*      */       } 
/* 1872 */       this.fElementDeclLastAttributeDeclIndex[elemChunk][elemIndex] = attributeDeclIndex;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected int createContentSpec() {
/* 1877 */     int chunk = this.fContentSpecCount >> 8;
/* 1878 */     int index = this.fContentSpecCount & 0xFF;
/*      */     
/* 1880 */     ensureContentSpecCapacity(chunk);
/* 1881 */     this.fContentSpecType[chunk][index] = -1;
/* 1882 */     this.fContentSpecValue[chunk][index] = null;
/* 1883 */     this.fContentSpecOtherValue[chunk][index] = null;
/*      */     
/* 1885 */     return this.fContentSpecCount++;
/*      */   }
/*      */   
/*      */   protected void setContentSpec(int contentSpecIndex, XMLContentSpec contentSpec) {
/* 1889 */     int chunk = contentSpecIndex >> 8;
/* 1890 */     int index = contentSpecIndex & 0xFF;
/*      */     
/* 1892 */     this.fContentSpecType[chunk][index] = contentSpec.type;
/* 1893 */     this.fContentSpecValue[chunk][index] = contentSpec.value;
/* 1894 */     this.fContentSpecOtherValue[chunk][index] = contentSpec.otherValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int createEntityDecl() {
/* 1899 */     int chunk = this.fEntityCount >> 8;
/* 1900 */     int index = this.fEntityCount & 0xFF;
/*      */     
/* 1902 */     ensureEntityDeclCapacity(chunk);
/* 1903 */     this.fEntityIsPE[chunk][index] = 0;
/* 1904 */     this.fEntityInExternal[chunk][index] = 0;
/*      */     
/* 1906 */     return this.fEntityCount++;
/*      */   }
/*      */   
/*      */   protected void setEntityDecl(int entityDeclIndex, XMLEntityDecl entityDecl) {
/* 1910 */     int chunk = entityDeclIndex >> 8;
/* 1911 */     int index = entityDeclIndex & 0xFF;
/*      */     
/* 1913 */     this.fEntityName[chunk][index] = entityDecl.name;
/* 1914 */     this.fEntityValue[chunk][index] = entityDecl.value;
/* 1915 */     this.fEntityPublicId[chunk][index] = entityDecl.publicId;
/* 1916 */     this.fEntitySystemId[chunk][index] = entityDecl.systemId;
/* 1917 */     this.fEntityBaseSystemId[chunk][index] = entityDecl.baseSystemId;
/* 1918 */     this.fEntityNotation[chunk][index] = entityDecl.notation;
/* 1919 */     this.fEntityIsPE[chunk][index] = entityDecl.isPE ? 1 : 0;
/* 1920 */     this.fEntityInExternal[chunk][index] = entityDecl.inExternal ? 1 : 0;
/*      */     
/* 1922 */     this.fEntityIndexMap.put(entityDecl.name, Integer.valueOf(entityDeclIndex));
/*      */   }
/*      */   
/*      */   protected int createNotationDecl() {
/* 1926 */     int chunk = this.fNotationCount >> 8;
/* 1927 */     ensureNotationDeclCapacity(chunk);
/* 1928 */     return this.fNotationCount++;
/*      */   }
/*      */   
/*      */   protected void setNotationDecl(int notationDeclIndex, XMLNotationDecl notationDecl) {
/* 1932 */     int chunk = notationDeclIndex >> 8;
/* 1933 */     int index = notationDeclIndex & 0xFF;
/*      */     
/* 1935 */     this.fNotationName[chunk][index] = notationDecl.name;
/* 1936 */     this.fNotationPublicId[chunk][index] = notationDecl.publicId;
/* 1937 */     this.fNotationSystemId[chunk][index] = notationDecl.systemId;
/* 1938 */     this.fNotationBaseSystemId[chunk][index] = notationDecl.baseSystemId;
/*      */     
/* 1940 */     this.fNotationIndexMap.put(notationDecl.name, Integer.valueOf(notationDeclIndex));
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
/*      */   protected int addContentSpecNode(short nodeType, String nodeValue) {
/* 1953 */     int contentSpecIndex = createContentSpec();
/*      */ 
/*      */     
/* 1956 */     this.fContentSpec.setValues(nodeType, nodeValue, null);
/* 1957 */     setContentSpec(contentSpecIndex, this.fContentSpec);
/*      */ 
/*      */     
/* 1960 */     return contentSpecIndex;
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
/*      */   protected int addUniqueLeafNode(String elementName) {
/* 1973 */     int contentSpecIndex = createContentSpec();
/*      */ 
/*      */     
/* 1976 */     this.fContentSpec.setValues((short)0, elementName, null);
/*      */     
/* 1978 */     setContentSpec(contentSpecIndex, this.fContentSpec);
/*      */ 
/*      */     
/* 1981 */     return contentSpecIndex;
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
/*      */   protected int addContentSpecNode(short nodeType, int leftNodeIndex, int rightNodeIndex) {
/* 1997 */     int contentSpecIndex = createContentSpec();
/*      */ 
/*      */     
/* 2000 */     int[] leftIntArray = new int[1];
/* 2001 */     int[] rightIntArray = new int[1];
/*      */     
/* 2003 */     leftIntArray[0] = leftNodeIndex;
/* 2004 */     rightIntArray[0] = rightNodeIndex;
/* 2005 */     this.fContentSpec.setValues(nodeType, leftIntArray, rightIntArray);
/* 2006 */     setContentSpec(contentSpecIndex, this.fContentSpec);
/*      */ 
/*      */     
/* 2009 */     return contentSpecIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeContentModelStack() {
/* 2016 */     if (this.fOpStack == null) {
/* 2017 */       this.fOpStack = new short[8];
/* 2018 */       this.fNodeIndexStack = new int[8];
/* 2019 */       this.fPrevNodeIndexStack = new int[8];
/* 2020 */     } else if (this.fDepth == this.fOpStack.length) {
/* 2021 */       short[] newStack = new short[this.fDepth * 2];
/* 2022 */       System.arraycopy(this.fOpStack, 0, newStack, 0, this.fDepth);
/* 2023 */       this.fOpStack = newStack;
/* 2024 */       int[] newIntStack = new int[this.fDepth * 2];
/* 2025 */       System.arraycopy(this.fNodeIndexStack, 0, newIntStack, 0, this.fDepth);
/* 2026 */       this.fNodeIndexStack = newIntStack;
/* 2027 */       newIntStack = new int[this.fDepth * 2];
/* 2028 */       System.arraycopy(this.fPrevNodeIndexStack, 0, newIntStack, 0, this.fDepth);
/* 2029 */       this.fPrevNodeIndexStack = newIntStack;
/*      */     } 
/* 2031 */     this.fOpStack[this.fDepth] = -1;
/* 2032 */     this.fNodeIndexStack[this.fDepth] = -1;
/* 2033 */     this.fPrevNodeIndexStack[this.fDepth] = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean isImmutable() {
/* 2038 */     return this.fIsImmutable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendContentSpec(XMLContentSpec contentSpec, StringBuffer str, boolean parens, int parentContentSpecType) {
/* 2049 */     int type, otherValue, thisContentSpec = contentSpec.type & 0xF;
/* 2050 */     switch (thisContentSpec) {
/*      */       case 0:
/* 2052 */         if (contentSpec.value == null && contentSpec.otherValue == null) {
/* 2053 */           str.append("#PCDATA");
/*      */         }
/* 2055 */         else if (contentSpec.value == null && contentSpec.otherValue != null) {
/* 2056 */           str.append("##any:uri=").append(contentSpec.otherValue);
/*      */         }
/* 2058 */         else if (contentSpec.value == null) {
/* 2059 */           str.append("##any");
/*      */         } else {
/*      */           
/* 2062 */           str.append(contentSpec.value);
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 1:
/* 2067 */         if (parentContentSpecType == 3 || parentContentSpecType == 2 || parentContentSpecType == 1) {
/*      */ 
/*      */           
/* 2070 */           getContentSpec(((int[])contentSpec.value)[0], contentSpec);
/* 2071 */           str.append('(');
/* 2072 */           appendContentSpec(contentSpec, str, true, thisContentSpec);
/* 2073 */           str.append(')');
/*      */         } else {
/*      */           
/* 2076 */           getContentSpec(((int[])contentSpec.value)[0], contentSpec);
/* 2077 */           appendContentSpec(contentSpec, str, true, thisContentSpec);
/*      */         } 
/* 2079 */         str.append('?');
/*      */         return;
/*      */       
/*      */       case 2:
/* 2083 */         if (parentContentSpecType == 3 || parentContentSpecType == 2 || parentContentSpecType == 1) {
/*      */ 
/*      */           
/* 2086 */           getContentSpec(((int[])contentSpec.value)[0], contentSpec);
/* 2087 */           str.append('(');
/* 2088 */           appendContentSpec(contentSpec, str, true, thisContentSpec);
/* 2089 */           str.append(')');
/*      */         } else {
/*      */           
/* 2092 */           getContentSpec(((int[])contentSpec.value)[0], contentSpec);
/* 2093 */           appendContentSpec(contentSpec, str, true, thisContentSpec);
/*      */         } 
/* 2095 */         str.append('*');
/*      */         return;
/*      */       
/*      */       case 3:
/* 2099 */         if (parentContentSpecType == 3 || parentContentSpecType == 2 || parentContentSpecType == 1) {
/*      */ 
/*      */ 
/*      */           
/* 2103 */           str.append('(');
/* 2104 */           getContentSpec(((int[])contentSpec.value)[0], contentSpec);
/* 2105 */           appendContentSpec(contentSpec, str, true, thisContentSpec);
/* 2106 */           str.append(')');
/*      */         } else {
/*      */           
/* 2109 */           getContentSpec(((int[])contentSpec.value)[0], contentSpec);
/* 2110 */           appendContentSpec(contentSpec, str, true, thisContentSpec);
/*      */         } 
/* 2112 */         str.append('+');
/*      */         return;
/*      */       
/*      */       case 4:
/*      */       case 5:
/* 2117 */         if (parens) {
/* 2118 */           str.append('(');
/*      */         }
/* 2120 */         type = contentSpec.type;
/* 2121 */         otherValue = ((int[])contentSpec.otherValue)[0];
/* 2122 */         getContentSpec(((int[])contentSpec.value)[0], contentSpec);
/* 2123 */         appendContentSpec(contentSpec, str, (contentSpec.type != type), thisContentSpec);
/* 2124 */         if (type == 4) {
/* 2125 */           str.append('|');
/*      */         } else {
/*      */           
/* 2128 */           str.append(',');
/*      */         } 
/* 2130 */         getContentSpec(otherValue, contentSpec);
/* 2131 */         appendContentSpec(contentSpec, str, true, thisContentSpec);
/* 2132 */         if (parens) {
/* 2133 */           str.append(')');
/*      */         }
/*      */         return;
/*      */       
/*      */       case 6:
/* 2138 */         str.append("##any");
/* 2139 */         if (contentSpec.otherValue != null) {
/* 2140 */           str.append(":uri=");
/* 2141 */           str.append(contentSpec.otherValue);
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 7:
/* 2146 */         str.append("##other:uri=");
/* 2147 */         str.append(contentSpec.otherValue);
/*      */         return;
/*      */       
/*      */       case 8:
/* 2151 */         str.append("##local");
/*      */         return;
/*      */     } 
/*      */     
/* 2155 */     str.append("???");
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
/*      */   private void printAttribute(int attributeDeclIndex) {
/* 2167 */     XMLAttributeDecl attributeDecl = new XMLAttributeDecl();
/* 2168 */     if (getAttributeDecl(attributeDeclIndex, attributeDecl)) {
/* 2169 */       System.out.print(" { ");
/* 2170 */       System.out.print(attributeDecl.name.localpart);
/* 2171 */       System.out.print(" }");
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
/*      */   private synchronized ContentModelValidator createChildModel(int contentSpecIndex) {
/* 2191 */     XMLContentSpec contentSpec = new XMLContentSpec();
/* 2192 */     getContentSpec(contentSpecIndex, contentSpec);
/*      */     
/* 2194 */     if ((contentSpec.type & 0xF) != 6 && (contentSpec.type & 0xF) != 7 && (contentSpec.type & 0xF) != 8) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2200 */       if (contentSpec.type == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2205 */         if (contentSpec.value == null && contentSpec.otherValue == null) {
/* 2206 */           throw new RuntimeException("ImplementationMessages.VAL_NPCD");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2214 */         this.fQName.setValues(null, (String)contentSpec.value, (String)contentSpec.value, (String)contentSpec.otherValue);
/*      */         
/* 2216 */         return new SimpleContentModel(contentSpec.type, this.fQName, null);
/* 2217 */       }  if (contentSpec.type == 4 || contentSpec.type == 5) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2223 */         XMLContentSpec contentSpecLeft = new XMLContentSpec();
/* 2224 */         XMLContentSpec contentSpecRight = new XMLContentSpec();
/*      */         
/* 2226 */         getContentSpec(((int[])contentSpec.value)[0], contentSpecLeft);
/* 2227 */         getContentSpec(((int[])contentSpec.otherValue)[0], contentSpecRight);
/*      */         
/* 2229 */         if (contentSpecLeft.type == 0 && contentSpecRight.type == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2235 */           this.fQName.setValues(null, (String)contentSpecLeft.value, (String)contentSpecLeft.value, (String)contentSpecLeft.otherValue);
/*      */           
/* 2237 */           this.fQName2.setValues(null, (String)contentSpecRight.value, (String)contentSpecRight.value, (String)contentSpecRight.otherValue);
/*      */           
/* 2239 */           return new SimpleContentModel(contentSpec.type, this.fQName, this.fQName2);
/*      */         } 
/* 2241 */       } else if (contentSpec.type == 1 || contentSpec.type == 2 || contentSpec.type == 3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2249 */         XMLContentSpec contentSpecLeft = new XMLContentSpec();
/* 2250 */         getContentSpec(((int[])contentSpec.value)[0], contentSpecLeft);
/*      */         
/* 2252 */         if (contentSpecLeft.type == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2258 */           this.fQName.setValues(null, (String)contentSpecLeft.value, (String)contentSpecLeft.value, (String)contentSpecLeft.otherValue);
/*      */           
/* 2260 */           return new SimpleContentModel(contentSpec.type, this.fQName, null);
/*      */         } 
/*      */       } else {
/* 2263 */         throw new RuntimeException("ImplementationMessages.VAL_CST");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2272 */     this.fLeafCount = 0;
/*      */     
/* 2274 */     this.fLeafCount = 0;
/* 2275 */     CMNode cmn = buildSyntaxTree(contentSpecIndex, contentSpec);
/*      */ 
/*      */     
/* 2278 */     return new DFAContentModel(cmn, this.fLeafCount, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final CMNode buildSyntaxTree(int startNode, XMLContentSpec contentSpec) {
/* 2286 */     CMNode nodeRet = null;
/* 2287 */     getContentSpec(startNode, contentSpec);
/* 2288 */     if ((contentSpec.type & 0xF) == 6) {
/*      */       
/* 2290 */       nodeRet = new CMAny(contentSpec.type, (String)contentSpec.otherValue, this.fLeafCount++);
/*      */     }
/* 2292 */     else if ((contentSpec.type & 0xF) == 7) {
/* 2293 */       nodeRet = new CMAny(contentSpec.type, (String)contentSpec.otherValue, this.fLeafCount++);
/*      */     }
/* 2295 */     else if ((contentSpec.type & 0xF) == 8) {
/* 2296 */       nodeRet = new CMAny(contentSpec.type, null, this.fLeafCount++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2302 */     else if (contentSpec.type == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2309 */       this.fQName.setValues(null, (String)contentSpec.value, (String)contentSpec.value, (String)contentSpec.otherValue);
/*      */       
/* 2311 */       nodeRet = new CMLeaf(this.fQName, this.fLeafCount++);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2317 */       int leftNode = ((int[])contentSpec.value)[0];
/* 2318 */       int rightNode = ((int[])contentSpec.otherValue)[0];
/*      */       
/* 2320 */       if (contentSpec.type == 4 || contentSpec.type == 5) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2329 */         nodeRet = new CMBinOp(contentSpec.type, buildSyntaxTree(leftNode, contentSpec), buildSyntaxTree(rightNode, contentSpec));
/*      */       }
/* 2331 */       else if (contentSpec.type == 2) {
/* 2332 */         nodeRet = new CMUniOp(contentSpec.type, buildSyntaxTree(leftNode, contentSpec));
/*      */       }
/* 2334 */       else if (contentSpec.type == 2 || contentSpec.type == 1 || contentSpec.type == 3) {
/*      */ 
/*      */         
/* 2337 */         nodeRet = new CMUniOp(contentSpec.type, buildSyntaxTree(leftNode, contentSpec));
/*      */       } else {
/*      */         
/* 2340 */         throw new RuntimeException("ImplementationMessages.VAL_CST");
/*      */       } 
/*      */     } 
/*      */     
/* 2344 */     return nodeRet;
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
/*      */   private void contentSpecTree(int contentSpecIndex, XMLContentSpec contentSpec, ChildrenList children) {
/* 2362 */     getContentSpec(contentSpecIndex, contentSpec);
/* 2363 */     if (contentSpec.type == 0 || (contentSpec.type & 0xF) == 6 || (contentSpec.type & 0xF) == 8 || (contentSpec.type & 0xF) == 7) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2369 */       if (children.length == children.qname.length) {
/* 2370 */         QName[] newQName = new QName[children.length * 2];
/* 2371 */         System.arraycopy(children.qname, 0, newQName, 0, children.length);
/* 2372 */         children.qname = newQName;
/* 2373 */         int[] newType = new int[children.length * 2];
/* 2374 */         System.arraycopy(children.type, 0, newType, 0, children.length);
/* 2375 */         children.type = newType;
/*      */       } 
/*      */ 
/*      */       
/* 2379 */       children.qname[children.length] = new QName(null, (String)contentSpec.value, (String)contentSpec.value, (String)contentSpec.otherValue);
/*      */ 
/*      */       
/* 2382 */       children.type[children.length] = contentSpec.type;
/* 2383 */       children.length++;
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 2391 */     int leftNode = (contentSpec.value != null) ? ((int[])contentSpec.value)[0] : -1;
/*      */     
/* 2393 */     int rightNode = -1;
/* 2394 */     if (contentSpec.otherValue != null) {
/* 2395 */       rightNode = ((int[])contentSpec.otherValue)[0];
/*      */     } else {
/*      */       return;
/*      */     } 
/* 2399 */     if (contentSpec.type == 4 || contentSpec.type == 5) {
/*      */       
/* 2401 */       contentSpecTree(leftNode, contentSpec, children);
/* 2402 */       contentSpecTree(rightNode, contentSpec, children);
/*      */       
/*      */       return;
/*      */     } 
/* 2406 */     if (contentSpec.type == 1 || contentSpec.type == 2 || contentSpec.type == 3) {
/*      */ 
/*      */       
/* 2409 */       contentSpecTree(leftNode, contentSpec, children);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2414 */     throw new RuntimeException("Invalid content spec type seen in contentSpecTree() method of AbstractDTDGrammar class : " + contentSpec.type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureElementDeclCapacity(int chunk) {
/* 2421 */     if (chunk >= this.fElementDeclName.length) {
/* 2422 */       this.fElementDeclIsExternal = resize(this.fElementDeclIsExternal, this.fElementDeclIsExternal.length * 2);
/*      */ 
/*      */       
/* 2425 */       this.fElementDeclName = resize(this.fElementDeclName, this.fElementDeclName.length * 2);
/* 2426 */       this.fElementDeclType = resize(this.fElementDeclType, this.fElementDeclType.length * 2);
/* 2427 */       this.fElementDeclContentModelValidator = resize(this.fElementDeclContentModelValidator, this.fElementDeclContentModelValidator.length * 2);
/* 2428 */       this.fElementDeclContentSpecIndex = resize(this.fElementDeclContentSpecIndex, this.fElementDeclContentSpecIndex.length * 2);
/* 2429 */       this.fElementDeclFirstAttributeDeclIndex = resize(this.fElementDeclFirstAttributeDeclIndex, this.fElementDeclFirstAttributeDeclIndex.length * 2);
/* 2430 */       this.fElementDeclLastAttributeDeclIndex = resize(this.fElementDeclLastAttributeDeclIndex, this.fElementDeclLastAttributeDeclIndex.length * 2);
/*      */     }
/* 2432 */     else if (this.fElementDeclName[chunk] != null) {
/*      */       return;
/*      */     } 
/*      */     
/* 2436 */     this.fElementDeclIsExternal[chunk] = new int[256];
/* 2437 */     this.fElementDeclName[chunk] = new QName[256];
/* 2438 */     this.fElementDeclType[chunk] = new short[256];
/* 2439 */     this.fElementDeclContentModelValidator[chunk] = new ContentModelValidator[256];
/* 2440 */     this.fElementDeclContentSpecIndex[chunk] = new int[256];
/* 2441 */     this.fElementDeclFirstAttributeDeclIndex[chunk] = new int[256];
/* 2442 */     this.fElementDeclLastAttributeDeclIndex[chunk] = new int[256];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureAttributeDeclCapacity(int chunk) {
/* 2448 */     if (chunk >= this.fAttributeDeclName.length) {
/* 2449 */       this.fAttributeDeclIsExternal = resize(this.fAttributeDeclIsExternal, this.fAttributeDeclIsExternal.length * 2);
/*      */       
/* 2451 */       this.fAttributeDeclName = resize(this.fAttributeDeclName, this.fAttributeDeclName.length * 2);
/* 2452 */       this.fAttributeDeclType = resize(this.fAttributeDeclType, this.fAttributeDeclType.length * 2);
/* 2453 */       this.fAttributeDeclEnumeration = resize(this.fAttributeDeclEnumeration, this.fAttributeDeclEnumeration.length * 2);
/* 2454 */       this.fAttributeDeclDefaultType = resize(this.fAttributeDeclDefaultType, this.fAttributeDeclDefaultType.length * 2);
/* 2455 */       this.fAttributeDeclDatatypeValidator = resize(this.fAttributeDeclDatatypeValidator, this.fAttributeDeclDatatypeValidator.length * 2);
/* 2456 */       this.fAttributeDeclDefaultValue = resize(this.fAttributeDeclDefaultValue, this.fAttributeDeclDefaultValue.length * 2);
/* 2457 */       this.fAttributeDeclNonNormalizedDefaultValue = resize(this.fAttributeDeclNonNormalizedDefaultValue, this.fAttributeDeclNonNormalizedDefaultValue.length * 2);
/* 2458 */       this.fAttributeDeclNextAttributeDeclIndex = resize(this.fAttributeDeclNextAttributeDeclIndex, this.fAttributeDeclNextAttributeDeclIndex.length * 2);
/*      */     }
/* 2460 */     else if (this.fAttributeDeclName[chunk] != null) {
/*      */       return;
/*      */     } 
/*      */     
/* 2464 */     this.fAttributeDeclIsExternal[chunk] = new int[256];
/* 2465 */     this.fAttributeDeclName[chunk] = new QName[256];
/* 2466 */     this.fAttributeDeclType[chunk] = new short[256];
/* 2467 */     this.fAttributeDeclEnumeration[chunk] = new String[256][];
/* 2468 */     this.fAttributeDeclDefaultType[chunk] = new short[256];
/* 2469 */     this.fAttributeDeclDatatypeValidator[chunk] = new DatatypeValidator[256];
/* 2470 */     this.fAttributeDeclDefaultValue[chunk] = new String[256];
/* 2471 */     this.fAttributeDeclNonNormalizedDefaultValue[chunk] = new String[256];
/* 2472 */     this.fAttributeDeclNextAttributeDeclIndex[chunk] = new int[256];
/*      */   }
/*      */ 
/*      */   
/*      */   private void ensureEntityDeclCapacity(int chunk) {
/* 2477 */     if (chunk >= this.fEntityName.length) {
/* 2478 */       this.fEntityName = resize(this.fEntityName, this.fEntityName.length * 2);
/* 2479 */       this.fEntityValue = resize(this.fEntityValue, this.fEntityValue.length * 2);
/* 2480 */       this.fEntityPublicId = resize(this.fEntityPublicId, this.fEntityPublicId.length * 2);
/* 2481 */       this.fEntitySystemId = resize(this.fEntitySystemId, this.fEntitySystemId.length * 2);
/* 2482 */       this.fEntityBaseSystemId = resize(this.fEntityBaseSystemId, this.fEntityBaseSystemId.length * 2);
/* 2483 */       this.fEntityNotation = resize(this.fEntityNotation, this.fEntityNotation.length * 2);
/* 2484 */       this.fEntityIsPE = resize(this.fEntityIsPE, this.fEntityIsPE.length * 2);
/* 2485 */       this.fEntityInExternal = resize(this.fEntityInExternal, this.fEntityInExternal.length * 2);
/*      */     }
/* 2487 */     else if (this.fEntityName[chunk] != null) {
/*      */       return;
/*      */     } 
/*      */     
/* 2491 */     this.fEntityName[chunk] = new String[256];
/* 2492 */     this.fEntityValue[chunk] = new String[256];
/* 2493 */     this.fEntityPublicId[chunk] = new String[256];
/* 2494 */     this.fEntitySystemId[chunk] = new String[256];
/* 2495 */     this.fEntityBaseSystemId[chunk] = new String[256];
/* 2496 */     this.fEntityNotation[chunk] = new String[256];
/* 2497 */     this.fEntityIsPE[chunk] = new byte[256];
/* 2498 */     this.fEntityInExternal[chunk] = new byte[256];
/*      */   }
/*      */ 
/*      */   
/*      */   private void ensureNotationDeclCapacity(int chunk) {
/* 2503 */     if (chunk >= this.fNotationName.length) {
/* 2504 */       this.fNotationName = resize(this.fNotationName, this.fNotationName.length * 2);
/* 2505 */       this.fNotationPublicId = resize(this.fNotationPublicId, this.fNotationPublicId.length * 2);
/* 2506 */       this.fNotationSystemId = resize(this.fNotationSystemId, this.fNotationSystemId.length * 2);
/* 2507 */       this.fNotationBaseSystemId = resize(this.fNotationBaseSystemId, this.fNotationBaseSystemId.length * 2);
/*      */     }
/* 2509 */     else if (this.fNotationName[chunk] != null) {
/*      */       return;
/*      */     } 
/*      */     
/* 2513 */     this.fNotationName[chunk] = new String[256];
/* 2514 */     this.fNotationPublicId[chunk] = new String[256];
/* 2515 */     this.fNotationSystemId[chunk] = new String[256];
/* 2516 */     this.fNotationBaseSystemId[chunk] = new String[256];
/*      */   }
/*      */ 
/*      */   
/*      */   private void ensureContentSpecCapacity(int chunk) {
/* 2521 */     if (chunk >= this.fContentSpecType.length) {
/* 2522 */       this.fContentSpecType = resize(this.fContentSpecType, this.fContentSpecType.length * 2);
/* 2523 */       this.fContentSpecValue = resize(this.fContentSpecValue, this.fContentSpecValue.length * 2);
/* 2524 */       this.fContentSpecOtherValue = resize(this.fContentSpecOtherValue, this.fContentSpecOtherValue.length * 2);
/*      */     }
/* 2526 */     else if (this.fContentSpecType[chunk] != null) {
/*      */       return;
/*      */     } 
/*      */     
/* 2530 */     this.fContentSpecType[chunk] = new short[256];
/* 2531 */     this.fContentSpecValue[chunk] = new Object[256];
/* 2532 */     this.fContentSpecOtherValue[chunk] = new Object[256];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[][] resize(byte[][] array, int newsize) {
/* 2543 */     byte[][] newarray = new byte[newsize][];
/* 2544 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2545 */     return newarray;
/*      */   }
/*      */   
/*      */   private static short[][] resize(short[][] array, int newsize) {
/* 2549 */     short[][] newarray = new short[newsize][];
/* 2550 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2551 */     return newarray;
/*      */   }
/*      */   
/*      */   private static int[][] resize(int[][] array, int newsize) {
/* 2555 */     int[][] newarray = new int[newsize][];
/* 2556 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2557 */     return newarray;
/*      */   }
/*      */   
/*      */   private static DatatypeValidator[][] resize(DatatypeValidator[][] array, int newsize) {
/* 2561 */     DatatypeValidator[][] newarray = new DatatypeValidator[newsize][];
/* 2562 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2563 */     return newarray;
/*      */   }
/*      */   
/*      */   private static ContentModelValidator[][] resize(ContentModelValidator[][] array, int newsize) {
/* 2567 */     ContentModelValidator[][] newarray = new ContentModelValidator[newsize][];
/* 2568 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2569 */     return newarray;
/*      */   }
/*      */   
/*      */   private static Object[][] resize(Object[][] array, int newsize) {
/* 2573 */     Object[][] newarray = new Object[newsize][];
/* 2574 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2575 */     return newarray;
/*      */   }
/*      */   
/*      */   private static QName[][] resize(QName[][] array, int newsize) {
/* 2579 */     QName[][] newarray = new QName[newsize][];
/* 2580 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2581 */     return newarray;
/*      */   }
/*      */   
/*      */   private static String[][] resize(String[][] array, int newsize) {
/* 2585 */     String[][] newarray = new String[newsize][];
/* 2586 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2587 */     return newarray;
/*      */   }
/*      */   
/*      */   private static String[][][] resize(String[][][] array, int newsize) {
/* 2591 */     String[][][] newarray = new String[newsize][][];
/* 2592 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 2593 */     return newarray;
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
/*      */   private static class ChildrenList
/*      */   {
/* 2614 */     public int length = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2621 */     public QName[] qname = new QName[2];
/*      */ 
/*      */     
/* 2624 */     public int[] type = new int[2];
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
/*      */   public boolean isEntityDeclared(String name) {
/* 2638 */     return (getEntityDeclIndex(name) != -1);
/*      */   }
/*      */   
/*      */   public boolean isEntityUnparsed(String name) {
/* 2642 */     int entityIndex = getEntityDeclIndex(name);
/* 2643 */     if (entityIndex > -1) {
/* 2644 */       int chunk = entityIndex >> 8;
/* 2645 */       int index = entityIndex & 0xFF;
/*      */       
/* 2647 */       return (this.fEntityNotation[chunk][index] != null);
/*      */     } 
/* 2649 */     return false;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/DTDGrammar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */