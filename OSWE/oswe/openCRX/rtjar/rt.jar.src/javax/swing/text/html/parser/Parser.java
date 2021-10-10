/*      */ package javax.swing.text.html.parser;
/*      */ 
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InterruptedIOException;
/*      */ import java.io.Reader;
/*      */ import java.util.Vector;
/*      */ import javax.swing.text.ChangedCharSetException;
/*      */ import javax.swing.text.SimpleAttributeSet;
/*      */ import javax.swing.text.html.HTML;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Parser
/*      */   implements DTDConstants
/*      */ {
/*   83 */   private char[] text = new char[1024];
/*   84 */   private int textpos = 0;
/*      */   
/*      */   private TagElement last;
/*      */   private boolean space;
/*   88 */   private char[] str = new char[128];
/*   89 */   private int strpos = 0;
/*      */   
/*   91 */   protected DTD dtd = null;
/*      */   
/*      */   private int ch;
/*      */   
/*      */   private int ln;
/*      */   private Reader in;
/*      */   private Element recent;
/*      */   private TagStack stack;
/*      */   private boolean skipTag = false;
/*  100 */   private TagElement lastFormSent = null;
/*  101 */   private SimpleAttributeSet attributes = new SimpleAttributeSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean seenHtml = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean seenHead = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean seenBody = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoreSpace;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean strict = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int crlfCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int crCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lfCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int currentBlockStartPos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lastBlockStartPos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  168 */   private static final char[] cp1252Map = new char[] { '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', '', '', '', '', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '', '', 'Ÿ' };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String START_COMMENT = "<!--";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String END_COMMENT = "-->";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getCurrentLine() {
/*  210 */     return this.ln;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getBlockStartPosition() {
/*  221 */     return Math.max(0, this.lastBlockStartPos - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TagElement makeTag(Element paramElement, boolean paramBoolean) {
/*  228 */     return new TagElement(paramElement, paramBoolean);
/*      */   }
/*      */   
/*      */   protected TagElement makeTag(Element paramElement) {
/*  232 */     return makeTag(paramElement, false);
/*      */   }
/*      */   
/*      */   protected SimpleAttributeSet getAttributes() {
/*  236 */     return this.attributes;
/*      */   }
/*      */   
/*      */   protected void flushAttributes() {
/*  240 */     this.attributes.removeAttributes(this.attributes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleText(char[] paramArrayOfchar) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleTitle(char[] paramArrayOfchar) {
/*  255 */     handleText(paramArrayOfchar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleComment(char[] paramArrayOfchar) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleEOFInComment() {
/*  271 */     int i = strIndexOf('\n');
/*  272 */     if (i >= 0) {
/*  273 */       handleComment(getChars(0, i));
/*      */       try {
/*  275 */         this.in.close();
/*  276 */         this.in = new CharArrayReader(getChars(i + 1));
/*  277 */         this.ch = 62;
/*  278 */       } catch (IOException iOException) {
/*  279 */         error("ioexception");
/*      */       } 
/*      */       
/*  282 */       resetStrBuffer();
/*      */     } else {
/*      */       
/*  285 */       error("eof.comment");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleEmptyTag(TagElement paramTagElement) throws ChangedCharSetException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleStartTag(TagElement paramTagElement) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleEndTag(TagElement paramTagElement) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleError(int paramInt, String paramString) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void handleText(TagElement paramTagElement) {
/*  323 */     if (paramTagElement.breaksFlow()) {
/*  324 */       this.space = false;
/*  325 */       if (!this.strict) {
/*  326 */         this.ignoreSpace = true;
/*      */       }
/*      */     } 
/*  329 */     if (this.textpos == 0 && (
/*  330 */       !this.space || this.stack == null || this.last.breaksFlow() || 
/*  331 */       !this.stack.advance(this.dtd.pcdata))) {
/*  332 */       this.last = paramTagElement;
/*  333 */       this.space = false;
/*  334 */       this.lastBlockStartPos = this.currentBlockStartPos;
/*      */       
/*      */       return;
/*      */     } 
/*  338 */     if (this.space) {
/*  339 */       if (!this.ignoreSpace) {
/*      */         
/*  341 */         if (this.textpos + 1 > this.text.length) {
/*  342 */           char[] arrayOfChar1 = new char[this.text.length + 200];
/*  343 */           System.arraycopy(this.text, 0, arrayOfChar1, 0, this.text.length);
/*  344 */           this.text = arrayOfChar1;
/*      */         } 
/*      */ 
/*      */         
/*  348 */         this.text[this.textpos++] = ' ';
/*  349 */         if (!this.strict && !paramTagElement.getElement().isEmpty()) {
/*  350 */           this.ignoreSpace = true;
/*      */         }
/*      */       } 
/*  353 */       this.space = false;
/*      */     } 
/*  355 */     char[] arrayOfChar = new char[this.textpos];
/*  356 */     System.arraycopy(this.text, 0, arrayOfChar, 0, this.textpos);
/*      */ 
/*      */     
/*  359 */     if (paramTagElement.getElement().getName().equals("title")) {
/*  360 */       handleTitle(arrayOfChar);
/*      */     } else {
/*  362 */       handleText(arrayOfChar);
/*      */     } 
/*  364 */     this.lastBlockStartPos = this.currentBlockStartPos;
/*  365 */     this.textpos = 0;
/*  366 */     this.last = paramTagElement;
/*  367 */     this.space = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void error(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  375 */     handleError(this.ln, paramString1 + " " + paramString2 + " " + paramString3 + " " + paramString4);
/*      */   }
/*      */   
/*      */   protected void error(String paramString1, String paramString2, String paramString3) {
/*  379 */     error(paramString1, paramString2, paramString3, "?");
/*      */   }
/*      */   protected void error(String paramString1, String paramString2) {
/*  382 */     error(paramString1, paramString2, "?", "?");
/*      */   }
/*      */   protected void error(String paramString) {
/*  385 */     error(paramString, "?", "?", "?");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void startTag(TagElement paramTagElement) throws ChangedCharSetException {
/*  395 */     Element element = paramTagElement.getElement();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     if (!element.isEmpty() || (this.last != null && 
/*  403 */       !this.last.breaksFlow()) || this.textpos != 0) {
/*      */       
/*  405 */       handleText(paramTagElement);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  411 */       this.last = paramTagElement;
/*      */ 
/*      */       
/*  414 */       this.space = false;
/*      */     } 
/*  416 */     this.lastBlockStartPos = this.currentBlockStartPos;
/*      */ 
/*      */     
/*  419 */     for (AttributeList attributeList = element.atts; attributeList != null; attributeList = attributeList.next) {
/*  420 */       if (attributeList.modifier == 2 && (this.attributes
/*  421 */         .isEmpty() || (
/*  422 */         !this.attributes.isDefined(attributeList.name) && 
/*  423 */         !this.attributes.isDefined(HTML.getAttributeKey(attributeList.name))))) {
/*  424 */         error("req.att ", attributeList.getName(), element.getName());
/*      */       }
/*      */     } 
/*      */     
/*  428 */     if (element.isEmpty()) {
/*  429 */       handleEmptyTag(paramTagElement);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  435 */       this.recent = element;
/*  436 */       this.stack = new TagStack(paramTagElement, this.stack);
/*  437 */       handleStartTag(paramTagElement);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void endTag(boolean paramBoolean) {
/*  446 */     handleText(this.stack.tag);
/*      */     
/*  448 */     if (paramBoolean && !this.stack.elem.omitEnd()) {
/*  449 */       error("end.missing", this.stack.elem.getName());
/*  450 */     } else if (!this.stack.terminate()) {
/*  451 */       error("end.unexpected", this.stack.elem.getName());
/*      */     } 
/*      */ 
/*      */     
/*  455 */     handleEndTag(this.stack.tag);
/*  456 */     this.stack = this.stack.next;
/*  457 */     this.recent = (this.stack != null) ? this.stack.elem : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean ignoreElement(Element paramElement) {
/*  463 */     String str1 = this.stack.elem.getName();
/*  464 */     String str2 = paramElement.getName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  471 */     if ((str2.equals("html") && this.seenHtml) || (str2
/*  472 */       .equals("head") && this.seenHead) || (str2
/*  473 */       .equals("body") && this.seenBody)) {
/*  474 */       return true;
/*      */     }
/*  476 */     if (str2.equals("dt") || str2.equals("dd")) {
/*  477 */       TagStack tagStack = this.stack;
/*  478 */       while (tagStack != null && !tagStack.elem.getName().equals("dl")) {
/*  479 */         tagStack = tagStack.next;
/*      */       }
/*  481 */       if (tagStack == null) {
/*  482 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  486 */     if ((str1.equals("table") && 
/*  487 */       !str2.equals("#pcdata") && !str2.equals("input")) || (str2
/*  488 */       .equals("font") && (str1
/*  489 */       .equals("ul") || str1.equals("ol"))) || (str2
/*  490 */       .equals("meta") && this.stack != null) || (str2
/*  491 */       .equals("style") && this.seenBody) || (str1
/*  492 */       .equals("table") && str2.equals("a"))) {
/*  493 */       return true;
/*      */     }
/*  495 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void markFirstTime(Element paramElement) {
/*  504 */     String str = paramElement.getName();
/*  505 */     if (str.equals("html")) {
/*  506 */       this.seenHtml = true;
/*  507 */     } else if (str.equals("head")) {
/*  508 */       this.seenHead = true;
/*  509 */     } else if (str.equals("body")) {
/*  510 */       if (this.buf.length == 1) {
/*      */         
/*  512 */         char[] arrayOfChar = new char[256];
/*      */         
/*  514 */         arrayOfChar[0] = this.buf[0];
/*  515 */         this.buf = arrayOfChar;
/*      */       } 
/*  517 */       this.seenBody = true;
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
/*      */   boolean legalElementContext(Element paramElement) throws ChangedCharSetException {
/*  529 */     if (this.stack == null) {
/*      */       
/*  531 */       if (paramElement != this.dtd.html) {
/*      */         
/*  533 */         startTag(makeTag(this.dtd.html, true));
/*  534 */         return legalElementContext(paramElement);
/*      */       } 
/*  536 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  540 */     if (this.stack.advance(paramElement)) {
/*      */       
/*  542 */       markFirstTime(paramElement);
/*  543 */       return true;
/*      */     } 
/*  545 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  572 */     String str1 = this.stack.elem.getName();
/*  573 */     String str2 = paramElement.getName();
/*      */ 
/*      */     
/*  576 */     if (!this.strict && ((str1
/*  577 */       .equals("table") && str2.equals("td")) || (str1
/*  578 */       .equals("table") && str2.equals("th")) || (str1
/*  579 */       .equals("tr") && !str2.equals("tr")))) {
/*  580 */       bool = true;
/*      */     }
/*      */ 
/*      */     
/*  584 */     if (!this.strict && !bool && (this.stack.elem.getName() != paramElement.getName() || paramElement
/*  585 */       .getName().equals("body")) && (
/*  586 */       this.skipTag = ignoreElement(paramElement))) {
/*  587 */       error("tag.ignore", paramElement.getName());
/*  588 */       return this.skipTag;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  595 */     if (!this.strict && str1.equals("table") && 
/*  596 */       !str2.equals("tr") && !str2.equals("td") && 
/*  597 */       !str2.equals("th") && !str2.equals("caption")) {
/*  598 */       Element element1 = this.dtd.getElement("tr");
/*  599 */       TagElement tagElement = makeTag(element1, true);
/*  600 */       legalTagContext(tagElement);
/*  601 */       startTag(tagElement);
/*  602 */       error("start.missing", paramElement.getName());
/*  603 */       return legalElementContext(paramElement);
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
/*  614 */     if (!bool && this.stack.terminate() && (!this.strict || this.stack.elem.omitEnd())) {
/*  615 */       for (TagStack tagStack = this.stack.next; tagStack != null; tagStack = tagStack.next) {
/*  616 */         if (tagStack.advance(paramElement)) {
/*  617 */           while (this.stack != tagStack) {
/*  618 */             endTag(true);
/*      */           }
/*  620 */           return true;
/*      */         } 
/*  622 */         if (!tagStack.terminate() || (this.strict && !tagStack.elem.omitEnd())) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  632 */     Element element = this.stack.first();
/*  633 */     if (element != null && (!this.strict || element.omitStart()) && (element != this.dtd.head || paramElement != this.dtd.pcdata)) {
/*      */ 
/*      */       
/*  636 */       TagElement tagElement = makeTag(element, true);
/*  637 */       legalTagContext(tagElement);
/*  638 */       startTag(tagElement);
/*  639 */       if (!element.omitStart()) {
/*  640 */         error("start.missing", paramElement.getName());
/*      */       }
/*  642 */       return legalElementContext(paramElement);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  650 */     if (!this.strict) {
/*  651 */       ContentModel contentModel = this.stack.contentModel();
/*  652 */       Vector<Element> vector = new Vector();
/*  653 */       if (contentModel != null) {
/*  654 */         contentModel.getElements(vector);
/*  655 */         for (Element element1 : vector) {
/*      */ 
/*      */ 
/*      */           
/*  659 */           if (this.stack.excluded(element1.getIndex())) {
/*      */             continue;
/*      */           }
/*      */           
/*  663 */           boolean bool1 = false;
/*      */           
/*  665 */           for (AttributeList attributeList = element1.getAttributes(); attributeList != null; attributeList = attributeList.next) {
/*  666 */             if (attributeList.modifier == 2) {
/*  667 */               bool1 = true;
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */           
/*  674 */           if (bool1) {
/*      */             continue;
/*      */           }
/*      */           
/*  678 */           ContentModel contentModel1 = element1.getContent();
/*  679 */           if (contentModel1 != null && contentModel1.first(paramElement)) {
/*      */             
/*  681 */             TagElement tagElement = makeTag(element1, true);
/*  682 */             legalTagContext(tagElement);
/*  683 */             startTag(tagElement);
/*  684 */             error("start.missing", element1.getName());
/*  685 */             return legalElementContext(paramElement);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  695 */     if (this.stack.terminate() && this.stack.elem != this.dtd.body && (!this.strict || this.stack.elem.omitEnd())) {
/*      */       
/*  697 */       if (!this.stack.elem.omitEnd()) {
/*  698 */         error("end.missing", paramElement.getName());
/*      */       }
/*      */       
/*  701 */       endTag(true);
/*  702 */       return legalElementContext(paramElement);
/*      */     } 
/*      */ 
/*      */     
/*  706 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void legalTagContext(TagElement paramTagElement) throws ChangedCharSetException {
/*  713 */     if (legalElementContext(paramTagElement.getElement())) {
/*  714 */       markFirstTime(paramTagElement.getElement());
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  719 */     if (paramTagElement.breaksFlow() && this.stack != null && !this.stack.tag.breaksFlow()) {
/*  720 */       endTag(true);
/*  721 */       legalTagContext(paramTagElement);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  726 */     for (TagStack tagStack = this.stack; tagStack != null; tagStack = tagStack.next) {
/*  727 */       if (tagStack.tag.getElement() == this.dtd.head) {
/*  728 */         while (this.stack != tagStack) {
/*  729 */           endTag(true);
/*      */         }
/*  731 */         endTag(true);
/*  732 */         legalTagContext(paramTagElement);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  738 */     error("tag.unexpected", paramTagElement.getElement().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void errorContext() throws ChangedCharSetException {
/*  746 */     for (; this.stack != null && this.stack.tag.getElement() != this.dtd.body; this.stack = this.stack.next) {
/*  747 */       handleEndTag(this.stack.tag);
/*      */     }
/*  749 */     if (this.stack == null) {
/*  750 */       legalElementContext(this.dtd.body);
/*  751 */       startTag(makeTag(this.dtd.body, true));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addString(int paramInt) {
/*  759 */     if (this.strpos == this.str.length) {
/*  760 */       char[] arrayOfChar = new char[this.str.length + 128];
/*  761 */       System.arraycopy(this.str, 0, arrayOfChar, 0, this.str.length);
/*  762 */       this.str = arrayOfChar;
/*      */     } 
/*  764 */     this.str[this.strpos++] = (char)paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getString(int paramInt) {
/*  771 */     char[] arrayOfChar = new char[this.strpos - paramInt];
/*  772 */     System.arraycopy(this.str, paramInt, arrayOfChar, 0, this.strpos - paramInt);
/*  773 */     this.strpos = paramInt;
/*  774 */     return new String(arrayOfChar);
/*      */   }
/*      */   
/*      */   char[] getChars(int paramInt) {
/*  778 */     char[] arrayOfChar = new char[this.strpos - paramInt];
/*  779 */     System.arraycopy(this.str, paramInt, arrayOfChar, 0, this.strpos - paramInt);
/*  780 */     this.strpos = paramInt;
/*  781 */     return arrayOfChar;
/*      */   }
/*      */   
/*      */   char[] getChars(int paramInt1, int paramInt2) {
/*  785 */     char[] arrayOfChar = new char[paramInt2 - paramInt1];
/*  786 */     System.arraycopy(this.str, paramInt1, arrayOfChar, 0, paramInt2 - paramInt1);
/*      */ 
/*      */     
/*  789 */     return arrayOfChar;
/*      */   }
/*      */   
/*      */   void resetStrBuffer() {
/*  793 */     this.strpos = 0;
/*      */   }
/*      */   
/*      */   int strIndexOf(char paramChar) {
/*  797 */     for (byte b = 0; b < this.strpos; b++) {
/*  798 */       if (this.str[b] == paramChar) {
/*  799 */         return b;
/*      */       }
/*      */     } 
/*      */     
/*  803 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void skipSpace() throws IOException {
/*      */     while (true) {
/*  812 */       switch (this.ch) {
/*      */         case 10:
/*  814 */           this.ln++;
/*  815 */           this.ch = readCh();
/*  816 */           this.lfCount++;
/*      */           continue;
/*      */         
/*      */         case 13:
/*  820 */           this.ln++;
/*  821 */           if ((this.ch = readCh()) == 10) {
/*  822 */             this.ch = readCh();
/*  823 */             this.crlfCount++;
/*      */             continue;
/*      */           } 
/*  826 */           this.crCount++;
/*      */           continue;
/*      */         
/*      */         case 9:
/*      */         case 32:
/*  831 */           this.ch = readCh();
/*      */           continue;
/*      */       } 
/*      */       break;
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
/*      */   boolean parseIdentifier(boolean paramBoolean) throws IOException {
/*  846 */     switch (this.ch) { case 65: case 66: case 67: case 68: case 69: case 70: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79: case 80: case 81: case 82: case 83: case 84: case 85:
/*      */       case 86:
/*      */       case 87:
/*      */       case 88:
/*      */       case 89:
/*      */       case 90:
/*  852 */         if (paramBoolean)
/*  853 */           this.ch = 97 + this.ch - 65;  break;
/*      */       case 97: case 98: case 99: case 100: case 101: case 102: case 103: case 104: case 105: case 106: case 107: case 108: case 109: case 110: case 111: case 112: case 113: case 114: case 115:
/*      */       case 116:
/*      */       case 117:
/*      */       case 118:
/*      */       case 119:
/*      */       case 120:
/*      */       case 121:
/*      */       case 122:
/*      */         break;
/*      */       default:
/*  864 */         return false; }
/*      */ 
/*      */     
/*      */     while (true)
/*  868 */     { addString(this.ch);
/*      */       
/*  870 */       switch (this.ch = readCh()) { case 65: case 66: case 67: case 68: case 69: case 70: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79: case 80: case 81: case 82: case 83: case 84: case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         case 89:
/*      */         case 90:
/*  876 */           if (paramBoolean)
/*  877 */             this.ch = 97 + this.ch - 65;  continue;
/*      */         case 45: case 46: case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: case 95: case 97: case 98: case 99: case 100: case 101: case 102: case 103: case 104: case 105: case 106:
/*      */         case 107:
/*      */         case 108:
/*      */         case 109:
/*      */         case 110:
/*      */         case 111:
/*      */         case 112:
/*      */         case 113:
/*      */         case 114:
/*      */         case 115:
/*      */         case 116:
/*      */         case 117:
/*      */         case 118:
/*      */         case 119:
/*      */         case 120:
/*      */         case 121:
/*      */         case 122:
/*  895 */           continue; }  break; }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char[] parseEntityReference() throws IOException {
/*  904 */     int i = this.strpos;
/*      */     
/*  906 */     if ((this.ch = readCh()) == 35) {
/*  907 */       int j = 0;
/*  908 */       this.ch = readCh();
/*  909 */       if ((this.ch >= 48 && this.ch <= 57) || this.ch == 120 || this.ch == 88) {
/*      */ 
/*      */         
/*  912 */         if (this.ch >= 48 && this.ch <= 57) {
/*      */           
/*  914 */           while (this.ch >= 48 && this.ch <= 57) {
/*  915 */             j = j * 10 + this.ch - 48;
/*  916 */             this.ch = readCh();
/*      */           } 
/*      */         } else {
/*      */           
/*  920 */           this.ch = readCh();
/*  921 */           char c = (char)Character.toLowerCase(this.ch);
/*  922 */           while ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')) {
/*      */             
/*  924 */             if (c >= '0' && c <= '9') {
/*  925 */               j = j * 16 + c - 48;
/*      */             } else {
/*  927 */               j = j * 16 + c - 97 + 10;
/*      */             } 
/*  929 */             this.ch = readCh();
/*  930 */             c = (char)Character.toLowerCase(this.ch);
/*      */           } 
/*      */         } 
/*  933 */         switch (this.ch) {
/*      */           case 10:
/*  935 */             this.ln++;
/*  936 */             this.ch = readCh();
/*  937 */             this.lfCount++;
/*      */             break;
/*      */           
/*      */           case 13:
/*  941 */             this.ln++;
/*  942 */             if ((this.ch = readCh()) == 10) {
/*  943 */               this.ch = readCh();
/*  944 */               this.crlfCount++;
/*      */               break;
/*      */             } 
/*  947 */             this.crCount++;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 59:
/*  952 */             this.ch = readCh();
/*      */             break;
/*      */         } 
/*  955 */         return mapNumericReference(j);
/*      */       } 
/*      */       
/*  958 */       addString(35);
/*  959 */       if (!parseIdentifier(false)) {
/*  960 */         error("ident.expected");
/*  961 */         this.strpos = i;
/*  962 */         return new char[] { '&', '#' };
/*      */       }
/*      */     
/*  965 */     } else if (!parseIdentifier(false)) {
/*  966 */       return new char[] { '&' };
/*      */     } 
/*      */ 
/*      */     
/*  970 */     boolean bool = false;
/*      */     
/*  972 */     switch (this.ch) {
/*      */       case 10:
/*  974 */         this.ln++;
/*  975 */         this.ch = readCh();
/*  976 */         this.lfCount++;
/*      */         break;
/*      */       
/*      */       case 13:
/*  980 */         this.ln++;
/*  981 */         if ((this.ch = readCh()) == 10) {
/*  982 */           this.ch = readCh();
/*  983 */           this.crlfCount++;
/*      */           break;
/*      */         } 
/*  986 */         this.crCount++;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 59:
/*  991 */         bool = true;
/*      */         
/*  993 */         this.ch = readCh();
/*      */         break;
/*      */     } 
/*      */     
/*  997 */     String str = getString(i);
/*  998 */     Entity entity = this.dtd.getEntity(str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1004 */     if (!this.strict && entity == null) {
/* 1005 */       entity = this.dtd.getEntity(str.toLowerCase());
/*      */     }
/* 1007 */     if (entity == null || !entity.isGeneral()) {
/*      */       
/* 1009 */       if (str.length() == 0) {
/* 1010 */         error("invalid.entref", str);
/* 1011 */         return new char[0];
/*      */       } 
/*      */       
/* 1014 */       String str1 = "&" + str + (bool ? ";" : "");
/*      */       
/* 1016 */       char[] arrayOfChar = new char[str1.length()];
/* 1017 */       str1.getChars(0, arrayOfChar.length, arrayOfChar, 0);
/* 1018 */       return arrayOfChar;
/*      */     } 
/* 1020 */     return entity.getData();
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
/*      */   private char[] mapNumericReference(int paramInt) {
/*      */     char[] arrayOfChar;
/* 1037 */     if (paramInt >= 65535) {
/*      */       try {
/* 1039 */         arrayOfChar = Character.toChars(paramInt);
/* 1040 */       } catch (IllegalArgumentException illegalArgumentException) {
/* 1041 */         arrayOfChar = new char[0];
/*      */       } 
/*      */     } else {
/* 1044 */       arrayOfChar = new char[1];
/* 1045 */       arrayOfChar[0] = (paramInt < 130 || paramInt > 159) ? (char)paramInt : cp1252Map[paramInt - 130];
/*      */     } 
/* 1047 */     return arrayOfChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void parseComment() throws IOException {
/*      */     while (true) {
/* 1056 */       int i = this.ch;
/* 1057 */       switch (i) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 45:
/* 1069 */           if (!this.strict && this.strpos != 0 && this.str[this.strpos - 1] == '-') {
/* 1070 */             if ((this.ch = readCh()) == 62) {
/*      */               return;
/*      */             }
/* 1073 */             if (this.ch == 33) {
/* 1074 */               if ((this.ch = readCh()) == 62) {
/*      */                 return;
/*      */               }
/*      */               
/* 1078 */               addString(45);
/* 1079 */               addString(33);
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/* 1086 */           if ((this.ch = readCh()) == 45) {
/* 1087 */             this.ch = readCh();
/* 1088 */             if (this.strict || this.ch == 62) {
/*      */               return;
/*      */             }
/* 1091 */             if (this.ch == 33) {
/* 1092 */               if ((this.ch = readCh()) == 62) {
/*      */                 return;
/*      */               }
/*      */               
/* 1096 */               addString(45);
/* 1097 */               addString(33);
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/* 1102 */             addString(45);
/*      */           } 
/*      */           break;
/*      */         
/*      */         case -1:
/* 1107 */           handleEOFInComment();
/*      */           return;
/*      */         
/*      */         case 10:
/* 1111 */           this.ln++;
/* 1112 */           this.ch = readCh();
/* 1113 */           this.lfCount++;
/*      */           break;
/*      */         
/*      */         case 62:
/* 1117 */           this.ch = readCh();
/*      */           break;
/*      */         
/*      */         case 13:
/* 1121 */           this.ln++;
/* 1122 */           if ((this.ch = readCh()) == 10) {
/* 1123 */             this.ch = readCh();
/* 1124 */             this.crlfCount++;
/*      */           } else {
/*      */             
/* 1127 */             this.crCount++;
/*      */           } 
/* 1129 */           i = 10;
/*      */           break;
/*      */         default:
/* 1132 */           this.ch = readCh();
/*      */           break;
/*      */       } 
/*      */       
/* 1136 */       addString(i);
/*      */     } 
/*      */   }
/*      */   
/*      */   void parseLiteral(boolean paramBoolean) throws IOException {
/*      */     while (true) {
/*      */       int j;
/*      */       byte b;
/*      */       char[] arrayOfChar;
/* 1145 */       int i = this.ch;
/* 1146 */       switch (i) {
/*      */         case -1:
/* 1148 */           error("eof.literal", this.stack.elem.getName());
/* 1149 */           endTag(true);
/*      */           return;
/*      */         
/*      */         case 62:
/* 1153 */           this.ch = readCh();
/* 1154 */           j = this.textpos - this.stack.elem.name.length() + 2; b = 0;
/*      */ 
/*      */           
/* 1157 */           if (j >= 0 && this.text[j++] == '<' && this.text[j] == '/') {
/* 1158 */             while (++j < this.textpos && 
/* 1159 */               Character.toLowerCase(this.text[j]) == this.stack.elem.name.charAt(b++));
/* 1160 */             if (j == this.textpos) {
/* 1161 */               this.textpos -= this.stack.elem.name.length() + 2;
/* 1162 */               if (this.textpos > 0 && this.text[this.textpos - 1] == '\n') {
/* 1163 */                 this.textpos--;
/*      */               }
/* 1165 */               endTag(false);
/*      */               return;
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 38:
/* 1172 */           arrayOfChar = parseEntityReference();
/* 1173 */           if (this.textpos + arrayOfChar.length > this.text.length) {
/* 1174 */             char[] arrayOfChar1 = new char[Math.max(this.textpos + arrayOfChar.length + 128, this.text.length * 2)];
/* 1175 */             System.arraycopy(this.text, 0, arrayOfChar1, 0, this.text.length);
/* 1176 */             this.text = arrayOfChar1;
/*      */           } 
/* 1178 */           System.arraycopy(arrayOfChar, 0, this.text, this.textpos, arrayOfChar.length);
/* 1179 */           this.textpos += arrayOfChar.length;
/*      */           continue;
/*      */         
/*      */         case 10:
/* 1183 */           this.ln++;
/* 1184 */           this.ch = readCh();
/* 1185 */           this.lfCount++;
/*      */           break;
/*      */         
/*      */         case 13:
/* 1189 */           this.ln++;
/* 1190 */           if ((this.ch = readCh()) == 10) {
/* 1191 */             this.ch = readCh();
/* 1192 */             this.crlfCount++;
/*      */           } else {
/*      */             
/* 1195 */             this.crCount++;
/*      */           } 
/* 1197 */           i = 10;
/*      */           break;
/*      */         default:
/* 1200 */           this.ch = readCh();
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1205 */       if (this.textpos == this.text.length) {
/* 1206 */         char[] arrayOfChar1 = new char[this.text.length + 128];
/* 1207 */         System.arraycopy(this.text, 0, arrayOfChar1, 0, this.text.length);
/* 1208 */         this.text = arrayOfChar1;
/*      */       } 
/* 1210 */       this.text[this.textpos++] = (char)i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String parseAttributeValue(boolean paramBoolean) throws IOException {
/* 1218 */     int i = -1;
/*      */ 
/*      */     
/* 1221 */     switch (this.ch) {
/*      */       case 34:
/*      */       case 39:
/* 1224 */         i = this.ch;
/* 1225 */         this.ch = readCh();
/*      */         break;
/*      */     } 
/*      */     while (true) {
/*      */       char[] arrayOfChar;
/*      */       byte b;
/* 1231 */       int j = this.ch;
/*      */       
/* 1233 */       switch (j) {
/*      */         case 10:
/* 1235 */           this.ln++;
/* 1236 */           this.ch = readCh();
/* 1237 */           this.lfCount++;
/* 1238 */           if (i < 0) {
/* 1239 */             return getString(0);
/*      */           }
/*      */           break;
/*      */         
/*      */         case 13:
/* 1244 */           this.ln++;
/*      */           
/* 1246 */           if ((this.ch = readCh()) == 10) {
/* 1247 */             this.ch = readCh();
/* 1248 */             this.crlfCount++;
/*      */           } else {
/*      */             
/* 1251 */             this.crCount++;
/*      */           } 
/* 1253 */           if (i < 0) {
/* 1254 */             return getString(0);
/*      */           }
/*      */           break;
/*      */         
/*      */         case 9:
/* 1259 */           if (i < 0)
/* 1260 */             j = 32; 
/*      */         case 32:
/* 1262 */           this.ch = readCh();
/* 1263 */           if (i < 0) {
/* 1264 */             return getString(0);
/*      */           }
/*      */           break;
/*      */         
/*      */         case 60:
/*      */         case 62:
/* 1270 */           if (i < 0) {
/* 1271 */             return getString(0);
/*      */           }
/* 1273 */           this.ch = readCh();
/*      */           break;
/*      */         
/*      */         case 34:
/*      */         case 39:
/* 1278 */           this.ch = readCh();
/* 1279 */           if (j == i)
/* 1280 */             return getString(0); 
/* 1281 */           if (i == -1) {
/* 1282 */             error("attvalerr");
/* 1283 */             if (this.strict || this.ch == 32) {
/* 1284 */               return getString(0);
/*      */             }
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */ 
/*      */         
/*      */         case 61:
/* 1292 */           if (i < 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1297 */             error("attvalerr");
/*      */ 
/*      */ 
/*      */             
/* 1301 */             if (this.strict) {
/* 1302 */               return getString(0);
/*      */             }
/*      */           } 
/* 1305 */           this.ch = readCh();
/*      */           break;
/*      */         
/*      */         case 38:
/* 1309 */           if (this.strict && i < 0) {
/* 1310 */             this.ch = readCh();
/*      */             
/*      */             break;
/*      */           } 
/* 1314 */           arrayOfChar = parseEntityReference();
/* 1315 */           for (b = 0; b < arrayOfChar.length; b++) {
/* 1316 */             j = arrayOfChar[b];
/* 1317 */             addString((paramBoolean && j >= 65 && j <= 90) ? (97 + j - 65) : j);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case -1:
/* 1322 */           return getString(0);
/*      */         
/*      */         default:
/* 1325 */           if (paramBoolean && j >= 65 && j <= 90) {
/* 1326 */             j = 97 + j - 65;
/*      */           }
/* 1328 */           this.ch = readCh();
/*      */           break;
/*      */       } 
/* 1331 */       addString(j);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void parseAttributeSpecificationList(Element paramElement) throws IOException {
/*      */     while (true) {
/*      */       AttributeList attributeList;
/*      */       String str1, str2;
/* 1342 */       skipSpace();
/*      */       
/* 1344 */       switch (this.ch) {
/*      */         case -1:
/*      */         case 47:
/*      */         case 60:
/*      */         case 62:
/*      */           return;
/*      */         
/*      */         case 45:
/* 1352 */           if ((this.ch = readCh()) == 45) {
/* 1353 */             this.ch = readCh();
/* 1354 */             parseComment();
/* 1355 */             this.strpos = 0; continue;
/*      */           } 
/* 1357 */           error("invalid.tagchar", "-", paramElement.getName());
/* 1358 */           this.ch = readCh();
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1367 */       if (parseIdentifier(true))
/* 1368 */       { str1 = getString(0);
/* 1369 */         skipSpace();
/* 1370 */         if (this.ch == 61) {
/* 1371 */           this.ch = readCh();
/* 1372 */           skipSpace();
/* 1373 */           attributeList = paramElement.getAttribute(str1);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1378 */           str2 = parseAttributeValue((attributeList != null && attributeList.type != 1 && attributeList.type != 11 && attributeList.type != 7));
/*      */         } else {
/*      */           
/* 1381 */           str2 = str1;
/* 1382 */           attributeList = paramElement.getAttributeByValue(str2);
/* 1383 */           if (attributeList == null) {
/* 1384 */             attributeList = paramElement.getAttribute(str1);
/* 1385 */             if (attributeList != null) {
/* 1386 */               str2 = attributeList.getValue();
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 1391 */               str2 = null;
/*      */             } 
/*      */           } 
/*      */         }  }
/* 1395 */       else { if (!this.strict && this.ch == 44) {
/* 1396 */           this.ch = readCh(); continue;
/*      */         } 
/* 1398 */         if (!this.strict && this.ch == 34) {
/* 1399 */           this.ch = readCh();
/* 1400 */           skipSpace();
/* 1401 */           if (parseIdentifier(true)) {
/* 1402 */             str1 = getString(0);
/* 1403 */             if (this.ch == 34) {
/* 1404 */               this.ch = readCh();
/*      */             }
/* 1406 */             skipSpace();
/* 1407 */             if (this.ch == 61) {
/* 1408 */               this.ch = readCh();
/* 1409 */               skipSpace();
/* 1410 */               attributeList = paramElement.getAttribute(str1);
/* 1411 */               str2 = parseAttributeValue((attributeList != null && attributeList.type != 1 && attributeList.type != 11));
/*      */             }
/*      */             else {
/*      */               
/* 1415 */               str2 = str1;
/* 1416 */               attributeList = paramElement.getAttributeByValue(str2);
/* 1417 */               if (attributeList == null) {
/* 1418 */                 attributeList = paramElement.getAttribute(str1);
/* 1419 */                 if (attributeList != null) {
/* 1420 */                   str2 = attributeList.getValue();
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } else {
/* 1425 */             char[] arrayOfChar = { (char)this.ch };
/* 1426 */             error("invalid.tagchar", new String(arrayOfChar), paramElement.getName());
/* 1427 */             this.ch = readCh();
/*      */             continue;
/*      */           } 
/* 1430 */         } else if (!this.strict && this.attributes.isEmpty() && this.ch == 61) {
/* 1431 */           this.ch = readCh();
/* 1432 */           skipSpace();
/* 1433 */           str1 = paramElement.getName();
/* 1434 */           attributeList = paramElement.getAttribute(str1);
/* 1435 */           str2 = parseAttributeValue((attributeList != null && attributeList.type != 1 && attributeList.type != 11));
/*      */         } else {
/*      */           
/* 1438 */           if (!this.strict && this.ch == 61) {
/* 1439 */             this.ch = readCh();
/* 1440 */             skipSpace();
/* 1441 */             str2 = parseAttributeValue(true);
/* 1442 */             error("attvalerr");
/*      */             return;
/*      */           } 
/* 1445 */           char[] arrayOfChar = { (char)this.ch };
/* 1446 */           error("invalid.tagchar", new String(arrayOfChar), paramElement.getName());
/* 1447 */           if (!this.strict) {
/* 1448 */             this.ch = readCh();
/*      */             
/*      */             continue;
/*      */           } 
/*      */           return;
/*      */         }  }
/*      */       
/* 1455 */       if (attributeList != null) {
/* 1456 */         str1 = attributeList.getName();
/*      */       } else {
/* 1458 */         error("invalid.tagatt", str1, paramElement.getName());
/*      */       } 
/*      */ 
/*      */       
/* 1462 */       if (this.attributes.isDefined(str1)) {
/* 1463 */         error("multi.tagatt", str1, paramElement.getName());
/*      */       }
/* 1465 */       if (str2 == null) {
/* 1466 */         str2 = (attributeList != null && attributeList.value != null) ? attributeList.value : "#DEFAULT";
/*      */       }
/* 1468 */       else if (attributeList != null && attributeList.values != null && !attributeList.values.contains(str2)) {
/* 1469 */         error("invalid.tagattval", str1, paramElement.getName());
/*      */       } 
/* 1471 */       HTML.Attribute attribute = HTML.getAttributeKey(str1);
/* 1472 */       if (attribute == null) {
/* 1473 */         this.attributes.addAttribute(str1, str2); continue;
/*      */       } 
/* 1475 */       this.attributes.addAttribute(attribute, str2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String parseDTDMarkup() throws IOException {
/* 1486 */     StringBuilder stringBuilder = new StringBuilder();
/* 1487 */     this.ch = readCh();
/*      */     while (true) {
/* 1489 */       switch (this.ch) {
/*      */         case 62:
/* 1491 */           this.ch = readCh();
/* 1492 */           return stringBuilder.toString();
/*      */         case -1:
/* 1494 */           error("invalid.markup");
/* 1495 */           return stringBuilder.toString();
/*      */         case 10:
/* 1497 */           this.ln++;
/* 1498 */           this.ch = readCh();
/* 1499 */           this.lfCount++;
/*      */           continue;
/*      */         case 34:
/* 1502 */           this.ch = readCh();
/*      */           continue;
/*      */         case 13:
/* 1505 */           this.ln++;
/* 1506 */           if ((this.ch = readCh()) == 10) {
/* 1507 */             this.ch = readCh();
/* 1508 */             this.crlfCount++;
/*      */             continue;
/*      */           } 
/* 1511 */           this.crCount++;
/*      */           continue;
/*      */       } 
/*      */       
/* 1515 */       stringBuilder.append((char)(this.ch & 0xFF));
/* 1516 */       this.ch = readCh();
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
/*      */   protected boolean parseMarkupDeclarations(StringBuffer paramStringBuffer) throws IOException {
/* 1530 */     if (paramStringBuffer.length() == "DOCTYPE".length() && paramStringBuffer
/* 1531 */       .toString().toUpperCase().equals("DOCTYPE")) {
/* 1532 */       parseDTDMarkup();
/* 1533 */       return true;
/*      */     } 
/* 1535 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void parseInvalidTag() throws IOException {
/*      */     while (true) {
/* 1544 */       skipSpace();
/* 1545 */       switch (this.ch) {
/*      */         case -1:
/*      */         case 62:
/* 1548 */           this.ch = readCh();
/*      */           return;
/*      */         case 60:
/*      */           return;
/*      */       } 
/* 1553 */       this.ch = readCh();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void parseTag() throws IOException {
/*      */     Element element;
/*      */     StringBuffer stringBuffer;
/*      */     String str1;
/*      */     TagStack tagStack;
/*      */     String str2;
/* 1564 */     boolean bool1 = false;
/* 1565 */     boolean bool2 = false;
/* 1566 */     boolean bool3 = false;
/*      */     
/* 1568 */     switch (this.ch = readCh()) {
/*      */       case 33:
/* 1570 */         switch (this.ch = readCh()) {
/*      */           
/*      */           case 45:
/*      */             while (true) {
/* 1574 */               if (this.ch == 45) {
/* 1575 */                 if (!this.strict || (this.ch = readCh()) == 45) {
/* 1576 */                   this.ch = readCh();
/* 1577 */                   if (!this.strict && this.ch == 45) {
/* 1578 */                     this.ch = readCh();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/* 1583 */                   if (this.textpos != 0) {
/* 1584 */                     char[] arrayOfChar = new char[this.textpos];
/* 1585 */                     System.arraycopy(this.text, 0, arrayOfChar, 0, this.textpos);
/* 1586 */                     handleText(arrayOfChar);
/* 1587 */                     this.lastBlockStartPos = this.currentBlockStartPos;
/* 1588 */                     this.textpos = 0;
/*      */                   } 
/* 1590 */                   parseComment();
/* 1591 */                   this.last = makeTag(this.dtd.getElement("comment"), true);
/* 1592 */                   handleComment(getChars(0)); continue;
/*      */                 } 
/* 1594 */                 if (!bool2) {
/* 1595 */                   bool2 = true;
/* 1596 */                   error("invalid.commentchar", "-");
/*      */                 } 
/*      */               } 
/* 1599 */               skipSpace();
/* 1600 */               switch (this.ch) {
/*      */                 case 45:
/*      */                   continue;
/*      */                 case 62:
/* 1604 */                   this.ch = readCh();
/*      */                 case -1:
/*      */                   return;
/*      */               } 
/* 1608 */               this.ch = readCh();
/* 1609 */               if (!bool2) {
/* 1610 */                 bool2 = true;
/* 1611 */                 error("invalid.commentchar", 
/* 1612 */                     String.valueOf((char)this.ch));
/*      */               } 
/*      */             } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1620 */         stringBuffer = new StringBuffer();
/*      */         while (true) {
/* 1622 */           stringBuffer.append((char)this.ch);
/* 1623 */           if (parseMarkupDeclarations(stringBuffer)) {
/*      */             return;
/*      */           }
/* 1626 */           switch (this.ch) {
/*      */             case 62:
/* 1628 */               this.ch = readCh();
/*      */             case -1:
/* 1630 */               error("invalid.markup");
/*      */               return;
/*      */             case 10:
/* 1633 */               this.ln++;
/* 1634 */               this.ch = readCh();
/* 1635 */               this.lfCount++;
/*      */               continue;
/*      */             case 13:
/* 1638 */               this.ln++;
/* 1639 */               if ((this.ch = readCh()) == 10) {
/* 1640 */                 this.ch = readCh();
/* 1641 */                 this.crlfCount++;
/*      */                 continue;
/*      */               } 
/* 1644 */               this.crCount++;
/*      */               continue;
/*      */           } 
/*      */ 
/*      */           
/* 1649 */           this.ch = readCh();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 47:
/* 1657 */         switch (this.ch = readCh()) {
/*      */           case 62:
/* 1659 */             this.ch = readCh();
/*      */           
/*      */           case 60:
/* 1662 */             if (this.recent == null) {
/* 1663 */               error("invalid.shortend");
/*      */               return;
/*      */             } 
/* 1666 */             element = this.recent;
/*      */             break;
/*      */           
/*      */           default:
/* 1670 */             if (!parseIdentifier(true)) {
/* 1671 */               error("expected.endtagname");
/*      */               return;
/*      */             } 
/* 1674 */             skipSpace();
/* 1675 */             switch (this.ch) {
/*      */               case 62:
/* 1677 */                 this.ch = readCh();
/*      */                 break;
/*      */               case 60:
/*      */                 break;
/*      */               default:
/* 1682 */                 error("expected", "'>'");
/* 1683 */                 while (this.ch != -1 && this.ch != 10 && this.ch != 62) {
/* 1684 */                   this.ch = readCh();
/*      */                 }
/* 1686 */                 if (this.ch == 62) {
/* 1687 */                   this.ch = readCh();
/*      */                 }
/*      */                 break;
/*      */             } 
/* 1691 */             str1 = getString(0);
/* 1692 */             if (!this.dtd.elementExists(str1)) {
/* 1693 */               error("end.unrecognized", str1);
/*      */               
/* 1695 */               if (this.textpos > 0 && this.text[this.textpos - 1] == '\n') {
/* 1696 */                 this.textpos--;
/*      */               }
/* 1698 */               element = this.dtd.getElement("unknown");
/* 1699 */               element.name = str1;
/* 1700 */               bool3 = true; break;
/*      */             } 
/* 1702 */             element = this.dtd.getElement(str1);
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1711 */         if (this.stack == null) {
/* 1712 */           error("end.extra.tag", element.getName());
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 1717 */         if (this.textpos > 0 && this.text[this.textpos - 1] == '\n')
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1722 */           if (this.stack.pre) {
/* 1723 */             if (this.textpos > 1 && this.text[this.textpos - 2] != '\n') {
/* 1724 */               this.textpos--;
/*      */             }
/*      */           } else {
/* 1727 */             this.textpos--;
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
/* 1748 */         if (bool3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1754 */           TagElement tagElement1 = makeTag(element);
/* 1755 */           handleText(tagElement1);
/* 1756 */           this.attributes.addAttribute(HTML.Attribute.ENDTAG, "true");
/* 1757 */           handleEmptyTag(makeTag(element));
/* 1758 */           bool3 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1768 */         if (!this.strict) {
/* 1769 */           str1 = this.stack.elem.getName();
/*      */           
/* 1771 */           if (str1.equals("table"))
/*      */           {
/*      */             
/* 1774 */             if (!element.getName().equals(str1)) {
/* 1775 */               error("tag.ignore", element.getName());
/*      */ 
/*      */               
/*      */               return;
/*      */             } 
/*      */           }
/*      */           
/* 1782 */           if ((str1.equals("tr") || str1
/* 1783 */             .equals("td")) && 
/* 1784 */             !element.getName().equals("table") && 
/* 1785 */             !element.getName().equals(str1)) {
/* 1786 */             error("tag.ignore", element.getName());
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/* 1791 */         tagStack = this.stack;
/*      */         
/* 1793 */         while (tagStack != null && element != tagStack.elem) {
/* 1794 */           tagStack = tagStack.next;
/*      */         }
/* 1796 */         if (tagStack == null) {
/* 1797 */           error("unmatched.endtag", element.getName());
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1806 */         str2 = element.getName();
/* 1807 */         if (this.stack != tagStack && (str2
/* 1808 */           .equals("font") || str2
/* 1809 */           .equals("center"))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1816 */           if (str2.equals("center")) {
/* 1817 */             while (this.stack.elem.omitEnd() && this.stack != tagStack) {
/* 1818 */               endTag(true);
/*      */             }
/* 1820 */             if (this.stack.elem == element) {
/* 1821 */               endTag(false);
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1833 */         while (this.stack != tagStack) {
/* 1834 */           endTag(true);
/*      */         }
/*      */         
/* 1837 */         endTag(false);
/*      */         return;
/*      */       
/*      */       case -1:
/* 1841 */         error("eof");
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1846 */     if (!parseIdentifier(true)) {
/* 1847 */       element = this.recent;
/* 1848 */       if (this.ch != 62 || element == null) {
/* 1849 */         error("expected.tagname");
/*      */         return;
/*      */       } 
/*      */     } else {
/* 1853 */       String str = getString(0);
/*      */       
/* 1855 */       if (str.equals("image")) {
/* 1856 */         str = "img";
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1861 */       if (!this.dtd.elementExists(str)) {
/*      */         
/* 1863 */         error("tag.unrecognized ", str);
/* 1864 */         element = this.dtd.getElement("unknown");
/* 1865 */         element.name = str;
/* 1866 */         bool3 = true;
/*      */       } else {
/* 1868 */         element = this.dtd.getElement(str);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1873 */     parseAttributeSpecificationList(element);
/*      */     
/* 1875 */     switch (this.ch) {
/*      */       case 47:
/* 1877 */         bool1 = true;
/*      */       case 62:
/* 1879 */         this.ch = readCh();
/* 1880 */         if (this.ch == 62 && bool1) {
/* 1881 */           this.ch = readCh();
/*      */         }
/*      */         break;
/*      */       case 60:
/*      */         break;
/*      */       default:
/* 1887 */         error("expected", "'>'");
/*      */         break;
/*      */     } 
/*      */     
/* 1891 */     if (!this.strict && 
/* 1892 */       element.getName().equals("script")) {
/* 1893 */       error("javascript.unsupported");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1899 */     if (!element.isEmpty()) {
/* 1900 */       if (this.ch == 10) {
/* 1901 */         this.ln++;
/* 1902 */         this.lfCount++;
/* 1903 */         this.ch = readCh();
/* 1904 */       } else if (this.ch == 13) {
/* 1905 */         this.ln++;
/* 1906 */         if ((this.ch = readCh()) == 10) {
/* 1907 */           this.ch = readCh();
/* 1908 */           this.crlfCount++;
/*      */         } else {
/*      */           
/* 1911 */           this.crCount++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1917 */     TagElement tagElement = makeTag(element, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1940 */     if (!bool3) {
/* 1941 */       legalTagContext(tagElement);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1947 */       if (!this.strict && this.skipTag) {
/* 1948 */         this.skipTag = false;
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1956 */     startTag(tagElement);
/*      */     
/* 1958 */     if (!element.isEmpty()) {
/* 1959 */       switch (element.getType()) {
/*      */         case 1:
/* 1961 */           parseLiteral(false);
/*      */           return;
/*      */         case 16:
/* 1964 */           parseLiteral(true);
/*      */           return;
/*      */       } 
/* 1967 */       if (this.stack != null) {
/* 1968 */         this.stack.net = bool1;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1977 */   private static final char[] SCRIPT_END_TAG = "</script>".toCharArray();
/* 1978 */   private static final char[] SCRIPT_END_TAG_UPPER_CASE = "</SCRIPT>"
/* 1979 */     .toCharArray(); private char[] buf; private int pos; private int len; private int currentPosition;
/*      */   
/*      */   void parseScript() throws IOException {
/* 1982 */     char[] arrayOfChar = new char[SCRIPT_END_TAG.length];
/* 1983 */     boolean bool = false;
/*      */ 
/*      */     
/*      */     while (true) {
/* 1987 */       byte b = 0;
/* 1988 */       while (!bool && b < SCRIPT_END_TAG.length && (SCRIPT_END_TAG[b] == this.ch || SCRIPT_END_TAG_UPPER_CASE[b] == this.ch)) {
/*      */ 
/*      */         
/* 1991 */         arrayOfChar[b] = (char)this.ch;
/* 1992 */         this.ch = readCh();
/* 1993 */         b++;
/*      */       } 
/* 1995 */       if (b == SCRIPT_END_TAG.length) {
/*      */         return;
/*      */       }
/*      */       
/* 1999 */       if (!bool && b == 1 && arrayOfChar[0] == "<!--".charAt(0)) {
/*      */         
/* 2001 */         while (b < "<!--".length() && "<!--"
/* 2002 */           .charAt(b) == this.ch) {
/* 2003 */           arrayOfChar[b] = (char)this.ch;
/* 2004 */           this.ch = readCh();
/* 2005 */           b++;
/*      */         } 
/* 2007 */         if (b == "<!--".length()) {
/* 2008 */           bool = true;
/*      */         }
/*      */       } 
/* 2011 */       if (bool) {
/* 2012 */         while (b < "-->".length() && "-->"
/* 2013 */           .charAt(b) == this.ch) {
/* 2014 */           arrayOfChar[b] = (char)this.ch;
/* 2015 */           this.ch = readCh();
/* 2016 */           b++;
/*      */         } 
/* 2018 */         if (b == "-->".length()) {
/* 2019 */           bool = false;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2024 */       if (b > 0) {
/* 2025 */         for (byte b1 = 0; b1 < b; b1++) {
/* 2026 */           addString(arrayOfChar[b1]);
/*      */         }
/*      */         continue;
/*      */       } 
/* 2030 */       switch (this.ch) {
/*      */         case -1:
/* 2032 */           error("eof.script");
/*      */           return;
/*      */         case 10:
/* 2035 */           this.ln++;
/* 2036 */           this.ch = readCh();
/* 2037 */           this.lfCount++;
/* 2038 */           addString(10);
/*      */           continue;
/*      */         case 13:
/* 2041 */           this.ln++;
/* 2042 */           if ((this.ch = readCh()) == 10) {
/* 2043 */             this.ch = readCh();
/* 2044 */             this.crlfCount++;
/*      */           } else {
/* 2046 */             this.crCount++;
/*      */           } 
/* 2048 */           addString(10);
/*      */           continue;
/*      */       } 
/* 2051 */       addString(this.ch);
/* 2052 */       this.ch = readCh();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void parseContent() throws IOException {
/* 2062 */     Thread thread = Thread.currentThread();
/*      */     while (true) {
/*      */       char[] arrayOfChar;
/* 2065 */       if (thread.isInterrupted()) {
/* 2066 */         thread.interrupt();
/*      */         
/*      */         break;
/*      */       } 
/* 2070 */       int i = this.ch;
/* 2071 */       this.currentBlockStartPos = this.currentPosition;
/*      */       
/* 2073 */       if (this.recent == this.dtd.script) {
/*      */ 
/*      */         
/* 2076 */         parseScript();
/* 2077 */         this.last = makeTag(this.dtd.getElement("comment"), true);
/*      */ 
/*      */         
/* 2080 */         String str = (new String(getChars(0))).trim();
/* 2081 */         int j = "<!--".length() + "-->".length();
/* 2082 */         if (str.startsWith("<!--") && str.endsWith("-->") && str
/* 2083 */           .length() >= j) {
/* 2084 */           str = str.substring("<!--".length(), str
/* 2085 */               .length() - "-->".length());
/*      */         }
/*      */ 
/*      */         
/* 2089 */         handleComment(str.toCharArray());
/* 2090 */         endTag(false);
/* 2091 */         this.lastBlockStartPos = this.currentPosition;
/*      */         
/*      */         continue;
/*      */       } 
/* 2095 */       switch (i) {
/*      */         case 60:
/* 2097 */           parseTag();
/* 2098 */           this.lastBlockStartPos = this.currentPosition;
/*      */           continue;
/*      */         
/*      */         case 47:
/* 2102 */           this.ch = readCh();
/* 2103 */           if (this.stack != null && this.stack.net) {
/*      */             
/* 2105 */             endTag(false); continue;
/*      */           } 
/* 2107 */           if (this.textpos == 0) {
/* 2108 */             if (!legalElementContext(this.dtd.pcdata)) {
/* 2109 */               error("unexpected.pcdata");
/*      */             }
/* 2111 */             if (this.last.breaksFlow()) {
/* 2112 */               this.space = false;
/*      */             }
/*      */           } 
/*      */           break;
/*      */         
/*      */         case -1:
/*      */           return;
/*      */         
/*      */         case 38:
/* 2121 */           if (this.textpos == 0) {
/* 2122 */             if (!legalElementContext(this.dtd.pcdata)) {
/* 2123 */               error("unexpected.pcdata");
/*      */             }
/* 2125 */             if (this.last.breaksFlow()) {
/* 2126 */               this.space = false;
/*      */             }
/*      */           } 
/* 2129 */           arrayOfChar = parseEntityReference();
/* 2130 */           if (this.textpos + arrayOfChar.length + 1 > this.text.length) {
/* 2131 */             char[] arrayOfChar1 = new char[Math.max(this.textpos + arrayOfChar.length + 128, this.text.length * 2)];
/* 2132 */             System.arraycopy(this.text, 0, arrayOfChar1, 0, this.text.length);
/* 2133 */             this.text = arrayOfChar1;
/*      */           } 
/* 2135 */           if (this.space) {
/* 2136 */             this.space = false;
/* 2137 */             this.text[this.textpos++] = ' ';
/*      */           } 
/* 2139 */           System.arraycopy(arrayOfChar, 0, this.text, this.textpos, arrayOfChar.length);
/* 2140 */           this.textpos += arrayOfChar.length;
/* 2141 */           this.ignoreSpace = false;
/*      */           continue;
/*      */         
/*      */         case 10:
/* 2145 */           this.ln++;
/* 2146 */           this.lfCount++;
/* 2147 */           this.ch = readCh();
/* 2148 */           if (this.stack != null && this.stack.pre) {
/*      */             break;
/*      */           }
/* 2151 */           if (this.textpos == 0) {
/* 2152 */             this.lastBlockStartPos = this.currentPosition;
/*      */           }
/* 2154 */           if (!this.ignoreSpace) {
/* 2155 */             this.space = true;
/*      */           }
/*      */           continue;
/*      */         
/*      */         case 13:
/* 2160 */           this.ln++;
/* 2161 */           i = 10;
/* 2162 */           if ((this.ch = readCh()) == 10) {
/* 2163 */             this.ch = readCh();
/* 2164 */             this.crlfCount++;
/*      */           } else {
/*      */             
/* 2167 */             this.crCount++;
/*      */           } 
/* 2169 */           if (this.stack != null && this.stack.pre) {
/*      */             break;
/*      */           }
/* 2172 */           if (this.textpos == 0) {
/* 2173 */             this.lastBlockStartPos = this.currentPosition;
/*      */           }
/* 2175 */           if (!this.ignoreSpace) {
/* 2176 */             this.space = true;
/*      */           }
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 9:
/*      */         case 32:
/* 2183 */           this.ch = readCh();
/* 2184 */           if (this.stack != null && this.stack.pre) {
/*      */             break;
/*      */           }
/* 2187 */           if (this.textpos == 0) {
/* 2188 */             this.lastBlockStartPos = this.currentPosition;
/*      */           }
/* 2190 */           if (!this.ignoreSpace) {
/* 2191 */             this.space = true;
/*      */           }
/*      */           continue;
/*      */         
/*      */         default:
/* 2196 */           if (this.textpos == 0) {
/* 2197 */             if (!legalElementContext(this.dtd.pcdata)) {
/* 2198 */               error("unexpected.pcdata");
/*      */             }
/* 2200 */             if (this.last.breaksFlow()) {
/* 2201 */               this.space = false;
/*      */             }
/*      */           } 
/* 2204 */           this.ch = readCh();
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2210 */       if (this.textpos + 2 > this.text.length) {
/* 2211 */         arrayOfChar = new char[this.text.length + 128];
/* 2212 */         System.arraycopy(this.text, 0, arrayOfChar, 0, this.text.length);
/* 2213 */         this.text = arrayOfChar;
/*      */       } 
/*      */ 
/*      */       
/* 2217 */       if (this.space) {
/* 2218 */         if (this.textpos == 0) {
/* 2219 */           this.lastBlockStartPos--;
/*      */         }
/* 2221 */         this.text[this.textpos++] = ' ';
/* 2222 */         this.space = false;
/*      */       } 
/* 2224 */       this.text[this.textpos++] = (char)i;
/* 2225 */       this.ignoreSpace = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getEndOfLineString() {
/* 2234 */     if (this.crlfCount >= this.crCount) {
/* 2235 */       if (this.lfCount >= this.crlfCount) {
/* 2236 */         return "\n";
/*      */       }
/*      */       
/* 2239 */       return "\r\n";
/*      */     } 
/*      */ 
/*      */     
/* 2243 */     if (this.crCount > this.lfCount) {
/* 2244 */       return "\r";
/*      */     }
/*      */     
/* 2247 */     return "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void parse(Reader paramReader) throws IOException {
/* 2256 */     this.in = paramReader;
/*      */     
/* 2258 */     this.ln = 1;
/*      */     
/* 2260 */     this.seenHtml = false;
/* 2261 */     this.seenHead = false;
/* 2262 */     this.seenBody = false;
/*      */     
/* 2264 */     this.crCount = this.lfCount = this.crlfCount = 0;
/*      */     
/*      */     try {
/* 2267 */       this.ch = readCh();
/* 2268 */       this.text = new char[1024];
/* 2269 */       this.str = new char[128];
/*      */       
/* 2271 */       parseContent();
/*      */ 
/*      */       
/* 2274 */       while (this.stack != null) {
/* 2275 */         endTag(true);
/*      */       }
/* 2277 */       paramReader.close();
/* 2278 */     } catch (IOException iOException) {
/* 2279 */       errorContext();
/* 2280 */       error("ioexception");
/* 2281 */       throw iOException;
/* 2282 */     } catch (Exception exception) {
/* 2283 */       errorContext();
/* 2284 */       error("exception", exception.getClass().getName(), exception.getMessage());
/* 2285 */       exception.printStackTrace();
/* 2286 */     } catch (ThreadDeath threadDeath) {
/* 2287 */       errorContext();
/* 2288 */       error("terminated");
/* 2289 */       threadDeath.printStackTrace();
/* 2290 */       throw threadDeath;
/*      */     } finally {
/* 2292 */       for (; this.stack != null; this.stack = this.stack.next) {
/* 2293 */         handleEndTag(this.stack.tag);
/*      */       }
/*      */       
/* 2296 */       this.text = null;
/* 2297 */       this.str = null;
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
/*      */   public Parser(DTD paramDTD) {
/* 2314 */     this.buf = new char[1];
/*      */     this.dtd = paramDTD;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int readCh() throws IOException {
/* 2326 */     if (this.pos >= this.len) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2332 */         this.len = this.in.read(this.buf);
/*      */       }
/* 2334 */       catch (InterruptedIOException interruptedIOException) {
/* 2335 */         throw interruptedIOException;
/*      */       } 
/*      */ 
/*      */       
/* 2339 */       if (this.len <= 0) {
/* 2340 */         return -1;
/*      */       }
/* 2342 */       this.pos = 0;
/*      */     } 
/* 2344 */     this.currentPosition++;
/*      */     
/* 2346 */     return this.buf[this.pos++];
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getCurrentPos() {
/* 2351 */     return this.currentPosition;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/parser/Parser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */