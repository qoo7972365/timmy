/*      */ package com.sun.org.apache.xalan.internal.xsltc.runtime;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.Translet;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.AbsoluteIterator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.ArrayNodeListIterator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.DOMAdapter;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.SingletonIterator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.StepIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;
/*      */ import com.sun.org.apache.xml.internal.serializer.NamespaceMappings;
/*      */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*      */ import com.sun.org.apache.xml.internal.utils.XML11Char;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.text.FieldPosition;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.Locale;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BasisLibrary
/*      */ {
/*      */   private static final String EMPTYSTRING = "";
/*      */   
/*   71 */   private static final ThreadLocal<StringBuilder> threadLocalStringBuilder = new ThreadLocal<StringBuilder>()
/*      */     {
/*      */       protected StringBuilder initialValue() {
/*   74 */         return new StringBuilder();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   81 */   private static final ThreadLocal<StringBuffer> threadLocalStringBuffer = new ThreadLocal<StringBuffer>()
/*      */     {
/*      */       protected StringBuffer initialValue() {
/*   84 */         return new StringBuffer();
/*      */       }
/*      */     };
/*      */   private static final int DOUBLE_FRACTION_DIGITS = 340; private static final double lowerBounds = 0.001D; private static final double upperBounds = 1.0E7D;
/*      */   private static DecimalFormat defaultFormatter;
/*      */   private static DecimalFormat xpathFormatter;
/*      */   
/*      */   public static int countF(DTMAxisIterator iterator) {
/*   92 */     return iterator.getLast();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int positionF(DTMAxisIterator iterator) {
/*  101 */     return iterator.isReverse() ? (iterator
/*  102 */       .getLast() - iterator.getPosition() + 1) : iterator
/*  103 */       .getPosition();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sumF(DTMAxisIterator iterator, DOM dom) {
/*      */     try {
/*  112 */       double result = 0.0D;
/*      */       int node;
/*  114 */       while ((node = iterator.next()) != -1) {
/*  115 */         result += Double.parseDouble(dom.getStringValueX(node));
/*      */       }
/*  117 */       return result;
/*      */     }
/*  119 */     catch (NumberFormatException e) {
/*  120 */       return Double.NaN;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String stringF(int node, DOM dom) {
/*  128 */     return dom.getStringValueX(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String stringF(Object obj, DOM dom) {
/*  135 */     if (obj instanceof DTMAxisIterator) {
/*  136 */       return dom.getStringValueX(((DTMAxisIterator)obj).reset().next());
/*      */     }
/*  138 */     if (obj instanceof Node) {
/*  139 */       return dom.getStringValueX(((Node)obj).node);
/*      */     }
/*  141 */     if (obj instanceof DOM) {
/*  142 */       return ((DOM)obj).getStringValue();
/*      */     }
/*      */     
/*  145 */     return obj.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String stringF(Object obj, int node, DOM dom) {
/*  153 */     if (obj instanceof DTMAxisIterator) {
/*  154 */       return dom.getStringValueX(((DTMAxisIterator)obj).reset().next());
/*      */     }
/*  156 */     if (obj instanceof Node) {
/*  157 */       return dom.getStringValueX(((Node)obj).node);
/*      */     }
/*  159 */     if (obj instanceof DOM)
/*      */     {
/*      */ 
/*      */       
/*  163 */       return ((DOM)obj).getStringValue();
/*      */     }
/*  165 */     if (obj instanceof Double) {
/*  166 */       Double d = (Double)obj;
/*  167 */       String result = d.toString();
/*  168 */       int length = result.length();
/*  169 */       if (result.charAt(length - 2) == '.' && result
/*  170 */         .charAt(length - 1) == '0') {
/*  171 */         return result.substring(0, length - 2);
/*      */       }
/*  173 */       return result;
/*      */     } 
/*      */     
/*  176 */     return (obj != null) ? obj.toString() : "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double numberF(int node, DOM dom) {
/*  184 */     return stringToReal(dom.getStringValueX(node));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double numberF(Object obj, DOM dom) {
/*  191 */     if (obj instanceof Double) {
/*  192 */       return ((Double)obj).doubleValue();
/*      */     }
/*  194 */     if (obj instanceof Integer) {
/*  195 */       return ((Integer)obj).doubleValue();
/*      */     }
/*  197 */     if (obj instanceof Boolean) {
/*  198 */       return ((Boolean)obj).booleanValue() ? 1.0D : 0.0D;
/*      */     }
/*  200 */     if (obj instanceof String) {
/*  201 */       return stringToReal((String)obj);
/*      */     }
/*  203 */     if (obj instanceof DTMAxisIterator) {
/*  204 */       DTMAxisIterator iter = (DTMAxisIterator)obj;
/*  205 */       return stringToReal(dom.getStringValueX(iter.reset().next()));
/*      */     } 
/*  207 */     if (obj instanceof Node) {
/*  208 */       return stringToReal(dom.getStringValueX(((Node)obj).node));
/*      */     }
/*  210 */     if (obj instanceof DOM) {
/*  211 */       return stringToReal(((DOM)obj).getStringValue());
/*      */     }
/*      */     
/*  214 */     String className = obj.getClass().getName();
/*  215 */     runTimeError("INVALID_ARGUMENT_ERR", className, "number()");
/*  216 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double roundF(double d) {
/*  224 */     return (d < -0.5D || d > 0.0D) ? Math.floor(d + 0.5D) : ((d == 0.0D) ? d : (
/*  225 */       Double.isNaN(d) ? Double.NaN : -0.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean booleanF(Object obj) {
/*  232 */     if (obj instanceof Double) {
/*  233 */       double temp = ((Double)obj).doubleValue();
/*  234 */       return (temp != 0.0D && !Double.isNaN(temp));
/*      */     } 
/*  236 */     if (obj instanceof Integer) {
/*  237 */       return (((Integer)obj).doubleValue() != 0.0D);
/*      */     }
/*  239 */     if (obj instanceof Boolean) {
/*  240 */       return ((Boolean)obj).booleanValue();
/*      */     }
/*  242 */     if (obj instanceof String) {
/*  243 */       return !((String)obj).equals("");
/*      */     }
/*  245 */     if (obj instanceof DTMAxisIterator) {
/*  246 */       DTMAxisIterator iter = (DTMAxisIterator)obj;
/*  247 */       return (iter.reset().next() != -1);
/*      */     } 
/*  249 */     if (obj instanceof Node) {
/*  250 */       return true;
/*      */     }
/*  252 */     if (obj instanceof DOM) {
/*  253 */       String temp = ((DOM)obj).getStringValue();
/*  254 */       return !temp.equals("");
/*      */     } 
/*      */     
/*  257 */     String className = obj.getClass().getName();
/*  258 */     runTimeError("INVALID_ARGUMENT_ERR", className, "boolean()");
/*      */     
/*  260 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String substringF(String value, double start) {
/*  268 */     if (Double.isNaN(start)) {
/*  269 */       return "";
/*      */     }
/*  271 */     int strlen = getStringLength(value);
/*  272 */     int istart = (int)Math.round(start) - 1;
/*      */     
/*  274 */     if (istart > strlen)
/*  275 */       return ""; 
/*  276 */     if (istart < 1)
/*  277 */       istart = 0; 
/*      */     try {
/*  279 */       istart = value.offsetByCodePoints(0, istart);
/*  280 */       return value.substring(istart);
/*  281 */     } catch (IndexOutOfBoundsException e) {
/*  282 */       runTimeError("RUN_TIME_INTERNAL_ERR", "substring()");
/*  283 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String substringF(String value, double start, double length) {
/*      */     int isum;
/*  292 */     if (Double.isInfinite(start) || 
/*  293 */       Double.isNaN(start) || 
/*  294 */       Double.isNaN(length) || length < 0.0D)
/*      */     {
/*  296 */       return "";
/*      */     }
/*  298 */     int istart = (int)Math.round(start) - 1;
/*  299 */     int ilength = (int)Math.round(length);
/*      */     
/*  301 */     if (Double.isInfinite(length)) {
/*  302 */       isum = Integer.MAX_VALUE;
/*      */     } else {
/*  304 */       isum = istart + ilength;
/*      */     } 
/*  306 */     int strlen = getStringLength(value);
/*  307 */     if (isum < 0 || istart > strlen) {
/*  308 */       return "";
/*      */     }
/*  310 */     if (istart < 0) {
/*  311 */       ilength += istart;
/*  312 */       istart = 0;
/*      */     } 
/*      */     
/*      */     try {
/*  316 */       istart = value.offsetByCodePoints(0, istart);
/*  317 */       if (isum > strlen) {
/*  318 */         return value.substring(istart);
/*      */       }
/*  320 */       int offset = value.offsetByCodePoints(istart, ilength);
/*  321 */       return value.substring(istart, offset);
/*      */     }
/*  323 */     catch (IndexOutOfBoundsException e) {
/*  324 */       runTimeError("RUN_TIME_INTERNAL_ERR", "substring()");
/*  325 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String substring_afterF(String value, String substring) {
/*  333 */     int index = value.indexOf(substring);
/*  334 */     if (index >= 0) {
/*  335 */       return value.substring(index + substring.length());
/*      */     }
/*  337 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String substring_beforeF(String value, String substring) {
/*  344 */     int index = value.indexOf(substring);
/*  345 */     if (index >= 0) {
/*  346 */       return value.substring(0, index);
/*      */     }
/*  348 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String translateF(String value, String from, String to) {
/*  355 */     int tol = to.length();
/*  356 */     int froml = from.length();
/*  357 */     int valuel = value.length();
/*      */     
/*  359 */     StringBuilder result = threadLocalStringBuilder.get();
/*  360 */     result.setLength(0);
/*  361 */     for (int i = 0; i < valuel; i++) {
/*  362 */       char ch = value.charAt(i); int j;
/*  363 */       for (j = 0; j < froml; j++) {
/*  364 */         if (ch == from.charAt(j)) {
/*  365 */           if (j < tol)
/*  366 */             result.append(to.charAt(j)); 
/*      */           break;
/*      */         } 
/*      */       } 
/*  370 */       if (j == froml)
/*  371 */         result.append(ch); 
/*      */     } 
/*  373 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize_spaceF(int node, DOM dom) {
/*  380 */     return normalize_spaceF(dom.getStringValueX(node));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize_spaceF(String value) {
/*  387 */     int i = 0, n = value.length();
/*  388 */     StringBuilder result = threadLocalStringBuilder.get();
/*  389 */     result.setLength(0);
/*      */     
/*  391 */     while (i < n && isWhiteSpace(value.charAt(i))) {
/*  392 */       i++;
/*      */     }
/*      */     while (true) {
/*  395 */       if (i < n && !isWhiteSpace(value.charAt(i))) {
/*  396 */         result.append(value.charAt(i++)); continue;
/*      */       } 
/*  398 */       if (i == n)
/*      */         break; 
/*  400 */       while (i < n && isWhiteSpace(value.charAt(i))) {
/*  401 */         i++;
/*      */       }
/*  403 */       if (i < n)
/*  404 */         result.append(' '); 
/*      */     } 
/*  406 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String generate_idF(int node) {
/*  413 */     if (node > 0)
/*      */     {
/*  415 */       return "N" + node;
/*      */     }
/*      */     
/*  418 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalName(String value) {
/*  425 */     int idx = value.lastIndexOf(':');
/*  426 */     if (idx >= 0) value = value.substring(idx + 1); 
/*  427 */     idx = value.lastIndexOf('@');
/*  428 */     if (idx >= 0) value = value.substring(idx + 1); 
/*  429 */     return value;
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
/*      */   public static void unresolved_externalF(String name) {
/*  442 */     runTimeError("EXTERNAL_FUNC_ERR", name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unallowed_extension_functionF(String name) {
/*  450 */     runTimeError("UNALLOWED_EXTENSION_FUNCTION_ERR", name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unallowed_extension_elementF(String name) {
/*  458 */     runTimeError("UNALLOWED_EXTENSION_ELEMENT_ERR", name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unsupported_ElementF(String qname, boolean isExtension) {
/*  469 */     if (isExtension) {
/*  470 */       runTimeError("UNSUPPORTED_EXT_ERR", qname);
/*      */     } else {
/*  472 */       runTimeError("UNSUPPORTED_XSL_ERR", qname);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String namespace_uriF(DTMAxisIterator iter, DOM dom) {
/*  479 */     return namespace_uriF(iter.next(), dom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String system_propertyF(String name) {
/*  486 */     if (name.equals("xsl:version"))
/*  487 */       return "1.0"; 
/*  488 */     if (name.equals("xsl:vendor"))
/*  489 */       return "Apache Software Foundation (Xalan XSLTC)"; 
/*  490 */     if (name.equals("xsl:vendor-url")) {
/*  491 */       return "http://xml.apache.org/xalan-j";
/*      */     }
/*  493 */     runTimeError("INVALID_ARGUMENT_ERR", name, "system-property()");
/*  494 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String namespace_uriF(int node, DOM dom) {
/*  501 */     String value = dom.getNodeName(node);
/*  502 */     int colon = value.lastIndexOf(':');
/*  503 */     if (colon >= 0) {
/*  504 */       return value.substring(0, colon);
/*      */     }
/*  506 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String objectTypeF(Object obj) {
/*  516 */     if (obj instanceof String)
/*  517 */       return "string"; 
/*  518 */     if (obj instanceof Boolean)
/*  519 */       return "boolean"; 
/*  520 */     if (obj instanceof Number)
/*  521 */       return "number"; 
/*  522 */     if (obj instanceof DOM)
/*  523 */       return "RTF"; 
/*  524 */     if (obj instanceof DTMAxisIterator) {
/*  525 */       return "node-set";
/*      */     }
/*  527 */     return "unknown";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DTMAxisIterator nodesetF(Object obj) {
/*  534 */     if (obj instanceof DOM) {
/*      */       
/*  536 */       DOM dom = (DOM)obj;
/*  537 */       return new SingletonIterator(dom.getDocument(), true);
/*      */     } 
/*  539 */     if (obj instanceof DTMAxisIterator) {
/*  540 */       return (DTMAxisIterator)obj;
/*      */     }
/*      */     
/*  543 */     String className = obj.getClass().getName();
/*  544 */     runTimeError("DATA_CONVERSION_ERR", "node-set", className);
/*  545 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isWhiteSpace(char ch) {
/*  552 */     return (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r');
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean compareStrings(String lstring, String rstring, int op, DOM dom) {
/*  557 */     switch (op) {
/*      */       case 0:
/*  559 */         return lstring.equals(rstring);
/*      */       
/*      */       case 1:
/*  562 */         return !lstring.equals(rstring);
/*      */       
/*      */       case 2:
/*  565 */         return (numberF(lstring, dom) > numberF(rstring, dom));
/*      */       
/*      */       case 3:
/*  568 */         return (numberF(lstring, dom) < numberF(rstring, dom));
/*      */       
/*      */       case 4:
/*  571 */         return (numberF(lstring, dom) >= numberF(rstring, dom));
/*      */       
/*      */       case 5:
/*  574 */         return (numberF(lstring, dom) <= numberF(rstring, dom));
/*      */     } 
/*      */     
/*  577 */     runTimeError("RUN_TIME_INTERNAL_ERR", "compare()");
/*  578 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compare(DTMAxisIterator left, DTMAxisIterator right, int op, DOM dom) {
/*  588 */     left.reset();
/*      */     int lnode;
/*  590 */     while ((lnode = left.next()) != -1) {
/*  591 */       String lvalue = dom.getStringValueX(lnode);
/*      */ 
/*      */       
/*  594 */       right.reset(); int rnode;
/*  595 */       while ((rnode = right.next()) != -1) {
/*      */         
/*  597 */         if (lnode == rnode) {
/*  598 */           if (op == 0)
/*  599 */             return true; 
/*  600 */           if (op == 1) {
/*      */             continue;
/*      */           }
/*      */         } 
/*  604 */         if (compareStrings(lvalue, dom.getStringValueX(rnode), op, dom))
/*      */         {
/*  606 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  610 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compare(int node, DTMAxisIterator iterator, int op, DOM dom) {
/*      */     int rnode;
/*  620 */     switch (op) {
/*      */       case 0:
/*  622 */         rnode = iterator.next();
/*  623 */         if (rnode != -1) {
/*  624 */           String value = dom.getStringValueX(node);
/*      */           do {
/*  626 */             if (node == rnode || value
/*  627 */               .equals(dom.getStringValueX(rnode))) {
/*  628 */               return true;
/*      */             }
/*  630 */           } while ((rnode = iterator.next()) != -1);
/*      */         } 
/*      */         break;
/*      */       case 1:
/*  634 */         rnode = iterator.next();
/*  635 */         if (rnode != -1) {
/*  636 */           String value = dom.getStringValueX(node);
/*      */           do {
/*  638 */             if (node != rnode && 
/*  639 */               !value.equals(dom.getStringValueX(rnode))) {
/*  640 */               return true;
/*      */             }
/*  642 */           } while ((rnode = iterator.next()) != -1);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 3:
/*  647 */         while ((rnode = iterator.next()) != -1) {
/*  648 */           if (rnode > node) return true;
/*      */         
/*      */         } 
/*      */         break;
/*      */       case 2:
/*  653 */         while ((rnode = iterator.next()) != -1) {
/*  654 */           if (rnode < node) return true; 
/*      */         } 
/*      */         break;
/*      */     } 
/*  658 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compare(DTMAxisIterator left, double rnumber, int op, DOM dom) {
/*      */     int node;
/*  669 */     switch (op)
/*      */     { case 0:
/*  671 */         while ((node = left.next()) != -1) {
/*  672 */           if (numberF(dom.getStringValueX(node), dom) == rnumber) {
/*  673 */             return true;
/*      */           }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  716 */         return false;case 1: while ((node = left.next()) != -1) { if (numberF(dom.getStringValueX(node), dom) != rnumber) return true;  }  return false;case 2: while ((node = left.next()) != -1) { if (numberF(dom.getStringValueX(node), dom) > rnumber) return true;  }  return false;case 3: while ((node = left.next()) != -1) { if (numberF(dom.getStringValueX(node), dom) < rnumber) return true;  }  return false;case 4: while ((node = left.next()) != -1) { if (numberF(dom.getStringValueX(node), dom) >= rnumber) return true;  }  return false;case 5: while ((node = left.next()) != -1) { if (numberF(dom.getStringValueX(node), dom) <= rnumber) return true;  }  return false; }  runTimeError("RUN_TIME_INTERNAL_ERR", "compare()"); return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compare(DTMAxisIterator left, String rstring, int op, DOM dom) {
/*      */     int node;
/*  726 */     while ((node = left.next()) != -1) {
/*  727 */       if (compareStrings(dom.getStringValueX(node), rstring, op, dom)) {
/*  728 */         return true;
/*      */       }
/*      */     } 
/*  731 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compare(Object left, Object right, int op, DOM dom) {
/*  738 */     boolean result = false;
/*  739 */     boolean hasSimpleArgs = (hasSimpleType(left) && hasSimpleType(right));
/*      */     
/*  741 */     if (op != 0 && op != 1) {
/*      */       
/*  743 */       if (left instanceof Node || right instanceof Node) {
/*  744 */         if (left instanceof Boolean) {
/*  745 */           right = new Boolean(booleanF(right));
/*  746 */           hasSimpleArgs = true;
/*      */         } 
/*  748 */         if (right instanceof Boolean) {
/*  749 */           left = new Boolean(booleanF(left));
/*  750 */           hasSimpleArgs = true;
/*      */         } 
/*      */       } 
/*      */       
/*  754 */       if (hasSimpleArgs) {
/*  755 */         switch (op) {
/*      */           case 2:
/*  757 */             return (numberF(left, dom) > numberF(right, dom));
/*      */           
/*      */           case 3:
/*  760 */             return (numberF(left, dom) < numberF(right, dom));
/*      */           
/*      */           case 4:
/*  763 */             return (numberF(left, dom) >= numberF(right, dom));
/*      */           
/*      */           case 5:
/*  766 */             return (numberF(left, dom) <= numberF(right, dom));
/*      */         } 
/*      */         
/*  769 */         runTimeError("RUN_TIME_INTERNAL_ERR", "compare()");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  775 */     if (hasSimpleArgs) {
/*  776 */       if (left instanceof Boolean || right instanceof Boolean) {
/*  777 */         result = (booleanF(left) == booleanF(right));
/*      */       }
/*  779 */       else if (left instanceof Double || right instanceof Double || left instanceof Integer || right instanceof Integer) {
/*      */         
/*  781 */         result = (numberF(left, dom) == numberF(right, dom));
/*      */       } else {
/*      */         
/*  784 */         result = stringF(left, dom).equals(stringF(right, dom));
/*      */       } 
/*      */       
/*  787 */       if (op == 1) {
/*  788 */         result = !result;
/*      */       }
/*      */     } else {
/*      */       
/*  792 */       if (left instanceof Node) {
/*  793 */         left = new SingletonIterator(((Node)left).node);
/*      */       }
/*  795 */       if (right instanceof Node) {
/*  796 */         right = new SingletonIterator(((Node)right).node);
/*      */       }
/*      */       
/*  799 */       if (hasSimpleType(left) || (left instanceof DOM && right instanceof DTMAxisIterator)) {
/*      */ 
/*      */         
/*  802 */         Object temp = right; right = left; left = temp;
/*  803 */         op = Operators.swapOp(op);
/*      */       } 
/*      */       
/*  806 */       if (left instanceof DOM) {
/*  807 */         if (right instanceof Boolean) {
/*  808 */           result = ((Boolean)right).booleanValue();
/*  809 */           return (result == ((op == 0)));
/*      */         } 
/*      */         
/*  812 */         String sleft = ((DOM)left).getStringValue();
/*      */         
/*  814 */         if (right instanceof Number) {
/*      */           
/*  816 */           result = (((Number)right).doubleValue() == stringToReal(sleft));
/*      */         }
/*  818 */         else if (right instanceof String) {
/*  819 */           result = sleft.equals(right);
/*      */         }
/*  821 */         else if (right instanceof DOM) {
/*  822 */           result = sleft.equals(((DOM)right).getStringValue());
/*      */         } 
/*      */         
/*  825 */         if (op == 1) {
/*  826 */           result = !result;
/*      */         }
/*  828 */         return result;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  833 */       DTMAxisIterator iter = ((DTMAxisIterator)left).reset();
/*      */       
/*  835 */       if (right instanceof DTMAxisIterator) {
/*  836 */         result = compare(iter, (DTMAxisIterator)right, op, dom);
/*      */       }
/*  838 */       else if (right instanceof String) {
/*  839 */         result = compare(iter, (String)right, op, dom);
/*      */       }
/*  841 */       else if (right instanceof Number) {
/*  842 */         double temp = ((Number)right).doubleValue();
/*  843 */         result = compare(iter, temp, op, dom);
/*      */       }
/*  845 */       else if (right instanceof Boolean) {
/*  846 */         boolean temp = ((Boolean)right).booleanValue();
/*  847 */         result = (((iter.reset().next() != -1)) == temp);
/*      */       }
/*  849 */       else if (right instanceof DOM) {
/*  850 */         result = compare(iter, ((DOM)right).getStringValue(), op, dom);
/*      */       } else {
/*      */         
/*  853 */         if (right == null) {
/*  854 */           return false;
/*      */         }
/*      */         
/*  857 */         String className = right.getClass().getName();
/*  858 */         runTimeError("INVALID_ARGUMENT_ERR", className, "compare()");
/*      */       } 
/*      */     } 
/*  861 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean testLanguage(String testLang, DOM dom, int node) {
/*  869 */     String nodeLang = dom.getLanguage(node);
/*  870 */     if (nodeLang == null) {
/*  871 */       return false;
/*      */     }
/*  873 */     nodeLang = nodeLang.toLowerCase();
/*      */ 
/*      */     
/*  876 */     testLang = testLang.toLowerCase();
/*  877 */     if (testLang.length() == 2) {
/*  878 */       return nodeLang.startsWith(testLang);
/*      */     }
/*      */     
/*  881 */     return nodeLang.equals(testLang);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean hasSimpleType(Object obj) {
/*  886 */     return (obj instanceof Boolean || obj instanceof Double || obj instanceof Integer || obj instanceof String || obj instanceof Node || obj instanceof DOM);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double stringToReal(String s) {
/*      */     try {
/*  896 */       return Double.valueOf(s).doubleValue();
/*      */     }
/*  898 */     catch (NumberFormatException e) {
/*  899 */       return Double.NaN;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int stringToInt(String s) {
/*      */     try {
/*  908 */       return Integer.parseInt(s);
/*      */     }
/*  910 */     catch (NumberFormatException e) {
/*  911 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  919 */   private static String defaultPattern = ""; private static FieldPosition _fieldPosition; private static char[] _characterArray; private static final ThreadLocal<AtomicInteger> threadLocalPrefixIndex; public static final String RUN_TIME_INTERNAL_ERR = "RUN_TIME_INTERNAL_ERR"; public static final String RUN_TIME_COPY_ERR = "RUN_TIME_COPY_ERR"; public static final String DATA_CONVERSION_ERR = "DATA_CONVERSION_ERR"; public static final String EXTERNAL_FUNC_ERR = "EXTERNAL_FUNC_ERR"; public static final String EQUALITY_EXPR_ERR = "EQUALITY_EXPR_ERR"; public static final String INVALID_ARGUMENT_ERR = "INVALID_ARGUMENT_ERR"; public static final String FORMAT_NUMBER_ERR = "FORMAT_NUMBER_ERR"; public static final String ITERATOR_CLONE_ERR = "ITERATOR_CLONE_ERR"; public static final String AXIS_SUPPORT_ERR = "AXIS_SUPPORT_ERR"; public static final String TYPED_AXIS_SUPPORT_ERR = "TYPED_AXIS_SUPPORT_ERR"; public static final String STRAY_ATTRIBUTE_ERR = "STRAY_ATTRIBUTE_ERR"; public static final String STRAY_NAMESPACE_ERR = "STRAY_NAMESPACE_ERR"; public static final String NAMESPACE_PREFIX_ERR = "NAMESPACE_PREFIX_ERR"; public static final String DOM_ADAPTER_INIT_ERR = "DOM_ADAPTER_INIT_ERR"; public static final String PARSER_DTD_SUPPORT_ERR = "PARSER_DTD_SUPPORT_ERR"; public static final String NAMESPACES_SUPPORT_ERR = "NAMESPACES_SUPPORT_ERR"; public static final String CANT_RESOLVE_RELATIVE_URI_ERR = "CANT_RESOLVE_RELATIVE_URI_ERR"; public static final String UNSUPPORTED_XSL_ERR = "UNSUPPORTED_XSL_ERR"; public static final String UNSUPPORTED_EXT_ERR = "UNSUPPORTED_EXT_ERR"; public static final String UNKNOWN_TRANSLET_VERSION_ERR = "UNKNOWN_TRANSLET_VERSION_ERR"; public static final String INVALID_QNAME_ERR = "INVALID_QNAME_ERR"; public static final String INVALID_NCNAME_ERR = "INVALID_NCNAME_ERR"; public static final String UNALLOWED_EXTENSION_FUNCTION_ERR = "UNALLOWED_EXTENSION_FUNCTION_ERR"; public static final String UNALLOWED_EXTENSION_ELEMENT_ERR = "UNALLOWED_EXTENSION_ELEMENT_ERR"; private static ResourceBundle m_bundle;
/*      */   public static final String ERROR_MESSAGES_KEY = "error-messages";
/*      */   
/*  922 */   static { NumberFormat f = NumberFormat.getInstance(Locale.getDefault());
/*  923 */     defaultFormatter = (f instanceof DecimalFormat) ? (DecimalFormat)f : new DecimalFormat();
/*      */ 
/*      */ 
/*      */     
/*  927 */     defaultFormatter.setMaximumFractionDigits(340);
/*  928 */     defaultFormatter.setMinimumFractionDigits(0);
/*  929 */     defaultFormatter.setMinimumIntegerDigits(1);
/*  930 */     defaultFormatter.setGroupingUsed(false);
/*      */ 
/*      */ 
/*      */     
/*  934 */     xpathFormatter = new DecimalFormat("", new DecimalFormatSymbols(Locale.US));
/*      */     
/*  936 */     xpathFormatter.setMaximumFractionDigits(340);
/*  937 */     xpathFormatter.setMinimumFractionDigits(0);
/*  938 */     xpathFormatter.setMinimumIntegerDigits(1);
/*  939 */     xpathFormatter.setGroupingUsed(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  987 */     _fieldPosition = new FieldPosition(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1385 */     _characterArray = new char[32];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1545 */     threadLocalPrefixIndex = new ThreadLocal<AtomicInteger>()
/*      */       {
/*      */         protected AtomicInteger initialValue()
/*      */         {
/* 1549 */           return new AtomicInteger();
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1604 */     String resource = "com.sun.org.apache.xalan.internal.xsltc.runtime.ErrorMessages";
/* 1605 */     m_bundle = SecuritySupport.getResourceBundle(resource); }
/*      */   public static String realToString(double d) { double m = Math.abs(d); if (m >= 0.001D && m < 1.0E7D) { String str = Double.toString(d); int length = str.length(); if (str.charAt(length - 2) == '.' && str.charAt(length - 1) == '0') return str.substring(0, length - 2);  return str; }  if (Double.isNaN(d) || Double.isInfinite(d)) return Double.toString(d);  d += 0.0D; StringBuffer result = threadLocalStringBuffer.get(); result.setLength(0); xpathFormatter.format(d, result, _fieldPosition); return result.toString(); }
/*      */   public static int realToInt(double d) { return (int)d; }
/*      */   public static String formatNumber(double number, String pattern, DecimalFormat formatter) { if (formatter == null) formatter = defaultFormatter;  try { StringBuffer result = threadLocalStringBuffer.get(); result.setLength(0); if (pattern != defaultPattern) formatter.applyLocalizedPattern(pattern);  formatter.format(number, result, _fieldPosition); return result.toString(); } catch (IllegalArgumentException e) { runTimeError("FORMAT_NUMBER_ERR", Double.toString(number), pattern); return ""; }  }
/*      */   public static DTMAxisIterator referenceToNodeSet(Object obj) { if (obj instanceof Node) return new SingletonIterator(((Node)obj).node);  if (obj instanceof DTMAxisIterator) return ((DTMAxisIterator)obj).cloneIterator().reset();  String className = obj.getClass().getName(); runTimeError("DATA_CONVERSION_ERR", className, "node-set"); return null; }
/*      */   public static NodeList referenceToNodeList(Object obj, DOM dom) { if (obj instanceof Node || obj instanceof DTMAxisIterator) { DTMAxisIterator iter = referenceToNodeSet(obj); return dom.makeNodeList(iter); }  if (obj instanceof DOM) { dom = (DOM)obj; return dom.makeNodeList(0); }  String className = obj.getClass().getName(); runTimeError("DATA_CONVERSION_ERR", className, "org.w3c.dom.NodeList"); return null; } public static Node referenceToNode(Object obj, DOM dom) { if (obj instanceof Node || obj instanceof DTMAxisIterator) { DTMAxisIterator iter = referenceToNodeSet(obj); return dom.makeNode(iter); }  if (obj instanceof DOM) { dom = (DOM)obj; DTMAxisIterator iter = dom.getChildren(0); return dom.makeNode(iter); }  String className = obj.getClass().getName(); runTimeError("DATA_CONVERSION_ERR", className, "org.w3c.dom.Node"); return null; } public static long referenceToLong(Object obj) { if (obj instanceof Number) return ((Number)obj).longValue();  String className = obj.getClass().getName(); runTimeError("DATA_CONVERSION_ERR", className, long.class); return 0L; } public static double referenceToDouble(Object obj) { if (obj instanceof Number) return ((Number)obj).doubleValue();  String className = obj.getClass().getName(); runTimeError("DATA_CONVERSION_ERR", className, double.class); return 0.0D; } public static boolean referenceToBoolean(Object obj) { if (obj instanceof Boolean) return ((Boolean)obj).booleanValue();  String className = obj.getClass().getName(); runTimeError("DATA_CONVERSION_ERR", className, boolean.class); return false; } public static String referenceToString(Object obj, DOM dom) { if (obj instanceof String) return (String)obj;  if (obj instanceof DTMAxisIterator) return dom.getStringValueX(((DTMAxisIterator)obj).reset().next());  if (obj instanceof Node) return dom.getStringValueX(((Node)obj).node);  if (obj instanceof DOM) return ((DOM)obj).getStringValue();  String className = obj.getClass().getName(); runTimeError("DATA_CONVERSION_ERR", className, String.class); return null; } public static DTMAxisIterator node2Iterator(Node node, Translet translet, DOM dom) { final Node inNode = node; NodeList nodelist = new NodeList() {
/*      */         public int getLength() { return 1; } public Node item(int index) { if (index == 0) return inNode;  return null; }
/* 1612 */       }; return nodeList2Iterator(nodelist, translet, dom); } public static void runTimeError(String code) { throw new RuntimeException(m_bundle.getString(code)); }
/*      */   private static DTMAxisIterator nodeList2IteratorUsingHandleFromNode(NodeList nodeList, Translet translet, DOM dom) { int n = nodeList.getLength(); int[] dtmHandles = new int[n]; DTMManager dtmManager = null; if (dom instanceof MultiDOM) dtmManager = ((MultiDOM)dom).getDTMManager();  for (int i = 0; i < n; i++) { int handle; Node node = nodeList.item(i); if (dtmManager != null) { handle = dtmManager.getDTMHandleFromNode(node); } else if (node instanceof DTMNodeProxy && ((DTMNodeProxy)node).getDTM() == dom) { handle = ((DTMNodeProxy)node).getDTMNodeNumber(); } else { runTimeError("RUN_TIME_INTERNAL_ERR", "need MultiDOM"); return null; }  dtmHandles[i] = handle; System.out.println("Node " + i + " has handle 0x" + Integer.toString(handle, 16)); }  return new ArrayNodeListIterator(dtmHandles); }
/*      */   public static DTMAxisIterator nodeList2Iterator(NodeList nodeList, Translet translet, DOM dom) { int n = 0; Document doc = null; DTMManager dtmManager = null; int[] proxyNodes = new int[nodeList.getLength()]; if (dom instanceof MultiDOM) dtmManager = ((MultiDOM)dom).getDTMManager();  for (int i = 0; i < nodeList.getLength(); i++) { Element mid; Node node = nodeList.item(i); if (node instanceof DTMNodeProxy) { DTMNodeProxy proxy = (DTMNodeProxy)node; DTM nodeDTM = proxy.getDTM(); int handle = proxy.getDTMNodeNumber(); boolean isOurDOM = (nodeDTM == dom); if (!isOurDOM && dtmManager != null) try { isOurDOM = (nodeDTM == dtmManager.getDTM(handle)); } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}  if (isOurDOM) { proxyNodes[i] = handle; n++; continue; }  }  proxyNodes[i] = -1; int nodeType = node.getNodeType(); if (doc == null) { if (!(dom instanceof MultiDOM)) { runTimeError("RUN_TIME_INTERNAL_ERR", "need MultiDOM"); return null; }  try { AbstractTranslet at = (AbstractTranslet)translet; doc = at.newDocument("", "__top__"); } catch (ParserConfigurationException e) { runTimeError("RUN_TIME_INTERNAL_ERR", e.getMessage()); return null; }  }  switch (nodeType) { case 1: case 3: case 4: case 5: case 7: case 8: mid = doc.createElementNS(null, "__dummy__"); mid.appendChild(doc.importNode(node, true)); doc.getDocumentElement().appendChild(mid); n++; break;case 2: mid = doc.createElementNS(null, "__dummy__"); mid.setAttributeNodeNS((Attr)doc.importNode(node, true)); doc.getDocumentElement().appendChild(mid); n++; break;default: runTimeError("RUN_TIME_INTERNAL_ERR", "Don't know how to convert node type " + nodeType); break; }  continue; }  DTMAxisIterator iter = null, childIter = null, attrIter = null; if (doc != null) { MultiDOM multiDOM = (MultiDOM)dom; DOM idom = (DOM)dtmManager.getDTM(new DOMSource(doc), false, null, true, false); DOMAdapter domAdapter = new DOMAdapter(idom, translet.getNamesArray(), translet.getUrisArray(), translet.getTypesArray(), translet.getNamespaceArray()); multiDOM.addDOMAdapter(domAdapter); DTMAxisIterator iter1 = idom.getAxisIterator(3); DTMAxisIterator iter2 = idom.getAxisIterator(3); iter = new AbsoluteIterator(new StepIterator(iter1, iter2)); iter.setStartNode(0); childIter = idom.getAxisIterator(3); attrIter = idom.getAxisIterator(2); }  int[] dtmHandles = new int[n]; n = 0; for (int j = 0; j < nodeList.getLength(); j++) { if (proxyNodes[j] != -1) { dtmHandles[n++] = proxyNodes[j]; } else { Node node = nodeList.item(j); DTMAxisIterator iter3 = null; int nodeType = node.getNodeType(); switch (nodeType) { case 1: case 3: case 4: case 5: case 7: case 8: iter3 = childIter; break;case 2: iter3 = attrIter; break;default: throw new InternalRuntimeError("Mismatched cases"); }  if (iter3 != null) { iter3.setStartNode(iter.next()); dtmHandles[n] = iter3.next(); if (dtmHandles[n] == -1) throw new InternalRuntimeError("Expected element missing at " + j);  if (iter3.next() != -1) throw new InternalRuntimeError("Too many elements at " + j);  n++; }  }  }  if (n != dtmHandles.length) throw new InternalRuntimeError("Nodes lost in second pass");  return new ArrayNodeListIterator(dtmHandles); }
/*      */   public static DOM referenceToResultTree(Object obj) { try { return (DOM)obj; } catch (IllegalArgumentException e) { String className = obj.getClass().getName(); runTimeError("DATA_CONVERSION_ERR", "reference", className); return null; }  }
/* 1616 */   public static DTMAxisIterator getSingleNode(DTMAxisIterator iterator) { int node = iterator.next(); return new SingletonIterator(node); } public static void copy(Object obj, SerializationHandler handler, int node, DOM dom) { try { if (obj instanceof DTMAxisIterator) { DTMAxisIterator iter = (DTMAxisIterator)obj; dom.copy(iter.reset(), handler); } else if (obj instanceof Node) { dom.copy(((Node)obj).node, handler); } else if (obj instanceof DOM) { DOM newDom = (DOM)obj; newDom.copy(newDom.getDocument(), handler); } else { String string = obj.toString(); int length = string.length(); if (length > _characterArray.length) _characterArray = new char[length];  string.getChars(0, length, _characterArray, 0); handler.characters(_characterArray, 0, length); }  } catch (SAXException e) { runTimeError("RUN_TIME_COPY_ERR"); }  } public static void checkAttribQName(String name) { int firstOccur = name.indexOf(':'); int lastOccur = name.lastIndexOf(':'); String localName = name.substring(lastOccur + 1); if (firstOccur > 0) { String newPrefix = name.substring(0, firstOccur); if (firstOccur != lastOccur) { String oriPrefix = name.substring(firstOccur + 1, lastOccur); if (!XML11Char.isXML11ValidNCName(oriPrefix)) runTimeError("INVALID_QNAME_ERR", oriPrefix + ":" + localName);  }  if (!XML11Char.isXML11ValidNCName(newPrefix)) runTimeError("INVALID_QNAME_ERR", newPrefix + ":" + localName);  }  if (!XML11Char.isXML11ValidNCName(localName) || localName.equals("xmlns")) runTimeError("INVALID_QNAME_ERR", localName);  } public static void checkNCName(String name) { if (!XML11Char.isXML11ValidNCName(name)) runTimeError("INVALID_NCNAME_ERR", name);  } public static void checkQName(String name) { if (!XML11Char.isXML11ValidQName(name)) runTimeError("INVALID_QNAME_ERR", name);  } public static String startXslElement(String qname, String namespace, SerializationHandler handler, DOM dom, int node) { try { int index = qname.indexOf(':'); if (index > 0) { String prefix = qname.substring(0, index); if (namespace == null || namespace.length() == 0) try { namespace = dom.lookupNamespace(node, prefix); } catch (RuntimeException e) { handler.flushPending(); NamespaceMappings nm = handler.getNamespaceMappings(); namespace = nm.lookupNamespace(prefix); if (namespace == null) runTimeError("NAMESPACE_PREFIX_ERR", prefix);  }   handler.startElement(namespace, qname.substring(index + 1), qname); handler.namespaceAfterStartElement(prefix, namespace); } else if (namespace != null && namespace.length() > 0) { String prefix = generatePrefix(); qname = prefix + ':' + qname; handler.startElement(namespace, qname, qname); handler.namespaceAfterStartElement(prefix, namespace); } else { handler.startElement((String)null, (String)null, qname); }  } catch (SAXException e) { throw new RuntimeException(e.getMessage()); }  return qname; } public static String getPrefix(String qname) { int index = qname.indexOf(':'); return (index > 0) ? qname.substring(0, index) : null; } public static String generatePrefix() { return "ns" + ((AtomicInteger)threadLocalPrefixIndex.get()).getAndIncrement(); } public static void resetPrefixIndex() { ((AtomicInteger)threadLocalPrefixIndex.get()).set(0); } public static void runTimeError(String code, Object[] args) { String message = MessageFormat.format(m_bundle.getString(code), args);
/*      */     
/* 1618 */     throw new RuntimeException(message); }
/*      */ 
/*      */   
/*      */   public static void runTimeError(String code, Object arg0) {
/* 1622 */     runTimeError(code, new Object[] { arg0 });
/*      */   }
/*      */   
/*      */   public static void runTimeError(String code, Object arg0, Object arg1) {
/* 1626 */     runTimeError(code, new Object[] { arg0, arg1 });
/*      */   }
/*      */   
/*      */   public static void consoleOutput(String msg) {
/* 1630 */     System.out.println(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String replace(String base, char ch, String str) {
/* 1637 */     return (base.indexOf(ch) < 0) ? base : 
/* 1638 */       replace(base, String.valueOf(ch), new String[] { str });
/*      */   }
/*      */   
/*      */   public static String replace(String base, String delim, String[] str) {
/* 1642 */     int len = base.length();
/* 1643 */     StringBuilder result = threadLocalStringBuilder.get();
/* 1644 */     result.setLength(0);
/*      */     
/* 1646 */     for (int i = 0; i < len; i++) {
/* 1647 */       char ch = base.charAt(i);
/* 1648 */       int k = delim.indexOf(ch);
/*      */       
/* 1650 */       if (k >= 0) {
/* 1651 */         result.append(str[k]);
/*      */       } else {
/*      */         
/* 1654 */         result.append(ch);
/*      */       } 
/*      */     } 
/* 1657 */     return result.toString();
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
/*      */   public static String mapQNameToJavaName(String base) {
/* 1671 */     return replace(base, ".-:/{}?#%*", new String[] { "$dot$", "$dash$", "$colon$", "$slash$", "", "$colon$", "$ques$", "$hash$", "$per$", "$aster$" });
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
/*      */   public static int getStringLength(String str) {
/* 1684 */     return str.codePointCount(0, str.length());
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/runtime/BasisLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */