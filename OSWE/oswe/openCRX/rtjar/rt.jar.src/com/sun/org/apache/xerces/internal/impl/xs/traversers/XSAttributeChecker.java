/*      */ package com.sun.org.apache.xerces.internal.impl.xs.traversers;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaGrammar;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSAttributeDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSGrammarBucket;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.XInt;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.XIntPool;
/*      */ import com.sun.org.apache.xerces.internal.util.DOMUtil;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XSAttributeChecker
/*      */ {
/*      */   private static final String ELEMENT_N = "element_n";
/*      */   private static final String ELEMENT_R = "element_r";
/*      */   private static final String ATTRIBUTE_N = "attribute_n";
/*      */   private static final String ATTRIBUTE_R = "attribute_r";
/*   84 */   private static int ATTIDX_COUNT = 0;
/*   85 */   public static final int ATTIDX_ABSTRACT = ATTIDX_COUNT++;
/*   86 */   public static final int ATTIDX_AFORMDEFAULT = ATTIDX_COUNT++;
/*   87 */   public static final int ATTIDX_BASE = ATTIDX_COUNT++;
/*   88 */   public static final int ATTIDX_BLOCK = ATTIDX_COUNT++;
/*   89 */   public static final int ATTIDX_BLOCKDEFAULT = ATTIDX_COUNT++;
/*   90 */   public static final int ATTIDX_DEFAULT = ATTIDX_COUNT++;
/*   91 */   public static final int ATTIDX_EFORMDEFAULT = ATTIDX_COUNT++;
/*   92 */   public static final int ATTIDX_FINAL = ATTIDX_COUNT++;
/*   93 */   public static final int ATTIDX_FINALDEFAULT = ATTIDX_COUNT++;
/*   94 */   public static final int ATTIDX_FIXED = ATTIDX_COUNT++;
/*   95 */   public static final int ATTIDX_FORM = ATTIDX_COUNT++;
/*   96 */   public static final int ATTIDX_ID = ATTIDX_COUNT++;
/*   97 */   public static final int ATTIDX_ITEMTYPE = ATTIDX_COUNT++;
/*   98 */   public static final int ATTIDX_MAXOCCURS = ATTIDX_COUNT++;
/*   99 */   public static final int ATTIDX_MEMBERTYPES = ATTIDX_COUNT++;
/*  100 */   public static final int ATTIDX_MINOCCURS = ATTIDX_COUNT++;
/*  101 */   public static final int ATTIDX_MIXED = ATTIDX_COUNT++;
/*  102 */   public static final int ATTIDX_NAME = ATTIDX_COUNT++;
/*  103 */   public static final int ATTIDX_NAMESPACE = ATTIDX_COUNT++;
/*  104 */   public static final int ATTIDX_NAMESPACE_LIST = ATTIDX_COUNT++;
/*  105 */   public static final int ATTIDX_NILLABLE = ATTIDX_COUNT++;
/*  106 */   public static final int ATTIDX_NONSCHEMA = ATTIDX_COUNT++;
/*  107 */   public static final int ATTIDX_PROCESSCONTENTS = ATTIDX_COUNT++;
/*  108 */   public static final int ATTIDX_PUBLIC = ATTIDX_COUNT++;
/*  109 */   public static final int ATTIDX_REF = ATTIDX_COUNT++;
/*  110 */   public static final int ATTIDX_REFER = ATTIDX_COUNT++;
/*  111 */   public static final int ATTIDX_SCHEMALOCATION = ATTIDX_COUNT++;
/*  112 */   public static final int ATTIDX_SOURCE = ATTIDX_COUNT++;
/*  113 */   public static final int ATTIDX_SUBSGROUP = ATTIDX_COUNT++;
/*  114 */   public static final int ATTIDX_SYSTEM = ATTIDX_COUNT++;
/*  115 */   public static final int ATTIDX_TARGETNAMESPACE = ATTIDX_COUNT++;
/*  116 */   public static final int ATTIDX_TYPE = ATTIDX_COUNT++;
/*  117 */   public static final int ATTIDX_USE = ATTIDX_COUNT++;
/*  118 */   public static final int ATTIDX_VALUE = ATTIDX_COUNT++;
/*  119 */   public static final int ATTIDX_ENUMNSDECLS = ATTIDX_COUNT++;
/*  120 */   public static final int ATTIDX_VERSION = ATTIDX_COUNT++;
/*  121 */   public static final int ATTIDX_XML_LANG = ATTIDX_COUNT++;
/*  122 */   public static final int ATTIDX_XPATH = ATTIDX_COUNT++;
/*  123 */   public static final int ATTIDX_FROMDEFAULT = ATTIDX_COUNT++;
/*      */   
/*  125 */   public static final int ATTIDX_ISRETURNED = ATTIDX_COUNT++;
/*      */   
/*  127 */   private static final XIntPool fXIntPool = new XIntPool();
/*      */   
/*  129 */   private static final XInt INT_QUALIFIED = fXIntPool.getXInt(1);
/*  130 */   private static final XInt INT_UNQUALIFIED = fXIntPool.getXInt(0);
/*  131 */   private static final XInt INT_EMPTY_SET = fXIntPool.getXInt(0);
/*  132 */   private static final XInt INT_ANY_STRICT = fXIntPool.getXInt(1);
/*  133 */   private static final XInt INT_ANY_LAX = fXIntPool.getXInt(3);
/*  134 */   private static final XInt INT_ANY_SKIP = fXIntPool.getXInt(2);
/*  135 */   private static final XInt INT_ANY_ANY = fXIntPool.getXInt(1);
/*  136 */   private static final XInt INT_ANY_LIST = fXIntPool.getXInt(3);
/*  137 */   private static final XInt INT_ANY_NOT = fXIntPool.getXInt(2);
/*  138 */   private static final XInt INT_USE_OPTIONAL = fXIntPool.getXInt(0);
/*  139 */   private static final XInt INT_USE_REQUIRED = fXIntPool.getXInt(1);
/*  140 */   private static final XInt INT_USE_PROHIBITED = fXIntPool.getXInt(2);
/*  141 */   private static final XInt INT_WS_PRESERVE = fXIntPool.getXInt(0);
/*  142 */   private static final XInt INT_WS_REPLACE = fXIntPool.getXInt(1);
/*  143 */   private static final XInt INT_WS_COLLAPSE = fXIntPool.getXInt(2);
/*  144 */   private static final XInt INT_UNBOUNDED = fXIntPool.getXInt(-1);
/*      */ 
/*      */ 
/*      */   
/*  148 */   private static final Map fEleAttrsMapG = new HashMap<>(29);
/*      */   
/*  150 */   private static final Map fEleAttrsMapL = new HashMap<>(79);
/*      */   
/*      */   protected static final int DT_ANYURI = 0;
/*      */   
/*      */   protected static final int DT_ID = 1;
/*      */   protected static final int DT_QNAME = 2;
/*      */   protected static final int DT_STRING = 3;
/*      */   protected static final int DT_TOKEN = 4;
/*      */   protected static final int DT_NCNAME = 5;
/*      */   protected static final int DT_XPATH = 6;
/*      */   protected static final int DT_XPATH1 = 7;
/*      */   protected static final int DT_LANGUAGE = 8;
/*      */   protected static final int DT_COUNT = 9;
/*      */   protected static final int DT_BLOCK = -1;
/*      */   protected static final int DT_BLOCK1 = -2;
/*      */   protected static final int DT_FINAL = -3;
/*      */   protected static final int DT_FINAL1 = -4;
/*      */   protected static final int DT_FINAL2 = -5;
/*      */   protected static final int DT_FORM = -6;
/*  169 */   private static final XSSimpleType[] fExtraDVs = new XSSimpleType[9]; protected static final int DT_MAXOCCURS = -7; protected static final int DT_MAXOCCURS1 = -8;
/*      */   
/*      */   static {
/*  172 */     SchemaGrammar grammar = SchemaGrammar.SG_SchemaNS;
/*      */     
/*  174 */     fExtraDVs[0] = (XSSimpleType)grammar.getGlobalTypeDecl("anyURI");
/*      */     
/*  176 */     fExtraDVs[1] = (XSSimpleType)grammar.getGlobalTypeDecl("ID");
/*      */     
/*  178 */     fExtraDVs[2] = (XSSimpleType)grammar.getGlobalTypeDecl("QName");
/*      */     
/*  180 */     fExtraDVs[3] = (XSSimpleType)grammar.getGlobalTypeDecl("string");
/*      */     
/*  182 */     fExtraDVs[4] = (XSSimpleType)grammar.getGlobalTypeDecl("token");
/*      */     
/*  184 */     fExtraDVs[5] = (XSSimpleType)grammar.getGlobalTypeDecl("NCName");
/*      */     
/*  186 */     fExtraDVs[6] = fExtraDVs[3];
/*      */     
/*  188 */     fExtraDVs[6] = fExtraDVs[3];
/*      */     
/*  190 */     fExtraDVs[8] = (XSSimpleType)grammar.getGlobalTypeDecl("language");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  213 */     int attCount = 0;
/*  214 */     int ATT_ABSTRACT_D = attCount++;
/*  215 */     int ATT_ATTRIBUTE_FD_D = attCount++;
/*  216 */     int ATT_BASE_R = attCount++;
/*  217 */     int ATT_BASE_N = attCount++;
/*  218 */     int ATT_BLOCK_N = attCount++;
/*  219 */     int ATT_BLOCK1_N = attCount++;
/*  220 */     int ATT_BLOCK_D_D = attCount++;
/*  221 */     int ATT_DEFAULT_N = attCount++;
/*  222 */     int ATT_ELEMENT_FD_D = attCount++;
/*  223 */     int ATT_FINAL_N = attCount++;
/*  224 */     int ATT_FINAL1_N = attCount++;
/*  225 */     int ATT_FINAL_D_D = attCount++;
/*  226 */     int ATT_FIXED_N = attCount++;
/*  227 */     int ATT_FIXED_D = attCount++;
/*  228 */     int ATT_FORM_N = attCount++;
/*  229 */     int ATT_ID_N = attCount++;
/*  230 */     int ATT_ITEMTYPE_N = attCount++;
/*  231 */     int ATT_MAXOCCURS_D = attCount++;
/*  232 */     int ATT_MAXOCCURS1_D = attCount++;
/*  233 */     int ATT_MEMBER_T_N = attCount++;
/*  234 */     int ATT_MINOCCURS_D = attCount++;
/*  235 */     int ATT_MINOCCURS1_D = attCount++;
/*  236 */     int ATT_MIXED_D = attCount++;
/*  237 */     int ATT_MIXED_N = attCount++;
/*  238 */     int ATT_NAME_R = attCount++;
/*  239 */     int ATT_NAMESPACE_D = attCount++;
/*  240 */     int ATT_NAMESPACE_N = attCount++;
/*  241 */     int ATT_NILLABLE_D = attCount++;
/*  242 */     int ATT_PROCESS_C_D = attCount++;
/*  243 */     int ATT_PUBLIC_R = attCount++;
/*  244 */     int ATT_REF_R = attCount++;
/*  245 */     int ATT_REFER_R = attCount++;
/*  246 */     int ATT_SCHEMA_L_R = attCount++;
/*  247 */     int ATT_SCHEMA_L_N = attCount++;
/*  248 */     int ATT_SOURCE_N = attCount++;
/*  249 */     int ATT_SUBSTITUTION_G_N = attCount++;
/*  250 */     int ATT_SYSTEM_N = attCount++;
/*  251 */     int ATT_TARGET_N_N = attCount++;
/*  252 */     int ATT_TYPE_N = attCount++;
/*  253 */     int ATT_USE_D = attCount++;
/*  254 */     int ATT_VALUE_NNI_N = attCount++;
/*  255 */     int ATT_VALUE_PI_N = attCount++;
/*  256 */     int ATT_VALUE_STR_N = attCount++;
/*  257 */     int ATT_VALUE_WS_N = attCount++;
/*  258 */     int ATT_VERSION_N = attCount++;
/*  259 */     int ATT_XML_LANG = attCount++;
/*  260 */     int ATT_XPATH_R = attCount++;
/*  261 */     int ATT_XPATH1_R = attCount++;
/*      */ 
/*      */     
/*  264 */     OneAttr[] allAttrs = new OneAttr[attCount];
/*  265 */     allAttrs[ATT_ABSTRACT_D] = new OneAttr(SchemaSymbols.ATT_ABSTRACT, -15, ATTIDX_ABSTRACT, Boolean.FALSE);
/*      */ 
/*      */ 
/*      */     
/*  269 */     allAttrs[ATT_ATTRIBUTE_FD_D] = new OneAttr(SchemaSymbols.ATT_ATTRIBUTEFORMDEFAULT, -6, ATTIDX_AFORMDEFAULT, INT_UNQUALIFIED);
/*      */ 
/*      */ 
/*      */     
/*  273 */     allAttrs[ATT_BASE_R] = new OneAttr(SchemaSymbols.ATT_BASE, 2, ATTIDX_BASE, null);
/*      */ 
/*      */ 
/*      */     
/*  277 */     allAttrs[ATT_BASE_N] = new OneAttr(SchemaSymbols.ATT_BASE, 2, ATTIDX_BASE, null);
/*      */ 
/*      */ 
/*      */     
/*  281 */     allAttrs[ATT_BLOCK_N] = new OneAttr(SchemaSymbols.ATT_BLOCK, -1, ATTIDX_BLOCK, null);
/*      */ 
/*      */ 
/*      */     
/*  285 */     allAttrs[ATT_BLOCK1_N] = new OneAttr(SchemaSymbols.ATT_BLOCK, -2, ATTIDX_BLOCK, null);
/*      */ 
/*      */ 
/*      */     
/*  289 */     allAttrs[ATT_BLOCK_D_D] = new OneAttr(SchemaSymbols.ATT_BLOCKDEFAULT, -1, ATTIDX_BLOCKDEFAULT, INT_EMPTY_SET);
/*      */ 
/*      */ 
/*      */     
/*  293 */     allAttrs[ATT_DEFAULT_N] = new OneAttr(SchemaSymbols.ATT_DEFAULT, 3, ATTIDX_DEFAULT, null);
/*      */ 
/*      */ 
/*      */     
/*  297 */     allAttrs[ATT_ELEMENT_FD_D] = new OneAttr(SchemaSymbols.ATT_ELEMENTFORMDEFAULT, -6, ATTIDX_EFORMDEFAULT, INT_UNQUALIFIED);
/*      */ 
/*      */ 
/*      */     
/*  301 */     allAttrs[ATT_FINAL_N] = new OneAttr(SchemaSymbols.ATT_FINAL, -3, ATTIDX_FINAL, null);
/*      */ 
/*      */ 
/*      */     
/*  305 */     allAttrs[ATT_FINAL1_N] = new OneAttr(SchemaSymbols.ATT_FINAL, -4, ATTIDX_FINAL, null);
/*      */ 
/*      */ 
/*      */     
/*  309 */     allAttrs[ATT_FINAL_D_D] = new OneAttr(SchemaSymbols.ATT_FINALDEFAULT, -5, ATTIDX_FINALDEFAULT, INT_EMPTY_SET);
/*      */ 
/*      */ 
/*      */     
/*  313 */     allAttrs[ATT_FIXED_N] = new OneAttr(SchemaSymbols.ATT_FIXED, 3, ATTIDX_FIXED, null);
/*      */ 
/*      */ 
/*      */     
/*  317 */     allAttrs[ATT_FIXED_D] = new OneAttr(SchemaSymbols.ATT_FIXED, -15, ATTIDX_FIXED, Boolean.FALSE);
/*      */ 
/*      */ 
/*      */     
/*  321 */     allAttrs[ATT_FORM_N] = new OneAttr(SchemaSymbols.ATT_FORM, -6, ATTIDX_FORM, null);
/*      */ 
/*      */ 
/*      */     
/*  325 */     allAttrs[ATT_ID_N] = new OneAttr(SchemaSymbols.ATT_ID, 1, ATTIDX_ID, null);
/*      */ 
/*      */ 
/*      */     
/*  329 */     allAttrs[ATT_ITEMTYPE_N] = new OneAttr(SchemaSymbols.ATT_ITEMTYPE, 2, ATTIDX_ITEMTYPE, null);
/*      */ 
/*      */ 
/*      */     
/*  333 */     allAttrs[ATT_MAXOCCURS_D] = new OneAttr(SchemaSymbols.ATT_MAXOCCURS, -7, ATTIDX_MAXOCCURS, fXIntPool
/*      */ 
/*      */         
/*  336 */         .getXInt(1));
/*  337 */     allAttrs[ATT_MAXOCCURS1_D] = new OneAttr(SchemaSymbols.ATT_MAXOCCURS, -8, ATTIDX_MAXOCCURS, fXIntPool
/*      */ 
/*      */         
/*  340 */         .getXInt(1));
/*  341 */     allAttrs[ATT_MEMBER_T_N] = new OneAttr(SchemaSymbols.ATT_MEMBERTYPES, -9, ATTIDX_MEMBERTYPES, null);
/*      */ 
/*      */ 
/*      */     
/*  345 */     allAttrs[ATT_MINOCCURS_D] = new OneAttr(SchemaSymbols.ATT_MINOCCURS, -16, ATTIDX_MINOCCURS, fXIntPool
/*      */ 
/*      */         
/*  348 */         .getXInt(1));
/*  349 */     allAttrs[ATT_MINOCCURS1_D] = new OneAttr(SchemaSymbols.ATT_MINOCCURS, -10, ATTIDX_MINOCCURS, fXIntPool
/*      */ 
/*      */         
/*  352 */         .getXInt(1));
/*  353 */     allAttrs[ATT_MIXED_D] = new OneAttr(SchemaSymbols.ATT_MIXED, -15, ATTIDX_MIXED, Boolean.FALSE);
/*      */ 
/*      */ 
/*      */     
/*  357 */     allAttrs[ATT_MIXED_N] = new OneAttr(SchemaSymbols.ATT_MIXED, -15, ATTIDX_MIXED, null);
/*      */ 
/*      */ 
/*      */     
/*  361 */     allAttrs[ATT_NAME_R] = new OneAttr(SchemaSymbols.ATT_NAME, 5, ATTIDX_NAME, null);
/*      */ 
/*      */ 
/*      */     
/*  365 */     allAttrs[ATT_NAMESPACE_D] = new OneAttr(SchemaSymbols.ATT_NAMESPACE, -11, ATTIDX_NAMESPACE, INT_ANY_ANY);
/*      */ 
/*      */ 
/*      */     
/*  369 */     allAttrs[ATT_NAMESPACE_N] = new OneAttr(SchemaSymbols.ATT_NAMESPACE, 0, ATTIDX_NAMESPACE, null);
/*      */ 
/*      */ 
/*      */     
/*  373 */     allAttrs[ATT_NILLABLE_D] = new OneAttr(SchemaSymbols.ATT_NILLABLE, -15, ATTIDX_NILLABLE, Boolean.FALSE);
/*      */ 
/*      */ 
/*      */     
/*  377 */     allAttrs[ATT_PROCESS_C_D] = new OneAttr(SchemaSymbols.ATT_PROCESSCONTENTS, -12, ATTIDX_PROCESSCONTENTS, INT_ANY_STRICT);
/*      */ 
/*      */ 
/*      */     
/*  381 */     allAttrs[ATT_PUBLIC_R] = new OneAttr(SchemaSymbols.ATT_PUBLIC, 4, ATTIDX_PUBLIC, null);
/*      */ 
/*      */ 
/*      */     
/*  385 */     allAttrs[ATT_REF_R] = new OneAttr(SchemaSymbols.ATT_REF, 2, ATTIDX_REF, null);
/*      */ 
/*      */ 
/*      */     
/*  389 */     allAttrs[ATT_REFER_R] = new OneAttr(SchemaSymbols.ATT_REFER, 2, ATTIDX_REFER, null);
/*      */ 
/*      */ 
/*      */     
/*  393 */     allAttrs[ATT_SCHEMA_L_R] = new OneAttr(SchemaSymbols.ATT_SCHEMALOCATION, 0, ATTIDX_SCHEMALOCATION, null);
/*      */ 
/*      */ 
/*      */     
/*  397 */     allAttrs[ATT_SCHEMA_L_N] = new OneAttr(SchemaSymbols.ATT_SCHEMALOCATION, 0, ATTIDX_SCHEMALOCATION, null);
/*      */ 
/*      */ 
/*      */     
/*  401 */     allAttrs[ATT_SOURCE_N] = new OneAttr(SchemaSymbols.ATT_SOURCE, 0, ATTIDX_SOURCE, null);
/*      */ 
/*      */ 
/*      */     
/*  405 */     allAttrs[ATT_SUBSTITUTION_G_N] = new OneAttr(SchemaSymbols.ATT_SUBSTITUTIONGROUP, 2, ATTIDX_SUBSGROUP, null);
/*      */ 
/*      */ 
/*      */     
/*  409 */     allAttrs[ATT_SYSTEM_N] = new OneAttr(SchemaSymbols.ATT_SYSTEM, 0, ATTIDX_SYSTEM, null);
/*      */ 
/*      */ 
/*      */     
/*  413 */     allAttrs[ATT_TARGET_N_N] = new OneAttr(SchemaSymbols.ATT_TARGETNAMESPACE, 0, ATTIDX_TARGETNAMESPACE, null);
/*      */ 
/*      */ 
/*      */     
/*  417 */     allAttrs[ATT_TYPE_N] = new OneAttr(SchemaSymbols.ATT_TYPE, 2, ATTIDX_TYPE, null);
/*      */ 
/*      */ 
/*      */     
/*  421 */     allAttrs[ATT_USE_D] = new OneAttr(SchemaSymbols.ATT_USE, -13, ATTIDX_USE, INT_USE_OPTIONAL);
/*      */ 
/*      */ 
/*      */     
/*  425 */     allAttrs[ATT_VALUE_NNI_N] = new OneAttr(SchemaSymbols.ATT_VALUE, -16, ATTIDX_VALUE, null);
/*      */ 
/*      */ 
/*      */     
/*  429 */     allAttrs[ATT_VALUE_PI_N] = new OneAttr(SchemaSymbols.ATT_VALUE, -17, ATTIDX_VALUE, null);
/*      */ 
/*      */ 
/*      */     
/*  433 */     allAttrs[ATT_VALUE_STR_N] = new OneAttr(SchemaSymbols.ATT_VALUE, 3, ATTIDX_VALUE, null);
/*      */ 
/*      */ 
/*      */     
/*  437 */     allAttrs[ATT_VALUE_WS_N] = new OneAttr(SchemaSymbols.ATT_VALUE, -14, ATTIDX_VALUE, null);
/*      */ 
/*      */ 
/*      */     
/*  441 */     allAttrs[ATT_VERSION_N] = new OneAttr(SchemaSymbols.ATT_VERSION, 4, ATTIDX_VERSION, null);
/*      */ 
/*      */ 
/*      */     
/*  445 */     allAttrs[ATT_XML_LANG] = new OneAttr(SchemaSymbols.ATT_XML_LANG, 8, ATTIDX_XML_LANG, null);
/*      */ 
/*      */ 
/*      */     
/*  449 */     allAttrs[ATT_XPATH_R] = new OneAttr(SchemaSymbols.ATT_XPATH, 6, ATTIDX_XPATH, null);
/*      */ 
/*      */ 
/*      */     
/*  453 */     allAttrs[ATT_XPATH1_R] = new OneAttr(SchemaSymbols.ATT_XPATH, 7, ATTIDX_XPATH, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  462 */     Container attrList = Container.getContainer(5);
/*      */     
/*  464 */     attrList.put(SchemaSymbols.ATT_DEFAULT, allAttrs[ATT_DEFAULT_N]);
/*      */     
/*  466 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_N]);
/*      */     
/*  468 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  470 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*      */     
/*  472 */     attrList.put(SchemaSymbols.ATT_TYPE, allAttrs[ATT_TYPE_N]);
/*  473 */     fEleAttrsMapG.put(SchemaSymbols.ELT_ATTRIBUTE, attrList);
/*      */ 
/*      */     
/*  476 */     attrList = Container.getContainer(7);
/*      */     
/*  478 */     attrList.put(SchemaSymbols.ATT_DEFAULT, allAttrs[ATT_DEFAULT_N]);
/*      */     
/*  480 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_N]);
/*      */     
/*  482 */     attrList.put(SchemaSymbols.ATT_FORM, allAttrs[ATT_FORM_N]);
/*      */     
/*  484 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  486 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*      */     
/*  488 */     attrList.put(SchemaSymbols.ATT_TYPE, allAttrs[ATT_TYPE_N]);
/*      */     
/*  490 */     attrList.put(SchemaSymbols.ATT_USE, allAttrs[ATT_USE_D]);
/*  491 */     fEleAttrsMapL.put("attribute_n", attrList);
/*      */ 
/*      */     
/*  494 */     attrList = Container.getContainer(5);
/*      */     
/*  496 */     attrList.put(SchemaSymbols.ATT_DEFAULT, allAttrs[ATT_DEFAULT_N]);
/*      */     
/*  498 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_N]);
/*      */     
/*  500 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  502 */     attrList.put(SchemaSymbols.ATT_REF, allAttrs[ATT_REF_R]);
/*      */     
/*  504 */     attrList.put(SchemaSymbols.ATT_USE, allAttrs[ATT_USE_D]);
/*  505 */     fEleAttrsMapL.put("attribute_r", attrList);
/*      */ 
/*      */     
/*  508 */     attrList = Container.getContainer(10);
/*      */     
/*  510 */     attrList.put(SchemaSymbols.ATT_ABSTRACT, allAttrs[ATT_ABSTRACT_D]);
/*      */     
/*  512 */     attrList.put(SchemaSymbols.ATT_BLOCK, allAttrs[ATT_BLOCK_N]);
/*      */     
/*  514 */     attrList.put(SchemaSymbols.ATT_DEFAULT, allAttrs[ATT_DEFAULT_N]);
/*      */     
/*  516 */     attrList.put(SchemaSymbols.ATT_FINAL, allAttrs[ATT_FINAL_N]);
/*      */     
/*  518 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_N]);
/*      */     
/*  520 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  522 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*      */     
/*  524 */     attrList.put(SchemaSymbols.ATT_NILLABLE, allAttrs[ATT_NILLABLE_D]);
/*      */     
/*  526 */     attrList.put(SchemaSymbols.ATT_SUBSTITUTIONGROUP, allAttrs[ATT_SUBSTITUTION_G_N]);
/*      */     
/*  528 */     attrList.put(SchemaSymbols.ATT_TYPE, allAttrs[ATT_TYPE_N]);
/*  529 */     fEleAttrsMapG.put(SchemaSymbols.ELT_ELEMENT, attrList);
/*      */ 
/*      */     
/*  532 */     attrList = Container.getContainer(10);
/*      */     
/*  534 */     attrList.put(SchemaSymbols.ATT_BLOCK, allAttrs[ATT_BLOCK_N]);
/*      */     
/*  536 */     attrList.put(SchemaSymbols.ATT_DEFAULT, allAttrs[ATT_DEFAULT_N]);
/*      */     
/*  538 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_N]);
/*      */     
/*  540 */     attrList.put(SchemaSymbols.ATT_FORM, allAttrs[ATT_FORM_N]);
/*      */     
/*  542 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  544 */     attrList.put(SchemaSymbols.ATT_MAXOCCURS, allAttrs[ATT_MAXOCCURS_D]);
/*      */     
/*  546 */     attrList.put(SchemaSymbols.ATT_MINOCCURS, allAttrs[ATT_MINOCCURS_D]);
/*      */     
/*  548 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*      */     
/*  550 */     attrList.put(SchemaSymbols.ATT_NILLABLE, allAttrs[ATT_NILLABLE_D]);
/*      */     
/*  552 */     attrList.put(SchemaSymbols.ATT_TYPE, allAttrs[ATT_TYPE_N]);
/*  553 */     fEleAttrsMapL.put("element_n", attrList);
/*      */ 
/*      */     
/*  556 */     attrList = Container.getContainer(4);
/*      */     
/*  558 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  560 */     attrList.put(SchemaSymbols.ATT_MAXOCCURS, allAttrs[ATT_MAXOCCURS_D]);
/*      */     
/*  562 */     attrList.put(SchemaSymbols.ATT_MINOCCURS, allAttrs[ATT_MINOCCURS_D]);
/*      */     
/*  564 */     attrList.put(SchemaSymbols.ATT_REF, allAttrs[ATT_REF_R]);
/*  565 */     fEleAttrsMapL.put("element_r", attrList);
/*      */ 
/*      */     
/*  568 */     attrList = Container.getContainer(6);
/*      */     
/*  570 */     attrList.put(SchemaSymbols.ATT_ABSTRACT, allAttrs[ATT_ABSTRACT_D]);
/*      */     
/*  572 */     attrList.put(SchemaSymbols.ATT_BLOCK, allAttrs[ATT_BLOCK1_N]);
/*      */     
/*  574 */     attrList.put(SchemaSymbols.ATT_FINAL, allAttrs[ATT_FINAL_N]);
/*      */     
/*  576 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  578 */     attrList.put(SchemaSymbols.ATT_MIXED, allAttrs[ATT_MIXED_D]);
/*      */     
/*  580 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*  581 */     fEleAttrsMapG.put(SchemaSymbols.ELT_COMPLEXTYPE, attrList);
/*      */ 
/*      */     
/*  584 */     attrList = Container.getContainer(4);
/*      */     
/*  586 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  588 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*      */     
/*  590 */     attrList.put(SchemaSymbols.ATT_PUBLIC, allAttrs[ATT_PUBLIC_R]);
/*      */     
/*  592 */     attrList.put(SchemaSymbols.ATT_SYSTEM, allAttrs[ATT_SYSTEM_N]);
/*  593 */     fEleAttrsMapG.put(SchemaSymbols.ELT_NOTATION, attrList);
/*      */ 
/*      */ 
/*      */     
/*  597 */     attrList = Container.getContainer(2);
/*      */     
/*  599 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  601 */     attrList.put(SchemaSymbols.ATT_MIXED, allAttrs[ATT_MIXED_D]);
/*  602 */     fEleAttrsMapL.put(SchemaSymbols.ELT_COMPLEXTYPE, attrList);
/*      */ 
/*      */     
/*  605 */     attrList = Container.getContainer(1);
/*      */     
/*  607 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*  608 */     fEleAttrsMapL.put(SchemaSymbols.ELT_SIMPLECONTENT, attrList);
/*      */ 
/*      */     
/*  611 */     attrList = Container.getContainer(2);
/*      */     
/*  613 */     attrList.put(SchemaSymbols.ATT_BASE, allAttrs[ATT_BASE_N]);
/*      */     
/*  615 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*  616 */     fEleAttrsMapL.put(SchemaSymbols.ELT_RESTRICTION, attrList);
/*      */ 
/*      */     
/*  619 */     attrList = Container.getContainer(2);
/*      */     
/*  621 */     attrList.put(SchemaSymbols.ATT_BASE, allAttrs[ATT_BASE_R]);
/*      */     
/*  623 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*  624 */     fEleAttrsMapL.put(SchemaSymbols.ELT_EXTENSION, attrList);
/*      */ 
/*      */     
/*  627 */     attrList = Container.getContainer(2);
/*      */     
/*  629 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  631 */     attrList.put(SchemaSymbols.ATT_REF, allAttrs[ATT_REF_R]);
/*  632 */     fEleAttrsMapL.put(SchemaSymbols.ELT_ATTRIBUTEGROUP, attrList);
/*      */ 
/*      */     
/*  635 */     attrList = Container.getContainer(3);
/*      */     
/*  637 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  639 */     attrList.put(SchemaSymbols.ATT_NAMESPACE, allAttrs[ATT_NAMESPACE_D]);
/*      */     
/*  641 */     attrList.put(SchemaSymbols.ATT_PROCESSCONTENTS, allAttrs[ATT_PROCESS_C_D]);
/*  642 */     fEleAttrsMapL.put(SchemaSymbols.ELT_ANYATTRIBUTE, attrList);
/*      */ 
/*      */     
/*  645 */     attrList = Container.getContainer(2);
/*      */     
/*  647 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  649 */     attrList.put(SchemaSymbols.ATT_MIXED, allAttrs[ATT_MIXED_N]);
/*  650 */     fEleAttrsMapL.put(SchemaSymbols.ELT_COMPLEXCONTENT, attrList);
/*      */ 
/*      */     
/*  653 */     attrList = Container.getContainer(2);
/*      */     
/*  655 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  657 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*  658 */     fEleAttrsMapG.put(SchemaSymbols.ELT_ATTRIBUTEGROUP, attrList);
/*      */ 
/*      */     
/*  661 */     attrList = Container.getContainer(2);
/*      */     
/*  663 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  665 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*  666 */     fEleAttrsMapG.put(SchemaSymbols.ELT_GROUP, attrList);
/*      */ 
/*      */     
/*  669 */     attrList = Container.getContainer(4);
/*      */     
/*  671 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  673 */     attrList.put(SchemaSymbols.ATT_MAXOCCURS, allAttrs[ATT_MAXOCCURS_D]);
/*      */     
/*  675 */     attrList.put(SchemaSymbols.ATT_MINOCCURS, allAttrs[ATT_MINOCCURS_D]);
/*      */     
/*  677 */     attrList.put(SchemaSymbols.ATT_REF, allAttrs[ATT_REF_R]);
/*  678 */     fEleAttrsMapL.put(SchemaSymbols.ELT_GROUP, attrList);
/*      */ 
/*      */     
/*  681 */     attrList = Container.getContainer(3);
/*      */     
/*  683 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  685 */     attrList.put(SchemaSymbols.ATT_MAXOCCURS, allAttrs[ATT_MAXOCCURS1_D]);
/*      */     
/*  687 */     attrList.put(SchemaSymbols.ATT_MINOCCURS, allAttrs[ATT_MINOCCURS1_D]);
/*  688 */     fEleAttrsMapL.put(SchemaSymbols.ELT_ALL, attrList);
/*      */ 
/*      */     
/*  691 */     attrList = Container.getContainer(3);
/*      */     
/*  693 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  695 */     attrList.put(SchemaSymbols.ATT_MAXOCCURS, allAttrs[ATT_MAXOCCURS_D]);
/*      */     
/*  697 */     attrList.put(SchemaSymbols.ATT_MINOCCURS, allAttrs[ATT_MINOCCURS_D]);
/*  698 */     fEleAttrsMapL.put(SchemaSymbols.ELT_CHOICE, attrList);
/*      */     
/*  700 */     fEleAttrsMapL.put(SchemaSymbols.ELT_SEQUENCE, attrList);
/*      */ 
/*      */     
/*  703 */     attrList = Container.getContainer(5);
/*      */     
/*  705 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  707 */     attrList.put(SchemaSymbols.ATT_MAXOCCURS, allAttrs[ATT_MAXOCCURS_D]);
/*      */     
/*  709 */     attrList.put(SchemaSymbols.ATT_MINOCCURS, allAttrs[ATT_MINOCCURS_D]);
/*      */     
/*  711 */     attrList.put(SchemaSymbols.ATT_NAMESPACE, allAttrs[ATT_NAMESPACE_D]);
/*      */     
/*  713 */     attrList.put(SchemaSymbols.ATT_PROCESSCONTENTS, allAttrs[ATT_PROCESS_C_D]);
/*  714 */     fEleAttrsMapL.put(SchemaSymbols.ELT_ANY, attrList);
/*      */ 
/*      */     
/*  717 */     attrList = Container.getContainer(2);
/*      */     
/*  719 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  721 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*  722 */     fEleAttrsMapL.put(SchemaSymbols.ELT_UNIQUE, attrList);
/*      */     
/*  724 */     fEleAttrsMapL.put(SchemaSymbols.ELT_KEY, attrList);
/*      */ 
/*      */     
/*  727 */     attrList = Container.getContainer(3);
/*      */     
/*  729 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  731 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*      */     
/*  733 */     attrList.put(SchemaSymbols.ATT_REFER, allAttrs[ATT_REFER_R]);
/*  734 */     fEleAttrsMapL.put(SchemaSymbols.ELT_KEYREF, attrList);
/*      */ 
/*      */     
/*  737 */     attrList = Container.getContainer(2);
/*      */     
/*  739 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  741 */     attrList.put(SchemaSymbols.ATT_XPATH, allAttrs[ATT_XPATH_R]);
/*  742 */     fEleAttrsMapL.put(SchemaSymbols.ELT_SELECTOR, attrList);
/*      */ 
/*      */     
/*  745 */     attrList = Container.getContainer(2);
/*      */     
/*  747 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  749 */     attrList.put(SchemaSymbols.ATT_XPATH, allAttrs[ATT_XPATH1_R]);
/*  750 */     fEleAttrsMapL.put(SchemaSymbols.ELT_FIELD, attrList);
/*      */ 
/*      */     
/*  753 */     attrList = Container.getContainer(1);
/*      */     
/*  755 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*  756 */     fEleAttrsMapG.put(SchemaSymbols.ELT_ANNOTATION, attrList);
/*      */     
/*  758 */     fEleAttrsMapL.put(SchemaSymbols.ELT_ANNOTATION, attrList);
/*      */ 
/*      */     
/*  761 */     attrList = Container.getContainer(1);
/*      */     
/*  763 */     attrList.put(SchemaSymbols.ATT_SOURCE, allAttrs[ATT_SOURCE_N]);
/*  764 */     fEleAttrsMapG.put(SchemaSymbols.ELT_APPINFO, attrList);
/*  765 */     fEleAttrsMapL.put(SchemaSymbols.ELT_APPINFO, attrList);
/*      */ 
/*      */     
/*  768 */     attrList = Container.getContainer(2);
/*      */     
/*  770 */     attrList.put(SchemaSymbols.ATT_SOURCE, allAttrs[ATT_SOURCE_N]);
/*      */     
/*  772 */     attrList.put(SchemaSymbols.ATT_XML_LANG, allAttrs[ATT_XML_LANG]);
/*  773 */     fEleAttrsMapG.put(SchemaSymbols.ELT_DOCUMENTATION, attrList);
/*  774 */     fEleAttrsMapL.put(SchemaSymbols.ELT_DOCUMENTATION, attrList);
/*      */ 
/*      */     
/*  777 */     attrList = Container.getContainer(3);
/*      */     
/*  779 */     attrList.put(SchemaSymbols.ATT_FINAL, allAttrs[ATT_FINAL1_N]);
/*      */     
/*  781 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  783 */     attrList.put(SchemaSymbols.ATT_NAME, allAttrs[ATT_NAME_R]);
/*  784 */     fEleAttrsMapG.put(SchemaSymbols.ELT_SIMPLETYPE, attrList);
/*      */ 
/*      */     
/*  787 */     attrList = Container.getContainer(2);
/*      */     
/*  789 */     attrList.put(SchemaSymbols.ATT_FINAL, allAttrs[ATT_FINAL1_N]);
/*      */     
/*  791 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*  792 */     fEleAttrsMapL.put(SchemaSymbols.ELT_SIMPLETYPE, attrList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  798 */     attrList = Container.getContainer(2);
/*      */     
/*  800 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  802 */     attrList.put(SchemaSymbols.ATT_ITEMTYPE, allAttrs[ATT_ITEMTYPE_N]);
/*  803 */     fEleAttrsMapL.put(SchemaSymbols.ELT_LIST, attrList);
/*      */ 
/*      */     
/*  806 */     attrList = Container.getContainer(2);
/*      */     
/*  808 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  810 */     attrList.put(SchemaSymbols.ATT_MEMBERTYPES, allAttrs[ATT_MEMBER_T_N]);
/*  811 */     fEleAttrsMapL.put(SchemaSymbols.ELT_UNION, attrList);
/*      */ 
/*      */     
/*  814 */     attrList = Container.getContainer(8);
/*      */     
/*  816 */     attrList.put(SchemaSymbols.ATT_ATTRIBUTEFORMDEFAULT, allAttrs[ATT_ATTRIBUTE_FD_D]);
/*      */     
/*  818 */     attrList.put(SchemaSymbols.ATT_BLOCKDEFAULT, allAttrs[ATT_BLOCK_D_D]);
/*      */     
/*  820 */     attrList.put(SchemaSymbols.ATT_ELEMENTFORMDEFAULT, allAttrs[ATT_ELEMENT_FD_D]);
/*      */     
/*  822 */     attrList.put(SchemaSymbols.ATT_FINALDEFAULT, allAttrs[ATT_FINAL_D_D]);
/*      */     
/*  824 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  826 */     attrList.put(SchemaSymbols.ATT_TARGETNAMESPACE, allAttrs[ATT_TARGET_N_N]);
/*      */     
/*  828 */     attrList.put(SchemaSymbols.ATT_VERSION, allAttrs[ATT_VERSION_N]);
/*      */     
/*  830 */     attrList.put(SchemaSymbols.ATT_XML_LANG, allAttrs[ATT_XML_LANG]);
/*  831 */     fEleAttrsMapG.put(SchemaSymbols.ELT_SCHEMA, attrList);
/*      */ 
/*      */     
/*  834 */     attrList = Container.getContainer(2);
/*      */     
/*  836 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  838 */     attrList.put(SchemaSymbols.ATT_SCHEMALOCATION, allAttrs[ATT_SCHEMA_L_R]);
/*  839 */     fEleAttrsMapG.put(SchemaSymbols.ELT_INCLUDE, attrList);
/*      */     
/*  841 */     fEleAttrsMapG.put(SchemaSymbols.ELT_REDEFINE, attrList);
/*      */ 
/*      */     
/*  844 */     attrList = Container.getContainer(3);
/*      */     
/*  846 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  848 */     attrList.put(SchemaSymbols.ATT_NAMESPACE, allAttrs[ATT_NAMESPACE_N]);
/*      */     
/*  850 */     attrList.put(SchemaSymbols.ATT_SCHEMALOCATION, allAttrs[ATT_SCHEMA_L_N]);
/*  851 */     fEleAttrsMapG.put(SchemaSymbols.ELT_IMPORT, attrList);
/*      */ 
/*      */     
/*  854 */     attrList = Container.getContainer(3);
/*      */     
/*  856 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  858 */     attrList.put(SchemaSymbols.ATT_VALUE, allAttrs[ATT_VALUE_NNI_N]);
/*      */     
/*  860 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_D]);
/*  861 */     fEleAttrsMapL.put(SchemaSymbols.ELT_LENGTH, attrList);
/*      */     
/*  863 */     fEleAttrsMapL.put(SchemaSymbols.ELT_MINLENGTH, attrList);
/*      */     
/*  865 */     fEleAttrsMapL.put(SchemaSymbols.ELT_MAXLENGTH, attrList);
/*      */     
/*  867 */     fEleAttrsMapL.put(SchemaSymbols.ELT_FRACTIONDIGITS, attrList);
/*      */ 
/*      */     
/*  870 */     attrList = Container.getContainer(3);
/*      */     
/*  872 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  874 */     attrList.put(SchemaSymbols.ATT_VALUE, allAttrs[ATT_VALUE_PI_N]);
/*      */     
/*  876 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_D]);
/*  877 */     fEleAttrsMapL.put(SchemaSymbols.ELT_TOTALDIGITS, attrList);
/*      */ 
/*      */     
/*  880 */     attrList = Container.getContainer(2);
/*      */     
/*  882 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  884 */     attrList.put(SchemaSymbols.ATT_VALUE, allAttrs[ATT_VALUE_STR_N]);
/*  885 */     fEleAttrsMapL.put(SchemaSymbols.ELT_PATTERN, attrList);
/*      */ 
/*      */     
/*  888 */     attrList = Container.getContainer(2);
/*      */     
/*  890 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  892 */     attrList.put(SchemaSymbols.ATT_VALUE, allAttrs[ATT_VALUE_STR_N]);
/*  893 */     fEleAttrsMapL.put(SchemaSymbols.ELT_ENUMERATION, attrList);
/*      */ 
/*      */     
/*  896 */     attrList = Container.getContainer(3);
/*      */     
/*  898 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  900 */     attrList.put(SchemaSymbols.ATT_VALUE, allAttrs[ATT_VALUE_WS_N]);
/*      */     
/*  902 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_D]);
/*  903 */     fEleAttrsMapL.put(SchemaSymbols.ELT_WHITESPACE, attrList);
/*      */ 
/*      */     
/*  906 */     attrList = Container.getContainer(3);
/*      */     
/*  908 */     attrList.put(SchemaSymbols.ATT_ID, allAttrs[ATT_ID_N]);
/*      */     
/*  910 */     attrList.put(SchemaSymbols.ATT_VALUE, allAttrs[ATT_VALUE_STR_N]);
/*      */     
/*  912 */     attrList.put(SchemaSymbols.ATT_FIXED, allAttrs[ATT_FIXED_D]);
/*  913 */     fEleAttrsMapL.put(SchemaSymbols.ELT_MAXINCLUSIVE, attrList);
/*      */     
/*  915 */     fEleAttrsMapL.put(SchemaSymbols.ELT_MAXEXCLUSIVE, attrList);
/*      */     
/*  917 */     fEleAttrsMapL.put(SchemaSymbols.ELT_MININCLUSIVE, attrList);
/*      */     
/*  919 */     fEleAttrsMapL.put(SchemaSymbols.ELT_MINEXCLUSIVE, attrList);
/*      */   }
/*      */   protected static final int DT_MEMBERTYPES = -9; protected static final int DT_MINOCCURS1 = -10; protected static final int DT_NAMESPACE = -11;
/*      */   protected static final int DT_PROCESSCONTENTS = -12;
/*  923 */   protected XSDHandler fSchemaHandler = null; protected static final int DT_USE = -13; protected static final int DT_WHITESPACE = -14; protected static final int DT_BOOLEAN = -15;
/*      */   protected static final int DT_NONNEGINT = -16;
/*      */   protected static final int DT_POSINT = -17;
/*  926 */   protected SymbolTable fSymbolTable = null;
/*      */ 
/*      */   
/*  929 */   protected Map fNonSchemaAttrs = new HashMap<>();
/*      */ 
/*      */   
/*  932 */   protected Vector fNamespaceList = new Vector();
/*      */ 
/*      */   
/*  935 */   protected boolean[] fSeen = new boolean[ATTIDX_COUNT];
/*  936 */   private static boolean[] fSeenTemp = new boolean[ATTIDX_COUNT];
/*      */   
/*      */   static final int INIT_POOL_SIZE = 10;
/*      */   
/*      */   static final int INC_POOL_SIZE = 10;
/*      */   Object[][] fArrayPool;
/*      */   
/*      */   public void reset(SymbolTable symbolTable) {
/*  944 */     this.fSymbolTable = symbolTable;
/*  945 */     this.fNonSchemaAttrs.clear();
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
/*      */   public Object[] checkAttributes(Element element, boolean isGlobal, XSDocumentInfo schemaDoc) {
/*  960 */     return checkAttributes(element, isGlobal, schemaDoc, false);
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
/*      */   public Object[] checkAttributes(Element element, boolean isGlobal, XSDocumentInfo schemaDoc, boolean enumAsQName)
/*      */   {
/*  979 */     if (element == null) {
/*  980 */       return null;
/*      */     }
/*      */     
/*  983 */     Attr[] attrs = DOMUtil.getAttrs(element);
/*      */ 
/*      */     
/*  986 */     resolveNamespace(element, attrs, schemaDoc.fNamespaceSupport);
/*      */     
/*  988 */     String uri = DOMUtil.getNamespaceURI(element);
/*  989 */     String elName = DOMUtil.getLocalName(element);
/*      */     
/*  991 */     if (!SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(uri)) {
/*  992 */       reportSchemaError("s4s-elt-schema-ns", new Object[] { elName }, element);
/*      */     }
/*      */     
/*  995 */     Map eleAttrsMap = fEleAttrsMapG;
/*  996 */     String lookupName = elName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1002 */     if (!isGlobal) {
/* 1003 */       eleAttrsMap = fEleAttrsMapL;
/* 1004 */       if (elName.equals(SchemaSymbols.ELT_ELEMENT)) {
/* 1005 */         if (DOMUtil.getAttr(element, SchemaSymbols.ATT_REF) != null)
/* 1006 */         { lookupName = "element_r"; }
/*      */         else
/* 1008 */         { lookupName = "element_n"; } 
/* 1009 */       } else if (elName.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
/* 1010 */         if (DOMUtil.getAttr(element, SchemaSymbols.ATT_REF) != null) {
/* 1011 */           lookupName = "attribute_r";
/*      */         } else {
/* 1013 */           lookupName = "attribute_n";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1018 */     Container attrList = (Container)eleAttrsMap.get(lookupName);
/* 1019 */     if (attrList == null) {
/*      */ 
/*      */ 
/*      */       
/* 1023 */       reportSchemaError("s4s-elt-invalid", new Object[] { elName }, element);
/* 1024 */       return null;
/*      */     } 
/*      */     
/* 1027 */     Object[] attrValues = getAvailableArray();
/* 1028 */     long fromDefault = 0L;
/*      */ 
/*      */     
/* 1031 */     System.arraycopy(fSeenTemp, 0, this.fSeen, 0, ATTIDX_COUNT);
/*      */ 
/*      */     
/* 1034 */     int length = attrs.length;
/* 1035 */     Attr sattr = null;
/* 1036 */     for (int i = 0; i < length; i++) {
/* 1037 */       sattr = attrs[i];
/*      */ 
/*      */       
/* 1040 */       String attrName = sattr.getName();
/* 1041 */       String attrURI = DOMUtil.getNamespaceURI(sattr);
/* 1042 */       String attrVal = DOMUtil.getValue(sattr);
/*      */       
/* 1044 */       if (attrName.startsWith("xml")) {
/* 1045 */         String attrPrefix = DOMUtil.getPrefix(sattr);
/*      */         
/* 1047 */         if ("xmlns".equals(attrPrefix) || "xmlns".equals(attrName)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1053 */         if (SchemaSymbols.ATT_XML_LANG.equals(attrName) && (SchemaSymbols.ELT_SCHEMA
/* 1054 */           .equals(elName) || SchemaSymbols.ELT_DOCUMENTATION
/* 1055 */           .equals(elName))) {
/* 1056 */           attrURI = null;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1062 */       if (attrURI != null && attrURI.length() != 0) {
/*      */ 
/*      */         
/* 1065 */         if (attrURI.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA)) {
/* 1066 */           reportSchemaError("s4s-att-not-allowed", new Object[] { elName, attrName }, element);
/*      */         } else {
/*      */           
/* 1069 */           if (attrValues[ATTIDX_NONSCHEMA] == null)
/*      */           {
/* 1071 */             attrValues[ATTIDX_NONSCHEMA] = new Vector(4, 2);
/*      */           }
/* 1073 */           ((Vector<String>)attrValues[ATTIDX_NONSCHEMA]).addElement(attrName);
/* 1074 */           ((Vector<String>)attrValues[ATTIDX_NONSCHEMA]).addElement(attrVal);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1098 */         OneAttr oneAttr = attrList.get(attrName);
/* 1099 */         if (oneAttr == null) {
/* 1100 */           reportSchemaError("s4s-att-not-allowed", new Object[] { elName, attrName }, element);
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1107 */           this.fSeen[oneAttr.valueIndex] = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1114 */             if (oneAttr.dvIndex >= 0) {
/* 1115 */               if (oneAttr.dvIndex != 3 && oneAttr.dvIndex != 6 && oneAttr.dvIndex != 7) {
/*      */ 
/*      */                 
/* 1118 */                 XSSimpleType dv = fExtraDVs[oneAttr.dvIndex];
/* 1119 */                 Object avalue = dv.validate(attrVal, schemaDoc.fValidationContext, (ValidatedInfo)null);
/*      */                 
/* 1121 */                 if (oneAttr.dvIndex == 2) {
/* 1122 */                   QName qname = (QName)avalue;
/* 1123 */                   if (qname.prefix == XMLSymbols.EMPTY_STRING && qname.uri == null && schemaDoc.fIsChameleonSchema)
/* 1124 */                     qname.uri = schemaDoc.fTargetNamespace; 
/*      */                 } 
/* 1126 */                 attrValues[oneAttr.valueIndex] = avalue;
/*      */               } else {
/* 1128 */                 attrValues[oneAttr.valueIndex] = attrVal;
/*      */               } 
/*      */             } else {
/*      */               
/* 1132 */               attrValues[oneAttr.valueIndex] = validate(attrValues, attrName, attrVal, oneAttr.dvIndex, schemaDoc);
/*      */             } 
/* 1134 */           } catch (InvalidDatatypeValueException ide) {
/* 1135 */             reportSchemaError("s4s-att-invalid-value", new Object[] { elName, attrName, ide
/* 1136 */                   .getMessage() }, element);
/*      */             
/* 1138 */             if (oneAttr.dfltValue != null)
/*      */             {
/* 1140 */               attrValues[oneAttr.valueIndex] = oneAttr.dfltValue;
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 1145 */           if (elName.equals(SchemaSymbols.ELT_ENUMERATION) && enumAsQName)
/* 1146 */             attrValues[ATTIDX_ENUMNSDECLS] = new SchemaNamespaceSupport(schemaDoc.fNamespaceSupport); 
/*      */         } 
/*      */       } 
/*      */       continue;
/*      */     } 
/* 1151 */     OneAttr[] reqAttrs = attrList.values;
/* 1152 */     for (int j = 0; j < reqAttrs.length; j++) {
/* 1153 */       OneAttr oneAttr = reqAttrs[j];
/*      */ 
/*      */ 
/*      */       
/* 1157 */       if (oneAttr.dfltValue != null && !this.fSeen[oneAttr.valueIndex]) {
/*      */         
/* 1159 */         attrValues[oneAttr.valueIndex] = oneAttr.dfltValue;
/* 1160 */         fromDefault |= (1 << oneAttr.valueIndex);
/*      */       } 
/*      */     } 
/*      */     
/* 1164 */     attrValues[ATTIDX_FROMDEFAULT] = new Long(fromDefault);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1169 */     if (attrValues[ATTIDX_MAXOCCURS] != null) {
/* 1170 */       int min = ((XInt)attrValues[ATTIDX_MINOCCURS]).intValue();
/* 1171 */       int max = ((XInt)attrValues[ATTIDX_MAXOCCURS]).intValue();
/* 1172 */       if (max != -1) {
/*      */ 
/*      */         
/* 1175 */         if (this.fSchemaHandler.fSecurityManager != null) {
/* 1176 */           String localName = element.getLocalName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1189 */           boolean optimize = ((localName.equals("element") || localName.equals("any")) && element.getNextSibling() == null && element.getPreviousSibling() == null && element.getParentNode().getLocalName().equals("sequence"));
/*      */           
/* 1191 */           if (!optimize) {
/*      */ 
/*      */             
/* 1194 */             int maxOccurNodeLimit = this.fSchemaHandler.fSecurityManager.getLimit(XMLSecurityManager.Limit.MAX_OCCUR_NODE_LIMIT);
/* 1195 */             if (max > maxOccurNodeLimit && !this.fSchemaHandler.fSecurityManager.isNoLimit(maxOccurNodeLimit)) {
/* 1196 */               reportSchemaFatalError("MaxOccurLimit", new Object[] { new Integer(maxOccurNodeLimit) }, element);
/*      */ 
/*      */               
/* 1199 */               attrValues[ATTIDX_MAXOCCURS] = fXIntPool.getXInt(maxOccurNodeLimit);
/*      */               
/* 1201 */               max = maxOccurNodeLimit;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1206 */         if (min > max) {
/* 1207 */           reportSchemaError("p-props-correct.2.1", new Object[] { elName, attrValues[ATTIDX_MINOCCURS], attrValues[ATTIDX_MAXOCCURS] }, element);
/*      */ 
/*      */           
/* 1210 */           attrValues[ATTIDX_MINOCCURS] = attrValues[ATTIDX_MAXOCCURS];
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1215 */     return attrValues; } private Object validate(Object[] attrValues, String attr, String ivalue, int dvIndex, XSDocumentInfo schemaDoc) throws InvalidDatatypeValueException { Vector<QName> memberType;
/*      */     int choice;
/*      */     StringTokenizer tokens;
/*      */     int num;
/*      */     String[] list;
/* 1220 */     if (ivalue == null) {
/* 1221 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1228 */     String value = XMLChar.trim(ivalue);
/* 1229 */     Object<QName> retValue = null;
/*      */ 
/*      */ 
/*      */     
/* 1233 */     switch (dvIndex) {
/*      */       case -15:
/* 1235 */         if (value.equals("false") || value
/* 1236 */           .equals("0")) {
/* 1237 */           retValue = (Object<QName>)Boolean.FALSE; break;
/* 1238 */         }  if (value.equals("true") || value
/* 1239 */           .equals("1")) {
/* 1240 */           retValue = (Object<QName>)Boolean.TRUE; break;
/*      */         } 
/* 1242 */         throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { value, "boolean" });
/*      */ 
/*      */       
/*      */       case -16:
/*      */         try {
/* 1247 */           if (value.length() > 0 && value.charAt(0) == '+')
/* 1248 */             value = value.substring(1); 
/* 1249 */           retValue = (Object<QName>)fXIntPool.getXInt(Integer.parseInt(value));
/* 1250 */         } catch (NumberFormatException e) {
/* 1251 */           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { value, "nonNegativeInteger" });
/*      */         } 
/* 1253 */         if (((XInt)retValue).intValue() < 0)
/* 1254 */           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { value, "nonNegativeInteger" }); 
/*      */         break;
/*      */       case -17:
/*      */         try {
/* 1258 */           if (value.length() > 0 && value.charAt(0) == '+')
/* 1259 */             value = value.substring(1); 
/* 1260 */           retValue = (Object<QName>)fXIntPool.getXInt(Integer.parseInt(value));
/* 1261 */         } catch (NumberFormatException e) {
/* 1262 */           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { value, "positiveInteger" });
/*      */         } 
/* 1264 */         if (((XInt)retValue).intValue() <= 0) {
/* 1265 */           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { value, "positiveInteger" });
/*      */         }
/*      */         break;
/*      */       case -1:
/* 1269 */         choice = 0;
/* 1270 */         if (value.equals("#all")) {
/* 1271 */           choice = 7;
/*      */         }
/*      */         else {
/*      */           
/* 1275 */           StringTokenizer t = new StringTokenizer(value, " \n\t\r");
/* 1276 */           while (t.hasMoreTokens()) {
/* 1277 */             String token = t.nextToken();
/*      */             
/* 1279 */             if (token.equals("extension")) {
/* 1280 */               choice |= 0x1; continue;
/*      */             } 
/* 1282 */             if (token.equals("restriction")) {
/* 1283 */               choice |= 0x2; continue;
/*      */             } 
/* 1285 */             if (token.equals("substitution")) {
/* 1286 */               choice |= 0x4;
/*      */               continue;
/*      */             } 
/* 1289 */             throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[] { value, "(#all | List of (extension | restriction | substitution))" });
/*      */           } 
/*      */         } 
/*      */         
/* 1293 */         retValue = (Object<QName>)fXIntPool.getXInt(choice);
/*      */         break;
/*      */ 
/*      */       
/*      */       case -3:
/*      */       case -2:
/* 1299 */         choice = 0;
/* 1300 */         if (value.equals("#all")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1312 */           choice = 31;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1317 */           StringTokenizer t = new StringTokenizer(value, " \n\t\r");
/* 1318 */           while (t.hasMoreTokens()) {
/* 1319 */             String token = t.nextToken();
/*      */             
/* 1321 */             if (token.equals("extension")) {
/* 1322 */               choice |= 0x1; continue;
/*      */             } 
/* 1324 */             if (token.equals("restriction")) {
/* 1325 */               choice |= 0x2;
/*      */               continue;
/*      */             } 
/* 1328 */             throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[] { value, "(#all | List of (extension | restriction))" });
/*      */           } 
/*      */         } 
/*      */         
/* 1332 */         retValue = (Object<QName>)fXIntPool.getXInt(choice);
/*      */         break;
/*      */       
/*      */       case -4:
/* 1336 */         choice = 0;
/* 1337 */         if (value.equals("#all")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1344 */           choice = 31;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1349 */           StringTokenizer t = new StringTokenizer(value, " \n\t\r");
/* 1350 */           while (t.hasMoreTokens()) {
/* 1351 */             String token = t.nextToken();
/*      */             
/* 1353 */             if (token.equals("list")) {
/* 1354 */               choice |= 0x10; continue;
/*      */             } 
/* 1356 */             if (token.equals("union")) {
/* 1357 */               choice |= 0x8; continue;
/*      */             } 
/* 1359 */             if (token.equals("restriction")) {
/* 1360 */               choice |= 0x2;
/*      */               continue;
/*      */             } 
/* 1363 */             throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[] { value, "(#all | List of (list | union | restriction))" });
/*      */           } 
/*      */         } 
/*      */         
/* 1367 */         retValue = (Object<QName>)fXIntPool.getXInt(choice);
/*      */         break;
/*      */       
/*      */       case -5:
/* 1371 */         choice = 0;
/* 1372 */         if (value.equals("#all")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1379 */           choice = 31;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1384 */           StringTokenizer t = new StringTokenizer(value, " \n\t\r");
/* 1385 */           while (t.hasMoreTokens()) {
/* 1386 */             String token = t.nextToken();
/*      */             
/* 1388 */             if (token.equals("extension")) {
/* 1389 */               choice |= 0x1; continue;
/*      */             } 
/* 1391 */             if (token.equals("restriction")) {
/* 1392 */               choice |= 0x2; continue;
/*      */             } 
/* 1394 */             if (token.equals("list")) {
/* 1395 */               choice |= 0x10; continue;
/*      */             } 
/* 1397 */             if (token.equals("union")) {
/* 1398 */               choice |= 0x8;
/*      */               continue;
/*      */             } 
/* 1401 */             throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[] { value, "(#all | List of (extension | restriction | list | union))" });
/*      */           } 
/*      */         } 
/*      */         
/* 1405 */         retValue = (Object<QName>)fXIntPool.getXInt(choice);
/*      */         break;
/*      */       
/*      */       case -6:
/* 1409 */         if (value.equals("qualified")) {
/* 1410 */           retValue = (Object<QName>)INT_QUALIFIED; break;
/* 1411 */         }  if (value.equals("unqualified")) {
/* 1412 */           retValue = (Object<QName>)INT_UNQUALIFIED; break;
/*      */         } 
/* 1414 */         throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[] { value, "(qualified | unqualified)" });
/*      */ 
/*      */ 
/*      */       
/*      */       case -7:
/* 1419 */         if (value.equals("unbounded")) {
/* 1420 */           retValue = (Object<QName>)INT_UNBOUNDED; break;
/*      */         } 
/*      */         try {
/* 1423 */           retValue = (Object<QName>)validate(attrValues, attr, value, -16, schemaDoc);
/* 1424 */         } catch (NumberFormatException e) {
/* 1425 */           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[] { value, "(nonNegativeInteger | unbounded)" });
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case -8:
/* 1431 */         if (value.equals("1")) {
/* 1432 */           retValue = (Object<QName>)fXIntPool.getXInt(1); break;
/*      */         } 
/* 1434 */         throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[] { value, "(1)" });
/*      */ 
/*      */ 
/*      */       
/*      */       case -9:
/* 1439 */         memberType = new Vector();
/*      */         try {
/* 1441 */           StringTokenizer t = new StringTokenizer(value, " \n\t\r");
/* 1442 */           while (t.hasMoreTokens()) {
/* 1443 */             String token = t.nextToken();
/* 1444 */             QName qname = (QName)fExtraDVs[2].validate(token, schemaDoc.fValidationContext, (ValidatedInfo)null);
/*      */             
/* 1446 */             if (qname.prefix == XMLSymbols.EMPTY_STRING && qname.uri == null && schemaDoc.fIsChameleonSchema)
/* 1447 */               qname.uri = schemaDoc.fTargetNamespace; 
/* 1448 */             memberType.addElement(qname);
/*      */           } 
/* 1450 */           retValue = (Object<QName>)memberType;
/*      */         }
/* 1452 */         catch (InvalidDatatypeValueException ide) {
/* 1453 */           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.2", new Object[] { value, "(List of QName)" });
/*      */         } 
/*      */         break;
/*      */       
/*      */       case -10:
/* 1458 */         if (value.equals("0")) {
/* 1459 */           retValue = (Object<QName>)fXIntPool.getXInt(0); break;
/* 1460 */         }  if (value.equals("1")) {
/* 1461 */           retValue = (Object<QName>)fXIntPool.getXInt(1); break;
/*      */         } 
/* 1463 */         throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[] { value, "(0 | 1)" });
/*      */ 
/*      */ 
/*      */       
/*      */       case -11:
/* 1468 */         if (value.equals("##any")) {
/*      */           
/* 1470 */           retValue = (Object<QName>)INT_ANY_ANY; break;
/* 1471 */         }  if (value.equals("##other")) {
/*      */           
/* 1473 */           retValue = (Object<QName>)INT_ANY_NOT;
/* 1474 */           String[] arrayOfString = new String[2];
/* 1475 */           arrayOfString[0] = schemaDoc.fTargetNamespace;
/* 1476 */           arrayOfString[1] = null;
/* 1477 */           attrValues[ATTIDX_NAMESPACE_LIST] = arrayOfString;
/*      */           break;
/*      */         } 
/* 1480 */         retValue = (Object<QName>)INT_ANY_LIST;
/*      */         
/* 1482 */         this.fNamespaceList.removeAllElements();
/*      */ 
/*      */         
/* 1485 */         tokens = new StringTokenizer(value, " \n\t\r");
/*      */ 
/*      */         
/*      */         try {
/* 1489 */           while (tokens.hasMoreTokens()) {
/* 1490 */             String tempNamespace, token = tokens.nextToken();
/* 1491 */             if (token.equals("##local")) {
/* 1492 */               tempNamespace = null;
/* 1493 */             } else if (token.equals("##targetNamespace")) {
/* 1494 */               tempNamespace = schemaDoc.fTargetNamespace;
/*      */             }
/*      */             else {
/*      */               
/* 1498 */               fExtraDVs[0].validate(token, schemaDoc.fValidationContext, (ValidatedInfo)null);
/* 1499 */               tempNamespace = this.fSymbolTable.addSymbol(token);
/*      */             } 
/*      */ 
/*      */             
/* 1503 */             if (!this.fNamespaceList.contains(tempNamespace)) {
/* 1504 */               this.fNamespaceList.addElement(tempNamespace);
/*      */             }
/*      */           } 
/* 1507 */         } catch (InvalidDatatypeValueException ide) {
/* 1508 */           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[] { value, "((##any | ##other) | List of (anyURI | (##targetNamespace | ##local)) )" });
/*      */         } 
/*      */ 
/*      */         
/* 1512 */         num = this.fNamespaceList.size();
/* 1513 */         list = new String[num];
/* 1514 */         this.fNamespaceList.copyInto((Object[])list);
/* 1515 */         attrValues[ATTIDX_NAMESPACE_LIST] = list;
/*      */         break;
/*      */ 
/*      */       
/*      */       case -12:
/* 1520 */         if (value.equals("strict")) {
/* 1521 */           retValue = (Object<QName>)INT_ANY_STRICT; break;
/* 1522 */         }  if (value.equals("lax")) {
/* 1523 */           retValue = (Object<QName>)INT_ANY_LAX; break;
/* 1524 */         }  if (value.equals("skip")) {
/* 1525 */           retValue = (Object<QName>)INT_ANY_SKIP; break;
/*      */         } 
/* 1527 */         throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[] { value, "(lax | skip | strict)" });
/*      */ 
/*      */ 
/*      */       
/*      */       case -13:
/* 1532 */         if (value.equals("optional")) {
/* 1533 */           retValue = (Object<QName>)INT_USE_OPTIONAL; break;
/* 1534 */         }  if (value.equals("required")) {
/* 1535 */           retValue = (Object<QName>)INT_USE_REQUIRED; break;
/* 1536 */         }  if (value.equals("prohibited")) {
/* 1537 */           retValue = (Object<QName>)INT_USE_PROHIBITED; break;
/*      */         } 
/* 1539 */         throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[] { value, "(optional | prohibited | required)" });
/*      */ 
/*      */ 
/*      */       
/*      */       case -14:
/* 1544 */         if (value.equals("preserve")) {
/* 1545 */           retValue = (Object<QName>)INT_WS_PRESERVE; break;
/* 1546 */         }  if (value.equals("replace")) {
/* 1547 */           retValue = (Object<QName>)INT_WS_REPLACE; break;
/* 1548 */         }  if (value.equals("collapse")) {
/* 1549 */           retValue = (Object<QName>)INT_WS_COLLAPSE; break;
/*      */         } 
/* 1551 */         throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[] { value, "(preserve | replace | collapse)" });
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1556 */     return retValue; }
/*      */ 
/*      */   
/*      */   void reportSchemaFatalError(String key, Object[] args, Element ele) {
/* 1560 */     this.fSchemaHandler.reportSchemaFatalError(key, args, ele);
/*      */   }
/*      */   
/*      */   void reportSchemaError(String key, Object[] args, Element ele) {
/* 1564 */     this.fSchemaHandler.reportSchemaError(key, args, ele);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkNonSchemaAttributes(XSGrammarBucket grammarBucket) {
/* 1573 */     Iterator<Map.Entry> entries = this.fNonSchemaAttrs.entrySet().iterator();
/*      */     
/* 1575 */     while (entries.hasNext()) {
/* 1576 */       Map.Entry entry = entries.next();
/*      */       
/* 1578 */       String attrRName = (String)entry.getKey();
/* 1579 */       String attrURI = attrRName.substring(0, attrRName.indexOf(','));
/* 1580 */       String attrLocal = attrRName.substring(attrRName.indexOf(',') + 1);
/*      */       
/* 1582 */       SchemaGrammar sGrammar = grammarBucket.getGrammar(attrURI);
/* 1583 */       if (sGrammar == null) {
/*      */         continue;
/*      */       }
/*      */       
/* 1587 */       XSAttributeDecl attrDecl = sGrammar.getGlobalAttributeDecl(attrLocal);
/* 1588 */       if (attrDecl == null) {
/*      */         continue;
/*      */       }
/* 1591 */       XSSimpleType dv = (XSSimpleType)attrDecl.getTypeDefinition();
/* 1592 */       if (dv == null) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1597 */       Vector<String> values = (Vector)entry.getValue();
/*      */       
/* 1599 */       String attrName = values.elementAt(0);
/*      */       
/* 1601 */       int count = values.size();
/* 1602 */       for (int i = 1; i < count; i += 2) {
/* 1603 */         String elName = values.elementAt(i);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1608 */           dv.validate(values.elementAt(i + 1), (ValidationContext)null, (ValidatedInfo)null);
/* 1609 */         } catch (InvalidDatatypeValueException ide) {
/* 1610 */           reportSchemaError("s4s-att-invalid-value", new Object[] { elName, attrName, ide
/* 1611 */                 .getMessage() }, null);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize(String content, short ws) {
/* 1620 */     int len = (content == null) ? 0 : content.length();
/* 1621 */     if (len == 0 || ws == 0) {
/* 1622 */       return content;
/*      */     }
/* 1624 */     StringBuffer sb = new StringBuffer();
/* 1625 */     if (ws == 1) {
/*      */ 
/*      */       
/* 1628 */       for (int i = 0; i < len; i++) {
/* 1629 */         char ch = content.charAt(i);
/* 1630 */         if (ch != '\t' && ch != '\n' && ch != '\r') {
/* 1631 */           sb.append(ch);
/*      */         } else {
/* 1633 */           sb.append(' ');
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1638 */       boolean isLeading = true;
/*      */       
/* 1640 */       for (int i = 0; i < len; i++) {
/* 1641 */         char ch = content.charAt(i);
/*      */         
/* 1643 */         if (ch != '\t' && ch != '\n' && ch != '\r' && ch != ' ') {
/* 1644 */           sb.append(ch);
/* 1645 */           isLeading = false;
/*      */         }
/*      */         else {
/*      */           
/* 1649 */           for (; i < len - 1; i++) {
/* 1650 */             ch = content.charAt(i + 1);
/* 1651 */             if (ch != '\t' && ch != '\n' && ch != '\r' && ch != ' ') {
/*      */               break;
/*      */             }
/*      */           } 
/* 1655 */           if (i < len - 1 && !isLeading) {
/* 1656 */             sb.append(' ');
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1661 */     return sb.toString();
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
/*      */   public XSAttributeChecker(XSDHandler schemaHandler) {
/* 1674 */     this.fArrayPool = new Object[10][ATTIDX_COUNT];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1679 */     this.fPoolPos = 0;
/*      */     this.fSchemaHandler = schemaHandler;
/*      */   }
/*      */   private static Object[] fTempArray = new Object[ATTIDX_COUNT]; int fPoolPos;
/*      */   protected Object[] getAvailableArray() {
/* 1684 */     if (this.fArrayPool.length == this.fPoolPos) {
/*      */       
/* 1686 */       this.fArrayPool = new Object[this.fPoolPos + 10][];
/*      */       
/* 1688 */       for (int i = this.fPoolPos; i < this.fArrayPool.length; i++) {
/* 1689 */         this.fArrayPool[i] = new Object[ATTIDX_COUNT];
/*      */       }
/*      */     } 
/* 1692 */     Object[] retArray = this.fArrayPool[this.fPoolPos];
/*      */ 
/*      */     
/* 1695 */     this.fArrayPool[this.fPoolPos++] = null;
/*      */ 
/*      */ 
/*      */     
/* 1699 */     System.arraycopy(fTempArray, 0, retArray, 0, ATTIDX_COUNT - 1);
/* 1700 */     retArray[ATTIDX_ISRETURNED] = Boolean.FALSE;
/*      */     
/* 1702 */     return retArray;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void returnAttrArray(Object[] attrArray, XSDocumentInfo schemaDoc) {
/* 1708 */     if (schemaDoc != null) {
/* 1709 */       schemaDoc.fNamespaceSupport.popContext();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1714 */     if (this.fPoolPos == 0 || attrArray == null || attrArray.length != ATTIDX_COUNT || ((Boolean)attrArray[ATTIDX_ISRETURNED])
/*      */ 
/*      */       
/* 1717 */       .booleanValue()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1722 */     attrArray[ATTIDX_ISRETURNED] = Boolean.TRUE;
/*      */     
/* 1724 */     if (attrArray[ATTIDX_NONSCHEMA] != null) {
/* 1725 */       ((Vector)attrArray[ATTIDX_NONSCHEMA]).clear();
/*      */     }
/* 1727 */     this.fArrayPool[--this.fPoolPos] = attrArray;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void resolveNamespace(Element element, Attr[] attrs, SchemaNamespaceSupport nsSupport) {
/* 1733 */     nsSupport.pushContext();
/*      */ 
/*      */     
/* 1736 */     int length = attrs.length;
/* 1737 */     Attr sattr = null;
/*      */     
/* 1739 */     for (int i = 0; i < length; i++) {
/* 1740 */       sattr = attrs[i];
/* 1741 */       String rawname = DOMUtil.getName(sattr);
/* 1742 */       String prefix = null;
/* 1743 */       if (rawname.equals(XMLSymbols.PREFIX_XMLNS)) {
/* 1744 */         prefix = XMLSymbols.EMPTY_STRING;
/* 1745 */       } else if (rawname.startsWith("xmlns:")) {
/* 1746 */         prefix = this.fSymbolTable.addSymbol(DOMUtil.getLocalName(sattr));
/* 1747 */       }  if (prefix != null) {
/* 1748 */         String uri = this.fSymbolTable.addSymbol(DOMUtil.getValue(sattr));
/* 1749 */         nsSupport.declarePrefix(prefix, (uri.length() != 0) ? uri : null);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/traversers/XSAttributeChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */