/*      */ package com.sun.org.apache.xerces.internal.impl.xpath;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XPath
/*      */ {
/*      */   private static final boolean DEBUG_ALL = false;
/*      */   private static final boolean DEBUG_XPATH_PARSE = false;
/*      */   private static final boolean DEBUG_ANY = false;
/*      */   protected String fExpression;
/*      */   protected SymbolTable fSymbolTable;
/*      */   protected LocationPath[] fLocationPaths;
/*      */   
/*      */   public XPath(String xpath, SymbolTable symbolTable, NamespaceContext context) throws XPathException {
/*   73 */     this.fExpression = xpath;
/*   74 */     this.fSymbolTable = symbolTable;
/*   75 */     parseExpression(context);
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
/*      */   public LocationPath[] getLocationPaths() {
/*   87 */     LocationPath[] ret = new LocationPath[this.fLocationPaths.length];
/*   88 */     for (int i = 0; i < this.fLocationPaths.length; i++) {
/*   89 */       ret[i] = (LocationPath)this.fLocationPaths[i].clone();
/*      */     }
/*   91 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public LocationPath getLocationPath() {
/*   96 */     return (LocationPath)this.fLocationPaths[0].clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  105 */     StringBuffer buf = new StringBuffer();
/*  106 */     for (int i = 0; i < this.fLocationPaths.length; i++) {
/*  107 */       if (i > 0) {
/*  108 */         buf.append("|");
/*      */       }
/*  110 */       buf.append(this.fLocationPaths[i].toString());
/*      */     } 
/*  112 */     return buf.toString();
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
/*      */   private static void check(boolean b) throws XPathException {
/*  127 */     if (!b) throw new XPathException("c-general-xpath");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LocationPath buildLocationPath(Vector stepsVector) throws XPathException {
/*  136 */     int size = stepsVector.size();
/*  137 */     check((size != 0));
/*  138 */     Step[] steps = new Step[size];
/*  139 */     stepsVector.copyInto((Object[])steps);
/*  140 */     stepsVector.removeAllElements();
/*      */     
/*  142 */     return new LocationPath(steps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseExpression(NamespaceContext context) throws XPathException {
/*  153 */     Tokens xtokens = new Tokens(this.fSymbolTable);
/*      */ 
/*      */     
/*  156 */     Scanner scanner = new Scanner(this.fSymbolTable)
/*      */       {
/*      */         protected void addToken(XPath.Tokens tokens, int token) throws XPathException {
/*  159 */           if (token == 6 || token == 35 || token == 11 || token == 21 || token == 4 || token == 9 || token == 10 || token == 22 || token == 23 || token == 36 || token == 8) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  172 */             super.addToken(tokens, token);
/*      */             return;
/*      */           } 
/*  175 */           throw new XPathException("c-general-xpath");
/*      */         }
/*      */       };
/*      */     
/*  179 */     int length = this.fExpression.length();
/*      */     
/*  181 */     boolean success = scanner.scanExpr(this.fSymbolTable, xtokens, this.fExpression, 0, length);
/*      */     
/*  183 */     if (!success) {
/*  184 */       throw new XPathException("c-general-xpath");
/*      */     }
/*      */     
/*  187 */     Vector<Step> stepsVector = new Vector();
/*  188 */     Vector<LocationPath> locationPathsVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  196 */     boolean expectingStep = true;
/*  197 */     boolean expectingDoubleColon = false;
/*      */     
/*  199 */     while (xtokens.hasMore()) {
/*  200 */       Step step; int token = xtokens.nextToken();
/*      */       
/*  202 */       switch (token) {
/*      */         case 23:
/*  204 */           check(!expectingStep);
/*  205 */           locationPathsVector.addElement(buildLocationPath(stepsVector));
/*  206 */           expectingStep = true;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 6:
/*  211 */           check(expectingStep);
/*      */ 
/*      */           
/*  214 */           step = new Step(new Axis((short)2), parseNodeTest(xtokens.nextToken(), xtokens, context));
/*  215 */           stepsVector.addElement(step);
/*  216 */           expectingStep = false;
/*      */           continue;
/*      */         
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*  222 */           check(expectingStep);
/*      */ 
/*      */           
/*  225 */           step = new Step(new Axis((short)1), parseNodeTest(token, xtokens, context));
/*  226 */           stepsVector.addElement(step);
/*  227 */           expectingStep = false;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 4:
/*  232 */           check(expectingStep);
/*  233 */           expectingStep = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  240 */           if (stepsVector.size() == 0) {
/*      */             
/*  242 */             Axis axis = new Axis((short)3);
/*  243 */             NodeTest nodeTest = new NodeTest((short)3);
/*  244 */             Step step1 = new Step(axis, nodeTest);
/*  245 */             stepsVector.addElement(step1);
/*      */             
/*  247 */             if (xtokens.hasMore() && xtokens
/*  248 */               .peekToken() == 22) {
/*      */               
/*  250 */               xtokens.nextToken();
/*      */ 
/*      */               
/*  253 */               axis = new Axis((short)4);
/*  254 */               nodeTest = new NodeTest((short)3);
/*  255 */               step1 = new Step(axis, nodeTest);
/*  256 */               stepsVector.addElement(step1);
/*  257 */               expectingStep = true;
/*      */             } 
/*      */           } 
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 22:
/*  267 */           throw new XPathException("c-general-xpath");
/*      */         
/*      */         case 21:
/*  270 */           check(!expectingStep);
/*  271 */           expectingStep = true;
/*      */           continue;
/*      */         
/*      */         case 35:
/*  275 */           check(expectingStep);
/*  276 */           expectingDoubleColon = true;
/*      */           
/*  278 */           if (xtokens.nextToken() == 8) {
/*      */ 
/*      */             
/*  281 */             step = new Step(new Axis((short)2), parseNodeTest(xtokens.nextToken(), xtokens, context));
/*  282 */             stepsVector.addElement(step);
/*  283 */             expectingStep = false;
/*  284 */             expectingDoubleColon = false;
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 36:
/*  289 */           check(expectingStep);
/*  290 */           expectingDoubleColon = true;
/*      */           continue;
/*      */         
/*      */         case 8:
/*  294 */           check(expectingStep);
/*  295 */           check(expectingDoubleColon);
/*  296 */           expectingDoubleColon = false;
/*      */           continue;
/*      */       } 
/*      */ 
/*      */       
/*  301 */       throw new XPathException("c-general-xpath");
/*      */     } 
/*      */ 
/*      */     
/*  305 */     check(!expectingStep);
/*      */     
/*  307 */     locationPathsVector.addElement(buildLocationPath(stepsVector));
/*      */ 
/*      */     
/*  310 */     this.fLocationPaths = new LocationPath[locationPathsVector.size()];
/*  311 */     locationPathsVector.copyInto((Object[])this.fLocationPaths);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NodeTest parseNodeTest(int typeToken, Tokens xtokens, NamespaceContext context) throws XPathException {
/*      */     String prefix;
/*      */     String uri;
/*      */     String localpart;
/*      */     String rawname;
/*  326 */     switch (typeToken) {
/*      */       case 9:
/*  328 */         return new NodeTest((short)2);
/*      */ 
/*      */       
/*      */       case 10:
/*      */       case 11:
/*  333 */         prefix = xtokens.nextTokenAsString();
/*  334 */         uri = null;
/*  335 */         if (context != null && prefix != XMLSymbols.EMPTY_STRING) {
/*  336 */           uri = context.getURI(prefix);
/*      */         }
/*  338 */         if (prefix != XMLSymbols.EMPTY_STRING && context != null && uri == null) {
/*  339 */           throw new XPathException("c-general-xpath-ns");
/*      */         }
/*      */         
/*  342 */         if (typeToken == 10) {
/*  343 */           return new NodeTest(prefix, uri);
/*      */         }
/*  345 */         localpart = xtokens.nextTokenAsString();
/*      */         
/*  347 */         rawname = (prefix != XMLSymbols.EMPTY_STRING) ? this.fSymbolTable.addSymbol(prefix + ':' + localpart) : localpart;
/*      */ 
/*      */         
/*  350 */         return new NodeTest(new QName(prefix, localpart, rawname, uri));
/*      */     } 
/*      */ 
/*      */     
/*  354 */     throw new XPathException("c-general-xpath");
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
/*      */   public static class LocationPath
/*      */     implements Cloneable
/*      */   {
/*      */     public XPath.Step[] steps;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LocationPath(XPath.Step[] steps) {
/*  389 */       this.steps = steps;
/*      */     }
/*      */ 
/*      */     
/*      */     protected LocationPath(LocationPath path) {
/*  394 */       this.steps = new XPath.Step[path.steps.length];
/*  395 */       for (int i = 0; i < this.steps.length; i++) {
/*  396 */         this.steps[i] = (XPath.Step)path.steps[i].clone();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  406 */       StringBuffer str = new StringBuffer();
/*  407 */       for (int i = 0; i < this.steps.length; i++) {
/*  408 */         if (i > 0 && (this.steps[i - 1]).axis.type != 4 && (this.steps[i]).axis.type != 4)
/*      */         {
/*  410 */           str.append('/');
/*      */         }
/*  412 */         str.append(this.steps[i].toString());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  423 */       return str.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  428 */       return new LocationPath(this);
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
/*      */   public static class Step
/*      */     implements Cloneable
/*      */   {
/*      */     public XPath.Axis axis;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public XPath.NodeTest nodeTest;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Step(XPath.Axis axis, XPath.NodeTest nodeTest) {
/*  459 */       this.axis = axis;
/*  460 */       this.nodeTest = nodeTest;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Step(Step step) {
/*  465 */       this.axis = (XPath.Axis)step.axis.clone();
/*  466 */       this.nodeTest = (XPath.NodeTest)step.nodeTest.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  475 */       if (this.axis.type == 3) {
/*  476 */         return ".";
/*      */       }
/*  478 */       if (this.axis.type == 2) {
/*  479 */         return "@" + this.nodeTest.toString();
/*      */       }
/*  481 */       if (this.axis.type == 1) {
/*  482 */         return this.nodeTest.toString();
/*      */       }
/*  484 */       if (this.axis.type == 4) {
/*  485 */         return "//";
/*      */       }
/*  487 */       return "??? (" + this.axis.type + ')';
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  492 */       return new Step(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Axis
/*      */     implements Cloneable
/*      */   {
/*      */     public static final short CHILD = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short ATTRIBUTE = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short SELF = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short DESCENDANT = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short type;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Axis(short type) {
/*  536 */       this.type = type;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Axis(Axis axis) {
/*  541 */       this.type = axis.type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  550 */       switch (this.type) { case 1:
/*  551 */           return "child";
/*  552 */         case 2: return "attribute";
/*  553 */         case 3: return "self";
/*  554 */         case 4: return "descendant"; }
/*      */       
/*  556 */       return "???";
/*      */     }
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  561 */       return new Axis(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class NodeTest
/*      */     implements Cloneable
/*      */   {
/*      */     public static final short QNAME = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short WILDCARD = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short NODE = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short NAMESPACE = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short type;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  600 */     public final QName name = new QName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NodeTest(short type) {
/*  608 */       this.type = type;
/*      */     }
/*      */ 
/*      */     
/*      */     public NodeTest(QName name) {
/*  613 */       this.type = 1;
/*  614 */       this.name.setValues(name);
/*      */     }
/*      */     
/*      */     public NodeTest(String prefix, String uri) {
/*  618 */       this.type = 4;
/*  619 */       this.name.setValues(prefix, null, null, uri);
/*      */     }
/*      */ 
/*      */     
/*      */     public NodeTest(NodeTest nodeTest) {
/*  624 */       this.type = nodeTest.type;
/*  625 */       this.name.setValues(nodeTest.name);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  635 */       switch (this.type) {
/*      */         case 1:
/*  637 */           if (this.name.prefix.length() != 0) {
/*  638 */             if (this.name.uri != null) {
/*  639 */               return this.name.prefix + ':' + this.name.localpart;
/*      */             }
/*  641 */             return "{" + this.name.uri + '}' + this.name.prefix + ':' + this.name.localpart;
/*      */           } 
/*  643 */           return this.name.localpart;
/*      */         
/*      */         case 4:
/*  646 */           if (this.name.prefix.length() != 0) {
/*  647 */             if (this.name.uri != null) {
/*  648 */               return this.name.prefix + ":*";
/*      */             }
/*  650 */             return "{" + this.name.uri + '}' + this.name.prefix + ":*";
/*      */           } 
/*  652 */           return "???:*";
/*      */         
/*      */         case 2:
/*  655 */           return "*";
/*      */         
/*      */         case 3:
/*  658 */           return "node()";
/*      */       } 
/*      */       
/*  661 */       return "???";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  667 */       return new NodeTest(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Tokens
/*      */   {
/*      */     static final boolean DUMP_TOKENS = false;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPEN_PAREN = 0;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_CLOSE_PAREN = 1;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPEN_BRACKET = 2;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_CLOSE_BRACKET = 3;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_PERIOD = 4;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_DOUBLE_PERIOD = 5;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_ATSIGN = 6;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_COMMA = 7;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_DOUBLE_COLON = 8;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_NAMETEST_ANY = 9;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_NAMETEST_NAMESPACE = 10;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_NAMETEST_QNAME = 11;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_NODETYPE_COMMENT = 12;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_NODETYPE_TEXT = 13;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_NODETYPE_PI = 14;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_NODETYPE_NODE = 15;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_AND = 16;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_OR = 17;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_MOD = 18;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_DIV = 19;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_MULT = 20;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_SLASH = 21;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_DOUBLE_SLASH = 22;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_UNION = 23;
/*      */ 
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_PLUS = 24;
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_MINUS = 25;
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_EQUAL = 26;
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_NOT_EQUAL = 27;
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_LESS = 28;
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_LESS_EQUAL = 29;
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_GREATER = 30;
/*      */     
/*      */     public static final int EXPRTOKEN_OPERATOR_GREATER_EQUAL = 31;
/*      */     
/*      */     public static final int EXPRTOKEN_FUNCTION_NAME = 32;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_ANCESTOR = 33;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_ANCESTOR_OR_SELF = 34;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_ATTRIBUTE = 35;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_CHILD = 36;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_DESCENDANT = 37;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_DESCENDANT_OR_SELF = 38;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_FOLLOWING = 39;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_FOLLOWING_SIBLING = 40;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_NAMESPACE = 41;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_PARENT = 42;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_PRECEDING = 43;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_PRECEDING_SIBLING = 44;
/*      */     
/*      */     public static final int EXPRTOKEN_AXISNAME_SELF = 45;
/*      */     
/*      */     public static final int EXPRTOKEN_LITERAL = 46;
/*      */     
/*      */     public static final int EXPRTOKEN_NUMBER = 47;
/*      */     
/*      */     public static final int EXPRTOKEN_VARIABLE_REFERENCE = 48;
/*      */     
/*  800 */     private static final String[] fgTokenNames = new String[] { "EXPRTOKEN_OPEN_PAREN", "EXPRTOKEN_CLOSE_PAREN", "EXPRTOKEN_OPEN_BRACKET", "EXPRTOKEN_CLOSE_BRACKET", "EXPRTOKEN_PERIOD", "EXPRTOKEN_DOUBLE_PERIOD", "EXPRTOKEN_ATSIGN", "EXPRTOKEN_COMMA", "EXPRTOKEN_DOUBLE_COLON", "EXPRTOKEN_NAMETEST_ANY", "EXPRTOKEN_NAMETEST_NAMESPACE", "EXPRTOKEN_NAMETEST_QNAME", "EXPRTOKEN_NODETYPE_COMMENT", "EXPRTOKEN_NODETYPE_TEXT", "EXPRTOKEN_NODETYPE_PI", "EXPRTOKEN_NODETYPE_NODE", "EXPRTOKEN_OPERATOR_AND", "EXPRTOKEN_OPERATOR_OR", "EXPRTOKEN_OPERATOR_MOD", "EXPRTOKEN_OPERATOR_DIV", "EXPRTOKEN_OPERATOR_MULT", "EXPRTOKEN_OPERATOR_SLASH", "EXPRTOKEN_OPERATOR_DOUBLE_SLASH", "EXPRTOKEN_OPERATOR_UNION", "EXPRTOKEN_OPERATOR_PLUS", "EXPRTOKEN_OPERATOR_MINUS", "EXPRTOKEN_OPERATOR_EQUAL", "EXPRTOKEN_OPERATOR_NOT_EQUAL", "EXPRTOKEN_OPERATOR_LESS", "EXPRTOKEN_OPERATOR_LESS_EQUAL", "EXPRTOKEN_OPERATOR_GREATER", "EXPRTOKEN_OPERATOR_GREATER_EQUAL", "EXPRTOKEN_FUNCTION_NAME", "EXPRTOKEN_AXISNAME_ANCESTOR", "EXPRTOKEN_AXISNAME_ANCESTOR_OR_SELF", "EXPRTOKEN_AXISNAME_ATTRIBUTE", "EXPRTOKEN_AXISNAME_CHILD", "EXPRTOKEN_AXISNAME_DESCENDANT", "EXPRTOKEN_AXISNAME_DESCENDANT_OR_SELF", "EXPRTOKEN_AXISNAME_FOLLOWING", "EXPRTOKEN_AXISNAME_FOLLOWING_SIBLING", "EXPRTOKEN_AXISNAME_NAMESPACE", "EXPRTOKEN_AXISNAME_PARENT", "EXPRTOKEN_AXISNAME_PRECEDING", "EXPRTOKEN_AXISNAME_PRECEDING_SIBLING", "EXPRTOKEN_AXISNAME_SELF", "EXPRTOKEN_LITERAL", "EXPRTOKEN_NUMBER", "EXPRTOKEN_VARIABLE_REFERENCE" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int INITIAL_TOKEN_COUNT = 256;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  856 */     private int[] fTokens = new int[256];
/*  857 */     private int fTokenCount = 0;
/*      */ 
/*      */     
/*      */     private SymbolTable fSymbolTable;
/*      */     
/*  862 */     private Map<String, Integer> fSymbolMapping = new HashMap<>();
/*      */ 
/*      */     
/*  865 */     private Map<Integer, String> fTokenNames = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int fCurrentTokenIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Tokens(SymbolTable symbolTable) {
/*  877 */       this.fSymbolTable = symbolTable;
/*  878 */       String[] symbols = { "ancestor", "ancestor-or-self", "attribute", "child", "descendant", "descendant-or-self", "following", "following-sibling", "namespace", "parent", "preceding", "preceding-sibling", "self" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  885 */       for (int i = 0; i < symbols.length; i++) {
/*  886 */         this.fSymbolMapping.put(this.fSymbolTable.addSymbol(symbols[i]), Integer.valueOf(i));
/*      */       }
/*  888 */       this.fTokenNames.put(Integer.valueOf(0), "EXPRTOKEN_OPEN_PAREN");
/*  889 */       this.fTokenNames.put(Integer.valueOf(1), "EXPRTOKEN_CLOSE_PAREN");
/*  890 */       this.fTokenNames.put(Integer.valueOf(2), "EXPRTOKEN_OPEN_BRACKET");
/*  891 */       this.fTokenNames.put(Integer.valueOf(3), "EXPRTOKEN_CLOSE_BRACKET");
/*  892 */       this.fTokenNames.put(Integer.valueOf(4), "EXPRTOKEN_PERIOD");
/*  893 */       this.fTokenNames.put(Integer.valueOf(5), "EXPRTOKEN_DOUBLE_PERIOD");
/*  894 */       this.fTokenNames.put(Integer.valueOf(6), "EXPRTOKEN_ATSIGN");
/*  895 */       this.fTokenNames.put(Integer.valueOf(7), "EXPRTOKEN_COMMA");
/*  896 */       this.fTokenNames.put(Integer.valueOf(8), "EXPRTOKEN_DOUBLE_COLON");
/*  897 */       this.fTokenNames.put(Integer.valueOf(9), "EXPRTOKEN_NAMETEST_ANY");
/*  898 */       this.fTokenNames.put(Integer.valueOf(10), "EXPRTOKEN_NAMETEST_NAMESPACE");
/*  899 */       this.fTokenNames.put(Integer.valueOf(11), "EXPRTOKEN_NAMETEST_QNAME");
/*  900 */       this.fTokenNames.put(Integer.valueOf(12), "EXPRTOKEN_NODETYPE_COMMENT");
/*  901 */       this.fTokenNames.put(Integer.valueOf(13), "EXPRTOKEN_NODETYPE_TEXT");
/*  902 */       this.fTokenNames.put(Integer.valueOf(14), "EXPRTOKEN_NODETYPE_PI");
/*  903 */       this.fTokenNames.put(Integer.valueOf(15), "EXPRTOKEN_NODETYPE_NODE");
/*  904 */       this.fTokenNames.put(Integer.valueOf(16), "EXPRTOKEN_OPERATOR_AND");
/*  905 */       this.fTokenNames.put(Integer.valueOf(17), "EXPRTOKEN_OPERATOR_OR");
/*  906 */       this.fTokenNames.put(Integer.valueOf(18), "EXPRTOKEN_OPERATOR_MOD");
/*  907 */       this.fTokenNames.put(Integer.valueOf(19), "EXPRTOKEN_OPERATOR_DIV");
/*  908 */       this.fTokenNames.put(Integer.valueOf(20), "EXPRTOKEN_OPERATOR_MULT");
/*  909 */       this.fTokenNames.put(Integer.valueOf(21), "EXPRTOKEN_OPERATOR_SLASH");
/*  910 */       this.fTokenNames.put(Integer.valueOf(22), "EXPRTOKEN_OPERATOR_DOUBLE_SLASH");
/*  911 */       this.fTokenNames.put(Integer.valueOf(23), "EXPRTOKEN_OPERATOR_UNION");
/*  912 */       this.fTokenNames.put(Integer.valueOf(24), "EXPRTOKEN_OPERATOR_PLUS");
/*  913 */       this.fTokenNames.put(Integer.valueOf(25), "EXPRTOKEN_OPERATOR_MINUS");
/*  914 */       this.fTokenNames.put(Integer.valueOf(26), "EXPRTOKEN_OPERATOR_EQUAL");
/*  915 */       this.fTokenNames.put(Integer.valueOf(27), "EXPRTOKEN_OPERATOR_NOT_EQUAL");
/*  916 */       this.fTokenNames.put(Integer.valueOf(28), "EXPRTOKEN_OPERATOR_LESS");
/*  917 */       this.fTokenNames.put(Integer.valueOf(29), "EXPRTOKEN_OPERATOR_LESS_EQUAL");
/*  918 */       this.fTokenNames.put(Integer.valueOf(30), "EXPRTOKEN_OPERATOR_GREATER");
/*  919 */       this.fTokenNames.put(Integer.valueOf(31), "EXPRTOKEN_OPERATOR_GREATER_EQUAL");
/*  920 */       this.fTokenNames.put(Integer.valueOf(32), "EXPRTOKEN_FUNCTION_NAME");
/*  921 */       this.fTokenNames.put(Integer.valueOf(33), "EXPRTOKEN_AXISNAME_ANCESTOR");
/*  922 */       this.fTokenNames.put(Integer.valueOf(34), "EXPRTOKEN_AXISNAME_ANCESTOR_OR_SELF");
/*  923 */       this.fTokenNames.put(Integer.valueOf(35), "EXPRTOKEN_AXISNAME_ATTRIBUTE");
/*  924 */       this.fTokenNames.put(Integer.valueOf(36), "EXPRTOKEN_AXISNAME_CHILD");
/*  925 */       this.fTokenNames.put(Integer.valueOf(37), "EXPRTOKEN_AXISNAME_DESCENDANT");
/*  926 */       this.fTokenNames.put(Integer.valueOf(38), "EXPRTOKEN_AXISNAME_DESCENDANT_OR_SELF");
/*  927 */       this.fTokenNames.put(Integer.valueOf(39), "EXPRTOKEN_AXISNAME_FOLLOWING");
/*  928 */       this.fTokenNames.put(Integer.valueOf(40), "EXPRTOKEN_AXISNAME_FOLLOWING_SIBLING");
/*  929 */       this.fTokenNames.put(Integer.valueOf(41), "EXPRTOKEN_AXISNAME_NAMESPACE");
/*  930 */       this.fTokenNames.put(Integer.valueOf(42), "EXPRTOKEN_AXISNAME_PARENT");
/*  931 */       this.fTokenNames.put(Integer.valueOf(43), "EXPRTOKEN_AXISNAME_PRECEDING");
/*  932 */       this.fTokenNames.put(Integer.valueOf(44), "EXPRTOKEN_AXISNAME_PRECEDING_SIBLING");
/*  933 */       this.fTokenNames.put(Integer.valueOf(45), "EXPRTOKEN_AXISNAME_SELF");
/*  934 */       this.fTokenNames.put(Integer.valueOf(46), "EXPRTOKEN_LITERAL");
/*  935 */       this.fTokenNames.put(Integer.valueOf(47), "EXPRTOKEN_NUMBER");
/*  936 */       this.fTokenNames.put(Integer.valueOf(48), "EXPRTOKEN_VARIABLE_REFERENCE");
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
/*      */     public String getTokenString(int token) {
/*  950 */       return this.fTokenNames.get(Integer.valueOf(token));
/*      */     }
/*      */     
/*      */     public void addToken(String tokenStr) {
/*  954 */       Integer tokenInt = null;
/*  955 */       for (Map.Entry<Integer, String> entry : this.fTokenNames.entrySet()) {
/*  956 */         if (((String)entry.getValue()).equals(tokenStr)) {
/*  957 */           tokenInt = entry.getKey();
/*      */         }
/*      */       } 
/*  960 */       if (tokenInt == null) {
/*  961 */         tokenInt = Integer.valueOf(this.fTokenNames.size());
/*  962 */         this.fTokenNames.put(tokenInt, tokenStr);
/*      */       } 
/*  964 */       addToken(tokenInt.intValue());
/*      */     }
/*      */     
/*      */     public void addToken(int token) {
/*      */       try {
/*  969 */         this.fTokens[this.fTokenCount] = token;
/*  970 */       } catch (ArrayIndexOutOfBoundsException ex) {
/*  971 */         int[] oldList = this.fTokens;
/*  972 */         this.fTokens = new int[this.fTokenCount << 1];
/*  973 */         System.arraycopy(oldList, 0, this.fTokens, 0, this.fTokenCount);
/*  974 */         this.fTokens[this.fTokenCount] = token;
/*      */       } 
/*  976 */       this.fTokenCount++;
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
/*      */     public void rewind() {
/*  989 */       this.fCurrentTokenIndex = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasMore() {
/*  996 */       return (this.fCurrentTokenIndex < this.fTokenCount);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextToken() throws XPathException {
/* 1006 */       if (this.fCurrentTokenIndex == this.fTokenCount)
/* 1007 */         throw new XPathException("c-general-xpath"); 
/* 1008 */       return this.fTokens[this.fCurrentTokenIndex++];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int peekToken() throws XPathException {
/* 1018 */       if (this.fCurrentTokenIndex == this.fTokenCount)
/* 1019 */         throw new XPathException("c-general-xpath"); 
/* 1020 */       return this.fTokens[this.fCurrentTokenIndex];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String nextTokenAsString() throws XPathException {
/* 1030 */       String s = getTokenString(nextToken());
/* 1031 */       if (s == null) throw new XPathException("c-general-xpath"); 
/* 1032 */       return s;
/*      */     }
/*      */ 
/*      */     
/*      */     public void dumpTokens() {
/* 1037 */       for (int i = 0; i < this.fTokenCount; i++) {
/* 1038 */         switch (this.fTokens[i]) {
/*      */           case 0:
/* 1040 */             System.out.print("<OPEN_PAREN/>");
/*      */             break;
/*      */           case 1:
/* 1043 */             System.out.print("<CLOSE_PAREN/>");
/*      */             break;
/*      */           case 2:
/* 1046 */             System.out.print("<OPEN_BRACKET/>");
/*      */             break;
/*      */           case 3:
/* 1049 */             System.out.print("<CLOSE_BRACKET/>");
/*      */             break;
/*      */           case 4:
/* 1052 */             System.out.print("<PERIOD/>");
/*      */             break;
/*      */           case 5:
/* 1055 */             System.out.print("<DOUBLE_PERIOD/>");
/*      */             break;
/*      */           case 6:
/* 1058 */             System.out.print("<ATSIGN/>");
/*      */             break;
/*      */           case 7:
/* 1061 */             System.out.print("<COMMA/>");
/*      */             break;
/*      */           case 8:
/* 1064 */             System.out.print("<DOUBLE_COLON/>");
/*      */             break;
/*      */           case 9:
/* 1067 */             System.out.print("<NAMETEST_ANY/>");
/*      */             break;
/*      */           case 10:
/* 1070 */             System.out.print("<NAMETEST_NAMESPACE");
/* 1071 */             System.out.print(" prefix=\"" + getTokenString(this.fTokens[++i]) + "\"");
/* 1072 */             System.out.print("/>");
/*      */             break;
/*      */           case 11:
/* 1075 */             System.out.print("<NAMETEST_QNAME");
/* 1076 */             if (this.fTokens[++i] != -1)
/* 1077 */               System.out.print(" prefix=\"" + getTokenString(this.fTokens[i]) + "\""); 
/* 1078 */             System.out.print(" localpart=\"" + getTokenString(this.fTokens[++i]) + "\"");
/* 1079 */             System.out.print("/>");
/*      */             break;
/*      */           case 12:
/* 1082 */             System.out.print("<NODETYPE_COMMENT/>");
/*      */             break;
/*      */           case 13:
/* 1085 */             System.out.print("<NODETYPE_TEXT/>");
/*      */             break;
/*      */           case 14:
/* 1088 */             System.out.print("<NODETYPE_PI/>");
/*      */             break;
/*      */           case 15:
/* 1091 */             System.out.print("<NODETYPE_NODE/>");
/*      */             break;
/*      */           case 16:
/* 1094 */             System.out.print("<OPERATOR_AND/>");
/*      */             break;
/*      */           case 17:
/* 1097 */             System.out.print("<OPERATOR_OR/>");
/*      */             break;
/*      */           case 18:
/* 1100 */             System.out.print("<OPERATOR_MOD/>");
/*      */             break;
/*      */           case 19:
/* 1103 */             System.out.print("<OPERATOR_DIV/>");
/*      */             break;
/*      */           case 20:
/* 1106 */             System.out.print("<OPERATOR_MULT/>");
/*      */             break;
/*      */           case 21:
/* 1109 */             System.out.print("<OPERATOR_SLASH/>");
/* 1110 */             if (i + 1 < this.fTokenCount) {
/* 1111 */               System.out.println();
/* 1112 */               System.out.print("  ");
/*      */             } 
/*      */             break;
/*      */           case 22:
/* 1116 */             System.out.print("<OPERATOR_DOUBLE_SLASH/>");
/*      */             break;
/*      */           case 23:
/* 1119 */             System.out.print("<OPERATOR_UNION/>");
/*      */             break;
/*      */           case 24:
/* 1122 */             System.out.print("<OPERATOR_PLUS/>");
/*      */             break;
/*      */           case 25:
/* 1125 */             System.out.print("<OPERATOR_MINUS/>");
/*      */             break;
/*      */           case 26:
/* 1128 */             System.out.print("<OPERATOR_EQUAL/>");
/*      */             break;
/*      */           case 27:
/* 1131 */             System.out.print("<OPERATOR_NOT_EQUAL/>");
/*      */             break;
/*      */           case 28:
/* 1134 */             System.out.print("<OPERATOR_LESS/>");
/*      */             break;
/*      */           case 29:
/* 1137 */             System.out.print("<OPERATOR_LESS_EQUAL/>");
/*      */             break;
/*      */           case 30:
/* 1140 */             System.out.print("<OPERATOR_GREATER/>");
/*      */             break;
/*      */           case 31:
/* 1143 */             System.out.print("<OPERATOR_GREATER_EQUAL/>");
/*      */             break;
/*      */           case 32:
/* 1146 */             System.out.print("<FUNCTION_NAME");
/* 1147 */             if (this.fTokens[++i] != -1)
/* 1148 */               System.out.print(" prefix=\"" + getTokenString(this.fTokens[i]) + "\""); 
/* 1149 */             System.out.print(" localpart=\"" + getTokenString(this.fTokens[++i]) + "\"");
/* 1150 */             System.out.print("/>");
/*      */             break;
/*      */           case 33:
/* 1153 */             System.out.print("<AXISNAME_ANCESTOR/>");
/*      */             break;
/*      */           case 34:
/* 1156 */             System.out.print("<AXISNAME_ANCESTOR_OR_SELF/>");
/*      */             break;
/*      */           case 35:
/* 1159 */             System.out.print("<AXISNAME_ATTRIBUTE/>");
/*      */             break;
/*      */           case 36:
/* 1162 */             System.out.print("<AXISNAME_CHILD/>");
/*      */             break;
/*      */           case 37:
/* 1165 */             System.out.print("<AXISNAME_DESCENDANT/>");
/*      */             break;
/*      */           case 38:
/* 1168 */             System.out.print("<AXISNAME_DESCENDANT_OR_SELF/>");
/*      */             break;
/*      */           case 39:
/* 1171 */             System.out.print("<AXISNAME_FOLLOWING/>");
/*      */             break;
/*      */           case 40:
/* 1174 */             System.out.print("<AXISNAME_FOLLOWING_SIBLING/>");
/*      */             break;
/*      */           case 41:
/* 1177 */             System.out.print("<AXISNAME_NAMESPACE/>");
/*      */             break;
/*      */           case 42:
/* 1180 */             System.out.print("<AXISNAME_PARENT/>");
/*      */             break;
/*      */           case 43:
/* 1183 */             System.out.print("<AXISNAME_PRECEDING/>");
/*      */             break;
/*      */           case 44:
/* 1186 */             System.out.print("<AXISNAME_PRECEDING_SIBLING/>");
/*      */             break;
/*      */           case 45:
/* 1189 */             System.out.print("<AXISNAME_SELF/>");
/*      */             break;
/*      */           case 46:
/* 1192 */             System.out.print("<LITERAL");
/* 1193 */             System.out.print(" value=\"" + getTokenString(this.fTokens[++i]) + "\"");
/* 1194 */             System.out.print("/>");
/*      */             break;
/*      */           case 47:
/* 1197 */             System.out.print("<NUMBER");
/* 1198 */             System.out.print(" whole=\"" + getTokenString(this.fTokens[++i]) + "\"");
/* 1199 */             System.out.print(" part=\"" + getTokenString(this.fTokens[++i]) + "\"");
/* 1200 */             System.out.print("/>");
/*      */             break;
/*      */           case 48:
/* 1203 */             System.out.print("<VARIABLE_REFERENCE");
/* 1204 */             if (this.fTokens[++i] != -1)
/* 1205 */               System.out.print(" prefix=\"" + getTokenString(this.fTokens[i]) + "\""); 
/* 1206 */             System.out.print(" localpart=\"" + getTokenString(this.fTokens[++i]) + "\"");
/* 1207 */             System.out.print("/>");
/*      */             break;
/*      */           default:
/* 1210 */             System.out.println("<???/>"); break;
/*      */         } 
/*      */       } 
/* 1213 */       System.out.println();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Scanner
/*      */   {
/*      */     private static final byte CHARTYPE_INVALID = 0;
/*      */     
/*      */     private static final byte CHARTYPE_OTHER = 1;
/*      */     
/*      */     private static final byte CHARTYPE_WHITESPACE = 2;
/*      */     
/*      */     private static final byte CHARTYPE_EXCLAMATION = 3;
/*      */     
/*      */     private static final byte CHARTYPE_QUOTE = 4;
/*      */     
/*      */     private static final byte CHARTYPE_DOLLAR = 5;
/*      */     
/*      */     private static final byte CHARTYPE_OPEN_PAREN = 6;
/*      */     
/*      */     private static final byte CHARTYPE_CLOSE_PAREN = 7;
/*      */     
/*      */     private static final byte CHARTYPE_STAR = 8;
/*      */     
/*      */     private static final byte CHARTYPE_PLUS = 9;
/*      */     
/*      */     private static final byte CHARTYPE_COMMA = 10;
/*      */     
/*      */     private static final byte CHARTYPE_MINUS = 11;
/*      */     
/*      */     private static final byte CHARTYPE_PERIOD = 12;
/*      */     
/*      */     private static final byte CHARTYPE_SLASH = 13;
/*      */     
/*      */     private static final byte CHARTYPE_DIGIT = 14;
/*      */     
/*      */     private static final byte CHARTYPE_COLON = 15;
/*      */     
/*      */     private static final byte CHARTYPE_LESS = 16;
/*      */     
/*      */     private static final byte CHARTYPE_EQUAL = 17;
/*      */     
/*      */     private static final byte CHARTYPE_GREATER = 18;
/*      */     
/*      */     private static final byte CHARTYPE_ATSIGN = 19;
/*      */     
/*      */     private static final byte CHARTYPE_LETTER = 20;
/*      */     
/*      */     private static final byte CHARTYPE_OPEN_BRACKET = 21;
/*      */     
/*      */     private static final byte CHARTYPE_CLOSE_BRACKET = 22;
/*      */     
/*      */     private static final byte CHARTYPE_UNDERSCORE = 23;
/*      */     private static final byte CHARTYPE_UNION = 24;
/*      */     private static final byte CHARTYPE_NONASCII = 25;
/* 1269 */     private static final byte[] fASCIICharMap = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 1, 5, 1, 1, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 1, 16, 17, 18, 1, 19, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 21, 1, 22, 1, 23, 1, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 1, 24, 1, 1, 1 };
/*      */ 
/*      */ 
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
/*      */     
/* 1293 */     private static final String fAndSymbol = "and".intern();
/* 1294 */     private static final String fOrSymbol = "or".intern();
/* 1295 */     private static final String fModSymbol = "mod".intern();
/* 1296 */     private static final String fDivSymbol = "div".intern();
/*      */     
/* 1298 */     private static final String fCommentSymbol = "comment".intern();
/* 1299 */     private static final String fTextSymbol = "text".intern();
/* 1300 */     private static final String fPISymbol = "processing-instruction".intern();
/* 1301 */     private static final String fNodeSymbol = "node".intern();
/*      */     
/* 1303 */     private static final String fAncestorSymbol = "ancestor".intern();
/* 1304 */     private static final String fAncestorOrSelfSymbol = "ancestor-or-self".intern();
/* 1305 */     private static final String fAttributeSymbol = "attribute".intern();
/* 1306 */     private static final String fChildSymbol = "child".intern();
/* 1307 */     private static final String fDescendantSymbol = "descendant".intern();
/* 1308 */     private static final String fDescendantOrSelfSymbol = "descendant-or-self".intern();
/* 1309 */     private static final String fFollowingSymbol = "following".intern();
/* 1310 */     private static final String fFollowingSiblingSymbol = "following-sibling".intern();
/* 1311 */     private static final String fNamespaceSymbol = "namespace".intern();
/* 1312 */     private static final String fParentSymbol = "parent".intern();
/* 1313 */     private static final String fPrecedingSymbol = "preceding".intern();
/* 1314 */     private static final String fPrecedingSiblingSymbol = "preceding-sibling".intern();
/* 1315 */     private static final String fSelfSymbol = "self".intern();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Scanner(SymbolTable symbolTable) {
/* 1325 */       this.fSymbolTable = symbolTable;
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
/*      */     public boolean scanExpr(SymbolTable symbolTable, XPath.Tokens tokens, String data, int currentOffset, int endOffset) throws XPathException {
/* 1339 */       boolean starIsMultiplyOperator = false;
/*      */ 
/*      */ 
/*      */       
/* 1343 */       while (currentOffset != endOffset) {
/*      */         int nameOffset; String nameHandle, prefixHandle; int qchar, litOffset, litLength;
/*      */         boolean isNameTestNCName, isAxisName;
/* 1346 */         int ch = data.charAt(currentOffset);
/*      */ 
/*      */ 
/*      */         
/* 1350 */         while ((ch == 32 || ch == 10 || ch == 9 || ch == 13) && 
/* 1351 */           ++currentOffset != endOffset)
/*      */         {
/*      */           
/* 1354 */           ch = data.charAt(currentOffset);
/*      */         }
/* 1356 */         if (currentOffset == endOffset) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1364 */         byte chartype = (ch >= 128) ? 25 : fASCIICharMap[ch];
/* 1365 */         switch (chartype) {
/*      */           case 6:
/* 1367 */             addToken(tokens, 0);
/* 1368 */             starIsMultiplyOperator = false;
/* 1369 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 7:
/* 1374 */             addToken(tokens, 1);
/* 1375 */             starIsMultiplyOperator = true;
/* 1376 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 21:
/* 1381 */             addToken(tokens, 2);
/* 1382 */             starIsMultiplyOperator = false;
/* 1383 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 22:
/* 1388 */             addToken(tokens, 3);
/* 1389 */             starIsMultiplyOperator = true;
/* 1390 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 12:
/* 1399 */             if (currentOffset + 1 == endOffset) {
/* 1400 */               addToken(tokens, 4);
/* 1401 */               starIsMultiplyOperator = true;
/* 1402 */               currentOffset++;
/*      */               continue;
/*      */             } 
/* 1405 */             ch = data.charAt(currentOffset + 1);
/* 1406 */             if (ch == 46)
/* 1407 */             { addToken(tokens, 5);
/* 1408 */               starIsMultiplyOperator = true;
/* 1409 */               currentOffset += 2; }
/* 1410 */             else if (ch >= 48 && ch <= 57)
/* 1411 */             { addToken(tokens, 47);
/* 1412 */               starIsMultiplyOperator = true;
/* 1413 */               currentOffset = scanNumber(tokens, data, endOffset, currentOffset); }
/* 1414 */             else if (ch == 47)
/* 1415 */             { addToken(tokens, 4);
/* 1416 */               starIsMultiplyOperator = true;
/* 1417 */               currentOffset++; }
/* 1418 */             else { if (ch == 124) {
/* 1419 */                 addToken(tokens, 4);
/* 1420 */                 starIsMultiplyOperator = true;
/* 1421 */                 currentOffset++; continue;
/*      */               } 
/* 1423 */               if (ch == 32 || ch == 10 || ch == 9 || ch == 13) {
/*      */ 
/*      */                 
/* 1426 */                 while (++currentOffset != endOffset)
/*      */                 
/*      */                 { 
/* 1429 */                   ch = data.charAt(currentOffset);
/* 1430 */                   if (ch != 32 && ch != 10 && ch != 9 && ch != 13)
/* 1431 */                     break;  }  if (currentOffset == endOffset || ch == 124 || ch == 47) {
/* 1432 */                   addToken(tokens, 4);
/* 1433 */                   starIsMultiplyOperator = true;
/*      */                   continue;
/*      */                 } 
/* 1436 */                 throw new XPathException("c-general-xpath");
/*      */               } 
/* 1438 */               throw new XPathException("c-general-xpath"); }
/*      */             
/* 1440 */             if (currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 19:
/* 1445 */             addToken(tokens, 6);
/* 1446 */             starIsMultiplyOperator = false;
/* 1447 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 10:
/* 1452 */             addToken(tokens, 7);
/* 1453 */             starIsMultiplyOperator = false;
/* 1454 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 15:
/* 1459 */             if (++currentOffset == endOffset)
/*      */             {
/* 1461 */               return false;
/*      */             }
/* 1463 */             ch = data.charAt(currentOffset);
/* 1464 */             if (ch != 58)
/*      */             {
/* 1466 */               return false;
/*      */             }
/* 1468 */             addToken(tokens, 8);
/* 1469 */             starIsMultiplyOperator = false;
/* 1470 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 13:
/* 1475 */             if (++currentOffset == endOffset) {
/* 1476 */               addToken(tokens, 21);
/* 1477 */               starIsMultiplyOperator = false;
/*      */               continue;
/*      */             } 
/* 1480 */             ch = data.charAt(currentOffset);
/* 1481 */             if (ch == 47) {
/* 1482 */               addToken(tokens, 22);
/* 1483 */               starIsMultiplyOperator = false;
/* 1484 */               if (++currentOffset == endOffset);
/*      */               
/*      */               continue;
/*      */             } 
/* 1488 */             addToken(tokens, 21);
/* 1489 */             starIsMultiplyOperator = false;
/*      */ 
/*      */           
/*      */           case 24:
/* 1493 */             addToken(tokens, 23);
/* 1494 */             starIsMultiplyOperator = false;
/* 1495 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 9:
/* 1500 */             addToken(tokens, 24);
/* 1501 */             starIsMultiplyOperator = false;
/* 1502 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 11:
/* 1507 */             addToken(tokens, 25);
/* 1508 */             starIsMultiplyOperator = false;
/* 1509 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 17:
/* 1514 */             addToken(tokens, 26);
/* 1515 */             starIsMultiplyOperator = false;
/* 1516 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 3:
/* 1521 */             if (++currentOffset == endOffset)
/*      */             {
/* 1523 */               return false;
/*      */             }
/* 1525 */             ch = data.charAt(currentOffset);
/* 1526 */             if (ch != 61)
/*      */             {
/* 1528 */               return false;
/*      */             }
/* 1530 */             addToken(tokens, 27);
/* 1531 */             starIsMultiplyOperator = false;
/* 1532 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */           
/*      */           case 16:
/* 1537 */             if (++currentOffset == endOffset) {
/* 1538 */               addToken(tokens, 28);
/* 1539 */               starIsMultiplyOperator = false;
/*      */               continue;
/*      */             } 
/* 1542 */             ch = data.charAt(currentOffset);
/* 1543 */             if (ch == 61) {
/* 1544 */               addToken(tokens, 29);
/* 1545 */               starIsMultiplyOperator = false;
/* 1546 */               if (++currentOffset == endOffset);
/*      */               
/*      */               continue;
/*      */             } 
/* 1550 */             addToken(tokens, 28);
/* 1551 */             starIsMultiplyOperator = false;
/*      */ 
/*      */           
/*      */           case 18:
/* 1555 */             if (++currentOffset == endOffset) {
/* 1556 */               addToken(tokens, 30);
/* 1557 */               starIsMultiplyOperator = false;
/*      */               continue;
/*      */             } 
/* 1560 */             ch = data.charAt(currentOffset);
/* 1561 */             if (ch == 61) {
/* 1562 */               addToken(tokens, 31);
/* 1563 */               starIsMultiplyOperator = false;
/* 1564 */               if (++currentOffset == endOffset);
/*      */               
/*      */               continue;
/*      */             } 
/* 1568 */             addToken(tokens, 30);
/* 1569 */             starIsMultiplyOperator = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 4:
/* 1576 */             qchar = ch;
/* 1577 */             if (++currentOffset == endOffset)
/*      */             {
/* 1579 */               return false;
/*      */             }
/* 1581 */             ch = data.charAt(currentOffset);
/* 1582 */             litOffset = currentOffset;
/* 1583 */             while (ch != qchar) {
/* 1584 */               if (++currentOffset == endOffset)
/*      */               {
/* 1586 */                 return false;
/*      */               }
/* 1588 */               ch = data.charAt(currentOffset);
/*      */             } 
/* 1590 */             litLength = currentOffset - litOffset;
/* 1591 */             addToken(tokens, 46);
/* 1592 */             starIsMultiplyOperator = true;
/* 1593 */             tokens.addToken(symbolTable.addSymbol(data.substring(litOffset, litOffset + litLength)));
/* 1594 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 14:
/* 1603 */             addToken(tokens, 47);
/* 1604 */             starIsMultiplyOperator = true;
/* 1605 */             currentOffset = scanNumber(tokens, data, endOffset, currentOffset);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 5:
/* 1611 */             if (++currentOffset == endOffset)
/*      */             {
/* 1613 */               return false;
/*      */             }
/* 1615 */             nameOffset = currentOffset;
/* 1616 */             currentOffset = scanNCName(data, endOffset, currentOffset);
/* 1617 */             if (currentOffset == nameOffset)
/*      */             {
/* 1619 */               return false;
/*      */             }
/* 1621 */             if (currentOffset < endOffset) {
/* 1622 */               ch = data.charAt(currentOffset);
/*      */             } else {
/*      */               
/* 1625 */               ch = -1;
/*      */             } 
/* 1627 */             nameHandle = symbolTable.addSymbol(data.substring(nameOffset, currentOffset));
/* 1628 */             if (ch != 58) {
/* 1629 */               prefixHandle = XMLSymbols.EMPTY_STRING;
/*      */             } else {
/* 1631 */               prefixHandle = nameHandle;
/* 1632 */               if (++currentOffset == endOffset)
/*      */               {
/* 1634 */                 return false;
/*      */               }
/* 1636 */               nameOffset = currentOffset;
/* 1637 */               currentOffset = scanNCName(data, endOffset, currentOffset);
/* 1638 */               if (currentOffset == nameOffset)
/*      */               {
/* 1640 */                 return false;
/*      */               }
/* 1642 */               if (currentOffset < endOffset) {
/* 1643 */                 ch = data.charAt(currentOffset);
/*      */               } else {
/*      */                 
/* 1646 */                 ch = -1;
/*      */               } 
/* 1648 */               nameHandle = symbolTable.addSymbol(data.substring(nameOffset, currentOffset));
/*      */             } 
/* 1650 */             addToken(tokens, 48);
/* 1651 */             starIsMultiplyOperator = true;
/* 1652 */             tokens.addToken(prefixHandle);
/* 1653 */             tokens.addToken(nameHandle);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 8:
/* 1668 */             if (starIsMultiplyOperator) {
/* 1669 */               addToken(tokens, 20);
/* 1670 */               starIsMultiplyOperator = false;
/*      */             } else {
/* 1672 */               addToken(tokens, 9);
/* 1673 */               starIsMultiplyOperator = true;
/*      */             } 
/* 1675 */             if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 20:
/*      */           case 23:
/*      */           case 25:
/* 1712 */             nameOffset = currentOffset;
/* 1713 */             currentOffset = scanNCName(data, endOffset, currentOffset);
/* 1714 */             if (currentOffset == nameOffset)
/*      */             {
/* 1716 */               return false;
/*      */             }
/* 1718 */             if (currentOffset < endOffset) {
/* 1719 */               ch = data.charAt(currentOffset);
/*      */             } else {
/*      */               
/* 1722 */               ch = -1;
/*      */             } 
/* 1724 */             nameHandle = symbolTable.addSymbol(data.substring(nameOffset, currentOffset));
/* 1725 */             isNameTestNCName = false;
/* 1726 */             isAxisName = false;
/* 1727 */             prefixHandle = XMLSymbols.EMPTY_STRING;
/* 1728 */             if (ch == 58) {
/* 1729 */               if (++currentOffset == endOffset)
/*      */               {
/* 1731 */                 return false;
/*      */               }
/* 1733 */               ch = data.charAt(currentOffset);
/* 1734 */               if (ch == 42) {
/* 1735 */                 if (++currentOffset < endOffset) {
/* 1736 */                   ch = data.charAt(currentOffset);
/*      */                 }
/* 1738 */                 isNameTestNCName = true;
/* 1739 */               } else if (ch == 58) {
/* 1740 */                 if (++currentOffset < endOffset) {
/* 1741 */                   ch = data.charAt(currentOffset);
/*      */                 }
/* 1743 */                 isAxisName = true;
/*      */               } else {
/* 1745 */                 prefixHandle = nameHandle;
/* 1746 */                 nameOffset = currentOffset;
/* 1747 */                 currentOffset = scanNCName(data, endOffset, currentOffset);
/* 1748 */                 if (currentOffset == nameOffset)
/*      */                 {
/* 1750 */                   return false;
/*      */                 }
/* 1752 */                 if (currentOffset < endOffset) {
/* 1753 */                   ch = data.charAt(currentOffset);
/*      */                 } else {
/*      */                   
/* 1756 */                   ch = -1;
/*      */                 } 
/* 1758 */                 nameHandle = symbolTable.addSymbol(data.substring(nameOffset, currentOffset));
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1764 */             while ((ch == 32 || ch == 10 || ch == 9 || ch == 13) && 
/* 1765 */               ++currentOffset != endOffset)
/*      */             {
/*      */               
/* 1768 */               ch = data.charAt(currentOffset);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1774 */             if (starIsMultiplyOperator) {
/* 1775 */               if (nameHandle == fAndSymbol) {
/* 1776 */                 addToken(tokens, 16);
/* 1777 */                 starIsMultiplyOperator = false;
/* 1778 */               } else if (nameHandle == fOrSymbol) {
/* 1779 */                 addToken(tokens, 17);
/* 1780 */                 starIsMultiplyOperator = false;
/* 1781 */               } else if (nameHandle == fModSymbol) {
/* 1782 */                 addToken(tokens, 18);
/* 1783 */                 starIsMultiplyOperator = false;
/* 1784 */               } else if (nameHandle == fDivSymbol) {
/* 1785 */                 addToken(tokens, 19);
/* 1786 */                 starIsMultiplyOperator = false;
/*      */               } else {
/*      */                 
/* 1789 */                 return false;
/*      */               } 
/* 1791 */               if (isNameTestNCName)
/*      */               {
/* 1793 */                 return false; } 
/* 1794 */               if (isAxisName)
/*      */               {
/* 1796 */                 return false;
/*      */               }
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */ 
/*      */             
/* 1804 */             if (ch == 40 && !isNameTestNCName && !isAxisName) {
/* 1805 */               if (nameHandle == fCommentSymbol) {
/* 1806 */                 addToken(tokens, 12);
/* 1807 */               } else if (nameHandle == fTextSymbol) {
/* 1808 */                 addToken(tokens, 13);
/* 1809 */               } else if (nameHandle == fPISymbol) {
/* 1810 */                 addToken(tokens, 14);
/* 1811 */               } else if (nameHandle == fNodeSymbol) {
/* 1812 */                 addToken(tokens, 15);
/*      */               } else {
/* 1814 */                 addToken(tokens, 32);
/* 1815 */                 tokens.addToken(prefixHandle);
/* 1816 */                 tokens.addToken(nameHandle);
/*      */               } 
/* 1818 */               addToken(tokens, 0);
/* 1819 */               starIsMultiplyOperator = false;
/* 1820 */               if (++currentOffset == endOffset);
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1829 */             if (isAxisName || (ch == 58 && currentOffset + 1 < endOffset && data
/*      */               
/* 1831 */               .charAt(currentOffset + 1) == ':')) {
/* 1832 */               if (nameHandle == fAncestorSymbol) {
/* 1833 */                 addToken(tokens, 33);
/* 1834 */               } else if (nameHandle == fAncestorOrSelfSymbol) {
/* 1835 */                 addToken(tokens, 34);
/* 1836 */               } else if (nameHandle == fAttributeSymbol) {
/* 1837 */                 addToken(tokens, 35);
/* 1838 */               } else if (nameHandle == fChildSymbol) {
/* 1839 */                 addToken(tokens, 36);
/* 1840 */               } else if (nameHandle == fDescendantSymbol) {
/* 1841 */                 addToken(tokens, 37);
/* 1842 */               } else if (nameHandle == fDescendantOrSelfSymbol) {
/* 1843 */                 addToken(tokens, 38);
/* 1844 */               } else if (nameHandle == fFollowingSymbol) {
/* 1845 */                 addToken(tokens, 39);
/* 1846 */               } else if (nameHandle == fFollowingSiblingSymbol) {
/* 1847 */                 addToken(tokens, 40);
/* 1848 */               } else if (nameHandle == fNamespaceSymbol) {
/* 1849 */                 addToken(tokens, 41);
/* 1850 */               } else if (nameHandle == fParentSymbol) {
/* 1851 */                 addToken(tokens, 42);
/* 1852 */               } else if (nameHandle == fPrecedingSymbol) {
/* 1853 */                 addToken(tokens, 43);
/* 1854 */               } else if (nameHandle == fPrecedingSiblingSymbol) {
/* 1855 */                 addToken(tokens, 44);
/* 1856 */               } else if (nameHandle == fSelfSymbol) {
/* 1857 */                 addToken(tokens, 45);
/*      */               } else {
/*      */                 
/* 1860 */                 return false;
/*      */               } 
/* 1862 */               if (isNameTestNCName)
/*      */               {
/* 1864 */                 return false;
/*      */               }
/* 1866 */               addToken(tokens, 8);
/* 1867 */               starIsMultiplyOperator = false;
/* 1868 */               if (!isAxisName) {
/* 1869 */                 currentOffset++;
/* 1870 */                 if (++currentOffset == endOffset);
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1880 */             if (isNameTestNCName) {
/* 1881 */               addToken(tokens, 10);
/* 1882 */               starIsMultiplyOperator = true;
/* 1883 */               tokens.addToken(nameHandle); continue;
/*      */             } 
/* 1885 */             addToken(tokens, 11);
/* 1886 */             starIsMultiplyOperator = true;
/* 1887 */             tokens.addToken(prefixHandle);
/* 1888 */             tokens.addToken(nameHandle);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/* 1896 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int scanNCName(String data, int endOffset, int currentOffset) {
/* 1903 */       int ch = data.charAt(currentOffset);
/* 1904 */       if (ch >= 128) {
/* 1905 */         if (!XMLChar.isNameStart(ch))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1911 */           return currentOffset;
/*      */         }
/*      */       } else {
/*      */         
/* 1915 */         byte chartype = fASCIICharMap[ch];
/* 1916 */         if (chartype != 20 && chartype != 23) {
/* 1917 */           return currentOffset;
/*      */         }
/*      */       } 
/* 1920 */       while (++currentOffset < endOffset) {
/* 1921 */         ch = data.charAt(currentOffset);
/* 1922 */         if (ch >= 128) {
/* 1923 */           if (!XMLChar.isName(ch)) {
/*      */             break;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/* 1933 */         byte chartype = fASCIICharMap[ch];
/* 1934 */         if (chartype != 20 && chartype != 14 && chartype != 12 && chartype != 11 && chartype != 23) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1942 */       return currentOffset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int scanNumber(XPath.Tokens tokens, String data, int endOffset, int currentOffset) {
/* 1949 */       int ch = data.charAt(currentOffset);
/* 1950 */       int whole = 0;
/* 1951 */       int part = 0;
/* 1952 */       while (ch >= 48 && ch <= 57) {
/* 1953 */         whole = whole * 10 + ch - 48;
/* 1954 */         if (++currentOffset == endOffset) {
/*      */           break;
/*      */         }
/* 1957 */         ch = data.charAt(currentOffset);
/*      */       } 
/* 1959 */       if (ch == 46 && 
/* 1960 */         ++currentOffset < endOffset) {
/*      */         
/* 1962 */         ch = data.charAt(currentOffset);
/* 1963 */         while (ch >= 48 && ch <= 57) {
/* 1964 */           part = part * 10 + ch - 48;
/* 1965 */           if (++currentOffset == endOffset) {
/*      */             break;
/*      */           }
/* 1968 */           ch = data.charAt(currentOffset);
/*      */         } 
/* 1970 */         if (part != 0)
/*      */         {
/*      */ 
/*      */           
/* 1974 */           throw new RuntimeException("find a solution!");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1980 */       tokens.addToken(whole);
/* 1981 */       tokens.addToken(part);
/* 1982 */       return currentOffset;
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
/*      */     protected void addToken(XPath.Tokens tokens, int token) throws XPathException {
/* 1999 */       tokens.addToken(token);
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
/*      */   public static void main(String[] argv) throws Exception {
/* 2011 */     for (int i = 0; i < argv.length; i++) {
/* 2012 */       String expression = argv[i];
/* 2013 */       System.out.println("# XPath expression: \"" + expression + '"');
/*      */       try {
/* 2015 */         SymbolTable symbolTable = new SymbolTable();
/* 2016 */         XPath xpath = new XPath(expression, symbolTable, null);
/* 2017 */         System.out.println("expanded xpath: \"" + xpath.toString() + '"');
/*      */       }
/* 2019 */       catch (XPathException e) {
/* 2020 */         System.out.println("error: " + e.getMessage());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xpath/XPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */