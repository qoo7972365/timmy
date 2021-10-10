/*      */ package com.sun.org.apache.xml.internal.serializer;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.serializer.utils.Utils;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.io.Writer;
/*      */ import java.util.Properties;
/*      */ import org.xml.sax.Attributes;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ToHTMLStream
/*      */   extends ToStream
/*      */ {
/*      */   protected boolean m_inDTD = false;
/*      */   private boolean m_inBlockElem = false;
/*   63 */   private static final CharInfo m_htmlcharInfo = CharInfo.getCharInfoInternal("com.sun.org.apache.xml.internal.serializer.HTMLEntities", "html");
/*      */ 
/*      */   
/*   66 */   static final Trie m_elementFlags = new Trie();
/*      */   
/*      */   static {
/*   69 */     initTagReference(m_elementFlags);
/*      */   }
/*      */ 
/*      */   
/*      */   static void initTagReference(Trie m_elementFlags) {
/*   74 */     m_elementFlags.put("BASEFONT", new ElemDesc(2));
/*   75 */     m_elementFlags.put("FRAME", new ElemDesc(10));
/*      */ 
/*      */     
/*   78 */     m_elementFlags.put("FRAMESET", new ElemDesc(8));
/*   79 */     m_elementFlags.put("NOFRAMES", new ElemDesc(8));
/*   80 */     m_elementFlags.put("ISINDEX", new ElemDesc(10));
/*      */ 
/*      */     
/*   83 */     m_elementFlags.put("APPLET", new ElemDesc(2097152));
/*      */ 
/*      */     
/*   86 */     m_elementFlags.put("CENTER", new ElemDesc(8));
/*   87 */     m_elementFlags.put("DIR", new ElemDesc(8));
/*   88 */     m_elementFlags.put("MENU", new ElemDesc(8));
/*      */ 
/*      */     
/*   91 */     m_elementFlags.put("TT", new ElemDesc(4096));
/*   92 */     m_elementFlags.put("I", new ElemDesc(4096));
/*   93 */     m_elementFlags.put("B", new ElemDesc(4096));
/*   94 */     m_elementFlags.put("BIG", new ElemDesc(4096));
/*   95 */     m_elementFlags.put("SMALL", new ElemDesc(4096));
/*   96 */     m_elementFlags.put("EM", new ElemDesc(8192));
/*   97 */     m_elementFlags.put("STRONG", new ElemDesc(8192));
/*   98 */     m_elementFlags.put("DFN", new ElemDesc(8192));
/*   99 */     m_elementFlags.put("CODE", new ElemDesc(8192));
/*  100 */     m_elementFlags.put("SAMP", new ElemDesc(8192));
/*  101 */     m_elementFlags.put("KBD", new ElemDesc(8192));
/*  102 */     m_elementFlags.put("VAR", new ElemDesc(8192));
/*  103 */     m_elementFlags.put("CITE", new ElemDesc(8192));
/*  104 */     m_elementFlags.put("ABBR", new ElemDesc(8192));
/*  105 */     m_elementFlags.put("ACRONYM", new ElemDesc(8192));
/*  106 */     m_elementFlags.put("SUP", new ElemDesc(98304));
/*      */ 
/*      */     
/*  109 */     m_elementFlags.put("SUB", new ElemDesc(98304));
/*      */ 
/*      */     
/*  112 */     m_elementFlags.put("SPAN", new ElemDesc(98304));
/*      */ 
/*      */     
/*  115 */     m_elementFlags.put("BDO", new ElemDesc(98304));
/*      */ 
/*      */     
/*  118 */     m_elementFlags.put("BR", new ElemDesc(98314));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  126 */     m_elementFlags.put("BODY", new ElemDesc(8));
/*  127 */     m_elementFlags.put("ADDRESS", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  134 */     m_elementFlags.put("DIV", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  141 */     m_elementFlags.put("A", new ElemDesc(32768));
/*  142 */     m_elementFlags.put("MAP", new ElemDesc(98312));
/*      */ 
/*      */ 
/*      */     
/*  146 */     m_elementFlags.put("AREA", new ElemDesc(10));
/*      */ 
/*      */     
/*  149 */     m_elementFlags.put("LINK", new ElemDesc(131082));
/*      */ 
/*      */ 
/*      */     
/*  153 */     m_elementFlags.put("IMG", new ElemDesc(2195458));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  161 */     m_elementFlags.put("OBJECT", new ElemDesc(2326528));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  169 */     m_elementFlags.put("PARAM", new ElemDesc(2));
/*  170 */     m_elementFlags.put("HR", new ElemDesc(58));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  178 */     m_elementFlags.put("P", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  185 */     m_elementFlags.put("H1", new ElemDesc(262152));
/*      */ 
/*      */     
/*  188 */     m_elementFlags.put("H2", new ElemDesc(262152));
/*      */ 
/*      */     
/*  191 */     m_elementFlags.put("H3", new ElemDesc(262152));
/*      */ 
/*      */     
/*  194 */     m_elementFlags.put("H4", new ElemDesc(262152));
/*      */ 
/*      */     
/*  197 */     m_elementFlags.put("H5", new ElemDesc(262152));
/*      */ 
/*      */     
/*  200 */     m_elementFlags.put("H6", new ElemDesc(262152));
/*      */ 
/*      */     
/*  203 */     m_elementFlags.put("PRE", new ElemDesc(1048584));
/*      */ 
/*      */     
/*  206 */     m_elementFlags.put("Q", new ElemDesc(98304));
/*      */ 
/*      */     
/*  209 */     m_elementFlags.put("BLOCKQUOTE", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  216 */     m_elementFlags.put("INS", new ElemDesc(0));
/*  217 */     m_elementFlags.put("DEL", new ElemDesc(0));
/*  218 */     m_elementFlags.put("DL", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  225 */     m_elementFlags.put("DT", new ElemDesc(8));
/*  226 */     m_elementFlags.put("DD", new ElemDesc(8));
/*  227 */     m_elementFlags.put("OL", new ElemDesc(524296));
/*      */ 
/*      */     
/*  230 */     m_elementFlags.put("UL", new ElemDesc(524296));
/*      */ 
/*      */     
/*  233 */     m_elementFlags.put("LI", new ElemDesc(8));
/*  234 */     m_elementFlags.put("FORM", new ElemDesc(8));
/*  235 */     m_elementFlags.put("LABEL", new ElemDesc(16384));
/*  236 */     m_elementFlags.put("INPUT", new ElemDesc(18434));
/*      */ 
/*      */ 
/*      */     
/*  240 */     m_elementFlags.put("SELECT", new ElemDesc(18432));
/*      */ 
/*      */     
/*  243 */     m_elementFlags.put("OPTGROUP", new ElemDesc(0));
/*  244 */     m_elementFlags.put("OPTION", new ElemDesc(0));
/*  245 */     m_elementFlags.put("TEXTAREA", new ElemDesc(18432));
/*      */ 
/*      */     
/*  248 */     m_elementFlags.put("FIELDSET", new ElemDesc(24));
/*      */ 
/*      */     
/*  251 */     m_elementFlags.put("LEGEND", new ElemDesc(0));
/*  252 */     m_elementFlags.put("BUTTON", new ElemDesc(18432));
/*      */ 
/*      */     
/*  255 */     m_elementFlags.put("TABLE", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  262 */     m_elementFlags.put("CAPTION", new ElemDesc(8));
/*  263 */     m_elementFlags.put("THEAD", new ElemDesc(8));
/*  264 */     m_elementFlags.put("TFOOT", new ElemDesc(8));
/*  265 */     m_elementFlags.put("TBODY", new ElemDesc(8));
/*  266 */     m_elementFlags.put("COLGROUP", new ElemDesc(8));
/*  267 */     m_elementFlags.put("COL", new ElemDesc(10));
/*      */ 
/*      */     
/*  270 */     m_elementFlags.put("TR", new ElemDesc(8));
/*  271 */     m_elementFlags.put("TH", new ElemDesc(0));
/*  272 */     m_elementFlags.put("TD", new ElemDesc(0));
/*  273 */     m_elementFlags.put("HEAD", new ElemDesc(4194312));
/*      */ 
/*      */     
/*  276 */     m_elementFlags.put("TITLE", new ElemDesc(8));
/*  277 */     m_elementFlags.put("BASE", new ElemDesc(10));
/*      */ 
/*      */     
/*  280 */     m_elementFlags.put("META", new ElemDesc(131082));
/*      */ 
/*      */ 
/*      */     
/*  284 */     m_elementFlags.put("STYLE", new ElemDesc(131336));
/*      */ 
/*      */ 
/*      */     
/*  288 */     m_elementFlags.put("SCRIPT", new ElemDesc(229632));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  296 */     m_elementFlags.put("NOSCRIPT", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  303 */     m_elementFlags.put("HTML", new ElemDesc(8));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  308 */     m_elementFlags.put("FONT", new ElemDesc(4096));
/*      */ 
/*      */     
/*  311 */     m_elementFlags.put("S", new ElemDesc(4096));
/*  312 */     m_elementFlags.put("STRIKE", new ElemDesc(4096));
/*      */ 
/*      */     
/*  315 */     m_elementFlags.put("U", new ElemDesc(4096));
/*      */ 
/*      */     
/*  318 */     m_elementFlags.put("NOBR", new ElemDesc(4096));
/*      */ 
/*      */     
/*  321 */     m_elementFlags.put("IFRAME", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  330 */     m_elementFlags.put("LAYER", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  338 */     m_elementFlags.put("ILAYER", new ElemDesc(56));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  352 */     ElemDesc elemDesc = (ElemDesc)m_elementFlags.get("A");
/*  353 */     elemDesc.setAttr("HREF", 2);
/*  354 */     elemDesc.setAttr("NAME", 2);
/*      */ 
/*      */     
/*  357 */     elemDesc = (ElemDesc)m_elementFlags.get("AREA");
/*  358 */     elemDesc.setAttr("HREF", 2);
/*  359 */     elemDesc.setAttr("NOHREF", 4);
/*      */ 
/*      */     
/*  362 */     elemDesc = (ElemDesc)m_elementFlags.get("BASE");
/*  363 */     elemDesc.setAttr("HREF", 2);
/*      */ 
/*      */     
/*  366 */     elemDesc = (ElemDesc)m_elementFlags.get("BUTTON");
/*  367 */     elemDesc.setAttr("DISABLED", 4);
/*      */ 
/*      */     
/*  370 */     elemDesc = (ElemDesc)m_elementFlags.get("BLOCKQUOTE");
/*  371 */     elemDesc.setAttr("CITE", 2);
/*      */ 
/*      */     
/*  374 */     elemDesc = (ElemDesc)m_elementFlags.get("DEL");
/*  375 */     elemDesc.setAttr("CITE", 2);
/*      */ 
/*      */     
/*  378 */     elemDesc = (ElemDesc)m_elementFlags.get("DIR");
/*  379 */     elemDesc.setAttr("COMPACT", 4);
/*      */ 
/*      */ 
/*      */     
/*  383 */     elemDesc = (ElemDesc)m_elementFlags.get("DIV");
/*  384 */     elemDesc.setAttr("SRC", 2);
/*  385 */     elemDesc.setAttr("NOWRAP", 4);
/*      */ 
/*      */     
/*  388 */     elemDesc = (ElemDesc)m_elementFlags.get("DL");
/*  389 */     elemDesc.setAttr("COMPACT", 4);
/*      */ 
/*      */     
/*  392 */     elemDesc = (ElemDesc)m_elementFlags.get("FORM");
/*  393 */     elemDesc.setAttr("ACTION", 2);
/*      */ 
/*      */ 
/*      */     
/*  397 */     elemDesc = (ElemDesc)m_elementFlags.get("FRAME");
/*  398 */     elemDesc.setAttr("SRC", 2);
/*  399 */     elemDesc.setAttr("LONGDESC", 2);
/*  400 */     elemDesc.setAttr("NORESIZE", 4);
/*      */ 
/*      */     
/*  403 */     elemDesc = (ElemDesc)m_elementFlags.get("HEAD");
/*  404 */     elemDesc.setAttr("PROFILE", 2);
/*      */ 
/*      */     
/*  407 */     elemDesc = (ElemDesc)m_elementFlags.get("HR");
/*  408 */     elemDesc.setAttr("NOSHADE", 4);
/*      */ 
/*      */ 
/*      */     
/*  412 */     elemDesc = (ElemDesc)m_elementFlags.get("IFRAME");
/*  413 */     elemDesc.setAttr("SRC", 2);
/*  414 */     elemDesc.setAttr("LONGDESC", 2);
/*      */ 
/*      */ 
/*      */     
/*  418 */     elemDesc = (ElemDesc)m_elementFlags.get("ILAYER");
/*  419 */     elemDesc.setAttr("SRC", 2);
/*      */ 
/*      */     
/*  422 */     elemDesc = (ElemDesc)m_elementFlags.get("IMG");
/*  423 */     elemDesc.setAttr("SRC", 2);
/*  424 */     elemDesc.setAttr("LONGDESC", 2);
/*  425 */     elemDesc.setAttr("USEMAP", 2);
/*  426 */     elemDesc.setAttr("ISMAP", 4);
/*      */ 
/*      */     
/*  429 */     elemDesc = (ElemDesc)m_elementFlags.get("INPUT");
/*  430 */     elemDesc.setAttr("SRC", 2);
/*  431 */     elemDesc.setAttr("USEMAP", 2);
/*  432 */     elemDesc.setAttr("CHECKED", 4);
/*  433 */     elemDesc.setAttr("DISABLED", 4);
/*  434 */     elemDesc.setAttr("ISMAP", 4);
/*  435 */     elemDesc.setAttr("READONLY", 4);
/*      */ 
/*      */     
/*  438 */     elemDesc = (ElemDesc)m_elementFlags.get("INS");
/*  439 */     elemDesc.setAttr("CITE", 2);
/*      */ 
/*      */ 
/*      */     
/*  443 */     elemDesc = (ElemDesc)m_elementFlags.get("LAYER");
/*  444 */     elemDesc.setAttr("SRC", 2);
/*      */ 
/*      */     
/*  447 */     elemDesc = (ElemDesc)m_elementFlags.get("LINK");
/*  448 */     elemDesc.setAttr("HREF", 2);
/*      */ 
/*      */     
/*  451 */     elemDesc = (ElemDesc)m_elementFlags.get("MENU");
/*  452 */     elemDesc.setAttr("COMPACT", 4);
/*      */ 
/*      */     
/*  455 */     elemDesc = (ElemDesc)m_elementFlags.get("OBJECT");
/*  456 */     elemDesc.setAttr("CLASSID", 2);
/*  457 */     elemDesc.setAttr("CODEBASE", 2);
/*  458 */     elemDesc.setAttr("DATA", 2);
/*  459 */     elemDesc.setAttr("ARCHIVE", 2);
/*  460 */     elemDesc.setAttr("USEMAP", 2);
/*  461 */     elemDesc.setAttr("DECLARE", 4);
/*      */ 
/*      */     
/*  464 */     elemDesc = (ElemDesc)m_elementFlags.get("OL");
/*  465 */     elemDesc.setAttr("COMPACT", 4);
/*      */ 
/*      */     
/*  468 */     elemDesc = (ElemDesc)m_elementFlags.get("OPTGROUP");
/*  469 */     elemDesc.setAttr("DISABLED", 4);
/*      */ 
/*      */     
/*  472 */     elemDesc = (ElemDesc)m_elementFlags.get("OPTION");
/*  473 */     elemDesc.setAttr("SELECTED", 4);
/*  474 */     elemDesc.setAttr("DISABLED", 4);
/*      */ 
/*      */     
/*  477 */     elemDesc = (ElemDesc)m_elementFlags.get("Q");
/*  478 */     elemDesc.setAttr("CITE", 2);
/*      */ 
/*      */     
/*  481 */     elemDesc = (ElemDesc)m_elementFlags.get("SCRIPT");
/*  482 */     elemDesc.setAttr("SRC", 2);
/*  483 */     elemDesc.setAttr("FOR", 2);
/*  484 */     elemDesc.setAttr("DEFER", 4);
/*      */ 
/*      */     
/*  487 */     elemDesc = (ElemDesc)m_elementFlags.get("SELECT");
/*  488 */     elemDesc.setAttr("DISABLED", 4);
/*  489 */     elemDesc.setAttr("MULTIPLE", 4);
/*      */ 
/*      */     
/*  492 */     elemDesc = (ElemDesc)m_elementFlags.get("TABLE");
/*  493 */     elemDesc.setAttr("NOWRAP", 4);
/*      */ 
/*      */     
/*  496 */     elemDesc = (ElemDesc)m_elementFlags.get("TD");
/*  497 */     elemDesc.setAttr("NOWRAP", 4);
/*      */ 
/*      */     
/*  500 */     elemDesc = (ElemDesc)m_elementFlags.get("TEXTAREA");
/*  501 */     elemDesc.setAttr("DISABLED", 4);
/*  502 */     elemDesc.setAttr("READONLY", 4);
/*      */ 
/*      */     
/*  505 */     elemDesc = (ElemDesc)m_elementFlags.get("TH");
/*  506 */     elemDesc.setAttr("NOWRAP", 4);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  511 */     elemDesc = (ElemDesc)m_elementFlags.get("TR");
/*  512 */     elemDesc.setAttr("NOWRAP", 4);
/*      */ 
/*      */     
/*  515 */     elemDesc = (ElemDesc)m_elementFlags.get("UL");
/*  516 */     elemDesc.setAttr("COMPACT", 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  522 */   private static final ElemDesc m_dummy = new ElemDesc(8);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean m_specialEscapeURLs = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean m_omitMetaTag = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSpecialEscapeURLs(boolean bool) {
/*  537 */     this.m_specialEscapeURLs = bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOmitMetaTag(boolean bool) {
/*  547 */     this.m_omitMetaTag = bool;
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
/*      */   public void setOutputFormat(Properties format) {
/*  569 */     this
/*  570 */       .m_specialEscapeURLs = OutputPropertyUtils.getBooleanProperty("{http://xml.apache.org/xalan}use-url-escaping", format);
/*      */ 
/*      */ 
/*      */     
/*  574 */     this
/*  575 */       .m_omitMetaTag = OutputPropertyUtils.getBooleanProperty("{http://xml.apache.org/xalan}omit-meta-tag", format);
/*      */ 
/*      */ 
/*      */     
/*  579 */     super.setOutputFormat(format);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean getSpecialEscapeURLs() {
/*  589 */     return this.m_specialEscapeURLs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean getOmitMetaTag() {
/*  599 */     return this.m_omitMetaTag;
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
/*      */   public static final ElemDesc getElemDesc(String name) {
/*  615 */     Object obj = m_elementFlags.get(name);
/*  616 */     if (null != obj)
/*  617 */       return (ElemDesc)obj; 
/*  618 */     return m_dummy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  626 */   private Trie m_htmlInfo = new Trie(m_elementFlags);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ElemDesc getElemDesc2(String name) {
/*  633 */     Object obj = this.m_htmlInfo.get2(name);
/*  634 */     if (null != obj)
/*  635 */       return (ElemDesc)obj; 
/*  636 */     return m_dummy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToHTMLStream() {
/*  646 */     this.m_charInfo = m_htmlcharInfo;
/*      */     
/*  648 */     this.m_prefixMap = new NamespaceMappings();
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
/*      */   protected void startDocumentInternal() throws SAXException {
/*  665 */     super.startDocumentInternal();
/*      */     
/*  667 */     this.m_needToCallStartDocument = false;
/*  668 */     this.m_needToOutputDocTypeDecl = true;
/*  669 */     this.m_startNewLine = false;
/*  670 */     setOmitXMLDeclaration(true);
/*      */     
/*  672 */     if (true == this.m_needToOutputDocTypeDecl) {
/*      */       
/*  674 */       String doctypeSystem = getDoctypeSystem();
/*  675 */       String doctypePublic = getDoctypePublic();
/*  676 */       if (null != doctypeSystem || null != doctypePublic) {
/*      */         
/*  678 */         Writer writer = this.m_writer;
/*      */         
/*      */         try {
/*  681 */           writer.write("<!DOCTYPE html");
/*      */           
/*  683 */           if (null != doctypePublic) {
/*      */             
/*  685 */             writer.write(" PUBLIC \"");
/*  686 */             writer.write(doctypePublic);
/*  687 */             writer.write(34);
/*      */           } 
/*      */           
/*  690 */           if (null != doctypeSystem) {
/*      */             
/*  692 */             if (null == doctypePublic) {
/*  693 */               writer.write(" SYSTEM \"");
/*      */             } else {
/*  695 */               writer.write(" \"");
/*      */             } 
/*  697 */             writer.write(doctypeSystem);
/*  698 */             writer.write(34);
/*      */           } 
/*      */           
/*  701 */           writer.write(62);
/*  702 */           outputLineSep();
/*      */         }
/*  704 */         catch (IOException e) {
/*      */           
/*  706 */           throw new SAXException(e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  711 */     this.m_needToOutputDocTypeDecl = false;
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
/*      */   public final void endDocument() throws SAXException {
/*  725 */     flushPending();
/*  726 */     if (this.m_doIndent && !this.m_isprevtext) {
/*      */       
/*      */       try {
/*      */         
/*  730 */         outputLineSep();
/*      */       }
/*  732 */       catch (IOException e) {
/*      */         
/*  734 */         throw new SAXException(e);
/*      */       } 
/*      */     }
/*      */     
/*  738 */     flushWriter();
/*  739 */     if (this.m_tracer != null) {
/*  740 */       fireEndDoc();
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
/*      */   public void startElement(String namespaceURI, String localName, String name, Attributes atts) throws SAXException {
/*  764 */     ElemContext elemContext = this.m_elemContext;
/*      */ 
/*      */     
/*  767 */     if (elemContext.m_startTagOpen) {
/*      */       
/*  769 */       closeStartTag();
/*  770 */       elemContext.m_startTagOpen = false;
/*      */     }
/*  772 */     else if (this.m_cdataTagOpen) {
/*      */       
/*  774 */       closeCDATA();
/*  775 */       this.m_cdataTagOpen = false;
/*      */     }
/*  777 */     else if (this.m_needToCallStartDocument) {
/*      */       
/*  779 */       startDocumentInternal();
/*  780 */       this.m_needToCallStartDocument = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  785 */     if (null != namespaceURI && namespaceURI.length() > 0) {
/*      */       
/*  787 */       super.startElement(namespaceURI, localName, name, atts);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  795 */       ElemDesc elemDesc = getElemDesc2(name);
/*  796 */       int elemFlags = elemDesc.getFlags();
/*      */ 
/*      */       
/*  799 */       if (this.m_doIndent) {
/*      */ 
/*      */         
/*  802 */         boolean isBlockElement = ((elemFlags & 0x8) != 0);
/*  803 */         if (this.m_ispreserve) {
/*  804 */           this.m_ispreserve = false;
/*  805 */         } else if (null != elemContext.m_elementName && (!this.m_inBlockElem || isBlockElement)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  811 */           this.m_startNewLine = true;
/*      */           
/*  813 */           indent();
/*      */         } 
/*      */         
/*  816 */         this.m_inBlockElem = !isBlockElement;
/*      */       } 
/*      */ 
/*      */       
/*  820 */       if (atts != null) {
/*  821 */         addAttributes(atts);
/*      */       }
/*  823 */       this.m_isprevtext = false;
/*  824 */       Writer writer = this.m_writer;
/*  825 */       writer.write(60);
/*  826 */       writer.write(name);
/*      */ 
/*      */ 
/*      */       
/*  830 */       if (this.m_tracer != null) {
/*  831 */         firePseudoAttributes();
/*      */       }
/*  833 */       if ((elemFlags & 0x2) != 0) {
/*      */ 
/*      */ 
/*      */         
/*  837 */         this.m_elemContext = elemContext.push();
/*      */ 
/*      */ 
/*      */         
/*  841 */         this.m_elemContext.m_elementName = name;
/*  842 */         this.m_elemContext.m_elementDesc = elemDesc;
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  847 */       elemContext = elemContext.push(namespaceURI, localName, name);
/*  848 */       this.m_elemContext = elemContext;
/*  849 */       elemContext.m_elementDesc = elemDesc;
/*  850 */       elemContext.m_isRaw = ((elemFlags & 0x100) != 0);
/*      */ 
/*      */ 
/*      */       
/*  854 */       if ((elemFlags & 0x400000) != 0) {
/*      */ 
/*      */         
/*  857 */         closeStartTag();
/*  858 */         elemContext.m_startTagOpen = false;
/*  859 */         if (!this.m_omitMetaTag)
/*      */         {
/*  861 */           if (this.m_doIndent)
/*  862 */             indent(); 
/*  863 */           writer.write("<META http-equiv=\"Content-Type\" content=\"text/html; charset=");
/*      */           
/*  865 */           String encoding = getEncoding();
/*  866 */           String encode = Encodings.getMimeEncoding(encoding);
/*  867 */           writer.write(encode);
/*  868 */           writer.write("\">");
/*      */         }
/*      */       
/*      */       } 
/*  872 */     } catch (IOException e) {
/*      */       
/*  874 */       throw new SAXException(e);
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
/*      */   public final void endElement(String namespaceURI, String localName, String name) throws SAXException {
/*  895 */     if (this.m_cdataTagOpen) {
/*  896 */       closeCDATA();
/*      */     }
/*      */     
/*  899 */     if (null != namespaceURI && namespaceURI.length() > 0) {
/*      */       
/*  901 */       super.endElement(namespaceURI, localName, name);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  909 */       ElemContext elemContext = this.m_elemContext;
/*  910 */       ElemDesc elemDesc = elemContext.m_elementDesc;
/*  911 */       int elemFlags = elemDesc.getFlags();
/*  912 */       boolean elemEmpty = ((elemFlags & 0x2) != 0);
/*      */ 
/*      */       
/*  915 */       if (this.m_doIndent) {
/*      */         
/*  917 */         boolean isBlockElement = ((elemFlags & 0x8) != 0);
/*  918 */         boolean shouldIndent = false;
/*      */         
/*  920 */         if (this.m_ispreserve) {
/*      */           
/*  922 */           this.m_ispreserve = false;
/*      */         }
/*  924 */         else if (this.m_doIndent && (!this.m_inBlockElem || isBlockElement)) {
/*      */           
/*  926 */           this.m_startNewLine = true;
/*  927 */           shouldIndent = true;
/*      */         } 
/*  929 */         if (!elemContext.m_startTagOpen && shouldIndent)
/*  930 */           indent(elemContext.m_currentElemDepth - 1); 
/*  931 */         this.m_inBlockElem = !isBlockElement;
/*      */       } 
/*      */       
/*  934 */       Writer writer = this.m_writer;
/*  935 */       if (!elemContext.m_startTagOpen) {
/*      */         
/*  937 */         writer.write("</");
/*  938 */         writer.write(name);
/*  939 */         writer.write(62);
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  946 */         if (this.m_tracer != null) {
/*  947 */           fireStartElem(name);
/*      */         }
/*      */ 
/*      */         
/*  951 */         int nAttrs = this.m_attributes.getLength();
/*  952 */         if (nAttrs > 0) {
/*      */           
/*  954 */           processAttributes(this.m_writer, nAttrs);
/*      */           
/*  956 */           this.m_attributes.clear();
/*      */         } 
/*  958 */         if (!elemEmpty) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  965 */           writer.write("></");
/*  966 */           writer.write(name);
/*  967 */           writer.write(62);
/*      */         }
/*      */         else {
/*      */           
/*  971 */           writer.write(62);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  976 */       if ((elemFlags & 0x200000) != 0)
/*  977 */         this.m_ispreserve = true; 
/*  978 */       this.m_isprevtext = false;
/*      */ 
/*      */       
/*  981 */       if (this.m_tracer != null) {
/*  982 */         fireEndElem(name);
/*      */       }
/*      */       
/*  985 */       if (elemEmpty) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  990 */         this.m_elemContext = elemContext.m_prev;
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  995 */       if (!elemContext.m_startTagOpen)
/*      */       {
/*  997 */         if (this.m_doIndent && !this.m_preserves.isEmpty())
/*  998 */           this.m_preserves.pop(); 
/*      */       }
/* 1000 */       this.m_elemContext = elemContext.m_prev;
/*      */     
/*      */     }
/* 1003 */     catch (IOException e) {
/*      */       
/* 1005 */       throw new SAXException(e);
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
/*      */   protected void processAttribute(Writer writer, String name, String value, ElemDesc elemDesc) throws IOException, SAXException {
/* 1026 */     writer.write(32);
/*      */     
/* 1028 */     if ((value.length() == 0 || value.equalsIgnoreCase(name)) && elemDesc != null && elemDesc
/*      */       
/* 1030 */       .isAttrFlagSet(name, 4)) {
/*      */       
/* 1032 */       writer.write(name);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1039 */       writer.write(name);
/* 1040 */       writer.write("=\"");
/* 1041 */       if (elemDesc != null && elemDesc
/* 1042 */         .isAttrFlagSet(name, 2)) {
/* 1043 */         writeAttrURI(writer, value, this.m_specialEscapeURLs);
/*      */       } else {
/* 1045 */         writeAttrString(writer, value, getEncoding());
/* 1046 */       }  writer.write(34);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isASCIIDigit(char c) {
/* 1056 */     return (c >= '0' && c <= '9');
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
/*      */   private static String makeHHString(int i) {
/* 1070 */     String s = Integer.toHexString(i).toUpperCase();
/* 1071 */     if (s.length() == 1)
/*      */     {
/* 1073 */       s = "0" + s;
/*      */     }
/* 1075 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isHHSign(String str) {
/* 1086 */     boolean sign = true;
/*      */     
/*      */     try {
/* 1089 */       char c = (char)Integer.parseInt(str, 16);
/*      */     }
/* 1091 */     catch (NumberFormatException e) {
/*      */       
/* 1093 */       sign = false;
/*      */     } 
/* 1095 */     return sign;
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
/*      */   public void writeAttrURI(Writer writer, String string, boolean doURLEscaping) throws IOException {
/* 1127 */     int end = string.length();
/* 1128 */     if (end > this.m_attrBuff.length)
/*      */     {
/* 1130 */       this.m_attrBuff = new char[end * 2 + 1];
/*      */     }
/* 1132 */     string.getChars(0, end, this.m_attrBuff, 0);
/* 1133 */     char[] chars = this.m_attrBuff;
/*      */     
/* 1135 */     int cleanStart = 0;
/* 1136 */     int cleanLength = 0;
/*      */ 
/*      */     
/* 1139 */     char ch = Character.MIN_VALUE;
/* 1140 */     for (int i = 0; i < end; i++) {
/*      */       
/* 1142 */       ch = chars[i];
/*      */       
/* 1144 */       if (ch < ' ' || ch > '~') {
/*      */         
/* 1146 */         if (cleanLength > 0) {
/*      */           
/* 1148 */           writer.write(chars, cleanStart, cleanLength);
/* 1149 */           cleanLength = 0;
/*      */         } 
/* 1151 */         if (doURLEscaping) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1163 */           if (ch <= '')
/*      */           {
/* 1165 */             writer.write(37);
/* 1166 */             writer.write(makeHHString(ch));
/*      */           }
/* 1168 */           else if (ch <= '߿')
/*      */           {
/*      */ 
/*      */             
/* 1172 */             int high = ch >> 6 | 0xC0;
/* 1173 */             int low = ch & 0x3F | 0x80;
/*      */             
/* 1175 */             writer.write(37);
/* 1176 */             writer.write(makeHHString(high));
/* 1177 */             writer.write(37);
/* 1178 */             writer.write(makeHHString(low));
/*      */           }
/* 1180 */           else if (Encodings.isHighUTF16Surrogate(ch))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1190 */             int highSurrogate = ch & 0x3FF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1196 */             int wwww = (highSurrogate & 0x3C0) >> 6;
/* 1197 */             int uuuuu = wwww + 1;
/*      */ 
/*      */             
/* 1200 */             int zzzz = (highSurrogate & 0x3C) >> 2;
/*      */ 
/*      */             
/* 1203 */             int yyyyyy = (highSurrogate & 0x3) << 4 & 0x30;
/*      */ 
/*      */             
/* 1206 */             ch = chars[++i];
/*      */ 
/*      */             
/* 1209 */             int lowSurrogate = ch & 0x3FF;
/*      */ 
/*      */             
/* 1212 */             yyyyyy |= (lowSurrogate & 0x3C0) >> 6;
/*      */ 
/*      */             
/* 1215 */             int xxxxxx = lowSurrogate & 0x3F;
/*      */             
/* 1217 */             int byte1 = 0xF0 | uuuuu >> 2;
/* 1218 */             int byte2 = 0x80 | (uuuuu & 0x3) << 4 & 0x30 | zzzz;
/*      */             
/* 1220 */             int byte3 = 0x80 | yyyyyy;
/* 1221 */             int byte4 = 0x80 | xxxxxx;
/*      */             
/* 1223 */             writer.write(37);
/* 1224 */             writer.write(makeHHString(byte1));
/* 1225 */             writer.write(37);
/* 1226 */             writer.write(makeHHString(byte2));
/* 1227 */             writer.write(37);
/* 1228 */             writer.write(makeHHString(byte3));
/* 1229 */             writer.write(37);
/* 1230 */             writer.write(makeHHString(byte4));
/*      */           }
/*      */           else
/*      */           {
/* 1234 */             int high = ch >> 12 | 0xE0;
/* 1235 */             int middle = (ch & 0xFC0) >> 6 | 0x80;
/*      */             
/* 1237 */             int low = ch & 0x3F | 0x80;
/*      */             
/* 1239 */             writer.write(37);
/* 1240 */             writer.write(makeHHString(high));
/* 1241 */             writer.write(37);
/* 1242 */             writer.write(makeHHString(middle));
/* 1243 */             writer.write(37);
/* 1244 */             writer.write(makeHHString(low));
/*      */           }
/*      */         
/*      */         }
/* 1248 */         else if (escapingNotNeeded(ch)) {
/*      */           
/* 1250 */           writer.write(ch);
/*      */         }
/*      */         else {
/*      */           
/* 1254 */           writer.write("&#");
/* 1255 */           writer.write(Integer.toString(ch));
/* 1256 */           writer.write(59);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1262 */         cleanStart = i + 1;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1267 */       else if (ch == '"') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1279 */         if (cleanLength > 0) {
/*      */           
/* 1281 */           writer.write(chars, cleanStart, cleanLength);
/* 1282 */           cleanLength = 0;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1287 */         if (doURLEscaping) {
/* 1288 */           writer.write("%22");
/*      */         } else {
/* 1290 */           writer.write("&quot;");
/*      */         } 
/*      */ 
/*      */         
/* 1294 */         cleanStart = i + 1;
/*      */       }
/* 1296 */       else if (ch == '&') {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1301 */         if (cleanLength > 0) {
/*      */           
/* 1303 */           writer.write(chars, cleanStart, cleanLength);
/* 1304 */           cleanLength = 0;
/*      */         } 
/* 1306 */         writer.write("&amp;");
/* 1307 */         cleanStart = i + 1;
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1313 */         cleanLength++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1319 */     if (cleanLength > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1324 */       if (cleanStart == 0) {
/* 1325 */         writer.write(string);
/*      */       } else {
/* 1327 */         writer.write(chars, cleanStart, cleanLength);
/*      */       } 
/* 1329 */     } else if (cleanLength == 1) {
/*      */ 
/*      */ 
/*      */       
/* 1333 */       writer.write(ch);
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
/*      */   public void writeAttrString(Writer writer, String string, String encoding) throws IOException, SAXException {
/* 1350 */     int end = string.length();
/* 1351 */     if (end > this.m_attrBuff.length)
/*      */     {
/* 1353 */       this.m_attrBuff = new char[end * 2 + 1];
/*      */     }
/* 1355 */     string.getChars(0, end, this.m_attrBuff, 0);
/* 1356 */     char[] chars = this.m_attrBuff;
/*      */ 
/*      */ 
/*      */     
/* 1360 */     int cleanStart = 0;
/* 1361 */     int cleanLength = 0;
/*      */     
/* 1363 */     char ch = Character.MIN_VALUE;
/* 1364 */     for (int i = 0; i < end; i++) {
/*      */       
/* 1366 */       ch = chars[i];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1372 */       if (escapingNotNeeded(ch) && !this.m_charInfo.isSpecialAttrChar(ch)) {
/*      */         
/* 1374 */         cleanLength++;
/*      */       }
/* 1376 */       else if ('<' == ch || '>' == ch) {
/*      */         
/* 1378 */         cleanLength++;
/*      */       }
/* 1380 */       else if ('&' == ch && i + 1 < end && '{' == chars[i + 1]) {
/*      */ 
/*      */         
/* 1383 */         cleanLength++;
/*      */       }
/*      */       else {
/*      */         
/* 1387 */         if (cleanLength > 0) {
/*      */           
/* 1389 */           writer.write(chars, cleanStart, cleanLength);
/* 1390 */           cleanLength = 0;
/*      */         } 
/* 1392 */         int pos = accumDefaultEntity(writer, ch, i, chars, end, false, true);
/*      */         
/* 1394 */         if (i != pos) {
/*      */           
/* 1396 */           i = pos - 1;
/*      */         }
/*      */         else {
/*      */           
/* 1400 */           if (Encodings.isHighUTF16Surrogate(ch) || 
/* 1401 */             Encodings.isLowUTF16Surrogate(ch))
/*      */           {
/* 1403 */             if (writeUTF16Surrogate(ch, chars, i, end) >= 0)
/*      */             {
/*      */               
/* 1406 */               if (Encodings.isHighUTF16Surrogate(ch)) {
/* 1407 */                 i++;
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1423 */           String outputStringForChar = this.m_charInfo.getOutputStringForChar(ch);
/* 1424 */           if (null != outputStringForChar) {
/*      */             
/* 1426 */             writer.write(outputStringForChar);
/*      */           }
/* 1428 */           else if (escapingNotNeeded(ch)) {
/*      */             
/* 1430 */             writer.write(ch);
/*      */           }
/*      */           else {
/*      */             
/* 1434 */             writer.write("&#");
/* 1435 */             writer.write(Integer.toString(ch));
/* 1436 */             writer.write(59);
/*      */           } 
/*      */         } 
/* 1439 */         cleanStart = i + 1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1445 */     if (cleanLength > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1450 */       if (cleanStart == 0) {
/* 1451 */         writer.write(string);
/*      */       } else {
/* 1453 */         writer.write(chars, cleanStart, cleanLength);
/*      */       } 
/* 1455 */     } else if (cleanLength == 1) {
/*      */ 
/*      */ 
/*      */       
/* 1459 */       writer.write(ch);
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
/*      */   
/*      */   public final void characters(char[] chars, int start, int length) throws SAXException {
/* 1496 */     if (this.m_elemContext.m_isRaw) {
/*      */       
/*      */       try {
/*      */         
/* 1500 */         if (this.m_elemContext.m_startTagOpen) {
/*      */           
/* 1502 */           closeStartTag();
/* 1503 */           this.m_elemContext.m_startTagOpen = false;
/*      */         } 
/* 1505 */         this.m_ispreserve = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1514 */         writeNormalizedChars(chars, start, length, false, this.m_lineSepUse);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1519 */         if (this.m_tracer != null) {
/* 1520 */           fireCharEvent(chars, start, length);
/*      */         }
/*      */         
/*      */         return;
/* 1524 */       } catch (IOException ioe) {
/*      */         
/* 1526 */         throw new SAXException(Utils.messages
/* 1527 */             .createMessage("ER_OIERROR", null), ioe);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1536 */     super.characters(chars, start, length);
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
/*      */   public final void cdata(char[] ch, int start, int length) throws SAXException {
/* 1571 */     if (null != this.m_elemContext.m_elementName && (this.m_elemContext.m_elementName
/* 1572 */       .equalsIgnoreCase("SCRIPT") || this.m_elemContext.m_elementName
/* 1573 */       .equalsIgnoreCase("STYLE"))) {
/*      */       
/*      */       try
/*      */       {
/* 1577 */         if (this.m_elemContext.m_startTagOpen) {
/*      */           
/* 1579 */           closeStartTag();
/* 1580 */           this.m_elemContext.m_startTagOpen = false;
/*      */         } 
/*      */         
/* 1583 */         this.m_ispreserve = true;
/*      */         
/* 1585 */         if (shouldIndent()) {
/* 1586 */           indent();
/*      */         }
/*      */         
/* 1589 */         writeNormalizedChars(ch, start, length, true, this.m_lineSepUse);
/*      */       }
/* 1591 */       catch (IOException ioe)
/*      */       {
/* 1593 */         throw new SAXException(Utils.messages
/* 1594 */             .createMessage("ER_OIERROR", null), ioe);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1603 */       super.cdata(ch, start, length);
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
/*      */   public void processingInstruction(String target, String data) throws SAXException {
/* 1623 */     flushPending();
/*      */ 
/*      */ 
/*      */     
/* 1627 */     if (target.equals("javax.xml.transform.disable-output-escaping")) {
/*      */       
/* 1629 */       startNonEscaping();
/*      */     }
/* 1631 */     else if (target.equals("javax.xml.transform.enable-output-escaping")) {
/*      */       
/* 1633 */       endNonEscaping();
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/* 1639 */         if (this.m_elemContext.m_startTagOpen) {
/*      */           
/* 1641 */           closeStartTag();
/* 1642 */           this.m_elemContext.m_startTagOpen = false;
/*      */         }
/* 1644 */         else if (this.m_needToCallStartDocument) {
/* 1645 */           startDocumentInternal();
/*      */         } 
/* 1647 */         if (shouldIndent()) {
/* 1648 */           indent();
/*      */         }
/* 1650 */         Writer writer = this.m_writer;
/*      */         
/* 1652 */         writer.write("<?");
/* 1653 */         writer.write(target);
/*      */         
/* 1655 */         if (data.length() > 0 && !Character.isSpaceChar(data.charAt(0))) {
/* 1656 */           writer.write(32);
/*      */         }
/*      */         
/* 1659 */         writer.write(data);
/* 1660 */         writer.write(62);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1665 */         if (this.m_elemContext.m_currentElemDepth <= 0) {
/* 1666 */           outputLineSep();
/*      */         }
/* 1668 */         this.m_startNewLine = true;
/*      */       }
/* 1670 */       catch (IOException e) {
/*      */         
/* 1672 */         throw new SAXException(e);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1677 */     if (this.m_tracer != null) {
/* 1678 */       fireEscapingEvent(target, data);
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
/*      */   public final void entityReference(String name) throws SAXException {
/*      */     try {
/* 1694 */       Writer writer = this.m_writer;
/* 1695 */       writer.write(38);
/* 1696 */       writer.write(name);
/* 1697 */       writer.write(59);
/*      */     }
/* 1699 */     catch (IOException e) {
/*      */       
/* 1701 */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void endElement(String elemName) throws SAXException {
/* 1709 */     endElement((String)null, (String)null, elemName);
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
/*      */   public void processAttributes(Writer writer, int nAttrs) throws IOException, SAXException {
/* 1729 */     for (int i = 0; i < nAttrs; i++)
/*      */     {
/* 1731 */       processAttribute(writer, this.m_attributes
/*      */           
/* 1733 */           .getQName(i), this.m_attributes
/* 1734 */           .getValue(i), this.m_elemContext.m_elementDesc);
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
/*      */   protected void closeStartTag() throws SAXException {
/*      */     try {
/* 1751 */       if (this.m_tracer != null) {
/* 1752 */         fireStartElem(this.m_elemContext.m_elementName);
/*      */       }
/* 1754 */       int nAttrs = this.m_attributes.getLength();
/* 1755 */       if (nAttrs > 0) {
/*      */         
/* 1757 */         processAttributes(this.m_writer, nAttrs);
/*      */         
/* 1759 */         this.m_attributes.clear();
/*      */       } 
/*      */       
/* 1762 */       this.m_writer.write(62);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1768 */       if (this.m_cdataSectionElements != null)
/* 1769 */         this.m_elemContext.m_isCdataSection = isCdataSection(); 
/* 1770 */       if (this.m_doIndent)
/*      */       {
/* 1772 */         this.m_isprevtext = false;
/* 1773 */         this.m_preserves.push(this.m_ispreserve);
/*      */       }
/*      */     
/*      */     }
/* 1777 */     catch (IOException e) {
/*      */       
/* 1779 */       throw new SAXException(e);
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
/*      */   protected synchronized void init(OutputStream output, Properties format) throws UnsupportedEncodingException {
/* 1794 */     if (null == format)
/*      */     {
/* 1796 */       format = OutputPropertiesFactory.getDefaultMethodProperties("html");
/*      */     }
/* 1798 */     init(output, format, false);
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
/*      */   public void setOutputStream(OutputStream output) {
/*      */     try {
/*      */       Properties format;
/* 1818 */       if (null == this.m_format) {
/* 1819 */         format = OutputPropertiesFactory.getDefaultMethodProperties("html");
/*      */       } else {
/* 1821 */         format = this.m_format;
/* 1822 */       }  init(output, format, true);
/*      */     }
/* 1824 */     catch (UnsupportedEncodingException unsupportedEncodingException) {}
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
/*      */   public void namespaceAfterStartElement(String prefix, String uri) throws SAXException {
/* 1845 */     if (this.m_elemContext.m_elementURI == null) {
/*      */       
/* 1847 */       String prefix1 = getPrefixPart(this.m_elemContext.m_elementName);
/* 1848 */       if (prefix1 == null && "".equals(prefix))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1854 */         this.m_elemContext.m_elementURI = uri;
/*      */       }
/*      */     } 
/* 1857 */     startPrefixMapping(prefix, uri, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 1863 */     this.m_inDTD = true;
/* 1864 */     super.startDTD(name, publicId, systemId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endDTD() throws SAXException {
/* 1874 */     this.m_inDTD = false;
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
/*      */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void elementDecl(String name, String model) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void internalEntityDecl(String name, String value) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addUniqueAttribute(String name, String value, int flags) throws SAXException {
/*      */     try {
/* 1935 */       Writer writer = this.m_writer;
/* 1936 */       if ((flags & 0x1) > 0 && m_htmlcharInfo.onlyQuotAmpLtGt) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1943 */         writer.write(32);
/* 1944 */         writer.write(name);
/* 1945 */         writer.write("=\"");
/* 1946 */         writer.write(value);
/* 1947 */         writer.write(34);
/*      */       }
/* 1949 */       else if ((flags & 0x2) > 0 && (value
/*      */         
/* 1951 */         .length() == 0 || value.equalsIgnoreCase(name))) {
/*      */         
/* 1953 */         writer.write(32);
/* 1954 */         writer.write(name);
/*      */       }
/*      */       else {
/*      */         
/* 1958 */         writer.write(32);
/* 1959 */         writer.write(name);
/* 1960 */         writer.write("=\"");
/* 1961 */         if ((flags & 0x4) > 0) {
/*      */           
/* 1963 */           writeAttrURI(writer, value, this.m_specialEscapeURLs);
/*      */         }
/*      */         else {
/*      */           
/* 1967 */           writeAttrString(writer, value, getEncoding());
/*      */         } 
/* 1969 */         writer.write(34);
/*      */       } 
/* 1971 */     } catch (IOException e) {
/* 1972 */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 1980 */     if (this.m_inDTD)
/*      */       return; 
/* 1982 */     super.comment(ch, start, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean reset() {
/* 1987 */     boolean ret = super.reset();
/* 1988 */     if (!ret)
/* 1989 */       return false; 
/* 1990 */     initToHTMLStream();
/* 1991 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initToHTMLStream() {
/* 1997 */     this.m_inBlockElem = false;
/* 1998 */     this.m_inDTD = false;
/*      */     
/* 2000 */     this.m_omitMetaTag = false;
/* 2001 */     this.m_specialEscapeURLs = true;
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
/*      */   static class Trie
/*      */   {
/*      */     public static final int ALPHA_SIZE = 128;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Node m_Root;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2030 */     private char[] m_charBuffer = new char[0];
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean m_lowerCaseOnly;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Trie() {
/* 2040 */       this.m_Root = new Node();
/* 2041 */       this.m_lowerCaseOnly = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Trie(boolean lowerCaseOnly) {
/* 2051 */       this.m_Root = new Node();
/* 2052 */       this.m_lowerCaseOnly = lowerCaseOnly;
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
/*      */     public Object put(String key, Object value) {
/* 2066 */       int len = key.length();
/* 2067 */       if (len > this.m_charBuffer.length)
/*      */       {
/*      */         
/* 2070 */         this.m_charBuffer = new char[len];
/*      */       }
/*      */       
/* 2073 */       Node node = this.m_Root;
/*      */       
/* 2075 */       for (int i = 0; i < len; i++) {
/*      */ 
/*      */         
/* 2078 */         Node nextNode = node.m_nextChar[Character.toLowerCase(key.charAt(i))];
/*      */         
/* 2080 */         if (nextNode != null) {
/*      */           
/* 2082 */           node = nextNode;
/*      */         }
/*      */         else {
/*      */           
/* 2086 */           for (; i < len; i++) {
/*      */             
/* 2088 */             Node newNode = new Node();
/* 2089 */             if (this.m_lowerCaseOnly) {
/*      */ 
/*      */               
/* 2092 */               node.m_nextChar[Character.toLowerCase(key
/* 2093 */                     .charAt(i))] = newNode;
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/* 2099 */               node.m_nextChar[Character.toUpperCase(key
/* 2100 */                     .charAt(i))] = newNode;
/*      */               
/* 2102 */               node.m_nextChar[Character.toLowerCase(key
/* 2103 */                     .charAt(i))] = newNode;
/*      */             } 
/*      */             
/* 2106 */             node = newNode;
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 2112 */       Object ret = node.m_Value;
/*      */       
/* 2114 */       node.m_Value = value;
/*      */       
/* 2116 */       return ret;
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
/*      */     public Object get(String key) {
/*      */       char ch;
/* 2129 */       int len = key.length();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2134 */       if (this.m_charBuffer.length < len) {
/* 2135 */         return null;
/*      */       }
/* 2137 */       Node node = this.m_Root;
/* 2138 */       switch (len) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 0:
/* 2145 */           return null;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/* 2150 */           ch = key.charAt(0);
/* 2151 */           if (ch < '') {
/*      */             
/* 2153 */             node = node.m_nextChar[ch];
/* 2154 */             if (node != null)
/* 2155 */               return node.m_Value; 
/*      */           } 
/* 2157 */           return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2182 */       for (int i = 0; i < len; i++) {
/*      */ 
/*      */         
/* 2185 */         char c = key.charAt(i);
/* 2186 */         if ('' <= c)
/*      */         {
/*      */           
/* 2189 */           return null;
/*      */         }
/*      */         
/* 2192 */         node = node.m_nextChar[c];
/* 2193 */         if (node == null) {
/* 2194 */           return null;
/*      */         }
/*      */       } 
/* 2197 */       return node.m_Value;
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
/*      */     private class Node
/*      */     {
/* 2214 */       final Node[] m_nextChar = new Node[128];
/* 2215 */       Object m_Value = null;
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
/*      */ 
/*      */     
/*      */     public Trie(Trie existingTrie) {
/* 2235 */       this.m_Root = existingTrie.m_Root;
/* 2236 */       this.m_lowerCaseOnly = existingTrie.m_lowerCaseOnly;
/*      */ 
/*      */       
/* 2239 */       int max = existingTrie.getLongestKeyLength();
/* 2240 */       this.m_charBuffer = new char[max];
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
/*      */     public Object get2(String key) {
/*      */       char ch;
/* 2254 */       int len = key.length();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2259 */       if (this.m_charBuffer.length < len) {
/* 2260 */         return null;
/*      */       }
/* 2262 */       Node node = this.m_Root;
/* 2263 */       switch (len) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 0:
/* 2270 */           return null;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/* 2275 */           ch = key.charAt(0);
/* 2276 */           if (ch < '') {
/*      */             
/* 2278 */             node = node.m_nextChar[ch];
/* 2279 */             if (node != null)
/* 2280 */               return node.m_Value; 
/*      */           } 
/* 2282 */           return null;
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
/*      */       
/* 2294 */       key.getChars(0, len, this.m_charBuffer, 0);
/*      */       
/* 2296 */       for (int i = 0; i < len; i++) {
/*      */         
/* 2298 */         char c = this.m_charBuffer[i];
/* 2299 */         if ('' <= c)
/*      */         {
/*      */           
/* 2302 */           return null;
/*      */         }
/*      */         
/* 2305 */         node = node.m_nextChar[c];
/* 2306 */         if (node == null) {
/* 2307 */           return null;
/*      */         }
/*      */       } 
/* 2310 */       return node.m_Value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLongestKeyLength() {
/* 2320 */       return this.m_charBuffer.length;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/ToHTMLStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */