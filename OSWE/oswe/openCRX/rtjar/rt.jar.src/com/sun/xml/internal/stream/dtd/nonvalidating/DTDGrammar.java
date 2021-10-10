/*     */ package com.sun.xml.internal.stream.dtd.nonvalidating;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDContentModelSource;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDSource;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTDGrammar
/*     */ {
/*     */   public static final int TOP_LEVEL_SCOPE = -1;
/*     */   private static final int CHUNK_SHIFT = 8;
/*     */   private static final int CHUNK_SIZE = 256;
/*     */   private static final int CHUNK_MASK = 255;
/*     */   private static final int INITIAL_CHUNK_COUNT = 4;
/*     */   private static final short LIST_FLAG = 128;
/*     */   private static final short LIST_MASK = -129;
/*     */   private static final boolean DEBUG = false;
/*  84 */   protected XMLDTDSource fDTDSource = null;
/*  85 */   protected XMLDTDContentModelSource fDTDContentModelSource = null;
/*     */ 
/*     */   
/*     */   protected int fCurrentElementIndex;
/*     */ 
/*     */   
/*     */   protected int fCurrentAttributeIndex;
/*     */ 
/*     */   
/*     */   protected boolean fReadingExternalDTD = false;
/*     */   
/*     */   private SymbolTable fSymbolTable;
/*     */   
/*  98 */   private ArrayList notationDecls = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private int fElementDeclCount = 0;
/*     */ 
/*     */   
/* 106 */   private QName[][] fElementDeclName = new QName[4][];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private short[][] fElementDeclType = new short[4][];
/*     */ 
/*     */ 
/*     */   
/* 116 */   private int[][] fElementDeclFirstAttributeDeclIndex = new int[4][];
/*     */ 
/*     */   
/* 119 */   private int[][] fElementDeclLastAttributeDeclIndex = new int[4][];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   private int fAttributeDeclCount = 0;
/*     */ 
/*     */   
/* 127 */   private QName[][] fAttributeDeclName = new QName[4][];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   private short[][] fAttributeDeclType = new short[4][];
/*     */ 
/*     */   
/* 136 */   private String[][][] fAttributeDeclEnumeration = new String[4][][];
/* 137 */   private short[][] fAttributeDeclDefaultType = new short[4][];
/* 138 */   private String[][] fAttributeDeclDefaultValue = new String[4][];
/* 139 */   private String[][] fAttributeDeclNonNormalizedDefaultValue = new String[4][];
/* 140 */   private int[][] fAttributeDeclNextAttributeDeclIndex = new int[4][];
/*     */ 
/*     */   
/* 143 */   private final Map<String, Integer> fElementIndexMap = new HashMap<>();
/*     */ 
/*     */   
/* 146 */   private final QName fQName = new QName();
/*     */ 
/*     */   
/* 149 */   protected XMLAttributeDecl fAttributeDecl = new XMLAttributeDecl();
/*     */ 
/*     */   
/* 152 */   private XMLElementDecl fElementDecl = new XMLElementDecl();
/*     */ 
/*     */   
/* 155 */   private XMLSimpleType fSimpleType = new XMLSimpleType();
/*     */ 
/*     */ 
/*     */   
/* 159 */   Map<String, XMLElementDecl> fElementDeclTab = new HashMap<>();
/*     */ 
/*     */   
/*     */   public DTDGrammar(SymbolTable symbolTable) {
/* 163 */     this.fSymbolTable = symbolTable;
/*     */   }
/*     */   
/*     */   public int getAttributeDeclIndex(int elementDeclIndex, String attributeDeclName) {
/* 167 */     if (elementDeclIndex == -1) {
/* 168 */       return -1;
/*     */     }
/* 170 */     int attDefIndex = getFirstAttributeDeclIndex(elementDeclIndex);
/* 171 */     while (attDefIndex != -1) {
/* 172 */       getAttributeDecl(attDefIndex, this.fAttributeDecl);
/*     */       
/* 174 */       if (this.fAttributeDecl.name.rawname == attributeDeclName || attributeDeclName
/* 175 */         .equals(this.fAttributeDecl.name.rawname)) {
/* 176 */         return attDefIndex;
/*     */       }
/* 178 */       attDefIndex = getNextAttributeDeclIndex(attDefIndex);
/*     */     } 
/* 180 */     return -1;
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
/*     */   public void startDTD(XMLLocator locator, Augmentations augs) throws XNIException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void elementDecl(String name, String contentModel, Augmentations augs) throws XNIException {
/* 216 */     XMLElementDecl tmpElementDecl = this.fElementDeclTab.get(name);
/* 217 */     if (tmpElementDecl != null) {
/* 218 */       if (tmpElementDecl.type == -1) {
/* 219 */         this.fCurrentElementIndex = getElementDeclIndex(name);
/*     */       }
/*     */       else {
/*     */         
/*     */         return;
/*     */       } 
/*     */     } else {
/*     */       
/* 227 */       this.fCurrentElementIndex = createElementDecl();
/*     */     } 
/*     */     
/* 230 */     XMLElementDecl elementDecl = new XMLElementDecl();
/* 231 */     QName elementName = new QName(null, name, name, null);
/*     */     
/* 233 */     elementDecl.name.setValues(elementName);
/* 234 */     elementDecl.scope = -1;
/* 235 */     if (contentModel.equals("EMPTY")) {
/* 236 */       elementDecl.type = 1;
/*     */     }
/* 238 */     else if (contentModel.equals("ANY")) {
/* 239 */       elementDecl.type = 0;
/*     */     }
/* 241 */     else if (contentModel.startsWith("(")) {
/* 242 */       if (contentModel.indexOf("#PCDATA") > 0) {
/* 243 */         elementDecl.type = 2;
/*     */       } else {
/*     */         
/* 246 */         elementDecl.type = 3;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 252 */     this.fElementDeclTab.put(name, elementDecl);
/*     */     
/* 254 */     this.fElementDecl = elementDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     setElementDecl(this.fCurrentElementIndex, this.fElementDecl);
/*     */     
/* 264 */     int chunk = this.fCurrentElementIndex >> 8;
/* 265 */     ensureElementDeclCapacity(chunk);
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
/*     */   public void attributeDecl(String elementName, String attributeName, String type, String[] enumeration, String defaultType, XMLString defaultValue, XMLString nonNormalizedDefaultValue, Augmentations augs) throws XNIException {
/* 298 */     if (type != XMLSymbols.fCDATASymbol && defaultValue != null) {
/* 299 */       normalizeDefaultAttrValue(defaultValue);
/*     */     }
/*     */     
/* 302 */     if (!this.fElementDeclTab.containsKey(elementName)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 308 */       this.fCurrentElementIndex = createElementDecl();
/*     */       
/* 310 */       XMLElementDecl elementDecl = new XMLElementDecl();
/* 311 */       elementDecl.name.setValues(null, elementName, elementName, null);
/*     */       
/* 313 */       elementDecl.scope = -1;
/*     */ 
/*     */       
/* 316 */       this.fElementDeclTab.put(elementName, elementDecl);
/*     */ 
/*     */       
/* 319 */       setElementDecl(this.fCurrentElementIndex, elementDecl);
/*     */     } 
/*     */ 
/*     */     
/* 323 */     int elementIndex = getElementDeclIndex(elementName);
/*     */ 
/*     */ 
/*     */     
/* 327 */     if (getAttributeDeclIndex(elementIndex, attributeName) != -1) {
/*     */       return;
/*     */     }
/*     */     
/* 331 */     this.fCurrentAttributeIndex = createAttributeDecl();
/*     */     
/* 333 */     this.fSimpleType.clear();
/* 334 */     if (defaultType != null) {
/* 335 */       if (defaultType.equals("#FIXED")) {
/* 336 */         this.fSimpleType.defaultType = 1;
/* 337 */       } else if (defaultType.equals("#IMPLIED")) {
/* 338 */         this.fSimpleType.defaultType = 0;
/* 339 */       } else if (defaultType.equals("#REQUIRED")) {
/* 340 */         this.fSimpleType.defaultType = 2;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 346 */     this.fSimpleType.defaultValue = (defaultValue != null) ? defaultValue.toString() : null;
/* 347 */     this.fSimpleType.nonNormalizedDefaultValue = (nonNormalizedDefaultValue != null) ? nonNormalizedDefaultValue.toString() : null;
/* 348 */     this.fSimpleType.enumeration = enumeration;
/*     */     
/* 350 */     if (type.equals("CDATA")) {
/* 351 */       this.fSimpleType.type = 0;
/*     */     }
/* 353 */     else if (type.equals("ID")) {
/* 354 */       this.fSimpleType.type = 3;
/*     */     }
/* 356 */     else if (type.startsWith("IDREF")) {
/* 357 */       this.fSimpleType.type = 4;
/* 358 */       if (type.indexOf("S") > 0) {
/* 359 */         this.fSimpleType.list = true;
/*     */       }
/*     */     }
/* 362 */     else if (type.equals("ENTITIES")) {
/* 363 */       this.fSimpleType.type = 1;
/* 364 */       this.fSimpleType.list = true;
/*     */     }
/* 366 */     else if (type.equals("ENTITY")) {
/* 367 */       this.fSimpleType.type = 1;
/*     */     }
/* 369 */     else if (type.equals("NMTOKENS")) {
/* 370 */       this.fSimpleType.type = 5;
/* 371 */       this.fSimpleType.list = true;
/*     */     }
/* 373 */     else if (type.equals("NMTOKEN")) {
/* 374 */       this.fSimpleType.type = 5;
/*     */     }
/* 376 */     else if (type.startsWith("NOTATION")) {
/* 377 */       this.fSimpleType.type = 6;
/*     */     }
/* 379 */     else if (type.startsWith("ENUMERATION")) {
/* 380 */       this.fSimpleType.type = 2;
/*     */     }
/*     */     else {
/*     */       
/* 384 */       System.err.println("!!! unknown attribute type " + type);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 390 */     this.fQName.setValues(null, attributeName, attributeName, null);
/* 391 */     this.fAttributeDecl.setValues(this.fQName, this.fSimpleType, false);
/*     */     
/* 393 */     setAttributeDecl(elementIndex, this.fCurrentAttributeIndex, this.fAttributeDecl);
/*     */     
/* 395 */     int chunk = this.fCurrentAttributeIndex >> 8;
/* 396 */     ensureAttributeDeclCapacity(chunk);
/*     */   }
/*     */ 
/*     */   
/*     */   public SymbolTable getSymbolTable() {
/* 401 */     return this.fSymbolTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstElementDeclIndex() {
/* 412 */     return (this.fElementDeclCount >= 0) ? 0 : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextElementDeclIndex(int elementDeclIndex) {
/* 422 */     return (elementDeclIndex < this.fElementDeclCount - 1) ? (elementDeclIndex + 1) : -1;
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
/*     */   public int getElementDeclIndex(String elementDeclName) {
/* 434 */     Integer mapping = this.fElementIndexMap.get(elementDeclName);
/* 435 */     if (mapping == null) {
/* 436 */       mapping = Integer.valueOf(-1);
/*     */     }
/*     */     
/* 439 */     return mapping.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getElementDeclIndex(QName elementDeclQName) {
/* 446 */     return getElementDeclIndex(elementDeclQName.rawname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getContentSpecType(int elementIndex) {
/* 454 */     if (elementIndex < 0 || elementIndex >= this.fElementDeclCount) {
/* 455 */       return -1;
/*     */     }
/*     */     
/* 458 */     int chunk = elementIndex >> 8;
/* 459 */     int index = elementIndex & 0xFF;
/*     */     
/* 461 */     if (this.fElementDeclType[chunk][index] == -1) {
/* 462 */       return -1;
/*     */     }
/*     */     
/* 465 */     return (short)(this.fElementDeclType[chunk][index] & 0xFFFFFF7F);
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
/*     */   public boolean getElementDecl(int elementDeclIndex, XMLElementDecl elementDecl) {
/* 480 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/* 481 */       return false;
/*     */     }
/*     */     
/* 484 */     int chunk = elementDeclIndex >> 8;
/* 485 */     int index = elementDeclIndex & 0xFF;
/*     */     
/* 487 */     elementDecl.name.setValues(this.fElementDeclName[chunk][index]);
/*     */     
/* 489 */     if (this.fElementDeclType[chunk][index] == -1) {
/* 490 */       elementDecl.type = -1;
/* 491 */       elementDecl.simpleType.list = false;
/*     */     } else {
/* 493 */       elementDecl.type = (short)(this.fElementDeclType[chunk][index] & 0xFFFFFF7F);
/* 494 */       elementDecl.simpleType.list = ((this.fElementDeclType[chunk][index] & 0x80) != 0);
/*     */     } 
/*     */     
/* 497 */     elementDecl.simpleType.defaultType = -1;
/* 498 */     elementDecl.simpleType.defaultValue = null;
/* 499 */     return true;
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
/*     */   public int getFirstAttributeDeclIndex(int elementDeclIndex) {
/* 513 */     int chunk = elementDeclIndex >> 8;
/* 514 */     int index = elementDeclIndex & 0xFF;
/*     */     
/* 516 */     return this.fElementDeclFirstAttributeDeclIndex[chunk][index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextAttributeDeclIndex(int attributeDeclIndex) {
/* 527 */     int chunk = attributeDeclIndex >> 8;
/* 528 */     int index = attributeDeclIndex & 0xFF;
/*     */     
/* 530 */     return this.fAttributeDeclNextAttributeDeclIndex[chunk][index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAttributeDecl(int attributeDeclIndex, XMLAttributeDecl attributeDecl) {
/*     */     short attributeType;
/*     */     boolean isList;
/* 542 */     if (attributeDeclIndex < 0 || attributeDeclIndex >= this.fAttributeDeclCount) {
/* 543 */       return false;
/*     */     }
/* 545 */     int chunk = attributeDeclIndex >> 8;
/* 546 */     int index = attributeDeclIndex & 0xFF;
/*     */     
/* 548 */     attributeDecl.name.setValues(this.fAttributeDeclName[chunk][index]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 553 */     if (this.fAttributeDeclType[chunk][index] == -1) {
/*     */       
/* 555 */       attributeType = -1;
/* 556 */       isList = false;
/*     */     } else {
/* 558 */       attributeType = (short)(this.fAttributeDeclType[chunk][index] & 0xFFFFFF7F);
/* 559 */       isList = ((this.fAttributeDeclType[chunk][index] & 0x80) != 0);
/*     */     } 
/* 561 */     attributeDecl.simpleType.setValues(attributeType, (this.fAttributeDeclName[chunk][index]).localpart, this.fAttributeDeclEnumeration[chunk][index], isList, this.fAttributeDeclDefaultType[chunk][index], this.fAttributeDeclDefaultValue[chunk][index], this.fAttributeDeclNonNormalizedDefaultValue[chunk][index]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 566 */     return true;
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
/*     */   public boolean isCDATAAttribute(QName elName, QName atName) {
/* 580 */     int elDeclIdx = getElementDeclIndex(elName);
/* 581 */     if (getAttributeDecl(elDeclIdx, this.fAttributeDecl) && this.fAttributeDecl.simpleType.type != 0)
/*     */     {
/* 583 */       return false;
/*     */     }
/* 585 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void printElements() {
/* 591 */     int elementDeclIndex = 0;
/* 592 */     XMLElementDecl elementDecl = new XMLElementDecl();
/* 593 */     while (getElementDecl(elementDeclIndex++, elementDecl))
/*     */     {
/* 595 */       System.out.println("element decl: " + elementDecl.name + ", " + elementDecl.name.rawname);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void printAttributes(int elementDeclIndex) {
/* 602 */     int attributeDeclIndex = getFirstAttributeDeclIndex(elementDeclIndex);
/* 603 */     System.out.print(elementDeclIndex);
/* 604 */     System.out.print(" [");
/* 605 */     while (attributeDeclIndex != -1) {
/* 606 */       System.out.print(' ');
/* 607 */       System.out.print(attributeDeclIndex);
/* 608 */       printAttribute(attributeDeclIndex);
/* 609 */       attributeDeclIndex = getNextAttributeDeclIndex(attributeDeclIndex);
/* 610 */       if (attributeDeclIndex != -1) {
/* 611 */         System.out.print(",");
/*     */       }
/*     */     } 
/* 614 */     System.out.println(" ]");
/*     */   }
/*     */ 
/*     */   
/*     */   protected int createElementDecl() {
/* 619 */     int chunk = this.fElementDeclCount >> 8;
/* 620 */     int index = this.fElementDeclCount & 0xFF;
/* 621 */     ensureElementDeclCapacity(chunk);
/* 622 */     this.fElementDeclName[chunk][index] = new QName();
/* 623 */     this.fElementDeclType[chunk][index] = -1;
/* 624 */     this.fElementDeclFirstAttributeDeclIndex[chunk][index] = -1;
/* 625 */     this.fElementDeclLastAttributeDeclIndex[chunk][index] = -1;
/* 626 */     return this.fElementDeclCount++;
/*     */   }
/*     */   
/*     */   protected void setElementDecl(int elementDeclIndex, XMLElementDecl elementDecl) {
/* 630 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/*     */       return;
/*     */     }
/* 633 */     int chunk = elementDeclIndex >> 8;
/* 634 */     int index = elementDeclIndex & 0xFF;
/*     */     
/* 636 */     int scope = elementDecl.scope;
/*     */ 
/*     */     
/* 639 */     this.fElementDeclName[chunk][index].setValues(elementDecl.name);
/* 640 */     this.fElementDeclType[chunk][index] = elementDecl.type;
/*     */ 
/*     */ 
/*     */     
/* 644 */     if (elementDecl.simpleType.list == true) {
/* 645 */       this.fElementDeclType[chunk][index] = (short)(this.fElementDeclType[chunk][index] | 0x80);
/*     */     }
/*     */     
/* 648 */     this.fElementIndexMap.put(elementDecl.name.rawname, Integer.valueOf(elementDeclIndex));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFirstAttributeDeclIndex(int elementDeclIndex, int newFirstAttrIndex) {
/* 656 */     if (elementDeclIndex < 0 || elementDeclIndex >= this.fElementDeclCount) {
/*     */       return;
/*     */     }
/*     */     
/* 660 */     int chunk = elementDeclIndex >> 8;
/* 661 */     int index = elementDeclIndex & 0xFF;
/*     */     
/* 663 */     this.fElementDeclFirstAttributeDeclIndex[chunk][index] = newFirstAttrIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int createAttributeDecl() {
/* 668 */     int chunk = this.fAttributeDeclCount >> 8;
/* 669 */     int index = this.fAttributeDeclCount & 0xFF;
/*     */     
/* 671 */     ensureAttributeDeclCapacity(chunk);
/* 672 */     this.fAttributeDeclName[chunk][index] = new QName();
/* 673 */     this.fAttributeDeclType[chunk][index] = -1;
/* 674 */     this.fAttributeDeclEnumeration[chunk][index] = null;
/* 675 */     this.fAttributeDeclDefaultType[chunk][index] = 0;
/* 676 */     this.fAttributeDeclDefaultValue[chunk][index] = null;
/* 677 */     this.fAttributeDeclNonNormalizedDefaultValue[chunk][index] = null;
/* 678 */     this.fAttributeDeclNextAttributeDeclIndex[chunk][index] = -1;
/* 679 */     return this.fAttributeDeclCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAttributeDecl(int elementDeclIndex, int attributeDeclIndex, XMLAttributeDecl attributeDecl) {
/* 685 */     int attrChunk = attributeDeclIndex >> 8;
/* 686 */     int attrIndex = attributeDeclIndex & 0xFF;
/* 687 */     this.fAttributeDeclName[attrChunk][attrIndex].setValues(attributeDecl.name);
/* 688 */     this.fAttributeDeclType[attrChunk][attrIndex] = attributeDecl.simpleType.type;
/*     */     
/* 690 */     if (attributeDecl.simpleType.list) {
/* 691 */       this.fAttributeDeclType[attrChunk][attrIndex] = (short)(this.fAttributeDeclType[attrChunk][attrIndex] | 0x80);
/*     */     }
/* 693 */     this.fAttributeDeclEnumeration[attrChunk][attrIndex] = attributeDecl.simpleType.enumeration;
/* 694 */     this.fAttributeDeclDefaultType[attrChunk][attrIndex] = attributeDecl.simpleType.defaultType;
/*     */     
/* 696 */     this.fAttributeDeclDefaultValue[attrChunk][attrIndex] = attributeDecl.simpleType.defaultValue;
/* 697 */     this.fAttributeDeclNonNormalizedDefaultValue[attrChunk][attrIndex] = attributeDecl.simpleType.nonNormalizedDefaultValue;
/*     */     
/* 699 */     int elemChunk = elementDeclIndex >> 8;
/* 700 */     int elemIndex = elementDeclIndex & 0xFF;
/* 701 */     int index = this.fElementDeclFirstAttributeDeclIndex[elemChunk][elemIndex];
/* 702 */     while (index != -1 && 
/* 703 */       index != attributeDeclIndex) {
/*     */ 
/*     */       
/* 706 */       attrChunk = index >> 8;
/* 707 */       attrIndex = index & 0xFF;
/* 708 */       index = this.fAttributeDeclNextAttributeDeclIndex[attrChunk][attrIndex];
/*     */     } 
/* 710 */     if (index == -1) {
/* 711 */       if (this.fElementDeclFirstAttributeDeclIndex[elemChunk][elemIndex] == -1) {
/* 712 */         this.fElementDeclFirstAttributeDeclIndex[elemChunk][elemIndex] = attributeDeclIndex;
/*     */       } else {
/* 714 */         index = this.fElementDeclLastAttributeDeclIndex[elemChunk][elemIndex];
/* 715 */         attrChunk = index >> 8;
/* 716 */         attrIndex = index & 0xFF;
/* 717 */         this.fAttributeDeclNextAttributeDeclIndex[attrChunk][attrIndex] = attributeDeclIndex;
/*     */       } 
/* 719 */       this.fElementDeclLastAttributeDeclIndex[elemChunk][elemIndex] = attributeDeclIndex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notationDecl(String name, XMLResourceIdentifier identifier, Augmentations augs) throws XNIException {
/* 727 */     XMLNotationDecl notationDecl = new XMLNotationDecl();
/* 728 */     notationDecl.setValues(name, identifier.getPublicId(), identifier.getLiteralSystemId(), identifier
/* 729 */         .getBaseSystemId());
/* 730 */     this.notationDecls.add(notationDecl);
/*     */   }
/*     */   
/*     */   public List getNotationDecls() {
/* 734 */     return this.notationDecls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printAttribute(int attributeDeclIndex) {
/* 742 */     XMLAttributeDecl attributeDecl = new XMLAttributeDecl();
/* 743 */     if (getAttributeDecl(attributeDeclIndex, attributeDecl)) {
/* 744 */       System.out.print(" { ");
/* 745 */       System.out.print(attributeDecl.name.localpart);
/* 746 */       System.out.print(" }");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureElementDeclCapacity(int chunk) {
/* 754 */     if (chunk >= this.fElementDeclName.length) {
/*     */       
/* 756 */       this.fElementDeclName = resize(this.fElementDeclName, this.fElementDeclName.length * 2);
/* 757 */       this.fElementDeclType = resize(this.fElementDeclType, this.fElementDeclType.length * 2);
/* 758 */       this.fElementDeclFirstAttributeDeclIndex = resize(this.fElementDeclFirstAttributeDeclIndex, this.fElementDeclFirstAttributeDeclIndex.length * 2);
/* 759 */       this.fElementDeclLastAttributeDeclIndex = resize(this.fElementDeclLastAttributeDeclIndex, this.fElementDeclLastAttributeDeclIndex.length * 2);
/*     */     }
/* 761 */     else if (this.fElementDeclName[chunk] != null) {
/*     */       return;
/*     */     } 
/*     */     
/* 765 */     this.fElementDeclName[chunk] = new QName[256];
/* 766 */     this.fElementDeclType[chunk] = new short[256];
/* 767 */     this.fElementDeclFirstAttributeDeclIndex[chunk] = new int[256];
/* 768 */     this.fElementDeclLastAttributeDeclIndex[chunk] = new int[256];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureAttributeDeclCapacity(int chunk) {
/* 774 */     if (chunk >= this.fAttributeDeclName.length) {
/* 775 */       this.fAttributeDeclName = resize(this.fAttributeDeclName, this.fAttributeDeclName.length * 2);
/* 776 */       this.fAttributeDeclType = resize(this.fAttributeDeclType, this.fAttributeDeclType.length * 2);
/* 777 */       this.fAttributeDeclEnumeration = resize(this.fAttributeDeclEnumeration, this.fAttributeDeclEnumeration.length * 2);
/* 778 */       this.fAttributeDeclDefaultType = resize(this.fAttributeDeclDefaultType, this.fAttributeDeclDefaultType.length * 2);
/* 779 */       this.fAttributeDeclDefaultValue = resize(this.fAttributeDeclDefaultValue, this.fAttributeDeclDefaultValue.length * 2);
/* 780 */       this.fAttributeDeclNonNormalizedDefaultValue = resize(this.fAttributeDeclNonNormalizedDefaultValue, this.fAttributeDeclNonNormalizedDefaultValue.length * 2);
/* 781 */       this.fAttributeDeclNextAttributeDeclIndex = resize(this.fAttributeDeclNextAttributeDeclIndex, this.fAttributeDeclNextAttributeDeclIndex.length * 2);
/*     */     }
/* 783 */     else if (this.fAttributeDeclName[chunk] != null) {
/*     */       return;
/*     */     } 
/*     */     
/* 787 */     this.fAttributeDeclName[chunk] = new QName[256];
/* 788 */     this.fAttributeDeclType[chunk] = new short[256];
/* 789 */     this.fAttributeDeclEnumeration[chunk] = new String[256][];
/* 790 */     this.fAttributeDeclDefaultType[chunk] = new short[256];
/* 791 */     this.fAttributeDeclDefaultValue[chunk] = new String[256];
/* 792 */     this.fAttributeDeclNonNormalizedDefaultValue[chunk] = new String[256];
/* 793 */     this.fAttributeDeclNextAttributeDeclIndex[chunk] = new int[256];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static short[][] resize(short[][] array, int newsize) {
/* 801 */     short[][] newarray = new short[newsize][];
/* 802 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 803 */     return newarray;
/*     */   }
/*     */   
/*     */   private static int[][] resize(int[][] array, int newsize) {
/* 807 */     int[][] newarray = new int[newsize][];
/* 808 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 809 */     return newarray;
/*     */   }
/*     */   
/*     */   private static QName[][] resize(QName[][] array, int newsize) {
/* 813 */     QName[][] newarray = new QName[newsize][];
/* 814 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 815 */     return newarray;
/*     */   }
/*     */   
/*     */   private static String[][] resize(String[][] array, int newsize) {
/* 819 */     String[][] newarray = new String[newsize][];
/* 820 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 821 */     return newarray;
/*     */   }
/*     */   
/*     */   private static String[][][] resize(String[][][] array, int newsize) {
/* 825 */     String[][][] newarray = new String[newsize][][];
/* 826 */     System.arraycopy(array, 0, newarray, 0, array.length);
/* 827 */     return newarray;
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
/*     */   private boolean normalizeDefaultAttrValue(XMLString value) {
/* 839 */     int oldLength = value.length;
/*     */     
/* 841 */     boolean skipSpace = true;
/* 842 */     int current = value.offset;
/* 843 */     int end = value.offset + value.length;
/* 844 */     for (int i = value.offset; i < end; i++) {
/* 845 */       if (value.ch[i] == ' ') {
/* 846 */         if (!skipSpace)
/*     */         {
/* 848 */           value.ch[current++] = ' ';
/* 849 */           skipSpace = true;
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 857 */         if (current != i) {
/* 858 */           value.ch[current] = value.ch[i];
/*     */         }
/* 860 */         current++;
/* 861 */         skipSpace = false;
/*     */       } 
/*     */     } 
/* 864 */     if (current != end) {
/* 865 */       if (skipSpace)
/*     */       {
/* 867 */         current--;
/*     */       }
/*     */       
/* 870 */       value.length = current - value.offset;
/* 871 */       return true;
/*     */     } 
/* 873 */     return false;
/*     */   }
/*     */   
/*     */   public void endDTD(Augmentations augs) throws XNIException {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/dtd/nonvalidating/DTDGrammar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */