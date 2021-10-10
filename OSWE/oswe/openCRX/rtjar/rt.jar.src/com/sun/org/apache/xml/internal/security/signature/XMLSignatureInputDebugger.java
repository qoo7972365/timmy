/*     */ package com.sun.org.apache.xml.internal.security.signature;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Set;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLSignatureInputDebugger
/*     */ {
/*     */   private Set<Node> xpathNodeSet;
/*     */   private Set<String> inclusiveNamespaces;
/*  52 */   private Document doc = null;
/*     */ 
/*     */   
/*  55 */   private Writer writer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String HTMLPrefix = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String HTMLSuffix = "</pre></body></html>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String HTMLExcludePrefix = "<span class=\"EXCLUDED\">";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String HTMLIncludePrefix = "<span class=\"INCLUDED\">";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String HTMLIncludeOrExcludeSuffix = "</span>";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String HTMLIncludedInclusiveNamespacePrefix = "<span class=\"INCLUDEDINCLUSIVENAMESPACE\">";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String HTMLExcludedInclusiveNamespacePrefix = "<span class=\"EXCLUDEDINCLUSIVENAMESPACE\">";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int NODE_BEFORE_DOCUMENT_ELEMENT = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int NODE_AFTER_DOCUMENT_ELEMENT = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   static final AttrCompare ATTR_COMPARE = new AttrCompare();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInputDebugger(XMLSignatureInput paramXMLSignatureInput) {
/* 123 */     if (!paramXMLSignatureInput.isNodeSet()) {
/* 124 */       this.xpathNodeSet = null;
/*     */     } else {
/* 126 */       this.xpathNodeSet = paramXMLSignatureInput.getInputNodeSet();
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
/*     */   public XMLSignatureInputDebugger(XMLSignatureInput paramXMLSignatureInput, Set<String> paramSet) {
/* 140 */     this(paramXMLSignatureInput);
/* 141 */     this.inclusiveNamespaces = paramSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHTMLRepresentation() throws XMLSignatureException {
/* 151 */     if (this.xpathNodeSet == null || this.xpathNodeSet.size() == 0) {
/* 152 */       return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n<blink>no node set, sorry</blink></pre></body></html>";
/*     */     }
/*     */ 
/*     */     
/* 156 */     Node node = this.xpathNodeSet.iterator().next();
/*     */     
/* 158 */     this.doc = XMLUtils.getOwnerDocument(node);
/*     */     
/*     */     try {
/* 161 */       this.writer = new StringWriter();
/*     */       
/* 163 */       canonicalizeXPathNodeSet(this.doc);
/* 164 */       this.writer.close();
/*     */       
/* 166 */       return this.writer.toString();
/* 167 */     } catch (IOException iOException) {
/* 168 */       throw new XMLSignatureException("empty", iOException);
/*     */     } finally {
/* 170 */       this.xpathNodeSet = null;
/* 171 */       this.doc = null;
/* 172 */       this.writer = null;
/*     */     } 
/*     */   }
/*     */   private void canonicalizeXPathNodeSet(Node paramNode) throws XMLSignatureException, IOException {
/*     */     Node node1;
/*     */     int i;
/*     */     Node node2;
/*     */     NamedNodeMap namedNodeMap;
/*     */     int j;
/*     */     Attr[] arrayOfAttr1;
/*     */     byte b1;
/*     */     Attr[] arrayOfAttr2;
/*     */     byte b2;
/*     */     Node node3;
/* 186 */     short s = paramNode.getNodeType();
/* 187 */     switch (s) {
/*     */ 
/*     */       
/*     */       case 2:
/*     */       case 6:
/*     */       case 11:
/*     */       case 12:
/* 194 */         throw new XMLSignatureException("empty");
/*     */       case 9:
/* 196 */         this.writer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n");
/*     */         
/* 198 */         node1 = paramNode.getFirstChild();
/* 199 */         for (; node1 != null; node1 = node1.getNextSibling()) {
/* 200 */           canonicalizeXPathNodeSet(node1);
/*     */         }
/*     */         
/* 203 */         this.writer.write("</pre></body></html>");
/*     */         break;
/*     */       
/*     */       case 8:
/* 207 */         if (this.xpathNodeSet.contains(paramNode)) {
/* 208 */           this.writer.write("<span class=\"INCLUDED\">");
/*     */         } else {
/* 210 */           this.writer.write("<span class=\"EXCLUDED\">");
/*     */         } 
/*     */         
/* 213 */         i = getPositionRelativeToDocumentElement(paramNode);
/*     */         
/* 215 */         if (i == 1) {
/* 216 */           this.writer.write("\n");
/*     */         }
/*     */         
/* 219 */         outputCommentToWriter((Comment)paramNode);
/*     */         
/* 221 */         if (i == -1) {
/* 222 */           this.writer.write("\n");
/*     */         }
/*     */         
/* 225 */         this.writer.write("</span>");
/*     */         break;
/*     */       
/*     */       case 7:
/* 229 */         if (this.xpathNodeSet.contains(paramNode)) {
/* 230 */           this.writer.write("<span class=\"INCLUDED\">");
/*     */         } else {
/* 232 */           this.writer.write("<span class=\"EXCLUDED\">");
/*     */         } 
/*     */         
/* 235 */         i = getPositionRelativeToDocumentElement(paramNode);
/*     */         
/* 237 */         if (i == 1) {
/* 238 */           this.writer.write("\n");
/*     */         }
/*     */         
/* 241 */         outputPItoWriter((ProcessingInstruction)paramNode);
/*     */         
/* 243 */         if (i == -1) {
/* 244 */           this.writer.write("\n");
/*     */         }
/*     */         
/* 247 */         this.writer.write("</span>");
/*     */         break;
/*     */       
/*     */       case 3:
/*     */       case 4:
/* 252 */         if (this.xpathNodeSet.contains(paramNode)) {
/* 253 */           this.writer.write("<span class=\"INCLUDED\">");
/*     */         } else {
/* 255 */           this.writer.write("<span class=\"EXCLUDED\">");
/*     */         } 
/*     */         
/* 258 */         outputTextToWriter(paramNode.getNodeValue());
/*     */         
/* 260 */         node2 = paramNode.getNextSibling();
/*     */         
/* 262 */         for (; node2 != null && (node2.getNodeType() == 3 || node2
/* 263 */           .getNodeType() == 4); 
/* 264 */           node2 = node2.getNextSibling())
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 272 */           outputTextToWriter(node2.getNodeValue());
/*     */         }
/*     */         
/* 275 */         this.writer.write("</span>");
/*     */         break;
/*     */       
/*     */       case 1:
/* 279 */         node2 = paramNode;
/*     */         
/* 281 */         if (this.xpathNodeSet.contains(paramNode)) {
/* 282 */           this.writer.write("<span class=\"INCLUDED\">");
/*     */         } else {
/* 284 */           this.writer.write("<span class=\"EXCLUDED\">");
/*     */         } 
/*     */         
/* 287 */         this.writer.write("&lt;");
/* 288 */         this.writer.write(node2.getTagName());
/*     */         
/* 290 */         this.writer.write("</span>");
/*     */ 
/*     */         
/* 293 */         namedNodeMap = node2.getAttributes();
/* 294 */         j = namedNodeMap.getLength();
/* 295 */         arrayOfAttr1 = new Attr[j];
/*     */         
/* 297 */         for (b1 = 0; b1 < j; b1++) {
/* 298 */           arrayOfAttr1[b1] = (Attr)namedNodeMap.item(b1);
/*     */         }
/*     */         
/* 301 */         Arrays.sort(arrayOfAttr1, ATTR_COMPARE);
/* 302 */         arrayOfAttr2 = arrayOfAttr1;
/*     */         
/* 304 */         for (b2 = 0; b2 < j; b2++) {
/* 305 */           Attr attr = arrayOfAttr2[b2];
/* 306 */           boolean bool1 = this.xpathNodeSet.contains(attr);
/* 307 */           boolean bool2 = this.inclusiveNamespaces.contains(attr.getName());
/*     */           
/* 309 */           if (bool1) {
/* 310 */             if (bool2) {
/*     */               
/* 312 */               this.writer.write("<span class=\"INCLUDEDINCLUSIVENAMESPACE\">");
/*     */             } else {
/*     */               
/* 315 */               this.writer.write("<span class=\"INCLUDED\">");
/*     */             }
/*     */           
/* 318 */           } else if (bool2) {
/*     */             
/* 320 */             this.writer.write("<span class=\"EXCLUDEDINCLUSIVENAMESPACE\">");
/*     */           } else {
/*     */             
/* 323 */             this.writer.write("<span class=\"EXCLUDED\">");
/*     */           } 
/*     */ 
/*     */           
/* 327 */           outputAttrToWriter(attr.getNodeName(), attr.getNodeValue());
/* 328 */           this.writer.write("</span>");
/*     */         } 
/*     */         
/* 331 */         if (this.xpathNodeSet.contains(paramNode)) {
/* 332 */           this.writer.write("<span class=\"INCLUDED\">");
/*     */         } else {
/* 334 */           this.writer.write("<span class=\"EXCLUDED\">");
/*     */         } 
/*     */         
/* 337 */         this.writer.write("&gt;");
/*     */         
/* 339 */         this.writer.write("</span>");
/*     */ 
/*     */         
/* 342 */         node3 = paramNode.getFirstChild();
/* 343 */         for (; node3 != null; 
/* 344 */           node3 = node3.getNextSibling()) {
/* 345 */           canonicalizeXPathNodeSet(node3);
/*     */         }
/*     */         
/* 348 */         if (this.xpathNodeSet.contains(paramNode)) {
/* 349 */           this.writer.write("<span class=\"INCLUDED\">");
/*     */         } else {
/* 351 */           this.writer.write("<span class=\"EXCLUDED\">");
/*     */         } 
/*     */         
/* 354 */         this.writer.write("&lt;/");
/* 355 */         this.writer.write(node2.getTagName());
/* 356 */         this.writer.write("&gt;");
/*     */         
/* 358 */         this.writer.write("</span>");
/*     */         break;
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
/*     */   private int getPositionRelativeToDocumentElement(Node paramNode) {
/* 381 */     if (paramNode == null) {
/* 382 */       return 0;
/*     */     }
/*     */     
/* 385 */     Document document = paramNode.getOwnerDocument();
/*     */     
/* 387 */     if (paramNode.getParentNode() != document) {
/* 388 */       return 0;
/*     */     }
/*     */     
/* 391 */     Element element = document.getDocumentElement();
/*     */     
/* 393 */     if (element == null) {
/* 394 */       return 0;
/*     */     }
/*     */     
/* 397 */     if (element == paramNode) {
/* 398 */       return 0;
/*     */     }
/*     */     
/* 401 */     for (Node node = paramNode; node != null; node = node.getNextSibling()) {
/* 402 */       if (node == element) {
/* 403 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 407 */     return 1;
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
/*     */   private void outputAttrToWriter(String paramString1, String paramString2) throws IOException {
/* 429 */     this.writer.write(" ");
/* 430 */     this.writer.write(paramString1);
/* 431 */     this.writer.write("=\"");
/*     */     
/* 433 */     int i = paramString2.length();
/*     */     
/* 435 */     for (byte b = 0; b < i; b++) {
/* 436 */       char c = paramString2.charAt(b);
/*     */       
/* 438 */       switch (c) {
/*     */         
/*     */         case '&':
/* 441 */           this.writer.write("&amp;amp;");
/*     */           break;
/*     */         
/*     */         case '<':
/* 445 */           this.writer.write("&amp;lt;");
/*     */           break;
/*     */         
/*     */         case '"':
/* 449 */           this.writer.write("&amp;quot;");
/*     */           break;
/*     */         
/*     */         case '\t':
/* 453 */           this.writer.write("&amp;#x9;");
/*     */           break;
/*     */         
/*     */         case '\n':
/* 457 */           this.writer.write("&amp;#xA;");
/*     */           break;
/*     */         
/*     */         case '\r':
/* 461 */           this.writer.write("&amp;#xD;");
/*     */           break;
/*     */         
/*     */         default:
/* 465 */           this.writer.write(c);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 470 */     this.writer.write("\"");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void outputPItoWriter(ProcessingInstruction paramProcessingInstruction) throws IOException {
/* 481 */     if (paramProcessingInstruction == null) {
/*     */       return;
/*     */     }
/*     */     
/* 485 */     this.writer.write("&lt;?");
/*     */     
/* 487 */     String str1 = paramProcessingInstruction.getTarget();
/* 488 */     int i = str1.length();
/*     */     
/* 490 */     for (byte b = 0; b < i; b++) {
/* 491 */       char c = str1.charAt(b);
/*     */       
/* 493 */       switch (c) {
/*     */         
/*     */         case '\r':
/* 496 */           this.writer.write("&amp;#xD;");
/*     */           break;
/*     */         
/*     */         case ' ':
/* 500 */           this.writer.write("&middot;");
/*     */           break;
/*     */         
/*     */         case '\n':
/* 504 */           this.writer.write("&para;\n");
/*     */           break;
/*     */         
/*     */         default:
/* 508 */           this.writer.write(c);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 513 */     String str2 = paramProcessingInstruction.getData();
/*     */     
/* 515 */     i = str2.length();
/*     */     
/* 517 */     if (i > 0) {
/* 518 */       this.writer.write(" ");
/*     */       
/* 520 */       for (byte b1 = 0; b1 < i; b1++) {
/* 521 */         char c = str2.charAt(b1);
/*     */         
/* 523 */         switch (c) {
/*     */           
/*     */           case '\r':
/* 526 */             this.writer.write("&amp;#xD;");
/*     */             break;
/*     */           
/*     */           default:
/* 530 */             this.writer.write(c);
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 536 */     this.writer.write("?&gt;");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void outputCommentToWriter(Comment paramComment) throws IOException {
/* 547 */     if (paramComment == null) {
/*     */       return;
/*     */     }
/*     */     
/* 551 */     this.writer.write("&lt;!--");
/*     */     
/* 553 */     String str = paramComment.getData();
/* 554 */     int i = str.length();
/*     */     
/* 556 */     for (byte b = 0; b < i; b++) {
/* 557 */       char c = str.charAt(b);
/*     */       
/* 559 */       switch (c) {
/*     */         
/*     */         case '\r':
/* 562 */           this.writer.write("&amp;#xD;");
/*     */           break;
/*     */         
/*     */         case ' ':
/* 566 */           this.writer.write("&middot;");
/*     */           break;
/*     */         
/*     */         case '\n':
/* 570 */           this.writer.write("&para;\n");
/*     */           break;
/*     */         
/*     */         default:
/* 574 */           this.writer.write(c);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 579 */     this.writer.write("--&gt;");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void outputTextToWriter(String paramString) throws IOException {
/* 589 */     if (paramString == null) {
/*     */       return;
/*     */     }
/*     */     
/* 593 */     int i = paramString.length();
/*     */     
/* 595 */     for (byte b = 0; b < i; b++) {
/* 596 */       char c = paramString.charAt(b);
/*     */       
/* 598 */       switch (c) {
/*     */         
/*     */         case '&':
/* 601 */           this.writer.write("&amp;amp;");
/*     */           break;
/*     */         
/*     */         case '<':
/* 605 */           this.writer.write("&amp;lt;");
/*     */           break;
/*     */         
/*     */         case '>':
/* 609 */           this.writer.write("&amp;gt;");
/*     */           break;
/*     */         
/*     */         case '\r':
/* 613 */           this.writer.write("&amp;#xD;");
/*     */           break;
/*     */         
/*     */         case ' ':
/* 617 */           this.writer.write("&middot;");
/*     */           break;
/*     */         
/*     */         case '\n':
/* 621 */           this.writer.write("&para;\n");
/*     */           break;
/*     */         
/*     */         default:
/* 625 */           this.writer.write(c);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/signature/XMLSignatureInputDebugger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */