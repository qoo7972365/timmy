/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.Format;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.BitSet;
/*      */ import java.util.Map;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.JFormattedTextField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class InternationalFormatter
/*      */   extends DefaultFormatter
/*      */ {
/*   99 */   private static final Format.Field[] EMPTY_FIELD_ARRAY = new Format.Field[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Format format;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Comparable max;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Comparable min;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient BitSet literalMask;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient AttributedCharacterIterator iterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean validMask;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String string;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean ignoreDocumentMutate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InternationalFormatter() {
/*  169 */     setOverwriteMode(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InternationalFormatter(Format paramFormat) {
/*  179 */     this();
/*  180 */     setFormat(paramFormat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFormat(Format paramFormat) {
/*  191 */     this.format = paramFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Format getFormat() {
/*  201 */     return this.format;
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
/*      */   public void setMinimum(Comparable paramComparable) {
/*  214 */     if (getValueClass() == null && paramComparable != null) {
/*  215 */       setValueClass(paramComparable.getClass());
/*      */     }
/*  217 */     this.min = paramComparable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparable getMinimum() {
/*  226 */     return this.min;
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
/*      */   public void setMaximum(Comparable paramComparable) {
/*  239 */     if (getValueClass() == null && paramComparable != null) {
/*  240 */       setValueClass(paramComparable.getClass());
/*      */     }
/*  242 */     this.max = paramComparable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparable getMaximum() {
/*  251 */     return this.max;
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
/*      */   public void install(JFormattedTextField paramJFormattedTextField) {
/*  285 */     super.install(paramJFormattedTextField);
/*  286 */     updateMaskIfNecessary();
/*      */     
/*  288 */     positionCursorAtInitialLocation();
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
/*      */   public String valueToString(Object paramObject) throws ParseException {
/*  300 */     if (paramObject == null) {
/*  301 */       return "";
/*      */     }
/*  303 */     Format format = getFormat();
/*      */     
/*  305 */     if (format == null) {
/*  306 */       return paramObject.toString();
/*      */     }
/*  308 */     return format.format(paramObject);
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
/*      */   public Object stringToValue(String paramString) throws ParseException {
/*  320 */     Object object = stringToValue(paramString, getFormat());
/*      */ 
/*      */ 
/*      */     
/*  324 */     if (object != null && getValueClass() != null && 
/*  325 */       !getValueClass().isInstance(object)) {
/*  326 */       object = super.stringToValue(object.toString());
/*      */     }
/*      */     try {
/*  329 */       if (!isValidValue(object, true)) {
/*  330 */         throw new ParseException("Value not within min/max range", 0);
/*      */       }
/*  332 */     } catch (ClassCastException classCastException) {
/*  333 */       throw new ParseException("Class cast exception comparing values: " + classCastException, 0);
/*      */     } 
/*      */     
/*  336 */     return object;
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
/*      */   public Format.Field[] getFields(int paramInt) {
/*  350 */     if (getAllowsInvalid())
/*      */     {
/*  352 */       updateMask();
/*      */     }
/*      */     
/*  355 */     Map<AttributedCharacterIterator.Attribute, Object> map = getAttributes(paramInt);
/*      */     
/*  357 */     if (map != null && map.size() > 0) {
/*  358 */       ArrayList arrayList = new ArrayList();
/*      */       
/*  360 */       arrayList.addAll(map.keySet());
/*  361 */       return (Format.Field[])arrayList.toArray((Object[])EMPTY_FIELD_ARRAY);
/*      */     } 
/*  363 */     return EMPTY_FIELD_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() throws CloneNotSupportedException {
/*  373 */     InternationalFormatter internationalFormatter = (InternationalFormatter)super.clone();
/*      */     
/*  375 */     internationalFormatter.literalMask = null;
/*  376 */     internationalFormatter.iterator = null;
/*  377 */     internationalFormatter.validMask = false;
/*  378 */     internationalFormatter.string = null;
/*  379 */     return internationalFormatter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Action[] getActions() {
/*  387 */     if (getSupportsIncrement()) {
/*  388 */       return new Action[] { new IncrementAction("increment", 1), new IncrementAction("decrement", -1) };
/*      */     }
/*      */     
/*  391 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object stringToValue(String paramString, Format paramFormat) throws ParseException {
/*  399 */     if (paramFormat == null) {
/*  400 */       return paramString;
/*      */     }
/*  402 */     return paramFormat.parseObject(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isValidValue(Object paramObject, boolean paramBoolean) {
/*  413 */     Comparable<Object> comparable1 = getMinimum();
/*      */     
/*      */     try {
/*  416 */       if (comparable1 != null && comparable1.compareTo(paramObject) > 0) {
/*  417 */         return false;
/*      */       }
/*  419 */     } catch (ClassCastException classCastException) {
/*  420 */       if (paramBoolean) {
/*  421 */         throw classCastException;
/*      */       }
/*  423 */       return false;
/*      */     } 
/*      */     
/*  426 */     Comparable<Object> comparable2 = getMaximum();
/*      */     try {
/*  428 */       if (comparable2 != null && comparable2.compareTo(paramObject) < 0) {
/*  429 */         return false;
/*      */       }
/*  431 */     } catch (ClassCastException classCastException) {
/*  432 */       if (paramBoolean) {
/*  433 */         throw classCastException;
/*      */       }
/*  435 */       return false;
/*      */     } 
/*  437 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Map<AttributedCharacterIterator.Attribute, Object> getAttributes(int paramInt) {
/*  444 */     if (isValidMask()) {
/*  445 */       AttributedCharacterIterator attributedCharacterIterator = getIterator();
/*      */       
/*  447 */       if (paramInt >= 0 && paramInt <= attributedCharacterIterator.getEndIndex()) {
/*  448 */         attributedCharacterIterator.setIndex(paramInt);
/*  449 */         return attributedCharacterIterator.getAttributes();
/*      */       } 
/*      */     } 
/*  452 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getAttributeStart(AttributedCharacterIterator.Attribute paramAttribute) {
/*  462 */     if (isValidMask()) {
/*  463 */       AttributedCharacterIterator attributedCharacterIterator = getIterator();
/*      */       
/*  465 */       attributedCharacterIterator.first();
/*  466 */       while (attributedCharacterIterator.current() != Character.MAX_VALUE) {
/*  467 */         if (attributedCharacterIterator.getAttribute(paramAttribute) != null) {
/*  468 */           return attributedCharacterIterator.getIndex();
/*      */         }
/*  470 */         attributedCharacterIterator.next();
/*      */       } 
/*      */     } 
/*  473 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AttributedCharacterIterator getIterator() {
/*  481 */     return this.iterator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateMaskIfNecessary() {
/*  488 */     if (!getAllowsInvalid() && getFormat() != null) {
/*  489 */       if (!isValidMask()) {
/*  490 */         updateMask();
/*      */       } else {
/*      */         
/*  493 */         String str = getFormattedTextField().getText();
/*      */         
/*  495 */         if (!str.equals(this.string)) {
/*  496 */           updateMask();
/*      */         }
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
/*      */   void updateMask() {
/*  510 */     if (getFormat() != null) {
/*  511 */       Document document = getFormattedTextField().getDocument();
/*      */       
/*  513 */       this.validMask = false;
/*  514 */       if (document != null) {
/*      */         try {
/*  516 */           this.string = document.getText(0, document.getLength());
/*  517 */         } catch (BadLocationException badLocationException) {
/*  518 */           this.string = null;
/*      */         } 
/*  520 */         if (this.string != null) {
/*      */           
/*  522 */           try { Object object = stringToValue(this.string);
/*      */             
/*  524 */             AttributedCharacterIterator attributedCharacterIterator = getFormat().formatToCharacterIterator(object);
/*      */             
/*  526 */             updateMask(attributedCharacterIterator); }
/*      */           
/*  528 */           catch (ParseException parseException) {  }
/*  529 */           catch (IllegalArgumentException illegalArgumentException) {  }
/*  530 */           catch (NullPointerException nullPointerException) {}
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getLiteralCountTo(int paramInt) {
/*  540 */     byte b1 = 0;
/*      */     
/*  542 */     for (byte b2 = 0; b2 < paramInt; b2++) {
/*  543 */       if (isLiteral(b2)) {
/*  544 */         b1++;
/*      */       }
/*      */     } 
/*  547 */     return b1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isLiteral(int paramInt) {
/*  555 */     if (isValidMask() && paramInt < this.string.length()) {
/*  556 */       return this.literalMask.get(paramInt);
/*      */     }
/*  558 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   char getLiteral(int paramInt) {
/*  565 */     if (isValidMask() && this.string != null && paramInt < this.string.length()) {
/*  566 */       return this.string.charAt(paramInt);
/*      */     }
/*  568 */     return Character.MIN_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isNavigatable(int paramInt) {
/*  577 */     return !isLiteral(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateValue(Object paramObject) {
/*  584 */     super.updateValue(paramObject);
/*  585 */     updateMaskIfNecessary();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replace(DocumentFilter.FilterBypass paramFilterBypass, int paramInt1, int paramInt2, String paramString, AttributeSet paramAttributeSet) throws BadLocationException {
/*  595 */     if (this.ignoreDocumentMutate) {
/*  596 */       paramFilterBypass.replace(paramInt1, paramInt2, paramString, paramAttributeSet);
/*      */       return;
/*      */     } 
/*  599 */     super.replace(paramFilterBypass, paramInt1, paramInt2, paramString, paramAttributeSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getNextNonliteralIndex(int paramInt1, int paramInt2) {
/*  609 */     int i = getFormattedTextField().getDocument().getLength();
/*      */     
/*  611 */     while (paramInt1 >= 0 && paramInt1 < i) {
/*  612 */       if (isLiteral(paramInt1)) {
/*  613 */         paramInt1 += paramInt2;
/*      */         continue;
/*      */       } 
/*  616 */       return paramInt1;
/*      */     } 
/*      */     
/*  619 */     return (paramInt2 == -1) ? 0 : i;
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
/*      */   boolean canReplace(DefaultFormatter.ReplaceHolder paramReplaceHolder) {
/*  632 */     if (!getAllowsInvalid()) {
/*  633 */       String str = paramReplaceHolder.text;
/*  634 */       boolean bool1 = (str != null) ? str.length() : false;
/*  635 */       JFormattedTextField jFormattedTextField = getFormattedTextField();
/*      */       
/*  637 */       if (!bool1 && paramReplaceHolder.length == 1 && jFormattedTextField.getSelectionStart() != paramReplaceHolder.offset) {
/*      */         
/*  639 */         paramReplaceHolder.offset = getNextNonliteralIndex(paramReplaceHolder.offset, -1);
/*  640 */       } else if (getOverwriteMode()) {
/*  641 */         int i = paramReplaceHolder.offset;
/*  642 */         int j = i;
/*  643 */         boolean bool2 = false;
/*      */         
/*  645 */         for (byte b = 0; b < paramReplaceHolder.length; b++) {
/*  646 */           for (; isLiteral(i); i++);
/*  647 */           if (i >= this.string.length()) {
/*  648 */             i = j;
/*  649 */             bool2 = true;
/*      */             break;
/*      */           } 
/*  652 */           j = ++i;
/*      */         } 
/*  654 */         if (bool2 || jFormattedTextField.getSelectedText() == null) {
/*  655 */           paramReplaceHolder.length = i - paramReplaceHolder.offset;
/*      */         }
/*      */       }
/*  658 */       else if (bool1) {
/*      */         
/*  660 */         paramReplaceHolder.offset = getNextNonliteralIndex(paramReplaceHolder.offset, 1);
/*      */       }
/*      */       else {
/*      */         
/*  664 */         paramReplaceHolder.offset = getNextNonliteralIndex(paramReplaceHolder.offset, -1);
/*      */       } 
/*  666 */       ((ExtendedReplaceHolder)paramReplaceHolder).endOffset = paramReplaceHolder.offset;
/*  667 */       ((ExtendedReplaceHolder)paramReplaceHolder)
/*  668 */         .endTextLength = (paramReplaceHolder.text != null) ? paramReplaceHolder.text.length() : 0;
/*      */     } else {
/*      */       
/*  671 */       ((ExtendedReplaceHolder)paramReplaceHolder).endOffset = paramReplaceHolder.offset;
/*  672 */       ((ExtendedReplaceHolder)paramReplaceHolder)
/*  673 */         .endTextLength = (paramReplaceHolder.text != null) ? paramReplaceHolder.text.length() : 0;
/*      */     } 
/*  675 */     boolean bool = super.canReplace(paramReplaceHolder);
/*  676 */     if (bool && !getAllowsInvalid()) {
/*  677 */       ((ExtendedReplaceHolder)paramReplaceHolder).resetFromValue(this);
/*      */     }
/*  679 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean replace(DefaultFormatter.ReplaceHolder paramReplaceHolder) throws BadLocationException {
/*  689 */     int i = -1;
/*  690 */     byte b = 1;
/*  691 */     int j = -1;
/*      */     
/*  693 */     if (paramReplaceHolder.length > 0 && (paramReplaceHolder.text == null || paramReplaceHolder.text.length() == 0) && (
/*  694 */       getFormattedTextField().getSelectionStart() != paramReplaceHolder.offset || paramReplaceHolder.length > 1))
/*      */     {
/*  696 */       b = -1;
/*      */     }
/*  698 */     if (!getAllowsInvalid()) {
/*  699 */       if ((paramReplaceHolder.text == null || paramReplaceHolder.text.length() == 0) && paramReplaceHolder.length > 0) {
/*      */         
/*  701 */         i = getFormattedTextField().getSelectionStart();
/*      */       } else {
/*      */         
/*  704 */         i = paramReplaceHolder.offset;
/*      */       } 
/*  706 */       j = getLiteralCountTo(i);
/*      */     } 
/*  708 */     if (super.replace(paramReplaceHolder)) {
/*  709 */       if (i != -1) {
/*  710 */         int k = ((ExtendedReplaceHolder)paramReplaceHolder).endOffset;
/*      */         
/*  712 */         k += ((ExtendedReplaceHolder)paramReplaceHolder).endTextLength;
/*  713 */         repositionCursor(j, k, b);
/*      */       } else {
/*      */         
/*  716 */         i = ((ExtendedReplaceHolder)paramReplaceHolder).endOffset;
/*  717 */         if (b == 1) {
/*  718 */           i += ((ExtendedReplaceHolder)paramReplaceHolder).endTextLength;
/*      */         }
/*  720 */         repositionCursor(i, b);
/*      */       } 
/*  722 */       return true;
/*      */     } 
/*  724 */     return false;
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
/*      */   private void repositionCursor(int paramInt1, int paramInt2, int paramInt3) {
/*  736 */     int i = getLiteralCountTo(paramInt2);
/*      */     
/*  738 */     if (i != paramInt2) {
/*  739 */       paramInt2 -= paramInt1;
/*  740 */       for (byte b = 0; b < paramInt2; b++) {
/*  741 */         if (isLiteral(b)) {
/*  742 */           paramInt2++;
/*      */         }
/*      */       } 
/*      */     } 
/*  746 */     repositionCursor(paramInt2, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   char getBufferedChar(int paramInt) {
/*  754 */     if (isValidMask() && 
/*  755 */       this.string != null && paramInt < this.string.length()) {
/*  756 */       return this.string.charAt(paramInt);
/*      */     }
/*      */     
/*  759 */     return Character.MIN_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isValidMask() {
/*  766 */     return this.validMask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isLiteral(Map paramMap) {
/*  773 */     return (paramMap == null || paramMap.size() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMask(AttributedCharacterIterator paramAttributedCharacterIterator) {
/*  782 */     if (paramAttributedCharacterIterator != null) {
/*  783 */       this.validMask = true;
/*  784 */       this.iterator = paramAttributedCharacterIterator;
/*      */ 
/*      */       
/*  787 */       if (this.literalMask == null) {
/*  788 */         this.literalMask = new BitSet();
/*      */       } else {
/*      */         
/*  791 */         for (int i = this.literalMask.length() - 1; i >= 0; 
/*  792 */           i--) {
/*  793 */           this.literalMask.clear(i);
/*      */         }
/*      */       } 
/*      */       
/*  797 */       paramAttributedCharacterIterator.first();
/*  798 */       while (paramAttributedCharacterIterator.current() != Character.MAX_VALUE) {
/*  799 */         Map<AttributedCharacterIterator.Attribute, Object> map = paramAttributedCharacterIterator.getAttributes();
/*  800 */         boolean bool = isLiteral(map);
/*  801 */         int i = paramAttributedCharacterIterator.getIndex();
/*  802 */         int j = paramAttributedCharacterIterator.getRunLimit();
/*      */         
/*  804 */         while (i < j) {
/*  805 */           if (bool) {
/*  806 */             this.literalMask.set(i);
/*      */           } else {
/*      */             
/*  809 */             this.literalMask.clear(i);
/*      */           } 
/*  811 */           i++;
/*      */         } 
/*  813 */         paramAttributedCharacterIterator.setIndex(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean canIncrement(Object paramObject, int paramInt) {
/*  824 */     return (paramObject != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void selectField(Object paramObject, int paramInt) {
/*  831 */     AttributedCharacterIterator attributedCharacterIterator = getIterator();
/*      */     
/*  833 */     if (attributedCharacterIterator != null && paramObject instanceof AttributedCharacterIterator.Attribute) {
/*      */       
/*  835 */       AttributedCharacterIterator.Attribute attribute = (AttributedCharacterIterator.Attribute)paramObject;
/*      */ 
/*      */       
/*  838 */       attributedCharacterIterator.first();
/*  839 */       while (attributedCharacterIterator.current() != Character.MAX_VALUE) {
/*  840 */         while (attributedCharacterIterator.getAttribute(attribute) == null && attributedCharacterIterator
/*  841 */           .next() != Character.MAX_VALUE);
/*  842 */         if (attributedCharacterIterator.current() != Character.MAX_VALUE) {
/*  843 */           int i = attributedCharacterIterator.getRunLimit(attribute);
/*      */           
/*  845 */           if (--paramInt <= 0) {
/*  846 */             getFormattedTextField().select(attributedCharacterIterator.getIndex(), i);
/*      */             
/*      */             break;
/*      */           } 
/*  850 */           attributedCharacterIterator.setIndex(i);
/*  851 */           attributedCharacterIterator.next();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object getAdjustField(int paramInt, Map paramMap) {
/*  861 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getFieldTypeCountTo(Object paramObject, int paramInt) {
/*  870 */     AttributedCharacterIterator attributedCharacterIterator = getIterator();
/*  871 */     byte b = 0;
/*      */     
/*  873 */     if (attributedCharacterIterator != null && paramObject instanceof AttributedCharacterIterator.Attribute) {
/*      */       
/*  875 */       AttributedCharacterIterator.Attribute attribute = (AttributedCharacterIterator.Attribute)paramObject;
/*      */ 
/*      */       
/*  878 */       attributedCharacterIterator.first();
/*  879 */       while (attributedCharacterIterator.getIndex() < paramInt) {
/*  880 */         while (attributedCharacterIterator.getAttribute(attribute) == null && attributedCharacterIterator
/*  881 */           .next() != Character.MAX_VALUE);
/*  882 */         if (attributedCharacterIterator.current() != Character.MAX_VALUE) {
/*  883 */           attributedCharacterIterator.setIndex(attributedCharacterIterator.getRunLimit(attribute));
/*  884 */           attributedCharacterIterator.next();
/*  885 */           b++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  892 */     return b;
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
/*      */   Object adjustValue(Object paramObject1, Map paramMap, Object paramObject2, int paramInt) throws BadLocationException, ParseException {
/*  905 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean getSupportsIncrement() {
/*  916 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void resetValue(Object paramObject) throws BadLocationException, ParseException {
/*  924 */     Document document = getFormattedTextField().getDocument();
/*  925 */     String str = valueToString(paramObject);
/*      */     
/*      */     try {
/*  928 */       this.ignoreDocumentMutate = true;
/*  929 */       document.remove(0, document.getLength());
/*  930 */       document.insertString(0, str, null);
/*      */     } finally {
/*  932 */       this.ignoreDocumentMutate = false;
/*      */     } 
/*  934 */     updateValue(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  943 */     paramObjectInputStream.defaultReadObject();
/*  944 */     updateMaskIfNecessary();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DefaultFormatter.ReplaceHolder getReplaceHolder(DocumentFilter.FilterBypass paramFilterBypass, int paramInt1, int paramInt2, String paramString, AttributeSet paramAttributeSet) {
/*  954 */     if (this.replaceHolder == null) {
/*  955 */       this.replaceHolder = new ExtendedReplaceHolder();
/*      */     }
/*  957 */     return super.getReplaceHolder(paramFilterBypass, paramInt1, paramInt2, paramString, paramAttributeSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ExtendedReplaceHolder
/*      */     extends DefaultFormatter.ReplaceHolder
/*      */   {
/*      */     int endOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int endTextLength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void resetFromValue(InternationalFormatter param1InternationalFormatter) {
/*  981 */       this.offset = 0;
/*      */       try {
/*  983 */         this.text = param1InternationalFormatter.valueToString(this.value);
/*  984 */       } catch (ParseException parseException) {
/*      */ 
/*      */         
/*  987 */         this.text = "";
/*      */       } 
/*  989 */       this.length = this.fb.getDocument().getLength();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class IncrementAction
/*      */     extends AbstractAction
/*      */   {
/*      */     private int direction;
/*      */ 
/*      */ 
/*      */     
/*      */     IncrementAction(String param1String, int param1Int) {
/* 1003 */       super(param1String);
/* 1004 */       this.direction = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1009 */       if (InternationalFormatter.this.getFormattedTextField().isEditable()) {
/* 1010 */         if (InternationalFormatter.this.getAllowsInvalid())
/*      */         {
/* 1012 */           InternationalFormatter.this.updateMask();
/*      */         }
/*      */         
/* 1015 */         boolean bool = false;
/*      */         
/* 1017 */         if (InternationalFormatter.this.isValidMask()) {
/* 1018 */           int i = InternationalFormatter.this.getFormattedTextField().getSelectionStart();
/*      */           
/* 1020 */           if (i != -1) {
/* 1021 */             AttributedCharacterIterator attributedCharacterIterator = InternationalFormatter.this.getIterator();
/*      */             
/* 1023 */             attributedCharacterIterator.setIndex(i);
/*      */             
/* 1025 */             Map<AttributedCharacterIterator.Attribute, Object> map = attributedCharacterIterator.getAttributes();
/* 1026 */             Object object = InternationalFormatter.this.getAdjustField(i, map);
/*      */             
/* 1028 */             if (InternationalFormatter.this.canIncrement(object, i)) {
/*      */               
/* 1030 */               try { Object object1 = InternationalFormatter.this.stringToValue(InternationalFormatter.this
/* 1031 */                     .getFormattedTextField().getText());
/* 1032 */                 int j = InternationalFormatter.this.getFieldTypeCountTo(object, i);
/*      */ 
/*      */                 
/* 1035 */                 object1 = InternationalFormatter.this.adjustValue(object1, map, object, this.direction);
/*      */                 
/* 1037 */                 if (object1 != null && InternationalFormatter.this.isValidValue(object1, false)) {
/* 1038 */                   InternationalFormatter.this.resetValue(object1);
/* 1039 */                   InternationalFormatter.this.updateMask();
/*      */                   
/* 1041 */                   if (InternationalFormatter.this.isValidMask()) {
/* 1042 */                     InternationalFormatter.this.selectField(object, j);
/*      */                   }
/* 1044 */                   bool = true;
/*      */                 }
/*      */                  }
/* 1047 */               catch (ParseException parseException) {  }
/* 1048 */               catch (BadLocationException badLocationException) {}
/*      */             }
/*      */           } 
/*      */         } 
/* 1052 */         if (!bool)
/* 1053 */           InternationalFormatter.this.invalidEdit(); 
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/InternationalFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */